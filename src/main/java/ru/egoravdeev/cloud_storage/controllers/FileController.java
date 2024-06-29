package ru.egoravdeev.cloud_storage.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.egoravdeev.cloud_storage.exception.ErrorDeleteFile;
import ru.egoravdeev.cloud_storage.exception.ErrorInputData;
import ru.egoravdeev.cloud_storage.exception.ErrorUpdateFile;
import ru.egoravdeev.cloud_storage.exception.ErrorUploadFile;
import ru.egoravdeev.cloud_storage.model.File;
import ru.egoravdeev.cloud_storage.services.FileService;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("file")
    public void uploadFile(@RequestParam String filename, @RequestParam String hash, @RequestParam MultipartFile file) throws IOException {

        if (filename == null || filename.isEmpty() || hash == null || hash.isEmpty() || file.isEmpty()) {
            throw new ErrorInputData("Error input data", HttpStatus.BAD_REQUEST);
        } else {
            fileService.uploadFile(File.builder().filename(filename).hash(hash).data(file.getBytes()).build());
        }

    }

    @GetMapping("file")
    public void downloadFile(@RequestParam String filename, HttpServletResponse response) {
        if (filename == null || filename.isEmpty()) {
            throw new ErrorInputData("Error input data", HttpStatus.BAD_REQUEST);
        }
        var file = fileService.downloadFile(filename);
        if (file != null) {
            response.setContentType("multipart/form-data");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"");

            try (OutputStream out =response.getOutputStream()) {
                out.write(file.getData());
                out.flush();
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        throw new ErrorUploadFile("Error upload file", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("file")
    public void deleteFile(@RequestParam String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new ErrorInputData("Error input data", HttpStatus.BAD_REQUEST);
        }
        if (!fileService.deleteFile(filename)) {
            throw new ErrorDeleteFile("Error delete file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("file")
    public void updateFile(@RequestParam("filename") String oldFilename, @RequestBody Map<String, String> requestBody) {
        var newFilename = requestBody.get("name");
        if (oldFilename == null || oldFilename.isEmpty() || requestBody.isEmpty() || newFilename == null || newFilename.isEmpty()) {
            throw new ErrorInputData("Error input data", HttpStatus.BAD_REQUEST);
        }

        if (!fileService.updateFile(oldFilename, newFilename)) {
            throw new ErrorUpdateFile("Error update file`s name", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package ru.egoravdeev.cloud_storage.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.egoravdeev.cloud_storage.exception.ErrorUploadFile;
import ru.egoravdeev.cloud_storage.model.File;
import ru.egoravdeev.cloud_storage.repositories.FileRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private DataSource dataSource;

    public FileService(FileRepository fileRepository, DataSource dataSource) {
        this.fileRepository = fileRepository;
        this.dataSource = dataSource;
    }

    public void uploadFile(File file) {
        fileRepository.save(file);
    }

    @Transactional
    public File downloadFile(String fileName) {
        var file = fileRepository.findByFilename(fileName);
        if (file.isPresent()) {
            try (Connection connection = dataSource.getConnection()) {
                connection.setAutoCommit(false);

                var fetchedFile = file.get();

                connection.setAutoCommit(true);

                return fetchedFile;
            } catch (SQLException e) {
                throw new ErrorUploadFile("Error upload file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return null;
    }

    @Transactional
    public boolean deleteFile(String filename) {
        var file = downloadFile(filename);

        if (file != null) {
            fileRepository.delete(file);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateFile(String oldFilename, String newFilename) {
        var file = downloadFile(oldFilename);

        if (file != null) {
            file.setFilename(newFilename);
            fileRepository.save(file);
            return true;
        }

        return false;
    }
}

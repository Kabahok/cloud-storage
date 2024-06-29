package ru.egoravdeev.cloud_storage.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.egoravdeev.cloud_storage.dto.ResponseError;
import ru.egoravdeev.cloud_storage.exception.ErrorDeleteFile;
import ru.egoravdeev.cloud_storage.exception.ErrorInputData;
import ru.egoravdeev.cloud_storage.exception.ErrorUpdateFile;
import ru.egoravdeev.cloud_storage.exception.ErrorUploadFile;



@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<ResponseError> errorInputDataHandler(ErrorInputData e) {
        return new ResponseEntity<>(new ResponseError(e.getMessage(), e.getStatus().value()), e.getStatus());
    }

    @ExceptionHandler(ErrorUploadFile.class)
    public ResponseEntity<ResponseError> errorUploadFileHandler(ErrorUploadFile e) {
        return new ResponseEntity<>(new ResponseError(e.getMessage(), e.getStatus().value()), e.getStatus());
    }

    @ExceptionHandler (ErrorDeleteFile.class)
    public ResponseEntity<ResponseError> errorDeleteFileHandler(ErrorDeleteFile e) {
        return new ResponseEntity<>(new ResponseError(e.getMessage(), e.getStatus().value()), e.getStatus());
    }

    @ExceptionHandler(ErrorUpdateFile.class)
    public ResponseEntity<ResponseError> errorUpdateFileHandler(ErrorUpdateFile e) {
        return new ResponseEntity<>(new ResponseError(e.getMessage(), e.getStatus().value()), e.getStatus());
    }

}

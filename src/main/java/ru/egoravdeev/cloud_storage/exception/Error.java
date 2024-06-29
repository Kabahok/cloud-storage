package ru.egoravdeev.cloud_storage.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Error extends RuntimeException {
    private HttpStatus status;
    public Error(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

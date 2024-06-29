package ru.egoravdeev.cloud_storage.exception;

import org.springframework.http.HttpStatus;

public class ErrorUploadFile extends Error {
    public ErrorUploadFile(String message, HttpStatus status) {
        super(message, status);
    }
}

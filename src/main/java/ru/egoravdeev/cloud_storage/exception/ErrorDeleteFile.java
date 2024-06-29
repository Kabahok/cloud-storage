package ru.egoravdeev.cloud_storage.exception;

import org.springframework.http.HttpStatus;

public class ErrorDeleteFile extends Error {
    public ErrorDeleteFile(String message, HttpStatus status) {
        super(message, status);
    }
}

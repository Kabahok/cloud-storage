package ru.egoravdeev.cloud_storage.exception;

import org.springframework.http.HttpStatus;

public class ErrorUpdateFile extends Error {
    public ErrorUpdateFile(String message, HttpStatus status) {
        super(message, status);
    }
}

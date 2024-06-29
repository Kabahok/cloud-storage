package ru.egoravdeev.cloud_storage.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorInputData extends Error {
    public ErrorInputData(String message, HttpStatus status) {
        super(message, status);
    }
}

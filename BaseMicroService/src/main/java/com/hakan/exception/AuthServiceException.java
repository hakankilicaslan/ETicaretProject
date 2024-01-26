package com.hakan.exception;

import lombok.Getter;

@Getter
public class AuthServiceException extends RuntimeException{
    private final ErrorType errorType;

    public AuthServiceException(ErrorType errorType) {
        super(errorType.getMesaj());
        this.errorType = errorType;
    }
    public AuthServiceException(ErrorType errorType, String mesaj) {
        super(mesaj);
        this.errorType = errorType;
    }

}

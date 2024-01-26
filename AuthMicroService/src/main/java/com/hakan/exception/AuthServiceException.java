package com.hakan.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AuthServiceException extends RuntimeException{
    private final List<ErrorType> errorTypes;

    public AuthServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorTypes = new ArrayList<>();
        this.errorTypes.add(errorType);
    }

    public AuthServiceException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorTypes = new ArrayList<>();
        this.errorTypes.add(errorType);
    }

    public AuthServiceException(List<ErrorType> errorTypes) {
        super("Birden Fazla Hata Meydana Geldi.");
        this.errorTypes = errorTypes;
    }

}

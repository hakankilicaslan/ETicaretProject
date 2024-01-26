package com.hakan.exception;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UserProfileServiceException extends RuntimeException{
    private final List<ErrorType> errorTypes;

    public UserProfileServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorTypes = new ArrayList<>();
        this.errorTypes.add(errorType);
    }

    public UserProfileServiceException(ErrorType errorType, String customMesaj) {
        super(customMesaj);
        this.errorTypes = new ArrayList<>();
        this.errorTypes.add(errorType);
    }

    public UserProfileServiceException(List<ErrorType> errorTypes) {
        super("Birden Fazla Hata Meydana Geldi.");
        this.errorTypes = errorTypes;
    }

}

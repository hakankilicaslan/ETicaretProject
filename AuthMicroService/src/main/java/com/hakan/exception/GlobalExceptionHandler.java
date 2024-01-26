package com.hakan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleManagerException(AuthServiceException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // Varsayılan durum
        List<ErrorType> errorTypes = exception.getErrorTypes();
        List<String> errorMessages = new ArrayList<>();
        for (ErrorType errorType : errorTypes) {
            httpStatus = errorType.getHttpStatus(); //bir HttpStatus alacağız.
            errorMessages.add(errorType.getMessage());
        }
        //create error yerine kullanıldı:
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage("Hatalar:"); // Sadece ilk hatayı mesaj olarak göstermek için.
        errorMessage.setFields(errorMessages);

        return new ResponseEntity<>(errorMessage, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorType errorType = ErrorType.PARAMETER_NOT_VALID;
        List<String> fields = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(e-> fields.add(e.getField()+": " + e.getDefaultMessage()));
        ErrorMessage errorMessage=createError(errorType,ex);
        errorMessage.setFields(fields);
        return  new ResponseEntity<>(errorMessage,errorType.getHttpStatus());
    }

    /*
    hata mesajı oluşturmak için metod.
     */
    private ErrorMessage createError(ErrorType errorType, Exception e){
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }
}

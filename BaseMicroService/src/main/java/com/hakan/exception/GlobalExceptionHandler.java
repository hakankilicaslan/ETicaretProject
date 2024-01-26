package com.hakan.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorMesaj> handleRuntimeException(RuntimeException exception){
        return ResponseEntity.ok(ErrorMesaj.builder()
                .code(9999)
                .mesaj("Beklenmeyen runtime hatası "+exception.getMessage())
                .build());
    }

    @ExceptionHandler(AuthServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorMesaj> handleDemoException(AuthServiceException exception){
        return ResponseEntity.
                status(exception.getErrorType().getHttpStatus()).
                body(ErrorMesaj.builder()
                        .code(exception.getErrorType().getCode())
                        .mesaj(exception.getErrorType().getMesaj())
                        .build());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<ErrorMesaj> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        return ResponseEntity.ok(ErrorMesaj.builder()
                .code(6666)
                .mesaj("Değerler uyuşmadı... "+exception.getMessage())
                .build());
    }
}

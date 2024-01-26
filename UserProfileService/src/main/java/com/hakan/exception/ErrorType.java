package com.hakan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorType {

    REGISTER_PASSWORD_MISMATCH(1001,"Girilen şifreler uyuşmadı. Lütfen tekrar deneyiniz...",HttpStatus.BAD_REQUEST),
    REGISTER_EMAIL_ALREADY_EXISTS(1002,"Bu email adresi daha önce kaydedilmiş...",HttpStatus.BAD_REQUEST),

    LOGIN_EMAILORPASSWORD_NOT_EXISTS(1003,"Kullanıcı adı veya şifre yanlış",HttpStatus.BAD_REQUEST),

    INVALID_TOKEN(1004,"Geçersiz token",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN_FORMAT(1005,"Geçersiz token formatı",HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(1006,"Kullanıcı bulunamadı",HttpStatus.NOT_FOUND),

    INTERNAL_SERVER_ERROR(5002,"Server hatası",HttpStatus.INTERNAL_SERVER_ERROR),
    RUNTIME_ERROR(6001,"Runtime hatası",HttpStatus.BAD_REQUEST),
    PARAMETER_NOT_VALID(5000,"Parametre Hatası",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus httpStatus;

}

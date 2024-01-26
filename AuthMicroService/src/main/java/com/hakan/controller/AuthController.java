package com.hakan.controller;

import com.hakan.dto.request.LoginRequestDto;
import com.hakan.dto.request.RegisterRequestDto;
import com.hakan.exception.AuthServiceException;
import com.hakan.exception.ErrorType;
import com.hakan.repository.entity.Auth;
import com.hakan.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import static com.hakan.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT+AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping(REGISTER)
    public ResponseEntity<Auth> register(@RequestBody @Valid RegisterRequestDto dto){
       //Bu tarz işlemleri genelde service tarafında yapıyoruz. Şifrelerin uyuşmama işi için DB üzerinde herhangi bir kontrol yapmayacağız.
       //Controller içinde şifreleri kontrol edip eşleşmiyorsa DB içine gitmeden daha hızlı bir şekilde kullanıcıya direkt hata gönderiyoruz.
       if(!dto.getPassword().equals(dto.getRepassword()))
           throw new AuthServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);
       return ResponseEntity.ok(service.register(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto){
       return ResponseEntity.ok(service.login(dto));
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<Auth>> findAll(String token){ //Dışarıdan Token doğru gönderildiyse bütün kullanıcıları listeleyeceğiz.
        return ResponseEntity.ok(service.findAll(token));
    }

    /**
     * ApiGateway Test Endpoint: Denemek için bu metodu yazıyoruz.
     */
    @GetMapping("/message")
    public ResponseEntity<String> getMessage(){
        return ResponseEntity.ok("AuthService getMessage erişim sağlandı.");
    }
}

package com.hakan.utility;

import org.springframework.stereotype.Component;

@Component //TokenManager'ın bir nesne olarak üretilmesini istediğimiz için @Component olarak işaretliyoruz. Bir Beani oluşturuluyor.
public class TokenManager {

    public String createToken(Long id){ //1. Token üreteceğiz.
        return "authtoken:"+id;
    }

    public Long getIdFromToken(String token){ //2. Üretilen tokendan bilgi çıkarımı yapacağız.
        String[] split = token.split(":");
        return Long.parseLong(split[1]);
    }

}

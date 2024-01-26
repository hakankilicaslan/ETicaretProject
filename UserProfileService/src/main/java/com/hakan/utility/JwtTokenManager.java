package com.hakan.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component //JwtTokenManager'ın bir nesne olarak üretilmesini istediğimiz için @Component olarak işaretliyoruz. Bir Beani oluşturuluyor.
public class JwtTokenManager {

    @Value("${userserviceconfig.secrets.secret-key}")
    String secretKey; //Random Password Generator sitesinden bir şifre alıyoruz.
    @Value("${userserviceconfig.secrets.issuer}")
    String issuer;
    Long expireTime = 1000L*60*15; //15dk lık bir süre boyunca token'ımız aktif olacak.

    //Claim objesi içine yazacağımız bilgiler herkes tarafından görülebileceğinden email-password gibi bilgiler olmamalıdır.
    public Optional<String> createToken(Long id){ //1. Token generate edeceğiz.(üret)
        try {
            return Optional.of(JWT.create().withAudience()
                    .withClaim("id", id)
                    .withClaim("service", "AuthMicroService")
                    .withClaim("ders", "Java JWT")
                    .withClaim("grup", "Java12") //claimler kendi kafamıza göre verdiklerimiz
                    .withIssuer(issuer) //jwt token oluşturan
                    .withIssuedAt(new Date(System.currentTimeMillis())) //jwt token oluşturma zamanı
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireTime)) //expireTime ile oluşturulan token'ın ne kadar süre aktif olacağını belirlemiş oluyoruz.
                    .sign(Algorithm.HMAC512(secretKey)));
        } catch (Exception e){
            return Optional.empty();
        }
    }

    public Boolean verifyToken(String token){ //2. Token verify edeceğiz.(doğrula)
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null)
                return false;
        } catch(Exception e){
            return false;
        }
        return true;
    }

    public Optional<Long> decodeToken(String token){ //3. Token decode edeceğiz. (bilgi çıkarımı yap)
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null)
                return Optional.empty();
            Long id = decodedJWT.getClaim("id").asLong();
            String service = decodedJWT.getClaim("service").asString();
            System.out.println("Tokenin oluşturulduğu service: "+service);
                return Optional.of(id);
        } catch(Exception e){
            return Optional.empty();
        }
    }

}

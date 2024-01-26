package com.hakan.utility;

/**
 * JWT(JSON Web Tokens): Web tabanlı sistemlerde kullanılan bir kimlik doğrulama ve yetkilendirme yöntemidir.
 * JWT JSON objesi halinde şifreli veya imzalı bilgi aktarılmasını sağlayan bir token türüdür.
 * Bu token'lar, bilgileri dijital olarak imzalanmış bir şekilde içeren JSON formatındaki veri parçalarıdır.
 * JWT'ler genellikle kullanıcı kimliğini doğrulamak, oturum yönetimi ve bilgi alışverişi gibi alanlarda kullanılır.
 *
 * JWT'ler üç ana bölümden oluşur: Header, Payload ve Signature.
 * Header, token'ın tipini ve kullandığı algoritmayı belirtir.
 * Payload, token'ın içeriğini, kullanıcı bilgilerini veya yetkilendirme verilerini barındırır.
 * Signature ise token'ın doğruluğunu kontrol etmek için kullanılan bir imzadır.
 *
 * JWT her api içinde oluşturduğumuz endpointlere ulaşmak için kullandığımız bir anahtar diyebiliriz.
 * Tokenların imzalı olmaları ve verilerin şifrelenmiş olması, güvenli bir iletişim sağlamalarını sağlar.
 *
 * Secret Key (Gizli Anahtar): SecretKey, JWT'leri oluştururken ve doğrularken kullanılan gizli bir değerdir.
 * Bu anahtar, JWT'nin imzalanması ve doğrulanması için kullanılan bir şifreleme algoritması ile birlikte kullanılır.
 * Token oluşturulurken bu anahtar kullanılarak imza oluşturulur ve doğrulama işleminde bu imza kontrol edilir.
 * Bu anahtar, sadece yetkili sistemler ve kullanıcılar tarafından bilinmeli ve güvende tutulmalıdır.
 * Yanlış ellerde bulunması güvenlik açığına yol açabilir.
 *
 * Issuer (Veren): Issuer, JWT'nin oluşturulduğu veya verildiği kaynağı temsil eder.
 * JWT spesifikasyonu, bu alanın bir URL veya benzersiz bir tanımlayıcı olabileceğini belirtir.
 * Issuer, JWT'yi doğrulamak isteyen sistemlerin, JWT'yi veren veya oluşturanın kim olduğunu doğrulamasına yardımcı olur.
 * Bu, güvenlik kontrollerinde ve kimlik doğrulama süreçlerinde kullanılır.
 *
 * SecretKey, token'ların güvenliği için kritik bir öneme sahiptir çünkü token'ların doğrulanması ve güvenli bir şekilde iletilmesi için kullanılır.
 * Issuer ise, token'ın kaynağını ve güvenilirliğini doğrulamak için kullanılan bir bilgidir.
 *
 * SecretKey ve Issuer gibi başkalarının görmesini ulaşmasını istemeyeceğimiz verileri sınıfımızda ya da yml dosyasında dahi tutmak sakıncalıdır.
 * Bundan dolayı bu bilgileri gizlememiz gereklidir. Bu bilgileri gizlemek için Environment Variable(Ortam Değişkenleri) kullanılabilir.
 * Denetim masasından sistem ortam değişkenleri düzenle kısmından ortam değişkenlerini seçerek yeni diyip key-value şeklinde ekleme yapıyoruz.
 * JAVA12_ISSUER -> Java12 ve JAVA12_SECRETKEY -> r56EWAKpd665uryNgcByz0kTDzUgfynftIoa4FQgT9FwtGVi8C gibi ekleyebiliriz.
 * Eklediğimiz environment variable kullanımında yml dosyamıza artık sadece key( ${key} şeklide ) kısımlarını yazıyoruz ve value kısımlarını gizlemiş oluyoruz.
 * JwtTokenManager içinde de @Value("${authserviceconfig.secrets.secret-key}") şeklinde ikisine de Value işaretleyerek yml dosyamızdaki yolu veriyoruz.
 * Bu işlemi yapınca sistem hata veriyor programı kapatıp açınca kendine geliyor.
 *
 * 2.YOL: Projeye sağ tıklayıp More Run/Debug->Modify Run Configuration diyoruz ve Environment Variables kısmına Key-Value olarakta ekleyebiliyoruz.
 * Proje içine eklediğimizde Denetim Masasından ulaştığımız Environment Variable içine ekleme yapmıyor.
 * Environment Variable çağırdığımız zaman oradan getiriyormuş gibi getiriyor. Kullanıcı değil proje bazlı ortam değişkeni gibi tutuyor.
 * Proje üzerinden yaptığımız zaman sistem okuyamadı ama Module içindeki Application üzerinden modify edince çalıştı.
 *
 * 3.YOL: Projeye environmentvar.bat dosyası açıp içine setx ler ile yine key value olarak ekliyoruz.
 * Terminal kısmından .\environmentvar.bat enter diyerek Environment Variable olarak denetim masası ortam değişkenlerine ekliyor.
 */

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

    @Value("${authserviceconfig.secrets.secret-key}") //yml içine yazdığımız keyleri burada @Value etiketiyle yolunu vererek Environment Variable içinden almamızı sağlıyor.
    String secretKey; //Random Password Generator sitesinden bir şifre alıyoruz.
    @Value("${authserviceconfig.secrets.issuer}") //yml içine yazdığımız keyleri burada @Value etiketiyle yolunu vererek Environment Variable içinden almamızı sağlıyor.
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

            //createToken metodunda withClaim içinde verdiğimiz name-value get metoduyla name kısmını yazınca geriye value kısmını dönüyor.
            //Geriye claim olarak dönüyor ama biz Long olduğunu bildiğimiz için asLong ile alabiliyoruz.
            Long id = decodedJWT.getClaim("id").asLong();

            String service = decodedJWT.getClaim("service").asString(); //Yukarıdaki gibi aynı şekilde service olanı da alabiliyoruz.
            System.out.println("Tokenin oluşturulduğu service: "+service);
                return Optional.of(id);
        } catch(Exception e){
            return Optional.empty();
        }
    }

}

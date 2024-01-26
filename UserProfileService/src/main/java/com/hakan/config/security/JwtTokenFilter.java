package com.hakan.config.security;

import com.hakan.exception.ErrorType;
import com.hakan.exception.UserProfileServiceException;
import com.hakan.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter { //Her bir istekte devreye girecek bir filter olan OncePerRequestFilter sınıfından miras alıyoruz. Porta her istek atıldığında oluşturduğumuz filtre devreye girecek.

    //JwtTokenManager ve JwtUserDetails @Component ve @Service olarak işaretlendiği için @Autowired ile field injection yapabiliyoruz ya da hep kullandığımız gibi (dependency injection) constructor içine ekletecektik.
    @Autowired
    JwtTokenManager jwtTokenManager;

    @Autowired
    JwtUserDetails jwtUserDetails;

    @Override //OncePerRequestFilter sınıfından miras alınca bu metodu bize implement ettiriyor.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //UsernamePasswordAuthenticationFilter'dan önce JwtTokenFilter devreye giriyor mu görmek için çıktı vererek kontrol ediyoruz.
        //9091'e her istek atıldığında çıktı olarak Console kısmında devreye giriyor yazısını görüyoruz ve bu şekilde JwtTokenFilter çalıştığını gözlemlemiş olduk.
        System.out.println("!!!  JwtTokenFilter devreye giriyor  !!!");

        //Postman içinden istek atarken Authorization kısmında type bölümünde Bearer Token seçerek istek atacağız.
        //Token yazan kısıma elimizdeki token'ı yazarak istek attığımızda girilen token'ı çekmek istiyoruz.
        //Bunun için istek attığımızda gelen Bearer Token içinden Token'ı ayıklayacağız.
        //İlk olarak request üzerinden getHeader içine Authorization yazarak istekte gelen Bearer Token'ı çekiyoruz.
        String bearerToken = request.getHeader("Authorization");

        //Burada bearerToken null değilse ve "Bearer " ile başlıyorsa şartına sokuyoruz. Şartı sağlamıyorsa if koşuluna girmeyecek ve isteği geldiği gibi gönderecek.
        if(Objects.nonNull(bearerToken) && bearerToken.startsWith("Bearer ") ) { //Bearer Token null değilse ve "Bearer " ile başlıyorsa şartına sokuyoruz

            //Gelen Bearer Token'ın başında "Bearer " ifadesi yer aldığı için substring ile Token'ı içinden ayıklıyoruz.
            String token = bearerToken.substring(7);

            //JwtTokenManager üzerinden decodeToken metoduyla gelen tokendan authid'yi çekiyoruz.
            Optional<Long> authid = jwtTokenManager.decodeToken(token);

            if (authid.isEmpty()) //Geriye Optional döndüğü için isEmpty koşuluna sokuyoruz. Eğer authid boşsa token yanlıştır ve hata fırlatıyoruz.
                throw new UserProfileServiceException(ErrorType.INVALID_TOKEN);

            //JwtUserDetails sınıfında loadUserByAuthid metoduyla authid vererek geriye UserDetails dönen bir metot yazmıştık. Buradaki aldığımız authid'yi vererek geriye bir UserDetails dönmesini sağlıyoruz.
            UserDetails userDetails = jwtUserDetails.loadUserByAuthid(authid.get());

            //SecurityConfig sınıfında JwtTokenFilter'dan sonra UsernamePasswordAuthenticationFilter'ımızı eklemiştik. Şimdi o filtreden geçecek bir token oluşturacağız.
            //UsernamePasswordAuthenticationFilter'dan geçebilecek token ise UsernamePasswordAuthenticationToken olduğu için onun nesnesini oluşturacağız.
            //Bu nesneyi oluştururken parametre olarak UserDetails veriyoruz. Credentials olmadığı için null veriyoruz ve yetkinlikler kısmına da UserDetails içindeki authorities'i veriyoruz.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            //SecurityContextHolder üzerinden getContext ile authentication'a bu oluşturduğumuz authenticationToken'ı set ediyoruz yani token'ı springe aktarıyoruz.
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(request,response); //filterChain ile doFilter metodu üzerinden request'i al response'u dön diyoruz.

    }

}

package com.hakan.config.security;

import com.hakan.exception.ErrorType;
import com.hakan.exception.UserProfileServiceException;
import com.hakan.repository.entity.UserProfile;
import com.hakan.service.UserProfileService;
import com.hakan.service.YetkiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserDetails implements UserDetailsService { //UserDetailsService interfaceten implement alıyoruz.

    private final UserProfileService userProfileService;

    private final YetkiService yetkiService;

    @Override //UserDetailsService interfaceten implement ettiğimiz için bu metodu da implement ettiriyor.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    //Yukarıdaki metodu override ettirdi ama biz token üzerinden işlem yapıyoruz ve onun içinden decodeToken() ile authid çekebiliyoruz.
    //Bundan dolayı üstteki metodun authid versiyonunu yazıyoruz ve authid kullanarak bir UserDetails nesnesi oluşturacağız
    public UserDetails loadUserByAuthid(Long authid) {

        //UserProfileService içine eklediğimiz metot ile verdiğimiz authid ile eşleşen UserProfile'ı dönmesini sağlayan bir findByAuthid metodu yazıyoruz.
        Optional<UserProfile> userProfile = userProfileService.findByAuthid(authid);

        if (userProfile.isEmpty()) //Geriye Optional döndüğü için boşsa hata fırlatıyoruz.
            throw new UserProfileServiceException(ErrorType.USER_NOT_FOUND);

        List<GrantedAuthority> authorities = new ArrayList<>(); //İçerisinde GrantedAuthority tutan bir yetkinlik nesnesi oluşturuyoruz.

        //Yukarıda authorities'leri liste olarak tuttuğumuz için add ile eklediğimiz her yetkinlik her kullanıcı için tanımlanıyor.
        //Aşağıda girdiğimiz üç yetkinlikte bütün kullanıcılara tanımlanmış oldu. Onun yerine kendimiz kullanıcıya direkt atama yapacağız.
        /*
            authorities.add(new SimpleGrantedAuthority("Admin"));
            authorities.add(new SimpleGrantedAuthority("VIP"));
            authorities.add(new SimpleGrantedAuthority("User"));
         */

        yetkiService.findAllByUserprofileid(userProfile.get().getId()).forEach(
                yetki->{
                    authorities.add(new SimpleGrantedAuthority(yetki.getYetki()));
                }
        );

        //UserDetails oluştururken springframework.security.core.userdetails içindeki User ile builder kullanarak gerekli parametreleri girip bir UserDetails dönüyoruz.
        //UserDetails oluşturmak için username ve password istiyor. Oluşturduğumuz UserProfile nesnesi içinden get metoduyla username bilgisini alabiliyoruz.
        //Sistemi kurarken UserProfile tarafında güvenlik açığı olabileceği için password tutmadık. Bundan dolayı oluşturduğumuz nesne içinden çekemiyoruz.
        //UserDetails içinde UsernamePasswordAuthenticationFilter filtresinden geçmek için bir password gerektiğinden içine bir şey yazmadan boş geçiyoruz.
        return User.builder()
                .username(userProfile.get().getUsername()) //UserProfile içinden username'i çekiyoruz
                .password("")
                .accountExpired(false) //Kullanıcının süresi doldu mu ksımını false yapıyoruz. Ekstradan kendimiz verdik.
                .accountLocked(false) //Kullanıcının hesabı kilitli mi ksımını false yapıyoruz. Ekstradan kendimiz verdik.
                .authorities(authorities) //Yetkinlikler de istediği için authorities içerisine yukarıda liste olarak tuttuğumuz yetkinlikleri vereceğiz.
                .build();

    }

}

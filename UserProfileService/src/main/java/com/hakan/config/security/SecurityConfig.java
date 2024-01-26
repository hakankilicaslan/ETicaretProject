package com.hakan.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Bir konfigürasyon dosyası olduğunu belirtmek için işaretliyoruz.
@EnableMethodSecurity //Metot üzerinde bir yetkilendirme yapmak istiyorsak bu şekilde işaretlemeliyiz.
public class SecurityConfig {

    //SecurityConfig sınıfı Service, Controller gibi normal sınıf olmadığı için injekte ederek alamıyoruz yani Configuration sınıfı olduğu için Bean olarak alabiliyoruz.
    @Bean
    public JwtTokenFilter jwtTokenFilter() { //Geriye yeni bir JwtTokenFilter nesnesi oluşturan bir metot yazdık.
        return new JwtTokenFilter();
    }

    //Burada Spring Security filtresi yerine artık kendi filtremizi devreye sokacağız. Bu şekilde bizi yönledirdiği login sayfasını yani default filter'ı devreden kaldırmış olduk.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //Bu kısımda gelen bütün istekleri doğrula demiş olduk ve default filter olan login sayfasına yönlendirdik yani Spring Security filtresini biz elimizle yazmış olduk.
        /*
            httpSecurity.authorizeRequests().anyRequest().authenticated(); //Burada bütün yetkilendirmelerde bütün isteklerde doğrulama istemiş oluyoruz.
            httpSecurity.formLogin(); //Burada da doğralanamadığı durumda formLogin ile login sayfasına yönlendirmiş oluyoruz.
            return httpSecurity.build();
         */

        //Bu kısımda ise yönlendirilen login sayfasını kendimiz tasarlamak için mylogin dosyası oluşturup orada tasarladığımız yere yönlendirme yapacağız.
        //Bunun için resources kısmına public isminde bir Directory açıyoruz ve içine mylogin.html dosyası oluşturup onun içinde login sayfamızı tasarlayabiliriz.
        //mylogin dosyası üzerinden yönlendirme yapacağız ama doğrulanmadığında mylogin sayfasına yönlendir diyoruz ama ona izin vermediğimiz için sonsuz bir döngüye giriyor.
        //Bunu çözmek için antMatchers ekleyerek mylogin dosyamıza gelen isteklere izin ver demiş olduk. Onun dışında gelen isteklerin authenticated durumu denetlenecek.
        //9091'e istek atıldığında artık bizim tasarladığımız login sayfasına yönlendirme yapılacak. Bu şekilde default login yerine kendi tasarladığımız login sayfasına yönlendirme yaptık.
        /*
            httpSecurity.authorizeRequests()
                        .antMatchers("/mylogin.html").permitAll()
                        .anyRequest().authenticated();
            httpSecurity.formLogin().loginPage("/mylogin.html");
            return httpSecurity.build();
         */

        //Şimdi de login sayfasına yönlendirmek yerine hangi uzantılara istek atmaya izin vereksek onları ekliyoruz ve hangi uzantıya hangi role sahip kullanıcı istek atabilecekse onu belirleyeceğiz.
        //csrf saldırılara karşı güvenlik sağlayan bir mekanizmadır ve gelen isteklerin güvenli olup olmadığını kontrol eder. Biz web sayfası gönderemiyoruz sadece veri gönderiyoruz.
        //Bundan dolayı csrf disable edilmediğinde hata alabiliriz. Bizim gönderdiğimiz isteklerin buraya takılmaması için disable ile csrf'i kapatıyoruz.
        //İlk antMatchers ile verdiğimiz uzantılara gelen isteklere permitAll ile izin vermiş oluyoruz. api/v1/yetki/** ve api/v1/user/findall diyerek yetki ile başlayan bütün isteklere ve findall'a izin ver demiş olduk.
        //"/swagger-ui/**" ve "/v3/api-docs/**" ile de swagger'a erişimi açmış olduk ama sadece bu ikisine izin verirsek swagger'a ulaşabiliyoruz ama endpointlere yine ulaşamıyoruz.
        //İkinci antMatchers ile viparea kısmına hasAnyAuthority içinde verdiğimiz role'e sahip kullanıcılar giriş yapabilsin yani sadece vip ve admin olanların giriş yetkisi olmasını sağladık.
        //Üst satırda yetki den sonra her şeye erişilebilsin dediğimiz için ve o satır üstte olduğu için alttakini ezdi ve o geçerli oldu. Bundan dolayı sadece VIP ve Admin olanlar değil herkes istek atabildi.
        httpSecurity.csrf().disable()
                    .authorizeRequests() //authorizeRequests ile gelen istekleri yetkilendir demiş oluyoruz.
                    .antMatchers("/swagger-ui/**","/v3/api-docs/**","/api/v1/yetki/**","/api/v1/user/findall").permitAll()
                    .antMatchers("(/api/v1/yetki/viparea").hasAnyAuthority("VIP","Admin")
                    .anyRequest().authenticated(); //antMatchers dışındaki bütün istekleri izin vermdiklerimizi de doğrula demiş oluyoruz.

        //Spring Security içerisinde UsernamePasswordAuthenticationFilter bulunuyor ve gelen istekler bu filtreden geçiyor ama biz token üzerinden gelen istekleri filtre etmek ve doğruluğunu sağlamaya çalışıyoruz.
        //Bundan dolayı da herhangi bir istek geldiğinde önce bizim oluşturduğumuz jwtTokenFilter devreye gimesini sağlıyoruz. JwtTokenFilter ile biz kendi custom filtremizi yazmış olduk.
        //Bu token üzerinden bir UserDetails nesnesi oluşturacağız ve UsernamePasswordAuthenticationFilter'dan geçecek bir UsernamePasswordAuthenticationToken oluşturmamız gerekiyor.
        //Yani bizim yapacağımız şey JwtTokenFilter içinden username ve password gibi bilgilerin çıkarımını yapıp UsernamePasswordAuthenticationFilter filtresinden geçiş yapacağız.
        httpSecurity.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

}

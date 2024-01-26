package com.hakan.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration //Bir configürasyon dosyası olduğunu belirtmek için işaretliyoruz.
@EnableCaching //Bu işaretlemeyle springe cacheleme mekanizmalarını aç demiş aktifleştrimiş oluyoruz.
//@EnableRedisRepositories //Redisi veritabanı gibi kullanmak istersek bununla işaretlemeliyiz.
public class RedisConfig {

    //Host ve port bilgilerimizi yml dosyamıza taşıyıp ordan çekeceğiz. yml doyamız ConfigServer içindeki user-micro-service olana ekleyeceğiz.
    @Value("${redisconfig.host}") //Çekeceğimiz değişkeni @Value ile işaretliyoruz ve içine yml dosyamızı gösteren yolu yazıyoruz.
    private String redisHost;
    @Value("${redisconfig.port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() { //Redisle bağlantı kurması için bu metodu yazıyoruz.
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
    }

}

package com.hakan.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SaveAuthModel implements Serializable { //Bütün modeller serileştirilmelidir. Ayrıca paket isi dahil bu sınıfın aynısı iletilen serviste de yani burada UserServicete'de deserilize edebilmesi için olmalıdır.
    //Önceki RabbitMQ örneklerinde MessageConverter üzerinden Jackson2JsonMessageConverter kullanmıştık. Onu kullanmak yerine burada serilize etme işlemini kullanacağız.
    Long authid;
    String username;
    String email;
}

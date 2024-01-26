package com.hakan.rabbitmq.producer;

import com.hakan.rabbitmq.model.SaveAuthModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreateUserProducer {

    private final RabbitTemplate rabbitTemplate; //Converter kullanmadığımız için RabbitTemplate direkt olarak getiriyoruz ve enjekte ediyoruz.

    public CreateUserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndSend(SaveAuthModel model){
        //RabbitTemplate üzerinden çağırdığımız convertAndSend metodumuza exchange ismini, routingKey ve modeli parametre olarak veriyoruz.
        rabbitTemplate.convertAndSend("direct-exchange-auth","save-binding-key",model);
    }
}

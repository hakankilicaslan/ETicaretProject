package com.hakan.rabbitmq.consumer;

import com.hakan.rabbitmq.model.SaveAuthModel;
import com.hakan.repository.entity.UserProfile;
import com.hakan.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserConsumer { //CreateUserProducer sınıfından gelen mesajı alabilmek için kuyruğu dinleyecek bir consumer sınıfı oluşturuyoruz.

    private final UserProfileService userProfileService; //UserProfileService enjekte ediyoruz.

    //AuthMicroService tarafında register kısmında kullanıcı oraya kaydedilirken UserProfileService tarafına da kaydedilsin diye bir CreateUserProducer oluşturup direct exchange ile SaveAuthModel olarak mesajımızı queue-auth ismindeki kuyruğa göndermiştik.
    //Şimdi o gönderdiğimiz mesajı yani SaveAuthModel'i dinleyen bir metot oluşturup gelen SaveAuthModel'i bir UserProfile'e çevirip bu tarafa da kaydedeceğiz.

    @RabbitListener(queues = "queue-auth")  //RabbitListener olarak işaretleyip dinleyeceği kuyruğun ismini yazıyoruz.
    public void createUserFromQueue(SaveAuthModel model){
        userProfileService.save(UserProfile.builder()
                .authid(model.getAuthid())
                .username(model.getUsername())
                .email(model.getEmail())
                .build()); //UserProfileService üzerinden save metodunu çağırıyoruz ve gönderilen SaveAuthModel'i builder ile UserProfile'e çevirip kaydetmiş oluyoruz.
    }

    //UserProfileService tarafına da kaydetmeye çalıştığımızda Serialization hatası aldık. Bunun önüne geçmek için SPRING_AMQP_DESERIALIZATION_TRUST_ALL=true diyerek deserilize işlemlerine güven demiş olduk.
    //Bunu gerçekleştirmek için UserProfileServiceApplication üzerine sağ tıklayıp Environment Variable kısmına SPRING_AMQP_DESERIALIZATION_TRUST_ALL=true ekledik ve düzeldi.
    //Zipkin tarafında da hata almamak için ayrıca onun içinde oluşturduğumuz container'ı ayağa kaldırdık.
}

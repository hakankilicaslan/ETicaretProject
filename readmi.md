# RabbitMQ Docker Kurulumu:
docker run -d --hostname my-rabbit --name some-rabbit -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=user -m=128m  rabbitmq:3-management


# RabbitMQ kullanımında deserilize ederken hata alıyoruz önüne geçmek için deserilize edecek mikroservisin Enviroment Variable kısmına aşağıdaki yazı eklenecek.
SPRING_AMQP_DESERIALIZATION_TRUST_ALL=true
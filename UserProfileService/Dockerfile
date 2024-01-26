#Operating System ve JVM
FROM amazoncorretto:19
#JAR Dosyası
COPY build/libs/UserProfileService-v0.1.jar app.jar
#ÇİFT TIKLAMA: java -jar AuthMicroService-v0.1.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#Dockerfile dosyası ile image oluşturmak için gerekli kod:
#docker build -t hakankilicaslan/eticaretauthmicroservice:001 .
#192.168.0.14
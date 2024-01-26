package com.hakan.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Document //Postgre için Entity olarak işaretliyorduk ama mongoDB için Document olarak işaretleyeceğiz
public class UserProfile extends BaseEntity{
    @MongoId //String olarak tanımladığımız id ise MongoId olarak işaretliyoruz.
    String id; //document nosql databaselerde id uuid tarafından oluşturulur. O da string tipiyle eşleştiği için String veriyoruz.
    Long authid; //Auth tarafıyla eşleşmesi için bir authid tutuyoruz.
    String username;
    String email;
    String photo;
    String phone;
    String website;
}

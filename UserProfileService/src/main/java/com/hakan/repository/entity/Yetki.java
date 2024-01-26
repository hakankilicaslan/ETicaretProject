package com.hakan.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Document(collection = "yetki") //MongoDB içinde dökümanları yetki isminde tutsun diye colleciton olarak yetki ismini verdik.
public class Yetki extends BaseEntity{ //UserProfile entity içine eklediğimiz bütün anostasyonları ve miras aldığı sınıfı da buraya ekliyoruz.
    @MongoId
    String id;
    String userprofileid;
    String yetki;
}

package com.hakan.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.MappedSuperclass;

@SuperBuilder //BaseEntity sınıfından miras alındığı için onda da @Builder yerine @SuperBuilder anotasyonu kullanmalıyız. Auth sınıfı üzerinden değişkenlere ulaşırken buradakilere de ulaşmamızı sağlıyor.
@MappedSuperclass //Miras alınan sınıf olduğu için ayrıca @MappedSuperclass olarakta işaretlemeliyiz. Auth için tablo oluştururken @MappedSuperclass sayesinde buradaki değişkenleri de o tabloya eklememizi sağlıyor.
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseEntity {
    Long createAt;
    Long updateAt;
    Boolean state;
}

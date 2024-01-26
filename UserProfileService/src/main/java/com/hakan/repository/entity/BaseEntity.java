package com.hakan.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder //BaseEntity sınıfından miras alındığı için onda da @Builder yerine @SuperBuilder anotasyonu kullanmalıyız. Auth sınıfı üzerinden değişkenlere ulaşırken buradakilere de ulaşmamızı sağlıyor.
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseEntity {
    Long createAt;
    Long updateAt;
    Boolean state;
}

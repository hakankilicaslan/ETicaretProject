package com.hakan.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true) //Üst sınıftan data çekerken @Data hata vermesin diye de bu anotasyonu ekledik.
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@SuperBuilder //Auth sınıfı BaseEntityden miras aldığı için @Builder yerine @SuperBuilder anotasyonunu kullanmalıyız.
@Table(name = "tblauth")
public class Auth extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    //Email eşşiz olsun diye aynı email ile başka bir kullanıcının kayıt olamamasın diye unique true kullandık.
    //Controller da kontrol ettirebilirdik ama buraya ekleyince DB içine elle eklerken de bu koşulu sağlasın diye buraya yazdık.
    @Column(unique = true)
    String email;
    String password;
}

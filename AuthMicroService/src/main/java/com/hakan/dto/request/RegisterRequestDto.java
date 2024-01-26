package com.hakan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterRequestDto {
    @NotBlank(message = "Kullanıcı adı boş bırakılamaz...")
    @Size(min = 3,max = 20,message = "Kullanıcı adı 3-20 karakter arası olmalıdır.")
    String username;
    @Email(message = "Email formatına uygun mail giriniz.")
    String email;
    /**
     * @NotNull: Null olamaz.
     * @NotEmpty: Null olamaz + boş kalamaz. en az 1 karakter olmalı. Ama bu karakter boşluk bile olabilir.
     * @NotBlank: Null olamaz. + boş olamaz + sadece boşluk kabul etmez yani çift tırnakta kabul etmiyor.
     *
     * (?=.*[^\w\d\s]): En az bir özel karakter (\w alfa sayısal karakterler, \d rakamlar, \s boşluk karakterleri dışında) içermesi gerektiğini belirtir.
     * (?=.*[!#.,^():;+=<>])
     *
     */
    @NotBlank(message = "Şifre alanı boş bırakılamaz...")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s])(?!=.*\\s).{8,32}$",
            message = "Şifre 8 ile 32 karakter arasında olmalıdır ve 1 rakam, 1 büyük karakter, 1 küçük karakter, 1 özel karakter barındırmalıdır.")
    String password;
    String repassword;
}








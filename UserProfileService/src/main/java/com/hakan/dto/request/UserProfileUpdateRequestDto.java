package com.hakan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserProfileUpdateRequestDto {
    String token; //Token sayesinde kimin güncelleyeceğini görüyoruz.
    String email;
    String photo;
    String phone;
    String website;
}

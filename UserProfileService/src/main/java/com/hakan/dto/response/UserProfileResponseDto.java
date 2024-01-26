package com.hakan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserProfileResponseDto {
    String id;
    String username;
    String email;
    String photo;
    String phone;
    String website;
}

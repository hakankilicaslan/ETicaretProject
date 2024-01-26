package com.hakan.manager;

import static com.hakan.constant.EndPoints.*;
import com.hakan.dto.request.UserProfileSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


//@FeignClient ile bir feign client tanımı yapılır. AuthServiceApplication bulmasını sağlamak için işaretledik.
//name eşsiz olmalı ve url kısmına vereceğimiz adres istek atılacak olan sınıfın adresi(requestmapping) olmalıdır.
@FeignClient(name = "user-profile-manager", url = "http://localhost:9091/api/v1/user", decode404 = true)
public interface IUserProfileManager {

    @PostMapping(SAVE)
    ResponseEntity<Boolean> save(@RequestBody UserProfileSaveRequestDto dto);

}

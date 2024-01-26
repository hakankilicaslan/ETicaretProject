package com.hakan.controller;

import static com.hakan.constant.EndPoints.*;

import com.hakan.dto.request.GetProfileFromTokenRequestDto;
import com.hakan.dto.request.UserProfileSaveRequestDto;
import com.hakan.dto.request.UserProfileUpdateRequestDto;
import com.hakan.dto.response.UserProfileResponseDto;
import com.hakan.repository.entity.UserProfile;
import com.hakan.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ROOT+USER)
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;

    @PostMapping(SAVE)
    public ResponseEntity<Boolean> save(@RequestBody UserProfileSaveRequestDto dto){
        return ResponseEntity.ok(service.saveDto(dto));
    }

    @PostMapping(GETFROMTOKEN)
    public ResponseEntity<UserProfileResponseDto> getProfileFromToken(@RequestBody GetProfileFromTokenRequestDto dto){
        return ResponseEntity.ok(service.getProfileFromToken(dto));
    }

    @PostMapping(UPDATE)
    public ResponseEntity<Boolean> updateProfile(@RequestBody UserProfileUpdateRequestDto dto){
        return ResponseEntity.ok(service.updateProfile(dto));
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }



    @GetMapping("/getuppername")
    public ResponseEntity<String> getUpperName(String name){
        return ResponseEntity.ok(service.getUpper(name));
    }

    @GetMapping("/clearcache")
    public ResponseEntity<Void> clearCache(){ //Geriye bir şey dönmek istemiyorsakvoid kullanmak için ResponseEntity büyük harflerle Void yazabiliriz.
        service.clearCache();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{name}")
    public ResponseEntity<Void> removeName(@PathVariable String name) {
        service.removeName(name);
        return ResponseEntity.ok().build();
    }

}

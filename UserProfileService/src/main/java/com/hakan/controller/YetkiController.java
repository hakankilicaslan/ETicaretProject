package com.hakan.controller;

import com.hakan.repository.entity.Yetki;
import com.hakan.service.YetkiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.hakan.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT+YETKI)
public class YetkiController {

    private final YetkiService service;

    @PostMapping(SAVE)
    public ResponseEntity<Yetki> save(@RequestBody Yetki yetki) {
        return ResponseEntity.ok(service.save(yetki));
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<Yetki>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('Admin')") //Bu şekilde buraya sadece adminlerin girebilmesini sağlıyoruz.
    public String adminarea() {
        return "Burası adminlere özel alandır.";
    }

    @GetMapping("/VIP")
    //@PreAuthorize("hasAnyAuthority('VIP')") //Bu şekilde buraya sadece viplerin girebilmesini sağlıyoruz.
    public String viparea() {
        return "Burası VIPlere özel alandır.";
    }

}

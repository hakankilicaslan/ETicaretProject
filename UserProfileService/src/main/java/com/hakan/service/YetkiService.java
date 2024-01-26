package com.hakan.service;

import com.hakan.repository.YetkiRepository;
import com.hakan.repository.entity.Yetki;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YetkiService {

    private final YetkiRepository repository;

    public Yetki save(Yetki yetki) {
        return repository.save(yetki);
    }

    public List<Yetki> findAll() {
        return repository.findAll();
    }

    public List<Yetki> findAllByUserprofileid(String userprofileid) {
        return repository.findAllByUserprofileid(userprofileid);
    }

}

package com.hakan.repository;

import com.hakan.repository.entity.Yetki;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface YetkiRepository extends MongoRepository<Yetki,String> {
    List<Yetki> findAllByUserprofileid(String userprofileid); //UserProfileId ile geriye kullanıcının bütün yetkilerini bir liste olarak dönecek bir metot yazdık.
}

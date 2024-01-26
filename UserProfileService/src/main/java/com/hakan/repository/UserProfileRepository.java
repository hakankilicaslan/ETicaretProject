package com.hakan.repository;

import com.hakan.repository.entity.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile,String> {
//Mongo da çalıştığımız için artık JpaRepositoryden değil MongoRepository extends edeceğiz.
//MongoRepository içinde de JpaRepositoryde olan metotlar yer alıyor.
    Optional<UserProfile> findOptionalByAuthid(Long id); //Authid kullanarak eşleşen UserProfile'ı bulmaya çalışıyoruz.
}

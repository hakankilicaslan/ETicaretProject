package com.hakan.repository;

import com.hakan.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {

    Boolean existsByEmail(String email); //Email sistemde kayıtlı olup olmadığını kontrol ediyoruz.
    Optional<Auth> findOptionalByEmailAndPassword(String email, String password);//Email ve şifre bilgisi kayıtlı ise o bilgiyi döndür.
}

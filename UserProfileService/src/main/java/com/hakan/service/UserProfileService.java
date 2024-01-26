package com.hakan.service;

import com.hakan.dto.request.GetProfileFromTokenRequestDto;
import com.hakan.dto.request.UserProfileSaveRequestDto;
import com.hakan.dto.request.UserProfileUpdateRequestDto;
import com.hakan.dto.response.UserProfileResponseDto;
import com.hakan.exception.ErrorType;
import com.hakan.exception.UserProfileServiceException;
import com.hakan.mapper.IUserProfileMapper;
import com.hakan.repository.UserProfileRepository;
import com.hakan.repository.entity.UserProfile;
import com.hakan.utility.JwtTokenManager;
import com.hakan.utility.ServiceManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    //Constructor Injection
    private final UserProfileRepository repository;
    private final JwtTokenManager jwtTokenManager;


    public UserProfileService(UserProfileRepository repository, JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository=repository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Boolean saveDto(UserProfileSaveRequestDto dto) {
        save(IUserProfileMapper.INSTANCE.dtoToUserProfile(dto));
        return true;
    }

    //Kullanıcı token bilgisini gönderecek ve jwtTokenManager ile token bilgisini doğrulayıp içinden authid bilgisini alacağız.
    public UserProfileResponseDto getProfileFromToken(GetProfileFromTokenRequestDto dto) {
        Optional<Long> authid = jwtTokenManager.decodeToken(dto.getToken());
        if(authid.isEmpty())
            throw new UserProfileServiceException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthid(authid.get());
        if (userProfile.isEmpty())
            throw new UserProfileServiceException(ErrorType.USER_NOT_FOUND);

        return IUserProfileMapper.INSTANCE.userProfileToResponseDto(userProfile.get());
    }

    public Boolean updateProfile(UserProfileUpdateRequestDto dto) {
        Optional<Long> authid = jwtTokenManager.decodeToken(dto.getToken());
        if(authid.isEmpty())
            throw new UserProfileServiceException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthid(authid.get());
        if (userProfile.isEmpty())
            throw new UserProfileServiceException(ErrorType.USER_NOT_FOUND);

        UserProfile updatedProfile = userProfile.get();
        updatedProfile.setEmail(dto.getEmail());
        updatedProfile.setPhoto(dto.getPhoto());
        updatedProfile.setPhone(dto.getPhone());
        updatedProfile.setWebsite(dto.getWebsite());

        update(updatedProfile);
        return true;
    }

    //Burada uzun sürecek bir işlem Thread.sleep ile simule ediliyor.
    //Buradaki metot girdiye göre hep aynı sonucu üretecektir.
    //Bundan dolayı bu metodu cachlemek gerekiyor.
    //condition ekleme kısmında da name olarak gönderilenlerden sadece A ile başlayanları cache'le demiş oluyoruz.
    //condition = "#name.startsWith('A')" tam tersi de unless = "#name.startsWith('A')" oluyor.
    //unless kullanımında ise A ile başlamıyorsa cache'le demiş oluyoruz.
    @Cacheable(value = "getUpperName", condition = "#name.startsWith('A')") //Bununla işaretleyerek cache edilebilir olduğunu belirtiyoruz yani cache'e eklenecek metodu belirleyebiliyoruz.
    public String getUpper(String name){ //Metot gönderilen ifadeyi 3sn bekleyip büyük harfe çevirip o şekilde geri dönecek.
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return name.toUpperCase();
    }

    //CacheEvict diyerek temizlenecek metot üstünde hangi value verdiysek onu yazıyoruz.
    //allEntries=true diyerek clearCache metodu çağırıldığında getUpperName altındaki bütün cacheler silinecek.
    @CacheEvict(value = "getUpperName", allEntries = true)
    public void clearCache(){
        System.out.println("Cache temizlendi...");
    }

    @CacheEvict(cacheNames = "getUpperName", key = "#name", beforeInvocation = true) //Parametre olarak verdiğimiz name eşleşmesi için key kısmına da vermemiz gerekiyor.
    public void removeName(String name) { //Burada da tamamını değil sadece name olarak verdiğimiz isimdekini silmesini istiyoruz. getUpperName kısmında eklediğimiz isimi yazınca sadece onu siliyor.
        System.out.println(name+" cacheden silindi.");
    }

    public Optional<UserProfile> findByAuthid(Long authid) { //JwtUserDetails sınıfında authid vererek UserProfile getirmemiz gerektiği için bu metot ile authid verip repository üzerinden eşleşen UserProfile'ı dönüyoruz.
        return repository.findOptionalByAuthid(authid);
    }

}

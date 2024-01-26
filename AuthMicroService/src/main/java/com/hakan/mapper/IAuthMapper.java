package com.hakan.mapper;

import com.hakan.dto.request.RegisterRequestDto;
import com.hakan.dto.request.UserProfileSaveRequestDto;
import com.hakan.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {

    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth registerRequestDtoToAuth(RegisterRequestDto dto);

    //Mapping çoklusu Mappings olarak kullanılıyor.
//    @Mappings({
//            @Mapping(source = "id",target = "authid")
//            @Mapping(source = "id",target = "authid")
//            @Mapping(source = "id",target = "authid")
//    })
    @Mapping(source = "id",target = "authid") //Auth sınıfındaki idyi UserProfileSaveRequestDto sınıfındaki authid'ye eşitlemiş oluyoruz. İsimleri aynı olmadığı için hata almayalım diye yaptık.
    UserProfileSaveRequestDto authToUserProfileSaveRequestDto(Auth auth);
}

package com.hakan.mapper;

import com.hakan.dto.request.UserProfileSaveRequestDto;
import com.hakan.dto.response.UserProfileResponseDto;
import com.hakan.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserProfileMapper {

    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);

    UserProfile dtoToUserProfile(UserProfileSaveRequestDto dto);

    UserProfileResponseDto userProfileToResponseDto(UserProfile userProfile);

}

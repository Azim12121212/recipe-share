package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.UserModel;
import com.mjc.school.service.dto.UserDtoRequest;
import com.mjc.school.service.dto.UserDtoResponse;
import org.mapstruct.Mapping;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface UserMapper {

    UserModel userDtoToModel(UserDtoRequest dtoRequest);

    UserDtoResponse userModelToDto(UserModel model);

    List<UserDtoResponse> userModelListToDtoList(List<UserModel> modelList);

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    UserModel userIdToModel(Long userId);
}
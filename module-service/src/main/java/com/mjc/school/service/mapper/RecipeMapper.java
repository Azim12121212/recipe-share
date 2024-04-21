package com.mjc.school.service.mapper;

import com.mjc.school.repository.UserRepositoryInterface;
import com.mjc.school.repository.model.RecipeModel;
import com.mjc.school.service.dto.RecipeDtoRequest;
import com.mjc.school.service.dto.RecipeDtoResponse;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class RecipeMapper {
    @Autowired
    protected UserRepositoryInterface userRepository;

    @Mapping(target = "userModel", expression = "java(userRepository.readById(dtoRequest.getUserId()).get())")
    @Mapping(target = "createDate", ignore = true)
    public abstract RecipeModel recipeDtoToModel(RecipeDtoRequest dtoRequest);

    @Mapping(target = "userDto", source = "userModel")
    public abstract RecipeDtoResponse recipeModelToDto(RecipeModel model);

    public abstract List<RecipeDtoResponse> recipeModelListToDtoList(List<RecipeModel> modelList);
}
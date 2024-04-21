package com.mjc.school.service;

import com.mjc.school.service.dto.RecipeDtoRequest;
import com.mjc.school.service.dto.RecipeDtoResponse;
import com.mjc.school.service.dto.UserDtoRequest;

public interface RecipeServiceInterface extends BaseService<RecipeDtoRequest, RecipeDtoResponse, Long> {

    RecipeDtoResponse likeRecipe(Long recipeId, UserDtoRequest userDtoRequest);
}
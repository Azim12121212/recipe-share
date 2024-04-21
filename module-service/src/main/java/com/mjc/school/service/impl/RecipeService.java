package com.mjc.school.service.impl;

import com.mjc.school.repository.RecipeRepositoryInterface;
import com.mjc.school.repository.UserRepositoryInterface;
import com.mjc.school.repository.model.RecipeModel;
import com.mjc.school.service.RecipeServiceInterface;
import com.mjc.school.service.dto.RecipeDtoRequest;
import com.mjc.school.service.dto.RecipeDtoResponse;
import com.mjc.school.service.dto.UserDtoRequest;
import com.mjc.school.service.errorsexceptions.NotFoundException;
import com.mjc.school.service.mapper.RecipeMapper;
import com.mjc.school.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService implements RecipeServiceInterface {
    @Autowired
    private RecipeRepositoryInterface recipeRepository;
    @Autowired
    private UserRepositoryInterface userRepository;
    @Autowired
    private RecipeMapper mapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<RecipeDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        return mapper.recipeModelListToDtoList(recipeRepository.readAll(pageNum, pageSize, sortBy));
    }

    @Override
    public RecipeDtoResponse readById(Long id) {
        return recipeRepository.readById(id)
                .map(mapper::recipeModelToDto)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
    }

    @Transactional
    @Override
    public RecipeDtoResponse create(RecipeDtoRequest createRequest) {
        RecipeModel recipeModel = recipeRepository.create(mapper.recipeDtoToModel(createRequest));
        return mapper.recipeModelToDto(recipeModel);
    }

    @Transactional
    @Override
    public RecipeDtoResponse update(RecipeDtoRequest updateRequest) {
        Optional<RecipeModel> currentRecipeOpt = recipeRepository.readById(updateRequest.getId());
        if (currentRecipeOpt.isPresent()) {
            if (updateRequest.getTitle()!=null) {
                currentRecipeOpt.get().setTitle(updateRequest.getTitle());
            }
            if (updateRequest.getUserId()!=null) {
                if (userRepository.existById(updateRequest.getUserId())) {
                    currentRecipeOpt.get().setUserModel(userMapper.userIdToModel(updateRequest.getUserId()));
                } else {
                    throw new NotFoundException("User not found");
                }
            }
            if (updateRequest.getImage()!=null) {
                currentRecipeOpt.get().setImage(updateRequest.getImage());
            }
            if (updateRequest.getDescription()!=null) {
                currentRecipeOpt.get().setDescription(updateRequest.getDescription());
            }

            currentRecipeOpt.get().setVegetarian(updateRequest.isVegetarian());

            return mapper.recipeModelToDto(recipeRepository.update(currentRecipeOpt.get()));
        }
        throw new NotFoundException("Recipe not found");
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (recipeRepository.existById(id)) {
            return recipeRepository.deleteById(id);
        }
        return false;
    }


    @Transactional
    @Override
    public RecipeDtoResponse likeRecipe(Long recipeId, UserDtoRequest userDtoRequest) {
        Optional<RecipeModel> currentRecipeOpt = recipeRepository.readById(recipeId);
        if (currentRecipeOpt.isPresent()) {
            if (userDtoRequest.getId()!=null && userRepository.existById(userDtoRequest.getId())) {
                if (currentRecipeOpt.get().getLikes().contains(userDtoRequest.getId())) {
                    currentRecipeOpt.get().getLikes().remove(userDtoRequest.getId());
                } else {
                    currentRecipeOpt.get().getLikes().add(userDtoRequest.getId());
                }
                recipeRepository.update(currentRecipeOpt.get());
                return mapper.recipeModelToDto(currentRecipeOpt.get());
            } else {
                throw new NotFoundException("User not found");
            }
        }
        throw new NotFoundException("Recipe not found");
    }
}
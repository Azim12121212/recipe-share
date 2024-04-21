package com.mjc.school.controller.impl;

import com.mjc.school.service.RecipeServiceInterface;
import com.mjc.school.service.UserServiceInterface;
import com.mjc.school.service.dto.RecipeDtoRequest;
import com.mjc.school.service.dto.RecipeDtoResponse;
import com.mjc.school.service.dto.UserDtoRequest;
import com.mjc.school.service.dto.UserDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/recipes")
public class RecipeController {
    @Autowired
    private RecipeServiceInterface recipeService;
    @Autowired
    private UserServiceInterface userService;

    @GetMapping
    public List<RecipeDtoResponse> getAllRecipes(
            @RequestParam(required = false, name = "page", defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, name = "size", defaultValue = "100") Integer pageSize,
            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortBy) {
        return recipeService.readAll(pageNum, pageSize, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDtoResponse createRecipe(@RequestBody RecipeDtoRequest dtoRequest,
                                          @RequestHeader("Authorization") String jwt) throws Exception {
        RecipeDtoResponse dtoResponse = null;
        UserDtoResponse userDtoByJwt = userService.findByJwt(jwt);
        if (userDtoByJwt!=null) {
            dtoRequest.setUserId(userDtoByJwt.getId());
            dtoResponse = recipeService.create(dtoRequest);
        }
        return dtoResponse;
    }

    @PutMapping(value = "/{recipeId}")
    public RecipeDtoResponse updateRecipe(@PathVariable Long recipeId,
                                          @RequestBody RecipeDtoRequest dtoRequest) {
        dtoRequest.setId(recipeId);
        RecipeDtoResponse updateDto = recipeService.update(dtoRequest);
        return updateDto;
    }

    @DeleteMapping(value = "/{recipeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteById(recipeId);
        return "Recipe has been deleted successfully";
    }

    @PutMapping(value = "/like/{recipeId}")
    public RecipeDtoResponse likeRecipe(@PathVariable Long recipeId,
                                        @RequestHeader("Authorization") String jwt) throws Exception {
        UserDtoResponse userDtoResp = userService.findByJwt(jwt);

        UserDtoRequest userDtoReq = new UserDtoRequest();
        userDtoReq.setId(userDtoResp.getId());
        userDtoReq.setEmail(userDtoResp.getEmail());
        userDtoReq.setFullName(userDtoResp.getFullName());

        RecipeDtoResponse dtoResponse = recipeService.likeRecipe(recipeId, userDtoReq);
        return dtoResponse;
    }
}
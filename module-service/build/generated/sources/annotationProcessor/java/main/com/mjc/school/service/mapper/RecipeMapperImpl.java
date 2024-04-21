package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.RecipeModel;
import com.mjc.school.service.dto.RecipeDtoRequest;
import com.mjc.school.service.dto.RecipeDtoResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-21T14:59:28+0600",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class RecipeMapperImpl extends RecipeMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public RecipeModel recipeDtoToModel(RecipeDtoRequest dtoRequest) {
        if ( dtoRequest == null ) {
            return null;
        }

        RecipeModel recipeModel = new RecipeModel();

        recipeModel.setId( dtoRequest.getId() );
        recipeModel.setTitle( dtoRequest.getTitle() );
        recipeModel.setImage( dtoRequest.getImage() );
        recipeModel.setDescription( dtoRequest.getDescription() );
        recipeModel.setVegetarian( dtoRequest.isVegetarian() );
        List<Long> list = dtoRequest.getLikes();
        if ( list != null ) {
            recipeModel.setLikes( new ArrayList<Long>( list ) );
        }

        recipeModel.setUserModel( userRepository.readById(dtoRequest.getUserId()).get() );

        return recipeModel;
    }

    @Override
    public RecipeDtoResponse recipeModelToDto(RecipeModel model) {
        if ( model == null ) {
            return null;
        }

        RecipeDtoResponse recipeDtoResponse = new RecipeDtoResponse();

        recipeDtoResponse.setUserDto( userMapper.userModelToDto( model.getUserModel() ) );
        recipeDtoResponse.setId( model.getId() );
        recipeDtoResponse.setTitle( model.getTitle() );
        recipeDtoResponse.setImage( model.getImage() );
        recipeDtoResponse.setDescription( model.getDescription() );
        recipeDtoResponse.setVegetarian( model.isVegetarian() );
        recipeDtoResponse.setCreateDate( model.getCreateDate() );
        List<Long> list = model.getLikes();
        if ( list != null ) {
            recipeDtoResponse.setLikes( new ArrayList<Long>( list ) );
        }

        return recipeDtoResponse;
    }

    @Override
    public List<RecipeDtoResponse> recipeModelListToDtoList(List<RecipeModel> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<RecipeDtoResponse> list = new ArrayList<RecipeDtoResponse>( modelList.size() );
        for ( RecipeModel recipeModel : modelList ) {
            list.add( recipeModelToDto( recipeModel ) );
        }

        return list;
    }
}

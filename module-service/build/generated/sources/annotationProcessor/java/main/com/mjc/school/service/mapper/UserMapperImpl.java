package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.UserModel;
import com.mjc.school.service.dto.UserDtoRequest;
import com.mjc.school.service.dto.UserDtoResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-21T14:59:28+0600",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserModel userDtoToModel(UserDtoRequest dtoRequest) {
        if ( dtoRequest == null ) {
            return null;
        }

        UserModel userModel = new UserModel();

        userModel.setId( dtoRequest.getId() );
        userModel.setEmail( dtoRequest.getEmail() );
        userModel.setPassword( dtoRequest.getPassword() );
        userModel.setFullName( dtoRequest.getFullName() );

        return userModel;
    }

    @Override
    public UserDtoResponse userModelToDto(UserModel model) {
        if ( model == null ) {
            return null;
        }

        UserDtoResponse userDtoResponse = new UserDtoResponse();

        userDtoResponse.setId( model.getId() );
        userDtoResponse.setEmail( model.getEmail() );
        userDtoResponse.setFullName( model.getFullName() );

        return userDtoResponse;
    }

    @Override
    public List<UserDtoResponse> userModelListToDtoList(List<UserModel> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<UserDtoResponse> list = new ArrayList<UserDtoResponse>( modelList.size() );
        for ( UserModel userModel : modelList ) {
            list.add( userModelToDto( userModel ) );
        }

        return list;
    }

    @Override
    public UserModel userIdToModel(Long userId) {
        if ( userId == null ) {
            return null;
        }

        UserModel userModel = new UserModel();

        userModel.setId( userId );

        return userModel;
    }
}

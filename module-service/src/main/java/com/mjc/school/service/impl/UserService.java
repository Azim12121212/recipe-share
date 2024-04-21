package com.mjc.school.service.impl;

import com.mjc.school.repository.UserRepositoryInterface;
import com.mjc.school.repository.model.UserModel;
import com.mjc.school.service.UserServiceInterface;
import com.mjc.school.service.dto.UserDtoRequest;
import com.mjc.school.service.dto.UserDtoResponse;
import com.mjc.school.service.errorsexceptions.NotFoundException;
import com.mjc.school.service.errorsexceptions.ValidatorException;
import com.mjc.school.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepositoryInterface userRepository;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public List<UserDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        return mapper.userModelListToDtoList(userRepository.readAll(pageNum, pageSize, sortBy));
    }

    @Override
    public UserDtoResponse readById(Long id) {
        return userRepository.readById(id)
                .map(mapper::userModelToDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    @Override
    public UserDtoResponse create(UserDtoRequest createRequest) {

        if (findByEmail(createRequest.getEmail())!=null) {
            throw new ValidatorException("User with email " + createRequest.getEmail() + " already exists!");
        }

        UserModel userModel = userRepository.create(mapper.userDtoToModel(createRequest));
        return mapper.userModelToDto(userModel);
    }

    @Transactional
    @Override
    public UserDtoResponse update(UserDtoRequest updateRequest) {
        if (userRepository.existById(updateRequest.getId())) {
            UserModel userModel = userRepository.update(mapper.userDtoToModel(updateRequest));
            return mapper.userModelToDto(userModel);
        }
        throw new NotFoundException("User not found");
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (userRepository.existById(id)) {
            return userRepository.deleteById(id);
        }
        return false;
    }

    @Override
    public UserDtoResponse findByEmail(String email) {
        Optional<UserModel> modelOptional = userRepository.findByEmail(email);
        if (modelOptional.isPresent()) {
            return mapper.userModelToDto(modelOptional.get());
        }
        return null;
    }

    @Override
    public UserDtoResponse findByJwt(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        if (email==null) {
            throw new Exception("Provide valid jwt token");
        }

        UserDtoResponse dtoResponse = mapper.userModelToDto(userRepository.findByEmail(email).get());

        if (dtoResponse==null) {
            throw new Exception("User not found with email " + email);
        }

        return dtoResponse;
    }
}
package com.mjc.school.service;

import com.mjc.school.service.dto.UserDtoRequest;
import com.mjc.school.service.dto.UserDtoResponse;

public interface UserServiceInterface extends BaseService<UserDtoRequest, UserDtoResponse, Long> {

    UserDtoResponse findByEmail(String email);

    UserDtoResponse findByJwt(String jwt) throws Exception;
}
package com.mjc.school.repository;

import com.mjc.school.repository.model.UserModel;

import java.util.Optional;

public interface UserRepositoryInterface extends BaseRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);
}
package com.mjc.school.controller.impl;

import com.mjc.school.service.UserServiceInterface;
import com.mjc.school.service.dto.UserDtoRequest;
import com.mjc.school.service.dto.UserDtoResponse;
import com.mjc.school.service.errorsexceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserServiceInterface userService;

    @GetMapping
    public List<UserDtoResponse> getAllUsers(
            @RequestParam(required = false, name = "page", defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, name = "size", defaultValue = "5") Integer pageSize,
            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortBy) {
        return userService.readAll(pageNum, pageSize, sortBy);
    }

    @GetMapping(value = "/profile")
    public UserDtoResponse findByJwt(@RequestHeader("Authorization") String jwt) throws Exception {

        return userService.findByJwt(jwt);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserDtoResponse createUser(@RequestBody UserDtoRequest userDtoRequest) {
//        UserDtoResponse userDtoResponse = userService.create(userDtoRequest);
//        return userDtoResponse;
//    }

    @PutMapping(value = "/{userId}")
    public UserDtoResponse updateUser(@PathVariable Long userId,
                                      @RequestBody UserDtoRequest dtoRequest) {
        dtoRequest.setId(userId);
        UserDtoResponse updateDto = userService.update(dtoRequest);
        return updateDto;
    }

//    @DeleteMapping(value = "/{userId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public String deleteUser(@PathVariable Long userId) {
//        userService.deleteById(userId);
//        return "User has been deleted successfully";
//    }

    @GetMapping(value = "/email/{email}")
    public UserDtoResponse findByEmail(@PathVariable String email) {
        UserDtoResponse dtoResponse = userService.findByEmail(email);
        if (dtoResponse==null) {
            throw new NotFoundException("User not found with email " + email);
        }
        return dtoResponse;
    }
}
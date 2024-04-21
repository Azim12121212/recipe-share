package com.mjc.school.controller.impl;

import com.mjc.school.service.impl.JwtProvider;
import com.mjc.school.service.UserServiceInterface;
import com.mjc.school.service.dto.AuthDtoResponse;
import com.mjc.school.service.dto.LoginDtoRequest;
import com.mjc.school.service.dto.UserDtoRequest;
import com.mjc.school.service.dto.UserDtoResponse;
import com.mjc.school.service.impl.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signup")
    public AuthDtoResponse createUser(@RequestBody UserDtoRequest userDtoReq) throws Exception {
        String email = userDtoReq.getEmail();
        String password = userDtoReq.getPassword();

        UserDtoResponse userDtoExistEmail = userService.findByEmail(email);

        if (userDtoExistEmail!=null) {
            throw new Exception("Email is already used with another account");
        }

        userDtoReq.setPassword(passwordEncoder.encode(password));
        UserDtoResponse userDtoSaved = userService.create(userDtoReq);

        Authentication auth = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtProvider.generateToken(auth);

        AuthDtoResponse authDtoResponse = new AuthDtoResponse();
        authDtoResponse.setJwt(token);
        authDtoResponse.setMessage("Signup success");

        return authDtoResponse;
    }

    @PostMapping(value = "/signin")
    public AuthDtoResponse signinHandler(@RequestBody LoginDtoRequest loginDtoReq) {
        String username = loginDtoReq.getEmail();
        String password = loginDtoReq.getPassword();

        Authentication auth = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtProvider.generateToken(auth);

        AuthDtoResponse authDtoResponse = new AuthDtoResponse();
        authDtoResponse.setJwt(token);
        authDtoResponse.setMessage("Signin success");

        return authDtoResponse;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

        if (userDetails==null) {
            throw new BadCredentialsException("User not found");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
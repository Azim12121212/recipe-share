package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeDtoResponse {
    private Long id;
    private String title;
    private UserDtoResponse userDto;
    private String image;
    private String description;
    private boolean vegetarian;
    private LocalDateTime createDate;
    private List<Long> likes;
}
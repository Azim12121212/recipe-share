package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeDtoRequest {
    private Long id;
    private String title;
    private Long userId;
    private String image;
    private String description;
    private boolean vegetarian;
    private List<Long> likes;
}
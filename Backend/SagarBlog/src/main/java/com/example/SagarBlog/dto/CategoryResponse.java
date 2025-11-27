package com.example.SagarBlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private String color; // add this

    public CategoryResponse(Long id, String name, String slug, String color) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.color = color;
    }
}


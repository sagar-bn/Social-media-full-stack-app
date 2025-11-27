package com.example.SagarBlog.dto;

import lombok.*;
@Data

@NoArgsConstructor
@Builder
public class TagResponse {
    private Long id;
    private String name;
    private String slug; // add this

    public TagResponse(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }
}



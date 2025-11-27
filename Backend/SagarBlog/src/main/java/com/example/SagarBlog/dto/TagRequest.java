package com.example.SagarBlog.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagRequest {
    private String name;
    private String description; // if needed
    private String slug; // add this field
}

package com.example.SagarBlog.dto;

import lombok.Data;

@Data
public class BlogPostRequest {
    private String title;
    private String content;
    private String featuredImageUrl;
}

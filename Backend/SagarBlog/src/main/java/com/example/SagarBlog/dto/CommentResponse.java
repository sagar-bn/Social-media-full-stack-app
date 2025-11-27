package com.example.SagarBlog.dto;

import lombok.*;



import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private String commenter;
    private LocalDateTime createdAt;


}

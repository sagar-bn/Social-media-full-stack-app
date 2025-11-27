package com.example.SagarBlog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blog_post_tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private BlogPost post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}

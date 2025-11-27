package com.example.SagarBlog.service;

import com.example.SagarBlog.dto.TagRequest;
import com.example.SagarBlog.dto.TagResponse;
import java.util.List;

public interface TagService {
    TagResponse createTag(TagRequest request);
    void deleteTag(Long id);
    List<TagResponse> getAllTags();
}

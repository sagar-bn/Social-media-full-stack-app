package com.example.SagarBlog.service;

import com.example.SagarBlog.dto.TagRequest;
import com.example.SagarBlog.dto.TagResponse;
import com.example.SagarBlog.model.Tag;
import com.example.SagarBlog.repo.TagRepository;
import com.example.SagarBlog.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagResponse createTag(TagRequest request) {
        Tag tag = Tag.builder().name(request.getName()).slug(request.getSlug()).build();
        tagRepository.save(tag);
        return new TagResponse(tag.getId(), tag.getName(), tag.getSlug());
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(t -> new TagResponse(t.getId(), t.getName(), t.getSlug()))
                .collect(Collectors.toList());
    }
}

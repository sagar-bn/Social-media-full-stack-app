package com.example.SagarBlog.service;


import com.example.SagarBlog.dto.CategoryRequest;
import com.example.SagarBlog.dto.CategoryResponse;
import com.example.SagarBlog.model.Category;
import com.example.SagarBlog.repo.CategoryRepository;
import com.example.SagarBlog.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .color(request.getColor())
                .build();
        categoryRepository.save(category);
        return new CategoryResponse(category.getId(), category.getName(), category.getSlug(), category.getColor());
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());
        category.setColor(request.getColor());
        categoryRepository.save(category);
        return new CategoryResponse(category.getId(), category.getName(), category.getSlug(), category.getColor());
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getSlug(), c.getColor()))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return new CategoryResponse(category.getId(), category.getName(), category.getSlug(), category.getColor());
    }
}

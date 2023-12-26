package ru.crazymax.spring.data.jdbc.https.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.crazymax.spring.data.jdbc.https.dtos.CategoryDto;
import ru.crazymax.spring.data.jdbc.https.dtos.CreateOrUpdateCategoryDtoRq;
import ru.crazymax.spring.data.jdbc.https.dtos.SimplestPageDto;
import ru.crazymax.spring.data.jdbc.https.entities.Category;
import ru.crazymax.spring.data.jdbc.https.exceptions.ResourceNotFoundException;
import ru.crazymax.spring.data.jdbc.https.services.CategoriesService;

import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {
    private final CategoriesService categoriesService;

    private static final Function<Category, CategoryDto> MAP_TO_DTO_FUNCTION = c -> new CategoryDto(c.getId(), c.getTitle());

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public SimplestPageDto<CategoryDto> findAll() {
        return new SimplestPageDto<>(categoriesService.findAll().stream().map(MAP_TO_DTO_FUNCTION).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Long id) {
        return categoriesService.findById(id).map(MAP_TO_DTO_FUNCTION).orElseThrow(() -> new ResourceNotFoundException("Категория не найдена"));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoriesService.deleteById(id);
    }

    @PostMapping
    public void createNewCategory(@RequestBody CreateOrUpdateCategoryDtoRq createOrUpdateCategoryDtoRq) {
        categoriesService.createNewCategory(createOrUpdateCategoryDtoRq);
    }

    @PutMapping
    public void updateCategory(@RequestBody CreateOrUpdateCategoryDtoRq createOrUpdateCategoryDtoRq) {
        categoriesService.fullUpdateCategory(createOrUpdateCategoryDtoRq);
    }
}

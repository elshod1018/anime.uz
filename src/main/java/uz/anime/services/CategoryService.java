package uz.anime.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.anime.domains.Category;
import uz.anime.dtos.category.CategoryCreateDTO;
import uz.anime.dtos.category.CategoryUpdateDTO;
import uz.anime.repositories.CategoryRepository;

import static uz.anime.mapper.CategoryMapper.CATEGORY_MAPPER;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;

    public Category create(CategoryCreateDTO dto) {
        Category category = CATEGORY_MAPPER.toEntity(dto);
        return categoryRepository.save(category);
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id '%s' ".formatted(id)));
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @SneakyThrows
    public Category update(CategoryUpdateDTO dto) {
        Category category = findById(dto.getId());
        CATEGORY_MAPPER.updateCategory(dto, category);
        return categoryRepository.save(category);
    }

    public Category delete(Integer id) {
        Category category = findById(id);
        category.setDeleted(true);
        return categoryRepository.save(category);
    }
}

package uz.anime.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import uz.anime.domains.Category;
import uz.anime.dtos.category.CategoryCreateDTO;
import uz.anime.dtos.category.CategoryUpdateDTO;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    Category toEntity(CategoryCreateDTO dto);

    CategoryCreateDTO toCreateDTO(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateCategory(CategoryUpdateDTO dto, @MappingTarget Category category);
}


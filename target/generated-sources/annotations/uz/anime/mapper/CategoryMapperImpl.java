package uz.anime.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.anime.domains.Category;
import uz.anime.dtos.category.CategoryCreateDTO;
import uz.anime.dtos.category.CategoryUpdateDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-31T12:33:35+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Private Build)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toEntity(CategoryCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.childBuilder();

        category.parentId( dto.getParentId() );
        category.name( dto.getName() );
        category.description( dto.getDescription() );
        category.hasChild( dto.isHasChild() );

        return category.build();
    }

    @Override
    public CategoryCreateDTO toCreateDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryCreateDTO categoryCreateDTO = new CategoryCreateDTO();

        categoryCreateDTO.setParentId( category.getParentId() );
        categoryCreateDTO.setName( category.getName() );
        categoryCreateDTO.setDescription( category.getDescription() );
        categoryCreateDTO.setHasChild( category.isHasChild() );

        return categoryCreateDTO;
    }

    @Override
    public void updateCategory(CategoryUpdateDTO dto, Category category) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            category.setId( dto.getId() );
        }
        if ( dto.getParentId() != null ) {
            category.setParentId( dto.getParentId() );
        }
        if ( dto.getName() != null ) {
            category.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            category.setDescription( dto.getDescription() );
        }
        category.setHasChild( dto.isHasChild() );
    }
}

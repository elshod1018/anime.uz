package uz.anime.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import uz.anime.domains.Lists;
import uz.anime.dtos.lists.ListsCreateDTO;
import uz.anime.dtos.lists.ListsUpdateDTO;


@Mapper(componentModel = "spring")
public interface ListsMapper {
    ListsMapper LISTS_MAPPER = Mappers.getMapper(ListsMapper.class);

    Lists toEntity(ListsCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateListsFromDTO(ListsUpdateDTO dto, @MappingTarget Lists lists);
}

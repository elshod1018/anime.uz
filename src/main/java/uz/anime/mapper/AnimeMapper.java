package uz.anime.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.anime.domains.Anime;
import uz.anime.dtos.anime.AnimeCreateDTO;


@Mapper(componentModel = "spring")
public interface AnimeMapper {
    AnimeMapper ANIME_MAPPER = Mappers.getMapper(AnimeMapper.class);

    AnimeCreateDTO toCreateDto(Anime anime);

    Anime toEntity(AnimeCreateDTO dto);

//    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
//    void updateUsersProfileFromDTO(UserProfileUpdateDTO dto, @MappingTarget AuthUser user);
}

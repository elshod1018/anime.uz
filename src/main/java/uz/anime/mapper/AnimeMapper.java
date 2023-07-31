package uz.anime.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import uz.anime.domains.Anime;
import uz.anime.dtos.anime.AnimeCreateDTO;
import uz.anime.dtos.anime.AnimeUpdateDTO;


@Mapper(componentModel = "spring")
public interface AnimeMapper {
    AnimeMapper ANIME_MAPPER = Mappers.getMapper(AnimeMapper.class);

    AnimeCreateDTO toCreateDto(Anime anime);

    Anime toEntity(AnimeCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateAnimeFromDTO(AnimeUpdateDTO dto, @MappingTarget Anime anime);
}

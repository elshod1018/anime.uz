package uz.anime.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.anime.domains.Anime;
import uz.anime.dtos.anime.AnimeCreateDTO;
import uz.anime.dtos.anime.AnimeUpdateDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-31T12:33:35+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Private Build)"
)
@Component
public class AnimeMapperImpl implements AnimeMapper {

    @Override
    public AnimeCreateDTO toCreateDto(Anime anime) {
        if ( anime == null ) {
            return null;
        }

        AnimeCreateDTO.AnimeCreateDTOBuilder animeCreateDTO = AnimeCreateDTO.builder();

        animeCreateDTO.name( anime.getName() );
        animeCreateDTO.description( anime.getDescription() );
        animeCreateDTO.manager( anime.getManager() );
        animeCreateDTO.author( anime.getAuthor() );
        animeCreateDTO.studio( anime.getStudio() );
        animeCreateDTO.license( anime.getLicense() );
        List<Integer> list = anime.getCategories();
        if ( list != null ) {
            animeCreateDTO.categories( new ArrayList<Integer>( list ) );
        }
        animeCreateDTO.publishedDate( anime.getPublishedDate() );

        return animeCreateDTO.build();
    }

    @Override
    public Anime toEntity(AnimeCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Anime.AnimeBuilder anime = Anime.childBuilder();

        anime.name( dto.getName() );
        anime.description( dto.getDescription() );
        anime.manager( dto.getManager() );
        anime.author( dto.getAuthor() );
        anime.studio( dto.getStudio() );
        anime.license( dto.getLicense() );
        List<Integer> list = dto.getCategories();
        if ( list != null ) {
            anime.categories( new ArrayList<Integer>( list ) );
        }
        anime.publishedDate( dto.getPublishedDate() );

        return anime.build();
    }

    @Override
    public void updateAnimeFromDTO(AnimeUpdateDTO dto, Anime anime) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            anime.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            anime.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            anime.setDescription( dto.getDescription() );
        }
        if ( dto.getManager() != null ) {
            anime.setManager( dto.getManager() );
        }
        if ( dto.getAuthor() != null ) {
            anime.setAuthor( dto.getAuthor() );
        }
        if ( dto.getStudio() != null ) {
            anime.setStudio( dto.getStudio() );
        }
        if ( dto.getLicense() != null ) {
            anime.setLicense( dto.getLicense() );
        }
        if ( anime.getCategories() != null ) {
            List<Integer> list = dto.getCategories();
            if ( list != null ) {
                anime.getCategories().clear();
                anime.getCategories().addAll( list );
            }
        }
        else {
            List<Integer> list = dto.getCategories();
            if ( list != null ) {
                anime.setCategories( new ArrayList<Integer>( list ) );
            }
        }
        if ( dto.getPublishedDate() != null ) {
            anime.setPublishedDate( dto.getPublishedDate() );
        }
    }
}

package uz.anime.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.anime.domains.Anime;
import uz.anime.dtos.anime.AnimeCreateDTO;
import uz.anime.repositories.AnimeRepository;

import static uz.anime.mapper.AnimeMapper.ANIME_MAPPER;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public Anime create(AnimeCreateDTO dto) {
        Anime anime = ANIME_MAPPER.toEntity(dto);
        return animeRepository.save(anime);
    }

    public Anime findById(Integer id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anime not found with id '%s' ".formatted(id)));
    }
}

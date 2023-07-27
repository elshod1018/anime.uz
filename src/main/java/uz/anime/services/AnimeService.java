package uz.anime.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.anime.domains.Anime;
import uz.anime.dtos.anime.AnimeCreateDTO;
import uz.anime.enums.FileAim;
import uz.anime.evenet_listeners.events.AnimeSetContentsEvent;
import uz.anime.repositories.AnimeRepository;

import java.util.List;

import static uz.anime.mapper.AnimeMapper.ANIME_MAPPER;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CategoryService categoryService;

    public Anime create(AnimeCreateDTO dto) {
        List<Integer> categories = dto.getCategories();
        categories.forEach(categoryService::findById);
        Anime anime = ANIME_MAPPER.toEntity(dto);
        return animeRepository.save(anime);
    }

    public Anime findById(Integer id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anime not found with id '%s' ".formatted(id)));
    }

    public Page<Anime> findAll(PageRequest pageRequest) {
        return animeRepository.findAll(pageRequest);
    }

    public Anime setContents(Integer animeId, MultipartFile photo, MultipartFile content) {
        Anime anime = findById(animeId);
        applicationEventPublisher.publishEvent(new AnimeSetContentsEvent(anime.getId(), photo, content));
        return anime;
    }

    public Anime update(Anime anime) {
        return animeRepository.save(anime);
    }
}

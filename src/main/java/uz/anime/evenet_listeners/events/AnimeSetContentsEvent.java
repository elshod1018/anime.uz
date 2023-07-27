package uz.anime.evenet_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.anime.enums.FileAim;

@Getter
@RequiredArgsConstructor
public final class AnimeSetContentsEvent {
    private final Integer animeId;
    private final MultipartFile photo;
    private final MultipartFile content;
}

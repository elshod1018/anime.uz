package uz.anime.evenet_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
@Getter
@RequiredArgsConstructor
public final class DocumentCreatedEvent {
    private final MultipartFile file;
    private final Integer documentId;
}

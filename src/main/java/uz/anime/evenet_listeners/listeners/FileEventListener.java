package uz.anime.evenet_listeners.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.anime.domains.Document;
import uz.anime.evenet_listeners.events.DocumentCreatedEvent;
import uz.anime.services.DocumentService;
import uz.anime.services.firebase.FirebaseService;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileEventListener {
    private final DocumentService documentService;
    private final FirebaseService firebaseService;

//    @Async
//    @EventListener(value = AnimeSetContentsEvent.class)
//    public void animeSetContentsEventListener(AnimeSetContentsEvent event) throws IOException {
//        Anime anime = animeService.findById(event.getAnimeId());
//        Document photoDocument = documentService.createDocument(event.getPhoto(), 5);
//        Document contentDocument = documentService.createDocument(event.getContent(), 5);
//        anime.setPhotoGeneratedName(photoDocument.getGeneratedName());
//        anime.setContentGeneratedName(contentDocument.getGeneratedName());
//        animeService.update(anime);
//    }

    @Async
    @EventListener(value = DocumentCreatedEvent.class)
    public void documentCreatedEventListener(DocumentCreatedEvent event) {
        MultipartFile file = event.getFile();
        Integer documentId = event.getDocumentId();
        Document document = documentService.findById(documentId);
        if (!Objects.isNull(file) && !Objects.isNull(document)) {
            String uploaded = firebaseService.upload(file, document.getGeneratedName());
            document.setFilePath(uploaded);
            documentService.update(document);
        }
    }
}

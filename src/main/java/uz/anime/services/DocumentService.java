package uz.anime.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;
import uz.anime.domains.Document;
import uz.anime.enums.FileType;
import uz.anime.evenet_listeners.events.DocumentCreatedEvent;
import uz.anime.repositories.DocumentRepository;
import uz.anime.services.firebase.FirebaseService;
import uz.anime.utils.MediaUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static uz.anime.utils.BaseUtils.generateFileName;
import static uz.anime.utils.BaseUtils.getExtension;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {
    private final FirebaseService firebaseService;
    private final DocumentRepository documentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AnimeService animeService;
    private final ObjectMapper objectMapper;

//    public Document saveMultipartDocuments(Integer animeId, MultipartFile photo, MultipartFile content) throws IOException {
//        saveMultipartDocuments(animeId, photo, FileType.PHOTO);
//        saveMultipartDocuments(animeId, content, FileType.CONTENT);
//
//        return null;
//    }

    //    public Document saveMultipartDocuments(Integer animeId, MultipartFile file, FileType fileAim) throws IOException {
//        long size = file.getSize();
//        if (size > 5 * 1024 * 1024) {
//            throw new IOException("File size must be less than 5MB");
//        }
//        final String originalFilename = file.getOriginalFilename();
//        final String extension = getExtension(Objects.requireNonNull(originalFilename));
//        final String generatedName = generateFileName() + extension;
//        Document document = Document.childBuilder()
//                .originalName(originalFilename)
//                .generatedName(generatedName)
//                .extension(extension)
//                .contentType(file.getContentType())
//                .size(size)
//                .build();
//        documentRepository.save(document);
//        Anime anime = animeService.findById(animeId);
//        if (FileType.CONTENT.equals(fileAim)) {
//            anime.setContentGeneratedName(generatedName);
//        } else {
//            anime.setPhotoGeneratedName(document.getGeneratedName());
//        }
//        log.warn("Anime is updating: {} , Document: '{}'", objectMapper.writeValueAsString(anime), objectMapper.writeValueAsString(document));
//        return document;
//    }
    @SuppressWarnings("unchecked")
    public Document createDocument(MultipartFile file, FileType fileType) throws IOException {
        String fileTypeName = fileType.name();
        String stringSize = FileType.VIDEO.name().equalsIgnoreCase(fileTypeName) ? "VIDEO_SIZE" : "PHOTO_SIZE";
        int size = (int) MediaUtils.FILE_SETTINGS.get(stringSize);
        String stringExtension = FileType.PHOTO.name().equalsIgnoreCase(fileTypeName) ? "PHOTO_EXTENSION" : "VIDEO_EXTENSION";
        List<String> extensions = (List<String>) MediaUtils.FILE_SETTINGS.get(stringExtension);
        return createDocument(file, size, extensions);
    }

    public Document createDocument(MultipartFile file, long size, List<String> extensions) throws IOException {
        long fileSize = file.getSize();
        if (fileSize > size * 1024 * 1024) {
            throw new IOException("File size must be less than %s MB".formatted(size));
        }
        final String originalFilename = file.getOriginalFilename();
        final String extension = getExtension(Objects.requireNonNull(originalFilename));
        if (!extensions.contains(extension)) {
            throw new RuntimeException("File extension must be one of %s".formatted(extensions));
        }
        final String generatedName = generateFileName() + extension;
        Document document = Document.childBuilder()
                .originalName(originalFilename)
                .generatedName(generatedName)
                .extension(extension)
                .contentType(file.getContentType())
                .size(fileSize)
                .build();
        documentRepository.save(document);
//        log.warn(" Document created: '{}'", objectMapper.writeValueAsString(document));
        applicationEventPublisher.publishEvent(new DocumentCreatedEvent(file, document.getId()));
        return document;
    }


    public Document saveFileDocument(File file) {
        String originalFilename = file.getName();
        String extension = getExtension(Objects.requireNonNull(originalFilename));
        String generatedName = generateFileName() + extension;
        Document document = Document.childBuilder()
                .originalName(originalFilename)
                .generatedName(generatedName)
                .extension(extension)
                .contentType(MimeType.valueOf("application/pdf").getType())
                .size(file.length())
                .build();
        documentRepository.save(document);
//        applicationEventPublisher.publishEvent(new DocumentSavedEvent(file, document));
        return document;
    }

    public File downloadFile(String generatedName) throws IOException {
        return firebaseService.download(generatedName);
    }


    public Document update(Document document) {
        return documentRepository.save(document);
    }

    public Document getById(Integer id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document Not found by id: '%s'".formatted(id)));
    }

    public Document findByGeneratedName(String generatedName) {
        return documentRepository.findByGeneratedName(generatedName)
                .orElseThrow(() -> new RuntimeException("Document Not found by generatedName: '%s'".formatted(generatedName)));
    }

    public Document findById(Integer documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document Not found by id: '%s'".formatted(documentId)));
    }
}

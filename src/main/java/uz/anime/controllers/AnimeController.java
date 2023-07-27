package uz.anime.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.anime.domains.Anime;
import uz.anime.dtos.ResponseDTO;
import uz.anime.dtos.anime.AnimeCreateDTO;
import uz.anime.services.AnimeService;

import static uz.anime.utils.UrlUtils.BASE_ANIME_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_ANIME_URL)
@PreAuthorize("isAuthenticated()")
@Tag(name = "Anime", description = "Anime CRUD API")
public class AnimeController {
    private final ObjectMapper objectMapper;
    private final AnimeService animeService;

    @Operation(summary = "For Authenticated users ,This API is used for create anime", responses = {
            @ApiResponse(responseCode = "200", description = "Anime Created", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDTO<Anime>> createAnime(@Valid @RequestBody AnimeCreateDTO dto) throws JsonProcessingException {
        Anime anime = animeService.create(dto);
        log.warn("Anime is creating : {}", objectMapper.writeValueAsString(anime));
        return ResponseEntity.ok(new ResponseDTO<>(anime, "Created successfully"));
    }

    @Operation(summary = "For Authenticated users ,This API is used for set cover photo and content", responses = {
            @ApiResponse(responseCode = "200", description = "Image and content set", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping(value = "/{animeId:.*}/set-contents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO<Void>> setAnimeContents(@PathVariable("animeId") Integer animeId,
                                                               @RequestParam(name = "photo") MultipartFile photo,
                                                               @RequestParam(name = "content") MultipartFile content) throws JsonProcessingException {
        Anime anime = animeService.setContents(animeId, photo, content);
        log.warn("files {} {}",  photo.getOriginalFilename(), content.getOriginalFilename());
        return ResponseEntity.ok(new ResponseDTO<>(null, "Contents set successfully"));
    }

    @Operation(summary = "For Authenticated users ,This API is used for get existing anime", responses = {
            @ApiResponse(responseCode = "200", description = "Anime returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping(value = "/get/{id:.*}")
    public ResponseEntity<ResponseDTO<Anime>> getAnime(@PathVariable("id") Integer id) throws JsonProcessingException {
        Anime anime = animeService.findById(id);
        return ResponseEntity.ok(new ResponseDTO<>(anime));
    }

    @Operation(summary = "For Authenticated users ,This API is used for get existing anime list", responses = {
            @ApiResponse(responseCode = "200", description = "Anime list returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping(value = "/get/all")
    public ResponseEntity<ResponseDTO<Page<Anime>>> getAllAnime(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                @RequestParam(name = "size", defaultValue = "10") Integer size) throws JsonProcessingException {
        Page<Anime> animePage = animeService.findAll(PageRequest.of(page - 1, size));
        return ResponseEntity.ok(new ResponseDTO<>(animePage));
    }

}


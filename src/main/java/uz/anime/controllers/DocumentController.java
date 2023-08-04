package uz.anime.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.anime.domains.Document;
import uz.anime.dtos.ResponseDTO;
import uz.anime.dtos.document.DocumentExtensionsUpdateDTO;
import uz.anime.dtos.document.DocumentSizeUpdateDTO;
import uz.anime.enums.FileType;
import uz.anime.services.DocumentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static uz.anime.utils.UrlUtils.BASE_DOCUMENTS_URL;


@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_DOCUMENTS_URL)
@PreAuthorize("isAuthenticated()")
@Tag(name = "Documents Controller", description = "Documents API")
public class DocumentController {
    private final DocumentService documentService;

    @Operation(summary = "For AUTHENTICATED USERS , This API is used for post file", responses = {
            @ApiResponse(responseCode = "200", description = "File saved", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping(name = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO<Document>> upload(@RequestParam(name = "fileType") FileType fileType, @RequestParam(name = "file") MultipartFile file) throws IOException {
        Document document = documentService.createDocument(file, fileType);
        return ResponseEntity.ok(new ResponseDTO<>(document, "File saved"));
    }

    @Operation(summary = "For AUTHENTICATED users, This API is used for download documents", responses = {
            @ApiResponse(responseCode = "200", description = "Document returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/download/{generatedName:.*}")
    public ResponseEntity<InputStreamResource> downloadByGeneratedName(@PathVariable(name = "generatedName") String generatedName) throws IOException {
        Document document = documentService.findByGeneratedName(generatedName);
        File file = documentService.downloadFile(generatedName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getOriginalName())
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Operation(summary = "For AUTHENTICATED users, This API is used for download documents", responses = {
            @ApiResponse(responseCode = "200", description = "Document returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/download/{id:.*}")
    public ResponseEntity<InputStreamResource> downloadById(@PathVariable(name = "id") Integer id) throws IOException {
        Document document = documentService.findById(id);
        File file = documentService.downloadFile(document.getGeneratedName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getOriginalName())
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Operation(summary = "For AUTHENTICATED USERS , This API is used for get documents", responses = {
            @ApiResponse(responseCode = "200", description = "Document returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/getById/{id:.*}")
    public ResponseEntity<ResponseDTO<Document>> getById(@PathVariable Integer id) throws IOException {
        Document document = documentService.getById(id);
        return ResponseEntity.ok(new ResponseDTO<>(document));
    }

    @Operation(summary = "For AUTHENTICATED USERS , This API is used for get documents by generated name", responses = {
            @ApiResponse(responseCode = "200", description = "Document returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/getByName/{generatedName:.*}")
    public ResponseEntity<ResponseDTO<Document>> getByGeneratedName(@PathVariable(name = "generatedName") String generatedName) throws IOException {
        Document document = documentService.findByGeneratedName(generatedName);
        return ResponseEntity.ok(new ResponseDTO<>(document));
    }


    @Operation(summary = "For ADMINS , This API is used for get file size(in MB)", responses = {
            @ApiResponse(responseCode = "200", description = "Updated ", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/document-size")
    public ResponseEntity<ResponseDTO<Map<String, Integer>>> getDocumentsSize() throws IOException {
        return ResponseEntity.ok(new ResponseDTO<>(documentService.getDocumentSize()));
    }
    @Operation(summary = "For ADMINS , This API is used for get file extensions", responses = {
            @ApiResponse(responseCode = "200", description = "Updated ", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/document-extensions")
    public ResponseEntity<ResponseDTO<Map<String, List<String>>>> getDocumentsExtensions() throws IOException {
        return ResponseEntity.ok(new ResponseDTO<>(documentService.getDocumentExtensions()));
    }

    @Operation(summary = "For ADMINS , This API is used for update file size(in MB)", responses = {
            @ApiResponse(responseCode = "200", description = "Updated ", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping("/update/document-size")
    public ResponseEntity<ResponseDTO<Map<String, Integer>>> updateDocumentsSize(@RequestBody DocumentSizeUpdateDTO dto) throws IOException {
        documentService.updateDocumentSize(dto);
        return ResponseEntity.ok(new ResponseDTO<>(documentService.getDocumentSize()));
    }

    @Operation(summary = "For ADMINS , This API is used for update file extensions", responses = {
            @ApiResponse(responseCode = "200", description = "Updated ", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping("/update/document-extensions")
    public ResponseEntity<ResponseDTO<Void>> updateDocumentsExtensions(@RequestBody DocumentExtensionsUpdateDTO dto) throws IOException {
        documentService.updateDocumentExtensions(dto);
        return ResponseEntity.ok(new ResponseDTO<>(null, "Extensions updated successfully"));
    }

    @Operation(summary = "For ADMINS , This API is used for update file size(in MB)", responses = {
            @ApiResponse(responseCode = "200", description = "Added ", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/add/document-extensions")
    public ResponseEntity<ResponseDTO<Void>> addToDocumentsExtensions(@RequestParam(name = "fileType") FileType fileType,
                                                                      @Schema(example = "[\"jpg\",\"mp4\"]") @RequestBody List<String> extensions) throws IOException {
        documentService.addToDocumentExtensions(fileType, extensions);
        return ResponseEntity.ok(new ResponseDTO<>(null, "Extensions updated successfully"));
    }
}


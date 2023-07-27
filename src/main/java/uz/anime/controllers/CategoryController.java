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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.anime.domains.Category;
import uz.anime.dtos.ResponseDTO;
import uz.anime.dtos.category.CategoryCreateDTO;
import uz.anime.dtos.category.CategoryUpdateDTO;
import uz.anime.services.CategoryService;

import static uz.anime.utils.UrlUtils.BASE_CATEGORY_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_CATEGORY_URL)
@Tag(name = "Category", description = "Category CRUD API")
public class CategoryController {
    private final ObjectMapper objectMapper;
    private final CategoryService categoryService;

    @Operation(summary = "For Authenticated Admins ,This API is used for create category", responses = {
            @ApiResponse(responseCode = "200", description = "Category created", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<Category>> createCategory(@Valid @RequestBody CategoryCreateDTO dto) throws JsonProcessingException {
        Category category = categoryService.create(dto);
        log.warn("Category is created : {}", objectMapper.writeValueAsString(category));
        return ResponseEntity.ok(new ResponseDTO<>(category, "Created successfully"));
    }

    @Operation(summary = "For Authenticated users ,This API is used for get existing category", responses = {
            @ApiResponse(responseCode = "200", description = "Category returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping(value = "/get/{id:.*}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ResponseDTO<Category>> getCategory(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(new ResponseDTO<>(categoryService.findById(id)));
    }

    @Operation(summary = "For Authenticated users ,This API is used for get existing category list", responses = {
            @ApiResponse(responseCode = "200", description = "Category list returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping(value = "/get/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ResponseDTO<Page<Category>>> getCategoryList(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                       @RequestParam(name = "size", defaultValue = "10") Integer size) {
        PageRequest p = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(new ResponseDTO<>(categoryService.findAll(p)));
    }

    @Operation(summary = "For Authenticated users ,This API is used for update existing category", responses = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<Category>> updateCategory(@RequestBody CategoryUpdateDTO dto) throws JsonProcessingException {
        Category category = categoryService.update(dto);
        log.warn("Category is updated : {}", objectMapper.writeValueAsString(category));
        return ResponseEntity.ok(new ResponseDTO<>(category, "Updated successfully"));
    }

    @Operation(summary = "For Authenticated users ,This API is used for delete existing category", responses = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @DeleteMapping(value = "/delete/{id:.*}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<Category>> deleteCategory(@PathVariable(name = "id") Integer id) throws JsonProcessingException {
        Category category = categoryService.delete(id);
        log.warn("Category is deleted : {}", objectMapper.writeValueAsString(category));
        return ResponseEntity.ok(new ResponseDTO<>(category, "Deleted successfully"));
    }


}


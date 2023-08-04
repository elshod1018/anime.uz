package uz.anime.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.anime.domains.Lists;
import uz.anime.dtos.ResponseDTO;
import uz.anime.dtos.lists.ListsCreateDTO;
import uz.anime.dtos.lists.ListsUpdateDTO;
import uz.anime.services.ListsService;

import java.io.IOException;
import java.util.List;

import static uz.anime.utils.UrlUtils.BASE_LISTS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_LISTS_URL)
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Lists Controller", description = "Util API")
public class ListsController {
    private final ListsService listsService;

    @Operation(summary = "For ADMINS , This API is used for add to lists", responses = {
            @ApiResponse(responseCode = "200", description = "Added ", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Lists>> createLists(@RequestBody ListsCreateDTO dto) throws IOException {
        Lists lists = listsService.create(dto);
        return ResponseEntity.ok(new ResponseDTO<>(lists, "Sizes updated successfully"));
    }

    @Operation(summary = "For ADMINS , This API is used  to get from lists", responses = {
            @ApiResponse(responseCode = "200", description = "Returned ", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/{id:.*}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDTO<Lists>> getById(@PathVariable(name = "id") Integer id) throws IOException {
        Lists lists = listsService.findById(id);
        return ResponseEntity.ok(new ResponseDTO<>(lists));
    }

    @Operation(summary = "For ADMINS , This API is used  to get from lists", responses = {
            @ApiResponse(responseCode = "200", description = "Returned ", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDTO<List<Lists>>> getByTypeId(@RequestParam(name = "typeId") Integer typeId) throws IOException {
        List<Lists> byTypeId = listsService.findByTypeId(typeId);
        return ResponseEntity.ok(new ResponseDTO<>(byTypeId));
    }


    @Operation(summary = "For ADMINS , This API is used for update lists", responses = {
            @ApiResponse(responseCode = "200", description = "Updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Lists>> updateLists(@RequestBody ListsUpdateDTO dto) throws IOException {
        Lists lists = listsService.update(dto);
        return ResponseEntity.ok(new ResponseDTO<>(lists, "Lists updated successfully"));
    }

    @Operation(summary = "For ADMINS , This API is used for delete lists", responses = {
            @ApiResponse(responseCode = "200", description = "Deleted", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping("/delete/{id:.*}")
    public ResponseEntity<ResponseDTO<Void>> deleteFromLists(@PathVariable(name = "id") Integer id) throws IOException {
        listsService.delete(id);
        return ResponseEntity.ok(new ResponseDTO<>(null, "Lists deleted successfully"));
    }
}
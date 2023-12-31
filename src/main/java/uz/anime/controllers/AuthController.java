package uz.anime.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.anime.domains.AuthUser;
import uz.anime.dtos.ResponseDTO;
import uz.anime.dtos.authuser.*;
import uz.anime.enums.SMSCodeType;
import uz.anime.services.AuthUserService;

import static uz.anime.utils.UrlUtils.BASE_AUTH_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_AUTH_URL)
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {
    private final AuthUserService authUserService;
    private final ObjectMapper objectMapper;

    @Operation(summary = "For ANONYM users ,This API is used for user registration", responses = {
            @ApiResponse(responseCode = "200", description = "User registered", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/user/register")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<ResponseDTO<AuthUser>> register(@Valid @RequestBody UserCreateDTO dto) throws JsonProcessingException {
        AuthUser authUser = authUserService.create(dto);
        log.warn("User registered : {}", objectMapper.writeValueAsString(authUser));
        return ResponseEntity.ok(new ResponseDTO<>(authUser, "Registered successfully"));
    }

    @Operation(summary = "For ANONYM users ,This API is used for generate access token", responses = {
            @ApiResponse(responseCode = "200", description = "Access token generated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/token/access")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<ResponseDTO<TokenResponse>> generateToken(TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authUserService.generateToken(tokenRequest);
        tokenResponse.setRole(authUserService.findByUsername(tokenRequest.username()).getRole());
        return ResponseEntity.ok(new ResponseDTO<>(tokenResponse));
    }

    @Operation(summary = "For ANONYM users ,This API is used for generating a new access token using the refresh token", responses = {
            @ApiResponse(responseCode = "200", description = "Access token generated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/token/refresh")
    public ResponseEntity<ResponseDTO<TokenResponse>> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        TokenResponse tokenResponse = authUserService.refreshAccessToken(refreshTokenRequest);
        return ResponseEntity.ok(new ResponseDTO<>(tokenResponse));
    }


    @Operation(summary = "For ANONYM users ,This API is used for user activating users through the activation code that was sent via Email that is user entered", responses = {
            @ApiResponse(responseCode = "200", description = "User activated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/user/activate")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<ResponseDTO<Boolean>> activate(@Valid @RequestBody UserActivationDTO dto) {
        authUserService.activate(dto);
        log.warn("User with username '{}' activated", dto.username());
        return ResponseEntity.ok(new ResponseDTO<>(null, "User activated successfully"));
    }

    @Operation(summary = "For ANONYM users ,This API is used for user activating users through the activation code that was sent via Email that is user entered", responses = {
            @ApiResponse(responseCode = "200", description = "User activated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/code/resend")
    public ResponseEntity<ResponseDTO<Void>> resendCode(@NonNull String username) {
        authUserService.resendCode(username, SMSCodeType.ACTIVATION);
        return ResponseEntity.ok(new ResponseDTO<>(null, "Sms code resent successfully"));
    }
}


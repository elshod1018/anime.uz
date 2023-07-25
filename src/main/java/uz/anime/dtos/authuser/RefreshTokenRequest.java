package uz.anime.dtos.authuser;

import jakarta.validation.constraints.NotBlank;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public record RefreshTokenRequest(@NotBlank String refreshToken) {
}

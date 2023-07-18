package uz.anime.dtos.authuser;

import jakarta.validation.constraints.NotBlank;

public record UserActivationDTO(@NotBlank(message = "Email can not be blank") String email,
                                @NotBlank(message = "Code can not be blank") String code) {
}

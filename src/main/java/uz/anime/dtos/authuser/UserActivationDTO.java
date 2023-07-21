package uz.anime.dtos.authuser;

import jakarta.validation.constraints.NotBlank;

public record UserActivationDTO(@NotBlank(message = "Username can not be blank") String username,
                                @NotBlank(message = "Code can not be blank") String code) {
}

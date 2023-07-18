package uz.anime.dtos.authuser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserResetPasswordDTO(@NotBlank(message = "Phone Number can not be blank")
                                   String phoneNumber,
                                   @NotBlank(message = "Password can not be blank")
                                   @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
                                   String password,
                                   @NotBlank(message = "Code can not be blank")
                                   String code) {}

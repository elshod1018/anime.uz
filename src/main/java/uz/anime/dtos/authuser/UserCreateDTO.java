package uz.anime.dtos.authuser;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import uz.anime.customAnnotations.annotations.UniqueUsername;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class UserCreateDTO implements Serializable {
    @UniqueUsername
    @NotBlank(message = "Username can not be blank")
    private String username;

    @Schema(example = "anime123@gmail.com")
    @NotBlank(message = "Email can not be blank")
    @Pattern(regexp = "^(\\w+)@([A-Za-z0-9]{3,9})\\.([a-z0-9]{2,4})$", message = "It must be email format")
    private String email;

    @Size(min = 8, max = 16, message = "Password must be between {min} and {max} character")
    @NotBlank(message = "Password can not be blank")
    private String password;

    @Size(min = 8, max = 16, message = "Password must be between {min} and {max} character")
    @NotBlank(message = "Confirm Password can not be blank")
    private String confirmPassword;
}

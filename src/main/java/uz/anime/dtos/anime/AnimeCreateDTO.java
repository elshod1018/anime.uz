package uz.anime.dtos.anime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimeCreateDTO {
    @NotBlank(message = "Name can not be blank")
    private String name;

    @NotBlank(message = "Description can not be blank")
    private String description;

    @NotBlank(message = "Manager can not be blank")
    private String manager;

    @NotBlank(message = "Author can not be blank")
    private String author;

    @NotBlank(message = "Studio can not be blank")
    private String studio;

    @NotBlank(message = "License can not be blank")
    private String license;

    private List<Integer> categories;

    @Past(message = "Published date must be past")
    private LocalDate publishedDate;

}

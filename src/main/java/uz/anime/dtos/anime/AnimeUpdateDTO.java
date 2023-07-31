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
public class AnimeUpdateDTO {
    private Integer id;

    private String name;

    private String description;

    private String manager;

    private String author;

    private String studio;

    private String license;

    private List<Integer> categories;

    private LocalDate publishedDate;

}

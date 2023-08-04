package uz.anime.dtos.anime;

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

    private String photoGeneratedName;

    private String contentGeneratedName;

    private List<Integer> categories;

    private LocalDate publishedDate;

}

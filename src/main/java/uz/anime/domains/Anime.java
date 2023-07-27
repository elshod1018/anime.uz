package uz.anime.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Anime extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private String manager;

    private String author;

    private String studio;

    private String license;

    private String photoGeneratedName;

    private String contentGeneratedName;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> categories;

    private Integer viewCount;

    private LocalDate publishedDate;

    private Integer rating;

    @Builder(builderMethodName = "childBuilder")
    public Anime(Integer createdBy, Integer updateBy, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted,
                 Integer id, String name, String description, String manager, String author, String studio, String license, String photoGeneratedName, String contentGeneratedName, List<Integer> categories, Integer viewCount, LocalDate publishedDate, Integer rating) {
        super(createdBy, updateBy, createdAt, updatedAt, deleted);
        this.id = id;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.author = author;
        this.studio = studio;
        this.license = license;
        this.photoGeneratedName = photoGeneratedName;
        this.contentGeneratedName = contentGeneratedName;
        this.categories = categories;
        this.viewCount = viewCount;
        this.publishedDate = publishedDate;
        this.rating = rating;
    }
}

package uz.anime.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer parentId;

    private String name;

    private String description;
    @Builder(builderMethodName = "childBuilder")
    public Category(Integer createdBy, Integer updateBy, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted,
                    Integer id, Integer parentId, String name, String description) {
        super(createdBy, updateBy, createdAt, updatedAt, deleted);
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.description = description;
    }
}

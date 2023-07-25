package uz.anime.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private boolean hasChild;
    @Builder(builderMethodName = "childBuilder")
    public Category(Integer createdBy, Integer updateBy, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted,
                    Integer id, Integer parentId, String name, String description, boolean hasChild) {
        super(createdBy, updateBy, createdAt, updatedAt, deleted);
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.description = description;
        this.hasChild = hasChild;
    }
}

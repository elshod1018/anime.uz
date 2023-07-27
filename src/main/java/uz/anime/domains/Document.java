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
public class Document extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String generatedName;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private long size;

    private String filePath;

    @Column(nullable = false)
    private String extension;

    @Builder(builderMethodName = "childBuilder")
    public Document(Integer createdBy, Integer updateBy, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted, Integer id, String originalName, String generatedName, String contentType, long size, String filePath, String extension) {
        super(createdBy, updateBy, createdAt, updatedAt, deleted);
        this.id = id;
        this.originalName = originalName;
        this.generatedName = generatedName;
        this.contentType = contentType;
        this.size = size;
        this.filePath = filePath;
        this.extension = extension;
    }
}

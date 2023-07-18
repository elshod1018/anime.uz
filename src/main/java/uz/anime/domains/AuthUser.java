package uz.anime.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import uz.anime.enums.Language;
import uz.anime.enums.Role;
import uz.anime.enums.Status;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUser extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private String fullName;

    private String email;

    private String profilePhotoGeneratedName;

    @ElementCollection
    private List<Integer> likedAnimeIds;

    @ElementCollection
    private List<Integer> savedAnimeIds;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder(builderMethodName = "childBuilder")
    public AuthUser(Integer createdBy, Integer updateBy, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted,
                    Integer id, String username, String password, String fullName, String email, String profilePhotoGeneratedName, List<Integer> likedAnimeIds, List<Integer> savedAnimeIds) {
        super(createdBy, updateBy, createdAt, updatedAt, deleted);
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.profilePhotoGeneratedName = profilePhotoGeneratedName;
        this.likedAnimeIds = likedAnimeIds;
        this.savedAnimeIds = savedAnimeIds;
        this.language = Language.UZ;
        this.role = Role.USER;
        this.status = Status.NO_ACTIVE;
    }
}

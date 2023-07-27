package uz.anime.dtos.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDTO {

    private Integer parentId;

    @NotBlank(message = "Name can not be blank")
    private String name;

    @NotBlank(message = "Description can not be blank")
    private String description;

    private boolean hasChild;
}

package uz.anime.dtos.lists;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListsCreateDTO {
    private Integer typeId;

    private String name;

    private String description;

    private String variable;

    private Integer value;
}

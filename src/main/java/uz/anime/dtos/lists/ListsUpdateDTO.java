package uz.anime.dtos.lists;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListsUpdateDTO {
    private Integer id;

    private Integer typeId;

    private String name;

    private String description;

    private String variable;

    private Integer value;
}

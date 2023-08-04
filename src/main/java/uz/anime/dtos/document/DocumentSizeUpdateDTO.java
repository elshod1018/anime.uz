package uz.anime.dtos.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSizeUpdateDTO {
    @Schema(example = "5")
    private Integer photoSize;

    @Schema(example = "20")
    private Integer videoSize;
}

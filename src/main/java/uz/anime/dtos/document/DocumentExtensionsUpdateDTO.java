package uz.anime.dtos.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentExtensionsUpdateDTO {
    @Schema(example = "[\"jpg\", \"png\", \"jpeg\"]")
    private List<String> photoExtensions;

    @Schema(example = "[\"mp4\", \"avi\", \"mkv\"]")
    private List<String> videoExtensions;
}

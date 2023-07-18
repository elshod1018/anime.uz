package uz.anime.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    private String message;
    private T data;
    private AppErrorDTO error;
    private boolean success;

    private ResponseDTO(String message, T data, boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public ResponseDTO(T data, String message) {
        this(message, data, true);
    }

    public ResponseDTO(T data) {
        this(null, data, true);
    }

    private ResponseDTO(AppErrorDTO error, boolean success) {
        this.error = error;
        this.success = success;
    }

    public ResponseDTO(AppErrorDTO error) {
        this(error, false);
    }
}

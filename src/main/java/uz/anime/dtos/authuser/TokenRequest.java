package uz.anime.dtos.authuser;

import lombok.NonNull;

public record TokenRequest(@NonNull String username, @NonNull String password) {
}

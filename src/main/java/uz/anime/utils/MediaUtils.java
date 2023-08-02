package uz.anime.utils;


import java.util.List;
import java.util.Map;

public interface MediaUtils {
    Map<String, Object> FILE_SETTINGS = Map.of(
            "PHOTO_SIZE", 5,
            "VIDEO_SIZE", 100,
            "PHOTO_EXTENSION", List.of(".jpg", ".jpeg", ".png", "jpg", "jpeg", "png"),
            "VIDEO_EXTENSION", List.of(".mp4", ".avi", ".mkv", "mp4", "avi", "mkv")
    );
    String BUCKET_NAME = "anime-uz.appspot.com";
    String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME + "/o/%s";
}

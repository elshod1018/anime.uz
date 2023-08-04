package uz.anime.utils;


import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.RequestEntity.put;

@UtilityClass
public class MediaUtils {
    public Map<String, Object> FILE_SETTINGS = new HashMap<>() {{
        put("PHOTO_SIZE", 5);
        put("VIDEO_SIZE", 100);
        put("PHOTO_EXTENSION", List.of("jpg", "jpeg", "png"));
        put("VIDEO_EXTENSION", List.of("mp4", "avi", "mkv"));
    }};

    public static final String BUCKET_NAME = "anime-uz.appspot.com";
    public static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME + "/o/%s";
}

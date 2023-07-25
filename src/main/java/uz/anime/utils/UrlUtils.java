package uz.anime.utils;

import org.springframework.stereotype.Component;


@Component
public interface UrlUtils {
    String BASE_URL = "/api/v1";

    /*Auth*/
    String BASE_AUTH_URL=BASE_URL+"/auth";

    /* Anime */
    String BASE_ANIME_URL=BASE_URL+"/anime";

    /* Category */
    String BASE_CATEGORY_URL=BASE_URL+"/category";

    /* Documents */
    String BASE_DOCUMENTS_URL = BASE_URL + "/documents";

}

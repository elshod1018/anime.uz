package uz.anime.utils;

import org.springframework.stereotype.Component;


@Component
public interface UrlUtils {
    String BASE_URL = "/api/v1";

    /*Auth*/
    String BASE_AUTH_URL=BASE_URL+"/auth";

    /* Documents */
    String BASE_DOCUMENTS_URL = BASE_URL + "/documents";

}

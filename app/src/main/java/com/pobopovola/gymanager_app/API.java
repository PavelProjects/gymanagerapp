package com.pobopovola.gymanager_app;

public interface API {
    String BASE_URL = "http://94.154.11.176:8080";

    String LOGIN_URL = BASE_URL + "/auth";

    String USER_INFO_URL = BASE_URL + "/user?login={login}";

    String CLIENT_ALL_URL = BASE_URL + "/client/all";
    String CLIENT_BY_ID_URL = BASE_URL + "/client/{id}";
    String CLIENT_CREATE_URL = BASE_URL + "/client";
    String CLIENT_UPDATE_URL = BASE_URL + "/client/update";

    String WORKOUT_BY_ID = BASE_URL + "/workout/{id}";

    String EXERCISE_TYPES_URL = BASE_URL + "/dict/exercise";
}

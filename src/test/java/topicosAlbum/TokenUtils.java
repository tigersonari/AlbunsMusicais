package topicosAlbum;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import topicosAlbum.dto.AuthDTO;

public class TokenUtils {

    public static String getAdminToken() {
        AuthDTO dto = new AuthDTO("admin", "123456"); // login/senha que você colocou no import.sql

        return given()
                .contentType(ContentType.JSON)
                .body(dto)
            .when()
                .post("/auth")
            .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    public static String getUserToken() {
        AuthDTO dto = new AuthDTO("user", "123456"); // se você criou esse usuário

        return given()
                .contentType(ContentType.JSON)
                .body(dto)
            .when()
                .post("/auth")
            .then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}

package helpers;

import com.jayway.jsonpath.DocumentContext;
import configuration.Configuration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@NoArgsConstructor
public class Helper {
    final String url = Configuration.getURL();
    final RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(url).setContentType(JSON).build();

    public final Response loginAsAGuestResponse() {
        final Map<String, String> params = new HashMap<String, String>() {{
            put("grant_type", "client_credentials");
            put("scope", "guest:default");
        }};

        return given()
                .spec(requestSpecification)
                .auth()
                .preemptive()
                .basic(Configuration.getBasicAuth(), "Password")
                .when()
                .body(params)
                .post("/v2/oauth2/token");
    }

    public final String getGuestToken() {
        return loginAsAGuestResponse().then().extract().path("access_token");
    }

    public final Response loginAsAPlayerResponse(final String username, final String password){
        final Map<String, String> params = new HashMap<String, String>() {{
            put("grant_type","password");
            put("username", username);
            put("password", password);
        }};

        return given()
                .spec(requestSpecification)
                .auth()
                .preemptive()
                .basic(Configuration.getBasicAuth(), "Password")
                .when()
                .body(params)
                .post("/v2/oauth2/token");

    }
    public final Response registerANewPlayerResponse(final DocumentContext doc) {
        return given()
                .spec(requestSpecification)
                .header("Authorization", "Bearer " + getGuestToken())
                .body(doc.jsonString())
                .post("/v2/players");
    }

    public final String getAuthorizedPlayerToken(final String username, final String password){
        return loginAsAPlayerResponse(Configuration.getUsername(),Configuration.getPassword()).then().extract().path("access_token");
    }

    public final Response getPlayerInfo(final int id, final String token) {
        return given()
                .spec(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .get("/v2/players/"+id);
    }
}

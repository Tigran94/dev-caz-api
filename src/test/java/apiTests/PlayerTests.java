package apiTests;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import configuration.Configuration;
import configuration.Constants;
import helpers.Helper;
import io.qameta.allure.Description;
import io.restassured.module.jsv.JsonSchemaValidator;
import listener.CustomListener;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RandomData;

import static org.hamcrest.CoreMatchers.equalTo;

@Listeners(CustomListener.class)
public class PlayerTests extends BaseTest {
    private Helper helper;

    @BeforeMethod
    public void init() {
        helper = new Helper();
    }

    @Test
    @Description("Create a new player and assert the response")
    public void test1() {
        final JSONObject jsonObject = generateJSONObjectFromResource(Constants.PATH_NEW_USER);
        final DocumentContext doc = JsonPath.parse(jsonObject);
        final String password = RandomData.getRandomPassword();
        final String username = RandomData.getRandomUsername();
        final String email = RandomData.getRandomEmail();

        doc.set("username", username);
        doc.set("password_change", password);
        doc.set("password_repeat", password);
        doc.set("email", email);

        helper.registerANewPlayerResponse(doc)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("username", equalTo(username))
                .body("email", equalTo(email))
                .body("name", equalTo(doc.read("name")))
                .body("surname", equalTo(doc.read("surname")))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/player_schema.json"));

    }

    @Test
    @Description("Get a player info and assert the response")
    public void test2() {
        final String token = helper.getAuthorizedPlayerToken(Configuration.getUsername(), Configuration.getPassword());
        helper.getPlayerInfo(7712, token)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/player_schema.json"));
    }

    @Test
    @Description("Get an incorrect player info and assert the response")
    public void test3() {
        final String token = helper.getAuthorizedPlayerToken(Configuration.getUsername(), Configuration.getPassword());
        helper.getPlayerInfo(7713, token)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/not_found_schema.json"));
    }
}

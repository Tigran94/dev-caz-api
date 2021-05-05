package apiTests;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
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

@Listeners(CustomListener.class)
public class AuthorizationTests extends BaseTest {
    private Helper helper;

    @BeforeMethod
    public void init(){
        helper = new Helper();
    }

    @Test
    @Description( "Get guest access token and validate that")
    public void test1(){
        helper.loginAsAGuestResponse()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/guest_authorization_schema.json"));
    }

    @Test
    @Description( "login as a player and validate the response")
    public void test2(){
        final JSONObject jsonObject = generateJSONObjectFromResource(Constants.PATH_NEW_USER);
        final DocumentContext doc = JsonPath.parse(jsonObject);
        final String password = RandomData.getRandomPassword();
        final String username = RandomData.getRandomUsername();
        final String email = RandomData.getRandomEmail();

        doc.set("username", username);
        doc.set("password_change", password);
        doc.set("password_repeat",password);
        doc.set("email",email);

        helper.registerANewPlayerResponse(doc)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);

        helper.loginAsAPlayerResponse(username,password)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user_authorization_schema.json"));
    }
}

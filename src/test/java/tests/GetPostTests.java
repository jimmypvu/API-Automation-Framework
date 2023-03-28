package tests;

import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetPostTests {
    private static final String BASEURL = "https://reqres.in/api";
    @Test
    public void getTest01(){
        baseURI = BASEURL;

        when()
                .get("/users?page=2")
        .then()
                .statusCode(200)
                .body("data[4].first_name", equalTo("George"))
                .body("data.first_name", hasItems("George", "Rachel"))
                .body("data.last_name", hasItems("Funke", "Edwards"))
                .log().all();

    }

    @Test
    public void getTest02(){
        baseURI = BASEURL;

        given()
                .header("content-type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .get("/users?page=2")
        .then()
                .statusCode(200)
        .and()
                .body("data[0]", hasKey("id"))
                .body("data[0]", hasKey("email"))
                .body("data[0]", hasKey("first_name"))
                .body("data[0]", hasKey("last_name"))
                .body("data[0]", hasKey("avatar"))
                .log().all();

//        System.out.println(get("users?page=2").body().asPrettyString());

    }

}

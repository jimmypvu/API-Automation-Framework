package tests;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import net.minidev.json.JSONObject;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class PutPatchDeleteTests {
    private static final String BASEURL = "https://reqres.in/api";
    @Test
    public void putTest01(){
        baseURI = BASEURL;

        JSONObject reqBody = new JSONObject();
        reqBody.put("name", "jimmy");
        reqBody.put("job", "qa tester");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(reqBody.toJSONString())
        .when()
                .put("/users/2")
        .then()
                .statusCode(200)
        .and()
                .body("job", equalTo("qa tester"))
                .log().all();
    }

    @Test
    public void patchTest01(){
        baseURI = BASEURL;

        JSONObject reqBody = new JSONObject();
        reqBody.put("name", "obi wan");
        reqBody.put("job", "jedi");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(reqBody.toJSONString())
        .when()
                .patch("/users/2")
        .then()
                .statusCode(200)
        .and()
                .body("name", equalTo("obi wan"))
                .body("job", equalTo("jedi"))
                .log().all();
    }

    @Test
    public void deleteTest01(){
        baseURI = BASEURL;
//        baseURI = "https://www.thecocktaildb.com/api.php";

        when()
                .delete("/users/2")
        .then()
                .statusCode(204)
                .log().all();

        List<Header> headers = delete("/users/2").getHeaders().asList();

        System.out.println("headers: ");
        System.out.println(headers);
    }
}

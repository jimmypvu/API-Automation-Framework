package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        .and()
                .body("data[4].first_name", equalTo("George"))
                .body("data.first_name", hasItems("George", "Rachel"))
                .body("data.last_name", hasItems("Funke", "Edwards"))
                .log().all();
    }

    @Test
    public void getTest02(){
        baseURI = BASEURL;

        RequestSpecification req = given()
                                    .header("content-type", "application/json")
                                    .contentType(ContentType.JSON)
                                    .accept(ContentType.JSON);

        Response res = req.get("/users?page=2");
        ResponseBody resBody = res.body();

        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertEquals(resBody.asString().contains("id"), true, "Response body contains 'id'");

        JsonPath jsp = res.jsonPath();
        List<String> emailList = jsp.getList("data.email");

        for(String email : emailList){
            Assert.assertEquals(email.contains("@"), true, "Email format valid (contain '@')");
            Assert.assertEquals(email.indexOf("@"), email.lastIndexOf("@"), "Email format valid (contain only 1 '@')");
            Assert.assertEquals(email.contains("."), true, "Email format valid (contain '.')");
        }
    }

    @Test
    public void getTest03(){
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
    }

    @Test
    public void postTest01(){
        baseURI = BASEURL;
        Map<String, String> map = new HashMap<>();
        map.put("name", "jimmy");
        map.put("job", "tester");

        JSONObject req = new JSONObject(map);

        given()
                .header("content-type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(req.toJSONString())
        .when()
                .post("/users")
        .then()
                .statusCode(201)
        .and()
                .body("name", equalTo("jimmy"))
                .body("id", matchesPattern("^[0-9]+$"))
                .log().all();
    }

    @Test
    public void postTest02(){
        baseURI = BASEURL;

        JSONObject req = new JSONObject();
        req.put("name", "jimmy");
        req.put("job", "qa");

        given()
                .header("content-type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(req.toJSONString())
        .when()
                .post("/users")
        .then()
                .statusCode(201)
        .and()
                .body("name", equalTo("jimmy"))
                .body("job", equalTo("qa"))
                .body("id", matchesPattern("^[0-9]+$"))
                .log().all();
    }
}

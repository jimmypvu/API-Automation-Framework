package tests;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExampleTests {
    @Test
    public void test01(){
        Response res = get("https://reqres.in/api/users?page=2");
        System.out.println(res.getStatusCode());
        System.out.println(res.getTime());
        System.out.println(res.getBody().asPrettyString());
        System.out.println(res.getStatusLine());
        System.out.println(res.getHeader("content-type"));

        int statusCode = res.statusCode();

        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void test02(){
        baseURI = "https://reqres.in/api";

        when()
                .get("/users?page=2")
        .then()
                .statusCode(200)
                .body("data[1].id", equalTo(8))
                .body("data[1].first_name", equalTo("Lindsay"))
                .log().all();

//        System.out.println(get("/users?page=2").getBody().asPrettyString());
    }
}

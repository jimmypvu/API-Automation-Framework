package tests;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

public class JSONSchemaValidator {
    private final String BASEURL = "https://reqres.in/api";
    @Test
    public void validateGetSchema(){
        baseURI = BASEURL;

        RequestSpecification req = given()
                .header("content-type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        when()
                .get("/users?page=2")
        .then()
                .statusCode(200)
        .and()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/testdata/restschema/ReqresListUsers_GETschema.json")))
                .log().all();
    }

    @Test
    public void validatePostSchema(){
        baseURI = BASEURL;

        JSONObject reqBody = new JSONObject();
        reqBody.put("name", "butters");
        reqBody.put("job", "professor (chaos studies)");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(reqBody.toJSONString())
        .when()
                .post("/users")
        .then()
                .statusCode(201)
        .and()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/testdata/restschema/ReqresCreateUser_POSTschema.json")))
                .log().all();
    }

    @Test
    public void validatePutSchema(){
        baseURI = BASEURL;

        JSONObject reqBody = new JSONObject();
        reqBody.put("name", "fredward mercury");
        reqBody.put("job", "astronaut");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(reqBody.toJSONString())
        .when()
                .put("/users/2")
        .then()
                .statusCode(200)
        .and()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/testdata/restschema/ReqresUpdateUser_PUTschema.json")))
                .log().all();
    }
}

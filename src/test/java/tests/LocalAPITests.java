package tests;

import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class LocalAPITests {
    private final String LOCALJSS = "http://localhost:3000";

    @Test
    public void get(){
        //json-server mock api
        baseURI = LOCALJSS;

        given()
                .get("/users")
        .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void post(){
        baseURI = LOCALJSS;

        JSONObject req = new JSONObject();
        req.put("firstName", "Prince");
        req.put("lastName", "The Arist FKA");
        req.put("subjectId", 2);

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(req.toJSONString())
        .when()
                .post("/users")
        .then()
                .statusCode(201)
                .log().all();
    }

    @Test
    public void put(){
        baseURI = LOCALJSS;

        JSONObject req = new JSONObject();
        req.put("firstName", "David");
        req.put("lastName", "Chappelle");
        req.put("subjectId", 2);

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(req.toJSONString())
        .when()
                .put("/users/4")
        .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void patch(){
        baseURI = LOCALJSS;

        JSONObject req = new JSONObject();
        req.put("lastName", "Banner");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(req.toJSONString())
        .when()
                .patch("/users/4")
        .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void delete(){
        baseURI = LOCALJSS;

        when()
                .delete("/users/4")
        .then()
                .statusCode(200)
                .log().all();
    }
}

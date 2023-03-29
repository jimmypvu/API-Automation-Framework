package tests;

import io.restassured.http.ContentType;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsd;

public class XMLSchemaValidator {
    private static final String SOAPSERVICE = "http://www.dneonline.com";
    @Test
    public void validatePostSchema() throws IOException {
        baseURI = SOAPSERVICE;

        File file = new File("./soapXMLs/Add_POST.xml");
        FileInputStream fis = new FileInputStream(file);
        String reqBody = IOUtils.toString(fis, "utf-8");

        given()
                .contentType("text/xml")
                .accept(ContentType.XML)
                .body(reqBody)
        .when()
                .post("/calculator.asmx")
        .then()
                .statusCode(200)
        .and()
                .assertThat()
                .body(matchesXsd(new File("./src/test/resources/testdata/soapschema/CalculatorAdd_POSTschema.xsd")))
                .log().all();
    }
}

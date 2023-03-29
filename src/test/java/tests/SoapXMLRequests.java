package tests;

import io.restassured.http.ContentType;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class SoapXMLRequests {
    private static final String SOAPURL = "http://www.dneonline.com";
    //http://www.dneonline.com/calculator.asmx

    @Test
    public void validateSoapXML() throws IOException {
        baseURI = SOAPURL;

        File file = new File("./soapXMLs/Add.xml");
        FileReader fr = new FileReader(file);
        String reqBody = IOUtils.toString(fr);

        given()
                .contentType("text/xml")
                .accept(ContentType.XML)
                .body(reqBody)
        .when()
                .post("/calculator.asmx")
        .then()
                .statusCode(200)
                .log().all()
        .and()
                .body("//*:AddResult.text()", equalTo("13"));
    }
}

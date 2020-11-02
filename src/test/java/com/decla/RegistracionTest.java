package com.decla;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class RegistracionTest {

    @Test
    public void testHelloEndpoint() {
      /*  
      given()
          .when().get("/")
          .then()
             .statusCode(200);

        
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("hello"));
             */
    }

}
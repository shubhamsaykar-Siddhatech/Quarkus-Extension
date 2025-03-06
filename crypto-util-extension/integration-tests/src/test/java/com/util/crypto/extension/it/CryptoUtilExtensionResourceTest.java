package com.util.crypto.extension.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CryptoUtilExtensionResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/crypto-util-extension")
                .then()
                .statusCode(200)
                .body(is("Hello crypto-util-extension"));
    }
}

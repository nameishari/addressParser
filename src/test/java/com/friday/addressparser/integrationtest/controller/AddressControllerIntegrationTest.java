package com.friday.addressparser.integrationtest.controller;

import com.friday.addressparser.dto.response.AddressDTO;
import com.friday.addressparser.exception.BadRequestException;
import io.restassured.RestAssured;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressControllerIntegrationTest {

    private static final String ADDRESS_PARSE_BASE_URL = "/address/parse";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testShouldParseAddress() {
        getAddressParsingTestCases().forEach((inputString, expectedResponse) -> {
            //@formatter:off
            given()
                .param("addressString", inputString)
            .when()
                .get(ADDRESS_PARSE_BASE_URL)
                .prettyPeek()
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("street", is(expectedResponse.getStreet()))
                .body("houseNumber", is(expectedResponse.getHouseNumber()));
            //@formatter:on
        });
    }

    @Test
    void testShouldThrowBadRequestWhenInputIsEmpty() {
        //@formatter:off
        given()
            .param("addressString", StringUtils.EMPTY)
        .when()
            .get(ADDRESS_PARSE_BASE_URL)
            .prettyPeek()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("exception", is(BadRequestException.class.getCanonicalName()));
        //@formatter:on
    }

    private Map<String, AddressDTO> getAddressParsingTestCases() {
        Map<String, AddressDTO> testCases = new HashMap<>();
        testCases.put("Winterallee 3", getAddressDTO("Winterallee", "3"));
        testCases.put("Lobeckstraße 40,", getAddressDTO("Lobeckstraße", "40"));
        testCases.put("Knobelsdorffstraße  11A", getAddressDTO("Knobelsdorffstraße", "11A"));
        testCases.put("Am Bächle 23", getAddressDTO("Am Bächle", "23"));
        testCases.put("Auf der Vogelwiese 23 b", getAddressDTO("Auf der Vogelwiese", "23 b"));
        testCases.put("4, rue de la revolution", getAddressDTO("rue de la revolution", "4"));
        testCases.put("200 Broadway Av", getAddressDTO("Broadway Av", "200"));
        testCases.put("Calle Aduana, 29", getAddressDTO("Calle Aduana", "29"));
        testCases.put("Calle 39 No 1540", getAddressDTO("Calle 39", "No 1540"));
        testCases.put("Rosenheimer strasse b", getAddressDTO("Rosenheimer strasse b", ""));
        return testCases;
    }

    private AddressDTO getAddressDTO(String street, String houseNumber) {
        return new AddressDTO(street, houseNumber);
    }
}

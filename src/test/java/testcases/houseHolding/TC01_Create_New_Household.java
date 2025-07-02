package testcases.houseHolding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;
import pojo.CreateHousehold;
import java.io.File;
import java.util.Map;

import java.io.File;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static paths.Paths.HOUSEHOLD_SCHEMA_PATH;
import static util.Enpoint.HOUSEHOLDS;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;
import static util.Utililty.*;

public class TC01_Create_New_Household extends TestBase {

    String fullName = generateRandomFullName();

    @Test(priority = 1, description = "Create new book with valid data")
    public void createNewBook_P() throws JsonProcessingException {
        CreateHousehold household = new CreateHousehold().setName(fullName);

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        Response response = given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(household))
                .when().post(HOUSEHOLDS)
                .then()
                .statusCode(201)
                .time(lessThan(2000L))
                .body("id", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchema(new File(HOUSEHOLD_SCHEMA_PATH)))
                .extract().response();

        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 5000, "Response time should be < 5000ms, but was: " + responseTime);

        TestBase.bookID = response.jsonPath().getInt("id");
        Assert.assertNotNull(bookID, "Book ID should not be null");

        Assert.assertTrue(bookID > 0, "Book ID should be a positive number");

        String actualName = response.jsonPath().getString("name");
        Assert.assertNotNull(actualName, "Title should not be null");
        Assert.assertFalse(actualName.isEmpty(), "Title should not be empty");
        Assert.assertEquals(actualName, fullName, "Title should match the request");

        String createdAt = response.jsonPath().getString("createdAt");
        Assert.assertNotNull(createdAt, "createdAt should not be null or empty");
        Assert.assertFalse(createdAt.isEmpty(), "createdAt should not be empty");

        String updatedAt = response.jsonPath().getString("updatedAt");
        Assert.assertNotNull(updatedAt, "updatedAt should not be null or empty");
        Assert.assertFalse(updatedAt.isEmpty(), "updatedAt should not be empty");
    }

}

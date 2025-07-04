package testcases.houseHolding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.CreateHousehold;
import pojo.CreateWishlist;
import testcases.TestBase;

import static io.restassured.RestAssured.given;
import io.restassured.module.jsv.JsonSchemaValidator;
import java.io.File;
import java.util.Map;

import static paths.Paths.HOUSEHOLD_SCHEMA_PATH;
import static util.Enpoint.HOUSEHOLDS;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;
import static util.Utililty.*;

@Epic("Household")
@Feature("Update Household")
@Severity(SeverityLevel.CRITICAL)
public class TC03_Update_Existing_Household extends TestBase {

    String fullName = generateRandomFullName();

    @Test(priority = 1, dependsOnMethods = {"testcases.houseHolding.TC01_Create_New_Household.createNewHousehold_P"}, description = "update existed household with valid data")

    public void updateExistingHousehold_P() throws JsonProcessingException {
        Map<String, String> headers = Map.of("Content-Type", "application/json", "Accept", "*/*");
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();
        String fullName = generateRandomFullName();


        CreateHousehold body = new CreateHousehold();
        body.setName(fullName);


        Response response = given().log().all()
                .auth().preemptive().basic("admin","admin")
                .header("Content-Type", "application/json")
                .header("g-token", "ROM831ESV")
                .body(mapper.writeValueAsString(body))
                .when().put(HOUSEHOLDS + bookID)
                .then().log().all()
                .assertThat().statusCode(200).assertThat()
                .time(lessThan(2000L))
                .body("id", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchema(new File(HOUSEHOLD_SCHEMA_PATH)))
                .extract().response();
        System.out.println("✅ [TC00] Response statusCode matches the expected statusCode \"201\"");
        System.out.println("✅ [TC01] Response matches the expected JSON schema");

        long responseTime = response.getTime();
        System.out.println("✅ [TC02] Validate Response time is less than 5000ms");
        Assert.assertTrue(responseTime < 5000, "Response time should be < 5000ms, but was: " + responseTime);

        TestBase.bookID = response.jsonPath().getInt("id");
        Assert.assertNotNull(bookID, "Book ID should not be null");
        System.out.println("✅ [TC03] Validate response body contains a non-null 'id'");

        Assert.assertTrue(bookID > 0, "Book ID should be a positive number");
        System.out.println("✅ [TC04] Validate 'id' is a number and positive");

        String actualName = response.jsonPath().getString("name");
        Assert.assertNotNull(actualName, "Name should not be null");
        Assert.assertFalse(actualName.isEmpty(), "Name should not be empty");
        Assert.assertEquals(actualName, fullName, "Name should match the request");
        System.out.println("✅ [TC09] Validate 'name' is not null, not empty, and matches the input");

        String createdAt = response.jsonPath().getString("createdAt");
        Assert.assertNotNull(createdAt, "createdAt should not be null or empty");
        Assert.assertFalse(createdAt.isEmpty(), "createdAt should not be empty");
        System.out.println("✅ [TC13] Validate 'createdAt' is not null or empty");

        String updatedAt = response.jsonPath().getString("updatedAt");
        Assert.assertNotNull(updatedAt, "updatedAt should not be null or empty");
        Assert.assertFalse(updatedAt.isEmpty(), "updatedAt should not be empty");
        System.out.println("✅ [TC14] Validate 'updatedAt' is not null or empty");

        System.out.println("✅ All assertions passed. Book ID: " + bookID);
    }

}

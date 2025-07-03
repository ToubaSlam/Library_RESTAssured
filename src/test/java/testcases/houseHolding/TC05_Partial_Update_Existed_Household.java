package testcases.houseHolding;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;
import io.restassured.module.jsv.JsonSchemaValidator;
import java.io.File;
import static paths.Paths.HOUSEHOLD_SCHEMA_PATH;
import static util.Enpoint.HOUSEHOLDS;
import static model.CreateBookBody.getCreateHouseholdBody;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;
import static util.Utililty.*;

public class TC05_Partial_Update_Existed_Household extends TestBase {

    String fullName = generateRandomFullName();

    @Test(priority = 1, description = "partial update existed household with valid data")

    public void partialUpdateExistingBook_P() {
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .header("g-token", "ROM831ESV")
                .auth().preemptive().basic("admin","admin")
                .body(getCreateHouseholdBody(fullName))
                .when().patch(HOUSEHOLDS + bookID)
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

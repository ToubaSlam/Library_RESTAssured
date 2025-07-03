package testcases.wishList;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;
import io.restassured.module.jsv.JsonSchemaValidator;
import java.io.File;
import static paths.Paths.WISHLIST_SCHEMA_PATH;
import static util.Enpoint.WISHLISTS;
import static model.CreateBookBody.getCreateWishlistBody;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;
import static util.Utililty.*;

public class TC05_Partial_Update_Existed_Wishlist extends TestBase {

    String firstName = getRandomFirstName();
    String lastName = generateRandomLastName();
    String email = generateRandomEmail("example.com", ".");

    @Test(priority = 1, description = "Partial update existing wishlist with valid data")

    public void partialUpdateExistingWishlist_P() {
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .header("g-token", "ROM831ESV")
                .auth().preemptive().basic("admin","admin")
                .body(getCreateWishlistBody(firstName, lastName, email))
                .when().patch(WISHLISTS + bookID)
                .then().log().all()
                .assertThat().statusCode(200).assertThat()
                .time(lessThan(2000L))
                .body("id", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchema(new File(WISHLIST_SCHEMA_PATH)))
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

        String actualFirstName = response.jsonPath().getString("firstName");
        Assert.assertNotNull(actualFirstName, "First name should not be null");
        Assert.assertFalse(actualFirstName.isEmpty(), "First name should not be empty");
        Assert.assertEquals(actualFirstName, firstName, "First name should match the request");
        System.out.println("✅ [TC09] Validate 'firstName' is not null, not empty, and matches the input");

        String actualLastName = response.jsonPath().getString("lastName");
        Assert.assertNotNull(actualLastName, "Last name should not be null");
        Assert.assertFalse(actualLastName.isEmpty(), "Last name should not be empty");
        Assert.assertEquals(actualLastName, lastName, "Last name should match the request");
        System.out.println("✅ [TC10] Validate 'lastName' is not null, not empty, and matches the input");

        String actualEmail = response.jsonPath().getString("email");
        Assert.assertNotNull(actualEmail, "Email should not be null");
        Assert.assertFalse(actualEmail.isEmpty(), "Email should not be empty");
        Assert.assertEquals(actualEmail, email, "Email should match the request");
        System.out.println("✅ [TC11] Validate 'email' is not null, not empty, and matches the input");

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

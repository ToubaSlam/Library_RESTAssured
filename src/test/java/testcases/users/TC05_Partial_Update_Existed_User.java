package testcases.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;
import pojo.CreateUser;

import java.io.File;
import java.util.Map;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;
import static paths.Paths.USER_SCHEMA_PATH;
import static util.Enpoint.USERS;
import static util.Utililty.*;

public class TC05_Partial_Update_Existed_User extends TestBase {

    String firstName = generateRandomFirstName();
    String lastName = generateRandomLastName();
    String email = generateRandomEmail();

    @Test(priority = 1, description = "Create new book with valid data")

    public void partialUpdateExistingBook_P() throws JsonProcessingException {
        CreateUser user = new CreateUser()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email);

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        Response response = given()
                .spec(createRequestSpecification(headers, queryParameters))
                .auth().preemptive().basic("admin","admin")
                .body(mapper.writeValueAsString(user))
                .when().patch(USERS + bookID)
                .then()
                .assertThat().statusCode(200).assertThat()
                .time(lessThan(2000L))
                .body("id", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchema(new File(USER_SCHEMA_PATH)))
                .extract().response();

        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 5000, "Response time should be < 5000ms, but was: " + responseTime);

        bookID = response.jsonPath().getInt("id");
        Assert.assertNotNull(bookID, "Book ID should not be null");
        Assert.assertTrue(bookID > 0, "Book ID should be a positive number");

        String actualFirstName = response.jsonPath().getString("firstName");
        Assert.assertNotNull(actualFirstName);
        Assert.assertFalse(actualFirstName.isEmpty());
        Assert.assertEquals(actualFirstName, firstName);

        String actualLastName = response.jsonPath().getString("lastName");
        Assert.assertNotNull(actualLastName);
        Assert.assertFalse(actualLastName.isEmpty());
        Assert.assertEquals(actualLastName, lastName);

        String actualEmail = response.jsonPath().getString("email");
        Assert.assertNotNull(actualEmail);
        Assert.assertFalse(actualEmail.isEmpty());
        Assert.assertEquals(actualEmail, email);

        Assert.assertNotNull(response.jsonPath().getString("createdAt"));
        Assert.assertNotNull(response.jsonPath().getString("updatedAt"));
    }

}

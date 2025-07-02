package testcases.houseHolding;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.util.Map;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static util.Enpoint.HOUSEHOLDS;
import static util.Utililty.*;

public class TC07_Delete_A_Household extends TestBase {

    @Test(priority = 1, description = "Create new book with valid data")

    public void deleteExistedBook() {
        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();

        Response response = given()
                .spec(createRequestSpecification(headers, queryParameters))
                .auth().preemptive().basic("admin","admin")
                .when().delete(HOUSEHOLDS + bookID)
                .then()
                .assertThat().statusCode(204).assertThat()
                .time(lessThan(2000L))
                .extract().response();

        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 5000, "Response time should be < 5000ms, but was: " + responseTime);

//        TestBase.bookID = response.jsonPath().getInt("id");
//        Assert.assertNotNull(bookID, "Book ID should not be null");
//        System.out.println("✅ [TC03] Validate response body contains a non-null 'id'");

    }

}

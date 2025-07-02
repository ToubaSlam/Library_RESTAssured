package testcases.wishList;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.util.Map;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static util.Enpoint.WISHLISTS;

public class TC08_Get_A_Wishlist extends TestBase {

    @Test(priority = 1, description = "Check that GET after DELETE returns 404 Not Found for deleted book")
    public void checkGetBooksWorking_P(){
        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();

        Response response = given()
                .spec(createRequestSpecification(headers, queryParameters))
                .when().get(WISHLISTS + bookID)
                .then()
                .assertThat().statusCode(404).assertThat()
                .time(lessThan(2000L))
                .extract().response();
        // 🧠 Optional: assert the response body message (if your API sends a message like "Book not found")
        String responseBody = response.getBody().asString();
        assert responseBody.contains("not found") || responseBody.contains("Book") :
                "❌ Expected a 'not found' message, but got: " + responseBody;

        // ✅ Optional: schema validation if your 404 error has a defined schema
        // .body(matchesJsonSchemaInClasspath("error-schema.json"));

    }

}

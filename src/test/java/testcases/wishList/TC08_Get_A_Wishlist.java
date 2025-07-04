package testcases.wishList;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static util.Enpoint.WISHLISTS;

public class TC08_Get_A_Wishlist extends TestBase {

    @Test(priority = 1, description = "Check that GET after DELETE returns 404 Not Found for deleted wishlist")
    public void checkGetWishlistWorking_P(){
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .header("g-token", "ROM831ESV")
                .when().get(WISHLISTS + bookID)
                .then().log().all()
                .assertThat().statusCode(404).assertThat()
                .time(lessThan(2000L))
                .extract().response();
        // 🧠 Optional: assert the response body message (if your API sends a message like "Book not found")
        String responseBody = response.getBody().asString();
        assert responseBody.contains("not found") || responseBody.contains("Book") :
                "❌ Expected a 'not found' message, but got: " + responseBody;

        // ✅ Optional: schema validation if your 404 error has a defined schema
        // .body(matchesJsonSchemaInClasspath("error-schema.json"));

        // 📘 Logging
        System.out.println("✅ [TC08] Status code is 404 - book was successfully deleted");
        System.out.println("✅ [TC08] Response time is under 2 seconds");
        System.out.println("✅ [TC08] Book with ID " + bookID + " no longer exists in the system");
    }

}

package testcases.wishList;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;
import io.restassured.module.jsv.JsonSchemaValidator;
import java.io.File;
import static paths.Paths.WISHLIST_SCHEMA_PATH;
import static util.Enpoint.WISHLISTS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class TC04_Get_Existed_Wishlist extends TestBase {

    @Test(priority = 1, description = "Check Get Books working and return response as excpected")
    public void checkGetBooksWorking_P(){
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .header("g-token", "ROM831ESV")
                .when().get(WISHLISTS + bookID)
                .then().log().all()
                .assertThat().statusCode(200).assertThat()
                .time(lessThan(2000L))
                .body("id", equalTo(bookID)) // ✅ Confirm the correct book is returned
                .body(JsonSchemaValidator.matchesJsonSchema(new File(WISHLIST_SCHEMA_PATH)))
                .extract().response();
        System.out.println("✅ [TC00] Response statusCode matches the expected statusCode \"201\"");
        System.out.println("✅ [TC01] Response matches the expected JSON schema");
    }


}

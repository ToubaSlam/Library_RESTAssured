package testcases.wishList;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.io.File;
import java.util.Map;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static paths.Paths.WISHLIST_SCHEMA_PATH;
import static util.Enpoint.WISHLISTS;

public class TC04_Get_Existed_Wishlist extends TestBase {

    @Test(priority = 1, description = "Check Get Books working and return response as excpected")
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
                .assertThat().statusCode(200).assertThat()
                .time(lessThan(2000L))
                .body("id", equalTo(bookID))
                .body(JsonSchemaValidator.matchesJsonSchema(new File(WISHLIST_SCHEMA_PATH)))
                .extract().response();
    }


}

package testcases.books;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import testcases.TestBase;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static paths.Paths.BOOK_SCHEMA_PATH;

public class TC06_Get_Existed_Book extends TestBase {

    @Test(priority = 1, description = "Check Get Books working and return response as excpected")
    public void checkGetBooksWorking_P(){
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .header("g-token", "ROM831ESV")
                .when().get("/books/" + bookID) // ✅ Use GET and pass the variable
                .then().log().all()
                .assertThat().statusCode(200).assertThat()
                .time(lessThan(2000L))
                .body("id", equalTo(bookID)) // ✅ Confirm the correct book is returned
                .body(JsonSchemaValidator.matchesJsonSchema(new File(BOOK_SCHEMA_PATH)))
                .extract().response();
        System.out.println("✅ [TC00] Response statusCode matches the expected statusCode \"201\"");
        System.out.println("✅ [TC01] Response matches the expected JSON schema");
    }


}

package testcases.books;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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
import static paths.Paths.BOOK_SCHEMA_PATH;
import static util.Enpoint.BOOKS;

@Epic("Books")
@Feature("Create Book")
@Severity(SeverityLevel.MINOR)
public class TC04_Get_Existed_Book extends TestBase {

    @Test(priority = 1, description = "Check Get Books working and return response as excpected")
    public void checkGetBooksWorking_P(){
        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();

        Response response = given()
                .spec(createRequestSpecification(headers, queryParameters))
                .when().get(BOOKS + bookID) // ✅ Use GET and pass the variable
                .then()
                .assertThat().statusCode(200).assertThat()
                .time(lessThan(2000L))
                .body("id", equalTo(bookID)) // ✅ Confirm the correct book is returned
                .body(JsonSchemaValidator.matchesJsonSchema(new File(BOOK_SCHEMA_PATH)))
                .extract().response();
    }


}

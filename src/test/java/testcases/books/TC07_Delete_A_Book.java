package testcases.books;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;
import pojo.CreateBook;
import java.util.Map;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static util.Enpoint.BOOKS;
import static util.Utililty.*;
@Epic("Books")
@Feature("Delete Book")
@Severity(SeverityLevel.CRITICAL)
public class TC07_Delete_A_Book extends TestBase {

    String isbn = generateRandomIsbn();
    String releaseDate = generateRandomPastDate();
    String title = generateRandomTitle();
    String author = generateRandomAuthor();

    @Test(dependsOnMethods = {"testcases.books.TC05_Partial_Update_A_Book.partialUpdateExistingBook_P"})

    public void deleteExistedBook() throws JsonProcessingException {
        CreateBook book = new CreateBook()
                .setTitle(title)
                .setAuthor(author)
                .setIsbn(isbn)
                .setReleaseDate(releaseDate);

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        Response response = given()
                .spec(createRequestSpecification(headers, queryParameters))
                .auth().preemptive().basic("admin", "admin")
                .body(mapper.writeValueAsString(book))
                .when().delete(BOOKS + bookID)
                .then()
                .statusCode(204)
                .time(lessThan(2000L))
                .extract().response();

        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 5000, "Response time should be < 5000ms, but was: " + responseTime);

    }

}

package testcases.books;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;
import pojo.CreateBook;
import java.io.File;
import java.util.Map;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;
import static paths.Paths.BOOK_SCHEMA_PATH;
import static util.Enpoint.BOOKS;
import static util.Utililty.*;

public class TC03_Update_Existing_Book extends TestBase {

    String isbn = generateRandomIsbn();
    String releaseDate = generateRandomPastDate();
    String title = generateRandomTitle();
    String author = generateRandomAuthor();

    @Test(dependsOnMethods = {"testcases.books.TC01_CreateNewBook.createNewBook_P"})

    public void updateExistingBook_P() throws JsonProcessingException {
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
                .when().put(BOOKS + bookID)
                .then()
                .statusCode(200)
                .time(lessThan(2000L))
                .body("id", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchema(new File(BOOK_SCHEMA_PATH)))
                .extract().response();

        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 5000, "Response time should be < 5000ms, but was: " + responseTime);

        bookID = response.jsonPath().getInt("id");
        Assert.assertNotNull(bookID, "Book ID should not be null");
        Assert.assertTrue(bookID > 0, "Book ID should be a positive number");

        Assert.assertEquals(response.jsonPath().getString("title"), title);
        Assert.assertEquals(response.jsonPath().getString("author"), author);
        Assert.assertEquals(response.jsonPath().getString("isbn"), isbn);
        Assert.assertEquals(response.jsonPath().getString("releaseDate"), releaseDate);

        Assert.assertNotNull(response.jsonPath().getString("createdAt"));
        Assert.assertNotNull(response.jsonPath().getString("updatedAt"));
    }

}

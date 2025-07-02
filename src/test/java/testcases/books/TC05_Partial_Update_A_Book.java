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

public class TC05_Partial_Update_A_Book extends TestBase {

    String isbn = generateRandomIsbn();
    String releaseDate = generateRandomPastDate();
    String title = generateRandomTitle();
    String author = generateRandomAuthor();

    @Test(dependsOnMethods = {"testcases.books.TC03_Update_Existing_Book.updateExistingBook_P"})
    public void partialUpdateExistingBook_P() throws JsonProcessingException {
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
                .when().patch(BOOKS + bookID)
                .then()
                .statusCode(200)
                .time(lessThan(2000L))
                .body("id", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchema(new File(BOOK_SCHEMA_PATH)))
                .extract().response();

        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 5000, "Response time should be < 5000ms, but was: " + responseTime);

        TestBase.bookID = response.jsonPath().getInt("id");
        Assert.assertNotNull(bookID, "Book ID should not be null");

        Assert.assertTrue(bookID > 0, "Book ID should be a positive number");

        String actualTitle = response.jsonPath().getString("title");
        Assert.assertNotNull(actualTitle, "Title should not be null");
        Assert.assertFalse(actualTitle.isEmpty(), "Title should not be empty");
        Assert.assertEquals(actualTitle, title, "Title should match the request");

        String actualAuthor = response.jsonPath().getString("author");
        Assert.assertNotNull(actualAuthor, "Author should not be null");
        Assert.assertFalse(actualAuthor.isEmpty(), "Author should not be empty");
        Assert.assertEquals(actualAuthor, author, "Author should match the request");

        String actualIsbn = response.jsonPath().getString("isbn");
        Assert.assertNotNull(actualIsbn, "ISBN should not be null");
        Assert.assertFalse(actualIsbn.isEmpty(), "ISBN should not be empty");
        Assert.assertEquals(actualIsbn, isbn, "ISBN should match the request");

        String actualReleaseDate = response.jsonPath().getString("releaseDate");
        Assert.assertNotNull(actualReleaseDate, "ReleaseDate should not be null");
        Assert.assertFalse(actualReleaseDate.isEmpty(), "ReleaseDate should not be empty");
        Assert.assertEquals(actualReleaseDate, releaseDate, "ReleaseDate should match the request");

        String createdAt = response.jsonPath().getString("createdAt");
        Assert.assertNotNull(createdAt, "createdAt should not be null or empty");
        Assert.assertFalse(createdAt.isEmpty(), "createdAt should not be empty");

        String updatedAt = response.jsonPath().getString("updatedAt");
        Assert.assertNotNull(updatedAt, "updatedAt should not be null or empty");
        Assert.assertFalse(updatedAt.isEmpty(), "updatedAt should not be empty");
    }

}

package testcases.books;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import paths.Paths;
import pojo.CreateBook;
import retryTest.Retry;
import testcases.TestBase;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static util.Enpoint.BOOKS;
import static util.Utililty.*;

@Epic("Books")
@Feature("Create Book")
@Severity(SeverityLevel.CRITICAL)
public class TC01_CreateNewBook extends TestBase {

    CreateBook book;
    String title = generateRandomTitle();
    String author = generateRandomAuthor();
    String isbn = generateRandomIsbn();
    String releaseDate = generateRandomPastDate();

    public TC01_CreateNewBook() throws IOException, ParseException {}

    @Story("Valid Book Creation")
    @Test(priority = 1, description = "TC01 - Create new book with valid data", retryAnalyzer = Retry.class)
    public void TC01_createNewBook_ShouldReturnValidResponse_P() throws JsonProcessingException {
        book = new CreateBook();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setReleaseDate(releaseDate);

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        Response response = given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(book))
                .when()
                .post(BOOKS)
                .then()
                .statusCode(201)
                .time(lessThan(5000L))
                .body(JsonSchemaValidator.matchesJsonSchema(new File(Paths.BOOK_SCHEMA_PATH)))
                .extract()
                .response();

        bookID = response.jsonPath().getInt("id");
        Assert.assertNotNull(bookID, "Book ID should not be null");
        Assert.assertTrue(bookID > 0, "Book ID should be a positive number");

        Assert.assertEquals(response.jsonPath().getString("title"), title, "Title should match input");
        Assert.assertEquals(response.jsonPath().getString("author"), author, "Author should match input");
        Assert.assertEquals(response.jsonPath().getString("isbn"), isbn, "ISBN should match input");
        Assert.assertEquals(response.jsonPath().getString("releaseDate"), releaseDate, "Release date should match input");

        Assert.assertNotNull(response.jsonPath().getString("createdAt"), "createdAt should not be null");
        Assert.assertNotNull(response.jsonPath().getString("updatedAt"), "updatedAt should not be null");

        System.out.println("✅ Book created successfully. ID: " + bookID);
    }
}

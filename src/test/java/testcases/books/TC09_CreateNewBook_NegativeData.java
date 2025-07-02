package testcases.books;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;
import testcases.TestBase;
import pojo.CreateBook;

import java.util.Map;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;
import static util.Enpoint.BOOKS;

public class TC09_CreateNewBook_NegativeData extends TestBase {

    /**
     * Create book with valid title and author but invalid ISBN (non numeric).
     * Expect 400 Bad Request.
     */
    @Test(priority = 1, description = "Create book with invalid ISBN")
    public void createBook_InvalidIsbn_N() throws JsonProcessingException {
        CreateBook book = new CreateBook()
                .setTitle("Invalid ISBN Book")
                .setAuthor("Author")
                .setIsbn("invalid_isbn")
                .setReleaseDate("2000-01-01");

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(book))
                .when()
                .post(BOOKS)
                .then()
                .statusCode(400)
                .body(containsString("isbn"))
                .time(lessThan(5000L));
    }

    /**
     * Create book with missing title.
     * Expect 400 Bad Request.
     */
    @Test(priority = 2, description = "Create book with missing title")
    public void createBook_MissingTitle_N() throws JsonProcessingException {
        CreateBook book = new CreateBook()
                .setTitle("")
                .setAuthor("Author")
                .setIsbn("1234567890123")
                .setReleaseDate("2000-01-01");

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(book))
                .when()
                .post(BOOKS)
                .then()
                .statusCode(400)
                .body(containsString("title"))
                .time(lessThan(5000L));
    }

    /**
     * Create book with future release date.
     * Expect 400 Bad Request.
     */
    @Test(priority = 3, description = "Create book with future release date")
    public void createBook_FutureReleaseDate_N() throws JsonProcessingException {
        CreateBook book = new CreateBook()
                .setTitle("Future Date Book")
                .setAuthor("Author")
                .setIsbn("1234567890123")
                .setReleaseDate("2999-12-31");

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(book))
                .when()
                .post(BOOKS)
                .then()
                .statusCode(400)
                .body(containsString("releaseDate"))
                .time(lessThan(5000L));
    }

    /**
     * Create book with extremely long title.
     * Expect 400 Bad Request.
     */
    @Test(priority = 4, description = "Create book with very long title")
    public void createBook_TitleTooLong_N() throws JsonProcessingException {
        String longTitle = "A".repeat(500);
        CreateBook book = new CreateBook()
                .setTitle(longTitle)
                .setAuthor("Author")
                .setIsbn("1234567890123")
                .setReleaseDate("2000-01-01");

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(book))
                .when()
                .post(BOOKS)
                .then()
                .statusCode(400)
                .body(containsString("title"))
                .time(lessThan(5000L));
    }
}

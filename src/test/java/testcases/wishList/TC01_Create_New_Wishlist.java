package testcases.wishList;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static model.CreateBookBody.getCreateBookBody;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;
import static util.Utililty.*;

public class TC01_Create_New_Wishlist extends TestBase {

    String isbn = generateRandomIsbn();
    String releaseDate = generateRandomPastDate();
    String title = generateRandomTitle();
    String author = generateRandomAuthor();

    @Test(priority = 1, description = "Create new book with valid data")

    public void createNewBook_P() {
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .header("g-token", "ROM831ESV")
                .body(getCreateBookBody(title, author, isbn, releaseDate))
                .when().post("/books")
                .then().log().all()
                .assertThat().statusCode(201).assertThat()
                .time(lessThan(2000L))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath("schema/book-schema.json"))
                .extract().response();
        System.out.println("✅ [TC00] Response statusCode matches the expected statusCode \"201\"");
        System.out.println("✅ [TC01] Response matches the expected JSON schema");

        long responseTime = response.getTime();
        System.out.println("✅ [TC02] Validate Response time is less than 5000ms");
        Assert.assertTrue(responseTime < 5000, "Response time should be < 5000ms, but was: " + responseTime);

        TestBase.bookID = response.jsonPath().getInt("id");
        Assert.assertNotNull(bookID, "Book ID should not be null");
        System.out.println("✅ [TC03] Validate response body contains a non-null 'id'");

        Assert.assertTrue(bookID > 0, "Book ID should be a positive number");
        System.out.println("✅ [TC04] Validate 'id' is a number and positive");

        String actualTitle = response.jsonPath().getString("title");
        Assert.assertNotNull(actualTitle, "Title should not be null");
        Assert.assertFalse(actualTitle.isEmpty(), "Title should not be empty");
        Assert.assertEquals(actualTitle, title, "Title should match the request");
        System.out.println("✅ [TC09] Validate 'title' is not null, not empty, and matches the input");

        String actualAuthor = response.jsonPath().getString("author");
        Assert.assertNotNull(actualAuthor, "Author should not be null");
        Assert.assertFalse(actualAuthor.isEmpty(), "Author should not be empty");
        Assert.assertEquals(actualAuthor, author, "Author should match the request");
        System.out.println("✅ [TC10] Validate 'author' is not null, not empty, and matches the input");

        String actualIsbn = response.jsonPath().getString("isbn");
        Assert.assertNotNull(actualIsbn, "ISBN should not be null");
        Assert.assertFalse(actualIsbn.isEmpty(), "ISBN should not be empty");
        Assert.assertEquals(actualIsbn, isbn, "ISBN should match the request");
        System.out.println("✅ [TC11] Validate 'isbn' is not null, not empty, and matches the input");

        String actualReleaseDate = response.jsonPath().getString("releaseDate");
        Assert.assertNotNull(actualReleaseDate, "ReleaseDate should not be null");
        Assert.assertFalse(actualReleaseDate.isEmpty(), "ReleaseDate should not be empty");
        Assert.assertEquals(actualReleaseDate, releaseDate, "ReleaseDate should match the request");
        System.out.println("✅ [TC12] Validate 'releaseDate' is not null, not empty, and matches the input");

        String createdAt = response.jsonPath().getString("createdAt");
        Assert.assertNotNull(createdAt, "createdAt should not be null or empty");
        Assert.assertFalse(createdAt.isEmpty(), "createdAt should not be empty");
        System.out.println("✅ [TC13] Validate 'createdAt' is not null or empty");

        String updatedAt = response.jsonPath().getString("updatedAt");
        Assert.assertNotNull(updatedAt, "updatedAt should not be null or empty");
        Assert.assertFalse(updatedAt.isEmpty(), "updatedAt should not be empty");
        System.out.println("✅ [TC14] Validate 'updatedAt' is not null or empty");

        System.out.println("✅ All assertions passed. Book ID: " + bookID);
    }

}

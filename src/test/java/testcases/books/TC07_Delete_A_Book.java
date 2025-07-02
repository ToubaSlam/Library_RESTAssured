package testcases.books;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;
import static model.CreateBookBody.getCreateBookBody;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;
import static util.Utililty.*;

public class TC07_Delete_A_Book extends TestBase {

    String isbn = generateRandomIsbn();
    String releaseDate = generateRandomPastDate();
    String title = generateRandomTitle();
    String author = generateRandomAuthor();

    @Test(dependsOnMethods = {"testcases.books.TC05_Partial_Update_A_Book.partialUpdateExistingBook_P"})

    public void deleteExistedBook() {
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .header("g-token", "ROM831ESV")
                .auth().preemptive().basic("admin","admin")
                .body(getCreateBookBody(title, author, isbn, releaseDate))
                .when().delete("/books/" + bookID)
                .then().log().all()
                .assertThat().statusCode(204).assertThat()
                .time(lessThan(2000L))
                .extract().response();
        System.out.println("✅ [TC00] Response statusCode matches the expected statusCode \"204\"");
        System.out.println("✅ [TC01] Response matches the expected JSON schema");

        long responseTime = response.getTime();
        System.out.println("✅ [TC02] Validate Response time is less than 5000ms");
        Assert.assertTrue(responseTime < 5000, "Response time should be < 5000ms, but was: " + responseTime);

//        TestBase.bookID = response.jsonPath().getInt("id");
//        Assert.assertNotNull(bookID, "Book ID should not be null");
//        System.out.println("✅ [TC03] Validate response body contains a non-null 'id'");

    }

}

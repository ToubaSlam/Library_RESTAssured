package testcases.wishList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import testcases.TestBase;
import pojo.CreateWishlist;

import java.util.Map;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;
import static util.Enpoint.WISHLISTS;

@Epic("Wishlist")
@Severity(SeverityLevel.CRITICAL)
public class TC09_CreateNewWishlist_NegativeData extends TestBase {

    /**
     * Create wishlist with invalid email.
     * Expect 400 Bad Request.
     */
    @Test(priority = 1, description = "Create wishlist with invalid email")
    public void createWishlist_InvalidEmail_N() throws JsonProcessingException {
        CreateWishlist wishlist = new CreateWishlist()
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("invalid_email");

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(wishlist))
                .when()
                .post(WISHLISTS)
                .then()
                .statusCode(400)
                .body(containsString("email"))
                .time(lessThan(5000L));
    }

    /**
     * Create wishlist with empty first name.
     * Expect 400 Bad Request.
     */
    @Test(priority = 2, description = "Create wishlist with empty first name")
    public void createWishlist_EmptyFirstName_N() throws JsonProcessingException {
        CreateWishlist wishlist = new CreateWishlist()
                .setFirstName("")
                .setLastName("Doe")
                .setEmail("john@example.com");

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(wishlist))
                .when()
                .post(WISHLISTS)
                .then()
                .statusCode(400)
                .body(containsString("firstName"))
                .time(lessThan(5000L));
    }

    /**
     * Create wishlist with null last name.
     * Expect 400 Bad Request.
     */
    @Test(priority = 3, description = "Create wishlist with null last name")
    public void createWishlist_NullLastName_N() throws JsonProcessingException {
        CreateWishlist wishlist = new CreateWishlist()
                .setFirstName("John")
                .setLastName(null)
                .setEmail("john@example.com");

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(wishlist))
                .when()
                .post(WISHLISTS)
                .then()
                .statusCode(400)
                .body(containsString("lastName"))
                .time(lessThan(5000L));
    }
}

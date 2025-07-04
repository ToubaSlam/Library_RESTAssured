package testcases.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import testcases.TestBase;
import pojo.CreateUser;

import java.util.Map;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;
import static util.Enpoint.USERS;

@Epic("Users")
@Severity(SeverityLevel.CRITICAL)
public class TC09_CreateNewUser_NegativeData extends TestBase {

    /**
     * Create user with invalid email format.
     * Expect 400 Bad Request.
     */
    @Test(priority = 1, description = "Create user with invalid email")
    public void createUser_InvalidEmail_N() throws JsonProcessingException {
        CreateUser user = new CreateUser()
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
                .body(mapper.writeValueAsString(user))
                .when()
                .post(USERS)
                .then()
                .statusCode(400)
                .body(containsString("email"))
                .time(lessThan(5000L));
    }

    /**
     * Create user with null first name.
     * Expect 400 Bad Request.
     */
    @Test(priority = 2, description = "Create user with null first name")
    public void createUser_NullFirstName_N() throws JsonProcessingException {
        CreateUser user = new CreateUser()
                .setFirstName(null)
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
                .body(mapper.writeValueAsString(user))
                .when()
                .post(USERS)
                .then()
                .statusCode(400)
                .body(containsString("firstName"))
                .time(lessThan(5000L));
    }

    /**
     * Create user with missing last name.
     * Expect 400 Bad Request.
     */
    @Test(priority = 3, description = "Create user with empty last name")
    public void createUser_EmptyLastName_N() throws JsonProcessingException {
        CreateUser user = new CreateUser()
                .setFirstName("John")
                .setLastName("")
                .setEmail("john@example.com");

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(user))
                .when()
                .post(USERS)
                .then()
                .statusCode(400)
                .body(containsString("lastName"))
                .time(lessThan(5000L));
    }
}

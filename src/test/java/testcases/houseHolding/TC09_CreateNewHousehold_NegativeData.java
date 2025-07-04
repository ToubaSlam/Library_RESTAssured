package testcases.houseHolding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import testcases.TestBase;
import pojo.CreateHousehold;

import java.util.Map;

import static builder.RequestBuilder.createRequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;
import static util.Enpoint.HOUSEHOLDS;

@Epic("Household")
@Severity(SeverityLevel.CRITICAL)
public class TC09_CreateNewHousehold_NegativeData extends TestBase {

    /**
     * Create household with empty name.
     * Expect 400 Bad Request.
     */
    @Test(priority = 1, description = "Create household with empty name")
    public void createHousehold_EmptyName_N() throws JsonProcessingException {
        CreateHousehold household = new CreateHousehold().setName("");

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(household))
                .when()
                .post(HOUSEHOLDS)
                .then()
                .statusCode(400)
                .body(containsString("name"))
                .time(lessThan(5000L));
    }

    /**
     * Create household with name too long.
     * Expect 400 Bad Request.
     */
    @Test(priority = 2, description = "Create household with long name")
    public void createHousehold_NameTooLong_N() throws JsonProcessingException {
        String longName = "A".repeat(300);
        CreateHousehold household = new CreateHousehold().setName(longName);

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(household))
                .when()
                .post(HOUSEHOLDS)
                .then()
                .statusCode(400)
                .body(containsString("name"))
                .time(lessThan(5000L));
    }

    /**
     * Create household with null name.
     * Expect 400 Bad Request.
     */
    @Test(priority = 3, description = "Create household with null name")
    public void createHousehold_NullName_N() throws JsonProcessingException {
        CreateHousehold household = new CreateHousehold().setName(null);

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "g-token", "ROM831ESV"
        );
        Map<String, String> queryParameters = Map.of();
        ObjectMapper mapper = new ObjectMapper();

        given()
                .spec(createRequestSpecification(headers, queryParameters))
                .body(mapper.writeValueAsString(household))
                .when()
                .post(HOUSEHOLDS)
                .then()
                .statusCode(400)
                .body(containsString("name"))
                .time(lessThan(5000L));
    }
}

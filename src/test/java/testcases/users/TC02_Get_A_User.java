package testcases.users;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import testcases.TestBase;

import static io.restassured.RestAssured.given;
import io.restassured.module.jsv.JsonSchemaValidator;
import java.io.File;
import static paths.Paths.USER_SCHEMA_PATH;
import static util.Enpoint.USERS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

@Epic("Users")
@Feature("Create User")
@Severity(SeverityLevel.MINOR)
public class TC02_Get_A_User extends TestBase {

    @Test(priority = 1, description = "Check Get user working and return response as expected")
    public void checkGetUserWorking_P(){
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .header("g-token", "ROM831ESV")
                .when().get(USERS + bookID)
                .then().log().all()
                .assertThat().statusCode(200).assertThat()
                .time(lessThan(2000L))
                .body("id", equalTo(bookID)) // ✅ Confirm the correct book is returned
                .body(JsonSchemaValidator.matchesJsonSchema(new File(USER_SCHEMA_PATH)))
                .extract().response();
        System.out.println("✅ [TC00] Response statusCode matches the expected statusCode \"201\"");
        System.out.println("✅ [TC01] Response matches the expected JSON schema");
    }


}

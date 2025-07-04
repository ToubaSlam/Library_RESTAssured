package testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.Matchers.lessThan;

@Listeners(ChainTestListener.class)
public class TestBase {

    public static int bookID; // ⬅️ Not just `static`, but `public static`
    Faker faker = new Faker();
//@BeforeSuite
//public void defineSuiteElements() throws IOException {
//
////    Utility.deleteFilesInFolder("allure-report");
//}
protected Map<String, String> headers = Map.of("Content-Type", "application/json", "Accept", "*/*");
    protected Map<String, String> queryParameters = Map.of();

    @Parameters("baseURL")
    @BeforeTest
    public void setBaseURL(String baseURL) {
        RestAssured.baseURI = baseURL;
    }
}
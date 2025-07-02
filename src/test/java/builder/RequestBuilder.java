package builder;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RequestBuilder {

    public static RequestSpecification createRequestSpecification(Map headers, Map query) {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addHeaders(headers)
                .addFormParams(query)
                .log(LogDetail.ALL)
                .build();
    }
}

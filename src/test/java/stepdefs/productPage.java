package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.ConfigReader;
import util.tokenStore;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class productPage {
    Response response;
    String baseURL = ConfigReader.get("base.url");
    String token;

    @Given("take token information")
    public void takeTokenInformation() {
        token = tokenStore.getInstance().getToken();
    }

    @When("open product endpoint")
    public void openProductEndpoint() {
        response = RestAssured
                .given()
                .baseUri(baseURL)
                .basePath("/products")
                .header("accessToken",token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get();
    }

    @Then("return limit count of products")
    public void returnLimitCountOfProducts() {
        assertEquals(HttpStatus.SC_OK,response.getStatusCode());
        JsonPath json = response.jsonPath();
        int limit = Integer.parseInt(json.getString("limit"));

        List<Integer> idlist= response.jsonPath().getList("products.id",Integer.class);
        assertEquals(idlist.size(),limit);
    }
}

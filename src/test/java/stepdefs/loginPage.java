package stepdefs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.ConfigReader;
import util.tokenStore;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class loginPage {

    String baseURL = ConfigReader.get("base.url");
    String username;
    String password;
    Response response;

    @Given("get user information from users endpoint")
    public void getUserInformationFromUsersEndpoint() {
        response = RestAssured
                .given()
                .baseUri(baseURL)
                .when()
                .get("/users");

        JsonPath json = response.jsonPath();
        username = json.getString("users[0].username");
        password = json.getString("users[0].password");
    }

    @When("user send request login endpoint")
    public void userSendRequestLoginEndpoint() {
        response = RestAssured
                .given()
                .baseUri(baseURL)
                .basePath("/auth/login")
                .contentType("application/json")
                .body("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }")
                .when()
                .post();
    }

    @Then("return success login and access token information")
    public void returnSuccessLoginAndAccessTokenInformation() {
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        response.then().body("accessToken",notNullValue());
        String accessToken = response.jsonPath().getString("accessToken");
        tokenStore.getInstance().setToken(accessToken);
    }

    @Given("get wrong user information")
    public void getWrongUserInformation() {
        response = RestAssured
                .given()
                .baseUri(baseURL)
                .when()
                .get("/users");

        JsonPath json = response.jsonPath();
        username = json.getString("users[0].firstname");
        password = json.getString("users[0].lastName");
    }

    @Then("return bad request {string}")
    public void returnBadRequestErrorMessage(String errorMessage) {
        assertEquals(HttpStatus.SC_BAD_REQUEST,response.getStatusCode());
        String resErrorMessage =response.jsonPath().getString("message");
        assertEquals(errorMessage,resErrorMessage);
    }

    @When("user send empty information request login endpoint")
    public void userSendEmptyInformationRequestLoginEndpoint() {
        response = RestAssured
                .given()
                .baseUri(baseURL)
                .basePath("/auth/login")
                .contentType("application/json")
                .body("{ \"username\": \"" + "" + "\", \"password\": \"" + "" + "\" }")
                .when()
                .post();
    }
}

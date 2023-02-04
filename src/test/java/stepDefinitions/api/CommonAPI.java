package stepDefinitions.api;

import hooks.api.HooksAPI;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import pojos.Dummy_Pojo.PojoDummyData;
import pojos.Dummy_Pojo.PojoDummyExpectedBody;
import pojos.Herokuapp_Pojo.PojoHerokuappBooking;
import pojos.Herokuapp_Pojo.PojoHerokuappBookingDates;
import pojos.Herokuapp_Pojo.PojoHerokuappExpectedBody;
import pojos.JsonPlace_Pojo.PojoJsonPlace;
import utilities.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class CommonAPI {

    public Response response;
    private RequestSpecification spec;

    PojoDummyData data;
    PojoDummyExpectedBody dummyExpectedBody;

    PojoHerokuappBookingDates bookingDates;
    PojoHerokuappBooking reqBody;
    PojoHerokuappExpectedBody expBody;
    PojoJsonPlace expData;

    PojoJsonPlace requestBody;
    @Before(order = 0)
    public void beforeAPIScnerio() {
        spec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("base_url_jsonplaceholder"))
                .build();
    }

    @Given("JsonPlaceHolder Api Put request icin gerekli URL ve Body hazirla")
    public void json_place_holder_api_put_request_icin_gerekli_url_ve_body_hazirla() {
       spec.pathParams("pp1","posts","pp2",70);

       requestBody = new PojoJsonPlace("Ahmet","Merhaba",10,70);

    }
    @Given("JsonPlaceHolder Api Put request icin Expected Data hazirla")
    public void json_place_holder_api_put_request_icin_expected_data_hazirla() {
       expData = requestBody;

    }
    @Given("JsonPlaceHolder Api Put request sonucunda donen Response'i kaydet")
    public void json_place_holder_api_put_request_sonucunda_donen_response_i_kaydet() {
        response = given().spec(spec).contentType(ContentType.JSON).when().body(requestBody).put("/{pp1}/{pp2}");

        response.prettyPrint();
    }
    @Given("JsonPlaceHolder Api Put request sonucunda donen Response'i assert et")
    public void json_place_holder_api_put_request_sonucunda_donen_response_i_assert_et() {

        PojoJsonPlace respPojo =response.as(PojoJsonPlace.class);

        assertEquals(expData.getTitle(),respPojo.getTitle());
        assertEquals(expData.getBody(),respPojo.getBody());
        assertEquals(expData.getUserId(),respPojo.getUserId());
        assertEquals(expData.getId(),respPojo.getId());

    }


    @Given("URL ve body hazirla")
    public void url_ve_body_hazirla() {
        spec.pathParam("pp1","booking");

        bookingDates = new PojoHerokuappBookingDates("2021-06-01","2021-06-10");
        reqBody = new PojoHerokuappBooking("Ahmet","Bulut",500,false,"wi-fi",bookingDates);

    }
    @Given("Expected Data hazirla")
    public void expected_data_hazirla() {
        expBody = new PojoHerokuappExpectedBody(24,reqBody);
    }
    @Given("Response'i kaydet")
    public void response_i_kaydet() {
        response = given().
                spec(spec).
                contentType(ContentType.JSON).
                when().
                body(reqBody).
                post("/{pp1}");
        response.prettyPrint();
    }
    @Given("Assertion")
    public void assertion() {
        PojoHerokuappExpectedBody respPojo = response.as(PojoHerokuappExpectedBody.class);

        assertEquals(expBody.getBooking().getFirstname(),respPojo.getBooking().getFirstname());
        assertEquals(expBody.getBooking().getLastname(),respPojo.getBooking().getLastname());
        assertEquals(expBody.getBooking().getTotalprice(),respPojo.getBooking().getTotalprice());
        assertEquals(expBody.getBooking().isDepositpaid(),respPojo.getBooking().isDepositpaid());
        assertEquals(expBody.getBooking().getAdditionalneeds(),respPojo.getBooking().getAdditionalneeds());
        assertEquals(expBody.getBooking().getBookingdates().getCheckin(),respPojo.getBooking().getBookingdates().getCheckin());
        assertEquals(expBody.getBooking().getBookingdates().getCheckout(),respPojo.getBooking().getBookingdates().getCheckout());

    }
    @Given("Dummy icin gerekli path params {string} hazirla")
    public void dummy_icin_gerekli_path_params_hazirla(String string) {
        spec.pathParams("pp1","employee","pp2",3);
    }

    @Given("Dummy get sorgusu icin Expected Data hazirla")
    public void dummy_get_sorgusu_icin_expected_data_hazirla() {
        data = new PojoDummyData(3,"Ashton Cox",86000,66,"");
        dummyExpectedBody = new PojoDummyExpectedBody("success",data,"Successfully! Record has been fetched.");
    }

    @Given("Get Request icin Response'i kaydet")
    public void get_request_icin_response_i_kaydet() {
        response = given().spec(spec).when().get("/{pp1}/{pp2}");
    }

    @Given("Donen Response'in Expected ile karsilastirmasini yap")
    public void donen_response_in_expected_ile_karsilastirmasini_yap() {
        PojoDummyExpectedBody respPojo = response.as(PojoDummyExpectedBody.class);

        assertEquals(dummyExpectedBody.getStatus(),respPojo.getStatus());
        assertEquals(dummyExpectedBody.getMessage(),respPojo.getMessage());
        assertEquals(dummyExpectedBody.getData().getEmployee_name(),respPojo.getData().getEmployee_name());
        assertEquals(dummyExpectedBody.getData().getEmployee_salary(),respPojo.getData().getEmployee_salary());
        assertEquals(dummyExpectedBody.getData().getEmployee_age(),respPojo.getData().getEmployee_age());
        assertEquals(dummyExpectedBody.getData().getProfile_image(),respPojo.getData().getProfile_image());
        assertEquals(dummyExpectedBody.getData().getId(),respPojo.getData().getId());
    }





    @Given("API user sets required path params {string}")
    public void api_user_sets_required_path_params(String rawPaths) {
        String[] paths = rawPaths.split(",");
        StringBuilder tempPath = new StringBuilder("{");
        for (int i = 0; i < paths.length; i++) {
            String key = "get" + i;
            spec.pathParam(key, paths[i].trim());
            tempPath.append(key + "}/{");
        }
        tempPath.deleteCharAt(tempPath.lastIndexOf("{"));
        tempPath.deleteCharAt(tempPath.lastIndexOf("/"));
        HooksAPI.fullPath = tempPath.toString();

        spec.queryParam("name","antin");
        spec.queryParam("key", ConfigReader.getProperty("key"));
        spec.queryParam("token",ConfigReader.getProperty("token"));

    }
    @When("Api user sends post requests and gets response")
    public void api_user_sends_post_requests_and_gets_response() {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .when()
                .post(HooksAPI.fullPath);
        HooksAPI.response = response;
        HooksAPI.response.prettyPrint();
    }

    @Given("API user sends requests and gets response")
    public void api_user_sends_requests_and_gets_response() {


        response = given()
                .headers("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .spec(spec)
                .when()
                //.body(HooksAPI.dataCredentials)
                .get(HooksAPI.fullPath);
        HooksAPI.response = response;
        //       response.prettyPrint();
        HooksAPI.response.prettyPrint();
    }

    @Then("API user verify that status code is {int}")
    public void api_user_verify_that_status_code_is(int statusCode) {
        HooksAPI.response.then().assertThat()
                .statusCode(statusCode)
                .contentType(ContentType.JSON);
//        response.prettyPrint();
        HooksAPI.response.prettyPrint();
    }

    @Then("API user verify response with matcher class")
    public void api_user_verify_response_with_matcher_class() {
        response.then().body("user.id", equalTo(453));
        response.then().body("user.username", equalTo("4545464646"));
        response.then().body("user.last_name", equalTo("testLastName"));
    }

    @Then("API user verify response with json path")
    public void api_user_verify_response_with_json_path() {
        JsonPath json = response.jsonPath();

        int actualId = json.getInt("user.customer_addresses[0].customer_id");
        Assert.assertEquals(453, actualId);

        String actualState = json.getString("user.customer_addresses[0].state");
        Assert.assertEquals("3953", actualState);
    }

    @Given("API user verify that response message is {string} v1")
    public void api_user_verify_that_response_message_is_v1(String message) {
        HooksAPI.response.then().body("msg", equalTo(message));
    }

    @Given("API user verify that response message is {string} v2")
    public void api_user_verify_that_response_message_is_v2(String message) {
        HooksAPI.response.then().body("message", equalTo(message));
    }

}

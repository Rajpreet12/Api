package api.Steps;

import api.TestOps.CreateUser_TestCase;
import api.TestOps.Morbidity_TestCase;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Morbidity_Steps {

	CreateUser_TestCase user= new CreateUser_TestCase();
	Morbidity_TestCase morbidity= new Morbidity_TestCase();
	String token;
	
	@When("User was authorized by token to make dietician request")
	public void user_was_authorized_by_token_to_make_dietician_request() {
		token = user.getDieticianToken();
	}
	
	@When("User sends HTTP request to get all morbidity")
	public void user_sends_HTTP_request_to_get_all_morbidity() {
		morbidity.getAllMorbidity(token);
	}

	@When("User sends HTTP request for morbidity with {string}")
	public void user_sends_HTTP_request_for_morbidity(String morbidityTestName) {
		String endpointTestName ="/"+morbidityTestName;
		morbidity.getAllMorbidityWithTestName(token, endpointTestName);
	}	
	
	@Then("Verify user receives {int} status")
	public void user_receives_status(Integer statusCode) {
		morbidity.verify_morbiditypost_status(statusCode);
	}
	
	@Then("Verify morbidity response data with TestName")
	public void verify_morbidity_response_data_with_TestName() {
		morbidity.verify_morbiditydata();
	}
		
	@Then("Verify morbidity response data with invalid TestName")
	public void verify_morbidity_response_data_with_invalid_TestName() {
		morbidity.verify_invalidmorbiditydata();
	}
}

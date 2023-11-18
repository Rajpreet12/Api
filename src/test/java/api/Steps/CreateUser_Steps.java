package api.Steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import api.TestOps.CreateUser_TestCase;
import api.Utils.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateUser_Steps {
	CreateUser_TestCase user= new CreateUser_TestCase();
	
	
	@Given("User creates token as Dietician and patient")
	public void user_creates_token_as_dietician_and_patient() {
	    
	}

	@When("User sends HTTP request with request body from the excel {string} and {int}")
	public void user_sends_http_request_with_request_body_from_the_excel_and(String sheetName, Integer rowNum) throws InvalidFormatException, IOException {
		ExcelReader reader=new ExcelReader();
		List<Map<String,String>> testData=reader.getData(".\\TesdData\\Wonder_Dietician.xlsx", sheetName);
		String password= testData.get(rowNum).get("password");
		String userLoginEmail= testData.get(rowNum).get("userLoginEmail");
		
		user.createToken(password, userLoginEmail);
	
	}

	@Then("User receives {int} created status")
	public void user_receives_created_status(Integer int1) {
	    System.out.println("The status code is "+ int1);
	}



}

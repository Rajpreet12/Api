package api.Steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import api.TestOps.CreateUser_TestCase;
import api.Utils.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateUser_Steps {
	ExcelReader reader;
	CreateUser_TestCase user= new CreateUser_TestCase();
	
	
	@Given("User creates token as Dietician and patient")
	public void user_creates_token_as_dietician_and_patient() {
	    
	}

	@When("User sends HTTP request with request body from the excel {string} and {int}")
	public void user_sends_http_request_with_request_body_from_the_excel_and(String sheetName, Integer rowNum) throws InvalidFormatException, IOException {
		try {
		ExcelReader reader=new ExcelReader();
		List<Map<String,String>> testData=reader.getData(".\\TesdData\\Wonder_Dietician.xlsx", sheetName);
		String password= testData.get(rowNum).get("password");
		String userLoginEmail= testData.get(rowNum).get("userLoginEmail");
		
		user.createToken(password, userLoginEmail);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	
	}

	
	@Then("User receives {int} status")
	public void user_receives_status(Integer statusCode) {
		user.verify_post_status(statusCode);
	}

	
// Create patient Id by Dietician

	@Given("User was authorized by {string}")
	public void user_was_authorized_by(String token) {
		user.getToken(token);
	}
	
	@When("User sends HTTP request with  request payload and under form-Data")
	public void user_sends_http_request_with_request_payload_and_under_form_data() {
	    user.createPatient();
	}

	
//Get all patients request

	@When("user sends HTTP request with valid endpoints {string} for all patients")
	public void user_sends_http_request_with_valid_endpoints_for_all_patients(String oAuth) {
		user.getAllPatients(oAuth);
	}
	
// Get Patients Morbidity Details

	@When("user sends HTTP request with valid endpoints {string} for morbidity {string} with {string}")
	public void user_sends_http_request_with_valid_endpoints_for_morbidity_with(String endpoints, String morbidityType, String token) {
		user.get_morbidity(endpoints, morbidityType,token);
	}
	
	
// Update patient by UserId
	@When("User sends HTTP request with  request payload and under form-Data with updated patient info")
	public void user_sends_http_request_with_request_payload_and_under_form_data_with_updated_patient_info() throws JsonMappingException, JsonProcessingException {
	   user.createPatient_UpdatePatient();
	}

// Delete Patient by UserId	
	@When("user sends HTTP request with valid endpoints {string} for IserId to delete the patient")
	public void user_sends_http_request_with_valid_endpoints_for_iser_id_to_delete_the_patient(String string) {
	    user.DelPatientId();
	}
	
// Get all patients and delete all patients	
	@When("User fetch all patients and delete all patients")
	public void user_fetch_all_patients_and_delete_all_patients() {
	    user.Get_DelPatients();
	}

	//Get Patients Morbidity Details and Retrieve Patient file by FileId As Patient
	
	@Given("User was authorized by {string} as PAtient")
	public void user_was_authorized_by_as_p_atient(String string) {
	    
	}

	@When("user sends HTTP request with valid endpoints {string} for morbidity {string} with {string} as PAtient")
	public void user_sends_http_request_with_valid_endpoints_for_morbidity_with_as_p_atient(String endpoints, String morbidityType, String token) {
	    user.get_morbidity_By_Patient(endpoints, morbidityType,token);
	}

//User logout
	
	@When("user sends HTTP request with valid endpoints {string}")
	public void user_sends_http_request_with_valid_endpoints(String oAuth) {
	    user.userLogout(oAuth);
	}

}

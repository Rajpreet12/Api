package api.Requests;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import api.POJO.CreatePatient_Payload;
import api.POJO.User_Payload;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class User_HTTPReq {
	
	RestAssured restassured;
	static Response response;
	static RequestSpecification requestSpecification= RestAssured.given().baseUri("https://dietician-dev-41d9a344a720.herokuapp.com/dietician");
	
	
	
//Using resourcebundle class  to get the endpoints	
	static ResourceBundle getUrl()
	{
		ResourceBundle endpoints= ResourceBundle.getBundle("endpoints");
		return endpoints;
	
	}
	
	public static Response createToken(User_Payload tokenPayload)
	{
		
		String CreateToken= getUrl().getString("CreateToken");
		response= RestAssured.given(requestSpecification).log().all()
		.auth().none().contentType(ContentType.JSON)
		.accept(ContentType.JSON).body(tokenPayload)
		.when().post(CreateToken).then().log().all().extract().response();
		
		return response;
		
	}
	
	public static Response createPatient(CreatePatient_Payload CreatePatient_Payload, String oAuthtoken)
	{
		String PostCreateNewPatient=getUrl().getString("PostCreateNewPatient");
		File report=new File(".\\TesdData\\HyperThyroid_Report_final (1).pdf");
		
		 Map<String, Object> patientInfo = new HashMap<>();
	        patientInfo.put("FirstName",CreatePatient_Payload.getFirstName() );
	        patientInfo.put("LastName", CreatePatient_Payload.getLastName());
	        patientInfo.put("ContactNumber", CreatePatient_Payload.getContactNumber());
	        patientInfo.put("Email", CreatePatient_Payload.getEmail());
	        patientInfo.put("Allergy", CreatePatient_Payload.getAllergy());
	        patientInfo.put("FoodCategory", CreatePatient_Payload.getFoodCategory());
	        patientInfo.put("DateOfBirth", CreatePatient_Payload.getDateOfBirth());
		
		response=RestAssured.given(requestSpecification).auth().oauth2(oAuthtoken)
		.multiPart("file",report)
		.multiPart("patientInfo",patientInfo)
		.contentType("multipart/form-data")
		.log().all()
		.when().post(PostCreateNewPatient).then().log().all().extract().response();
		
		return response;
	}
	
	public static Response getAllPatients(String oAuthtoken)
	{
		String GetallPatients= getUrl().getString("GetallPatients");
		return response=RestAssured.given(requestSpecification).auth().oauth2(oAuthtoken)
		.when().get(GetallPatients).then().log().all().extract().response();
	}

}

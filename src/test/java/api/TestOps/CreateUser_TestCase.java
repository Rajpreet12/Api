package api.TestOps;

import java.text.SimpleDateFormat;

import org.testng.Assert;

import com.github.javafaker.Faker;

import api.POJO.CreatePatient_Payload;
import api.POJO.User_Payload;
import api.Requests.User_HTTPReq;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateUser_TestCase {
	
	static Response response;
	static String strResponse;
	JsonPath jsPath;
	static String token_Dietician;
	static String token_Patient;
	Faker faker;
	int expec_statusCode;
	static String token;
	static SimpleDateFormat sdf;
	
	public void createToken(String password,String userLoginEmail)
	{
// creating object for the class User_Payload	
		User_Payload payload= new User_Payload();
		
//calling the set methods from teh class User_Payload using the obj payload
		payload.setPassword(password);
		payload.setUserLoginEmail(userLoginEmail);
		
		
//fetching the response from teh HTTP req of user
		response= User_HTTPReq.createToken(payload);
		System.out.println("The response is :"+ response);
		strResponse= response.then().log().all().extract().response().asString();
		jsPath=new JsonPath(strResponse);
		
 //extracting the token from the response
		if(token_Dietician==null)
		{
		 token_Dietician= jsPath.getString("token");
		 System.out.println("The token for dietician is :" + token_Dietician);
 //extracting the creator/userId who created this token
		 String userId_Dietician = jsPath.getString("userId");
		}
		else
		{
			System.out.println("The token for dietician is :" + token_Dietician);
			token_Patient=jsPath.getString("token");
			System.out.println("The token for Patient is :" + token_Patient);
 //extracting the creator/userId who created this token
			 String userId_Dietician = jsPath.getString("userId");
		}
	
	}
	
 //Authorization method
	
	public String getToken(String token)
	{
		System.out.println(token_Dietician);
		token=token_Dietician;
		System.out.println("The token is :"+ token);
		return token;
	}
	
 // Status verification
	
	public void verify_post_status(int actual_statusCode)
	{
		expec_statusCode=response.getStatusCode();
		Assert.assertEquals(expec_statusCode, actual_statusCode);
		
		Assert.assertEquals(response.header("Content-Type"), "application/json");
		Assert.assertEquals(response.header("Connection"), "keep-alive");
	}
	
	
 //Create PAtient by Dietician
	
	public void createPatient()
	{
	//DateFormat for fake DOB
		sdf= new SimpleDateFormat("yyyy-MM-dd");
		faker=new Faker();
		String token=getToken("token");
		CreatePatient_Payload CreatePatient_Payload=new CreatePatient_Payload();
		CreatePatient_Payload.setFirstName(faker.name().firstName());
		CreatePatient_Payload.setLastName(faker.name().lastName());	
		CreatePatient_Payload.setContactNumber(faker.number().numberBetween(1000000000L, 9999999999L));
		CreatePatient_Payload.setEmail("wonderdietician@gmail.com");
		CreatePatient_Payload.setAllergy("Peanuts");
		CreatePatient_Payload.setFoodCategory("Vegetarian");
		CreatePatient_Payload.setDateOfBirth(sdf.format(faker.date().birthday()));
		System.out.println("Checking the value of token "+token);
		User_HTTPReq.createPatient(CreatePatient_Payload, token);
		
	}

 //Get all patients
	 public static void getAllPatients(String token)
	 {
		 token=token_Dietician;
		 System.out.println("The bearer token is "+ token);
		 response=User_HTTPReq.getAllPatients(token);
		 strResponse=response.then().log().all().extract().response().asString();
		 System.out.println("The value of token is "+ token);
	 }
}

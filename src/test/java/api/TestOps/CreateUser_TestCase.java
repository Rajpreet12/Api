package api.TestOps;

import api.POJO.User_Payload;
import api.Requests.User_HTTPReq;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateUser_TestCase {
	
	Response response;
	String strResponse;
	JsonPath jsPath;
	static String token_Dietician;
	static String token_Patient;
	
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
		}
		else
		{
			System.out.println("The token for dietician is :" + token_Dietician);
			token_Patient=jsPath.getString("token");
			System.out.println("The token for Patient is :" + token_Patient);
		}
 //extracting the creator/userId who created this token
		
		
		
		
		
	}

}

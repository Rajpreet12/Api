package api.Requests;

import java.util.ResourceBundle;

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

}

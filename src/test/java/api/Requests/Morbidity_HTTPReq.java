package api.Requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Morbidity_HTTPReq {
	
	static User_HTTPReq User_HTTPReq = new User_HTTPReq();
	static Response response;
	
	public static Response getAllMorbidity(String oAuthtoken)
	{
		String GetallMorbidities= api.Requests.User_HTTPReq.getUrl().getString("GetallMorbidities");
		return response=RestAssured.given(api.Requests.User_HTTPReq.requestSpecification).auth().oauth2(oAuthtoken)
		.when().get(GetallMorbidities).then().log().all().extract().response();
	}
	
	public static Response getAllMorbidityWithTestName(String oAuthtoken, String morbidityTestName)
	{
		String GetallMorbidities= api.Requests.User_HTTPReq.getUrl().getString("GetallMorbidities")+morbidityTestName;
		return response=RestAssured.given(api.Requests.User_HTTPReq.requestSpecification).auth().oauth2(oAuthtoken)
		.when().get(GetallMorbidities).then().log().all().extract().response();
	}

}

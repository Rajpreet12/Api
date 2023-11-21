package api.TestOps;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.isA;

public class Morbidity_TestCase {

	static Response response;
	static String strResponse;
	JsonPath jsPath;
	String morbidityTestName;
	String morbidityTestNameJson;
	
	 //Get all Morbidity
	 public void getAllMorbidity(String token)
	 {
		 response=api.Requests.Morbidity_HTTPReq.getAllMorbidity(token);
	 }
	 
	 //Get all Morbidity with Test name
	 public void getAllMorbidityWithTestName(String token, String morbidityTestNameR)
	 {
		morbidityTestName = morbidityTestNameR;
		response=api.Requests.Morbidity_HTTPReq.getAllMorbidityWithTestName(token, morbidityTestName);
		strResponse= response.then().log().all().extract().response().asString();
		jsPath=new JsonPath(strResponse);
	 }
	 
	public void verify_morbiditypost_status(int actual_statusCode)
		{
			int expec_statusCode=response.getStatusCode();
			Assert.assertEquals(expec_statusCode, actual_statusCode);			
			Assert.assertEquals(response.header("Content-Type"), "application/json");
			Assert.assertEquals(response.header("Connection"), "keep-alive");
		}
	
	public void verify_morbiditydata()
	{
		morbidityTestNameJson = jsPath.getString("morbidityTestName");
		String morbidityTestNameJsonwithout = morbidityTestNameJson.replace("/", "");
		Assert.assertTrue(morbidityTestNameJson.contains(morbidityTestNameJsonwithout));
	}
	
	public void verify_invalidmorbiditydata()
	{
		String morbidityErrorCode = jsPath.getString("errorCode");
		String morbidityErrorMessage = jsPath.getString("errorMessage");
		Assert.assertTrue(morbidityErrorCode.contains("NOT_FOUND"));
		Assert.assertTrue(morbidityErrorMessage.contains("Morbidity not found with MorbidityTestName :"));
		
	}
}

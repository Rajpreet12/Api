package api.TestOps;

import java.text.SimpleDateFormat;
import java.util.List;

import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;

import api.POJO.CreatePatient_Payload;
import api.POJO.User_Payload;
import api.Requests.User_HTTPReq;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateUser_TestCase {
	
	static Response response;
	static String strResponse;
	static String strResponse_createPatient;
	static JsonPath jsPath;
	static String token_Dietician;
	static String token_Patient;
	Faker faker;
	int expec_statusCode;
	static String token;
	static SimpleDateFormat sdf;
	static String patientId;
	static String fileId;
	
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
	
	public static String getToken(String token)
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
		String contentType = response.header("Content-Type");
		
		
		if ("application/json".equals(contentType)) {
	        Assert.assertEquals(contentType, "application/json");
	    } else if ("application/pdf".equals(contentType))
		{
	        Assert.assertEquals(contentType, "application/pdf");
	    } 
	    else
	    {
	    	Assert.assertEquals(contentType, "text/plain;charset=UTF-8");
	    }
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
		Response response=User_HTTPReq.createPatient(CreatePatient_Payload, token);
		
		strResponse=response.then().log().all().extract().response().asString();
		System.out.println("The response of strResponse is "+strResponse);
		jsPath=new JsonPath(strResponse);
		patientId= jsPath.getString("patientId");

		
// Get all keys under "FileMorbidity".getMap obtains all the key value inside fm and from the
// obtained map,keyset fetch teh keys which is dynamic and then iterates and get the dynamic key
		
        Object fileMorbidityKeyObj = jsPath.getMap("FileMorbidity").keySet().iterator().next();
        String fileMorbidityKey = fileMorbidityKeyObj.toString();
//extracting response to check the thyroidism range
		 String t4StringValue=jsPath.getString("FileMorbidity."+fileMorbidityKey+ ".T4");
		 String tshStringValue=jsPath.getString("FileMorbidity."+fileMorbidityKey+ ".TSH");
		 String t3StringValue= jsPath.getString("FileMorbidity."+fileMorbidityKey+ ".T3");
 //// Remove non-numeric characters from the string values and converting numeric string into double by parsing
		 double t4DoubleValue =Double.parseDouble(t4StringValue.replaceAll("[^\\d.]", ""));
		 double tshDoubleValue =Double.parseDouble(tshStringValue.replaceAll("[^\\d.]", ""));
		 double t3DoubleValue=Double.parseDouble(t3StringValue.replaceAll("[^\\d.]", ""));
		 System.out.println("The values are :"+tshDoubleValue+ " isT4 "+t4DoubleValue+ " is TSH "+t3DoubleValue+ "is T3");
		 
		
		 
//using logical operator to find the thyroidism
		if((tshDoubleValue < 0.55 && t3DoubleValue > 1.8 && t4DoubleValue > 12))
		{
			 System.out.println("Hyperthyroidism detected!");
			 String Hyper=jsPath.get("FileMorbidityCondition."+fileMorbidityKey);
			 System.out.println("The value of FileMorbidityCondition is "+ Hyper);
		}
		
	    else if (tshDoubleValue > 4.78 && t4DoubleValue < 5) {
	   
	    System.out.println("Hypothyroidism detected!");
	    String Hypo=jsPath.get("FileMorbidityCondition."+fileMorbidityKey);
	    System.out.println("The value of FileMorbidityCondition is "+ Hypo);
		
	  } else {
	    
	    System.out.println("No thyroid condition detected.");
	}
	}

 //Get all patients
	 public static void getAllPatients(String token)
	 {
		 token=token_Dietician;
		 System.out.println("The bearer token is "+ token);
		 response=User_HTTPReq.getAllPatients(token);
		 //strResponse=response.then().log().all().extract().response().asString();
		 System.out.println("The value of token is "+ token);
	 }
	 
 //Get Patients Morbidity Details By patient Id and fileId By the dietician

	 public static void get_morbidity(String endpoints,String patientId_endpoint,String token )
	 {
		 String patient_Id ="patient_Id";
		// String auth_token=getToken(token);
		 token=token_Dietician;
		 if( patientId_endpoint.equals(patient_Id))
		 {
		 patientId_endpoint=patientId;
		 System.out.println("The value of patientId is "+ patientId);
		 response=User_HTTPReq.get_morbidity(endpoints,patientId_endpoint, token);
		 System.out.println("The value of "+ endpoints+ "and"+ patientId_endpoint);
		 strResponse=response.then().log().all().extract().response().asString();
		 jsPath=new JsonPath(strResponse);
		 fileId= jsPath.getString("[0].fileId");
		 patientId_endpoint =fileId;
		 System.out.println("The file id for the patientId"+ patientId_endpoint+"is :"+ fileId);
		 }
		 else
		 {
			 patientId_endpoint=fileId;
			 response=User_HTTPReq.get_morbidity(endpoints,patientId_endpoint, token);
			 System.out.println("The value of "+ endpoints+ "and"+ patientId_endpoint);
		 
		 }
	 }
	 
	 
	//Get Patients Morbidity Details and Retrieve Patient file by FileId As Patient
	 
		//Get Patients Morbidity Details By patient Id and fileId By the Patient

			 public static void get_morbidity_By_Patient(String endpoints,String patientId_endpoint,String token )
			 {
				
				 String patient_Id ="patient_Id";
				 System.out.println("The patient id is:"+patient_Id);
				
				
				token=token_Patient;
				System.out.println("The patient generated token is :"+ token);
				
				 if( patientId_endpoint.equals(patient_Id))
				 {
				 patientId_endpoint=patientId;
				 System.out.println("The value of patientId is "+ patientId);
				 response=User_HTTPReq.get_morbidity(endpoints,patientId_endpoint, token);
				 System.out.println("The value of "+ endpoints+ "and"+ patientId_endpoint);
				 strResponse=response.then().log().all().extract().response().asString();
				 jsPath=new JsonPath(strResponse);
				 fileId= jsPath.getString("[0].fileId");
				 patientId_endpoint =fileId;
				 System.out.println("The file id for the patientId"+ patientId_endpoint+"is :"+ fileId);
				 }
				 else
				 {
					 patientId_endpoint=fileId;
					 response=User_HTTPReq.get_morbidity(endpoints,patientId_endpoint, token);
					 System.out.println("The value of "+ endpoints+ "and"+ patientId_endpoint);
				 
				 }
			 }
	 
// Update patient by UserId
	 public void createPatient_UpdatePatient() throws JsonMappingException, JsonProcessingException
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
			Response createPatientResp=User_HTTPReq.createPatient(CreatePatient_Payload, token);
			
			strResponse_createPatient=createPatientResp.then().log().all().extract().response().asString();
			System.out.println("The response of strResponse_createPatient is "+strResponse_createPatient);
			jsPath=new JsonPath(strResponse_createPatient);
			String patientId=jsPath.getString("patientId");
//Parse json response into objMapper
			ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode actualRoot = objectMapper.readTree(strResponse_createPatient);
// Update the value for the key "Allergy"
	        ((ObjectNode) actualRoot).put("Allergy", "NewAllergy");
	        
// Convert the modified JSON back to a string
	       /* String modifiedResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(actualRoot);
	        System.out.println(modifiedResponse);*/
	        
// Serialize the updated JsonNode back to JSON
            String updatedJson = objectMapper.writeValueAsString(actualRoot);
            System.out.println(updatedJson);
           response= User_HTTPReq.createPatient_UpdatePatient(updatedJson,token,patientId);
           //String updatedResponse= response.then().log().all().extract().response().asString();
          // System.out.println("The updated String response is "+ updatedResponse);
	 
}
	
 // Delete Patient by UserId	
	 public static void DelPatientId()
	 {
		 token=getToken(token);
		 
		response= User_HTTPReq.delete_PatientId( token,patientId);
	 }
	
// Get all patients and delete all patients	
	 public static void Get_DelPatients()
	 {
		 	token=getToken(token);
		    response=User_HTTPReq.getAllPatients(token);
		    List<String> patientIds=response.jsonPath().getList("patientId");
/*		   // String resp=response.toString();		 	
		 	//jsPath=new JsonPath(resp);
		 //	List<String> patientIds=jsPath.getList("patientId");
		 	
 // Iterate through each patient and perform actions
		 	for(String patientId:patientIds)
		 	{
		 		response=User_HTTPReq.delete_PatientId(token, patientId);
		 	}
		/* 	for(int i=0;i<=20;i++)
		 	{
		 		String patientId=patientIds.get(i);
		 		response=User_HTTPReq.delete_PatientId(token, patientId);
		 	}*/
		    
		    
		    // Iterate through each patient and perform actions
		    for (Object patientIdObject : patientIds) {
		        if (patientIdObject instanceof String) {
		            String patientId = (String) patientIdObject;
		           // response = User_HTTPReq.delete_PatientId(token, patientId);
		        } else if (patientIdObject instanceof Integer) {
		            // Convert the integer to a string and then use it
		            String patientId = String.valueOf(patientIdObject);
		          //  response = User_HTTPReq.delete_PatientId(token, patientId);
		        } else {
		            // Handle other cases if needed
		            System.out.println("Unsupported patient ID type: " + patientIdObject.getClass());
		        }
		    }
	 }
	 
 // User Logoout
	 public static void userLogout(String oAuth)
	 {
		 token=getToken(token);
		response= User_HTTPReq.userLogout(token);
	 }
	 

}

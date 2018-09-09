package com.onforce.interview.assignment.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onforce.interview.assignment.BaseAPI;
import com.onforce.interview.assignment.client.RestClient;
import com.onforce.interview.assignment.data.Users;
import com.onforce.interview.assignment.util.ExcelReader;
import com.onforce.interview.assignment.util.TestUtil;


public class PostAPITest extends BaseAPI {
	
	BaseAPI baseApi;
	String endpointURL;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	String result;
	
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		baseApi = new BaseAPI();
		endpointURL = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url = endpointURL + apiUrl;
	}
	
	@Test
	public void invalidURL() throws JsonGenerationException, JsonMappingException, IOException {
		
		System.out.println("Scenario 1: Invalid Service URL");
		url = url + "/123";
		RestClient restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>(); 
		headerMap.put("Content-Type", "application/json"); 
		ObjectMapper mapper = new ObjectMapper(); //jackson API to build the JSON from data
		Users user = new Users("Test","User","testuser@gmail.com","test123","Tr34&cx","222-222-2222");
		mapper.writeValue(new File(System.getProperty("user.dir")+"/src/main/java/com/onforce/interview/assignment/data/Users.json"), user);//Object to JSON File
		String userJsonString = mapper.writeValueAsString(user); //Object to JSON String
		closeableHttpResponse = restClient.post(url, userJsonString, headerMap); //call the API
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("HttpStatusCode : " + statusCode);
		if (statusCode == RESPONSE_STATUS_CODE_404) 
			System.out.println("TestResult : " + "PASS");
		else
			System.out.println("TestResult : " + "FAIL");
	}
	
	@Test
	public void postAPITest() throws ClientProtocolException, IOException {
		
		System.out.println("\nScenario 2: Param Validations");
		RestClient restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>(); 
		headerMap.put("Content-Type", "application/json"); 

		//Formatting to print in Console
		for (int i=0; i<150; i++){
		    System.out.print("_");
		}
		System.out.println();
		String pipe = "|";
		System.out.format("%35s%1s%16s%1s%12s%1s%15s%1s%s\n", "Test Case Name", pipe, "HTTPStatusCode", pipe, "SuccessFlag", pipe,  " TestResult", pipe, "ErrorMsg");
		for (int i=0; i<150; i++){
		    System.out.print("_");
		}
		System.out.println();
		
		//Get Row Count and read Excel to get the inputs
		ExcelReader reader = new ExcelReader(System.getProperty("user.dir")+"/src/main/java/com/onforce/interview/assignment/data/TestData.xlsx");
		int rowCount = reader.getRowCount("signup");
		for(int i = 2; i<=rowCount;i++) {
			String scenario = reader.getCellData("signup", i, "scenario");
			String expectedResult = reader.getCellData("signup", i, "expectedResult");
			String firstName = reader.getCellData("signup", i, "firstName");
			String lastName = reader.getCellData("signup", i, "lastName");
			String email = reader.getCellData("signup", i, "email");
			String username = reader.getCellData("signup", i, "username");
			String password = reader.getCellData("signup", i, "password");
			String phoneNumber = reader.getCellData("signup", i, "phoneNumber");

			
			ObjectMapper mapper = new ObjectMapper(); //jackson API to build the JSON from data
			Users user = new Users(firstName,lastName,email,username,password,phoneNumber);
			mapper.writeValue(new File(System.getProperty("user.dir")+"/src/main/java/com/onforce/interview/assignment/data/Users.json"), user);
			String userJsonString = mapper.writeValueAsString(user); //Object to JSON String
			closeableHttpResponse = restClient.post(url, userJsonString, headerMap); //call the API

			//validate response from API: 
			int statusCode = closeableHttpResponse.getStatusLine().getStatusCode(); 		 
			String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8"); //JsonString
 
			JSONObject responseJson = new JSONObject(responseString); 
			String successKey = TestUtil.getValueByJPath(responseJson, "/success");
			
			if (statusCode == RESPONSE_STATUS_CODE_201) {
				if (Boolean.parseBoolean(expectedResult)==true) 
					result = "PASS";
				else
					result = "FAIL";
				System.out.format("%35s%1s%16s%1s%12s%1s%15s%1s%s\n", scenario, pipe, statusCode, pipe, successKey, pipe, result, pipe, "None");
			} else if (statusCode != RESPONSE_STATUS_CODE_201) {
				String errorMsg = TestUtil.getValueByJPath(responseJson, "/errors");
				if (Boolean.parseBoolean(expectedResult)==true)
					result = "FAIL";
				else
					result = "PASS";
				System.out.format("%35s%1s%16s%1s%12s%1s%15s%1s%s\n", scenario, pipe, statusCode, pipe, successKey, pipe, result, pipe, errorMsg);
				
			}
		}
		for (int i=0; i<150; i++){
		    System.out.print("_");
		}
		System.out.println();
	}
}

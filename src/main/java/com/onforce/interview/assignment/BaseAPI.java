package com.onforce.interview.assignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseAPI {
	
	public int RESPONSE_STATUS_CODE_200 = 200; 
	public int RESPONSE_STATUS_CODE_500 = 500; 
	public int RESPONSE_STATUS_CODE_400 = 400; 
	public int RESPONSE_STATUS_CODE_405 = 405;
	public int RESPONSE_STATUS_CODE_404 = 404; 
	public int RESPONSE_STATUS_CODE_201 = 201; 

	
	public Properties prop;
	
 	public BaseAPI(){ 
  		try { 
  			prop = new Properties(); 
  			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/onforce/interview/assignment/config/config.properties"); 
  			prop.load(ip); 
  		} catch (FileNotFoundException e) { 
  			e.printStackTrace(); 
  		} catch (IOException e) { 
  			e.printStackTrace(); 
  		} 
 	}

}

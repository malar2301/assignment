package com.onforce.interview.assignment.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {

	//POST Method
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url); //http POST request
		httpPost.setEntity(new StringEntity(entityString)); //For sending payload
		
		//For headers 
  		for(Map.Entry<String,String> entry : headerMap.entrySet()){ 
  			httpPost.addHeader(entry.getKey(), entry.getValue()); 
  		} 
  		
  		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
  		return closeableHttpResponse;
	}
		
}

package com.boi.grp.utilities;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.boi.grp.pageobjects.BasePage;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;


public class RestHelper extends BasePage{
	
	public Map<String,String> authenticationHeaders;
	public Logger logMan=null;
    public FileInputStream fis;
    public Properties config;
    String value;

	public RestHelper(HashMap<String,String> authenticationDetails)
	{
        try {
            logMan= LogManager.getInstance();
            this.authenticationHeaders=authenticationDetails;
            fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/endPointUrl.properties");
            config = new Properties();
            config.load(fis);
        } catch (Throwable e) {
            logError("Error in constructor of RestHelper");
        }
    }

	public Response PostMessageByMessageBody(String ServiceURLPart, String Messagebody)
	{
		Response response=null;	
		try 
		{
			logMan.info("---------------------------POST URL START--------------------------------");
			logMan.debug("Posting URL="+ServiceURLPart);
			logMan.info("----------------------------POST URL END-------------------------------");
			
			
			logMan.info("---------------------------REQUEST BODY START--------------------------------");
			logMan.debug("Posting request as-\n\n"+Messagebody);
			logMan.info("----------------------------REQUEST BODY END-------------------------------");
			response = given().headers(authenticationHeaders).
			contentType(ContentType.JSON).
			body(Messagebody).
			post(ServiceURLPart);			
			
			logMan.info("--------------------------RESPONSE BODY START---------------------------------");
			logMan.debug("Response Generated successfully and has value =\n\n"+response.asString());
			logMan.info("----------------------------RESPONSE BODY END-------------------------------");	
		}
		catch (Throwable t) 
		{
			logMan.fatal("Error Occured Inside PostMessageByMessageBody function while posting at URL ="+ServiceURLPart+" , Messagebody= "+Messagebody);
			System.out.println("Error Occured Inside PostMessageByMessageBody function while posting at URL ="+ServiceURLPart+" , Messagebody= "+Messagebody);			
		}	
		return response;
		}


    public Response PostMessageByMessageBodyAsObjectType(String ServiceURLPart, Object objectClass)
    {
        Response response=null;
        try
        {
            logMan.info("---------------------------POST URL START--------------------------------");
            logMan.debug("Posting URL="+ServiceURLPart);
            logMan.info("----------------------------POST URL END-------------------------------");


            logMan.info("---------------------------REQUEST BODY START--------------------------------");
            logMan.debug("Posting request as-\n\n"+objectClass);
            logMan.info("----------------------------REQUEST BODY END-------------------------------");
            response = given().headers(authenticationHeaders).
                    contentType(ContentType.JSON).
                    body(objectClass).
                    post(ServiceURLPart);

            logMan.info("--------------------------RESPONSE BODY START---------------------------------");
            logMan.debug("Response Generated successfully and has value =\n\n"+response.asString());
            logMan.info("----------------------------RESPONSE BODY END-------------------------------");
        }
        catch (Throwable t)
        {
            logMan.fatal("Error Occured Inside PostMessageByMessageBody function while posting at URL ="+ServiceURLPart+" , Messagebody= "+objectClass.toString());
            System.out.println("Error Occured Inside PostMessageByMessageBody function while posting at URL ="+ServiceURLPart+" , Messagebody= "+objectClass.toString());
        }
        return response;
    }
	
	public Response UpdateUsingPutMessage(String ServiceURLPart,String MessageBody)
	{		
		Response response=null;		
		try {
			response = given().headers(authenticationHeaders).
			contentType(ContentType.JSON).
			body(MessageBody).		
			when().			
			put(ServiceURLPart);			
			String returnedStatisCode=String.valueOf(response.getStatusCode());
			if(returnedStatisCode.equalsIgnoreCase("200"))
			{
				logMan.info("UpdateUsingPutMessage function Executed successfully, response ="+response.asString());
			}
			else
			{
				logMan.error("Error Occured while Update using UpdateUsingPutMessage function, returnedStatisCode="+returnedStatisCode+",URL="+ServiceURLPart+", Error Descripton="+response.asString());
			}
		}
		catch (Throwable t) {
			logMan.fatal("Error Occured Inside UpdateUsingGetMessage function while updating using PUT"+ServiceURLPart+",Error Description="+t.getMessage());
			System.out.println("Error Occured Inside UpdateUsingGetMessage function while updating using PUT"+ServiceURLPart+",Error Description="+t.getMessage());			
		}		
		return response;		
	}
	
	public Response UpdateUsingPutMessagewithoutbody(String ServiceURLPart)
	{		
		logMan.info("UpdateUsingPutMessagewithoutbody function START");
		Response response=null;		
		try {
			response = given().headers(authenticationHeaders).
			contentType(ContentType.JSON).					
			when().			
			put(ServiceURLPart);
			String returnedStatisCode=String.valueOf(response.getStatusCode());
			if(returnedStatisCode.equalsIgnoreCase("200"))
			{
			logMan.info("UpdateUsingPutMessagewithoutbody function Executed successfully, response ="+response.asString());
			}
			else
			{
				logMan.error("Error Occured while Update using UpdateUsingPutMessagewithoutbody function,returnedStatisCode="+returnedStatisCode+" URL="+ServiceURLPart+", Error Descripton="+response.asString());
			}
		} 
		catch (Throwable t) {
			logMan.fatal("Error Occured Inside UpdateUsingPutMessagewithoutbody function while updating using PUT"+ServiceURLPart+", Error Description="+t.getMessage());
			System.out.println("Error Occured Inside UpdateUsingPutMessagewithoutbody function while updating using PUT"+ServiceURLPart+", Error Description="+t.getMessage());			
		}		
		logMan.info("UpdateUsingPutMessagewithoutbody function END");
		return response;		
	}
	
	public Response GetMessage(String ServiceURLPart)
	{
		logMan.info("GetMessage function START");
		Response response=null;
		
		try 
		{
			logMan.info("---------------------------GET URL START--------------------------------");
			logMan.debug("Get Method, URL="+ServiceURLPart);
			logMan.info("----------------------------GET URL END-------------------------------");
			response = given().headers(authenticationHeaders).
				contentType(ContentType.JSON).
				when().
				get(ServiceURLPart);
			logMan.info("GetMessage function END");
			
			logMan.info("--------------------------RESPONSE BODY START---------------------------------");
			logMan.debug("Response Generated successfully and has value =\n\n"+response.asString());
			logMan.info("----------------------------RESPONSE BODY END-------------------------------");	
		}
		catch (Throwable t) 
		{
			logMan.fatal("Error Occured Inside GetMessage function while posting at URL ="+ServiceURLPart+" , Error= "+t.getMessage());

            System.out.println("Error Occured Inside GetMessage function while targetting at URL ="+ServiceURLPart+" , Error= "+t.getMessage());
		}	
		return response;
	}

	public String returnFileAsASingleString(String FilePath)
	{
		BufferedReader br;
		StringBuilder sb;
		String line;
		String everything="";		
		try 
		{
			br = new BufferedReader(new FileReader(FilePath));
		     sb = new StringBuilder();
		    line = br.readLine();

		    while (line != null)
		    {
		    	sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    everything = sb.toString();
		    br.close();
		    logMan.debug("File read successfully, file path="+FilePath);
		}
		catch(Throwable t)
		{
			System.out.println("Error Occured Inside returnFileAsASingleString function for path ="+FilePath);
		}
		
		return everything;
	}


    public void getToken(){
        //project will implement this according to their need
    }



    public void spec(){
        RestAssured.baseURI="";
    }


    public String fnCreateFinalUrlEndpoint(String baseURL, String sURI){
        String baseFinalURI = null;
        try {
            baseFinalURI=baseURL+sURI;
            insertMessageToHtmlReport("Final Endpoint created successfully "+baseFinalURI);
            //step("Final Endpoint created successfully "+baseFinalURI);
        } catch (Exception e) {
            injectErrorToCucumberReport("Final Endpoint NOT created successfully "+baseFinalURI);
            //step("Final Endpoint NOT created successfully "+baseFinalURI);
            logError("Error in fnCreateFinalUrlEndpoint method, Error = "+e.getMessage());
        }
        return baseFinalURI;
    }


    public boolean validateExceptedAndActualWithHelpOfJsonPath(Response result, String jsonPath, String expectedValue){
        boolean returnValue=false;
        try
        {
            String actualValue = result.jsonPath().getString(jsonPath);
            if(actualValue.equalsIgnoreCase(expectedValue))
            {
                Assert.assertTrue("Matched successfully, Expected = "+expectedValue+" and Actual value = "+actualValue, true);
                insertMessageToHtmlReport("Matched Successfully , Expected = "+expectedValue+" and Actual = "+actualValue);
                insertMessageToHtmlReport("Response = "+result.asString());
                logMessage("Matched Successfully , Expected = "+expectedValue+" and Actual = "+actualValue);
                returnValue=true;
            }
            else
            {
                logError("Mismatch in Expected and Actual. Expected value = "+expectedValue+ " and Actual value = "+actualValue);
                insertErrorMessageToHtmlReport("Mismatch in Expected and Actual. Expected value = "+expectedValue+ " and Actual value = "+actualValue);
                insertErrorMessageToHtmlReport("Response = "+result.asString());
                Assert.assertTrue("Mismatch in  Expected and Actual. Expected value = "+expectedValue+ " and Actual value = "+actualValue,false);
            }
        }
        catch(Exception t)
        {
            logError("Error occurred in validateExceptedAndActualWithHelpOfJsonPath matching, Error = "+ t.getMessage());
        }
        return returnValue;
    }



    public void validateResponseStatusCode(Response response, int estatusCode){
        try{
            if(response.getStatusCode()==Integer.valueOf(estatusCode))
            {
                insertMessageToHtmlReport("Status code matched successfully. Expected Code =  "+estatusCode+" Actual code = "+response.getStatusCode());
                Assert.assertTrue("Status code is matched successfully  "+estatusCode, true);
            }
            else{
                insertErrorMessageToHtmlReport("Status code NOT matched successfully. Expected code = "+estatusCode+" Actual code = "+response.getStatusCode());
                logMan.error("Error: Mismatch in request code and response code. Expected code = "+estatusCode+ " Actual code = "+String.valueOf(response.getStatusCode()));
                Assert.assertTrue("Error: Mismatch in request code and response code. Expected code = "+estatusCode+ " Actual code = "+String.valueOf(response.getStatusCode()),false);
            }

        }catch (Exception e) {
            //insertErrorMessageToHtmlReport("Error occured in Response status code "+response.getStatusCode());
            logMan.error("Error occured in Response status code");
            logMan.error("Error description : "+e.getMessage());
        }
    }


    public void validateResponseStatusLine(Response response, String estatusLine){

        try
        {
            String astatusLine = response.getStatusLine();
            Assert.assertEquals("Correct Response status line ",estatusLine,astatusLine);
            insertMessageToHtmlReport("Response line actual value matched successfully "+ response.getStatusLine());
            logMan.info("Response line actual value and matched successfully " + response.getStatusLine());

        }catch (Exception e) {
            logMan.error("Error occured in Response status line");
            insertErrorMessageToHtmlReport("Response line actual value NOT matched successfully "+e.getMessage());
            logMan.error("Error description : "+e.getMessage());
        }
    }


    public void validateResponseContentType(Response response, String econtentType){
        try
        {
            String aRContentType=response.getContentType();
            Assert.assertEquals("Correct Response content type ",econtentType,aRContentType);
            insertMessageToHtmlReport("Content type matched successfully "+ aRContentType);
            logMan.info("Content type matched successfully " + aRContentType);
        }catch (Exception e) {
            insertErrorMessageToHtmlReport("Content type NOT matched successfully "+e.getMessage());
            logMan.error("Error occured in Response in Content Type");
            logMan.error("Error description : "+e.getMessage());
        }
    }


    public void printResponseHeader(Response response){

        try
        {
            Headers allHeaders = response.headers();
            for(Header header : allHeaders)
            {
                injectMessageToCucumberReport("Key: " + header.getName() + " Value: " + header.getValue());
                logMan.info("Key: " + header.getName() + " Value: " + header.getValue());
            }
        }catch (Exception e) {
            logMan.error("Error occured in Print Response headers");
            logMan.error("Error description : "+e.getMessage());
        }
    }

    public void printResponseBody(Response response){
        try
        {
            String responseBody = response.getBody().prettyPrint();
            logMan.info("Response body is : "+ responseBody);
            insertMessageToHtmlReport("Response body is : "+ responseBody);
        }catch (Exception e) {
            insertErrorMessageToHtmlReport("Error occured in Print Response headers");
            logMan.error("Error occured in Print Response headers");
            logMan.error("Error description : "+e.getMessage());
        }
    }


    public void printResponseTime(Response response){
        try
        {
            long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
            insertMessageToHtmlReport("Response time is : " +responseTime);
            logMan.info("Response time is : "+ responseTime);
        }catch (Exception e) {
            logMan.error("Error occured in Print Response headers");
            logMan.error("Error description : "+e.getMessage());
        }
    }

    public void validateTagValue(Response response, String key){

        try {
            String resp=response.asString();
            JsonPath js=new JsonPath(resp);
            String[] splitVal=key.split(":");

            String tagKey=splitVal[0].toString();
            String tagValue=splitVal[1].toString();
            String acTagValue = response.path(tagKey);

            if(acTagValue.equals(tagValue)){
                insertMessageToHtmlReport("Tag value matched successfully ");
                //step("Tag value matched successfully ");
                logMan.info("Tag value matched successfully");
            }else{
                insertErrorMessageToHtmlReport("Tag value NOT matched successfully ");
                logError("Tag value NOT matched successfully");
            }
        } catch (Exception e) {
            logMan.error("Error in validateTagValue method, Error = "+e.getMessage());
        }
    }

    public void validateResponseBodyReceived(Response response){

        String respBody=response.asString();
        if(respBody.isEmpty()){
            insertErrorMessageToHtmlReport("Response body is empty ");
            logError("Response body is empty ");
        }
        else{
            insertMessageToHtmlReport("Response body is received successfully ");
            logMessage("Response body is received successfully");
        }
    }


    public void validateBodyContains(Response response, String strBodyValue){

        try {
            String responseBody=response.getBody().asString();
            Assert.assertTrue("Response body contains : ", responseBody.contains(strBodyValue));
            insertMessageToHtmlReport("Response Body contains : " +strBodyValue);
            logMan.info("Response Body contains : " +responseBody);
        } catch (Exception e) {
            insertErrorMessageToHtmlReport("Error occured in Body contains function");
            logMan.error("Error occured in Body contains function");
            logMan.error("Error description : "+e.getMessage());
        }
    }


    public void validateBodyContainsMultipleKeyTags(Response response, String strTagValue){

        try {
            String responseBody=response.getBody().asString();
            String[] ArrsplitVal=strTagValue.split("\\|");
            for(String currVal:ArrsplitVal){
                Assert.assertTrue("Response body contains : ", responseBody.contains(currVal));
                insertMessageToHtmlReport("Response Body contains : " +currVal);
                logMan.info("Response Body contains : " +currVal);
            }
        } catch (Exception e) {
            insertErrorMessageToHtmlReport("Response Body DOESNOT contains the required tag " );
            logMan.error("Error occured in Body contains function");
            logMan.error("Error description : "+e.getMessage());
        }
    }


    public void setValueForJsonKey(JSONObject jsonObject, String key){
        this.value=(String)jsonObject.get(key);
        //return jsonObject.get(key);
    }


    public String getValueForJsonKey(){
        //to fetch the value, first call parseJson method and then call this method
        return value;
    }


	public void parseJson(JSONObject json,String key){
        try {
            boolean keyExists = json.has(key);
            Iterator<?> iterator;
            String nextKey;
            if(!keyExists){
                iterator = json.keys();
                while (iterator.hasNext()){
                    nextKey=(String)iterator.next();
                    try {
                        if(json.get(nextKey) instanceof JSONObject){
                            if(keyExists==false){
                                parseJson(json.getJSONObject(nextKey),key);
                            }

                        }else if(json.get(nextKey) instanceof JSONArray){
                            JSONArray jsonArray = json.getJSONArray(nextKey);
                            for (int i=0;i<jsonArray.length();i++){
                                String jsonArrayString = jsonArray.get(i).toString();
                                JSONObject innerJsonObject = new JSONObject(jsonArrayString);
                                if(keyExists==false){
                                    parseJson(innerJsonObject,key);
                                }
                            }
                        }

                    }catch (Exception e){
                        logMan.error("Error in Json parsing, Error= "+e.getMessage());
                    }
                }
            }else{
                setValueForJsonKey(json,key);
            }
        } catch (Exception e) {
            logMan.error("Error in parseJson method, Error = "+e.getMessage());
        }
    }

    public String parseJsonPayload(String filePath,String parameter){
        String value=null;
        try {
            Map<String, Map<String, String>> deSerialData = getDeserializeDataForJsonPayLoad();
            Map<String, String> data = deSerialData.get(parameter);

            BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
            String line=bufferedReader.readLine();
            StringBuilder builder=new StringBuilder();
            while(line!=null){
                builder.append(line);
                line=bufferedReader.readLine();
            }
            value=builder.toString();

            int regexIndex = value.indexOf("$");
            while(regexIndex<value.length()){
                int nextRegexIndex = value.indexOf("$", regexIndex+1);
                String keyNameWithRegex = value.substring(regexIndex, nextRegexIndex + 1);
                String keyName = keyNameWithRegex.replaceAll("\\$", "");
                if(data.containsKey(keyName)){
                    String valueToBeReplaced=data.get(keyName);
                    if(valueToBeReplaced.equalsIgnoreCase("NA")){
                        int colonIndex = value.lastIndexOf(":", regexIndex);
                        int tempIndex = value.lastIndexOf("\"", colonIndex);
                        int keyIndex = value.lastIndexOf("\"", tempIndex-1);

                        int firstQuotesIndex = value.indexOf("\"", regexIndex);
                        int secondQuotesIndex=value.indexOf("\"",firstQuotesIndex+1);
                        if(secondQuotesIndex==-1){
                            secondQuotesIndex=firstQuotesIndex+1;
                        }

                        String firstPart=value.substring(0,keyIndex);
                        String secondPart=value.substring(secondQuotesIndex);

                        value=firstPart+secondPart;
                        regexIndex=value.indexOf("$");
                        if(regexIndex==-1){
                            break;
                        }
                    }else{
                        String firstPart=value.substring(0,regexIndex);
                        String secondPart=value.substring(nextRegexIndex+1);
                        value=firstPart+valueToBeReplaced+secondPart;
                        regexIndex=value.indexOf("$");
                        if(regexIndex==-1){
                            break;
                        }
                    }

                }else{
                    logError("Please check the columnName written in jsonPayload with key as, = "+keyName);
                }
            }
            System.out.println(value);
        } catch (Throwable e) {
            logError("Error in parseJsonPayload method, Error = "+e.getMessage());
        }
        return value;
    }



    public String parseExpectedJsonResponseFromTextFile(String fileName,Map<String, String> data){
        String value=null;
        String responsePath = System.getProperty("user.dir") + "/src/test/java/com/boi/grp/response/";
        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader(responsePath+fileName));
            String line=bufferedReader.readLine();
            StringBuilder builder=new StringBuilder();
            while(line!=null){
                builder.append(line);
                line=bufferedReader.readLine();
            }
            value=builder.toString();
            if (data.size()>0) {
                int regexIndex = value.indexOf("$");
                if(!(regexIndex ==-1)){
                    while(regexIndex<value.length()){
                        int nextRegexIndex = value.indexOf("$", regexIndex+1);
                        String keyNameWithRegex = value.substring(regexIndex, nextRegexIndex + 1);
                        String keyName = keyNameWithRegex.replaceAll("\\$", "");
                        if(data.containsKey(keyName)){
                            String valueToBeReplaced=data.get(keyName);
                            String firstPart=value.substring(0,regexIndex);
                            String secondPart=value.substring(nextRegexIndex+1);
                            value=firstPart+valueToBeReplaced+secondPart;
                            regexIndex=value.indexOf("$");
                            if(regexIndex==-1){
                                break;
                            }
                        }else{
                            logError("Please check the columnName written in jsonPayload with key as, = "+keyName);
                        }
                    }
                }
            }
            System.out.println(value);
        } catch (Throwable e) {
            logError("Error in parseJsonPayload method, Error = "+e.getMessage());
        }
        return value;
    }


    public List<String> compareJsonSchema(String expectedJson, String actualJson){
        List<String> finalList = new ArrayList<String>();
        try {
            List<String> list = new ArrayList<String>();
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> firstMap = gson.fromJson(expectedJson, mapType);
            Map<String, Object> secondMap = gson.fromJson(actualJson, mapType);

            Map<String, Object> leftFlatMap = FlatMapUtil.flatten(firstMap);
            Map<String, Object> rightFlatMap = FlatMapUtil.flatten(secondMap);
            MapDifference<String, Object> difference = Maps.difference(leftFlatMap, rightFlatMap);

            difference.entriesOnlyOnLeft()
                    .forEach((key, value) -> list.add("Present only in Expected Json "+key + ": " + value));

            difference.entriesOnlyOnRight()
                    .forEach((key, value) -> list.add("Present only in Actual Json "+key + ": " + value));

            difference.entriesDiffering()
                    .forEach((key, value) -> list.add("Mismatch on common values "+key + ": " + value));

            finalList.addAll(list);
            //System.out.println(list.toString());
        } catch (JsonSyntaxException e) {
            logError("Error in JSON comparison , error = "+e.getMessage());
        }
        return finalList;
    }


    public Map<String,Map<String, String>> getDeserializeDataForJsonPayLoad(){
        Map<String,Map<String,String>> mappedData = new HashMap<String, Map<String, String>>();

        try{
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/target/jsonPayLoadData");
            ObjectInputStream ois = new ObjectInputStream(fis);
            mappedData = (Map<String,Map<String, String>>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e){
            logError("Error in getDeserializeDataForMCConfiguration, Error = "+e.getMessage());
            return mappedData;
        } catch (ClassNotFoundException e){
            logError("Class not found, Error = "+e.getMessage());
            return mappedData;
        }
        logInfo("Deserialization of excel data is complete");
        return mappedData;
    }


}

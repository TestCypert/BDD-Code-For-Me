package com.boi.grp.pageobjects;

import java.util.HashMap;
import java.util.Map;

import com.boi.grp.utilities.RestHelper;
import com.jayway.restassured.response.Response;
import org.junit.Assert;

public class RestCountries extends BasePage{


	/**
	 * This Method will
	 * @param result
	 * @param ResponseCode
     * @return
     */
	public boolean doesResponseCodeMatched(Response result,String ResponseCode)
	{
		boolean returnValue=false;
		try
		{
			if(result.getStatusCode()==Integer.valueOf(ResponseCode))
			{
                Assert.assertTrue("Status code is matched successfully , city = "+ResponseCode, true);
                injectMessageToCucumberReport("Status code is matched successfully, Expected code= "+ResponseCode+", and Actual code = "+String.valueOf(result.getStatusCode()));
				returnValue=true;
			}
			else{
                injectErrorToCucumberReport("Error: Mismatch in request code and response code. Expected code = "+ResponseCode+ " Actual code = "+String.valueOf(result.getStatusCode()));
			    logError("Error: Mismatch in request code and response code. Expected code = "+ResponseCode+ " Actual code = "+String.valueOf(result.getStatusCode()));
				Assert.assertTrue("Error: Mismatch in request code and response code. Expected code = "+ResponseCode+ " Actual code = "+String.valueOf(result.getStatusCode()),false);
			}
			
		}
		catch(Exception t)
		{
			injectErrorToCucumberReport("Error: Mismatch in Status code, Error = "+t.getMessage());
            Assert.assertTrue("Error: Mismatch in Status code matching, Error = "+t.getMessage(),false);
			logError("Error occurred in status code matching, Error = "+ t.getMessage());
		}
		return returnValue;
	}
	
	public boolean doesCapitalCityByNameMatched(Response result,String ExpectedCapitalCityByName)
	{
		boolean returnValue=false;
		try
		{
			String generatedCapitalCity = result.jsonPath().getString("capital[0]");
			System.setProperty("generatedCapitalCity", generatedCapitalCity);
			if(generatedCapitalCity.equalsIgnoreCase(ExpectedCapitalCityByName))
			{
				Assert.assertTrue("Capital city is matched successfully , city = "+generatedCapitalCity, true);
				injectMessageToCucumberReport("Capital City Matched successfully, Expected Capital = "+ExpectedCapitalCityByName+", and Actual = "+generatedCapitalCity);
				injectMessageToCucumberReport("Response = "+result.asString());
                returnValue=true;
			}
			else
			{
                injectErrorToCucumberReport("Error: Mismatch in Capital City Name. Expected Capital Name = "+ExpectedCapitalCityByName+ " Actual Capital Name = "+generatedCapitalCity+", Where Response = "+result.asString());
                logError("Error: Mismatch in Capital City Name. Expected Capital Name = "+ExpectedCapitalCityByName+ " Actual Capital Name = "+generatedCapitalCity);
                Assert.assertTrue("Error: Mismatch in Capital City Name. Expected Capital Name = "+ExpectedCapitalCityByName+ " Actual Capital Name = "+generatedCapitalCity,false);
			}
		}
		catch(Exception t)
		{
            injectErrorToCucumberReport("Error: Mismatch in Capital City Name, Error = "+t.getMessage());
			Assert.assertTrue("Error: Mismatch in Capital City Name, Error = "+t.getMessage(),false);
            logError("Error occurred in city matching, Error = "+ t.getMessage());
		}
		return returnValue;
	}
}

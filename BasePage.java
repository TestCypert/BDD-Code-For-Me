package com.boi.grp.pageobjects;

import com.boi.grp.utilities.LogManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Status;
import org.apache.log4j.Logger;
import com.boi.grp.hooks.Hooks;
import com.boi.grp.utilities.LogManagerPreRun;
import org.assertj.core.api.SoftAssertions;

import static io.qameta.allure.Allure.step;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BasePage {
	public Logger logMan=null;

	public BasePage()
	{
		logMan= LogManager.getInstance();
	}
	
	public void logMessage(String Message)
	{
		logMan.debug(Message);
		System.out.println("Message = "+Message);
	}
	public void logInfo(String Message)
	{
		logMan.info(Message);
		System.out.println("Info = "+Message);
	}
	public void logWarning(String Message)
	{
		logMan.warn(Message);
		System.out.println("Warning = "+Message);
	}
	public void logError(String ErrorMessage)
	{
		logMan.error("\n"+ErrorMessage+"\n");	
		System.out.println("Error = "+ErrorMessage);		
		//Assert.assertTrue(ErrorMessage,false);
	}
	public void injectMessageToCucumberReport(String Message)
	 {
		Hooks.testscenario.write("MESSAGE : "+Message);
		 logMessage(Message);
	 }

	 public void injectWarningMessageToCucumberReport(String Message)
	{
		Hooks.testscenario.write(" WARNING : "+Message);
		logWarning(Message);

	}

	public void injectErrorToCucumberReport(String Message)
	 {
		 Hooks.testscenario.write(" ERROR : "+Message);
	 }

	public void insertMessageToHtmlReport(String message){
        try {
            String reportType = System.getProperty("REPORT_TYPE");
            switch (reportType.toUpperCase()) {
                case "ALL":
                    step(message);
                    Hooks.testscenario.write("MESSAGE : " + message);
                    break;
                case "ALLURE":
                    step(message);
                    break;
                case "CONSOLIDATEDREPORT":
                    Hooks.testscenario.write("MESSAGE : " + message);
                    break;
                default:
                    Hooks.testscenario.write("MESSAGE : " + message);
                    break;
            }
        }catch(Throwable e){
            logError("Error in insertMessageToHtmlReport method, Error = "+e.getMessage());
        }

	}

    public void insertWarningMessageToHtmlReport(String message){
        try {
            String reportType = System.getProperty("REPORT_TYPE");
            switch (reportType.toUpperCase()) {
                case "ALL":
                    step(message, Status.BROKEN);
                    Hooks.testscenario.write("WARNING : " + message);
                    break;
                case "ALLURE":
                    step(message, Status.BROKEN);
                    break;
                case "CONSOLIDATEDREPORT":
                    Hooks.testscenario.write("WARNING : " + message);
                    break;
                default:
                    Hooks.testscenario.write("WARNING : " + message);
                    break;
            }
        }catch(Throwable e){
            logError("Error in insertWarningMessageToHtmlReport method, Error = "+e.getMessage());
        }
    }

    public void insertErrorMessageToHtmlReport(String message){
        try {
            String reportType = System.getProperty("REPORT_TYPE");
            switch (reportType.toUpperCase()) {
                case "ALL":
                    step(message, Status.FAILED);
                    Hooks.testscenario.write("ERROR : " + message);
                    break;
                case "ALLURE":
                    step(message, Status.FAILED);
                    break;
                case "CONSOLIDATEDREPORT":
                    Hooks.testscenario.write("ERROR : " + message);
                    break;
                default:
                    Hooks.testscenario.write("ERROR : " + message);
                    break;
            }
        }catch(Throwable e){
            logError("Error in insertErrorMessageToHtmlReport method, Error = "+e.getMessage());
        }
    }


    public void softAssertString(String actualMessage,String expectedMessage){
        Hooks.softAssertions.assertThat(actualMessage).isEqualTo(expectedMessage);
    }


    public void softAssertBoolean(boolean actualCondition,boolean expectedCondition){
        Hooks.softAssertions.assertThat(actualCondition).isEqualTo(expectedCondition);
    }
    
    public void softlyAssert(String actualMessage,String expectedMessage){
        //Hooks.softAssertions.assertThat(actualMessage).isEqualTo(expectedMessage);
    	SoftAssertions softAssertions = new SoftAssertions();
    	softAssertions.assertThat(actualMessage).isEqualTo(expectedMessage);
    	
    	List<Throwable> list = softAssertions.errorsCollected();
    	if(list.size()>0){
    		insertWarningMessageToHtmlReport("ERROR in SoftAssertion \n"+list.get(0).getMessage());
    	}else{
            insertMessageToHtmlReport("Soft assertion is successful, Expected = "+expectedMessage+" and Actual = "+actualMessage);
        }
    }

    public void softlyAssert(boolean actualCondition,boolean expectedCondition){
        //Hooks.softAssertions.assertThat(actualCondition).isEqualTo(expectedCondition);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualCondition).isEqualTo(expectedCondition);

        List<Throwable> list = softAssertions.errorsCollected();
        if(list.size()>0){
            insertWarningMessageToHtmlReport("ERROR in SoftAssertion \n"+list.get(0).getMessage());
        }else{
            insertMessageToHtmlReport("Soft assertion is successful, Expected = "+expectedCondition+" and Actual = "+actualCondition);
        }
    }
    
    
    public void changeThePlatform(String platformType){
    	Map<String, Map<String, String>> data = getDeserializeDataForPlatformDetails();
    	
    	if(data.containsKey(platformType)){
    		Map<String, String> value = data.get(platformType);
    		//set the new system properties pertaining to new platform details
    		for (Map.Entry<String, String> entry : value.entrySet()) {
                if(!value.entrySet().isEmpty()){
                    System.setProperty(entry.getKey(), entry.getValue());
                    logInfo(entry.getKey().trim().toUpperCase() + " property is set from environment to "
                            + entry.getValue());
                }
			}
    	}else{
    		logWarning("Please check your platform type");
    	}
    }
    
    public Map<String, Map<String, String>> getDeserializeDataForPlatformDetails(){
        Map<String,Map<String,String>> platformDetails = new HashMap<String,Map<String,String>>();

        try{
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/target/platformData");
            ObjectInputStream ois = new ObjectInputStream(fis);
            platformDetails = (Map<String,Map<String, String>>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e){
            logError("Error in getDeserializeDataForPlatformDetails, Error = "+e.getMessage());
            return platformDetails;
        } catch (ClassNotFoundException e){
        	logError("Class not found, Error = "+e.getMessage());
            return platformDetails;
        }
        logInfo("Deserialization of excel data is complete");
        return platformDetails;
    }


}

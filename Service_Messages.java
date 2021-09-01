/*---------------------------------Start <SD_Service_Messages>----------------------------------------
Function Name: SD_Service_Messages
Purpose: To view the messages in message tab
Parameter:
Author Name: CAF Automation 
 Create Date: 12-05-2021
Modified Date| Modified By  |Modified purpose 
 12/05/2021      C113329       Code completion
-----------------------------------End <SD_Service_Messages>---------------------------------------
*/

package com.boi.grp.pageobjects.Services;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
       
public class Service_Messages extends BasePageForAllPlatform{
	
	public UIResusableLibrary cafFunctional;
	
	Service_SS_ManageAccountPage manageAcc;
	Service_SS_ServicesManageCards manageCards;
		
	By ServiceTab = getObject("mw.sd.tabServices");
	By MessageBtn = getObject("sd.buttonMessage");
	By SentTab = getObject("sd.tabSent");
	By MessageLine = getObject("sd.messageLine");
	By MessageValidation = getObject("sd.messageTextVal");
	By Inbox = getObject("sd.tabInbox");
	By Message = getObject("sd.confirmMessage");
	By actMessage = getObject("sd.actualMessage");
	By SentMesRefNum = getObject("sd.txtSentMesReferenceNum");
	
	public Service_Messages(WebDriver driver) {

		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional= new UIResusableLibrary(driver);
	}
	
	/*---------------------------------Start <SD_SS_ServMsgs>----------------------------------------
	Function Name: SD_SS_ServMsgs
	Purpose: To view the messages in message tab
	Parameter:
	Author Name: CAF Automation 
	 Create Date: 12-05-2021
	Modified Date| Modified By  |Modified purpose 
	 12/05/2021      C113329       Code completion
	-----------------------------------End <SD_SS_ServMsgs>---------------------------------------
	*/
	
	public void SD_SS_ServMsgs(String serMsgOpt, String serOpt){
        try{
        	waitForElementToBeDisplayed(ServiceTab);
			clickJS(ServiceTab, "Services Tab Clicked");
        	clickJS(MessageBtn);
            boolean bFlagSerMsgOptTab = false;
            logMessage("SERVICES MESSAGES OPTION TYPE : "+serMsgOpt);
                  
      switch (serMsgOpt.toUpperCase()) {                 
      case "INBOX":
        if(isElementDisplayed(Inbox)){
               SD_SerMsg_Inbox_P();
               bFlagSerMsgOptTab = true;
        }else{
               bFlagSerMsgOptTab=false;
        }
           break;
          
      case "SENT_MESSAGES":
        if(isElementDisplayed(SentTab)){
        	SD_ServMsg_Sent_P(serOpt);
               bFlagSerMsgOptTab = true;
        }else{
               bFlagSerMsgOptTab=false;
        }             
        break;
      }
		if(bFlagSerMsgOptTab==true){
        appendScreenshotToCucumberReport();
                     logMessage(serMsgOpt+" Click on Messages tile successfully in SD_Service_Messages function ");
                     injectMessageToCucumberReport(serMsgOpt+" Click on Messages tile successfully");
                     Allure.step("Click on Messages tile successfully ",Status.PASSED);
      }else{
        logMessage(serMsgOpt+" Click on Messages tile NOT successfully");
        injectMessageToCucumberReport(serMsgOpt+" Failure in Click on Services tab ");
         appendScreenshotToCucumberReport();
      }
        }catch (Exception e) {
               logError("Error Occured While clicking on Services Tab " +e.getMessage());
               injectWarningMessageToCucumberReport("Failure in Navigation,Copy,Session,Services functionality "+e.getMessage());
               appendScreenshotToCucumberReport();
               Allure.step("Failure in Navigation,Copy,Services functionality", Status.FAILED);
        
        }
        
 }
	
	/*---------------------------------Start <SD_SerMsg_Inbox_P>----------------------------------------
	Function Name: SD_SerMsg_Inbox_P
	Purpose: To view the messages in message tab
	Parameter:
	Author Name: CAF Automation 
	 Create Date: 00-00-0000
	Modified Date| Modified By  |Modified purpose 
	 00/00/0000      C113329       Code completion
	-----------------------------------End <SD_SerMsg_Inbox_P>---------------------------------------
	*/
	
	private void SD_SerMsg_Inbox_P() {
		System.out.println("User Click on Inbox tab");
		//TODO
	}

	/*---------------------------------Start <SD_ServMsg_Sent_P>----------------------------------------
	Function Name: SD_ServMsg_Sent_P
	Purpose: To view the messages in message tab
	Parameter:
	Author Name: CAF Automation 
	 Create Date: 12-05-2021
	Modified Date| Modified By  |Modified purpose 
	 12/05/2021      C113329       Code completion
	-----------------------------------End <SD_ServMsg_Sent_P>---------------------------------------
	*/
	
	public void SD_ServMsg_Sent_P(String serviceOpt) throws Exception{
		try {
			//services.ClickOnServicesTab();		
			if(isElementDisplayed(SentTab)){
				clickJS(SentTab);
				clickMessageLine(serviceOpt);
				}
				
			} catch (Exception e) {
			logError("Error Occured inside SD_ServMsg_Sent_P " +e.getMessage());
			/*
			appendScreenshotToCucumberReport();
			Allure.step("Failure in Services Sent Message functionality", Status.FAILED);*/
		
		}
	}
	

	//Click on message & check message format
	private void clickMessageLine(String serviceOpt) throws Exception{
		try {
			
			clickJS(MessageLine);
			String actualrefNum = " ";
			switch (serviceOpt.toUpperCase()) {                 
		      case "MANAGE_ACCOUNT":
		    	    actualrefNum = manageAcc.setReferNum();
					break;
		    	  
		      case "MANAGE_CARDS":
		    	  actualrefNum = manageCards.setReferNum();
				  break;
		    	  
			}
		    	  
			String sentRefNum = driver.findElement(SentMesRefNum).getText();
			String expRefNum = sentRefNum.substring(4, 18);
			Assert.assertEquals(expRefNum, actualrefNum);
			
			appendScreenshotToCucumberReport();
            logMessage(serviceOpt+" Click on Messages line successfully in clickMessageLine function ");
            injectMessageToCucumberReport(serviceOpt+" Click on Messages line successfully");
            Allure.step("Click on Messages line successfully ",Status.PASSED);
			
			
		} catch (Exception e) {
			logError("Error Occured inside clickMessageLine " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in clickMessageLine functionality "+e.getMessage());
			appendScreenshotToCucumberReport();
			//Allure.step("Failure in clickMessageLine functionality", Status.FAILED);
		
		}
	}
	
	
}

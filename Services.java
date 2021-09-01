package com.boi.grp.pageobjects.Services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import junit.framework.Assert;

public class Services extends BasePageForAllPlatform {

	private static final boolean False = false;
	public UIResusableLibrary cafFunctional;

	Service_SS_ServicesManageCards manageCards;

	By ServiceTab = getObject("sd.tabServices");
	

	public Services(WebDriver driver) {

		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional= new UIResusableLibrary(driver);
	}
	
	/*Verify the Services Tab*/
	public void ClickOnServicesTab() throws InterruptedException {
		
		try {
			if (isElementDisplayed(getObject(devTypeToGetProperty +"sd.tabServices"))) {
				clickJS(getObject(devTypeToGetProperty +"sd.tabServices"));
				
				Thread.sleep(5000);	
				appendScreenshotToCucumberReport();
				logMessage("Click on Service tab successfully in ClickOnServicesTab function ");
				injectMessageToCucumberReport("Click on Services tab successfully");
				Allure.step("Click on Services tab Successfully ",Status.PASSED);
			} else {
				logMessage("Services tab is not clicked ");
			} 
			//waitForElementToBeDisplayed(ServiceTab);
			//clickJS(ServiceTab, "Services Tab Clicked");
			
			
		} catch (Exception e) {
			logError("Error Occured inside ClickOnServicesTab" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Click on Services tab "+e.getMessage());
			Allure.step("Click on Services tab NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}
	
	/*public void service_NavigateTo(String serMenuOpt, String serMCOpt, String serMAOpt){
		
		try {
				boolean bFlagServiceMenu = False;
				switch (serMenuOpt.toUpperCase()) {                 
	            case "MANAGE_CARDS":          	
	                if(isElementDisplayed(ManageCards)){      //TODO Update xpath
	               // 	manageCards.SD_SS_ManageCards(serMCOpt);  //TODO 
	                	manageCards.serMC_navigateTo();
	                	bFlagServiceMenu=true;
	                }
	                else{
	                	bFlagServiceMenu=false;
	                }
	                break;
	                
	            case "MANAGE_ACCOUNTS":          	
	                if(isElementDisplayed(ManageAccounts)){     //TODO Update xpath
	                	lostOrStolenCardsPageValidation();
	                	bFlagServiceMenu=true;
	                }
	                else{
	                	bFlagServiceMenu=false;
	                }
	                break;
	                
	            case "SECURITY_DEVICES":          	
	                if(isElementDisplayed(LostOrStolenCards)){
	                	lostOrStolenCardsPageValidation();
	                	bFlagServiceMenu=true;
	                }
	                else{
	                	bFlagServiceMenu=false;
	                }
	                break;
	                
	            case "NEEDHELP":          	
	                if(isElementDisplayed(LostOrStolenCards)){
	                	lostOrStolenCardsPageValidation();
	                	bFlagServiceMenu=true;
	                }
	                else{
	                	bFlagServiceMenu=false;
	                }
	                break;
	                
	            case "MORE_SERVICES":          	
	                if(isElementDisplayed(LostOrStolenCards)){
	                	lostOrStolenCardsPageValidation();
	                	bFlagServiceMenu=true;
	                }
	                else{
	                	bFlagServiceMenu=false;
	                }
	                break;
	                
	            case "MESSAGES":          	
	                if(isElementDisplayed(LostOrStolenCards)){
	                	lostOrStolenCardsPageValidation();
	                	bFlagServiceMenu=true;
	                }
	                else{
	                	bFlagServiceMenu=false;
	                }
	                break;
	            
				appendScreenshotToCucumberReport();
				logMessage("Click on Service tab successfully in ClickOnServicesTab function ");
				injectMessageToCucumberReport("Click on Services tab successfully");
				Allure.step("Click on Services tab Successfully ",Status.PASSED);
			

		} catch (Exception e) {
			logError("Error Occured inside ClickOnServicesTab" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Click on Services tab "+e.getMessage());
			Allure.step("Click on Services tab NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}

		
	}
*/	
	public void Services_VerifyMoreFields(){
		try {
			//TO DO: Verify More field for function
			clickJS(getObject(getDeviceType() +"home.lnkServiceDesk"),"Services link ");
									
			boolean displayFlag=false;
			boolean viewFlagATM = isElementDisplayed(getObject("service.lnkSerHelpFindATM"), 5);//TODO : Add xpath
			displayFlag = viewFlagATM;
			
			boolean viewFlagloststolen = isElementDisplayed(getObject("mc.btnMoreReqLostorStolen"), 5);//TODO : Add xpath
			if(viewFlagloststolen==false){
				displayFlag = true;
			}
			
			boolean viewFlagOrderRep = isElementDisplayed(getObject("mc.btnMoreReqReplaceCard"), 5);//TODO : Add xpath
			if(viewFlagOrderRep==false){
				displayFlag = true;
			}
			if(displayFlag=true){
				logInfo("Verify More requirement changes on Services page successfully in Services_VerifyMoreFields function");
				injectMessageToCucumberReport("Verify More requirement changes on Services page successfully");
				appendScreenshotToCucumberReport();
				Allure.step("Verify More requirement changes on Services page successfully ",Status.PASSED);
			}
			else
			{
				logError("Verify More requirement changes on Services page successfully in Services_VerifyMoreFields function");
				injectWarningMessageToCucumberReport("Failure in Verify More requirement changes on Services page ");
				appendScreenshotToCucumberReport();
				Allure.step("Verify More requirement changes on Services page NOT successfully", Status.FAILED);
				Assert.assertEquals(true,displayFlag);
			}
		} catch (Exception e) {
			logError("Error Occured inside Services_VerifyMoreFields " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify More requirement changes on Services page "+e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Verify More requirement changes on Services page NOT successfully", Status.FAILED);
		}
	}
}

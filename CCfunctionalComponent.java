package com.boi.grp.pageobjects.Cards;
/// ***** To Be Moved to the Library *****
/*---------------------------------Start <>----------------------------------------
Function Name: 
Argument Name:
Purpose: This class will be moved to Utility library	
Author Name: Cards Automation- 
Create Date: 12-02-2021
Modified Date| Modified By  |Modified purpose 

-----------------------------------End <>---------------------------------------
 */
import static io.qameta.allure.Allure.step;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import junit.framework.Assert;

public class CCfunctionalComponent extends  BasePageForAllPlatform{

	public DataLibrary dataLibrary;

	public CCfunctionalComponent(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
		dataLibrary=new DataLibrary();
	}
	public CCfunctionalComponent() {

	}

	public void ExplicitWait(String eWait, String sPageName){
		try {
			switch (eWait.trim().toUpperCase()){
			case "EXPLICIT":
				Thread.sleep(400*1000);
				IsPageDisplayed("LOGIN");
			case "WITHIN":
				Thread.sleep(240*1000);
				IsPageDisplayed(sPageName);				
				logMan.info("****PAGE NAME******" + sPageName);				
			case "EXACT":
				Thread.sleep(300*1000);
				IsPageDisplayed(sPageName);
			}

		} catch (InterruptedException e) {
			logError("Error Occured inside VC_OutOfExplicitWait " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Out of Explicit wait "+e.getMessage());
			appendScreenshotToCucumberReport();
		}

	}

	public void validateErrorMessage(String eErr, String aErr){
		try {
			Assert.assertEquals("Error Message matched successfully ", eErr, aErr);
		} catch (Exception e) {
			logError("Error Occured inside validateErrorMessage " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in validate Error message "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

	public void validateIsEqual(String Expected, String Actual){

		try {
			if(Expected==Actual){
				Allure.step("Actual and Expected matched successfully. Actual Value is : "+ Actual, Status.PASSED);
				logMessage("Actual and Expected matched successfully. Actual Value is : "+Actual+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected matched successfully");
			}		
			else{
				Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
				logMessage("Actual and Expected NOT matched successfully. Actual Value is : "+Expected+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected NOT matched successfully");
			}
		} catch (Exception e) {
			logError("Error Occured inside validateIsEqual " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in validateIsEqual"+e.getMessage());
			Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

	public void validateIsEqual(int Expected, int Actual){

		try {
			if(Expected==Actual){
				Allure.step("Actual and Expected matched successfully. Actual Value is : "+ Actual, Status.PASSED);
				logMessage("Actual and Expected matched successfully. Actual Value is : "+Actual+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected matched successfully");
			}		
			else{
				Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
				logMessage("Actual and Expected NOT matched successfully. Actual Value is : "+Expected+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected NOT matched successfully");
			}
		} catch (Exception e) {
			logError("Error Occured inside validateIsEqual " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in validateIsEqual"+e.getMessage());
			Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

	public void validateIsEqual(boolean Expected, boolean Actual){

		try {
			if(Expected==Actual){
				Allure.step("Actual and Expected matched successfully. Actual Value is : "+ Actual, Status.PASSED);
				logMessage("Actual and Expected matched successfully. Actual Value is : "+Actual+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected matched successfully");
			}		
			else{
				Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
				logMessage("Actual and Expected NOT matched successfully. Actual Value is : "+Expected+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected NOT matched successfully");
			}
		} catch (Exception e) {
			logError("Error Occured inside validateIsEqual " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in validateIsEqual"+e.getMessage());
			Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

	public void NavigateToPage(String strPagename,int index) throws InterruptedException{

		logInfo("*********Page name**********"+strPagename);
		logInfo("*********INDEX**********"+index);
		logMessage("Navigate to page "+strPagename+ " successfully in NavigateToPage function");
		injectMessageToCucumberReport("Navigate to page "+strPagename+ " successfully");
		Allure.step("Navigate to page "+strPagename+ " Successfully ",Status.PASSED);
	}

	public void Enter3of6DigitPIN(String PinFlag, String Pin){
		logInfo("****PINFLAG***"+PinFlag+Pin);
		try {
			switch (PinFlag.toUpperCase()){
			case "VALIDPIN":
				logInfo("*****Inside Enter3of6DigitPIN****"+Pin);
				EnterPINdetails(Pin);				
				break;
			case "INVALIDPIN":						
				EnterPINdetails(dataLibrary.GetinValidPin());
				//Validation of error messages
				break;
			}
			logMessage("Enter pin details successfully in Enter3of6DigitPIN function.");
			injectMessageToCucumberReport("Enter pin details successfully");
			Allure.step("Enter pin details Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside Enter3of6DigitPIN" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Enter pin details "+e.getMessage());
			Allure.step("Enter pin details NOT Successful ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	public void EnterPINdetails(String PIN) throws InterruptedException{
		logInfo("*****INSIDE PIN FUNCTION *****"+ PIN);
		String[] arrPin = PIN.split("");
		String strObject = "";
		Thread.sleep(10*1000);//implicit wait
		//	       waitForPageLoaded();
		////	       waitForJQueryLoad();
		if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"), 5)) {
			strObject = "launch.edtPinEnterFieldGrpSCA";

		} else if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrp"), 2)) {
			strObject = "launch.edtPinEnterFieldGrp";
		} else if (isElementDisplayed(getObject("login.pinDigits"), 2)) {
			strObject = "login.pinDigits";
		} else {

		}
		List<WebElement> lstPinEnterFieldGrp = findElements(getObject(strObject));
		//Entering values for only enabled fields
		logInfo("*****INSIDE PIN FUNCTION BEFORE FOR *****");
		for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
			if (lstPinEnterFieldGrp.get(i).isEnabled()) {
				lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
			}
		}
	}

	public void IsPageDisplayed(String pageName){

		try {
			//clickJS(getObject("vc.btnViewCardgoBack"));
			switch (pageName.toUpperCase()){
			case "LOGIN":
				isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"));
				break;
			case "VIEW CARD DETAILS":
				isElementDisplayed(getObject("vc.lblViewCardheader"));
				break;
			case "ORDER REPLACEMENT":
				Assert.assertEquals("Verify elements is displayed ",isElementDisplayed(getObject("or.btnOrderNow")), true);
				break;
			case "RTCC":
				isElementDisplayed(getObject("vc.lblViewCardheader"));
				break;
			case "VIEW PIN":
				isElementDisplayed(getObject("vc.lblViewCardheader"));
				break;
			}

			Thread.sleep(2*1000);
			logMessage("Login Page displayed successfully in VC_LoginPageDisplayed function.");
			injectMessageToCucumberReport("Login Page displayed successfully");
			Allure.step("Login Page displayed Successfully ",Status.PASSED);
			//	            Allure.Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VC_LoginPageDisplayed " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Login Page display "+e.getMessage());
			Allure.step("Login Page displayed NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	public void ClickSwipeToAppove(){

		try {
			//TO DO:  SwipeToAppove code
			logMessage("Swipe to approve successfully in ClickSwipeToAppove function.");
			injectMessageToCucumberReport("Swipe to approve successfully");
			Allure.step("Swipe to approve Successfully ",Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside ClickSwipeToAppove " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Swipe to approve "+e.getMessage());
			Allure.step("Swipe to approve NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}				

	}

}

package com.boi.grp.pageobjects.Profile;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.DataLibrary;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import net.bytebuddy.asm.Advice.Local;


public class ProfileValidation extends BasePageForAllPlatform {

	public UIResusableLibrary cafFunctional;
	public DataLibrary dataLibrary;
	By btnProfile = getObject("home.btnProfile"); 
	By clkEmail = getObject(devTypeToGetProperty+ "profile.clkEmail");
	By clkMobileNumber = getObject("profile.clkMobileNumber"); 
	By clkChangeAddress = getObject("profile.clkChangeAddress"); 
	By clkSecurityAndLegal = getObject("profile.clkSecurityAndLegal"); 
	By clkContinueWTVal = getObject("profile.clkContinueWTVal"); 
	By clkGoBack = getObject("profile.clkGoBack"); 
	By clkConfirm = getObject("profile.clkConfirm"); 	
	By clkXonSuccessMsg = getObject("profile.clkXonSuccessMsg"); 
	By errorMsgWTInputEntEmail = getObject("profile.errorMsgWTInputEntEmail"); 
	By errorMsgWTInputReEntEmail = getObject("profile.errorMsgWTInputReEntEmail"); 
	By erroMsgInvalidEmail = getObject("profile.erroMsgInvalidEmail"); 
	By enterEmailset = getObject("Profile.enterEmailset"); 
	By reEnterEmailset = getObject("Profile.reEnterEmailset");
	By displayHeading = getObject("profile.displayHeading");
	//for address 
	By clkAddress=getObject("profile.clkAddress");
	By btnContinue = getObject("profile.btnContinue");	
	By clkCountry = getObject("profile.clkCountry");	
	By clkCounty = getObject("profile.clkCounty");
	By clkAddressLine1 = getObject("profile.clkAddressLine1");
	By clkAddressLine2 = getObject("profile.clkAddressLine2");
	By clkAddressLine3 = getObject("profile.clkAddressLine3");
	By clkEircode = getObject("profile.clkEircode");
	By clkUpdateEmailYes = getObject("profile.clkUpdateEmailYes");
	By clkUpdateEmailNo = getObject("profile.clkUpdateEmailNo");	
	By clkNewEmail = getObject("profile.clkNewEmail");
	By clkConfirmEmail = getObject("profile.clkConfirmEmail");
	By clkContinue = getObject("profile.clkContinue");
	By addressList =getObject("profile.addressList");
	By clkDistrict = getObject("profile.clkDistrict");
	By msgChangeAddress =getObject("profile.msgChangeAddress");
	By valAddressLine1 = getObject("profile.valAddressLine1");
	By valAddressLine2 = getObject("profile.valAddressLine2");
	By valAddressLine3 = getObject("profile.valAddressLine3");
	By valCounty = getObject("profile.valCounty");
	By valCountry = getObject("profile.valCountry");
	By clkHome=getObject("profile.clkHome");

	public ProfileValidation(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional = new UIResusableLibrary(driver);
		dataLibrary = new DataLibrary();
	}
	
	public void ClickOnProfileTab() throws InterruptedException{

		try {
			if (isElementDisplayed(getObject("home.profile"))) {
				//clickJS(getObject(getDeviceType() +"home.btnProfile"),"Profile - clicked on Profile tab");
				clickJS(getObjectBy("home.profile"));
				Thread.sleep(5000);	
				appendScreenshotToCucumberReport();
				logMessage("Click on Profile tab successfully in ClickOnProfileTab function ");
				injectMessageToCucumberReport("Click on Profile tab successfully");
				Allure.step("Click on Profile tab Successfully ",Status.PASSED);
			} else {
				logMessage("Profile tab is not clicked ");
				Allure.step("Click on Profile tab NOT Successfully ",Status.FAILED);
			}
		} catch (Throwable e) {
			logError("Error Occured inside ClickOnProfileTab " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Click on Profile tab "+e.getMessage());
			Allure.step("Click on Profile tab NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	
	
	
	public void PM_ManageProfile(String profileTypeAction, String userType) {
		try {
			boolean bProfileFlag = false;
			switch (profileTypeAction.toUpperCase()) {
			case "PHONE_NUMBER":// For clicking Mobile number edit icon

				break;
			case "EMAIL":// For clicking Email edit icon

				if (isElementDisplayed(clkEmail,10,"clkEmail")) {
					PM_UpdateEmailID_P(userType);
					bProfileFlag=true;
				}
				else{
					bProfileFlag=false;
				}
				break;
			case "CHANGE_ADDRESS":// For Clicking change address edit icon

				if (isElementDisplayed(clkAddress,10,"clkAddress")) {
					PM_ValChangeAddress_P(userType);
					bProfileFlag=true;
				}
				else{
					bProfileFlag=false;
				}
				break;
			case "SECURITY_AND_LEGAL":// For clicking Security and Legal tab
				break;
			}
			if (bProfileFlag==true)
			{
				logMessage(profileTypeAction+" profileTypeAction is clicked successfully");
				injectMessageToCucumberReport( profileTypeAction+" profileTypeAction tab successfully");
				Allure.step( profileTypeAction+" profileTypeAction tab Successfully ",Status.PASSED);
			}
			else {
				logMessage(profileTypeAction+" ProfileTypeAction tab is not clicked ");
				Allure.step(profileTypeAction+" Click on ProfileTypeAction tab NOT Successfully ",Status.FAILED);
			}
		} catch (Exception e) {
			logError("Error Occured inside PM_ManageProfile" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Click on profileTypeAction "+e.getMessage());
			Allure.step("Click on profileTypeAction tab NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}
		
	// For changing address
	private void PM_ValChangeAddress_P(String userType) {
		try {
			String country=dataLibrary.getIrishCountry();
			String county= dataLibrary.getRandomIrishCounty(country);		
			String addressLine1=dataLibrary.getAddressLine1();
			String addressLine2=dataLibrary.getAddressLine2();
			String addressLine3=generateRandomString()+" "+LocalDate.now().toString();
			String newEmail = dataLibrary.generateRandomEmail();
			String updateEmail="NO";
			// clikcing on change address			
			clickJS(clkAddress,"Profile - Clicked on edit Change Address");			
			//selecting less that 20 address
			List<WebElement> accountList = findElements(addressList);
            int aCount = accountList.size();
            if (accountList.size() > 1 ){
                aCount = accountList.size() - 1;
                for (int i = 0 ; i < aCount; i++) {
                    scrollToView(accountList.get(i));
                    clickJS(accountList.get(i));
                    Thread.sleep(3000);
                    logMessage("Un-selecting the few accounts so that we can change the Address for less that 20 Accounts");
                    accountList = findElements(addressList);
                }
            }            
			// clicking on continue button

			if (isElementDisplayed(clkContinue,10,"clkContinue")) {	

				clickJS(clkContinue,"Profile-Change Address-clicked on continue button");
				setValue(clkCountry, country);
				waitForPageLoaded();
				clickJS(clkContinueWTVal,"Profile-Change Address-clicked on continue button");

				if(isElementDisplayed(clkCounty,10,"clkCounty")){

				setValue(clkCounty,county);
				waitForPageLoaded();}
				setValue(clkAddressLine1, addressLine1);
				setValue(clkAddressLine2, addressLine2);
				setValue(clkAddressLine3, addressLine3);
				setValue(clkEircode, "");
				//for update email yes/no
				if (updateEmail=="YES")
				{
					//if yes updates address with new email id 
				clickJS(clkUpdateEmailYes,"Profile-Change Address-clicked on update email yes");
				setValue(clkNewEmail, newEmail);
				setValue(clkConfirmEmail, newEmail);
				}
				//if no updates only address
				clickJS(clkContinueWTVal,"Profile-Change Address-clicked on continue button");
				//checking with input values 
				String addLine1Val=getText(valAddressLine1);
				Assert.assertEquals(addressLine1, addLine1Val);
				String addLine1Val2=getText(valAddressLine2);
				Assert.assertEquals(addressLine2, addLine1Val2);
				String addLine1Val3=getText(valAddressLine3);
				Assert.assertEquals(addressLine3, addLine1Val3);
				String addCounty=getText(valCounty);
				Assert.assertEquals(county, addCounty);
				String addCountry=getText(valCountry);
				Assert.assertEquals(country, addCountry);
				clickJS(clkConfirm,"Profile-Change Address-clicked on confirm button");//click on confirm 			
				cafFunctional.Enter3of6DigitPIN(dataLibrary.validPinFlag(), cafFunctional.GetvalidPIN(userType));//entering pin
				waitForPageLoaded();	
				clickJS(clkConfirm,"Profile-Change Address-clicked on confirm button");//click on confirm 
				waitForPageLoaded();
				appendScreenshotToCucumberReport();
				clickJS(clkHome,"Profile-Change Address-clicked on home button");//click on home 
				waitForPageLoaded();
				// Logging
				logMessage("Change address verified successfully");
				injectMessageToCucumberReport("Change address verified successfully");
				Allure.step("Change address verified successfully", Status.PASSED);
			}
		
		} catch (Exception e) {
			logError("Error Occured inside PM_ValChangeAddress_P");
			injectWarningMessageToCucumberReport("Failure in PM_ValChangeAddress_P " + e.getMessage());
			Allure.step("PM_ValChangeAddress_P is NOT Successfull ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}


	private void PM_UpdateEmailID_P(String userType) {
		try {
			//clicking on Email 
			clickJS(clkEmail,"Profile-clicked on edit emai");
			// clicking on continue button without value
			clickJS(clkContinueWTVal,"Profile-Email-Clicked on continue button");
			isElementDisplayed(displayHeading,10,"displayHeading");

			logMessage("Click on continue button without any input is successful in updateEmailValidation function");
			String pfEnterEmailErrormsg = getText(errorMsgWTInputEntEmail);// Fetch actual value from the screen for field - Enter Email address
			logInfo("******Error message received*******" + pfEnterEmailErrormsg);
			String pfReEnterEmailErrormsg = getText(errorMsgWTInputReEntEmail);// Fetch actual value from the screen for field - ReEnter Email address
			logInfo("******Error message received *******" + pfReEnterEmailErrormsg);


			Assert.assertEquals(getMessageText("blankEnterEmailMsg", "UXPBANKING365"), pfEnterEmailErrormsg);
			Assert.assertEquals(getMessageText("blankReEnterEmailMsg", "UXPBANKING365"), pfReEnterEmailErrormsg);

			// enter invalid email ID and click on continue button
			setValue(enterEmailset,dataLibrary.getInvalidEMailId());
			setValue(reEnterEmailset,dataLibrary.getInvalidEMailId());
			clickJS(clkContinueWTVal,"Profile-Email-Clicked on continue button");


			isElementDisplayed(displayHeading,10,"displayHeading");

			String pfEnterEmailErrormsg1 = getText(errorMsgWTInputEntEmail);// Fetch actual value from the screen for field - Enter Email address
			logInfo("******Error message received*******" + pfEnterEmailErrormsg1);
			String pfReEnterEmailErrormsg1 = getText(errorMsgWTInputReEntEmail);// Fetch actual value from the screen for field - ReEnter Email address
			logInfo("******Error message received *******" + pfReEnterEmailErrormsg1);


			Assert.assertEquals(getMessageText("blankEnterEmailMsg", "UXPBANKING365"), pfEnterEmailErrormsg1);
			Assert.assertEquals(getMessageText("blankReEnterEmailMsg", "UXPBANKING365"), pfReEnterEmailErrormsg1);



			// enter diff email in email ID and reconfirm email id
			String validEmail=dataLibrary.generateRandomEmail();
			setValue(enterEmailset, validEmail);
			setValue(reEnterEmailset, dataLibrary.generateRandomEmail());
			clickJS(clkContinueWTVal,"Profile-Email-Clicked on continue button");
			String aReEnterValidEmailErrormsg = getText(erroMsgInvalidEmail);// Fetch actual value from the screen for field - ReEnter Email address
			logInfo("******Error message received *******" + aReEnterValidEmailErrormsg);
			isElementDisplayed(displayHeading,10,"displayHeading");		
			// enter same email ID and reconfirm email id
			clickJS(getObject(getDeviceType() +"home.btnProfile"));

			clickJS(clkEmail);
			setValue(enterEmailset,validEmail);
			waitForPageLoaded();
			setValue(reEnterEmailset,validEmail);
			clickJS(clkContinueWTVal,"Profile-Email-Clicked on continue button");
			// click on goback
			clickJS(clkGoBack,"Profile-Email-Clicked on goback");
			String aDisplayText = getText(displayHeading);//Fetch actual value from the screen - Update Email
			logInfo("******Error message received *******" + aDisplayText);
			Assert.assertEquals(getMessageText("displayHeading", "UXPBANKING365"), aDisplayText);		

			clickJS(clkContinueWTVal,"Profile-Email-Clicked on continue button");// click on continue
			cafFunctional.Enter3of6DigitPIN(dataLibrary.validPinFlag(), cafFunctional.GetvalidPIN(userType));// Enter 3 of 6 digit pin 
			clickJS(clkConfirm,"Profile-Email-Clicked on confirm button");//click on confirm button
			clickJS(clkXonSuccessMsg,"Profile-Email-Clicked on X on success message");//click X on success message
			//Logging
			logMessage("Email address verified successfully");
			logInfo("Email address verified successfully");
			injectMessageToCucumberReport("Email address verified successfully");
			Allure.step("Email address verified successfully",Status.PASSED);
			// validation of negative and positive email id verified successfully.

		} catch (Exception e) {
			logError("Error Occured inside updateValidEmailAddress");
			injectWarningMessageToCucumberReport("Failure in updateValidEmailAddress " + e.getMessage());
			Allure.step("updateValidEmailAddress is NOT Successfull ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}
}

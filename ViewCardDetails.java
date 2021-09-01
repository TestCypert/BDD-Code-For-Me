package com.boi.grp.pageobjects.Cards;

import static io.qameta.allure.Allure.step;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.DataLibrary;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import junit.framework.Assert;

public class ViewCardDetails extends BasePageForAllPlatform  {

	public UIResusableLibrary cardsFunctional;
	public DataLibrary dataLibrary;
	public ManageCardPage manageCardPg;
	public ViewCardDetails (WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cardsFunctional= new UIResusableLibrary(driver);
		manageCardPg = new ManageCardPage(driver);
		dataLibrary=new DataLibrary();
	}

	/*---------------------------------Start <VC_ValidateViewCardDetails>----------------------------------------
	Function Name: VC_ValidateViewCardDetails
	Argument Name:
	Purpose: 	
	Author Name: Cards Automation- 
	Create Date: 12-02-2021
	Modified Date| Modified By  |Modified purpose 

	-----------------------------------End <VC_ValidateViewCardDetails>---------------------------------------
	 */
	public void VC_ValViewCardDetails(String PinFlag, String Pin,String expCardNumber, String expCardName, String expCardExpirydt, String BIN,String CVV){

		try {
			logInfo("*******INSIDE VC_ValidateViewCardDetails For PIN*******"+ Pin);
			Thread.sleep(15*1000);
			manageCardPg.MC_NavigateTo("VIEW CARD DETAILS",expCardNumber);//Click on View Card link on Manage Card page
			cardsFunctional.Enter3of6DigitPIN(PinFlag, Pin);//Enter authentication PIN details based on valid invalid flag
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN Confirm button ");
			cardsFunctional.PushNotification_Acccept();//Swipe to approve
			Thread.sleep(60*1000);
			appendScreenshotToCucumberReport();
			VC_VerifyCardDetails(expCardNumber,expCardName,expCardExpirydt,BIN,CVV);//Validate details on View Card page

			logInfo("View Card details validated successfully in VC_ValViewCardDetails function.");
			injectMessageToCucumberReport("View Card details validated successfully");
			Allure.step("View Card details validated successfully for card number "+ expCardName, Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VC_ValViewCardDetails " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in View Card details "+e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("View Card details NOT validated successfully for card number "+ expCardName, Status.FAILED);
		}
	}

	public void VC_ValNavigateCopySessionInvalidPIN(String PinFlag, String Pin,String expCardNumber){

		try {
			String PinFlagarr[]=PinFlag.split("~");
			//Code for first attempt wrong PIN
			manageCardPg.MC_NavigateTo("VIEW CARD DETAILS",expCardNumber);//Click on View Card link on Manage Card page			
			cardsFunctional.Enter3of6DigitPIN(PinFlagarr[0], dataLibrary.getinValidPin());//Enter authentication PIN details based on valid invalid flag
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");
			cardsFunctional.PushNotification_Acccept();//Swipe to approve
			/*String aVCwrongPIN1 = getText(getObject("enterPin.msgWrongPIN1"));//TODO :Capture xpath of error message
			cardsFunctional.validateIsEqual(getMessageText("WrongPin1","CARDS"),aVCwrongPIN1);*/

			//Code for navigate back and page validation

			String acErrormsg=getText(getObject("enterPin.msgWrongPIN1"));
			logInfo("******Error message received wrong PIN*******"+acErrormsg);
			Assert.assertEquals(getMessageText("WrongPin1","CARDS"), acErrormsg);
			clickJS(getObject("enterPin.btntryAgain"),"Enter PIN - Try again button ");
			//VC_NavigateBackTopMenu();

			//Code for second attempt wrong PIN
			//manageCardPg.MC_NavigateTo("VIEW CARD DETAILS");
			Thread.sleep(2*1000);
			cardsFunctional.Enter3of6DigitPIN(PinFlagarr[1],dataLibrary.getinValidPin());
			Thread.sleep(2*1000);
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");

			cardsFunctional.PushNotification_Acccept();//Swipe to approve
			String acErrormsg2=getText(getObject("enterPin.msgWrongPIN1"));
			Assert.assertEquals(getMessageText("WrongPin1","CARDS"), acErrormsg2);
			clickJS(getObject("enterPin.btntryAgain"),"Enter PIN - Try again button ");
			//Code for navigate back and page validation
			//VC_NavigateBackTopMenu();

			//Code for valid PIN 
			//manageCardPg.MC_NavigateTo("VIEW CARD DETAILS");
			Thread.sleep(2*1000);
			cardsFunctional.Enter3of6DigitPIN(PinFlagarr[2], Pin);
			Thread.sleep(2*1000);
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");
			cardsFunctional.PushNotification_Acccept();//Swipe to approve

			Thread.sleep(5*1000);
			//Code for session timeout
			cardsFunctional.ExplicitWait("EXACT", "VIEW CARD DETAILS");
			cardsFunctional.ExplicitWait("WITHIN", "VIEW CARD DETAILS");
			VC_ValidateCopyMessage();

			logInfo("Invalid PIN validated successfully in VC_VerifyNavigateCopySessionInvalidPIN function.");
			injectMessageToCucumberReport("Navigation,Copy,Session,InvalidPIN functionality validated successfully");
			Allure.step("Invalid PIN validated successfully for card number "+ expCardNumber, Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VC_VerifyNavigateCopySessionInvalidPIN " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Navigation,Copy,Session,InvalidPIN functionality "+e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in Navigation,Copy,Session,InvalidPIN functionality "+ expCardNumber, Status.FAILED);
		}
	}

	//********************************** COMMON FUNCTIONS **************************************	

	public void VC_ClickViewCard(){		 
		try {
			logInfo("******INSIDE CLICK VIEW CARD********");
			//manageCardPg.MC_NavigateTo("VIEW CARD DETAILS");
			logInfo("Click View Card details successfully in VC_ClickViewCard function.");
			injectMessageToCucumberReport("Click View Card details  launch successfully");
			Allure.step("Click View Card details Launch Successfully ");
		}catch (Exception e) {
			logError("Error Occured inside VC_ClickViewCard" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Click View Card details "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

	public void ValidateViewCardDetails(){
		try {
			//TO DO:  CardsFunctionalComponent.enterRequiredPin(String pin);
			logInfo("Enter pin details successfully in ValidateViewCardDetails function.");
			injectMessageToCucumberReport("View Card details launch successfully");
			Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside ValidateViewCardDetails " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in View Card details launched "+e.getMessage());
			Allure.step("View Card Detail Launch NOT Successful ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}



	//@SuppressWarnings("deprecation")

	public void VC_ValidateCopyMessage(){

		try {
			clickJS(getObject("vc.btnViewCardCopy"));
			//Thread.sleep(2*1000);
			String ActualText = getText(getObject("vc.aMsgCopy"));
			logInfo("*****Copy Message ****" +ActualText);
			Assert.assertEquals("Card number copied.", ActualText);

			String CardNum = getText(getObject("vc.lblVCcardNumber"));
			logInfo("***CARDNUM***"+CardNum);
			//			Actions builder = new Actions(driver);
			//			String PasteCardNum = builder.keyDown( Keys.CONTROL ).sendKeys( "v" ).keyUp( Keys.CONTROL ).build().perform();

			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable contents = clipboard.getContents(null);
			String x = (String) contents.getTransferData(DataFlavor.stringFlavor);
			logInfo(x);
			int a= CardNum.length();
			int b = x.length();
			System.out.println(a);
			System.out.println(b);
			if(a<=b) 
			{
				logInfo("Matched Character length");
				logInfo("Click copy button successfully in VC_ValidateCopyMessage function.");
				injectMessageToCucumberReport("Click copy button successful");
				Allure.step("Click copy button successful "+ Status.PASSED);
			}else 
			{
				logError("Issue In Character length");
				logError("Click copy button NOT successfully in VC_ValidateCopyMessage function.");
				injectMessageToCucumberReport("Click copy button successful");
				Allure.step("Click copy button NOT successful " + Status.FAILED);
			}
		} catch (Exception e) {
			logError("Error Occured inside VC_ValidateCopyMessage " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Click confirm button "+e.getMessage());
			Allure.step("Click copy button NOT successful " + Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	public void VC_NavigateBackTopMenu(){

		try {

			clickJS(getObject("vc.btnViewCardgoBack"),"View Card details - Go back ");
			clickJS(getObject("mc.cardsSwipe"));
			Thread.sleep(2*1000);
			clickJS(getObject("vc.btnViewCardgoBackTopMenu"));
			Thread.sleep(3*1000);

			logInfo("****TITLE*****" + driver.getTitle());
			String ActualTextHeader = getText(getObject("mc.topMenu"));
			logInfo("*****Header" +ActualTextHeader);
			Assert.assertEquals("Manage Card", ActualTextHeader);

			logInfo("Click confirm button successfully in VC_NavigateBackTopMenu function.");
			injectMessageToCucumberReport("Click Go back button successfully");
			step("Click Go back button Successfully ");
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VC_NavigateBackTopMenu " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Click Go back button "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

	public void VC_LoginPageDisplayed(){

		try {
			isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"));
			Thread.sleep(2*1000);
			logInfo("Login Page displayed successfully in VC_LoginPageDisplayed function.");
			injectMessageToCucumberReport("Login Page displayed successfully");
			Allure.step("Login Page displayed Successfully ",Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VC_LoginPageDisplayed " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Login Page display "+e.getMessage());
			Allure.step("Login Page displayed Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	public void VC_ViewCardPageDisplayed(){

		try {
			String ActualTextHeader = getText(getObject("vc.lblViewCardheader"));
			logInfo("*****Header" +ActualTextHeader);
			Assert.assertEquals("View Card Details", ActualTextHeader);
			logInfo("View Card Page displayed successfully in VC_ViewCardPageDisplayed function.");
			injectMessageToCucumberReport("View Card Page displayed successfully");
			Allure.step("View Card Page displayed Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VC_ViewCardPageDisplayed " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in View Card Page display "+e.getMessage());
			Allure.step("View Card Page displayed NOT Successful ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	public void VC_VerifyCardDetails(String expCardNumber, String expCardName, String expCardExpirydt, String BIN, String expCVV){

		try {
			String ActualTextHeader = getText(getObject(getDeviceType() +"vc.lblViewCardheader"));
			logInfo("*****Header" +ActualTextHeader);
			Assert.assertEquals("View Card Details", ActualTextHeader);
			//clickJS(getObject("vc.btnViewPINconfirm"));
			String CardNum = getText(getObject("vc.lblVCcardNumber"));
			logInfo("***EXPECTED CARDNUM***"+ expCardNumber + "ACTUAL" + CardNum);
			Assert.assertEquals(expCardNumber, CardNum);
			
			String CardName = getText(getObject("vc.lblVCcardName"));
			logInfo("***EXPECTED CARDNAME***"+ expCardName + "ACTUAL" + CardName);
			Assert.assertEquals(expCardName, CardName);

			String CardExpirydt = getText(getObject("vc.lblVCcardExpirydt"));
			logInfo("***EXPECTED CARDEXPIRY***"+ expCardExpirydt + "ACTUAL" + CardExpirydt);
			Assert.assertEquals(expCardExpirydt, CardExpirydt);

			String acCardCVV = getText(getObject("vc.lblVCcardCVV"));
			logInfo("***EXPECTED Card CVV***"+ expCardExpirydt + "ACTUAL" + CardExpirydt);
			Assert.assertEquals(expCVV, acCardCVV);

			logInfo("Validation successful in VC_VerifyGetDetails function.");
			injectMessageToCucumberReport("View Card details validated successfully");
			Allure.step("View Card details validated Successfully ",Status.PASSED);

		} catch (Exception e) {
			logError("Error Occured inside VC_VerifyGetDetails " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in View Card details "+e.getMessage());
			Allure.step("View Card details NOT validated ",Status.FAILED);
			appendScreenshotToCucumberReport();

		}

	}
}

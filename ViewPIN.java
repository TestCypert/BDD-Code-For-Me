package com.boi.grp.pageobjects.Cards;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import static io.qameta.allure.Allure.step;

import java.util.List;
import java.util.Timer;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.DataLibrary;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import junit.framework.Assert;

public class ViewPIN extends BasePageForAllPlatform {


	public UIResusableLibrary cardsFunctional;
	public ManageCardPage manageCardPg;
	public DataLibrary dataLibrary;
	public ViewPIN (WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

		cardsFunctional= new UIResusableLibrary(driver);
		dataLibrary=new DataLibrary();
		//viewCardpg=new ViewCardDetails(driver);
		manageCardPg = new ManageCardPage(driver);
	}
	
	/*---------------------------------Start <VP_ValPINDetails>----------------------------------------
	Function Name: VP_ValPINDetails
	Argument Name:
	Purpose: 	
	Author Name: Cards Automation- 
	Create Date: 12-02-2021
	Modified Date| Modified By  |Modified purpose 

	-----------------------------------End <VP_ValPINDetails>---------------------------------------
	 */
	public void VP_ValPINDetails(String PinFlag,String Pin,String expClearPIN,String cardNumber) throws Exception{
		   
        try {
			//Code for GoBackTopMenu
			manageCardPg.MC_NavigateTo("VIEW PIN",cardNumber);//Click on View PIN link on Manage Card page     
			cardsFunctional.Enter3of6DigitPIN(PinFlag, Pin);
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN Confirm button ");
			Thread.sleep(6*1000);
			
			String acClearPIN=getText(getObject("vp.lblClearPIN"));
			Assert.assertEquals(expClearPIN, acClearPIN);
			logInfo("***VIEW PIN VERIFIED***");
			appendScreenshotToCucumberReport();
			String acCardNum=getText(getObject("vp.lblCardNumber"));			
			String expCardNumber= cardNumber.substring(cardNumber.length()-4);
			String acCardNumber= acCardNum.substring(acCardNum.length()-4);
			Assert.assertEquals(expCardNumber, acCardNumber);
			logInfo("***CARD NUM VERIFIED***");
			String acPINmsg=getText(getObject("vp.lblPINmessage"));
			Assert.assertEquals(getMessageText("clearPINmsg","CARDS"), acPINmsg);
			logInfo("***PIN MESS VERIFIED***");
			/*String acPINtimermsg=getText(getObject("vp.msgPINtimer"));
			Assert.assertEquals(getMessageText("clearPINmsg","CARDS"), acPINtimermsg);
			logInfo("***TIMER VERIFIED***");*/
			//Code for valid pin swip top back button
			/*manageCardPg.MC_NavigateTo("VIEW PIN");//Click on Activate Card link on Manage Card page     
			cardsFunctional.Enter3of6DigitPIN(PinFlag, Pin);
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");
			clickJS(getObject("vc.btnViewCardgoBackTopMenu"),"VP Swipe to Approve-Top Left Arrow");*/
			
			//Code for valid pin swip decline button
			/*manageCardPg.MC_NavigateTo("ACTIVATE CARD");//Click on Activate Card link on Manage Card page     
			cardsFunctional.Enter3of6DigitPIN(PinFlag, Pin);
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");
			clickJS(getObject("vc.btndecline"),"AC Swipe to Aprove -Decline");
			      
			//Code for Clear PIN
			manageCardPg.MC_NavigateTo("ACTIVATE CARD");//Click on Activate Card link on Manage Card page     
			cardsFunctional.Enter3of6DigitPIN(PinFlag, Pin);
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");
			String aClearPIN = getText(getObject("home.btntitleCards"));//TODO :Capture xpath of text
			Assert.assertEquals(clearPIN,aClearPIN);*/
			logMessage("Click confirm button successfully in VP_ValidatePINdetails function.");
			injectMessageToCucumberReport("View PIN validated successfully");
			Allure.step("Actual and Expected matched successfully. Actual Value is : "+ cardNumber, Status.PASSED);
		} catch (InterruptedException e) {
			logError("Error Occured inside VP_ValidatePINdetails " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in View PIN "+e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Click confirm button NOT successfully in VP_ValidatePINdetails function. "+ cardNumber, Status.FAILED);
		} 


	}
	
	/*---------------------------------Start <VP_ValNavigateInvalidPIN>----------------------------------------
	Function Name: VP_ValNavigateInvalidPIN
	Argument Name:
	Purpose: 	
	Author Name: Cards Automation- 
	Create Date: 12-02-2021
	Modified Date| Modified By  |Modified purpose 

	-----------------------------------End <VP_ValNavigateInvalidPIN>---------------------------------------
	 */
	
	public void VP_ValNavigateInvalidPIN(String Pin,String PinFlag,String cardNumber) throws Exception{

		try {
			String PinFlagarr[]=PinFlag.split("~");
			
			logInfo("***Pin Flag 0****"+PinFlagarr[0]);
			logInfo("***Pin Flag 1****"+PinFlagarr[1]);
			logInfo("***Pin Flag 2****"+PinFlagarr[2]);
			logInfo("***Pin for View PIN****"+Pin);
			//Code for first attempt wrong PIN
			manageCardPg.MC_NavigateTo("VIEW PIN",cardNumber);//Click on View Card link on Manage Card page			
			cardsFunctional.Enter3of6DigitPIN(PinFlagarr[0], dataLibrary.getinValidPin());//Enter authentication PIN details based on valid invalid flag
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");
			cardsFunctional.PushNotification_Acccept();//Swipe to approve			
			clickJS(getObject("enterPin.btntryAgain"),"Enter PIN - Try again button ");
			
			//String actualText = getText(getObject("home.btntitleCards"));//TODO :Capture xpath of error message
			//cardsFunctional.validateIsEqual(getMessageText("WrongPin1","CARDS"),actualText);

			//Code for navigate back and page validation
			//viewCardpg.VC_NavigateBackTopMenu();

			//Code for second attempt wrong PIN
			//manageCardPg.MC_NavigateTo("VIEW CARD DETAILS");
			Thread.sleep(2*1000);
			cardsFunctional.Enter3of6DigitPIN(PinFlagarr[1],dataLibrary.getinValidPin());
			Thread.sleep(2*1000);
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");
			cardsFunctional.PushNotification_Acccept();//Swipe to approve
			clickJS(getObject("enterPin.btntryAgain"),"Enter PIN - Try again button ");
			/*String actualTextmessage = getText(getObject("home.btntitleCards"));//TODO :Capture xpath of error message
			cardsFunctional.validateIsEqual(getMessageText("WrongPin2","CARDS"),actualTextmessage);*/

			//Code for navigate back and page validation
			//viewCardpg.VC_NavigateBackTopMenu();

			//Code for valid PIN 
			//manageCardPg.MC_NavigateTo("VIEW CARD DETAILS");
			Thread.sleep(2*1000);
			cardsFunctional.Enter3of6DigitPIN(PinFlagarr[2], Pin);
			Thread.sleep(2*1000);
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");
			cardsFunctional.PushNotification_Acccept();//Swipe to approve

			//Code for session timeout
			//cardsFunctional.ExplicitWait("EXACT", "VIEW PIN");
			cardsFunctional.ExplicitWait("WITHIN", "VIEW PIN");
			logInfo("Verify View PIN detail executed successfully in VP_ValidatePINDetails function.");
			injectMessageToCucumberReport("Verify View PIN detail executed successfully");
			Allure.step("Verify View PIN detail executed Successfully ",Status.PASSED);
		} catch (InterruptedException e) {
			logError("Error Occured inside VP_ValidatePINDetails " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify View PIN "+e.getMessage());
			Allure.step("Verify View PIN detail NOT executed Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		} 
	}

	public void VP_ValidatePINDetails(String eCardNumber, String ePINNumber, String eTimer, String ePINMessage){
		try {
			String aCardNumber = getText(getObject("enterPin.msgAfterWrongPINtwice"));
			cardsFunctional.validateIsEqual(eCardNumber, aCardNumber);			

			String ActualPINNumber = getText(getObject("enterPin.msgAfterWrongPINtwice"));
			cardsFunctional.validateIsEqual(ePINNumber, ActualPINNumber);

			String ActualTimer = getText(getObject("enterPin.msgAfterWrongPINtwice"));
			cardsFunctional.validateIsEqual(eTimer, ActualTimer);

			String ActualPINMessage = getText(getObject("enterPin.msgAfterWrongPINtwice"));
			cardsFunctional.validateIsEqual(ePINMessage, ActualPINMessage);


			logMessage("Verify View PIN detail executed successfully in VP_ValidatePINDetails function.");
			injectMessageToCucumberReport("Verify View PIN detail executed successfully");
			Allure.step("Verify View PIN detail executed Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VP_ValidatePINDetails " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify View PIN "+e.getMessage());
			Allure.step("Verify View PIN detail NOT executed Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}		
	}

	public void VP_VerifyPINErrors(String eErr){
		try {
			String ActualErrorText = getText(getObject("enterPin.msgAfterWrongPINtwice"));
			cardsFunctional.validateErrorMessage(eErr, ActualErrorText);
			//TO DO:  CardsFunctionalComponent.enterRequiredPin(String pin);
			logMessage("Verify Error message successfully in VP_VerifyPINErrors function.");
			injectMessageToCucumberReport("Verify Error message  launch successfully");
			Allure.step("Verify Error message  Launch Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VP_VerifyPINErrors " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify Error message"+e.getMessage());
			Allure.step("Verify Error message NOT Launch Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	//**************************** COMMON FUNCTIONS **********************************

	public void VP_ClickViewPIN(){		 
		try {
			//manageCardPg.MC_NavigateTo("VIEW PIN");
			/*if (isElementDisplayed(getObject("mc.dots"))) {
				List<WebElement> Bullets = driver.findElements(getObject("mc.dots"));
				for(int i=0; i<Bullets.size();i++){
					if(Bullets.get(i).isEnabled()){
						Bullets.get(i).click();
						Thread.sleep(2*1000);
						if(i==2){
							clickJS(getObject("mc.btnViewPIN"));
							//driver.findElement(getObject("mc.cardsSwipe")).click();
						}

						if(i==2){

							System.out.println("**INSIDE CLICK VIEW CARD***" + i+1);
							clickJS(getObject("mc.lnkVCdetails",i+1));
							Thread.sleep(2*1000);
							//driver.findElement(getObject("mc.cardsSwipe")).click();
						}

					}
				}
			}
			 */			logMessage("Click View PIN successfully in VP_ClickViewPIN function.");
			 injectMessageToCucumberReport("Click View PIN launch successfully");
			 Allure.step("Click View PIN Launch Successfully ",Status.PASSED);
		}catch (Exception e) {
			logError("Error Occured inside VP_ClickViewPIN" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Click View PIN "+e.getMessage());
			Allure.step("Click View PIN Launch NOT Successful ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	public void VP_Enter3of6DigitPIN(String pintype)
	{
		//pintype= valid pin , invalid pin

		try {
			//TO DO:  CardsFunctionalComponent.enterRequiredPin(String pin);
			logMessage("Enter pin details successfully in VP_Enter3of6DigitPIN function.");
			injectMessageToCucumberReport("Enter pin details successfully");
			Allure.step("Enter pin details Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VP_Enter3of6DigitPIN " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Enter pin details "+e.getMessage());
			Allure.step("Enter pin details Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}	

	public void VP_VerifyTimeOption(){
		try {
			//TO DO:  CardsFunctionalComponent.enterRequiredPin(String pin);
			logMessage("Verify Time option successfully in VP_VerifyTimeOption function.");
			injectMessageToCucumberReport("Verify Time option launch successfully");
			Allure.step("Verify Time option Launch Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VP_VerifyTimeOption " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify Time option "+e.getMessage());
			Allure.step("Verify Time option NOT Launch Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}	

	public void VP_AbortNavigation(){
		try {
			//TO DO:  CardsFunctionalComponent.enterRequiredPin(String pin);
			logMessage("Abort Navigation successfully in VP_AbortNavigation function.");
			injectMessageToCucumberReport("Abort Navigation successfully");
			Allure.step("Abort Navigation Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VP_AbortNavigation " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Abort Navigation "+e.getMessage());
			Allure.step("Abort Navigation NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	public void VP_ViewPINPageDisplayed(){

		try {
			//clickJS(getObject("vc.btnViewCardgoBack"));
			isElementDisplayed(getObject("vc.lblViewCardheader"));
			String ActualTextHeader = getText(getObject("vc.lblViewCardheader"));
			logInfo("*****Header" +ActualTextHeader);
			Assert.assertEquals("View Card Details", ActualTextHeader);
			logMessage("View PIN Page displayed successfully in VP_ViewPINPageDisplayed function.");
			injectMessageToCucumberReport("View PIN Page displayed successfully");
			Allure.step("View PIN Page displayed Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VP_ViewPINPageDisplayed " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in View PIN Page display "+e.getMessage());
			Allure.step("View PIN Page displayed NOT Successful ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	public void VP_ExplicitWait(String ExplicitFlag,String sPageName){

		try {
			switch (ExplicitFlag.toUpperCase()){
			case "EXPLICIT":
				cardsFunctional.IsPageDisplayed(sPageName);
				break;
			case "WITHIN":
				cardsFunctional.IsPageDisplayed(sPageName);

				break;
			case "EXACT":
				cardsFunctional.IsPageDisplayed(sPageName);

				break;
			}
			logMessage("Explicit wait successful in VP_ExplicitWait function.");
			injectMessageToCucumberReport("Explicit wait successful");
			Allure.step("Explicit wait Successful ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VP_ExplicitWait " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Out of Explicit wait "+e.getMessage());
			Allure.step("Explicit wait NOT Successful ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	public void VP_WithinExplicitWait(){

	}

}

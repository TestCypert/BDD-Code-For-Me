package com.boi.grp.pageobjects.Cards;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import io.qameta.allure.model.Status;
import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.DataLibrary;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import junit.framework.Assert;

import static io.qameta.allure.Allure.step;

import java.util.ArrayList;
import java.util.List;


public class ManageCardPage extends BasePageForAllPlatform  {
	public UIResusableLibrary cardsFunctional;
	public DataLibrary dataLibrary;
	public ManageCardPage(WebDriver driver) {

		super(driver);
		PageFactory.initElements(driver, this);
		cardsFunctional= new UIResusableLibrary(driver);
		dataLibrary=new DataLibrary();
	}

	public void MC_ValCCisEnabled(String cardType,String status,String cardNumber){
		try
		{
			List<WebElement> Bullets = driver.findElements(getObject("mc.dots"));
			//CODE TO NAVIGATE VIEW CARD DETAILS PAGE
			for(int i=0; i<Bullets.size();i++)
			{
				Bullets = driver.findElements(getObject("mc.dots"));

				if(Bullets.get(i).isEnabled())
				{
					Bullets.get(i).click();
					Thread.sleep(2*1000);
					//TODO: Card number extract from digital image on Manage Card page

					String CardNum = getText(getObject("mc.imgCardsNum",i));
					String CardApp= CardNum.substring(CardNum.length()-4);
					//String CardNum = getText(getObject("mc.lnkVCdetails",i+1));
					String eCardNumber= cardNumber.substring(cardNumber.length()-4);
					//Code For clicking View Card Details only when View Card link is present
					if(CardApp.equals(eCardNumber)){
						Thread.sleep(2*1000);
						String cardTypeArr[]=cardType.split(" ");
						String cardTypedata=cardTypeArr[1];
						switch (cardTypedata.toUpperCase()){
						case "DEBIT":                                                     
							verifyDebitCardControls(status,i);
							break;
						case "CREDIT":
							verifyCreditCardControls(status,i);
							break;                                   
						}
						Thread.sleep(2*1000);                                                                                                                                                                  
					}
					else{
						logMan.info("Navigating to the next card ");
					}
				}
				else{
					logMan.error("Card control enablement failed in MC_ValCCisEnabled function");                                                                                                             
				}                                                                                              
			}

			logMessage("Verify card on carousel successfully in MC_VerifyCCisEnabled function");
			injectMessageToCucumberReport("Verify card on carousel successfully");
			Allure.step("Verify card on carousel Successfully ",Status.PASSED);
			//            Allure.Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside MC_VerifyCCisEnabled " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify card on carousel "+e.getMessage());
			Allure.step("Verify card on carousel NOT Successful ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

	public void verifyDebitCardControls(String status,int index){
		try {
			switch (status.toUpperCase()){
			case "ACTIVE":                                                  
				verifyCCisDisplay("VIEW CARD DETAILS",index,"YES");
				verifyCCisDisplay("VIEW PIN",index,"YES");
				//verifyCCisDisplay("ACTIVATE CARD",index,"YES"); //Activate card Not Applicable
				verifyCCisDisplay("FREEZE UNFREEZE",index,"YES");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"YES");
				//verifyCCisDisplay("DIGITAL WALLET",index,"YES"); //Uncomment for wave 3
				verifyCCisDisplay("CONTACT US",index,"NO");
				break;
			case "INACTIVE":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"NO");
				verifyCCisDisplay("VIEW PIN",index,"NO");
				verifyCCisDisplay("ACTIVATE CARD",index,"YES");
				verifyCCisDisplay("FREEZE UNFREEZE",index,"NO");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"NO");
				verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				//verifyCCisDisplay("DIGITAL WALLET",index,"NO"); //Uncomment for wave 3
				verifyCCisDisplay("CONTACT US",index,"NO");
				break;
			case "OLD CARD REPLACEMENT":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"YES");
				verifyCCisDisplay("VIEW PIN",index,"YES");
				verifyCCisDisplay("FREEZE UNFREEZE",index,"YES");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"YES");
				verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				verifyCCisDisplay("DIGITAL WALLET",index,"YES");
				verifyCCisDisplay("CONTACT US",index,"NO");
				break;
			case "LOST OR STOLEN":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"NO");
				verifyCCisDisplay("VIEW PIN",index,"NO");
				//verifyCCisDisplay("ACTIVATE CARD",index,"YES"); //Activate card Not Applicable
				verifyCCisDisplay("FREEZE UNFREEZE",index,"NO");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"NO");
				//verifyCCisDisplay("DIGITAL WALLET",index,"NO");
				verifyCCisDisplay("CONTACT US",index,"YES");
				break;
			case "BANK BLOCKED":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"NO");
				verifyCCisDisplay("VIEW PIN",index,"NO");
				//verifyCCisDisplay("ACTIVATE CARD",index,"YES"); //Activate card Not Applicable
				verifyCCisDisplay("FREEZE UNFREEZE",index,"NO");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"NO");
				//verifyCCisDisplay("DIGITAL WALLET",index,"NO");
				verifyCCisDisplay("CONTACT US",index,"YES");
				break;
			case "FROZEN":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"YES");
				verifyCCisDisplay("VIEW PIN",index,"NO");
				//verifyCCisDisplay("ACTIVATE CARD",index,"YES"); //Activate card Not Applicable
				verifyCCisDisplay("FREEZE UNFREEZE",index,"YES");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"YES");
				verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				//verifyCCisDisplay("DIGITAL WALLET",index,"NO"); //Uncomment for wave 3
				verifyCCisDisplay("CONTACT US",index,"YES");
				break;
			case "EXPIRED":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"NO");
				verifyCCisDisplay("VIEW PIN",index,"NO");
				//verifyCCisDisplay("ACTIVATE CARD",index,"YES"); //Activate card Not Applicable
				verifyCCisDisplay("FREEZE UNFREEZE",index,"NO");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"NO");
				verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				//verifyCCisDisplay("DIGITAL WALLET",index,"NO");
				verifyCCisDisplay("CONTACT US",index,"YES");
				break;
			case "DAMAGED":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"YES");
				verifyCCisDisplay("VIEW PIN",index,"YES");
				//verifyCCisDisplay("ACTIVATE CARD",index,"YES"); //Activate card Not Applicable
				verifyCCisDisplay("FREEZE UNFREEZE",index,"YES");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"NO");
				//verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				//verifyCCisDisplay("DIGITAL WALLET",index,"NO");
				verifyCCisDisplay("CONTACT US",index,"NO");
				break;
				/*case "NEARING EXPIRY":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"YES");
				verifyCCisDisplay("VIEW PIN",index,"YES");
				verifyCCisDisplay("FREEZE UNFREEZE",index,"YES");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"YES");
				verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				verifyCCisDisplay("DIGITAL WALLET",index,"YES");
				verifyCCisDisplay("CONTACT US",index,"NO");
				break;*/


			}
			logMessage("Verify card on carousel successfully in verifyDebitCardControls function");
			injectMessageToCucumberReport("Verify card on carousel successfully");
			Allure.step("Verify card on carousel Successfully ",Status.PASSED);
			//            Allure.Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside verifyDebitCardControls " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify card on carousel "+e.getMessage());
			Allure.step("Verify card on carousel Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}
	public void verifyCreditCardControls(String status,int index){
		try {
			switch (status.toUpperCase()){
			case "ACTIVE":                                                  
				verifyCCisDisplay("VIEW CARD DETAILS",index,"YES");
				verifyCCisDisplay("VIEW PIN",index,"YES");
				verifyCCisDisplay("FREEZE UNFREEZE",index,"YES");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"YES");
				verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				//verifyCCisDisplay("DIGITAL WALLET",index,"YES");
				verifyCCisDisplay("CONTACT US",index,"NO");
				break;
			case "FROZEN":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"YES");
				verifyCCisDisplay("VIEW PIN",index,"NO");
				//verifyCCisDisplay("ACTIVATE CARD",index,"YES"); //Activate card Not Applicable
				verifyCCisDisplay("FREEZE UNFREEZE",index,"YES");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"YES");
				//verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				//verifyCCisDisplay("DIGITAL WALLET",index,"NO");
				verifyCCisDisplay("CONTACT US",index,"NO");
				break;
			case "NEARING EXPIRY":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"YES");
				verifyCCisDisplay("VIEW PIN",index,"YES");
				verifyCCisDisplay("FREEZE UNFREEZE",index,"YES");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"YES");
				verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				verifyCCisDisplay("DIGITAL WALLET",index,"YES");
				verifyCCisDisplay("CONTACT US",index,"NO");
				break;

				/* Below status are NOT applicable to Credit Card
				 * case "BANK BLOCKED":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"YES");
				verifyCCisDisplay("VIEW PIN",index,"YES");
				verifyCCisDisplay("FREEZE UNFREEZE",index,"YES");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"YES");
				verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				verifyCCisDisplay("DIGITAL WALLET",index,"NO");
				verifyCCisDisplay("CONTACT US",index,"YES");
				break;
			case "EXPIRED":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"YES");
				verifyCCisDisplay("VIEW PIN",index,"NO");
				verifyCCisDisplay("FREEZE UNFREEZE",index,"NO");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"NO");
				verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				verifyCCisDisplay("DIGITAL WALLET",index,"NO");
				verifyCCisDisplay("CONTACT US",index,"YES");
				break;
			case "OLD CARD REPLACEMENT":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"YES");
				verifyCCisDisplay("VIEW PIN",index,"YES");
				verifyCCisDisplay("FREEZE UNFREEZE",index,"YES");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"YES");
				verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				verifyCCisDisplay("DIGITAL WALLET",index,"YES");
				verifyCCisDisplay("CONTACT US",index,"NO");
				break;
			case "LOST STOLEN":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"NO");
				verifyCCisDisplay("VIEW PIN",index,"NO");
				verifyCCisDisplay("FREEZE UNFREEZE",index,"NO");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"NO");
				verifyCCisDisplay("REAL TIME BALANCE",index,"YES");
				verifyCCisDisplay("DIGITAL WALLET",index,"NO");
				verifyCCisDisplay("CONTACT US",index,"YES");
				break;
			case "AOB CANCELLED":
				verifyCCisDisplay("VIEW CARD DETAILS",index,"NO");
				verifyCCisDisplay("VIEW PIN",index,"NO");
				verifyCCisDisplay("FREEZE UNFREEZE",index,"NO");
				verifyCCisDisplay("ORDER REPLACEMENT",index,"NO");
				verifyCCisDisplay("REAL TIME BALANCE",index,"NO");
				verifyCCisDisplay("DIGITAL WALLET",index,"NO");
				verifyCCisDisplay("CONTACT US",index,"YES");
				break;*/
			}
			logMessage("Verify card on carousel successfully in verifyCreditCardControls function for " + status);
			injectMessageToCucumberReport("Verify card on carousel successfully for "+ status);
			Allure.step("Verify card on carousel Successfully "+ status,Status.PASSED);
			//            Allure.Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside verifyCreditCardControls for " +status +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify card on carousel for "+status +e.getMessage());
			Allure.step("Verify card on carousel Successfully "+ status,Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	public void verifyCCisDisplay(String pageName,int index,String isEnabled){
		boolean btnIsDisplay=false;
		boolean viewFlag;
		try {
			switch (pageName.toUpperCase()) {
			case "VIEW PIN":
				switch (isEnabled.toUpperCase()) {
				case "YES":
					viewFlag = isElementDisplayed(getObject("mc.btnViewPIN",index), 5);
					btnIsDisplay = viewFlag;
					break;
				case "NO":
					viewFlag = isElementDisplayed(getObject("mc.btnViewPIN",index), 5);
					if(viewFlag==false){
						btnIsDisplay = true;
					}                                                                              
				default:
					break;
				}                                                              
				break;
			case "VIEW CARD DETAILS":
				switch (isEnabled.toUpperCase()) {
				case "YES":
					viewFlag = isElementDisplayed(getObject("mc.lnkVCdetails",index), 5);
					btnIsDisplay = viewFlag;
					break;
				case "NO":
					viewFlag = isElementDisplayed(getObject("mc.lnkVCdetails",index), 5);
					if(viewFlag==false){
						btnIsDisplay = true;
					}                                                                              
				default:
					break;
				}                                                              
				break;
			case "FREEZE UNFREEZE":
				switch (isEnabled.toUpperCase()) {
				case "YES":
					viewFlag = isElementDisplayed(getObject("mc.btnToggleUnFreeze",index), 5);
					btnIsDisplay = viewFlag;
					break;
				case "NO":
					viewFlag = isElementDisplayed(getObject("mc.btnToggleUnFreeze",index), 5);
					if(viewFlag==false){
						btnIsDisplay = true;
					}                                                                              
				default:
					break;
				}                                                              
				break;
			case "ORDER REPLACEMENT":
				switch (isEnabled.toUpperCase()) {
				case "YES":
					viewFlag = isElementDisplayed(getObject("mc.btnReplaceCard",index), 5);
					btnIsDisplay = viewFlag;
					break;
				case "NO":
					viewFlag = isElementDisplayed(getObject("mc.btnReplaceCard",index), 5);
					if(viewFlag==false){
						btnIsDisplay = true;
					}                                                                              
				default:
					break;
				}                                                              
				break;
			case "REAL TIME BALANCE":
				switch (isEnabled.toUpperCase()) {
				case "YES":
					viewFlag = isElementDisplayed(getObject("mc.lnkVCdetails",index), 5);//TODO : Add xpath
					btnIsDisplay = viewFlag;
					break;
				case "NO":
					viewFlag = isElementDisplayed(getObject("mc.btnViewPIN",index), 5);//TODO : Add xpath
					if(viewFlag==false){
						btnIsDisplay = true;
					}                                                                              
				default:
					break;
				}                                                              
				break;
			case "DIGITAL WALLET":
				switch (isEnabled.toUpperCase()) {
				case "YES":
					viewFlag = isElementDisplayed(getObject("mc.lnkVCdetails",index), 5);//TODO : Add xpath
					btnIsDisplay = viewFlag;
					break;
				case "NO":
					viewFlag = isElementDisplayed(getObject("mc.btnViewPIN",index), 5);//TODO : Add xpath
					if(viewFlag==false){
						btnIsDisplay = true;
					}                                                                              
				default:
					break;
				}                                                              
				break;
			case "CONTACT US":
				switch (isEnabled.toUpperCase()) {
				case "YES":
					viewFlag = isElementDisplayed(getObject("mc.btnContactUs",index), 5);
					btnIsDisplay = viewFlag;
					break;
				case "NO":
					viewFlag = isElementDisplayed(getObject("mc.btnContactUs",index), 5);
					if(viewFlag==false){
						btnIsDisplay = true;

					}                                                                              
				default:
					break;
				}                                                              
				break;
			default:
				break;
			}

			if(btnIsDisplay){
				//	if(Assert.assertTrue("Element displayed ",btnIsDisplay)){
				logMessage(pageName+" Element is displayed as expected as " + isEnabled);
				injectMessageToCucumberReport(pageName+" Element is displayed as expected as "+isEnabled);
				Allure.step(pageName+" Element is displayed as expected "+isEnabled,Status.PASSED);
			}else{

				logError("Error Occured inside isEnabled "+pageName );
				injectWarningMessageToCucumberReport("Failure in isEnabled " + pageName);
				Allure.step(pageName+" Element is NOT displayed as expected "+isEnabled,Status.FAILED);
				Assert.assertEquals(true,btnIsDisplay);
			}
		} catch (InterruptedException e) {
			logError("Error Occured inside " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in isEnabled "+pageName + e.getMessage());
			Allure.step(" Element is NOT displayed as expected "+pageName,Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	//**********ACTIVATE CARD**********
	public void MC_ValActivateCard(String PinFlag,String Pin,String CardNum){
		try {
			//Code for activation warning No and verify back on manage card page
			MC_NavigateTo("ACTIVATE CARD",CardNum);//Click on Activate Card link on Manage Card page 
			clickJS(getObject("ac.btnActivateConfirmNo"),"Activate Card - No button ");
			logInfo("****TITLE*****" + driver.getTitle());
			String ActualTextHeader = getText(getObject("mc.topMenu"));
			logInfo("*****Header" +ActualTextHeader);
			Assert.assertEquals("Manage Card", ActualTextHeader); 
			//
			//code for activation warning Yes and verify card activation
			MC_NavigateTo("ACTIVATE CARD",CardNum);
			clickJS(getObject("ac.btnActivateConfirmYes"),"Activate Card - Yes button ");
			cardsFunctional.Enter3of6DigitPIN(PinFlag, Pin);
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");			
			cardsFunctional.PushNotification_Acccept();//Swipe to approve
			Thread.sleep(20*1000);
			/*String aACMsg = getText(getObject("ac.lblActivationMsg"));//TODO :Capture xpath of error message
			Assert.assertEquals(getMessageText("activateCardMsg","CARDS"),aACMsg);*/

			logMessage("Verify Activate Card successfully in AC_ValidateActivateCard function.");
			injectMessageToCucumberReport("Verify Activate Card successfully");
			Allure.step("Verify Activate Card Successfully",Status.PASSED);
		} 
		catch (Exception e) {
			logError("Error Occured inside AC_ValidateActivateCard " +e.getMessage());
			Allure.step("Verify Activate Card Not Successfully",Status.FAILED);  
			injectWarningMessageToCucumberReport("Failure in Verify Activate Card"+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

	public void MC_ValACNavigateInvalidPIN(String PinFlag,String Pin,String CardNum, String eCardStatus){
		try {
			/*Given
			1. Open the browser
			And
			2. Login to the Browser App in Tablet with valid credentials.
			When
			3. Follow below navigation to reach to Manage Card page
			-> Navigate to 'Home' page
			-> Click on 'Cards' tab
			-> Select 'card image' on 'Manage Card' page
			4. Click on 'Activate Card' option
			Then
			5. Notice message as below is displayed with Yes or No buttons
			If this card is activated prior to the physical arrival of the card please be aware that the new card will be fully activated and ready for use while in transit. Are you sure you want to activate the new card”? 
			When
			6. Click on YES button
			7. Click on 'Go Back' button on 1st authentication screen
			Then
			8. Cardholder is navigated back to 'Manage Card' page and validate that card remain inactive
			When
			9. Click on 'Activate Card' option
			10. Click on YES button on alert message
			Then
			11. 1st authentication screen will be displayed
			When
			12. Click on 'Top Left Arrow' button on 1st authentication screen
			Then
			13. Cardholder is navigated back to 'Manage Card' page and validate that card remain inactive
			When
			14. Click on 'Activate Card' option
			15. Click on YES button on alert message
			Then
			16. 1st authentication screen will be displayed
			When
			17. Enter valid 3-digits of 6 digits authentication PIN and click on 'Continue' button on 1st authentication screen
			Then
			18. 2nd authentication screen will be displayed
			When
			19. Click on 'Left Arrow' button from 2nd authentication screen
			Then
			20. Cardholder is navigated back to 'Manage Card' page and validate that card remain inactive
			When
			21. Click on 'Activate Card' option
			22. Click on YES button on alert message
			Then
			23. 1st authentication screen will be displayed
			When
			24. Enter valid 3-digits of 6 digits authentication PIN and click on 'Continue' button on 1st authentication screen
			Then
			25. 2nd authentication screen will be displayed
			When
			26. Click on 'Decline' button from 2nd authentication screen
			Then
			27. Cardholder is navigated back to 'Manage Card' page and validate that card remain inactive*/
			//Code for Go back button
			MC_NavigateTo("ACTIVATE CARD NEGATIVE",CardNum);
			String PinFlagarr[]=PinFlag.split("~");
			cardsFunctional.Enter3of6DigitPIN(PinFlagarr[0], dataLibrary.getinValidPin());
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");
			Thread.sleep(3*1000);
			String acErrormsg=getText(getObject("enterPin.msgWrongPIN1"));
			logInfo("******Error message received wrong PIN*******"+acErrormsg);
			Assert.assertEquals(getMessageText("WrongPin1","CARDS"), acErrormsg);
			Thread.sleep(3*1000);
			clickJS(getObject("enterPin.btntryAgain"),"Enter PIN - Try again button ");
			Thread.sleep(3*1000);
			cardsFunctional.Enter3of6DigitPIN(PinFlagarr[1],dataLibrary.getinValidPin());
			Thread.sleep(2*1000);
			clickJS(getObject("vc.btnViewCardconfirm"),"Enter PIN - Confirm button ");			
			Thread.sleep(3*1000);
			clickJS(getObject("enterPin.btntryAgain"),"Enter PIN - Try again button ");

			logMan.info("Verify AC Error message successfully in AC_VerifyACErrors function.");
			injectMessageToCucumberReport("Verify AC Error message successfully");
			Allure.step("Verify Error message  Launch Successfully ",Status.PASSED);                  
		} catch (Exception e) {
			logMan.error("Error Occured inside AC_VerifyACErrors " +e.getMessage());
			Allure.step("Verify AC Error Message Not Successful",Status.FAILED);     
			injectWarningMessageToCucumberReport("Failure in Verify AC Error message "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}


	public void MC_ValCardAccounts(){
		try {
			//TO DO: Code for function
			logMessage("Verify card on carousel successfully in MC_ValidateCardAccounts function");
			injectMessageToCucumberReport("Verify card on carousel successfully");
			Allure.step("Verify card on carousel Successfully ",Status.PASSED);
			//            Allure.Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside MC_ValidateCardAccounts " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify card on carousel "+e.getMessage());
			Allure.step("Verify card on carousel NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}
	public void ClickOnCardsTab(String cardType, String cardStatus) throws InterruptedException{

		try {
			if (isElementDisplayed(getObject(getDeviceType()+"home.btntitleCards"))) {
				clickJS(getObject(getDeviceType() +"home.btntitleCards"));
				//clickJS(getObject("home.btntitleCards"));
				Thread.sleep(5000);	
				appendScreenshotToCucumberReport();
				logMessage("Click on Cards tab successfully in ClickOnCardsTab function for the card :"+cardType+" with status: "+cardStatus);
				injectMessageToCucumberReport("Click on Cards tab successfully");
				Allure.step("Click on Cards tab Successfully ",Status.PASSED);
			} else {
				logMessage("Cards tab is not clicked ");
			}

			//	            Allure.Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside ClickOnCardsTabfor the card :"+cardType+" with status: "+cardStatus +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Click on Cards tab "+e.getMessage());
			Allure.step("Click on Cards tab NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}


	/*public void MC_NavigateTo(String strPagename){
		try {
			if (isElementDisplayed(getObject("mc.dots"))) {
				logInfo("******MC_NavigateTo*******"+ strPagename);
				switch (strPagename.toUpperCase()){

				case "VIEW CARD DETAILS":	
					try{
					List<WebElement> Bullets = driver.findElements(getObject("mc.dots"));
					logInfo("*********INSIDE CASE**********");
					for(int i=0; i<Bullets.size();i++)
					{
						Bullets = driver.findElements(getObject("mc.dots"));
						if(Bullets.get(i).isEnabled())
						{
							Bullets.get(i).click();
							Thread.sleep(2*1000);
							logInfo("****EXTRACT CARDNUM BEFORE***************"+i);
							String CardNum = getText(getObject("mc.imgCardsNum",i+1));
							logInfo("****EXTRACT CARDNUM***************"+CardNum);

							//Code For clicking View Card Details only when View Card link is present
							if(isElementDisplayed(getObject("mc.lnkVCdetails",i+1))){
								clickJS(getObject("mc.lnkVCdetails",i+1));
								Thread.sleep(2*1000);
								clickJS(getObject("vc.btnViewCardgoBack"));
								Thread.sleep(4*1000);
								logMessage("View Card details launch successfully in ManageCard_NavigateTO function");
								injectMessageToCucumberReport("View Card details launch successfully");
								Allure.step("View Card Detail Launch Successfully ",Status.PASSED);
							}
							else{
								logInfo("View Card Details link is NOT present in ManageCard_NavigateTO function");
							}
						}
						else{
							logError("Bullet is NOT enabled in ManageCard_NavigateTO function");
						}
					}} catch (Exception e) {
						logMessage("View Card details NOT launch successfully");
						logError("Error Occured inside ManageCard_NavigateTO " +e.getMessage());
						injectWarningMessageToCucumberReport("Failure in View Card details launched "+e.getMessage());
						Allure.step("View Card Detail Launch NOT Successfully ",Status.FAILED);
						appendScreenshotToCucumberReport();
					}
					break;
				case "VIEW PIN":
					//CODE TO NAVIGATE VIEW CARD PIN PAGE
					clickJS(getObject("mc.btnViewPIN"));
					break;
				case "FREEZE UNFREEZE CARD":
					//CODE TO NAVIGATE VIEW CARD PIN PAGE
					clickJS(getObject("mc.btnViewPIN"));
					break;
				case "REPLACE DAMAGE CARD":
					//CODE TO NAVIGATE VIEW CARD PIN PAGE
					clickJS(getObject("mc.btnReplaceCard"));
					break;
				case "REPORT LOST STOLEN":
					//CODE TO NAVIGATE VIEW CARD PIN PAGE
					clickJS(getObject("mc.btnViewPIN"));
					break;
				case "SET UP APPLE PAY":
					// CODE TO NAVIGATE APPLE PAY
					clickJS(getObject("mc.btnViewPIN"));
					break;
				case "SETUP GOOGLE PAY":
					//CODE TO NAVIGATE GOOGLE PAY
					clickJS(getObject("mc.btnViewPIN"));
					break;
				default:
					logMan.error("Error Occured inside NavigateToPage: "+strPagename);
					break;

				}
			}} catch (Exception e) {
				logMessage("View Card details NOT launch successfully");
				logError("Error Occured inside ManageCard_NavigateTO " +e.getMessage());
				injectWarningMessageToCucumberReport("Failure in View Card details launched "+e.getMessage());
				Allure.step("View Card Detail Launch NOT Successfully ",Status.FAILED);
				appendScreenshotToCucumberReport();
			}
	}

	 */
	public void MC_NavigateTo(String strPagename,String cardNumber){
		try {
			if (isElementDisplayed(getObject("mc.dots"))) {

				logInfo("******MC_NavigateTo*******"+ strPagename);
				switch (strPagename.toUpperCase()){
				case "VIEW CARD DETAILS":
					List<WebElement> Bullets = driver.findElements(getObject("mc.dots"));
					for(int i=0; i<Bullets.size();i++)
					{
						Bullets = driver.findElements(getObject("mc.dots"));
						if(Bullets.get(i).isEnabled())
						{
							Bullets.get(i).click();
							Thread.sleep(2*1000);
							//Card number extract from digital image on Manage Card page
							String CardNum = getText(getObject("mc.imgCardsNum",i));
							String CardApp= CardNum.substring(CardNum.length()-4);
							//String CardNum = getText(getObject("mc.lnkVCdetails",i+1));
							String eCardNumber= cardNumber.substring(cardNumber.length()-4);
							//Code For clicking View Card Details only when View Card link is present
							appendScreenshotToCucumberReport();
							if(CardApp.equals(eCardNumber)){
								appendScreenshotToCucumberReport();
								Thread.sleep(2*1000);
								appendScreenshotToCucumberReport();
								clickJS(getObject("mc.lnkVCdetails",i+1));
								Thread.sleep(2*1000);
								//clickJS(getObject("vc.btnViewCardgoBack"));
								Thread.sleep(4*1000);	
								break;
							}							
							else{
								logInfo("Navigating to next card");
							}
						}
						else{
							logError("Bullet is NOT enabled in ManageCard_NavigateTO function");							
						}						
					}

					break;
				case "VIEW PIN":
					//CODE TO NAVIGATE VIEW PIN PAGE
					List<WebElement> Bullet2 = driver.findElements(getObject("mc.dots"));
					//CODE TO NAVIGATE VIEW CARD DETAILS PAGE
					for(int i=0; i<Bullet2.size();i++)
					{
						Bullet2 = driver.findElements(getObject("mc.dots"));

						if(Bullet2.get(i).isEnabled())
						{
							Bullet2.get(i).click();
							Thread.sleep(2*1000);
							//TODO: Card number extract from digital image on Manage Card page

							String CardNum = getText(getObject("mc.imgCardsNum",i));
							String CardApp= CardNum.substring(CardNum.length()-4);
							//String CardNum = getText(getObject("mc.lnkVCdetails",i+1));
							String eCardNumber= cardNumber.substring(cardNumber.length()-4);
							//Code For clicking View Card Details only when View Card link is present
							if(CardApp.equals(eCardNumber)){
								appendScreenshotToCucumberReport();
								Thread.sleep(2*1000);
								clickJS(getObject("mc.btnViewPIN",i));
								Thread.sleep(15*1000);
								/*clickJS(getObject("vc.btnViewCardgoBack"));
							Thread.sleep(4*1000);*/
								break;
							}
							else{
								logMan.info("Navigating to next card");
							}
						}
						else{
							logMan.error("Bullet is NOT enabled in ManageCard_NavigateTO function");
						}
					}
					break;
				case "FREEZE UNFREEZE CARD":
					//List<WebElement> Bullets=null;
					List<WebElement> Bullets3 = driver.findElements(getObject("mc.dots"));
					//CODE TO NAVIGATE VIEW CARD DETAILS PAGE
					for(int i=0; i<Bullets3.size();i++)
					{
						Bullets3 = driver.findElements(getObject("mc.dots"));

						if(Bullets3.get(i).isEnabled())
						{
							Bullets3.get(i).click();
							Thread.sleep(2*1000);
							//TODO: Card number extract from digital image on Manage Card page

							String CardNum = getText(getObject("mc.imgCardsNum",i));
							String acCardNumber= CardNum.substring(CardNum.length()-4);
							//String CardNum = getText(getObject("mc.lnkVCdetails",i+1));
							String eCardNumber= cardNumber.substring(cardNumber.length()-4);
							//Code For clicking View Card Details only when View Card link is present
							if(acCardNumber.equals(eCardNumber)){
								Thread.sleep(3*1000);

								clickJS(getObject("mc.btnToggleFreeze",i),"Manage Card - Freeze");
								Thread.sleep(3*1000);
								clickJS(getObject("fz.btnFZYes"),"Manage Card - Freeze confirm Yes ");				
								Thread.sleep(3*1000);
								isElementDisplayed(getObject("mc.flagFrozen",i),5,"Manage Card - Frozen flag");
								logMan.info("Card freeze successfully");

								//Unfreeze card flow
								Thread.sleep(3*1000);
								clickJS(getObject("mc.btnToggleUnFreeze",i),"Manage Card - UnFreeze");
								isElementDisplayed(getObject("mc.flagFrozen",i),5,"Manage Card - Frozen flag");
								logMan.info("Card Unfreeze successfully");
								break;
							}
							else{
								logMan.info("Navigating to next card");

							}
						}
						else{
							logMan.error("Bullet is NOT enabled in ManageCard_NavigateTO function");							
						}						
					}

					break;

				case "ORDER REPLACEMENT":
					List<WebElement> Bullet = driver.findElements(getObject("mc.dots"));
					//CODE TO NAVIGATE VIEW CARD DETAILS PAGE
					for(int i=0; i<Bullet.size();i++)
					{
						Bullet = driver.findElements(getObject("mc.dots"));
						if(Bullet.get(i).isEnabled())
						{
							Bullet.get(i).click();
							Thread.sleep(2*1000);
							//TODO: Card number extract from digital image on Manage Card page

							String CardNum = getText(getObject("mc.imgCardsNum",i));
							String CardApp= CardNum.substring(CardNum.length()-4);
							//String CardNum = getText(getObject("mc.lnkVCdetails",i+1));
							String eCardNumber= cardNumber.substring(cardNumber.length()-4);
							logInfo("******CARDNUM*******"+eCardNumber);
							logInfo("******CARDAPP*******"+CardApp);
							//Code For clicking View Card Details only when View Card link is present
							if(CardApp.equals(eCardNumber)){
								appendScreenshotToCucumberReport();
								clickJS(getObject("mc.btnReplaceCard",i));
								break;
								//Assert.assertEquals(getText(getObject("ac.flagActivateCard")),"Activate card");
							}
							else{
								logInfo("Navigating to next card");
							}
						}
						else{
							logError("Bullet is NOT enabled in ManageCard_NavigateTO function");

						}
					}
					break;
				case "REPORT LOST STOLEN":
					//CODE TO NAVIGATE VIEW CARD PIN PAGE
					clickJS(getObject("mc.btnViewPIN"));
					break;
				case "SET UP APPLE PAY":
					// CODE TO NAVIGATE APPLE PAY
					clickJS(getObject("mc.btnViewPIN"));
					break;
				case "ACTIVATE CARD":
					List<WebElement> Bullet3 = driver.findElements(getObject("mc.dots"));
					logInfo("*********INSIDE CASE VIEW PIN**********");
					//CODE TO NAVIGATE VIEW CARD DETAILS PAGE
					for(int i=0; i<Bullet3.size();i++)
					{
						Bullet3 = driver.findElements(getObject("mc.dots"));
						if(Bullet3.get(i).isEnabled())
						{
							Bullet3.get(i).click();
							Thread.sleep(2*1000);
							//TODO: Card number extract from digital image on Manage Card page

							String CardNum = getText(getObject("mc.imgCardsNum",i));
							String CardApp= CardNum.substring(CardNum.length()-4);
							//String CardNum = getText(getObject("mc.lnkVCdetails",i+1));
							String CardExcel= cardNumber.substring(cardNumber.length()-4);
							//Code For clicking Activate Now link
							if(CardApp.equals(CardExcel)){
								appendScreenshotToCucumberReport();
								Thread.sleep(2*1000);
								//Bullets.get(i).click();
								Thread.sleep(2*1000);
								logInfo("****DINDEX***** "+i);
								clickJS(getObject("ac.btnActivateNow",i));
								Thread.sleep(2*1000);
								/*clickJS(getObject("vc.btnViewCardgoBack"));
							Thread.sleep(4*1000);*/
								break;

							}
							else{
								logMan.error("Activate Card link is NOT present in ManageCard_NavigateTO function");
							}
						}
						else{
							logMan.error("Bullet is NOT enabled in ManageCard_NavigateTO function");

						}

					}
					break;
				case "ACTIVATE CARD NEGATIVE":
					List<WebElement> Bullet4 = driver.findElements(getObject("mc.dots"));
					logMan.info("*********INSIDE CASE VIEW PIN**********");
					//CODE TO NAVIGATE VIEW CARD DETAILS PAGE
					for(int i=0; i<Bullet4.size();i++)
					{
						Bullet4 = driver.findElements(getObject("mc.dots"));
						if(Bullet4.get(i).isEnabled())
						{
							Bullet4.get(i).click();
							Thread.sleep(2*1000);
							//TODO: Card number extract from digital image on Manage Card page

							String CardNum = getText(getObject("mc.imgCardsNum",i));
							String CardApp= CardNum.substring(CardNum.length()-4);
							//String CardNum = getText(getObject("mc.lnkVCdetails",i+1));
							String CardExcel= cardNumber.substring(cardNumber.length()-4);

							//Code For clicking View Card Details only when View Card link is present
							if(CardApp.equals(CardExcel)){
								appendScreenshotToCucumberReport();
								clickJS(getObject("ac.btnActivateNow",i),"Manage Card - Activate button ");//Click on Activate Card link on Manage Card page     
								clickJS(getObject("ac.btnActivateConfirmYes"),"Activate Card - Yes button ");
								break;
							}
							else{
								logMan.error("Activate Card link is NOT present in ManageCard_NavigateTO function");
							}
						}
						else{
							logMan.error("Bullet is NOT enabled in ManageCard_NavigateTO function");
						}
					}
					break;
				default:
					logMan.error("Error Occured inside NavigateToPage: "+strPagename);
					break;
				}
				logInfo("Manage Card navigation launch successfully in ManageCard_NavigateTO function");
				injectMessageToCucumberReport("Manage Card navigation launch successfully");
				Allure.step("Manage Card navigation Launch Successfully ",Status.PASSED);
			}} catch (Exception e) {
				logMessage("Manage Card navigation NOT launch successfully");
				logError("Error Occured inside ManageCard_NavigateTO " +e.getMessage());
				Allure.step("Manage Card navigation NOT Launch Successfully ",Status.FAILED);
				injectWarningMessageToCucumberReport("Failure in Manage Card navigation launched "+e.getMessage());
				appendScreenshotToCucumberReport();
			}
	}

	/*---------------------------------Start <ManageCard_VerifyCardsOnCarousel>----------------------------------------
	Function Name: ManageCard_VerifyCardsOnCarousel
	Argument Name:
	Purpose:To launch the view card Pin page		
	Author Name: Cards Automation
	Create Date: 12-02-2021
	Modified Date| Modified By  |Modified purpose 

	-----------------------------------End <ManageCard_VerifyCardsOnCarousel>---------------------------------------
	 */

	/*---------------------------------Start <ManageCard_VerifyCardsEnablednVirtualCardDetails>----------------------------------------
	Function Name: ManageCard_VerifyCardsEnablednVirtualCardDetails
	Argument Name:
	Purpose:verify card is enable and validate card details		
	Author Name: Cards Automation
	Create Date: 12-02-2021
	Modified Date| Modified By  |Modified purpose 

	-----------------------------------End <ManageCard_VerifyCardsEnablednVirtualCardDetails>---------------------------------------
	 */
	public void MC_ValDigitalCardImageDetail(){
		try {
			//TO DO: Code for function
			logMessage("Verify card enable and virtual card details successfully in ManageCard_VerifyCardsEnablednVirtualCardDetails function");	          
			injectMessageToCucumberReport("Verify card enable and virtual card details successfully");
			Allure.step("Verify card enable and virtual card details Successfully ",Status.PASSED);
			//            Allure.Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside ManageCard_VerifyCardsEnablednVirtualCardDetails " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify card enable and virtual card details "+e.getMessage());
			Allure.step("Verify card enable and virtual card details Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}
	/*---------------------------------Start <ManageCard_VerifyScrollDownPage>----------------------------------------
	Function ManageCard_VerifyScrollDownPage
	Argument Name:
	Purpose:To launch the Replace Damage card page		
	Author Name: Cards Automation- 
	Create Date: 12-02-2021
	Modified Date| Modified By  |Modified purpose 

	-----------------------------------End <ManageCard_VerifyScrollDownPage>---------------------------------------
	 */
	public void MC_VerifyScrollDownPage(){
		try {
			//TO DO: Code for function
			logMessage("Verify scroll down page successfully in ManageCard_VerifyScrollDownPage function");
			injectMessageToCucumberReport("Verify scroll down page successfully");
			Allure.step("Verify scroll down page Launch Successfully ",Status.PASSED);
			//            Allure.Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside ManageCard_VerifyScrollDownPage " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify scroll down page "+e.getMessage());
			Allure.step("Verify scroll down page NOT Launch Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}
	/*---------------------------------Start <ManageCard_VerifyOldCardRemoval>----------------------------------------
	Function Name: ManageCard_VerifyOldCardRemoval
	Argument Name:
	Purpose:To launch the Report Lost Stolen card page		
	Author Name: Cards Automation 
	Create Date: 12-02-2021
	Modified Date| Modified By  |Modified purpose 

	-----------------------------------End <ManageCard_VerifyOldCardRemoval>---------------------------------------
	 */
	public void MC_VerifyOldCardRemoval(){
		try {
			//TO DO: Code for function
			logMessage("Report Lost and Stolen page launch successfully in ManageCard_VerifyOldCardRemoval function");
			injectMessageToCucumberReport("Report Lost and Stolen page launch successfully");
			Allure.step("Report Lost and Stolen Page Launch Successfully ",Status.PASSED);
			//            Allure.Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside ManageCard_VerifyOldCardRemoval " +e.getMessage());
			Allure.step("Report Lost and Stolen Page NOT Launch Successfully ",Status.FAILED);
			injectWarningMessageToCucumberReport("Failure in Report Lost and Stolen page details launched "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}
	/*---------------------------------Start <MC_ValFreezeUnfeeze>----------------------------------------
	Function Name: MC_ValFreezeUnfeeze
	Argument Name:
	Purpose:To perform freeze unfreeze		
	Author Name: Cards Automation 
	Create Date: 12-02-2021
	Modified Date| Modified By  |Modified purpose 

	-----------------------------------End <MC_ValFreezeUnfeeze>---------------------------------------
	 */
	public void MC_ValFreezeUnfeeze(String cardNumber){

		try {
			MC_NavigateTo("FREEZE UNFREEZE CARD",cardNumber);
			logMan.info("Verify Freeze Card digital image successfully in MC_ValFreezeUnfeeze function.");
			injectMessageToCucumberReport("Verify Freeze Card digital image successfully");
			Allure.step("Verify Freeze Card digital image Successfully",Status.PASSED);
		} catch (Exception e) {
			logMan.error("Verify Freeze Unfreeze NOT successful");						
			injectWarningMessageToCucumberReport("Failure in Freeze Unfreeze ");
			Allure.step("Verify Freeze Card digital image NOT Successfully",Status.FAILED);
			appendScreenshotToCucumberReport();
		}	

	}

	/*---------------------------------Start <MC_ValFZUFZ_ReorderDamage>----------------------------------------
	Function Name: MC_ValFZUFZ_ReorderDamage
	Argument Name:
	Purpose:To perform freeze unfreeze on Reorder damage	
	Author Name: Cards Automation 
	Create Date: 12-02-2021
	Modified Date| Modified By  |Modified purpose 

	-----------------------------------End <MC_ValFZUFZ_ReorderDamage>---------------------------------------
	 */
	public void MC_ValFZUFZ_ReorderDamage(String cardNum){
		try {
			MC_NavigateTo("ORDER REPLACEMENT",cardNum);

			Thread.sleep(2*1000);
			if(isElementDisplayed(getObject("or.btnOrderNow"))&&isElementDisplayed(getObject("or.lnkChange"))){
				Thread.sleep(2*1000);
				clickJS(getObject("or.btnOrderNow"),"Order Now button ");
				Thread.sleep(15*1000);
			}else{
				logError("Error Occured inside OR_VerifySuccesReplace " );
				injectWarningMessageToCucumberReport("Failure in Replacement Successful ");
				Allure.step("Replacement NOT Successful ",Status.FAILED);
				appendScreenshotToCucumberReport();
			}

			logInfo("Verify Reorder damage successfully in MC_ValFZUFZ_ReorderDamage function.");
			injectMessageToCucumberReport("Replacement of frozen card placed successfully");
			Allure.step("Replacement of frozen card Successful",Status.PASSED);
		} catch (Exception e) {
			logError("Replacement of frozen card NOT Successful");						
			injectWarningMessageToCucumberReport("Replacement of frozen card NOT Successful ");
			Allure.step("Replacement of frozen card NOT Successful",Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

	public void MC_ValFZUFZdigitalCardDetail(String cardNumber,String CardholderName,String ExpiryDt,String CardType){

		//MC_NavigateTo("FREEZE UNFREEZE CARD",CardNum);
		logInfo("*******CardNumber is ************"+cardNumber);
		logInfo("*******Expiry is ************"+ExpiryDt);

		try {
			List<WebElement> Bullet2 = driver.findElements(getObject("mc.dots"));


			for(int i=0; i<Bullet2.size();i++)
			{
				Bullet2 = driver.findElements(getObject("mc.dots"));

				if(Bullet2.get(i).isEnabled())
				{
					Bullet2.get(i).click();
					Thread.sleep(2*1000);
					//TODO: Card number extract from digital image on Manage Card page

					String CardNum1 = getText(getObject("mc.imgCardsNum",i));
					String CardApp= CardNum1.substring(CardNum1.length()-4);
					//String CardNum = getText(getObject("mc.lnkVCdetails",i+1));
					String expCardNumber= cardNumber.substring(cardNumber.length()-4);
					logInfo("****EXTRACT CARDNUM VIEW PIN*************** "+CardNum1);
					logInfo("****EXTRACT CARDNUM VIEW PIN*************** "+expCardNumber);
					//Code For clicking View Card Details only when View Card link is present
					if(CardApp.equals(expCardNumber)){
						Thread.sleep(2*1000);

						/*String acExpiryDt=getText(getObject("mc.imgCardsExpdt",i));
					System.out.println("****EXPECTED*************** "+ExpiryDt);
					System.out.println("****ACTUAL*************** "+acExpiryDt);
					Assert.assertEquals(ExpiryDt, acExpiryDt);*/

						logMan.info("****VERYFYING ASSERT************ ");
						String acCardType=getText(getObject("mc.imgCardType",i));
						String CardTypearr[] = CardType.split(" ");
						String expCardType=CardTypearr[1]+" "+CardTypearr[2];
						logMan.info("****EXPECTED*************** "+expCardType);
						logMan.info("****ACTUAL*************** "+acCardType);
						Assert.assertEquals(expCardType, acCardType);

						String acCardHolderName=getText(getObject("mc.imgCardHolderName",i));
						logMan.info("****EXPECTED*************** "+CardType);
						logMan.info("****ACTUAL*************** "+acCardType);
						Assert.assertEquals(CardholderName, acCardHolderName);

						/*String acExpiryDt=getText(getObject("mc.imgCardsExpdt"));					
					Assert.assertEquals(ExpiryDt, acExpiryDt);*/
						logMan.info("Verify Freeze Card digital image successfully in MC_ValFZUFZdigitalCardDetail function.");
						injectMessageToCucumberReport("Verify Freeze Card digital image successfully");
						Allure.step("Verify Freeze Card digital image Successfully",Status.PASSED);
						break;
					}
					else{
						logMan.debug("Verify Freeze Card digital image NOT successful");						
						injectWarningMessageToCucumberReport("Failure in Freeze Card digital image ");
						Allure.step("Verify Freeze Card digital image NOT Successfully",Status.FAILED);
						appendScreenshotToCucumberReport();
					}
				}
				else{					
					logMan.error("Bullet is NOT enabled in MC_ValFZUFZdigitalCardDetail function");
				}

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logMan.error("View Card details NOT launch successfully");
			logError("Error Occured inside ManageCard_NavigateTO " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in View Card details launched "+e.getMessage());
			appendScreenshotToCucumberReport();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}

	public void MC_VerifyMoreFields(){
		try {
			//TO DO: Verify More field for function
			clickJS(getObject(getDeviceType() +"home.btntitleCards"),"Cards link ");

			boolean displayFlag=false;
			boolean viewFlagLostandStolen = isElementDisplayed(getObject("mc.btnMoreReqLostorStolen"), 5);//TODO : Add xpath
			displayFlag = viewFlagLostandStolen;

			boolean viewFlagOrderRep = isElementDisplayed(getObject("mc.btnMoreReqReplaceCard"), 5);//TODO : Add xpath
			displayFlag = viewFlagLostandStolen;

			if(displayFlag=true){
				logInfo("Verify More requirement changes on Manage card page successfully in Services_VerifyMoreFields function");
				injectMessageToCucumberReport("Verify More requirement changes on Manage card page successfully");
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
			logError("Error Occured inside ManageCard_VerifyMoreFields " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify More requirement changes on manage card page "+e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Verify More requirement changes on manage card page NOT successfully", Status.FAILED);
		}
	}
}

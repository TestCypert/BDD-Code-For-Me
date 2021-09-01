/*---------------------------------Start <SD_SS_ServicesManageCards>----------------------------------------
 Function Name: SD_SS_ServicesManageCards
 Argument Name:
 Purpose: Method to set different Manage Cards Options
 Author Name: CAF Automation 
 Create Date: 18-05-2021
 Modified Date| Modified By  |Modified purpose 
  18/05/2021      C113329     Code update
 -----------------------------------End<SD_SS_ServicesManageCards>---------------------------------------
 */

package com.boi.grp.pageobjects.Services;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.DataLibrary;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class Service_SS_ServicesManageCards extends BasePageForAllPlatform {

	public UIResusableLibrary cafFunctional;
	public DataLibrary dataLibrary;
	public String referenceNumber;

	By LostOrStolenCards = getObject("sd.tileLostOrStolenCards");
	By ReplaceDamageDebitCards = getObject("sd.tileReplaceDamageDebitCard");
	By DebitCardList = getObject("sd.debitCartAccountList");
	By AccDebitCardDropDown = getObject("sd.AccDebitCardDropDown");
	By DebitCardNumber = getObject("sd.DebitCardNumber");
	By ContinueReplaceDDCards = getObject("sd.buttonContinue");
	By NotificationYesOpt = getObject("sd.RepalceDamageDebitCardNotificaionYesOpt");
	By NotificationNoOpt = getObject("sd.RepalceDamageDebitCardNotificaionNoOpt");
	By NotificationMobileOpt = getObject("sd.RepalceDamageDebitCardNotificaionContMobileOpt");
	By NotificationEmailOpt = getObject("sd.RepalceDamageDebitCardNotificaionContEmailOpt");
	By NotificationEmailText = getObject("sd.RepalceDamageDebitCardNotificaionContEmailText");
	By NotificationMobileText = getObject("sd.RepalceDamageDebitCardNotificaionContMobileText");
	By ContinueBtn = getObject("sd.continueBtn");
	By EmergencyROI = getObject("sd.EmergencyROI");
	By EmergencyNIGB = getObject("sd.EmergencyNI/GB");
	By OtherLocation = getObject("sd.OtherLocation");
	By selectAccountName = getObject("sd.selectAccName");
	By reviewDebitCardNumber = getObject("sd.reviewDebitCardNumber");
	By ConfirmBtn = getObject("sd.buttonConfirm");
	By ServiceTab = getObject("sd.tabServices");
	By RequestSentMessage = getObject("sd.textRequestSentMessage");
	By RefNumberReplaceDDCardTxt = getObject("sd.RefNumberReplaceDDCardTxt");
	By buttonBackToHome = getObject("sd.buttonBackToHome");
	
	public Service_SS_ServicesManageCards(WebDriver driver) {

		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional = new UIResusableLibrary(driver);
		dataLibrary = new DataLibrary();
	}

	/*---------------------------------Start <SD_SS_ManageCards>----------------------------------------
	 Function Name: SD_SS_ManageCards
	 Argument Name:
	 Purpose: Method to set different Manage Cards Options
	 Author Name: CAF Automation 
	 Create Date: 17-05-2021
	 Modified Date| Modified By  |Modified purpose 
	  17/05/2021      C113329     Code update
	 -----------------------------------End<SD_SS_ManageCards>---------------------------------------
	 */

	public void SD_SS_ManageCards(String serMCOpt) {
		try {
			boolean bFlagSerMCMenu = false;
			logMessage("SERVICES OPTION TYPE : " + serMCOpt);

			switch (serMCOpt.toUpperCase()) {
			case "REPORT_LOST_OR_STOLEN_CARD":
				if (isElementDisplayed(LostOrStolenCards, 10, "LostOrStolenCards")) {
					SD_MC_RptLostStolenCard_P();
					bFlagSerMCMenu = true;
				} else {
					bFlagSerMCMenu = false;
				}
				break;

			case "REPLACE_DAMANGED_DEBIT_CARD":
				if (isElementDisplayed(ReplaceDamageDebitCards, 10, "ReplaceDamageDebitCards")) {
					SD_MC_ReplaceDebitCard_P();
					bFlagSerMCMenu = true;
				} else {
					bFlagSerMCMenu = false;
				}
				break;
			}
			if (bFlagSerMCMenu == true) {
				appendScreenshotToCucumberReport();
				logMessage(serMCOpt + " Click on Manage Cards tile successfully in SD_SS_ManageCards function ");
				injectMessageToCucumberReport(serMCOpt + " Click on Manage Cards tile successfully");
				Allure.step("Click on Manage Cards tile successfully ", Status.PASSED);
			} else {
				logMessage(serMCOpt + " Click on Manage Cards tile was NOT successful");
				injectMessageToCucumberReport(serMCOpt + " Failure in Click on Services tab ");
				Allure.step(serMCOpt + " Click on Manage Cards tile NOT successfully ", Status.FAILED);
				appendScreenshotToCucumberReport();
			}

		} catch (Exception e) {
			logError("Error Occured inside SD_SS_ManageCards " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Manage Cards option selection " + e.getMessage());
			Allure.step("Failure in Manage Cards option selection ", Status.FAILED);
			appendScreenshotToCucumberReport();

		}

	}
	
	public String setReferNum(){
		
		return referenceNumber;
	}

	/*---------------------------------Start <SD_MC_RptLostStolenCard_P>----------------------------------------
	 Function Name: SD_MC_RptLostStolenCard_P
	 Argument Name:
	 Purpose: Method for Replace And Stolen Cards Functionality
	 Author Name: CAF Automation 
	 Create Date: 18-05-2021
	 Modified Date| Modified By  |Modified purpose 
	  18/05/2021      C113329     Code update
	 -----------------------------------End<SD_MC_RptLostStolenCard_P>---------------------------------------
	 */

	private void SD_MC_RptLostStolenCard_P() {

		try {
			clickJS(LostOrStolenCards, "Service- Manage Cards - Lost OR Stolen Cards");
			waitForPageLoaded();
			String ExpectedPageTitle = "Lost Stolen Card - Bank of Ireland";
			String ActualPageTitle = driver.getTitle();
			Assert.assertEquals("Page Title Validated Successfully", ExpectedPageTitle, ActualPageTitle);
			verifyContactNumberSection();
			appendScreenshotToCucumberReport();
			logMessage("Click on Manage Cards tile successfully in SD_MC_RptLostStolenCard_P function ");

		} catch (Throwable e) {
			logError("Error Occured inside SD_MC_RptLostStolenCard_P " + e.getMessage());
		}
	}

	/*---------------------------------Start <SD_MC_ReplaceDebitCard_P>----------------------------------------
	 Function Name: SD_MC_ReplaceDebitCard_P
	 Argument Name:
	 Purpose: Method for Replace And Stolen Cards Functionality
	 Author Name: CAF Automation 
	 Create Date: 25-05-2021
	 Modified Date| Modified By  |Modified purpose 
	  25/05/2021      C113329     Code update
	 -----------------------------------End<SD_MC_ReplaceDebitCard_P>---------------------------------------
	 */

	private void SD_MC_ReplaceDebitCard_P() {

		try {
			String DDAccNumber = dataLibrary.generateRandomNumber(8);
			// String mobileNumber = dataLibrary.generateRandomMobileNumber();
			String mobileNumber = "0877121212";
			String emailId = dataLibrary.generateRandomEmail();

			clickJS(ReplaceDamageDebitCards, "Service - Manage Cards - Replace Damage Debit Cards");
			if (isElementDisplayed(DebitCardList, 10, "DebitCardList")) {
				clickJS(DebitCardList, "Service - Manage Cards -Debit Cards List");
				cafFunctional.clickSelectValueFromDropDown(DebitCardList, selectAccountName, "CURRENT ACCOUNT ~ 6381");
				clickJS(DebitCardList, "Service - Manage Cards -Debit Cards List");
				setValue(DebitCardNumber, DDAccNumber);

				if (isElementDisplayed(NotificationYesOpt, 10, "NotificationYesOpt")) {
					clickJS(NotificationYesOpt, "Notification Yes Option Clicked");
					if (isElementDisplayed(NotificationMobileOpt, 10, "NotificationMobileOpt")) {
						clickJS(NotificationMobileOpt, "Notification Mobile Option Selected");
						Thread.sleep(3000);
						setValue(NotificationMobileText, mobileNumber);
					} else if (isElementDisplayed(NotificationEmailOpt, 10, "NotificationEmailOpt")) {
						clickJS(NotificationEmailOpt, "Notification Email Option Selected");
						setValue(NotificationEmailText, emailId);
					}
				} else {
					if (isElementDisplayed(NotificationNoOpt, 10, "NotificationNoOpt")) {
						clickJS(NotificationNoOpt, "Notification No Option Selected");
					} else {
						logMessage("Notification Option Not Found On Replace DD Card Page");
					}
				}
				clickJS(ContinueBtn, "Service - Manage Cards - Lost Or Stolen Cards Continue btn");
				reviewReplaceDamageDebitCardPage(DDAccNumber);
				
				String verifySuccessMess = driver.findElement(RequestSentMessage).getText();
				String expectedSuccessMesg = "Request sent";
				Assert.assertEquals(expectedSuccessMesg, verifySuccessMess);
				
				String refNum = driver.findElement(RefNumberReplaceDDCardTxt).getText();
				referenceNumber = refNum.substring(15, refNum.length());
				clickJS(buttonBackToHome, "Service - Manage Account - Order Up To Date Back btn");
				
			} else {
				logError("Error occured on clicking Continue button for Replace Damage Debit Card Page");
			}
		} catch (Exception e) {
			logError("Error Occured inside SD_MC_ReplaceDebitCard_P " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Replace Debit Cards verification " + e.getMessage());
			Allure.step("Failure in Replace Debit Cards verification  ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	private void reviewReplaceDamageDebitCardPage(String dDAccNumber) {
		try {
			String actualDCNumber = driver.findElement(reviewDebitCardNumber).getText();
			if (actualDCNumber.equals(dDAccNumber)) {
				logMessage("Account Name is as execpected on Review Page");
				clickJS(ConfirmBtn, "Service - Manage Cards -Damange Debit Cards Confirm btn");
			} else {
				logMessage("Account Name is not as execpected on Review Page");
			}
		} catch (Exception e) {
			logError("Error Occured inside reviewReplaceDamageDebitCardPage " + e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

	// Method to validate Contact Number Section
	private void verifyContactNumberSection() {
		try {
			if ((isElementDisplayed(EmergencyROI, 10, "EmergencyROI"))
					&& (getText(EmergencyROI).equalsIgnoreCase(getMessageText("strROI", "UXPBANKING365")))) {
				logMessage("Emergency ROI details are available");
			} else {
				logMessage("Emergency ROI details are Not available");
			}

			if ((isElementDisplayed(EmergencyNIGB, 10, "EmergencyNIGB")) && ((getText(EmergencyNIGB)
					.equalsIgnoreCase(getMessageText("strNIGB", "UXPBANKING365"))))) {
				logMessage("Emergency NI/GB details are available");
			} else {
				logMessage("Emergency NI/GB details are Not available");
			}

			if ((isElementDisplayed(OtherLocation, 10, "OtherLocation")) && ((getText(OtherLocation)
					.equalsIgnoreCase(getMessageText("strotherLoc", "UXPBANKING365"))))) {
				logMessage("Emergency Other Location details are available");
			} else {
				logMessage("Emergency Other Location details are Not available");
			}

		} catch (Exception e) {
			logError("Error Occured inside verifyContactNumberSection " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Contact Number verification " + e.getMessage());
			Allure.step("Failure in Contact Number verification  ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

}

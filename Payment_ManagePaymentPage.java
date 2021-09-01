package com.boi.grp.pageobjects.Payments;

import com.boi.grp.pageobjects.login.LoginPageNew;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class Payment_ManagePaymentPage extends BasePageForAllPlatform {
	public UIResusableLibrary cafFunctional;
	By transBetweenAccTab = getObject("pd.transBetweenAccTab");
	By managePayeeTab = getObject("pd.managepayeetab");
	By standingorderlink = getObject("pdStandingOrder.linkStandingOrder");
	By linkDirectDebit = getObject("pd.linkDirectDebit");
	By linkmobiletopup = getObject("pd.mobileTopUp");	
	By linkFDP = getObject("pd.FutureDatedPaymentlink");
	By linksendmoney = getObject("pd.SendMoneylink");
	By linkcancelDirectdebit  = getObject("pd.canceldirectdebitlink");
	By linkPBP  = getObject("pd.PBPlink");
	
	public Payment_ManagePaymentPage(WebDriver driver) {
		super(driver);
		cafFunctional = new UIResusableLibrary(driver);
		PageFactory.initElements(driver, this);

	}
	/*---------------------------------Start <PD_PaymentOptions>----------------------------------------
	 Function Name: PD_PaymentOptions
	 Argument Name:PDOptionType
	 Purpose: Navigate to Payments tab and click on options available on page based on arguments passed.
	 Author Name: CAF Automation 
	 Create Date: 13-06-2021
	 Modified Date| Modified By  |Modified purpose 
	  22/06/2021      C113331     Code update
	 -----------------------------------End <PD_PaymentOptions>---------------------------------------
	 */
	public void PD_PaymentOptions(String PDOptionType) {
		try {
			boolean bFlagPayOpt = false;
			ClickOnPaymentsTab();
			switch (PDOptionType.trim().toUpperCase()) {
			case "SEND_MONEYORPAY_BILL":
				if (isElementDisplayed(linksendmoney)) {
					clickJS(linksendmoney);
					bFlagPayOpt = true;
				} else {
					bFlagPayOpt = false;
				}
				
				break;
			case "TRANSFER_BETWEEN_MY_ACCOUNTS":
				if (isElementDisplayed(transBetweenAccTab)) {
					clickJS(transBetweenAccTab);
					bFlagPayOpt = true;
				} else {
					bFlagPayOpt = false;
				}
				break;
			case "STANDING_ORDERS":
				if (isElementDisplayed(standingorderlink)) {
					clickJS(standingorderlink);
					bFlagPayOpt = true;
				} else {
					bFlagPayOpt = false;
					}
				
				break;
			case "MANAGE_PAYEES":
				if (isElementDisplayed(managePayeeTab)) {
					clickJS(managePayeeTab);
					bFlagPayOpt = true;
				} else {
					bFlagPayOpt = false;
					}
								break;
			case "MOBILE_TOPUP":
				if (isElementDisplayed(linkmobiletopup)) {
					clickJS(linkmobiletopup);
					bFlagPayOpt = true;
				} else {
					bFlagPayOpt = false;
				}
				
				break;
			case "PRIVATE_BANKING":
				if (isElementDisplayed(linkPBP)) {
					clickJS(linkPBP);
					bFlagPayOpt = true;
				} else {
					bFlagPayOpt = false;
				}
				break;
			case "DIRECT_DEBITS":
				if (isElementDisplayed(linkDirectDebit)) {
					clickJS(linkDirectDebit);
					bFlagPayOpt = true;
				} else {
					bFlagPayOpt = false;
				}
				
				break;
			case "FUTURE_DATED_PAYMENTS":
				if (isElementDisplayed(linkFDP)) {
					clickJS(linkFDP);
					bFlagPayOpt = true;
				} else {
					bFlagPayOpt = false;
				}
				break;
			case "CANCEL_DIRECTDEBITS":
				if (isElementDisplayed(linkcancelDirectdebit)) {
					clickJS(linkcancelDirectdebit);
					
					bFlagPayOpt = true;
				} else {
					bFlagPayOpt = false;
				}
				break;
				
			}
			
			if (bFlagPayOpt = true) {
				logMessage(PDOptionType + " Option is selected successfully");
				appendScreenshotToCucumberReport();
				insertMessageToHtmlReport("Success in Payment type selection and navigation to page " + PDOptionType);
				Allure.step("Success in Payment type selection and navigation to page" + PDOptionType,Status.PASSED);
				
			}
			else{ 
				logMessage(PDOptionType + " Option is not selected successfully");
				appendScreenshotToCucumberReport();
				insertMessageToHtmlReport("Failure in Payment type selection and navigation to page " + PDOptionType);
				Allure.step("Failure in Payment type selection and navigation to page " + PDOptionType,Status.PASSED);
			}
			
		} catch (Exception e) {
			logError("Error occured inside Payment options type" + e.getMessage());
			appendScreenshotToCucumberReport();
			insertErrorMessageToHtmlReport("Failure in Payment type selection and navigation to page " + PDOptionType);

		}
	}
	/*---------------------------------Start <ClickOnPaymentsTab>----------------------------------------
	 Function Name: ClickOnPaymentsTab
	 Argument Name:NA
	 Purpose: To click on Payments tab.
	 Author Name: CAF Automation 
	 Create Date: 13-06-2021	 
	 -----------------------------------End <ClickOnPaymentsTab>---------------------------------------
	 */
	private void ClickOnPaymentsTab() throws InterruptedException {
		try {
			if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
				isElementDisplayed(getObject(devTypeToGetProperty + "home.tabPayments"));
				clickJS(getObject(devTypeToGetProperty + "home.tabPayments"));
			} else if (devTypeToGetProperty.equalsIgnoreCase("mw.")) {
				isElementDisplayed(getObject(devTypeToGetProperty + "home.tabPayments"));
				clickJS(getObject(devTypeToGetProperty + "home.tabPayments"));
			} else {
				if(devTypeToGetProperty.equalsIgnoreCase("app.")){
					isElementDisplayed(getObject(devTypeToGetProperty + "home.tabPayments"));
					clickJS(getObject(devTypeToGetProperty + "home.tabPayments"));
				}
				//logMessage("Payments tab is not clicked ");
			}
		} catch (Exception e) {
			insertMessageToHtmlReport("Payments tab is not clicked Successfully ");
			appendScreenshotToCucumberReport();
		}
	}

	/*---------------------------------Start <ClickOnPaymentsTab>----------------------------------------
	 Function Name: payFrom
	 Argument Name:NA
	 Purpose: To .
	 Author Name: CAF Automation
	 Create Date: 13-06-2021
	 -----------------------------------End <ClickOnPaymentsTab>---------------------------------------
	 */

	public void payFrom_SendMoney(String fromAccount) throws Exception {
		try {
			isElementDisplayed(getObject(devTypeToGetProperty + "AddPayee.headerPayFrom"));
			isElementDisplayed(getObject("Payments.lblPayFrom"));
			isElementDisplayed(getObject("Payments.lblAccount"));
			isElementDisplayed(getObject("SendMoney.lstSelectAccount"));
			clickJS(getObject("SendMoney.lstSelectAccount"),"Select account");
			clickJS(By.xpath("//li[contains(text(),'"+ fromAccount.split("~")[1].trim() +"')]"));
			while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
				waitForPageLoaded();
			}
			insertMessageToHtmlReport("From Account '"+fromAccount+"' is selected successfully");
		} catch (Throwable e) {
			logError("Error in payFrom_SendMoney , Error = "+e.getMessage());
			Assert.fail("Unable to select from Account");
		}
	}


	private String parseValue(String text){
		String newText;
		if(text.equalsIgnoreCase("NA")){
			newText="";
		}else{
			newText=text;
		}
		return newText;
	}

	public void formValidationsForAllUsers() throws Exception {

		//new LoginPageNew();
		String data = System.getProperty("FormValidation.DataALL");
		String[] arr = data.split(";");
		//Pay from::CURRENT ACCOUNT ~ 8369;Pay to::Payee post ~ 6953;Amount::2.20;Reason for payment::test;Relationship_Status::NA;ProcessTransfer::NA;Transfer_Date::NA;
		String strPayFromAccount = parseValue(arr[0].split("::")[1]);
		String strPayToAccount = parseValue(arr[1].split("::")[1]);
		String strAmount = parseValue(arr[2].split("::")[1]);
		String strReasonForPayment=parseValue(arr[3].split("::")[1]);
		String strRelationshipStatus=parseValue(arr[4].split("::")[1]);
		String strProcessTransferType=parseValue(arr[5].split("::")[1]);
		String strTransferDate=parseValue(arr[6].split("::")[1]);

		payFrom_SendMoney(strPayFromAccount);
		payTo_SendMoney(strPayToAccount);
		amount_Sendmoney(strAmount);
		reasonForPayment(strReasonForPayment);
		VerifyMessageToPayee(strRelationshipStatus);
		continue_SendMoney();
		reviewPage_SendMoney(strProcessTransferType,System.getProperty("FormValidation.VerifyDetails"));
		continue_SendMoney();
		LoginPageNew login = new LoginPageNew(driver);
		login.enterRequiredPin(System.getProperty("pin"));
		login.clickPinContinueButton();
		//pin code
		pushNotification_Acccept();
		success_SendMoney(strPayFromAccount,strAmount,strPayToAccount,strProcessTransferType,strTransferDate);
		clickBackToHomeButton();
	}


	public void payTo_SendMoney(String strPayToAccount) throws Exception {
		try {
			while (isElementDisplayed(getObject("launch.spinSpinner"), 10)) {
				waitForPageLoaded();
				Thread.sleep(3000);
			}
			isElementDisplayed(getObject("Payments.lblPayTo"));
			isElementDisplayed(getObject(devTypeToGetProperty + "Payments.lblSelectPayee"));
			if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
				isElementDisplayed(getObject("sendMoney.lstToFrom"));
				while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
					waitForPageLoaded();
				}
				clickJS(getObject("sendMoney.lstToFrom"));
				if (strPayToAccount.contains("~")) {
					waitForElementToBeDisplayed(By.xpath("//li[contains(text(),'" + strPayToAccount.split("~")[1] + "')]"));
					clickJS(By.xpath("//li[contains(text(),'" + strPayToAccount.split("~")[1] + "')]"), "Payee from list");
				} else {
					waitForElementToBeDisplayed(By.xpath("//li[contains(text(),'" + strPayToAccount + "')]"));
					scrollToView(By.xpath("//li[contains(text(),'" + strPayToAccount + "')]"));
					clickJS(By.xpath("//li[contains(text(),'" + strPayToAccount + "')]"), "Payee from list");
				}
			} else {
				isElementDisplayed(getObject(devTypeToGetProperty + "sendMoney.lstToFrom"));
				clickJS(getObject(devTypeToGetProperty+ "sendMoney.lstToFrom"), "Select payee dropdown");
				if(strPayToAccount.contains("~")){
					while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
						waitForPageLoaded();
					}
					clickJS(getObject("xpath~//*[contains(@class,'boi_label')][contains(text(),'" + strPayToAccount.split("~")[0].trim() + "')]"), "Payee from list");
				}else{
					while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
						waitForPageLoaded();
					}
					clickJS(getObject("xpath~//*[contains(@class,'boi_label')][contains(text(),'" + strPayToAccount+ "')]"), "Payee from list");
				}
				while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
					waitForPageLoaded();
				}
				//clickJS(getObject("xpath~//*[contains(@class,'boi_label')][contains(text(),'" + strPayToAccount.split("~")[0].trim() + "')]"), "Payee from list");
			}
			insertMessageToHtmlReport("To Account '"+strPayToAccount+"' is selected ");
			appendScreenshotToCucumberReport();
			while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
				waitForPageLoaded();
			}
		} catch (Throwable e) {
			logError("Error in payTo money , Error= "+e.getMessage());
			Assert.fail("Unable to select to account");
		}
	}

	public void amount_Sendmoney(String amount) throws Exception {
		try {
			String strAmount ="";
			if (!isElementDisplayed(getObject("xpath~//input[contains(@name,'INTERNATIONALFORM[1].AMOUNT')]"),3)){
				strAmount = "sendMoney.edtAmount";
			} else {
				strAmount = "sendMoney.edtInternationalAmount";
			}
			isElementDisplayed(getObject(strAmount));
			setValue(getObject(strAmount), amount);
			waitForPageLoaded();
			insertMessageToHtmlReport("Amount '"+amount+"' set successfully");
		} catch (Throwable e) {
			logError("Error in amount send money , Error = "+e.getMessage());
			Assert.fail("Unable to set Amount");
		}
	}

	public void reasonForPayment(String strReasonForPayment) throws Exception{
		try {
			isElementDisplayed(getObject("sendMoney.lblReasonForPayment"));
			sendKeysJS(getObject("sendMoney.edtReasonForPayment"),strReasonForPayment);
			insertMessageToHtmlReport("Reason for payment is set successfully");
		} catch (Throwable e) {
			logError("Error in reason For payment, Error = "+e.getMessage());
			Assert.fail("Assertion error : Error in reason For payment, Error = "+e.getMessage());
		}
	}


	public void VerifyMessageToPayee(String strMsgToPayee) throws Exception {
		try {
			if (isElementDisplayed(getObject("SendMoney.txtMsgToPayee"), 5)) {
				scrollToView(getObject("SendMoney.txtMsgToPayee"));
				//clickJS(getObject("SendMoney.txtMsgToPayee"), "Click Message to Payee.");
				setValue(getObject("SendMoney.txtMsgToPayee"), strMsgToPayee);
				Thread.sleep(1000);
				driver.findElement(getObject("SendMoney.txtMsgToPayee")).sendKeys(Keys.TAB);
			}
		} catch (Throwable e) {
			logError("Error in VerifyMessageToPayee, Error = "+e.getMessage());
		}
	}

	public void continue_SendMoney() throws Exception {
		try{
			scrollToView(getObject("SendMoney.btnContinue"));
			clickJS(getObject("SendMoney.btnContinue"));
			//driver.findElement(getObject("SendMoney.btnContinue")).click();
			logMessage("continue_SendMoney is clicked");
			insertMessageToHtmlReport("continue_SendMoney is clicked");
		}catch(Exception e){
			scrollToView(getObject("SendMoney.btnContinue"));
			clickJS(getObject("SendMoney.btnContinue"), "Continue");
			insertMessageToHtmlReport("continue_SendMoney is clicked");
		}
		waitForPageLoaded();
	}

	public void reviewPage_SendMoney(String strProssTran,String verifyDetails )throws Exception {
		try {
			LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
			String[] strVerifyDetails = verifyDetails.split(";");
			for (int i = 0; i < strVerifyDetails.length; i++) {
				String strFieldName = strVerifyDetails[i].split("::")[0];
				String strFieldvalue = strVerifyDetails[i].split("::")[1];
				if(strFieldName.equalsIgnoreCase("Pay from")&& strFieldvalue.contains("~")){
					strFieldvalue = strFieldvalue.split("~")[1];

				}
				mapData.put(strFieldName, strFieldvalue);
			}

			for (Map.Entry obj : mapData.entrySet()) {
				if (getText(getObject("xpath~//*[contains(text(),'" + obj.getKey() + "')]/../../following-sibling::*")).contains(obj.getValue().toString())) {
					logMessage("reviewPage_SendMoney is correctly displayed on review page");
				} else {
					logError("reviewPage_SendMoney is NOT correctly displayed on review page");
					//appendScreenshotToCucumberReport();
				}
			}
			if (strProssTran.equalsIgnoreCase("Schedule future date")) {
				isElementDisplayedWithLog(getObject("Payments.lblProcessPayment"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
				isElementDisplayedWithLog(getObject("TBA.lblReviewProcessing"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
				isElementDisplayedWithLog(getObject("TBA.lblReviewProcessMessge"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
				isElementDisplayedWithLog(getObject("Payments.txtDailyLimits"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
				isElementDisplayedWithLog(getObject("payments.txtDailyLimitsMsg"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
			}
		} catch (Exception e) {
			logError("Error in reviewPage_SendMoney, Error = "+e.getMessage());
		}

	}

	public void success_SendMoney(String strPayFromAccount,String strAmount,String strPayToAccount,String strProssTran,String strTransferDate) throws Exception {
		//PayeeAddConfirmation("SendMoney.parentConfirmation");
		try {
			String dataSectionUI = getText(getObject("SendMoney.parentConfirmation"));
			while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), Integer.parseInt(System.getProperty("IMPLICITWAIT")))) {
				waitForPageLoaded();
			}
			isElementDisplayed(getObject(devTypeToGetProperty+ "Payments.lblHeaderSendMoney"));
			isElementDisplayed(getObject("Payments.lblSuccess"));

			if(!strPayFromAccount.equalsIgnoreCase("")) {
				isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'" + strPayFromAccount.split("~")[1] + "')]"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
				if (isElementDisplayed(getObject("xpath~//*[contains(@class,'gutter')][contains(text(),'" + strAmount + "')]/.."), 3)) {
					isElementDisplayedWithLog(getObject("xpath~//*[contains(@class,'gutter')][contains(text(),'" + strAmount + "')]/.."),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
				} else if (isElementDisplayed(getObject("xpath~//*[contains(@style,'inline-block')]//*[contains(text(),'" + strAmount + "')]/.."), 3)) {
					isElementDisplayedWithLog(getObject("xpath~//*[contains(@style,'inline-block')]//*[contains(text(),'" + strAmount + "')]/.."),3);
				}
				if (isElementDisplayed(getObject("xpath~//*[contains(text(),'DropDownDisplayName')]/following-sibling::*[contains(text(),'" + strPayToAccount.split("~")[0].trim() + "')]"), 3)) {
					isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'DropDownDisplayName')]/following-sibling::*[contains(text(),'" + strPayToAccount.split("~")[0].trim() + "')]"),3);
				} else if (isElementDisplayed(getObject("xpath~//div[not(contains(@style,'display: none;'))][contains(@class,'page')]//*[contains(text(),'To')]/following-sibling::*[contains(text(),'" + strPayToAccount.split("~")[0].trim() + "')]"), 3)) {
					isElementDisplayedWithLog(getObject("xpath~//div[not(contains(@style,'display: none;'))][contains(@class,'page')]//*[contains(text(),'To')]/following-sibling::*[contains(text(),'" + strPayToAccount.split("~")[0].trim() + "')]"),3);
				}
				insertMessageToHtmlReport("From Account '"+strPayFromAccount+"' , To Account '"+strPayToAccount+"' ,  and Amount '"+strAmount+"' ,  is displayed successfully in success send money page");
			}

			isElementDisplayedWithLog(getObject("SendMoney.btnBacktoHome"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
			isElementDisplayedWithLog(getObject("SendMoney.btnBackToPayment"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
			insertMessageToHtmlReport("Back to home and Back to Payment button are visible");

			if (strProssTran.equalsIgnoreCase("Schedule future date")) {
				isElementDisplayedWithLog(getObject("TBA.lblReviewScheduleFor"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
				if (getText(getObject("TBA.lblReviewScheduleFor")).contains(strTransferDate.replace("/", "\n"))) {
					logMessage("success_SendMoney is displayed same as given on success page");
				} else {
					logError("success_SendMoney , Schedule for date is not same as given on success page");
					appendScreenshotToCucumberReport();
				}
				isElementDisplayedWithLog(getObject("TBA.lblReviewPageMsg"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
			}
			isElementDisplayedWithLog(getObject("TBA.lblReviewTimeOfRequest"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
			appendScreenshotToCucumberReport();
			logMessage("Success in sending money");
			insertMessageToHtmlReport("Success in Sending money ");
		} catch (Exception e) {
			logError("Error in success_SendMoney, Error = "+e.getMessage());
		}
	}


	public void pushNotification_Acccept() throws Exception {
		//waitForPageLoaded();
		try {
			Thread.sleep(1000);
			if (System.getProperty("RUNTYPE").equalsIgnoreCase("MOBILEAPP")) {
				getAppiumDriver().context("NATIVE_APP");
				if(driver.toString().contains("IOS")){
					if (isElementDisplayed(getObject("id~Swipe to approve"), 15)) {
						JavascriptExecutor js = (JavascriptExecutor) driver;
						HashMap<String, String> scrollObject = new HashMap<String, String>();
						scrollObject.put("direction", "right");
						scrollObject.put("element", ((RemoteWebElement) getAppiumDriver().findElementByAccessibilityId("Swipe to approve")).getId());
						js.executeScript("mobile: swipe", scrollObject);
						logMessage("Push Notification Accepted");
						insertMessageToHtmlReport("Push Notification Accepted");
						appendScreenshotToCucumberReport();
						changeNativeToWebview();
					} else {
						logMessage("Accept Push Notification object is not found on the screen");
					}
				}else{
					if (isElementDisplayed(getObject("id~com.bankofireland.tcmb:id/acceptSlideButton"), 10)) {
						MobileElement el1 = (MobileElement) getAppiumDriver().findElementById("com.bankofireland.tcmb:id/acceptSlideButton");
						int startX = el1.getLocation().getX();
						int startY = el1.getLocation().getY();
						int intWidth = el1.getRect().getWidth();
						int endX = startX + intWidth;
						(new TouchAction(getAppiumDriver())).press(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, startY)).release().perform();
						logMessage("Push Notification Accepted");
						insertMessageToHtmlReport("Push Notification Accepted");
						appendScreenshotToCucumberReport();
						changeNativeToWebview();
					} else {
						logMessage("Accept Push Notification object is not found on the screen");
					}
				}
			} else {
				waitForPageLoaded();
				waitForJQueryLoad();
			}
		} catch (Exception e) {
			logError("Error in Push notification , Error = "+e.getMessage());
		}
	}

	public void clickBackToHomeButton() throws Exception{
		try {
			scrollToView(getObject("SendMoney.btnBacktoHome"));
			clickJS(getObject("SendMoney.btnBacktoHome"),"Back to home");
			waitForPageLoaded();
			while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 10)) {
				waitForPageLoaded();
			}
			isElementDisplayedWithLog(getObject(devTypeToGetProperty+ "home.lblWelcome"),Integer.parseInt(System.getProperty("IMPLICITWAIT")));
		} catch (Throwable e) {
			logError("Error in clickBackToHomeButton , Error = "+e.getMessage());
		}
	}

	//CFPUR80_ETE_TC063 starts from error message

	public void sepaReferenceForROI() throws Exception {
		String data = System.getProperty("FormValidation.DataROI");
		String[] arr = data.split(";");

		String strPayFromAccount = parseValue(arr[0].split("::")[1]);
		String strPayToAccount = parseValue(arr[1].split("::")[1]);
		String strAmount = parseValue(arr[2].split("::")[1]);
		String strProcessTransferType=parseValue(arr[3].split("::")[1]);
		String strTransferDate=parseValue(arr[4].split("::")[1]);
		String strRefNum=parseValue(arr[5].split("::")[1]);
		String strBillRefNum=parseValue(arr[6].split("::")[1]);

		payFrom_SendMoney(strPayFromAccount);
		payTo_SendMoney(strPayToAccount);
		amount_Sendmoney(strAmount);
		errorMsg_SendMoney(strRefNum);
		reference_SendMoney(strBillRefNum);
		continue_SendMoney();
		reviewPage_SendMoney(strProcessTransferType,System.getProperty("FormValidation.VerifyDetails1"));
		continue_SendMoney();
		//pin
		LoginPageNew login = new LoginPageNew(driver);
		login.enterRequiredPin(System.getProperty("pin"));
		login.clickPinContinueButton();
		pushNotification_Acccept();
		success_SendMoney(strPayFromAccount,strAmount,strPayToAccount,strProcessTransferType,strTransferDate);
	}




	public void errorMsg_SendMoney(String strRefernceNumber) throws Exception {
		try {
			String strErrRefernce = System.getProperty("FormValidation.ErrorText");
			String[] arrStrErrRefernce = strErrRefernce.split(";");
			waitForPageLoaded();
			if(isElementDisplayed(getObject("sendMoney.lnkEditReference"),5)){
				clickJS(getObject("sendMoney.lnkEditReference"),"Edit Reference link");
				waitForJQueryLoad();
				waitForPageLoaded();
			}
			isElementDisplayedWithLog(getObject("sendMoney.edtRefernce"),  5);
			setValue(getObject("sendMoney.edtRefernce"),  "");
			continue_SendMoney();
			fetchTextToCompare(getObject("sendMoney.errRefernce"),
					arrStrErrRefernce[0],"Error message for blank value in reference field");
			appendScreenshotToCucumberReport();
			setValue(getObject("sendMoney.edtRefernce"),strRefernceNumber);
			continue_SendMoney();
			fetchTextToCompare(getObject("sendMoney.errRefernce"),
					arrStrErrRefernce[1], "Error message for special char in reference field");
			appendScreenshotToCucumberReport();
		} catch (Exception e) {
			logError("Error in errorMsg_SendMoney , Error = "+e.getMessage());
			Assert.fail("Assertion error errorMsg_SendMoney");
		}
	}

	public boolean fetchTextToCompare(By by, String textToBeCompared,
									  String textToBeReported) {
		String textFetched = null;
		boolean IstextFetchedEqual = false;

		try {
			WebDriverWait wait = new WebDriverWait((WebDriver) driver, 40);
			WebElement element = wait.until(ExpectedConditions
					.presenceOfElementLocated(by));
			scrollToView(by);
			textFetched = element.getText();
			textFetched = textFetched.toString().trim();
		} catch (Exception e) {
			logError("Exception in visibility of element in fetchTextToCompare method, Error = "+e.getMessage());
		}

		if (textFetched.toUpperCase().contains(textToBeCompared.trim().toUpperCase())) {
			IstextFetchedEqual = true;
			logMessage(textToBeReported+ " matches the expected value "+textToBeCompared);
			insertMessageToHtmlReport(textToBeReported+ " matches the expected value "+textToBeCompared);
		} else {
			IstextFetchedEqual = false;
			logError(textToBeReported+ " does not matches the expected value "+textToBeCompared);
			insertErrorMessageToHtmlReport(textToBeReported+ " does not matches the expected value "+textToBeCompared);
		}
		return IstextFetchedEqual;
	}


	public void reference_SendMoney(String strBillReference) throws Exception {
		try {
			waitForPageLoaded();
			scrollToView(getObject("sendMoney.edtRefernce"));
			if(isElementDisplayed(getObject("sendMoney.lnkEditReference"),5)){
				clickJS(getObject("sendMoney.lnkEditReference"),"Edit Reference link");
				waitForJQueryLoad();waitForPageLoaded();
			}
			waitForJQueryLoad();
			waitForPageLoaded();
			setValue(getObject("sendMoney.edtRefernce"), strBillReference);
			insertMessageToHtmlReport("Successfully entered rreferenceas , "+strBillReference);
			waitForJQueryLoad();
			waitForPageLoaded();
		} catch (InterruptedException e) {
			logError(" Error in reference_SendMoney, Error = "+e.getMessage());
		}
	}

	// CFPUR83_TC047 starts

	public void sepaReferenceForUKPayee() throws Exception {
		String data;
		if(System.getProperty("formType").equalsIgnoreCase("SEPA_UK_CURRENT")){
			data = System.getProperty("FormValidation.UKPayeeCurrentAcc");
		}else{
			data = System.getProperty("FormValidation.UKPayee");
		}
		String[] arr = data.split(";");
		String strPayFromAccount = parseValue(arr[0].split("::")[1]);
		String strPayToAccount = parseValue(arr[1].split("::")[1]);
		String amount=parseValue(arr[2].split("::")[1]);
		String jurisdictionType=parseValue(arr[3].split("::")[1]);
		String currencySymbol=parseValue(arr[4].split("::")[1]);
		String strRefNum=parseValue(arr[5].split("::")[1]);
		String strBillerDesc=parseValue(arr[6].split("::")[1]);
		String strOperation=parseValue(arr[7].split("::")[1]);
		String strProcessTransfer=parseValue(arr[8].split("::")[1]);
		String strTransferDate=parseValue(arr[9].split("::")[1]);
		String strNavigation=parseValue(arr[10].split("::")[1]);

		payFrom_SendMoney(strPayFromAccount);
		payTo_SendMoney(strPayToAccount);
		sendMoneyDetailsUKDomestic(jurisdictionType,amount,currencySymbol,strRefNum,strBillerDesc,strOperation);
		verifyUKDomesticPaymentsReviewPage(amount,currencySymbol,System.getProperty("FormValidation.VerifyDetails2"),strProcessTransfer,strTransferDate,strOperation);
		//pin
		LoginPageNew login = new LoginPageNew(driver);
		login.enterRequiredPin(System.getProperty("pin"));
		login.clickPinContinueButton();
		pushNotification_Acccept();
		checkUKDomesticPaymentsConfirmationPage(strProcessTransfer,strTransferDate,strNavigation);
		//selectAccountTab -> AccountOptions
		//verifyAccountClickAndNavigate -> AccountOptions
		/*VerifyInprogressTransactionDetails();
		VerifyAmountPatternInTransactions();*/

	}

	public void sendMoneyDetailsUKDomestic(String JurisdictionType,String payAmount,String currencySymbol,String referenceNumber,String billerDescription,String strOperationOnPutonHold) throws Exception {
		//Information text for Cross Customer
		if (JurisdictionType.equals("Cross")) {
			String UiAlertText = getText(getObject("SendMoney.infotxtUKDomesticCross"));
			String expectedAlerttxt = "The payees and currencies available to you for payments can differ between your EUR and GBP accounts.";
			if (UiAlertText.equals(expectedAlerttxt)) {
				logMessage("Information text for UK Domestic Payment for Cross is  displayed is as Expected");
				insertMessageToHtmlReport("Information text for UK Domestic Payment for Cross is  displayed is as Expected");
			} else {
				logError("Information text for UK Domestic Payment for Cross is  displayed is NOT as Expected");
				insertErrorMessageToHtmlReport("Information text for UK Domestic Payment for Cross is  displayed is NOT as Expected");
			}
		}

		// Verify ALert Text
		if (JurisdictionType.equals("NI") || (JurisdictionType.equals("GB"))) {
			String UiAlertText = getText(getObject("SendMoney.alrttxtUKDomestic"));
			String expectedAlerttxt = "You're sending money within the UK.";
			if (UiAlertText.equals(expectedAlerttxt)) {
				logMessage("Alert text for UK Domestic Payment for NI/GB is  displayed is as Expected");
				insertMessageToHtmlReport("Alert text for UK Domestic Payment for NI/GB is  displayed is as Expected");
			} else {
				logError("Alert text for UK Domestic Payment for NI/GB is  displayed is NOT as Expected ");
				insertErrorMessageToHtmlReport("Alert text for UK Domestic Payment for NI/GB is  displayed is NOT as Expected ");
			}
		}

		//Amount
		if (isElementDisplayed(getObject("SendMoney.lblAccount"))) {
			logMessage("Label 'Amount' is displayed on Send Money enter details Page");
			String uiCurrency = getText(getObject("SendMoney.lblCurrSymbol"));
			if (uiCurrency.equals(currencySymbol)) {
				logMessage("Currency in Amount textbox is correctly displayed on Send Money enter details Page");
				insertMessageToHtmlReport("Currency in Amount textbox is correctly displayed on Send Money enter details Page");
			} else {
				logError("Currency in Amount textbox is not correctly displayed on Send Money enter details Page");
				insertErrorMessageToHtmlReport("Currency in Amount textbox is not correctly displayed on Send Money enter details Page");
			}
			if (!payAmount.equals("")) {
				setValue(getObject("SendMoney.txtUKDomCurrAmount"), payAmount);
				insertMessageToHtmlReport("Amount is successfully set to = "+payAmount);
				waitForPageLoaded();
			}
		} else {
			logError("Label 'Amount' is NOT displayed on Send Money enter details Page");
		}

		//Reference
		if (isElementDisplayed(getObject("AddBillPayee.lblReference"), 5)) {
			logMessage("Reference Label is displayed");
			if (isElementDisplayed(getObject("SendMoney.lblUKDomVisibletoPayee"), 5)) {
				logMessage("Visible to Payee Label is displayed");
				if (isElementDisplayed(getObject("SendMoney.txtUKDomReference"), 5)) {
					logMessage("Reference Text box is displayed");
					if (getText(getObject("SendMoney.txtUKDomReference")).equals(referenceNumber)) {
						scrollToView(getObject("SendMoney.txtUKDomReference"));
						logMessage("Reference Text '"+referenceNumber+"' is displayed");
						insertMessageToHtmlReport("Reference label, Payee label and Reference text is visible");
					} else {
						logError("Reference Text '"+referenceNumber+"' is Not displayed");
						insertErrorMessageToHtmlReport("Reference label, Payee label and Reference text is Not visible");
					}
				} else {
					logError("Reference Text box is not displayed");
				}
			} else {
				logError("Visible to Payee label is not displayed");
			}
		} else {
			logError("Reference label is not displayed");
		}

		//Description
		if (isElementDisplayed(getObject("AddBillPayee.lblDescription"), 5)) {
			logMessage("Description Label is displayed");
			if (isElementDisplayed(getObject("SendMoney.txtUKDomDescription"), 5)) {
				logMessage("Description Prefilled Text box is displayed");
				if (getText(getObject("SendMoney.txtUKDomDescription")).equals(billerDescription)) {
					scrollToView(getObject("SendMoney.txtUKDomDescription"));
					logMessage("Description Text '"+billerDescription+"' is displayed");
				} else {
					logError("Description Text '"+billerDescription+"' is displayed");
				}
			} else {
				logError("Description Prefilled Text box is not displayed");
			}
		} else {
			logError("Description Label is not displayed");
		}

		//Process Payment
		if (isElementDisplayed(getObject("SendMoney.lblUKDomProcessPayment"), 5)) {
			logMessage("Process Payment Label is displayed");
			insertMessageToHtmlReport("Process Payment Label is displayed");
		/*	TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
			if (dataTable.getData("General_Data", "ErrorText").equals("")) {
				transBetAccnt.selectOptProcessTransfer();
			} else {
				transBetAccnt.errorMsgsScheduleFutureDate();
			}*/

		} else {
			insertErrorMessageToHtmlReport("Process Payment Label is not displayed");
			logError("Process Payment Label is not displayed");
		}

		//continue & Goback
		if (isElementDisplayed(getObject("AddPayee.GoBack"), 5)) {
			logMessage("Button 'Go back' is displayed on Page");
		} else {
			logError("Button 'Go back' is NOT displayed on Page");
		}

		if (isElementDisplayed(getObject("AddPayee.Continue"), 5)) {
			appendScreenshotToCucumberReport();
			logMessage("Button 'Continue' is displayed on Page");
		} else {
			logError("Button 'Continue' is NOT displayed on Page");
		}

		if (!strOperationOnPutonHold.equals("")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			switch (strOperationOnPutonHold.toUpperCase()) {
				case "CONTINUE":
					js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.Continue")));
					Thread.sleep(2000);
					clickJS(getObject("AddPayee.Continue"), "'Continue', on Page");
					break;
				case "GO BACK":
					js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.GoBack")));
					Thread.sleep(2000);
					clickJS(getObject("AddPayee.GoBack"), "'Go back', on Page");
					if (isElementDisplayed(getObject("xpath~//h1[@title='Payments']"), 5)) {
						logMessage("Page navigated to Payment landing Page upon clicking 'Go back");
					} else {
						logError("Page does not navigated to Payment landing Page upon clicking 'Go back'");
					}
					break;
				default:
					logError("Please provide the valid operation on Send Money Page");
			}
		}
	}

	public void verifyUKDomesticPaymentsReviewPage(String strPayAmount,String symbol,String verifyDetails,String strProssTran,String strTransferDate,String operation) throws Exception {
		if (isElementDisplayed(getObject("xpath~//h3[text()='Review']"), 5)) {
			logMessage("Section header 'Review' is displayed on Send Money Review page");
		} else {
			logError("Section header 'Review' is NOT displayed on Send Money Review page");
		}

		//Amount in
		String strAmountIn = symbol+strPayAmount;
		//dataTable.getData("General_Data", "Currency_Symbol") + dataTable.getData("General_Data", "Current_Balance");
		if (isElementDisplayed(getObject("xpath~//*[text()='Amount']"), 5)) {
			logMessage("Label 'Amount' is displayed on Send Money Review page");
			if (isElementDisplayed(getObject("xpath~//*[text()='Amount']/ancestor::div[contains(@class,'ecDIB')]/following-sibling::*/div"), 5)) {
				String actAmount = getText(getObject("xpath~//*[text()='Amount']/ancestor::div[contains(@class,'ecDIB')]/following-sibling::*/div"));
				if (actAmount.equals(strAmountIn)) {
					logMessage("Amount '" + strAmountIn + "' is appearing on Review Page");
					insertMessageToHtmlReport("Amount '" + strAmountIn + "' is appearing on Review Page");
					appendScreenshotToCucumberReport();
				} else {
					logError("Amount '" + strAmountIn + "' is not appearing on Review Page");
					insertErrorMessageToHtmlReport("Amount '" + strAmountIn + "' is not appearing on Review Page");
				}
			}
		} else {
			logError("Label '" + strAmountIn + "' is NOT displayed on Send Money Review page");
			insertErrorMessageToHtmlReport("Label '" + strAmountIn + "' is NOT displayed on Send Money Review page");
		}

		//validate review section details
		LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
		String[] strVerifyDetails = verifyDetails.split(";");

		for (int i = 0; i < strVerifyDetails.length; i++) {
			String strFieldName = strVerifyDetails[i].split("::")[0];
			String strFieldvalue = strVerifyDetails[i].split("::")[1];
			mapData.put(strFieldName, strFieldvalue);
		}

		try {
			for (Map.Entry obj : mapData.entrySet()) {
				if (!obj.getValue().equals("NA")) {
					boolean bflag = driver.findElement(By.xpath("//*[text()='" + obj.getKey() + "']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[text()='" + obj.getValue() + "']")).isDisplayed();
					if (bflag) {
						logMessage("verifyUKDomesticPaymentsReviewPage, " + "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue());
					} else {
						logError("verifyUKDomesticPaymentsReviewPage, " + "'" + obj.getKey() + "' NOT correctly displayed on review page,input::" + obj.getValue());
					}
				} else {
					if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
						logMessage("verifyUKDomesticPaymentsReviewPage"+"'" + obj.getKey() + "' is displayed on review page");
					} else {
						logError("verifyUKDomesticPaymentsReviewPage"+"'" + obj.getKey() + "' is Not displayed on review page");
					}
				}
			}
		} catch (Exception e) {
			logError("Error in verifying Review page details, Error = "+e.getMessage());
		}

		if (strProssTran.equalsIgnoreCase("Schedule future date")) {
			String ActDate = getText(getObject("xpath~//span[text()='Process payment']/ancestor::div[contains(@class,'question-part')]/following-sibling::div")).replace("\n"," ");;
			String ExpDate = strTransferDate.replace("/", " ");
			if (ActDate.equals(ExpDate)) {
				logMessage("verifyUKDomesticPaymentsReviewPage "+"'" + ExpDate + "' correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'");
				insertMessageToHtmlReport("verifyUKDomesticPaymentsReviewPage "+"'" + ExpDate + "' correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'");
			} else {
				insertErrorMessageToHtmlReport("verifyUKDomesticPaymentsReviewPage "+"'" + ExpDate + "' is not correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'");
				logError("verifyUKDomesticPaymentsReviewPage "+"'" + ExpDate + "' is not correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'");
			}
			validateProcessingAndDailylimits(symbol);
		} else {
			validateDailylimitsNCutOffTimes(symbol);
		}
		appendScreenshotToCucumberReport();
		SendMoneyReviewPageOperation(operation);
	}

	public void validateProcessingAndDailylimits(String currencySymbol) throws Exception {

		if (isElementDisplayed(getObject("SendMoney.lblProcessing"), 5)) {
			logMessage("Label 'Processing' is displayed on Review page");
			if (isElementDisplayed(getObject("xpath~//div[contains(text(),'If you schedule a payment for a Saturday, Sunday or bank holiday, we will process it on the next working day.')]"), 10)) {
				logMessage("Text 'If you schedule a payment for a Saturday, Sunday or bank holiday, we will process it on the next working day.' is displayed on Review page");
			} else {
				logError("Text 'If you schedule a payment for a Saturday, Sunday or bank holiday, we will process it on the next working day.' is NOT displayed on Review page");
			}
		} else {
			logError("Label 'Processing' is NOT displayed on Review page");
		}

		if (isElementDisplayed(getObject("SendMoney.lblDailyLimits"), 5)) {
			logMessage("Label 'Daily limits' is displayed on Review page");
			if (currencySymbol.equalsIgnoreCase("€")) {
				if (isElementDisplayed(getObject("xpath~//div[contains(text(),'Your payments are limited to a total of €20,000 per working day.')]"), 10)) {
					logMessage("Text 'Your payments are limited to a total of €20,000 per working day. More about daily limits.' is displayed on Review page");
					scrollToView(getObject("xpath~//div[text()='Your payments are limited to a total of €20,000 per working day. ']/*[text()='More about daily limits.']"));
				} else {
					logError("Text 'Your payments are limited to a total of €20,000 per working day. More about daily limits.' is NOT displayed on Review page");
				}
			} else {
				if (currencySymbol.equalsIgnoreCase("£")) {
					if (isElementDisplayed(getObject("xpath~//div[contains(text(),'Your payments are limited to a total of £20,000 per working day.')]"), 10)) {
						logMessage("Text 'Your payments are limited to a total of £20,000 per working day. More about daily limits.' is displayed on Review page");
						scrollToView(getObject("xpath~//div[text()='Your payments are limited to a total of £20,000 per working day. ']/*[text()='More about daily limits.']"));
					} else {
						logError("Text 'Your payments are limited to a total of £20,000 per working day. More about daily limits.' is NOT displayed on Review page");
					}
				}
			}
		}

	}


	public void validateDailylimitsNCutOffTimes(String currencySymbol) throws Exception {
		if (isElementDisplayed(getObject("SendMoney.lblDailyLimits"), 5)) {
			logMessage("Label 'Daily limits' is displayed on Review page");
			if (currencySymbol.equalsIgnoreCase("€")) {
				if (isElementDisplayed(getObject("xpath~//div[contains(text(),'Your payments are limited to a total of €20,000 per working day.')]"), 10)) {
					logMessage("Text 'Your payments are limited to a total of €20,000 per working day. More about daily limits.' is displayed on Review page");
					scrollToView(getObject("xpath~//div[text()='Your payments are limited to a total of €20,000 per working day. ']/*[text()='More about daily limits.']"));
				} else {
					logError("Text 'Your payments are limited to a total of €20,000 per working day. More about daily limits.' is NOT displayed on Review page");
				}
			} else {
				if ((currencySymbol.equalsIgnoreCase("£"))) {
					if (isElementDisplayed(getObject("xpath~//div[contains(text(),'Your payments are limited to a total of £20,000 per working day.')]"), 10)) {
						logMessage("Text 'Your payments are limited to a total of £20,000 per working day. More about daily limits.' is displayed on Review page");
						scrollToView(getObject("xpath~//div[text()='Your payments are limited to a total of £20,000 per working day. ']/*[text()='More about daily limits.']"));
					} else {
						logError("Text 'Your payments are limited to a total of £20,000 per working day. More about daily limits.' is NOT displayed on Review page");
					}
				}
			}
		} else {
			logError("Label 'Daily limits' is NOT displayed on Review page");
		}

		if (isElementDisplayed(getObject("SendMoney.lblCutOffTimes"), 5)) {
			logMessage("Label 'Cut-off times' is displayed on Review page");
			if (currencySymbol.equalsIgnoreCase("€")) {
				if (isElementDisplayed(getObject("SendMoney.lblCutOffTimesTextEUR"), 5)) {
					logMessage("Text 'Money sent after 3:30pm, or at any time on a Saturday, Sunday or bank holiday, and we will start processing it on the next working day. More about cut-off times.' is displayed on Review page");
				} else {
					logError("Text 'Money sent after 3:30pm, or at any time on a Saturday, Sunday or bank holiday, and we will start processing it on the next working day. More about cut-off times.' is NOT displayed on Review page");
				}
			} else {
				if (currencySymbol.equalsIgnoreCase("£")) {
					if (isElementDisplayed(getObject("SendMoney.lblCutOffTimesTextGBP"), 5)) {
						logMessage("Text 'Money sent after 3:30pm, or at any time on a Saturday, Sunday or Bank Holiday, and we will start processing it on the next working day. More about cut-off times.' is displayed on Review page");
					} else {
						logError("Text 'Money sent after 4:30pm, or at any time on a Saturday, Sunday or Bank Holiday, and we will start processing it on the next working day. More about cut-off times.' is NOT displayed on Review page");
					}
				}
			}
		} else {
			logError("Label 'Cut-off times' is NOT displayed on Review page");
		}
	}

	public void SendMoneyReviewPageOperation(String strOperationOnPutonHold) throws Exception {
		if (!strOperationOnPutonHold.equals("")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			switch (strOperationOnPutonHold.toUpperCase()) {
				case "CONTINUE":
					js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Payments.SendMoneyReviewbtnContinue")));
					Thread.sleep(2000);
					clickJS(getObject("Payments.SendMoneyReviewbtnContinue"), "Clicked on 'Continue', on Send Money Review Page");
					insertMessageToHtmlReport("Clicked on 'Continue', on Send Money Review Page");
					break;
				case "GO BACK":
					js.executeScript("window.scrollTo(0, document.body.scrollHeight);", driver.findElement(getObject( devTypeToGetProperty+"Payments.SendMoneyReviewbtnGoback")));
					Thread.sleep(2000);
					clickJS(getObject( devTypeToGetProperty+"Payments.SendMoneyReviewbtnGoback"), "Clicked on 'Go back', on Send Money Review Page");
					if (isElementDisplayed(getObject("Payments.btnContinue"), 5)) {
						logMessage("Page navigated to Send Money first Page upon clicking 'Go back'");
						insertMessageToHtmlReport("Page navigated to Send Money first Page upon clicking 'Go back'");
					} else {
						insertErrorMessageToHtmlReport("Page does not navigated to Send Money first Page upon clicking 'Go back'");
						logError("Page does not navigated to Send Money first Page upon clicking 'Go back'");
					}
					break;
				default:
					logError("Please provide the valid operation on Send Money Review Page, Continue or Go Back");
			}
		}
	}

	public void checkUKDomesticPaymentsConfirmationPage(String strProssTran,String strTransferDate,String navigationfromConfirmationPage) throws Exception {

		while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
			waitForPageLoaded();
		}
		waitForElementToBeDisplayed(getObject(devTypeToGetProperty + "SendMoney.hdrtxt"));
		if (isElementDisplayed(getObject(devTypeToGetProperty + "SendMoney.hdrtxt"), 5)) {
			logMessage("Section Header 'Send Money' is displayed on Confirmation Page");
			insertMessageToHtmlReport("Section Header 'Send Money' is displayed on Confirmation Page");
		} else {
			logError("Section Header 'Send Money' is Not displayed on Confirmation Page");
			insertErrorMessageToHtmlReport("Section Header 'Send Money' is Not displayed on Confirmation Page");
		}

		if (isElementDisplayed(getObject("AddBillPayee.imgSuccess"), 5)) {
			logMessage("Success Image is Displayed on Confirmation Page");
			insertMessageToHtmlReport("Success Image is Displayed on Confirmation Page");
		} else {
			logError("Success Image is NOT Displayed on Confirmation Page");
			insertErrorMessageToHtmlReport("Success Image is NOT Displayed on Confirmation Page\"");
		}
		verifyElementDetailsConfim("SendMoney.confrmPage");
		appendScreenshotToCucumberReport();
		if (strProssTran.equalsIgnoreCase("Schedule future date")) {
			String ActDate = getText(getObject("xpath~//*[@class='accessibilityStyle'][text()='Day']/..")).replace("\n"," ");
			String ExpDate = strTransferDate.replaceAll("/", " ");
			if (ActDate.replaceAll("  "," ").contains(ExpDate)) {
				logMessage("'" + ExpDate + "' correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'");
				insertMessageToHtmlReport("'" + ExpDate + "' correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'");
			} else {
				logError("'" + ExpDate + "' is not correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'");
				insertErrorMessageToHtmlReport("'" + ExpDate + "' is not correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'");
			}
		}

		if (isElementDisplayed(getObject("SendMoney.btnBackToPayment"), 5)) {
			logMessage("Link Button 'Back to payments' is displayed on Confirmation page");
			insertMessageToHtmlReport("Link Button 'Back to payments' is displayed on Confirmation page");
		} else {
			logError("Link Button 'Back to payments' is NOT displayed on Confirmation page");
			insertMessageToHtmlReport("Link Button 'Back to payments' is NOT displayed on Confirmation page");
		}

		if (isElementDisplayed(getObject("xpath~//div[@class='boi-position-relative  ']/div/div[contains(text(),'Time of request')]"), 5)) {
			logMessage("Time Stamp with message 'Time of request' is displayed on Confirmation page");

			String strRequestSubmitted = getText(getObject("xpath~//div[@class='boi-position-relative  ']/div/div[contains(text(),'Time of request')]"));
			String uiDateText = strRequestSubmitted.split(": ")[1];

			DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
			try {
				df.parse(uiDateText);
				logMessage("Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM");
			} catch (ParseException e) {
				logError("Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'");
			}
		} else {
			logError("Time Stamp with message 'Time of request' is NOT displayed on Cancelled Standing Order Confirmation page");
		}
		appendScreenshotToCucumberReport();

		//Validating Navigation/functionality of the buttons
		if (navigationfromConfirmationPage.equalsIgnoreCase("Back to payments")) {
			appendScreenshotToCucumberReport();
			clickJS(getObject("SendMoney.btnBackToPayment"), "'Back to payments'");
			if (isElementDisplayed(getObject(devTypeToGetProperty + "home.tabPaymentshdr"), 5)) {
				logMessage("Upon clicking 'Back to payments' ,successfully Navigated to Payments screen from Add Payee Confirmation Page");
			} else {
				logError("Upon clicking 'Back to payments' ,navigation unsuccessful to Payments screen from Add Payee Confirmation Page");
			}
		}
	}

	public void verifyElementDetailsConfim(String sectionElement) throws Exception {

		/*String[] arrValidation = dataTable.getData("General_Data", "VerifyContent").split(";");
		if (isElementDisplayed(getObject(sectionElement), 5)) {
			String dataSectionUI = getText(getObject(sectionElement)).toUpperCase();
			report.updateTestLog("verifyElementDetailsConfim", ":: Page Screenshot ::", Status_CRAFT.SCREENSHOT);
			for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
				String strValidateHead = arrValidation[intValidate].split("::")[0];
				String strValidateData = arrValidation[intValidate].split("::")[1];
				if (dataSectionUI.contains(strValidateData.toUpperCase())) {
					report.updateTestLog("verifyElementDetailsConfim", strValidateHead + " value '" + strValidateData + "' is displayed correctly ", Status_CRAFT.PASS);
				} else {
					report.updateTestLog("verifyElementDetailsConfim", strValidateHead + " value '" + strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
				}
			}

		} else {
			report.updateTestLog("verifyElementDetailsConfim", "Section element is not displayed on Screen", Status_CRAFT.FAIL);
		}*/
	}

	//selectAccountTab -> AccountOptions
	//verifyAccountClickAndNavigate -> AccountOptions


	public void VerifyInprogressTransactionDetails() throws Exception{
		List<WebElement> oTransaction = driver.findElements(getObject("TransactionTab.InProgressTableRows"));
		int intNumberOfoTransaction = oTransaction.size();
		int intNumberOfTransactionNew = 0;
		logMessage("On Mobile app when we open transaction tab first time, Total In-Progress Transactions:: " + intNumberOfoTransaction);

		if ((intNumberOfoTransaction <=24) && (!(isElementDisplayed(getObject("Transaction.lblLoadMoreButton"),1)))){
			logMessage("On Mobile app when transactions are less that or equal to 24 'Load More' is not visible.");
		}

		if (isElementDisplayed(getObject("Transaction.lblLoadMoreButton"),5)){
			int intCounter = 2;
			do {
				ScrollAndClickJS("Transaction.lblLoadMoreButton");
				if (intNumberOfoTransaction <= (24 * intCounter)) {
					List<WebElement> oTransactionNew = driver.findElements(getObject("TransactionTab.InProgressTableRows"));
					intNumberOfTransactionNew = oTransactionNew.size();
					logMessage("On Mobile app after clicking 'Load More' button :: " + intNumberOfTransactionNew + " :: In-Progress transactions are Present.");
					intCounter++;
				} else {
					logError("On clicking 'Load more' button , In-Progress transactions count is not getting added..which is not expected.");
				}

			} while (isElementDisplayed(getObject("Transaction.lblLoadMoreButton"), 10));
		}

		List<WebElement> oClockIcon = driver.findElements(getObject("Transaction.icnInprogressClock"));
		if (oClockIcon.size() >= 1) {
			logMessage("The 'clock' icons are present for all In-Progress Transactions");
		}
		else {
			logError("The 'clock' icons are not present for In-Progress Transactions ");
		}
	}

	public void VerifyAmountPatternInTransactions() throws Exception{
		boolean blnReturnValue = false;
		boolean blnReturnFailedValue = true;
		String regExpDateCheck = "[+-]?(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)";
		List<WebElement> oTransaction = driver.findElements(getObject(devTypeToGetProperty+"Transaction.lblInprogTrasacAmount"));
		if (oTransaction.size() > 0 ){
			for(int i = 0; i<oTransaction.size() ; i++){
				blnReturnValue = false;
				String strAmountTovalidate = oTransaction.get(i).getText();
				if (Pattern.matches(regExpDateCheck, strAmountTovalidate)) {
					blnReturnValue = true;
				} else {
					blnReturnFailedValue = false;
					logError(" For In-progress transactions :: Amount value is not matching with the format (either: 1.11 OR 1,111.11 OR 1,111,111.11) and its displayed as ::" + oTransaction.get(i) + ":: which is not expected.");
				}
			}
			if (blnReturnValue && blnReturnFailedValue) {
				logMessage(" For All In-progress transactions :: Amount value is matching with the format (either: 1.11 OR 1,111.11 OR 1,111,111.11).");
			}else{
				logError(" For All In-progress transactions :: Amount value is NOT matching with the format (either: 1.11 OR 1,111.11 OR 1,111,111.11).");
			}

		}else{
			logMessage(" In-progress transactions are not available for this account.");
		}
	}


	public void sepaReferenceForSEPAPayee() throws Exception {

		String data = System.getProperty("FormValidation.SepaPayee");
		String[] arr = data.split(";");

		String strPayFromAccount = parseValue(arr[0].split("::")[1]);
		String strPayToAccount = parseValue(arr[1].split("::")[1]);
		String strAmount = parseValue(arr[2].split("::")[1]);
		String strPaymentType = parseValue(arr[3].split("::")[1]);
		String strProcessTransfer = parseValue(arr[4].split("::")[1]);
		String strTransferDate= parseValue(arr[5].split("::")[1]);
		String strRelationshipStatus = parseValue(arr[6].split("::")[1]);

		payFrom_SendMoney(strPayFromAccount);
		payTo_SendMoney(strPayToAccount);
		amount_Sendmoney(strAmount);
		selectOptProcessTransfer(strPaymentType,strProcessTransfer,strTransferDate);
		VerifyMessageToPayee(strRelationshipStatus);
		continue_SendMoney();
		reviewPage_SendMoney(strProcessTransfer,System.getProperty("FormValidation.VerifyDetails3"));
		continue_SendMoney();
		//pin
		LoginPageNew login = new LoginPageNew(driver);
		login.enterRequiredPin(System.getProperty("pin"));
		login.clickPinContinueButton();
		pushNotification_Acccept();
		success_SendMoney(strPayFromAccount,strAmount,strPayToAccount,strProcessTransfer,strTransferDate);

	}

	public void selectOptProcessTransfer(String strPaymentType,String strProssTran,String strSetDate) throws Exception {

		try {
			String strXpath = "xpath~//*[contains(text(),'" + strProssTran + "')]";
			String strTransferDate = getFutureDate(strSetDate);
			if (strProssTran.equalsIgnoreCase("Schedule future date")) {
				if(isElementDisplayed(getObject(strXpath),4)) {
					clickJS(getObject(strXpath), strProssTran);
					while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
						waitForPageLoaded();
					}
					isElementDisplayed(getObject("TBA.lblSfdInputPageMsg1"));
					if (strPaymentType.contains("Transfer between my accounts")) {
						isElementDisplayed(getObject("TBA.lblSfdInputPageMsg2"));
					} else {
						isElementDisplayed(getObject("Payments.lblSfdInputPageMsg2"));
					}
				}
				enterDate("TBA.edtTransDate", strTransferDate);
			} else {
				clickJS(getObject(strXpath),strProssTran);
				logMessage("Process Transfer 'Today' option is by default selected");
			}
			click(getObject("TBA.edtPayAmount")); //* to extract focus from date selected calender
			Thread.sleep(1000);
		} catch (Exception e) {
			logError(" Error in selectOptProcessTransfer , Error = "+e.getMessage());
		}
	}

	public void enterDate(String objDOB, String strDate) throws Exception {

		//Splitting the date
		try {
			String strYY = strDate.split("/")[2];
			String strMON = strDate.split("/")[1];
			String strDT = strDate.split("/")[0].replaceAll("^0*", "");

			//Date selection
			clickJS(getObject(objDOB), "Date Object");
			waitForPageLoaded();
			selectValueFromDropDown(getObject("launch.lstYearSavingDate"), strYY);
			selectValueFromDropDown(getObject("launch.lstMonthSavingDate"), strMON);
			clickJS(getObject("xpath~//a[.='" + strDT + "']"));
			insertMessageToHtmlReport("Successfully entered date , = "+strDate);
		} catch (Exception e) {
			logError("Error in entering date , Error = "+e.getMessage());
			Assert.fail("Error in entering date");
		}
	}

	public String getFutureDate(String futureDate) throws Exception {
		String date = "";
		String pattern = "dd MMM yyyy";
		LocalDate today = LocalDate.now();
		switch (futureDate) {
			case "Today+1":
				LocalDate Tomorrow = today.plusDays(1);
				date = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(Tomorrow).replace(" ", "/");
				break;
			case "Today+60":
				LocalDate TodayPlus60 = today.plusDays(60);
				date = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(TodayPlus60).replace(" ", "/");
				break;
			case "Today+30":
				LocalDate TodayBetwn60 = today.plusDays(30);
				date = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(TodayBetwn60).replace(" ", "/");
				break;
			default:
				date = futureDate;
		}
		return date;
	}


	public  void domesticTransferUKCustomer() throws Exception {
		String data = System.getProperty("FormValidation.UKDomesticDataTransfer");
		String[] arr = data.split(";");

		String strPayFromAccount = parseValue(arr[0].split("::")[1]);
		String strPayToAccount = parseValue(arr[1].split("::")[1]);
		String strAmount = parseValue(arr[2].split("::")[1]);
		String strPayToCurr = parseValue(arr[3].split("::")[1]);
		String strCurrSymbol = parseValue(arr[4].split("::")[1]);
		
		verifySelectPayfromPayToSendMoney(strPayFromAccount,strPayToAccount,strPayToCurr);
		verifyCurrencyandReference(strCurrSymbol);
		clickgoback();
		appendScreenshotToCucumberReport();
		Payment_ManagePaymentPage paymentsPg = new Payment_ManagePaymentPage(driver);
		paymentsPg.PD_PaymentOptions("SEND_MONEYORPAY_BILL");
		verifySelectPayfromPayToSendMoney(strPayFromAccount,strPayToAccount,strPayToCurr);
		amountFieldValidation_Inline();
		amountFieldValidation_Mainframe();
	}

	public void verifySelectPayfromPayToSendMoney(String fromAccount,String payToAcc,String payToCurr) throws Exception {
		payFrom_SendMoney(fromAccount);
		selectPayToSendMoney(payToAcc,payToCurr);
	}


	public void selectPayToSendMoney(String strPayToAccount,String payToCurency) throws Exception {
		//verify section header
		try {
			if (isElementDisplayed(getObject("Sendmoney.lblPayTo"), 5)) {
				logMessage("Section header'Pay to' is displayed on Send Money landing Page");
				insertMessageToHtmlReport("Section header'Pay to' is displayed on Send Money landing Page");
			} else {
				logError("Section header'Pay to' is NOT displayed on Send Money landing Page");
			}

			//Select Payee
			if (isElementDisplayed(getObject(devTypeToGetProperty + "SendMoney.lblSelectPayee"), 5)) {
				insertMessageToHtmlReport("Label'Select Payee' is displayed on Send Money landing Page");
				logMessage("Label'Select Payee' is displayed on Send Money landing Page");
			} else {
				if (isElementDisplayed(getObject("xpath~//*[text()='Select payee']"), 5)) {
					logMessage("Label'Select Payee' is displayed on Send Money landing Page");
				} else {
					logError("Label header'Pay to' is NOT displayed on Send Money landing Page");
				}
			}
			while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
				waitForPageLoaded();
			}
			Thread.sleep(3000);
			if (!strPayToAccount.equals("")) {
				if (devTypeToGetProperty.equals("w.")||devTypeToGetProperty.equals("mw.")) {
					if (isElementDisplayed(getObject(devTypeToGetProperty+"SendMoney.lstPayToPayee"), 5)) {
						logMessage("Dropdown of 'Select Payee' is displayed on Send Money landing Page");
						//validating directional text
						if (getText(getObject("SendMoney.lblPayToPayeeDirectionalText")).equalsIgnoreCase("Choose a payee or bill")) {
							logMessage("Directional text 'Please select' is correctly displayed on the Pay from Account dropdown");
						} else {
							logError("Directional text 'Choose a payee or bill' is not as expected for Pay To drop down");
						}
						while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
							waitForPageLoaded();
						}
						Thread.sleep(1000);
						clickJS(getObject(devTypeToGetProperty+"SendMoney.lstPayToPayee"), "Pay To Account Dropdown ");
						while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
							waitForPageLoaded();
						}
						waitForElementToClickable(By.xpath("//button[contains(text(),'Choose a payee or bill')]/following-sibling::ul/li[contains(.,'" + strPayToAccount + "')]"), 20);
						WebElement elm = driver.findElement(By.xpath("//button[contains(text(),'Choose a payee or bill')]/following-sibling::ul/li[contains(.,'" + strPayToAccount + "')]"));
						String curr = elm.getText();
						if (curr.contains(payToCurency)) {
							logMessage("Currency '" + payToCurency+ "' is displayed in drop down for Payee/Bill '");
						} else {
							logError("Currency is NOT displayed/incorrect in dropdown for Payee/Bill ");
						}
						elm.click();
						logMessage("Account/Bill Selected ");
						waitForPageLoaded();
					} else {
						logError("Pay To Payee dropdown is not displayed on Send Money landing Page");
					}
				} else {
					//waitForElementPresent(By.xpath("//span[(text()='" + dataTable.getData("General_Data", "PayTo_Account") + "')]   "), 10);
					clickJS(getObject(devTypeToGetProperty + "SendMoney.lstPayToPayee"), "Pay To Account Dropdown ");
					while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 10)) {
						waitForPageLoaded();
					}
					waitForPageLoaded();waitForJQueryLoad();

					String payeename = strPayToAccount;
					String strxpath = "";
					WebElement elm =null;
					if(payeename.contains("~")){
						try{
							strxpath= "//*[contains(@aria-label,'"+payeename.split("~")[0].trim()+"')]/ancestor::div[@role='button']/" +
									"descendant::*[@class='ecDIB  '][not(@style='display: none;')]/descendant::*[contains(text(),'"+payeename.split("~")[1].trim()+"')]";
							elm = driver.findElement(By.xpath(strxpath));
						}catch(Exception e){
							try{
								strxpath= "//*[contains(@aria-label,'"+payeename.split("~")[0].trim()+"')]/ancestor::div[@role='button']/descendant::*[contains(@class,'ecDIB  ')]/descendant::*[contains(text(),'"+payeename.split("~")[1].trim()+"')]";
								elm = driver.findElement(By.xpath(strxpath));
							}catch(Exception f){}
						}
					}else {
						try {
							waitForPageLoaded();
							waitForPageLoaded();
							//strxpath = "//div[@style='text-align: left; '][contains(@class,'ecDIB ')]/div/span[contains(text(),'" + payeename + "')]";
							strxpath = "(//span[contains(text(),'" + payeename + "')]/ancestor::*[contains(@class,'col-full text-left')])[1]";
							waitForPageLoaded();
							waitForJQueryLoad();
							elm = driver.findElement(By.xpath(strxpath));
						}catch(Exception f){}
					}

					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].scrollIntoView(false);",elm);
					js.executeScript("arguments[0].click();", elm );
					waitForJQueryLoad(); waitForPageLoaded();
					logMessage("Account/Bill Selected ");
					waitForPageLoaded();
				}
			}

			//validating the icons
			//add bill
			if (devTypeToGetProperty.equals("w.")) {
				waitForElementToClickable(getObject("SendMoney.btnAddBill"), 5);
				if (isElementDisplayed(getObject("SendMoney.btnAddBill"), 5)) {
					logMessage("Button 'Add Bill' is displayed on Send Money landing Page");
				} else {
					logError("Button 'Add Bill' is NOT displayed on Send Money landing Page");
				}

				//add person
				if (isElementDisplayed(getObject("SendMoney.btnAddPerson"), 5)) {
					logMessage("Button 'Add payee' is displayed on Send Money landing Page");
				} else {
					logError("Button 'Add payee' is NOT displayed on Send Money landing Page");
				}
			}
		} catch (Exception e) {
			logError("Error in selectPayToSendMoney, Error = "+e.getMessage());
		}
	}


	public void verifyCurrencyandReference(String strCurrSymbol) throws Exception {
		if (isElementDisplayed(getObject("Payments.lblAmount"), 5)) {
			logMessage("Label 'Amount' is displayed on Send Money Page");
			//Validate Currency Symbol
			if (getText(getObject("Payments.lblCurrencySymbol")).equals(strCurrSymbol)) {
				logMessage("Currency Symbol label is correct '" + strCurrSymbol + "' in Amount Field on Send Money Page");
			} else {
				logError("Currency Symbol label is NOT correct '" + strCurrSymbol + "' in Amount Field on Send Money Page, UI label value '");
			}
		}

		if (isElementDisplayed(getObject("AddBillPayee.lblReference"), 5)) {
			logMessage("Reference Label is displayed");
			if (isElementDisplayed(getObject("SendMoney.lblVisibletoPayee"), 5)) {
				logMessage("Visible to Payee Label is displayed");
			} else {
				logError("Visible to Payee label is not displayed");
			}
		} else {
			logError("Reference label is not displayed");
		}
	}

	public void clickgoback() throws Exception {
		Thread.sleep(2000);
		if (isElementDisplayed(getObject("home.Goback"), 20)) {
			clickJS(getObject("home.Goback"), "Go back");
		} else {
			logError("Go Back link is not present on Screen");
		}

	}

	//selectPaymentOption from existing code

	//verifySelectPayfromPayToSendMoney

	public void amountFieldValidation_Inline() throws Exception {
		//String strError = dataTable.getData("General_Data", "ErrorText");
		//String strAmount = dataTable.getData("General_Data", "NewAmount");
		if (isElementDisplayed(getObject("SendMoney.btnContinue"))) {
			clickJS(getObject("SendMoney.btnContinue"), "clicked Continue");
			waitForPageLoaded();
		}
		String expAmountError = "Please enter an amount.";
		String UIAmountError = getText(By.xpath("//span[contains(@class,'boi_input_sm_error boi-error-color boi-error-position boi-fs-m2  ')][@style='visibility: visible; display: inline;']"));
		if (UIAmountError.equalsIgnoreCase(expAmountError)){
			logMessage("Amount Field Validation");
			appendScreenshotToCucumberReport();
		} else {
			logError("Errorr in Amount Field Validation");
		}
		waitForJQueryLoad(); waitForPageLoaded();
	/*	if (strError.contains("::")) {
			String[] expError = strError.split("::");
			String[] amount = strAmount.split("::");
			for (int i = 0; i < expError.length; i++) {
				scrollToView(getObject("SendMoney.lblAmount"));
				sendKeys(getObject("SendMoney.lblAmount"), amount[i]);
				clickJS(getObject("SendMoney.btnContinue"), "Continue");
				String AmountError = getText(By.xpath("//span[contains(@class,'boi_input_sm_error boi-error-color boi-error-position boi-fs-m2  ')][@style='visibility: visible; display: inline;']"));
				if (expError[i].equals(AmountError)) {
					report.updateTestLog("ValidateError", "Amount Field Validation message ", Status_CRAFT.PASS);
				} else {
					report.updateTestLog("ValidateError", "Amount Field Validation message ", Status_CRAFT.FAIL);
				}
			}
		}*/
	}

	public void amountFieldValidation_Mainframe() throws Exception {

		/*String strError = dataTable.getData("General_Data", "VerifyDetails");
		String strAmount = dataTable.getData("General_Data", "DepositAmount");
		if (strError.contains("::")) {
			String[] expError = strError.split("::");
			String[] amount = strAmount.split("::");
			for (int i = 0; i < expError.length; i++) {
				scrollToView(getObject("SendMoney.lblAmount"));
				sendKeys(getObject("SendMoney.lblAmount"), amount[i]);
				clickJS(getObject("SendMoney.btnContinue"), "Continue");
				waitForJQueryLoad();waitForPageLoaded();

				String AmountError = getText(By.xpath("//span[contains(@class,'boi_input')][contains(text(),'" + expError[i] + "')]"));
				if (AmountError.replaceAll("  ", " ").contains(expError[i].replaceAll("  ", " "))) {
					// String str = "The amount that you wish to transfer is greater than your available funds.  Please try again with a lesser amount.".replace('','');
					report.updateTestLog("validateErrors", "Amount enterted is " + amount[i] + "Error message displayed is as expected. Expected:: '" + expError[i] + "', Actual::'" + AmountError + "'", Status_CRAFT.PASS);
				} else {
					report.updateTestLog("validateErrors", "Amount enterted is " + amount[i] + "Error message displayed is not as expected. Expected::'" + expError[i] + "', Actual::'" + AmountError + "'", Status_CRAFT.FAIL);
				}
			}
		}*/
	}

}

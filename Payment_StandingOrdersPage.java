package com.boi.grp.pageobjects.Payments;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.validator.GenericTypeValidator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.pageobjects.Cards.CCfunctionalComponent;
import com.boi.grp.utilities.DataLibrary;
import com.boi.grp.utilities.UIResusableLibrary;

import freemarker.core.ParseException;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class Payment_StandingOrdersPage extends BasePageForAllPlatform {
	public UIResusableLibrary cafFunctional;
	public CCfunctionalComponent ccFunctional;
	public DataLibrary dataLib;
	By setupSOcreateLnk = getObject("pdStandingOrder.linkSetupnewStandingOrder");
	By setupSOcreatetitle = getObject("pdStandingOrder.titleSetupnewstandingOrder");
	By setupSOcreatelblsection = getObject("PDSetupStandingOrder.lblSectionCategory");
	By setupSOcreatelblinfotxt = getObject("PDSetupstandingOrder.lblInformativetext");
	By setupSOcreatelblpayfrom = getObject("PDSetupStaningOrder.lblPayfrom");
	By setupSOcreateselacc = getObject("PDSetupStandingOrder.lstPayfrom");
	By setupSOcreatepayfromopt = getObject("PDSetupStandingOrder.lstItemsPayfrom");
	By confirmButton = getObject("pd.btnConfirmPIN");
	By setupSOerr = getObject("SetupStandingOrder.DuplicateSOError");
	//Amend
    By sltAccViewStandOrder = getObject("pd.sltAccViewStandOrder");
    By selectaccount = getObject("pd.selectAmendStandOrderAccount");
    By selection = getObject("pd.amendSelection");
    By manage = getObject("pd.btnManage");
    By amtToBeAmended = getObject("pd.amtToBeAmended");
    By ctnBtn = getObject("pd.continueBtn");
    By allstatus = getObject("pd.allstatus");
    By amendStatus = getObject("pd.amendStatus");
    By amendName = getObject("pd.amendName");
    By amendAccountDetails = getObject("pd.amendAccountDetails");
    By amendFrequency = getObject("pd.amendFrequency");
    By amendPreviousAmount = getObject("pd.amendPreviousAmount");
    By changeamountbtn = getObject("pd.changeamountbtn");
    By putOnHoldbtn = getObject("pd.putOnHoldbtn");
    By cancelthisbtn = getObject("pd.cancelthisbtn");
    By errormsg = getObject("pd.errormsg");
  	//SO - Validate Allert 
	By selectAccountSO = getObject("pd.btnSelectAccount");
	By selectAccountListName = getObject("pd.selectAccountName");
	By goBackSO = getObject("pd.btnGoBack");
	By setupNewSOLnk = getObject("pd.setupNewSOLnk"); 
	By weeklyFreqSO = getObject("pd.weeklyFreqSO");
	By fortnightlyFreqSO = getObject("pd.fortnightlyFreqSO");
	By monthlyFreqSO = getObject("pd.monthlyFreqSO");
	By yearlyFreqSO = getObject("pd.yearlyFreqSO");
	By inactiveSO = getObject("pd.inactiveSO");
	By cancelSOBtn = getObject("pd.cancelSOBtn");
	By yesCancelSOBtn = getObject("pd.yesCancelSOBtn");	
	By cancelSOSuccessTxt = getObject("pd.cancelSOSuccessTxt");
	By alertMsgInactiveSO = getObject("pd.alertMsgInactiveSO");
	By successMessage = getObject("pd.imgSuccess");


	public Payment_StandingOrdersPage(WebDriver driver) {
		super(driver);
		cafFunctional = new UIResusableLibrary(driver);
		ccFunctional = new CCfunctionalComponent(driver);
		dataLib = new DataLibrary();
		PageFactory.initElements(driver, this);
	}

	/*---------------------------------Start <PD_StandingOrder>----------------------------------------
	 Function Name: PD_StandingOrder
	 Argument :ordertype and usertype
	 Purpose: Navigate to standingOrder page ,validate the screen and clicks on setup a new standing order for create flow and follows setup SO page with form,
	 review page, pin page, accept notification to success page .
	 Author Name: CAF Automation 
	 Create Date: 13-06-2021
	 Modified Date| Modified By  |Modified purpose 
	  28/07/2021      C113331 ,C112253    Code update
	 -----------------------------------End <PD_StandingOrder>--------------------------------------- */
	public void PD_StandingOrder(String ordertype, String country,String accounttype, String userType) {
		try {
			logMessage("SELECT STANDING ORDER OPTION AND NAVIGATE TO PAGE : START");
			logMessage("ORDER TYPE : " + ordertype);
			ValidateStandingOrderScreen();
			switch (ordertype.toUpperCase()) {
			case "CREATE":
				try {
					// code to click standing order button
					logMessage("Click Set up new Standing Order button : START");
					clickJS(setupSOcreateLnk);

					// code to validate title & Label screen
					ValidateSetupStandingOrderScreen();

					// code to select account
					selectPayFrom(country);

					// code to enter all payee details and review the entered
					// details in review page
					EnterPayeeDetails(country,userType);

					// Enter pin to approve
					String pin = cafFunctional.GetvalidPIN(userType);
					ccFunctional.Enter3of6DigitPIN("VALIDPIN", pin);

					clickJS(confirmButton);
					// verify confirmation page
					SetupSOConfirmationPage();

					appendScreenshotToCucumberReport();
					insertMessageToHtmlReport("Verify standing order flow success");
					break;
				} catch (Exception e) {
					logError("Error Occured inside standing order - CREATE flow " + e.getMessage());
					insertMessageToHtmlReport("Failure in Verify Standing order - CREATE flow" + e.getMessage());
					appendScreenshotToCucumberReport();
				}
			case "AMEND":
				AmendStandingOrder(accounttype);
				break;
			}

		} catch (Exception e) {
			logError("Error Occured inside standing order  flow " + e.getMessage());
			insertMessageToHtmlReport("Failure in Verify Standing order" + e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

	/*---------------------------------Start <EnterPayeeDetails>----------------------------------------
	 Function Name: EnterPayeeDetails
	 Argument :usertype
	 Purpose: Enter payee details for all usertypes ( Payeename, IBAN/NSC & Account number if usertype is other than GB ,Payee message, Payfrom dropdown ,date 
	 and amount )in Set up a new standing order page and Review page validation (details entered in payee form is displayed or not)
	 
	 Author Name: CAF Automation 
	 Create Date: 13-06-2021
	 Modified Date| Modified By  |Modified purpose 
	  24/06/2021      C113331     Code update 
	 -----------------------------------End <EnterPayeeDetails>--------------------------------------- */
	private void EnterPayeeDetails(String country,String userType) throws InterruptedException {
		boolean ispayeedetailentered = false;
		try {
			logMessage("ENTER PAYEE DETAILS");
			logMessage("USER JURISDICTION TYPE : " + userType);

			boolean isIbanavailable = false;
			String strPayeeName = generateRandomString();
			String strPayeeMsg = generateRandomString() + "Message";
			String strIBAN_BIC = dataLib.getIBAN_BIC(country);
			String[] arrIBANData = strIBAN_BIC.split("_");
			String strPayeeIBAN = arrIBANData[0];
			String strNSC = dataLib.getNSC(strPayeeIBAN, country);
			String strAccno = dataLib.getAccNo(strPayeeIBAN, country);
			String strAmount = generateRandomNumber(2);
			String strDate = cafFunctional.getDate(1);
			String freqname = null;

			setValue(getObject("SetupStandingOrder.txtName"), strPayeeName);

			if (isElementDisplayed(getObject("SetupStandingOrder.txtIBAN"))) {
				setValue(getObject("SetupStandingOrder.txtIBAN"), dataLib.getIBAN_BIC(userType));
				isIbanavailable = true;
			} else if (isElementDisplayed(getObject("StandingOrder.lblAccountNumber"))) {
				setValue(getObject("StandingOrder.lblAccountNumber"), dataLib.getAccNo(strPayeeIBAN, userType));
				ispayeedetailentered = true;
				if (isElementDisplayed(getObject("StandingOrder.lblSortCode"))) {
					setValue(getObject("StandingOrder.lblSortCode"), dataLib.getNSC(strPayeeIBAN, userType));
					ispayeedetailentered = true;
				}
			} else {
				ispayeedetailentered = false;
			}
			setValue(getObject("SetupStandingOrder.txtMessageToPayee"), strPayeeMsg);
			freqname = cafFunctional.selectRandomValuefromDropdown(getObject("PDStandingOrder.frequecy"));
			setValue(getObject("SetupStandingOrder.txtStartDate"), strDate);
			setValue(getObject("ManageStandingOrder.txtNewAmount"), strAmount);
			clickJS(getObject("ManageStandingOrder.btnContinue"));
			if (isElementDisplayed(setupSOerr)) {
				ispayeedetailentered = false;
				Assert.assertFalse("Enter Payee details is not successful", ispayeedetailentered);
			}
			SetupSOReviewPage(strPayeeName, isIbanavailable, strPayeeIBAN, strAccno, strPayeeMsg, strNSC, freqname,
					strDate, strAmount);

		} catch (Exception e) {
			if (ispayeedetailentered == false) {
				Assert.assertFalse("Enter Payee details is not successful", ispayeedetailentered);
				Allure.step("Enter Payee details is not successful", Status.FAILED);
				appendScreenshotToCucumberReport();

			}
			logError("Error Occured in Payee entry " + e.getMessage());
			appendScreenshotToCucumberReport();

		}
	}

	/*---------------------------------Start <selectPayFrom>----------------------------------------
	 Function Name: selectPayFrom
	 Argument :usertype
	 Purpose: code to select current account based on jurisdiction in Set up a new standing order page 	 
	 Author Name: CAF Automation 
	 Create Date: 13-06-2021
	 Modified Date| Modified By  |Modified purpose 
	  24/06/2021      C113331     Code update 
	 -----------------------------------End <selectPayFrom>--------------------------------------- */

	private void selectPayFrom(String userType) throws InterruptedException {
		try {
			logMessage("SELECT PD FROM ACCOUNT : START");
			logMessage("USER JURISDICTION TYPE : " + userType);

			// Code to click Pay from account
			isElementDisplayed(setupSOcreatelblpayfrom);
			clickJS(setupSOcreateselacc);

			// Code to select Pay from account from list of accounts
			selectValueFromDropDown(setupSOcreatepayfromopt, "CURRENT ACCOUNT " + userType);

		} catch (Exception e) {
			Allure.step("Pay from Account dropdown click is not successful", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	/*---------------------------------Start <SetupSOReviewPage>----------------------------------------
	 Function Name: SetupSOReviewPage
	 Argument :strPayeeName,ibanVal,strPayeeIBAN,strAccountNumber,strPayeeMsg,strSortCode,freqname,strDate,strAmount
	 Purpose: code to validate data in Set up a new standing order review page 	 
	 Author Name: CAF Automation 
	 Create Date: 13-06-2021
	 Modified Date| Modified By  |Modified purpose 
	  24/06/2021      C113331     Code update 
	 -----------------------------------End <SetupSOReviewPage>--------------------------------------- */
	private void SetupSOReviewPage(String strPayeeName, boolean ibanVal, String strPayeeIBAN, String strAccountNumber,
			String strPayeeMsg, String strSortCode, String freqname, String strDate, String strAmount)
			throws Exception {
		// validating page title 'Set up Standing Order'
		try {

			if (isElementDisplayed(getObject("ManageStandingOrder.hdrReview"))) {
				Assert.assertTrue(isElementDisplayed(getObject("ManageStandingOrder.hdrReview")));
				logInfo("SetupSOReviewPage - Review title is displayed on review page ");
			}
			if (ibanVal) {
				strPayeeIBAN = strPayeeIBAN;
				if (isElementDisplayed(By.xpath("//*[text()='" + strPayeeIBAN + "']"), 5)) {
					logInfo("SetupSOReviewPage - " + strPayeeIBAN + "' is displayed on review page ");
				} else {
					logInfo("SetupSOReviewPage - " + strPayeeIBAN + "' is displayed on review page ");
				}

			}
			String[] reviewtext = { strPayeeName, strAccountNumber, strPayeeMsg, strSortCode, freqname, strDate,
					strAmount };
			for (int i = 0; i < reviewtext.length; i++) {
				if (isElementDisplayed(By.xpath("//*[text()='" + reviewtext[i] + "']"), 5)) {
					logInfo("SetupSOReviewPage - " + reviewtext + "' is displayed on review page ");
				} else {
					logInfo("SetupSOReviewPage - " + reviewtext + "' is displayed on review page ");
				}

			}
			// Validate Go Back Button on Review Page
			if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
				isElementDisplayed(getObject(devTypeToGetProperty + "SetupStandingOrder.btnGoBack"));
			}
			// Validate Continue Button on Review Page

			if (isElementDisplayed(getObject("SetupStandingOrder.btnContinue"))) {
				clickJS(getObject("SetupStandingOrder.btnContinue"));
			}
		} catch (Exception e) {

			logError("Error Occured in Review page " + e.getMessage());
		}

	}

	
			
	/*---------------------------------Start <ValidateSetupStandingOrderScreen>----------------------------------------
	 Function Name: ValidateSetupStandingOrderScreen
	 Argument : NA
	 Purpose: code to validate setup standing screen and your account label in setup a new standing Order page	 
	 Author Name: CAF Automation 
	 Create Date: 13-06-2021
	 Modified Date| Modified By  |Modified purpose 
	  24/06/2021      C113331     Code update 
	 -----------------------------------End <ValidateSetupStandingOrderScreen>--------------------------------------- */
	private void ValidateSetupStandingOrderScreen() {
		try {

			// Code to verify title on standing order page
			logInfo("****TITLE*****" + driver.getTitle());
			String ActualTextHeader = getText(setupSOcreatetitle);
			Assert.assertEquals("Set Up Standing Order", ActualTextHeader);
			injectMessageToCucumberReport("Set up a new Standing Order page is displayed successfully");
			appendScreenshotToCucumberReport();

			// validating section category
			isElementDisplayed(setupSOcreatelblsection);
			String ActualTextSectionText = getText(setupSOcreatelblsection);
			logInfo("*****Set up standing order page -your account section category" + ActualTextSectionText);
			Assert.assertEquals("Your Account", ActualTextSectionText);

			// // validating informative text
			// isElementDisplayed(setupSOcreatelblinfotxt);
			// String ActualTextInfoText = getText(setupSOcreatelblinfotxt);
			// logInfo("*****Set up standing order page - Info text" +
			// ActualTextInfoText);
			// Assert.assertEquals("You can set up a standing order online from
			// your current account to another account in the same
			// country.",ActualTextHeader);
			//
			// // validating dropdown label
			// isElementDisplayed(getObject("SetupStaningOrder.lblPayfrom"));
			// String ActualDropdownText =
			// getText(getObject("SetupStaningOrder.lblPayfrom"));
			// logInfo("*****Set up standing order page -Pay from Dropdown val"
			// + ActualDropdownText);
			// Assert.assertEquals("Dropdown label 'Pay from'",
			// ActualDropdownText);
			//
		} catch (Exception e) {
			Allure.step("Validate Standing order- Title + Label validation is not successful", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	/*---------------------------------Start <ValidateStandingOrderScreen>----------------------------------------
	 Function Name: ValidateSetupStandingOrderScreen
	 Argument : NA
	 Purpose: code to validate title of standing order page in  standing Order page	 
	 Author Name: CAF Automation 
	 Create Date: 13-06-2021
	 Modified Date| Modified By  |Modified purpose 
	  24/06/2021      C113331     Code update 
	 -----------------------------------End <ValidateStandingOrderScreen>--------------------------------------- */
	private void ValidateStandingOrderScreen() throws InterruptedException {
		String ActualTextHeader = null;
		boolean verifytitle = false;
		try {
			logInfo("****TITLE*****" + driver.getTitle());
			if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
				ActualTextHeader = getText(getObject(devTypeToGetProperty + "StandingOrderDetails.hdrPageTitle"));
				verifytitle = true;
			} else if (devTypeToGetProperty.equalsIgnoreCase("mw.")) {
				ActualTextHeader = getText(getObject(devTypeToGetProperty + "StandingOrderDetails.hdrPageTitle"));
				verifytitle = true;
			}
			logInfo("*****Header" + ActualTextHeader);
			if (verifytitle) {
				Assert.assertEquals("Standing Orders", ActualTextHeader);
				insertMessageToHtmlReport(
						"Standing Order clicked for " + devTypeToGetProperty + "  is clicked Successfully ");
				appendScreenshotToCucumberReport();
			}
		} catch (Exception e) {
			logInfo("*****Header is not matched******");
			Assert.assertFalse("Header is not matched", verifytitle);
			insertMessageToHtmlReport("Standing Order is not clicked Successfully ");
			appendScreenshotToCucumberReport();

		}
	}

	
	/* C113331 code to validate Standing order confirmation page */

	/*---------------------------------Start <SetupSOConfirmationPage>----------------------------------------
	 Function Name: SetupSOConfirmationPage
	 Argument : NA
	 Purpose: code to validate success/confirmation page in setup a new standing order screen
	 Author Name: CAF Automation 
	 Create Date: 13-06-2021
	 Modified Date| Modified By  |Modified purpose 
	  24/06/2021      C113331     Code update 
	  -----------------------------------End <SetupSOConfirmationPage>--------------------------------------- */
	public void SetupSOConfirmationPage() throws Exception {
		try {
			// validating page title 'Set up Standing Order'
			isElementDisplayed(getObject(devTypeToGetProperty + "SetupStandingOrder.hdrPageTitle"));
			Assert.assertTrue(isElementDisplayed(getObject("SetupStandingOrder.lblPleaseActivate"), 15));
			isElementDisplayed(getObject(devTypeToGetProperty + "StandingOrder.btnBacktoHome"));

			if (isElementDisplayed(getObject("StandingOrder.lblTimeOfRequest"))) {
				String strRequestSubmitted = getText(getObject("StandingOrder.lblTimeOfRequest"));
				String uiDateText = strRequestSubmitted.split(": ")[1];
				DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
				df.parse(uiDateText);
				insertMessageToHtmlReport("StandingOrder.lblTimeOfRequest - Text Label '" + strRequestSubmitted
						+ "' has correct date format DD/MM/YYYY HH:MM");
				appendScreenshotToCucumberReport();
			}

		} catch (Exception e) {
			logError("Error Occured inside successpage " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in success page " + e.getMessage());
			appendScreenshotToCucumberReport();

		}
	}
	/*---------------------------------Start <AmendStandingOrder>----------------------------------------
	 Function Name: AmendStandingOrder
	 Argument :accounttype
	 Purpose: Function for Amending Standing Order
	 Author Name: CAF Automation 
	 Create Date: 28/07/2021 
	 Created By: C112253
	 -----------------------------------End <AmendStandingOrder>--------------------------------------- */
    public void AmendStandingOrder(String accounttype) throws Exception {
          try {
              cafFunctional.clickSelectValueFromDropDown(sltAccViewStandOrder, selectaccount, accounttype);
         ArrayList<Integer> order = new ArrayList<>();
                 List<WebElement> status = driver.findElements(allstatus);
                 appendScreenshotToCucumberReport();
                 
                 // To fetch first Active standing order
                 if (!status.isEmpty()) {
                       validateTransactionOnAmendPage();
                       int i = 0;
                       for (WebElement element : status) {
                              i++;
                              String pay = element.getText();
                              if (pay.equalsIgnoreCase("Active")) {
                                    order.add(i);
                                    break;
                              }

                       }
                       By selection = getObject("pd.amendSelection", order.get(0));
                       clickJS(selection, "Selecting record to be amended");
                       isElementDisplayedWithLog(manage, 10);
                       clickJS(manage, "Click on Manage Button");
                       validateButtonsOnAmendPage();
                       validateErrMessages();
                       clickJS(amtToBeAmended, "Amount to be amended");
                       setValue(amtToBeAmended, dataLib.generateRandomNumber(1));
                     clickJS(ctnBtn, "Click on Continue Button");
                     //TODO Code for Success scenario 
                 } else {
                       logError("No Standing Order Exist");
                        injectWarningMessageToCucumberReport("No Standing Order Exist");
                       appendScreenshotToCucumberReport();
                 }

          } catch (Exception e) {
                 logError("Error Occured inside successpage " + e.getMessage());
                 injectWarningMessageToCucumberReport("Failure in success page " + e.getMessage());
                 appendScreenshotToCucumberReport();

          }
    }
    
    private void validateTransactionOnAmendPage() throws Exception{
          String status = getText(amendStatus);
          Assert.assertEquals("Checking Status field", status,"Status");
          String name = getText(amendName);
          Assert.assertEquals("Checking Name field", name,"Name");
          String accountdetails = getText(amendAccountDetails);
          Assert.assertEquals("Checking AccountDetails field", accountdetails,"Account details");
          String frequency = getText(amendFrequency);
          Assert.assertEquals("Checking Frequency field", frequency,"Frequency");
          String previousamount = getText(amendPreviousAmount);
          Assert.assertEquals("Checking Previous Amount field", previousamount,"Previous amount");
    }
    
    private void validateButtonsOnAmendPage() throws Exception{
          String changeamount = getText(changeamountbtn);
          Assert.assertEquals("Checking Change amount Button", changeamount,"Change amount");
          String onhold = getText(putOnHoldbtn);
          Assert.assertEquals("Checking PutOnHoldbtn Button", onhold,"Put on hold");
          String cancel = getText(cancelthisbtn);
          Assert.assertEquals("Checking Cancel Button", cancel,"Cancel this");
    }
    
    private void validateErrMessages() throws Exception{
          clickJS(ctnBtn, "Click on Continue Button");
          String error = getText(errormsg);
          String errInvalidMessage = getMessageText("expErrMessageInvalidAmount", "UXPBANKING365");
          Assert.assertEquals("Validate Invalid Value", error,errInvalidMessage);
          clickJS(amtToBeAmended, "Amount to be amended");
          setValue(amtToBeAmended, dataLib.generateRandomNumber(5));
          clickJS(ctnBtn, "Click on Continue Button");
          appendScreenshotToCucumberReport();
          String err = getText(errormsg);
          String errExceedMessage = getMessageText("expErrMessageExceedAmount", "UXPBANKING365");
          Assert.assertEquals("Validate Invalid Value", err,errExceedMessage);
    }
  
	/*---------------------------------Start <PD_SO_ValidateAlert_P>----------------------------------------
	 Function Name: PD_SO_ValidateAlert_P
	 Argument : NA
	 Purpose: Payments_AmendStandingOrder_SOAlertmessageValidation_AllCustomers
	 Author Name: CAF Automation 
	 Create Date: 05-08-2021
	 Modified Date| Modified By  |Modified purpose 
	  05/08/2021      C114322     Code update 
	  -----------------------------------End <PD_SO_ValidateAlert_P>--------------------------------------- */
	public void PD_SO_ValidateAlert_P(String userType,String accountNumber){
		try{
			waitForElementToBeDisplayed(selectAccountSO);
			cafFunctional.clickSelectValueFromDropDown(selectAccountSO, selectAccountListName, accountNumber);
			waitForElementToBeDisplayed(setupNewSOLnk);
			clickJS(setupNewSOLnk,"Click on Setup New SO");
			appendScreenshotToCucumberReport();
			validateFrequencyDetailsOnSO(accountNumber);
			cafFunctional.clickSelectValueFromDropDown(selectAccountSO, selectAccountListName, accountNumber);
			waitForElementToBeDisplayed(inactiveSO);
			if(isElementDisplayed(inactiveSO)){
				clickJS(inactiveSO);
				validateAlertMSgForInactiveSO();
				clickJS(cancelSOBtn,"Click on Cancel Standing Order button");
				appendScreenshotToCucumberReport();
				clickJS(yesCancelSOBtn,"Click on Yes,Cancel standing order");
				isElementDisplayedWithLog(successMessage,5);
				isElementDisplayedWithLog(cancelSOSuccessTxt,5);
			}else{
				logMessage("Inactive records are not avalable for the user:" +userType);
				logError("Inactive records are not avalable for the user:" +userType);
				injectWarningMessageToCucumberReport("Inactive records are not avalable for the user:" +userType);
				appendScreenshotToCucumberReport();
			}
		}catch(Exception e){
			logError("Standing order alert validation is not successfull " + e.getMessage());
			injectWarningMessageToCucumberReport("Standing order alert validation is not successfull " + e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}
	
	//Methods for validation of frequency details on set up new standing order page
	private void validateFrequencyDetailsOnSO(String accountNumber){
		try{
			isElementDisplayedWithLog(selectAccountSO,10);
			cafFunctional.clickSelectValueFromDropDown(selectAccountSO, selectAccountListName, accountNumber);
			Assert.assertTrue(isElementDisplayedWithLog(weeklyFreqSO,5));
			Assert.assertTrue(isElementDisplayedWithLog(fortnightlyFreqSO,5));
			Assert.assertTrue(isElementDisplayedWithLog(monthlyFreqSO,5));
			Assert.assertTrue(isElementDisplayedWithLog(yearlyFreqSO,5));
			appendScreenshotToCucumberReport();
			clickJS(goBackSO,"Click on Go Back");
		}catch(Exception e){
			logError("Frequency Details validation is not successfull " + e.getMessage());
			injectWarningMessageToCucumberReport("Frequency Details validation is not successfull " + e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

	//Method for Alert message validation for inactive standing order 
	private void validateAlertMSgForInactiveSO(){
		try{
			String expectedAlertMsg = getMessageText("expAlertMsgForInactiveSO", "UXPBANKING365");
			String actualAlertMsg = getText(alertMsgInactiveSO);
			System.out.println("AlertMessage: "+actualAlertMsg);
			Assert.assertEquals("Alert message is not displayed for Inactive SO",expectedAlertMsg ,actualAlertMsg);
			appendScreenshotToCucumberReport();
		}catch(Exception e){
			logError("Alert message validation is not successfull " + e.getMessage());
			injectWarningMessageToCucumberReport("Alert message validation is not successfull " + e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

}

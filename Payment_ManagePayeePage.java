package com.boi.grp.pageobjects.Payments;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import com.boi.grp.pageobjects.login.LoginPageNew;
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

public class Payment_ManagePayeePage extends BasePageForAllPlatform {

	public UIResusableLibrary cafFunctional;
	public DataLibrary dataLibrary;
	public Payment_ManagePaymentPage managePaymentpg;
	public Payment_SendMoneyPage sendmoneypg;
	By addBillBtn = getObject("pd.btnAddBill");
	By addBillTxt = getObject("pd.txtAddBill");
	By billNameFieldDropdown = getObject("pd.dropdownBillName");
	By billNameSelect = getObject("pd.selectBillName");
	By refNumText = getObject("pd.txtreferenceNumber");
	By continueButton = getObject("pd.btnContinue");
	By confirmButton = getObject("pd.btnConfirmPIN");
	By successMessage = getObject("pd.imgSuccess");
	By billNameOnReviewPg = getObject("pd.txtBillNameReview");
	By refNumOnReviewPg = getObject("pd.txtRefNumReview");
	By descriptionOnReviewPg = getObject("pd.txtDescriptionReview");
	By accountText = getObject("pd.txtAccount");
	By selectAccountDropdown = getObject("pd.btnSelectAccount");
	By selectAccountName = getObject("pd.selectAccountName");
	By dailyLimitText = getObject("pd.txtDailyLimit");
	By moreAboutDailyLimitLink = getObject("pd.moreAboutDailyLimit");
	By backToHome = getObject("pd.btnBackToHome");
	By goBackBtn = getObject("pd.btnGoBack");
	By payeeNames = getObject("pd.payeeNames");
	By payButtons = getObject("pd.payButtons");
	By limitsText = getObject("pd.limitText");
	By deleteButtons = getObject("pd.deleteButtons");
	By deletePayeeButtons = getObject("pd.deletePayeeButtons");
	By deletePayeeSucessMsg = getObject("pd.deletePayeeSucess");
	// Add Bill Mortgage
	By billNameLabel = getObject("pd.billNameLbl");
	By referenceLabel = getObject("pd.billRefLbl");
	By addItHereLink = getObject("pd.addItHereLnk");
	By addPayeePgTextLbl = getObject("pd.billAddPayeePgTxtLbl");
	By errMsgDuplicateRecordBill = getObject("pd.errDuplicateBillMsg");
	// ADD PAYEE
	
	By addPayeeTxt = getObject("pd.txtAddPayee");
	By countryDropdown = getObject("pd.dropdownCountry");
	
	By currencyDropdown = getObject("pd.dropdownCurrency");
	By payeeNameText = getObject("pd.txtName");
	By payeeIBANText = getObject("pd.txtIBAN");
	By payeeBICText = getObject("pd.txtBIC");
	By payeeRefText = getObject("pd.txtReference");
	By payeeABAText = getObject("pd.txtABACode");
	By payeeNSCText = getObject("pd.txtNSC");
	By payeeAccNoText = getObject("pd.txtAccNumber");
	By payeeDescriptionText = getObject("pd.txtDescription");
	By payeeAddres1Text = getObject("pd.txtPayeeAddress1");
	By payeeAddres2Text = getObject("pd.txtPayeeAddress2");
	By payeeCountryofresidence = getObject("pd.txtCountryofresidence");
	By bankAddress1Text = getObject("pd.txtPayeeBankAddress1");
	By bankAddress2Text = getObject("pd.txtPayeeBankAddress2");
	By bankCityTxt = getObject("pd.txtBankCity");
	By goBackButton = getObject("pd.btnGoBack");
	By payeeContinueButton = getObject("pd.btnContinue");
	By payeeConfirmButton = getObject("pd.btnConfirm");
	By payeeSuccessMessage = getObject("pd.imgSuccess");
	By payeeNameOnReviewPg = getObject("pd.txtNameReview");
	By referenceOnReviewPg = getObject("pd.txtRefNumReview");
	By IBANOnReviewPg = getObject("pd.txtIBANReview");
	By BICOnReviewPg = getObject("pd.txtBICReview");
	By payeelabelCountrycurr = getObject("pd.labelCountrycurr");
	By payeelabelCountry = getObject("pd.labelCountry");
	By payeelabellocation = getObject("pd.labellocation");
	By payeelabelselCountry = getObject("pd.labelselCountry");
	By payeelabelcurr = getObject("pd.labelcurr");
	By payeelabelcurrtxt = getObject("pd.labelcurrtxt");
	By payeelabelvisittxt = getObject("pd.labelvistxt");
	By payeelabeldesc = getObject("pd.labeldesc");
	By btnReviewSendMoney = getObject("pd.btnReviewSendMoney");
	By payeeIBANCalclink = getObject("pd.linkBICIBANCalc");
	By payeeAuthFailErr = getObject("pd.autherr");
	By payeeAuthFailTileImg = getObject("pd.alerttile");
	By payeebtnTryagain = getObject("pd.btntryagain");
	By loginattemptErrMsg = getObject("pd.loginattemptalert");
	// currency converter
	By sendmoneyselAcc = getObject("SendMoney.lstSelectAccount");
	By sendmoneyselAllAcc = getObject("SendMoney.lstAllaccoptions");
	By sendmoneyselPay = getObject("SendMoney.lstSelectPayee");
	By sendmoneyselAllPay = getObject("SendMoney.lstAllpayeeoptions");
	By sendmoneytxtEnterAmtEUR = getObject("pd.txtEnterAmtEUR");
	By sendmoneyCurrencyConvertor = getObject("pd.CurrencyConvertor");
	By sendmoneybtnConvertamnt = getObject("pd.btnConvertamnt");
	By sendmoneybtnConverterrmsg = getObject("pd.CurConvertorerrmsg");
	By sendmoneyCurConvertoramounterrmsg = getObject("pd.CurConvertoramounterrmsg");
	By sendmoneyCurConvertorcurrerrmsg = getObject("pd.CurConvertorcurrerrmsg");

	public Payment_ManagePayeePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional = new UIResusableLibrary(driver);
		dataLibrary = new DataLibrary();
	}

	/*---------------------------------Start <PD_ManagePayee>----------------------------------------
	Function Name: PD_ManagePayee
	Argument Name:
	Purpose: Navigate to manage payee page.
	Author Name: CAF Automation 
	 Create Date: 13-05-2021
	Modified Date| Modified By  |Modified purpose 
	  17/05/2021      C114322     Code update
	-----------------------------------End <PD_ManagePayee>---------------------------------------
	*/

	public void PD_ManagePayee(String managePayeeOption) throws Exception {
		try {
			boolean bFlagMPayOpt = false;
			logMessage("MANAGE PAYEE OPTION TYPE : " + managePayeeOption);
			switch (managePayeeOption.toUpperCase()) {
			// click on add payee option
			case "ADD_PAYEE":
				if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
					isElementDisplayed(getObject(devTypeToGetProperty + "pd.btnAddPayee"));
					clickJS(getObject(devTypeToGetProperty + "pd.btnAddPayee"));
				} else if (devTypeToGetProperty.equalsIgnoreCase("mw.")) {
					isElementDisplayed(getObject(devTypeToGetProperty + "pd.btnAddPayee"));
					clickJS(getObject(devTypeToGetProperty + "pd.btnAddPayee"));
				} else {
					bFlagMPayOpt = false;
				}
				break;
			// click on add bill option
			case "ADD_BILL":
				if (isElementDisplayed(addBillBtn)) {
					clickJS(addBillBtn, "Add Bill");
					bFlagMPayOpt = true;
				} else {
					bFlagMPayOpt = false;
				}
				break;
			case "ADD_BILL_MORTGAGE":
				if (isElementDisplayed(addBillBtn)) {
					clickJS(addBillBtn, "Add Bill");
					bFlagMPayOpt = true;
				} else {
					bFlagMPayOpt = false;
				}
				break;
			}
			logMessage(managePayeeOption + "Manage payee option selected successfully");
			appendScreenshotToCucumberReport();
		} catch (Exception e) {
			logError("Error occured inside manage payee options type" + e.getMessage());
			injectWarningMessageToCucumberReport(
					"Failure in manage payee selection and navigation to page" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in manage payee selection and navigation to page" + managePayeeOption, Status.FAILED);
		}
	}

	/*---------------------------------Start <PD_AddBillPay>----------------------------------------
	Function Name: PD_AddBillPay_P
	Argument Name:
	Purpose: UB-324_Payments_ManagePayee_AddBillDailyLimitsValidation_AllCustomers
	Author Name: CAF Automation 
	 Create Date: 13-05-2021
	Modified Date| Modified By  |Modified purpose 
	  17/05/2021      C114322     Code update
	-----------------------------------End <PD_AddBillPay>---------------------------------------
	*/

	public void PD_AddBillPay_P(String userType, String account, String billNameValue, String billRefNum) {
		try {
			validateCustomerIsOnAddBillPage();
			// Select account from drop down - method
			if (isElementDisplayed(accountText, 10)) {
				cafFunctional.clickSelectValueFromDropDown(selectAccountDropdown, selectAccountName, account);
				appendScreenshotToCucumberReport();
				clickJS(continueButton, "Continue");
			}
			isElementDisplayedWithLog(billNameFieldDropdown, 5);
			// TODO - Re-check with React code - C114322
			cafFunctional.clickSelectValueFromDropDown(billNameFieldDropdown, billNameSelect, billNameValue); // Click
																												// and
																												// Select
																												// value
																												// from
																												// dropdown
			String billName = getText(billNameFieldDropdown);
			setValue(refNumText, billRefNum); // Set value for reference number
												// field
			clickJS(continueButton, "Continue");
			reviewBillDetailsOnReviewPage(billName, billRefNum); // Review bill
																	// details
																	// on Review
																	// Page
			clickJS(continueButton, "Continue");
			cafFunctional.EnterPINdetails(cafFunctional.GetvalidPIN(userType));
			clickJS(confirmButton, "Confirm");
			isElementDisplayedWithLog(successMessage, 5);
			verifyBillCreatedAndDelete(billRefNum);
			injectMessageToCucumberReport("Add Bill pay is added successfully");
			Assert.assertTrue("Add Bill pay is added successfully", true);
		} catch (Exception e) {
			logError("Add Bill pay is not added successfully - " + e.getMessage());
			injectWarningMessageToCucumberReport("Add Bill pay is not added successfully" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Add Bill pay is not added successfully", Status.FAILED);
			Assert.fail("Add Bill pay is not added successfully" + e.getMessage());
		}
	}

	// Check if the customer is navigated to add bill page successfully
	private void validateCustomerIsOnAddBillPage() throws Exception {
		String actualMessage = getText(addBillTxt);
		Assert.assertEquals("Customer is not on Add Bill page", actualMessage, "Add Bill");
	}

	// Review bill details if it matchs with the values enter for bill
	private void reviewBillDetailsOnReviewPage(String billName, String refnum) throws Exception {
		String actualBillName = getText(billNameOnReviewPg);
		String actualRefNum = getText(refNumOnReviewPg);
		String actualDailyLimitText = getVisibleDailyLimitElementText();
		String expectedDailyLimitText = getMessageText("expDailyLimitText", "UXPBANKING365");
		isElementDisplayedWithLog(billNameOnReviewPg, 5);
		Assert.assertEquals("Bill Name is not as expected on Review page", billName, actualBillName); // Validate
																										// Bill
																										// Name
																										// Field
		Assert.assertEquals("Reference number is not as expected on Review page", refnum, actualRefNum); // Validate
																											// Reference
																											// Number
																											// Field
		appendScreenshotToCucumberReport();
		if (actualDailyLimitText.contains(expectedDailyLimitText)) { // Validate
																		// daily
																		// limit
																		// per
																		// day
			Assert.assertTrue("Daily Limit is available", true);
		} else {
			Assert.fail("Daily Limit is not available");
		}
		waitForElementToClickable(moreAboutDailyLimitLink, 10);
		clickVisibleDailyLimitElement();
		validateMoreAboutDailyLimitLink();
	}

	// TODO - Replace selenium methods with new methods available in new
	// framework
	// To get daily limit text from the review page
	public String getVisibleDailyLimitElementText() {
		try {
			List<WebElement> dailyLimitTexts = driver.findElements(dailyLimitText);
			for (WebElement dailyLimit : dailyLimitTexts) {
				if (dailyLimit.isDisplayed()) {
					return dailyLimit.getText();
				}
			}
		} catch (Exception e) {
			logError("Daily limit text is not fetched successfully  - " + e.getMessage());
		}
		return null;
	}

	// TODO - Replace selenium methods with new methods available in new
	// framework
	// Click on more about daily limit link on the review page - Navigate to new
	// tab
	public void clickVisibleDailyLimitElement() {
		try {
			List<WebElement> moreAboutDailyLimitLinks = driver.findElements(moreAboutDailyLimitLink);
			for (WebElement dailyLimitLink : moreAboutDailyLimitLinks) {
				if (dailyLimitLink.isDisplayed()) {
					dailyLimitLink.click();
				}
			}
		} catch (Exception e) {
			logError("More about daily limit link is not clicked successfully  - " + e.getMessage());
		}
	}

	// TODO - Check getWindowHandles to be added in the BasePageForAllPlatform
	// Validate if the customer is able to navigate to more about daily limit
	// page
	public void validateMoreAboutDailyLimitLink() {
		String moreAboutDailyLimitUrl = getMessageText("urlMoreAboutDailyLimit", "UXPBANKING365");
		Set<String> allWindowHandles = driver.getWindowHandles();
		String parentWindowHandle = driver.getWindowHandle();
		Iterator<String> iteratorForWindow = allWindowHandles.iterator();
		while (iteratorForWindow.hasNext()) {
			String childWindowHandle = iteratorForWindow.next();
			if (!parentWindowHandle.equals(childWindowHandle)) {
				driver.switchTo().window(childWindowHandle);
				String actualUrl = getPageURL();
				appendScreenshotToCucumberReport();
				Assert.assertTrue("More About Daily Limit Link is not navigated successfully",
						actualUrl.startsWith(moreAboutDailyLimitUrl));
			}
		}
		driver.switchTo().window(parentWindowHandle);
	}

	// TODO - Replace selenium methods with new methods available in new
	// framework
	// Verify the Bill Created on Payee list and Delete the payee created
	public void verifyBillCreatedAndDelete(String billRefNum) {
		try {
			String last4RefNum = billRefNum.substring(billRefNum.length() - 4);
			String expDelSeccessMessage = getMessageText("expDeletePayeeSuccessMsg", "UXPBANKING365");
			if (isElementDisplayedWithLog(backToHome, 10)) {
				clickJS(backToHome, "Back to Home");
				clickJS(getObject(devTypeToGetProperty + "home.tabPayments"), "Payments Tab");
				isElementDisplayedWithLog(getObject("pd.managepayeetab"), 5);
				clickJS(getObject("pd.managepayeetab"), "Manage Payee Option");
			}
			List<WebElement> payeeNamesList = driver.findElements(payeeNames);
			List<WebElement> limitsList = driver.findElements(limitsText);
			List<WebElement> payButtonList = driver.findElements(payButtons);
			List<WebElement> deleteButtonList = driver.findElements(deleteButtons);
			List<WebElement> deletePayeeButtonList = driver.findElements(deletePayeeButtons);

			for (int option = 0; option <= payeeNamesList.size() - 1; option++) {
				if (payeeNamesList.get(option).getText().endsWith(last4RefNum)) {
					payeeNamesList.get(option).click();
					Assert.assertTrue(limitsList.get(option).isDisplayed());
					Assert.assertTrue(payButtonList.get(option).isDisplayed());
					Assert.assertTrue(deleteButtonList.get(option).isDisplayed());
					appendScreenshotToCucumberReport();
					deleteButtonList.get(option).click();
					Assert.assertTrue(deletePayeeButtonList.get(option).isDisplayed());
					deletePayeeButtonList.get(option).click();
					break;
				}
			}
			isElementDisplayedWithLog(deletePayeeSucessMsg, 5);
			Assert.assertEquals(expDelSeccessMessage, getText(deletePayeeSucessMsg));

		} catch (Exception e) {
			logError("Bill is not added successfully - " + e.getMessage());
			injectWarningMessageToCucumberReport("Bill is not added successfully" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Bill is not added successfully", Status.FAILED);
			Assert.fail("Bill is not added successfully" + e.getMessage());
		}

	}

	// TODO - Check getCurrentUrl to be added in the BasePageForAllPlatform
	// To get the URL of the page
	public String getPageURL() {
		return driver.getCurrentUrl();
	}

	/*---------------------------------Start <PD_AddBillPay_MortgageBill_ROI_P>----------------------------------------
	Function Name: PD_AddBillPay_P
	Argument Name:
	Purpose: UB-339_Payments_ManagePayee_AddBillDailyLimitsValidation_AllCustomers
	Author Name: CAF Automation 
	 Create Date: 15-07-2021
	Modified Date| Modified By  |Modified purpose 
	  15/07/2021      C114322     Code update
	-----------------------------------End <PD_AddBillPay_MortgageBill_ROI_P>---------------------------------------
	*/
	public void PD_AddBillPay_MortgageBill_ROI_P(String userType, String billNameValue, String billRefNum) {
		try {
			validateCustomerIsOnAddBillPage();
			validateAddBillFieldContents();
			isElementDisplayedWithLog(billNameFieldDropdown, 5);
			cafFunctional.clickSelectValueFromDropDown(billNameFieldDropdown, billNameSelect, billNameValue); // Click
																												// and
																												// Select
																												// value
																												// from
																												// dropdown
			String billName = getText(billNameFieldDropdown);
			setValue(refNumText, billRefNum); // Set value for reference number
												// field
			clickJS(continueButton, "Continue");
			reviewBillDetailsOnReviewPage(billName, billRefNum); // Review bill
																	// details
																	// on Review
																	// Page
			clickJS(continueButton, "Continue");
			cafFunctional.EnterPINdetails(cafFunctional.GetvalidPIN(userType));
			clickJS(confirmButton, "Confirm");
			isElementDisplayedWithLog(successMessage, 5);
			verifyErrorMsgForDuplicateRecord(billNameValue, billRefNum);
			clickJS(goBackBtn, "Click on Go Back");
			verifyBillCreatedAndDelete(billRefNum);
			injectMessageToCucumberReport("Add Bill Mortgage is added successfully");
			Assert.assertTrue("Add Bill Mortgage is added successfully", true);

		} catch (Exception e) {
			logError("Add Bill Mortgage is not added successfully - " + e.getMessage());
			injectWarningMessageToCucumberReport("Add Bill Mortgage is not added successfully" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Add Bill Mortgage is not added successfully", Status.FAILED);
			Assert.fail("Add Bill Mortgage is not added successfully" + e.getMessage());
		}
	}

	private void validateAddBillFieldContents() {
		try {
			Assert.assertEquals("Bill Name Field is not available", "Bill name", getText(billNameLabel));
			Assert.assertTrue("Reference Field is not available", getText(referenceLabel).contains("Reference"));
			verifiyAddItHereLink();
		} catch (Exception e) {
			logError("Failure in validating the Add Bill page Fields" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in validating the Add Bill page Fields" + e.getMessage());
			appendScreenshotToCucumberReport();
			Assert.fail("Failure in validating the Add Bill page Fields" + e.getMessage());
		}
	}

	private void verifiyAddItHereLink() {
		try {
			clickJS(addItHereLink, "Click on Add It Here link");
			isElementDisplayedWithLog(addPayeePgTextLbl, 5);
			clickJS(goBackBtn, "Click on Go Back");
			clickJS(addBillBtn, "Add Bill");
		} catch (Exception e) {
			logError("Failure on clicking on Add It Here Link" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure on clicking on Add It Here Link" + e.getMessage());
			appendScreenshotToCucumberReport();
			Assert.fail("Failure on clicking on Add It Here Link" + e.getMessage());
		}
	}

	private void verifyErrorMsgForDuplicateRecord(String billNameValue, String billRefNum) {
		try {
			String expectedDuplicateErrMsg = getMessageText("expErrMessageDuplicateBill", "UXPBANKING365");
			clickJS(backToHome, "Back to Home");
			clickJS(getObject(devTypeToGetProperty + "home.tabPayments"), "Payments Tab");
			isElementDisplayedWithLog(getObject("pd.managepayeetab"), 5);
			clickJS(getObject("pd.managepayeetab"), "Manage Payee Option");
			clickJS(addBillBtn, "Add Bill");
			isElementDisplayedWithLog(billNameFieldDropdown, 5);
			cafFunctional.clickSelectValueFromDropDown(billNameFieldDropdown, billNameSelect, billNameValue); // Click
																												// and
																												// Select
																												// value
																												// from
																												// dropdown
			setValue(refNumText, billRefNum); // Set value for reference number
												// field
			clickJS(continueButton, "Continue");
			appendScreenshotToCucumberReport();
			String actualDuplicateErrMsg = getText(errMsgDuplicateRecordBill);
			Assert.assertEquals("Duplicate error msg is not displayed", expectedDuplicateErrMsg, actualDuplicateErrMsg);
		} catch (Exception e) {
			logError("Duplicate bill - The account entered is already registered as a payee." + e.getMessage());
			injectWarningMessageToCucumberReport(
					"Duplicate bill - The account entered is already registered as a payee." + e.getMessage());
			appendScreenshotToCucumberReport();
			Assert.fail("Duplicate bill - The account entered is already registered as a payee." + e.getMessage());
		}
	}
	/*---------------------------------Start <PD_AddPayee>----------------------------------------
	Function Name: PD_AddBPayee_P
	Argument Name:
	Purpose: UB-328_Payments_ManagePayee_AddUKDomesticPayeeValidation_NI/GBCustomer
	Author Name: CAF Automation 
	Create Date: 08-07-2021
	Modified Date| Modified By  |Modified purpose 
	08/07/2021      C111079     Code update
	-----------------------------------End <PD_AddPayee>---------------------------------------
	*/

	public void PD_AddPayee_P(String usertype, String country, String valType) {

		try {
			boolean bFlagMPayOpt = false;
			logMessage("MANAGE PAYEE VAL TYPE : " + valType);
			String strIBAN_BIC = dataLibrary.getIBAN_BIC(country.toUpperCase());
			String[] arrIBANData = strIBAN_BIC.split("_");
			String strPayeeIBAN = arrIBANData[0];
			String last4RefNum = strPayeeIBAN.substring(strPayeeIBAN.length() - 4);

			Deletepayee(last4RefNum,country);
			PD_ManagePayee("ADD_PAYEE");
			switch (valType.toUpperCase()) {

			case "ADD_PAYEE":
			case "ADD_SEPAPAYEE":
			case "ADD_UKDOMESTICPAYEE":
			case "ADD_INTERNATIONALPAYEE":
				String PayRef = PD_AddPayeeval_P(usertype, country, valType);
				Thread.sleep(3000);
				cafFunctional.EnterPINdetails(cafFunctional.GetvalidPIN(usertype));
				clickJS(confirmButton, "Confirm");
				isElementDisplayedWithLog(successMessage, 5);
				isElementDisplayedWithLog(backToHome, 5);
				isElementDisplayedWithLog(btnReviewSendMoney, 5);
				appendScreenshotToCucumberReport();
				injectMessageToCucumberReport(" Payee is added successfully");
				Assert.assertTrue("Payee is added successfully", true);
				
				if (isElementDisplayedWithLog(backToHome, 10)) {
					clickJS(backToHome, "Back to Home");
				}
				break;

			case "INCORRECT_PIN":
				PD_AddPayeeval_P(usertype, country, valType);
				Thread.sleep(3000);
				cafFunctional.EnterPINdetails(dataLibrary.getinValidPin());
				clickJS(confirmButton, "Confirm");
				isElementDisplayedWithLog(payeeAuthFailErr, 5);
				isElementDisplayedWithLog(payeeAuthFailTileImg, 5);
				clickJS(payeebtnTryagain, "Try Again");
				cafFunctional.EnterPINdetails(dataLibrary.getinValidPin());
				isElementDisplayedWithLog(loginattemptErrMsg, 5);
				clickJS(payeebtnTryagain, "Try Again");
				cafFunctional.EnterPINdetails(cafFunctional.GetvalidPIN(usertype));
				clickJS(confirmButton, "Confirm");
				isElementDisplayedWithLog(successMessage, 5);
				isElementDisplayedWithLog(backToHome, 5);
				isElementDisplayedWithLog(btnReviewSendMoney, 5);
				break;
			case "INACTIVE_PAYEE":

				Inactivepayee();
				break;
			case "PAYEE_ERRMSGVAL":

				break;
			case "PAYEE_FIELDVAL":
				validateAddPayeeFieldContents();
				break;
			case "CURRENCY_CONVERTER":
				PayRef = PD_AddPayeeval_P(usertype, country, valType);
				Thread.sleep(3000);
				cafFunctional.EnterPINdetails(cafFunctional.GetvalidPIN(usertype));
				clickJS(confirmButton, "Confirm");
				isElementDisplayedWithLog(successMessage, 5);
				appendScreenshotToCucumberReport();
				injectMessageToCucumberReport(" Payee is added successfully");
				Assert.assertTrue("Payee is added successfully", true);
				clickJS(btnReviewSendMoney, "Send Money in Review page");
				sendmoneypg = new Payment_SendMoneyPage(driver);
				sendmoneypg.ValidateSendMoneyScreen();
				clickJS(sendmoneyselAcc, "Select pay from");
				selectValueFromDropDown(sendmoneyselAllAcc, "CURRENT ACCOUNT " + usertype);
				Thread.sleep(30000);
				isElementDisplayedWithLog(sendmoneyselPay, 10);
				clickJS(sendmoneyselPay, "Select pay To");
				selectValueFromDropDown(sendmoneyselAllPay, "International Form B Legacy");
				verifyCurrencyConverterErr();
				verifyBillCreatedAndDelete(PayRef);
				break;
			}

			appendScreenshotToCucumberReport();
		} catch (Exception e) {
			logError("Error occured inside manage payee options type" + e.getMessage());
			injectWarningMessageToCucumberReport(
					"Failure in manage payee selection and navigation to page" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in manage payee validation " + valType, Status.FAILED);

		}
	}

	private void validateCustomerIsOnAddPayeePage() throws Exception {
		String actualMessage = getText(addPayeeTxt);
		Assert.assertEquals("User is not navigated to Add Payee page", "Add Payee", actualMessage);
	}

	private String PD_AddPayeeval_P(String usertype, String country, String valType) {
		String strPayeename = null;
		try {
			
			String strPayeeReftxt= null;
			String strPayeeIBAN = null;
			validateCustomerIsOnAddPayeePage();
			// Select country from drop down - method
			if (isElementDisplayedWithLog((addPayeeTxt), 5)) {
				logMessage("Add Payee Title is displayed on Add Payee Details Page");
				clickJS(countryDropdown,"Country");
				clickJS(getObject("pd.selectCountry",country));
				String payeeCountry = getText(countryDropdown);
			}
			Thread.sleep(3000);


			strPayeename = generateRandomString();
			setValue(payeeNameText, strPayeename);
			String strIBAN_BIC = dataLibrary.getIBAN_BIC(country.toUpperCase());
			String[] arrIBANData = strIBAN_BIC.split("_");
			strPayeeIBAN = arrIBANData[0];
			String strPayeeBIC = arrIBANData[1];
			// enter IBAN


			setValue(payeeBICText, strPayeeBIC);

			switch (country) {
			case "Ireland":
			case "France":
				setValue(payeeIBANText, strPayeeIBAN);
				verifyBICIBANCalculator();
				strPayeeReftxt = generateRandomString() + "Reftext";

				setValue(payeeDescriptionText, generateRandomString());
				setValue(payeeRefText, strPayeeReftxt);
				break;
			case "United Kingdom": // addr1, addr2,country of res, bankaddr1, bankaddr2,city
				setValue(payeeIBANText, strPayeeIBAN);
				
				//setValue(payeeABAText,dataLibrary.getABAcode());
				//setValue(payeeNSCText,dataLibrary.getNSC(strPayeeIBAN,country));
				//setValue(payeeAccNoText,dataLibrary.getAccNo(strPayeeIBAN,country));
				setValue(payeeAddres1Text, dataLibrary.getAddressLine1());
				setValue(payeeAddres2Text, dataLibrary.getAddressLine1());
				setValue(payeeCountryofresidence, country);
				setValue(bankAddress1Text, dataLibrary.getAddressLine1());
				setValue(bankAddress2Text, dataLibrary.getAddressLine1());
				setValue(bankCityTxt, dataLibrary.getAddressCountry(usertype));
				
				break;
				
				
			case "Poland":
				setValue(payeeIBANText, strPayeeIBAN);
				setValue(payeeAddres1Text, dataLibrary.getAddressLine1());
				setValue(payeeAddres2Text, dataLibrary.getAddressLine1());
				setValue(payeeCountryofresidence, country);

				if (isElementDisplayedWithLog(bankAddress1Text, 5)) {
					setValue(bankAddress1Text, dataLibrary.getAddressLine1());
				}
				if (isElementDisplayedWithLog(bankAddress2Text, 5)) {
					setValue(bankAddress2Text, dataLibrary.getAddressLine1());
				}
				if (isElementDisplayedWithLog(bankCityTxt, 5)) {
					setValue(bankCityTxt, dataLibrary.getAddressCountry(usertype));
				}
				break;
			case "United States":

				// BIC,ABA code, account number
				setValue(payeeABAText, dataLibrary.getABAcode());
				setValue(payeeAccNoText, dataLibrary.getInternationalAccNo(country));
				setValue(payeeAddres1Text, dataLibrary.getAddressLine1());
				setValue(payeeAddres2Text, dataLibrary.getAddressLine1());
				setValue(payeeCountryofresidence, country);
				if (isElementDisplayedWithLog(bankAddress1Text, 5)) {
					setValue(bankAddress1Text, dataLibrary.getAddressLine1());
				}
				if (isElementDisplayedWithLog(bankAddress2Text, 5)) {
					setValue(bankAddress2Text, dataLibrary.getAddressLine1());
				}
				if (isElementDisplayedWithLog(bankCityTxt, 5)) {
					setValue(bankCityTxt, dataLibrary.getAddressCountry(usertype));
				}
				break;
			case "Canada":
			case "India":

				// BIC,ABA code, account number

				setValue(payeeAccNoText, dataLibrary.getInternationalAccNo(country));
				setValue(payeeAddres1Text, dataLibrary.getAddressLine1());
				setValue(payeeAddres2Text, dataLibrary.getAddressLine1());
				setValue(payeeCountryofresidence, country);
				if (isElementDisplayedWithLog(bankAddress1Text, 5)) {
					setValue(bankAddress1Text, dataLibrary.getAddressLine1());
				}
				if (isElementDisplayedWithLog(bankAddress2Text, 5)) {
					setValue(bankAddress2Text, dataLibrary.getAddressLine1());
				}
				if (isElementDisplayedWithLog(bankCityTxt, 5)) {
					setValue(bankCityTxt, dataLibrary.getAddressCountry(usertype));
				}
				break;
			}
			clickJS(continueButton, "Continue");
			Allure.step("Add Payee form is completed successfully", Status.PASSED);
			appendScreenshotToCucumberReport();
			reviewPayeeDetailsOnReviewPage(strPayeename, strPayeeReftxt, strPayeeBIC, country);
				
			
		} catch (Exception e) {
			logError("Add Payee is NOT added successfully - " + e.getMessage());
			injectWarningMessageToCucumberReport("Add Payee is NOT added successfully" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Add Payee is not added successfully", Status.FAILED);
			Assert.fail("Add Payee is not added successfully" + e.getMessage());
		}
		return strPayeename;
		
	}

	// // Review payee details if it matches with the values of payee entered
	private void reviewPayeeDetailsOnReviewPage(String payeeName, String reference, String BIC, String country) throws Exception {

		String actualPayeeName = getText(payeeNameOnReviewPg);
		String countryName = country;
		String actualReference = null;
		if ((!country.equalsIgnoreCase("Poland"))) { 
			actualReference = getText(referenceOnReviewPg);
		}
		String actualIBAN = getText(IBANOnReviewPg);
		String actualBIC = getText(BICOnReviewPg);
		if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
			isElementDisplayedWithLog(getObject(devTypeToGetProperty + "pd.hdrPageTitle"), 5);
		} else if (devTypeToGetProperty.equalsIgnoreCase("mw.")) {
			isElementDisplayedWithLog(getObject(devTypeToGetProperty + "pd.hdrPageTitle"), 5);
		} else {
			logMessage("Add Payee- Review page is not displayed ");
		}
		isElementDisplayedWithLog(payeeNameOnReviewPg, 5);
		Assert.assertEquals("Payee Name is NOT as expected on Review page", payeeName, actualPayeeName);
		if ((!country.equalsIgnoreCase("Poland"))) { 
			Assert.assertEquals("Reference text is NOT as expected on Review page", reference, actualReference);
		}
		Assert.assertEquals("IBAN is NOT as expected on Review page", BIC, actualBIC);
		Allure.step("Payee Form Review is successful", Status.PASSED);
		clickJS(continueButton, "Continue");
		appendScreenshotToCucumberReport();

	}

	private void validateAddPayeeFieldContents() throws Exception {
		// Check the Label = Countrycurrency
		isElementDisplayedWithLog(payeelabelCountrycurr, 5);
		// Check the Label = Country
		isElementDisplayedWithLog(payeelabelCountry, 5);

		// Check the Label = Location of Payee's bank
		isElementDisplayedWithLog(payeelabellocation, 5);

		// Check the Defaulted Country
		String strDefaultedCountry = getText(countryDropdown);
		isElementDisplayedWithLog(payeelabelselCountry, 3);
		// Check the Label = Currency
		isElementDisplayedWithLog(payeelabelcurr, 5);

		// Check the defaulted currency
		String strValueDefaulted = getText(payeelabelcurrtxt);
		isElementDisplayedWithLog(payeelabelcurrtxt, 3);

		// Check the Label = Visible to payee
		isElementDisplayedWithLog(payeelabelvisittxt, 5);

		// Check the Label = Description
		isElementDisplayedWithLog(payeelabeldesc, 5);
	}
	public void Deletepayee(String billRefNum,String country) {
		try {
			boolean payeefound = false;
			if (isElementDisplayedWithLog(goBackBtn, 10)) {
				clickJS(goBackBtn, "Go back button");

			}

			String last4RefNum = billRefNum.substring(billRefNum.length() - 4);
			String expDelSeccessMessage = getMessageText("expDeletePayeeSuccessMsg", "UXPBANKING365");
			String expInactivePayMessage = getMessageText("expPayinactiveErr", "UXPBANKING365");
			List<WebElement> payeeNamesList = driver.findElements(payeeNames);
			List<WebElement> limitsList = driver.findElements(limitsText);
			List<WebElement> payButtonList = driver.findElements(payButtons);
			List<WebElement> deleteButtonList = driver.findElements(deleteButtons);
			List<WebElement> deletePayeeButtonList = driver.findElements(deletePayeeButtons);
			
			for (int option = 0; option <= payeeNamesList.size() - 1; option++) {
				
				if (payeeNamesList.get(option).getText().endsWith(last4RefNum)) {
					payeeNamesList.get(option).click();

					if (isElementDisplayed(payButtonList.get(option))) {
						Allure.step("Payee is active ", Status.PASSED);
						Assert.assertTrue(limitsList.get(option).isDisplayed());
						Allure.step("Transfer limits  is displayed for active payee", Status.PASSED);
						Assert.assertTrue(payButtonList.get(option).isDisplayed());
						Allure.step("Pay button is displayed for active payee", Status.PASSED);
						payeefound = true;
					} else {

						Allure.step("Payee is not active ", Status.PASSED);
					}
					if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
						if(country.equalsIgnoreCase("Ireland")){
						String strRowText = payeeNamesList.get(option).getText().toUpperCase();
						if(strRowText.contains(billRefNum))
						{
							Allure.step("IBAN number is displayed for active payee", Status.PASSED);
						}
						}
						Assert.assertTrue(deleteButtonList.get(option).isDisplayed());
						Allure.step("Delete button is displayed for active payee", Status.PASSED);
						appendScreenshotToCucumberReport();
						deleteButtonList.get(option).click();
						Assert.assertTrue(deletePayeeButtonList.get(option).isDisplayed());
						deletePayeeButtonList.get(option).click();
						Allure.step("Payee is deleted successfully", Status.PASSED);
						break;
					} else if (devTypeToGetProperty.equalsIgnoreCase("mw.")) {
						Assert.assertTrue(
								isElementDisplayedWithLog(getObject(devTypeToGetProperty + "pd.Deletebtn"), 3));
						Allure.step("Delete button is displayed for active payee", Status.PASSED);
						clickJS(getObject(devTypeToGetProperty + "pd.Deletebtn"));
						Assert.assertTrue(deletePayeeButtonList.get(option).isDisplayed());
						deletePayeeButtonList.get(option).click();
						Allure.step("Payee is deleted successfully", Status.PASSED);
						break;
					}
				}

			}
			if (payeefound == true) {
				isElementDisplayedWithLog(deletePayeeSucessMsg, 5);
				Assert.assertEquals(expDelSeccessMessage, getText(deletePayeeSucessMsg));
			} else {
				Allure.step("Payee is not found in the Manage payee list", Status.PASSED);
			}

		} catch (Exception e) {
			logError("Payee is not deleted successfully - " + e.getMessage());
			injectWarningMessageToCucumberReport("Payee is not deleted successfully" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Payee is not deleted successfully", Status.FAILED);
			Assert.fail("Payee is not deleted successfully" + e.getMessage());
		}
	}

	/*---------------------------------Start <verifyCurrenmcyConverterErr>----------------------------------------
	 Function Name: verifyCurrenmcyConverterErr
	 Purpose: verify currency converter link is working .
	 Prerequisite: 
	 Author Name: CAF Automation 
	 Create Date: 29-07-2021
	 -----------------------------------End <verifyCurrenmcyConverterErr>---------------------------------------
	 */
	public void verifyCurrencyConverterErr() throws Exception {
		try {
			String strErrorMessage = getMessageText("expAmountErr", "UXPBANKING365");
			String strCurrencyErrorMessage = getMessageText("expAmountErr", "UXPBANKING365");
			isElementDisplayedWithLog(sendmoneyCurrencyConvertor, 5);
			clickJS(sendmoneyCurrencyConvertor, "Currency Converter Button clicked");
			clickJS(sendmoneybtnConvertamnt, "Convert amount Button clicked");
			if (isElementDisplayedWithLog((sendmoneybtnConverterrmsg), 30)) {
				String strText = getText(sendmoneyCurConvertoramounterrmsg);
				String strText2 = getText(sendmoneyCurConvertorcurrerrmsg);
				if (strText.equals(strErrorMessage) && strText2.equals(strCurrencyErrorMessage)) {
					// sendKeys(getObject("Payments.txtEnterAmtEUR"), "");
					Thread.sleep(2000);
					Assert.assertTrue(
							"validateCurrencyConverterErrors- Input box on amount field :: Error Message ::" + strText,
							true);
					Assert.assertTrue(
							"validateCurrencyConverterErrors- Input box on amount field :: Error Message ::" + strText2,
							true);
				} else {
					Assert.fail(
							"Failure in validateCurrencyConverterErrors - Input box on amount field / on currency field :: Error Message :: '"
									+ strErrorMessage + "':: OR :: '" + strCurrencyErrorMessage
									+ "':: is NOT displayed Successfully...for Input Value");
				}
			}
			String[] strErrorMessageInputValue = new String[] { "0.00;Please enter an amount greater than AUD 0.00",
					"0.19;Please enter a valid amount." };
			for (int i = 0; i < strErrorMessageInputValue.length; i++) {
				String strErrorMessageCheck[] = strErrorMessageInputValue[i].split(";");
				String strInputValue = strErrorMessageInputValue[i];
				String strErrorMessages = strErrorMessageCheck[1];
				if (isElementDisplayedWithLog(sendmoneytxtEnterAmtEUR, 20)) {
					Thread.sleep(2000);
					setValue(sendmoneytxtEnterAmtEUR, strInputValue);
					Thread.sleep(2000);
					clickJS(sendmoneybtnConvertamnt);
					Thread.sleep(4000);
				}

				if (isElementDisplayed(sendmoneybtnConverterrmsg, 30)) {
					String strText = getText(sendmoneyCurConvertoramounterrmsg);
					if (strText.equals(strErrorMessages.trim())) {
						setValue(sendmoneytxtEnterAmtEUR, "");
						Thread.sleep(4000);
						Assert.assertTrue(
								"validateCurrencyConverterErrors- Input box on amount field :: Error Message ::"
										+ strText,
								true);
					} else {
						Assert.fail(
								"Failure in validateCurrencyConverterErrors - Input box on amount field / on currency field :: Error Message :: '"
										+ strErrorMessage + "':: OR :: '" + strCurrencyErrorMessage
										+ "':: is NOT displayed Successfully...for Input Value");
					}
				}
			}
		} catch (Exception e) {
			logError("Failure in validateCurrencyConverterErrors  - " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in validateCurrencyConverterErrors " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in validateCurrencyConverterErrors ", Status.FAILED);
			Assert.fail("Failure in validateCurrencyConverterErrors " + e.getMessage());
		}
	}

	/*---------------------------------Start <verifyBICIBANCalculator>----------------------------------------
	Function Name: verifyBICIBANCalculator
	Purpose: verify BIC/IBAN link is working .
	Prerequisite: 
	Author Name: CAF Automation 
	Create Date: 29-07-2021
	-----------------------------------End <verifyBICIBANCalculator>---------------------------------------*/
	public void verifyBICIBANCalculator() throws Exception {
		try {
			if (isElementDisplayed(payeeIBANCalclink, 5)) {
				verifyLinkAndItsNavigation(payeeIBANCalclink);
			}
		} catch (Exception e) {
			logError(
					"Failure in verifyBICIBANCalculator - BIC/IBAN calculator is NOT appearing in Add Payee page when adding a SEPA Paye - "
							+ e.getMessage());
			injectWarningMessageToCucumberReport(
					"Failure in verifyBICIBANCalculator - BIC/IBAN calculator is NOT appearing in Add Payee page when adding a SEPA Paye "
							+ e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step(
					"Failure in verifyBICIBANCalculator - BIC/IBAN calculator is NOT appearing in Add Payee page when adding a SEPA Paye ",
					Status.FAILED);
			Assert.fail(
					"Failure in verifyBICIBANCalculator - BIC/IBAN calculator is NOT appearing in Add Payee page when adding a SEPA Paye"
							+ e.getMessage());
		}
	}

	/*---------------------------------Start <verifyLinkAndItsNavigation>----------------------------------------
	Function Name: verifyLinkAndItsNavigation
	Purpose: verify link is working from link obj passed
	Prerequisite: 
	Author Name: CAF Automation 
	Create Date: 29-07-2021
	-----------------------------------End <verifyLinkAndItsNavigation>---------------------------------------*/
	public void verifyLinkAndItsNavigation(By linkobj) {
		try {
			if (isElementDisplayed(linkobj, 5)) {
				String strLinkText = getText(linkobj);
				Assert.assertTrue("verifyLink - Link '" + strLinkText + "' is displayed correctly.", true);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView();", linkobj);
				js.executeScript("arguments[0].click();", linkobj);
				String strlinkobj = driver.findElement(linkobj).getAttribute("href");
				cafFunctional.verifyNewlyOpenedTab(strlinkobj);
				js.executeScript("arguments[0].click();", strlinkobj);
				if (devTypeToGetProperty.equalsIgnoreCase("mw.") || devTypeToGetProperty.equalsIgnoreCase("mw.")) {
					String link = strlinkobj;
					if (link == null || link.isEmpty()) {
						Assert.assertTrue("verifyNewlyOpendTab- New Tab did not opened successfully..Please check...!",
								true);
					} else {
						Assert.assertTrue("verifyNewlyOpendTab- Newly Opened Tab Opens the URL", true);
					}
				} else {
					cafFunctional.verifyNewlyOpenedTab(strlinkobj);
				}

			}
		} catch (Exception e) {
			logError("Failure in link verification - Link is not opened successfully in newtab- " + e.getMessage());
		}
	}

	public void Inactivepayee() {
		try {
			boolean payeefound = false;
			if (isElementDisplayedWithLog(goBackBtn, 10)) {
				clickJS(goBackBtn, "Go back button");
			}
			List<WebElement> inactivepayeeNamesList = null;
			List<WebElement> inactivepayeestatusList = null;
			List<WebElement> inactivepayeealertList = null;			
			if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
				inactivepayeeNamesList = driver.findElements(getObject(devTypeToGetProperty + "pd.payeeinactivelist"));
			} else if (devTypeToGetProperty.equalsIgnoreCase("mw.")) {
				inactivepayeeNamesList = driver.findElements(getObject(devTypeToGetProperty + "pd.payeeinactivelist"));
			} else {
				logMessage("Manage Payee - No inactive records found");
			}


			for (int option = 0; option <= inactivepayeeNamesList.size() - 1; option++) {
				String strRowText = inactivepayeeNamesList.get(option).getText().toUpperCase();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView();", inactivepayeeNamesList.get(option));
				Thread.sleep(1000);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				appendScreenshotToCucumberReport();
				executor.executeScript("arguments[0].click();", inactivepayeeNamesList.get(option));
				Thread.sleep(4000);
				Assert.assertTrue("Payee details of " + strRowText + " are verified successfully", true);
				Allure.step("Payee details of " + strRowText + " are verified successfully", Status.PASSED);
                break;
				
			}
			if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
				inactivepayeestatusList = driver
						.findElements(getObject(devTypeToGetProperty + "pd.payeeinactivestatuslist"));
				Assert.assertTrue(isElementDisplayed(inactivepayeestatusList.get(0)));
				Allure.step("Payee is Inactive -Status is displayed as expected", Status.PASSED);
				inactivepayeealertList = driver
						.findElements(getObject(devTypeToGetProperty + "pd.inactivepayeealertmsglist"));
				Assert.assertTrue(isElementDisplayed(inactivepayeealertList.get(0)));
				Allure.step("Payee is Inactive -Inactive alert message is displayed as expected", Status.PASSED);
				Assert.assertTrue(isElementDisplayed(getObject(devTypeToGetProperty + "pd.Deletebtn")));
				Allure.step("Payee is inactive and delete button is displayed", Status.PASSED);
				List<WebElement> payButtonList = driver.findElements(payButtons);
				Assert.assertFalse(isElementDisplayed(payButtonList.get(0)));
				Allure.step("Payee is inactive and pay button is not displayed as expected", Status.PASSED);
				
			} else if (devTypeToGetProperty.equalsIgnoreCase("mw.")) {
				By inactivepayeestatus = getObject(devTypeToGetProperty + "pd.payeeinactivestatus");
				Assert.assertTrue(isElementDisplayed(inactivepayeestatus));
				Allure.step("Payee is Inactive -Status is displayed as expected", Status.PASSED);
				By inactivepayeealert = getObject(devTypeToGetProperty + "pd.inactivepayeealertmsg");
				isElementDisplayedWithLog(inactivepayeealert, 3);
				Assert.assertTrue(isElementDisplayed(inactivepayeealert));
				Allure.step("Payee is Inactive -Inactive alert message is displayed as expected", Status.PASSED);
				Assert.assertTrue(isElementDisplayed(getObject(devTypeToGetProperty + "pd.Deletebtn")));
				Allure.step("Payee is inactive and delete button is displayed as expected", Status.PASSED);
			} else {
				logMessage("Manage Payee - Payee status not found");
			}
			
		}

		catch (Exception e) {
			logError("Payee is not inactive  - " + e.getMessage());
			injectWarningMessageToCucumberReport("Payee is not inactive" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Payee is not inactive", Status.FAILED);
			Assert.fail("Payee is not inactive" + e.getMessage());
		}
	}

}

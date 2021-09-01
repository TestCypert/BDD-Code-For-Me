package com.boi.grp.pageobjects.Accounts;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;


public class AccountChequeSearch extends BasePageForAllPlatform {
	public UIResusableLibrary cafFunctional;
	public AccountOptions accOpt;
	By linkaccChequeSearch = getObject("accChequeSearch.linkaccChequeSearch");
	By tbChequeNo = getObject("accChequeSearch.tbChequeNo");
	By btnSearchCheque = getObject("accChequeSearch.btnSearchCheque");
	By lblChequeNo = getObject("accChequeSearch.lblChequeNo");
	By lblChequeAmt = getObject("accChequeSearch.lblChequeAmt");
	By lblDateDebited = getObject("accChequeSearch.lblDateDebited");
	By lblErrorInline = getObject("accChequeSearch.lblErrorInline");
	By lblErrorAlert = getObject("accChequeSearch.lblErrorAlert");
	By btnNewSearch = getObject("accChequeSearch.btnNewSearch");
	String ExpectedAlertMsg = getMessageText("accChequeSearch.ExpectedAlertMsg", "UXPBANKING365");
	String ExpectedErrorMsg = getMessageText("accChequeSearch.ExpectedErrorMsg", "UXPBANKING365");

	public AccountChequeSearch(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional = new UIResusableLibrary(driver);
		accOpt = new AccountOptions(driver);
	}

	/*---------------------------------Start <ACC_ChequeSearch>----------------------------------------
	Function Name: ACC_ChequeSearch
	Argument Name:
	Purpose: Validate account cheque search functionality.
	Author Name: CAF Automation 
	 Create Date: 30-04-2021
	Modified Date| Modified By  |Modified purpose 
	  30/04/2021      C114323     Code update
	-----------------------------------End <ACC_ChequeSearch>---------------------------------------
	*/
	// TODO for Mobile code - inline code of getOBject for different paths for
	// different platforms
	public void ACC_ChequeSearch_P(String ChequeNo) {
		boolean bChequeSearchDone = false;
		try {
			accOpt.ACC_NavigateTO("CHEQUE_SEARCH");
			String arr[] = ChequeNo.split("-");
			String dataVal = arr[0];
			String ChequeNumber = arr[1];
			logMessage("DATAVAL value is : " + dataVal + "   Cheque Number is : = " + ChequeNumber);
			Assert.assertTrue(isElementDisplayed(tbChequeNo));

			switch (dataVal.toUpperCase()) {

			case "VALID":
				setValue(tbChequeNo, ChequeNumber);
				clickJS(btnSearchCheque);
				waitForElementToBeDisplayed(lblChequeNo);
				Assert.assertTrue(isElementDisplayed(lblChequeNo));
				Assert.assertTrue(isElementDisplayed(lblChequeAmt));
				Assert.assertTrue(isElementDisplayed(lblDateDebited));
				clickJS(btnNewSearch);
				bChequeSearchDone = true;
				break;

			case "INVALID":
				setValue(tbChequeNo, ChequeNumber);
				clickJS(btnSearchCheque);
				String actualAlertMsg = getText(lblErrorAlert);
				Assert.assertEquals(ExpectedAlertMsg, actualAlertMsg);
				bChequeSearchDone = true;
				break;

			case "BLANK":
				// setValue(tbChequeNo,ChequeNumber);
				clickJS(btnSearchCheque);
				String actualErrorMsg = getText(lblErrorInline);
				Assert.assertEquals(ExpectedErrorMsg, actualErrorMsg);
				bChequeSearchDone = true;
				break;
			}
			logMessage("Verified cheque search functionality successfully : END");
			injectMessageToCucumberReport("Account cheque search completed");
			appendScreenshotToCucumberReport();
			Allure.step("Success in account cheque search" + ChequeNumber, Status.PASSED);

		} catch (Error e) {
			logError("Error occured inside ACC_ChequeSearch_P" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in account cheque search with" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in account cheque search with " + ChequeNo, Status.FAILED);
			Assert.assertTrue(bChequeSearchDone == true);
		} catch (Exception e) {
			logError("Error occured inside ACC_ChequeSearch_P" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in account cheque search with" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in account cheque search with " + ChequeNo, Status.FAILED);
			Assert.assertTrue(bChequeSearchDone == true);
		}
	}
}

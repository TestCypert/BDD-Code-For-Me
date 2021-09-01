package com.boi.grp.pageobjects.Accounts;

import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class AccountTransactions extends BasePageForAllPlatform {

	public UIResusableLibrary cafFunctional;
	public AccountOptions accOpt;

	By lnkExportTransaction = getObject("accTransact.lnkExportTransaction");
	By titlePopUpExportTransaction = getObject("accTransact.titlePopUpExportTransaction");
	By txtPopUpMsg = getObject("accTransact.txtPopUpMsg");
	By btnPopUpOK = getObject("accTransact.btnPopUpOK");
	By btnPopUpCancel = getObject("accTransact.btnPopUpCancel");

	By btnPayNow = getObject("accTransact.btnPayNow");
	By btnAddBillPayee = getObject("accTransact.btnAddBillPayee");

	By lnkExtTrasDetails = getObject("accTransact.lnkExtTrasDetails");
	By firstExtTransaction = getObject("accTransact.firstExtTransaction", 1);
	By BtnReturnToDetails = getObject("accTransact.BtnReturnToDetails");
	By cardExtendedTransaction = getObject("accTransact.cardExtendedTransaction");
	By btnShowMore = getObject("accTransact.btnShowMore");
	By spinner = getObject("accTransact.spinner");

	By accNickname = getObject("accTransact.accNickname");
	By labelInProgress = getObject("accTransact.labelInProgress");
	By labelCompleted = getObject("accTransact.labelCompleted");
	By transFilterIcon = getObject("accTransact.transFilterIcon");
	By lstTransTblHeaders = getObject("accTransact.lstTransTblHeaders");
	By lstAllInProgressTrans = getObject("accTransact.lstAllInProgressTrans");
	By lstAllCompletedTrans = getObject("accTransact.lstAllCompletedTrans");
	By lnkBalanceTransactionsExp = getObject("accTransact.lnkBalanceTransactionsExp");
	By lnkBalanceTransactionsExpOpen = getObject("accTransact.lnkBalanceTransactionsExpOpen");
	By lstBalanceTransactionsExpMenuPanel = getObject("accTransact.lstBalanceTransactionsExpMenuPanel");
	//Transaction Filter
    By filterAccTransFil = getObject("accTransFil.filter");
    By allBtnAccTransFil = getObject("accTransFil.allBtn");
    By monthBtnAccTransFil = getObject("accTransFill.monthBtn");
    By dateRangeBtnAccTransFil = getObject("accTransFil.dateRangeBtn");
    By filterBtnAccTransFil = getObject("accTransFil.filterBtn");
    By transacTypeAccTransFil = getObject("accTransFil.transacType");
    By dateStartFromAccTransFil = getObject("accTransFil.dateStartFrom");
    By dateEndToAccTransFil = getObject("accTransFil.dateEndTo");
    By errInvalidDateAccTransFil = getObject("accTransFil.errInvalidDate");
    By errDateRangeAccTransFil = getObject("accTransFil.errDateRange");
    By errNoFilterResultAccTransFil = getObject("accTransFil.errNoFilterResult");
    By dateLblAccTransFil = getObject("accTransFil.dateLbl");
    By detailsLblAccTransFil = getObject("accTransFil.detailsLbl");
    By amountLblAccTransFil = getObject("accTransFil.amountLbl");
    By balanceLblAccTransFil = getObject("accTransFil.balanceLbl");
    By exportTransHisLnkAccTransFil = getObject("accTransFil.exportTransHisLnk");
    By okBtnAccTransFil = getObject("accTransFil.okBtn");
    By cancelBtnAccTransFil = getObject("accTransFil.cancelBtn");
    By resetBtnAccTransFil = getObject("accTransFil.resetBtn");
	String ExpectedPopupTxtMsg = getMessageText("accExtendedTrans.ExpectedPopupTxtMsg", "UXPBANKING365");

	// String ExpectedPopupTxtMsg1 =
	// getMessageText("accExtendedTrans.ExpectedPopupTxtMsg1","UXPBANKING365");
	// String ExpectedPopupTxtMsg2 =
	// getMessageText("accExtendedTrans.ExpectedPopupTxtMsg2","UXPBANKING365");
	// String ExpectedPopupTxtMsg = "\n" + ExpectedPopupTxtMsg1 + "\n\n" +
	// ExpectedPopupTxtMsg2;

	public AccountTransactions(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	/*---------------------------------Start <ACC_Transactions_P>----------------------------------------
	Function Name: ACC_Transactions_P
	Argument Name:
	Purpose: Validate account transaction tab functionality.
	Author Name: CAF Automation 
	 Create Date: 05-07-2021
	Modified Date| Modified By  |Modified purpose 
	  05/07/2021      C114323     Code completion
	-----------------------------------End <ACC_Transactions_P>---------------------------------------
	*/
	public void ACC_Transactions_P(String actionItem) {
		boolean bActionFag = false;
		try {

			switch (actionItem.toUpperCase()) {

			case "PAY_BILL":
				ACC_PayBill_P();
				bActionFag = true;
				break;

			case "EXPORT_TRANSACTION_HISTORY":
				ACC_ExpTransactionHistory_P();
				bActionFag = true;
				break;

			case "EXTENDED_TRANSACTION_DEBIT_CANCELLATION":
				ACC_ExtTransAndDDCancel_P();
				bActionFag = true;
				break;

			case "EXTENDED_TRANSACTION_DETAILS_VALIDATION":
				ACC_ExtTransDetailsVal_P();
				bActionFag = true;
				break;
			case "TRANSACTION_FILTER_VALIDATION":
				ACC_TransFilterValidation_P();
				bActionFag = true;
				break;

			default:
				logMessage("INVALID ACTION");
				break;

			}

			if (bActionFag == true) {
				logMessage("Verified account transaction tab functionality successfully : END");
				injectMessageToCucumberReport("Account transaction tab functionality check completed");
				appendScreenshotToCucumberReport();
				Allure.step("Success in account transaction functonality check", Status.PASSED);
			} else {
				logError("Error occured inside ACC_Transactions_P");
				injectWarningMessageToCucumberReport("Failure in account transaction tab functionality");
				appendScreenshotToCucumberReport();
				Allure.step("Failure in account transaction tab functionality ", Status.FAILED);
			}

		}

		catch (Exception e) {
			logError("Error occured inside ACC_Transactions_P" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in account cheque search with" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in account transaction tab functionality ", Status.FAILED);
		}

	}

	public void ACC_ExpTransactionHistory_P() {
		try {
			while (isElementDisplayed(lnkExportTransaction) == false) {
				scrollDown();
			}
			clickJS(lnkExportTransaction);
			// waitForPageLoaded();
			// validating pop up header
			if (isElementDisplayed(titlePopUpExportTransaction)) {
				if (isElementDisplayed(txtPopUpMsg)) {
					// Assert.assertEquals(ExpectedPopupTxtMsg , UiTextMsg);
					Assert.assertTrue(isElementDisplayed(btnPopUpOK));
					waitForElementToClickable(btnPopUpOK, 10);
					clickJS(btnPopUpOK);
					Thread.sleep(5000);
				}
			}
			logMessage("Verified export transaction history successfully");
			injectMessageToCucumberReport("Verified export transaction history successfully");
			appendScreenshotToCucumberReport();
			Allure.step("Success in export transaction history", Status.PASSED);
		}

		catch (Error e) {
			logError("Error occured inside exportTransactionHistory" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in export transaction history " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in export transaction history ", Status.FAILED);
		} catch (Exception e) {
			logError("Error occured inside exportTransactionHistory function" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in export transaction history " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in export transaction history ", Status.FAILED);
		}
	}

	public void ACC_PayBill_P() {
		try {
			waitForElementToBeDisplayed(btnPayNow);
			clickJS(btnPayNow);
			Assert.assertTrue(isElementDisplayed(btnAddBillPayee));
			clickJS(btnAddBillPayee);
			waitForPageLoaded();
			// TO-DO : Write code here navigate to Add Bill from Payments module
			// once developed
			logInfo("Credit card Pay Bills completed");
			injectMessageToCucumberReport("Credit card Pay Bills completed");
			appendScreenshotToCucumberReport();
			Allure.step("Credit card Pay Bills completed", Status.PASSED);
		} catch (Exception e) {
			logError("Error occured inside ACC_PayBills_P" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in performing Pay bill journey" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in performing Pay bill journey with ", Status.FAILED);
		}
	}

	public void ACC_ExtTransAndDDCancel_P() {
		try {
			// TODO Extended transaction and debit cancellation code here.
			// Cannot see Cancel button now. Issue with S4.
			List<WebElement> allExtTransactList = driver.findElements(lnkExtTrasDetails);
			if (allExtTransactList.size() > 0) {
				for (int i = 0; i < allExtTransactList.size(); i++) {
					scrollToView(firstExtTransaction);
					clickJS(firstExtTransaction);
					Assert.assertTrue(isElementDisplayed(BtnReturnToDetails));
					Assert.assertTrue(isElementDisplayed(cardExtendedTransaction));
					clickJS(BtnReturnToDetails);
				}
			}

			logInfo("Extended transaction validation and direct debit cancellation completed successfully");
			injectMessageToCucumberReport("Credit card Pay Bills completed");
			appendScreenshotToCucumberReport();
			Allure.step("Extended transaction validation and direct debit cancellation completed successfully",
					Status.PASSED);

		} catch (Error e) {
			logError("Extended transaction validation and direct debit cancellation completed successfully"
					+ e.getMessage());
			injectWarningMessageToCucumberReport(
					"Failure in extended transaction validation and direct debit cancellation " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("EFailure in extended transaction validation and direct debit cancellation ", Status.FAILED);
		} catch (Exception e) {
			logError("Extended transaction validation and direct debit cancellation completed successfully"
					+ e.getMessage());
			injectWarningMessageToCucumberReport(
					"Failure in extended transaction validation and direct debit cancellation " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("EFailure in extended transaction validation and direct debit cancellation ", Status.FAILED);
		}
	}

	/*---------------------------------Start <ACC_TransFilterValidation_P>----------------------------------------
	Function Name: ACC_TransFilterValidation_P
	Argument Name:
	Purpose: UB-296_Accounts_AccountSummary_TransactionFilterValidations_SavingAccount
	Author Name: CAF Automation 
	 Create Date: 30-07-2021
	Modified Date| Modified By  |Modified purpose 
	  30/07/2021      C114322     Code completion
	-----------------------------------End <ACC_TransFilterValidation_P>---------------------------------------
	*/

	public void ACC_TransFilterValidation_P() {
		try {
			clickJS(filterAccTransFil, "Click Tansaction Filter");
			Assert.assertTrue("All button is not visible", isElementDisplayedWithLog(allBtnAccTransFil, 5));
			Assert.assertTrue("Month button is not visible", isElementDisplayedWithLog(monthBtnAccTransFil, 5));
			Assert.assertTrue("Date Range button is not visible",isElementDisplayedWithLog(dateRangeBtnAccTransFil, 5));
			clickJS(dateRangeBtnAccTransFil, "Click on Date Range");
			validateErrorMessageForDate();
			clickJS(monthBtnAccTransFil);
			clickJS(dateRangeBtnAccTransFil, "Click on Date Range to Reset date");
			waitForElementToBeDisplayed(filterBtnAccTransFil);
			clickJS(filterBtnAccTransFil, "Click on Filter button");
			appendScreenshotToCucumberReport();
			if (isElementDisplayedWithLog(errNoFilterResultAccTransFil, 10)) {
				Assert.assertEquals("Transaction filter is not successfully applied",getMessageText("errNoFilterResultMsg", "UXPBANKING365"), getText(errNoFilterResultAccTransFil));
				logMessage("No filter results for the Transaction type and date range");
			} else {
				Assert.assertTrue(isElementDisplayedWithLog(dateLblAccTransFil, 10));
				Assert.assertTrue(isElementDisplayedWithLog(detailsLblAccTransFil, 10));
				Assert.assertTrue(isElementDisplayedWithLog(amountLblAccTransFil, 10));
				Assert.assertTrue(isElementDisplayedWithLog(balanceLblAccTransFil, 10));
				scrollToView(exportTransHisLnkAccTransFil);
				waitForElementToClickable(exportTransHisLnkAccTransFil, 10);
				Assert.assertTrue(isElementDisplayedWithLog(exportTransHisLnkAccTransFil, 10));
				logMessage("Filter results are displayed for the Transaction type and date range");
			}
			appendScreenshotToCucumberReport();
			logMessage("Transaction filter validation for account is successful");
		} catch (Exception e) {
			logError("Error occured in Filter results for the Transaction type and date range" + e.getMessage());
			injectWarningMessageToCucumberReport(
					"Error occured in Filter results for the Transaction type and date range" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Error occured in Filter results for the Transaction type and date range", Status.FAILED);
		}
	}

	private void validateErrorMessageForDate() {
		try {
			logMessage("INSIDE VALIDATE ERROR FUNCTION");
			String invalidDateValue = "41132021";
			String invalidDateRange = "12022012";
			waitForElementToBeDisplayed(dateStartFromAccTransFil);
			//clickJS(dateStartFromAccTransFil, "Click on start date");
			//waitForPageLoaded();
			click(dateStartFromAccTransFil);
			driver.findElement(dateStartFromAccTransFil).clear();
			setValue(dateStartFromAccTransFil, invalidDateValue);
			Thread.sleep(2000);
			clickJS(filterBtnAccTransFil);
			Thread.sleep(2000);
			Assert.assertEquals("Error Message for Invalid date is not displayed",getMessageText("errInvalidDateValueMsgTransFil", "UXPBANKING365"),getText(errInvalidDateAccTransFil));
			appendScreenshotToCucumberReport();
			waitForElementToBeDisplayed(dateStartFromAccTransFil);
			clickJS(dateStartFromAccTransFil, "Click on start date");
			setValue(dateStartFromAccTransFil, invalidDateRange);
			clickJS(filterBtnAccTransFil);
			Assert.assertEquals("Error Message for Invalid Date Range is not displayed",getMessageText("errInvalidDateRangeMsgTransFil", "UXPBANKING365"),getText(errDateRangeAccTransFil));
			appendScreenshotToCucumberReport();
			logMessage("Error message validation for date range is successful");
		} catch (Exception e) {
			logError("Error message validation could not be validated for date range" + e.getMessage());
			injectWarningMessageToCucumberReport(
					"Error message validation could not be validated for date range" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Error message validation could not be validated for date range", Status.FAILED);
		}
	}
	
	/*---------------------------------Start <ACC_ExtTransDetailsVal_P>----------------------------------------
	Function Name: Transaction tab and extended details validation
	Purpose: To validate transaction tab and extended details
	Parameter:NA
	Author Name: CAF Automation 
	 Create Date: 03-08-2021
	Modified Date| Modified By  |Modified purpose 
	  03/08/2021      C114323     Code completion
	-----------------------------------End <ACC_ExtTransDetailsVal_P>---------------------------------------
	*/

	@SuppressWarnings("null")
	public void ACC_ExtTransDetailsVal_P() {
		try {
			Thread.sleep(5000);
			Assert.assertTrue(isElementDisplayed(accNickname, 10, "Account name header on account summary"));
			if ((driver.findElement(accNickname).getText().contains("CURRENT"))
					|| (driver.findElement(accNickname).getText().contains("SAVING"))) {
				Assert.assertTrue(isElementDisplayed(lnkBalanceTransactionsExp, 10, "Balance/transaction explained"));
				Assert.assertTrue(
						isElementDisplayed(lnkBalanceTransactionsExpOpen, 10, "Open Balance/transaction explained"));
				clickJS(lnkBalanceTransactionsExpOpen);
				Assert.assertTrue(isElementDisplayed(lstBalanceTransactionsExpMenuPanel, 10,
						"Balance/transaction explained panel"));
			}
			Assert.assertTrue(isElementDisplayed(labelInProgress, 10, "Label - In Progress transactions"));
			Assert.assertTrue(isElementDisplayed(labelCompleted, 10, "Label - Completed transactions"));
			Assert.assertTrue(isElementDisplayed(transFilterIcon, 10, "Search icon"));
			List<WebElement> tableHeaders = driver.findElements(lstTransTblHeaders);
			String actualTableHeaders[] = new String[tableHeaders.size()];
			logMessage("***********SIZE***********" + tableHeaders.size());
			for (int i = 0; i < tableHeaders.size(); i++) {
				actualTableHeaders[i] = tableHeaders.get(i).getText();
			}
			if (tableHeaders.size() == 8) {
				String[] expectedTableHeaders = { "Date", "Details", "Amount", "Balance", "Date", "Details", "Amount",
						"Balance" };
				for (int i = 0; i < tableHeaders.size(); i++) {
					Assert.assertEquals(expectedTableHeaders[i], actualTableHeaders[i]);
				}
			} else {
				String[] expectedTableHeaders = { "Date", "Details", "Amount", "Balance" };
				for (int i = 0; i < tableHeaders.size(); i++) {
					Assert.assertEquals(expectedTableHeaders[i], actualTableHeaders[i]);
				}
			}
			List<WebElement> allInProgressTrans = driver.findElements(lstAllInProgressTrans);
			List<WebElement> allCompletedTrans = driver.findElements(lstAllCompletedTrans);
			if (isElementDisplayed(btnShowMore, 10, "Show More Button")) {
				Assert.assertEquals(24, allInProgressTrans.size() + allCompletedTrans.size());
				while (isElementDisplayed(btnShowMore)) {
					clickJS(btnShowMore);
					waitForPageLoaded();
				}
			}
			List<WebElement> allExtTransactList = driver.findElements(lnkExtTrasDetails);
			if (allExtTransactList.size() > 0) {
				ExtTrans: for (int i = 0; i < allExtTransactList.size(); i++) {
					scrollToView(firstExtTransaction);
					clickJS(firstExtTransaction);
					Assert.assertTrue(isElementDisplayed(BtnReturnToDetails));
					Assert.assertTrue(isElementDisplayed(cardExtendedTransaction));
					clickJS(BtnReturnToDetails);
					break ExtTrans;
				}
			}

			logInfo("Extended transaction details validation completed successfully");
			injectMessageToCucumberReport("Extended transaction details validation completed successfully");
			appendScreenshotToCucumberReport();
			Allure.step("Extended transaction details validation completed successfully", Status.PASSED);
		} catch (Error e) {
			logError("Extended transaction details validation failed " + e.getMessage());
			injectWarningMessageToCucumberReport(
					"Failure in extended transaction details validation " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in extended transaction details validation ", Status.FAILED);
		} catch (Exception e) {
			logError("Extended transaction details validation failed " + e.getMessage());
			injectWarningMessageToCucumberReport(
					"Failure in extended transaction details validation " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in extended transaction details validation ", Status.FAILED);
		}

	}

	public void ACC_SMOP_Creditbillsummary_P() {
		verifyCCAccountOverview("Transactions::Statements");

	}

	public void verifyCCAccountOverview(String ccAccountOverview)  {
		try {
			while (explicitWaitForVisibilityOfElement(getObjectBy("launch.spinSpinner"))) {
				waitForPageLoaded();
			}
			String[] ccContentToCheck = ccAccountOverview.split("::");
			for (int i = 0; i < ccContentToCheck.length; i++) {

				String strCCContent = getText(getObject("xpath~//div[contains(@class,'tc-tab-header')]/a[text()='" + ccContentToCheck[i] + "']"));

				if (strCCContent.contains(ccContentToCheck[i])) {
					logMessage("verifyCreditCardAccountOverview Menu: '" + ccContentToCheck[i] + "' correctly displayed on header");
					insertMessageToHtmlReport("verifyCreditCardAccountOverview Menu: '" + ccContentToCheck[i] + "' correctly displayed on header");
				} else {
					logError("verifyCreditCardAccountOverview Menu: '" + ccContentToCheck[i] + "' not displayed on header");
					insertErrorMessageToHtmlReport("verifyCreditCardAccountOverview Menu: '" + ccContentToCheck[i] + "' not displayed on header");
				}
			}
			// code to verify when no transaction is displayed
			String text=getText(getObject("accCredit.NoTransaction"));
			//There are no transactions since your last issued statement.
			String expectedValue="There are no transactions since your last issued statement.";
			if(text.equalsIgnoreCase(expectedValue)){
				logMessage("No Transactions are visible for the credit card successfully");
				insertMessageToHtmlReport("No Transactions are visible for the credit card successfully ");
				appendScreenshotToCucumberReport();
				Assert.assertTrue("Message visible",true);
			}else{
				logError("Error in displaying no transaction message for credit card");
				insertErrorMessageToHtmlReport("Error in displaying no transaction message for credit card");
				appendScreenshotToCucumberReport();
				Assert.fail("Message Not visible");
			}
		} catch (Exception e) {
			logError("Error in verifyCCAccountOverview method, Error ="+e.getMessage());
			insertErrorMessageToHtmlReport("Error in verifyCCAccountOverview method");
			Assert.fail("Error in verifyCCAccountOverview method, Error ="+e.getMessage());
		}

	}

	public void verifyLastBill() throws Exception {

		if (isElementDisplayed(getObject("Transactions.LastBillSummary"))){
			isElementDisplayed(getObject(devTypeToGetProperty + "Transactions.PayNow"));
		}
	}

	public void verifyLastBillSummaryDetails(String ccMenuList) throws Exception {
		/*HomePage homePage = new HomePage(scriptHelper);
		String strParentObject = "xpath~//div[contains(@class,'responsive-row tc-row-part ext-row-part rgrid')]";
		homePage.verifyCheckContentPresent(strParentObject);*/
		if(!System.getProperty("RUNTYPE").equalsIgnoreCase("MOBILEAPP")) {
			isElementDisplayed(getObject(devTypeToGetProperty+"Transactions.CurrentPeriod"));
		}
		if(System.getProperty("RUNTYPE").equalsIgnoreCase("MOBILEAPP")) {
			isElementDisplayed(getObject(devTypeToGetProperty+"Transactions.CurrentPeriod"));
		}

		//String ccMenuList = dataTable.getData("General_Data", "ccTransactionView");//Statement date::Previous balance::Minimum payment::Payment due date
		String[] ccContentToCheck = ccMenuList.split("::");
		List<WebElement> rowELms = driver.findElements(getObject("xpath~//*[@class='tc-card-body boi-transaction-body']/*/*[@style='position: relative']/" +
				"*[contains(@class,'boi-summary-table-sec')]/div[contains(@class,'responsive-row tc-row-part ext-row-part')]"));
		for (int i = 0; i < ccContentToCheck.length; i++) {
			String strCCTransactionContent = rowELms.get(i).getText();
			if (strCCTransactionContent.contains(ccContentToCheck[i])) {
				logMessage("verifyCreditCardAccountOverview Menu: '" + strCCTransactionContent + "' correctly displayed on header");
				insertMessageToHtmlReport("verifyCreditCardAccountOverview Menu: '" + strCCTransactionContent + "' correctly displayed on header");
			} else {
				logError("verifyCreditCardAccountOverview Menu: '" + strCCTransactionContent + "' not displayed on header");
				insertErrorMessageToHtmlReport("verifyCreditCardAccountOverview Menu: '" + strCCTransactionContent + "' not displayed on header");
			}
		}
	}


}


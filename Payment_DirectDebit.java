
package com.boi.grp.pageobjects.Payments;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.DataLibrary;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

// Class Performing Direct Debit Functionality 
public class Payment_DirectDebit extends BasePageForAllPlatform {
	// ********************************************* UNPOPULATED
	// *********************************************
	public UIResusableLibrary cardsFunctional;
	public DataLibrary dataLibrary;
	public Payment_ManagePaymentPage mngPaymentPg;
	By heading = getObject("pd.heading");
	By linkCancelDirectDebit = getObject("pd.linkCancelDirectDebit");
	By linkUnpopulated = getObject("pd.linkCancelDirectDebit");
	By linkCancelDDCont = getObject("pd.linkCancelDDCont");
	By imgSEPADirectDebitService = getObject("pd.imgSEPADirectDebitService");
	By divSEPATip = getObject("pd.divSEPATip");
	By inputCreditName = getObject("pd.inputCreditName");
	By inputCreditId = getObject("pd.inputCreditId");
	By inputCreditUmr = getObject("pd.inputCreditUmr");
	By dropdownSEPADirectDebitService = getObject("pd.dropdownSEPADirectDebitService");
	By lastPayment = getObject("pd.lastPayment");
	By radioCancelDirectDebit = getObject("pd.radioCancelDirectDebit");
	By inputSEPAFname = getObject("pd.inputSEPAFname");
	By inputSEPALname = getObject("pd.inputSEPALname");
	By radioscreenvalueYesNo = getObject("pd.radioscreenvalueYesNo");
	By radiovalueYesNo = getObject("pd.radiovalueYesNo");
	By linkCancelDirectPh = getObject("pd.linkCancelDirectPh");
	By btnDirectDebitConti = getObject("pd.btnDirectDebitConti");
	By btnDirectDebitConfirm = getObject("pd.btnDirectDebitConfirm");
	By btnDirectDebitBacktohome = getObject("pd.btnDirectDebitBacktohome");
	By contactEmail = getObject("pd.contactEmail");
	By textContactEmail = getObject("pd.textContactEmail");
	By selectdropdwn = getObject("pd.selectdropdwn");
	// ********************************************* REACTIVE
	// *********************************************
	By dropReactNext = getObject("pd.dropReactNext");
	By btnReactNext = getObject("pd.btnReactNext");
	By btnReactCont = getObject("pd.btnReactCont");
	By lblReactCrName = getObject("pd.lblReactCrName");
	By txtReactCrName = getObject("pd.txtReactCrName");
	By lblReactCrId = getObject("pd.lblReactCrId");
	By txtReactCrId = getObject("pd.txtReactCrId");
	By lblReactUmr = getObject("pd.lblReactUmr");
	By txtReactUmr = getObject("pd.txtReactUmr");
	By optReactIban = getObject("pd.optReactIban");
	By valReactIban = getObject("pd.valReactIban");
	By txtReactAmtpay = getObject("pd.txtReactAmtpay");
	By btnReactDate = getObject("pd.btnReactDate");
	By valReactDate = getObject("pd.valReactDate");
	By lblReactFname = getObject("pd.lblReactFname");
	By txtReactFname = getObject("pd.txtReactFname");
	By lblReactLname = getObject("pd.lblReactLname");
	By txtReactLname = getObject("pd.txtReactLname");
	By lblReactPh = getObject("pd.lblReactPh");
	By txtReactPh = getObject("pd.txtReactPh");
	By txtReactEmail = getObject("pd.txtReactEmail");
	By btnReactNextCont = getObject("pd.btnReactNextCont");
	By btnReactConfirm = getObject("pd.btnReactConfirm");
	By btnReactBackHome = getObject("pd.btnReactBackHome");
	// ********************************************* REFUSE
	// *********************************************
	By dropRefusNext = getObject("pd.dropRefusNext");
	By btnRefuseNext = getObject("pd.btnRefuseNext");
	By btnRefuseCont = getObject("pd.btnRefuseCont");
	By lblRefuseCrName = getObject("pd.lblRefuseCrName");
	By txtRefuseCrName = getObject("pd.txtRefuseCrName");
	By lblRefuseCrId = getObject("pd.lblRefuseCrId");
	By txtRefuseCrId = getObject("pd.txtRefuseCrId");
	By lblRefuseUmr = getObject("pd.lblRefuseUmr");
	By txtRefuseUmr = getObject("pd.txtRefuseUmr");
	By optRefuseIban = getObject("pd.optRefuseIban");
	By valRefuseIban = getObject("pd.valRefuseIban");
	By txtRefuseAmtpay = getObject("pd.txtRefuseAmtpay");
	By btnRefuseDate = getObject("pd.btnRefuseDate");
	By valRefuseDate = getObject("pd.valRefuseDate");
	By lblRefuseFname = getObject("pd.lblRefuseFname");
	By txtRefuseFname = getObject("pd.txtRefuseFname");
	By lblRefuseLname = getObject("pd.lblRefuseLname");
	By txtRefuseLname = getObject("pd.txtRefuseLname");
	By lblRefusePh = getObject("pd.lblRefusePh");
	By txtRefusePh = getObject("pd.txtRefusePh");
	By txtRefuseEmail = getObject("pd.txtRefuseEmail");
	By btnRefuseNextCont = getObject("pd.btnRefuseNextCont");
	By btnRefuseConfirm = getObject("pd.btnRefuseConfirm");
	By btnRefuseBackHome = getObject("pd.btnRefuseBackHome");
	By refuseTextContactEmail = getObject("pd.refuseTextContactEmail");

	public Payment_DirectDebit(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cardsFunctional = new UIResusableLibrary(driver);
		mngPaymentPg = new Payment_ManagePaymentPage(driver);
		dataLibrary = new DataLibrary();
	}

	/*---------------------------------Start <PD_DirectDebit>----------------------------------------
	Function Name: PD_DirectDebit
	Purpose: Unpopulated Detail Validation
	Parameter:Payment Option
	Author Name: CAF Automation 
	 Create Date: 27-05-2021
	Modified Date| Modified By  |Modified purpose 
	 15/06/2021      C112253       Code completion
	-----------------------------------End <PD_DirectDebit>---------------------------------------
	*/

	public void PD_DirectDebit(String directDebitOption) throws Exception {
		logMessage("DIRECT DEBIT OPTION TYPE : " + directDebitOption);
		try {

			boolean bFlagDirectDebit = false;
			switch (directDebitOption.toUpperCase()) {
			case "UNPOPULATED":

				if (isElementDisplayed(linkUnpopulated)) {
					clickJS(linkUnpopulated);
					PD_UNPOPULATED_DirectDebit();
					bFlagDirectDebit = true;
				} else {
					bFlagDirectDebit = false;
				}
				break;
			case "REACTIVATE":
				if (isElementDisplayed(dropReactNext)) {
					clickJS(dropReactNext);
					PD_REACTIVATE_DirectDebit();
					bFlagDirectDebit = true;
				} else {
					bFlagDirectDebit = false;
				}
				PD_REACTIVATE_DirectDebit();
				break;

			case "EXTENDTRANSACTION":
				// if (isElementDisplayed(linkExtendTransaction)) {
				// clickJS(linkExtendTransaction);
				// PD_EXTENDTRANSACTION_DirectDebit();
				// bFlagDirectDebit = true;
				// } else {
				// bFlagDirectDebit = false;
				// }
				break;

			case "REFUSE":
				if (isElementDisplayed(dropRefusNext)) {
					clickJS(dropRefusNext);
					PD_REFUSE_DirectDebit();
					bFlagDirectDebit = true;
				} else {
					bFlagDirectDebit = false;
				}
				break;
			}
			if (bFlagDirectDebit == true) {
				logMessage(directDebitOption + "Direct Debit option selected successfully");
				injectMessageToCucumberReport("Verify Direct Debit successfully");
				Allure.step("Verify Direct Debit Successfully ", Status.PASSED);
				appendScreenshotToCucumberReport();
			} else {
				logError("Error Occured inside PD_DirectDebit ");
				injectWarningMessageToCucumberReport("Failure in Verify Direct Debit option ");
				Allure.step("Verify  NOT Successful ", Status.FAILED);
				appendScreenshotToCucumberReport();
			}
		} catch (Exception e) {
			logError("Error Occured inside PD_DirectDebit " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify Direct Debit option " + e.getMessage());
			Allure.step("Verify  NOT Successful ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	// verify cancel direct debit
	private void PD_UNPOPULATED_DirectDebit() {
		try {
			//
			clickJS(linkCancelDDCont, "Direct Debit -- click on Continue");
			isElementDisplayed(imgSEPADirectDebitService, 10);
			isElementDisplayed(divSEPATip);
			waitForElementToBeDisplayed(inputCreditName);
			setValue(inputCreditName, generateRandomString());
			waitForElementToBeDisplayed(inputCreditId);
			setValue(inputCreditId, dataLibrary.generateRandomString(15));
			waitForElementToBeDisplayed(inputCreditUmr);
			setValue(inputCreditUmr, generateRandomString());
			clickJS(dropdownSEPADirectDebitService, "Direct Debit -- click on dropdown");
			clickJS(selectdropdwn, "Direct Debit -- click on dropdown value");
			selectValueFromList(dropdownSEPADirectDebitService, "CURRENT ACC ~ 2929");// TODO
																						// Random_selection
			setValue(lastPayment, generateRandomNumber(5));
			clickJS(radioCancelDirectDebit, "Direct Debit -- click on radio btn");
			setValue(inputSEPAFname, generateRandomString());
			setValue(inputSEPALname, generateRandomString());
			String phNo = "0982604523";// TODO replace with library method once
										// it is approved
			setValue(linkCancelDirectPh, phNo);
			clickJS(radiovalueYesNo, "Direct Debit -- click on radio btn");
			clickJS(radioscreenvalueYesNo, "Direct Debit -- click on radio btn");
			clickJS(contactEmail);
			waitForElementToBeDisplayed(textContactEmail);
			setValue(textContactEmail, dataLibrary.generateRandomEmail());
			clickJS(btnDirectDebitConti, "Direct Debit -- click on direct debit continue btn");
			waitForElementToBeDisplayed(heading);
			String statement = getText(heading);
			Assert.assertEquals("SEPA Direct Debit Services", statement);
			clickJS(btnDirectDebitConfirm, "Direct Debit -- click on confirm btn");
			appendScreenshotToCucumberReport();
			clickJS(btnDirectDebitBacktohome, "Direct Debit -- click on back to home btn");

			logMessage("Verify Direct Debit  successfully in PD_DirectDebit function");
			injectMessageToCucumberReport("Verify Direct Debit successfully");
			Allure.step("Verify Direct Debit Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside PD_DirectDebit()" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify Direct Debit" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Verify Direct Debit NOT Successful" + Status.FAILED);
		}
	}

	// verify reactive direct debit
	private void PD_REACTIVATE_DirectDebit() {
		try {
			String statement = getText(heading);
			clickJS(btnReactNext, "Direct Debit -- click on Next btn");
			clickJS(btnReactCont, "Direct Debit -- click on continue btn");
			setValue(txtReactCrName, generateRandomString());
			setValue(lblReactCrId, dataLibrary.generateRandomString(15));
			setValue(txtReactUmr, generateRandomString());
			clickJS(optReactIban, "Direct Debit -- click on dropdown");
			clickJS(valReactIban, "Direct Debit -- click on dropdown value");
			setValue(txtReactAmtpay, generateRandomNumber(5));
			clickJS(btnReactDate, "Direct Debit -- click on date");
			clickJS(valReactDate, "Direct Debit -- click on date value");
			setValue(txtReactFname, generateRandomString());
			setValue(txtReactLname, generateRandomString());
			String phNo = "0982604523"; // TODO Random_selection
			setValue(txtReactPh, phNo);
			clickJS(radiovalueYesNo, "Direct Debit -- click on radio btn");
			clickJS(contactEmail);
			setValue(txtReactEmail, dataLibrary.generateRandomEmail());
			clickJS(btnDirectDebitConti, "Direct Debit -- click on direct debit continue btn");
			Assert.assertEquals("SEPA Direct Debit Services", statement);
			clickJS(btnDirectDebitConfirm, "Direct Debit -- click on confirm btn");
			appendScreenshotToCucumberReport();
			clickJS(btnDirectDebitBacktohome, "Direct Debit -- click on back to home btn");

			logMessage("Verify Direct Debit  successfully in PD_DirectDebit function");
			injectMessageToCucumberReport("Verify Direct Debit successfully");
			Allure.step("Verify Direct Debit Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside PD_DirectDebit()" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify Direct Debit" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Verify Direct Debit NOT Successful" + Status.FAILED);
		}
	}

	// verify extend transaction direct debit
	private void PD_EXTENDTRANSACTION_DirectDebit() {
		// TODO
	}

	// verify refuse direct debit
	private void PD_REFUSE_DirectDebit() {
		try {

			clickJS(btnRefuseNext, "Direct Debit -- click on Next btn");
			clickJS(btnRefuseCont, "Direct Debit -- click on continue btn");
			setValue(txtRefuseCrName, generateRandomString());
			setValue(txtRefuseCrId, dataLibrary.generateRandomString(15));
			setValue(txtRefuseUmr, generateRandomString());
			clickJS(optRefuseIban, "Direct Debit -- click on dropdown");
			clickJS(valRefuseIban, "Direct Debit -- click on dropdown value");
			selectValueFromList(optRefuseIban, "CURRENT ACC ~ 2929");// TODO
																		// Random_selection
			setValue(txtRefuseAmtpay, generateRandomNumber(5));
			clickJS(btnRefuseDate, "Direct Debit -- click on date");
			clickJS(valRefuseDate, "Direct Debit -- click on date value");
			clickJS(radiovalueYesNo, "Direct Debit -- click on radio btn");
			setValue(txtRefuseFname, generateRandomString());
			setValue(txtRefuseLname, generateRandomString());
			String phNo = "0982604523";// TODO replace with library method once
										// it is approved
			setValue(txtRefusePh, phNo);
			clickJS(radiovalueYesNo, "Direct Debit -- click on radio btn");
			clickJS(radioscreenvalueYesNo, "Direct Debit -- click on radio btn");
			clickJS(contactEmail);
			waitForElementToBeDisplayed(refuseTextContactEmail);
			setValue(refuseTextContactEmail, dataLibrary.generateRandomEmail());
			clickJS(btnDirectDebitConti, "Direct Debit -- click on direct debit continue btn");
			waitForElementToBeDisplayed(heading);
			String statement = getText(heading);
			Assert.assertEquals("SEPA Direct Debit Services", statement);
			clickJS(btnDirectDebitConfirm, "Direct Debit -- click on confirm btn");
			appendScreenshotToCucumberReport();
			clickJS(btnDirectDebitBacktohome, "Direct Debit -- click on back to home btn");

			logMessage("Verify Direct Debit  successfully in PD_DirectDebit function");
			injectMessageToCucumberReport("Verify Direct Debit successfully");
			Allure.step("Verify Direct Debit Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside PD_REFUSE_DirectDebit()" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify Direct Debit" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Verify Direct Debit NOT Successful" + Status.FAILED);
		}
	}
}

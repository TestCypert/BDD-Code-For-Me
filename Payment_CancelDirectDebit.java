package com.boi.grp.pageobjects.Payments;

import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

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

// Class Performing Direct Debit Functionality 
public class Payment_CancelDirectDebit extends BasePageForAllPlatform {
	// ********************************************* UNPOPULATED
	// *********************************************
	public UIResusableLibrary cardsFunctional;
	public DataLibrary dataLibrary;
	public Payment_ManagePaymentPage mngPaymentPg;
	By PaymentsTab = getObject("CancelDirectDebit.PaymentsTab");
	By CancelDirectDebit = getObject("CancelDirectDebit.CancelDirectDebit");
	By RadioPersonal = getObject("CancelDirectDebit.RadioPersonal");
	By RadioBusiness = getObject("CancelDirectDebit.RadioBusiness");
	By FirstName = getObject("CancelDirectDebit.FirstName");
	By Surname = getObject("CancelDirectDebit.Surname");
	By BusinessName = getObject("CancelDirectDebit.BusinessName");
	By Address = getObject("CancelDirectDebit.Address");
	By PostCode = getObject("CancelDirectDebit.PostCode");
	By DOB = getObject("CancelDirectDebit.DOB");
	By ContactNumber = getObject("CancelDirectDebit.ContactNumber");
	By Email = getObject("CancelDirectDebit.Email");
	By AccNo = getObject("CancelDirectDebit.AccNo");
	By SortCode = getObject("CancelDirectDebit.SortCode");
	By Originator = getObject("CancelDirectDebit.Originator");
	By SingleDirectDebit = getObject("CancelDirectDebit.SingleDirectDebit");
	By MultipleDirectDebit = getObject("CancelDirectDebit.MultipleDirectDebit");
	By UniqueMandateRef = getObject("CancelDirectDebit.UniqueMandateRef");
	By NextButton = getObject("CancelDirectDebit.NextButton");
	By Conf1 = getObject("CancelDirectDebit.Confirmation1");
	By Conf2 = getObject("CancelDirectDebit.Confirmation2");
	By SubmitBut = getObject("CancelDirectDebit.SubmitButton");
	By FinalMsg = getObject("CancelDirectDebit.FinalMessage");

	public Payment_CancelDirectDebit(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cardsFunctional = new UIResusableLibrary(driver);
		mngPaymentPg = new Payment_ManagePaymentPage(driver);
		dataLibrary = new DataLibrary();
	}

	public void PD_CancelDirectDebit(String CancelDirectDebitOption, String MultipleDirectDebitOption)
			throws Exception {
		try {
			boolean bFlagCancelDirectDebit = false;
			clickJS(PaymentsTab, "Main page -> PaymentsTab");
			clickJS(CancelDirectDebit, "Payments Tab -> Canced Direct Debit option");
			Set<String> WinHandles = driver.getWindowHandles();
			String Parent = driver.getWindowHandle();
			java.util.Iterator<String> Windowiterator = WinHandles.iterator();
			while (Windowiterator.hasNext()) {
				String Child = Windowiterator.next();
				if (!Child.equals(Parent)) {
					driver.switchTo().window(Child);
					switch (CancelDirectDebitOption.toUpperCase()) {
					case "PERSONAL":
						if (!bFlagCancelDirectDebit) {
							PersonalCancelDirectDebitForm_P();
							CommonFieldForm_P();
							switch (MultipleDirectDebitOption.toUpperCase()) {
							case "SINGLE":
								SingleDirectDebit_P();
								break;
							case "MULTIPLE":
								MultipleDirectDebit_P();
								break;
							}
							bFlagCancelDirectDebit = CompleteProcess_P();
						} else {
							bFlagCancelDirectDebit = false;
						}
						break;
					case "BUSINESS":
						if (!bFlagCancelDirectDebit) {
							BusinessCancelDirectDebitForm_P();
							CommonFieldForm_P();
							switch (MultipleDirectDebitOption.toUpperCase()) {
							case "SINGLE":
								SingleDirectDebit_P();
								break;
							case "MULTIPLE":
								MultipleDirectDebit_P();
								break;
							}
							bFlagCancelDirectDebit = CompleteProcess_P();
						} else {
							bFlagCancelDirectDebit = false;
						}
						break;
					}
				}
				driver.switchTo().window(Parent);
			}
			if (bFlagCancelDirectDebit = true) {
				logMessage("Cancel Direct Debit is completed successfully");
				appendScreenshotToCucumberReport();
				insertMessageToHtmlReport("Success in Cancel Direct Debit ");
				Allure.step("Success in Cancel Direct Debit", Status.PASSED);
			} else {
				logMessage("Cancel Direct Debit is Unsuccessful");
				appendScreenshotToCucumberReport();
				insertMessageToHtmlReport("Failure in Cancel Direct Debit ");
				Allure.step("Failure in Cancel Direct Debit", Status.FAILED);
			}
		} catch (Exception e) {
			logError("Error Occured inside PD_CancelDirectDebit " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Cancel Direct Debit option " + e.getMessage());
			Allure.step("Cancel Direct Debit NOT Successful ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	private void PersonalCancelDirectDebitForm_P() {
		try {
			clickJS(RadioPersonal, "CancelDirectDebit form page -> Personal radio button");
			String RanFName = generateRandomString();
			String RanLName = generateRandomString();
			String DateOfBirth = dataLibrary.generateRabdomDate();
			setValue(FirstName, RanFName);
			setValue(Surname, RanLName);
			setValue(DOB, DateOfBirth);
		} catch (Exception e) {
			System.out.println("Error in Personal Naming convention " + e);
		}
	}

	private void BusinessCancelDirectDebitForm_P() {
		try {
			Thread.sleep(3000);
			clickJS(RadioBusiness, "CancelDirectDebit form page -> Business radio button");
			Thread.sleep(2000);
			String RanBusinessName = generateRandomString();
			setValue(BusinessName, RanBusinessName);
		} catch (Exception e) {
			System.out.println("Error Occured inside Single Direct Debit selection " + e);
		}
	}

	private void CommonFieldForm_P() {
		try {
			String Addr = generateRandomString();
			String PostalCode = generateRandomNumber(6);
			String ContactNo = generateRandomNumber(10);
			String EmailAddress = dataLibrary.generateRandomEmail();
			String BOIAccNum = generateRandomNumber(8);
			String BOISortCode = generateRandomNumber(6);
			String OriginatorName = generateRandomString();
			setValue(Address, Addr);
			setValue(PostCode, PostalCode);
			setValue(ContactNumber, ContactNo);
			setValue(Email, EmailAddress);
			setValue(AccNo, BOIAccNum);
			setValue(SortCode, BOISortCode);
			setValue(Originator, OriginatorName);
			clickJS(Conf1, "CancelDirectDebit -> First Confirmation check box");
			clickJS(Conf2, "CancelDirectDebit -> Second Confirmation check box");
		} catch (Exception e) {
			System.out.println("Error Occured inside Single Direct Debit selection " + e);
		}
	}

	private void SingleDirectDebit_P() {
		try {
			clickJS(SingleDirectDebit, "CancelDirectDebit form page -> Do you have multiple direct debit no option");
		} catch (Exception e) {
			System.out.println("Error Occured inside Single Direct Debit selection " + e);
		}
	}

	private void MultipleDirectDebit_P() {
		try {
			clickJS(MultipleDirectDebit, "CancelDirectDebit form page -> Do you have multiple direct debit no option");
			String UniqueMandateRefNum = generateRandomNumber(6);
			setValue(UniqueMandateRef, UniqueMandateRefNum);
		} catch (Exception e) {
			System.out.println("Error Occured inside Multiple Direct Debit selection " + e);
		}
	}

	private boolean CompleteProcess_P() {
		boolean val = false;
		try {
			appendScreenshotToCucumberReport();
			clickJS(NextButton, "CancelDirectDebit form page -> Click on Next Page");
			appendScreenshotToCucumberReport();
			clickJS(SubmitBut);
			appendScreenshotToCucumberReport();
			if (isElementDisplayed(FinalMsg)) {
				val = true;
			} else {
				val = false;
			}
		} catch (Exception e) {

		}
		return val;
	}
}

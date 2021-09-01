package com.boi.grp.pageobjects.Payments;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.DataLibrary;
import io.qameta.allure.model.Status;
import io.qameta.allure.Allure;
import com.boi.grp.utilities.UIResusableLibrary;
import com.boi.grp.pageobjects.Payments.Payment_ManagePaymentPage;
import com.boi.grp.pageobjects.Payments.Payment_TransBetweenAccPage;

public class Payment_FutureDatedPayments extends BasePageForAllPlatform {
	public UIResusableLibrary cardsFunctional;
	public DataLibrary dataLibrary;
	public Payment_ManagePaymentPage mngPaymentPg;
	public Payment_TransBetweenAccPage transPaymentPg;
	By paymenttab = getObject("w.home.tabPayments");
	By allPayments = getObject("pd.allFDPPayments");
	By backbtn = getObject("pd.backbtn");
	String info = "";

	public Payment_FutureDatedPayments(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cardsFunctional = new UIResusableLibrary(driver);
		mngPaymentPg = new Payment_ManagePaymentPage(driver);
		transPaymentPg = new Payment_TransBetweenAccPage(driver);
		dataLibrary = new DataLibrary();
	}

	/*---------------------------------Start <PD_FDPsPayee>----------------------------------------
	 Function Name: PD_TransBetweenAcc
	 Argument Name:
	 Purpose: UB-337_Payments_Create+EditFutureDatedPayment
	 Author Name: CAF Automation 
	 Create Date: 12-07-2021
	 Modified Date| Modified By  |Modified purpose 
	  12/07/2021      C112253     Code update
	 -----------------------------------End <PD_FDPsPayee>----------------------------------------*/
	public void PD_FDPsPayee(String validate, String payOptionType, String userType, String fromAcc, String toAcc) {
		try {
			switch (payOptionType) {
			case "TRANSFER_BETWEEN_MY_ACCOUNTS":
				mngPaymentPg.PD_PaymentOptions(payOptionType);
				info = transPaymentPg.PD_TransBetweenAcc(userType, fromAcc, toAcc);
				Assert.assertNotNull(info);
				Thread.sleep(20000);
				mngPaymentPg.PD_PaymentOptions("FUTURE_DATED_PAYMENTS");
				if (validate.equalsIgnoreCase("CANCEL")) {
					CancelPayment(info);

				} else {
					ViewPayment(info);
				}
				break;
			case "SEND_MONEYORPAY_BILL":
				// TODO
				break;
			case "SEPA PAYEE":
				// TODO
				break;

			}

		} catch (Exception e) {
			logError("Error Occured inside PD_FDPsPayee_P()" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Validation of FutureDatedPayments" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Validation of FutureDatedPayments NOT Successful" + Status.FAILED);
		}
	}

	// Perform CancelPayment Operation.
	public void CancelPayment(String info) {
		try {
			String Pending = "Pending";
			ArrayList<String> payee = new ArrayList<>();
			ArrayList<Integer> order = new ArrayList<>();
			List<WebElement> allElement = driver.findElements(allPayments);
			for (WebElement element : allElement) {
				String pay = element.getText();
				payee.add(pay);
			}

			// To fetch the position of information to be cancelled.

			int i = 0;
			for (String pays : payee) {
				i++;
				String check = pays.replaceAll("[\r\n]+", " ");
				if (check.equalsIgnoreCase(Pending + " " + info)) {
					int num = i;
					order.add(num);
					break;
				}
			}

			By selection = getObject("pd.selection", order.get(0));
			clickJS(selection);

			By payment = getObject("pd.status", order.get(0));
			String pending = getText(payment);
			Assert.assertEquals("Checking Pending status", "Pending", pending);

			By cancelPayment = getObject("pd.cancelPayment", order.get(0));
			clickJS(cancelPayment);

			By closebtn = getObject("pd.closebutton", order.get(0));
			clickJS(closebtn);
			clickJS(cancelPayment);
			clickJS(backbtn);
			clickJS(cancelPayment);

			By yesCancelPayment = getObject("pd.yesbutton", order.get(0));
			clickJS(yesCancelPayment);

			By cancelSelection = getObject("pd.selection", order.get(0));
			clickJS(cancelSelection);
			appendScreenshotToCucumberReport();

			By cancelStatus = getObject("pd.status", order.get(0));
			String status = getText(cancelStatus);
			Assert.assertEquals("Checking Cancelled status", "Cancelled", status);
			logMessage("Validation of CancelPayments done successfully");
			injectMessageToCucumberReport("Validation of CancelPayments done successfully");
			Allure.step("Validation of CancelPayments done Successfully ", Status.PASSED);

		} catch (Exception e) {
			logError("Error Occured inside CancelPayment(String)" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Validation of CancelPayments" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Validation of CancelPayments NOT Successful" + Status.FAILED);
		}
	}

	// Perform ViewPayment Operation.
	public void ViewPayment(String info) {
		try {
			String Pending = "Pending";
			appendScreenshotToCucumberReport();
			ArrayList<String> payee = new ArrayList<>();
			ArrayList<Integer> order = new ArrayList<>();
			List<WebElement> allElement = driver.findElements(allPayments);
			for (WebElement element : allElement) {
				String pay = element.getText();
				payee.add(pay);
			}

			// To fetch the position of information to be viewed.

			int i = 0;
			for (String pays : payee) {
				i++;
				String check = pays.replaceAll("[\r\n]+", " ");
				if (check.equalsIgnoreCase(Pending + " " + info)) {
					int num = i;
					order.add(num);
					break;
				}
			}

			By selection = getObject("pd.selection", order.get(0));
			clickJS(selection);
			appendScreenshotToCucumberReport();
			By payment = getObject("pd.status", order.get(0));
			String pending = getText(payment);
			Assert.assertEquals("Checking Pending status", "Pending", pending);
			logMessage("Validation of ViewPayments done successfully");
			injectMessageToCucumberReport("Validation of ViewPayments done successfully");
			Allure.step("Validation of ViewPayments done Successfully ", Status.PASSED);
			
		} catch (Exception e) {
			logError("Error Occured inside ViewPayment(String)" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Validation of ViewPayment" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Validation of ViewPayment NOT Successful" + Status.FAILED);
		}
	}
}

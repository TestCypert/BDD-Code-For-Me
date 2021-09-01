package com.boi.grp.pageobjects.Payments;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.poi.ss.formula.functions.CalendarFieldFunction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.pageobjects.Cards.CCfunctionalComponent;
import com.boi.grp.utilities.DataLibrary;
import com.boi.grp.utilities.UIResusableLibrary;

import freemarker.core.ParseException;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class Payment_SendMoneyPage extends BasePageForAllPlatform {
	public UIResusableLibrary cafFunctional;
	public CCfunctionalComponent ccFunctional;
	public DataLibrary dataLib;
	public Payment_ManagePayeePage manangepaypg;
	
	By sendmoneyselAcc= getObject("SendMoney.lstSelectAccount");
	By sendmoneyselAllAcc= getObject("SendMoney.lstAllaccoptions");
	By sendmoneyAmttxt = getObject("SendMoney.txtbxAmountField");
	By sendmoneyselPay = getObject("SendMoney.lstSelectPayee");
	By sendmoneyselAllPay = getObject("SendMoney.lstAllpayeeoptions");
	By sendmoneybtnToday = getObject("SendMoney.btnToday");
	By sendmoneylblvisibletoPayee = getObject("SendMoney.lblVisibletoPayee");
	By sendmoneytxtRef = getObject("SendMoney.txtReference");
	By sendmoneyRefid = getObject("SendMoney.txtRefNo");
	By sendmoneydescrtxt = getObject("SendMoney.txtDescription");
	By sendmoneycontinueBtn = getObject("SendMoney.btnContinue");
	By moreAboutDailyLimitLink = getObject("pd.moreAboutDailyLimit");
	By successMessage = getObject("pd.imgSuccess");
	By Confirmbutton = getObject("pd.btnConfirmPIN");
	By labelAvailableFunds = getObject("SendMoney.labelAvailableFunds");
	By labelArrangedOverdraftRemaining = getObject("SendMoney.labelArrangedOverdraftRemaining");
	By labelPayFromAvailableFunds = getObject("SendMoney.labelPayFromAvailableFunds");
	By labelPayFromArrangedOverdraftRemaining = getObject("SendMoney.labelPayFromArrangedOverdraftRemaining");			
	
	public Payment_SendMoneyPage(WebDriver driver) {
		super(driver);
		cafFunctional = new UIResusableLibrary(driver);
		ccFunctional = new CCfunctionalComponent(driver);
		dataLib = new DataLibrary();
		manangepaypg = new Payment_ManagePayeePage(driver);
		PageFactory.initElements(driver, this);
	}

	/*---------------------------------Start <PD_StandingOrder>----------------------------------------
	 Function Name: PD_SendMoneyOrPayBill
	 Argument :ordertype and usertype
	 Purpose: Navigate to sendmoney page ,validate the screen
	 Author Name: CAF Automation 
	 Create Date:  06/07/2021 
     -----------------------------------End <PD_StandingOrder>--------------------------------------- */
	public void PD_SendMoneyOrPayBill(String userType,String processtype,String cardtype) {
		try {
			ValidateSendMoneyScreen();					
			switch (processtype.toUpperCase()) {
			case "PAYCREDITBILL":													
					PD_PayCreditBill_P(userType,cardtype);	
			    break;				
			case "CREDITBILLSUMMARY":
				//TODO code for CREDITBILLSUMMARY

				break;
			case "PAYNOW":
				//TODO code PAYNOW

				break;
			case "ARRANGE_OVERDRAFT":
				clickJS(sendmoneyselAcc,"Select pay from");	
				selectValueFromDropDown(sendmoneyselAllAcc, "CURRENT ACCOUNT " + cardtype);
				PD_ArrangeOverdraft_P(userType);
				break;
			case "FORM_VALIDATION":
				String formType = System.getProperty("formType");
				PD_SMOP_FormValidation_P(formType);
				//TODO code ARRANGEOVFORM_VALIDATIONERDRAFT

				break;
			}

		} catch (Exception e) {
			logError("Error Occured inside  "+ processtype + e.getMessage());
			Allure.step("Failure in " + processtype, Status.FAILED);
			insertMessageToHtmlReport("Failure in " + processtype + e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}
	/*---------------------------------Start <ValidateSendMoneyScreen>----------------------------------------
	 Function Name: ValidateSendMoneyScreen
	 Argument : NA
	 Purpose: code to validate title of sendmoney page 	 
	 Author Name: CAF Automation 
	 Create Date:  07/07/2021 
	 -----------------------------------End <ValidateSendMoneyScreen>--------------------------------------- */
	public void ValidateSendMoneyScreen() throws InterruptedException {
		String ActualTextHeader = null;
		boolean verifytitle = false;
		
			try {
				if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
					isElementDisplayed(getObject(devTypeToGetProperty + "Payments.lblHeaderSendMoney"));
					ActualTextHeader = getText(getObject(devTypeToGetProperty + "Payments.lblHeaderSendMoney"));
					verifytitle = true;
				} else if (devTypeToGetProperty.equalsIgnoreCase("mw.")) {
					isElementDisplayed(getObject(devTypeToGetProperty + "Payments.lblHeaderSendMoney"));
					ActualTextHeader = getText(getObject(devTypeToGetProperty + "Payments.lblHeaderSendMoney"));
					verifytitle = true;
				} 
				 else if (devTypeToGetProperty.equalsIgnoreCase("tw.")) {
						isElementDisplayed(getObject(devTypeToGetProperty + "Payments.lblHeaderSendMoney"));
						ActualTextHeader = getText(getObject(devTypeToGetProperty + "Payments.lblHeaderSendMoney"));
						verifytitle = true;
				 }
						
			if (verifytitle) {
				Assert.assertEquals("Send Money", ActualTextHeader);
				insertMessageToHtmlReport(
						"SendMoney clicked for " + devTypeToGetProperty + "  is clicked Successfully ");
				appendScreenshotToCucumberReport();
			}
		
		}catch (Exception e) {
			logInfo("*****Header is not matched******");
			Assert.assertFalse("Header is not matched", verifytitle);
			insertMessageToHtmlReport("SendMoney is not clicked Successfully ");
			appendScreenshotToCucumberReport();

		}
	
	}

	/*---------------------------------Start <reviewSendMoneyDetailsOnReviewPage>----------------------------------------
	 Function Name: reviewSendMoneyDetailsOnReviewPage
	 Argument Name: Usertype
	 Purpose:Review bill details if it matchs with the values enter for bill
	 Author Name: CAF Automation 
	 Create Date: 09-07-2021
	 -----------------------------------End <reviewSendMoneyDetailsOnReviewPage>---------------------------------------*/
	
		private void reviewSendMoneyDetailsOnReviewPage(String strAmt,String refid,String descriptiontxt) throws Exception{
			boolean reviewtxtflag = false;
			String actualDailyLimitText = manangepaypg.getVisibleDailyLimitElementText();
			String expectedDailyLimitText = getMessageText("expDailyLimitText","UXPBANKING365");
			System.out.println("Daily limit xpath = " +actualDailyLimitText);
					
			String[] reviewtext = {refid, descriptiontxt};
			for (int i = 0; i < reviewtext.length; i++) {
				if (isElementDisplayed(By.xpath("//span[contains(text(),'" + reviewtext[i] + "')]"), 5)) {
					reviewtxtflag = true;
					logInfo("SetupSOReviewPage - " + reviewtext[i] + "' is displayed on review page ");
					Assert.assertTrue("SetupSOReviewPage - " + reviewtext[i] + "' is displayed on review page ", reviewtxtflag);
				} else {
					Assert.fail("SetupSOReviewPage - " + reviewtext[i] + "' is displayed on review page ");
				}
			appendScreenshotToCucumberReport();
			if(actualDailyLimitText.contains(expectedDailyLimitText)){											//Validate daily limit per day
				Assert.assertTrue("Daily Limit is available",true);
			}			else{
				Assert.fail("Daily Limit is not available");
			}
			waitForElementToClickable(moreAboutDailyLimitLink, 10);			
			manangepaypg.validateMoreAboutDailyLimitLink();
		}
		
		}
		
		/*---------------------------------Start <PD_ArrangeOverdraft_P>----------------------------------------
		 Function Name: PD_ArrangeOverdraft_P
		 Argument Name: Usertype 
		 Purpose: Available funds and overdraft amnt verification for all payees for which arranged overdraft is applicable.
		 Prerequisite: Arranged overdraft should be applicable for user/customer type data passed.
		 Author Name: CAF Automation 
		 Create Date: 15-07-2021
		 -----------------------------------End <PD_ArrangeOverdraft_P>---------------------------------------
		 */
		
		private void PD_ArrangeOverdraft_P(String userType){
			try{
			boolean bFlagAvlfunds = false;
			boolean bFlagArrOvrfunds = false;
			String[] arrUsData = userType.split("_");
			String usertype1=arrUsData[0];	
			String strAmount = generateRandomNumber(2);
			Thread.sleep(30000);		
			isElementDisplayedWithLog(labelAvailableFunds,2);			
            if (isElementDisplayed(labelAvailableFunds)) {
            	isElementDisplayedWithLog(labelPayFromAvailableFunds,2);
				bFlagAvlfunds = true;
				Assert.assertTrue( "Available Funds is expected on screen and is appearing below the Pay from dropdown",bFlagAvlfunds);
			} else {
				bFlagAvlfunds = false;
			}
            if (isElementDisplayed(labelArrangedOverdraftRemaining)) {	
            	isElementDisplayedWithLog(labelPayFromArrangedOverdraftRemaining,2);
            	bFlagArrOvrfunds = true;
				Assert.assertTrue( "Arrange Overdraft is expected on screen and is appearing below the Pay from dropdown",bFlagArrOvrfunds);
			} else {
				bFlagArrOvrfunds = false;
			}         
            if (bFlagAvlfunds == true && bFlagArrOvrfunds == true) {				
				appendScreenshotToCucumberReport();	
				insertMessageToHtmlReport("Arrange Overdraft is expected on screen and is appearing below the Pay from dropdown ");
		        Allure.step("Arrange Overdraft is expected on screen and is appearing below the Pay from dropdown",Status.PASSED);
			
			}
	            			    	
			}
			catch(Exception e){
				logError("Arrange Available/Overdraftverification is not successful - " +e.getMessage());
				injectWarningMessageToCucumberReport("Arrange Available/Overdraft verification is not successful" +e.getMessage());
				appendScreenshotToCucumberReport();
				Allure.step("Arrange Available/Overdraft verification is not successful",Status.FAILED);
	            Assert.fail("Arrange Available/Overdraft verification is not successful" + e.getMessage());
			}
		}
		/*---------------------------------Start <PD_Arrangel_P>----------------------------------------
		 Function Name: PD_PayCreditBill_P
		 Argument Name: Usertype 
		 Purpose: Send money to cardtype [Credit/Commerical/Mortgage] account via sendmoney tab in payments .
		 Prerequisite: Add nickname to any of Current account mapped to the usertype
		 as "CURRENT ACCOUNT" followed by usertype (GB or CR1 or CR2) specified in logindata.prop file and pay to should 
		 have "CREDIT CARD" account.
		 Author Name: CAF Automation 
		 Create Date: 09-07-2021
		 -----------------------------------End <PD_PayCreditBill_P>---------------------------------------
		 */
		
		private void PD_PayCreditBill_P(String userType,String cardtype){
			try{
			String[] arrUsData = userType.split("_");
			String usertype1=arrUsData[0];	
			String strAmount = generateRandomNumber(2);
			clickJS(sendmoneyselAcc,"Select pay from");	
			selectValueFromDropDown(sendmoneyselAllAcc, "CURRENT ACCOUNT " + usertype1);
			Thread.sleep(40000);	
			isElementDisplayedWithLog(sendmoneyselPay, 10);
			clickJS(sendmoneyselPay,"Select pay To");
			selectValueFromDropDown(sendmoneyselAllPay,cardtype);
			waitForElementToClickable(sendmoneyAmttxt,30);
			clickJS(sendmoneyAmttxt," amount field is clicked");
	        setValue(sendmoneyAmttxt, strAmount);	
	        String Refid = getText(sendmoneyRefid);
	        String descripName = getText(sendmoneydescrtxt);
//	        setValue(sendmoneytxtRef, generateRandomString() + "Reference");
	        clickJS(sendmoneybtnToday,"Today is selected");
	        scrollToView(sendmoneycontinueBtn); 
	        clickJS(sendmoneycontinueBtn,"Continue button "); 
	        reviewSendMoneyDetailsOnReviewPage(strAmount,Refid,descripName);		//Review bill details on Review Page
	        clickJS(sendmoneycontinueBtn,"Continue button "); 
	        cafFunctional.EnterPINdetails(cafFunctional.GetvalidPIN(userType));
	        clickJS(Confirmbutton,"Confirm PIN button"); 	        
	        isElementDisplayedWithLog(successMessage,10);		
	        appendScreenshotToCucumberReport();	        
	    	injectMessageToCucumberReport("Pay Credit bill is added successfully");
	    	Assert.assertTrue("Pay Credit bill is added successfully",true);
		    	
			}
			catch(Exception e){
				logError("Pay credit card bill is not added successfully - " +e.getMessage());
				injectWarningMessageToCucumberReport("Pay credit card bill is not added successfully" +e.getMessage());
				appendScreenshotToCucumberReport();
				Allure.step("Pay credit card bill is not added successfully",Status.FAILED);
	            Assert.fail("Pay credit card bill is not added successfully" + e.getMessage());
			}
		}
		
	public void PD_SMOP_FormValidation_P(String formType) throws Exception {
		Payment_ManagePaymentPage payment_managePaymentPage=new Payment_ManagePaymentPage(driver);
		switch (formType.toUpperCase()){
			case "ALL":
				payment_managePaymentPage.formValidationsForAllUsers();
				break;
			case "SEPA_ROI":
				payment_managePaymentPage.sepaReferenceForROI();
				break;
			case "SEPA_UK_CURRENT":
			case "SEPA_UK":
				payment_managePaymentPage.sepaReferenceForUKPayee();
				break;
			case "SEPA":
				payment_managePaymentPage.sepaReferenceForSEPAPayee();
				break;
			case "DOMESTIC_TRANSFER_UK":
				payment_managePaymentPage.domesticTransferUKCustomer();
				break;
		}

	}
		
}

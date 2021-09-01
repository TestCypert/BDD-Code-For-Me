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

public class Payment_TransBetweenAccPage extends BasePageForAllPlatform {
	

	
	public UIResusableLibrary cafFunctional;
	public DataLibrary dataLibrary;
	By transBtwAccLabel = getObject("pd.transBetweenAcclbl");
	By selectTransFromAcc = getObject("pd.sltAccTrasFrom");
	By selectTransToAcc = getObject("pd.sltAccTrasTo");
	By selectTransFromAccAfter = getObject("pd.sltAccTrasAfterFrom");
	By selectTransToAccAfter = getObject("pd.sltAccTrasAfterTo");
	By selectAccTransFromValues =  getObject("pd.selectValueFrom");
	By selectAccTransToValues =  getObject("pd.selectValueTo");
	By selectValueTo = getObject("pd.selectValueTo");
	By continueButton = getObject("pd.btnContinue");
	By confirmButton = getObject("pd.btnConfirmPIN");
	By amountValue = getObject("pd.txtAmount");
	By availableFundText = getObject("pd.msgAvailableFunds");
	By errMsgForAmountField = getObject("pd.errMsgForAmount");
	By errMsgScheduleDateField = getObject("pd.errMsgForDate");
	By radioBtnToday = getObject("pd.radiobtnToday");
	By radioBtnScheduleDate = getObject("pd.radiobtnSceduleFDP");
	By scheduleDate = getObject("pd.sltScheduleDate");
	By amountReviewPg = getObject("pd.txtAmountReview");
	By accFromReviewPg = getObject("pd.txtAccFromReview");
	By accToReviewPg = getObject("pd.txtAccToReview");
	By processTransReviewPg = getObject("pd.txtProcessTransferReview");
	By successMessage = getObject("pd.imgSuccess");
	By backToHomeButton = getObject("pd.btnBackToHome");
	By backToPaymentsButton = getObject("pd.btnBackToPayments");
	By backToHome = getObject("pd.btnBackToHome");
	String finalresponse = "";
	

	
	public Payment_TransBetweenAccPage(WebDriver driver) {                
		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional= new UIResusableLibrary(driver);
		dataLibrary = new DataLibrary();
	}
	
	/*---------------------------------Start <PD_TransferBetweenAccounts>----------------------------------------
	 Function Name: PD_TransBetweenAcc
	 Argument Name:
	 Purpose: UB-309_Payments_TransferBetweenAccounts_ValidateSuccessfulOwnAccountTransfer_NI/GBCustomer
	 Author Name: CAF Automation 
	 Create Date: 24-05-2021
	 Modified Date| Modified By  |Modified purpose 
	  24/05/2021      C114322     Code update
	 -----------------------------------End <PD_TransferBetweenAccounts>----------------------------------------*/
	
	
	public String PD_TransBetweenAcc(String userType,String fromAcc,String toAcc) throws Exception {
		
			try{
				String amount = dataLibrary.generateRandomNumber(1);
				validateCustomerIsOnTransBtwAccPage();
				
				cafFunctional.clickSelectValueFromDropDown(selectTransFromAcc,selectAccTransFromValues,fromAcc);
				validateAvaialbleFundText();					//Validate the 'Available Funds' text for account
				waitForElementToClickable(selectTransToAcc, 10);
		        isElementDisplayedWithLog(selectTransToAcc,5);
				cafFunctional.clickSelectValueFromDropDown(selectTransToAcc,selectAccTransToValues,toAcc);
		        isElementDisplayedWithLog(continueButton,5);
				clickJS(continueButton,"Click on Continue Button");
		        isElementDisplayedWithLog(errMsgForAmountField,5);
				validateErrMsgForAmountField();					//Validate the error message on Amount - Click on continue before entering the amount value
				setValue(amountValue, amount);
				clickJS(radioBtnScheduleDate);
		        isElementDisplayedWithLog(continueButton,5);
				clickJS(continueButton,"Click on Continue Button");
				validateErrMsgForDateField();
				String scheduleDateValue = cafFunctional.getDate(2);
				clickJS(scheduleDate);
				setValue(scheduleDate,scheduleDateValue);
		        String transAccFromValue = getText(selectTransFromAccAfter);
		        String transAccToValue = getText(selectTransToAccAfter);
		        isElementDisplayedWithLog(continueButton, 5);
				//clickJS(continueButton,"Click on Continue Button");
				driver.findElement(continueButton).click();
				reviewTransBetAccOnReviewPage(transAccFromValue,transAccToValue,amount,scheduleDateValue);
				isElementDisplayedWithLog(continueButton, 5);
				clickJS(continueButton,"Click on Continue Button");
		        cafFunctional.EnterPINdetails(cafFunctional.GetvalidPIN(userType));
		        clickJS(confirmButton,"Confirm");
				appendScreenshotToCucumberReport();
				isElementDisplayedWithLog(successMessage,5);
		        injectMessageToCucumberReport("Trasfer between account is done successfully");
		    	Assert.assertTrue("Trasfer between account is not done successfully",true);
		        
		        //TODO - Implement FDP code
		        finalresponse = transAccToValue.replaceAll("[^A-Za-z ]", "").trim() + " " + amount +".00" + " " + scheduleDateValue;
		        clickJS(backToHome,"Back to Home");
				
			}
			
			catch(Exception e){
				logError("Error occured while navigating to transfer between accounts page " + e);
				injectWarningMessageToCucumberReport("Failued to navigate to transfer between accounts page" +e.getMessage());
				appendScreenshotToCucumberReport();
				Allure.step("Failued to navigate to transfer between accounts page",Status.FAILED);
			}
			return finalresponse;
	}
	
	private void reviewTransBetAccOnReviewPage(String transAccFromValue, String transAccToValue, String amountFieldValue, String schDateFieldValue) throws Exception{
		String actualAmount = getText(amountReviewPg);
		String actualTransAccFrom = getText(accFromReviewPg);
		String actualTransAccTo = getText(accToReviewPg);
		String actualSchDate = getText(processTransReviewPg);
		Assert.assertTrue(actualAmount.contains(amountFieldValue));
		Assert.assertEquals(transAccFromValue,actualTransAccFrom);
		Assert.assertEquals(transAccToValue,actualTransAccTo);
		Assert.assertTrue(schDateFieldValue.contains(actualSchDate));
		appendScreenshotToCucumberReport();
	}

	
	
	private void validateCustomerIsOnTransBtwAccPage() throws Exception{
		String actualMessage = getText(transBtwAccLabel);
		Assert.assertEquals("Customer is not on Transfer Between Accounts page", actualMessage,"Transfer Between Accounts");
	} 
	
	private void validateAvaialbleFundText() throws Exception{
		isElementDisplayedWithLog(availableFundText, 10);
		String actualMessage = getText(availableFundText);
		String availableFund = actualMessage.substring(17); // as string 
		//Assert.assertTrue("Available fund is not avaialble", availableFund.compareTo(amount) > 4);
		Assert.assertTrue("Available fund is not avaialble", availableFund.length() > 4);
	}
	
	private void validateErrMsgForAmountField() throws Exception {
		appendScreenshotToCucumberReport();
		String actualMessage = getText(errMsgForAmountField);
		String expectedMessage = getMessageText("expErrMessageAmount", "UXPBANKING365");
		Assert.assertEquals("Error message is not displayed for Amount field", actualMessage,expectedMessage);
	}
	
	private void validateErrMsgForDateField() throws Exception {
		appendScreenshotToCucumberReport();
		isElementDisplayedWithLog(errMsgScheduleDateField, 5);
		String actualMessage = getText(errMsgScheduleDateField);
		String expectedMessage = getMessageText("expErrMessageSchDate", "UXPBANKING365");
		Assert.assertEquals("Error message is not displayed for Date field", actualMessage,expectedMessage);
	}
	
	


}

package com.boi.grp.pageobjects.Payments;



import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class Payment_AddBillPage extends BasePageForAllPlatform {

	public UIResusableLibrary cafFunctional;
	By addPayeeBtn = getObject("pd.btnAddPayee");
	By addBillTxt = getObject("pd.txtAddBill");
	By billNameFieldDropdown = getObject("pd.dropdownBillName");
	By billNameSelect = getObject("pd.selectBillName");
	By refNumText = getObject("pd.txtreferenceNumber");
	By descriptionText = getObject("pd.txtDescription");
	By continueButton = getObject("pd.btnContinue");
	By confirmButton = getObject("pd.btnConfirmPIN");
	By successMessage = getObject("pd.imgSuccess");
	By billNameOnReviewPg = getObject("pd.txtBillNameReview");
	By refNumOnReviewPg = getObject("pd.txtRefNumReview");
	By descriptionOnReviewPg = getObject("pd.txtDescriptionReview");
	
	public Payment_AddBillPage(WebDriver driver) {                
		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional= new UIResusableLibrary(driver);
	}
	
	/*---------------------------------Start <PD_AddBillPay>----------------------------------------
	 Function Name: PD_AddBillPay
	 Argument Name:
	 Purpose: UB-324_Payments_ManagePayee_AddBillDailyLimitsValidation_AllCustomers
	 Author Name: CAF Automation 
	 Create Date: 13-05-2021
	 Modified Date| Modified By  |Modified purpose 
	  17/05/2021      C114322     Code update
	 -----------------------------------End <PD_ManagePayee>---------------------------------------
	 */
	


	public void PD_AddBillPay() throws Exception{
		String refnum = getTestData("referenceNumber");
		String description = getTestData("description");
		try{
			validateCustomerIsOnAddBillPage();
			logMessage("ADD BILL OPTION FOR DAILY LIMITS VALIDATION FOR ALL CUSTOMER : START");
			if (isElementDisplayed(billNameFieldDropdown)) {
				logMessage("Dropdown of Bill Payee is displayed on Add Bill Payee Details Page");			
					//Re-check with React code - C114322
	                cafFunctional.clickSelectValueFromDropDown(billNameFieldDropdown,billNameSelect,"POWER");
	                String billName = getText(billNameFieldDropdown);
	                setValue(refNumText,refnum);
	                setValue(descriptionText,description);
	                clickJS(continueButton);								
	                waitForPageLoaded();				
	                reviewBillDetailsOnReviewPage(billName,refnum,description);
	                clickJS(continueButton);
	                enterPINForAddBill();
	                clickJS(confirmButton);
	                waitForElementToBeDisplayed(successMessage);  
	        } else {
	        	logMessage("Dropdown of Bill Payee is NOT displayed on Add Bill Payee Details Page");
	        }
		}
		
		catch(Exception e){
			logError("Error occured while navigating to add bill page" +e.getMessage());
			injectWarningMessageToCucumberReport("Failued to navigate to add bill pay page" +e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failued to navigate to add bill pay page",Status.FAILED);
		}
	}
	
	
	private void validateCustomerIsOnAddBillPage() throws Exception{
		String actualMessage = driver.findElement(addBillTxt).getText();
		String expectedMessage = "Add Bill";
		if(actualMessage.equals(expectedMessage))
			logMessage("User is navigated to Add Bill page");
		else
			logError("User is not navigated to Add Bill page");
	}
	
	
	private void reviewBillDetailsOnReviewPage(String billName,String refnum,String description) throws Exception{
		String actualBillName = driver.findElement(billNameOnReviewPg).getText();
		String actualRefNum = driver.findElement(refNumOnReviewPg).getText();
		String actualDescription = driver.findElement(descriptionOnReviewPg).getText();
		//Validate Bill Name Field
		if(actualBillName.equals(billName))
			logMessage("Bill Name is as expected on Review page");
		else
			logError("Bill Name is not as expected on Review page");
		//Validate Reference Number Field
		if(actualRefNum.equals(refnum))
			logMessage("Reference number is as expected on Review page");
		else
			logError("Reference number is not as expected on Review page");
		//Validate Description Field
		if(actualDescription.equals(description))
			logMessage("Description is as expected on Review page");
		else
			logError("Description is not as expected on Review page");
		
		//Pending with validation of Daily Limit text on Review page 
		
	}
	
	private void enterPINForAddBill() throws Exception{
		//Need to modify code for getting PIN details - C114322
		String PIN =getLoginData("payuserPIN");
		
		String[] arrPin = PIN.split("");
		String strObject = "";
	     
		//////       waitForJQueryLoad();
		if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"), 5)) {
			strObject = "launch.edtPinEnterFieldGrpSCA";

		} else if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrp"), 2)) {
			strObject = "launch.edtPinEnterFieldGrp";
		} else if (isElementDisplayed(getObject("login.pinDigits"), 2)) {
			strObject = "login.pinDigits";
		} else {

		}
		
		List<WebElement> lstPinEnterFieldGrp = findElements(getObject(strObject));
		//Entering values for only enabled fields
		for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
			if (lstPinEnterFieldGrp.get(i).isEnabled()) {
				lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
			}
		}
	}
}

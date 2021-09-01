/*---------------------------------Start <SD_SS_ManageAccountPage>----------------------------------------
 Function Name: SD_SS_ManageAccountPage
 Argument Name:
 Purpose: Method to set different Manage Account Options
 Author Name: CAF Automation 
 Create Date: 17-05-2021
 Modified Date| Modified By  |Modified purpose 
  17/05/2021      C113329     Code update
 -----------------------------------End<SD_SS_ManageAccountPage>---------------------------------------
 */

package com.boi.grp.pageobjects.Services;

import java.util.List;
import java.time.LocalDate;
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

public class Service_SS_ManageAccountPage extends BasePageForAllPlatform {

	public UIResusableLibrary cafFunctional;
	public DataLibrary dataLibrary;
	public String referenceNumber;

	By manageAccountAlert = getObject("sd.buttonManageAccAlert");
	By manageAccountList = getObject("sd.listManageAccount");
	By AccountInList = getObject("sd.accountInList");
	By numberOfAccountInList = getObject("sd.numberOfAccountsInList");
	By OffToggleBtn = getObject("sd.buttonOffToggle");
	By OnToggleBtn = getObject("sd.buttonOnToggle");
	By ContinueBtn = getObject("sd.buttonContinue");
	By GoBackBtn = getObject("sd.buttonGoBack");
	By ConfirmBtn = getObject("sd.buttonConfirm");
	By ManageStatements = getObject("sd.buttonManageStatement");
	By refNumberAccountAlertText = getObject("sd.refNumberAccountAlertText");
	By AccountAlertsContinueBtn = getObject("sd.buttonAccountAlertContinue");
	By AccountAlertsConfirmBtn = getObject("sd.buttonAccountAlertsConfirm");
	
	// Order Up To Date Statement
	By OrderUpToDateAccDropdown = getObject("sd.dropdownOrderUpToDateStat");
	By SelectOrderUpToDateAccount = getObject("sd.selectOrderUpToDateAccount");
	By OrderUpToDateNotifyYesOptBtn = getObject("sd.btnOptOrderUpToDateNotifyYes");
	By OrderUpToDateNotifyNoOptBtn = getObject("sd.btnOptOrderUpToDateNotifyNo");
	By OrderUpToDateMobileNumText = getObject("sd.textOrderUpToDateMobileNum");
	By OrderUpToDateEmailIdText = getObject("sd.textOrderUpToDateEmailId");
	By OrderUpToDateStatMobileOptBtn = getObject("sd.btnOptOrderUpToDateStatMobile");
	By OrderUpToDateStatEmailOptBtn = getObject("sd.btnOptOrderUpToDateStatEmail");
	By OrdrUpToDateBackToHome =getObject("sd.btnOrderUpToDateBackToHome");
	By RequestSentMessage = getObject("sd.txtRequestSentMessage");
	By reviewOUTDMobNum = getObject("sd.reviewOUTDMobNum");
	
	// Order Copy Of Statement
	By OrderCopyOfStatDropDown = getObject("sd.dropdownOrderCopyOfStat");
	By PayChargesFromAccDropDown = getObject("sd.dropdownPayChargesFromAcc");
	By OrderCopyOfStatNotifyOpt = getObject("sd.optOrderCopyOfStatNotify");
	By OrderCopyOfStatNotifyNoOpt = getObject("sd.optOrderCopyOfStatNotifyNo");
	By OrderCopyOfStatMobileOptBtn = getObject("sd.btnOptOrderUpToDateStatMobile");
	By OrderCopyOfStatEmailOptBtn = getObject("sd.btnOptOrderUpToDateStatEmail");
	By OrderCopyOfStatMobileNumTxt = getObject("sd.textOrderCopyOfStatMobileNum");
	By OrderCopyOfStatEmailIdTxt = getObject("sd.textOrderCopyOfStatEmailId");
	By RefNumberOUTDTxt = getObject("sd.txtRefNumber");
	By RefNumberOCOStatTxt = getObject("sd.RefNumberOCOStatTxt");
		
	// Paper Statement On Off
	By PaperStatementOnOffLink = getObject("sd.linkPaperStatementOnOff");
	By StatementEmailNotify = getObject("sd.linkStatementEmailNotify");
	By StatementEmailNotifyOk = getObject("sd.buttonStatementEmailNotifyOk");
	By StatementEmailNotifyCancel = getObject("sd.buttonStatementEmailNotifyCancel");
	By TurnPaperStatOnOffToggle = getObject("sd.toggleTurnPaperStatOnOff");
	By GoBackButton = getObject("sd.buttonGoBack");
	
	By StatementEmailNotificationOn = getObject("sd.toggleStatementEmailNotificationOn");
	By StatementEmailNotificationOff = getObject("sd.toggleStatementEmailNotificationOff");
	By StmntNotification = getObject("sd.lnkStmntNotification");
	By ToggleStatus = getObject("sd.toggleStatus");

	By ChangeAddressAccError = getObject("sd.changeAddressAccountErrorContinue");
	By AddAccount = getObject("sd.buttonAddAccount");
	By OrderIntrestCertificate = getObject("sd.buttonOrderInterestCertificate");
	By TaxYearEnded = getObject("sd.buttonYearDropDown");

	By RegisterMobile = getObject("sd.buttonRegisterMobile");
	By NotificationYesOpt = getObject("sd.RepalceDamageDebitCardNotificaionYesOpt");
	By NotificationNoOpt = getObject("sd.RepalceDamageDebitCardNotificaionNoOpt");
	By NotificationMobileOpt = getObject("sd.RepalceDamageDebitCardNotificaionContMobileOpt");
	By NotificationEmailOpt = getObject("sd.RepalceDamageDebitCardNotificaionContEmailOpt");
	By NotificationEmailText = getObject("sd.RepalceDamageDebitCardNotificaionContEmailText");
	By NotificationMobileText = getObject("sd.RepalceDamageDebitCardNotificaionContMobileText");
	By OrderUpToDateStatement = getObject("sd.linkOrderUpToDateStatement");
	By OrderUpToDateStatementContinueBtn = getObject("sd.continueBtn");
	By OrderCopyOfStatement = getObject("sd.textOrderCopyOfStatement");
	By ReviewPage = getObject("sd.textReview");
	By AccountNumber = getObject("sd.textAccountNumber");
	By AddressLine1 = getObject("sd.textAddressLine1");
	By AddressLine2 = getObject("sd.textAddressLine2");
	By DOB = getObject("sd.textDOBLine");
	
	// For Add Account/Policy Tile
	By accountDropDown = getObject("sd.dropdownAccountList");
	By ValueDropDownAccount =getObject("sd.valueDropDownAccount");
	By CurrentAccount = getObject("sd.AddCurrentAccount");
	By LoanAccount = getObject("sd.AddLoanAccount");
	By DemandDepositAcc = getObject("sd.DemandAndDepositAccount");
	By BOILifePensionPolicy = getObject("sd.BOILifePensionPolicy");
	By BOILifeProtectionPolicy = getObject("sd.BOILifeProtectionPolicy");
	By CreditAccount = getObject("sd.CreditCard");
	By TermSavingNotice = getObject("sd.TermSavingAccount");
	By BOIMortage = getObject("sd.BOIMortgage");
	By BOILifeInvestSavingPlan = getObject("sd.BOILifeInvestment");
	
	//For Add Current Account Form
	By CAAcountNumber = getObject("sd.textCAAccountNumber");
	By CAAddressLine1 = getObject("sd.textCAAddressLine1");
	By CAAddressLine2 = getObject("sd.textCAAddressLine2");
	By CAAddressLine3 = getObject("sd.textCAAddressLine3");
	By CADateOfBirth = getObject("sd.textCADateOfBirth");
	By reviewCAAccNum = getObject("sd.reviewtextCAAccNum");

	
	// For Add Demand Deposit Account Form
	By DDAcountNumber = getObject("sd.textDDAccountNumber");
	By DDAddressLine1 = getObject("sd.textDDAddressLine1");
	By DDAddressLine2 = getObject("sd.textDDAddressLine2");
	By DDAddressLine3 = getObject("sd.textDDAddressLine3");
	By DDDateOfBirth = getObject("sd.textDDDateOfBirth");
	By reviewDDAccName = getObject("sd.reviewDDAccName");
	By reviewDDAccNum = getObject("sd.reviewDDAccNum");
	By reviewAdd1 = getObject("sd.reviewAdd1");
	By reviewAdd2 = getObject("sd.reviewAdd1");
	By reviewAdd3 = getObject("sd.reviewAdd1");
	By reviewDOB = getObject("sd.reviewDOB");
	By SuccessMes = getObject("sd.textSuccessMes");		
	By RefNumberDemandDebitAccTxt = getObject("sd.RefNumberDemandDebitAccTxt");
	
	// For Order Balance Certificate
	By clkOBCTile = getObject("sd.buttonOrderBalanceCertificate");
	By AddAccList = getObject("sd.selectOBCDropDown");
	By FromDate = getObject("sd.textFromDate");
	By ToDate = getObject("sd.textToDate");
	By valAccountName = getObject("sd.valAccountName");
	By valFromDate = getObject("sd.valFromDate");
	By valToDate = getObject("sd.valToDate");
	By valNotification = getObject("sd.valNotification");
	By LedgerBalanceDate = getObject("sd.textOBCLegendBalanceDate");
	By OBCNotifyOptYes = getObject("sd.optOBCNotify");
	By OBCNotifyOptNo = getObject("sd.optOBCNotifyNo");
	By OBCMobileNumOpt = getObject("sd.optOBCMobileNum");
	By OBCEmailIdOpt = getObject("sd.optOBCEmailId");
	By OBCMobileNumTxt = getObject("sd.textOBCMobileNum");
	By OBCMobileEmailIdTxt = getObject("sd.textOBCEmailId");
	By OBCLegendBalDateReview = getObject("sd.textOBCLegendBalDateReview");
	By RefNumberOBCTxt = getObject("sd.RefNumberOBCTxt");
	
	// For Change Address
	By clkAddress = getObject("sd.clkAddress");
	By btnContinue = getObject("sd.btnContinue");
	By clkCountry = getObject("sd.clkCountry");
	By clkCounty = getObject("sd.clkCounty");
	By clkAddressLine1 = getObject("sd.clkAddressLine1");
	By clkAddressLine2 = getObject("sd.clkAddressLine2");
	By clkAddressLine3 = getObject("sd.clkAddressLine3");
	By clkEircode = getObject("sd.clkEircode");
	By clkUpdateEmailYes = getObject("sd.clkUpdateEmailYes");
	By clkUpdateEmailNo = getObject("sd.clkUpdateEmailNo");
	By clkNewEmail = getObject("sd.clkNewEmail");
	By clkConfirmEmail = getObject("sd.clkConfirmEmail");
	By clkContinue = getObject("sd.clkContinue");
	By addressList = getObject("sd.addressList");
	By clkDistrict = getObject("sd.clkDistrict");
	By msgChangeAddress = getObject("sd.msgChangeAddress");
	By valAddressLine1 = getObject("sd.valAddressLine1");
	By valAddressLine2 = getObject("sd.valAddressLine2");
	By valAddressLine3 = getObject("sd.valAddressLine3");
	By valCounty = getObject("sd.valCounty");
	By valCountry = getObject("sd.valCountry");
	By RefNumberChangeAddressTxt = getObject("sd.RefNumberChangeAddressTxt");
	
	//For Order Interest Certificate
	By serviceTab = getObject("sd.serviceBtn");
	By orderIneterstCertificateBtn = getObject("sd.oredrInterestCertificate");
	By selectAccDrpDown = getObject("sd.selectACCDrpDowm");
	By selectAccName = getObject("sd.selectAccName");
	By taxYearDrpDown = getObject("sd.taxYearDrpDown");
	By selectYearName = getObject("sd.selectYearName");
	By selectLastYear = getObject("sd.selectLastYear");
	By notifyRadioBtn = getObject("sd.yesNotify");
	By notifyMethod = getObject("sd.selectOptToNotify");
	By enterMobileNo = getObject("sd.enterMobileNumber");
	By continueBtn = getObject("sd.continueBtn");
	By accNameReviewPg = getObject("sd.reviewAccountNumber");
	By taxYearReviewPg = getObject("sd.reviewTaxYear");
	By notifyOptReviewPg = getObject("sd.reviewNotify");
	By notifyMethodReviewPg = getObject("sd.reviewNotifyMethod");
	By mobileNoReviewPage = getObject("sd.reviewMobileNumber");
	By confirmBtn = getObject("sd.confirmBtn");
	By backToHomeBtn = getObject("sd.backToHome");
	By messageBtn = getObject("sd.messageBtn");
	By sentTab = getObject("sd.sentTab");
	By confirmMessage = getObject("sd.confirmMessage");
	//for reg to pay 
	By clkRegisterOrManage= getObject("sd.clkRegisterOrManage"); 
	By clkRegisterButton= getObject("sd.clkRegisterButton");
	By clkUpdateProfile= getObject("sd.clkUpdateProfile");
	By selectaccountdropdown= getObject("sd.selectaccountdropdown");
	By clkPhoneNum = getObject("profile.clkPhoneNum");
	By clkEnterYourMobileNum = getObject("profile.clkEnterYourMobileNum");
	By clkReEnterYourMobileNum= getObject("profile.clkReEnterYourMobileNum");
	By SuccessMessage = getObject("profile.UpdateNumSuccessMesg");
	By clkConfirm = getObject("profile.clkConfirm"); 	
	By clkXonSuccessMsg = getObject("profile.clkXonSuccessMsg"); 

	public Service_SS_ManageAccountPage(WebDriver driver) {

		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional = new UIResusableLibrary(driver);
		dataLibrary = new DataLibrary();
	}

	/*---------------------------------Start <SD_SS_ManageAccount>----------------------------------------
	 Function Name: SD_SS_ManageAccount
	 Argument Name:
	 Purpose: Method to set different Manage Account Options
	 Author Name: CAF Automation 
	 Create Date: 17-05-2021
	 Modified Date| Modified By  |Modified purpose 
	  17/05/2021      C113329     Code update
	 -----------------------------------End<SD_SS_ManageAccount>---------------------------------------
	 */
	
	public void SD_SS_ManageAccount(String serMAOpt, String subSerType, String userType) throws Exception {
				
		try {

			logMessage("SERVICES OPTION TYPE : " + serMAOpt);
			boolean bFlagSerMAOpt = false;
			switch (serMAOpt.toUpperCase()) {
			case "CHANGE_ADDRESS":
				if (isElementDisplayed(clkAddress, 10, "ChangeAddress")) {
					scrollToView(clkAddress);
					SD_MA_ChangeAddress_P(userType);
					bFlagSerMAOpt = true;
				} else {
					bFlagSerMAOpt = false;
				}
				break;

			case "MANAGE_STATEMENTS":
				if (isElementDisplayed(ManageStatements, 10, "ManageStatements")) {
					scrollToView(ManageStatements);
					SD_MA_ManageStatement_P(subSerType);
					bFlagSerMAOpt = true;
				} else {
					bFlagSerMAOpt = false;
				}
				break;

			case "ADD_ACCOUNT":
				if (isElementDisplayed(AddAccount, 10, "AddAccount")) {
					/*JavascriptExecutor js = (JavascriptExecutor) driver;
		            js.executeScript("arguments[0].scrollIntoView(AddAccount)");*/
					scrollToView(AddAccount);
					SD_MA_AddAccPolicy_P(subSerType);
					bFlagSerMAOpt = true;
				} else {
					bFlagSerMAOpt = false;
				}
				break;

			case "MANAGE_ACCOUNT_ALERTS":
				if (isElementDisplayed(manageAccountAlert, 10, "manageAccountAlert")) {
					scrollToView(manageAccountAlert);
					SD_MA_Alerts_P();
					bFlagSerMAOpt = true;
				} else {
					bFlagSerMAOpt = false;
				}
				break;

			case "REGISTER_MANAGE_PAY_TO_MOBILE":
				if (isElementDisplayed(clkRegisterOrManage, 10, "clkRegisterOrManage")) {
					scrollToView(clkRegisterOrManage);
					SD_MA_RegisterPayToMobile_P(userType);
					bFlagSerMAOpt = true;
				} else {
					bFlagSerMAOpt = false;
				}
				break;

			case "ORDER_INTREST_CERTIFICATE":
				if (isElementDisplayed(OrderIntrestCertificate, 10, "OrderIntrestCertificate")) {
					scrollToView(OrderIntrestCertificate);
					SD_MA_OrderInterestCertificate_P();
					bFlagSerMAOpt = true;
				} else {
					bFlagSerMAOpt = false;
				}
				break;

			case "ORDER_BALANCE_CERTIFICATE":
				if (isElementDisplayed(clkOBCTile, 10, "clkOBCTile")) {
					scrollToView(clkOBCTile);
					SD_MA_OrderBalanceCertificate_P();
					bFlagSerMAOpt = true;
				} else {
					bFlagSerMAOpt = false;
				}
				break;
			}
			if (bFlagSerMAOpt == true) {
				appendScreenshotToCucumberReport();
				logMessage(serMAOpt + " Click on Manage Account tile successfully in SD_SS_ManageAccount function ");
				injectMessageToCucumberReport(serMAOpt + " Click on Manage Account tile successfully");
				Allure.step("Click on Manage Account tile successfully", Status.PASSED);
			} else {
				logMessage(serMAOpt + " Click on Manage Account tile NOT successfully");
				injectMessageToCucumberReport(serMAOpt + " Failure in Click on Manage Account tab ");
				Allure.step(serMAOpt + " Click on Manage Account tile NOT successfully ", Status.FAILED);
				appendScreenshotToCucumberReport();
			}
		} catch (Exception e) {
			logError("Error Occured inside SD_SS_ManageAccount " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Manage Account option selection " + e.getMessage());
			Allure.step("Failure in Manage Account option selection ", Status.FAILED);
			appendScreenshotToCucumberReport();

		}
	}
	
	public String setReferNum(){
		
		return referenceNumber;
	}

	/*---------------------------------Start <SD_MA_ChangeAddress_P>----------------------------------------
	 Function Name: SD_MA_ChangeAddress_P
	 Argument Name:
	 Purpose: Method to change account address
	 Author Name: CAF Automation 
	 Create Date: 17-05-2021
	 Modified Date| Modified By  |Modified purpose 
	  17/05/2021      C113329     Code update
	 -----------------------------------End<SD_MA_ChangeAddress_P>---------------------------------------
	 */

	private void SD_MA_ChangeAddress_P(String userType) {
		try {
			String country = dataLibrary.getAddressCountry(userType);
			String county = dataLibrary.getRandomIrishCounty(country);
			String addressLine1 = dataLibrary.getAddressLine1();
			String addressLine2 = dataLibrary.getAddressLine2();
			String addressLine3 = generateRandomString() + " " + LocalDate.now().toString();
			String updateEmail = "NO";
			
			clickJS(clkAddress, "Services - Manage Account - Change Address");

			List<WebElement> accountList = findElements(addressList);
			int aCount = accountList.size();
			System.out.println(aCount);
			if (accountList.size() > 1) {
				System.out.println(aCount);
				aCount = accountList.size() - 1;
				System.out.println(aCount);
				for (int i = 0; i < aCount; i++) {
					scrollToView(accountList.get(i));
					clickJS(accountList.get(i));
					Thread.sleep(3000);
					logMessage(
							"Un-selecting the few accounts so that we can change the Address for less that 20 Accounts");
					accountList = findElements(addressList);
				}
			}

			// clicking on continue button
			if (isElementDisplayed(clkContinue, 10, "clkContinue")) {
				clickJS(clkContinue, "Services - Manage Account - Change Address Continue");
				System.out.println("******************- Country "+country);
				setValue(clkCountry, country);
				waitForPageLoaded();
				clickJS(clkContinue, "Services - Manage Account - Change Address Continue County");
				if (isElementDisplayed(clkCounty, 10, "clkCounty")) {
					setValue(clkCounty, county);
					waitForPageLoaded();
				}
				setValue(clkAddressLine1, addressLine1);
				setValue(clkAddressLine2, addressLine2);
				setValue(clkAddressLine3, addressLine3);
				setValue(clkEircode, "");
				// for update email yes/no
				if (updateEmail == "YES") {
					// if yes
					clickJS(clkUpdateEmailYes, "Services - Manage Account - Change Address Email Update");
					String newEmail = dataLibrary.generateRandomEmail();
					setValue(clkNewEmail, newEmail);
					setValue(clkConfirmEmail, newEmail);
				}
				// if no
				clickJS(clkContinue, "Services - Manage Account - Change Address Email Update Continue");
				// checking with input values
				String addLine1Val = getText(valAddressLine1);
				Assert.assertEquals(addressLine1, addLine1Val);
				String addLine1Val2 = getText(valAddressLine2);
				Assert.assertEquals(addressLine2, addLine1Val2);
				String addLine1Val3 = getText(valAddressLine3);
				Assert.assertEquals(addressLine3, addLine1Val3);
				String addCounty = getText(valCounty);
				Assert.assertEquals(county, addCounty);
				String addCountry = getText(valCountry);
				Assert.assertEquals(country, addCountry);
				// click on confirm
				clickJS(ConfirmBtn, "Services - Manage Account - Change Address Review Page Confirm");
				// entering pin
				cafFunctional.Enter3of6DigitPIN(dataLibrary.validPinFlag(), cafFunctional.GetvalidPIN(userType));
				waitForPageLoaded();
				// click on confirm
				clickJS(ConfirmBtn, "Services - Manage Account - Change Address 3 Pin Confirm");
				waitForPageLoaded();
				cafFunctional.PushNotification_Acccept();
				
				String verifySuccessMess = driver.findElement(RequestSentMessage).getText();
				String expectedSuccessMesg = "Request sent";
				Assert.assertEquals(expectedSuccessMesg, verifySuccessMess);
								
				String refNum = driver.findElement(RefNumberChangeAddressTxt).getText();
				referenceNumber = refNum.substring(15, refNum.length());
								
				clickJS(backToHomeBtn, "Service - Change Address - Back btn");

				// Logging
				logMessage("Change address verified successfully");
				logInfo("Change address verified successfully");
				injectMessageToCucumberReport("Change address verified successfully");
				Allure.step("Email address verified successfully", Status.PASSED);
				appendScreenshotToCucumberReport();
			}

		} catch (Exception e) {
			logError("Error Occured inside change Address");
			injectWarningMessageToCucumberReport("Failure in change Address " + e.getMessage());
			Allure.step("change Address is NOT Successfull ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}


	/*---------------------------------Start <SD_MA_ManageStatement_P>----------------------------------------
	 Function Name: SD_MA_ManageStatement_P
	 Argument Name:
	 Purpose: Method to manage statement types
	 Author Name: CAF Automation 
	 Create Date: 27-05-2021
	 Modified Date| Modified By  |Modified purpose 
	  27/05/2021      C113329     Code update
	 -----------------------------------End<SD_MA_ManageStatement_P>---------------------------------------
	 */
	
	private void SD_MA_ManageStatement_P(String subSerType) {
		try {

			clickJS(ManageStatements, "Services - Manage Account - Manage Statement");
			logMessage("SERVICES MANAGE STATEMENT OPTION TYPE : " +subSerType);
			boolean bFlagSerMSOpt = false;
			switch (subSerType.toUpperCase()) {
			case "ORDER_UPTO_DATE_STATEMENT":
				if(isElementDisplayed(OrderUpToDateStatement, 10, "OrderUpToDateStatement")){
				scrollToView(OrderUpToDateStatement);
				orderUpToDateStatementPage();
				bFlagSerMSOpt = true;
				}else{
					bFlagSerMSOpt = false;
				}
				break;

			case "ORDER_COPYOF_STATEMENT":
				if(isElementDisplayed(OrderCopyOfStatement, 10, "OrderCopyOfStatement")){
				scrollToView(OrderCopyOfStatement);
				orderCopyOfStatementPage();
				bFlagSerMSOpt = true;
				}else{
					bFlagSerMSOpt = false;
				}
				break;

			case "TURN_PAPER_STATEMENT_ONOFF":
				if(isElementDisplayed(StmntNotification, 10, "StmntNotification")){
				scrollToView(StmntNotification);
				turnPaperStatementOnOffPage();
				bFlagSerMSOpt = true;
				}else{
				bFlagSerMSOpt = false;
				}
				break;

			case "STATEMENT_NOTIFICATION":
				if(isElementDisplayed(StatementEmailNotificationOff, 10, "StatementEmailNotificationOff")){
				scrollToView(StatementEmailNotificationOff);
				statementNotificationPage();
				bFlagSerMSOpt = true;
				}else{
				bFlagSerMSOpt = false;
				}
				break;
			}
			if (bFlagSerMSOpt == true) {
				appendScreenshotToCucumberReport();
				logMessage(subSerType + " Click on Manage Statement tile successfully in SD_MA_ManageStatement_P function ");
				injectMessageToCucumberReport(subSerType + " Click on Manage Statement tile successfully");
				Allure.step("Click on Manage Statement tile successfully", Status.PASSED);
			} else {
				logMessage(subSerType + " Click on Manage Statement tile NOT successfully");
				injectMessageToCucumberReport(subSerType + " Failure in Click on Manage Statement tab ");
				Allure.step(subSerType + " Click on Manage Statement tile NOT successfully ", Status.FAILED);
				appendScreenshotToCucumberReport();
			}

		} catch (InterruptedException e) {
			logError("Error Occured inside SD_MA_ManageStatement_P " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Manage Statement functionality " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in Manage Statement functionality", Status.FAILED);
		}
	}

	/*---------------------------------Start <SD_MA_AddAccPolicy_P>----------------------------------------
	 Function Name: SD_MA_AddAccPolicy_P
	 Argument Name:
	 Purpose: Method to manage statement types
	 Author Name: CAF Automation 
	 Create Date: 04-06-2021
	 Modified Date| Modified By  |Modified purpose 
	  04/06/2021      C113329     Code update
	 -----------------------------------End<SD_MA_AddAccPolicy_P>---------------------------------------
	 */
	
	private void SD_MA_AddAccPolicy_P(String subSerType) throws Exception {
		try {
			clickJS(AddAccount, "Services - Add Account or Policy");
			if(isElementDisplayed(accountDropDown, 10, "accountDropDown")){
				
				//cafFunctional.clickSelectValueFromDropDown(accountDropDown,selectAccName,subSerType);  //Select Account Dropdown
				//cafFunctional.clickJS(selectAccName);
				
				clickJS(accountDropDown,"SUB_SER_TYPE");
				clickJS(getObject("sd.valueDropDownAccount",subSerType));
				String accName = getText(selectAccDrpDown);
				
				logMessage("SERVICES ADD ACCOUNT OPTION TYPE : " + subSerType);
				boolean bFlagSerAAopt = false;
				switch (subSerType.toUpperCase()) {
				case "CURRENT ACCOUNT":
					if(isElementDisplayed(accountDropDown, 10, "Current Account")){
					//if(getText(accountDropDown)=="Current Account"){
					verifyAddAccountform();
					bFlagSerAAopt = true;
					}else{
					bFlagSerAAopt = false;
					}
					break;

				case "DEMAND DEPOSIT ACCOUNT":
					if(isElementDisplayed(accountDropDown, 10, "Demand Deposit Account")){
					//	if(getText(accountDropDown)=="Demand Deposit Account"){
					verifyDDAddAccountform();
					bFlagSerAAopt = true;
					}else{
					bFlagSerAAopt = false;
					}
					break;

				case "LOAN ACCOUNT":
					if(isElementDisplayed(LoanAccount, 10, "LoanAccount")){
					verifyAddAccountform();
					bFlagSerAAopt = true;
					}else{
					bFlagSerAAopt = false;
					}
					break;

				case "Credit Account":
					if(isElementDisplayed(CreditAccount, 10, "CreditAccount")){
					verifyCreditAddAccountform();
					bFlagSerAAopt = true;
					}else{
					bFlagSerAAopt = false;
					}
					break;
					
				case "Term Saving Notice Or Regular Saving Account":
					if(isElementDisplayed(TermSavingNotice, 10, "TermSavingNotice")){
					verifyTSNRSavingAddAccountform();
					bFlagSerAAopt = true;
					}else{
					bFlagSerAAopt = false;
					}
					break;
					
				case "BOI Mortgage":
					if(isElementDisplayed(BOIMortage, 10, "BOIMortage")){
					verifyBOIMortageAddAccountform();
					bFlagSerAAopt = true;
					}else{
					bFlagSerAAopt = false;
					}
					break;
					
				case "BOI Life Investment Or Saving Plan":
					if(isElementDisplayed(BOILifeInvestSavingPlan, 10, "BOILifeInvestSavingPlan")){
					verifyBOILifeInvestAddAccountform();
					bFlagSerAAopt = true;
					}else{
					bFlagSerAAopt = false;
					}
					break;
					
				case "BOI Life Pension Policy":
					if(isElementDisplayed(BOILifePensionPolicy, 10, "BOILifePensionPolicy")){
					verifyBOILifePentionAddAccountform();
					bFlagSerAAopt = true;
					}else{
					bFlagSerAAopt = false;
					}
					break;
					
				case "BOI Life Protection Policy":
					if(isElementDisplayed(BOILifeProtectionPolicy, 10, "BOILifeProtectionPolicy")){
					verifyBOILifeProtectionAddAccountform();
					bFlagSerAAopt = true;
					}else{
					bFlagSerAAopt = false;
					}
					break;
				}
				if (bFlagSerAAopt == true) {
					appendScreenshotToCucumberReport();
					logMessage(subSerType + " Click on Add Account tile successfully in SD_MA_AddAccPolicy_P function ");
					injectMessageToCucumberReport(subSerType + " Click on Add Account tile successfully");
					Allure.step("Click on Add Account tile successfully", Status.PASSED);
				} else {
					logMessage(subSerType + " Click on Add Account tile NOT successfully");
					injectMessageToCucumberReport(subSerType + " Failure in Click on Add Account tab ");
					Allure.step(subSerType + " Click on Add Account tile NOT successfully ", Status.FAILED);
					appendScreenshotToCucumberReport();
				}

			} }catch (InterruptedException e) {
				logError("Error Occured inside SD_MA_AddAccPolicy_P " + e.getMessage());
				injectWarningMessageToCucumberReport("Failure in Add Account functionality " + e.getMessage());
				appendScreenshotToCucumberReport();
				Allure.step("Failure in Add Account functionality", Status.FAILED);
			}
		}
	
	/*---------------------------------Start <SD_MA_Alerts_P>----------------------------------------
	 Function Name: SD_MA_Alerts_P
	 Argument Name:
	 Purpose: Method to manage statement types
	 Author Name: CAF Automation 
	 Create Date: 14-06-2021
	 Modified Date| Modified By  |Modified purpose 
	  14/06/2021      C113329     Code update
	 -----------------------------------End<SD_MA_Alerts_P>---------------------------------------
	 */

	private void verifyAddAccountform() throws Exception {

		try {
			String accNumber = dataLibrary.generateRandomNumber(8);
			String addressLine1 = dataLibrary.getAddressLine1();
			String addressLine2 = dataLibrary.getAddressLine2();
			String addressLine3 = generateRandomString() + " " + LocalDate.now().toString();
			String DOB = dataLibrary.generateRabdomDate();
			
			
			//cafFunctional.clickSelectValueFromDropDown(accountDropDown, ValueDropDownAccount,"Current Account");  //Select Account Dropdown
			Thread.sleep(3000);
			//clickJS(accountDropDown, "Services - Manage Account - Add Account List");
			Thread.sleep(3000);
			setValue(CAAcountNumber, accNumber);
			setValue(CAAddressLine1, addressLine1);
			setValue(CAAddressLine2, addressLine2);
			setValue(CAAddressLine3, addressLine3);
			setValue(CADateOfBirth, DOB);
			clickJS(ContinueBtn, "Services - Manage Account - Add Account List Continue");
			reviewAddCurrentAccPage(accNumber, addressLine1, addressLine2, addressLine2, DOB);
			
			String verifySuccessMess = driver.findElement(RequestSentMessage).getText();
			String expectedSuccessMesg = "Request sent";
			Assert.assertEquals(expectedSuccessMesg, verifySuccessMess);
							
			String refNum = driver.findElement(RefNumberDemandDebitAccTxt).getText();
			referenceNumber = refNum.substring(15, refNum.length());
								
			clickJS(backToHomeBtn, "Services - Manage Account - Add Current Account Back To Home Page");
			
		} catch (Exception e) {

			logError("Error Occured inside clickAddAccountContinue " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Add Current Account functionality " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in Add Current Account functionality", Status.FAILED);
		}
	}

	private void reviewAddCurrentAccPage(String accNumber,String addressLine1, String addressLine2, String addressLine3, String DOB) {
		
			try{
			
			String actualAccNum = driver.findElement(reviewCAAccNum).getText();
			String actualAdd1 = driver.findElement(reviewAdd1).getText();
			String actualAdd2 = driver.findElement(reviewAdd2).getText();
			String actualAdd3 = driver.findElement(reviewAdd3).getText();
			String actualDOB = driver.findElement(reviewDOB).getText();
					
			
			//Validate Account Number Field
			if(actualAccNum.equals(accNumber))
				logMessage("Account Name is as execpected on Review Page");
			else
				logMessage("Account Name is not as execpected on Review Page");
			
			//Validate Add Line1 Field
			if(actualAdd1.equals(addressLine1))
				logMessage("Account Address Line 1 is as execpected on Review Page");
			else
				logMessage("Account Address Line 2 is not as execpected on Review Page");
			
			//Validate Add Line2 Field
			if(actualAdd2.equals(addressLine2))
				logMessage("Account Address Line 2 is as execpected on Review Page");
			else
				logMessage("Account Address Line 2 is not as execpected on Review Page");
			
			//Validate Add Line3 Field
			if(actualAdd3.equals(addressLine1))
				logMessage("Account Address line 3 is as execpected on Review Page");
			else
				logMessage("Account Address line 3 is not as execpected on Review Page");
			
			//Validate DOB Field
			if(actualDOB.equals(DOB))
				logMessage("Account DOB is as execpected on Review Page");
			else
				logMessage("Account DOB is not as execpected on Review Page");
			
			waitForPageLoaded();
			clickJS(ConfirmBtn, "Services - Manage Account - Add Account Review Page Confirm");
									
			} catch(Exception e){
				logError("Error Occured inside reviewAddCurrentAccPage " +e.getMessage());
				injectWarningMessageToCucumberReport("Failure in reviewAddCurrentAccPage "+e.getMessage());
				appendScreenshotToCucumberReport();
				Allure.step("Failure in Review Page in Add Current Account ", Status.FAILED);
			}
		}


		private void SD_MA_Alerts_P() throws Exception {
			
			try {
				scrollToView(manageAccountAlert);  //Scroll to manage account - account alerts tile
				if(isElementDisplayed(manageAccountAlert, 10, "manageAccountAlert")){
					clickJS(manageAccountAlert, "Services - Manage Account - Manage Account Alerts");	
					verifyAccountList();
					
					String verifySuccessMess = driver.findElement(RequestSentMessage).getText();
					String expectedSuccessMesg = "Request sent";
					Assert.assertEquals(expectedSuccessMesg, verifySuccessMess);
									
					String refNum = driver.findElement(refNumberAccountAlertText).getText();
					referenceNumber = refNum.substring(15, refNum.length());
										
					clickJS(backToHomeBtn, "Services - Manage Account - Add Demand Deposit Account Back To Home Page");
					
					}
					appendScreenshotToCucumberReport();
					logMessage("Click on Manage Account Alerts tile successfully in SD_MA_Alerts_P function ");
					injectMessageToCucumberReport("Click on Manage Account Alerts tile successfully");
					Allure.step("Click on Manage Account Alerts tile successfully ", Status.PASSED);
				}catch(Exception e){
			logError("Error Occured inside SD_MA_Alerts_P " + e.getMessage());
			//injectWarningMessageToCucumberReport("Failure in Manage Account Alerts option " + e.getMessage());
			Allure.step("Failure in Manage Account Alerts option ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}

		}
		
		/*---------------------------------Start <SD_MA_RegisterPayToMobile_P>----------------------------------------
		 Function Name: SD_MA_RegisterPayToMobile_P
		 Argument Name:
		 Purpose: Method for Register Pay To Mobile Option
		 Author Name: CAF Automation 
		 Create Date: 10-08-2021
		 Modified Date| Modified By  |Modified purpose 
		  12-08-2021     C114688    Code update
		 -----------------------------------End<SD_MA_RegisterPayToMobile_P>---------------------------------------
		 */
		
		private void SD_MA_RegisterPayToMobile_P(String userType)throws Exception {
		try {
			clickJS(clkRegisterOrManage,"Services - Clicked on Register/Manage pay to mobile");
			clickJS(clkRegisterButton,"Services - Manage Account - Clicked on Register button");
			clickJS(clkUpdateProfile,"Services - Manage Account - Clicked on Update profile link");
			// update mobile number
			clickJS(clkPhoneNum,"Profile - Clicked on edit Phone number");
			String mobileNum = dataLibrary.generateRandomIrishMobileNumber();
			setValue(clkEnterYourMobileNum, mobileNum);
			isElementDisplayed(clkReEnterYourMobileNum, 10, "Re-Enter Your Mobile Num");
			setValue(clkReEnterYourMobileNum,mobileNum );
			clickJS(clkContinue,"Profile - Clicked on continue");
			cafFunctional.Enter3of6DigitPIN(dataLibrary.validPinFlag(), cafFunctional.GetvalidPIN(userType));//entering pin
			clickJS(clkConfirm,"Profile-PhoneNumber-Clicked on confirm button");//click on confirm button
			Assert.assertTrue(isElementDisplayed(SuccessMessage, 10, "You have successfully updated your mobile number."));
			clickJS(clkXonSuccessMsg,"Profile-PhoneNumber-Clicked on X on success message");//click X on success message
			//Click on service tab
			Services service = new Services(driver);
			service.ClickOnServicesTab();
			//Registering new mobile num
			clickJS(clkRegisterOrManage,"Services - Clicked on Register/Manage pay to mobile");			
			clickJS(clkRegisterButton,"Services - Manage Account - Clicked on Register button");
			isElementDisplayed(selectaccountdropdown, 10, "Select account");
			cafFunctional.clickSelectValueFromDropDown(selectaccountdropdown,selectAccName,"CURRENT");			
			clickJS(clkContinue,"Services - Manage Account - Clicked on Continue");
			appendScreenshotToCucumberReport();
			clickJS(clkContinue,"Services - Manage Account - Clicked on Continue");
			cafFunctional.Enter3of6DigitPIN(dataLibrary.validPinFlag(), cafFunctional.GetvalidPIN(userType));// entering pin			
			//logging and screenshot
			logMessage("Register pay to mobile update verified successfully");
			injectMessageToCucumberReport("Register pay to mobile update verified successfully");
			Allure.step("Register pay to mobile verified successfully", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside SD_MA_RegisterPayToMobile_P " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in SD_MA_RegisterPayToMobile_P " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Register to pay to mobiles is NOT successfully", Status.FAILED);
		}
		}
	
		/*---------------------------------Start <SD_MA_OrderInterestCertificate_P>----------------------------------------
		 Function Name:SD_MA_OrderInterestCertificate_P
		 Argument Name:
		 Purpose: 
		 Author Name: CAF Automation 
		 Create Date: 21-06-2021
		 Modified Date| Modified By  |Modified purpose 
		  21/06/2021      C115572     Code update
		 -----------------------------------End<SD_MA_OrderInterestCertificate_P>---------------------------------------
		 */

		private void SD_MA_OrderInterestCertificate_P() throws Exception{

			//String MobileNo = dataLib.randomPhoneNumber("0");      //To generate random mobile number
			String mobileNo = "0877121212";
			try{
				

			clickJS(orderIneterstCertificateBtn);
			if(isElementDisplayed(selectAccDrpDown)){
				
				cafFunctional.clickSelectValueFromDropDown(selectAccDrpDown,selectAccName,"SAVINGS ACCOUNT ~ 2317");  //Select Account Dropdown
				String accNum = getText(selectAccDrpDown);
				
				cafFunctional.clickSelectValueFromDropDown(taxYearDrpDown,selectYearName,"2020"); //Tax year Dropdown
				String taxYear = getText(taxYearDrpDown);
				
				clickJS(notifyRadioBtn);
				waitForElementToBeDisplayed(notifyMethod);
			    clickJS(notifyMethod);
			    waitForElementToBeDisplayed(enterMobileNo);
			    clickJS(enterMobileNo);
			    setValue(enterMobileNo,mobileNo);             //To Enter Mobile Number
				clickJS(continueBtn);
				
				reviewOrderInterestOnReviewPg(accNum,taxYear,mobileNo);   //To check review page details
				
				clickJS(confirmBtn);
				clickJS(backToHomeBtn);
				appendScreenshotToCucumberReport();
				Allure.step("Order Interest Certificate Services page executed successfully", Status.PASSED);
				//sentMessageConfirmation();    //To Check the genetrated message
			 } else {
				 
				 logMessage("Dropdown of Select Account is not displayed on Order Interest Certificate Page");
		       }
			 
					
			}catch(Exception e) {
				logError("Error Occured inside SD_MA_OrderInterestCertificate_P " +e.getMessage());
				injectWarningMessageToCucumberReport("Failure in Order Interest Certificate Page "+e.getMessage());
				appendScreenshotToCucumberReport();
				Allure.step("Order Interest Certificate Services page NOT successfully", Status.FAILED);
			}
			
		}
		
		//To Review the Order Interest Certificate Details
		private void reviewOrderInterestOnReviewPg(String accNum,String taxYear,String mobileNo) throws Exception{
			try{
			String actualAccNum = driver.findElement(accNameReviewPg).getText();
			String actualTaxYear = driver.findElement(taxYearReviewPg).getText();
			String actualMobileNo = driver.findElement(mobileNoReviewPage).getText();
			
			//Validate Account Name Field
			if(actualAccNum.equals(accNum))
				logMessage("Account Name is as execpected on Review Page");
			else
				logMessage("Account Name is not as execpected on Review Page");
			
			//Validate Tax Year Field
			if(actualTaxYear.equals(taxYear))
				logMessage("Tax Year is as execpected on Review Page");
			else
				logMessage("Tax Year is not as execpected on Review Page");
			
			//Validate Mobile Number
			if(actualMobileNo.equals(mobileNo))
				logMessage("Mobile Number is as execpected on Review Page");
			else
				logMessage("Mobile Number is not as execpected on Review Page");
			
			} catch(Exception e){
				logError("Error Occured inside reviewOrderInterestOnReviewPg " +e.getMessage());
				injectWarningMessageToCucumberReport("Failure in reviewOrderInterestOnReviewPg "+e.getMessage());
				appendScreenshotToCucumberReport();
				Allure.step("Failure in Order Interest Certificate Review Page", Status.FAILED);
			}
		}
	
	/*---------------------------------Start <SD_MA_OrderBalanceCertificate_P>----------------------------------------
	 Function Name: SD_MA_OrderBalanceCertificate_P
	 Argument Name:
	 Purpose: Method for Order Balance Certificate
	 Author Name: CAF Automation 
	 Create Date: 21-06-2021
	 Modified Date| Modified By  |Modified purpose 
	  21/06/2021      C113329     Code update
	 -----------------------------------End<SD_MA_OrderBalanceCertificate_P>---------------------------------------
	 */
	
	private void SD_MA_OrderBalanceCertificate_P() {
		
		try{
			String mobileNumber = "0877121212";
			String emailId = dataLibrary.generateRandomEmail();
			
			clickJS(clkOBCTile, "Services - Manage Account - Order Balance Cert");
			if(isElementDisplayed(AddAccList, 10, "AddAccList")){
			
			cafFunctional.clickSelectValueFromDropDown(AddAccList, selectAccName, "CURRENT ACCOUNT ");
			clickJS(AddAccList, "Service - Manage Account -Order Balance Certificate");
			//String accNum = getText(selectAccDrpDown);
			
			String ledgerBalanceDate = dataLibrary.generateRabdomDate();
			setValue(LedgerBalanceDate, ledgerBalanceDate);
			
			if (isElementDisplayed(OBCNotifyOptYes, 10, "OBCNotifyOptYes")) {
				clickJS(OBCNotifyOptYes, "Notification Yes Option Clicked");
				if (isElementDisplayed(OBCMobileNumOpt, 10, "OBCMobileNumOpt")) {
					clickJS(OBCMobileNumOpt, "Notification Mobile Option Selected");
					Thread.sleep(3000);
					setValue(OBCMobileNumTxt, mobileNumber);
				} else if (isElementDisplayed(OBCEmailIdOpt, 10, "OBCEmailIdOpt")) {
					clickJS(OBCEmailIdOpt, "Notification Email Option Selected");
					setValue(OBCMobileEmailIdTxt, emailId);
				}
			} else {
				if (isElementDisplayed(OBCNotifyOptNo, 10, "OBCNotifyOptNo")) {
					clickJS(OBCNotifyOptNo, "Notification No Option Selected");
				} else {
					logMessage("Notification Option Not Found On Replace DD Card Page");
				}
						
			reviewOrderBalanceOnReviewPg(ledgerBalanceDate,mobileNumber);   //To check review page details
			
			String verifySuccessMess = driver.findElement(RequestSentMessage).getText();
			String expectedSuccessMesg = "Request sent";
			Assert.assertEquals(expectedSuccessMesg, verifySuccessMess);
							
			String refNum = driver.findElement(RefNumberOBCTxt).getText();
			referenceNumber = refNum.substring(15, refNum.length());
								
			clickJS(backToHomeBtn, "Services - Manage Account - Order Balance Cert Review Page Back");
			appendScreenshotToCucumberReport();
			Allure.step("Order Interest Certificate Services page executed successfully", Status.PASSED);
			//sentMessageConfirmation();    //To Check the genetrated message
		 } }else {
			 
			 logMessage("Dropdown of Select Account is not displayed on Order Balance Certificate Page");
	       }
		 
				
		}catch(Exception e) {
			logError("Error Occured inside SD_MA_OrderBalanceCertificate_P " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Order Balance Certificate Page "+e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Order Balance Certificate Services page NOT successfully", Status.FAILED);
		}
	}

	//To Review the Order Balance Certificate Details
		private void reviewOrderBalanceOnReviewPg(String taxYear,String mobileNo) throws Exception{
			try{
			/*String actualAccNum = driver.findElement(accNameReviewPg).getText();*/
			String actualLBD = driver.findElement(OBCLegendBalDateReview).getText();
			String actualMobileNo = driver.findElement(mobileNoReviewPage).getText();
			
			/*//Validate Account Name Field
			if(actualAccNum.equals(accNum))
				logMessage("Account Name is as execpected on Review Page");
			else
				logMessage("Account Name is not as execpected on Review Page");*/
			
			//Validate Tax Year Field
			if(actualLBD.equals(taxYear))
				logMessage("Tax Year is as execpected on Review Page");
			else
				logMessage("Tax Year is not as execpected on Review Page");
			
			//Validate Mobile Number
			if(actualMobileNo.equals(mobileNo))
				logMessage("Mobile Number is not as execpected on Review Page");
			else
				logMessage("Mobile Number is not as execpected on Review Page");
			
			clickJS(confirmBtn, "Services - Manage Account - Order Balance Cert Review Page Confirm");
			
			} catch(Exception e){
				logError("Error Occured inside reviewOrderInterestOnReviewPg " +e.getMessage());
				injectWarningMessageToCucumberReport("Failure in reviewOrderInterestOnReviewPg "+e.getMessage());
				appendScreenshotToCucumberReport();
				Allure.step("Failure in Review Page in Order Interest Certificate", Status.FAILED);
			}
		}
		
	/*-------------------Start <Services - Manage Statement - Statement Notification>----------------------------------------
		 Function Name: Statement Notification
		 Argument Name:
		 Purpose: Method to manage Statement Notification types
		 Author Name: CAF Automation 
		 Create Date: 27-05-2021
		 Modified Date| Modified By  |Modified purpose 
		  27/05/2021      C113329     Code update
	 -------------------Start <Services - Manage Statement - Statement Notification>--------------------------------------- */
		
	private void statementNotificationPage() {
		try {
			String strFlow = "";
			if (isElementDisplayed(StatementEmailNotificationOff, 10, "StatementEmailNotificationOff")) {
				strFlow = "Email_Notification_On_To_Off";
			} else if (isElementDisplayed(StatementEmailNotificationOn, 10, "StatementEmailNotificationOn")) {
				strFlow = "Email_Notification_Off_To_On";
			}

			clickJS(StmntNotification, "Services - Manage Account - Manage Statement Notification");
			clickJS(StatementEmailNotifyOk);
			boolean blnON = false;

			if (isElementDisplayed(ToggleStatus, 10, "ToggleStatus")) {
				blnON = false;
				logMessage("Email Statement notification toggle button Status : On ");
			} else {
				blnON = true;
				logMessage("Email Statement notification toggle button Status : Off ");

			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}
	}

	/*-------------------Start <Services - Manage Statement - Paper Statement Notification>----------------------------------------
	 Function Name: Paper Statement Notification
	 Argument Name:
	 Purpose: Method to manage Paper Statement Notification types
	 Author Name: CAF Automation 
	 Create Date: 27-05-2021
	 Modified Date| Modified By  |Modified purpose 
	  27/05/2021      C113329     Code update
	-------------------Start <Services - Manage Statement - Paper Statement Notification>--------------------------------------- */
	
	private void turnPaperStatementOnOffPage() {
		try{
			if(isElementDisplayed(PaperStatementOnOffLink, 10, "PaperStatementOnOffLink")){
				clickJS(PaperStatementOnOffLink, "Manage Account - Manage Statement - Turn PaperStatement No Off");
				
				List<WebElement> accountList = findElements(TurnPaperStatOnOffToggle);
				int aCount = accountList.size();
				System.out.println(aCount);
				if (accountList.size() > 1) {
					System.out.println(aCount);
					aCount = accountList.size() - 1;
					System.out.println(aCount);
					for (int i = 0; i < aCount; i++) {
						scrollToView(accountList.get(i));
						clickJS(accountList.get(i));
						Thread.sleep(3000);
						logMessage("Toggle button for Turning Paper Statement On Off");
						accountList = findElements(TurnPaperStatOnOffToggle);
					}
				}
				clickJS(GoBackButton, "Manage Account - Manage Statement - Paper Statement Turn On Off Go Back");
			}
		} catch (Exception e){
			logError("Error Occured inside turnPaperStatementOnOffPage " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in turnPaperStatementOnOffPage "+e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in Turn Paper Statement On Off Page", Status.FAILED);
	}
	

		System.out.println("Turn Paper Statement On Off");
		//TBU - TODO
	}

	// Method For Order Copy Of Statement BP
	private void orderCopyOfStatementPage() {
		try {
						
			if (isElementDisplayed(OrderCopyOfStatement, 10, "OrderCopyOfStatement")) {
				//click(OrderCopyOfStatement);
				clickJS(OrderCopyOfStatement, "Services - Manage Account - Order Copy Of Statement");
				clickJS(ContinueBtn, "Services - Manage Account - Order Copy Of Statement Continue");
				
				if(isElementDisplayed(OrderCopyOfStatDropDown, 10, "AddAccList")){
					
				cafFunctional.clickSelectValueFromDropDown(OrderCopyOfStatDropDown, selectAccName, "CURRENT ACCOUNT ");  //Select Account Dropdown
				clickJS(OrderCopyOfStatDropDown, "Services - Manage Account - Order Copy Of Statement Account List");
				
				String fromDate = dataLibrary.generateRabdomDate();
				setValue(FromDate, fromDate);
				String toDate = dataLibrary.generateRabdomDate();
				setValue(ToDate, toDate);

				clickJS(ContinueBtn, "Services - Manage Account - Order Copy Of Statement Continue");
				fillChargeFormPage();

				// Review Page
				String accName = getText(valAccountName);
				String fDate = getText(valFromDate);
				String tDate = getText(valToDate);
				String notification = getText(valNotification);
				// String acountName=getText(valAccountName);
				Assert.assertEquals(accName, valAccountName);
				// String FromDate=getText(valFromDate);
				Assert.assertEquals(fDate, valFromDate);
				// String ToDate=getText(valToDate);
				Assert.assertEquals(tDate, valToDate);
				// String Notification=getText(valNotification);
				Assert.assertEquals(notification, valNotification);
				
				// click on confirm
				clickJS(ConfirmBtn, "Services - Manage Account - Order Copy Of Statement Confirm");
				
				String verifySuccessMess = driver.findElement(RequestSentMessage).getText();
				String expectedSuccessMesg = "Request sent";
				Assert.assertEquals(expectedSuccessMesg, verifySuccessMess);
								
				String refNum = driver.findElement(RefNumberOCOStatTxt).getText();
				referenceNumber = refNum.substring(15, refNum.length());
								
				clickJS(backToHomeBtn, "Service - Manage Account - Order Up To Date Back btn");

			}
			} }catch (Exception e) {
			logError("Error Occured inside order copy of statement");
			injectWarningMessageToCucumberReport("Failure in Order Copy Of Statement " + e.getMessage());
			Allure.step("Order Copy Of Statement is NOT Successfull ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	// Method to fill form for charges for order copy of statement
	private void fillChargeFormPage() {
		
		try {
			String mobileNumber = "0877121212";
			String emailId = dataLibrary.generateRandomEmail();
			
			if(isElementDisplayed(PayChargesFromAccDropDown, 10, "PayChargesFromAccDropDown")){
				
				cafFunctional.clickSelectValueFromDropDown(PayChargesFromAccDropDown, selectAccName,"CURRENT ACCOUNT ");  //Select Account Dropdown
				clickJS(PayChargesFromAccDropDown, "Services - Manage Account - Order Copy Of Statement Account List");				
				clickJS(notifyRadioBtn, "Services - Manage Account - Order Copy Of Statement Notify Radio button");
							
				if (isElementDisplayed(OrderCopyOfStatNotifyOpt, 10, "OrderCopyOfStatNotifyOpt")) {
					clickJS(OrderCopyOfStatNotifyOpt, "Notification Yes Option Clicked");
					if (isElementDisplayed(OrderCopyOfStatMobileOptBtn, 10, "OrderUpToDateStatMobileOptBtn")) {
						clickJS(OrderCopyOfStatMobileOptBtn, "Notification Mobile Option Selected");
						Thread.sleep(3000);
						setValue(OrderCopyOfStatMobileNumTxt, mobileNumber);
					} else if (isElementDisplayed(OrderCopyOfStatEmailOptBtn, 10, "OrderCopyOfStatEmailOptBtn")) {
						clickJS(OrderCopyOfStatEmailOptBtn, "Notification Email Option Selected");
						setValue(OrderCopyOfStatEmailIdTxt, emailId);
					}
				} else {
					if (isElementDisplayed(OrderCopyOfStatNotifyNoOpt, 10, "OrderCopyOfStatNotifyNoOpt")) {
						clickJS(OrderCopyOfStatNotifyNoOpt, "Notification No Option Selected");
					} else {
						logMessage("Notification Option Not Found On Order Copy Of Statement Page");
					}

				}
				}

		} catch (InterruptedException e) {
			logError("Error Occured inside filling charges form for order copy of statement");
			injectWarningMessageToCucumberReport("Failure in Fill Charges Form for Order Copy Of Statement " + e.getMessage());
			Allure.step("Fill Charges Form for Order Copy Of Statement is NOT Successfull ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

	/*-------------------Start <Services - Manage Statement - Order Up To Date Statement>----------------------------------------
	 Function Name: Order Up To Date Statement
	 Argument Name:
	 Purpose: Method to manage Order Up To Date Statement types
	 Author Name: CAF Automation 
	 Create Date: 27-05-2021
	 Modified Date| Modified By  |Modified purpose 
	  27/05/2021      C113329     Code update
	 -------------------Start <Services - Manage Statement - Order Up To Date Statement>---------------------------------------
	 */
	
	private void orderUpToDateStatementPage() throws InterruptedException {
		try {
			String mobileNumber = "0877121212";
			String emailId = dataLibrary.generateRandomEmail();
			
			if (isElementDisplayed(OrderUpToDateStatement, 10, "OrderUpToDateStatement")) {
				clickJS(OrderUpToDateStatement, "Services - Manage Account - Order Up To Date Statement");
				
				cafFunctional.clickSelectValueFromDropDown(OrderUpToDateAccDropdown, SelectOrderUpToDateAccount, "CURRENT ACCOUNT ");
				clickJS(OrderUpToDateAccDropdown, "Service - Manage Cards -Debit Cards List");
				
				if (isElementDisplayed(OrderUpToDateNotifyYesOptBtn, 10, "OrderUpToDateNotifyYesOptBtn")) {
					clickJS(OrderUpToDateNotifyYesOptBtn, "Notification Yes Option Clicked");
					if (isElementDisplayed(OrderUpToDateStatMobileOptBtn, 10, "OrderUpToDateStatMobileOptBtn")) {
						clickJS(OrderUpToDateStatMobileOptBtn, "Notification Mobile Option Selected");
						Thread.sleep(3000);
						setValue(OrderUpToDateMobileNumText, mobileNumber);
					} else if (isElementDisplayed(OrderUpToDateStatEmailOptBtn, 10, "OrderUpToDateStatEmailOptBtn")) {
						clickJS(OrderUpToDateStatEmailOptBtn, "Notification Email Option Selected");
						setValue(OrderUpToDateEmailIdText, emailId);
					}
				} else {
					if (isElementDisplayed(OrderUpToDateNotifyNoOptBtn, 10, "OrderUpToDateNotifyNoOptBtn")) {
						clickJS(OrderUpToDateNotifyNoOptBtn, "Notification No Option Selected");
					} else {
						logMessage("Notification Option Not Found On Replace DD Card Page");
					}
				}
				clickJS(ContinueBtn, "Service - Manage Account - Order Up To Date Continue btn");
				reviewOrderUpToDateStatPage(mobileNumber);
				/*clickJS(ConfirmBtn, "Service - Manage Account - Order Up To Date Confirm btn");*/
				
				String verifySuccessMess = driver.findElement(RequestSentMessage).getText();
				String expectedSuccessMesg = "Request sent";
				Assert.assertEquals(expectedSuccessMesg, verifySuccessMess);
				
				String refNum = driver.findElement(RefNumberOUTDTxt).getText();
				referenceNumber = refNum.substring(15, refNum.length());
				
				clickJS(backToHomeBtn, "Service - Manage Account - Order Up To Date Back btn");							
				//clickJS(serviceTab, "Service - Services btn");
			}
		} catch (InterruptedException e) {
			logError("Error Occured inside order upto date statement");
			injectWarningMessageToCucumberReport("Failure in Order Upto Date Statement " + e.getMessage());
			Allure.step("Order Upto Date Statement is NOT Successfull ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}

		
	}
	
	
	
	private void reviewOrderUpToDateStatPage(String mobnum) {
		try {
			String actualOUDMobNumber = driver.findElement(reviewOUTDMobNum).getText();
			if (actualOUDMobNumber.equals(mobnum)) {
				logMessage("Mobile Number is displayed as Expexted");
				clickJS(ConfirmBtn, "Service - Manage Cards -Damange Debit Cards Confirm btn");
			} else {
				logMessage("Account Name is not as execpected on Review Page");
			}
		} catch (Exception e) {
			logError("Error Occured inside reviewReplaceDamageDebitCardPage " + e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

	
	private void verifyCreditAddAccountform() {

		try {
			clickJS(ContinueBtn, "Services - Manage Account - Add Credit Account");
			if (isElementDisplayed(ReviewPage, 10, "ReviewPage")) {
				clickJS(confirmBtn, "Services - Manage Account - Add Credit Account Confirm");
			} else {
				logError("Review Page Not Found");
			}
		} catch (Exception e) {

			logError("Error Occured inside clickCreditAddAccountContinue " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Add Credit Card Account functionality " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in Add Credit Card Account functionality", Status.FAILED);
		}
	}

	private void verifyDDAddAccountform() throws Exception {

		try {
			String accNumber = dataLibrary.generateRandomNumber(8);
			String addressLine1 = dataLibrary.getAddressLine1();
			String addressLine2 = dataLibrary.getAddressLine2();
			String addressLine3 = generateRandomString() + " " + LocalDate.now().toString();
			String DOB = dataLibrary.generateRabdomDate();
			
			
			//cafFunctional.clickSelectValueFromDropDown(accountDropDown, ValueDropDownAccount,"Demand Deposit Account");  //Select Account Dropdown
			Thread.sleep(3000);
			//clickJS(accountDropDown, "Services - Manage Account - Add Account List");
			//Thread.sleep(3000);
			setValue(DDAcountNumber, accNumber);
			setValue(DDAddressLine1, addressLine1);
			setValue(DDAddressLine2, addressLine2);
			setValue(DDAddressLine3, addressLine3);
			setValue(DDDateOfBirth, DOB);
			clickJS(ContinueBtn, "Services - Manage Account - Add Account List Continue");
			reviewAddDemandDepositAccPage(accNumber, addressLine1, addressLine2, addressLine2, DOB);
			
			String verifySuccessMess = driver.findElement(RequestSentMessage).getText();
			String expectedSuccessMesg = "Request sent";
			Assert.assertEquals(expectedSuccessMesg, verifySuccessMess);
							
			String refNum = driver.findElement(RefNumberDemandDebitAccTxt).getText();
			referenceNumber = refNum.substring(15, refNum.length());
								
			clickJS(backToHomeBtn, "Services - Manage Account - Add Demand Deposit Account Back To Home Page");
			
		} catch (Exception e) {

			logError("Error Occured inside clickAddAccountContinue " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Add Demand Deposit Account functionality " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in Add Demand Deposit Account functionality", Status.FAILED);
		}
	}

	private void reviewAddDemandDepositAccPage(String accNumber,String addressLine1, String addressLine2, String addressLine3, String DOB) {
		
			try{
			
			String actualAccNum = driver.findElement(reviewDDAccNum).getText();
			String actualAdd1 = driver.findElement(reviewAdd1).getText();
			String actualAdd2 = driver.findElement(reviewAdd2).getText();
			String actualAdd3 = driver.findElement(reviewAdd3).getText();
			String actualDOB = driver.findElement(reviewDOB).getText();
					
			
			//Validate Account Number Field
			if(actualAccNum.equals(accNumber))
				logMessage("Account Name is as execpected on Review Page");
			else
				logMessage("Account Name is not as execpected on Review Page");
			
			//Validate Add Line1 Field
			if(actualAdd1.equals(addressLine1))
				logMessage("Account Address Line 1 is as execpected on Review Page");
			else
				logMessage("Account Address Line 2 is not as execpected on Review Page");
			
			//Validate Add Line2 Field
			if(actualAdd2.equals(addressLine2))
				logMessage("Account Address Line 2 is as execpected on Review Page");
			else
				logMessage("Account Address Line 2 is not as execpected on Review Page");
			
			//Validate Add Line3 Field
			if(actualAdd3.equals(addressLine1))
				logMessage("Account Address line 3 is as execpected on Review Page");
			else
				logMessage("Account Address line 3 is not as execpected on Review Page");
			
			//Validate DOB Field
			if(actualDOB.equals(DOB))
				logMessage("Account DOB is as execpected on Review Page");
			else
				logMessage("Account DOB is not as execpected on Review Page");
			
			waitForPageLoaded();
			clickJS(ConfirmBtn, "Services - Manage Account - Add Account Review Page Confirm");
									
			} catch(Exception e){
				logError("Error Occured inside reviewAddDemandDepositAccPage " +e.getMessage());
				injectWarningMessageToCucumberReport("Failure in reviewAddDemandDepositAccPage "+e.getMessage());
				appendScreenshotToCucumberReport();
				Allure.step("Failure in Review Page in Add Demand Deposit Account", Status.FAILED);
			}
		}
	

	private void verifyBOILifeProtectionAddAccountform() {
		System.out.println("Method To Add BOI Life Protection Form Account");
		// TBU
	
	}

	private void verifyBOILifePentionAddAccountform() {
	
		System.out.println("Method To Add BOI Life Pention Account");
		// TBU
	}

	private void verifyBOILifeInvestAddAccountform() {
	
		System.out.println("Method To Add BOI Life Investment Account");
		// TBU
	}

	private void verifyBOIMortageAddAccountform() {
	
		System.out.println("Method To Add BOI Mortage Account");
		// TBU
	}

	private void verifyTSNRSavingAddAccountform() {
	
		System.out.println("Method To Add BOI Life Pention Account");
		// TBU
	}
	
	private void verifyAccountList() throws Exception {
		try {
			
				List<WebElement> AccntList = driver.findElements(numberOfAccountInList);
				List<WebElement> OffToggleList = driver.findElements(OffToggleBtn);
				List<WebElement> OnToggleList = driver.findElements(OnToggleBtn);
				for (int i = 0; i < AccntList.size(); i++) {
					// WebElement element=AccntList.get(i);
					if (i % 2 == 0) {
						clickJS(OffToggleList.get(i));
						
					} else {
						clickJS(OnToggleList.get(i));
						
					}
					
				}
				
				clickJS(AccountAlertsContinueBtn, "Services - Manage Account - Manage Account Alearts Continue");
				clickJS(AccountAlertsConfirmBtn, "Services - Manage Account - MAnage Account Alerts Confirm");
			
		} catch (Exception e) {
			logError("Error Occured inside verifyAccountList " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Select Account option " + e.getMessage());
			Allure.step("Failure in Select Account option ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

}

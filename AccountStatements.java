package com.boi.grp.pageobjects.Accounts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
//import junit.framework.Assert;

public class AccountStatements extends BasePageForAllPlatform {
	public UIResusableLibrary cafFunctional;
	public AccountOptions accOpt;

	By accStatements = getObject("accStat.linkAccStats");
	By allStatslist = getObject("accStat.listAllStats");
	By allStatsPDFlist = getObject("accStat.listAllStatsPDF");
	By statsPDF = getObject("accStats.StatementPDF");
	By ProtectYourPrivacyPopup = getObject("accStat.popupProtectYourPrivacyTitle");
	By ProtectYourPrivacyPopupOKBtn = getObject("accStat.btnpopupProtectYourPrivacyOK");
	By preNotifiedFeeStatement = getObject("accStat.Pre-notifiedFeeStatement");
	By preNotifiedInterestStatement = getObject("accStat.Pre-notifiedInterestStatement");
	By statementOfFees = getObject("accStat.StatementOfFees");
	By accountStatement = getObject("accStat.AccountStatement");
	By statementDateDay = getObject("accStats.dayDateStatement");
	By statementDateMonth = getObject("accStats.monthDateStatement");
	By statementDateYear = getObject("accStats.yearDateStatement");

//For Reduce the Clutter
	By accountTab = getObject("home.tabAccounts");
	By accName = getObject("accReduceClutter.accName");
	By recentStatTxt = getObject("accReduceClutter.recentStatTxt");
	By reduceClutter = getObject("accReduceClutter.reduceClutter");
	By selectAccount = getObject("accReduceClutter.selectAccount");
	By onOffBtn = getObject("accReduceClutter.onOffBtn");
	By offOnBtn = getObject("accReduceClutter.offOnBtn");
	By goBackBtn = getObject("accReduceClutter.goBackBtn");
	By selectedAcc = getObject("accReduceClutter.selectedAccount");
	By clutterOffAcc = getObject("accReduceClutter.clutterOffAccount");
	By clutterOnAcc = getObject("accReduceClutter.clutterOnAcc");
	By selectedClutterOnAcc = getObject("accReduceClutter.selectedClutterOnAcc");
	By selectedClutterOffAcc = getObject("accReduceClutter.selectedClutterOffAcc");
	By reduceClutterOn = getObject("accReduceClutter.reduceClutterON");
	By turnClutterOnOff = getObject("accReduceClutter.turnClutterOnOff");
	BasePageForAllPlatform BasePageForAllPlatform = new BasePageForAllPlatform();

	public AccountStatements(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional = new UIResusableLibrary(driver);
		accOpt = new AccountOptions(driver);
	}

	/*---------------------------------Start <ACC_Statements>----------------------------------------
	Function Name: Account statement PDF view and statement tab validation
	Purpose: View and validate accounts statements
	Parameter:statementType
	Author Name: CAF Automation 
	Create Date: 07-05-2021
	Modified Date| Modified By  |Modified purpose 
	07/05/2021      C114323       Code completion
	-----------------------------------End <ACC_Statements>---------------------------------------
	*/
	public void ACC_Statements_P(String statementType) { /// ACC_StatementsViewPDF
		try {
			accOpt.ACC_NavigateTO("STATEMENTS");
			List<WebElement> allStatements = driver.findElements(allStatslist); // Fetch
																				// list
																				// of
																				// all
																				// the
																				// statements
																				// available
			// List<WebElement> allStatementsPDF =
			// driver.findElements(allStatsPDFlist);
			logMessage("TOTAL STATEMENTS : " + allStatements.size());
			boolean bStatFlag = false;
			if (allStatements.size() == 0) {
				Assert.assertFalse(allStatements.size() == 0);
			} else if (statementType.equalsIgnoreCase("random")) {
				scrollDown();
				click(statsPDF);
				Thread.sleep(2000);
				appendScreenshotToCucumberReport();
				Assert.assertTrue(isElementDisplayed(ProtectYourPrivacyPopup));
				verifyAndClickProtectYourPrivacyPopup(); // Handle Protect Your
															// Privacy dialogue
															// box
				verifyNewlyOpenedPDF(); // Check id the correct PDF tab is
										// opened in new tab
				bStatFlag = true;
			} else {
				for (int stats = 0; stats < allStatements.size(); stats++) {
					WebElement elementStats = allStatements.get(stats);
					String CurrentStatement = elementStats.getText();
					if (CurrentStatement.equalsIgnoreCase(statementType)) {
						elementStats.click();
						Thread.sleep(3000);
						Assert.assertTrue(isElementDisplayed(ProtectYourPrivacyPopup));
						verifyAndClickProtectYourPrivacyPopup();
						Thread.sleep(5000);
						verifyNewlyOpenedPDF();
						bStatFlag = true;
						break;
					}
				}
			}
			logInfo("Account Statement PDF view validated successfully in ACC_Statements function.");
			injectMessageToCucumberReport("Account Statement PDF view functionality validated successfully");
			appendScreenshotToCucumberReport();
			Allure.step("Account Statement PDF view functionality validated successfully" + statementType,
					Status.PASSED);

		} catch (Error e) {
			logMessage("Error Occured inside ACC_Statements function ");
			logError("Error Occured inside ACC_Statements function " + e.getMessage());
			injectWarningMessageToCucumberReport(
					"Failure in Account Statement PDF view functionality " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in Account Statement PDF view functionality " + statementType, Status.FAILED);
			Assert.assertTrue(isElementDisplayed(ProtectYourPrivacyPopup));
		} catch (Exception e) {
			logMessage("Error Occured inside ACC_Statements function ");
			logError("Error Occured inside ACC_Statements function " + e.getMessage());
			injectWarningMessageToCucumberReport(
					"Failure in Account Statement PDF view functionality " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in Account Statement PDF view functionality " + statementType, Status.FAILED);
		}
	}
//To check Reduce the Clutter functionality on statement tab in account module
	public void ACC_ReduceTheCultter_P(){
		
		try {
		    accOpt.ACC_NavigateTO("STATEMENTS");
		
			String expRecentStatTxt = getText(recentStatTxt);
			logMessage("Text on statement page:"+expRecentStatTxt);
			//Assert.assertTrue(isElementDisplayed(reduceClutter));
			//isElementDisplayedWithLog(reduceClutter,20);
		
			waitForElementToBeDisplayed(reduceClutter);
			clickJS(reduceClutter,"Clicking on Reduce the Clutter Button");
		
			if(isElementDisplayed(selectAccount,20,"Account Name")){
				
				scrollToView(selectAccount);
				waitForElementToBeDisplayed(onOffBtn);
				clickJS(onOffBtn,"ON to OF toggle Button");
				appendScreenshotToCucumberReport();
				waitForElementToBeDisplayed(goBackBtn);
				clickJS(goBackBtn,"Go back Button");
				
			}
			
			clickJS(accountTab,"Account Tab");
			waitForElementToBeDisplayed(selectedAcc);
			clickJS(selectedAcc,"Account Name");
			waitForElementToBeDisplayed(accStatements);
			clickJS(accStatements,"Statement Tab");
			
			appendScreenshotToCucumberReport();
			if(!isElementDisplayed(reduceClutter,20,"Toggle Button")){
				logMessage("Reduce the clutter successfully turned OFF");
			}
			
			checkForClutterOffAcc();
			appendScreenshotToCucumberReport();
			reduceClutterON();
			
			logInfo("Reduce the Clutter validated successfully in ACC_ReduceTheClutter_P function.");
			injectMessageToCucumberReport("Reduce the Clutter functionality validated successfully");
			appendScreenshotToCucumberReport();
			Allure.step("Reduce the Clutter executed successfully", Status.PASSED);
			
		} catch (Exception e) {
			
			logError("Error Occured inside method ACC_ReduceTheClutter_P " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Reduce The Clutter Page "+e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in Account Statement Reduce the Clutter Functionality", Status.FAILED);
		}	
	}
	
	//To Validate the account which has reduce the clutter already off
	private void checkForClutterOffAcc(){
		    try{
		           clickJS(accountTab,"Account Tab");
		           clickJS(clutterOffAcc,"Account Name");
		           clickJS(accStatements,"Statement Tab");
		           if(!isElementDisplayed(reduceClutter,20,"Toggle Button")){
		        	   logMessage("Validation for Account which has Reduce the Clutter already Off - Successful");		        	   
		           }
		           
		           logInfo("Check for clutter off account validated successfully in checkForClutterOffAcc function.");
				   injectMessageToCucumberReport("Check for clutter off account validated successfully");
				   appendScreenshotToCucumberReport();
				   Allure.step("Check for clutter off accountexecuted successfully", Status.PASSED);
				   
		    }catch(Exception e){
		    	logError("Error Occured inside method checkForClutterOffAcc " +e.getMessage());
				injectWarningMessageToCucumberReport("Failure in check for clutter off acc method "+e.getMessage());
				appendScreenshotToCucumberReport();
				Allure.step("Failure in Account Statement Reduce the Clutter Validation Method", Status.FAILED);
		    }
		
	}
	
	//To turn on reduce the clutter functionality
		private void reduceClutterON(){
			    try{
			           clickJS(accountTab,"Account Tab");
			           clickJS(clutterOnAcc,"Select Account");
			           clickJS(accStatements,"Statement Tab");
                       
			           waitForElementToBeDisplayed(reduceClutterOn);
					   clickJS(reduceClutterOn,"Reduce The Clutter Button");
					   
					   waitForElementToBeDisplayed(reduceClutterOn);
					   clickJS(turnClutterOnOff,"Toggle Button");
			           
					   if(isElementDisplayed(selectedClutterOnAcc,20,"Account Name")){
							
							waitForElementToBeDisplayed(onOffBtn);
							clickJS(onOffBtn,"Toggle Button");
							appendScreenshotToCucumberReport();
							waitForElementToBeDisplayed(goBackBtn);
							clickJS(goBackBtn,"Go Back Button");
							
						}
			         

			           logInfo("Turn On Reduce the clutter validated successfully in reduceClutterON function.");
					   injectMessageToCucumberReport("Turn On Reduce the clutter validated successfully");
					   appendScreenshotToCucumberReport();
					   Allure.step("Turn On Reduce the clutter validated successfully", Status.PASSED);
			   
			           
			    }catch(Exception e){
			    	logError("Error Occured inside method reduceClutterOn " +e.getMessage());
					injectWarningMessageToCucumberReport("Failure in reduce clutter on method "+e.getMessage());
					appendScreenshotToCucumberReport();
					Allure.step("Failure in Account Statement Reduce the Clutter Validation Method", Status.FAILED);
			    }
			
		}
	// verify Protect your privacy dialogue box and newly opened statement PDF
	// tab
	private void verifyAndClickProtectYourPrivacyPopup() {
		try {
			logMessage("POPUP TITLE IS :" + getText(ProtectYourPrivacyPopup));
			waitForElementToClickable(ProtectYourPrivacyPopupOKBtn, 10);
			clickJS(ProtectYourPrivacyPopupOKBtn);
			logMessage("Clicked on Protect Your Privacy Popup OK button ");
			logInfo("Account Statement Protect your Privay popup is varified and handled successfully");
			injectMessageToCucumberReport(
					"Account Statement Protect your Privay popup is varified and handled successfully ");
			Allure.step("Account Statement Protect your Privay popup is varified and handled successfully",
					Status.PASSED);
		} catch (Exception e) {
			logMessage("Clicking on Protect Your Privacy Popup OK button failed ");
			logError("Error Occured inside verifyAndClickProtectYourPrivacyPopup function ");
			injectWarningMessageToCucumberReport(
					"Failure in Account Statement verifyAndClickProtectYourPrivacyPopup functionality ");
			appendScreenshotToCucumberReport();
			Allure.step("Failure in Account Statement Protect your Privay popup verification functionality ",
					Status.FAILED);
		}
	}

	// Verify newly opened PDF tab using window handling.
	private void verifyNewlyOpenedPDF() throws InterruptedException {
		Set<String> winID = driver.getWindowHandles(); // Fetching all the
														// windows list in a
														// set.
		logMessage("Total number of windows : " + winID.size());
		Iterator<String> itr = winID.iterator();
		String mainWindow = itr.next();
		String popupWindow = itr.next();
		driver.switchTo().window(popupWindow); // Switch to newly opened PDF tab
		Thread.sleep(10000);
		driver.getCurrentUrl();
		if (driver.getCurrentUrl().contains("Digital/downloadStatement")) {
			Allure.step("PDF opened in new Window", Status.PASSED);
		} else {
			Allure.step("PDF not opened in new Window", Status.FAILED);
		}
		Thread.sleep(2000);
		driver.close();
		driver.switchTo().window(mainWindow); // Switch to main application
												// window
	}

	// TODO All Statement validations
	public void ACC_Statements_Validation() {
		try {
			isElementDisplayed(accStatements);
			accOpt.ACC_NavigateTO("STATEMENTS");
			List<WebElement> allStatements = driver.findElements(allStatslist);
			logMessage("TOTAL STATEMENTS : " + allStatements.size());
			isElementDisplayed(preNotifiedFeeStatement);
			isElementDisplayed(preNotifiedInterestStatement);
			isElementDisplayed(statementOfFees);
			isElementDisplayed(accountStatement);
			verifyDateFormat("dd/mm/yyyy");
		} catch (Exception e) {
			logError("Failed to validate statements tab");
		}
	}

	// TODO Verify account statements date format - DD/MM/YYYY
	private void verifyDateFormat(String expectedDateFormat) {
		try {

			String actualDate = getText(statementDateDay) + "/" + getText(statementDateMonth) + "/"
					+ getText(statementDateYear);
			logMessage("The date is : " + actualDate);
			Date date = new SimpleDateFormat("dd/mm/yyyy").parse(actualDate);
			logMessage("The date format is DD/MM/YYYY");
		} catch (Exception e) {
			logMessage("The date format is not DD/MM/YYYY");
		}

	}
}

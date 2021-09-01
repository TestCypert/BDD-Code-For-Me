/* Fetch the list of account 
 * Iterate over list using iterator to select the required account
* Click on the required account
*/

package com.boi.grp.pageobjects.Accounts;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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

public class AccountOptions extends BasePageForAllPlatform {
                public UIResusableLibrary cafFunctional;
    			public DataLibrary dataLib;
                
                By tabAccounts = getObject(devTypeToGetProperty+"home.tabAccounts");
                By allAccountsList = getObject("acc.listAllAccounts");
                By firstAccount = getObject("acc.listFirstAccount");
                By accNickname = getObject("accNick.linkaccNickname");
                By accStatements = getObject("accStat.linkAccStats");
                By accTransactions = getObject("accTransact.linkAccTransacts");
                By accChequeSearch = getObject("accChequeSearch.linkaccChequeSearch");
			    By btnPayNow = getObject("accTransact.btnPayNow");
    			By btnAddBillPayee = getObject("accTransact.btnAddBillPayee");
				
				By allDepAccnts = getObject("accNTW.allDepAccnts");
            	By listDepAmount = getObject("accNTW.listDepAmount");
            	By listWDFundLinks = getObject("accNTW.listWDFundLinks");
            	By BalanceplusInterest = getObject("accNTW.BalanceplusInterest");
            	By txtDepositAmount = getObject("accNTW.txtDepositAmount");
            	By ddTransferTo = getObject("accNTW.ddTransferTo");
            	By ddTransferToMenu = getObject("accNTW.ddTransferToMenu");
            	By radiobtnEmail = getObject("accNTW.radiobtnEmail");
            	By tbEmail = getObject("accNTW.tbEmail");
            	By tbConfirmEmail = getObject("accNTW.tbConfirmEmail");
            	By btnContinue = getObject("accNTW.btnContinue");
            	By txtReview = getObject("accNTW.txtReview");
            	By btnConfirm = getObject("accNTW.btnConfirm");
            	By btnPin = getObject("accNTW.btnPin");
            	By RequestSent = getObject("accNTW.RequestSent");
            	By lnkPrint = getObject("accNTW.lnkPrint");
            	By btnBacktoHome = getObject("accNTW.btnBacktoHome");
                
                By selectAccountListAccTransFil = getObject("accTransFil.selectAccountList");
                
                BasePageForAllPlatform BasePageForAllPlatform = new BasePageForAllPlatform();
    public AccountOptions(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        cafFunctional = new UIResusableLibrary(driver);
    }

    public void ClickOnAccountsTab() throws InterruptedException {

        try {
            if (isElementDisplayed(tabAccounts)) {
                clickJS(tabAccounts);
                //clickJS(getObject("home.btntitleCards"));
                Thread.sleep(10000);
                appendScreenshotToCucumberReport();
                logMessage("Click on Accounts tab successfully in ClickOnAccountsTab function");
                injectMessageToCucumberReport("Click on Accounts tab successfully");
                Allure.step("Click on Accounts tab Successfully ", Status.PASSED);
            } else {
                logMessage("Accounts tab is not clicked ");
                injectMessageToCucumberReport("Click on Accounts tab failed");
                Allure.step("Click on Accounts tab failed ", Status.FAILED);
            }

        } catch (Exception e) {
            logError("Error Occured inside ClickOnAccountsTab the card " + e.getMessage());
            injectWarningMessageToCucumberReport("Failure in Click on Accounts tab " + e.getMessage());
            Allure.step("Click on Accounts tab NOT Successfully ", Status.FAILED);
            appendScreenshotToCucumberReport();
        }
    }             
                                                
                                /*---------------------------------Start <ACC_DiffOptions>----------------------------------------
                                Function Name: Account selection on Account home page
                                Purpose: Select account functionality
                                Parameter:accountType
                                Author Name: CAF Automation 
                                 Create Date: 26-04-2021
                                Modified Date| Modified By  |Modified purpose 
                                  27/04/2021      C114323     Code completion
                                -----------------------------------End <ACC_DiffOptions>---------------------------------------*/
  		public void ACC_DiffOptions(String accountType) {
        try {
            List<WebElement> allAccounts = driver.findElements(allAccountsList);                                                           //fetch all the list of accounts
            logMessage("Total accounts found =" + allAccounts.size());
            WebElement elementAcc = null;
            boolean bFlagAccSelect = false;
            String currentAccount = null;
            switch (accountType.toUpperCase()) {

                case "RANDOM":
                    clickJS(firstAccount);
                    bFlagAccSelect = true;
                    break;

                case "CURRENT_ACCOUNT":
                    
outer1:                for (int acc = 0; acc < allAccounts.size(); acc++) {
	                        elementAcc = allAccounts.get(acc);
	                        logMessage("element is : " + elementAcc);
	                        currentAccount = elementAcc.getText().toUpperCase();                                                   //Fetch the account name pointed by iterator
	                        logMessage("THE CURRENT ACCOUNT IS : " + currentAccount);
	                        if (!isElementDisplayed(elementAcc)) {
	                            scrollToView(elementAcc);
	                        }
	                        if (currentAccount.contains("CURRENT")) {                                                                                                                                                                            //Comparing current account with expected account
	                            elementAcc.click();
	                            bFlagAccSelect = true;
	                            break outer1;
	                            //isElementDisplayed(accNickname);
	                        } else {
	                            bFlagAccSelect = false;
	                        }
						}

                    break;

                case "CREDIT_CARD":
                    
outer2:                 for (int acc = 0; acc < allAccounts.size(); acc++) {
                        elementAcc = allAccounts.get(acc);
                        logMessage("element is : " + elementAcc);
                        currentAccount = elementAcc.getText().toUpperCase();                                      //Fetch the account name pointed by iterator
                        logMessage("THE CURRENT ACCOUNT IS : " + currentAccount);
                        if (!isElementDisplayed(elementAcc)) {
                            scrollToView(elementAcc);
                        }
                        if (currentAccount.contains("CREDIT")) {                                                                                                                                                                 //Comparing current account with expected account
                            elementAcc.click();
                            bFlagAccSelect = true;
                            break outer2;
                        } else {
                            bFlagAccSelect = false;
                        }

                    }
                    break;

                case "LOAN_ACCOUNT":  //TODO Validate For Loan Account
                    
outer3:                   for (int acc = 0; acc < allAccounts.size(); acc++) {
                        elementAcc = allAccounts.get(acc);
                        logMessage("element is : " + elementAcc);
                        currentAccount = elementAcc.getText().toUpperCase();                                      //Fetch the account name pointed by iterator
                        logMessage("THE CURRENT ACCOUNT IS : " + currentAccount);
                        if (!isElementDisplayed(elementAcc)) {
                            scrollToView(elementAcc);
                        }
                        if (currentAccount.contains("LOAN")) {                                                                                                                                                                   //Comparing current account with expected account
                            elementAcc.click();
                            bFlagAccSelect = true;
                            break outer3;
                        } else {
                            bFlagAccSelect = false;
                        }

                    }
                    break;

                case "STATEMENT_PDF":  
                    //bFlagAccSelect = dataLib.selectAccForStatPDF(allAccounts, elementAcc);
                    
outer4:                    for (int acc = 0; acc < allAccounts.size(); acc++) {
                        elementAcc = allAccounts.get(acc);
                        currentAccount = elementAcc.getText().toUpperCase();                                      //Fetch the account name pointed by iterator
                        if (!isElementDisplayed(elementAcc)) {
                            scrollToView(elementAcc);
                        }
                        if (currentAccount.contains("MKO")) {                                                                                                                                                                     //Comparing current account with expected account
                            elementAcc.click();
                            bFlagAccSelect = true;
                            break outer4;
                        } else {
                            bFlagAccSelect = false;
                        }

                    }
                    break;

                case "CHEQUE_SEARCH":
                    //bFlagAccSelect = dataLib.selectAccForChqSearch(allAccounts, elementAcc);
                    
outer5:                    for (int acc = 0; acc < allAccounts.size(); acc++) {
                        elementAcc = allAccounts.get(acc);
                        currentAccount = elementAcc.getText().toUpperCase().trim();                                      //Fetch the account name pointed by iterator
                        if (!isElementDisplayed(elementAcc)) {
                            scrollToView(elementAcc);
                        }
                        if (currentAccount.contains("CTO PAY CURRENT AC")) {                                                                                                                                                                   //Comparing current account with expected account
                            elementAcc.click();                                                               //TODO remove later
                            bFlagAccSelect = true;
                            break outer5;
                        } else {
                            bFlagAccSelect = false;
                        }
                    }

                    break;


                default:
                    logMessage("PLEASE SELECT VALID ACCOUNT");
            }

            if (bFlagAccSelect = true) {
                logMessage(accountType + " ACCOUNT SELECTED SUCCESSFLLY");
                logInfo(accountType + "Account selected successfully");
                injectMessageToCucumberReport("Account Selection successful");
                Allure.step(accountType + " ACCOUNT SELECTED SUCCESSFLLY", Status.PASSED);
            } else {
                logMessage(accountType + " ACCOUNT NOT SELECTED SUCCESSFLLY");
                logInfo(accountType + "Account not selected successfully");
                injectMessageToCucumberReport("Account Selection Failed");
                Allure.step(accountType + " ACCOUNT NOT SELECTED SUCCESSFLLY", Status.FAILED);
            }
                                
      } catch (Exception e) {
            logError("Error occured inside ACC_DiffOptions" + e.getMessage());
            injectWarningMessageToCucumberReport("Failure in selecting acccount and navigation to account summary" + e.getMessage());
            appendScreenshotToCucumberReport();
            Allure.step("Failure in account selection and navigation to account summary" + accountType, Status.FAILED);
        }
    }
               
                //Select account for Transaction filter validation 
                
                public void selectAccountFromAccountPg(String ValueToBeSelected){
                    try{
                                    List<WebElement> dropDownlist = driver.findElements(selectAccountListAccTransFil);
                                    for (WebElement element:dropDownlist) {
                                                    if(element.getText().endsWith(ValueToBeSelected)){
                                                                    appendScreenshotToCucumberReport();
                                                                    element.click();
                                                                    logMessage("Value "+ValueToBeSelected+" is selected successfully in the list , xpath = "+selectAccountListAccTransFil);
                                                                    break;
                                                    }
                                    }
                    } catch (Exception e) {
                                    logError("Value "+ValueToBeSelected+" is not found in List, where Xpath =  "+ValueToBeSelected);
                    }
            }         
     
    //Navigation to Transactions , Statements , Account Nickname , Cheque Search
    public void ACC_NavigateTO(String strTabname) {
        try {
            boolean bFlagAccOpt = false;
            if (isElementDisplayed(accNickname)) {
                switch (strTabname.toUpperCase()) {

                    case "TRANSACTIONS":
                        if (isElementDisplayed(accTransactions)) {
                            click(accTransactions);
                            bFlagAccOpt = true;
                            //logMessage("NAVIGATED TO TRANSACTION TAB");
                        } else {
                            bFlagAccOpt = false;
                            //logError("TRANSACTION TAB NOT FOUND");
                        }
                        break;

                    case "STATEMENTS":
                        if (isElementDisplayed(accStatements)) {
                            click(accStatements);
                            bFlagAccOpt = true;
                            //logMessage("NAVIGATED TO STATEMENTS TAB");
                        } else {
                            bFlagAccOpt = false;
                            //logError("STATEMENTS TAB NOT FOUND");
                        }
                        break;

                    case "ACCOUNT_NICKNAME":
                        if (isElementDisplayed(accNickname)) {
                            click(accNickname);
                            bFlagAccOpt = true;
                            //logMessage("NAVIGATED TO ACCOUNT NICKNAME TAB");
                        } else {
                            bFlagAccOpt = false;
                            //logError("ACCOUNT NICKNAME TAB NOT FOUND");
                        }
                        break;

                    case "CHEQUE_SEARCH":
                        if (isElementDisplayed(accChequeSearch)) {
                            click(accChequeSearch);
                            bFlagAccOpt = true;
                            //logMessage("NAVIGATED TO CHEQUE SEARCH TAB");
                        } else {
                            bFlagAccOpt = false;
                            //logError("CHEQUE SEARCH TAB NOT FOUND");
                        }
                        break;

                    default:
                        logMan.error("Error Occured while navigation to Accounts tabs " + strTabname);
                        break;
                }
              }
            if (bFlagAccOpt = true) {
                logMessage("ACCOUNT NAVIGATE TO " + strTabname + " TAB SUCCESSFULLY");
                injectMessageToCucumberReport("Account Summary Navigation launched successfully" + strTabname);
                appendScreenshotToCucumberReport();
                Allure.step("ACCOUNT NAVIGATE TO " + strTabname, Status.PASSED);
            } else {
                logMessage("ACCOUNT NAVIGATE TO " + strTabname + " TAB IS NOT SUCCESSFUL");
                injectMessageToCucumberReport("Account Summary Navigation failed" + strTabname);
                appendScreenshotToCucumberReport();
                Allure.step("FAILED TO ACCOUNT NAVIGATE TO " + strTabname, Status.FAILED);
            }
        } catch (Exception e) {
            logError("Error occured inside ACC_NavigateTO" + e.getMessage());
            injectWarningMessageToCucumberReport("Failure in navigation to account summary" + e.getMessage());
            appendScreenshotToCucumberReport();
            Allure.step("Failure in navigation to account summary", Status.FAILED);
        }
    }
   public void verifyAccountClickAndNavigate() throws Exception {
        String strAccountIBAN = null;
        String strParentObject = "";
        if (deviceType.equalsIgnoreCase("MobileWeb") || deviceType.equalsIgnoreCase("Tablet")) {
            //strParentObject = "xpath~//*[@class='accessibilityStyle']/following-sibling::span[contains(@class,'boi_input boi-')][text()='" + strAccountIBAN + "']";
            ScrollAndClickJS(strParentObject);
            waitForJQueryLoad();
            waitForPageLoaded();
            logMessage(" Account having IBAN  :: '" + strAccountIBAN + " ' selected ");
            waitForPageLoaded();

        } else if (deviceType.equalsIgnoreCase("Web")) {
            List<WebElement> oUIRows = driver.findElements(getObject("home.AccountIBAN_Tile"));
            logMessage("The Content Screenshot ");
            appendScreenshotToCucumberReport();
            scrollToView(getObject("home.AccountIBAN_Tile"));
            for (int j = 0; j < oUIRows.size(); j++) {
                String strRowText = oUIRows.get(j).getText().toUpperCase();
                if (strRowText.contains(strAccountIBAN.toUpperCase())) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                    logMessage("verifyAccountStatement_AllAccounts\", \":: The Content Screenshot ::");
                    js.executeScript("arguments[0].click();", oUIRows.get(j));
                    waitForElementToBeDisplayed(getObject("AccStatement.lblStatementTab"));
                    logMessage(" The Account having Last four digit of IBAN :: " + strAccountIBAN + " :: is displayed at position ::");
                    break;
                }
            }
        }

        if (isElementDisplayed(getObject("AccStatement.lblStatementTab"), 10)) {
            logMessage("Account details Drill Down Page Opened Successfully and Validated against statement tab");
        } else {
            logError(" Account or IBAN having value with last four digit :: '" + strAccountIBAN + "' not found");
            driver.close();
            driver.quit();
        }

    }     
               /*---------------------------------Start <ACC_Notice2WD>----------------------------------------
            	Function Name: Notice ti Withdraw
            	Purpose: Notice to withdraw funds from deposit account
            	Parameter: NA
            	Author Name: CAF Automation 
            	 Create Date: 26-04-2021
            	Modified Date| Modified By  |Modified purpose 
            	  27/04/2021      C114323     Code completion
            	-----------------------------------End <ACC_Notice2WD>---------------------------------------
            	*/
            	public void ACC_Notice2WD() {
            		try {
            			//TODO - Execution pending
            			logMessage("INSIDE ACC_NOTICETOWITHDRAW");
            			List<WebElement> allDepAccounts = driver.findElements(allDepAccnts);



            			logMessage("TOTAL ACCOUNTS FOUND = " + allDepAccounts.size());
            			WebElement elementDepAcc,elementDepAccWDLink,elementDepAccBal;
            			int accName;
            			double accBal;
            accSelectLoop:for (int depAcc = 0; depAcc < allDepAccounts.size(); depAcc++) {
            				elementDepAcc = allDepAccounts.get(depAcc);
            				accName = Integer.parseInt(elementDepAcc.getText().split("~")[1].trim());
            				accBal = Double.parseDouble(driver.findElement(getObject("accNTW.txtDepAmnt", accName)).getAttribute("textContent").replaceAll(",", "").replaceAll("�", ""));
            				elementDepAccWDLink = driver.findElement(getObject("accNTW.lnkWDFund",accName));
            				elementDepAccBal = driver.findElement(getObject("accNTW.txtDepAmnt",accName));
            				
            				if(isElementDisplayed(elementDepAccBal)){
            					scrollToView(elementDepAccBal);
            				}
            				
            				accBal = Double.parseDouble(elementDepAccBal.getAttribute("textContent").replace(",", "").replace("�", ""));
            				logMessage("ACCOUNT BALANCE = "+accBal);
            				if(accBal > 100.0){
            					if(elementDepAccWDLink.isEnabled()){
            						ScrollAndClickJS(elementDepAccWDLink);
            						break accSelectLoop;
            					}
            				}
            			}

            			// Code to fill WD form here
            			if (!isElementDisplayed(BalanceplusInterest)){
            				logMessage("BALANCE PLUS INTEREST CHECK");
            				if (isElementDisplayed(txtDepositAmount)) {
            					logMessage("DEPOSIT AMOUNT");
            					setValue(txtDepositAmount, "1");
            					Thread.sleep(2000);
            				}
            			}
            			
//            			cafFunctional.clickSelectValueFromDropDown(ddTransferTo,ddTransferToMenu,"SAVING");
            			click(ddTransferTo);
            			Thread.sleep(2000);
            			click(ddTransferToMenu);
            			
            /*			JavascriptExecutor js = (JavascriptExecutor) driver;  
            			js.executeScript("document.getElementById('C5__QUE_F126F8ADA2F5A283697131_widgetARIA').selectedIndex='2'");
            */
            /*			Actions act = new Actions(driver);
            			act.moveToElement(driver.findElement(ddTransferTo),10,10).click().perform();
            			Thread.sleep(2000);
            */
//            			click(ddTransferToMenu);
//            			Thread.sleep(2000);
            			click(radiobtnEmail);
            			//String email = dataLib.generateRandomEmail();
            			String email = "test@gmail.com";
            			Thread.sleep(5000);
            			setValue(tbEmail, email);
            			clickJS(tbConfirmEmail);
            			setValue(tbConfirmEmail, email);
            			Thread.sleep(5000);
            			click(btnContinue);
            			Assert.assertTrue(isElementDisplayed(txtReview));
            			Thread.sleep(5000);
            			click(btnConfirm);
            			Assert.assertTrue(isElementDisplayed(btnPin));
            			cafFunctional.Enter3of6DigitPIN("VALIDPIN", "457652");
            			cafFunctional.PushNotification_Acccept();
            			Assert.assertTrue(isElementDisplayed(RequestSent));
            			Assert.assertTrue(isElementDisplayed(lnkPrint));
            			Assert.assertTrue(isElementDisplayed(btnBacktoHome));
            			click(btnBacktoHome);
            			Assert.assertTrue(isElementDisplayed(tabAccounts,10,"Accounts Tab"));
            			
            			logMessage("FUND WITHDRAWN FROM DEPOSIT ACCOUNT SUCCESSFLLY");
            			logInfo("FUND WITHDRAWN FROM DEPOSIT ACCOUNT SUCCESSFLLY");
            			injectMessageToCucumberReport("FUND WITHDRAWN FROM DEPOSIT ACCOUNT SUCCESSFLLY");
            			Allure.step("FUND WITHDRAWN FROM DEPOSIT ACCOUNT SUCCESSFLLY", Status.PASSED);
            		} catch (Error e) {
            			logError("Error Occured inside ACC_Notice2WD " + e.getMessage());
            			injectWarningMessageToCucumberReport("Failure in fund withdraw notice" + e.getMessage());
            			Allure.step("Withdraw Fund Notice failed ", Status.FAILED);
            			appendScreenshotToCucumberReport();
            		} catch (Exception e) {
            			logError("Error Occured inside ACC_Notice2WD " + e.getMessage());
            			injectWarningMessageToCucumberReport("Failure in fund withdraw notice" + e.getMessage());
            			Allure.step("Withdraw Fund Notice failed ", Status.FAILED);
            			appendScreenshotToCucumberReport();
            		}
            	}	
}
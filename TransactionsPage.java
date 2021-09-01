package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Function/Epic: Transactions Page for an account
 * Class: Transaction Page
 * Developed on: 11/10/2018
 * Developed by: C100493
 * Update on     Updated by     Reason
 * 16/04/2019     C103403       Done code clean up activity
 */

public class TransactionsPage extends WebDriverHelper {

    public static LinkedHashMap<String, String> mapSEPATopCardDetails = new LinkedHashMap<String, String>();
    public static String strCurrType;
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public TransactionsPage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /*
    *Function: To Verify the Account Number on header of Transaction Page
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void verifyTransactionPageLoaded() throws Exception {
        String strAccountName = dataTable.getData("General_Data", "Nickname");
        if (getText(getObject("Transaction.hdrAccount")).equalsIgnoreCase(strAccountName)) {
            report.updateTestLog("verifyTransactionPageLoaded", "Account Number: '" + strAccountName + "' correctly displayed on header", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyTransactionPageLoaded", "Account Number: '" + strAccountName + "' correctly displayed on header", Status_CRAFT.FAIL);
        }
    }

    /*
    * Function: To Verify the Credit Card Account Overview details on Transaction Page
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void verifyCCAccountOverview() throws Exception {
        waitforInVisibilityOfElementLocated(getObject("launch.spinSpinner"));
        String ccAccountOverview = dataTable.getData("General_Data", "CCAccountView");
        String[] ccContentToCheck = ccAccountOverview.split("::");
        for (int i = 0; i < ccContentToCheck.length; i++) {

            String strCCContent = getText(getObject("xpath~//div[contains(@class,'tc-tab-header')]/a[text()='" + ccContentToCheck[i] + "']"));



            if (strCCContent.contains(ccContentToCheck[i])) {
                report.updateTestLog("verifyCreditCardAccountOverview", "Menu: '" + ccContentToCheck[i] + "' correctly displayed on header", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyCreditCardAccountOverview", "Menu: '" + ccContentToCheck[i] + "' not displayed on header", Status_CRAFT.FAIL);
            }
        }

    }

    public void navigateToAnotherAccount() throws Exception{
        if(isElementDisplayed(getObject("transaction.changeaccount"),5))
        {

            clickJS(getObject("transaction.changeaccount"),"another loan account is clicked");
        }

    }
    /**
     * Function to validate the Last Bill
     */

    public void verifyLastBill() throws Exception {
        if (isElementDisplayedWithLog(getObject("Transactions.LastBillSummary"),"Last Bill Summary","Transactions", 5)){
        isElementDisplayedWithLog(getObject(deviceType() + "Transactions.PayNow"),"Pay Now Button","Transactions", 5);}
    }

    /**
     * Function: To validate the Last Bill Summary Details
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
     */

    public void verifyLastBillSummaryDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String strParentObject = "xpath~//div[contains(@class,'responsive-row tc-row-part ext-row-part rgrid')]";
        homePage.verifyCheckContentPresent(strParentObject);
        if(!isMobile) {
            isElementDisplayedWithLog(getObject(deviceType()+"Transactions.CurrentPeriod"), "Current Period", "Transactions", 5);
        }
        if(isMobile) {
            isElementDisplayedWithLog(getObject(deviceType()+"Transactions.CurrentPeriod"), "Current Period", "Transactions", 5);
        }

        String ccMenuList = dataTable.getData("General_Data", "ccTransactionView");
        String[] ccContentToCheck = ccMenuList.split("::");
        List<WebElement> rowELms = driver.findElements(getObject("xpath~//*[@class='tc-card-body boi-transaction-body']/*/*[@style='position: relative']/" +
                "*[contains(@class,'boi-summary-table-sec')]/div[contains(@class,'responsive-row tc-row-part ext-row-part')]"));
        for (int i = 0; i < ccContentToCheck.length; i++) {
            String strCCTransactionContent = rowELms.get(i).getText();
            if (strCCTransactionContent.contains(ccContentToCheck[i])) {
                report.updateTestLog("verifyCreditCardAccountOverview", "Menu: '" + strCCTransactionContent + "' correctly displayed on header", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyCreditCardAccountOverview", "Menu: '" + strCCTransactionContent + "' not displayed on header", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function: To validate the Current Period subtabs icons
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
     */

    public void verifyCurrentPeriod() throws Exception {

        isElementDisplayedWithLog(getObject("Transactions.CurrentPeriod"), "Current Period", "Transactions", 5); {
            isElementDisplayedWithLog(getObject("CurrentPeriod.TransactionsSubTab"), "Transactions sub tab", "Transactions", 5);
            isElementDisplayedWithLog(getObject("CurrentPeriod.SummarySubTab"), "Summary sub tab", "Transactions", 5);
        }
    }

    /**
     * Function: To validate the transaction sub tab complete table
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
     */

    public void verifyCurrentPeriodTransactionsDetails(String sectionElement) throws Exception {
        isElementDisplayedWithLog(getObject("CurrentPeriod.TransactionsSubTab"),"Trsansaction sub tab","Transactions", 5); {
            report.updateTestLog("verifyCurrentPeriodTransactions", "Transactions sub tab is displayed", Status_CRAFT.PASS);
            clickJS(getObject("CurrentPeriod.TransactionsSubTab"), "Transactions Sub Tab under Current Period Section");
            String topHeaderAmount = dataTable.getData("General_Data", "CurrentPeriodTransactionsTopHeaderView");
            if (getText(getObject("CurrentPeriod.TransactionsSubTabAmountsHeader")).equalsIgnoreCase(topHeaderAmount)) {
                report.updateTestLog("verifyCurrentPeriodTransactions", " '" + topHeaderAmount + "' is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyCurrentPeriodTransactions", " '" + topHeaderAmount + "' is not displayed", Status_CRAFT.FAIL);
            }
            String[] arrValidation = dataTable.getData("General_Data", "CurrentPeriodTransactionDetails").split("::");
            isElementDisplayedWithLog(getObject(sectionElement),"Section element '" + sectionElement + "'","Transactions", 5); {
                String dataSectionUI = getText(getObject(sectionElement));
                for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                    String strValidateData = arrValidation[intValidate].split("::")[0];
                    if (dataSectionUI.contains(strValidateData)) {
                        report.updateTestLog("verifyCurrentPeriodTransactionsDetails", strValidateData + "' is displayed correctly ", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyCurrentPeriodTransactionsDetails", strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                    }
                }
            }
        }
    }

    /**
     * Function: To validate the Summary sub tab complete table
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
     */

    public void verifyCurrentPeriodSummaryDetails(String sectionElement) throws Exception {
        isElementDisplayedWithLog(getObject("CurrentPeriod.SummarySubTab"),"Summary sub tab","Transactions", 5); {
            report.updateTestLog("verifyCurrentPeriodSummary", "Summary sub tab is displayed", Status_CRAFT.PASS);
            clickJS(getObject("CurrentPeriod.SummarySubTab"), "Summary Sub Tab under Current Period Section");

         if(!isMobile) {
             String topHeaderAmount = dataTable.getData("General_Data", "CurrentPeriodSummaryTopHeaderView");
             if (getText(getObject("CurrentPeriod.SummarySubTabAmountsHeader")).equalsIgnoreCase(topHeaderAmount)) {
                 report.updateTestLog("verifyCurrentPeriodSummary", " '" + topHeaderAmount + "' is displayed", Status_CRAFT.PASS);
             } else {
                 report.updateTestLog("verifyCurrentPeriodSummary", " '" + topHeaderAmount + "' is not displayed", Status_CRAFT.FAIL);
             }
         }
            String[] arrValidation = dataTable.getData("General_Data", "CurrentPeriodSummaryDetails").split("::");
            String summaryText = getText(getObject(sectionElement));
            isElementDisplayedWithLog(getObject(sectionElement),"Section element '" + summaryText + "'","Transactions", 5); {
                String dataSectionUI = getText(getObject(sectionElement));
                for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                    String strValidateData = arrValidation[intValidate].split("::")[0];
                    if (dataSectionUI.contains(strValidateData)) {
                        report.updateTestLog("verifyCurrentPeriodSummaryDetails",  strValidateData + "' is displayed correctly ", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyCurrentPeriodSummaryDetails",  strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                    }
                }
            }
        }
    }

    /**
     * Function: To verify the Cash Advance tooltip in Summary Sub Tab on Transactions Page
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
     */

    public void verifySummarytooltip1() throws Exception {
        WebElement elm = driver.findElement((getObject("billSummary.lbltooltip_cash")));
    //    elm.click();
     /*   JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("billSummary.lbltooltip_cash")));
        js.executeScript("arguments[0].click();",driver.findElement(getObject("billSummary.lbltooltip_cash")));*/
        Actions actions = new Actions(driver.getWebDriver());
        actions.moveToElement(elm).click().build().perform();
       // clickJS(getObject("billSummary.lbltooltip_cash"), "Cash tooltip");
    //    waitForElementPresent(getObject(deviceType() + "Transactions.SummarySubTab.cashAdvanceTooltip"),2000);
        WebElement elm2 = driver.findElement(getObject(deviceType() + "Transactions.SummarySubTab.cashAdvanceTooltip"));
        String str = elm2.getText();
        String expmessage = dataTable.getData("General_Data", "Tooltip1");
        if (str.equals(expmessage)) {
            report.updateTestLog("Verify Tooltip1", "ToolTip message " + expmessage + " verified successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Verify Tooltip1", "ToolTip message " + expmessage + " is not verified successfully", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function: To verify the Credit payment tooltip in Summary Sub Tab on Transactions Page
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
     */

    public void verifySummarytooltip2() throws Exception {
        waitForElementPresent(getObject("billSummary.lbltooltip_credit"),5000);
        WebElement elm = driver.findElement((getObject("billSummary.lbltooltip_credit")));
            elm.click();
   //     waitForElementPresent(getObject(deviceType() + "Transactions.SummarySubTab.creditPaymentTooltip"), 2000);
        WebElement elm3 = driver.findElement(getObject(deviceType() + "Transactions.SummarySubTab.creditPaymentTooltip"));
        String str = elm3.getText();
        String expmessage = dataTable.getData("General_Data", "Tooltip2");
        if (str.equals(expmessage)) {
            report.updateTestLog("Verify Tooltip2", "ToolTip message " + expmessage + " verified successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Verify Tooltip2", "ToolTip message " + expmessage + " is not verified successfully", Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To Click on Any particular account from home page provided Account Name and IBAN
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void verifyAndClickAnyAccount() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");

        isElementDisplayedWithLog(getObject(deviceType() + "home.lnkShowmoreacc"),"Show more Account link","Transactions", 3); {
            click(getObject(deviceType() + "home.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
          wait(9500);
        }
        String strParentObject = deviceType() + "home.parentaccount_new";
        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyAndClickAnyAccount", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        ScrollToVisibleJS(strParentObject);
        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();
            if ((strRowText.contains(strAccountToCheck.toUpperCase())) && (strRowText.contains(strAccountIBAN.toUpperCase()))) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                wait(3000);
                JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                report.updateTestLog("verifyAndClickAnyAccount", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                executor.executeScript("arguments[0].click();", oUIRows.get(j));
                Thread.sleep(8500);
                Thread.sleep(9000);
                waitForElementPresent(getObject("AccStatement.lblStatementTab"), 20);
                report.updateTestLog("verifyAndClickAnyAccount", "The Account :: " + strAccountToCheck + ":: is displyed at position ::" + (j + 1) + ":: and clicked successfully.", Status_CRAFT.DONE);
                break;
            }
        }
    }

    /*
    *Function: To Verify Cheque Search in Account level page (User Story : CFPUR-73)
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void verifyChequeSearch() throws Exception {

        String sectionElement = deviceType()+"AccStatement.SearchPageCheque";

        if(deviceType.equalsIgnoreCase("Web")){
            click(getObject("w.AccStatement.lblChequeSearchTab"));
        }
        else if(deviceType.equalsIgnoreCase("MobileWeb")) {
            clickJS(getObject("mw.AccStatement.lblChequeMoreTab"),"Click More link");
            waitforVisibilityOfElementLocated(getObject("mw.AccStatement.lblChequeSearchTab"));
            if (isElementDisplayed(getObject("mw.AccStatement.lblChequeSearchTab"), 10)) {
                clickJS(getObject("mw.AccStatement.lblChequeSearchTab"), "Click Search cheque");
            }
        }
        String strChequeNumber = dataTable.getData("General_Data", "Relationship_Status");
        String strInvalidChequeNumber = dataTable.getData("General_Data", "Account_Name");
        sendKeysJS(getObject("AccStatement.txtbxChequeNumber"),strChequeNumber);
        click(getObject("AccStatement.btnSearchCheque"));

        String[] arrValidation = dataTable.getData("General_Data", "VerifyDetails").split(";");
        isElementDisplayedWithLog(getObject(sectionElement),"Section element '" + sectionElement + "'","Transactions", 5); {
            String dataSectionUI = getText(getObject(sectionElement)).toUpperCase();
            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                String strValidateHead = arrValidation[intValidate].split("::")[0];
                String strValidateData = arrValidation[intValidate].split("::")[1];
                if (dataSectionUI.contains(strValidateData.toUpperCase())) {
                    report.updateTestLog("verifyElementDetails", strValidateHead + " value '" + strValidateData + "' is displayed correctly ", Status_CRAFT.PASS);
                }else if(strValidateHead.equalsIgnoreCase("Header")){
                    if(deviceType().equalsIgnoreCase("w.")){
                        if(isElementDisplayed(getObject("xpath~//h1[@class='ecDIB  '][text()='"+strValidateData+"']"),2)){
                            report.updateTestLog("verifyElementDetails", strValidateHead + " value 'Cheque Search' is displayed correctly ", Status_CRAFT.PASS);
                        }else{
                            report.updateTestLog("verifyElementDetails", strValidateHead + " value 'Cheque Search' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                        }
                    }else{
                       if(isElementDisplayed(getObject("xpath~//h1[contains(@class,'boi-mobile-header')][text()='"+strValidateData+"']"),2)){
                           report.updateTestLog("verifyElementDetails", strValidateHead + " value 'Cheque Search' is displayed correctly ", Status_CRAFT.PASS);
                       }else{
                           report.updateTestLog("verifyElementDetails", strValidateHead + " value 'Cheque Search' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                       }
                    }

                }else {
                    report.updateTestLog("verifyElementDetails", strValidateHead + " value '" + strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                }
            }
        }
        //Click on new search
        click(getObject("AccStatement.btnNewSearch"));
        waitForElementPresent(getObject("AccStatement.btnSearchCheque"),3000);
        //Put Blank Value in search box
        click(getObject("AccStatement.btnSearchCheque"));
        isElementDisplayedWithLog(getObject("AccStatement.lblErrorInline"),"Error Content for blank value in Cheque Number text box","Transactions",7);
        //Put Invalid Value in search box
        sendKeys(getObject("AccStatement.txtbxChequeNumber"),strInvalidChequeNumber);
        clickJS(getObject("AccStatement.btnSearchCheque"), "Search Cheque");
        waitForElementPresent(getObject("AccStatement.lblErrorAlert"),3000);
        isElementDisplayedWithLog(getObject("AccStatement.lblErrorAlert"),"Error Content for Incorrect value in Cheque Number text box","Transactions",6);
    }

    /*
    *Function: Scroll to view particular  element using JS
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void ScrollToVisibleJS(String linkToVisible) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToVisible))));
            wait(2000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    /*
    *Function: To verify the transaction table details for SEPA, Debit or Credit Account
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void SEPADebitOrCreditDetails() throws Exception{
        if(deviceType.equalsIgnoreCase("Web")){
            selectSEPADebitOrCreditDetails_Web();
        }else {
            String strXpathMobile = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'Completed transactions')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
            String strXpathTable  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'Completed transactions')]/following-sibling::div/descendant::table";

            if(isElementDisplayed(getObject("xpath~"+ strXpathMobile),5)){
                selectSEPADebitOrCreditDetails_Mobile();
            }else if(isElementDisplayed(getObject("xpath~"+ strXpathTable),5)){
                selectSEPADebitOrCreditDetails_Web();
            }
            else{
                report.updateTestLog("SEPADebitOrCreditDetails", "Transactions table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
            }
        }
    }

    /*
    *Function: To verify the transaction table details for SEPA, Debit or Credit Account for Desktop
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void selectSEPADebitOrCreditDetails_Web() throws Exception{
        String strtableHeader;
        if(dataTable.getData("General_Data", "Account_Type").equalsIgnoreCase("TL")){
            strtableHeader = "Transactions";
        }else{
            strtableHeader = "Completed transactions";
        }
        int intDate = getCompletedTransactionTableColIndex("Date");
        int intDetails = getCompletedTransactionTableColIndex("Details");
        int intAmount = getCompletedTransactionTableColIndex("Amount");

        boolean bflag = false;
        String strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr";
        List<WebElement> elmTblRow = driver.findElements(By.xpath(strXpathTblRows));

        switch (dataTable.getData("General_Data", "SEPAOption").toUpperCase()){
            case "CREDIT":
                for(int i=0;i<elmTblRow.size();i++){
                    String strAmountColXpath = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr["+(i+1)+"]/td["+ intAmount +"]";
                    String strDetailsColXpath = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr["+(i+1)+"]/td["+ intDetails +"]";
                    String strDateColXpath = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr["+(i+1)+"]/td["+ intDate +"]";
                    String strViewDetailsColXpath = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr["+(i+1)+"]/td[contains(.,'View details')]";
                    if(elmTblRow.get(i).getText().toUpperCase().contains("VIEW DETAILS")){

                        strCurrType = getText(getObject("Transaction.lblAmountInCurrency")).split("Amount in ")[1];
                        mapSEPATopCardDetails.put("Details",getText(getObject("xpath~"+strDetailsColXpath)));
                        mapSEPATopCardDetails.put("Date",getText(getObject("xpath~"+strDateColXpath)));
                        mapSEPATopCardDetails.put("Amount",strCurrType + getText(getObject("xpath~"+strAmountColXpath)));

                        driver.findElement(By.xpath(strViewDetailsColXpath)).click();
                        report.updateTestLog("selectSEPADebitOrCreditDetails_Web", "Clicked on 'View details' at row number :" +(i+1), Status_CRAFT.PASS);
                        bflag = true;
                        break;
                    }
                }
                if(!bflag){
                    report.updateTestLog("selectSEPADebitOrCreditDetails_Web", "SEPA credit view details are not present in any row of Transactions table", Status_CRAFT.FAIL);
                }
                break;
            case "DEBIT":
                for(int i=0;i<elmTblRow.size();i++){
                    String strAmountColXpath = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr["+(i+1)+"]/td["+ intAmount +"]";
                    String strDetailsColXpath = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr["+(i+1)+"]/td["+ intDetails +"]";
                    String strDateColXpath = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr["+(i+1)+"]/td["+ intDate +"]";
                    String strViewDetailsColXpath = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr["+(i+1)+"]/td[contains(.,'View details')]";

                    if(elmTblRow.get(i).getText().toUpperCase().contains("VIEW DETAILS") && getText(getObject("xpath~"+strAmountColXpath)).contains("−")){
                        strCurrType = getText(getObject("Transaction.lblAmountInCurrency")).split("Amount in ")[1];
                        mapSEPATopCardDetails.put("Details",getText(getObject("xpath~"+strDetailsColXpath)));
                        mapSEPATopCardDetails.put("Date",getText(getObject("xpath~"+strDateColXpath)));
                        mapSEPATopCardDetails.put("Amount",strCurrType + getText(getObject("xpath~"+strAmountColXpath)));

                        driver.findElement(By.xpath(strViewDetailsColXpath)).click();
                        report.updateTestLog("selectSEPADebitOrCreditDetails_Web", "Clicked on 'View details' at row number :" +(i+1), Status_CRAFT.PASS);
                        bflag = true;
                        break;
                    }
                }
                if(!bflag){
                    report.updateTestLog("selectSEPADebitOrCreditDetails_Web", "SEPA Debit view details are not present in any row of Transactions table", Status_CRAFT.FAIL);
                }
                break;
            default:
                report.updateTestLog("selectSEPADebitOrCreditDetails_Web", "Please Enter a valid SEPA transfer Type: Debit or Credit", Status_CRAFT.FAIL);
        }
    }
    /**
     * Function: To verify the transaction table details for SEPA, Debit or Credit Account for Mobile Devices
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
    */
    public void selectSEPADebitOrCreditDetails_Mobile() throws Exception{
        String strtableHeader;
        if(dataTable.getData("General_Data", "Account_Type").equalsIgnoreCase("TL")){
            strtableHeader = "Transactions";
        }else{
            strtableHeader = "Completed transactions";
        }

        boolean bflag = false;
        String strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[contains(@class,'boi-plain-table-odd-row')or contains(@class,'boi-plain-table-even-row')]";
        List<WebElement> elmTblRow = driver.findElements(By.xpath(strXpathTblRows));

        switch (dataTable.getData("General_Data", "SEPAOption").toUpperCase()){
            case "CREDIT":
                for(int i=0;i<elmTblRow.size();i++){
                    String strAmountColXpath = strXpathTblRows + "["+ (i+1)+ "]/div[2]/div[1]/div[1]";
                    String strDetailsColXpath =  strXpathTblRows + "["+ (i+1)+ "]/div[1]/div[1]";
                    String strDateColXpath = strXpathTblRows + "["+ (i+1)+ "]/div[1]/div[2]" ;
                    String strViewDetailsColXpath = strXpathTblRows + "["+ (i+1)+ "]/div[2]/div[1]";

                    if(elmTblRow.get(i).getText().toUpperCase().contains("VIEW DETAILS")){

                        strCurrType = getText(getObject("Transaction.lblAmountInCurrency")).split("Amount in ")[1];
                        mapSEPATopCardDetails.put("Details",getText(getObject("xpath~"+strDetailsColXpath)));
                        mapSEPATopCardDetails.put("Date",getText(getObject("xpath~"+strDateColXpath)));
                        mapSEPATopCardDetails.put("Amount",strCurrType + getText(getObject("xpath~"+strAmountColXpath)));
                        try {
                            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(strViewDetailsColXpath)));
                            wait(2000);
                        } catch (UnreachableBrowserException e) {
                            e.printStackTrace();
                        } catch (StaleElementReferenceException e) {
                            e.printStackTrace();
                        }
                        driver.findElement(By.xpath(strViewDetailsColXpath)).click();
                        report.updateTestLog("selectSEPADebitOrCreditDetails_Web", "Clicked on 'View details' at row number :" +(i+1), Status_CRAFT.PASS);
                        bflag = true;
                        break;
                    }
                }
                if(!bflag){
                    report.updateTestLog("selectSEPADebitOrCreditDetails_Web", "SEPA credit details are not present in any row of Transactions table", Status_CRAFT.FAIL);
                }
                break;
            case "DEBIT":
                for(int i=0;i<elmTblRow.size();i++){
                    String strAmountColXpath = strXpathTblRows + "["+ i+ "]/div[1]/div[2]/div[1]";
                    String strDetailsColXpath =  strXpathTblRows + "["+ i+ "]/div[1]/div[1]";
                    String strDateColXpath = strXpathTblRows + "["+ i+ "]/div[1]/div[2]" ;
                    String strViewDetailsColXpath = strXpathTblRows + "["+ i+ "]/div[1]/div[2]/div[2]";

                    if(elmTblRow.get(i).getText().toUpperCase().contains("VIEW DETAILS") && getText(getObject("xpath~"+strAmountColXpath)).contains("−")){
                        strCurrType = getText(getObject("Transaction.lblAmountInCurrency")).split("Amount in ")[1];
                        mapSEPATopCardDetails.put("Details",getText(getObject("xpath~"+strDetailsColXpath)));
                        mapSEPATopCardDetails.put("Date",getText(getObject("xpath~"+strDateColXpath)));
                        mapSEPATopCardDetails.put("Amount",strCurrType + getText(getObject("xpath~"+strAmountColXpath)));

                        driver.findElement(By.xpath(strViewDetailsColXpath)).click();
                        report.updateTestLog("selectSEPADebitOrCreditDetails_Web", "Clicked on 'View details' at row number :" +(i+1), Status_CRAFT.PASS);
                        bflag = true;
                        break;
                    }
                }
                if(!bflag){
                    report.updateTestLog("selectSEPADebitOrCreditDetails_Web", "SEPA Debit details are not present in any row of Transactions table", Status_CRAFT.FAIL);
                }
                break;
            default:
                report.updateTestLog("selectSEPADebitOrCreditDetails_Web", "Please Enter a valid SEPA transfer Type: Debit or Credit", Status_CRAFT.FAIL);
        }
    }
    /**
     * Function: To get the number of Completed Transaction Table Columns
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
    */
    public int getCompletedTransactionTableColIndex(String colName) throws Exception {
        int colIndex = 0;
        String strtableHeader;
        String strXpathTblRows="";
        if(dataTable.getData("General_Data", "Account_Type").equalsIgnoreCase("TL")){
            strtableHeader = "Transactions";
        }else{
            strtableHeader = "Completed";
        }

        if(deviceType.equalsIgnoreCase("Web")){
            strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::th";
        }else {
            String strXpathMobile = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
            String strXpathTable  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table";

            if(isElementDisplayed(getObject("xpath~"+ strXpathMobile),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[contains(@class,'boi-plain-table-odd-row')or contains(@class,'boi-plain-table-even-row')]";
            }else if(isElementDisplayed(getObject("xpath~"+ strXpathTable),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::th";
            }
            else{
                report.updateTestLog("validateTransactionsTableColumns", "Transactions table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
            }
        }
        List<WebElement> strColHdr = driver.findElements(By.xpath(strXpathTblRows));
        for (int col = 0; col < strColHdr.size(); col++) {
            if (strColHdr.get(col).getText().equalsIgnoreCase(colName)) {
                colIndex = col+1;
                report.updateTestLog("getCompletedTransactionTableColIndex", "Column with header '"+ colName +"' found in 'Transactions/Completed Transactions' table, at index : "+ colIndex +"'", Status_CRAFT.DONE);
                break;
            }
        }
        if(colIndex==0){
            report.updateTestLog("getCompletedTransactionTableColIndex", "Column with header '"+ colName +"' is not present in Completed transaction table", Status_CRAFT.FAIL);
        }
        return colIndex;
    }

    /**
     * Function: To verify the presence of Column in Completed Transaction Table
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
    */
    public void ColumnNotPresentCompletedTransTable(String colName)throws Exception{
        int colIndex = 0;
        String strtableHeader;
        if(dataTable.getData("General_Data", "Account_Type").equalsIgnoreCase("TL")){
            strtableHeader = "Transactions";
        }else{
            strtableHeader = "Completed transactions";
        }
        List<WebElement> strColHdr = driver.findElements(By.xpath("//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/thead/tr/th"));
        for (int col = 0; col < strColHdr.size(); col++) {
            if (strColHdr.get(col).getText().equalsIgnoreCase(colName)) {
                colIndex = col+1;
                report.updateTestLog("getCompletedTransactionTableColIndex", "Column with header '"+ colName +"' found in 'Transactions/Completed Transactions' table, at index : "+ colIndex +"'", Status_CRAFT.FAIL);
                break;
            }
        }
        if(colIndex==0){report.updateTestLog("getCompletedTransactionTableColIndex", "Column with header '"+ colName +"' is NOT present in Completed transaction table", Status_CRAFT.DONE);}
    }

    /**
     * Function: To verify the presence of Column in In-Prpogress Transaction Table
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
    */
    public void ColumnNotPresentInPrgressTransTable(String colName) throws Exception{
        int colIndex = 0;
        String strtableHeader="";
        //Today's & in-progress transactions
        if (isMobile){
             strtableHeader = "In progress";
        }else{
             strtableHeader = "in-progress transactions";
        }
        String strXpathTblRows="";

        String strXpathTable;
        if(deviceType.equalsIgnoreCase("Web")){
            strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/thead/tr/th";
        }else {
            String strXpathMobile = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
            strXpathTable  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table";

            if(isElementDisplayed(getObject("xpath~"+ strXpathMobile),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[contains(@class,'boi-plain-table-odd-row')or contains(@class,'boi-plain-table-even-row')]";
            }else if(isElementDisplayed(getObject("xpath~"+ strXpathTable),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/thead/tr/th";
            }
            else{
                report.updateTestLog("validateTransactionsTableColumns", "Transactions table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
            }
        }

        List<WebElement> strColHdr = driver.findElements(By.xpath(strXpathTblRows));
        for (int col = 0; col < strColHdr.size(); col++) {
            if (strColHdr.get(col).getText().equalsIgnoreCase(colName)) {
                colIndex = col+1;
                report.updateTestLog("getCompletedTransactionTableColIndex", "Column with header '"+ colName +"' found in 'Transactions/Completed Transactions' table, at index : "+ colIndex +"'", Status_CRAFT.FAIL);
                break;
            }
        }
        if(colIndex==0){report.updateTestLog("getCompletedTransactionTableColIndex", "Column with header '"+ colName +"' is not present in Completed transaction table", Status_CRAFT.PASS);}
    }

    /**
     * Function: To get the number of Today's & In-Progress Transaction Table Columns
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
    */
    public int getInProgressTransactionTableColIndex(String colName) throws Exception {
        int colIndex = 0;
        //Today's & in-progress transactions
        String strtableHeader = "in-progress transactions";
        String strXpathTblRows="";

        String strXpathTable;
        if(deviceType.equalsIgnoreCase("Web")){
            strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left']/following-sibling::div/descendant::table/thead/tr/th";
        }else {
            String strXpathMobile = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
            strXpathTable  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table";

            if(isElementDisplayed(getObject("xpath~"+ strXpathMobile),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[contains(@class,'boi-plain-table-odd-row')or contains(@class,'boi-plain-table-even-row')]";
            }else if(isElementDisplayed(getObject("xpath~"+ strXpathTable),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/thead/tr/th";
            }
            else{
                report.updateTestLog("validateTransactionsTableColumns", "Transactions table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
            }
        }
        List<WebElement> strColHdr = driver.findElements(By.xpath(strXpathTblRows));
        for (int col = 0; col < strColHdr.size(); col++) {
            if (strColHdr.get(col).getText().equalsIgnoreCase(colName)) {
                colIndex = col+1;
                report.updateTestLog("getInProgressTransactionTableColIndex", "Column with header '"+ colName +"' found in 'In-progress & Today's Transactions' table, at index : "+ colIndex +"'", Status_CRAFT.DONE);
                break;
            }
        }
        if(colIndex==0){
            report.updateTestLog("getInProgressTransactionTableColIndex", "Column with header '"+ colName +"' is not present in 'In-progress & Today's Transactions' table", Status_CRAFT.FAIL);
        }
        return colIndex;
    }

    /**
     * Function: To Verify the Transaction details page for SEPA
     * Update on     Updated by     Reason
     * 16/04/2019     C103403      Done code clean up activity
    */
    public void SEPATransactionDetailsPage() throws Exception{

        //verify 'return to transaction list' present
        if(isElementDisplayed(getObject("Transactions.lnkReturnToTranslist"),5)){
            report.updateTestLog("SEPATransactionDetailsPage", "Link 'Return to transaction list' is displayed on view SEPA transaction details Page", Status_CRAFT.PASS);
        }else{report.updateTestLog("SEPATransactionDetailsPage", "Link 'Return to transaction list' is NOT displayed on view SEPA transaction details Page", Status_CRAFT.FAIL);}

        //verify extended top card
        List<WebElement> elmTopCard = driver.findElements(By.xpath("//div[@class='boi-transaction-extended-top-card']/descendant::div[contains(@class,'boi-ex-trans-summary-ques')]"));
        if(elmTopCard.get(0).getText().equals(mapSEPATopCardDetails.get("Details"))){
            report.updateTestLog("SEPATransactionDetailsPage", "Details column value '"+ mapSEPATopCardDetails.get("Details") +"' is correctly displayed on top card at view SEPA transaction details Page", Status_CRAFT.PASS);
        }else{report.updateTestLog("SEPATransactionDetailsPage", "Details column value '"+ mapSEPATopCardDetails.get("Details") +"' is NOT correctly displayed on top card at view SEPA transaction details Page, Actual: '"+elmTopCard.get(0).getText()+"'", Status_CRAFT.FAIL);}

        if(dataTable.getData("General_Data", "SEPAOption").equalsIgnoreCase("Debit")){
            mapSEPATopCardDetails.put("Amount",strCurrType + mapSEPATopCardDetails.get("Amount").split("−")[1]);
        }

        if(elmTopCard.get(1).getText().equals(mapSEPATopCardDetails.get("Amount"))){
            report.updateTestLog("SEPATransactionDetailsPage", "Details column value '"+ mapSEPATopCardDetails.get("Amount") +"' is correctly displayed on top card at view SEPA transaction details Page", Status_CRAFT.PASS);
        }else{report.updateTestLog("SEPATransactionDetailsPage", "Details column value '"+ mapSEPATopCardDetails.get("Amount") +"' is NOT correctly displayed on top card at view SEPA transaction details Page, Actual: '"+elmTopCard.get(1).getText()+"'", Status_CRAFT.FAIL);}

        if(elmTopCard.get(2).getText().equals(mapSEPATopCardDetails.get("Date"))){
            report.updateTestLog("SEPATransactionDetailsPage", "Date column value '"+ mapSEPATopCardDetails.get("Date") +"' is correctly displayed on top card at view SEPA transaction details Page", Status_CRAFT.PASS);
        }else{report.updateTestLog("SEPATransactionDetailsPage", "Date column value '"+ mapSEPATopCardDetails.get("Date") +"' is NOT correctly displayed on top card at view SEPA transaction details Page, Actual: '"+elmTopCard.get(2).getText()+"'", Status_CRAFT.FAIL);}

        if(dataTable.getData("General_Data", "SEPAOption").equalsIgnoreCase("Debit")){
            if(isElementDisplayed(getObject("Transaction.btnCancel"),5)){
                report.updateTestLog("SEPATransactionDetailsPage", "Cancel Button is present on Top Card for SEPA Debit on SEPA transaction details Page", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("SEPATransactionDetailsPage", "Cancel Button is NOT present on Top Card for SEPA Debit on SEPA transaction details Page", Status_CRAFT.FAIL);
            }
        }
        //validate all the fields and their tooltips
        SepaTransactionDetails();

        //validate/click to transaction link
        if(dataTable.getData("General_Data", "ReturnToTransaction").equalsIgnoreCase("Yes")){
            click(getObject("Transactions.lnkReturnToTranslist"),"Clicked on link 'Return to transaction list");
            if(isElementDisplayed(getObject("xpath~//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'Completed transactions')]/following-sibling::div/descendant::table|//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'Transactions')]/following-sibling::div/descendant::table"),5)){
                report.updateTestLog("SEPATransactionDetailsPage", "Successfully navigated back to Transaction page, upon clicking 'Return to transaction' link", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("SEPATransactionDetailsPage", "Navigation back to Transaction page, upon clicking 'Return to transaction' link is unsuccessful", Status_CRAFT.FAIL);
            }
        }
    }
    /*
    *Function: To Verify the tooltip on Transaction details for SEPA
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void SepaTransactionDetails()throws Exception{

        String[] arrFieldLabelNToolTip = dataTable.getData("General_Data", "SEPAFieldsAndToolTip").split(";;");
        for(int intLbl = 0;intLbl<arrFieldLabelNToolTip.length;intLbl++){
            String strLabel = arrFieldLabelNToolTip[intLbl].split("::")[0].trim();
            String strToolTip = arrFieldLabelNToolTip[intLbl].split("::")[1].trim();

            String strXpathLabel = "//div[@class='boi-tooltip-dark-wrap'][contains(text(),'"+strLabel+"')]/span[@class='boi-device-tooltip fa  fa-info-circle']";
            if(isElementDisplayed(getObject("xpath~"+strXpathLabel),5)){
                report.updateTestLog("SepaTransactionDetails", "SEPA field label '"+ strLabel +"' along with information icon is displayed on View SEPA details page", Status_CRAFT.PASS);
               if(intLbl>1){
                   try {
                       JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                       js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("xpath~"+strXpathLabel)));
                   } catch (UnreachableBrowserException e) {
                       e.printStackTrace();
                   } catch (StaleElementReferenceException e) {
                       e.printStackTrace();
                   }
               }

                click(getObject("xpath~"+strXpathLabel),"Clicked on the Info Icon of '"+strLabel+"'");
                String ActualToolTip1Msg = driver.findElement(By.xpath("//div[@class='boi-tooltip-dark-wrap'][contains(text(),'"+strLabel+"')]/span[2]")).getText();
                try {
                    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//div[@class='boi-transaction-extended-top-card']/descendant::div[contains(@class,'boi-ex-trans-summary-ques')][3]")));
                } catch (UnreachableBrowserException e) {
                    e.printStackTrace();
                } catch (StaleElementReferenceException e) {
                    e.printStackTrace();
                }
                driver.findElement(By.xpath("//div[@class='boi-transaction-extended-top-card']/descendant::div[contains(@class,'boi-ex-trans-summary-ques')][1]")).click();

                if(strToolTip.equals(ActualToolTip1Msg)){
                    report.updateTestLog("SepaTransactionDetails", "ToolTip verified successfully for label '"+ strLabel +"' Expected ::'"+ strToolTip +"'", Status_CRAFT.PASS);}
                else{report.updateTestLog("SepaTransactionDetails", "ToolTip verification FAILED for label '"+ strLabel +"' Expected ::'"+ strToolTip +"', Actual::'"+ ActualToolTip1Msg +"'", Status_CRAFT.FAIL);}

            }else if(strLabel.equals("Payment reference")|strLabel.equals("Remittance information")){
                boolean blag=false;
                List<WebElement> elmLbl = driver.findElements(getObject("xpath~"+strXpathLabel));
                for(int i=0;i<elmLbl.size();i++){
                    if(elmLbl.get(i).isDisplayed()){
                        report.updateTestLog("SepaTransactionDetails", "SEPA field label '"+ strLabel +"' along with information icon is displayed on View SEPA details page", Status_CRAFT.PASS);
                        try {
                            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                            js.executeScript("arguments[0].scrollIntoView();", elmLbl.get(i));
                        } catch (UnreachableBrowserException e) {
                            e.printStackTrace();
                        } catch (StaleElementReferenceException e) {
                            e.printStackTrace();
                        }

                        elmLbl.get(i).click();
                        List<WebElement> elmToolTip = driver.findElements(By.xpath("//div[@class='boi-tooltip-dark-wrap'][contains(text(),'"+strLabel+"')]/span[@class='boi_input_white boi-tooltip-content']"));
                        boolean blagToolTip = false;

                        for(int iToolTip=0;iToolTip<elmToolTip.size();iToolTip++){
                            if(elmToolTip.get(iToolTip).isDisplayed()){
                                String ActualToolTip1Msg = elmToolTip.get(iToolTip).getText();
                                driver.findElement(getObject("xpath~//div[@class='boi-transaction-extended-top-card']/descendant::div[contains(@class,'boi-ex-trans-summary-ques')][1]")).click();

                                if(strToolTip.equals(ActualToolTip1Msg)){
                                    report.updateTestLog("SepaTransactionDetails", "ToolTip verified successfully for label '"+ strLabel +"' Expected ::'"+ strToolTip +"'", Status_CRAFT.PASS);}
                                else{report.updateTestLog("SepaTransactionDetails", "ToolTip verification FAILED for label '"+ strLabel +"' Expected ::'"+ strToolTip +"', Actual::'"+ ActualToolTip1Msg +"'", Status_CRAFT.FAIL);}
                                blagToolTip = true;
                                break;
                            }
                        }
                        if(!blagToolTip){
                            report.updateTestLog("SepaTransactionDetails", "SEPA Tool Tip for label '"+ strLabel +"' is NOT displayed on View SEPA details page, Expected tooltip::'"+ strToolTip +"'", Status_CRAFT.FAIL);
                        }
                        blag=true;
                        break;
                    }
                }
                if(!blag){
                    report.updateTestLog("SepaTransactionDetails", "SEPA field label '"+ strLabel +"' along with information icon is NOT displayed on View SEPA details page", Status_CRAFT.FAIL);
                }
            }
            else{
                report.updateTestLog("SepaTransactionDetails", "SEPA field label '"+ strLabel +"' along with information icon is NOT displayed on View SEPA details page", Status_CRAFT.FAIL);
            }
        }
    }
    /*
    *Function: To Verify the SEPA details presence for In-progress transactions
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void SepaViewDetailsNotPresentInprogressTable() throws Exception{
        //Today's & in-progress transactions
        String strtableHeader = "in-progress transactions";
        String strXpathTblRows="";
        if(deviceType.equalsIgnoreCase("Web")){
            strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr";
        }else {
            String strXpathMobile = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+ strtableHeader +"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
            String strXpathTable  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+ strtableHeader +"')]/following-sibling::div/descendant::table";

            if(isElementDisplayed(getObject("xpath~"+ strXpathMobile),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[contains(@class,'boi-plain-table-odd-row')or contains(@class,'boi-plain-table-even-row')]";
            }else if(isElementDisplayed(getObject("xpath~"+ strXpathTable),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr";
            }
            else{
                report.updateTestLog("SepaViewDetailsNotPresentInprogressTable", "Transactions table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
            }
        }

        boolean bflag = false;
        List<WebElement> elmTblRow = driver.findElements(By.xpath(strXpathTblRows));
        for(int i=0;i<elmTblRow.size();i++){
            if(elmTblRow.get(i).getText().toUpperCase().contains("VIEW DETAILS")|elmTblRow.get(i).getText().toUpperCase().contains("View details or cancel")){
                bflag = true;
                report.updateTestLog("SepaViewDetailsNotPresentInprogressTable", "'View Details' or 'View details or cancel' is displayed in In-progress transaction table, at row number: "+(i+1), Status_CRAFT.FAIL);
            }
        }
        if(!bflag){
            report.updateTestLog("SepaViewDetailsNotPresentInprogressTable", "'View Details' or 'View details or cancel' is NOT displayed in In-progress transaction table in any row", Status_CRAFT.PASS);
        }
    }
    /*
    *Function: To Verify the navigation of 'Return to Transaction list' Back button on Transaction page
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void verifyReturnToTransactionLinkFunc() throws Exception{
        //validate/click to transaction link
        if(dataTable.getData("General_Data", "ReturnToTransaction").equalsIgnoreCase("Yes")){
            click(getObject("Transactions.lnkReturnToTranslist"),"Clicked on link 'Return to transaction list");
            if(isElementDisplayed(getObject("xpath~//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'Completed transactions')]/following-sibling::div/descendant::table|//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'Transactions')]/following-sibling::div/descendant::table"),5)){
                report.updateTestLog("verifyReturnToTransactionLinkFunc", "Successfully navigated back to Transaction page, upon clicking 'Return to transaction' link", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("verifyReturnToTransactionLinkFunc", "Navigation back to Transaction page, upon clicking 'Return to transaction' link is unsuccessful", Status_CRAFT.FAIL);
            }
        }
    }
    /*
    *Function: To Verify the Transactions presence
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void TransactionsOrCompletedTransactionsDisplayed() throws Exception{

        String strtableHeader;
        String strXpathTblRows="";
        String strTableXpath;
        //validating Page title
        validateAccountAsPageHeader();

        if(dataTable.getData("General_Data", "Account_Type").equalsIgnoreCase("TL")){
            strtableHeader = "Transactions";
        }else{
            strtableHeader = "Completed";
        }

        String strAmountIn = getText(getObject("Transaction.lblAmountInCurrency"));

        if(strAmountIn.equals("Amount in "+ dataTable.getData("General_Data","Currency_Symbol"))){
            report.updateTestLog("verifyTransactionsDisplayed", "'Amount in' label is correctly displayed. Expected: '"+ strAmountIn +"'", Status_CRAFT.PASS);
        }else {report.updateTestLog("verifyTransactionsDisplayed", "'Amount in' label is NOT correctly displayed. Actual: '"+ strAmountIn +"'", Status_CRAFT.FAIL);}

        if(deviceType.equalsIgnoreCase("Web")){
            strTableXpath  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table";
            strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr";
            isElementDisplayedWithLog(getObject("xpath~"+strTableXpath),"Transactions/Completed Transactions Table","Transactions", 5);
        }else {
            String strXpathMobile = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
            String strXpathTable  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table";

            if(isElementDisplayed(getObject("xpath~"+ strXpathMobile),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[contains(@class,'boi-plain-table-odd-row')or contains(@class,'boi-plain-table-even-row')]";
                report.updateTestLog("verifyTransactionsDisplayed", "Transactions/Completed Transactions Table is displayed on Transactions page", Status_CRAFT.PASS);
            }else if(isElementDisplayed(getObject("xpath~"+ strXpathTable),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr";
                report.updateTestLog("verifyTransactionsDisplayed", "Transactions/Completed Transactions Table is displayed on Transactions page", Status_CRAFT.PASS);
            }
            else{
                report.updateTestLog("validateTransactionsTableColumns", "Transactions table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
            }
        }

        //validating column headers
        validateTransactionsTableColumns();
        //validating data is present in table
        List<WebElement> elmTblRow = driver.findElements(By.xpath(strXpathTblRows));
        if(elmTblRow.size()>0){
            report.updateTestLog("verifyTransactionsDisplayed", "'Transactions/Completed Transactions' records are displayed", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("verifyTransactionsDisplayed", "'Transactions/Completed Transactions' does not have any records displayed", Status_CRAFT.FAIL);
        }
    }
    /*
    *Function: To Validate the transaction details
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void validateTransactionsTableColumns() throws Exception{
        String strtableHeader;
        if(dataTable.getData("General_Data", "Account_Type").equalsIgnoreCase("TL")){
            strtableHeader = "Transactions";
        }else{
            strtableHeader = "Completed";
        }
        if(deviceType.equalsIgnoreCase("Web")){
            String[] arrcolumnsName = {"Date","Details","Amount","Balance"};
            for(int colname = 0;colname<arrcolumnsName.length;colname++){
                int x = getCompletedTransactionTableColIndex(arrcolumnsName[colname]);
            }
        }else {
            String strXpathMobile = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
            String strXpathTable  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table";

            if(isElementDisplayed(getObject("xpath~"+ strXpathMobile),5)){
                String[] arrShouldColsNotPresent = {"Date","Details","Amount","Balance"};
                for(int colname = 0;colname<arrShouldColsNotPresent.length;colname++){
                    ColumnNotPresentCompletedTransTable(arrShouldColsNotPresent[colname]);
                }
            }else if(isElementDisplayed(getObject("xpath~"+ strXpathTable),5)){
                String[] arrcolumnsName = {"Date","Details","Amount","Balance"};
                for(int colname = 0;colname<arrcolumnsName.length;colname++){
                    getCompletedTransactionTableColIndex(arrcolumnsName[colname]);
                }
            }
            else{
                report.updateTestLog("validateTransactionsTableColumns", "Transactions table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
            }
        }
    }

    /*
    * Function: To Verify In-Progress Transactions presence
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void InprogressTransDisplayedMobileDesktop() throws Exception{
        if (deviceType.equalsIgnoreCase("MobileWeb")){
            validateInProgressTodaysTransTableColumnsMobile();
        }else{
            InprogressTodaysTransactionsDisplayed();
        }
    }

    /*
    * Function: validateInProgressTodaysTransTableColumnsMobile :: Mobile App Automation
    * Update on     Updated by     Reason
    * 16/04/2019     C966119      Done code clean up activity
    */
    public void validateInProgressTodaysTransTableColumnsMobile() throws Exception{

        String strtableHeader = "In progress";
        String strXpathMobile = "xpath~//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+ strtableHeader +"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
        String strXpathTable  = "xpath~//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody";

        if(isElementDisplayed(getObject(strXpathMobile),80)){

            String[] arrShouldColsNotPresent = {"Date","Details","Amount","Balance"};
            for(int colname = 0;colname<arrShouldColsNotPresent.length;colname++){
                ColumnNotPresentInPrgressTransTable(arrShouldColsNotPresent[colname]);
            }
        }else if(isElementDisplayed(getObject(strXpathTable),5)){
            String[] arrcolumnsName = {"Date","Details","Amount","Balance"};
            for(int colname = 0;colname<arrcolumnsName.length;colname++){
                getInProgressTransactionTableColIndex(arrcolumnsName[colname]);
            }
        }
        else{
            report.updateTestLog("validateInProgressTodaysTransTableColumns", "Inprogress & Today's Transaction table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To Verify In-Progress Transactions presence
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void InprogressTodaysTransactionsDisplayed() throws Exception{
        String strtableHeader="in-progress transactions";
        String strXpathTblRows="";
        String strTableXpath="";
        if(deviceType.equalsIgnoreCase("Web")){
            strTableXpath = "//div[@class='boi-table-top-space col-full tc-float-left']/following-sibling::div/descendant::table/tbody";
            strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left']/following-sibling::div/descendant::table/tbody/tr";
        }else {
            String strXpathMobile = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+ strtableHeader +"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
            String strXpathTable  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody";

            if(isElementDisplayed(getObject("xpath~"+ strXpathMobile),5)){
                strTableXpath = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+ strtableHeader +"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[contains(@class,'boi-plain-table-odd-row')or contains(@class,'boi-plain-table-even-row')]";
            }else if(isElementDisplayed(getObject("xpath~"+ strXpathTable),5)){
                strTableXpath = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody";
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr";
            }
            else{
                report.updateTestLog("validateInProgressTodaysTransTableColumns", "Inprogress & Today's Transaction table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
            }
        }

            String strAmountIn = getText(getObject("Transaction.lblAmountInCurrencyInProgress"));

            if(strAmountIn.equals("Amount in "+ dataTable.getData("General_Data","Currency_Symbol"))){
                report.updateTestLog("InprogressTodaysTransactionsDisplayed", "'Amount in' label is correctly displayed. Expected: '"+ strAmountIn +"'", Status_CRAFT.PASS);
            }else {report.updateTestLog("InprogressTodaysTransactionsDisplayed", "'Amount in' label is NOT correctly displayed. Actual: '"+ strAmountIn +"'", Status_CRAFT.FAIL);}

            if (isElementDisplayed(getObject("xpath~"+strTableXpath), 5)){
                report.updateTestLog("InprogressTodaysTransactionsDisplayed", "'In-progress & Today's Transactions' Table is displayed on Transactions page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("InprogressTodaysTransactionsDisplayed", "'In-progress & Today's Transactions' Table is NOT displayed on Transactions page", Status_CRAFT.FAIL);
            }

            //validating column headers
            validateInProgressTodaysTransTableColumns();
            //validating data is present in table
            List<WebElement> elmTblRow = driver.findElements(By.xpath(strXpathTblRows));
            if(elmTblRow.size()>0){
                report.updateTestLog("InprogressTodaysTransactionsDisplayed", "'In-progress & Today's Transactions' records are displayed", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("InprogressTodaysTransactionsDisplayed", "'In-progress & Today's Transactions' does not have any records displayed", Status_CRAFT.FAIL);
            }
    }
    /*
    *Function: To Verify Todays & In-Progress Transaction table details
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void validateInProgressTodaysTransTableColumns() throws Exception{

        String strtableHeader = "in-progress transactions";
        if(deviceType.equalsIgnoreCase("Web")){
            String[] arrcolumnsName = {"Date","Details","Amount","Balance"};
            for(int colname = 0;colname<arrcolumnsName.length;colname++){
                getInProgressTransactionTableColIndex(arrcolumnsName[colname]);
            }
        }else {
            String strXpathMobile = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+ strtableHeader +"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
            String strXpathTable  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody";

            if(isElementDisplayed(getObject("xpath~"+ strXpathMobile),5)){

                String[] arrShouldColsNotPresent = {"Date","Details","Amount","Balance"};
                for(int colname = 0;colname<arrShouldColsNotPresent.length;colname++){
                    ColumnNotPresentInPrgressTransTable(arrShouldColsNotPresent[colname]);
                }
            }else if(isElementDisplayed(getObject("xpath~"+ strXpathTable),5)){
                String[] arrcolumnsName = {"Date","Details","Amount","Balance"};
                for(int colname = 0;colname<arrcolumnsName.length;colname++){
                    getInProgressTransactionTableColIndex(arrcolumnsName[colname]);
                }
            }
            else{
                report.updateTestLog("validateInProgressTodaysTransTableColumns", "Inprogress & Today's Transaction table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
            }
        }
    }
    /*
    *Function: To Verify Todays & In-Progress Transaction table presence for Mobile & Tablet devices
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void InprogressTodaysTransactionsNOTDisplayed() throws Exception{
        String strtableHeader="in-progress transactions";
        String strTableXpath="";
        boolean bflag =true;

        if(deviceType.equalsIgnoreCase("Web")){
            strTableXpath = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody";
            if(isElementDisplayed(getObject("xpath~"+ strTableXpath),5)){
                bflag =false;
            }
        }else {
            String strXpathMobile = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+ strtableHeader +"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
            String strXpathTable  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody";

            if(isElementDisplayed(getObject("xpath~"+ strXpathMobile),5)){
                bflag =false;
            }else if(isElementDisplayed(getObject("xpath~"+ strXpathTable),5)){
                bflag =false;
            }
        }

        if(bflag){
            report.updateTestLog("InprogressTodaysTransactionsNOTDisplayed", "'Inprogress & Today's Transaction' table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("InprogressTodaysTransactionsNOTDisplayed", "'Inprogress & Today's Transaction' table present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To Verify Error message on Transactions Page when no transaction is issued
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void ErrNoTransactionsIssued() throws Exception{
        //validating Page title
    //    validateAccountAsPageHeader();
        if(isElementDisplayed(getObject("Transaction.lblErrNoTransactions"),5)){
            report.updateTestLog("ErrNoTransactionsIssued", "Error message 'There have been no transactions since your last issued statement.' present in Transaction page for Accounts having no transactions", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("ErrNoTransactionsIssued", "Error message 'There have been no transactions since your last issued statement.' is NOT present in Transaction page for Accounts having no transactions", Status_CRAFT.FAIL);
        }

        if(!isElementDisplayed(getObject("Transaction.lnkExportTransaction"),5)){
            report.updateTestLog("ErrNoTransactionsIssued", "Link 'Export Transaction' with CTA icon is NOT displayed at the bottom of transaction Page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("ErrNoTransactionsIssued", "Link 'Export Transaction' with CTA icon is displayed at the bottom of transaction Page", Status_CRAFT.FAIL);
        }

        if(isElementDisplayed(getObject("Transaction.btnViewStatement"),5)){
            report.updateTestLog("ErrNoTransactionsIssued", "Button 'View Statement' present in Transaction page for Accounts having no transactions", Status_CRAFT.PASS);
            click(getObject("Transaction.btnViewStatement"),"Clicked on button View Statement");
            if(isElementDisplayed(getObject("ManageStatements.lblRecentStatements"),5)){
                report.updateTestLog("ErrNoTransactionsIssued", "Page successfully navigated to Statements page, upon clicking 'View Statement' button", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("ErrNoTransactionsIssued", "Page does not navigated to Statements page, upon clicking 'View Statement' button", Status_CRAFT.FAIL);
            }
        }else{
            report.updateTestLog("ErrNoTransactionsIssued", "Button 'View Statement' is NOT present in Transaction page for Accounts having no transactions", Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To Verify Error message on Transactions Page when unable to process request
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void ErrUnableToProcessRequest() throws Exception{
        //validating Page title
        validateAccountAsPageHeader();

        if(isElementDisplayed(getObject("Transaction.lblErrUnableToProcess"),5)){
            report.updateTestLog("ErrUnableToProcessRequest", "Error message 'We are unable to process your request at present. Please try again.' present in Transaction page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("ErrUnableToProcessRequest", "Error message 'We are unable to process your request at present. Please try again.' is NOT present in Transaction page", Status_CRAFT.FAIL);
        }
        if(isElementDisplayed(getObject("Transaction.btnViewStatement"),5)){
            report.updateTestLog("ErrUnableToProcessRequest", "Button 'View Statement' present in Transaction page for Accounts having no transactions", Status_CRAFT.PASS);
            click(getObject("Transaction.btnViewStatement"),"Clicked on button View Statement");
            if(isElementDisplayed(getObject("ManageStatements.lblRecentStatements"),5)){
                report.updateTestLog("ErrUnableToProcessRequest", "Page successfully navigated to Statements page, upon clicking 'View Statement' button", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("ErrUnableToProcessRequest", "Page does not navigated to Statements page, upon clicking 'View Statement' button", Status_CRAFT.FAIL);
            }
        }else{
            report.updateTestLog("ErrUnableToProcessRequest", "Button 'View Statement' is NOT present in Transaction page for Accounts having no transactions", Status_CRAFT.FAIL);
        }
        if(!isElementDisplayed(getObject("Transaction.lnkExportTransaction"),5)){
            report.updateTestLog("ErrUnableToProcessRequest", "Link 'Export Transaction' with CTA icon is NOT displayed at the bottom of transaction Page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("ErrUnableToProcessRequest", "Link 'Export Transaction' with CTA icon is displayed at the bottom of transaction Page", Status_CRAFT.FAIL);
        }
    }

    /**
    *Function: To enter the search filters
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void enterSearchTransactionFilters() throws Exception{
        //verify page title
        validateAccountAsPageHeader();
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject("Transaction.iconFilter") ,70)){
                //scrollToView(getObject("Transaction.iconFilter"));
                Thread.sleep(3000);
                ScrollAndClickJS("Transaction.iconFilter");
                Thread.sleep(200);
            }
        }else {
            click(getObject("Transaction.iconSearch"),"Search link Icon clicked");
        }

        waitForPageLoaded();
        //validate all search tabs are present
        selectAndEnterDateSearchFilter();
        String strScenario= dataTable.getData("General_Data", "Relationship_Status");
        if (strScenario.equals("ErrCheck")){
            return;
        }
        waitForPageLoaded();
        //Validate and enter the Transaction Type Filter
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            transactionTypeDropDownItemsNSelectMobile();
        }else{
            transactionTypeDropDownItemsNSelect();
        }
        waitForPageLoaded();
        scrollToView(getObject(deviceType()+ "Transaction.btnSearch"));
        //click on Search Button
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(deviceType()+ "Transaction.btnSearch")));
        clickJS(getObject(deviceType()+"Transaction.btnSearch"),"Search Button");

    }

    /*
    *Function: To Verify the search result table as per the set filters
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void searchTransactionResultPage() throws Exception{

        //validate top section
        topSectionSearchPage();

        String strXpathTblRows="";
        boolean bTableFound = false;
        if(deviceType.equalsIgnoreCase("Web")){
            strXpathTblRows = "xpath~//div[@class='boi-table-top-space col-full tc-float-left']/following-sibling::div/descendant::table/tbody/tr";
            isElementDisplayedWithLog(getObject("Transaction.tblSearchTableWeb"),"Search Result Table","Transactions", 5);{
                bTableFound=true;
            }

        }else {
            String strXpathMobile = "xpath~//div[@class='boi-table-top-space col-full tc-float-left']/following-sibling::div/descendant::div[contains(@class,'boi-plain-table-odd-row')or contains(@class,'boi-plain-table-even-row')]";

            if(isElementDisplayed(getObject("Transaction.tblSearchTableMobile"),5)){
                strXpathTblRows = "xpath~//div[@class='boi-table-top-space col-full tc-float-left']/following-sibling::div/descendant::div[contains(@class,'boi-plain-table-odd-row')or contains(@class,'boi-plain-table-even-row')]";
                report.updateTestLog("searchTransactionResultPage", "Search Result Table is displayed on Transactions page", Status_CRAFT.PASS);
                bTableFound=true;
            }else if(isElementDisplayed(getObject("Transaction.tblSearchTableWeb"),5)){
                strXpathTblRows = "xpath~//div[@class='boi-table-top-space col-full tc-float-left']/following-sibling::div/descendant::table/tbody/tr";
                report.updateTestLog("searchTransactionResultPage", "Search Result Table is displayed on Transactions page", Status_CRAFT.PASS);
                bTableFound=true;
            }
            else{

                report.updateTestLog("searchTransactionResultPage", "Search Result Table is NOT displayed on Transactions page", Status_CRAFT.FAIL);
            }
        }

        if(bTableFound=true){
            List<WebElement> srchTblRows = driver.findElements(getObject(strXpathTblRows));
            if(srchTblRows.size()>0){
                report.updateTestLog("searchTransactionResultPage", "'Search results' Table has records.", Status_CRAFT.DONE);
            }else {
                report.updateTestLog("searchTransactionResultPage", "'Search results' Table has NO records.", Status_CRAFT.FAIL);
            }
        }else{
            report.updateTestLog("searchTransactionResultPage", "'Search results' Table is NOT displayed.", Status_CRAFT.FAIL);
        }

        if(isElementDisplayed(getObject("Transaction.lnkExportTransaction"),5)){
            report.updateTestLog("searchTransactionResultPage", "Link 'Export Transaction' with CTA icon is displayed at the bottom of search transaction result", Status_CRAFT.PASS);
        }else{
            if (isMobile) {
                report.updateTestLog("searchTransactionResultPage", "For Mobile App : Link 'Export Transaction' with CTA icon is NOT displayed at the bottom of search transaction result", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("searchTransactionResultPage", "Link 'Export Transaction' with CTA icon is NOT displayed at the bottom of search transaction result", Status_CRAFT.FAIL);
            }
        }

    }

    /*
    *Function: To Verify Search result table details
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void topSectionSearchPage() throws Exception{

        if(isElementDisplayed(getObject(deviceType()+ "Transaction.lblSearchResults"),5)){
            report.updateTestLog("searchTransactionResultPage", "'Search result' label is displayed", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("searchTransactionResultPage", "'Search result' label is NOT displayed", Status_CRAFT.FAIL);
        }

        //validate start and end dates
        String strTransactionType = dataTable.getData("General_Data", "ReturnToTransaction");
        if(strTransactionType.equalsIgnoreCase("")){
            strTransactionType = "All";
        }

        if(isElementDisplayed(getObject("Transaction.lblStartEndDates"),5)){
            String strSearchDates = getText(getObject("Transaction.lblStartEndDates"));
            report.updateTestLog("TopSectionSearchPage", "Start & End date containing label is displayed on Transaction search Page", Status_CRAFT.DONE);
        }else{report.updateTestLog("TopSectionSearchPage", "Start & End date containing label is NOT displayed on Transaction search Page", Status_CRAFT.FAIL);}

        //validate Transaction Type
        if(isElementDisplayed(getObject("Transaction.lblSearchedTransactionType"),5)){
            String strUiTransTypeFilter = getText(getObject("Transaction.lblSearchedTransactionType"));
            if(strUiTransTypeFilter.equalsIgnoreCase(strTransactionType)){
                report.updateTestLog("TopSectionSearchPage","Transaction Type filter value is correctly displayed in Top section of on Transaction search Page, Expected:'"+strTransactionType+"'", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("TopSectionSearchPage", "Transaction Type filter value is incorrect in Top section of on Transaction search Page, Expected:'"+strTransactionType+"', Actual :'"+ strUiTransTypeFilter +"'", Status_CRAFT.FAIL);
            }
        }else{
            report.updateTestLog("TopSectionSearchPage", "Transaction Type filter containing label is NOT displayed on Transaction search Page", Status_CRAFT.FAIL);
        }

        //reset
        if(isElementDisplayed(getObject(deviceType()+"Transaction.lnkReset"),5)){
            report.updateTestLog("TopSectionSearchPage", "'Link 'Reset' is displayed on Transaction search Page", Status_CRAFT.PASS);
        }else{report.updateTestLog("TopSectionSearchPage", "'Link 'Reset' is NOT displayed on Transaction search Page", Status_CRAFT.FAIL);}

        //change search
        if(isElementDisplayed(getObject(deviceType()+ "Transaction.lnkChangeSearch"),5)){
            report.updateTestLog("TopSectionSearchPage", "'Link 'Change filter' is displayed on Transaction search Page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("TopSectionSearchPage", "'Link 'Change filter' is NOT displayed on Transaction search Page", Status_CRAFT.FAIL);}
    }

    /*
    *Function: To Export the transactions through clicking on the 'Export Transaction' link button
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void exportTransaction() throws Exception{

        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Transaction.lnkExportTransaction")));
        click(getObject("Transaction.lnkExportTransaction"),"Clicked on link Export Transaction's CTA icon");
        waitForPageLoaded();

        //validating pop up header
        if(isElementDisplayed(getObject("Transaction.titlePopupExporttransaction"),5)){
            report.updateTestLog("exportTransaction", "Popup Header ''Before you continue ...' is displayed", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("exportTransaction", "Popup Header ''Before you continue ...' is NOT displayed", Status_CRAFT.FAIL);
        }
        //validating popup title
        if(isElementDisplayed(getObject("Transaction.titlePopUpExportTransaction"),5)){
            report.updateTestLog("exportTransaction", "Popup title 'Export Transaction' is displayed", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("exportTransaction", "Popup title 'Export Transaction' is Not displayed", Status_CRAFT.FAIL);
        }

        //validating pop up message
        if(isElementDisplayed(getObject("Transaction.txtPopUpMsg"),5)){
            String UiTextMsg = getText(getObject("Transaction.txtPopUpMsg")).replace("<br>","");
            if(UiTextMsg.equals("Export up to 256 lines of your transactions in CSV format to any standard program (e.g.MS Excel).\n\nPlease note Bank of Ireland can’t be held responsible for maintaining the confidentiality of any information once it’s stored on your system.")){
                report.updateTestLog("exportTransaction", "Popup message content is as expected,Expected 'Export up to 256 lines of your transactions in CSV format to any standard program (e.g.MS Excel).    Please note Bank of Ireland can’t be held responsible for maintaining the confidentiality of any information once it’s stored on your system.'", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("exportTransaction", "Popup message content is NOT as expected,Expected 'Export up to 256 lines of your transactions in CSV format to any standard program (e.g.MS Excel).  Please note Bank of Ireland can’t be held responsible for maintaining the confidentiality of any information once it’s stored on your system.'", Status_CRAFT.FAIL);
            }
        }else{
            report.updateTestLog("exportTransaction", "Popup message content is Not displayed", Status_CRAFT.FAIL);
        }

        if(isElementDisplayed(getObject("Transaction.btnPopUpOK"),5)){
            report.updateTestLog("exportTransaction", "Pop up window has the 'OK' button", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("exportTransaction", "Pop up window does not have the 'OK' button", Status_CRAFT.FAIL);
        }

        if(isElementDisplayed(getObject("Transaction.btnPopUpCancel"),5)){
            report.updateTestLog("exportTransaction", "Pop up window has the 'Cancel' button", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("exportTransaction", "Pop up window does not have the 'Cancel' button", Status_CRAFT.FAIL);
        }

        switch (dataTable.getData("General_Data", "Operation").toUpperCase()){
            case "OK":
                click(getObject("Transaction.btnPopUpOK"),"Clicked on 'OK' button");
                break;
            case "CANCEL":
                click(getObject("Transaction.btnPopUpCancel"),"Clicked on 'OK' button");
                waitForPageLoaded();
               wait(3000);
                if(!isElementDisplayed(getObject("Transaction.btnPopUp"),5)){
                    report.updateTestLog("exportTransaction", "Cancel button is working as expected", Status_CRAFT.PASS);
                }else{
                    report.updateTestLog("exportTransaction", "Cancel button is NOT working as expected", Status_CRAFT.FAIL);
                }

                break;
            default:
                report.updateTestLog("exportTransaction", "Please provide the valid operation OK or Cancel", Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To Validate the Transaction Type Dropdown items
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void transactionTypeDropDownItemsNSelectMobile() throws Exception{

        if (isElementDisplayed(getObject("Transaction.lblTransactionType"), 5)&&(isElementDisplayed(getObject("mw.Transaction.lstTransactionType"),5))) {
            report.updateTestLog("transactionTypeDropDownItemsNSelectMobile", "Label & dropdown 'Transaction Type' is displayed ", Status_CRAFT.PASS);
            //validate default selection
            String strCurrentSelection = getText(getObject("xpath~(//select[contains(@name,'TRANSACTION')]/../following-sibling::div[1]/button)[2][text()='All']"));
            if(strCurrentSelection.equals("All")){
                report.updateTestLog("transactionTypeDropDownItemsNSelectMobile", "Default selection in Transaction Type is as expected :'All'", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("transactionTypeDropDownItemsNSelectMobile", "Default selection in Transaction Type is NOT correct,Expected :'All', Actual :'"+strCurrentSelection+"'", Status_CRAFT.FAIL);
            }
            //validate Dropdown values
            String strListItems = dataTable.getData("General_Data", "DropDownItems");
            String[] arrlistItems = strListItems.split("::");
            //String[] temp = new String[10];
            clickJS(getObject("mw.Transaction.lstTransactionType"), "Click on Transaction Type");
            Thread.sleep(800);
            for (int i = 0; i < arrlistItems.length; i++) {
                if (isElementDisplayed(getObject("xpath~//ul/li[contains(text(),'" + arrlistItems[i] + "')]"),1)) {
                    report.updateTestLog("transactionTypeDropDownItemsNSelect", "Drop down Element ' " + arrlistItems[i] + "' is present in Transaction Type dropdown", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("transactionTypeDropDownItemsNSelect", "Drop down Element ' " + arrlistItems[i] + "'  is NOT present in Transaction Type dropdown", Status_CRAFT.FAIL);
                }
            }
            String strTransactionType = dataTable.getData("General_Data", "TransactionTypeFilter");
            if(!strTransactionType.equalsIgnoreCase("")){
                //click(getObject("mw.Transaction.lstTransactionType"),"Clicked on the dropdown Transaction Type");
                 clickJS(getObject("xpath~//ul/li[contains(text(),'" + strTransactionType + "')]"), "Selected Transaction Type As  '" + strTransactionType + "'");
                 Thread.sleep(800);
                //click(getObject("xpath~//select[contains(@name,'TRANSACTIONTYPES')]/following-sibling::div/ul[@class='list']/li[@class='option'][text()='"+strTransactionType+"']"),"Selected Transaction Type '"+strTransactionType+"'");
            }
        }else{
            report.updateTestLog("transactionTypeDropDownItemsNSelectMobile", "Label or dropdown 'Transaction Type' is NOT displayed ", Status_CRAFT.PASS);
        }
    }

    /*
    *Function: To Validate the Transaction Type Dropdown items
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void transactionTypeDropDownItemsNSelect() throws Exception{

        if (isElementDisplayed(getObject("Transaction.lblTransactionType"), 5)&&(isElementDisplayed(getObject("Transaction.lstTransactionType"),5))) {
            report.updateTestLog("transactionTypeDropDownItemsNSelect", "Label & dropdown 'Transaction Type' is displayed ", Status_CRAFT.PASS);
            //validate default selection
            String strCurrentSelection = getText(getObject("xpath~//button[@id='C5__C1__QUE_C43D882310721ECB1171803_widgetARIA']"));
            if(strCurrentSelection.equals("All")){
                report.updateTestLog("transactionTypeDropDownItemsNSelect", "Default selection in Transaction Type is as expected :'All'", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("transactionTypeDropDownItemsNSelect", "Default selection in Transaction Type is NOT correct,Expected :'All', Actual :'"+strCurrentSelection+"'", Status_CRAFT.FAIL);
            }
            //validate Dropdown values
            String strListItems = dataTable.getData("General_Data", "DropDownItems");
            String[] arrlistItems = strListItems.split("::");
            String[] temp = new String[10];
            click(getObject("Transaction.lstTransactionType"));
            Thread.sleep(1000);
            for (int i = 0; i < arrlistItems.length; i++) {

                String UiValue = getText(getObject("xpath~//button[@id='C5__C1__QUE_C43D882310721ECB1171803_widgetARIA']/following-sibling::ul[@class='exp_elem_list_widget list']/li[contains(@class,'option')][" + (i + 1) + "]"));
                temp[i] = UiValue;
                if (UiValue.equalsIgnoreCase(arrlistItems[i])) {
                    report.updateTestLog("transactionTypeDropDownItemsNSelect", "Drop down Element ' " + arrlistItems[i] + "' is present in Transaction Type dropdown", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("transactionTypeDropDownItemsNSelect", "Drop down Element ' " + arrlistItems[i] + "'  is NOT present in Transaction Type dropdown", Status_CRAFT.FAIL);
                }
            }
            click(getObject("Transaction.lstTransactionType"));

            String strTransactionType = dataTable.getData("General_Data", "TransactionTypeFilter");
            if(!strTransactionType.equalsIgnoreCase("")){
                click(getObject("Transaction.lstTransactionType"),"Clicked on the dropdown Transaction Type");
                click(getObject("xpath~//li[@id='C5__C1__QUE_C43D882310721ECB1171803_item_"+strTransactionType+"']"),"Selected Transaction Type '"+strTransactionType+"'");
            }
        }else{
            report.updateTestLog("transactionTypeDropDownItemsNSelect", "Label or dropdown 'Transaction Type' is NOT displayed ", Status_CRAFT.PASS);
        }
    }

    /*
    * Function: To Validate the Date Range Error Messages
    * Created on     Created by     Reason
    * 20/06/2019    C966119
    */
    public void errDateRangeFilter() throws Exception {
        String strDates =dataTable.getData("General_Data", "Current_Balance");
        String arrDates[] = strDates.split(";");
        clickJS(getObject("Transaction.dateStartToCal") ,"Click on date Start To");
        waitForPageLoaded();
        System.out.println(arrDates[0]);
        enterDate("Transaction.dateStartTo",arrDates[0]);
        waitForPageLoaded();
        clickJS(getObject("Transaction.dateStartFromCal") ,"Click on date Start From");
        waitForPageLoaded();
        enterDate("Transaction.dateStartFrom",arrDates[1]);
        waitForPageLoaded();
        errToDateBeforeThanFromDate();
        //clickJS(getObject("Transaction.dateStartFromCal") ,"Click on date Start From");
        driver.findElement(getObject("Transaction.dateStartFrom")).sendKeys(Keys.TAB);
        waitForPageLoaded();
        sendKeysJS(getObject("Transaction.dateStartFrom") , arrDates[2]);
        driver.findElement(getObject("Transaction.dateStartFrom")).sendKeys(Keys.TAB);
        driver.findElement(getObject("Transaction.dateStartTo")).sendKeys(Keys.TAB);
        waitForPageLoaded();
        driver.findElement(getObject("Transaction.dateStartTo")).clear();
        sendKeysJS(getObject("Transaction.dateStartTo") , arrDates[2]);
        driver.findElement(getObject("Transaction.dateStartTo")).sendKeys(Keys.TAB);
        driver.findElement(getObject("Transaction.dateStartFrom")).sendKeys(Keys.TAB);
        waitForPageLoaded();
        errInvalidDateFormat();
    }

    /*
    *Function: To Verify the Error message for invlaid Date format
    * Created on     Created by     Reason
    * 20/06/2019    C966119
    */
    public void errInvalidDateFormat() throws Exception{
        if(isElementDisplayed(getObject("Transaction.errInvalidDateFormat"),5)){
            scrollToView(getObject("Transaction.errInvalidDateFormat"));
            report.updateTestLog("errInvalidDateFormat", "Error Message 'Invalid date format' is displayed", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("errInvalidDateFormat", "Error Message 'Invalid date format' is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To Verify the Error message on future date
    * Created on     Created by     Reason
    * 20/06/2019    C966119
    */
    public void errFutureDate() throws Exception{
        if(isElementDisplayed(getObject("Transaction.errFutureDateFormat"),5)){
            report.updateTestLog("errToDateBeforeThanFromDate", "Error Message 'The date range should be between twelve months ago and yesterday.' is displayed", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("errToDateBeforeThanFromDate", "Error Message 'The date range should be between twelve months ago and yesterday.' is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To enter the date in the Date search filter
    * Updated on     Updated by     Reason
    * 20/06/2019    C966119
    */
    public void selectAndEnterDateSearchFilter() throws Exception {

        String[] expectedTabs = {"All", "Month", "Date range"};

        List<WebElement> elmTabs = driver.findElements(By.xpath("//div[contains(@class,'boi-toggle-multiple-tab')]"));
        if (elmTabs.size() != 0) {
            for (int i = 0; i < elmTabs.size() && i < 3; i++) {
                if (elmTabs.get(i).getText().equals(expectedTabs[i])) {
                    report.updateTestLog("selectAndEnterDateSearchFilter", "Search Filter Tab '" + expectedTabs[i] + "' is present", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("selectAndEnterDateSearchFilter", "Search Filter Tab '" + expectedTabs[i] + "' is NOT present, Actual: '" + elmTabs.get(i) + "'", Status_CRAFT.FAIL);
                }
            }
            if (!dataTable.getData("General_Data", "TransactionSearchDateFilter").equals("")) {
                switch (dataTable.getData("General_Data", "TransactionSearchDateFilter").toUpperCase()) {

                    case "ALL":
                        click(getObject("Transaction.tabAll"), "Selected Tab All");
                        waitForPageLoaded();
                        break;
                    case "MONTH":
                        clickJS(getObject("Transaction.tabMonth"), "Selected Tab Month");
                        waitForPageLoaded();
                        isElementDisplayedWithLog(getObject("Transaction.lblDateSearchMonth"),"Label 'Select Month'","Transactions", 5);

                        isElementDisplayedWithLog(getObject("Transaction.lstSelectMonth"), "Drop Down to Select Month","Transactions", 5); {
                            if (getText(getObject("Transaction.lstSelectMonthDirectionalText")).equalsIgnoreCase("Please select a month")) {
                                report.updateTestLog("selectAndEnterDateSearchFilter", "Directional text is correctly displayed on the Select Month dropdown, Expected: 'Please select a month' ", Status_CRAFT.PASS);
                            } else {
                                report.updateTestLog("selectAndEnterDateSearchFilter", "Directional text is not as expected for Select Month, Expected: 'Please select' :: Actual:'" + getText(getObject("Transaction.lstSelectMonthDirectionalText")) + "'", Status_CRAFT.FAIL);
                            }

                            //add here code to validate the drop down shows month in decending order, first being the latest
                            String strMonth = dataTable.getData("General_Data", "SearchMonthFilter");
                            clickJS(getObject("Transaction.lstSelectMonth"), "Clicked on 'Select Month' drop down");
                            clickJS(getObject("xpath~//ul/li[contains(text(),'" + strMonth + "')]"), "Selected Month  '" + strMonth + "'");
                            //Add the code to validate Transaction Type
                        }
                        break;

                    case "DATE RANGE":
                        clickJS(getObject("Transaction.tabDateRange"), "Selected Tab Date range");
                        scrollToView(getObject("Transaction.tabTransactions"));
                        waitForPageLoaded();
                        isElementDisplayedWithLog(getObject("Transaction.lblDateRangeFrom"),"Label for Date range 'From'","Transactions", 5);
                        isElementDisplayedWithLog(getObject("Transaction.lblDateRangeTo"),"Label for Date range 'To'", "Transactions", 5);
                        isElementDisplayedWithLog(getObject("Transaction.dateStartFrom"),"Date object for date range 'From'","Transactions", 5);
                        String strStartDate = dataTable.getData("General_Data", "DateRangeFrom");
                        String strScenario= dataTable.getData("General_Data", "Relationship_Status");
                        if (strScenario.equals("ErrCheck")){
                            errDateRangeFilter();
                            return;
                        }

                        //clickJS(getObject("Transaction.dateStartFromCal") ,"Click on date Start From");
                        enterDate("Transaction.dateStartFromCal", strStartDate);
                        waitForPageLoaded();
                        Thread.sleep(2000);
                        isElementDisplayedWithLog(getObject("Transaction.dateStartTo"), "Date object for date range 'To'","Transactions",5);
                        String strEndDate = dataTable.getData("General_Data", "DateRangeTo");
                        enterDate("Transaction.dateStartToCal", strEndDate);
                        Thread.sleep(2000);
                        waitForPageLoaded();
                        break;
                    default:
                        report.updateTestLog("selectAndEnterDateSearchFilter", "Please provide the valid search filter; All/Month/Date range", Status_CRAFT.FAIL);
                }
            }

        } else {
            report.updateTestLog("selectAndEnterDateSearchFilter", "Date Filter Tabs are not present", Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To Verify Page header with the account number
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */

    public void validateAccountAsPageHeader() throws Exception{

        String accType = dataTable.getData("General_Data", "Account_Type");
        String accno = dataTable.getData("General_Data", "AccountNumber");
        String strxpath="";
        if(deviceType.equalsIgnoreCase("Web")){
            strxpath = "//h1[@class='boi-no-text-transform  ecDIB  '][contains(text(),'"+ accType + " ~ "+ accno.substring(4)+"')]" ;
            //h1[@class='boi-no-text-transform  ecDIB  '][contains(text(),'7938')]
        }else{
            accno = dataTable.getData("General_Data", "IBAN_Number");
            strxpath = "//h1[contains(text(),'"+ accno +"')]" ;
        }
        waitForElementPresent(By.xpath(strxpath), 10);
        if(driver.findElement(By.xpath(strxpath)).isDisplayed()){
            report.updateTestLog("validateAccountAsPageHeader", "Page header/title is correctly displayed on Transaction page, Expected with last four digits:" + accno , Status_CRAFT.PASS);
        }else{
            report.updateTestLog("validateAccountAsPageHeader", "Page header/title is NOT correctly displayed on Transaction page, Expected:" + accno, Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To enter Date
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void enterDate(String objDOB, String strDate) throws Exception {

        //Splitting the date
        String strYY = strDate.split("/")[2];
        String strMON = strDate.split("/")[1];
        String strDT = strDate.split("/")[0].replaceAll("^0*", "");

        //Date selection
        clickJS(getObject(objDOB), "Date Object");
        selectDropDown(getObject("launch.lstYearSavingDate"), strYY);
        selectDropDown(getObject("launch.lstMonthSavingDate"), strMON);
        clickJS(getObject("xpath~//a[.='" + strDT + "']"), "Date :" + strDT + " selected");
    }

    /*
   *Function: To Verify Load more button is present or not
   * Update on     Updated by     Reason
   * 05/06/2019     C103401
   */
    public void loadMoreTransactions() throws Exception {
        if (isElementDisplayed(getObject("Transaction.lblLoadMoreButton"), 5)) {
            report.updateTestLog("verifyLoadMore", "Load more button is displayed ", Status_CRAFT.PASS);
        }
        else {
            report.updateTestLog("verifyLoadMore", "Load more button is not displayed ", Status_CRAFT.FAIL);
        }
    }
    /*
     *Function: To Verify Load more button is present or not
     * Update on     Updated by     Reason
     * 05/06/2019     C103401
     */
    public void loadMoreTransactionsPositive() throws Exception {
        if (isElementDisplayed(getObject("xpath~//*[text()='Show more']/ancestor::a"), 5)) {
            report.updateTestLog("verifyLoadMore", "Show more button is displayed ", Status_CRAFT.PASS);
        }
        else {
            report.updateTestLog("verifyLoadMore", "Show more button is not displayed ", Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To Verify thje tooltip
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void verifyTransactionsTooltip() throws Exception {
        String textBalanceTransaction = getText(getObject(deviceType() + "transaction.lnkBalanceTransactionsExp"));
        if (isElementDisplayed(getObject(deviceType() + "transaction.lnkBalanceTransactionsExp"),6)) {
      /*      waitForElementPresent(getObject(deviceType() + "transaction.lnkBalanceTransactionsExp"), 6);
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject(deviceType() + "transaction.lnkBalanceTransactionsExp")));*/
            click(getObject(deviceType() + "transaction.lnkBalanceTransactionsExp"),"Clicked on Balance/transaction explained link");
        }
    }

    /*
    *Function: To Verify the link and text dispalyed under Balance/transactions
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void verifyTransactionsTooltipMenu()throws Exception {
        String TextToVerify = dataTable.getData("General_Data", "VerifyDetails");
        String[] arrTextToVerify = TextToVerify.split(";");
        List<WebElement> wl = driver.findElements(By.xpath("//*[@class='boi-sub-accordion-section']"));

        for (int i = 0; i < arrTextToVerify.length; i++) {
            for (int j = 0; j < wl.size(); j++) {

                if ((arrTextToVerify[i].split("::")[0]).equalsIgnoreCase(wl.get(j).getText())) {
                    String ExpLink = arrTextToVerify[i].split("::")[0];
                    wl.get(j).click();
                    waitForPageLoaded();
                    if (isElementDisplayed(getObject("xpath~//*[@class='boi-sub-accordion-section'][" + (j + 1) + "]/descendant::*[@class='boi_label_sm']"), 5)) {
                        String expText = arrTextToVerify[i].split("::")[1];
                        String actualText = getText(getObject("xpath~//*[@class='boi-sub-accordion-section'][" + (j + 1) + "]/descendant::*[@class='boi_label_sm']"));
                        if (expText.equalsIgnoreCase(actualText)) {
                            report.updateTestLog("Link '","Text '" + expText + "' is displayed correctly under '" + ExpLink + "' Link", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("Link '","Text '" + expText + "' Text is not displayed correctly", Status_CRAFT.FAIL);
                        }
                    }
                }
            }
        }
    }

    /*
    *Function: To Verify the Error message 'There are no search results for this transaction type' &'Export Transaction' CTA
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void errNoSearchResultForTransaction() throws Exception{
        //validate top section
        topSectionSearchPage();

        if(!isElementDisplayed(getObject("Transaction.lblErrNoSearchResult"),5)){
            report.updateTestLog("errNoSearchResultForTransaction", "Message 'There are no search results for this transaction type' for search record with no transaction is displayed", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("errNoSearchResultForTransaction", "Message 'There are no search results for this transaction type' for search record with no transaction is NOT displayed", Status_CRAFT.FAIL);
        }
        if(isElementDisplayed(getObject("Transaction.lnkExportTransaction"),5)){
            scrollToView(getObject("Transaction.lnkExportTransaction"));
            report.updateTestLog("errNoSearchResultForTransaction", "Link 'Export Transaction' with CTA icon is displayed at the bottom of transaction Page", Status_CRAFT.FAIL);
        }else{
            report.updateTestLog("errNoSearchResultForTransaction", "Link 'Export Transaction' with CTA icon is NOT displayed at the bottom of transaction Page", Status_CRAFT.PASS);
        }

    }

    /*
    * Function: To Verify the Error message on Date range field
    * Created on     Created by     Reason
    * 20/06/2019    C966119
    */
    public void errToDateBeforeThanFromDate() throws Exception{
        if(isElementDisplayed(getObject("Transaction.errFromDateLessThanStartDate"),5)){
            scrollToView(getObject("Transaction.errFromDateLessThanStartDate"));
            report.updateTestLog("errToDateBeforeThanFromDate", "Error Message 'The from date that you have entered is after the to date' is displayed", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("errToDateBeforeThanFromDate", "Error Message 'The from date that you have entered is after the to date' is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To Verify the Error message 'The to date that you have entered is before the from date'
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void errFromDateafterThanToDate() throws Exception{
        if(isElementDisplayed(getObject("Transaction.errEndDateLessThanStartDate"),5)){
            scrollToView(getObject("Transaction.errEndDateLessThanStartDate"));
            report.updateTestLog("errToDateBeforeThanFromDate", "Error Message 'The to date that you have entered is before the from date' is displayed", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("errToDateBeforeThanFromDate", "Error Message 'The to date that you have entered is before the from date' is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    /*
    *Function: To Verify the Exchange rate in Transaction summary list for Credit Card
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void  foreigncurrencyTransaction() throws Exception{

        waitforInVisibilityOfElementLocated(getObject("launch.spinSpinner"));
        String exchangeRate = dataTable.getData("General_Data", "Currency_Symbol").toUpperCase();
        waitforVisibilityOfElementLocated(getObject("Transaction.tblTransactionList"));
        if(isElementDisplayed(getObject("Transaction.tblTransactionList"), 8)){
            boolean bflag = false;
            String strUiCurrency = getText(getObject("Transaction.lblAmountInCreditCard")).split("in ")[1].trim();
            String strCurr="";
            switch(strUiCurrency.toUpperCase()){
                case "€":
                    strCurr = "EUR";
                    break;
                case "£":
                    strCurr = "GBP";
                    break;
            }
            do{
                List<WebElement> tblRows=null;
//                if(deviceType().equalsIgnoreCase(".mw")){
//                    tblRows = driver.findElements(By.xpath("//table[*='TransactionList']/tbody/tr"));
//                }else{
//                    tblRows = driver.findElements(By.xpath("//table[contains(@class,'boi_transaction_table')]/tbody/tr"));
//                }
//                List<WebElement> tblRows = driver.findElements(By.xpath("//table[*='TransactionList']/tbody/tr"));
                tblRows = findElements(getObject(deviceType()+ "Transactions.lblRateDesc"));
                for(int i=0;i<tblRows.size();i++){
                    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                    js.executeScript("arguments[0].scrollIntoView();", tblRows.get(i));
                    waitForPageLoaded();waitForPageLoaded();
                    report.updateTestLog("foreigncurrencyTransaction", "Exchange rate Details are  :: Screenshot "  , Status_CRAFT.SCREENSHOT);
                    if(tblRows.get(i).getText().contains(exchangeRate)){
                        report.updateTestLog("foreigncurrencyTransaction", "Exchange rate is present in :: '"+(i+1)+"' :: row of Transaction summary list for Credit Card", Status_CRAFT.PASS);
                        report.updateTestLog("foreigncurrencyTransaction", "Exchange rate Details are  :: " + tblRows.get(i).getText() , Status_CRAFT.PASS);
                        bflag = true;
                        break;
                    }
                }
                if((!bflag)&& (isElementDisplayed(getObject("xpath~//*[text()='Show more']/ancestor::a"),2))){
                    ScrollToVisibleJS(("xpath~//*[text()='Show more']/ancestor::a"));
                    clickJS(getObject("xpath~//*[text()='Show more']/ancestor::a"),"Load more");
                    waitForJQueryLoad();waitForPageLoaded();
                }
            }while((bflag==false)&&isElementDisplayed(getObject("xpath~//*[text()='Show more']/ancestor::a"),2));

            if(!bflag){
                report.updateTestLog("foreigncurrencyTransaction", "Exchange rate is NOT present in any row of Transaction summary list for Credit Card", Status_CRAFT.FAIL);
            }

        }else{
            report.updateTestLog("foreigncurrencyTransaction", "Transaction List table is not displayed for the Credit Card, on Transaction Page", Status_CRAFT.FAIL);
        }
    }

    /*
   *Function: To Verify the presence of 'Load More' CTA
   * Update on     Updated by     Reason
   * 16/04/2019     C103403      Done code clean up activity
   */
    public void verifyLoadMoreButton()throws Exception {
        isElementDisplayedWithLog(getObject("Transaction.lblLoadMoreButton"),"Load More Button", "Transactions", 6);
        click(getObject("Transaction.lblLoadMoreButton"), "Load More Button");
    }

     /*
    *Function: To Verify the Details of Footer Login
    * Update on     Updated by     Reason
    * 16/04/2019     C103403      Done code clean up activity
    */
    public void verifyDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject("Transaction.lblOriginatortext"),6)) {
            homePage.verifyElementDetails("homepage.footerLink_Login");
            scriptHelper.commonData.iterationFlag = true;
        } else {
            homePage.verifyElementDetails("homepage.footerLink");
            scriptHelper.commonData.iterationFlag = null;
        }
    }

    /*
   * Function: Smoke : Test : Transactions
   * Update on     Updated by     Reason
   * 16/04/2019     C103403      Done code clean up activity
   */
    public void TransactionsDisplayedSmoke() throws Exception{
        String strAmountIn = "";
        String strtableHeader;
        String strXpathTblRows="";
        String strTableXpath;
        click(getObject("Transaction.lblTransactionTab"));

        if(dataTable.getData("General_Data", "Account_Type").equalsIgnoreCase("TL")){
            strtableHeader = "Transactions";
        }else{
            strtableHeader = "Completed transactions";
        }

        if (isElementDisplayedWithLog(getObject("Transaction.lblAmountInCurrency") ,"Label Amount in Currency","Transactions", 20)){
            strAmountIn = getText(getObject("Transaction.lblAmountInCurrency"));
        }
        report.updateTestLog("verifyTransactionsDisplayed", " :: Content Screenshot ::", Status_CRAFT.SCREENSHOT);
        if(strAmountIn.equals("Amount in "+ dataTable.getData("General_Data","Currency_Symbol"))){
            report.updateTestLog("verifyTransactionsDisplayed", "'Amount in' label is correctly displayed. Expected: '"+ strAmountIn +"'", Status_CRAFT.DONE);
        }else {report.updateTestLog("verifyTransactionsDisplayed", "'Amount in' label is NOT correctly displayed. Actual: '"+ strAmountIn +"'", Status_CRAFT.FAIL);}

        if(deviceType.equalsIgnoreCase("Web")){
            strTableXpath  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table";
            strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr";
            isElementDisplayedWithLog(getObject("xpath~"+strTableXpath),"Transactions/Completed Transactions","Transactions", 5);

        }else {
            String strXpathMobile = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']";
            String strXpathTable  = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table";

            if(isElementDisplayed(getObject("xpath~"+ strXpathMobile),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::div[contains(@class,'boi-plain-table-odd-row')or contains(@class,'boi-plain-table-even-row')]";
                report.updateTestLog("verifyTransactionsDisplayed", "Transactions/Completed Transactions Table is displayed on Transactions page", Status_CRAFT.DONE);
            }else if(isElementDisplayed(getObject("xpath~"+ strXpathTable),5)){
                strXpathTblRows = "//div[@class='boi-table-top-space col-full tc-float-left'][contains(.,'"+strtableHeader+"')]/following-sibling::div/descendant::table/tbody/tr";
                report.updateTestLog("verifyTransactionsDisplayed", "Transactions/Completed Transactions Table is displayed on Transactions page", Status_CRAFT.DONE);
            }
            else{
                report.updateTestLog("validateTransactionsTableColumns", "Transactions table NOT present in Transaction page for Mobile/Tablet Browser view", Status_CRAFT.FAIL);
            }
        }

        //validating column headers
        validateTransactionsTableColumns();
        //validating data is present in table
        List<WebElement> elmTblRow = driver.findElements(By.xpath(strXpathTblRows));
        if(elmTblRow.size()>0){
            report.updateTestLog("verifyTransactionsDisplayed", "'Transactions/Completed Transactions' records are displayed", Status_CRAFT.DONE);
        }else{
            report.updateTestLog("verifyTransactionsDisplayed", "'Transactions/Completed Transactions' does not have any records displayed", Status_CRAFT.FAIL);
        }
    }

    /*
    CFPUR-6758: No Txns since last issued statement - make statements link clickable
    Function: To verify the View Statements button and its functionality
     */
    public void verifyViewStatements_Credit() throws Exception {
        //Validating the No transactions text statement
        String txtActual = getText(getObject("Transaction.txtnoTransactioncredit"));
        String txtExpected = "There are no transactions since your last issued statement.";
        if(txtExpected.equals(txtActual)){
            report.updateTestLog("verifyViewStatements", "'There are no transactions since your last issued statement.' text message appearing on the Account level page for credit card", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("transactions.btnviewstatementscredit"), 5)) {
                scrollToView(getObject("transactions.btnviewstatementscredit"));
                report.updateTestLog("verifyViewStatements", "View statements button is displayed on Transactions Page with no transactions", Status_CRAFT.PASS);
                click(getObject("transactions.btnviewstatementscredit"),"View statements button");
                isElementDisplayedWithLog(getObject("transactions.tabstatementsclicked"),"Statements tab open", "Account Page", 5);
            }
            else{
                report.updateTestLog("verifyViewStatements", "View statements button is not displayed on Transactions Page with no transactions", Status_CRAFT.FAIL);
            }
        }else{
            report.updateTestLog("verifyViewStatements", "No transactions present statement is not as per expectations", Status_CRAFT.FAIL);
        }
    }

    public void verifyViewStatements_BKK() throws Exception {
        //Validating the No transactions text statement
        String txtActual = getText(getObject("Transaction.txtnoTransactionbkk"));
        String txtExpected = "There have been no transactions since your last issued statement.";
        if(txtExpected.equals(txtActual)){
            report.updateTestLog("verifyViewStatements", "'There have been no transactions since your last issued statement.' text message appearing on the account level page for Book keeping Account", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("transactions.btnviewstatementsbkk"), 5)) {
                scrollToView(getObject("transactions.btnviewstatementsbkk"));
                report.updateTestLog("verifyViewStatements", "View statements button is displayed on Transactions Page with no transactions", Status_CRAFT.PASS);
                click(getObject("transactions.btnviewstatementsbkk"),"View statements button");
                isElementDisplayedWithLog(getObject("transactions.tabstatementsclicked"),"Statements tab open", "Account Page", 5);
            }
            else{
                report.updateTestLog("verifyViewStatements", "View statements button is not displayed on Transactions Page with no transactions", Status_CRAFT.FAIL);
            }
        }else{
            report.updateTestLog("verifyViewStatements", "No transactions present statement is not as per expectations", Status_CRAFT.FAIL);
        }

    }

    /**
     * Mobile App Automation Method : Check In progress Transactions elements : Bapu Joshi
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void VerifyInprogressTransactionDetails() throws Exception{
        List<WebElement> oTransaction = driver.findElements(getObject("TransactionTab.InProgressTableRows"));
        int intNumberOfoTransaction = oTransaction.size();
        int intNumberOfTransactionNew = 0;
        report.updateTestLog("VerifyInprogressTransactionDetails", "On Mobile app when we open transaction tab first time, Total In-Progress Transactions:: " + intNumberOfoTransaction, Status_CRAFT.DONE);

        if ((intNumberOfoTransaction <=24) && (!(isElementDisplayed(getObject("Transaction.lblLoadMoreButton"),1)))){
            report.updateTestLog("VerifyInprogressTransactionDetails", "On Mobile app when transactions are less that or equal to 24 'Load More' is not visible.", Status_CRAFT.PASS);
        }

        if (isElementDisplayed(getObject("Transaction.lblLoadMoreButton"),5)){
            int intCounter = 2;
            do {
                ScrollAndClickJS("Transaction.lblLoadMoreButton");
                if (intNumberOfoTransaction <= (24 * intCounter)) {
                    List<WebElement> oTransactionNew = driver.findElements(getObject("TransactionTab.InProgressTableRows"));
                    intNumberOfTransactionNew = oTransactionNew.size();
                    report.updateTestLog("VerifyInprogressTransactionDetails", "On Mobile app after clicking 'Load More' button :: " + intNumberOfTransactionNew + " :: In-Progress transactions are Present.", Status_CRAFT.PASS);
                    intCounter++;
                } else {
                    report.updateTestLog("VerifyInprogressTransactionDetails", "On clicking 'Load more' button , In-Progress transactions count is not getting added..which is not expected.", Status_CRAFT.FAIL);
                }

            } while (isElementDisplayed(getObject("Transaction.lblLoadMoreButton"), 10));
        }

        List<WebElement> oClockIcon = driver.findElements(getObject("Transaction.icnInprogressClock"));
        if (oClockIcon.size() >= 1) {
            report.updateTestLog("VerifyInprogressTransactionDetails", " Expected : Clock icons should be present for All In-Progress Transactions, " +
                    "Actual : The 'clock' icons are present for all In-Progress Transactions ", Status_CRAFT.PASS);        }
        else {
            report.updateTestLog("VerifyInprogressTransactionDetails", " Expected : Clock icons should present for In-Progress Transactions, " +
                    "Actual : The 'clock' icons are not present for In-Progress Transactions ", Status_CRAFT.FAIL);
        }
    }

    /*General Function:
      Scroll and Click on particular  element using JS
    */
    public void ScrollAndClickJS(String linkToClick) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(2000);
            //report.updateTestLog("ScrollAndClickJS", "Element To Click", Status_CRAFT.SCREENSHOT);
            JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
            executor.executeScript("arguments[0].click();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(3000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mobile App Automation Method : Verify the amount pattern in all the transactions
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void VerifyAmountPatternInTransactions() throws Exception{
        boolean blnReturnValue = false;
        boolean blnReturnFailedValue = true;
        String regExpDateCheck = "[+-]?(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)";
        List<WebElement> oTransaction = driver.findElements(getObject(deviceType() + "Transaction.lblInprogTrasacAmount"));
        if (oTransaction.size() > 0 ){
            for(int i = 0; i<oTransaction.size() ; i++){
                blnReturnValue = false;
                String strAmountTovalidate = oTransaction.get(i).getText();
                if (Pattern.matches(regExpDateCheck, strAmountTovalidate)) {
                    blnReturnValue = true;
                } else {
                    blnReturnFailedValue = false;
                    report.updateTestLog("VerifyAmountPatternInTransactions", " For In-progress transactions :: Amount value is not matching with the format (either: 1.11 OR 1,111.11 OR 1,111,111.11) and its displayed as ::" + oTransaction.get(i) + ":: which is not expected." , Status_CRAFT.FAIL);
                }
            }
            if (blnReturnValue && blnReturnFailedValue) {
                report.updateTestLog("VerifyAmountPatternInTransactions", " For All In-progress transactions :: Amount value is matching with the format (either: 1.11 OR 1,111.11 OR 1,111,111.11).", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("VerifyAmountPatternInTransactions", " For All In-progress transactions :: Amount value is NOT matching with the format (either: 1.11 OR 1,111.11 OR 1,111,111.11).", Status_CRAFT.FAIL);
            }

        }else{
            report.updateTestLog("VerifyAmountPatternInTransactions", " In-progress transactions are not available for this account.", Status_CRAFT.PASS);
        }
    }

    /**
     * Mobile App Automation Method : Verify the amount pattern in Last BillSummary
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void VerifyAmountPatternInLastBill() throws Exception{
        boolean blnReturnValue = false;
        boolean blnReturnFailedValue = true;
        String regExpDateCheck = "[+-]?(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)";
        List<WebElement> oTransaction = driver.findElements(getObject("Transaction.lblInprogTrasacAmount"));
        if (oTransaction.size() > 0 ){
            for(int i = 0; i<oTransaction.size() ; i++){
                blnReturnValue = false;
                String strAmountTovalidate = oTransaction.get(i).getText();
                if (Pattern.matches(regExpDateCheck, strAmountTovalidate)) {
                    blnReturnValue = true;
                } else {
                    blnReturnFailedValue = false;
                    report.updateTestLog("VerifyAmountPatternInTransactions", " For In-progress transactions :: Amount value is not matching with the format (either: 1.11 OR 1,111.11 OR 1,111,111.11) and its displayed as ::" + oTransaction.get(i) + ":: which is not expected." , Status_CRAFT.FAIL);
                }
            }
            if (blnReturnValue && blnReturnFailedValue) {
                report.updateTestLog("VerifyAmountPatternInTransactions", " For All In-progress transactions :: Amount value is matching with the format (either: 1.11 OR 1,111.11 OR 1,111,111.11).", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("VerifyAmountPatternInTransactions", " For All In-progress transactions :: Amount value is NOT matching with the format (either: 1.11 OR 1,111.11 OR 1,111,111.11).", Status_CRAFT.FAIL);
            }

        }else{
            report.updateTestLog("VerifyAmountPatternInTransactions", " In-progress transactions are not available for this account.", Status_CRAFT.PASS);
        }
    }

    /**
     * Mobile App Automation Method : Check Inprogress Transactions elements : Bapu Joshi
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void VerifyCompletedTransactionDetails() throws Exception{
        if (isElementDisplayed(getObject("Transaction.tblTransactionsCompleted") ,40)){
            List<WebElement> oTransaction = driver.findElements(getObject("TransactionTab.CompletedTableRows"));
            int intNumberOfoTransaction = oTransaction.size();
            int intNumberOfTransactionNew = 0;
            report.updateTestLog("VerifyCompletedTransactionDetails", "On Mobile app when we open transaction tab first time Total Completed Transactions are :: " + intNumberOfoTransaction, Status_CRAFT.DONE);

            if (isElementDisplayed(getObject("Transaction.lblLoadMoreButton"),5)){
                do {
                    ScrollAndClickJS("Transaction.lblLoadMoreButton");
                    if (intNumberOfoTransaction >= 1) {
                        List<WebElement> oTransactionNew = driver.findElements(getObject("TransactionTab.CompletedTableRows"));
                        intNumberOfTransactionNew = oTransactionNew.size();
                        report.updateTestLog("VerifyCompletedTransactionDetails", "On Mobile app after clicking 'Load More' button :: " + intNumberOfTransactionNew + " :: Completed transactions are Present.", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyCompletedTransactionDetails", "On clicking 'Load more' button , Completed transactions count is not getting added..which is not expected.", Status_CRAFT.FAIL);
                    }

                } while (isElementDisplayed(getObject("Transaction.lblLoadMoreButton"), 10));
            }else{
                report.updateTestLog("VerifyCompletedTransactionDetails", " 'Load more' button is not displayed", Status_CRAFT.PASS);
            }
        }
    }

    /**
     * Mobile App Automation Method : Verify the amount pattern in all the transactions
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void VerifyExtendedTransactions() throws Exception{
        List<WebElement> oTransactionDetails = driver.findElements(getObject(deviceType() + "TransactionTab.lnkCompTrasDetails"));
        if (oTransactionDetails.size() > 0) {
            for (int i = 0; i < oTransactionDetails.size(); i++) {
                    ScrollAndClickJS("xpath~(//span[@aria-label='View transaction details'])[1]");
                if (isElementDisplayed(getObject("TransactionTab.ReturnToDetails"), 25) && isElementDisplayed(getObject("TransactionTab.cardExtendedTransaction"), 3)) {
                    report.updateTestLog("VerifyExtendedTransactions", "Completed extended transactions Details card and Return To Details back arrow displayed successfully.", Status_CRAFT.PASS);
                    List<WebElement> oTransactionDetailsText = driver.findElements(getObject(deviceType() + "TransactionTab.lnkCompTrasDetails"));
                    for (int j = 0; j < oTransactionDetailsText.size(); j++) {
                        String strValue = oTransactionDetailsText.get(j).getText();
                        report.updateTestLog("VerifyExtendedTransactions", " Completed transactions Details With Value :: " + strValue + " :: displayed as successfully.", Status_CRAFT.PASS);
                    }
                    break;
                } else {
                    report.updateTestLog("VerifyExtendedTransactions", "Completed extended transactions Details card and Return To Details back arrow is not displayed successfully. ..which is not expected.", Status_CRAFT.FAIL);
                }
            }
        }else{
            report.updateTestLog("VerifyExtendedTransactions", " 'View transaction details' as extended transactions is not available to any transaction for this user id and account.", Status_CRAFT.DONE);
        }
    }

    /**
     * Mobile App Automation Method : Verify the amount pattern in all the transactions
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void VerifyAmountPatternCompletedTransactions() throws Exception{
        boolean blnReturnValue = false;
        String regExpDateCheck = "[+-]?(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)";
        List<WebElement> oTransaction = driver.findElements(getObject(deviceType() + "Transaction.lblCompletedTrasacAmount"));
        if (oTransaction.size() > 0 ){
            for(int i = 0; i<oTransaction.size() ; i++){
                blnReturnValue = false;
                String strAmountTovalidate = oTransaction.get(i).getText();
                if (Pattern.matches(regExpDateCheck, strAmountTovalidate)) {
                    blnReturnValue = true;
                } else {
                    report.updateTestLog("VerifyAmountPatternCompletedTransactions", " For Completed transactions :: Amount value is not matching with the format (either: 1.11 , 1,111.11) and its displayed as ::" + oTransaction.get(i) + ":: which is not expected." , Status_CRAFT.FAIL);
                }
            }
            if (blnReturnValue) {
                report.updateTestLog("VerifyAmountPatternCompletedTransactions", " For Completed transactions :: Amount value is matching with the format (either: 1.11 , 1,111.11).", Status_CRAFT.PASS);
            }
        }else {
            report.updateTestLog("VerifyAmountPatternCompletedTransactions", " Completed transactions are not available for this account.", Status_CRAFT.PASS);
        }
    }

    /**
     * Mobile App Automation Method : Verify Debit/Credit Amount
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void VerifyDebitCreditTransactionCount() throws Exception{
        List<WebElement> oCredit = driver.findElements(getObject("xpath~//div[@class='col-hidden-md col-hidden-lg col-hidden-xl']//div[contains(@onclick,'C5__C1__WORKINGELEMENTS')]//div[@style='text-align: left; ']/div/span[contains(@class,'boi-credit-amount')]"));
        if (oCredit.size() >= 1) {
            report.updateTestLog("VerifyDebitCreditTransactionCount", " Credit Amount is displayed in 'Green' and Total Credit Amount transactions are :" + oCredit.size() , Status_CRAFT.DONE);
        } else {
            report.updateTestLog("VerifyDebitCreditTransactionCount", " No Credit Amount transactions are present", Status_CRAFT.DONE);
        }
        List<WebElement> oDebit = driver.findElements(getObject("xpath~//div[@class='col-hidden-md col-hidden-lg col-hidden-xl']//div[contains(@onclick,'C5__C1__WORKINGELEMENTS')]//div[@style='text-align: left; ']/div/span[contains(@class,'boi-debit-amount')]"));
        if (oDebit.size() >= 1) {
            report.updateTestLog("VerifyDebitCreditTransactionCount", "Debit Amount is displayed in 'Grey' and Total Debit Amount transactions are :" + oDebit.size() , Status_CRAFT.DONE);
        } else {
            report.updateTestLog("VerifyDebitCreditTransactionCount", " No Debit Amount transactions are present", Status_CRAFT.DONE);
        }
    }


    public void validatePayNowDetails() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyPayNowPopUpDetails(deviceType () + "Transactions.PayNow");
    }


    /**
     * CFPUR-7089: Transactions load more button - CTA content change
     * Function: To verify the presence of Show more button
     * Created on     Created by     Reason
     * 30/07/2019    C966119        New Story
     */
    public void ShowMoreTransactions() throws Exception {
        if (isElementDisplayed(getObject("Transaction.btnShowMore"), 5)) {
            report.updateTestLog("ShowMoreTransactions", "Show more button is displayed ", Status_CRAFT.PASS);
            while (isElementDisplayed(getObject("Transaction.btnShowMore"), 5)){
                clickJS(getObject("Transaction.btnShowMore"),"Show more button");
                while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                    waitForPageLoaded();
                }
            }
            report.updateTestLog("ShowMoreTransactions", "All the transactions has been loaded", Status_CRAFT.DONE);
        }
        else {
            report.updateTestLog("ShowMoreTransactions", "Show more button is not displayed ", Status_CRAFT.PASS);
        }
    }


    /**
     * CFPUR-11005: Available funds & arranged overdraft balance on transactions page (NI/GB/Cross only)
     * Function: To verify the vailable funds & arranged overdraft balance on transactions page
     * Created on     Created by     Reason
     * 11/12/2019    C103403        New Story
     */
    public void verifyAvailable_OverdraftFunds() throws Exception {
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
        if ((dataTable.getData("General_Data", "JurisdictionType").equals("CROSS"))||(dataTable.getData("General_Data", "JurisdictionType").equals("NI"))||(dataTable.getData("General_Data", "JurisdictionType").equals("GB"))){
            isElementDisplayedWithLog(getObject("transactions.lblAvailableFunds"),"label 'Available funds'","Transactions Page",5);
            if(isElementDisplayed(getObject("transactions.tooltipAvailableFunds"),3)){
                report.updateTestLog("verifyAvailable_OverdraftFunds","Available Funds tooltip is appearing on the screen",Status_CRAFT.PASS);
                Actions action = new Actions((WebDriver) driver.getWebDriver());
                action.moveToElement(driver.findElement(getObject("transactions.tooltipAvailableFunds"))).clickAndHold().build().perform();
                String TooltipActualText = getText(getObject("transactions.txttooltipAvailableFunds"));
                String TooltipExpectedText = "Your Available Funds balance includes today’s transactions and transactions in progress. It does not include any arranged overdraft facility available to you on this account. Your Available Funds may change once we clear any cheques that you have lodged.";
                if(TooltipActualText.equals(TooltipExpectedText)){
                    report.updateTestLog("verifyAvailable_OverdraftFunds","Expected Text and Actual Text for the tooltip are same",Status_CRAFT.PASS);
                    report.updateTestLog("verifyAvailable_OverdraftFunds","Expected Text and Actual Text: '"+ TooltipActualText +"' ",Status_CRAFT.DONE);
                }else {
                    report.updateTestLog("verifyAvailable_OverdraftFunds","Expected Text and Actual Text for the tooltip are not matching, Actual Text: '"+ TooltipActualText +"' ",Status_CRAFT.FAIL);
                }
            }else {
                report.updateTestLog("verifyAvailable_OverdraftFunds","Available Funds tooltip is not appearing on the screen",Status_CRAFT.FAIL);
            }
            isElementDisplayedWithLog(getObject("transactions.lblArrangedOverdraft"),"label 'Arranged overdraft remaining'","Transactions Page",1);
            if(isElementDisplayed(getObject("transactions.linkbuttonMoreInfo"),1)){
                report.updateTestLog("verifyAvailable_OverdraftFunds","More Info link button is appearing on the screen",Status_CRAFT.PASS);
                clickJS(getObject("transactions.linkbuttonMoreInfo"),"More Info link");
                if(isElementDisplayed(getObject("transactions.popupMoreInfoheader"),1)){
                    report.updateTestLog("verifyAvailable_OverdraftFunds","Pop up header 'Information' is appearing on the More Info pop up",Status_CRAFT.PASS);
                    isElementDisplayedWithLog(getObject("transactions.iconClose"),"Close 'X' icon","Information Popup",1);
                    isElementDisplayedWithLog(getObject("transactions.popuplblArrangedOverdraft"),"label 'Arranged overdraft remaining'","Information Popup",1);
                    isElementDisplayedWithLog(getObject("transactions.infoText1"),"'"+getText(getObject("transactions.infoText1"))+"'","Information Popup",1);
                    isElementDisplayedWithLog(getObject("transactions.infoText2"),"'"+getText(getObject("transactions.infoText2"))+"'","Information Popup",1);
                    isElementDisplayedWithLog(getObject("transactions.buttonClose"),"Close button","Information Popup",1);
                    clickJS(getObject("transactions.buttonClose"),"Close");
                }else{
                    report.updateTestLog("verifyAvailable_OverdraftFunds","Pop up header 'Information' is not appearing on the More Info pop up",Status_CRAFT.FAIL);
                }
            }else {
                report.updateTestLog("verifyAvailable_OverdraftFunds","More Info link button is not appearing on the screen",Status_CRAFT.FAIL);
            }
            if(isElementDisplayed(getObject("transactions.buttonBalance"),1)){
                report.updateTestLog("verifyAvailable_OverdraftFunds","'Balance/transaction explained' green button is displayed on the screen ",Status_CRAFT.PASS);
                clickJS(getObject("transactions.buttonBalance"),"Balance/transaction explained");
                if(isElementDisplayed(getObject("transactions.buttonAvailableFunds"),1)){
                    clickJS(getObject("transactions.buttonAvailableFunds"),"Available funds");
                    String ActualText = getText(getObject("transactions.textAvailableFunds"));
                    String ExpectedText = "Your Available Funds balance includes today’s transactions and transactions in progress. It does not include any arranged overdraft facility available to you on this account. Your Available Funds may change once we clear any cheques that you have lodged.";
                    if(ActualText.equals(ExpectedText)){
                        report.updateTestLog("verifyAvailable_OverdraftFunds","Expected Text and Actual Text are matching",Status_CRAFT.PASS);
                        report.updateTestLog("verifyAvailable_OverdraftFunds","Expected Text and Actual Text: '"+ ActualText +"' ",Status_CRAFT.DONE);
                    }else {
                        report.updateTestLog("verifyAvailable_OverdraftFunds", "Expected Text and Actual Text are not matching, Actual Text: '" + ActualText + "' ", Status_CRAFT.FAIL);
                    }
                }else{
                    report.updateTestLog("verifyAvailable_OverdraftFunds","'Available Funds' button is not displayed on the screen ",Status_CRAFT.FAIL);
                }
                if(isElementDisplayed(getObject("transactions.buttonArrangedOverdraft"),1)){
                    clickJS(getObject("transactions.buttonArrangedOverdraft"),"Arranged overdraft remaining");
                    String ActualText = getText(getObject("transactions.textArrangedOverdraft"));
                    String ExpectedText = "This balance shows your most up to date arranged overdraft available balance including today's transactions and other transactions which are still in progress. The balance will be updated during the day as new transactions are presented. This balance is subject to the clearance of any uncleared cheques lodged recently.";
                    if(ActualText.equals(ExpectedText)){
                        report.updateTestLog("verifyAvailable_OverdraftFunds","Expected Text and Actual Text are matching",Status_CRAFT.PASS);
                        report.updateTestLog("verifyAvailable_OverdraftFunds","Expected Text and Actual Text: '"+ ActualText +"' ",Status_CRAFT.DONE);
                    }else {
                        report.updateTestLog("verifyAvailable_OverdraftFunds", "Expected Text and Actual Text are not matching, Actual Text: '" + ActualText + "' ", Status_CRAFT.FAIL);
                    }
                }else{
                    report.updateTestLog("verifyAvailable_OverdraftFunds","'Arranged overdraft remaining' button is not displayed on the screen ",Status_CRAFT.FAIL);
                }
            }else {
                report.updateTestLog("verifyAvailable_OverdraftFunds","'Balance/transaction explained' green button is not displayed on the screen ",Status_CRAFT.FAIL);
            }
        }else {
            report.updateTestLog("verifyAvailable_OverdraftFunds","If this is a UK or CROSS jurisdictional user then please update the JurisdictionType in General_Data sheet",Status_CRAFT.DONE);
        }
    }

    /**
     * Function: To Verify for High Cost of Credit that Available Funds is appearing in the transactions tab
     * Created by: C103403
     * Created on: 05/02/2020
     */
    public void verifyAvailableFunds() throws Exception{
        if(dataTable.getData("General_Data","AvailableFundsFlag").equals("Y")){
            if(isElementDisplayed(getObject("HCC.labelAvailableFunds"),2)){
                report.updateTestLog("verifyAvailableFunds", "Available Funds expected on screen and are appearing in the blue card on Transactions Page", Status_CRAFT.PASS);
            }else {
                report.updateTestLog("verifyAvailableFunds", "Available Funds are expected on screen and are not appearing in the blue card on Transactions Page", Status_CRAFT.FAIL);
            }
        }else if(dataTable.getData("General_Data","AvailableFundsFlag").equals("N")){
            if(isElementDisplayed(getObject("HCC.labelAvailableFunds"),2)){
                report.updateTestLog("verifyAvailableFunds", "Available Funds are not expected on screen and are appearing in the blue card on Transactions Page", Status_CRAFT.FAIL);
            }else {
                report.updateTestLog("verifyAvailableFunds", "Available Funds are not expected on screen and are not appearing in the blue card on Transactions Page", Status_CRAFT.PASS);
            }
        }else {
            report.updateTestLog("verifyAvailableFunds","Please check the datatable 'General_Data,AvailableFunds' for the proper validation ",Status_CRAFT.FAIL);
        }
    }

    /**
     * Function: To Verify for High Cost of Credit that Arranged Overdraft Remaining is appearing in the transactions tab
     * Created by: C103403
     * Created on: 05/02/2020
     */
    public void verifyArrangedOverdraftRemaining() throws Exception{
        if(dataTable.getData("General_Data","ArrangedOverdraftRemainingFlag").equals("Y")){
            if(isElementDisplayed(getObject("HCC.labelArrangedOverdraftRemaining"),2)){
                report.updateTestLog("verifyArrangedOverdraftRemaining", "Arranged Overdraft Remaining is expected on screen and is appearing in the blue card on Transactions Page", Status_CRAFT.PASS);
            }else {
                report.updateTestLog("verifyArrangedOverdraftRemaining", "Arranged Overdraft Remaining is expected on screen and is not appearing in the blue card on Transactions Page", Status_CRAFT.FAIL);
            }
        }else if(dataTable.getData("General_Data","ArrangedOverdraftRemainingFlag").equals("N")){
            if(isElementDisplayed(getObject("HCC.labelArrangedOverdraftRemaining"),2)){
                report.updateTestLog("verifyArrangedOverdraftRemaining", "Arranged Overdraft Remaining is not expected on screen and is appearing in the blue card on Transactions Page", Status_CRAFT.FAIL);
            }else {
                report.updateTestLog("verifyArrangedOverdraftRemaining", "Arranged Overdraft Remaining is not expected on screen and is not appearing in the blue card on Transactions Page", Status_CRAFT.PASS);
            }
        }else {
            report.updateTestLog("verifyAvailableFunds","Please check the datatable 'General_Data,ArrangedOverdraftRemaining' for the proper validation ",Status_CRAFT.FAIL);
        }
    }


    public void verifyPayFromHCCDetails() throws Exception{

        if(isElementDisplayed(getObject("HCC.labelPayFromAvailableFunds"),2)){
            report.updateTestLog("verifyAvailableFunds", "Available Funds is expected on screen and is appearing below the Pay from dropdown", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("verifyAvailableFunds", "Available Funds is expected on screen and is not appearing below Pay from the dropdown", Status_CRAFT.FAIL);
        }

        if(dataTable.getData("General_Data","ArrangedOverdraftRemainingFlag").equals("Y")){
            if(isElementDisplayed(getObject("HCC.labelPayFromArrangedOverdraftRemaining"),2)){
                report.updateTestLog("verifyArrangedOverdraftRemaining", "Arranged Overdraft Remaining is expected on screen and is appearing below the Pay from dropdown", Status_CRAFT.PASS);
            }else {
                report.updateTestLog("verifyArrangedOverdraftRemaining", "Arranged Overdraft Remaining is expected on screen and is not appearing below the Pay from dropdown", Status_CRAFT.FAIL);
            }
        }else if(dataTable.getData("General_Data","ArrangedOverdraftRemainingFlag").equals("N")){
            if(isElementDisplayed(getObject("HCC.labelPayFromArrangedOverdraftRemaining"),2)){
                report.updateTestLog("verifyArrangedOverdraftRemaining", "Arranged Overdraft Remaining is not expected on screen and is appearing  below the Pay from dropdown", Status_CRAFT.FAIL);
            }else {
                report.updateTestLog("verifyArrangedOverdraftRemaining", "Arranged Overdraft Remaining is not expected on screen and is not appearing below the Pay from dropdown", Status_CRAFT.PASS);
            }
        }else {
            report.updateTestLog("verifyAvailableFunds","Please check the datatable 'General_Data,ArrangedOverdraftRemaining' for the proper validation ",Status_CRAFT.FAIL);
        }
    }



}
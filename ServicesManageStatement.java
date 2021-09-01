package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.Color;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import com.thoughtworks.selenium.condition.Not;

/**
 * Function/Epic : Manage statement as part of sprint-11 (Squad-1)
 * Class : ServiceManageStatement
 * Developed on : 18/10/2018
 * Developed by : C966119
 * Update on       Update by       Reason
 * 23/04/2019      c101979         Done code clean up activity
 */

public class ServicesManageStatement extends WebDriverHelper {
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public ServicesManageStatement(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Mobile App Automation Method : To Verify and Select Any option from Services menu
     * Update on     Updated by     Reason
     *
     */
    public void verifyAndClickServicesOption() throws Exception {
        String strServiceOption = dataTable.getData("General_Data", "Operation");
        //click(getObject("mw.home.btnServiceDesk"));
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        String strServiceOptionXpath = "xpath~//div[contains(@class,'boi-services-btn')]//div/descendant::span[text()='" + strServiceOption + "']";
        /*if(isMobile){
            strServiceOptionXpath= "xpath~//*[@title='" + strServiceOption + "']";
        }*/
        waitForElementPresent(getObject(strServiceOptionXpath), 40);
        if(isElementDisplayed(getObject(strServiceOptionXpath),3)){
            clickJS(getObject(strServiceOptionXpath),"Manage Statements");
            report.updateTestLog("VerifyManageStatements", "Service Option ::" + strServiceOption + ":: is Clicked", Status_CRAFT.DONE);
        }
        else{
            report.updateTestLog("VerifyManageStatements", "Service Option ::" + strServiceOption + ":: is  not Clicked", Status_CRAFT.FAIL);
        }

    }

    /**
     * Function : To Verify credit card services option and the details of popup from Services menu (CFPUR-4243)
     * Update on       Update by       Reason
     * 23/04/2019      c101979         Done code clean up activity
     */
    public void verifyAndClickCreditcardServicesOption() throws Exception {
        String strServiceOption = dataTable.getData("General_Data", "Operation");
        String linkToVerify = dataTable.getData("General_Data", "VerifyDetails");
        waitForElementPresent(getObject(deviceType() + "home.btnServiceDesk"),5);
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        String strServiceOptionXpath = "xpath~//*[@class='boi-services-btn boi_input_sm tc-ripple-effect boi-remove-title']/span[text()='"+strServiceOption+"']";
        waitForElementPresent(getObject(strServiceOptionXpath),5);
        String strIterationFlalgPolicy = dataTable.getData("General_Data","IterationFlagforPolicy");
        if (strIterationFlalgPolicy.equalsIgnoreCase("Yes"))
        {
                waitForElementPresent(getObject(strServiceOptionXpath), 15);
                click(getObject(strServiceOptionXpath), "Credit card services");
                report.updateTestLog("verifyCreditCardServices", "Service Option ::" + strServiceOption + ":: is Clicked", Status_CRAFT.DONE);
                waitForElementPresent(getObject("services.ccPopupTitle"), 15);
                isElementDisplayedWithLog(getObject("services.ccPopupTitle"),"Pop up Title","Credit card services",5);
        }
                isElementDisplayedWithLog(getObject("services.ccPopupHeader"),"Pop up Header","Credit card services",5);
                isElementDisplayedWithLog(getObject("services.ccPopupButtons"),"Pop up Button","Credit card services",5);
                HomePage homePage = new HomePage(scriptHelper);
                if (isMobile){
                    homePage.verifyHrefLink(linkToVerify, "xpath~//button[@class='btn-primary boi-full-width boi-close-popup tc-ripple-effect boi-remove-title']");
                }else {
                    clickJS(getObject("xpath~//button[@class='btn-primary boi-full-width boi-close-popup tc-ripple-effect boi-remove-title']"),"Tripple effect pop-up");
                    homePage.verifyNewlyOpenedTab(linkToVerify);
                }
            }

    /**
     * CFPUR-4243: To Verify credit card services option and the details of popup from Services menu
     */
    public void verifyAndClickCreditcardServices() throws Exception {
        String strServiceOption = dataTable.getData("General_Data", "Operation");
        String linkToVerify = dataTable.getData("General_Data", "VerifyDetails");
        Thread.sleep(2000);
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        Thread.sleep(3000);
        String strServiceOptionXpath = " xpath~//*[@id=\"C5__BUT_8FE79AD30AAC7638985609\"]/span[2]";
        if (isElementDisplayed(getObject(strServiceOptionXpath), 5)) {
            report.updateTestLog("verifyCreditCardServices", "Credit Card Service Link Exist", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyCreditCardServices", "Credit Card Service Link NOT Exist", Status_CRAFT.PASS);
        }
    }

    public void ManagestetmentsNavigation() throws Exception {
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        if  (isElementDisplayed(getObject(deviceType() + "services.Managestatements"), 5)){
            clickJS(getObject(deviceType() + "services.Managestatements"),"Click on Manage statements option");
        }
    }

    /*General Function:
     *Scroll to view particular  element using JS
    */
    public void ScrollToVisibleJS(String linkToVisible) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToVisible))));
            Thread.sleep(2000);
             } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }


    }

    /**
     * CFPUR-76 To Verify Manage Statements - Paper
     */
    public void verifyPaperStatements() throws Exception {

        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "ManageStatements.lnkTurnPaperONOFF"), 10)) {
                click(getObject(deviceType() + "ManageStatements.lnkTurnPaperONOFF"));
                if (isElementDisplayed(getObject(deviceType()+"ManageStatements.lnkTurnPaperStmtHeader"), 10)) {
                    report.updateTestLog("verifyPaperStatements", "Turn paper statements On/Off is Clicked successfully.", Status_CRAFT.PASS);
                    int intTotalCount = findElements(getObject(deviceType()+"ManageStatements.lnkTurnPaperOnOffCount")).size();
                    int intTotalOnCount = findElements(getObject(deviceType()+"ManageStatements.lnkTurnPaperONCount")).size();
                    report.updateTestLog("verifyPaperStatements", "Turn paper statements On/Off Total account :: " + intTotalCount, Status_CRAFT.DONE);
                    report.updateTestLog("verifyPaperStatements", "Turn paper statements On is on account :: " + intTotalOnCount, Status_CRAFT.DONE);
                    report.updateTestLog("verifyPaperStatements", "Turn paper statements Off is on account :: " + (intTotalCount - intTotalOnCount), Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyPaperStatements", "Turn paper statements On/Off is not Clicked", Status_CRAFT.FAIL);
                }
            }
        }

        else {
            if (isElementDisplayed(getObject("ManageStatements.lnkTurnPaperONOFF"), 10)) {
                click(getObject("ManageStatements.lnkTurnPaperONOFF"));
                if (isElementDisplayed(getObject("xpath~//*[text()='Turn paper statements On/Off']"), 10)) {
                    report.updateTestLog("verifyPaperStatements", "Turn paper statements On/Off is Clicked successfully.", Status_CRAFT.PASS);
                    int intTotalCount = findElements(getObject("ManageStatements.lnkTurnPaperOnOffCount")).size();
                    int intTotalOnCount = findElements(getObject("ManageStatements.lnkTurnPaperONCount")).size();
                    report.updateTestLog("verifyPaperStatements", "Turn paper statements On/Off Total account :: " + intTotalCount, Status_CRAFT.DONE);
                    report.updateTestLog("verifyPaperStatements", "Turn paper statements On is on account :: " + intTotalOnCount, Status_CRAFT.DONE);
                    report.updateTestLog("verifyPaperStatements", "Turn paper statements Off is on account :: " + (intTotalCount - intTotalOnCount), Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyPaperStatements", "Turn paper statements On/Off is not Clicked", Status_CRAFT.FAIL);
                }
            }
        }
    }

    public void verifyPaperSwitch() throws Exception {
        //To verify page title,profile icon and back button
        if (isElementDisplayed(getObject(deviceType() + "ManageStatements.pageTitle") , 20)){
            if ((getText(getObject(deviceType() + "ManageStatements.pageTitle")).equalsIgnoreCase("Turning on/off paper statements won't affect your eStatements.")) ) {
                report.updateTestLog("Header_PaperStatements ", "Paper statements header is Correctly displayed", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("Header_PaperStatements", "Paper statements header is not valid", Status_CRAFT.FAIL);
            }
        }


        //to add elements
        waitForElementPresent(getObject(deviceType() + "PaperStatements.TurnPaperONOFF"), 5);
        boolean paperStatementFlag = click(getObject(deviceType() + "PaperStatements.TurnPaperONOFF"));
        if (paperStatementFlag == true) {
            report.updateTestLog("VerifyPaperStatementsFlag", "PaperStatements is Clicked", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPaperStatementsFlag", "PaperStatements is not Clicked", Status_CRAFT.FAIL);
        }

        //List of all the Accounts
        //To verify the ON/OFF button against each account
        String strParentObject = "";
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyAccountPresent", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        for (int i = 1; i <= oUIRows.size(); i++) {
            String a[] = {"Savings", "Current", "TL", "Card"};
            String strRowText = oUIRows.get(i).getText();
            boolean blnFlag = strRowText.contains("Savings") || strRowText.contains("TL") || strRowText.contains("Current") || strRowText.contains("Card");
            if (blnFlag == true) {
                //logic for toggle switch
                //report
            }

        }

    }

    /**
     * Function : To Verify and Select Any option from Services menu (CFPUR-4243)
     */
    public void verifyManageStatementOptions() throws Exception {
        ManagestetmentsNavigation();
        String[] arrValidation = {"ManageStatements.lnkOrderUpToDate", "ManageStatements.lblOrderUpToDateInfo","ManageStatements.lblOrderCopyInfo1","ManageStatements.lnkOrderCopy","ManageStatements.lblOrderCopyInfo", "ManageStatements.lnkTurnPaperONOFF", "ManageStatements.lnkStmntNotification"};
        if (isElementDisplayed(getObject(deviceType()+"services.ManagestatementsText"), 6)) {
            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                String strText = getText(getObject(arrValidation[intValidate]));
                if (isElementDisplayed(getObject(arrValidation[intValidate]), 1)) {
                    report.updateTestLog("verifyManageStatementOptions", "Element is displayed with correct text :: '" + strText + "'", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyManageStatementOptions", "Element is not displayed with correct text ::  '" + strText + "'", Status_CRAFT.FAIL);
                }
            }
        }
    }

    /**
     * Function : To Verify Paperless promotion (CFPUR-293)
     */
    public void verifyPaperlessPromotion() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");

        if (isElementDisplayed(getObject(deviceType() + "home.lnkShowmoreacc"), 20)) {
            click(getObject(deviceType() + "home.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
            Thread.sleep(9500);
            report.updateTestLog("verifyAccountStatement_AllAccounts", "Show more Accounts link exist and is clicked", Status_CRAFT.DONE);
        }

        String strParentObject = deviceType() + "home.parentaccount_new";

        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyCheckContentNotPresent", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        ScrollToVisibleJS(strParentObject);

        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();
            if ((strRowText.contains(strAccountToCheck.toUpperCase())) && (strRowText.contains(strAccountIBAN.toUpperCase()))) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                Thread.sleep(3000);
                JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                executor.executeScript("arguments[0].click();", oUIRows.get(j));
                 waitForElementPresent(getObject("AccStatement.lblStatementTab"), 50);
                report.updateTestLog("verifyAccountStatement_AllAccounts", "The Account :: " + strAccountToCheck + ":: is displyed at position ::" + (j + 1) + ":: and clicked successfully.", Status_CRAFT.DONE);
                break;
            }
        }

        report.updateTestLog("verifyAccountStatement_AllAccounts", "Blue Card Page", Status_CRAFT.SCREENSHOT);
        click(getObject("AccStatement.lblStatementTab"));
        Thread.sleep(5500);
        report.updateTestLog("verifyAccountStatement_AllAccounts", "Statements", Status_CRAFT.SCREENSHOT);
        //code for promotion present
        // String strDeviceName = scriptHelper.getTestParameters().getDeviceName().toString();
        String aa = getText(getObject(deviceType() + "PaperStatements.Promotion_First"));
        String bb = getText(getObject(deviceType() + "PaperStatements.Promotion_Second"));
        // if (deviceType().equals("w.")|| strDeviceName.equalsIgnoreCase("Apple iPad") || strDeviceName.equalsIgnoreCase("Apple iPad Mini")) {
        if ((getText(getObject(deviceType() + "PaperStatements.Promotion_First")).equalsIgnoreCase("Reduce the clutter")) && (getText(getObject(deviceType() + "PaperStatements.Promotion_Second")).equalsIgnoreCase("Turn off paper statements"))) {
            report.updateTestLog("PaperLess_Promotion_Present_Verification", "Promotion present", Status_CRAFT.PASS);
            click(getObject(deviceType() + "PaperStatements.Promotion_First"));
            report.updateTestLog("PaperLess_Promotion_Click", "Promotion Clicked", Status_CRAFT.DONE);
            if (getText(getObject(deviceType() + "PaperlessPromotion_pageTitle")).equalsIgnoreCase("Turn paper statements On/Off")) {
                report.updateTestLog("Paper statement On/Off", "Paper statement On/Off Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Paper statement On/Off", "No Paper statement On/Off Page", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("PaperLess_Promotion_Present_Verification", "Promotion not present", Status_CRAFT.FAIL);
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
            report.updateTestLog("ScrollAndClickJS", "Element To Click", Status_CRAFT.DONE);
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
      * To Verify the info displayed on paper statements on/off : (CFPUR-3991)
      * Update on       Update by       Reason
      * 23/04/2019      c101979         Done code clean up activity
     **/

    public void verifyinfo() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        waitForElementPresent(getObject(deviceType() + "ManageStatements.pageTitle"), 5);
        if ((getText(getObject(deviceType() + "ManageStatements.pageTitle")).equalsIgnoreCase("Turning on/off paper statements won't affect your eStatements.")) ) {
            report.updateTestLog("Header_PaperStatements ", "Paper Statements header is valid", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Header_PaperStatements", "Paper Statements header is not valid", Status_CRAFT.FAIL);
        }

        String strJurisdicationType = dataTable.getData("General_Data","JurisdictionType");

        if (strJurisdicationType.equalsIgnoreCase("NI") ||
                strJurisdicationType.equalsIgnoreCase("GB")) {
            if (isElementDisplayed(getObject("ManageStatements.infoHeaderNI"), 3)) {
                report.updateTestLog("verifyServicesPaperStatementsinfo", "'Joint accounts' Header is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyServicesPaperStatementsinfo", "'Joint accounts' Header is not displayed successfully", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("ManageStatements.infoLabelNI"), 3)) {
                report.updateTestLog("verifyServicesPaperStatementsinfo", "Informative text specific to NI/GB customers is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyServicesPaperStatementsinfo", "Informative text specific to NI/GB customers is not displayed successfully", Status_CRAFT.FAIL);
            }
        }
        else if (strJurisdicationType.equalsIgnoreCase("ROI"))
        {
            if (isElementDisplayed(getObject("ManageStatements.infoHeaderROI"), 3)) {
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(("ManageStatements.infoHeaderROI"))));
                report.updateTestLog("verifyServicesPaperStatementsinfo", "'Joint accounts' Header is displayed successfully", Status_CRAFT.PASS);
            } else {
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(("ManageStatements.infoHeaderROI"))));
                report.updateTestLog("verifyServicesPaperStatementsinfo", "'Joint accounts' Header is not displayed successfully", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("ManageStatements.infoLabelROI"), 3)) {
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(("ManageStatements.infoLabelROI"))));
                report.updateTestLog("verifyServicesPaperStatementsinfo", "Informative text specific to UK customers is displayed successfully", Status_CRAFT.PASS);
            } else {
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(("ManageStatements.infoLabelROI"))));
                report.updateTestLog("verifyServicesPaperStatementsinfo", "Informative text specific to UK customers is not displayed successfully", Status_CRAFT.FAIL);
            }
        }
        else {
            if (strJurisdicationType.equalsIgnoreCase("Cross")) {
                if (isElementDisplayed(getObject("ManageStatements.infoHeaderCross"), 3)) {
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(("ManageStatements.infoHeaderCross"))));
                    report.updateTestLog("verifyServicesPaperStatementsinfo", "'Joint accounts' Header is displayed successfully", Status_CRAFT.PASS);
                } else {
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(("ManageStatements.infoHeaderCross"))));
                    report.updateTestLog("verifyServicesPaperStatementsinfo", "'Joint accounts' Header is not displayed successfully", Status_CRAFT.FAIL);
                }
                if (isElementDisplayed(getObject("ManageStatements.infoLabelCrossRepublic"), 3))
                {
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(("ManageStatements.infoLabelCrossRepublic"))));
                    report.updateTestLog("verifyServicesPaperStatementsinfo", "Informative text specific to Cross customers Republic of Ireland is displayed successfully", Status_CRAFT.PASS);
                }else{
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(("ManageStatements.infoLabelCrossRepublic"))));
                    report.updateTestLog("verifyServicesPaperStatementsinfo", "Informative text specific to Cross customers Republic of Ireland is not displayed successfully", Status_CRAFT.FAIL);
                }
            }
        }

		// for Credit Cards.... added by subodh
		if (strJurisdicationType.equalsIgnoreCase("NI") || strJurisdicationType.equalsIgnoreCase("GB")
				|| strJurisdicationType.equalsIgnoreCase("ROI") || strJurisdicationType.equalsIgnoreCase("Cross")) {

			if (isElementDisplayed(getObject("ManageStatements.infoHeaderCC"), 3)) {
				js.executeScript("arguments[0].scrollIntoView();",
						driver.findElement(getObject(("ManageStatements.infoHeaderCC"))));
				report.updateTestLog("verifyServicesPaperStatementsinfo",
						"'Credit cards' Header is displayed successfully", Status_CRAFT.PASS);
			} else {
				js.executeScript("arguments[0].scrollIntoView();",
						driver.findElement(getObject(("ManageStatements.infoHeaderROI"))));
				report.updateTestLog("verifyServicesPaperStatementsinfo",
						"'Credit cards' Header is not displayed successfully", Status_CRAFT.FAIL);
			}
			if (isElementDisplayed(getObject("ManageStatements.infoLabelCC"), 3)) {
				js.executeScript("arguments[0].scrollIntoView();",
						driver.findElement(getObject(("ManageStatements.infoLabelCC"))));
				report.updateTestLog("verifyServicesPaperStatementsinfo",
						"Credit cards Informative text is displayed successfully", Status_CRAFT.PASS);
			} else {
				js.executeScript("arguments[0].scrollIntoView();",
						driver.findElement(getObject(("ManageStatements.infoLabelCC"))));
				report.updateTestLog("verifyServicesPaperStatementsinfo",
						"Credit cards Informative text is not displayed successfull", Status_CRAFT.FAIL);
			}
		}
    }

    public void verifyInfoGoBack() throws Exception{
        if(isElementDisplayed(getObject("ManageStatements.infoGoBackButton"), 3)){
            report.updateTestLog("verifyInfoGoBack",
                    "Manage statement go back button displayed", Status_CRAFT.PASS);

            Boolean buttonClicked = click(getObject("ManageStatements.infoGoBackButton"));
            if(buttonClicked){
                report.updateTestLog("verifyInfoGoBack",
                        "Manage statement go back button Clicked", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("verifyInfoGoBack",
                        "Manage statement go back button Clicked failed", Status_CRAFT.FAIL);
            }
        }else{
            report.updateTestLog("verifyInfoGoBack",
                    "Manage statement go back button displayed", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify the Order up to date statements : CFPUR-1234
     **/

    public void verifyManageStatementsEmailNotification() throws Exception {

        //Verify the flow considering the current status
        String strFlow = "";
        report.updateTestLog("verifyManageStatementsEmailNotification", ":: Page Screenshot ::", Status_CRAFT.SCREENSHOT);
        if (isElementDisplayed(getObject("xpath~//span[contains(@class,'boi-flip-switch__text')][text()='On']"),10)){
            strFlow = "Email_Notification_On_To_Off";
        }else if (isElementDisplayed(getObject("xpath~//span[contains(@class,'boi-flip-switch__text')][text()='Off']"),10)){
            strFlow = "Email_Notification_Off_To_On";
        }
        clickJS(getObject("ManageStatements.lnkStmntNotification"), "Click Menu Option : Email Statement Notification.");
        boolean blnON = false;
        report.updateTestLog("verifyManageStatementsEmailNotification", ":: Page Screenshot ::"+ strFlow , Status_CRAFT.SCREENSHOT);

        if (isElementDisplayed(getObject("ManageStatements.btnEmailNotification"),10)){
            //Verify the On/Off Flag
            //fetchAttribute(getObject("xpath~//input[contains(@name,'ISESTATEMENTEMAILON')]"),"checked");
            String var = driver.findElement(getObject("xpath~//input[contains(@name,'ISESTATEMENTEMAILON')]")).getAttribute("checked");
            if (var==null){
                blnON = false;
                report.updateTestLog("verifyManageStatementsEmailNotification", "Email Statement notification toggle button Status : OFF " , Status_CRAFT.DONE);
            }else{
                blnON = true;
                report.updateTestLog("verifyManageStatementsEmailNotification", "Email Statement notification toggle button Status : ON " , Status_CRAFT.DONE);
 }
        }else{
            report.updateTestLog("verifyManageStatementsEmailNotification", "Email Statement notification toggle button is not correctly displayed.", Status_CRAFT.FAIL);
        }

        // Validate the text and email id
        String strEmailID = dataTable.getData("General_Data", "VerifyDetails");
        if (isElementDisplayed(getObject("xpath~//div[contains(@class,'ecDIB  boi_label')]/descendant::*[text()='Notifications']"),3) && isElementDisplayed(getObject("xpath~//div[contains(@class,'ecDIB  boi_label')]/descendant::*[contains(text(),'"+ strEmailID +"')]"),3)){
            report.updateTestLog("verifyManageStatementsEmailNotification", "The Text : Notifications and Email ID : " + strEmailID , Status_CRAFT.DONE);
        }else {
            report.updateTestLog("verifyManageStatementsEmailNotification", "Email Statement notification toggle button Status : OFF " , Status_CRAFT.FAIL);
        }

        // Check the Edit button and update the Email ID
        String strEditFlag = dataTable.getData("General_Data", "Nickname");
        if (blnON && strEditFlag.equals("EditEmail")){
            if (isElementDisplayed(getObject("ManageStatements.btnEditMail"),10)){
                //Code to update email id
                String strNewEmailID = dataTable.getData("General_Data", "Relationship_Status");
                clickJS(getObject("ManageStatements.btnEditMail"),"Click on the email id button to edit");
                if (isElementDisplayed(getObject("ManageStatements.inputNewEmail"),10)){
                    sendKeys(getObject("ManageStatements.inputNewEmail") , strNewEmailID );
                    sendKeys(getObject("ManageStatements.inputConfirmEmail") , strNewEmailID );
                    clickJS(getObject("xpath~//span[text()='Ok']"), "Click on OK button");
                    Thread.sleep(3000);
                }

                //Validate the newly updated email ID
                if (isElementDisplayed(getObject("xpath~//div[contains(@class,'ecDIB  boi_label')]/descendant::*[contains(text(),'"+ strEmailID +"')]"),3)){
                    report.updateTestLog("verifyManageStatementsEmailNotification", "Successfully Updated New Email ID : " + strEmailID , Status_CRAFT.DONE);
                }else {
                    report.updateTestLog("verifyManageStatementsEmailNotification", "New Email Id is not updated successfully..!!" , Status_CRAFT.FAIL);
                }
            }
        }

        clickJS(getObject("ManageStatements.btnEmailNotification"), "Click on ON/OFF button : Email Statement Notification.");
        verifyPopUpDetails();
        clickJS(getObject("xpath~//span[text()='Ok']"), "Click on Ok button on pop up.");
        Thread.sleep(4000);
        //Verify the Change updated correctly
        if (strFlow.equals("Email_Notification_On_To_Off")){
            if (isElementDisplayed(getObject("xpath~//span[@class='boi_moreinfo'][text()='ON']"),10)){
                report.updateTestLog("verifyManageStatementsEmailNotification", "Email_Notification_On_To_Off : Notification status is updated correctly as ON" , Status_CRAFT.DONE);
            }
        }else if (strFlow.equals("Email_Notification_Off_To_On")){
            if (isElementDisplayed(getObject("xpath~//span[@class='boi_moreinfo'][text()='ON']"),10)){
                report.updateTestLog("verifyManageStatementsEmailNotification", "Email_Notification_Off_To_On : Notification status is updated correctly as OFF" , Status_CRAFT.DONE);
            }
        }

        // back to the Manage statements
        clickJS(getObject("xpath~//h4[text()='Statement Notifications']"), "Click Statement Notifications breadcrum.");
        Thread.sleep(3000);
    }
    public void verifyStatementsNotificationToggleSwitch() throws Exception {

        //Verify the flow considering the current status
        String strFlow = "";
        report.updateTestLog("verifyManageStatementsEmailNotification", ":: Page Screenshot ::", Status_CRAFT.SCREENSHOT);
        if (isElementDisplayed(getObject("xpath~//*[text()='On']"),10)){
            strFlow = "Email_Notification_On_To_Off";
        }else if (isElementDisplayed(getObject("xpath~//*[text()='Off']"),10)){
            strFlow = "Email_Notification_Off_To_On";
        }

        clickJS(getObject("ManageStatements.lnkStmntNotification"), "Click Menu Option : Email Statement Notification.");
        boolean blnON = false;
        report.updateTestLog("verifyManageStatementsEmailNotification", ":: Page Screenshot ::"+ strFlow , Status_CRAFT.SCREENSHOT);

        verifyPopUpDetails();
        clickJS(getObject("xpath~//button[text()='Cancel']"), "Cancel");

        clickJS(getObject("ManageStatements.lnkStmntNotification"), "Click Menu Option : Email Statement Notification.");
        clickJS(getObject("xpath~//*[text()='Ok']"), "Ok");

            //Verify the Change updated correctly
            if (strFlow.equals("Email_Notification_On_To_Off")){
                if (isElementDisplayed(getObject("xpath~//*[text()='Off']"),10)){
                    report.updateTestLog("verifyManageStatementsEmailNotification", "Email_Notification_On_To_Off : Notification status is updated correctly as OFF" , Status_CRAFT.DONE);
                }
            }else if (strFlow.equals("Email_Notification_Off_To_On")){
                if (isElementDisplayed(getObject("xpath~//*[text()='On']"),10)){
                    report.updateTestLog("verifyManageStatementsEmailNotification", "Email_Notification_Off_To_On : Notification status is updated correctly as ON" , Status_CRAFT.DONE);
                }
            }

    }

    /* Reusable : Verify the content of any PopUp
        - Verify the Pop up Header
        - x close sign
        - Text body of the pop up
    */
    public void verifyPopUpDetails() throws Exception {
        String strContentToCheck = dataTable.getData("General_Data", "FirstName");
        String[] arrContentToCheck = strContentToCheck.split("::");
        Thread.sleep(2000);
        report.updateTestLog("verifyPopUpAndClickOnLink", "The Title and Content ", Status_CRAFT.SCREENSHOT);

        // To Verify Title
        String strTitleXpath = "xpath~//div[@id='EDGE_CONNECT_PROCESS']/descendant::span[@id='ui-id-1'][text()='" + arrContentToCheck[0] + "']";
        String strCloseButton = "xpath~//div[@id='EDGE_CONNECT_PROCESS']/descendant::span[contains(@class,'closethick')]";
        if (isElementDisplayed(getObject(strTitleXpath), 20) && isElementDisplayed(getObject(strCloseButton), 20)) {
            report.updateTestLog("verifyPopUpAndClickOnLink", "The title of the Dialog :: " + arrContentToCheck[0] + ":: is correctly displyed along with 'x' close button", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyPopUpAndClickOnLink", "The title of the Dialog :: " + arrContentToCheck[0] + ":: or 'x' close button is not correctly displyed.", Status_CRAFT.FAIL);
        }

        //Validate the content
        for (int i = 1; i < arrContentToCheck.length; i++) {
            String strContentXpath = "xpath~//div[@id='EDGE_CONNECT_PROCESS']/descendant::*[text()='" + arrContentToCheck[i] + "']";
            if (isElementDisplayed(getObject(strContentXpath), 1)) {
                report.updateTestLog("verifyPopUpAndClickOnLink", "The Content of the Dialog :: " + arrContentToCheck[i] + ":: is correctly displyed.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyPopUpAndClickOnLink", "The Content of the Dialog :: " + arrContentToCheck[i] + ":: is not correctly displyed.", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * CFPUR-328-Contact us page
     */
    public void ClickServicesContactUsLink() throws Exception {
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), 10)) {
                // Navigating to Services tab
                click(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), "Select 'Services Tab'");
                waitForPageLoaded();
                if (isElementDisplayed(getObject(deviceType() + "serviceDesk.ContactUs"), 10)) {
                    // Navigating to Contact Us
                    click(getObject(deviceType() + "serviceDesk.ContactUs"), "Select 'Contact Us'");
                    waitForPageLoaded();
                    report.updateTestLog("verifyServicesContactUs", "' User navigated to Contact Us ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyServicesContactUs", "' Failed to navigate to Contact Us ", Status_CRAFT.FAIL);
                }
            }
        }
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), 10)) {
                // Navigating to Services tab
                clickJS(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), "Select 'Services Tab'");
                waitForPageLoaded();
                if (isElementDisplayed(getObject(deviceType() + "serviceDesk.ContactUs"), 10)) {
                    // Navigating to Contact Us
                    clickJS(getObject(deviceType() + "serviceDesk.ContactUs"), "Select 'Contact Us'");
                    waitForPageLoaded();
                    report.updateTestLog("verifyServicesContactUs", "' User navigated to Contact Us ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyServicesContactUs", "' Failed to navigate to Contact Us ", Status_CRAFT.FAIL);
                }
            }
        }
    }

    public void creditCardServicesDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if(deviceType.equalsIgnoreCase("Web")) {
            if (scriptHelper.commonData.iterationFlag != true) {
                homePage.verifyElementDetails("ServiceManage.tablePhoneBanking");
                click(getObject("ServiceManage.creditCardService"));
                waitForJQueryLoad();
                waitForPageLoaded();

                waitForJQueryLoad();waitForPageLoaded();

                scriptHelper.commonData.iterationFlag = true;
            } else {
                homePage.verifyElementDetails("ServiceManage.tableCreditCardService");
            }
        }
        else if(deviceType.equalsIgnoreCase("MobileWeb")) {
            if (scriptHelper.commonData.iterationFlag != true) {
                homePage.verifyElementDetails("ServiceManage.tablePhoneBanking");
                clickJS(getObject("ServiceManage.creditCardService"), "Select 'Credit Card Services' Tab'");
                scriptHelper.commonData.iterationFlag = true;
            } else {
                homePage.verifyElementDetails("ServiceManage.tableCreditCardService");
            }
        }
    }

    public void clickServices() throws InterruptedException {
        if (isElementDisplayed(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), 10)) {
            // Navigating to Services tab
            click(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), "Select 'Services Tab'");
            report.updateTestLog("verifyServices", "' Navigate to Services", Status_CRAFT.PASS);
            waitForPageLoaded();
        }else{
            report.updateTestLog("verifyServices", "' Failed to navigate to Services", Status_CRAFT.FAIL);
        }
    }

    public void service_BackNavigation() throws Exception{
        String tabName[] =  dataTable.getData("General_Data","TabName").split("::");
        for(int i=0;i<tabName.length;i++){
                if(isElementDisplayed(getObject("xpath~//span[contains(text(),'"+tabName[i]+"')]"),5)){
                    click(getObject("xpath~//span[contains(text(),'"+tabName[i]+"')]"));
                    Thread.sleep(3000);
                    report.updateTestLog("ClickFunction", "Clicked on '"+tabName[i]+"' button successfully", Status_CRAFT.PASS);
                    if (isElementDisplayed(getObject("ServiceManage.backNavigation"),5)){
                        click(getObject("ServiceManage.backNavigation"));
                        Thread.sleep(3000);
                        report.updateTestLog("BackNavigation", "Clicked on back button successfully on Page", Status_CRAFT.PASS);
                }else{
                        report.updateTestLog("ClickFunction", "Unable to clicked on '"+tabName[i]+"' button", Status_CRAFT.FAIL);
                    }
            }
            }
        }

    /**
     * CFPUR-1228-To Verify PDF statements promotion
     */
    public void verifyPDFStatements() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");

        if (isElementDisplayed(getObject(deviceType() + "home.lnkShowmoreacc"), 20)) {
            click(getObject(deviceType() + "home.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
            Thread.sleep(9500);
            report.updateTestLog("verifyAccountStatement_AllAccounts", "Show more Accounts link exist and is clicked", Status_CRAFT.DONE);
        }

        String strParentObject = deviceType() + "home.parentaccount_new";

        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyCheckContentNotPresent", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        ScrollToVisibleJS(strParentObject);
        boolean bfound = false;
        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();
            if ((strRowText.contains(strAccountToCheck.toUpperCase())) && (strAccountIBAN.contains(strAccountIBAN.toUpperCase()))) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                Thread.sleep(3000);
                report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                js.executeScript("arguments[0].click();", oUIRows.get(j));
                waitForElementPresent(getObject("AccStatement.lblStatementTab"), 50);
                report.updateTestLog("verifyAccountStatement_AllAccounts", "The Account :: " + strAccountToCheck + ":: is displayed at position ::" + (j + 1) + ":: and clicked successfully.", Status_CRAFT.DONE);
                bfound = true;
                break;
            }
        }
        if(!bfound){
            report.updateTestLog("verifyAccountStatement_AllAccounts", "The Account :: " + strAccountToCheck + ":: is NOT present.", Status_CRAFT.FAIL);
            return;
        }

        report.updateTestLog("verifyAccountStatement_AllAccounts", "Blue Card Page", Status_CRAFT.SCREENSHOT);
        clickJS(getObject("AccStatement.lblStatementTab"), "Statemenets tab");
        Thread.sleep(5500);
        report.updateTestLog("verifyAccountStatement_AllAccounts", "Statements", Status_CRAFT.SCREENSHOT);

        if (isElementDisplayed(getObject(deviceType() + "AccStatementPDF"), 20)) {
            clickJS(getObject(deviceType() + "AccStatementPDF"), "AccStatementPDF");
            report.updateTestLog("verifyAccountStatement_AllAccounts_PDF", "PDF is Clicked", Status_CRAFT.PASS);
            Thread.sleep(5000);
            waitforInVisibilityOfElementLocated(getObject("AccStatementPDFSpinner"));
            waitForJQueryLoad();waitForPageLoaded();
            if (isElementDisplayed(getObject("ServiceManage.txtServiceUnavailable") , 8)){
                report.updateTestLog("selectServiceDeskOption", " 'Service unavailable :: Unfortunately, this service is unavailable at this time. Please try again later.' is displyed on Service Desk page", Status_CRAFT.FAIL);
            }
            click(getObject("AccPDFOk"));
            Thread.sleep(5000); // PDF takes time load as it's invoking the native app
        if (!isMobile) {
            report.updateTestLog("verifyAccountStatement_AllAccounts_PDF_OK", "Ok button clicked on poupup", Status_CRAFT.PASS);
            String ParentTab = driver.getWindowHandle();
            ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
            newTab.remove(ParentTab);
            // change focus to new tab
            driver.switchTo().window(newTab.get(0));
            Thread.sleep(10000);
            driver.getCurrentUrl();
            if (driver.getCurrentUrl().contains("Digital/downloadStatement")) {
                report.updateTestLog("verifyPDF_title", "PDF opened in new window", Status_CRAFT.PASS);
                //    driver.switchTo().window(ParentTab);
                //    report.updateTestLog("verifyParent_title", "ParentWindowValidated", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyPDF_title", "PDF is not opened in new window", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAccountStatement_AllAccounts_PDF_OK", "Ok button clicked on poupup", Status_CRAFT.PASS);
        }
    }
        else if (isElementDisplayed(getObject(deviceType() + "AccStatementPDFNotFound"), 20)) {
                report.updateTestLog("verifyAccountStatement_AllAccounts_PDF", "Statement PDF is not displayed for this account", Status_CRAFT.PASS);
                    }
        else {
            report.updateTestLog("verifyAccountStatement_AllAccounts_PDF", "Unable to click on Statement PDF", Status_CRAFT.FAIL);
        }
    }

    /**
     * Mobile App Automation Method : To Verify the Order up to date statements : CFPUR-71
     * Update on     Updated by     Reason
     * 27/05/2019    C966119
     */
    public void verifyOrderUpToDateStatements() throws Exception {

        String strAccountNumber = dataTable.getData("General_Data", "Account_Name");

        if (isElementDisplayed(getObject("xpath~//button[contains(text(),'Please select')]"),10)) {
            clickJS(getObject("xpath~//button[contains(text(),'Please select')]"), "Click on Select Account");
            String strAccToClick = "xpath~//button[contains(text(),'Please select')]/following-sibling::ul/li[contains(.,'" + strAccountNumber + "')]";

            if (!isMobile){
                driver.findElement(By.xpath("//button[contains(text(),'Please select')]/following-sibling::ul/li[contains(.,'" + strAccountNumber + "')]")).click();
            waitForPageLoaded();
            waitForJQueryLoad();
        }
          else  if(isMobile) {
                clickJS(getObject("xpath~//button[contains(text(),'Please select')]/following-sibling::ul/li[contains(.,'"+strAccountNumber+"')]"),"Clicked on Account Number IBAN Ending with :: ");
                waitForPageLoaded();
                waitForJQueryLoad();
            }
        }else{
            report.updateTestLog("verifyOrderUpToDateStatements", "'Please select' : drop down is not available ...please check..!!", Status_CRAFT.FAIL);
        }

        verifyMobielAndEmailOptions();

        clickJS(getObject("xpath~//span[text()='Continue']"), "Clicking on : Continue  : Button ");
       waitForPageLoaded();
    }

    /**
     * Mobile App Automation Method : To verify the Mobile, Email Options
     * Update on     Updated by     Reason
     * 27/05/2019    C966119
     */
    public void verifyMobielAndEmailOptions() throws Exception {
        String strMobileNumber = dataTable.getData("General_Data", "IBAN_Number");
        if (isElementDisplayed(getObject("ManageStatements.radioButtonYes"),10) && isElementDisplayed(getObject("ManageStatements.radioButtonNo"),10)) {
            clickJS(getObject("ManageStatements.radioButtonYes"), "Clicking on : Yes : Option");
            Thread.sleep(1000);
            waitForJQueryLoad();waitForPageLoaded();
            //Validate the Email and Mobile Options
            if (isElementDisplayed(getObject("ManageStatements.radioButtonMobile"), 5) && isElementDisplayed(getObject("ManageStatements.radioButtonEmail"), 5)) {
                clickJS(getObject("ManageStatements.radioButtonMobile"), "Clicking on : Mobile : Option");
                Thread.sleep(1000);
                waitForJQueryLoad();waitForPageLoaded();
                if (isElementDisplayed(getObject("xpath~//input[@type='tel']"), 10)) {
                    report.updateTestLog("verifyMobielAndEmailOptions", "After Selecting Mobile Option , Numeric input field opened successfully for Mobile number.", Status_CRAFT.PASS);
                }
                else{
                    report.updateTestLog("verifyMobielAndEmailOptions", "After Selecting Mobile Option , Numeric input field  not opened successfully for Mobile number.", Status_CRAFT.FAIL);
                }

                clickJS(getObject("ManageStatements.radioButtonEmail"), "Clicking on : Email : Option");
                Thread.sleep(1000);
                waitForJQueryLoad();waitForPageLoaded();
                if (isElementDisplayed(getObject("xpath~//input[@type='email']"), 10)) {
                    report.updateTestLog("verifyMobielAndEmailOptions", "After Selecting Email Option , Alpha Numeric input field opened successfully for Email ID", Status_CRAFT.PASS);
                }
                else{
                    report.updateTestLog("verifyMobielAndEmailOptions", "After Selecting Email Option , Alpha Numeric input field  not opened successfully for Email ID", Status_CRAFT.FAIL);
                }
            }

            clickJS(getObject("ManageStatements.radioButtonNo"), "Clicking on : No : Option");
            Thread.sleep(1000);
            waitForJQueryLoad();waitForPageLoaded();
            if (!(isElementDisplayed(getObject("ManageStatements.radioButtonMobile"), 1) && isElementDisplayed(getObject("ManageStatements.radioButtonEmail"), 1))) {
                report.updateTestLog("verifyMobielAndEmailOptions", "After Selecting No Option , Email ID/ Mobile field is not visible", Status_CRAFT.PASS);
            }
            else{
                report.updateTestLog("verifyMobielAndEmailOptions", "After Selecting No Option , Email ID/ Mobile field is visible which is not expected", Status_CRAFT.FAIL);
            }
            clickJS(getObject("ManageStatements.radioButtonYes"), "Clicking on : Yes : Option");
            Thread.sleep(1000);
            waitForJQueryLoad();waitForPageLoaded();
            clickJS(getObject("ManageStatements.radioButtonMobile"), "Clicking on : Mobile : Option");
            Thread.sleep(1000);
            waitForJQueryLoad();waitForPageLoaded();
            click(getObject("ManageStatements.textBoxMobile"));
            Thread.sleep(1000);
            sendKeys(getObject("ManageStatements.textBoxMobile"), strMobileNumber );
        }else{
            report.updateTestLog("verifyMobielAndEmailOptions", "Mobile and Email Options are not correctly displayed.", Status_CRAFT.FAIL);
        }
    }

    /**
     * Mobile App Automation Method : To Review page details
     * Update on     Updated by     Reason
     * 27/05/2019    C966119
     */
    public void verifyGeneralReviewPage() throws Exception {
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String strFieldName = "";
        String strFieldvalue = "";

        for (int i = 0; i < strVerifyDetails.length; i++) {
            strFieldName = strVerifyDetails[i].split("::")[0];
            strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        report.updateTestLog("verifyGeneralReviewPage", " :: Page Screenshot :: ", Status_CRAFT.DONE);
        for (Map.Entry obj : mapData.entrySet()) {
            if (isElementDisplayed(getObject("xpath~//*[contains(text(),'" + obj.getKey() + "')]/ancestor::div[contains(@class,'tc-question')]/following-sibling::*/descendant::*[contains(text(),'" + obj.getValue() + "')]"), 5)) {
                report.updateTestLog("verifyGeneralReviewPage", "'" + obj.getKey() + "' :: correctly displayed on review page,input:: " + obj.getValue(), Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyGeneralReviewPage", "'" + obj.getKey() + "' :: Not correctly displayed on review page,Expected:: " + obj.getValue(), Status_CRAFT.FAIL);
            }
        }

        Thread.sleep(2500);
        clickJS(getObject("xpath~//span[text()='Confirm']"),"Click on Confirm Button");
    }

    /**
     * Mobile App Automation Method : To Validate Directional text and Success icon on the confirmation page
     * Update on     Updated by     Reason
     * 27/05/2019    C966119
     */
    public void verifyElementDetailsConfimMobile() throws Exception {
        String[] arrValidation = dataTable.getData("General_Data", "VerifyContent").split(";");
        String dataSectionUI = "";
        String strValidateHead = "";
        String strValidateData = "";
        if (isElementDisplayed(getObject("AddPolicy.imgSuccess"), 5)) {
            report.updateTestLog("verifyElementDetailsConfim", " Success image is displayed correctly on request sent confirmation page.", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyElementDetailsConfim", " Success image is not displayed correctly on request sent confirmation page.", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("ManageStatements.paraText"), 5)) {
            List<WebElement> para = findElements(getObject("ManageStatements.paraText"));

                for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                    report.updateTestLog("verifyElementDetailsConfim", ":: Page Screenshot ::", Status_CRAFT.DONE);
                    strValidateHead = arrValidation[intValidate].split("::")[0];
                    strValidateData = arrValidation[intValidate].split("::")[1];
                    String paragraphUI= para.get(intValidate).getText();
               
                    if (paragraphUI.contains(strValidateData)) {
                        report.updateTestLog("verifyElementDetailsConfim", strValidateHead + " value '" + strValidateData + "' is displayed correctly ", Status_CRAFT.DONE);
                    } else {
                        report.updateTestLog("verifyElementDetailsConfim", strValidateHead + " value '" + strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                    }
            }

        } else {
            report.updateTestLog("verifyElementDetailsConfim", "Section element is not displayed on Request sent confirmation Screen", Status_CRAFT.FAIL);
        }

        click(getObject("services.btnBackToHome"),"Clicked on 'Back to home'");
        SCA_MobileAPP homepage = new SCA_MobileAPP(scriptHelper);
        homepage.validateHomepage();
    }

    public void verifyElementDetailsConfim(String sectionElement) throws Exception {

        String[] arrValidation = dataTable.getData("General_Data", "VerifyContent").split(";");
        if (isElementDisplayed(getObject(sectionElement), 5)) {
            String dataSectionUI = getText(getObject(sectionElement)).toUpperCase();
            report.updateTestLog("verifyElementDetailsConfim", ":: Page Screenshot ::", Status_CRAFT.SCREENSHOT);
        
            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                String strValidateHead = arrValidation[intValidate].split("::")[0];
                String strValidateData = arrValidation[intValidate].split("::")[1];
                if (dataSectionUI.contains(strValidateData.toUpperCase())) {
                    report.updateTestLog("verifyElementDetailsConfim", strValidateHead + " value '" + strValidateData + "' is displayed correctly ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyElementDetailsConfim", strValidateHead + " value '" + strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                }
            }

        } else {
            report.updateTestLog("verifyElementDetailsConfim", "Section element is not displayed on Screen", Status_CRAFT.FAIL);
        }
    }

    /**
     * Reusable : Smoke
     * Manage Statements email
     */
    public void verifyManageStatementsEmailNotificationSmoke() throws Exception {

        //Verify the flow considering the current status
        String strFlow = "";
        report.updateTestLog("verifyManageStatementsEmailNotificationSmoke", ":: Page Screenshot ::", Status_CRAFT.SCREENSHOT);
        if (isElementDisplayed(getObject("xpath~//span[@class='boi_moreinfo'][contains(text(),'ON')]"),30)){
            strFlow = "Email_Notification_On_To_Off";
        }else if (isElementDisplayed(getObject("xpath~//span[@class='boi_moreinfo'][contains(text(),'OFF')]"),10)){
            strFlow = "Email_Notification_Off_To_On";
        }
        clickJS(getObject("ManageStatements.lnkStmntNotification"), "Click Menu Option : Email Statement Notification.");
        boolean blnON = false;
        report.updateTestLog("verifyManageStatementsEmailNotificationSmoke", ":: Page Screenshot :: "+ strFlow , Status_CRAFT.SCREENSHOT);

        if (isElementDisplayed(getObject("ManageStatements.btnEmailNotification"),40)){
            //Verify the On/Off Flag
            if (driver.findElement(getObject("xpath~//input[contains(@name,'ISESTATEMENTEMAILON')]")).getAttribute("checked").equals("true")){
                blnON = true;
                report.updateTestLog("verifyManageStatementsEmailNotification", "Email Statement notification toggle button Status : ON " , Status_CRAFT.DONE);
            }else{
                blnON = false;
                report.updateTestLog("verifyManageStatementsEmailNotification", "Email Statement notification toggle button Status : OFF " , Status_CRAFT.DONE);
            }
        }else{
            report.updateTestLog("verifyManageStatementsEmailNotification", "Email Statement notification toggle button is not correctly displayed.", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("xpath~//div[contains(@class,'ecDIB  boi-flex')]/descendant::span[text()='Notifications']"),3) &&
                isElementDisplayed(getObject("xpath~//div[contains(@class,'ecDIB  boi-flex')]/descendant::span[text()='test@tester.com']"),3) &&
                isElementDisplayed(getObject("xpath~//div[contains(@class,'ecDIB  boi-flex')]/descendant::span[text()='Email']"),3)){
            report.updateTestLog("verifyManageStatementsEmailNotification", "The Text : Notifications and Email ID : test@tester.com" , Status_CRAFT.DONE);
        }else {
            report.updateTestLog("verifyManageStatementsEmailNotification", "Email Statement notification text is not correctly displayed " , Status_CRAFT.FAIL);
        }

        clickJS(getObject("ManageStatements.btnEmailNotification"), "Click on ON/OFF button : Email Statement Notification.");
        clickJS(getObject("xpath~//button[text()='Ok']"), "Click on Ok button on pop up.");
        //Verify the Change updated correctly
        if (strFlow.equals("Email_Notification_On_To_Off")){
            if (isElementDisplayed(getObject("xpath~//span[@class='boi_moreinfo'][text()='OFF']"),15)){
                report.updateTestLog("verifyManageStatementsEmailNotification", "Email_Notification_On_To_Off : Notification status is updated correctly as ON" , Status_CRAFT.DONE);
            }
        }else if (strFlow.equals("Email_Notification_Off_To_On")){
            if (isElementDisplayed(getObject("xpath~//span[@class='boi_moreinfo'][text()='ON']"),15)){
                report.updateTestLog("verifyManageStatementsEmailNotification", "Email_Notification_Off_To_On : Notification status is updated correctly as OFF" , Status_CRAFT.DONE);
            }
        }

        // back to the Manage statements
        clickJS(getObject("xpath~//h4[text()='Statement Notifications']"), "Click Statement Notifications breadcrum.");
    }

    /**
     * Reusable : Smoke
     * Manage Statements
     */
    public void verifyManageStatementsSmoke() throws Exception {
        verifyManageStatementOptions();
        verifyPaperStatements();
        click(getObject("xpath~//input[@title='DesktopBackNavigation']"));
        verifyManageStatementsEmailNotificationSmoke();
    }
    public void VerifyBBKnone() throws Exception{
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        String strJurisdictionType = dataTable.getData("General_Data","JurisdictionType");
        if(isElementDisplayed(getObject(deviceType()+"services.tilemanagecard"),5)) {
            report.updateTestLog("verifyServicePagelayout", "Manage card header is as expected", Status_CRAFT.PASS);
            if(isElementDisplayed(getObject(deviceType()+"services.Reportloststolendcard"),5)) {
                clickJS(getObject(deviceType() + "services.Reportloststolendcard"), "Report lost or stolen card tile is displayed and clicked");
                isElementDisplayed(getObject(deviceType() + "services.loststolendcard"), 5);
                report.updateTestLog("verifyServicePagelayout", "Lost stolen card page title is as expected", Status_CRAFT.PASS);
                isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                clickJS(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
            }else {
                report.updateTestLog("verifyServicePagelayout", "Lost stolen card tile is not as expected", Status_CRAFT.FAIL);
            }
            if(isElementDisplayed(getObject(deviceType()+"services.ReplaceDamagedDebitCard"),5)){
                clickJS(getObject(deviceType()+"services.ReplaceDamagedDebitCard"),"Replace Damaged Debit Card is clicked");
                isElementDisplayed(getObject(deviceType()+"services.ReplaceDamagedDCardtitle"),5);
                report.updateTestLog("verifyServicePagelayout", "Replace damage debit card page title is as expected", Status_CRAFT.PASS);
                isElementDisplayed(getObject(deviceType()+"services.gobackloststolendcard"),5);
                clickJS(getObject(deviceType()+"services.gobackloststolendcard"),"Replace Damaged Debit Card Page is clicked");
            }else {
                report.updateTestLog("verifyServicePagelayout", "Replace damage debit card tile is not as expected", Status_CRAFT.FAIL);
            }
            if (strJurisdictionType.equalsIgnoreCase("UK")){
                if(isElementDisplayed(getObject(deviceType()+"service.Creditcardtile"),5)){
                    clickJS(getObject(deviceType()+"service.Creditcardtile"),"Credit Card tile is clicked");
                    isElementDisplayed(getObject(deviceType()+"service.creditcardtitle"),5);
                    report.updateTestLog("verifyServicePagelayout", "credit card page title is as expected", Status_CRAFT.PASS);
                    isElementDisplayed(getObject(deviceType()+"services.creditcardok"),5);
                    clickJS(getObject(deviceType()+"services.creditcardok"),"Credit Card Page ok button is clicked");
                }else {
                    report.updateTestLog("verifyServicePagelayout", "Replace damage debit card tile is not as expected", Status_CRAFT.FAIL);
                }
            }
            else{
                report.updateTestLog("verifyServicePagelayout", "Credit card tile is not valid for ROI", Status_CRAFT.PASS);
            }
        }else {
            report.updateTestLog("verifyServicePagelayout", "Manage card header is as not as expected", Status_CRAFT.FAIL);
        }

        // To validate Security device tabs
        if (isElementDisplayed(getObject(deviceType()+"services.managesecurityDevice"),5)){
            report.updateTestLog("verifyServicePagelayout", "Manage Security header is as expected", Status_CRAFT.PASS);
            if(isElementDisplayed(getObject(deviceType()+"services.SecurityDevices"),5)){
                clickJS(getObject(deviceType()+"services.SecurityDevices"),"Security devices tile is clicked");
                isElementDisplayed(getObject(deviceType()+"services.loststolendcard"),5);
                report.updateTestLog("verifyServicePagelayout", "Lost stolen card page title is as expected", Status_CRAFT.PASS);
                isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                clickJS(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
            }else{
                report.updateTestLog("verifyServicePagelayout", "Security device tile is not as expected", Status_CRAFT.FAIL);
            }
        }else{
            report.updateTestLog("verifyServicePagelayout", "Manage Security header is not as expected", Status_CRAFT.FAIL);
        }


        // To validate Manage accounts tabs and tiles

        //Change address
        if(isElementDisplayed(getObject(deviceType()+"services.Manageaccounts"),5)){
            report.updateTestLog("verifyServicePagelayout", "Manage Security header is as expected", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject(deviceType()+"services.ChangeAddress"),5)){
                clickJS(getObject(deviceType()+"services.ChangeAddress"),"Change address tile is clicked ");
                waitForPageLoaded();
                isElementDisplayed(getObject(deviceType()+"services.changeAddresstitle"),5);
                report.updateTestLog("verifyServicePagelayout", "Change address page title is as expected", Status_CRAFT.PASS);
                isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                clickJS(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
            }else{
                report.updateTestLog("verifyServicePagelayout", "Change address tile is not as expected", Status_CRAFT.FAIL);
            }

            //Manage Statements
            if (isElementDisplayed(getObject(deviceType()+"services.Mangestatements"),5)){
                clickJS(getObject(deviceType()+"services.Mangestatements"),"Manage Statement tile is clicked ");
                waitForPageLoaded();
                isElementDisplayed(getObject(deviceType()+"services.Managestatemetstitle"),5);
                report.updateTestLog("verifyServicePagelayout", "Manage Statement page title is as expected", Status_CRAFT.PASS);
                isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                clickJS(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
            }else{
                report.updateTestLog("verifyServicePagelayout", "Manage Statement page title is not as expected", Status_CRAFT.FAIL);
            }

            // Add Account
            if(strJurisdictionType.equalsIgnoreCase("ROI")){
                if (isElementDisplayed(getObject(deviceType()+"services.addaccount"),5)){
                    clickJS(getObject(deviceType()+"services.addaccount"),"Add account and add policy tile is clicked ");
                    waitForPageLoaded();
                    isElementDisplayed(getObject(deviceType()+"services.addaccounttitle"),5);
                    report.updateTestLog("verifyServicePagelayout", "Add account and add policy title is as expected", Status_CRAFT.PASS);
                    isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                    clickJS(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
                }else{
                    report.updateTestLog("verifyServicePagelayout", "Manage Statement page title is not as expected", Status_CRAFT.FAIL);
                }
            }else{
                if (isElementDisplayed(getObject(deviceType()+"services.addaccount"),5)){
                    clickJS(getObject(deviceType()+"services.addaccount"),"Add account and add policy tile is clicked ");
                    waitForPageLoaded();
                    isElementDisplayed(getObject(deviceType()+"services.addaccounttitle"),5);
                    report.updateTestLog("verifyServicePagelayout", "Add account and add policy title is as expected", Status_CRAFT.PASS);
                    isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                    clickJS(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
                }
            }

            //manage alerts account
            if(strJurisdictionType.equalsIgnoreCase("UK")){
                if (isElementDisplayed(getObject(deviceType()+"service.manageAlerts"),5)){
                    //click(getObject(deviceType()+"services.Register/manage"),"Register manage pay to mobile tile is clicked ");
                    waitForPageLoaded();
                    //isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                    //click(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
                    report.updateTestLog("verifyServicePagelayout", "Register/Manage pay to mobile tile is as expected", Status_CRAFT.PASS);
                }else{
                    report.updateTestLog("verifyServicePagelayout", "Register/Manage pay to mobile tile is not as expected", Status_CRAFT.FAIL);
                }
            }else{
                report.updateTestLog("verifyServicePagelayout", "Manage account alerts is not valid for ROI", Status_CRAFT.PASS);
            }

            //Register/manage pay to mobile
            if (isElementDisplayed(getObject(deviceType()+"services.Register/manage"),5)){
                //click(getObject(deviceType()+"services.Register/manage"),"Register manage pay to mobile tile is clicked ");
                waitForPageLoaded();
                //isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                //click(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
                report.updateTestLog("verifyServicePagelayout", "Register/Manage pay to mobile tile is as expected", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("verifyServicePagelayout", "Register/Manage pay to mobile tile is not as expected", Status_CRAFT.FAIL);
            }

            //Order interest certificate
            if (isElementDisplayed(getObject(deviceType()+"services.orderinterestcertificate"),5)){
                clickJS(getObject(deviceType()+"services.orderinterestcertificate"),"Order interest certificate tile is clicked ");
                waitForPageLoaded();
                isElementDisplayed(getObject(deviceType()+"services.OICtitle"),5);
                report.updateTestLog("verifyServicePagelayout", "Order interest certificate page title is as expected", Status_CRAFT.PASS);
                isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                clickJS(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
            }else{
                report.updateTestLog("verifyServicePagelayout", "Order interest certificate title is not as expected", Status_CRAFT.FAIL);
            }

            //Order balance ceritificate
            if (isElementDisplayed(getObject(deviceType()+"services.ordrbalnccertificate"),5)){
                clickJS(getObject(deviceType()+"services.ordrbalnccertificate"),"Order Balance certificate tile is clicked ");
                waitForPageLoaded();
                isElementDisplayed(getObject(deviceType()+"services.OCBTile"),5);
                report.updateTestLog("verifyServicePagelayout", "Order Balance certificate page title is as expected", Status_CRAFT.PASS);
                isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                clickJS(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
            }else{
                report.updateTestLog("verifyServicePagelayout", "Order Balance certificate title is not as expected", Status_CRAFT.FAIL);
            }
        }else{
            report.updateTestLog("verifyServicePagelayout", "Manage Account header is not as expected", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject(deviceType()+"services.Needhelp"),5)){
            report.updateTestLog("verifyServicePagelayout", "Need Help header is as expected", Status_CRAFT.PASS);
            if(isElementDisplayed(getObject(deviceType()+"services.contactus"),5)){
                clickJS(getObject(deviceType()+"services.contactus"),"Contact us tile is clicked");
                isElementDisplayed(getObject(deviceType()+"services.contactustitle"),5);
                report.updateTestLog("verifyServicePagelayout", "Contact us page title is as expected", Status_CRAFT.PASS);
                isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                clickJS(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
            }else{
                report.updateTestLog("verifyServicePagelayout", "Contact us tile is not as expected", Status_CRAFT.FAIL);
            }
            if(isElementDisplayed(getObject(deviceType()+"services.FAQs"),5)){
                /*click(getObject(deviceType()+"services.FAQs"),"Contact us tile is clicked");
                isElementDisplayed(getObject(deviceType()+"services.contactustitle"),5);*/
                report.updateTestLog("verifyServicePagelayout", "Contact us page title is as expected", Status_CRAFT.PASS);
               /* isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                click(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");*/
            }else{
                report.updateTestLog("verifyServicePagelayout", "Contact us tile is not as expected", Status_CRAFT.FAIL);
            }

        }else{
            report.updateTestLog("verifyServicePagelayout", "Need help  header is not as expected", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject(deviceType()+"services.Moreservices"),5)){
            report.updateTestLog("verifyServicePagelayout", "More Services header is as expected", Status_CRAFT.PASS);
            if(isElementDisplayed(getObject(deviceType()+"services.sendusmessage"),5)){
                clickJS(getObject(deviceType()+"services.sendusmessage"),"Contact us tile is clicked");
                isElementDisplayed(getObject(deviceType()+"AAQ.textbeforeucont"),5);
                report.updateTestLog("verifyServicePagelayout", "Send us a message page title is as expected", Status_CRAFT.PASS);
                isElementDisplayed(getObject(deviceType() + "AAQ.btnGoBack"), 5);
                clickJS(getObject(deviceType() + "AAQ.btnGoBack"), "Go back button is clicked and navigated back to services page ");
            }else{
                report.updateTestLog("verifyServicePagelayout", "Send us a message tile is not as expected", Status_CRAFT.FAIL);
            }

        }
        else{
            report.updateTestLog("verifyServicePagelayout", "More services  header is not as expected", Status_CRAFT.FAIL);
        }}
    public void verifyServicePagelayout() throws Exception{
        String strJurisdictionType = dataTable.getData("General_Data","JurisdictionType");
        VerifyBBKnone();
        if(strJurisdictionType.equalsIgnoreCase("ROI")){
            if(isElementDisplayed(getObject(deviceType()+"services.moreservices"),5)){
                clickJS(getObject(deviceType()+"services.moreservices"),"Contact us tile is clicked");
                isElementDisplayed(getObject(deviceType()+"services.moreservicestitle"),5);
                report.updateTestLog("verifyServicePagelayout", "Contact us page title is as expected", Status_CRAFT.PASS);
                isElementDisplayed(getObject(deviceType() + "services.gobackloststolendcard"), 5);
                clickJS(getObject(deviceType() + "services.gobackloststolendcard"), "Go back button is clicked and navigated back to services page ");
            }else{
                report.updateTestLog("verifyServicePagelayout", "More services tile is not as expected", Status_CRAFT.FAIL);
            }}else{
            report.updateTestLog("verifyServicePagelayout", "More services title is as not expected for NI", Status_CRAFT.PASS);
        }
    }


    /**
     * Mobile App Automation Method : Validate the Error Messages for Mobile Field
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void validateMobilePhoneErrors() throws Exception {
        String strErrorMessageInputValue[] = dataTable.getData("General_Data", "VerifyDetails").split("::");

        for (int i = 0; i < strErrorMessageInputValue.length; i++) {
            String  strErrorMessageCheck[] = strErrorMessageInputValue[i].split(";");
            String  strInputValue= strErrorMessageCheck[0];
            String  strErrorMessage = strErrorMessageCheck[1];
            if (isElementDisplayed(getObject("profile.inputboxProfile"),20)){
                click(getObject("profile.inputboxProfile"));
                sendKeys(getObject("profile.inputboxProfile") , strInputValue);
                waitForElementPresent(getObject("AddPolicy.Continue"),5);
                click(getObject("AddPolicy.Continue"));
            }

            if (isElementDisplayed(getObject("xpath~//*[contains(@class,'qlrError boi_input_sm_error tc-error-position tc-fs-m2  ')]"),15)){
                String strText = getText(getObject("xpath~//*[contains(@class,'qlrError boi_input_sm_error tc-error-position tc-fs-m2  ')]"));
                if (strText.equals(strErrorMessage)) {
                    sendKeys(getObject("profile.inputboxProfile") , "");
                    Thread.sleep(4000);
                    report.updateTestLog("validateMobilePhoneEmailErrors", " Input box :: Error Message :: '" + strErrorMessage + "':: Input Value :: " + strInputValue + " :: Which is expected.", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("validateMobilePhoneEmailErrors", " Input box :: Error Message :: '" + strErrorMessage + "':: is NOT displayed Successfully...for Input Value :: " + strInputValue, Status_CRAFT.FAIL);
                }
            }
        }

    }

    /**
     * Mobile App Automation Method : Verify that Manage Credit Card link should not present for MOBILE APP (negative check)
     * Update on     Updated by     Reason
     * 20/06/2019    C966055        for mobile Automation
     */
    public void verifyCreditCardTile() throws Exception {
        waitForElementPresent(getObject(deviceType() + "home.btnServiceDesk"),5);
        clickJS(getObject(deviceType() + "home.btnServiceDesk"),"Service Desk");
        if (isElementDisplayed(getObject("Services.creditCardServicestile"),5)){
            report.updateTestLog("verifyCreditCardServicesTile", "Credit Card Services tile is displayed on mobile which is not expected", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyCreditCardServicesTile", "Credit Card Services tile is not displayed on mobile which is as expected ", Status_CRAFT.PASS);
        }
    }

    public void switchPaperStatementForAccount() throws Exception{
        String strAccountName = dataTable.getData("General_Data","Account_Name");
        String xpath;
        if(strAccountName.equalsIgnoreCase("ANY")){
            xpath = "(//*[@class='boi-standard-flipswitch  ']/descendant::input[@type='checkbox'])[1]";
            clickJS(getObject("xpath~"+xpath),"Paper statement toggle switch clicked for 1st account ");
        }else{
            xpath = "//label[text()='"+strAccountName+"']/ancestor::*[@class='boi-standard-flipswitch  ']/descendant::input[@type='checkbox']";
            clickJS(getObject("xpath~"+xpath),"Paper statement toggle switch clicked for account "+strAccountName);
        }

    }

    public void verifyElementDetails(String strSectionElement) throws Exception {
        String[] arrValidation = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String dataSectionUI = "";
        String strValidateHead = "";
        String strValidateData = "";
        if (isElementDisplayed(getObject(strSectionElement), 5)) {
            dataSectionUI = getText(getObject(strSectionElement)).toUpperCase();
            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                strValidateHead = arrValidation[intValidate].split("::")[0];
                strValidateData = arrValidation[intValidate].split("::")[1];
                if (dataSectionUI.contains(strValidateData.toUpperCase())) {
                    report.updateTestLog("verifyElementDetails", strValidateHead + " value '" + strValidateData + "' is displayed correctly ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyElementDetails", strValidateHead + " value '" + strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("verifyElementDetails", "Section element '" + strSectionElement + "' is not displayed on Screen", Status_CRAFT.FAIL);
        }
    }

    public void servicepageverification() throws Exception{
        if (isElementDisplayed(getObject(deviceType() + "xpath~//div[@class='tc-ripple-effect responsive-row boi-margin-accountdetails account-type-border tabindex    color-credit-card-account    ']"), 10)) {
            report.updateTestLog("Account tile ", "Credit card tile is displayed on Page", Status_CRAFT.PASS);
            String colorCode = "#58bedb";
            String color = driver.findElement(By.xpath("//div[@class='tc-ripple-effect responsive-row boi-margin-accountdetails account-type-border tabindex    color-credit-card-account    ']")).getCssValue("border-color");
            String hexColorValue = Color.fromString(color).asHex();
            System.out.println(hexColorValue);
            String arrValidation = dataTable.getData("General_Data", "VerifyDetails");
            if (colorCode.equalsIgnoreCase(hexColorValue)) {
                report.updateTestLog("Credit card", "Color code is equal to hex color value that is 'Blue' for credit card", Status_CRAFT.PASS);
                ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
                //verifyElementDetails("xpath~//div[@class='col-full-xs col-full-sm col-3-4-md col-3-4-lg col-3-4-xl tc-center-align-block services-wrapper']");
            } else {
                report.updateTestLog("Credit card", "Color code is not equal to hex color value ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("Account tile ", "Credit card is not displayed on Page", Status_CRAFT.FAIL);
        }
    }
}
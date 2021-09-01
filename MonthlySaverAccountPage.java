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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Function/Epic: Open 365 Monthly Saver Account
 * Class: MonthlySaverAccountPage
 * Developed on: 12/03/2018
 * Developed by: C966003
 * Update on     Updated by    Reason
 * 12/04/2019    C966119       Done code clean up activity
 */
public class MonthlySaverAccountPage extends WebDriverHelper {
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */

    public MonthlySaverAccountPage (ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function : Navigate to the Monthly Saver Page
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void NavigateMonthlySaverAccountPage() throws Exception {
        click(getObject(deviceType() + "home.btnServiceDesk"));
        click(getObject("MonthlySaver.btnOpenSaver"), "Click on Open Monthly Saver iocn ");
    }

    /**
     * Function : To Verify text of a particular section/element , text of the section
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void verifyTextDetails(String sectionElement , String strExcelColumn ) throws Exception {
        String[] arrValidation = dataTable.getData("General_Data", strExcelColumn).split("::");
        if (isElementDisplayed(getObject(sectionElement), 10)) {
            String dataSectionUI = getText(getObject(sectionElement)).toUpperCase();
            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                if (dataSectionUI.contains(arrValidation[intValidate].toUpperCase())) {
                    report.updateTestLog("verifyTextDetails",  arrValidation[intValidate] + " :: value is displayed correctly on page", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyTextDetails",arrValidation[intValidate] + " :: value is NOT displayed correctly or not present ", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("verifyElementDetails", "Section element '" + sectionElement + "' is not displayed on Screen", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Scroll to view particular  element using JS
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void ScrollToVisibleJS(String linkToVisible) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToVisible))));
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }


    }

    /**
     * Function : Scroll and Click on particular  element using JS
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void ScrollAndClickJS(String linkToClick) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToClick))));
            report.updateTestLog("ScrollAndClickJS", "Element To Click", Status_CRAFT.SCREENSHOT);
            JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
            executor.executeScript("arguments[0].click();", driver.findElement(getObject((linkToClick))));
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function : Saving Application look and feel :: Page 1 :: Eligibility : CFPUR-302
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void verifyMonthlySaverEligibilityPage() throws Exception {

        String linkToVerify = dataTable.getData("General_Data", "Account_Grouped");

        report.updateTestLog("verifyMonthlySaverEligibilityPage",  ":: Page Screenshots::", Status_CRAFT.SCREENSHOT);
        verifyTextDetails("MonthlySaver.sectionParentPage1" , "Current_Balance");
        verifyTableValues("Available_Balance","Currency_Symbol");
        clickJS(getObject("MonthlySaver.lnkTermNConditions") , "Clicking on link :'Terms and conditions'");
        verifyNewlyOpenedTab(linkToVerify);
        clickJS(getObject("MonthlySaver.btnOpenAccnt") , "Clicking on button :'Open Account'");
    }

    /**
     * Function : Saving Application Consent Page :: Page 4 :: Consent
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void verifyMonthlySaverConsentPage() throws Exception {
        String[] arrDataValidateTable = dataTable.getData("General_Data", "VerifyContent").split("::");
        report.updateTestLog("verifyMonthlySaverConsentPage",  ":: Page Screenshots::", Status_CRAFT.SCREENSHOT);
        for (int i = 0; i < arrDataValidateTable.length; i++) {
            String objLink = arrDataValidateTable[i].split(":;")[0];
            String strLinkToVerify = arrDataValidateTable[i].split(":;")[1];
            clickJS(getObject(objLink) , "Clicking on link :'Terms and conditions'");
            verifyNewlyOpenedTab(strLinkToVerify);
        }
        verifyTextDetails("MonthlySaver.sectionParentPage1" , "Current_Balance");
        verifyTableValues("Available_Balance","Currency_Symbol");
        clickJS(getObject("MonthlySaver.lnkTermNConditions") , "Clicking on link :'Terms and conditions'");
        clickJS(getObject("MonthlySaver.btnOpenAccnt") , "Clicking on button :'Open Account'");
    }

    /**
     * Function : verifyTableValues , Verify the Table-Data , Table-Column
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void verifyTableValues(String strExcelColumnData , String strExcelColumnName )throws Exception {

        String[] arrDataValidateTable = dataTable.getData("General_Data", strExcelColumnData).split("::");
        String[] arrColumnTable = dataTable.getData("General_Data", strExcelColumnName).split("::");

        List<WebElement> rowsTable = driver.findElements(By.cssSelector("tbody > tr"));
        List<WebElement> columnTable = driver.findElements(By.cssSelector("tr > th"));
        List<WebElement> dataFromTable = driver.findElements(By.cssSelector("tr > td"));

        for (int i = 1; i < columnTable.size(); i++) {
            if(columnTable.get(i).getText().equals(arrColumnTable[i])){
                report.updateTestLog("verifyTableValues",  "Matching Table Header value :: "+ columnTable.get(i).getText(), Status_CRAFT.DONE);
            }else{
                report.updateTestLog("verifyTableValues",  "Not Matching Table Header value :: "+ columnTable.get(i).getText(), Status_CRAFT.FAIL);
            }
        }

        for (int i = 1; i < dataFromTable.size(); i++) {
            if(dataFromTable.get(i).getText().equals(arrDataValidateTable[i])){
                report.updateTestLog("verifyTableValues",  "Data value :: "+ dataFromTable.get(i).getText(), Status_CRAFT.DONE);
            }else{
                report.updateTestLog("verifyTableValues",  "Not Matching Table Header value :: "+ dataFromTable.get(i).getText(), Status_CRAFT.FAIL);
            }
        }

    }

    /**
     * Function : Method to verify the Newly opened tab and its URL
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void verifyNewlyOpenedTab(String linkToVerify) throws Exception {
        waitForPageLoaded();

        // Store all currently open tabs in tabs
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        if (tabs.size() > 0) {
            // Switch newly open Tab
            driver.switchTo().window(tabs.get(1));
            waitForPageLoaded();
            String linkURL = driver.getCurrentUrl();
            if (linkURL.equalsIgnoreCase(linkToVerify)) {
                report.updateTestLog("verifyNewlyOpendTab", " After clicking on link, new tab with correct url :: '" + linkToVerify + "':: opened successfully.", Status_CRAFT.PASS);
                // Close newly open tab after performing some operations.
                driver.close();
                driver.switchTo().window(tabs.get(0));
            } else {
                report.updateTestLog("verifyNewlyOpendTab", " Fail..Newly Opened Tab did not match with expected url :: " + linkToVerify, Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyNewlyOpendTab", " New Tab did not opened successfully..Please check...!", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : General Review Page
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void verifyGeneralReviewPage() throws Exception {
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        report.updateTestLog("verifySendMoneyReviewPage", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        for (Map.Entry obj : mapData.entrySet()) {
            //Check for the account from
            if (obj.getKey().toString().contains("Account from")){
                if (isElementDisplayed(getObject("xpath~//*[contains(text(),'"+ obj.getValue() +"')]"),1)){
                    report.updateTestLog("verifyGeneralReviewPage", " 'Account from which you'll transfer funds' correctly displayed on review page,input ::" + obj.getValue(), Status_CRAFT.DONE);
                }
            }

            if (isElementDisplayed(getObject("xpath~//*[contains(text(),'" + obj.getKey() +"')]/following-sibling::*[contains(text(),'" + obj.getValue() + "')]"), 5)) {
                report.updateTestLog("verifyGeneralReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyGeneralReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
            }
        }

        clickJS(getObject("xpath~//span[contains(text(),'Continue')]"),"Click on Continue Button");

    }

    /**
     * Function : CFPUR-5123 : Open 365 Monthly Saver Account Look and feel  (Page 2 form)
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void verifyMonthlySaverForm2() throws Exception {
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String strAmount = strVerifyDetails[0].split("::")[1];
        String strAccountNumber = strVerifyDetails[1].split("::")[1];
        String strStartDate = strVerifyDetails[2].split("::")[1];
        String strFullName = strVerifyDetails[3].split("::")[1];
        String strPPSNumber = strVerifyDetails[4].split("::")[1];

        verifyTextDetails("MonthlySaver.sectionParentPage1" , "Current_Balance");
        verifyAndSelectAccount(strAccountNumber);
        click(getObject("MonthlySaver.txtAmount"));
        sendKeys(getObject("MonthlySaver.txtAmount") , strAmount);
        sendKeys(getObject("MonthlySaver.txtStartDate") , strStartDate);
        sendKeys(getObject("MonthlySaver.txtFullName") , strFullName);
        clickJS(getObject("MonthlySaver.chkConfirm") , "Select the 'Confirm' check box");
        verifyOptionsForPPS(strPPSNumber);

        clickJS(getObject("xpath~//span[text()='Continue']"), "Clicking on : Continue  : Button");

    }

    /**
     * Function : Select particular account from drop down
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void verifyAndSelectAccount(String strAccountNumber) throws Exception {
        clickJS(getObject("MonthlySaver.drpDwnSelectAccnt"), "Click on Select Account");
        List<WebElement> arrValidationAccnt = findElements(getObject("xpath~//li[contains(@class,'option')]"));
        int index = 0;
        for (int i = 0; i < arrValidationAccnt.size(); i++) {
            String strAccntText = arrValidationAccnt.get(i).getText();
            if (strAccntText.contains(strAccountNumber)) {
                index = i;
            }
        }
        String strAccToClick = "xpath~//li[contains(@class,'option')][@data-value='" + index + "']";
        clickJS(getObject(strAccToClick), "Click on Option :" + strAccountNumber);
    }

    /**
     * Function : CFPUR-5565 :: Monthly saver PPS field fix
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void verifyOptionsForPPS(String strPPSNumber) throws Exception {

        if (strPPSNumber.equals("Not Provided")){
            clickJS(getObject("MonthlySaver.chkNoPPS"), "Selecting : No : PPS Option");
            if (isElementDisplayed(getObject("xpath~//label[text()='I do not have my PPS number at this time but will provide it and proof documentation to my local branch.']"),5)){
                report.updateTestLog("verifyOptionsForPPS", " The PPS number not provided message is correctly displayed " , Status_CRAFT.DONE);
            }
        }else{
            clickJS(getObject("MonthlySaver.chkYesPPS"), "Selecting : Yes : PPS Option");
            if (isElementDisplayed(getObject("MonthlySaver.txtInputPPS") , 3) && isElementDisplayed(getObject("xpath~//label[text()='I declare this number to be true and accurate and I will provide my PPS number proof documentation to my local branch.']"),5)){
                sendKeys(getObject("MonthlySaver.txtInputPPS") , strPPSNumber);
                report.updateTestLog("verifyOptionsForPPS", " The PPS number provided message is correctly displayed " , Status_CRAFT.DONE);
            }
        }

        clickJS(getObject("MonthlySaver.chkBxDeclare") , "Select the PPS declration check box.");
    }

    /**
     * Function : CFPUR-5126 :: Open 365 Monthly Saver Account Look and feel  (Page 5 Success)
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void PayeeAddConfirmation(String sectionElement) throws Exception {
        String[] arrValidation = dataTable.getData("General_Data", "VerifyContent").split(";");
        if (isElementDisplayed(getObject(sectionElement), 120)) {
            String dataSectionUI = getText(getObject(sectionElement)).toUpperCase();
            report.updateTestLog("PayeeAddConfirmation", ":: Page Screenshots ::", Status_CRAFT.SCREENSHOT);

            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                String strValidateHead = arrValidation[intValidate].split("::")[0];
                String strValidateData = arrValidation[intValidate].split("::")[1];
                if (dataSectionUI.contains(strValidateData.toUpperCase())) {
                    report.updateTestLog("PayeeAddConfirmation", strValidateHead + " value '" + strValidateData + "' is displayed correctly ", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("PayeeAddConfirmation", strValidateHead + " value '" + strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("PayeeAddConfirmation", "Section element '" + sectionElement + "' is not displayed on Screen", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function: To verify the presence of error message
     * Update on     Updated by     Reason
     * 17/04/2019     C103403      Done code clean up activity
     */
    public void errorMessageValidation() throws Exception {
        String errorMessage[] = dataTable.getData("General_Data", "ErrorText").split("::");
        report.updateTestLog("errorMessageValidation", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        for (int i = 0; i < errorMessage.length; i++) {
            if (isElementDisplayed(getObject("xpath~//*[contains(@class,'boi_input_sm_error')][text()='" + errorMessage[i] + "']"), 5)) {
                report.updateTestLog("errorMessageValidation", "Error Message '" + errorMessage[i] + "' is displayed on Add Payee Page", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("errorMessageValidation", "Error Message '" + errorMessage[i] + "' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function: To Verify the Functional component Error messages for the FORM page
     * Update on     Updated by     Reason
     * 17/04/2019     C103403      Done code clean up activity
     */
    public void FormPageErrorMessageValidation() throws Exception {
        clickJS(getObject("MonthlySaver.btnOpenAccnt") , "Clicking on button :'Open Account'");
        clickJS(getObject("xpath~//span[text()='Continue']"), "Clicking on : Continue  : Button");
        errorMessageValidation();
    }


}

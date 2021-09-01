package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Created by C966003 on 21/12/2018.
 */
public class TransferBetweenAccounts extends WebDriverHelper {

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public TransferBetweenAccounts(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }


    /**
     * Function to launch 'Transfer Between my Accounts' Page
     */
    public void launchTransferBetAccounts() throws Exception {
        HomePage homepage = new HomePage(scriptHelper);
        homepage.selectPaymentOption();
    }

    /**
     * Function to select Account from Pay From dropdown from Transfer Between Account screen
     */
    public void selectAccountPayFrom() throws Exception {
        String strAccount = dataTable.getData("General_Data", "PayFrom_Account");
        String strAccnt = strAccount.substring(strAccount.length()-4);
        if (isElementDisplayed(getObject(deviceType() + "TBA.lblTansBetAccnt"), 10)) {
            if (isElementEnabled(getObject("TBA.lstPayFrom"))) {
                waitForJQueryLoad();waitForPageLoaded();
                if (!isElementclickable(getObject("TBA.lstPayTo"), 5)) {
                    report.updateTestLog("selectAccountPayFrom", " 'PAY TO' drop-down is not editable '", Status_CRAFT.PASS);
                    selectDropDownJS(getObject("TBA.lstPayFrom"), strAccnt);
                } else {
                    report.updateTestLog("selectAccountPayFrom", " 'PAY FROM' drop-down is editable '", Status_CRAFT.FAIL);
                }
            }
        }else{
            report.updateTestLog("selectAccountPayFrom", " 'Transfer between accounts' page is not present '", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to select Account from Pay To dropdown from Transfer Between Account screen
     */
    public void selectAccountPayTo() throws Exception {
        String strAccount[] = dataTable.getData("General_Data", "PayTo_Account").split("~");
        String strAccnt = strAccount[1];
        if (isElementDisplayed(getObject("TBA.lstPayTitle"),5)) {
            report.updateTestLog("selectAccountPayTo", " 'PAY TO' drop-down is editable '", Status_CRAFT.PASS);
            waitForElementToClickable(getObject("TBA.lstPayTo"), 10);
            waitForJQueryLoad();waitForPageLoaded();
            selectDropDownJS(getObject("TBA.lstPayTo"), strAccnt);
            if(isElementDisplayed(getObject("TBA.lblAvailFundTo"),3)){
                String strAvailFund = getText(getObject("TBA.lblAvailFundTo"));
                isElementDisplayedWithLog(getObject("TBA.lblAvailFundTo"), "Available fund : " + strAvailFund, "Transfer between...", 3);
                waitForJQueryLoad();waitForPageLoaded();
            }
            isElementDisplayedWithLog(getObject("TBA.edtPayAmount"), "Amount", "Transfer Between Accounts", 3);
            waitForJQueryLoad();waitForPageLoaded();
            isElementDisplayedWithLog(getObject("TBA.lblProcessTransOption")
                    , "Process Transfer Options", "Transfer Between Accounts", 3);
        } else {
            report.updateTestLog("selectAccountPayTo", " 'PAY TO drop-down is not editable '", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to enter Amount on Transfer Between Account screen
     */
    public void enterAmount() throws Exception {
        String strAmount = dataTable.getData("General_Data", "Pay_Amount");
        if (isElementDisplayed(getObject("TBA.edtPayAmount"), 2)) {
            sendKeys(getObject("TBA.edtPayAmount"), strAmount, "Pay Amount");
        }
    }

    /**
     * Function to select Process Transfer radio button on Transfer Between Account screen
     * Created by : C966003
     * Update on    Updated by     Reason
     * 01/08/2019   C966003        Newly created
     */
    public void selectOptProcessTransfer() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String strPaymentType = dataTable.getData("General_Data", "PaymentType");
        scriptHelper.commonData.strProssTran = dataTable.getData("General_Data", "Process_Transfer");
        scriptHelper.commonData.strSetDate = dataTable.getData("General_Data", "Transfer_Date");
        String strXpath = "xpath~//*[contains(text(),'" + scriptHelper.commonData.strProssTran + "')]";
        scriptHelper.commonData.strTransferDate = getFutureDate(scriptHelper.commonData.strSetDate);
        if (scriptHelper.commonData.strProssTran.equalsIgnoreCase("Schedule future date")) {
            if(isElementDisplayed(getObject(strXpath),4)) {
                clickJS(getObject(strXpath), scriptHelper.commonData.strProssTran);
                while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                    waitForPageLoaded();
                }
                isElementDisplayedWithLog(getObject("TBA.lblSfdInputPageMsg1"),
                        "Schedule future date msg 1 : " + getText(getObject("TBA.lblSfdInputPageMsg1")), "Input page", 5);
                if (strPaymentType.contains("Transfer between my accounts")) {
                    isElementDisplayedWithLog(getObject("TBA.lblSfdInputPageMsg2"),
                            "Schedule future date msg 2 : " + getText(getObject("TBA.lblSfdInputPageMsg2")), "TBA input page", 5);
                } else {
                    isElementDisplayedWithLog(getObject("Payments.lblSfdInputPageMsg2"),
                            "Schedule future date msg 2 : " + getText(getObject("Payments.lblSfdInputPageMsg2")), "Input page", 5);
                }
            }
            homePage.enterDate("TBA.edtTransDate", scriptHelper.commonData.strTransferDate);
        } else {
            clickJS(getObject(strXpath), scriptHelper.commonData.strProssTran);
            report.updateTestLog("selectOptProcessTransfer", "Process Transfer 'Today' option is by default selected", Status_CRAFT.PASS);
        }
        click(getObject("TBA.edtPayAmount")); //* to extract focus from date selected calender
        Thread.sleep(1000);
    }

    /**
     * Function : To select schedule future date from TBA page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 01/08/2019   C966003        Newly created
     */
    public String getFutureDate(String futureDate) throws Exception {
        String date = "";
        String pattern = "dd MMM yyyy";
        LocalDate today = LocalDate.now();
        switch (futureDate) {
            case "Today+1":
                LocalDate Tomorrow = today.plusDays(1);
                date = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(Tomorrow).replace(" ", "/");
                break;
            case "Today+60":
                LocalDate TodayPlus60 = today.plusDays(60);
                date = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(TodayPlus60).replace(" ", "/");
                break;
            case "Today+30":
                LocalDate TodayBetwn60 = today.plusDays(30);
                date = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(TodayBetwn60).replace(" ", "/");
                break;
            default:
                date = futureDate;
        }
        return date;
    }

    /**
     * Function: To validate all error messages related to Schedule future date field
     * Created by : C966003
     * Update on    Updated by     Reason
     * 08/08/2019   C966003        Newly created
     */
    public void errorMsgsScheduleFutureDate() throws Exception {
        scriptHelper.commonData.strProssTran = dataTable.getData("General_Data", "Process_Transfer");
        String strXpath = "xpath~//*[contains(text(),'" + scriptHelper.commonData.strProssTran + "')]";
        String strErrors = dataTable.getData("General_Data", "ErrorText");
        String date = "";
        String pattern = "dd MM yyyy";
        String strErrorMsg = "";
        String[] arrError = strErrors.split(";");
        String[] arrDate = {"Blank", "Today", "Today-1", "Today+61", "Invalid"};
        LocalDate today = LocalDate.now();
        clickJS(getObject(strXpath), scriptHelper.commonData.strProssTran);
        for (int i = 0; i < arrDate.length; i++) {
            if (i == 0) {
                clickContinue_TBA();
                fetchTextToCompare(getObject("TBA.lblSfdErrorMessage"), arrError[i], "Error message for : " + arrDate[i]);
            } else {
                switch (arrDate[i]) {
                    case "Today":
                        date = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(today).replace(" ", "/");
                        break;
                    case "Today-1":
                        LocalDate yesterday = today.minusDays(1);
                        date = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(yesterday).replace(" ", "/");
                        break;
                    case "Today+61":
                        LocalDate TodayPlus61 = today.plusDays(61);
                        date = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(TodayPlus61).replace(" ", "/");
                        break;
                    case "Invalid":
                        date = "32/25/25252";
                        break;
                }
                if ( deviceType()== "mw." |  deviceType()== "tw." ) {
                    clickJS(getObject("TBA.edtSfdBox") , "Click Date picker ");
                    Thread.sleep(1000);
                    //sendKeys working for mobileApp -Rajiv
                    sendKeys(getObject("TBA.edtSfdBox"), date);
                    if(isMobile){
                        clickJS((getObject("xpath~//*[contains(@class,'ui-datepicker-trigger fa fa-calendar')]")),"Date Picker");
                        clickJS((getObject("xpath~//div[contains(@class,'tc-full-width boi_label boi-full-width tc-question-part')]//span[contains(text(),'Process')]/..")),"Process lable");
                        waitForPageLoaded();waitForPageLoaded();
                        ScrollAndClickJS("Sendmoney.lstPayFromAccount");
                        clickJS((getObject("xpath~//*[contains(@class,'ui-datepicker-trigger fa fa-calendar')]")),"Date Picker");
                    }
                    else{
                        try {
                       Actions actions = new Actions(driver.getRemoteWebDriver());
                       actions.moveToElement((driver.findElement(getObject("SendMoney.lblUKDomProcessPayment")))).click().build().perform();
                    } catch (Exception e) {
                        report.updateTestLog("Clicking Process Lable","Error Clicking Process Lable",Status_CRAFT.WARNING);
                        }
                    }
//
                }else{
                    sendKeys(getObject("TBA.edtSfdBox"), date);
                }

                ScrollAndClickJS("Sendmoney.lstPayFromAccount");
                Thread.sleep(2000);
                click(getObject("xpath~//*[text()='Please note']"));
                clickContinue_TBA();
                click(getObject("TBA.edtPayAmount"));
                Thread.sleep(2000);
                fetchTextToCompare(getObject("TBA.lblSfdErrorMessage"), arrError[i], "Error message for : " + arrDate[i]);
                clickJS(getObject(strXpath), scriptHelper.commonData.strProssTran);
            }
        }
    }

    /**
     * Function to verify details on Review page on Transfer Between Account screen
     */
    public void DetailsReviewPage_TBA() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails("TBA.lblReviewDetails");
    }

    /**
     * Function for Pin Validation on Transfer Between Account screen
     */
    public void PIN_TBA() throws Exception {

        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("TBA.edtPin"));
        waitForPageLoaded();
        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                Thread.sleep(2000);
            }
            report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.PASS);
        }
    }

    /**
     * Function for Confirm Button
     */
    public void clickConfirm_TBA() throws Exception {
        if (isElementDisplayed(getObject("TBA.btnConfirm"), 5)) {
            if(isMobile){
                driver.context("NATIVE_APP");
                MobileElement el1 = (MobileElement) driver.findElementByXPath("//android.view.View[3]/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.Button");
                (new TouchAction(driver.getAppiumDriver())).tap(PointOption.point(el1.getCenter().getX(),el1.getCenter().getY())).perform();
                report.updateTestLog("Confirm button", "Confirm button clicked",Status_CRAFT.PASS);
            }
            else{
                clickJS(getObject("TBA.btnConfirm"), "Confirm");
                Thread.sleep(3000);
                waitForPageLoaded();
            }

        }
    }

    /**
     * Function to click on continue button on Transfer Between Account screen
     */
    public void clickContinue_TBA() throws Exception {
        isElementDisplayedWithLog(getObject("TBA.btnContinue"),
                "Continue button", "Transfer between", 5);
        clickJS(getObject("TBA.btnContinue"), "Continue");
    }

    /**
     * Negative Scenario: Function to check if Transfer Between my Accounts does not exists
     */

    public void verifyTBAexists() throws Exception {
        String strPaymentType = dataTable.getData("General_Data", "PaymentType");
        if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 10)) {
            click(getObject(deviceType() + "home.tabPayments"), "Select 'Payments Tab'");
            if (isElementDisplayed(getObject("xpath~//*[text()='" + strPaymentType + "']"), 5)) {
                report.updateTestLog("verifyTBAexists", "'" + strPaymentType + "' Payment type exists on Home Page", Status_CRAFT.FAIL);
                clickJS(getObject("xpath~//*[text()='" + strPaymentType + "']"),"Transfer between account link");
                if(isElementDisplayed(getObject("TBA.txtSelAccntPayFrom"),5)){
                    report.updateTestLog("verifyPayFrom", "Pay From Dropdown is displayed", Status_CRAFT.PASS);
                }
                else {
                    report.updateTestLog("verifyPayFrom", "Pay From Dropdown is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("verifyTBAexists", "'" + strPaymentType + "' Payment type do not exists on Page", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("verifyTBAexists", " Payment tab do not exists on Page", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function for Request Sent or success screen
     */
    public void requestSentValidation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        Thread.sleep(2000);
        homePage.verifyElementDetails("TBA.RequestSent");
    }

    /**
     * Function to Capture Error messages on Transfer Between Account screen
     */
    public void captureErrorMsgOnTBAScreen() throws Exception {
        String strProssTran = dataTable.getData("General_Data", "Process_Transfer");
        String strAvailFundPayFrom = getText(getObject("xpath~(//*[contains(text(),'Available funds')])[1]"));
        double strAvailFund;
        String strXpath = "xpath~//input[contains(@name,'DATEOFPAYMENT')]/following-sibling::label[contains(text(),'" + strProssTran + "')]";
        click(getObject(strXpath));
        Thread.sleep(2000);
        clickContinue_TBA();
        String strErrAmount = getText(getObject("TBA.errAmount"));
        String strErrFutureDate = getText(getObject("TBA.errFutureDate"));
        if (isElementDisplayed(getObject("TBA.errAmount"), 3)) {
            report.updateTestLog("captureErrorMsgOnTBAScreen", "For blank Amount field error message displayed as : " + strErrAmount, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("captureErrorMsgOnTBAScreen", "For blank Amount field error message is not displayed ", Status_CRAFT.FAIL);
        }
        if (strProssTran.equalsIgnoreCase("Select future date")) {
            if (isElementDisplayed(getObject("TBA.errFutureDate"), 3)) {
                report.updateTestLog("captureErrorMsgOnTBAScreen", "For blank Future date field error message displayed as : " + strErrFutureDate, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("captureErrorMsgOnTBAScreen", "For blank Future date field error message is not displayed ", Status_CRAFT.FAIL);
            }
        }
        sendKeys(getObject("TBA.edtPayAmount"), "0.001", "Pay Amount");
        clickContinue_TBA();
        if (isElementDisplayed(getObject("TBA.errAmount"), 3)) {
            report.updateTestLog("captureErrorMsgOnTBAScreen", "For minimum Amount field error message displayed as : " + strErrAmount, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("captureErrorMsgOnTBAScreen", "For minimum Amount field error message is not displayed ", Status_CRAFT.FAIL);
        }
        if (strAvailFundPayFrom.contains("€")) {
            strAvailFund = Double.parseDouble((strAvailFundPayFrom.split("€")[1]).replaceAll(",", "")) + 1.00;
            sendKeys(getObject("TBA.edtPayAmount"), Double.toString(strAvailFund), "Pay Amount");
            clickContinue_TBA();
            String errAmountGreaterAvailfund = getText(getObject("TBA.errMaxAmount"));
            if( isElementDisplayed(getObject("TBA.errMaxAmount"),10)){
                 report.updateTestLog("TBA error amount","TBA error amount is getting displayed",Status_CRAFT.PASS);
            }else {
                 report.updateTestLog("TBA error amount","TBA error amount is getting displayed",Status_CRAFT.FAIL);
            }
        } else if (strAvailFundPayFrom.contains("£")) {
            strAvailFund = Double.parseDouble((strAvailFundPayFrom.split("£")[1]).replaceAll(",", "")) + 1.00;
            sendKeys(getObject("TBA.edtPayAmount"), Double.toString(strAvailFund), "Pay Amount");
            clickContinue_TBA();
            String errAmountGreaterAvailfund = getText(getObject("TBA.errMaxAmount"));
            isElementDisplayedWithLog(getObject("TBA.errMaxAmount"),
                    "Error message for amount greater than Available fund : " + errAmountGreaterAvailfund, "Transfer between account", 5);
        }
    }

    /**
     * Function to Capture Error messages on Transfer Between Account screen
     */
    public void captureErrorPinPage() throws Exception {
        clickConfirm_TBA();
        String strErrorBlankPin = getText(getObject("TBA.errBlankPin"));
        if (isElementDisplayed(getObject("TBA.errBlankPin"), 3)) {
            report.updateTestLog("captureErrorPinPage", "For blank PIN error message displayed as : " + strErrorBlankPin, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("captureErrorPinPage", "For blank PIN error message is not displayed ", Status_CRAFT.FAIL);
        }
    }

    /**
     * Common function to verify details on Transfer Between Account screen
     */
    public void verifyDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String strProssTran = dataTable.getData("General_Data", "Process_Transfer");
        while (isElementDisplayed(getObject("launch.spinSpinner"), 6)) {
            waitForPageLoaded();
        }
        if (scriptHelper.commonData.iterationFlag != true) {
            homePage.verifyElementDetails("TBA.lblReviewDetails");
            if (strProssTran.equalsIgnoreCase("Schedule future date")) {
                isElementDisplayedWithLog(getObject("TBA.lblReviewProcessTransfer"),
                        getText(getObject("TBA.lblReviewProcessTransfer")), "Review", 4);
                isElementDisplayedWithLog(getObject("TBA.lblReviewProcessing"),
                        "Label : " + getText(getObject("TBA.lblReviewProcessing")), "Review", 4);
                isElementDisplayedWithLog(getObject("TBA.lblReviewProcessMessge"),
                        "Processing message : " + getText(getObject("TBA.lblReviewProcessMessge")), "Review", 4);
            }
            scriptHelper.commonData.iterationFlag = true;
        } else {
            if(!"".equalsIgnoreCase(dataTable.getData("General_Data", "VerifyDetails"))){
                homePage.verifyElementDetails("TBA.RequestSent");
            }
            if (strProssTran.equalsIgnoreCase("Schedule future date")) {
                isElementDisplayedWithLog(getObject("TBA.lblReviewScheduleFor"),
                        getText(getObject("TBA.lblReviewScheduleFor")), "Success", 4);
                isElementDisplayedWithLog(getObject("TBA.lblReviewPageMsg"),
                        getText(getObject("TBA.lblReviewPageMsg")), "Success", 4);
                isElementDisplayedWithLog(getObject("TBA.lblReviewTimeOfRequest"),
                        getText(getObject("TBA.lblReviewTimeOfRequest")), "Success", 4);
            }
        }
    }

    public void fromAccountSelection() throws Exception {
        String strAccnt = dataTable.getData("General_Data", "PayFrom_Account");
        if (isElementDisplayed(getObject("TBA.Payform"), 2)) {
            Thread.sleep(1000);
            clickJS(getObject("TBA.Payform"), "pay from");
            Thread.sleep(2000);
            List<WebElement> allLinks = findElements(getObject("TBA.Fromlist"));
            if (allLinks.size() > 1) {
                int intIndex = 1;
                for (WebElement ele : allLinks) {
                    String linkname = ele.getText();
                    if (linkname.contains(strAccnt)) {
                        // Actions action = new Actions((WebDriver) driver.getWebDriver());
                        //action.moveToElement(driver.findElement(getObject("xpath~//ul/li[contains(text(),'" + strAccnt + "')]"))).clickAndHold().build().perform();
                        clickJS(getObject("xpath~//div/ul/li[contains(.,'"+strAccnt+"')]"), "Pay From Account Dropdown");
                        ele.click();
                        intIndex = intIndex;
                        report.updateTestLog("fromAccountSelection", strAccnt + " is clicked  ", Status_CRAFT.PASS);
                        break;
                    }
                    intIndex = intIndex++;
                }
            }
        }
        Thread.sleep(2000);
    }

    public void toAccountSelection() throws Exception {
        String strAccnt = dataTable.getData("General_Data", "PayTo_Account");
        if (isElementDisplayed(getObject("TBA.Payto"), 2)) {
            Thread.sleep(1000);
            click(getObject("TBA.Payto"));
            Thread.sleep(2000);
            List<WebElement> allLinks = findElements(getObject("TBA.Tolist"));
            if (allLinks.size() > 1) {
                int intIndex = 1;
                for (WebElement ele : allLinks) {
                    String linkname = ele.getText();
                    if (linkname.equalsIgnoreCase(strAccnt)) {
                        ele.click();
                        intIndex = intIndex;
                        report.updateTestLog("toAccountSelection", strAccnt + " is clicked  ", Status_CRAFT.PASS);
                        break;
                    }
                    intIndex = intIndex++;
                }
            }
        }
        Thread.sleep(2000);
    }

    public void selectAccountPayFrom1() throws Exception {
        String strAccount[] = dataTable.getData("General_Data", "PayFrom_Account").split("~");
        String strAccnt = strAccount[1];
        if (isElementDisplayed(getObject(deviceType() + "TBA.lblTansBetAccnt"), 5)) {
            if (isElementEnabled(getObject("TBA.lstPayFrom"))) {
                if (!isElementclickable(getObject("TBA.lstPayTo"), 5)) {
                    report.updateTestLog("selectAccountPayFrom", " 'PAY TO' drop-down is not editable '", Status_CRAFT.PASS);
                    clickJS(getObject("xpath~//button[contains(text(),'account to transfer from')]"), "Click on Select Account from which money to send.");
                    Thread.sleep(1000);
                    List<WebElement> arrValidationAccnt = findElements(getObject("xpath~//button[contains(text(),'account to transfer from')]/../ul/li"));
                    String AccountList = dataTable.getData("General_Data", "IBAN_Number");
                    String[] strAccountNumber = AccountList.split("::");
                    Boolean blnindex = false;
                    for (int j = 0; j < strAccountNumber.length; j++) {
                        for (int i = 0; i < arrValidationAccnt.size(); i++) {
                            String strAccntText = arrValidationAccnt.get(i).getText();
                            if (strAccntText.contains(strAccountNumber[j])) {
                                blnindex = true;
                            }
                        }
                        if (blnindex == true) {
                            report.updateTestLog("selectAccountPayFrom", "BlockedToAll/BlockedToDebit/Dormanat/Lien account is displayed in PayFrom dropdown", Status_CRAFT.FAIL);
                            blnindex = false;
                        } else {
                            report.updateTestLog("selectAccountPayFrom", "BlockedToAll/BlockedToDebit/Dormanat/Lien account is not displayed in PayFrom dropdown for IBAN" + j, Status_CRAFT.PASS);
                        }
                    }
                }
                selectDropDownJS(getObject("TBA.lstPayFrom"), strAccnt);
            } else {
                report.updateTestLog("selectAccountPayFrom", " 'PAY TO' drop-down is editable '", Status_CRAFT.FAIL);
            }
        }
    }



}


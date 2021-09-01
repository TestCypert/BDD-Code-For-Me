package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import com.thoughtworks.selenium.webdriven.commands.Click;

/**
 * Created by C966119 on 09/11/2018.
 */
public class PaymentsManagePayees extends WebDriverHelper {

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     * {@link DriverScript}
     */
    // public boolean bBackFlag;
    public static String defaultCountry;
    public static String defaultCurrency;
    public static AppiumDriver Mobiledriver=null;
    public PaymentsManagePayees(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Method to select any options from Payments menu
     *
     * @param : strPaymentType in excel
     *          CFPUR-1672(included)
     */

    /*General Function:
      Scroll and Click on particular  element using JS
     */
    public void ScrollAndClickJS(String linkToClick) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(2000);
            report.updateTestLog("ScrollAndClickJS", "Element To Click", Status_CRAFT.SCREENSHOT);
            JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
            executor.executeScript("arguments[0].click();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(3000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    public void selectPaymentOption() throws Exception {
        String strPaymentType = dataTable.getData("General_Data", "PaymentType");
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 10)) {
                driver.findElement(By.xpath("//*[text()='PAYMENTS']")).click();
                Thread.sleep(2000);
                driver.findElement((By.xpath("//*[text()='" + strPaymentType + "']"))).click();
                report.updateTestLog("selectPaymentOption", "'" + strPaymentType + "' Payment type selected on Home Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPaymentOption", "Payment menu tab not present on Home Page", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject("mw.home.lblFooter_Payments"), 10)) {
                clickJS(getObject("mw.home.lblFooter_Payments"), " 'Payments' Footer");
                waitForElementToClickable(getObject("xpath~//*[text()='" + strPaymentType + "']"), 35);
                clickJS(getObject("xpath~//*[text()='" + strPaymentType + "']"), "Click '" + strPaymentType + "'");
                report.updateTestLog("selectPaymentOption", "'" + strPaymentType + "' Payment type selected on Home Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPaymentOption", "Payment menu tab not present on Home Page", Status_CRAFT.FAIL);
            }
        }

    }

    /**
     * CFPUR-1672
     * Method to add Payee from Manage Payee menu
     */
    public void addPayee() throws Exception {
        String strAddPayeeType = dataTable.getData("General_Data", "AdditionPayeeType");
        waitForPageLoaded();
        Thread.sleep(8000);
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 10)) {
                WebElement elm = driver.findElement(By.xpath("//div[@class='ecDIBCol  ecDIB  ']/div/div[@class='ecDIB  col-full  ']/div/button[@title='" + strAddPayeeType + "']"));
                waitForElementToClickable(getObject("xpath~//div[@class='ecDIBCol  ecDIB  ']/div/div[@class='ecDIB  col-full  ']/div/button[@title='" + strAddPayeeType + "']"), 5);
                elm.click();
                report.updateTestLog("selectAddPayeeOption", "'" + strAddPayeeType + "' Add Payee type selected on Home Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectAddPayeeOption", "Add Payee menu tab not present on Manage Payee Page", Status_CRAFT.FAIL);
            }
        } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject("mw.payments.headerManagePayee"), 5)) {
                //waitForElementToClickable(getObject("xpath~//button[@class='boiManagePayees__addButton boi_label btn-no-padding'][@title='" + strAddPayeeType + "']"), 15);
                clickJS(getObject("xpath~//button[@class='boiManagePayees__addButton boi_label btn-no-padding tc-ripple-effect'][@title='" + strAddPayeeType + "']"), "Add payee Icon");
                report.updateTestLog("selectAddPayeeOption", "'" + strAddPayeeType + "' type selected on Manage Payee Page", Status_CRAFT.PASS);
            } else {
                if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 10)) {
                    WebElement elm = driver.findElement(By.xpath("//buttonfieldset/div/[@title='" + strAddPayeeType + "']"));
                    waitForElementToClickable(getObject("xpath~//buttonfieldset/div/[@title='" + strAddPayeeType + "']"), 5);
                    elm.click();
                    report.updateTestLog("selectAddPayeeOption", "'" + strAddPayeeType + "' Add Payee type selected on Home Page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("selectAddPayeeOption", "Add Payee menu tab not present on Manage Payee Page", Status_CRAFT.FAIL);
                }
            }
        }

    }


    /**
     * Function: Checking the defaulted currency and country combination
     * Update on     Updated by     Reason
     * 20/06/2019     C966119       Done code clean up activity
     */
    public void VerfiyDefaultCountryAndCurrency() throws Exception {
        String strUser = dataTable.getData("General_Data", "Account_Type");
        String strErrorCheck = "";
        String strCurrency = "";
        if (strUser.equals("Ireland")) {
            strCurrency = "EUR - Euro";
        } else if (strUser.equals("United Kingdom")) {
            strCurrency = "GBP - Euro";
        }
        String strDefaultedCountry = "xpath~//button[contains(@class,'boi-rounded-1')][text()='" + strUser + "']";
        if (isElementDisplayed(getObject(strDefaultedCountry), 3)) {
            report.updateTestLog("verifyPaymentCountryCurrency", "User Specific Default Country is as expected :: " + strUser, Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyPaymentCountryCurrency", "The Pay To Country is not Defaulted :: " + strUser, Status_CRAFT.FAIL);
        }

        String strValueDefaulted = "xpath~//span[@class='boi_input'][contains(text(),'" + strCurrency + "')]";
        if (isElementDisplayed(getObject(strValueDefaulted), 3)) {
            report.updateTestLog("verifyPaymentCountryCurrency", "Defaulted Currency :: EUR - Euro :: for country : Ireland", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyPaymentCountryCurrency", "Defaulted Currency is NOT displayed correctly :: EUR - Euro :: for country : Ireland ", Status_CRAFT.FAIL);
        }

        //Check the error : When fields are blank
        if (strErrorCheck.length() > 0) {
            clickJS(getObject("AddPayee.Continue"), "Add Payee : Continue button is clicked successfully..!!");
            Thread.sleep(3000);
            //errorMessageValidation();
        }
    }

    /**
     * Function: Enter Payee Details
     * Update on     Updated by     Reason
     * 20/06/2019     C966119       Done code clean up activity
     */
    public void EnterPayeeDetailsOnBackNavigation() throws Exception {
        String strPayeeDetails = dataTable.getData("General_Data", "Current_Balance");
        String[] arrDetails = strPayeeDetails.split("::");
        String strName = arrDetails[0];
        String strIBAN = arrDetails[1];
        String strBIC = arrDetails[2];
        String strMessageToPayee = arrDetails[3];
        String strDescription = arrDetails[4];
        Thread.sleep(4000);
        clickJS(getObject("AddPayee.GoBack"), "Click Go back button");
        Thread.sleep(4000);
        AddSEPAPayeeSelectCountry();
        click(getObject("AddPayee.Name"));
        sendKeys(getObject("AddPayee.Name"), strName);
        click(getObject("AddPayee.IBAN"));
        sendKeys(getObject("AddPayee.IBAN"), strIBAN);
        click(getObject("AddPayee.BIC"));
        sendKeys(getObject("AddPayee.BIC"), strBIC);
        click(getObject("AddPayee.Message"));
        sendKeys(getObject("AddPayee.Message"), strMessageToPayee);
        click(getObject("AddPayee.Description"));
        sendKeys(getObject("AddPayee.Description"), strDescription);
        click(getObject("AddPayee.Continue"));
        report.updateTestLog("AddPayeeDetails", "After Back Navigation Through button 'Go Back'...Add Payee Details filled Successfully..!!", Status_CRAFT.DONE);
    }

    /**
     * Function: Enter Payee Details
     * Update on     Updated by     Reason
     * 20/06/2019     C966119       Done code clean up activity
     */
    public void EnterPayeeDetails() throws Exception {
        Thread.sleep(2000);
        String Name = dataTable.getData("General_Data", "FirstName");
        String IBAN = dataTable.getData("General_Data", "IBAN_Number");
        String BIC = dataTable.getData("General_Data", "BIC_Number");
        String messageToPayee = dataTable.getData("General_Data", "ReferenceNumber");
        String description = dataTable.getData("General_Data", "Nickname");
        if(deviceType.equalsIgnoreCase("Web")){
            click(getObject("AddPayee.Name"), "Name Textbox");
            sendKeys(getObject("AddPayee.Name"), Name, "Name");
            click(getObject("AddPayee.IBAN"), "IBAN Textbox");
            sendKeys(getObject("AddPayee.IBAN"), IBAN, "IBAN");
            click(getObject("AddPayee.BIC"), "BIC Textbox");
            sendKeys(getObject("AddPayee.BIC"), BIC, "BIC");
            click(getObject("AddPayee.Message"), "Reference Textbox");
            sendKeys(getObject("AddPayee.Message"), messageToPayee, "Reference");
            click(getObject("AddPayee.Description"), "Description Textbox");
            sendKeys(getObject("AddPayee.Description"), description, "Description");
            Thread.sleep(1000);
            click(getObject("AddPayee.Continue"), "Clicked on Add Payee button");
        }else{
            clickJS(getObject("AddPayee.Name"), "Name Textbox");
            sendKeys(getObject("AddPayee.Name"), Name, "Name");
            clickJS(getObject("AddPayee.IBAN"), "IBAN Textbox");
            sendKeysJS(getObject("AddPayee.IBAN"), IBAN, "IBAN");
            clickJS(getObject("AddPayee.BIC"), "BIC Textbox");
            sendKeysJS(getObject("AddPayee.BIC"), BIC, "BIC");
            clickJS(getObject("AddPayee.Message"), "Reference Textbox");
            sendKeysJS(getObject("AddPayee.Message"), messageToPayee, "Reference");
            clickJS(getObject("AddPayee.Description"), "Description Textbox");
            sendKeysJS(getObject("AddPayee.Description"), description, "Description");
            Thread.sleep(1000);
            clickJS(getObject("AddPayee.Continue"), "Clicked on Add Payee button");}

        //Check the MF error
        if (isElementDisplayed(getObject("AddPayee.txtMfErrorMsg"), 5)) {
            String strErrorMesg = getText(getObject("AddPayee.txtMfErrorMsg"));
            report.updateTestLog("addPayeeDetails", "MF Error Message :: '" + strErrorMesg + "' is displayed...Which is not expected while adding Pay..!!", Status_CRAFT.FAIL);
            return;
        }
    }

    public void AddPayeeToolTip() throws Exception {
        Actions action = new Actions((WebDriver) driver.getWebDriver());
        action.moveToElement(driver.findElement(getObject("AddPayee.tooltipIconIBAN"))).clickAndHold().build().perform();
        String strExpectedToolTip = "The International Bank Account Number of the payee quoted in international format that identifies the individual account as being in a specific financial institution in a particular country.";
        String strUIToolTip = getText(getObject("AddPayee.tooltipIBAN"));

        if (strUIToolTip.equals(strExpectedToolTip)) {
            report.updateTestLog("enterPayeeDetails", "IBAN tooltip is correctly displayed on Add Payee Page, Expected '" + strExpectedToolTip + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetails", "IBAN tooltip is NOT correctly displayed on Add Payee Page, Actual '" + strUIToolTip + "'", Status_CRAFT.FAIL);
        }

        action.moveToElement(driver.findElement(getObject("AddPayee.tooltipIconBIC"))).clickAndHold().build().perform();
        String strExpectedToolTip1 = "Bank Identification Code (BIC) is the SWIFT address assigned to the payee’s bank. A BIC consists of eight or eleven characters. If you choose not to provide a BIC, we will use the IBAN provided by you to determine it.";
        String strUIToolTip1 = getText(getObject("AddPayee.tooltipBIC"));

        if (strUIToolTip1.equals(strExpectedToolTip1)) {
            report.updateTestLog("enterPayeeDetails", "BIC tooltip is correctly displayed on Add Payee Page, Expected '" + strExpectedToolTip1 + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetails", "BIC tooltip is NOT correctly displayed on Add Payee Page, Actual '" + strUIToolTip1 + "'", Status_CRAFT.FAIL);
        }

        action.moveToElement(driver.findElement(getObject("AddPayee.tooltipIconReference"))).clickAndHold().build().perform();
        String strExpectedToolTip2 = "You can use letters and numbers but when adding a credit card, only enter the 16 digit card number.";
        String strUIToolTip2 = getText(getObject("AddPayee.tooltipReference"));

        if (strUIToolTip2.equals(strExpectedToolTip2)) {
            report.updateTestLog("enterPayeeDetails", "Reference tooltip is correctly displayed on Add Payee Page, Expected '" + strExpectedToolTip2 + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetails", "Reference tooltip is NOT correctly displayed on Add Payee Page, Actual '" + strUIToolTip2 + "'", Status_CRAFT.FAIL);
        }

        action.moveToElement(driver.findElement(getObject("AddPayee.tooltipIconDescription"))).clickAndHold().build().perform();
        String strExpectedToolTip3 = "A keyword or phrase that helps you recognise this payee in future.";
        String strUIToolTip3 = getText(getObject("AddPayee.tooltipDescription"));

        if (strUIToolTip3.equals(strExpectedToolTip3)) {
            report.updateTestLog("enterPayeeDetails", "Reference tooltip is correctly displayed on Add Payee Page, Expected '" + strExpectedToolTip3 + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetails", "Reference tooltip is NOT correctly displayed on Add Payee Page, Actual '" + strUIToolTip3 + "'", Status_CRAFT.FAIL);
        }

    }


    /**
     * Function: Adding Payee Details
     * Update on     Updated by     Reason
     * 20/06/2019     C966119       Done code clean up activity
     */
    public void addPayeeDetails() throws Exception {
        String strErrorCheck = dataTable.getData("General_Data", "ErrorText");
        String strOperation = dataTable.getData("General_Data", "Operation");

        //VerfiyDefaultCountryAndCurrency();
        //Check the error : When fields are blank
        if (strErrorCheck.length() > 0) {
            clickJS(getObject("AddPayee.Continue"), "Add Payee : Continue button is clicked successfully..!!");
            Thread.sleep(3000);
        }

        selectPayeeCountryAndCurrency();
        if ((dataTable.getData("General_Data", "JurisdictionType").equals("ROI"))) {
            EnterPayeeDetails();
            if (strOperation.equalsIgnoreCase("Continue")) {
                waitForElementToClickable(getObject("AddPayee.Continue") , 10);
                ScrollToVisibleJS("AddPayee.Continue");
                clickJS(getObject("AddPayee.Continue") , "Clicked on Add Payee button");
            }
        }else {
            addUKDomPayeeDetails();
        }
//      enterPayeeDetails();
        //clickJS(getObject("AddPayee.Continue") , "Clicked on Add Payee button");
        //Check the MF error
        if (isElementDisplayed(getObject("AddPayee.txtMfErrorMsg"), 5)) {
            String strErrorMesg = getText(getObject("AddPayee.txtMfErrorMsg"));
            report.updateTestLog("addPayeeDetails", "MF Error Message :: '" + strErrorMesg + "' is displayed...Which is not expected while adding Pay..!!", Status_CRAFT.FAIL);
            return;
        }
        if (strOperation.equals("BackNavigation")) {
            EnterPayeeDetailsOnBackNavigation();
        }
    }


    public void verifySEPApayeeFormDetail() throws Exception {
        String strErrorCheck = dataTable.getData("General_Data", "ErrorText");

        //Payee Name
        if (isElementDisplayed(getObject("AddPayee.hdrPayeeDetails"), 5)) {
            report.updateTestLog("verifySEPApayeeFormDetails", "Label 'Name' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeeName = dataTable.getData("General_Data", "FirstName");
            if (!strPayeeName.equals("")) {
                sendKeys(getObject("AddPayee.txtName"), strPayeeName, "Name entered is '" + strPayeeName + "'");
            }
        } else {
            report.updateTestLog("verifySEPApayeeFormDetails", "Label 'Name' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //IBAN
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankIBAN"), 5)) {
            report.updateTestLog("verifySEPApayeeFormDetails", "Label 'IBAN' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String IBAN = dataTable.getData("General_Data", "IBAN_Number");
            if (!IBAN.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankIBAN"), IBAN, "IBAN entered is '" + IBAN + "'");
            }
        } else {
            report.updateTestLog("verifySEPApayeeFormDetails", "Label 'IBAN' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //BIC
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankBIC"), 5)) {
            report.updateTestLog("verifySEPApayeeFormDetails", "Label 'BIC' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String BIC = dataTable.getData("General_Data", "BIC_Number");
            if (!BIC.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankBic"), BIC, "BIC entered is '" + BIC + "'");
            }
        } else {
            report.updateTestLog("verifySEPApayeeFormDetails", "Label 'BIC' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //Reference
        if (isElementDisplayed(getObject("AddPayee.lblReference"), 5)) {
            report.updateTestLog("verifySEPApayeeFormDetails", "Label 'REFERNECE' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String messageToPayee = dataTable.getData("General_Data", "ReferenceNumber");
            if (!messageToPayee.equals("")) {
                sendKeys(getObject("AddPayee.txtReference"), messageToPayee, "Reference entered is '" + messageToPayee + "'");
            }
        } else {
            report.updateTestLog("verifySEPApayeeFormDetails", "Label 'Reference' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //Description
        if (isElementDisplayed(getObject("Addpayee.lblDescription"), 5)) {
            report.updateTestLog("verifySEPApayeeFormDetails", "Label 'DESCRIPTION' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String description = dataTable.getData("General_Data", "Nickname");
            if (!description.equals("")) {
                sendKeys(getObject("Addpayee.txtDescription"), description, "DESCRIPTION entered is '" + description + "'");
            }
        } else {
            report.updateTestLog("verifySEPApayeeFormDetails", "Label 'BIC' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        clickJS(getObject("AddPayee.Continue"), "Continue on Add Payee Page");


    }

    public void errorValidation() throws Exception {
        String ExpError = "The account entered is already registered as a payee.";
        String ActualError = getText(getObject("AddPayee.duplicatePayeeError"));
        if (ExpError.equalsIgnoreCase(ActualError)) {
            report.updateTestLog("verifyError", "Error '" + ExpError + "' is displayed successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyError", "Error '" + ExpError + "' is not displayed successfully", Status_CRAFT.FAIL);
        }
    }

    /**
     * To select the currency for the particular country : CFPUR-1781
     */
    public void AddSEPAPayeeSelectCountry() throws Exception {
        String strCountryToSelect = dataTable.getData("General_Data", "Available_Balance");
        String strCurrencyToSelect = dataTable.getData("General_Data", "Currency_Symbol");
        String strUser = dataTable.getData("General_Data", "Account_Type");

        scrollToView(getObject("AddPayee.lblCountryAndCurr"));
        String strDefaultedCountry = "xpath~//button[contains(@class,'boi-rounded-1')][text()='" + strUser + "']";
        clickJS(getObject(strDefaultedCountry), "Selecting the Country ::" + strCountryToSelect);
        // To Select Country
        //String strCountryName = "xpath~//div[contains(@class,'tc-rounded-1')]/descendant::li[contains(@class,'option')][text()='" + strCountryToSelect + "']";
        //clickJS(getObject("Payments.lstSelectCountry"), "Selecting the Country ::" + strCountryName);
        Thread.sleep(2000);
        String strCountryName = "xpath~//ul/li[@role='option'][contains(.,'" + strCountryToSelect + "')]";
        clickJS(getObject(strCountryName), strCountryToSelect + " :: Country Selected");
        Thread.sleep(3000);

        //Selecting Currency
        String strValueDefaulted = "xpath~//span[@class='boi_input'][contains(text(),'" + strCurrencyToSelect + "')]";
        if (isElementDisplayed(getObject(strValueDefaulted), 5)) {
            report.updateTestLog("verifyPaymentCountryCurrency", "Defaulted Currency :: EUR - Euro :: for country : " + strCountryName, Status_CRAFT.DONE);
        } else {
            String strDropDownValue = "xpath~(//div[contains(@class,'tc-rounded-1')])[2]/descendant::li[contains(@class,'option')][contains(text(),'" + strCurrencyToSelect + "')]";
            clickJS(getObject("Payments.lstSelectCurrency"), "Selecting the Currency ::" + strCurrencyToSelect);
            Thread.sleep(2000);
            clickJS(getObject(strDropDownValue), "Currency Selected");
            Thread.sleep(2000);
        }

    }

    public void clickDeletebutton() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "AddPayee.delete"), 3)) {
            click(getObject((deviceType() + "AddPayee.delete")));
            report.updateTestLog("verifyDeletebutton", "Delete is clicked", Status_CRAFT.PASS);
            Thread.sleep(2000);
        }

        if ((isElementDisplayed(getObject("Addpayee.deletepayee"), 10))) {
            clickJS(getObject("Addpayee.deletepayee"), "Delete Payee");
            Thread.sleep(3000);
        }

    }
    public void clicksendmoney() throws Exception {
        if (isElementDisplayed(getObject("SendMoney.iconSendMoney"), 5)) {
            report.updateTestLog("NickNameOnPages", "Menu Tab 'Payments' is displayed", Status_CRAFT.PASS);
            click(getObject("SendMoney.iconSendMoney"), "Click on send money'Send money or pay a bill'");
        } else {
            report.updateTestLog("NickNameOnPages", " Tab 'Send money or pay a bill' is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    /**
     * To verify the currency for the particular country : CFPUR-1781
     */
    public void verifyPaymentCountryCurrency() throws Exception {

        //String strCurrencyType = dataTable.getData("General_Data", "VerifyContent");
        String strCountry = dataTable.getData("General_Data", "VerifyDetails");
        String[] arrValidationCountry = strCountry.split(",");

        // Navigation from Mobile and Desktop app
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            click(getObject("mw.home.lblFooter_Payments"));
            click(getObject("Payments.btnManagePayee"));
        } else {
            selectPaymentOption();
        }
//        clickJS(getObject("Payments.btnAddPayees"));
        clickJS(getObject("Payments.btnAddPayees"), "Add person");
        Thread.sleep(4000);

        // To Check the Defaulted Country for particular user : ROI - Ireland , NI/GB : United Kingdom
        String strJurisdiction = dataTable.getData("General_Data", "Account_Type");
        String strDefaultedCountry = "xpath~//div[(@class = 'aria_exp_wrapper ')]/button[contains(@class,'exp_button')][text()='" + strJurisdiction + "']";

        if (isElementDisplayed(getObject(strDefaultedCountry), 3)) {
            report.updateTestLog("verifyPaymentCountryCurrency", "User Specific Default Country :: " + strJurisdiction, Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyPaymentCountryCurrency", "The Pay To Country is not Defaulted :: " + strJurisdiction, Status_CRAFT.FAIL);
        }

        //To Validate Country List from the dropdown only for particular profile Customer
        for (int i = 0; i < arrValidationCountry.length; i++) {
//            String strDropDownValue = "xpath~//div[contains(@class,'tc-rounded-1')]/descendant::li[contains(@class,'option')][text()='" + arrValidationCountry[i] + "']";
            String strDropDownValue = "xpath~//*[@class='aria_exp_wrapper ']/ul/li[contains(text(),'" + arrValidationCountry[i] + "')]";
            //String strValue1 = "xpath~//div[contains(@class,'tc-rounded-1')]/descendant::li[contains(@class,'option')][@data-value>0]";
            WebElement oItems = driver.findElement(getObject(strDropDownValue));
            String intIndex = oItems.getAttribute("data-value");
            if (intIndex.equals(Integer.toString(i + 1))) {
                report.updateTestLog("verifyPaymentCountryCurrency", "Country ::" + arrValidationCountry[i] + " - " + intIndex, Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyPaymentCountryCurrency", "The Pay To Country Drop Down Does NOT Contains Value at index :: " + intIndex, Status_CRAFT.FAIL);
            }
        }

        // Selecting Particular country
        for (int j = 0; j < arrValidationCountry.length; j++) {
            boolean blnFlagSelected = false;
            String strCountryName = "xpath~//div[@class='aria_exp_wrapper ']/ul/li[contains(text(),'" + arrValidationCountry[j] + "')]";
            //Session time out pop up
            if (isElementDisplayed(getObject(deviceType() + "home.lbltimeoutPopOut"), 3)) {
                click(getObject(deviceType() + "home.lbltimeoutPopOut"));
                waitForPageLoaded();
                Thread.sleep(4000);
            }
            click(getObject("Payments.lstSelectCountry"));

            click(getObject(strCountryName));
            report.updateTestLog("verifyPaymentCountryCurrency", ":: Index :: " + (j + 1), Status_CRAFT.DONE);
            String strSelectedCountry = "xpath~//div[@class='aria_exp_wrapper ']/ul/li[contains(text(),'" + arrValidationCountry[j] + "')]";
            if (isElementDisplayed(getObject(strSelectedCountry), 3)) {
                blnFlagSelected = true;
                report.updateTestLog("verifyPaymentCountryCurrency", "Country :: " + arrValidationCountry[j], Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyPaymentCountryCurrency", "The Pay To Country NOT Selected :: " + arrValidationCountry[j], Status_CRAFT.FAIL);
            }

            //Check Currency only if Country is selected
            if (blnFlagSelected == true) {
                // Validate the currency options for selected country
                String[] arrValidationCurrency = dataTable.getData("General_Data", "VerifyContent").split(",");
                String[] arrValidationCurrencyValue = arrValidationCurrency[j].split("/");
                int intNoOfCurrency = arrValidationCurrencyValue.length;

                //Session time out pop up
                if (isElementDisplayed(getObject(deviceType() + "home.lbltimeoutPopOut"), 3)) {
                    click(getObject(deviceType() + "home.lbltimeoutPopOut"));
                    waitForPageLoaded();
                    Thread.sleep(4000);
                }
                if (intNoOfCurrency == 1) {
                    String strValueDefaulted = "xpath~//span[@class='boi_input'][contains(text(),'" + arrValidationCurrencyValue[0].trim() + " - ')]";
                    if (isElementDisplayed(getObject(strValueDefaulted), 3)) {
                        report.updateTestLog("verifyPaymentCountryCurrency", "Defaulted Currency :: " + arrValidationCurrencyValue[0].trim(), Status_CRAFT.DONE);
                    } else {
                        report.updateTestLog("verifyPaymentCountryCurrency", "Defaulted Currency is NOT displayed correctly :: " + arrValidationCurrencyValue[0].trim(), Status_CRAFT.FAIL);
                    }

                } else {
                    for (int k = 0; k < arrValidationCurrencyValue.length; k++) {
                        if (k == 0) {
                            String strDefaultValue = "xpath~//div[@class='aria_exp_wrapper ']/button[contains(@class,'nice-select')][contains(text(),'" + arrValidationCurrencyValue[0].trim() + " - ')]";
                            Thread.sleep(4000);
                            if (isElementDisplayed(getObject(strDefaultValue), 4)) {
                                report.updateTestLog("verifyPaymentCountryCurrency", "Defaulted Currency :: " + arrValidationCurrencyValue[0].trim(), Status_CRAFT.DONE);
                                report.updateTestLog("verifyPaymentCountryCurrency", "Index :: " + (k + 1) + " :: Currency :: " + arrValidationCurrencyValue[0].trim(), Status_CRAFT.DONE);
                            } else {
                                report.updateTestLog("verifyPaymentCountryCurrency", "Defaulted Currency is NOT displayed correctly :: " + arrValidationCurrencyValue[0].trim(), Status_CRAFT.FAIL);
                            }
                        } else {
                            String strDropDownValue = "xpath~//div[@class='aria_exp_wrapper ']/ul/li[contains(text(),'" + arrValidationCurrencyValue[k].trim() + " - ')]";

                            click(getObject("Payments.lstSelectCurrency"));
                            Thread.sleep(2000);
                            click(getObject(strDropDownValue));
                            String strNewDefaultValue = "xpath~//div[@class='aria_exp_wrapper ']/button[contains(@class,'nice-select')][contains(text(),'" + arrValidationCurrencyValue[k].trim() + " - ')]";
                            if (isElementDisplayed(getObject(strNewDefaultValue), 3)) {
                                Thread.sleep(2000);
                                report.updateTestLog("verifyPaymentCountryCurrency", (k + 1) + " :: Currency :: " + arrValidationCurrencyValue[k].trim(), Status_CRAFT.DONE);
                            } else {
                                report.updateTestLog("verifyPaymentCountryCurrency", (k + 1) + " :: Currency Drop Down Does Not Have Value :: " + arrValidationCurrencyValue[k].trim(), Status_CRAFT.FAIL);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Function to mobile pin digits
     */
    public void enterRequiredPin() throws Exception {
        if(isMobile){
            String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
            List<WebElement> lstPinEnterFieldGrp = findElements(getObject("AddPayee.PINFields"));
            //Entering values for only enabled fields
            for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
                if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                    lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                }
            }
            report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.PASS);

            SCA_MobileAPP sc = new SCA_MobileAPP(scriptHelper);
            sc.changeWebviewToNative();

            if(isIOS){
                clickButtonOrLinkIOS("Confirm");
            }else{
                MobileElement el1 = (MobileElement) driver.findElementByXPath("//android.view.View[3]/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.Button");
                (new TouchAction(driver.getAppiumDriver())).tap(PointOption.point(el1.getCenter().getX(),el1.getCenter().getY())).perform();
            }
        }
        else{
            //invoke app
            SCA_MobileAPP sca=new SCA_MobileAPP(scriptHelper);
//            Mobiledriver=sca.MobileAppPushNotification("ANDROID");
            //on browser
            String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
            List<WebElement> lstPinEnterFieldGrp = findElements(getObject("AddPayee.PINFields"));
            //Entering values for only enabled fields
            for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
                if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                    lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                }
            }
            report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.PASS);
            clickJS(getObject("AddPayee.Confirm"), "Confirm");
            Thread.sleep(3000);
//            if(isIOS){
//                clickButtonOrLinkIOS("Confirm");
//            }else{
//                MobileElement el1 = (MobileElement) driver.findElementByXPath("//android.view.View[3]/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.Button");
//                (new TouchAction(driver.getAppiumDriver())).tap(PointOption.point(el1.getCenter().getX(),el1.getCenter().getY())).perform();
//            }
            //APP- accept
//            sca.PushNotification_Acccept(Mobiledriver);

        }

    }

    /**
     * Function to validate the payee is added
     */
    public void PayeeAddConfirmation(String sectionElement) throws Exception {
       waitForJQueryLoad();waitForPageLoaded();
        String[] arrValidation = dataTable.getData("General_Data", "VerifyContent").split(";");
        if (isElementDisplayed(getObject(sectionElement), 15)) {
            String dataSectionUI = getText(getObject(sectionElement)).toUpperCase();
            report.updateTestLog("PayeeAddConfirmation", "Message displayed is " + dataSectionUI, Status_CRAFT.PASS);
            report.updateTestLog("PayeeAddConfirmation", "Payee Add confirmation page is displayed successfully", Status_CRAFT.PASS);
            if(deviceType.equalsIgnoreCase("Web")) {
                if (isElementDisplayed(getObject(deviceType()+"home.Goback"), 1)) {
                    report.updateTestLog("VerifyAddPayee", "Go Back link is present on header which is as expected", Status_CRAFT.PASS);
                }else {
                    report.updateTestLog("VerifyAddPayee", "Go Back link is not present on header which is NOT as expected", Status_CRAFT.FAIL);
                }
            } else if (isElementDisplayed(getObject(deviceType()+"home.Goback"), 1)) {
                report.updateTestLog("VerifyAddPayee", "Go Back link is present on header which is NOT as expected", Status_CRAFT.FAIL);
            }else {
                report.updateTestLog("VerifyAddPayee", "Go Back link is not present on header which is as expected", Status_CRAFT.PASS);
            }


            if (isElementDisplayed(getObject(deviceType() +"ManagePayee.profileIcon"), 1)) {
                report.updateTestLog("VerifyAddPayee", "Profile Icon is present on header", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyAddPayee", "Profile Icon is not present on header", Status_CRAFT.FAIL);
            }
            clickJS(getObject("AddPayee.btnBackToHome"), "Back to home");
        } else {
            report.updateTestLog("PayeeAddConfirmation", "Section element '" + sectionElement + "' is not displayed on Screen", Status_CRAFT.FAIL);
        }
    }

    /*General Function:
     Scroll to view particular  element using JS
    */
    public void ScrollToVisibleJS(String linkToVisible) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToVisible))));
            Thread.sleep(2000);
            //report.updateTestLog("ScrollAndClickJS", "Element To View", Status_CRAFT.SCREENSHOT);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }

    }


    public void verifyAddPayeeCrossJuridication() throws Exception {
        String accountToChoose = dataTable.getData("General_Data", "Account_Name");
        //label for the Cross
        if (isElementDisplayed(getObject(deviceType() + "AddPayee.lblCross"), 3)) {
            report.updateTestLog("verifyAddPayeeCrossJuridication", " Label for Cross Juricition Add payee is  displayed correctly ", Status_CRAFT.DONE);
            if (getText(getObject(deviceType() + "AddPayee.lblCross")).equals("The countries/currencies available to you when adding a new payee can differ between your Republic of Ireland and UK payment accounts.")) {
                report.updateTestLog("verifyAddPayeeCrossJuridication", " Label text for Cross Juricition Add payee is  displayed correctly ", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddPayeeCrossJuridication", " Label text for Cross Juricition Add payee is not displayed correctly ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAddPayeeCrossJuridication", " Page for Cross Juricition Add payee is not displayed correctly ", Status_CRAFT.FAIL);
        }

        //Selecting the account
        clickJS(getObject("xpath~//div[contains(@class,'aria_exp_wrapper')]/button"), "Please Select option Selected");
        List<WebElement> account = findElements(getObject("xpath~//ul[@role='listbox']/li"));
        int index = 0;
        boolean bflag = false;
        for (int i = 0; i < account.size(); i++) {
            String strAccntText = account.get(i).getText();
            if (strAccntText.contains(accountToChoose)) {
                index = i;
                bflag=true;
            }
        }
        if(bflag=true){
            clickJS(getObject("xpath~//ul[@role='listbox']/descendant::li[@data-value='" + index + "']"), "Account Selected");
            report.updateTestLog("Account DropDown", " Account DropDown is  displayed correctly ", Status_CRAFT.PASS);
            clickJS(getObject("SendMoney.btnContinue"), "Continue");
        }
        else {
            report.updateTestLog("Account DropDown", "Account DropDown is NOT present in the list.", Status_CRAFT.FAIL);
        }
    }


    /*CFPUR-90*/
    public void verifyDirectDebitPage() throws Exception {
        HomePage home = new HomePage(scriptHelper);
        home.verifyElementDetails("xpath~(//div[contains(@class,'tc-card-bg shadow-style-1 boi-dd-card tc-card')])[1]");
    }

    /**
     * CFPUR-2800 : Payment : Happy Path for ROI User only
     */
    public void VerifyMessageToPayee() throws Exception {
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");
        if (isElementDisplayed(getObject("SendMoney.txtMsgToPayee"), 5)) {
            scrollToView(getObject("SendMoney.txtMsgToPayee"));
            //clickJS(getObject("SendMoney.txtMsgToPayee"), "Click Message to Payee.");
            sendKeys(getObject("SendMoney.txtMsgToPayee"), strMsgToPayee);
            Thread.sleep(1000);
            driver.findElement(getObject("SendMoney.txtMsgToPayee")).sendKeys(Keys.TAB);
        }
    }

    /**
     * CFPUR-2800 : Payment : Happy Path for ROI User only
     */
    public void SendMoneyFormHappyPath() throws Exception {
        String strAccountNumber = dataTable.getData("General_Data", "Account_Name");
        String strPayee = dataTable.getData("General_Data", "Nickname");
        String strPayeeIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strPayeeBIC = dataTable.getData("General_Data", "BIC_Number");
        String strAmountToSend = dataTable.getData("General_Data", "Current_Balance");
        String strRefrenceToSend = dataTable.getData("General_Data", "SequenceAccType");
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");
        String strBackNavigation = dataTable.getData("General_Data", "Operation");
        String strErrorCheckForFields = dataTable.getData("General_Data", "Account_Name");

        //Navigate to till Send Money Page From Home Page
        if (deviceType.equalsIgnoreCase("Web")) {
            //waitForJQueryLoad();
            clickJS(getObject("xpath~//*[contains(@class,'menuAccounts')][text()='PAYMENTS']"), "Payments");
            waitForJQueryLoad();
        } else {
            waitForJQueryLoad();waitForPageLoaded();
            clickJS(getObject("mw.home.lblFooter_Payments"), " 'Payments' Footer");
            waitForJQueryLoad();waitForPageLoaded();
        }


        //Check the icon send money
        report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        if (isElementDisplayed(getObject("SendMoney.iconSendMoney"), 3)) {
            report.updateTestLog("SendMoneyFormHappyPath", " Send Money icon is visible.", Status_CRAFT.DONE);
            clickJS(getObject("SendMoney.iconSendMoney"), "Send Money");
            waitForJQueryLoad();waitForPageLoaded();
        } else {
            report.updateTestLog("SendMoneyFormHappyPath", " Send Money icon is not visible..!!", Status_CRAFT.FAIL);
        }

        // Validate Send Money Page
        if (isElementDisplayed(getObject("SendMoney.lstSelectAccount"), 3)) {
            clickJS(getObject("SendMoney.lstSelectAccount"), "Click on Select Account from which money to send.");
            waitForJQueryLoad();waitForPageLoaded();
            List<WebElement> arrValidationAccnt = findElements(getObject("xpath~//li[contains(@class,'option')]"));
            boolean bfound = false;
            for (int i = 0; i < arrValidationAccnt.size(); i++) {
                String strAccntText = arrValidationAccnt.get(i).getText();
                if (strAccntText.toUpperCase().contains(strAccountNumber.toUpperCase())) {
                    bfound=true;
                    arrValidationAccnt.get(i).click();
                    report.updateTestLog("SendMoneyFormHappyPath", "The Account ::" + strAccountNumber + ":: is selected successfully.", Status_CRAFT.PASS);
                    break;
                }
            }
            if(!bfound){report.updateTestLog("SendMoneyFormHappyPath", "The Account ::" + strAccountNumber + ":: is not present in the list.", Status_CRAFT.FAIL);}
            waitForJQueryLoad();waitForJQueryLoad();

        }
        waitforInVisibilityOfElementLocated(getObject("SendMoney.availableBalText"));

        // Select Payee to send money
        if (isElementDisplayed(getObject("SendMoney.lstSelectPayee"), 10)) {
            report.updateTestLog("SendMoneyFormHappyPath", " Once Send from account is selected successfully and then only Select Payee option is available", Status_CRAFT.DONE);
            clickJS(getObject("SendMoney.lstSelectPayee"), "Select Payee");
            waitForJQueryLoad();waitForPageLoaded();
            List<WebElement> arrValidationPayee = findElements(getObject("xpath~//button[contains(text(),'Choose a payee or bill')]/following-sibling::ul/li[contains(@class,'option')]"));
            boolean bPayeeFound = false;
            for (int i = 1; i < arrValidationPayee.size(); i++) {
                String strPayeeText = arrValidationPayee.get(i).getText();
                if (strPayeeText.toUpperCase().contains(strPayee.toUpperCase())) {
                    arrValidationPayee.get(i).click();
                    report.updateTestLog("SendMoneyFormHappyPath", "Payee ::" + strPayee + ":: is selected successfully.", Status_CRAFT.PASS);
                    bPayeeFound=true;
                    break;
                }
            }
            if(!bPayeeFound){report.updateTestLog("SendMoneyFormHappyPath", "Payee ::" + strPayee + ":: is not present in the Payee list.", Status_CRAFT.FAIL);}
            waitForJQueryLoad();waitForPageLoaded();
        }



        // Enter and verify the amount field
        if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 1)) {
            clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send.");
            sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmountToSend);
        } else {
            report.updateTestLog("SendMoneyFormHappyPath", "Amount field or amount filed place holder is not displayed correctly.", Status_CRAFT.FAIL);
        }

        // Enter reference field
        if(isElementDisplayed(getObject("xpath~//a/*[contains(text(),'Edit')]"),3)){
           click(getObject("xpath~//a/*[contains(text(),'Edit')]"),"Clicking Edit button as, reference already present");
            waitForJQueryLoad();waitForJQueryLoad();
        }

        if (isElementDisplayed(getObject("SendMoney.txtReference"), 1)) {
            clickJS(getObject("SendMoney.txtReference"), "Enter The Refrence field to send.");
            if(isMobile){sendKeysJS(getObject("SendMoney.txtReference"), strRefrenceToSend);}
            else{sendKeysJS(getObject("SendMoney.txtReference"), strRefrenceToSend);}
        } else {
            report.updateTestLog("SendMoneyFormHappyPath", "Reference field is not displayed correctly.", Status_CRAFT.FAIL);
        }


        if (isElementDisplayed(getObject("SendMoney.btnToday"), 1) && isElementDisplayed(getObject("SendMoney.txtReference"), 1) && isElementDisplayed(getObject("SendMoney.txtMsgToPayee"), 1)) {
            clickJS(getObject("SendMoney.txtMsgToPayee"), "Click Message to Payee.");
            if(isMobile){sendKeysJS(getObject("SendMoney.txtMsgToPayee"), strMsgToPayee);}
            else{sendKeys(getObject("SendMoney.txtMsgToPayee"), strMsgToPayee);}
            waitForJQueryLoad();waitForPageLoaded();
            report.updateTestLog("SendMoneyFormHappyPath", "Payment Type Today or Ref Text Or Message to Payee is displayed successfully.", Status_CRAFT.PASS);
        }

        ScrollToVisibleJS(("SendMoney.btnContinue"));
        report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        if (isElementDisplayed(getObject("SendMoney.btnContinue"), 3)) {
            waitForJQueryLoad();waitForPageLoaded();
            clickJS(getObject("SendMoney.btnContinue"), "Click on Continue Button");
            waitForJQueryLoad();waitForPageLoaded();
        }

        if (strErrorCheckForFields.equals("CheckFieldErrorValidations")) {
            return;
        }
        //To check the back navigation flow
        if (strBackNavigation.equals("BackNavigationCheck")) {
            verifyBackNavigationFlow("BackToSendMoneyPage");
            verifySendMoneyReviewPageROI();
            verifyBackNavigationFlow("BackToReviewPage");
            enterRequiredPin();

            SCA_MobileAPP scaMobileApp = new SCA_MobileAPP(scriptHelper);
            scaMobileApp.PushNotification_Acccept();
            PayeeAddConfirmation("SendMoney.parentConfirmation");

        } else if (strBackNavigation.equals("BackNavigationCheck_MainPayments")) {
            clickJS(getObject("SendMoney.btnGoBack"), "Click on Go Back Button from Review Page");
            waitForJQueryLoad();waitForJQueryLoad();
            clickJS(getObject("SendMoney.btnGoBack"), "Click on Go Back Button from Send Money Page");
            waitForJQueryLoad();waitForJQueryLoad();
        } else if (strBackNavigation.equals("BackButtonClickOnReviewPage")) {
            verifyClickOnReviewPageROI();
            clickJS(getObject("SendMoney.btnGoBack"), "Click on Go Back Button from Review Page");
            waitForJQueryLoad();waitForJQueryLoad();
            clickJS(getObject("SendMoney.btnGoBack"), "Click on Go Back Button from Review Page");
            SendMoneyPageChangeValue();
            verifySendMoneyReviewPageROI();
            enterRequiredPin();
            PayeeAddConfirmation("SendMoney.parentConfirmation");
        } else if (strBackNavigation.equals("BackArrowClickOnReviewPage")) {
            clickJS(getObject("SendMoney.Backbutton"), "Click on Back button from Review Page");
            SendMoneyPageChangeValue();
            verifySendMoneyReviewPageROI();
            waitForJQueryLoad();waitForJQueryLoad();
            //verifyClickOnReviewPageROI();
            enterRequiredPin();
            PayeeAddConfirmation("SendMoney.parentConfirmation");
        } else {
            verifySendMoneyReviewPageROI();
            enterRequiredPin();
            PayeeAddConfirmation("SendMoney.parentConfirmation");
            clickJS(getObject("SendMoney.btnBackToPayment"), "Click on Back To Payment Page.");
        }
    }


    public void verifyClickOnReviewPageROI() throws Exception {
        if (isElementDisplayed(getObject("xpath~//*[text()='Continue']"), 5)) {
            report.updateTestLog("verifyReviewPageCreditCard", "'Continue' button is displayed", Status_CRAFT.DONE);
            Thread.sleep(3000);
            clickJS(getObject("xpath~//*[text()='Continue']"), "Clicked on Button Continue");
            Thread.sleep(3000);
        } else {
            report.updateTestLog("verifyReviewPageCreditCard", "'Continue' button is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    public void SendMoneyPageChangeValue() throws Exception {
        String strAccountNumber = dataTable.getData("General_Data", "PayFrom_Account");
        String strPayee = dataTable.getData("General_Data", "PayTo_Account");
        String strAmountToSend = dataTable.getData("General_Data", "Pay_Amount");
        String strRefrenceToSend = dataTable.getData("General_Data", "Process_Transfer");
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");
        String preSelectedAccount = dataTable.getData("General_Data", "Account_Name");


        // Validate Send Money Page

        String strClick = "xpath~//span[text()='" + preSelectedAccount + "']";
        if (isElementDisplayed(getObject(strClick), 5)) {
            clickJS(getObject(strClick), "Click on Select Account from which money to send.");
            Thread.sleep(3000);
            List<WebElement> arrValidationAccnt = findElements(getObject("xpath~//li[contains(@class,'option')]"));
            int index = 0;
            for (int i = 0; i < arrValidationAccnt.size(); i++) {
                String strAccntText = arrValidationAccnt.get(i).getText();
                if (strAccntText.contains(strAccountNumber)) {
                    index = i;
                }
            }

            //String strAccToClick = "xpath~//li[contains(@class,'option')][@data-value='" + index + "']/descendant::span[contains(@class,'boi_doubleLineDropdownSpan')]";
            String strAccToClick = "xpath~//li[contains(@class,'option')][@data-value='" + index + "']";
            String ActualAccountSelected = getText(getObject(strAccToClick));
            clickJS(getObject(strAccToClick), "Click on Option :" + strAccountNumber);
            Thread.sleep(3000);

            String strAccSelected = "xpath~//select[contains(@name,'PAYFROMACCOUNT')]/following-sibling::div/span[@class='current']";
            if ((ActualAccountSelected).equals(strAccountNumber)) {
                report.updateTestLog("SendMoneyFormHappyPath", "The Account ::" + strAccountNumber + ":: selected successfully.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("SendMoneyFormHappyPath", "The Account ::" + strAccountNumber + ":: is not selected successfully.", Status_CRAFT.FAIL);
            }
        }

        waitforInVisibilityOfElementLocated(getObject("SendMoney.availableBalText"));

        if (isElementDisplayed(getObject("SendMoney.lstSelectPayeeBackButton"), 5)) {
            report.updateTestLog("SendMoneyFormHappyPath", " Once Send from account is selected successfully and then only Select Payee option is available", Status_CRAFT.DONE);
            clickJS(getObject("SendMoney.lstSelectPayeeBackButton"), "Select Payee");
            Thread.sleep(3000);
            List<WebElement> arrValidationPayee = findElements(getObject("xpath~//span[contains(@class,'ls-dli boi_input boi_doubleLineDropdownSpan')]"));
            int indexPayee = 0;
            for (int i = 0; i < arrValidationPayee.size(); i++) {
                String strPayeeText = arrValidationPayee.get(i).getText();
                if (strPayeeText.contains(strPayee)) {
                    indexPayee = i;
                    break;
                }
            }

            String strPayeeToSelect = "xpath~//li[@data-value='" + indexPayee + "']/div/span[1]";
            clickJS(getObject(strPayeeToSelect), "Click on Option :" + strPayee);
        }

        // Enter and verify the amount field
        if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
            clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send.");
            sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmountToSend);
        } else {
            report.updateTestLog("SendMoneyFormHappyPath", "Amount field or amount filed place holder is not displayed correctly.", Status_CRAFT.FAIL);
        }

        // Enter reference field
        if (isElementDisplayed(getObject("SendMoney.txtReference"), 5)) {
            clickJS(getObject("SendMoney.txtReference"), "Enter The Refrence field to send.");
            sendKeysJS(getObject("SendMoney.txtReference"), strRefrenceToSend);
        } else {
            report.updateTestLog("SendMoneyFormHappyPath", "Reference field is not displayed correctly.", Status_CRAFT.FAIL);
        }

        ScrollToVisibleJS(("SendMoney.btnContinue"));
        report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        if (isElementDisplayed(getObject("SendMoney.btnContinue"), 10)) {
            Thread.sleep(1000);
            clickJS(getObject("SendMoney.btnContinue"), "Click on Continue Button");
            Thread.sleep(4000);
        }
    }


   /*Payments Back Navigation check :
    * Check the Back Navigation
    * */

    public void verifyBackNavigationFlow(String AccountName) throws Exception {
        switch (AccountName) {

            case "BackToSendMoneyPage":
                clickJS(getObject("SendMoney.btnGoBack"), "Click on Go Back Button from Review Page");
                waitForJQueryLoad();waitForJQueryLoad();
                LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
                String[] strVerifyDetails = dataTable.getData("General_Data", "AccountPosition_Grouped").split(";");

                for (int i = 0; i <= (strVerifyDetails.length - 1); i++) {
                    String strFieldName = strVerifyDetails[i].split("-->")[0];
                    String strFieldvalue = strVerifyDetails[i].split("-->")[1];
                    mapData.put(strFieldName, strFieldvalue);
                }

                report.updateTestLog("verifyBackNavigationFlow", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                for (Map.Entry obj : mapData.entrySet()) {
                    if (getText(getObject((obj.getKey()).toString())).toUpperCase().equals(obj.getValue().toString().toUpperCase())) {
                        report.updateTestLog("verifySendMoneyReviewPage", "After clicking back button value :'" + obj.getValue() + "' on send money page is correctly displayed.", Status_CRAFT.DONE);
                    } else {
                        report.updateTestLog("verifySendMoneyReviewPage", "After clicking back button value :'" + obj.getValue() + "' on send money page is not correctly displayed.", Status_CRAFT.FAIL);
                    }
                }

                ScrollToVisibleJS(("SendMoney.btnContinue"));
                report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if (isElementDisplayed(getObject("SendMoney.btnContinue"), 10)) {
                    waitForJQueryLoad();waitForPageLoaded();
                    clickJS(getObject("SendMoney.btnContinue"), "Click on Continue Button");
                    waitForJQueryLoad();waitForPageLoaded();
                }
                break;

            case "BackToReviewPage":
                clickJS(getObject("SendMoney.btnGoBack"), "Click on Go Back Button from PIN Page.");
                waitForJQueryLoad();waitForPageLoaded();
                verifySendMoneyReviewPageROI();
                break;
        }

    }


    /*Payments Review page :
    * Review All the details that we have entered
    * */

    public void verifySendMoneyReviewPage() throws Exception {
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String strOperation = dataTable.getData("General_Data", "Operation");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        report.updateTestLog("verifySendMoneyReviewPage", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        for (Map.Entry obj : mapData.entrySet()) {
            if (isElementDisplayed(getObject("xpath~//*[contains(text(),'" + obj.getKey() + "')]/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[contains(text(),'" + obj.getValue() + "')]"), 5)) {
                report.updateTestLog("verifySendMoneyReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifySendMoneyReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
            }
        }

        String strProssTran = dataTable.getData("General_Data", "Process_Transfer");
        if (strProssTran.equalsIgnoreCase("Schedule future date")) {
            String ActDate = getText(getObject("xpath~//span[text()='Process payment']/ancestor::div[contains(@class,'question-part')]/following-sibling::div")).replace("\n"," ");
            String ExpDate = (scriptHelper.commonData.strTransferDate).replace("/", " ");
            if (ActDate.equals(ExpDate)) {
                report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + ExpDate + "' correctly displayed on review page,Expected::'" + ExpDate + "', Actual::''" + ActDate + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + ExpDate + "' is not correctly displayed on review page,Expected::'" + ExpDate + "', Actual::''" + ActDate + "'", Status_CRAFT.FAIL);
            }
            validateProcessingAndDailylimits();
        } else {
            validateDailylimitsNCutOffTimes();
        }
        //Directional text to verify
        if (isElementDisplayed(getObject(deviceType() + "SendMoney.hdrReviewPage"), 5)) {
            report.updateTestLog("verifySendMoneyReviewPage", "Send Money Header correctly displayed on review page.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifySendMoneyReviewPage", "Send Money Header not displayed on review page.", Status_CRAFT.FAIL);
        }

        if(!dataTable.getData("General_Data", "Properties_Variable").equalsIgnoreCase("")) {
            String[] strTextDetails = dataTable.getData("General_Data", "Properties_Variable").split("::");
            ScrollToVisibleJS(("xpath~//*[text()='Cut-off times']"));
            report.updateTestLog("verifySendMoneyReviewPage", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
            for (int j = 0; j < strTextDetails.length; j++) {
                if (isElementDisplayed(getObject("xpath~//*[contains(text(),'" + strTextDetails[j] + "')]"), 5)) {
                    report.updateTestLog("verifySendMoneyReviewPage", "'" + strTextDetails[j] + "' :: correctly displayed on review page.", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifySendMoneyReviewPage", "'" + strTextDetails[j] + "' :: is not correctly displayed on review page.", Status_CRAFT.FAIL);
                }
            }
        }

        //Back and Continue Button to Verify
        if (isElementDisplayed(getObject("xpath~//*[text()='Go back']"), 5)) {
            report.updateTestLog("verifyReviewPageCreditCard", "'Go back' button is displayed", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyReviewPageCreditCard", "'Go back' button is NOT displayed", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("xpath~//*[text()='Continue']"), 5)) {
            report.updateTestLog("verifyReviewPageCreditCard", "'Continue' button is displayed", Status_CRAFT.DONE);
            Thread.sleep(3000);
            clickJS(getObject("xpath~//*[text()='Continue']"), "Clicked on Button Continue");
            Thread.sleep(3000);
        } else {
            report.updateTestLog("verifyReviewPageCreditCard", "'Continue' button is NOT displayed", Status_CRAFT.FAIL);
        }

    }

    public void AccountNotPresent() throws Exception {
        String strAccountNumber = dataTable.getData("General_Data", "Account_Name");
        String strPayee = dataTable.getData("General_Data", "Nickname");


        //Navigate to till Send Money Page From Home Page
        driver.findElement(By.xpath("//*[text()='PAYMENTS']")).click();
        Thread.sleep(3000);

        //Check the icon send money
        report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        if (isElementDisplayed(getObject("SendMoney.iconSendMoney"), 3)) {
            report.updateTestLog("SendMoneyFormHappyPath", " Send Money icon is visible.", Status_CRAFT.DONE);
            click(getObject("SendMoney.iconSendMoney"));
            Thread.sleep(4000);
        } else {
            report.updateTestLog("SendMoneyFormHappyPath", " Send Money icon is not visible..!!", Status_CRAFT.FAIL);
        }

        // Validate Send Money Page
        if (isElementDisplayed(getObject("SendMoney.lstSelectAccount"), 3)) {
            clickJS(getObject("SendMoney.lstSelectAccount"), "Click on Select Account from which money to send.");
            Thread.sleep(3000);
            List<WebElement> arrValidationAccnt = findElements(getObject("xpath~//li[contains(@class,'option')]"));
            int index = 0;
            for (int i = 0; i < arrValidationAccnt.size(); i++) {
                String strAccntText = arrValidationAccnt.get(i).getText();
                if (strAccntText.contains(strAccountNumber)) {
                    report.updateTestLog("SendMoneyAccountPresent", "Blocked to Debit account number is present", Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("SendMoneyAccountPresent", "Blocked to Debit account number is not present", Status_CRAFT.PASS);
                }
            }
        }
    }

    // Add Payee Review Page
    public void verifyAddPayeeDetail() throws Exception {
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] arrValidation = dataTable.getData("General_Data", "VerifyDetails").split(";");

        for (int i = 0; i < arrValidation.length; i++) {
            String strFieldName = arrValidation[i].split("::")[0];
            String strFieldvalue = arrValidation[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }
        report.updateTestLog("verifyAddPayeeDetail", " :: Review Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        for (Map.Entry obj : mapData.entrySet()) {
            if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']/ancestor::div[contains(@class,'question-part')]/following-sibling::div[contains(@class,'answer-part')]/descendant::*[text()='" + obj.getValue() + "']"), 5)) {
                report.updateTestLog("verifyAddPayeeDetail", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyAddPayeeDetail", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
            }
        }
        if(isMobile){
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);", driver.findElement(getObject(deviceType()+"Payments.btnGoback")));
        }
        else{
            scrollToView(getObject(deviceType()+"Payments.btnGoback"));
        }

        if (isElementDisplayed(getObject(deviceType() + "login.AmendedContinue"), 5)) {
            report.updateTestLog("verifyAddPayeeDetails", "'Continue' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddPayeeDetails", "'Continue' button is NOT displayed", Status_CRAFT.FAIL);
        }

        if (dataTable.getData("General_Data", "Operation").equals("")) {
            clickJS(getObject(deviceType() + "login.AmendedContinue"), "Continue");
        } else {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "OperationOnReviewPage");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.Continue")));
                    Thread.sleep(2000);
                    clickJS(getObject("AddPayee.Continue"), "Clicked on 'Continue', Add Payee Page");
                    break;
                case "GO BACK":
                    js.executeScript("window.scrollTo(0, document.body.scrollHeight);", driver.findElement(getObject("AddPayee.ReviewGoBack")));
                    Thread.sleep(2000);
                    if(isMobile || deviceType().equals("tw.")){
                        clickJS(getObject("xpath~//input[contains(@class,'mobile-breadcrumb headerBack')]"),"Clicked on 'Go back', Add Payee Page");
                    }
                    else{
                        clickJS(getObject("AddPayee.ReviewGoBack"), "Clicked on 'Go back', Add Payee Page");
                    }
                    break;
            }
        }
    }

    public void errorMessageValidation() throws Exception {
        clickJS(getObject("AddPayee.Continue"), "Add Payee : Continue button is clicked successfully..!!");
        Thread.sleep(3000);
        String errorMessage[] = dataTable.getData("General_Data", "ErrorText").split("::");
        report.updateTestLog("errorMessageValidation", " :: Error Screenshot ::", Status_CRAFT.SCREENSHOT);
        for (int i = 0; i < errorMessage.length; i++) {
            if (isElementDisplayed(getObject("xpath~//span[contains(@class,'boi_input_sm_error tc-error-position tc-fs-m2')][contains(text(),'" + errorMessage[i] + "')]"), 5)) {
                report.updateTestLog("errorMessageValidation", "Expected Error Message '" + errorMessage[i] + "' is displayed on Add Payee Page", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("errorMessageValidation", "Expected Error Message '" + errorMessage[i] + "' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
            }
        }
    }

    public void clickBacktoPayee() throws Exception {
        if (isElementDisplayed(getObject("xpath~//*[text()='Back to home']"), 10)) {
            click(getObject("xpath~//*[text()='Back to home']"), "Click Back to home");
            report.updateTestLog("verifyAddedPayee", " Back to home clicked successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddedPayee", "Back to home couldn't be clicked", Status_CRAFT.FAIL);
        }
    }

    public void verifyAddedPayeeDetails(String sectionElement) throws Exception {
        String[] arrValidation = dataTable.getData("General_Data", "Account_Name").split(";");
        if (isElementDisplayed(getObject(sectionElement), 5)) {
            String dataSectionUI = getText(getObject(sectionElement)).toUpperCase();
            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                String strValidateHead = arrValidation[intValidate].split("::")[0];
                String strValidateData = arrValidation[intValidate].split("::")[1];
                if (dataSectionUI.contains(strValidateData.toUpperCase())) {
                    report.updateTestLog("verifyAddedPayeeDetails", strValidateHead + " value '" + strValidateData + "' is displayed correctly ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyAddedPayeeDetails", strValidateHead + " value '" + strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("verifyAddedPayeeDetails", "Section element '" + sectionElement + "' is not displayed on Screen", Status_CRAFT.FAIL);
        }
    }


    public void TransferAccountLnkNotPresent() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 10)) {
            click(getObject(deviceType() + "home.tabPayments"), "'Payments Tab'");
            String strParentObject = deviceType() + "Payments.FundTransferLinkParent";
            String strContentToCheck = dataTable.getData("General_Data", "VerifyContent");
            String[] arrContentToCheck = strContentToCheck.split("::");
            for (int i = 0; i < arrContentToCheck.length; i++) {
                String strAllContent = getText(getObject(strParentObject));
                if (strAllContent.contains(arrContentToCheck[i])) {
                   report.updateTestLog("verifyCheckContentNotPresent", "The Content " + arrContentToCheck[i] + " is displayed.", Status_CRAFT.FAIL);
                } else {report.updateTestLog("verifyCheckContentNotPresent", "The Content " + arrContentToCheck[i] + " is not displayed.", Status_CRAFT.PASS);}
          }
        } else {
            report.updateTestLog("selectPaymentOption", "Payment menu tab not present on Home Page", Status_CRAFT.FAIL);
        }
    }

    public void validateMaxlength() throws Exception {
        String[] arr = dataTable.getData("General_Data", "MaxLength").split("::");
        String[] objNames = dataTable.getData("General_Data", "objName").split("::");
        for (int i = 0; i < arr.length; i++) {
            int maxlength = Integer.parseInt(arr[i]);
            HomePage homePage = new HomePage(scriptHelper);
            homePage.verifyMaxLength(maxlength, objNames[i]);
        }
    }


    /**
     * CFPUR-2800/1537/80 : Payment Form 1 : Errors
     */
    public void SendMoneyForm_Error() throws Exception {

        driver.findElement(By.xpath("//*[text()='PAYMENTS']")).click();
        Thread.sleep(5000);
        String strAccountNumber = dataTable.getData("General_Data", "Account_Name");
        String strPayee = dataTable.getData("General_Data", "Nickname");
        String strPayeeIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strPayeeBIC = dataTable.getData("General_Data", "BIC_Number");
        String strAmountToSend = dataTable.getData("General_Data", "Current_Balance");
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");

        //Navigate to till Send Money Page From Home Page :
        if (isElementDisplayed(getObject("SendMoney.iconSendMoney"), 3)) {
            click(getObject("SendMoney.iconSendMoney"));
            Thread.sleep(4000);
        }

        //Validate Send Money Page
        if (isElementDisplayed(getObject("SendMoney.lstSelectAccount"), 3)) {
            clickJS(getObject("SendMoney.lstSelectAccount"), "Click on Select Account");
            Thread.sleep(3000);
            List<WebElement> arrValidationAccnt = findElements(getObject("xpath~//li[contains(@class,'option')]"));
            int index = 0;
            for (int i = 0; i < arrValidationAccnt.size(); i++) {
                String strAccntText = arrValidationAccnt.get(i).getText();
                if (strAccntText.contains(strAccountNumber)) {
                    index = i;
                }
            }
            //String strAccToClick = "xpath~//li[contains(@class,'option')]/descendant::span[contains(@class,'ls-dli boi_input boi_doubleLineDropdownSpan')][text()='"+ strAccountNumber +"']";
            String strAccToClick = "xpath~//li[contains(@class,'option')][@data-value='" + index + "']/descendant::span[contains(@class,'boi_doubleLineDropdownSpan')]";
            clickJS(getObject(strAccToClick), "Click on Option :" + strAccountNumber);
            Thread.sleep(3000);
        }

        if (isElementDisplayed(getObject("SendMoney.lstSelectPayee"), 3)) { // Yet to finish
            clickJS(getObject("SendMoney.lstSelectPayee"), "Select Payee");
            Thread.sleep(3000);
            List<WebElement> arrValidationPayee = findElements(getObject("xpath~(//div[contains(@class,'nice-select')])[2]/descendant::li[contains(@class,'option')]"));
            int indexPayee = 0;
            for (int i = 0; i < arrValidationPayee.size(); i++) {
                String strPayeeText = arrValidationPayee.get(i).getText();
                if (strPayeeText.contains(strPayee)) {
                    indexPayee = i;
                }
            }
            String strPayeeToSelect = "xpath~(//div[contains(@class,'nice-select')])[2]/descendant::li[contains(@class,'option')][@data-value='" + indexPayee + "']";
            clickJS(getObject(strPayeeToSelect), "Click on Option :" + strPayee);
            //Validate the Payee selected
            String strPayeeIBANObj = "xpath~//span[contains(@class,'boi_label_sm ml-5')][text()='" + strPayeeIBAN + "']";
            String strPayeeBICObj = "xpath~//span[contains(@class,'boi_label_sm ml-5')][text()='" + strPayeeBIC + "']";
            //Validate the Payee IBAN and BIC
            if (isElementDisplayed(getObject(strPayeeIBANObj), 5) && isElementDisplayed(getObject(strPayeeBICObj), 5)) {
                report.updateTestLog("SendMoneyFormHappyPath", strPayee + ":: Account is selected successfully.", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("SendMoneyFormHappyPath", strPayee + ":: Account is not selected successfully.", Status_CRAFT.FAIL);
            }
        }

        //Validation for empty Amount and empty Reference error messages -Front end validation
        driver.findElement(By.xpath("//input[contains(@name,'C6__BOIPAYPERSONORBILL[1].SCREENELEMENTS[1].BOI-SEPATRANSFERFORM[1].REFERENCE')]")).clear();
        Thread.sleep(2000);
        clickJS(getObject("SendMoney.btnContinue"), "clicked Continue");
        String Amount_error = "Please enter an amount";
        String Reference_Error = "Please enter reference text";
        if (Amount_error.equals(getText(By.xpath("//span[contains(@class,'boi_input_sm_error boi-error-color boi-error-position boi-fs-m2')]"))) && Reference_Error.equals(getText(By.xpath("//span[contains(@class,'tc-error-color tc-error-position tc-fs-m2   edgeConnectDisabled')]")))) {
            report.updateTestLog("SendMoneyFormErrorPath", ":: Amount and Reference Error validation done successfully.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneyFormErrorPath", ":: Amount and Reference Error validation not done successfully.", Status_CRAFT.FAIL);
        }

        //validation of mainframe errors
       /* Error Message	                                                         Pay From	 Pay To	          Amount
       1. The transaction amount is invalid for the payment.	                11111111	 SEPA Payee_1	   101
       2. A technical error has occurred while processing this payment.	        11111111	SEPA Payee_1	    1
       3. The account specified is invalid.	                                    22222222	SEPA Payee_1	   99
       4.The 'From' account and the 'To' account cannot be the same,
       please re-select another account.	                                    11111111	SEPA Payee_3	1001
       5.Sorry, payments to this payee cannot exceed €19,005.	                11111111	SEPA Payee_4	20000
       6. The amount that you wish to transfer is greater than your available funds. Please try again with a lesser amount.	11111111	SEPA Payee_1	21000
       7. Transfers to this payee cannot exceed €140,000.                    	11111111	SEPA Payee_1	200000
       8.Daily lower limit exceeded for the payment.	                       11111111	SEPA Payee_1	250
       9. You have exceeded your daily limit.	                               11111111	SEPA Payee_1	10000
*/
        //Checking for Error:1 :: The transaction amount is invalid for the payment.
        sendKeys(getObject("SendMoney.lblReference"), "1111111");
        sendKeys(getObject("SendMoney.lblAmount"), "101");
        Thread.sleep(1000);
        clickJS(getObject("SendMoney.btnContinue"), "clicked Continue for Error:1");
        if (getText(getObject("SendMoney.lblMWError")).equals("The transaction amount is invalid for the payment.")) {
            scrollToView(getObject("SendMoney.lblMWError"));
            Thread.sleep(1500);
            report.updateTestLog("SendMoneyFormMWError::1", "::  Error-1 validation done successfully.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneyFormMWError::1", "::  Error-1 validation not done successfully.", Status_CRAFT.FAIL);
        }


        //Checking for Error:2 :: A technical error has occurred while processing this payment.
        //sendKeys(getObject("SendMoney.lblReference"),"1111111");
        sendKeys(getObject("SendMoney.lblAmount"), "1");
        Thread.sleep(1000);
        clickJS(getObject("SendMoney.btnContinue"), "clicked Continue for Error:2");
        if (getText(getObject("SendMoney.lblMWError")).equals("A technical error has occurred while processing this payment.")) {
            scrollToView(getObject("SendMoney.lblMWError"));
            Thread.sleep(1500);
            report.updateTestLog("SendMoneyFormMWError::2", "::  Error-2 validation done successfully.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneyFormMWError::2", "::  Error-2 validation not done successfully.", Status_CRAFT.FAIL);
        }

        //Checking for Error:6 :: The amount that you wish to transfer is greater than your available funds. Please try again with a lesser amount.
        //sendKeys(getObject("SendMoney.lblReference"),"1111111");
        sendKeys(getObject("SendMoney.lblAmount"), "21000");
        Thread.sleep(1000);
        clickJS(getObject("SendMoney.btnContinue"), "clicked Continue for Error:6");
        if (getText(getObject("SendMoney.lblMWError")).equals("The amount that you wish to transfer is greater than your available funds. Please try again with a lesser amount.")) {
            scrollToView(getObject("SendMoney.lblMWError"));
            Thread.sleep(1500);
            report.updateTestLog("SendMoneyFormMWError::6", "::  Error-6 validation done successfully.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneyFormMWError::6", "::  Error-6 validation not done successfully.", Status_CRAFT.FAIL);
        }

        //Checking for Error:7 :: Transfers to this payee cannot exceed €140,000.
        //sendKeys(getObject("SendMoney.lblReference"),"1111111");
        sendKeys(getObject("SendMoney.lblAmount"), "200000");
        Thread.sleep(1000);
        clickJS(getObject("SendMoney.btnContinue"), "clicked Continue for Error:7");
        if (getText(getObject("SendMoney.lblMWError")).equals("Transfers to this payee cannot exceed €140,000.")) {
            scrollToView(getObject("SendMoney.lblMWError"));
            Thread.sleep(1500);
            report.updateTestLog("SendMoneyFormMWError::7", "::  Error-7 validation done successfully.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneyFormMWError::7", "::  Error-7 validation not done successfully.", Status_CRAFT.FAIL);
        }

        //Checking for Error:8::Daily lower limit exceeded for the payment.
        //sendKeys(getObject("SendMoney.lblReference"),"1111111");
        sendKeys(getObject("SendMoney.lblAmount"), "250");
        Thread.sleep(1000);
        clickJS(getObject("SendMoney.btnContinue"), "clicked Continue for Error:8");
        if (getText(getObject("SendMoney.lblMWError")).equals("Daily lower limit exceeded for the payment.")) {
            scrollToView(getObject("SendMoney.lblMWError"));
            Thread.sleep(1500);
            report.updateTestLog("SendMoneyFormMWError::8", "::  Error-8 validation done successfully.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneyFormMWError::8", "::  Error-8 validation not done successfully.", Status_CRAFT.FAIL);
        }

    }

    /**
     * CFPUR-2800 : Payment : Validation of all field level error message
     */
    public void SEPAPayment_AllError() throws Exception {
        String strAccountNumber = dataTable.getData("General_Data", "Account_Name");
        String strPayee = dataTable.getData("General_Data", "Nickname");
        String strPayeeIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strPayeeBIC = dataTable.getData("General_Data", "BIC_Number");
        String strAmountToSend = dataTable.getData("General_Data", "Current_Balance");
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");
        String strBackNavigation = dataTable.getData("General_Data", "Operation");
        String ErrorValidationField = dataTable.getData("General_Data", "DirectionalText");

        //Navigate to till Send Money Page From Home Page
        if (!isMobile) {
            driver.findElement(By.xpath("//*[text()='PAYMENTS']")).click();
        } else {
            driver.findElement(By.xpath("//div[text()='Payments']")).click();
        }
        waitForJQueryLoad();
        waitForPageLoaded();
        //Check the icon send money
        report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        if (isElementDisplayed(getObject("SendMoney.iconSendMoney"), 3)) {
            report.updateTestLog("SendMoneyFormHappyPath", " Send Money icon is visible.", Status_CRAFT.DONE);
            click(getObject("SendMoney.iconSendMoney"));
            waitForJQueryLoad();
            waitForPageLoaded();
        } else {
            report.updateTestLog("SendMoneyFormHappyPath", " Send Money icon is not visible..!!", Status_CRAFT.FAIL);
        }

        // Validate Send Money Page
        if (isElementDisplayed(getObject("SendMoney.lstSelectAccount"), 3)) {
            clickJS(getObject("SendMoney.lstSelectAccount"), "Click on Select Account from which money to send.");
            waitForJQueryLoad();
            waitForPageLoaded();
            List<WebElement> arrValidationAccnt = findElements(getObject("xpath~//ul/li[contains(@class,'option')]"));
            boolean bflag = false;
            for (int i = 0; i < arrValidationAccnt.size(); i++) {
                String strAccntText = arrValidationAccnt.get(i).getText();
                if (strAccntText.toUpperCase().contains(strAccountNumber.toUpperCase())) {
                    arrValidationAccnt.get(i).click();
                    waitForJQueryLoad();
                    waitForPageLoaded();
                    bflag = true;
                    report.updateTestLog("SEPAPayment_AllError", "The Account ::" + strAccountNumber + ":: is selected from 'Pay from' list.", Status_CRAFT.PASS);
                    break;
                }
            }
            if (!bflag) {
                report.updateTestLog("SEPAPayment_AllError", "The Account ::" + strAccountNumber + ":: is NOT present in the list.", Status_CRAFT.FAIL);
            }
            waitforInVisibilityOfElementLocated(getObject("SendMoney.availableBalText"));

            // Select Payee to send money
            if (isElementDisplayed(getObject("SendMoney.lstSelectPayee"), 10)) {
                report.updateTestLog("SendMoneyFormHappyPath", " Once Send from account is selected successfully and then only Select Payee option is available", Status_CRAFT.DONE);
                clickJS(getObject("SendMoney.lstSelectPayee"), "Select Payee");
                waitForJQueryLoad();waitForPageLoaded();
                List<WebElement> arrValidationPayee = findElements(getObject("xpath~//ul/li[@role='option'][contains(text(),'Choose a payee or bill')]/following-sibling::li"));
                boolean bflagPayTo = false;
                for (int i = 0; i < arrValidationPayee.size(); i++) {
                    String strPayeeText = arrValidationPayee.get(i).getText();
                    if (strPayeeText.toUpperCase().contains(strPayee.toUpperCase())) {
                        arrValidationPayee.get(i).click();
                        report.updateTestLog("SEPAPayment_AllError", "The Payee ::" + strPayee + ":: is selected from 'Pay to' list.", Status_CRAFT.PASS);
                        bflagPayTo = true;
                        break;
                    }
                }
                if (!bflagPayTo) {
                    report.updateTestLog("SEPAPayment_AllError", "The Payee ::" + strPayee + ":: is NOT present in the 'Pay to' list.", Status_CRAFT.FAIL);
                }
            }

            // Enter and verify the amount field
            if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
                clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send.");
                sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmountToSend);
            } else {
                report.updateTestLog("SendMoneyFormHappyPath", "Amount field or amount filed place holder is not displayed correctly.", Status_CRAFT.FAIL);
            }

            // Enter reference field
            if(isElementDisplayed(getObject("xpath~//a/*[contains(text(),'Edit')]"),3)){
                click(getObject("xpath~//a/*[contains(text(),'Edit')]"),"Clicking Edit button as, reference already present");
                waitForJQueryLoad();waitForJQueryLoad();
            }

            if (isElementDisplayed(getObject("SendMoney.btnToday"), 5) && isElementDisplayed(getObject("SendMoney.txtReference"), 5) && isElementDisplayed(getObject("SendMoney.txtMsgToPayee"), 5)) {
                clickJS(getObject("SendMoney.txtMsgToPayee"), "Click Message to Payee.");
                waitForJQueryLoad();waitForPageLoaded();
                report.updateTestLog("SendMoneyFormHappyPath", "Payment Type Today or Ref Text Or Message to Payee is displayed successfully.", Status_CRAFT.PASS);
            }

            // Enter invalid amount and verify the error message
            if (ErrorValidationField.equalsIgnoreCase("AmountErrorValidation")) {
                // Verify Invalid Amount
                String strAmount = dataTable.getData("General_Data", "DepositAmount");
                if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
                    clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send.");
                    sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmount);
                } else {
                    report.updateTestLog("SendMoneyFormHappyPath", "Amount field or amount filed place holder is not displayed correctly.", Status_CRAFT.FAIL);
                }

                ScrollToVisibleJS(("SendMoney.btnContinue"));
                report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if (isElementDisplayed(getObject("SendMoney.btnContinue"), 3)) {
                    clickJS(getObject("SendMoney.btnContinue"), "Click on Continue Button");
                }

//                String InvalidDigitExpError = "Amount entered is invalid. Please try again";
                String InvalidDigitExpError ="Sorry, you can't enter that as the amount.";
                if (isElementDisplayed(getObject("SendMoney.lblInvalidAmountError"), 5)) {
                    String InvalidDigitActualError = getText(getObject("SendMoney.lblInvalidAmountError"));
                    if (InvalidDigitExpError.equalsIgnoreCase(InvalidDigitActualError)) {
                        report.updateTestLog("ErrorMessage", "Error '" + InvalidDigitExpError + "' is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("ErrorMessage", "Error '" + InvalidDigitExpError + "' is not displayed Actual::'"+InvalidDigitActualError+"'", Status_CRAFT.FAIL);
                    }
                }else{
                    report.updateTestLog("ErrorMessage", "Error is not displayed", Status_CRAFT.FAIL);
                }

                // Verify Amount Greater than available fund
                String strAmountMoreThanAvailable = dataTable.getData("General_Data", "NewAmount");
                if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
                    clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send.");
                    sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmountMoreThanAvailable);
                } else {
                    report.updateTestLog("SendMoneyFormHappyPath", "Amount field or amount field place holder is not displayed correctly.", Status_CRAFT.FAIL);
                }

                ScrollToVisibleJS(("SendMoney.btnContinue"));
                report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if (isElementDisplayed(getObject("SendMoney.btnContinue"), 3)) {
                    clickJS(getObject("SendMoney.btnContinue"), "Click on Continue Button");
                }

                String MoreThanAvailableFundError = "The amount that you wish to transfer is greater than your available funds. Please try again with a lesser amount.";
                if (isElementDisplayed(getObject("SendMoney.lblMoreThanAvailableFundError"), 5)) {
                    String InvalidDigitActualError = getText(getObject("SendMoney.lblMoreThanAvailableFundError"));
                    InvalidDigitActualError = InvalidDigitActualError.replaceAll("  "," ");
                    if (InvalidDigitActualError.contains(MoreThanAvailableFundError)) {
                        report.updateTestLog("ErrorMessage", "Error '" + MoreThanAvailableFundError + "' is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("ErrorMessage", "Error '" + MoreThanAvailableFundError + "' is not displayed", Status_CRAFT.FAIL);
                    }
                }else {
                    report.updateTestLog("ErrorMessage", "Error '" + MoreThanAvailableFundError + "' is not displayed", Status_CRAFT.FAIL);
                }

                // Verify Amount Greater than available limit of payee.
                String strAmountLimitExceed = dataTable.getData("General_Data", "Pay_Amount");
                if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
                    clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send.");
                    sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmountLimitExceed);
                } else {
                    report.updateTestLog("SendMoneyFormHappyPath", "Amount field or amount field place holder is not displayed correctly.", Status_CRAFT.FAIL);
                }


                ScrollToVisibleJS(("SendMoney.btnContinue"));
                report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if (isElementDisplayed(getObject("SendMoney.btnContinue"), 3)) {
                    clickJS(getObject("SendMoney.btnContinue"), "Click on Continue Button");
                }

                String amountExceedError = "Sorry, payments to this payee cannot exceed €20,000.";
                if (isElementDisplayed(getObject("SendMoney.lblExceedAmountError"), 5)) {
                    String InvalidDigitActualError = getText(getObject("SendMoney.lblExceedAmountError"));
                    if (InvalidDigitActualError.equalsIgnoreCase(amountExceedError)) {
                        report.updateTestLog("ErrorMessage", "Error '" + amountExceedError + "' is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("ErrorMessage", "Error '" + amountExceedError + "' is not displayed, Actual error::'"+InvalidDigitActualError+"' ", Status_CRAFT.FAIL);
                    }
                }else{
                    report.updateTestLog("ErrorMessage", "Error is not displayed", Status_CRAFT.FAIL);
                }
            }

            // To verify error validation for reference field
            if (ErrorValidationField.equalsIgnoreCase("ReferenceFieldErrorValidation")) {

                // Enter reference field
                if(isElementDisplayed(getObject("xpath~//a/*[contains(text(),'Edit')]"),3)){
                    click(getObject("xpath~//a/*[contains(text(),'Edit')]"),"Clicking Edit button as, reference already present");
                    waitForJQueryLoad();waitForJQueryLoad();
                }


                // Verify error for blank reference field
                if (isElementDisplayed(getObject("SendMoney.txtReference"), 5)) {
                    clickJS(getObject("SendMoney.txtReference"), "Enter The Reference field to send.");
                    sendKeysJS(getObject("SendMoney.txtReference"), "");
                } else {
                    report.updateTestLog("SendMoneyFormHappyPath", "Reference field is not displayed correctly.", Status_CRAFT.FAIL);
                }

                ScrollToVisibleJS(("SendMoney.btnContinue"));
                report.updateTestLog("VerifyRefrence", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if (isElementDisplayed(getObject("SendMoney.btnContinue"), 10)) {
                    //  Thread.sleep(1000);
                    clickJS(getObject("SendMoney.btnContinue"), "Click on Continue Button");
                    waitForJQueryLoad();waitForPageLoaded();
                    //  Thread.sleep(4000);
                }

                String BlankRefExpError = "Please enter a reference.";
                if (isElementDisplayed(getObject("SendMoney.lblBlankRefFieldError"), 5)) {
                    String BlankRefActualError = getText(getObject("SendMoney.lblBlankRefFieldError"));
                    if (BlankRefExpError.equalsIgnoreCase(BlankRefActualError)) {
                        report.updateTestLog("ErrorMessage", "Error '" + BlankRefExpError + "' is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("ErrorMessage", "Error '" + BlankRefExpError + "' is not displayed, Actual error::'"+BlankRefActualError+"'", Status_CRAFT.FAIL);
                    }
                }else{
                    report.updateTestLog("ErrorMessage", "Error is not displayed", Status_CRAFT.FAIL);
                }

                // Verify error for invalid reference field
                String strInvalidCharacterValue = dataTable.getData("General_Data", "SequenceAccType");
                if (isElementDisplayed(getObject("SendMoney.txtReference"), 5)) {
                    clickJS(getObject("SendMoney.txtReference"), "Enter The Reference field to send.");
                    sendKeysJS(getObject("SendMoney.txtReference"), strInvalidCharacterValue);
                } else {
                    report.updateTestLog("SendMoneyFormHappyPath", "Reference field is not displayed correctly.", Status_CRAFT.FAIL);
                }

                ScrollToVisibleJS(("SendMoney.btnContinue"));
                report.updateTestLog("VerifyRefrence", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if (isElementDisplayed(getObject("SendMoney.btnContinue"), 10)) {
                    //  Thread.sleep(1000);
                    clickJS(getObject("SendMoney.btnContinue"), "Click on Continue Button");
                    // Thread.sleep(4000);
                }

//                String InvalidRefExpError = "The reference field you are adding must be alphanumeric";
                String InvalidRefExpError = "Reference must contain letters and numbers only.";
                if (isElementDisplayed(getObject("SendMoney.lblBlankRefFieldError"), 5)) {//SendMoney.lblInvalidRefFieldError
                    String InvalidRefActualError = getText(getObject("SendMoney.lblBlankRefFieldError"));
                    if (InvalidRefExpError.equalsIgnoreCase(InvalidRefActualError)) {
                        report.updateTestLog("ErrorMessage", "Error '" + InvalidRefExpError + "' is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("ErrorMessage", "Error '" + InvalidRefExpError + "' is not displayed,Actual error::'"+InvalidRefActualError+"'", Status_CRAFT.FAIL);
                    }
                }else{
                    report.updateTestLog("ErrorMessage", "Error is not displayed", Status_CRAFT.FAIL);
                }
            }

            //**** To verify error validation for Message to Payee field
            if (ErrorValidationField.equalsIgnoreCase("MessageToPayeeErrorValidation")) {
                // Verify error for invalid reference field
                String strInvalidCharacterValue = dataTable.getData("General_Data", "SequenceAccType");
                if (isElementDisplayed(getObject("SendMoney.messagetoPayeePlaceholder"), 5)) {
                    click(getObject("SendMoney.txtMsgToPayee"), "Enter the message to payee field.");
                    sendKeys(getObject("SendMoney.txtMsgToPayee"), strInvalidCharacterValue);
                } else {
                    report.updateTestLog("SendMoneyFormHappyPath", "Message to Payee field is not displayed correctly.", Status_CRAFT.FAIL);
                }

                ScrollToVisibleJS(("SendMoney.btnContinue"));
                report.updateTestLog("VerifyRefrence", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if (isElementDisplayed(getObject("SendMoney.btnContinue"), 10)) {
                    //  Thread.sleep(1000);
                    clickJS(getObject("SendMoney.btnContinue"), "Click on Continue Button");
                    waitForJQueryLoad();waitForPageLoaded();
                    // Thread.sleep(4000);
                }

//                String InvalidRefExpError = "The Message to payee field you are adding must be alphanumeric and no more than 140 characters";
                String InvalidRefExpError = "Sorry, you can't use some of those characters here.";
                if (isElementDisplayed(getObject("SendMoney.lblBlankRefFieldError"), 5)) {
                    String InvalidRefActualError = getText(getObject("SendMoney.lblBlankRefFieldError"));//SendMoney.messagetoPayeeInvalidCharError
                    if (InvalidRefExpError.equalsIgnoreCase(InvalidRefActualError)) {
                        report.updateTestLog("ErrorMessage", "Error '" + InvalidRefExpError + "' is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("ErrorMessage", "Error '" + InvalidRefExpError + "' is not displayed, Actual error::'"+InvalidRefActualError+"'", Status_CRAFT.FAIL);
                    }
                }else{
                    report.updateTestLog("ErrorMessage", "Error is not displayed", Status_CRAFT.FAIL);
                }
            }

            ScrollToVisibleJS(("SendMoney.btnContinue"));
            report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
            if (isElementDisplayed(getObject("SendMoney.btnContinue"), 10)) {
                clickJS(getObject("SendMoney.btnContinue"), "Click on Continue Button");
            }
        }
    }



    public void ValidateAlertText() throws Exception {
        if ((dataTable.getData("General_Data", "JurisdictionType").equals("NI")) | (dataTable.getData("General_Data", "JurisdictionType").equals("GB"))) {
            String UiAlertText = getText(getObject("AddPayee.txtAlert"));
            String expectedAlerttxt = "If you choose EUR for sending money to Ireland or other SEPA zone countries (excluding UK) the account will be set up as a SEPA payee - see FAQs for more info on sending money and any associated charges.";
            String expectedAlerttxt2 = "The currency selection you make may impact the charges applicable – more info on charges. If you choose EUR for a SEPA zone country, you will make a SEPA transfer every time you send money outside the UK to this account.";
            if (UiAlertText.equals(expectedAlerttxt) || UiAlertText.equals(expectedAlerttxt2)) {
                report.updateTestLog("VerifyAlertText", "Alert text for PAD 2 for NI/GB is  displayed is as Expected '" + expectedAlerttxt2 + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyAlertText", "Alert text for PAD 2 for NI/GB is  displayed is NOT as Expected '" + expectedAlerttxt2 + "', Actual ''" + UiAlertText + "", Status_CRAFT.FAIL);
            }
        }
        if (!isMobile) {
            click(getObject("AddPayee.linkFAQ"));
            report.updateTestLog("verifyClickLink", "Link Clicked", Status_CRAFT.PASS);
            String ParentTab = driver.getWindowHandle();
            ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
            newTab.remove(ParentTab);
            // change focus to new tab
            driver.switchTo().window(newTab.get(0));
            Thread.sleep(10000);
            driver.getCurrentUrl();
            if (driver.getCurrentUrl().contains("https://www.bankofireland.com/fees-and-charges-365-online/"))  {
                report.updateTestLog("verifyWindowTitle", "New Window is displayed with correct URL", Status_CRAFT.PASS);

            } else {
                report.updateTestLog("verifyWindowTitle", "New Window is displayed with incorrect URL", Status_CRAFT.FAIL);
            }
        }
    }



    /*Payments Review page :
    * Review All the details that we have entered
    * */

    public void verifySendMoneyReviewPageROI() throws Exception {
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String strOperation = dataTable.getData("General_Data", "Operation");

        waitforInVisibilityOfElementLocated(getObject("launch.spinSpinner"));
        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        report.updateTestLog("verifySendMoneyReviewPage", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        for (Map.Entry obj : mapData.entrySet()) {
            if (isElementDisplayed(getObject("xpath~//*[contains(text(),'" + obj.getKey() + "')]/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[contains(text(),'" + obj.getValue() + "')]"), 5)) {
                scrollToView(getObject("xpath~//*[contains(text(),'" + obj.getKey() + "')]/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[contains(text(),'" + obj.getValue() + "')]"));
                report.updateTestLog("verifySendMoneyReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifySendMoneyReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
            }
        }

        //Directional text to verify
        if (isMobile){
            isElementDisplayedWithLog(getObject("xpath~//h3[text()='Send Money']"),"Header 'Send Money'","Review Page",2);
        }else{
            isElementDisplayedWithLog(getObject("xpath~//h1[@class='ecDIB  '][text()='Send Money']"),"Header 'Send Money'","Review Page",2);
        }

        String[] strTextDetails = dataTable.getData("General_Data", "Properties_Variable").split("::");
        ScrollToVisibleJS(("xpath~//*[text()='Cut-off times']"));
        report.updateTestLog("verifySendMoneyReviewPage", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        for (int j = 0; j < strTextDetails.length; j++) {
            if (isElementDisplayed(getObject("xpath~//*[text()='" + strTextDetails[j] + "']"), 5)) {
                report.updateTestLog("verifySendMoneyReviewPage", "'" + strTextDetails[j] + "' :: correctly displayed on review page.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifySendMoneyReviewPage", "'" + strTextDetails[j] + "' :: is not correctly displayed on review page.", Status_CRAFT.FAIL);
            }
        }

        //Back and Continue Button to Verify
        if (isElementDisplayed(getObject("xpath~//span[text()='Go back']"), 1)) {
            report.updateTestLog("verifyReviewPageCreditCard", "'Go back' button is displayed", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyReviewPageCreditCard", "'Go back' button is NOT displayed", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("xpath~//*[text()='Continue']"), 1)) {
            report.updateTestLog("verifyReviewPageCreditCard", "'Continue' button is displayed", Status_CRAFT.DONE);
            waitForJQueryLoad();waitForPageLoaded();
            clickJS(getObject("xpath~//*[text()='Continue']"), "Clicked on Button Continue");
            waitForJQueryLoad();waitForPageLoaded();
        } else {
            report.updateTestLog("verifyReviewPageCreditCard", "'Continue' button is NOT displayed", Status_CRAFT.FAIL);
        }

    }

    public void selectPayeeCountryAndCurrency() throws Exception {
        //validating page title 'Add Payee'
        if (isElementDisplayed(getObject(deviceType() + "AddPayee.hdrPageTitle"), 5)) {
            report.updateTestLog("selectPayeeCountryAndCurrency", "Page Title 'Add Payee' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPayeeCountryAndCurrency", "Page Title 'Add Payee' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating section title Country & currency
        if (isElementDisplayed(getObject("AddPayee.hdrCountry&currency"), 5)) {
            report.updateTestLog("selectPayeeCountryAndCurrency", "Section heading 'Country & currency' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPayeeCountryAndCurrency", "Section heading 'Country & currency' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //label Country
        if (isElementDisplayed(getObject("AddPayee.lblCountry"), 5)) {
            report.updateTestLog("selectPayeeCountryAndCurrency", "Label 'Country' is displayed on Add Payee Page", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("AddPayee.lblPayeeBankLocation"), 5)) {
                report.updateTestLog("selectPayeeCountryAndCurrency", "Label 'Location of payee's bank.' is displayed on Add Payee Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPayeeCountryAndCurrency", "Label 'Location of payee's bank.' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
            }
            //validate default country & Currency
            //   String defaultCountry = getText(getObject("xpath~//select[contains(@name,'PAYTOCOUNTRY')]/following-sibling::div/span[@class='current']"));
            String flag = dataTable.getData("General_Data", "IterationFlag");
            if (flag.equalsIgnoreCase("Yes")) {
                //put your code here
                defaultCountry = getText(getObject("AddPayee.newDefaultCountry"));
                defaultCurrency = getText(getObject("AddPayee.lstCurrency"));
            } else if (flag.equalsIgnoreCase("")) {
                //this is to check the default
                defaultCountry = getText(getObject("AddPayee.lblDefaultCountry"));
                defaultCurrency = getText(getObject("AddPayee.lblDefaultCurrency"));
            }
            String expectedCountry = dataTable.getData("General_Data", "DefaultCountry");
            String expectedCurrency = dataTable.getData("General_Data", "DefaultCurrency");
            //default country
            if (defaultCountry.equalsIgnoreCase(expectedCountry)) {
                report.updateTestLog("selectPayeeCountryAndCurrency", "Default Country displayed is as Expected '" + expectedCountry + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPayeeCountryAndCurrency", "Default Country selected is NOT as Expected '" + expectedCountry + "', Actual ''" + defaultCountry + "", Status_CRAFT.FAIL);
            }
            //default currency
            if (defaultCurrency.equalsIgnoreCase(expectedCurrency)) {
                report.updateTestLog("selectPayeeCountryAndCurrency", "Default Currency displayed is as Expected '" + expectedCurrency + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPayeeCountryAndCurrency", "Default Currency selected is NOT as Expected '" + expectedCurrency + "', Actual ''" + defaultCurrency + "", Status_CRAFT.FAIL);
            }


            if (!dataTable.getData("General_Data", "PayeeCountry").equals("")) {

                if (deviceType.equalsIgnoreCase("Web")) {
                    click(getObject("AddPayee.lblDefaultCountry"), "Country dropdown Selected");
                    String countryName = dataTable.getData("General_Data", "PayeeCountry");
                    click(getObject("xpath~//button[text()='" + expectedCountry + "']/following-sibling::ul/li[contains(.,'" + countryName + "')]"), "'" + countryName + "' Country Selected");
                } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
                    clickJS(getObject("AddPayee.lblDefaultCountry"), "Country dropdown Selected");
                    String countryName = dataTable.getData("General_Data", "PayeeCountry");
                    clickJS(getObject("xpath~//ul/li[@role='option'][contains(.,'" + countryName + "')]"), "'" + countryName + "' Country Selected");
                }
//                clickJS(getObject("AddPayee.lstCountry"), "Add Payee Dropdown");
//                Thread.sleep(2000);
//                driver.findElement(By.xpath("//ul/li[@role='option'][contains(.,'" + dataTable.getData("General_Data", "PayeeCountry") + "')]")).click();
//                report.updateTestLog("selectPayeeCountryAndCurrency", "Payee Country selected '" + dataTable.getData("General_Data", "PayeeCountry") + "'", Status_CRAFT.DONE);
//                waitForPageLoaded();
//                Thread.sleep(2000);
            }

            if ((dataTable.getData("General_Data", "JurisdictionType").equals("NI")) | (dataTable.getData("General_Data", "JurisdictionType").equals("GB"))) {
                String UiAlertText = getText(getObject("AddPayee.txtAlert"));
                String expectedAlerttxt = "If you choose EUR for sending money to Ireland or other SEPA zone countries (excluding UK) the account will be set up as a SEPA payee - see FAQs for more info on sending money and any associated charges.";
                String expectedAlerttxt2 = "The currency selection you make may impact the charges applicable – more info on charges. If you choose EUR for a SEPA zone country, you will make a SEPA transfer every time you send money outside the UK to this account.";
                if (UiAlertText.equals(expectedAlerttxt) || UiAlertText.equals(expectedAlerttxt2)) {
                    report.updateTestLog("selectPayeeCountryAndCurrency", "Alert text for PAD 2 for NI/GB is  displayed is as Expected '" + expectedAlerttxt2 + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("selectPayeeCountryAndCurrency", "Alert text for PAD 2 for NI/GB is  displayed is NOT as Expected '" + expectedAlerttxt2 + "', Actual ''" + UiAlertText + "", Status_CRAFT.FAIL);
                }

            }

        } else {
            report.updateTestLog("selectPayeeCountryAndCurrency", "Label 'Country' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //currency selection
        scrollToView(getObject("AddPayee.lblCurrency"));
        if (isElementDisplayed(getObject("AddPayee.lblCurrency"), 10)) {
            report.updateTestLog("enterAddPayeeDetails", "Label 'Currency' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String newCurrency = dataTable.getData("General_Data", "PayeeCurrency");
            if (dataTable.getData("General_Data", "DefaultPopulated").equalsIgnoreCase("True")) {
                if (getText(getObject("AddPayee.lblDefaultCurrencyAfterSelectionPLN")).contains(newCurrency) || getText(getObject("AddPayee.lblDefaultCurrencyAfterSelectionCAD")).contains(newCurrency)) {
                    report.updateTestLog("selectPayeeCountryAndCurrency", "Correct Currency populated upon country selection, Expected '" + dataTable.getData("General_Data", "PayeeCurrency") + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("selectPayeeCountryAndCurrency", "Correct Currency is NOT populated upon country selection '" + dataTable.getData("General_Data", "PayeeCurrency") + "', Actual ''" + getText(getObject("AddPayee.lblDefaultCurrency")) + "", Status_CRAFT.FAIL);
                }

            } else {
                if (!dataTable.getData("General_Data", "PayeeCurrency").equals("")) {
                    WebElement elm = driver.findElement(getObject("AddPayee.lstCurrency"));
                    ScrollAndClickJS("AddPayee.lstCurrency");
                    Thread.sleep(1000);
                    String PayeeCurrency = dataTable.getData("General_Data", "PayeeCurrency");
                    if(isMobile){
                        clickJS(getObject("xpath~//ul/li[contains(text(),'" + PayeeCurrency + "')]"), "Selecting Currency");
                    }else{
                        click(getObject("xpath~//ul/li[contains(text(),'" + PayeeCurrency + "')]"), "Selecting Currency");
                    }
                    waitForPageLoaded();
                    Thread.sleep(2000);
                }
            }
        } else {
            report.updateTestLog("selectPayeeCountryAndCurrency", "Label 'Currency' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }
    }

    public void VerifyPayeeCountryAndCurrency() throws Exception {
        String expectedCountry = dataTable.getData("General_Data", "DefaultCountry");
        String countryName = dataTable.getData("General_Data", "PayeeCountry");
        if (!dataTable.getData("General_Data", "PayeeCountry").equals("")) {
            if (deviceType.equalsIgnoreCase("Web")) {
                click(getObject("AddPayee.lblDefaultCountry"), "Country dropdown Selected");
                clickJS(getObject("xpath~//button[@id='C5__QUE_E50DFFB0F8190DA23674858_widgetARIA']/following-sibling::ul/li[contains(.,'" + countryName + "')]"), "Country Selected");
                //click(getObject("xpath~//button[text()='" + expectedCountry + "']/following-sibling::ul/li[contains(.,'" + countryName + "')]"),"Country Selected");
            } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
                clickJS(getObject("AddPayee.lblDefaultCountry"), "Country dropdown Selected");
                clickJS(getObject("xpath~//button[@id='C5__QUE_E50DFFB0F8190DA23674858_widgetARIA']/following-sibling::ul/li[contains(.,'" + countryName + "')]"), "Country Selected");
            }
            report.updateTestLog("selectPayeeCountryAndCurrency", "Payee Country selected '" + dataTable.getData("General_Data", "PayeeCountry") + "'", Status_CRAFT.DONE);
            waitForPageLoaded();
            Thread.sleep(2000);
        }

        //currency selection
        if (isElementDisplayed(getObject("AddPayee.lblCurrency"), 5)) {
            report.updateTestLog("enterAddPayeeDetails", "Label 'Currency' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String newCurrency = dataTable.getData("General_Data", "DefaultCurrency");
            if (dataTable.getData("General_Data", "DefaultPopulated").equalsIgnoreCase("True")) {
                if (getText(getObject("AddPayee.lblDefaultCurrencyAfterSelectionPLN")).equals(newCurrency) || getText(getObject("AddPayee.lblDefaultCurrencyAfterSelectionCAD")).equals(newCurrency)) {
                    report.updateTestLog("selectPayeeCountryAndCurrency", "Correct Currency populated upon country selection, Expected '" + newCurrency + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("selectPayeeCountryAndCurrency", "Correct Currency is NOT populated upon country selection '" + newCurrency + "', Actual ''" + getText(getObject("AddPayee.lblDefaultCurrency")) + "", Status_CRAFT.FAIL);
                }
            } else {
                if (!dataTable.getData("General_Data", "PayeeCurrency").equals("")) {
                    click(getObject("AddPayee.lstCurrency"));
                    driver.findElement(By.xpath("//select[contains(@name,'CURRENCIES')]/following-sibling::div/ul/li[contains(.,'" + dataTable.getData("General_Data", "PayeeCurrency") + "')]")).click();
                    report.updateTestLog("selectPayeeCountryAndCurrency", "Payee currency selected '" + dataTable.getData("General_Data", "PayeeCurrency") + "'", Status_CRAFT.DONE);
                    waitForPageLoaded();
                    Thread.sleep(2000);
                }
            }
        }
    }

    public void enterPayeeDetails() throws Exception {
        //validating section title Payee details
        if (isElementDisplayed(getObject("AddPayee.hdrPayeeDetails"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Section heading 'Payee details' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Section heading 'Payee details' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //Payee Name
        if (isElementDisplayed(getObject("AddPayee.lblName"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Name' is displayed on Add Payee Page", Status_CRAFT.PASS);
            if (!dataTable.getData("General_Data", "FirstName").equals("")) {
                String strPayeeName = dataTable.getData("General_Data", "FirstName");
                if (!strPayeeName.equals("")) {
                    sendKeys(getObject("AddPayee.txtName"), strPayeeName, "Name entered is '" + strPayeeName + "'");
                }
            } else {
                String strPayeeName = dataTable.getData("General_Data", "PayeeName");
                if (!strPayeeName.equals("")) {
                    sendKeys(getObject("AddPayee.txtName"), strPayeeName, "Name entered is '" + strPayeeName + "'");
                }
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Name' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //Address 1
        if (isElementDisplayed(getObject("AddPayee.lblAddress1"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Address 1' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strAddress1 = dataTable.getData("General_Data", "Address1");
            if (!strAddress1.equals("")) {
                sendKeys(getObject("AddPayee.txtAddress1"), strAddress1, "Address 1 entered is '" + strAddress1 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Address 1' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Address 2'
        if (isElementDisplayed(getObject("AddPayee.lblAddress2"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Address 2' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strAddress2 = dataTable.getData("General_Data", "Address2");
            if (!strAddress2.equals("")) {
                sendKeys(getObject("AddPayee.txtAddress2"), strAddress2, "Address 2 entered is '" + strAddress2 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Address 2' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Country of residence'
        if (isElementDisplayed(getObject("AddPayee.lblResidenceCountry"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Country of residence' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strResidenceCountry = dataTable.getData("General_Data", "ResidenceCountry");
            if (!strResidenceCountry.equals("")) {
                sendKeys(getObject("AddPayee.txtResidenceCountry"), strResidenceCountry, "Country of residence entered is '" + strResidenceCountry + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Country of residence' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

    }

    public void operationOnPayeeDetailsPage() throws Exception {
        if (isElementDisplayed(getObject("AddPayee.GoBack"), 5)) {
            report.updateTestLog("operationOnPayeeDetailsPage", "Button 'Go back' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("operationOnPayeeDetailsPage", "Button 'Go back' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.Continue"), 5)) {
            report.updateTestLog("operationOnPayeeDetailsPage", "Button 'Continue' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("operationOnPayeeDetailsPage", "Button 'Continue' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

       /* if(!deviceType.equals("w.")){
            driver.hideKeyboard();
        }*/

        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":

                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.Continue")));
                    waitForJQueryLoad();
                    waitForPageLoaded();
                    if(deviceType.equalsIgnoreCase("web")){
                        click(getObject("AddPayee.Continue"), "Clicked on 'Continue', Add Payee Page");
                    }else{
                        clickJS(getObject("AddPayee.Continue"), "Clicked on 'Continue', Add Payee Page");
                    }

                    break;
                case "GO BACK":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.GoBack")));
                    Thread.sleep(2000);
                    click(getObject("ReviewGoBack"), "Clicked on 'Go back', Add Payee Page");
                    if (isElementDisplayed(getObject("xpath~//button[@title='Add new Person']"), 5)) {
                        report.updateTestLog("enterPayeeDetailsFormA", "Page navigated to Manage Payee Page upon clicking 'Go back'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("operationOnPayeeDetailsPage", "Page does not navigated to Manage Payee Page upon clicking 'Go back'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("operationOnPayeeDetailsPage", "Please provide the valid operation on Add Payee, Continue or Go Back", Status_CRAFT.FAIL);
            }
        }
    }

    public void enterPayeeDetailsFormA() throws Exception {
        //enter payee details
        enterPayeeDetails();
        //validating section title Payee bank details
        if (isElementDisplayed(getObject("AddPayee.hdrPayeeBankDetails"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Section heading 'Payee bank details' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Section heading 'Payee bank details' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'BIC'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankBIC"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'BIC' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
            click(getObject("xpath~//h3[text()='Payee bank details']"));
            if(!isMobile){
                Actions action = new Actions((WebDriver) driver.getWebDriver());
                action.moveToElement(driver.findElement(getObject("AddPayee.lblPayeeBankBIC"))).clickAndHold().build().perform();
                String strExpectedBICToolTip = "Bank Identification Code (BIC) is the SWIFT address assigned to the payee’s bank. A BIC consists of eight or eleven characters.";
                String strUIIBICToolTip = getText(getObject("AddPayee.elmPayeeBankBICTooltip"));
                if (strUIIBICToolTip.equals(strExpectedBICToolTip)) {
                    report.updateTestLog("enterPayeeDetailsFormA", "BIC tooltip is correctly displayed on Add Payee Page, Expected '" + strExpectedBICToolTip + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("enterPayeeDetailsFormA", "BIC tooltip is NOT correctly displayed on Add Payee Page, Actual '" + strUIIBICToolTip + "'", Status_CRAFT.FAIL);
                }
            }else{
                TouchActions touchActions = new TouchActions((WebDriver) driver.getWebDriver());
                touchActions.singleTap(driver.findElement(getObject("AddPayee.lblPayeeBankBIC")));
                String strExpectedBICToolTip = "Bank Identification Code (BIC) is the SWIFT address assigned to the payee’s bank. A BIC consists of eight or eleven characters.";
                String strUIIBICToolTip = getText(getObject("AddPayee.elmPayeeBankBICTooltip"));
                if (strUIIBICToolTip.equals(strExpectedBICToolTip)) {
                    report.updateTestLog("enterPayeeDetailsFormA", "BIC tooltip is correctly displayed on Add Payee Page, Expected '" + strExpectedBICToolTip + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("enterPayeeDetailsFormA", "BIC tooltip is NOT correctly displayed on Add Payee Page, Actual '" + strUIIBICToolTip + "'", Status_CRAFT.FAIL);
                }
            }
            //enter the BIC
            String strPayeeBIC = dataTable.getData("General_Data", "BIC_Number");
            if (!strPayeeBIC.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankBic"), strPayeeBIC, "BIC entered is '" + strPayeeBIC + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'BIC' or its tooltip ICON is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }
        //validating label 'IBAN'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankIBAN"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'IBAN' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
            waitForJQueryLoad();
            click(getObject("xpath~//h3[text()='Payee bank details']"));
                      if(!isMobile){
                Actions action = new Actions((WebDriver) driver.getWebDriver());
                action.moveToElement(driver.findElement(getObject("AddPayee.lblPayeeBankIBAN"))).clickAndHold().build().perform();
                String strUIIIBANToolTip = getText(getObject("AddPayee.elmPayeeBankIBANToolTip"));
                String strExpecteIBANToolTip = "The International Bank Account Number of the payee quoted in international format that identifies the individual account as being in a specific financial institution in a particular country.";
                if (strUIIIBANToolTip.equals(strExpecteIBANToolTip)) {
                    report.updateTestLog("enterPayeeDetailsFormA", "IBAN tooltip is correctly displayed on Add Payee Page, Expected '" + strExpecteIBANToolTip + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("enterPayeeDetailsFormA", "IBAN tooltip is NOT correctly displayed on Add Payee Page, Actual '" + strUIIIBANToolTip + "'", Status_CRAFT.FAIL);
                }
            }else{
                TouchActions touchActions = new TouchActions((WebDriver) driver.getWebDriver());
                touchActions.singleTap(driver.findElement(getObject("AddPayee.lblPayeeBankIBAN")));
                String strExpecteIBANToolTip = "The International Bank Account Number of the payee quoted in international format that identifies the individual account as being in a specific financial institution in a particular country.";
                String strUIIIBANToolTip = getText(getObject("AddPayee.elmPayeeBankIBANToolTip"));
            }

            //enter the IBAN
            String strPayeeIBAN = dataTable.getData("General_Data", "IBAN_Number");
            if (!strPayeeIBAN.equals("")) {
//                driver.findElement(getObject("AddPayee.txtPayeeBankIBAN")).sendKeys(strPayeeIBAN);
                sendKeys(getObject("AddPayee.txtPayeeBankIBAN"), strPayeeIBAN, "IBAN entered is '" + strPayeeIBAN + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'IBAN' or its tooltip ICON is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }



        //Bank Name
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankName"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Bank name' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Bank name' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }
        //This will be determined by the BIC provided.
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankNameEditabletext"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'This will be determined by the BIC provided.' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'This will be determined by the BIC provided.' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank Address 1'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankAddress1"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Bank address 1' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeebankAddress1 = dataTable.getData("General_Data", "PayeeBankAddress1");
            if (!strPayeebankAddress1.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankAddress1"), strPayeebankAddress1, "Bank address 1 entered is '" + strPayeebankAddress1 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Bank address 1' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank address 2 (optional)'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankAddress2Optional"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Bank address 2 (optional)' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeeBankAddress2 = dataTable.getData("General_Data", "PayeeBankAddress2");
            if (!strPayeeBankAddress2.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankAddress2"), strPayeeBankAddress2, "Bank address 2 (optional) entered is '" + strPayeeBankAddress2 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Bank address 2 (optional)' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank city'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankCity"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Bank city' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeebankCity = dataTable.getData("General_Data", "PayeeBankCity");
            if (!strPayeebankCity.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankCity"), strPayeebankCity, "Bank city entered is '" + strPayeebankCity + "'");
                driver.findElement(getObject("AddPayee.lblPayeeBankCity")).click();
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormA", "Label 'Bank city' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //go back & continue button & operation
        operationOnPayeeDetailsPage();

    }

    /* Function to verify Error message for maximum payee addition  */
    public void verifyErroMessageMaximumPayee() throws Exception {
        String ActualError = getText(getObject("AddPayee.maximumPayeeError"));
        String ExpectedError = "This payee cannot be added as it already exists on your profile.";
        if (ExpectedError.equalsIgnoreCase(ActualError)) {
            report.updateTestLog("VerifyErrorMessage", "Error message displayed is correctly", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyErrorMessage", "Error message is not displayed correctly", Status_CRAFT.FAIL);
        }
    }

    /*CFPUR-1833: Payments landing page*/
    /*function to click on payments page*/
    public void clickPaymentsPage() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 10)) {
            click(getObject(deviceType() + "home.tabPayments"), "Select 'Payments Tab'");
            report.updateTestLog("selectPaymentOption", "Payment tab clicked on Home Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPaymentOption", "Payment tab not present on Home Page", Status_CRAFT.FAIL);
        }
    }

    /*function to Verify the data on Payments Page*/

    public void verifyPaymentsPageDetails(String sectionElement) throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "payments.headerPayments"), 5)) {
            report.updateTestLog("verifyPaymentsPageDetails", "Payment header is displayed correctly", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyPaymentsPageDetails", "Payment header is not displayed correctly", Status_CRAFT.FAIL);
        }
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("xpath~//*[@name='C1____6E1FF5E40B512908 FormButton 61']"), 5)) {
                if (isElementDisplayed(getObject("payments.bckArrow"), 5)) {
                    report.updateTestLog("verifyPaymentsPageDetails", "Back arrow icon is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyPaymentsPageDetails", "Back arrow icon is NOT displayed", Status_CRAFT.FAIL);
                }
            }
        }
    }

    public void verifyTransfer() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "payments.iconTransfer"), 5)) {
            report.updateTestLog("verifyTransfer", "Transfer icon is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyTransfer", "Transfer icon is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    public void verifySendMoney() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "payments.iconSendMoney"), 5)) {
            report.updateTestLog("verifySendMoney", "Send Money icon is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifySendMoney", "Send Money icon is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    public void enterPayeeDetailsFormB() throws Exception {
        //enter payee details
        enterPayeeDetails();
        //validating section title Payee bank details
        if (isElementDisplayed(getObject("AddPayee.hdrPayeeBankDetails"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormB", "Section heading 'Payee bank details' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormB", "Section heading 'Payee bank details' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }
        //validating label 'BIC'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankBIC"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'BIC' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
            Actions action = new Actions((WebDriver) driver.getWebDriver());
            action.moveToElement(driver.findElement(getObject("AddPayee.lblPayeeBankBIC"))).clickAndHold().build().perform();
            String strExpectedBICToolTip = "Bank Identification Code (BIC) is the SWIFT address assigned to the payee’s bank. A BIC consists of eight or eleven characters.";
            String strUIIBICToolTip = getText(getObject("AddPayee.elmPayeeBankBICTooltip"));
/*
            if (strUIIBICToolTip.equals(strExpectedBICToolTip)) {
                report.updateTestLog("enterPayeeDetailsFormB", "BIC tooltip is correctly displayed on Add Payee Page, Expected '" + strExpectedBICToolTip + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("enterPayeeDetailsFormB", "BIC tooltip is NOT correctly displayed on Add Payee Page, Actual '" + strUIIBICToolTip + "'", Status_CRAFT.FAIL);
            }
            */
            //enter the BIC
            click(getObject("AddPayee.txtPayeeBankBic"),"Bicpayee text box clicked");
            String strPayeeBIC = dataTable.getData("General_Data", "BIC_Number");
            if (!strPayeeBIC.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankBic"), strPayeeBIC, "BIC entered is '" + strPayeeBIC + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'BIC' or its tooltip ICON is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating National sort code
        if (isElementDisplayed(getObject("AddPayee.lblNSC"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'National sort code' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
            Actions action = new Actions((WebDriver) driver.getWebDriver());
            action.moveToElement(driver.findElement(getObject("AddPayee.lblNSC"))).clickAndHold().build().perform();
            //    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            //    js.executeScript("arguments[0].scrollIntoView();",
            //          driver.findElement(getObject("AddPayee.lblNSC")));
            //    clickJS(getObject("AddPayee.lblNSC"),"NSC Icon");
            String strExpectedNSCToolTip = "This is the code used to identify your payee’s bank branch.";
            String strUIINSCToolTip = getText(getObject("AddPayee.elmPayeeBankNSCTooltip"));
            if (strUIINSCToolTip.equals(strExpectedNSCToolTip)) {
                report.updateTestLog("enterPayeeDetailsFormB", "NSC tooltip is correctly displayed on Add Payee Page, Expected '" + strExpectedNSCToolTip + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("enterPayeeDetailsFormB", "NSC tooltip is NOT correctly displayed on Add Payee Page, Actual '" + strUIINSCToolTip + "'", Status_CRAFT.FAIL);
            }
            //enter the BIC
            String strPayeeNSC = dataTable.getData("General_Data", "NationalSortCode");
            if (!strPayeeNSC.equals("")) {
                sendKeys(getObject("AddPayee.txtNSC"), strPayeeNSC, "BIC entered is '" + strPayeeNSC + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'National sort code' or its tooltip ICON is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating Account Number
        if (isElementDisplayed(getObject("AddPayee.lblAccountnumber"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'Account number' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
            //enter the Account number
            String strPayeeAccNum = dataTable.getData("General_Data", "AccountNumber");
            if (!strPayeeAccNum.equals("")) {
//                driver.findElement(getObject("AddPayee.txtPayeeBankIBAN")).sendKeys(strPayeeIBAN);
                sendKeys(getObject("AddPayee.txtAccNumber"), strPayeeAccNum, "Account number entered is '" + strPayeeAccNum + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'Account number' or  is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //Bank Name
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankName"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'Bank name' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormB", "Bank name' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //This will be determined by the BIC provided.
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankNameEditabletext"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'This will be determined by the BIC provided.' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'This will be determined by the BIC provided.' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank Address 1'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankAddress1"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'Bank address 1' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeebankAddress1 = dataTable.getData("General_Data", "PayeeBankAddress1");
            if (!strPayeebankAddress1.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankAddress1"), strPayeebankAddress1, "Bank address 1 entered is '" + strPayeebankAddress1 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'Bank address 1' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank address 2 (optional)'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankAddress2Optional"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'Bank address 2 (optional)' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeeBankAddress2 = dataTable.getData("General_Data", "PayeeBankAddress2");
            if (!strPayeeBankAddress2.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankAddress2"), strPayeeBankAddress2, "Bank address 2 (optional) entered is '" + strPayeeBankAddress2 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'Bank address 2 (optional)' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank city'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankCity"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'Bank city' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeebankCity = dataTable.getData("General_Data", "PayeeBankCity");
            if (!strPayeebankCity.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankCity"), strPayeebankCity, "Bank city entered is '" + strPayeebankCity + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormB", "Label 'Bank city' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //go back & continue button & operation
        operationOnPayeeDetailsPage();

    }

    public void enterPayeeDetailsFormC() throws Exception {
        //enter payee details
        enterPayeeDetails();

        //validating section title Payee bank details
        if (isElementDisplayed(getObject("AddPayee.hdrPayeeBankDetails"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormC", "Section heading 'Payee bank details' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormC", "Section heading 'Payee bank details' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'BIC'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankBIC"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'BIC' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
            //enter the BIC
            String strPayeeBIC = dataTable.getData("General_Data", "BIC_Number");
            if (!strPayeeBIC.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankBic"), strPayeeBIC, "BIC entered is '" + strPayeeBIC + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'BIC' or its tooltip ICON is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating National sort code
        if (isElementDisplayed(getObject("AddPayee.lblNSCoptional"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'National sort code (optional)' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
            //enter the NSC
            String strPayeeNSC = dataTable.getData("General_Data", "NationalSortCode");
            if (!strPayeeNSC.equals("")) {
                sendKeys(getObject("AddPayee.txtNSC"), strPayeeNSC, "National sort code entered is '" + strPayeeNSC + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'National sort code(optional)' or its tooltip ICON is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating Account Number
        if (isElementDisplayed(getObject("AddPayee.lblAccountnumber"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'Account number' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
            //enter the Account number
            String strPayeeAccNum = dataTable.getData("General_Data", "AccountNumber");
            if (!strPayeeAccNum.equals("")) {
                sendKeys(getObject("AddPayee.txtAccNumber"), strPayeeAccNum, "Account number entered is '" + strPayeeAccNum + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'Account number' or  is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //Bank Name
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankName"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'Bank name' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormC", "Bank name' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //This will be determined by the BIC provided.
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankNameEditabletext"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'This will be determined by the BIC provided.' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'This will be determined by the BIC provided.' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank Address 1'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankAddress1"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'Bank address 1' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeebankAddress1 = dataTable.getData("General_Data", "PayeeBankAddress1");
            if (!strPayeebankAddress1.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankAddress1"), strPayeebankAddress1, "Bank address 1 entered is '" + strPayeebankAddress1 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'Bank address 1' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank address 2 (optional)'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankAddress2Optional"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'Bank address 2 (optional)' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeeBankAddress2 = dataTable.getData("General_Data", "PayeeBankAddress2");
            if (!strPayeeBankAddress2.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankAddress2"), strPayeeBankAddress2, "Bank address 2 (optional) entered is '" + strPayeeBankAddress2 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'Bank address 2 (optional)' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank city'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankCity"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'Bank city' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeebankCity = dataTable.getData("General_Data", "PayeeBankCity");
            if (!strPayeebankCity.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankCity"), strPayeebankCity, "Bank city entered is '" + strPayeebankCity + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormC", "Label 'Bank city' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //go back & continue button & operation
        operationOnPayeeDetailsPage();

    }

    public void enterPayeeDetailsFormD() throws Exception {
        //enter payee details
        enterPayeeDetails();

        //validating section title Payee bank details
        if (isElementDisplayed(getObject("AddPayee.hdrPayeeBankDetails"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormD", "Section heading 'Payee bank details' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormD", "Section heading 'Payee bank details' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'BIC'
        if (isElementDisplayed(getObject("AddPayee.lblBICoptional"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'BIC' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
            //enter the BIC
            String strPayeeBIC = dataTable.getData("General_Data", "BIC_Number");
            if (!strPayeeBIC.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankBic"), strPayeeBIC, "BIC entered is '" + strPayeeBIC + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'BIC' or its tooltip ICON is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating ABA code
        if (isElementDisplayed(getObject("AddPayee.lblABAcode"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'ABA code' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
//            enter the ABA code
            String strPayeeABA = dataTable.getData("General_Data", "ABACode");
            if (!strPayeeABA.equals("")) {
                sendKeys(getObject("AddPayee.txtABACode"), strPayeeABA, "BIC entered is '" + strPayeeABA + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'ABA code' or its tooltip ICON is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }
        //validating Account Number
        if (isElementDisplayed(getObject("AddPayee.lblAccountnumber"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'Account number' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
            //enter the Account number
            String strPayeeAccNum = dataTable.getData("General_Data", "AccountNumber");
            if (!strPayeeAccNum.equals("")) {
                sendKeys(getObject("AddPayee.txtAccNumber"), strPayeeAccNum, "Account number entered is '" + strPayeeAccNum + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'Account number' or  is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //Bank Name
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankName"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'Bank name' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeeBankName = dataTable.getData("General_Data", "BankName");
            if (!strPayeeBankName.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankName"), strPayeeBankName, "Bank Name entered is '" + strPayeeBankName + "'");
            }

        } else {
            report.updateTestLog("enterPayeeDetailsFormD", "Bank name' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }


        //validating label 'Bank Address 1'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankAddress1"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'Bank address 1' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeebankAddress1 = dataTable.getData("General_Data", "PayeeBankAddress1");
            if (!strPayeebankAddress1.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankAddress1"), strPayeebankAddress1, "Bank address 1 entered is '" + strPayeebankAddress1 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'Bank address 1' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank address 2 (optional)'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankAddress2Optional"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'Bank address 2 (optional)' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeeBankAddress2 = dataTable.getData("General_Data", "PayeeBankAddress2");
            if (!strPayeeBankAddress2.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankAddress2"), strPayeeBankAddress2, "Bank address 2 (optional) entered is '" + strPayeeBankAddress2 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'Bank address 2 (optional)' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank city'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankCity"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'Bank city' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeebankCity = dataTable.getData("General_Data", "PayeeBankCity");
            if (!strPayeebankCity.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankCity"), strPayeebankCity, "Bank city entered is '" + strPayeebankCity + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormD", "Label 'Bank city' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //go back & continue button & operation
        operationOnPayeeDetailsPage();

    }

    public void enterPayeeDetailsFormE() throws Exception {
        //enter payee details
        enterPayeeDetails();

        //validating section title Payee bank details
        if (isElementDisplayed(getObject("AddPayee.hdrPayeeBankDetails"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormE", "Section heading 'Payee bank details' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormE", "Section heading 'Payee bank details' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'BIC'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankBIC"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'BIC' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);

            //enter the BIC
            String strPayeeBIC = dataTable.getData("General_Data", "BIC_Number");
            if (!strPayeeBIC.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankBic"), strPayeeBIC, "BIC entered is '" + strPayeeBIC + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'BIC' or its tooltip ICON is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating National sort code
        if (isElementDisplayed(getObject("AddPayee.lblCLABE"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'CLABE' is displayed along with Tooltip Icon on Add Payee Page", Status_CRAFT.PASS);
            //enter the CLABE
            String strPayeeCLABE = dataTable.getData("General_Data", "CLABE");
            if (!strPayeeCLABE.equals("")) {
                sendKeys(getObject("AddPayee.txtCLABE"), strPayeeCLABE, "CLABE entered is '" + strPayeeCLABE + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'CLABE' or its tooltip ICON is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //Bank Name
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankName"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'Bank name' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormE", "Bank name' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //This will be determined by the BIC provided.
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankNameEditabletext"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'This will be determined by the BIC provided.' is displayed on Add Payee Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'This will be determined by the BIC provided.' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank Address 1'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankAddress1"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'Bank address 1' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeebankAddress1 = dataTable.getData("General_Data", "PayeeBankAddress1");
            if (!strPayeebankAddress1.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankAddress1"), strPayeebankAddress1, "Bank address 1 entered is '" + strPayeebankAddress1 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'Bank address 1' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank address 2 (optional)'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankAddress2Optional"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'Bank address 2 (optional)' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeeBankAddress2 = dataTable.getData("General_Data", "PayeeBankAddress2");
            if (!strPayeeBankAddress2.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankAddress2"), strPayeeBankAddress2, "Bank address 2 (optional) entered is '" + strPayeeBankAddress2 + "'");
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'Bank address 2 (optional)' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //validating label 'Bank city'
        if (isElementDisplayed(getObject("AddPayee.lblPayeeBankCity"), 5)) {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'Bank city' is displayed on Add Payee Page", Status_CRAFT.PASS);
            String strPayeebankCity = dataTable.getData("General_Data", "PayeeBankCity");
            if (!strPayeebankCity.equals("")) {
                sendKeys(getObject("AddPayee.txtPayeeBankCity"), strPayeebankCity, "Bank city entered is '" + strPayeebankCity + "'");
                click(getObject("AddPayee.lblPayeeBankCity"));
            }
        } else {
            report.updateTestLog("enterPayeeDetailsFormE", "Label 'Bank city' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }

        //go back & continue button & operation
        operationOnPayeeDetailsPage();
    }

    public void AddPayeeReviewPage() throws Exception {
        //validating page title 'Add Payee'
        if (isElementDisplayed(getObject(deviceType() + "AddPayee.hdrPageTitle"), 5)) {
            report.updateTestLog("selectPayeeCountryAndCurrency", "Page Title 'Add Payee' is displayed on Review Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPayeeCountryAndCurrency", "Page Title 'Add Payee' is NOT displayed on Review Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("ManageStandingOrder.hdrReview"), 5)) {
            report.updateTestLog("AddPayeeReviewPage", "Section Header 'Review' is displayed on Review Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("AddPayeeReviewPage", "Section Header 'Review' is NOT displayed on Review Page", Status_CRAFT.FAIL);
        }

        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {
            if (!obj.getValue().equals("NA")) {
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[text()='" + obj.getValue() + "']"), 5)) {
                    report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("AddPayeeReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
                }
            } else {
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
                    report.updateTestLog("AddPayeeReviewPage", "'" + obj.getKey() + "' is displayed on review page for blank input", Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("AddPayeeReviewPage", "'" + obj.getKey() + "' is Not displayed on review page for blank input", Status_CRAFT.PASS);
                }
            }
        }

        JavascriptExecutor jp = (JavascriptExecutor) driver.getWebDriver();
        jp.executeScript("window.scrollTo(0, document.body.scrollHeight);", driver.findElement(getObject("xpath~//a[@id='C5__BUT_E50DFFB0F8190DA23699462']/descendant::span[text()='Go back']")));
        if (isElementDisplayed(getObject("xpath~//a[@id='C5__BUT_E50DFFB0F8190DA23699462']/descendant::span[text()='Go back']"), 5)) {
            report.updateTestLog("AddPayeeReviewPage", "'Go back' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("AddPayeeReviewPage", "'Go back' button is NOT displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("xpath~//a/descendant::*[text()='Continue']"), 5)) {
            report.updateTestLog("AddPayeeReviewPage", "'Continue' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("AddPayeeReviewPage", "'Continue' button is NOT displayed", Status_CRAFT.FAIL);
        }

        String strOperation = dataTable.getData("General_Data", "OperationOnReviewPage");
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']")));
        Thread.sleep(2000);
        waitForElementToClickable(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']"), 5);
        clickJS(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
        if (strOperation.equalsIgnoreCase("Go back")) {
            if (isElementDisplayed(getObject(deviceType() + "AddPayee.hdrPageTitle "), 5)) {
                report.updateTestLog("AddPayeeReviewPage", "Page successfully navigated to Add Payee Details page, after clicking 'Go back'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("AddPayeeReviewPage", "Page did not navigate to Add Payee Details page, after clicking 'Go back'", Status_CRAFT.FAIL);
            }
        }
    }

    public void enterNewPin() throws Exception {
        //validating page header
        String strPageHeader = dataTable.getData("General_Data", "PageHeader");
        if (deviceType.equalsIgnoreCase("Web")) {

            if(strPageHeader.trim().equals("Add Payee")||strPageHeader.trim().equals("Add a payee")){strPageHeader="Add payee";}

            if (isElementDisplayed(getObject("xpath~//h1[text()='" + strPageHeader + "'][not(contains(@class,'boi-mobile-header'))]"), 5)) {
                report.updateTestLog("enterNewPin", "Page Title '" + strPageHeader + "' is displayed enter pin Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("enterNewPin", "Page Title '" + strPageHeader + "' is NOT displayed on enter pin Page", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject("xpath~//h1[text()='" + strPageHeader + "'][contains(@class,'boi-mobile-header')]"), 5)) {
                report.updateTestLog("enterNewPin", "Page Title '" + strPageHeader + "' is displayed enter pin Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("enterNewPin", "Page Title '" + strPageHeader + "' is NOT displayed on enter pin Page", Status_CRAFT.FAIL);
            }
        }

        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("ManageStandingOrder.txtPinObjects"));

        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                report.updateTestLog("enterNewPin", "Entered pin :" + arrPin[i], Status_CRAFT.DONE);
            }
        }
        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":
                    if(isMobile){
                        try{
                            driver.context("NATIVE_APP");
                            MobileElement el1 = (MobileElement) driver.findElementByXPath("//android.view.View[3]/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.Button");
                            (new TouchAction(driver.getAppiumDriver())).tap(PointOption.point(el1.getCenter().getX(),el1.getCenter().getY())).perform();
                            report.updateTestLog("PushNotification_Acccept", "Push Notification Accepted",Status_CRAFT.PASS);
                        }catch(Exception e){
                            System.out.print("Exception is there ::"+e.getMessage());
                        }
                        Thread.sleep(1000);
                    }else{
                        clickJS(getObject("ManageStandingOrder.btnConfirm"),"Clicked on 'Confirm', Enter Pin Page");
                    }
                    break;
                case "GO BACK":
                    clickJS(getObject("ManageStandingOrder.btnGoback"), "Clicked on 'Go back', Enter Pin  Page");
                    break;
                case "Continue":
                    clickJS(getObject("ManageStandingOrder.btnConfirm"), "Clicked on 'Confirm', Enter Pin Page");
                    break;
                default:
                    report.updateTestLog("enterNewPin", "Please provide the valid operation Continue/Go back", Status_CRAFT.FAIL);
            }
        //    Thread.sleep(3000);
        }
    }

    public void AddPayeeConfirmationPage() throws Exception {
        //validating page title 'Add Payee'
        Thread.sleep(2000);
        if (isElementDisplayed(getObject(deviceType() + "AddPayee.hdrPageTitlePIN"), 5)) {
            report.updateTestLog("AddPayeeConfirmationPage", "Page Title 'Add Payee' is displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("AddPayeeConfirmationPage", "Page Title 'Add Payee' is NOT displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("StandingOrder.iconConfirmation"), 5)) {
            report.updateTestLog("CancelStandingOrderConfirmationPage", "Success Icon 'Tick Mark' is displayed on Cancelled Standing Order Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("CancelStandingOrderConfirmationPage", "Success Icon 'Tick Mark' is NOT displayed on Cancelled Standing Order Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.txtPayeeAdded"), 5)) {
            report.updateTestLog("AddPayeeConfirmationPage", "Text message 'Payee Added' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("AddPayeeConfirmationPage", "Text message 'Payee Added' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.btnSendMoney"), 5)) {
            report.updateTestLog("AddPayeeConfirmationPage", "Button 'Send Money' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("AddPayeeConfirmationPage", "Button 'Send Money' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.btnBackToHome"), 5)) {
            report.updateTestLog("AddPayeeConfirmationPage", "Link Button 'Back to home' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("AddPayeeConfirmationPage", "Link Button 'Back to home' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("xpath~//div[@class='ecDIB  tc-center-align boi-force-left-align-xs boi_para col-full boi-mb-15']/div"), 5)) {
            report.updateTestLog("AddPayeeConfirmationPage", "Time Stamp with message 'Time of request' is displayed on Confirmation page", Status_CRAFT.PASS);

            String strRequestSubmitted = getText(getObject("xpath~//div[@class='ecDIB  tc-center-align boi-force-left-align-xs boi_para col-full boi-mb-15']/div"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("AddPayeeConfirmationPage", "Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("AddPayeeConfirmationPage", "Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("AddPayeeConfirmationPage", "Time Stamp with message 'Time of request' is NOT displayed on Cancelled Standing Order Confirmation page", Status_CRAFT.FAIL);
        }

        //Validating Navigation/functionality of the buttons
        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("back to my payees")) {
            if (deviceType.equalsIgnoreCase("Web")) {
                click(getObject("AddPayee.btnBackToMyPayees"), "Clicked on 'back to my payees'");
            } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
                clickJS(getObject("AddPayee.btnBackToMyPayees"), "Clicked on 'back to my payees'");
            }
            if (isElementDisplayed(getObject("xpath~//button[@title='Add person']"), 5)) {
                report.updateTestLog("AddPayeeConfirmationPage", "Upon clicking 'Back to home' ,successfully Navigated to My Payees screen from Add Payee Confirmation Page", Status_CRAFT.PASS);
                if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to home")) {
                    click(getObject("AddPayee.btnBackToHome"), "Clicked on 'Back to home'");
                    if (isElementDisplayed(getObject(deviceType() + "home.titleMyAccounts"), 5)) {
                        report.updateTestLog("AddPayeeConfirmationPage", "Upon clicking 'Back to home' ,successfully Navigated to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("AddPayeeConfirmationPage", "Upon clicking 'Back to home' ,navigation unsuccessful to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.FAIL);
                    }
                }

                if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Send Money")) {
                    click(getObject("AddPayee.btnSendMoney"), "Clicked on 'Send Money'");
                    if (isElementDisplayed(getObject("SendMoney.hdrtxt"), 5)) {
                        report.updateTestLog("AddPayeeConfirmationPage", "Upon clicking 'Send Money' ,successfully Navigated to Send Money screen from Add Payee Confirmation Page", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("AddPayeeConfirmationPage", "Upon clicking 'Send Money' ,navigation unsuccessful to Send Money screen from Add Payee Confirmation Page", Status_CRAFT.FAIL);
                    }
                }

            }
        }
    }

    public void errorMessageValidations() throws Exception {
        waitForPageLoaded();
        clickJS(getObject("AddBillPayee.btnContinue") ,"Click : Continue ");
        Thread.sleep(10000);
        String errorMessage[] = dataTable.getData("General_Data", "ErrorText").split("::");
        for (int i = 0; i < errorMessage.length; i++) {
            if (!(errorMessage[i].equals("Sorry, you can't use some of those characters here."))) {
                if (isElementDisplayed(getObject("xpath~//*[contains(@class,'tc-error-position')][contains(text(),'" + errorMessage[i] + "')]"), 5)) {
                    report.updateTestLog("errorMessageValidations", "Error Message '" + errorMessage[i] + "' is displayed on Add Payee Page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("errorMessageValidations", "Error Message '" + errorMessage[i] + "' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
                }
            } else {
                String str = "Sorry, you can't use some of those characters here.";
                if (isElementDisplayed(getObject("xpath~//*[contains(@class,'tc-error-position')][@style='visibility: visible; display: inline;'][contains(text(),'Sorry, you can')]"), 5)) {
                    if (getText(getObject("xpath~//*[contains(@class,'tc-error-position')][@style='visibility: visible; display: inline;'][contains(text(),'Sorry, you can')]")).contains(str)) {
                        report.updateTestLog("errorMessageValidations", "Error Message '" + errorMessage[i] + "' is displayed on Add Payee Page", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("errorMessageValidations", "Error Message '" + errorMessage[i] + "' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
                    }
                } else {
                    report.updateTestLog("errorMessageValidations", "Error Message '" + errorMessage[i] + "' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
                }

            }
        }
    }

    /**
     * Mobile App Automation Method : To Navigate to the Add Bill Payee Page
     * Update on     Updated by     Reason
     * 27/06/2019    C966119        for mobile Automation
     */
    public void addBillPayeeNavigation() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "Payments.btnAddBill"), 5)) {
            clickJS(getObject(deviceType() + "Payments.btnAddBill"), " Clicking  : Add Bill Payee");
            report.updateTestLog("addBillPayeeDetails", "Page Title is Displayed for Add Bill Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Manage Payees:", "Add Bill Button is not displayed on Screen", Status_CRAFT.FAIL);
        }
    }


    public void checkAddBillPayeeDetailsPage() throws Exception {
        //Validate the Enter Bill Details page

        if (isElementDisplayed(getObject(deviceType() + "AddBillPayee.lblAddBill"), 5)) {
            report.updateTestLog("addBillPayeeDetails", "Page Title is Displayed for 'Add Bill' Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addBillPayeeDetails", "Page Title is Missing for Add Bill Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.lblBillDetails"), 1)) {
            report.updateTestLog("addBillPayeeDetails", "Bill Section 'Bill details' Title is Displayed on Add Bill Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addBillPayeeDetails", "Bill Section 'Bill details' Title is not Displayed on Add Bill Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.lblBillName"), 1)) {
            report.updateTestLog("addBillPayeeDetails", "Bill name label displayed before dropdown on Add bill page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addBillPayeeDetails", "Bill name label is not displayed before dropdown on Add bill page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.lstBillPayee"), 1)) {
            report.updateTestLog("addBillPayeeDetails", "Dropdown of Bill Payee is displayed on Add Bill Payee Details Page", Status_CRAFT.PASS);
            if (!dataTable.getData("General_Data", "BillerName").equals("")) {
                clickJS(getObject("AddBillPayee.lstBillPayee"), "Selecting the Bill Payee");
                Thread.sleep(2000);
                clickJS(getObject(("xpath~//*[contains(@class,'aria_exp_wrapper')]/ul/li[contains(.,'" + dataTable.getData("General_Data", "BillerName") + "')]")), "Selecting the Bill Payee");
                report.updateTestLog("addBillPayeeDetails", "Bill Selected '" + dataTable.getData("General_Data", "BillerName") + "'", Status_CRAFT.DONE);
                waitForPageLoaded();
            } else {
                report.updateTestLog("addBillPayeeDetails", "Billers are not listed in the dropdown", Status_CRAFT.DONE);
            }
        } else {
            report.updateTestLog("addBillPayeeDetails", "Dropdown of Bill Payee is NOT displayed on Add Bill Payee Details Page", Status_CRAFT.FAIL);
        }

        if(isElementDisplayed(getObject("AddBillPayee.lblBillNotAvailable"),5)) {
            report.updateTestLog("addBillPayeeDetails", "Bill not in the list? is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addBillPayeeDetails", "Bill not in the list label is not available", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.lnkAddItHere"), 1)) {
            report.updateTestLog("addBillPayeeDetails", "Add it here link is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addBillPayeeDetails", "Add it here link is not displayed", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.lblReference"), 1)) {
            report.updateTestLog("addBillPayeeDetails", "Reference Number Label is displayed", Status_CRAFT.PASS);
            sendKeys(getObject("AddBillPayee.txtReference"), dataTable.getData("General_Data", "BillReference"));
        } else {
            report.updateTestLog("addBillPayeeDetails", "Reference Number label is not displayed", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.lblEnterBillGuidelineText"), 5)) {
            report.updateTestLog("addBillPayeeDetails", "Please enter your bill account number. Label is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addBillPayeeDetails", "Please enter your bill account number. Label is not displayed", Status_CRAFT.FAIL);
        }

        String Jurisdiction = dataTable.getData("General_Data", "JurisdictionType");
        if (Jurisdiction.equals("ROI")) {
            if (isElementDisplayed(getObject("AddBillPayee.lblExampleROI"), 1)) {
                report.updateTestLog("addBillPayeeDetails", "Example Label is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("addBillPayeeDetails", "Example Label is not displayed", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("AddBillPayee.lblExampleThree"), 1)) {
                report.updateTestLog("addBillPayeeDetails", "Example-16 digit credit card number Label is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("addBillPayeeDetails", "Example-16 digit credit card number Label is not displayed", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("AddBillPayee.lblExampleTwo"), 1)) {
                report.updateTestLog("addBillPayeeDetails", "Example-7 or 8 digit credit mortgage account Label is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("addBillPayeeDetails", "Example-7 or 8 digit credit mortgage account Label is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject("AddBillPayee.lblExampleNIGB"), 1)) {
                report.updateTestLog("addBillPayeeDetails", "Example Label is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("addBillPayeeDetails", "Example Label is not displayed", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("AddBillPayee.lblExampleTwoUK"), 1)) {
                report.updateTestLog("addBillPayeeDetails", "16 digit credit card number", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("addBillPayeeDetails", "16 digit credit card number is not displayed under examples", Status_CRAFT.FAIL);
            }
        }

        if (isElementDisplayed(getObject("AddBillPayee.lblDescription"), 1)) {
            if (isElementDisplayed(getObject("AddBillPayee.lblOptionalDescription"), 1)) {
                report.updateTestLog("addBillPayeeDetails", "Description (optional)Label is displayed", Status_CRAFT.PASS);
                sendKeys(getObject("AddBillPayee.txtDescription"), dataTable.getData("General_Data", "BillerDescription"));
                Thread.sleep(2000);
            }else{
                report.updateTestLog("addBillPayeeDetails", "Description (optional) Label is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("addBillPayeeDetails", "Description Label is not displayed", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType()+"AddBillPayee.btnGoBack"), 5)) {
            report.updateTestLog("addBillPayeeDetails", "Go Back Button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addBillPayeeDetails", "Go Back Button is not displayed", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.btnContinue"), 1)) {
            report.updateTestLog("addBillPayeeDetails", "Continue Button is displayed", Status_CRAFT.PASS);
            clickJS(getObject("AddBillPayee.btnContinue"), "Continue ");
        } else {
            report.updateTestLog("addBillPayeeDetails", "Continue Button is not displayed", Status_CRAFT.FAIL);
        }
    }

    //  checkAddBillReviewPageDetails
    public void checkAddBillReviewPageDetails() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "AddBillPayee.lblReviewPageHeader"), 5)) {
            report.updateTestLog("checkAddBillReviewPageDetails", "Section Header 'Add Bill' is displayed on Review Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkAddBillReviewPageDetails", "Section Header 'Add Bill' is not displayed on Review Page", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("AddBillPayee.lblReview"), 5)) {
            report.updateTestLog("checkAddBillReviewPageDetails", "Review Heading displayed on Review page of Add Bill", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkAddBillReviewPageDetails", "Review Heading is not displayed on Review page of Add Bill", Status_CRAFT.FAIL);
        }

        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String strOperation = dataTable.getData("General_Data", "Operation");


        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {
            if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']/ancestor::div[contains(@class,'boi_label tc-question-part boi-float-none-force')]/following-sibling::*/descendant::*[text()='" + obj.getValue() + "']"), 5)) {
                report.updateTestLog("checkAddBillReviewPageDetails", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
            } else {
                report.updateTestLog("checkAddBillReviewPageDetails", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
            }
        }
        if (isElementDisplayed(getObject(deviceType()+"AddBillPayee.btnGoBack"), 2)) {
            report.updateTestLog("checkAddBillReviewPageDetails", "'Go back' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkAddBillReviewPageDetails", "'Go back' button is NOT displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("xpath~//*[text()='Continue']"), 2)) {
            report.updateTestLog("checkAddBillReviewPageDetails", "'Continue' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkAddBillReviewPageDetails", "'Continue' button is NOT displayed", Status_CRAFT.FAIL);
        }
        //Navigate to PIN Page
        clickJS(getObject("xpath~//*[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
    }

    //PIN Page Code


    public void checkAddBillConfirmationPage() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "AddBillPayee.lblReviewPageHeader"), 5)) {
            report.updateTestLog("checkAddBillReviewPageDetails", "Section Header 'Add Bill' is displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkAddBillReviewPageDetails", "Section Header 'Add Bill' is NOT displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.imgSuccess"), 1)) {
            report.updateTestLog("checkAddBillConfirmationPage", "Success Image is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkAddBillConfirmationPage", "Success Image is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.txtPayeeAdded"), 1)) {
            report.updateTestLog("checkAddBillConfirmationPage", "'Payee Added' text is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkAddBillConfirmationPage", "Payee Added' text  is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.btnSendMoney"), 1)) {
            report.updateTestLog("checkAddBillConfirmationPage", "Button 'Send Money' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkAddBillConfirmationPage", "Button 'Send Money' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.btnBackToHome"), 1)) {
            report.updateTestLog("checkAddBillConfirmationPage", "Link Button 'Back to home' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkAddBillConfirmationPage", "Link Button 'Back to home' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("xpath~//div[contains(@class,'boi_para')]"), 1)) {
            report.updateTestLog("checkAddBillConfirmationPage", "Time Stamp with message 'Time of request' is displayed on Confirmation page", Status_CRAFT.PASS);

            String strRequestSubmitted = getText(getObject("xpath~//div[contains(@class,'boi_para')]"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("checkAddBillConfirmationPage", "Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("checkAddBillConfirmationPage", "Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("checkAddBillConfirmationPage", "Time Stamp with message 'Time of request' is NOT displayed on Cancelled Standing Order Confirmation page", Status_CRAFT.FAIL);
        }

        //Validating Navigation/functionality of the buttons
        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to home")) {
            click(getObject("AddPayee.btnBackToHome"), "Clicked on 'Back to home'");
            if (isElementDisplayed(getObject(deviceType() + "home.lblFirstName"), 5)) {
                report.updateTestLog("checkAddBillConfirmationPage", "Upon clicking 'Back to home' ,successfully Navigated to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("checkAddBillConfirmationPage", "Upon clicking 'Back to home' ,navigation unsuccessful to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.FAIL);
            }
        }
    }

    public void checkAddBillErrors() throws Exception {
        waitForPageLoaded();
        clickJS(getObject("AddBillPayee.btnContinue"), "Clicking : Continue");
        String errorMessage[] = dataTable.getData("General_Data", "ErrorText").split("::");
        for (int i = 0; i < errorMessage.length; i++) {
            if (isElementDisplayed(getObject("xpath~//*[contains(@class,'tc-error-position')][contains(text(),'" + errorMessage[i] + "')]"), 5)) {
                report.updateTestLog("errorMessageValidations", errorMessage[i] + " :: Error Message displayed on Add Bill Payee Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMessageValidations", errorMessage[i] + " :: Error Message is not displayed on Add Bill Payee Page", Status_CRAFT.FAIL);
            }
        }
    }

    public void CheckAddItHereLink() throws Exception {
        click(getObject("AddBillPayee.lnkAddItHere"));
        String errorMessage[] = dataTable.getData("General_Data", "ErrorText").split("::");
        if (isElementDisplayed(getObject(deviceType()+"Payments.hdrManagePayees"), 5)) {
            report.updateTestLog("checkBackButtonBillPayees", "As Expected: Manage Payee Header is displayed once user move back from add bill page", Status_CRAFT.PASS);

        } else {
            report.updateTestLog("checkBackButtonBillPayees", "Issue: Incorrect Header Displayed once user move back to manage payee page from the add bill page", Status_CRAFT.FAIL);
        }
    }


    /**
     * Mobile App Automation Method : To Verify and Validating the Manage Payee list
     * Created by  C966119
     * Update on    Updated by    Reason
     * 24/06/2019   C966003      Added list to select payee as per reference
     */
    public void SelectPayeeFromManagePayeeList() throws Exception {
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 15)) {
            waitForPageLoaded();
        }
        String strExpectedPayeeName = dataTable.getData("General_Data", "PayeeName");
        String strBillReference = dataTable.getData("General_Data", "BillReference");
        if (!deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject("xpath~//*[contains(@title,'Payee name')][contains(text(),'"+ strExpectedPayeeName + "')]"), 5)) {
                clickJS(getObject("xpath~//*[contains(@title,'Payee name')][contains(text(),'"+ strExpectedPayeeName + "')]"), "Selected Payee '" + strExpectedPayeeName + "' from the Manage Payee list");
                if(getText(getObject(deviceType() + "AddBillPayee.lblBillRefernce")).trim().equalsIgnoreCase(strBillReference)){
                    scriptHelper.commonData.confirmationFlag = true;
                    if (dataTable.getData("General_Data","OperationDeletePayee").equals("DELETE")){
                        deletePayee();
                    }
                }
            }
        } else {
            if (isElementDisplayed(getObject("xpath~//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']"), 10)) {
                if (isElementDisplayed(getObject("xpath~//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']/descendant::*[contains(text(),'" + strExpectedPayeeName +"')]"),5)){
                    if(!"".equalsIgnoreCase(strExpectedPayeeName)){
                        List<WebElement> strList = findElements(getObject("xpath~//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']/descendant::*[contains(text(),'" + strExpectedPayeeName + "')]"));
                        for(int i=1; i<=strList.size(); i++){
                            clickJS(getObject("xpath~(//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']/descendant::*[contains(text(),'" + strExpectedPayeeName + "')])["+i+"]"),"Payee :: "+strExpectedPayeeName);
                            if(getText(getObject(deviceType() + "AddBillPayee.lblBillRefernce")).trim().equalsIgnoreCase(strBillReference)){
                                if (dataTable.getData("General_Data","OperationDeletePayee").equals("DELETE")){
                                    deletePayee();
                                }
                                scriptHelper.commonData.confirmationFlag = true;
                                break;
                            } else {
                                if(isElementDisplayed(getObject(deviceType()+ "home.Goback"),5)){
                                    clickJS(getObject(deviceType()+ "home.Goback"),"Go back");
                                }else{
                                    clickJS(getObject(deviceType()+ "AAQ.btnGobackarrow"),"Go bacarrowk");
                                }

                            }
                        }
                    }
                }
            } else {
                if (isElementDisplayed(getObject("xpath~//table[contains(@class,'boiArrowTable')][@summary='PayeeList']"), 5)) {
                    clickJS(getObject("xpath~//table[contains(@class,'boiArrowTable')][@summary='PayeeList']/tbody/tr/td/descendant::*[text()='" + strExpectedPayeeName + "']"), "Selected Payee '" + strExpectedPayeeName + "' from the Manage Payee list");
                    if (dataTable.getData("General_Data","OperationDeletePayee").equals("DELETE")){
                        deletePayee();
                    }
                } else {
                    report.updateTestLog("SelectPayeeFromManagePayeeList", "Manage Payees table  is NOT displayed on Manage Payee page", Status_CRAFT.FAIL);
                }
            }
        }

    }

    /**
     * Mobile App Automation Method : To Verify and Validating the International Payee index from the list
     * Update on     Updated by     Reason
     * 24/06/2019    C966119        for mobile Automation
     */
    public int IdentifyInternationalPayeeList(String strExpectedPayeeName) throws Exception {
        String strPayeeType = dataTable.getData("General_Data", "AccountPosition_Grouped");
        int intIndex = 55555;
        boolean blnPayeeFound = true;
        List<WebElement> oUIRows = driver.findElements(getObject(strExpectedPayeeName));
        if (oUIRows.size() > 0) {
            for (int i = 0; i < oUIRows.size(); i++) {
                String strRowText = oUIRows.get(i).getText().toUpperCase();
                if (!(strRowText.contains("EUR")) && strPayeeType.equals("InternationalPayeeForSEPA")) {
                    intIndex = i;
                    blnPayeeFound = true;
                    break;
                } else if (strRowText.contains("GBP") && strPayeeType.equals("DomesticUK")) {
                    intIndex = i;
                    blnPayeeFound = true;
                    break;
                } else if (strRowText.contains("GBP") && strPayeeType.equals("ROI_InternationalPayeeUK")) {
                    intIndex = i;
                    blnPayeeFound = true;
                    break;
                } else if (strRowText.contains("EUR") && strPayeeType.equals("InternationalPayeeForUK")) {
                    intIndex = i;
                    blnPayeeFound = true;
                    break;
                } else if (strRowText.contains("EUR") && strPayeeType.equals("SEPA_Payee")) {
                    strPayeeType = "SEPA";
                    intIndex = i;
                    blnPayeeFound = true;
                    break;
                } else {
                    blnPayeeFound = false;
                }
            }
        }

        if (blnPayeeFound == false) {
            intIndex = 55555;
        } else {
            report.updateTestLog("IdentifyInternationalPayeeList", "Payee Type :: " + strPayeeType + " :: is Selected to validate the details ", Status_CRAFT.PASS);
        }
        return intIndex;
    }

    /**
     * Mobile App Automation Method : To Verify and Validating the Manage Payee list
     * Update on     Updated by     Reason
     * 24/06/2019    C966119        for mobile Automation
     */
    public void IdentifyPayeeFromManagePayeeList() throws Exception {
        String strExpectedPayeeName = "";
        String strStatus = "";
        String strIBAN = "";
        String strName = "";
        int intIndex = 0;
        String strCurrency = "";
        String strOperation = dataTable.getData("General_Data", "Account_Type");

        switch (strOperation) {
            case "BillPayeeActive":
                strExpectedPayeeName = "PayeeDetails.activebillpayee";
                break;
            case "InActiveBillPayee":
                strExpectedPayeeName = "PayeeDetails.inactivebillpayee";
                break;
            case "ActivePayee":
                strExpectedPayeeName = "PayeeDetails.activePayee";
                break;
            case "InActivePayee":
                strExpectedPayeeName = "PayeeDetails.inactivepayee";
                break;
            default:
                report.updateTestLog("IdentifyPayeeFromManagePayeeList", "Please check the Data sheet and put correct switch case like Active payee !!", Status_CRAFT.WARNING);
        }
        Thread.sleep(4000);
        List<WebElement> oUIRows = driver.findElements(getObject(strExpectedPayeeName));
        if (oUIRows.size() > 0) {
            intIndex = IdentifyInternationalPayeeList(strExpectedPayeeName);
            if (intIndex == 55555) {
                report.updateTestLog("IdentifyPayeeFromManagePayeeList", "In Manage Payees table  NO RECORD FOUND..Change the Data... for Payee Type :: " + strOperation, Status_CRAFT.FAIL);
                return;
            }
            String strRowText = oUIRows.get(intIndex).getText().toUpperCase();
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(intIndex));
            Thread.sleep(1000);
            JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
            report.updateTestLog("IdentifyPayeeFromManagePayeeList", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
            executor.executeScript("arguments[0].click();", oUIRows.get(intIndex));
            String[] ccContentToCheck = strRowText.split("\\n");
            strName = ccContentToCheck[0].toString();
            strIBAN = ccContentToCheck[1];
            strCurrency = ccContentToCheck[2].toString();
            if (strOperation.contains("InActive")) {
                strStatus = ccContentToCheck[3].toString();
            }
            Thread.sleep(4000);
            report.updateTestLog("IdentifyPayeeFromManagePayeeList", strOperation + " :: Payee Record Having Name :: " + strName + " :: Is Selected to validate the details ", Status_CRAFT.PASS);
            payeeDetailsMobile(strName, strCurrency, strStatus, strIBAN);
        } else {
            report.updateTestLog("IdentifyPayeeFromManagePayeeList", "In Manage Payees table  No Record found for Payee Type :: " + strOperation, Status_CRAFT.FAIL);
        }

    }


    /**
     * Mobile App Automation Method : validating Payee Details Page, after selecting a payee from Manage Payee list
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void payeeDetailsMobile(String strExpectedPayeeName, String strCurrency, String strExpPayeeStatus, String strIBAN) throws Exception {
        String strOperationToPer = dataTable.getData("General_Data", "Relationship_Status");
        String strIBANorAccount = "IBAN";
        String strExpMessage = "";
        String strPageHeader = "";
        String strUiPayeename = "";
        String strExpectedPayeeCurrency = strCurrency;
        String strUiCurrency = "";
        String strPayeeType = dataTable.getData("General_Data", "Account_Type");

        if (strIBAN.contains("IBAN")) {
            strIBAN = strIBAN.split(" ")[1].toString();
        }else if(strPayeeType.toUpperCase().contains("Bill".toUpperCase())){
            strIBANorAccount = "Reference";
        }else{
            strIBANorAccount = "Account number";
        }

        strPageHeader = dataTable.getData("General_Data", "PageHeader");
        if (isElementDisplayed(getObject("xpath~//div[@style='text-align: center; ; text-align:center;width:100%;margin-left:0px;']/descendant::h1[text()='" + strPageHeader + "']"), 45)) {
            report.updateTestLog("payeeDetailsPage", "Page Title '" + strPageHeader + "' is displayed Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("payeeDetailsPage", "Page Title '" + strPageHeader + "' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        strUiPayeename = getText(getObject("mw.home.txtLabel"));
        if (strUiPayeename.equalsIgnoreCase(strExpectedPayeeName)) {
            report.updateTestLog("payeeDetailsPage", "Payee Name '" + strExpectedPayeeName + "' is displayed correctly", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("payeeDetailsPage", "Payee Name is not correct on Mobile Payee Details Page, Expected:'" + strExpectedPayeeName + "', Actual:'" + strUiPayeename + "'", Status_CRAFT.FAIL);
        }

        if (strPayeeType.contains("InActive")) {
            strUiCurrency = getText(getObject("AddPayee.txtDetailsPayeeCurrency"));
            if (strUiCurrency.trim().equals(strExpectedPayeeCurrency)) {
                report.updateTestLog("payeeDetailsPage", "Payee Currency '" + strUiCurrency + "' is correctly displayed Payee Details Page", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("payeeDetailsPage", "Payee Currency is not correct on Payee Details Page, Expected:'" + strExpectedPayeeCurrency + "', Actual:'" + strUiCurrency + "'", Status_CRAFT.FAIL);
            }

            String strUIPayeeStatus = getText(getObject("AddPayee.txtDetailsPayeeStatus"));
            if (strUIPayeeStatus.trim().equalsIgnoreCase(strExpPayeeStatus)) {
                report.updateTestLog("payeeDetailsPage", "Payee Status '" + strUIPayeeStatus + "' is correctly displayed under Currency in Payee Details Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("payeeDetailsPage", "Payee Status is not correct under Currency in Payee Details Page, Expected:'" + strExpPayeeStatus + "', Actual:'" + strUIPayeeStatus + "'", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("Payments.icnExclaimPayees"), 2) && isElementDisplayed(getObject("Payments.txtInActivePayee"), 2)) {
                strExpMessage = dataTable.getData("General_Data", "Current_Balance");
                String strActMessage = getText(getObject("Payments.txtInActivePayee"));
                if (strExpMessage.equals(strActMessage.replaceAll("\n", ""))) {
                    report.updateTestLog("payeeDetailsPage", "Payee Status :: InActive Payee ; alert message :: " + strActMessage + " :: correctly displayed on Payee Details Page", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("payeeDetailsPage", "Payee Status :: InActive Payee ; UI alert message :: " + strActMessage + " :: Is NOT as expected '"+strExpMessage+"' on Payee Details Page", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("payeeDetailsPage", "Payee Status InActive Alert Message is not displayed, Actual Message :: " + strExpMessage, Status_CRAFT.FAIL);
            }
        } else {
            strUiCurrency = getText(getObject("xpath~//*[contains(@class,'top-card-right-align-content top-card-active-content')]"));
            if (strUiCurrency.trim().equals(strExpectedPayeeCurrency)) {
                report.updateTestLog("payeeDetailsPage", "Payee Currency '" + strUiCurrency + "' is correctly displayed Payee Details Page", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("payeeDetailsPage", "Payee Currency is not correct on Payee Details Page, Expected:'" + strExpectedPayeeCurrency + "', Actual:'" + strUiCurrency + "'", Status_CRAFT.FAIL);
            }
        }

        scrollToView((getObject("xpath~//div[not(@style='display: none;')]/div[contains(@class,'tc-question-part boi_label')]/descendant::*[text()='" + strIBANorAccount + "']")));
        if (isElementDisplayed(getObject("xpath~//div[not(@style='display: none;')]/div[contains(@class,'tc-question-part boi_label')]/descendant::*[text()='" + strIBANorAccount + "']"), 5)) {
            String strUiValue = getText(getObject("xpath~//div[not(@style='display: none;')]/div[contains(@class,'tc-question-part boi_label')]/descendant::*[text()='" + strIBANorAccount + "']/ancestor::*[contains(@class,'tc-question-part boi_label')]/following-sibling::*[contains(@class,'tc-answer-part boi_input')]"));
            if (strUiValue.trim().toUpperCase().equals(strIBAN.toUpperCase())) {
                report.updateTestLog("payeeDetailsPage", "Value of label ::" + strIBANorAccount + " :: is correctly displayed on Payee details page, expected'" + strIBAN + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("payeeDetailsPage", "Value of label ::" + strIBANorAccount + " ::  is NOT correctly displayed on Payee details page, expected'" + strIBAN + "', Actual : '" + strUiValue + "'", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("payeeDetailsPage", "Label ::" + strIBANorAccount + " :: is not displayed on Payee details page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.lableDetailsPayeeLimit"), 5)) {
            String strUiValue = getText(getObject("AddPayee.txtDetailsPayeeLimit"));
            if (strUiValue.trim().toUpperCase().contains(dataTable.getData("General_Data", "VerifyDetails").toUpperCase())) {
                report.updateTestLog("payeeDetailsPage", "Value of label Transfer limit is correctly displayed on Payee details page, expected'" + strUiValue + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("payeeDetailsPage", "Value of label Transfer limit is NOT correctly displayed on Payee details page, expected'" + strUiValue + "', Actual : '" + strUiValue + "'", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("payeeDetailsPage", "Label Transfer Limit is not displayed on Payee details page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "Payments.DeletePayeebutton"), 5)) {
            report.updateTestLog("checkAddBillReviewPageDetails", "'Delete payee' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkAddBillReviewPageDetails", "'Delete payee' button is NOT displayed", Status_CRAFT.FAIL);
        }

        if (!strPayeeType.contains("InActive")) {
            if (isElementDisplayed(getObject(deviceType() + "Payments.Paybutton"), 5)) {
                report.updateTestLog("payeeDetailsPage", "'Pay' button is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("payeeDetailsPage", "'Pay' button is NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject(deviceType() + "Payments.Paybutton"), 5)) {
                report.updateTestLog("payeeDetailsPage", "'Pay' button is displayed", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("payeeDetailsPage", "'Pay' button is NOT displayed", Status_CRAFT.PASS);
            }
        }

        //TODO need to check for desktop
        if (strOperationToPer.equals("Back")&&(deviceType.equals("TabletWeb")||deviceType.equals("MobileWeb"))) {
            clickJS(getObject("m.backarrow"), "Clicked on Back Button");
            Thread.sleep(2000);
            waitForPageLoaded();
        }

    }

    /**
     * Mobile App Automation Method : validating Payee Details Page, after selecting a payee from Manage Payee list
     * Update on    Updated by      Reason
     * 29/10/2019   C103403         CFPUR-11263 (Manage Payees page - Mobile)
     */
    public void payeeDetailsPage() throws Exception {

        //validating page header
        String strPageHeader = dataTable.getData("General_Data", "PageHeader");
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("xpath~//h1[text()='" + strPageHeader + "']"), 5)) {
                report.updateTestLog("payeeDetailsPage", "Page Title '" + strPageHeader + "' is displayed Payee Details Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("payeeDetailsPage", "Page Title '" + strPageHeader + "' is NOT displayed on Payee Details Page", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject("xpath~//h3[text()='" + strPageHeader + "']"), 5)) {
                report.updateTestLog("payeeDetailsPage", "Page Title '" + strPageHeader + "' is displayed enter pin Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("payeeDetailsPage", "Page Title '" + strPageHeader + "' is NOT displayed on enter pin Page", Status_CRAFT.FAIL);
            }
        }

        //validate PayeeName
        String strExpectedPayeeName = dataTable.getData("General_Data", "PayeeName");
        String strUiPayeename = getText(getObject("xpath~//*[contains(@class,'boiArrowTable__cell--name')]"));
        if (deviceType.equalsIgnoreCase("Web")) {
            if (strUiPayeename.equals(strExpectedPayeeName)) {
                report.updateTestLog("payeeDetailsPage", "Payee Name '" + strUiPayeename + "' is correctly displayed Payee Details Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("payeeDetailsPage", "Payee Name is not correct on Payee Details Page, Expected:'" + strExpectedPayeeName + "', Actual:'" + strUiPayeename + "'", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject("xpath~//*[contains(@class,'boi_label')][text()='" + strExpectedPayeeName + "']"), 5)) {
                report.updateTestLog("payeeDetailsPage", "Payee Name '" + strExpectedPayeeName + "' is displayed correctly", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("payeeDetailsPage", "Payee Name is not correct on Mobile Payee Details Page, Expected:'" + strExpectedPayeeName + "', Actual:'" + strUiPayeename + "'", Status_CRAFT.FAIL);
            }

        }

        String strExpectedPayeeCurrency = dataTable.getData("General_Data", "PayeeCurrency");
        if (dataTable.getData("General_Data", "PayeeStatus").equalsIgnoreCase("Inactive")) {
            String strUiCurrency = getText(getObject("xpath~//*[contains(@class,'top-card-right-align-content boi_input')]"));
            if (strUiCurrency.trim().equals(strExpectedPayeeCurrency)) {
                report.updateTestLog("payeeDetailsPage", "Payee Currency '" + strUiCurrency + "' is correctly displayed Payee Details Page", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("payeeDetailsPage", "Payee Currency is not correct on Payee Details Page, Expected:'" + strExpectedPayeeCurrency + "', Actual:'" + strUiCurrency + "'", Status_CRAFT.FAIL);
            }
            //validate 'Inactive' if the payee is inactive just below the currency abbreviation
            String strExpPayeeStatus = dataTable.getData("General_Data", "PayeeStatus");
            String strUIPayeeStatus = getText(getObject("xpath~//label[text()='Status']/following-sibling::*[contains(@class,'top-card-right-align-content')]"));
            if (strUIPayeeStatus.trim().equals(strExpPayeeStatus)) {
                report.updateTestLog("payeeDetailsPage", "Payee Status '" + strUIPayeeStatus + "' is correctly displayed under Currency in Payee Details Page", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("payeeDetailsPage", "Payee Status is not correct under Currency in Payee Details Page, Expected:'" + strExpPayeeStatus + "', Actual:'" + strUIPayeeStatus + "'", Status_CRAFT.FAIL);
            }
        } else {
            String strUiCurrency = getText(getObject("xpath~//*[contains(@class,'top-card-right-align-content top-card-active-content')]"));
            if (strUiCurrency.trim().equals(strExpectedPayeeCurrency)) {
                report.updateTestLog("payeeDetailsPage", "Payee Currency '" + strUiCurrency + "' is correctly displayed Payee Details Page", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("payeeDetailsPage", "Payee Currency is not correct on Payee Details Page, Expected:'" + strExpectedPayeeCurrency + "', Actual:'" + strUiCurrency + "'", Status_CRAFT.FAIL);
            }
        }

        //Verify Payee Details, currently passing it through excel, once deployed in E2E we need to check it though UI
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        mapData.clear();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String strOperation = dataTable.getData("General_Data", "Operation");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {
            scrollToView((getObject("xpath~//div[not(@style='display: none;')]/div[contains(@class,'tc-question-part boi_label')]/descendant::*[text()='" + obj.getKey() + "']")));
            if (isElementDisplayed(getObject("xpath~//div[not(@style='display: none;')]/div[contains(@class,'tc-question-part boi_label')]/descendant::*[text()='" + obj.getKey() + "']"), 5)) {
                //valiating reference tooltip
                if (obj.getKey().equals("Reference")) {
                    clickJS(getObject("xpath~//div[not(@style='display: none;')]/div[contains(@class,'tc-question-part boi_label')]/descendant::*[text()='Reference']/*[contains(@class,'fa-info-circle')]"), "Reference tooltip");
                    String UiTooltip = getText(getObject("xpath~//div[not(@style='display: none;')]/div[contains(@class,'tc-question-part boi_label')]/descendant::*[text()='Reference']/descendant::*[contains(@class,'boi-tooltip__content')]"));
                    String strExpectedTooltip = "This is visible to the payee.";
                    if (!deviceType.equalsIgnoreCase("Web")) {
                        if (UiTooltip.equals(strExpectedTooltip)) {
                            report.updateTestLog("payeeDetailsPage", "Tooltip of label '" + obj.getKey() + "' is correctly displayed on Payee details page, Expected: '" + strExpectedTooltip + "'", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("payeeDetailsPage", "Tooltip of '" + obj.getKey() + "' is NOT  correctly displayed on Payee details page, Expected: '" + strExpectedTooltip + "', Actual:" + UiTooltip + "'", Status_CRAFT.FAIL);
                        }
                    }
                }

                if ((obj.getKey().equals("Transfer limit"))&& (dataTable.getData("General_Data", "JurisdictionType").equalsIgnoreCase("CROSS"))&&((dataTable.getData("General_Data", "ProductName").equalsIgnoreCase("SEPA"))||(dataTable.getData("General_Data", "ProductName").equalsIgnoreCase("INTERNATIONAL")))) {
                    clickJS(getObject("xpath~//div[not(@style='display: none;')]/div[contains(@class,'tc-question-part boi_label')]/descendant::*[text()='Transfer limit']/../*[contains(@class,'fa-info-circle')]"), "Transfer limit tooltip");
                    String UiTooltip = getText(getObject("xpath~//div[not(@style='display: none;')]/div[contains(@class,'tc-question-part boi_label')]/descendant::*[text()='Transfer limit']/../descendant::*[contains(@class,'boi-tooltip__content')]"));
                    String strExpectedTooltip = "The limit is in the currency of the account you use when sending money";
                    if (!deviceType.equalsIgnoreCase("Web")) {
                        if (UiTooltip.equals(strExpectedTooltip)) {
                            report.updateTestLog("payeeDetailsPage", "Tooltip of label '" + obj.getKey() + "' is correctly displayed on Payee details page, Expected: '" + strExpectedTooltip + "'", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("payeeDetailsPage", "Tooltip of '" + obj.getKey() + "' is NOT  correctly displayed on Payee details page, Expected: '" + strExpectedTooltip + "', Actual:" + UiTooltip + "'", Status_CRAFT.FAIL);
                        }
                    }
                }

                String strUiValue = getText(getObject("xpath~//div[not(@style='display: none;')]/div[contains(@class,'tc-question-part boi_label')]/descendant::*[text()='" + obj.getKey() + "']/ancestor::*[contains(@class,'tc-question-part boi_label')]/following-sibling::*[contains(@class,'tc-answer-part boi_input')]"));
                if (strUiValue.trim().equals(obj.getValue())) {
                    report.updateTestLog("payeeDetailsPage", "Value of label '" + obj.getKey() + "' is correctly displayed on Payee details page, expected'" + obj.getValue() + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("payeeDetailsPage", "Value of label '" + obj.getKey() + "' is NOT correctly displayed on Payee details page, expected'" + obj.getValue() + "', Actual : '" + strUiValue + "'", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("payeeDetailsPage", "Label '" + obj.getKey() + "' is not displayed on Payee details page", Status_CRAFT.FAIL);
            }
        }

        mapData.clear();

        if (isElementDisplayed(getObject(deviceType() + "Payments.DeletePayeebutton"), 5)) {  //"xpath~//a/*[text()='Delete payee']"
            report.updateTestLog("checkAddBillReviewPageDetails", "'Delete payee' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkAddBillReviewPageDetails", "'Delete payee' button is NOT displayed", Status_CRAFT.FAIL);
        }

        if (!dataTable.getData("General_Data", "PayeeStatus").equalsIgnoreCase("Inactive")) {
            if (isElementDisplayed(getObject(deviceType() + "Payments.Paybutton"), 5)) {   //xpath~//a/*[text()='Pay']
                report.updateTestLog("payeeDetailsPage", "'Pay' button is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("payeeDetailsPage", "'Pay' button is NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject(deviceType() + "Payments.Paybutton"), 5)) {
                report.updateTestLog("payeeDetailsPage", "'Pay' button is displayed", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("payeeDetailsPage", "'Pay' button is NOT displayed", Status_CRAFT.PASS);
            }
        }
        //clicking on the desired button
        if (!strOperation.equals("")) {
            clickJS(getObject("xpath~//a/*[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
        }

        if (!strOperation.equals("Back")) {
            clickJS(getObject("mw.Payments.btnGoback"), "Clicked on Back Button");
            Thread.sleep(2000);
        }


    }

    // Pay Button on Manage payees will be clicked if available
    public void clickPay() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "Payments.Paybutton"), 5)) {
            report.updateTestLog("payeeDetailsPage", "'Pay' button is displayed", Status_CRAFT.PASS);
            clickJS((getObject(deviceType() + "Payments.Paybutton")), "Pay Button");
        } else {
            report.updateTestLog("payeeDetailsPage", "'Pay' button is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    // Function to select the Pay From Account from the first page of Payments (Send Money Page)
    public void PayFromAccountSelection() throws Exception {
//        Select Account to pay from
        if (isElementDisplayed(getObject("Payments.PayFromAccountSelection"), 5)) {
            String strAccount = dataTable.getData("General_Data", "Account_Type");
            if (getText(getObject("Payments.PayFromAccountSelection")).equalsIgnoreCase("Select account")) {
                report.updateTestLog("PayFromAccountSelection", "Directional text is correctly displayed on the Select Account for Pay From dropdown", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("PayFromAccountSelection", "Directional text is not as expected of Select Account for Pay From dropdown, Expected: 'Select account' :: Actual:'" + getText(getObject("Payments.PayFromAccountSelection")) + "'", Status_CRAFT.FAIL);
            }

            if (deviceType.equalsIgnoreCase("Web")) {
                click(getObject("Payments.PayFromAccountSelection"));
                driver.findElement(By.xpath("//*[@class='option'][contains(.,'" + strAccount + "')]")).click();
                report.updateTestLog("PayFromAccountSelection", "'" + strAccount + "' Account is selected Standing Order dropdown", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("PayFromAccountSelection", "Drop Down to Select Pay From Account Not Present", Status_CRAFT.FAIL);
        }
        Thread.sleep(2000);

        //Enter Amount in the Amount text box in send money page

        if (isElementDisplayed(getObject("Payments.lblAmount"), 5)) {
            String strCurrSymbol = dataTable.getData("General_Data", "Currency_Symbol");
            report.updateTestLog("PayFromAccountSelection", "Label 'Amount' is displayed on Send Money Page", Status_CRAFT.PASS);

            //Validate Currency Symbol
            if (getText(getObject("Payments.lblCurrencySymbol")).equals(strCurrSymbol)) {
                report.updateTestLog("PayFromAccountSelection", "Currency Symbol label is correct '" + strCurrSymbol + "' in Amount Field on Send Money Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("PayFromAccountSelection", "Currency Symbol label is NOT correct '" + strCurrSymbol + "' in Amount Field on Send Money Page, UI label value '" + getText(getObject("xpath~//label[text()='Amount ']/span[@class='boi_input_sm ']")) + "'", Status_CRAFT.FAIL);
            }

            String strAmount = dataTable.getData("General_Data", "NewAmount");
            if (!strAmount.equals("")) {
                sendKeys(getObject("Payments.txtNewAmount"), strAmount, "Amount entered is '" + strAmount + "'");
            }
        } else {
            report.updateTestLog("PayFromAccountSelection", "Label 'Amount' is NOT displayed on Send Money Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("Payments.btnGoback"), 5)) {
            report.updateTestLog("PayFromAccountSelection", "Button 'Go back' is displayed on Send Money Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("PayFromAccountSelection", "Button 'Go back' is NOT displayed on Send Money Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("Payments.btnContinue"), 5)) {
            report.updateTestLog("PayFromAccountSelection", "Button 'Continue' is displayed on Send Money Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("PayFromAccountSelection", "Button 'Continue' is NOT displayed on Send Money Page", Status_CRAFT.FAIL);
        }
    }

    public void PayFromAccountOperation() throws Exception {
        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":

                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Payments.btnContinue")));
                    Thread.sleep(2000);
                    click(getObject("Payments.btnContinue"), "Clicked on 'Continue', on Send Money Page");
                    break;
                case "GO BACK":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Payments.btnGoback")));
                    Thread.sleep(2000);
                    click(getObject("Payments.btnGoback"), "Clicked on 'Go back', on Send Money Page");
                    if (isElementDisplayed(getObject("Payments.ManagePayeetxt"), 5)) {
                        report.updateTestLog("PayFromAccountOperation", "Page navigated to Manage Payees Page upon clicking 'Go back'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("PayFromAccountOperation", "Page does not navigated to Manage Payees Page upon clicking 'Go back'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("PayFromAccountOperation", "Please provide the valid operation on Send Money Page, Continue or Go Back", Status_CRAFT.FAIL);
            }
        }
    }

    public void SendMoneyReviewPageOperation() throws Exception {
        if (!dataTable.getData("General_Data", "OperationOnReviewPage").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "OperationOnReviewPage");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Payments.SendMoneyReviewbtnContinue")));
                    Thread.sleep(2000);
                    clickJS(getObject("Payments.SendMoneyReviewbtnContinue"), "Clicked on 'Continue', on Send Money Review Page");
                    break;
                case "GO BACK":
                    js.executeScript("window.scrollTo(0, document.body.scrollHeight);", driver.findElement(getObject( deviceType()+"Payments.SendMoneyReviewbtnGoback")));
                    Thread.sleep(2000);
                    clickJS(getObject( deviceType()+"Payments.SendMoneyReviewbtnGoback"), "Clicked on 'Go back', on Send Money Review Page");
                    if (isElementDisplayed(getObject("Payments.btnContinue"), 5)) {
                        report.updateTestLog("SendMoneyReviewPageOperation", "Page navigated to Send Money first Page upon clicking 'Go back'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("SendMoneyReviewPageOperation", "Page does not navigated to Send Money first Page upon clicking 'Go back'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("SendMoneyReviewPageOperation", "Please provide the valid operation on Send Money Review Page, Continue or Go Back", Status_CRAFT.FAIL);
            }
        }
    }

    public void verifyPayFromAccountSelection() throws Exception {
//        Select Account to pay from
        if (isElementDisplayed(getObject("Payments.PayFromAccountSelection"), 5)) {
            String strAccount = dataTable.getData("General_Data", "Account_Type");
            if (getText(getObject("Payments.PayFromAccountSelection")).equalsIgnoreCase("Select account")) {
                report.updateTestLog("PayFromAccountSelection", "Directional text is correctly displayed on the Select Account for Pay From dropdown", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("PayFromAccountSelection", "Directional text is not as expected of Select Account for Pay From dropdown, Expected: 'Select account' :: Actual:'" + getText(getObject("Payments.PayFromAccountSelection")) + "'", Status_CRAFT.FAIL);
            }
        }
    }


    public void selectPayFromAccount() throws Exception {
        //validating page title
        String strPageHeader = dataTable.getData("General_Data", "PageHeader");
        String strPayFromAccount = dataTable.getData("General_Data", "PayFrom_Account");
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("xpath~//h1[text()='" + strPageHeader + "']"), 5)) {
                report.updateTestLog("selectPayFromAccount", "Page Title '" + strPageHeader + "' is displayed on Add Bill pay from account Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPayFromAccount", "Page Title '" + strPageHeader + "' is NOT displayed on Add Bill pay from account Page", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject("xpath~(//h1[contains(text(),'" + strPageHeader + "')])[1]"), 5)) {
                report.updateTestLog("selectPayFromAccount", "Page Title '" + strPageHeader + "' is displayed Add Bill pay from account Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPayFromAccount", "Page Title '" + strPageHeader + "' is NOT displayed on Add Bill pay from account Page", Status_CRAFT.FAIL);
            }
        }

        //verify section header
        if (isElementDisplayed(getObject("xpath~//h3[text()='Pay from']"), 5)) {
            report.updateTestLog("selectPayFromAccount", "Section header'Paye from' is displayed Add Bill pay from account Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPayFromAccount", "Section header'Paye from' is NOT displayed on Add Bill pay from account Page", Status_CRAFT.FAIL);
        }

        //verify text content
        String strExpectedContent = "The bills available to add can differ between your EUR and GBP accounts.";
        String strUIContent = getText(getObject("AddBillPayee.lblInformativeText"));
        if (strUIContent.equals(strExpectedContent)) {
            report.updateTestLog("selectPayFromAccount", "Informative text '" + strUIContent + "'is displayed on Add Bill pay from account Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPayFromAccount", "Informative text is NOT displayed correctly on Add Bill pay from account Page, Expected:'" + strExpectedContent + "', Actual :'" + strUIContent + "'", Status_CRAFT.FAIL);
        }


        //validating field name 'Account'
        if (isElementDisplayed(getObject("StandingOrder.lblAccount"), 5)) {
            report.updateTestLog("selectPayFromAccount", "Field name 'Account'is displayed on Add Bill pay from account Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPayFromAccount", "Field name 'Account'is NOT displayed on Add Bill pay from account Page", Status_CRAFT.FAIL);
        }
        //Select account
        if (isElementDisplayed(getObject(deviceType() + "AddBillPayee.lstPayFromAccount"), 5)) {
            report.updateTestLog("addBillPayeeDetails", "Dropdown of 'Account' is dislayed on Add Bill Payee Details Page", Status_CRAFT.PASS);
            //validating directional text

            if (!strPayFromAccount.equals("")) {
                //selectDropDownJS(getObject("AddBillPayee.lstAccountList"),strPayFromAccount);
                clickJS(getObject("xpath~//button[contains(text(),'Select account')]"), "Select account");
                Thread.sleep(2000);
                if(strPayFromAccount.contains("~")){
                    clickJS(getObject("xpath~//li[contains(text(),'" + strPayFromAccount.split("~")[1].trim() + "')]"), "Account No.");
                }else if(strPayFromAccount.contains("-")){
                    clickJS(getObject("xpath~//li[contains(text(),'" + strPayFromAccount.split("-")[1].trim() + "')]"), "Account No.");
                }else{
                    clickJS(getObject("xpath~//li[contains(text(),'" + strPayFromAccount + "')]"), "Account No.");
                }

                waitForPageLoaded();
            }
        } else {
            report.updateTestLog("addBillPayeeDetails", "Pay from Account is not displayed", Status_CRAFT.FAIL);
        }
        operationOnPayeeDetailsPage();
    }


    public void checkBackButtonBillPayees() throws Exception {
        click(getObject("AddBillPayee.btnGoBack"));
        if (isElementDisplayed(getObject(deviceType()+"Payments.hdrManagePayees"), 5)) {
            report.updateTestLog("checkBackButtonBillPayees", "As Expected: Manage Payee Header is displayed once user move back from add bill page", Status_CRAFT.PASS);

        } else {
            report.updateTestLog("checkBackButtonBillPayees", "Issue: Incorrect Header Displayed once user move back to manage payee page from the add bill page", Status_CRAFT.FAIL);
        }
        addBillPayeeNavigation();
        click(getObject("AddBillPayee.lstBillPayee"));
        driver.findElement(By.xpath("//*[@class='aria_exp_wrapper']/ul/li[contains(.,'" + dataTable.getData("General_Data", "BillerName") + "')]")).click();
        waitForPageLoaded();
        sendKeys(getObject("AddBillPayee.txtReference"), dataTable.getData("General_Data", "BillReference"));
        sendKeys(getObject("AddBillPayee.txtDescription"), dataTable.getData("General_Data", "BillerDescription"));
        click(getObject("AddBillPayee.btnContinue"));
        click(getObject("AddBillPayee.btnGoBack"));
        if (isElementDisplayed(getObject("AddBillPayee.lblBillName"), 5)) {
            report.updateTestLog("addBillPayeeDetails", "User navigate back from Review Bill page to Add bill details page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addBillPayeeDetails", "Defect: User is not properly navigated back from Review Bill page to Add bill details page", Status_CRAFT.FAIL);
        }
        click(getObject("AddBillPayee.btnGoBack"));
        if (isElementDisplayed(getObject(deviceType()+"Payments.hdrManagePayees"), 5)) {
            report.updateTestLog("checkBackButtonBillPayees", "As Expected: Manage Payee Header is displayed once user move back from add bill page", Status_CRAFT.PASS);

        } else {
            report.updateTestLog("checkBackButtonBillPayees", "Issue: Incorrect Header Displayed once user move back to manage payee page from the add bill page", Status_CRAFT.FAIL);
        }
    }

    // To Verify the Delete Payee Pop Up on Manage Payees Page
    public void verifyDeletePayeePopUpDetails() throws Exception {
        String strContentToCheck = dataTable.getData("General_Data", "FirstName");
        String[] arrContentToCheck = strContentToCheck.split("::");
        report.updateTestLog("verifyDeletePayeePopUpDetails", "The Title and Content ", Status_CRAFT.SCREENSHOT);
        // To Verify Title
        String strTitleXpath = "xpath~//span[text()='" + arrContentToCheck[0] + "']";
        //String strTitleXpath = "xpath~//div[@class='ecDIB  boi-popup-dialog__title']/div[text()='" + arrContentToCheck[0] + "']";
        if (isElementDisplayed(getObject(strTitleXpath), 15)) {
            report.updateTestLog("verifyDeletePayeePopUpDetails", "The title of the Dialog :: " + arrContentToCheck[0] + ":: is correctly displayed.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyDeletePayeePopUpDetails", "The title of the Dialog :: " + arrContentToCheck[0] + ":: is not correctly displayed.", Status_CRAFT.FAIL);
        }

        // Verify Close icon
        if (isElementDisplayed(getObject("PTM.DeRegePopUpCloseIcon"), 2)) {
            report.updateTestLog("verifyDeletePayeePopUpDetails", "The Close icon 'X' is correctly displayed.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyDeletePayeePopUpDetails", "The Close icon 'X' is not correctly displayed.", Status_CRAFT.FAIL);
        }
        //Validate the content
        String strContentXpath = "xpath~//span[text()='" + arrContentToCheck[1] + "']";
        if (isElementDisplayed(getObject(strContentXpath), 2)) {
            report.updateTestLog("verifyDeletePayeePopUpDetails", "The Content of the Dialog :: " + arrContentToCheck[1] + ":: is correctly displayed.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyDeletePayeePopUpDetails", "The Content of the Dialog :: " + arrContentToCheck[1] + ":: is not correctly displayed.", Status_CRAFT.FAIL);
        }
    }

    public void validateAndCheckDeletePayee() throws Exception {
        verifyDeletePayee((deviceType() + "Payments.DeletePayeebutton"), "Payments.DeleteSuccessMsg", "Payments.DeleteSuccessMsgCloseIcon");
    }


    // Verify Delete Payee Functionality
    public void verifyDeletePayee(String strdeleteBtn, String strSuccessMsg, String strSuccessCloseIcon) throws Exception {

        if (isElementDisplayed(getObject(strdeleteBtn), 5)) {
            clickJS(getObject(strdeleteBtn), "Click on 'Delete Payee'");
            verifyDeletePayeePopUpDetails();
            if (!dataTable.getData("General_Data", "Operation").equals("")) {
                String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                switch (strOperationOnPutonHold.toUpperCase()) {
                    case "DELETE PAYEE":
                        // Click on Delete Payee button
                        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Payments.PopUpBtnDeletePayee")));
                        click(getObject("Payments.PopUpBtnDeletePayee"), "'Delete Payee Button' on Delete Payee Pop Up");
                        Thread.sleep(2000);
                        // Verify success message on manage payees screen
                        if (isElementDisplayed(getObject(strSuccessMsg), 10)) {
                            report.updateTestLog("verifyDeletePayee", "Success Message 'Success: your payee has been deleted' is appearing on the screen ", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("verifyDeletePayee", "Success Message 'Success: your payee has been deleted' is not appearing on the screen", Status_CRAFT.FAIL);
                        }
                        //Click on Close Icon
                        if (isElementDisplayed(getObject(strSuccessCloseIcon), 10)) {
                            report.updateTestLog("verifyDeletePayee", "Success Close Icon is appearing on the screen ", Status_CRAFT.PASS);
                            click(getObject(strSuccessCloseIcon), "Success Close Icon");
                        } else {
                            report.updateTestLog("verifyDeletePayee", "Success Close Icon is not appearing on the screen", Status_CRAFT.FAIL);
                        }

                        break;
                    case "CANCEL":
                        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Payments.PopUpBtnCancel")));
                        Thread.sleep(2000);
                        clickJS(getObject("Payments.PopUpBtnCancel"), "'Cancel Button' on Delete Payee Pop up");
                        if (isElementDisplayed(getObject(deviceType() + "Payments.DeletePayeebutton"), 5)) {
                            report.updateTestLog("verifyDeletePayee", "User navigated to Manage Payees Page upon clicking 'Close Icon'", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("verifyDeletePayee", "User does not navigated to Manage Payees Page upon clicking 'Close Icon'", Status_CRAFT.FAIL);
                        }
                        break;
                    case "CLOSE":
                        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Payments.PopUpBtnCancel")));
                        Thread.sleep(2000);
                        clickJS(getObject("Payments.DeletePayeePopUpCloseIcon"), "'Close Icon' Pop up");
                        if (isElementDisplayed(getObject(deviceType() + "Payments.DeletePayeebutton"), 5)) {
                            report.updateTestLog("verifyDeletePayee", "User navigated to Manage Payees Page upon clicking 'Close Icon'", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("verifyDeletePayee", "User does not navigated to Manage Payees Page upon clicking 'Close Icon'", Status_CRAFT.FAIL);
                        }

                        break;

                    default:
                        report.updateTestLog("verifyDeletePayee", "Please provide the valid operation on Manage Payees Page, DELETE PAYEE or CANCEL", Status_CRAFT.FAIL);
                }
            }
        } else {
            if (isElementDisplayed(getObject("w.Payments.DeletePayeebutton"), 5)) {

                click(getObject("w.Payments.DeletePayeebutton"));
                verifyDeletePayeePopUpDetails();
                if (!dataTable.getData("General_Data", "Operation").equals("")) {
                    String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
                    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                    switch (strOperationOnPutonHold.toUpperCase()) {
                        case "DELETE PAYEE":
                            // Click on Delete Payee button
                            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Payments.PopUpBtnDeletePayee")));
                            click(getObject("Payments.PopUpBtnDeletePayee"), "'Delete Payee Button' on Delete Payee Pop Up");
                            Thread.sleep(2000);
                            // Verify success message on manage payees screen
                            if (isElementDisplayed(getObject(strSuccessMsg), 10)) {
                                report.updateTestLog("verifyDeletePayee", "Success Message 'Success: your payee has been deleted' is appearing on the screen ", Status_CRAFT.PASS);
                            } else {
                                report.updateTestLog("verifyDeletePayee", "Success Message 'Success: your payee has been deleted' is not appearing on the screen", Status_CRAFT.FAIL);
                            }
                            //Click on Close Icon
                            if (isElementDisplayed(getObject(strSuccessCloseIcon), 10)) {
                                report.updateTestLog("verifyDeletePayee", "Success Close Icon is appearing on the screen ", Status_CRAFT.PASS);
                                click(getObject(strSuccessCloseIcon), "Success Close Icon");
                            } else {
                                report.updateTestLog("verifyDeletePayee", "Success Close Icon is not appearing on the screen", Status_CRAFT.FAIL);
                            }

                            break;
                        case "CANCEL":
                            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Payments.PopUpBtnCancel")));
                            Thread.sleep(2000);
                            click(getObject("Payments.PopUpBtnCancel"), "'Cancel Button' on Delete Payee Pop up");
                            if (isElementDisplayed(getObject(deviceType() + "Payments.btnAddPayees"), 5)) {
                                report.updateTestLog("verifyDeletePayee", "User navigated to Manage Payees Page upon clicking 'Cancel Button'", Status_CRAFT.PASS);
                            } else {
                                report.updateTestLog("verifyDeletePayee", "User does not navigated to Manage Payees Page upon clicking 'Cancel Button'", Status_CRAFT.FAIL);
                            }
                            break;
                        case "CLOSE":
                            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Payments.PopUpBtnCancel")));
                            Thread.sleep(2000);
                            click(getObject("Payments.DeletePayeePopUpCloseIcon"), "'Close Icon' on Delete Payee Pop up");
                            if (isElementDisplayed(getObject((deviceType() + "Payments.DeletePayeebutton")), 5)) {
                                report.updateTestLog("verifyDeletePayee", "User navigated to Manage Payees Page upon clicking 'Close Icon'", Status_CRAFT.PASS);
                            } else {
                                report.updateTestLog("verifyDeletePayee", "User does not navigated to Manage Payees Page upon clicking 'Close Icon'", Status_CRAFT.FAIL);
                            }
                            break;
                        default:
                            report.updateTestLog("verifyDeletePayee", "Please provide the valid operation on Manage Payees Page, DELETE PAYEE or CANCEL", Status_CRAFT.FAIL);
                    }
                }
            } else {
                report.updateTestLog("verifyDeletePayee", "Delete Button is unavailable on Screen", Status_CRAFT.FAIL);
            }

        }
    }





    public void selectSendMoneyPayfrom() throws Exception {
        if (!dataTable.getData("General_Data", "PayFrom_Account").equals("")) {
            clickJS(getObject("Sendmoney.lstPayFromAccount"), "Pay From Account Dropdown");
            clickJS(getObject("SendMoney.payfrom"), "Select Account");
            report.updateTestLog("selectPayfromSendMoney", "Account Selected '" + dataTable.getData("General_Data", "PayFrom_Account") + "'", Status_CRAFT.DONE);
            Thread.sleep(2000);
        } else {
            report.updateTestLog("selectPayfromSendMoney", "Pay from Account dropdown is not displayed on Send Money landing Page", Status_CRAFT.FAIL);
            waitForPageLoaded();
        }
    }

    public void selectSendMoneyPayto() throws Exception {
        if (!dataTable.getData("General_Data", "PayFrom_Account").equals("")) {
            clickJS(getObject("SendMoney.lstPayToPayee"), "Pay To Account Dropdown ");
            clickJS(getObject("SendMoney.lblPayto"), "Select Account");
            report.updateTestLog("selectPayfromSendMoney", "Account Selected '" + dataTable.getData("General_Data", "PayFrom_Account") + "'", Status_CRAFT.DONE);
            Thread.sleep(2000);
        } else

        {
            report.updateTestLog("selectPayfromSendMoney", "Pay from Account dropdown is not displayed on Send Money landing Page", Status_CRAFT.FAIL);
            waitForPageLoaded();
        }
    }

    public void selectPayfromSendMoney() throws Exception {
        validatePageHeader();

        //verify section header
        if (isElementDisplayed(getObject("SendMoney.headerPayFrom"), 5)) {
            report.updateTestLog("selectPayfromSendMoney", "Section header'Pay from' is displayed on Send Money landing Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPayfromSendMoney", "Section header'Pay from' is NOT displayed on Send Money landing Page", Status_CRAFT.FAIL);
        }

        //validating field name 'Account'
        if (isElementDisplayed(getObject("StandingOrder.lblAccount"), 5)) {
            report.updateTestLog("selectPayfromSendMoney", "Field name 'Account'is displayed on Send Money landing Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPayfromSendMoney", "Field name 'Account'is NOT displayed on Send Money landing Page", Status_CRAFT.FAIL);
        }
        //Select account
        if (isElementDisplayed(getObject("Sendmoney.lstPayFromAccount"), 5)) {
            report.updateTestLog("selectPayfromSendMoney", "Dropdown of 'Pay from' is displayed on Send Money landing Page", Status_CRAFT.PASS);
            String strSelectAccount = dataTable.getData("General_Data", "PayFrom_Account");
            if (strSelectAccount.length() > 5 ) {
                strSelectAccount = strSelectAccount.substring(strSelectAccount.length() - 4);
            }
            if (!dataTable.getData("General_Data", "PayFrom_Account").equals("")) {
                clickJS(getObject("Sendmoney.lstPayFromAccount"), "Pay From Account Dropdown");
                Thread.sleep(3000);
                if(!isMobile) {
                    click(getObject("xpath~//button[contains(text(),'Select account')]/following-sibling::ul/li[contains(text(),'"+ strSelectAccount+"')]"));
                }else{
                     clickJS(getObject("xpath~//button[contains(text(),'Select account')]/following-sibling::ul/li[contains(text(),'"+ strSelectAccount +"')]"), "Pay From Account Dropdown");
                }

                report.updateTestLog("selectPayfromSendMoney", "Account Selected '" + dataTable.getData("General_Data", "PayFrom_Account") + "'", Status_CRAFT.DONE);
                Thread.sleep(4000);
            }
        } else {
            report.updateTestLog("selectPayfromSendMoney", "Pay from Account dropdown is not displayed on Send Money landing Page", Status_CRAFT.FAIL);
        }
        waitForPageLoaded();
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
    }


    public void selectPayToSendMoney() throws Exception {
        //verify section header
        if (isElementDisplayed(getObject("Sendmoney.lblPayTo"), 5)) {
            report.updateTestLog("selectPayToSendMoney", "Section header'Pay to' is displayed on Send Money landing Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPayToSendMoney", "Section header'Pay to' is NOT displayed on Send Money landing Page", Status_CRAFT.FAIL);
        }

        //Select Payee
        if (isElementDisplayed(getObject(deviceType() + "SendMoney.lblSelectPayee"), 5)) {
            report.updateTestLog("selectPayToSendMoney", "Label'Select Payee' is displayed on Send Money landing Page", Status_CRAFT.PASS);
        } else {
            if (isElementDisplayed(getObject("xpath~//*[text()='Select payee']"), 5)) {
                report.updateTestLog("selectPayToSendMoney", "Label'Select Payee' is displayed on Send Money landing Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPayToSendMoney", "Label header'Pay to' is NOT displayed on Send Money landing Page", Status_CRAFT.FAIL);
            }
        }
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
        Thread.sleep(3000);
        if (!dataTable.getData("General_Data", "PayTo_Account").equals("")) {
            if (deviceType().equals("w.")||deviceType().equals("tw.")) {
                if (isElementDisplayed(getObject(deviceType()+"SendMoney.lstPayToPayee"), 5)) {
                    report.updateTestLog("selectPayToSendMoney", "Dropdown of 'Select Payee' is displayed on Send Money landing Page", Status_CRAFT.PASS);
                    //validating directional text
                    if (getText(getObject("SendMoney.lblPayToPayeeDirectionalText")).equalsIgnoreCase("Choose a payee or bill")) {

                        report.updateTestLog("selectPayToSendMoney", "Directional text 'Please select' is correctly displayed on the Pay from Account dropdown", Status_CRAFT.PASS);

                    } else {
                        report.updateTestLog("selectPayToSendMoney", "Directional text 'Choose a payee or bill' is not as expected for Pay To drop down, Expected: 'Choose a payee or bill' :: Actual:'" + getText(getObject("SendMoney.lblPayToPayeeDirectionalText")) + "'", Status_CRAFT.FAIL);
                    }
                    while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                        waitForPageLoaded();
                    }
                    Thread.sleep(1000);
                    clickJS(getObject(deviceType()+"SendMoney.lstPayToPayee"), "Pay To Account Dropdown ");
                    while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                        waitForPageLoaded();
                    }
                    waitForElementPresent(By.xpath("//button[contains(text(),'Choose a payee or bill')]/following-sibling::ul/li[contains(.,'" + dataTable.getData("General_Data", "PayTo_Account") + "')]"), 20);
                    WebElement elm = driver.findElement(By.xpath("//button[contains(text(),'Choose a payee or bill')]/following-sibling::ul/li[contains(.,'" + dataTable.getData("General_Data", "PayTo_Account") + "')]"));
                    String curr = driver.findElement(By.xpath("//button[contains(text(),'Choose a payee or bill')]/following-sibling::ul/li[contains(.,'" + dataTable.getData("General_Data", "PayTo_Account") + "')]")).getText();
                    if (curr.contains(dataTable.getData("General_Data", "PayToCurrency"))) {
                        report.updateTestLog("selectPayToSendMoney", "Currency '" + dataTable.getData("General_Data", "PayToCurrency") + "' is displayed in drop down for Payee/Bill '" + dataTable.getData("General_Data", "PayTo_Account") + "'", Status_CRAFT.DONE);
                    } else {
                        report.updateTestLog("selectPayToSendMoney", "Currency '" + dataTable.getData("General_Data", "PayToCurrency") + "', Actual: '" + curr + "' is NOT displayed/incorrect in dropdown for Payee/Bill '" + dataTable.getData("General_Data", "PayTo_Account") + "'", Status_CRAFT.FAIL);
                    }
                    elm.click();
                    report.updateTestLog("selectPayToSendMoney", "Account/Bill Selected '" + dataTable.getData("General_Data", "PayTo_Account") + "'", Status_CRAFT.DONE);
                    waitForPageLoaded();
                } else {
                    report.updateTestLog("selectPayToSendMoney", "Pay To Payee dropdown is not displayed on Send Money landing Page", Status_CRAFT.FAIL);
                }
            } else {
                //waitForElementPresent(By.xpath("//span[(text()='" + dataTable.getData("General_Data", "PayTo_Account") + "')]   "), 10);
                clickJS(getObject(deviceType() + "SendMoney.lstPayToPayee"), "Pay To Account Dropdown ");
                while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 10)) {
                    waitForPageLoaded();
                }
//                String payeename;
//                if(dataTable.getData("General_Data", "PayTo_Account").contains("~")){
//                    payeename = dataTable.getData("General_Data", "PayTo_Account").split("~")[1].trim();
//                }else{
//                    payeename = dataTable.getData("General_Data", "PayTo_Account");
//                }
                waitForPageLoaded();waitForJQueryLoad();
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                String payeename = dataTable.getData("General_Data", "PayTo_Account");
                String strxpath = "";
                WebElement elm =null;
                if(payeename.contains("~")){
                    try{
                        strxpath= "//*[contains(@aria-label,'"+payeename.split("~")[0].trim()+"')]/ancestor::div[@role='button']/" +
                                "descendant::*[@class='ecDIB  '][not(@style='display: none;')]/descendant::*[contains(text(),'"+payeename.split("~")[1].trim()+"')]";
                        elm = driver.findElement(By.xpath(strxpath));
                    }catch(Exception e){
                        try{
                            strxpath= "//*[contains(@aria-label,'"+payeename.split("~")[0].trim()+"')]/ancestor::div[@role='button']/descendant::*[contains(@class,'ecDIB  ')]/descendant::*[contains(text(),'"+payeename.split("~")[1].trim()+"')]";
                            elm = driver.findElement(By.xpath(strxpath));
                        }catch(Exception f){}
                    }
                }else {
                    try {
                        waitForPageLoaded();
                        waitForPageLoaded();
                        //strxpath = "//div[@style='text-align: left; '][contains(@class,'ecDIB ')]/div/span[contains(text(),'" + payeename + "')]";
                        strxpath = "(//span[contains(text(),'" + payeename + "')]/ancestor::*[contains(@class,'col-full text-left')])[1]";
                        waitForPageLoaded();
                        waitForJQueryLoad();
                        js.executeScript("arguments[0].scrollIntoView(false);", driver.findElement(getObject(("xpath~" + strxpath))));
                        elm = driver.findElement(By.xpath(strxpath));
                        waitForPageLoaded();
                        waitForJQueryLoad();
                    }catch(Exception f){}
                }


                waitForPageLoaded();waitForJQueryLoad();
                js.executeScript("arguments[0].scrollIntoView(false);",elm);
                waitForPageLoaded();waitForJQueryLoad();
                js.executeScript("arguments[0].click();", elm );
                waitForJQueryLoad(); waitForPageLoaded();
                report.updateTestLog("selectPayToSendMoney", "Account/Bill Selected '" + dataTable.getData("General_Data", "PayTo_Account") + "'", Status_CRAFT.DONE);
                waitForPageLoaded();
            }
        }

        //validating the icons
        //add bill
        if (deviceType().equals("w.")) {
            waitForElementPresent(getObject("SendMoney.btnAddBill"), 5);
            if (isElementDisplayed(getObject("SendMoney.btnAddBill"), 5)) {
                report.updateTestLog("selectPayToSendMoney", "Button 'Add Bill' is displayed on Send Money landing Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPayToSendMoney", "Button 'Add Bill' is NOT displayed on Send Money landing Page", Status_CRAFT.FAIL);
            }

            //add person
            if (isElementDisplayed(getObject("SendMoney.btnAddPerson"), 5)) {
                report.updateTestLog("selectPayToSendMoney", "Button 'Add payee' is displayed on Send Money landing Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPayToSendMoney", "Button 'Add payee' is NOT displayed on Send Money landing Page", Status_CRAFT.FAIL);
            }
        }
    }

    public void sendmoney() throws Exception {

        //entering amount
        if (!dataTable.getData("General_Data", "Pay_Amount").equals("")) {
            sendKeys(getObject("SendMoney.txtCurrAmount"), dataTable.getData("General_Data", "Pay_Amount"));
            waitForPageLoaded();
        }
        //Reason for payment
        if (isElementDisplayed(getObject("SendMoney.lblReasonForPayment"), 5)) {
            String strPaymentReason = dataTable.getData("General_Data", "PaymentReason");
            report.updateTestLog("PayFromAccountSelection", "Label 'Reason for payment' is displayed on Send Money enter details Page", Status_CRAFT.PASS);
            click(getObject("SendMoney.lblReasonForPayment"), "Reason for Payment");
            if (!strPaymentReason.equals("")) {
                Thread.sleep(2000);
                driver.findElement(getObject("SendMoney.txtReasonForPayment")).sendKeys(strPaymentReason);
                //sendKeysJS(getObject("SendMoney.txtReasonForPayment"),strPaymentReason);
            }
        } else {
            report.updateTestLog("sendMoneyDetails", "Label 'Reason for payment' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }
        //Message to payee (optional)
        if (isElementDisplayed(getObject("SendMoney.lblMessageToPayee"), 5)) {
            report.updateTestLog("PayFromAccountSelection", "Label 'Message to payee' is displayed on Send Money enter details Page", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.lblMessageToPayee1"), 5)) {
                report.updateTestLog("PayFromAccountSelection", "Label 'Message line 1 (optional)' is displayed on Send Money enter details Page", Status_CRAFT.PASS);
                String MessageToPayee1 = dataTable.getData("General_Data", "MessageToPayee1");
                if (!MessageToPayee1.equals("")) {
                    sendKeys(getObject("SendMoney.txtMessageToPyaee1"), MessageToPayee1, "MessageToPayee1 entered is '" + MessageToPayee1 + "'");
                }
            } else {
                report.updateTestLog("sendMoneyDetails", "Label 'Message line 1' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("SendMoney.lblMessageToPayee2"), 5)) {
                report.updateTestLog("PayFromAccountSelection", "Label 'Message line 2 (optional)' is displayed on Send Money enter details Page", Status_CRAFT.PASS);
                String MessageToPayee2 = dataTable.getData("General_Data", "MessageToPayee2");
                if (!MessageToPayee2.equals("")) {
                    sendKeys(getObject("SendMoney.txtMessageToPyaee2"), MessageToPayee2, "MessageToPayee2 entered is '" + MessageToPayee2 + "'");
                }
            } else {
                report.updateTestLog("sendMoneyDetails", "Label 'Message line 2' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("SendMoney.lblMessageToPayee3"), 5)) {
                report.updateTestLog("PayFromAccountSelection", "Label 'Message line 3 (optional)' is displayed on Send Money enter details Page", Status_CRAFT.PASS);
                String MessageToPayee3 = dataTable.getData("General_Data", "MessageToPayee3");
                if (!MessageToPayee3.equals("")) {
                    sendKeys(getObject("SendMoney.txtMessageToPyaee3"), MessageToPayee3, "MessageToPayee3 entered is '" + MessageToPayee3 + "'");
                }
            } else {
                report.updateTestLog("sendMoneyDetails", "Label 'Message line 3' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
            }

        } else {
            report.updateTestLog("sendMoneyDetails", "Label 'Message to payee' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //continue & Goback
        if (isElementDisplayed(getObject("AddPayee.GoBack"), 5)) {
            report.updateTestLog("sendMoneyDetails", "Button 'Go back' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetails", "Button 'Go back' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.Continue"), 5)) {
            report.updateTestLog("sendMoneyDetails", "Button 'Continue' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetails", "Button 'Continue' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.Continue")));
                    Thread.sleep(2000);
                    clickJS(getObject("AddPayee.Continue"), "Clicked on 'Continue', on Page");
                    break;
                case "GO BACK":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.GoBack")));
                    Thread.sleep(2000);
                    click(getObject("AddPayee.GoBack"), "Clicked on 'Go back', on Page");
                    if (isElementDisplayed(getObject("xpath~//h1[@title='Payments']"), 5)) {
                        report.updateTestLog("sendMoneyDetails", "Page navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("sendMoneyDetails", "Page does not navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("sendMoneyDetails", "Please provide the valid operation on Send Money Page, Continue or Go Back", Status_CRAFT.FAIL);
            }
        }

    }

    /*
    CFPUR-5637: Credit Card Pay Now - balance zero
    */

    public void clickpaynow() throws Exception {
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
        clickJS(getObject(deviceType() + "SendMoney.btnPaynow"), "'Pay now'");
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
        isElementDisplayedWithLog(getObject(deviceType() + "SendMoney.PageHeader"), "Send Money Page Header", "Send Money Page", 5);
    }

    public void clickContinue() throws Exception {
        if (isElementDisplayedWithLog(getObject("SendMoney.btnContinue"), "Continue", "Send Money Page", 5)) {
            clickJS(getObject("SendMoney.btnContinue"), "'Continue'");
        } else {
            report.updateTestLog("clickContinue", "Continue button is NOT displayed on Send Money Input Details page", Status_CRAFT.FAIL);
        }

    }

    public void verifyprefilledamntreview() throws Exception {
        String strAmountIn = dataTable.getData("General_Data", "Current_Balance");
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
        if (isElementDisplayed(getObject("xpath~//*[text()='Amount']"), 5)) {
            report.updateTestLog("BillPaymentsReviewPage", "Label 'Amount' is displayed on Send Money Review page", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("xpath~//*[text()='Amount']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/div"), 5)) {
                String actAmount = getText(getObject("xpath~//*[text()='Amount']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/div"));
                if (actAmount.contains(strAmountIn)) {
                    report.updateTestLog("BillPaymentsReviewPage", "Aomount '" + strAmountIn + "' is appearing on Review Page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("BillPaymentsReviewPage", "Aomount '" + strAmountIn + "' is not appearing on Review Page", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("BillPaymentsReviewPage", "Label '" + strAmountIn + "' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }
    }

    public void clickGoBack() throws Exception {
        if (isElementDisplayed(getObject("AddPayee.GoBack"), 5)) {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Button 'Go back' is displayed on Page", Status_CRAFT.PASS);
            clickJS(getObject("AddPayee.GoBack"), "'Go back'");
        } else {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Button 'Go back' is NOT displayed on Page", Status_CRAFT.FAIL);
        }
    }

    public void sendMoneyDetails() throws Exception {
        //link currency converter
        if (isElementDisplayed(getObject("SendMoney.lblCurrencyConverter"), 5)) {
            report.updateTestLog("sendMoneyDetails", "Link 'Currency converter' is displayed on Send Money enter details Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetails", "Link 'Currency converter' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //currency label and tooltip
        if (isElementDisplayed(getObject("SendMoney.lblCurrency"), 5)) {
            report.updateTestLog("sendMoneyDetails", "Label 'Currency' and tooltip Icon is displayed on Send Money enter details Page", Status_CRAFT.PASS);

            //Currency and tooltip
            Actions action = new Actions((WebDriver) driver.getWebDriver());
            action.moveToElement(driver.findElement(getObject("SendMoney.lblCurrency"))).clickAndHold().build().perform();
            String strExpectedBICToolTip = "Bank Identifier Code (BIC) is the SWIFT address assigned to a Payee's bank. A BIC consists of eight or eleven characters. if you choose not to provide a BIC, we will use the IBAN to determine it.";
            String strUIBicToolTip = getText(getObject("SetupStandingOrder.elmBICToolTip"));

            String strExpectedToolTip = dataTable.getData("General_Data", "ToolTipText");
            String strUICurrToolTip = getText(getObject("SendMoney.elmCurrencyToolTip"));
            action.moveToElement(driver.findElement(getObject("SendMoney.lblCurrency"))).release().build().perform();

            if (strUICurrToolTip.equals(strExpectedToolTip)) {
                report.updateTestLog("sendMoneyDetails", "Currency tooltip is correctly displayed on Send Money enter details Page, Expected '" + strExpectedToolTip + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetails", "Currency tooltip is NOT correctly displayed on Send Money enter details Page, Actual '" + strUICurrToolTip + "', Expected;'" + strExpectedToolTip + "'", Status_CRAFT.FAIL);
            }

            String strCurrAvailable = dataTable.getData("General_Data", "SingleOrMultipleCurrency");
            //if more than 1 currency, currency drop down containing available currencies is displayed else label with currency details
            //'select currency you want to send'  only for NI//Gb
            if (strCurrAvailable.equalsIgnoreCase("Multiple")) {
                //add the code here for currency selection from the dropdown
            } else {
                String uiCurrency = getText(getObject("SendMoney.elmTextBoxCurrency"));
                String expectedCurrency = dataTable.getData("General_Data", "DefaultCurrency");
                if (uiCurrency.equals(expectedCurrency)) {
                    report.updateTestLog("sendMoneyDetails", "Currency in Textbox is correctly displayed on Send Money enter details Page, Expected '" + expectedCurrency + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("sendMoneyDetails", "Currency in Textbox is correctly displayed on Send Money enter details Page, Expected '" + expectedCurrency + "'", Status_CRAFT.FAIL);
                }
            }

        } else {
            report.updateTestLog("sendMoneyDetails", "Label 'Currency' and tooltip Icon is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //Amount
        if (isElementDisplayed(getObject("SendMoney.lblCurrAmount"), 5)) {
            report.updateTestLog("sendMoneyDetails", "Label 'Amount' and tooltip Icon is displayed on Send Money enter details Page", Status_CRAFT.PASS);

            Actions action = new Actions((WebDriver) driver.getWebDriver());
            action.moveToElement(driver.findElement(getObject("SendMoney.lblCurrAmount"))).clickAndHold().build().perform();

            String strExpectedToolTip = "Some foreign banks may deduct charges from this amount. These charges can vary and may have no maximum limit.";
            String strUICurrToolTip = getText(getObject("SendMoney.lblCurrAmountTooltip"));
            action.moveToElement(driver.findElement(getObject("SendMoney.lblCurrAmount"))).release().build().perform();

            if (strUICurrToolTip.equals(strExpectedToolTip)) {
                report.updateTestLog("sendMoneyDetails", "Amount tooltip is correctly displayed on Send Money enter details Page, Expected '" + strExpectedToolTip + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetails", "Amount tooltip is NOT correctly displayed on Send Money enter details Page, Actual '" + strUICurrToolTip + "', Expected;'" + strExpectedToolTip + "'", Status_CRAFT.FAIL);
            }

            String uiCurrency = getText(getObject("SendMoney.lblCurrSymbol"));
            String expectedCurrency = dataTable.getData("General_Data", "Currency_Symbol");
            if (uiCurrency.equals(expectedCurrency)) {
                report.updateTestLog("sendMoneyDetails", "Currency in Amount textbox is correctly displayed on Send Money enter details Page, Expected '" + expectedCurrency + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetails", "Currency in Amount textbox is not correctly displayed on Send Money enter details Page, Expected '" + expectedCurrency + "'", Status_CRAFT.FAIL);
            }

            //entering amount
            if (!dataTable.getData("General_Data", "Pay_Amount").equals("")) {
                sendKeys(getObject("SendMoney.txtCurrAmount"), dataTable.getData("General_Data", "Pay_Amount"));
                waitForPageLoaded();
            }
        } else {
            report.updateTestLog("sendMoneyDetails", "Label 'Amount' and tooltip Icon is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //Pad 2 content

        //Reason for payment
        if (isElementDisplayed(getObject("SendMoney.lblReasonForPayment"), 5)) {
            String strPaymentReason = dataTable.getData("General_Data", "PaymentReason");
            report.updateTestLog("PayFromAccountSelection", "Label 'Reason for payment' is displayed on Send Money enter details Page", Status_CRAFT.PASS);
            click(getObject("SendMoney.txtReasonForPayment"), "Reason for payment");
            if (!strPaymentReason.equals("")) {
                Thread.sleep(2000);
                driver.findElement(getObject("SendMoney.txtReasonForPayment")).sendKeys(strPaymentReason);
                //sendKeysJS(getObject("SendMoney.txtReasonForPayment"),strPaymentReason);
            }
        } else {
            report.updateTestLog("sendMoneyDetails", "Label 'Reason for payment' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //Message to payee (optional)
        waitForElementPresent(getObject("SendMoney.lblMessageToPayee"), 5);
        if (isElementDisplayed(getObject("SendMoney.lblMessageToPayee"), 5)) {
            report.updateTestLog("PayFromAccountSelection", "Label 'Message to payee (optional)' is displayed on Send Money enter details Page", Status_CRAFT.PASS);

            String MessageToPayee1 = dataTable.getData("General_Data", "MessageToPayee1");
            if (!MessageToPayee1.equals("")) {
                sendKeys(getObject("SendMoney.txtMessageToPyaee1"), MessageToPayee1, "MessageToPayee1 entered is '" + MessageToPayee1 + "'");
            }

            String MessageToPayee2 = dataTable.getData("General_Data", "MessageToPayee2");
            if (!MessageToPayee2.equals("")) {
                sendKeys(getObject("SendMoney.txtMessageToPyaee2"), MessageToPayee2, "MessageToPayee2 entered is '" + MessageToPayee2 + "'");
            }

            String MessageToPayee3 = dataTable.getData("General_Data", "MessageToPayee3");
            if (!MessageToPayee3.equals("")) {
                sendKeys(getObject("SendMoney.txtMessageToPyaee3"), MessageToPayee3, "MessageToPayee3 entered is '" + MessageToPayee3 + "'");
            }

        } else {
            report.updateTestLog("sendMoneyDetails", "Label 'Reason for payment' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //continue & Goback
        if (isElementDisplayed(getObject("AddPayee.GoBack"), 5)) {
            report.updateTestLog("sendMoneyDetails", "Button 'Go back' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetails", "Button 'Go back' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.Continue"), 5)) {
            report.updateTestLog("sendMoneyDetails", "Button 'Continue' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetails", "Button 'Continue' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.Continue")));
                    Thread.sleep(2000);
                    click(getObject("AddPayee.Continue"), "Clicked on 'Continue', on Page");
                    break;
                case "GO BACK":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.GoBack")));
                    Thread.sleep(2000);
                    click(getObject("AddPayee.GoBack"), "Clicked on 'Go back', on Page");
                    if (isElementDisplayed(getObject("xpath~//h1[@title='Payments']"), 5)) {
                        report.updateTestLog("sendMoneyDetails", "Page navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("sendMoneyDetails", "Page does not navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("sendMoneyDetails", "Please provide the valid operation on Send Money Page, Continue or Go Back", Status_CRAFT.FAIL);
            }
        }
    }

    public void internationalPaymentsReviewPage() throws Exception {
        //validating page title
        validatePageHeader();

        //Section header-Review
        if (isElementDisplayed(getObject("xpath~//h3[text()='Review']"), 5)) {
            //ios// if (isElementDisplayed(getObject("xpath~//XCUIElementTypeStaticText[@name="Review"]"), 5))
            report.updateTestLog("internationalPaymentsReviewPage", "Section header 'Review' is displayed on Send Money Review page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("internationalPaymentsReviewPage", "Section header 'Review' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }

        //Pay in
        String strPayin = "Pay in " + dataTable.getData("General_Data", "Currency_Symbol");
        if (isElementDisplayed(getObject("xpath~//*[text()='" + strPayin + "']"), 5)) {
            report.updateTestLog("internationalPaymentsReviewPage", "Label '" + strPayin + "' is displayed on Send Money Review page", Status_CRAFT.PASS);
            String strExpected = dataTable.getData("General_Data", "Currency_Symbol") + " " + dataTable.getData("General_Data", "Pay_Amount");
            String strUiPayinValue = getText(getObject("xpath~//*[text()='" + strPayin + "']/../../following-sibling::div[@style='text-align: right; ']/div"));
            if (strUiPayinValue.equals(strExpected)) {
                report.updateTestLog("internationalPaymentsReviewPage", "Label '" + strPayin + "' value on right side is correct,'" + strUiPayinValue + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("internationalPaymentsReviewPage", "Label '" + strPayin + "' value on right side is incorrect,Expected'" + strExpected + "', Actual '" + strUiPayinValue + "'", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("internationalPaymentsReviewPage", "Label '" + strPayin + "' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }
        //Exchange rate
        if (isElementDisplayed(getObject("xpath~//*[text()='Exchange rate']"), 5)) {
            report.updateTestLog("internationalPaymentsReviewPage", "Label 'Exchange rate' is displayed on Send Money Review page", Status_CRAFT.PASS);
            String strExchangeRate = getText(getObject("xpath~//*[text()='Exchange rate']/../../following-sibling::div[@style='text-align: right; ']"));
            if (!strExchangeRate.equals("")) {
                report.updateTestLog("internationalPaymentsReviewPage", "Label 'Exchange rate' value on right side is correct,'" + strExchangeRate + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("internationalPaymentsReviewPage", "Label 'Exchange rate' value on right side is blank", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("internationalPaymentsReviewPage", "Label 'Exchange rate' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }

        //Amount in
        String strAmountIn = "Amount in " + dataTable.getData("General_Data", "AmountInCurrency");
        if (isElementDisplayed(getObject("xpath~//*[text()='" + strAmountIn + "']"), 5)) {
            report.updateTestLog("internationalPaymentsReviewPage", "Label '" + strAmountIn + "' is displayed on Send Money Review page", Status_CRAFT.PASS);

        } else {
            report.updateTestLog("internationalPaymentsReviewPage", "Label '" + strAmountIn + "' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }

        //Bank of Ireland charge
        if (isElementDisplayed(getObject("xpath~//*[text()='Bank of Ireland charge']"), 5)) {
            report.updateTestLog("internationalPaymentsReviewPage", "Label 'Bank of Ireland charge' is displayed on Send Money Review page", Status_CRAFT.PASS);
            String strBOICharge = getText(getObject("xpath~//*[text()='Bank of Ireland charge']/../../following-sibling::div[@style='text-align: right; ']"));
            if (strBOICharge.contains(dataTable.getData("General_Data", "AmountInCurrency_Symbol"))) {
                report.updateTestLog("internationalPaymentsReviewPage", "Label 'Bank of Ireland charge' value on right side is ,'" + strBOICharge + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("internationalPaymentsReviewPage", "Label 'Bank of Ireland charge' value on right side is blank", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("internationalPaymentsReviewPage", "Label 'Bank of Ireland charge' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }
        //Total to be debited
        if (isElementDisplayed(getObject("xpath~//*[text()='Total to be debited']"), 5)) {
            report.updateTestLog("internationalPaymentsReviewPage", "Label 'Total to be debited' is displayed on Send Money Review page", Status_CRAFT.PASS);
            String strTotalDebited = getText(getObject("xpath~//*[text()='Total to be debited']/../../following-sibling::div[@style='text-align: right; ']"));
            if (strTotalDebited.contains(dataTable.getData("General_Data", "AmountInCurrency_Symbol"))) {
                report.updateTestLog("internationalPaymentsReviewPage", "Label 'Total to be debited' value on right side is ,'" + strTotalDebited + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("internationalPaymentsReviewPage", "Label 'Total to be debited' value on right side is blank", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("internationalPaymentsReviewPage", "Label 'Total to be debited' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }

        //foreign bank charges
        String strNotification = "Foreign bank charges: Some foreign banks may deduct charges from the amount you send. These charges can vary and may have no maximum limit.";
        if (getText(getObject("xpath~//*[@class='boi-alert-content']")).equals(strNotification)) {
            report.updateTestLog("internationalPaymentsReviewPage", "Warning message'" + strNotification + "' is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("internationalPaymentsReviewPage", "Warning message'" + strNotification + "' is NOT displayed/incorrect,'Actual '" + getText(getObject("xpath~//*[@class=’boi-alert-content’]")) + "'", Status_CRAFT.FAIL);
        }

        //Section Header Details
        if (isElementDisplayed(getObject("xpath~//h4[text()='Details']"), 5)) {
            report.updateTestLog("internationalPaymentsReviewPage", "Section header 'Details' is displayed on Send Money Review page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("internationalPaymentsReviewPage", "Section header 'Details' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }

        //validate review section details
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {
            if (!obj.getValue().equals("NA")) {

                if (obj.getKey().equals("Message to payee")) {
                    if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
                        report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' displayed on review page", Status_CRAFT.PASS);
                        String expectedVal = getText(getObject("xpath~//*[text()='Message to payee']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::fieldset")).replaceAll("\n", " ");
                        if (expectedVal.equals(obj.getValue())) {
                            report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
                        }
                    } else {
                        report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' Not displayed on review page", Status_CRAFT.FAIL);
                    }
                } else {
                    boolean bflag = driver.findElement(By.xpath("//*[text()='" + obj.getKey() + "']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[contains(text(),'" + obj.getValue() + "')]")).isDisplayed();
                    if (bflag) {
                        report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
                    }
                }

            } else {
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
                    report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' is displayed on review page for blank input", Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' is Not displayed on review page for blank input", Status_CRAFT.PASS);
                }
            }
        }

        //validateDailylimitsNCutOffTimes();
        SendMoneyReviewPageOperation();

    }

    public void validatePageHeader() throws Exception {
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 3)) {
            waitForPageLoaded();
        }
        //validating page title
        String strPageHeader = dataTable.getData("General_Data", "PageHeader");
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("xpath~//h1[not(contains(@class,'mobile'))][text()='" + strPageHeader + "']"), 5)) {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeader + "' is displayed on Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeader + "' is NOT displayed on Page", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject("xpath~//h1[contains(@class,'boi-mobile-header')][text()='" + strPageHeader + "']"), 5))
            // if (isElementDisplayed(getObject("xpath~//XCUIElementTypeStaticText[@name='" + strPageHeader + "']"), 5))
            {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeader + "' is displayed on Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeader + "' is NOT displayed on Page", Status_CRAFT.FAIL);
            }
        }
    }

    public void validateDailylimitsNCutOffTimes() throws Exception {

        if (isElementDisplayed(getObject("SendMoney.lblDailyLimits"), 5)) {
            report.updateTestLog("validateDailylimitsNCutOffTimes", "Label 'Daily limits' is displayed on Review page", Status_CRAFT.PASS);
            if (dataTable.getData("General_Data", "Currency_Symbol").equalsIgnoreCase("€")) {
                if (isElementDisplayed(getObject("xpath~//div[contains(text(),'Your payments are limited to a total of €20,000 per working day.')]"), 10)) {
                    report.updateTestLog("validateDailylimitsNCutOffTimes", "Text 'Your payments are limited to a total of €20,000 per working day. More about daily limits.' is displayed on Review page", Status_CRAFT.PASS);
                    scrollToView(getObject("xpath~//div[text()='Your payments are limited to a total of €20,000 per working day. ']/*[text()='More about daily limits.']"));
//                    click(getObject("xpath~//div[text()='Your payments are limited to a total of €20,000 per working day. ']/*[text()='More about daily limits.']"), "'More about daily Limits' link");
//                    validateURLOpens("https://www.bankofireland.com/help-centre/faq/much-can-transfer-365-online/");
                } else {
                    report.updateTestLog("validateDailylimitsNCutOffTimes", "Text 'Your payments are limited to a total of €20,000 per working day. More about daily limits.' is NOT displayed on Review page", Status_CRAFT.FAIL);
                }
            } else {
                if ((dataTable.getData("General_Data", "Currency_Symbol").equalsIgnoreCase("£"))) {
                    if (isElementDisplayed(getObject("xpath~//div[contains(text(),'Your payments are limited to a total of £20,000 per working day.')]"), 10)) {
                        report.updateTestLog("validateDailylimitsNCutOffTimes", "Text 'Your payments are limited to a total of £20,000 per working day. More about daily limits.' is displayed on Review page", Status_CRAFT.PASS);
                        scrollToView(getObject("xpath~//div[text()='Your payments are limited to a total of £20,000 per working day. ']/*[text()='More about daily limits.']"));
//                        click(getObject("xpath~//div[text()='Your payments are limited to a total of £20,000 per working day. ']/*[text()='More about daily limits.']"), "'More about daily Limits' link");
//                        validateURLOpens("https://www.bankofireland.com/help-centre/faq/much-can-transfer-365-online/");
                    } else {
                        report.updateTestLog("validateDailylimitsNCutOffTimes", "Text 'Your payments are limited to a total of £20,000 per working day. More about daily limits.' is NOT displayed on Review page", Status_CRAFT.FAIL);
                    }
                }
            }
        } else {
            report.updateTestLog("validateDailylimitsNCutOffTimes", "Label 'Daily limits' is NOT displayed on Review page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("SendMoney.lblCutOffTimes"), 5)) {
            report.updateTestLog("validateDailylimitsNCutOffTimes", "Label 'Cut-off times' is displayed on Review page", Status_CRAFT.PASS);
            if (dataTable.getData("General_Data", "Currency_Symbol").equalsIgnoreCase("€")) {
                if (isElementDisplayed(getObject("SendMoney.lblCutOffTimesTextEUR"), 5)) {
                    report.updateTestLog("validateDailylimitsNCutOffTimes", "Text 'Money sent after 3:30pm, or at any time on a Saturday, Sunday or bank holiday, and we will start processing it on the next working day. More about cut-off times.' is displayed on Review page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("validateDailylimitsNCutOffTimes", "Text 'Money sent after 3:30pm, or at any time on a Saturday, Sunday or bank holiday, and we will start processing it on the next working day. More about cut-off times.' is NOT displayed on Review page", Status_CRAFT.FAIL);
                }
            } else {
                if (dataTable.getData("General_Data", "Currency_Symbol").equalsIgnoreCase("£")) {
                    if (isElementDisplayed(getObject("SendMoney.lblCutOffTimesTextGBP"), 5)) {
                        report.updateTestLog("validateDailylimitsNCutOffTimes", "Text 'Money sent after 3:30pm, or at any time on a Saturday, Sunday or Bank Holiday, and we will start processing it on the next working day. More about cut-off times.' is displayed on Review page", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("validateDailylimitsNCutOffTimes", "Text 'Money sent after 4:30pm, or at any time on a Saturday, Sunday or Bank Holiday, and we will start processing it on the next working day. More about cut-off times.' is NOT displayed on Review page", Status_CRAFT.FAIL);
                    }
                }
            }
        } else {
            report.updateTestLog("validateDailylimitsNCutOffTimes", "Label 'Cut-off times' is NOT displayed on Review page", Status_CRAFT.FAIL);
        }

    }

    public void validateURLOpens(String strUrl) throws Exception {

        String ParentTab = driver.getWindowHandle();
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        newTab.remove(ParentTab);
        driver.switchTo().window(newTab.get(0));
//        if (strUrl.equals(driver.getCurrentUrl()))
            if (driver.getCurrentUrl().contains(strUrl)){
            report.updateTestLog("validateURLOpens", "Expected Url '" + strUrl + "' opens upon clicking the link", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateURLOpens", "Expected Url '" + strUrl + "' does NOT opens upon clicking the link, Actual:'" + driver.getCurrentUrl() + "'", Status_CRAFT.FAIL);
        }
        driver.close();
        Thread.sleep(2000);
        newTab.add(ParentTab);
        driver.switchTo().window(newTab.get(1));
    }

    public void sendMoneyConfirmationPage() throws Exception {
        //This function needs to be updated from scratch
        //validating page title 'Set up Standing Order'
        if (isElementDisplayed(getObject(deviceType() + "SetupStandingOrder.hdrPageTitle"), 5)) {
            report.updateTestLog("SetupSOConfirmationPage", "Page Title 'Set up Standing Order' is displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SetupSOConfirmationPage", "Page Title 'Set up Standing Order' is NOT displayed on Confirmation Page", Status_CRAFT.FAIL);
        }


        if (isElementDisplayed(getObject("SetupStandingOrder.imgCalender"), 5)) {
            report.updateTestLog("SetupSOConfirmationPage", "Calender Icon is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SetupSOConfirmationPage", "Calender Icon is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("SetupStandingOrder.lblPleaseActivate"), 5)) {
            report.updateTestLog("SetupSOConfirmationPage", "Success Icon heading 'Please activate' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SetupSOConfirmationPage", "Success Icon heading 'Please activate' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("StandingOrder.lblThankYou"), 5)) {
            report.updateTestLog("SetupSOConfirmationPage", "Text message 'Thank you!' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SetupSOConfirmationPage", "Text message 'Thank you!' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("StandingOrderDetails.lblInformationMsg_Inactive"), 5)) {
            report.updateTestLog("SetupSOConfirmationPage", "Informative message 'Once activated, this will be added to your list of standing orders within 1 working day.' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SetupSOConfirmationPage", "Informative message 'Once activated, this will be added to your list of standing orders within 1 working day.'is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("StandingOrder.btnBacktoHome"), 5)) {
            report.updateTestLog("SetupSOConfirmationPage", "Button 'Back to home' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SetupSOConfirmationPage", "Button 'Back to home' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }


        if (isElementDisplayed(getObject("StandingOrder.lblTimeOfRequest"), 5)) {
            report.updateTestLog("SetupSOConfirmationPage", "Time Stamp with message 'Time of request' is displayed on Confirmation page", Status_CRAFT.PASS);

            String strRequestSubmitted = getText(getObject("StandingOrder.lblTimeOfRequest"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("StandingOrder.lblTimeOfRequest", "Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("StandingOrder.lblTimeOfRequest", "Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("SetupSOConfirmationPage", "Time Stamp with message 'Time of request' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        //Validating Navigation/functionality of the buttons
        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to home")) {
            click(getObject("StandingOrder.btnBacktoHome"), "Clicked on 'Back to home'");
            if (isElementDisplayed(getObject("home.sectionTimeLine"), 5)) {
                report.updateTestLog("SetupSOConfirmationPage", "Upon clicking 'Back to home' ,successfully Navigated to Home page from Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("SetupSOConfirmationPage", "Upon clicking 'Back to home' ,navigation unsuccessful to Home page from Confirmation Page", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * CFPUR-2800 : Payment : Happy Path for ROI User only smoke pack
     */
    public void SendMoneyFormHappyPath_SmokePack() throws Exception {
        String strAccountNumber = dataTable.getData("General_Data", "Account_Name");
        String strPayee = dataTable.getData("General_Data", "Nickname");
        String strPayeeIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strPayeeBIC = dataTable.getData("General_Data", "BIC_Number");
        String strAmountToSend = dataTable.getData("General_Data", "Current_Balance");
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");
        String strBackNavigation = dataTable.getData("General_Data", "Operation");

        Actions action = new Actions((WebDriver) driver.getWebDriver());
        action.moveToElement(driver.findElement(getObject("SendMoney.lblCurrAmount"))).clickAndHold().build().perform();
        //Navigate to till Send Money Page From Home Page
        driver.findElement(By.xpath("//*[text()='PAYMENTS']")).click();

        String strExpectedToolTip = "Some foreign banks may deduct charges from this amount. These charges can vary and may have no maximum limit.";
        String strUICurrToolTip = getText(getObject("SendMoney.lblCurrAmountTooltip"));
        action.moveToElement(driver.findElement(getObject("SendMoney.lblCurrAmount"))).release().build().perform();
        //Check the icon send money
        report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        if (isElementDisplayed(getObject("SendMoney.iconSendMoney"), 3)) {
            report.updateTestLog("SendMoneyFormHappyPath", " Send Money icon is visible.", Status_CRAFT.DONE);
            click(getObject("SendMoney.iconSendMoney"));
        } else {
            report.updateTestLog("SendMoneyFormHappyPath", " Send Money icon is not visible..!!", Status_CRAFT.FAIL);
        }

        if (strUICurrToolTip.equals(strExpectedToolTip)) {
            report.updateTestLog("sendMoneyDetails", "Amount tooltip is correctly displayed on Send Money enter details Page, Expected '" + strExpectedToolTip + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetails", "Amount tooltip is NOT correctly displayed on Send Money enter details Page, Actual '" + strUICurrToolTip + "', Expected;'" + strExpectedToolTip + "'", Status_CRAFT.FAIL);
        }

        String uiCurrency = getText(getObject("SendMoney.lblCurrSymbol"));
        String expectedCurrency = dataTable.getData("General_Data", "Currency_Symbol");
        if (uiCurrency.equals(expectedCurrency)) {
            report.updateTestLog("sendMoneyDetails", "Currency in Amount textbox is correctly displayed on Send Money enter details Page, Expected '" + expectedCurrency + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetails", "Currency in Amount textbox is not correctly displayed on Send Money enter details Page, Expected '" + expectedCurrency + "', Actual: '" + uiCurrency + "'", Status_CRAFT.FAIL);
        }

        //entering amount
        if (!dataTable.getData("General_Data", "Pay_Amount").equals("")) {
            sendKeys(getObject("SendMoney.txtCurrAmount"), dataTable.getData("General_Data", "Pay_Amount"));
            waitForPageLoaded();
        } else {
            report.updateTestLog("sendMoneyDetails", "Label 'Amount' and tooltip Icon is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //Pad 2 content

        //Reason for payment
        if (isElementDisplayed(getObject("SendMoney.lblReasonForPayment"), 5)) {
            String strPaymentReason = dataTable.getData("General_Data", "PaymentReason");
            report.updateTestLog("PayFromAccountSelection", "Label 'Reason for payment' is displayed on Send Money enter details Page", Status_CRAFT.PASS);
            click(getObject("SendMoney.lblReasonForPayment"));
            if (!strPaymentReason.equals("")) {
                Thread.sleep(2000);
                driver.findElement(getObject("SendMoney.txtReasonForPayment")).sendKeys(strPaymentReason);
                //sendKeysJS(getObject("SendMoney.txtReasonForPayment"),strPaymentReason);
            }
        } else {
            report.updateTestLog("sendMoneyDetails", "Label 'Reason for payment' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //Message to payee (optional)
        if (isElementDisplayed(getObject("SendMoney.lblMessageToPayee"), 5)) {
            report.updateTestLog("PayFromAccountSelection", "Label 'Message to payee (optional)' is displayed on Send Money enter details Page", Status_CRAFT.PASS);

            String MessageToPayee1 = dataTable.getData("General_Data", "MessageToPayee1");
            if (!MessageToPayee1.equals("")) {
                sendKeys(getObject("SendMoney.txtMessageToPyaee1"), MessageToPayee1, "MessageToPayee1 entered is '" + MessageToPayee1 + "'");
            }

            String MessageToPayee2 = dataTable.getData("General_Data", "MessageToPayee2");
            if (!MessageToPayee2.equals("")) {
                sendKeys(getObject("SendMoney.txtMessageToPyaee2"), MessageToPayee2, "MessageToPayee2 entered is '" + MessageToPayee2 + "'");
            }

            String MessageToPayee3 = dataTable.getData("General_Data", "MessageToPayee3");
            if (!MessageToPayee3.equals("")) {
                sendKeys(getObject("SendMoney.txtMessageToPyaee3"), MessageToPayee3, "MessageToPayee3 entered is '" + MessageToPayee3 + "'");
            }

        } else {
            report.updateTestLog("sendMoneyDetails", "Label 'Reason for payment' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //continue & Goback
        if (isElementDisplayed(getObject("AddPayee.GoBack"), 5)) {
            report.updateTestLog("sendMoneyDetails", "Button 'Go back' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetails", "Button 'Go back' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.Continue"), 5)) {
            report.updateTestLog("sendMoneyDetails", "Button 'Continue' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetails", "Button 'Continue' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.Continue")));
                    Thread.sleep(2000);
                    click(getObject("AddPayee.Continue"), "Clicked on 'Continue', on Page");
                    break;
                case "GO BACK":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.GoBack")));
                    Thread.sleep(2000);
                    click(getObject("AddPayee.GoBack"), "Clicked on 'Go back', on Page");
                    if (isElementDisplayed(getObject("xpath~//h1[@title='Payments']"), 5)) {
                        report.updateTestLog("sendMoneyDetails", "Page navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("sendMoneyDetails", "Page does not navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("sendMoneyDetails", "Please provide the valid operation on Send Money Page, Continue or Go Back", Status_CRAFT.FAIL);
            }
        }
    }

    public void verifyCreditCardWarningtxt() throws Exception {
        String Expcontenttxt = dataTable.getData("General_Data", "VerifyContent");
        if (!dataTable.getData("General_Data", "JurisdictionType").equals("")) {
            String Contenttxt = dataTable.getData("General_Data", "JurisdictionType");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (Contenttxt) {
                case "ROI":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("xpath~//*[@class='boi-alert-content boi_input']/span[contains(text(),'Payments before')]/..")));
                    String ActcontenttxtROI = getText(getObject("xpath~//*[@class='boi-alert-content boi_input']/span[contains(text(),'Payments before')]/.."));
                    if (ActcontenttxtROI.equals(Expcontenttxt)) {
                        report.updateTestLog("verifyCreditCardWarningMsg", "'" + Expcontenttxt + "' warning text is displayed on screen ", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyCreditCardWarningMsg", "'" + Expcontenttxt + "' warning text is not displayed on screen ", Status_CRAFT.FAIL);
                    }
                    break;

                case "NI/GB":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("xpath~//*[@class='boi-alert-content boi_input']/span[contains(text(),'Payments before')]/..")));
                    String ActcontenttxtNIGB = getText(getObject("xpath~//*[@class='boi-alert-content boi_input']/span[contains(text(),'Payments before')]/.."));
                    if (ActcontenttxtNIGB.equals(Expcontenttxt)) {
                        report.updateTestLog("verifyCreditCardWarningMsg", "'" + Expcontenttxt + "' warning text is displayed on screen ", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyCreditCardWarningMsg", "'" + Expcontenttxt + "' warning text is not displayed on screen ", Status_CRAFT.FAIL);
                    }
                    break;
            }
        }
    }

    public void verifyBOIMortgageWarningtxt(String Warningtxt) throws Exception {

        String Expcontenttxt = dataTable.getData("General_Data", "VerifyContent");
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(Warningtxt)));
        String Actcontenttxt = getText(getObject(Warningtxt));
        if (Actcontenttxt.equals(Expcontenttxt)) {
            report.updateTestLog("verifyBOIMortgageWarningtxt", "'" + Expcontenttxt + "' warning text is displayed on screen ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyBOIMortgageWarningtxt", "'" + Expcontenttxt + "' warning text is not displayed on screen ", Status_CRAFT.FAIL);
        }
    }


    public void verifyPAD2Contenttxt() throws Exception {
        if (isElementDisplayed(getObject("AddPayee.lblCountry"), 5)) {
            report.updateTestLog("verifyPAD2Contenttxt", "Label 'Country' is displayed on Add Payee Page", Status_CRAFT.PASS);
            if (!dataTable.getData("General_Data", "PayeeCountry").equals("")) {
                click(getObject("xpath~//*[@class='ecDIB  tc-answer-part boi-full-width boi-gs-field-margin  ']/div/div[2]/button[text()='United Kingdom']"));
                driver.findElement(By.xpath("//*[@class='exp_elem_list_widget list']/li[contains(.,'" + dataTable.getData("General_Data", "PayeeCountry") + "')]")).click();
                report.updateTestLog("verifyPAD2Contenttxt", "Payee Country selected '" + dataTable.getData("General_Data", "PayeeCountry") + "'", Status_CRAFT.PASS);
                waitForPageLoaded();
                Thread.sleep(2000);
            } else {
                report.updateTestLog("verifyPAD2Contenttxt", "Payee Country not selected '" + dataTable.getData("General_Data", "PayeeCountry") + "'", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyPAD2Contenttxt", "Label 'Country' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
        }
        verifyBOIMortgageWarningtxt("SendMoney.internationalPAD2");
    }


    public void SendMoneySEPA() throws Exception {
        String strAccountNumber = dataTable.getData("General_Data", "Account_Name");
        String strPayee = dataTable.getData("General_Data", "Nickname");
        String strPayeeIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strPayeeBIC = dataTable.getData("General_Data", "BIC_Number");
        String strAmountToSend = dataTable.getData("General_Data", "Current_Balance");
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");
        String strBackNavigation = dataTable.getData("General_Data", "Operation");

        //warning text
        if (getText(getObject("SendMoney.sepaWarngTxt")).equals("You’re sending money outside the UK (SEPA). If you wish to make a non-SEPA payment, add the payee with a different currency.")) {
            report.updateTestLog("SendMoneySEPA", "Warning Text 'You’re sending money outside the UK (SEPA). If you wish to make a non-SEPA payment, add the payee with a different currency.' ia appearing on the screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneySEPA", "Warning Text 'You’re sending money outside the UK (SEPA). If you wish to make a non-SEPA payment, add the payee with a different currency.' ia NOT appearing on the screen", Status_CRAFT.FAIL);
        }

        // Enter and verify the amount field
        if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
            clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send.");
            sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmountToSend);
        } else {
            report.updateTestLog("SendMoneySEPA", "Amount field or amount filed place holder is not displayed correctly.", Status_CRAFT.FAIL);
        }

        //View Exchange Rate link
        if (isElementDisplayed(getObject("SendMoney.lnkViewExchangeRate"), 5)) {
            report.updateTestLog("SendMoneySEPA", "View exchange rate link is appearing on the screen.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneySEPA", "View exchange rate link is NOT appearing on the screen.", Status_CRAFT.FAIL);
        }

        //Reference
        if (isElementDisplayed(getObject("AddBillPayee.lblReference"), 5)) {
            report.updateTestLog("SendMoneySEPA", "Reference Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.lblVisibletoPayee"), 5)) {
                report.updateTestLog("SendMoneySEPA", "Visible to Payee Label is displayed", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("SendMoney.txtReference"), 5)) {
                    report.updateTestLog("SendMoneySEPA", "Reference Text box is displayed", Status_CRAFT.PASS);
                    String strValue = driver.findElement(getObject("SendMoney.txtReference")).getAttribute("value");
                    if (strValue.equals(dataTable.getData("General_Data", "ReferenceNumber"))) {
                        report.updateTestLog("SendMoneySEPA", "Reference Text '" + dataTable.getData("General_Data", "ReferenceNumber") + "' is displayed", Status_CRAFT.PASS);
                        if (!dataTable.getData("General_Data", "objName").equalsIgnoreCase("")) {
                            scrollToView(getObject("SendMoney.txtReference"));
                            driver.findElement(By.xpath("//input[contains(@name,'REFERENCE')]")).clear();
                            sendKeys(getObject("SendMoney.txtReference"), dataTable.getData("General_Data", "objName"));
                        }
                    } else {
                        report.updateTestLog("SendMoneySEPA", "Reference Text '" + dataTable.getData("General_Data", "ReferenceNumber") + "' Actual Test '" + getText(getObject("SendMoney.txtReference")) + " ' is not displayed", Status_CRAFT.FAIL);
                    }
                } else {
                    report.updateTestLog("SendMoneySEPA", "Reference Text box is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("SendMoneySEPA", "Visible to Payee label is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("SendMoneySEPA", "Reference label is not displayed", Status_CRAFT.FAIL);
        }

        //Description
        if (isElementDisplayed(getObject("SendMoney.lblDescription"), 5)) {
            report.updateTestLog("SendMoneySEPA", "Description Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.txtDescription"), 5)) {
                report.updateTestLog("SendMoneySEPA", "Description Prefilled Text box is displayed", Status_CRAFT.PASS);
                //String strValue= driver.findElement(getObject("SendMoney.txtDescription")).getAttribute("value");
                if (getText(getObject("SendMoney.txtDescription")).equals(dataTable.getData("General_Data", "BillerDescription"))) {
                    //if (strValue.equals(dataTable.getData("General_Data", "BillerDescription"))) {
                    scrollToView(getObject("SendMoney.txtDescription"));
                    report.updateTestLog("SendMoneySEPA", "Description Text '" + dataTable.getData("General_Data", "BillerDescription") + "' is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("SendMoneySEPA", "Description Text '" + dataTable.getData("General_Data", "BillerDescription") + "' is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("SendMoneySEPA", "Description Prefilled Text box is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("SendMoneySEPA", "Description Label is not displayed", Status_CRAFT.FAIL);
        }

        //Message to Payee
        if (isElementDisplayed(getObject("SendMoney.lblMsgToPayee"), 5)) {
            report.updateTestLog("SendMoneySEPA", "Message to Payee Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.lblOptionalMsgToPayee"), 5)) {
                report.updateTestLog("SendMoneySEPA", "Message to Payee (optional) Label is displayed", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("SendMoney.txtMsgToPayee"), 5)) {
                    scrollToView(getObject("SendMoney.txtMsgToPayee"));
                    sendKeys(getObject("SendMoney.txtMsgToPayee"), strMsgToPayee);
                    report.updateTestLog("SendMoneySEPA", "Message to Payee text box is displayed with enterd text", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("SendMoneySEPA", "Message to Payee text box is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("SendMoneySEPA", "Message to Payee (optional) Label is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("SendMoneySEPA", "Message to Payee Label is not displayed", Status_CRAFT.FAIL);
        }

        //FAQ message
        if (getText(getObject("SendMoney.txtFAQmsg")).equals("Check out our FAQs for further details on sending money and our schedule of charges for any associated charges.")) {
            report.updateTestLog("SendMoneySEPA", "FAQ Message text 'Check out our FAQs for further details on sending money and our schedule of charges for any associated charges.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneySEPA", "FAQ Message Text 'Check out our FAQs for further details on sending money and our schedule of charges for any associated charges.' ia NOT appearing on the screen", Status_CRAFT.FAIL);
        }


        //Go Back button

        if (isElementDisplayed(getObject("AddBillPayee.btnGoBack"), 5)) {
            report.updateTestLog("SendMoneySEPA", "Go Back Button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneySEPA", "Go Back Button is not displayed", Status_CRAFT.FAIL);
        }

        //Continue Button

        ScrollToVisibleJS(("SendMoney.btnContinue"));
        report.updateTestLog("SendMoneySEPA", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        if (isElementDisplayed(getObject("SendMoney.btnContinue"), 10)) {
            Thread.sleep(1000);
            clickJS(getObject("SendMoney.btnContinue"), "Click on Continue Button");
            Thread.sleep(4000);
        }

        //To check the back navigation flow
        if (strBackNavigation.equals("BackNavigationCheck")) {
            verifyBackNavigationFlow("BackToSendMoneyPage");
            verifySendMoneyReviewPageROI();
            verifyBackNavigationFlow("BackToReviewPage");
            enterRequiredPin();
            PayeeAddConfirmation("SendMoney.parentConfirmation");
            clickJS(getObject("SendMoney.btnBackToPayment"), "Click on Back To Payment Page.");

        } else if (strBackNavigation.equals("BackNavigationCheck_MainPayments")) {
            clickJS(getObject("SendMoney.btnGoBack"), "Click on Go Back Button from Review Page");
            Thread.sleep(4000);
            clickJS(getObject("SendMoney.btnGoBack"), "Click on Go Back Button from Send Money Page");
            Thread.sleep(4000);
        } else {
            waitForPageLoaded();
            verifySendMoneyReviewPageROI();
            enterRequiredPin();
            PayeeAddConfirmation("SendMoney.parentConfirmation");
            clickJS(getObject("SendMoney.btnBackToPayment"), "Click on Back To Payment Page.");
        }

        if (isElementDisplayed(getObject("SendMoney.iconSendMoney"), 20)) {
            report.updateTestLog("SendMoneySEPA", "Send Money icon pay correctly displayed..once Payment is successful and we clicked on Back to Payments Page...!!", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneySEPA", "Send Money Header correctly displayed on review page.", Status_CRAFT.FAIL);
        }
    }


    // Verify Errors for Amount in SEPA Payment
    public void SendMoneySEPAAmountErr() throws Exception {
        String strAmountToSend = dataTable.getData("General_Data", "Current_Balance");
        if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
            report.updateTestLog("SendMoneySEPAAmountErr", "Amount field or amount filed place holder is displayed correctly.", Status_CRAFT.PASS);
            clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send.");
            sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmountToSend);
            report.updateTestLog("SendMoneySEPAAmountErr", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
            errorMessageValidation();
        } else {
            report.updateTestLog("SendMoneySEPAAmountErr", "Amount field or amount filed place holder is not displayed correctly.", Status_CRAFT.FAIL);
        }
    }

    // Verify Errors for Reference in SEPA Payment
    public void SendMoneySEPAReferenceErr() throws Exception {
        String strReference = dataTable.getData("General_Data", "ReferenceNumber");
        if (isElementDisplayed(getObject("AddBillPayee.lblReference"), 5)) {
            report.updateTestLog("SendMoneySEPAReferenceErr", "Reference Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.lblVisibletoPayee"), 5)) {
                report.updateTestLog("SendMoneySEPAReferenceErr", "Visible to Payee Label is displayed", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("SendMoney.txtReference"), 5)) {
                    report.updateTestLog("SendMoneySEPAReferenceErr", "Reference Text box is displayed", Status_CRAFT.PASS);
                    clickJS(getObject("SendMoney.txtReference"), "Enter the text to update the Reference.");
                    sendKeysJS(getObject("SendMoney.txtReference"), strReference);
                    report.updateTestLog("SendMoneySEPAReferenceErr", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                    errorMessageValidation();
                } else {
                    report.updateTestLog("SendMoneySEPA", "Reference Text box is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("SendMoneySEPA", "Visible to Payee label is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("SendMoneySEPA", "Reference label is not displayed", Status_CRAFT.FAIL);
        }

    }

    // Verify Errors for Message to Payees in SEPA Payment
    public void SendMoneySEPAMsgtoPayeeErr() throws Exception {
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");
        if (isElementDisplayed(getObject("SendMoney.lblMsgToPayee"), 5)) {
            report.updateTestLog("SendMoneySEPA", "Message to Payee Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.lblOptionalMsgToPayee"), 5)) {
                report.updateTestLog("SendMoneySEPA", "Message to Payee (optional) Label is displayed", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("SendMoney.txtMsgToPayee"), 5)) {
                    scrollToView(getObject("SendMoney.txtMsgToPayee"));
                    sendKeys(getObject("SendMoney.txtMsgToPayee"), strMsgToPayee);
                    report.updateTestLog("SendMoneySEPA", "Message to Payee text box is displayed with enterd text", Status_CRAFT.PASS);
                    errorMessageValidation();
                } else {
                    report.updateTestLog("SendMoneySEPA", "Message to Payee text box is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("SendMoneySEPA", "Message to Payee (optional) Label is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("SendMoneySEPA", "Message to Payee Label is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void verifyNavigationToAllPaymentsOprions() throws Exception {

        String[] arrPageHead = dataTable.getData("General_Data", "Current_Balance").split(";");
        for (int intValidate = 0; intValidate < arrPageHead.length; intValidate++) {
            String strPageHead = arrPageHead[intValidate].split("::")[0];
            String strValidateData = arrPageHead[intValidate].split("::")[1];
            String myobj;
            if (deviceType == "Web") {
                myobj = "xpath ~//h1[text()='" + strPageHead + "'][not(contains(@class,'mobile'))]";
            } else {
                if(strPageHead.equals("Mobile Topup")){
                    myobj = "xpath~//h1[text()='Mobile topup'][contains(@class,'mobile')]";
                }else{
                    myobj = "xpath~//h1[text()='" + strPageHead + "'][contains(@class,'mobile')]";
                }
            }
            String strPaymentOption = "xpath~//*[text()='" + strValidateData + "']";
            if (isElementDisplayed(getObject(strPaymentOption), 25)) {
                click(getObject(strPaymentOption));
                report.updateTestLog("verifyNavigationToAllPaymentsOprions", strPageHead + " :: Option Displayed Successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyNavigationToAllPaymentsOprions", strPageHead + " :: Option Not displayed Successfully", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject(myobj), 25)) {
                report.updateTestLog("verifyNavigationToAllPaymentsOprions", strPageHead + " :: Page Displayed Successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyNavigationToAllPaymentsOprions", strPageHead + " :: Page  not displayed Successfully", Status_CRAFT.FAIL);
            }
          click(getObject(deviceType() + "ChangeAdd.backButton"));

        }
    }

     /*
    Function: To verify FAQ text on Send Money Page
     */

    public void verifyFAQtxt() throws Exception {
        if (getText(getObject("SendMoney.txtFAQmsg")).equals("Check out our FAQs for further details on sending money and our schedule of charges for any associated charges.")) {
            report.updateTestLog("SendMoneySEPA", "FAQ Message text 'Check out our FAQs for further details on sending money and our schedule of charges for any associated charges.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneySEPA", "FAQ Message Text 'Check out our FAQs for further details on sending money and our schedule of charges for any associated charges.' ia NOT appearing on the screen", Status_CRAFT.FAIL);
        }

    }

    /*
    Function: To verify the presence of PAY NOW button on credit card transactions page(CFPUR-5113)
     */

    public void verifyPayNowbutton() throws Exception {
        if (isElementDisplayed(getObject(deviceType()+"Transactions.PayNow"), 5)) {
            report.updateTestLog("verifyPayNowbutton", "Pay Now button is displayed", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyPayNowbutton", "Pay Now button is not displayed", Status_CRAFT.PASS);
        }
    }

    /*
    Function: To verify the details of PAY NOW pop up (CFPUR-66)
     */
    public void verifyPayNowPopUpDetails(String paynowBtn) throws Exception {
        if (isElementDisplayed(getObject(paynowBtn), 5)) {
            clickJS(getObject(paynowBtn), "Pay Now Button");
            verifyDeletePayeePopUpDetails();
        } else {
            report.updateTestLog("verifyPayNowPopUpDetails", "Pay Now button is not displayed", Status_CRAFT.FAIL);
        }
    }

    /*
    Function : To verify the Balance and Another amount button on Pay now pop up for credit card (CFPUR-1194)
     */
    public void verifyPayNowPopUpbtnNavigation() throws Exception {
        //verifyPayNowPopUpDetails("Transactions.PayNow");
        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "BALANCE":
                    // Click on Balance button
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Transactions.PaynowBalanceBtn")));
                    clickJS(getObject("Transactions.PaynowBalanceBtn"), "'Balance Button' on Pay now Pop Up");
                    while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                        waitForPageLoaded();
                    }
                    selectPayfromSendMoney();
                    waitForElementPresent(getObject("SendMoney.txtbxAmountField"), 15);
                    verifyAmount(deviceType() + "SendMoney.PaynowBalanceAmt");
                    break;
                case "ANOTHER AMOUNT":
                    //Click on Another Amount button
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Transactions.PaynowAnotherAmntBtn")));
                    clickJS(getObject("Transactions.PaynowAnotherAmntBtn"), "'Another Amount Button' on Pay Now Pop up");
                    while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                        waitForPageLoaded();
                    }
                    selectPayfromSendMoney();
                    waitForElementPresent(getObject("SendMoney.txtbxAmountField"), 25);
                    verifyAmount("SendMoney.PaynowAnotherAmt");
                    break;
                case "CLOSE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("Payments.DeletePayeePopUpCloseIcon")));
                    Thread.sleep(2000);
                    clickJS(getObject("Payments.DeletePayeePopUpCloseIcon"), "'Close Icon' on Pay Now Pop up");
                    if (isElementDisplayed(getObject(deviceType() + "Transactions.PayNow"), 5)) {
                        report.updateTestLog("verifyPayNowPopUpbtnNavigation", "User redirected to Transactions Page upon clicking 'Close Icon'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyPayNowPopUpbtnNavigation", "User does not redirects to Transactions Page upon clicking 'Close Icon'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("verifyPayNowPopUpbtnNavigation", "Please provide the valid operation on PAY NOW pop up: BALANCE, ANOTHER AMOUNT or CLOSE", Status_CRAFT.FAIL);
            }
        }
    }

    /*
    Function: To verify the amount from the Amount text box (CFPUR-1194)
     */
    public void verifyAmount(String Amntbtn) throws Exception {
        String strAmountToverify = dataTable.getData("General_Data", "Available_Balance");
        String strAmountToSend = dataTable.getData("General_Data", "Current_Balance");
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(Amntbtn)));
        if (isElementDisplayed(getObject(Amntbtn), 15)) {
            report.updateTestLog("verifyAmount", "Amount field is displayed correctly.", Status_CRAFT.PASS);
            scrollToView(getObject(Amntbtn));
            String strValue = driver.findElement(getObject(Amntbtn)).getAttribute("placeholder");
            if (strValue == "") {
                if (strValue.equals(strAmountToverify)) {
                    report.updateTestLog("verifyAmount", "Amount in Amount text box is expected as '" + strAmountToverify + "', Actual: '" + strValue + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyAmount", "Amount in Amount text box is not as per expectation as '" + strAmountToverify + "' , Actual: '" + strValue + "' or Amount text box is not displayed properly", Status_CRAFT.FAIL);
                }
            }else{
                if (strValue.equals(strAmountToverify)) {
                    report.updateTestLog("verifyAmount", "Amount in Amount text box is expected as '" + strAmountToverify + "', Actual: '" + strValue + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyAmount", "Amount in Amount text box is not as per expectation as '" + strAmountToverify + "' , Actual: '" + strValue + "' or Amount text box is not displayed properly", Status_CRAFT.FAIL);
                }
            }
            clickJS(getObject(Amntbtn), "Enter The amount to send.");
            sendKeysJS(getObject(Amntbtn), strAmountToSend);
            report.updateTestLog("verifyAmount", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);

        } else {
            report.updateTestLog("verifyAmount", "Amount field is not displayed correctly.", Status_CRAFT.FAIL);
        }
    }

    /*
    Function: To verify the International Payments Confirmation page (CFPUR-3605)
    Created by: C103403, Date: 03/05/2019
     */
    public void checkInternationalPaymentsConfirmationPage() throws Exception {

        waitForElementPresent(getObject(deviceType() + "SendMoney.hdrtxt"), 5);
        if (isElementDisplayed(getObject(deviceType() + "SendMoney.hdrtxt"), 5)) {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Section Header 'Send Money' is displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Section Header 'Send Money' is displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.imgSuccess"), 5)) {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Success Image is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Success Image is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Services.verifyElementDetailsConfim("SendMoney.confrmPage");

        click(getObject("SendMoney.lnkMoreInformation"), "'More information on processing times for specific countries.' link");
        validateURLOpens("https://www.bankofireland.com/help-centre/faq/can-transfer-funds-365-online/");

        if (isElementDisplayed(getObject("SendMoney.btnBackToPayment"), 30)) {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Link Button 'Back to payments' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Link Button 'Back to payments' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("xpath~//div[@class='boi-position-relative  ']/div/div[contains(text(),'Time of request')]"), 5)) {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Time Stamp with message 'Time of request' is displayed on Confirmation page", Status_CRAFT.PASS);

            String strRequestSubmitted = getText(getObject("xpath~//div[@class='boi-position-relative  ']/div/div[contains(text(),'Time of request')]"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("checkAddBillConfirmationPage", "Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("checkAddBillConfirmationPage", "Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Time Stamp with message 'Time of request' is NOT displayed on Cancelled Standing Order Confirmation page", Status_CRAFT.FAIL);
        }

        //Validating Navigation/functionality of the buttons
        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to payments")) {
            click(getObject("SendMoney.btnBackToPayment"), "Clicked on 'Back to payments'");
            if (isElementDisplayed(getObject(deviceType() + "home.tabPaymentshdr"), 5)) {
                report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Upon clicking 'Back to payments' ,successfully Navigated to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Upon clicking 'Back to payments' ,navigation unsuccessful to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.FAIL);
            }
        }
    }

    /*
    Function: To Verify the first page of Send Money for UK Domestic payment (CFPUR-83)
     */
    public void sendMoneyDetailsUKDomestic() throws Exception {
        //Information text for Cross Customer
        if ((dataTable.getData("General_Data", "JurisdictionType").equals("Cross"))) {
            String UiAlertText = getText(getObject("SendMoney.infotxtUKDomesticCross"));
            String expectedAlerttxt = "The payees and currencies available to you for payments can differ between your EUR and GBP accounts.";
            if (UiAlertText.equals(expectedAlerttxt)) {
                report.updateTestLog("sendMoneyDetailsUKDomestic", "Information text for UK Domestic Payment for Cross is  displayed is as Expected '" + expectedAlerttxt + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetailsUKDomestic", "Information text for UK Domestic Payment for Cross is  displayed is NOT as Expected '" + expectedAlerttxt + "', Actual ''" + UiAlertText + "", Status_CRAFT.FAIL);
            }
        }

        // Verify ALert Text
        if ((dataTable.getData("General_Data", "JurisdictionType").equals("NI")) | (dataTable.getData("General_Data", "JurisdictionType").equals("GB"))) {
            String UiAlertText = getText(getObject("SendMoney.alrttxtUKDomestic"));
            String expectedAlerttxt = "You're sending money within the UK.";
            if (UiAlertText.equals(expectedAlerttxt)) {
                report.updateTestLog("sendMoneyDetailsUKDomestic", "Alert text for UK Domestic Payment for NI/GB is  displayed is as Expected '" + expectedAlerttxt + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetailsUKDomestic", "Alert text for UK Domestic Payment for NI/GB is  displayed is NOT as Expected '" + expectedAlerttxt + "', Actual ''" + UiAlertText + "", Status_CRAFT.FAIL);
            }
        }

        //Amount
        if (isElementDisplayed(getObject("SendMoney.lblAccount"), 5)) {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Label 'Amount' is displayed on Send Money enter details Page", Status_CRAFT.PASS);
            String uiCurrency = getText(getObject("SendMoney.lblCurrSymbol"));
            String expectedCurrency = dataTable.getData("General_Data", "Currency_Symbol");
            if (uiCurrency.equals(expectedCurrency)) {
                report.updateTestLog("sendMoneyDetailsUKDomestic", "Currency in Amount textbox is correctly displayed on Send Money enter details Page, Expected '" + expectedCurrency + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetailsUKDomestic", "Currency in Amount textbox is not correctly displayed on Send Money enter details Page, Expected : '" + expectedCurrency + "', Actual : '" + uiCurrency + "'", Status_CRAFT.FAIL);
            }

            //entering amount
            if (!dataTable.getData("General_Data", "Pay_Amount").equals("")) {
                sendKeys(getObject("SendMoney.txtUKDomCurrAmount"), dataTable.getData("General_Data", "Pay_Amount"));
                waitForPageLoaded();
            }
        } else {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Label 'Amount' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //Reference
        if (isElementDisplayed(getObject("AddBillPayee.lblReference"), 5)) {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Reference Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.lblUKDomVisibletoPayee"), 5)) {
                report.updateTestLog("sendMoneyDetailsUKDomestic", "Visible to Payee Label is displayed", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("SendMoney.txtUKDomReference"), 5)) {
                    report.updateTestLog("sendMoneyDetailsUKDomestic", "Reference Text box is displayed", Status_CRAFT.PASS);
                    if (getText(getObject("SendMoney.txtUKDomReference")).equals(dataTable.getData("General_Data", "ReferenceNumber"))) {
                        scrollToView(getObject("SendMoney.txtUKDomReference"));
                        report.updateTestLog("sendMoneyDetailsUKDomestic", "Reference Text '" + dataTable.getData("General_Data", "ReferenceNumber") + "' is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("sendMoneyDetailsUKDomestic", "Reference Text '" + dataTable.getData("General_Data", "ReferenceNumber") + "' Actual Test '" + getText(getObject("SendMoney.txtReference")) + " ' is not displayed", Status_CRAFT.FAIL);
                    }
                } else {
                    report.updateTestLog("sendMoneyDetailsUKDomestic", "Reference Text box is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("sendMoneyDetailsUKDomestic", "Visible to Payee label is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Reference label is not displayed", Status_CRAFT.FAIL);
        }

        //Description
        if (isElementDisplayed(getObject("SendMoney.lblDescription"), 5)) {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Description Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.txtUKDomDescription"), 5)) {
                report.updateTestLog("sendMoneyDetailsUKDomestic", "Description Prefilled Text box is displayed", Status_CRAFT.PASS);
                if (getText(getObject("SendMoney.txtUKDomDescription")).equals(dataTable.getData("General_Data", "BillerDescription"))) {
                    scrollToView(getObject("SendMoney.txtUKDomDescription"));
                    report.updateTestLog("sendMoneyDetailsUKDomestic", "Description Text '" + dataTable.getData("General_Data", "BillerDescription") + "' is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("sendMoneyDetailsUKDomestic", "Description Text '" + dataTable.getData("General_Data", "BillerDescription") + "' is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("sendMoneyDetailsUKDomestic", "Description Prefilled Text box is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Description Label is not displayed", Status_CRAFT.FAIL);
        }

        //Process Payment
        if (isElementDisplayed(getObject("SendMoney.lblUKDomProcessPayment"), 5)) {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Process Payment Label is displayed", Status_CRAFT.PASS);
            TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
            if (dataTable.getData("General_Data", "ErrorText").equals("")) {
                transBetAccnt.selectOptProcessTransfer();
            } else {
                transBetAccnt.errorMsgsScheduleFutureDate();
            }

        } else {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Process Payment Label is not displayed", Status_CRAFT.FAIL);
        }

        //continue & Goback
        if (isElementDisplayed(getObject("AddPayee.GoBack"), 5)) {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Button 'Go back' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Button 'Go back' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.Continue"), 5)) {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Button 'Continue' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetailsUKDomestic", "Button 'Continue' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.Continue")));
                    Thread.sleep(2000);
                    clickJS(getObject("AddPayee.Continue"), "'Continue', on Page");
                    break;
                case "GO BACK":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.GoBack")));
                    Thread.sleep(2000);
                    click(getObject("AddPayee.GoBack"), "'Go back', on Page");
                    if (isElementDisplayed(getObject("xpath~//h1[@title='Payments']"), 5)) {
                        report.updateTestLog("sendMoneyDetailsUKDomestic", "Page navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("sendMoneyDetailsUKDomestic", "Page does not navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("sendMoneyDetailsUKDomestic", "Please provide the valid operation on Send Money Page, Continue or Go Back", Status_CRAFT.FAIL);
            }
        }
    }

    /*
    Function: To validate UK Domestic Review Page (CFPUR-83)
     */
    public void verifyUKDomesticPaymentsReviewPage() throws Exception {
        //validating page title
        validatePageHeader();
        //Section header-Review
        if (isElementDisplayed(getObject("xpath~//h3[text()='Review']"), 5)) {
            report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "Section header 'Review' is displayed on Send Money Review page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "Section header 'Review' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }

        //Amount in
        String strAmountIn = dataTable.getData("General_Data", "Currency_Symbol") + dataTable.getData("General_Data", "Current_Balance");
        if (isElementDisplayed(getObject("xpath~//*[text()='Amount']"), 5)) {
            report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "Label 'Amount' is displayed on Send Money Review page", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("xpath~//*[text()='Amount']/ancestor::div[contains(@class,'ecDIB')]/following-sibling::*/div"), 5)) {
                String actAmount = getText(getObject("xpath~//*[text()='Amount']/ancestor::div[contains(@class,'ecDIB')]/following-sibling::*/div"));
                if (actAmount.equals(strAmountIn)) {
                    report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "Amount '" + strAmountIn + "' is appearing on Review Page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "Amount '" + strAmountIn + "' is not appearing on Review Page", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "Label '" + strAmountIn + "' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }

        //validate review section details
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {
            if (!obj.getValue().equals("NA")) {
                boolean bflag = driver.findElement(By.xpath("//*[text()='" + obj.getKey() + "']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[text()='" + obj.getValue() + "']")).isDisplayed();
                if (bflag) {
                    report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
                }
            } else {
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
                    report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + obj.getKey() + "' is displayed on review page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + obj.getKey() + "' is Not displayed on review page", Status_CRAFT.FAIL);
                }
            }
        }


        String strProssTran = dataTable.getData("General_Data", "Process_Transfer");
        if (strProssTran.equalsIgnoreCase("Schedule future date")) {
            String ActDate = getText(getObject("xpath~//span[text()='Process payment']/ancestor::div[contains(@class,'question-part')]/following-sibling::div")).replace("\n"," ");;
            String ExpDate = (scriptHelper.commonData.strTransferDate).replace("/", " ");
            if (ActDate.equals(ExpDate)) {
                report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + ExpDate + "' correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + ExpDate + "' is not correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'", Status_CRAFT.FAIL);
            }
            validateProcessingAndDailylimits();
        } else {
            validateDailylimitsNCutOffTimes();
        }
        SendMoneyReviewPageOperation();
    }

    public void verifyPayNowPopUp(String paynowBtn) throws Exception {
        if (isElementDisplayed(getObject(paynowBtn), 5)) {
            clickJS(getObject(paynowBtn), "Pay Now Button");
        } else {
            report.updateTestLog("verifyPayNowPopUpDetails", "Pay Now button is not displayed", Status_CRAFT.FAIL);
        }
    }

    /*
    Function: To verify the UK Domestic Confirmation Page
     */
    public void checkUKDomesticPaymentsConfirmationPage() throws Exception {

        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
        waitForElementPresent(getObject(deviceType() + "SendMoney.hdrtxt"), 5);
        if (isElementDisplayed(getObject(deviceType() + "SendMoney.hdrtxt"), 5)) {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Section Header 'Send Money' is displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Section Header 'Send Money' is displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.imgSuccess"), 5)) {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Success Image is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Success Image is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }
        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Services.verifyElementDetailsConfim("SendMoney.confrmPage");
        String strProssTran = dataTable.getData("General_Data", "Process_Transfer");
        if (strProssTran.equalsIgnoreCase("Schedule future date")) {
            String ActDate = getText(getObject("xpath~//*[@class='accessibilityStyle'][text()='Day']/..")).replace("\n"," ");
            String ExpDate = (scriptHelper.commonData.strTransferDate).replaceAll("/", " ");
            if (ActDate.replaceAll("  "," ").contains(ExpDate)) {
                report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + ExpDate + "' correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + ExpDate + "' is not correctly displayed on review page,Expected::'" + ExpDate + "', Actual::'" + ActDate + "'", Status_CRAFT.FAIL);
            }
        }

        if (isElementDisplayed(getObject("SendMoney.btnBackToPayment"), 5)) {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Link Button 'Back to payments' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Link Button 'Back to payments' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("xpath~//div[@class='boi-position-relative  ']/div/div[contains(text(),'Time of request')]"), 5)) {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Time Stamp with message 'Time of request' is displayed on Confirmation page", Status_CRAFT.PASS);

            String strRequestSubmitted = getText(getObject("xpath~//div[@class='boi-position-relative  ']/div/div[contains(text(),'Time of request')]"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("checkAddBillConfirmationPage", "Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("checkAddBillConfirmationPage", "Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Time Stamp with message 'Time of request' is NOT displayed on Cancelled Standing Order Confirmation page", Status_CRAFT.FAIL);
        }

        //Validating Navigation/functionality of the buttons
        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to payments")) {
            clickJS(getObject("SendMoney.btnBackToPayment"), "'Back to payments'");
            if (isElementDisplayed(getObject(deviceType() + "home.tabPaymentshdr"), 5)) {
                report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Upon clicking 'Back to payments' ,successfully Navigated to Payments screen from Add Payee Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("checkInternationalPaymentsConfirmationPage", "Upon clicking 'Back to payments' ,navigation unsuccessful to Payments screen from Add Payee Confirmation Page", Status_CRAFT.FAIL);
            }
        }
    }

    /*
    Function: To Verify Errors for Amount in UK Domestic Payment
     */

    public void SendMoneyUKDomesticAmountErr() throws Exception {
        String strAmountToSend = dataTable.getData("General_Data", "Current_Balance");
        if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
            report.updateTestLog("SendMoneyUKDomesticAmountErr", "Amount field or amount filed place holder is displayed correctly.", Status_CRAFT.PASS);
            clickJS(getObject("SendMoney.txtUKDomCurrAmount"), "Enter The amount to send.");
            sendKeysJS(getObject("SendMoney.txtUKDomCurrAmount"), strAmountToSend);
            report.updateTestLog("SendMoneyUKDomesticAmountErr", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
            clickJS(getObject("SendMoney.btnContinue"), "Continue");
            errorMessageValidation();
        } else {
            report.updateTestLog("SendMoneyUKDomesticAmountErr", "Amount field or amount filed place holder is not displayed correctly.", Status_CRAFT.FAIL);
        }
    }


    //CFPUR-112

    public void verifyAddBillPayee() throws Exception {
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");

        //Validate Cancel Button

        if (isElementDisplayed(getObject("SendMoney.btnCancel"), 5)) {
            report.updateTestLog("verifyAddBillPayee", "Cancel Button' is present on Add bill payee", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddBillPayee", "Cancel Button' NOT present on Add bill payee", Status_CRAFT.FAIL);
        }

        //Validate Content

        if (isElementDisplayed(getObject("SendMoney.txtContent"), 5)) {
            String strContent = getText(getObject("SendMoney.txtContent"));
            if (strContent.equalsIgnoreCase(strMsgToPayee)) {
                report.updateTestLog("verifyAddBillPayee", "Expected:'" + strMsgToPayee + "Actual:" + strContent + "is Present on Add Bill payee Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddBillPayee", "Expected:'" + strMsgToPayee + "Actual:" + strContent + "NOT Present on Add Bill payee Page", Status_CRAFT.FAIL);
            }
        }

        if (isElementDisplayed(getObject("SendMoney.btnAddBillPayee"), 5)) {
            click(getObject("SendMoney.btnAddBillPayee"), "Clicked on 'Add Bill Payee'");
            if (isElementDisplayed(getObject(deviceType() + "SendMoney.lblAddbill"), 5)) {
                report.updateTestLog("verifyAddBillPayee", "Upon clicking 'Add Bill Payee' ,successfully Navigated to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddBillPayee", "Upon clicking 'Add Bill Payee' ,navigation unsuccessful to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.FAIL);
            }
        }
    }


    /*
    Function : To verify the Send money details page for Bill Payments (CFPUR-78)
     */
    public void sendMoneyDetailsBill() throws Exception {
        //Information text for Cross Customer
        if ((dataTable.getData("General_Data", "JurisdictionType").equals("Cross"))) {
            String UiAlertText = getText(getObject("SendMoney.infotxtUKDomesticCross"));
            String expectedAlerttxt = "The payees and currencies available to you for payments can differ between your EUR and GBP accounts.";
            if (UiAlertText.equals(expectedAlerttxt)) {
                report.updateTestLog("sendMoneyDetailsBill", "Information text for UK Domestic Payment for Cross is  displayed is as Expected '" + expectedAlerttxt + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetailsBill", "Information text for UK Domestic Payment for Cross is  displayed is NOT as Expected '" + expectedAlerttxt + "', Actual ''" + UiAlertText + "", Status_CRAFT.FAIL);
            }
        }

        //Warning Text for BOI Mortgage Bill type
        if(dataTable.getData("General_Data", "Nickname").equalsIgnoreCase("BOI MORTGAGE")){
            String UiAlertText = getText(getObject("SendMoney.warningtxtBoiMortgage"));
            String expectedAlerttxt = "Warning: If we suffer a loss because you pay off some or all of your fixed rate mortgage loan before the due date, you will need to pay compensation to us for this loss. For more info on lump sum payments call 01 611 3333 or view Mortgage Fees and Costs";
            if (UiAlertText.equals(expectedAlerttxt)) {
                report.updateTestLog("sendMoneyDetailsBill", "Information text for BOI MORTGAGE Payment displayed is as Expected '" + expectedAlerttxt + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetailsBill", "Information text for BOI MORTGAGE Payment displayed is not as Expected '" + expectedAlerttxt + "', Actual ''" + UiAlertText + "", Status_CRAFT.FAIL);
            }
        }

        //Amount
        if (isElementDisplayed(getObject("SendMoney.lblAccount"), 5)) {
            report.updateTestLog("sendMoneyDetailsBill", "Label 'Amount' is displayed on Send Money enter details Page", Status_CRAFT.PASS);
            String uiCurrency = getText(getObject("SendMoney.lblCurrSymbol"));
            String expectedCurrency = dataTable.getData("General_Data", "Currency_Symbol");
            if (uiCurrency.equals(expectedCurrency)) {
                report.updateTestLog("sendMoneyDetailsBill", "Currency in Amount textbox is correctly displayed on Send Money enter details Page, Expected '" + expectedCurrency + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetailsBill", "Currency in Amount textbox is not correctly displayed on Send Money enter details Page, Expected '" + expectedCurrency + "'", Status_CRAFT.FAIL);
            }

            //entering amount
            if (!dataTable.getData("General_Data", "Pay_Amount").equals("")) {
                sendKeys(getObject("SendMoney.txtUKDomCurrAmount"), dataTable.getData("General_Data", "Pay_Amount"));
                waitForPageLoaded();
            }
        } else {
            report.updateTestLog("sendMoneyDetailsBill", "Label 'Amount' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //Reference
        if (isElementDisplayed(getObject("AddBillPayee.lblReference"), 5)) {
            report.updateTestLog("sendMoneyDetailsBill", "Reference Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.txtBillsReference"), 5)) {
                report.updateTestLog("sendMoneyDetailsBill", "Reference Text box is displayed", Status_CRAFT.PASS);
                if (getText(getObject("SendMoney.txtBillsReference")).equals(dataTable.getData("General_Data", "ReferenceNumber"))) {
                    scrollToView(getObject("SendMoney.txtBillsReference"));
                    report.updateTestLog("sendMoneyDetailsBill", "Reference Text '" + dataTable.getData("General_Data", "ReferenceNumber") + "' is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("sendMoneyDetailsBill", "Reference Text '" + dataTable.getData("General_Data", "ReferenceNumber") + "' Actual Test '" + getText(getObject("SendMoney.txtReference")) + " ' is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("sendMoneyDetailsBill", "Reference Text box is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("sendMoneyDetailsBill", "Reference label is not displayed", Status_CRAFT.FAIL);
        }

        //Description
        if (isElementDisplayed(getObject("SendMoney.lblDescription"), 5)) {
            report.updateTestLog("sendMoneyDetailsBill", "Description Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.txtUKDomDescription"), 5)) {
                report.updateTestLog("sendMoneyDetailsBill", "Description Prefilled Text box is displayed", Status_CRAFT.PASS);
                if (getText(getObject("SendMoney.txtUKDomDescription")).equals(dataTable.getData("General_Data", "BillerDescription"))) {
                    scrollToView(getObject("SendMoney.txtUKDomDescription"));
                    report.updateTestLog("sendMoneyDetailsBill", "Description Text '" + dataTable.getData("General_Data", "BillerDescription") + "' is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("sendMoneyDetailsBill", "Description Text '" + dataTable.getData("General_Data", "BillerDescription") + "' is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("sendMoneyDetailsBill", "Description Prefilled Text box is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("sendMoneyDetailsBill", "Description Label is not displayed", Status_CRAFT.FAIL);
        }

        //Process Payment
        if (isElementDisplayed(getObject("SendMoney.lblUKDomProcessPayment"), 5)) {
            report.updateTestLog("sendMoneyDetailsBill", "Process Payment Label is displayed", Status_CRAFT.PASS);
            TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
            if (dataTable.getData("General_Data", "ErrorText").equals("")) {
                transBetAccnt.selectOptProcessTransfer();
            } else {
                transBetAccnt.errorMsgsScheduleFutureDate();
            }

        } else {
            report.updateTestLog("sendMoneyDetailsBill", "Process Payment Label is not displayed", Status_CRAFT.FAIL);
        }

        //continue & Goback
        if (isElementDisplayed(getObject("AddPayee.GoBack"), 5)) {
            report.updateTestLog("sendMoneyDetails", "Button 'Go back' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetails", "Button 'Go back' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.Continue"), 5)) {
            report.updateTestLog("sendMoneyDetails", "Button 'Continue' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetails", "Button 'Continue' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

    }

    public void inputdetailsBillOperation() throws Exception {
        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.Continue")));
                    Thread.sleep(2000);
                    clickJS(getObject("AddPayee.Continue"), "'Continue'");
                    break;
                case "GO BACK":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.GoBack")));
                    Thread.sleep(2000);
                    clickJS(getObject("AddPayee.GoBack"), "'Go back'");
                    if (isElementDisplayed(getObject("xpath~//h1[@title='Payments']"), 5)) {
                        report.updateTestLog("sendMoneyDetails", "Page navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("sendMoneyDetails", "Page does not navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("sendMoneyDetails", "Please provide the valid operation on Send Money Page, Continue or Go Back", Status_CRAFT.FAIL);
            }
        }

    }
    //CFPUR-112

    public void verifyManagePayeePage() throws Exception {
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");

        //Click on Payments Tab
        clickJS(getObject(deviceType() + "home.tabPayments"), "Select 'Payments Tab'");
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }

        //Click on Manage Payees
        clickJS(getObject("Payments.ManagePayee"), "Manage Payees clicked");
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
        Thread.sleep(10000);

        //Validate Add Person and Add Bill
        String myobj = "";
        String AddBillTitle = "";
        String btnAddPayees = "";
        String AddpayeeTitle = "";

        if (isElementDisplayed(getObject(deviceType() + "Payments.btnAddBill"), 5)) {
            clickJS(getObject(deviceType() + "Payments.btnAddBill"), "Validate Add Person and Add Bill");
            Thread.sleep(5000);
            report.updateTestLog("addBillPayeeDetails", "Page Title is Displayed for Add Bill Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Manage Payees:", "Add Bill Button is not displayed on Screen", Status_CRAFT.FAIL);
        }
        clickJS(getObject(deviceType() + "Payments.btnGoback"), "Clicked on Go back Add Bill");

        if (isElementDisplayed(getObject(deviceType() + "Payments.btnAddPayees"), 5)) {
            clickJS(getObject(deviceType() + "Payments.btnAddPayees"), "Clicked on Add payee ");
            Thread.sleep(5000);
            report.updateTestLog("addBillPayeeDetails", "Page Title is Displayed for Add Payees Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Manage Payees:", "Add Payees Button is not displayed on Screen", Status_CRAFT.FAIL);
        }
        clickJS(getObject(deviceType() + "Payments.btnGoback"), "Clicked on GO BACK");

        //Validate Columns of Manage Payee Page
        if (deviceType().equals("w.")) {
            if (isElementDisplayed(getObject("ManagePayee.lblName"), 5)) {
                report.updateTestLog("Manage Payees:", "Payee name is Displayed for Manage Payees Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Manage Payees:", "Payee name NOT Displayed for Manage Payees Page", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("Accountdetails.lblName"), 5)) {
                report.updateTestLog("Manage Payees:", "Account details is Displayed for Manage Payees Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Manage Payees:", "Account details NOT Displayed for Manage Payees Page", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("ManagePayee.lblReference"), 5)) {
                report.updateTestLog("Manage Payees:", "Reference is Displayed for Manage Payees Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Manage Payees:", "Reference NOT Displayed for Manage Payees Page", Status_CRAFT.FAIL);
            }

            //Validate Reference Tool Tip

            Thread.sleep(5000);
            click(getObject("ManagePayee.Reference"));
            Thread.sleep(2000);
            String UiTooltip = getText(getObject("ManagePayee.ToolTip"));
            String strExpectedTooltip = "This is visible to the payee";
            if (UiTooltip.equals(strExpectedTooltip)) {
                report.updateTestLog("payeeDetailsPage", "Tooltip of label is correctly displayed on Payee details page, Expected: '" + strExpectedTooltip + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("payeeDetailsPage", "Tooltip of is NOT  correctly displayed on Payee details page, Expected: '" + strExpectedTooltip + "', Actual:" + UiTooltip + "'", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("ManagePayee.lblCountry"), 5)) {
                report.updateTestLog("Manage Payees:", "Country is Displayed for Manage Payees Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Manage Payees:", "Country NOT Displayed for Manage Payees Page", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("ManagePayee.lblDescription"), 5)) {
                report.updateTestLog("Manage Payees:", "Description is Displayed for Manage Payees Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Manage Payees:", "Description NOT Displayed for Manage Payees Page", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("ManagePayee.lblCurrencylimit"), 5)) {
                report.updateTestLog("Manage Payees:", "Currency & limit  is Displayed for Manage Payees Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Manage Payees:", "Currency & limit NOT Displayed for Manage Payees Page", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject(deviceType()+"Payments.hdrManagePayees"), 5) || isElementDisplayed(getObject(deviceType()+"AddPayee.hdrPageTitle"), 5) ) {
                report.updateTestLog("Manage Payees:", "My Payees is Displayed for Manage Payees Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Manage Payees:", "My Payees is NOT Displayed for Manage Payees Page", Status_CRAFT.FAIL);
            }
        }
    }

    //Validate functionality of Manage Payees.Click on Blue Radio Button and Validate Pay and Delete Button
//CFPUR-112
    public void verifyManagePayeeFunctionality() throws Exception {
        String managePayeesTbl = "";
        //Click on blue radio Button to validate Pay Enable Button
        if (deviceType().equals("w.")) {
            managePayeesTbl = "ManagePayee.tblManagePayee";
            List<WebElement> elmLabels = driver.findElements(By.xpath(managePayeesTbl));
            int size = 20;
            int intRow = 0;
            int intTr = 0;

            for (int irow = 1; irow < size; irow++) {
                String Expvalue = getText(getObject("xpath~//*[@id='C5__p4_QUE_4ACB89C10BA8A5F4647071_R" + irow + "']"));
                String Actualvalue = "Up to €";
                String ActualvaluePound = "Up to £";
                if (Expvalue.contains(Actualvalue) || Expvalue.contains(ActualvaluePound)) {
                    clickJS(getObject("xpath~//*[@id='C5__FS_QUE_3283E3CB3B21920D1399547_R" + irow + "']"), "Blue Button clicked");
                    if (isElementEnabled(getObject("ManagePayee.btnPay")) && isElementEnabled(getObject("ManagePayee.btnDelete"))) {
                        report.updateTestLog("ManagePayees", "Blue Button Clicked on Manage Payees: and Pay Button is Enable '" + getText(getObject(("xpath~//*[@id='C5__FS_QUE_3283E3CB3B21920D1399547_R" + irow + "]'"))) + "'", Status_CRAFT.PASS);
                        break;
                    } else {
                        report.updateTestLog("ManagePayees", "Blue Button Clicked on Manage Payees: and Pay Button NOT Enable '" + getText(getObject(("xpath~//*[@id='C5__FS_QUE_3283E3CB3B21920D1399547_R" + irow + "]'"))) + "'", Status_CRAFT.FAIL);
                    }
                }

            }
        } else {
            managePayeesTbl = "//div[contains(@class,'hidden-desktop col-hidden-md ')]/..";
            List<WebElement> elmLabels = driver.findElements(By.xpath("//div[contains(@class,'hidden-desktop col-hidden-md ')]/.."));
            for (int irow = 1; irow <= elmLabels.size(); irow++) {
                String Expvalue = getText(getObject("xpath~//*[@id='C5__FMT_C39AA4A7AE355E601046826_R" + irow + "']"));
                String Actualvalue = "Inactive";

                if (!Expvalue.contains(Actualvalue)) {
                    clickJS(getObject("xpath~//*[@id='C5__row_HEAD_C39AA4A7AE355E601360437_R" + irow + "']"), "Arrow navigation clicked");
                    report.updateTestLog("ManagePayees", "Blue Button Clicked on Manage Payees: and Pay Button is Enable '" + getText(getObject(("xpath~//*[@id='C5__FS_QUE_3283E3CB3B21920D1399547_R" + irow + "]'"))) + "'", Status_CRAFT.PASS);
                    break;
                } else {
                    report.updateTestLog("ManagePayees", "Blue Button Clicked on Manage Payees: and Pay Button NOT Enable '" + getText(getObject(("xpath~//*[@id='C5__FS_QUE_3283E3CB3B21920D1399547_R" + irow + "]'"))) + "'", Status_CRAFT.FAIL);
                }
            }
        }

    }

    public void currencyConvsendMoney() throws Exception {
        //Click on Payments Tab
        clickJS(getObject(deviceType() + "home.tabPayments"), "Select 'Payments Tab'");
        Thread.sleep(4000);
        clickJS(getObject("SendMoney.iconSendMoney"), "select SEND MONEY Options");
        Thread.sleep(4000);
    }

    public void validateSelectAccountFrom() throws Exception {

        String strErrorMessageInputValue[] = dataTable.getData("General_Data", "VerifyDetails").split(";");
        //Click on Pay Button
        click(getObject("ManagePayee.btnPay"), "Select 'Select account'");
        Thread.sleep(4000);
        //Click on Payments Tab
        click(getObject("SendMoney.listSelectAccount"), "Select 'Select account'");
        Thread.sleep(4000);
        String strText = getText(getObject("SendMoney.lstOpenSelectaccount"));
        Thread.sleep(4000);
        for (int i = 0; i < strErrorMessageInputValue.length; i++) {
            String strErrorMessageCheck = strErrorMessageInputValue[i];
            String strInputValue = strErrorMessageCheck;
            if (strText.trim().contains(strInputValue.trim())) {
                report.updateTestLog("validatedropdownAccounts", " Dropdown :: Account :: '" + strInputValue + "':: Present on Account select dropdown :: " + "", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validatedropdownAccounts", " Dropdown :: Account :: '" + strInputValue + "':: is NOT displayed Successfully...", Status_CRAFT.FAIL);
            }
        }
    }

    //CFPUR-330
    // Verify Errors for Currency Convertor

    public void currencyConvertorErr() throws Exception {
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");
        String strErrorMessageInputValue[] = dataTable.getData("General_Data", "VerifyDetails").split("::");
        String strErrorMessage = "Please enter an amount.";
        String strCurrencyErrorMessage = "Please select a currency.";
        if (isElementDisplayed(getObject("Payments.CurrencyConvertor"), 5)) {
            clickJS(getObject("Payments.CurrencyConvertor"), "Currency Converter Button clicked");
            if (isElementDisplayed(getObject("Payments.btnConvertamnt"), 5)) {
                clickJS(getObject("Payments.btnConvertamnt"), "Convert amount Button clicked");
                if (isElementDisplayed(getObject("xpath~//*[contains(@class,'qlrError boi_input_sm_error boi-error-color boi-error-position boi-fs-m2  ')]"), 30)) {
                    String strText = getText(getObject("xpath~(//*[contains(@class,'qlrError boi_input_sm_error boi-error-color boi-error-position boi-fs-m2')])[1]"));
                    String strText2 = getText(getObject("xpath~(//*[contains(@class,'qlrError boi_input_sm_error boi-error-color boi-error-position boi-fs-m2')])[2]"));

                    if (strText.equals(strErrorMessage) && strText2.equals(strCurrencyErrorMessage)) {
                        // sendKeys(getObject("Payments.txtEnterAmtEUR"), "");
                        Thread.sleep(2000);
                        report.updateTestLog("validateCurrencyConverterErrors", " Input box on amount field :: Error Message :: '" + strText + "':: Input Value :: " + "", Status_CRAFT.PASS);
                        report.updateTestLog("validateCurrencyConverterErrors", " Input box on currency field :: Error Message :: '" + strText2 + "':: Input Value :: " + "", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("validateCurrencyConverterErrors", " Input box on amount field / on currency field :: Error Message :: '" + strErrorMessage + "':: OR :: '" + strCurrencyErrorMessage+ "':: is NOT displayed Successfully...for Input Value :: " + "", Status_CRAFT.FAIL);
                    }
                }

                for (int i = 0; i < strErrorMessageInputValue.length; i++) {
                    String strErrorMessageCheck[] = strErrorMessageInputValue[i].split(";");
                    String strInputValue = strErrorMessageCheck[0];
                    String strErrorMessages = strErrorMessageCheck[1];
                    if (isElementDisplayed(getObject("Payments.txtEnterAmtEUR"), 20)) {
                        Thread.sleep(2000);
                     //   clickJS(getObject("Payments.txtEnterAmtEUR"), "Entering value");
                        sendKeys(getObject("Payments.txtEnterAmtEUR"), strInputValue);
                        Thread.sleep(2000);
                        click(getObject("Payments.btnConvertamnt"));
                        Thread.sleep(4000);
                    }

                    if (isElementDisplayed(getObject("xpath~//*[contains(@class,'qlrError boi_input_sm_error boi-error-color boi-error-position boi-fs-m2  ')]"), 30)) {
                        String strText = getText(getObject("xpath~(//*[contains(@class,' boi_input_sm_error boi-error-color boi-error-position boi-fs-m2  ')])[1]"));
                        if (strText.equals(strErrorMessages.trim())) {
                            sendKeys(getObject("Payments.txtEnterAmtEUR"), "");
                            Thread.sleep(4000);
                            report.updateTestLog("validateCurrencyConverterErrors", " Input box :: Error Message :: '" + strText + "':: Input Value :: " + strInputValue, Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("validateCurrencyConverterErrors", " Input box :: Error Message :: '" + strErrorMessages + "':: is NOT displayed Successfully...for Input Value :: " + strInputValue, Status_CRAFT.FAIL);
                        }
                    }
                }
            }
        }
    }
    /*

     */

    public void sendMoneyDetailsSEPA() throws Exception {
        //Information text for Cross Customer
        if ((dataTable.getData("General_Data", "JurisdictionType").equals("Cross"))) {
            String UiAlertText = getText(getObject("SendMoney.infotxtUKDomesticCross"));
            String expectedAlerttxt = "The payees and currencies available to you for payments can differ between your EUR and GBP accounts.";
            if (UiAlertText.equals(expectedAlerttxt)) {
                report.updateTestLog("sendMoneyDetailsSEPA", "Information text for UK Domestic Payment for Cross is  displayed is as Expected '" + expectedAlerttxt + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetailsSEPA", "Information text for UK Domestic Payment for Cross is  displayed is NOT as Expected '" + expectedAlerttxt + "', Actual ''" + UiAlertText + "", Status_CRAFT.FAIL);
            }
        }

        // Verify ALert Text
        if ((dataTable.getData("General_Data", "JurisdictionType").equals("NI")) | (dataTable.getData("General_Data", "JurisdictionType").equals("GB"))) {
            String UiAlertText = getText(getObject("SendMoney.alrttxtUKDomestic"));
            String expectedAlerttxt = "You’re sending money outside the UK (SEPA). If you wish to make a non-SEPA payment, add the payee with a different currency.";
            if (UiAlertText.equals(expectedAlerttxt)) {
                report.updateTestLog("sendMoneyDetailsSEPA", "Alert text for UK Domestic Payment for NI/GB is  displayed is as Expected '" + expectedAlerttxt + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetailsSEPA", "Alert text for UK Domestic Payment for NI/GB is  displayed is NOT as Expected '" + expectedAlerttxt + "', Actual ''" + UiAlertText + "", Status_CRAFT.FAIL);
            }
            if (getText(getObject("SendMoney.sepaWarngTxt")).equals("You’re sending money outside the UK (SEPA). If you wish to make a non-SEPA payment, add the payee with a different currency.")) {
                report.updateTestLog("SendMoneySEPA", "Warning Text 'You’re sending money outside the UK (SEPA). If you wish to make a non-SEPA payment, add the payee with a different currency.' ia appearing on the screen", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("SendMoneySEPA", "Warning Text 'You’re sending money outside the UK (SEPA). If you wish to make a non-SEPA payment, add the payee with a different currency.' ia NOT appearing on the screen", Status_CRAFT.FAIL);
            }
            if (getText(getObject("SendMoney.txtFAQmsg")).equals("Check out our FAQs for further details on sending money and our schedule of charges for any associated charges.")) {
                report.updateTestLog("SendMoneySEPA", "FAQ Message text 'Check out our FAQs for further details on sending money and our schedule of charges for any associated charges.", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("SendMoneySEPA", "FAQ Message Text 'Check out our FAQs for further details on sending money and our schedule of charges for any associated charges.' ia NOT appearing on the screen", Status_CRAFT.FAIL);
            }
        }

        //Amount
        if (isElementDisplayed(getObject("SendMoney.lblAccount"), 5)) {
            report.updateTestLog("sendMoneyDetailsSEPA", "Label 'Amount' is displayed on Send Money enter details Page", Status_CRAFT.PASS);
            String uiCurrency = getText(getObject("SendMoney.lblCurrSymbol"));
            String expectedCurrency = dataTable.getData("General_Data", "Currency_Symbol");
            if (uiCurrency.equals(expectedCurrency)) {
                report.updateTestLog("sendMoneyDetailsSEPA", "Currency in Amount textbox is correctly displayed on Send Money enter details Page, Expected '" + expectedCurrency + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("sendMoneyDetailsSEPA", "Currency in Amount textbox is not correctly displayed on Send Money enter details Page, Expected '" + expectedCurrency + "'", Status_CRAFT.FAIL);
            }

            //entering amount
            if (!dataTable.getData("General_Data", "Pay_Amount").equals("")) {
                sendKeys(getObject("SendMoney.txtUKDomCurrAmount"), dataTable.getData("General_Data", "Pay_Amount"));
                waitForPageLoaded();
            }
        } else {
            report.updateTestLog("sendMoneyDetailsSEPA", "Label 'Amount' is NOT displayed on Send Money enter details Page", Status_CRAFT.FAIL);
        }

        //Reference
        String txtReference = dataTable.getData("General_Data", "BillReference");
        if (isElementDisplayed(getObject("AddBillPayee.lblReference"), 5)) {
            report.updateTestLog("sendMoneyDetailsSEPA", "Reference Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.lblSEPAVisibletoPayee"), 5)) {
                report.updateTestLog("sendMoneyDetailsSEPA", "Visible to Payee Label is displayed", Status_CRAFT.PASS);
                waitForElementPresent(getObject("AddBillPayee.txtReference"), 15);
                if (isElementDisplayed(getObject("AddBillPayee.txtReference"), 5)) {
                    scrollToView(getObject("AddBillPayee.txtReference"));
                    report.updateTestLog("sendMoneyDetailsSEPA", "Reference Text box is displayed", Status_CRAFT.PASS);
                    sendKeysJS(getObject("AddBillPayee.txtReference"), txtReference);
                } else {
                    report.updateTestLog("sendMoneyDetailsSEPA", "Reference Text box is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("sendMoneyDetailsSEPA", "Visible to Payee label is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("sendMoneyDetailsSEPA", "Reference label is not displayed", Status_CRAFT.FAIL);
        }

        //Description
        if (isElementDisplayed(getObject("SendMoney.lblDescription"), 5)) {
            report.updateTestLog("sendMoneyDetailsSEPA", "Description Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.txtUKDomDescription"), 5)) {
                report.updateTestLog("sendMoneyDetailsSEPA", "Description Prefilled Text box is displayed", Status_CRAFT.PASS);
                if (getText(getObject("SendMoney.txtUKDomDescription")).equals(dataTable.getData("General_Data", "BillerDescription"))) {
                    scrollToView(getObject("SendMoney.txtUKDomDescription"));
                    report.updateTestLog("sendMoneyDetailsSEPA", "Description Text '" + dataTable.getData("General_Data", "BillerDescription") + "' is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("sendMoneyDetailsSEPA", "Description Text '" + dataTable.getData("General_Data", "BillerDescription") + "' is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("sendMoneyDetailsSEPA", "Description Prefilled Text box is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("sendMoneyDetailsSEPA", "Description Label is not displayed", Status_CRAFT.FAIL);
        }

        //Message to Payee
        String msgtopayee = dataTable.getData("General_Data", "Relationship_Status");
        if (isElementDisplayed(getObject("SendMoney.lblMsgToPayee"), 5)) {
            report.updateTestLog("sendMoneyDetailsSEPA", "Message to Payee Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.lblOptionalMsgToPayee"), 5)) {
                report.updateTestLog("sendMoneyDetailsSEPA", "Message to Payee (optional) Label is displayed", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("SendMoney.txtSEPAMsgtoPayee"), 5)) {
                    scrollToView(getObject("SendMoney.txtSEPAMsgtoPayee"));
                    report.updateTestLog("sendMoneyDetailsSEPA", "Message to Payee text box is displayed", Status_CRAFT.PASS);
                    if (!dataTable.getData("General_Data", "Relationship_Status").equals("")) {
                        click(getObject("SendMoney.txtSEPAMsgtoPayee"), "Message to Payee Text Box");
                        sendKeys(getObject("SendMoney.txtSEPAMsgtoPayee"), msgtopayee, "Message to Payee");
                    }
                } else {
                    report.updateTestLog("sendMoneyDetailsSEPA", "Message to Payee text box is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("sendMoneyDetailsSEPA", "Message to Payee (optional) Label is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("sendMoneyDetailsSEPA", "Message to Payee Label is not displayed", Status_CRAFT.FAIL);
        }

        //Process Payment
        if ((dataTable.getData("General_Data", "JurisdictionType").equals("ROI")) | (dataTable.getData("General_Data", "JurisdictionType").equals("Cross"))) {
            if (isElementDisplayed(getObject("SendMoney.lblUKDomProcessPayment"), 5)) {
                report.updateTestLog("sendMoneyDetailsSEPA", "Process Payment Label is displayed", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("SendMoney.txtUKDomProcessPayment"), 5)) {
                    report.updateTestLog("sendMoneyDetailsSEPA", "Process Payment Text box is displayed", Status_CRAFT.PASS);
                    if (getText(getObject("SendMoney.txtUKDomProcessPayment")).equals(dataTable.getData("General_Data", "Process_Transfer"))) {
                        scrollToView(getObject("SendMoney.txtUKDomProcessPayment"));
                        report.updateTestLog("sendMoneyDetailsSEPA", "Process Payment Text '" + dataTable.getData("General_Data", "BillerDescription") + "' is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("sendMoneyDetailsSEPA", "Process Payment Text '" + dataTable.getData("General_Data", "BillerDescription") + "' is not displayed", Status_CRAFT.FAIL);
                    }
                } else {
                    report.updateTestLog("sendMoneyDetailsSEPA", "Process Payment Prefilled Text box is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("sendMoneyDetailsSEPA", "Process Payment Label is not displayed", Status_CRAFT.FAIL);
            }
        }

        //continue & Goback
        if (isElementDisplayed(getObject("AddPayee.GoBack"), 5)) {
            report.updateTestLog("sendMoneyDetailsSEPA", "Button 'Go back' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetailsSEPA", "Button 'Go back' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.Continue"), 5)) {
            report.updateTestLog("sendMoneyDetailsSEPA", "Button 'Continue' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("sendMoneyDetailsSEPA", "Button 'Continue' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.Continue")));
                    Thread.sleep(2000);
                    click(getObject("AddPayee.Continue"), "Clicked on 'Continue', on Page");
                    break;
                case "GO BACK":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.GoBack")));
                    Thread.sleep(2000);
                    click(getObject("AddPayee.GoBack"), "Clicked on 'Go back', on Page");
                    if (isElementDisplayed(getObject("xpath~//h1[@title='Payments']"), 5)) {
                        report.updateTestLog("sendMoneyDetailsSEPA", "Page navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("sendMoneyDetailsSEPA", "Page does not navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("sendMoneyDetailsSEPA", "Please provide the valid operation on Send Money Page, Continue or Go Back", Status_CRAFT.FAIL);
            }
        }
    }

    /*
    Function: To add the inpurt details for Adding UK Domestic payee (CFPUR-278)
     */
    public void addUKDomPayeeDetails() throws Exception {
        String Name = dataTable.getData("General_Data", "FirstName");
        String NSC = dataTable.getData("General_Data", "NationalSortCode");
        String AccNo = dataTable.getData("General_Data", "AccountNumber");
        String Ref = dataTable.getData("General_Data", "ReferenceNumber");
        String description = dataTable.getData("General_Data", "Nickname");
        String strErrorCheck = dataTable.getData("General_Data", "ErrorText");

        //Check the Header = Country & currency
        if (isElementDisplayed(getObject("xpath~//*[text()='Country & currency']"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Header of page diaplayed is: Country & currency", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Header of page is ot displayed as: Country & currency", Status_CRAFT.FAIL);
        }

        //Check the Label = Country
        if (isElementDisplayed(getObject("xpath~//*[text()='Country ']"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : Country", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : Country", Status_CRAFT.FAIL);
        }

        //Check the Label = Location of Payee's bank
        if (isElementDisplayed(getObject("xpath~//*[@class='boi-standard-question-label  ']/div/div/label/div[contains(text(),'Location')]"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : Location of Payee's bank", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : Location of Payee's bank", Status_CRAFT.FAIL);
        }

        //Check the Defaulted Country
        String strDefaultedCountry = "xpath~//div[contains(@class,'aria_exp_wrapper')]/button[text()='United Kingdom']";
        if (isElementDisplayed(getObject(strDefaultedCountry), 3)) {
            report.updateTestLog("addUKDomPayeeDetails", "User Specific Default Country :: United Kingdom ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "The Pay To Country is not Defaulted :: United Kingdom ", Status_CRAFT.FAIL);
        }

        //Check the Label = Currency
        if (isElementDisplayed(getObject("xpath~//*[contains(@class,'tc-full-width boi_label boi-full-width tc-question-part')]/div/*[text()='Currency']"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : Currency", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : Currency", Status_CRAFT.FAIL);
        }

        //Check the defaulted currency
        String strValueDefaulted = "xpath~//span[@class='boi_input'][contains(text(),'GBP - UK Sterling')]";
        if (isElementDisplayed(getObject(strValueDefaulted), 3)) {
            report.updateTestLog("addUKDomPayeeDetails", "Defaulted Currency :: GBP - UK Sterling :: for country : United Kingdom", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Defaulted Currency is NOT displayed correctly :: GBP - UK Sterling :: for country : United Kingdom ", Status_CRAFT.FAIL);
        }

        //Check the Label = Visible to payee
        if (isElementDisplayed(getObject("xpath~//div[contains(text(),'Visible to payee.')]"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : Visible to payee", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : Visible to payee", Status_CRAFT.FAIL);
        }

        //Check the Label = Description
        if (isElementDisplayed(getObject("xpath~//label[contains(text(),'Description')]"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : Description", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : Description", Status_CRAFT.FAIL);
        }

        //continue & Goback
        if (isElementDisplayed(getObject("AddPayee.GoBack"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Button 'Go back' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Button 'Go back' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayee.Continue"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Button 'Continue' is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Button 'Continue' is NOT displayed on Page", Status_CRAFT.FAIL);
        }

        //Check the Label = Name and send keys
        if (isElementDisplayed(getObject("xpath~//label[contains(text(),'Name')]"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : Name", Status_CRAFT.PASS);
            Thread.sleep(1000);
            driver.findElement(By.xpath("//input[@name='C5__BOIPAYEEDETAILS[1].ADDPAYEE[1].NAME']")).clear();
            clickJS(getObject("AddPayeeUKDom.Name"), "Name Textbox");
            if (!Name.equalsIgnoreCase("")) {
                Thread.sleep(1000);
                sendKeys(getObject("AddPayeeUKDom.Name"), Name, "Name Textbox");
            }
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : Name", Status_CRAFT.FAIL);
        }

        //Check the Label = Account Number and send keys
        if (isElementDisplayed(getObject("xpath~//label[contains(text(),'Account number')]"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : Account number", Status_CRAFT.PASS);
            Thread.sleep(1000);
            driver.findElement(By.xpath("//input[@name='C5__BOIPAYEEDETAILS[1].ADDPAYEE[1].ACCOUNTNUMBER']")).clear();
            clickJS(getObject("AddPayeeUKDom.AccNo"), "Account Number Textbox");
            if (!AccNo.equalsIgnoreCase("")) {
                Thread.sleep(1000);
                sendKeys(getObject("AddPayeeUKDom.AccNo"), AccNo, "Account Number Textbox");
            }
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : Account number", Status_CRAFT.FAIL);
        }


        //Check the Label = NSC and send keys
        if (isElementDisplayed(getObject("xpath~//label[contains(text(),'National sort code')]"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : National sort code", Status_CRAFT.PASS);
            driver.findElement(By.xpath("//input[@name='C5__BOIPAYEEDETAILS[1].ADDPAYEE[1].SORTCODE']")).clear();
            clickJS(getObject("AddPayeeUKDom.NSC"), "National Sort Code Textbox");
            if (!NSC.equalsIgnoreCase("")) {
                Thread.sleep(1000);
                sendKeys(getObject("AddPayeeUKDom.NSC"), NSC, "National Sort Code Textbox");
            }
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : National sort code", Status_CRAFT.FAIL);
        }

        //Check the Label = Reference
        if (isElementDisplayed(getObject("xpath~//label[contains(text(),'Reference')]"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : Reference", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : Reference", Status_CRAFT.FAIL);
        }

        //Check the Label = Visible to payee
        if (isElementDisplayed(getObject("xpath~//div[contains(text(),'Visible to payee.')]"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : Visible to payee", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : Visible to payee", Status_CRAFT.FAIL);
        }

        //Check the Label = Reference(optional)
        if (isElementDisplayed(getObject("xpath~//label[contains(text(),'Reference')]/span[contains(text(),'optional')]"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : (optional)", Status_CRAFT.PASS);
            driver.findElement(By.xpath("//input[@name='C5__BOIPAYEEDETAILS[1].ADDPAYEE[1].MESSAGECONTENT']"));
            clickJS(getObject("AddPayeeUKDom.Ref"), "Reference Textbox");
            if (!Ref.equalsIgnoreCase("")) {
                Thread.sleep(1000);
                sendKeys(getObject("AddPayeeUKDom.Ref"), Ref, "Reference Textbox");
                Thread.sleep(1000);
            }
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : (optional)", Status_CRAFT.FAIL);
        }

        //Check the Label = Description(optional)
        if (isElementDisplayed(getObject("xpath~//label[contains(text(),'Description')]/span[contains(text(),'(optional)')]"), 5)) {
            report.updateTestLog("addUKDomPayeeDetails", "Label displayed as : (optional)", Status_CRAFT.PASS);
            driver.findElement(By.xpath("//input[@name='C5__BOIPAYEEDETAILS[1].ADDPAYEE[1].DESCRIPTIONCONTENT']")).clear();
            clickJS(getObject("AddPayeeUKDom.Desc"), "Description Textbox");
            if (!description.equalsIgnoreCase("")) {
                Thread.sleep(1000);
                sendKeys(getObject("AddPayeeUKDom.Desc"), description, "Description Textbox");
                clickJS(getObject("xpath~//label[contains(text(),'Description')]"), "Description");
                Thread.sleep(2000);
            }
        } else {
            report.updateTestLog("addUKDomPayeeDetails", "Label not displayed as : (optional)", Status_CRAFT.FAIL);
        }

        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "CONTINUE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.Continue")));
                    Thread.sleep(2000);
                    clickJS(getObject("AddPayee.Continue"), "Clicked on 'Continue', on Page");
                    Thread.sleep(2000);
                    break;
                case "GO BACK":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("AddPayee.GoBack")));
                    Thread.sleep(2000);
                    clickJS(getObject("AddPayee.GoBack"), "Clicked on 'Go back', on Page");
                    if (isElementDisplayed(getObject("xpath~//h1[@title='Payments']"), 5)) {
                        report.updateTestLog("sendMoneyDetailsSEPA", "Page navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("sendMoneyDetailsSEPA", "Page does not navigated to Payment landing Page upon clicking 'Go back'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("sendMoneyDetailsSEPA", "Please provide the valid operation on Send Money Page, Continue or Go Back", Status_CRAFT.FAIL);
            }
        }

        //Check the error : When fields are blank
        if (strErrorCheck.length() > 0) {
            clickJS(getObject("AddPayee.Continue"), "Add Payee : Continue button");
            Thread.sleep(3000);
            errorMessageValidations();
        }

    }

    /*
    Function: To validate UK Domestic Add Payee Review Page (CFPUR-278)
     */
    public void verifyUKDomesticAddPayeeReviewPage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails("SendMoney.pageView");
        SendMoneyReviewPageOperation();
    }

    /*
    Function: To verify the UK Domestic Confirmation Page
     */
    public void checkUKDomesticAddPayeeConfirmationPage() throws Exception {

        waitForElementPresent(getObject("AddPayee.hdrPageTitle"), 5);
        if (isElementDisplayed(getObject("AddPayee.hdrPageTitle"), 5)) {
            report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Section Header 'Add Payee' is displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Section Header 'Add Payee' is NOT displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.imgSuccess"), 5)) {
            report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Success Image is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Success Image is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }
        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Services.verifyElementDetailsConfim("AddPayeeUKDom.pgReview");

        if (isElementDisplayed(getObject("AddPayeeUKDom.btnbcktohome"), 30)) {
            report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Link Button 'Back to home' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Link Button 'Back to home' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPayeeUKDom.btnSendMoney"), 30)) {
            report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Button 'Send Money' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Button 'Send Money' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }


        if (isElementDisplayed(getObject("xpath~//h5[@class='boi_input_sm  ecDIB  '][contains(text(),'Time of request')]"), 5)) {
            report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Time Stamp with message 'Time of request' is displayed on Confirmation page", Status_CRAFT.PASS);

            String strRequestSubmitted = getText(getObject("xpath~//h5[@class='boi_input_sm  ecDIB  '][contains(text(),'Time of request')]"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Time Stamp with message 'Time of request' is NOT displayed on Cancelled Standing Order Confirmation page", Status_CRAFT.FAIL);
        }

        //Validating Navigation/functionality of the buttons
        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to home")) {
            click(getObject("AddPayeeUKDom.btnbcktohome"), "Back to home button");
            if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 5)) {
                report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Upon clicking 'Back to payments' ,successfully Navigated to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Upon clicking 'Back to payments' ,navigation unsuccessful to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.FAIL);
            }
        }

        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Send money")) {
            click(getObject("AddPayeeUKDom.btnSendMoney"), "Send Money button");
            if (isElementDisplayed(getObject("SendMoney.hdrtxt"), 5)) {
                report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Upon clicking 'Send money' ,successfully Navigated to send noney page from Add Payee Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("checkUKDomesticAddPayeeConfirmationPage", "Upon clicking 'Send money' ,navigation unsuccessful to send money page from Add Payee Confirmation Page", Status_CRAFT.FAIL);
            }
        }

    }

    /*
    Function: Error Validation (CFPUR-278)
     */
    public void errorMsgValidation() throws Exception {
        String errorMessage[] = dataTable.getData("General_Data", "ErrorText").split("::");
        for (int i = 0; i < errorMessage.length; i++) {
            if (isElementDisplayed(getObject("xpath~//*[contains(@class,'boi_input_sm_error tc-error-position tc-fs-m2')][text()='" + errorMessage[i] + "']"), 5)) {
                report.updateTestLog("errorMsgValidation", "Error Message '" + errorMessage[i] + "' is displayed on Add Payee Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMsgValidation", "Error Message '" + errorMessage[i] + "' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
            }
        }
    }


    public void verifyAllPaymentsOptions() throws Exception {
        clickPaymentsPage();
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails(deviceType() + "payments.pagePayments");
        verifyPaymentsPageDetails(deviceType() + "payments.pagePayments");
        verifyTransfer();
        verifySendMoney();
        verifyNavigationToAllPaymentsOprions();
    }

    public void verifyPaymentsOptions() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 10)) {
            click(getObject(deviceType() + "home.tabPayments"));
            String strMenuOptionPage = "xpath~//*[text()='Payments']";
            String[] arrValidation = {"Payments.standingOrders", "Payments.managePayees", "Payments.directDebits"};

            if (isElementDisplayed(getObject(strMenuOptionPage), 15)) {
                for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                    String strText = getText(getObject(arrValidation[intValidate]));
                    if (isElementDisplayed(getObject(arrValidation[intValidate]), 1)) {
                        report.updateTestLog("verifyPaymentsOptions", "Element ::" + arrValidation[intValidate] + "::is displayed::" + strText, Status_CRAFT.DONE);
                    } else {
                        report.updateTestLog("verifyPaymentsOptions", "Element ::" + arrValidation[intValidate] + "::is not displayed::" + strText, Status_CRAFT.FAIL);
                    }
                }
            }
        }
    }


    /*
    CFPUR-6763: Remove future dated payments menu item from Payments landing page
    Function: To verify the absence of Future Dated Payments option from Payments page menu list
    CREATED BY: C103403
     */
    public void verifyfuturedatedoptions() throws Exception {
        isElementDisplayedWithLog(getObject("payments.lblFutureDatedPayment"), "Future Dated Payments Option", "Menu list of Payments page", 5);
    }

    /*
    CFPUR-1669: BIC / IBAN calculator
    Function: To verify if BIC/IBAN calculator is displayed for the SEPA Payee, and to verify the link when URL opens up in the browser
     */
    public void verifyBICIBANCalculator() throws Exception {
        if (isElementDisplayed(getObject("AddPayee.linkBICIBANCalc"), 5)) {
            report.updateTestLog("verifyBICIBANCalculator", "BIC/IBAN calculator is appearing in Add Payee page when adding a SEPA Payee", Status_CRAFT.PASS);
            HomePage homePage = new HomePage(scriptHelper);
            homePage.verifyLinkAndItsNavigation();
        } else {
            report.updateTestLog("verifyBICIBANCalculator", "BIC/IBAN calculator is NOT appearing in Add Payee page when adding a SEPA Payee", Status_CRAFT.FAIL);
        }
    }

    public void choosePayeeCountry() throws Exception {
        if (dataTable.getData("General_Data", "JurisdictionType").equalsIgnoreCase("Cross")) {
            if (!dataTable.getData("General_Data", "PayFrom_Account").equals("")) {
                click(getObject("AddBillPayee.CrossPayFromAccount"));
                driver.findElement(By.xpath("//button[contains(@class,'boi-rounded-1 boi_input tc-form-control exp_button_widget')]/following-sibling::ul/li[contains(.,'" + dataTable.getData("General_Data", "PayFrom_Account") + "')]")).click();
                report.updateTestLog("addBillPayeeDetails", "Account Selected '" + dataTable.getData("General_Data", "PayFrom_Account") + "' from the Pay From Dropdown", Status_CRAFT.PASS);
                waitForPageLoaded();
                clickJS(getObject("xpath~//*[text()='Continue']"), "Continue");
            } else {
                report.updateTestLog("addBillPayeeDetails", "Account is not displayed in Pay From Account dropdown", Status_CRAFT.FAIL);
            }
        }
        if (!dataTable.getData("General_Data", "PayeeCountry").equals("")) {
            String expectedCountry = dataTable.getData("General_Data", "DefaultCountry");
            if (deviceType.equalsIgnoreCase("Web")) {
                click(getObject("AddPayee.lblDefaultCountry"), "Country dropdown");
                String countryName = dataTable.getData("General_Data", "PayeeCountry");
                click(getObject("xpath~//button[text()='" + expectedCountry + "']/following-sibling::ul/li[contains(.,'" + countryName + "')]"), "Country");
            } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
                clickJS(getObject("AddPayee.lblDefaultCountry"), "Country dropdown");
                String countryName = dataTable.getData("General_Data", "PayeeCountry");
                clickJS(getObject("xpath~//button[@id='C6__QUE_E50DFFB0F8190DA23674858_widgetARIA']/following-sibling::ul/li[contains(.,'" + countryName + "')]"), "Country Selected");
            }
            Thread.sleep(2000);
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
            Thread.sleep(2000);
        }
    }

    /*
    CFPUR-6607: Add SEPA payee country information to manage payee page
    Function: To verify that if country is displayed for all the payees in manage payees page
     */

    public void verifyPayeeCountry() throws Exception {
        List<WebElement> elmLabels = driver.findElements(getObject("ManagePayee.tablerows"));
        for (int i = 1; i < elmLabels.size(); i++) {
            if (isElementDisplayed(getObject("xpath~//tr[@class='boiPayeeImage .boiArrowTable__row tc-ripple-effect inactive   red'][" + i + "]"), 10)) {
                String selectedPayee = getText(getObject("ManagePayee.selectedPayee"));
                scrollToView(getObject("ManagePayee.selectedPayee"));
                report.updateTestLog("verifyPayeeCountry", "Validation check for payee '" + selectedPayee + "'", Status_CRAFT.DONE);
                if (isElementDisplayed(getObject("xpath~//tr[@class='boiPayeeImage .boiArrowTable__row tc-ripple-effect inactive   red'][" + i + "]/td[@class='col-full-xs col-5-8-sm col-8-12-md col-8-12-lg col-10-16-xl tc-answer-part  ']"), 5)) {
                    String actCountry = getText(getObject("ManagePayee.selectedCoutry"));
                    if (!actCountry.equals("")) {
                        report.updateTestLog("verifyPayeeCountry", "Country '" + actCountry + "' is displayed for the Payee '" + selectedPayee + "' ", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyPayeeCountry", "Country is NOT displayed for the Payee", Status_CRAFT.FAIL);
                    }
                }
            } else {
                report.updateTestLog("verifyPayeeCountry", "Inactive Payee is unavailable", Status_CRAFT.FAIL);
            }
        }
    }

    public void billPaymentsReviewPage() throws Exception {
        //validating page title
        validatePageHeader();

        //Section header-Review
        if (isElementDisplayed(getObject("xpath~//h3[text()='Review']"), 5)) {
            report.updateTestLog("BillPaymentsReviewPage", "Section header 'Review' is displayed on Send Money Review page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("BillPaymentsReviewPage", "Section header 'Review' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }

        //Amount in
        String strAmountIn = dataTable.getData("General_Data", "Current_Balance");
        if (isElementDisplayed(getObject("xpath~//*[text()='Amount']"), 5)) {
            report.updateTestLog("BillPaymentsReviewPage", "Label 'Amount' is displayed on Send Money Review page", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("xpath~//*[text()='Amount']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/div"), 5)) {
                String actAmount = getText(getObject("xpath~//*[text()='Amount']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/div"));
                if (actAmount.equals(strAmountIn)) {
                    report.updateTestLog("BillPaymentsReviewPage", "Amount '" + strAmountIn + "' is appearing on Review Page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("BillPaymentsReviewPage", "Amount '" + strAmountIn + "' is not appearing on Review Page", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("BillPaymentsReviewPage", "Label '" + strAmountIn + "' is NOT displayed on Send Money Review page", Status_CRAFT.FAIL);
        }

        //validate review section details
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {
            if (!obj.getValue().equals("NA")) {

                if (obj.getKey().equals("Message to payee")) {
                    if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
                        report.updateTestLog("BillPaymentsReviewPage", "'" + obj.getKey() + "' displayed on review page", Status_CRAFT.PASS);
                        String expectedVal = getText(getObject("xpath~//*[text()='Message to payee']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[@id='C5__GROUP_FS_QUE_1F8AD46D586A83031462427']")).replaceAll("\n", " ");
                        if (expectedVal.equals(obj.getValue())) {
                            report.updateTestLog("BillPaymentsReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("BillPaymentsReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
                        }
                    } else {
                        report.updateTestLog("BillPaymentsReviewPage", "'" + obj.getKey() + "' Not displayed on review page", Status_CRAFT.FAIL);
                    }
                } else {
                    boolean bflag = driver.findElement(By.xpath("//*[text()='" + obj.getKey() + "']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[text()='" + obj.getValue() + "']")).isDisplayed();
                    if (bflag) {
                        report.updateTestLog("BillPaymentsReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("BillPaymentsReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
                    }
                }

            } else {
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
                    report.updateTestLog("BillPaymentsReviewPage", "'" + obj.getKey() + "' is displayed on review page for blank input", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("BillPaymentsReviewPage", "'" + obj.getKey() + "' is Not displayed on review page for blank input", Status_CRAFT.FAIL);
                }
            }
        }
        String strProssTran = dataTable.getData("General_Data", "Process_Transfer");
        if (strProssTran.equalsIgnoreCase("Schedule future date")) {
            String ActDate = getText(getObject("xpath~//span[text()='Process payment']/ancestor::div[contains(@class,'question-part')]/following-sibling::div")).replace("\n"," ");
            String ExpDate = (scriptHelper.commonData.strTransferDate).replace("/", " ");
            if (ActDate.equals(ExpDate)) {
                report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + ExpDate + "' correctly displayed on review page,Expected::'" + ExpDate + "', Actual::''" + ActDate + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + ExpDate + "' is not correctly displayed on review page,Expected::'" + ExpDate + "', Actual::''" + ActDate + "'", Status_CRAFT.FAIL);
            }
            validateProcessingAndDailylimits();
        } else {
            validateDailylimitsNCutOffTimes();
        }

        if (dataTable.getData("General_Data", "JurisdictionType").equals("ROI")) {
            String ExpWarningtxt = "You are making a credit transfer to another account.";
            String ActWarningtxt = getText(getObject("xpath~//div[text()='You are making a credit transfer to another account.']"));
            if (ExpWarningtxt.equals(ActWarningtxt)) {
                report.updateTestLog("BillPaymentsReviewPage", "Warning Text 'You are making a credit transfer to another account.' is appearing on the Send Money Review Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("BillPaymentsReviewPage", "Warning Text 'You are making a credit transfer to another account.' is not appearing on the Send Money Review Page", Status_CRAFT.FAIL);
            }
        }

        SendMoneyReviewPageOperation();

    }

    public void verifyReviewPage() throws Exception {
        if (!scriptHelper.commonData.iterationFlag == true) {
            checkAddBillReviewPageDetails();
            scriptHelper.commonData.iterationFlag = true;
        } else {
            billPaymentsReviewPage();
        }
    }

    public void checkBillPaymentsConfirmationPage() throws Exception {

        waitForElementPresent(getObject(deviceType() + "SendMoney.hdrtxt"), 5);
        if (isElementDisplayed(getObject(deviceType() + "SendMoney.hdrtxt"), 5)) {
            report.updateTestLog("checkBillPaymentsConfirmationPage", "Section Header 'Send Money' is displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkBillPaymentsConfirmationPage", "Section Header 'Send Money' is displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddBillPayee.imgSuccess"), 5)) {
            report.updateTestLog("checkBillPaymentsConfirmationPage", "Success Image is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkBillPaymentsConfirmationPage", "Success Image is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        String[] arrValidation = dataTable.getData("General_Data", "DirectionalText").split(";");
        if (isElementDisplayed(getObject("SendMoney.confrmPage"), 5)) {
            String dataSectionUI = getText(getObject("SendMoney.confrmPage")).toUpperCase();
//            report.updateTestLog("checkBillPaymentsConfirmationPage", ":: Page Screenshot ::", Status_CRAFT.SCREENSHOT);
            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                String strValidateHead = arrValidation[intValidate].split("::")[0];
                String strValidateData = arrValidation[intValidate].split("::")[1];
                if (dataSectionUI.contains(strValidateData.toUpperCase())) {
                    report.updateTestLog("checkBillPaymentsConfirmationPage", strValidateHead + " value '" + strValidateData + "' is displayed correctly ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("checkBillPaymentsConfirmationPage", strValidateHead + " value '" + strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("checkBillPaymentsConfirmationPage", "Section element is not displayed on Screen", Status_CRAFT.FAIL);
        }

        String strProssTran = dataTable.getData("General_Data", "Process_Transfer");
        if (strProssTran.equalsIgnoreCase("Schedule future date")) {
            String ActDate = getText(getObject("xpath~//span[text()='Process payment']/..//following-sibling::div")).replace("\n", " ");
            String ExpDate = (scriptHelper.commonData.strTransferDate).replace("/", " ");
            if (ActDate.equals(ExpDate)) {
//            String ActDate = getText(getObject("xpath~//*[@class='accessibilityStyle'][text()='Day']/..")).replace("\n"," ");
//            String ExpDate = (scriptHelper.commonData.strTransferDate).replaceAll("/", " ");
//            if (ActDate.replaceAll("  "," ").contains(ExpDate)) {
                report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + ExpDate + "' correctly displayed on review page,Expected::'" + ExpDate + "', Actual::''" + ActDate + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyUKDomesticPaymentsReviewPage", "'" + ExpDate + "' is not correctly displayed on review page,Expected::'" + ExpDate + "', Actual::''" + ActDate + "'", Status_CRAFT.FAIL);
            }
        }
        if (isElementDisplayed(getObject("SendMoney.btnBackToPayment"), 5)) {
            report.updateTestLog("checkBillPaymentsConfirmationPage", "Link Button 'Back to payments' is displayed on Confirmation page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("checkBillPaymentsConfirmationPage", "Link Button 'Back to payments' is NOT displayed on Confirmation page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("xpath~//div[@class='boi-position-relative  ']/div/div[contains(text(),'Time of request')]"), 5)) {
            report.updateTestLog("checkBillPaymentsConfirmationPage", "Time Stamp with message 'Time of request' is displayed on Confirmation page", Status_CRAFT.PASS);

            String strRequestSubmitted = getText(getObject("xpath~//div[@class='boi-position-relative  ']/div/div[contains(text(),'Time of request')]"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("checkBillPaymentsConfirmationPage", "Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("checkBillPaymentsConfirmationPage", "Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("checkBillPaymentsConfirmationPage", "Time Stamp with message 'Time of request' is NOT displayed on Cancelled Standing Order Confirmation page", Status_CRAFT.FAIL);
        }

        //Validating Navigation/functionality of the buttons
        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to payments")) {
            clickJS(getObject("SendMoney.btnBackToPayment"), "'Back to payments'");
            if (isElementDisplayed(getObject(deviceType() + "home.tabPaymentshdr"), 5)) {
                report.updateTestLog("checkBillPaymentsConfirmationPage", "Upon clicking 'Back to payments' ,successfully Navigated to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("checkBillPaymentsConfirmationPage", "Upon clicking 'Back to payments' ,navigation unsuccessful to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.FAIL);
            }
        }
    }

    /*
    Function: To Verify the Authentication Failure Page and Its Operation
     */
    public void verifyAuthFailure() throws Exception {
        //verify the header of the page -> Update 'Something went wrong' in Excel
        validatePageHeader();

        //verify the text and image of failure
        isElementDisplayedWithLog(getObject(deviceType()+"payments.imgAuthFail"), "Error Image", "Authentication Failure Screen", 5);
        if (isElementDisplayed(getObject(deviceType()+"payments.txtInfoAuthFail"),5)){
            String expInfoText = dataTable.getData("General_Data", "ErrorText");
            String actInfoText = getText(getObject(deviceType()+"payments.txtInfoAuthFail"));
            System.out.print(actInfoText);
            if (expInfoText.equals(actInfoText)){
                report.updateTestLog("verifyAuthFailure","Information Text is appearing as per the expectation, 'Expectation': '"+expInfoText+"', 'Actual': '"+expInfoText+"'",Status_CRAFT.PASS);
            }else{
                report.updateTestLog("verifyAuthFailure","Information Text is not appearing as per the expectataion",Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAuthFailure", "Information Text is NOT appearing on the Screen", Status_CRAFT.PASS);
        }

        //Verify the buttons present on the screen -> Try again &   Review details buttons
        isElementDisplayedWithLog(getObject("payments.btnTryAgain"), "Try Again button", "Authentication Failure Page", 5);
        isElementDisplayedWithLog(getObject("payments.btnReviewDetails"), "Review details button", "Authentication Failure Page", 5);

        //Operation
        if (!dataTable.getData("General_Data", "Properties_Variable").equals("")) {
            String strOperationOnPutonHold = dataTable.getData("General_Data", "Properties_Variable");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()) {
                case "TRY AGAIN":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("payments.btnTryAgain")));
                    clickJS(getObject("payments.btnTryAgain"), "Try Again button on Authentication Failure Page");
                    break;
                case "REVIEW DETAILS":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("payments.btnReviewDetails")));
                    clickJS(getObject("payments.btnReviewDetails"), "Review Details button on Authentication Failure Page");
                    isElementDisplayedWithLog(getObject("xpath~//h3[text()='Review']"),"Review header", "Review Page",5);
                    break;
                default:
                    report.updateTestLog("verifyAuthFailure", "Please provide the valid operation on Authentication Failure Screen, Try Again or Review Details", Status_CRAFT.FAIL);
            }
        }

    }

    public void selectPayFromOnSendMoney() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "SendMoney.PageHeader"), 10)) {
            report.updateTestLog("selectPayFromOnSendMoney", "Send Money Page header correctly displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPayFromOnSendMoney", "Send Money Page header does not exist", Status_CRAFT.FAIL);
        }
        String payfromacc = dataTable.getData("General_Data", "PayFrom_Account");
        if (!payfromacc.equals("")) {
            clickJS(getObject("SendMoney.lstSelectAccount"), "Click on Select Account from which money to send.");
            Thread.sleep(5000);
//            click(getObject("xpath~//li[contains(text(),'" + payfromacc + "')]"),"Account '" + payfromacc + "'");
            driver.findElement(By.xpath("//li[contains(text(),'" + payfromacc + "')]")).click();
            report.updateTestLog("selectPayFromOnSendMoney", "Pay From account selected '" + payfromacc + "'", Status_CRAFT.PASS);
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
            if (!dataTable.getData("General_Data", "PayFrom_Account").equals("")) {
                clickJS(getObject("Sendmoney.lstPayFromAccount"), "Pay From Dropdown");
                Thread.sleep(2000);
                //if(isElementDisplayed(getObject("xpath~//ul/li[contains(.,'" + dataTable.getData("General_Data", "PayFrom_Account") + "')]"),10)){
                driver.findElement(By.xpath("//ul/li[contains(.,'" + dataTable.getData("General_Data", "PayFrom_Account") + "')]")).click();
                report.updateTestLog("selectPayFromOnSendMoney", "Pay to account selected '" + dataTable.getData("General_Data", "PayFrom_Account") + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPayFromOnSendMoney", "Pay to account drop down value  '" + dataTable.getData("General_Data", "PayFrom_Account") + "' is not present/found", Status_CRAFT.FAIL);
            }
            waitForPageLoaded();
            Thread.sleep(2000);
        }
    }


    /**
     * Function : To select account from PayFrom for send money
     * Created by : C966003
     * Update on    Updated by     Reason
     * 26/06/2019   C966003        Newly created
     */
    public void payFrom_SendMoney() throws Exception {
        scriptHelper.commonData.strPayFromAccount = dataTable.getData("General_Data", "PayFrom_Account");
        isElementDisplayedWithLog(getObject(deviceType() + "AddPayee.headerPayFrom"),
                "Page header 'Send Money'","Send Money",3);
        isElementDisplayedWithLog(getObject("Payments.lblPayFrom"),
                "Section header 'Pay from'","Send Money",2);
        isElementDisplayedWithLog(getObject("Payments.lblAccount"),
                "label 'Account' for 'Pay from' list","Send Money",3);
        isElementDisplayedWithLog(getObject("SendMoney.lstSelectAccount"),
                "Pay From account list", "Send Money", 3);
        clickJS(getObject("SendMoney.lstSelectAccount"),"Select account");
        clickJS(getObject("xpath~//li[contains(text(),'" + scriptHelper.commonData.strPayFromAccount.split("~")[1] + "')]"),"Pay from account : "+scriptHelper.commonData.strPayFromAccount);
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
    }

    /**
     * Function : To select account from Pay for send money
     * Created by : C966003
     * Update on    Updated by     Reason
     * 26/06/2019   C966003        Newly created
     */
    public void payTo_SendMoney() throws Exception {
        scriptHelper.commonData.strPayToAccount = dataTable.getData("General_Data", "PayTo_Account");
        String strPayToAccount = scriptHelper.commonData.strPayToAccount;
        while (isElementDisplayed(getObject("launch.spinSpinner"), 10)) {
            waitForPageLoaded();
            Thread.sleep(3000);
        }
        isElementDisplayedWithLog(getObject("Payments.lblPayTo"),
                "Section header 'Pay to'","Send Money",3);
        isElementDisplayedWithLog(getObject(deviceType() + "Payments.lblSelectPayee"),
                "label 'Select payee' for 'Pay to' list","Send Money",3);
        if (!deviceType().equalsIgnoreCase("mw.")) {
            isElementDisplayedWithLog(getObject("sendMoney.lstToFrom"),
                    "Pay To account list", "Send Money", 3);
            while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
                waitForPageLoaded();
            }
            clickJS(getObject("sendMoney.lstToFrom"), "Select payee dropdown");
            if (strPayToAccount.contains("~")) {
                waitForElementPresent(getObject("xpath~//li[contains(text(),'" + strPayToAccount.split("~")[1] + "')]"), 10);
                clickJS(getObject("xpath~//li[contains(text(),'" + strPayToAccount.split("~")[1] + "')]"), "Payee from list");
            } else {
                waitForElementPresent(getObject("xpath~//li[contains(text(),'" + strPayToAccount + "')]"), 10);
                scrollToView(getObject("xpath~//li[contains(text(),'" + strPayToAccount + "')]"));
                clickJS(getObject("xpath~//li[contains(text(),'" + strPayToAccount + "')]"), "Payee from list");
            }
            while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
                waitForPageLoaded();
            }
        } else if (deviceType().equalsIgnoreCase("mw.")){
            isElementDisplayedWithLog(getObject(deviceType() + "sendMoney.lstToFrom"),
                    "Pay To account list", "Send Money", 3);
            clickJS(getObject(deviceType() + "sendMoney.lstToFrom"), "Select payee dropdown");
            while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
                waitForPageLoaded();
            }
            clickJS(getObject("xpath~//*[contains(@class,'boi_label')][contains(text(),'" + strPayToAccount.split("~")[0].trim() + "')]"), "Payee from list");
            while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
                waitForPageLoaded();
            }
        }
    }

    /**
     * Function : To enter amount for send money
     * Created by : C966003
     * Update on    Updated by     Reason
     * 26/06/2019   C966003        Newly created
     */
    public void amount_Sendmoney() throws Exception {
        scriptHelper.commonData.strAmount = dataTable.getData("General_Data", "Pay_Amount");
        String strAmount ="";
        if (!isElementDisplayed(getObject("xpath~//input[contains(@name,'INTERNATIONALFORM[1].AMOUNT')]"),3)){
            strAmount = "sendMoney.edtAmount";
        } else {
            strAmount = "sendMoney.edtInternationalAmount";
        }
        isElementDisplayedWithLog(getObject(strAmount),
                "Amount", "Send Money", 3);
        sendKeys(getObject(strAmount), scriptHelper.commonData.strAmount, "Amount");
        waitForPageLoaded();
    }
    /**
     * Function : To enter reason for payment for international flow of send money
     * Created by : C966003
     * Update on    Updated by     Reason
     * 29/10/2019   C966003        Newly created
     */
    public void reasonForPayment() throws Exception{
        String strReasonForPayment = dataTable.getData("General_Data","PaymentReason");
        isElementDisplayedWithLog(getObject("sendMoney.lblReasonForPayment"),
                "Label : 'Reason for payment'","Send Money",3);
        sendKeysJS(getObject("sendMoney.edtReasonForPayment"),strReasonForPayment, "Reason for payment");
    }


    /**
     * Function : To check error validation for reference field for send money
     * Created by : C966003
     * Update on    Updated by     Reason
     * 26/06/2019   C966003        Newly created
     */
    public void errorMsg_SendMoney() throws Exception {
        String strErrRefernce = dataTable.getData("General_Data", "ErrorText");
        String strRefernceNumber = dataTable.getData("General_Data", "ReferenceNumber");
        String[] arrStrErrRefernce = strErrRefernce.split(";");
        //while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
        waitForPageLoaded();
        //}
        if(isElementDisplayed(getObject("sendMoney.lnkEditReference"),5)){
            clickJS(getObject("sendMoney.lnkEditReference"),"Edit Reference link");
            waitForJQueryLoad();
            waitForPageLoaded();
        }
        isElementDisplayedWithLog(getObject("sendMoney.edtRefernce"), "Reference", "Send Money", 5);
        sendKeys(getObject("sendMoney.edtRefernce"), "", "Blank reference");
        continue_SendMoney();
        fetchTextToCompare(getObject("sendMoney.errRefernce"),
                arrStrErrRefernce[0],"Error message for blank value in reference field");
        sendKeys(getObject("sendMoney.edtRefernce"),strRefernceNumber,"Special char reference");
        continue_SendMoney();
        fetchTextToCompare(getObject("sendMoney.errRefernce"),
                arrStrErrRefernce[1], "Error message for special char in reference field");
    }

    /**
     * Function : To add reference field for send money
     * Created by : C966003
     * Update on    Updated by     Reason
     * 26/06/2019   C966003        Newly created
     */
    public void reference_SendMoney() throws Exception {
        String strReference = dataTable.getData("General_Data", "BillReference");
        waitForPageLoaded();
        scrollToView(getObject("sendMoney.edtRefernce"));
        if(isElementDisplayed(getObject("sendMoney.lnkEditReference"),5)){
            clickJS(getObject("sendMoney.lnkEditReference"),"Edit Reference link");
            waitForJQueryLoad();
            waitForPageLoaded();
        }
        sendKeys(getObject("sendMoney.edtRefernce"), strReference, "Reference");
    }

    /**
     * Function : Validate the Bill Payee Page Errors
     * Update on    Updated by     Reason
     * 20/06/2019   C103401
     */

    public void BillPayment_AllError() throws Exception {
        waitForPageLoaded();
        String strAmountToSend = dataTable.getData("General_Data", "Current_Balance");
        String strBackNavigation = dataTable.getData("General_Data", "Operation");
        String ErrorValidationField = dataTable.getData("General_Data", "DirectionalText");
        String PayFromAccount = dataTable.getData("General_Data", "PayFrom_Account");
        String PayToAccount = dataTable.getData("General_Data", "PayTo_Account");

        //Navigate to till Send Money Page From Home Page
        if (!isMobile | !(deviceType()== "mw.")) {
            driver.findElement(By.xpath("//*[text()='PAYMENTS']")).click();
        }else {
            driver.findElement(By.xpath("//div/button[text()='Payments']")).click();
        }

        //Check the icon send money
        report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        if (isElementDisplayed(getObject("SendMoney.iconSendMoney"), 5)) {
            report.updateTestLog("SendMoneyFormHappyPath", " Send Money icon is visible.", Status_CRAFT.DONE);
            click(getObject("SendMoney.iconSendMoney"));
            waitForJQueryLoad();waitForPageLoaded();
            //     Thread.sleep(4000);
        } else {
            report.updateTestLog("SendMoneyFormHappyPath", " Send Money icon is not visible..!!", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("SendMoney.lstSelectAccount"), 5)) {
            clickJS(getObject("SendMoney.lstSelectAccount"), "Click on Select Account from which money to send.");
            waitForJQueryLoad();waitForPageLoaded();
            driver.findElement(By.xpath("//li[contains(text(),'" + PayFromAccount + "')]")).click();
        }
        waitforInVisibilityOfElementLocated(getObject("SendMoney.availableBalText"));

        //verify section header
        if (isElementDisplayed(getObject("xpath~//h3[text()='Pay to']"), 5)) {
            report.updateTestLog("selectPayToSendMoney", "Section header'Pay to' is displayed on Send Money landing Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectPayToSendMoney", "Section header'Pay to' is NOT displayed on Send Money landing Page", Status_CRAFT.FAIL);
        }

        //Select Payee
        if (isElementDisplayed(getObject(deviceType() + "SendMoney.lblSelectPayee"), 5)) {
            report.updateTestLog("selectPayToSendMoney", "Label'Select Payee' is displayed on Send Money landing Page", Status_CRAFT.PASS);
            if (deviceType().equals("mw.")) {
                clickJS(getObject(deviceType() + "SendMoney.lblSelectPayee"), "Pay To Account Dropdown ");
                report.updateTestLog("SelectPayeeMenu", "'Select Payee' is displayed on Send Money landing Page", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("selectPayToSendMoney", "Label header'Pay to' is NOT displayed on Send Money landing Page", Status_CRAFT.FAIL);
        }
        if (!deviceType.equalsIgnoreCase("Web")) {
            if (!dataTable.getData("General_Data", "PayTo_Account").equals("")) {
                waitForElementPresent(By.xpath("//*[(text()='" + PayToAccount + "')]"), 10);
                WebElement elm = driver.findElement(By.xpath("//*[(text()='" + PayToAccount + "')]"));
                String curr = elm.getText();
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//*[contains(text(),'" + PayToAccount + "')]")));
                js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[contains(text(),'" + PayToAccount + "')]")));
                Thread.sleep(3000);
                report.updateTestLog("selectPayToSendMoney", "Account/Bill Selected '" + PayToAccount + "'", Status_CRAFT.DONE);
                waitForPageLoaded();
            }
            if (isElementDisplayed(getObject(deviceType() + "billPayeeWarning"), 5)) {
                String expectedMessage = getText(getObject(deviceType() + "billPayeeWarning"));
                String actualMessage = "Warning: If you pay a lump sum to a fixed rate mortgage, a funding fee may be added to your mortgage balance. Before paying, please contact us on 01 611 3333 to understand what fees may be applicable.";
                if (expectedMessage.equalsIgnoreCase(actualMessage)) {
                    report.updateTestLog("VerifyWarningMessage", "Warning Message is displayed successfully", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("VerifyWarningMessage", "Warning Message is not displayed successfully", Status_CRAFT.FAIL);
                }
            }
            if (isElementDisplayed(getObject("SendMoney.btnToday"), 5) && isElementDisplayed(getObject("SendMoney.txtReference"), 5) && isElementDisplayed(getObject("SendMoney.txtMsgToPayee"), 5)) {
                report.updateTestLog("SendMoneyFormHappyPath", "Payment Type Today and Ref Text Or Message and Description is displayed successfully.", Status_CRAFT.PASS);
            }

            // Enter invalid amount and verify the error message
            if (ErrorValidationField.equalsIgnoreCase("AmountErrorValidation")) {
                // Verify Invalid Amount
                String strAmount = dataTable.getData("General_Data", "DepositAmount");
                if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
                    clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send");
                    sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmount);
                } else {
                    report.updateTestLog("SendMoneyFormHappyPath", "Amount field or amount filed place holder is not displayed correctly.", Status_CRAFT.FAIL);
                }

                ScrollToVisibleJS(("SendMoney.btnContinue"));
                report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if (isElementDisplayed(getObject("SendMoney.btnContinue"), 10)) {
                    clickJS(getObject("SendMoney.btnContinue"), "Continue");
                }

                // Verify invalid amount i.e. 123456789
                String InvalidDigitExpError = "Sorry, you can't enter that as the amount.";
                if (isElementDisplayed(getObject(deviceType() + "SendMoney.lblInvalidAmountError"), 5)) {
                    String InvalidDigitActualError = getText(getObject(deviceType() + "SendMoney.lblInvalidAmountError"));
                    if (InvalidDigitExpError.equalsIgnoreCase(InvalidDigitActualError)) {
                        report.updateTestLog("ErrorMessage", "Error '" + InvalidDigitExpError + "' is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("ErrorMessage", "Error '" + InvalidDigitExpError + "' is not displayed", Status_CRAFT.FAIL);
                    }
                }
                // Verify Amount Greater than available fund
                String strAmountMoreThanAvailable = dataTable.getData("General_Data", "NewAmount");
                if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
                    clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send");
                    sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmountMoreThanAvailable);
                } else {
                    report.updateTestLog("SendMoneyFormHappyPath", "Amount field or amount field place holder is not displayed correctly.", Status_CRAFT.FAIL);
                }

                ScrollToVisibleJS(("SendMoney.btnContinue"));
                report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if (isElementDisplayed(getObject("SendMoney.btnContinue"), 10)) {
                    clickJS(getObject("SendMoney.btnContinue"), "Continue");
                }

                String MoreThanAvailableFundError = "The amount that you wish to transfer is greater than your available funds. Please try again with a lesser amount.";
                if (isElementDisplayed(getObject(deviceType() + "SendMoney.lblMoreThanAvailableFundError"), 5)) {
                    String InvalidDigitActualError = getText(getObject(deviceType() + "SendMoney.lblMoreThanAvailableFundError"));
                    if (MoreThanAvailableFundError.equalsIgnoreCase(InvalidDigitActualError)) {
                        report.updateTestLog("ErrorMessage", "Error '" + MoreThanAvailableFundError + "' is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("ErrorMessage", "Error '" + MoreThanAvailableFundError + "' is not displayed", Status_CRAFT.FAIL);
                    }
                }

                // Verify Amount Greater than available limit of payee.
                String strAmountLimitExceed = dataTable.getData("General_Data", "Pay_Amount");
                if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
                    clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send");
                    sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmountLimitExceed);
                } else {
                    report.updateTestLog("SendMoneyFormHappyPath", "Amount field or amount field place holder is not displayed correctly.", Status_CRAFT.FAIL);
                }


                ScrollToVisibleJS(("SendMoney.btnContinue"));
                report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if (isElementDisplayed(getObject("SendMoney.btnContinue"), 10)) {
                    clickJS(getObject("SendMoney.btnContinue"), "Continue");
                }

                String amountExceedError = "Sorry, payments to this payee cannot exceed £20,000.";
                if (isElementDisplayed(getObject(deviceType() + "SendMoney.lblExceedAmountError"), 5)) {
                    String InvalidDigitActualError = getText(getObject(deviceType() + "SendMoney.lblExceedAmountError"));
                    if (amountExceedError.equalsIgnoreCase(InvalidDigitActualError)) {
                        report.updateTestLog("ErrorMessage", "Error '" + amountExceedError + "' is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("ErrorMessage", "Error '" + amountExceedError + "' is not displayed", Status_CRAFT.FAIL);
                    }
                }
            }

            // Enter valid amount in amount field
            if (isElementDisplayed(getObject("SendMoney.txtbxAmountPlaceholder"), 5)) {
                clickJS(getObject("SendMoney.txtbxAmountField"), "Enter The amount to send");
                sendKeysJS(getObject("SendMoney.txtbxAmountField"), strAmountToSend);
            } else {
                report.updateTestLog("SendMoneyFormHappyPath", "Amount field or amount field place holder is not displayed correctly.", Status_CRAFT.FAIL);
            }

            ScrollToVisibleJS(("SendMoney.btnContinue"));
            report.updateTestLog("SendMoneyFormHappyPath", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
            if (isElementDisplayed(getObject("SendMoney.btnContinue"), 10)) {
                clickJS(getObject("SendMoney.btnContinue"), "Continue");
            }
        }
    }

    /**
     * Function : To click continue button
     * Created by : C966003
     * Update on    Updated by     Reason
     * 26/06/2019   C966003        Newly created
     */
    public void continue_SendMoney() throws Exception {
        clickJS(getObject("SendMoney.btnContinue"), "Continue");
        waitForPageLoaded();
    }

    /**
     * Function : To validate success page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 06/08/2019   C966003        Newly created
     */
    public void success_SendMoney() throws Exception {
        //PayeeAddConfirmation("SendMoney.parentConfirmation");
        String dataSectionUI = getText(getObject("SendMoney.parentConfirmation"));
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 6)) {
            waitForPageLoaded();
        }
        isElementDisplayedWithLog(getObject(deviceType() + "Payments.lblHeaderSendMoney"),
                "Page header : " + getText(getObject(deviceType() + "Payments.lblHeaderSendMoney")), "Success", 3);
        isElementDisplayedWithLog(getObject("Payments.lblSuccess"),
                "Page title : " + getText(getObject("Payments.lblSuccess")), "Success", 3);

        if(!scriptHelper.commonData.strPayFromAccount.equalsIgnoreCase("")) {
            isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'" + scriptHelper.commonData.strPayFromAccount.split("~")[1] + "')]"),
                    "From : " + getText(getObject("xpath~//*[contains(text(),'" + scriptHelper.commonData.strPayFromAccount.split("~")[1] + "')]")), "Success", 3);
            if (isElementDisplayed(getObject("xpath~//*[contains(@class,'gutter')][contains(text(),'" + scriptHelper.commonData.strAmount + "')]/.."), 3)) {
                isElementDisplayedWithLog(getObject("xpath~//*[contains(@class,'gutter')][contains(text(),'" + scriptHelper.commonData.strAmount + "')]/.."),
                        "Amount : " + getText(getObject("xpath~//*[contains(@class,'gutter')][contains(text(),'" + scriptHelper.commonData.strAmount + "')]/..")), "Success", 3);
            } else if (isElementDisplayed(getObject("xpath~//*[contains(@style,'inline-block')]//*[contains(text(),'" + scriptHelper.commonData.strAmount + "')]/.."), 3)) {
                isElementDisplayedWithLog(getObject("xpath~//*[contains(@style,'inline-block')]//*[contains(text(),'" + scriptHelper.commonData.strAmount + "')]/.."),
                        "Amount : " + getText(getObject("xpath~//*[contains(@class,'boi-loan')]//*[contains(text(),'" + scriptHelper.commonData.strAmount + "')]/..")), "Success", 3);
            }
            if (isElementDisplayed(getObject("xpath~//*[contains(text(),'DropDownDisplayName')]/following-sibling::*[contains(text(),'" + scriptHelper.commonData.strPayToAccount.split("~")[0].trim() + "')]"), 3)) {
                isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'DropDownDisplayName')]/following-sibling::*[contains(text(),'" + scriptHelper.commonData.strPayToAccount.split("~")[0].trim() + "')]"),
                        "Pay to : " + getText(getObject("xpath~//*[contains(text(),'DropDownDisplayName')]/following-sibling::*[contains(text(),'" + scriptHelper.commonData.strPayToAccount.split("~")[0].trim() + "')]")), "Success", 3);
            } else if (isElementDisplayed(getObject("xpath~//div[not(contains(@style,'display: none;'))][contains(@class,'page')]//*[contains(text(),'To')]/following-sibling::*[contains(text(),'" + scriptHelper.commonData.strPayToAccount.split("~")[0].trim() + "')]"), 3)) {
                isElementDisplayedWithLog(getObject("xpath~//div[not(contains(@style,'display: none;'))][contains(@class,'page')]//*[contains(text(),'To')]/following-sibling::*[contains(text(),'" + scriptHelper.commonData.strPayToAccount.split("~")[0].trim() + "')]"),
                        "Pay to : " + getText(getObject("xpath~//div[not(contains(@style,'display: none;'))][contains(@class,'page')]//*[contains(text(),'To')]/following-sibling::*[contains(text(),'" + scriptHelper.commonData.strPayToAccount.split("~")[0].trim() + "')]")), "Success", 3);
            }
        }

        isElementDisplayedWithLog(getObject("SendMoney.btnBacktoHome"),
                "Back to home","Success",3);
        isElementDisplayedWithLog(getObject("SendMoney.btnBackToPayment"),
                "Back to payments","Success",3);

        if (scriptHelper.commonData.strProssTran.equalsIgnoreCase("Schedule future date")) {
            isElementDisplayedWithLog(getObject("TBA.lblReviewScheduleFor"),
                    getText(getObject("TBA.lblReviewScheduleFor")), "Success", 4);
            if (getText(getObject("TBA.lblReviewScheduleFor")).contains(scriptHelper.commonData.strTransferDate.replace("/", "\n"))) {
                report.updateTestLog("success_SendMoney", getText(getObject("TBA.lblReviewScheduleFor")) + " is displayed same as given on success page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("success_SendMoney", "Schedule for date is not same as given on success page", Status_CRAFT.FAIL);
            }
            isElementDisplayedWithLog(getObject("TBA.lblReviewPageMsg"),
                    getText(getObject("TBA.lblReviewPageMsg")), "Success", 4);
        }

        isElementDisplayedWithLog(getObject("TBA.lblReviewTimeOfRequest"),
                getText(getObject("TBA.lblReviewTimeOfRequest")), "Success", 4);
    }

    /**
     * Function : To validate success page
     * Created by : C103401
     * Update on    Updated by     Reason
     * 06/08/2019   C103401        Newly created
     */
    public void SendMoney_Confirmation() throws Exception {
        String dataSectionUI = getText(getObject("SendMoney.parentConfirmation"));
        isElementDisplayedWithLog(getObject(deviceType() + "Payments.lblHeaderSendMoney"),
                "Page header : " + getText(getObject(deviceType() + "Payments.lblHeaderSendMoney")), "Success", 5);
        isElementDisplayedWithLog(getObject("Payments.lblSuccess"),
                "Page title : " + getText(getObject("Payments.lblSuccess")), "Success", 5);

        if(isElementDisplayed(getObject("xpath~//*[contains(@class,'gutter')][contains(text(),'" + scriptHelper.commonData.strAmount + "')]/.."),5)){
            isElementDisplayedWithLog(getObject("xpath~//*[contains(@class,'gutter')][contains(text(),'" + scriptHelper.commonData.strAmount + "')]/.."),
                    "Amount : " + getText(getObject("xpath~//*[contains(@class,'gutter')][contains(text(),'" + scriptHelper.commonData.strAmount + "')]/..")), "Success", 3);
        } else if (isElementDisplayed(getObject("xpath~//*[contains(@style,'inline-block')]//*[contains(text(),'" + scriptHelper.commonData.strAmount + "')]/.."),3)){
            isElementDisplayedWithLog(getObject("xpath~//*[contains(@style,'inline-block')]//*[contains(text(),'" + scriptHelper.commonData.strAmount + "')]/.."),
                    "Amount : " + getText(getObject("xpath~//*[contains(@class,'boi-loan')]//*[contains(text(),'" + scriptHelper.commonData.strAmount + "')]/..")), "Success", 3);
        }

        isElementDisplayedWithLog(getObject("SendMoney.btnBacktoHome"),
                "Back to home","Success",3);
        isElementDisplayedWithLog(getObject("SendMoney.btnBackToPayment"),
                "Back to payments","Success",3);
    }

    /**
     * Function : To click on back to home button and navigate to home
     * Created by : C966003
     * Update on    Updated by     Reason
     * 16/10/2019   C966003        Newly created
     */
    public void clickBackToHomeButton() throws Exception{
        scrollToView(getObject("SendMoney.btnBacktoHome"));
        clickJS(getObject("SendMoney.btnBacktoHome"),"Back to home");
        waitForPageLoaded();
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 10)) {
            waitForPageLoaded();
        }
        isElementDisplayedWithLog(getObject(deviceType()+ "home.lblWelcome"),
                "Welcome card","Homepage",4);
    }

    /**
     * Function : To validate review page details under Send Money
     * Created by : C966003
     * Update on    Updated by     Reason
     * 26/06/2019   C966003        Newly created
     */
    public void reviewPage_SendMoney() throws Exception {
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            if(strFieldName.equalsIgnoreCase("Pay from")&& strFieldvalue.contains("~")){
                strFieldvalue = strFieldvalue.split("~")[1];

            }
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {
            if (getText(getObject("xpath~//*[contains(text(),'" + obj.getKey() + "')]/../../following-sibling::*")).contains(obj.getValue().toString())) {
                report.updateTestLog("verifySendMoneyReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifySendMoneyReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
            }
        }
        if (scriptHelper.commonData.strProssTran.equalsIgnoreCase("Schedule future date")) {
            isElementDisplayedWithLog(getObject("Payments.lblProcessPayment"),
                    "Process payment : " + getText(getObject("Payments.lblProcessPayment")), "Review", 3);
            isElementDisplayedWithLog(getObject("TBA.lblReviewProcessing"),
                    "Blue label : " + getText(getObject("TBA.lblReviewProcessing")), "Review", 4);
            isElementDisplayedWithLog(getObject("TBA.lblReviewProcessMessge"),
                    "Processing message : " + getText(getObject("TBA.lblReviewProcessMessge")), "Review", 4);
            isElementDisplayedWithLog(getObject("Payments.txtDailyLimits"),
                    getText(getObject("Payments.txtDailyLimits")), "Review", 4);
            isElementDisplayedWithLog(getObject("payments.txtDailyLimitsMsg"),
                    "Daily limits message : " + getText(getObject("payments.txtDailyLimitsMsg")), "Review", 4);
        }

    }


    public void SendMoneyUKDomesticAmountError() throws Exception {
        String strErrorMessageInputValue[] = dataTable.getData("General_Data", "VerifyDetails").split("::");
        String strObjectToClick = dataTable.getData("General_Data", "AccountPosition_Grouped");
        click(getObject(strObjectToClick));
        Actions action = new Actions((WebDriver) driver.getWebDriver());
        //switch ( strOperation ) {

        //case "AddMobileTextBox":

        for (int i = 0; i < strErrorMessageInputValue.length; i++) {
            String strErrorMessageCheck[] = strErrorMessageInputValue[i].split(";");
            String strInputValue = strErrorMessageCheck[0];
            String strErrorMessage = strErrorMessageCheck[1];
            if (isElementDisplayed(getObject("profile.inputboxProfile"), 20)) {
                waitForElementPresent(getObject("profile.inputboxProfile"), 5);
                click(getObject("profile.inputboxProfile"));
                sendKeys(getObject("profile.inputboxProfile"), strInputValue);
                waitForElementPresent(getObject("AddPolicy.Continue"), 5);
                click(getObject("AddPolicy.Continue"));
                Thread.sleep(4000);
            }

            if (isElementDisplayed(getObject("xpath~//*[contains(@class,'qlrError boi_input_sm_error tc-error-position tc-fs-m2  ')]"), 5)) {
                String strText = getText(getObject("xpath~//*[contains(@class,'qlrError boi_input_sm_error tc-error-position tc-fs-m2  ')]"));
                if (strText.equals(strErrorMessage)) {
                    sendKeys(getObject("profile.inputboxProfile"), "");
                    Thread.sleep(4000);
                    report.updateTestLog("validateMobilePhoneEmailErrors", " Input box :: Error Message :: '" + strErrorMessage + "':: Input Value :: " + strInputValue, Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("validateMobilePhoneEmailErrors", " Input box :: Error Message :: '" + strErrorMessage + "':: is NOT displayed Successfully...for Input Value :: " + strInputValue, Status_CRAFT.FAIL);
                }
            }
            if (isElementDisplayed(getObject("xpath~//*[contains(@class,'boi_input_sm_error tc-error-position tc-fs-m2  ')]"), 5)) {
                String strText = getText(getObject("xpath~//*[contains(@class,'boi_input_sm_error tc-error-position tc-fs-m2  ')]"));
                if (strText.equals(strErrorMessage)) {
                    sendKeys(getObject("profile.inputboxProfile"), "");
                    Thread.sleep(4000);
                    report.updateTestLog("validateMobilePhoneEmailErrors", " Input box :: Error Message :: '" + strErrorMessage + "':: Input Value :: " + strInputValue, Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("validateMobilePhoneEmailErrors", " Input box :: Error Message :: '" + strErrorMessage + "':: is NOT displayed Successfully...for Input Value :: " + strInputValue, Status_CRAFT.FAIL);
                }
            }

        }
    }

    public void clickgoback() throws Exception {
        Thread.sleep(2000);
        if (isElementDisplayed(getObject("home.Goback"), 20)) {
            report.updateTestLog("gobacknavigation", "Go Back link present on Screen", Status_CRAFT.PASS);
            clickJS(getObject("home.Goback"), "Go back");
        } else {
            report.updateTestLog("gobacknavigation", "Go Back link is not present on Screen", Status_CRAFT.FAIL);
        }

    }

    public void amountFieldValidation_Inline() throws Exception {
        if (isElementDisplayedWithLog(getObject("SendMoney.btnContinue"), "Continue button", "Send Money Page", 5)) {
            clickJS(getObject("SendMoney.btnContinue"), "clicked Continue");
        }
        String expAmountError = "Please enter an amount.";
        String UIAmountError = getText(By.xpath("//span[contains(@class,'boi_input_sm_error boi-error-color boi-error-position boi-fs-m2')]"));
        if (expAmountError.equals(UIAmountError)) {
            report.updateTestLog("SendMoneyErrorMsg", "Amount field Error message : " + UIAmountError + " is displayed as expected", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SendMoneyErrorMsg", "Amount field Error message : " + UIAmountError + " is not as expected ", Status_CRAFT.FAIL);
        }

        String strError = dataTable.getData("General_Data", "ErrorText");
        String strAmount = dataTable.getData("General_Data", "NewAmount");
        Thread.sleep(5000);
        if (strError.contains("::")) {
            String[] expError = strError.split("::");
            String[] amount = strAmount.split("::");
            for (int i = 0; i < expError.length; i++) {
                scrollToView(getObject("SendMoney.lblAmount"));
                sendKeys(getObject("SendMoney.lblAmount"), amount[i]);
                clickJS(getObject("SendMoney.btnContinue"), "Continue");
                String AmountError = getText(By.xpath("//span[contains(@class,'boi_input_sm_error boi-error-color boi-error-position boi-fs-m2')][@style='visibility: visible; display: inline;']"));
                if (expError[i].equalsIgnoreCase(AmountError)) {
                    report.updateTestLog("validateErrors", "Amount enterted is " + amount[i] + "Error message displayed is as expected. Expected:: '" + expError[i] + "', Actual::'" + AmountError + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("validateErrors", "Amount enterted is " + amount[i] + "Error message displayed is not as expected. Expected::'" + expError[i] + "', Actual::'" + AmountError + "'", Status_CRAFT.FAIL);
                }
            }
        }
    }

    public void amountFieldValidation_Mainframe() throws Exception {

        String strError = dataTable.getData("General_Data", "VerifyDetails");
        String strAmount = dataTable.getData("General_Data", "DepositAmount");
        if (strError.contains("::")) {
            String[] expError = strError.split("::");
            String[] amount = strAmount.split("::");
            for (int i = 0; i < expError.length; i++) {
                scrollToView(getObject("SendMoney.lblAmount"));
                sendKeys(getObject("SendMoney.lblAmount"), amount[i]);
                clickJS(getObject("SendMoney.btnContinue"), "Continue");
                waitForJQueryLoad();waitForPageLoaded();

                String AmountError = getText(By.xpath("//span[contains(@class,'boi_input')][contains(text(),'" + expError[i] + "')]"));
                if (AmountError.replaceAll("  ", " ").contains(expError[i].replaceAll("  ", " "))) {
                    // String str = "The amount that you wish to transfer is greater than your available funds.  Please try again with a lesser amount.".replace('','');
                    report.updateTestLog("validateErrors", "Amount enterted is " + amount[i] + "Error message displayed is as expected. Expected:: '" + expError[i] + "', Actual::'" + AmountError + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("validateErrors", "Amount enterted is " + amount[i] + "Error message displayed is not as expected. Expected::'" + expError[i] + "', Actual::'" + AmountError + "'", Status_CRAFT.FAIL);
                }
            }
        }
    }

    public void verifyCurrencyandReference() throws Exception {
        if (isElementDisplayed(getObject("Payments.lblAmount"), 5)) {
            String strCurrSymbol = dataTable.getData("General_Data", "Currency_Symbol");
            report.updateTestLog("PayFromAccountSelection", "Label 'Amount' is displayed on Send Money Page", Status_CRAFT.PASS);
            //Validate Currency Symbol
            if (getText(getObject("Payments.lblCurrencySymbol")).equals(strCurrSymbol)) {
                report.updateTestLog("PayFromAccountSelection", "Currency Symbol label is correct '" + strCurrSymbol + "' in Amount Field on Send Money Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("PayFromAccountSelection", "Currency Symbol label is NOT correct '" + strCurrSymbol + "' in Amount Field on Send Money Page, UI label value '" + getText(getObject("xpath~//label[text()='Amount ']/span[@class='boi_input_sm ']")) + "'", Status_CRAFT.FAIL);
            }
        }

        if (isElementDisplayed(getObject("AddBillPayee.lblReference"), 5)) {
            report.updateTestLog("SendMoneySEPA", "Reference Label is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("SendMoney.lblVisibletoPayee"), 5)) {
                report.updateTestLog("SendMoneySEPA", "Visible to Payee Label is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("SendMoneySEPA", "Visible to Payee label is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("SendMoneySEPA", "Reference label is not displayed", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To validate EURO vs GBP accounts under manage payees
     * Created by : C966003
     * Update on    Updated by     Reason
     * 27/06/2019   C966003        Newly created
     */
    public void validateAccountsHomeVsPayFrom() throws Exception {
        isElementDisplayedWithLog(getObject("xpath~//button[contains(text(),'Select account')]"),
                "Pay From", "Send Money", 4);
        clickJS(getObject("xpath~//button[contains(text(),'Select account')]"), "Select account");
        List<WebElement> lstPayFrom = findElements(getObject("xpath~//*[@role='option']"));
        for (int i = 0; i < scriptHelper.commonData.accountList.size(); i++) {
            if (scriptHelper.commonData.accountList.get(i).contains("€")) {
                report.updateTestLog("validateAccountsHomeVsPayFrom", scriptHelper.commonData.accountList.get(i) +
                        " Euro account present on homepage", Status_CRAFT.PASS);
                for (WebElement payFrmAccnt : lstPayFrom) {
                    String strAccountDetails = payFrmAccnt.getText();
                    if (scriptHelper.commonData.accountList.get(i).contains(strAccountDetails)) {
                        report.updateTestLog("validateAccountsHomeVsPayFrom", "Euro account present for GBP Payee in PayFrom dropdown", Status_CRAFT.FAIL);
                        break;
                    } else {
                        report.updateTestLog("validateAccountsHomeVsPayFrom", strAccountDetails +
                                " GBP account present on PayFrom screen", Status_CRAFT.PASS);
                    }
                }
            }
        }
    }

    /**
     * Function : Validate the Page Errors
     * Update on    Updated by     Reason
     * 20/06/2019   C103401
     */
    public void validateAndVerifySOErrors() throws Exception {
        clickJS(getObject("ManageStandingOrder.btnContinue"), "Clicked on 'Continue', Standing Order Page");
        String strError = dataTable.getData("General_Data", "ErrorText");
        Thread.sleep(5000);
        if (strError.contains("::")) {
            String[] arrError = strError.split("::");
            for (int i = 0; i < arrError.length; i++) {
                if (isElementDisplayed(getObject("xpath~//*[contains(@class,'boi_input_sm_error')][contains(text(),'" + arrError[i] + "')]"), 5)) {
                    report.updateTestLog("verifyErrorText", "Error Text '" + arrError[i] + "' is present on UI", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorText", "Error Text '" + arrError[i] + "' NOT is present on UI", Status_CRAFT.FAIL);
                }
            }
        } else {
            if (isElementDisplayed(getObject("xpath~//*[contains(@class,'boi_input_sm_error')][contains(text(),'" + strError + "')]"), 5)) {
                report.updateTestLog("verifyErrorText", "Error Text '" + strError + "' is present on UI", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorText", "Error Text '" + strError + "' NOT is present on UI", Status_CRAFT.FAIL);
            }
        }
    }


    /**
     * Function : To check Present message if the user only has accounts blocked for payment
     * Created by : C966003
     * Update on    Updated by     Reason
     * 28/06/2019   C966003        Newly created
     */
    public void txtBlockedpaymentaccounts() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String strPageHeader = dataTable.getData("General_Data", "PageHeader");
        if (!deviceType.equals("MobileWeb")) {
            isElementDisplayedWithLog(getObject("xpath~//h1[text()='" + strPageHeader + "']"),
                    "Header : " + strPageHeader, strPageHeader, 5);
        } else {
            isElementDisplayedWithLog(getObject("xpath~//h3[text()='" + strPageHeader + "']"),
                    "Header : " + strPageHeader, strPageHeader, 5);
        }
        homePage.verifyElementDetails("SendMoney.pageView");
    }

    /**
     * Function : To click Add Payee button for Manage payee
     * Created by : C966003
     * Update on    Updated by     Reason
     * 28/06/2019   C966003        Newly created
     */
    public void addPayee_ManagePayee() throws Exception {
        waitForPageLoaded();
        clickJS(getObject(deviceType() + "Payments.btnAddPayees"), "Add Payee");
    }

    /**
     * Function : To verify country field displayed below IBAN
     * Created by : C966003
     * Update on    Updated by     Reason
     * 01/07/2019   C966003        Newly created
     */
    public void payeeCountryPosition_ManagePayee() throws Exception {
        waitForPageLoaded();
        boolean flag1 = false;
        for (int i = 0; i <= 12; i++) {
            String strElementText = getText(getObject("xpath~(//*[contains(@class,'tc-row-part tc-row-flex')])[" + i + "]"));
            if (flag1 == true) {
                if (strElementText.contains("Payee country") || strElementText.contains("")) {
                    if (strElementText.contains("Payee country")) {
                        report.updateTestLog("payeeCountryPosition_ManagePayee",
                                "'Payee country' field displayed below 'IBAN' ", Status_CRAFT.PASS);
                    }
                } else {
                    report.updateTestLog("payeeCountryPosition_ManagePayee",
                            strElementText + " field displayed below 'IBAN' ", Status_CRAFT.FAIL);
                }
            }
            if (strElementText.contains("IBAN")) {
                flag1 = true;
            }
        }

    }

    public void addPayeeFormToolTip() throws Exception {
        String[] arrError = dataTable.getData("General_Data", "Tooltip1").split("::");

        for (int i = 0; i < arrError.length; i++) {
            String strLabel = arrError[i].split("||")[0];
            String strToolTip = arrError[i].split("||")[1];
            isElementclickable(getObject("xpath~//label[contains(text(),'" + strLabel + "')]/following-sibling::span[@class='boi-tooltip boi-top-3 fa fa-info-circle']"), 5);
            clickJS(getObject("xpath~//label[contains(text(),'" + strLabel + "')]/following-sibling::span[@class='boi-tooltip boi-top-3 fa fa-info-circle']"), "ToolTip Icon");
            Thread.sleep(1000);
            if (isElementDisplayed(getObject("xpath~//span[contains(@class,'boi-tooltip__content')][contains(text(),'" + strToolTip + "')]"), 10)) {
                report.updateTestLog("verifyErrorText", "Error Text '" + arrError[i] + "' is present on UI", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorText", "Error Text '" + arrError[i] + "' NOT is present on UI", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : To check rate exchange functionality for send money
     * Created by : C966003
     * Update on    Updated by     Reason
     * 03/07/2019   C966003        Newly created
     */

    public void viewExchangeRate_SendMoney() throws Exception {
        String strAmount = dataTable.getData("General_Data", "Current_Balance");
        String strXpath = "xpath~//span[contains(@class,'boi_input')][contains(text(),'Please enter an amount greater than €0.00')]";
        isElementDisplayedWithLog(getObject("SendMoney.lnkViewExchangeRate"),
                "View exchange rate", "Send Money", 4);
        clickJS(getObject("SendMoney.lnkViewExchangeRate"), "View exchange rate");
        waitForPageLoaded();
        String errBlankAmount = getText(getObject(strXpath));
        isElementDisplayedWithLog(getObject(strXpath),
                "Exchange rate error msg for blank amount : " + errBlankAmount, "Send Money", 4);
        sendKeys(getObject("sendMoney.edtAmount"), strAmount, "Amount");
        waitForJQueryLoad();waitForPageLoaded();
        clickJS(getObject("SendMoney.lnkViewExchangeRate"), "View exchange rate");
        waitForJQueryLoad();waitForPageLoaded();
        String strExchabgeRate = getText(getObject("SendMoney.lblExchangeRate"));
        String strTotalAmount = getText(getObject("SendMoney.lblExchangeAmount"));
        isElementDisplayedWithLog(getObject("SendMoney.lblExchangeRate"),
                "Exchange rate : " + strExchabgeRate, "Send Money", 4);
        isElementDisplayedWithLog(getObject("SendMoney.lblExchangeAmount"),
                "Total amount in GBP : " + strTotalAmount, "Send Money", 4);
    }

    /**
     * Function : To delete payee
     * Created by : C966003
     * Update on    Updated by     Reason
     * 08/11/2019   C966003        added flag and changed xpath for Delete payee 1 and 3
     */
    public void deletePayee() throws Exception {
        waitForPageLoaded();
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
            report.updateTestLog("deletePayee", "Screen view", Status_CRAFT.SCREENSHOT);
        }
        if(!scriptHelper.commonData.confirmationFlag==false) {
            clickJS(getObject(deviceType() + "ManagePayee.btnDeletePayee1"), "Delete payee");
            isElementDisplayedWithLog(getObject("ManagePayee.btnDeletePayee3"),
                    "Delete payee", "Delete payee", 5);
            clickJS(getObject( "ManagePayee.btnDeletePayee3"), "Delete payee");
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
                report.updateTestLog("deletePayee", "Screen view", Status_CRAFT.SCREENSHOT);
            }
        }
    }

    /**
     * Function : Go Back Arrow Click
     * Update on    Updated by     Reason
     * 10/07/2019   C966119        Newly created
     */
    public void verifyAndClickGoBackArrowMobile() throws Exception {
        if (isElementDisplayed(getObject("mw.Payments.btnGoback"), 10)) {
            clickJS(getObject("mw.Payments.btnGoback"), "Clicked on Back Arrow Button");
            Thread.sleep(2000);
            waitForPageLoaded();
        } else {
            report.updateTestLog("VerifyAndClickGoBackArrowMobile", "GO Back Arrow NOT is present on Mobile Header", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To validate errors on pin page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 04/07/2019   C966003        Newly created
     */
    public void capturePinPageError() throws Exception {

        String[] arrPin = dataTable.getData("General_Data", "Invalid_PIN").split("");
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("AddPayee.PINFields"));
        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
            }
        }
        report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.PASS);
        driver.context("NATIVE_APP");
        MobileElement el1 = (MobileElement) driver.findElementByXPath("//android.view.View[3]/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.Button");
        (new TouchAction(driver.getAppiumDriver())).tap(PointOption.point(el1.getCenter().getX(),el1.getCenter().getY())).perform();
    }

    /*
    Function: To verify if the payee is active or not, if the Pay button is enabled then the payee will be active
     */
    public void paybtnenabledmobile() throws Exception {
        if (isElementDisplayed(getObject("Payments.btnPayManagePayee"), 5)) {
            report.updateTestLog("paybtnenabledmobile", "Pay button is displayed and enabled on  mobile device that means payee is active ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("paybtnenabledmobile", "Pay button is not displayed or disabled on  mobile device that means payee is inactive ", Status_CRAFT.FAIL);
        }
    }

    public void selectaccountpayfrom() throws Exception {
        if (!dataTable.getData("General_Data", "PayFrom_Account").equals("")) {
            String payfromacc = dataTable.getData("General_Data", "PayFrom_Account");
            clickJS(getObject(deviceType() + "AddBillPayee.lstPayFromAccount"), "Pay From Account Dropdpown");
            clickJS(getObject("xpath~//ul/li[contains(.,'" + payfromacc + "')]"), "Account '" + payfromacc + "'");
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
            clickJS(getObject("AddPayee.Continue"), "Continue button");
        }

    }

    public void validateAndVerifyErrors() throws Exception {
        List<WebElement> objErr = driver.findElements(getObject("xpath~//*[contains(@class,'error-position')]"));
        for (int i = 0; i < objErr.size(); i++) {
            String stErr = objErr.get(i).getText();
            scrollToView(getObject("xpath~//*[contains(@class,'error-position')][contains(text(),'" + stErr + "')]"));
            report.updateTestLog("errorMessageValidations", stErr + " :: Error Message displayed Correctly ", Status_CRAFT.PASS);
        }
    }

    public void enterinvalidPin() throws InterruptedException {
        String[] arrPin = dataTable.getData("General_Data", "Invalid_PIN_Chars").split("");
        String strObject = "";
        waitForJQueryLoad();
        //To handle the SCA pin page
        if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"), 5)) {
            strObject = "launch.edtPinEnterFieldGrpSCA";
            report.updateTestLog("VerifyHomePage", ":: Step 1 of 3 :: SCA - Uplift ::", Status_CRAFT.DONE);
        } else if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrp"), 2)) {
            strObject = "launch.edtPinEnterFieldGrp";
        } else if (isElementDisplayed(getObject("login.pinDigits"), 2)) {
            strObject = "login.pinDigits";
        } else {
            report.updateTestLog("VerifyHomePage", "Pin Group Not found", Status_CRAFT.FAIL);
        }

        List<WebElement> lstPinEnterFieldGrp = findElements(getObject(strObject));
        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
            }
        }

        report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.DONE);

        if(isMobile){
            try{
                driver.context("NATIVE_APP");
                MobileElement el1 = (MobileElement) driver.findElementByXPath("//android.view.View[3]/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.Button");
                (new TouchAction(driver.getAppiumDriver())).tap(PointOption.point(el1.getCenter().getX(),el1.getCenter().getY())).perform();
                report.updateTestLog("EnterInvalidPin", "Push Notification Accepted",Status_CRAFT.PASS);
            }catch(Exception e){
                System.out.print("Exception is there ::"+e.getMessage());
            }
            Thread.sleep(1000);
        }else{
            //To handle the SCA pin page
            if (isElementDisplayed(getObject("launch.btnConfirm"), 2)) {
                clickJS(getObject("launch.btnConfirm"), "Clicked on Confirm button");
            } else if (isElementDisplayed(getObject("launch.btnLogIn"), 2)) {
                clickJS(getObject("launch.btnLogIn"), "Clicked on Login button");
            } else if (isElementDisplayed(getObject("launch.btnContinue"), 2)) {
                clickJS(getObject("launch.btnContinue"), "Clicked on Continue button");
            }
        }

    }

    public void verifyProfileIcon() throws Exception {
        if (isElementDisplayed(getObject("home.Goback"), 2)) {
            report.updateTestLog("VerifyAddPayee", "Go Back link is present on header which is not expected", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("VerifyAddPayee", "Go Back link is not present on header which is as expected", Status_CRAFT.PASS);
        }
        if (isElementDisplayed(getObject("ManagePayee.profileIcon"), 3)) {
            report.updateTestLog("VerifyAddPayee", "Profile Icon is present on header", Status_CRAFT.PASS);
            clickJS(getObject("ManagePayee.profileIcon"), "Profile Icon");
        } else {
            report.updateTestLog("VerifyAddPayee", "Profile Icon is not present on header", Status_CRAFT.FAIL);
        }
    }


    public void goBackButton() throws Exception {
        clickJS(getObject(deviceType() + "Payments.btnGoback"), "Clicked on GO BACK option");
    }

    /**
     * Function : To Perform different operation on popup
     * Update on    Updated by     Reason
     * 28/06/2019   C103401        Newly created
     */

    public void verifyAddBillPayeeOperationOnPopup() throws Exception {
        if (!dataTable.getData("General_Data", "Operation").equals("")) {
            String popupOperation = dataTable.getData("General_Data", "Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (popupOperation.toUpperCase()) {
                case "ADD PAYEE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(deviceType() + "SendMoney.btnAddBillPayee")));
                    click(getObject(deviceType() + "SendMoney.btnAddBillPayee"), "'Add Bill Payee'");
                    if (isElementDisplayed(getObject(deviceType() + "SendMoney.lblAddbill"), 7)) {
                        report.updateTestLog("verifyAddBillPayee", "Upon clicking 'Add Bill Payee' user successfully Navigated to Add Bill Page", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyAddBillPayee", "Upon clicking 'Add Bill Payee' user navigation unsuccessful to Add Bill Page", Status_CRAFT.FAIL);
                    }
                    break;
                case "CANCEL":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(deviceType() + "SendMoney.btnCancel")));
                    Thread.sleep(2000);
                    click(getObject(deviceType() + "SendMoney.btnCancel"), "'Cancel Button' on Add Payee Pop up");
                    if (isElementDisplayed(getObject(deviceType() + "Transactions.PayNow"), 5)) {
                        report.updateTestLog("verifyAddBillPayeeOperationOnPopup", "User is on Transactions page upon clicking 'Cancel Button'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyAddBillPayeeOperationOnPopup", "User is not on Transactions page upon clicking 'Cancel Button'", Status_CRAFT.FAIL);
                    }
                    break;
                case "CLOSE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(deviceType() + "SendMoney.btnClose")));
                    Thread.sleep(2000);
                    click(getObject(deviceType() + "SendMoney.btnClose"), "'Close Icon' on Add Payee Pop up");
                    if (isElementDisplayed(getObject(deviceType() + "Transactions.PayNow"), 5)) {
                        report.updateTestLog("verifyAddBillPayeeOperationOnPopup", "User is on Transactions page upon clicking 'Close Icon'", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyAddBillPayeeOperationOnPopup", "User is not on Transactions page upon clicking 'Close Icon'", Status_CRAFT.FAIL);
                    }
                    break;
                default:
                    report.updateTestLog("verifyAddBillPayeeOperationOnPopup", "Please provide the valid operation on Manage Payees Page, ADD PAYEE or CANCEL", Status_CRAFT.FAIL);
            }
        }
    }


    public void validateMainFrameErrors() throws Exception {
        String expectedErr = dataTable.getData("General_Data", "VerifyContent");
        String errXpath = "xpath~//*[contains(@class,'fa-exclamation')]//following-sibling::*[@class='boi_input']";
        //String errXpath ="xpath~//*[contains(@class,'fa-exclamation')]/following-sibling::*[contains(text(),'" + expectedErr + "')]";
        if (isElementDisplayed(getObject(errXpath), 5)) {
            String actualErr = getText(getObject(errXpath));
            if (expectedErr.trim().equals(actualErr.trim())) {
                report.updateTestLog("validateMainFrameErrors", "Mainframe error message displayed is as expected. Expected::'" + expectedErr + "', Actual::'" + actualErr + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validateMainFrameErrors", "Mainframe error message displayed is not as expected. Expected::'" + expectedErr + "', Actual::'" + actualErr + "'", Status_CRAFT.FAIL);
            }
        }else{
            report.updateTestLog("validateMainFrameErrors", "Mainframe error message is NOT displayed on the Top of the screen", Status_CRAFT.FAIL);
        }
    }

    public void validateProcessingAndDailylimits() throws Exception {

        if (isElementDisplayed(getObject("SendMoney.lblProcessing"), 5)) {
            report.updateTestLog("validateProcessingAndDailylimits", "Label 'Processing' is displayed on Review page", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("xpath~//div[contains(text(),'If you schedule a payment for a Saturday, Sunday or bank holiday, we will process it on the next working day.')]"), 10)) {
                report.updateTestLog("validateProcessingAndDailylimits", "Text 'If you schedule a payment for a Saturday, Sunday or bank holiday, we will process it on the next working day.' is displayed on Review page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validateProcessingAndDailylimits", "Text 'If you schedule a payment for a Saturday, Sunday or bank holiday, we will process it on the next working day.' is NOT displayed on Review page", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("validateProcessingAndDailylimits", "Label 'Processing' is NOT displayed on Review page", Status_CRAFT.FAIL);

        }

        if (isElementDisplayed(getObject("SendMoney.lblDailyLimits"), 5)) {
            report.updateTestLog("validateProcessingAndDailylimits", "Label 'Daily limits' is displayed on Review page", Status_CRAFT.PASS);
            if (dataTable.getData("General_Data", "Currency_Symbol").equalsIgnoreCase("€")) {
                if (isElementDisplayed(getObject("xpath~//div[contains(text(),'Your payments are limited to a total of €20,000 per working day.')]"), 10)) {
                    report.updateTestLog("validateProcessingAndDailylimits", "Text 'Your payments are limited to a total of €20,000 per working day. More about daily limits.' is displayed on Review page", Status_CRAFT.PASS);
                    scrollToView(getObject("xpath~//div[text()='Your payments are limited to a total of €20,000 per working day. ']/*[text()='More about daily limits.']"));
                } else {
                    report.updateTestLog("validateProcessingAndDailylimits", "Text 'Your payments are limited to a total of €20,000 per working day. More about daily limits.' is NOT displayed on Review page", Status_CRAFT.FAIL);
                }
            } else {
                if ((dataTable.getData("General_Data", "Currency_Symbol").equalsIgnoreCase("£"))) {
                    if (isElementDisplayed(getObject("xpath~//div[contains(text(),'Your payments are limited to a total of £20,000 per working day.')]"), 10)) {
                        report.updateTestLog("validateProcessingAndDailylimits", "Text 'Your payments are limited to a total of £20,000 per working day. More about daily limits.' is displayed on Review page", Status_CRAFT.PASS);
                        scrollToView(getObject("xpath~//div[text()='Your payments are limited to a total of £20,000 per working day. ']/*[text()='More about daily limits.']"));
                    } else {
                        report.updateTestLog("validateProcessingAndDailylimits", "Text 'Your payments are limited to a total of £20,000 per working day. More about daily limits.' is NOT displayed on Review page", Status_CRAFT.FAIL);
                    }
                }
            }
        }

    }


    public void validatePTMConfirmationPage() throws Exception {
        PayeeAddConfirmation("SendMoney.parentConfirmation");
    }


    public void verifyManagePayeePageNew() throws Exception {
        String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");

        //Click on Payments Tab
        clickJS(getObject(deviceType() + "home.tabPayments"), "Select 'Payments Tab'");
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }

        //Click on Manage Payees
        clickJS(getObject("Payments.ManagePayee"), "Manage Payees clicked");
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }

        //Validate Add Person and Add Bill
        String myobj = "";
        String AddBillTitle = "";
        String btnAddPayees = "";
        String AddpayeeTitle = "";

        if (isElementDisplayed(getObject(deviceType() + "Payments.btnAddBill"), 5)) {
            clickJS(getObject(deviceType() + "Payments.btnAddBill"), "Add Bill");
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
            report.updateTestLog("verifyManagePayeePageNew", "Page Title is Displayed for Add Bill Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyManagePayeePageNew", "Add Bill Button is not displayed on Screen", Status_CRAFT.FAIL);
        }
        clickJS(getObject(deviceType() + "Payments.btnGoback"), "Go back Add Bill");

        if (isElementDisplayed(getObject(deviceType() + "Payments.btnAddPayees"), 5)) {
            clickJS(getObject(deviceType() + "Payments.btnAddPayees"), "Add payee ");
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
            report.updateTestLog("verifyManagePayeePageNew", "Page Title is Displayed for Add Payees Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyManagePayeePageNew", "Add Payees Button is not displayed on Screen", Status_CRAFT.FAIL);
        }
        clickJS(getObject(deviceType() + "Payments.btnGoback"), "GO BACK");

        //Validate Columns of Manage Payee Page
        if (deviceType().equals("w.")) {
            if (isElementDisplayed(getObject("ManagePayee.lblName"), 5)) {
                report.updateTestLog("verifyManagePayeePageNew", "Payee name column header is Displayed for Manage Payees Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyManagePayeePageNew", "Payee name column header NOT Displayed for Manage Payees Page", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("ManagePayee.lblCountry"), 5)) {
                report.updateTestLog("verifyManagePayeePageNew", "Country column header is Displayed for Manage Payees Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyManagePayeePageNew", "Country column header NOT Displayed for Manage Payees Page", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("ManagePayee.lblCurrency"), 5)) {
                report.updateTestLog("verifyManagePayeePageNew", "Currency column header is Displayed for Manage Payees Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyManagePayeePageNew", "Currency column header NOT Displayed for Manage Payees Page", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * CFPUR- 6687: New design for Manage payees desktop
     * Function: To click on the Payee and expand it on Manage Payee Page
     * Created by: C103403      Created on: 26/08/2019
     */
    public void selectPayeeFromManagePayee() throws Exception {
        String strExpectedPayeeName = dataTable.getData("General_Data", "PayeeName");
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("ManagePayee.tblNewManagePayee"), 5)) {
                clickJS(getObject("xpath~//*[contains(@class,'boi-no-arrow-after-table boi-show-selector')]/descendant::*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]"), "'" + strExpectedPayeeName + "' on the Manage Payee list");
            } else {
                report.updateTestLog("SelectPayeeFromManagePayee", "Manage Payees table  is NOT displayed on Manage Payee page", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject("xpath~//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']"), 5)) {
                clickJS(getObject("xpath~//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']/descendant::*[contains(text(),'" + strExpectedPayeeName + "')]"), "Selected Payee '" + strExpectedPayeeName + "' from the Manage Payee list");
            } else {
                if (isElementDisplayed(getObject("ManagePayee.tblNewManagePayee"), 5)) {
                    clickJS(getObject("xpath~//*[contains(@class,'boi-no-arrow-after-table boi-show-selector')]/descendant::*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]"), "Selected Payee '" + strExpectedPayeeName + "' from the Manage Payee list");
                } else {
                    report.updateTestLog("SelectPayeeFromManagePayee", "Manage Payees table  is NOT displayed on Manage Payee page", Status_CRAFT.FAIL);
                }
            }
        }
    }

    public void verifyExpandedPayee() throws Exception {
        waitForPageLoaded();
        String strExpectedPayeeName = dataTable.getData("General_Data", "PayeeName");
        String strExpectedPayeeCountry = dataTable.getData("General_Data", "PayeeCountry");
        String strExpectedPayeeCurrency = dataTable.getData("General_Data", "PayeeCurrency");
        //Country Validation
        if (!strExpectedPayeeCountry.equals("")) {
            isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-position-rel icon-abs-user-left vertical-align-middle active')]/../descendant::*[@title='Country'][contains(text(),'" + strExpectedPayeeCountry + "')]"), "'" + strExpectedPayeeCountry + "'", "Expanded view for '" + strExpectedPayeeName + "'", 5);
        }

        //Currency Validation
        isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-position-rel icon-abs-user-left vertical-align-middle active')]/../descendant::*[@title='Currency'][contains(text(),'" + strExpectedPayeeCurrency + "')]"), "'" + strExpectedPayeeCurrency + "'", "Expanded view for '" + strExpectedPayeeName + "'", 5);

        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "ManagePayeeDetails").split(";");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {
            if (isElementDisplayed(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'payee-table--left-border tc-row-style tc-selected-row-style')]/descendant::*[text()='" + obj.getKey() + "']/ancestor::div[contains(@class,'tc-question-part boi_payee_detail_label')]/following-sibling::*/descendant::*[contains(text(),'" + obj.getValue() + "')]"), 5)) {
                report.updateTestLog("verifyExpandedPayee", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyExpandedPayee", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
            }
        }

        //Reference Tooltip
        if (isElementDisplayed(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style payee_table_left_border')]/descendant::*[text()='Reference']"), 5)) {
            click(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style payee_table_left_border')]/descendant::*[@aria-label='View info on reference']"), "Reference Tooltip");
            String UiTooltip = getText(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style')]/descendant::*[@aria-label='View info on reference']/span"));
            String strExpectedTooltip = "This reference will be made visible to the payee.";
            if (UiTooltip.equals(strExpectedTooltip)) {
                report.updateTestLog("verifyExpandedPayee", "Tooltip of Reference label is correctly displayed on Payee details page, Expected: '" + strExpectedTooltip + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyExpandedPayee", "Tooltip of Reference label is NOT  correctly displayed on Payee details page, Expected: '" + strExpectedTooltip + "', Actual:" + UiTooltip + "'", Status_CRAFT.FAIL);
            }
        }
        click(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style')]/descendant::*[text()='Status']"), "Status label");
        //Limit Tooltip
        if ((dataTable.getData("General_Data", "JurisdictionType").equals("CROSS")) && ((dataTable.getData("General_Data", "PayeeCountry").equals("")) || (dataTable.getData("General_Data", "PayeeCountry").equals("-")))) {
            if(!(driver.findElement(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/../..")).getAttribute("class")).contains("bill")) {
                click(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style')]/descendant::*[@aria-label='View info on transfer limit']"), "Limit Tooltip");
                Thread.sleep(2000);
                String UiLimitTooltip = getText(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style')]/descendant::*[@aria-label='View info on transfer limit']/span"));
                String strExpectedLimitTooltip = "The limit is in the currency of the account you use when sending money.";
                if (UiLimitTooltip.equals(strExpectedLimitTooltip)) {
                    report.updateTestLog("verifyExpandedPayee", "Tooltip of Limit label is correctly displayed on Payee details page, Expected: '" + strExpectedLimitTooltip + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyExpandedPayee", "Tooltip of Limit label is NOT  correctly displayed on Payee details page, Expected: '" + strExpectedLimitTooltip + "', Actual:" + UiLimitTooltip + "'", Status_CRAFT.FAIL);
                }
            }
        }
        //Pay Button
        String ExpectedStatus = "Active";
        String ActualStatus = getText(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style')]/descendant::*[text()='Status']/ancestor::div[contains(@class,'tc-question-part boi_payee_detail_label')]/following-sibling::*/descendant::span"));
        if (ActualStatus.equals(ExpectedStatus)) {
            isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style')]/descendant::*//a/*[text()='Pay']"), "Pay button", "Payee Expanded view '" + strExpectedPayeeName + "'", 5);
        }
        //Delete Button
        isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style')]/descendant::*//a/*[text()='Delete']"), "Delete button", "Payee Expanded view '" + strExpectedPayeeName + "'", 5);
    }

    public void operationManagePayee() throws Exception {
        String strExpectedPayeeName = dataTable.getData("General_Data", "PayeeName");
        if (!dataTable.getData("General_Data", "ManagePayeeOperation").equals("")) {
            String strOperationManagePayee = dataTable.getData("General_Data", "ManagePayeeOperation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationManagePayee.toUpperCase()) {
                case "PAY":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style')]/descendant::*//a/*[text()='Pay']")));
                    click(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style')]/descendant::*//a/*[text()='Pay']"), "Pay button for Payee '" + strExpectedPayeeName + "'on Manage Payee Page");
                    break;
                case "DELETE":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style')]/descendant::*//a/*[text()='Delete']")));
                    click(getObject("xpath~//*[contains(text(),'" + strExpectedPayeeName.split("~")[1] + "')]/ancestor::div[contains(@class,'tc-row-style tc-selected-row-style')]/descendant::*//a/*[text()='Delete']"), "Delete button for Payee '" + strExpectedPayeeName + "'on Manage Payee Page");
                    waitForPageLoaded();
                    click(getObject("ManagePayee.btnDeletePayee"), "Delete payee on Delete payee pop up");
                    while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                        waitForPageLoaded();
                    }
                    break;
                default:
                    report.updateTestLog("operationManagePayee", "Please provide the valid operation on Manage Payees Page, Pay or Delete", Status_CRAFT.FAIL);
            }
        }
    }

    /*
    Function: To use both the function when using the same column in datasheet for data verification
     */
    public void verifyUKDomReviewPage()throws Exception{
        if(!scriptHelper.commonData.iterationFlag==true){
            verifyUKDomesticAddPayeeReviewPage();
            scriptHelper.commonData.iterationFlag=true;
        }else{
            verifyUKDomesticPaymentsReviewPage();
        }
    }

    /*
    Function: To use both the function when using the same column in datasheet for data verification
     */
    public void verifyUKDomConfirmPage()throws Exception{
        if(!scriptHelper.commonData.confirmationFlag==true){
            PayeeAddConfirmation("w.Addpayee.lblConfirmPage");
            scriptHelper.commonData.confirmationFlag=true;
        }else{
            checkUKDomesticPaymentsConfirmationPage();
        }
    }

    /*
    Function: To use both the function when using the same column in datasheet for data verification for SEPA Review Page
     */
    public void verifySEPAReviewPage()throws Exception{
        if(!scriptHelper.commonData.iterationFlag==true){
            verifyUKDomesticAddPayeeReviewPage();
            scriptHelper.commonData.iterationFlag=true;
        }else{
            verifySendMoneyReviewPage();
        }
    }

    /*
    Function: To use both the function when using the same column in datasheet for data verification in review page for International Payee
     */
    public void verifyInternationalReviewPage()throws Exception{
        if(!scriptHelper.commonData.iterationFlag==true){
            AddPayeeReviewPage();
            scriptHelper.commonData.iterationFlag=true;
        }else{
            internationalPaymentsReviewPage();
        }
    }


    /**
     * CFPUR-815: Private Banking Payment Landing Page - Link to GWS
     * Function: To validate the Private Banking Payment Page
     */
    public void verifyPrivateBankingPaymentPage() throws Exception{
        if(isElementDisplayed(getObject(deviceType()+"PBP.pageHeader"),2)){
            isElementDisplayedWithLog(getObject("PBP.imagePrivate"),"Private Image","Private Banking Payment",1);
            isElementDisplayedWithLog(getObject("PBP.informationText"),"Text '"+getText(getObject("PBP.informationText")) +"'","Private Banking Payment",1);
            isElementDisplayedWithLog(getObject("PBP.list1Heading"),"List 1 heading 'When, for example:'","Private Banking Payment",1);
            isElementDisplayedWithLog(getObject("PBP.list1Text1"),"Information Text '"+getText(getObject("PBP.list1Text1"))+"' in 'When, for example:' list","Private Banking Payment",1);
            isElementDisplayedWithLog(getObject("PBP.list1Text2"),"Information Text '"+getText(getObject("PBP.list1Text2"))+"' in 'When, for example:' list","Private Banking Payment",1);
            isElementDisplayedWithLog(getObject("PBP.list1Text3"),"Information Text '"+getText(getObject("PBP.list1Text3"))+"' in 'When, for example:' list","Private Banking Payment",1);
            isElementDisplayedWithLog(getObject("PBP.list2Heading"),"List 2 heading 'Please note:'","Private Banking Payment",1);
            isElementDisplayedWithLog(getObject("PBP.list2Text1"),"Information Text '"+getText(getObject("PBP.list2Text1"))+"' in 'Please note:' list","Private Banking Payment",1);
            isElementDisplayedWithLog(getObject("PBP.list2Text2"),"Information Text '"+getText(getObject("PBP.list2Text2"))+"' in 'Please note:' list","Private Banking Payment",1);
            isElementDisplayedWithLog(getObject("PBP.list2Text3"),"Information Text '"+getText(getObject("PBP.list2Text3"))+"' in 'Please note:' list","Private Banking Payment",1);
            isElementDisplayedWithLog(getObject("PBP.list2Text4"),"Information Text '"+getText(getObject("PBP.list2Text4"))+"' in 'Please note:' list","Private Banking Payment",1);
            isElementDisplayedWithLog(getObject("PBP.btnRequestPayment"),"Request payment button","Private Banking Payment",1);
            ScrollToVisibleJS("PBP.btnGoBack");
            isElementDisplayedWithLog(getObject("PBP.btnGoBack"),"Go back button","Private Banking Payment",1);

            if (dataTable.getData("General_Data", "Operation").equals("")) {
                report.updateTestLog("verifyPrivateBankingPaymentPage", "No Operation is to be performed", Status_CRAFT.SCREENSHOT);
            } else {
                String strOperationOnPBP = dataTable.getData("General_Data", "Operation");
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                switch (strOperationOnPBP.toUpperCase()) {
                    case "REQUEST PAYMENT":
                        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("PBP.btnRequestPayment")));
                        HomePage homePage = new HomePage(scriptHelper);
                        if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                            String strExpectedLink = "https://form.bankofireland.com/interpay-transfer/?ctype=pb";
                            homePage.verifyHrefLink(strExpectedLink, "PBP.btnRequestPayment");
                            clickJS(getObject("PBP.btnRequestPayment"), "Request payment");
                            Thread.sleep(3000);
                            report.updateTestLog("verifyPrivateBankingPaymentPage", "In-App Website After 3 Sec", Status_CRAFT.SCREENSHOT);
                        }else {
                            clickJS(getObject("PBP.btnRequestPayment"), "Request payment");
                            homePage.verifyNewlyOpenedTab("https://form.bankofireland.com/interpay-transfer/?ctype=pb");
                        }
                        break;
                    case "GO BACK":
                        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("PBP.btnGoBack")));
                        clickJS(getObject("PBP.btnGoBack"), "Go back");
                        break;
                    default:
                        report.updateTestLog("verifyPrivateBankingPaymentPage", "Please provide the valid operation on Private Banking Payment Page, Request payment or Go back", Status_CRAFT.FAIL);
                }
            }
        }
    }

    public void payeeExistAndDelete() throws Exception{
        String payeeName = dataTable.getData("General_Data","PayeeName");
        String IbanOrAccNo = dataTable.getData("General_Data","IBAN_Number");
        String secondaryPayeeName = dataTable.getData("General_Data","AddedPayeeName");
        String secondaryIbanOrReference = dataTable.getData("General_Data","AddedIbanOrRefernce");
        String strExpectedPayeeName = null;
        String strBillReference = null;

        if(!"".equals(secondaryPayeeName)&&!"".equals(secondaryIbanOrReference)){
            strExpectedPayeeName = secondaryPayeeName;
            strBillReference = secondaryIbanOrReference;
        }else{
            strExpectedPayeeName = payeeName;
            strBillReference = IbanOrAccNo;
        }
        if (!deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject("xpath~//*[contains(@title,'Payee name')][contains(text(),'"+ strExpectedPayeeName + "')]"), 5)) {
                clickJS(getObject("xpath~//*[contains(@title,'Payee name')][contains(text(),'"+ strExpectedPayeeName + "')]"), "Selected Payee '" + strExpectedPayeeName + "' from the Manage Payee list");
                if(getText(getObject(deviceType() + "AddBillPayee.lblBillRefernce")).trim().equalsIgnoreCase(strBillReference)||
                        getText(getObject(deviceType() + "AddBillPayee.lblPayeeIBAN")).trim().equalsIgnoreCase(strBillReference)||
                        getText(getObject(deviceType() + "AddBillPayee.lblPayeeAccountNum")).trim().equalsIgnoreCase(strBillReference)){
                    deletePayee();
                }
            }
        } else {
            if (isElementDisplayed(getObject("xpath~//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']"), 10)) {
                if (isElementDisplayed(getObject("xpath~//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']/descendant::*[contains(text(),'" + strExpectedPayeeName +"')]"),5)){
                    if(!"".equalsIgnoreCase(strExpectedPayeeName)){
                        List<WebElement> strList = findElements(getObject("xpath~//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']/descendant::*[contains(text(),'" + strExpectedPayeeName + "')]"));
                        for(int i=1; i<=strList.size(); i++){
                            clickJS(getObject("xpath~(//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']/descendant::*[contains(text(),'" + strExpectedPayeeName + "')])["+i+"]"),"Payee :: "+strExpectedPayeeName);
                            if(getText(getObject(deviceType() + "AddBillPayee.lblBillRefernce")).trim().equalsIgnoreCase(strBillReference)||
                                    getText(getObject(deviceType() + "AddBillPayee.lblPayeeIBAN")).trim().equalsIgnoreCase(strBillReference)||
                                    getText(getObject(deviceType() + "AddBillPayee.lblPayeeAccountNum")).trim().equalsIgnoreCase(strBillReference)){
                                deletePayee();
                                break;
                            } else {
                                clickJS(getObject(deviceType()+ "home.Goback"),"Go back");
                            }
                        }
                    }
                }
            } else {
                if (isElementDisplayed(getObject("xpath~//table[contains(@class,'boiArrowTable')][@summary='PayeeList']"), 5)) {
                    clickJS(getObject("xpath~//table[contains(@class,'boiArrowTable')][@summary='PayeeList']/tbody/tr/td/descendant::*[text()='" + strExpectedPayeeName + "']"), "Selected Payee '" + strExpectedPayeeName + "' from the Manage Payee list");
                    deletePayee();
                } else {
                    report.updateTestLog("SelectPayeeFromManagePayeeList", "Manage Payees table  is NOT displayed on Manage Payee page", Status_CRAFT.FAIL);
                }
            }
        }

//        if(!"".equals(secondaryPayeeName)&&!"".equals(secondaryIbanOrReference)){
//            if(deviceType().equalsIgnoreCase("w.")||deviceType().equalsIgnoreCase("tw.")){
//                secondaryIbanOrReference = secondaryIbanOrReference.substring(secondaryIbanOrReference.length()-4);
//                xpath = "//*[contains(text(),'"+secondaryPayeeName+"')]/ancestor::*[contains(@class,'tc-float-left')]/" +
//                        "descendant::*[not(@style)][@class='col-full text-left col-pad-l-25  ']/" +
//                        "descendant::*[@class='boi_input_sm'][contains(text(),'"+secondaryIbanOrReference+"')]";
//            }else{
//                xpath = "//*[contains(text(),'"+secondaryPayeeName+"')]/ancestor::*[contains(@class,'tc-float-left')]/" +
//                        "descendant::*[not(@style)][@class='col-full text-left col-pad-l-25  ']/" +
//                        "descendant::*[@class='boi_input_sm'][contains(text(),'"+secondaryIbanOrReference+"')]";
//            }
//
//            if(isElementDisplayed(getObject("xpath~"+xpath),10)){
//                clickJS(getObject("xpath~"+xpath),"Payee name '"+secondaryPayeeName+"', IBAN/AccNo.'"+secondaryIbanOrReference+"'");
//                report.updateTestLog("payeeExistAndDelete", "Payee already exist, going ahead with its deletion before adding it again for testing", Status_CRAFT.DONE);
//                waitForJQueryLoad();waitForPageLoaded();
//                deletePayee_New();
//                waitForJQueryLoad();waitForPageLoaded();
//            }
//        }else{
//            xpath = "//*[contains(text(),'"+payeeName+"')]/ancestor::*[contains(@class,'tc-float-left')]/" +
//                    "descendant::*[not(@style)][@class='col-full text-left col-pad-l-25  ']/" +
//                    "descendant::*[@class='boi_input_sm'][contains(text(),'"+IbanOrAccNo+"')]";
//            if(isElementDisplayed(getObject("xpath~"+xpath),10)){
//                clickJS(getObject("xpath~"+xpath),"Payee name '"+payeeName+"', IBAN/AccNo.'"+IbanOrAccNo+"'");
//                report.updateTestLog("payeeExistAndDelete", "Payee already exist, going ahead with its deletion before adding it again for testing", Status_CRAFT.DONE);
//                waitForJQueryLoad();waitForPageLoaded();
//                deletePayee_New();
//                waitForJQueryLoad();waitForPageLoaded();
//            }
//        }


    }
    public void deletePayee_New() throws Exception{
        waitForPageLoaded();
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
            report.updateTestLog("deletePayee", "Screen view", Status_CRAFT.SCREENSHOT);
        }
        clickJS(getObject(deviceType() + "ManagePayee.btnDeletePayee1"),"Delete payee");
        isElementDisplayedWithLog(getObject(deviceType() + "ManagePayee.btnDeletePayee3"),
                "Delete payee","Delete payee",5);
        clickJS(getObject(deviceType() + "ManagePayee.btnDeletePayee3"),"Delete payee");
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
            report.updateTestLog("deletePayee", "Screen view", Status_CRAFT.SCREENSHOT);
        }
    }




    public void ownAccountTransferInvalidAccount() throws Exception{
        String strPaymentType = dataTable.getData("General_Data", "PaymentType");

        waitForElementToClickable(getObject(deviceType() + "home.tabPayments"), 5);
        clickJS(getObject(deviceType() + "home.tabPayments"), "Select 'Payments Tab'");
        waitForPageLoaded();
        waitForJQueryLoad();
        if(isElementDisplayed(getObject("xpath~//*[contains(text(),'" + strPaymentType + "')]"),5))
        {
            report.updateTestLog("selectPaymentOption", "Transfer between my acounts is displayed which is not expected", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("selectPaymentOption", "Transfer between my acounts is not displayed as expected", Status_CRAFT.PASS);
        }
    }

    /**
     * Function : CFPUR - 8931 To Inform customer of limit associated with payees when adding
     * Created by : C966003
     * Update on    Updated by     Reason
     * 18/11/2019   C966003        Newly created
     */
    public void dailyLimitsForAddPayeeReviewPage() throws Exception{
        String strLinkToVerify = dataTable.getData("General_Data","VerifyContent");
        HomePage homepage = new HomePage(scriptHelper);
        isElementDisplayedWithLog(getObject("AddPayee.pageheader"),
                "Review header title","Review page for Add Payee",4);
        isElementDisplayedWithLog(getObject("AddPayee.lblDailyLimitsText"),
                "Daily limits text dispalyed as : " + getText(getObject("AddPayee.lblDailyLimitsText")),"Review page for Add Payee",3);

        if (isMobile){
            homepage.verifyHrefLink(strLinkToVerify, deviceType()+"AddPayee.lnkDailyLimits");
        }else {
            clickJS(getObject(deviceType()+"AddPayee.lnkDailyLimits"),"Daily limits link");
            homepage.verifyNewlyOpenedTab(strLinkToVerify);
        }
    }


    /**
     * Function : To select account from PayFrom for add payee
     * Created by : C966003
     * Update on    Updated by     Reason
     * 19/11/2019   C966003        Newly created
     */
    public void payFrom_AddPayee() throws Exception {
        String strPayFromAccount = dataTable.getData("General_Data", "PayFrom_Account");
        clickJS(getObject("SendMoney.lstSelectAccount"),"Select account");
        clickJS(getObject("xpath~//li[contains(text(),'" + strPayFromAccount.split("~")[1] + "')]"),"Pay from account : "+strPayFromAccount);
        waitForPageLoaded();
        continue_SendMoney();
    }


}




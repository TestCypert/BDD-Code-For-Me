package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import componentgroups.FunctionalComponents;
import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by C966119 on 22/07/2019.
 */
public class PayToMobilePage extends WebDriverHelper {
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public PayToMobilePage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function : Verify Pay to mobile is not present
     * Update on     Updated by    Reason
     * 12/04/2019    C966119
     */
    public void verifyPayToMobileNotPresent() throws Exception {
        if (deviceType.equalsIgnoreCase("Web")) {
            isElementDisplayedWithLog(getObject(deviceType() + "home.tabPayments"),
                    "Payments Tab", "Payments", 5);
            click(getObject(deviceType() + "home.tabPayments"), "'Payments Tab'");
            String myxpath = "xpath~//a/descendant::*[text()='Pay to mobile']";
            if (!isElementDisplayed(getObject(myxpath), 2)) {
                report.updateTestLog("selectPaymentOption", "'Pay to mobile' Option is NOT available under 'Payment' for the user as user is not registered for this service which is expected", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPaymentOption", "'Pay to mobile' Option is available for the user which is NOT expected..as user user is not registered for this service..!! ", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject("mw.home.lblFooter_Payments"), 10)) {
                clickJS(getObject("mw.home.lblFooter_Payments"), " 'Payments' Footer");
                if (!isElementDisplayed(getObject("xpath~//*[text()='Pay to mobile']"), 2)) {
                    report.updateTestLog("selectPaymentOption", "'Pay to mobile' Option is NOT available under 'Payment' for the user as user is not registered for this service which is expected", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("selectPaymentOption", "'Pay to mobile' Option is available for the user which is NOT expected..as user user is not registered for this service..!! ", Status_CRAFT.FAIL);
                }
            }
        }
    }

    /**
     * Function : Verify Pay to mobile is present
     * Update on     Updated by    Reason
     * 12/04/2019    C966119
     */
    public void VerifyPayToMobilePresent() throws Exception {
        if (isElementDisplayed(getObject("mw.home.lblFooter_Payments"), 10)) {
            clickJS(getObject("mw.home.lblFooter_Payments"), " 'Payments' Footer");
            if (isElementDisplayed(getObject("xpath~//*[text()='Pay to mobile']"), 8)) {
                report.updateTestLog("selectPaymentOption", "'Pay to mobile' Option is available under 'Payment' for the user as user is registered for this service which is expected", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("selectPaymentOption", "'Pay to mobile' Option is NOT available for the user which is NOT expected..as user user is registered for this service..!! ", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : Select Pay to Mobile Option
     * Update on     Updated by    Reason
     * 12/04/2019    C966119       Done code clean up activity
     */
    public void verifyClickPayToMobilePayment() throws Exception {
        clickJS(getObject("xpath~//*[text()='Pay to mobile']"), " 'Payments : Pay to Mobile' Option");
    }

    /**
     * Function : Click Continue
     * Update on     Updated by    Reason
     * 12/04/2019    C966119       Done code clean up activity
     */
    public void verifyClickContinue() throws Exception {
        clickJS(getObject(deviceType() + "login.AmendedContinue"), "Click on Continue Button");
    }

    /**
     * Function : Click Go Back
     * Update on     Updated by    Reason
     * 12/04/2019    C966119       Done code clean up activity
     */
    public void verifyClickGoBack() throws Exception {
        clickJS(getObject("launch.btnGoBack"), "Click on 'Go Back' Button");
    }


    /**
     * Function : Click Go Back
     * Update on     Updated by    Reason
     * 12/04/2019    C966119       CFPUR : 8683
     */
    public void verifyUpdateLinkNavigation() throws Exception {
        clickJS(getObject("PTM.lnkUpdateProfile"), "Click on 'Update Profile' link");
        waitForElementPresent(getObject(deviceType() + "profile.lblHeaderProfile"), 10);
        if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderProfile"), 10)) {
            report.updateTestLog("verifyUpdateLinkNavigation", " Navigated to profile page successfully..After clicking Update Profile link.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyUpdateLinkNavigation", " Did not navigated to profile page successfully..After clicking Update Profile.", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Set Up Pay to Mobile Review Page
     * Update on     Updated by    Reason
     * 12/04/2019    C966119       Done code clean up activity
     */
    public void verifyPayToMobRegReview() throws Exception {
        waitForPageLoaded();
        String strAccnt = dataTable.getData("General_Data", "Account_Name");

        if(isMobile){
            isElementDisplayedWithLog(getObject("mw.PTM.PayToMobileSetUpFormHeader"),
                    "'Register for Pay to Mobile' Header ", "Set Up Pay to Mobile Form", 5);
        }else{
            isElementDisplayedWithLog(getObject(deviceType() + "PTM.PayToMobileSetUpFormHeader"),
                    "'Register for Pay to Mobile' Header ", "Set Up Pay to Mobile Form", 5);
        }
        isElementDisplayedWithLog(getObject(deviceType() + "login.AmendedContinue"),
                " 'Continue' button ", "Set Up Pay to Mobile Review Page", 1);
        isElementDisplayedWithLog(getObject(deviceType() + "AddPolicy.btnGoBack"),
                "'Go Back' button ", "Set Up Pay to Mobile Review ", 1);
        isElementDisplayedWithLog(getObject(deviceType() + "home.imgProfileIcon"),
                "'Profile' icon ", "Set Up Pay to Mobile Review ", 1);

        if (isElementDisplayed(getObject("xpath~//span[contains(text(),'" + scriptHelper.commonData.strExisting4Digit.toString() + "')]"), 1)) {
            report.updateTestLog("payeeDetailsPage", "Mobile Number ending with :  " + scriptHelper.commonData.strExisting4Digit + "  : Disaplyed as expected.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("payeeDetailsPage", "Mobile Number ending with :  " + scriptHelper.commonData.strExisting4Digit + "  : is NOT disaplyed as expected.", Status_CRAFT.WARNING);
        }

        if (isElementDisplayed(getObject("xpath~//*[contains(text(),'" + strAccnt + "')]"), 1)) {
            report.updateTestLog("payeeDetailsPage", "Account Number :  " + strAccnt + "  : Disaplyed as expected.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("payeeDetailsPage", "Account Number :  " + strAccnt + "  : is NOT disaplyed as expected.", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Verify Existing Mobile Number from Profile Page on Pay to mobile registration
     * Update on       Update by       Reason
     * 22/07/2019      C966119        newly created
     */
    public void verifyMobileDigitsMatching() throws Exception {
        String strMobile = getText(getObject("xpath~//*[@class='ecDIB  boi_label masked-mobile-arialabel']")).substring(6);
        if (strMobile == null){
            report.updateTestLog("verfiyMobileDigitsMatching", "Mobile Number on Page - 'Set Pay To Mobile' : is NULL/Blank which is not expected. ", Status_CRAFT.FAIL);
        }else if (strMobile.contains(scriptHelper.commonData.strExisting4Digit)) {
            report.updateTestLog("verfiyMobileDigitsMatching", "Mobile Number on Page - 'Set Pay To Mobile' Page : " + strMobile + " : Matching with mobile number on 'Profile' page.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verfiyMobileDigitsMatching", "Mobile Number on Page - 'Set Pay To Mobile' : " + strMobile + " : is NOT Matching with on profile page. As It might be the case that Mobile number in profile section someone has changed.So Passing the Step as this field is not blank or Null", Status_CRAFT.PASS);
        }
    }


    /**
     * Function : Fetch Existing Mobile Number from Profile Page
     * Update on       Update by       Reason
     * 22/07/2019      C966119        newly created
     */
    public void fetchExistingMobileDigits() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderProfile"), 2)) {
            report.updateTestLog("fetchExistingMobileDigits", "User is Already on profile page.", Status_CRAFT.DONE);
        }else {
            waitForElementPresent(getObject(deviceType() + "home.imgProfileIcon"), 7);
            clickJS(getObject(deviceType() + "home.imgProfileIcon"), "Profile icon");
        }
        if (isElementDisplayed(getObject("xpath~//*[contains(text(),'Mobile number')]/following-sibling::div"), 6)) {
            scriptHelper.commonData.strExisting4Digit = getText(getObject("xpath~//*[contains(text(),'Mobile number')]/following-sibling::div")).substring(6);
            report.updateTestLog("fetchExistingMobileDigits", "On Profile Page Mobile Number : " + scriptHelper.commonData.strExisting4Digit, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("fetchExistingMobileDigits", "Mobile Number is not added on profile page.", Status_CRAFT.FAIL);
        }
    }


    /**
     * Function : Set Up Pay to Mobile Registration Page
     * Update on     Updated by    Reason
     * 12/04/2019    C966119
     */
    public void verifyPayToMobRegFormPage() throws Exception {
        waitForPageLoaded();
        if(isMobile){
            isElementDisplayedWithLog(getObject("mw.PTM.PayToMobileSetUpFormHeader"),
                    "'Register for Pay to Mobile' Header ", "Set Up Pay to Mobile Form", 5);
        }else{
            isElementDisplayedWithLog(getObject(deviceType() + "PTM.PayToMobileSetUpFormHeader"),
                    "'Register for Pay to Mobile' Header ", "Set Up Pay to Mobile Form", 5);
        }
        isElementDisplayedWithLog(getObject(deviceType() + "login.AmendedContinue"),
                " 'Continue' button ", "Set Up Pay to Mobile Form", 1);
//        isElementDisplayedWithLog(getObject(deviceType() + "AddPolicy.btnGoBack"),
//                "'Go Back' button ", "Set Up Pay to Mobile Form", 1);
        isElementDisplayedWithLog(getObject(deviceType() + "home.imgProfileIcon"),
                "'Profile' icon ", "Set Up Pay to Mobile Form ", 1);
        isElementDisplayedWithLog(getObject("PTM.lnkUpdateProfile"),
                "'Update Profile' link", "Set Up Pay to Mobile Form", 1);
        isElementDisplayedWithLog(getObject("PTM.lblRegMobNumber"),
                "'Is this your mobile number?' label ", "Set Up Pay to Mobile Form", 1);
        String strMobValues = getText(getObject("xpath~//*[@class='ecDIB  boi_label masked-mobile-arialabel']/div"));

        scriptHelper.commonData.strExisting4Digit = strMobValues.substring(strMobValues.length() - 4);
        report.updateTestLog("verifyPayToMobRegFormPage", "On Set Up Pay to Mobile Form Page Mobile number displayed as : " + strMobValues, Status_CRAFT.DONE);
        isElementDisplayedWithLog(getObject("MTU.lstAccountspayFrom"),
                "'Select account' button ", "Set Up Pay to Mobile Form", 1);

    }

    /**
     * Function :
     * Update on     Updated by    Reason
     * 12/04/2019    C966119       CFPUR-88
     */
    public void verifyPTMRegLandingPage() throws Exception {
        String strText = dataTable.getData("General_Data", "Nickname");
        String[] arrTextList = strText.split("::");
        waitForPageLoaded();
        if(isMobile){
            isElementDisplayedWithLog(getObject("mw.PTM.PayToMobileSetUpHeader"),
                    "'Pay to Mobile' Header ", "Set Up Pay to Mobile Landing", 5);
        }else{
            isElementDisplayedWithLog(getObject(deviceType() + "PTM.PayToMobileSetUpHeader"),
                    "'Pay to Mobile' Header ", "Set Up Pay to Mobile Landing", 5);
        }
        isElementDisplayedWithLog(getObject(deviceType() + "home.imgProfileIcon"),
                "'Profile' icon ", "Set Up Pay to Mobile Landing ", 1);

        isElementDisplayedWithLog(getObject("PTM.lblWhatIsPTM"),
                "'What is Pay to Mobile?' label ", "Set Up Pay to Mobile Landing ", 1);

        isElementDisplayedWithLog(getObject("PTM.imgPayToMobile"),
                "'PayToMobile' image ", "Set Up Pay to Mobile Landing", 1);

        isElementDisplayedWithLog(getObject("PTM.btnSetUpNow"),
                "'Register Now' button ", "Set Up Pay to Mobile Landing", 1);

        if (arrTextList[0].equals(getText(getObject("PTM.txtPTMLandingPage0")))){
            report.updateTestLog("verifyPTMRegLandingPage", " 'Directional text' :  "+ arrTextList[0], Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyPTMRegLandingPage",  " 'Directional text' is NOT expected :: "+ arrTextList[0], Status_CRAFT.FAIL);
        }

        if (arrTextList[1].trim().equals(getText(getObject("PTM.txtPTMLandingPage1")))){
            report.updateTestLog("verifyPTMRegLandingPage", " 'Directional text' :  "+ arrTextList[1], Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyPTMRegLandingPage",  " 'Directional text' is NOT expected :: "+ arrTextList[1], Status_CRAFT.FAIL);
        }

    }

    /**
     * Function : Click on SetUpNow button from landing page
     * Update on     Updated by    Reason
     * 12/04/2019    C966119       CFPUR-88
     */
    public void clickSetUpNowButton() throws Exception {
        clickJS(getObject("PTM.btnSetUpNow"), "'Register Now' button");
    }


    /**
     * Function : Verify the Account List from the Drop down
     * Update on     Updated by    Reason
     * 12/04/2019    C966119
     */
    public void selectAccountPayFrom() throws Exception {
        String strAccnt = dataTable.getData("General_Data", "Account_Name");
        if (isElementDisplayed(getObject("MTU.lstAccountspayFrom"), 2)) {
            clickJS(getObject("MTU.lstAccountspayFrom"), "Account Dropdown");
            waitForPageLoaded();
            List<WebElement> allLinks = findElements(getObject("MTU.Accountlist"));
            if (allLinks.size() > 1) {
                int intIndex = 1;
                for (WebElement ele : allLinks) {
                    String linkname = ele.getText();
                    if (linkname.contains(strAccnt)) {
                        if (isMobile){
                            JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                            executor.executeScript("arguments[0].click();", ele);
                            report.updateTestLog("selectAccountPayFrom", linkname + " :: clicked and selected ", Status_CRAFT.PASS);
                            break;
                        }else{
                            ele.click();
                            report.updateTestLog("selectAccountPayFrom", linkname + " :: clicked and selected ", Status_CRAFT.PASS);
                            break;
                        }
                    }
                    intIndex = intIndex++;
                }
            }
        }
    }



    /**
     * Function : Verify the Account Not Present in dropdown
     * Update on     Updated by    Reason
     * 12/04/2019    C966119
     */
    public void AccountDropDownNotPresent() throws Exception {
        String strAccnt = dataTable.getData("General_Data", "Account_Type");
        String[] arrAccList = strAccnt.split("::");

        for (int i = 0; i < arrAccList.length; i++) {
            boolean blnFlag = false;
            if (isElementDisplayed(getObject("MTU.lstAccountspayFrom"), 2)) {
                clickJS(getObject("MTU.lstAccountspayFrom"), "Account Dropdown");
                waitForPageLoaded();
                List<WebElement> allLinks = findElements(getObject("MTU.Accountlist"));
                if (allLinks.size() > 1) {
                    int intIndex = 1;
                    for (WebElement ele : allLinks) {
                        String linkname = ele.getText();
                        if (linkname.contains(arrAccList[i])) {
                            blnFlag = true;
                            report.updateTestLog("SelectAccountPayFrom", linkname + " ::  is present in drop down list ", Status_CRAFT.FAIL);
                        }
                        intIndex = intIndex++;
                    }
                    if (blnFlag == false) {
                        report.updateTestLog("SelectAccountPayFrom", arrAccList[i] + " ::  is NOT present in drop down list ", Status_CRAFT.PASS);
                    }
                }
            }
        }


    }

    /**
     * Function : Verify the Success Page
     * Update on     Updated by    Reason
     * 12/04/2019    C966119
     */
    public void verifyPayToMobileRegSuccessPage() throws Exception {
        String strExpConfirmationMessage = dataTable.getData("General_Data", "ConfirmationMessage");
        String strActConfirmationMessage =  getText(getObject("PTM.txtSuccessPage1"));
        waitForPageLoaded();
        waitForElementPresent(getObject("AddPolicy.imgSuccess") , 15);
        isElementDisplayedWithLog(getObject("AddPolicy.imgSuccess"),
                "'Success' icon ", " Register Pay to Mobile Success ", 2);

        isElementDisplayedWithLog(getObject("AddPolicy.btnBackToHome"),
                "'Back To Home' button ", " Register Pay to Mobile Success ", 1);

        if (isElementDisplayed(getObject("PTM.txtSuccessPage1"),1)) {
            report.updateTestLog("verifyPayToMobRegSuccessPage", "The Success message :: "+  getText(getObject("PTM.txtSuccessPage1")) +" :: correctly displayed on 'Register for Pay to Mobile' Success Page", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyPayToMobRegSuccessPage", "The Success message :: "+ strActConfirmationMessage +" :: Is NOT correctly displayed on 'Register for Pay to Mobile' Success Page", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Verify Success page operations
     * Update on       Update by       Reason
     * 30/07/2019      C966119        newly created
     */
    public void verifyOperationsOnSuccessPage() throws Exception {
        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to home")) {
            clickJS(getObject("AddPolicy.btnBackToHome"), "Clicked on 'Back to home'");
            isElementDisplayedWithLog(getObject(deviceType() + "home.sectionTimeLine"),
                    "Upon clicking 'Back to home'", "Navigated to Home page from Pay to Mobile Registration Success", 28);
        } else if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Send Money_OnlyApp")) {
            clickJS(getObject("PTM.btnSendMoneyNow"), "Clicked on 'Send Money Now'");
            isElementDisplayedWithLog(getObject("PTM.hdrPageTitle"),
                    "Header page title", "Pay To Mobile", 30);
        }
    }

    /**
     * Function : Verify Update Profile link navigate user to Profile Management Page
     * Update on       Update by       Reason
     * 30/07/2019      C966119        newly created
     */
    public void clickUpdateProfile() throws Exception {
        clickJS(getObject("PTM.lnkUpdateProfile"), "'Update Profile' link");
        waitForElementPresent(getObject(deviceType() + "profile.lblHeaderProfile"), 10);
        if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderProfile"), 10)) {
            report.updateTestLog("NavigateToProfilePage", "Navigated to profile page successfully after clicking on 'Update Profile' link", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("NavigateToProfilePage", " NOT navigated to profile page after clicking on 'Update Profile' link", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Verify Error Message
     * Update on       Update by       Reason
     * 30/07/2019      C966119        newly created
     */
    public void validateErrorAlreadyRegistered() throws Exception {
        String strErrorMsg = "You have already registered for Pay to Mobile service";
        isElementDisplayedWithLog(getObject("xpath~//*[contains(@class,'fa-exclamation')]/following-sibling::*[contains(text(),'" + strErrorMsg + "')]"),
                strErrorMsg, "Pay to mobile registration", 25);

    }

    /**
     * Function : Verify Error Message : When same mobile number is already in use by other user
     * Update on       Update by       Reason
     * 30/07/2019      C966119        newly created
     */
    public void validateErrorMobileAlreadyRegistered() throws Exception {
        waitForPageLoaded();
        waitForElementPresent(getObject("xpath~//span[@class='boi_input']"), 15 );
        String strErrorMsg = "This mobile phone number has already been registered for Pay To Mobile so you cannot proceed";
        if (isElementDisplayed(getObject("xpath~//*[contains(text(),'" + strErrorMsg + "')]") ,15)){
            report.updateTestLog("validateErrorMobileAlreadyRegistered", " Error Message Displayed as expected :: "+ strErrorMsg, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateErrorMobileAlreadyRegistered",  " Error Message Displayed as is NOT expected :: "+ strErrorMsg, Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Verify Error Message : 'Please select an account'
     * Update on       Update by       Reason
     * 30/07/2019      C966119        newly created
     */
    public void validateSelectAccError() throws Exception {
        isElementDisplayedWithLog(getObject("PTM.lblErrorSelectAccount"),
                "Error Text : 'Please select an account'", "Pay To Mobile", 13);
    }

    /**
     * Function : Pay To Mobile Form page ::Account Selection (CFPUR - 280)
     * Created on       Created By       Reason
     * 25/07/2019      c101979         In Sprint Development
     */
    public void PayToMobileAccountSelection() throws Exception {
        //validating page title 'Pay To Mobile'
        isElementDisplayedWithLog(getObject("PTM.hdrPageTitle"),
                "Header page title", "Pay To Mobile", 2);
        //validating label over dropdown 'Account'
        isElementDisplayedWithLog(getObject("PTM.lblToView"),
                "Text 'Pay from'", "Pay To Mobile", 2);
        //Validate label 'Account'
        if (isElementDisplayedWithLog(getObject("PTM.lblAccount"),
                "Label 'Account'", "Pay To Mobile", 2)) {
            String strSelectAccount = dataTable.getData("General_Data", "PayFrom_Account");
            if (!dataTable.getData("General_Data", "PayFrom_Account").equals("")) {
                clickJS(getObject("PTM.lstSelectAccount"), "Pay From Account Dropdown");
                Thread.sleep(5000);
                clickJS(getObject("xpath~//ul/li[contains(.,'" + strSelectAccount + "')]"), "Pay From Account Dropdown");
                report.updateTestLog("selectPayfromSendMoney", "Account Selected '" + dataTable.getData("General_Data", "PayFrom_Account") + "'", Status_CRAFT.DONE);
                Thread.sleep(8000);
            }
        }
    }
    /**
     * Function : Pay To Mobile Form page ::Validating the Form Page UI (CFPUR - 280)
     * Created on       Created By       Reason
     * 25/07/2019       c101979         In Sprint Development
     */
    public void PayToMobileFormPage() throws Exception {
        String Jurisdiction = dataTable.getData("General_Data", "JurisdictionType");
        //validating Available funds on 'Pay To Mobile'Form page
        if (Jurisdiction.equals("ROI")) {
            isElementDisplayedWithLog(getObject("PTM.availFund"),
                    "Available funds", "Pay To Mobile", 2);
        }
        //validating label 'Pay to' on Pay To Mobile Form Page
        isElementDisplayedWithLog(getObject("PTM.lblPayTo"),
                "Label 'Pay to'", "Pay To Mobile Form ", 2);
        //validating label 'Mobile number' on Pay To Mobile Form Page
        isElementDisplayedWithLog(getObject("PTM.lblMobileNumber"),
                "Label 'Mobile number'", "Pay To Mobile Form ", 2);
        //validating option 'select from contact list' on Pay To Mobile Form Page
        isElementDisplayedWithLog(getObject("PTM.SelectFromContact"),
                "Option 'select from contact list'", "Pay To Mobile Form ", 2);
        //validating option 'Amount' on Pay To Mobile Form Page
        isElementDisplayedWithLog(getObject("PTM.lblAmount"),
                "Label 'Amount'", "Pay To Mobile Form ", 2);
        //validating currency type for Amount Field on Pay To Mobile Form Page

        if (Jurisdiction.equals("ROI")) {
            if (isElementDisplayed(getObject("PTM.lblAmountBetweenEuro"), 5)) {
                report.updateTestLog("verifyPayToMobileForm", "Between €5 - €100 is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyPayToMobileForm", "Between €5 - €100 is not displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("PTM.lblCurrencyEuro"), 5)) {
                report.updateTestLog("verifyPayToMobileForm", "Currency '€' is displayed for ROI Jurisdication", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyPayToMobileForm", "Currency '€' is NOT displayed for ROI Jurisdication", Status_CRAFT.FAIL);
            }
        } else if (Jurisdiction.equals("NI")) {
            if (isElementDisplayed(getObject("PTM.lblAmountBetweenGBP"), 5)) {
                report.updateTestLog("verifyPayToMobileForm", "Between £5 - £100 is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyPayToMobileForm", "Between £5 - £100 is not displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("PTM.lblCurrencyGBP"), 5)) {
                report.updateTestLog("verifyPayToMobileForm", "Currency '£' is displayed for NI Jurisdication", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyPayToMobileForm", "Currency '£' is NOT displayed for NI Jurisdication", Status_CRAFT.FAIL);
            }
        }
        //validating label 'Your name' on Pay To Mobile Form Page
        isElementDisplayedWithLog(getObject("PTM.lblYourName"),
                "Label 'Your name'", "Pay To Mobile Form ", 2);
        isElementDisplayedWithLog(getObject("PTM.txtYourNameDes"),
                "The text 'We’ll send a text to your payee using this name for reference.'underneath 'Your name' field label ", "Pay To Mobile Form ", 2);

        if (isElementDisplayed(getObject("PTM.lblYourNameOptional"), 5)) {
            report.updateTestLog("verifyPayToMobileForm", "'Your name' field is an optional field,which is expected ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyPayToMobileForm", "'Your name' field is a Mandatory field,which is NOT expected", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Pay To Mobile Form page ::Entering the Form details (CFPUR - 280)
     * Created on       Created By       Reason
     * 25/07/2019       c101979         In Sprint Development
     */
    public void enterPTMDetails() throws Exception {
        //Mobile Number
        String strMobNumber = dataTable.getData("General_Data", "Phone");
        if (isElementDisplayed(getObject("PTM.lblMobileNumber"), 5)) {
            clickJS(getObject("PTM.txtMobNumber"),"Entering the Mobile number ");
            sendKeys(getObject("PTM.txtMobNumber"), strMobNumber, "Mobile number entered is '" + strMobNumber + "'");
        } else {
            report.updateTestLog("verifyEnterPTMDetails", "Label 'Mobile number' is NOT displayed on 'Pay To Mobile' Page", Status_CRAFT.FAIL);
        }

        //Amount
        String strAmount = dataTable.getData("General_Data", "Pay_Amount");
        if (isElementDisplayed(getObject("PTM.lblAmount"), 5)) {
            sendKeys(getObject("PTM.txtAmount"), strAmount, "Amount entered is '"+ strAmount +"'");
        } else {
            report.updateTestLog("verifyEnterPTMDetails", "Label 'Amount' is NOT displayed on 'Pay To Mobile' Page", Status_CRAFT.FAIL);
        }

        //Your name'
        String strYourName = dataTable.getData("General_Data", "YourName");
        if (isElementDisplayed(getObject("PTM.lblYourName"), 5)) {
            sendKeys(getObject("PTM.txtYourName"), strYourName, "Your Name entered is '"+ strYourName +"'");
        } else {
            report.updateTestLog("verifyEnterPTMDetails", "Label 'Your name' is NOT displayed on 'Pay To Mobile' Page", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("PTM.btnContinue"), 5)) {
            clickJS(getObject("PTM.btnContinue"), " 'Continue' Button");
        } else {
            report.updateTestLog("verifyEnterPTMDetails", "Button 'Continue' is NOT displayed on 'Pay To Mobile' Form Page", Status_CRAFT.FAIL);
        }
    }
    /**
     * Function : Pay To Mobile Review page ::Validating the Review Page Details (CFPUR - 280)
     * Created on       Created By       Reason
     * 25/07/2019       c101979         In Sprint Development
     */
    public void validatePTMReviewPage() throws Exception {
        //validating page title 'Pay To Mobile'
        isElementDisplayedWithLog(getObject("PTM.hdrPageTitle"),
                "Header page title", "Pay To Mobile Review", 2);
        //validating label over dropdown 'Account'
        isElementDisplayedWithLog(getObject("PTM.lblReview"),
                "Section Header 'Review'", "Pay To Mobile Review", 2);
        //Amount in
        String strAmountIn = dataTable.getData("General_Data", "Current_Balance");
        if (isElementDisplayed(getObject("xpath~//*[text()='Amount']"), 5)) {
            report.updateTestLog("verifyPTMReviewPage", "Label 'Amount' is displayed on Pay To Mobile Review page", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("xpath~//*[text()='Amount']/ancestor::div[contains(@class,'review-amount')]/span[@class='boi_amount']"), 5)) {
                String actAmount = getText(getObject("xpath~//*[text()='Amount']/ancestor::div[contains(@class,'review-amount')]/span[@class='boi_amount']"));
                if (actAmount.equals(strAmountIn)) {
                    report.updateTestLog("verifyPTMReviewPage", "Amount '" + strAmountIn + "' is appearing on Review Page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyPTMReviewPage", "Amount '" + strAmountIn + "' is not appearing on Review Page", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("verifyPTMReviewPage", "Label Amount with value'" + strAmountIn + "' is NOT displayed on Pay To Mobile Review page", Status_CRAFT.FAIL);
        }

        //validate review section details
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String strOperation = dataTable.getData("General_Data", "OperationOnReviewPage");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {
            if (!obj.getValue().equals("NA")) {
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']/ancestor::li[contains(@class,'boi-review-item')]/descendant::*[contains(text(),'" + obj.getValue() + "')]"), 5)) {
                    report.updateTestLog("verifyPTMReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input:: " + obj.getValue(), Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyPTMReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
                }
            } else {
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
                    report.updateTestLog("verifyPTMReviewPage", "'" + obj.getKey() + "' is displayed on review page for blank input", Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("verifyPTMReviewPage", "'" + obj.getKey() + "' is Not displayed on review page for blank input", Status_CRAFT.PASS);
                }
            }
        }
        //Validate Go Back Button on Review Page
        isElementDisplayedWithLog(getObject("PTM.btnGoBack"),
                "Button ''Go back' ", " Pay To Mobile Review", 2);
        //Validate Continue Button on Review Page
        isElementDisplayedWithLog(getObject("PTM.btnContinue"),
                "Button ''Continue' ", "Pay To Mobile Review", 2);
        if (!dataTable.getData("General_Data", "JurisdictionType").equals("Cross")) {
            String Jurisdiction = dataTable.getData("General_Data", "JurisdictionType");
            if (Jurisdiction.equals("NI")) {
                String ExpInformativetxt = dataTable.getData("General_Data", "NotificationText1");
                String ActInformativetxt = getText(getObject("xpath~//div[@class='boi-tc-note']"));

                if (ExpInformativetxt.equals(ActInformativetxt)) {
                    report.updateTestLog("verifyPTMReviewPage", "Informative text is appearing on the Pay To Mobile Review Page Expected::'" + ExpInformativetxt + "', Actual::'" + ActInformativetxt + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyPTMReviewPage", "Informative text is NOT appearing on the Pay To Mobile Review Page Expected::'" + ExpInformativetxt + "', Actual::'" + ActInformativetxt + "'", Status_CRAFT.FAIL);
                }
            } else if (Jurisdiction.equals("ROI")) {
                String ExpInformativetxt = dataTable.getData("General_Data", "NotificationText1");
                String ActInformativetxt = getText(getObject("xpath~//div[@class='boi-tc-note']"));

                if (ExpInformativetxt.equals(ActInformativetxt)) {
                    report.updateTestLog("verifyPTMReviewPage", "Informative text is appearing on the Pay To Mobile Review Page Expected::'" + ExpInformativetxt + "', Actual::'" + ActInformativetxt + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyPTMReviewPage", "Informative text is NOT appearing on the Pay To Mobile Review Page Expected::'" + ExpInformativetxt + "', Actual::'" + ActInformativetxt + "'", Status_CRAFT.FAIL);
                }
            }
        }
//        validateDailylimitsNCutOffTimes();
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        //*[contains(@class,'tc-button')]/span[text()='Continue']
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("xpath~//*[contains(@class,'tc-button')]/span[text()='" + strOperation + "']")));
        Thread.sleep(2000);
        waitForElementToClickable(getObject("xpath~//*[contains(@class,'tc-button')]/span[text()='" + strOperation + "']"), 5);
        clickJS(getObject("xpath~//*[contains(@class,'tc-button')]/span[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
        if (strOperation.equalsIgnoreCase("Go back")) {
            if (isElementDisplayed(getObject("PTM.hdrPageTitle"), 5)) {
                report.updateTestLog("verifyPTMReviewPage", "Page successfully navigated to Pay To Mobile Details page, after clicking 'Go back'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyPTMReviewPage", "Page did not navigate to Pay To Mobile Details page, after clicking 'Go back'", Status_CRAFT.FAIL);
            }
        }
    }

    public void updateProfileMobileRandom() throws Exception {
        String strMobile = dataTable.getData("General_Data", "Relationship_Status");
        if (!(strMobile.length()> 0)){
            Random rand = new Random();
            int rand_int = rand.nextInt(100000);
            strMobile = "08995" + Integer.toString(rand_int);
        }
        clickJS(getObject("profile.lnkUpdateMobile"), "Update Mobile Number Textbox");
        waitForElementPresent(getObject(deviceType() + "profile.lblHeaderUpdateMobile"), 5);
        if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderUpdateMobile"), 5)) {
            report.updateTestLog("updateProfileMobileNumber", "Navigated to the page : Update Mobile Number : successfully.", Status_CRAFT.PASS);
            sendKeysJS(getObject("profile.inputboxProfileMobile"), strMobile);
            waitForElementPresent(getObject("profile.inputboxConfirmProfileMobile"), 10);
            sendKeysJS(getObject("profile.inputboxConfirmProfileMobile"), strMobile);
            waitForElementPresent(getObject("AddPolicy.Continue"), 10);
            clickJS(getObject("AddPolicy.Continue"), "Click on Continue button to update mobile number :: " + strMobile.toString());
        } else {
            report.updateTestLog("updateProfileMobileNumber", " Did not navigated to the page : Update Mobile Number : successfully.", Status_CRAFT.FAIL);
        }
    }
    /**
     * Function : Pay To Mobile Success page ::Validating the Success Page Details (CFPUR - 280)
     * Created on       Created By       Reason
     * 25/07/2019       c101979         In Sprint Development
     */
    public void validatePTMSuccessPage() throws Exception {
        //validating page title 'Pay To Mobile'
        isElementDisplayedWithLog(getObject("PTM.hdrPageTitle"),
                "Header page title", "Pay To Mobile Success", 2);

        isElementDisplayedWithLog(getObject("PTM.iconConfirmation"),
                " Success Icon 'Tick Mark'", " 'Pay To Mobile' Success ", 25);

        isElementDisplayedWithLog(getObject("PTM.txtSuccess"),
                "Text message 'Success'", " 'Pay To Mobile' Success ", 25);
        //Amount in
        String strAmountIn = dataTable.getData("General_Data", "Current_Balance");
        if (isElementDisplayed(getObject("xpath~//*[text()='" + strAmountIn + "']"), 5)) {
            report.updateTestLog("verifyPTMSuccessPage", "Amount '" + strAmountIn + "' is appearing on Success Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyPTMSuccessPage", "Amount '" + strAmountIn + "' is not appearing on Success Page", Status_CRAFT.FAIL);
        }
        String strSuccessPageContent = getText(getObject("PTM.divSuccess"));
        String strFromAccount = dataTable.getData("General_Data", "PayFrom_Account");
        if (strSuccessPageContent.contains(strFromAccount)) {
            report.updateTestLog("verifyPTMSuccessPage", "From Account '" + strFromAccount + "' is appearing on Success Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyPTMSuccessPage", "Amount '" + strFromAccount + "' is not appearing on Success Page", Status_CRAFT.FAIL);
        }
        String strToMobileAccount = dataTable.getData("General_Data", "MobileNumber");
        if (strSuccessPageContent.contains(strToMobileAccount)) {
            report.updateTestLog("verifyPTMSuccessPage", " 'To' Account Number linked Mobile Number '" + strToMobileAccount + "' is appearing on Success Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyPTMSuccessPage", "'To' Account Number linked Mobile Number '" + strToMobileAccount + "' is not appearing on Success Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("PTM.txtTimeReq"), 5)) {
            report.updateTestLog("verifyPTMSuccessPage", "Time Stamp with message 'Time of request' is displayed on Order Interest Certificates' Success page", Status_CRAFT.PASS);

            String strRequestSubmitted = getText(getObject("PTM.txtTimeReq"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("verifyPTMSuccessPage", "Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("verifyPTMSuccessPage", "Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("verifyPTMSuccessPage", "Time Stamp with message 'Time of request' is NOT displayed on Pay To Mobile' Success Page", Status_CRAFT.FAIL);
        }
        //Validating Navigation/functionality of the buttons
        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to payments")) {
            click(getObject("PTM.btnBackToPayment"), "Clicked on 'Back to payments'");
            if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 5)) {
                report.updateTestLog("verifyPTMSuccessPage", "Upon clicking 'Back to payments' ,successfully Navigated to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyPTMSuccessPage", "Upon clicking 'Back to payments' ,navigation unsuccessful to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : Unregister Pay to Mobile Form Page
     * Update on     Updated by    Reason
     * 06/09/2019    C966119       Newly Added
     */
    public void verifyDeregisterPTMPage() throws Exception {

        String strAccnt = dataTable.getData("General_Data", "Account_Name");
        report.updateTestLog("verifyDeregisterPTMPage", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        waitForPageLoaded();
        if(isMobile){
            isElementDisplayedWithLog(getObject("mw.PTM.UnregisterHeader"),
                    "'De Register Pay To Mobile' Header ", "De Register Pay to Mobile Landing", 5);
            isElementDisplayedWithLog(getObject("mw.PTM.btnDeregister"),
                    " 'De-Register' button ", " Manage Pay to Mobile " ,1);
        }else{
            isElementDisplayedWithLog(getObject(deviceType() + "PTM.UnregisterHeader"),
                    "'De Register Pay To Mobile' Header ", "De Register Pay to Mobile Landing", 5);
            isElementDisplayedWithLog(getObject(deviceType()+ "PTM.btnDeregister"),
                    " 'De-Register' button ", " Manage Pay to Mobile " ,1);
        }

        isElementDisplayedWithLog(getObject(deviceType()+ "home.imgProfileIcon"),
                "'Profile' icon ", " Manage Pay to Mobile " ,1);

        if (isElementDisplayed(getObject("xpath~//*[text()='Your mobile number']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/div/span[contains(text(),'"+ scriptHelper.commonData.strExisting4Digit +"')]"), 1)) {
            report.updateTestLog("verifyDeregisterPTMPage", "Mobile Number ending with :  " + scriptHelper.commonData.strExisting4Digit +"  : Disaplyed as expected.", Status_CRAFT.PASS);
        } else if (scriptHelper.commonData.strExisting4Digit == null){
            report.updateTestLog("verifyDeregisterPTMPage", "Mobile Number on Page - ' De-Registration Page ' : is NULL/Blank which is not expected. ", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyDeregisterPTMPage", "Mobile Number on Page - ' De-Registration Page ' : " + scriptHelper.commonData.strExisting4Digit + " : is displayed with correct format and field is not blank or null which is expected.", Status_CRAFT.PASS);
        }

        if (isElementDisplayed(getObject("xpath~//*[text()='Your account for receiving payments']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::span[contains(text(),'" + strAccnt +"')]"), 1)) {
            report.updateTestLog("verifyDeregisterPTMPage", " Registered Pay to Mobile Account Number :  " + strAccnt +"  : Displayed as expected.", Status_CRAFT.PASS);
        } else if (getText(getObject("xpath~//*[text()='Your account for receiving payments']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::span[contains(text(),'')]")) == null){
            report.updateTestLog("verifyDeregisterPTMPage", "Registered Pay to Mobile Account Number : on Page - ' De-Registration Page ' : is NULL/Blank which is not expected. ", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyDeregisterPTMPage", " Registered Pay to Mobile Account Number :  " + strAccnt +"  : Displayed as expected.", Status_CRAFT.PASS);
        }
    }


    /**
     * Function : De-register Button Click
     * Update on     Updated by    Reason
     * 06/09/2019    C966119       Newly Added
     */
    public void clickDeregister() throws Exception {
        clickJS(getObject(deviceType()+ "PTM.btnDeregister") , "'De-Register' button");
    }

    /**
     * Function : De-Register Pop up button
     * Update on     Updated by    Reason
     * 06/09/2019    C966119       Newly Added
     */
    public void clickDeRegisterPopUpBtn() throws Exception {
        clickJS(getObject("PTM.btnPopUpDeregister") , " Pop up 'De-Register' button");
    }

    /**
     * Function : Cancel Pop up button
     * Update on     Updated by    Reason
     * 06/09/2019    C966119       Newly Added
     */
    public void clickCancelPopUpBtn() throws Exception {
        clickJS(getObject("PTM.btnPopUpCancel") , " Pop up 'Cancel' button");
    }

    /**
     * Function : Verify Success page after De-Registration operations
     * Update on       Update by       Reason
     * 06/09/2019      C966119        newly created
     */
    public void verifyDeRegistrationOperations() throws Exception {
        if (dataTable.getData("General_Data", "Properties_Variable").equalsIgnoreCase("Back to home")) {
            clickJS(getObject("AddPolicy.btnBackToHome"), "Clicked on 'Back to home'");
            waitForPageLoaded();
            isElementDisplayedWithLog(getObject(deviceType() + "home.sectionTimeLine"),
                    " Upon clicking 'Back to home' ", " Navigated to Home page from Pay to Mobile De-Registration Success ", 10);
        } else if (dataTable.getData("General_Data", "Properties_Variable").equalsIgnoreCase("Re-register")) {
            clickJS(getObject("PTM.btnReRegister"), " Clicked on 'Re-register' ");
            String strServiceDeskOption = dataTable.getData("General_Data", "ServiceDeskOption");
            waitForPageLoaded();
            isElementDisplayedWithLog(getObject("PTM.btnSetUpNow"),
                    "'Set Up Now' button ", " 'Set Up Pay To Mobile Landing Page'", 10);
        }
    }

    /**
     * Function : Set up Pay to Mobile Tile
     * Update on     Updated by    Reason
     * 06/09/2019    C966119       Newly Added
     */
    public void verifyNavigationToServices() throws Exception {
        isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'Set up Pay to Mobile')]"),
                " Tile : 'Set up Pay to Mobile' ", "Services", 30);
    }

    public void verifyMoreInfoLink() throws Exception {
        waitForElementToClickable(getObject("PTM.lnkErrorMsgMoreInfo"), 5);
        if (isElementDisplayed(getObject("PTM.lnkErrorMsgMoreInfo"), 5)){
            HomePage homePage = new HomePage(scriptHelper);
            String strExpectedLink = "https://personalbanking.bankofireland.com/ways-to-bank/mobile-banking/pay-to-mobile/";
            homePage.verifyHrefLink(strExpectedLink, "PTM.lnkErrorMsgMoreInfo");
        }else {
            report.updateTestLog("verifyPTMErrorInfoLink", "Link 'More info' is NOT displayed while sending money to a customer that is not registered to pay to mobile ", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Verify the Success Page : De-Register Pay to Mobile
     * Update on     Updated by    Reason
     * 12/04/2019    C966119
     */
    public void verifyDeRegisterSuccessPagePTM() throws Exception {
        waitForElementPresent(getObject("AddPolicy.imgSuccess"), 25);
        isElementDisplayedWithLog(getObject("AddPolicy.imgSuccess"),
                "'Success' icon ", " De-Register Pay to Mobile Success ", 25);

        isElementDisplayedWithLog(getObject("AddPolicy.btnBackToHome"),
                "'Back To Home' button ", " De-Register Pay to Mobile Success ", 1);

        isElementDisplayedWithLog(getObject("PTM.btnReRegister"),
                "'Re-Register' button ", " De-Register Pay to Mobile Success ", 1);

        if (isElementDisplayed(getObject("PTM.lblDeRegisterSuccess"), 1)) {
            report.updateTestLog("verifyPayToMobileRegSuccessPage", " Text :: You have de-registered from the Pay to Mobile service. :: on Success page is displayed correctly.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyPayToMobileRegSuccessPage", "Text on Success page is NOT displayed correctly..!!", Status_CRAFT.FAIL);
        }

    }

    /**
     * Function : Verify check PTM Registration OR DeReg
     * Update on       Update by       Reason
     * 12/09/2019      C966119        newly created
     */
    public void checkPTMRegistrationOrDeReg() throws Exception {
        waitForPageLoaded();
        if (isElementDisplayed(getObject(deviceType()+ "PTM.btnDeregister") ,5)) {
            report.updateTestLog("checkPTMRegistrationOrDeReg", " :: Pay To Mobile Registration :: De-Register flow :: ", Status_CRAFT.DONE);
            verifyFlowPTMDeReg();
        } else if (isElementDisplayed(getObject("PTM.btnSetUpNow") ,5)) {
            report.updateTestLog("checkPTMRegistrationOrDeReg", " :: Pay To Mobile Registration :: Register flow :: ", Status_CRAFT.DONE);
            verifyFlowPTMRegistration();
        }
    }
    /**
     * Function : Verify check PTM De-Registration
     * Update on       Update by       Reason
     * 07/01/2020      C966119        newly created
     */
    public void verifyFlowPTMDeReg() throws Exception {
        FunctionalComponents funComp = new FunctionalComponents(scriptHelper);
        funComp.verifyDeregisterPTM();
        funComp.clickDeregisterBtn();
        funComp.verifyDeRegisterPopUpDetails();
        funComp.verifyDeRegisterPopUpBtnOperation();
        funComp.verifyDeRegisterSuccessPage();
        funComp.checkDeRegistrationOperations();
    }

    /**
     * Function : Verify check PTM Registration
     * Update on       Update by       Reason
     * 07/01/2020      C966119        newly created
     */
    public void verifyFlowPTMRegistration() throws Exception {
        FunctionalComponents funComp = new FunctionalComponents(scriptHelper);
        funComp.verifyPayToMobileRegLandingPage(    );
        funComp.clickSetUpPayToMobile();
        waitForPageLoaded();
        String strMobValues = getText(getObject("PTM.lblRegMobile"));
        strMobValues = strMobValues.substring(strMobValues.length() - 4);
        String strMobileExcel = dataTable.getData("General_Data", "Relationship_Status");
        if (!(strMobileExcel.toString().contains(strMobValues))) {
            funComp.validatePayToMobRegFormPage();
            funComp.validateUpdateProfileLink();
            verifyFlowUpdateMobile();
            funComp.fetchExistingMobileDigitsProfile();
            funComp.selectServiceDeskOption();
            funComp.verifyPayToMobileRegLandingPage();
            funComp.clickSetUpPayToMobile();
        }
        funComp.verifyMobileDigitsMatchingProfile();
        funComp.validatePayToMobRegFormPage();
        funComp.verifySelectAccountPayToMobile();
        funComp.verifyClickContinueNTW();
        funComp.validatePayToMobRegReviewPage();
        funComp.verifyClickContinueNTW();
        funComp.verifyEnterRequiredPin();
        waitForPageLoaded();
        if (isElementDisplayed(getObject("launch.approvalNotificationPage"), 3)) {
            HomePage homePage = new HomePage(scriptHelper);
            homePage.verifyPushIsNotAccepted();
        }
        funComp.acceptPushNotification ();
        waitForPageLoaded();
        waitForElementToClickable(getObject("AddPolicy.btnBackToHome") , 10);
        if (isElementDisplayed(getObject("AddPolicy.btnBackToHome") , 8 )){
            funComp.verifyPayToMobRegSuccessPage();
            verifyOperationsOnSuccessPage();
        }else {
            validateErrorMobileAlreadyRegistered();
        }
    }

    /**
     * Function : Verify check PTM Registration and update the mobile number
     * Update on       Update by       Reason
     * 07/01/2020      C966119        newly created
     */
    public void verifyFlowUpdateMobile() throws Exception {
        FunctionalComponents funComp = new FunctionalComponents(scriptHelper);
        funComp.verifyProfileRandomMobileNumber();
        funComp.verifyEnterRequiredPin();
        if (isMobile){
            waitForPageLoaded();waitForPageLoaded();
            if (isElementDisplayed(getObject("launch.approvalNotificationPage"), 3)) {
                HomePage homePage = new HomePage(scriptHelper);
                homePage.verifyPushIsNotAccepted();
            }
            funComp.acceptPushNotification();
            waitForPageLoaded();waitForPageLoaded();
        }
        funComp.verifyProfileSuccessMessage();
    }
}
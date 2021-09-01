package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.CraftDriver;
import com.cognizant.framework.selenium.WebDriverHelper;
import componentgroups.FunctionalComponents;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;


/**
 * Function/Epic: Home Page Epic (Squad-1,2)
 * Class: HomePage
 * Developed on: 12/07/2018
 * Developed by: C966003
 * Update on     Updated by    Reason
 * 12/04/2019    C966119       Done code clean up activity
 */

public class HomePage extends WebDriverHelper {

    public static String strAccount;
   

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public HomePage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function : To click Launch Application
     * Created by : C966003
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     */
    public void launchApplication() throws Exception {
        String strAppUrl = dataTable.getData("Login_Data", "Application_URL");
        driver.get(strAppUrl);
        report.updateTestLog("InvokeChannelApplication", "Launching Application: " + strAppUrl, Status_CRAFT.DONE);
        if (isElementDisplayed(getObject("preloginDetail"), 2)) {
            click(getObject("preloginDetail"), "Advance");
            waitForPageLoaded();
            click(getObject("preLoginProceed"), "Proceed link");
        }
        if (isElementDisplayed(getObject("launch.btnLogInSecurityDeviceScreen"), 2)) {
            if (deviceType.equalsIgnoreCase("Web")) {
                click(getObject("launch.btnLogInSecurityDeviceScreen"), "LogIn");
            } else if(deviceType.equalsIgnoreCase("MobileWeb")|| deviceType.equalsIgnoreCase("TabletWeb")){
                clickJS(getObject("launch.btnLogInSecurityDeviceScreen"), "LogIn");
            }
        }
    }


    /**
     * Function : To verify digital application launch
     * Update on     Updated by    Reason
     */
    public void verifyappLaunch() throws Exception {
        verifyContinuetoLogin();
        //isElementDisplayedWithLog(getObject("launch.edtUserName"), "Enter user id","Launch Application",25);
    }

    /**
     * Function : To click and verify the continue to login button
     * Update on     Updated by    Reason
     */
    public void verifyContinuetoLogin() throws InterruptedException {

        if (isElementDisplayed(getObject("home.ContinueTologin"), 2)) {
            clickJS(getObject("home.ContinueTologin"), "continue to login clicked");
        }
        if (isElementDisplayed(getObject("home.loginbutton"), 2)) {
            clickJS(getObject("home.loginbutton"), "Login and set up device button is clicked");
        }
        if (isElementDisplayed(getObject("home.loginsetupdevice"), 2)) {
            clickJS(getObject("home.loginsetupdevice"), "Login and set up device button is clicked");
        }
    }


    /**
     * Function : Verify the tooltip and its content
     * Update on     Updated by    Reason
     * 15/04/2019   C966119        Updated the Thread.sleep
     */
    public void verifytooltip() throws Exception {
        isElementclickable(getObject(deviceType() + "home.lbltooltip"), 5);
        click(getObject(deviceType() + "home.lbltooltip"), "Click on ToolTip");
        waitForElementToClickable(getObject(deviceType() + "home.lbltooltip"), 20);
        String strActualToolTipMsg = getText(getObject(deviceType() + "home.lbltooltip"));
        String strExpectedToolTipMsg = dataTable.getData("General_Data", "Properties_Variable");
        if (strActualToolTipMsg.equals(strExpectedToolTipMsg)) {
            report.updateTestLog("Verify Tooltip", "ToolTip verified successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Verify Tooltip", "ToolTip not verified  successfully", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Verify the Login filed
     * Update on     Updated by    Reason
     * 15/04/2019   C966119        Updated the Thread.sleep
     */
    public void verifyLoginFields() throws Exception {
        String user = dataTable.getData("Login_Data", "Username");
        String strActualErrorUserMsg = "";
        String ActualInvalidUserMsg = "";
        String ActualErrorDOBMsg = "";
        String ExpectedToolDOBMsg = "";
        String ActualErrorPINMsg = "";
        String ExpectedToolPINMsg = "";
        String strExpectedToolUserMsg = "No user ID entered, please try again";
        String ExpectedInvalidUserMsg = "No user ID entered, please try again";

        click(getObject("launch.btnLogIn"));
        strActualErrorUserMsg = getText(getObject(deviceType() + "home.userIdErrormsg"));

        if (strActualErrorUserMsg.equals(strExpectedToolUserMsg)) {
            report.updateTestLog("Verify UserID null validation", "UserID null validation verified successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Verify UserID null validation", "UserID null validation not verified  successfully", Status_CRAFT.FAIL);
        }

        if (user.length() == 6 || user.length() == 8) {
            sendKeys(getObject("launch.edtUserName"), dataTable.getData("Login_Data", "Username"), "Username");
            report.updateTestLog("Verify Valid UserID  validation", "UserID null validation verified successfully", Status_CRAFT.PASS);
        } else if (user.length() != 6 || user.length() != 8) {
            sendKeys(getObject("launch.edtUserName"), dataTable.getData("Login_Data", "Username"), "Username");
            ActualInvalidUserMsg = getText(getObject(deviceType() + "home.userIdErrormsg"));
            if (ActualInvalidUserMsg.equals(ExpectedInvalidUserMsg)) {
                report.updateTestLog("Verify UserID Invalid validation", "UserID null validation verified successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Verify UserID invalid validation", "UserID null validation verified not successfully", Status_CRAFT.FAIL);
            }
        }

        if (isElementDisplayed(getObject("launch.edtDateOfBirth"), 2)) {
            click(getObject("launch.btnLogIn"));
            ActualErrorDOBMsg = getText(getObject(deviceType() + "home.DOBErrormsg"));
            ExpectedToolDOBMsg = "Invalid date, please try again. Date must be in format DD/MM/YYYY";
            if (ActualErrorDOBMsg.equals(ExpectedToolDOBMsg)) {
                report.updateTestLog("Verify DOB null validation", "DOB null validation verified successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Verify DOB null validation", "DOB null validation not verified  successfully", Status_CRAFT.FAIL);
            }
        } else {
            isElementDisplayed(getObject("launch.edtPhoneNumberDigits"), 2);
            click(getObject("launch.btnLogIn"));
            ActualErrorPINMsg = getText(getObject(deviceType() + "home.PINErrormsg"));
            ExpectedToolPINMsg = "No contact number, please try again";
            if (ActualErrorPINMsg.equals(ExpectedToolPINMsg)) {
                report.updateTestLog("Verify Contact number null validation", "PIN null validation verified successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Verify Contact number null validation", "PIN null validation not verified  successfully", Status_CRAFT.FAIL);
            }
        }

        if (isElementDisplayed(getObject("home.lnkSecurityConcern"), 5)) {
            click(getObject("home.lnkSecurityConcern"));
            // isElementDisplayed(getObject("launch.securityconcerndialog"), 5);
            report.updateTestLog("securityconcern", "securityconcern link displayed  successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("securityconcern", "securityconcern link not displayed  successfully", Status_CRAFT.FAIL);
        }
    }


    /**
     * Function : To navigate to the login page
     * Update on     Updated by    Reason
     */
    public void LoginDetailsNavigation() throws InterruptedException {
        if (isElementDisplayed(getObject("w.sca.Continuetologin"), 10)) {
            click(getObject("w.sca.Continuetologin"));
        } else {
            report.updateTestLog("Continue to login", " Continue to login not displayed successfully", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("w.sca.BOILabel"), 10)) {
            ScrollToVisibleJS("w.sca.BOILabel");
            report.updateTestLog("Validate BOI LOGO", "BOI LOGO is present on footer", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Validate BOI LOGO", "BOI LOGO NOT present on footer", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To enter user name and date of birth
     * Update on     Updated by    Reason
     * 15/04/2019   C966119        Updated the Thread.sleep
     */
    public void enterLoginDetails() throws InterruptedException {
        waitForJQueryLoad();
        String user = dataTable.getData("Login_Data", "Username");
        String strWaterMark = "";
        if (user.length() == 6 || user.length() == 8) {
            clickJS(getObject("launch.edtUserName"), " Click on User Name ");
            sendKeys(getObject("launch.edtUserName"), dataTable.getData("Login_Data", "Username"), "Username");
        }

        if (isElementDisplayed(getObject("launch.edtDateOfBirth"), 10)) {

            String strSavingDate = dataTable.getData("Login_Data", "DOB");
            System.out.println(strSavingDate);
            String waterMark = driver.findElement(getObject("launch.edtDateOfBirth")).getAttribute("placeholder");
            if (waterMark.equals("DD/MM/YYYY")) {
                report.updateTestLog("WaterMark for DOB", " WaterMark for DOB verified successfully", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("WaterMark for DOB", " WaterMark for DOB  not verified successfully", Status_CRAFT.FAIL);
            }
            //Date selection
            waitForPageLoaded();
            waitForElementToClickable(getObject("launch.edtDateOfBirth"), 5);
            scrollToView(getObject("launch.edtDateOfBirth"));
            clickJS(getObject("launch.edtDateOfBirth"), "DOB");
            waitForPageLoaded();
            waitForElementToClickable(getObject("launch.edtDateOfBirth"), 10);
            sendKeys(getObject("launch.edtDateOfBirth"), strSavingDate);
        } else {
            waitForElementToClickable(getObject("launch.edtPhoneNumberDigits"), 10);
            strWaterMark = driver.findElement(getObject("launch.edtPhoneNumberDigits")).getAttribute("placeholder");
            if (strWaterMark.equals("Enter Last 4 Digits")) {
                report.updateTestLog("WaterMark for Contact", " WaterMark for DOB verified successfully", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("WaterMark for Contact", " WaterMark for DOB  not verified successfully", Status_CRAFT.FAIL);
            }
            scrollToView(getObject("launch.edtPhoneNumberDigits"));
            click(getObject("launch.edtPhoneNumberDigits"), "last digit");
            sendKeys(getObject("launch.edtPhoneNumberDigits"), dataTable.getData("Login_Data", "PhoneNumberDigits_DGAPP"), "Password");
        }

        clickJS(getObject("launch.btnContinue"), "Login");
        //clickJS(getObject("launch.btnLogIn"),"Login");
    }

// To verify if user waits for less than 5 minutes on screen and able to proceed without timeout message..

    public void enterLoginDetailsTimeoutNegative() throws InterruptedException {
        String user = dataTable.getData("Login_Data", "Username");
        if (user.length() == 6 || user.length() == 8) {
            click(getObject("launch.edtUserName"));
            Thread.sleep(3000);
            sendKeys(getObject("launch.edtUserName"), dataTable.getData("Login_Data", "Username"), "Username");
        }
        if (isElementDisplayed(getObject("launch.edtDateOfBirth"), 2)) {
            String strSavingDate = dataTable.getData("Login_Data", "DOB");
            String waterMark = driver.findElement(getObject("launch.edtDateOfBirth")).getAttribute("placeholder");
            if (waterMark.equals("DD/MM/YYYY")) {
                report.updateTestLog("WaterMark for DOB", " WaterMark for DOB verified successfully", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("WaterMark for DOB", " WaterMark for DOB  not verified successfully", Status_CRAFT.FAIL);
            }
            //Date selection
            waitForPageLoaded();
            click(getObject("launch.edtDateOfBirth"), "DOB");
            waitForPageLoaded();
            sendKeys(getObject("launch.edtDateOfBirth"), strSavingDate);
        } else {

            String waterMark = driver.findElement(getObject("launch.edtPhoneNumberDigits")).getAttribute("placeholder");
            if (waterMark.equals("Enter Last 4 Digits")) {
                report.updateTestLog("WaterMark for Contact", " WaterMark for DOB verified successfully", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("WaterMark for Contact", " WaterMark for DOB  not verified successfully", Status_CRAFT.FAIL);
            }
            click(getObject("launch.edtPhoneNumberDigits"), "last digit");
            sendKeys(getObject("launch.edtPhoneNumberDigits"), dataTable.getData("Login_Data", "PhoneNumberDigits_DGAPP"), "Password");
        }
        Thread.sleep(120000);
        clickJS(getObject("launch.btnLogIn"), "Login");

        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        //To handle the SCA pin page
        String strObject = "";
        if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"), 5)) {
            strObject = "launch.edtPinEnterFieldGrpSCA";
            report.updateTestLog("VerifyHomePage", ":: Step 1 of 3 :: SCA - Uplift ::", Status_CRAFT.SCREENSHOT);
        } else if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrp"), 2)) {
            strObject = "launch.edtPinEnterFieldGrp";
        }
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject(strObject));
        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
            }
        }
        report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.PASS);
        Thread.sleep(120000);
        //To handle the SCA pin page
        if (isElementDisplayed(getObject("launch.btnConfirm"), 2)) {
            clickJS(getObject("launch.btnConfirm"), "Clicked on Confirm button");
        } else if (isElementDisplayed(getObject("w.home.btnLogin"), 2)) {
            clickJS(getObject("w.home.btnLogin"), "Clicked on Login button");
        }
    }

    /**
     * Function : To validate pin fields
     * Update on     Updated by    Reason
     * 15/04/2019   C966119        Updated the Thread.sleep
     */
    public void verifyPINFields() throws Exception {
        String strActualErrorPINMsg = "";
        String strExpectedToolPINMsg = "";
        waitForElementToClickable(getObject("launch.btnLogIn"), 10);
        click(getObject("launch.btnLogIn"), "Login");
        waitForElementToClickable(getObject("launch.btnLogIn"), 10);
        strActualErrorPINMsg = getText(getObject("home.lblPINErrormsg"));
        strExpectedToolPINMsg = "You did not enter the 3 requested digits of your PIN! Your request HAS NOT been submitted. Please try again";

        if (strActualErrorPINMsg.equals(strExpectedToolPINMsg)) {
            report.updateTestLog("Verify PIN digits null validation", "PIN digits null validation verified successfully", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("Verify PIN digits null validation", "PIN digits null validation not verified  successfully", Status_CRAFT.FAIL);
        }
    }


    /**
     * Function : Function to mobile PIN input
     * Update on     Updated by    Reason
     * 15/04/2019   C966119        Updated the Thread.sleep
     */
    public void enterRequiredPin() throws InterruptedException {
        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        String strObject = "";
        waitForPageLoaded();
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
        if (isMobile) {
            driver.hideKeyboard();
        }
        //To handle the SCA pin page
        if (isElementDisplayed(getObject("launch.btnConfirm"), 2)) {
            clickJS(getObject("launch.btnConfirm"), "Confirm button");
        } else if (isElementDisplayed(getObject("w.home.btnLogin"), 2)) {
            clickJS(getObject("w.home.btnLogin"), "Login button");
        } else if (isElementDisplayed(getObject("launch.btnContinue"), 10)) {
            clickJS(getObject("launch.btnContinue"), "Continue button");
        }
    }

    /**
     * Function : method to session time out on PIN
     * Update on     Updated by    Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, log details
     */
    public void pinSessionTimeOut() throws Exception {
        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("launch.edtPinEnterFieldGrp"));
        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).click();   //sendKeys(arrPin[i]);
            }
        }
        report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.PASS);
        Thread.sleep(320000);
        clickJS(getObject("w.home.btnLogin"), "Login");
        waitForElementPresent(getObject(deviceType() + "login.sessionTimeoutHeader"), 2);
        isElementDisplayedWithLog(getObject(deviceType() + "login.sessionTimeoutHeader"),
                "Session time out Header", "Session time out pop up", 5);
        isElementDisplayedWithLog(getObject(deviceType() + "login.sessionTimeoutMessage"),
                "Session time out message", "Session time out pop up", 5);
        isElementDisplayedWithLog(getObject(deviceType() + "login.loginButton"),
                "Login Button", "Launch Application Page", 5);
    }

    /**
     * Function : Method to select BOI terms and conditions
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep
     */
    public void selectBoiTerms() throws InterruptedException {
        if (isElementDisplayed(getObject("launch.chkBoiTermsAndCondi"), 2)) {
            ScrollToVisibleJS("launch.chkBoiTermsAndCondi");
            clickJS(getObject("launch.chkBoiTermsAndCondi"), "Terms and conditions");
            Thread.sleep(2000);
            waitForElementToClickable(getObject("launch.btnContinue"), 2);
            clickJS(getObject("launch.btnContinue"), "Continue");
        } else if (isElementDisplayed(getObject("launch.lblTermsAndCondi"), 2)) {
            waitForElementToClickable(getObject("launch.btnContinue"), 2);
            clickJS(getObject("launch.btnContinue"), "Continue");
        }
    }

    /**
     * Function : Function to Device list (SCA)
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep
     */
    public void verifyDeviceListSCA() throws Exception {
        if (isElementDisplayed(getObject("launch.deviceListPage"), 5)) {
            report.updateTestLog("DeviceListPage", " Choose security device page is displayed", Status_CRAFT.PASS);
//            int x = driver.findElement(By.xpath("//fieldset[@id='C2__C1__FS_QUE_C67AAE282C16C109639843_R1']")).getLocation().getX();
//            int y = driver.findElement(By.xpath("//fieldset[@id='C2__C1__FS_QUE_C67AAE282C16C109639843_R1']")).getLocation().getY();
//            Actions a = new Actions((WebDriver) driver.getWebDriver());
//            WebElement elm1 = driver.findElement(By.xpath("//label[@for='C2__C1__QUE_C67AAE282C16C109639843_0_R1']"));
//            a.moveToElement(elm1).contextClick().build().perform();
//            Robot robot = new Robot(); //java.awt.Robot
//            robot.mouseMove(730, 169); // moves your cursor to the supplied absolute position on the screen
            //robot.mousePress(InputEvent.BUTTON1_MASK); //execute mouse click
            //robot.mouseRelease(InputEvent.BUTTON1_MASK); //java.awt.event.InputEvent
            // driver.findElement(By.xpath("//fieldset[@id='C2__C1__FS_QUE_C67AAE282C16C109639843_R1']")).click();
            clickJS(getObject((("xpath~//*[@id='C2__C1__p1_QUE_0D857B61A322D937351294_R1']/div/label/span"))), "Device");
            report.updateTestLog("DeviceListPage", " Device selected from list successfully", Status_CRAFT.PASS);
            Thread.sleep(2000);
            clickJS(getObject("launch.btnChooseDevices"), "Choose Devices");
            Thread.sleep(2000);
        } else {
            report.updateTestLog("DeviceListPage", " Device is not selected/present ", Status_CRAFT.DONE);
        }

    }


    /**
     * Function : Validate the SCA notification
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void sca_Notification() throws InterruptedException {
        int intCounter = 0;
        boolean blnStatus = false;
        if (isElementDisplayed(getObject("launch.deviceListPage"), 1)) {
            waitForPageLoaded();
            report.updateTestLog("sca_Notification page", " :: SCA Page - Choose Device :: ", Status_CRAFT.SCREENSHOT);
            click(getObject("launch.imgDeviceIcon"));
            if (isElementDisplayed(getObject("launch.btnContinue_deviceList"), 15)) {
                //click(getObject("launch.btnContinue_deviceList"));
                ScrollAndClickJS("launch.btnContinue_deviceList");
            }
            do {
                intCounter++;
                blnStatus = isElementDisplayed(getObject("home.sectionTimeLine"), 10);
                if (intCounter == 20) {
                    break;
                }
            } while (blnStatus);

        } else {
            report.updateTestLog("sca_Notification page", "SCA Mobile notification page not displayed successfully", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Function to Handle both SCA and Normal flow
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyLoginWithSCA_OnOff() throws Exception {
        if (isElementDisplayed(getObject("launch.btnContinue_deviceList"), 2)) {
            sca_Notification();
        } else {
            selectBoiTerms();
        }

    }

    /**
     * Mobile App Automation : Function to verify home page after login
     */
    public void verifyHomePageMobile() throws Exception {
        if (isElementDisplayed(getObject("launch.AmendContinue"), 2)) {
            click(getObject("launch.AmendContinue"));
            report.updateTestLog("verifyHomePageMobile", "Term and condition page is accepted and clicked on 'Continue' button", Status_CRAFT.SCREENSHOT);
        }

        if (isElementDisplayed(getObject("m.home.sectionWelcome"), 460)) {
            String strValCaptured = getText(getObject("m.home.sectionTimeLine"));
            report.updateTestLog("VerifyHomePage", "'Welcome' Home Page and 'Last Login Displayed with Value'::: " + strValCaptured, Status_CRAFT.DONE);
        } else {
            report.updateTestLog("VerifyHomePage", "'Welcome' Home Page is not displayed.", Status_CRAFT.FAIL);
        }
    }


    /**
     * Function : Function to verify home page after login
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyHomePage() throws Exception {

        if (isElementDisplayed(getObject("AddPolicy.imgSuccess"), 3)) {
            report.updateTestLog("VerifyHomePage", ":: Step 3 of 3 :: SCA - Uplift ::", Status_CRAFT.SCREENSHOT);
            if (isElementDisplayed(getObject("launch.btnToHomepage"), 1)) {
                click(getObject("launch.btnToHomepage"));
            } else if (isElementDisplayed(getObject("SendMoney.btnContinue"), 5)) {
                click(getObject("SendMoney.btnContinue"));
            }
        }

        if (isElementDisplayed(getObject("launch.AmemdT&c"), 3)) {
            report.updateTestLog("VerifyHomePage", "Amend T&C displayed successfully", Status_CRAFT.PASS);
            click(getObject("launch.AmendContinue"), "Continue on Amend T&C");
        }


        String strValCaptured = "";
        /*631- change in mobile page - My Account will be present*/

        if (deviceType.equalsIgnoreCase("web")) {
            if (isElementDisplayed(getObject(deviceType() + "home.titleMyAccounts"), 2)) {
                report.updateTestLog("VerifyHomePage", "Home page displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyHomePage", "Home page not displayed successfully", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : To Verify session timeout on Login Page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyLoginTimeout() throws InterruptedException {
        String strUser = dataTable.getData("Login_Data", "Username");
        String strSavingDate = "";
        String strWaterMark = "";
        if (strUser.length() == 6 || strUser.length() == 8) {
            waitForElementToClickable(getObject("launch.edtUserName"), 5);
            click(getObject("launch.edtUserName"));
            sendKeys(getObject("launch.edtUserName"), dataTable.getData("Login_Data", "Username"), "Username");
        }

        if (isElementDisplayed(getObject("launch.edtDateOfBirth"), 2)) {
            strSavingDate = dataTable.getData("Login_Data", "DOB");
            strWaterMark = driver.findElement(getObject("launch.edtDateOfBirth")).getAttribute("placeholder");
            if (strWaterMark.equals("DD/MM/YYYY")) {
                report.updateTestLog("WaterMark for DOB", " WaterMark for DOB verified successfully", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("WaterMark for DOB", " WaterMark for DOB  not verified successfully", Status_CRAFT.FAIL);
            }
            //Date selection
            waitForPageLoaded();
            waitForElementToClickable(getObject("launch.edtDateOfBirth"), 10);
            click(getObject("launch.edtDateOfBirth"), "DOB");
            waitForPageLoaded();
            sendKeys(getObject("launch.edtDateOfBirth"), strSavingDate);

        } else {
            strWaterMark = driver.findElement(getObject("launch.edtPhoneNumberDigits")).getAttribute("placeholder");
            if (strWaterMark.equals("Enter Last 4 Digits")) {
                report.updateTestLog("WaterMark for Contact", " WaterMark for DOB verified successfully", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("WaterMark for Contact", " WaterMark for DOB  not verified successfully", Status_CRAFT.FAIL);
            }
            click(getObject("launch.edtPhoneNumberDigits"), "last digit");
            sendKeys(getObject("launch.edtPhoneNumberDigits"), dataTable.getData("Login_Data", "PhoneNumberDigits_DGAPP"), "Password");
        }

        Thread.sleep(320000);
        waitForElementToClickable(getObject("launch.btnLogIn"), 10);
        clickJS(getObject("launch.btnLogIn"), "Login");
        isElementDisplayedWithLog(getObject(deviceType() + "login.sessionTimeoutHeader"),
                "Session Timeout Header", "Session Time out pop up", 5);
        isElementDisplayedWithLog(getObject(deviceType() + "login.sessionTimeoutMessage"),
                "Session Timeout Message", "Session Time out pop up", 5);
        isElementDisplayedWithLog(getObject(deviceType() + "login.loginButton"),
                "Login button", "Session Time out pop up", 5);
        clickJS(getObject("launch.btnLogInTimeout"), "Login");

    }

    /**
     * Function : To Verify Nickname from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyNickName_Home() throws Exception {
        String objectName = "";
        String strExpNickName = dataTable.getData("General_Data", "Nickname");

        if (strExpNickName.equalsIgnoreCase("Test")) {
            objectName = deviceType() + "home.lblNickname_Current";
        } else if (strExpNickName.equalsIgnoreCase("Savings Account")) {
            objectName = deviceType() + "home.lblNickname_Saving";
        } else if (strExpNickName.equalsIgnoreCase("TL")) {
            objectName = deviceType() + "home.lblNickname_TLoan";
        } else if (strExpNickName.equalsIgnoreCase("DEPOSIT ~ 7908 ")) {
            objectName = deviceType() + "home.lblDeposit";
        } else if (strExpNickName.equalsIgnoreCase("MORTGAGE ~ 7919")) {
            objectName = deviceType() + "home.lblMortage";
        } else if (strExpNickName.equalsIgnoreCase(" Pension Policy ~ 7914     ")) {
            objectName = "home.lblPensionPolicy";
        } else if (strExpNickName.equalsIgnoreCase(" Protection Policy ~ 7912     ")) {
            objectName = deviceType() + "home.lblProtectionPolicy";
        } else if (strExpNickName.equalsIgnoreCase("InvestmentPolicy")) {
            objectName = deviceType() + "home.lblInvsPolicyTableChildRows";
        } else if (strExpNickName.equalsIgnoreCase(" DEPOSIT ~ 7908  ")) {
            objectName = deviceType() + "home.lblDeposit";
        }
        if (isElementDisplayed(getObject(objectName), 3)) {
            fetchTextToCompare(getObject(objectName), strExpNickName, "Nickname");
            report.updateTestLog("VerifyNickname", "Nickname displayed successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyNickname", "Nickname not displayed successfully", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify CurrentBalance from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyCurrentBalance(String strAccountName) throws Exception {
        String strVal = "";
        waitforPresenceOfElementLocated(getObject(strAccountName));
        if (isElementDisplayed(getObject(strAccountName), 5)) {
            strVal = getText(getObject(strAccountName));
            report.updateTestLog("verifyCurrentBalance", "Current Balance displayed as: " + strVal, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyCurrentBalance", "Current Balance is not displayed successfully", Status_CRAFT.FAIL);
        }
    }


    /**
     * Function : To Verify Currency Symbol from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyCurrencySymbol(String strCurrencySymbol) throws Exception {
        String strVal = "";
        String strCurrSymbol = dataTable.getData("General_Data", "Currency_Symbol");
        waitforPresenceOfElementLocated(getObject(strCurrencySymbol));
        if (isElementDisplayed(getObject(strCurrencySymbol), 5)) {
            strVal = getText(getObject(strCurrencySymbol));
            if (strVal.contains(strCurrSymbol)) {
                report.updateTestLog("verifyCurrency", "Currency Symbol " + strCurrSymbol + " is validated successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyCurrency", "Failed to validate Currency Symbol " + strCurrSymbol, Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyCurrency", "Object '" + strCurrencySymbol + "' does not exist", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify account is not displayed from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyAcctNotDisplayed(String accountName) throws Exception {
        String strVal = "";
        String strNickName = dataTable.getData("General_Data", "Nickname");
        strVal = getText(getObject(accountName));
        if (strVal.equalsIgnoreCase(strNickName)) {
            report.updateTestLog("verifyAccountNotDisplayed", strNickName + " account is displayed : ", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyAccountNotDisplayed", strNickName + " account is not displayed ", Status_CRAFT.PASS);
        }
    }

    /**
     * Function : To Verify Nickname on Blue card
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyNicknameOnBlueCard(String accountName, String blueCardAccountName) throws Exception {
        String strVal = "";
        waitforPresenceOfElementLocated(getObject(accountName));
        if (isElementDisplayed(getObject(accountName), 5)) {
            click(getObject(accountName), "Nickname on home page");
            if (isElementDisplayed(getObject(blueCardAccountName), 5)) {
                strVal = getText(getObject(blueCardAccountName));
                report.updateTestLog("verifyNicknameOnBlueCard", "Nickname on blue card displayed as : " + strVal, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyNicknameOnBlueCard", "Nickname on blue card not displayed successfully", Status_CRAFT.FAIL);
            }
        }

    }

    /**
     * Function : To Verify CurrentBalance on Blue card
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyCurrentBalanceOnBlueCard(String strAccountName, String blueCardCurrentBalance) throws Exception {
        String strVal = "";
        waitforPresenceOfElementLocated(getObject(strAccountName));
        if (isElementDisplayed(getObject(strAccountName), 5)) {
            click(getObject(strAccountName), "Account on home page");
            if (isElementDisplayed(getObject(blueCardCurrentBalance), 5)) {
                strVal = getText(getObject(blueCardCurrentBalance));
                report.updateTestLog("verifyCurrentBalanceOnBlueCard", "Balance on blue card displayed as: " + strVal, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyCurrentBalanceOnBlueCard", "Balance on blue card is not displayed successfully", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : To Verify AvailableBalance on Blue card
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyAvailableBalanceOnBlueCard(String strAccountName, String strBlueCardAbailableBalance) throws Exception {
        String strVal = "";
        waitforPresenceOfElementLocated(getObject(strAccountName));
        if (isElementDisplayed(getObject(strAccountName), 5)) {
            click(getObject(strAccountName), "Account on home page");
            if (isElementDisplayed(getObject(strBlueCardAbailableBalance), 5)) {
                strVal = getText(getObject(strBlueCardAbailableBalance));
                report.updateTestLog("verifyAvailableBalanceOnBlueCard", "Available Balance on blue card displayed as : " + strVal, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAvailableBalanceOnBlueCard", "Available Balance on blue card is not displayed successfully", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : To Verify Currency Symbol on Blue card
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyCurrencySymbolOnBlueCard(String strAccountName, String strBlueCardCurrencySymbol) throws Exception {
        String strVal = "";
        String strCurrSymbol = dataTable.getData("General_Data", "Currency_Symbol");
        waitforPresenceOfElementLocated(getObject(strAccountName));
        if (isElementDisplayed(getObject(strAccountName), 5)) {
            click(getObject(strAccountName), "Account on home page");
            if (isElementDisplayed(getObject(strBlueCardCurrencySymbol), 5)) {
                strVal = getText(getObject(strBlueCardCurrencySymbol));
                if (strVal.contains(strCurrSymbol)) {
                    report.updateTestLog("verifyCurrencySymbolOnBlueCard", "Currency Symbol on blue card " + strCurrSymbol + " is validated successfully", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyCurrencySymbolOnBlueCard", "Failed to validate Currency Symbol on blue card " + strCurrSymbol, Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("verifyCurrencySymbolOnBlueCard", "Object '" + strAccountName + "' does not exist ", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify IBAN number on Blue card
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyIBANNumberOnBlueCard() throws Exception {
        String strObjectName = "";
        String strAccType = dataTable.getData("General_Data", "Account_Type");

        //Capturing object based on type of account
        if (strAccType.equalsIgnoreCase("Current Account")) {
            strObjectName = deviceType() + "myAccount.lblBlueCardIBANnumber_Current";
        } else if (strAccType.equalsIgnoreCase("Savings Account")) {
            strObjectName = deviceType() + "myAccount.lblBlueCardIBANnumber_Saving";
        } else if (strAccType.equalsIgnoreCase("TL")) {
            strObjectName = deviceType() + "myAccount.lblBlueCardIBANnumber_Tloan";
            click(getObject("xpath~//span[text()='" + strAccType + "']"), "Account Type  :'" + strAccType + "' Clicked");
            if (isElementDisplayed(getObject(strObjectName), 5)) {
                //IBAN pattern verification
                String regExpBalance = "^(IBAN - IE)\\d{2}[A-Z]{4} \\d{6} \\d{8}";
                String strActIBAN = getText(getObject(strObjectName));
                if (Pattern.matches(regExpBalance, strActIBAN)) {
                    report.updateTestLog("verifyIBANNumberOnBlueCard", "IABN displayed with Pattern : " +
                            "IE(0-9{2 Digits})(A-Z{4 Digits}(Space)(0-9{6 Digits})(Space)(0-9{8 Digits}))", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyIBANNumberOnBlueCard", "IABN not displayed with Pattern: " +
                            "IE(0-9{2 Digits})(A-Z{4 Digits}(Space)(0-9{6 Digits})(Space)(0-9{8 Digits}))", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("VerifyIBANOnBlueCard", strAccType + " - IBAN number on blue card is not displayed", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : To Verify Account Number on blue card for UK Bookkeeping accounts from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyAccountNumberOnBlueCard(String accountName, String blueCardAccountNumber) throws Exception {
        String strVal = "";
        waitforPresenceOfElementLocated(getObject(accountName));
        if (isElementDisplayed(getObject(accountName), 5)) {
            click(getObject(accountName), "Account on home page");
            if (isElementDisplayed(getObject(blueCardAccountNumber), 5)) {
                strVal = getText(getObject(blueCardAccountNumber));
                report.updateTestLog("verifyAccountNumberOnBlueCard", " 'Account No' on blue card displayed as : " + strVal, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAccountNumberOnBlueCard", " 'Account No' on blue card is not displayed successfully", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : To Verify Account Number on blue card for UK Bookkeeping accounts from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyNSCOnBlueCard(String strAccountName, String strBlueCardAccountNumber) throws Exception {
        String strVal = "";
        waitforPresenceOfElementLocated(getObject(strAccountName));
        if (isElementDisplayed(getObject(strAccountName), 5)) {
            click(getObject(strAccountName), "Account on home page");
            if (isElementDisplayed(getObject(strBlueCardAccountNumber), 5)) {
                strVal = getText(getObject(strBlueCardAccountNumber));
                report.updateTestLog("verifyAccountNumberOnBlueCard", " 'NSC' on blue card displayed as : " + strVal, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAccountNumberOnBlueCard", " 'NSC' on blue card is not displayed successfully", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : To Verify MiniStatement from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyMiniStatement() throws Exception {
        if (!isElementDisplayed(getObject("home.tabStatement"), 5))
            report.updateTestLog("Verify MiniStatement", "MiniStatement not found on homepage", Status_CRAFT.PASS);
        else {
            report.updateTestLog("Verify MiniStatement", "MiniStatement found on homepage", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify CurrentBalance from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyCurrentBalance() throws Exception {
        String strVal = "";
        String strCurrBalance = dataTable.getData("General_Data", "Current_Balance");
        waitforPresenceOfElementLocated(getObject("home.lblCurrentBalance"));
        if (isElementDisplayed(getObject("home.lblCurrentBalance"), 5)) {
            strVal = getText(getObject("home.lblCurrentBalance"));
            report.updateTestLog("verifyCurrentBalance", "Current Balance displayed as: " + strVal, Status_CRAFT.PASS);
            fetchTextToCompare(getObject("home.lblCurrentBalance"), strCurrBalance, "Current Balance");
        } else {
            report.updateTestLog("verifyCurrentBalance", "Current Balance is not displayed successfully", Status_CRAFT.FAIL);
        }
    }


    public void bluecardbalance_current(String s) throws Exception {
        String balance = "xpath~//*[@id='C5__QUE_4A0F2E6D283E5482213262_R1']";
        String blueBal = "xpath~//*[@class='boi_acc_b_euro boi-info-card-spacing boi-currency-formatter  ecDIB  ']";
        String bal = getText(getObject(balance));
        String blue = getText(getObject(blueBal));
        clickJS(getObject(s), "clicked on tab of current");
        Thread.sleep(2000);
        report.updateTestLog("bluecardbalance_current on", "Current Balance displayed as: " + blue, Status_CRAFT.PASS);
    }


    /**
     * Function : To Verify AvailableBalance from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyAvailableBal() throws Exception {
        String strObjectName = "";
        String strFinalBalance = "";
        String strCurrSymbol = "";
        String strBalance = "";
        String strAccountType = dataTable.getData("General_Data", "Account_Type");

        //Capturing object based on type of account
        if (strAccountType.equalsIgnoreCase("Current Account")) {
            strObjectName = deviceType() + "home.lblAvailableBalance_Current";
        } else if (strAccountType.equalsIgnoreCase("Savings Account")) {
            strObjectName = deviceType() + "home.lblAvailableBalance_Saving";
        } else if (strAccountType.equalsIgnoreCase("TL")) {
            strObjectName = "home.lblOutstandingBalance_TLoan";
        }

        if (isElementDisplayed(getObject(strObjectName), 3)) {
            strBalance = getText(getObject(strObjectName));
            //Finding Currency Symbol with respect to balance value
            if (!strBalance.contains("€")) {
                strCurrSymbol = driver.findElement(getObject(strObjectName))
                        .findElement(By.xpath("./following-sibling::span")).getText();
                strFinalBalance = strBalance + strCurrSymbol;
            } else {
                strFinalBalance = strBalance;
            }

            String regExpBalance = "^\\d,+\\.\\d{2} €";

            if (Pattern.matches(regExpBalance, strFinalBalance)) {
                report.updateTestLog("VerifyAvailableBalance", "Available Balance displayed with pattern: 0000.00 €", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyAvailableBalance", "Available Balance not displayed with pattern: 0000.00 €", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAvailableBalance", "Available Balance not displayed successfully", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify IBAN number
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyIBANnumber(String object) throws Exception {
        String strVal = "";
        String strIBANNumb = dataTable.getData("General_Data", "IBAN_Number");
        if (isElementDisplayed(getObject(object), 5)) {
            strVal = getText(getObject(object));
            report.updateTestLog("verifyIBANnumber", "IBAN number displayed with : " + strVal, Status_CRAFT.PASS);
            fetchTextToCompare(getObject(object), strIBANNumb, "IBAN Number");
        } else {
            report.updateTestLog("verifyIBANnumber", "IBAN number not displayed successfully", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify IBANnumber from home page for Mobile as well as Desktop for Current
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyIBANnumberCurrent() throws Exception {
        String strExpIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strObject = deviceType() + "home.lblIBANnumber_Current";
        if (isElementDisplayed(getObject(deviceType() + "home.lblIBANnumber_Current"), 5)) {
            fetchTextToCompare(getObject(strObject), strExpIBAN, "IBAN Number");
        } else {
            report.updateTestLog("verifyIBANnumber", "IBAN number not displayed successfully", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify BIC Number on Blue card
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyBICNumberOnBlueCard(String accountName, String blueCardBICNumber) throws Exception {
        String Val = "";
        waitforPresenceOfElementLocated(getObject(accountName));
        if (isElementDisplayed(getObject(accountName), 5)) {
            click(getObject(accountName), "Account on home page");
            if (isElementDisplayed(getObject(blueCardBICNumber), 5)) {
                Val = getText(getObject(blueCardBICNumber));
                report.updateTestLog("verifyBICNumberOnBlueCard", "BIC number on blue card displayed as : " + Val, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyBICNumberOnBlueCard", "BIC number on blue card is not displayed successfully", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : To Verify IBANnumber from home page for Mobile as well as Desktop for Current
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyIBAN_NumberHomePge() throws Exception {
        String strObjectName = "";
        String strAccountType = dataTable.getData("General_Data", "Account_Type");

        //Capturing object based on type of account
        if (strAccountType.equalsIgnoreCase("Current Account")) {
            strObjectName = deviceType() + "home.lblIBANnumber_Current";
        } else if (strAccountType.equalsIgnoreCase("Savings Account")) {
            strObjectName = deviceType() + "home.lblIBANnumber_Saving";
        } else if (strAccountType.equalsIgnoreCase("TL")) {
            strObjectName = deviceType() + "home.lblIBANnumber_TLoan";
        }

        if (isElementDisplayed(getObject(strObjectName), 5)) {
            //IBAN pattern verification
            String regExpBalance_ROI = "^(IE)\\d{2}[A-Z]{4} \\d{6} \\d{8}";
            String regExpBalance_GB = "^(GB)\\d{2}[A-Z]{4} \\d{6} \\d{8}";
            String strActIBAN = getText(getObject(strObjectName));
            if ((Pattern.matches(regExpBalance_ROI, strActIBAN)) || Pattern.matches(regExpBalance_GB, strActIBAN)) {
                report.updateTestLog("VerifyIBAN_NumberHomePge", "IABN displayed with Pattern : " +
                        "IE(0-9{2 Digits})(A-Z{4 Digits}(Space)(0-9{6 Digits})(Space)(0-9{8 Digits}))", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyIBAN_NumberHomePge", "IABN not displayed with Pattern: " +
                        "IE(0-9{2 Digits})(A-Z{4 Digits}(Space)(0-9{6 Digits})(Space)(0-9{8 Digits}))", Status_CRAFT.FAIL);
            }

        } else {
            report.updateTestLog("VerifyIBANOnBlueCard", strAccountType + " - IBAN number on blue card is not displayed", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify account tile is clickable
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyAccountTileClickable(String strAccountName) throws Exception {
        if (isElementDisplayed(getObject(strAccountName), 5)) {
            click(getObject(strAccountName), "Loan account tile");
            report.updateTestLog("verifyLoanAccountTileClickable", "Loan account tile is clickable", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyLoanAccountTileClickable", "Loan account tile is not clickable", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify Deposit Label
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyDepositLabel(String strLabel) throws Exception {
        String strDepositLabel = "";
        waitforPresenceOfElementLocated(getObject(strLabel));
        if (isElementDisplayed(getObject(strLabel), 5)) {
            strDepositLabel = getText(getObject(strLabel));
            int intLength = strDepositLabel.length();
            String strDigits = strDepositLabel.substring(intLength - 4, intLength);
            int strActualDigits = Integer.parseInt(strDigits);
            if (strDepositLabel.contains(("DEPOSIT ~ "))) {
                report.updateTestLog("verifyDepositLabel", "Label for Deposit account  is displayed " + strDepositLabel, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyDepositLabel", "Label for Deposit account  is not displayed", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : To Verify Deposit Balance from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyDepositeBalance(String strAccountName) throws Exception {
        String strVal = "";
        String strAvailBalance = dataTable.getData("General_Data", "Available_Balance");
        waitforPresenceOfElementLocated(getObject(strAccountName));
        if (isElementDisplayed(getObject(strAccountName), 10)) {
            strVal = getText(getObject(strAccountName));
            report.updateTestLog("verifyDepositeBalance", "Available Balance displayed is : " + strVal, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyDepositeBalance", "Available Balance not displayed successfully", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify element is not clickable
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyTileNotClickable(String strElementName) throws Exception {
        if (isElementDisplayed(getObject(strElementName), 5)) {
            click(getObject(strElementName), "Clicked on the item : " + strElementName);
            if (isElementDisplayed(getObject(strElementName), 5)) {
                report.updateTestLog("verifyTileNotClickable", "Label/element '" + strElementName + "' is not clickable", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyTileNotClickable", "Label/element '" + strElementName + "' is clickable", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyTileNotClickable", "Label/element '" + strElementName + "' is not present on screen", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify Element's position vertically against another element
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyWithDrawLinkAgainstBalance(String elementBalance, String elementWithdraw) throws Exception {
        if (isElementDisplayed(getObject(elementWithdraw), 5)) {
            if (isElementDisplayed(getObject(elementBalance), 5)) {
                int intBalanceY = driver.findElement(getObject(elementBalance)).getLocation().getY();
                int intWithdrawY = driver.findElement(getObject(elementWithdraw)).getLocation().getY();
                if (intBalanceY < intWithdrawY) {
                    report.updateTestLog("verifyWithDrawLinkAgainstBalance", "Label/element '" + elementWithdraw + "' is below element '" + elementBalance + "'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyWithDrawLinkAgainstBalance", "Label/element '" + elementWithdraw + "' is below element '" + elementBalance + "'", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("verifyWithDrawLinkAgainstBalance", "Label/element '" + elementBalance + "' is not present on screen", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyWithDrawLinkAgainstBalance", "Label/element '" + elementWithdraw + "' is not present on screen", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify information tab is not displayed after clicking on account tab
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyInformationTabNotDisplayed(String strAccountName, String strInformationTab) throws Exception {
        waitforPresenceOfElementLocated(getObject(strAccountName));
        if (isElementDisplayed(getObject(strAccountName), 5)) {
            click(getObject(strAccountName), "Account on home page");
            if (isElementDisplayed(getObject(strInformationTab), 5)) {
                report.updateTestLog("verifyInformationTabNotDisplayed", "Information tab is not displayed ", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyInformationTabNotDisplayed", "Information tab is displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyInformationTabNotDisplayed", "Element '" + strAccountName + "' os not displayed", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify Saving account is not displayed from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifySavingAcctNotDisplayed() throws Exception {
        String strVal = "";
        String strNickName = dataTable.getData("General_Data", "Nickname");
        strVal = getText(getObject(deviceType() + "home.lblNickname_Saving"));
        if (strVal.equalsIgnoreCase(strNickName)) {
            report.updateTestLog("verifySavingAcctNotDisplayed", "Saving account is displayed : " + strVal, Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifySavingAcctNotDisplayed", "Saving account is not displayed ", Status_CRAFT.PASS);
        }
    }

    /**
     * Function : To Verify Saving account is not displayed from home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyDepositAcctNotDisplayed() throws Exception {
        String strVal = "";
        String strNickName = dataTable.getData("General_Data", "Nickname");
        strVal = getText(getObject(deviceType() + "home.lblDeposit"));
        if (strVal.equalsIgnoreCase(strNickName)) {
            report.updateTestLog("verifySavingAcctNotDisplayed", "Saving account is displayed : " + strVal, Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifySavingAcctNotDisplayed", "Saving account is not displayed ", Status_CRAFT.PASS);
        }
    }

    /**
     * Function : To Verify Account Type is Position in The Group On Home Page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyPositionInTab(String strParentObjectName) throws IOException {
        String strAccountName = dataTable.getData("General_Data", "Account_Grouped");
        Integer accountPosition = Integer.parseInt(dataTable.getData("General_Data", "AccountPosition_Grouped"));
        int intIndex = 0;
        String strChildElmValue = "";
        List<WebElement> childRows = driver.findElements(getObject(strParentObjectName));

        if (childRows.size() > 1) {
            intIndex = 1;
            for (WebElement childElement : childRows) {
                strChildElmValue = childElement.getText().toUpperCase();
                if (strChildElmValue.contains(strAccountName.toUpperCase())) {
                    intIndex = intIndex;
                    break;
                }
                intIndex = intIndex++;
            }
            if (accountPosition.intValue() == intIndex) {
                report.updateTestLog("verifyCheckPosition", "Position for Account Type '" + strAccountName + "' is as expected at index :" + intIndex, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyCheckPosition", "Position for Account Type '" + strAccountName + "' is as not expected, expected index :" + accountPosition.intValue() + ", actual indeix :" + intIndex, Status_CRAFT.FAIL);
            }

        }
    }

    /**
     * Function : To Verify Account Type Grouping on home page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyAccountTypeGrouping(String strParentObjectName) throws Exception {
        boolean bflag = true;
        String accountName = dataTable.getData("General_Data", "Account_Grouped");
        String accountNameColor = "";
        if (accountName.contains("Saving")) {
            accountNameColor = "rgb(215, 198, 230)";
        } else if (accountName.contains("Current")) {
            accountNameColor = "rgb(129, 188, 41)";
        } else if (accountName.contains("MORTGAGE")) {
            accountNameColor = "rgb(255, 255, 255)";
        } else if (accountName.contains("Term Loan")) {
            accountNameColor = "rgb(16, 129, 166)";
        } else if (accountName.contains("Card")) {
            accountNameColor = "rgb(88, 190, 219)";
        } else if (accountName.contains("Loan")) {
            accountNameColor = "rgb(255, 255, 255)";
        }

        List<WebElement> childRows = driver.findElements(getObject(strParentObjectName));
        String objProperty = getObjectStoredProperty(strParentObjectName);
        if (childRows.size() > 1) {
            int intIndex = 1;
            for (int i = 0; i <= childRows.size() - 1; i++) {
                WebElement wel = childRows.get(i);
                String strChildElmValue = wel.getCssValue("border-color");
                if (strChildElmValue.contains(accountNameColor)) {
                    if (bflag == true) {
                        List<WebElement> childRows2 = driver.findElements(getObject(strParentObjectName));
                        for (int j = i; j < childRows2.size(); j++) {
                            WebElement wel2 = childRows2.get(j);
                            String strChildElmValue2 = wel2.getCssValue("border-color");
                            String strChildElmName2 = wel2.getText();
                            if (strChildElmValue2.contains(accountNameColor)) {
                                report.updateTestLog("verifyAccountTypeGrouping", "Account '" + strChildElmName2 + "' found at number : " + (j + 1), Status_CRAFT.PASS);
                                intIndex = j + 1;
                                bflag = true;
                            } else {
                                bflag = false;
                                intIndex = j;
                                i = intIndex;
                                break;
                            }
                        }
                    } else {
                        report.updateTestLog("verifyAccountTypeGrouping", "Grouping for Account Type '" + accountName + "' is unsuccessful,found again at index :" + intIndex, Status_CRAFT.FAIL);
                        break;
                    }
                } else {
                    intIndex = intIndex + 1;
                }
                if (i == childRows.size() - 1) {
                    bflag = true;
                }
            }
        }
        if (bflag == true) {
            report.updateTestLog("verifyAccountTypeGrouping", "Grouping and color coding for Account Type '" + accountName + "' is successful", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAccountTypeGrouping", "Grouping and color coding for Account Type '" + accountName + "' is not successful", Status_CRAFT.FAIL);
        }
    }


    /**
     * Function : To Verify Details of a particular section/element
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyElementDetails(String strSectionElement) throws Exception {
        String[] arrValidation = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String dataSectionUI = "";
        String strValidateHead = "";
        String strValidateData = "";
        if (isElementDisplayed(getObject(strSectionElement), 5)) {
            scrollToView(getObject(strSectionElement));
            dataSectionUI = getText(getObject(strSectionElement)).toUpperCase();

            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                strValidateHead = arrValidation[intValidate].split("::")[0];
                strValidateData = arrValidation[intValidate].split("::")[1];
                if (strValidateData.contains("~")) {
                    strValidateData = "~" + strValidateData.split("~")[1];
                }
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

    /**
     * Function : To Verify Particular Menu Tab not present upon clicking an AccountType
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyMenuTabNotPresent() throws Exception {
        String strAccType = dataTable.getData("General_Data", "Account_Type");
        String strTabName = dataTable.getData("General_Data", "TabName");
        if (isElementDisplayed(getObject("xpath~//span[text()='" + strAccType + "']"), 5)) {
            click(getObject("xpath~//span[text()='" + strAccType + "']"), "Account Type  :'" + strAccType + "' Clicked");
            if (!isElementDisplayed(getObject("xpath~//span[text()='" + strTabName + "']"), 5)) {
                report.updateTestLog("verifyMenuTabNotPresent", "Menu Tab '" + strTabName + " ' is not displayed after clicking Account Type " + strAccType, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyMenuTabNotPresent", "Menu Tab '" + strTabName + " ' is displayed after clicking Account Type " + strAccType, Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAccInfoNotPresent", "Account Type : '" + strAccType + " ' is not present", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify Blue card for a Particular Account Type
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void ClkAccTypeAndValidateBlueCardDetails() throws Exception {
        boolean bflag = false;
        String accType = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strIterationForAccountType = dataTable.getData("General_Data", "IterationFlag");
        //isMobile>>TODO need to check the imapact:-Rajiv
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            strAccountIBAN = "~ " + dataTable.getData("General_Data", "IBAN_Number").substring(strAccountIBAN.length() - 4);
        }
        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(deviceType() + "home.lblAccountListRows"));
        for (int j = 0; j < oUIRows.size(); j++) {

            String strRowText = oUIRows.get(j).getText().toUpperCase();
            if ((strRowText.contains(accType.toUpperCase())) && (strRowText.contains(strAccountIBAN.toUpperCase()))) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                Thread.sleep(3000);
                report.updateTestLog("verifyAccountStatement_AllAccounts", "Account name'" + accType + "',with details '" + strAccountIBAN + "'", Status_CRAFT.SCREENSHOT);
                js.executeScript("arguments[0].click();", oUIRows.get(j));
                report.updateTestLog("verifyAccountStatement_AllAccounts", "Account click with details as Account name'" + accType + "',with details '" + strAccountIBAN + "'", Status_CRAFT.PASS);
                bflag = true;
                //use the loop as there are 3 elements showing on HTML, but on UI it is only 1
                //isMobile>>TODO need to check the imapact:-Rajiv
                if (deviceType.equalsIgnoreCase("MobileWeb")) {

                    if (strIterationForAccountType.equalsIgnoreCase("BlockToAll")) {
                        if (!isElementDisplayed(getObject(deviceType() + "home.lblNameAvilableFundsText"), 5)) {
                            report.updateTestLog("verifyBlueCardDetailsForAccType", "Available fund is not displayed for account with Overdraft set and status with 'Blocked to All'.", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("verifyBlueCardDetailsForAccType", "Available fund is displayed for account with Overdraft set and status with 'Blocked to All'.", Status_CRAFT.FAIL);
                        }
                    }
                } else {
                    String strAccountBal = strRowText.split("\n")[2];
                    validateBlueCardDetails(strAccountIBAN, strAccountBal); //-By Rajiv
                }
                break;
            }
        }
        if (!bflag) {
            report.updateTestLog("ClkAccTypeAndValidateBlueCardDetails", "Account Type : '" + accType + " ' is not present", Status_CRAFT.FAIL);
        }

    }

    public void validateBlueCardDetails(String strIban, String strAccBal) throws Exception {
        String strBlueCardUIText = getText(getObject(deviceType() + "myAccount.sectionBlueCard"));
        String strJurisdictionType = dataTable.getData("General_Data", "JurisdictionType");
        String strUiAccBAl = strBlueCardUIText.split("\n")[0].split(" ")[0] + strBlueCardUIText.split("\n")[0].split(" ")[1];
        if (!strIban.contains("***")) {
            if (!strJurisdictionType.equals("") && !strJurisdictionType.equals("ROI")) {
                String strNsc = strIban.split(" ")[1];
                String strAccNo = strIban.split(" ")[2];
                if (strBlueCardUIText.toUpperCase().contains("NSC - " + strNsc)) {
                    report.updateTestLog("validateBlueCardDetails", "NSC '" + strNsc + "' is correctly displayed on blue card", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("validateBlueCardDetails", "NSC '" + strNsc + "' is not correctly displayed on blue card", Status_CRAFT.FAIL);
                }
                if (strBlueCardUIText.contains("Account No - " + strAccNo)) {
                    report.updateTestLog("validateBlueCardDetails", "Account No. '" + strAccNo + "' is correctly displayed on blue card", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("validateBlueCardDetails", "Account No. '" + strAccNo + "' is not correctly displayed on blue card", Status_CRAFT.FAIL);
                }
            } else {
                if (strBlueCardUIText.toUpperCase().contains("IBAN - " + strIban)) {
                    report.updateTestLog("validateBlueCardDetails", "IBAN '" + strIban + "' is correctly displayed on blue card", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("validateBlueCardDetails", "IBAN '" + strIban + "' is not correctly displayed on blue card", Status_CRAFT.FAIL);
                }
            }

        }

        if (strUiAccBAl.contains(strAccBal)) {
            report.updateTestLog("validateBlueCardDetails", "Account balance '" + strAccBal + "' is correctly displayed on blue card", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateBlueCardDetails", "Account balance '" + strAccBal + "' is not correctly displayed on blue card", Status_CRAFT.FAIL);
        }

    }

    public void blueCardValidate() throws Exception {
        if (deviceType.equals("Web")) {
            displayElementText(deviceType() + "myAccount.sectionBlueCard");
            //verifyElementDetails(deviceType() + "myAccount.sectionBlueCard");
        } else {
            //Unfold/Fold Functionality
            driver.findElement(By.xpath("//a[@id='boi_share_toggle'][text()='BIC\\IBAN']")).click();
            Thread.sleep(5000);
            displayElementText(deviceType() + "myAccount.sectionBlueCard");
            //verifyElementDetails(deviceType() + "myAccount.sectionBlueCard");
        }
    }

    /**
     * Function : To Verify element not present
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyElementNotPresent(String ElementView) throws Exception {
        if (!isElementDisplayed(getObject(ElementView), 5)) {
            report.updateTestLog("VerifyMortgage Not Present", " Mortgage not found", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyMortgage Not Present", " Mortgage Account found", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify the welcome card details
     * Update on    Updated by     Reason
     * 15/04/2019   C966055        Mobile automation
     */
    public void verifyWelcomeCardMessage() throws Exception {

        String strFirstName = dataTable.getData("General_Data", "FirstName");
        String strWelcomeFirstName = getText(getObject(deviceType() + "home.lblFirstName"));
        if (strWelcomeFirstName.equalsIgnoreCase(strFirstName)) {
            report.updateTestLog("verifyWelcomeCardMessage", "The first name displayed on welcome card is " + strWelcomeFirstName + " which is as expected", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyWelcomeCardMessage", "The first name is not correctly displayed on welcome card", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "home.lblLastLogin"), 5)) {
            String strValCaptured = getText(getObject(deviceType() + "home.lblLastLogin"));
            report.updateTestLog("verifyWelcomeCardMessage", "Last Login is displayed with value:-" + strValCaptured, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyWelcomeCardMessage", "Last Login is not displayed", Status_CRAFT.FAIL);
        }

        //Last payee should not be displayed for mobile (negative check)
        if (deviceType.equalsIgnoreCase("MobileWeb") || deviceType.equalsIgnoreCase("TabletWeb")) {
            if (isElementDisplayed(getObject("home.lblLastPayeeadded"), 5)) {
                String strValCaptured = getText(getObject("home.lblLastPayeeadded"));
                report.updateTestLog("verifyWelcomeCardMessage", "Last Payee displayed with Value:-" + strValCaptured, Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyWelcomeCardMessage", "Last Payee not displayed on card which is as expected", Status_CRAFT.PASS);
            }
        } else if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("home.lblLastPayeeadded"), 5)) {
                String strValCaptured = getText(getObject("home.lblLastPayeeadded"));
                String strValCapturedTime = getText(getObject("home.lblLastPayeeaddedTime"));
                report.updateTestLog("verifyWelcomeCardMessage", "Last Payee Displayed with Value:-" + strValCaptured, Status_CRAFT.DONE);
                report.updateTestLog("verifyWelcomeCardMessage", "Last Payee added Time Displayed with Value:-" + strValCapturedTime, Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyWelcomeCardMessage", "Last payee not displayed on card", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : To Verify All the Relevant Details from the Blue Card
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyBlueCardDetails(String strBlueCardSection, String strAccountSection) throws Exception {
        String strAccountName = dataTable.getData("General_Data", "Nickname");
        String strBalance = dataTable.getData("General_Data", "Currency_Symbol") + " " + dataTable.getData("General_Data", "Available_Balance");
        String strIbanNumber = dataTable.getData("General_Data", "IBAN_Number");
        String strBicNumber = dataTable.getData("General_Data", "BIC_Number");

        waitforPresenceOfElementLocated(getObject(strAccountSection));
        click(getObject(strAccountSection), "Account on home page");
        if (isElementDisplayed(getObject(strBlueCardSection), 5)) {
            String blueCardSection = getText(getObject(strBlueCardSection)).toUpperCase();
            if (strBalance != "") {
                if (blueCardSection.contains(strBalance.toUpperCase())) {
                    report.updateTestLog("verifyBlueCardDetails", "Account balance '" + strBalance + "' is displayed correctly on Blue Card. ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyBlueCardDetails", "Account balance '" + strBalance + "' is not displayed correctly on Blue Card. ", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("verifyBlueCardDetails", "Account Balance passed from data sheet is empty", Status_CRAFT.WARNING);
            }

            //Check the IBAN on Blue Card
            if (strIbanNumber != "") {
                if (blueCardSection.contains(strIbanNumber.toUpperCase())) {
                    report.updateTestLog("verifyBlueCardDetails", "Account IBAN '" + strIbanNumber + "' is displayed correctly on Blue Card. ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyBlueCardDetails", "Account IBAN '" + strIbanNumber + "' is not displayed correctly on Blue Card. ", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("verifyBlueCardDetails", "Account IBAN passed from data sheet is empty", Status_CRAFT.WARNING);
            }
            //Check the BIC number
            if (strBicNumber != "") {
                if (blueCardSection.contains(strBicNumber.toUpperCase())) {
                    report.updateTestLog("verifyBlueCardDetails", "Account BIC '" + strBicNumber + "' is displayed correctly on Blue Card. ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyBlueCardDetails", "Account BIC '" + strBicNumber + "' is not displayed correctly on Blue Card. ", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("verifyBlueCardDetails", "Account BIC passed from data sheet is empty", Status_CRAFT.WARNING);
            }
        } else {
            report.updateTestLog("verifyBlueCardDetails", " The Blue Card Section : '" + strBlueCardSection + "' is not displayed ..Please Check..!!", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To Verify the link presences and Its Navigation
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyLinkAndItsNavigation() throws Exception {
        String strLinkToCheck = dataTable.getData("General_Data", "Properties_Variable");
        String strLinkToVerify = dataTable.getData("General_Data", "VerifyDetails");
        String strLinkText = "";
        String strCustomerRegion = "";
        if (isElementDisplayed(getObject(strLinkToCheck), 5)) {
            strLinkText = getText(getObject(strLinkToCheck));
            report.updateTestLog("verifyLink", "Link '" + strLinkText + "' is displayed correctly.", Status_CRAFT.PASS);
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((strLinkToCheck))));
            js.executeScript("arguments[0].click();", driver.findElement(getObject((strLinkToCheck))));
            verifyNewlyOpenedTab(strLinkToVerify);
            js.executeScript("arguments[0].click();", driver.findElement(getObject((strLinkToCheck))));
            if (deviceType() == "mw." | deviceType() == "tw." | isMobile) {
                String link = driver.findElement(getObject(strLinkToCheck)).getAttribute("href");
                if (link.equals(strLinkToVerify)) {
                    report.updateTestLog("verifyNewlyOpendTab", " Newly Opened Tab Opens the URL" + link, Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyNewlyOpendTab", " New Tab did not opened successfully..Please check...!", Status_CRAFT.FAIL);
                }
            } else {
                verifyNewlyOpenedTab(strLinkToVerify);
            }

        } else {
            strCustomerRegion = dataTable.getData("General_Data", "Account_Type");
            if (strCustomerRegion.equalsIgnoreCase("ROI")) {
                report.updateTestLog("verifyLinkAndItsNavigation", "Link for : UK Saving Rates Not found on home page for ROI customer.", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyLinkAndItsNavigation", "The expected link not found....Please Check..!!", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : To Verify the link presences and It's Navigation for negative scenario
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyLinkAndItsNavigationNegative() throws Exception {
        String strLinkToCheck = dataTable.getData("General_Data", "Properties_Variable");
        String strLinkToVerify = dataTable.getData("General_Data", "VerifyDetails");

        if (isElementDisplayed(getObject(strLinkToCheck), 5)) {
            report.updateTestLog("verifyLink", "Link is displayed", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyLink", "The respective Link is not displayed as expected.", Status_CRAFT.PASS);
        }
    }

    /**
     * Function : To Verify the content of any PopUp
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyPopUpDetails() throws Exception {
        String strContentToCheck = dataTable.getData("General_Data", "FirstName");
        String[] arrContentToCheck = strContentToCheck.split("::");
        String strTitleXpath = "";
        String strContentXpath = "";
        report.updateTestLog("verifyPopUpAndClickOnLink", "The Title and Content ", Status_CRAFT.SCREENSHOT);
        if (deviceType().equalsIgnoreCase("w.")) {
            strTitleXpath = "xpath~//div[@class='ecDIB  boi-popup-dialog__title']/*[text()='" + arrContentToCheck[0] + "']";
        } else {
            strTitleXpath = "xpath~//div[@id='EDGE_CONNECT_PROCESS']/descendant::span[@id='ui-id-1'][text()='" + arrContentToCheck[0] + "']";
        }

        isElementDisplayedWithLog(getObject(strTitleXpath), "The title of the Dialog", "Pop up", 25);
        for (int i = 1; i < arrContentToCheck.length; i++) {
            strContentXpath = "xpath~//div[@class='col-hidden tc-popup-dialog tc-popup-modal noSwipe ui-dialog-content ui-widget-content']/descendant::*[text()='" + arrContentToCheck[i] + "']";
            isElementDisplayedWithLog(getObject(strContentXpath), "The Content of the Dialog :: " + arrContentToCheck[i], "Pop up", 25);
        }
    }

    /**
     * Function : Method to click on button --> validate the Pop Up Content--> Click on Any link on pop  :: CFPUR-32
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */


    public void verifyPreLoginDetails(String strLinkToClick, String strDlgToValidate, String copycontent) throws Exception {
        clickJS(getObject(strLinkToClick), "security concerns");
        if (isElementDisplayed(getObject(strLinkToClick), 5)) {
            click(getObject(strLinkToClick));
            if (getText(getObject(strDlgToValidate)).equals("Stay safe and secure")) {
                getText(getObject(copycontent)).contains("We will never email you requesting your online details. Please report any suspicious emails to 365security@boi.com.");
                report.updateTestLog("verify  safe and secure pop-up", "pop-up content verified", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verify  safe and secure pop-up", "pop-up content not verified", Status_CRAFT.FAIL);
            }

        }

    }

    public void verifyPopUpAndClickOnLink(String strLinkToClick, String strDlgToValidate, String strlinkToClickOnPopUp) throws Exception {
        String strLinkToVerify = dataTable.getData("General_Data", "VerifyDetails");
        String strContentToCheck = dataTable.getData("General_Data", "FirstName");
        String[] arrContentToCheck = strContentToCheck.split("::");
        if (isElementDisplayed(getObject(strLinkToClick), 5)) {
            clickJS(getObject(strLinkToClick), "Logout link clicked");
            if (isElementDisplayed(getObject(strDlgToValidate), 10)) {
                verifyPopUpDetails();
                clickJS(getObject(strlinkToClickOnPopUp), "Logout Dialog Option");
            } else {
                report.updateTestLog("verifyPopUpAndClickOnLink", "Object " + strDlgToValidate + " Not Found", Status_CRAFT.FAIL);

            }
            if (strLinkToVerify.contains("https://")) {
                verifyNewlyOpenedTab(strLinkToVerify);
            }
        }
    }

    /**
     * Function : Verify the register now link :: CFPUR-32
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyRegisterNowLink(String strLinkToClick) throws Exception {
        String strLinkToVerify = dataTable.getData("General_Data", "VerifyDetails");
        if (isElementDisplayed(getObject(strLinkToClick), 5)) {
            click(getObject(strLinkToClick));
            if (strLinkToVerify.contains("https://")) {
                if (isMobile) {
                    verifyHrefLink(strLinkToVerify, strLinkToClick);
                } else {
                    verifyNewlyOpenedTab(strLinkToVerify);
                }
            }
        }
    }

    /**
     * Function : Method to verify the Newly opend tab and its URL
     * Created on    Created by     Reason
     * 31/03/2020   C966119
     */
    public void verifyHrefLink(String strExpectedLink, String strObjectName) throws Exception {
        String strLink = "";
        try {
            strLink = driver.findElement(getObject(strObjectName)).getAttribute("href");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strLink == null) {
            strLink = driver.findElement(getObject(strObjectName)).getAttribute("data-target");
        }
        if (strLink.equals(strExpectedLink)) {
            report.updateTestLog("verifyHrefLink", " The Href link is matching with expected. Actual : " + strLink + " : Expected : " + strExpectedLink, Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyHrefLink", " The Href link is NOT matching with expected. Actual : " + strLink + " : Expected : " + strExpectedLink, Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Method to verify the Newly opend tab and its URL
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     * 17/10/2019   C966003        Removed driver.close,  added logic for window handle
     */
    public void verifyNewlyOpenedTab(String strLinkToVerify) throws Exception {
        waitForPageLoaded();
        ArrayList<String> arrTabs = new ArrayList<String>(driver.getWindowHandles());
        if (arrTabs.size() > 0) {
            driver.switchTo().window(arrTabs.get(arrTabs.size() - 1));
            waitForPageLoaded();
            waitForJQueryLoad();
            String linkURL = driver.getCurrentUrl();
            waitForPageLoaded();
            //strTextToCompare(strLinkToVerify, linkURL, "new tab with url");
            if (linkURL.contains(strLinkToVerify)) {
                report.updateTestLog("verifyNewlyOpendTab", " Newly opened tab successfully matching with correct url. Expected : " + strLinkToVerify + "Actual : " + linkURL, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyNewlyOpendTab", " Newly opened tab Is NOT matching with correct url. Expected : " + strLinkToVerify + "Actual : " + linkURL, Status_CRAFT.FAIL);
            }
            if (isMobile) {
                driver.context("WEBVIEW_com.bankofireland.tcmb");
                driver.navigate().back();
            }
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
            driver.switchTo().window(arrTabs.get(0));
            waitForJQueryLoad();
            waitForPageLoaded();

        } else {
            report.updateTestLog("verifyNewlyOpendTab", " New Tab did not opened successfully..Please check...!", Status_CRAFT.FAIL);
        }
    }


    /**
     * Function : To logout of B365
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifylogout(String logOut) throws Exception {
        clickJS(getObject(logOut), "Clicking on logout pop-up");
        if (deviceType().equals("mw.")) {
            WebElement slide = driver.findElementById("mw.home.lblSlideLogout");

            String subWindowHandler = null;
            Set<String> handles = driver.getWindowHandles(); // get all window handles
            Iterator<String> iterator = handles.iterator();
            while (iterator.hasNext()) {
                subWindowHandler = iterator.next();
            }
            driver.switchTo().window(subWindowHandler);
            click(getObject("mw.home.lblPopLogout"));
        }
        click(getObject(deviceType() + "home.btnPopLogout"));
        String expectedlogoutmsg = "You have been logged out.";
        String actualLogoutMsg = getText(getObject("xpath~//div[contains(@class,'block boi_popup_title')]/div"));
        if (actualLogoutMsg.equals(expectedlogoutmsg)) {
            report.updateTestLog("VerifyLogout", "Logging out of the  Application: ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyLogout", "Logging out failed of the  Application: ", Status_CRAFT.FAIL);
        }

    }

    /**
     * Function : To logout of B365
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifylogoutTimeOut() throws Exception {
        String strActualtimeOutPopUpMsg = "";
        String strExpectedtimeOutPopUpMsg = "Continue session";
        String strTypeOfCheck = dataTable.getData("General_Data", "VerifyDetails");
        String linkToclick = dataTable.getData("General_Data", "Properties_Variable");
        isElementDisplayed(getObject(deviceType() + "home.lbltimeoutPopOut"), 240);
        strActualtimeOutPopUpMsg = getText(getObject(deviceType() + "home.lbltimeoutPopOut"));
        if (strActualtimeOutPopUpMsg.equals(strExpectedtimeOutPopUpMsg)) {
            report.updateTestLog("Verify TimeOut Pop-Up", "TimeOut Pop-up is present for the  Application: ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Verify TimeOut Pop-Up", "TimeOut Pop-up is not present for the  Application: ", Status_CRAFT.FAIL);
        }
        if (strTypeOfCheck.equalsIgnoreCase("PositiveFlow")) {
            click(getObject(deviceType() + "home.lbltimeoutPopOut"));
            report.updateTestLog("TimeOut Pop-Up", "TimeOut Pop-up is clicked for the  Stay Alive: ", Status_CRAFT.PASS);
        } else if (strTypeOfCheck.equalsIgnoreCase("NegativeFlow")) {
            if (isElementDisplayed(getObject(deviceType() + "home.lbltimeoutPopOut"), 60)) {
                click(getObject(deviceType() + linkToclick));
                isElementDisplayedWithLog(getObject("w.home.lblAccountOverview"), "Button :: 'No, keep me logged in'", "Log Out Pop up", 25);
            }
        } else if (strTypeOfCheck.equalsIgnoreCase("AutoLogout")) {
            String timeOutMessage = "Your session is about to end.";
            Thread.sleep(1000);
            String actualErrorMsg = getText(getObject(deviceType() + "home.lbltimeoutSession"));
            if (actualErrorMsg.contains(timeOutMessage)) {
                report.updateTestLog("Verify TimeOut", "TimeOut message '" + actualErrorMsg + "' is displayed", Status_CRAFT.PASS);

            } else {
                report.updateTestLog("Verify TimeOut", "TimeOut  message is not displayed correctly: ", Status_CRAFT.FAIL);
            }
        }
    }

    public void sessionTimeOut() throws InterruptedException {
        //Scripts needs to wait for 1 minutes to verify the session time out page
        Thread.sleep(60000);
        if (isElementDisplayed(getObject(deviceType() + "home.lbltimeoutHeader"), 1)) {
            report.updateTestLog("Verify TimeOut", "TimeOut header Session Time Out is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Verify TimeOut", "TimeOut header Session Time Out is not displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject(deviceType() + "home.lbltimeoutText"), 1)) {
            report.updateTestLog("Verify TimeOut", "TimeOut message Your session timed out. Log in to continue. is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Verify TimeOut", "TimeOut message Your session timed out. Log in to continue. is displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject(deviceType() + "home.btnLogin"), 1)) {
            report.updateTestLog("Verify TimeOut", "Login button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Verify TimeOut", "Login button is not displayed", Status_CRAFT.FAIL);
        }
    }


    /*CFPUR-1162*/
    public void verifyLostCardAndStolenPage() throws Exception {
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject(deviceType() + "home.btnServiceDesk")));
            clickJS(getObject(deviceType() + "home.btnServiceDesk"), "Service Desk ");
        } else if (deviceType.equalsIgnoreCase("Web")) {
            click(getObject(deviceType() + "home.btnServiceDesk"));
        }

        waitForElementPresent(getObject(deviceType() + "home.lnkLostAndStolen"), 15);
        clickJS(getObject(deviceType() + "home.lnkLostAndStolen"), "Lost and Stolen link");

        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "home.lostAndStolenPageHeader"), 5)) {
                report.updateTestLog("verifycardLostAndStolen", "Header 'Lost or stolen card' is correctly displayed on UI", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifycardLostAndStolen", "Existing Header on UI is incorrect", Status_CRAFT.FAIL);
            }
        }

        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject(deviceType() + "home.lostAndStolenPageHeader"), 5)) {
                report.updateTestLog("verifycardLostAndStolen", "Header 'Lost or stolen card' is correctly displayed on UI", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifycardLostAndStolen", "Existing Header on UI is incorrect", Status_CRAFT.FAIL);
            }
        }

        if (isElementDisplayed(getObject("home.lostAndStolenPageLabel"), 5)) {
            String label1 = getText(getObject("home.lostAndStolenPageLabel"), 3);
            report.updateTestLog("verifycardLostAndStolen", "Label " + label1 + " is correctly displayed on UI", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifycardLostAndStolen", "Existing Label on UI is incorrect", Status_CRAFT.FAIL);
        }

        String strContentToCheck = dataTable.getData("General_Data", "VerifyContent");
        String[] arrContentToCheck = strContentToCheck.split("::");
        int intFoundIndex = 0;
        List<WebElement> oUIRows = findElements(getObject("home.lostandStolenSection"));
        report.updateTestLog("verifyCheckContentPresent", "The Content displayed screenshot", Status_CRAFT.SCREENSHOT);
        for (int i = 0; i < arrContentToCheck.length; i++) {
            int j = intFoundIndex;
            for (j = intFoundIndex; j < oUIRows.size(); j++) {
                if (oUIRows.get(j).getText().contains(arrContentToCheck[i])) {
                    intFoundIndex = j;
                    report.updateTestLog("verifyCheckContentPresent", "The Content - " + arrContentToCheck[i] + " is correctly displayed on UI", Status_CRAFT.PASS);
                    break;
                } else if (!(j == intFoundIndex)) {
                    report.updateTestLog("verifyCheckContentPresent", "The Content - " + arrContentToCheck[i] + " is not displayed on UI", Status_CRAFT.FAIL);
                    break;
                }
            }
        }
    }


    /**
     * Function : Verify Order of accounts on mobile and desktop On Home Page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyAccountOrdering() throws Exception {
        String strExpSequence = dataTable.getData("General_Data", "SequenceAccType");
        String strParentobject = deviceType() + "home.AccountListRows";
        String[] arrExpectedOrdering = strExpSequence.split(";");
        int intEndOfIndex = 0;
        int intElementFoundIndex = 0;
        if (isElementDisplayed(getObject(deviceType() + "home.lnkShowmoreacc"), 20)) {
            click(getObject(deviceType() + "home.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
            report.updateTestLog("verifyAccountOrdering", "Show more Accounts link exist and is clicked", Status_CRAFT.DONE);
        } else {
            strParentobject = deviceType() + "home.parentaccount_NoShowMore";
        }

        List<WebElement> oUIRows = driver.findElements(getObject(deviceType() + "home.AccountListRows"));

        for (int i = 0; i < arrExpectedOrdering.length; i++) {

            // Initailizing and resetting the endofIndex value
            int endOfIndex = 0;
            int elementFoundIndex = 0;
            //Check Grouping and Sequencing
            for (int j = 0; j < oUIRows.size(); j++) {
                String value = oUIRows.get(j).getText();
                //String strClassName = oUIRows.get(j).getAttribute(getClass().toString());
                //if (oUIRows.get(j).getText().contains(arrExpectedOrdering[i])) {
                if (value.contains(arrExpectedOrdering[i])) {
                    intElementFoundIndex = j;
                    report.updateTestLog("verifyAccountOrdering", arrExpectedOrdering[i] + " :: On Home screen element Found at position :: " + (j + 1), Status_CRAFT.PASS);

                } else {
                    if (intElementFoundIndex > 0) {
                        intEndOfIndex = intElementFoundIndex;
                    }
                }
            }
        }

    }

    /**
     * Function : Verify the ordering of account policy section
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyAccountOrderingPolicy() throws Exception {
        String strExpSequence = dataTable.getData("General_Data", "SequencePolicyType");
        String strCustomerRegion = dataTable.getData("General_Data", "Account_Type");
        String strParentobject = "";
        int intEndOfIndex = 0;
        int intElementFoundIndex = 0;

       /* if (strCustomerRegion.equalsIgnoreCase("GB/NI")) {
            report.updateTestLog("verifyAccountOrdering", "My Online Portfolio section is not present for GB/NI customer", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAccountOrdering", "My Online Portfolio section is present for ROI customer", Status_CRAFT.FAIL);
        }*/

        String[] arrExpectedOrdering = strExpSequence.split(",");
        strParentobject = deviceType() + "home.parentpolicy";
        List<WebElement> oUIRows = driver.findElements(getObject(strParentobject));
        for (int i = 0; i < arrExpectedOrdering.length; i++) {
            intEndOfIndex = 0;
            intElementFoundIndex = 0;
            for (int j = 0; j < oUIRows.size(); j++) {
                if (oUIRows.get(j).getText().contains(arrExpectedOrdering[i])) {
                    if (intEndOfIndex == 0) {
                        intElementFoundIndex = j;
                        report.updateTestLog("verifyAccountOrdering", arrExpectedOrdering[i] + " :: On Home screen element Found at position :: " + (j + 1), Status_CRAFT.PASS);
                    } else if (intEndOfIndex < j) {
                        report.updateTestLog("verifyAccountOrdering", "For element : " + arrExpectedOrdering[i] + " :: Last position found was : " + intEndOfIndex + ": Though it found at position :" + (j + 1), Status_CRAFT.FAIL);
                    }
                } else {
                    if (intElementFoundIndex > 0) {
                        intEndOfIndex = intElementFoundIndex;
                    }
                }
            }
        }
    }

    /**
     * Function : Verify Tool tip associated with field
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyTooltipText() throws Exception {
        String strAccountName = dataTable.getData("General_Data", "Account_Name");
        String strExpectVal = dataTable.getData("General_Data", "VerifyDetails");
        String strElement = "";
        switch (strAccountName) {

            case "Pension Policy ~ 7914":
                strElement = deviceType() + "home.lblInvestPloicyToolTip.1";
                break;
            case "Pension Policy ~ ":
                strElement = deviceType() + "home.lblInvestPloicyToolTip.2";
                break;
            case "Investment Policy ~ 7918":
                strElement = deviceType() + "home.lblInvestPloicyToolTip.3";
                break;
            case "Pension Policy ~ 7920":
                strElement = deviceType() + "home.lblInvestPloicyToolTip.4";
                break;
            case "Protection ~ 7922":
                strElement = deviceType() + "home.lblInvestPloicyToolTip.5";
                break;
            case "Protection Policy ~ 7924":
                strElement = deviceType() + "home.lblInvestPloicyToolTip.6";
                break;
            case "Pension Policy ~ 7926":
                strElement = deviceType() + "home.lblInvestPloicyToolTip.7";
                break;
            case "Protection ~ 7928":
                strElement = deviceType() + "home.lblInvestPloicyToolTip.8";
                break;
            case "Protection ~ 7916":
                strElement = deviceType() + "home.lblInvestPloicyToolTip.9";
                break;
        }

        if (isElementDisplayed(getObject(strElement), 5)) {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject(strElement)));
            click(getObject(strElement), "Click on ToolTip");
            String toolTipText = getText(getObject(strElement));
            if (strExpectVal.equalsIgnoreCase(toolTipText)) {
                report.updateTestLog("VerifyToolTiptext", "Tool Tip text dispalyed as : " + toolTipText, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyToolTiptext", "Tool Tip text is not dispalyed/present correctly ", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : Verify Logout details
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifylogoutdetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String strLinkToCheck = dataTable.getData("General_Data", "Properties_Variable");
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            clickJS(getObject(deviceType() + "home.imgProfileIcon"), "Clicking on Profile Icon");
            clickJS(getObject(deviceType() + "home.btnLogout"), "Clicking on Logout button");
            homePage.verifyPopUpAndClickOnLinkMwLogout("mw.home.btnLogout", "launch.dlgSafe&Secure", strLinkToCheck);
            click(getObject(strLinkToCheck));
        } else {
            homePage.verifyPopUpAndClickOnLink("w.home.btnLogout", "launch.dlgSafe&Secure", strLinkToCheck);
        }
    }

    public void verifyLogout() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (deviceType() == "mw." | deviceType() == "tw." | isMobile) {
            click(getObject(deviceType() + "home.imgProfileIcon"), "Profile icon clicked");
            click(getObject(deviceType() + "home.btnLogout"), "Logout link");
            homePage.verifyPopUpAndClickOnLinkMwLogout("mw.home.btnLogout", "launch.dlgSafe&Secure", deviceType() + "home.btnYesMeLogOut");
            //Commenting because as a part of new CR - feedback is removed from the logout page
            //homePage.verifylogoutFeedback();
        } else {
            homePage.verifyPopUpAndClickOnLink("w.home.btnLogout", "launch.dlgSafe&Secure", deviceType() + "home.btnYesMeLogOut");
        }
    }

    public void verifyLogoutPage() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.BOIlogo"), 1)) {
            report.updateTestLog("VerifyLogout", "Bank of Ireland logo is displayed", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject(deviceType() + "home.logoutlogo"), 1)) ;
            {
                report.updateTestLog("VerifyLogout", "Logout logo is displayed", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("VerifyLogout", "Bank of Ireland/Logout logo is not displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject(deviceType() + "home.logoutText"), 1)) {
            report.updateTestLog("VerifyLogout", "Text 'You have been logged out.'is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyLogout", "Text 'You have been logged out.'is not displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject(deviceType() + "home.btnLogIn"), 1)) {
            report.updateTestLog("VerifyLogout", "Log in button is present", Status_CRAFT.PASS);
            click(getObject(deviceType() + "home.btnLogIn"), "Log in");
        } else {
            report.updateTestLog("VerifyLogout", "Log in button is not present", Status_CRAFT.FAIL);
        }

    }

    /**
     * Function : Verify feedback details on logout page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifylogoutFeedback() throws Exception {
        String strTypeOfCheck = dataTable.getData("General_Data", "VerifyDetails");
        String strExpectedlogoutmsg = "";
        String strActualLogoutMsg = "";
        String strContentXpath = "";
        if (strTypeOfCheck.equalsIgnoreCase("PositiveFlow")) {
            if (isElementDisplayed(getObject("home.lblLogOutPass"), 10)) {
                strExpectedlogoutmsg = "You're now logged out.";
                strActualLogoutMsg = getText(getObject("home.lblLogOutPass"));
                report.updateTestLog("VerifyLogout", "Screenshot : Content ", Status_CRAFT.SCREENSHOT);
                if (strActualLogoutMsg.equals(strExpectedlogoutmsg)) {
                    report.updateTestLog("VerifyLogout", "Logging out of the  Application: ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("VerifyLogout", "Logging out failed of the  Application: ", Status_CRAFT.FAIL);
                }
            }
            String[] arrContentToCheck = {"Positive", "Neutral", "Negative"};
            for (int i = 0; i < arrContentToCheck.length; i++) {
                strContentXpath = "xpath~//a[@id='C2__Feedback-LogOutPage-" + arrContentToCheck[i] + "']/span";
                scrollToView(getObject(strContentXpath));
                isElementDisplayedWithLog(getObject(strContentXpath), "Type of Feedback :: " + arrContentToCheck[i], "Logout feedback Page", 15);
            }
        } else if (strTypeOfCheck.equalsIgnoreCase("NegativeFlow")) {
            if (isElementDisplayed(getObject(deviceType() + "home.btnLogout"), 1)) {
                click(getObject(deviceType() + "home.btnLogout"));
                if (isElementDisplayed(getObject("launch.dlgSafe&Secure"), 5)) {
                    report.updateTestLog("VerifyLogout", "Link :: 'No, go back' Working as expected on log out pop.", Status_CRAFT.DONE);
                }
            }

        } else if (strTypeOfCheck.equalsIgnoreCase("E2ENegativeFlow")) {
            isElementDisplayedWithLog(getObject(deviceType() + "home.btnLogout"),
                    "Log out button", "Customer is logged in and navigated back to Logout link page", 15);
        }
    }

    /**
     * Function : Method to check any content is not present on page / Frame
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyCheckContentNotPresent() throws Exception {
        String strParentObject = dataTable.getData("General_Data", "Properties_Variable");
        String strContentToCheck = dataTable.getData("General_Data", "VerifyContent");
        String[] arrContentToCheck = strContentToCheck.split("::");
        for (int i = 0; i < arrContentToCheck.length; i++) {
            String strAllContent = getText(getObject(strParentObject));
            if (strAllContent.contains(arrContentToCheck[i])) {
                report.updateTestLog("verifyCheckContentNotPresent", "The Content :: " + arrContentToCheck[i] + ":: is displyed.", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyCheckContentNotPresent", "The Content :: " + arrContentToCheck[i] + ":: is not displyed.", Status_CRAFT.PASS);
            }
        }
    }

    /**
     * Function : Method to check any content is not present home page tab
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyCheckContentNotPresent_Account() throws Exception {
        String strParentObject = dataTable.getData("General_Data", "Properties_Variable");
        String strContentToCheck = dataTable.getData("General_Data", "VerifyContent");
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strAccountSetUp = dataTable.getData("General_Data", "VerifyDetails");
        String[] arrContentToCheck = strContentToCheck.split("::");
        String strBlueCardText = "";

        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyCheckContentNotPresent", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        for (int i = 0; i < arrContentToCheck.length; i++) {
            for (int j = 0; j < oUIRows.size(); j++) {
                if (oUIRows.get(j).getText().toUpperCase().contains(strAccountToCheck.toUpperCase())) {
                    if (oUIRows.get(j).getText().toUpperCase().contains(arrContentToCheck[i].toUpperCase())) {
                        if (strAccountSetUp.equals("TextPresent")) {
                            report.updateTestLog("verifyCheckContentNotPresent", "The Content :: " + arrContentToCheck[i] + ":: is displyed at position ::" + (j + 1), Status_CRAFT.DONE);
                        } else {
                            report.updateTestLog("verifyCheckContentNotPresent", "The Content :: " + arrContentToCheck[i] + ":: is displyed at position ::" + (j + 1) + ":: havng content ::" + oUIRows.get(j).getText(), Status_CRAFT.FAIL);
                        }
                    } else {
                        report.updateTestLog("verifyCheckContentNotPresent", "The Content :: " + arrContentToCheck[i] + ":: is not displyed at position ::" + (j + 1), Status_CRAFT.DONE);
                        oUIRows.get(j).click();
                        waitForElementPresent(getObject("myAccount.sectionBlueCard"), 15);
                        strBlueCardText = getText(getObject("myAccount.sectionBlueCard"));
                        if (strBlueCardText.toUpperCase().contains("Available :")) {
                            report.updateTestLog("verifyCheckContentNotPresent", "The Content :: " + arrContentToCheck[i] + ":: is displyed on Blue card for account at position :: " + (j + 1), Status_CRAFT.FAIL);
                        } else {
                            report.updateTestLog("verifyCheckContentNotPresent", "The Content :: " + arrContentToCheck[i] + ":: is Not displyed on Blue card for account at position :: " + (j + 1), Status_CRAFT.PASS);
                        }
                        if (deviceType.equalsIgnoreCase("MobileWeb")) {
                            click(getObject("mw.myAccount.imgBack"));
                        } else if (deviceType.equalsIgnoreCase("Web")) {
                            click(getObject(deviceType() + "home.titleMyAccounts"));
                        }
                        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
                            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
                            oUIRows = driver.findElements(getObject(strParentObject));
                        }
                    }
                }
            }
        }

    }

    /**
     * Function : Method to check any content is present on page / Frame / Parent Table
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyCheckContentPresent(String strParentObject) throws Exception {
        String strContentToCheck = dataTable.getData("General_Data", "VerifyContent");
        String[] arrContentToCheck = strContentToCheck.split("::");
        int intFoundIndex = 0;
        List<WebElement> oUIRows = findElements(getObject(strParentObject));
        ScrollToVisibleJS(strParentObject);
        report.updateTestLog("verifyCheckContentPresent", "The Content displayed screenshot", Status_CRAFT.SCREENSHOT);
        for (int i = 0; i < arrContentToCheck.length; i++) {
            int j = intFoundIndex;
            for (j = intFoundIndex; j < oUIRows.size(); j++) {
                if (oUIRows.get(j).getText().contains(arrContentToCheck[i])) {
                    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                    js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                    intFoundIndex = j;
                    report.updateTestLog("verifyCheckContentPresent", "The Content :: " + arrContentToCheck[i] + ":: is displayed at location :: " + (j + 1), Status_CRAFT.DONE);
                    break;
                } else if (!(j == intFoundIndex)) {
                    report.updateTestLog("verifyCheckContentPresent", "The Content :: " + arrContentToCheck[i] + ":: is not displayed.", Status_CRAFT.FAIL);
                    break;

                } else {
                    intFoundIndex = intFoundIndex + 1;
                }


            }
        }
    }
    /*public void verifyLostCardAndStolenPage() throws Exception{
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
                    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject(deviceType() + "home.btnServiceDesk")));
            clickJS(getObject(deviceType() + "home.btnServiceDesk"), "Service Desk ");
        } else if (deviceType.equalsIgnoreCase("Web")) {
            click(getObject(deviceType() + "home.btnServiceDesk"));
        }
        waitForElementPresent(getObject(deviceType() + "home.lnkLostAndStolen"), 15);
        click(getObject(deviceType() + "home.lnkLostAndStolen"));
//        String strPresentHeader = dataTable.getData("General_Data", "FirstName");
        if (isElementDisplayed(getObject("home.lostAndStolenPageHeader"), 5)) {
                report.updateTestLog("verifycardLostAndStolen", "Header 'Lost or stolen card' is correctly displayed on UI", Status_CRAFT.PASS);
            } else {
            report.updateTestLog("verifycardLostAndStolen", "Existing Header on UI is incorrect", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("home.lostAndStolenPageLabel"), 5)) {
            report.updateTestLog("verifycardLostAndStolen", "Label 'Call our 24/7 team immediately to report a lost/stolen card' is correctly displayed on UI", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifycardLostAndStolen", "Existing Label on UI is incorrect", Status_CRAFT.FAIL);
        }
        String strContentToCheck = dataTable.getData("General_Data","VerifyContent");
        String[] arrContentToCheck = strContentToCheck.split("::");
        int intFoundIndex = 0;
        List<WebElement> oUIRows = findElements(getObject("home.lostandStolenSection"));
        report.updateTestLog("verifyCheckContentPresent", "The Content dispalyed screenshot", Status_CRAFT.SCREENSHOT);
        for (int i = 0; i < arrContentToCheck.length; i++) {
            int j = intFoundIndex;
            for (j = intFoundIndex; j < oUIRows.size(); j++) {
                if (oUIRows.get(j).getText().contains(arrContentToCheck[i])) {
                    intFoundIndex = j;
                    report.updateTestLog("verifyCheckContentPresent", "The Content :: " + arrContentToCheck[i] + ":: is displayed at location :: " + (j + 1), Status_CRAFT.DONE);
                    break;
                } else if (!(j == intFoundIndex)) {
                    report.updateTestLog("verifyCheckContentPresent", "The Content :: " + arrContentToCheck[i] + ":: is not displayed.", Status_CRAFT.FAIL);
                    break;
                }
            }
        }
    }*/


    /**
     * Function : Validate the Lost card Details
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void NavigateToSliderMenu(String strMenuItem, String strDestination) throws Exception {
        boolean blnDisplayed = false;
        click(getObject(deviceType() + "home.btnMenu")); //click on menu
        click(getObject(deviceType() + "home.lblMenuBar")); //click on site navigation
        report.updateTestLog("NavigateToSliderMenu", "Slider Menu items", Status_CRAFT.SCREENSHOT);
        click(getObject(deviceType() + strMenuItem)); //click on menu Item
        blnDisplayed = isElementDisplayed(getObject(deviceType() + strDestination), 10);
        if (blnDisplayed = true) {
            report.updateTestLog("NavigateToSliderMenu", "Slider Menu item :: " + strMenuItem + ":: is displayed correctly and on click navigated to :: " + strDestination + ":: Successfully..!", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("NavigateToSliderMenu", "Please check the Slider menu item and its navigation...!!", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Validate the Lost card Details
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyLostcard() throws Exception {
        boolean blnValueDisplay = true;
        String strExpectedTopHeader = "";
        String strValCaptured = "";
        String strContentToCheck = "";
        String strExpectedHeader = "";
        String strExpectedHeaderMsg = "";
        String strBackNavigation = "";
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            strValCaptured = "";
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject(deviceType() + "home.btnServiceDesk")));
            clickJS(getObject(deviceType() + "home.btnServiceDesk"), "Service Desk ");
        } else if (deviceType.equalsIgnoreCase("Web")) {
            click(getObject(deviceType() + "home.btnServiceDesk"));
        }
        waitForElementPresent(getObject(deviceType() + "home.lnkLostAndStolen"), 15);
        click(getObject(deviceType() + "home.lnkLostAndStolen"));
        waitForElementPresent(getObject("home.lblHeader"), 15);
        strContentToCheck = dataTable.getData("General_Data", "FirstName");
        String[] arrContentToCheck = strContentToCheck.split("::");
        strExpectedHeader = getText(getObject("home.lblHeader"));
        strExpectedHeaderMsg = getText(getObject("home.lblHeaderMsg"));
        strBackNavigation = getText(getObject(deviceType() + "home.lblNavigationName"));

        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            strExpectedTopHeader = "";
            arrContentToCheck[0] = "";
            blnValueDisplay = strExpectedTopHeader.equals(arrContentToCheck[0]) & strExpectedHeader.equals(arrContentToCheck[1]) & strExpectedHeaderMsg.equals(arrContentToCheck[2]) & strBackNavigation.equals(arrContentToCheck[3]);
        } else if (deviceType.equalsIgnoreCase("Web")) {
            strExpectedTopHeader = getText(getObject(deviceType() + "home.lblTopHeader"));
            blnValueDisplay = strExpectedTopHeader.equals(arrContentToCheck[0]) & strExpectedHeader.equals(arrContentToCheck[1]) & strExpectedHeaderMsg.equals(arrContentToCheck[2]) & strBackNavigation.equals(arrContentToCheck[3]);
        }
        if (blnValueDisplay == true) {
            report.updateTestLog("verifyLostcard", "The Back Navigation Text :: " + strBackNavigation + ":: is displyed correctly.", Status_CRAFT.DONE);
            report.updateTestLog("verifyLostcard", "The Top Header :: " + strExpectedTopHeader + ":: is displyed correctly.", Status_CRAFT.DONE);
            report.updateTestLog("verifyLostcard", "The Header :: " + strExpectedHeader + ":: is displyed correctly.", Status_CRAFT.DONE);
            report.updateTestLog("verifyLostcard", "The Header Message :: " + strExpectedHeaderMsg + ":: is displyed correctly.", Status_CRAFT.DONE);
            verifyCheckContentPresent("home.parentLostStolenTable");
        } else {
            report.updateTestLog("verifyLostcard", "The Content either Back Navigation Text, Top Header, Header, Header Message is not displyed.", Status_CRAFT.FAIL);
        }

    }

    /**
     * Function : Verify Particular Account Type on Home Page
     * Update on    Updated by     Reason
     * 15/04/2019   C966119        Updated the Thread.sleep, variable declaration
     */
    public void verifyAccTypeExistAndClicked() throws Exception {
        boolean bflag = false;
        String strUiText;
        String accType = dataTable.getData("General_Data", "Account_Type").toUpperCase();
        String accno = dataTable.getData("General_Data", "AccountNumber");

        //isMobile>>TODO need to check the impact-Rajiv
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (!dataTable.getData("General_Data", "Nickname").equals("Credit Card")) {
                accno = "~ " + dataTable.getData("General_Data", "AccountNumber").substring(accno.length() - 4);
            }
        }
        List<WebElement> temp = driver.findElements(By.xpath("//div[contains(@class,'responsive-row boi-margin-accountdetails')]"));
        //isMobile>>TODO need to check the impact-Rajiv
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            temp = driver.findElements(By.xpath("//*[@class='tc-ripple-effect  textRole']"));
        }
        if (temp.size() != 0) {
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).isDisplayed()) {
                    strUiText = temp.get(i).getText().toUpperCase();
                    if (strUiText.contains(accType) && strUiText.contains(accno)) {
                        report.updateTestLog("verifyAccTypeExistAndClicked", "Account Type : '" + accType + "' is present", Status_CRAFT.PASS);
                        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                        js.executeScript("arguments[0].scrollIntoView();", temp.get(i));
                        js.executeScript("arguments[0].click();", temp.get(i));
//                        temp.get(i).click();
                        report.updateTestLog("verifyAccTypeExistAndClicked", "Account Type : '" + accType + "'  is Selected", Status_CRAFT.PASS);
                        bflag = true;
                        break;
                    }
                }
            }
        }

        if (!bflag) {
            report.updateTestLog("verifyAccTypeExistAndClicked", "Account Type : '" + accType + " ' is not present", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to Verify Particular Currency on Home Page
     */
    public void VerifyCurrencySymbolHomePage() throws Exception {
        boolean bflag = false;
        String strUiText;
        String currencySymbol = dataTable.getData("General_Data", "Currency_Symbol");
        List<WebElement> temp = driver.findElements(By.xpath("//div[contains(@class,'responsive-row boi-margin-accountdetails')]"));

        if (temp.size() != 0) {
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).isDisplayed()) {
                    strUiText = temp.get(i).getText();
                    if (strUiText.contains(currencySymbol)) {
                        report.updateTestLog("verifyAccTypeExistAndClicked", "Currency Type : '" + currencySymbol + " ' is present", Status_CRAFT.PASS);
                        bflag = true;
                        //   break;
                    }
                }
            }
        }
        if (!bflag) {
            report.updateTestLog("verifyAccTypeExistAndClicked", "Currency Type : '" + currencySymbol + " ' is not present", Status_CRAFT.FAIL);
        }
    }


    /**
     * Function to Verify Particular Menu Tab not present upon clicking an AccountType
     */
    public void verifyMenuTabExistAndPerfromClick() throws Exception {
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            clickJS(getObject("AccNickName.btnMoreOption"), "Clicked on menu tab");
            report.updateTestLog("verifyPresentAcctNickName", "Clicked on More options", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("AccNickname.btnAccountNickName"), 5)) {
                report.updateTestLog("verifyAccountNicknameTab", "Account Nickname tab is displayed", Status_CRAFT.PASS);
                clickJS(getObject("AccNickname.btnAccountNickName"), "Clicked on Account Nickname Tab ");
            } else {
                report.updateTestLog("verifyAccountNicknameTab", "Account Nickname tab is not displayed", Status_CRAFT.FAIL);
            }

        } else {
            String strTabName = dataTable.getData("General_Data", "TabName");
            waitForJQueryLoad();
            waitForPageLoaded();
            if (deviceType.equals("MobileWeb") && strTabName.equals("Edit account nickname")) {
                clickJS(getObject("xpath~//div[contains(@class,'tc-tab-header ecDIB')]/span[text()='More options']/.."), "Clicked on menu tab");

                report.updateTestLog("verifyPresentAcctNickName", "Clicked on More options for edit nickname", Status_CRAFT.PASS);
                waitForJQueryLoad();
                waitForPageLoaded();
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();",
                        driver.findElement(getObject("xpath~//span[text()='" + strTabName + "']")));
                if (isElementDisplayed(getObject("xpath~//span[text()='" + strTabName + "']"), 10)) {
                    report.updateTestLog("verifyMenuTabExistAndPerfromClick", "Menu Tab '" + strTabName + " ' is displayed", Status_CRAFT.PASS);
                    clickJS(getObject("xpath~//span[text()='" + strTabName + "']"), "Click on Menu Tab ' " + strTabName + "'");
                } else {
                    report.updateTestLog("verifyMenuTabExistAndPerfromClick", "Menu Tab '" + strTabName + " ' is NOT displayed", Status_CRAFT.FAIL);
                }

            } else {
                if (isElementDisplayed(getObject("xpath~//a[text()='" + strTabName + "']"), 10)) {
                    report.updateTestLog("verifyMenuTabExistAndPerfromClick", "Menu Tab '" + strTabName + " ' is displayed", Status_CRAFT.PASS);
                    clickJS(getObject("xpath~//a[text()='" + strTabName + "']"), "Click on Menu Tab ' " + strTabName + "'");
                } else {
                    report.updateTestLog("verifyMenuTabExistAndPerfromClick", "Menu Tab '" + strTabName + " ' is NOT displayed", Status_CRAFT.FAIL);
                }
            }
        }
    }

    /**
     * Function to Verify existing Account Nickname on Account Nickname page
     */
    public void verifyPresentAcctNickName() throws Exception {
        String strPresentNickname = dataTable.getData("General_Data", "Nickname");
        if (isElementDisplayed(getObject("AccNickname.txtAccountNickname"), 5)) {
            // String strUiNickname = driver.findElement(By.xpath("//input[contains(@name,'CHANGEACCOUNTNICKNAME[1]')]")).getAttribute("value");
            String strUiNickname = getText(getObject("AccNickname.txtAccountNickname"));
            if (strUiNickname.equalsIgnoreCase(strPresentNickname)) {
                report.updateTestLog("verifyPresentAcctNickName", "Existing Nickname '" + strUiNickname + "'is correctly displayed on UI", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyPresentAcctNickName", "Existing Nickname on UI is incorrect, Expected '" + strPresentNickname + "', on UI '" + strUiNickname + "'", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyPresentAcctNickName", "Account Nickname textbox doesnot exist on Account NickName page", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to Verify Save Functionality of Account Nickname on Account Nickname page
     */
    public void verifyAcctNicknameSave() throws Exception {
        String strNewNickname = dataTable.getData("General_Data", "NewNickname");

        if (isElementDisplayed(getObject("AccNickname.txtAccountNickname"), 5) &&
                isElementDisplayed(getObject("AccNickname.btnEdit"), 5) &&
                isElementDisplayed(getObject("AccNickname.titleAccountNickName"), 5)) {
            report.updateTestLog("verifyAcctNicknameSave", "Account Nickname textbox And Edit Button are visible on Account NickName page", Status_CRAFT.PASS);

            clickJS(getObject("AccNickname.btnEdit"), " Clicking Edit Button on Account Nickname page");
            sendKeys(getObject("xpath~//input[contains(@name,'CHANGEACCOUNTNICKNAME[1]')]"), strNewNickname);
            clickJS(getObject("AccNickname.btnSave"), " Clicking Save Button on Account Nickname page");

            if (isElementDisplayed(getObject("AccNickname.lblUpdateSuccess"), 5)) {
                report.updateTestLog("verifyAcctNicknameSave", "Success message is displayed after performing Save operation", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAcctNicknameSave", "Success message is NOT displayed after performing Save operation", Status_CRAFT.FAIL);
            }

            //String strUiNickname = driver.findElement(By.xpath("//input[@id='C6__C7__QUE_5F54A2D5AAF5458B423400']")).getAttribute("value");
            String strUiNickname = getText(getObject("AccNickname.txtAccountNickname"));
            if (strUiNickname.equalsIgnoreCase(strNewNickname)) {
                report.updateTestLog("verifyAcctNicknameSave", "New Nickname '" + strUiNickname + "'is successfully updated on UI", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAcctNicknameSave", "New Nickname on UI is incorrect, Expected '" + strNewNickname + "', on UI '" + strUiNickname + "'", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAcctNicknameSave", "Account Nickname textbox OR Edit Button are not visible on Account NickName page", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to Verify Cancel Functionality of Account Nickname on Account Nickname page
     */
    public void verifyAcctNicknameCancel() throws Exception {

        String strPresentNickname = dataTable.getData("General_Data", "Nickname");
        String strNewNickname = dataTable.getData("General_Data", "NewNickname");

        if (isElementDisplayed(getObject("AccNickname.txtAccountNickname"), 5) &&
                isElementDisplayed(getObject("AccNickname.btnEdit"), 1) &&
                isElementDisplayed(getObject("AccNickname.titleAccountNickName"), 1)) {
            report.updateTestLog("verifyAcctNicknameSave", "Account Nickname textbox And Edit Button are visible on Account NickName page", Status_CRAFT.PASS);

            clickJS(getObject("AccNickname.btnEdit"), " Clicking Edit Button on Account Nickname page");
            sendKeys(getObject("AccNickname.edtAccountNickname"), strNewNickname);
            clickJS(getObject("AccNickname.btnCancel"), " Clicking Cancel Button on Account Nickname page");
            String strUiNickname = getText(getObject("AccNickname.txtAccountNickname"));
            if (strUiNickname.equalsIgnoreCase(strPresentNickname)) {
                report.updateTestLog("verifyAcctNicknameCancel", "New Nickname '" + strUiNickname + "'is same as old nickname,CANCEL Operation Successful", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAcctNicknameCancel", "New Nickname on UI is incorrect, Expected '" + strPresentNickname + "', on UI '" + strUiNickname + "'", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAcctNicknameSave", "Account Nickname textbox OR Edit Button are not visible on Account NickName page", Status_CRAFT.FAIL);
        }

        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            clickJS(getObject("AccNickName.btnMobileHeaderBack"), "back button");
            waitForJQueryLoad();
            waitForPageLoaded();
        }
    }

    /**
     * Function to Verify Maximum characters of an input/textbox
     */
    public void verifyMaxLength(int intmaxLenth, String strTextbox) throws Exception {

        if (isElementDisplayed(getObject(strTextbox), 10)) {
            int intUiMaxlength = Integer.parseInt(driver.findElement(getObject(strTextbox)).getAttribute("maxLength"));
            if (intmaxLenth == intUiMaxlength) {
                report.updateTestLog("verifyMaxLength", "Expected maxlength '" + intmaxLenth + "' of the input/textbox matches with actual '" + intUiMaxlength + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyMaxLength", "Expected maxlength '" + intmaxLenth + "' of the input/textbox does not match with actual '" + intUiMaxlength + "'", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyMaxLength", "Element '" + strTextbox + "' text box does not exist on page", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to Enable edit Box for Account NickName
     */
    public void EnableAcctNickNameEditText() throws Exception {

        if (isElementDisplayed(getObject("AccNickname.txtAccountNickname"), 5) &&
                isElementDisplayed(getObject("AccNickname.btnEdit"), 5)) {
            report.updateTestLog("verifyAcctNickName_EditFunctionality", "Account Nickname textbox And Edit Button are visible on Account NickName page", Status_CRAFT.PASS);
            //waitForElementToClickable(getObject("AccNickname.btnEdit"),5);
            click(getObject("AccNickname.btnEdit"), "Edit Button on Account Nickname page");

        } else {
            report.updateTestLog("verifyAcctNickName_EditFunctionality", "Account Nickname textbox OR Edit Button are not visible on Account NickName page", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to Verify Account NickName Textbox Max Length
     */
    public void verifyAccNicknameMaxLength() throws Exception {

        int intMaxLength = Integer.parseInt(dataTable.getData("General_Data", "MaxLength"));
        verifyMaxLength(intMaxLength, "AccNickname.txtAccountNickname");
    }

    /**
     * Function to Verify Account NickName Textbox Max Length
     */
    public void verifyAccontNicknameInvalidCharacter() throws Exception {
        String strNewNickname = dataTable.getData("General_Data", "NewNickname");
        sendKeys(getObject("AccNickname.txtAccountNickname"), strNewNickname);
        click(getObject("AccNickname.btnSave"), " Clicking Save Button on Account Nickname page");
        if (isElementDisplayed(getObject("AccNickNameInvalidSpecialChar"), 5)) {
            report.updateTestLog("verifyAccontNicknameInvalidCharacter", "Error Message is displayed for Invalid /not permitted data not got accepted, Input data '" + strNewNickname + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAccontNicknameInvalidCharacter", "Error Message is NOT displayed for Invalid /not permitted data got accepted, Input data '" + strNewNickname + "'", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to Verify Account NickName Textbox Max Length
     */
    public void verifyAccNicknameMoreThanMaxCharacter() throws Exception {

        String strNewNickname = dataTable.getData("General_Data", "NewNickname");
        sendKeys(getObject("AccNickname.txtAccountNickname"), strNewNickname);
        // click(getObject("AccNickname.btnSave")," Clicking Save Button on Account Nickname page");
//        String strUiNickname = driver.findElement(By.xpath("//input[@id='C6__C7__QUE_5F54A2D5AAF5458B423400']")).getAttribute("value");
        String strUiNickname = driver.findElement(By.xpath("//input[contains(@name,'CHANGEACCOUNTNICKNAME[1]')]")).getAttribute("value");
        if (!strUiNickname.equalsIgnoreCase(strNewNickname)) {
            report.updateTestLog("verifyAccNicknameMoreThanMaxCharacter", "Input data more than max length is not getting accepted, input data '" + strNewNickname + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAccNicknameMoreThanMaxCharacter", "Input data more than max length is getting accepted, input data '" + strNewNickname + "'", Status_CRAFT.FAIL);
        }
    }

    public void verifyAccNicknameBlankInput() throws Exception {
        String strNewNickname = "";
        sendKeys(getObject("AccNickname.edtAccountNickname"), strNewNickname);
        click(getObject("AccNickname.btnSave"), " Clicking Save Button on Account Nickname page");
        if (isElementDisplayed(getObject("AccNickname.lblERRorBlankInput"), 5)) {
            report.updateTestLog("verifyAccNicknameBlankInput", "Error Message displayed for Blank data input", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAccNicknameBlankInput", "Error Message is NOT displayed for Blank data input", Status_CRAFT.FAIL);
        }
    }

    public void verifyAccNicknameNotPermittedInput() throws Exception {
        String strNewNickname = "";
        sendKeys(getObject("AccNickname.edtAccountNickname"), strNewNickname);
        click(getObject("AccNickname.btnSave"), " Clicking Save Button on Account Nickname page");
        if (isElementDisplayed(getObject("AccNickname.lblErrorInvalidInput"), 5) | isElementDisplayed(getObject("AccNickname.lblErrorInputNotPermitted"), 5)) {
            report.updateTestLog("verifyAccontNicknameInvalidCharacter", "Error Message displayed Blank data not permitted or Characters more than maxlength not accepted, Input data '" + strNewNickname + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAccontNicknameInvalidCharacter", "Error Message displayed and Invalid, not permitted or Characters more than maxlength getting accepted, Input data '" + strNewNickname + "'", Status_CRAFT.FAIL);
        }
    }

    public void verifyAccNicknameOffensiveInput() throws Exception {
        String strNewNickname = dataTable.getData("General_Data", "NewNickname");
        sendKeys(getObject("AccNickname.edtAccountNickname"), strNewNickname);
        click(getObject("AccNickname.btnSave"), " Clicking Save Button on Account Nickname page");
        if (isElementDisplayed(getObject("AccNickname.lblErrorOffensiveInput"), 5)) {
            report.updateTestLog("verifyAccNicknameOffensiveInput", "Error Message displayed for Offensive Input, Input data '" + strNewNickname + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAccNicknameOffensiveInput", "Error Message NOT displayed for Offensive Input, Input data '" + strNewNickname + "'", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to navigate to service desk and validate its
     */
    public void verifyServiceDeskNaviagtionFunc() throws Exception {

        String myobj;
        if (deviceType == "Web") {
            myobj = "xpath~//span[text()='SERVICES']";
        } else {
            myobj = "xpath~//div[text()='Services']";
        }
        String strValCaptured = "";
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();",
                driver.findElement(getObject(myobj)));
        if (isElementDisplayed(getObject(myobj), 5)) {
            clickJS(getObject(myobj), " Clicked at Service Desk Icon on Home Page");
            if (isElementDisplayed(getObject("serviceDesk.hdrServiceDesk"), 5)) {
                report.updateTestLog("ServiceDeskNaviagtion", "Service Desk Page Loaded successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("ServiceDeskNaviagtion", "Service Desk Page NOT Loaded successfully Or 'Service Desk' Header Not found", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("ServiceDeskNaviagtion", "Service Desk Icon not found on Home Page for DeskTop Browser", Status_CRAFT.FAIL);
        }

        //To check lost or stolen option
        if (isElementDisplayed(getObject("home.lostorstolen"), 5)) {
            clickJS(getObject("home.lostorstolen"), "Lost and stolen card option");
        }

        if (isElementDisplayed(getObject("home.lostoestolentext"), 5)) {
            String text = getText(getObject("home.lostoestolentext"), 5);
            report.updateTestLog("LostorStolen", "Text " + text + " displayed successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("LostorStolen", "Text not displayed", Status_CRAFT.FAIL);
        }
    }


    public void verifyselectServiceDeskOption() throws Exception {
        String strServiceDeskOption = dataTable.getData("General_Data", "ServiceDeskOption");
        String myobj = "";
        if (deviceType() == "w.") {
            myobj = "xpath~//span[text()='SERVICES']";
        } else {
            myobj = "mw.home.btnServiceDesk";
        }
        String strValCaptured = "";
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();",
                driver.findElement(getObject(myobj)));
        if (isElementDisplayed(getObject(myobj), 5)) {
            clickJS(getObject(myobj), " Clicked at Service Desk Icon on Home Page");
            if (isElementDisplayed(getObject(myobj), 5)) {
                report.updateTestLog("ServiceDeskNaviagtion", "Service Desk Page Loaded successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("ServiceDeskNaviagtion", "Service Desk Page NOT Loaded successfully Or 'Service Desk' Header Not found", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("ServiceDeskNaviagtion", "Service Desk Icon not found on Home Page.", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("xpath~//*[contains(text(),'" + strServiceDeskOption + "')]"), 5)) {
            report.updateTestLog("selectServiceDeskOption", "Service Desk's option '" + strServiceDeskOption + "' present on Service Desk page", Status_CRAFT.PASS);
            clickJS(getObject("xpath~//*[contains(text(),'" + strServiceDeskOption + "')]"), " Selected option '" + strServiceDeskOption + " on Service Desk Page");

        } else {
            report.updateTestLog("selectServiceDeskOption", "Service Desk's option '" + strServiceDeskOption + "' not found on Service Desk page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("ServiceManage.txtServiceUnavailable"), 2)) {
            report.updateTestLog("selectServiceDeskOption", " 'Service unavailable :: Unfortunately, this service is unavailable at this time. Please try again later.' is displyed on Service Desk page", Status_CRAFT.FAIL);
        }
    }


    public void verifyAddAccPolicyDropDownItems() throws Exception {

        if (isElementDisplayed(getObject("AddPolicy.lblSelectAccDirectionaltxt"), 5)) {
            if (getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")).equalsIgnoreCase(dataTable.getData("General_Data", "DirectionalText"))) {
                report.updateTestLog("verifyAddAccPolicyDropDownItems", "Directional Text is as expected for Add Account/Policy or Add Account", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccPolicyDropDownItems", "Directional Text is as NOT expected for Add Account/Policy or Add Account", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAddAccPolicyDropDownItems", "Directional Text is NOT present for Add Account/Policy or Add Account", Status_CRAFT.FAIL);
        }

        isElementDisplayedWithLog(getObject("AddPolicy.lblAddYourAccount"),
                "Text : " + getText(getObject("AddPolicy.lblAddYourAccount")), "Add Account/Policy", 5);


        String strListItems = dataTable.getData("General_Data", "DropDownItems");
        String[] arrlistItems = strListItems.split("::");
        clickJS(getObject("AddPolicy.lblSelectAccDirectionaltxt"), "Dirctional text");
        for (int i = 0; i < arrlistItems.length; i++) {
            String UiValue = getText(getObject("xpath~//*[@id='C5__C1__QUE_618BBB1BD78DAE8E1975_exp_wrapper']/ul/li[" + (i + 1) + "]"));
            if (UiValue.equalsIgnoreCase(arrlistItems[i])) {
                report.updateTestLog("verifyAddAccPolicyDropDownItems", "Drop down Element ' " + arrlistItems[i] + "' is present", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccPolicyDropDownItems", "Drop down Element ' " + arrlistItems[i] + "' NOT is present", Status_CRAFT.FAIL);
            }
        }
    }


    public void verifyDropDownDirectionalText(String Item, String oDropDown) throws Exception {
        boolean bfound = false;
        Select oSelect = new Select(driver.findElement(getObject(oDropDown)));
        List<WebElement> oListItems = oSelect.getOptions();
        int iSize = oListItems.size();
        for (int i = 0; i < iSize; i++) {
            String sValue = oListItems.get(i).getText();
            String sClass = oListItems.get(i).getAttribute("class");
            if (sValue.equalsIgnoreCase(Item) && sClass.equalsIgnoreCase("option selected")) {
                report.updateTestLog("verifyDropDownDirectionalText", "Direction text '" + Item + "' present in dropdown '" + oDropDown + "'", Status_CRAFT.PASS);
                bfound = true;
                break;
            }
        }
        if (!bfound) {
            report.updateTestLog("verifyDropDownDirectionalText", "Direction text '" + Item + "' is NOT present in dropdown '" + oDropDown + "'", Status_CRAFT.FAIL);
        }
    }

    public void verifyOptionOfDropDown(String Item, String oDropDown) throws Exception {
        boolean bfound = false;
        List<WebElement> oListItems = driver.findElements(getObject(oDropDown));
        int iSize = oListItems.size();

        for (int i = 0; i < iSize; i++) {
            String sValue = oListItems.get(i).getText().trim();
            if (sValue.equalsIgnoreCase(Item.trim())) {
                report.updateTestLog("verifyItemsOfDropDown", "Drop Down option '" + Item + "' present in dropdown '" + oDropDown + "'", Status_CRAFT.PASS);
                bfound = true;
                break;
            }
        }
        if (!bfound) {
            report.updateTestLog("verifyItemsOfDropDown", "Drop Down option '" + Item + "' is present in dropdown '" + oDropDown + "'", Status_CRAFT.FAIL);
        }
    }

    public void verifySelectAddAccPolicy() throws Exception {

        if (isElementDisplayed(getObject("AddPolicy.lstSelectAccount"), 5)) {
            String policyName = dataTable.getData("General_Data", "PolicyType");
            if (getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")).equalsIgnoreCase("please select")) {
                report.updateTestLog("verifySelectAddAccPolicy", "Directional text is correctly displayed on the Select Account dropdown", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifySelectAddAccPolicy", "Directional text is not as expected for Select Account, Expected: 'please select' :: Actual:'" + getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")) + "'", Status_CRAFT.FAIL);
            }

            clickJS(getObject("AddPolicy.lstSelectAccount"), "Dropdown list");
            clickJS(getObject("xpath~//*[@id='C6__C1__QUE_618BBB1BD78DAE8E1975_div']/div/ul/li[contains(.,'" + policyName + "')]"), "Selected policy '" + policyName + "'");
            //selectDropDownJS(getObject("AddPolicy.lstSelectAccount"),dataTable.getData("General_Data", "Credit Card"));
        } else {
            report.updateTestLog("verifyAddAccPolicyDropDownItems", "Drop Down to Add account/policy Not Present", Status_CRAFT.FAIL);
        }
    }

    public void verifyAddAccPolicyForm_CreditCard() throws Exception {
        String strPolicyName = dataTable.getData("General_Data", "PolicyType");

        if (isElementDisplayed(getObject("AddPolicy.hdrAccountToView"), 5)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "header 'Add your account to view it online' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "header 'Add your account to view it online' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }

        //Verify Account with pretext selection
        if (getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")).equalsIgnoreCase(strPolicyName)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Pretext is correctly present select Account dropdown Expected:" + strPolicyName, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Pretext is NOT correctly Present Select Account dropdown, Expected: '" + strPolicyName + "':: Actual:'" + getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")) + "'", Status_CRAFT.FAIL);
        }

        //Verify Credit Card Label
        if (isElementDisplayed(getObject("AddPolicy.lblCreditCard"), 5)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label 'Credit Card number' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label 'Credit Card number' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }

        ////Verify Credit Card Principal Text label
        if (isElementDisplayed(getObject("AddPolicy.lblPrincipalCardHolder"), 5)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label 'Only the principal card holder can add a credit card to the profile.' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label 'Only the principal card holder can add a credit card to the profile.' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }

        //Verify Credit Card text with pretext is present
        String strPretext = driver.findElement(By.xpath("//input[contains(@name,'CARDNO')]")).getAttribute("placeholder");
        if (strPretext.equalsIgnoreCase("16-digit number")) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Pretext is correctly present Credit Card text Box", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Pretext is NOT correctly present Credit Card text Box, Expected: 'Please enter 16 digit card number':: Actual:'" + getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")) + "'", Status_CRAFT.FAIL);
        }

        //Verify  Date of birth*  <<Prefilled with Directional text DD/MM/YYYY
        String strPretext_Date = driver.findElement(By.xpath("//input[contains(@name,'.DOB')]")).getAttribute("placeholder");
        if (strPretext_Date.equalsIgnoreCase("DD/MM/YYYY")) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Directional Text is correctly present DOB field", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Directional Text is NOT correctly present DOB field, Expected: 'DD/MM/YYYY':: Actual:'" + getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")) + "'", Status_CRAFT.FAIL);
        }

//        if (isElementDisplayed(getObject("AddPolicy.btnGoBack"), 5)) {
//            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "'Go back' Button is present on Add Account Policy page", Status_CRAFT.PASS);
//        } else {
//            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "'Go back' Button is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
//        }

        if (isElementDisplayed(getObject("AddPolicy.Continue"), 5)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "'Continue' Button is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "'Continue' Button is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }

    }

    public void verifyAddAccPolicyForm_CurrentAccount() throws Exception {
        String strPolicyName = dataTable.getData("General_Data", "PolicyType");

        if (isElementDisplayed(getObject("AddPolicy.hdrAccountToView"), 5)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "header 'Add your account to view it online' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "header 'Add your account to view it online' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }

        //Verify Account with pretext selection
        if (getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")).equalsIgnoreCase(strPolicyName)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Pretext is correctly present select Account dropdown Expected:" + strPolicyName, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Pretext is NOT correctly Present Select Account dropdown, Expected: '" + strPolicyName + "':: Actual:'" + getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")) + "'", Status_CRAFT.FAIL);
        }
        //Verify Request Header
        if (isElementDisplayed(getObject("AddPolicy.hdrVerifyReq"), 5)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "header 'We may need your details to verify your request. The information must match what we have on record.' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "header 'We may need your details to verify your request. The information must match what we have on record.' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }
        //Verify Address Line 1* Label
        if (isElementDisplayed(getObject("AddPolicy.lblAddress1"), 5)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label 'Address line 1*' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label 'Label 'Address line 1*' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }
        //Verify Address Line 2* Label
        if (isElementDisplayed(getObject("AddPolicy.lblAddress2"), 5)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label 'Address line 2*' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label 'Label 'Address line 2*' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }
        //Verify Address Line 3* Label
        if (isElementDisplayed(getObject("AddPolicy.lblAddress3"), 5)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label 'Address line 3*' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label 'Label 'Address line 3*' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }
        //Verify Date of birth* Label
        if (isElementDisplayed(getObject("AddPolicy.lbldate"), 5)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label 'Date of birth*' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "Label Label 'Date of birth*' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPolicy.Continue"), 5)) {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "'Continue' Button is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyForm_CreditCard", "'Continue' Button is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }
    }


    public void EnterAddAccPolicyFormCreditCard() throws Exception {
        String strCreditCardNo = dataTable.getData("General_Data", "CreditCardNo");
        String strDOB = dataTable.getData("General_Data", "DOB");
        waitForElementPresent(getObject("AddPolicy.txtCreditCardNo"), 2);
        click(getObject("AddPolicy.txtCreditCardNo"));
        click(getObject("xpath~//span[@class='boi-clear-btn fa fa-times show']"), "x icon");
        driver.findElement(By.xpath("//*[@type='number'][@placeholder='16-digit number']")).sendKeys(strCreditCardNo);
        report.updateTestLog("EnterAddAccPolicyFormCreditCard", "Credit Card No. entered ::" + strCreditCardNo, Status_CRAFT.PASS);
        if (strDOB != "") {
            if (isElementDisplayed(getObject("Addpolicy.txtDOB"), 5)) {
                sendKeys(getObject("Addpolicy.txtDOB"), strDOB);
                //Thread.sleep(2000);
                report.updateTestLog("EnterAddAccPolicyFormCreditCard", "DOB entered ::" + strDOB, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("EnterAddAccPolicyFormCreditCard", "Date Of Birth input box not present on Add Account/policy page", Status_CRAFT.FAIL);
            }
        }

        waitForJQueryLoad();
        click(getObject("AddPolicy.Continue"), "Clicked Continue Button");
        //clickJS(getObject("AddPolicy.Continue"), "Clicked Continue Button");

    }

    public void EnterAddAccPolicyFormCreditCardnew() throws Exception {
        String strCreditCardNo = dataTable.getData("General_Data", "CreditCardNo");
        String strDOB = dataTable.getData("General_Data", "DOB");
        waitForElementPresent(getObject("AddPolicy.txtCreditCardNo"), 2);
        click(getObject("AddPolicy.txtCreditCardNo"));
        // clickJS(getObject("xpath~//span[@class='boi-clear-btn fa fa-times show']"), "x icon");
        driver.findElement(By.xpath("//*[@type='number'][@placeholder='16-digit number']")).sendKeys(strCreditCardNo);
        report.updateTestLog("EnterAddAccPolicyFormCreditCard", "Credit Card No. entered ::" + strCreditCardNo, Status_CRAFT.PASS);
        if (strDOB != "") {
            if (isElementDisplayed(getObject("Addpolicy.txtDOB"), 5)) {
                sendKeys(getObject("Addpolicy.txtDOB"), strDOB);
                Thread.sleep(2000);
                report.updateTestLog("EnterAddAccPolicyFormCreditCard", "DOB entered ::" + strDOB, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("EnterAddAccPolicyFormCreditCard", "Date Of Birth input box not present on Add Account/policy page", Status_CRAFT.FAIL);
            }
        }


        clickJS(getObject("AddPolicy.Continue"), "Clicked Continue Button");
        //clickJS(getObject("AddPolicy.Continue"), "Clicked Continue Button");

    }

    public void enterAddAccPolicyFormCreditCardNew() throws Exception {

        String strCreditCardNo = dataTable.getData("General_Data", "CreditCardNo");
        String strDOB = dataTable.getData("General_Data", "DOB");
    /*Select dropdown = new  Select(driver.findElement(By.xpath("//*[@class='boi-rounded-1 boi_input tc-form-control exp_button_widget']")));
    dropdown.selectByIndex(3);*/
        waitForElementPresent(getObject("AddPolicy.txtCreditCardNo"), 2);
        click(getObject("AddPolicy.txtCreditCardNo"));
        driver.findElement(By.xpath("//*[@type='number'][@placeholder='16-digit number']")).sendKeys(strCreditCardNo);
        sendKeys(getObject("Addpolicy.txtDOB"), strDOB);
        clickJS(getObject("AddPolicy.Continue"), "Clicked Continue Button");


    }


    public void EnterAddAccPolicyFormCurrAcc() throws Exception {
        String strAccountNo = dataTable.getData("General_Data", "AccountNumber");
        String strAddress1 = dataTable.getData("General_Data", "Address1");
        String strAddress2 = dataTable.getData("General_Data", "Address2");
        String strAddress3 = dataTable.getData("General_Data", "Address3");
        String strDOB = dataTable.getData("General_Data", "DOB");

        waitForElementPresent(getObject("AddPolicy.txtAccountNum"), 10);
        if (strDOB != "") {
            if (isElementDisplayed(getObject("Addpolicy.txtDOB"), 5)) {
                if (deviceType.equalsIgnoreCase("Web")) {
                    sendKeys(getObject("Addpolicy.txtDOB"), strDOB);
                } else {
                    ScrollToVisibleJS("Addpolicy.txtDOB");
                    Thread.sleep(1500);
                    sendKeys(getObject("Addpolicy.txtDOB"), strDOB);
                }
                report.updateTestLog("EnterAddAccPolicyFormCreditCard", "DOB entered ::" + strDOB, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("EnterAddAccPolicyFormCreditCard", "Date Of Birth input box not present on Add Account/policy page", Status_CRAFT.FAIL);
            }
        }

        if (deviceType.equalsIgnoreCase("Web")) {
            sendKeys(getObject("AddPolicy.edtAccountNum"), strAccountNo, "Account Number");
            sendKeys(getObject("AddPolicy.txtAddr1"), strAddress1, "Address1");
            sendKeys(getObject("AddPolicy.txtAddr2"), strAddress2, "Address2");
            sendKeys(getObject("AddPolicy.txtAddr3"), strAddress3, "Address3");

        } else {
            sendKeys(getObject("AddPolicy.edtAccountNum"), strAccountNo, " Account Number ");
            Thread.sleep(1000);
            ScrollToVisibleJS("Addpolicy.txtDOB");
            sendKeys(getObject("AddPolicy.txtAddr1"), strAddress1);
            Thread.sleep(1000);
            sendKeys(getObject("AddPolicy.txtAddr2"), strAddress2);
            Thread.sleep(1000);
            sendKeys(getObject("AddPolicy.txtAddr3"), strAddress3);
            Thread.sleep(1000);
            report.updateTestLog("EnterAddAccPolicyFormCreditCard", " :: Address entered :: ", Status_CRAFT.SCREENSHOT);
        }

        ScrollAndClickJS("AddPolicy.Continue");
        Thread.sleep(1000);
        if (isElementDisplayed(getObject("AddPolicy.Continue"), 1)) {
            clickJS(getObject("AddPolicy.Continue"), "Clicked Continue Button");
        }
    }


    public void verifyErrorText_Details() throws Exception {
        String strError = dataTable.getData("General_Data", "ErrorText");
        waitForPageLoaded();
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

    public void verifyInvalidFutureDate() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/mm/yyyy");
        LocalDateTime now = LocalDateTime.now();
        int currentDate = now.getDayOfMonth() + 1;
        click(getObject("Addpolicy.txtDOB"), "Date Object");

        if (!isElementEnabled(getObject("xpath~//*[contains(@class,'boi_input_sm_error')][text()='" + currentDate + "']"))) {
            report.updateTestLog("verifyErrorText", "Invalid/future date is NOT allowed to enter on UI", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyErrorText", "Invalid/future date is allowed to enter on UI", Status_CRAFT.FAIL);
        }
    }


    public void verifyReviewPageCreditCard() throws Exception {

        if (isElementDisplayed(getObject("AddpolicyReview.lblReview"), 5)) {

            String strPolicyType = dataTable.getData("General_Data", "PolicyType");
            String strCreditCardNo = dataTable.getData("General_Data", "CreditCardNo");
//            String strDOB = dataTable.getData("General_Data", "DOB");
            //need to add the code to change the format
            String strDOB = "01/01/1980";
            String strOperation = dataTable.getData("General_Data", "Operation");
            if (isElementDisplayed(getObject("xpath~//span[contains(text(),'" + strPolicyType + "')]"), 5)) {
//            if (isElementDisplayed(getObject("xpath~//label[text()='Account/policy']/../../following-sibling::*/*[contains(.,'" + strPolicyType + "')]"), 5)) {
                report.updateTestLog("verifyReviewPageCreditCard", "Account/policy correctly displayed on review page,input::" + strPolicyType, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyReviewPageCreditCard", "Account/policy Not correctly displayed on review page,Expected::" + strPolicyType, Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("xpath~//span[contains(text(),'" + strCreditCardNo + "')]"), 5)) {
//            if (isElementDisplayed(getObject("xpath~//label[text()='Credit card number']/../../following-sibling::*/*[contains(.,'" + strCreditCardNo + "']"), 5)) {
                report.updateTestLog("verifyReviewPageCreditCard", "Credit Card Number correctly displayed on review page,input::" + strCreditCardNo, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyReviewPageCreditCard", "Credit Card Number Not correctly displayed on review page,Expected::" + strCreditCardNo, Status_CRAFT.FAIL);
            }

//            if (isElementDisplayed(getObject("xpath~//label[text()='Date of birth']/../../following-sibling::div/descendant::*[text()='" + strDOB + "+']"), 5)) {
            if (isElementDisplayed(getObject("xpath~//span[contains(text(),'" + strDOB + "')]"), 5)) {
                report.updateTestLog("verifyReviewPageCreditCard", "Date of birth correctly displayed on review page,input::" + strDOB, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyReviewPageCreditCard", "Date of birth Not correctly displayed on review page,Expected::" + strDOB, Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("AddPolicy.goBack"), 5)) {
                report.updateTestLog("verifyReviewPageCreditCard", "Go Back button is present", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyReviewPageCreditCard", "Go Back button is not present" + strDOB, Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("AddPolicy.confirm"), 5)) {
                report.updateTestLog("verifyReviewPageCreditCard", "Confirm button is present" + strDOB, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyReviewPageCreditCard", "Confirm button is not present" + strDOB, Status_CRAFT.FAIL);
            }
            click(getObject("xpath~//span[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
        } else {
            report.updateTestLog("verifyReviewPageCreditCard", "Review Page does not exist/loaded properly", Status_CRAFT.FAIL);
        }
    }

    public void verifyAddAccountPolicyReviewPage() throws Exception {
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String strOperation = dataTable.getData("General_Data", "Operation");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {
            if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']/ancestor::div[contains(@class,'question-part boi_label')]/following-sibling::*/descendant::*[text()='" + obj.getValue() + "']"), 5)) {
                report.updateTestLog("verifyAddAccountPolicyReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccountPolicyReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
            }
        }
        if (isElementDisplayed(getObject("xpath~//*[text()='Go back']"), 5)) {
            report.updateTestLog("verifyReviewPageCreditCard", "'Go back' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyReviewPageCreditCard", "'Go back' button is NOT displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("xpath~//*[text()='Confirm']"), 5)) {
            report.updateTestLog("verifyReviewPageCreditCard", "'Confirm' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyReviewPageCreditCard", "'Confirm' button is NOT displayed", Status_CRAFT.FAIL);
        }
        clickJS(getObject("xpath~//*[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
//        click(getObject("xpath~//*[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
    }

    public void verifyAddAccPolicyConfirmationPage() throws Exception {


        if (isElementDisplayed(getObject("AddPolicy.imgSuccess"), 5)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Success Image is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Success Image is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPolicy.lblRequestSent"), 5)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Request Sent' text is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Request Sent' text  is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPolicy.lblThankYou"), 5)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Thank You' text is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Thank You' text is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (!dataTable.getData("General_Data", "PolicyType").equals("Bank of Ireland Life Investment or Savings Plan")) {

            if (isElementDisplayed(getObject("AddPolicy.lblReference"), 5)) {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Reference ' is Displayed on Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Reference ' is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
            }
        }

        if (isElementDisplayed(getObject("AddPolicy.lblTime"), 5)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Time' is Displayed on Confirmation Page", Status_CRAFT.PASS);

        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Time' is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPolicy.btnBackToHome"), 5)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Back To Home' button is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Back To Home' button is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }
    }

    public void verifyElementMaxlength() throws Exception {
        //max length
        int intMaxLength = Integer.parseInt(dataTable.getData("General_Data", "MaxLength"));
        String objName = dataTable.getData("General_Data", "objName");
        verifyMaxLength(intMaxLength, objName);
    }

    public void verifyNOErrorPresent() throws Exception {
        if (!isElementDisplayed(getObject("xpath~//*[contains(@class,'boi_input_sm_error')]"), 5)) {
            report.updateTestLog("verifyNOErrorPresent", "Error Text is Not present on UI", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyNOErrorPresent", "Error Text is present on UI", Status_CRAFT.FAIL);
        }
    }

    public void enterDate(String objDOB, String strDate) throws Exception {

        //Splitting the date
        String strYY = strDate.split("/")[2];
        String strMON = strDate.split("/")[1];
        String strDT = strDate.split("/")[0].replaceAll("^0*", "");

        //Date selection
        clickJS(getObject(objDOB), "Date Object");
        waitForPageLoaded();
        selectDropDown(getObject("launch.lstYearSavingDate"), strYY);
        selectDropDown(getObject("launch.lstMonthSavingDate"), strMON);
        click(getObject("xpath~//a[.='" + strDT + "']"), "Date :" + strDT + " selected");
    }

    public void EnterAddAccPolicyFormBOI_LifeInvestmentorSavingsPlan() throws Exception {
        String strPolicyNo = dataTable.getData("General_Data", "Policy_Number");
        String strEmail = dataTable.getData("General_Data", "Email");
        String strContactDetail = dataTable.getData("General_Data", "VerifyDetails");
        String strPhoneNo = dataTable.getData("General_Data", "Phone_No");

        waitForElementPresent(getObject("AddPolicy.txtPolicyNo"), 10);
        waitForElementPresent(getObject("AddPolicy.txtEmail"), 10);
        sendKeys(getObject("AddPolicy.txtPolicyNo"), strPolicyNo);
        sendKeys(getObject("AddPolicy.txtEmail"), strEmail);
        if (strContactDetail.equalsIgnoreCase("Phone")) {
            selectRadiobutton(By.xpath("//*[@id='C6__C1__QUE_618BBB1BD78DAE8E1987_0']"));
            if (isElementDisplayed(getObject("AddPolicy.txtPhoneNo"), 5)) {
                sendKeys(getObject("AddPolicy.txtPhoneNo"), strPhoneNo);
            } else {
                selectRadiobutton(By.xpath("//*[@id='C6__C1__QUE_618BBB1BD78DAE8E19871_1']"));
            }
        }
        report.updateTestLog("EnterAddAccPolicyFormBOI_LifeInvestmentorSavingsPlan", "Policy No. entered ::" + strPolicyNo, Status_CRAFT.PASS);
        click(getObject("AddPolicy.Continue"), "Clicked Continue Button");
    }


    /**
     * CFPUR - 321,323 : Bapu Joshi
     * Function to navigate from home menu (Footer menu / Header Menu) to various other options
     */

    public void verifyHomePageNaviagtion() throws Exception {
        String strLableToCheck = dataTable.getData("General_Data", "VerifyContent");
        String strNagationToAndFrom = dataTable.getData("General_Data", "VerifyDetails");
        String[] arrContentToCheck = strNagationToAndFrom.split("::");
        report.updateTestLog("verifyHomePageNaviagtion", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);

        // To Check the Lables and Icon only
        if (strLableToCheck.equals("OnlyLableAndIconCheck")) {
            report.updateTestLog("verifyHomePageNaviagtion", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
            String[] arrLableAndIconCheck = strNagationToAndFrom.split("::");

            for (int i = 0; i < arrLableAndIconCheck.length; i++) {
                String[] arrValidationCheck = arrLableAndIconCheck[i].split(",");
                String strIconXpath = arrValidationCheck[0];
                String strLable = arrValidationCheck[1];
                ScrollToVisibleJS(strIconXpath);
                if (isElementDisplayed(getObject(strIconXpath), 15)) {
                    String strActualLable = getText(getObject(strIconXpath));
                    if (strLable.equals("blank")) {
                        strActualLable = "blank";
                    }
                    if (strLable.equals(strActualLable)) {
                        report.updateTestLog("verifyHomePageNaviagtion", "The icone and lable for :: " + strActualLable + ":: displayed on screen correctly.", Status_CRAFT.DONE);
                    } else {
                        report.updateTestLog("verifyHomePageNaviagtion", "Lable for :: " + strActualLable + ":: is not displayed on screen correctly.", Status_CRAFT.FAIL);
                    }
                } else {
                    report.updateTestLog("verifyHomePageNaviagtion", "Icon for :: " + strLable + ":: is not displayed on screen correctly.", Status_CRAFT.FAIL);
                }

            }
        } else if ((strLableToCheck.equals("Navigation_Switching")) || (strLableToCheck.equals("Navigation_BackButton"))) {
            for (int i = 0; i < arrContentToCheck.length; i++) {
                String[] arrNavigationCheck = arrContentToCheck[i].split(",");
                String strNavigateFrom = arrNavigationCheck[0];
                String strNavigateTo = arrNavigationCheck[1];
                String strNavigateToCheckPoint = arrNavigationCheck[2];
                String strNavigateFromCheckPoint = arrNavigationCheck[3];

                if ((strLableToCheck.equals("Navigation_Switching")) && (deviceType.equalsIgnoreCase("MobileWeb"))) {
                    if (!(strNavigateFrom.equals("Accounts"))) {
                        strNavigateFromCheckPoint = "xpath~//div[contains(@class,'ecDIB  boi-mobile-header boi_acc_b text-centerEC_INLINE_STYLE_0')]/div[text()='" + strNavigateFromCheckPoint + "']";
                    }
                    if (!(strNavigateTo.equals("Accounts"))) {
                        strNavigateToCheckPoint = "xpath~//div[contains(@class,'ecDIB  boi-mobile-header boi_acc_b text-centerEC_INLINE_STYLE_0')]/div[text()='" + strNavigateToCheckPoint + "']";
                    }
                    strNavigateFrom = "xpath~//div[contains(@class, 'boi_label_sm')][text()='" + strNavigateFrom + "']";
                    strNavigateTo = "xpath~//div[contains(@class, 'boi_label_sm')][text()='" + strNavigateTo + "']";
                }

                click(getObject(strNavigateFrom));
                click(getObject(strNavigateTo));
                //Check the Navigation form one menu to another
                report.updateTestLog("verifyHomePageNaviagtion", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if ((isElementDisplayed(getObject(strNavigateToCheckPoint), 15)) && (isElementDisplayed(getObject(deviceType() + "home.imgProfileIcon"), 5))) {
                    report.updateTestLog("verifyHomePageNaviagtion", "Navigation from :: " + strNavigateFrom + " ->-> " + strNavigateTo + " :: working as expected with validation check point ::" + strNavigateToCheckPoint, Status_CRAFT.DONE);

                    //Check the Back Navigation for above navigation
                    String strOperation = dataTable.getData("General_Data", "Operation");
                    if (strOperation.equals("BackArrow")) {
                        strNavigateFrom = "xpath~//input[@title='BackNavigationAccOveerview']";
                    }
                    click(getObject(strNavigateFrom));
                    report.updateTestLog("verifyHomePageNaviagtion", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                    if ((isElementDisplayed(getObject(strNavigateFromCheckPoint), 15)) && (isElementDisplayed(getObject(deviceType() + "home.imgProfileIcon"), 5))) {
                        report.updateTestLog("verifyHomePageNaviagtion", "Back Navigation from :: " + strNavigateTo + " ->-> " + strNavigateFrom + " :: working as expected with validation check point ::" + strNavigateFromCheckPoint, Status_CRAFT.DONE);
                    } else {
                        report.updateTestLog("verifyHomePageNaviagtion", "Back Navigation from :: " + strNavigateTo + " ->-> " + strNavigateFrom + " :: is not working as expected with validation check point ::" + strNavigateFromCheckPoint, Status_CRAFT.FAIL);
                    }
                } else {
                    report.updateTestLog("verifyHomePageNaviagtion", "Navigation from :: " + strNavigateFrom + " ->-> " + strNavigateTo + " :: is not working as expected with validation check point ::" + strNavigateToCheckPoint, Status_CRAFT.FAIL);
                }
            }

        }


    }


    /*Credit card info */
    public void verifyCreditCardInfo() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.CreditCardElement"), 5)) {
            report.updateTestLog("VerifyCreditCard", "Credit Card Account Present", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("VerifyCreditCard", "Credit Card Account not Present", Status_CRAFT.PASS);
        }

        //Verifying CreditCard Name
        String CreditCardName = dataTable.getData("General_Data", "Account_Type");
        if (getText(getObject(deviceType() + "home.lblCrediCardName")).equals(CreditCardName)) {
            report.updateTestLog("VerifyCreditCardName", "Credit Card Name Verified", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyCreditCardName", "Credit Card Name not Verified", Status_CRAFT.FAIL);
        }

        //Verify Credit Card Account Format
        String CreditCardFormat = dataTable.getData("General_Data", "FirstName");
        if (getText(getObject(deviceType() + "home.lblCrediCardFormat")).equals(CreditCardFormat)) {
            report.updateTestLog("VerifyCreditCardFormat", "Credit Card Format Verified", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("VerifyCreditCardFormat", "Credit Card Format not Verified", Status_CRAFT.FAIL);
        }

        //Verify Credit Card Account Currency
        String CreditCardCurrency = dataTable.getData("General_Data", "Current_Balance");
        if (getText(getObject(deviceType() + "home.lblCrediCardBalance")).equals(CreditCardCurrency)) {
            report.updateTestLog("VerifyCreditCardCurrency", "Credit Card Currency Verified", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("VerifyCreditCardCurrency", "Credit Card Currency not Verified", Status_CRAFT.FAIL);
        }

        //Verify Bluecard Details
        click(getObject(deviceType() + "home.CreditCardElement"), "for Blue card");
        verifyElementDetails(deviceType() + "myAccount.CreditCardBlueCard");
        String ccAccountOverview = dataTable.getData("General_Data", "VerifyDetails");
        String[] ccContentToCheck = ccAccountOverview.split("::");
        for (int i = 0; i < ccContentToCheck.length; i++) {
            String strCCContent = getText(getObject("myAccount.CreditCardBlueCard"));
            if (strCCContent.contains(ccContentToCheck[i])) {
                report.updateTestLog("BlueCardDetails", " '" + strCCContent + "' correctly displayed on BlueCard", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("BlueCardDetails", " '" + strCCContent + "' not displayed on BlueCard", Status_CRAFT.FAIL);
            }
        }


    }

    public void verifyCreditCardUnavailable() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.CreditCardElement"), 5)) {
            report.updateTestLog("VerifyCreditCard", "Credit Card Account Present", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("VerifyCreditCard", "Credit Card Account not Present", Status_CRAFT.PASS);
        }
    }


    /**
     * General Function
     * Function to Validate The Date format using Regular Expression
     */
    public void ValidateDateFormat(String datDateToValidate, String strDateFormat) throws Exception {
        String regExpDateCheck = "";
        SimpleDateFormat myFormat = new SimpleDateFormat(strDateFormat);
        Date dateBefore = myFormat.parse(datDateToValidate);
        switch (strDateFormat) {

            case "DD MMM YYYY":
                regExpDateCheck = "[0-9]{1,2} [a-zA-Z]{3} [0-9]{4}";
                break;
            case "DD-MM-YYYY":
                regExpDateCheck = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
                break;
            case "MM/DD/YYYY":
                regExpDateCheck = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
                break;
            case "DD/MM/YYYY":
                regExpDateCheck = "(0[1-9]|[12][0-9]|[3][01])/(0[1-9]|1[012])/\\\\d{4}";
                break;
        }

        if (Pattern.matches(regExpDateCheck, datDateToValidate.toString())) {
            report.updateTestLog("ValidateDateFormat", "Date is ::" + datDateToValidate + ":: Matching with Expected Date Format ::" + strDateFormat, Status_CRAFT.DONE);
        } else {
            report.updateTestLog("ValidateDateFormat", "Date is not ::" + datDateToValidate + ":: Matching with Expected Date Format ::" + strDateFormat, Status_CRAFT.FAIL);
        }

    }

    /**
     * General Function
     * Function to Validate Newest Statement First
     */
    public boolean ValidateNewestFirst(String strDateStatement1, String strDateStatement2, String strDateFormat) throws Exception {
        boolean blnReturnValue = false;
        SimpleDateFormat myFormat = new SimpleDateFormat(strDateFormat);
        Date dateBefore = null;
        Date dateAfter = null;
        try {
            dateBefore = myFormat.parse(strDateStatement1);
            dateAfter = myFormat.parse(strDateStatement2);
            long lngDateDifference = dateBefore.getTime() - dateAfter.getTime();
            float fltDaysBetween = (lngDateDifference / (1000 * 60 * 60 * 24));

            if (fltDaysBetween >= 0) {
                blnReturnValue = true;
            } else {
                blnReturnValue = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return blnReturnValue;
    }

    /**
     * CFPUR-69, CFPUR-1388
     * Function to Validate Account Statement Details
     */
    public void VerifyAccountStatement_AllAccounts() throws Exception {

        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");

        if (deviceType() == "tw.") {
            waitForJQueryLoad();
            String strParentObject = "xpath~//*[@class='accessibilityStyle']/following-sibling::span[contains(@class,'boi_input boi-')][text()='" + strAccountIBAN + "']";
            ScrollAndClickJS(strParentObject);
            report.updateTestLog("verifyAccountClickAndNavigate", "Clicked the Account IBAN having value with last four digit :: " + strAccountIBAN, Status_CRAFT.DONE);
            waitForPageLoaded();
        } else {
            List<WebElement> oUIRows = driver.findElements(getObject("home.AccountIBAN_Tile"));
            report.updateTestLog("verifyCheckContentNotPresent", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
            ScrollToVisibleJS("home.AccountIBAN_Tile");

            for (int j = 0; j < oUIRows.size(); j++) {
                String strRowText = oUIRows.get(j).getText().toUpperCase();
                if (strRowText.contains(strAccountIBAN.toUpperCase())) {
                    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                    js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                    report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                    Thread.sleep(2000);
                    if (deviceType.equalsIgnoreCase("web")) {
                        oUIRows.get(j).click();
                    } else {
                        js.executeScript("arguments[0].click();", oUIRows.get(j));
                    }
                    waitForElementPresent(getObject("AccStatement.lblStatementTab"), 10);
                    report.updateTestLog("verifyAccountStatement_AllAccounts", "The Account having Last four digit of IBAN :: " + strAccountIBAN + ":: is displayed at position ::" + (j + 1) + ":: and clicked successfully.", Status_CRAFT.DONE);
                    break;
                }
            }
        }

        waitForElementToClickable(getObject("AccStatement.lblStatementTab"), 10);
        if (deviceType.equalsIgnoreCase("web")) {
            click(getObject("AccStatement.lblStatementTab"), " 'Statements' tab");
        } else {
            clickJS(getObject("AccStatement.lblStatementTab"), " 'Statements' tab");
        }

        if (isElementDisplayed(getObject("ServiceManage.txtServiceUnavailable"), 5)) {
            report.updateTestLog("selectServiceDeskOption", " 'Service unavailable :: Unfortunately, this service is unavailable at this time. Please try again later.' is displyed on Service Desk page", Status_CRAFT.FAIL);
        }

        report.updateTestLog("verifyAccountStatement_AllAccounts", "Statements", Status_CRAFT.DONE);

        if (isElementDisplayed(getObject("AccStatement.lblNoStatementFound"), 8)) { //Object needs to be added
            report.updateTestLog("verifyAccountStatement_AllAccounts", "As no statement is available for this account so message ..'No Statements Found' Displayed correctly.", Status_CRAFT.PASS);
            return;
        } else {
            report.updateTestLog("verifyAccountStatement_AllAccounts", " :: Screenshot Statements :: ", Status_CRAFT.SCREENSHOT);
            List<WebElement> oStatementsIconPDF1 = driver.findElements(getObject(deviceType() + "AccStatement.iconStatementPDF"));
            if (oStatementsIconPDF1.size() >= 1) {
                report.updateTestLog("verifyAccountStatement_AllAccounts", (oStatementsIconPDF1.size()) + " :: Account Statements are Present.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyAccountStatement_AllAccounts", "Statements are not Present.", Status_CRAFT.FAIL);
            }

        }

        //To Handle the device Type
//        String strExecutionMode = scriptHelper.getTestParameters().getExecutionMode().toString();
//        String strDeviceName = scriptHelper.getTestParameters().getDeviceName().toString();
//        if (strExecutionMode.equalsIgnoreCase("LOCAL")) {
//            deviceType = "Web";
//        } else if (strExecutionMode.equalsIgnoreCase("LOCAL_EMULATED_DEVICE")) {
//            deviceType = "MobileWeb";
//            if (strDeviceName.equalsIgnoreCase("Apple iPad")) {
//                deviceType = "Web";
//            }
//        }
        //Code to check Page wise validation
        if (deviceType.equalsIgnoreCase("Web") || deviceType.equalsIgnoreCase("TabletWeb") && (isElementDisplayed(getObject("AccStatement.lblCurrentPageNumber"), 5))) {
            //To Calculate Number of pages
            for (int k = 1; k < 20; k++) {
                String strCurrentFirstPage = "xpath~//span[@id='ANCHOR_TABLE_73A800B0B8F80D69_FormTable_8_PAG_BOTTOM_" + k + "']";
                String strOtherThanCurrentPage = "xpath~//a[@class='tc-table-nav-item tc-ripple-effect boi-table-nav-item boi_widget_sm_blue'][text()='" + k + "']";
                if ((isElementDisplayed(getObject(strCurrentFirstPage), 5))) {
                    verifyStatementInfo(strCurrentFirstPage, k);
                } else if ((isElementDisplayed(getObject(strOtherThanCurrentPage), 5))) {
                    verifyStatementInfo(strOtherThanCurrentPage, k);
                } else {
                    report.updateTestLog("verifyAccountStatement_AllAccounts", "Total Number of Pages :" + (k - 1), Status_CRAFT.DONE);
                    break;
                }
            }
        } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
            verifyStatementInfo(deviceType() + "AccStatement.lblShowMore", 1);
        } else {
            verifyStatementInfo("AccStatement.lblStatementTab", 1);
        }
    }

    public void VerifyPaperlessStatementPromotionInfo() throws Exception {
        waitforVisibilityOfElementLocated(getObject("launch.btnGoBack"));
        if (isElementDisplayed(getObject("AccStatement.lblStatementTab"), 5)) {
            report.updateTestLog("Statement Tab", "Statement Tab is present", Status_CRAFT.PASS);
            click(getObject("AccStatement.lblStatementTab"));
        } else {
            report.updateTestLog("Statement Tab", "Statement Tab is not present", Status_CRAFT.FAIL);
        }
        waitforVisibilityOfElementLocated(getObject("ManageStatements.lblRecentStatements"));

        if (isElementDisplayed(getObject("PaperlessStmtPromotionIcon"), 5)) {
            report.updateTestLog("PaperlessStmtPromotionIcon", "Paperless Statement Promotion is displayed", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("PaperlessStmtPromotionIcon", "Paperless Statement Promotion is not displayed", Status_CRAFT.DONE);
        }

        clickJS(getObject("Transaction.tabAccounts"), "Accounts");
        //   waitForPageLoaded();
        waitforVisibilityOfElementLocated(getObject("w.home.titleMyAccounts"));
    }


    /***/
    /*  Function to Validate Account Statement Details
     */
    public void verifyStatementInfo(String strPageToClick, int intPageNumber) throws Exception {
        String strStatementListParent = deviceType() + "AccStatement.tblParent";
        List<WebElement> oStatements = driver.findElements(getObject(strStatementListParent));
        int intNumberOfStatement = oStatements.size();
        if (deviceType.equalsIgnoreCase("Web")) {
            if (intPageNumber > 1) {
                ScrollAndClickJS(strPageToClick);
            }
            Thread.sleep(5000);
            //12 Statements per page
            intNumberOfStatement = oStatements.size();
            if (intNumberOfStatement <= 12) {
                report.updateTestLog("verifyStatementInfo", "On Page Number : " + intPageNumber + ":: " + intNumberOfStatement + " :: Statements are Present.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyStatementInfo", "On Page Number : " + intPageNumber + ":: " + intNumberOfStatement + " :: Statements are Present.", Status_CRAFT.FAIL);
            }

            //Validate the PDF Icon
            List<WebElement> oStatementsIconPDF = driver.findElements(getObject(deviceType() + "AccStatement.iconStatementPDF"));

            if (oStatementsIconPDF.size() <= 12) {
                report.updateTestLog("verifyStatementInfo", "On Page Number : " + intPageNumber + ":: " + intNumberOfStatement + " :: Number of PDF icons present are :" + oStatementsIconPDF.size(), Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyStatementInfo", "On Page Number : " + intPageNumber + ":: " + intNumberOfStatement + " :: Number of PDF icons present are not present", Status_CRAFT.FAIL);
            }

        } else if (deviceType.equalsIgnoreCase("MobileWeb")) {

            // Check the number of Statemnets Present when statemnet page opens first
            if (intNumberOfStatement <= 12) {
                report.updateTestLog("verifyStatementInfo", "On Mobile Page Number : " + intPageNumber + ":: " + intNumberOfStatement + " :: 12-Statements are Present.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyStatementInfo", "On Mobile Page Number : " + intPageNumber + ":: " + intNumberOfStatement + " :: 12-Statements are Present.", Status_CRAFT.FAIL);
            }

            //Click on showmore while show more element is present
            int intCounter = 2;
            do {
                ScrollAndClickJS(deviceType() + "AccStatement.lblShowMore");
                if (intNumberOfStatement <= (12 * intCounter)) {
                    report.updateTestLog("verifyStatementInfo", "On Mobile Page Number : " + intCounter + ":: " + intNumberOfStatement + " :: 12-Statements are Present.", Status_CRAFT.DONE);
                    intCounter++;
                } else {
                    report.updateTestLog("verifyStatementInfo", "On Mobile Page Number : " + intCounter + ":: " + intNumberOfStatement + " :: 12-Statements are Present.", Status_CRAFT.FAIL);
                }

            } while (isElementDisplayed(getObject(deviceType() + "AccStatement.lblShowMore"), 10));

            //Validate the PDF Icon
            ScrollToVisibleJS(strStatementListParent);
            List<WebElement> oStatementsIconPDF = driver.findElements(getObject(deviceType() + "AccStatement.iconStatementPDF"));

            if (oStatementsIconPDF.size() > 1) {
                report.updateTestLog("verifyStatementInfo", "On Mobile Statement page number of PDF icons present are :" + oStatementsIconPDF.size(), Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyStatementInfo", "On Mobile Statement page number of PDF icons present are not present", Status_CRAFT.FAIL);
            }
        }

        //Newest statements should appears first :
        List<WebElement> oStatementsLabel = driver.findElements(getObject(deviceType() + "AccStatement.lblStatementTitle"));
        ScrollToVisibleJS(strStatementListParent);
        if (oStatementsLabel.size() > 2) {
            String strDateStatement1 = oStatementsLabel.get(1).getText().toUpperCase();
            String strDateStatement2 = oStatementsLabel.get(2).getText().toUpperCase();
            ValidateDateFormat(strDateStatement1, "dd/MM/yyyy");
            boolean blnNewestFirst = ValidateNewestFirst(strDateStatement1, strDateStatement2, "dd/MM/yyyy");
            if (blnNewestFirst) {
                report.updateTestLog("verifyStatementInfo", "On Page Number : " + intPageNumber + ":: Newest Statement appears first.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyStatementInfo", "On Page Number : " + intPageNumber + ":: Newest Statement did not appears first.", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyStatementInfo", "On Page Number : " + intPageNumber + " :: Only one statement is present so can not check the Newest statement first validation.", Status_CRAFT.DONE);
        }


        //Verify the Which Statement Type is present as per account ; Including Negative Check
        verifyStatementType();
    }

    /**
     * Function to Validate Account Statement Type
     */
    public void verifyStatementType() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Nickname");
        String strAccountRegion = dataTable.getData("General_Data", "Account_Label");
        String strStatmentType = "";
        boolean blnCheckStatement = false;

        List<WebElement> oStatementsType = driver.findElements(getObject(deviceType() + "AccStatement.lblStatementType"));

        for (int i = 0; i < oStatementsType.size(); i++) {
            String strStatementText = oStatementsType.get(i).getText();

            switch (strAccountToCheck) {

                case "Current Account":
                    if (strAccountRegion.equals("ROI")) {
                        blnCheckStatement = strStatementText.contains("Account Statement") || strStatementText.contains("Fee Statement")
                                || strStatementText.contains("Interest Statement") || strStatementText.contains("Overlimit Items Statement")
                                || strStatementText.contains("Pre-notified Fee Statement") || strStatementText.contains("Pre-notified Interest Statement")
                                || strStatementText.contains("Statement of Fees");
                    } else if (strAccountRegion.equals("NI/GB")) {
                        blnCheckStatement = strStatementText.contains("Account Statement") || strStatementText.contains("Fee Statement")
                                || strStatementText.contains("Interest Statement")
                                || strStatementText.contains("Pre-notified Fee Statement") || strStatementText.contains("Pre-notified Interest Statement")
                                || strStatementText.contains("Statement of Fees");
                    } else if (strAccountRegion.equals("Negative_Check_NI/GB")) {
                        strStatmentType = "Overlimit Items Statement, Monthly Summary Statement, Monthly Statement, Annual Summary Statement";
                        blnCheckStatement = strStatementText.contains("Overlimit Items Statement") || strStatementText.contains("Monthly Summary Statement")
                                || strStatementText.contains("Monthly Statement") || strStatementText.contains("Annual Summary Statement");

                        blnCheckStatement = !(blnCheckStatement);
                    }

                    break;

                case "Savings Account":
                    if ((strAccountRegion.equals("ROI")) || (strAccountRegion.equals("NI/GB"))) {
                        blnCheckStatement = strStatementText.contains("Account Statement")
                                || strStatementText.contains("Statement of Fees");
                    } else if (strAccountRegion.equals("Negative_Check")) {
                        strStatmentType = "Overlimit Items Statement, Fee Statement, Interest Statement, Overlimit Items Statement,Pre-notified Fee Statement, Pre-notified Interest Statement ";
                        blnCheckStatement = strStatementText.contains("Fee Statement")
                                || strStatementText.contains("Interest Statement") || strStatementText.contains("Overlimit Items Statement")
                                || strStatementText.contains("Pre-notified Fee Statement") || strStatementText.contains("Pre-notified Interest Statement");
                        blnCheckStatement = !(blnCheckStatement);
                    }

                    break;

                case "Term Loan":
                    blnCheckStatement = strStatementText.contains("Account Statement");
                    break;

                case "CreditCard":
                    blnCheckStatement = strStatementText.contains("Monthly Statement") || strStatementText.contains("Annual Summary Statement");
                    break;
            }

            if (blnCheckStatement) {
                if (strAccountRegion.contains("Negative")) {
                    report.updateTestLog("verifyStatementType", "As Expected for Account Type :: " + strAccountToCheck + " With Jurisdiction :: " + strAccountRegion + " :: Statement Type :: " + strStatementText + ":: is not available.", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyStatementType", "As Expected for Account Type :: " + strAccountToCheck + " With Jurisdiction :: " + strAccountRegion + " :: Statement Type :: " + strStatementText + ":: is available.", Status_CRAFT.DONE);
                }
            } else {
                if (strStatementText.equals("Turn off paper statements") && i == 0) {
                    report.updateTestLog("verifyStatementType", "If 'Turn off paper statements' is ON for Account with Region ::" + strAccountToCheck + "-" + strAccountRegion + ":: then the first Statement Type Would be ::" + strStatementText + ":: is available.", Status_CRAFT.DONE);
                    //} else if (strStatementText.length() < 1 && i == 0) { --Remove It All TC Passed
                    //    report.updateTestLog("verifyStatementType", "If 'Turn off paper statements' is Off for Account with Region ::" + strAccountToCheck + "-" + strAccountRegion + ":: then the first Statement Type Would be 'Blank'.", Status_CRAFT.DONE);
                } else if (strAccountRegion.contains("Negative")) {
                    report.updateTestLog("verifyStatementType", "As expected for Account Type :: " + strAccountToCheck + " With Jurisdiction :: " + strAccountRegion + " :: Statement Type :: " + strStatementText + ":: is available.", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyStatementType", "Account with region ::" + strAccountToCheck + "-" + strAccountRegion + ":: Statement Type ::" + strStatementText + ":: is ..available..Which is not expected....!!", Status_CRAFT.FAIL);
                }
            }
            blnCheckStatement = false;
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
            //report.updateTestLog("ScrollAndClickJS", "Element To Click", Status_CRAFT.DONE);
            JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
            executor.executeScript("arguments[0].click();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(3000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
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
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    public void validateOptsTextDisplydDesktop() throws Exception {

        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails(deviceType() + "home.lblPageSection");
    }

    /**
     * Funtion to select PAYMENTS options on home page
     *
     * @throws Exception
     */
    public void selectPaymentOption() throws Exception {
        String strPaymentType = dataTable.getData("General_Data", "PaymentType");
        while (isElementDisplayed(getObject("xpath~//[@class='spinner']"), 4)) {
            waitForPageLoaded();
        }
        if (deviceType.equalsIgnoreCase("Web")) {
            isElementDisplayedWithLog(getObject(deviceType() + "home.tabPayments"),
                    "Payments Tab", "Payments", 5);
            clickJS(getObject(deviceType() + "home.tabPayments"), "'Payments Tab'");
            if (strPaymentType.equalsIgnoreCase("Transfer between my accounts") || strPaymentType.equalsIgnoreCase("Send money or pay a bill")) {
                clickJS(getObject("xpath~//*[contains(text(),'" + strPaymentType + "')]"), "The'" + strPaymentType + "'");
                report.updateTestLog("selectPaymentOption", "'" + strPaymentType + "' Payment type selected on Home Page", Status_CRAFT.PASS);
            } else {
                String myxpath = "//a/descendant::*[text()='" + strPaymentType + "']";
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(myxpath)));
                Thread.sleep(2000);
                clickJS(getObject("xpath~" + myxpath), "mobile device payments");
            }
            report.updateTestLog("selectPaymentOption", "'" + strPaymentType + "' Payment type selected on Home Page", Status_CRAFT.PASS);
        } else {
            waitForElementToClickable(getObject(deviceType() + "home.tabPayments"), 5);
            waitForJQueryLoad();
            waitForPageLoaded();
            clickJS(getObject(deviceType() + "home.tabPayments"), "'Payments Tab'");
            waitForJQueryLoad();
            waitForPageLoaded();
            if (strPaymentType.equalsIgnoreCase("Transfer between my accounts") ||
                    strPaymentType.equalsIgnoreCase("Send money or pay a bill")) {
                waitForJQueryLoad();
                waitForPageLoaded();
                clickJS(getObject("xpath~//*[contains(text(),'" + strPaymentType + "')]"), "Click '" + strPaymentType + "'");
                waitForJQueryLoad();
                waitForPageLoaded();
            } else {

                clickJS(getObject("xpath~//*[text()='" + strPaymentType + "']"), "Click '" + strPaymentType + "'");

                report.updateTestLog("selectPaymentOption", "'" + strPaymentType + "' Payment type selected on Home Page", Status_CRAFT.PASS);
            }
        }
        Thread.sleep(2000);
    }

    public void verifyPopUpAndClickOnLinkMwLogout(String strLinkToClick, String strDlgToValidate, String strlinkToClickOnPopUp) throws Exception {
        String linkToVerify = dataTable.getData("General_Data", "VerifyDetails");
        String strContentToCheck = dataTable.getData("General_Data", "FirstName");
        String[] arrContentToCheck = strContentToCheck.split("::");
        if (isElementDisplayed(getObject(strLinkToClick), 5)) {
            clickJS(getObject(strLinkToClick), "logout clicked");
            if (isElementDisplayed(getObject(strDlgToValidate), 10)) {
                //verifyPopUpDetails();
                report.updateTestLog("verifyPopUpAndClickOnLink", "The Title and Content ", Status_CRAFT.SCREENSHOT);
                // To Verify Title
                String strTitleXpath = "xpath~//div[@class='dialog-content']/descendant::h1[text()='" + arrContentToCheck[0] + "']";

                if (isElementDisplayed(getObject(strTitleXpath), 1)) {
                    report.updateTestLog("verifyPopUpAndClickOnLink", "The title of the Dialog :: " + arrContentToCheck[0] + ":: is correctly displyed.", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyPopUpAndClickOnLink", "The title of the Dialog :: " + arrContentToCheck[0] + ":: is not correctly displyed.", Status_CRAFT.FAIL);
                }

                //Validate the content
                for (int i = 1; i < arrContentToCheck.length; i++) {
                    String strContentXpath = "xpath~//div[@id='C5__C1__FMT_CFE6D34A664518F1456092']/descendant::*[text()='" + arrContentToCheck[i] + "']";
                    if (isElementDisplayed(getObject(strContentXpath), 1)) {
                        report.updateTestLog("verifyPopUpAndClickOnLink", "The Content of the Dialog :: " + arrContentToCheck[i] + ":: is correctly displyed.", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyPopUpAndClickOnLink", "The Content of the Dialog :: " + arrContentToCheck[i] + ":: is not correctly displyed.", Status_CRAFT.FAIL);
                    }
                }
                if (deviceType.equals("web")) {
                    if (linkToVerify.contains("https://")) {
                        verifyNewlyOpenedTab(linkToVerify);
                    }
                }
                //Click on Ok button
                click(getObject(strlinkToClickOnPopUp), "Yes, log me out");
            }
        }
    }

    /**
     * Function : Verify the Credit Card Details
     * Created on    Created by     Reason
     * 15/04/2019   C966119
     */
    public void verifyCreditCardAccountDetails() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = deviceType() + "home.lblAccountListRows";
        report.updateTestLog("verifyNavigateToAccountPage", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        //isMobile>>TODO need to check the impact-Rajiv
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            String strAccountTile = "xpath~//div[contains(@class,'tc-ripple-effect  dtr-details prevent-enter  textRole')]/descendant::*[text()='" + strAccountToCheck + "']";
            if (isElementDisplayed(getObject(strAccountTile), 3)) {
                report.updateTestLog("verifyCreditCardAccountDetails", "The Credit Card number " + strAccountIBAN + " with " + strAccountToCheck + " status displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyCreditCardAccountDetails", "The Credit Card number " + strAccountIBAN + " with " + strAccountToCheck + " status is not displayed", Status_CRAFT.FAIL);
            }
            //To verify the CC account tile should not be clickable
            if (isElementclickable(getObject(strAccountTile), 3)) {
                report.updateTestLog("verifyCreditCardAccountTile", "The Credit Card account tile is clickable which is not expected", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyCreditCardAccountTile", "The Credit Card account tile is not clickable which is as expected", Status_CRAFT.PASS);
            }
        } else {
            //List of all the Accounts
            List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
            ScrollToVisibleJS(strParentObject);
            for (int j = 0; j < oUIRows.size(); j++) {
                String strRowText = oUIRows.get(j).getText().toUpperCase();
                if ((strRowText.contains(strAccountToCheck.toUpperCase())) && (strRowText.contains(strAccountIBAN.toUpperCase()))) {
                    report.updateTestLog("verifyCreditCardAccountDetails", "The Credit Card with " + strAccountToCheck + " status displayed successfully", Status_CRAFT.PASS);
                }
            }
        }
    }

    /**
     * Function to verify login detail fields for E2E flow
     */
    public void verifyUserNameE2E() throws Exception {
        click(getObject("launch.btnContinue"));
        //validation of DOB as password
        if (isElementDisplayed(getObject("launch.edtDateOfBirth"), 2)) {
            click(getObject("launch.btnContinue"));
            Thread.sleep(2000);
            String ActualErrorUserMsg = getText(getObject(deviceType() + "home.userIdErrormsg"));
            String ExpectedToolUserMsg = "No user ID entered, please try again";
            if (ActualErrorUserMsg.equals(ExpectedToolUserMsg)) {
                report.updateTestLog("Verify UserID null validation", "UserID null validation verified successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Verify UserID null validation", "UserID null validation not verified  successfully", Status_CRAFT.FAIL);
            }

            String ActualErrorDOBMsg = getText(getObject(deviceType() + "home.DOBErrormsg"));
            String ExpectedToolDOBMsg = "Invalid date, please try again. Date must be in format DD/MM/YYYY";
            if (ActualErrorDOBMsg.equals(ExpectedToolDOBMsg)) {
                report.updateTestLog("Verify DOB null validation", "DOB null validation verified successfully", Status_CRAFT.PASS);

            } else {
                report.updateTestLog("Verify DOB null validation", "DOB null validation not verified  successfully", Status_CRAFT.FAIL);
            }
            Thread.sleep(3000);
            sendKeys(getObject("launch.edtDateOfBirth"), dataTable.getData("Login_Data", "DOB"), "Date of Birth");
            click(getObject("launch.btnContinue"));
        }
        //validation of Contact number as password
        else {
            isElementDisplayed(getObject("launch.edtPhoneNumberDigits"), 2);
            click(getObject("launch.btnContinue"));
            Thread.sleep(3000);
            String ActualErrorUserMsg = getText(getObject(deviceType() + "home.userIdErrormsg"));
            String ExpectedToolUserMsg = "No user ID entered, please try again";
            if (ActualErrorUserMsg.equals(ExpectedToolUserMsg)) {
                report.updateTestLog("Verify UserID null validation", "UserID null validation verified successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Verify UserID null validation", "UserID null validation not verified  successfully", Status_CRAFT.FAIL);
            }
            String ActualErrorPINMsg = getText(getObject(deviceType() + "home.PINErrormsg"));
            //This needs to changes as per mainframe
            String ExpectedToolPINMsg = "No contact number, please try again";
            if (ActualErrorPINMsg.equals(ExpectedToolPINMsg)) {
                report.updateTestLog("Verify Contact number null validation", "Contact number null validation verified successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Verify Contact number null validation", "Contact number null validation not verified successfully", Status_CRAFT.FAIL);
            }
            Thread.sleep(3000);
        }

        sendKeys(getObject("launch.edtUserName"), dataTable.getData("General_Data", "User_ID_Less_Digit"), "Username");
        click(getObject("launch.btnContinue"));
        String ActualErrorUserMsgLessDigitUserName = getText(getObject(deviceType() + "home.userIdErrormsg"));
        String ExpectedToolUserMsgLessDigitUserName = "Invalid user id, please try again";
        if (ActualErrorUserMsgLessDigitUserName.equals(ExpectedToolUserMsgLessDigitUserName)) {
            report.updateTestLog("Verify UserID less digit validation", "UserID with less than 6 digit validation verified successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Verify UserID less digit validation", "UserID with less than 6 digit validation not verified successfully", Status_CRAFT.FAIL);
        }

        sendKeys(getObject("launch.edtUserName"), dataTable.getData("Login_Data", "Username"), "Username");
    }

    public void verifyPhoneDOBE2E() throws Exception {
        if (isElementDisplayed(getObject("launch.edtPhoneNumberDigits"), 2)) {
            Thread.sleep(3000);
            //validation of less than 4 digit contact number
            sendKeys(getObject("launch.edtPhoneNumberDigits"), dataTable.getData("General_Data", "Contact_Num_Less_Digit"), "Contact Number");
            click(getObject("launch.btnContinue"));
            String ActualErrorUserMsgLessDigitPhone = getText(getObject(deviceType() + "home.PINErrormsg"));
            String ExpectedToolUserMsgLessDigitPhone = "Invalid contact number, please try again";
            if (ActualErrorUserMsgLessDigitPhone.equals(ExpectedToolUserMsgLessDigitPhone)) {
                report.updateTestLog("Verify Contact number null validation", "Contact Number with less than 4 digit validation verified successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Verify Contact number null validation", "Contact Number with less than 4 digit validation not verified successfully", Status_CRAFT.FAIL);
            }
            sendKeys(getObject("launch.edtPhoneNumberDigits"), dataTable.getData("Login_Data", "PhoneNumberDigits_DGAPP"), "Contact Number");
            click(getObject("launch.btnContinue"));
        } else if (isElementDisplayed(getObject("launch.edtDateOfBirth"), 2)) {
            Thread.sleep(3000);
            sendKeys(getObject("launch.edtDateOfBirth"), dataTable.getData("General_Data", "Invalid_DOB"), "Date of Birth");
            click(getObject("launch.btnContinue"));
            String ActualErrorDOBMsg = getText(getObject(deviceType() + "home.DOBErrormsg"));
            String ExpectedToolDOBMsg = "Invalid date, please try again. Date must be in format DD/MM/YYYY";
            if (ActualErrorDOBMsg.equals(ExpectedToolDOBMsg)) {
                report.updateTestLog("Verify Invalid DOB validation", "Invalid DOB validation successfull", Status_CRAFT.PASS);

            } else {
                report.updateTestLog("Verify Invalid DOB validation", "Invalid DOB validation not successfull", Status_CRAFT.FAIL);
            }

            Thread.sleep(3000);
            driver.findElement(getObject("launch.edtDateOfBirth")).clear();
            Thread.sleep(3000);
            sendKeys(getObject("launch.edtDateOfBirth"), dataTable.getData("General_Data", "Invalid_DOB_Chars"), "Date of Birth");
            click(getObject("launch.btnContinue"));
            String ActualErrorDOBMsg1 = getText(getObject(deviceType() + "home.DOBErrormsg"));
            String ExpectedToolDOBMsg1 = "Invalid date, please try again. Date must be in format DD/MM/YYYY";
            if (ActualErrorDOBMsg1.equals(ExpectedToolDOBMsg1)) {
                report.updateTestLog("Verify Invalid DOB validation", "Invalid DOB with character validation successfull", Status_CRAFT.PASS);

            } else {
                report.updateTestLog("Verify Invalid DOB validation", "Invalid DOB with character validation not successfull", Status_CRAFT.FAIL);
            }

            Thread.sleep(3000);
            driver.findElement(getObject("launch.edtDateOfBirth")).clear();
            Thread.sleep(3000);
            sendKeys(getObject("launch.edtDateOfBirth"), dataTable.getData("Login_Data", "DOB"), "Date of Birth");
            click(getObject("launch.btnContinue"));
        }
    }

    public void VerifyPINE2E() throws Exception {
        //To verify error message if null values entered in PIN
        verifyPINFields();
        String[] arrPin = dataTable.getData("General_Data", "Invalid_PIN").split("");
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("launch.edtPinEnterFieldGrp"));

        //Entering values for only enabled fields
        int j = 0;
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                j++;
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                if (j == 2) {
                    break;
                }
            }
        }
        click(getObject("w.home.btnLogin"), "Login");
        String ActualErrorWrongPIN = getText(getObject("home.lblPINErrormsg"));
        String ExpectedErrorWrongPIN = "You did not enter the 3 requested digits of your PIN! Your request HAS NOT been submitted. Please try again";

        if (ActualErrorWrongPIN.equals(ExpectedErrorWrongPIN)) {
            report.updateTestLog("Verify PIN digits validation", "Less than 3 digit of PIN validation verified successfully", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("Verify PIN digits validation", "Less than 3 digit of PIN validation is not verified successfully", Status_CRAFT.FAIL);
        }
        String[] arrPinChars = dataTable.getData("General_Data", "Invalid_PIN_Chars").split("");
        List<WebElement> lstPinEnterFieldGrpChars = findElements(getObject("launch.edtPinEnterFieldGrp"));

        //Entering values for only enabled fields
        int k = 0;
        for (int i = 0; i < lstPinEnterFieldGrpChars.size(); i++) {
            if (lstPinEnterFieldGrpChars.get(i).isEnabled()) {
                k++;
                lstPinEnterFieldGrpChars.get(i).sendKeys(arrPinChars[i]);
                if (k == 2) {
                    break;
                }
            }
        }
        click(getObject("w.home.btnLogin"), "Login");
        String ActualErrorWrongPINChar = getText(getObject("home.lblPINErrormsg"));
        String ExpectedErrorWrongPINChar = "You did not enter the 3 requested digits of your PIN! Your request HAS NOT been submitted. Please try again";

        if (ActualErrorWrongPINChar.equals(ExpectedErrorWrongPINChar)) {
            report.updateTestLog("Verify PIN digits null validation", "Less than 3 digit of PIN validation verified successfully", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("Verify PIN digits null validation", "Less than 3 digit of PIN validation is not verified successfully", Status_CRAFT.FAIL);
        }
        //To Login with Valid PIN
        enterRequiredPin();

    }

    /*
    Function to verify invalid pin error message
     */
    public void verifyPIN() throws Exception {

        // To Verify error message if wrong PIN is entered
        String[] arrPin = dataTable.getData("General_Data", "Invalid_PIN").split("");
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("launch.edtPinEnterFieldGrpSCA"));

        //Entering values for only enabled fields
        int j = 0;
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                j++;
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                if (j == 2) {
                    break;
                }
            }
        }
        click(getObject("w.home.btnLogin"), "Login");
        String ActualErrorWrongPIN = getText(getObject("home.lblPINErrormsg"));
        String ExpectedErrorWrongPIN = "You did not enter the 3 requested digits of your PIN! Your request HAS NOT been submitted. Please try again";

        if (ActualErrorWrongPIN.equals(ExpectedErrorWrongPIN)) {
            report.updateTestLog("Verify PIN digits validation", "Error message as expected: " + ActualErrorWrongPIN, Status_CRAFT.DONE);
        } else {
            report.updateTestLog("Verify PIN digits validation", "Error message is not getting displayed", Status_CRAFT.FAIL);
        }

    }

    /**
     * Function to logout feedback details:
     */

    /**
     * Function to veify back navigation and again click on selected account
     */
    public void clickAccountTab() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.titleMyAccounts"), 5)) {
            report.updateTestLog("NickNameOnPages", "Menu Tab 'Accounts' is displayed", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "home.titleMyAccounts"), "Click on Menu Tab 'Accounts'");
        } else {
            report.updateTestLog("NickNameOnPages", "Menu Tab 'Accounts' is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function verify Nickname of Transaction & Home Page
     */
    public void NickNameOnPages() throws Exception {
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            //navigate and validate on Transaction Page
            if (isElementDisplayed(getObject("xpath~//span[text()='Transactions']"), 5)) {
                report.updateTestLog("NickNameOnPages", "Menu Tab 'Transaction' is displayed", Status_CRAFT.PASS);
                clickJS(getObject("xpath~//input[@name='C5__CONNECT_ACTIVE_TAB']/../div/div/span[text()='Transactions']"), "Click on Menu Tab 'Transaction'");
            }
            String strAccountName = dataTable.getData("General_Data", "NewNicknameToVerify");
            if (isElementDisplayed(getObject(deviceType() + "Transaction.hdrAccount"), 5)) {
                if (getText(getObject(deviceType() + "Transaction.hdrAccount")).equalsIgnoreCase(strAccountName)) {
                    report.updateTestLog("NickNameOnPages", "Account Number: '" + strAccountName + "' correctly displayed on header of Transaction Page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("NickNameOnPages", "Account Number: '" + strAccountName + "' is not correctly displayed on header of Transaction Page", Status_CRAFT.FAIL);
                }
            }
        } else {
            //navigate and validate on Transaction Page
            if (isElementDisplayed(getObject("xpath~//a[text()='Transactions']"), 5)) {
                report.updateTestLog("NickNameOnPages", "Menu Tab 'Transaction' is displayed", Status_CRAFT.PASS);
                clickJS(getObject("xpath~//a[text()='Transactions']"), "Click on Menu Tab 'Transaction'");
            } else {
                report.updateTestLog("NickNameOnPages", "Menu Tab 'Transaction ' is NOT displayed", Status_CRAFT.FAIL);
            }
            String strAccountName = dataTable.getData("General_Data", "NewNicknameToVerify") + " ~ " + dataTable.getData("General_Data", "AccountNumber");
            if (isElementDisplayed(getObject(deviceType() + "Transaction.hdrAccount"), 5)) {
                if (getText(getObject("Transaction.hdrAccount")).equalsIgnoreCase(strAccountName)) {
                    report.updateTestLog("NickNameOnPages", "Account Number: '" + strAccountName + "' correctly displayed on header of Transaction Page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("NickNameOnPages", "Account Number: '" + strAccountName + "' is not correctly displayed on header of Transaction Page", Status_CRAFT.FAIL);
                }
            }
        }

        //navigate and validate on HomePage Account Details

        if (isElementDisplayed(getObject(deviceType() + "home.titleMyAccounts"), 10)) {
            report.updateTestLog("NickNameOnPages", "'ACCOUNTS' Tab is displayed", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "home.titleMyAccounts"), "Click on Account Tabs");
            waitForJQueryLoad();
            waitForPageLoaded();
        } else {
            report.updateTestLog("NickNameOnPages", "'ACCOUNTS' Tab is NOT displayed", Status_CRAFT.FAIL);
        }
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            boolean bflag = false;
            String strUiText;
            String accno = dataTable.getData("General_Data", "AccountNumber");
            String accType = dataTable.getData("General_Data", "NewNicknameToVerify");
            List<WebElement> temp = driver.findElements(By.xpath("//div[contains(@class,'responsive-row boi-margin-accountdetails')]"));
            if (temp.size() != 0) {
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).isDisplayed()) {
                        strUiText = temp.get(i).getText();
                        if (strUiText.contains(accType)) {
                            report.updateTestLog("NickNameOnPages", "Account Type : '" + accType + " ' is present on Home Page", Status_CRAFT.PASS);
                            bflag = true;
                        }
                    }
                }
            }
            if (!bflag) {
                report.updateTestLog("NickNameOnPages", "Account Type : '" + accType + " ' is not present on Home Page", Status_CRAFT.FAIL);
            }
        } else {
            boolean bflag = false;
            String strUiText;
            String accType = dataTable.getData("General_Data", "NewNicknameToVerify");
            String accno = dataTable.getData("General_Data", "AccountNumber");
            List<WebElement> temp = driver.findElements(By.xpath("//div[contains(@class,'responsive-row boi-margin-accountdetails')]"));
            if (temp.size() != 0) {
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).isDisplayed()) {
                        strUiText = temp.get(i).getText();
                        if (strUiText.contains(accType) && strUiText.contains(accno)) {
                            report.updateTestLog("NickNameOnPages", "Account Type : '" + accType + " ' is present on Home Page", Status_CRAFT.PASS);
                            bflag = true;
                        }
                    }
                }
            }
            if (!bflag) {
                report.updateTestLog("NickNameOnPages", "Account Type : '" + accType + " ' is not present on Home Page", Status_CRAFT.FAIL);
            }
        }
    }

    public void verifyAcctTileDisplayed(String accountName) throws Exception {
        String Val = "";
        String nickName = dataTable.getData("General_Data", "Nickname");
        Val = getText(getObject(accountName));
        if (Val.equalsIgnoreCase(nickName)) {
            report.updateTestLog("verifyAccountDisplayed", nickName + " account is displayed : ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAccountNotDisplayed", nickName + " account is not displayed ", Status_CRAFT.FAIL);
        }
    }


    public void LoginDetailsDeleteTextOption() throws InterruptedException {
        String user = dataTable.getData("Login_Data", "Username");
        if (user.length() == 6 || user.length() == 8) {
            click(getObject("launch.edtUserName"));
            Thread.sleep(3000);
            sendKeys(getObject("launch.edtUserName"), dataTable.getData("Login_Data", "Username"), "Username");

            if (isElementDisplayed(getObject("launch.edtUserNameClearIcon"), 5)) {
                click(getObject("launch.edtUserNameClearIcon"));
                report.updateTestLog("clearUsername", "X is displayed and clicked on Username text box", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("clearUsername", "X is not displayed on Username text box", Status_CRAFT.FAIL);
            }

        }

        if (isElementDisplayed(getObject("launch.edtDateOfBirth"), 2)) {
            String strSavingDate = dataTable.getData("Login_Data", "DOB");
            String waterMark = driver.findElement(getObject("launch.edtDateOfBirth")).getAttribute("placeholder");
            if (waterMark.equals("DD/MM/YYYY")) {
                report.updateTestLog("WaterMark for DOB", " WaterMark for DOB verified successfully", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("WaterMark for DOB", " WaterMark for DOB  not verified successfully", Status_CRAFT.FAIL);
            }
            //Date selection
            waitForPageLoaded();
            Thread.sleep(1500);
            click(getObject("launch.edtDateOfBirth"), "DOB");
            waitForPageLoaded();
            sendKeys(getObject("launch.edtDateOfBirth"), strSavingDate);

            if (isElementDisplayed(getObject("launch.edtDOBClearIcon"), 5)) {
                click(getObject("launch.edtDOBClearIcon"));
                report.updateTestLog("clearDOB", "X is displayed and clicked on DOB text box", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("clearDOB", "X is not displayed on DOB text box", Status_CRAFT.FAIL);
            }


        } else {
            Thread.sleep(1500);
            String waterMark = driver.findElement(getObject("launch.edtPhoneNumberDigits")).getAttribute("placeholder");
            if (waterMark.equals("Enter Last 4 Digits")) {
                report.updateTestLog("WaterMark for Contact", " WaterMark for Phone number verified successfully", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("WaterMark for Contact", " WaterMark for Phone number  not verified successfully", Status_CRAFT.FAIL);
            }
            click(getObject("launch.edtPhoneNumberDigits"), "last digit");
            sendKeys(getObject("launch.edtPhoneNumberDigits"), dataTable.getData("Login_Data", "PhoneNumberDigits_DGAPP"), "Password");

            if (isElementDisplayed(getObject("launch.edtPhoneNumberClearIcon"), 5)) {
                click(getObject("launch.edtPhoneNumberClearIcon"));
                report.updateTestLog("clearContactNumber", "X is displayed and clicked on Contact number text box", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("clearContactNumber", "X is NOT displayed on Contact number text box", Status_CRAFT.FAIL);
            }

        }

    }

    public void verifyIBANnumberCurrentTile(String IBANName) throws Exception {
        String strExpIBAN = dataTable.getData("General_Data", "IBAN_Number");
        if (isElementDisplayed(getObject(IBANName), 5)) {
            fetchTextToCompare(getObject(IBANName), strExpIBAN, "IBAN Number");
        } else {
            report.updateTestLog("verifyIBANnumber", "IBAN number not displayed successfully", Status_CRAFT.FAIL);
        }
    }


    public void verifyPageRefresh() throws Exception {
        driver.navigate().refresh();
    }


    public void LoginDetailsDeleteTextOptionError() throws InterruptedException {
        String user = dataTable.getData("Login_Data", "Username");
        if (user.length() == 6 || user.length() == 8) {
            click(getObject("launch.edtUserName"));
            Thread.sleep(3000);
            sendKeys(getObject("launch.edtUserName"), dataTable.getData("General_Data", "Invalid_Username"), "Username");
        }

        if (isElementDisplayed(getObject("launch.edtDateOfBirth"), 2)) {

            String strSavingDate = dataTable.getData("General_Data", "Invalid_DOB");
            System.out.println(strSavingDate);
            String waterMark = driver.findElement(getObject("launch.edtDateOfBirth")).getAttribute("placeholder");
            if (waterMark.equals("DD/MM/YYYY")) {
                report.updateTestLog("WaterMark for DOB", " WaterMark for DOB verified successfully", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("WaterMark for DOB", " WaterMark for DOB  not verified successfully", Status_CRAFT.FAIL);
            }
            //Date selection
            waitForPageLoaded();
            Thread.sleep(1500);
            click(getObject("launch.edtDateOfBirth"), "DOB");
            waitForPageLoaded();
            sendKeys(getObject("launch.edtDateOfBirth"), strSavingDate);


        } else {
            Thread.sleep(1500);
            String waterMark = driver.findElement(getObject("launch.edtPhoneNumberDigits")).getAttribute("placeholder");
            if (waterMark.equals("Enter Last 4 Digits")) {
                report.updateTestLog("WaterMark for Contact", " WaterMark for Phone number verified successfully", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("WaterMark for Contact", " WaterMark for Phone number  not verified successfully", Status_CRAFT.FAIL);
            }
            click(getObject("launch.edtPhoneNumberDigits"), "last digit");
            sendKeys(getObject("launch.edtPhoneNumberDigits"), dataTable.getData("General_Data", "Invalid_ContactNo"), "Password");


        }
        clickJS(getObject("launch.btnLogIn"), "Login");
        if (isElementDisplayed(getObject("launch.edtUserNameClearIcon"), 5)) {
            click(getObject("launch.edtUserNameClearIcon"));
            report.updateTestLog("clearUsername", "X is displayed with error and clicked on Username text box", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("clearUsername", "X is not displayed on Username text box", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("launch.edtDateOfBirth"), 2)) {

            if (isElementDisplayed(getObject("launch.edtDOBClearIcon"), 5)) {
                click(getObject("launch.edtDOBClearIcon"));
                report.updateTestLog("clearDOB", "X is displayed with error and clicked on DOB text box", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("clearDOB", "X is not displayed on DOB text box", Status_CRAFT.FAIL);
            }

        } else {
            if (isElementDisplayed(getObject("launch.edtPhoneNumberClearIcon"), 5)) {
                click(getObject("launch.edtPhoneNumberClearIcon"));
                report.updateTestLog("clearContactNumber", "X is displayed with error and clicked on Contact number text box", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("clearContactNumber", "X is NOT displayed on Contact number text box", Status_CRAFT.FAIL);
            }

        }
    }


    public void verifyHomePageProfileNaviagtion() throws Exception {
        String UserID = "";
        String strUserCheck = dataTable.getData("Login_Data", "Username");
        Thread.sleep(2000);
        if (isElementDisplayed(getObject(deviceType() + "home.imgProfileIcon"), 5)) {
            clickJS(getObject(deviceType() + "home.imgProfileIcon"), "Img Profile Icon");
            String strUserID = "xpath~//span[text()='" + strUserCheck + "']";
            if (isElementDisplayed(getObject(strUserID), 5)) {
                report.updateTestLog("verifyHomePageProfileNaviagtion", "Profile Navigation successful and UserID displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyHomePageProfileNaviagtion", "Profile Navigation ICON/UserID is not displayed correctly..for UserID :: " + strUserCheck, Status_CRAFT.FAIL);
            }
        }
        Thread.sleep(2000);
        if (deviceType.equals("web")) {
            click(getObject("w.profile.backLink"));
        }

    }

    public void verifyApplyLink() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Label");
        if ((deviceType.equalsIgnoreCase("Web")) && (strAccountToCheck.equalsIgnoreCase("ROI"))) {

            if (isElementDisplayed(getObject(deviceType() + "home.Apply"), 10)) {

                report.updateTestLog("verifyApplyLink", "Apply link present on home page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyApplyLink", "Apply link not present on home page", Status_CRAFT.FAIL);
            }

        } else if ((deviceType.equalsIgnoreCase("Web")) && (strAccountToCheck.equalsIgnoreCase("NI"))) {
            if (isElementDisplayed(getObject(deviceType() + "home.tabApply"), 10)) {

                report.updateTestLog("verifyApplyLink", "Apply link present on home page", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyApplyLink", "Apply link not present on home page", Status_CRAFT.PASS);
            }
        }

    }

    public void verifyRefreshFunctionlity() throws Exception {
        String accountName = "home.AccountSelection";
        // Refreshing home page
        Actions action = new Actions((WebDriver) driver.getWebDriver());
        action.sendKeys(Keys.F5);
        report.updateTestLog("verifyRefreshFunctionlity", "' Home Page refresh sucess", Status_CRAFT.PASS);

        Thread.sleep(4000);
        //Navigating to account level page
        String Val = "";
        waitforPresenceOfElementLocated(getObject(accountName));
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(accountName), 5)) {
                click(getObject(accountName), "Account on home page");
                Thread.sleep(2000);
                // Refreshing account level page
                action.sendKeys(Keys.F5);
                //     Thread.sleep(2000);
                report.updateTestLog("verifyRefreshFunctionlity", "' Account Page refresh sucessfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyRefreshFunctionlity", "' Account Page refresh failed", Status_CRAFT.FAIL);
            }
        } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject(deviceType() + "home.AccountSelection"), 5)) {
                click(getObject(deviceType() + "home.AccountSelection"), "Account on home page");
                Thread.sleep(2000);
                // Refreshing account level page
                action.sendKeys(Keys.F5);
                //       Thread.sleep(2000);
                report.updateTestLog("verifyRefreshFunctionlity", "'Account Page refreshed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyRefreshFunctionlity", "'Account Page refresh failed", Status_CRAFT.FAIL);
            }
        }
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 10)) {
                //Navigating to Payments tab
                click(getObject(deviceType() + "home.tabPayments"), "Select 'Payments Tab'");
                //Navigating to Manage Payee
                //       Thread.sleep(2000);
                if (isElementDisplayed(getObject(deviceType() + "Payments.ManagePayee"), 10)) {
                    click(getObject(deviceType() + "Payments.ManagePayee"), "Select 'ManagePayee'");
                }
                //Refreshing Manage Payee
                Thread.sleep(2000);
                action.sendKeys(Keys.F5);
                //       Thread.sleep(2000);
                report.updateTestLog("verifyRefreshFunctionlity", "'ManagePayee refresh successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyRefreshFunctionlity", "'ManagePayee refresh failed", Status_CRAFT.FAIL);
            }
        }
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), 10)) {
                // Navigating to Services tab
                waitforInVisibilityOfElementLocated(getObject(deviceType() + "serviceDesk.hdrServiceDesk"));
                click(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), "Select 'Services Tab'");
                //        Thread.sleep(2000);
                //Navigating to Add policy
                if (isElementDisplayed(getObject(deviceType() + "serviceDesk.AddPolicy"), 10)) {
                    click(getObject(deviceType() + "serviceDesk.AddPolicy"), "Select 'Add Policy'");
                }
                Thread.sleep(2000);
                //Refreshing Add policy
                action.sendKeys(Keys.F5);
                Thread.sleep(2000);
                report.updateTestLog("verifyRefreshFunctionlity", "' Add Account policy refresh sucessfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyRefreshFunctionlity", "' Add Account policy refresh failed", Status_CRAFT.FAIL);
            }
        }
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), 10)) {
// Navigating to Services tab
                waitforInVisibilityOfElementLocated(getObject(deviceType() + "serviceDesk.hdrServiceDesk"));
                click(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), "Select 'Services Tab'");

                //         Thread.sleep(2000);
                //Navigating to Change Address
                if (isElementDisplayed(getObject(deviceType() + "serviceDesk.ChangeAddress"), 10)) {
                    click(getObject(deviceType() + "serviceDesk.ChangeAddress"), "Select 'Change Address'");
                }
                Thread.sleep(2000);
                //Refreshing ChangeAddress
                action.sendKeys(Keys.F5);
                Thread.sleep(2000);
                report.updateTestLog("verifyRefreshFunctionlity", "' Change Address refresh sucessfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyRefreshFunctionlity", "' Change Address refresh failed", Status_CRAFT.FAIL);
            }
        }
    }

    public void verifySwitchAccount() throws Exception {
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "accountpage.SwitchLink"), 10)) {
                report.updateTestLog("verifySwitchAccounts", "' Switch between my accounts' link is available ", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifySwitchAccounts", "' Switch between my accounts' link is not available", Status_CRAFT.FAIL);
            }
        }
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("accountpage.Switchaccount"), 10))
                click(getObject("accountpage.Switchaccount"), "Select 'BKK account'");
            Thread.sleep(3000);

            if (isElementDisplayed(getObject("accountPage.TransactionsTabSelected"), 10)) {
                report.updateTestLog("verifySwitchAccounts", "' User successfully able to switch between accounts and Transactions Tab is selected", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifySwitchAccounts", "' User not able to switch between accounts", Status_CRAFT.FAIL);
            }
        }
    }

    // To verify the links navigation forth and back under services tab
    public void ServicesLinksNavigation() throws Exception {
        String strlinktoClick = dataTable.getData("General_Data", "VerifyContent");
        String[] arrlinktoClick = strlinktoClick.split("::");
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), 10)) {
                // Navigating to Services tab
                clickJS(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), "Select 'Services Tab'");
                waitForPageLoaded();

                for (int i = 0; i < arrlinktoClick.length; i++) {
                    click(getObject("xpath~//a[contains(@id,'" + arrlinktoClick[i] + "')]"), "Selected '" + arrlinktoClick[i] + "' Link'");
                    if (isElementDisplayed(getObject(deviceType() + "home.backHeaderlink"), 5)) {
                        clickJS(getObject(deviceType() + "home.backHeaderlink"), "Click Back Arrow link'");
                        waitForPageLoaded();
                    }
                }
            }
        }
    }

    // To verify the links navigation forth and back under Payments tab
    public void PaymentsLinksNavigation() throws Exception {
        String strlinktoClick = dataTable.getData("General_Data", "VerifyContent");
        String[] arrlinktoClick = strlinktoClick.split("::");
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 10)) {
                // Navigating to Services tab
                clickJS(getObject(deviceType() + "home.tabPayments"), "Select Payments Tab'");
                waitForPageLoaded();

                for (int i = 0; i < 2; i++) {
                    clickJS(getObject("xpath~//input[@title='" + arrlinktoClick[i] + "']"), "Selected '" + arrlinktoClick[i] + "' Link'");
                    waitForPageLoaded();
                    if (isElementDisplayed(getObject(deviceType() + "home.backHeaderlink"), 5)) {
                        clickJS(getObject(deviceType() + "home.backHeaderlink"), "Click Back Arrow link'");
                        waitForPageLoaded();
                    }
                }
                for (int i = 2; i < arrlinktoClick.length; i++) {
                    clickJS(getObject("xpath~//span[text()='" + arrlinktoClick[i] + "']"), "Selected '" + arrlinktoClick[i] + "' Link'");
                    waitForPageLoaded();
                    if (isElementDisplayed(getObject(deviceType() + "home.backHeaderlink"), 5)) {
                        clickJS(getObject(deviceType() + "home.backHeaderlink"), "Click Back Arrow link'");
                        waitForPageLoaded();
                    }
                }
            }
        }
    }


    public void verifyServicesContactUsLinks() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), 10)) {
            click(getObject(deviceType() + "serviceDesk.hdrServiceDesk"), "Select 'Services Tab'");
            waitForJQueryLoad();
            waitForPageLoaded();
            if (isElementDisplayed(getObject(deviceType() + "serviceDesk.ContactUs"), 5)) {
                // Navigating to Contact Us
                clickJS(getObject(deviceType() + "serviceDesk.ContactUs"), "Select 'Contact Us'");
                waitForJQueryLoad();
                waitForPageLoaded();
                report.updateTestLog("verifyServicesContactUs", "' User navigated to Contact Us ", Status_CRAFT.PASS);

                if (isElementDisplayed(getObject(deviceType() + "contactus.TopQueries"), 10)) {
                    waitForElementToClickable(getObject(deviceType() + "contactus.TopQueries"), 10);
                    clickJS(getObject(deviceType() + "contactus.TopQueries"), "Select 'Top Queries Answered '");
                    waitForJQueryLoad();
                    waitForPageLoaded();
                    report.updateTestLog("verifyServicesContactUs", "' Top Queries Answered clicked ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyServicesContactUs", "' Failed to click Top Queries Answered ", Status_CRAFT.FAIL);
                }

                if (isElementDisplayed(getObject(deviceType() + "contactus.twitter"), 10)) {
                    waitForElementToClickable(getObject(deviceType() + "contactus.twitter"), 10);
                    clickJS(getObject(deviceType() + "contactus.twitter"), "Select 'Twitter Link '");
                    waitForJQueryLoad();
                    waitForPageLoaded();
                    report.updateTestLog("verifyServicesContactUs", "' Twitter link successfully clicked ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyServicesContactUs", "' Failed to click Twitter link ", Status_CRAFT.FAIL);
                }

                if (isElementDisplayed(getObject(deviceType() + "contactus.board"), 10)) {
                    waitForElementToClickable(getObject(deviceType() + "contactus.board"), 10);
                    clickJS(getObject(deviceType() + "contactus.board"), "board.ie '");
                    waitForJQueryLoad();
                    waitForPageLoaded();
                    report.updateTestLog("verifyServicesContactUs", "' Board.ie link successfully clicked ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyServicesContactUs", "' Failed to click Board.ie link ", Status_CRAFT.FAIL);
                }

            } else {
                report.updateTestLog("verifyServicesContactUs", "' Failed to navigate to Contact Us ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyServicesContactUs", "Services Tab/Menu Item not found", Status_CRAFT.FAIL);
        }

    }

        /*CFPUR-1233*
 Navigation menu - Account level 'More' option (Mobile view only)
 */

    public void moreOptionMobile_Account() throws Exception {
        boolean bflag = false;
        String accType = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = deviceType() + "home.parentaccount_new";
        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyCheckContentNotPresent", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        ScrollToVisibleJS(strParentObject);

        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();
            if ((strRowText.contains(accType.toUpperCase())) && (strRowText.contains(strAccountIBAN.toUpperCase()))) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                Thread.sleep(3000);
                JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                executor.executeScript("arguments[0].click();", oUIRows.get(j));
                Thread.sleep(8500);
                report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                bflag = true;
                break;
            }
        }

        if (deviceType.equals("MobileWeb")) {
            Thread.sleep(3000);
            click(getObject(deviceType() + "myAccount.moreOptions"));
            Thread.sleep(3000);
            report.updateTestLog("verifyMoreOptions", ":: More Options Clicked :: ", Status_CRAFT.PASS);
            Thread.sleep(3000);
            verifyElementDetails(deviceType() + "myAccount.verifyMoreOptionMenu");
        } else if (deviceType.equals("Web")) {
            isElementNotDisplayed(getObject(deviceType() + "myAccount.moreOptions"), 2);
            report.updateTestLog("verifyMoreOptions", ":: More Options for Desktop not present:: ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyMoreOptions", ":: More Options present for Desktop-->should not be present:: ", Status_CRAFT.FAIL);
        }

    }


    /*CFPUR-631 - No Show More button verification*/
    public void verifyNoShowMoreButton() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            report.updateTestLog("verifyNoShowMoreOptions", ":: Show More options on Desktop  present:: ", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyNoShowMoreOptions", ":: Show More options  Desktop not present:: ", Status_CRAFT.PASS);
        }

    }

    /*CFPUR631 - No Portfolio component verification*/
    public void verifyNoPolicyPortfolio() {
        if (isElementNotDisplayed(getObject(deviceType() + "launch.portfolio"), 5)) {
            report.updateTestLog("verifyNoPortfolio", ":: No Portfolio options on Desktop not present:: ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyNoPortfolio", ":: No Portfolio options on Desktop  present:: ", Status_CRAFT.FAIL);
        }

    }

    /*CFPUR-1527- PAD2-Header*/
    public void verifyPAD2Header() throws Exception {
        boolean bflag = false;
        String accType = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = deviceType() + "home.parentaccount_new";
        String linkToCheckPAD2 = "xpath~//span[contains(text(),'View bank statement - most common terms')]";
        String url = "https://www.bankofirelanduk.com/statement-common-terms/";
        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyCheckContentNotPresent", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        ScrollToVisibleJS(strParentObject);
        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();
            if ((strRowText.contains(accType.toUpperCase())) && (strRowText.contains(strAccountIBAN.toUpperCase()))) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                executor.executeScript("arguments[0].click();", oUIRows.get(j));
                report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                bflag = true;
                break;
            }
        }

        //For click on URl and verify the new tab
        if (isElementDisplayed(getObject(linkToCheckPAD2), 15)) {
            if (isMobile) {
                verifyHrefLink(url, linkToCheckPAD2);
            } else {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToCheckPAD2))));
                js.executeScript("arguments[0].click();", driver.findElement(getObject((linkToCheckPAD2))));
                verifyNewlyOpenedTab(url);
            }
        }
    }


    /**
     * CFPUR-40 Function to verify Apply link is not present for NI/GB customer
     */
    public void verifyNoApplyLink() throws Exception {
        if (isElementDisplayed(getObject("home.lnkApplyMainMenu"), 3)) {
            report.updateTestLog("verifyNoApplyLink", ":: Apply Link present on home page for NI/GB:: ", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyNoApplyLink", ":: No Apply Link present on home page for NI/GB:: ", Status_CRAFT.PASS);
        }
    }

    public void userIdTimeoutError() throws Exception {
        String strTypeOfCheck = dataTable.getData("General_Data", "VerifyDetails");
        String linkToclick = dataTable.getData("General_Data", "Properties_Variable");

        Thread.sleep(300000);
        if (isElementDisplayed(getObject(deviceType() + "home.lbltimeoutPopOutUserId"), 60)) {
            report.updateTestLog("Verify TimeOut Pop-Up", "TimeOut Pop-up is present for the  Application: ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Verify TimeOut Pop-Up", "TimeOut Pop-up is not present for the  Application: ", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("w.home.lblAccountOverview"), 5)) {
            report.updateTestLog("VerifyLogout", "Link :: 'No, keep me logged in' Working as expected on log out pop.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyLogout", "Link :: 'No, keep me logged in' is not Working as expected on log out pop.", Status_CRAFT.FAIL);
        }
        if (strTypeOfCheck.equalsIgnoreCase("AutoLogout")) {
            String timeOutMessage = "Your session timed out.";
            // Thread.sleep(75000);
            Thread.sleep(61000);
            String actualErrorMsg = getText(getObject(deviceType() + "home.lbltimeoutSession"));
            if (actualErrorMsg.equals(timeOutMessage)) {
                // report.updateTestLog("Verify TimeOut", "TimeOut  of the  Application: ", Status_CRAFT.PASS);
                report.updateTestLog("Verify TimeOut", "TimeOut message '" + actualErrorMsg + "' is displayed", Status_CRAFT.PASS);
            } else {
                //  report.updateTestLog("Verify TimeOut", "TimeOut  failed of the  Application: ", Status_CRAFT.FAIL);
                report.updateTestLog("Verify TimeOut", "TimeOut  message is not displayed correctly: ", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * CFPUR-1224 Function to verify Accessibility link on footer
     */
    public void AccessibilityText() throws Exception {
        // if (deviceType() == "mw.") {
        //   click(getObject("home.lnkPreLgnMore"), "More button");
        //}
        if (isElementDisplayed(getObject(deviceType() + "home.lblAccessibilityLink"), 3)) {
            clickJS(getObject(deviceType() + "home.lblAccessibilityLink"), "Clicked on Accessibility link");
            if (isElementDisplayed(getObject(deviceType() + "home.lblAccessibilityText"), 3)) {
                String strAccessibiltyText = getText(getObject(deviceType() + "home.lblAccessibilityText"));
                report.updateTestLog("verifyAccessibilityText", "Accessibility text is displayed as : " + strAccessibiltyText, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAccessibilityText", "Accessibility text is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAccessibilityText", "Accessibility link is not displayed", Status_CRAFT.FAIL);
        }
    }

    /**
     * CFPUR-1221 Function to verify Regulatory Information link on footer
     */
    public void RegulatoryInfoText() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.lblRegulatoryInfoLink"), 3)) {
            clickJS(getObject(deviceType() + "home.lblRegulatoryInfoLink"), "Clicked on Regulatory Information Link");
            if (isElementDisplayed(getObject(deviceType() + "home.lblRegulatoryInfoText"), 3)) {
                String strRegulatoryInfoText = getText(getObject(deviceType() + "home.lblRegulatoryInfoText"));
                report.updateTestLog("verifyRegulatoryInfoText", "Regulatory Information text is displayed as : " + strRegulatoryInfoText, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyRegulatoryInfoText", "Regulatory Information text is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyRegulatoryInfoText", "Regulatory Information link is not displayed", Status_CRAFT.FAIL);
        }
    }


    public void regulatoryGoBack() throws InterruptedException {
        if (isElementDisplayed(getObject("homepage.RegulatoryGoBack"), 5)) {
            click(getObject("homepage.RegulatoryGoBack"));
            report.updateTestLog("regulatoryGoBack", "Clicked on regulatory-GoBack button succesfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("regulatoryGoBack", "Unable to clicked on regulatory-GoBack button", Status_CRAFT.FAIL);

        }
    }

    public void footerText() throws Exception {
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject("mw.homepage.footertext"), 5)) {
                String FooterText = getText(getObject("mw.homepage.footertext"));
                report.updateTestLog("FooterText", "Text is correctly present, Expected:" + FooterText, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("FooterText", "Text is NOT correctly present", Status_CRAFT.FAIL);
            }
        } else if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("homepage.footerText"), 5)) {
                String FooterText = getText(getObject("homepage.footerText"));
                report.updateTestLog("FooterText", "Text is correctly present, Expected:" + FooterText, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("FooterText", "Text is NOT correctly present", Status_CRAFT.FAIL);
            }
        }
    }


    public void verifyDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            clickJS(getObject("mw.homepage.footerMore"), "More Icon clicked successfully");
            if (scriptHelper.commonData.iterationFlag != true) {
                homePage.verifyElementDetails("mw.homepage.footerlink");
                if (!isMobile) {
                    scriptHelper.commonData.iterationFlag = true;
                    clickJS(getObject("mw.homepage.GoBack"), "Go Back button clicked successfully");
                }
            } else {
                homePage.verifyElementDetails("mw.homepage.footerlink");
                scriptHelper.commonData.iterationFlag = null;
            }
        } else if (deviceType.equalsIgnoreCase("Web")) {
            if (scriptHelper.commonData.iterationFlag != true) {
                homePage.verifyElementDetails("homepage.footerLink_Login");
                scriptHelper.commonData.iterationFlag = true;
            } else {
                homePage.verifyElementDetails("homepage.footerLink");
                scriptHelper.commonData.iterationFlag = null;
            }
        }
    }


    public void logOut() throws InterruptedException {
        if (isElementDisplayed(getObject("home.logout"), 5)) {
            clickJS(getObject("home.logout"), "Logout");
            report.updateTestLog("verifyLogout", "Clicked on Logout successfully", Status_CRAFT.PASS);
            Thread.sleep(3000);

            if (isElementDisplayed(getObject("home.YesLogout"), 10)) {
                //    click(getObject("home.YesLogout"));
                clickJS(getObject("home.YesLogout"), "Logout");
                report.updateTestLog("verifyLogout", "Clicked on 'Yes-Log me out' succesfully", Status_CRAFT.PASS);
                waitForPageLoaded();
            }
        }
    }


    public void blueCardDetails() throws Exception {
        if (deviceType.equals("Web")) {
            //verifyElementDetails(deviceType() + "myAccount.sectionBlueCard");
            displayElementText(deviceType() + "myAccount.sectionBlueCard");
        } else {
            //Unfold/Fold Functionality
            driver.findElement(By.xpath("//a[@id='boi_share_toggle'][text()='BIC\\IBAN']")).click();
            Thread.sleep(5000);
            //verifyElementDetails(deviceType() + "myAccount.sectionBlueCard");
            displayElementText(deviceType() + "myAccount.sectionBlueCard");
            if (isElementDisplayed(getObject(deviceType() + "home.titleMyAccounts"), 5)) {
                report.updateTestLog("NickNameOnPages", "'ACCOUNTS' Tab is displayed", Status_CRAFT.PASS);
                click(getObject(deviceType() + "home.titleMyAccounts"), "Click on Account Tabs");
            } else {
                report.updateTestLog("NickNameOnPages", "'ACCOUNTS' Tab is NOT displayed", Status_CRAFT.FAIL);
            }

        }
    }

    /**
     * Function verify Nickname of Transaction & Home Page
     */
    public void NickNamePresentOnPages() throws Exception {
        //navigate and validate on HomePage Account Details
        if (isElementDisplayed(getObject(deviceType() + "home.titleMyAccounts"), 5)) {
            report.updateTestLog("NickNameOnPages", "'ACCOUNTS' Tab is displayed", Status_CRAFT.PASS);
            click(getObject(deviceType() + "home.titleMyAccounts"), "Click on Account Tabs");
        } else {
            report.updateTestLog("NickNameOnPages", "'ACCOUNTS' Tab is NOT displayed", Status_CRAFT.FAIL);
        }
        boolean bflag = false;
        String strUiText;
        String accType = dataTable.getData("General_Data", "NewNickname");
        String accno = dataTable.getData("General_Data", "AccountNumber");
        List<WebElement> temp = driver.findElements(By.xpath("//div[contains(@class,'responsive-row boi-margin-accountdetails')]"));

        if (temp.size() != 0) {
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).isDisplayed()) {
                    strUiText = temp.get(i).getText();
                    if (strUiText.contains(accType) && strUiText.contains(accno)) {
                        report.updateTestLog("verifyAccTypeExistAndClicked", "Account Type : '" + accType + " ' is present", Status_CRAFT.PASS);
                        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                        js.executeScript("arguments[0].scrollIntoView();", temp.get(i));
                        JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                        executor.executeScript("arguments[0].click();", temp.get(i));
                        report.updateTestLog("verifyAccTypeExistAndClicked", "Account Type : '" + accType + " ' is Selected", Status_CRAFT.PASS);
                        bflag = true;
                        break;
                    }
                }
            }
        }

        if (!bflag) {
            report.updateTestLog("NickNameOnPages", "Account Type : '" + accType + " ' is not present on Home Page", Status_CRAFT.FAIL);
        }
        //navigate and validate on Transaction Page
      /*  if (isElementDisplayed(getObject("xpath~//span[text()='Transactions']"), 5)) {
            report.updateTestLog("NickNameOnPages", "Menu Tab 'Transaction' is displayed", Status_CRAFT.PASS);
            click(getObject("xpath~//span[text()='Transactions']"), "Click on Menu Tab 'Transaction'");
        } else {
            report.updateTestLog("NickNameOnPages", "Menu Tab 'Transaction ' is NOT displayed", Status_CRAFT.FAIL);
        }*/

        String strAccountName = dataTable.getData("General_Data", "NewNicknameToVerify") + " ~ " + dataTable.getData("General_Data", "AccountNumber");
        if (getText(getObject("Transaction.hdrAccount")).equalsIgnoreCase(strAccountName)) {
            report.updateTestLog("NickNameOnPages", "Account Name: '" + strAccountName + "' correctly displayed on header of Transaction Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("NickNameOnPages", "Account Name: '" + strAccountName + "' correctly not displayed on header of Transaction Page", Status_CRAFT.FAIL);
        }
    }


    /**
     * CFPUR826_E2E_TC058: Function to verify 'Switch between my accounts' option is not displayed
     */

    public void verifyCreditCardswitch() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "accountpage.Switchaccount1"), 3)) {
            report.updateTestLog("verifyCreditCardswitch", "'Switch between my accounts' option is displayed", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyCreditCardswitch", "'Switch between my accounts' option is not displayed", Status_CRAFT.PASS);
        }
    }

    /**
     * CFPUR1710_ETE_TC030: Function to verify Arrow Button is displayed on HomePage
     */

    public void verifyArrowButton() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "homepage.lblArrowButton"), 6)) {
            report.updateTestLog("verifyArrowButton", "Arrow Button is displayed on HomePage", Status_CRAFT.DONE);
            if (deviceType.equalsIgnoreCase("Web")) {
                click(getObject(deviceType() + "homepage.lblArrowButton"), "Arrow Button");
            } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
                clickJS(getObject(deviceType() + "homepage.lblArrowButton"), "Arrow Button");
            }
        } else {
            report.updateTestLog("verifyArrowButton", "Arrow Button is not displayed on HomePage", Status_CRAFT.FAIL);
        }
    }

    public void ValidateAccountTileDetails() throws Exception {
        boolean bflag = false;
        List<WebElement> temp = null;
        String strUiText;
        String IBAN = dataTable.getData("General_Data", "IBAN_Number");
        String currency = dataTable.getData("General_Data", "Currency_Symbol");
        String accType = dataTable.getData("General_Data", "Account_Name");
        String accno = dataTable.getData("General_Data", "AccountNumber");

        if (deviceType.equals("MobileWeb")) {
            IBAN = "~ " + dataTable.getData("General_Data", "IBAN_Number").substring(IBAN.length() - 4);
        }

        if (!isMobile) {
            temp = driver.findElements(By.xpath("//div[contains(@class,'responsive-row boi-margin-accountdetails')]"));
        } else {
            temp = driver.findElements(By.xpath("//*[@class='tc-ripple-effect  textRole']"));

        }


        if (temp.size() != 0) {
            for (int i = 1; i <= temp.size(); i++) {
                if (temp.get(i - 1).isDisplayed()) {
                    strUiText = temp.get(i - 1).getText();
                    if (strUiText.toUpperCase().contains(accType.toUpperCase()) && strUiText.contains(IBAN)) {
                        report.updateTestLog("verifyAccTypeExistAndClicked", "Account Type : '" + accType + " ' is present", Status_CRAFT.PASS);

                        String xpathAccount = "xpath~//div[@id='C5__FMT_514551D0A02B71C0646808_R" + i + "']";
                        //isMobile>>TODO need to check the impact-Rajiv
                        if (deviceType.equalsIgnoreCase("MobileWeb")) {
                            xpathAccount = "xpath~//div[@id='C6__FMT_40136344F08048B7363571_R" + i + "']";
                        }

                        String strFetchAccount = getText(getObject(xpathAccount));
                        if (strFetchAccount.contains(currency)) {
                            report.updateTestLog("verifyCurrencyExist", "Currency : '" + currency + " ' displayed correctly", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("verifyCurrencyExist", "Currency : '" + currency + " ' displayed is not correct", Status_CRAFT.FAIL);
                        }

                        if (strFetchAccount.contains(IBAN)) {
                            report.updateTestLog("verifyIBANExist", "IBAN : '" + IBAN + " ' displayed is correct", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("verifyIBANExist", "IBAN : '" + IBAN + " ' displayed is not correct", Status_CRAFT.FAIL);
                        }


                        bflag = true;
                        break;
                    }
                }
            }
            if (!bflag) {
                report.updateTestLog("verifyIBANExist", "Account name with details '" + accType + "' & IBAN '" + IBAN + "' is not present", Status_CRAFT.FAIL);
            }
        }
    }

    public void clickStatementsTab() throws InterruptedException {
        if (isElementDisplayed(getObject("AccStatement.lblStatementTab"), 8)) {
            clickJS(getObject("AccStatement.lblStatementTab"), "Statements tab is clicked");
            waitForElementToClickable(getObject("ManageStatements.lblRecentStatements"), 10);
            report.updateTestLog("verifyStatementTab", "Statement tab is getting clicked successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyStatementTab", "Statement tab is not clicked", Status_CRAFT.FAIL);
        }
    }


    public void clickPDFAccountStatements() throws InterruptedException {
        String StatmentDate = dataTable.getData("General_Data", "Relationship_Status");
        String strExecutionMode = scriptHelper.getTestParameters().getExecutionMode().toString();
        String StatementToClick = "xpath~//div[@class='hide-mobile']/descendant::*[text()='" + StatmentDate + "']";
        waitForJQueryLoad();waitForPageLoaded();
        waitforInVisibilityOfElementLocated(getObject("AccStatementPDFSpinner"));
        if (isElementDisplayed(getObject("ServiceManage.txtServiceUnavailable") , 8)){
            report.updateTestLog("selectServiceDeskOption", " 'Service unavailable :: Unfortunately, this service is unavailable at this time. Please try again later.' is displyed on Service Desk page", Status_CRAFT.FAIL);
        }
        if (deviceType.equalsIgnoreCase("MobileWeb") || deviceType().equals("mw.")) {
            List<WebElement> oStatementsIconPDF = driver.findElements(getObject("mw.AccStatement.iconStatementPDF"));
            List<WebElement> oStatementsStatemntType = driver.findElements(getObject("mw.AccStatement.lblStatementType"));
            if (oStatementsIconPDF.size() >= 1) {
                oStatementsIconPDF.get(0).click();
                report.updateTestLog("verify Account Statement PDF", " Statement Type :: " + oStatementsStatemntType.get(0).getText() + " :: PDF is Clicked", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("ServiceManage.txtServiceUnavailable"), 8)) {
                    report.updateTestLog("selectServiceDeskOption", " 'Service unavailable :: Unfortunately, this service is unavailable at this time. Please try again later.' is displyed on Service Desk page", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("verify Account Statement PDF", "List of Account Statement PDF is not present", Status_CRAFT.FAIL);
            }
        }

        Thread.sleep(3000);
        waitforInVisibilityOfElementLocated(getObject("AccStatementPDFSpinner"));

        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("AccPDFOk"), 10)) {
                report.updateTestLog("verifyAccountStatement_Popup", "Popup Window is displayed", Status_CRAFT.PASS);
                waitForElementToClickable(getObject("AccPDFOk"), 7);
                clickJS(getObject("AccPDFOk"), "Privacy OK button");
                report.updateTestLog("verifyAccountStatement_AllAccounts_PDF_OK", "Ok Clicked", Status_CRAFT.PASS);
                String ParentTab = driver.getWindowHandle();
                ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
                newTab.remove(ParentTab);
                driver.switchTo().window(newTab.get(0));
                Thread.sleep(10000);
                driver.getCurrentUrl();
                if (driver.getCurrentUrl().contains("Digital/downloadStatement")) {
                    report.updateTestLog("verifyPDF_title", "PDF opened in new Window", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyPDF_title", "PDF opened in new Window", Status_CRAFT.FAIL);
                }
            }
        } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject("AccPDFOk"), 10)) {
                report.updateTestLog("verifyAccountStatement_Popup", "Popup Window is displayed", Status_CRAFT.PASS);
                clickJS(getObject("AccPDFOk"), "Privacy OK button");
                Thread.sleep(4000);
                report.updateTestLog("verifyPDF_title", "PDF opened with in APP successfully", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("verifyAccountStatement_Popup", "Popup Window is not displayed", Status_CRAFT.FAIL);
        }

    }

    public void verifyPopUpDetailsE2E() throws Exception {
        String strContentToCheck = dataTable.getData("General_Data", "VerifyContent");
        String[] arrContentToCheck = strContentToCheck.split("::");
        Thread.sleep(1000);
        report.updateTestLog("verifyPopUpAndClickOnLink", "The Title and Content ", Status_CRAFT.SCREENSHOT);
        // To Verify Title
        String strTitleXpath = "xpath~//div[@id='EDGE_CONNECT_PROCESS']/descendant::span[@id='ui-id-1'][text()='" + arrContentToCheck + "']";
        if (isElementDisplayed(getObject(strTitleXpath), 20)) {
            report.updateTestLog("verifyPopUpAndClickOnLink", "The title of the Dialog :: " + arrContentToCheck + ":: is correctly displyed.", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyPopUpAndClickOnLink", "The title of the Dialog :: " + arrContentToCheck + ":: is not correctly displyed.", Status_CRAFT.FAIL);
        }
    }

    // User story 69 - To Verify the pagination link at bottom of page in Statments Page
    public void verifyPaginationDisplayed() throws Exception {
        String strPaperLessFlag = dataTable.getData("General_Data", "Operation");
        if (strPaperLessFlag.equalsIgnoreCase("Pagination_Yes")) {
            if (isElementDisplayed(getObject("AccStatement.lblCurrentPageNumber"), 5)) {
                report.updateTestLog("verifyPaginationLink", "Pagination is displayed at bottom of Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyPaginationLink", "Pagination is not displayed at bottom of Page", Status_CRAFT.FAIL);
            }
        } else if (strPaperLessFlag.equalsIgnoreCase("Pagination_No")) {
            if (isElementDisplayed(getObject("AccStatement.lblCurrentPageNumber"), 5)) {
                report.updateTestLog("verifyPaginationLink", "Pagination is displayed at bottom of Page..Please check the correct data.", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyPaginationLink", "Pagination is not displayed at bottom of Page", Status_CRAFT.PASS);
            }
        }
    }


    public void NavigationToHomePage() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "home.titleMyAccounts"), 5)) {
            report.updateTestLog("verifyAccountsLink", "Accounts link is displayed", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "home.titleMyAccounts"), "My Accounts Link");
            waitForPageLoaded();
            isElementDisplayedWithLog(getObject(deviceType() + "home.sectionTimeLine"),
                    "Upon clicking 'Back to home'", "Navigated to Home page ", 20);
        } else {
            report.updateTestLog("verifyAccountsLink", "Accounts link is displayed", Status_CRAFT.FAIL);
        }
    }


    public void verifyServerNameFooter_desktop() throws Exception {
        ScrollToVisibleJS("home.servernamefooter_desktop");
        String strServerName = getText(getObject("home.servernamefooter_desktop"));
        if (isElementDisplayed(getObject("home.servernamefooter_desktop"), 3)) {
            report.updateTestLog("verifyServerNameFooter_desktop", "Server name on footer is displayed as : " + strServerName, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyServerNameFooter_desktop", "Server name on footer is not  displayed", Status_CRAFT.FAIL);
        }

    }

    /**
     * Function to Display Text of elements
     * I/O element containing the data
     */

    public void displayElementText(String sectionElement) throws Exception {
        if (isElementDisplayed(getObject(sectionElement), 5)) {
            String dataSectionUI = getText(getObject(sectionElement)).toUpperCase();
            report.updateTestLog("displayAllElementText", dataSectionUI + " details are displayed successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("displayAllElementText", "Section element '" + sectionElement + "' is not displayed on Screen", Status_CRAFT.FAIL);
        }
    }


    /**
     * Reusable :
     * Navigate to any account from the home page
     */
    public void verifyNavigateToAnyAccount() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = deviceType() + "home.parentaccount_new";

        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        ScrollToVisibleJS(strParentObject);

        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();
            if ((strRowText.contains(strAccountToCheck.toUpperCase())) && (strRowText.contains(strAccountIBAN.toUpperCase()))) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                Thread.sleep(3000);
                JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                report.updateTestLog("verifyNavigateToAnyAccount", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                executor.executeScript("arguments[0].click();", oUIRows.get(j));
                Thread.sleep(8500);
                waitForElementPresent(getObject("AccStatement.lblStatementTab"), 20);
                report.updateTestLog("verifyNavigateToAnyAccount", "The Account :: " + strAccountToCheck + ":: is displyed at position ::" + (j + 1) + ":: and clicked successfully.", Status_CRAFT.DONE);
                break;
            }
        }

    }


    /**
     * CFPUR : 4078 , 1185
     * Navigate to any account from the home page
     **/
    public void verifyPaperlessStmntPromotion() throws Exception {
        String strAccountName = dataTable.getData("General_Data", "Account_Type");
        String strPaperLessFlag = dataTable.getData("General_Data", "Relationship_Status");
        click(getObject("AccStatement.lblStatementTab"));
        if (isElementDisplayed(getObject("ManageStatements.lblRecentStatements"), 3)) {
            report.updateTestLog("verifyPaperlessStmntPromotion", "The text 'Recent Account Statement' and Account Name with H1 tag ::" + strAccountName + " :: is displayed correctly.", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyPaperlessStmntPromotion", "Either text 'Recent Account Statement' and Account Name with H1 tag is NOT displayed correctly.", Status_CRAFT.FAIL);
        }

        //To Check the paperless promotion
        if (strPaperLessFlag.equals("CheckPaperlessPromotion")) {
            if (isElementDisplayed(getObject("PaperStatements.Promotion_First"), 3) && isElementDisplayed(getObject("PaperStatements.Promotion_Second"), 3)) {
                report.updateTestLog("verifyPaperlessStmntPromotion", "The Paper less promotion icon and message is displayed correctly.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyPaperlessStmntPromotion", "The Paper less promotion icon and message is NOT displayed correctly.", Status_CRAFT.FAIL);
            }

            click(getObject("PaperStatements.Promotion_First"));

            if (isElementDisplayed(getObject(deviceType() + "PaperlessPromotion_pageTitle"), 20)) {
                report.updateTestLog("verifyPaperlessStmntPromotion", "Navigation to the page paper notification ON/OFF successful.", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyPaperlessStmntPromotion", "Navigation to the page paper notification ON/OFF is NOT successful", Status_CRAFT.FAIL);
            }

        } else {
            if (!(isElementDisplayed(getObject("PaperStatements.Promotion_First"), 3) && isElementDisplayed(getObject("PaperStatements.Promotion_Second"), 3))) {
                report.updateTestLog("verifyPaperlessStmntPromotion", "The Paper less promotion is off for account : " + strAccountName, Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyPaperlessStmntPromotion", "The Paper less promotion On for account:: " + strAccountName + " ::which is NOT expected..!!", Status_CRAFT.FAIL);
            }
        }

    }

    //***********Smoke Added : In progress ::

    /**
     * Reusable : Smoke
     * Navigate to any account from the home page : Using IBAN and Account Type
     */
    public void verifyNavigateToAccountPageAnyAccount() throws Exception {
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = deviceType() + "home.parentaccount_new";
        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyNavigateToAccountPageAnyAccount", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        ScrollToVisibleJS(strParentObject);
        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();
            if (strRowText.contains(strAccountIBAN.toUpperCase())) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                report.updateTestLog("verifyNavigateToAccountPageAnyAccount", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                executor.executeScript("arguments[0].click();", oUIRows.get(j));
                waitForElementPresent(getObject("AccStatement.lblStatementTab"), 60);
                report.updateTestLog("verifyNavigateToAccountPageAnyAccount", "The Account :: is displyed at position ::" + (j + 1) + ":: and clicked successfully.", Status_CRAFT.DONE);
                break;
            }
        }

    }

    /**
     * Reusable : Smoke
     * Navigate to any account from the home page : Using ColorCode
     */
    public void verifyNavigateToAccountUsingColor() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strParentObject = "xpath~//div[@class='tc-card-body boi-card-body tc-card-body-homepage']/descendant::div[contains(@class,'color-" + strAccountToCheck + "-account')]";
        String strFirstParentObject = "xpath~(//div[@class='tc-card-body boi-card-body tc-card-body-homepage']/descendant::div[contains(@class,'color-" + strAccountToCheck + "-account')])[1]";
        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyNavigateToAccountUsingColor", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        ScrollToVisibleJS(strFirstParentObject);
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(1));
        JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
        executor.executeScript("arguments[0].click();", oUIRows.get(1));
        waitForElementPresent(getObject("AccStatement.lblStatementTab"), 30);
        report.updateTestLog("verifyNavigateToAccountPageAnyAccount", "The Account :: " + strAccountToCheck + ":: is and clicked successfully.", Status_CRAFT.DONE);
    }

    /**
     * Reusable : Smoke
     * Transactions
     */
    public void verifyStatementsSmoke() throws Exception {
        clickJS(getObject("AccStatement.lblStatementTab"), "Clicking on the statements tab");
        waitForElementPresent(getObject(deviceType() + "AccStatement.tblParent"), 40);

        if (isElementDisplayed(getObject("AccStatement.lblNoStatementFound"), 30)) {
            report.updateTestLog("verifyStatementsSmoke", "No Statements Found...!!", Status_CRAFT.PASS);
        } else if (isElementDisplayed(getObject(deviceType() + "AccStatement.tblParent"), 30)) {
            String strStatementListParent = deviceType() + "AccStatement.tblParent";
            List<WebElement> oStatements = driver.findElements(getObject(strStatementListParent));
            int intNumberOfStatement = oStatements.size();
            if (deviceType.equalsIgnoreCase("Web")) {
                //12 Statements per page
                intNumberOfStatement = oStatements.size();
                if (intNumberOfStatement <= 12) {
                    report.updateTestLog("verifyStatementsSmoke", "On Page :: " + intNumberOfStatement + " :: Statements are Present.", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyStatementsSmoke", "On Page Statements are Not Present.", Status_CRAFT.FAIL);
                }
                //Validate the PDF Icon ::  w.PaperStatements.Promotion_First
                List<WebElement> oStatementsIconPDF = driver.findElements(getObject(deviceType() + "AccStatement.iconStatementPDF"));

                if (oStatementsIconPDF.size() <= 12) {
                    report.updateTestLog("verifyStatementsSmoke", "On Page Number of PDF icons present are :" + oStatementsIconPDF.size(), Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyStatementsSmoke", "On Page PDF icons present are not present", Status_CRAFT.FAIL);
                }

            } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
                // Check the number of Statemnets Present when statemnet page opens first
                if (intNumberOfStatement <= 12) {
                    report.updateTestLog("verifyStatementInfo", "On Mobile Page " + intNumberOfStatement + " :: Statements are Present.", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyStatementInfo", "On Mobile Page Statements are not Present.", Status_CRAFT.FAIL);
                }

                //Click on show more while show more element is present
                int intCounter = 2;
                do {
                    ScrollAndClickJS(deviceType() + "AccStatement.lblShowMore");
                    if (intNumberOfStatement <= (12 * intCounter)) {
                        report.updateTestLog("verifyStatementInfo", "On Mobile Page " + intNumberOfStatement + " :: Statements are Present.", Status_CRAFT.DONE);
                        intCounter++;
                    } else {
                        report.updateTestLog("verifyStatementInfo", "On Mobile Page Statements are not Present.", Status_CRAFT.FAIL);
                    }

                } while (isElementDisplayed(getObject(deviceType() + "AccStatement.lblShowMore"), 10));

                //Validate the PDF Icon
                ScrollToVisibleJS(strStatementListParent);
                List<WebElement> oStatementsIconPDF = driver.findElements(getObject(deviceType() + "AccStatement.iconStatementPDF"));

                if (oStatementsIconPDF.size() > 1) {
                    report.updateTestLog("verifyStatementInfo", "On Mobile Statement page number of PDF icons present are :" + oStatementsIconPDF.size(), Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyStatementInfo", "On Mobile Statement page number of PDF icons present are not present", Status_CRAFT.FAIL);
                }
            }

            //Newest statements should appears first :
            List<WebElement> oStatementsLabel = driver.findElements(getObject(deviceType() + "AccStatement.lblStatementTitle"));
            ScrollToVisibleJS(strStatementListParent);
            String strDateStatement1 = oStatementsLabel.get(1).getText().toUpperCase();
            String strDateStatement2 = oStatementsLabel.get(2).getText().toUpperCase();
            boolean blnNewestFirst = ValidateNewestFirst(strDateStatement1, strDateStatement2, "dd MMM yyyy");
            if (blnNewestFirst) {
                report.updateTestLog("verifyStatementInfo", " Newest Statement appears first.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyStatementInfo", " Newest Statement did not appears first.", Status_CRAFT.FAIL);
            }
        }
    }


    /**
     * Reusable : Smoke
     * Transactions
     */
    public void verifyNickNameSmoke() throws Exception {
        click(getObject("NickName.lblNickNameTab"));
        if (isElementDisplayed(getObject("AccNickname.txtAccountNickname"), 10) &&
                isElementDisplayed(getObject("AccNickname.btnEdit"), 10) &&
                isElementDisplayed(getObject("AccNickname.titleAccountNickName"), 10)) {

            String strUiNickname = driver.findElement(By.xpath("//input[contains(@name,'CHANGEACCOUNTNICKNAME[1]')]")).getAttribute("value");
            report.updateTestLog("verifyPresentAcctNickName", "Account Nickname textbox , Edit Button and Existing Nickname '" + strUiNickname + "'is correctly displayed on UI", Status_CRAFT.DONE);
            clickJS(getObject("AccNickname.btnEdit"), " Clicking Edit Button on Account Nickname page");
            if (isElementDisplayed(getObject("AccNickname.txtAccountNickname"), 30)) {
                sendKeys(getObject("AccNickname.txtAccountNickname"), "NickNameSmoke");
                click(getObject("AccNickname.btnCancel"), " Clicking Cancel Button on Account Nickname page");
                scrollToView(getObject("NickName.lblNickNameTab"));
            }
        }
    }

    /**
     * Reusable : Smoke
     * Cheque Search
     */
    public void verifyChequeSearchSmoke() throws Exception {
        clickJS(getObject("AccStatement.lblChequeSearchTab"), " Click on Cheque Search tab ");
        if (isElementDisplayed(getObject("AccStatement.txtbxChequeNumber"), 15) &&
                isElementDisplayed(getObject("AccStatement.btnSearchCheque"), 15)) {
            //Put Blank Value in search box
            click(getObject("AccStatement.btnSearchCheque"));
            if (isElementDisplayed(getObject("AccStatement.lblErrorInline"), 20)) {
                report.updateTestLog("verifyChequeSearch", "The Error Content ::" + getText(getObject("AccStatement.lblErrorInline")) + "::appears correctly for Null Values in Cheque Number text box.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyChequeSearch", "The Error Content appears incorrectly for Null Values in Cheque Number text box.", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * To verify if Back button present on Confirmation screen CFPUR-4717
     */
    public void verifyBackArrow() throws Exception {

        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("w.backarrow"), 20)) {
                report.updateTestLog("verifyBackArrow", "Back Navigation arrow present on the screen", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyBackArrow", "Back Navigation arrow not present on the screen", Status_CRAFT.PASS);
            }
        } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject("m.backarrow"), 20)) {
                report.updateTestLog("verifyBackArrow", "Back Navigation arrow present on the screen", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyBackArrow", "Back Navigation arrow not present on the screen", Status_CRAFT.PASS);
            }
        }
    }

    public void continuenavigation() throws InterruptedException {
        waitForPageLoaded();
        click(getObject(deviceType() + "login.AmendedContinue"));
        if (isElementDisplayed(getObject("launch.continuetohome"), 10)) {
            Thread.sleep(1000);
            click(getObject("launch.continuetohome"), "Continue to Home Clicked");
            waitForPageLoaded();
            report.updateTestLog("continuenavigation", "Continue to Home clicked", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("continuenavigation", "Continue to Home not present on the screen", Status_CRAFT.DONE);
        }
    }

    public void standingordernavigation() throws Exception {
        Thread.sleep(2000);
        if (isElementDisplayed(getObject("launch.Satndingorder"), 5)) {
            click(getObject("launch.Satndingorder"), "Standing order Clicked");
            Thread.sleep(2000);
            report.updateTestLog("standingordernavigation", "Standing order Clicked", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("standingordernavigation", "Standing order not present on the screen", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Function to check InterruptsImage before home page
     * Update on     Updated by    Reason
     * 11/02/2019    C966119        Added Script for the function
     */
    public void verifyInterruptsImage() throws Exception {
        if (!isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 3)) {
            if (isElementDisplayed(getObject("launch.imgMarketing"), 2)) {
                clickJS(getObject("xpath~//*[text()='Continue to home']/parent::a"), "'Continue to home'");
                waitForPageLoaded();
            }
            if (isElementDisplayed(getObject("launch.continuetohome"), 2)) {
                clickJS(getObject("launch.continuetohome"), "Continue to home");
                waitForPageLoaded();
            }
        }
    }


    public void verifyFAQslink() throws InterruptedException {
        scrollToView(getObject("home.FAQslink"));
        if (isElementDisplayed(getObject("home.FAQslink"), 20)) {
            clickJS(getObject("home.FAQslink"), "FAQs link Clicked");
            Thread.sleep(2000);
            report.updateTestLog("FAQslink", "FAQs link clicked", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("FAQslink", "FAQs link not present on the screen", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to Accountsecurity pin digits
     */

    public void enterAccountSecurityPin() throws InterruptedException {
        if (isElementDisplayed(getObject("w.Accountsecurityheader"), 5)) {
            report.updateTestLog("Accountsecurityheader", "Accountsecurityheader Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Accountsecurityheader", "Accountsecurityheader not displayed successfully", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("w.Accountsecurityforgetpin"), 2)) {
            report.updateTestLog("Accountsecurity forgetpin", "Accountsecurity forgetpin Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Accountsecurity forgetpin", "Accountsecurity forgetpin not displayed successfully", Status_CRAFT.FAIL);
        }
        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");

        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("w.Accountsecuritypin"));

        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
            }
        }

        //Find x and y coordinates of Forgot Pin Link
        WebElement Image = driver.findElement(getObject("w.Accountsecurityforgetpin"));
        //Used points class to get x and y coordinates of element.
        Point ForgotLink = Image.getLocation();
        int xcordi = ForgotLink.getX();
        int ycordi = ForgotLink.getY();

        //Find x and y coordinates of login Button
        WebElement confButton = driver.findElement(getObject("w.home.btnLogin"));
        //Used points class to get x and y coordinates of element.
        Point confirmButton = confButton.getLocation();
        int xcordi1 = confirmButton.getX();
        int ycordi1 = confirmButton.getY();

        if (ycordi > ycordi1) {
            report.updateTestLog("validateForgotPin Position", "Forgot your Pin Position is below Confirm Button", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateForgotPin Position", "Forgot your Pin Position NOT below Confirm Button", Status_CRAFT.FAIL);
        }

        report.updateTestLog("EnterAccountsecuritypin", "Entered PIN digits", Status_CRAFT.PASS);
        clickJS(getObject("w.home.btnLogin"), "Log in");

    }

    public void verifyCookieslink() throws InterruptedException {

        scrollToView(getObject("home.cookieslink"));
        if (isElementDisplayed(getObject("home.cookieslink"), 20)) {
            clickJS(getObject("home.cookieslink"), "Cookies link Clicked");
            Thread.sleep(2000);
            report.updateTestLog("Cookieslink", "Cookies link clicked", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Cookieslink", "Cookies link not present on the screen", Status_CRAFT.FAIL);
        }
    }

    // child window handler function
    public void childWindowhandler() throws Exception {

        waitForPageLoaded();
        Thread.sleep(3000);
        String URLlink = dataTable.getData("General_Data", "VerifyDetails");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        if (tabs.size() > 0) {

            driver.switchTo().window(tabs.get(1));
            waitForPageLoaded();
            String linkURL = driver.getCurrentUrl();
            if (linkURL.equalsIgnoreCase(URLlink)) {
                report.updateTestLog("ChildWindowhandler", " User is naviagted to correct URL :: " + URLlink + ":: succssfully.", Status_CRAFT.PASS);
                verifyCheckContentPresent(deviceType() + "home.Accessibilitycontent");
                driver.close();
                driver.switchTo().window(tabs.get(0));
            } else {
                report.updateTestLog("ChildWindowhandler", " User is not naviagte to correct URL ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("ChildWindowhandler", " New Tab did not open ", Status_CRAFT.FAIL);
        }

    }

    //function to click footer links (1292,1294)
    public void verifyfooterlinknaviagtion() throws Exception {
        String strLink = dataTable.getData("General_Data", "Nickname");//add the link name to be clicked in data sheet
        scrollToView(getObject("home.cookieslink"));
        Thread.sleep(3000);
        List<WebElement> allLinks = findElements(getObject("home.footerlist"));
        if (allLinks.size() > 1) {
            int intIndex = 1;
            for (WebElement ele : allLinks) {
                String linkname = ele.getText();
                if (linkname.equalsIgnoreCase(strLink)) {
                    ele.click();
                    intIndex = intIndex;
                    report.updateTestLog("verifyfooterlinks", strLink + " is clicked  ", Status_CRAFT.PASS);
                    break;
                }
                intIndex = intIndex++;

            }
        }
        Thread.sleep(1000);
        // childWindowhandler();

    }


    /**
     * Function : Verify the Credit Card Details
     * Created on    Created by     Reason
     * 15/04/2019
     */
    /*public void verifyCreditCardAccountDetails() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = deviceType() + "home.parentaccount_new";

        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyNavigateToAccountPageAnyAccount", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        ScrollToVisibleJS(strParentObject);

        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();
            if ((strRowText.contains(strAccountToCheck.toUpperCase())) && (strRowText.contains(strAccountIBAN.toUpperCase()))) {
                report.updateTestLog("verifyCreditCardAccountDetails", "The Credit Card with 'Unavailable' status displayed successfully.", Status_CRAFT.DONE);
            }
        }
    }*/

    // Function to verify presence of Go Back arrow on screens
    public void gobacknavigation() throws Exception {
        waitForPageLoaded();
        waitForElementToClickable(getObject(deviceType() + "home.Goback"), 14);
        if (isElementDisplayed(getObject(deviceType() + "home.Goback"), 3)) {
            report.updateTestLog("gobacknavigation", "Go Back link present on Screen", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "home.Goback"), "Back Arrow");
            waitForPageLoaded();
        } else {
            report.updateTestLog("gobacknavigation", "Go Back link is not present on Screen", Status_CRAFT.FAIL);
        }
    }

    public void gobacknavigationPayments() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.GoBack"), 20)) {
            clickJS(getObject(deviceType() + "home.GoBack"), "Go Back link");
            Thread.sleep(2000);
        }

        if (isElementDisplayed(getObject("Check.Payments"), 20)) {

            report.updateTestLog("gobacknavigationPayments", "Successful navigation by Go Back link ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("gobacknavigationPayments", "Navigation incorrect", Status_CRAFT.FAIL);
        }

    }

    public void gobacknavigationServices() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.GoBack"), 20)) {
            clickJS(getObject(deviceType() + "home.GoBack"), "Go Back link");
            Thread.sleep(2000);
        }

        if (isElementDisplayed(getObject("Check.services"), 20)) {

            report.updateTestLog("gobacknavigationPayments", "Successful navigation by Go Back link ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("gobacknavigationPayments", "Navigation incorrect", Status_CRAFT.FAIL);
        }

    }

    public void gobacknavigationAccountsPage() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.GoBack"), 20)) {
            clickJS(getObject(deviceType() + "home.GoBack"), "Go Back link");
            Thread.sleep(2000);
        }

        if (isElementDisplayed(getObject("Check.accountspage"), 20)) {

            report.updateTestLog("gobacknavigationPayments", "Successful navigation by Go Back link ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("gobacknavigationPayments", "Navigation incorrect", Status_CRAFT.FAIL);
        }

    }

    public void AmendSecurityPageContinue() throws Exception {
        if (!isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 30)) {
            if (isElementDisplayed(getObject("AmndTnC.pageTitle"), 2)
                    || isElementDisplayed(getObject("xpath~//h3/descendant::*[contains(text(),'Terms and Conditions ')]"), 2)
                    || isElementDisplayed(getObject("xpath~//*[contains(text(),'365 Phone and Digital')]"), 2)) {
                report.updateTestLog("AmendSecurityPageContinue", "Amend Terms And Conditions page exist ", Status_CRAFT.DONE);
                waitForPageLoaded();
                clickJS(getObject("AmndTnc.btnContinue"), "Continue Button on Amend terms And Conditions");
                waitForPageLoaded();
                waitForJQueryLoad();
                if (isElementDisplayed(getObject("xpath~//*[text()='Continue to home']/parent::a"), 5)) {
                    clickJS(getObject("xpath~//*[text()='Continue to home']/parent::a"), "'Continue to home'");
                    waitForPageLoaded();
                } else if (isElementDisplayed(getObject("AmndTnc.btnContinue"), 2)) {
                    clickJS(getObject("AmndTnc.btnContinue"), "Continue Button on Amend terms And Conditions");
                    waitForPageLoaded();
                } else if (isElementDisplayed(getObject("launch.continuetohome"), 2)) {
                    scrollToView(getObject("launch.continuetohome"));
                    clickJS(getObject("launch.continuetohome"), "Continue to home");
                    waitForPageLoaded();
                }
            }
        }
    }


    /**
     * Mobile App Automation Method : Click on any account from home page
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void verifyAccountClickAndNavigate() throws Exception {
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = "";
        if (deviceType.equalsIgnoreCase("MobileWeb") || deviceType.equalsIgnoreCase("Tablet")) {
            strParentObject = "xpath~//*[@class='accessibilityStyle']/following-sibling::span[contains(@class,'boi_input boi-')][text()='" + strAccountIBAN + "']";
            ScrollAndClickJS(strParentObject);
            waitForJQueryLoad();waitForPageLoaded();
            report.updateTestLog("verifyAccountClickAndNavigate", " Account having IBAN  :: '" + strAccountIBAN + " ' selected ", Status_CRAFT.PASS);
            waitForPageLoaded();

        } else if (deviceType.equalsIgnoreCase("Web")) {
            List<WebElement> oUIRows = driver.findElements(getObject("home.AccountIBAN_Tile"));
            report.updateTestLog("verifyCheckContentNotPresent", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
            ScrollToVisibleJS("home.AccountIBAN_Tile");

            for (int j = 0; j < oUIRows.size(); j++) {
                String strRowText = oUIRows.get(j).getText().toUpperCase();
                if (strRowText.contains(strAccountIBAN.toUpperCase())) {
                    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                    js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                    report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                    js.executeScript("arguments[0].click();", oUIRows.get(j));
                    waitForElementPresent(getObject("AccStatement.lblStatementTab"), 10);
                    report.updateTestLog("verifyAccountStatement_AllAccounts", "The Account having Last four digit of IBAN :: " + strAccountIBAN + " :: is displayed at position ::" + (j + 1) + ":: and clicked successfully.", Status_CRAFT.DONE);
                    break;
                }
            }
        }
        else {
            report.updateTestLog("verifyAccountClickAndNavigate", " Account or IBAN having value with last four digit :: '" + strAccountIBAN + "' not found", Status_CRAFT.FAIL);
            driver.close();
            driver.quit();
        }
        if (isElementDisplayed(getObject(deviceType()+ "AccStatement.lblStatementTab"), 30)) {
            report.updateTestLog("verifyAccountClickAndNavigate", "Account details Drill Down Page Opened Successfully and Validated against statement tab", Status_CRAFT.PASS);
        }
    }

    /**
     * Mobile App Automation Method : Click on any Credit card from home page
     * Update on     Updated by     Reason
     * 27/09/2019    C103401        for mobile Automation
     */
    public void verifyCreditCardClickAndNavigate() throws Exception {
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = "";
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            //*[not(contains(@class,'col-hidden'))]/*/*[@class='accessibilityStyle']/following-sibling::*[@aria-hidden='true'][contains(@class,'boi_input')][text()='**** **** **** 4766']
            strParentObject = "xpath~//*[not(contains(@class,'col-hidden'))]/*/*[@class='accessibilityStyle']/following-sibling::*[@aria-hidden='true'][contains(@class,'boi_input')][contains(@class,'boi_input boi-')][text()='" + strAccountIBAN + "']";
        } else if (deviceType.equalsIgnoreCase("Web")) {
            strParentObject = "xpath~//*[@class='accessibilityStyle']/following-sibling::*[(@aria-hidden='true')][contains(@class,'boi_input boi-')][text()='" + strAccountIBAN + "']";
        }

        ScrollAndClickJS(strParentObject);
        waitForPageLoaded();
        report.updateTestLog("verifyAccountClickAndNavigate", "Clicked Credit Card with IBAN value :: " + strAccountIBAN, Status_CRAFT.DONE);
        if (isElementDisplayed(getObject("w.myAccount.sectionBlueCard"), 5)) {
            report.updateTestLog("verifyAccountClickAndNavigate", "Blue Card Drill Down Page Opened Successfully.", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyAccountClickAndNavigate", "Blue Card Drill Down Page Did NOT Opened Successfully.", Status_CRAFT.FAIL);
        }
    }

    /**
     * Mobile App Automation Method : To Validate Account Statement Details on mobile App: CFPUR-69, CFPUR-1388
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void verifyAccountStatementOnMobile() throws Exception {
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        waitForJQueryLoad();
        waitForPageLoaded();
        String strParentObject = "xpath~//*[@class='accessibilityStyle']/following-sibling::span[contains(@class,'boi_input boi-')][text()='" + strAccountIBAN + "']";
        ScrollAndClickJS(strParentObject);
        waitForJQueryLoad();
        waitForPageLoaded();
        waitForElementToClickable(getObject(deviceType() + "AccStatement.lblStatementTab"), 15);
        clickJS(getObject(deviceType() + "AccStatement.lblStatementTab"), "Clicking on 'Statements' tab");
        waitForJQueryLoad();
        waitForPageLoaded();
        waitForPageLoaded();
        if (isElementDisplayed(getObject("ServiceManage.txtServiceUnavailable"), 5)) {
            report.updateTestLog("selectServiceDeskOption", " 'Service unavailable :: Unfortunately, this service is unavailable at this time. Please try again later.' is displyed on Service Desk page", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("mw.AccStatement.lblNoStatementFound"), 10)) {
            report.updateTestLog("verifyAccountStatement_AllAccounts", "As no statement is available for this account so message ..'No Statements Found' Displayed correctly.", Status_CRAFT.DONE);
            return;
        } else {
            waitForPageLoaded();
            waitForElementToClickable(getObject("xpath~//div[@class='visible-mobile']/descendant::div[contains(@class,'boi-statements-displayed')]/descendant::button[@class='boi-statement-button'][1]"), 8);
            List<WebElement> oStatementsIconPDF1 = driver.findElements(getObject("mw.AccStatement.iconStatementPDF"));
            if (oStatementsIconPDF1.size() >= 1) {
                report.updateTestLog("verifyAccountStatement_AllAccounts", "Total ::" + (oStatementsIconPDF1.size()) + " :: Account Statements are Present when we open the statemnets tab.", Status_CRAFT.PASS);
                verifyStatementInfo(deviceType() + "AccStatement.lblShowMore");
            } else {
                report.updateTestLog("verifyAccountStatement_AllAccounts", "Not able to see any statements..Failing the step", Status_CRAFT.FAIL);
            }
        }

    }

    /**
     * Mobile App Automation Method : To Validate Newest Statement first : CFPUR-69, CFPUR-1388
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void verifyNewestStatementFirst() throws Exception {
        List<WebElement> oStatementsLabelDay = driver.findElements(getObject("mw.AccStatement.lblStatementDay"));
        List<WebElement> oStatementsLabelMonth = driver.findElements(getObject("mw.AccStatement.lblStatementMonth"));
        List<WebElement> oStatementsLabelYear = driver.findElements(getObject("mw.AccStatement.lblStatementYear"));

        String strDateStatement1 = oStatementsLabelDay.get(0).getText() + "/" + oStatementsLabelMonth.get(0).getText() + "/" + oStatementsLabelYear.get(0).getText();
        String strDateStatement2 = oStatementsLabelDay.get(1).getText() + "/" + oStatementsLabelMonth.get(1).getText() + "/" + oStatementsLabelYear.get(1).getText();
        boolean blnNewestFirst = ValidateNewestFirst(strDateStatement1, strDateStatement2, "dd/MM/yyyy");
        if (blnNewestFirst) {
            report.updateTestLog("verifyStatementInfo", " Correct statement order displayed as newest appears first ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyStatementInfo", " Newest Statement did not appears first.", Status_CRAFT.FAIL);
        }

    }

    /**
     * Mobile App Automation Method : To Validate and Load Account Statement : CFPUR-69, CFPUR-1388
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void verifyLoadAllStatement() throws Exception {
        String strStatementListParent = "mw.AccStatement.tblParent";
        List<WebElement> oStatements = driver.findElements(getObject(strStatementListParent));
        int intNumberOfStatement = oStatements.size();
        int intNumberOfStatementNew = 0;
        if (isElementDisplayed(getObject(deviceType()+ "AccStatement.lblShowMore"), 5)) {
            int intCounter = 2;
            do {
                ScrollAndClickJS(deviceType()+ "AccStatement.lblShowMore");
                if (intNumberOfStatement <= (12 * intCounter)) {
                    List<WebElement> oStatementsNew = driver.findElements(getObject(strStatementListParent));
                    intNumberOfStatementNew = oStatementsNew.size();
                    report.updateTestLog("verifyStatementInfo", "On Mobile app after clicking 'Show More' button :: " + intNumberOfStatementNew + " :: Statements are Present.", Status_CRAFT.PASS);
                    intCounter++;
                } else {
                    report.updateTestLog("verifyStatementInfo", "On clicking 'Show more' button , statements count is not getting added..which is not expected.", Status_CRAFT.FAIL);
                }

            } while (isElementDisplayed(getObject(deviceType()+ "AccStatement.lblShowMore"), 10));

        }

    }

    /**
     * Mobile App Automation Method : To Validate Account Statement Information : CFPUR-69, CFPUR-1388
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void verifyStatementInfo(String strPageToClick) throws Exception {
        String strStatementListParent = "mw.AccStatement.tblParent";
        verifyLoadAllStatement();
        ScrollToVisibleJS(strStatementListParent);
        List<WebElement> oStatementsIconPDF = driver.findElements(getObject("mw.AccStatement.iconStatementPDF"));
        if (oStatementsIconPDF.size() >= 1) {
            report.updateTestLog("verifyStatementInfo", "On Mobile App Total number of Statements PDF icons present are :" + oStatementsIconPDF.size(), Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyStatementInfo", " PDF icons present are not present", Status_CRAFT.FAIL);
        }
        if (oStatementsIconPDF.size() >= 3) {
            verifyNewestStatementFirst();
        }
        verifyStatementType();
    }

    /**
     * Mobile App Automation Method : To Validate Account eStatement Information for all accounts : CFPUR-69, CFPUR-1388
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void VerifyAccountEStatement() throws Exception {
        if (deviceType.equals("MobileWeb")) {
            verifyAccountStatementOnMobile();
        } else {
            VerifyAccountStatement_AllAccounts();
        }
    }


    /**
     * Mobile App Automation Method : To Verify Login to home page after device provising
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void verifyLoginToHomePage() throws Exception {
        SCA_MobileAPP SCAPage = new SCA_MobileAPP(scriptHelper);
        SCAPage.welcomeScreen();
        enterRequiredPin();
        verifyHomePage();
    }

    /**
     * Mobile App Automation Method : To navigate from home menu (Footer menu / Header Menu) to various other options
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void verifyFooterNavigation() throws Exception {
        String strNavigationToAndFrom = dataTable.getData("General_Data", "VerifyDetails");
        String[] arrContentToCheck = strNavigationToAndFrom.split("::");
        String strLable = "";
        String strFooterHeader = "";
        String strNavigateTo = "";
        String strVerifyHeader = "";
        for (int i = 0; i < arrContentToCheck.length; i++) {
            String[] arrValidationCheck = arrContentToCheck[i].split(",");
            strLable = arrValidationCheck[0];
            strFooterHeader = arrValidationCheck[1];
            strNavigateTo = "xpath~//button[@role='menuitem'][text()='" + strLable + "']";
            if (strLable.equals("Accounts")) {
                click(getObject(strNavigateTo), "Clicked on 'Account' footer");
                verifyHomePageMobile();
            } else {
                strVerifyHeader = "xpath~//div[@style='text-align: center; ; text-align:center;width:100%;margin-left:0px;']/descendant::h1[text()='" + strFooterHeader + "']";
                click(getObject(strNavigateTo));
                if ((isElementDisplayed(getObject(strVerifyHeader), 25)) && (isElementDisplayed(getObject("mw.home.imgProfileIcon"), 20))) {
                    report.updateTestLog("verifyFooterNavigation", "Clicked On Footer :: " + strFooterHeader + " :: Navigated successfully to Page Having Header :: " + strFooterHeader + " :: with Profile icon is visible successfully.", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyFooterNavigation", "Either Page Header or Profile icon is not visible on Page :: " + strLable, Status_CRAFT.FAIL);
                }
            }
        }
    }

    /*
     * Created by : C966003
     * Update on     Updated by     Reason
     * 12/06/2019     C966003
     */
    public void backButtonValidation() throws Exception {
        String strAccount = dataTable.getData("General_Data", "Account_Name");
        String[] strAccountList = strAccount.split(";");

        for (int i = 0; i < strAccountList.length; i++) {
            if (strAccountList[i].equalsIgnoreCase("current") |
                    strAccountList[i].equalsIgnoreCase("savings") |
                    strAccountList[i].equalsIgnoreCase("loan")) {

                String strColorXpath = "xpath~(//*[contains(@class,'tc-ripple-effect')]" +
                        "/descendant::*[contains(@class,'color-" + strAccountList[i] + "-account')])[1]";
                isElementDisplayedWithLog(getObject(strColorXpath),
                        strAccountList[i] + " account", "HomePage", 5);
                clickJS(getObject("xpath~(//*[contains(@class,'tc-ripple-effect')]" +
                        "/descendant::*[contains(@class,'color-" + strAccountList[i] + "-account')])[1]"), strAccountList[i]);
                waitForPageLoaded();
                if (!isMobile) {
                    clickJS(getObject("xpath~//*[contains(@alt,'Back')][not(contains(@disabled,'disabled'))]"), "Back button");
                } else {
                    if (isElementDisplayed(getObject("xpath~//*[contains(@title,'MobileHeaderBack')]"), 5)) {
                        clickJS(getObject("xpath~//*[contains(@title,'MobileHeaderBack')]"), "Back button");
                    } else if (isElementDisplayed(getObject("xpath~//*[contains(@title,'HeaderBack')]"), 1)) {
                        clickJS(getObject("xpath~//*[contains(@title,'HeaderBack')]"), "Back button");
                    } else {
                        report.updateTestLog("backButtonValidation", "Back Arrow icon is not visible on Account Page for :: " + strAccountList[i], Status_CRAFT.FAIL);
                    }
                }
                waitForPageLoaded();
            } else if (strAccountList[i].equalsIgnoreCase("Personal Details")) {
                if (!isMobile) {
                    clickJS(getObject("xpath~//*[contains(@alt,'Back')][not(contains(@disabled,'disabled'))]"), "Back button");
                } else {
                    clickJS(getObject("xpath~//button[contains(text(),'" + strAccountList[i] + "')]"), strAccountList[i]);
                    waitForPageLoaded();
                    if (isElementDisplayed(getObject("xpath~//*[contains(@title,'MobileHeaderBack')]"), 5)) {
                        clickJS(getObject("xpath~//*[contains(@title,'MobileHeaderBack')]"), "Back button");
                    } else if (isElementDisplayed(getObject("xpath~//*[contains(@title,'HeaderBack')]"), 1)) {
                        clickJS(getObject("xpath~//*[contains(@title,'HeaderBack')]"), "Back button");
                    } else {
                        report.updateTestLog("backButtonValidation", "Back Arrow icon is not visible on Account Page for :: " + strAccountList[i], Status_CRAFT.FAIL);
                    }
                }
                waitForPageLoaded();

            }
        }
    }

    /**
     * Mobile App Automation Method : To Verify Login to home page after device provising
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void verifyLinksNavigation() throws Exception {

        String linkToVerify = dataTable.getData("General_Data", "Account_Label");
        String[] linkToVerifyArr = linkToVerify.split("::");
        clickJS(getObject("home.FAQtab"), "Clicking on link :'FAQ link'");
        waitForPageLoaded();
        waitForJQueryLoad();
        verifyNewlyOpenedTab(linkToVerifyArr[0]);
        if (isMobile) {
            driver.navigate().back();
        }
        waitForPageLoaded();
        waitForJQueryLoad();
        waitForPageLoaded();
        clickJS(getObject("home.security"), "Clicking on link :'Security link'");
        waitForPageLoaded();
        waitForJQueryLoad();
        verifyNewlyOpenedTab(linkToVerifyArr[1]);
        if (isMobile) {
            driver.navigate().back();
        }
        waitForPageLoaded();
        waitForJQueryLoad();
        waitForPageLoaded();
        clickJS(getObject("home.terms&conditions"), "Clicking on link :'Terms and conditions link'");
        waitForPageLoaded();
        waitForJQueryLoad();
        verifyNewlyOpenedTab(linkToVerifyArr[2]);
        if (isMobile) {
            driver.navigate().back();
        }
    }

    /**
     * Mobile App Automation Method : To Verify Paperless Statement Promotion Info
     * Update on     Updated by     Reason
     * 27/06/2019    C966119        for mobile Automation
     */
    public void VerifyPaperlessStatementPromotionInfoMobile() throws Exception {
        if (isElementDisplayed(getObject(deviceType() +"AccStatement.lblStatementTab"), 5)) {
            report.updateTestLog("Statement Tab", "Statement Tab is present", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() +"AccStatement.lblStatementTab"), "Statement Tab");
        } else {
            report.updateTestLog("Statement Tab", "Statement Tab is not present", Status_CRAFT.FAIL);
        }
        Thread.sleep(3000);
        waitforVisibilityOfElementLocated(getObject("ManageStatements.lblRecentStatements"));
        String statementPromotion = dataTable.getData("General_Data", "Relationship_Status");
        if ((statementPromotion.toUpperCase()).equalsIgnoreCase("YES")) {
            if (isElementDisplayed(getObject(deviceType() + "PaperStatements.Promotion_First"), 5)) {
                report.updateTestLog("PaperlessStmtPromotionIcon", "Paperless Statement Promotion is displayed with Text :: " + getText(getObject(deviceType() + "PaperStatements.Promotion_Second")), Status_CRAFT.PASS);
            } else {
                report.updateTestLog("PaperlessStmtPromotionIcon", "Paperless Statement Promotion is not displayed", Status_CRAFT.FAIL);
            }
        } else if ((statementPromotion.toUpperCase()).equalsIgnoreCase("NO")) {
            if (isElementDisplayed(getObject(deviceType() + "PaperStatements.Promotion_First"), 5)) {
                report.updateTestLog("PaperlessStmtPromotionIcon", "Paperless Statement Promotion is displayed", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("PaperlessStmtPromotionIcon", "Paperless Statement Promotion is not displayed", Status_CRAFT.PASS);
            }
        }

    }

    /**
     * Mobile App Automation Method : To Verify Paperless Statement Promotion Info
     * Update on     Updated by     Reason
     * 27/06/2019    C966119        for mobile Automation
     */
    public void VerifyPaperlessStatementPromotionAfterChange() throws Exception {
//        if (deviceType.equalsIgnoreCase("Web")) {
            NavigationToHomePage();
            waitForPageLoaded();
            String strParentObject = "xpath~//*[@class='accessibilityStyle']/following-sibling::span[contains(@class,'boi_input boi-')][text()='" + strAccount + "']";
            waitForElementToClickable(getObject(strParentObject) , 10);
            ScrollAndClickJS(strParentObject);
            report.updateTestLog("verifyAccountClickAndNavigate", "Clicked the Account IBAN having value with last four digit :: " + strAccount, Status_CRAFT.DONE);
            if (isElementDisplayed(getObject(deviceType() + "myAccount.sectionBlueCard"), 5)) {
                report.updateTestLog("verifyAccountClickAndNavigate", "Blue Card Drill Down Page Opened Successfully.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyAccountClickAndNavigate", "Blue Card Drill Down Page Did NOT Opened Successfully.", Status_CRAFT.FAIL);
            }
//        } else {
//            waitforVisibilityOfElementLocated(getObject(deviceType() + "titleGoback"));
//            clickJS(getObject(deviceType() + "titleGoback"), " Back ");
//        }
        if (isElementDisplayed(getObject(deviceType() +"AccStatement.lblStatementTab"), 5)) {
            report.updateTestLog("Statement Tab", "Statement Tab is present", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() +"AccStatement.lblStatementTab"), "Statement Tab");
        } else {
            report.updateTestLog("Statement Tab", "Statement Tab is not present", Status_CRAFT.FAIL);
        }
        Thread.sleep(3000);
        waitforVisibilityOfElementLocated(getObject("ManageStatements.lblRecentStatements"));
        String statementPromotionAfterChange = dataTable.getData("General_Data", "Nickname");
        if ((statementPromotionAfterChange.toUpperCase()).equalsIgnoreCase("NO_TO_YES")) {
            waitForElementToClickable(getObject(deviceType()+"PaperStatements.Promotion_First"), 15);
            if (isElementDisplayed(getObject(deviceType() + "PaperStatements.Promotion_First"), 3)) {
                report.updateTestLog("PaperlessStmtPromotionIcon", "Paperless Statement Promotion is Changed from NO_TO_YES", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("PaperlessStmtPromotionIcon", "Paperless Statement Promotion is not Changed from NO_TO_YES", Status_CRAFT.FAIL);
            }
        } else if ((statementPromotionAfterChange.toUpperCase()).equalsIgnoreCase("YES_TO_NO")) {
            if (isElementDisplayed(getObject(deviceType() + "PaperStatements.Promotion_First"), 5)) {
                report.updateTestLog("PaperlessStmtPromotionIcon", "Paperless Statement Promotion is NOT Changed from YES_TO_NO", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("PaperlessStmtPromotionIcon", "Paperless Statement Promotion is Changed from YES_TO_NO", Status_CRAFT.PASS);
            }
        }

    }

    /**
     * Mobile App Automation Method : To Verify Paperless Statement Promotion Info
     * Update on     Updated by     Reason
     * 27/06/2019    C966119        for mobile Automation
     */
    public void VerifyPaperlessStatementPromotionNavigationMobile() throws Exception {

        clickJS(getObject(deviceType() + "PaperStatements.Promotion_First"), "Paperless Statement Promotions icon ");
        if (isElementDisplayed(getObject(deviceType() + "ManageStatements.lblHeaderTurnOnOff"), 5)) {
            report.updateTestLog("VerifyPaperlessStatementPromotionNavigationMobile", "Once Clicked on 'Reduce Clutter' icon..Successfully navigated to the 'Turn Paper statement ON/OFF'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPaperlessStatementPromotionNavigationMobile", "Once Clicked on 'Reduce Clutter' icon..not navigated to the 'Turn Paper statement ON/OFF'", Status_CRAFT.FAIL);
        }
    }

    /**
     * Mobile App Automation Method : To Verify Paperless Statement Promotion Info
     * Update on     Updated by     Reason
     * 27/06/2019    C966119        for mobile Automation
     */
    public void VerifyToggleOnOFfPaperOFF() throws Exception {
        String strChangeStatus = dataTable.getData("General_Data", "Nickname");
        String strStatus = "Checked";
        String strObj = "xpath~//label[contains(text(),'" + strAccount + "')]/../../following-sibling::div[@style='text-align: left; ']/div/div/input[@type='checkbox']";
        strStatus = driver.findElement(getObject("launch.edtDateOfBirth")).getAttribute("checked");
        if (!(strStatus.equals("Checked"))) {
            String strObjToClick = "xpath~//label[contains(text(),'" + strAccount + "')]/../../following-sibling::div[@style='text-align: left; ']/div/div/label[@class='onoffswitch-label']";
            report.updateTestLog("VerifyToggleOnOFfPaperChecked", "Account number Ending with four digit :: " + strAccount + " :: have Paper less statement flag 'OFF'", Status_CRAFT.PASS);
            if (strChangeStatus.equals("NO_TO_YES")) {
                clickJS(getObject(strObjToClick), "Changing the Paper statement notification from ON to OFF");
            }
        } else {
            report.updateTestLog("VerifyToggleOnOFfPaperChecked", "Account number Ending with four digit :: " + strAccount + " :: have Paper less statement flag 'ON'", Status_CRAFT.FAIL);
        }
    }

    /**
     * Mobile App Automation Method : To Verify Paperless Statement Promotion Info
     * Update on     Updated by     Reason
     * 27/06/2019    C966119        for mobile Automation
     */
    public void VerifyToggleOnOFfPaperChecked() throws Exception {
        String strChangeStatus = dataTable.getData("General_Data", "Nickname");
        String strObj = "xpath~//label[contains(text(),'" + strAccount + "')]/../../following-sibling::div[@style='text-align: left; ']/div/div/input[@checked='checked']";
        if (isElementDisplayed(getObject(strObj), 5)) {
            report.updateTestLog("VerifyToggleOnOFfPaperChecked", "Account number Ending with four digit :: " + strAccount + " :: have Paper less statement flag 'ON'", Status_CRAFT.PASS);
            String strObjToClick = "xpath~//label[contains(text(),'" + strAccount + "')]/../../following-sibling::div[@style='text-align: left; ']/div/div/label[@class='onoffswitch-label']";
            if (strChangeStatus.equals("YES_TO_NO")) {
                scrollToView(getObject(strObjToClick));
                Thread.sleep(1000);
                clickJS(getObject(strObjToClick), "Changing the Paper statement notification from ON to OFF");
                Thread.sleep(3000);
            }
        } else {
            report.updateTestLog("VerifyToggleOnOFfPaperChecked", "Account number Ending with four digit :: " + strAccount + " :: have Paper less statement flag 'OFF'", Status_CRAFT.FAIL);
        }
    }

    /**
     * Mobile App Automation Method : To Verify and Get Account Having Paper ON
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void VerifyCopyOnTurnPaperOnOff() throws Exception {
        String strText = dataTable.getData("General_Data", "Account_Label");
        if (strText.length() > 0) {
            String[] strTextToCheck = strText.split("::");
            for (int i = 0; i < strTextToCheck.length; i++) {
                scrollToView(getObject("xpath~//div[@class='boi_label']"));
                if (isElementDisplayed(getObject("xpath~//div[@class='boi_para'][text()='" + strTextToCheck[i] + "']"), 1)) {
                    report.updateTestLog("VerifyCopyOnTurnPaperOnOff", " Copy Content on 'Turn paper statements On/Off' is displayed as expected :: " + strTextToCheck[i], Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("VerifyCopyOnTurnPaperOnOff", " Copy Content on 'Turn paper statements On/Off' is displayed is NOT expected", Status_CRAFT.FAIL);
                }
            }
        }
    }

    /**
     * Mobile App Automation Method : To Verify and Get Account Having Paper ON
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void verifySetPaperOnAfterChangeMobile() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "ManageStatements.lblHeaderTurnOnOff"), 4)) {
            report.updateTestLog("verifySetPaperOnAfterChangeMobile", "Page 'Turn paper statements On/Off' is opened successfully.", Status_CRAFT.PASS);
            List<WebElement> oPaperOnCount = driver.findElements(getObject("mw.PaperlessPromotionON"));
            if (oPaperOnCount.size() > 0) {
                for (int j = 0; j < oPaperOnCount.size(); j++) {
                    String strRowText = oPaperOnCount.get(j).getText().toUpperCase();
                    if (strRowText.length() > 4) {
                        strAccount = strRowText.substring(strRowText.length() - 4);
                        String strObj = "xpath~//label[contains(text(),'" + strAccount + "')]/../../following-sibling::div[@style='text-align: left; ']/div/div/input[@checked='checked']";
                        if (isElementDisplayed(getObject(strObj), 3)) {
                        } else {
                            String strObjToClick = "xpath~//label[contains(text(),'" + strAccount + "')]/../../following-sibling::div[@style='text-align: left; ']/div/div/label[@class='onoffswitch-label']";
                            scrollToView(getObject(strObjToClick));
                            try {
                                clickJS(getObject(strObjToClick), "Changing the Papeless Statement Notification from OFF to ON for Account Number ending with ::" + strAccount);
                                waitForPageLoaded();
                            } catch (UnreachableBrowserException e) {
                            e.printStackTrace();
                            }
                            oPaperOnCount = driver.findElements(getObject("mw.PaperlessPromotionON"));
                        }
                    } else {
                        report.updateTestLog("verifySetPaperOnAfterChangeMobile", "Account Number on Page is not identified correctly..!!", Status_CRAFT.FAIL);
                    }
                }
                return;
            } else {
                report.updateTestLog("verifySetPaperOnAfterChangeMobile", "No Account found on 'Turn paper statements On/Off' Page..!", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifySetPaperOnAfterChangeMobile", "Page 'Turn paper statements On/Off' is not opened..!", Status_CRAFT.FAIL);
        }
    }

    /**
     * Mobile App Automation Method : To Verify and Get Account Having Paper ON
     * Update on     Updated by     Reason
     * 27/06/2019    C966119        for mobile Automation
     */
    public void VerifyAndGetAccountHavingPaperON() throws Exception {
        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Boolean blnFoundFlag = true;
        Services.verifyAndClickServicesOption();
        if (isElementDisplayed(getObject("mw.ManageStatements.lnkTurnPaperONOFF"), 5)) {
            clickJS(getObject("mw.ManageStatements.lnkTurnPaperONOFF"), "Click Menu Option : Turn paper statements On/Off");
        } else {
            report.updateTestLog("VerifyAndGetAccountHavingPaperON", "Link 'Turn paper statements On/Off' is not visible", Status_CRAFT.FAIL);
        }
        verifySetPaperOnAfterChangeMobile();
        if (isElementDisplayed(getObject(deviceType() + "ManageStatements.lblHeaderTurnOnOff"), 5)) {
            VerifyCopyOnTurnPaperOnOff();
            report.updateTestLog("VerifyAndGetAccountHavingPaperON", "Page 'Turn paper statements On/Off' is opened successfully.", Status_CRAFT.PASS);
            List<WebElement> oPaperOnCount = driver.findElements(getObject("mw.PaperlessPromotionON"));
            if (oPaperOnCount.size() > 0) {
                for (int j = 0; j < oPaperOnCount.size(); j++) {
                    String strRowText = oPaperOnCount.get(j).getText().toUpperCase();
                    if (strRowText.length() > 4) {
                        strAccount = strRowText.substring(strRowText.length() - 4);
                        String strObj = "xpath~//label[contains(text(),'" + strAccount + "')]/../../following-sibling::div[@style='text-align: left; ']/div/div/input[@checked='checked']";
                        String strObjAcc = dataTable.getData("General_Data", "Account_Type");
                        if (isElementDisplayed(getObject(strObj), 3) && strRowText.contains(strObjAcc.toUpperCase())) {
                            report.updateTestLog("VerifyAndGetAccountHavingPaperON", "Fetched the Account Number ending with last four digits ::" + strAccount, Status_CRAFT.DONE);
                            report.updateTestLog("VerifyAndGetAccountHavingPaperON", "Account Name displayed as ::" + strRowText, Status_CRAFT.DONE);
                            NavigationToHomePage();
                            String strParentObject = "xpath~//*[@class='accessibilityStyle']/following-sibling::span[contains(@class,'boi_input boi-')][text()='" + strAccount + "']";
                            waitForPageLoaded();
                            ScrollAndClickJS(strParentObject);
                            report.updateTestLog("verifyAccountClickAndNavigate", "Clicked the Account IBAN having value with last four digit :: " + strAccount, Status_CRAFT.DONE);
                            if (isElementDisplayed(getObject("w.myAccount.sectionBlueCard"), 50)) {
                                report.updateTestLog("verifyAccountClickAndNavigate", "Blue Card Drill Down Page Opened Successfully.", Status_CRAFT.DONE);
                            } else {
                                report.updateTestLog("verifyAccountClickAndNavigate", "Blue Card Drill Down Page Did NOT Opened Successfully.", Status_CRAFT.FAIL);
                            }
                            blnFoundFlag = true;
                            break;
                        } else if (!isElementDisplayed(getObject(strObj), 3)) {
                            String strObjToClick = "xpath~//label[contains(text(),'" + strAccount + "')]/../../following-sibling::div[@style='text-align: left; ']/div/div/label[@class='onoffswitch-label']";
                            scrollToView(getObject(strObjToClick));
                            clickJS(getObject(strObjToClick), "Changing the Papeless Statement Notification from OFF to ON");
                            oPaperOnCount = driver.findElements(getObject("mw.PaperlessPromotionON"));
                            blnFoundFlag = false;
                        } else {
                            if (strRowText.contains(strObjAcc.toUpperCase())) {
                                blnFoundFlag = false;
                                report.updateTestLog("VerifyAndGetAccountHavingPaperON", "NO account Found with Paperless statement notification having ON..!", Status_CRAFT.FAIL);
                            }
                        }

                    } else {
                        report.updateTestLog("VerifyAndGetAccountHavingPaperON", "Account Number on Page is not identified correctly..!!", Status_CRAFT.FAIL);
                    }

                }
                return;
            } else {
                report.updateTestLog("VerifyAndGetAccountHavingPaperON", "No Account found on 'Turn paper statements On/Off' Page..!", Status_CRAFT.FAIL);
            }

            if (blnFoundFlag == false) {
                report.updateTestLog("VerifyAndGetAccountHavingPaperON", "No Account Found With Paper less Statements Notification ON..!!", Status_CRAFT.FAIL);
            }

        } else {
            report.updateTestLog("VerifyAndGetAccountHavingPaperON", "Page 'Turn paper statements On/Off' is not opened..!", Status_CRAFT.FAIL);
        }


    }

    public void waitForNotificationOnDesktop() throws Exception {
        if (!isMobile) {
            if (isElementDisplayed(getObject("xpath~//*[contains(text(),'You will need your smartphone or tablet for this step)]"), 5)) {
                Thread.sleep(120000);
            }
        }

    }

    public void verifyPolicyDetailsLink() throws Exception {
        String strObjAcc = dataTable.getData("General_Data", "Account_Type");
        String[] arrstrObjAcc = strObjAcc.split("::");
        for (int i = 0; i < arrstrObjAcc.length; i++) {
            String strObjToCheck = "xpath~//div[@class='boi-mobile-tab-container']/descendant::span[@class='boi-flex']/*[contains(text(),'" + arrstrObjAcc[i].split("~")[1].trim() + "')]/..";
            //div[@class='boi-mobile-tab-container']/descendant::span[@class='boi-flex'][contains(text(),'Investment Policy')]/*[contains(text(),'5511')]/..
            String strPolicyName = getText(getObject(strObjToCheck));
            if (isElementDisplayed(getObject(strObjToCheck), 5)) {
                report.updateTestLog("verifyPolicy", "Policy Name " + strObjAcc + " is displayed", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("homepage.ViewPolicyLink"), 1)) {
                    report.updateTestLog("verifyPolicyLink", "For Policy name " + strObjAcc + " View Policy link is displayed", Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("verifyPolicyLink", "For Policy name " + strObjAcc + " View Policy link is not displayed which is as expected", Status_CRAFT.PASS);
                }
            } else {
                report.updateTestLog("verifyPolicy", "Policy Name " + strPolicyName + " is not displayed ", Status_CRAFT.FAIL);
            }
        }
    }

    public void verifyAddEmailbutton() throws Exception {
        if (isElementDisplayed(getObject("ManageStatement.btnAddEmail"), 2)) {
            report.updateTestLog("verifyAddEmailButton", "Edit Email button is displayed", Status_CRAFT.PASS);
            clickJS(getObject("ManageStatement.btnAddEmail"), "Edit Email button");
            String expectedTitle = "Update Email";
            waitForPageLoaded();
            clickJS(getObject("xpath~//div[text()='Email']"), "Add Email button");
            String actualTitle = getText(getObject("ManageStatement.titleAddEmail"));
            if (actualTitle.equalsIgnoreCase(expectedTitle)) {
                report.updateTestLog("verifyAddEmailPage", "Add Email Page is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddEmailPage", "Add Email Page is not displayed", Status_CRAFT.FAIL);
            }
        } else if (isElementDisplayed(getObject("ManageStatement.lnkEditEmailAddress"), 2)) {
            report.updateTestLog("verifyAddEmailPage", "Add Email button is not displayed,since email is already registered", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyAddEmailButton", "Add Email button is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void verifyElementNotDisplayed() throws Exception {
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = "xpath~//*[@class='accessibilityStyle']/following-sibling::span[contains(@class,'boi_input boi-')][text()='" + strAccountIBAN + "']";
        ScrollAndClickJS(strParentObject);
        waitForPageLoaded();
        report.updateTestLog("verifyAccountClickAndNavigate", "Clicked the Account IBAN having value with last four digit :: " + strAccountIBAN, Status_CRAFT.PASS);
        waitForPageLoaded();
        if (isElementDisplayed(getObject("w.myAccount.sectionBlueCard"), 5)) {
            report.updateTestLog("verifyAccountClickAndNavigate", "Blue Card Drill Down Page Opened Successfully.", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "home.moreOption"), "More Option");
            if (isElementDisplayed(getObject(deviceType() + "services.ManagestatementsText"), 5)) {
                report.updateTestLog("verifyManageStatementHeader", "Manage Statements header is present", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyManageStatementHeader", "Manage Statements header is not present which is as expected", Status_CRAFT.PASS);
            }
            if (isElementDisplayed(getObject("ManageStatements.lnkOrderUpToDate"), 5)) {
                report.updateTestLog("verify Order an up-to-date statement", "Order an up-to-date statement tab is present", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verify Order an up-to-date statement", "Order an up-to-date statement tab is not present which is as expected", Status_CRAFT.PASS);
            }
            if (isElementDisplayed(getObject("mw.ManageStatements.lnkTurnPaperONOFF"), 5)) {
                report.updateTestLog("verify Turn paper statements On/Off", "Turn paper statements On/Off tab is present", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verify Turn paper statements On/Off", "Turn paper statements On/Off tab is not present which is as expected", Status_CRAFT.PASS);
            }
            if (isElementDisplayed(getObject("ManageStatements.lnkStmntNotification"), 5)) {
                report.updateTestLog("verify Statement notifications", "Statement notifications tab is present", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verify Statement notifications", "Statement notifications tab is not present which is as expected", Status_CRAFT.PASS);
            }
            if (isElementDisplayed(getObject(deviceType() + "backarrow"), 5)) {
                report.updateTestLog("verifyBackArrow", "Back Navigation arrow present on the screen", Status_CRAFT.PASS);
                clickJS(getObject(deviceType() + "backarrow"), "Back navigation");
                waitForPageLoaded();
            } else {
                report.updateTestLog("verifyBackArrow", "Back Navigation arrow not present on the screen", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAccountClickAndNavigate", "Blue Card Drill Down Page Did NOT Opened Successfully.", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To capture BKK accounts details
     * Created by : C966003
     * Update on    Updated by     Reason
     * 27/06/2019   C966003        Newly created
     */
    public void captureBKKAccountsDetails_Homepage() throws Exception {
        List<WebElement> lstAccounts = findElements(getObject(deviceType() + "home.lstAccountDetails"));
        for (WebElement strAccount : lstAccounts) {
            String strAccountDetails = strAccount.getText();
            scriptHelper.commonData.accountList.add(strAccountDetails);
        }
    }

    //CFPUR-7249 to check absence of Manage Accounts link in different types of accounts

    public void clkAccTypeAndValidateAccount() throws Exception {
        boolean bflag = false;
        String accType = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = deviceType() + "home.parentaccount_new";

        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyCheckContentNotPresent", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        ScrollToVisibleJS(strParentObject);

        // && (strRowText.contains(strAccountIBAN.toUpperCase()))
        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText1 = oUIRows.get(j).getText().toUpperCase();
            String strRowText2 = strRowText1.split("\n")[0] + strRowText1.split(" \n")[1];
            String strRowText = strRowText2.split("\n")[0];

            if ((strRowText.contains(accType.toUpperCase()))) {
                //ScrollToVisibleJS(strParentObject);;
                //oUIRows.get(j).click();
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                //Thread.sleep(3000);
                JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                executor.executeScript("arguments[0].click();", oUIRows.get(j));
                Thread.sleep(3000);
                report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                bflag = true;
                break;
            }
        }
    }


    /*
    Function: To verify the More section is appearing in order (CFPUR-7045)
     */
    public void verifyMoreSection() throws Exception {

        waitForPageLoaded();
        if (isMobile) {
            driver.hideKeyboard();
        }
        if (isElementDisplayed(getObject("homepage.footerlinkmore"), 5)) {
            clickJS(getObject("homepage.footerlinkmore"), "More link");
            String strMorelist = dataTable.getData("General_Data", "VerifyDetails");
            String[] arrValidationList = strMorelist.split(";");
            //To validate the list in the More section in the perticular order
            for (int i = 0; i < arrValidationList.length; i++) {
                int j = i + 2;
                String strActListValue = getText(getObject("xpath~(//*[contains(@class,'ecDIB  boi-full-width')][not(contains(@style,'text-align: left; display: none;  display: none;'))])[" + j + "]"));
                if (arrValidationList[i].equals(strActListValue)) {
                    report.updateTestLog("verifyMoreSection", "Option in the list is appearing as per order, Expected:'" + arrValidationList[i] + "', Actual: '" + strActListValue + "'", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("verifyMoreSection", "Option in the list is not appearing as per order, Expected:'" + arrValidationList[i] + "', Actual: '" + strActListValue + "'", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("verifyMoreSection", "More link is not displayed on Homepage", Status_CRAFT.FAIL);

        }
    }


    public void managealertslinkNotPresent() throws Exception {
        String accType = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        if (isElementDisplayed(getObject(deviceType() + "home.moreOption"), 5)) {
            clickJS(getObject(deviceType() + "home.moreOption"), "More Option Is clicked");
            Thread.sleep(1000);
            report.updateTestLog("verifyaccountsManageAlert", "More option is displayed", Status_CRAFT.PASS);
            if (!isElementDisplayed(getObject(deviceType() + "accounts.ManageAlerts"), 10)) {
                report.updateTestLog("verifyaccountsManageAlert", "Manage Alerts link not present on '" + accType + "' account detail page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyaccountsManageAlert", "Manage Alerts link present on  '" + accType + "' saccount detail page", Status_CRAFT.FAIL);
            }
            Thread.sleep(2000);

        } else {
            report.updateTestLog("verifyaccountsManageAlert", "More option is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void majorminorAlert() throws Exception {

        String majorAlertTitle = getText(getObject(deviceType() + "home.MajorAlert"));
        String minorAlertTitle = getText(getObject(deviceType() + "home.MinorAlert"));
        if (isElementDisplayed(getObject(deviceType() + "home.MajorAlert"), 5)) {
            report.updateTestLog("verifymajorAlert", "MajorAlert" + majorAlertTitle + " is displayed on login Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifymajorAlert", "MajorAlert" + majorAlertTitle + " NOT displayed on login Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "home.MinorAlert"), 5)) {
            report.updateTestLog("verifyminorAlert", "MinorAlert" + minorAlertTitle + " is displayed on login Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyminorAlert", "MinorAlert" + minorAlertTitle + " NOT displayed on login Page", Status_CRAFT.FAIL);
        }

    }

    //CFPUR-1365
    public void VerifyUnavailableBKKAccount() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = deviceType() + "home.parentaccount_new";
        String strStatustoCheck = dataTable.getData("General_Data", "Current_Balance");

        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyNavigateToAccountPageAnyAccount", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);

        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();

            if ((strRowText.contains(strAccountToCheck.toUpperCase())) && (strRowText.contains(strAccountIBAN.toUpperCase()))) {
                String ExpStatus = getText(getObject("xpath~//div[@id='C5__COL_8281DD706B18287A191315_R" + (j + 1) + "']"));
                if (strStatustoCheck.toUpperCase().equalsIgnoreCase(ExpStatus.toUpperCase())) {
                    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                    js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                    JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                    if (isElementclickable(getObject("xpath~//div[@id='C5__COL_8281DD706B18287A191315_R" + (j + 1) + "']"), 5)) {
                        report.updateTestLog("verifyUnavailableBKKAccount", "Account tile is clickable.", Status_CRAFT.FAIL);
                    } else {
                        report.updateTestLog("verifyUnavailableBKKAccount", "Account tile is NOT clickable.", Status_CRAFT.PASS);

                    }

                    report.updateTestLog("verifyUnavailableBKKAccount", "The BKK Account with 'Unavailable/Invalid' status displayed successfully.", Status_CRAFT.PASS);

                } else {
                    report.updateTestLog("verifyUnavailableBKKAccount", "The BKK Account with 'Unavailable' status NOT displayed.", Status_CRAFT.FAIL);

                }

                // System.out.println(strRowText);

            }
        }
    }

    //CFPUR-6218_5766
    public void VerifyUnavaiInvalBKKAccountBluecard() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strParentObject = deviceType() + "myAccount.AccountList";
        String strStatustoCheck = dataTable.getData("General_Data", "Current_Balance");

        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyNavigateToAccountPageAnyAccount", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);

        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();
            System.out.println(strRowText);
            scrollToView(getObject("xpath~//*[text()='" + strRowText + "']"));
            if ((strRowText.contains(strStatustoCheck.toUpperCase()))) {
                report.updateTestLog("VerifyUnavaiInvalBKKAccountBluecard", "Unavailable/Invalid BKK/Credit Card Accounts are displayed.", Status_CRAFT.FAIL);

            } else {
                report.updateTestLog("VerifyUnavaiInvalBKKAccountBluecard", "Unavailable/Invalid BKK/Credit Card Accounts are NOT displayed.", Status_CRAFT.PASS);

            }
        }
    }

    /**
     * Function : Function to Verify My Overview section removed
     * Update on    Updated by     Reason
     * 16/07/2019   C966119        created new code
     */
    public void verifyNoMyOverview() {
        if (isElementNotDisplayed(getObject("home.lblMyOverview"), 2)) {
            report.updateTestLog("verifyNoMyOverview", " Header :: 'My Overview' :: is not present on home page ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyNoMyOverview", " Header :: 'My Overview' :: is present on home page ", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Function to select registered device from Device list (SCA)
     * Created by : C966003
     * Update on    Updated by     Reason
     * 16/07/2019   C966003        created new code
     */

    public void verifySelectRegisterDevice() throws Exception {
        String strRegisteredDevice = dataTable.getData("Login_Data", "DeviceName");
        String strDeviceName = "";
        int strPageCount = 0;
        boolean deviceFlag = false;
        if (isElementDisplayed(getObject("launch.approvalNotificationPage"), 3)) {
            report.updateTestLog("verifySelectRegisterDevice", " Waiting for your approval page is displayed..As Only One Device is registered for this user", Status_CRAFT.DONE);
            waitForPageLoaded();
            if (isElementDisplayed(getObject("launch.approvalNotificationPage"), 3)) {
                verifyPushIsNotAccepted();
            }
            return;
        }
        if (isElementDisplayed(getObject("launch.deviceListPage"), 5)) {
           // if (getText(getObject("xpath~//div[contains(text(),'Page 1 of ')]")).equalsIgnoreCase("Page 1 of")) {
           //Sindhuja - if more than one page,logic to calculate the page count
        	if (getText(getObject("launch.deviceListPageCount")).contains("Page 1 of")) {
                strPageCount = Integer.parseInt((getText(getObject("launch.deviceListPageCount")).substring(10, 11)).trim());
        	}
        	else if (getText(getObject("launch.deviceListPageCount")).substring(10, 11).trim().length() > 2) {       		
                strPageCount = Integer.parseInt((getText(getObject("launch.deviceListPageCount")).substring(10, 12)).trim()); }
            else {
            	 report.updateTestLog("verifyPageCount", " Page count is not displayed", Status_CRAFT.FAIL);	
        	}
        
            waitForPageLoaded();
            waitForElementPresent(getObject("launch.lstRegisteredDevices"), 5);
            for (int i = 1; i <= strPageCount; i++) {
//            	waitForPageLoaded();
                waitForElementPresent(getObject("launch.lstRegisteredDevices"), 5);
                List<WebElement> lstDevices = findElements(getObject("launch.lstRegisteredDevices"));
                for (WebElement device : lstDevices ) {
                 for (int k = 0; k <= 2; k++) {
                     try {
	                    strDeviceName = device.getText();
	                    break;
                     } catch (StaleElementReferenceException e) {
                         e.printStackTrace();
                     }
                 }
                    //Sindhuja - to select the exact the device present in application with datasheet
                    if (strDeviceName.equals(strRegisteredDevice)) {
                        for (int j = 0; j <= 2; j++) {
                            try {
                            	device.click(); 
                                deviceFlag = true;
                                break;
                            } catch (StaleElementReferenceException e) {
                                e.printStackTrace();
                            }
                        }
//                       Sindhuja - if the device name is present break the loop
                        if(deviceFlag) {
                         report.updateTestLog("verifySelectRegisterDevice", " Device with name '" + strRegisteredDevice + "' selected on Device selection page ", Status_CRAFT.PASS);
                         break;
                     }
                    }
                                   
                }
                //Sindhuja - if the device name is present break the loop
                if(deviceFlag) {
                 report.updateTestLog("verifySelectRegisterDevice", " Device with name '" + strRegisteredDevice + "' selected on Device selection page ", Status_CRAFT.PASS);
                 break; }
                //Sindhuja - if the device is not present in page 1 logic extended to handle 'Next' page click.
                if (!deviceFlag && i==strPageCount) {
                    report.updateTestLog("verifySelectRegisterDevice", " Device with name '" + strRegisteredDevice + "' is not present in this page. Click Next to verify' ", Status_CRAFT.PASS);
                    click(getObject("launch.btnPageNext"));
                    deviceFlag = selectdevice(strRegisteredDevice);
                    
                }
                
            }
          
	            if (!deviceFlag) {
	              report.updateTestLog("verifySelectRegisterDevice", " Device with name '" + strRegisteredDevice + "' is NOT present on Device selection page, ::Exiting test script execution", Status_CRAFT.FAIL);         
	              driver.quit();
	            } 
            	else{
                waitForPageLoaded();
                for (int j = 0; j <= 2; j++) {
                    try {
                        clickJS(getObject("launch.btnChooseDevices"), "Choose Device");
                        break;
                    } catch (StaleElementReferenceException e) {
                        e.printStackTrace();
                    }
                }
                waitForPageLoaded();
            }
            
            if (isElementDisplayed(getObject("launch.approvalNotificationPage"), 3)) {
                verifyPushIsNotAccepted();
            }
            
        }
    }


    public void notificationGIF() throws Exception {
        if (isElementDisplayed(getObject("launch.approvalNotificationPage"), 1)) {
            report.updateTestLog("notificationGIF", "Waiting for your approval is displayed on the Page", Status_CRAFT.PASS);
            /*if(getText(getObject("xpath~//span[text()='to your registered device']")).equals("to your registered device")){
                report.updateTestLog("notificationGIF", "Sending notification to a unknown device for simulated journey", Status_CRAFT.PASS);
            }
            else{
                report.updateTestLog("notificationGIF", "Sending notification to a unknown device for simulated journey is not as expected", Status_CRAFT.FAIL);
            }*/
        } else {
            report.updateTestLog("notificationGIF", "notificationGIF is not displayed on the Page", Status_CRAFT.PASS);
        }

    }

    public void errorSimulatedJourney() throws Exception {
        if (isElementDisplayed(getObject("xpath~//*[text()='Your login details are incorrect, please try again.']"), 2)) {
            report.updateTestLog("errorSimulatedJourney", "errorSimulatedJourney is displayed on the Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("errorSimulatedJourney", "errorSimulatedJourney is not displayed on the Page", Status_CRAFT.PASS);
        }

    }

    /**
     * Function : CFPUR-667 validate devices
     * Update on     Updated by    Reason
     * 06/02/2020    C966119
     */
    public void validateActiveBlockDevices() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "profileIcon"), 3)) {
            clickJS(getObject(deviceType() + "profileIcon"), "Profile Icon");
            if (isElementDisplayed(getObject(deviceType() + "txtprofile"), 3)) {
                if (deviceType().equals("mw.")) {
                    clickJS(getObject(deviceType() + "home.SecurityDevices"), "Security Device");
                }
                report.updateTestLog("verifyprofileclick", "click on profile successfully", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("verifyprofileclick", "Profile Icon not clicked", Status_CRAFT.FAIL);
        }
    }


    public void activeDeviceSelection() throws Exception {

        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        for (int i = 1; i < allLinks.size(); i++) {
           /* if (isElementDisplayed(getObject(deviceType() + "txtmysecuritydevices"), 3)) {
                clickJS(getObject(deviceType() + "txtmysecuritydevices"),"Security Device");
                //click(getObject(deviceType() + "txtmysecuritydevices"));
                report.updateTestLog("My security device","My security device is CLICKED" , Status_CRAFT.PASS);
            }
            else
                {
                    report.updateTestLog("My security device","My security device NOT CLICKED" , Status_CRAFT.FAIL);
                }
*/
            String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
            ScrollToVisibleJS(activexpath);
            if (isElementDisplayed(getObject(activexpath), 3)) {

                if (getText(getObject(activexpath)).contains("Active")) {
                    clickJS(getObject(activexpath), "Security Device");
                    //click(getObject(activexpath));
                    report.updateTestLog("VerifyActivedevices", "Active Devices No." + i + "is CLICKED", Status_CRAFT.PASS);
                   /* if (isElementDisplayed(getObject(deviceType() + "txtblockdevices"), 3)) {
                        report.updateTestLog("VerifyUnblockdevices", "block Device button is present", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyUnblockdevices", "block Device NOT present", Status_CRAFT.FAIL);
                    }*/
                    Thread.sleep(2000);

                    //click(getObject(deviceType() + "titleGoback"));
                    clickJS(getObject(deviceType() + "titleGoback"), "Go Back");
                    Thread.sleep(2000);
                } else {
                    click(getObject(activexpath));
                    report.updateTestLog("Verifyblockdevices", "BLOCK Device  is CLICKED", Status_CRAFT.PASS);

                    if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevices"), 3)) {
                        report.updateTestLog("VerifyUnblockdevices", "Unblock Device button is present", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyUnblockdevices", "Unblock Device NOT present", Status_CRAFT.FAIL);
                    }
                    if (isElementDisplayed(getObject(deviceType() + "txtRemovekdevices"), 3)) {
                        report.updateTestLog("VerifyRemovedevices", "Remove Device button is present", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyRemovedevices", "Remove Device NOT present", Status_CRAFT.FAIL);
                    }
                }

            }
        }
    }


    //Function to select device from device list

    public void selectDeviceListSCA() throws Exception {
        String strdevice = dataTable.getData("General_Data", "Relationship_Status");

        if (isElementDisplayed(getObject("launch.deviceListPage"), 5)) {
            report.updateTestLog("DeviceListPage", " Choose security device page is displayed", Status_CRAFT.PASS);


            if (isElementDisplayed(getObject("Devicelist.Next"), 5)) {
                report.updateTestLog("selectDeviceListSCA", "Next button is displayed", Status_CRAFT.PASS);

                while (!isElementDisplayed(getObject("xpath~//span[text()='" + strdevice + "']"), 3)) {

                    clickJS(getObject("Devicelist.Next"), "Next button");
                }

            }
            Thread.sleep(1000);

            clickJS(getObject((("xpath~//span[text()='" + strdevice + "']"))), "Device");
            report.updateTestLog("DeviceListPage", " Device selected from list successfully", Status_CRAFT.PASS);
            clickJS(getObject("launch.btnChooseDevices"), "Choose Devices");
            Thread.sleep(120000);
        } else {
            report.updateTestLog("DeviceListPage", " Device is not selected/present ", Status_CRAFT.DONE);
        }

    }

    //CFPUR-3442 to check absence of Manage Accounts link in different types of accounts

    public void Registerdevicenavigation() throws Exception {
        clickJS(getObject("Hardtoken.registerdevice"), "Register device");
        if (isElementDisplayed(getObject("Registerdevice.header"), 5)) {
            report.updateTestLog("Register Device Header", "Register Device page is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Register Device page", "Navigation incorrect", Status_CRAFT.FAIL);
        }
    }

    public void ReadNewUnreadMessage() throws Exception {

        boolean blnClicked;

        if (isElementDisplayed(getObject(deviceType() + "Home.XpathMessage"), 5)) {
            clickJS(getObject(deviceType() + "Home.XpathMessage"), "navigate to message");
            Thread.sleep(2000);
            report.updateTestLog("verifyMesasageindicator", "Unread messages are present and indicator symbol is displayed", Status_CRAFT.PASS);
                /*String messageindivalue = getText(getObject("xpath~//button[text()='Messages ']/span[@aria-hidden='true']"));
                System.out.println(messageindivalue);
                String updateindicator = messageindivalue.substring(1,3);
                int oldcount = Integer.parseInt(updateindicator);*/
            List<WebElement> mailcount = driver.findElements(By.xpath("//div[contains(@id,'C5__TXT_116D03C0E6B21296386922_R')]/span[@class='fa fa-icon  fa-envelope boi_blue  boi-fs-24']"));
            for (int i = 0; i < mailcount.size(); i++) {
                List<WebElement> mailcount1 = driver.findElements(By.xpath("//div[contains(@id,'C5__TXT_116D03C0E6B21296386922_R')]/span[@class='fa fa-icon  fa-envelope boi_blue  boi-fs-24']"));


                //clickJS(getObject("xpath~//div[contains(@id,'C5__COL_116D03C0E6B21296386887_R"+i+"')]/descendant::span[@class='fa fa-icon  fa-envelope boi_blue  boi-fs-24']"), "click on unread msg");
                //  mailcount1.get(0).click();
                //mailcount1.get(0).g
                //clickJS(mailcount1.get(0));
                ////////////////////
                try {

                    //WebElement element = driver.findElement(locator);
                    JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                    executor.executeScript("arguments[0].click();", mailcount1.get(0));
                } catch (UnreachableBrowserException e) {
                    e.printStackTrace();
                } catch (StaleElementReferenceException e) {
                    e.printStackTrace();
                }
                blnClicked = true;
                Thread.sleep(2000);
                clickJS(getObject(deviceType() + "SL.BacktoInbox"), "Back to Inbox");

            }
            ////div[@class='boi-card boi-card--bg boi-card__link-wrap boi-text-align-left']

        }

        if (isElementDisplayed(getObject(deviceType() + "NavigateToHomeXpath"), 5)) {
            clickJS(getObject(deviceType() + "NavigateToHomeXpath"), "navigate to Home");
            Thread.sleep(2000);
            report.updateTestLog("Navigate back to Home", "Navigation to Home page ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Navigate back to Home", "Navigation to Home page", Status_CRAFT.FAIL);
        }

        if (!isElementDisplayed(getObject(deviceType() + "Home.XpathMessage"), 5)) {
            report.updateTestLog("verifyMesasageindicator", "No unread message is displayed", Status_CRAFT.PASS);
        }
    }


    public void NavigateBackToHomePage() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "NavigateToHomeXpath"), 5)) {
            clickJS(getObject(deviceType() + "NavigateToHomeXpath"), "navigate to Home");
            Thread.sleep(2000);
            report.updateTestLog("Navigate back to Home", "Navigation to Home page ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Navigate back to Home", "Navigation to Home page", Status_CRAFT.FAIL);
        }
    }

    public void VerifyTextOnHomePage() throws Exception {

        String MessageCountText = "";
        if ((isElementDisplayed(getObject("Home.XpathMessage"), 10))) { // add device type

            MessageCountText = getText(getObject("Home.XpathMessage"));// add device type

            report.updateTestLog("verifyUnreadMessageCount", "Message Count text is correctly displayed as:" + MessageCountText, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyUnreadMessageCount", "Message Count text is correctly displayed as:", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "XpathNewMessage"), 5)) {
            clickJS(getObject(deviceType() + "XpathNewMessage"), "Click New message tab");
            Thread.sleep(2000);
            report.updateTestLog("ReadNewUnreadMessage", "Unread message open", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("ReadNewUnreadMessage", "Unread message open", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "Home.XpathMessage"), 5)) {
            Thread.sleep(2000);

            String RevisedMessageCountText = getText(getObject("Home.XpathMessage"));
            if (!RevisedMessageCountText.equalsIgnoreCase(MessageCountText)) {

                report.updateTestLog("VerifyTextOnHomePage", "The count of message check ", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyTextOnHomePage", "The count of message check ", Status_CRAFT.FAIL);
            }


        }
    }

    public void clickmore() throws Exception {
        waitForPageLoaded();
        if (deviceType().equals("mw.") || deviceType().equals("tw.")) {
            waitForElementToClickable(getObject("home.lnkPreLgnMore"), 7);
            report.updateTestLog("clickmore", " Screenshot ", Status_CRAFT.SCREENSHOT);
            if (isElementDisplayed(getObject("home.lnkPreLgnMore"), 3)) {
                waitForElementPresent(getObject("home.lnkPreLgnMore"), 1);
                clickJS(getObject("home.lnkPreLgnMore"), "More button is clicked");
                report.updateTestLog("clickmore", " More button is clicked from footer", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("clickmore", " More button is not clicked from footer", Status_CRAFT.FAIL);
            }
        } else if (deviceType().equals("w.") & isElementNotDisplayed(getObject("home.lnkPreLgnMore"), 3)) {
            report.updateTestLog("clickmore", " More button is not an available on desktop footer Which is expected", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("clickmore", " Please check the device type !!", Status_CRAFT.FAIL);
        }

    }


    public void VerifyMessageOnAwayToHomeScreen() throws Exception {
        String MessageCountText = "";
        if ((isElementDisplayed(getObject(deviceType() + "Home.XpathMessage"), 5))) { // add device type

            MessageCountText = getText(getObject(deviceType() + "Home.XpathMessage"), 5);// add device type

            report.updateTestLog("verifyUnreadMessageCount", "Message Count text is correctly displayed as:" + MessageCountText, Status_CRAFT.PASS);
        } else if (isElementDisplayed(getObject(deviceType() + "home.AdvertUpdateEmail"), 1)) {
            report.updateTestLog("verifyUnreadMessageCount", "Message Count text is not displayed. Update Email Advert is present. Prerequisite for test does not meet", Status_CRAFT.WARNING);
            return;
        } else {
            report.updateTestLog("verifyUnreadMessageCount", "Message Count text is not displayed/found", Status_CRAFT.FAIL);
            return;
        }

        if ((isElementDisplayed(getObject(deviceType() + "PaymentTabXpath"), 5))) {
            clickJS(getObject(deviceType() + "PaymentTabXpath"), "navigation to payment tab");
            clickJS(getObject(deviceType() + "NavigateToHomeXpath"), "navigation to Home page");
        }

        //clickJS(getObject(deviceType() + "NavigateToHomeXpath"), "navigate to Home");

        if (isElementDisplayed(getObject(deviceType() + "Home.XpathMessage"), 5)) {
            Thread.sleep(2000);

            String RevisedMessageCountText = getText(getObject(deviceType() + "Home.XpathMessage"), 5);
            if (RevisedMessageCountText.equalsIgnoreCase(MessageCountText)) {

                report.updateTestLog("VerifyTextOnHomePage", "The count of message check ", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyTextOnHomePage", "The count of message check ", Status_CRAFT.FAIL);
            }


        }

    }

    public void verifyNoElementOnHomepage() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.welcomeHeadline"), 5)) {
            report.updateTestLog("VerifyTextOnHomePage", "Navigated to homepage successfully ", Status_CRAFT.PASS);
            if (isElementNotDisplayed(getObject(deviceType() + "Home.XpathMessage"), 5)) {

                report.updateTestLog("VerifyTextOnHomePage", "The count of message check ", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyTextOnHomePage", "The count of message check ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("VerifyTextOnHomePage", "Navigated to homepage successfully ", Status_CRAFT.FAIL);

        }
    }


    public void VerifyAllUnreadMessageIsOpened() throws Exception {
        WebElement UnreadMail = driver.findElement(By.xpath("//div[@class='ecDIBCol  ecDIB  boi-message__column-content--left']/div/div/div/span[2][text()='RE: [RefA9040100068803]Credit card']/../../../../../../descendant::div[@class='ecDIBCol  ecDIB  boi-message__column-icon boi-pr-15']/div/span[@class='fa fa-icon  fa-envelope-open  boi-fs-24']"));

        if (UnreadMail.isDisplayed()) {

            report.updateTestLog("verifyinboxMsg", "Mail icon is changes succesfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyinboxMsg", "Mail icon does not change correctly", Status_CRAFT.FAIL);
        }


    }

    public void verifylogoutexception() throws Exception {

        if (isElementDisplayed(getObject("home.logoutkey"), 20)) {
            clickJS(getObject("home.logoutkey"), "Logout");
            Thread.sleep(2000);
            clickJS(getObject("home.logoutpopupbutton"), "Logout");
            Thread.sleep(2000);
        }

        if (isElementDisplayed(getObject("home.logedouttext"), 20)) {

            report.updateTestLog("verifylogoutexception", "User is successfully Loged out ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifylogoutexception", "Logout Failed", Status_CRAFT.FAIL);
        }

        //Applying wait for 5 mins
        Thread.sleep(300000);
        clickJS(getObject("home.logoutpagelogin"), "Login");

        //verifying exception page
        if (isElementDisplayed(getObject("home.exceptionpagetext"), 20)) {
            clickJS(getObject("home.exceptionpagelogin"), "Login");
            Thread.sleep(3000);
            if (isElementDisplayed(getObject("launch.edtUserName"), 20)) {

                report.updateTestLog("verifylogoutexception", "Exceptional error is not displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifylogoutexception", "Navigation incorrect", Status_CRAFT.FAIL);
            }


        }
    }
    //CFPUR-7249 to check absence of Manage Accounts link in different types of accounts

    public void verifyaccountsManageAlert() throws Exception {
        String strAccount = dataTable.getData("General_Data", "Account_Name");
        String[] arrAccounts = strAccount.split(";");

        for (int i = 0; i < arrAccounts.length; i++) {
            String strAccountName = arrAccounts[i].split("::")[0];

            List<WebElement> allLinks = findElements(getObject("home.accountlist"));
            if (allLinks.size() > 1) {
                int intIndex = 1;
                for (WebElement ele : allLinks) {
                    String linkname = ele.getText();
                    if (linkname.equalsIgnoreCase(strAccountName)) {
                        ele.click();
                        intIndex = intIndex;
                        report.updateTestLog("ReactivateAllDirectDebitDetails", strAccountName + " is clicked  ", Status_CRAFT.PASS);
                        Thread.sleep(2000);
                        if (!isElementDisplayed(getObject("accounts.ManageAlerts"), 10)) {
                            report.updateTestLog("verifyaccountsManageAlert", "Manage Alerts link not present on '" + strAccountName + "' account detail page", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("verifyaccountsManageAlert", "Manage Alerts link present on  '" + strAccountName + "' saccount detail page", Status_CRAFT.FAIL);
                        }
                        clickJS(getObject("home.accountstab"), "Accounts Tab");
                        Thread.sleep(2000);
                        break;
                    }
                    intIndex = intIndex++;

                }
            }

        }

    }

    // CFPUR-7987
    // To verify user is navigated to Login screen after clicking after waiting 5 mins on Logout screen

    public void verifyloginredirect() throws Exception {

        if (isElementDisplayed(getObject("home.logoutkey"), 20)) {
            clickJS(getObject("home.logoutkey"), "Logout");
            Thread.sleep(2000);
            clickJS(getObject("home.logoutpopupbutton"), "Logout");
            Thread.sleep(2000);
        }

        if (isElementDisplayed(getObject("home.logedouttext"), 20)) {

            report.updateTestLog("verifyloginredirect", "User is successfully Loged out ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyloginredirect", "Logout Failed", Status_CRAFT.FAIL);
        }

        //Applying wait for 5 mins
        Thread.sleep(300000);
        click(getObject("home.logoutpagelogin"), "Login");
        Thread.sleep(2000);
        //verifying exception page
        if (isElementDisplayed(getObject("home.exceptionpagetext"), 20)) {
            clickJS(getObject("home.exceptionpagelogin"), "Login");
            Thread.sleep(3000);

            if (isElementDisplayed(getObject("home.welcomePage"), 20)) {

                report.updateTestLog("verifyloginredirect", "User is correctly navigated to Welcome page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyloginredirect", "Navigation incorrect", Status_CRAFT.FAIL);
            }


        }
    }

    // CFPUR-7517
    // To verify advertising deeplinks

    public void verifyAdvertisingBox() throws Exception {

        String strAdvertText = dataTable.getData("General_Data", "Relationship_Status");
        //To verify if advert is displayed
        if (isElementDisplayed(getObject("home.advertbox"), 20)) {

            report.updateTestLog("verifyAdvertisingLinks", "White square advert is displayed over my accounts", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAdvertisingLinks", "White square advert not displayed", Status_CRAFT.FAIL);
        }

        //To verfiy advert main text

        if ((isElementDisplayed(getObject("home.advertText"), 10))
                && ((getText(getObject("home.advertText")).equalsIgnoreCase(strAdvertText)))) {
            report.updateTestLog("verifyAdvertisingBox", "Advert text is correctly displayed as: " + strAdvertText, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAdvertisingBox", "Advert text is incorrect", Status_CRAFT.FAIL);
        }

    }

    /**
     * Function to veify click on ATM Locator
     */
    public void clickATMLocation() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.lnkPreLgnFindATM"), 5)) {
            report.updateTestLog("ATMLocator", "Find ATM/Branch' is displayed", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "home.lnkPreLgnFindATM"), "Click on Find ATM/Branch");
            if (isElementDisplayed(getObject(deviceType() + "home.txtATMBranch"), 5)) {
                String txtAtm = getText(getObject(deviceType() + "home.txtATMBranch"), 5);
                if (txtAtm.equals("ATM / Branch Locator")) {
                    report.updateTestLog("ATMLocator", "Find ATM/Branch' is clicked successfully", Status_CRAFT.PASS);
                    driver.close();
                } else {
                    report.updateTestLog("ATMLocator", "Find ATM/Branch' NOT clicked", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("ATMLocator", "ATM / Branch Locator' is NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("ATMLocator", "Find ATM/Branch' is NOT displayed", Status_CRAFT.FAIL);
        }
    }


    public void selectDeepLink() throws Exception {

        String strLink = dataTable.getData("General_Data", "Nickname");//add the link name to be clicked in data sheet

        List<WebElement> allLinks = findElements(getObject("home.deeplinks"));
        if (allLinks.size() > 1) {
            int intIndex = 1;
            for (WebElement ele : allLinks) {
                String linkname = ele.getText();
                if (linkname.equalsIgnoreCase(strLink)) {
                    ele.click();
                    intIndex = intIndex;
                    report.updateTestLog("selectDeepLink", strLink + " is clicked  ", Status_CRAFT.PASS);
                    break;
                }
                intIndex = intIndex++;

            }
        }
        Thread.sleep(2000);
    }

    public void handlingDeepLinkbackNavigation() throws Exception {
        if (isElementDisplayed(getObject("home.Goback"), 20)) {
            clickJS(getObject("home.Goback"), "Go Back link");
            Thread.sleep(2000);
            report.updateTestLog("handlingDeepLinkbackNavigation", "Navigated back to parent page ", Status_CRAFT.PASS);
        } else {
            if (isElementDisplayed(getObject("home.Accountslabel"), 20)) {
                clickJS(getObject("home.Accountslabel"), "Accounts label");
                Thread.sleep(2000);
                report.updateTestLog("handlingDeepLinkbackNavigation", "Navigated back to Home page ", Status_CRAFT.PASS);
            }
        }

    }

    // Function to verify hardtoken location
    public void hardtoken() throws Exception {
        int softtokenY = 0;
        int hardtokenY = 0;
        String obj = "xpath~//div[contains(@class,'boi-tile-standard-rectangle boi-tile-height-67 ')]";
        String softtokenobj = "xpath~//div[contains(@class,'boi-tile-standard-rectangle boi-tile-height-67 ')]/descendant::div[@style='text-align: left; ']/div/img[@src='images/BOI/profile/mobile-active.svg']";
        String hardtokendeviceobj = "xpath~//div[contains(@class,'boi-tile-standard-rectangle boi-tile-height-67 ')]/descendant::div[@style='text-align: left; ']/div/img[@src='images/BOI/profile/hard-token.svg']";


        List<WebElement> softtokenindex = driver.findElements(getObject(softtokenobj));
        List<WebElement> hardtokenindex = driver.findElements(getObject(hardtokendeviceobj));

        if (softtokenindex.size() > 1) {
            WebElement wel1 = softtokenindex.get(softtokenindex.size() - 1);
            softtokenY = wel1.getLocation().getY();
        }
        if (hardtokenindex.size() >= 1) {
            WebElement wel2 = hardtokenindex.get(0);
            hardtokenY = wel2.getLocation().getY();
        }
        if (softtokenY < hardtokenY) {
            report.updateTestLog("HardToken ", "HardToken is below SoftToken", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("HardToken", "HardToken is above SoftToken", Status_CRAFT.FAIL);
        }

    }

    // Function to verify hardtoken location
    public void editdevicenickname() throws Exception {
        String ncikname = dataTable.getData("General_Data", "TabName");
        String profileobj = "xpath~//span[text()='" + ncikname + "']";
        clickJS(getObject(profileobj), ncikname + "device");
        Thread.sleep(1000);
        clickJS(getObject(deviceType() + "profile.imgnickname"), "edit nickname");
        sendKeys(getObject(deviceType() + "profile.edtnickname"), dataTable.getData("General_Data", "NewNickname"), "NewNickname");
        clickJS(getObject(deviceType() + "profile.continue"), "Continue");

        if (isElementDisplayed(getObject(deviceType() + "updatednickname"), 3)) {
            report.updateTestLog("VerifyNickname", "Nickname" + ncikname + "is Updated", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyNickname", "Nickname NOT Updated", Status_CRAFT.FAIL);
        }
        if (deviceType().equals("w.")) {
            String Account = "xpath~//span[text()='ACCOUNTS']";
            clickJS(getObject(Account), "Account");
            Thread.sleep(1000);
        }
  /*   String profileobj1 = "xpath~//span[text()='"+dataTable.getData("General_Data", "NewNickname")+"']";
     clickJS(getObject(profileobj1),dataTable.getData("General_Data", "NewNickname")+"device");
     Thread.sleep(1000);
     clickJS(getObject(deviceType() +"profile.imgnickname"),"edit nickname");
     sendKeys(getObject(deviceType() +"profile.edtnickname"), dataTable.getData("General_Data", "TabName"), "changeNewNickname");
     click(getObject(deviceType() +"profile.continue"));*/

    }

    public void editDeviceNameBackToOriginal() throws Exception {

        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        for (int i = 1; i < allLinks.size(); i++) {
            String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
            if (isElementDisplayed(getObject(activexpath), 3)) {

                if (((getText(getObject(activexpath)).contains("Active")) || (getText(getObject(activexpath)).contains("(Active)"))) && (getText(getObject(activexpath)).contains("(Now being used)"))) {
                    clickJS(getObject(activexpath), "Current Security Device");
                    String status = getText(getObject(deviceType() + "StatusDevice"));
                    if (isElementDisplayed(getObject(deviceType() + "StatusDevice"), 3)) {
                        report.updateTestLog("VerifyStatus", "Device Status :: " + status + " :: is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyNickname", "Status :: " + status + "NOT displayed", Status_CRAFT.FAIL);
                    }

                    String strRegisteredDevice = dataTable.getData("Login_Data", "DeviceName");

                    if (isElementDisplayed(getObject(deviceType() + "editnickname"), 3)) {
                        clickJS(getObject(deviceType() + "editnickname"), "Edit Icon");
                    }

                    if (isElementDisplayed(getObject(deviceType() + "txtnewnickname"), 3)) {
                        sendKeys(getObject(deviceType() + "txtnewnickname"), strRegisteredDevice);
                    }
                    clickJS(getObject(deviceType() + "deviceContinue"), "Continue");

                    if (isElementDisplayed(getObject(deviceType() + "updatednickname"), 3)) {
                        report.updateTestLog("VerifyNickname", "Original Nickname ::" + strRegisteredDevice + " :: is Updated", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyNickname", "Nickname NOT Updated", Status_CRAFT.FAIL);
                    }

                    if (isElementDisplayed(getObject(deviceType() + "StatusDevice"), 3)) {
                        report.updateTestLog("VerifyStatus", "Status  :: " + status + " ::is displayed and did not changed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyNickname", "Status" + status + "NOT displayed", Status_CRAFT.FAIL);
                    }
                }
            }
        }
    }

    public void editdevice() throws Exception {

        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        for (int i = 1; i < allLinks.size(); i++) {
            String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
            if (isElementDisplayed(getObject(activexpath), 3)) {

                if (((getText(getObject(activexpath)).contains("Active")) || (getText(getObject(activexpath)).contains("(Active)"))) && (getText(getObject(activexpath)).contains("(Now being used)"))) {
                    clickJS(getObject(activexpath), "Current Security Device");

                    //Verify Status
                    String status = getText(getObject(deviceType() + "StatusDevice"));
                    if (isElementDisplayed(getObject(deviceType() + "StatusDevice"), 3)) {
                        report.updateTestLog("VerifyStatus", "Device Status :: " + status + " :: is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyNickname", "Status :: " + status + "NOT displayed", Status_CRAFT.FAIL);
                    }

                    String strNewNickName = dataTable.getData("General_Data", "Nickname");
                    //click on edit button
                    if (isElementDisplayed(getObject(deviceType() + "editnickname"), 3)) {
                        clickJS(getObject(deviceType() + "editnickname"), "Edit Icon");
                    }

                    if (isElementDisplayed(getObject(deviceType() + "txtnewnickname"), 3)) {
                        sendKeys(getObject(deviceType() + "txtnewnickname"), strNewNickName);
                    }
                    clickJS(getObject(deviceType() + "deviceContinue"), "Continue");
                    if (isElementDisplayed(getObject(deviceType() + "updatednickname"), 3)) {
                        report.updateTestLog("VerifyNickname", "Nickname ::" + strNewNickName + " :: is Updated", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyNickname", "Nickname NOT Updated", Status_CRAFT.FAIL);
                    }

                    if (isElementDisplayed(getObject(deviceType() + "StatusDevice"), 3)) {
                        report.updateTestLog("VerifyStatus", "Status  :: " + status + " ::is displayed and did not changed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyNickname", "Status" + status + "NOT displayed", Status_CRAFT.FAIL);
                    }
                }
            }
        }
    }

    public void validateupdateNickName() throws Exception {

        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        for (int i = 1; i < allLinks.size(); i++) {
            /*if (deviceType == "MobileWeb") {
                if (isElementDisplayed(getObject(deviceType() + "txtmysecuritydevices"), 3)) {
                    clickJS(getObject(deviceType() + "txtmysecuritydevices"), "Security Device");
                    //click(getObject(deviceType() + "txtmysecuritydevices"));
                    report.updateTestLog("My security device", "My security device is CLICKED", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("My security device", "My security device NOT CLICKED", Status_CRAFT.FAIL);
                }
            }*/
            String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
            if (isElementDisplayed(getObject(activexpath), 3)) {

                if (((getText(getObject(activexpath)).contains("Active")) || (getText(getObject(activexpath)).contains("(Active)"))) && (getText(getObject(activexpath)).contains("(Now being used)"))) {
                    clickJS(getObject(activexpath), "Current Security Device");

                    //Verify Status
                    String status = getText(getObject(deviceType() + "StatusDevice"));
                    if (isElementDisplayed(getObject(deviceType() + "StatusDevice"), 3)) {
                        report.updateTestLog("VerifyStatus", "Status" + status + "is displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyNickname", "Status" + status + "NOT displayed", Status_CRAFT.FAIL);
                    }

                    //Get Device Name

                    String devNickName = getText(getObject(deviceType() + "txtExistDeviceName"));
                    //click on edit button
                    if (isElementDisplayed(getObject(deviceType() + "editnickname"), 3)) {
                        clickJS(getObject(deviceType() + "editnickname"), "Edit Icon");
                    }

                    if (isElementDisplayed(getObject(deviceType() + "txtnewnickname"), 3)) {
                        sendKeys(getObject(deviceType() + "txtnewnickname"), devNickName);
                    }

                    //click on continue

                    clickJS(getObject(deviceType() + "deviceContinue"), "Continue");

                    //validate duplicate Error

                    if (isElementDisplayed(getObject(deviceType() + "duplicateNickName"), 3)) {
                        report.updateTestLog("VerifyDuplicateName", "Error" + getText(getObject(deviceType() + "duplicateNickName")) + "is displayed after enter existing device name", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyDuplicateName", "Error" + getText(getObject(deviceType() + "duplicateNickName")) + "NOT displayed", Status_CRAFT.FAIL);
                    }

                    //click on continue

                    clickJS(getObject(deviceType() + "deviceContinue"), "Continue");

                    //Validate Error when value is null

                    if (isElementDisplayed(getObject(deviceType() + "txtNickNameError"), 3)) {
                        report.updateTestLog("VerifyNicknameError", "Error" + getText(getObject(deviceType() + "txtNickNameError")) + "is displayed after enter existing device name", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyNicknameError", "Error" + getText(getObject(deviceType() + "txtNickNameError")) + "NOT displayed", Status_CRAFT.FAIL);
                    }

                    String strNewNickName = dataTable.getData("General_Data", "Nickname");

                    if (isElementDisplayed(getObject(deviceType() + "txtnewnickname"), 3)) {
                        sendKeys(getObject(deviceType() + "txtnewnickname"), strNewNickName);
                    }

                    //click on continue
                    clickJS(getObject(deviceType() + "deviceContinue"), "Continue");

                    //validate Nickname Message

                    if (isElementDisplayed(getObject(deviceType() + "updatednickname"), 3)) {
                        report.updateTestLog("VerifyNickname", " 16 digit alphanumeric Nickname" + strNewNickName + "is Updated", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyNickname", "16 digit alphanumeric Nickname NOT Updated", Status_CRAFT.FAIL);
                    }

                    if (isElementDisplayed(getObject(deviceType() + "StatusDevice"), 3)) {
                        report.updateTestLog("VerifyStatus", "Status" + status + "is displayed and did not changed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("VerifyNickname", "Status" + status + "NOT displayed", Status_CRAFT.FAIL);
                    }

                    //Validate Cancel Button

                    if (isElementDisplayed(getObject(deviceType() + "editnickname"), 3)) {
                        clickJS(getObject(deviceType() + "editnickname"), "Edit Icon");
                    }
                    if (isElementDisplayed(getObject(deviceType() + "txtnewnickname"), 3)) {
                        sendKeys(getObject(deviceType() + "txtnewnickname"), strNewNickName);
                    }

                    clickJS(getObject(deviceType() + "deviceCancel"), "Cancel");

                    if (isElementDisplayed(getObject(deviceType() + "updatednickname"), 3)) {
                        report.updateTestLog("VerifyCancel", "Cancel button NOT worked Properly", Status_CRAFT.FAIL);
                    } else {
                        report.updateTestLog("VerifyCancel", "Cancel Button worked properly", Status_CRAFT.PASS);
                    }

                    //Validate Go Back

                    if (isElementDisplayed(getObject(deviceType() + "editnickname"), 3)) {
                        clickJS(getObject(deviceType() + "editnickname"), "Edit Icon");
                    }
                    if (deviceType == "MobileWeb") {
                        clickJS(getObject(deviceType() + "FDPs.lblBack"), "Go back");

                    } else {
                        //click Goback
                        clickJS(getObject(deviceType() + "FDPs.lblBack"), "Go back");
                    }
                    Thread.sleep(1000);
                    if (deviceType == "MobileWeb") {

                    } else {
                        if (isElementDisplayed(getObject(deviceType() + "txtSecurityDevices"), 3)) {
                            report.updateTestLog("My security device", "My security device is displayed after click on Go back Button", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("My security device", "My security device NOT displayed after click on Go back Button", Status_CRAFT.FAIL);
                        }
                    }
                }
            }
        }
    }

    /**
     * Function : To Verify session timeout on manage device Page
     */
    public void verifyDeviceTimeout() throws InterruptedException {
        String ncikname = dataTable.getData("General_Data", "TabName");
        String profileobj = "xpath~//span[text()='" + ncikname + "']";
        clickJS(getObject(profileobj), ncikname + "device");
        Thread.sleep(1000);
        clickJS(getObject(deviceType() + "txtblockdevices"), "Block Device");
        isElementDisplayedWithLog(getObject(deviceType() + "Device.Devicetimeoutheader"),
                "Session Timeout Header", "Session Time out pop up", 360);
        isElementDisplayedWithLog(getObject(deviceType() + "Device.Devicetimeoutheader"),
                "Session Timeout Message", "Session Time out pop up", 360);
        isElementDisplayedWithLog(getObject(deviceType() + "login.loginButton"),
                "Login button", "Session Time out page", 70);
        clickJS(getObject(deviceType() + "login.loginButton"), "Login button");
    }

    /**
     * Function : To Verify session timeout on manage device Page
     */
    public void TimeoutDeviceverify() throws InterruptedException {
        String ncikname = dataTable.getData("General_Data", "TabName");
        String profileobj = "xpath~//span[text()='" + ncikname + "']";
        clickJS(getObject(profileobj), ncikname + "device");
        Thread.sleep(1000);
        isElementDisplayedWithLog(getObject(deviceType() + "txtblockdevices"),
                "Blcok device", "Block device button", 60);
    }

    public void securitydeviceAction() throws Exception {
        int i = 1;
        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        String deviceselect = dataTable.getData("General_Data", "Tokendeviceselect");
        if (deviceselect.equals("Blocked")) {
            for (i = 1; i <= allLinks.size(); i++) {
                String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
                ScrollToVisibleJS(activexpath);
                if (isElementDisplayed(getObject(activexpath), 3)) {

                    if ((getText(getObject(activexpath)).equals(deviceselect)) || (getText(getObject(activexpath)).equals("(" + deviceselect + ")"))) {
                        clickJS(getObject(activexpath), "Security Device");
                        report.updateTestLog("Verify" + deviceselect, deviceselect + "Devices No." + i + "is CLICKED", Status_CRAFT.PASS);
                        if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevices"), 3)) {
                            report.updateTestLog("VerifyUnblockdevices", "UnBlock Device button is present", Status_CRAFT.PASS);
                            clickJS(getObject(deviceType() + "txtUnblockdevices"), "UnBlock Device");
                            if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevicespopup"), 3)) {
                                report.updateTestLog("VerifyunBlockdevices", "unBlock Device button is present on Pop up", Status_CRAFT.PASS);
                                clickJS(getObject(deviceType() + "txtUnblockdevicespopup"), "UnBlock Device popup");
                                enterRequiredPin();
                                if (!isMobile) {
                                    if (isElementDisplayed(getObject("launch.approvalNotificationPage"), 3)) {
                                        verifyPushIsNotAccepted();
                                    }
                                }
                                if (isElementDisplayed(getObject(deviceType() + "unblockmessage"), 30)) {
                                    report.updateTestLog("unblocksuccessmessage", "unblock success message displayed", Status_CRAFT.PASS);
                                } else {
                                    report.updateTestLog("unblocksuccessmessage", "unblock success message NOT displayed", Status_CRAFT.FAIL);
                                }


                                //click on edit button
                                if (isElementDisplayed(getObject(deviceType() + "editnickname"), 3)) {
                                    clickJS(getObject(deviceType() + "editnickname"), "Edit Icon");
                                }

                                if (isElementDisplayed(getObject(deviceType() + "txtnewnickname"), 3)) {
                                    sendKeys(getObject(deviceType() + "txtnewnickname"), dataTable.getData("General_Data", "NewNickname"));
                                }
                                clickJS(getObject(deviceType() + "deviceContinue"), "Continue");
                                if (isElementDisplayed(getObject(deviceType() + "updatednickname"), 3)) {
                                    report.updateTestLog("VerifyNickname", " 16 digit alphanumeric Nickname is Updated", Status_CRAFT.PASS);
                                } else {
                                    report.updateTestLog("VerifyNickname", "16 digit alphanumeric Nickname NOT Updated", Status_CRAFT.FAIL);
                                }

                                clickJS(getObject(deviceType() + "txtblockdevices"), "Block Device");
                                if (isElementDisplayed(getObject(deviceType() + "txtblockdevicespopup"), 3)) {
                                    report.updateTestLog("VerifyBlockdevices", "Block Device button is present on Pop up", Status_CRAFT.PASS);
                                    clickJS(getObject(deviceType() + "txtblockdevicespopup"), "UnBlock Device popup");
                                    enterRequiredPin();
                                    if (!isMobile) {
                                        if (isElementDisplayed(getObject("launch.approvalNotificationPage"), 3)) {
                                            verifyPushIsNotAccepted();
                                        }
                                    }
                                    if (isElementDisplayed(getObject(deviceType() + "blockmessage"), 30)) {
                                        report.updateTestLog("unblocksuccessmessage", "block success message displayed", Status_CRAFT.PASS);
                                    } else {
                                        report.updateTestLog("unblocksuccessmessage", "block success message NOT displayed", Status_CRAFT.FAIL);
                                    }
                                    break;
                                } else {
                                    report.updateTestLog("VerifyBlockdevices", "Block Device button is not present on Pop up", Status_CRAFT.FAIL);
                                }


                            } else {
                                report.updateTestLog("VerifyunBlockdevices", "unBlock Device button is not present on Pop up", Status_CRAFT.FAIL);
                            }

                        }
                    }
                }
            }
        } else {
            if (deviceselect.equals("Active")) {
                for (i = 1; i <= allLinks.size(); i++) {
                    String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
                    if (isElementDisplayed(getObject(activexpath), 3)) {

                        if ((getText(getObject(activexpath)).equals(deviceselect)) || (getText(getObject(activexpath)).equals("(" + deviceselect + ")"))) {
                            clickJS(getObject(activexpath), "Security Device");
                            report.updateTestLog("Verify" + deviceselect, deviceselect + "Devices No. :: " + i + " :: is CLICKED", Status_CRAFT.PASS);
                            if (isElementDisplayed(getObject(deviceType() + "txtblockdevices"), 3)) {
                                report.updateTestLog("blockdevices", "Block Device button is present", Status_CRAFT.PASS);
                                clickJS(getObject(deviceType() + "txtblockdevices"), "Block Device");
                                if (isElementDisplayed(getObject(deviceType() + "txtblockdevicespopup"), 3)) {
                                    report.updateTestLog("VerifyBlockdevices", "Block Device button is present on Pop up", Status_CRAFT.PASS);
                                    clickJS(getObject(deviceType() + "txtblockdevicespopup"), "UnBlock Device popup");
                                    enterRequiredPin();
                                    if (isElementDisplayed(getObject(deviceType() + "blockmessage"), 30)) {
                                        report.updateTestLog("blocksuccessmessage", "block success message displayed", Status_CRAFT.PASS);
                                    } else {
                                        report.updateTestLog("blocksuccessmessage", "block success message NOT displayed", Status_CRAFT.FAIL);
                                    }

                                    clickJS(getObject(deviceType() + "txtUnblockdevices"), "UnBlock Device");
                                    if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevicespopup"), 3)) {
                                        report.updateTestLog("VerifyunBlockdevices", "unBlock Device button is present on Pop up", Status_CRAFT.PASS);
                                        clickJS(getObject(deviceType() + "txtUnblockdevicespopup"), "UnBlock Device popup");
                                        enterRequiredPin();
                                        if (isElementDisplayed(getObject(deviceType() + "unblockmessage"), 30)) {
                                            report.updateTestLog("unblocksuccessmessage", "unblock success message displayed", Status_CRAFT.PASS);
                                        } else {
                                            report.updateTestLog("unblocksuccessmessage", "unblock success message NOT displayed", Status_CRAFT.FAIL);
                                        }
                                    } else {
                                        report.updateTestLog("Unblockdevicespopup", "Unblock Device Pop up not displayed", Status_CRAFT.FAIL);
                                    }
                                    break;
                                } else {
                                    report.updateTestLog("VerifyBlockdevices", "Block Device button is not present on Pop up", Status_CRAFT.FAIL);
                                }

                            }
                        }

                    }
                }

            }
        }
        if (i > allLinks.size()) {
            report.updateTestLog(deviceselect, deviceselect + " Device  is not present ", Status_CRAFT.FAIL);
        }
    }

    /*
CS-2401 My Security Devices - Delete a provisioned device - SCA
      */
    public void validateDeleteDevice() throws Exception {

        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        for (int i = 1; i <= allLinks.size(); i++) {
            String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
            if (isElementDisplayed(getObject(activexpath), 3)) {
                ScrollToVisibleJS(activexpath);
                if ((getText(getObject(activexpath)).equals("Active")) || (getText(getObject(activexpath)).equals("(Active)"))) {
                    String devicenameXpath = "xpath~//*[@id='C5__C1__C4__QUE_22C3AC5D525AD48C348548_R" + i + "']";
                    String deviceName = getText(getObject(devicenameXpath));
                    clickJS(getObject(activexpath), "Security Device");
                    report.updateTestLog("VerifyActivedevices", "Active Devices : '" + deviceName + "' is CLICKED", Status_CRAFT.PASS);
                    if (isElementDisplayed(getObject(deviceType() + "txtRemovekdevices"), 8)) {
                        report.updateTestLog("VerifyUnblockdevices", "Remove Device button is present", Status_CRAFT.PASS);
                        clickJS(getObject(deviceType() + "txtRemovekdevices"), "Remove Device");
                        if (isElementDisplayed(getObject(deviceType() + "txtRemovedevicespopup"), 3)) {
                            clickJS(getObject(deviceType() + "txtRemovedevicespopup"), "Remove Device on Popup");
                            if (isElementDisplayed(getObject(deviceType() + "deletedeviceGoback"), 3)) {
                                clickJS(getObject(deviceType() + "deletedeviceGoback"), "Go back on 3/6");
                                if (isElementDisplayed(getObject(deviceType() + "devicenickname"), 3)) {
                                    report.updateTestLog("VerifyGobackbutton", "After click on Go back button Page is navigated to Detail Device Screen", Status_CRAFT.PASS);
                                } else {
                                    report.updateTestLog("VerifyGobackbutton", "After click on Go back button Page NOT navigated to Detail Device Screen", Status_CRAFT.FAIL);
                                }
                            } else {
                                report.updateTestLog("VerifyGobackbutton", "Go back Button NOT exist on delete 3/6 screen", Status_CRAFT.FAIL);
                            }
                        }
                    } else {
                        report.updateTestLog("VerifyBlockdevices", "Remove Device button NOT present", Status_CRAFT.FAIL);
                    }
                    clickJS(getObject(deviceType() + "txtRemovekdevices"), "Remove Device");
                    Thread.sleep(1000);
                    clickJS(getObject(deviceType() + "txtRemovedevicespopup"), "Remove Device on Popup");
                    break;
                } else if (getText(getObject(activexpath)).toString().contains("This device")) {
                    report.updateTestLog("VerifyUnblockdevices", " No Action Performed on device :: " + i + " :: As this is the Current Using which user logged in ", Status_CRAFT.DONE);
                } else {
                    report.updateTestLog("VerifyUnblockdevices", " This user do not have any other device that can be removed. Please change the data ", Status_CRAFT.FAIL);
                }
            }
        }
    }

    public void validateDeleteDeviceStatus() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "txtremovedevicestatus"), 30)) {
            String deviceremovestatus = getText(getObject(deviceType() + "txtremovedevicestatus"));
            report.updateTestLog("VerifyRemovedevices", " Device Remove Message : '" + deviceremovestatus + " 'is displayed and device is removed from Device List", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyRemovedevices", "Device Not Removed : ", Status_CRAFT.FAIL);
        }

        if (deviceType == "MobileWeb") {
            if (isElementDisplayed(getObject(deviceType() + "txtmysecuritydevices"), 30)) {
                clickJS(getObject(deviceType() + "txtmysecuritydevices"), "Security Device");
                //click(getObject(deviceType() + "txtmysecuritydevices"));
                report.updateTestLog("My security device", "My security device is CLICKED and Delete Device Not Exist", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("My security device", "My security device NOT CLICKED", Status_CRAFT.FAIL);
            }
        }
    }

    /*
CS-3238 My Security Devices - Popup before every action ( Block, Unblock ,Delete)- UI
       */
    public void validatePopUpAction() throws Exception {
        waitForPageLoaded();
        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));
        if (allLinks.size() == 1) {
            report.updateTestLog("VerifyActivedevices", "This user has only one device registred. Hence we can not perform the Bolck and delete operation. Please change the User..!!", Status_CRAFT.FAIL);
            return;
        }
        for (int i = 1; i <= allLinks.size(); i++) {
            String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
            if (isElementDisplayed(getObject(activexpath), 3)) {
                ScrollToVisibleJS(activexpath);
                report.updateTestLog("VerifyActivedevices", " :: Screenshot :: ", Status_CRAFT.SCREENSHOT);
                if ((getText(getObject(activexpath)).equals("Active")) || (getText(getObject(activexpath)).equals("(Active)"))) {
                    clickJS(getObject(activexpath), "Security Device");
                    report.updateTestLog("VerifyActivedevices", "Active Devices No. :: " + i + " :: is CLICKED", Status_CRAFT.PASS);
                    if (isElementDisplayed(getObject(deviceType() + "txtblockdevices"), 3)) {
                        report.updateTestLog("VerifyUnblockdevices", "Block Device button is present", Status_CRAFT.PASS);
                        clickJS(getObject(deviceType() + "txtblockdevices"), "Block Device");
                        if (isElementDisplayed(getObject(deviceType() + "txtblockdevicespopup"), 3)) {
                            report.updateTestLog("VerifyBlockdevices", "Block Device button is present on Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyBlockdevices", "Block Device button is present on Pop up", Status_CRAFT.FAIL);
                        }
                        if (isElementDisplayed(getObject(deviceType() + "txtBlockGoBackpopup"), 3)) {
                            report.updateTestLog("VerifyBlockdevices", "Go back button is present on Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyBlockdevices", "Go back button NOT present on Pop up", Status_CRAFT.FAIL);
                        }

                        //Pop up content verification

                        if (isElementDisplayed(getObject(deviceType() + "cntntblockDevice"), 3)) {
                            report.updateTestLog("VerifyBlockdevices", "Content" + getText(getObject(deviceType() + "cntntblockDevice")) + "is present on Block Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyBlockdevices", "Content" + getText(getObject(deviceType() + "cntntblockDevice")) + "NOT present on Block Pop up", Status_CRAFT.FAIL);
                        }

                        //close pop up
                        if (isElementDisplayed(getObject(deviceType() + "iconcloseUnblockpopup"), 3)) {
                            clickJS(getObject(deviceType() + "iconcloseUnblockpopup"), "Close Block Pop up");
                        } else {
                            report.updateTestLog("VerifyBlockpopup", "Block Pop up NOT closed", Status_CRAFT.FAIL);
                        }

                    } else {
                        report.updateTestLog("VerifyUnblockdevices", "block Device NOT present", Status_CRAFT.FAIL);
                    }

                    if (isElementDisplayed(getObject(deviceType() + "txtRemovekdevices"), 3)) {
                        report.updateTestLog("VerifyUnblockdevices", "Remove Device button is present", Status_CRAFT.PASS);
                        clickJS(getObject(deviceType() + "txtRemovekdevices"), "Remove Device");
                        if (isElementDisplayed(getObject(deviceType() + "txtRemovedevicespopup"), 3)) {
                            report.updateTestLog("VerifyRemovedevices", "Remove Device button is present on Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyBlockdevices", "Remove Device button NOT present on Pop up", Status_CRAFT.FAIL);
                        }

                        if (isElementDisplayed(getObject(deviceType() + "txtRemoveGoBackpopup"), 3)) {
                            report.updateTestLog("VerifyBlockdevices", "Go back button is present on Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyBlockdevices", "Go back button NOT present on Pop up", Status_CRAFT.FAIL);
                        }

                        if (isElementDisplayed(getObject(deviceType() + "cntntRemoveDevice"), 3)) {
                            report.updateTestLog("VerifyRemovedevices", "Content : " + getText(getObject(deviceType() + "cntntRemoveDevice")) + "is present on Remove Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyRemovedevices", "Content : " + getText(getObject(deviceType() + "cntntRemoveDevice")) + "NOT present on Remove Pop up", Status_CRAFT.FAIL);
                        }

                        if (isElementDisplayed(getObject(deviceType() + "iconRemoveclosepopup"), 3)) {
                            clickJS(getObject(deviceType() + "iconRemoveclosepopup"), "Close Block Pop up");
                        } else {
                            report.updateTestLog("VerifyBlockpopup", "Block Pop up NOT closed", Status_CRAFT.FAIL);
                        }

                    } else {
                        report.updateTestLog("VerifyUnblockdevices", "Remove Device NOT present", Status_CRAFT.FAIL);
                    }
                    Thread.sleep(2000);
                    clickJS(getObject(deviceType() + "titleGoback"), "Go Back");
                } else if (getText(getObject(activexpath)).toString().contains("This device")) {
                    report.updateTestLog("VerifyUnblockdevices", " No Action Performed on device :: " + i + " :: As this is the Current Using which user logged in ", Status_CRAFT.DONE);
                } else {
                    ScrollAndClickJS(activexpath);
                    report.updateTestLog("Verifyblockdevices", "BLOCK Device is CLICKED", Status_CRAFT.PASS);
                    if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevices"), 3)) {
                        report.updateTestLog("VerifyUnblockdevices", "UnBlock Device button is present", Status_CRAFT.PASS);
                        clickJS(getObject(deviceType() + "txtUnblockdevices"), "UnBlock Device");
                        if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevicespopup"), 3)) {
                            report.updateTestLog("VerifyUnBlockdevices", "UnBlock Device button is present on Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyBlockdevices", "UnBlock Device button NOT present on Pop up", Status_CRAFT.FAIL);
                        }

                        //Go back button
                        if (isElementDisplayed(getObject(deviceType() + "txtUnBlkGoBackpopup"), 3)) {
                            report.updateTestLog("VerifyBlockdevices", "Go back button is present on Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyBlockdevices", "Go back button NOT present on Pop up", Status_CRAFT.FAIL);
                        }

                        if (isElementDisplayed(getObject(deviceType() + "cntntUnblockDevice"), 3)) {
                            report.updateTestLog("VerifyUnBlockdevices", "Content : " + getText(getObject(deviceType() + "cntntUnblockDevice")) + "is present on Block Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyUnBlockdevices", "Content : " + getText(getObject(deviceType() + "cntntUnblockDevice")) + "NOT present on Block Pop up", Status_CRAFT.FAIL);
                        }

                        if (isElementDisplayed(getObject(deviceType() + "iconcloseUnblockpopup"), 3)) {
                            clickJS(getObject(deviceType() + "iconcloseUnblockpopup"), "Close UnBlock Pop up");
                        } else {
                            report.updateTestLog("VerifyUnBlockpopup", "UnBlock Pop up NOT closed", Status_CRAFT.FAIL);
                        }

                    } else {
                        report.updateTestLog("VerifyUnblockdevices", "block Device NOT present", Status_CRAFT.FAIL);
                    }
                    Thread.sleep(2000);

                    if (isElementDisplayed(getObject(deviceType() + "txtRemovekdevices"), 3)) {
                        report.updateTestLog("VerifyUnblockdevices", "Remove Device button is present", Status_CRAFT.PASS);
                        clickJS(getObject(deviceType() + "txtRemovekdevices"), "Remove Device");
                        if (isElementDisplayed(getObject(deviceType() + "txtRemovedevicespopup"), 3)) {
                            report.updateTestLog("VerifyRemovedevices", "Remove Device button is present on Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyBlockdevices", "Remove Device button NOT present on Pop up", Status_CRAFT.FAIL);
                        }

                        //Go back button
                        if (isElementDisplayed(getObject(deviceType() + "txtRemoveGoBackpopup"), 3)) {
                            report.updateTestLog("VerifyBlockdevices", "Go back button is present on Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyBlockdevices", "Go back button NOT present on Pop up", Status_CRAFT.FAIL);
                        }

                        //Pop up content verification

                        if (isElementDisplayed(getObject(deviceType() + "cntntRemoveDevice"), 3)) {
                            report.updateTestLog("VerifyRemovedevices", "Content : " + getText(getObject(deviceType() + "cntntRemoveDevice")) + "is present on Remove Pop up", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("VerifyRemovedevices", "Content : " + getText(getObject(deviceType() + "cntntRemoveDevice")) + "NOT present on Remove Pop up", Status_CRAFT.FAIL);
                        }

                        //close pop up
                        if (isElementDisplayed(getObject(deviceType() + "iconRemoveclosepopup"), 3)) {
                            clickJS(getObject(deviceType() + "iconRemoveclosepopup"), "Close Block Pop up");
                        } else {
                            report.updateTestLog("VerifyBlockpopup", "Block Pop up NOT closed", Status_CRAFT.FAIL);
                        }

                    } else {
                        report.updateTestLog("VerifyUnblockdevices", "Remove Device NOT present", Status_CRAFT.FAIL);
                    }
                    Thread.sleep(2000);
                    clickJS(getObject(deviceType() + "titleGoback"), "Go Back");
                    Thread.sleep(2000);
                    if (deviceType == "MobileWeb") {
                        if (isElementDisplayed(getObject(deviceType() + "txtmysecuritydevices"), 3)) {
                            clickJS(getObject(deviceType() + "txtmysecuritydevices"), "Security Device");
                            report.updateTestLog("My security device", "My security device is CLICKED", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("My security device", "My security device NOT CLICKED", Status_CRAFT.FAIL);
                        }
                    }
                    break;
                }

            }
        }

    }

    public void securitydeviceBlockAction() throws Exception {
        int i = 1;
        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        String deviceselect = dataTable.getData("General_Data", "Tokendeviceselect");
        if (deviceselect.equals("Blocked")) {
            for (i = 1; i <= allLinks.size(); i++) {
                // String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
                String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
                if (isElementDisplayed(getObject(activexpath), 3)) {

                    if ((getText(getObject(activexpath)).equals(deviceselect)) || (getText(getObject(activexpath)).equals("(" + deviceselect + ")"))) {
                        clickJS(getObject(activexpath), "Security Device");


                        //click(getObject(activexpath));
                        report.updateTestLog("Verify" + deviceselect, deviceselect + "Devices No." + i + "is CLICKED", Status_CRAFT.PASS);
                        if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevices"), 3)) {
                            report.updateTestLog("VerifyUnblockdevices", "UnBlock Device button is present", Status_CRAFT.PASS);
                            clickJS(getObject(deviceType() + "txtUnblockdevices"), "UnBlock Device");
                            if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevicespopup"), 3)) {
                                report.updateTestLog("VerifyunBlockdevices", "unBlock Device button is present on Pop up", Status_CRAFT.PASS);
                                clickJS(getObject(deviceType() + "txtUnblockdevicespopup"), "UnBlock Device popup");
                                enterinvalidPin();
                                Thread.sleep(3000);
                                clickJS(getObject(deviceType() + "tryagain"), "tryagain");
                                enterinvalidPin();
                                Thread.sleep(3000);
                                clickJS(getObject(deviceType() + "tryagain"), "tryagain");
                                enterinvalidPin();
                                Thread.sleep(3000);
                                clickJS(getObject(deviceType() + "tryagain"), "tryagain");
                                enterinvalidPin();
                                Thread.sleep(3000);
                                clickJS(getObject(deviceType() + "tryagain"), "tryagain");
                                enterinvalidPin();

                                if (isElementDisplayed(getObject(deviceType() + "blockmsg"), 30)) {
                                    report.updateTestLog("blocksuccessmessage", "user block success message displayed", Status_CRAFT.PASS);
                                } else {
                                    report.updateTestLog("blocksuccessmessage", "user block success message NOT displayed", Status_CRAFT.FAIL);
                                }


                            } else {
                                report.updateTestLog("VerifyunBlockdevices", "unBlock Device button is not present on Pop up", Status_CRAFT.FAIL);
                            }

                        }
                    }
                }
            }
        } else {
            if (deviceselect.equals("Active")) {
                for (i = 1; i <= allLinks.size(); i++) {
                    // String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
                    String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
                    if (isElementDisplayed(getObject(activexpath), 3)) {

                        if ((getText(getObject(activexpath)).equals(deviceselect)) || (getText(getObject(activexpath)).equals("(" + deviceselect + ")"))) {
                            clickJS(getObject(activexpath), "Security Device");
                            //click(getObject(activexpath));
                            report.updateTestLog("Verify" + deviceselect, deviceselect + "Devices No." + i + "is CLICKED", Status_CRAFT.PASS);
                            if (isElementDisplayed(getObject(deviceType() + "txtblockdevices"), 3)) {
                                report.updateTestLog("blockdevices", "Block Device button is present", Status_CRAFT.PASS);
                                clickJS(getObject(deviceType() + "txtblockdevices"), "Block Device");
                                if (isElementDisplayed(getObject(deviceType() + "txtblockdevicespopup"), 3)) {
                                    report.updateTestLog("VerifyBlockdevices", "Block Device button is present on Pop up", Status_CRAFT.PASS);
                                    clickJS(getObject(deviceType() + "txtblockdevicespopup"), "UnBlock Device popup");
                                    enterinvalidPin();
                                    Thread.sleep(3000);
                                    clickJS(getObject(deviceType() + "tryagain"), "tryagain");
                                    enterinvalidPin();
                                    Thread.sleep(3000);
                                    clickJS(getObject(deviceType() + "tryagain"), "tryagain");
                                    enterinvalidPin();
                                    Thread.sleep(3000);
                                    clickJS(getObject(deviceType() + "tryagain"), "tryagain");
                                    enterinvalidPin();
                                    Thread.sleep(3000);
                                    clickJS(getObject(deviceType() + "tryagain"), "tryagain");
                                    enterinvalidPin();

                                    if (isElementDisplayed(getObject(deviceType() + "blockmsg"), 30)) {
                                        report.updateTestLog("blocksuccessmessage", "user block success message displayed", Status_CRAFT.PASS);
                                    } else {
                                        report.updateTestLog("blocksuccessmessage", "user block success message NOT displayed", Status_CRAFT.FAIL);
                                    }


                                    break;
                                } else {
                                    report.updateTestLog("VerifyBlockdevices", "Block Device button is not present on Pop up", Status_CRAFT.FAIL);
                                }

                            }
                        }

                    }
                }

            }
        }
        if (i > allLinks.size()) {
            report.updateTestLog(deviceselect, deviceselect + " Device  is not present ", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Function to invalid PIN input
     * Update on     Updated by    Reason
     * 15/04/2019   C961300        Updated the Thread.sleep
     */
    public void enterinvalidPin() throws InterruptedException {
        String[] arrPin = dataTable.getData("General_Data", "InvalidPIN").split("");
        String strObject = "";

        //To handle the SCA pin page
        if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"), 30)) {
            strObject = "launch.edtPinEnterFieldGrpSCA";
            report.updateTestLog("VerifyHomePage", ":: Step 1 of 3 :: SCA - Uplift ::", Status_CRAFT.SCREENSHOT);
        } else if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrp"), 2)) {
            strObject = "launch.edtPinEnterFieldGrp";
        }

        List<WebElement> lstPinEnterFieldGrp = findElements(getObject(strObject));
        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
            }
        }

        report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.DONE);

        //To handle the SCA pin page
        if (isElementDisplayed(getObject("launch.btnConfirm"), 2)) {
            clickJS(getObject("launch.btnConfirm"), "Clicked on Confirm button");
        } else if (isElementDisplayed(getObject("w.home.btnLogin"), 2)) {
            clickJS(getObject("w.home.btnLogin"), "Clicked on Login button");
        } else if (isElementDisplayed(getObject("launch.btnContinue"), 2)) {
            clickJS(getObject("launch.btnContinue"), "Clicked on Continue button");
        }
    }

    //cs-4635 Forget your  UserID
    public void verifyForgetUserIDContent() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "launch.forgetuserId"), 2)) {
            clickJS(getObject(deviceType() + "launch.forgetuserId"), "Forgot your User ID link");
            //*[text()='Based on where you are right now, text the most suitable number below and we’ll reply with your user ID.']
            List<WebElement> call = driver.findElements(By.xpath("//*[@class='boi_contact-phonenumbers-row']/div/p"));
            if (call.size() != 0) {
                for (int i = 0; i < call.size(); i++) {
                    String text = call.get(i).getText();
                    if (text.equals("Republic of Ireland") || text.equals("Text \"user\" to 50365") || text.equals("Northern Ireland and Great Britain") || text.equals("Text \"user\" to 50365") || text.equals("Other locations") || text.equals("Text \"user\" to +353 86 180 3888")) {
                        report.updateTestLog("verifyForgetUserIDContent", "Call button/details have correct details", Status_CRAFT.PASS);

                    }
                }
            } else {
                report.updateTestLog("verifyForgetUserIDContent", "Call button/details not present", Status_CRAFT.FAIL);
            }

        } else {
            report.updateTestLog("verifyForgetUserIDContent", "verifyForgetUserIDContent is not clicked successfully", Status_CRAFT.FAIL);
        }

        if (deviceType().equals("mw.")) {
            clickJS(getObject("xpath~//*[text()='Close']"), "Close button");
        } else {
            clickJS(getObject("xpath~//button[@title='Close']"), "Close button");
        }
    }


    //Squad5---------------------


    public void validateHardTokenLinkPopup() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "hardtokenLink"), 3)) {
            clickJS(getObject(deviceType() + "hardtokenLink"), "Faster,Easier Link");
        } else {
            report.updateTestLog("HardTokenLink", "HardToken Link NOT Found", Status_CRAFT.FAIL);
        }
        //Validate Pop Up Heading
        if (isElementDisplayed(getObject(deviceType() + "popupheading"), 3)) {
            String deviceremovestatus = getText(getObject(deviceType() + "popupheading"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Heading : '" + deviceremovestatus + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp NOT Found", Status_CRAFT.FAIL);
        }

        //Validate Pop up one Content
        if (isElementDisplayed(getObject(deviceType() + "headingone"), 3)) {
            String popupHeadingContent = getText(getObject(deviceType() + "headingone"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading : '" + popupHeadingContent + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading NOT Found", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "headingonecontent"), 3)) {
            String popupHeadingoneContent = getText(getObject(deviceType() + "headingonecontent"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading One : '" + popupHeadingoneContent + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading One NOT Found", Status_CRAFT.FAIL);
        }

        //Validate Pop up two Content
        if (isElementDisplayed(getObject(deviceType() + "headingtwo"), 3)) {
            String popupHeadingContent = getText(getObject(deviceType() + "headingtwo"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading : '" + popupHeadingContent + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading NOT Found", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "headingtwocontent"), 3)) {
            String popupHeadingthreeContent = getText(getObject(deviceType() + "headingtwocontent"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading Two : '" + popupHeadingthreeContent + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading Two NOT Found", Status_CRAFT.FAIL);
        }

        //Validate Pop up three Content
        if (isElementDisplayed(getObject(deviceType() + "headingthree"), 3)) {
            String popupHeading2Content = getText(getObject(deviceType() + "headingthree"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading three : '" + popupHeading2Content + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading NOT Found", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "headingthreecontent"), 3)) {
            String popupHeadingthreeContent = getText(getObject(deviceType() + "headingthreecontent"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading Three : '" + popupHeadingthreeContent + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading Three NOT Found", Status_CRAFT.FAIL);
        }

        //validate Cross Sign

        if (isElementDisplayed(getObject(deviceType() + "crosssignclose"), 3)) {
            report.updateTestLog("VerifyPopUpHeading", "Cross sign for pop up close is present on POP up window", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "crosssignclose"), "Cross Close Sign");
        } else {
            clickJS(getObject(deviceType() + "crosssigncloseDevice"), "Cross Close Sign");
        }

        //click on link again

        clickJS(getObject(deviceType() + "hardtokenLink"), "Faster,Easier Link");

        //Validate Close Button

        if (isElementDisplayed(getObject(deviceType() + "closepopup"), 3)) {
            report.updateTestLog("VerifyPopUpHeading", "Close Button is present on POP up window", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "closepopup"), "Close Button");
        } else {
            report.updateTestLog("VerifyPopUpHeading", "Close Button NOT present on POP up window", Status_CRAFT.FAIL);
        }
    }

    //CS-4494
    public void validateRequestPhysical() throws Exception {

        //Validate Pop Up Heading
        if (isElementDisplayed(getObject(deviceType() + "RequestPhysicalKeyhead"), 3)) {
            String deviceremovestatus = getText(getObject(deviceType() + "RequestPhysicalKeyhead"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Heading : '" + deviceremovestatus + " 'is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp NOT Found", Status_CRAFT.FAIL);
        }

        //Validate Pop up one Content
        if (isElementDisplayed(getObject(deviceType() + "headingone"), 3)) {
            String popupHeadingContent = getText(getObject(deviceType() + "headingone"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading : '" + popupHeadingContent + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading NOT Found", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "headingonecontent"), 3)) {
            String popupHeadingoneContent = getText(getObject(deviceType() + "headingonecontent"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading One : '" + popupHeadingoneContent + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading One NOT Found", Status_CRAFT.FAIL);
        }

        //Validate Pop up two Content
        if (isElementDisplayed(getObject(deviceType() + "headingtwo"), 3)) {
            String popupHeadingContent = getText(getObject(deviceType() + "headingtwo"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading : '" + popupHeadingContent + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading NOT Found", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "headingtwocontent"), 3)) {
            String popupHeadingthreeContent = getText(getObject(deviceType() + "headingtwocontent"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading Two : '" + popupHeadingthreeContent + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading Two NOT Found", Status_CRAFT.FAIL);
        }

        //Validate Pop up three Content
        if (isElementDisplayed(getObject(deviceType() + "headingthree"), 3)) {
            String popupHeading2Content = getText(getObject(deviceType() + "headingthree"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading three : '" + popupHeading2Content + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading NOT Found", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "headingthreecontent"), 3)) {
            String popupHeadingthreeContent = getText(getObject(deviceType() + "headingthreecontent"));
            report.updateTestLog("VerifyPopUpHeading", " Pop Up Content Heading Three : '" + popupHeadingthreeContent + " 'is displayed on Pop Up", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyPopUpHeading", "PopUp Content Heading Three NOT Found", Status_CRAFT.FAIL);
        }

        //click on link again

        clickJS(getObject(deviceType() + "dontHaveSmartPhoneLink"), "Don’t have a smartphone or tablet? Link");

        //Now Click on Request a Physical Security key link
        waitForPageLoaded();
        clickJS(getObject("xpath~//*[text()='Request Physical Security Key']"), "Request Physical Security Key");
    }


    public void verifyRequestPhysicalSecurityKey() throws Exception {
        if (isElementDisplayed(getObject("xpath~//span[text()='Contact us']"), 3)) {
            report.updateTestLog("verifyRequestPhysicalSecurityKey", "Contact Us is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyRequestPhysicalSecurityKey", "Contact Us is NOT Found", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("xpath~//a[text()='Access the old site']"), 3)) {
            String link = driver.findElement(By.xpath("//a[text()='Access the old site']")).getAttribute("href");
            report.updateTestLog("Access the old site", "Access the old siteis displayed", Status_CRAFT.PASS);
            if (link.equals("https://www.365online.com")) {
                report.updateTestLog("Access the old site", "Access the old siteis displayed", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("Access the old site", "Access the old site is NOT Found as a part of phase 3 desktop changes", Status_CRAFT.DONE);
        }

        if (isElementDisplayed(getObject("xpath~//span[text()='Set up your smartphone or tablet']"), 3)) {
            report.updateTestLog("verifyRequestPhysicalSecurityKey", "Set up your smartphone or tablet button is displayed", Status_CRAFT.PASS);
            clickJS(getObject("xpath~//span[text()='Set up your smartphone or tablet']"), "link");
            if (isElementDisplayed(getObject(deviceType() + "RequestPhysicalKeyhead"), 3)) {
                String deviceremovestatus = getText(getObject(deviceType() + "RequestPhysicalKeyhead"));
                report.updateTestLog("VerifyPopUpHeading", " Pop Up Heading : '" + deviceremovestatus + " 'is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyPopUpHeading", "PopUp NOT Found", Status_CRAFT.FAIL);
            }

        } else {
            report.updateTestLog("verifyRequestPhysicalSecurityKey", "Set up your smartphone or tablet buttonis NOT Found", Status_CRAFT.FAIL);
        }


    }

    public void selectDeviceListHTokenSCA() throws Exception {
        String strdevice = dataTable.getData("General_Data", "Relationship_Status");
        //String strdevice = dataTable.getData("General_Data", "Relationship_Status");

        if (isElementDisplayed(getObject("launch.deviceListPage"), 5)) {
            report.updateTestLog("DeviceListPage", " Choose security device page is displayed", Status_CRAFT.PASS);


            if (isElementDisplayed(getObject("Devicelist.Next"), 5)) {
                report.updateTestLog("selectDeviceListSCA", "Next button is displayed", Status_CRAFT.PASS);

                while (!isElementDisplayed(getObject("xpath~//span[text()='" + strdevice + "']"), 3)) {

                    clickJS(getObject("Devicelist.Next"), "Next button");
                }

            }
            Thread.sleep(1000);

            clickJS(getObject((("xpath~//span[text()='" + strdevice + "']"))), "Device");
            report.updateTestLog("DeviceListPage", " Device selected from list successfully", Status_CRAFT.PASS);
            clickJS(getObject("launch.btnChooseDevices"), "Choose Devices");
        } else {
            report.updateTestLog("DeviceListPage", " Device is not selected/present ", Status_CRAFT.DONE);
        }

    }


    /*
     CS-3597 Pop-up for 'Want this process to be faster, easier just as secure?' link
           */
    public void validatePSKLink() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "profileIcon"), 3)) {
            Thread.sleep(200);
            clickJS(getObject(deviceType() + "profileIcon"), "Profile Icon");
            //click(getObject(deviceType() + "profileIcon"));
            if (isElementDisplayed(getObject(deviceType() + "txtprofile"), 3)) {
                if (deviceType().equals("mw.")) {
                    clickJS(getObject(deviceType() + "home.SecurityDevices"), "Security Device");
                }
                report.updateTestLog("verifyprofileclick", "click on profile successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyprofileclick", "Profile Icon not clicked", Status_CRAFT.FAIL);
            }
        }

        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        for (int i = 1; i <= allLinks.size() + 1; i++) {
            String devicenameXpath = "xpath~//*[@id='C5__C1__C4__QUE_22C3AC5D525AD48C348548_R" + i + "']";
            if (isElementDisplayed(getObject(devicenameXpath), 3)) {
                if ((getText(getObject(devicenameXpath)).equals("Physical Security Key")) || (getText(getObject(devicenameXpath)).equals("(Physical Security Key)"))) {
                    String deviceName = getText(getObject(devicenameXpath));
                    clickJS(getObject(devicenameXpath), "Physical Security Key");
                    Thread.sleep(2000);
                    validateHardTokenLinkPopup();
                    break;
                }
            }
        }
    }

    // Function to verify presence of Go Back arrow on screens and click action over it
    public void gobacknav() throws Exception {
        waitForPageLoaded();
        if (isElementDisplayed(getObject(deviceType() + "btnGoBack"), 20)) {
            report.updateTestLog("verifyGoBackNavigation", "Go Back link present on Screen", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "btnGoBack"), "Back Arrow");
            waitForPageLoaded();
        } else {
            report.updateTestLog("verifyGoBackNavigation", "Go Back link is not present on Screen", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to validate the back arrow navigation from Profile Management to service desk
     */
    public void validateServicesNavigation() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "service.hdrServiceDesk"), 5)) {
            report.updateTestLog("ServiceDeskNaviagtion", "Service Desk Page Loaded successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("ServiceDeskNaviagtion", "Service Desk Page NOT Loaded successfully Or 'Service Desk' Header Not found", Status_CRAFT.FAIL);
        }
    }


    /**
     * Function : Function to check error message on invalid UXP 360 URL entered
     * Update on     Updated by    Reason
     * 18/11/2019   C966119        Newly Created
     */
    public void validateErrorOnInvalidURL() throws Exception {
        String strAppUrl = dataTable.getData("Login_Data", "Application_URL");
        driver.get(strAppUrl);
        report.updateTestLog("validateErrorOnInvalidURL", "Launching Application with correct URL as :: " + strAppUrl, Status_CRAFT.PASS);
        strAppUrl = strAppUrl.replace("servletcontroller", "..servletcontroller");
        driver.get(strAppUrl);
        report.updateTestLog("validateErrorOnInvalidURL", "Launching Application with incorrect URL as :: " + strAppUrl, Status_CRAFT.PASS);

        isElementDisplayedWithLog(getObject(deviceType() + "errorPage.lblHeader"),
                "'Error' Header ", "Error Landing", 5);

        isElementDisplayedWithLog(getObject("errorPage.imgError"),
                "'Error' image ", "Error Landing", 5);

        isElementDisplayedWithLog(getObject("errorPage.lblErrText"),
                "'Error' Text :: " + getText(getObject("errorPage.lblErrText")), "Error Landing", 5);
        waitForPageLoaded();

    }


    /**
     * Function : Function to check SSO Life Online
     * Update on     Updated by    Reason
     * 20/11/2019   C966119        Newly Created CFPUR-56
     */
    public void validateLifeOnlineSSO() throws Exception {
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String ProdType = dataTable.getData("General_Data", "Relationship_Status");
        String strParentObject = "xpath~//*[contains(@class,'boi')][contains(text(),'" + strAccountIBAN + "')]/ancestor::div[contains(@class,'boi-table-tile-with-border')]/descendant::*[contains(text(),'View my policy')]";
        String strParentObject1 = "xpath~//*[contains(@class,'boi')][contains(text(),'" + strAccountIBAN + "')]/ancestor::div[contains(@class,'boi-table-tile-with-border')]/descendant::*[contains(text(),'View my policy')]/..";
        if (deviceType() == "mw." | deviceType() == "tw." | isMobile) {
            if (isElementDisplayed(getObject(strParentObject), 2)) {
                report.updateTestLog("validateLifeOnlineSSO", " 'View my policy' is available for Life online SSO on Mobile App , Tablet App , Mobile Browser , Tablet browser...which is NOT correct ", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("validateLifeOnlineSSO", " 'View my policy' is not available for Life online SSO on Mobile App , Tablet App , Mobile Browser , Tablet browser.", Status_CRAFT.PASS);
            }
            return;
        }
        ScrollToVisibleJS(strParentObject);
        report.updateTestLog("validateLifeOnlineSSO", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        isElementDisplayedWithLog(getObject(strParentObject), " 'Life Online Policy' with Last four digits ending with :: " + strAccountIBAN, "Policy section on Home", 1);
        clickJS(getObject(strParentObject), "Clicking on 'View my policy' link");
        String link = driver.findElement(getObject(strParentObject1)).getAttribute("href");
        System.out.println(link);
        if (link.contains(ProdType)) {
            report.updateTestLog("validateLifeOnlineSSO", " Product type " + ProdType + " is present for Life online SSO ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateLifeOnlineSSO", " Product type " + ProdType + " is not present correctly for Life online SSO ", Status_CRAFT.FAIL);
        }
        String strLinkToVerify = "https://dev-iandi-dev.tibus.net/defaultpage";
        ArrayList<String> arrTabs = new ArrayList<String>(driver.getWindowHandles());
        waitForPageLoaded();
        if (arrTabs.size() > 0) {
            driver.switchTo().window(arrTabs.get(1));
            String strLinkToVerifyNew = driver.getCurrentUrl();
            report.updateTestLog("validateLifeOnlineSSO", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
            if (strLinkToVerifyNew.equals(strLinkToVerify)) {
                report.updateTestLog("verifyNewlyOpendTab", " Newly Opened Tab Opens the URL" + strLinkToVerifyNew, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyNewlyOpendTab", " New Tab did not opened successfully..Please check...!", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function : Function to check Regulatory Test
     * Update on     Updated by    Reason
     * 20/11/2019   C966119        Newly Created CFPUR-56
     */
    public void verifyregulatoryText() throws Exception {
        HomePage home = new HomePage(scriptHelper);
        FunctionalComponents function = new FunctionalComponents(scriptHelper);

        if (deviceType().equals("mw.") || deviceType().equals("tw.") || isMobile) {
            home = new HomePage(scriptHelper);
            waitForElementToClickable(getObject("home.lnkPreLgnMore"), 14);
            home.clickmore();
            waitForPageLoaded();
            home.verifyCheckContentPresent(deviceType() + "home.regulatorytext");
            waitForElementToClickable(getObject("home.BackArrow"), 8);
            clickJS(getObject("home.BackArrow"), "Back Arrow");
            waitForPageLoaded();
            SCA_MobileAPP scaMobileAPP = new SCA_MobileAPP(scriptHelper);
            if (deviceType().equals("tw.") || isMobile) {
                scaMobileAPP.functionToHandleLogin();
            }
            function.login();
            if (deviceType.equalsIgnoreCase("Web")) {
                if (isElementDisplayed(getObject("launch.approvalNotificationPage"), 3)) {
                    verifyPushIsNotAccepted();
                }
            }
            waitForPageLoaded();
            clickJS(getObject("home.More"), "More item");
            home.verifyCheckContentPresent(deviceType() + "home.regulatorytext");
            waitForPageLoaded();
            clickJS(getObject("home.AccountItem"), "Accounts item");
            click(getObject(deviceType() + "home.imgProfileIcon"), "Profile icon clicked");
            if (isElementDisplayed(getObject(deviceType() + "home.btnLogout"), 5)) {
                clickJS(getObject(deviceType() + "home.btnLogout"), "logout clicked");
            }
            click(getObject(deviceType() + "home.btnYesMeLogOut"), "Yes, log me out");
            home.clickmore();
            home.verifyCheckContentPresent(deviceType() + "home.regulatorytext");
        } else {
            waitForPageLoaded();
            home.verifyCheckContentPresent(deviceType() + "home.regulatorytext");
            enterLoginDetails();
            enterRequiredPin();
            if (isElementDisplayed(getObject("launch.deviceListPage"), 5)) {
                home.verifyCheckContentPresent(deviceType() + "home.regulatorytext");
                verifySelectRegisterDevice();
            }
            if (isElementDisplayed(getObject("launch.approvalNotificationPage"), 3)) {
                verifyPushIsNotAccepted();
            }
            home.verifyHomePage();
            home.verifylogout(deviceType() + "home.btnLogout");
            home.verifyCheckContentPresent(deviceType() + "home.regulatorytext");

        }
    }

    /**
     * Function : Function to PushIsNotAccepted
     * Update on     Updated by    Reason
     * 20/11/2019
     */
    public void verifyPushIsNotAccepted() throws Exception {
        int intHours = 5;
        long intMilSeconds = intHours * 60 * 60 * 2 * 1000 * 1000;
        int intCounter = 0;
        report.updateTestLog("verifyPushIsNotAccepted", " Waiting till Push is getting Accepted ", Status_CRAFT.DONE);
        for (int i = 1; i <= intMilSeconds; i++) {
            do {
                if (!(isElementDisplayed(getObject("launch.approvalNotificationPage"), 3))) {
                    if (isElementDisplayed(getObject("xpath~//*[text()='Your login attempt failed. Please try again.']"), 3)) {
                        report.updateTestLog("verifyPushIsNotAccepted", "SCAPushnotificationistimmedout..witherrormessage:Yourloginattemptfailed.Pleasetryagain.", Status_CRAFT.FAIL);
                    }
                    return;
                }
            } while (!(isElementDisplayed(getObject("launch.approvalNotificationPage"), 3)));
        }

    }

    /**
     * Function : Function to check Regulatory Test
     * Update on     Updated by    Reason
     * 20/11/2019
     */
    public void verifyRegulatoryTextOption() throws Exception {
        waitForPageLoaded();
        clickJS(getObject("home.More"), "More item");
        FunctionalComponents function = new FunctionalComponents(scriptHelper);
        waitForPageLoaded();
        function.verifyRegulatoryInfo();
        verifylogout(deviceType() + "home.btnLogout");
        clickJS(getObject("home.More"), "More item");
        function.verifyRegulatoryInfo();
    }


    public void verifyUKsavingsAndFSCSbanner() throws Exception {
        waitForPageLoaded();
        waitForJQueryLoad();
        ScrollToVisibleJS(deviceType() + "home.UKSaving");
        if (isElementDisplayed(getObject(deviceType() + "home.UKSaving"), 5)) {
            report.updateTestLog("HOME Page Update", "UK savings is displayed successfully", Status_CRAFT.PASS);
            if (deviceType() == "mw." | deviceType() == "tw." | isMobile) {
                String strExpectedLink = "https://www.bankofirelanduk.com/rates-and-fees/ni-rates-and-fees/savings-and-deposits-rates/";
                verifyHrefLink(strExpectedLink, deviceType() + "home.UKSaving");
            } else {
                clickJS(getObject(deviceType() + "home.UKSaving"), "Uk savings is clicked");
                verifyNewlyOpenedTab("https://www.bankofirelanduk.com/rates-and-fees/ni-rates-and-fees/savings-and-deposits-rates/");
            }
        } else {
            report.updateTestLog("HOME Page Update", "UK savings is not displayed ", Status_CRAFT.PASS);
        }

        ScrollToVisibleJS(deviceType() + "home.FSCSbanner");
        if (isElementDisplayed(getObject(deviceType() + "home.FSCSbanner"), 5)) {
            report.updateTestLog("HOME Page Update", "FSCS Banner is displayed successfully", Status_CRAFT.PASS);
            if (deviceType() == "mw." | deviceType() == "tw." | isMobile) {
                String strExpectedLink = "https://www.fscs.org.uk/";
                verifyHrefLink(strExpectedLink, deviceType() + "home.FSCSbanner");
            } else {
                clickJS(getObject(deviceType() + "home.FSCSbanner"), "FSFC Banner is clicked");
                verifyNewlyOpenedTab("https://www.fscs.org.uk/");
            }
        } else {
            report.updateTestLog("HOME Page Update", "FSCS Banner is not displayed ", Status_CRAFT.FAIL);
        }
    }

    public void verifyUKsavingsForNOBKK() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "home.UKSaving"), 5)) {
            report.updateTestLog("HOME Page Update", "UK savings is displayed successfully", Status_CRAFT.PASS);
            waitForPageLoaded();
            report.updateTestLog("HOME Page Update", "UK savings is displayed for NI without BKK", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("HOME Page Update", "UK savings is not displayed ", Status_CRAFT.PASS);
        }

    }


    public void verifyFSCSNeg() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "home.FSCSbanner"), 5)) {
            report.updateTestLog("HOME Page Update", "FSCS Banner is displayed successfully", Status_CRAFT.PASS);


            if (isElementDisplayed(getObject(deviceType() + "home.UKSaving"), 5)) {
                waitForPageLoaded();
                report.updateTestLog("HOME Page Update", "UK savings is displayed for NI without BKK", Status_CRAFT.FAIL);

            } else {
                //report.updateTestLog("HOME Page Update", "UK savings is not displayed ", Status_CRAFT.SCREENSHOT);
                report.updateTestLog("HOME Page Update", "UK savings is not displayed ", Status_CRAFT.PASS);


            }

        }
    }
    
    public boolean selectdevice(String strRegisteredDevice ) throws Exception {
    	 String strDeviceName = "";    	
    	 Boolean deviceFlag = false;
    	List<WebElement> lstDevices = findElements(getObject("launch.lstRegisteredDevices"));
        for (WebElement device : lstDevices ) {
         for (int k = 0; k <= 2; k++) {
             try {
                strDeviceName = device.getText();
                break;
             } catch (StaleElementReferenceException e) {
                 e.printStackTrace();
             }
         }
            //Sindhuja - to select the exact the device present in application with datasheet
            if (strDeviceName.equals(strRegisteredDevice)) {
                for (int j = 0; j <= 2; j++) {
                    try {
                    	device.click(); 
                        deviceFlag = true;
                        break;
                    } catch (StaleElementReferenceException e) {
                        e.printStackTrace();
                    }
                }
                waitForPageLoaded();
                if(deviceFlag) {
                 report.updateTestLog("verifySelectRegisterDevice", " Device with name '" + strRegisteredDevice + "' selected on Device selection page ", Status_CRAFT.PASS);
                 break;
             }
            }     
           }
     return deviceFlag;
  } 
   
}







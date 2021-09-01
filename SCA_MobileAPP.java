package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Settings;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.Util;
import com.cognizant.framework.selenium.WebDriverHelper;
import componentgroups.FunctionalComponents;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by C100136 on 30/04/2019.
 */
public class SCA_MobileAPP extends WebDriverHelper {
    private static Properties mobileProperties;

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */

    public static AppiumDriver Mobiledriver = null;
    public SCA_MobileAPP(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    public void changeNativeToWebview() {
        Set<String> availableContexts = driver.getContextHandles();
        System.out.println("Total No of Context Found After we reach to WebView = " + availableContexts.size());
        for (String context : availableContexts) {
            if (context.contains("WEBVIEW_com.bankofireland.tcmb")||context.contains("WEBVIEW_Temenos")||context.contains("WEBVIEW_")) {
                System.out.println("Context Name is " + context);
                driver.context(context);
                break;
            }
        }
    }

    public void changeWebviewToNative() {
        // 4.4 To stop automating in the web view context we can simply call the context again with id NATIVE_APP.
        Set<String> Contexts = driver.getContextHandles();
        for (String context : Contexts) {
            if (context.contains("NATIVE")) {
                System.out.println("We are Back to " + context);
                driver.context(context);
                System.out.println("Context Switched");
            }
        }
    }


    /*
     * Update on     Updated by     Reason
     * 06/06/2019     C966003       Written code ass per framework standard
     */
    public void welcomeScreen() throws InterruptedException {
        Thread.sleep(2000);
        changeNativeToWebview();
        click(getObject("launch.btnRegisterThisDevice"),"Register this Device");
    }


    /*
     * Update on     Updated by     Reason
     * 06/06/2019     C966003       Written code ass per framework standard
     */
    public void beforeYouBegin() throws Exception {
        String strHeadTitle = getText(getObject("launch.txtBYBHeadTitle"));
        waitForPageLoaded();
        if (strHeadTitle.equals("Before you begin")) {
            report.updateTestLog("BeforeYourBegin", "BeforeYourBegin Page launched successfully", Status_CRAFT.PASS);
            clickJS(getObject("launch.btnContinue"), "Continue");
        } else {
            report.updateTestLog("BeforeYourBegin", "BeforeYourBegin Page  not launched successfully", Status_CRAFT.FAIL);
        }
    }

    public void beforeYouBeginvalidations() throws Exception {
        String strHeadTitle = getText(getObject("launch.txtBYBHeadTitle"));
        String strMessege1 = getText(getObject("launch.txtBYBMessegae1"));
        String strMessage2 = getText(getObject("launch.txtBYBMessegae2"));
        waitForPageLoaded();
        isElementDisplayedWithLog(getObject("launch.txtBYBMessegae1"),
                "Message 1 : " + strMessege1, "Your User ID", 5);
        isElementDisplayedWithLog(getObject("launch.txtBYBMessegae2"),
                "Message 2 : " + strMessage2, "Your User ID", 5);

        if (strHeadTitle.equals("Before you begin")) {
            report.updateTestLog("BeforeYourBegin", "BeforeYourBegin Page launched successfully", Status_CRAFT.PASS);
            clickJS(getObject("launch.btnContinue"), "Continue");
        } else {
            report.updateTestLog("BeforeYourBegin", "BeforeYourBegin Page  not launched successfully", Status_CRAFT.FAIL);
        }
    }


    //Dont Know your 365 user ID?
    public void userID_links() throws Exception {
        WebElement el_links = driver.findElementByXPath("//a[contains(@class,'resetFocus boi-device-italic-blue-button')]/span");
        if (el_links.getText().equals("Don't know your user ID?")) {
            el_links.click();
            report.updateTestLog("userID_links", "userID_links present successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("userID_links", "userID_links present successfully", Status_CRAFT.FAIL);
        }

        WebElement el_header = driver.findElementById("C1__HEAD_67001D45209E1F4A265361");
        if (el_header.getText().contains("Don't know your 365 user ID?")) {
            report.updateTestLog("userID_links_Header", "userID_links_Header present as per wireframe", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("userID_links_Header", "userID_links_Header present as per wireframe", Status_CRAFT.FAIL);
        }

        clickJS(getObject("sca.overlayClose"), "Closing the overlay on User ID");


    }

    public void verifySecurityConcerns() throws Exception {
        clickJS(getObject("sca.securityconcerns"), "Security Concerns link");
        if (isElementDisplayed(By.xpath("//*[text()='Security concerns']"), 3)) {
            String header = driver.findElement(By.xpath("//*[text()='Security concerns']")).getText();
            report.updateTestLog("verifySecurityConcerns", "SecurityConcerns clicked successfully with header::" + header, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifySecurityConcerns", "SecurityConcerns not clicked successfully", Status_CRAFT.PASS);
        }

        if (isElementDisplayed(getObject("sca.crossButton"), 3)) {
            clickJS(getObject("sca.crossButton"), "Closing security concerns overlay");
            report.updateTestLog("SecurityConcernsClose", "SecurityConcerns overlay closed successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SecurityConcernsClose", "SecurityConcerns overlay  not closed successfully", Status_CRAFT.PASS);
        }

    }

    public void verifyForgetPINLinks() throws Exception {
        driver.hideKeyboard();
        clickJS(getObject("sca.forgetPINlink"), "Forget PIN link");
        if (isElementDisplayed(By.xpath("//span[text()='Forgot your PIN']"), 3)) {
            String header = driver.findElement(By.xpath("//span[text()='Forgot your PIN']")).getText();
            report.updateTestLog("verifyForgetPINLinks", "Forget PIN link clicked successfully with header::" + header, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyForgetPINLinks", "Forget PIN link  not clicked successfully", Status_CRAFT.PASS);
        }

        if (isElementDisplayed(getObject("sca.cross"), 3)) {
            clickJS(getObject("sca.cross"), "Closing Forget PIN link overlay");
            report.updateTestLog("ForgetPINLinksClosed", "Forget PIN link overlay closed successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("ForgetPINLinksClosed", "Forget PIN link overlay  not closed successfully", Status_CRAFT.PASS);
        }

    }


    public void verifyAnotherUserIDLink() throws Exception {

        if (isElementDisplayed(getObject("sca.AnotherIdlink"), 3)) {
            clickJS(getObject("sca.AnotherIdlink"), "Another User Id link");
            report.updateTestLog("verifyAnotherUserIDLinks", " Another User Id link clicked successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAnotherUserIDLinks", "Another User Id link link  not clicked successfully", Status_CRAFT.FAIL);
        }
    }


    public void beforeYouBeginLinks() throws Exception {
        //links and copy head
        if (getText(getObject("sca.beforeYorBeginLinks")).contains("Details on how to register can be found here.")) {
            clickJS(getObject("sca.beforeYorBeginLinks"), "Before you Begin Links");
            report.updateTestLog("BeforeYourBegin_Link", "BeforeYourBegin_Link clicked successfully", Status_CRAFT.PASS);
        } else if (isElementDisplayed(getObject("sca.noActivatedMobileNumber"), 3)) {
            report.updateTestLog("NoActivated_HeaderOverlay", "NoActivated_HeaderOverlay present", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("BeforeYourBegin_Link", "BeforeYourBegin_Link not clicked successfully", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("sca.registeredScreenonBeforeScreen"), 4)) {
            report.updateTestLog("BeforeYourBegin_Link_HeaderOverlay", "BeforeYourBegin_Link_HeaderOverlay present", Status_CRAFT.PASS);
        } else if (isElementDisplayed(getObject("sca.noActivatedMobileNumber"), 3)) {
            report.updateTestLog("NoActivated_HeaderOverlay", "NoActivated_HeaderOverlay present", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("HeaderOverlay", "HeaderOverlay not present", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("sca.registeredScreenonBeforeScreenBody1"), 2)) {
            report.updateTestLog("BeforeYourBegin_Links_body1", "BeforeYourBegin_Link_HeaderOverlay present", Status_CRAFT.PASS);
        } else if (isElementDisplayed(getObject("sca.registeredScreenForNotActivatedPhone"), 2)) {
            report.updateTestLog("No_registeredScreen_ForNotActivatedPhone", "No_registered_Screen_ForNotActivatedPhone present", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("BeforeYourBegin_Links_body1", "BeforeYourBegin_Link_HeaderOverlay not present", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("sca.registeredScreenSubHeading"), 4)) {
            report.updateTestLog("BeforeYourBegin_Link_registeredScreenSubHeading", "registered_Screen_SubHeading  present", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("registeredScreenSubHeading", "registeredScreenSubHeading not present", Status_CRAFT.FAIL);
        }
        //PhoneNumbers and country validation

        List<WebElement> Country = findElements(getObject("sca.listCountry"));
        List<WebElement> phonenNumbers = findElements(getObject("sca.listPhoneNumbers"));
        for (int i = 0; i < Country.size(); i++) {
            String a = Country.get(i).getText();
            if (Country.get(i).getText().equals("Republic of Ireland") || Country.get(i).getText().equals("Northern Ireland and Great Britain") || Country.get(i).getText().equals("Other locations")) {
                report.updateTestLog("BeforeYourBegin_Link_Country", "BeforeYourBegin_Link_Country present correctly", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("BeforeYourBegin_Link_Country", "BeforeYourBegin_Link_Country  not present correctly", Status_CRAFT.FAIL);
            }
            if (phonenNumbers.get(i).getText().equals("0818 200 362") || phonenNumbers.get(i).getText().equals("0345 7 365 555") || phonenNumbers.get(i).getText().equals("+ 353 1 460 6410")) {
                report.updateTestLog("BeforeYourBegin_Link_PhoneNumbers", "BeforeYourBegin_Link_PhoneNumbers present correctly", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("BeforeYourBegin_Link_PhoneNumbers", "BeforeYourBegin_Link_PhoneNumbers  not present correctly", Status_CRAFT.FAIL);
            }

        }
        Thread.sleep(2000);
        clickJS(getObject("sca.overlayClose"), "Closing the overlay");
    }

    /*For Blocked Customer during Device Provision*/
    public void verifyblockedUserDp() throws Exception {
        //links and copy head
        if (getText(getObject("sca.blockedUserHeader")).contains("Your 365 account may be blocked")) {
            report.updateTestLog("verifyblockedUserDp", "verifyblockedUserDp header is coming as expected successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyblockedUserDp", "verifyblockedUserDp header is not coming as expected", Status_CRAFT.FAIL);
        }

        if (getText(getObject("sca.blockedUserSubHeader")).equals("You will need to contact 365 phone banking.")) {
            report.updateTestLog("verifyblockedUserDpHeaderOverlay", "verifyblockedUserDpHeaderOverlay present", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyblockedUserDpHeaderOverlay", "verifyblockedUserDpHeaderOverlay not present", Status_CRAFT.FAIL);
        }

        //PhoneNumbers and country validation

        List<WebElement> Country = findElements(getObject("sca.listCountry"));
        List<WebElement> phonenNumbers = findElements(getObject("sca.listPhoneNumbers"));
        for (int i = 0; i < Country.size(); i++) {
            if (Country.get(i).getText().equals("Republic of Ireland") || Country.get(i).getText().equals("Northern Ireland and Great Britain") || Country.get(i).getText().equals("Other locations")) {
                report.updateTestLog("verifyblockedUserDp_Country", "verifyblockedUserDp_Country present correctly", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyblockedUserDp_Country", "verifyblockedUserDp_Country  not present correctly", Status_CRAFT.FAIL);
            }
            if (phonenNumbers.get(i).getText().equals("0818 200 362") || phonenNumbers.get(i).getText().equals("0345 7 365 555") || phonenNumbers.get(i).getText().equals("+ 353 1 460 6410")) {
                report.updateTestLog("verifyblockedUserDp_PhoneNumbers", "verifyblockedUserDp_PhoneNumbers present correctly", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyblockedUserDp_PhoneNumbers", "verifyblockedUserDp_PhoneNumbers  not present correctly", Status_CRAFT.FAIL);
            }

        }
        Thread.sleep(2000);
        clickJS(getObject("sca.overlayClose"), "Closing the overlay");
    }


    /*
     * Update on     Updated by     Reason
     * 06/06/2019     C966003       Written code as per framework standard
     */
    public void userIDpage() throws Exception {
        String userID = dataTable.getData("Login_Data", "Username");
        waitForJQueryLoad();waitForJQueryLoad();
        sendKeys(getObject("launch.edtUserID"), userID);
        report.updateTestLog("Device Provisioning - User ID", "User ID Entered" + userID + "", Status_CRAFT.DONE);
        clickJS(getObject("launch.btnContinue"), "Continue");
        report.updateTestLog("Device Provisioning - User ID", "Device Provisioning - User ID launched successfully", Status_CRAFT.PASS);
    }

    public void userIDpagevalidations() throws Exception {
        String userID = dataTable.getData("Login_Data", "Username");

        waitForPageLoaded();
        isElementDisplayedWithLog(getObject("launch.txtBack"),
                "Back button", "Your User ID", 5);
        isElementDisplayedWithLog(getObject("launch.progressBar"),
                "Progress bar", "Your User ID", 5);
        isElementDisplayedWithLog(getObject("launch.txtStep1to5"),
                "Step heading 'Step 1 of 5'", "Your User ID", 5);
        isElementDisplayedWithLog(getObject("launch.txtPageHeading"),
                "Page heading 'Your user ID'", "Your User ID", 5);
        isElementDisplayedWithLog(getObject("launch.txtHeadTextMsgUserID"),
                "Head text message : " + getText(getObject("launch.txtHeadTextMsgUserID")), "Your User ID", 5);
        isElementDisplayedWithLog(getObject("launch.lblLabelEnterUserID"),
                "Label 'Enter your user ID'", "Your User ID", 5);

        sendKeys(getObject("launch.edtUserID"), userID);
        clickJS(getObject("launch.btnContinue"), "Continue on User ID page");
        report.updateTestLog("Device Provisioning - User ID", "Device Provisioning - User ID launched successfully", Status_CRAFT.PASS);
    }

    /*
     * Update on     Updated by     Reason
     * 06/06/2019     C966003       Written code as per framework standard
     */
    public void DOB() throws Exception {

        String strSavingDate = dataTable.getData("Login_Data", "DOB");

//        while (isElementDisplayed(getObject("launch.spinSpinner"), 3)) {
//            waitForPageLoaded();
//        }
        if (isElementDisplayed(getObject("launch.edtDay"), 2)) {
            sendKeys(getObject("launch.edtDay"), strSavingDate.split("/")[0]);
            sendKeys(getObject("launch.edtMonth"), strSavingDate.split("/")[1]);
            sendKeys(getObject("launch.edtYear"), strSavingDate.split("/")[2]);
        } else {
            sendKeys(getObject("launch.edtEnterYourDateOfBirth"), strSavingDate);
        }

        clickJS(getObject("launch.btnContinue") , "DOB Continue button");

    }

    public void DOBvalidations() throws Exception {

        String strSavingDate = dataTable.getData("Login_Data", "DOB");

        while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
            waitForPageLoaded();
        }

        isElementDisplayedWithLog(getObject("launch.txtBack"),
                "Back button", "Enter your date of birth", 5);
        isElementDisplayedWithLog(getObject("launch.progressBar"),
                "Progress bar", "Enter your date of birth", 5);
        isElementDisplayedWithLog(getObject("launch.txtStep2to5"),
                "Step heading 'Step 2 of 5'", "Enter your date of birth", 5);
        isElementDisplayedWithLog(getObject("launch.txtHeadDateOfBirth"),
                "Page heading 'Enter your date of birth'", "Enter your date of birth", 5);
        isElementDisplayedWithLog(getObject("launch.lblDateOfBirth"),
                "Label 'Date of birth'", "Enter your date of birth", 5);

        if (isElementDisplayed(getObject("launch.edtDay"), 2)) {
            sendKeys(getObject("launch.edtDay"), strSavingDate.split("/")[0]);
            sendKeys(getObject("launch.edtMonth"), strSavingDate.split("/")[1]);
            sendKeys(getObject("launch.edtYear"), strSavingDate.split("/")[2]);
        } else {
            sendKeys(getObject("launch.edtEnterYourDateOfBirth"), strSavingDate);
        }

        clickJS(getObject("launch.btnContinue"), "Continue on DOB screen");
        report.updateTestLog("Device Provisioning - DOB", "Device Provisioning -  DOB launched successfully", Status_CRAFT.PASS);
    }

    /*
     * Update on     Updated by     Reason
     * 06/06/2019     C966003       Written code as per framework standard
     */
    public void PINpage() throws Exception {

        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        String strObject = "";

        while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
            waitForPageLoaded();
        }
        if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"), 5)) {
            strObject = "launch.edtPinEnterFieldGrpSCA";
            report.updateTestLog("PIN input", ":: PIN input ::", Status_CRAFT.DONE);
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
        driver.hideKeyboard();
        clickJS(getObject("launch.btnContinue"),"Continue Button");
    }

    public void PINpagevalidations() throws Exception {

        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        String strObject = "";

        while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
            waitForPageLoaded();
        }
        isElementDisplayedWithLog(getObject("launch.txtBack"),
                "Back button", "PIN", 5);
        isElementDisplayedWithLog(getObject("launch.progressBar"),
                "Progress bar", "PIN", 5);
        isElementDisplayedWithLog(getObject("launch.txtStep3to5"),
                "Step heading 'Step 3 of 5'", "PIN", 5);
        isElementDisplayedWithLog(getObject("launch.txtPIN"),
                "Page heading 'PIN'", "PIN", 5);
        isElementDisplayedWithLog(getObject("launch.txtHeadTxtMsgPIN"),
                "Head text message :" + getText(getObject("launch.txtHeadTxtMsgPIN")), "PIN", 5);
        isElementDisplayedWithLog(getObject("launch.lnkForgotYourPIN"),
                "Link 'Forgot your PIN'", "PIN", 5);

        if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"), 5)) {
            strObject = "launch.edtPinEnterFieldGrpSCA";
            report.updateTestLog("PIN input", ":: PIN input ::", Status_CRAFT.DONE);
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

        clickJS(getObject("launch.btnContinue"), "Continue on 3-6 PIN");
        report.updateTestLog("Device Provisioning - PIN ", "Device Provisioning -PIN page launched successfully", Status_CRAFT.PASS);
    }

    /*
     * Update on     Updated by     Reason
     * 06/06/2019     C966003       Written code as per framework standard
     */
    public void notificationPage() throws Exception {

        while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
            waitForPageLoaded();
        }
        click(getObject("launch.btnAllowNotification"));
        report.updateTestLog("notificationPage", "Allow Notifications Granted", Status_CRAFT.SCREENSHOT);

    }

    public void notificationPagevalidations() throws Exception {

        while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
            waitForPageLoaded();
        }

        isElementDisplayedWithLog(getObject("launch.txtBack"),
                "Back button", "Notifications", 5);
        isElementDisplayedWithLog(getObject("launch.progressBar"),
                "Progress bar", "Notifications", 5);
        isElementDisplayedWithLog(getObject("launch.txtStep4to5"),
                "Step heading 'Step 4 of 5'", "Notifications", 5);
        isElementDisplayedWithLog(getObject("launch.txtHeadNotification"),
                "Page heading 'Notifications'", "Notifications", 5);
        isElementDisplayedWithLog(getObject("launch.txtCopyHeadNotification1"),
                "Copy head 1 'We now use Strong Customer Authentication (S C A).'", "Notifications", 5);
        //        isElementDisplayedWithLog(getObject("launch.txtCopyHeadNotification2"),
//                "Copy head 2 'Why must you allow these?'", "Notifications", 3);
//        isElementDisplayedWithLog(getObject("launch.txtCopyHeadNotification3"),
//                "Copy head 3 'Examples of how we’ll use notifications'", "Notifications", 3);
        isElementDisplayedWithLog(getObject("launch.txtCopyHeadNotification3"),"Please note section content : " + getText(getObject("launch.txtCopyHeadNotification3")), "Notifications" , 5);
        clickJS(getObject("launch.btnAllowNotification"), "Continue on Step 4 of 5");
        report.updateTestLog("Device Provisioning - Notification Page", "Device Provisioning - Notification Page launched successfully", Status_CRAFT.PASS);
    }

    /*
     * Update on     Updated by     Reason
     * 06/06/2019     C966003       Written code as per framework standard
     */
    public void locationServicePage() throws Exception {

        while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
            waitForPageLoaded();
        }
        click(getObject("launch.btnContinue"));
        report.updateTestLog("locationServicePage", "Location Service Page Accepted", Status_CRAFT.SCREENSHOT);
    }

    public void locationServicePagevalidations() throws Exception {

        while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
            waitForPageLoaded();
        }

        isElementDisplayedWithLog(getObject("launch.txtBack"),
                "Back button", "Location services", 5);
        isElementDisplayedWithLog(getObject("launch.progressBar"),
                "Progress bar", "Location services", 5);
        isElementDisplayedWithLog(getObject("launch.txtStep5to5"),
                "Step heading 'Step 5 of 5'", "Location services", 5);
        isElementDisplayedWithLog(getObject("launch.txtLocationServices"),
                "Page heading 'Location services'", "Location services", 5);
        isElementDisplayedWithLog(getObject("launch.txtBodyHeadMsgLocSer"),
                "Body head : " + getText(getObject("launch.txtBodyHeadMsgLocSer")), "Location services", 5);

        clickJS(getObject("launch.btnContinue"), "Continue on Step 5 of 5");
        report.updateTestLog("Device Provisioning - Location Service Page", "Device Provisioning - Location Service Page launched successfully", Status_CRAFT.PASS);
    }

    /*
     * Update on     Updated by     Reason
     * 06/06/2019     C966003       Written code as per framework standard
     */
    public void registerDevice() throws Exception {

        while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
            waitForPageLoaded();
        }
        click(getObject("launch.btnRegisterYourDevice"));

    }

    public void registerDevicevalidations() throws Exception {

        while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
            waitForPageLoaded();
        }

        isElementDisplayedWithLog(getObject("launch.txtBack"),
                "'Back' button", "Register your device", 5);
        isElementDisplayedWithLog(getObject("launch.txtHeadRegisterYourDevice"),
                "Head title : " + getText(getObject("launch.txtHeadRegisterYourDevice")), "Register your device", 5);
        isElementDisplayedWithLog(getObject("launch.txtBodyMsgRegisterYourDevice1"),
                "Body message 1 : " + getText(getObject("launch.txtBodyMsgRegisterYourDevice1")), "Register your device", 5);
        isElementDisplayedWithLog(getObject("launch.txtBodyMsgRegisterYourDevice2"),
                "Body message 2 : " + getText(getObject("launch.txtBodyMsgRegisterYourDevice2")), "Register your device", 5);
        report.updateTestLog("Device Provisioning - Register Device Page", "Device Provisioning - Register Device page launched successfully", Status_CRAFT.PASS);
        clickJS(getObject("launch.btnRegisterYourDevice"), "Continue on Register this device screen");

    }

    /*
     * Update on     Updated by     Reason
     * 06/06/2019     C966003       Written code as per framework standard
     */
    public void inviteCodeScreen() throws Exception {
        mobileProperties = Settings.getMobilePropertiesInstance();

        while (isElementDisplayed(getObject("launch.spinSpinner"),4)) {
            waitForPageLoaded();
        }
        String strMobNumEndingDigits = getText(getObject("xpath~//*[contains(text(),'ending in ')]")).split("ending in ")[1].substring(0,4);
        if(mobileProperties.getProperty("PickCodeFromEngageHub").equalsIgnoreCase("true")){
            sendKeys(getObject("launch.txtInvitationCode"),getActivationCodeFromEngageHub(strMobNumEndingDigits));
        };
        String strCaptureInvitationCode = fetchAttribute(getObject("launch.txtInvitationCode"), "value");

        if (dataTable.getData("General_Data", "ErrorText").equalsIgnoreCase("Yes")) {
            sendKeys(getObject("launch.txtInvitationCode"), "3fst$5488");
            click(getObject("launch.btnContinue"));
            isElementDisplayedWithLog(getObject("launch.errInvalidInvitationCode"),
                    "Error message for invalid invitation code : " + getText(getObject("launch.errInvalidInvitationCode")), "Activation code", 5);
            sendKeys(getObject("launch.txtInvitationCode"), strCaptureInvitationCode);
        }

        waitForElementToClickable(getObject("launch.btnContinue"),60);
        Thread.sleep(1000);
        clickJS(getObject("launch.btnContinue"),"Continue");
        waitForElementPresent(getObject("launch.txtNameYourDevice"), 15);
    }

    public void inviteCodeScreenvalidations() throws Exception {

        while (isElementDisplayed(getObject("launch.spinSpinner"), 18)) {
            waitForPageLoaded();
        }

        String strCaptureInvitationCode = fetchAttribute(getObject("launch.txtInvitationCode"), "value");

        isElementDisplayedWithLog(getObject("launch.txtHeadActivationCode"),
                "Head :" + getText(getObject("launch.txtHeadActivationCode")), "Activation code", 5);
        isElementDisplayedWithLog(getObject("launch.txtHeadMsgActivationCode1"),
                "Head message 1 : " + getText(getObject("launch.txtHeadMsgActivationCode1")), "Activation code", 5);
        isElementDisplayedWithLog(getObject("launch.txtHeadMsgActivationCode2"),
                "Head message 2 : " + getText(getObject("launch.txtHeadMsgActivationCode2")), "Activation code", 5);
        isElementDisplayedWithLog(getObject("launch.lblActivaitionCode"),
                "Label 'Activation code'", "Activation code", 5);
        isElementDisplayedWithLog(getObject("launch.txtInvitationCode"),
                "Invitation code : " + strCaptureInvitationCode, "Activation code", 5);

        if (dataTable.getData("General_Data", "ErrorText").equalsIgnoreCase("Yes")) {
            sendKeys(getObject("launch.txtInvitationCode"), "3fst$5488");
            click(getObject("launch.btnContinue"));
            isElementDisplayedWithLog(getObject("launch.errInvalidInvitationCode"),
                    "Error message for invalid invitation code : " + getText(getObject("launch.errInvalidInvitationCode")), "Activation code", 5);
            sendKeys(getObject("launch.txtInvitationCode"), strCaptureInvitationCode);
        }

        clickJS(getObject("launch.btnContinue"), "Continue on ActivationCode");
        report.updateTestLog("Device Provisioning - Invitation Code", "Device Provisioning -Invitation Code launched successfully", Status_CRAFT.PASS);
        waitForElementPresent(getObject("launch.txtNameYourDevice"), 15);
    }


    /*
     * Update on     Updated by     Reason
     * 06/06/2019     C966003       Written code as per framework standard
     */
    public void deviceName() throws Exception {

        while (isElementDisplayed(getObject("launch.spinSpinner"), 6)) {
            waitForPageLoaded();
        }
        String strRegisteredDevice =dataTable.getData("Login_Data","DeviceName");
        sendKeys(getObject("launch.edtDeviceName"), strRegisteredDevice);
        clickJS(getObject("launch.btnContinue"), "Continue");
    }

    public void deviceNickNameDuplicateName_validation() throws Exception {

        String strDeviceName = dataTable.getData("Login_Data", "DeviceName");
        while (isElementDisplayed(getObject("launch.spinSpinner"), 8)) {
            waitForPageLoaded();
        }

        int intUiMaxlength = Integer.parseInt(driver.findElement(getObject("launch.edtDeviceName")).getAttribute("maxLength"));
        if (intUiMaxlength==16) {
            report.updateTestLog("verifyMaxLength", "Expected maxlength '" + intUiMaxlength + "' of the input/textbox matches with actual '" + intUiMaxlength + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyMaxLength", "Expected maxlength '" + intUiMaxlength + "' of the input/textbox does not match with actual '" + intUiMaxlength + "'", Status_CRAFT.FAIL);
        }

        sendKeys(getObject("launch.edtDeviceName"), ":)");
        clickJS(getObject("launch.btnContinue"), "Continue");
        Thread.sleep(2000);
        WebElement emot= driver.findElement(By.xpath("//span[contains(@class,'boi_input_sm_error tc-error')][text()='Sorry, you can’t use special characters or emoji’s here.']"));
        if(emot.getText().equals("Sorry, you can’t use special characters or emoji’s here."))
        report.updateTestLog("Device Provisioning - Device Name Page Duplication Validation EMOTIONS", "Device Provisioning - Device Name Page Duplication validition successfully", Status_CRAFT.PASS);
        else{
            report.updateTestLog("Device Provisioning - Device Name Page Duplication Validation EMOTIONS", "Device Provisioning - Device Name Page Duplication  validition not done successfully", Status_CRAFT.FAIL);
        }

        sendKeys(getObject("launch.edtDeviceName"), strDeviceName);
        clickJS(getObject("launch.btnContinue"), "Continue");
        WebElement duplicate = driver.findElement(By.xpath("//span[contains(@class,'boi_input_sm_error tc-error')][text()='You already have a device with this nickname. Please choose another.']"));
        while (isElementDisplayed(getObject("launch.spinSpinner"), 5)) {
            waitForPageLoaded();
        }
        if(duplicate.getText().equals("You already have a device with this nickname. Please choose another."))
            report.updateTestLog("Device Provisioning - Device Name Page Duplication Validation", "Device Provisioning - Device Name Page Duplication validition successfully", Status_CRAFT.PASS);
        else{
            report.updateTestLog("Device Provisioning - Device Name Page Duplication Validation", "Device Provisioning - Device Name Page Duplication  validition not done successfully", Status_CRAFT.FAIL);
        }
    }

    public void deviceNamevalidations() throws Exception {

        while (isElementDisplayed(getObject("launch.spinSpinner"), 8)) {
            waitForPageLoaded();
        }

        isElementDisplayedWithLog(getObject("launch.txtNameYourDevice"),
                "Header : " + getText(getObject("launch.txtNameYourDevice")), "Name your device", 5);
        isElementDisplayedWithLog(getObject("launch.txtHeadMsgNameYourDevice"),
                "Head message : " + getText(getObject("launch.txtHeadMsgNameYourDevice")),
                "Name your device", 5);
        isElementDisplayedWithLog(getObject("launch.lblNameYourDevice"),
                "Label 'Name'", "Name your device", 5);

        sendKeys(getObject("launch.edtDeviceName"), "MAutoDevice123");
        clickJS(getObject("launch.btnContinue"), "Continue");
        report.updateTestLog("Device Provisioning - Device Name Page", "Device Provisioning - Device Name Page launched successfully", Status_CRAFT.PASS);

    }


    /*
     * Update on     Updated by     Reason
     * 08/06/2019     C966003       Written code as per framework standard
     */
    public void allDone() throws Exception {
        clickJS(getObject("launch.btnContinueToLoginDone"), "Continue to login");
    }

    public void allDonevalidations() throws Exception {

        isElementDisplayedWithLog(getObject("launch.txtHeadBOILogo"),
                "Bank Of Ireland logo", "Done", 5);
        isElementDisplayedWithLog(getObject("launch.imgSuccess"),
                "Success image", "Done", 5);
        isElementDisplayedWithLog(getObject("launch.txtDone"),
                "Head 'Done'", "Done", 5);
//        isElementDisplayedWithLog(getObject("launch.txtBodyMsgDone"),
//                "Done text", "Done", 5);
        isElementDisplayedWithLog(getObject("launch.txtBodyMsgDone"),
                "Body message : " + getText(getObject("launch.txtBodyMsgDone")), "Done", 5);
        isElementDisplayedWithLog(getObject("launch.btnContinueToLoginDone"),
                "Continue to login button", "Done", 5);
        clickJS(getObject("launch.btnContinueToLoginDone"), "Continue to login");
        report.updateTestLog("Device Provisioning - allDonevalidations", "Device Provisioning - Device Provision successfully", Status_CRAFT.PASS);
    }

    /*
     * Update on     Updated by     Reason
     * 07/06/2019     C966003       Written code as per framework standard
     */
    public void errorCaptureMobileNotRegistr() throws Exception {
        while (isElementDisplayed(getObject("launch.spinSpinner"), 5)) {
            waitForPageLoaded();
        }
        String strErrorMobileNotRegisterd = getText(getObject("launch.errMsgMobileNotRegistered"));
        String strPhoneBankingNo = getText(getObject("launch.txtPhoneBanking"));
        isElementDisplayedWithLog(getObject("launch.txtNoActivatedMobileNo"),
                "Header title : " + getText(getObject("launch.txtNoActivatedMobileNo")), "No activated mobile number", 5);
        isElementDisplayedWithLog(getObject("launch.errMsgMobileNotRegistered"),
                "Alert error message : " + strErrorMobileNotRegisterd, "No activated mobile number", 5);
        isElementDisplayedWithLog(getObject("launch.txtPhoneBanking"),
                "Phone banking contacts : " + strPhoneBankingNo, "No activated mobile number", 5);
    }


    /*
     * Update on     Updated by     Reason
     * 07/06/2019     C966003       Written code as per framework standard
     */
    public void pinPage_login() throws Exception {
        waitForJQueryLoad();
        while (isElementDisplayed(getObject("launch.spinSpinner"), 10)) {
            waitForPageLoaded();
        }
        waitForJQueryLoad();
        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        String strObject = "";

        isElementDisplayedWithLog(getObject("xpath~//*[text()='Your 365 PIN']"),
                "Head : " + getText(getObject("xpath~//*[text()='Your 365 PIN']")), "Your 365 PIN", 5);
        isElementDisplayedWithLog(getObject("launch.txtHeadTxtMsgPIN"),
                "Body text : " + getText(getObject("launch.txtHeadTxtMsgPIN")), "Your 365 PIN", 5);
        isElementDisplayedWithLog(getObject("xpath~//*[@class='boi_link boi-sca-mt-6']"),
                "Link " + getText(getObject("xpath~//*[@class='boi_link boi-sca-mt-6']")), "Your 365 PIN", 5);

        if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"), 5)) {
            strObject = "launch.edtPinEnterFieldGrpSCA";
            report.updateTestLog("PIN input", ":: PIN input ::", Status_CRAFT.DONE);
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

        click(getObject("w.home.btnLogin"));

    }

    /*
     * Update on     Updated by     Reason
     * 07/06/2019     C966003       Written code as per framework standard
     */
    public void validateHomepage() throws Exception {
        if (isElementDisplayed(getObject("launch.txtHeaderTerms&Cond"), 4)) {
            if (getText(getObject("launch.txtHeaderTerms&Cond")).contains("Terms and Conditions")) {
                click(getObject("launch.btnContinue"));
            }
        }

        while (isElementDisplayed(getObject("launch.spinSpinner"), 2)) {
            waitForPageLoaded();
        }

        isElementDisplayedWithLog(getObject("launch.txtWelcomMsg"),
                "Homepage welcome message : " + getText(getObject("launch.txtWelcomMsg")), "Homepage", 5);
    }

    public void deviceProvision_negative() {
        MobileElement el1 = (MobileElement) driver.findElementById("C1__BUT_D053A6694904EE05274868");
        if (el1.isDisplayed()) {
            el1.click();
            report.updateTestLog("RegisterLink", "RegisterLink  clicked successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("RegisterLink", "RegisterLink  not clicked successfully", Status_CRAFT.FAIL);
        }

        //Begin your screen
        MobileElement ell21 = (MobileElement) driver.findElementById("C1__BUT_4D0B5F8F4537CA4D366725");
        if (ell21.isDisplayed()) {
            ell21.click();
            report.updateTestLog("BeforeYourBegin", "BeforeYourBegin  clicked successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("BeforeYourBegin", "BeforeYourBegin  not clicked successfully", Status_CRAFT.FAIL);
        }

        //User Id page
        MobileElement el2 = (MobileElement) driver.findElementById("C1__QUE_C6CBE7CE8DAFADC54357632");
        if (el2.isDisplayed()) {
            el2.sendKeys("155555");
            MobileElement el3 = (MobileElement) driver.findElementById("C1__BUT_C6CBE7CE8DAFADC54357636");
            el3.click();
            report.updateTestLog("UserId_Filled", "UserId_Filled successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("UserId_Filled", "UserId_Filled not successfully", Status_CRAFT.FAIL);
        }

        //DOB page
        MobileElement el4 = (MobileElement) driver.findElementById("C1__QUE_AA79D2D74268369D581259");
        if (el4.isDisplayed()) {
            el4.sendKeys("01/01/1980");
            driver.hideKeyboard();
            MobileElement el5 = (MobileElement) driver.findElementById("C1__BUT_C6CBE7CE8DAFADC54357636");
            el5.click();
            report.updateTestLog("DOB", "DOB  clicked successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("DOB", "DOB  not clicked successfully", Status_CRAFT.FAIL);
        }


        //PIN page

        //C1__C1__BUT_74588A4EA6E130203242851

        //C1__C1__Authentication-PINPage-Continue
        MobileElement el6 = (MobileElement) driver.findElementById("C1__C1__QUE_188DA78A3B0DB18F2500");
        MobileElement el7 = (MobileElement) driver.findElementById("C1__C1__QUE_188DA78A3B0DB18F2501");
        MobileElement el71 = (MobileElement) driver.findElementById("C1__C1__QUE_188DA78A3B0DB18F2502");
        if (el6.isDisplayed() || el7.isDisplayed()) {
            el6.sendKeys("1");
            el7.sendKeys("2");
            el71.sendKeys("3");
            MobileElement el8 = (MobileElement) driver.findElementById("C1__C1__BUT_74588A4EA6E130203242851");
            el8.click();
            report.updateTestLog("PIN page", "PIN page filled/clicked successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("PIN page", "PIN page filled/clicked not successfully", Status_CRAFT.FAIL);
        }


        MobileElement el8 = (MobileElement) driver.findElementById("C1__HEAD_4FA011E513732BA2455810_");
        if (el8.isDisplayed()) {
            // el8.click();
            String Error = el8.getText();
            report.updateTestLog("RegisterLink", "::::::::Error Message::::::" + Error, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("RegisterLink", "RegisterLink  not clicked successfully", Status_CRAFT.FAIL);
        }

    }

    public void deviceProvisionErrorPIN() throws Exception {
        Thread.sleep(2000);
        MobileElement el8 = (MobileElement) driver.findElementById("C1__HEAD_4FA011E513732BA2455810_");
        if (el8.isDisplayed()) {
            // el8.click();
            String Error = el8.getText();
            report.updateTestLog("RegisterLink", "::::::::Error Message::::::" + Error, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("RegisterLink", "::::::::Error Message::::::  not clicked successfully", Status_CRAFT.FAIL);
        }
    }

    /*
     * Update on     Updated by     Reason
     * 02/10/2019    C103401       This function will accept push notification for Mobileapp and Desktop Browser.
     */

    public void PushNotification_Acccept() throws Exception {
        //waitForPageLoaded();
        Thread.sleep(1000);
        report.updateTestLog("PushNotifcationScreenShot","Received Push Notifation",Status_CRAFT.SCREENSHOT);
        //waitForElementPresent(getObject("id~com.bankofireland.tcmb:id/acceptSlideButton"), 10);
        //waitForElementToClickable(getObject("id~com.bankofireland.tcmb:id/acceptSlideButton"), 5);
        if (isMobile) {
            driver.context("NATIVE_APP");
            if(isIOS){
                if (isElementDisplayed(getObject("id~Swipe to approve"), 15)) {
                    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                    HashMap<String, String> scrollObject = new HashMap<String, String>();
                    scrollObject.put("direction", "right");
                    scrollObject.put("element", ((RemoteWebElement) driver.findElementByAccessibilityId("Swipe to approve")).getId());
                    js.executeScript("mobile: swipe", scrollObject);

                    report.updateTestLog("PushNotification_Acccept", "Push Notification Accepted", Status_CRAFT.DONE);
                    report.updateTestLog("PushNotification_Acccept", "Push Notification Accepted", Status_CRAFT.SCREENSHOT);
                    changeNativeToWebview();
                } else {
                    report.updateTestLog("PushNotification_Acccept", "Accept Push Notification object is not found on the screen", Status_CRAFT.FAIL);
                }
            }else{
                if (isElementDisplayed(getObject("id~com.bankofireland.tcmb:id/acceptSlideButton"), 10)) {
                    MobileElement el1 = (MobileElement) driver.findElementById("com.bankofireland.tcmb:id/acceptSlideButton");
                    int startX = el1.getLocation().getX();
                    int startY = el1.getLocation().getY();
                    int intWidth = el1.getRect().getWidth();
                    int endX = startX + intWidth;
                    (new TouchAction(driver.getAppiumDriver())).press(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, startY)).release().perform();
                    report.updateTestLog("PushNotification_Acccept", "Push Notification Accepted", Status_CRAFT.DONE);
                    report.updateTestLog("PushNotification_Acccept", "Push Notification Accepted", Status_CRAFT.SCREENSHOT);
                    driver.context("WEBVIEW_com.bankofireland.tcmb");
                } else {
                    report.updateTestLog("PushNotification_Acccept", "Accept Push Notification object is not found on the screen", Status_CRAFT.FAIL);
                }
            }
        } else {
            SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
            waitForPageLoaded();
            waitForJQueryLoad();
            Thread.sleep(5000);
//            sca.PushNotification_Acccept(Mobiledriver);
        }
    }

    public void PushNotification_Decline() throws Exception {
        driver.context("NATIVE_APP");
        if(isIOS){
            if(isElementDisplayed(getObject("id~Double tap to declain"),15)){
                driver.findElementByAccessibilityId("Double tap to declain").click();
                report.updateTestLog("PushNotification_Decline", "Push Notification Declined", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("PushNotification_Decline", "Decline Push Notification object is not found on the screen", Status_CRAFT.FAIL);
            }

        }else{
            if(isElementDisplayed(getObject("id~com.bankofireland.tcmb:id/closeIcon"), 10)) {
                MobileElement el2 = (MobileElement) driver.findElementById("com.bankofireland.tcmb:id/closeIcon");
                el2.click();
                report.updateTestLog("PushNotification_Decline", "Push Notification Declined", Status_CRAFT.PASS);
                driver.context("WEBVIEW_com.bankofireland.tcmb");
            } else {
                report.updateTestLog("PushNotification_Decline", "Decline Push Notification object is not found on the screen", Status_CRAFT.FAIL);
            }
        }

    }

    /***********************************************************************
     * Method Name    : acceptNotificationToAccess
     * Modified By    : C103516
     * Modified On    : 24/06/19
     * Comment       : acceptNotificationToAccess method has been updated for accepting general access which comes in App
     * Input         : No Input Parameters
     * <p>
     * /
     ***********************************************************************/

    public void acceptNotificationToAccess() throws Exception {
        changeWebviewToNative();
        if(isIOS){
            if (isElementDisplayed(getObject("id~Allow"), 5)) {
                MobileElement el1 = (MobileElement) driver.findElementById("Allow");
                el1.click();
                report.updateTestLog("acceptNotificationToAccess", "Allowed  Access", Status_CRAFT.PASS);
//                report.updateTestLog("acceptNotificationToAccess", "Allowed  Access", Status_CRAFT.SCREENSHOT);
            } else {
                report.updateTestLog("acceptNotificationToAccess", "Allowed  Access object is not found on the screen", Status_CRAFT.DONE);
            }

        }else {
            if (isElementDisplayed(getObject("id~com.android.packageinstaller:id/permission_allow_button"), 10)) {
                MobileElement el1 = (MobileElement) driver.findElementById("com.android.packageinstaller:id/permission_allow_button");
                el1.click();
                report.updateTestLog("acceptNotificationToAccess", "Allowed  Access", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("acceptNotificationToAccess", "Allowed  Access object is not found on the screen", Status_CRAFT.DONE);
            }
        }
        report.updateTestLog("acceptNotificationToAccess", "acceptNotificationToAccess", Status_CRAFT.SCREENSHOT);
        changeNativeToWebview();
    }

    /***********************************************************************
     * Method Name    : MobileAppPushNotification
     * Created By    : C103516
     * Created On    : 24/06/19
     * Comment       : Initiate mobile driver to switch control from desktop to mobile
     * Input         : Execution platform
     * <p>
     * /
     ***********************************************************************/

    public AppiumDriver MobileAppPushNotification(String strPlatform) throws Exception {
        mobileProperties = Settings.getMobilePropertiesInstance();
        AppiumDriver MobileDriverApp = null;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("userName",mobileProperties.getProperty("MobileCenterUser"));
        desiredCapabilities.setCapability("password",mobileProperties.getProperty("MobileCenterPassword"));
        desiredCapabilities.setCapability("udid",mobileProperties.getProperty("UDID"));
        desiredCapabilities.setCapability("deviceName",mobileProperties.getProperty("DeviceName"));
        //desiredCapabilities.setCapability("version",mobileProperties.getProperty("version"));
        try {
            switch (strPlatform) {
                case "ANDROID":
                    desiredCapabilities.setCapability("platformName", "Android");
                    desiredCapabilities.setCapability("appPackage",
                            mobileProperties.getProperty("Application_Package_Name"));
                    desiredCapabilities.setCapability("appActivity",
                            mobileProperties.getProperty("Application_MainActivity_Name"));
                    desiredCapabilities.setCapability("automationName", "uiautomator2");
                    try {
                        MobileDriverApp = new AndroidDriver(new URL(mobileProperties.getProperty("MobileCenterHost")),
                                desiredCapabilities);
                        System.out.println("Connection Established between Mobile Center Connector and Android Mobile Device");
                    } catch (MalformedURLException e) {
                        throw new FrameworkException(
                                "The android driver invokation has problem, please re-check the capabilities and check the mobileCenter details URL, Username and Password ");
                    }

                    break;

                case "IOS":

                    desiredCapabilities.setCapability("platformName", "iOS");
                    desiredCapabilities.setCapability("udid", mobileProperties.getProperty("UDID"));
                    desiredCapabilities.setCapability("deviceName", mobileProperties.getProperty("deviceName"));
                    desiredCapabilities.setCapability("newCommandTimeout", 120);
                    desiredCapabilities.setCapability("bundleId", mobileProperties.getProperty("iPhoneBundleID"));
                    desiredCapabilities.setCapability("automationName", "iOS");


                    try {
                        MobileDriverApp = new IOSDriver(new URL(mobileProperties.getProperty("MobileCenterHost")),
                                desiredCapabilities);
                        System.out.println("Connection Established between Mobile Center Connector and IOS Mobile Device");

                    } catch (MalformedURLException e) {
                        throw new FrameworkException(
                                "The IOS driver invokation has problem, please re-check the capabilities and check the mobileCenter details URL, Username and Password ");
                    }
                    break;

                default:
                    throw new FrameworkException("Unhandled ExecutionMode!");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new FrameworkException(
                    "The mobileCenter appium driver invocation created a problem , please check the capabilities");
        }
        return MobileDriverApp;

    }

    /***********************************************************************
     * Method Name    : PushNotification_Acccept (method overloaded)
     * Created By    : C103516
     * Created On    : 24/06/19
     * Comment       : To Accept push notification in mobile device which triggered from browser
     * Input         : AppiumDriver ( mobile driver which was initiated)
     * <p>
     * /
     ***********************************************************************/

    public void PushNotification_Acccept(AppiumDriver Mobdriver) throws Exception {
        System.out.println("Control has been switched to device");
        Mobdriver.context("NATIVE_APP");
            //Thread.sleep(4000);
        if (Mobdriver.findElementById("com.bankofireland.tcmb:id/acceptSlideButton").isDisplayed()) {
           // Thread.sleep(4000);
            System.out.println("Push displayed");
            MobileElement el1 = (MobileElement) Mobdriver.findElementById("com.bankofireland.tcmb:id/acceptSlideButton");
            int startX = el1.getLocation().getX();
            int startY = el1.getLocation().getY();
            int intWidth = el1.getRect().getWidth();
            int endX = startX + intWidth;
            (new TouchAction(Mobdriver)).press(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, startY)).release().perform();
            report.updateTestLog("PushNotification_Acccept", "Push Notification Accepted from Mobile", Status_CRAFT.PASS);
            System.out.println("Push Accepted");
        } else {
            report.updateTestLog("PushNotification_Acccept", "Accept Push Notification object is not found on the screen", Status_CRAFT.FAIL);
        }

     //   Mobdriver.quit();

    }

    /***********************************************************************
     * Method Name    : invalidErrors for UserID
     * Modified By    : C100136
     * Modified On    : 28/06/19
     * Comment       : For Checking the inline errors
     * Input         : No Input Parameters
     * <p>
     * /
     ***********************************************************************/

    public void invalidErrors_UserID() throws Exception {
        String expError = dataTable.getData("General_Data", "ErrorText");
        String invalidData = dataTable.getData("Login_Data", "Username");
        if (isElementDisplayed(getObject("launch.edtUserID"), 3)) {
            sendKeys(getObject("launch.edtUserID"), invalidData);
            click(getObject("launch.btnContinue"));
            if (getText(getObject("sca.invalidErrors_UserID")).equals(expError)) {
                report.updateTestLog("invalidErrors_UserID", "invalid input is given and error is coming as expected ", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("invalidErrors_UserID", "invalid input is given and error is not coming as expected ", Status_CRAFT.FAIL);
        }
    }


    /***********************************************************************
     * Method Name    : invalidErrors for DOB
     * Modified By    : C100136
     * Modified On    : 28/06/19
     * Comment       : For Checking the inline errors
     * Input         : No Input Parameters
     * <p>
     * /
     ***********************************************************************/

    public void invalidErrors_DOB() throws Exception {
        String expError = dataTable.getData("General_Data", "ErrorText");
        String strSavingDate = dataTable.getData("Login_Data", "DOB");
        if (isElementDisplayed(getObject("launch.edtDay"), 2)) {
            sendKeys(getObject("launch.edtDay"), strSavingDate.split("/")[0]);
            sendKeys(getObject("launch.edtMonth"), strSavingDate.split("/")[1]);
            sendKeys(getObject("launch.edtYear"), strSavingDate.split("/")[2]);
            if (getText(getObject("sca.invalidErrors_DOB")).equals(expError)) {
                Thread.sleep(2000);
                report.updateTestLog("invalidErrors_DOB", "invalid input is given and error is coming as expected ", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("invalidErrors_DOB", "invalid input is given and error is not coming as expected ", Status_CRAFT.FAIL);
        }

    }

    public void setRelevantWebViewTab() throws Exception {

        boolean bflag = false;
//        driver.context("WEBVIEW_com.bankofireland.tcmb");
//        String myView = "hjd";
//        driver.context(myView);
        changeNativeToWebview();
        try {
            Iterable<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                driver.switchTo().window(windowHandle);
                if (driver.getTitle().equals("Bank of Ireland") || driver.getTitle().equals("Temenos")||driver.getTitle().contains("Bank of Ireland")) {
                    bflag = true;
                    report.updateTestLog("Verify PIN digits null validation", "WebView with browser title 'Bank of Ireland' set", Status_CRAFT.DONE);
                    break;
                }
            }
            if (!bflag) {
                report.updateTestLog("Verify PIN digits null validation", "WebView with browser title 'Bank of Ireland' not found", Status_CRAFT.DONE);
            }

        } catch (Exception e) {
            report.updateTestLog("Verify PIN digits null validation", "WebView with browser title 'Bank of Ireland' not found", Status_CRAFT.DONE);
        }
    }

    public void functionToHandleLogin() throws Exception {
        waitForPageLoaded();
        waitForJQueryLoad();
        FunctionalComponents FC = new FunctionalComponents(scriptHelper);
        if(isElementDisplayed(getObject("launch.LoginHeading"),9)){
        	report.updateTestLog("UserSelection", "Multiple user profiles page", Status_CRAFT.SCREENSHOT);
            String userID = dataTable.getData("Login_Data", "Username");
            boolean bflag = false;
            List<WebElement> elm = driver.findElements(getObject("launch.userlist"));
            for (int i = 0; i < elm.size(); i++) {
                String ID = elm.get(i).getText();
                if (ID.equals(userID)) {
                    scrollToView(getObject("xpath~//span[text()='" +userID+ "']"));
                    elm.get(i).click();
                    report.updateTestLog("UserSelection","User id '"+userID+"' selected", Status_CRAFT.DONE);
                    bflag = true;
                    break;
                }
            }
            if(!bflag){
                clickJS(getObject("launch.Adduser"), "Add user");
                FC.deviceProvisioning();
            }
        }
        else if(isElementDisplayed(getObject("sca.AnotherIdlink"),3)) {
            clickJS(getObject("sca.AnotherIdlink"), "Another User Id link");
            FC.deviceProvisioning();
        }else{
            FC.deviceProvisioning();
        }

    }

    public void welcomeScreen_iOS() throws InterruptedException {
        Thread.sleep(2000);
        changeNativeToWebview();
        MobileElement el1 = (MobileElement) driver.findElementByAccessibilityId("Register this device");
        if (el1.isDisplayed()){
            el1.click();
            report.updateTestLog("RegisterLink", "RegisterLink  clicked successfully", Status_CRAFT.PASS);
        }
        else{
            report.updateTestLog("RegisterLink", "RegisterLink  not clicked successfully", Status_CRAFT.FAIL);
        }


    }

    public void beforeYouBegin_iOS() throws Exception {
        MobileElement beforeYouBegin_iOS_Header = (MobileElement) driver.findElementByXPath("//XCUIElementTypeStaticText[@name='Before you begin']");
        //	//XCUIElementTypeStaticText[@name="Before you begin"]
        String strHeadTitle = beforeYouBegin_iOS_Header.getAttribute("name").toString();
        if (strHeadTitle.equals("Before you begin")){
            MobileElement el2 = (MobileElement) driver.findElementByAccessibilityId("Continue");
            el2.click();
            report.updateTestLog("BeforeYourBegin", "BeforeYourBegin  clicked successfully", Status_CRAFT.PASS);
        }
        else{
            report.updateTestLog("BeforeYourBegin", "BeforeYourBegin  not clicked successfully", Status_CRAFT.FAIL);
        }
    }

    public void userIDpage_iOS() throws Exception {
        String userID = dataTable.getData("Login_Data", "Username");
        MobileElement el4 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Enter your 365 user ID\"]");
        if((el4.getAttribute("name").toString()).equals("Enter your 365 user ID")){
            MobileElement el5 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Bank of Ireland\"]/XCUIElementTypeOther[22]/XCUIElementTypeTextField");
            el5.sendKeys(userID);
            report.updateTestLog("Device Provisioning - User ID", "User ID Entered"+userID+"", Status_CRAFT.DONE);
            MobileElement el6 = (MobileElement) driver.findElementByAccessibilityId("Done");
            el6.click();
            MobileElement el7 = (MobileElement) driver.findElementByAccessibilityId("Continue");
            el7.click();
            report.updateTestLog("Device Provisioning - User ID", "Device Provisioning - User ID launched successfully", Status_CRAFT.PASS);
        }
        else{
            report.updateTestLog("Device Provisioning - User ID", "Device Provisioning - User ID not texted successfully", Status_CRAFT.FAIL);
        }


    }

    public void DOB_iOS() throws Exception {

        String strSavingDate = dataTable.getData("Login_Data", "DOB");
        MobileElement el8 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Enter your date of birth\"]");
        if(el8.isDisplayed()){
            MobileElement el9 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Bank of Ireland\"]/XCUIElementTypeOther[24]/XCUIElementTypeOther[1]/XCUIElementTypeTextField");
            MobileElement el10 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Bank of Ireland\"]/XCUIElementTypeOther[24]/XCUIElementTypeOther[2]/XCUIElementTypeTextField");
            MobileElement el11 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Bank of Ireland\"]/XCUIElementTypeOther[24]/XCUIElementTypeOther[3]/XCUIElementTypeTextField");
            el9.sendKeys(strSavingDate.split("/")[0]);
            el10.sendKeys(strSavingDate.split("/")[1]);
            el11.sendKeys(strSavingDate.split("/")[2]);
            report.updateTestLog("Device Provisioning - DOB_iOS", "Device Provisioning - DOB_iOS launched successfully", Status_CRAFT.PASS);
        }
        else{
            report.updateTestLog("Device Provisioning - DOB_iOS", "Device Provisioning - DOB_iOS not texted successfully", Status_CRAFT.FAIL);
        }
        MobileElement el12 = (MobileElement) driver.findElementByAccessibilityId("Continue");
        el12.click();

    }


    public void PINpage_iOS() throws Exception {

        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        String strObject = "";

        String Alpha=null;
        MobileElement el_digits1=null;
        MobileElement el_digits2=null;
        MobileElement el_digits3=null;
        MobileElement el_digits4=null;
        MobileElement el_digits5=null;
        MobileElement el_digits6=null;
        try
        {
            el_digits1 = (MobileElement) driver.findElementByAccessibilityId("Digit1");
            if(el_digits1.isEnabled()){
                el_digits1.sendKeys(arrPin[0]);
            }

        }
        catch(Exception e)
        {
            e.getMessage();
        }
        try
        {
            el_digits2 = (MobileElement) driver.findElementByAccessibilityId("Digit2");
            if(el_digits2.isEnabled()){
                el_digits2.sendKeys(arrPin[1]);
            }
        }
        catch(Exception e)
        {
            e.getMessage();
        }

        try
        {
            el_digits3 = (MobileElement) driver.findElementByAccessibilityId("Digit3");
            if(el_digits3.isEnabled()){
                el_digits3.sendKeys(arrPin[2]);
            }
        }
        catch(Exception e)
        {
            e.getMessage();
        }

        try
        {
            el_digits4 = (MobileElement) driver.findElementByAccessibilityId("Digit4");
            if(el_digits4.isEnabled()){
                el_digits4.sendKeys(arrPin[3]);
            }
        }
        catch(Exception e)
        {
            e.getMessage();
        }

        try
        {
            el_digits5 = (MobileElement) driver.findElementByAccessibilityId("Digit5");
            if(el_digits5.isEnabled()){
                el_digits5.sendKeys(arrPin[4]);
            }
        }
        catch(Exception e)
        {
            e.getMessage();
        }

        try
        {
            el_digits6 = (MobileElement) driver.findElementByAccessibilityId("Digit6");
            if(el_digits6.isEnabled()){
                el_digits6.sendKeys(arrPin[5]);
            }
        }
        catch(Exception e)
        {
            e.getMessage();
        }

        MobileElement el16 = (MobileElement) driver.findElementByAccessibilityId("Done");
        el16.click();
        MobileElement el11 = (MobileElement) driver.findElementByAccessibilityId("Continue");
        if(el11.isDisplayed()){
            el11.click();
            report.updateTestLog("PIN page", "PIN page filled/clicked successfully", Status_CRAFT.PASS);
        }
        else
        {
            report.updateTestLog("PIN page", "PIN page filled/clicked not successfully", Status_CRAFT.FAIL);
        }

    }

    public void notificationPage_iOS() throws Exception{
        MobileElement el18 = (MobileElement) driver.findElementByAccessibilityId("Allow notifications");
        if(el18.isDisplayed()){
            el18.click();
            (new TouchAction(driver.getAppiumDriver())).tap(PointOption.point(272, 511)).perform();
            report.updateTestLog("Device Provisioning - Notification Page", "Device Provisioning - Notification Page launched successfully", Status_CRAFT.PASS);
        }
        else{
            report.updateTestLog("Device Provisioning - Notification Page", "Device Provisioning - Notification Page not launched successfully", Status_CRAFT.FAIL);
        }

    }

    public void locationServicePage_iOS() throws Exception {
        MobileElement el19 = (MobileElement) driver.findElementByAccessibilityId("Continue");
        if(el19.isDisplayed()){
            el19.click();
            (new TouchAction(driver.getAppiumDriver())).tap(PointOption.point(272, 516)).perform();
            report.updateTestLog("Device Provisioning - Location Service Page", "Device Provisioning - Location Service Page launched successfully", Status_CRAFT.PASS);
        }
        else{
            report.updateTestLog("Device Provisioning - Location Service Page", "Device Provisioning - Location Service Page not launched successfully", Status_CRAFT.FAIL);
        }


    }

    public void registerDevice_iOS() throws Exception {
        Thread.sleep(1000);
        MobileElement el20 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeButton[@name=\"Register your device\"]");
        el20.click();
    }

    public void inviteCodeScreen_iOS() throws Exception{

        MobileElement el21 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeStaticText[@name=\"We've sent you an activation code\"]");
        MobileElement el22= (MobileElement) driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Bank of Ireland\"]/XCUIElementTypeOther[14]/XCUIElementTypeTextField") ;
        String strCaptureInvitationCode =  el22.getText();
        MobileElement el23 = (MobileElement) driver.findElementByAccessibilityId("Continue");
        el23.click();
        Thread.sleep(15000);

    }

    public void deviceName_iOS() throws Exception {
        MobileElement el23 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Bank of Ireland\"]/XCUIElementTypeOther[11]/XCUIElementTypeTextField");
        el23.sendKeys("AutoIOSDevice");
        MobileElement el24 = (MobileElement) driver.findElementByAccessibilityId("Done");
        el24.click();
        MobileElement el25 = (MobileElement) driver.findElementByAccessibilityId("Continue");
        el25.click();

    }

    public void allDone_iOS() throws Exception{
        MobileElement el26 = (MobileElement) driver.findElementByAccessibilityId("Success image");
        MobileElement el27 = (MobileElement) driver.findElementByAccessibilityId("Now you'll need to log in to continue banking. You can do that here or on your computer.");
        if(el26.isDisplayed() && el27.isDisplayed()){
            MobileElement el28 = (MobileElement) driver.findElementByAccessibilityId("Continue to login");
            el28.click();
            Thread.sleep(5000);
            changeNativeToWebview();
        }
    }

    public void enterRequiredPiniOS() throws InterruptedException {
        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        String strObject = "";
        waitForJQueryLoad();
        //To handle the SCA pin page
        MobileElement el_digits1=null;
        MobileElement el_digits2=null;
        MobileElement el_digits3=null;
        MobileElement el_digits4=null;
        MobileElement el_digits5=null;
        MobileElement el_digits6=null;
        Thread.sleep(1000);
        try
        {
            el_digits1 = (MobileElement) driver.findElementByAccessibilityId("Digit1");
            Thread.sleep(1000);
            if(el_digits1.isEnabled()){
                System.out.println("DIGIT 1 - PIN 1 " +arrPin[0]);
                el_digits1.sendKeys(arrPin[0]);

            }

        }
        catch(Exception e)
        {

        }
        try
        {
            el_digits2 = (MobileElement) driver.findElementByAccessibilityId("Digit2");
            Thread.sleep(1000);
            if(el_digits2.isEnabled()){
                System.out.println("DIGIT 2 - PIN 2 "+arrPin[1]);
                el_digits2.sendKeys(arrPin[1]);

            }
        }
        catch(Exception e)
        {

        }

        try
        {
            el_digits3 = (MobileElement) driver.findElementByAccessibilityId("Digit3");
            Thread.sleep(1000);
            if(el_digits3.isEnabled()){
                System.out.println("DIGIT 3 - PIN 3 "+arrPin[2]);
                el_digits3.sendKeys(arrPin[2]);

            }
        }
        catch(Exception e)
        {

        }

        try
        {
            el_digits4 = (MobileElement) driver.findElementByAccessibilityId("Digit4");
            Thread.sleep(1000);
            if(el_digits4.isEnabled()){
                System.out.println("DIGIT 4 - PIN 4 "+arrPin[3]);
                el_digits4.sendKeys(arrPin[3]);

            }
        }
        catch(Exception e)
        {

        }

        try
        {
            el_digits5 = (MobileElement) driver.findElementByAccessibilityId("Digit5");
            Thread.sleep(1000);
            if(el_digits5.isEnabled()){
                System.out.println("DIGIT 5 - PIN 5 "+arrPin[4]);
                el_digits5.sendKeys(arrPin[4]);

            }
        }
        catch(Exception e)
        {

        }

        try
        {
            el_digits6 = (MobileElement) driver.findElementByAccessibilityId("Digit6");
            Thread.sleep(1000);
            if(el_digits6.isEnabled()){
                System.out.println("DIGIT 6 - PIN 6 "+arrPin[5]);
                el_digits6.sendKeys(arrPin[5]);

            }
        }
        catch(Exception e)
        {

        }

        MobileElement el16 = (MobileElement) driver.findElementByAccessibilityId("Done");
        el16.click();
        MobileElement el11 = (MobileElement) driver.findElementByAccessibilityId("Continue");
        if(el11.isDisplayed()){
            el11.click();
            report.updateTestLog("PIN page After Device provisioning", "PIN page filled/clicked successfully", Status_CRAFT.PASS);
        }
        else
        {
            report.updateTestLog("PIN page After Device provisioning", "PIN page filled/clicked not successfully", Status_CRAFT.FAIL);
        }
    }


    /***********************************************************************
     * Method Name    : AllPushNotification_Acccept
     * Created By    : C966119
     * Created On    : 24/06/19
     * Comment       : To Keep Accepting push notification in mobile device which triggered from browser during batch mode
     * Input         : AppiumDriver ( mobile driver which was initiated)
     * <p>
     * /
     ***********************************************************************/
    public void AllPushNotification_Acccept() throws Exception {
        int intHours = 15 ;
        long intMilSeconds = intHours * 60 * 60 * 2*1000*1000;
        System.out.println("This Mobile app would keep accepting the push notification maximum 15 Hours ::"+ intMilSeconds);

        if (scriptHelper.commonData.exitMobileAppFlag == true ) {
            return;
        }

        for (int i = 1; i <= intMilSeconds; i++) {
            if(scriptHelper.commonData.exitMobileAppFlag){
                break;
            }
            driver.context("NATIVE_APP");
            do {
                if (isElementDisplayed(getObject("id~com.bankofireland.tcmb:id/acceptSlideButton"), 2)) {
                    report.updateTestLog("PushNotification_Acccept", " :: Screenshot :: " , Status_CRAFT.SCREENSHOT);
                    break;
                }
            } while (isElementDisplayed(getObject("id~com.bankofireland.tcmb:id/acceptSlideButton"), 5));

            if (isElementDisplayed(getObject("id~com.bankofireland.tcmb:id/acceptSlideButton"), 1)) {
                MobileElement el1 = (MobileElement) driver.findElementById("com.bankofireland.tcmb:id/acceptSlideButton");
                int startX = el1.getLocation().getX();
                int startY = el1.getLocation().getY();
                int intWidth = el1.getRect().getWidth();
                int endX = startX + intWidth;
                (new TouchAction(driver.getAppiumDriver())).press(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, startY)).release().perform();
                report.updateTestLog("PushNotification_Acccept", "Push Notification Accepted " , Status_CRAFT.PASS);
                driver.context("WEBVIEW_com.bankofireland.tcmb");
                scriptHelper.commonData.exitMobileAppFlag = false ;
            }
        }
    }

    ////////////////// CAF 2.0 /////////////////////////////////////////

    public void functionToHandleLogin_validate() throws Exception {
        waitForPageLoaded();
        waitForJQueryLoad();
        FunctionalComponents FC = new FunctionalComponents(scriptHelper);
        if(isElementDisplayed(getObject("launch.LoginHeading"),9)){
            String userID = dataTable.getData("Login_Data", "Username");
            boolean bflag = false;
            List<WebElement> elm = driver.findElements(getObject("launch.userlist"));
            for (int i = 0; i < elm.size(); i++) {
                String ID = elm.get(i).getText();
                if (ID.equals(userID)) {
                    scrollToView(getObject("xpath~//span[text()='" +userID+ "']"));
                    elm.get(i).click();
                    bflag = true;
                    break;
                }
            }
            if(!bflag){
                clickJS(getObject("launch.Adduser"), "Add user");
                FC.deviceProvisioningValidations();
            }
        }
        else if(isElementDisplayed(getObject("sca.AnotherIdlink"),3)) {
            clickJS(getObject("sca.AnotherIdlink"), "Another User Id link");
            FC.deviceProvisioningValidations();
        }else{
            FC.deviceProvisioningValidations();
        }

    }


    public String getActivationCodeFromEngageHub(String regMobileNumEnding) throws Exception {

        regMobileNumEnding = getMobileNumber(regMobileNumEnding);

        if(!"".equalsIgnoreCase(regMobileNumEnding)){

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("--disable-extensions");
            options.addArguments("--start-maximized");
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");

            // Takes the system proxy settings automatically
            String driverPath = frameworkParameters.getRelativePath() + Util.getFileSeparator() + "src" + Util.getFileSeparator()
                    + "main" + Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "Drivers" + Util.getFileSeparator() + "EngageHub" + Util.getFileSeparator()
                    + "chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", driverPath);

            ChromeDriver mydriver = new ChromeDriver(options);
            mydriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            mydriver.get("https://boi-st.test.engagehub.com/MEnable/Client/servlets/Login?");

//            String email = "//input[@name='Email']";
//            String password = "//input[@name='Password']";
//            String logIn = "//input[@class='login-button']";
//            String title = "//span[text()='Enter mobile number']";
//            String searchNumber = "//input[@name='SearchMobileNumber']";
//            String search = "//input[@value='Search']";
//            String refresh = "//span/button[text()='Refresh']";

            mydriver.findElementByXPath( "//input[@name='Email']").sendKeys("Sarang.Pande@boi.com");
            mydriver.findElementByXPath("//input[@name='Password']").sendKeys("Ireland@2105");
            //mydriver.findElementByXPath("//input[@class='button']").click();
            mydriver.findElementByXPath("//input[@value='Log in']").click();
            Thread.sleep(3000);

            mydriver.findElementByXPath("//input[@name='SearchMobileNumber']").sendKeys(regMobileNumEnding);
            mydriver.findElementByXPath("//input[@value='Search']").click();

            Thread.sleep(1000);
            mydriver.findElementByXPath("//span/button[text()='Refresh']").click();
            Thread.sleep(1000);

            mydriver.findElement(By.xpath("//table[@id='main-table-persistent']/tbody/descendant::tr[1]/descendant::button[text()='Options']")).click();
            mydriver.findElement(By.xpath("//table[@id='main-table-persistent']/tbody/descendant::tr[1]/descendant::*[@class='dropdown-menu show']/a")).click();
            Thread.sleep(1000);

            String message = mydriver.findElementByXPath("//*[@id='message-modal-text']").getText();
            String [] words = message.split("activation code ");
            String activationCode = words[1].substring(0,10);

            System.out.println("Activation code is" + activationCode);
            mydriver.close();
            mydriver.quit();

            return activationCode;
        }
        else{
            report.updateTestLog("FetchingNumber","Number Ending With '"+regMobileNumEnding+"' not present on the list",Status_CRAFT.FAIL);
            driver.close();
            driver.quit();
            return null;
        }
    }

    public void getInstalledAppVersion() throws Exception{
        if(isElementDisplayed(getObject("home.lnkPreLgnMore"),5)){
            click(getObject("home.lnkPreLgnMore"), "More button");
            if(isElementDisplayed(getObject("xpath~//*[@class='js-mobile-app-version']"),5)){
                scrollToView(getObject("xpath~//*[@class='js-mobile-app-version']"));
                report.updateTestLog("validateAppVersion","App version insatlled :"+getText(getObject("xpath~//*[@class='js-mobile-app-version']")),Status_CRAFT.SCREENSHOT);
                System.out.print(getText(getObject("xpath~//*[@class='js-mobile-app-version']")));
            }else{
                report.updateTestLog("validateAppVersion","App Version element not found",Status_CRAFT.SCREENSHOT);
            }
            //navigating back to login/user id page
            click(getObject("xpath~//input[@title='Back']"));
            waitForJQueryLoad();waitForPageLoaded();
            Thread.sleep(2000);

        }else{
            report.updateTestLog("validateAppVersion","More link not found",Status_CRAFT.SCREENSHOT);
        }
    }

    public String getMobileNumber(String regMobileNumEnding) throws Exception{

        String [] allAvailableNums = {"0873488025","0873955754","0873429793","0873425867","0873818930","0877210495","0876389563","0871251062",
                "0870565043","0873486820","0873426579","0871954511","0873459864","0871665030","0873486598","0873450230","0873936870","0873432739",
                "0873432739","0873388040","0873464145","0873936870","0873432113","0871090672","0873978727","0872910118","0871017867","0877145450",
                "0871019779","0872266622","0873534711","0872233306","0872272105","0879494883","0873385255","0877197972","0873814150","0873850655",
                "0877075253","0873850304","0873497275","0877207941","0873489075","0871198362","0860413171","0872272105","0872233306"};

        boolean numfound = false;
        for(String num:allAvailableNums){
            if(num.endsWith(regMobileNumEnding)){
                regMobileNumEnding = num;
                System.out.println("Looking for invitation code from mobile number '"+regMobileNumEnding+"'");
                numfound=true;
                break;
            }
        }

        if(numfound){
            return regMobileNumEnding;
        }else{
            return "";
        }
    }

}


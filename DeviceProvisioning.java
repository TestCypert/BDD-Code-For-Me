package com.boi.grp.pageobjects.login;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import io.appium.java_client.MobileElement;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeviceProvisioning extends BasePageForAllPlatform {

	private boolean isDeviceLimitReached;
	
	public DeviceProvisioning(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
		if(!driver.toString().contains("appium")){
			driver.manage().window().maximize();
		}
	}

    /**
     * This is a main method which will help in device provisioning for new user
     * @param userId
     * @param dob
     * @param pin
     * @param deviceName
     */

    public void registerNewDevice(String userId,String dob,String pin,String deviceName){
        waitForPageLoaded();
        welcomeScreen();
        beforeYouBegin();
        enterUserID(userId);
        checkDeviceLimitReached();
        if (explicitWaitForVisibilityOfElement(getObjectBy("dp.Login"))) {
            clickJS(getObjectBy("dp.Login"));
        }else{
            if(!isDeviceLimitReached){
                enterDOB(dob);
                enterUserPin(pin);
                notificationPage();
                if(driver.toString().contains("IOS")){
                    acceptNotificationToAccess();
                }
                locationServicePage();
                acceptNotificationToAccess();
                registerDevice();
                permissionToSendPage();
                acceptNotificationToAccess();
                fetchEngageHubCode();
                deviceName(deviceName);
                allDone();
            }
        }
    }


    /**
     * This method wil help clicking on Register This Device button on Welcome Page.
     */
	private void welcomeScreen() {
		try {
            appendScreenshotToCucumberReport();
//			changeNativeToWebview();
			if(explicitWaitForVisibilityOfElement(getObjectBy("dp.btnRegisterThisDevice"))){
				clickJS(getObjectBy("dp.btnRegisterThisDevice"));
				logMessage("clicked, register this device successfully");
				insertMessageToHtmlReport("clicked, register this device successfully");
                Assert.assertTrue("clicked, register this device successfully",true);
			}else{
				logError("Error in visibility of Register button");
				insertErrorMessageToHtmlReport("Error in visibility of Register button");
                Assert.assertTrue("Error in visibility of Register button",false);
			}
		} catch (Throwable e) {
			logError("Register this device failed"+ ", Error Description=" + e.getMessage());
			appendScreenshotToCucumberReport();
			insertErrorMessageToHtmlReport("Register this device button is not clicked");
            Assert.fail("Register this device button is not clicked");

        }
	}

    /**
     * This method will verify the page title if it contains Before you begin then, clicks on Continue button.
     */
    private void beforeYouBegin(){
        try {
            waitForPageLoaded();
            String strHeadTitle = getText(getObjectBy("dp.txtBYBHeadTitle"));
            if (strHeadTitle.contains("Before you begin")) {
                logMessage("BeforeYourBegin Page launched successfully");
                waitForSeconds(1);
                clickJS(getObjectBy("dp.btnContinue"));
                Assert.assertTrue("BeforeYourBegin Page launched successfully",true);
            } else {
                logError("BeforeYourBegin Page  not launched successfully ");
                insertErrorMessageToHtmlReport("BeforeYourBegin Page  not launched successfully ");
                appendScreenshotToCucumberReport();
                Assert.assertTrue("BeforeYourBegin Page launched successfully",false);
            }
        } catch (Throwable e) {
            logError("BeforeYourBegin Page  not loaded "+e.getMessage());
            insertErrorMessageToHtmlReport("BeforeYourBegin Page  not loaded successfully");
            appendScreenshotToCucumberReport();
            Assert.fail("BeforeYourBegin Page  not loaded successfully");
        }
    }

    /**
     * enterUserID is a method to enter the six digit user ID and clicks on Continue button on the user ID page
     * @param userId
     */
    private void enterUserID(String userId) {
        try {
            waitForVisibilityOfElement(getObjectBy("dp.headingUserId"));
            clickJS(getObjectBy("dp.edtUserID"));
            sendKey(getObjectBy("dp.edtUserID"),userId);
            logMessage("Userid '" + userId+"' is entered successfully");
            insertMessageToHtmlReport("Userid '" + userId+"' is entered successfully");
            appendScreenshotToCucumberReport();
            /*if(System.getProperty("RUNTYPE").equalsIgnoreCase("MOBILEAPP")){
                getAppiumDriver().hideKeyboard();
            }*/
            clickJS(getObjectBy("dp.btnContinue"), "Continue");
            Assert.assertTrue("Userid is entered successfully",true);
        } catch (Throwable e) {
            logError("Userid" + userId+" is not entered successfully, Error = "+e.getMessage());
            insertErrorMessageToHtmlReport("Userid" + userId+" is not entered successfully");
            appendScreenshotToCucumberReport();
            Assert.fail("Device provisioning failed at step 1 userID : " + e);
        }
    }

    /**
     * checkDeviceLimitReached is a method which checks for the header message stating 'Device Limit Reached' and sets
     * the boolean value for isDeviceLimitReached flag.
     */
    private void checkDeviceLimitReached() {
        try {
            if (explicitWaitForVisibilityOfElement(getObjectBy("dp.headingDeviceLimitReached"))) {
                isDeviceLimitReached=true;
                Assert.fail("Device Limit Reached");
            }
        } catch (Exception TimeoutException) {
            isDeviceLimitReached = false;
        }
    }

    /**
     * enterDOB is a method which is used to enter date of birth.
     * @param dob
     */
    private void enterDOB(String dob) {
        try {
            if (explicitWaitForVisibilityOfElement(getObjectBy("dp.edtEnterYourDateOfBirth"))) {
                click(getObjectBy("dp.edtEnterYourDateOfBirth"));
                setValue(getObjectBy("dp.edtEnterYourDateOfBirth"), dob);
            } else {
                setValue(getObjectBy("dp.edtDay"), dob.split("/")[0]);
                setValue(getObjectBy("dp.edtMonth"), dob.split("/")[1]);
                setValue(getObjectBy("dp.edtYear"), dob.split("/")[2]);
            }
            clickJS(getObjectBy("dp.btnContinue") , "DOB Continue button");
            logMessage("Successfully entered DOB");
            appendScreenshotToCucumberReport();
            insertMessageToHtmlReport("Successfully entered DOB");
            Assert.assertTrue("Successfully entered DOB",true);
        } catch (Throwable e) {
            logError("Device provisioning failed at step 2 DOB , Error = "+e.getMessage());
            insertErrorMessageToHtmlReport("Device provisioning failed at step 2 DOB");
            appendScreenshotToCucumberReport();
            Assert.fail("Device provisioning failed at step 2 DOB : " + e.getMessage());
        }
    }

    /**
     * enterUserPin is a method to enter six digit secure pin number.
     * @param pin
     */
    private void enterUserPin(String pin) {
        try {
            LoginPageNew login = new LoginPageNew(driver);
            login.enterRequiredPin(pin);
            login.clickPinContinueButton();
            appendScreenshotToCucumberReport();
        } catch (Throwable e) {
            logError("Device provisioning failed at step 3 PIN : " + e.getMessage());
            insertErrorMessageToHtmlReport("Device provisioning failed at step 3 PIN");
            Assert.fail("Device provisioning failed at step 3 PIN : " + e.getMessage());
        }
    }

    /**
     * notificationPage is the method to allow the notification to be send to the selected device.
     */
    private void notificationPage() {
        try {
            while (explicitWaitForVisibilityOfElement(getObjectBy("dp.spinSpinner"))) {
                waitForPageLoaded();
            }
            waitForVisibilityOfElement(getObjectBy("dp.btnAllowNotification"));
            clickJS(getObjectBy("dp.btnAllowNotification"));
            insertMessageToHtmlReport("Allow Notifications Granted");
            logMessage("Allow Notifications Granted");
        } catch (Throwable e) {
            logError("Allow Notifications Not Granted, Error = "+e.getMessage());
            insertErrorMessageToHtmlReport("Allow Notifications Not Granted");
            appendScreenshotToCucumberReport();
        }
    }

    /**
     * acceptNotificationToAccess is a main method which will help to click on the 'Allow" option on the Native pop up that
     * appears in the device provisioning flow. It calls 2 sub methods acceptNotificationSubMethod and getObjectForIOSCOntinuePopUp
     */
    private void acceptNotificationToAccess() {
        changeWebViewToNative();
        if(driver.toString().contains("IOS")){
            String popupLocator = getObjectForIOSContinuePopUp();
            acceptNotificationSubMethod(popupLocator);
        }else {
            acceptNotificationSubMethod("dp.AndroidAllow");
        }
        changeNativeToWebview();
    }

    /**
     * acceptNotificationSubMethod verifies whether the allow option and allow while using the app option is displayed on
     * on the pop up and clicks on the same.
     * @param args
     */
    private void acceptNotificationSubMethod(String args){
        if (explicitWaitForVisibilityOfElement(getObjectBy(args))) {
            MobileElement element;
            if(driver.toString().contains("IOS")){
                element = (MobileElement)findElementByXPath(System.getProperty(args).split("~")[1]);
            }else{
                element = (MobileElement)findElementById(System.getProperty(args).split("~")[1]);
            }
            appendScreenshotToCucumberReport();
            element.click();
            insertMessageToHtmlReport("Permission to access the Device location is granted Successfully");
            appendScreenshotToCucumberReport();
            logMessage("Permission to access the Device location is granted Successfully");
            Assert.assertTrue("Permission to access the Device location is granted Successfully",true);
        } else {
            try {
                while (explicitWaitForVisibilityOfElement(getObjectBy("dp.spinSpinner"))) {
                    waitForPageLoaded();
                }
                //waitForVisibilityOfElement(getObjectBy("dp.btnRegisterYourDevice"));
                logMessage("Permission to access the Device location popup is not present");
                insertMessageToHtmlReport("Permission to access the Device location popup is not present");
            } catch (Throwable e) {
                logError("Error in accepting Native popup, Error = "+e.getMessage());
                insertErrorMessageToHtmlReport("Error in accepting Native popup");
                appendScreenshotToCucumberReport();
                Assert.assertTrue("Error in accepting Native popup",false);
            }
        }
    }

    /**
     * This method will click continue button on location page
     */
    private void locationServicePage() {

        while (explicitWaitForVisibilityOfElement(getObjectBy("dp.spinSpinner"))) {
            waitForPageLoaded();
        }
        try {
            waitForVisibilityOfElement(getObjectBy("dp.btnContinue"));
            appendScreenshotToCucumberReport();
            clickJS(getObjectBy("dp.btnContinue"));
            logMessage("Location Service Page Accepted");
            insertMessageToHtmlReport("Location Service Page Accepted");
            Assert.assertTrue("Location Service Page Accepted",true);
        } catch (Throwable e) {
            logError("Location Service Page Not Accepted , Error = "+e.getMessage());
            insertErrorMessageToHtmlReport("Location Service Page Not Accepted");
            appendScreenshotToCucumberReport();
            //Assert.assertTrue("Location Service Page Accepted",false);
        }
    }

    /**
     * This method will the registration page and click continue button if visible
     */
    private void registerDevice() {

        while (explicitWaitForVisibilityOfElement(getObjectBy("dp.spinSpinner"))) {
            waitForPageLoaded();
        }
        try {
            waitForVisibilityOfElement(getObjectBy("dp.btnRegisterYourDevice"));
            clickJS(getObjectBy("dp.btnRegisterYourDevice"));
            logMessage("Register device clicked successfully");
            insertMessageToHtmlReport("Register device clicked successfully");
        } catch (Throwable e) {
            logWarning("Register device not visible , Error = "+e.getMessage());
            insertWarningMessageToHtmlReport("Register device not visible");
            appendScreenshotToCucumberReport();
            //Assert.fail("Register device not visible");
        }
    }

    /**
     * This method will fetch the code from Engage hub from the registered mob numbers and will enter
     * the same in app
     */
    private void fetchEngageHubCode() {
        try {
            long expectedEndTime = System.currentTimeMillis()+60*1000;
            while (explicitWaitForVisibilityOfElement(getObjectBy("dp.spinSpinner"))) {
                waitForPageLoaded();
                long currentTime = System.currentTimeMillis();
                if(currentTime>expectedEndTime){
                    logError("Spinner is not going off");
                    Assert.assertTrue("Spinner is not going off",false);
                    break;
                }
            }
            String strMobNumEndingDigits = getText(getObjectBy("dp.mobileDigit")).split("ending in ")[1].substring(0,4);
            if(System.getProperty("PICK_CODE_FROM_ENGAGE_HUB").equalsIgnoreCase("TRUE")){
                sendKey(getObjectBy("dp.txtInvitationCode"),getActivationCodeFromEngageHub(strMobNumEndingDigits));
            }else{
                logError("PICK_CODE_FROM_ENGAGE_HUB is Not True, Please make this as True ");
                insertErrorMessageToHtmlReport("PICK_CODE_FROM_ENGAGE_HUB is Not True, Please make this as True ");
                Assert.fail("PICK_CODE_FROM_ENGAGE_HUB is Not True, Please make this as True ");
            }
            appendScreenshotToCucumberReport();
            //String strCaptureInvitationCode = getAttribute(getObjectBy("launch.txtInvitationCode"), "value");
            fluentWaitCondition(getObjectBy("dp.btnContinue"));
            clickJS(getObjectBy("dp.btnContinue"));
            explicitWaitForVisibilityOfElement(getObjectBy("dp.txtNameYourDevice"));
        } catch (Throwable e) {
            logError("Error in getting invite code, Error =  "+e.getMessage());
            insertErrorMessageToHtmlReport("");
            appendScreenshotToCucumberReport();
            Assert.assertTrue("Error in getting invite code",false);
        }
    }

    /**
     * This method will fetch Engage hub code by launching chrome in headless mode
     * @param regMobileNumEnding
     * @return
     */
    private String getActivationCodeFromEngageHub(String regMobileNumEnding) {
        try {
            regMobileNumEnding = getMobileNumber(regMobileNumEnding);
            if(!"".equalsIgnoreCase(regMobileNumEnding)){

                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("useAutomationExtension", false);
                options.setAcceptInsecureCerts(true);
                options.addArguments("--disable-extensions");
                options.addArguments("--start-maximized");
                options.addArguments("--headless");
                options.addArguments("--window-size=1920,1080");

                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/webdrivers/windows/chrome/"+System.getProperty("CHROME_VERSION")+"/chromedriver.exe");

                ChromeDriver mydriver = new ChromeDriver(options);
                mydriver.manage().timeouts().implicitlyWait(Integer.valueOf(System.getProperty("IMPLICITWAIT")), TimeUnit.SECONDS);
                mydriver.get(System.getProperty("dp.engageHubUrl"));

                String activationCode = "";
                try {
                    mydriver.findElement(getObjectBy("dp.engageHubEmailLocator")).sendKeys(System.getProperty("dp.engageHubEmail"));
                    mydriver.findElement(getObjectBy("dp.engageHubPasswordLocator")).sendKeys(System.getProperty("dp.engageHubPassword"));
                    mydriver.findElement(getObjectBy("dp.engageLoginButton")).click();
                    waitForSeconds(3);

                    mydriver.findElement(getObjectBy("dp.searchMobNum")).sendKeys(regMobileNumEnding);
                    mydriver.findElement(getObjectBy("dp.searchButton")).click();
                    logMessage("successfully entered search button in engage hub");
                    waitForSeconds(1);

                    try {
                        String url = mydriver.getCurrentUrl();
                        //mydriver.get(url);
                        mydriver.navigate().refresh();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    mydriver.manage().timeouts().implicitlyWait(Integer.valueOf(System.getProperty("IMPLICITWAIT")),TimeUnit.SECONDS);

                    mydriver.findElement(getObjectBy("dp.options")).click();
                    mydriver.findElement(getObjectBy("dp.optionsDropDown")).click();
                    waitForSeconds(1);

                    String message=mydriver.findElement(getObjectBy("dp.modalText")).getText();
                    String [] words = message.split("activation code ");
                    activationCode = words[1].substring(0,10);

                    logMessage("Activation code is" + activationCode);
                    insertMessageToHtmlReport("Activation code is" + activationCode);
                } catch (Throwable e) {
                    logError("Error in engage hub GUI selenium code , Error = "+e.getMessage());
                }
                mydriver.close();
                mydriver.quit();
                return activationCode;
            }else{
                logError("Number Ending With '"+regMobileNumEnding+"' not present on the list");
                Assert.assertTrue("Number Ending With '"+regMobileNumEnding+"' not present on the list ",false);
                return "";
            }
        } catch (Throwable e) {
            logError("Error in fetching code from engagehub , Error = "+e.getMessage());
            Assert.fail("Error in fetching code from engagehub");
            return "";
        }
    }

    /**
     * This method will provide mobile number based on last 4 digit of mob number
     * @param regMobileNumEnding
     * @return
     */
    private String getMobileNumber(String regMobileNumEnding){
        try {
            //below list may be updated in future as per the status of mobile number
            List<String> allAvailableNumbers = Arrays.asList("0873488025", "0873955754", "0873429793", "0873425867", "0873818930", "0877210495", "0876389563", "0871251062",
                    "0870565043", "0873486820", "0873426579", "0871954511", "0873459864", "0871665030", "0873486598", "0873450230", "0873936870", "0873432739",
                    "0873432739", "0873388040", "0873464145", "0873936870", "0873432113", "0871090672", "0873978727", "0872910118", "0871017867", "0877145450",
                    "0871019779", "0872266622", "0873534711", "0872233306", "0872272105", "0879494883", "0873385255", "0877197972", "0873814150", "0873850655",
                    "0877075253", "0873850304", "0873497275", "0877207941", "0873489075", "0871198362", "0860413171", "0872272105", "0872233306","0872156621");

            boolean numFound = false;
            for(String num:allAvailableNumbers){
                if(num.endsWith(regMobileNumEnding)){
                    regMobileNumEnding = num;
                    logMessage("Looking for invitation code from mobile number '"+regMobileNumEnding+"'");
                    numFound=true;
                    break;
                }
            }
            if(numFound){
                return regMobileNumEnding;
            }else{
                return "";
            }
        } catch (Throwable e) {
            logError("Error in getting mobile number  , Error = "+e.getMessage());
            Assert.fail("Error in getting mobile number");
            return "";
        }
    }

    /**
     * This method will setup the device nickname for tha uxp application
     * @param strRegisteredDeviceName
     */
    private void deviceName(String strRegisteredDeviceName) {
        try {
            long expectedEndTime = System.currentTimeMillis()+30*1000;
            while (explicitWaitForVisibilityOfElement(getObjectBy("dp.spinSpinner"))) {
                waitForPageLoaded();
                long currentTime = System.currentTimeMillis();
                if(currentTime>expectedEndTime){
                    logError("Spinner is not going off");
                    Assert.assertTrue("Spinner is not going off",false);
                    break;
                }
            }
            waitForVisibilityOfElement(getObjectBy("dp.edtDeviceName"));
            sendKey(getObjectBy("dp.edtDeviceName"), strRegisteredDeviceName);
            appendScreenshotToCucumberReport();
            clickJS(getObjectBy("dp.btnContinue"));
        } catch (Throwable e) {
            logError("Error in entering device name , Error = "+e.getMessage());
            insertErrorMessageToHtmlReport("Error in entering device name");
            appendScreenshotToCucumberReport();
            Assert.fail("Error in entering device name");
        }
    }

    /**
     * This method will click continue to login button which is the last step of Device Provisioning
     */
    private void allDone() {
        try {
            waitForVisibilityOfElement(getObjectBy("dp.btnContinueToLoginDone"));
            clickJS(getObjectBy("dp.btnContinueToLoginDone"));
            appendScreenshotToCucumberReport();
            logMessage("Login Done button clicked successfully while Device Provisioning");
            insertMessageToHtmlReport("Login Done button clicked successfully while Device Provisioning");
            Assert.assertTrue("Login Done button clicked successfully while Device Provisioning",true);
        } catch (Throwable e) {
            logError("Continue to login button in all done method not visible , Error = "+e.getMessage());
            insertErrorMessageToHtmlReport("Continue to login button in all done method not visible");
            appendScreenshotToCucumberReport();
            Assert.fail("Continue to login button in all done method not visible");
        }
    }

    /**
     * This method will click permission to send page button for ios
     */
    private void permissionToSendPage(){
        try {
            if(driver.toString().contains("IOS")){
                click(getObjectBy("dp.permissionToSendPage"));
                logMessage("Permission to send code page");
                insertMessageToHtmlReport("Permission to send code page");
            }
        } catch (Throwable e) {
            logError("Error in permissionToSendPage , Error = "+e.getMessage());
        }
    }

    /**
     * This methos will return locator string for the two different types of IOS native popups
     * @return
     */
    private String getObjectForIOSContinuePopUp() {
        String object;
        try {
            if (explicitWaitForVisibilityOfElement(getObjectBy("dp.IOSAllow"))) {
                object="dp.IOSAllow";
                return object;
            } else{
                object="dp.IOSAllowOld";
                return object;
            }
        } catch (Throwable e) {
            logError("Error in getObjectForIOSContinuePopUp, Error = "+e.getMessage());
        }
        return "";
    }
}

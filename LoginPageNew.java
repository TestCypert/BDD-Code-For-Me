package com.boi.grp.pageobjects.login;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by C112083 on 07/05/2021.
 */
public class LoginPageNew extends BasePageForAllPlatform {

    @FindBy(xpath ="//div[@id='C2__p4_Login-TermsAndCondition-Continue']")
    public WebElement loginTermsAndConditionContinue;

    @FindBy(xpath = "//a[contains(@class,'boi-terms-conditions__link')]")
    public List <WebElement> boiTermsAndConditionsLink;

    @FindBy(xpath = "//h1[text()='Log in']")
    public  List <WebElement>  multiProfileHeading;

    @FindBy(xpath = "//span[@class='boi-sca-user-answer-part']")
    public List <WebElement> userProfileName;

    @FindBy (xpath = "//button[@title='Add user']")
    public WebElement addUser;

    @FindBy (xpath = "//a[@id='C2__C1__BUT_0F5043357FD8A5C462752']")
    public  List <WebElement>  loginWithAnotherId;

    @FindBy (xpath = "//a[@id='C2__C1__BUT_0F5043357FD8A5C462752']")
    public WebElement loginWithAnotherIdBtn;

    @FindBy(xpath ="//*[@id='C7__firstMenuItem']")
    public WebElement boiWelcomePageApp;

    public LoginPageNew(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        if(!driver.toString().contains("appium")){
            driver.manage().window().maximize();
        }
    }


    /**
     * launchUXPApplication method is a generic method used to launch UXP application on any of the three different platform
     * Desktop Web/Mobile web/Mobile app
     * @param userType
     */
    public void launchUXPApplication(String userType){
        String usData=getLoginData(userType);
        String[] arrUsData = usData.split(":");
        String userId=arrUsData[0];
        String dob =arrUsData[1];
        String pin =arrUsData[2];
        String L4D =arrUsData[3];
        String deviceName =arrUsData[4];
		 System.setProperty("pin",pin);

        try {
            String runType=System.getProperty("RUNTYPE");
            if(runType.equalsIgnoreCase("MOBILEAPP")){
                launchUXPApplicationForApp(userId,dob,pin);
            }else {
                launchUXPApplicationForBrowser();
                enterDesktopCredentials(userId,dob);
            }
            loginUXP(pin);
        } catch (Throwable throwable) {
            logError("Error in launching application , Error = "+throwable.getMessage());
        }
    }


    /**
     * UXloginUXPPlogin method is for entering the pin, clicking Continue button on pin page.
     * Further to this it selects the registered device if the platform is desktop browser and verifies if home page is
     * visible.
     * @param pin
     */
    public void loginUXP(String pin){
        try {
            enterRequiredPin(pin);
            clickPinContinueButton();
            if(!System.getProperty("RUNTYPE").equalsIgnoreCase("MOBILEAPP")){
                selectRegisteredDevice(getDeviceName());
            }
            checkHomePageIsVisible();
        } catch (Exception e) {
            logError("Error in logging into application , Error = "+e.getMessage());
        }
    }


    /**
     * checkHomePageIsVisible is the method to verify if terms and condition page is visible
     * before navigating to home page and accepts the same if visible and verifies further if home page is displayed.
     */
    private void checkHomePageIsVisible(){
        waitForPageLoaded();
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
        if(explicitWaitForVisibilityOfElement(getObjectBy(devTypeToGetProperty+"login.HomePage"))){
            verifyHomepage();
        }else{
            //changes can be made in future to handle new pages
            verifyInterruptsImage();
            acceptTermsAndCondition();
            digitalPayInterrupt();
            verifyHomepage();
        }
    }


    /**
     * launchUXPApplicationForBrowser is the method to launch application on desktop/mobile browser.
     *  @throws Exception if Login button on the Welcome/Landing page is not clicked.
     */
    public void launchUXPApplicationForBrowser() {
        try {
            if (isElementDisplayed(getObjectBy("login.loginButton"))) {
                    clickJS(getObjectBy("login.loginButton"));
                Assert.assertTrue("Login Button clicked",true);
            }
        } catch (Exception e) {
            logError("Error in clicking Login button, Error = "+e.getMessage());
            Assert.assertFalse("Login Button not clicked",true);
        }
    }


    /**
     * launchUXPApplicationForApp is the method which checks if there are any users registered on mobile device already, then
     * selects the required user. If user is not registered then it goes ahead with device provisioning flow. As part of that
     * it checks if loginWithAnotherId option or Add user button
     * is present, and clicks on it whichever is displayed. If both not present, then it calls the register new device function.
     * @param userId
     * @param dob
     * @param pin
     */
    public void launchUXPApplicationForApp(String userId,String dob, String pin) {
        DeviceProvisioning deviceProvisioning = new DeviceProvisioning(driver);
        try {
            waitForPageLoaded();
            waitForSeconds(2);
            waitForVisibilityOfElement(By.xpath("//h1[text()='Log in']"));
                if (multiProfileHeading.size() > 0) {
                    boolean bflag = false;
                    for (int i = 0; i < userProfileName.size(); i++) {
                        String ID = userProfileName.get(i).getText();
                        if (ID.equals(userId)) {
                            if (userProfileName.size() >= 5) {
                                scrollToView(By.xpath("//span[text()='" + userId + "']"));
                            }
                            userProfileName.get(i).click();
                            System.out.print("User Id selected '" + userId + "' from multiple profile.");
                            bflag = true;
                            break;
                        }
                    }
                    if (!bflag) {
                        Assert.assertTrue("Add new user is displayed", addUser.isDisplayed());
                        appendScreenshotToCucumberReport();
                        scrollToView(addUser);
                        addUser.click();
                        deviceProvisioning.registerNewDevice(userId, dob, pin,getDeviceName());
                    }
                } else if (loginWithAnotherId.size() > 0) {
                    waitForPageLoaded();
                    loginWithAnotherIdBtn.click();
                    deviceProvisioning.registerNewDevice(userId, dob, pin,getDeviceName());
                } else {
                    deviceProvisioning.registerNewDevice(userId, dob, pin,getDeviceName());
                }
        } catch(Exception e) {
            Assert.fail("Mobile login failed" + e.getMessage());
        }
    }


    /**
     * enterDesktopCredentials is a method to enter the Login details (username and date of birth) and click continue button on
     * browser application launched in desktop.
     * @param userName
     * @param dob
     */
    public void enterDesktopCredentials(String userName,String dob){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(getObjectBy(devTypeToGetProperty+"login.loginPage")));
            enterUserId(userName);
            enterDob(dob);
            clickContinue();
        } catch (Exception e) {
            logError("Error in entering desktop credentials, Error =  "+e.getMessage());
            Assert.assertFalse("Desktop details not entered"+ e.getMessage(),true);
        }
    }


    /**
     * enterUserId is a sub method to enter six digit user ID based on the device type selected for execution. This is a
     * common method which can be used to enter user id on desktop browser application, Mobile device Browser, and Mobile application in
     * mobile device.
     * @param userName
     */
    private void enterUserId(String userName){
        try {
            if (isElementDisplayed(getObjectBy("login.edtUserID"))) {
                clickJS((getObjectBy("login.edtUserID")));
                setValue((getObjectBy("login.edtUserID")), userName);
                logMessage("UserName is entered in the field");
                insertMessageToHtmlReport("UserName is entered in the field");
                Assert.assertTrue("User name is entered",true);
            }
        } catch (Exception e) {
            logError("Error in entering user id , Error = "+e.getMessage());
            appendScreenshotToCucumberReport();
            insertErrorMessageToHtmlReport("Unable to enter user Id");
            Assert.assertFalse("Unable to enter user Id "+e.getMessage(),true);
        }
    }


    /**
     * enterDob is a sub method to enter users date of birth based on the device type selected for execution. This is a
     * common method which can be used to enter date of birth on desktop browser application, Mobile device Browser, and Mobile application in
     * mobile device.
     * @param dob
     */
    private void enterDob(String dob){
        try {
            if(explicitWaitForVisibilityOfElement(getObjectBy("login.edtEnterYourDateOfBirth"))) {
                click(getObjectBy("login.edtEnterYourDateOfBirth"));
                setValue(getObjectBy("login.edtEnterYourDateOfBirth"), dob);
                appendScreenshotToCucumberReport();
                logMessage("BirthDate is entered in the field");
                insertMessageToHtmlReport("BirthDate is entered in the field");
                Assert.assertTrue("DOB is entered", true);
            }
        } catch (Exception e) {
            logError("Error in entering DOB, Error = "+e.getMessage());
            appendScreenshotToCucumberReport();
            insertErrorMessageToHtmlReport("Unable to enter DOB");
            Assert.assertFalse("Unable to enter DOB "+e.getMessage(),true);
        }
    }


    /**
     * clickContinue is the common method to click on the continue button based on the type of platform.
     */
    private void clickContinue(){
        try {
            clickJS(getObjectBy("login.continue"));
            logMessage("User id page Continue button is clicked");
            insertMessageToHtmlReport("User id page Continue button is clicked");
            Assert.assertTrue("User id page Continue button is clicked",true);
        } catch (Exception e) {
            logError("Error in clicking continue in UserId page, Error = "+e.getMessage());
            appendScreenshotToCucumberReport();
            insertErrorMessageToHtmlReport("Unable to click continue in UserId page");
            Assert.assertFalse("Unable to click continue in UserId page "+e.getMessage(),true);
        }
    }


    /**
     * enterRequiredPin is the method to enter the 6 digit secured PIN number.
     * @param pin
     */
    public void enterRequiredPin(String pin){
        String[] arrPin = pin.split("");
        waitForPageLoaded();
        try {
            List<WebElement> lstPinEnterFieldGrp = findElements(getObjectBy(getLocatorValueForPin()));
            waitForSeconds(2);
            for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
                if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                    if(i==0){
                        clickJS(lstPinEnterFieldGrp.get(i));
                    }
                    lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                }
            }
            logMessage("Successfully entered pin ");
            Assert.assertTrue("Pin entered successfully",true);
        } catch (Exception e) {
            logError("Error in entering pin , Error = "+e.getMessage());
            insertErrorMessageToHtmlReport("Error in entering pin");
            appendScreenshotToCucumberReport();
            Assert.fail("Error in entering pin");
        }
    }


    /**
     * getLocatorValueForPin is a method to find the locator value for the entering the secure 6 digit pin number.
     * @return It returns the key for the locator which is visible.
     */
    private String getLocatorValueForPin() {
        String strObject=null;
        try {
            boolean flag=false;
            if (isElementDisplayed(getObjectBy("login.edtPinEnterFieldGrpSCA"))) {
                strObject = "login.edtPinEnterFieldGrpSCA";
            } else if (isElementDisplayed(getObjectBy("login.edtPinEnterFieldGrp"))) {
                strObject = "login.edtPinEnterFieldGrp";
            } else if (isElementDisplayed(getObjectBy("login.pinDigits"))) {
                strObject = "login.pinDigits";
            } else {
                flag=true;

            }

            if(flag){
                Assert.fail("Pin Group not found/visible");
            }else{
                insertMessageToHtmlReport("Pin group is visible");
            }
        } catch (Exception e) {
            logError("Error in getting locator value for Pin digits");
        }
        return strObject;
    }

    /**
     * clickPinContinueButton is a method which clicks on Next button on Pin Page.
     */
    public void clickPinContinueButton(){
        try {
            if(System.getProperty("RUNTYPE").equalsIgnoreCase("MOBILEAPP")){
                getAppiumDriver().hideKeyboard();
            }
            clickJS(getObjectBy(getObjectForClickingPinNextButton()));
            logMessage("Successfully clicked next button after pin page");
            insertMessageToHtmlReport("Successfully clicked next button after pin page");
        } catch (Exception e) {
            logError("Unable to click next button after pin page");
            insertErrorMessageToHtmlReport("Unable to click next button after pin page");
            appendScreenshotToCucumberReport();
            Assert.assertFalse("Unable to click next button after pin page",true);
        }
    }

    /**
     * getObjectForClickingPinNextButton is method to verify which button is displayed on the Pin button. It verifies either
     * Next button or Confirm button or Continue button is displayed.
     * @return the object value.
     */
    private String getObjectForClickingPinNextButton() {
        String object;
        try {
            if (explicitWaitForVisibilityOfElement(getObjectBy("login.pinConfirmButton"))) {
                object="login.pinConfirmButton";
                return object;
            } else if (explicitWaitForVisibilityOfElement(getObjectBy("login.pinLoginButton"))) {
                object="login.pinLoginButton";
                return object;
            } else if (explicitWaitForVisibilityOfElement(getObjectBy("login.pinContinueButton"))) {
                object="login.pinContinueButton";
                return object;
            }
        } catch (Exception e) {
            logError("Error in getObjectForClickingPinNextButton, Error = "+e.getMessage());
        }
        return null;
    }

    /**
     * selectRegisteredDevice is a main method which has different sub methods called in it. It selects the required device
     * based on nickname provided and asserts whether device flag is selected and clicks on Next button.It also confirms whether notification sent to the selected
     * device has been approved or not.
     * @param strRegisteredDevice
     */
    public void selectRegisteredDevice(String strRegisteredDevice){
        boolean deviceFlag;
        if (explicitWaitForVisibilityOfElement(getObjectBy("login.approvalNotificationPage"))) {
            insertMessageToHtmlReport("Waiting for your approval page is displayed..As Only One Device is registered for this user");
            waitForPageLoaded();
            handleNotification();
            return;
        }
        deviceFlag=selectDevice(strRegisteredDevice);
        assertDeviceSelectionAndClickNext(strRegisteredDevice,deviceFlag);
        handleNotification();
    }


    /**
     * handleNotification is method which checks whether SCA push notification has been approved. It checks for a string
     * message stating Failed Login attempt and sets the boolean condition. If its true then it prints/log error message else
     * prints/logs success message.
     *
     */
    private void handleNotification(){
            //if (isElementDisplayed(getObjectBy(devTypeToGetProperty+"launch.approvalNotificationPage"))) {
            //WebElement element = fluentWait(getObjectBy(devTypeToGetProperty + "login.FailedLoginAttemptAfterNotification"));
        boolean condition = fluentWaitCondition(getObjectBy("login.FailedLoginAttemptAfterNotification"));
            if(condition){
                logError("SCAPushNotification is timed out");
                insertErrorMessageToHtmlReport("SCAPushNotification is timed out");
                appendScreenshotToCucumberReport();
                Assert.assertFalse("SCAPushNotification is timed out",true);
            }else{
                logMessage("SCAPushNotification is approved");
                insertMessageToHtmlReport("SCAPushNotification is approved");
                Assert.assertTrue("SCAPushNotification is approved",true);
            }
        //}
    }


    /**
     * assertDeviceSelectionAndClickNext is a method which will help in asserting whether the device with given device nickname is
     * present on device selection page. It clicks the next button if assertion is successful.
     * @param strRegisteredDevice
     * @param deviceFlag
     */
    private void assertDeviceSelectionAndClickNext(String strRegisteredDevice,boolean deviceFlag) {
        if (!deviceFlag) {
            insertErrorMessageToHtmlReport("Device with name " + strRegisteredDevice + "' is NOT present on Device selection page");
            Assert.assertFalse("Device with name " + strRegisteredDevice + "' is NOT present on Device selection page",true);
        } else {
            //waitForPageLoaded();
            for (int j = 0; j <= 2; j++) {
                try {
                    clickJS(getObjectBy("login.selectDeviceNextButton"), "Choose Device");
                    break;
                } catch (StaleElementReferenceException e) {
                    e.printStackTrace();
                }
            }
            waitForPageLoaded();
        }
    }


    /**
     * getPageCount gets the page count if there are multiple registered devices listed,usually 5 registered devices are
     * displayed in one page. But if there are more than 5 devices then they are displayed in the subsequent pages and so on.
     * It will return the page count.
     * @return the page count.
     */
    public int getPageCount(){
        int count = 0;
        try {
            String text=getText(getObjectBy("login.deviceListPageCount"));
            int position = text.indexOf("of") + 2;
            String lastPart = text.substring(position).trim();
            int val = lastPart.indexOf("<");
            if(val==-1){
                count = Integer.valueOf(text.substring(position).trim());
            }else{
                count=Integer.valueOf(lastPart.substring(0,val).trim());
            }
        } catch (Exception e) {
            logError("Error in getting page count in device selection page , Error = "+e.getMessage());
        }
        return count;
    }


    /**
     * selectDevice is the method which checks if the list of registered devices is displayed. If there are multiple
     * devices extending to the next pages as well then it gets the page count and calls the clickRegisteredDevice method
     * further with page count and registered device nickname to be selected. It will also set the device flag and return
     * its value.
     * @param registeredDevice
     * @return boolean condition
     */
    private boolean selectDevice(String registeredDevice){
        int strPageCount;
        boolean deviceFlag = false;

        try {
            if (explicitWaitForVisibilityOfElement(getObjectBy("login.deviceListPage"))) {
                strPageCount=getPageCount();
                waitForPageLoaded();
                deviceFlag = clickRegisteredDevice(strPageCount, registeredDevice);
            }
        } catch (Exception e) {
            logError("Unable to select device, Error = "+e.getMessage());
            insertErrorMessageToHtmlReport("Unable to select device");
            appendScreenshotToCucumberReport();
            Assert.assertFalse("Unable to select device",true);
        }
        return deviceFlag;
    }

    /**
     * clickRegisteredDevice is the generic method to select the registered device based on the device nickname provided.
     * It also takes the page count and verifies If the device nick name provided is not present on first page then,if not
     * then it searches for the nick name on all further pages till the device is selected. If the required device nickname is
     * not found it will print the related error message.
     * @param strPageCount
     * @param registeredDevice
     * @return the boolean condition
     */
    private boolean clickRegisteredDevice(int strPageCount, String registeredDevice ){
        boolean deviceFlag=false;
        try {
            isElementDisplayed(getObjectBy("login.lstRegisteredDevices"));
            for (int i = 1; i <= strPageCount; i++) {
                List<WebElement> lstDevices = findElements(getObjectBy("login.lstRegisteredDevices"));
                for (WebElement device : lstDevices) {
                    if(registeredDevice.isEmpty()){
                        for (int j = 0; j <= 2; j++) {
                            try {
                                scrollToView(device);
                                device.click();
                                break;
                            } catch (StaleElementReferenceException e) {
                                logWarning("Issue in clicking registered device, Error = "+e.getMessage());
                            }
                        }
                        deviceFlag = true;
                        logMessage("First Device is selected on Device selection page ");
                        insertMessageToHtmlReport("First Device is selected on Device selection page ");
                        appendScreenshotToCucumberReport();
                        break;
                    }else{
                        String strDeviceName = device.getText();
                        if (strDeviceName.contains(registeredDevice)) {
                            for (int j = 0; j <= 2; j++) {
                                try {
                                    scrollToView(device);
                                    device.click();
                                    break;
                                } catch (StaleElementReferenceException e) {
                                    logWarning("Issue in clicking registered device, Error = "+e.getMessage());
                                }
                            }
                            deviceFlag = true;
                            logMessage(" Device with name '" + registeredDevice + "' selected on Device selection page ");
                            insertMessageToHtmlReport(" Device with name '" + registeredDevice + "' selected on Device selection page ");
                            appendScreenshotToCucumberReport();
                            break;
                        }
                    }

                }
                if (!deviceFlag) {
                    if (i<strPageCount) {
                        waitForVisibilityOfElement(getObjectBy("login.deviceListPageNext"));
                        scrollToView(getObjectBy("login.deviceListPageNext"));
                        clickJS(getObjectBy("login.deviceListPageNext"));
                    }
                }else{
                    break;
                }
            }
        } catch (InterruptedException e) {
            logError("Unable to click registered device from the list, Error = "+e.getMessage());
        }
        return deviceFlag;
    }

    /**
     * verifyHomepage is generic method to verify if the home page is visible successfully. It verifies if the accounts menu
     * option is displayed in case of desktop browser whereas in case of mobile device application or mobile browser it verifies
     * if Accounts option is displayed.
     */
    public void verifyHomepage() {
        try {
            explicitWaitForVisibilityOfElement(getObjectBy(devTypeToGetProperty+"login.HomePage"));
            logMessage("Logged in Successfully, Home Page is visible");
            insertMessageToHtmlReport("Logged in Successfully , Home Page is visible");
            appendScreenshotToCucumberReport();
            Assert.assertTrue("Home page is displayed", true);
        } catch (Exception e){
            logError("Error in verifying home page , Error = "+e.getMessage());
            appendScreenshotToCucumberReport();
            Assert.fail("Home page not displayed" + e);
        }
    }


    /**
     * acceptTermsAndCondition is a method to check if Home Page is not displayed and it verifies if page title displayed
     * is having "Terms & Conditions". If yes, then it clicks on Continue button on Terms and Condition page.
     */
    private void acceptTermsAndCondition(){
        try {
            if (!explicitWaitForVisibilityOfElement(getObjectBy(devTypeToGetProperty + "login.HomePage"))) {
                if (explicitWaitForVisibilityOfElement(getObjectBy("login.TnC"))) {
                    appendScreenshotToCucumberReport();
                    if (getText(getObjectBy("login.TnC")).contains("Terms and Conditions")) {
                        clickJS(getObjectBy("login.btnContinue"));
                    }
                    logInformationForTncPage();
                }else if(explicitWaitForVisibilityOfElement(getObjectBy("login.TncLink"))) {
                    appendScreenshotToCucumberReport();
                    clickJS(getObjectBy("login.TncContinue"));
                    logInformationForTncPage();
                }else if (explicitWaitForVisibilityOfElement(getObjectBy("login.TnCPageTitle"))
                        || explicitWaitForVisibilityOfElement(getObjectBy("login.TncPageTitle1"))
                        || explicitWaitForVisibilityOfElement(getObjectBy("login.TncPageTitle2"))) {

                    appendScreenshotToCucumberReport();
                    clickJS(getObjectBy("login.TnCContinueButton"));
                    logInformationForTncPage();
                }else{
                    logMessage("Terms and Condition Page is not visible");
                }
            }
        } catch (Exception e) {
            logError("Error in tnc page, Error = "+e.getMessage());
            insertErrorMessageToHtmlReport("Error in tnc page");
            appendScreenshotToCucumberReport();
            Assert.assertTrue("Error in tnc page, Error",false);
        }
    }

    /**
     * This is the sub method for TnC page handling and it logs the information about that page
     */
    private void logInformationForTncPage(){
        try {
            insertMessageToHtmlReport("Terms And Conditions page exist");
            logMessage("Terms And Conditions page exist");
            waitForPageLoaded();
            waitForJQueryLoad();
            if (explicitWaitForVisibilityOfElement(getObjectBy("login.continueToHome"))) {
                clickJS(getObjectBy("login.continueToHome"));
                waitForPageLoaded();
            } else if (explicitWaitForVisibilityOfElement(getObjectBy("login.TnCContinueButton"))) {
                clickJS(getObjectBy("login.TnCContinueButton"));
                waitForPageLoaded();
            } else if (explicitWaitForVisibilityOfElement(getObjectBy("login.continueToHomeNew"))) {
                scrollToView(getObjectBy("login.continueToHomeNew"));
                clickJS(getObjectBy("login.continueToHomeNew"));
                waitForPageLoaded();
            }
        } catch (Exception e) {
            logError("Error in logging information about TnC page, Error = "+e.getMessage());
        }
    }

    /**
     * This will handle any interrupt image page during login into application
     */
    public void verifyInterruptsImage() {
        try {
            if (!explicitWaitForVisibilityOfElement(getObjectBy(devTypeToGetProperty + "login.HomePage"))) {
                if (explicitWaitForVisibilityOfElement(getObjectBy("login.interruptImage"))) {
                    clickJS(getObjectBy("login.continueToHome"));
                    waitForPageLoaded();
                }
                if (explicitWaitForVisibilityOfElement(getObjectBy("login.continueToHomeNew"))) {
                    clickJS(getObjectBy("login.continueToHomeNew"));
                    waitForPageLoaded();
                }
            }else{
                logMessage("Marketing Interrupt page is not present");
                appendScreenshotToCucumberReport();
            }
        } catch (Exception e) {
            logError("Error in verifying Interrupt image, Error = "+e.getMessage());
            insertErrorMessageToHtmlReport("Error in verifying Interrupt image");
            appendScreenshotToCucumberReport();
            Assert.assertTrue("Error in verifying Interrupt image",false);
        }
    }

    /**
     * This method handles the digital pay page
     */
    private void digitalPayInterrupt(){
        try {
            if (explicitWaitForVisibilityOfElement(getObjectBy("login.digitalPayInterrupt"))) {
                clickJS(getObjectBy("login.digitalPayInterrupt"));
                waitForPageLoaded();
                logMessage("Digital Pay interrupt clicked successfully");
                insertMessageToHtmlReport("Digital Pay interrupt clicked successfully");
                appendScreenshotToCucumberReport();
            }else{
                logMessage("Digital pay Interrupt page is not present");
            }
        } catch (Exception e) {
            logMessage("Error in clicking digital Pay interrupt , Error = "+e.getMessage());
            appendScreenshotToCucumberReport();
            Assert.assertTrue("Error in clicking digital Pay interrupt",false);
        }
    }


    /**
     * continueToDashboard is a method to click on Continue To Home button on any intermediate page before login page.
     */
    public void continueToDashboard() {
        try {
            boolean image = explicitWaitForVisibilityOfElement(getObjectBy("image.interruptImage"));
            if (image) {
                clickJS(getObjectBy("button.continueToHome"));
            }
        } catch (Exception exception) {
            new WebDriverWait(driver, 20)
                    .until(ExpectedConditions
                            .visibilityOfElementLocated(getObjectBy("button.continueToHome")))
                    .click();
        }
    }


    /**
     * logout is a generic method to log out of the UXP application. It can be used in case of any of the three platforms,
     * Desktop Browser, Mobile Browser, Mobile device Application.
     * For Logging out of desktop browser application, it directly clicks on Logout button,but in case of Mobile browser
     * and Mobile device application it clicks on Profile button first and then clicks on log out button. Further to this,
     * it clicks on Yes log me out option on the pop up and verifies the successful log out text message appearing on screen.
     */
    public void logout(){
        try {
            if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
                if(explicitWaitForElementTobeClickable(getObjectBy(devTypeToGetProperty+"home.btnLogout"))){
                    clickJS(getObjectBy(devTypeToGetProperty+"home.btnLogout"));
                }else{
                    Assert.assertTrue("Logout button not present",false);
                }
            }else{
                if(explicitWaitForElementTobeClickable(getObjectBy("home.profile"))){
                    clickJS(getObjectBy("home.profile"));
                }else{
                    Assert.assertTrue("Profile button not present",false);
                }
                waitForVisibilityOfElement(getObjectBy(devTypeToGetProperty+"home.btnLogout"));
                clickJS(getObjectBy(devTypeToGetProperty+"home.btnLogout"));
            }
            waitForVisibilityOfElement(getObjectBy("home.btnPopLogout"));
            appendScreenshotToCucumberReport();
            clickJS(getObjectBy("home.btnPopLogout"));

            String expectedLogoutMsg = "You have been logged out.";
            String actualLogoutMsg = getText(getObjectBy("home.LogoutMessage"));
            if (actualLogoutMsg.equals(expectedLogoutMsg)) {
                logMessage("successfully logged out of the application");
                insertMessageToHtmlReport("successfully logged out of the application");
                appendScreenshotToCucumberReport();
            } else {
                logError("Logging out is failed");
                insertErrorMessageToHtmlReport("Logging out is failed");
                appendScreenshotToCucumberReport();
            }
        } catch (Exception e) {
            logError("Error in logout method, Error = "+e.getMessage());
        }
    }

    /**
     * This method generates deviceName
     * for App execution : if DEVICE_NICKNAME parameter is empty then generate name from
     * deviceName_Pattern key present in loginRepo.properties file
     * for Web execution : if DEVICE_NICKNAME parameter is empty then it will return empty name
     * else it will select the name present in DEVICE_NICKNAME parameter
     * @return device name
     */
    private String getDeviceName(){
        String generatedName="";
        try {
            String deviceName = System.getProperty("DEVICE_NICKNAME");
            if (System.getProperty("RUNTYPE").equalsIgnoreCase("MOBILEAPP")){
                if(deviceName.isEmpty()){
                    String pattern = System.getProperty("deviceName_Pattern");
                    //ACC_UDID_ENVIRONMENT
                    StringBuilder builder = new StringBuilder();
                    String[] arr = pattern.split("_");
                    for (String val:arr) {
                        if(val.equalsIgnoreCase("UDID")||val.equalsIgnoreCase("ENVIRONMENT")){
                            if(val.equalsIgnoreCase("UDID")){
                                String var = System.getProperty(val);
                                int totalLen = var.length();
                                builder.append(var.substring(totalLen-5));
                            }else{
                                builder.append(System.getProperty(val));
                            }
                        }else{
                            builder.append(val);
                        }
                    }
                    generatedName= builder.toString();
                }else{
                    generatedName=deviceName;
                }
            }else{
                if(deviceName.isEmpty()){
                    generatedName="";
                }else {
                    generatedName=deviceName;
                }
            }
        } catch (Exception e) {
            logError("Failed to generate device name, Error= "+e.getMessage());
        }
        logMessage("Device Name = "+generatedName);
        return generatedName;

    }



}

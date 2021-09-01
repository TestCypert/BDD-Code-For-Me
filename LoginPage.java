package com.boi.grp.pageobjects.login;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.pageobjects.BasePageForBrowsers;
import com.boi.grp.utilities.UIResusableLibrary;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

/**
 * Function/Epic:
 * Class: HomePage
 * Developed on: 12/07/2018
 * Developed by: C111947
 * Update on     Updated by    Reason
 */
public class LoginPage extends BasePageForAllPlatform {

    public UIResusableLibrary cardsFunctionalComponent;

    @FindBy(how = How.XPATH, using = "//*[@id='details-button']")
    WebElement advance;

    @FindBy(how = How.XPATH, using = "//*[@id='proceed-link']")
    WebElement proceed;

    @FindBy(how = How.XPATH, using = "//*[contains(@name,'USERID')]")
    WebElement userName;

    @FindBy(how = How.XPATH, using = "//*[@name='overridelink']")
    WebElement advanceIE;

    //TODO : IE continue
    public LoginPage(WebDriver driver) {
        super(driver);
        cardsFunctionalComponent = new UIResusableLibrary(driver);
        PageFactory.initElements(driver, this);

        if (System.getProperty("PLATFORM").equalsIgnoreCase("WINDOWS")) {
            driver.manage().window().maximize();
        }

    }


    /**
     * Function : To click Launch Application
     * Created by : C966003
     * Update on     Updated by    Reason
     * 12/04/2019    C966003       Done code clean up activity
     *
     * @param usName
     */
    public void launchApplication(String sURL, String usName, String bDate, String PIN) throws Exception {

        switch (System.getProperty("PLATFORM").trim().toUpperCase()) {
            case "WINDOWS":
                switch (System.getProperty("TYPE").trim().toUpperCase()) {
                    case "BROWSER":
                        String strAppUrl = System.getProperty("URL").trim();
                        System.out.println("****URL is****** " + strAppUrl);
                        switch (System.getProperty("PREFERRED_BROWSER").trim().toUpperCase()) {
                            case "CHROME":
                                System.out.println("****URL is****** " + strAppUrl);
                                desktopBrowserLaunch(strAppUrl);
                                if (isElementDisplayed(advance)) {
                                    clickJS(advance); //, "Advance"
                                    appendScreenshotToCucumberReport();
                                    logMessage("InvokeChannelApplication: Advance link is clicked ");
                                    waitForPageLoaded();
                                    clickJS(proceed); //, "Proceed link"
                                    logMessage("InvokeChannelApplication: Proceed link is clicked ");
                                } else {
                                    Thread.sleep(3 * 1000);
                                    clickJS(advanceIE); //, "Advance IE"
                                    logMessage("InvokeChannelApplication: Advance link is clicked ");
                                    waitForPageLoaded();
                                }

                                break;
                            case "IE":
                                desktopBrowserLaunch(strAppUrl);
                                Thread.sleep(3 * 1000);
                                //driver.findElement(By.xpath("//*[@name='overridelink']")).click();
                                clickJS(advanceIE); //, "Advance IE"
                                logMessage("InvokeChannelApplication: Advance link is clicked ");
                                waitForPageLoaded();
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
            case "MOBILECENTER":
                switch (System.getProperty("TYPE").trim().toUpperCase()) {
                    case "APPLICATION":
                        switch (System.getProperty("DEVICEPLATFORM").trim().toUpperCase()) {
                            case "ANDROID":
                                setRelevantWebViewTab();
                                waitForPageLoaded();
                                break;
                            case "IOS":
                                setRelevantWebViewTab();
                                waitForPageLoaded();
                                Thread.sleep(6 * 1000);
                                scrollToView(getObject(devTypeToGetProperty + "login.loginButton"));
                                appendScreenshotToCucumberReport();
                                break;
                            default:
                                break;
                        }
                        break;
                    case "BROWSER":
                        switch (System.getProperty("DEVICEPLATFORM").trim().toUpperCase()) {
                            case "ANDROID":
                                switch (System.getProperty("PREFERRED_BROWSER").trim().toUpperCase()) {
                                    case "CHROME":
                                        String strAppUrl = System.getProperty("URL").trim();
                                        System.out.println("****URL is****** " + strAppUrl);
                                        AndroidDriver andriodDriver = (AndroidDriver) driver;
                                        Set context = andriodDriver.getContextHandles();
                                        System.out.println(context);
                                        andriodDriver.context("CHROMIUM");
                                        Thread.sleep(3000);
                                        driver.get(strAppUrl);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case "IOS":
                                switch (System.getProperty("PREFERRED_BROWSER").trim().toUpperCase()) {
                                    case "SAFARI":
                                        String strAppUrl = System.getProperty("URL").trim();
                                        System.out.println("****URL is****** " + strAppUrl);
                                        driver.get(strAppUrl);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            default:
                                break;
                        }
                        break;

                    default:
                        break;
                }
                appendScreenshotToCucumberReport();
                logInfo("Mobilecentre Webbrowser launched");
                break;

        }
        //Login welcome screen
        clickJS(getObject(devTypeToGetProperty + "login.loginButton"));
        appendScreenshotToCucumberReport();
        Thread.sleep(5000);

        if (isElementDisplayed(getObject(devTypeToGetProperty + "launch.edtUserID"))) {
            appendScreenshotToCucumberReport();
            clickJS((getObject(devTypeToGetProperty + "launch.edtUserID"))); //, "UserID"
            setValue((getObject(devTypeToGetProperty + "launch.edtUserID")), usName);
            logMessage("UserName is entered in the field");
            click(getObject(devTypeToGetProperty + "launch.edtEnterYourDateOfBirth"));
            setValue(getObject(devTypeToGetProperty + "launch.edtEnterYourDateOfBirth"), bDate);
            appendScreenshotToCucumberReport();
            logMessage("BirthDate is entered in the field");
            // waitForPageLoaded();

            //Thread.sleep(5000);
            clickJS(getObject(devTypeToGetProperty + "login.AmendedContinue")); //, "Continue link"
            appendScreenshotToCucumberReport();
            logMessage("Invoke URL: Continue link is clicked ");
        } else {
            logMessage("Invoke URL: Continue link is NOT clicked ");
        }
        //Allure.step("Launch app"+sURL,Status.PASSED);


        //cardsFunctionalComponent.EnterPINdetails(PIN);

        Thread.sleep(10000);

        String[] arrPin = PIN.split("");
        String strObject = "";

        //////       waitForJQueryLoad();
        if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"), 5)) {
            strObject = "launch.edtPinEnterFieldGrpSCA";

        } else if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrp"), 2)) {
            strObject = "launch.edtPinEnterFieldGrp";
        } else if (isElementDisplayed(getObject("login.pinDigits"), 2)) {
            strObject = "login.pinDigits";
        } else {

        }

        List<WebElement> lstPinEnterFieldGrp = findElements(getObject(strObject));
        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
            }
        }
        appendScreenshotToCucumberReport();
        Thread.sleep(4000);
        clickJS(getObject(devTypeToGetProperty + "home.btnLogin"));//code for click Loginbutton for desktop browser
        Thread.sleep(4000);
        //clickJS(getObject("launch.btndeviceNickName"), "Device");
        appendScreenshotToCucumberReport();
        //clickJS(getObject(devTypeToGetProperty +"home.btnNext"));
        clickJS(getObject(devTypeToGetProperty + "launch.btndeviceNickName"));
        System.out.println("**** DEVICE NICKNAME");
        Thread.sleep(6000);
        //clickJS(getObject("launch.btnChooseDevices"), "Choose Devices");
        clickJS(getObject(devTypeToGetProperty + "launch.btnChooseDevices"));
        Thread.sleep(60000);
        appendScreenshotToCucumberReport();
    }


    /**
     * Function : To click Launch Application (Overriding method)
     * Created by : C114323
     * Update on     Updated by    Reason
     * 18/05/2021    C114323       create new overriding method
     *
     * @param userType
     */
    public void launchApplication(String userType) throws Exception {
        String usData = getLoginData(userType);
        String[] arrUsData = usData.split(":");
        String usName = arrUsData[0];
        String bDate = arrUsData[1];
        String PIN = arrUsData[2];
        String L4D = arrUsData[3];
        String deviceName = arrUsData[4];

        //@Value(("#{'${userType+"user"}'.split('::')")
        switch (System.getProperty("PLATFORM").trim().toUpperCase()) {
            case "WINDOWS":
                switch (System.getProperty("TYPE").trim().toUpperCase()) {
                    case "BROWSER":
                        String strAppUrl = System.getProperty("URL").trim();
                        System.out.println("****URL is****** " + strAppUrl);
                        switch (System.getProperty("PREFERRED_BROWSER").trim().toUpperCase()) {
                            case "CHROME":
                                System.out.println("****URL is****** " + strAppUrl);
                                desktopBrowserLaunch(strAppUrl);
                                if (isElementDisplayed(advance)) {
                                    clickJS(advance); //, "Advance"
                                    //appendScreenshotToCucumberReport();
                                    logMessage("InvokeChannelApplication: Advance link is clicked ");
                                    waitForPageLoaded();
                                    clickJS(proceed); //, "Proceed link"
                                    logMessage("InvokeChannelApplication: Proceed link is clicked ");
                                } else {
                                    Thread.sleep(3 * 1000);
                                    clickJS(advanceIE); //, "Advance IE"
                                    logMessage("InvokeChannelApplication: Advance link is clicked ");
                                    waitForPageLoaded();
                                }
                                loginBrowser(usName, bDate, PIN, deviceName);

                                break;
                            case "IE":
                                desktopBrowserLaunch(strAppUrl);
                                Thread.sleep(3 * 1000);
                                //driver.findElement(By.xpath("//*[@name='overridelink']")).click();
                                clickJS(advanceIE); //, "Advance IE"
                                logMessage("InvokeChannelApplication: Advance link is clicked ");
                                waitForPageLoaded();
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
            case "MOBILECENTER":
                switch (System.getProperty("TYPE").trim().toUpperCase()) {
                    case "APPLICATION":
                        switch (System.getProperty("DEVICEPLATFORM").trim().toUpperCase()) {
                            case "ANDROID":
                                setRelevantWebViewTab();
                                waitForPageLoaded();
                                loginApp(usName, bDate, PIN);
                                break;
                            case "IOS":
                                setRelevantWebViewTab();
                                //waitForPageLoaded();
                                Thread.sleep(6 * 1000);
                                loginApp(usName, bDate, PIN);
                                break;
                            default:
                                break;
                        }
                        break;
                    case "BROWSER":
                        switch (System.getProperty("DEVICEPLATFORM").trim().toUpperCase()) {
                            case "ANDROID":
                                switch (System.getProperty("PREFERRED_BROWSER").trim().toUpperCase()) {
                                    case "CHROME":
                                        String strAppUrl = System.getProperty("URL").trim();
                                        System.out.println("****URL is****** " + strAppUrl);
                                        AndroidDriver andriodDriver = (AndroidDriver) driver;
                                        Set context = andriodDriver.getContextHandles();
                                        System.out.println(context);
                                        andriodDriver.context("CHROMIUM");
                                        Thread.sleep(3000);
                                        driver.get(strAppUrl);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case "IOS":
                                switch (System.getProperty("PREFERRED_BROWSER").trim().toUpperCase()) {
                                    case "SAFARI":
                                        String strAppUrl = System.getProperty("URL").trim();
                                        System.out.println("****URL is****** " + strAppUrl);
                                        driver.get(strAppUrl);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            default:
                                break;
                        }
                        break;

                    default:
                        break;
                }
                appendScreenshotToCucumberReport();
                logInfo("Mobilecentre Webbrowser launched");
                break;

        }
    }

    public void loginBrowser(String usName, String bDate, String PIN, String deviceName) {
        //Login welcome screen
        try {
            clickJS(getObject(devTypeToGetProperty + "login.loginButton"));
            //appendScreenshotToCucumberReport();
            Thread.sleep(5000);

            if (isElementDisplayed(getObject(devTypeToGetProperty + "launch.edtUserID"))) {
                //appendScreenshotToCucumberReport();
                clickJS((getObject(devTypeToGetProperty + "launch.edtUserID"))); //, "UserID"
                setValue((getObject(devTypeToGetProperty + "launch.edtUserID")), usName);
                logMessage("UserName is entered in the field");
                click(getObject(devTypeToGetProperty + "launch.edtEnterYourDateOfBirth"));
                setValue(getObject(devTypeToGetProperty + "launch.edtEnterYourDateOfBirth"), bDate);
                appendScreenshotToCucumberReport();
                logMessage("BirthDate is entered in the field");
                clickJS(getObject(devTypeToGetProperty + "login.AmendedContinue")); //, "Continue link"
                //appendScreenshotToCucumberReport();
                logMessage("Invoke URL: Continue link is clicked ");
            } else {
                logMessage("Invoke URL: Continue link is NOT clicked ");
            }
            Thread.sleep(10000);

            String[] arrPin = PIN.split("");
            String strObject = "";

            if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"), 5)) {
                strObject = "launch.edtPinEnterFieldGrpSCA";

            } else if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrp"), 2)) {
                strObject = "launch.edtPinEnterFieldGrp";
            } else if (isElementDisplayed(getObject("login.pinDigits"), 2)) {
                strObject = "login.pinDigits";
            } else {

            }

            List<WebElement> lstPinEnterFieldGrp = findElements(getObject(strObject));
            //Entering values for only enabled fields
            for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
                if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                    lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                }
            }
            appendScreenshotToCucumberReport();
            Thread.sleep(4000);
            clickJS(getObject(devTypeToGetProperty + "home.btnLogin"));//code for click Loginbutton for desktop browser
            Thread.sleep(4000);
            //clickJS(getObject("launch.btndeviceNickName"), "Device");
            //appendScreenshotToCucumberReport();

            boolean flag = false;
            do {
                flag = selectValueFromList(getObject("launch.browserDeviceList"), deviceName);
                if (!flag)
                    clickJS(getObject("launch.btnPageNext"), "Next Page");
            } while (!flag);
            clickJS(getObject(devTypeToGetProperty + "launch.btnChooseDevices"), "Choose the Device");

//                            if(isElementDisplayed(getObject(devTypeToGetProperty +"launch.btndeviceNickName"))){
//                            clickJS(getObject(devTypeToGetProperty +"launch.btndeviceNickName"));
//                            System.out.println("**** DEVICE NICKNAME");
//                            clickJS(getObject(devTypeToGetProperty +"launch.btnChooseDevices"));
            Thread.sleep(10000);
            //clickJS(getObject("launch.btnChooseDevices"), "Choose Devices");

            appendScreenshotToCucumberReport();

            logMessage("User login in browser Successfully");
            injectMessageToCucumberReport(usName + "User login in browser Successfully");
            appendScreenshotToCucumberReport();
            Allure.step(usName + "User login in browser Successfully", Status.PASSED);
        } catch (Exception e) {
            logError("Error Occured inside loginBrowser " + e.getMessage());
            injectWarningMessageToCucumberReport("Failure in Login to browser " + e.getMessage());
            appendScreenshotToCucumberReport();
            Allure.step("Login to browser failed " + usName, Status.FAILED);
        }
    }

    public void loginApp(String usName, String bDate, String PIN) throws InterruptedException {
        try {

            waitForPageLoaded();
            waitForJQueryLoad();


            //     if(isElementDisplayed(getObject("launch.LoginHeading"),9)){
            logMessage("Multiple user profiles page");
            boolean bflag = false;
            Thread.sleep(6 * 1000);
            //List<WebElement> elm = driver.findElements(getObject("launch.userlist"));
            List<WebElement> elm = driver.findElements(By.xpath("//span[@class='boi-sca-user-answer-part']"));
            //System.out.println("USerList-----"+elm.size());
            //System.out.println("Element-----"+elm.toString());
            for (int i = 0; i < elm.size(); i++) {
                String ID = elm.get(i).getText();
                if (ID.equals(usName)) {
                    if (elm.size() >= 5) {
                        scrollToView(By.xpath("//span[text()='" + usName + "']"));
                    }
                    //scrollToView(getObject("xpath~//span[text()='" +userID+ "']"));
                    elm.get(i).click();
                    Thread.sleep(6 * 1000);
                    logMessage("UserSelection with User id '" + usName + "' selected");
                    appendScreenshotToCucumberReport();
                    bflag = true;
                    break;
                }
            }


            Thread.sleep(10000);
            String[] arrPin = PIN.split("");
            String strObject = "";
            if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"), 5)) {
                strObject = "launch.edtPinEnterFieldGrpSCA";

            } else if (isElementDisplayed(getObject("launch.edtPinEnterFieldGrp"), 2)) {
                strObject = "launch.edtPinEnterFieldGrp";
            } else if (isElementDisplayed(getObject("login.pinDigits"), 2)) {
                strObject = "login.pinDigits";
            } else {

            }

            List<WebElement> lstPinEnterFieldGrp = findElements(getObject(strObject));
            //Entering values for only enabled fields
            for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
                if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                    lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                }
            }
            Thread.sleep(4000);
            //clickJS(getObject("home.btnLogin"));
            clickJS(getObject("launch.LoginInButton"));
            appendScreenshotToCucumberReport();
            Thread.sleep(10000);
            //driver.switchTo().alert().accept();

            /******************** If GPay Screen is displayed ***********************************
             if (isElementDisplayed(getObject("launch.GPayHome"))) {
             clickJS(getObject("launch.GPayHome"), "Click on Home button");
             }else{
             logMessage("GPay screen is not displayed");
             }
             */

            logMessage("User login in app successfully");
            injectMessageToCucumberReport(usName + "User login in appa Successfully");
            appendScreenshotToCucumberReport();
            Allure.step(usName + "User login in app Successfully", Status.PASSED);

        } catch (Exception e) {
            logError("Error Occured inside loginApp " + e.getMessage());
            injectWarningMessageToCucumberReport("Failure in Login " + e.getMessage());
            appendScreenshotToCucumberReport();
            Allure.step("Login to App NOT successful " + usName, Status.FAILED);
        }

    }


    public void desktopBrowserLaunch(String strAppUrl) {
        try {
            Thread.sleep(3000);
            driver.get(strAppUrl);
            //appendScreenshotToCucumberReport();
            driver.manage().window().maximize();
            Thread.sleep(5000);
            waitForBrowserToCompleteProcessing();
            waitForPageLoaded();
            logMessage("InvokeChannelApplication: " + strAppUrl + "is launched successfully");
            Thread.sleep(5000);
            //appendScreenshotToCucumberReport();
        } catch (InterruptedException e) {
            logError("Error in launching desktop URL");
        }
    }

}

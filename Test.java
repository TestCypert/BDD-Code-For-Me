package com.boi.grp.runners;

import com.boi.grp.hooks.Hooks;
import com.boi.grp.pageobjects.BasePage;
import com.boi.grp.request.Request;
import com.boi.grp.request.Root;
import com.boi.grp.utilities.FlatMapUtil;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by C112083 on 16/10/2020.
 */
public class Test extends BasePage{
    WebDriver driver;

    public Test(WebDriver driver){
        this.driver=driver;
    }

    public static void main(String[] args) throws Exception {
        List<String> lit=new ArrayList<String>();
        lit.add("WEBVIEW_com.bankofireland.boiinapp");
        lit.add("WEBVIEW_com.bankofireland.tcmb");
        lit.add("WEBVIEW_");

        String bjj="WEBVIEW_chrome";

        if(lit.contains(bjj)){
            System.out.println("");
        }



   /*     String json1 = "{\"name\":\"ABC\", \"city\":\"XYZ\", \"state\":\"CA\"}";

        String json2 = "{\"city\":\"XYZ\", \"street\":\"123 anyplace\", \"name\":\"ABC\"}";*/

        String json1="{\n" +
                "    \"menu\": {\n" +
                "        \"id\": \"file1\",\n" +
                "        \"popup\": {\n" +
                "            \"menuitem\": {\n" +
                "                \"menuitem-2\": {\n" +
                "                    \"menuitem-2.1\": \"sometext\",\n" +
                "                    \"menuitem-2.2\": \"sometext\",\n" +
                "                    \"menuitem-2.3\": {\n" +
                "                        \"menuitem-2.3.1\": \"sometext\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"value\": \"File\"\n" +
                "    }\n" +
                "}";

        String json2="{\n" +
                "    \"menu\": {\n" +
                "        \"id\": \"file\",\n" +
                "        \"popup\": {\n" +
                "            \"menuitem\": {\n" +
                "                \"menuitem-1\": \"sometext-1\",\n" +
                "                \"menuitem-2\": {\n" +
                "                    \"menuitem-2.1\": \"sometext-2.1\",\n" +
                "                    \"menuitem-2.2\": \"sometext\",\n" +
                "                    \"menuitem-2.3\": {\n" +
                "                        \"menuitem-2.3.1\": \"sometext\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"value\": \"File\"\n" +
                "    }\n" +
                "}";

        Gson g = new Gson();
        Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> firstMap = g.fromJson(json1, mapType);
        Map<String, Object> secondMap = g.fromJson(json2, mapType);

//        MapDifference<String, Object> diff = Maps.difference(firstMap, secondMap);
        //System.out.println(Maps.difference(firstMap, secondMap));

        Map<String, Object> leftFlatMap = FlatMapUtil.flatten(firstMap);
        Map<String, Object> rightFlatMap = FlatMapUtil.flatten(secondMap);

        MapDifference<String, Object> difference = Maps.difference(leftFlatMap, rightFlatMap);
        List<String> list = new ArrayList<String>();

        //System.out.println("**** for left mismatch");
        difference.entriesOnlyOnLeft()
                .forEach((key, value) -> System.out.println(key + ": " + value));

        difference.entriesOnlyOnLeft()
                .forEach((key, value) -> list.add("Present only in Expected Json "+key + ": " + value));


        difference.entriesOnlyOnRight()
                .forEach((key, value) -> System.out.println(key + ": " + value));

        difference.entriesOnlyOnRight()
                .forEach((key, value) -> list.add("Present only in Actual Json "+key + ": " + value));

        System.out.println("*** for right mismatch \n");

        difference.entriesDiffering()
                .forEach((key, value) -> System.out.println(key + ": " + value));

        difference.entriesDiffering()
                .forEach((key, value) -> list.add("Mismatch on common values "+key + ": " + value));

        System.out.println("*** for common mismatch \n");

        System.out.println(list.toString());



       /* Root fff = Root.builder().request(Request.builder().method("root").url("root url").build()).build();
        Request payLoad = Request.builder().method("request").url("request url").build();*/

        Hooks hooks = new Hooks();
        hooks.loadGlobalConfig();
        hooks.failSafePropertyGenenration();
        WebDriver driver=hooks.GetDriver();
        Test obj=new Test(driver);
        obj.invokeApplication();
    }




    public void invokeApplication() throws Exception {

            waitForJQueryLoad();
            //report.updateTestLog("invokeApp", "::::Launching App::::", Status_CRAFT.SCREENSHOT);
            setRelevantWebViewTab();
            //To Get the installed app version
            // sca.getInstalledAppVersion();
            functionToHandleLogin();
        //Method to install app

    }

    public void screen(String name) {
        TakesScreenshot tt = (TakesScreenshot) driver;
        File src = tt.getScreenshotAs(OutputType.FILE);
        File dest = new File("C:\\All softwares\\"+name+".png");
        try {
            FileUtils.copyFile(src,dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeNativeToWebview() {
        Set<String> availableContexts = getContextHandles();
        System.out.println("Total No of Context Found After we reach to WebView = " + availableContexts.size());
        for (String context : availableContexts) {
            if (context.contains("WEBVIEW_com.bankofireland.tcmb")||context.contains("WEBVIEW_Temenos")||context.contains("WEBVIEW_")) {
                System.out.println("Context Name is " + context);
                context(context);
                break;
            }
        }
    }

    public Set<String> getContextHandles() {
        return ((AppiumDriver) driver).getContextHandles();
    }

    public WebDriver context(String arg0) {
        return ((AppiumDriver) driver).context(arg0);
    }

    public void setRelevantWebViewTab() throws Exception {

        boolean bflag = false;
        changeNativeToWebview();
        try {
            Iterable<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                driver.switchTo().window(windowHandle);
                if (driver.getTitle().equals("Bank of Ireland") || driver.getTitle().equals("Temenos")||driver.getTitle().contains("Bank of Ireland")) {
                    bflag = true;
                    //report.updateTestLog("Verify PIN digits null validation", "WebView with browser title 'Bank of Ireland' set", Status_CRAFT.DONE);
                    break;
                }
            }
            if (!bflag) {
                //report.updateTestLog("Verify PIN digits null validation", "WebView with browser title 'Bank of Ireland' not found", Status_CRAFT.DONE);
            }

        } catch (Exception e) {
            //report.updateTestLog("Verify PIN digits null validation", "WebView with browser title 'Bank of Ireland' not found", Status_CRAFT.DONE);
        }
    }

    public void functionToHandleLogin() throws Exception {
        waitForPageLoaded();
        waitForJQueryLoad();
        if(isElementDisplayed(By.xpath("//h4[@class='ecDIB  '][text()='Log in']"),9)){
            String userID ="161695";
            boolean bflag = false;
              List<WebElement> elm = findElements(By.xpath("//span[@class='boi-sca-user-answer-part']"));
            for (int i = 0; i < elm.size(); i++) {
                String ID = elm.get(i).getText();
                if (ID.equals(userID)) {
                    scrollToView(By.xpath("//span[text()='" +userID+ "']"));
                    elm.get(i).click();
                    bflag = true;
                    break;
                }
            }
            if(!bflag){
                clickJS(By.xpath("//button[@title='Add user']"), "Add user");
                deviceProvisioningValidations();
            }
        }
        else if(isElementDisplayed(By.xpath("//div[@class='tc-float-left  ']/descendant::span[text()=' Log in with another ID']"),3)) {
            clickJS(By.xpath("//div[@class='tc-float-left  ']/descendant::span[text()=' Log in with another ID']"), "Another User Id link");
            deviceProvisioningValidations();
        }else{
            deviceProvisioningValidations();
        }

    }


    public void clickJS(By locator, String alt) throws InterruptedException {

        boolean blnClicked = false;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();",
                driver.findElement(locator));
        int waitTime=10;
        if (isElementDisplayed(locator, waitTime)) {
            try {
                WebElement element = driver.findElement(locator);
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
            } catch (UnreachableBrowserException e) {
                e.printStackTrace();
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
            }
            blnClicked = true;
        }
//        waitForPageLoaded();
//        waitForJQueryLoad();
        try {
            if (blnClicked) {

            } else {

            }
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }



    public void waitForJQueryLoad() {
        //Wait for jQuery to load
        WebDriver tempDriver = driver;
        WebDriverWait wait = new WebDriverWait(tempDriver, 20);
        JavascriptExecutor jsExec = (JavascriptExecutor) tempDriver;
        try{
            Thread.sleep(1000);
            ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) driver)
                    .executeScript("return jQuery.active") == 0);

            //Get JQuery is Ready
            boolean jqueryReady = (Boolean) jsExec.executeScript("return jQuery.active==0");

            //Wait JQuery until it is Ready!
            if (!jqueryReady) {
                System.out.println("JQuery is NOT Ready!");
                //Wait for jQuery to load
                wait.until(jQueryLoad);
            } else {
                System.out.println("JQuery is Ready!");
            }
        }catch (Exception e){
            System.out.println("javascript error: jQuery is not defined");
        }

    }

    public boolean isElementDisplayed(By by, int timeout) throws InterruptedException {

        boolean elementPresent = true;
        int count = 0;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        while (elementPresent)
        {
            try
            {
                if (driver.findElement(by).isDisplayed())
                {
                    break;
                }

                else
                {
                    if (count == timeout)
                    {
                        elementPresent = false;
                        break;
                    }
                    Thread.sleep(1000);
                    count++;
                }
            }
            catch (Exception ex)
            {
                if (count == timeout)
                {
                    elementPresent = false;
                    break;
                }

                Thread.sleep(1000);
                count++;
            }
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return elementPresent;
    }

    public void scrollToView(By locator) {

        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(locator));
        } catch (Exception e)

        {
            System.out.println("Unable to scroll to view");
        }

    }

    public boolean click(By locator) {
        boolean blnClicked = false;
        int waitTime=10;
        try {
            if (isElementDisplayed(locator, waitTime)) {
                driver.findElement(locator).click();
                blnClicked = true;
            }
        } catch (Exception e) {
            if (isElementclickable(locator, 10)) {
                driver.findElement(locator).click();
                blnClicked = true;
            }
        }

        return blnClicked;
    }

    public boolean isElementclickable(By by, int timeout) {
        boolean isClickable = false;
        try {
            driver.manage().timeouts()
                    .implicitlyWait(timeout, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait((WebDriver) driver, timeout);
            wait.until(ExpectedConditions.elementToBeClickable(by));
            isClickable = driver.findElement(by).isDisplayed();

        } catch (StaleElementReferenceException stle) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            driver.manage().timeouts()
                    .implicitlyWait(timeout, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait((WebDriver) driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(by));
            isClickable = driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            isClickable = false;
        }
        driver.manage().timeouts()
                .implicitlyWait(15, TimeUnit.SECONDS);
        return isClickable;

    }


    public void waitForPageLoaded() {

        WebDriver tempDriver = driver;

        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver tempDriver) {
                return ((JavascriptExecutor) tempDriver).executeScript("return document.readyState").equals("complete");
            }
        };

        WebDriverWait wait = new WebDriverWait(driver, 30);

        try {

            wait.until(expectation);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public String getText(By elementBy) throws Exception {

        if (isElementDisplayed(elementBy, 5)) {

            return driver.findElement(elementBy).getText().trim();

        } else {

            return "";
        }
    }

    public boolean isElementDisplayedWithLog(By by, String strElementName,
                                             String strPage, int timeout) {
        boolean isDisplayed = false;
        try {
            driver.manage().timeouts()
                    .implicitlyWait(timeout, TimeUnit.SECONDS);
            //WebDriverWait wait = new WebDriverWait((WebDriver) driver, timeout);
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            scrollToView(by);
            isDisplayed = driver.findElement(by).isDisplayed();

        } catch (StaleElementReferenceException stle) {
            driver.manage().timeouts()
                    .implicitlyWait(timeout, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            scrollToView(by);
            isDisplayed = driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            isDisplayed = false;
        }
        if (isDisplayed) {

        } else {

        }
        return isDisplayed;

    }

    public void sendKeys(By locator, String valuetoType) throws InterruptedException {
        if (isElementDisplayed(locator, 5)) {
            try {
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(valuetoType);
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(valuetoType);
            }
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value=arguments[1];",
                    driver.findElement(locator), valuetoType);
        }
    }

    public List<WebElement> findElements(By elementBy) throws InterruptedException {
        List<WebElement> lstElements = null;
    int waitTime=10;
        if (isElementDisplayed(elementBy, waitTime)) {
            lstElements = driver.findElements(elementBy);

        } else {
           // report.updateTestLog("Find Elements", "Elements group not available", Status_CRAFT.FAIL);
            //throw new FrameworkException("Stopping test");

        }
        return lstElements;
    }

    public void deviceProvisioningValidations() throws Exception {
        welcomeScreen_sca();
        //beforeYouBegin_scavalidate();
        beforeYouBegin_sca();
        userIDpage_scavaliate();
        dob_scavaliate();
        pinPage_scavaliate();
     /*   notificationPage_scavaliate();
        locationServicePage_scavaliate();
        acceptNotificationToAccess();
        //registerDevice_scavaliate();
        registerDevice_sca();
        acceptNotificationToAccess();
        inviteCodeScreen_scavaliate();
        //inviteCodeScreen_sca();
        deviceName_scavaliate();
        allDone_scavaliate()*/;
    }

    public void welcomeScreen_sca() throws Exception {
        //SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        welcomeScreen();
    }

    public void welcomeScreen() throws InterruptedException {
        Thread.sleep(2000);
        changeNativeToWebview();
        click(By.xpath("//*[text()='Register this device']"));
        screen("test1");
    }

    public void beforeYouBegin_sca() throws Exception {
        //SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        beforeYouBegin();
    }

    public void beforeYouBegin() throws Exception {
        String strHeadTitle = getText(By.xpath("//*[text()='Before you begin']"));
        waitForPageLoaded();
        if (strHeadTitle.equals("Before you begin")) {
            //report.updateTestLog("BeforeYourBegin", "BeforeYourBegin Page launched successfully", Status_CRAFT.PASS);
            clickJS(By.xpath("//a[.='Continue']"), "Continue");
        } else {
            //report.updateTestLog("BeforeYourBegin", "BeforeYourBegin Page  not launched successfully", Status_CRAFT.FAIL);
        }
        screen("test2");
    }

    public void userIDpage_scavaliate() throws Exception {
       // SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        userIDpagevalidations();
    }

    public void userIDpagevalidations() throws Exception {
        String userID ="161695";

        waitForPageLoaded();
        isElementDisplayedWithLog(By.xpath("//*[text()='Back']"),
                "Back button", "Your User ID", 5);
        isElementDisplayedWithLog(By.xpath("//*[@class='boi-sca-progress-bar boi-no-padding']"),
                "Progress bar", "Your User ID", 5);
        isElementDisplayedWithLog(By.xpath("//*[contains(text(),'Step 1 of 5')]"),
                "Step heading 'Step 1 of 5'", "Your User ID", 5);
        isElementDisplayedWithLog(By.xpath("//*[contains(text(),'Enter your 365 user ID')]"),
                "Page heading 'Your user ID'", "Your User ID", 5);
        isElementDisplayedWithLog(By.xpath("//*[contains(text(),'This is the unique number we gave you to access your online banking.')]"),
                "Head text message : " + getText(By.xpath("//*[contains(text(),'This is the unique number we gave you to access your online banking.')]")), "Your User ID", 5);
        isElementDisplayedWithLog(By.xpath("//*[contains(text(),'Enter your user ID')]"),
                "Label 'Enter your user ID'", "Your User ID", 5);

        sendKeys(By.xpath("//*[contains(@name,'USERID')]"), userID);
        screen("test3");
        clickJS(By.xpath("//*[text()='Continue']"), "Continue on User ID page");
        //report.updateTestLog("Device Provisioning - User ID", "Device Provisioning - User ID launched successfully", Status_CRAFT.PASS);
    }

    public void dob_scavaliate() throws Exception {
        //SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        DOBvalidations();
    }

    public void DOBvalidations() throws Exception {

        String strSavingDate = "01/01/1900";

        while (isElementDisplayed(By.xpath("//*[@class='spinner']"), 4)) {
            waitForPageLoaded();
        }

        isElementDisplayedWithLog(By.xpath("//*[text()='Back']"),
                "Back button", "Enter your date of birth", 5);
        isElementDisplayedWithLog(By.xpath("/*[@class='boi-sca-progress-bar boi-no-padding']"),
                "Progress bar", "Enter your date of birth", 5);
        isElementDisplayedWithLog(By.xpath("//*[contains(text(),'Step 2 of 5')]"),
                "Step heading 'Step 2 of 5'", "Enter your date of birth", 5);
        isElementDisplayedWithLog(By.xpath("//*[text()='Enter your date of birth']"),
                "Page heading 'Enter your date of birth'", "Enter your date of birth", 5);
        isElementDisplayedWithLog(By.xpath("//label[text()='Date of birth']"),
                "Label 'Date of birth'", "Enter your date of birth", 5);

        if (isElementDisplayed(By.xpath("//input[@name='Day']"), 2)) {
            sendKeys(By.xpath("//input[@name='Day']"), strSavingDate.split("/")[0]);
            sendKeys(By.xpath("//input[@name='Month']"), strSavingDate.split("/")[1]);
            sendKeys(By.xpath("//input[@name='Year']"), strSavingDate.split("/")[2]);
        } else {
            sendKeys(By.xpath("launch.edtEnterYourDateOfBirth"), strSavingDate);
        }
        screen("test4");
        clickJS(By.xpath("//*[text()='Continue']"), "Continue on DOB screen");
        //report.updateTestLog("Device Provisioning - DOB", "Device Provisioning -  DOB launched successfully", Status_CRAFT.PASS);
    }

    public void pinPage_scavaliate() throws Exception {
        //SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        PINpagevalidations();
    }

    public void PINpagevalidations() throws Exception {

        String[] arrPin = "1111".split("");
        String strObject = "";

        while (isElementDisplayed(By.xpath("//*[@class='spinner']"), 4)) {
            waitForPageLoaded();
        }
        isElementDisplayedWithLog(By.xpath("//*[text()='Back']"),
                "Back button", "PIN", 5);
        isElementDisplayedWithLog(By.xpath("//*[@class='boi-sca-progress-bar boi-no-padding']"),
                "Progress bar", "PIN", 5);
        isElementDisplayedWithLog(By.xpath("//*[contains(text(),'Step 3 of 5')]"),
                "Step heading 'Step 3 of 5'", "PIN", 5);
        isElementDisplayedWithLog(By.xpath("//*[text()='PIN']"),
                "Page heading 'PIN'", "PIN", 5);
        isElementDisplayedWithLog(By.xpath("//*[contains(@class,'boi-mt-30 boi_para')]"),
                "Head text message :" + getText(By.xpath("//*[contains(@class,'boi-device-italic-blue-button')]2")), "PIN", 5);
        isElementDisplayedWithLog(By.xpath("//*[contains(@class,'boi-device-italic-blue-button')]"),
                "Link 'Forgot your PIN'", "PIN", 5);

        if (isElementDisplayed(By.xpath("//fieldset[contains(@id,\"GROUP_FS_HEAD\")]/descendant::input[@type=\"number\"]"), 5)) {
            strObject = "//fieldset[contains(@id,\"GROUP_FS_HEAD\")]/descendant::input[@type=\"number\"]";
            //report.updateTestLog("PIN input", ":: PIN input ::", Status_CRAFT.DONE);
        } else if (isElementDisplayed(By.xpath("//fieldset[@id='C2__GROUP_FS_HEAD_C153BBAC8B09CBAC3953749']//input"), 2)) {
            strObject = "//fieldset[@id='C2__GROUP_FS_HEAD_C153BBAC8B09CBAC3953749']//input";
        }

        List<WebElement> lstPinEnterFieldGrp = findElements(By.xpath(strObject));

        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
            }
        }
        screen("test5");
        clickJS(By.xpath("//*[text()='Continue']"), "Continue on 3-6 PIN");
        //report.updateTestLog("Device Provisioning - PIN ", "Device Provisioning -PIN page launched successfully", Status_CRAFT.PASS);
    }

    public void appendScreenshotToCucumberReport() {
        try{
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Hooks.testscenario.embed(screenshot, "image/png");
            logInfo("screen shot is captured");
        }catch (Throwable e) {
            logError("appendScreenshotToCucumberReport, Error=" + e.getMessage());
        }
        // stick it in the report
    }


}

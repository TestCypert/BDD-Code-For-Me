package com.boi.grp.pageobjects;

import com.boi.grp.hooks.Hooks;
import com.boi.grp.utilities.LogManager;
import com.boi.grp.utilities.LogManagerPreRun;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by C112083 on 23/10/2020.
 */
public class BasePageForDevices extends BasePage{
    public Logger logMan = null;
    WebDriver driver;
    AppiumDriver appiumDriver;
    int waitTime=Integer.valueOf(System.getProperty("IMPLICITWAIT"));
    public final String CONTEXTVIEW1="WEBVIEW_com.bankofireland.tcmb";
    public final String CONTEXTVIEW2="CONTEXTVIEW2=WEBVIEW_Temenos";
    public final String CONTEXTVIEW3="WEBVIEW_";
    public FileInputStream FIS;
    public Properties Config;



    public BasePageForDevices(WebDriver driver) {
        this.driver = driver;
        try {
            appiumDriver=(AppiumDriver) driver;
            logMan = LogManager.getInstance();
            FIS = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/objectRepo.properties");
            Config = new Properties();
            Config.load(FIS);
        }catch (Exception e) {
            logError("BasePageForDevices, error = "+e.getMessage());
        }
    }

    public void appendScreenshotToCucumberReport() {
        try{
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Hooks.testscenario.embed(screenshot, "image/png");
            logInfo("screen shot is captured");
        }catch (Throwable e) {
            logError("appendScreenshotToCucumberReport, Error=" + e.getMessage());
        }
    }

    public AppiumDriver getAppiumDriver(){
        return appiumDriver;
    }

    public By getWebElementFromOR(String key){
        By by = null;
        try {
            String locator=Config.getProperty(key);
            String[] arr = key.split("_");
            String webElementType=arr[arr.length-1];
            switch (webElementType.toUpperCase()){
                case "ID":
                    by = By.id(locator);
                    break;
                case "NAME":
                    by=By.name(locator);
                    break;
                case "CLASSNAME":
                    by=By.className(locator);
                    break;
                case "TAGNAME":
                    by=By.tagName(locator);
                    break;
                case "CSSSELECTOR":
                    by=By.cssSelector(locator);
                    break;
                case "XPATH":
                    by=By.xpath(locator);
                    break;
                case "LINKTEXT":
                    by=By.linkText(locator);
                    break;
                case "PARTIALLINKTEXT":
                    by=By.partialLinkText(locator);
                    break;
            }
        } catch (Exception e) {
            logError("Error in getWebElementFromOR method, Error = "+e.getMessage());
        }
        return by;
    }

    public void changeNativeToWebview() {
        try {
            Set<String> availableContexts = getContextHandles();
            logMessage("Total No of Context Found After we reach to WebView = " + availableContexts.size());
            for (String context : availableContexts) {
                if (context.contains(CONTEXTVIEW1)||context.contains(CONTEXTVIEW2)||context.contains(CONTEXTVIEW3)) {
                    logMessage("Context Name is " + context);
                    context(context);
                    break;
                }
            }
        } catch (Throwable e) {
            logError("changeNativeToWebview, error ="+e.getMessage());
        }
    }

    public void clickJS(By locator) throws InterruptedException {
        boolean blnClicked = false;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();",
                driver.findElement(locator));
        int waitTime=Integer.valueOf(System.getProperty("IMPLICITWAIT"));
        if (isElementDisplayed(locator)) {
            try {
                WebElement element = driver.findElement(locator);
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
            } catch (UnreachableBrowserException e) {
                logError("clickJS, error = "+e.getMessage());
            } catch (StaleElementReferenceException e) {
                logError("clickJS, error = "+e.getMessage());
            }
            blnClicked = true;
        }
        try {
            if (blnClicked) {
                logMessage("Element is clicked successfully, locator = "+locator);
            } else {
                logError("Element is NOT clicked, locator = "+locator);
            }
        } catch (StaleElementReferenceException e) {
            logError("clickJS, error = "+e.getMessage());
        }
    }

    public void clickJS(WebElement element) {
        boolean blnClicked = false;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        if (isElementDisplayed(element)) {
            try {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
            } catch (UnreachableBrowserException e) {
                logError("clickJS, error = "+e.getMessage());
            } catch (StaleElementReferenceException e) {
                logError("clickJS, error = "+e.getMessage());
            }
            blnClicked = true;
        }
        try {
            if (blnClicked) {
                logMessage("Element is clicked successfully, webelement = "+element);
            } else {
                logMessage("Element is NOT clicked, webelement = "+element);
            }
        } catch (StaleElementReferenceException e) {
            logError("clickJS, error = "+e.getMessage());
        }
    }
public void screen(String name){
    TakesScreenshot tt = (TakesScreenshot) driver;
    File src = tt.getScreenshotAs(OutputType.FILE);
    File dest = new File("C:\\All softwares\\"+name+".png");
    try {
        FileUtils.copyFile(src,dest);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void setRelevantWebViewTab() {
        boolean bflag = false;
        changeNativeToWebview();
        try {
            Iterable<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                driver.switchTo().window(windowHandle);
                if (driver.getTitle().equals("Bank of Ireland") || driver.getTitle().equals("Temenos")||driver.getTitle().contains("Bank of Ireland")) {
                    bflag = true;
                    logMessage("WebView with browser title 'Bank of Ireland' set");
                    appendScreenshotToCucumberReport();
                    break;
                }
            }
            if (!bflag) {
                logError("WebView with browser title 'Bank of Ireland' not found");
            }
        } catch (Exception e) {
            logError("WebView with browser title 'Bank of Ireland' not found, error = "+e.getMessage());
        }
    }

    public void waitForJQueryLoad() {
        WebDriver tempDriver = driver;
        WebDriverWait wait = new WebDriverWait(tempDriver, Integer.valueOf(System.getProperty("EXPLICITWAIT")));
        JavascriptExecutor jsExec = (JavascriptExecutor) tempDriver;
        try{
            ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) driver)
                    .executeScript("return jQuery.active") == 0);
            boolean jqueryReady = (Boolean) jsExec.executeScript("return jQuery.active==0");
            if (!jqueryReady) {
                logError("JQuery is NOT Ready!");
                wait.until(jQueryLoad);
            } else {
                logMessage("JQuery is Ready!");
            }
        }catch (Exception e){
            logError("javascript error: jQuery is not defined, error = "+e.getMessage());
        }

    }

    public boolean isElementDisplayed(By by){
        boolean elementPresent = true;
        int count = 0;
        int timeout=waitTime;
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        try {
            while (true){
                try{
                    if (driver.findElement(by).isDisplayed()){
                        logMessage("Element is displayed successfully for locator, = "+by);
                        break;
                    }else{
                        if (count == timeout){
                            elementPresent = false;
                            logError("Element not displayed for locator, = "+by);
                            break;
                        }
                        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
                        count++;
                    }
                } catch (Exception ex){
                    if (count == timeout){
                        elementPresent = false;
                        logError("Element not displayed for locator, = "+by);
                        break;
                    }
                    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
                    count++;
                }
            }
        } catch (Throwable e) {
            logError("isElementDisplayed for locator error, = "+e.getMessage());
        }
        driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
        return elementPresent;
    }


    public boolean isElementDisplayed(WebElement element) {
        int timeout=Integer.valueOf(System.getProperty("IMPLICITWAIT"));
        boolean elementPresent = true;
        int count = 0;
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        try {
            while (true){
                try{
                    if (element.isDisplayed()){
                        logMessage("Element is displayed successfully for element, = "+element);
                        break;
                    }else{
                        if (count == timeout){
                            elementPresent = false;
                            logWarning("Element not displayed for element, = "+element);
                            break;
                        }
                        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
                        count++;
                    }
                } catch (Exception ex){
                    if (count == timeout){
                        elementPresent = false;
                        logError("Element not displayed for element, = "+element);
                        break;
                    }
                    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
                    count++;
                }
            }
        } catch (Throwable e) {
            logError("isElementDisplayed  for element error, = "+e.getMessage());
        }
        driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
        return elementPresent;
    }

    public void scrollToView(By locator) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(locator));
            logMessage("successfully Scrolled to element, locator =  "+locator);
        } catch (Throwable e){
            logError("Unable to scroll to view");
        }

    }

    public void scrollToView(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();",
                    element);
            logMessage("successfully Scrolled to element, webElement =  "+element);
        } catch (Throwable e){
            logError("Unable to scroll to view");
        }

    }

    public boolean click(By locator) {
        boolean blnClicked = false;
        try {
            if (isElementDisplayed(locator)) {
                driver.findElement(locator).click();
                logMessage("Element is clicked successfully, locator = "+locator);
                blnClicked = true;
            }
        } catch (Exception e) {
            if (isElementClickable(locator)) {
                driver.findElement(locator).click();
                logMessage("Element is clicked successfully, locator = "+locator);
                blnClicked = true;
            }
        }
        logError("Element is Not clicked successfully, for locator= "+locator);
        return blnClicked;
    }

    public boolean click(WebElement element) {
        boolean blnClicked = false;
        try {
            if (isElementDisplayed(element)) {
                element.click();
                logMessage("Element is clicked successfully, webelement = "+element);
                blnClicked = true;
            }
        } catch (Exception e) {
            if (isElementClickable(element)) {
                element.click();
                logMessage("Element is clicked successfully, webelement = "+element);
                blnClicked = true;
            }
        }
        if(!blnClicked){
            logError("Element is Not clicked successfully, for webElement= "+element);
        }

        return blnClicked;
    }

    public boolean isElementClickable(By by) {
        boolean isClickable;
        try {
            driver.manage().timeouts()
                    .implicitlyWait(waitTime, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, waitTime);
            wait.until(ExpectedConditions.elementToBeClickable(by));
            isClickable = driver.findElement(by).isDisplayed();
            logMessage("Element is displayed successfully, locator = "+by);
        } catch (StaleElementReferenceException stle) {
            driver.manage().timeouts()
                    .implicitlyWait(waitTime, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, Integer.valueOf(System.getProperty("EXPLICITWAIT")));
            wait.until(ExpectedConditions.elementToBeClickable(by));
            isClickable = driver.findElement(by).isDisplayed();
            logMessage("Element is displayed successfully under catch Block, locator = "+by);
        } catch (Throwable e) {
            isClickable = false;
            logError("Element is not displayed, locator = "+by);
        }
        driver.manage().timeouts()
                .implicitlyWait(waitTime, TimeUnit.SECONDS);
        return isClickable;

    }

    public boolean isElementClickable(WebElement element) {
        boolean isClickable;
        try {
            driver.manage().timeouts()
                    .implicitlyWait(waitTime, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, waitTime);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            isClickable = element.isDisplayed();
            logMessage("Element is displayed successfully , webelement = "+element);
        } catch (StaleElementReferenceException stle) {
            driver.manage().timeouts()
                    .implicitlyWait(waitTime, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, Integer.valueOf(System.getProperty("EXPLICITWAIT")));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            isClickable = element.isDisplayed();
            logMessage("Element is displayed successfully under catch Block, webelement = "+element);
        } catch (Throwable e) {
            isClickable = false;
            logError("Element is not displayed, webelement = "+element);
        }
        driver.manage().timeouts()
                .implicitlyWait(waitTime, TimeUnit.SECONDS);
        return isClickable;

    }

    public void waitForPageLoaded() {
        WebDriver tempDriver = driver;
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver tempDriver) {
                return ((JavascriptExecutor) tempDriver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, Integer.valueOf(System.getProperty("EXPLICITWAIT")));
        try {
            wait.until(expectation);
            logMessage("Page loaded successfully using javascript");
        } catch (Exception ex) {
            logError("Error in Page loaded, Method = waitForPageLoaded using javascript, error = "+ ex.getMessage());
        }
    }

    public String getText(By elementBy) throws Exception {
        try {
            if (isElementDisplayed(elementBy)) {
                return driver.findElement(elementBy).getText().trim();
            } else {
                return "";
            }
        } catch (Exception e) {
            logError("getText, error = "+e.getMessage());
            return "";
        }
    }

    public String getText(WebElement element){
        try {
            if (isElementDisplayed(element)) {
                return element.getText().trim();
            } else {
                return "";
            }
        } catch (Exception e) {
            logError("getText, error = "+e.getMessage());
            return "";
        }
    }

    public boolean isElementDisplayedWithLog(By by, int timeout) {
        boolean isDisplayed;
        try {
            driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            scrollToView(by);
            isDisplayed = driver.findElement(by).isDisplayed();
        } catch (StaleElementReferenceException stle) {
            driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, Integer.valueOf(System.getProperty("EXPLICITWAIT")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            scrollToView(by);
            isDisplayed = driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            isDisplayed = false;
            logError("isElementDisplayedWithLog, error = "+e.getMessage());
        }
        if (isDisplayed) {
            logMessage("Element displayed successfully");
        } else {
            logError("Element Not displayed successfully");
        }
        return isDisplayed;
    }

    public boolean isElementDisplayedWithLog(WebElement element) {
        boolean isDisplayed;
        int timeout=Integer.valueOf(System.getProperty("IMPLICITWAIT"));
        try {
            driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
            scrollToView(element);
            isDisplayed = element.isDisplayed();
        } catch (StaleElementReferenceException stle) {
            driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, Integer.valueOf(System.getProperty("EXPLICITWAIT")));
            wait.until(ExpectedConditions.visibilityOf(element));
            scrollToView(element);
            isDisplayed = element.isDisplayed();
        } catch (Exception e) {
            isDisplayed = false;
            logError("isElementDisplayedWithLog, error = "+e.getMessage());
        }
        if (isDisplayed) {
            logMessage("Element displayed successfully");
        } else {
            logError("Element Not displayed successfully");
        }
        return isDisplayed;
    }



    public void sendKey(By locator, String valuetoType) throws InterruptedException {
        if (isElementDisplayed(locator)) {
            try {
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(valuetoType);
                logMessage("Entered the value , = "+valuetoType);
            } catch (StaleElementReferenceException e) {
                logError("Stalelement exception, error = "+e.getMessage());
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(valuetoType);
                logMessage("Entered the value , = "+valuetoType);
            }
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value=arguments[1];",
                    driver.findElement(locator), valuetoType);
            logMessage("Entered the value using javascript , value  = "+valuetoType);
        }
    }

    public void sendKey(WebElement element, String valuetoType) {
        if (isElementDisplayed(element)) {
            try {
                element.clear();
                element.sendKeys(valuetoType);
                logMessage("Entered the value , = "+valuetoType);
            } catch (StaleElementReferenceException e) {
                logError("Stalelement exception, error = "+e.getMessage());
                element.clear();
                element.sendKeys(valuetoType);
                logMessage("Entered the value , = "+valuetoType);
            }
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value=arguments[1];",
                    element, valuetoType);
            logMessage("Entered the value using javascript , value  = "+valuetoType);
        }
    }

    public List<WebElement> findElements(By elementBy) throws InterruptedException {
        List<WebElement> lstElements = null;
        int waitTime=10;
        if (isElementDisplayed(elementBy)) {
            lstElements = driver.findElements(elementBy);
            logMessage("Find Elements, Elements group available");
        } else {
            logError("Find Elements, Elements group not available");
        }
        return lstElements;
    }


    /**
         * Function Applicable only when the tool used is <b>APPIUM i.e.,
         * {@link AppiumDriver}.
         */
        @SuppressWarnings("rawtypes")
    public WebElement findElementByName(String arg0) {
        return appiumDriver.findElementByName(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<WebElement> findElementsByName(String arg0) {
        return appiumDriver.findElementsByName(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>PERFECTO i.e.,
     * {@link 'IMobileWebDriver} OR <b>APPIUM i.e., {@link AppiumDriver} OR
     * <b>SEETEST i.e., {@link 'MobileWebDriver}.
     */
    @SuppressWarnings("rawtypes")
    public WebElement findElementByXPath(String arg0) {
       return appiumDriver.findElementByXPath(arg0);

    }





    /**
     * Function Applicable only when the tool used is <b>PERFECTO i.e.,
     * {@link 'IMobileWebDriver} OR <b>APPIUM i.e., {@link AppiumDriver} OR
     * <b>SEETEST i.e., {@link 'MobileWebDriver}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<WebElement> findElementsByLinkText(String arg0) {
        return appiumDriver.findElementsByLinkText(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>PERFECTO i.e.,
     * {@link 'IMobileWebDriver} OR <b>APPIUM i.e., {@link AppiumDriver} OR
     * <b>SEETEST i.e., {@link 'MobileWebDriver}.
     */
    @SuppressWarnings("rawtypes")
    public WebElement findElementByPartialLinkText(String arg0) {
        return appiumDriver.findElementByPartialLinkText(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>PERFECTO i.e.,
     * {@link 'IMobileWebDriver} OR <b>APPIUM i.e., {@link AppiumDriver} OR
     * <b>SEETEST i.e., {@link 'MobileWebDriver}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<WebElement> findElementsByPartialLinkText(String arg0) {
        return appiumDriver.findElementsByPartialLinkText(arg0);
    }


    /**
     * Function Applicable only when the tool used is <b>PERFECTO i.e.,
     * {@link 'IMobileWebDriver} OR <b>APPIUM i.e., {@link AppiumDriver} OR
     * <b>SEETEST i.e., {@link 'MobileWebDriver}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<WebElement> findElementsByClassName(String arg0) {
        return appiumDriver.findElementsByClassName(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public WebElement findElementByTagName(String arg0) {
        return appiumDriver.findElementByTagName(arg0);

    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WebElement> findElementsByTagName(String arg0) {
        return appiumDriver.findElementsByTagName(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public WebElement findElementByCssSelector(String arg0) {
        return appiumDriver.findElementByCssSelector(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<WebElement> findElementsByCssSelector(String arg0) {
        return appiumDriver.findElementsByCssSelector(arg0);

    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public Capabilities getCapabilities() {
        return appiumDriver.getCapabilities();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public CommandExecutor getCommandExecutor() {
        return appiumDriver.getCommandExecutor();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public ErrorHandler getErrorHandler() {
        return appiumDriver.getErrorHandler();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public ExecuteMethod getExecuteMethod() {
        return appiumDriver.getExecuteMethod();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public FileDetector getFileDetector() {
        return appiumDriver.getFileDetector();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public Keyboard getKeyboard() {
        return appiumDriver.getKeyboard();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({ "rawtypes", "deprecation" })
    public Mouse getMouse() {
        return appiumDriver.getMouse();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public WebDriver context(String arg0) {
        return appiumDriver.context(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Response execute(String driverCommand, Map<String, ?> parameters) {
        return appiumDriver.execute(driverCommand, parameters);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void performMultiTouchAction(MultiTouchAction arg0) {
        appiumDriver.performMultiTouchAction(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public TouchAction performTouchAction(TouchAction arg0) {
        return appiumDriver.performTouchAction(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public String getContext() {
        return appiumDriver.getContext();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Set<String> getContextHandles() {
        return appiumDriver.getContextHandles();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public ScreenOrientation getOrientation() {
        return appiumDriver.getOrientation();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public URL getRemoteAddress() {
        return appiumDriver.getRemoteAddress();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public SessionId getSessionId() {
        return appiumDriver.getSessionId();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void rotate(ScreenOrientation arg0) {
        appiumDriver.rotate(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public WebElement findElementByAccessibilityId(String arg0) {

        return appiumDriver.findElementByAccessibilityId(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WebElement> findElementsByAccessibilityId(String arg0) {

        return appiumDriver.findElementsByAccessibilityId(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public Location location() {
        return appiumDriver.location();
    }

    // public int lockScreen(int seconds){
    // return appiumDriver.lockScreen(seconds);
    // }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void setLocation(Location arg0) {
        appiumDriver.setLocation(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void hideKeyboard() {
        appiumDriver.hideKeyboard();
    }


    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void setErrorHandler(ErrorHandler handler) {
        appiumDriver.setErrorHandler(handler);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void setFileDetector(FileDetector detector) {
        appiumDriver.setFileDetector(detector);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void setLogLevel(Level level) {
        appiumDriver.setLogLevel(level);
    }


    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public byte[] pullFile(String remotePath) {
        return appiumDriver.pullFile(remotePath);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public byte[] pullFolder(String remotePath) {
        return appiumDriver.pullFolder(remotePath);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void closeApp() {
        appiumDriver.closeApp();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void installApp(String appPath) {
        appiumDriver.installApp(appPath);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public boolean isAppInstalled(String bundleId) {
        return appiumDriver.isAppInstalled(bundleId);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void launchApp() {
        appiumDriver.launchApp();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void removeApp(String bundleId) {
        appiumDriver.removeApp(bundleId);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void resetApp() {
        appiumDriver.resetApp();
    }


    }


package com.boi.grp.pageobjects;

import static io.qameta.allure.Allure.step;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;

import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import org.apache.log4j.Logger;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.*;

import com.boi.grp.hooks.Hooks;
import com.boi.grp.utilities.LogManager;
import com.boi.grp.utilities.LogManagerPreRun;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;


public class BasePageForAllPlatform extends BasePage {
    public Logger logMan = null;
    public WebDriver driver = null;
    public WebDriverWait wait = null;
    AppiumDriver appiumDriver;
    public FileInputStream CCORFIS = null;
    public FileInputStream ACCORFIS = null;
    public FileInputStream SERORFIS = null;
    public FileInputStream PDORIS = null;
    public FileInputStream LOGINORFIS = null;
    public FileInputStream MSGORFIS = null;
    public FileInputStream CCMSGERR = null;
    public FileInputStream PFMSGERR = null;
    public Properties PFValMsgFile = null;
    public Properties CCValMsgFile = null;
    public Properties ValMsgFile = null;
    public Properties LOGINpropertyFile = null;
    public Properties ACCpropertyFile = null;
    public Properties PDpropertyFile = null;
    public FileInputStream PFORFIS = null;
    public Properties PFpropertyFile = null;
    public Properties SERpropertyFile = null;
    public Properties CCpropertyFile = null;

    //***** variables added by Cards Team
    int waitTime = Integer.valueOf(System.getProperty("IMPLICITWAIT"));
    //protected RepositoryParser parser;
    protected String deviceType;
    //***** variables added by Cards Team
    public String devTypeToGetProperty;
    public final String CONTEXTVIEW1 = "WEBVIEW_com.bankofireland.tcmb";
    public final String CONTEXTVIEW2 = "WEBVIEW_";
    //public final String CONTEXTVIEW3="TEST";
    public final String CONTEXTVIEW3 = "WEBVIEW_com.bankofireland.boiinapp";

    /*public BasePageForAllPlatform(WebDriver driver) {
        this.driver = driver;
        //***** variables added by Cards Team
        //deviceType=System.getProperty("DEVICETYPE");
        //***** variables added by Cards Team
        logMan = LogManager.getInstance();
        try {


            CCMSGERR = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/uxp_ValidationMsg.properties");
            CCValMsgFile = new Properties();
            CCValMsgFile.load(CCMSGERR);

            LOGINORFIS = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/loginData.properties");
            LOGINpropertyFile = new Properties();
            LOGINpropertyFile.load(LOGINORFIS);

            CCORFIS = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/cardsObjectRepo.properties");
            CCpropertyFile = new Properties();
            CCpropertyFile.load(CCORFIS);

            ACCORFIS = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/accountsOR.properties");
            ACCpropertyFile = new Properties();
            ACCpropertyFile.load(ACCORFIS);

            SERORFIS = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/servicesOR.properties");
            SERpropertyFile = new Properties();
            SERpropertyFile.load(SERORFIS);

            PDORIS = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/paymentsOR.properties");
            PDpropertyFile = new Properties();
            PDpropertyFile.load(PDORIS);


//			PFORFIS = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/profileOR.properties");
//			PFpropertyFile = new Properties();
//			PFpropertyFile.load(PFORFIS);

            wait = new WebDriverWait(driver, Integer.valueOf(System.getProperty("EXPLICITWAIT")));
            driver.manage().timeouts().implicitlyWait(Integer.valueOf(System.getProperty("IMPLICITWAIT")),
                    TimeUnit.SECONDS);
            try {
                devTypeToGetProperty = getDeviceType();
            } catch (Exception e) {
                e.printStackTrace();
        }
            //parser = new RepositoryParser(System.getProperty("user.dir")+"//src/test/resources/repository/cardsObjectRepo.properties");
        } catch (FileNotFoundException e) {
            logError(
                    "Please check the Config.properties file at src//test//java//com.boi.grp.globalconfig//GlobalConfig.properties, it does not exists, Stoping Execution");

        }
        catch (IOException e) {

            logError(
                    "IO execption occured accessing Config.properties file at src//test//java//com.boi.grp.globalconfig//GlobalConfig.properties - Stoping Execution");
        }

    }*/
    public BasePageForAllPlatform(WebDriver driver) {
        this.driver = driver;
        if (System.getProperty("PLATFORM").equalsIgnoreCase("MOBILECENTER")) {
            appiumDriver = (AppiumDriver) driver;
        }
        //***** variables added by Cards Team
        //deviceType=System.getProperty("DEVICETYPE");  
        //***** variables added by Cards Team
        logMan = LogManager.getInstance();
        try {
            devTypeToGetProperty = getDeviceType();
        } catch (Exception e) {
            e.getMessage();
        }
        wait = new WebDriverWait(driver, Integer.valueOf(System.getProperty("EXPLICITWAIT")));
        driver.manage().timeouts().implicitlyWait(Integer.valueOf(System.getProperty("IMPLICITWAIT")),
                TimeUnit.SECONDS);
        /*try {
               CCORFIS = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/cardsObjectRepo.properties");
               CCpropertyFile = new Properties();
               CCpropertyFile.load(CCORFIS);

               CCMSGERR = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/cardsValMsg.properties");
               CCValMsgFile = new Properties();
               CCValMsgFile.load(CCMSGERR);
               wait = new WebDriverWait(driver, Integer.valueOf(System.getProperty("EXPLICITWAIT")));
               driver.manage().timeouts().implicitlyWait(Integer.valueOf(System.getProperty("IMPLICITWAIT")),
                            TimeUnit.SECONDS);

               //parser = new RepositoryParser(System.getProperty("user.dir")+"//src/test/resources/repository/cardsObjectRepo.properties");
        } catch (FileNotFoundException e) {
               logError(
                            "Please check the Config.properties file at src//test//java//com.boi.grp.globalconfig//GlobalConfig.properties, it does not exists, Stoping Execution");

        } catch (IOException e) {
               // Auto-generated catch block
               logError(
                            "IO execption occured accessing Config.properties file at src//test//java//com.boi.grp.globalconfig//GlobalConfig.properties - Stoping Execution");
        }
*/
    }

    public BasePageForAllPlatform() {

    }

    public void appendScreenshotToCucumberReport() {
        try {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Hooks.testscenario.embed(screenshot, "image/png");
            logInfo("screen shot is captured");
        } catch (Throwable e) {
            logError("appendScreenshotToCucumberReport, Error=" + e.getMessage());
        }
        // stick it in the report
    }


    public void setValue(WebElement Field, String ValueToBeSet) {
        try {
            if (Field.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(Field));
                Field.clear();
                logMessage("Field  with description " + Field.toString() + " is cleared successfully");
                Field.sendKeys(ValueToBeSet);
                logMessage("Value with description " + ValueToBeSet + " is set successfully in Field  = "
                        + Field.toString());
            }
        } catch (Exception e) {
            logError("Field Not found, Field with description " + Field.toString() + " Error=" + e.getMessage());
        }
    }

    public void setValue(By Field, String ValueToBeSet) {
        try {
            if (driver.findElement(Field).isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(Field));
                driver.findElement(Field).clear();
                logMessage("Field  with description " + Field.toString() + " is cleared successfully");
                driver.findElement(Field).sendKeys(ValueToBeSet);
                logMessage("Value with description " + ValueToBeSet + " is set successfully in Field  = "
                        + Field.toString());
            }
        } catch (Exception e) {
            logError("Field Not found, Field with description " + Field.toString() + " Error=" + e.getMessage());
        }
    }

    public void EnterClick() {
        try {
            logMan.info("EnterClick Start");
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ENTER).build().perform();
            logMan.info("EnterClick End");
        } catch (Exception e) {
            logError("Error in EnterClick method, Error = " + e.getMessage());
        }
    }

    /**
     * @param ElementToBeClicked
     */
    public void click(WebElement ElementToBeClicked) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(ElementToBeClicked));
            highlight(ElementToBeClicked);
            ElementToBeClicked.click();
            logMessage("Clicked successfully on Element with Description " + ElementToBeClicked.toString());
        } catch (NoSuchElementException e) {
            logError("Field Not found, Field with description " + ElementToBeClicked.toString());
        }
    }

    /**
     * @param ElementToBeDoubleClicked
     */
    public void doubleclick(WebElement ElementToBeDoubleClicked) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(ElementToBeDoubleClicked));
            Actions action = new Actions(driver);
            action.doubleClick(ElementToBeDoubleClicked).build().perform();
            logMessage("Double clicked successfully on Element with Description " + ElementToBeDoubleClicked.toString());
        } catch (NoSuchElementException e) {
            logError("Field Not found, Field with description " + ElementToBeDoubleClicked.toString());
        }
    }

    /**
     * @param by
     */
    public void doubleclick(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(by)).doubleClick().build().perform();
            logMessage("Double clicked successfully on Element with Xpath ");
        } catch (NoSuchElementException e) {
            logError("Field Not found, Field with xpath ");
        }
    }

    /**
     * @param Element
     * @param SuccessMessage
     * @param FailureMessage
     * @return whether element is displayed or not
     */
    public boolean verifyElement(WebElement Element, String SuccessMessage, String FailureMessage) {
        try {
            if (Element.isDisplayed()) {
                if (SuccessMessage != "") {
                    logMessage(SuccessMessage);
                }
                return true;
            } else {
                if (FailureMessage != "") {
                    logError(FailureMessage);
                }
                return false;
            }
        } catch (NoSuchElementException e) {
            logError("No Such Element Found, Element desctiption  =" + Element.toString());
            return false;
        }
    }

    /**
     * @param Xpath
     * @param SuccessMessage
     * @param FailureMessage
     * @return verification for the visibility of the element
     */
    public boolean verifyElement(String Xpath, String SuccessMessage, String FailureMessage) {
        try {
            if (driver.findElements(By.xpath(Xpath)).size() > 0) {
                if (SuccessMessage != "") {
                    logMessage(SuccessMessage);
                }
                return true;
            } else {
                if (FailureMessage != "") {
                    logError(FailureMessage);
                }
                return false;
            }
        } catch (NoSuchElementException e) {
            logError("No Such Element Found, Element Xpath  =" + Xpath);
            return false;
        }
    }

    /**
     * wait for page to be loaded completely
     */
    public void waitForBrowserToCompleteProcessing() {
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        logInfo("Page load completed");
    }

    /**
     * This will be used to mousehover when having xpath
     *
     * @param Xpath
     */
    public void mouseHover(String Xpath) {
        if (driver.findElements(By.xpath(Xpath)).size() == 1) {
            logInfo("mouseHover, Found element with xpath = " + Xpath);
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(By.xpath(Xpath))).click().perform();
            logMessage("MouseHover done over" + Xpath);
        } else if (driver.findElements(By.xpath(Xpath)).size() > 1) {
            logError("More than 1 element identified with xpath " + Xpath);
        } else {
            logError("No element identified with xpath " + Xpath);
        }
    }

    /**
     * This will be used to mousehover when having WebElement
     *
     * @param element
     */
    public void mouseHover(WebElement element) {
        try {
            Actions action = new Actions(driver);
            action.moveToElement(element).click().perform();
            logMessage("MouseHover done over" + element.getText());
        } catch (Throwable t) {
            logError("Error Occured Inside mouseHover function");
        }
    }

    /**
     * Thread wait
     *
     * @param Seconds
     */
    public void waitForSeconds(int Seconds) {
        try {
            Thread.sleep(1000 * Seconds);
        } catch (Throwable e) {
            logError("Error Occured in waitForSeconds function's  thread.sleep section, reason = " + e.getMessage());
        }
    }

    /**
     * This will scroll up the page
     */
    public void scrollUp() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-550)", "");
        logMessage("Scrolled up the Page");
        waitForSeconds(1);
    }

    /**
     * This will scroll up the page A BIT
     */
    public void scrollUpAbit() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-100)", "");
        logMessage("Scrolled up the Page a bit");
        waitForSeconds(1);
    }

    /**
     * This will scroll down the page
     */
    public void scrollDown() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,500)", "");
        logMessage("Scrolled down the Page");
        waitForSeconds(1);
    }

    /**
     * This will highlight element in RED
     *
     * @param element
     */
    public void highlight(WebElement element) {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("arguments[0].style.border='3px solid red'", element);
            logMessage("Highlighted successfully on " + element.toString());
        } catch (Exception e) {
            logError("Error Occured While Highlighting a control ,Reason = " + e.getMessage());
        }
    }

    /**
     * This will highlight element in BLUE
     *
     * @param element
     */
    public void highlightBlue(WebElement element) {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("arguments[0].style.border='3px solid blue'", element);
            logMessage("Highlighted successfully on " + element.toString());
        } catch (Exception e) {
            logError("Error Occured While Highlighting a control ,Reason = " + e.getMessage());
        }
    }

    /**
     * Check the presence of element
     *
     * @param by
     * @return boolean
     */
    public boolean checkElement(By by) {
        if (driver.findElements(by).size() == 1) {
            highlight(driver.findElements(by).get(0));
            logInfo("Found element with element = " + by);
            return true;
        } else
            return false;
    }

    /**
     * Check the presence of element and click
     *
     * @param by
     * @return boolean
     */
    public boolean checkElementAndClick(By by) {
        boolean returnvalue = false;
        if (checkElement(by)) {
            click(driver.findElement(by));
            logMessage("Clicked successfully on element with xpath = " + by);
            returnvalue = true;
        }
        return returnvalue;
    }

    /**
     * Double Click and presence of element
     *
     * @param by
     * @return boolean condition
     */
    public boolean checkElementAndDoubleClick(By by) {
        if (checkElement(by)) {
            doubleclick(by);
            logMessage("Clicked successfully on element with xpath = " + by);
            return true;
        } else
            return false;
    }

    /**
     * Select value from list of weblement
     *
     * @param SelectAsWebElement
     * @param ValueToBeSelected
     * @return boolean
     */
    public boolean selectValueFromList(WebElement SelectAsWebElement, String ValueToBeSelected) {
        try {
            Select list = new Select(SelectAsWebElement);
            list.selectByVisibleText(ValueToBeSelected);
            logMessage("Value " + ValueToBeSelected + " is selected successfully in the list " + SelectAsWebElement.toString());
        } catch (Exception e) {
            logError("Value " + ValueToBeSelected + " is not found in List " + SelectAsWebElement.toString());
            return false;
        }
        return true;
    }

    /**
     * Select value from list when having parent Xpath
     *
     * @param DropdownParentXpath
     * @param ValueToBeSelected
     * @return
     */
    public boolean selectValueFromList(By DropdownParentXpath, String ValueToBeSelected) {
        boolean returnvalue = false;
        try {
            List<WebElement> dropDownlist = driver.findElements(DropdownParentXpath);
            for (WebElement element : dropDownlist) {
                if (element.getText().equalsIgnoreCase(ValueToBeSelected)) {
                    element.click();
                    logMessage("Value " + ValueToBeSelected + " is selected successfully in the list , xpath = " + DropdownParentXpath);
                    returnvalue = true;
                    break;
                }
            }
        } catch (Exception e) {
            logError("Value " + ValueToBeSelected + " is not found in List, where Xpath =  " + ValueToBeSelected);
            returnvalue = false;
        }
        return returnvalue;
    }

    /**
     * Click method when having Xpath
     *
     * @param by
     */
    public void click(By by) {
        try {
            WebElement Field;
            if (driver.findElements(by).size() == 1) {
                Field = driver.findElement(by);
                Actions act = new Actions(driver);
                act.moveToElement(Field).perform();
                wait.until(ExpectedConditions.elementToBeClickable(Field));
                Field.click();
                logMessage("Clicked successfully on Element, Field = " + Field);
            } else {
                logError("Field Not found, Xpath is = " + by);
            }
        } catch (Exception e) {
            highlight(driver.findElement(by));
            logError("Error occured clicking on " + by + " Description =" + e.getMessage());
        }
    }

    /**
     * Click on link
     *
     * @param LinkText
     */
    public void clickonLink(String LinkText) {
        try {
            WebElement Field;
            if (driver.findElements(By.linkText(LinkText)).size() == 1) {
                Field = driver.findElement(By.linkText(LinkText));
                wait.until(ExpectedConditions.elementToBeClickable(Field));
                highlight(Field);
                Field.click();
                logMessage("Clicked successfully on Field " + Field + " whose Link with text = " + LinkText);
                waitForBrowserToCompleteProcessing();
            } else {
                logError("Link Not found,  Link Text= " + LinkText);
            }
        } catch (Exception e) {
            logError("Error occurred clicking on Link with text " + LinkText + " Description =" + e.getMessage());
        }
    }

    /**
     * Create new System Property to save data
     *
     * @param PropertyName
     * @param Value
     */
    public void createNewProperty(String PropertyName, String Value) {
        try {
            System.setProperty(PropertyName, Value);
            logMessage("Property with Name = " + PropertyName + " with value  = " + Value + " is created successfully");
        } catch (Throwable t) {
            logError("Property with Name = " + PropertyName + " with value  = " + Value + " is NOT created successfully, Error =" + t.getMessage());
        }
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public WebDriver context(String arg0) {
        logMessage("******inside Context" + appiumDriver.context(arg0));
        return appiumDriver.context(arg0);
    }


    /**
     * Wait Till ProcessingImage Is Displayed
     *
     * @param MaxProcessingTime
     * @param by
     * @param Maxwait
     */
    public void waitTillProcessingImageIsDisplayed(long MaxProcessingTime, By by, long Maxwait) {
        logMessage("Waiting For Processing to be completed....");
        try {
            wait = new WebDriverWait(driver, MaxProcessingTime);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            wait = new WebDriverWait(driver, Maxwait);
        } catch (Throwable t) {
            logError("Processing of image is still going for field xpath = " + by);
        }
    }


    /**
     * Wait Till Locator Is Displayed
     */
    //Written By C114322
    public void waitForElementToBeDisplayed(By by) {
        logMessage("Waiting For Element to be displayed....");
        try {
            wait = new WebDriverWait(driver, Integer.valueOf(System.getProperty("EXPLICITWAIT")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable t) {
            logError("Waiting For Element is still going for field xpath = " + by);
        }
    }

    /**
     * Wait Till Processing Icon Is Displayed
     *
     * @param MaxProcessingTime
     * @param by
     * @param Maxwait
     */
    public void waitTillProcessingIconIsDisplayed(long MaxProcessingTime, By by, long Maxwait) {
        logMessage("Waiting For Processing to be completed....");
        try {
            wait = new WebDriverWait(driver, MaxProcessingTime);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            wait = new WebDriverWait(driver, Maxwait);
        } catch (Throwable t) {
            logError("Processing of icon is still continuing for field xpath = " + by);
        }
    }

    /**
     * Generate Random numbers
     *
     * @param DigitsOfTheRandomNumber
     * @return
     */
    public String generateRandomNumber(int DigitsOfTheRandomNumber) {
        logMessage("generateRandomNumber function - START");
        String returnValue = "";
        try {
            while (returnValue.length() != DigitsOfTheRandomNumber) {
                returnValue = String.valueOf((long) (Math.random() * 90000000000000000l)).substring(0, DigitsOfTheRandomNumber);
                ;
            }
        } catch (Throwable t) {
            returnValue = "Error";
        }
        logMessage("generateRandomNumber function - END");
        return returnValue;
    }

    /**
     * Generate Random string
     *
     * @return Function written by
     */
    public String generateRandomString() {
        logMessage("generateRandomString function - START");
        Random rand = new Random();
        String rand_str = "Test";
        int rand_int = rand.nextInt(999);
        logMessage("RANDOMLY GENERATED STRING IS:" + rand_str + rand_int);
        logMessage("generateRandomString function - END");
        return (rand_str + rand_int);
    }

    /**
     * Java Script Checking
     *
     * @param Term
     */
    public void JavascriptChecking(String Term) {
        String Page = null;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Page = js.executeScript("return document.documentElement.outerHTML;").toString();
        while (Page.contains(Term)) {
            try {
                Thread.sleep(500);
                Page = js.executeScript("return document.documentElement.outerHTML;").toString();
            } catch (InterruptedException e) {
                logError("Java script checking Fails for Term = " + Term);
            }
        }
    }

    /**
     * Set Value in Disabled field
     *
     * @param element
     * @param ValueToBeSet
     */
    public void setValueInDisabledField(WebElement element, String ValueToBeSet) {

        if (element.isDisplayed()) {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].removeAttribute('readonly')", element);
            element.clear();
            logMessage("Field " + element + " is cleared successfully");
            Actions action = new Actions(driver);
            action.sendKeys(ValueToBeSet).build().perform();
            String mouseclickScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('click',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject){ arguments[0].fireEvent('onclick');}";
            //JavascriptExecutor js = (JavascriptExecutor) driver;
            //WebElement someElem = driver.findElement(By.xpath(Xpath));
            js.executeScript(mouseclickScript, element);
            logMessage("Value with description " + ValueToBeSet + " is set successfully in Field  = " + element);
        } else {
            logError("Field Not found, Field Name = " + element);
        }
    }

    /**
     * Compare value with expression
     *
     * @param ValueToBeCompared
     * @param ExpressionToBeEvaluated
     * @param ReferenceValueForComparision
     * @return boolean
     */
    public boolean comparevaluewithexpression(String ValueToBeCompared, String ExpressionToBeEvaluated, String ReferenceValueForComparision) {
        logInfo("comparevaluewithexpression function - START");
        boolean returnvalue = false;
        try {
            if (ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS EQUAL TO")) {
                if (ValueToBeCompared.trim().toUpperCase().equalsIgnoreCase(ReferenceValueForComparision)) {
                    returnvalue = true;
                }
            } else {
                int IntegerValueToBeCompared = Integer.valueOf(ValueToBeCompared);
                int IntegerReferenceValueForComparision = 0;
                if (!ReferenceValueForComparision.contains(",")) {
                    IntegerReferenceValueForComparision = Integer.valueOf(ReferenceValueForComparision);
                }
                if (ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS GREATER THAN")) {
                    if (IntegerValueToBeCompared > IntegerReferenceValueForComparision)
                        returnvalue = true;
                } else if (ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS GREATER THAN EQUAL TO")) {
                    if (IntegerValueToBeCompared >= IntegerReferenceValueForComparision)
                        returnvalue = true;
                } else if (ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS LESS THAN")) {
                    if (IntegerValueToBeCompared < IntegerReferenceValueForComparision)
                        returnvalue = true;
                } else if (ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS LESS THAN EQUAL TO")) {
                    if (IntegerValueToBeCompared <= IntegerReferenceValueForComparision)
                        returnvalue = true;
                } else if (ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS BETWEEN AND")) {
                    String[] refvaluesarray = ReferenceValueForComparision.split(",");
                    int refstartvalue = Integer.valueOf(refvaluesarray[0]);
                    int refendvalue = Integer.valueOf(refvaluesarray[1]);
                    if ((IntegerValueToBeCompared >= refstartvalue) & (IntegerValueToBeCompared <= refendvalue)) {
                        returnvalue = true;
                    }
                }
            }
        } catch (Throwable t) {
            logError("Error Occured Inside comparevaluewithexpression function, desc=" + t.getMessage());
        }
        logInfo("comparevaluewithexpression function - END");
        return returnvalue;
    }

    /**
     * Move to particular element
     *
     * @param element
     */
    public void moveToElement(WebElement element) {
        try {
            Actions action = new Actions(driver);
            action.moveToElement(element).build().perform();
            logMessage("successfully moved to the element " + element);
        } catch (Throwable t) {
            logError("Error Occured Inside moveToElement function, desc=" + t.getMessage());
        }
    }
    //Code added by Cards Team

    public WebDriver getWebDriver() {
        return driver;
    }

    public WebElement findElementByAccessibilityId(String arg0) {

        return ((AppiumDriver) driver).findElementByAccessibilityId(arg0);
    }

    public WebElement findElementById(String arg0) {

        return ((AppiumDriver) driver).findElementById(arg0);

    }

    //Wait for JQuery Load
    public void waitForJQueryLoad() {
        //Wait for jQuery to load
        //WebDriver tempDriver = driver.getWebDriver();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        try {
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
        } catch (Exception e) {
            System.out.println("javascript error: jQuery is not defined");
        }

    }

    public void waitForPageLoaded() {

        //WebDriver tempDriver = driver.getWebDriver();

        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver tempDriver) {
                return ((JavascriptExecutor) tempDriver).executeScript("return document.readyState").equals("complete");
            }
        };

        // WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 30);

        try {

            wait.until(expectation);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    /* Function written by C114323
     * Wait till perticular element gets loaded
     * @parameter Webelement
     */
    public boolean isElementDisplayed(WebElement element) {
        int timeout = Integer.valueOf(System.getProperty("IMPLICITWAIT"));
        boolean elementPresent = true;
        int count = 0;
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        try {
            while (true) {
                try {
                    if (element.isDisplayed()) {
                        logMessage("Element is displayed successfully for element, = " + element);
                        break;
                    } else {
                        if (count == timeout) {
                            elementPresent = false;
                            logWarning("Element not displayed for element, = " + element);
                            break;
                        }
                        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
                        count++;
                    }
                } catch (Exception ex) {
                    if (count == timeout) {
                        elementPresent = false;
                        logError("Element not displayed for element, = " + element);
                        break;
                    }
                    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
                    count++;
                }
            }
        } catch (Throwable e) {
            logError("isElementDisplayed  for element error, = " + e.getMessage());
        }
        driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
        return elementPresent;
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
                logError("clickJS, error = " + e.getMessage());
            } catch (StaleElementReferenceException e) {
                logError("clickJS, error = " + e.getMessage());
            }
            blnClicked = true;
        }
        try {
            if (blnClicked) {
                logMessage("Element is clicked successfully, webelement = " + element);
            } else {
                logMessage("Element is NOT clicked, webelement = " + element);
            }
        } catch (StaleElementReferenceException e) {
            logError("clickJS, error = " + e.getMessage());
        }
    }

    protected By getObject(String objKey) {
        if(objKey.contains("~")){
            By locator=null;
            String[] arr = objKey.split("~");
            String key=arr[0];
            String value = arr[1];
            switch (key.toUpperCase()){
                case "XPATH":
                    locator = By.xpath(value);
                    break;
                case "ID":
                    locator = By.id(value);
                    break;
            }
            return locator;
        }else {
            return getObjectBy(objKey);
        }
    }


    protected By getObject(String objKey, int index) {

        return getObjectBy(objKey, index);
    }
	
	protected By getObject(String objKey, String value) {

		return getObjectBy(objKey,value);
	}
	

    /*
        protected By getObject(String objKey, Properties propertyFile) {

            return getObjectBy(objKey,propertyFile);
        }
    */
    public String getMessageText(String msgText, String Module) {
        String msgTextValue = null;
        try {
            switch (Module.toUpperCase()) {
                case "UXPBANKING365":
                    msgTextValue = System.getProperty(msgText);
                    break;
                case "CARDS":
                    msgTextValue = System.getProperty(msgText);
                    break;
                case "ACCOUNTS":
                    msgTextValue = System.getProperty(msgText);
                    break;
                case "SERVICES":
                    msgTextValue = System.getProperty(msgText);
                    break;
                case "APPLY":
                    msgTextValue = System.getProperty(msgText);
                    break;
                case "PAYMENTS":
                    msgTextValue = System.getProperty(msgText);
                    break;
                default:
                    logMan.error("Error Occured inside getMessageText: " + msgText);
                    break;

            }
        } catch (Exception e) {

        }
        return msgTextValue;


    }


    //Written By C114323
    public String getLoginData(String userData) {
        String loginData = null;
        loginData = System.getProperty(userData);
        return loginData;
    }

    /* Function to return device type
     */
    protected String getDeviceType() throws Exception {

        deviceType = System.getProperty("DEVICETYPE");
        try {
            String devTypeToGetProperty = "";
            if (deviceType.equalsIgnoreCase("MobileWeb")) {
                devTypeToGetProperty = "mw.";
            } else if (deviceType.equalsIgnoreCase("Web")) {
                devTypeToGetProperty = "w.";
            } else if (deviceType.equalsIgnoreCase("TabletWeb")) {
                devTypeToGetProperty = "tw.";
            } else if (deviceType.equalsIgnoreCase("App")) {
                devTypeToGetProperty = "app.";
            }
            return devTypeToGetProperty;
        } catch (Exception e) {
            logError("Exception in Method deviceType(): " + e.getMessage());
            throw new Exception();
        }

    }

    public boolean isElementDisplayed(By by) {
        boolean elementPresent = true;
        int count = 0;
        int timeout = waitTime;
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        try {
            while (true) {
                try {
                    if (driver.findElement(by).isDisplayed()) {
                        logMessage("Element is displayed successfully for locator, = " + by);
                        break;
                    } else {
                        if (count == timeout) {
                            elementPresent = false;
                            logError("Element not displayed for locator, = " + by);
                            break;
                        }
                        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
                        count++;
                    }
                } catch (Exception ex) {
                    if (count == timeout) {
                        elementPresent = false;
                        logError("Element not displayed for locator, = " + by);
                        break;
                    }
                    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
                    count++;
                }
            }
        } catch (Throwable e) {
            logError("isElementDisplayed for locator error, = " + e.getMessage());
        }
        driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
        return elementPresent;
    }

    public String getAPIendpoint(String locatorName) {
        String locatorProperty = System.getProperty(locatorName);
        //String locatorProperty = CCpropertyFile.getProperty(locatorName);
        System.out.println(locatorProperty.toString());
        //    			String[] arraylocator = locatorProperty.toString().split("|");
        //
        //    			String locatorType = arraylocator[0].toString();
        //    			String locatorValue = arraylocator[1].toString();
        //locatorProperty.split("|")[0];
        //String locatorValue = locatorProperty.split("|")[1];

        //    			System.out.println("*****LocatorType"+locatorType);
        //    			System.out.println("*****locatorValue"+locatorValue);
        String endpt = locatorProperty.toString();

        //    			switch(locatorType)
        //    			{
        //    			case "Tsys":
        //    				endpt = locatorValue;
        //    				break;
        //    			case "Fiserv":
        //    				endpt = locatorValue;
        //    				break;
        //    			}
        System.out.println("*****InsideRepoParser****" + endpt);
        return endpt;
    }

    //public By getbjectLocator(String locatorName)
    //Written by Atmaj
    public By getObjectBy(String locatorName) {
        String locatorProperty = null;
        try {
            locatorProperty = System.getProperty(locatorName);
        } finally {
            if (locatorProperty==null) {
                if(locatorName.contains("app.")){
                    String[] arr = locatorName.split("\\.");
                    StringBuilder builder = new StringBuilder();
                    for (int i=1;i<arr.length;i++){
                        builder.append(arr[i]).append(".");
                    }
                    String str = builder.toString();
                    String newStr = str.substring(0, str.length() - 1);

                    locatorName="mw."+newStr;
                    locatorProperty = System.getProperty(locatorName);
                }
            }
        }
        String locatorType = locatorProperty.split("~")[0];
        String locatorValue = locatorProperty.split("~")[1];

        By locator = null;
        try {
            switch (locatorType.toUpperCase()) {
                case "ID":
                    locator = By.id(locatorValue);
                    break;
                case "NAME":
                    locator = By.name(locatorValue);
                    break;
                case "CSSSELECTOR":
                    locator = By.cssSelector(locatorValue);
                    break;
                case "LINKTEXT":
                    locator = By.linkText(locatorValue);
                    break;
                case "PARTIALLINKTEXT":
                    locator = By.partialLinkText(locatorValue);
                    break;
                case "TAGNAME":
                    locator = By.tagName(locatorValue);
                    break;
                case "XPATH":
                    locator = By.xpath(locatorValue);
                    break;
                default:
                    logMan.error(" Object identifier needs to be corrected in property file: " + locatorValue);
                    break;
            }
        } catch (Exception e) {
            logMan.fatal(" Object identifier not found in property file: " + e.getMessage());

        }
        return locator;

    }
/*
	//Written by C114323
	
	public By getObjectBy(String locatorName, Properties propertyFile)
	{
		String locatorProperty = propertyFile.getProperty(locatorName);
		System.out.println(locatorProperty.toString());
		String locatorType = locatorProperty.split("~")[0];
		String locatorValue = locatorProperty.split("~")[1];
		
		By locator = null;
		try {
			switch(locatorType.toUpperCase())
			{
			case "ID":
				locator = By.id(locatorValue);
				break;
			case "NAME":
				locator = By.name(locatorValue);
				break;
			case "CSSSELECTOR":
				locator = By.cssSelector(locatorValue);
				break;
			case "LINKTEXT":
				locator = By.linkText(locatorValue);
				break;
			case "PARTIALLINKTEXT":
				locator = By.partialLinkText(locatorValue);
				break;
			case "TAGNAME":
				locator = By.tagName(locatorValue);
				break;
			case "XPATH":
				locator = By.xpath(locatorValue);
				break;
			default:
				logMan.error(" Object identifier needs to be corrected in property file: "+locatorValue);
				break;
			}
		} catch (Exception e) {
			logMan.fatal(" Object identifier not found in property file: "+e.getMessage());

		}
		return locator;
	}
	
*/


    public By getObjectBy(String locatorName, int index) {
        String locatorProperty = System.getProperty(locatorName);
        //String locatorProperty = CCpropertyFile.getProperty(locatorName);
        System.out.println(locatorProperty.toString());
        String locatorType = locatorProperty.split("~")[0];
        String locatorValue = locatorProperty.split("~")[1];

        if (locatorValue.contains("DINDEX")) {

            String[] arrlocatorValue = locatorValue.split(" ");
            for (int i = 0; i < arrlocatorValue.length; i++) {

                if (arrlocatorValue[i].contains("DINDEX")) {
                    arrlocatorValue[i] = arrlocatorValue[i].replaceAll("DINDEX", String.valueOf(index));
                    //String.valueOf(index);

                }

            }
            String JoinString = String.join(" ", arrlocatorValue);
            logMan.info("******JOINSTRING*******" + JoinString);
            locatorValue = JoinString;
        }

        By locator = null;
        try {
            switch (locatorType.toUpperCase()) {
                case "ID":
                    locator = By.id(locatorValue);
                    break;
                case "NAME":
                    locator = By.name(locatorValue);
                    break;
                case "CSSSELECTOR":
                    locator = By.cssSelector(locatorValue);
                    break;
                case "LINKTEXT":
                    locator = By.linkText(locatorValue);
                    break;
                case "PARTIALLINKTEXT":
                    locator = By.partialLinkText(locatorValue);
                    break;
                case "TAGNAME":
                    locator = By.tagName(locatorValue);
                    break;
                case "XPATH":
                    locator = By.xpath(locatorValue);
                    break;
                default:
                    logMan.error(" Object identifier needs to be corrected in property file: " + locatorValue);
                    break;
            }
        } catch (Exception e) {
            logMan.fatal(" Object identifier not found in property file: " + e.getMessage());

        }
		/*String newVar=locator;
		if(((List<WebElement>) locator).contains("DINDEX")){
			System.out.println("***INSIDE IF****" + String.valueOf(index));
			//locatorValue.replaceAll("DINDEX", String.valueOf(index));
			locator.replace("DINDEX", String.valueOf(index));
			System.out.println("***INSIDE IF****" + locator);
		}*/
        logMan.info("LOCATOR" + locator);
        return locator;
    }

	//Written By - C111079 
	public By getObjectBy(String locatorName,String value)
	{
		String locatorProperty = System.getProperty(locatorName);
		//String locatorProperty = CCpropertyFile.getProperty(locatorName);
		System.out.println(locatorProperty.toString());
		String locatorType = locatorProperty.split("~")[0];
		String locatorValue = locatorProperty.split("~")[1];
		
		if(locatorValue.contains("DVALUE")){
			
			String[] arrlocatorValue=locatorValue.split(" ");
			for(int i=0;i<arrlocatorValue.length;i++){					
				
				if(arrlocatorValue[i].contains("DVALUE")){
					arrlocatorValue[i]=arrlocatorValue[i].replaceAll("DVALUE", value);
					//String.valueOf(index);
					
				}
				
			}
			String JoinString = String.join(" ", arrlocatorValue);
			logMan.info("******JOINSTRING*******"+ JoinString);
			locatorValue=JoinString;
		}

		By locator = null;
		try {
			switch(locatorType.toUpperCase())
			{
			case "ID":
				locator = By.id(locatorValue);
				break;
			case "NAME":
				locator = By.name(locatorValue);
				break;
			case "CSSSELECTOR":
				locator = By.cssSelector(locatorValue);
				break;
			case "LINKTEXT":
				locator = By.linkText(locatorValue);
				break;
			case "PARTIALLINKTEXT":
				locator = By.partialLinkText(locatorValue);
				break;
			case "TAGNAME":
				locator = By.tagName(locatorValue);
				break;
			case "XPATH":
				locator = By.xpath(locatorValue);
				break;
			default:
				logMan.error(" Object identifier needs to be corrected in property file: "+locatorValue);
				break;
			}
		} catch (Exception e) {
			logMan.fatal(" Object identifier not found in property file: "+e.getMessage());

		}
		/*String newVar=locator;
		if(((List<WebElement>) locator).contains("DINDEX")){
			System.out.println("***INSIDE IF****" + String.valueOf(index));
			//locatorValue.replaceAll("DINDEX", String.valueOf(index));
			locator.replace("DINDEX", String.valueOf(index));
			System.out.println("***INSIDE IF****" + locator);
		}*/
		logMan.info("LOCATOR" + locator);
		return locator;
	}

	
	
    public void clickJS(By locator) {
        boolean blnClicked = false;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();",
                driver.findElement(locator));
        int waitTime = Integer.valueOf(System.getProperty("IMPLICITWAIT"));
        if (isElementDisplayed(locator)) {
            try {
                WebElement element = driver.findElement(locator);
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
            } catch (UnreachableBrowserException e) {
                logError("clickJS, error = " + e.getMessage());
            } catch (StaleElementReferenceException e) {
                logError("clickJS, error = " + e.getMessage());
            }
            blnClicked = true;
        }
        try {
            if (blnClicked) {
                logMessage("Element is clicked successfully, locator = " + locator);
            } else {
                logError("Element is NOT clicked, locator = " + locator);
            }
        } catch (StaleElementReferenceException e) {
            logError("clickJS, error = " + e.getMessage());
        }
    }

    public void clickJS(By locator, String pageCtrlName) {
        boolean blnClicked = false;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();",
                driver.findElement(locator));
        int waitTime = Integer.valueOf(System.getProperty("IMPLICITWAIT"));
        if (isElementDisplayed(locator)) {
            try {
                WebElement element = driver.findElement(locator);
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
            } catch (UnreachableBrowserException e) {
                logError("clickJS, error = " + e.getMessage());
            } catch (StaleElementReferenceException e) {
                logError("clickJS, error = " + e.getMessage());
            }
            blnClicked = true;
        }
        try {
            if (blnClicked) {
                logMessage(pageCtrlName + " Element is clicked successfully, locator = " + locator);
                injectMessageToCucumberReport(pageCtrlName + " Element is clicked successfully");
                Allure.step(pageCtrlName + " Element is clicked successfully ", Status.PASSED);
            } else {
                logError(pageCtrlName + " Element is NOT clicked, locator = " + locator);
                Allure.step(pageCtrlName + " Element is NOT clicked successfully ", Status.FAILED);
            }
        } catch (StaleElementReferenceException e) {
            logError("Error Occured inside " + e.getMessage());
            injectWarningMessageToCucumberReport("Failure in ClickJS " + e.getMessage());
            Allure.step(pageCtrlName + " Element is NOT clicked successfully ", Status.FAILED);
            appendScreenshotToCucumberReport();
        }
    }


    public boolean isElementDisplayed(By by, int timeout) throws InterruptedException {

        boolean elementPresent = true;
        int count = 0;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        // Thread.sleep(4000);
        while (elementPresent) {
            try {
                if (driver.findElement(by).isDisplayed()) {
                    break;
                } else {
                    if (count == timeout) {
                        elementPresent = false;
                        break;
                    }
                    Thread.sleep(1000);
                    count++;
                }
            } catch (Exception ex) {
                if (count == timeout) {
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
    //

    public boolean isElementDisplayed(By by, int timeout, String pageCtrlName) throws InterruptedException {

        boolean elementPresent = true;
        int count = 0;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        // Thread.sleep(4000);
        while (elementPresent) {
            try {
                if (driver.findElement(by).isDisplayed()) {
                    break;
                } else {
                    if (count == timeout) {
                        elementPresent = false;
                        break;
                    }
                    Thread.sleep(1000);
                    count++;
                }
            } catch (Exception ex) {
                if (count == timeout) {
                    elementPresent = false;
                    break;
                }

                Thread.sleep(1000);
                count++;
            }
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        try {
            if (elementPresent) {
                logMessage(pageCtrlName + " Element is present, locator = " + by);
                injectMessageToCucumberReport(pageCtrlName + " Element is present");
                Allure.step(pageCtrlName + " Element is present ", Status.PASSED);
            } else {
                logError(pageCtrlName + " Element is NOT present, locator = " + by);
                Allure.step(pageCtrlName + " Element is NOT present ", Status.FAILED);
            }
        } catch (StaleElementReferenceException e) {
            logError("Error Occured inside " + e.getMessage());
            injectWarningMessageToCucumberReport("Failure in isElementDisplayed " + e.getMessage());
            Allure.step(pageCtrlName + " Element is NOT present ", Status.FAILED);
            appendScreenshotToCucumberReport();
        }
        return elementPresent;
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
            logError("isElementDisplayedWithLog, error = " + e.getMessage());
        }
        if (isDisplayed) {
            logMessage("Element displayed successfully");
        } else {
            logError("Element Not displayed successfully");
        }
        return isDisplayed;
    }

    public List<WebElement> findElements(By elementBy) throws InterruptedException {
        List<WebElement> lstElements = null;

        if (isElementDisplayed(elementBy)) {
            lstElements = driver.findElements(elementBy);

        } else {


        }
        return lstElements;
    }

    //Writter By C114323
    public void waitForElementToClickable(By by, int timeout) {
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
                .implicitlyWait(timeout, TimeUnit.SECONDS);

    }


    public String getText(By elementBy) throws Exception {
        try {
            if (isElementDisplayed(elementBy)) {
                return driver.findElement(elementBy).getText().trim();
            } else {
                return "";
            }
        } catch (Exception e) {
            logError("getText, error = " + e.getMessage());
            return "";
        }
    }

    public void setRelevantWebViewTab() {
        boolean bflag = false;
        changeNativeToWebview();
        try {
            Iterable<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                driver.switchTo().window(windowHandle);
                if (driver.getTitle().equals("Bank of Ireland") || driver.getTitle().equals("Temenos") || driver.getTitle().contains("Bank of Ireland")) {
                    bflag = true;
                    logMessage("WebView with browser title 'Bank of Ireland' set");
                    appendScreenshotToCucumberReport();
                    break;
                }
            }
            if (!bflag) {
                logError("WebView with browser title 'Bank of Ireland' not found");
                Assert.fail("WebView with browser title 'Bank of Ireland' not found");
            }
        } catch (Throwable e) {
            logError("WebView with browser title 'Bank of Ireland' not found, error = " + e.getMessage());
            Assert.fail("WebView with browser title 'Bank of Ireland' not found");
        }
    }

    public void scrollToView(By locator) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(locator));
            logMessage("successfully Scrolled to element, locator =  " + locator);
        } catch (Throwable e) {
            logError("Unable to scroll to view");
        }

    }

    public void scrollToView(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();",
                    element);
            logMessage("successfully Scrolled to element, webElement =  " + element);
        } catch (Throwable e) {
            logError("Unable to scroll to view");
        }

    }

    //Written By C114323
    public String getTestData(String testDataName) {
        String fileTestData = null;
        fileTestData = System.getProperty(testDataName);
        return fileTestData;
    }

    public AppiumDriver getAppiumDriver() {
        return appiumDriver;
    }

    // Written by C113329
    public void selectValueFromDropDown(By locator, String value) {
        try {
            List<WebElement> allOptions = driver.findElements(locator);
            for (int option = 0; option <= allOptions.size() - 1; option++) {
                if (allOptions.get(option).getText().toUpperCase().contains(value)) {
                    allOptions.get(option).click();
                    break;
                }
            }
        } catch (Exception e) {
            logError("Error Occured inside selectValueFromDropDown " + e.getMessage());
            injectWarningMessageToCucumberReport("Failure in select value from drop down " + e.getMessage());
            Allure.step("Select value from drop down NOT Successfully ", Status.FAILED);
            appendScreenshotToCucumberReport();

        }
    }


    public void changeNativeToWebview() {
        try {
            Set<String> availableContexts = getContextHandles();
            logMessage("Total No of Context Found After we reach to WebView = " + availableContexts.size());
            for (String context : availableContexts) {
                if(driver.toString().contains("IOS")){
                    if(context.contains(CONTEXTVIEW2)){
                        logMessage("Context Name is " + context);
                        context(context);
                        break;
                    }
                }else{
                    //if (context.equalsIgnoreCase(CONTEXTVIEW1) || context.equalsIgnoreCase(CONTEXTVIEW3)) {
                    if (context.equalsIgnoreCase("WEBVIEW_"+System.getProperty("APPPACKAGENAME"))) {
                    logMessage("Context Name is " + context);
                    context(context);
                    break;
                    }
                }
                /*if (context.contains(CONTEXTVIEW1) || context.contains(CONTEXTVIEW3) || context.contains(CONTEXTVIEW2)) {
                    logMessage("Context Name is " + context);
                    context(context);
                    break;
                }*/
            }
        } catch (Throwable e) {
            logError("changeNativeToWebview, error =" + e.getMessage());
        }
    }


    /**
     * Method to click element using Java script
     *
     * @param locator
     * @param alt     Name
     * @throws Exception
     */
	/*public void clickJS(By locator, String alt) throws InterruptedException {

    		        boolean blnClicked = false;
    		        JavascriptExecutor js = (JavascriptExecutor) driver..getWebDriver();
    		        js.executeScript("arguments[0].scrollIntoView();",
    		                driver.findElement(locator));

    		        if (isElementDisplayed(locator, waitTime)) {
    		            try {
    		                WebElement element = driver.findElement(locator);
    		                JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
    		                executor.executeScript("arguments[0].click();", element);
    		            } catch (UnreachableBrowserException e) {
    		                e.printStackTrace();
    		            } catch (StaleElementReferenceException e) {
    		                e.printStackTrace();
    		            }
    		            blnClicked = true;
    		        }
//    		        waitForPageLoaded();
//    		        waitForJQueryLoad();
    		        try {
    		            if (blnClicked) {
//    		                report.updateTestLog("Click <b>" + alt + "</b>", "<b>" + alt
//    		                        + "</b> button clicked", Status_CRAFT.PASS);
    		            } else {
//    		                report.updateTestLog("Click <b>" + alt + "</b>",
//    		                        "Unable to click on <b>" + alt + "</b>", Status_CRAFT.FAIL);
    		            }
    		        } catch (StaleElementReferenceException e) {
    		            e.printStackTrace();
    		        }
    		    }



	 */

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<WebElement> findElementsByName(String arg0) {
        return getAppiumDriver().findElementsByName(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>PERFECTO i.e.,
     * {@link 'IMobileWebDriver} OR <b>APPIUM i.e., {@link AppiumDriver} OR
     * <b>SEETEST i.e., {@link 'MobileWebDriver}.
     */
    @SuppressWarnings("rawtypes")
    public WebElement findElementByXPath(String arg0) {
        return getAppiumDriver().findElementByXPath(arg0);

    }


    /**
     * Function Applicable only when the tool used is <b>PERFECTO i.e.,
     * {@link 'IMobileWebDriver} OR <b>APPIUM i.e., {@link AppiumDriver} OR
     * <b>SEETEST i.e., {@link 'MobileWebDriver}.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<WebElement> findElementsByLinkText(String arg0) {
        return getAppiumDriver().findElementsByLinkText(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>PERFECTO i.e.,
     * {@link 'IMobileWebDriver} OR <b>APPIUM i.e., {@link AppiumDriver} OR
     * <b>SEETEST i.e., {@link 'MobileWebDriver}.
     */
    @SuppressWarnings("rawtypes")
    public WebElement findElementByPartialLinkText(String arg0) {
        return getAppiumDriver().findElementByPartialLinkText(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>PERFECTO i.e.,
     * {@link 'IMobileWebDriver} OR <b>APPIUM i.e., {@link AppiumDriver} OR
     * <b>SEETEST i.e., {@link 'MobileWebDriver}.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<WebElement> findElementsByPartialLinkText(String arg0) {
        return getAppiumDriver().findElementsByPartialLinkText(arg0);
    }


    /**
     * Function Applicable only when the tool used is <b>PERFECTO i.e.,
     * {@link 'IMobileWebDriver} OR <b>APPIUM i.e., {@link AppiumDriver} OR
     * <b>SEETEST i.e., {@link 'MobileWebDriver}.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<WebElement> findElementsByClassName(String arg0) {
        return getAppiumDriver().findElementsByClassName(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public WebElement findElementByTagName(String arg0) {
        return getAppiumDriver().findElementByTagName(arg0);

    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<WebElement> findElementsByTagName(String arg0) {
        return getAppiumDriver().findElementsByTagName(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public WebElement findElementByCssSelector(String arg0) {
        return getAppiumDriver().findElementByCssSelector(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<WebElement> findElementsByCssSelector(String arg0) {
        return getAppiumDriver().findElementsByCssSelector(arg0);

    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public Capabilities getCapabilities() {
        return getAppiumDriver().getCapabilities();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public CommandExecutor getCommandExecutor() {
        return getAppiumDriver().getCommandExecutor();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public ErrorHandler getErrorHandler() {
        return getAppiumDriver().getErrorHandler();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public ExecuteMethod getExecuteMethod() {
        return getAppiumDriver().getExecuteMethod();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public FileDetector getFileDetector() {
        return getAppiumDriver().getFileDetector();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public Keyboard getKeyboard() {
        return getAppiumDriver().getKeyboard();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({"rawtypes", "deprecation"})
    public Mouse getMouse() {
        return getAppiumDriver().getMouse();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Response execute(String driverCommand, Map<String, ?> parameters) {
        return getAppiumDriver().execute(driverCommand, parameters);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void performMultiTouchAction(MultiTouchAction arg0) {
        getAppiumDriver().performMultiTouchAction(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public TouchAction performTouchAction(TouchAction arg0) {
        return getAppiumDriver().performTouchAction(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public String getContext() {
        return getAppiumDriver().getContext();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Set<String> getContextHandles() {
        return getAppiumDriver().getContextHandles();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public ScreenOrientation getOrientation() {
        return getAppiumDriver().getOrientation();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public URL getRemoteAddress() {
        return getAppiumDriver().getRemoteAddress();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public SessionId getSessionId() {
        return getAppiumDriver().getSessionId();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void rotate(ScreenOrientation arg0) {
        getAppiumDriver().rotate(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<WebElement> findElementsByAccessibilityId(String arg0) {

        return getAppiumDriver().findElementsByAccessibilityId(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public Location location() {
        return getAppiumDriver().location();
    }

    // public int lockScreen(int seconds){
    // return getAppiumDriver().lockScreen(seconds);
    // }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void setLocation(Location arg0) {
        getAppiumDriver().setLocation(arg0);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void hideKeyboard() {
        getAppiumDriver().hideKeyboard();
    }


    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void setErrorHandler(ErrorHandler handler) {
        getAppiumDriver().setErrorHandler(handler);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void setFileDetector(FileDetector detector) {
        getAppiumDriver().setFileDetector(detector);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void setLogLevel(Level level) {
        getAppiumDriver().setLogLevel(level);
    }


    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public byte[] pullFile(String remotePath) {
        return getAppiumDriver().pullFile(remotePath);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public byte[] pullFolder(String remotePath) {
        return getAppiumDriver().pullFolder(remotePath);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void closeApp() {
        getAppiumDriver().closeApp();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void installApp(String appPath) {
        getAppiumDriver().installApp(appPath);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public boolean isAppInstalled(String bundleId) {
        return getAppiumDriver().isAppInstalled(bundleId);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void launchApp() {
        getAppiumDriver().launchApp();
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void removeApp(String bundleId) {
        getAppiumDriver().removeApp(bundleId);
    }

    /**
     * Function Applicable only when the tool used is <b>APPIUM i.e.,
     * {@link AppiumDriver}.
     */
    @SuppressWarnings("rawtypes")
    public void resetApp() {
        getAppiumDriver().resetApp();
    }

    public boolean explicitWaitForVisibilityOfElement(By by) {
        boolean flag;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            flag = true;
        } catch (Exception e) {
            flag = false;
            //logError("Element, "+by.toString()+" is not visible , Error = "+e.getMessage());
        }
        return flag;
    }

    public boolean fluentWaitCondition(By locatorValue) {
        boolean flag = false;
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(35))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);

        WebElement element;
        try {
            element = wait.until(new Function<WebDriver, WebElement>() {
                @Override
                public WebElement apply(WebDriver webDriver) {
                    return driver.findElement(locatorValue);
                }
            });
            if (element.isDisplayed()) {
                flag = true;
            }

        } catch (Exception e) {
            flag = false;
            logWarning("Fluent wait condition is 'false', Message =" + e.getMessage());
        }

        return flag;
    }

    public WebElement waitForVisibilityOfElement(By by) {
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            logError("Element, " + by.toString() + " is not visible , Error = " + e.getMessage());
        }
        return element;
    }

    public boolean explicitWaitForElementTobeClickable(By by) {
        boolean flag;
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
            flag = true;
        } catch (Exception e) {
            flag = false;
            //logError("Element, "+by.toString()+" is not displayed , Error = "+e.getMessage());
        }
        return flag;
    }

    public void sendKey(By locator, String valuetoType) throws InterruptedException {
        if (isElementDisplayed(locator)) {
            try {
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(valuetoType);
                logMessage("Entered the value , = " + valuetoType);
            } catch (StaleElementReferenceException e) {
                logError("Stalelement exception, error = " + e.getMessage());
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(valuetoType);
                logMessage("Entered the value , = " + valuetoType);
            }
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value=arguments[1];",
                    driver.findElement(locator), valuetoType);
            logMessage("Entered the value using javascript , value  = " + valuetoType);
        }
    }

    public void changeWebViewToNative() {
        try {
            Set<String> Contexts = getContextHandles();
            for (String context : Contexts) {
                if (context.contains("NATIVE")) {
                    logMessage("Context name is " + context);
                    context(context);
                    break;
                }
            }
        } catch (Exception e) {
            logError("changeWebviewToNative, error =" + e.getMessage());
        }
    }
public void sendKeysJS(By locator, String valuetoType) throws InterruptedException {
		boolean blnTextEntered = false;

		if (isElementDisplayed(locator, 5)) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				WebElement inputField = driver.findElement(locator);
				js.executeScript("arguments[0].setAttribute('value', '" + valuetoType + "')", inputField);
				blnTextEntered = true;
			} catch (StaleElementReferenceException e) {
				e.printStackTrace();
				driver.findElement(locator).sendKeys(valuetoType);
				blnTextEntered = true;
			}
		}
		if (blnTextEntered) {
			logMessage("Entered value = "+valuetoType);
		} else {
			logError("Unable to enter value = " +valuetoType);
		}

	}
  
	public void ScrollAndClickJS(String linkToClick) throws InterruptedException {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToClick))));
			Thread.sleep(2000);
			//report.updateTestLog("ScrollAndClickJS", "Element To Click", Status_CRAFT.DONE);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(getObject((linkToClick))));
			Thread.sleep(3000);
		} catch (UnreachableBrowserException e) {
			e.printStackTrace();
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This will scroll till element and click on that
	 */
	//Written by C114323 - 19/07/2021
	public void ScrollAndClickJS(WebElement linkToClick) throws InterruptedException {
	    try {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].scrollIntoView(false);", linkToClick);
	        Thread.sleep(2000);
	        JavascriptExecutor executor = (JavascriptExecutor) driver;
	        executor.executeScript("arguments[0].click();", linkToClick);
	        Thread.sleep(3000);
	    } catch (UnreachableBrowserException e) {
	        e.printStackTrace();
	    } catch (StaleElementReferenceException e) {
	        e.printStackTrace();
	    }
	}
}

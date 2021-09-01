package com.boi.grp.pageobjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.boi.grp.utilities.LogManager;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.boi.grp.hooks.Hooks;
import com.boi.grp.utilities.LogManagerPreRun;



public class BasePageForBrowsers extends BasePage {
	public Logger logMan = null;
	public WebDriver driver = null;
	public WebDriverWait wait = null;
	public FileInputStream FIS = null;
	public Properties Config = null;

	public BasePageForBrowsers(WebDriver driver) {
		this.driver = driver;

		logMan = LogManager.getInstance();
		try {
			FIS = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/repository/objectRepo.properties");
			Config = new Properties();
			Config.load(FIS);
			wait = new WebDriverWait(driver, Integer.valueOf(System.getProperty("EXPLICITWAIT")));
			driver.manage().timeouts().implicitlyWait(Integer.valueOf(System.getProperty("IMPLICITWAIT")),
					TimeUnit.SECONDS);
		} catch (FileNotFoundException e) {
			logError(
					"Please check the Config.properties file at src//test//java//com.boi.grp.globalconfig//GlobalConfig.properties, it does not exists, Stoping Execution");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logError(
					"IO execption occured accessing Config.properties file at src//test//java//com.boi.grp.globalconfig//GlobalConfig.properties - Stoping Execution");
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
		 // stick it in the report
	}

    public By getWebElementFromOR(String key){
        By by = null;
        try {
            String locator=Config.getProperty(key);
            if (locator.contains("DATATOBEREPLACED")){
                locator.replaceAll("DATATOBEREPLACED","");
            }
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

    public By getDynamicWebElementFromOR(String key,String dynamicData){
        By by = null;
        try {
            String locator=Config.getProperty(key);
            if (locator.contains("DATATOBEREPLACED")){
                locator=locator.replaceAll("DATATOBEREPLACED",dynamicData);
            }
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
                /*driver.findElement(Field).click();
                driver.findElement(Field).sendKeys("\n");*/
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
            logError("Error in EnterClick method, Error = "+e.getMessage());
        }
    }

    public void searchValueInGoogle(By Field,By listItem){
        try {
            if(System.getProperty("PLATFORM").equalsIgnoreCase("WINDOWS")){
                EnterClick();
            }else{
                if(System.getProperty("DEVICEPLATFORM").equalsIgnoreCase("ANDROID")){
                    driver.findElement(Field).click();
                    driver.findElement(Field).sendKeys("\n");
                }else if (System.getProperty("DEVICEPLATFORM").equalsIgnoreCase("IOS")){
                    //enter is not working so user can select from list
                    clickWithBy(listItem);
                }
            }

        } catch (Exception e) {
            logError("Error in EnterClick method, Error = "+e.getMessage());
        }

    }

    public void EnterClickForAppium() {
        try {
            logMan.info("EnterClick Start");

            if(System.getProperty("DEVICEPLATFORM").equalsIgnoreCase("IOS")){
                IOSDriver iosDriver=(IOSDriver) driver;
                //iosDriver.getKeyboard().pressKey(Keys.ENTER);
                //iosDriver.getKeyboard().sendKeys(Keys.RETURN);
                //iosDriver.executeScript("mobile:performEditorAction", ImmutableMap.of("action","done"));
                //iosDriver.getKeyboard().releaseKey(Keys.ENTER);
            }else {
                AndroidDriver andriodDriver = (AndroidDriver) driver;
                andriodDriver.pressKey(new KeyEvent(AndroidKey.ENTER));
            }
            logMan.info("EnterClick End");
        } catch (Exception e) {

            IOSDriver iosDriver=(IOSDriver) driver;
            iosDriver.getKeyboard().pressKey(Keys.ENTER);
            logError("Error in EnterClick method, Error = "+e.getMessage());
        }
    }

    /**
     *
     * @param ElementToBeClicked
     */
	public void click(WebElement ElementToBeClicked){
	    try{
			wait.until(ExpectedConditions.elementToBeClickable(ElementToBeClicked));
			highlight(ElementToBeClicked);
            ElementToBeClicked.click();
			logMessage("Clicked successfully on Element with Description "+ElementToBeClicked.toString());
		} catch (NoSuchElementException e){
			logError("Field Not found, Field with description "+ElementToBeClicked.toString());
		}
	}

    public void clickWithBy(By by){
        try{
            wait.until(ExpectedConditions.elementToBeClickable(by));
            driver.findElement(by).click();
            logMessage("Clicked successfully on Element with Description "+by.toString());
        } catch (NoSuchElementException e){
            logError("Field Not found, Field with description "+by.toString());
        }
    }

    /**
     *
     * @param ElementToBeDoubleClicked
     */
	public void doubleclick(WebElement ElementToBeDoubleClicked){
		try {
			wait.until(ExpectedConditions.elementToBeClickable(ElementToBeDoubleClicked));
			Actions action = new Actions(driver);
			action.doubleClick(ElementToBeDoubleClicked).build().perform();
			logMessage("Double clicked successfully on Element with Description "+ElementToBeDoubleClicked.toString());
		} catch (NoSuchElementException e){
		    logError("Field Not found, Field with description "+ElementToBeDoubleClicked.toString());
		}
	}

    /**
     *
     * @param by
     */
	public void doubleclick(By by){
		try {
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(by)).doubleClick().build().perform();
			logMessage("Double clicked successfully on Element with Xpath ");
		} catch (NoSuchElementException e){
			logError("Field Not found, Field with xpath ");
		}
	}

    /**
     *
     * @param Element
     * @param SuccessMessage
     * @param FailureMessage
     * @return whether element is displayed or not
     */
    public boolean verifyElement(WebElement Element,String SuccessMessage,String FailureMessage){
        try {
            if (Element.isDisplayed()){
                if(SuccessMessage != ""){
                    logMessage(SuccessMessage);
                }
                return true;
            } else{
                if(FailureMessage != ""){
                    logError(FailureMessage);
                }
                return false;
            }
        } catch (NoSuchElementException e) {
            logError("No Such Element Found, Element desctiption  ="+Element.toString());
            return false;
        }
    }

    /**
     *
     * @param Xpath
     * @param SuccessMessage
     * @param FailureMessage
     * @return verification for the visibility of the element
     */
    public boolean verifyElement(String Xpath,String SuccessMessage,String FailureMessage){
        try {
            if (driver.findElements(By.xpath(Xpath)).size()>0){
                if(SuccessMessage != ""){
                    logMessage(SuccessMessage);
                }
                return true;
            } else{
                if(FailureMessage != ""){
                    logError(FailureMessage);
                }
                return false;
            }
        } catch (NoSuchElementException e) {
            logError("No Such Element Found, Element Xpath  ="+Xpath);
            return false;
        }
    }

    /**
     * wait for page to be loaded completely
     */
    public void waitForBrowserToCompleteProcessing(){
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        logInfo("Page load completed");
    }

    /**
     * This will be used to mousehover when having xpath
     * @param Xpath
     */
    public void mouseHover(String Xpath){
        if(driver.findElements(By.xpath(Xpath)).size()==1){
            logInfo("mouseHover, Found element with xpath = "+Xpath);
            Actions action=new Actions(driver);
            action.moveToElement(driver.findElement(By.xpath(Xpath))).click().perform();
            logMessage("MouseHover done over"+Xpath);
        }
        else if(driver.findElements(By.xpath(Xpath)).size() >1){
            logError("More than 1 element identified with xpath "+Xpath);
        } else{
            logError("No element identified with xpath "+Xpath);
        }
    }

    /**
     * This will be used to mousehover when having WebElement
     * @param element
     */
    public void mouseHover(WebElement element){
        try {
            Actions action = new Actions(driver);
            action.moveToElement(element).click().perform();
            logMessage("MouseHover done over" + element.getText());
        } catch(Throwable t){
            logError("Error Occured Inside mouseHover function");
        }
    }

    /**
     * Thread wait
     * @param Seconds
     */
    public void waitForSeconds(int Seconds){
        try {
            Thread.sleep(1000*Seconds);
        } catch (Throwable e) {
            logError("Error Occured in waitForSeconds function's  thread.sleep section, reason = "+e.getMessage());
        }
    }

    /**
     * This will scroll up the page
     */
    public void scrollUp(){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,-550)", "");
        logMessage("Scrolled up the Page");
        waitForSeconds(1);
    }

    /**
     * This will scroll up the page A BIT
     */
    public void scrollUpAbit(){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,-100)", "");
        logMessage("Scrolled up the Page a bit");
        waitForSeconds(1);
    }

    /**
     * This will scroll down the page
     */
    public void scrollDown(){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,500)", "");
        logMessage("Scrolled down the Page");
        waitForSeconds(1);
    }

    /**
     * This will highlight element in RED
     * @param element
     */
    public void highlight(WebElement element){
        try{
            JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
            jsDriver.executeScript("arguments[0].style.border='3px solid red'", element);
            logMessage("Highlighted successfully on "+element.toString());
        } catch (Exception e){
            logError("Error Occured While Highlighting a control ,Reason = " + e.getMessage());
        }
    }

    /**
     * This will highlight element in BLUE
     * @param element
     */
    public void highlightBlue(WebElement element){
        try{
            JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
            jsDriver.executeScript("arguments[0].style.border='3px solid blue'", element);
            logMessage("Highlighted successfully on "+element.toString());
        } catch (Exception e){
            logError("Error Occured While Highlighting a control ,Reason = " + e.getMessage());
        }
    }

    /**
     * Check the presence of element
     * @param by
     * @return boolean
     */
    public boolean checkElement(By by){
        if(driver.findElements(by).size()==1){
            highlight(driver.findElements(by).get(0));
            logInfo("Found element with element = "+by);
            return true;
        } else
            return false;
    }

    /**
     * Check the presence of element and click
     * @param by
     * @return boolean
     */
    public boolean checkElementAndClick(By by){
        boolean returnvalue=false;
        if(checkElement(by)){
            click(driver.findElement(by));
            logMessage("Clicked successfully on element with xpath = "+by);
            returnvalue=true;
        }
        return returnvalue;
    }

    /**
     * Double Click and presence of element
     * @param by
     * @return boolean condition
     */
    public boolean checkElementAndDoubleClick(By by){
        if(checkElement(by)){
            doubleclick(by);
            logMessage("Clicked successfully on element with xpath = "+by);
            return true;
        } else
            return false;
    }

    /**
     * Select value from list of weblement
     * @param SelectAsWebElement
     * @param ValueToBeSelected
     * @return boolean
     */
    public boolean selectValueFromList(WebElement SelectAsWebElement,String ValueToBeSelected){
        try{
            Select list=new Select(SelectAsWebElement);
            list.selectByVisibleText(ValueToBeSelected);
            logMessage("Value "+ValueToBeSelected+" is selected successfully in the list "+SelectAsWebElement.toString());
        } catch (Exception e) {
            logError("Value "+ValueToBeSelected+" is not found in List "+SelectAsWebElement.toString());
            return false;
        }
        return true;
    }

    /**
     * Select value from list when having parent Xpath
     * @param DropdownParentXpath
     * @param ValueToBeSelected
     * @return
     */
    public boolean selectValueFromList(By DropdownParentXpath,String ValueToBeSelected){
        boolean returnvalue=false;
        try{
            List<WebElement> dropDownlist = driver.findElements(DropdownParentXpath);
            for (WebElement element:dropDownlist) {
                if(element.getText().equalsIgnoreCase(ValueToBeSelected)){
                    element.click();
                    logMessage("Value "+ValueToBeSelected+" is selected successfully in the list , xpath = "+DropdownParentXpath);
                    returnvalue=true;
                }
            }
        } catch (Exception e) {
            logError("Value "+ValueToBeSelected+" is not found in List, where Xpath =  "+ValueToBeSelected);
            returnvalue=false;
        }
        return returnvalue;
    }

    /**
     * Click method when having Xpath
     * @param by
     */
    public void click(By by){
        try{
            WebElement Field;
            if (driver.findElements(by).size() ==1){
                Field=driver.findElement(by);
                Actions act=new Actions(driver);
                act.moveToElement(Field).perform();
                wait.until(ExpectedConditions.elementToBeClickable(Field));
                Field.click();
                logMessage("Clicked successfully on Element, Field = "+Field);
            } else{
                logError("Field Not found, Xpath is = "+by);
            }
        }
        catch (Exception e){
            highlight(driver.findElement(by));
            logError("Error occured clicking on "+by+" Description ="+e.getMessage());
        }
    }

    /**
     * Click on link
     * @param LinkText
     */
    public void clickonLink(String LinkText){
        try{
            WebElement Field;
            if (driver.findElements(By.linkText(LinkText)).size() ==1){
                Field=driver.findElement(By.linkText(LinkText));
                wait.until(ExpectedConditions.elementToBeClickable(Field));
                highlight(Field);
                Field.click();
                logMessage("Clicked successfully on Field "+Field +" whose Link with text = "+LinkText);
                waitForBrowserToCompleteProcessing();
            }
            else{
                logError("Link Not found,  Link Text= "+LinkText);
            }
        } catch (Exception e){
            logError("Error occurred clicking on Link with text "+LinkText+" Description ="+e.getMessage());
        }
    }

    /**
     * Create new System Property to save data
     * @param PropertyName
     * @param Value
     */
    public void createNewProperty(String PropertyName,String Value){
        try{
            System.setProperty(PropertyName,Value);
            logMessage("Property with Name = "+PropertyName+" with value  = "+ Value+" is created successfully");
        } catch (Throwable t){
            logError("Property with Name = "+PropertyName+" with value  = "+ Value+" is NOT created successfully, Error ="+t.getMessage());
        }
    }

    /**Wait Till ProcessingImage Is Displayed
     *
     * @param MaxProcessingTime
     * @param by
     * @param Maxwait
     */
    public void waitTillProcessingImageIsDisplayed(long MaxProcessingTime, By by, long Maxwait){
        logMessage("Waiting For Processing to be completed....");
        try{
            wait=new WebDriverWait(driver, MaxProcessingTime);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            wait=new WebDriverWait(driver, Maxwait);
        } catch(Throwable t){
            logError("Processing of image is still going for field xpath = "+by);
        }
    }

    /**
     * Wait Till Processing Icon Is Displayed
     * @param MaxProcessingTime
     * @param by
     * @param Maxwait
     */
    public void waitTillProcessingIconIsDisplayed(long MaxProcessingTime, By by, long Maxwait){
        logMessage("Waiting For Processing to be completed....");
        try{
            wait=new WebDriverWait(driver,MaxProcessingTime );
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            wait=new WebDriverWait(driver, Maxwait);
        } catch(Throwable t){
            logError("Processing of icon is still continuing for field xpath = "+by);
        }
    }

    /**
     * Generate Random numbers
     * @param DigitsOfTheRandomNumber
     * @return
     */
    public  String generateRandomNumber(int DigitsOfTheRandomNumber){
        logMessage("generateRandomNumber function - START");
        String returnValue="";
        try{
            while(returnValue.length() != DigitsOfTheRandomNumber){
                returnValue =String.valueOf((long) (Math.random() * 90000000000000000l)).substring(0, DigitsOfTheRandomNumber);;
            }
        } catch(Throwable t){
            returnValue="Error";
        }
        logMessage("generateRandomNumber function - END");
        return returnValue;
    }

    /**
     * Java Script Checking
     * @param Term
     */
    public void JavascriptChecking(String Term){
        String Page=null;
        JavascriptExecutor js = (JavascriptExecutor)driver;
        Page=js.executeScript("return document.documentElement.outerHTML;").toString();
        while(Page.contains(Term)){
            try {
                Thread.sleep(500);
                Page=js.executeScript("return document.documentElement.outerHTML;").toString();
            } catch (InterruptedException e) {
                logError("Java script checking Fails for Term = " + Term);
            }
        }
    }

    /**
     * Set Value in Disabled field
     * @param element
     * @param ValueToBeSet
     */
    public void setValueInDisabledField(WebElement element,String ValueToBeSet){

        if (element.isDisplayed()){
            wait.until(ExpectedConditions.elementToBeClickable(element));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].removeAttribute('readonly')",element);
            element.clear();
            logMessage("Field "+element+" is cleared successfully");
            Actions action = new Actions(driver);
            action.sendKeys(ValueToBeSet).build().perform();
            String mouseclickScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('click',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject){ arguments[0].fireEvent('onclick');}";
            //JavascriptExecutor js = (JavascriptExecutor) driver;
            //WebElement someElem = driver.findElement(By.xpath(Xpath));
            js.executeScript(mouseclickScript, element);
            logMessage("Value with description "+ValueToBeSet+" is set successfully in Field  = "+element);
        }
        else{
            logError("Field Not found, Field Name = "+element);
        }
    }

    /**
     * Compare value with expression
     * @param ValueToBeCompared
     * @param ExpressionToBeEvaluated
     * @param ReferenceValueForComparision
     * @return boolean
     */
    public boolean comparevaluewithexpression(String ValueToBeCompared,String ExpressionToBeEvaluated,String ReferenceValueForComparision){
        logInfo("comparevaluewithexpression function - START");
        boolean returnvalue=false;
        try{
            if(ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS EQUAL TO")){
                if(ValueToBeCompared.trim().toUpperCase().equalsIgnoreCase(ReferenceValueForComparision)){
                    returnvalue=true;
                }
            } else{
                int IntegerValueToBeCompared=Integer.valueOf(ValueToBeCompared);
                int IntegerReferenceValueForComparision=0;
                if(!ReferenceValueForComparision.contains(",")){
                    IntegerReferenceValueForComparision=Integer.valueOf(ReferenceValueForComparision);
                }
                if(ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS GREATER THAN")){
                    if(IntegerValueToBeCompared > IntegerReferenceValueForComparision)
                        returnvalue=true;
                }
                else if(ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS GREATER THAN EQUAL TO")){
                    if(IntegerValueToBeCompared >= IntegerReferenceValueForComparision)
                        returnvalue=true;
                }
                else if(ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS LESS THAN")){
                    if(IntegerValueToBeCompared < IntegerReferenceValueForComparision)
                        returnvalue=true;
                }
                else if(ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS LESS THAN EQUAL TO")){
                    if(IntegerValueToBeCompared <= IntegerReferenceValueForComparision)
                        returnvalue=true;
                }
                else if(ExpressionToBeEvaluated.trim().equalsIgnoreCase("IS BETWEEN AND")){
                    String[] refvaluesarray=ReferenceValueForComparision.split(",");
                    int refstartvalue=Integer.valueOf(refvaluesarray[0]);
                    int refendvalue=Integer.valueOf(refvaluesarray[1]);
                    if((IntegerValueToBeCompared >= refstartvalue) & (IntegerValueToBeCompared <= refendvalue))
                    {
                        returnvalue=true;
                    }
                }
            }
        } catch(Throwable t){
            logError("Error Occured Inside comparevaluewithexpression function, desc="+t.getMessage());
        }
        logInfo("comparevaluewithexpression function - END");
        return returnvalue;
    }

    /**
     * Move to particular element
     * @param element
     */
    public void moveToElement(WebElement  element){
        try{
            Actions action = new Actions(driver);
            action.moveToElement(element).build().perform();
            logMessage("successfully moved to the element "+element);
        } catch(Throwable t){
            logError("Error Occured Inside moveToElement function, desc="+t.getMessage());
        }
    }





}

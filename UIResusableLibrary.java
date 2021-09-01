package com.boi.grp.utilities;
/// ***** To Be Moved to the Library *****
/*---------------------------------Start <>----------------------------------------
Function Name: 
Argument Name:
Purpose: This class will be moved to Utility library	
Author Name: Cards Automation- 
Create Date: 12-02-2021
Modified Date| Modified By  |Modified purpose 

-----------------------------------End <>---------------------------------------
 */
import static io.qameta.allure.Allure.step;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.ArrayList; 
import org.openqa.selenium.Keys; 
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.DataLibrary;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import junit.framework.Assert;

public class UIResusableLibrary extends  BasePageForAllPlatform{

	public DataLibrary dataLibrary;

	public UIResusableLibrary(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
		dataLibrary=new DataLibrary();
	}
	public UIResusableLibrary() {

	}

	public void ExplicitWait(String eWait, String sPageName){
		try {
			switch (eWait.trim().toUpperCase()){
			case "EXPLICIT":
				Thread.sleep(400*1000);
				IsPageDisplayed(sPageName);
			case "WITHIN":
				Thread.sleep(240*1000);
				IsPageDisplayed(sPageName);				
				logMan.info("****PAGE NAME******" + sPageName);				
			case "EXACT":
				Thread.sleep(300*1000);
				IsPageDisplayed(sPageName);
			}

		} catch (InterruptedException e) {
			logError("Error Occured inside VC_OutOfExplicitWait " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Out of Explicit wait "+e.getMessage());
			appendScreenshotToCucumberReport();
		}

	}

	public void validateErrorMessage(String eErr, String aErr){
		try {
			Assert.assertEquals("Error Message matched successfully ", eErr, aErr);
		} catch (Exception e) {
			logError("Error Occured inside validateErrorMessage " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in validate Error message "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

	public void validateIsEqual(String Expected, String Actual){

		try {
			if(Expected==Actual){
				Allure.step("Actual and Expected matched successfully. Actual Value is : "+ Actual, Status.PASSED);
				logMessage("Actual and Expected matched successfully. Actual Value is : "+Actual+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected matched successfully");
			}		
			else{
				Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
				logMessage("Actual and Expected NOT matched successfully. Actual Value is : "+Expected+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected NOT matched successfully");
			}
		} catch (Exception e) {
			logError("Error Occured inside validateIsEqual " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in validateIsEqual"+e.getMessage());
			Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

	public void validateIsEqual(int Expected, int Actual){

		try {
			if(Expected==Actual){
				Allure.step("Actual and Expected matched successfully. Actual Value is : "+ Actual, Status.PASSED);
				logMessage("Actual and Expected matched successfully. Actual Value is : "+Actual+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected matched successfully");
			}		
			else{
				Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
				logMessage("Actual and Expected NOT matched successfully. Actual Value is : "+Expected+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected NOT matched successfully");
			}
		} catch (Exception e) {
			logError("Error Occured inside validateIsEqual " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in validateIsEqual"+e.getMessage());
			Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

	public void validateIsEqual(boolean Expected, boolean Actual){

		try {
			if(Expected==Actual){
				Allure.step("Actual and Expected matched successfully. Actual Value is : "+ Actual, Status.PASSED);
				logMessage("Actual and Expected matched successfully. Actual Value is : "+Actual+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected matched successfully");
			}		
			else{
				Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
				logMessage("Actual and Expected NOT matched successfully. Actual Value is : "+Expected+" validateIsEqual function.");
				injectMessageToCucumberReport("Actual and Expected NOT matched successfully");
			}
		} catch (Exception e) {
			logError("Error Occured inside validateIsEqual " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in validateIsEqual"+e.getMessage());
			Allure.step("Actual and Expected NOT matched successfully. Expected Value is : "+ Expected, Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

	public void NavigateToPage(String strPagename,int index) throws InterruptedException{

		logInfo("*********Page name**********"+strPagename);
		logInfo("*********INDEX**********"+index);
		logMessage("Navigate to page "+strPagename+ " successfully in NavigateToPage function");
		injectMessageToCucumberReport("Navigate to page "+strPagename+ " successfully");
		Allure.step("Navigate to page "+strPagename+ " Successfully ",Status.PASSED);
	}

	public void Enter3of6DigitPIN(String PinFlag, String Pin){
		logInfo("****PINFLAG***"+PinFlag+Pin);
		try {
			switch (PinFlag.toUpperCase()){
			case "VALIDPIN":
				logInfo("*****Inside Enter3of6DigitPIN****"+Pin);
				EnterPINdetails(Pin);
				break;
			case "INVALIDPIN":						
				EnterPINdetails(dataLibrary.getinValidPin());
				//Validation of error messages
				break;
			}
			logMessage("Enter pin details successfully in Enter3of6DigitPIN function.");
			injectMessageToCucumberReport("Enter pin details successfully");
			Allure.step("Enter pin details Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside Enter3of6DigitPIN" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Enter pin details "+e.getMessage());
			Allure.step("Enter pin details NOT Successful ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

	public void EnterPINdetails(String PIN) throws InterruptedException{
		logInfo("*****INSIDE PIN FUNCTION *****"+ PIN);
		String[] arrPin = PIN.split("");
		String strObject = "";
		Thread.sleep(10*1000);//implicit wait
		//	       waitForPageLoaded();
		////	       waitForJQueryLoad();
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
		logInfo("*****INSIDE PIN FUNCTION BEFORE FOR *****");
		for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
			if (lstPinEnterFieldGrp.get(i).isEnabled()) {
				lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
			}
		}
		appendScreenshotToCucumberReport();
	}

	public void IsPageDisplayed(String pageName){

		try {
			//clickJS(getObject("vc.btnViewCardgoBack"));
			switch (pageName.toUpperCase()){
			case "LOGIN":
				isElementDisplayed(getObject("launch.edtPinEnterFieldGrpSCA"));
				break;
			case "VIEW CARD DETAILS":
				isElementDisplayed(getObject("vc.lblViewCardheader"));
				break;
			case "ORDER REPLACEMENT":
				Assert.assertEquals("Verify elements is displayed ",true,isElementDisplayed(getObject("or.btnOrderNow")));
				break;
			case "RTCC":
				//isElementDisplayed(getObject("rt.lnkCreditAmountSpent"));
				break;
			case "VIEW PIN":
				isElementDisplayed(getObject("vc.lblViewCardheader"));
				break;
			}

			Thread.sleep(2*1000);
			logMessage("Login Page displayed successfully in VC_LoginPageDisplayed function.");
			injectMessageToCucumberReport("Login Page displayed successfully");
			Allure.step("Login Page displayed Successfully ",Status.PASSED);
			//	            Allure.Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VC_LoginPageDisplayed " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Login Page display "+e.getMessage());
			Allure.step("Login Page displayed NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}

//	public void ClickSwipeToAppove(){
//
//		try {
//			//TO DO:  SwipeToAppove code
//			logMessage("Swipe to approve successfully in ClickSwipeToAppove function.");
//			injectMessageToCucumberReport("Swipe to approve successfully");
//			Allure.step("Swipe to approve Successfully ",Status.PASSED);
//		} catch (Exception e) {
//			logError("Error Occured inside ClickSwipeToAppove " +e.getMessage());
//			injectWarningMessageToCucumberReport("Failure in Swipe to approve "+e.getMessage());
//			Allure.step("Swipe to approve NOT Successfully ",Status.FAILED);
//			appendScreenshotToCucumberReport();
//		}				
//
//	}
	public void PushNotification_Acccept() {

        try {
               Thread.sleep(1000);
               logInfo("PushNotifcationScreenShot - Received Push Notifation");
               // waitForElementPresent(getObject("id~com.bankofireland.tcmb:id/acceptSlideButton"),
               // 10);
               // waitForElementToClickable(getObject("id~com.bankofireland.tcmb:id/acceptSlideButton"),
               // 5);
               if (System.getProperty("TYPE").equalsIgnoreCase("APPLICATION")) {
                     context("NATIVE_APP");
                     if (System.getProperty("DEVICEPLATFORM").equalsIgnoreCase("IOS")) {
                            if (isElementDisplayed(getObject(devTypeToGetProperty + "swipeToApprove"), 15)) {
                                   appendScreenshotToCucumberReport();
                                   System.out.println("*****INSIDE IF PUSHNOTIFICATION*****");
                                   JavascriptExecutor js = (JavascriptExecutor) driver;// .getWebDriver();
                                   HashMap<String, String> scrollObject = new HashMap<String, String>();
                                   scrollObject.put("direction", "right");
                                   scrollObject.put("element",
                                                 ((RemoteWebElement) findElementByAccessibilityId("Swipe to approve")).getId());
                                   js.executeScript("mobile: swipe", scrollObject);
                                   
                                   logInfo("PushNotification_Accept - Push Notification Accepted");

                                   changeNativeToWebview();
                            } else {
                                   logMessage(
                                                 "PushNotification_Accept - Accept Push Notification object is not found on the screen");
                            }
                     } else {
                            if (isElementDisplayed(getObject("swipeToApproveAndroid"), 10)) {
                                   appendScreenshotToCucumberReport();
                                   // RemoteWebElement el1 = (RemoteWebElement)
                                   // findElementById("com.bankofireland.tcmb:id/acceptSlideButton");
                                   MobileElement el1 = (MobileElement) findElementById("com.bankofireland.boiinapp:id/acceptSlideButton");
                                   int startX = el1.getLocation().getX();
                                   int startY = el1.getLocation().getY();
                                   int intWidth = el1.getRect().getWidth();
                                   int endX = startX + intWidth;
                                   (new TouchAction(getAppiumDriver())).press(PointOption.point(startX, startY))
                                   .moveTo(PointOption.point(endX, startY)).release().perform();
                                   logInfo("PushNotification_Acccept - Push Notification Accepted");

                                   context("WEBVIEW_com.bankofireland.boiinapp");
                            } else {
                                   logMessage(
                                                 "PushNotification_Accept - Accept Push Notification object is not found on the screen");
                            }
                     }
               } else {
                     // SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
                      waitForPageLoaded();
                     waitForJQueryLoad();
                     Thread.sleep(5000);
                     // sca.PushNotification_Acccept(Mobiledriver);
               }

               /*
               * logMessage("Swipe to approve successfully in ClickSwipeToAppove function."
               * );
               * injectMessageToCucumberReport("Swipe to approve successfully");
               * Allure.step("Swipe to approve Successfully ",Status.PASSED);
               */
        } catch (Exception e) {
               logError("Error Occured inside ClickSwipeToAppove " + e.getMessage());
               injectWarningMessageToCucumberReport("Failure in Swipe to approve " + e.getMessage());
               Allure.step("Swipe to approve NOT Successfully ", Status.FAILED);
               appendScreenshotToCucumberReport();
        }

 }

 /*---------------------------------Start <PushNotification_Decline>----------------------------------------
Function Name: PushNotification_Decline
Argument Name:
Purpose:To validate push notification decline functionality
Author Name: Cards Automation 
Create Date: 17-06-2021
Modified Date| Modified By  |Modified purpose 

-----------------------------------End <ClickSwipeToAppove>---------------------------------------
*/
public void PushNotification_Decline(){
try {
            context("NATIVE_APP");
            if (System.getProperty("DEVICEPLATFORM").equalsIgnoreCase("IOS")) {
                if(isElementDisplayed(getObject(devTypeToGetProperty +"lnkDecline"),15)){
                appendScreenshotToCucumberReport();
                    findElementByAccessibilityId("Double tap to declain").click();
                    logMessage("Push Notification Declined successfully");
                }else{
                   logMessage("Decline Push Notification object is not found on the screen");
                }

            }else{
                if(isElementDisplayed(getObject("btnDeclineAndroid"), 10)) {
                    MobileElement el2 = (MobileElement) findElementById("com.bankofireland.boiinapp:id/closeIcon");
                    el2.click();
                    logMessage("Push Notification Declined");
                    context("WEBVIEW_com.bankofireland.boiinapp");
                } else {
                   logMessage("Decline Push Notification object is not found on the screen");
                }
            }
     } catch (Exception e) {
            logError("Error Occured inside PushNotification_Decline " + e.getMessage());
            injectWarningMessageToCucumberReport("Failure in Push Notification Decline " + e.getMessage());
            Allure.step("Push Nofitication decline NOT Successfully ", Status.FAILED);
            appendScreenshotToCucumberReport();
     }
}


	
	//written by C114322
	/*Function to Click on dropdown and select the value
		*dropDrowField -> to click on drop down element
		*dropDownValues -> to get the list of value available in drop down list
		*value -> value to be selected from list
	 */
	public void clickSelectValueFromDropDown(By dropDownField,By dropDownValues,String value){
		try{
			clickJS(dropDownField);	
			List<WebElement> allOptions = driver.findElements(dropDownValues);
			for (int option=0; option<=allOptions.size()-1 ; option++ ){
				if(allOptions.get(option).getText().toUpperCase().contains(value)){
					allOptions.get(option).click();
					break;
				}
			}
		}
		catch(Exception e) {
			logError("Error Occured inside selectValueFromDropDown " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in select value from drop down "+e.getMessage());
			Allure.step("Select value from drop down NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}
	
	//Written By C113331
	public String GetvalidPIN(String userType) throws InterruptedException{
	       
		 String usData=getLoginData(userType);
			String[] arrUsData = usData.split(":");
			String usName=arrUsData[0];	
			String bDate =arrUsData[1];
			String PIN =arrUsData[2];
			String L4D =arrUsData[3];
	              return PIN;
	       }
	
	/*---------------------------------Start <selectRandomValuefromDropdown>----------------------------------------
    Function Name: enterDate
    Argument : objDOB,strDate
    Purpose: code to select any random value from dropdown takes By object as
    parameter
    Author Name: CAF Automation 
     Create Date: 13-06-2021
    Modified Date| Modified By  |Modified purpose 
      24/06/2021      C113331     Code update 
      -----------------------------------End <selectRandomValuefromDropdown>--------------------------------------- */

    public String selectRandomValuefromDropdown(By locator) {

                    String seltext = null;
                    try {
                                    List<WebElement> allOptions = driver.findElements(locator);
                                    int cnt = allOptions.size();
                                    Random num = new Random();
                                    int iSelect = num.nextInt(cnt);
                                    seltext = allOptions.get(iSelect).getText();
                                    allOptions.get(iSelect).click();
                    } catch (Exception e) {
                                    logError("Error Occured inside selectRandomValuefromDropdown " + e.getMessage());
                                    injectWarningMessageToCucumberReport("Failure in select value from drop down " + e.getMessage());
                                    Allure.step("Select value from drop down NOT Successfully ", Status.FAILED);
                                    appendScreenshotToCucumberReport();

                    }

                    return seltext;
    }
/*---------------------------------Start <getDate>----------------------------------------
    Function Name: getDate
    Argument : adddate
    Purpose: code to add date to current date, if given 0, current date will be returned and 1 
     date will be added to current date.
    Author Name: CAF Automation 
     Create Date: 13-06-2021
    Modified Date| Modified By  |Modified purpose 
      24/06/2021      C113331     Code update 
      -----------------------------------End <getDate>--------------------------------------- */
    public String getDate(int adddate) throws Exception {
                    String newdate = null;
                    try {
                                    String todaydate;
                                    int adddate1 = adddate;
                                    SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    todaydate = dtf.format(date);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(dtf.parse(todaydate));
                                    cal.add(Calendar.DAY_OF_MONTH, adddate1);
                                    newdate = dtf.format(cal.getTime());

                    } catch (Exception e) {
                                    logError("Error Occured in fetching date " + e.getMessage());
                                    injectWarningMessageToCucumberReport("Failure in fetching date " + e.getMessage());
                                    appendScreenshotToCucumberReport();

                    }
                    return newdate;
    }

    /*---------------------------------Start <enterDate>----------------------------------------
    Function Name: enterDate
    Argument : objDOB,strDate
    Purpose: code to pass the date object to be added as integer with today's date 
     Author Name: CAF Automation 
     Create Date: 13-06-2021
    Modified Date| Modified By  |Modified purpose 
      24/06/2021      C113331     Code update 
      -----------------------------------End <enterDate>--------------------------------------- */
    public void enterDate(String objDOB, String strDate) throws Exception {

                    // Splitting the date
                    String strYY = strDate.split("/")[2];
                    String strMON = strDate.split("/")[1];
                    String strDT = strDate.split("/")[0].replaceAll("^0*", "");
                    // if(!devTypeToGetProperty.equals("w.")){
                    // driver.hideKeyboard();
                    // }
                    // Date selection
                    clickJS(getObject(objDOB), "Date Object");
                    waitForPageLoaded();
                    Thread.sleep(1000);
                    selectValueFromDropDown(getObject("launch.lstYearSavingDate"), strYY);
                    selectValueFromDropDown(getObject("launch.lstMonthSavingDate"), strMON);
                    clickJS(getObject("xpath~//a[.='" + strDT + "']"), "Date :" + strDT + " selected");
    }
  
  /*---------------------------------Start <verifyNewlyOpenedTab>----------------------------------------
     * Function : Method to verify the Newly opend tab and its URL
     * Update on    Updated by     Reason
     * 21/07/2021   C113331        
    
    -----------------------------------End <verifyNewlyOpenedTab>--------------------------------------- */
    public void verifyNewlyOpenedTab(String strLinkToVerify) throws Exception {
       try{
        waitForPageLoaded();
        ArrayList<String> arrTabs = new ArrayList<String>(driver.getWindowHandles());
        if (arrTabs.size() > 0) {
            driver.switchTo().window(arrTabs.get(arrTabs.size() - 1));
            waitForPageLoaded();
            waitForJQueryLoad();
            String linkURL = driver.getCurrentUrl();
            //strTextToCompare(strLinkToVerify, linkURL, "new tab with url");
            if(strLinkToVerify.equalsIgnoreCase("https://www.bankofirelanduk.com/rates-and-fees/ni-rates-and-fees/savings-and-deposits-rates/")){
                if(linkURL.startsWith("https://www.bankofirelanduk.com/rates-and-fees")&&(linkURL.contains("ni-rates-and-fees")&&(linkURL.contains("savings-and-deposits-rates")))){
                     Assert.assertTrue("verifyNewlyOpendTab-  Newly opened tab is matching with correct url. Expected ", true);
                }else {
                    Assert.assertTrue("verifyNewlyOpendTab-  Newly opened tab is NOT matching with correct url. Expected ", true);
                }
            }else{
                if (linkURL.contains(strLinkToVerify)) {
                     Assert.assertTrue("verifyNewlyOpendTab-  Newly opened tab is matching with correct url. Expected ", true);
                } else {
                    Assert.assertTrue("verifyNewlyOpendTab-  Newly opened tab is NOT matching with correct url. Expected ", true);
                }
            }

//            if (isMobile) {
//                driver.context("WEBVIEW_com.bankofireland.tcmb");
//                driver.navigate().back();
//            }
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
            driver.switchTo().window(arrTabs.get(0));
            waitForJQueryLoad();
            waitForPageLoaded();

        } else {
             Assert.assertTrue("verifyNewlyOpendTab-  Newly opened tab is NOT matching with correct url. Expected ", true);
        }
       }
       catch (Exception e) {
            logError("Error Occured in validating new tab " + e.getMessage());
            injectWarningMessageToCucumberReport("Failure in opening new tab " + e.getMessage());
            appendScreenshotToCucumberReport();

              }
       
    }


}

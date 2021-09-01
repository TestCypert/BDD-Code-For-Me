package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.List;

/**
 * Created by C966003 on 20/11/2018.
 */

/**
 * Class for storing component groups functionality
 *
 * @author Cognizant
 */
public class WithdrawFunds extends WebDriverHelper {
    public String Referencenumber = "";
    public  String TimeofRequest = "";


    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public WithdrawFunds(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /*General Function:
       Scroll and Click on particular  element using JS
    */
    public void ScrollAndClickJS(String linkToClick) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView(false);", driver.findElement(getObject((linkToClick))));
            Thread.sleep(2000);
            report.updateTestLog("ScrollAndClickJS", "Element To Click", Status_CRAFT.SCREENSHOT);
            JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
            executor.executeScript("arguments[0].click();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(3000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    public void ScrollToVisibleJS(String linkToVisible) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToVisible))));
            Thread.sleep(1000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }



    /**
     * Function to launch Withdraw Funds page
     */
    public void launchWithdrawFunds() throws Exception {
        String strAccount = dataTable.getData("General_Data", "Account_Name");
        String strProductName = dataTable.getData("General_Data", "ProductName");
        waitForPageLoaded();waitForJQueryLoad();
        if (deviceType() == "mw.") {
            if (isElementDisplayed(getObject(deviceType() + "NTW.lblAccountList"), 3)) {
                List<WebElement> accntList = findElements(getObject(deviceType() + "NTW.lblAccountList"));
                int aCount = accntList.size();
                report.updateTestLog("Accounts List", "'Accounts List' is displayed successfully", Status_CRAFT.PASS);
                if(strAccount.contains("~")){
                    strAccount = strAccount.split("~")[1];
                }

                String StrAccountName = "xpath~//span[@class='boi-flex']/span[contains(text(),'" + (strAccount.trim()) + "')]/ancestor::div[contains(@class,'boi-table-tile-with-border')]/descendant::*[contains(text(),'Withdraw funds')]";
                ScrollToVisibleJS(StrAccountName);
                Thread.sleep(1000);
                if (isElementDisplayed(getObject(StrAccountName) ,10 )) {
                    ScrollAndClickJS(StrAccountName);
                    report.updateTestLog("launchWithdrawFunds", " Account :: "+ strAccount +" :: having Product name :: "+ strProductName +" :: clicked successfully.", Status_CRAFT.PASS);
                }else {
                    report.updateTestLog("Accounts List", "'Accounts List' is not displayed successfully", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("Accounts List", "'Accounts List' is not displayed successfully", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject(deviceType() + "NTW.lblAccountList"), 3)) {
                List<WebElement> accntList = findElements(getObject(deviceType() + "NTW.lblAccountList"));
                int aCount = accntList.size();
                report.updateTestLog("Accounts List", "'Accounts List' is displayed successfully", Status_CRAFT.PASS);
                strAccount = strAccount.split("~")[1];
                String StrAccountName = "xpath~//*[contains(@class,'boi')][contains(text(),'" + (strAccount.trim()) + "')]/ancestor::div[contains(@class,'boi-table-tile-with-border')]/descendant::*[contains(text(),'Withdraw funds')]/.";
                ScrollToVisibleJS(StrAccountName);
                Thread.sleep(1000);
                if (isElementDisplayed(getObject(StrAccountName) ,10 )) {
                    ScrollAndClickJS(StrAccountName);
                    report.updateTestLog("launchWithdrawFunds", " Account :: "+ strAccount +" :: having Product name :: "+ strProductName +" :: clicked successfully.", Status_CRAFT.PASS);
                }else {
                    report.updateTestLog("Accounts List", "'Accounts List' is not displayed successfully", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("Accounts List", "'Accounts List' is not displayed successfully", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function to close Account toggle
     */
    public void closeAccountToggle() throws Exception {
        report.updateTestLog("verifyCloseAccountToggle", " Screenshot ", Status_CRAFT.SCREENSHOT);
        String strTogglecloseAccountcheck = dataTable.getData("General_Data", "AccountToggle");
        if (strTogglecloseAccountcheck.equalsIgnoreCase("Yes")) {
            if (isElementDisplayed(getObject("withdrawFunds.CloseAccountToggle"), 3)) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();",
                        driver.findElement(getObject("withdrawFunds.CloseAccountToggle")));
                clickJS(getObject("withdrawFunds.CloseAccountToggle"), "Close Account toggle button");
                Thread.sleep(2000);
            }
            if (isElementDisplayed(getObject("withdrawFunds.BalanceplusInterest"), 5)) {
                report.updateTestLog("verifyCloseAccountToggle", "Balance plus interest amount is displayed ", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyCloseAccountToggle", "Balance plus interest amount is not displayed ", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function to Enter Amount
     */
    public void enterAmount() throws Exception {
        String strAmount = dataTable.getData("General_Data", "DepositAmount");
        if (! isElementDisplayed(getObject("withdrawFunds.BalanceplusInterest"), 4)) {
            if (isElementDisplayed(getObject("withdrawFunds.txtDepositAmount"), 7)) {
                if(deviceType.equalsIgnoreCase("web")){
                    try{sendKeys(getObject("withdrawFunds.txtDepositAmount"), strAmount, "Amount");}
                    catch(Exception e){
                        sendKeysJS(getObject("withdrawFunds.txtDepositAmount"), strAmount, "Amount");}

                }else{
                    sendKeysJS(getObject("withdrawFunds.txtDepositAmount"), strAmount, "Amount");
                }

                waitForPageLoaded();
                waitForJQueryLoad();
            } else {
                report.updateTestLog("Deposit Amount", "'Amount' text box is not displayed", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function to enter mobile Number
     */
    public void enterMobile() throws Exception {
        String strPhone = dataTable.getData("General_Data", "Phone");
        String strphonelabel = "Mobile number";
        // String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
        if (isElementDisplayed(getObject("withdrawFunds.radiobtnMobile"), 3)) {
            clickJS(getObject("withdrawFunds.radiobtnMobile"), "Phone As A Preference");
            Thread.sleep(2000);
            //To verify Mobile Number label (CFPUR-6570)
            String strmobile = getText(getObject("withdrawFunds.mobilenumberlabel"));
            if (isElementDisplayed(getObject("withdrawFunds.mobilenumberlabel"), 3) && (strmobile.equalsIgnoreCase(strphonelabel))) {
                report.updateTestLog("enterMobile", "Mobile field label is correctly displayed as : " + strphonelabel, Status_CRAFT.PASS);
            }
            if (isElementDisplayed(getObject("withdrawFunds.txtPhoneNumber"), 3)) {
                click(getObject("withdrawFunds.txtPhoneNumber"), "Phone number textbox");
                driver.findElement(getObject("withdrawFunds.txtPhoneNumber")).clear();
                sendKeys(getObject("withdrawFunds.txtPhoneNumber"),  strPhone);
                if (isMobile) {
                    sendKeysJS(getObject("withdrawFunds.txtPhoneNumber"), strPhone);
                }
            }
            Thread.sleep(2000);

            //clickJS(getObject("withdrawFunds.helpbox"), "Help box clicked");
            waitForPageLoaded();
            //clickJS(getObject("withdrawFunds.helpbox"), "Help box clicked");
        } else {
            report.updateTestLog("Phone", "'Phone' text box is not displayed", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to enter email
     */
    public void enterEmail() throws Exception {
        String strEmail = dataTable.getData("General_Data", "Email");
        String strConfirmEmail = dataTable.getData("General_Data", "NewEmail");
        String strConfirmEmailLabel = "Confirm email";
        if (isElementDisplayed(getObject("withdrawFunds.radiobtnEmail"), 3)) {
            clickJS(getObject("withdrawFunds.radiobtnEmail"), "Email As A Preference");
            Thread.sleep(2000);

//To verify Confirm Email label (CFPUR-6570)
            String strconfirmMail = getText(getObject("withdrawFunds.emaillabel"));
            if (isElementDisplayed(getObject("withdrawFunds.emaillabel"), 3) && (strconfirmMail.equalsIgnoreCase(strConfirmEmailLabel))) {
                report.updateTestLog("enterMobile", "Confirm Email field label is correctly displayed as : " + strConfirmEmailLabel, Status_CRAFT.PASS);
            }

            sendKeys(getObject("withdrawFunds.txtEmail"), strEmail, "Email Entered");
            Thread.sleep(2000);
            click(getObject("withdrawFunds.txtConfirmEmail"), "Confirm Email textbox Clicked");
            driver.findElement(getObject("withdrawFunds.txtConfirmEmail")).clear();
            sendKeys(getObject("withdrawFunds.txtConfirmEmail"), strConfirmEmail, "Confirm Email Entered");
            Thread.sleep(2000);
            //for changing the focus from email box
            driver.findElement(By.xpath("//span[text()='Important information']")).click();
            report.updateTestLog("Email", "'Email' text box is  displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Email", "'Email' text box is not displayed", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function for Continue button
     */

    public void clickContinue() throws Exception {
        waitForJQueryLoad();waitForPageLoaded();
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();",
                driver.findElement(getObject(deviceType()+ "withdrawFunds.btnContinue")));
        waitForElementToClickable(getObject(deviceType()+ "withdrawFunds.btnContinue"), 10);
        if(deviceType.equalsIgnoreCase("web")){
            click(getObject(deviceType()+ "withdrawFunds.btnContinue"), "Continue ");
        }else{
            clickJS(getObject(deviceType()+ "withdrawFunds.btnContinue"), "Continue ");
        }

        waitForPageLoaded();
    }

    /**
     * Function for Review
     */
    public void withdrawFundsReview() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject("withdrawFunds.txtReview"),5)){
            homePage.verifyElementDetails("withdrawFunds.txtReview");
        }else {
            homePage.verifyElementDetails("xpath~//div[@class='ecDIB  boi-full-width boi-mt-20 boi-tg__font--regular boi-tg__size--default  ']");
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
        if(isMobile) {
            driver.hideKeyboard();
        }
        //To handle the SCA pin page
        if (isElementDisplayed(getObject("launch.btnConfirm"), 2)) {
            clickJS(getObject("launch.btnConfirm"), "Confirm button");
        } else if (isElementDisplayed(getObject("launch.btnLogIn"), 2)) {
            clickJS(getObject("launch.btnLogIn"), "Login button");
        } else if (isElementDisplayed(getObject("launch.btnContinue"), 10)) {
            clickJS(getObject("launch.btnContinue"), "Continue button");
        }

    }


    /**
     * Function for Confirm Button
     */
    public void clickConfirm() throws Exception {
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            clickJS(getObject("AddPayee.Confirm"), "Confirm");
        } else if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "withdrawFunds.btnConfirm"), 7)) {
                click(getObject(deviceType() + "withdrawFunds.btnConfirm"), "Confirm");
                Thread.sleep(2000);
            }
        }

    }

/*
    public boolean tapOnElementWithCoordinates(AndroidElement elem){
        AppiumDriver dr= (AppiumDriver) driver.getWebDriver();
              try{

            if(elem==null){
                return false;
            }
            int windowHeight = driver.manage().window().getSize().height;
            int windowWidth = driver.manage().window().getSize().width;
            System.out.println("Window Height : "+windowHeight);
            System.out.println("Window Width : "+windowWidth);

            int height = elem.getSize().height;
            int width = elem.getSize().width;
            int x = elem.getLocation().x;
            int y = elem.getLocation().y;
            System.out.println("Element Height : "+height);
            System.out.println("Element Width : "+width);
            int tapX = x+(width/2);
            int tapY = y+(height/2);

            if(tapX > windowWidth){
                System.out.println("Element displaced to the right.Correcting.");
                tapX = tapX - windowWidth;
            }
            if(tapX < 0){
                System.out.println("Element displaced to the left.Correcting.");
                tapX = tapX + windowWidth;
            }
            if(tapY > windowHeight){
                System.out.println("Element displaced to the bottom.Correcting.");
                tapY = tapY - windowHeight;
            }
            if(tapY < 0){
                System.out.println("Element displaced to the top.Correcting.");
                tapY = tapY + windowHeight;
            }
            System.out.println("Element Position x : "+tapX);
            System.out.println("Element Position y : "+tapY);
                  (new TouchAction(driver.getAppiumDriver())).tap(tapX,tapY).release().perform();


            //Logger.printDebug("Tap Executed using - TapOnElementWithCoordinates...");
            return true;
        }
        catch(Exception e){
            Assert.fail("DEBUG: TapOnElementWithCoordinates Failed for : "+elem,e);
        }
        return false;
    }
*/

    /*public void PushNotification_Acccept()throws Exception{

        if(isElementDisplayed(getObject("xpath~//span[text()='Confirm']"),10)){
            MobileElement el1 = (MobileElement) driver.findElementByXPath("//span[text()='Confirm']");
            int startX = el1.getLocation().getX();
            int startY = el1.getLocation().getY();
            int intWidth = el1.getRect().getWidth();
            int endX = startX + intWidth;
            (new TouchAction(driver.getAppiumDriver())).press(PointOption.point(startX,startY)).moveTo(PointOption.point(endX,startY)).release().perform();
            //press(PointOption.point(startX,startY)).
                    //.moveTo(PointOption.point(endX,startY)).release().perform();
            report.updateTestLog("PushNotification_Acccept", "Push Notification Accepted",Status_CRAFT.PASS);
        }else{
            report.updateTestLog("PushNotification_Acccept", "Accept Push Notification object is not found on the screen",Status_CRAFT.FAIL);
        }

    }
*/






    /**
     * Function to capture tool tip
     */
    public void captureToolTip() throws Exception {
        String strTooltipData = dataTable.getData("General_Data", "ToolTipText");
        String[] arrTooltipData = strTooltipData.split("::");

        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (arrTooltipData[0].contains("iconAvailableFunds")) {
                clickJS(getObject(deviceType() + arrTooltipData[0]), "ToolTip icon");
                report.updateTestLog("captureToolTip", "'ToolTip'  is displayed", Status_CRAFT.PASS);
                /*if (isElementDisplayed(getObject(deviceType() + arrTooltipData[1]), 3)) {
                    fetchTextToCompare(getObject(deviceType() + arrTooltipData[1]), arrTooltipData[2], "ToolTip Text");
                    report.updateTestLog("captureToolTip", "'ToolTip' text is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("captureToolTip", "'ToolTip' text is not displayed", Status_CRAFT.FAIL);
                }*/
            } else if (arrTooltipData[0].contains("iconMethodOfContact")) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(deviceType() + arrTooltipData[0])));
                clickJS(getObject(deviceType() + arrTooltipData[0]), "ToolTip icon");
/*
             if (isElementDisplayed(getObject(deviceType()+arrTooltipData[1]),3)){
                 fetchTextToCompare(getObject(deviceType()+arrTooltipData[1]),arrTooltipData[2],"ToolTip Text");
             } else {
                 report.updateTestLog("captureToolTip", "'ToolTip' text is not displayed", Status_CRAFT.FAIL);
             }
*/
            }
        } else if (deviceType.equalsIgnoreCase("Web")) {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",driver.findElement(getObject(deviceType() + arrTooltipData[0])));
            Thread.sleep(3000);
            click(getObject(deviceType() + arrTooltipData[0]), "ToolTip icon");
            WebElement wb = driver.findElement(getObject(deviceType() + arrTooltipData[1]));
            Actions ab = new Actions((WebDriver) driver.getWebDriver());
            ab.moveToElement(wb);
            if (isElementDisplayed(getObject(deviceType() + arrTooltipData[1]), 3)) {
                fetchTextToCompare(getObject(deviceType() + arrTooltipData[1]), arrTooltipData[2], "ToolTip Text");
            } else {
                report.updateTestLog("captureToolTip", "'ToolTip' text is not displayed", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function to capture error messages for withdraw finds screen
     */
    public void errorMessagesWithdrawFunds() throws Exception {
        if (isElementDisplayed(getObject("NTW.lblEnterDetails"), 3)) {
            //** To capture error messages on blank field click
            clickContinue();
            String strErrAmount = getText(getObject("NTW.errAmount"));
            String strErrTransTo = getText(getObject("NTW.errTransferTo"));
            String strErrMethodOfContc = getText(getObject("NTW.errMethodOfContact"));
            if (isElementDisplayed(getObject("NTW.errAmount"), 3)) {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrAmount + "'Error Message'is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrAmount + "'Error Message'is not displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject(deviceType() + "NTW.iconTransferTo"), 3)) {
                if (isElementDisplayed(getObject("NTW.errTransferTo"), 3)) {
                    report.updateTestLog("errorMessagesWithdrawFunds", strErrTransTo + "'Error Message'is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("errorMessagesWithdrawFunds", strErrTransTo + "'Error Message'is not displayed", Status_CRAFT.FAIL);
                }
            }
            if (isElementDisplayed(getObject("NTW.errMethodOfContact"), 3)) {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrMethodOfContc + "'Error Message'is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrMethodOfContc + "'Error Message'is not displayed", Status_CRAFT.FAIL);
            }

            //** To capture error messages on blank mobile field **//
            String strInvalChar = "@#@%%$#%^^&$";
            String strInvalAmount = "7545465758548";
            String strMinMobileNo = "12";
            Thread.sleep(2000);
            clickJS(getObject("withdrawFunds.radiobtnMobile"), "Phone As A Preference");
            clickContinue();
            String strErrMobileNo = getText(getObject("NTW.errMobileNo."));
            if (isElementDisplayed(getObject("NTW.errMobileNo."), 3)) {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrMobileNo + "'Error Message'is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrMobileNo + "'Error Message'is not displayed", Status_CRAFT.FAIL);
            }
            //** To capture error messages on invalid mobile no.
            sendKeys(getObject("withdrawFunds.txtPhoneNumber"), strInvalChar, "Phone Number Entered");
            clickContinue();
            String strErrInvalMobileNo = getText(getObject("NTW.errMobileNo."));
            if (isElementDisplayed(getObject("NTW.errMobileNo."), 3)) {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrInvalMobileNo + "'Error Message'is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrInvalMobileNo + "'Error Message'is not displayed", Status_CRAFT.FAIL);
            }

            //** To capture error message on min mobile length
            sendKeys(getObject("withdrawFunds.txtPhoneNumber"), strMinMobileNo, "Phone Number Entered");
            clickContinue();
            String strErrMinMobileNo = getText(getObject("NTW.errMobileNo."));
            if (isElementDisplayed(getObject("NTW.errMobileNo."), 3)) {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrMinMobileNo + "'Error Message'is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMessagesWithdrawFunds", "Error Message for min mobile length is not displayed", Status_CRAFT.FAIL);
            }

            //** To capture error messages on blank email field
            clickJS(getObject("withdrawFunds.radiobtnEmail"), "Email As A Preference");
            Thread.sleep(3000);
            clickContinue();
            String strErrEmail = getText(getObject("NTW.errEmail"));
            String strErrConfmEmail = getText(getObject("NTW.errConfirmEmail"));
            if (isElementDisplayed(getObject("NTW.errEmail"), 3)) {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrEmail + "'Error Message'is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrEmail + "'Error Message'is not displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("NTW.errConfirmEmail"), 3)) {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrConfmEmail + "'Error Message'is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrConfmEmail + "'Error Message'is not displayed", Status_CRAFT.FAIL);
            }

            //** To capture error messages on invalid Email address
            sendKeys(getObject("withdrawFunds.txtEmail"), strInvalChar, "Email Entered");
            click(getObject("withdrawFunds.txtConfirmEmail"), "Confirm Email textbox Clicked");
            sendKeys(getObject("withdrawFunds.txtConfirmEmail"), strInvalChar, "Confirm Email Entered");
            clickContinue();
            String strErrInvalEmail = getText(getObject("NTW.errEmail"));
            String strErrInvalConfmEmail = getText(getObject("NTW.errConfirmEmail"));
            if (isElementDisplayed(getObject("NTW.errEmail"), 3)) {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrInvalEmail + "'Error Message'is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrInvalEmail + "'Error Message'is not displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("NTW.errConfirmEmail"), 3)) {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrInvalConfmEmail + "'Error Message'is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrInvalConfmEmail + "'Error Message'is not displayed", Status_CRAFT.FAIL);
            }

            //** To capture error messages on invalid amount
            sendKeys(getObject("withdrawFunds.txtDepositAmount"), strInvalAmount, "Amount to withdraw");
            if (isElementDisplayed(getObject(deviceType() + "NTW.iconTransferTo"), 3)) {
                transferToDropDown();
            }
            Thread.sleep(2000);
            clickJS(getObject("withdrawFunds.radiobtnMobile"), "Phone As A Preference");
            if(deviceType.equalsIgnoreCase("Web")){
                sendKeys(getObject("withdrawFunds.txtPhoneNumber"), "089584587458", "Phone Number Entered");
            }else{
                sendKeysJS(getObject("withdrawFunds.txtPhoneNumber"), "089584587458", "Phone Number Entered");
            }

            //transferToDropDown();
            clickContinue();
            Thread.sleep(3000);
            //** To capture error message on min amount length
            String strMinAmount = "0.001";
            if(deviceType.equalsIgnoreCase("web")){
                try{sendKeys(getObject("withdrawFunds.txtDepositAmount"), strMinAmount, "Amount");}
                catch(Exception e){
                    sendKeysJS(getObject("withdrawFunds.txtDepositAmount"), strMinAmount, "Amount");
                }
            }else{
                sendKeysJS(getObject("withdrawFunds.txtDepositAmount"), strMinAmount, "Amount");
            }

            clickContinue();
            String strErrMinAmount = getText(getObject("NTW.errAmount"));
            if (isElementDisplayed(getObject("NTW.errAmount"), 3)) {
                report.updateTestLog("errorMessagesWithdrawFunds", strErrMinAmount + "'Error Message'is displayed for Min Amount", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("errorMessagesWithdrawFunds", "Error for Min Amount is not displayed", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function for Transfer to dropDown
     */
    public void transferToDropDown() throws Exception {
        String strAccounts = dataTable.getData("General_Data", "Account_Type");
        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("NTW.lstTransferTo"), 5)) {
                clickJS(getObject("NTW.lstTransferTo"), "TransferToDropDown");
                Thread.sleep(2000);
                driver.findElement(By.xpath("//button[contains(text(),'Please select')]/following-sibling::ul[contains(@class,'list')]/li[contains(text(),'"+ strAccounts.split("~")[1] +"')]")).click();
                Thread.sleep(2000);
            }else if(isElementDisplayed(getObject("NTW.txtTrandferToAccInfo"), 5)){
                ScrollToVisibleJS("NTW.txtTrandferToAccInfo");
                report.updateTestLog("transferToDropDown", "As Expected Account Dropdown is not present as amount will get deposited to :: 'The account you nominated originally, as per terms and conditions.'", Status_CRAFT.PASS);

            }else {
                report.updateTestLog("transferToDropDown", "No dropdown of account is not selected", Status_CRAFT.FAIL);
            }
        } else if (deviceType().equals("mw.")) {

            if (isElementDisplayed(getObject("NTW.lstTransferTo"), 5)) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();",
                        driver.findElement(getObject("NTW.lstTransferTo")));
                clickJS(getObject("NTW.lstTransferTo"), "TransferToDropDown");
                Thread.sleep(2000);
                clickJS(getObject("xpath~//*[@id='C5__QUE_F126F8ADA2F5A283697131_list_aria']/li[2]"), "Selected Account is : '" + strAccounts + "'");
                report.updateTestLog("transferToDropDown", "Selected Account is : '" + strAccounts + "'", Status_CRAFT.PASS);
                Thread.sleep(2000);

            } else if(isElementDisplayed(getObject("xpath~//button[@id='C5__QUE_F126F8ADA2F5A283697131_widgetARIA']"), 5)){

                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();",
                        driver.findElement(getObject("xpath~//button[@id='C5__QUE_F126F8ADA2F5A283697131_widgetARIA']")));
                clickJS(getObject("xpath~//button[@id='C5__QUE_F126F8ADA2F5A283697131_widgetARIA']"), "TransferToDropDown");
                Thread.sleep(2000);
                clickJS(getObject("xpath~//*[@id='C5__QUE_F126F8ADA2F5A283697131_list_aria']/li[4]"), "Selected Account is : '" + strAccounts + "'");
                report.updateTestLog("transferToDropDown", "Selected Account is : '" + strAccounts + "'in the second iteration", Status_CRAFT.PASS);
                Thread.sleep(2000);
            } else if(isElementDisplayed(getObject("NTW.txtTrandferToAccInfo"), 5)){
                ScrollToVisibleJS("NTW.txtTrandferToAccInfo");
                report.updateTestLog("transferToDropDown", "As Expected Account Dropdown is not present as amount will get deposited to :: 'The account you nominated originally, as per terms and conditions.'", Status_CRAFT.PASS);

            } else {
                report.updateTestLog("transferToDropDown", "No dropdown of account is not selected", Status_CRAFT.FAIL);
            }

        } else {
            report.updateTestLog("transferToDropDown", "No account is not displayed", Status_CRAFT.FAIL);
        }

        enterAmount();
    }

    /**
     * Function for Request Sent
     */
    public void requestSentValidation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        //only for desktop
        homePage.verifyElementDetails("withdrawFunds.RequestSent");
        //only for desktop
        if(deviceType().equals("w.")) {
            if (isElementDisplayed(getObject("withdrawFunds.lnkPrint"), 3)) {
                String SelectPrint = getText(getObject("withdrawFunds.lnkPrint"));
                report.updateTestLog("Print link", SelectPrint + " is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Print link", " is not displayed successfully", Status_CRAFT.FAIL);
            }
        }
        if (isMobile) {
            clickJS(getObject(deviceType() + "withdrawFunds.btnBacktoHome"), "Back to Home ");
        }
        else{
            click(getObject(deviceType() + "withdrawFunds.btnBacktoHome"), "Back to Home");
            waitForJQueryLoad();waitForPageLoaded();
        }
    }


    public void withdrawfundsSuccessScreen() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String SuccessMessage = dataTable.getData("General_Data", "TabName");
        if (isElementDisplayed(getObject("withdrawFunds.successmsg"), 3)) {
            String SuccessMsg = getText(getObject("withdrawFunds.successmsg"));
            if (SuccessMsg.equalsIgnoreCase(SuccessMessage)) {
                report.updateTestLog("VerifySuccessMessage", "Success message is displayed correctly as : " + SuccessMsg, Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("withdrawFunds.successmsgtext"),5)){
                    String SuccessMsgTxt = getText(getObject("withdrawFunds.successmsgtext"));
                    String SuccessMsgtxtUi = "Thank you. We will notify you when your request has been completed. Details of this request are saved in your sent messages in Services.";
                    if (SuccessMsgTxt.equalsIgnoreCase(SuccessMsgtxtUi)) {
                        report.updateTestLog("VerifySuccessMessageText", "Success message text is displayed correctly as : " + SuccessMsgTxt, Status_CRAFT.PASS);
                    }else{
                        report.updateTestLog("VerifySuccessMessageText", "Success message text is not displayed correctly", Status_CRAFT.FAIL);
                    }
                }else {
                    report.updateTestLog("VerifySuccessMessageText", "Success message is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("VerifySuccessMessage", "Success message is not displayed correctly", Status_CRAFT.FAIL);
            }
        }

        String ImpInformMessage = dataTable.getData("General_Data", "VerifyContent").trim();
        if (isElementDisplayed(getObject("withdrawFunds.importantinformationmsg"), 3)) {
            String ImpInformMsg = getText(getObject("withdrawFunds.importantinformationmsg"));
            String ImpinforTextUi = "If you need to cancel this withdrawal request you can only do so by calling us direct. We're available on 1890 365 254 or 01 404 4000, 8am-6pm Mon-Fri.";

            String ImpInfoText = getText(getObject("xpath~//p[@class='boi_para']"));
            if (ImpinforTextUi.equalsIgnoreCase(ImpInfoText)) {
                report.updateTestLog("VerifyInfoMessage", "Important information message is displayed correctly as : " + ImpInfoText, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyInfoMessage", "Important information message is displayed correctly", Status_CRAFT.FAIL);
            }
        }else {
            report.updateTestLog("VerifyInfoMessage", "Important information Label is not displayed correctly", Status_CRAFT.FAIL);
        }

        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("withdrawFunds.lnkPrint"), 3)) {
                String SelectPrint = getText(getObject("withdrawFunds.lnkPrint"));
                report.updateTestLog("Print link", SelectPrint + " is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Print link", " is not displayed successfully", Status_CRAFT.FAIL);
            }
        }

        if (deviceType.equalsIgnoreCase("MobileWeb")) {

            isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Reference: ')]"), "Reference Number", "Reference number is dispalyed", 5);
            Referencenumber = getText(getObject("xpath~//div[contains(text(),'Reference: ')]"), 10);
//            String FinalRef[] = Referencenumber.split(":");
//            String FinalRef1 = FinalRef[1].trim();

            isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Time of request: ')]"), "Time of Request", "Time of request is dispalyed", 5);
            TimeofRequest = getText(getObject("xpath~//div[contains(text(),'Time of request: ')]"), 5);

            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject(deviceType() + "withdrawFunds.btnBacktoHome")));
            clickJS(getObject(deviceType() + "withdrawFunds.btnBacktoHome"), "Back to Home ");
        } else if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "withdrawFunds.btnBacktoHome"), 7)){
                isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Reference: ')]"), "Reference Number", "Reference number is dispalyed", 5);
                Referencenumber = getText(getObject("xpath~//div[contains(text(),'Reference: ')]"), 10);

                isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Time of request: ')]"), "Time of Request", "Time of request is dispalyed", 5);
                TimeofRequest = getText(getObject("xpath~//div[contains(text(),'Time of request: ')]"), 5);
                click(getObject(deviceType() + "withdrawFunds.btnBacktoHome"), "Back to Home");
                Thread.sleep(1000);

//                if (isElementDisplayed(getObject(deviceType() + "withdrawFunds.btnBacktoHome"), 7)) {
//                    click(getObject(deviceType() + "withdrawFunds.btnBacktoHome"), "Back to Home");
//                    Thread.sleep(2000);
//                }

            }
        }
    }




    public void clickMessage() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "SL.tabMessageValidation"), 10)) {
            clickJS(getObject(deviceType() + "SL.tabMessageValidation"), "Message tab is clicked");
            report.updateTestLog("Services Lozenges", "Message tab is clicked ", Status_CRAFT.PASS);
            waitForPageLoaded();
        } else {
            report.updateTestLog("Services Lozenges", "Message tab is not clicked ", Status_CRAFT.FAIL);
        }
    }

    public void clickShowmorecomplete() throws Exception{
        if (isElementDisplayed(getObject("SL.showmore"),5)) {
            report.updateTestLog("verifyShowMoreButton", "Show more button is displayed", Status_CRAFT.PASS);
            do {
                clickJS(getObject("SL.showmore"), "Show more button");
                waitForPageLoaded();
//                break;
//                if (!isElementDisplayed(getObject(deviceType()+"SL.showmore"), 3)) {
//                    report.updateTestLog("verifyCompletedSection", "Completed Section is displayed", Status_CRAFT.PASS);
//
//                }
            } while (isElementDisplayed(getObject("SL.showmore"), 3));
        }}

    public void clickSent() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "SL.senttab"), 10)) {
            clickJS(getObject(deviceType() + "SL.senttab"), "Sent Tab");
            waitForPageLoaded();
            report.updateTestLog("Services Lozenges", "Sent tab is clicked ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Services Lozenges", "Sent tab is not clicked ", Status_CRAFT.FAIL);
        }
    }


    public void sentmessagevalidation() throws Exception {
        boolean blnClicked;
        String  s1 = this.Referencenumber;
        String  s2 = this.TimeofRequest;
        String  refMsg=null;
        String timeMsg=null;

        //Referencenumber = getText(getObject("xpath~//div[contains(text(),'Reference: ')]"),10);
        String FinalRef[] = s1.split(":");
        String FinalRef1 = FinalRef[1].trim();
        //TimeofRequest = getText(getObject("xpath~//div[contains(text(),'Time of request: ')]"),5);
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        clickMessage();
        clickSent();
        clickShowmorecomplete();
        List<WebElement> Sentmailslist = driver.findElements(By.xpath("//span[contains(@id,'C5__QUE_781A38DFD311C2FD268777_R')]"));
        for(int i=0; i<Sentmailslist.size();i++){
            String text = Sentmailslist.get(i).getText();
            if (text.equals(FinalRef1)){
                ///////////////

                try {

                    //WebElement element = driver.findElement(locator);
                    JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                    executor.executeScript("arguments[0].click();", Sentmailslist.get(i));
                } catch (UnreachableBrowserException e) {
                    e.printStackTrace();
                } catch (StaleElementReferenceException e) {
                    e.printStackTrace();
                }
                blnClicked = true;
                Thread.sleep(2000);



                /////////////////
                // Sentmailslist.get(i).click();
                refMsg=getText(getObject("xpath~//span[@id='C5__QUE_09D302FA0AE6D1974460703']")).substring(4,18);
                if(refMsg.equals(FinalRef1)){
                    System.out.print("Reference number matched");
                }
                else{
                    System.out.print("Reference numeber not matched");
                }
                break;
            }

        }

    }

    public void withdrawfundsMessage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);

        String TransferMsg = dataTable.getData("General_Data", "TabName");
        if (isElementDisplayed(getObject("withdrawFunds.transferToText"), 3)) {
            String TransferToMessage = getText(getObject("withdrawFunds.transferToText"));
            if (TransferMsg.equalsIgnoreCase(TransferToMessage)) {
                report.updateTestLog("VerifySuccessMessage", "Transfer to message is displayed correctly", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifySuccessMessage", "Transfer to message is displayed correctly", Status_CRAFT.FAIL);
            }
        }

        String ImpInformMessage = dataTable.getData("General_Data", "VerifyContent");
        if (isElementDisplayed(getObject("withdrawFunds.infomessageText"), 3)) {
            String ImpInformMsg = getText(getObject("withdrawFunds.infomessageText"));
            if (ImpInformMsg.equalsIgnoreCase(ImpInformMessage)) {
                report.updateTestLog("VerifyInfoMessage", "Important information message is displayed correctly", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyInfoMessage", "Important information message is not displayed correctly", Status_CRAFT.FAIL);
            }
        }
    }


    public void enterBlankMobile() throws Exception {
        String strPhone = "";
        // String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
        if (isElementDisplayed(getObject("withdrawFunds.radiobtnMobile"), 3)) {
            clickJS(getObject("withdrawFunds.radiobtnMobile"), "Phone As A Preference");
            sendKeys(getObject("withdrawFunds.txtPhoneNumber"), strPhone, "Phone Number Entered");
        } else {
            report.updateTestLog("Phone", "'Phone' text box is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void VerifyErrorBlankMobile() throws Exception {
        Thread.sleep(3000);
        String ExpectedBlankMobileError = "Please enter a valid Mobile Phone Number";
        String ActualBlankMobileError = getText(getObject("NTW.BlankMobileDigitError"));
        // String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
        if (ExpectedBlankMobileError.equalsIgnoreCase(ActualBlankMobileError)) {
            report.updateTestLog("VerifyInfoMessage", "Error message '" + ExpectedBlankMobileError + "' is displayed correctly", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyInfoMessage", "Error message is not displayed correctly", Status_CRAFT.FAIL);
        }
    }

    public void enterBlankEmail() throws Exception {

        String strEmail = "";
        if (isElementDisplayed(getObject("withdrawFunds.radiobtnEmail"), 3)) {
            clickJS(getObject("withdrawFunds.radiobtnEmail"), "Email As A Preference");
            sendKeys(getObject("withdrawFunds.txtEmail"), strEmail, "Email Entered");
            clickJS(getObject("withdrawFunds.txtConfirmEmail"), "Confirm Email textbox Clicked");
            sendKeys(getObject("withdrawFunds.txtConfirmEmail"), strEmail, "Confirm Email Entered");
            report.updateTestLog("Email", "'Email' text box is  displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Email", "'Email' text box is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void VerifyErrorForEmail() throws Exception {
        Thread.sleep(3000);
        String ExpectedBlankEmailError = "Please enter a valid Email address";
        String ActualEmailError;
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject("NTW.EmailError")));
            ActualEmailError = getText(getObject("NTW.EmailError"));


            if (ExpectedBlankEmailError.equalsIgnoreCase(ActualEmailError)) {
                report.updateTestLog("VerifyInfoMessage", "Error message '" + ExpectedBlankEmailError + "' is displayed correctly for Email field", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyInfoMessage", "Error message is not displayed correctly for Email field", Status_CRAFT.FAIL);
            }

        }

        if (deviceType.equalsIgnoreCase("Web")) {
            ActualEmailError = getText(getObject("NTW.EmailError"));


            if (ExpectedBlankEmailError.equalsIgnoreCase(ActualEmailError)) {
                report.updateTestLog("VerifyInfoMessage", "Error message '" + ExpectedBlankEmailError + "' is displayed correctly for Email field", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyInfoMessage", "Error message is not displayed correctly for Email field", Status_CRAFT.FAIL);
            }


        }

    }


    public void VerifyErrorForConfirmEmail() throws Exception {
        Thread.sleep(3000);
        String ExpectedBlankEmailError = "Please enter a valid Email address";
        String ActualEmailError;
        String ActualConfirmEmailError;
        if (deviceType.equalsIgnoreCase("MobileWeb")) {

            JavascriptExecutor js1 = (JavascriptExecutor) driver.getWebDriver();
            js1.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject("NTW.ConfirmEmailError")));
            ActualConfirmEmailError = getText(getObject("NTW.ConfirmEmailError"));

            if (ExpectedBlankEmailError.equalsIgnoreCase(ActualConfirmEmailError)) {
                report.updateTestLog("VerifyInfoMessage", "Error message '" + ExpectedBlankEmailError + "' is displayed correctly for confirm Email field", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyInfoMessage", "Error message is not displayed correctly for confirm Email field", Status_CRAFT.FAIL);
            }

        }

        if (deviceType.equalsIgnoreCase("Web")) {
            ActualConfirmEmailError = getText(getObject("NTW.ConfirmEmailError"));
            if (ExpectedBlankEmailError.equalsIgnoreCase(ActualConfirmEmailError)) {
                report.updateTestLog("VerifyInfoMessage", "Error message '" + ExpectedBlankEmailError + "' is displayed correctly for confirm Email field", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyInfoMessage", "Error message is not displayed correctly for confirm Email field", Status_CRAFT.FAIL);
            }
        }
        // String strContactPreference = dataTable.getData("General_Data", "ContactPreference");

    }

    public void enterInvalidMobile() throws Exception {
        String strPhone = dataTable.getData("General_Data", "Invalid_ContactNo");
        // String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
        if (isElementDisplayed(getObject("withdrawFunds.radiobtnMobile"), 3)) {
            clickJS(getObject("withdrawFunds.radiobtnMobile"), "Phone As A Preference");
            waitForElementToClickable(getObject("withdrawFunds.txtPhoneNumber"),5);
            sendKeys(getObject("withdrawFunds.txtPhoneNumber"), strPhone, "Phone Number Entered");
        } else {
            report.updateTestLog("Phone", "'Phone' text box is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void VerifyErrorInvalidMobile() throws Exception {
        String ExpectedInvalidMobileError = "The mobile phone number you have entered is too short. Please re-enter.";
        String ActualInvalidMobileError = getText(getObject("NTW.InvalidMobileDigitError"));
        if (ExpectedInvalidMobileError.equalsIgnoreCase(ActualInvalidMobileError)) {
            report.updateTestLog("VerifyInfoMessage", "Error message '" + ExpectedInvalidMobileError + "' is displayed correctly", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyInfoMessage", "Error message is not displayed correctly", Status_CRAFT.FAIL);
        }
    }

    public void enterInvalidEmail() throws Exception {
        String strEmail = dataTable.getData("General_Data", "InvalidEmail");
        String strConfirmEmail = dataTable.getData("General_Data", "InvalidEmail");
        if (isElementDisplayed(getObject("withdrawFunds.radiobtnEmail"), 3)) {
            clickJS(getObject("withdrawFunds.radiobtnEmail"), "Email As A Preference");
            sendKeys(getObject("withdrawFunds.txtEmail"), strEmail, "Email Entered");
            clickJS(getObject("withdrawFunds.txtConfirmEmail"), "Confirm Email textbox Clicked");
            sendKeys(getObject("withdrawFunds.txtConfirmEmail"), strConfirmEmail, "Confirm Email Entered");
            report.updateTestLog("Email", "'Email' text box is  displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Email", "'Email' text box is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void VerifyErrorInvalidEmail() throws Exception {
        Thread.sleep(3000);
        String ExpectedInvalidEmailError = "";
        String ActualInvalidEmailError = getText(getObject("NTW.InvalidEmailError"));
        // String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
        if (ExpectedInvalidEmailError.equalsIgnoreCase(ActualInvalidEmailError)) {
            report.updateTestLog("VerifyInfoMessage", "Error message '" + ExpectedInvalidEmailError + "' is displayed correctly", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyInfoMessage", "Error message is not displayed correctly", Status_CRAFT.FAIL);
        }
    }

    public void VerifyErrorPIN() throws Exception {
        String strBlankPINError = "Make sure you have entered all 3 digits.";
        String strInvalidPINError="Your PIN is incorrect. Please check your details and try again.";
        String BlankPINError = getText(getObject(deviceType()+"NTW.errBlankPin"));
        if (strBlankPINError.equalsIgnoreCase(BlankPINError)||strBlankPINError.equalsIgnoreCase(strInvalidPINError)) {
            report.updateTestLog("VerifyInfoMessage", "Error message '" + strBlankPINError + "' is displayed correctly", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyInfoMessage", "Error message is not displayed correctly", Status_CRAFT.FAIL);
        }

    }

    public void GObackContinueButton() throws Exception {
        clickJS(getObject("PIN.GoBack"),"Go back");
        Thread.sleep(2000);
        clickJS(getObject(deviceType() + "withdrawFunds.btnContinue"), "Continue");
    }

    /**
     * Function for Invalid Pin Validation
     */
    public void enterWithdrawFundsInvalidPIN() throws Exception {
        String[] arrPin = dataTable.getData("General_Data", "Invalid_PIN").split("");
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("withdrawFunds.btnPin"));
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
    }

    /**
     * Function for validate details from page review and request sent from Withdraw funds story
     */
    public void details_NTW() throws Exception {
        if (scriptHelper.commonData.iterationFlag != true) {
            withdrawFundsReview();
            scriptHelper.commonData.iterationFlag = true;
        } else {
            requestSentValidation();
        }
    }

    public void errInvalidPIN() throws Exception {
        clickConfirm();
        String InvalidPINError = getText(getObject( deviceType()+"NTW.errBlankPin"));
        if (isElementDisplayed(getObject(deviceType()+"NTW.errBlankPin"), 3)) {
            report.updateTestLog("VerifyInfoMessage", "Error message '" + InvalidPINError + "' is displayed correctly", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyInfoMessage", "Error message is not displayed correctly for blank PIN", Status_CRAFT.FAIL);
        }
    }

    public void enterInvalidMobilenumber() throws Exception {
        String strmobile = dataTable.getData("General_Data", "MobileNumber");
        Boolean blnFlagCheck = false ;
        String strerrormessage_nonnumeric = "The mobile phone number entered contains a non-numeric value. Please re-enter.";
        String strerrormessage = "The mobile phone number you have entered is too short. Please re-enter.";
        String strerrormessage_international = "Please re-enter your mobile number in the correct format e.g. 0877121212 or your international number 00353877121212.";
        String strerrormessage_start = "The mobile phone number entered contains a non-numeric value. International mobile numbers must begin with '00', e.g. 003538771212121. Please re-enter.";
        String strerrormessage_blank = "Please enter a number without blank spaces e.g. 0871234567.";
        String strerrormessage_invalid = "Please enter a valid Mobile Phone Number";
        String strerrormessage_excess = "The mobile number you have entered is invalid. Please re-enter.";
        clickJS(getObject("withdrawFunds.radiobtnEmail"), "Phone As A Preference");
        if (isElementDisplayed(getObject("withdrawFunds.radiobtnMobile"), 3)) {
            clickJS(getObject("withdrawFunds.radiobtnMobile"), "Phone As A Preference");
            Thread.sleep(1000);
            if (isElementDisplayed(getObject("withdrawFunds.txtPhoneNumber"), 3)) {
                driver.findElement(By.xpath("//input[contains(@name,'PHONENUMBER')]")).click();
                driver.findElement(By.xpath("//input[contains(@name,'PHONENUMBER')]")).clear();
                driver.findElement(By.xpath("//input[contains(@name,'PHONENUMBER')]")).sendKeys(strmobile);
                if (isMobile){
                    sendKeysJS(getObject("withdrawFunds.txtPhoneNumber") , strmobile);
                }
                report.updateTestLog("enterInvalidMobilenumber", " :: Screenshot :: ", Status_CRAFT.SCREENSHOT);
                clickJS(getObject("withdrawFunds.helpbox"), "Help box clicked");
                clickJS(getObject("withdrawFunds.continue"), "Continue clicked");
                Thread.sleep(3000);
            }
            waitForJQueryLoad();
            waitForPageLoaded(); waitForPageLoaded();
            waitForElementToClickable(getObject("withdrawFunds.txtPhoneNumber") , 10);
            String strErrorMsg_InvalidMobile = getText(getObject("withdrawFunds.mobileerror"));

            if (isElementDisplayed(getObject("withdrawFunds.mobileerror"), 3) && (strerrormessage_nonnumeric.equalsIgnoreCase(strErrorMsg_InvalidMobile))) {
                report.updateTestLog("enterInvalidMobilenumber", "Error msg is displayed as : " + strErrorMsg_InvalidMobile, Status_CRAFT.PASS);
                driver.findElement(By.xpath("//input[contains(@name,'PHONENUMBER')]")).clear();
                blnFlagCheck = true ;
            }
            if (isElementDisplayed(getObject("withdrawFunds.mobileerror"), 3) && (strerrormessage.equalsIgnoreCase(strErrorMsg_InvalidMobile))) {
                report.updateTestLog("enterInvalidMobilenumber", "Error msg is displayed as : " + strErrorMsg_InvalidMobile, Status_CRAFT.PASS);
                driver.findElement(By.xpath("//input[contains(@name,'PHONENUMBER')]")).clear();
                blnFlagCheck = true ;
            }
            if (isElementDisplayed(getObject("withdrawFunds.mobileerror"), 3) && (strerrormessage_international.equalsIgnoreCase(strErrorMsg_InvalidMobile))) {
                report.updateTestLog("enterInvalidMobilenumber", "Error msg is displayed as : " + strErrorMsg_InvalidMobile, Status_CRAFT.PASS);
                driver.findElement(By.xpath("//input[contains(@name,'PHONENUMBER')]")).clear();
                blnFlagCheck = true ;
            }
            if (isElementDisplayed(getObject("withdrawFunds.mobileerror"), 3) && (strerrormessage_start.equalsIgnoreCase(strErrorMsg_InvalidMobile))) {
                report.updateTestLog("enterInvalidMobilenumber", "Error msg is displayed as : " + strErrorMsg_InvalidMobile, Status_CRAFT.PASS);
                driver.findElement(By.xpath("//input[contains(@name,'PHONENUMBER')]")).clear();
                blnFlagCheck = true ;
            }
            if (isElementDisplayed(getObject("withdrawFunds.mobileerror"), 3) && (strerrormessage_blank.equalsIgnoreCase(strErrorMsg_InvalidMobile))) {
                report.updateTestLog("enterInvalidMobilenumber", "Error msg is displayed as : " + strErrorMsg_InvalidMobile, Status_CRAFT.PASS);
                driver.findElement(By.xpath("//input[contains(@name,'PHONENUMBER')]")).clear();
                blnFlagCheck = true ;
            }
            if (isElementDisplayed(getObject("withdrawFunds.mobileerror"), 3) && (strerrormessage_invalid.equalsIgnoreCase(strErrorMsg_InvalidMobile))) {
                report.updateTestLog("enterInvalidMobilenumber", "Error msg is displayed as : " + strErrorMsg_InvalidMobile, Status_CRAFT.PASS);
                driver.findElement(By.xpath("//input[contains(@name,'PHONENUMBER')]")).clear();
                blnFlagCheck = true ;
            }
            if (isElementDisplayed(getObject("withdrawFunds.mobileerror"), 3) && (strerrormessage_excess.equalsIgnoreCase(strErrorMsg_InvalidMobile))) {
                report.updateTestLog("enterInvalidMobilenumber", "Error msg is displayed as : " + strErrorMsg_InvalidMobile, Status_CRAFT.PASS);
                driver.findElement(By.xpath("//input[contains(@name,'PHONENUMBER')]")).clear();
                blnFlagCheck = true ;
            }

            if (blnFlagCheck ==false){
                driver.findElement(By.xpath("//input[contains(@name,'PHONENUMBER')]")).clear();
                report.updateTestLog("enterInvalidMobilenumber", "Error msg is NOT displayed as : " + strErrorMsg_InvalidMobile, Status_CRAFT.FAIL);
            }
        }
    }


    public void clicklink() throws Exception {
        if (isElementDisplayed(getObject("withdrawFunds.withdrawlink"), 3)) {
            clickJS(getObject("withdrawFunds.withdrawlink"), "link clicked");
            Thread.sleep(2000);
        }
    }

    public void navigationMobile() throws Exception {
        String strPhone = dataTable.getData("General_Data", "Phone");
        // String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
        if (isElementDisplayed(getObject("withdrawFunds.radiobtnMobile"), 3)) {
            clickJS(getObject("withdrawFunds.radiobtnMobile"), "Phone As A Preference");
            Thread.sleep(2000);
            sendKeys(getObject("withdrawFunds.txtPhoneNumber"), strPhone, "Phone Number Entered");
            clickJS(getObject("withdrawFunds.helpbox"), "Help box clicked");
        } else {
            report.updateTestLog("Phone", "'Phone' text box is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void selectfromdropdown() throws Exception {
        String strAccnt = dataTable.getData("General_Data", "Account_Label");
        if (isElementDisplayed(getObject("withdrawFunds.dropdown"), 5)) {
            clickJS(getObject("withdrawFunds.dropdown"), "Transfer to dropdown");
            //waitForJQueryLoad();
            //String elm = "xpath~//div[@class='aria_exp_wrapper boi-position-relative']/ul[@class='exp_elem_list_widget list hidden']/li";
            //selectDropDownJS(getObject("xpath~//div[@class='aria_exp_wrapper boi-position-relative']/ul[@class='exp_elem_list_widget list hidden']/li"),dataTable.getData("General_Data", "Account_Label"));
            //selectDropDownJS(getObject("withdrawFunds.accountlist"),"CURRENT ~0886");
            // waitForElementPresent(By.xpath("//button[contains(text(),'Please select')]/following-sibling::ul/li[contains(.,'" + dataTable.getData("General_Data", "Account_Name") + "')]"), 20);
            //WebElement elm = driver.findElement(By.xpath("//button[contains(text(),'Please select')]/following-sibling::ul/li[contains(.,'" + dataTable.getData("General_Data", "Account_Name") + "')]"));
            // elm.click();
            List<WebElement> allLinks = findElements(getObject("withdrawFunds.accountlist"));
            if (allLinks.size() > 1) {
                int intIndex = 1 ;
                for (WebElement ele: allLinks) {
                    String linkname = ele.getText().trim();

                    if (linkname.equalsIgnoreCase(strAccnt)) {
                        //click(getObject("withdrawFunds.dropdown"), "Transfer to dropdown");
                        //waitForJQueryLoad();
                        ele.click();
                        report.updateTestLog("selectAccountPayFrom", strAccnt + " is clicked  ", Status_CRAFT.PASS);
                        break;
                    }
                    intIndex = intIndex++;

                }
            }
        }
    }


    public void verifyWFinputpage() throws Exception{

        String StrtooltipAF = dataTable.getData("General_Data", "Operation");
        String StrtooltipContact = dataTable.getData("General_Data", "ErrorText");

        //To verify header on the input page
        if ((isElementDisplayed(getObject("withdrawFunds.inputHeader"), 10))) {
            report.updateTestLog("verifyWFinputpage", "Withdraw Funds title correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyWFinputpage", "Withdraw Funds title not present on screen", Status_CRAFT.FAIL);
        }

        // To verify Available Funds and Contact tooltips

        //Verify Available funds tooltip

        Actions a = new Actions((WebDriver) driver.getWebDriver());
        WebElement elm1 = driver.findElement(By.xpath("//*[@class='vertical-align-top boi-mt-2--negative boi-tooltip fa fa-info-circle']"));
        a.clickAndHold().moveToElement(elm1);
        a.moveToElement(elm1).build().perform();

        String StrAFtip = getText(getObject("withdrawFunds.tooltiptext"), 10).replace("\n", "");
        if (StrAFtip.equalsIgnoreCase(StrtooltipAF)) {
            report.updateTestLog("verifyWFinputpage ", "Available Funds Tool tip text matches ", Status_CRAFT.PASS);
            Thread.sleep(1000);
        } else {
            report.updateTestLog("verifyWFinputpage", "Available Funds Tool tip text does not matches ", Status_CRAFT.FAIL);
        }


        // Verify Contact tooltip
        if ((isElementDisplayed(getObject("withdrawFunds.TooltipContact"), 10))) {
            clickJS(getObject("withdrawFunds.TooltipContact"), "navigated");
        }

        WebElement elm2 = driver.findElement(By.xpath("//*[@class='boi-tooltip boi-top-3 fa fa-info-circle']"));
        a.clickAndHold().moveToElement(elm2);
        a.moveToElement(elm2).build().perform();

        String StrContacttip = getText(getObject("withdrawFunds.tooltipcontact"), 10).replace("\n", "");
        if (StrContacttip.equalsIgnoreCase(StrtooltipContact)) {

            report.updateTestLog("verifyWFinputpage ", "Contact Tool tip text matches ", Status_CRAFT.PASS);
            Thread.sleep(1000);
        } else {
            report.updateTestLog("verifyWFinputpage", "Contact Tool tip text does not matches ", Status_CRAFT.FAIL);
        }



    }

    public void verifyImptextSuccessPage() throws Exception {

        Thread.sleep(3000);
        String Strheader = dataTable.getData("General_Data", "VerifyContent");
        String Strhelptext = dataTable.getData("General_Data", "VerifyText");

        String StrHeadertip = getText(getObject("withdrawFunds.impInfo"), 10);
        String StrHelptip = getText(getObject("withdraw.helptext"), 10);

        if (isElementDisplayed(getObject("withdrawFunds.impInfo"), 3) && (StrHeadertip.equalsIgnoreCase(Strheader))) {
            report.updateTestLog("verifyImptextSuccessPage", "Important Text header is displayed as : " + Strheader, Status_CRAFT.PASS);
        }

        if (isElementDisplayed(getObject("withdraw.helptext"), 3) && (StrHelptip.equalsIgnoreCase(Strhelptext))) {
            report.updateTestLog("verifyImptextSuccessPage", "Important Text help text is displayed as : " + Strhelptext, Status_CRAFT.PASS);
        }

    }

}
package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by C101965 on 25/07/2019.
 */
public class OrderaCopyofStatement extends WebDriverHelper {

    public String Referencenumber = "";
    public  String TimeofRequest = "";

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */

        public OrderaCopyofStatement(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /*General Function:
     *Scroll to view particular  element using JS
    */

    //String strAccount = dataTable.getData("General_Data", "IBAN_Number");
    public static LinkedHashMap<String, String> mapOCS = new LinkedHashMap<String, String>();

    public void ScrollToVisibleJS(String linkToVisible) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToVisible))));
            Thread.sleep(2000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }


    }
    /*General Function:
      Scroll and Click on particular  element using JS
   */
    public void ScrollAndClickJS(String linkToClick) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToClick))));
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


    public void validatePageHeader() throws Exception {
        //validating page title
        String strPageHeader = dataTable.getData("General_Data", "PageHeader");
        String strPageHeaderMob = dataTable.getData("General_Data", "Nickname");

        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("xpath~//h1[@class='boi-no-text-transform  ecDIB  '][text()='" + strPageHeader + "']"), 5)) {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeader + "' is displayed on Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeader + "' is NOT displayed on Page", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject("xpath~//h1[text()='" + strPageHeaderMob + "']"), 5)) {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeaderMob + "' is displayed on Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeaderMob + "' is NOT displayed on Page", Status_CRAFT.FAIL);
            }
        }
    }

    public void ManagestetmentsNavigation() throws Exception {
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        if  (isElementDisplayed(getObject(deviceType() + "services.Managestatements"), 10)){
            clickJS(getObject(deviceType() + "services.Managestatements"),"Click on Manage statements option");
        }
    }

    public void OrderacopyOfstatement() throws Exception{
        if(isElementDisplayed(getObject(deviceType() + "OCS.orderacopyofstatementTile"),5)) {
            report.updateTestLog("Order a copy of statement", "Tile is displayed on Page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("Order a copy of statement", "Tile is not displayed on Page", Status_CRAFT.FAIL);
        }
        if(isElementDisplayed(getObject(deviceType() + "OCS.orderacopyofstatementtext"),5)){
            report.updateTestLog("Order a copy of statement", "Text is displayed on Page", Status_CRAFT.PASS);
        } else{
            report.updateTestLog("Order a copy of statement", "Text is not not displayed on Page", Status_CRAFT.FAIL);
        }
        if(isElementDisplayed(getObject(deviceType() +"OCS.tileText"),3)){
            report.updateTestLog("Order a copy of statement", "Text is displayed on Page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("Order a copy of statement", "Text is not displayed on Page", Status_CRAFT.FAIL);
        }
        clickJS(getObject(deviceType() + "OCS.orderacopyofstatementTile"),"Ordercopystatement tile clicked");
}
    public void OrderacopyOfstatementLandingPage() throws Exception{

        validatePageHeader();
    }

    public void clickContinue() throws Exception{
        Boolean continuekButton=driver.findElement(By.xpath("//span[text()='Continue']")).isDisplayed();
        if (continuekButton){
            if(!deviceType.equalsIgnoreCase("Web")){
                //driver.hideKeyboard();
                ScrollAndClickJS(deviceType() + "OCS.btnContinue");
            } else{
                click(getObject(deviceType() + "OCS.btnContinue"),"Continue button is clicked");
            }
            Thread.sleep(1000);
            report.updateTestLog("Order a copy of statement", "Continue Button is clicked", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("Order a copy of statement", "Continue Button is not displayed", Status_CRAFT.FAIL);
        }
    }
    public void clickGoBackarrow() throws Exception{
        String strvalidation = dataTable.getData("General_Data", "PageHeader");
        if (isElementDisplayed(getObject(deviceType() + "OCS.btnGobackarrow"),3)){
            click(getObject(deviceType() + "OCS.btnGobackarrow"),"Go Back button clicked");
            Thread.sleep(3000);
            report.updateTestLog("clickGoBackarrow", "Go Back arrow is clicked", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("clickGoBackarrow", "Go Back arrow is not clicked", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + strvalidation),3)){
            report.updateTestLog("clickGoBackarrow", "Navigation to landing page is correct", Status_CRAFT.PASS);

        }
        else {
            report.updateTestLog("clickGoBackarrow", "Incorrect navigation", Status_CRAFT.FAIL);
        }


    }
    public void clickGoBackbutton() throws Exception{
        if (deviceType.equalsIgnoreCase("Web")) {
        if (isElementDisplayed(getObject(deviceType() + "OCS.btnGobackbutton"),3)){
            click(getObject(deviceType() + "OCS.btnGobackbutton"),"Go Back button");
            Thread.sleep(1000);
           // report.updateTestLog("Order a copy of statement", "Go Back button is clicked", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("Order a copy of statement", "Go Back button is not clicked", Status_CRAFT.FAIL);
        }
        String strvalidation = dataTable.getData("General_Data", "GoBackHeaderCheck");
        if (isElementDisplayed(getObject(deviceType() + strvalidation),3)){
            report.updateTestLog("clickGoBackarrow", "Navigation to previous page is correct", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("clickGoBackarrow", "Incorrect navigation", Status_CRAFT.FAIL);
        }
        }else {
            String strGoback = deviceType()+"OCS.btnGobackbutton";
        //    Boolean gobackButton=driver.findElementByXPath(strGoback).isDisplayed();
            if (isElementDisplayed(getObject(deviceType() + "OCS.btnGobackbutton"),3)){
            ScrollAndClickJS(strGoback);
            Thread.sleep(3000);
            report.updateTestLog("Order a copy of statement", "Go Back button is clicked", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("Order a copy of statement", "Go Back button is not clicked", Status_CRAFT.FAIL);
        }
        String strvalidation = dataTable.getData("General_Data", "GoBackHeaderCheck");
        if (isElementDisplayed(getObject(deviceType() + strvalidation),3)){
            report.updateTestLog("clickGoBackarrow", "Navigation to previous page is correct", Status_CRAFT.PASS);

        }
        else {
            report.updateTestLog("clickGoBackarrow", "Incorrect navigation", Status_CRAFT.FAIL);
        }
        }
    }


    public void statementdetailsvalidation() throws Exception{
        if (isElementDisplayed(getObject(deviceType() + "OCS.validateheader"),3)){
            report.updateTestLog("Statement details", "Statement details text is displayed", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("Statement details", "Statement details text is not displayed", Status_CRAFT.FAIL);
        }
    }
    public void selectaccount() throws Exception{

        String strAccount = dataTable.getData("General_Data", "IBAN_Number");
        String strAccountcheck = deviceType() + "OCS.accountDropdown";
        ScrollToVisibleJS(strAccountcheck);
        if (isElementDisplayed(getObject(deviceType() + "OCS.accountDropdown"), 3)) {
            report.updateTestLog("statementDetails", "Dropdown of 'Account' is dislayed on Add Bill Payee Details Page", Status_CRAFT.PASS);
            if(!strAccount.equals("")) {
                    clickJS(getObject("xpath~//button[contains(text(),'Please select')]"),"Select account");
                    clickJS(getObject("xpath~//li[contains(text(),'"+strAccount+"')]"),"Account No.");
                    waitForPageLoaded();
                    }
        }
    }

    public void captureToolTip() throws Exception {
        String strTooltipData = dataTable.getData("General_Data", "ToolTipText");
        String[] arrTooltipData = strTooltipData.split("::");

        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            if (arrTooltipData[0].contains("OCS.statementnumberTooltip")) {
                click(getObject(deviceType() + arrTooltipData[0]), "ToolTip icon");
                if (isElementDisplayed(getObject(deviceType() + arrTooltipData[1]), 3)) {
                    fetchTextToCompare(getObject(deviceType() + arrTooltipData[1]), arrTooltipData[2], "ToolTip Text");
                } else {
                    report.updateTestLog("captureToolTip", "'ToolTip' text is not displayed", Status_CRAFT.FAIL);
                }

        }
        } else {
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
        }}
    public void enterStatementNumber() throws Exception{
        if (isElementDisplayed(getObject(deviceType() + "OCS.statementnumber"),3)){
            click(getObject(deviceType() +"OCS.statementnumber"));
            sendKeys(getObject(deviceType() + "OCS.statementnumber"),"Abcd");
            report.updateTestLog("Statement Number", "Statement Number is entered", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("Statement Number", "Statement Number is not entered", Status_CRAFT.FAIL);
        }
    }

    public void enterFromDate(String strDateboxFrom) throws Exception{
        waitForPageLoaded();
        String strFromDate = dataTable.getData("General_Data", "DateRangeFrom");
        waitForJQueryLoad();
        Thread.sleep(3000);
        if (isElementDisplayed(getObject(strDateboxFrom), 3)) {
            if(!deviceType.equalsIgnoreCase("Web")){
                //driver.hideKeyboard();
                waitForJQueryLoad();
                clickJS(getObject(strDateboxFrom), " Form Date ");
                sendKeys(getObject(strDateboxFrom),strFromDate);
                if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                    sendKeysJS(getObject(strDateboxFrom), strFromDate);
                }
                mapOCS.put("From",strFromDate);
                report.updateTestLog("enterFromDate", "From Date is selected successfully ", Status_CRAFT.PASS);
                waitForJQueryLoad();
                waitForJQueryLoad();
                if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                    report.updateTestLog("verifyDatefieldinvaliderrors", " :: Screenshot :: ", Status_CRAFT.SCREENSHOT);
                    String strAccountcheck = deviceType() + "OCS.accountDropdown";
                    ScrollToVisibleJS(strAccountcheck);
                    clickJS(getObject("xpath~//button[contains(text(),'Please select')]"), "Select account");
                    waitForJQueryLoad();waitForPageLoaded();
                }
            }
            else{
                click(getObject(strDateboxFrom), "Date Picker");
                waitForJQueryLoad();
                sendKeys(getObject(strDateboxFrom),strFromDate);
                mapOCS.put("From",strFromDate);
                report.updateTestLog("enterFromDate", "From Date is selected successfully ", Status_CRAFT.PASS);
                waitForJQueryLoad();waitForPageLoaded();
            }

        }
        else{
            report.updateTestLog("enterFromDate", "From Date is not selected successfully ", Status_CRAFT.FAIL);
        }
    }

    public void enterTODate(String strDateboxTo) throws Exception{
        waitForPageLoaded();
        String strToDate = dataTable.getData("General_Data", "DateRangeTo");
        waitForJQueryLoad();
        Thread.sleep(3000);
        if (isElementDisplayed(getObject(strDateboxTo), 3)) {
            if(!deviceType.equalsIgnoreCase("Web")){
            //   clickJS(getObject(deviceType() + strDateboxTo), "Date Picker");
                //driver.hideKeyboard();
                waitForJQueryLoad();
                sendKeys(getObject(strDateboxTo),strToDate);
                sendKeys(getObject(strDateboxTo),strToDate);
                if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                    sendKeysJS(getObject(strDateboxTo), strToDate);
                }
                Thread.sleep(1000);
                //mapOCS.put("To",strToDate);
                report.updateTestLog("enterTODate", "To Date is selected successfully ", Status_CRAFT.PASS);
                Thread.sleep(1000);

                if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                    report.updateTestLog("verifyDatefieldinvaliderrors", " :: Screenshot :: ", Status_CRAFT.SCREENSHOT);
                    String strAccountcheck = deviceType() + "OCS.accountDropdown";
                    ScrollToVisibleJS(strAccountcheck);
                    clickJS(getObject("xpath~//button[contains(text(),'Please select')]"), "Select account");
                    Thread.sleep(4000);
                }
            }
            else{
                click(getObject(strDateboxTo), "Date Picker");
                waitForJQueryLoad();
                sendKeys(getObject(strDateboxTo),strToDate);
                mapOCS.put("To",strToDate);
                report.updateTestLog("enterTODate", "To Date is selected successfully ", Status_CRAFT.PASS);
                Thread.sleep(2000);
            }

        }
        else{
            report.updateTestLog("enterTODate", "To Date is not selected successfully ", Status_CRAFT.FAIL);
        }
    }

    public void addmoredateverificattion() throws Exception {
        String strFromDate = dataTable.getData("General_Data", "Account_Name");
        String strToDate = dataTable.getData("General_Data", "Account_Label");
        String strRemovelink = dataTable.getData("General_Data", "AccountPosition_Grouped");

        enterFromDate(strFromDate);
        enterTODate(strToDate);

        if (isElementDisplayed(getObject(deviceType() + "OCS.addMoredates"), 3)) {
            report.updateTestLog("addmoredateverificattion", "Add more dates present on screen ", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "OCS.addMoredates"), "Add More dates link");
            Thread.sleep(3000);
        }

        if (!strRemovelink.equals("")) {
            if (isElementDisplayed(getObject(deviceType() + strRemovelink), 3)) {
                report.updateTestLog("addmoredateverificattion", "Remove link for date is present on screen ", Status_CRAFT.PASS);
            }

        } else  {
            if (!isElementDisplayed(getObject(deviceType() + "OCS.addMoredates"), 3)) {
                report.updateTestLog("addmoredateverificattion", "Add more dates no longer present on screen ", Status_CRAFT.PASS);
            }
        }
    }
    public void payToCharges() throws Exception{
        String strChargeAccount = dataTable.getData("General_Data", "Account_Name");
        String strAccountcheck = deviceType() + "OCS.ddpaytocharges";
        ScrollToVisibleJS(strAccountcheck);
        if (isElementDisplayed(getObject(deviceType() + "OCS.ddpaytocharges"), 3)) {
            report.updateTestLog("payToCharges", "Dropdown of 'Account' is dislayed ", Status_CRAFT.PASS);
            if(!strChargeAccount.equals("")) {
                clickJS(getObject("xpath~//button[contains(text(),'Please select')]"),"Select account");
                clickJS(getObject("xpath~//li[contains(text(),'"+strChargeAccount +"')]"),"Account No.");
               // mapOCS.put("Charge paying account",strChargeAccount);
                waitForPageLoaded();
            }
        }
    }

    public void notificationreq() throws Exception{
        String strNotificationReq = dataTable.getData("General_Data", "NotificationReq");
        String StrContactPreference = dataTable.getData("General_Data", "ContactPreference");
        String strEmail = dataTable.getData("General_Data", "Email");
        String strPhone = dataTable.getData("General_Data", "Phone");
        mapOCS.put("Notification",strNotificationReq);
        String strnotificationobj = "xpath~//*[text()='Will we notify you when your request is complete?']";
        ScrollToVisibleJS(strnotificationobj);
        if (strNotificationReq.equalsIgnoreCase("Yes")){
            clickJS(getObject(deviceType() +"OCS.NotificationYes"),"Notification method.");
            Thread.sleep(1000);
            report.updateTestLog("Notification Request" , strNotificationReq +" is selected",Status_CRAFT.PASS);
            mapOCS.put("Notification method",StrContactPreference);
            if (StrContactPreference.equalsIgnoreCase("Mobile")){
                clickJS(getObject(deviceType() + "OCS.radiobtnMobile"),"Mobile radio button is selected");
                waitForElementPresent(getObject(deviceType() + "OCS.txtMobile"),5);
                clickJS(getObject(deviceType() + "OCS.txtMobile"),"Mobile text field is clicked");
                sendKeys(getObject(deviceType() + "OCS.txtMobile"),strPhone);
                mapOCS.put("Mobile phone number",strPhone);
                report.updateTestLog("Mobile Field ",strPhone +"Mobile number is entered", Status_CRAFT.PASS);

            }else {
                clickJS(getObject(deviceType()+"OCS.radiobtnEmail"),"Email Radio button is selected");
                waitForElementPresent(getObject(deviceType()+ "OCS.radiobtnEmail"),3);
                clickJS(getObject(deviceType()+ "OCS.txtEmail"),"Email text field is clicked");
                sendKeys(getObject(deviceType()+ "OCS.txtEmail"),strEmail);
                mapOCS.put("Email address",strEmail);
                report.updateTestLog("Email Field ",strEmail + "Email is entered", Status_CRAFT.PASS );
            }

        }else{
            ScrollToVisibleJS(strnotificationobj);
            clickJS(getObject(deviceType() + "OCS.NotificationNO"), "No Button is click for Notification");
            report.updateTestLog("Notification Request" , strNotificationReq +" is selected",Status_CRAFT.PASS);
        }
    }
    public void validatepleasenotecontent() throws Exception{
        String strJurisdictionType = dataTable.getData("General_Data","JurisdictionType");
        String strROIText = dataTable.getData("General_Data","ROIContentvalidation");
        String strNIText = dataTable.getData("General_Data","NIContentVAlidation");
        if(strJurisdictionType.equalsIgnoreCase("ROI")){
            String strparenttext = getText(getObject(deviceType() + "OCS.textvalidation"));
            if(strparenttext.contains(strROIText)){
                report.updateTestLog("Please Note Content" , strparenttext +" is validated",Status_CRAFT.PASS);
                if(isElementDisplayed(getObject(deviceType() + "OCS.confirmationText"),3)){
                    click(getObject(deviceType()+"OCS.btncheckbox"),"Checkbox is clicked");
                    report.updateTestLog("Checkbox click", "check box is clicked", Status_CRAFT.PASS);
                    clickContinue();
                }else {
                    report.updateTestLog("Checkbox click", "check box is not clicked", Status_CRAFT.FAIL);
                }
            }else{
                report.updateTestLog("Please Note Content" , strROIText +" is not validated",Status_CRAFT.FAIL);
            }

        }else{
            String strparenttextNI = getText(getObject(deviceType() + "OCS.textvalidationNI"));
            if(strparenttextNI.contains(strNIText)){
                report.updateTestLog("Please Note Content" , strparenttextNI +" is validated",Status_CRAFT.PASS);
                clickJS(getObject(deviceType() + "OCS.btnContinue"),"Continue button is clicked");
            }else{
                report.updateTestLog("Please Note Content" , strparenttextNI +" is not validated",Status_CRAFT.FAIL);
            }
        }
    }
    public void confirmCheckbox() throws Exception{
        if(isElementDisplayed(getObject(deviceType() + "OCS.confirmationText"),3)){
            clickJS(getObject(deviceType()+"OCS.btncheckbox"),"Checkbox is clicked");
            report.updateTestLog("Checkbox click", "check box is clicked", Status_CRAFT.PASS);
            clickContinue();
        }else {
            report.updateTestLog("Checkbox click", "check box is not clicked", Status_CRAFT.FAIL);
        }
    }

    public void verifyreviewpage()throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        Thread.sleep(2000);
        if(isElementDisplayed(getObject(deviceType()+"OCS.reviewValidation"),5)) {
            homePage.verifyElementDetails(deviceType() + "OCS.reviewValidation");
            Thread.sleep(2000);
            clickConfirm();
        }else {
            homePage.verifyElementDetails("xpath~//div[@class='ecDIB  boi-full-width boi-mt-20 boi-tg__font--regular boi-tg__size--default  ']");
        }
    }


    public void verifyOCSreviewpage()throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if(isElementDisplayed(getObject(deviceType() + "OCS.reviewValidation"),3)){
            report.updateTestLog("verifyOCSreviewpage", "Review page is displayed", Status_CRAFT.PASS);

            if(mapOCS.size()!=0){
                for (Map.Entry obj : mapOCS.entrySet()) {

                    if (isElementDisplayed(getObject("xpath~//div[@class='tc-card boi-card-standard tc-card-bg']/descendant::*[text()='" + obj.getKey() + "']"), 5)) {

                        String strUiValue = getText(getObject("xpath~//div[@class='tc-card boi-card-standard tc-card-bg']/descendant::*[text()='" + obj.getKey() + "']/ancestor::*[contains(@class,'tc-question-part boi')]/following-sibling::*[contains(@class,'tc-answer-part boi')]"));
                        String strExpectedValue = obj.getValue().toString();
                        if(strExpectedValue.contains("//"))
                        {
                            strExpectedValue = strExpectedValue.replaceAll("//","/");
                            System.out.println("Expected value is : " +strExpectedValue);
                        }
                        if (strUiValue.trim().contains(strExpectedValue))
                        {
                            report.updateTestLog("verifyOCSreviewpage", "Value of label '" + obj.getKey() + "' is correctly displayed on OCS Review Page, expected: '" + strExpectedValue + "'", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("verifyOCSreviewpage", "Value of label '" + obj.getKey() + "' is NOT correctly displayed on OCS Review Page, expected: '" + strExpectedValue + "', Actual : '" + strUiValue + "'", Status_CRAFT.FAIL);
                        }
                    } else {
                        report.updateTestLog("verifyOCSreviewpage", "Label '" + obj.getKey() + "' is not displayed on Review Page", Status_CRAFT.FAIL);
                    }
                }

            }else{
                report.updateTestLog("verifyOCSreviewpage", "FAIL---------", Status_CRAFT.FAIL);

            }

        }else {
            homePage.verifyElementDetails("xpath~//div[@class='ecDIB  boi-full-width boi-mt-20 boi-tg__font--regular boi-tg__size--default  ']");
        }
  }

    public void verifyConfirmationScreen() throws Exception{
        if (isElementDisplayed(getObject(deviceType() + "OCS.refrenceNumber"), 5) &&
                isElementDisplayed(getObject(deviceType() + "OCS.timeOfrequest"), 5)) {

            String strReferenceID = getText(getObject(deviceType() + "OCS.refrenceNumber"));
            String strTimestamp = getText(getObject(deviceType() + "OCS.timeOfrequest"));

            report.updateTestLog("Referencetimestamp", "Reference is displayed as : "+strReferenceID+ " and Time Stamp is displayed as : " +strTimestamp , Status_CRAFT.PASS);

        } else {
            report.updateTestLog("Referencetimestamp", "Reference and Time Stamp are not displayed on screen", Status_CRAFT.FAIL);
        }

        if(isElementDisplayed(getObject(deviceType()+ "OCS.textsent"),5) && isElementDisplayed(getObject(deviceType()+ "AAQ.txtvalidation"),5)){
            String strTextvaliadtion = getText(getObject(deviceType()+"AAQ.txtvalidation"),5);
            String strTextvaliadtionUi = "We will process your request within 5 working days. Details of this request are saved in your sent messages in Services.\n" + "Your statement copy (or copies) will be posted to your address.";
            if(strTextvaliadtion.equalsIgnoreCase(strTextvaliadtionUi)){
                report.updateTestLog("Text Message and Sent ", "Text Message and Sent text are displayed on screen as "+strTextvaliadtion, Status_CRAFT.PASS);
            }else{
                report.updateTestLog("Text Message and Sent ", "Text Message and Sent text are not as expected", Status_CRAFT.FAIL);
            }}else{
            report.updateTestLog("Text Message and Sent ", "Text Message and Sent text are not displayed on screen", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject(deviceType() +"OCS.Backtohome"), 5)) {

            isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Reference: ')]"),"Reference Number","Reference number is dispalyed",5);
            Referencenumber = getText(getObject("xpath~//div[contains(text(),'Reference: ')]"),10);
//            String FinalRef[] = Referencenumber.split(":");
//            String FinalRef1 = FinalRef[1].trim();

            isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Time of request: ')]"),"Time of Request","Time of request is dispalyed",5);
            TimeofRequest = getText(getObject("xpath~//div[contains(text(),'Time of request: ')]"),5);
            clickJS(getObject(deviceType() +"OCS.Backtohome"), "Back to Home");
            Thread.sleep(2000);
            report.updateTestLog("BacktoHome button ", "BacktoHome button Clicked on confirmation screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("BacktoHome button ", "BacktoHome button not Clicked on confirmation screen", Status_CRAFT.FAIL);
        }
    }

    public void verifyAccountfielderror() throws Exception {
        clickContinue();
        waitForJQueryLoad();waitForPageLoaded();
        String strerrormessage = dataTable.getData("General_Data","FirstName");
        if (isElementDisplayed(getObject(deviceType() + "OCS.account.error"),3)){
            String strErrorMsg_InvalidAccount = getText(getObject(deviceType() + "OCS.account.error"));
            if (strerrormessage.equalsIgnoreCase(strErrorMsg_InvalidAccount)){
                report.updateTestLog("verifyAccountfielderror", "Correct error message is displayed", Status_CRAFT.PASS);
            }else {
                report.updateTestLog("verifyAccountfielderror", "Correct error message is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAccountfielderror", "Error message is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void verifyDatefieldblankerror() throws Exception {


        while (isElementDisplayed(getObject(deviceType() + "OCS.addMoredates"), 3)) {

            clickJS(getObject(deviceType() + "OCS.addMoredates"), "Add More dates link");
            Thread.sleep(3000);
        }
        clickContinue();
        Thread.sleep(1000);

        String strerrormessage = dataTable.getData("General_Data", "VerifyText");
        String strdates = dataTable.getData("General_Data", "Operation");

        String[] arrDates = strdates.split(";");

        for (int i = 0; i < arrDates.length; i++) {
            String strdate = arrDates[i];

            if (isElementDisplayed(getObject(deviceType() + strdate), 3)) {

                String strErrorMsg_InvalidAccount = getText(getObject(deviceType() + strdate));

                if (strerrormessage.equalsIgnoreCase(strErrorMsg_InvalidAccount)) {
                    report.updateTestLog("verifyAccountfielderror", "Correct error message is displayed:" +strerrormessage, Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyAccountfielderror", "Correct error message is not displayed", Status_CRAFT.FAIL);
                }
            } else {

                report.updateTestLog("verifyAccountfielderror", "Error message is not displayed", Status_CRAFT.FAIL);
            }


        }
    }

    public void verifyDatefieldinvaliderrors() throws Exception {

        while (isElementDisplayed(getObject(deviceType() + "OCS.addMoredates"), 3)) {
            clickJS(getObject(deviceType() + "OCS.addMoredates"), "Add More dates link");
            waitForJQueryLoad(); waitForPageLoaded();
        }
        String strFromDate = dataTable.getData("General_Data", "Account_Grouped");
        String strToDate = dataTable.getData("General_Data", "Account_Label");
        String strenterdate = dataTable.getData("General_Data", "DOB");
        String[] arrFromDates = strFromDate.split(";");
        for (int i = 0; i < arrFromDates.length; i++){
            String strdate = arrFromDates[i];
            ScrollToVisibleJS(deviceType() + strdate);
            Thread.sleep(1000);
            clickJS(getObject(deviceType() + strdate), "From date");
            Thread.sleep(3000);
            sendKeys(getObject(deviceType() + strdate),strenterdate);
            if (isMobile){
                sendKeysJS(getObject(deviceType() + strdate),strenterdate);
            }else{
                sendKeys(getObject(deviceType() + strdate),strenterdate);
            }
            Thread.sleep(1000);
            clickJS(getObject(deviceType() + "OCS.btnContinue"),"Continue button clicked");
            Thread.sleep(5000);
        }
        String[] arrToDates = strToDate.split(";");
        for (int i = 0; i < arrToDates.length; i++){
            String strdate = arrToDates[i];
            clickJS(getObject(deviceType() + strdate), "To date");
            Thread.sleep(2000);
            waitForJQueryLoad(); waitForPageLoaded();
            sendKeys(getObject(deviceType() + strdate),strenterdate);
            sendKeys(getObject(deviceType() + strdate),strenterdate);
            if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                sendKeysJS(getObject(deviceType() + strdate),strenterdate);
                Thread.sleep(3000);
            }
            waitForJQueryLoad();waitForPageLoaded();
            if(!deviceType().equalsIgnoreCase("w.")){
                driver.hideKeyboard();
            }else{
                Thread.sleep(4000);
                WebElement straccdd = driver.findElement(By.xpath("//div[@id='C5__C1__QUE_8D882C10C5D81C001143569_exp_wrapper']"));
                ScrollToVisibleJS("xpath~//div[@id='C5__C1__QUE_8D882C10C5D81C001143569_exp_wrapper']");
                Thread.sleep(2000);
                Actions action = new Actions((WebDriver) driver.getWebDriver());
                action.moveToElement(straccdd).click().build().perform();
                Thread.sleep(2000);
            }
            waitForJQueryLoad(); waitForPageLoaded();
        }

        if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
            report.updateTestLog("verifyDatefieldinvaliderrors", " :: Screenshot :: ", Status_CRAFT.SCREENSHOT);
            String strAccountcheck = deviceType() + "OCS.accountDropdown";
            ScrollToVisibleJS(strAccountcheck);
            clickJS(getObject("xpath~//button[contains(text(),'Please select')]"), "Select account");
            Thread.sleep(4000);
        }
        clickContinue();
        waitForJQueryLoad(); waitForPageLoaded();
        String strFromerrormessage = dataTable.getData("General_Data", "PolicyNumber");
        String strToerrormessage = dataTable.getData("General_Data", "PolicyName");
        String strFdates = dataTable.getData("General_Data", "DropDownItems");
        String strTdates = dataTable.getData("General_Data", "DirectionalText");
        String[] arrFDates = strFdates.split(";");
        for (int i = 0; i < arrFDates.length; i++) {
            String strdate = arrFDates[i];
            ScrollToVisibleJS(deviceType() + strdate);
            if (isElementDisplayed(getObject(deviceType() + strdate), 3)) {
                String strErrorMsg_InvalidAccount = getText(getObject(deviceType() + strdate));
                if (strFromerrormessage.equalsIgnoreCase(strErrorMsg_InvalidAccount)) {
                    report.updateTestLog("verifyDatefieldinvaliderrors", "For Form Date Field Correct error message is displayed:" +strFromerrormessage, Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyDatefieldinvaliderrors", "For Form Date Field Correct error message is not displayed", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("verifyDatefieldinvaliderrors", "Error message is not displayed", Status_CRAFT.FAIL);
            }
        }

        Boolean flgError = false ;
        String[] arrTDates = strTdates.split(";");
        for (int i = 0; i < arrTDates.length; i++) {
            String strdate = arrTDates[i];
            flgError = false ;
            scrollToView(getObject(deviceType() + strdate));
            if (isElementDisplayed(getObject(deviceType() + strdate), 3)) {
                String strErrorMsg_InvalidAccount = getText(getObject(deviceType() + strdate));
                if (strToerrormessage.equalsIgnoreCase(strErrorMsg_InvalidAccount)) {
                    flgError = true ;
                     break;
                }
            }
        }
        if (flgError) {
            report.updateTestLog("verifyAccountfielderror", "For To Date Field Correct error message is displayed:" + strToerrormessage, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAccountfielderror", "For To Date Field Correct error message is not displayed", Status_CRAFT.FAIL);
        }
        while (isElementDisplayed(getObject(deviceType() + "OCS.removelink"), 3)) {
            clickJS(getObject(deviceType() + "OCS.removelink"), "remove link is removed");
            waitForJQueryLoad(); waitForPageLoaded();
        }
    }

    public void clickConfirm() throws Exception{
        if (isElementDisplayed(getObject(deviceType() + "OCS.btnConfirm"),3)){
            clickJS(getObject(deviceType() + "OCS.btnConfirm"),"Continue button clicked");
            waitForJQueryLoad(); waitForPageLoaded();
            report.updateTestLog("Order a copy of statement", "Continue button is clicked", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("Order a copy of statement", "Continue button is not clicked", Status_CRAFT.FAIL);
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
        if (isElementDisplayed(getObject(deviceType()+"SL.showmore"),5)) {
            report.updateTestLog("verifyShowMoreButton", "Show more button is displayed", Status_CRAFT.PASS);
            do {
                clickJS(getObject(deviceType()+"SL.showmore"), "Show more button");
                waitForPageLoaded();
//                break;
//                if (!isElementDisplayed(getObject(deviceType()+"SL.showmore"), 3)) {
//                    report.updateTestLog("verifyCompletedSection", "Completed Section is displayed", Status_CRAFT.PASS);
//
//                }
            } while (isElementDisplayed(getObject(deviceType()+"SL.showmore"), 3));
        }}

    public void clickSent() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "SL.senttab"), 10)) {
            clickJS(getObject(deviceType() + "SL.senttab"), "Sent Tab");
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



    public void NavigationToOrderStatement() throws Exception {
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        if  (isElementDisplayed(getObject(deviceType() + "services.Managestatements"), 10)){
            clickJS(getObject(deviceType() + "services.Managestatements"),"Click on Manage statements option");
            if(isElementDisplayed(getObject(deviceType()+"ManageStatements.lnkOrderUpToDate"),10)){
            clickJS(getObject(deviceType() + "ManageStatements.lnkOrderUpToDate"),"order statement is clicked successfully");
            }
        }
    }



}


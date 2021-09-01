package businesscomponents;

/**
 * @Function/Epic: Change address of customer accounts as part of Sprint-7 (Squad-3)
 * Class: Change Address
 * Developed on: 22/10/2018
 * @Developed by: C966003
 * Update on     Updated by     Reason
 * 12/04/2019     C966003       Done code clean up activity
 */

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.awt.*;
import java.util.List;

//import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

/**
 * Class for storing component groups functionality
 * @author Cognizant
 */
public class ChangeAddress extends WebDriverHelper {
    public String Referencenumber = "";
    public String TimeofRequest = "";

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public ChangeAddress(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /*launch Changeaddress*/

    public void launchChangeAddress() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.btnServiceDesk"), 3)) {
            clickJS(getObject((deviceType() + "home.btnServiceDesk")),"Services Tab clicked");
            report.updateTestLog("launchChangeAddress", "Services is displayed", Status_CRAFT.PASS);
            //report.updateTestLog(" 'SERVICES' ", "  'SERVICES' link is displayed successfully", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "services.lblChangeAddress"), "Change address");
            Thread.sleep(5000);
            /*waitForElementPresent(getObject("changeAddress.lblSelectAccount"), 5);
            if (isElementDisplayed(getObject("changeAddress.lblSelectAccount"), 3)) {
                String SelectAccount = getText(getObject("changeAddress.lblSelectAccount"));
                report.updateTestLog("SelectAccount", SelectAccount + " screen is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("SelectAccount", "'Select Accounts' screen is not displayed successfully", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog(" 'SERVICES' ", "  'SERVICES' link is not displayed successfully", Status_CRAFT.FAIL);
        }*/
        }
    }


    public void clickContinue() throws Exception {
        isElementDisplayedWithLog(getObject("changeAddress.btnContinue"),
                "Continue","Select accounts",2);
        scrollToView(getObject("changeAddress.btnContinue"));
        if(deviceType.equalsIgnoreCase("web")){
            click(getObject("changeAddress.btnContinue"), "Continue");}
        else{
            clickJS(getObject("changeAddress.btnContinue"), "Continue");}

        if (isElementDisplayed(getObject("changeAddress.accounterror") ,4 )){
            scrollToView(getObject("changeAddress.accounterror"));
            report.updateTestLog("clickContinue", getText(getObject("changeAddress.accounterror")) + " :: Error Displayed : Un-selecting the few accounts ", Status_CRAFT.PASS);
            uncheckAccountForChangeAddress();
            scrollToView(getObject("changeAddress.btnContinue"));
            if(deviceType.equalsIgnoreCase("web")){
                click(getObject("changeAddress.btnContinue"), "Continue");}
            else{clickJS(getObject("changeAddress.btnContinue"), "Continue");}
        }
    }

    public void uncheckAccountForChangeAddress() throws Exception {
        if (isElementDisplayed(getObject("changeAddress.accounterror") ,4 )){
            List<WebElement> accountList = findElements(getObject("xpath~//*[contains(@name,'ISSELECTED')]"));
            int aCount = accountList.size();
            if (accountList.size() > 15 ){
                aCount = accountList.size() - 15 ;
                for (int i = 0 ; i < aCount; i++) {
                    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                    js.executeScript("arguments[0].scrollIntoView();", accountList.get(i));
                    js.executeScript("arguments[0].click();", accountList.get(i));
                    Thread.sleep(3000);
                    report.updateTestLog("clickContinue", " Un-selecting the few accounts so that we can change the Address for less that 20 Accounts.", Status_CRAFT.DONE);
                    accountList = findElements(getObject("xpath~//*[contains(@name,'ISSELECTED')]"));
                }
            }
        }
    }


    public void changeAddress_EasySteps_ToolTip() throws Exception {
        Thread.sleep(1000);
        String strEasyStepsTxt1 = "1. Select the account(s) which you want to change the address for.";
        String strEasyStepsTxt2 = "2. Enter your new address and press continue.";
        String strEasyStepsTxt3 = "3. Complete the security process to confirm your request.";
        click(getObject("changeAddress.easystepsLink"), "3 easy steps tool tip clicked");
        String strStepsText1 = getText(getObject("changeAddress.easystep1"), 10);
        String strStepsText2 = getText(getObject("changeAddress.easystep2"), 10);
        String strStepsText3 = getText(getObject("changeAddress.easystep3"), 10);
        if ((strStepsText1.equalsIgnoreCase(strEasyStepsTxt1)) && (strStepsText2.equalsIgnoreCase(strEasyStepsTxt2)) && (strStepsText3.equalsIgnoreCase(strEasyStepsTxt3)))
        {
            click(getObject("changeAddress.popup_OkBtn"), "OK button clicked");
            report.updateTestLog("changeAddress_EasySteps_ToolTip", "3 steps to Update address is displayed successfully", Status_CRAFT.PASS);
            Thread.sleep(2000);
        } else {
            report.updateTestLog("changeAddress_EasySteps_ToolTip", "3 asy steps not displayed", Status_CRAFT.PASS);
        }
    }



    public void clickConfirm() throws Exception {
//        if(isMobile){
            if(isIOS){
                SCA_MobileAPP scaMobileAPP=new SCA_MobileAPP(scriptHelper);
                scrollToView(getObject("changeAddress.btnConfirm"));
                waitForJQueryLoad();waitForPageLoaded();
                scaMobileAPP.changeWebviewToNative();
                clickButtonOrLinkIOS("Confirm");
                scaMobileAPP.changeNativeToWebview();
//            }else{
//                try{
//                    driver.context("NATIVE_APP");
//                    if(isIOS){
//                        clickButtonOrLinkIOS("Confirm");
//                    }else{
//                        MobileElement el1 = (MobileElement) driver.findElementByXPath("//android.view.View[3]/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.Button");
//                        (new TouchAction(driver.getAppiumDriver())).tap(PointOption.point(el1.getCenter().getX(),el1.getCenter().getY())).perform();
//                    }
//                    report.updateTestLog("PushNotification_Acccept", "Push Notification Accepted",Status_CRAFT.PASS);
//                }catch(Exception e){
//                    System.out.print("Exception is there ::"+e.getMessage());
//                }
//            }
//
//            Thread.sleep(3000);
        }else{
            isElementDisplayedWithLog(getObject("changeAddress.btnConfirm"),
                    "Confirm","Review",2);
            clickJS(getObject("changeAddress.btnConfirm"), "Confirm");
        }
    }


    public void selectAccountsChangeAddress() throws Exception {
        String strAccount = dataTable.getData("General_Data", "Account_Name");
        String[] arrStrAccount = strAccount.split(";");
        for (int accnt = 0; accnt < arrStrAccount.length; accnt++) {
            // if (arrStrAccount[accnt].equalsIgnoreCase("Select all")) {
                /*if (!driver.findElement(getObject("xpath~//input[contains(@name,'SELECTALL')]")).isSelected()) {
                    clickJS(getObject("xpath~//input[contains(@name,'SELECTALL')]"), "Select all");
                } else {*/
            //    report.updateTestLog("Select all", "'Select all Accounts' check box button is selected successfully", Status_CRAFT.PASS);

            // } else {
            // if (driver.findElement(getObject("xpath~//input[contains(@name,'SELECTALL')]")).isSelected()) {
            //   clickJS(getObject("xpath~//input[contains(@name,'SELECTALL')]"), "DeSelected all accounts");
            // }
            //  if (!driver.findElement(getObject("xpath~//input[contains(@name,'SELECTALL')]")).isSelected()) {
            List<WebElement> accountList = findElements(getObject("xpath~//div[@class='ecDIB  boi-tile-label-primary boi_label_semibold']"));
            int aCount = accountList.size();
            for (int i = accnt + 1; i <= aCount; i++) {
                String xpathAccount = "xpath~//div[@id='C6__C1__p1_HEAD_F5DF587E6482FCF3609134_R" + i + "']";
                String xpathCheckAccount = "xpath~//input[@id='C6__C1__ID_SELECTED_ACCOUNT_0_R" + i + "']";
                String strFetchAccount = getText(getObject(xpathAccount));
                if (strFetchAccount.equalsIgnoreCase(arrStrAccount[accnt])) {
                    clickJS(getObject(xpathCheckAccount), arrStrAccount[accnt] + " Checkbox");
                    Thread.sleep(2000);
                    break;
                }
            }
        }
        //}
        //}
        clickContinue();
    }


    public void enterAddressLinedetails() throws Exception {
        String strAddress1 = dataTable.getData("General_Data", "Address1");
        String strAddress2 = dataTable.getData("General_Data", "Address2");
        String strAddress3 = dataTable.getData("General_Data", "Address3");

        isElementDisplayedWithLog(getObject("changeAddress.edtAddress1"),
                "Address Line","Enter address details",2);
        sendKeys(getObject("changeAddress.edtAddress1"), strAddress1, "Address Line 1");
        sendKeys(getObject("changeAddress.edtAddress2"), strAddress2, "Address Line 2");
        sendKeys(getObject("changeAddress.edtAddress3"), strAddress3, "Address Line 3");
    }


    public void enterEmailDetails() throws Exception {
        String strToggleMailcheck = dataTable.getData("General_Data", "emailToggle");
        String strEmail = dataTable.getData("General_Data", "Email");
        String strConfirmEmail = dataTable.getData("General_Data", "NewEmail");

        if (strToggleMailcheck.equalsIgnoreCase("Yes")) {
            isElementDisplayedWithLog(getObject("changeAddress.btnEmailToggle"),
                    "Email toggle button","Enter address details",2);
            clickJS(getObject("changeAddress.btnEmailToggle"), "Email toggle button");
            sendKeys(getObject("changeAddress.edtNewEmail"), strEmail, "New Email");
            Thread.sleep(4000);
            clickJS(getObject("changeAddress.edtConfirmEmail"), "ConfirmEmail Textbox clicked");
            sendKeys(getObject("changeAddress.edtConfirmEmail"), strConfirmEmail, "Confirm Email");
        }
    }

    /**
     * Function : To Create Auto-Complete dropdown
     * Created on     Created by    Reason
     * 25/11/2019    C966119
     */
    public void selectAutoCompleteDropdown(String strCountry) throws Exception {
        if (isMobile) {
            MobileElement el1 = (MobileElement) driver.findElementByXPath("//div[@role='option'][text()='" + strCountry + "']");
            el1.click();
        }else if(deviceType().equals("w.")) {
            try {
                WebElement country = driver.findElement(By.xpath("//div[@role='option'][text()='"+ strCountry +"']"));
                Actions actions = new Actions(driver.getWebDriver());
                actions.moveToElement(country).click().build().perform();
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
            }
        } else {
            try {
                WebElement country = driver.findElement(By.xpath("//div[@role='option'][text()='"+ strCountry +"']"));
                Actions actions = new Actions(driver.getRemoteWebDriver());
                actions.moveToElement(country).click().build().perform();
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
            }
        }
        Thread.sleep(2000);
    }

    public void selectAddressDropdowns() throws Exception {
        String strCountry = dataTable.getData("General_Data", "selectCountry");
        String strCounty = dataTable.getData("General_Data", "selectCounty");
        String strDublinDistrict = dataTable.getData("General_Data", "selectDublinDistrict");

        ScrollAndClickJS(deviceType()+ "changeAddress.lblEnterAddress");
        if (isElementDisplayed(getObject(deviceType() + "changeAddress.lblEnterAddress"), 5)) {
            report.updateTestLog("selectAddressDropdowns", "Change Address screen is displayed successfully", Status_CRAFT.PASS);
            waitForElementPresent(getObject(deviceType() + "changeAddress.inputCounty"), 10);
            sendKeys(getObject(deviceType() + "changeAddress.inputCounty"), strCounty, "selectCounty");
            selectAutoCompleteDropdown(strCounty);
            if (strCounty.equalsIgnoreCase("Dublin")) {
                waitForElementToClickable(getObject(deviceType() + "changeAddress.drpdownDublinDistrict"), 10);
                clickJS(getObject(deviceType() + "changeAddress.drpdownDublinDistrict") , "Select County");
                clickJS(getObject("xpath~//div[contains(@class,'aria_exp_wrapper')]/ul/li[@data-value='" + strDublinDistrict + "']"), " Selected Dublin District is :- '" + strDublinDistrict + "'");
            } else {
                report.updateTestLog("selectAddressDropdowns", "Dublin District is not displayed, if Country is other than Ireland", Status_CRAFT.PASS);
            }

        } else {
            report.updateTestLog("selectAddressDropdowns", "Change Address screen is NOT displayed", Status_CRAFT.FAIL);

        }
    }

    public void selectCountryTextbox () throws Exception {
        String strCountry = dataTable.getData("General_Data", "selectCountry");
        String strCounty = dataTable.getData("General_Data", "selectCounty");
        String strDublinDistrict = dataTable.getData("General_Data", "selectDublinDistrict");
        if (isElementDisplayed(getObject("changeAddress.txtCountry"), 5)) {
           click(getObject("changeAddress.txtCountry"));
            report.updateTestLog("Country textbox update", "On click on Country text box list is not displayed", Status_CRAFT.PASS);
            sendKeys(getObject("changeAddress.txtCountry"), strCountry, "selectCountry");
           click(getObject("xpath~//div[@class='boi_account_level_heading col-hidden-xs col-hidden-sm col-hidden-md  ']"));
            report.updateTestLog("Country textbox update", "Country is selected", Status_CRAFT.PASS);
            click(getObject("changeAddress.txtCounty"));
            report.updateTestLog("County textbox update", "On click on County text box list is not displayed", Status_CRAFT.PASS);
            sendKeys(getObject(("changeAddress.txtCounty")), strCounty, "selectCounty");
            Thread.sleep(1000);
            click(getObject("xpath~//div[@class='boi_account_level_heading col-hidden-xs col-hidden-sm col-hidden-md  ']"));
            report.updateTestLog("County textbox update", "County is selected", Status_CRAFT.PASS);
            click(getObject("changeAddress.crossclearcounty"));
            report.updateTestLog("County textbox update", "County Textbox is cleared", Status_CRAFT.PASS);
            sendKeys(getObject("changeAddress.txtCounty"), strCounty, "selectCounty");
            Thread.sleep(1000);
           click(getObject("xpath~//div[@class='tc-card boi-card-standard tc-card-bg']"));
            report.updateTestLog("County textbox update", "County is selected", Status_CRAFT.PASS);
            if (strCounty.equalsIgnoreCase("Dublin")) {
                waitForElementPresent(getObject("changeAddress.ddDublindDisctrict"), 10);
                click(getObject(("changeAddress.ddDublindDisctrict")));
                Thread.sleep(1000);
                click(getObject("xpath~//div[contains(@class,'aria_exp_wrapper')]/ul/li[@data-value='" + strDublinDistrict + "']"), "Selected Dublin District is- '" + strDublinDistrict + "'");
            } else {
                report.updateTestLog("selectAddressDropdowns", "Dublin District is not displayed", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("Country textbox update", "Country is not selected", Status_CRAFT.FAIL);
        }

    }


    public void selectCountry() throws Exception {
        String strCountry = dataTable.getData("General_Data", "selectCountry");
        HomePage homePage = new HomePage(scriptHelper);
        homePage.ScrollToVisibleJS(deviceType()+ "changeAddress.lblEnterAddress");
        if (isElementDisplayed(getObject("changeAddress.txtCountry"), 5)) {
            click(getObject("changeAddress.txtCountry"),"Country Box clicked");
            sendKeys(getObject("changeAddress.txtCountry"),strCountry, "selectCountry :: ");
            Thread.sleep(1000);
            selectAutoCompleteDropdown(strCountry);
            report.updateTestLog("Country textbox update", "Country '"+ strCountry +"is selected", Status_CRAFT.PASS);
            Thread.sleep(1000);
        } else {
            report.updateTestLog("selectAddressDropdowns", "Change Address screen is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    public void selectCounty_Ireland_NorthIreland() throws Exception {
        String strCountry = dataTable.getData("General_Data", "selectCounty");
        HomePage homePage = new HomePage(scriptHelper);
        homePage.ScrollToVisibleJS(deviceType()+ "changeAddress.lblEnterAddress");
        if (isElementDisplayed(getObject(deviceType() + "changeAddress.lblEnterAddress"), 5)) {
            click(getObject((deviceType() + "changeAddress.inputCounty")), "select County");
            sendKeys(getObject((deviceType() + "changeAddress.inputCounty")), strCountry, "select County");
            selectAutoCompleteDropdown(strCountry);
            report.updateTestLog("Country textbox update", "Country '"+ strCountry +"is selected", Status_CRAFT.PASS);

        } else {
            report.updateTestLog("selectAddressDropdowns", "Change Address screen is NOT displayed", Status_CRAFT.FAIL);
        }
    }


    public void enterCounty_Others() throws Exception {
        String strCountry = dataTable.getData("General_Data", "selectCounty");
        HomePage homePage = new HomePage(scriptHelper);
        homePage.ScrollToVisibleJS(deviceType()+ "changeAddress.lblEnterAddress");
        if (isElementDisplayed(getObject(deviceType() + "changeAddress.lblEnterAddress"), 5)) {
            click(getObject((deviceType() + "changeAddress.inputCounty_Others")), "select County");
            sendKeys(getObject((deviceType() + "changeAddress.inputCounty_Others")), strCountry, "Select County :: ");
            if (!isElementDisplayed(getObject("changeAddress.lblCountyOptional") , 4)) {
                selectAutoCompleteDropdown(strCountry);
            }else {
                report.updateTestLog("enterCounty_Others", "Country '"+ strCountry +" :: is selected and County ::"+ strCountry, Status_CRAFT.PASS);
            }
            report.updateTestLog("Country textbox update", "Country '"+ strCountry +" :: is selected", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("selectAddressDropdowns", "Change Address screen is NOT displayed", Status_CRAFT.FAIL);
        }
    }


    public void enterEircode() throws Exception {
        String strEircode = dataTable.getData("General_Data", "Eircode");
        if (isElementDisplayed(getObject(deviceType() + "changeAddress.txtEirCode"), 5)) {
            report.updateTestLog("enterEircode", "EirCode is displayed successfully", Status_CRAFT.PASS);
            sendKeys(getObject(deviceType() + "changeAddress.txtEirCode"), strEircode, "Eircode");
        } else {
            //report.updateTestLog("enterEircode", "EirCode is NOT displayed", Status_CRAFT.FAIL);
        }
    }


    public void enterPostcode() throws Exception {
        String strPostcode = dataTable.getData("General_Data", "Postcode");

        isElementDisplayedWithLog(getObject(deviceType() + "changeAddress.txtPostcode"),
                "Postcode","Change address page",2);
        sendKeys(getObject(deviceType() + "changeAddress.txtPostcode"), strPostcode, "Postcode");
    }

    public void selectCounty_EngWales() throws Exception {
        String strCounty = dataTable.getData("General_Data", "selectCounty");

        isElementDisplayedWithLog(getObject(deviceType() + "changeAddress.inputCounty_Others"),
                "County","Change address page",2);
        sendKeys(getObject(deviceType() + "changeAddress.inputCounty_Others"), strCounty, "selectCounty");
    }


    public void clearCountrybox() throws Exception {
        String strCountry = dataTable.getData("General_Data", "selectCountry");

        isElementDisplayedWithLog(getObject(deviceType() + "changeAddress.lblEnterAddress"),
                "Enter address title","Change address page",2);
        sendKeys(getObject(deviceType() + "changeAddress.inputCountry"), strCountry, "selectCountry");
        isElementDisplayedWithLog(getObject("changeAddress.clearCountrySearchBox"),
                "X icon on Country search box","Change address page",5);
                click(getObject("changeAddress.clearCountrySearchBox"));
    }


    public void clearCountybox() throws Exception {
        String strCounty = dataTable.getData("General_Data", "selectCounty");

        isElementDisplayedWithLog(getObject(deviceType() + "changeAddress.inputCounty_Others"),
                "Country search box","Change address page",2);
        sendKeys(getObject(deviceType() + "changeAddress.inputCounty_Others"), strCounty, "selectCounty");
        isElementDisplayedWithLog(getObject("changeAddress.clearCountySearchBox"),
            "X icon on Country search box","Change address page",5);
            click(getObject("changeAddress.clearCountySearchBox"));
    }

    public void verifyElementDetails(String strSectionElement) throws Exception {
        String[] arrValidation = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String dataSectionUI = "";
        String strValidateHead = "";
        String strValidateData = "";
        String strValidateData1 = "";
        if (isElementDisplayed(getObject(strSectionElement), 5)) {
            dataSectionUI = getText(getObject(strSectionElement)).toUpperCase();
            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                strValidateHead = arrValidation[intValidate].split("::")[0];
                strValidateData = arrValidation[intValidate].split("::")[1];
                strValidateData1 = arrValidation[intValidate].split("::")[2];
                if (dataSectionUI.contains(strValidateData.toUpperCase()) && dataSectionUI.contains(strValidateData1.toUpperCase())){
                    report.updateTestLog("verifyElementDetails", strValidateHead + " value '" + strValidateData + "'value'" + strValidateData1 + "'is displayed correctly ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyElementDetails", strValidateHead + " value '" + strValidateData +  "'value'" + strValidateData1 + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("verifyElementDetails", "Section element '" + strSectionElement + "' is not displayed on Screen", Status_CRAFT.FAIL);
        }
    }

    public void changeAddressReview() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject("changeAddress.ReviewChangeAddress"),5)) {
            homePage.verifyElementDetails("changeAddress.ReviewChangeAddress");
        } else {
            verifyElementDetails("OrderIntcert.messagetextvalidation");
    }}



    public void verifyErrorMessages() throws Exception {
        String strAccount = dataTable.getData("General_Data", "Account_Name");
        String strErrorText = dataTable.getData("General_Data", "ErrorText");
        String[] arrStrAccount = strAccount.split(";");
        for (int accnt = 0; accnt < arrStrAccount.length; accnt++) {
            if (arrStrAccount[accnt].equalsIgnoreCase("De Select all")) {
                clickJS(getObject("xpath~//input[contains(@name,'SELECTALL')]"), "DeSelected all accounts");
                clickContinue();
                String errText = getText(getObject("changeAddress.txtAccntErrorMsg"));
                scrollToView(getObject("changeAddress.txtAccntErrorMsg"));
                if (errText.equalsIgnoreCase(strErrorText)) {
                    report.updateTestLog("Error Message", "Error Message is displayed successfully as : " + errText, Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("Error Message", "Error Message is not displayed successfully", Status_CRAFT.FAIL);
                }
            } else {
                if (driver.findElement(getObject("xpath~//input[contains(@name,'SELECTALL')]")).isSelected()) {
                    clickJS(getObject("xpath~//input[contains(@name,'SELECTALL')]"), "De Select all accounts");
                }
                if (!driver.findElement(getObject("xpath~//input[contains(@name,'SELECTALL')]")).isSelected()) {
                    List<WebElement> accountList = findElements(getObject("xpath~//div[@class='ecDIB  boi-tile-label-primary boi_label_semibold']"));
                    int aCount = accountList.size();
                    if (aCount > 20) {
                        for (int i = accnt + 1; i <= aCount; i++) {
                            String xpathAccount = "xpath~//div[@id='C5__C1__p1_HEAD_F5DF587E6482FCF3609134_R" + i + "']";
                            String xpathCheckAccount = "xpath~//input[@id='C5__C1__ID_SELECTED_ACCOUNT_0_R" + i + "']";
                            String strFetchAccount = getText(getObject(xpathAccount));
                            if (isElementDisplayed(getObject(xpathCheckAccount), 5)) {
                                clickJS(getObject(xpathCheckAccount), strFetchAccount + " Checkbox");
                                //Thread.sleep(2000);
                                wait(1000);
                                if (i == 21) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!strAccount.equalsIgnoreCase("De Select all;")) {
            clickContinue();
            String errText = getText(getObject("changeAddress.txt20AccntErrorMsg"));
            scrollToView(getObject("changeAddress.txt20AccntErrorMsg"));
            if (errText.equalsIgnoreCase(strErrorText)) {
                report.updateTestLog("Error Message", "Error Message is displayed successfully as : " + errText, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Error Message", "Error Message is not displayed successfully", Status_CRAFT.FAIL);
            }
        }
    }


    public void verifyErrorMessageChangeAddress() throws Exception {
        String strMinChar = "1";
        String strMinChar2 = "e";

        /* Without entering any data and clicking on continue button and capturing error messages */
        isElementDisplayedWithLog(getObject("changeAddress.btnContinue"),
                "Continue","Enter address details page",2);
        if (dataTable.getData("General_Data", "emailToggle").equalsIgnoreCase("Yes")) {
            clickJS(getObject("changeAddress.btnEmailToggle"), "Email toggle button");
        }
        clickContinue();
//        wait(1000);//Thread.sleep(2000);
        String strSelectCountry = getText(getObject("changeAddress.errSelCountry"));
        String strEnterAdd1 = getText(getObject("changeAddress.errAdd1"));
        String strNewEmail = getText(getObject("changeAddress.errNewEmail"));
        String strConfmEmail = getText(getObject("changeAddress.errConfmEmail"));

        isElementDisplayedWithLog(getObject("changeAddress.errSelCountry"),
                strSelectCountry+ "error message","Enter address details",2);
        isElementDisplayedWithLog(getObject("changeAddress.errAdd1"),
                strEnterAdd1+ "error message","Enter address details",2);
        if (dataTable.getData("General_Data", "emailToggle").equals("Yes")) {
            isElementDisplayedWithLog(getObject("changeAddress.errNewEmail"),
                    strNewEmail+ "error message","Enter address details",2);
            isElementDisplayedWithLog(getObject("changeAddress.errConfmEmail"),
                    strConfmEmail+ "error message","Enter address details",2);
        }

        /* Enter 1 char in address line 1, 2, 3 and capture error message for min limit as 2 */
        sendKeys(getObject("changeAddress.edtAddress1"), strMinChar, "Address Line 1");
        sendKeys(getObject("changeAddress.edtAddress2"), strMinChar, "Address Line 2");
        sendKeys(getObject("changeAddress.edtAddress3"), strMinChar2, "Address Line 3");
        clickContinue();
        String strErrMinCharAdd1 = getText(getObject("changeAddress.errInvalAdd1"));
        String strErrMinCharAdd2 = getText(getObject("changeAddress.errInvalAdd2"));
        String strErrMinCharAdd3 = getText(getObject("changeAddress.errInvalAdd3"));

        if (isElementDisplayed(getObject("changeAddress.errInvalAdd1"), 3)) {
            report.updateTestLog("verifyErrorTextforChangeAddress", strErrMinCharAdd1 + "' error message is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyErrorTextforChangeAddress", "Error message is not displayed for min limit char in Address 1", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("changeAddress.errInvalAdd2"), 3)) {
            report.updateTestLog("verifyErrorTextforChangeAddress", strErrMinCharAdd2 + "' error message is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyErrorTextforChangeAddress", "Error message is not displayed for min limit char in Address 2", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("changeAddress.errInvalAdd3"), 3)) {
            report.updateTestLog("verifyErrorTextforChangeAddress", strErrMinCharAdd3 + "' error message is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyErrorTextforChangeAddress", "Error message is not displayed for min limit char in Address 3", Status_CRAFT.FAIL);
        }
        /*
         *Enter invalid character in address line 1, 2, 3 and email and capture error message
         */
        String strinvalidChar = "#$^&%";
        String strinvalidChar2 = "#$^&%#$^&%";
        sendKeys(getObject("changeAddress.edtAddress1"), strinvalidChar, "Address Line 1");
        sendKeys(getObject("changeAddress.edtAddress2"), strinvalidChar, "Address Line 2");
        sendKeys(getObject("changeAddress.edtAddress3"), strinvalidChar, "Address Line 3");
       // click(getObject("changeAddress.btnEmailToggle"), "Email toggle button");
        //clickJS(getObject("changeAddress.btnEmailToggle"), "Email toggle button");
        if(dataTable.getData("General_Data", "emailToggle").equals("Yes")){
            sendKeys(getObject("changeAddress.edtNewEmail"), strinvalidChar, "New Email");
            click(getObject("changeAddress.edtConfirmEmail"), "Confirm Email");
            sendKeys(getObject("changeAddress.edtConfirmEmail"), strinvalidChar2, "Confirm Email");
        }
        clickContinue();
        String strErrInvalAdd1 = getText(getObject("changeAddress.errInvalAdd1"));
        String strErrInvalAdd2 = getText(getObject("changeAddress.errInvalAdd2"));
        String strErrInvalAdd3 = getText(getObject("changeAddress.errInvalAdd3"));
        String strErrInvalNewEmail = getText(getObject("changeAddress.errNewEmail"));
        String strErrInvalConfmEmail = getText(getObject("changeAddress.errConfmEmail"));
        if (isElementDisplayed(getObject("changeAddress.errInvalAdd1"), 3)) {
            report.updateTestLog("verifyErrorTextforChangeAddress", strErrInvalAdd1 + "' error message is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyErrorTextforChangeAddress", "Error message is not displayed for invalid char in Address 1", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("changeAddress.errInvalAdd2"), 3)) {
            report.updateTestLog("verifyErrorTextforChangeAddress", strErrInvalAdd2 + "' error message is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyErrorTextforChangeAddress", "Error message is not displayed for invalid char in Address 2", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("changeAddress.errInvalAdd3"), 3)) {
            report.updateTestLog("verifyErrorTextforChangeAddress", strErrInvalAdd3 + "' error message is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyErrorTextforChangeAddress", "Error message is not displayed for invalid char in Address 3", Status_CRAFT.FAIL);
        }
        if (dataTable.getData("General_Data", "emailToggle").equals("Yes")) {
            if (isElementDisplayed(getObject("changeAddress.errNewEmail"), 3)) {
                report.updateTestLog("verifyErrorTextforChangeAddress", strErrInvalNewEmail + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforChangeAddress", "Error message for invalid char in new email field is not displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("changeAddress.errConfmEmail"), 3)) {
                report.updateTestLog("verifyErrorTextforChangeAddress", strErrInvalConfmEmail + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforChangeAddress", "Error message for invalid char in confirm email field is not displayed", Status_CRAFT.FAIL);
            }
        }
    }
    public void verifyChangeAddressDetails() throws Exception {
        //To verify Change Address title, back button,Select Accounts title and 4 easy steps message
        waitForElementPresent(getObject(deviceType() + "ChangeAdd.PageTitle"), 5);
        if ((getText(getObject(deviceType() + "ChangeAdd.PageTitle")).equalsIgnoreCase("Change address")) &&
                (getText(getObject(deviceType() + "ChangeAdd.SelectAccounts")).equalsIgnoreCase("Select Accounts")) &&
                (isElementDisplayed(getObject(deviceType() + "ChangeAdd.backButton"), 2)) &&
                (isElementDisplayed(getObject(deviceType() + "ChangeAdd.EasyStepstooltip"), 2)) &&
                (getText(getObject(deviceType() + "ChangeAdd.msg")).equalsIgnoreCase("Change your address in")) &&
                (getText(getObject(deviceType() + "ChangeAdd.EasyStepsmsg")).equalsIgnoreCase("4 easy steps"))) {
            report.updateTestLog("verifyChangeAddressDetails", "All the Details are verified", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyChangeAddressDetails", "All the Details are not verified", Status_CRAFT.FAIL);
        }
    }

    public void verifyFooterText() throws Exception {
        String message = "If your mobile Number is not registered for 365 online please contact us on 0818 365 365";
        String pageTxt = getText(getObject("changeAddress.pagetxt"));
        if (pageTxt.contains(message)) {
            report.updateTestLog("verifyFooter", "footer text " + message + " is present", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyFooter", "footer text " + message + " is not present", Status_CRAFT.PASS);
        }

    }

    public void verifyXonChangeAddressDetails() throws InterruptedException {
        if (isElementDisplayed(getObject("changeAddress.edtAddress1"), 3)) {
            sendKeys(getObject("changeAddress.edtAddress1"), "abcd", "Address Line 1");
            clickJS(getObject("changeAddress.lblAddress1X"), "X-Clear Field");
        } else {
            report.updateTestLog("Address Line 1", "'Address Line' text box is not displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("changeAddress.edtAddress2"), 3)) {
            sendKeys(getObject("changeAddress.edtAddress2"), "xyz", "Address Line 2");
            clickJS(getObject("changeAddress.lblAddress2X"), "X-Clear Field");
        } else {
            report.updateTestLog("Address Line 2", "'Address Line' text box is not displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("changeAddress.edtAddress3"), 3)) {
            sendKeys(getObject("changeAddress.edtAddress3"), "pqr", "Address Line 3");
            clickJS(getObject("changeAddress.lblAddress3X"), "X-Clear Field");
        } else {
            report.updateTestLog("Address Line 3", "'Address Line' text box is not displayed", Status_CRAFT.FAIL);
        }
        String strToggleMailcheck = dataTable.getData("General_Data", "emailToggle");
        if (strToggleMailcheck.equalsIgnoreCase("Yes")) {
            if (isElementDisplayed(getObject("changeAddress.btnEmailToggle"), 3)) {
                clickJS(getObject("changeAddress.btnEmailToggle"), "Email toggle button");
                Thread.sleep(2000);
                if (isElementDisplayed(getObject("changeAddress.edtNewEmail"), 3)) {
                    sendKeys(getObject("changeAddress.edtNewEmail"), "a@b.com", "New Email");
                    clickJS(getObject("changeAddress.edtNewEmailX"), "X-Clear Field");
                    if (isElementDisplayed(getObject("changeAddress.edtConfirmEmail"), 3)) {
                        Thread.sleep(2000);
                        sendKeys(getObject("changeAddress.edtConfirmEmail"), "a@b.com", "Confirm Email");
                        clickJS(getObject("changeAddress.edtConfirmEmailX"), "X-ClearField");
                        Thread.sleep(2000);
                        clickJS(getObject("changeAddress.btnEmailToggle"), "Email toggle button");
                        Thread.sleep(1000);
                    }
                }
            }
        } else {
            report.updateTestLog("Email toggle button", "'Email toggle button' is not displayed", Status_CRAFT.PASS);
        }

    }

    public void verifyReviewPageOrder() throws Exception {
        Thread.sleep(2000);
        String ExpSequence = dataTable.getData("General_Data", "ReviewPageSeqType");
        String[] arrExpectedOrdering = ExpSequence.split(";");
        String parentobject = "changeAddress.parentReviewPage";
        List<WebElement> oUIRows = driver.findElements(getObject(parentobject));
        for (int i = 0; i < arrExpectedOrdering.length; i++) {
            // Initailizing and resetting the endofIndex value
            int endOfIndex = 0;
            int elementFoundIndex = 0;
            //Check Grouping and Sequencing
            for (int j = 0; j < oUIRows.size(); j++) {
                if (oUIRows.get(j).getText().contains(arrExpectedOrdering[i])) {
                    if (endOfIndex == 0) {
                        elementFoundIndex = j;
                        report.updateTestLog("verifyReviewPageOrder", arrExpectedOrdering[i] + " :: On Review page element Found at position :: " + (j + 1), Status_CRAFT.PASS);
                    } else if (endOfIndex < j) {
                        report.updateTestLog("verifyReviewPageOrder", "For element : " + arrExpectedOrdering[i] + " :: Last position found was : " + endOfIndex + ": Though it found at position :" + (j + 1), Status_CRAFT.FAIL);
                    }
                } else {
                    if (elementFoundIndex > 0) {
                        endOfIndex = elementFoundIndex;
                    }
                }
            }
        }
    }


    /**
     * Function to select one default account for smoke pack
     **/
    public void selectDefaultAccountsChangeAddress() throws Exception {

        if (driver.findElement(getObject("xpath~//input[contains(@name,'SELECTALL')]")).isSelected()) {
            clickJS(getObject("xpath~//input[contains(@name,'SELECTALL')]"), "DeSelected all accounts");
        }
        String xpathAccount = "xpath~//div[@id='C6__C1__p1_HEAD_F5DF587E6482FCF3609134_R1']";
        String xpathCheckAccount = "xpath~//input[@id='C6__C1__ID_SELECTED_ACCOUNT_0_R1']";
        scriptHelper.commonData.defaultAccountName = getText(getObject(xpathAccount));
        clickJS(getObject(xpathCheckAccount), scriptHelper.commonData.defaultAccountName + " Checkbox");
        clickContinue();
    }

    public void ChangeAdressPIN() throws Exception {

        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("changeAddress.btnChangeAddressPin"));
        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                Thread.sleep(2000);
            }
        }
        report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.PASS);
    }

    public void clickSCAConfirm() throws Exception {

        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject(deviceType() + "withdrawFunds.btnConfirm")));
            clickJS(getObject(deviceType() + "withdrawFunds.btnConfirm"), "Confirm ");
        } else if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "withdrawFunds.btnConfirm"), 7)) {
                click(getObject(deviceType() + "withdrawFunds.btnConfirm"), "Confirm");
                Thread.sleep(2000);
            }
        }

    }

    /**
     * Function to Device list (SCA)
     */
    public void verifyDeviceListSCA() throws Exception {
        if (isElementDisplayed(getObject("launch.deviceListPage"), 5)) {
//            int x = driver.findElement(By.xpath("//fieldset[@id='C2__C1__FS_QUE_C67AAE282C16C109639843_R1']")).getLocation().getX();
//            int y = driver.findElement(By.xpath("//fieldset[@id='C2__C1__FS_QUE_C67AAE282C16C109639843_R1']")).getLocation().getY();
//            Actions a = new Actions((WebDriver) driver.getWebDriver());
//            WebElement elm1 = driver.findElement(By.xpath("//label[@for='C2__C1__QUE_C67AAE282C16C109639843_0_R1']"));
//            a.moveToElement(elm1).contextClick().build().perform();
            Robot robot = new Robot(); //java.awt.Robot
            robot.mouseMove(730, 169); // moves your cursor to the supplied absolute position on the screen
            //robot.mousePress(InputEvent.BUTTON1_MASK); //execute mouse click
            //robot.mouseRelease(InputEvent.BUTTON1_MASK); //java.awt.event.InputEvent
            // driver.findElement(By.xpath("//fieldset[@id='C2__C1__FS_QUE_C67AAE282C16C109639843_R1']")).click();
            report.updateTestLog("DeviceListPage", " Device selected from list successfully", Status_CRAFT.PASS);
            click(getObject("launch.btnContinue_deviceList"));
        } else {
            report.updateTestLog("DeviceListPage", " Device is not selected from list successfully", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to verify reference and timestamp present on screen
     */
    public void verifyreferencetimestamp() throws Exception {
        Thread.sleep(4000);
        HomePage homePage = new HomePage(scriptHelper);
        homePage.ScrollToVisibleJS("changeAddress.reference");
        if (isElementDisplayed(getObject("changeAddress.reference"), 5) &&
                isElementDisplayed(getObject("changeAddress.time"), 5)) {

            report.updateTestLog("Referencetimestamp", "Reference and Time Stamp are displayed on screen", Status_CRAFT.PASS);

        }
        else
        {
            report.updateTestLog("Referencetimestamp", "Reference and Time Stamp are not displayed on screen", Status_CRAFT.FAIL);
        }

        isElementDisplayedWithLog(getObject("changeaddress.confirmationtext"), " 'Request sent' Message", "SEPA Direct Debit Services Confirmation", 10);
        String strMessagecheck1 = "You'll receive an SMS within 5 working days once we've completed the change. Details of this request are saved in your sent messages in Services.";
        String strMessagecheck2 = "If you're changing address on a joint account, the other account holder will be notified of the request.";
        String strMessagetext1 = getText(getObject("xpath~//p[@class='boi_para'][1]"));
        String strMessagetext2 = getText(getObject("xpath~//p[@class='boi_para'][2]"));
        if (strMessagetext1.equalsIgnoreCase(strMessagecheck1) && strMessagetext2.equalsIgnoreCase(strMessagecheck2)) {
            report.updateTestLog("verifyDDConfirmationPage", "Confirmation Message is displayed successfully.Confirmation Message:" + strMessagecheck1.concat(strMessagecheck2), Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyDDConfirmationPage", "Confirmation Message is NOT displayed successfully.Confirmation Message: " + strMessagecheck1.concat(strMessagecheck2), Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DirectDebit.btnBacktoHome"), 5)) {
            isElementDisplayedWithLog(getObject("changeAddress.reference"),"Reference Number","Reference number is dispalyed",5);
            Referencenumber = getText(getObject("changeAddress.reference"),10);
            isElementDisplayedWithLog(getObject("changeAddress.time"),"Time of Request","Time of request is dispalyed",5);
            TimeofRequest = getText(getObject("changeAddress.time"),5);
            clickJS(getObject("DirectDebit.btnBacktoHome"), "Back to Home");
            Thread.sleep(2000);
            report.updateTestLog("BacktoHome button ", "BacktoHome button Clicked on confirmation screen", Status_CRAFT.PASS);
        }
        else{
            report.updateTestLog("BacktoHome button ", "BacktoHome button not Clicked on confirmation screen", Status_CRAFT.FAIL);
        }
    }

    public void modifyCheckboxes() throws Exception {

        String strerror = "You can only update the address on a maximum of 20 accounts at a time. If you want to update it on more accounts, please submit a separate request.";

        if (isElementDisplayed(getObject("changeAddress.checkboxes"), 10)) {
            waitForJQueryLoad();
            waitForPageLoaded();
            List<WebElement> allLinks = findElements(getObject("changeAddress.checkboxes"));
            int a = allLinks.size();
            if (allLinks.size() <= 20) {
                clickContinue();
            }
            if (allLinks.size() > 20) {

                if (isElementDisplayed(getObject("changeAddress.accounterror"), 3)
                        && ((getText(getObject("changeAddress.accounterror")).equalsIgnoreCase(strerror)))) {
                    report.updateTestLog("modifyCheckboxes", "Correct error message is displayed for more than 20 accounts", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("modifyCheckboxes", "Error message is not displayed", Status_CRAFT.PASS);
                }

                String xpathCheckAccount =null;
                for (int i = allLinks.size(); i >= 20; i--) {

                    if(deviceType().equalsIgnoreCase("w.")){
                        xpathCheckAccount = "xpath~//input[@id='C5__C1__ID_SELECTED_ACCOUNT_R" + i + "']";}
                    else{
                        xpathCheckAccount = "xpath~//input[@id='C5__C1__ID_SELECTED_ACCOUNT_0_R" + i + "']";
                    }
                    // String strFetchAccount = getText(getObject(xpathAccount));

                   clickJS(getObject(xpathCheckAccount), " Checkbox deselected");
                    Thread.sleep(1000);


                }
                clickContinue();
                Thread.sleep(3000);
            }
        } else {
            report.updateTestLog("modifyCheckboxes", "Accounts are not displayed for selection", Status_CRAFT.FAIL);
        }
    }

    public void verifyAccountNotListedLinks() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Label");
        if ((strAccountToCheck.equalsIgnoreCase("ROI"))) {

            if (isElementDisplayed(getObject("changeAddress.addAcountLink"), 10)) {
                report.updateTestLog("verifyAccountNotListedLinks", "Add Account and policy link present on screen ", Status_CRAFT.PASS);
                click(getObject("changeAddress.addAcountLink"), "Add Account and Policy link");

                if (isElementDisplayed(getObject("changeAddress.AddAcountPage"), 10)){
                    report.updateTestLog("verifyAccountNotListedLinks", "User is navigated to Add Account and policy page", Status_CRAFT.PASS);
                }
            } else {
                report.updateTestLog("verifyAccountNotListedLinks", "Add Account and policy link not present on screen", Status_CRAFT.FAIL);
            }

        } else if ((strAccountToCheck.equalsIgnoreCase("NI"))) {

            if (isElementDisplayed(getObject("changeAddress.ChnageaddressformLink"), 10)) {

                report.updateTestLog("verifyAccountNotListedLinks", "Change Address form link present on screen", Status_CRAFT.PASS);
                click(getObject("changeAddress.ChnageaddressformLink"), "Change address form link");
                HomePage homePage = new HomePage(scriptHelper);
                Thread.sleep(2000);
                //homePage.childWindowhandler();
            } else {
                report.updateTestLog("verifyAccountNotListedLinks", "Change Address form link not present on screen", Status_CRAFT.FAIL);
            }
        }
    }

    public void verifyChangeAddressScreen() throws Exception {
        String strHeader = "Change Address";
        if ((isElementDisplayed(getObject("changeAddress.Header"), 10))
              && ((getText(getObject("changeAddress.Header")).equalsIgnoreCase(strHeader)))){
            report.updateTestLog("verifyChangeAddressScreen", "Change Address header present on screen", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("verifyChangeAddressScreen", "Change Address header not present on screen", Status_CRAFT.FAIL);
        }

        if (!isElementDisplayed(getObject("changeAddress.SelectAll"), 10)){
            report.updateTestLog("verifyChangeAddressScreen", "SelectAll not present on screen", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("verifyChangeAddressScreen", "SelectAll present on screen", Status_CRAFT.FAIL);
        }
        if (!isElementDisplayed(getObject("changeAddress.Changeaddresspopup"), 10)){
            report.updateTestLog("verifyChangeAddressScreen", "Change your address in 3 easy steps not present on screen", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("verifyChangeAddressScreen", "Change your address in 3 easy steps present on screen", Status_CRAFT.FAIL);
        }
    }
    public void selectCountryauto() throws Exception {
        String strCountry = dataTable.getData("General_Data", "selectCountry");
        if (isElementDisplayed(getObject(deviceType() + "changeAddress.lblEnterAddress"), 5)) {
            report.updateTestLog("selectAddressDropdowns", "Change Address screen is displayed successfully", Status_CRAFT.PASS);
            sendKeys(getObject((deviceType() + "changeAddress.inputCountry")), strCountry, "selectCountry");
            Thread.sleep(1000);
            //clickJS(getObject("xpath~//input[contains(@name,'ADDRESSDETAILS[1].COUNTRY')]/following-sibling::ul/li/a[text()='" + strCountry + "']"), "Selected Country is- '" + strCountry + "'");
            WebElement country = driver.findElement(By.xpath("//*['strCountry']"));
            Actions actions = new Actions(driver.getWebDriver());
            actions.moveToElement(country).click().build().perform();
        } else {
            report.updateTestLog("selectAddressDropdowns", "Change Address screen is NOT displayed", Status_CRAFT.FAIL);
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
            JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
            executor.executeScript("arguments[0].click();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(3000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
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
            report.updateTestLog("Services Lozenges","Sent tab is clicked ", Status_CRAFT.PASS);
        } else{
            report.updateTestLog("Services Lozenges", "Sent tab is not clicked ", Status_CRAFT.FAIL);
        }
    }



    public void sentmessagevalidation() throws Exception {
        boolean blnClicked;
        String  s1 =this.Referencenumber;
        String  s2 =this.TimeofRequest;
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
    public void verifyCountry() throws Exception {

        String strCountry = dataTable.getData("General_Data", "selectCountry");

        if (isElementDisplayed(getObject("changeAddress.txtCountry"), 5)) {
            click(getObject("changeAddress.txtCountry"));
            sendKeys(getObject(("changeAddress.txtCountry")), strCountry, "selectCountry");
            click(getObject("xpath~//div[@class='tc-card boi-card-standard tc-card-bg']"));
            report.updateTestLog("Country textbox update", strCountry + "Country is selected", Status_CRAFT.PASS);
        }
        Thread.sleep(1000);


    }


}





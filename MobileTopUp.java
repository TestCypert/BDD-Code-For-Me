package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.List;


/**
 * Created by C101965 on 11/01/2019.
 */
public class MobileTopUp extends WebDriverHelper {

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public MobileTopUp(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

/*   Scroll to view particular  element using JS
    */
public void ScrollToVisibleJS(String linkToVisible) throws InterruptedException {
    try {
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToVisible))));
        Thread.sleep(2000);
        //report.updateTestLog("ScrollAndClickJS", "Element To View", Status_CRAFT.SCREENSHOT);
    } catch (UnreachableBrowserException e) {
        e.printStackTrace();
    } catch (StaleElementReferenceException e) {
        e.printStackTrace();
    }
}

    public void launchPaymentsTab() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.selectPaymentOption();
    }


    public void selectAccountPayFrom() throws Exception {
        String strAccnt = dataTable.getData("General_Data", "Account_Name");
        if (isElementDisplayed(getObject("MTU.lstAccountspayFrom"), 2)) {
            waitForJQueryLoad(); waitForPageLoaded();
            clickJS(getObject("MTU.lstAccountspayFrom"), "Account Dropdown");
            waitForJQueryLoad(); waitForPageLoaded();

            List<WebElement> allLinks = findElements(getObject("MTU.Accountlist"));
            if (allLinks.size() > 1) {
                int intIndex = 1;
                for (WebElement ele : allLinks) {
                    String linkname = ele.getText();
                    if (linkname.equalsIgnoreCase(strAccnt)) {
                        ele.click();
                        report.updateTestLog("selectAccountPayFrom", strAccnt + " is clicked  ", Status_CRAFT.PASS);
                        break;
                    }
                    intIndex = intIndex++;

                }
            }
        }
    }

    public void selectProvider() throws Exception{
        String strProvider = dataTable.getData("General_Data", "Provider_Name");
        String strIrishNo;

       //labels to check
        if (isElementDisplayed(getObject("MTU.lblTopUpProvider"), 3)) {
            report.updateTestLog("Mobile Number ", "Top Up label is displayed", Status_CRAFT.PASS);
            if(getText(getObject("MTU.lblTopUpProvider")).equals("Top ups only available to Vodafone, Three and Eir")){
                report.updateTestLog("Mobile Number ", "Top Up label - details is displayed", Status_CRAFT.PASS);
            }
            else{
                report.updateTestLog("Mobile Number ", "Top Up label- details is not  matching with expected", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("Mobile Number ", "Top Up label is not  displayed", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("MTU.btnProviderSelect"), 3)) {
            if (deviceType == "MobileWeb"){
                waitForPageLoaded();
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//label[contains(@class,'boi-topup__label boi-topup__label__" + strProvider + "')]")));
                ////label[contains(@class,'boi-services-btn boi-topup__label boi-topup__label__THREE')]
                waitForPageLoaded();
                clickJS(getObject("xpath~//label[contains(@class,'boi-topup__label boi-topup__label__" + strProvider + "')]"), "Selected Provider is- '" + strProvider + "'");
                waitForPageLoaded();
                report.updateTestLog("selectProvider", strProvider + " Provider is selected ", Status_CRAFT.PASS);
            }else{
               click(getObject("xpath~//label[contains(@class,'boi-topup__label boi-topup__label__" + strProvider + "')]"));
                report.updateTestLog("selectProvider", strProvider + " Provider is selected ", Status_CRAFT.PASS);
            }

            if (isElementDisplayed(getObject("MTU.txtboxMobileNumber"), 3)) {
                report.updateTestLog("Mobile Number ", "Mobile Number TextBox is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Mobile Number ", "Mobile Number TextBox is not  displayed", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("MTU.txtboxConfirmNumber"), 3)) {
                report.updateTestLog("Confirm Mobile Number ", "Confirm Mobile Number TextBox is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Confirm Mobile Number ", "Confirm Mobile Number TextBox is not  displayed", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("MTU.drdwnlabelAmount"), 3)) {
                report.updateTestLog("Drop Down Amount ", "Amount drop down is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Drop Down Amount ", "Amount drop down is not  displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("selectProvider", " 'Provider is not selected'", Status_CRAFT.FAIL);
        }
    }


    public void selectAmount() throws Exception {
        String strpayAmount = dataTable.getData("General_Data", "Pay_Amount");
        if (isElementDisplayed(getObject("MTU.drdwnlabelAmount"), 3)) {
            waitForPageLoaded();
            driver.findElement(getObject("xpath~//*[contains(@class,'amount_dropdown')]")).click();
            waitForPageLoaded();
            List<WebElement> allLinks = findElements(getObject("xpath~//div[@class='aria_exp_wrapper boi-position-relative']/ul[@class='exp_elem_list_widget list']/li"));
            if (allLinks.size() > 1) {
                int intIndex = 1;
                for (WebElement ele : allLinks) {
                    String linkname = ele.getText();
                    if (linkname.contains(strpayAmount)) {
                        ele.click();
                        report.updateTestLog("selectAmount", strpayAmount + " is clicked  ", Status_CRAFT.PASS);
                        break;
                    }
                    intIndex = intIndex++;
                }
            }
        } else {
            report.updateTestLog("selectAmountPay", " 'amount drop-down is not Selected '", Status_CRAFT.FAIL);
       }
    }


    /**
     * Function to Verify error message when Account balance<Amount to be topped up on Mobile Top Up screen
     */

    public void verifyErrorAccountBalancelessAmount() throws Exception {
        selectAmount();
        clickContinue_MTU();
        //if (isElementDisplayed(getObject("MTU.errAmountless"), 3)) {
            String strErrorMsg_Accountless = getText(getObject("MTU.errAmountless"));
            if (isElementDisplayed(getObject("MTU.errAmountless"), 3)) {
                report.updateTestLog("verifyErrorAccountBalanceless", "Error msg is displayed as : " + strErrorMsg_Accountless, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorAccountBalanceless", "Error message is not displayed ", Status_CRAFT.FAIL);
            }
        }

    /**
     * Function to Enter Mobile number on Mobile Top Up screen
     */


    public void enterPhoneNumber() throws Exception {
        String strPhone = dataTable.getData("General_Data", "MobileNumber");
               if (isElementDisplayed(getObject("MTU.txtboxMobileNumber"), 3)) {
                   waitForPageLoaded();
                   sendKeys(getObject("MTU.txtboxMobileNumber"), strPhone, "Mobile phone number Entered");
                   waitForJQueryLoad();
                   waitForPageLoaded();
               } else {
            report.updateTestLog("MobileNumber", "'Phone' text box is not displayed", Status_CRAFT.FAIL);
        }
    }
    public void enterConfirmNumber() throws Exception {
        String strConfirmPhone = dataTable.getData("General_Data", "ConfirmNumber");
        if (isElementDisplayed(getObject("MTU.txtboxConfirmNumber"), 5)) {
            clickJS(getObject("MTU.txtboxConfirmNumber"), "ConfirmNumber");
            waitForJQueryLoad();
            waitForPageLoaded();
            sendKeysJS(getObject("MTU.txtboxConfirmNumber"), strConfirmPhone, "Confirm number Entered");
            waitForJQueryLoad();
            waitForPageLoaded();
        } else {
            report.updateTestLog("ConfirmNumber", "'Confirm Number' text box is not displayed", Status_CRAFT.FAIL);
        }
    }
    /**
     * Function to verify Important information on Mobile Top Up screen
     */
    public void textImportantInformation() throws Exception{
        String strImpInfo = dataTable.getData("General_Data", "VerifyContent");
        String strImpInfoText = getText(getObject(deviceType() + "MTU.txtImpInfoText")).replace("\n","");
        if (strImpInfo.equalsIgnoreCase(strImpInfoText)){
            report.updateTestLog("Important Information Text  ", "Important Information Text is displayed as " + strImpInfoText, Status_CRAFT.PASS);
            waitForPageLoaded();
        } else {
            report.updateTestLog("Important Information Text box ", "Important Information Text is not as expected", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to Enter Invalid Mobile number on Mobile Top Up screen
     */


    public void enterInvalidPhoneNumber() throws Exception {
        String strPhone = dataTable.getData("General_Data", "InvalidMobileNumber");
        if (isElementDisplayed(getObject("MTU.txtboxMobileNumber"), 3))
        {
            sendKeys(getObject("MTU.txtboxMobileNumber"), strPhone, "Mobile phone number Entered");
            clickJS(getObject("MTU.txtboxConfirmNumber"),"Confirm Phone number ");
            String strErrorMsg_InvalidPhone = getText(getObject("MTU.errorMsg_Invalid_PhoneNumber"));
            if(isElementDisplayed(getObject("MTU.errorMsg_Invalid_PhoneNumber"), 3))
            {
                report.updateTestLog("enterInvalidPhoneNumber", "Error msg is displayed as : " + strErrorMsg_InvalidPhone, Status_CRAFT.PASS);
            }
        else {
                report.updateTestLog("enterInvalidPhoneNumber", "Error message is not displayed ", Status_CRAFT.FAIL);
            }
  }
        else
        {
            report.updateTestLog("enterInvalidPhoneNumber", "'Phone' text box is not displayed", Status_CRAFT.FAIL);
        }

        Thread.sleep(2000);
    }

    /**
     * Function to Enter Ivalid Confirm Mobile number on Mobile Top Up screen
     */

    public void enterInvalidConfirmNumber() throws Exception {
        String strPhone = dataTable.getData("General_Data", "InvalidConfirmNumber");
        if (isElementDisplayed(getObject("MTU.txtboxConfirmNumber"), 3)) {
            sendKeys(getObject("MTU.txtboxConfirmNumber"), strPhone, "Confirm phone number Entered");
            click(getObject("MTU.btnContinue"), "");
            String strErrorMsg_InvalidConfirmPhn = getText(getObject("MTU.errorMsg_Invalid_ConfirmNumber"));
            if (isElementDisplayed(getObject("MTU.errorMsg_Invalid_ConfirmNumber"), 3)) {
                report.updateTestLog("enterInvalidConfirmNumber", "Error msg is displayed as : " + strErrorMsg_InvalidConfirmPhn, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("enterInvalidConfirmNumber", "Error message is not displayed ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("enterInvalidPhoneNumber", "'Phone' text box is not displayed", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to Enter Mismatched Confirm Mobile number on Mobile Top Up screen
     */

    public void enterMismatchConfirmNumber() throws Exception {
        String strPhone = dataTable.getData("General_Data", "MismatchConfirmNumber");
        if (isElementDisplayed(getObject("MTU.txtboxConfirmNumber"), 3)) {
            click(getObject("MTU.txtboxConfirmNumber"),"");
            Thread.sleep(3000);
            sendKeys(getObject("MTU.txtboxConfirmNumber"), strPhone, "Confirm phone number Entered");
            click(getObject("MTU.btnContinue"), "");
            String strErrorMsg_MismatchConfirmPhn = getText(getObject("MTU.errorMsg_mismatch_ConfirmNuumber"));
            if (isElementDisplayed(getObject("MTU.errorMsg_mismatch_ConfirmNuumber"), 3)) {
                report.updateTestLog("enterMismatchConfirmNumber", "Error msg is displayed as : " + strErrorMsg_MismatchConfirmPhn, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("enterMismatchConfirmNumber", "Error message is not displayed ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("enterMismatchConfirmNumber", "'Phone' text box is not displayed", Status_CRAFT.FAIL);
        }
    }
    /**
     * Function to clear Phone fields on Mobile Top Up screen
     */

    public void clearPhonefields() throws Exception {
        waitForElementPresent(getObject("MTU.btn_Clear_PhoneNumber"), 5);
        waitForElementPresent(getObject("MTU.btn_Clear_ConfirmNumber"), 5);
        if (isElementDisplayed(getObject("MTU.btn_Clear_PhoneNumber"), 5)) {
            click(getObject("MTU.btn_Clear_PhoneNumber"));
            click(getObject("MTU.btn_Clear_ConfirmNumber"));
            report.updateTestLog("clearPhonefields", "X is displayed and clicked on Phone number box", Status_CRAFT.PASS);
            report.updateTestLog("clearPhonefields", "X is displayed and clicked on Confirm number box", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("clearPhonefields", "X is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to verify all the error validation for mobile fields on Mobile Top Up screen
     */

    public void verifyPhoneValidations() throws Exception {
        clickContinue_MTU();
        waitForElementPresent(getObject("MTU.errorMsg_Blank_PhoneNumber"), 5);
        waitForElementPresent(getObject("MTU.errorMsg_Blank_ConfirmNumber"), 5);
        if (isElementDisplayed(getObject("MTU.errorMsg_Blank_PhoneNumber"), 5) &&
                isElementDisplayed(getObject("MTU.errorMsg_Blank_ConfirmNumber"), 5)) {
            String strErrorMsg_BlankPhone = getText(getObject("MTU.errorMsg_Blank_PhoneNumber"));
            String strErrorMsg_BlankConfirmPhone = getText(getObject("MTU.errorMsg_Blank_ConfirmNumber"));
            report.updateTestLog("enterInvalidPhoneNumber", "Error msg is displayed as : " + strErrorMsg_BlankPhone, Status_CRAFT.PASS);
            report.updateTestLog("enterInvalidPhoneNumber", "Error msg is displayed as : " + strErrorMsg_BlankConfirmPhone, Status_CRAFT.PASS);
            enterInvalidPhoneNumber();
            enterInvalidConfirmNumber();
            clearPhonefields();
            enterPhoneNumber();
            enterMismatchConfirmNumber();
            clearPhonefields();
            enterPhoneNumber();
            enterConfirmNumber();

        }

    }



    /**
     * Function to click on continue button on Mobile Top Up screen
     */
    public void clickContinue_MTU() throws Exception {
        if (isElementDisplayed(getObject("MTU.btnContinue"), 7)) {
            Thread.sleep(2000);
            clickJS(getObject("MTU.btnContinue"), "Continue");
            Thread.sleep(2000);
        }
    }

    /**
     * Function to check that only valid accounts are displayed in accounts dropdown in MTU
     */
    public void validAccounts_MTU() throws Exception {

        String[] arrValidation = dataTable.getData("General_Data", "VerifyDetails").split(";");
        if (isElementDisplayed(getObject("MTU.lstAccountspayFrom"), 2)) {
            Thread.sleep(1000);
            clickJS(getObject("MTU.lstAccountspayFrom"),"DropDown Account");
            Thread.sleep(2000);
            List<WebElement> payfrmAccntList = findElements(getObject(deviceType()+"MTU.lstArryPayfrom"));
            for (int i = 1; i <= payfrmAccntList.size(); i++) {
                String strPayfromAccount = getText(getObject("xpath~//select[@id='C6__QUE_04CEAA34BD3248B5676617']" +
                        "/following-sibling::div/ul/li" + "[" + i + "]"));

                for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                    String strValidate = arrValidation[intValidate];
                    if(deviceType().equals("mw.")){
                        strPayfromAccount = getText(getObject("xpath~//div[contains(@id,'C5__QUE_04CEAA34BD3248B5676617')]" +
                                "/descendant::ul/li" + "[" + i + "]"));
                        i=i+1;
                    }
                    if (strPayfromAccount.contains(strValidate)) {
                        report.updateTestLog("validAccounts_MTU", "'Only valid accounts are displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("validAccounts_MTU", "'Unexpected accounts are displayed", Status_CRAFT.FAIL);
                    }


                }
            }
            Thread.sleep(2000);
        }
    }
    /**
     * Function to check that Invalid accounts are displayed in pay from accounts dropdown in MTU
     */
    public void PayfromInvalidAccounts_MTU() throws Exception {

        String[] arrValidation = dataTable.getData("General_Data", "VerifyDetails").split(";");
        if (isElementDisplayed(getObject("MTU.lstAccountspayFrom"), 2)) {
            Thread.sleep(1000);
            clickJS(getObject("MTU.lstAccountspayFrom"),"DropDown Account");
            Thread.sleep(2000);
            int accno = 0;
            List<WebElement> payfrmAccntList = findElements(getObject(deviceType()+"MTU.lstArryPayfrom"));
            for (int i = 1; i <= payfrmAccntList.size(); i++) {
                String strPayfromAccount = getText(getObject("xpath~//select[@id='C6__QUE_04CEAA34BD3248B5676617']" +
                        "/following-sibling::div/ul/li" + "[" + i + "]"));
                if(deviceType().equals("mw.")){
                    strPayfromAccount = getText(getObject("xpath~//ul[contains(@id,'C5__QUE_8B184A636AF46252381255')]" +
                            "/descendant::li" + "[" + i + "]"));

                }
                for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                    String strValidate = arrValidation[intValidate];

                    if (strPayfromAccount.contains(strValidate)) {
                        report.updateTestLog("validAccounts_MTU", "'Unexpected accounts are displayed", Status_CRAFT.FAIL);
                        break;
                    }
                    accno = intValidate + 1;
                }

                if (accno < arrValidation.length) {
                    break;
                }
            }
            if (accno >= arrValidation.length) {
                report.updateTestLog("validAccounts_MTU", "'Only valid accounts are displayed", Status_CRAFT.PASS);
            }
            Thread.sleep(2000);
        }
    }

    /**
     * Function to check that Invalid accounts are displayed in pay from accounts dropdown in MTU
     */
    public void PayToInvalidAccounts_MTU() throws Exception {

        String[] arrValidation = dataTable.getData("General_Data", "VerifyDetails").split(";");
        if (isElementDisplayed(getObject(deviceType()+"sendMoney.lstToFrom"), 2)) {
            Thread.sleep(1000);
            clickJS(getObject(deviceType()+"sendMoney.lstToFrom"),"DropDown Account");
            Thread.sleep(2000);
            int accno = 0;
            List<WebElement> payfrmAccntList = findElements(getObject(deviceType()+"SendMoney.lstPayToPayee"));
            for (int i = 1; i <= payfrmAccntList.size(); i++) {
                String strPayfromAccount = getText(getObject("xpath~//select[@id='C6__QUE_04CEAA34BD3248B5676617']" +
                        "/following-sibling::div/ul/li" + "[" + i + "]"));
                if(deviceType().equals("mw.")){
                    strPayfromAccount = getText(getObject("xpath~//ul[contains(@id,'C5__QUE_8B184A636AF46252381255')]" +
                            "/descendant::li" + "[" + i + "]"));

                }
                for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                    String strValidate = arrValidation[intValidate];

                    if (strPayfromAccount.contains(strValidate)) {
                        report.updateTestLog("validAccounts_MTU", "'Unexpected accounts are displayed", Status_CRAFT.FAIL);
                        break;
                    }
                    accno = intValidate + 1;
                }

                if (accno < arrValidation.length) {
                    break;
                }
            }
            if (accno >= arrValidation.length) {
                report.updateTestLog("validAccounts_MTU", "'Only valid accounts are displayed", Status_CRAFT.PASS);
            }
            Thread.sleep(2000);
        }
    }

 /**
     * Function for Review Screen of  Mobile Top Up screen
     */
    public void MobileTopUpReview() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        Thread.sleep(2000);
        homePage.verifyElementDetails("MTU.MobileTopUpReview");
        Thread.sleep(2000);
    }
    /**
     * Function for Confirmation screen of  Mobile Top Up screen
     */
    public void requestSentValidation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        Thread.sleep(2000);
        homePage.verifyElementDetails("MTU.MobileTopUpConfirmation");
        Thread.sleep(1000);
        if(isElementDisplayed(getObject("MTU.lnkPrint"),3)){
            String SelectPrint = getText(getObject("MTU.lnkPrint"));
            report.updateTestLog("Print link", SelectPrint +" is displayed successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Print link", " is not displayed successfully", Status_CRAFT.FAIL);
        }
        if(isElementDisplayed(getObject("MTU.btnBacktoPayments"),5)){
            clickJS(getObject("MTU.btnBacktoPayments"),"Back to Payments");
            Thread.sleep(2000);
        }
    }
    /**
     * Common function to verify details on Mobile Top up  screen
     */
    public void verifyDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (scriptHelper.commonData.iterationFlag != true) {
            homePage.verifyElementDetails("MTU.MobileTopUpReview");
            scriptHelper.commonData.iterationFlag = true;
        } else {
            if (deviceType().equals("Web")) {
                homePage.verifyElementDetails("MTU.RequestSent");
                if (isElementDisplayed(getObject("MTU.lnkPrint"), 3)) {
                    String SelectPrint = getText(getObject("MTU.lnkPrint"));
                    report.updateTestLog("Print link", SelectPrint + " is displayed successfully", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("Print link", " is not displayed successfully", Status_CRAFT.FAIL);
                }
                if (isElementDisplayed(getObject("MTU.btnBacktoPayments"), 5)) {
                    clickJS(getObject("MTU.btnBacktoPayments"), "Back to Payments");
                    Thread.sleep(2000);
                }
            }
            else {
                homePage.verifyElementDetails("MTU.RequestSent");
                if (isElementDisplayed(getObject("MTU.btnBacktoPayments"), 5)) {
                    clickJS(getObject("MTU.btnBacktoPayments"), "Back to Payments");
                    Thread.sleep(2000);
                }
            }
        }
    }

    public void verifyMobileTopUpReviewPage() throws Exception{
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails("MTU.MobileTopUpReview");
        clickContinue_MTU();
    }

    public void mobileTopUpPIN() throws Exception {

        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("withdrawFunds.btnPin"));
        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                Thread.sleep(2000);
            }
        }
        report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.DONE);
        //clickJS(getObject("launch.btnConfirm"), "Confirm button");
        clickConfirm();


    }

    public void clickConfirm() throws Exception {

        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject(deviceType() + "withdrawFunds.btnConfirm")));
            clickJS(getObject(deviceType() + "withdrawFunds.btnConfirm"), "Confirm ");
        }
        else if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "withdrawFunds.btnConfirm"), 7)) {
                click(getObject(deviceType() + "withdrawFunds.btnConfirm"), "Confirm");
                Thread.sleep(2000);
            }
        }

    }

    public void MobileTopUpSuccessScreen() throws Exception {

        Thread.sleep(5000);
        HomePage homePage = new HomePage(scriptHelper);
        verifySuccessMessage();

        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("withdrawFunds.lnkPrint"), 3)) {
                String SelectPrint = getText(getObject("withdrawFunds.lnkPrint"));
                report.updateTestLog("Print link", SelectPrint + " is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Print link", " is not displayed successfully", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject(deviceType() + "MTU.btnBacktoPayments"), 7)) {
                click(getObject(deviceType() + "MTU.btnBacktoPayments"), "Back to Payments");
                waitForPageLoaded();
            }
        } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject(deviceType() + "MTU.btnBacktoPayments")));
            clickJS(getObject(deviceType() + "MTU.btnBacktoPayments"), "Back to Payments ");
            report.updateTestLog("Success page Mobile"," is displayed successfully", Status_CRAFT.PASS);
        }
        else{
            report.updateTestLog("Success page","SUCCESS page not is displayed successfully", Status_CRAFT.FAIL);
        }
    }

	public void MobileTopUpSuccessScreenBackToHome() throws Exception {

            Thread.sleep(5000);
           verifySuccessMessage();

        if (deviceType.equalsIgnoreCase("Web")) {
        	 if (isElementDisplayed(getObject("withdrawFunds.lnkPrint"), 3)) {
                 String SelectPrint = getText(getObject("withdrawFunds.lnkPrint"));
                 report.updateTestLog("Print link", SelectPrint + " is displayed successfully", Status_CRAFT.PASS);
             } else {
                 report.updateTestLog("Print link", " is not displayed successfully", Status_CRAFT.FAIL);
             }
            if (isElementDisplayed(getObject(deviceType() + "MTU.btnBacktoHome"), 7)) {
                click(getObject(deviceType() + "MTU.btnBacktoHome"), "Back to home");
                waitForPageLoaded();
            }

        } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject(deviceType() + "MTU.btnBacktoHome")));
            clickJS(getObject(deviceType() + "MTU.btnBacktoHome"), "Back to home");

            report.updateTestLog("Success page Mobile"," is displayed successfully", Status_CRAFT.PASS);


        }
        else{
            report.updateTestLog("Success page","SUCCESS page not is displayed successfully", Status_CRAFT.FAIL);
        }
    }


   /* created a separate method to verify mobile top up success screen message as the below code is common for two methods*/
    private void verifySuccessMessage() throws Exception{

        if (isElementDisplayed(getObject("withdrawFunds.successmsg"), 3)){
            String SuccessMsg = getText(getObject("withdrawFunds.successmsg"));
            if (SuccessMsg.equalsIgnoreCase("Success")) {
                report.updateTestLog("VerifySuccessMessage", "Success message is displayed correctly", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifySuccessMessage", "Success message is not displayed correctly", Status_CRAFT.FAIL);
            }
        }

        if (isElementDisplayed(getObject("MTU.SuccessMessage"), 3)){
            String ImpInformMsg = getText(getObject("MTU.SuccessMessage"));
            if (ImpInformMsg.equalsIgnoreCase("Please allow up to 30 minutes for the mobile to be topped up.")) {
                report.updateTestLog("VerifyInfoMessage", "Success update  message is displayed correctly", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyInfoMessage", "Success update message is displayed correctly", Status_CRAFT.FAIL);
            }
        }

    }

}





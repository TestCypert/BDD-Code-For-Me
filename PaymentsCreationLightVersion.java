package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by C966003 on 15/11/2019.
 */

public class PaymentsCreationLightVersion extends WebDriverHelper {
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public PaymentsCreationLightVersion(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function : To select payment method
     * Created by : C966003
     * Update on    Updated by     Reason
     * 15/11/2019   C966003        Newly created
     */
    public void selectPaymentOption_Light() throws Exception {
        scriptHelper.commonData.watch.start();
        String strPaymentType = dataTable.getData("General_Data", "PaymentType");
        while (isElementDisplayed(getObject("xpath~/[@class='spinner']"), 4)) {
            waitForPageLoaded();
        }
        if (deviceType.equalsIgnoreCase("Web")) {
            click(getObject(deviceType() + "home.tabPayments"), "'Payments Tab'");
            if (strPaymentType.equalsIgnoreCase("Transfer between my accounts") || strPaymentType.equalsIgnoreCase("Send money or pay a bill")) {
                clickJS(getObject("xpath~//*[contains(text(),'" + strPaymentType + "')]"), "The'" + strPaymentType + "'");
                report.updateTestLog("selectPaymentOption", "'" + strPaymentType + "' Payment type selected on Home Page", Status_CRAFT.PASS);
            } else {
                String myxpath = "//a/descendant::*[text()='" + strPaymentType + "']";
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(myxpath)));
                click(getObject("xpath~" + myxpath));
            }
            report.updateTestLog("selectPaymentOption", "'" + strPaymentType + "' Payment type selected on Home Page", Status_CRAFT.PASS);
        } else {
            waitForElementToClickable(getObject(deviceType() + "home.tabPayments"), 5);
            clickJS(getObject(deviceType() + "home.tabPayments"), "'Payments Tab'");
            if (strPaymentType.equalsIgnoreCase("Transfer between my accounts") ||
                    strPaymentType.equalsIgnoreCase("Send money or pay a bill")) {
                clickJS(getObject("xpath~//*[contains(text(),'" + strPaymentType + "')]"), "Click '" + strPaymentType + "'");
            } else {
                clickJS(getObject("xpath~//*[text()='" + strPaymentType + "']"), "Click '" + strPaymentType + "'");
                report.updateTestLog("selectPaymentOption", "'" + strPaymentType + "' Payment type selected on Home Page", Status_CRAFT.PASS);
            }
        }
//        getFullScreenshot("Payment");
    }

    /**
     * Function : To select account from PayFrom account list in send money journey
     * Created by : C966003
     * Update on    Updated by     Reason
     * 15/11/2019   C966003        Newly created
     */
    public void payFrom_SendMoney_Light() throws Exception {
        scriptHelper.commonData.strPayFromAccount = dataTable.getData("General_Data", "PayFrom_Account");
        clickJS(getObject("SendMoney.lstSelectAccount"),"Select account");
        clickJS(getObject("xpath~//li[contains(text(),\"" + scriptHelper.commonData.strPayFromAccount.split("~")[1] + "\")]"),"Pay from account : "+scriptHelper.commonData.strPayFromAccount);
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
    }

    /**
     * Function : To select account from Pay To account list in send money journey
     * Created by : C966003
     * Update on    Updated by     Reason
     * 15/11/2019   C966003        Newly created
     */
    public void payTo_SendMoney_Light() throws Exception {
        scriptHelper.commonData.strPayToAccount = dataTable.getData("General_Data", "PayTo_Account");
        String strPayToAccount = scriptHelper.commonData.strPayToAccount;
//        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
//        payments.selectPayToSendMoney();

        if (!deviceType().equalsIgnoreCase("mw.")) {
            while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
                waitForPageLoaded();
            }
            if(isElementDisplayed(getObject("sendMoney.lstToFrom"),2)) {
                clickJS(getObject("sendMoney.lstToFrom"), "Select payee dropdown");
                waitForElementPresent(getObject("xpath~//li[contains(text(),\"" + strPayToAccount.split("~")[1] + "\")]"), 10);
                if (findElements(getObject("xpath~//li[contains(text(),\"" + strPayToAccount.split("~")[1] + "\")]")).size() <= 1) {
                    clickJS(getObject("xpath~//li[contains(text(),\"" + strPayToAccount.split("~")[1] + "\")]"), "Pay to : " + scriptHelper.commonData.strPayToAccount);
                } else {
                    List<WebElement> elList = findElements(getObject("xpath~//li[contains(text(),\"" + strPayToAccount.split("~")[1] + "\")]"));
                    for (WebElement e1 : elList) {
                        if (e1.getText().contains(strPayToAccount.split("~")[0])) {
                            e1.click();
                            break;
                        }
                    }
                }
                while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
                    waitForPageLoaded();
                }
            } else {
                isElementDisplayedWithLog(getObject("xpath~//*[@aria-haspopup='listbox'][contains(text(),'"+strPayToAccount.split("~")[0].trim()+"')]"),
                        "Pay to pre selected account","Send Money",2);
            }


        } else if (deviceType().equalsIgnoreCase("mw.")){
            clickJS(getObject(deviceType()+"SendMoney.lstPayToPayee"), "Pay To Account Dropdown ");
//            need to fix here selection of payee
            String payeename = dataTable.getData("General_Data", "PayTo_Account");
            String strxpath = "";
            WebElement elm =null;
            if(payeename.contains("~")){
                try{
                    strxpath= "//*[contains(@aria-label,'"+payeename.split("~")[0].trim()+"')]/ancestor::div[@role='button']/" +
                            "descendant::*[@class='ecDIB  '][not(@style='display: none;')]/descendant::*[contains(text(),'"+payeename.split("~")[1].trim()+"')]";
                    elm = driver.findElement(By.xpath(strxpath));
                }catch(Exception e){
                    try{
                        strxpath= "//*[contains(@aria-label,'"+payeename.split("~")[0].trim()+"')]/ancestor::div[@role='button']/descendant::*[contains(@class,'ecDIB  ')]/descendant::*[contains(text(),'"+payeename.split("~")[1].trim()+"')]";
                        elm = driver.findElement(By.xpath(strxpath));
                    }catch(Exception f){}
                }

            }else {
                strxpath = "(//span[contains(text(),'" + payeename + "')]/ancestor::*[contains(@class,'col-full text-left')])[1]";
                elm = driver.findElement(By.xpath(strxpath));
            }

            //*[contains(@aria-label,'MalcomeSmythMurphy')]/ancestor::div[@role='button']/descendant::*[contains(@class,'ecDIB  ')]/descendant::*[contains(text(),'3097')]
            //*[contains(@aria-label,'Samanta Frey')]/ancestor::div[@role='button']/descendant::*[@class='ecDIB  '][not(@style='display: none;')]/descendant::*[contains(text(),'8554')]
//            Thread.sleep(10000);
//            WebElement elm = driver.findElement(By.xpath(strxpath));
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",elm);
            js.executeScript("arguments[0].click();", elm );
            Thread.sleep(3000);
            report.updateTestLog("selectPayToSendMoney", "Account/Bill Selected '" + dataTable.getData("General_Data", "PayTo_Account") + "'", Status_CRAFT.DONE);
            waitForPageLoaded();
        }
    }

    /**
     * Function : To enter amount in send money journey
     * Created by : C966003
     * Update on    Updated by     Reason
     * 15/11/2019   C966003        Newly created
     */
    public void amount_Sendmoney_Light() throws Exception {
        scriptHelper.commonData.strAmount = dataTable.getData("General_Data", "Pay_Amount");
        String strAmount ="";
        String strCurrency = dataTable.getData("General_Data", "PayeeCurrency");
        if(isElementDisplayed(getObject("SendMoney.lblCurrencyText"),3)){
            clickJS(getObject("SendMoney.lstCurrency"),"Currency list box");
            clickJS(getObject("xpath~//label[contains(text(),'Currency')]/../../../following-sibling::" +
                    "*//*[@aria-haspopup='listbox']/following-sibling::*/li[contains(text(),'"+strCurrency+"')]"),"Currency"+strCurrency);
        }
        if (!isElementDisplayed(getObject("SendMoney.edtInternationalAmount"),3)){
            strAmount = "sendMoney.edtAmount";
        } else {
            strAmount = "sendMoney.edtInternationalAmount";
        }
        waitForPageLoaded();
        sendKeysJS(getObject(strAmount), scriptHelper.commonData.strAmount, "Amount : "+scriptHelper.commonData.strAmount);
        waitForPageLoaded();
    }

    /**
     * Function : To enter reason for payment for international flow of send money
     * Created by : C966003
     * Update on    Updated by     Reason
     * 15/11/2019   C966003        Newly created
     */
    public void reasonForPayment_Light() throws Exception{
        String strReasonForPayment = dataTable.getData("General_Data","PaymentReason");
        sendKeys(getObject("sendMoney.edtReasonForPayment"),strReasonForPayment, "Reason for payment");
    }

    /**
     * Function : To add reference field for send money
     * Created by : C966003
     * Update on    Updated by     Reason
     * 15/11/2019   C966003        Newly created
     */
    public void reference_SendMoney_Light() throws Exception {
        String strReference = dataTable.getData("General_Data", "BillReference");
        waitForPageLoaded();
        scrollToView(getObject("sendMoney.edtRefernce"));
        sendKeys(getObject("sendMoney.edtRefernce"), strReference, "Reference");
    }

    /**
     * Function : To enter message to payee
     * Created by : C966003
     * Update on    Updated by     Reason
     * 15/11/2019   C966003        Newly created
     */
    public void verifyMessageToPayee_Light() throws Exception {
        //String strMsgToPayee = dataTable.getData("General_Data", "Relationship_Status");
        String strMsgToPayee = dataTable.getData("General_Data", "PayeeMessage");
        if (isElementDisplayed(getObject("SendMoney.txtMsgToPayee"), 5)) {
            scrollToView(getObject("SendMoney.txtMsgToPayee"));
            sendKeys(getObject("SendMoney.txtMsgToPayee"), strMsgToPayee,"Msg : "+strMsgToPayee);
            Thread.sleep(1000);
            driver.findElement(getObject("SendMoney.txtMsgToPayee")).sendKeys(Keys.TAB);
        }
    }

    /**
     * Function : To enter message line 1, 2, 3 to payee
     * Created by : C966003
     * Update on    Updated by     Reason
     * 25/11/2019   C966003        Newly created
     */
    public void messageLine123ToPayee_Light() throws Exception {
        String MessageToPayee1 = dataTable.getData("General_Data", "MessageToPayee1");
        String MessageToPayee2 = dataTable.getData("General_Data", "MessageToPayee2");
        String MessageToPayee3 = dataTable.getData("General_Data", "MessageToPayee3");
        if (isElementDisplayed(getObject("SendMoney.lblMessageToPayee1"), 5)) {
            scrollToView(getObject("SendMoney.txtMessageToPyaee1"));
            sendKeys(getObject("SendMoney.txtMessageToPyaee1"), MessageToPayee1, "Msg 1 : " + MessageToPayee1);
            sendKeys(getObject("SendMoney.txtMessageToPyaee2"), MessageToPayee2, "Msg 2 : " + MessageToPayee2);
            sendKeys(getObject("SendMoney.txtMessageToPyaee3"), MessageToPayee3, "Msg 3 : " + MessageToPayee3);
        }
        driver.hideKeyboard();
    }


    /**
     * Function : To click continue button
     * Created by : C966003
     * Update on    Updated by     Reason
     * 15/11/2019   C966003        Newly created
     */
    public void continue_SendMoney_Light() throws Exception {
        getFullScreenshot("Continue button");
        scrollToView(getObject("SendMoney.btnContinue"));
        clickJS(getObject("SendMoney.btnContinue"), "Continue");
        waitForPageLoaded();
    }

    /**
     * Function : To validate review page details under Send Money
     * Created by : C966003
     * Update on    Updated by     Reason
     * 15/11/2019   C966003        Newly created
     */
    public void reviewPage_SendMoney_Light() throws Exception {
        report.updateTestLog("verifySendMoneyReviewPage", " :: Page Screenshot :: ", Status_CRAFT.FULLPAGESCREENSHOT);
//        getFullScreenshot("Review");
    }

    /**
     * Function to mobile pin digits
     */
    public void enterRequiredPin_Light() throws Exception {
        if(isMobile){
            String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
            List<WebElement> lstPinEnterFieldGrp = findElements(getObject("AddPayee.PINFields"));
            //Entering values for only enabled fields
            for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
                if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                    lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                }
            }
            report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.PASS);
            SCA_MobileAPP scpage = new SCA_MobileAPP(scriptHelper);
            scpage.changeWebviewToNative();
            MobileElement el1 = (MobileElement) driver.findElementByXPath("//android.view.View[3]/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.Button");
            (new TouchAction(driver.getAppiumDriver())).tap(PointOption.point(el1.getCenter().getX(),el1.getCenter().getY())).perform();
            scpage.changeNativeToWebview();
        } else {
            //invoke app
            SCA_MobileAPP sca=new SCA_MobileAPP(scriptHelper);
            //Mobiledriver=sca.MobileAppPushNotification("ANDROID");
            //on browser
            String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
            List<WebElement> lstPinEnterFieldGrp = findElements(getObject("AddPayee.PINFields"));
            //Entering values for only enabled fields
            for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
                if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                    lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                }
            }
            report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.PASS);
            getFullScreenshot("Confirm button");
            clickJS(getObject("AddPayee.Confirm"), "Confirm");
            Thread.sleep(3000);
        }

    }

    /**
     * Function : To validate success page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 15/11/2019   C966003        Newly created
     */
    public void success_SendMoney_Light() throws Exception {
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 6)) {
            waitForPageLoaded();
        }
//        report.updateTestLog("success_SendMoney_Light", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        isElementDisplayedWithLog(getObject("Payments.lblSuccess"),
                "Page title : " + getText(getObject("Payments.lblSuccess")), "Success", 3);
        if (isElementDisplayed(getObject("SendMoney.lblTotalDebited"),2)){
            scriptHelper.commonData.strFetchedAmount = getText(getObject("SendMoney.lblTotalDebitedAmount")).substring(1);
        } else if(isElementDisplayed(getObject("SendMoney.txtDebitedAmount"),2)){
            scriptHelper.commonData.strFetchedAmount = getText(getObject("SendMoney.txtDebitedAmount"));
        } else if(isElementDisplayed(getObject("SendMoney.txtDebitedAmountEuro"),2)){
            scriptHelper.commonData.strFetchedAmount = getText(getObject("SendMoney.txtDebitedAmountEuro"));
        } else if(isElementDisplayed(getObject("SendMoney.txtDebitedAmountGBP"),2)){
            scriptHelper.commonData.strFetchedAmount = getText(getObject("SendMoney.txtDebitedAmountGBP"));
        } else {
            report.updateTestLog("Success page amount capture", "Amount not captured on success page ", Status_CRAFT.WARNING);
        }
        isElementDisplayedWithLog(getObject("SendMoney.btnBacktoHome"),
                "Back to home","Success",3);
        isElementDisplayedWithLog(getObject("SendMoney.btnBackToPayment"),
                "Back to payments","Success",3);
        isElementDisplayedWithLog(getObject("TBA.lblReviewTimeOfRequest"),
                getText(getObject("TBA.lblReviewTimeOfRequest")), "Success", 4);
        getFullScreenshot("Success");
    }
    /**
     * Function : To click on back to home button and navigate to home
     * Created by : C966003
     * Update on    Updated by     Reason
     * 15/11/2019   C966003        Newly created
     */
    public void clickBackToHomeButton_Light() throws Exception{
        scrollToView(getObject("SendMoney.btnBacktoHome"));
        clickJS(getObject("SendMoney.btnBacktoHome"),"Back to home");
        waitForPageLoaded();
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 10)) {
            waitForPageLoaded();
        }
        isElementDisplayedWithLog(getObject(deviceType()+ "home.lblWelcome"),
                "Welcome card","Homepage",4);
    }
    /**
     * Function : To click on back to payments button and navigate to payment page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 16/12/2019   C966003        Newly created
     */
    public void clickBackToPaymentsButton_Light() throws Exception{
        scrollToView(getObject("SendMoney.btnBackToPayment"));
        clickJS(getObject("SendMoney.btnBackToPayment"),"Back to payments");
        waitForPageLoaded();
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 10)) {
            waitForPageLoaded();
        }
    }

    /**
     * Function to select Account from Pay From dropdown from Transfer Between Account screen
     */
    public void selectAccountPayFrom_Light() throws Exception {
        String strAccnt = dataTable.getData("General_Data", "PayFrom_Account");
        if (isElementDisplayed(getObject(deviceType() + "TBA.lblTansBetAccnt"), 5)) {
            if (isElementEnabled(getObject("TBA.lstPayFrom"))) {
                if (!isElementclickable(getObject("TBA.lstPayTo"), 5)) {
                    report.updateTestLog("selectAccountPayFrom", " 'PAY TO' drop-down is not editable '", Status_CRAFT.PASS);
                    selectDropDownJS(getObject("TBA.lstPayFrom"), strAccnt);
                } else {
                    report.updateTestLog("selectAccountPayFrom", " 'PAY FROM' drop-down is editable '", Status_CRAFT.FAIL);
                }
            }
        }
    }

    /**
     * Function to select Account from Pay To dropdown from Transfer Between Account screen
     */
    public void selectAccountPayTo_Light() throws Exception {
        String strAccnt = dataTable.getData("General_Data", "PayTo_Account");
        if (isElementEnabled(getObject("TBA.lstPayTo"))) {
            report.updateTestLog("selectAccountPayTo", " 'PAY TO' drop-down is editable '", Status_CRAFT.PASS);
            waitForElementToClickable(getObject("TBA.lstPayTo"), 10);
            Thread.sleep(1000);
            selectDropDownJS(getObject("TBA.lstPayTo"), strAccnt);
        } else {
            report.updateTestLog("selectAccountPayTo", " 'PAY TO drop-down is not editable '", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to enter Amount on Transfer Between Account screen
     */
    public void enterAmount_Light() throws Exception {
        String strAmount = dataTable.getData("General_Data", "Pay_Amount");
        if (isElementDisplayed(getObject("TBA.edtPayAmount"), 2)) {
            sendKeysJS(getObject("TBA.edtPayAmount"), strAmount, "Pay Amount");
        }
    }

    /**
     * Common function to verify details on Transfer Between Account screen
     */
    public void success_TBA_Light() throws Exception {
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 6)) {
            waitForPageLoaded();
        }
        isElementDisplayedWithLog(getObject("SendMoney.btnBacktoHome"),
                "Back to home","Success",3);
        isElementDisplayedWithLog(getObject("SendMoney.btnBackToPayment"),
                "Back to payments","Success",3);
        isElementDisplayedWithLog(getObject("TBA.lblReviewTimeOfRequest"),
                getText(getObject("TBA.lblReviewTimeOfRequest")), "Success", 4);
        getFullScreenshot("Success");
    }

    /**
     * Function : To Select country and validate selected currency
     * Created by : C966003
     * Update on    Updated by     Reason
     * 21/11/2019   C966003        Newly created
     */
    public void selectCountryAddPayee_Light() throws Exception{
        String countryName = dataTable.getData("General_Data", "PayeeCountry");
        if (!dataTable.getData("General_Data", "PayeeCountry").equals("")) {
            if (deviceType.equalsIgnoreCase("Web")) {
                click(getObject("AddPayee.lblDefaultCountry"), "Country dropdown Selected");
                clickJS(getObject("xpath~//button[@id='C5__QUE_E50DFFB0F8190DA23674858_widgetARIA']/following-sibling::ul/li[contains(.,'" + countryName + "')]"), "Country Selected");
            } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
                clickJS(getObject("AddPayee.lblDefaultCountry"), "Country dropdown Selected");
                clickJS(getObject("xpath~//button[@id='C5__QUE_E50DFFB0F8190DA23674858_widgetARIA']/following-sibling::ul/li[contains(.,'" + countryName + "')]"), "Country Selected");
            }
            report.updateTestLog("selectPayeeCountryAndCurrency", "Payee Country selected '" + countryName + "'", Status_CRAFT.DONE);
            waitForPageLoaded();
        }
    }

    /**
     * Function : To Fill all details while adding international payee details
     * Created by : C966003
     * Update on    Updated by     Reason
     * 21/11/2019   C966003        Newly created
     */
    public void addPayeeDetails_International_Light() throws Exception{
        String strPayeeName = dataTable.getData("General_Data", "FirstName");
        String strAddress1 = dataTable.getData("General_Data", "Address1");
        String strAddress2 = dataTable.getData("General_Data", "Address2");
        String strResidenceCountry = dataTable.getData("General_Data", "ResidenceCountry");
        String strPayeeBIC = dataTable.getData("General_Data", "BIC_Number");
        String strPayeeNSC = dataTable.getData("General_Data", "NationalSortCode");
        String strPayeeAccNum = dataTable.getData("General_Data", "AccountNumber");
        String strPayeebankAddress1 = dataTable.getData("General_Data", "PayeeBankAddress1");
        String strPayeeBankAddress2 = dataTable.getData("General_Data", "PayeeBankAddress2");
        String strPayeebankCity = dataTable.getData("General_Data", "PayeeBankCity");

        //**Payee details
        sendKeys(getObject("AddPayee.txtName"), strPayeeName, "Name entered is '" + strPayeeName + "'");
        sendKeys(getObject("AddPayee.txtAddress1"), strAddress1, "Address 1 entered is '" + strAddress1 + "'");
        sendKeys(getObject("AddPayee.txtAddress2"), strAddress2, "Address 2 entered is '" + strAddress2 + "'");
        sendKeys(getObject("AddPayee.txtResidenceCountry"), strResidenceCountry, "Country of residence entered is '" + strResidenceCountry + "'");

        //**Payee bank details
        sendKeys(getObject("AddPayee.txtPayeeBankBic"), strPayeeBIC, "BIC entered is '" + strPayeeBIC + "'");
        sendKeys(getObject("AddPayee.txtNSC"), strPayeeNSC, "BIC entered is '" + strPayeeNSC + "'");
        sendKeys(getObject("AddPayee.txtAccNumber"), strPayeeAccNum, "Account number entered is '" + strPayeeAccNum + "'");
        sendKeys(getObject("AddPayee.txtPayeeBankAddress1"), strPayeebankAddress1, "Bank address 1 entered is '" + strPayeebankAddress1 + "'");
        sendKeys(getObject("AddPayee.txtPayeeBankAddress2"), strPayeeBankAddress2, "Bank address 2 (optional) entered is '" + strPayeeBankAddress2 + "'");
        sendKeys(getObject("AddPayee.txtPayeeBankCity"), strPayeebankCity, "Bank city entered is '" + strPayeebankCity + "'");

        getFullScreenshot("Add Payee");

        clickJS(getObject("AddPayee.Continue"),"Continue");
    }

    /**
     * Function : To select account from home page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 26/11/2019   C966003        Newly created
     */
    public void selectAccountFromHomepage_Light()throws Exception{
        String strFromAccount = "";
        if(!scriptHelper.commonData.strPayFromAccount.isEmpty()){
            strFromAccount = scriptHelper.commonData.strPayFromAccount;
        } else {
            strFromAccount = dataTable.getData("General_Data","PayFrom_Account");
        }
        waitForPageLoaded();
        clickJS(getObject("xpath~//*[contains(text(),'"+strFromAccount.split("~")[1].trim()+"')]"),"Account : "+strFromAccount);
    }

    /**
     * Function : To verify today's in progress transaction amount for payment
     * Created by : C966003
     * Update on    Updated by     Reason
     * 26/11/2019   C966003        Newly created
     */
    public void inProgressTransaction_Light()throws Exception{
        String strAmountDebited = scriptHelper.commonData.strFetchedAmount;
//        if(isElementDisplayed(getObject("account.transaction.btnShowMore"),3)){
//            waitForPageLoaded();
//            clickJS(getObject("account.transaction.btnShowMore"),"Show More");
//            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 6)) {
//                waitForPageLoaded();
//            }
//            click(getObject("account.transaction.btnShowMore"), "Show More");
//            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 6)) {
//                waitForPageLoaded();
//            }
//        }
//        getFullScreenshot("Transaction");
        if (!isMobile) {
            List<WebElement> amountList = findElements(getObject("xpath~//*[contains(@style,'vertical-align:middle')]//*[contains(text(),'" + strAmountDebited + "')]"));
            if (amountList.size() >= 0) {
                for (WebElement el : amountList) {
                    isElementDisplayedWithLog(getObject("xpath~//*[contains(@style,'vertical-align:middle')]//*[contains(text(),'" + el.getText() + "')]"),
                            el.getText() + "Amount displayed for in progress transaction", "Transactions", 3);
                }
            } else {
                report.updateTestLog("inProgressTransaction", "Amount : " + strAmountDebited + " is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            boolean bfound = false;
            do{
//                List<WebElement> amountList = findElements(getObject("xpath~//*[contains(@style,'text-align: left')]//*[contains(text(),'" + strAmountDebited + "')]"));
                List<WebElement> amountList = findElements(getObject("xpath~//*[@aria-label='In progress']/ancestor::*[contains(@class,'-row boi-td-control max_height_none  col-full tc-clearfix')]/descendant::*[@class='boi_label boi-debit-amount boi_amount_spacing'][text()='" + strAmountDebited + "']"));
                if (amountList.size() >= 0) {
                    for (WebElement el : amountList) {
                        isElementDisplayedWithLog(getObject("xpath~//*[contains(@style,'text-align: left')]//*[contains(text(),'" + el.getText() + "')]"),
                                el.getText() + "Amount displayed for in progress transaction", "Transactions", 3);
                        bfound=true;
                    }
                } else {
                    report.updateTestLog("inProgressTransaction", "Amount : " + strAmountDebited + " is not dispalyed", Status_CRAFT.FAIL);
                }
            }while(!bfound && isElementDisplayed(getObject("account.transaction.btnShowMore"),1));

            if(!bfound){
                report.updateTestLog("inProgressTransaction", "Amount : " + strAmountDebited + " is not dispalyed", Status_CRAFT.FAIL);
            }
            report.updateTestLog("FullPageInProgressTransaction", "Full page screen capture", Status_CRAFT.FULLPAGESCREENSHOT);

        }
    }

    /**
     * Function : To select set up new standing order option
     * Created by : C966003
     * Update on    Updated by     Reason
     * 27/11/2019   C966003        Newly created
     */
    public void selectSetUpNewSO_Light() throws Exception {
        waitForPageLoaded();
        clickJS(getObject("StandingOrder.btnSetUpNewStandingOrder"), "Clicked on 'Set up new standing order' Button");
        waitForPageLoaded();
    }

    /**
     * Function : To select pay from account from Pay from list of standing order
     * Created by : C966003
     * Update on    Updated by     Reason
     * 27/11/2019   C966003        Newly created
     */
    public void selectPayFromSO_Light() throws Exception{
        scriptHelper.commonData.strPayFromAccount = dataTable.getData("General_Data", "PayFrom_Account");
        String strPayFromAccount = scriptHelper.commonData.strPayFromAccount;
        waitForPageLoaded();
        clickJS(getObject("xpath~//*[contains(text(),'Select an account')][@aria-haspopup='listbox']"),"Pay from list");
        clickJS(getObject("xpath~//ul/li[contains(text(),'"+strPayFromAccount.split("~")[1].trim()+"')]"),"Account : "+strPayFromAccount);
        waitForPageLoaded();
    }
    /**
     * Function : To enter payee details of standing order
     * Created by : C966003
     * Update on    Updated by     Reason
     * 27/11/2019   C966003        Newly created
     */
    public void enterPayeeDetailsOfSO_Light()throws Exception{
        String strPayeeName = dataTable.getData("General_Data", "PayeeName");
        String strPayeeBIC = dataTable.getData("General_Data","BIC_Number");
        String strPayeeIBAN = dataTable.getData("General_Data","IBAN_Number");
        String strPayeeMessage = dataTable.getData("General_Data","PayeeMessage");
        String strFreq = dataTable.getData("General_Data","SOFrequency");
        String strAccountNo = strPayeeIBAN.substring(14,22);
        String strSortCode = strPayeeIBAN.substring(8,14);

        sendKeysJS(getObject("SetupStandingOrder.txtName"), strPayeeName, "Payee Name entered is '" + strPayeeName + "'");
        if(isElementDisplayed(getObject("SetupStandingOrder.txtBIC"),2)){
            sendKeysJS(getObject("SetupStandingOrder.txtBIC"),strPayeeBIC,"BIC entered is '"+strPayeeBIC+"'");
            sendKeysJS(getObject("SetupStandingOrder.txtIBAN"),strPayeeIBAN,"IBAN entered is '"+strPayeeIBAN+"'");
        } else {
            sendKeysJS(getObject("StandingOrder.lblAccountNumber"),strAccountNo,"Account number entered is '"+strAccountNo+"'");
            sendKeysJS(getObject("StandingOrder.lblSortCode"),strSortCode,"National sort code entered is '"+strSortCode+"'");
        }
        sendKeys(getObject("SetupStandingOrder.txtMessageToPayee"),strPayeeMessage,"Message to payee entered is '"+strPayeeMessage+"'");
        driver.hideKeyboard();
        click(getObject("xpath~//ul/li/*[contains(text(),'"+strFreq+"')]"),"Frequency selected is '"+strFreq+"'");
        waitForPageLoaded();
        Thread.sleep(1000);
    }
    /**
     * Function : To enter payee details of standing order
     * Created by : C966003
     * Update on    Updated by     Reason
     * 27/11/2019   C966003        Newly created
     */
    public void enterAmountOfSO_Light() throws Exception{
        String strAmount = dataTable.getData("General_Data","Pay_Amount");
        sendKeys(getObject("ManageStandingOrder.txtNewAmount"),strAmount,"Amount entered is '"+strAmount+"'");
    }
    /**
     * Function : To validate review page details under Standing orders
     * Created by : C966003
     * Update on    Updated by     Reason
     * 27/11/2019   C966003        Newly created
     */
    public void reviewPage_SO_Light() throws Exception {
        report.updateTestLog("verifySendMoneyReviewPage", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        getFullScreenshot("Review");
    }

    /**
     * Function : To validate success page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 27/11/2019   C966003        Newly created
     */
    public void success_SO_Light() throws Exception {
        try{
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 6)) {
                waitForPageLoaded();
            }
            report.updateTestLog("success_SO_Light", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
            isElementDisplayedWithLog(getObject("SendMoney.btnBacktoHome"),
                    "Back to home","Success",3);
            isElementDisplayedWithLog(getObject("SendMoney.btnBackToPayment"),
                    "Back to payments","Success",3);
            isElementDisplayedWithLog(getObject("TBA.lblReviewTimeOfRequest"),
                    getText(getObject("TBA.lblReviewTimeOfRequest")), "Success", 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getFullScreenshot("Success");
    }

    /**
     * Function : To select account from Account dropdown in Standing orders
     * Created by : C966003
     * Update on    Updated by     Reason
     * 12/12/2019   C966003        Newly created
     */
    public void selectAccount_SO_Light() {
        try{
            scriptHelper.commonData.strPayFromAccount = dataTable.getData("General_Data", "PayFrom_Account");
            waitForPageLoaded();
            clickJS(getObject("StandingOrder.lstSelectAccount"), "Standing Order Dropdown");
            clickJS(getObject("xpath~//li[contains(text(),'" + scriptHelper.commonData.strPayFromAccount.split("~")[1] + "')]"),"Account : "+scriptHelper.commonData.strPayFromAccount);
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Function : To select SO account from Account list displayed in Standing orders
     * Created by : C966003
     * Update on    Updated by     Reason
     * 12/12/2019   C966003        Newly created
     */
    public void selectRequiredSO_Light() {
        try {
            scriptHelper.commonData.strPayToAccount = dataTable.getData("General_Data", "PayTo_Account");
            waitForPageLoaded();
            clickJS(getObject("xpath~//*[contains(text(),'" + scriptHelper.commonData.strPayToAccount.split("~")[0].trim() + "')]"), "Standing Order list");
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Function : To click on manage options for standing orders
     * Created by : C966003
     * Update on    Updated by     Reason
     * 12/12/2019   C966003        Newly created
     */
    public void manageSO_Light(){
        try{
            clickJS(getObject("xpath~//*[contains(text(),'Manage')][@style]"),"Manage");
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Function : To change amount for SO on manage standing orders page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 12/12/2019   C966003        Newly created
     */
    public void changeAmount_SO_Light(){
        try{
            scriptHelper.commonData.strAmount = dataTable.getData("General_Data","Pay_Amount");
            clickJS(getObject("xpath~//*[contains(text(),'Change amount')]"),"Change amount");
            sendKeysJS(getObject("xpath~//*[contains(@name,'.AMOUNT')]"),scriptHelper.commonData.strAmount,"New amount");
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Function : To remove hold for SO on manage standing orders page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 12/12/2019   C966003        Newly created
     */
    public void removeHold_SO_Light(){
        try{
            clickJS(getObject("xpath~//*[contains(text(),'Remove hold')]"),"Remove hold");
            clickJS(getObject("xpath~//*[@class='tc-tab-pane'][contains(@style,'display: block')]//*[contains(text(),'Yes')]"),"Yes");
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Function : To put on hold for SO on manage standing orders page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 12/12/2019   C966003        Newly created
     */
    public void putOnHold_SO_Light(){
        try{
            clickJS(getObject("xpath~//*[contains(text(),'Put on hold')]"),"Remove hold");
            clickJS(getObject("xpath~//*[@class='tc-tab-pane'][contains(@style,'display: block')]//*[contains(text(),'Yes')]"),"Yes");
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Function : To cancel this SO on manage standing orders page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 12/12/2019   C966003        Newly created
     */
    public void cancelThis_SO_Light(){
        try{
            clickJS(getObject("xpath~//*[contains(text(),'Cancel this')]"),"Cancel this");
            clickJS(getObject("xpath~//*[@role]//*[contains(text(),'Cancel standing order')]"),"Cancel standing order");
//            if (isElementDisplayed(getObject("xpath~//*[contains(text(),'Yes, cancel standing order')]"),2)){
//                clickJS(getObject("xpath~//*[contains(text(),'Yes, cancel standing order')]"),"Yes, cancel standing order");
//            }
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function : To select future dated payee to view details
     * Created by : C966003
     * Update on    Updated by     Reason
     * 16/12/2019   C966003        Newly created
     */
    public void selectFDPsPayee_Light(){
        try{
            if(!deviceType.equalsIgnoreCase("Web") && !deviceType.equalsIgnoreCase("TabletWeb") ){
                selectFDPsPayee_mobile_Light();
            } else {
                selectFDPsPayee_desktop_Light();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function : To select future dated payee to view details for mobile
     * Created by : C966003
     * Update on    Updated by     Reason
     * 16/12/2019   C966003        Newly created
     */
    public void selectFDPsPayee_mobile_Light()throws Exception{
        String strPayFrom = dataTable.getData("General_Data","PayFrom_Account"); boolean flagPayeeTest = false;
        String strPayTo = dataTable.getData("General_Data","PayTo_Account");
        String strAmount = dataTable.getData("General_Data","Pay_Amount");
        String strDate = dataTable.getData("General_Data","Transfer_Date");
        String strPayeeStatus = dataTable.getData("General_Data","PayeeStatus");
        String strPayToNew = "";
        if (strPayTo.split("~")[0].length()<=18){
            strPayToNew = strPayTo.split("~")[0].trim();
        } else {
            strPayToNew = strPayTo.split("~")[0].trim().substring(0,18);
        }

        List<WebElement> lstPayee = findElements(getObject("xpath~//*[@role='button']//*[contains(text(),\""+ strPayToNew +"\")][contains(@class,'padding-left')]"));
        report.updateTestLog("selectFDPsPayee", lstPayee.size() +" count of "+ strPayToNew +" payees found", Status_CRAFT.DONE);
        for(int i=1; i<=lstPayee.size(); i++){
            if(lstPayee.size()>= 1){
                String strStatusFetched = getText(getObject("xpath~(//*[@role='button']//*[contains(text(),\""+ strPayToNew +"\")][contains(@class,'padding-left')])["+i+"]/ancestor::div[@role='button']")).split("\n")[2];
                String strAmountFetched = getText(getObject("xpath~(//*[@role='button']//*[contains(text(),\""+ strPayToNew +"\")][contains(@class,'padding-left')])["+i+"]/ancestor::div[@role='button']")).split("\n")[1];
                if(strPayeeStatus.equalsIgnoreCase(strStatusFetched)){
                    clickJS(getObject("xpath~(//*[@role='button']//*[contains(text(),\""+ strPayToNew +"\")][contains(@class,'padding-left')])[" + i + "]"), strPayTo.split("~")[0].trim());
                    while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
                        waitForPageLoaded();
                    }
                    if(payeeValidator_mobile_Light(strAmount,strPayFrom,strPayTo,strPayToNew,strDate,strPayeeStatus)){
                        flagPayeeTest = true;
                        break;
                    }
                    clickJS(getObject(deviceType()+"FDPs.lblBack"),"Back");
                }
            } else {
                report.updateTestLog("selectFDPsPayee", "Payee not listed in FDPs list", Status_CRAFT.FAIL);
            }
        }
        if(!flagPayeeTest == true){
            report.updateTestLog("selectFDPsPayee", "Required payee details are not found in FDPs list", Status_CRAFT.FAIL);
        }
        getFullScreenshot("FutureDatedPayee");
    }
    /**
     * Function : To validate correct payee and return boolean value for mobile
     * Created by : C966003
     * Update on    Updated by     Reason
     * 16/12/2019   C966003        Newly created
     */
    public boolean payeeValidator_mobile_Light(String strAmount, String strPayFrom, String strPayTo, String strPayToNew, String strDueDate, String strStatus)throws Exception{
        boolean testFlag = false; String newDueDate = ""; String newPaidDate = "";
        String strFetchDate = getText(getObject("FDPs.lblPayDueDate"));
        String strFetchPayfrom = getText(getObject("FDPs.lblPayFrom"));
        String strFetchAmount = getText(getObject("FDPs.lblPayAmount"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date varDueDate = dateFormat.parse(strDueDate.replace("/","-"));
        dateFormat=new SimpleDateFormat("dd-MM-yyyy"); newDueDate=dateFormat.format(varDueDate).replace("-","/");

        if(newDueDate.equalsIgnoreCase(strFetchDate)&&
                strPayFrom.split("~")[0].trim().equalsIgnoreCase(strFetchPayfrom)&&
                strAmount.substring(1).equalsIgnoreCase(strFetchAmount)){
            report.updateTestLog("selectFDPsPayee", "****** Required payee found ******", Status_CRAFT.SCREENSHOT);
            testFlag = true;
        }
        return testFlag;
    }

    /**
     * Function : To select future dated payee to view details for desktop
     * Created by : C966003
     * Update on    Updated by     Reason
     * 16/12/2019   C966003        Newly created
     */
    public void selectFDPsPayee_desktop_Light()throws Exception{
        String newDueDate = ""; boolean flagPayeeTest = false; String strPayToNew = "";int j =1;
        String strPayFrom = dataTable.getData("General_Data","PayFrom_Account");
        String strPayTo = dataTable.getData("General_Data","PayTo_Account");
        String strAmount = dataTable.getData("General_Data","Pay_Amount");
        if(!scriptHelper.commonData.strTransferDate.equals("")){
            scriptHelper.commonData.strDate = scriptHelper.commonData.strTransferDate;
        }else{
            scriptHelper.commonData.strDate = dataTable.getData("General_Data","Transfer_Date");
        }
        String strPayeeStatus = dataTable.getData("General_Data","PayeeStatus");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date varDueDate = dateFormat.parse(scriptHelper.commonData.strDate.replace("/","-"));
        dateFormat=new SimpleDateFormat("dd-MM-yyyy"); newDueDate=dateFormat.format(varDueDate).replace("-","/");

        if (strPayTo.split("~")[0].length()<=18){
            strPayToNew = strPayTo.split("~")[0].trim();
        } else {
            strPayToNew = strPayTo.split("~")[0].trim().substring(0,18);
        }

        List<WebElement> lstPayee = findElements(getObject("xpath~//*[contains(text(),\""+ strPayToNew +"\")][contains(@class,'boi-pl-14')]"));
        report.updateTestLog("selectFDPsPayee", lstPayee.size() +" count of "+ strPayToNew +" payees found", Status_CRAFT.DONE);
        for(int i=1; i<=lstPayee.size(); i++){
            if(lstPayee.size()>= 1){
                String strStatusFetched = getText(getObject("xpath~(//*[contains(text(),\""+ strPayToNew +"\")][contains(@class,'boi-pl-14')])["+i+"]/../../..")).split("\n")[0];
                String strAmountFetched = getText(getObject("xpath~(//*[contains(text(),\""+ strPayToNew +"\")][contains(@class,'boi-pl-14')])["+i+"]/../../..")).split("\n")[2];
                String strDueDate = getText(getObject("xpath~(//*[contains(text(),\""+ strPayToNew +"\")][contains(@class,'boi-pl-14')])["+i+"]/../../..")).split("\n")[3];
                if(strPayeeStatus.equalsIgnoreCase(strStatusFetched) &&
                        strAmount.contains(strAmountFetched) &&
                        newDueDate.equalsIgnoreCase(strDueDate)){
                    clickJS(getObject("xpath~(//*[contains(text(),\""+ strPayToNew +"\")][contains(@class,'boi-pl-14')])[" + i + "]"), strPayToNew);
                    report.updateTestLog("selectFDPsPayee", "Required payee found : "+j, Status_CRAFT.SCREENSHOT);
                    j=j+1;
                    flagPayeeTest = true;
                }
            } else {
                report.updateTestLog("selectFDPsPayee", "Payee not listed in FDPs list", Status_CRAFT.FAIL);
            }
        }
        if(!flagPayeeTest == true){
            report.updateTestLog("selectFDPsPayee", "Required payee details are not found in FDPs list", Status_CRAFT.FAIL);
        }
        getFullScreenshot("FutureDatedPayment");
    }

    /**
     * Function for Review Screen of  Mobile Top Up screen
     */
    public void mobileTopUpReview_Light() throws Exception {
        report.updateTestLog("MobileTopUpReview_light","Mobile Top Up Review Screen",Status_CRAFT.SCREENSHOT);
        scrollToView(getObject(""));
        getFullScreenshot("Review");
    }
    public void mobileTopUpSuccessScreen_Light() throws Exception {

        while (isElementDisplayed(getObject("xpath~/[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
        if (isElementDisplayed(getObject("withdrawFunds.successmsg"), 3)){
            String SuccessMsg = getText(getObject("withdrawFunds.successmsg"));
            if (SuccessMsg.equalsIgnoreCase("Success!")) {
                report.updateTestLog("VerifySuccessMessage", "Success message is displayed correctly", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifySuccessMessage", "Success message is not displayed correctly", Status_CRAFT.FAIL);
            }
        }
        if (isElementDisplayed(getObject(deviceType() + "MTU.btnBacktoPayments"), 7)) {
            click(getObject(deviceType() + "MTU.btnBacktoPayments"), "Back to Payments");
            Thread.sleep(2000);
        }else if (deviceType.equalsIgnoreCase("MobileWeb")) {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();",
                    driver.findElement(getObject(deviceType() + "MTU.btnBacktoPayments")));
            clickJS(getObject(deviceType() + "MTU.btnBacktoPayments"), "Back to Payments ");
            report.updateTestLog("Success page Mobile"," is displayed successfully", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("Success page","SUCCESS page not is displayed successfully", Status_CRAFT.FAIL);
        }
        getFullScreenshot("Success");
    }
    public void selectProvider_Light() throws Exception{
        String strProvider = dataTable.getData("General_Data", "Provider_Name");
        String strIrishNo;

        while (isElementDisplayed(getObject("xpath~/[@class='spinner']"), 1)) {
            waitForPageLoaded();
        }
        if (isElementDisplayed(getObject("MTU.btnProviderSelect"), 1)) {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//*[contains(text(),'"+strProvider+"')]")));
            clickJS(getObject("xpath~//*[contains(text(),'"+strProvider+"')]"), "Selected Provider is- '" + strProvider + "'");
            report.updateTestLog("selectProvider", strProvider + " Provider is selected ", Status_CRAFT.PASS);
        }
        else {
            report.updateTestLog("selectProvider", " 'Provider is not selected'", Status_CRAFT.FAIL);
        }
    }

    public void selectAmount_Light() throws Exception {
        String strpayAmount = dataTable.getData("General_Data", "Pay_Amount");
        if (isElementDisplayed(getObject("MTU.drdwnlabelAmount"), 1)) {
            driver.findElement(getObject("xpath~//*[contains(@class,'amount_dropdown')]")).click();
            List<WebElement> allLinks = findElements(getObject("xpath~//div[@class='aria_exp_wrapper boi-position-relative']/ul[@class='exp_elem_list_widget list']/li"));
            waitForElementPresent(getObject("xpath~//div[@class='aria_exp_wrapper boi-position-relative']/ul[@class='exp_elem_list_widget list']/li"),3);
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

    public void enterPhoneNumber_Light() throws Exception {
        String strPhone = dataTable.getData("General_Data", "MobileNumber");
        if (isElementDisplayed(getObject("MTU.txtboxMobileNumber"), 1)) {
            sendKeysJS(getObject("MTU.txtboxMobileNumber"), strPhone, "Mobile phone number Entered");
        }else {
            report.updateTestLog("MobileNumber", "'Phone' text box is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void enterConfirmNumber_Light() throws Exception {
        String strConfirmPhone = dataTable.getData("General_Data", "ConfirmNumber");
        if (isElementDisplayed(getObject("MTU.txtboxConfirmNumber"), 1)) {
            clickJS(getObject("MTU.txtboxConfirmNumber"), "ConfirmNumber");
            sendKeysJS(getObject("MTU.txtboxConfirmNumber"), strConfirmPhone, "Confirm number Entered");
        } else {
            report.updateTestLog("ConfirmNumber", "'Confirm Number' text box is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void enterPTMDetails_Light() throws Exception {
        //Mobile Number
        String strMobNumber = dataTable.getData("General_Data", "Phone");
        if (isElementDisplayed(getObject("PTM.lblMobileNumber"), 1)) {
            clickJS(getObject("PTM.txtMobNumber"),"Entering the Mobile number ");
            sendKeys(getObject("PTM.txtMobNumber"), strMobNumber, "Mobile number entered is '" + strMobNumber + "'");
        } else {
            report.updateTestLog("verifyEnterPTMDetails", "Label 'Mobile number' is NOT displayed on 'Pay To Mobile' Page", Status_CRAFT.FAIL);
        }

        //Amount
        String strAmount = dataTable.getData("General_Data", "Pay_Amount");
        if (isElementDisplayed(getObject("PTM.lblAmount"), 1)) {
            sendKeys(getObject("PTM.txtAmount"), strAmount, "Amount entered is '"+ strAmount +"'");
        } else {
            report.updateTestLog("verifyEnterPTMDetails", "Label 'Amount' is NOT displayed on 'Pay To Mobile' Page", Status_CRAFT.FAIL);
        }

        //Your name'
        String strYourName = dataTable.getData("General_Data", "YourName");
        if (isElementDisplayed(getObject("PTM.lblYourName"), 1)) {
            sendKeys(getObject("PTM.txtYourName"), strYourName, "Your Name entered is '"+ strYourName +"'");
        } else {
            report.updateTestLog("verifyEnterPTMDetails", "Label 'Your name' is NOT displayed on 'Pay To Mobile' Page", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("PTM.btnContinue"), 1)) {
            clickJS(getObject("PTM.btnContinue"), " 'Continue' Button");
        } else {
            report.updateTestLog("verifyEnterPTMDetails", "Button 'Continue' is NOT displayed on 'Pay To Mobile' Form Page", Status_CRAFT.FAIL);
        }
    }

    public void validatePTMSuccessPage_Light() throws Exception {
        //validating page title 'Pay To Mobile'
        isElementDisplayedWithLog(getObject("PTM.hdrPageTitle"),"Header page title", "Pay To Mobile Success", 1);
        isElementDisplayedWithLog(getObject("PTM.iconConfirmation")," Success Icon 'Tick Mark'", " 'Pay To Mobile' Success ", 1);
        isElementDisplayedWithLog(getObject("PTM.txtSuccess"),"Text message 'Success'", " 'Pay To Mobile' Success ", 1);
        //Validating Navigation/functionality of the buttons
        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to payments")) {
            click(getObject("PTM.btnBackToPayment"), "Clicked on 'Back to payments'");
            if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 1)) {
                report.updateTestLog("verifyPTMSuccessPage", "Upon clicking 'Back to payments' ,successfully Navigated to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyPTMSuccessPage", "Upon clicking 'Back to payments' ,navigation unsuccessful to HomePage screen from Add Payee Confirmation Page", Status_CRAFT.FAIL);
            }
        }
        getFullScreenshot("Success");
    }

    public void validatePTMReviewPage_Light() throws Exception {
        isElementDisplayedWithLog(getObject("PTM.hdrPageTitle"),"Header page title", "Pay To Mobile Review", 2);
        isElementDisplayedWithLog(getObject("PTM.lblReview"),"Section Header 'Review'", "Pay To Mobile Review", 2);
        getFullScreenshot("Review");
    }

    /**
     *To Verify and Validating the Manage Payee list loght version
     * Created by  C966003
     * Update on    Updated by    Reason
     * 03/06/2020   C966003      Added list to select payee as per reference
     */
    public void selectPayeeFromManagePayeeList_Light() throws Exception {
//        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 15)) {
//            waitForPageLoaded();
//        }
        String strExpectedPayeeName = dataTable.getData("General_Data", "PayeeName");
        String strBillReference = dataTable.getData("General_Data", "BillReference");
        if (!deviceType.equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject("xpath~//*[contains(@title,'Payee name')][contains(text(),'"+ strExpectedPayeeName.split("~")[0].trim() + "')]"), 5)) {
                clickJS(getObject("xpath~//*[contains(@title,'Payee name')][contains(text(),'"+ strExpectedPayeeName.split("~")[0].trim() + "')]"), "Selected Payee '" + strExpectedPayeeName + "' from the Manage Payee list");
                if(getText(getObject(deviceType() + "AddBillPayee.lblBillRefernce")).trim().equalsIgnoreCase(strBillReference)){
                    scriptHelper.commonData.confirmationFlag = true;
                    if (dataTable.getData("General_Data","OperationDeletePayee").equals("DELETE")){
                        deletePayee_Light();
                    }
                }
            }
        } else {
            if (isElementDisplayed(getObject("xpath~//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']"), 10)) {
                if (isElementDisplayed(getObject("xpath~//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']/descendant::*[contains(text(),'" + strExpectedPayeeName +"')]"),5)){
                    if(!"".equalsIgnoreCase(strExpectedPayeeName)){
                        List<WebElement> strList = findElements(getObject("xpath~//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']/descendant::*[contains(text(),'" + strExpectedPayeeName + "')]"));
                        for(int i=1; i<=strList.size(); i++){
                            clickJS(getObject("xpath~(//*[@class='boi-plain-table boi_transaction_table tc-clearfix col-full']/descendant::*[contains(text(),'" + strExpectedPayeeName + "')])["+i+"]"),"Payee :: "+strExpectedPayeeName);
                            if(getText(getObject(deviceType() + "AddBillPayee.lblBillRefernce")).trim().equalsIgnoreCase(strBillReference)){
                                if (dataTable.getData("General_Data","OperationDeletePayee").equals("DELETE")){
                                    deletePayee_Light();
                                }
                                scriptHelper.commonData.confirmationFlag = true;
                                break;
                            } else {
                                clickJS(getObject(deviceType()+ "home.Goback"),"Go back");
                            }
                        }
                    }
                }
            } else {
                if (isElementDisplayed(getObject("xpath~//table[contains(@class,'boiArrowTable')][@summary='PayeeList']"), 5)) {
                    clickJS(getObject("xpath~//table[contains(@class,'boiArrowTable')][@summary='PayeeList']/tbody/tr/td/descendant::*[text()='" + strExpectedPayeeName + "']"), "Selected Payee '" + strExpectedPayeeName + "' from the Manage Payee list");
                    if (dataTable.getData("General_Data","OperationDeletePayee").equals("DELETE")){
                        deletePayee_Light();
                    }
                } else {
                    report.updateTestLog("SelectPayeeFromManagePayeeList", "Manage Payees table  is NOT displayed on Manage Payee page", Status_CRAFT.FAIL);
                }
            }
        }

    }

    /**
     * Function : To delete payee light version
     * Created by : C966003
     * Update on    Updated by     Reason
     * 03/06/2020   C966003        added flag and changed xpath for Delete payee 1 and 3
     */
    public void deletePayee_Light() throws Exception {
        waitForPageLoaded();
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
            waitForPageLoaded();
            report.updateTestLog("deletePayee", "Screen view", Status_CRAFT.SCREENSHOT);
        }
        if(!scriptHelper.commonData.confirmationFlag==false) {
            clickJS(getObject(deviceType() + "ManagePayee.btnDeletePayee1"), "Delete payee");
            isElementDisplayedWithLog(getObject("ManagePayee.btnDeletePayee3"),
                    "Delete payee", "Delete payee", 5);
            clickJS(getObject( "ManagePayee.btnDeletePayee3"), "Delete payee");
            while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                waitForPageLoaded();
                report.updateTestLog("deletePayee", "Screen view", Status_CRAFT.SCREENSHOT);
            }
        }
    }

    /**
     * Function : To click Add Payee button for Manage payee
     * Created by : C966003
     * Update on    Updated by     Reason
     * 03/06/2020   C966003        Newly created
     */
    public void addPayee_FillDetails_Light() throws Exception {
        String strName = dataTable.getData("General_Data", "FirstName");
        String strIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strBIC = dataTable.getData("General_Data", "BIC_Number");
        String strPayeeAccNum = dataTable.getData("General_Data", "AccountNumber");
        String strPayeeNSC = dataTable.getData("General_Data", "NationalSortCode");
        String strReference = dataTable.getData("General_Data", "BillReference");
        String strDescription = dataTable.getData("General_Data", "BillerDescription");

        waitForPageLoaded();
        clickJS(getObject(deviceType() + "Payments.btnAddPayees"), "Add Payee");
        waitForPageLoaded();

        sendKeys(getObject("AddPayee.Name"), strName, "Name");
        if(isElementDisplayed(getObject("AddPayee.IBAN"),2)) {
            sendKeys(getObject("AddPayee.IBAN"), strIBAN, "IBAN");
            sendKeys(getObject("AddPayee.BIC"), strBIC, "BIC");
        } else if (isElementDisplayed(getObject("AddPayee.txtAccNumber"),2)){
            sendKeys(getObject("AddPayee.txtAccNumber"), strPayeeAccNum, "Account number");
            sendKeys(getObject("AddPayee.txtNSC"), strPayeeNSC, "Sort code");
        }

        sendKeys(getObject("AddPayee.Message"), strReference+"#%", "Reference");
        continue_SendMoney_Light();
        getFullScreenshot("AddPayee with reference error");
        click(getObject("AddPayee.Description"));
        click(getObject("AddPayee.iconXRefnc"), "x icon");
        sendKeys(getObject("AddPayee.Description"), strDescription, "Description");
        sendKeys(getObject("AddPayee.Message"), strReference, "Reference");
    }

    /**
     * Function : To validate success page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 04/06/2020   C966003        Newly created
     */
    public void success_Light() throws Exception {
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 6)) {
            waitForPageLoaded();
        }
        report.updateTestLog("success_Light", " :: Page Screenshot :: ", Status_CRAFT.SCREENSHOT);
        isElementDisplayedWithLog(getObject("SendMoney.btnBacktoHome"),
                "Back to home","Success",3);
        getFullScreenshot("Success");
    }

    /**
     * Function : To validate success page
     * Created by : C966003
     * Update on    Updated by     Reason
     * 04/06/2020   C966003        Newly created
     */
    public void clickBackToHome_Success_Light() throws Exception {

        isElementDisplayedWithLog(getObject("SendMoney.btnBacktoHome"),
                "Back to home","Success",3);
        click(getObject("SendMoney.btnBacktoHome"),"Back to home");
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 6)) {
            waitForPageLoaded();
        }
    }

    /**
     * Function : click SendMoney button on add payee success
     * Created by : C966003
     * Update on    Updated by     Reason
     * 04/06/2020   C966003        Newly created
     */
    public void clickSendMoney_Success_Light() throws Exception {

        isElementDisplayedWithLog(getObject("SendMoney.btnSendMoney"),
                "Send Money button","Success",3);
        click(getObject("SendMoney.btnSendMoney"),"Send Money");
        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 6)) {
            waitForPageLoaded();
        }
    }

}

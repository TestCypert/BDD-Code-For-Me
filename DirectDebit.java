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
 * Created by C103403 on 16/01/2019.
 */
public class DirectDebit extends WebDriverHelper {
    public String Referencenumber = "";
    public  String TimeofRequest = "";

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public DirectDebit(ScriptHelper scriptHelper) {
        super(scriptHelper);
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


    public void launchPaymentsTab() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.selectPaymentOption();
        Thread.sleep(1000);
    }

    /**
     * Function: To verify Direct Debit page
     * Update on     Updated by     Reason
     * 15/04/2019     C101965       Done code clean up activity
     */

    public void verifyDirectDebitPage() throws Exception {
        if (isElementDisplayed(getObject("DirectDebit.lblLimitMoreInfoLink"), 6)) {
            click(getObject("DirectDebit.lblLimitMoreInfoLink"), "OK Button");
            report.updateTestLog("verifyDirectDebitPage", "More Info LInk for Limit mandate by amount or frequency is displayed", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyDirectDebitPage", "More Info LInk for Limit mandate by amount or frequency is not displayed", Status_CRAFT.FAIL);
        }
        HomePage homepage = new HomePage(scriptHelper);
        homepage.verifyPopUpDetails();
        if(deviceType.equalsIgnoreCase("Web")){click(getObject("DirectDebit.lblLimitMoreInfoPopupOK"), "Clicked on OK button");}
        else{clickJS(getObject("DirectDebit.lblLimitMoreInfoPopupOK"), "Clicked on OK button");}

        if (isElementDisplayed(getObject("DirectDebit.lblAddMoreInfoLink"), 6)) {
            click(getObject("DirectDebit.lblAddMoreInfoLink"), "OK Button");
            report.updateTestLog("verifyDirectDebitPage", "More Info LInk for Add or delete creditors to black or white list is displayed", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyDirectDebitPage", "More Info LInk for Add or delete creditors to black or white list is not displayed", Status_CRAFT.FAIL);
        }
//        homepage.verifyPopUpDetailsE2E();
        if(deviceType.equalsIgnoreCase("Web")){click(getObject("DirectDebit.lblLimitMoreInfoPopupOK"), "Clicked on OK button");}
        else{clickJS(getObject("DirectDebit.lblLimitMoreInfoPopupOK"), "Clicked on OK button");}
        Thread.sleep(1000);

        /* Verify if Need a hand is displayed
         */
        if (isElementDisplayed(getObject("DirectDebit.lblNeedHand"), 6)) {
            report.updateTestLog("verifyDirectDebitPage", "Need a Hand is displayed", Status_CRAFT.DONE);
            clickJS(getObject("DirectDebit.lblNeedHand"), "Need a Hand");
        } else {
            report.updateTestLog("verifyDirectDebitPage", "Need a Hand is not displayed", Status_CRAFT.FAIL);
        }

    }


    /**
     * Function to Verify Cancel Direct Debit Mandate
     */
    public void cancelDirectDebitMandate() throws Exception {
        if(isElementDisplayedWithLog(getObject("DirectDebit.tileCancelMandate"),"Cancel Direct Debit mandate tile","Direct Debit", 3)){
            clickJS(getObject("DirectDebit.tileCancelMandate"), "Direct Debit Mandate");
            waitforPresenceOfElementLocated(getObject("DirectDebit.btnContinue"));
            isElementDisplayedWithLog(getObject("DirectDebit.btnContinue"),"Cancel Direct Debit mandate continue button","Direct Debit", 3);
            clickJS(getObject("DirectDebit.btnContinue"), "Continue Button");
            Thread.sleep(2000);
        }else{
            report.updateTestLog("cancelDirectDebitMandate", "'Cancel Direct Debit mandate tile' is not displayed or found", Status_CRAFT.FAIL);
        }
    }

    public void CreditorDetails() throws Exception {
        String strCreditorName = dataTable.getData("General_Data", "CreditorName");
        String strCreditorID = dataTable.getData("General_Data", "CreditorID");
        String strUMR = dataTable.getData("General_Data", "UMR");
        isElementDisplayedWithLog(getObject("DirectDebit.txtCreditorName"), "Creditor Name", "SEPA direct debit Services", 10);
        clickJS(getObject("DirectDebit.txtCreditorName"), "Creditor Name");
        sendKeys(getObject("DirectDebit.txtCreditorName"), strCreditorName, "Creditor Name Entered");
        Thread.sleep(1000);
        isElementDisplayedWithLog(getObject("DirectDebit.txtCreditorId"), "Creditor Id", "SEPA direct debit Services", 10);
        clickJS(getObject("DirectDebit.txtCreditorId"), "Creditor ID");
        sendKeys(getObject("DirectDebit.txtCreditorId"), strCreditorID, "Creditor Id Entered");
        Thread.sleep(2000);
        isElementDisplayedWithLog(getObject("DirectDebit.txtUMR"), "Unique Mandate Reference (UMR)", "SEPA direct debit Services", 10);
        clickJS(getObject("DirectDebit.txtUMR"), "Unique Mandate Reference (UMR)");
        sendKeys(getObject("DirectDebit.txtUMR"), strUMR, "Unique Mandate Reference (UMR)");

    }


    public void DDYourIBANText() throws Exception {
        String strIbanTextvalue = "Your BOI IBAN";
        String strIbanText = getText(getObject("xpath~//div[@id='C5__p1_QUE_521FB69B20935F1B5590243']"));
        if (strIbanTextvalue.equals(strIbanText)) {
            report.updateTestLog("IBAN text ", "IBAN text is " + strIbanText, Status_CRAFT.PASS);
        }else{
            report.updateTestLog("IBAN text ", "IBAN text is not as expected, Expected::'" + strIbanTextvalue +"', Actual:'"+strIbanText+"'", Status_CRAFT.PASS);
        }
    }

    public void DirectDebitDetails() throws Exception {
        String strDDIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strAmountofRecentPay = dataTable.getData("General_Data", "Pay_Amount");
        String strCancelDD = dataTable.getData("General_Data", "DirectDebitCancel");

        if (isElementDisplayed(getObject("DirectDebit.drpdwnIBAN"), 3)) {
            Thread.sleep(1000);
            clickJS(getObject("DirectDebit.drpdwnIBAN"), "IBAN Dropdown");
            Thread.sleep(2000);
            boolean bflagfound = false;
            List<WebElement> IbanAccntList = findElements(getObject("xpath~//ul[@class='exp_elem_list_widget list']/li"));
            for (int i = 1; i <= IbanAccntList.size(); i++) {
                String strIban = getText(getObject("xpath~//ul[@class='exp_elem_list_widget list']/li" + "[" + i + "]"));
                if (strDDIBAN.equalsIgnoreCase(strIban)) {
                    clickJS(getObject("xpath~//ul[@class='exp_elem_list_widget list']/li" + "[" + i + "]"), "IBAN");
                    report.updateTestLog("Select IBAN", strIban + "IBAN selected from Your BOI iban dropdown", Status_CRAFT.PASS);
                    bflagfound=true;
                    //return;
                    break;
                }
            }
            if(!bflagfound){
                report.updateTestLog("Select IBAN", strDDIBAN + "IBAN NOT found in the 'Select Your BOI iban' dropdown", Status_CRAFT.FAIL);
            }
            waitforPresenceOfElementLocated(getObject("DirectDebit.txtAmountRecentpay"));
            isElementDisplayedWithLog(getObject("DirectDebit.txtAmountRecentpay"), "Amount", "SEPA Direct Debit", 5);
            sendKeys(getObject("DirectDebit.txtAmountRecentpay"), strAmountofRecentPay, " Amount Entered :: ");
            Thread.sleep(1000);
        }

        if (strCancelDD.equalsIgnoreCase("Yes")) {
            Thread.sleep(2000);
            ScrollToVisibleJS("DirectDebit.lblcancelMandateYes");
            clickJS(getObject("DirectDebit.lblcancelMandateYes"), "'Yes' clicked for Canceling Mandate before first time");
        } else {
            clickJS(getObject("DirecDebit.lblcancelMandateNo"), "'No' clicked For Canceling Mandate Cancel Mandate before first time");
        }

    }



    public void DirectDebitContactDetails() throws Exception {
        String strFirstName = dataTable.getData("General_Data", "FirstName");
        String strLastName = dataTable.getData("General_Data", "LastName");
        String strPhoneNumber = dataTable.getData("General_Data", "MobileNumber");
        waitForElementPresent(getObject("DirectDebit.txtFirstName"), 3);
        isElementDisplayedWithLog(getObject("DirectDebit.txtFirstName"), "First Name", "SEPA Direct Debit Services", 10);
        waitForElementToClickable(getObject("DirectDebit.txtFirstName") , 3);
        clickJS(getObject("DirectDebit.txtFirstName"), "First Name Textbox");
        ScrollToVisibleJS("DirectDebit.txtFirstName");
        waitForPageLoaded();waitForPageLoaded();
        driver.findElement(getObject("DirectDebit.txtFirstName")).clear();
        sendKeys(getObject("DirectDebit.txtFirstName"), strFirstName, "First Name Entered");
        if (isMobile ){
            waitForPageLoaded();waitForPageLoaded();
            sendKeysJS(getObject("DirectDebit.txtFirstName"), strFirstName);
        }
        waitForElementPresent(getObject("DirectDebit.txtLastName"), 5);
        isElementDisplayedWithLog(getObject("DirectDebit.txtLastName"), "Last Name", "SEPA Direct Debit Services", 10);
        waitForElementToClickable(getObject("DirectDebit.txtFirstName") , 3);
        clickJS(getObject("DirectDebit.txtLastName"), "Last Name Text box");
        sendKeys(getObject("DirectDebit.txtLastName"), strLastName, "Last Name Entered");
        waitForElementPresent(getObject("DirectDebit.txtPhonenumber"), 3);
        isElementDisplayedWithLog(getObject("DirectDebit.txtPhonenumber"), "Phone Number", "SEPA Direct Debit Services", 10);
        clickJS(getObject("DirectDebit.txtPhonenumber"), "Phone Number textBox");
        sendKeys(getObject("DirectDebit.txtPhonenumber"), strPhoneNumber, "PhoneNumber Entered");
        Thread.sleep(1000);
        ScrollToVisibleJS("DirectDebit.txtFirstName");
        if (isMobile ){
            sendKeysJS(getObject("DirectDebit.txtFirstName"), strFirstName);
        }
    }



    public void DirectDebitNotification() throws Exception {
        String strNotificationReq = dataTable.getData("General_Data", "NotificationReq");
        String StrContactPreference = dataTable.getData("General_Data", "ContactPreference");
        String strEmail = dataTable.getData("General_Data", "Email");
        String strPhone = dataTable.getData("General_Data", "Phone");

        isElementDisplayedWithLog(getObject("DirectDebit.NotiText"), " 'Notification' Label", "SEPA Direct Debit Services", 10);
        if (strNotificationReq.equalsIgnoreCase("Yes")) {

            clickJS(getObject("DirectDebit.lblNotifyRequestYes"), "Yes Button for Notification");
            Thread.sleep(3000);
            String expNotitxt1 = dataTable.getData("General_Data", "NotificationText1");
            String expNotitxt2 = dataTable.getData("General_Data", "NotificationText2");
            String Notitxt1 = getText(getObject(deviceType()+"DirectDebit.NotiText1"), 10).replace("\n", "");
            String Notitxt2 = getText(getObject("DirectDebit.NotiText2"), 10).replace("\n", "");
            if (Notitxt1.equalsIgnoreCase(expNotitxt1)) {
                report.updateTestLog("verifyRefuseAllDDNotificationOption", "Notification Text is displayed as Expected :" + expNotitxt1, Status_CRAFT.PASS);
                Thread.sleep(2000);
            } else {
                report.updateTestLog("verifyRefuseAllDDNotificationOption", "Notification Text is NOT displayed as Expected. Actual :" + Notitxt1, Status_CRAFT.FAIL);
            }

            if (Notitxt2.equalsIgnoreCase(expNotitxt2)) {
                report.updateTestLog("verifyRefuseAllDDNotificationOption", "Notification:Contact Preference Question/label is displayed as Expected :" + expNotitxt2, Status_CRAFT.PASS);
                Thread.sleep(2000);
            } else {
                report.updateTestLog("verifyRefuseAllDDNotificationOption", "Notification:Contact Preference Question/label is NOT displayed as Expected. Actual :" + Notitxt2, Status_CRAFT.FAIL);
            }


            waitForElementPresent(getObject("DirectDebit.lblContactEmail"), 10);
            if (StrContactPreference.equalsIgnoreCase("Email")) {
                clickJS(getObject("DirectDebit.lblContactEmail"), "Email Radio");
                waitForElementPresent(getObject("DirectDebit.txtContactEmail"), 5);
                clickJS(getObject("DirectDebit.txtContactEmail"), "Email Text box");
                Thread.sleep(1000);
                sendKeys(getObject("DirectDebit.txtContactEmail"), strEmail, "Email Entered");
                ScrollToVisibleJS("DirectDebit.btnContinue");
                Thread.sleep(1000);
                clickJS(getObject("DirectDebit.btnContinue"), "Continue button");
                waitForPageLoaded();
            } else {
                clickJS(getObject("DirectDebit.lblContactMobile"), "Mobile Radio button");
                waitForElementPresent(getObject("DirectDebit.txtMobileNumber"), 5);
                clickJS(getObject("DirectDebit.txtMobileNumber"), "Mobile Text box");
                Thread.sleep(1000);
                sendKeys(getObject("DirectDebit.txtMobileNumber"), strPhone, "Phone Number Entered");
                ScrollToVisibleJS("DirectDebit.btnContinue");
                Thread.sleep(3000);
                clickJS(getObject("DirectDebit.btnContinue"), "Continue button");
                Thread.sleep(5000);
            }

        } else {
            clickJS(getObject("DirectDebit.lblNotifyRequestNo"), "No Button for Notification");
            waitForElementPresent(getObject("DirectDebit.btnContinue"), 5);
            clickJS(getObject("DirectDebit.btnContinue"), "Continue button");
        }

    }

    public void DirectDebitToolTip() throws Exception {
        Thread.sleep(1000);
        String strCreditoIDText = "The direct debit scheme ID of the creditor with whom you've signed the direct debit mandate.To find it, contact your creditor or check your recent 365direct debit transactions where extended payment info is available.";
        // dataTable.getData("General_Data", "NotificationReq");
        String strUMRtext = "The Unique Mandate Reference (UMR) is the ID record associated with the mandate.To find it, contact your creditor or check your recent 365direct debit transactions where extended payment info is available.";
        String strCreditoNameText = "The company with whom you signed the direct debit mandate.";

        clickJS(getObject("DirectDebit.CreditorNameToolTip"), "Crd");
        Thread.sleep(1000);
        String StrCredNAme = getText(getObject("DirectDebit.CreditorNameTTText"), 10);
        if (strCreditoNameText.equalsIgnoreCase(StrCredNAme)) {
            report.updateTestLog("Creditor Name ID ", "Creditor Name Tool tip text matches ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Creditor Name ID ", "Creditor Name Tool tip text does not matches ", Status_CRAFT.FAIL);
        }
        clickJS(getObject("DirectDebit.CreditorIDtooltip"), "CreatorID tool tip");
        String StrCreditorId = getText(getObject("DirectDebit.CreditorToolTipText"), 10).replace("\n", "");
        if (StrCreditorId.equalsIgnoreCase(strCreditoIDText)) {
            Thread.sleep(1000);
            clickJS(getObject("DirectDebit.btnCreditorIdOk"), "OK button clicked");
            report.updateTestLog("Creditor ID ", "Creditor Id Tool tip text matches ", Status_CRAFT.PASS);
            Thread.sleep(1000);
        } else {
            report.updateTestLog("Creditor ID ", "Creditor Id Tool tip text does not matches ", Status_CRAFT.FAIL);
        }
        click(getObject("DirectDebit.UmrToolTip"), "Urm tool tip clicked");
        String strUmrText = getText(getObject("DirectDebit.UMRToolTipText"), 10).replace("\n", "");
        if (strUmrText.equalsIgnoreCase(strUMRtext)) {
            click(getObject("DirectDebit.btnUMROk"), "OK button clicked");
            report.updateTestLog("UMR ID ", "UMR Id Tool tip text matches ", Status_CRAFT.PASS);
            Thread.sleep(2000);
        } else {
            report.updateTestLog("UMR ID ", "UMR Id Tool tip text does not matches ", Status_CRAFT.FAIL);
        }
    }
     /*
    Function for Review screen
     */

    public void DirectDebitReview() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        Thread.sleep(2000);
        if (isElementDisplayed(getObject("DirectDebit.txtReview"),3)){
            homePage.verifyElementDetails("DirectDebit.txtReview");
            Thread.sleep(2000);
            String strIbanTextReview = "Your BOI IBAN";
            String strIbanTextvalidate = getText(getObject("xpath~//span[@class='accessibilityStyle'][text()='Your B O I I ban']/preceding-sibling::*"));
            if (strIbanTextReview.equals(strIbanTextvalidate)) {
                report.updateTestLog("IBAN text ", "IBAN text is " + strIbanTextvalidate, Status_CRAFT.PASS);
            }else{
                report.updateTestLog("IBAN text ", "IBAN text is not as expected. Expected:'"+strIbanTextReview+"' Actual:" + strIbanTextvalidate, Status_CRAFT.FAIL);
            }
            Thread.sleep(2000);
            String strbuttoncheck = "Confirm";
            String strbuttontext = getText(getObject("DirectDebit.btnconfirm"));
            if (strbuttontext.equalsIgnoreCase(strbuttoncheck)) {
                report.updateTestLog("Button text ", "Button present on screen is " + strbuttontext, Status_CRAFT.PASS);
                clickJS(getObject("DirectDebit.btnconfirm"), "Confirm button Clicked");
            } else {
                report.updateTestLog("Button text ", "Button present on screen is not " + strbuttontext, Status_CRAFT.PASS);
            }

        }else {
            homePage.verifyElementDetails("xpath~//div[@class='ecDIB  boi-full-width boi-mt-20 boi-tg__font--regular boi-tg__size--default  ']");
        }
    }


    /*
    Function for Confirmation screen
     */

    public void DirectDebitConfirmation() throws Exception {
        if (isElementDisplayed(getObject("DirectDebit.lblRequestSent"), 5)) {
            report.updateTestLog("verifyDDConfirmationPage", "'Request Sent' text is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyDDConfirmationPage", "Request Sent' text  is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("DirectDebit.ConfirmationText"), " 'Thank You' Message", "SEPA Direct Debit Services Confirmation", 10);
        String strMessagecheck = dataTable.getData("General_Data","ConfirmationMessage");
        String strMessagetext = getText(getObject("DirectDebit.ConfirmationMessage"));
        if (strMessagetext.contains(strMessagecheck)) {
            report.updateTestLog("verifyDDConfirmationPage", "Confirmation Message is displayed successfully.Confirmation Message:" + strMessagecheck, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyDDConfirmationPage", "Confirmation Message is NOT displayed successfully.Confirmation Message: " + strMessagetext, Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DirectDebit.Reference"), 5) && isElementDisplayed(getObject("DirectDebit.Timestamp"), 5)) {
            report.updateTestLog("Referencetimestamp", "Reference and Time Stamp are displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Referencetimestamp", "Reference and Time Stamp are not displayed on screen", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DirectDebit.btnBacktoHome"), 5)) {

            isElementDisplayedWithLog(getObject("DirectDebit.Reference"),"Reference Number","Reference number is dispalyed",5);
            Referencenumber = getText(getObject("DirectDebit.Reference"),10);
//            String FinalRef[] = Referencenumber.split(":");
//            String FinalRef1 = FinalRef[1].trim();

            isElementDisplayedWithLog(getObject("DirectDebit.Timestamp"),"Time of Request","Time of request is dispalyed",5);
            TimeofRequest = getText(getObject("DirectDebit.Timestamp"),5);
            clickJS(getObject("DirectDebit.btnBacktoHome"), "Back to Home");
            Thread.sleep(2000);
            report.updateTestLog("BacktoHome button ", "BacktoHome button Clicked on confirmation screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("BacktoHome button ", "BacktoHome button not Clicked on confirmation screen", Status_CRAFT.FAIL);
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




    /**
     * Function to verify all the error validation for fields on Direct Debit form
     */


    public void verifyblankfeildValidations() throws Exception {

        clickContinue_DD();
        String strerrormessage_creditor = "Please enter a Creditor Name";
        String strerrormessage_UMR = "Please enter a Unique Mandate Reference";
        String strerrormessage_IBAN = "Please select Debtor IBAN";
        String strerrormessage_FirstName = "Please enter a First Name";
        String strerrormessage_LastName = "Please enter a Last Name";
        String strerrormessage_Notification = "Please select a notification preference";

        waitForElementPresent(getObject("DD.errorMsg_Blank_Creditor"), 5);
        String strErrorMsg_Blank_Creditor = getText(getObject("DD.errorMsg_Blank_Creditor"));

        waitForElementPresent(getObject("DD.errorMsg_Blank_UMR"), 1);
        String strErrorMsg_Blank_UMR = getText(getObject("DD.errorMsg_Blank_UMR"));

        waitForElementPresent(getObject("DD.errorMsg_Blank_IBAN"), 1);
        String strErrorMsg_Blank_IBAN = getText(getObject("DD.errorMsg_Blank_IBAN"));

        waitForElementPresent(getObject("DD.errorMsg_Blank_Firstname"), 1);
        String strErrorMsg_Blank_Firstname = getText(getObject("DD.errorMsg_Blank_Firstname"));

        waitForElementPresent(getObject("DD.errorMsg_Blank_Lastname"), 1);
        String strErrorMsg_Blank_Lastname = getText(getObject("DD.errorMsg_Blank_Lastname"));

        waitForElementPresent(getObject("DD.errorMsg_Blank_Notification"), 1);
        String strErrorMsg_Blank_Notification = getText(getObject("DD.errorMsg_Blank_Notification"));

        if (isElementDisplayed(getObject("DD.errorMsg_Blank_Creditor"), 1) && (strerrormessage_creditor.equalsIgnoreCase(strErrorMsg_Blank_Creditor))) {
            report.updateTestLog("enterBlankCreditor", "Error msg is displayed as : " + strErrorMsg_Blank_Creditor, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterBlankCreditor", "Expected error msg '"+strerrormessage_creditor+"' is not displayed, Actual:"+strErrorMsg_Blank_Creditor, Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DD.errorMsg_Blank_UMR"), 1) && (strerrormessage_UMR.equalsIgnoreCase(strErrorMsg_Blank_UMR))) {
            report.updateTestLog("enterBlankUMR", "Error msg is displayed as : " + strErrorMsg_Blank_UMR, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterBlankUMR", "Expected error msg '"+strerrormessage_UMR+"' is not displayed, Actual:"+strErrorMsg_Blank_UMR, Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DD.errorMsg_Blank_IBAN"), 1) && (strerrormessage_IBAN.equalsIgnoreCase(strErrorMsg_Blank_IBAN))) {
            report.updateTestLog("enterBlankIBAN", "Error msg is displayed as : " + strErrorMsg_Blank_IBAN, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterBlankIBAN", "Expected error msg '"+strerrormessage_IBAN+"' is not displayed, Actual:"+strErrorMsg_Blank_IBAN, Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("DD.errorMsg_Blank_Firstname"), 1) && (strerrormessage_FirstName.equalsIgnoreCase(strErrorMsg_Blank_Firstname))) {
            report.updateTestLog("enterBlankFirstname", "Error msg is displayed as : " + strErrorMsg_Blank_Firstname, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterBlankFirstname", "Expected error msg '"+strerrormessage_FirstName+"' is not displayed, Actual:"+strErrorMsg_Blank_Firstname, Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DD.errorMsg_Blank_Lastname"), 1) && (strerrormessage_LastName.equalsIgnoreCase(strErrorMsg_Blank_Lastname))) {
            report.updateTestLog("enterBlankLastname", "Error msg is displayed as : " + strErrorMsg_Blank_Lastname, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterBlankLastname", "Expected error msg '"+strerrormessage_LastName+"' is not displayed, Actual:"+strErrorMsg_Blank_Lastname, Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DD.errorMsg_Blank_Notification"), 1) && (strerrormessage_Notification.equalsIgnoreCase(strErrorMsg_Blank_Notification))) {
            report.updateTestLog("enterBlankNotification", "Error msg is displayed as : " + strErrorMsg_Blank_Notification, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("enterBlankNotification", "Expected error msg '"+strerrormessage_Notification+"' is not displayed, Actual:"+strErrorMsg_Blank_Notification, Status_CRAFT.FAIL);
        }
    }

    public void veifyerrorcancelDD() throws Exception {
        String strCreditorID = dataTable.getData("General_Data", "CreditorID");
        String strAmountofRecentPay = dataTable.getData("General_Data", "Pay_Amount");

        sendKeys(getObject("DirectDebit.txtCreditorId"), strCreditorID, "Creditor Id Entered");
        Thread.sleep(1000);
        sendKeys(getObject("DirectDebit.txtAmountRecentpay"), strAmountofRecentPay, "Amount Entered");
        Thread.sleep(1000);
        clickContinue_DD();
        Thread.sleep(1000);
        waitForElementPresent(getObject("DD.errorMsg_CreditorId"), 5);
        waitForElementPresent(getObject("DD.errorMsg_amount"), 5);

        if (isElementDisplayed(getObject("DD.errorMsg_CreditorId"), 5)) {
            String strErrorMsg_BlankNotification = getText(getObject("DD.errorMsg_CreditorId"));

            report.updateTestLog("enterinvalidCreditorID", "Error msg is displayed as : " + strErrorMsg_BlankNotification, Status_CRAFT.PASS);
        }
        if (isElementDisplayed(getObject("DD.errorMsg_amount"), 5)) {
            String strErrorMsg_BlankNotification = getText(getObject("DD.errorMsg_amount"));

            report.updateTestLog("enterinvalidamount", "Error msg is displayed as : " + strErrorMsg_BlankNotification, Status_CRAFT.PASS);
        }
    }

    /**
     * Function to click on continue button on Direct Debit form
     */
    public void clickContinue_DD() throws Exception {
        if (isElementDisplayed(getObject("DirectDebit.btnContinue"), 7)) {
            clickJS(getObject("DirectDebit.btnContinue"), "Continue");
            waitForPageLoaded();
        }
    }

    public void clkAccTypeAndValidateAccount() throws Exception {
        boolean bflag = false;
        String accType = dataTable.getData("General_Data", "Account_Type");
        // String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = deviceType() + "home.parentaccount_new";

        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyCheckContentNotPresent", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        ScrollToVisibleJS(strParentObject);

        // && (strRowText.contains(strAccountIBAN.toUpperCase()))
        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();
            if ((strRowText.contains(accType.toUpperCase()))) {
                //ScrollToVisibleJS(strParentObject);;
                //oUIRows.get(j).click();
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                //Thread.sleep(3000);
                JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                executor.executeScript("arguments[0].click();", oUIRows.get(j));
                //Thread.sleep(3000);
                report.updateTestLog("verifyAccountStatement_AllAccounts", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                bflag = true;
                break;
            }
        }
    }

    public void clickExtendedTransaction() throws Exception {

        if (isElementDisplayed(getObject("DirectDebit.btnLoadMore"), 5)) {
            do {
                waitForPageLoaded();
                scrollToView(getObject("DirectDebit.btnLoadMore"));
                clickJS(getObject("DirectDebit.btnLoadMore"), "Show more button");
                waitForPageLoaded();
                Thread.sleep(3000);
            } while (isElementDisplayed(getObject("DirectDebit.btnLoadMore"), 3));
        } else {
            report.updateTestLog("verifyShowMoreButton", "show more button is not present for selected account", Status_CRAFT.DONE);
        }

        //isMobile
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            List<WebElement> accntList = findElements(getObject(deviceType() + "Completed.accountlist"));
//        if (isMobile) {
//            List<WebElement> accntList =driver.findElements(By.xpath("//table[@id='C5__C1__TBL_BFD8DEEF0DC0AB48248009']/tbody/tr"));
            int aCount = accntList.size();
            report.updateTestLog("Accounts List", "'Accounts List' is displayed successfully", Status_CRAFT.PASS);
            for (int i = 1; i < aCount; i++) {
                String amount1 = getText(getObject("xpath~//div[@id='C5__C1__row_QUE_EB9FE540F04890D6654242_R" + i + "']"), 2);
                //div[@id='C5__C1__QUE_EB9FE540F04890D6654242_R" + i + "']
                WebElement minus = driver.findElement(By.cssSelector("div.C5__C1__QUE_EB9FE540F04890D6654242_R" + i + ":before"));
                if (minus.getCssValue("content").equals("-") && isElementDisplayed(getObject("xpath~//span[text()=' View details ']"),2)) {
                    clickJS(getObject("xpath~//span[@id='C5__C1__QUE_A27BA6EB7326FBB91249050_R" + i + "']"), "View Details click");
                    break;
                }
            }
        } else {
            List<WebElement> accntList = findElements(getObject(deviceType()+"Completed.accountlist"));
            int aCount = accntList.size();
            report.updateTestLog("Accounts List", "'Accounts List' is displayed successfully", Status_CRAFT.PASS);
            for (int i = 1; i <= aCount; i++) {
                String amount1 = getText(getObject("xpath~//td[@id='C5__C1__p4_QUE_BFD8DEEF0DC0AB48248014_R" + i + "']"), 2);
                System.out.println(amount1);
                click(getObject("xpath~//td[@id='C5__C1__p4_QUE_BFD8DEEF0DC0AB48248014_R" + (i+2) + "']"), "View Details click");
                break;

            }
            /*for (int i = 1; i <= aCount; i++) {
                accntList.get(i).click();
                break;
            }*/
        }

    }

//        if(isElementDisplayed(getObject("DirectDebit.lblExtendedTransaction"),10)){
//           click(getObject("DirectDebit.lblExtendedTransaction"));
//            Thread.sleep(1000);
//            report.updat  eTestLog("clickExtendedTransaction", "view details Button displayed", Status_CRAFT.PASS);
//        }
//        else{
//            report.updateTestLog("clickExtendedTransaction", "view details Button not displayed", Status_CRAFT.FAIL);
//        }


    public void clickCancelButton() throws Exception {
        if (isElementDisplayed(getObject("DirectDebit.btnCancel"), 5)) {
            clickJS(getObject("DirectDebit.btnCancel"), "Cancel button");
            Thread.sleep(1000);
        } else {
            report.updateTestLog("clickCancelButton", "Cancel Button not displayed", Status_CRAFT.FAIL);
        }
    }

    public void clickContinuePopup() throws InterruptedException {
        if (isElementDisplayed(getObject("DirectDebit.btnContinuePopup"), 5)) {
            clickJS(getObject("DirectDebit.btnContinuePopup"), "Continue on Pop up");
        } else {
            report.updateTestLog("Continue on Pop up", "Continue on Pop up is not displayed", Status_CRAFT.FAIL);
        }

    }

    public void DirectDebitMandateExtenViewForm() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        Thread.sleep(2000);
        homePage.verifyElementDetails("DirectDebit.CDDMExtendedView");
        Thread.sleep(2000);
        // click(getObject("DirectDebit.btnContinue"),"Continue button Clicked");
    }


    /**
     * Verify Direct Debits options CFPUR-4021
     */

    public void verifyDD_options() throws Exception {

        if (isElementDisplayed(getObject("DirectDebit.lblNeedHand"), 6)) {
            report.updateTestLog("verifyDirectDebitPage", "Need a Hand is displayed", Status_CRAFT.DONE);
            click(getObject("DirectDebit.lblNeedHand"), "Need a Hand");
        } else {
            report.updateTestLog("verifyDD_options", "Need a Hand is not displayed", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("DirectDebit.tileCancelMandate"), 3)) {
            report.updateTestLog("Cancel Direct debit Mandate", "Cancel Direct Debit Mandate Tile is Displayed", Status_CRAFT.PASS);
            click(getObject("DirectDebit.tileCancelMandate"), "Direct Debit Mandate");
            Thread.sleep(1000);
        } else {
            report.updateTestLog("verifyDD_options", "Cancel Direct debit Mandate is not displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DirectDebit.tileRefuse"), 3)) {
            report.updateTestLog("Refuse Direct debit", "Refuse Direct debit Tile is Displayed", Status_CRAFT.PASS);
            clickJS(getObject("DirectDebit.tileRefuse"), "Direct Debit Refuse Payment");
            Thread.sleep(1000);
        } else {
            report.updateTestLog("verifyDD_options", "Refuse Direct debit Tile is not displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DirectDebit.tileReactivate"), 3)) {
            report.updateTestLog("Reactivate Direct debit", "Reactivate Direct debit Tile is Displayed", Status_CRAFT.PASS);
            clickJS(getObject("DirectDebit.tileReactivate"), "Direct Debit Reactivate Payment");
            Thread.sleep(1000);
        } else {
            report.updateTestLog("verifyDD_options", "Reactivate Direct debit Tile is not displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DirectDebit.tileBlock"), 3)) {
            report.updateTestLog("Block/Unblock Direct debit accounts", "Block/Unblock Direct debit Tile is Displayed", Status_CRAFT.PASS);
            clickJS(getObject("DirectDebit.tileBlock"), "Direct Debit Block/Unblock user");
            Thread.sleep(1000);
        } else {
            report.updateTestLog("verifyDD_options", "Block/Unblock Direct debit Tile is not displayed", Status_CRAFT.FAIL);
        }

        //TODO have to check its impact-Rajiv
        if (!isMobile) {
            if (isElementDisplayed(getObject("DirectDebit.tileMoreServices"), 3)) {
                report.updateTestLog("More Direct Debit services", "More Direct Debit services Tile is Displayed", Status_CRAFT.PASS);
                clickJS(getObject("DirectDebit.tileMoreServices"), "More Direct Debit services");
                Thread.sleep(1000);
            } else {
                report.updateTestLog("verifyDD_options", "More Direct Debit services Tile is not displayed", Status_CRAFT.FAIL);
            }

            //Checking more info popups
            if (isElementDisplayed(getObject("DirectDebit.Moreinfolink1"), 3)) {
                click(getObject("DirectDebit.Moreinfolink1"), "More info link");
                Thread.sleep(1000);
                //driver.switchTo().alert().accept();
                click(getObject("DirectDebit.Moreinfolink1Ok"), "OK button clicked");
                report.updateTestLog("verifyDD_options", "Limit Mandate popup present", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyDD_options", "Limit Mandate popup is not displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("DirectDebit.Moreinfolink2"), 3)) {
                click(getObject("DirectDebit.Moreinfolink2"), "More info link");
                Thread.sleep(1000);
                //driver.switchTo().alert().accept();
                click(getObject("DirectDebit.Moreinfolink2Ok"), "OK button clicked");
                report.updateTestLog("verifyDD_options", "Add/Delete creditor popup present", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyDD_options", "Add/Delete creditor popup is not displayed", Status_CRAFT.FAIL);
            }

        }
    }

    /**
     * Function to Verify Reactivate next DD
     */
    public void reactivatenextDD() throws Exception {
        String expReacNexttxt = dataTable.getData("General_Data", "VerifyContent");
        String expReaYouNeedtxt = dataTable.getData("General_Data", "DirectDebitOptionsText");
        String expReaUMRtxt = dataTable.getData("General_Data", "DirectionalText");

        isElementDisplayedWithLog(getObject(deviceType() + "DirectDebit.pageTitle"),
                "SEPA Direct debit Services title", "Direct Debit", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.tileReactivate"),
                "Reactivate next/all direct debits", "SEPA Direct Debit Services", 10);
        clickJS(getObject("DirectDebit.tileReactivate"), "Reactivate next/all Direct debits");
        Thread.sleep(1000);
        isElementDisplayedWithLog(getObject("DirectDebit.Reactivatenext"),
                "Reactivate next button", "Reactivate next/all debit debits block", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.Reactivateall"),
                "Reactivate all button", "Reactivate next/all debit debits block", 10);
        clickJS(getObject("DirectDebit.Reactivatenext"), "Reactivate next Payment");
        String reacNextText = getText(getObject("DirectDebit.reacNextText"));
        System.out.print(expReacNexttxt);
        if (reacNextText.equalsIgnoreCase(expReacNexttxt)) {
            report.updateTestLog("verifyReactiveNextDDOption", "Reactivate Next text for 'Reactivate next/all direct debits ' displayed is as Expected '" + expReacNexttxt + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyReactiveNextDDOption", "Reactivate Next text for 'Reactivate next/all direct debits is NOT as Expected , Actual ''" + expReacNexttxt + "", Status_CRAFT.FAIL);
        }
        String reactivateAllYou = getText(getObject("DirectDebit.reaAllYou"));
        if (reactivateAllYou.equalsIgnoreCase(expReaYouNeedtxt)) {
            report.updateTestLog("verifyReactiveNextDDOption", "Important Information Text for 'Reactivate next/all direct debits ' displayed is as Expected '" + expReaYouNeedtxt + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyReactiveNextDDOption", "Important Information Text for 'Reactivate next/all direct debits is NOT as Expected , Actual ''" + reactivateAllYou + "", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("DirectDebit.reaNextCreditorText"),
                "Creditor name & ID", "Reactivate next/all direct debits", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.reaNextCreditorIDlink"),
                "What is a creditor ID?", "Reactivate next/all direct debits", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.reaNextUMRText"),
                "Unique mandate reference", "Reactivate next/all direct debits", 10);
        String reaNextUMR = getText(getObject("DirectDebit.reaNextUMRlink"));
        if (reaNextUMR.equalsIgnoreCase(expReaUMRtxt)) {
            report.updateTestLog("verifyReactiveNextDDOption", "UMR link for 'Reactivate next/all direct debits ' displayed is as Expected '" + expReaUMRtxt + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyReactiveNextDDOption", "UMR link for 'Reactivate next/all direct debits is NOT as Expected : Actual '" + reaNextUMR + "", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("DirectDebit.ReacNextCont"),
                "Continue button", "Reactivate next/all debit debits block", 10);
        clickJS(getObject("DirectDebit.ReacNextCont"), "Continue");
        Thread.sleep(2000);
    }

    /**
     * Function to Verify Reactivate all DD
     */
    public void reactivateAllDD() throws Exception {
        String expReacNexttxt = dataTable.getData("General_Data", "VerifyContent");
        String expReaYouNeedtxt = dataTable.getData("General_Data", "DirectDebitOptionsText");
        String expReaUMRtxt = dataTable.getData("General_Data", "DirectionalText");

        isElementDisplayedWithLog(getObject(deviceType() + "DirectDebit.pageTitle"),
                "SEPA Direct debit Services title", "Direct Debit", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.tileReactivate"),
                "Reactivate next/all direct debits", "SEPA Direct Debit Services", 10);
        clickJS(getObject("DirectDebit.tileReactivate"), "Reactivate next/all Direct debits");
        Thread.sleep(1000);
        isElementDisplayedWithLog(getObject("DirectDebit.Reactivatenext"),
                "Reactivate next button", "Reactivate next/all debit debits block", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.Reactivateall"),
                "Reactivate all button", "Reactivate next/all debit debits block", 10);
        clickJS(getObject("DirectDebit.Reactivateall"), "Reactivate all Payment");
        String reacAllText = getText(getObject("DirectDebit.reacNextText"));
        if (reacAllText.equalsIgnoreCase(expReacNexttxt)) {
            report.updateTestLog("verifyReactiveAllDDOption", "Reactivate all text for 'Reactivate next/all direct debits ' displayed is as Expected '" + expReacNexttxt + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyReactiveAllDDOption", "Reactivate all text for 'Reactivate next/all direct debits is NOT as Expected , Actual ''" + expReacNexttxt + "", Status_CRAFT.FAIL);
        }
        String reactivateAllYou = getText(getObject("DirectDebit.reaAllYou"));
        if (reactivateAllYou.equalsIgnoreCase(expReaYouNeedtxt)) {
            report.updateTestLog("verifyReactiveAllDDOption", "Important Information Text for 'Reactivate next/all direct debits ' displayed is as Expected '" + expReaYouNeedtxt + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyReactiveAllDDOption", "Important Information Text for 'Reactivate next/all direct debits is NOT as Expected , Actual ''" + reactivateAllYou + "", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("DirectDebit.reaNextCreditorText"),
                "Creditor name & ID", "Reactivate next/all direct debits", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.reaNextCreditorIDlink"),
                "What is a creditor ID?", "Reactivate next/all direct debits", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.reaNextUMRText"),
                "Unique mandate reference", "Reactivate next/all direct debits", 10);
        String reaAllUMR = getText(getObject("DirectDebit.reaNextUMRlink"));
        if (reaAllUMR.equalsIgnoreCase(expReaUMRtxt)) {
            report.updateTestLog("verifyReactiveAllDDOption", "UMR link for 'Reactivate next/all direct debits ' displayed is as Expected '" + expReaUMRtxt + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyReactiveAllDDOption", "UMR link for 'Reactivate next/all direct debits is NOT as Expected : Actual '" + reaAllUMR + "", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("DirectDebit.ReacAllCont"),
                "Continue button", "Reactivate next/all debit debits block", 10);
        clickJS(getObject("DirectDebit.ReacAllCont"), "Continue");
        Thread.sleep(2000);


    }

    public void ReactivateAllDirectDebitDetails() throws Exception {
        String strDDIBAN = dataTable.getData("General_Data", "IBAN_Number");
        isElementDisplayedWithLog(getObject("DirectDebit.drpdwnIBAN"), "Your BOI IBAN", "SEPA Direct Debit Services", 10);
        clickJS(getObject("DirectDebit.drpdwnIBAN"), "Your BOI IBAN Dropdown");
        Thread.sleep(2000);
        List<WebElement> allLinks = driver.findElements(getObject("DirectDebit.IBANlist"));
        if (allLinks.size() > 1) {
            int intIndex = 0;
            for (WebElement ele : allLinks) {
                String linkname = ele.getText();
                if (linkname.equalsIgnoreCase(strDDIBAN)) {
                    ele.click();
                    intIndex = intIndex++;
                    report.updateTestLog("ReactivateAllDirectDebitDetails", strDDIBAN + " IBAN is select from the Your BOI IBAN list  ", Status_CRAFT.PASS);
                    break;
                }
            }
        } else {
            report.updateTestLog("ReactivateAllDirectDebitDetails", strDDIBAN + " IBAN is not present in the list and is not clicked  ", Status_CRAFT.FAIL);
        }
    }


    public void ReactivateNextDirectDebitDetails() throws Exception {
        String strDDIBAN = "";
        String strAmountofRecentPay = "";
        //String strDate = "";
        //int daten = Integer.parseInt(strDate);
        if (isElementDisplayed(getObject("DirectDebit.drpdwnIBAN"), 3)) {
            strDDIBAN = dataTable.getData("General_Data", "IBAN_Number");
            clickJS(getObject("DirectDebit.drpdwnIBAN"), "Iban Dropdown");
            waitForJQueryLoad();
            waitForPageLoaded();
            List<WebElement> allLinks = driver.findElements(getObject("DirectDebit.IBANlist"));
            if (allLinks.size() >= 1) {
                for (WebElement ele : allLinks) {
                    String linkname = ele.getText();
                    if (linkname.contains(strDDIBAN)) {
                        ele.click();
                        report.updateTestLog("ReactivateAllDirectDebitDetails", strDDIBAN + " IBAN is clicked  ", Status_CRAFT.PASS);
                        break;
                    }
                }
            }
        }
        strAmountofRecentPay = dataTable.getData("General_Data", "Pay_Amount");
        sendKeys(getObject("DirectDebit.txtamountNextPay"), strAmountofRecentPay, "Amount Entered");
        waitForPageLoaded();
        clickJS(getObject("xpath~//*[@class='ui-datepicker-trigger fa fa-calendar']"), "Date Object");
        TransactionsPage datePicker = new TransactionsPage(scriptHelper);
        String strDateforCalender = dataTable.getData("General_Data", "DateRangeFrom");
        datePicker.enterDate(("DirectDebit.Reactivatedate"), strDateforCalender);
    }

    /**
     * CFPUR-93
     *
     * @throws Exception
     */
    public void blockDDservices() throws Exception {
        String strDDIBAN = dataTable.getData("General_Data", "IBAN_Number");
        if (isElementDisplayed(getObject("DirectDebit.tileBlock"), 3)) {
            report.updateTestLog("Block/Unblock Direct debit accounts", "Block/Unblock Direct debit Tile is displayed", Status_CRAFT.PASS);
            clickJS(getObject("DirectDebit.tileBlock"), "Direct Debit Block/Unblock user");
        }else {
            report.updateTestLog("Block/Unblock Direct debit accounts", "Block/Unblock Direct debit Tile is not displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DirectDebit.btnBlock"), 3)) {
            report.updateTestLog("Block/Unblock Direct debit button", "Block button is displayed", Status_CRAFT.PASS);
            clickJS(getObject("DirectDebit.btnBlock"), "Block Button");
        }else{
            report.updateTestLog("Block/Unblock Direct debit button", "Block button is not displayed", Status_CRAFT.FAIL);
        }
        String[] expectedErr = dataTable.getData("General_Data", "ErrorText").split("::");
        List<WebElement> elmTextBlocks= driver.findElements(By.xpath("//div[contains(@id,'row_DirectDebit-LandingPage-BlockContinue')]/../descendant::li"));
        for(int i=0;i<elmTextBlocks.size();i++) {
            String strActualTxt = elmTextBlocks.get(i).getText();
            if (strActualTxt.equals(expectedErr[i])) {
                report.updateTestLog("verifyText", "Test displayed :'"+ strActualTxt + "'is as expected", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyText", "Test displayed :'"+ strActualTxt + "'is not as expected", Status_CRAFT.FAIL);
            }
        }
        if (isElementDisplayed(getObject("DirectDebit.BlockUnblockContinue"),3)){
            report.updateTestLog("block and continue button", "Block continue button is displayed", Status_CRAFT.PASS);
            clickJS(getObject("DirectDebit.BlockUnblockContinue"), "Continue");
            Thread.sleep(3000);
        } else {
            report.updateTestLog("block and continue button", "Block continue button is not displayed", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("DirectDebit.drpdwnIBAN"), 3)) {
            Thread.sleep(1000);
            clickJS(getObject("DirectDebit.drpdwnIBAN"), "IBAN Dropdown");
            Thread.sleep(1000);
            List<WebElement> IbanAccntList = findElements(getObject("DirectDebit.IBANlist"));
            for (int i = 1; i <= IbanAccntList.size(); i++) {
                String strIban = getText(getObject("xpath~//button[@class='boi-rounded-1 boi_input tc-form-control exp_button_widget open']/following-sibling::ul/li" + "[" + i + "]"));
                if (strDDIBAN.equalsIgnoreCase(strIban)) {
                    clickJS(getObject("xpath~//button[@class='boi-rounded-1 boi_input tc-form-control exp_button_widget open']/following-sibling::ul/li" + "[" + i + "]"), "IBAN Dropdown");
                    report.updateTestLog("Select IBAN", strIban + "IBAN is selected from 'Your BOI IBAN dropdown'  ", Status_CRAFT.PASS);
                    Thread.sleep(1000);
                }
            }
        }
    }

    public void unblockDDservices() throws Exception {
        String strDDIBAN = dataTable.getData("General_Data", "IBAN_Number");
        if (isElementDisplayed(getObject("DirectDebit.tileBlock"), 3)) {
            report.updateTestLog("Block/Unblock Direct debit accounts", "Block/Unblock Direct debit Tile is displayed", Status_CRAFT.PASS);
            clickJS(getObject("DirectDebit.tileBlock"), "Direct Debit Block/Unblock user");
        }else {
            report.updateTestLog("Block/Unblock Direct debit accounts", "Block/Unblock Direct debit Tile is not displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DirectDebit.btnUnblock"), 3)) {
            report.updateTestLog("Block/Unblock Direct debit button", "Unblock button is displayed", Status_CRAFT.PASS);
            clickJS(getObject("DirectDebit.btnUnblock"), "Unblock Button");
        }else{
            report.updateTestLog("Block/Unblock Direct debit button", "Unblock button is not displayed", Status_CRAFT.FAIL);
        }
        String[] expectedErr = dataTable.getData("General_Data", "ErrorText").split("::");
        List<WebElement> elmTextBlocks= driver.findElements(By.xpath("//div[contains(@id,'LandingPage-BlockContinue')]/../../following-sibling::div[@class='tc-tab-pane']/descendant::li"));
        for(int i=0;i<elmTextBlocks.size();i++) {
            String strActualTxt = elmTextBlocks.get(i).getText();
            if (strActualTxt.trim().equalsIgnoreCase(expectedErr[i].trim())){
                report.updateTestLog("verifyText", "Test displayed :'"+ strActualTxt + "'is as expected", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyText", "Test displayed :'"+ strActualTxt + "'is not as expected", Status_CRAFT.FAIL);
            }
        }
        if (isElementDisplayed(getObject("DirectDebit.btnUnblockContinue"),3)){
            report.updateTestLog("unblock and continue button", "unblock continue button is displayed", Status_CRAFT.PASS);
            clickJS(getObject("DirectDebit.btnUnblockContinue"), "Continue");
            Thread.sleep(1000);
        } else {
            report.updateTestLog("unblock and continue button", "unblock continue button is not displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("DirectDebit.drpdwnIBAN"), 3)) {
            Thread.sleep(1000);
            clickJS(getObject("DirectDebit.drpdwnIBAN"), "IBAN Dropdown");
            Thread.sleep(1000);
            List<WebElement> IbanAccntList = findElements(getObject("DirectDebit.IBANlist"));
            for (int i = 1; i <= IbanAccntList.size(); i++) {
                String strIban = getText(getObject("xpath~//button[@class='boi-rounded-1 boi_input tc-form-control exp_button_widget open']/following-sibling::ul/li" + "[" + i + "]"));
                if (strDDIBAN.equalsIgnoreCase(strIban)) {
                    clickJS(getObject("xpath~//button[@class='boi-rounded-1 boi_input tc-form-control exp_button_widget open']/following-sibling::ul/li" + "[" + i + "]"), "IBAN Dropdown");
                    report.updateTestLog("Select IBAN", strIban + "IBAN is selected from 'Your BOI IBAN dropdown'  ", Status_CRAFT.PASS);
                    Thread.sleep(1000);
                }
            }
        }
    }

    /**
     * Function to Verify Refuse next DD
     */
    public void refusenextDD() throws Exception {
        if (isElementDisplayed(getObject("DirectDebit.tileRefuse"), 3)) {
            report.updateTestLog("refuseenextDD ", "Refuse Direct debit Tile is Displayed", Status_CRAFT.PASS);
            click(getObject("DirectDebit.tileRefuse"), "Refuse Direct debit");
            Thread.sleep(1000);
            if (isElementDisplayed(getObject("DirectDebit.Refuseenext"), 3)) {
                report.updateTestLog("Refuse Direct debit Next ", "Refuse Direct debit Next Payment button is Displayed", Status_CRAFT.PASS);
                click(getObject("DirectDebit.Refuseenext"), "Next Button");
                Thread.sleep(2000);

                if (isElementDisplayed(getObject("DirectDebit.Refusecontnext"), 3)) {
                    report.updateTestLog("Refuse Direct debit Continue ", "Refuse Direct debit Continue button is Displayed", Status_CRAFT.PASS);
                    clickJS(getObject("DirectDebit.Refusecontnext"), "Continue Button");
                    Thread.sleep(2000);
                }
            }
        }
    }

    public void FirstpaymentDD() throws Exception {
        String strFirstPay = dataTable.getData("General_Data", "AccountToggle");
        switch (strFirstPay.toUpperCase()){
            case "YES": {
                clickJS(getObject("DirectDebit.FirstPayYes"), "First Pay 'Yes'");
                report.updateTestLog("FirstpaymentDD ", "First Pay selected as Yes", Status_CRAFT.PASS);
                break;
            }
            case "NO":{
                clickJS(getObject("DirectDebit.FirstPayNo"), "First Pay 'NO'");
                report.updateTestLog("FirstpaymentDD ", "First Pay selected as NO", Status_CRAFT.PASS);
                break;
            }
            default:
                report.updateTestLog("FirstpaymentDD ", "First Pay is not present", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to Verify Refuse all DD
     */
    public void refuseallDD() throws Exception {

        String expRefAlltxt = dataTable.getData("General_Data", "VerifyContent");
        String expRefYouNeedtxt = dataTable.getData("General_Data", "DirectDebitOptionsText");
        String expRefUMRtxt = dataTable.getData("General_Data", "DirectionalText");

        isElementDisplayedWithLog(getObject(deviceType() + "DirectDebit.pageTitle"),
                "SEPA Direct debit Services title", "Direct Debit", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.tileRefuse"),
                "Refuse next/all direct debits", "SEPA Direct Debit Services", 10);
        clickJS(getObject("DirectDebit.tileRefuse"), "Refuse next/all Direct debits");
        Thread.sleep(1000);
        isElementDisplayedWithLog(getObject("DirectDebit.Refuseenext"),
                "Refuse next button", "Refuse next/all debit debits block", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.Refuseall"),
                "Refuse all button", "Refuse next/all debit debits block", 10);
        clickJS(getObject("DirectDebit.Refuseall"), "Refuse all Payment");
        String refuseAllText = getText(getObject("DirectDebit.refuseAllText"));
        Thread.sleep(1000);
        if (refuseAllText.equalsIgnoreCase(expRefAlltxt)) {
            report.updateTestLog("verifyRefuseAllDDOption", "Refuse All text for 'Refuse next/all direct debits ' displayed is as Expected '" + expRefAlltxt + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyRefuseAllDDOption", "Refuse All text for 'Refuse next/all direct debits is NOT as Expected , Actual ''" + refuseAllText + "", Status_CRAFT.FAIL);
        }
        String refuseAllYou = getText(getObject("DirectDebit.refAllYou"));
        Thread.sleep(1000);
        if (refuseAllYou.equalsIgnoreCase(expRefYouNeedtxt)) {
            report.updateTestLog("verifyRefuseAllDDOption", "Important Information Text for 'Refuse next/all direct debits ' displayed is as Expected '" + expRefYouNeedtxt + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyRefuseAllDDOption", "Important Information Text for 'Refuse next/all direct debits is NOT as Expected , Actual ''" + refuseAllYou + "", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("DirectDebit.refuseAllCreditorText"),
                "Creditor name & ID", "Refuse next/all direct debits", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.refuseAllCreditorIDlink"),
                "What is a creditor ID?", "Refuse next/all direct debits", 10);
        isElementDisplayedWithLog(getObject("DirectDebit.refuseAllUMRText"),
                "Unique mandate reference", "Refuse next/all direct debits", 10);
        String refuseAllUMR = getText(getObject("DirectDebit.refuseAllUMRlink"));
        if (refuseAllUMR.equalsIgnoreCase(expRefUMRtxt)) {
            report.updateTestLog("verifyRefuseAllDDOption", "UMR link for 'Refuse next/all direct debits ' displayed is as Expected '" + expRefUMRtxt + "'", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyRefuseAllDDOption", "UMR link for 'Refuse next/all direct debits is NOT as Expected : Actual '" + refuseAllUMR + "", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("DirectDebit.Refusecontall"),
                "Continue button", "Refuse next/all debit debits block", 10);
        clickJS(getObject("DirectDebit.Refusecontall"), "Continue");
        Thread.sleep(2000);
    }

    /**
     * Function to Enter Invalid Creditor ID on Direct debit screen
     */
    public void directDebitFormToolTip() throws Exception {
        String expCreNameTooltiptxt = dataTable.getData("General_Data", "Tooltip1");
        String expCreIDTooltiptxt = dataTable.getData("General_Data", "Tooltip2");
        String expUMRTooltiptxt = dataTable.getData("General_Data", "ToolTipText");
        isElementDisplayedWithLog(getObject("DirectDebit.CreTooltip"),
                "Creditor Name Tooltip ICON","SEPA direct debit Services",10);

       if(deviceType.equalsIgnoreCase("Web")){
            Actions action = new Actions((WebDriver) driver.getWebDriver());
            action.moveToElement(driver.findElement(getObject("DirectDebit.CreTooltip"))).clickAndHold().build().perform();
            String CreNameTooltiptxt = getText(getObject("DirectDebit.CreTooltiptxt"),10).replace("\n","");
            if (CreNameTooltiptxt.equalsIgnoreCase(expCreNameTooltiptxt)) {
                report.updateTestLog("verifyDDToolTip", "Tooltip for Creditor's Name displayed is as Expected '" + expCreNameTooltiptxt + "'", Status_CRAFT.PASS);
            } else
            {
                report.updateTestLog("verifyDDToolTip", "Tooltip for Creditor's Name is NOT as Expected , Actual ''" + CreNameTooltiptxt + "", Status_CRAFT.FAIL);
            }
        }

        isElementDisplayedWithLog(getObject("DirectDebit.TooltipCreID"),
                "What is a Creditor ID? Tooltip", "SEPA direct debit Services", 5);
        if(!deviceType.equalsIgnoreCase("Web")){
//            driver.hideKeyboard();
//            driver.hideKeyboard();waitForJQueryLoad();waitForPageLoaded();
            waitForElementToClickable(getObject("DirectDebit.TooltipCreID"), 5);
            ScrollAndClickJS("DirectDebit.TooltipCreID");
           // clickJS(getObject("DirectDebit.TooltipCreID"), "CreatorID tool tip");
        }else{
            click(getObject("DirectDebit.TooltipCreID"), "CreatorID tool tip");
        }

        String CreIDTooltiptxt = getText(getObject("DirectDebit.CreIDTooltiptxt"), 5).replace("\n","");
        if (CreIDTooltiptxt.equalsIgnoreCase(expCreIDTooltiptxt)) {
            clickJS(getObject("DirectDebit.TooltipCreIDOK"), "OK button clicked");
            waitForJQueryLoad();waitForPageLoaded();
            report.updateTestLog("Creditor ID ", "Creditor Id Tool tip text matches ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Creditor ID ", "Creditor Id Tool tip text does not matches ", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("DirectDebit.TooltipUMR"),
                "Where's my UMR? Tooltip", "SEPA direct debit Services", 5);
        if(!deviceType.equalsIgnoreCase("Web")){
            //driver.hideKeyboard();waitForJQueryLoad();waitForPageLoaded();
            clickJS(getObject("DirectDebit.TooltipUMR"), "UMR tool tip");
        }else {
            click(getObject("DirectDebit.TooltipUMR"), "UMR tool tip");
        }

        String UMRTooltiptxt = getText(getObject("DirectDebit.UMRTooltiptxt"), 5).replace("\n","");
        if (UMRTooltiptxt.equalsIgnoreCase(expUMRTooltiptxt)) {
            clickJS(getObject("DirectDebit.TooltipUMROK"), "OK button clicked");
            waitForJQueryLoad();waitForPageLoaded();
            report.updateTestLog("UMR ID ", "UMR Id Tool tip text matches ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("UMR ID ", "UMR Id Tool tip text does not matches ", Status_CRAFT.FAIL);
        }

    }

    public void enterInvalidCreditorID() throws Exception {
        String strcreditorID = dataTable.getData("General_Data", "CreditorID");
        String strerrormessage = "The details you have entered in the Creditor ID field are invalid";

        if (isElementDisplayed(getObject("DirectDebit.txtCreditorId"), 3)) {
            sendKeys(getObject("DirectDebit.txtCreditorId"), strcreditorID, "Creditor ID Entered");
            click(getObject("DirectDebit.txtAmountRecentpay"), "");
            String strErrorMsg_InvalidCreditorID = getText(getObject("DD.errorMsg_CreditorId"));
            if (isElementDisplayed(getObject("DD.errorMsg_CreditorId"), 3) && (strerrormessage.equalsIgnoreCase(strErrorMsg_InvalidCreditorID))) {
                report.updateTestLog("enterInvalidCreditorID", "Error msg is displayed as : " + strErrorMsg_InvalidCreditorID, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("enterInvalidCreditorID", "Error message is not displayed ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("enterInvalidCreditorID", "'Creditor ID' text box is not displayed", Status_CRAFT.FAIL);
        }
        Thread.sleep(2000);
    }

    /**
     * Function to Enter Invalid Amount of Next Payment on Direct debit screen
     */


    public void enterInvalidamount() throws Exception {
        String stramount = dataTable.getData("General_Data", "Pay_Amount");
        String strerrormessage = "The maximum value is 999999999.99";
        String strerrormessage0 = "Please enter an amount greater than €0.00";
        if (isElementDisplayed(getObject("DirectDebit.txtAmountRecentpay"), 3)) {
            sendKeys(getObject("DirectDebit.txtAmountRecentpay"), stramount, "Amount entered");
            click(getObject("DirectDebit.txtCreditorId"), "");
            String strErrorMsg_InvalidAmount = getText(getObject("DD.errorMsg_amount"));
            if (isElementDisplayed(getObject("DD.errorMsg_amount"), 3) && (strerrormessage.equalsIgnoreCase(strErrorMsg_InvalidAmount))) {
                report.updateTestLog("enterInvalidamount", "Error msg is displayed as : " + strErrorMsg_InvalidAmount, Status_CRAFT.PASS);
            }
            if (isElementDisplayed(getObject("DD.errorMsg_amount"), 3) && (strerrormessage0.equalsIgnoreCase(strErrorMsg_InvalidAmount))) {
                report.updateTestLog("enterInvalidamount", "Error msg is displayed as : " + strErrorMsg_InvalidAmount, Status_CRAFT.PASS);
            }

        } else {
            report.updateTestLog("enterInvalidamount", "'Error message not dispalyed", Status_CRAFT.FAIL);
        }

        Thread.sleep(2000);
    }

    /**
     * Function to Enter Invalid Phone number on Direct debit screen
     */


    public void enterInvalidPhonenumber() throws Exception {
        String strphone = dataTable.getData("General_Data", "MobileNumber");
        String strerrormessagephone = "The Phone Number you have entered is too short. Please re-enter";

        if (isElementDisplayed(getObject("DirectDebit.txtPhonenumber"), 3)) {
            sendKeys(getObject("DirectDebit.txtPhonenumber"), strphone, "Phone Number entered");
            click(getObject("DirectDebit.txtLastName"), "");
            String strErrorMsg_InvalidPhone = getText(getObject("DD.errorMsg_PhoneNumber"));
            if (isElementDisplayed(getObject("DD.errorMsg_PhoneNumber"), 3) && (strerrormessagephone.equalsIgnoreCase(strErrorMsg_InvalidPhone))) {
                report.updateTestLog("enterInvalidPhonenumber", "Error msg is displayed as : " + strErrorMsg_InvalidPhone, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("enterInvalidPhonenumber", "Error message is not displayed ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("enterInvalidPhonenumber", "'Creditor ID' text box is not displayed", Status_CRAFT.FAIL);
        }

        Thread.sleep(2000);
    }

    /**
     * Function to Enter Invalid Email on Direct debit screen
     */


    public void enterInvalidEmail() throws Exception {
        String stremail = dataTable.getData("General_Data", "Email");
        String strerrormessage = "The Email address you have entered is invalid. Please re-enter";
        String strerrormessageblank = "Please enter a valid email address";
        if (isElementDisplayed(getObject("DirectDebit.txtContactEmail"), 3)) {
            sendKeys(getObject("DirectDebit.txtContactEmail"), stremail, "Email entered");
            click(getObject("DirectDebit.txtLastName"), "");
            String strErrorMsg_InvalidEmail = getText(getObject("DD.errorMsg_Email"));
            if (isElementDisplayed(getObject("DD.errorMsg_Email"), 3) && (strerrormessage.equalsIgnoreCase(strErrorMsg_InvalidEmail))) {
                report.updateTestLog("enterInvalidEmail", "Error msg is displayed as : " + strErrorMsg_InvalidEmail, Status_CRAFT.PASS);
            }
            if (isElementDisplayed(getObject("DD.errorMsg_Email"), 3) && (strerrormessageblank.equalsIgnoreCase(strErrorMsg_InvalidEmail))) {
                report.updateTestLog("enterInvalidEmail", "Error msg is displayed as : " + strErrorMsg_InvalidEmail, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("enterInvalidEmail", "Error message is not displayed ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("enterInvalidEmail", "'Creditor ID' text box is not displayed", Status_CRAFT.FAIL);
        }

        Thread.sleep(2000);
    }

    /**
     * Function to Enter Invalid Mobile number on Direct debit screen
     */


    public void enterInvalidMobilenumber() throws Exception {
        String strmobile = dataTable.getData("General_Data", "Phone");
        String strerrormessage_nonnumeric = "The mobile phone number entered contains a non-numeric value. Please re-enter.";
        String strerrormessage = "The mobile phone number you have entered is too short. Please re-enter.";

        if (isElementDisplayed(getObject("DirectDebit.txtMobileNumber"), 3)) {
            sendKeys(getObject("DirectDebit.txtMobileNumber"), strmobile, "Mobile Number entered");
            click(getObject("DirectDebit.txtLastName"), "");
            String strErrorMsg_InvalidMobile = getText(getObject("DD.errorMsg_MobileNumber"));
            if (isElementDisplayed(getObject("DD.errorMsg_MobileNumber"), 3) && (strerrormessage_nonnumeric.equalsIgnoreCase(strErrorMsg_InvalidMobile))) {
                report.updateTestLog("enterInvalidMobilenumber", "Error msg is displayed as : " + strErrorMsg_InvalidMobile, Status_CRAFT.PASS);
            }
            if (isElementDisplayed(getObject("DD.errorMsg_MobileNumber"), 3) && (strerrormessage.equalsIgnoreCase(strErrorMsg_InvalidMobile))) {
                report.updateTestLog("enterInvalidMobilenumber", "Error msg is displayed as : " + strErrorMsg_InvalidMobile, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("enterInvalidMobilenumber", "Error message is not displayed ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("enterInvalidMobilenumber", "'Creditor ID' text box is not displayed", Status_CRAFT.FAIL);
        }

        Thread.sleep(2000);
    }

    public void DirectDebitNotificationerror() throws Exception {
        String strNotificationReq = dataTable.getData("General_Data", "NotificationReq");
        String StrContactPreference = dataTable.getData("General_Data", "ContactPreference");
        String strEmail = dataTable.getData("General_Data", "Email");
        String strPhone = dataTable.getData("General_Data", "Phone");
        String strerrormessage = "The Email address you have entered is invalid. Please re-enter";
        String strerrormessageblank = "Please enter a valid email address";
        if (strNotificationReq.equalsIgnoreCase("Yes")) {
            click(getObject("DirectDebit.lblNotifyRequestYes"), "Yes Button is click for Notification");
            waitForElementPresent(getObject("DirectDebit.lblContactEmail"), 10);
            if (StrContactPreference.equalsIgnoreCase("Email")) {
                click(getObject("DirectDebit.lblContactEmail"), "Email Radio button is clicked");
                waitForElementPresent(getObject("DirectDebit.txtContactEmail"), 5);
                click(getObject("DirectDebit.txtContactEmail"), "Email Text box is clicked");
                Thread.sleep(1000);
                sendKeys(getObject("DirectDebit.txtContactEmail"), strEmail, "Email Entered");
                Thread.sleep(1000);
                click(getObject("DirectDebit.btnContinue"), "Continue button Clicked");
                Thread.sleep(1000);
                String strErrorMsg_InvalidEmail = getText(getObject("DD.errorMsg_Email"));
                if (isElementDisplayed(getObject("DD.errorMsg_Email"), 3) && (strerrormessage.equalsIgnoreCase(strErrorMsg_InvalidEmail))) {
                    report.updateTestLog("enterInvalidEmail", "Error msg is displayed as : " + strErrorMsg_InvalidEmail, Status_CRAFT.PASS);
                }
                if (isElementDisplayed(getObject("DD.errorMsg_Email"), 3) && (strerrormessageblank.equalsIgnoreCase(strErrorMsg_InvalidEmail))) {
                    report.updateTestLog("enterInvalidEmail", "Error msg is displayed as : " + strErrorMsg_InvalidEmail, Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("enterInvalidEmail", "Error message is not displayed ", Status_CRAFT.FAIL);
                }
            } else {
                click(getObject("DirectDebit.lblContactMobile"), "Mobile Radio button is clicked");
                waitForElementPresent(getObject("DirectDebit.txtMobileNumber"), 5);
                click(getObject("DirectDebit.txtMobileNumber"), "Mobile Text box is clicked");
                Thread.sleep(1000);
                sendKeys(getObject("DirectDebit.txtMobileNumber"), strPhone, "Phone Number Entered");
                Thread.sleep(1000);
                click(getObject("DirectDebit.btnContinue"), "Continue button Clicked");
                String strErrorMsg_InvalidMobile = getText(getObject("DD.errorMsg_MobileNumber"));
                if (isElementDisplayed(getObject("DD.errorMsg_MobileNumber"), 3)) {
                    report.updateTestLog("enterInvalidMobilenumber", "Error msg is displayed as : " + strErrorMsg_InvalidMobile, Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("enterInvalidMobilenumber", "Error message is not displayed ", Status_CRAFT.FAIL);
                }
            }
        } else {
            click(getObject("DirectDebit.lblNotifyRequestNo"), "No Button is click for Notification");
            waitForElementPresent(getObject("DirectDebit.btnContinue"), 5);
            click(getObject("DirectDebit.btnContinue"), "Continue button Clicked");
        }

    }

    public void CancelDDErrorvalidation() throws Exception {

        enterInvalidCreditorID();
        enterInvalidamount();
        enterInvalidPhonenumber();
        DirectDebitNotificationerror();

    }

    public void CancelDDimage() throws Exception {

        if (isElementDisplayed(getObject("DirectDebit.CancelImage"), 3)) {

            report.updateTestLog("CancelDDimage", "Help Image is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("CancelDDimage", "Help Image is  not displayed ", Status_CRAFT.FAIL);
        }
    }

    public void CancelDDtext() throws Exception {
        String StrHelpText = dataTable.getData("General_Data", "VerifyDetails");
        String StrHeaderText = dataTable.getData("General_Data", "Nickname");
        String strText = getText(getObject("DirectDebit.Cancelhelptext"));
        String strTip = getText(getObject("DirectDebit.Cancelheadertext"));

        if (isElementDisplayed(getObject("DirectDebit.Cancelheadertext"), 3) && (StrHeaderText.equalsIgnoreCase(strTip))) {
            report.updateTestLog("CancelDDtext", "Correct Header Text is displayed for Cancel DD", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("CancelDDtext", "Correct Header Text is not displayed for Cancel DD ", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("DirectDebit.Cancelhelptext"), 3) && (StrHelpText.equalsIgnoreCase(strText))) {
            report.updateTestLog("CancelDDtext", "Correct Help Text is displayed for Cancel DD", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("CancelDDtext", "Correct Help Text is not displayed for Cancel DD ", Status_CRAFT.FAIL);
        }
    }

    public void CancelDDEasyWay() throws Exception {

        CancelDDimage();
        CancelDDtext();
    }

    /**
     * CFPUR-6724-To verify Cancel DD popup
     */

    public void CancelDDpopup() throws Exception {
        String StrpopupText = dataTable.getData("General_Data", "VerifyDetails");
        String StrHeaderText = dataTable.getData("General_Data", "Nickname");
        String strText = getText(getObject("DirectDebit.Cancelpopuptext"));
        String strheader = getText(getObject("DirectDebit.Cancelpopupheadertext"));

        if (isElementDisplayed(getObject("DirectDebit.Cancelpopupheadertext"), 3) && (StrHeaderText.equalsIgnoreCase(strheader))) {
            report.updateTestLog("CancelDDpopup", "Correct Header Text is displayed for Cancel DD popup", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("CancelDDpopup", "Correct Header Text is not displayed for Cancel DD popup ", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("DirectDebit.Cancelpopuptext"), 3) && (StrpopupText.equalsIgnoreCase(strText))) {
            report.updateTestLog("CancelDDpopup", "Correct help Text is displayed for Cancel DD popup", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("CancelDDpopup", "Correct help Text is not displayed for Cancel DD popup ", Status_CRAFT.FAIL);
        }

    }

    public void CancelDDpopupClose() throws Exception {
        if (isElementDisplayed(getObject("DirectDebit.Cancelpopupcloseicon"), 3)) {
            report.updateTestLog("CancelDDpopupClose", "Close Icon is displayed on Popup", Status_CRAFT.PASS);
            click(getObject("DirectDebit.Cancelpopupcloseicon"), "Close icon");
            Thread.sleep(2000);
        } else {
            report.updateTestLog("CancelDDpopup", "Close Icon not displayed on Popup", Status_CRAFT.FAIL);
        }
    }

    public void CancelDDpopupContinue() throws Exception {
        if (isElementDisplayed(getObject("DirectDebit.CancelpopupContinue"), 3)) {
            report.updateTestLog("CancelDDpopupContinue", "Continue is displayed on Popup", Status_CRAFT.PASS);
            click(getObject("DirectDebit.CancelpopupContinue"), "Continue Clicked");
            Thread.sleep(2000);
        } else {
            report.updateTestLog("CancelDDpopupContinue", "Continue not displayed on Popup", Status_CRAFT.FAIL);
        }

       /* if (isElementDisplayed(getObject("DirectDebit.CancelDDheader"), 3)) {
            report.updateTestLog("CancelDDpopupContinue", "Navigation to Cancel DD page is correct", Status_CRAFT.PASS);
        }
        else
        {
            report.updateTestLog("CancelDDpopupContinue", "Navigation to Cancel DD page incorrect", Status_CRAFT.FAIL);
        }*/
    }

    public void CancelDDpopupGoback() throws Exception {
        if (isElementDisplayed(getObject("DirectDebit.CancelpopupGoBack"), 3)) {
            report.updateTestLog("CancelDDpopupGoback", "Go Back button is displayed on Popup", Status_CRAFT.PASS);
            click(getObject("DirectDebit.CancelpopupGoBack"), "Go Back button");
            Thread.sleep(2000);
        } else {
            report.updateTestLog("CancelDDpopupGoback", "Go Back button not displayed on Popup", Status_CRAFT.FAIL);
        }
    }

    public void ddContentChangesRefuse() throws Exception {
        String strRefuseall = "Refuse all";
        String strRefusenext = "Refuse next";
        String strSelectionText = "Make a selection";
        if (isElementDisplayed(getObject("DirectDebit.tileRefuse"), 3)) {
            click(getObject("DirectDebit.tileRefuse"), "Direct Debit Refuse Payment");
            report.updateTestLog("Refuse Tile  ", "Refuse Tile is dispalyed", Status_CRAFT.PASS);
            String strSelectionTextobject = getText(getObject("xpath~//h2[@id='C5__HEAD_B9262221F69645B2504080'][text()='Make a selection']"));
            String strRefuseallObject = getText(getObject("xpath~//span[text()='Refuse all']"));
            String strRefuseanextObject = getText(getObject("xpath~//span[text()='Refuse next']"));
            if (strSelectionTextobject.equalsIgnoreCase(strSelectionText)) {
                report.updateTestLog("Selection Header ", "Selection text is " + strSelectionTextobject, Status_CRAFT.PASS);
                if (strRefuseallObject.equalsIgnoreCase(strRefuseall)) {
                    report.updateTestLog("Refuse all ", "Text is" + strRefuseallObject, Status_CRAFT.PASS);
                    if (strRefuseanextObject.equalsIgnoreCase(strRefusenext)) {
                        report.updateTestLog("Refuse next ", "Text is" + strRefuseanextObject, Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("Refuse next ", "Text is is not correct", Status_CRAFT.FAIL);
                    }
                } else {
                    report.updateTestLog("Refuse all ", "Text is is not correct", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("Selection Header ", "Selection text is " + strSelectionTextobject, Status_CRAFT.PASS);
            }

        } else {
            report.updateTestLog("Refuse Tile  ", "Refuse Tile is not  dispalyed", Status_CRAFT.FAIL);
        }
    }


    public void ddContentChangesReactivate() throws Exception {
        String strReactivateall = "Reactivate all";
        String strReactivatenext = "Reactivate next";
        String strSelectionText = "Make a selection";
        if (isElementDisplayed(getObject("DirectDebit.tileReactivate"), 3)) {
            click(getObject("DirectDebit.tileReactivate"), "Direct Debit Reactivate Payment");
            report.updateTestLog("Reactivate Tile  ", "Reactivate Tile is dispalyed", Status_CRAFT.PASS);
            String strSelectionTextobject = getText(getObject("xpath~//h2[@id='C5__HEAD_80E5F369625278D4497785'][text()='Make a selection']"));
            String strReactivateallObject = getText(getObject("xpath~//span[text()='Reactivate all']"));
            String strReactivatenextObject = getText(getObject("xpath~//span[text()='Reactivate next']"));
            if (strSelectionTextobject.equalsIgnoreCase(strSelectionText)) {
                report.updateTestLog("Selection Header ", "Selection text is " + strSelectionTextobject, Status_CRAFT.PASS);
                if (strReactivateallObject.equalsIgnoreCase(strReactivateall)) {
                    report.updateTestLog("Reactivate all ", "Text is" + strReactivateallObject, Status_CRAFT.PASS);
                    if (strReactivatenextObject.equalsIgnoreCase(strReactivatenext)) {
                        report.updateTestLog("Reactivate next ", "Text is" + strReactivatenextObject, Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("Reactivate next ", "Text is is not correct", Status_CRAFT.FAIL);
                    }
                } else {
                    report.updateTestLog("Reactivate all ", "Text is is not correct", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("Selection Header ", "Selection text is " + strSelectionTextobject, Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("Reactivate Tile  ", "Reactivate Tile is dispalyed", Status_CRAFT.FAIL);
        }
    }


    public void ddcontentChangesBlock() throws Exception {
        String strSelectionText = "Make a selection";
        if (isElementDisplayed(getObject("DirectDebit.tileBlock"), 3)) {
            click(getObject("DirectDebit.tileBlock"), "Direct Debit Block/Unblock user");
            report.updateTestLog("Block and Unblock Tile  ", "Block and Unblock Tile is dispalyed", Status_CRAFT.PASS);
            String strSelectionTextobject = getText(getObject("xpath~//h2[@id='C5__HEAD_8A04FB4B39219F0E532248'][text()='Make a selection']"));
            if (strSelectionTextobject.equalsIgnoreCase(strSelectionText)) {
                report.updateTestLog("Selection Header ", "Selection text is " + strSelectionTextobject, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Selection Header ", "Selection text is not correct", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("Block and Unblock Tile  ", "Block and Unblock Tile is dispalyed", Status_CRAFT.FAIL);
        }
    }

    public void setupDirectDebitMaxLength() throws Exception {
        String[] obj = dataTable.getData("General_Data", "objName").split("::");
        String[] maxLength = dataTable.getData("General_Data", "MaxLength").split("::");
        HomePage homepage = new HomePage(scriptHelper);
        for (int i = 0; i < obj.length; i++) {
            int intMaxLength = Integer.parseInt(maxLength[i]);
            homepage.verifyMaxLength(intMaxLength, obj[i]);
        }
    }

    public void errorMessageValidations() throws Exception {
        waitForPageLoaded();
        clickJS(getObject("DirectDebit.btnContinue"), "Continue button");
        Thread.sleep(3000);
        String errorMessage[] = dataTable.getData("General_Data", "ErrorText").split("::");
        for (int i = 0; i < errorMessage.length; i++) {
            if (isElementDisplayed(getObject("xpath~//div[contains(@class,'tc-card boi-card-standard tc-card-bg')]/descendant::span[contains(text(),'" + errorMessage[i] + "')][contains(@style,'inline')]"), 5)) {
                    report.updateTestLog("errorMessageValidations", "Error Message '" + errorMessage[i] + "' is displayed on Add Payee Page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("errorMessageValidations", "Error Message '" + errorMessage[i] + "' is NOT displayed on Add Payee Page", Status_CRAFT.FAIL);
                }

        }
    }
}















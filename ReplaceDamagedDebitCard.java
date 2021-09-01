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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Created by C101979 on 30/09/2019.
 */
public class ReplaceDamagedDebitCard extends WebDriverHelper {

    public String Referencenumber = "";
    public String TimeofRequest = "";
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public ReplaceDamagedDebitCard(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function : Verify Order Interest Cert Form page
     * Created on     Created by    Reason
     * 30/09/2019     C101979       In Sprint Development
     */

    /*General Function:
       Scroll and Click on particular  element using JS
    */
    public void ScrollAndClickJS(String linkToClick) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(2000);
            report.updateTestLog("ScrollAndClickJS", "Element To Click", Status_CRAFT.DONE);
            JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
            executor.executeScript("arguments[0].click();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(3000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    public void verifyReplaceDDCardFormPage() throws Exception {
        String strActualDamagedDCNote = getText(getObject("ReplaceDDCard.headerNote"));
        String strExpectedDamagedDCNote = dataTable.getData("General_Data", "VerifyContent");
        waitForPageLoaded();
        if(isMobile){
            isElementDisplayedWithLog(getObject("mw.ReplaceDDCard.header"),
                    "'Replace Damaged Debit Card' Header ", "Replace Damaged Debit Card Form", 5);
        }else{
            isElementDisplayedWithLog(getObject(deviceType() + "ReplaceDDCard.header"),
                    "'Replace Damaged Debit Card' Header ", "Replace Damaged Debit Card Form", 5);
        }

        if (isElementDisplayed(getObject("ReplaceDDCard.headerNote"),1)) {
            report.updateTestLog("validateOrderBalanceCertFormPage", "Note'" + strExpectedDamagedDCNote + "' is appearing on Form Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateOrderBalanceCertFormPage", "Note '" + strActualDamagedDCNote + "' is not appearing on Form Page", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("ReplaceDDCard.lblDCDetails"),
                "'Enter debit card details' Label ", "Replace Damaged Debit Card Form", 5);
        isElementDisplayedWithLog(getObject("ReplaceDDCard.lblSelectAccount"),
                "Account associated with debit card' Label ", "Replace Damaged Debit Card Form", 5);
        isElementDisplayedWithLog(getObject("ReplaceDDCard.lblDebitCardNumber"),
                "'Debit card number' label", "Replace Damaged Debit Card Form", 1);
        isElementDisplayedWithLog(getObject("ReplaceDDCard.txtDebitCardNumber"),
                "'8-digit number at the bottom of your card' text", "Replace Damaged Debit Card Form", 1);
        isElementDisplayedWithLog(getObject("ReplaceDDCard.lblNotification"),
                "'Notification' label ", "Replace Damaged Debit Card Form", 1);
        isElementDisplayedWithLog(getObject("ReplaceDDCard.lblNotify"),
                "'Will we notify you when your request is complete?' label ", "Replace Damaged Debit Card Form", 1);
        isElementDisplayedWithLog(getObject("ReplaceDDCard.lblNotifyRequestYes"),
                "'Yes' option for Notification confirmation question", "Replace Damaged Debit Card Form", 1);
        isElementDisplayedWithLog(getObject("ReplaceDDCard.lblNotifyRequestNo"),
                "'No' option for Notification confirmation question", "Replace Damaged Debit Card Form", 1);
    }

    public void ReplaceDamagedDC_enterDetails() throws Exception {
        String strIterationFlag = dataTable.getData("General_Data", "IterationFlag");
        String strNotificationFlag = dataTable.getData("General_Data", "NotificationFlag");
        String strEmail = dataTable.getData("General_Data", "Email");
        String strCardNumber = dataTable.getData("General_Data", "DebitCardNumber");
        String strPhone = dataTable.getData("General_Data", "Phone");
        String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
        String strNotification = dataTable.getData("General_Data", "NotificationReq");
        String strAccnt = dataTable.getData("General_Data", "Account_Name");
        if (strIterationFlag.equalsIgnoreCase("Yes")) {
            clickJS(getObject("AddPolicy.Continue"), "No details is entered and Continue (Enter Details Page)");
        } else {
            if (isElementDisplayed(getObject("MTU.lstAccountspayFrom"), 2)) {
                clickJS(getObject("MTU.lstAccountspayFrom"), "Account Dropdown");
                isElementDisplayedWithLog(getObject("ReplaceDDCard.optSelectAccount"),
                        "'Select account followed by other accounts", "Replace Damaged Debit Card Form", 1);
                waitForPageLoaded();
                List<WebElement> allLinks = findElements(getObject("ReplaceDDCard.lstAccount"));
                if (allLinks.size() > 1) {
                    int intIndex = 1;
                    for (WebElement ele : allLinks) {
                        String linkname = ele.getText();
                        if (linkname.contains(strAccnt)) {
                            ele.click();
                            report.updateTestLog("enterDDCardDetails", linkname + " :: clicked and selected ", Status_CRAFT.PASS);
                            waitForPageLoaded();
                            break;
                        }
                        intIndex = intIndex++;

                    }
                }
            } else {
                report.updateTestLog("enterDDCardDetails", "Label 'Account'is not displayed.", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("ReplaceDDCard.txtCardNumber"), 3)) {
                sendKeys(getObject("ReplaceDDCard.txtCardNumber"), strCardNumber, "Debit Card Number Entered");
            } else {
                report.updateTestLog("enterDDCardDetails", "Unable to enter Debit Card Number.", Status_CRAFT.FAIL);
            }
            if (strNotification.equalsIgnoreCase("Yes")) {
                clickJS(getObject("ReplaceDDCard.lblNotifyRequestYes"), "Selected Yes Option");
                isElementDisplayedWithLog(getObject("ReplaceDDCard.lblContactMethod"),"'How will we contact you?'question displayed after selecting 'Yes' option for Notification'", "Replace Damaged Debit Card Form", 1);
                if (strNotificationFlag.equalsIgnoreCase("Yes")) {
                    clickJS(getObject("OrderBalCert.btnContinue"), "Continue button is clicked without selecting a 'Contact Preference' method");
                }
                if (strContactPreference.equalsIgnoreCase("Email")) {
                    clickJS(getObject("ReplaceDDCard.lblContactEmail"), "Email Option Button");
                    Thread.sleep(1000);
                    sendKeys(getObject("ReplaceDDCard.txtContactEmail"), strEmail, "Email address Entered");
                } else if (strContactPreference.equalsIgnoreCase("Phone")) {
                    clickJS(getObject("ReplaceDDCard.lblContactMobile"), "Phone As A Preference");
                    Thread.sleep(1000);
                    sendKeys(getObject("ReplaceDDCard.txtMobileNumber"), strPhone, "Phone Number Entered");
                }
            }
            else if (strNotification.equalsIgnoreCase("No")) {
                clickJS(getObject("ReplaceDDCard.lblNotifyRequestNo"), "Selected 'No' Option");
                report.updateTestLog("enterDDCardDetails", "Notification Method is selected as 'No'", Status_CRAFT.PASS);
            }
            else
            {
                report.updateTestLog("enterDDCardDetails", "User has not selected the Notification Method", Status_CRAFT.FAIL);
            }
            clickJS(getObject("ReplaceDDCard.btnContinue"), "Continue (Enter Details Page)");
        }

    }

    public void ReplaceDamagedDCReviewPage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String strAccount = dataTable.getData("General_Data", "Account_Name");

        //validating page title 'Replace Damaged Debit Card'
        if(isElementDisplayed(getObject("ReplaceDDCard.hdrReview"),5)){
            if(isMobile){
                isElementDisplayedWithLog(getObject("mw.ReplaceDDCard.header"),
                        "'Replace Damaged Debit Card' Header ", "Replace Damaged Debit Card Review", 5);
            }else{
                isElementDisplayedWithLog(getObject(deviceType() + "ReplaceDDCard.header"),
                        "'Replace Damaged Debit Card' Header ", "Replace Damaged Debit Card Review", 5);
            }
            isElementDisplayedWithLog(getObject("ReplaceDDCard.hdrReview"),
                "Section Header 'Review'", "Replace Damaged Debit Card Review" ,25);
            if (isElementDisplayed(getObject("xpath~//*[text()='Account associated with debit card']"), 5)) {
                report.updateTestLog("validateReplaceDDCardReviewPage", "Label 'Account' is displayed on Replace Damaged Debit Card Review page", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("xpath~//*[text()='Account associated with debit card']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[contains(text(),'" + strAccount + "')]"), 5)) {
                String actAccount = getText(getObject("xpath~//*[text()='Account associated with debit card']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[contains(text(),'" + strAccount + "')]"));
                    if (actAccount.contains(strAccount)) {
                        report.updateTestLog("validateReplaceDDCardReviewPage", "Account  '" + actAccount + "' is appearing on Review Page", Status_CRAFT.PASS);
                    } else {
                    report.updateTestLog("validateReplaceDDCardReviewPage", "Account '" + actAccount + "' is not appearing on Review Page", Status_CRAFT.FAIL);
                    }
            }
        } else {
            report.updateTestLog("validateReplaceDDCardReviewPage", "Label Account is NOT displayed on Pay To Mobile Review page", Status_CRAFT.FAIL);
        }
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }
        for (Map.Entry obj : mapData.entrySet()) {
            if (!obj.getValue().equals("NA")) {
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[text()='" + obj.getValue() + "']"), 5)) {
                    report.updateTestLog("validateReplaceDDCardReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("validateReplaceDDCardReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
                }
            } else {
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
                    report.updateTestLog("validateReplaceDDCardReviewPage", "'" + obj.getKey() + "' is displayed on review page for blank input", Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("validateReplaceDDCardReviewPage", "'" + obj.getKey() + "' is Not displayed on review page for blank input", Status_CRAFT.PASS);
                }
            }
        }

        if (isElementDisplayed(getObject("xpath~//a[contains(@class,'tc-secondary-card-button-new')]/*[text()='Go back']"), 5)) {
            report.updateTestLog("validateReplaceDDCardReviewPage", "'Go back' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateReplaceDDCardReviewPage", "'Go back' button is NOT displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("xpath~//a/descendant::*[text()='Confirm']"), 5)) {
            report.updateTestLog("validateReplaceDDCardReviewPage", "'Confirm' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateReplaceDDCardReviewPage", "'Confirm' button is NOT displayed", Status_CRAFT.FAIL);
        }

        String strOperation = dataTable.getData("General_Data", "OperationOnReviewPage");
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']")));
        Thread.sleep(2000);
        waitForElementToClickable(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']"), 5);
        clickJS(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
        if (strOperation.equalsIgnoreCase("Go back")) {
            if (isElementDisplayed(getObject(deviceType() + "OrderInteCert.header"), 5)) {
                report.updateTestLog("validateReplaceDDCardReviewPage", "Page successfully navigated to Order Balance Certificates Form page, after clicking 'Go back'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validateReplaceDDCardReviewPage", "Page did not navigate to Order Balance Certificates Form page, after clicking 'Go back' button", Status_CRAFT.FAIL);
            }
        }
    }else{
            homePage.verifyElementDetails("OrderIntcert.messagetextvalidation");
        }
    }


    public void ReplaceDamagedDCSuccessPage() throws Exception {
        //validating page title 'Order Interest Certificates'
        String strExpMessage = dataTable.getData("General_Data", "DirectionalText");
        String strActMessage =  getText(getObject("ReplaceDDCard.txtMessage"));
        String strExpConfirmationMessage = dataTable.getData("General_Data", "ConfirmationMessage");
        String strActConfirmationMessage =  getText(getObject("ReplaceDDCard.txtConfirmationMessage"));
        waitForPageLoaded();
        if(isMobile){
            isElementDisplayedWithLog(getObject("mw.ReplaceDDCard.header"),
                    "'Replace Damaged Debit Card' Header", " 'Replace Damaged Debit Card' Success " ,5);
        }else{
            isElementDisplayedWithLog(getObject(deviceType() + "ReplaceDDCard.header"),
                    "'Replace Damaged Debit Card' Header", " 'Replace Damaged Debit Card' Success " ,5);
        }
        isElementDisplayedWithLog(getObject("ReplaceDDCard.iconConfirmation"),
                " Success Icon 'Tick Mark'", " 'Replace Damaged Debit Card' Success " ,25);
        isElementDisplayedWithLog(getObject("ReplaceDDCard.txtReqSent"),
                "Text message 'Request Sent'", " 'Replace Damaged Debit Card' Success " ,25);

        if (isElementDisplayed(getObject("ReplaceDDCard.txtConfirmationMessage"),1)) {
            report.updateTestLog("validateReplaceDDCardSuccessPage", "The informative message :: "+ strExpConfirmationMessage +" :: correctly displayed on 'Replace Damaged Debit Card' Success Page", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("validateReplaceDDCardSuccessPage", "The informative message :: "+ strActConfirmationMessage +" :: Is NOT correctly displayed on 'Replace Damaged Debit Card' Success Page", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("ReplaceDDCard.btnBackToHome"),
                "Button 'Back to home'", " 'Order Balance Certificates' Success ",25);
        String strRef = getText(getObject("ReplaceDDCard.txtReference"));
        if (isElementDisplayed(getObject("ReplaceDDCard.txtReference"), 5)) {
            report.updateTestLog("validateReplaceDDCardSuccessPage", " 'Reference: '" + strRef + "' is displayed on Replace Damaged Debit Card' Success page", Status_CRAFT.PASS);
            } else {
            report.updateTestLog("validateReplaceDDCardSuccessPage", " 'Reference: '" + strRef + "' is NOT displayed correctly on Replace Damaged Debit Card' Success page", Status_CRAFT.PASS);
            }
        if (isElementDisplayed(getObject("ReplaceDDCard.txtTimeReq"), 5)) {
            report.updateTestLog("validateOrderBalCertSuccessPage", "Time Stamp with message 'Time of request' is displayed on Replace Damaged Debit Card' Success page", Status_CRAFT.PASS);
            String strRequestSubmitted = getText(getObject("ReplaceDDCard.txtTimeReq"));
            String uiDateText = strRequestSubmitted.split(": ")[1];
            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("validateReplaceDDCardSuccessPage", "Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("validateReplaceDDCardSuccessPage", "Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("validateReplaceDDCardSuccessPage", "Time Stamp with message 'Time of request' is NOT displayed on Replace Damaged Debit Card' Success Page", Status_CRAFT.FAIL);
        }
        //Validating Navigation/functionality of the buttons

                if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to home")) {
                    isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Reference: ')]"),"Reference Number","Reference number is dispalyed",5);
                    Referencenumber = getText(getObject("xpath~//div[contains(text(),'Reference: ')]"),10);
                    isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Time of request: ')]"),"Time of Request","Time of request is dispalyed",5);
                    TimeofRequest = getText(getObject("xpath~//div[contains(text(),'Time of request: ')]"),5);
                    clickJS(getObject("ReplaceDDCard.btnBackToHome"), "Clicked on 'Back to home'");
                    if (isElementDisplayed(getObject(deviceType() + "home.titleMyAccounts"), 5)) {
                        report.updateTestLog("validateReplaceDDCardSuccessPage", "Upon clicking 'Back to home' ,successfully Navigated to HomePage screen from Replace Damaged Debit Card' Success Page", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("validateReplaceDDCardSuccessPage", "Upon clicking 'Back to home' ,navigation unsuccessful to HomePage screen from Replace Damaged Debit Card' Success Page", Status_CRAFT.FAIL);
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
        String FinalRef1[] = FinalRef[1].split(" ");
        String FinalRef2 = FinalRef1[2].trim();
        //TimeofRequest = getText(getObject("xpath~//div[contains(text(),'Time of request: ')]"),5);
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        clickMessage();
        clickSent();
        clickShowmorecomplete();
        List<WebElement> Sentmailslist = driver.findElements(By.xpath("//span[contains(@id,'C5__QUE_781A38DFD311C2FD268777_R')]"));
        for(int i=0; i<Sentmailslist.size();i++){
            String text = Sentmailslist.get(i).getText();
            if (text.equals(FinalRef2)){
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
                if(refMsg.equals(FinalRef2)){
                    System.out.print("Reference number matched");
                }
                else{
                    System.out.print("Reference numeber not matched");
                }
                break;
            }

        }

    }

}

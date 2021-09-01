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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by C101979 on 12/08/2019.
 */
public class OrderIntCert extends WebDriverHelper {

    public String Referencenumber = "";
    public String TimeofRequest = "";

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public OrderIntCert(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function : Verify Order Interest Cert Form page
     * Created on     Created by    Reason
     * 12/08/2019     C101979       In Sprint Development
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

    public void verifyOrderInterestCertFormPage() throws Exception {
        String firstYear = "";
        String pattern = "dd MM yyyy";
        LocalDate today = LocalDate.now();
        LocalDate lastYear = today.minusYears(7);
        firstYear = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(lastYear).replace(" ","/");
        String strYY = firstYear.split("/")[2];
        String strActualOrderCertsNote = getText(getObject("OrderInteCert.headerNote"));
        String strExpectedOrderCertsNote = dataTable.getData("General_Data", "VerifyContent");
        String strExpectedFeesAndCharges = dataTable.getData("General_Data", "verifyfeesAndCharges");
        String strExecute= dataTable.getData("General_Data", "Operation");

        if(isMobile){
            isElementDisplayedWithLog(getObject("mw.OrderInteCert.header"),
                    "'Order Interest Certificates' Header ", "Order Balance Certs Form", 5);
        }else{
            isElementDisplayedWithLog(getObject(deviceType() + "OrderInteCert.header"),
                    "'Order Interest Certificates' Header ", "Order Balance Certs Form", 5);
        }
        isElementDisplayedWithLog(getObject("OrderInteCert.label"),
                "Order certificates for' Header ", "Order Interest Certs", 5);
        isElementDisplayedWithLog(getObject("OrderInteCert.lblAccount"),
                "'Account' label", "Order Interest Certs", 1);
        isElementDisplayedWithLog(getObject("OrderInteCert.lblTaxYear"),
                "'Tax year ended' label", "Order Interest Certs", 1);
        isElementDisplayedWithLog(getObject("OrderInteCert.lblNotify"),
                "'Will we notify you when your order is ready?' label ", "Order Interest Certs", 1);
        isElementDisplayedWithLog(getObject("OrderInteCert.lblNotifyRequestYes"),
                "'Yes' option for Notification confirmation question", "Order Interest Certs", 1);
        isElementDisplayedWithLog(getObject("OrderInteCert.lblNotifyRequestNo"),
                "'No' option for Notification confirmation question", "Order Interest Certs", 1);

        String Jurisdiction = dataTable.getData("General_Data", "JurisdictionType");
        if (Jurisdiction.equals("NI")||Jurisdiction.equals("GB")) {
            String strActualFeesAndCharges = getText(getObject("OrderInteCert.txtFeesAndChargesNI"));
                if (strActualFeesAndCharges.equals(strExpectedFeesAndCharges)) {
                    report.updateTestLog("validateOrderInterestCertFormPage", "Fees and charges '" + strActualFeesAndCharges + "' is appearing on Form Page ", Status_CRAFT.PASS);
                    HomePage homePage = new HomePage(scriptHelper);
                    if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                        String strExpectedLink = "https://www.bankofireland.com/fees-and-charges";
                        homePage.verifyHrefLink(strExpectedLink, "OrderInteCert.txtFeesAndChargesNIlink");
                    }else {
                        clickJS(getObject("OrderInteCert.txtFeesAndChargesNIlink"), "Fees and Charges Link");
                        homePage.verifyNewlyOpenedTab("https://www.bankofireland.com/fees-and-charges");
                    }

                }else {
                    report.updateTestLog("validateOrderInterestCertFormPage", "Fees and charges '" + strActualFeesAndCharges + "' is not appearing on Form Page", Status_CRAFT.FAIL);
                }

        }
        else if (Jurisdiction.equals("CROSS")) {
            String strActualFeesAndCharges = getText(getObject("OrderInteCert.txtFeesAndChargesCross"));
            if (strActualFeesAndCharges.equals(strExpectedFeesAndCharges)) {
                report.updateTestLog("validateOrderInterestCertFormPage", "Fees and charges '" + strActualFeesAndCharges + "' is appearing on Form Page ", Status_CRAFT.PASS);
                HomePage homePage = new HomePage(scriptHelper);
                if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                    String strExpectedLink = "https://www.bankofireland.com/fees-and-charges";
                    homePage.verifyHrefLink(strExpectedLink, "OrderInteCert.txtFeesAndChargesCrosslink");
                }else {
                    clickJS(getObject("OrderInteCert.txtFeesAndChargesCrosslink"), "Fees and Charges Link");
                    homePage.verifyNewlyOpenedTab("https://www.bankofireland.com/fees-and-charges");
                }
            } else {
                report.updateTestLog("validateOrderInterestCertFormPage", "Fees and charges '" + strActualFeesAndCharges + "' is not appearing on Form Page", Status_CRAFT.FAIL);
            }
        }
        else if (Jurisdiction.equals("ROI")) {
            if (isElementNotDisplayed(getObject("OrderInteCert.txtFeesAndChargesCrosslink"), 5)) {
                report.updateTestLog("validateOrderInterestCertFormPage", "The fees & charges is not appearing on Form Page for ROI Customer", Status_CRAFT.PASS);
            } else
                report.updateTestLog("validateOrderInterestCertFormPage", "The fees & charges is not appearing on Form Page for ROI Customer", Status_CRAFT.PASS);
        }
        if (strActualOrderCertsNote.equals(strExpectedOrderCertsNote)) {
            report.updateTestLog("validateOrderInterestCertFormPage", "Note'" + strActualOrderCertsNote + "' is appearing on Form Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateOrderInterestCertFormPage", "Note '" + strActualOrderCertsNote + "' is not appearing on Form Page", Status_CRAFT.FAIL);
        }

    }

    public void OrderInterestCertFormPage_enterDetails() throws Exception {
        String strIterationFlag = dataTable.getData("General_Data", "IterationFlag");
        String strNotificationFlag = dataTable.getData("General_Data", "NotificationFlag");
        String strEmail = dataTable.getData("General_Data", "Email");
        String strPhone = dataTable.getData("General_Data", "Phone");
        String strYear = dataTable.getData("General_Data", "Year");
        String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
        String strNotification = dataTable.getData("General_Data", "NotificationReq");
        String strAccnt = dataTable.getData("General_Data", "Account_Name");
        if (strIterationFlag.equalsIgnoreCase("Yes")) {
            clickJS(getObject("AddPolicy.Continue"), "No details is entered and Continue (Enter Details Page)");
        } else {
            if (isElementDisplayed(getObject("MTU.lstAccountspayFrom"), 2)) {
                clickJS(getObject("MTU.lstAccountspayFrom"), "Account Dropdown");
                waitForPageLoaded();
                List<WebElement> allLinks = findElements(getObject("MTU.Accountlist"));
                if (allLinks.size() > 1) {
                    int intIndex = 1;
                    for (WebElement ele : allLinks) {
                        String linkname = ele.getText();
                        if (linkname.contains(strAccnt)) {
                            ele.click();
                            report.updateTestLog("selectAccountPayFrom", linkname + " :: clicked and selected ", Status_CRAFT.PASS);
                            break;
                        }
                        intIndex = intIndex++;
                    }
                }
            } else {
                report.updateTestLog("validateOrderInterestCertFormPage", "Label 'Account'is not displayed.", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("OrderInteCert.lstSelectTaxYear"), 3)) {
                clickJS(getObject("OrderInteCert.lstSelectTaxYear"), "Click on Select Year");
                waitForJQueryLoad();
                waitForPageLoaded();
                List<WebElement> arrValidationYear = findElements(getObject("xpath~//li[contains(@class,'option')][contains(@id,'Select year')]"));
                int index = 0;
                for (int i = 0; i < arrValidationYear.size(); i++) {
                    String strYearText = arrValidationYear.get(i).getText();
                    if (strYearText.contains(strYear)) {
                        index = i;
                    }
                }
                String strYearToClick = "xpath~//li[contains(@class,'option')][@data-value='" + strYear + "']";
                clickJS(getObject(strYearToClick), "Clicks on Option :" + strYear);

                waitForJQueryLoad();
                waitForPageLoaded();
            } else {
                report.updateTestLog("validateOrderInterestCertFormPage", "Label 'Tax year ended'is not displayed.", Status_CRAFT.FAIL);
            }
            if (strNotification.equalsIgnoreCase("Yes")) {
                click(getObject("OrderInteCert.lblNotifyRequestYes"), "Selected Yes Option");
                if (strNotificationFlag.equalsIgnoreCase("Yes")) {
                    clickJS(getObject("OrderInteCert.btnContinue"), "Continue button is clicked without selecting a 'Contact Preference' method");
                                    }
                if (strContactPreference.equalsIgnoreCase("Email")) {
                    clickJS(getObject("OrderInteCert.lblContactEmail"), "Email Option Button");
                    waitForJQueryLoad();
                    waitForPageLoaded();
                    sendKeys(getObject("OrderInteCert.txtContactEmail"), strEmail, "Email address Entered");
                } else if (strContactPreference.equalsIgnoreCase("Phone")) {
                    clickJS(getObject("OrderInteCert.lblContactMobile"), "Phone As A Preference");
                    waitForJQueryLoad();
                    waitForPageLoaded();
                    sendKeys(getObject("OrderInteCert.txtMobileNumber"), strPhone, "Phone Number Entered");
                }
            }
            else if (strNotification.equalsIgnoreCase("No")) {
                click(getObject("OrderInteCert.lblNotifyRequestNo"), "Selected 'No' Option");
                report.updateTestLog("enterOrderInterestCertFormPage", "Notofication Method is selected as 'No'", Status_CRAFT.PASS);
            }
            clickJS(getObject("OrderInteCert.btnContinue"), "Continue (Enter Details Page)");
        }

    }
    public void OrderIntCertsReviewPage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String strAccount = dataTable.getData("General_Data", "Account_Name");

        //validating page title 'Order Interest Certificates'
        if (isElementDisplayed(getObject("OrderInteCert.hdrReview"),5)){
            if(isMobile){
                isElementDisplayedWithLog(getObject("mw.OrderInteCert.header"),
                        "'Order Interest Certificates' Header", " 'Order Interest Certificates' Review " ,5);
            }else{
                isElementDisplayedWithLog(getObject(deviceType() + "OrderInteCert.header"),
                        "'Order Interest Certificates' Header", " 'Order Interest Certificates' Review " ,5);
            }

            isElementDisplayedWithLog(getObject("OrderInteCert.hdrReview"),
                "Section Header 'Review'", " 'Order Interest Certificates' Review " ,25);
            if (isElementDisplayed(getObject("xpath~//*[text()='Account']"), 5)) {
                report.updateTestLog("validateOrderIntCertReviewPage", "Label 'Account' is displayed on Order Interest Certificates Review page", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("xpath~//*[text()='Account']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[contains(text(),'" + strAccount + "')]"), 5)) {
                String actAccount = getText(getObject("xpath~//*[text()='Account']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[contains(text(),'" + strAccount + "')]"));
                if (actAccount.contains(strAccount)) {
                    report.updateTestLog("validateOrderIntCertReviewPage", "Account  '" + actAccount + "' is appearing on Review Page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("validateOrderIntCertReviewPage", "Amount '" + actAccount + "' is not appearing on Review Page", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("validateOrderIntCertReviewPage", "Label Account is NOT displayed on Pay To Mobile Review page", Status_CRAFT.FAIL);
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
                    report.updateTestLog("validateOrderIntCertReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("validateOrderIntCertReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
                }
            } else {
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
                    report.updateTestLog("validateOrderIntCertReviewPage", "'" + obj.getKey() + "' is displayed on review page for blank input", Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("validateOrderIntCertReviewPage", "'" + obj.getKey() + "' is Not displayed on review page for blank input", Status_CRAFT.PASS);
                }
            }
        }

        if (isElementDisplayed(getObject("xpath~//a[@role='button']/span[text()='Go back']"), 5)) {
            report.updateTestLog("validateOrderIntCertReviewPage", "'Go back' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateOrderIntCertReviewPage", "'Go back' button is NOT displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("xpath~//a/descendant::*[text()='Confirm']"), 5)) {
            report.updateTestLog("validateOrderIntCertReviewPage", "'Confirm' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateOrderIntCertReviewPage", "'Confirm' button is NOT displayed", Status_CRAFT.FAIL);
        }

        String strOperation = dataTable.getData("General_Data", "OperationOnReviewPage");
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']")));
        Thread.sleep(2000);
        waitForElementToClickable(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']"), 5);
        clickJS(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
        if (strOperation.equalsIgnoreCase("Go back")) {
            if (isElementDisplayed(getObject(deviceType() + "OrderInteCert.header"), 5)) {
                report.updateTestLog("validateOrderIntCertReviewPage", "Page successfully navigated to Order Interest Certificates Form page, after clicking 'Go back'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validateOrderIntCertReviewPage", "Page did not navigate to validateOrderIntCertReviewPage Form page, after clicking 'Go back' button", Status_CRAFT.FAIL);
            }
        }
    }else{
            homePage.verifyElementDetails("OrderIntcert.messagetextvalidation");
        }
    }

    public void OrderIntCertsSuccessPage() throws Exception {
        //validating page title 'Order Interest Certificates'
        String strExpMessage = dataTable.getData("General_Data", "DirectionalText").trim();
        String strActMessage =  getText(getObject("OrderInteCert.txtMessage")).trim();
        waitForPageLoaded();
        if(isMobile){
            isElementDisplayedWithLog(getObject("mw.OrderInteCert.header"),
                    "'Order Interest Certificates' Header", " 'Order Interest Certificates' Success " ,5);
        }else{
            isElementDisplayedWithLog(getObject(deviceType() + "OrderInteCert.header"),
                    "'Order Interest Certificates' Header", " 'Order Interest Certificates' Success " ,5);
        }

        isElementDisplayedWithLog(getObject("OrderInteCert.iconConfirmation"),
                " Success Icon 'Tick Mark'", " 'Order Interest Certificates' Success " ,5);

        isElementDisplayedWithLog(getObject("OrderInteCert.txtSuccess"),
                "Text message 'Success'", " 'Order Interest Certificates' Success " ,5);

        if (strActMessage.contentEquals(strExpMessage))
        {
            report.updateTestLog("validateOrderIntCertSuccessPage", "The informative message :: "+ strActMessage +" :: correctly displayed on 'Order Interest Certificates' Success Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateOrderIntCertSuccessPage", "The informative message :: "+ strActMessage +" :: Is NOT correctly displayed on 'Order Interest Certificates' Success Page", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("OrderInteCert.btnBackToHome"),
                "Button 'Back to home'", " 'Order Interest Certificates' Success ",25);
        String strRef = getText(getObject("OrderInteCert.txtReference"));
        if (isElementDisplayed(getObject("OrderInteCert.txtReference"), 5)) {
            report.updateTestLog("validateOrderIntCertSuccessPage", " 'Reference: '" + strRef + "' is displayed on Order Interest Certificates' Success page", Status_CRAFT.PASS);
            } else {
            report.updateTestLog("validateOrderIntCertSuccessPage", " 'Reference: '" + strRef + "' is NOT displayed correctly on Order Interest Certificates' Success page", Status_CRAFT.PASS);
            }

        if (isElementDisplayed(getObject("OrderInteCert.txtTimeReq"), 5)) {
            report.updateTestLog("validateOrderIntCertSuccessPage", "Time Stamp with message 'Time of request' is displayed on Order Interest Certificates' Success page", Status_CRAFT.PASS);

            String strRequestSubmitted = getText(getObject("OrderInteCert.txtTimeReq"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("validateOrderIntCertSuccessPage", "Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("validateOrderIntCertSuccessPage", "Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("validateOrderIntCertSuccessPage", "Time Stamp with message 'Time of request' is NOT displayed on Order Interest Certificates' Success Page", Status_CRAFT.FAIL);
        }

        //Validating Navigation/functionality of the buttons

                if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to home")) {
                    isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Reference: ')]"),"Reference Number","Reference number is dispalyed",5);
                    Referencenumber = getText(getObject("xpath~//div[contains(text(),'Reference: ')]"),10);
                    isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Time of request: ')]"),"Time of Request","Time of request is dispalyed",5);
                    TimeofRequest = getText(getObject("xpath~//div[contains(text(),'Time of request: ')]"),5);
                    clickJS(getObject("OrderInteCert.btnBackToHome"), "Clicked on 'Back to home'");
                    if (isElementDisplayed(getObject(deviceType() + "home.titleMyAccounts"), 5)) {
                        report.updateTestLog("validateOrderIntCertSuccessPage", "Upon clicking 'Back to home' ,successfully Navigated to HomePage screen from Order Interest Certificates' Success Page", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("validateOrderIntCertSuccessPage", "Upon clicking 'Back to home' ,navigation unsuccessful to HomePage screen from Order Interest Certificates' Success Page", Status_CRAFT.FAIL);
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

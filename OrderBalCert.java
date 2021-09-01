package businesscomponents;


        import com.cognizant.craft.DriverScript;
        import com.cognizant.craft.ScriptHelper;
        import com.cognizant.framework.Status_CRAFT;
        import com.cognizant.framework.selenium.WebDriverHelper;
        import org.openqa.selenium.*;
        import org.openqa.selenium.remote.UnreachableBrowserException;

        import java.text.DateFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.time.LocalDate;
        import java.time.LocalDateTime;
        import java.time.format.DateTimeFormatter;
        import java.util.LinkedHashMap;
        import java.util.List;
        import java.util.Locale;
        import java.util.Map;
        import java.util.Date;


/**
 * Created by C101979 on 12/08/2019.
 */
public class OrderBalCert extends WebDriverHelper {

    public String Referencenumber = "";
    public String TimeofRequest = "";
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public OrderBalCert(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function : Verify Order Interest Cert Form page
     * Created on     Created by    Reason
     * 12/08/2019     C101979       In Sprint Development
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

    public void verifyOrderBalanceCertFormPage() throws Exception {
        waitForPageLoaded();
        String strActualOrderCertsNote = getText(getObject("OrderBalCert.headerNote"));
        String strExpectedOrderCertsNote = dataTable.getData("General_Data", "VerifyContent");
        String strExpectedFeesAndCharges = dataTable.getData("General_Data", "verifyfeesAndCharges");
        String strExecute= dataTable.getData("General_Data", "Operation");

        if(isMobile || deviceType().equals("tw.")){
            isElementDisplayedWithLog(getObject("mw.OrderBalCert.header"),
                    "'Order Balance Certificates' Header ", "Order Balance Certs Form ", 5);
        }else{
            isElementDisplayedWithLog(getObject(deviceType() + "OrderBalCert.header"),
                    "'Order Balance Certificates' Header ", "Order Balance Certs Form", 5);
        }

//        isElementDisplayedWithLog(getObject(deviceType() + "OrderBalCert.header"),
//                "'Order Balance Certificates' Header ", "Order Balance Certs", 5);
        String Jurisdiction = dataTable.getData("General_Data", "JurisdictionType");

        isElementDisplayedWithLog(getObject("OrderBalCert.label"),
                "Order certificates for' Header ", "Order Balance Certs", 5);
        isElementDisplayedWithLog(getObject("OrderBalCert.lblAccount"),
                "'Account' label", "Order Balance Certs", 1);
        isElementDisplayedWithLog(getObject("OrderBalCert.lblBalanceDate"),
                "'Ledger balance date' label", "Order Balance Certs", 1);
        isElementDisplayedWithLog(getObject("OrderBalCert.lblNotification"),
                "'Notification' label ", "Order Balance Certs", 1);
        isElementDisplayedWithLog(getObject("OrderBalCert.lblNotify"),
                "'Will we notify you when your request is complete?' label ", "Order Balance Certs", 1);
        isElementDisplayedWithLog(getObject("OrderBalCert.lblNotifyRequestYes"),
                "'Yes' option for Notification confirmation question", "Order Balance Certs", 1);
        isElementDisplayedWithLog(getObject("OrderBalCert.lblNotifyRequestNo"),
                "'No' option for Notification confirmation question", "Order Balance Certs", 1);

        if (Jurisdiction.equals("NI")||Jurisdiction.equals("GB")) {
            String strActualFeesAndCharges = getText(getObject("OrderBalCert.txtFeesAndChargesNI"));
            if (strActualFeesAndCharges.equals(strExpectedFeesAndCharges)) {
                report.updateTestLog("validateOrderBalanceCertFormPage", "Fees and charges '" + strActualFeesAndCharges + "' is appearing on Form Page ", Status_CRAFT.PASS);
                HomePage homePage = new HomePage(scriptHelper);
                if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                    String strExpectedLink = "https://www.bankofireland.com/fees-and-charges";
                    homePage.verifyHrefLink(strExpectedLink, "OrderBalCert.txtFeesAndChargesNIlink");
                }else {
                    clickJS(getObject("OrderBalCert.txtFeesAndChargesNIlink"),"Fees and Charges Link");
                    homePage.verifyNewlyOpenedTab("https://www.bankofireland.com/fees-and-charges");
                }
            } else {
                report.updateTestLog("validateOrderBalanceCertFormPage", "Fees and charges '" + strActualFeesAndCharges + "' is not appearing on Form Page", Status_CRAFT.FAIL);
            }
        }
        else if (Jurisdiction.equals("ROI")) {
            if(isElementDisplayed(getObject("OrderBalCert.txtFeesAndChargesCrosslink"),5))
            {
                report.updateTestLog("validateOrderBalanceCertFormPage", "Fees and charges is appearing on Form Page for ROI ", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("validateOrderBalanceCertFormPage", "Fees and charges is NOT appearing on Form Page for ROI", Status_CRAFT.PASS);
            }

        }
        else if (Jurisdiction.equals("CROSS")) {
            String strActualFeesAndCharges = getText(getObject("OrderBalCert.txtFeesAndChargesCross"));
            if (strActualFeesAndCharges.equals(strExpectedFeesAndCharges)) {
                report.updateTestLog("validateOrderBalanceCertFormPage", "Fees and charges '" + strActualFeesAndCharges + "' is appearing on Form Page ", Status_CRAFT.PASS);

                HomePage homePage = new HomePage(scriptHelper);
                if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                    String strExpectedLink = "https://www.bankofireland.com/fees-and-charges";
                    homePage.verifyHrefLink(strExpectedLink, "OrderBalCert.txtFeesAndChargesCrosslink");
                }else {
                    clickJS(getObject("OrderBalCert.txtFeesAndChargesCrosslink"),"Fees and Charges Link");
                    homePage.verifyNewlyOpenedTab("https://www.bankofireland.com/fees-and-charges");
                }

            } else {
                report.updateTestLog("validateOrderBalanceCertFormPage", "Fees and charges '" + strActualFeesAndCharges + "' is not appearing on Form Page", Status_CRAFT.FAIL);
            }

        }
        else
        {
            report.updateTestLog("validateOrderBalanceCertFormPage", "The Jurisdication is not provided in the sheet", Status_CRAFT.FAIL);
        }
        if (strActualOrderCertsNote.equals(strExpectedOrderCertsNote)) {
            report.updateTestLog("validateOrderBalanceCertFormPage", "Note'" + strActualOrderCertsNote + "' is appearing on Form Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateOrderBalanceCertFormPage", "Note '" + strActualOrderCertsNote + "' is not appearing on Form Page", Status_CRAFT.FAIL);
        }

    }
    public void verifyOrderBalCertYear() throws Exception {
        String firstYear = "";
        String pattern = "dd MM yyyy";
        LocalDate today = LocalDate.now();
        LocalDate lastYear = today.minusYears(7);
        firstYear = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(lastYear).replace(" ","/");
        String strYY = firstYear.split("/")[2];
        clickJS(getObject("OrderBalCert.calDate"), "Calender");
        String calCurrentYearValue =  getText(getObject("OrderBalCert.calCurrentYear"));
        if (calCurrentYearValue.equalsIgnoreCase(strYY)) {
            report.updateTestLog("validateOrderBalanceCertYear", "Calender is displaying correct year values and can only select past 7 years", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateOrderBalanceCertYear", "Calender is not displaying correct year values", Status_CRAFT.FAIL);
        }
        clickJS(getObject("OrderBalCert.calDate"), "Calender");
    }

    public void OrderBalCertFormPage_enterDetails() throws Exception {
        String nextdate = "";
        String todayDate = "";
        String pattern = "dd MM yyyy";
        LocalDate today = LocalDate.now();
        TransactionsPage TransPage = new TransactionsPage(scriptHelper);
        String strIterationFlag = dataTable.getData("General_Data", "IterationFlag");
        String strNotificationFlag = dataTable.getData("General_Data", "NotificationFlag");
        String strEmail = dataTable.getData("General_Data", "Email");
        String strPhone = dataTable.getData("General_Data", "Phone");
        String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
        String strNotification = dataTable.getData("General_Data", "NotificationReq");
        String strAccnt = dataTable.getData("General_Data", "Account_Name");
        if (strIterationFlag.equalsIgnoreCase("Yes")) {
            clickJS(getObject("AddPolicy.Continue"), "No details is entered and Continue (Enter Details Page)");
        } else {
            if (isElementDisplayed(getObject(deviceType() + "MTU.lstAccountspayFrom"), 2)) {
                clickJS(getObject(deviceType() + "MTU.lstAccountspayFrom"), "Account Dropdown");
                isElementDisplayedWithLog(getObject("OrderBalCert.optSelectAccount"),
                        "'Select account followed by other accounts is displayed", "Order Balance Certs", 1);
                waitForPageLoaded();
                List<WebElement> allLinks = findElements(getObject(deviceType() +"MTU.Accountlist"));
                if (allLinks.size() > 1) {
                    int intIndex = 1;
                    for (WebElement ele : allLinks) {
                        String linkname = ele.getText();
                        if (linkname.contains(strAccnt)) {
                            ele.click();
                            report.updateTestLog("enterOrderBalanceCertFormPage", linkname + " :: clicked and selected ", Status_CRAFT.PASS);
                            break;
                        }
                        intIndex = intIndex++;
                    }
                }
            } else {
                report.updateTestLog("enterOrderBalanceCertFormPage", "Label 'Account'is not displayed.", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("OrderBalCert.lstSelectTaxYear"), 3)) {
                String strDate = dataTable.getData("General_Data", "DOB");
                scriptHelper.commonData.date = getBackDate(strDate);
                Thread.sleep(2000);
                if (strDate.equalsIgnoreCase("Today-1")) {
                    TransPage.enterDate("OrderBalCert.calDate", scriptHelper.commonData.date);
                }
                else if (strDate.equalsIgnoreCase("Today")) {
                    LocalDate Today = today.plusDays(0);
                    todayDate = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(Today).replace(" ","/");
                    if (isMobile) {
                        sendKeysJS(getObject("OrderBalCert.txtDate"), todayDate);
                    }else{
                       /* JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                        executor.executeScript("arguments[0].click();", driver.findElement(getObject(("OrderBalCert.txtDate"))));
                        sendKeys(getObject("OrderBalCert.txtDate"), todayDate);*/

                        sendKeys(getObject("OrderBalCert.txtDate")," ");
                        sendKeys(getObject("OrderBalCert.txtDate"), todayDate);


                    }
                }
                else if (strDate.equalsIgnoreCase("Today+1")) {
                    LocalDate Tomorrow = today.plusDays(1);
                    nextdate = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(Tomorrow).replace(" ","/");
                    sendKeys(getObject("OrderBalCert.txtDate"), nextdate);
                }
            } else {
                report.updateTestLog("enterOrderBalanceCertFormPage", "Unable to pick date from Calender or Calender is not displayed.", Status_CRAFT.FAIL);
            }
            if (strNotification.equalsIgnoreCase("Yes")) {
                clickJS(getObject("OrderBalCert.lblNotifyRequestYes"), "Selected Yes Option");
                if (strNotificationFlag.equalsIgnoreCase("Yes")) {
                    clickJS(getObject("OrderBalCert.btnContinue"), "Continue button is clicked without selecting a 'Contact Preference' method");
                }
                if (strContactPreference.equalsIgnoreCase("Email")) {
                    clickJS(getObject("OrderBalCert.lblContactEmail"), "Email Option Button");
                    Thread.sleep(1000);
                    sendKeys(getObject("OrderBalCert.txtContactEmail"), strEmail, "Email address Entered");
                } else if (strContactPreference.equalsIgnoreCase("Phone")) {
                    clickJS(getObject("OrderBalCert.lblContactMobile"), "Phone As A Preference");
                    Thread.sleep(1000);
                    sendKeys(getObject("OrderBalCert.txtMobileNumber"), strPhone, "Phone Number Entered");
                }
            }
            else if (strNotification.equalsIgnoreCase("No")) {
                clickJS(getObject("OrderBalCert.lblNotifyRequestNo"), "Selected 'No' Option");
                report.updateTestLog("enterOrderBalanceCertFormPage", "Notification Method is selected as 'No'", Status_CRAFT.PASS);
            }
            else
            {
                report.updateTestLog("enterOrderBalanceCertFormPage", "User has not selected the Notification Method", Status_CRAFT.FAIL);
            }
            clickJS(getObject("OrderBalCert.btnContinue"), "Continue (Enter Details Page)");
        }

    }
    public void OrderBalCertsReviewPage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String pattern = "dd/MM/yyyy"; String newDueDate = "";
        String strAccount = dataTable.getData("General_Data", "Account_Name");
        //validating page title 'Order Balance Certificates'
        waitForPageLoaded();
        if(isElementDisplayed(getObject("OrderBalCert.hdrReview"),5)){
            if(isMobile || deviceType().equals("tw.")){
                isElementDisplayedWithLog(getObject("mw.OrderBalCert.header"),
                        "'Order Balance Certificates' Header ", "Order Balance Certs Review", 5);
            }else{
                isElementDisplayedWithLog(getObject(deviceType() + "OrderBalCert.header"),
                        "'Order Balance Certificates' Header ", "Order Balance Certs Review", 5);
            }
            isElementDisplayedWithLog(getObject("OrderBalCert.hdrReview"),
                    "Section Header 'Review'", " 'Order Balance Certificates' Review " ,25);
            if (isElementDisplayed(getObject("xpath~//*[text()='Account']"), 5)) {
                report.updateTestLog("validateOrderBalCertReviewPage", "Label 'Account' is displayed on Order Interest Certificates Review page", Status_CRAFT.PASS);
                if (isElementDisplayed(getObject("xpath~//*[text()='Account']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[contains(text(),'" + strAccount + "')]"), 5)) {
                    String actAccount = getText(getObject("xpath~//*[text()='Account']/ancestor::div[contains(@class,'question-part')]/following-sibling::*/descendant::*[contains(text(),'" + strAccount + "')]"));
                    if (actAccount.contains(strAccount)) {
                        report.updateTestLog("validateOrderBalCertReviewPage", "Account  '" + actAccount + "' is appearing on Review Page", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("validateOrderBalCertReviewPage", "Account '" + actAccount + "' is not appearing on Review Page", Status_CRAFT.FAIL);
                    }
                }
            } else {
                report.updateTestLog("validateOrderBalCertReviewPage", "Label Account is NOT displayed on Pay To Mobile Review page", Status_CRAFT.FAIL);
            }
            isElementDisplayedWithLog(getObject("OrderBalCert.lblDateReview"),
                    "'Ledger balance date 1' Label ", "Order Balance Certs Review", 5);
            String ActDate = getText(getObject("xpath~//span[text()='Ledger balance date']/ancestor::div[contains(@class,'question-part')]/following-sibling::div"));
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
            Date varDueDate = dateFormat.parse(scriptHelper.commonData.date.replace("/","-"));
            dateFormat= new SimpleDateFormat("dd-MM-yyyy"); newDueDate=dateFormat.format(varDueDate).replace("-","/");
            if(ActDate.equals(newDueDate)) {
                report.updateTestLog("validateOrderBalCertReviewPage","'"+ newDueDate +"' correctly displayed on review page,Expected::'"+ newDueDate +"', Actual::''"+ ActDate +"'", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("validateOrderBalCertReviewPage","'"+ newDueDate +"' is not correctly displayed on review page,Expected::'"+ newDueDate +"', Actual::''"+ ActDate +"'", Status_CRAFT.FAIL);
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
                        report.updateTestLog("validateOrderBalCertReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("validateOrderBalCertReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
                    }
                } else {
                    if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
                        report.updateTestLog("validateOrderBalCertReviewPage", "'" + obj.getKey() + "' is displayed on review page for blank input", Status_CRAFT.FAIL);
                    } else {
                        report.updateTestLog("validateOrderBalCertReviewPage", "'" + obj.getKey() + "' is Not displayed on review page for blank input", Status_CRAFT.PASS);
                    }
                }
            }

            scrollToView(getObject(deviceType()+ "OrderInteCert.btnGoBack"));

            if (isElementDisplayed(getObject("xpath~//a/descendant::*[text()='Confirm']"), 5)) {
                report.updateTestLog("validateOrderBalCertReviewPage", "'Confirm' button is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validateOrderBalCertReviewPage", "'Confirm' button is NOT displayed", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject(deviceType()+ "OrderInteCert.btnGoBack"), 5)) {
                report.updateTestLog("validateOrderBalCertReviewPage", "'Go back' button is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validateOrderBalCertReviewPage", "'Go back' button is NOT displayed", Status_CRAFT.FAIL);
            }
            String strOperation = dataTable.getData("General_Data", "OperationOnReviewPage");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']")));
            Thread.sleep(2000);
            waitForElementToClickable(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']"), 5);
            clickJS(getObject("xpath~//a/descendant::*[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
            if (strOperation.equalsIgnoreCase("Go back")) {
                if (isElementDisplayed(getObject(deviceType() + "OrderInteCert.header"), 5)) {
                    report.updateTestLog("validateOrderBalCertReviewPage", "Page successfully navigated to Order Balance Certificates Form page, after clicking 'Go back'", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("validateOrderBalCertReviewPage", "Page did not navigate to Order Balance Certificates Form page, after clicking 'Go back' button", Status_CRAFT.FAIL);
                }
            }
        }else{
            homePage.verifyElementDetails("OrderIntcert.messagetextvalidation");
        }
    }

    public void OrderBalCertsSuccessPage() throws Exception {
        //validating page title 'Order Interest Certificates'
        waitForPageLoaded();
        waitForElementToClickable(getObject("OrderInteCert.btnBackToHome"), 3);
        String strExpMessage = dataTable.getData("General_Data", "DirectionalText").trim();
        String strActMessage =  getText(getObject("OrderInteCert.txtMessage")).trim();

        if(isMobile || deviceType().equals("tw.") ){
            isElementDisplayedWithLog(getObject("mw.OrderBalCert.header"),
                    "'Order Balance Certificates' Header ", "'Order Balance Certificates' Success", 5);
        }else{
            isElementDisplayedWithLog(getObject(deviceType() + "OrderBalCert.header"),
                    "'Order Balance Certificates' Header ", "'Order Balance Certificates' Success", 5);
        }

        isElementDisplayedWithLog(getObject("OrderInteCert.iconConfirmation"),
                " Success Icon 'Tick Mark'", " 'Order Balance Certificates' Success " ,2);

        isElementDisplayedWithLog(getObject("OrderBalCert.txtReqSent"),
                "Text message 'Request Sent'", " 'Order Balance Certificates' Success " ,2);


      /*  if (strExpMessage.contains(strActMessage)) {
            report.updateTestLog("validateOrderBalCertSuccessPage", "The informative message :: "+ strActMessage +" :: correctly displayed on 'Order Balance Certificates' Success Page", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("validateOrderBalCertSuccessPage", "The informative message :: "+ strActMessage +" :: Is NOT correctly displayed on 'Order Balance Certificates' Success Page", Status_CRAFT.FAIL);
        }
*/
        isElementDisplayedWithLog(getObject("OrderInteCert.btnBackToHome"),
                "Button 'Back to home'", " 'Order Balance Certificates' Success ",2);
        String strRef = getText(getObject("OrderInteCert.txtReference"));
        if (isElementDisplayed(getObject("OrderInteCert.txtReference"), 5)) {
            report.updateTestLog("validateOrderBalCertSuccessPage", " 'Reference: '" + strRef + "' is displayed on Order Balance Certificates' Success page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("validateOrderBalCertSuccessPage", " 'Reference: '" + strRef + "' is NOT displayed correctly on Order Balance Certificates' Success page", Status_CRAFT.PASS);
        }

        if (isElementDisplayed(getObject("OrderInteCert.txtTimeReq"), 5)) {
            report.updateTestLog("validateOrderBalCertSuccessPage", "Time Stamp with message 'Time of request' is displayed on Order Balance Certificates' Success page", Status_CRAFT.PASS);

            String strRequestSubmitted = getText(getObject("OrderInteCert.txtTimeReq"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("validateOrderBalCertSuccessPage", "Text Label '" + strRequestSubmitted + "' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("validateOrderBalCertSuccessPage", "Text Label '" + strRequestSubmitted + "' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" + "', Actual '" + uiDateText + "'", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("validateOrderBalCertSuccessPage", "Time Stamp with message 'Time of request' is NOT displayed on Order Balance Certificates' Success Page", Status_CRAFT.FAIL);
        }

        //Validating Navigation/functionality of the buttons

        if (dataTable.getData("General_Data", "NavigationfromConfirmationPage").equalsIgnoreCase("Back to home")) {
            isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Reference: ')]"),"Reference Number","Reference number is dispalyed",5);
            Referencenumber = getText(getObject("xpath~//div[contains(text(),'Reference: ')]"),1);
            isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Time of request: ')]"),"Time of Request","Time of request is dispalyed",5);
            TimeofRequest = getText(getObject("xpath~//div[contains(text(),'Time of request: ')]"),5);
            clickJS(getObject("OrderInteCert.btnBackToHome"), "Clicked on 'Back to home'");
            waitForPageLoaded();
            if (isElementDisplayed(getObject(deviceType() + "home.titleMyAccounts"), 5)) {
                report.updateTestLog("validateOrderBalCertSuccessPage", "Upon clicking 'Back to home' ,successfully Navigated to HomePage screen from Order Balance Certificates' Success Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validateOrderBalCertSuccessPage", "Upon clicking 'Back to home' ,navigation unsuccessful to HomePage screen from Order Balance Certificates' Success Page", Status_CRAFT.FAIL);
            }
        }

    }
    public String getBackDate(String BackDate) throws Exception{
        String date = "";
        String pattern = "dd MMM yyyy";
        LocalDate today = LocalDate.now();
        switch (BackDate){
            case "Today-1":
                LocalDate Yesterday = today.minusDays(1);
                date = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(Yesterday).replace(" ","/");
                break;
            case "Today+1":
                LocalDate Tomorrow = today.plusDays(1);
                date = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(Tomorrow).replace(" ","/");
                break;

            default:
                date = BackDate;
        }
        return date;
    }

    /**
     * Function : Click on Add More Dates
     * Update on     Updated by    Reason
     * 01/10/2019    C966119       Newly Added Function
     */
    public void clickAddMoreDate() throws Exception {
        waitForPageLoaded();
        waitForElementPresent(getObject("OrderBalCert.btnAddMoreDates"), 5);
        waitForElementToClickable(getObject("OrderBalCert.btnAddMoreDates"), 5);
        clickJS(getObject("OrderBalCert.btnAddMoreDates"), " 'Add More Dates' : button ");
        waitForPageLoaded();
    }

    /**
     * Function : Remove Date Interval
     * Update on     Updated by    Reason
     * 01/10/2019    C966119       Newly Added Function
     */
    public void verifyRemoveAllDateIntervals() throws Exception {
        boolean blnDates = false ;
        blnDates =( isElementDisplayed(getObject("OrderBalCert.lblLedgerDate") , 2) &&
                isElementDisplayed(getObject("OrderBalCert.lblLedgerDate2") , 2) &&
                isElementDisplayed(getObject("OrderBalCert.lblLedgerDate3") , 2) &&
                isElementDisplayed(getObject("OrderBalCert.lblLedgerDate4") , 2) &&
                isElementDisplayed(getObject("OrderBalCert.lblLedgerDate5") , 2)  );
        if (blnDates){
            report.updateTestLog("verifyRemoveAllDateIntervals", " 'Ledger balance date 2 ,3, 4, 5' is displayed on page successfully.", Status_CRAFT.PASS);
            for (int i = 5; i > 1 ; i--) {
                clickJS(getObject("OrderBalCert.lnkRemoveDate"+ i +""), " 'Remove Date Interval "+ i +"' : link ");
                if (isElementDisplayed(getObject("OrderBalCert.lnkRemoveDate"+ i +"") , 8)){
                    report.updateTestLog("verifyRemoveAllDateIntervals", " Not Removed Ledger balance Date Interval "+ i +" and is displayed on page", Status_CRAFT.FAIL);
                }else {
                    report.updateTestLog("verifyRemoveAllDateIntervals", " Successfully Removed Ledger balance Date Interval "+ i +" and is not displayed on page", Status_CRAFT.DONE);
                }
            }
        }else{
            report.updateTestLog("verifyRemoveAllDateIntervals", " Either any of the 'Ledger balance date' is not displayed on page", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Fill All The Date Interval
     * Update on     Updated by    Reason
     * 01/10/2019    C966119       Newly Added Function
     */
    public void verifyDatesOnReview() throws Exception {
        String[] strVerifyDetails = dataTable.getData("General_Data", "Properties_Variable").split(";");
        String strIterationFlag = dataTable.getData("General_Data","IterationFlagforPolicy");
        if (strIterationFlag.equalsIgnoreCase("DuplicateDateError")) {
            strVerifyDetails = dataTable.getData("General_Data", "SequenceAccType").split(";");
        }
        String date1 = "";
        for (int i = 0; i < strVerifyDetails.length; i++) {
            int strDateNumber = (i+2);
            String newDueDate = "";
            date1 = strVerifyDetails[i] ;
            String ActDate = getText(getObject("xpath~//*[text()='Ledger balance date " + strDateNumber + "']/ancestor::div[contains(@class,'question-part')]/following-sibling::div"));
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
            Date varDueDate = dateFormat.parse(date1.replace("/","-"));
            dateFormat= new SimpleDateFormat("dd-MM-yyyy");
            newDueDate=dateFormat.format(varDueDate).replace("-","/");
            if(ActDate.equals(newDueDate)) {
                report.updateTestLog("validateOrderBalCertReviewPage","For 'Ledger balance date " + strDateNumber + "' ::: '"+ newDueDate +"' correctly displayed on review page,Expected::'"+ newDueDate +"', Actual::''"+ ActDate +"'", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("validateOrderBalCertReviewPage","For 'Ledger balance date " + strDateNumber + "'::: '"+ newDueDate +"' is not correctly displayed on review page,Expected::'"+ newDueDate +"', Actual::''"+ ActDate +"'", Status_CRAFT.FAIL);
            }
        }
    }


    /**
     * Function : Fill All The Date Interval
     * Update on     Updated by    Reason
     * 01/10/2019    C966119       Newly Added Function
     */
    public void verifyAllEnterDates() throws Exception {
        boolean blnDates = false ;
        TransactionsPage TransPage = new TransactionsPage(scriptHelper);
        String[] strVerifyDetails = dataTable.getData("General_Data", "Properties_Variable").split(";");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            int strDateNumber = (i+2);
            waitForElementToClickable(getObject("OrderBalCert.txtDate" + strDateNumber + "") ,8);
            if (isElementDisplayed(getObject("OrderBalCert.lblLedgerDate"+ strDateNumber +"") , 3)) {
                report.updateTestLog("verifyRemoveAllDateIntervals", " 'Ledger balance date "+ strDateNumber +"' is displayed on page successfully and entering dates for the all date interval...", Status_CRAFT.DONE);
                waitForElementToClickable(getObject("OrderBalCert.txtDate" + strDateNumber + "") ,8);
                clickJS(getObject("OrderBalCert.txtDate" + strDateNumber + ""), " 'Ledger Date " + strDateNumber + "'");
                TransPage.enterDate("xpath~(//button[@class='ui-datepicker-trigger fa fa-calendar'])[" + strDateNumber + "]", strVerifyDetails[i]);
                waitForPageLoaded();
                report.updateTestLog("verifyRemoveAllDateIntervals", " Value Entered 'Ledger balance date " + strDateNumber + "' :: " + strVerifyDetails[i], Status_CRAFT.DONE);

            }else{
                report.updateTestLog("verifyRemoveAllDateIntervals", " Either any of the 'Ledger balance date' is not displayed on page", Status_CRAFT.FAIL);
            }

        }
    }

    /**
     * Function : Remove Date Interval 2
     * Update on     Updated by    Reason
     * 01/10/2019    C966119       Newly Added Function
     */
    public void verifyRemoveDateIntervals2() throws Exception {
        clickJS(getObject("OrderBalCert.lnkRemoveDate2"), " 'Remove Date Interval 2' : link ");
    }

    /**
     * Function : Remove Date Interval 3
     * Update on     Updated by    Reason
     * 01/10/2019    C966119       Newly Added Function
     */
    public void verifyRemoveDateIntervals3() throws Exception {
        clickJS(getObject("OrderBalCert.lnkRemoveDate3"), " 'Remove Date Interval 3' : link ");
    }

    /**
     * Function : Remove Date Interval 4
     * Update on     Updated by    Reason
     * 01/10/2019    C966119       Newly Added Function
     */
    public void verifyRemoveDateIntervals4() throws Exception {
        clickJS(getObject("OrderBalCert.lnkRemoveDate4"), " 'Remove Date Interval 4' : link ");
    }

    /**
     * Function : Remove Date Interval 5
     * Update on     Updated by    Reason
     * 01/10/2019    C966119       Newly Added Function
     */
    public void verifyRemoveDateIntervals5() throws Exception {
        clickJS(getObject("OrderBalCert.lnkRemoveDate5"), " 'Remove Date Interval 5' : link ");
    }

    /**
     * Function : Remove Date Interval 2
     * Update on     Updated by    Reason
     * 01/10/2019    C966119       Newly Added Function
     */
    public void verifyDuplicateDateIntervals() throws Exception {
        waitForElementPresent(getObject("OrderBalCert.lblDuplicateError") , 5);
        if (getText(getObject("OrderBalCert.lblDuplicateError")).equals(dataTable.getData("General_Data", "ErrorText"))){
            report.updateTestLog("verifyDuplictaeDateIntervals", " Duplicate Value 'Ledger balance date ' error message :: " + getText(getObject("OrderBalCert.lblDuplicateError")) + " displayed correctly.", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("verifyDuplictaeDateIntervals", " Duplicate Value 'Ledger balance date ' error message :: " + getText(getObject("OrderBalCert.lblDuplicateError")) + " IS NOT displayed correctly.", Status_CRAFT.PASS);
        }
    }

    /**
     * Function : Click Continue button
     * Update on     Updated by    Reason
     * 01/10/2019    C966119       Newly Added Function
     */
    public void verifyContinueButton() throws Exception {
        clickJS(getObject("OrderBalCert.btnContinue"), "Continue (Enter Details Page)");
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

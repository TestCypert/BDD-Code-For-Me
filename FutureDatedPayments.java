package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by C966003 on 29/08/2019.
 */
public class FutureDatedPayments extends WebDriverHelper {
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public FutureDatedPayments(ScriptHelper scriptHelper) { super(scriptHelper);}

    /**
     * Function : To capture basic details of future dated payment screen
     * Created by : C966003
     * Update on    Updated by     Reason
     * 30/08/2019   C966003        Newly created
     */
    public void basicFDPsPageDetails()throws Exception{
        while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
            waitForPageLoaded();
        }
        isElementDisplayedWithLog(getObject(deviceType()+ "FDPs.lblTitle"),
                "Page title : "+getText(getObject(deviceType()+ "FDPs.lblTitle")),"Future dated pa...",4);
        isElementDisplayedWithLog(getObject(deviceType()+ "FDPs.lblBack"),
                "Back button","Future dated pa...",4);
        isElementDisplayedWithLog(getObject(deviceType()+ "FDPs.lblPersonalDetails"),
                "Personal details icon","Future dated pa...",4);
    }

    /**
     * Function : To select future dated payee to view details
     * Created by : C966003
     * Update on    Updated by     Reason
     * 30/08/2019   C966003        Newly created
     */
    public void selectFDPsPayee()throws Exception{
        if(!deviceType.equalsIgnoreCase("Web") && !deviceType.equalsIgnoreCase("TabletWeb") ){
            selectFDPsPayee_mobile();
        } else {
            selectFDPsPayee_desktop();
        }
    }

    /**
     * Function : To select future dated payee to view details for mobile
     * Created by : C966003
     * Update on    Updated by     Reason
     * 30/08/2019   C966003        Newly created
     */
    public void selectFDPsPayee_mobile()throws Exception{
        waitForJQueryLoad();waitForJQueryLoad();
        String strPayFrom = dataTable.getData("General_Data","PayFrom_Account"); boolean flagPayeeTest = false;
        String strPayTo = dataTable.getData("General_Data","PayTo_Account");
        String strAmount = dataTable.getData("General_Data","Pay_Amount");
        TransferBetweenAccounts tbd = new TransferBetweenAccounts(scriptHelper);
        String strDate = tbd.getFutureDate(dataTable.getData("General_Data","Transfer_Date"));
        String strPayeeStatus = dataTable.getData("General_Data","PayeeStatus");
        String strPayToNew = "";
        if (strPayTo.split("~")[0].length()<=18){
            strPayToNew = strPayTo.split("~")[0].trim();
        } else {
            strPayToNew = strPayTo.split("~")[0].trim().substring(0,18);
        }

        List<WebElement> lstPayee = findElements(getObject("xpath~//*[@role='button']//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'padding-left')]"));
        report.updateTestLog("selectFDPsPayee", lstPayee.size() +" count of "+ strPayToNew +" payees found", Status_CRAFT.DONE);
        for(int i=1; i<=lstPayee.size(); i++){
            if(lstPayee.size()>= 1){
               String strStatusFetched = getText(getObject("xpath~(//*[@role='button']//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'padding-left')])["+i+"]/ancestor::div[@role='button']")).split("\n")[2];
               String strAmountFetched = getText(getObject("xpath~(//*[@role='button']//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'padding-left')])["+i+"]/ancestor::div[@role='button']")).split("\n")[1];
               if(strPayeeStatus.equalsIgnoreCase(strStatusFetched)){
                   waitForJQueryLoad();waitForPageLoaded();
                   waitForElementToClickable(getObject("xpath~(//*[@role='button']//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'padding-left')])[" + i + "]"), 10);
                   clickJS(getObject("xpath~(//*[@role='button']//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'padding-left')])[" + i + "]"), strPayTo.split("~")[0].trim());
                   while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
                       waitForPageLoaded();
                       Thread.sleep(4000); // Thread.Sleep needed as Future dated payment page takes time to load page : blank page issue is alreday reported
                   }
                   if(deviceType.equalsIgnoreCase("Web") && deviceType.equalsIgnoreCase("TabletWeb") ){
                       if(payeeValidator_mobile(strAmount,strPayFrom,strPayTo,strPayToNew,strDate,strPayeeStatus)){
                            flagPayeeTest = true;
                            break;
                       }
                   }else{
                       if(payeeValidator_mobile(strAmount,strPayFrom,strPayTo,strPayToNew,strDate,strPayeeStatus)){
                           flagPayeeTest = true;
                           break;
                       }
                   }

                   clickJS(getObject(deviceType()+"FDPs.lblBack"),"Back");
                   waitForJQueryLoad();waitForPageLoaded();
               }
            } else {
                report.updateTestLog("selectFDPsPayee", "Payee not listed in FDPs list", Status_CRAFT.FAIL);
            }
        }
        if(!flagPayeeTest == true){
            report.updateTestLog("selectFDPsPayee", "Required payee details are not found in FDPs list", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To validate correct payee and return boolean value for mobile
     * Created by : C966003
     * Update on    Updated by     Reason
     * 30/08/2019   C966003        Newly created
     */
    public boolean payeeValidator_mobile(String strAmount, String strPayFrom, String strPayTo, String strPayToNew, String strDueDate, String strStatus)throws Exception{
        boolean testFlag = false; String newDueDate = ""; String newPaidDate = "";
        String strPaymentType = dataTable.getData("General_Data","PayeeMessage");
        String strUnsuccessReason = dataTable.getData("General_Data","UnsuccessfulReason");
        String strPaidDate = dataTable.getData("General_Data","Paid_Date");
        String strIBAN = dataTable.getData("General_Data","IBAN_Number");
        String strBIC = dataTable.getData("General_Data","BIC_Number");
        String strReference = dataTable.getData("General_Data","BillReference");
        String strNSC = dataTable.getData("General_Data","NSC_Number");
        String strPayeeAccountNo = dataTable.getData("General_Data","PayeeAccount_number");
        String strFetchDate = getText(getObject("FDPs.lblPayDueDate"));
        String strFetchPayfrom = getText(getObject("FDPs.lblPayFrom"));
        String strFetchAmount = getText(getObject("FDPs.lblPayAmount"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date varDueDate = dateFormat.parse(strDueDate.replace("/","-"));
        dateFormat=new SimpleDateFormat("dd-MM-yyyy"); newDueDate=dateFormat.format(varDueDate).replace("-","/");

        if(newDueDate.equalsIgnoreCase(strFetchDate)&&
                strPayFrom.split("~")[0].trim().equalsIgnoreCase(strFetchPayfrom)&&
                strAmount.contains(strFetchAmount)){
            report.updateTestLog("selectFDPsPayee", "****** Required payee found ******", Status_CRAFT.PASS);
            fetchTextToCompare(getObject("FDPs.lblPayeeName"),strPayToNew,"Payee name");

            if(strStatus.equalsIgnoreCase("Unsuccessful")){
                fetchTextToCompare(getObject("FDPs.lblUnsuccessReason"),strUnsuccessReason,"Unsuccessful reason");
            } else {
                if(isElementDisplayed(getObject("FDPs.lblUnsuccessReason"),4)){
                    report.updateTestLog("selectFDPsPayee", "'Unsuccessful reason' is displayed for status : "+strStatus, Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("selectFDPsPayee", "'Unsuccessful reason' is not displayed for status : "+strStatus, Status_CRAFT.PASS);
                }
            }
            fetchTextToCompare(getObject("FDPs.lblPaymentStatus"),strStatus,"Payment status");
            fetchTextToCompare(getObject("FDPs.lblPayAmount"),strAmount.substring(1),"Payment amount");

            if(strStatus.equalsIgnoreCase("Successful")){
                dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                Date varPaidDate = dateFormat.parse(strPaidDate.replace("/","-"));
                dateFormat=new SimpleDateFormat("dd-MM-yyyy");
                newPaidDate=dateFormat.format(varPaidDate).replace("-","/");
                fetchTextToCompare(getObject("FDPs.lblPaidDate"),newPaidDate,"Paid date");
            } else {
                if(isElementDisplayed(getObject("FDPs.lblPaidDate"),4)){
                    report.updateTestLog("selectFDPsPayee", "'Paid date' is displayed for status : "+strStatus, Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("selectFDPsPayee", "'Paid date' is not displayed for status : "+strStatus, Status_CRAFT.PASS);
                }
            }
            fetchTextToCompare(getObject("FDPs.lblPayFrom"),strPayFrom.split("~")[0].trim(),"Pay from");
            fetchTextToCompare(getObject("FDPs.lblPayDueDate"),newDueDate,"Payment due date");

            if(strPaymentType.equalsIgnoreCase("Transfer between my accounts")){
                isElementDisplayedWithLog(getObject("FDPs.lblAccountNo"),
                        "Transfer btn Account no : "+getText(getObject("FDPs.lblAccountNo")),"FDP details",4);
                fetchTextToCompare(getObject("FDPs.lblAccountNo"),"XXXX"+strPayTo.split("~")[1].trim(),
                        "Account no.with last 4 digit");
            } else {
                if (strAmount.contains("€")) {
                    fetchTextToCompare(getObject("FDPs.lblPayeeBic"),strBIC,"Payee BIC");
                    fetchTextToCompare(getObject("FDPs.lblPayeeIban"),strIBAN,"Payee IBAN");
                } else {
                    fetchTextToCompare(getObject("FDPs.lblPayeeNsc"),strNSC,"Payee NSC");
                    fetchTextToCompare(getObject("FDPs.lblPayeeAccount"),strPayeeAccountNo,"Payee account");
                }
                if(!strPaymentType.equalsIgnoreCase("UK domestic")){
                    if(getText(getObject("FDPs.lblPayRefernce")).equalsIgnoreCase("-")){
                        report.updateTestLog("selectFDPsPayee", "'Payee Reference' is blank for payment type : "+strPaymentType, Status_CRAFT.FAIL);
                    } else {
                        report.updateTestLog("selectFDPsPayee", "'Payee Reference' is not blank for payment type : "+strPaymentType, Status_CRAFT.PASS);
                        fetchTextToCompare(getObject("FDPs.lblPayRefernce"),strReference,"Payee Reference");
                    }
                } else {
                    if((strReference.equalsIgnoreCase("")||strReference.equalsIgnoreCase("-") )&&
                            getText(getObject("FDPs.lblPayRefernce")).equalsIgnoreCase("-")){
                        report.updateTestLog("selectFDPsPayee", " For blank 'Payee Reference', " +
                                "'"+getText(getObject("FDPs.lblPayRefernce"))+"' is displayed for payment type :  "+strPaymentType, Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("selectFDPsPayee", " For blank 'Payee Reference', " +
                                "'"+getText(getObject("FDPs.lblPayRefernce"))+"' is displayed for payment type :  "+strPaymentType, Status_CRAFT.FAIL);
                    }
                }
            }
            if(strStatus.equalsIgnoreCase("Pending")){
                fetchTextToCompare(getObject("FDPs.btnCancelPayment"),"Cancel payment","Cancel payment button");
            }
            testFlag = true;
        }
        return testFlag;
    }

    /**
     * Function : To select future dated payee to view details for desktop
     * Created by : C966003
     * Update on    Updated by     Reason
     * 25/09/2019   C966003        Newly created
     */
    public void selectFDPsPayee_desktop()throws Exception{
        waitForPageLoaded();waitForJQueryLoad();
        String newDueDate = ""; boolean flagPayeeTest = false; String strPayToNew = "";
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

        List<WebElement> lstPayee = findElements(getObject("xpath~//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'boi-pl-14')]"));
        report.updateTestLog("selectFDPsPayee", lstPayee.size() +" count of "+ strPayToNew +" payees found", Status_CRAFT.DONE);
        for(int i=1; i<=lstPayee.size(); i++){
            if(lstPayee.size()>= 1){
                String strStatusFetched = getText(getObject("xpath~(//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'boi-pl-14')])["+i+"]/../../..")).split("\n")[0];
                String strAmountFetched = getText(getObject("xpath~(//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'boi-pl-14')])["+i+"]/../../..")).split("\n")[2];
                String strDueDate = getText(getObject("xpath~(//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'boi-pl-14')])["+i+"]/../../..")).split("\n")[3];
                if(strPayeeStatus.equalsIgnoreCase(strStatusFetched) &&
                        strAmount.contains(strAmountFetched) &&
                        newDueDate.equalsIgnoreCase(strDueDate)){
                    clickJS(getObject("xpath~(//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'boi-pl-14')])[" + i + "]"), strPayToNew);
                    if(payeeValidator_desktop(strPayFrom, strPayTo, strPayToNew, strPayeeStatus, strAmount, i)){
                        flagPayeeTest = true;
                        break;
                    }
                }
            } else {
                report.updateTestLog("selectFDPsPayee", "Payee not listed in FDPs list", Status_CRAFT.FAIL);
            }
        }
        if(!flagPayeeTest == true){
            report.updateTestLog("selectFDPsPayee", "Required payee details are not found in FDPs list", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : To validate correct payee and return boolean value for desktop
     * Created by : C966003
     * Update on    Updated by     Reason
     * 25/09/2019   C966003        Newly created
     */
    public boolean payeeValidator_desktop(String strPayFrom, String strPayTo, String strPayToNew,  String strStatus, String strAmount, int i)throws Exception{
        boolean testFlag = false; String newPaidDate = ""; String strDueDate = ""; String newDueDate = "";
        String strPaymentType = dataTable.getData("General_Data","PayeeMessage");
        String strUnsuccessReason = dataTable.getData("General_Data","UnsuccessfulReason");
        String strPaidDate = dataTable.getData("General_Data","Paid_Date");
        String strIBAN = dataTable.getData("General_Data","IBAN_Number");
        String strBIC = dataTable.getData("General_Data","BIC_Number");
        String strReference = dataTable.getData("General_Data","BillReference");
        String strNSC = dataTable.getData("General_Data","NSC_Number");
        String strPayeeAccountNo = dataTable.getData("General_Data","PayeeAccount_number");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        scriptHelper.commonData.intPayeeCount = i;
        if(strPayFrom.split("~")[0].trim().equalsIgnoreCase(strFDPsValues("Pay from", strPayToNew, i))){
            report.updateTestLog("selectFDPsPayee", "****** Required payee found ******", Status_CRAFT.PASS);
            strTextToCompare(strStatus,strFDPsValues("Status", strPayToNew, i),"Status");

            if(strStatus.equalsIgnoreCase("Unsuccessful")){
                strTextToCompare(strUnsuccessReason,strFDPsValues("Reason", strPayToNew, i),"Unsuccessful reason");
            } else {
                if(strTextToCompare(strUnsuccessReason,strFDPsValues("Reason", strPayToNew, i),"Unsuccessful reason")&&
                        !strFDPsValues("Reason", strPayToNew, i).equalsIgnoreCase("")){
                    report.updateTestLog("selectFDPsPayee", "'Unsuccessful reason' is displayed for status : "+strStatus, Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("selectFDPsPayee", "'Unsuccessful reason' is not displayed for status : "+strStatus, Status_CRAFT.PASS);
                }
            }

            if(strStatus.equalsIgnoreCase("Successful")){
                Date varPaidDate = dateFormat.parse(strPaidDate.replace("/","-"));
                dateFormat=new SimpleDateFormat("dd-MM-yyyy");
                newPaidDate=dateFormat.format(varPaidDate).replace("-","/");
                strTextToCompare(newPaidDate,strFDPsValues("Paid date", strPayToNew, i),"Paid date");
            } else {
                if(strTextToCompare(newPaidDate,strFDPsValues("Paid date", strPayToNew, i),"Paid date")&&
                        !strFDPsValues("Paid date", strPayTo, i).equalsIgnoreCase("")){
                    report.updateTestLog("selectFDPsPayee", "'Paid date' is displayed for status : "+strStatus, Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("selectFDPsPayee", "'Paid date' is not displayed for status : "+strStatus, Status_CRAFT.PASS);
                }
            }
            strTextToCompare(strPayFrom.split("~")[0],strFDPsValues("Pay from", strPayToNew, i),"Pay from");

            if(strPaymentType.equalsIgnoreCase("Transfer between my accounts")){
                strTextToCompare("XXXX"+strPayTo.split("~")[1].trim(),strFDPsValues("Account number", strPayToNew, i),"Account no.with last 4 digit");
            } else {
                if (strAmount.contains("€")) {
                    strTextToCompare(strIBAN,strFDPsValues("Payee IBAN", strPayToNew, i),"Payee IBAN");
                    strTextToCompare(strBIC,strFDPsValues("Payee BIC", strPayToNew, i),"Payee BIC");
                } else {
                    strTextToCompare(strNSC,strFDPsValues("Payee NSC", strPayToNew, i),"Payee NSC");
                    strTextToCompare(strPayeeAccountNo,strFDPsValues("Payee account", strPayToNew, i),"Payee account");
                }
                if(!strPaymentType.equalsIgnoreCase("UK domestic")){
                    if(!strTextToCompare(strReference,strFDPsValues("Reference", strPayToNew, i),"Reference")){
                        report.updateTestLog("selectFDPsPayee", "'Payee Reference' is blank for payment type : "+strPaymentType, Status_CRAFT.FAIL);
                    } else {
                        report.updateTestLog("selectFDPsPayee", "'Payee Reference' is not blank for payment type : "+strPaymentType, Status_CRAFT.PASS);
                        strTextToCompare(strReference,strFDPsValues("Reference", strPayToNew, i),"Reference");
                    }
                } else {
                    if((strReference.equalsIgnoreCase("")||strReference.equalsIgnoreCase("-") )&&
                            strFDPsValues("Reference", strPayToNew, i).trim().equalsIgnoreCase("-")){
                        report.updateTestLog("selectFDPsPayee", " For blank 'Payee Reference', " +
                                "'"+strFDPsValues("Reference", strPayToNew, i).trim()+"' is displayed for payment type :  "+strPaymentType, Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("selectFDPsPayee", " For blank 'Payee Reference', " +
                                "'"+strFDPsValues("Reference", strPayToNew, i).trim()+"' is displayed for payment type :  "+strPaymentType, Status_CRAFT.FAIL);
                    }
                }
            }

            if(strStatus.equalsIgnoreCase("Pending")){
                String strCancelPayment = getText(getObject("xpath~(//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'boi-pl-14')])["+i+"]" +
                        "/../../../../../following-sibling::div//*[contains(text(),'Cancel payment')]"));
                strTextToCompare("Cancel payment",strCancelPayment,"Cancel payment button");
            } else {
                String strCancelPayment = getText(getObject("xpath~(//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'boi-pl-14')])["+i+"]" +
                        "/../../../../../following-sibling::div//*[contains(text(),'Cancel payment')]"));
                if(strCancelPayment.equalsIgnoreCase("Cancel payment")){
                    report.updateTestLog("selectFDPsPayee", "'Cancel payment button' is displayed for status : "+strStatus, Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("selectFDPsPayee", "'Cancel payment button' is not displayed for status : "+strStatus, Status_CRAFT.PASS);
                }
            }
            testFlag = true;
        }
        return testFlag;
    }
    /**
     * Function : To return fetched values for FDPs details in desktop
     * Created by : C966003
     * Update on    Updated by     Reason
     * 26/09/2019   C966003        Newly created
     */
    public String strFDPsValues(String strLabel, String strPayToNew, int i )throws Exception{
        scrollToView(getObject("xpath~(//*[contains(text(),'"+strPayToNew+"')][contains(@class,'boi-pl-14')])["+i+"]" +
                "/../../../../../following-sibling::div//*[contains(text(),'"+strLabel+"')]/../../following-sibling::div"));
        String strValue = getText(getObject("xpath~(//*[contains(text(),'"+strPayToNew+"')][contains(@class,'boi-pl-14')])["+i+"]" +
                "/../../../../../following-sibling::div//*[contains(text(),'"+strLabel+"')]/../../following-sibling::div[@style='text-align: left; ']"));
        return strValue;
    }

    /**
     * Function : Click cancel payment button to cancel pending payment
     * Created by : C966003
     * Update on    Updated by     Reason
     * 02/09/2019   C966003        Newly created
     */
    public void cancelPayment()throws Exception{
        if(deviceType.equalsIgnoreCase("Web")){
            String strPayToNew = "";
            int i = scriptHelper.commonData.intPayeeCount;
            String strPayTo = dataTable.getData("General_Data","PayTo_Account");
            if (strPayTo.split("~")[0].length()<=18){
                strPayToNew = strPayTo.split("~")[0].trim();
            } else {
                strPayToNew = strPayTo.split("~")[0].trim().substring(0,18);
            }
            isElementDisplayedWithLog(getObject("xpath~(//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'boi-pl-14')])["+i+"]/../../../../../following-sibling::div//*[contains(text(),'Cancel payment')]"),"Cancel Payment button", "Expanded view of '"+ strPayToNew +"' payee",5);
            clickJS(getObject("xpath~(//*[contains(text(),'"+ strPayToNew +"')][contains(@class,'boi-pl-14')])["+i+"]/../../../../../following-sibling::div//*[contains(text(),'Cancel payment')]"),"Cancel Payment button for '"+ strPayToNew +"' payee");
        }else{
            isElementDisplayedWithLog(getObject("FDPs.btnCancelPayment"),
                    "Cancel payment button : "+getText(getObject("FDPs.btnCancelPayment")),"FDP details",4);
            clickJS(getObject("FDPs.btnCancelPayment"),"Cancel Payment");
        }
    }


    /**CFPUR-8991: No future dated payments message
     * Function: To verify that when there are no future dated payments then i will see a text message on the screen
     * CREATED BY: C103403
     */

    public void verifyNoFDPScreen() throws Exception{
        basicFDPsPageDetails();
        isElementDisplayedWithLog(getObject(deviceType()+"FDP.txtNoFDP"),
                "FDP Text Message : 'You have no future dated payments pending'","Future Dated Payments Page",4);

        if(dataTable.getData("General_Data", "Operation").equalsIgnoreCase("Send money")){
            isElementDisplayedWithLog(getObject(deviceType()+"FDP.btnSendMoney"),"Send money button", "Future Dated Payments Page ",3);
            click(getObject(deviceType()+"FDP.btnSendMoney"),"Send money button");
            while (isElementDisplayed(getObject("launch.spinSpinner"), 4)) {
                waitForPageLoaded();
            }
            isElementDisplayedWithLog(getObject("FDP.lblPayFrom"),"Pay from label", "Send Money Page",5);
        }
    }


    /**
     * CFPUR-5451: Cancel transfer between accounts future dated payments (ROI & NI/GB)
     * Function: To test the functionality of Cancel Payment in Future Dated Payment
     * Created by: C103403      Created on: 27/09/2019
     */

    public void verifyCancelPaymnent() throws Exception {

        if (isElementDisplayed(getObject(deviceType()+"FDP.popupCancel"), 5)) {
            if (!dataTable.getData("General_Data", "Operation").equals("")) {
                String strOperationOnPutonHold = dataTable.getData("General_Data", "Operation");
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                switch (strOperationOnPutonHold.toUpperCase()) {
                    case "CANCEL PAYMENT":
                        // Click on Cancel Payment button
                        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("FDP.btnCancelPayment")));
                        click(getObject("FDP.btnCancelPayment"), "'Yes, cancel payment' button");
                        while (isElementDisplayed(getObject("xpath~//*[@class='spinner']"), 1)) {
                            waitForPageLoaded();
                        }
                        // Verify success message on Future Dated Payments screen
                        //Amended Success Message Content CS-8202 (Saurabh)
                        if (isElementDisplayed(getObject(deviceType() + "FDP.txtSuccessMsg"), 10)) {
                             report.updateTestLog("verifyCancelPaymnent", "Success: your future dated payment has been cancelled. It may take a few minutes for your payment list to update.' is appearing on the screen ", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("verifyCancelPaymnent", "Success Message 'Success: your future dated payment has been cancelled. It may take a few minutes for your payment list to update.' is not appearing on the screen", Status_CRAFT.FAIL);
                        }
                        //Click on Close Icon
                        if (isElementDisplayed(getObject(deviceType() + "FDP.iconCloseSuccess"), 10)) {
                            report.updateTestLog("verifyCancelPaymnent", "Success Close Icon is appearing on the screen ", Status_CRAFT.PASS);
                            click(getObject(deviceType() + "FDP.iconCloseSuccess"), "Success Close Icon");
                        } else {
                            report.updateTestLog("verifyCancelPaymnent", "Success Close Icon is not appearing on the screen", Status_CRAFT.FAIL);
                        }

                        break;
                    case "GO BACK":
                        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("FDP.btnGoBack")));
                        Thread.sleep(2000);
                        clickJS(getObject("FDP.btnGoBack"), "'Go back' Button");
                        if (isElementDisplayed(getObject(deviceType()+"FDP.hdrStatus"), 5)) {
                            report.updateTestLog("verifyCancelPaymnent", "User navigated to Future Dated Payments Page upon clicking 'Go back' Button", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("verifyCancelPaymnent", "User does not navigated to Future Dated Payments Page upon clicking 'Go back' Button", Status_CRAFT.FAIL);
                        }
                        break;
                    case "CLOSE":
                        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("FDP.iconClose")));
                        Thread.sleep(2000);
                        clickJS(getObject("FDP.iconClose"), "'Close Icon'");
                        if (isElementDisplayed(getObject(deviceType()+"FDP.hdrStatus"), 5)) {
                            report.updateTestLog("verifyCancelPaymnent", "User navigated to Future Dated Payments Page upon clicking 'Go back' Button", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("verifyCancelPaymnent", "User does not navigated to Future Dated Payments Page upon clicking 'Go back' Button", Status_CRAFT.FAIL);
                        }

                        break;

                    default:
                        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("FDP.iconClose")));
                        Thread.sleep(2000);
                        clickJS(getObject("FDP.iconClose"), "'Close Icon'");
                        report.updateTestLog("verifyCancelPaymnent", "Pop Up is closed", Status_CRAFT.PASS);
                }
            }
            else {
                report.updateTestLog("verifyCancelPaymnent", "Operation is empty in datatable, unable to operate. Please provide the valid operation on Future Dated Payments Page, CANCEL PAYMENT, GO BACK or CLOSE", Status_CRAFT.DONE);
            }
        }
        else {
            report.updateTestLog("verifyCancelPaymnent", "Cancel future dated payment Pop up is unavailable on Screen", Status_CRAFT.FAIL);
        }

    }

    /**
     * CFPUR-5451: Cancel transfer between accounts future dated payments (ROI & NI/GB)
     * Function: To test the details appearing on Cancel Payment popup in Future Dated Payment Page
     * Created by: C103403      Created on: 27/09/2019
     */

    public void verifyCancelPaymnetpopupdetails() throws Exception{
        isElementDisplayedWithLog(getObject("FDP.hdrtxtCancelPopup"),"Header text '" + getText(getObject("FDP.hdrtxtCancelPopup")) +"' ","Cancel future dated payment",5);
        isElementDisplayedWithLog(getObject("FDP.iconClose"),"Close Icon","Cancel future dated payment",2);
        isElementDisplayedWithLog(getObject("FDP.txtWarnCancelPopup"),"Warning text '" + getText(getObject("FDP.txtWarnCancelPopup")) +"' ","Cancel future dated payment",2);
        isElementDisplayedWithLog(getObject("FDP.btnCancelPayment"),"Yes, cancel payment button","Cancel future dated payment",2);
        isElementDisplayedWithLog(getObject("FDP.btnGoBack"),"Go back","Cancel future dated payment",2);
    }


    /*
   Function: To use both the function when using the same column in datasheet for Transfer Date
    */
    public void fdpPayFromPayToDetails()throws Exception{
        if(!scriptHelper.commonData.transferFlag==true){
            PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
            payments.selectPayfromSendMoney();
            payments.selectPayToSendMoney();
            scriptHelper.commonData.transferFlag=true;
        }else{
            selectFDPsPayee();
        }
    }









}

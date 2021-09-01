package businesscomponents;


import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Created by C100493 on 18/09/2018.
 */
public class AccountPolicyPage extends WebDriverHelper {

    public String Referencenumber = "";
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public static String strRandomPolicyNo;
    public AccountPolicyPage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }


    public void verifyAddAccountPolicies() throws Exception {

        String strPolicyName = dataTable.getData("General_Data", "PolicyName");
        String strPolicyNumber = dataTable.getData("General_Data", "PolicyNumber");
        String strEmail = dataTable.getData("General_Data", "Email");
        String strPhone = dataTable.getData("General_Data", "Phone");
        String strNewEmail = dataTable.getData("General_Data", "NewEmail");
        String strContactPreference = dataTable.getData("General_Data", "ContactPreference");

        //Add Account Policy - Add Page
//        click(getObject("home.lnkServiceDesk"), "ServiceDesk navigation from Header");
        String myobj;
        if (deviceType == "Web") {
            myobj = "xpath~//span[text()='SERVICES']";
        } else {
            myobj = "xpath~//div[text()='Services']";
        }
        String strValCaptured = "";
        JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();",
                driver.findElement(getObject(myobj)));
        if (isElementDisplayed(getObject(myobj), 5))
        {
            clickJS(getObject(myobj), " Clicked at Service Desk Icon on Home Page");
            clickJS(getObject("AddPolicy.lnkAddAccount"), "Add account policy Menu on ServiceDesk Page");
            verifySelectAddAccPolicy();
            sendKeys(getObject("AddPolicy.txtPolicyNo"), strPolicyNumber, "Policy Number");
            sendKeys(getObject("AddPolicy.txtEmail"), strEmail, "Email ID");

            if (strContactPreference.equalsIgnoreCase("Email")) {
                clickJS(getObject("AddPolicy.btnEmailOption"), "Email Option Button");
            } else if (strContactPreference.equalsIgnoreCase("Phone")) {
                clickJS(getObject("AddPolicy.btnPhoneOption"), "Phone As A Preference");
                sendKeys(getObject("AddPolicy.txtPhoneNo"), strPhone, "Phone Number Entered");
            }
            click(getObject("AddPolicy.Continue"), "Continue (Enter Details Page)");
            click(getObject("AddPolicy.btnGoBack"), "Go Back Button Checked");
            sendKeys(getObject("AddPolicy.txtEmail"), strNewEmail, "Email ID Edited");
            click(getObject("AddPolicy.Continue"), "Continue Button Clicked on Enter Details Page");
            verifyAddAccountPolicyReviewPage();
            verifyAddAccPolicyConfirmationPage();
            click(getObject("AddPolicy.btnBackToHome"), "User journey Add Account Policy 1 Success. Back to Home Button Clicked");
        }
    }
    public void verifyErrorTextforInvalidAccFunc() throws Exception {
        String strIterationFlalgPolicy = dataTable.getData("General_Data","IterationFlagforPolicy");
        if (strIterationFlalgPolicy.equalsIgnoreCase("Yes")) {
            clickJS(getObject("AddPolicy.Continue"), "Continue (Enter Details Page)");
        }
//        String strErrEmail = getText(getObject("AddPolicy.errEmail"));
        String strErrContact = getText(getObject("AddPolicy.errContact"));
        String strErrorAccntNum = getText(getObject("AddPolicy.errBlankAccountNum"));

        String strPolicyType = dataTable.getData("General_Data","PolicyType");
//        String strErrAccNo = getText(getObject("AddPolicy.errAccountNo"));
//        String strErrAddLine1 = getText(getObject("AddPolicy.errAddressLine1"));
//        String strErrAddLine2 = getText(getObject("AddPolicy.errAddressLine2"));
//        String strErrDOB = getText(getObject("AddPolicy.errDateOfBirth"));

        if (strPolicyType.equalsIgnoreCase("Current Account")||
                strPolicyType.equalsIgnoreCase("Demand Deposit Account")||
                strPolicyType.equalsIgnoreCase("Term Saving, Notice or Regular Savings Account")||
                strPolicyType.equalsIgnoreCase("Loan Account")||
                strPolicyType.equalsIgnoreCase("Bank of Ireland Mortgage")){

            if (strIterationFlalgPolicy.equalsIgnoreCase("Yes")) {
                if (isElementDisplayed(getObject("AddPolicy.errBlankAccountNum"), 5)) {
                    report.updateTestLog("verifyErrorTextforInvalidAccFunc", strErrorAccntNum + "' error message is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorTextforInvalidAccFunc", strErrorAccntNum + "' error message is displayed", Status_CRAFT.FAIL);
                }
            }
            if (isElementDisplayed(getObject("AddPolicy.errContact"),3)){
                report.updateTestLog("verifyErrorTextforInvalidAccFunc", strErrContact +"' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidAccFunc", strErrContact +"' error message is displayed", Status_CRAFT.FAIL);
            }
//            if (isElementDisplayed(getObject("AddPolicy.errAddressLine2"),3)){
//                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAddLine2 +"' error message is displayed", Status_CRAFT.PASS);
//            } else {
//                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAddLine2 +"' error message is displayed", Status_CRAFT.FAIL);
//            }
//            if (isElementDisplayed(getObject("AddPolicy.errDateOfBirth"), 3)) {
//                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrDOB + "' error message is displayed", Status_CRAFT.PASS);
//            } else {
//                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrDOB + "' error message is not displayed", Status_CRAFT.FAIL);
//            }
            //****entered invalid account number  to validate error message
            String strAccntNum = "521458";
            if(isElementDisplayed(getObject("AddPolicy.edtAccountNum"),4)){
                sendKeys(getObject("AddPolicy.edtAccountNum"),strAccntNum,"Account Number");
                click(getObject("AddPolicy.Continue"), "Continue (Enter Details Page)");
                String strErrorInvAccntNum = getText(getObject("AddPolicy.errInvAccountNum"));
                if (isElementDisplayed(getObject("AddPolicy.errInvAccountNum"),5)){
                    report.updateTestLog("verifyErrorTextforInvalidAccFunc", strErrorInvAccntNum + "' error message is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorTextforInvalidAccFunc", strErrorInvAccntNum + "' error message is displayed", Status_CRAFT.FAIL);
                }
            }
        }



    }
    public void verifyErrorTextforInvalidPolicyFunc() throws Exception {
        String strIterationFlalgPolicy = dataTable.getData("General_Data", "IterationFlagforPolicy");
        if (strIterationFlalgPolicy.equalsIgnoreCase("Yes")) {
            sendKeys(getObject("AddPolicy.edtPolicyNum"), "", "Policy Number");
            sendKeys(getObject("AddPolicy.edtemail"), "", "Email");
            if (isElementDisplayed(getObject("AddPolicy.Continue"), 5)) {
                clickJS(getObject("AddPolicy.Continue"), "Continue (Enter Details Page)");
            }else{
                report.updateTestLog("verifyErrorTextforInvalidPolicyFunc", "Continue button not found on Enter Policy Details page", Status_CRAFT.FAIL);
            }
        }
        String strErrPolicy = getText(getObject("AddPolicy.errPolicy"));
        String strErrEmail = getText(getObject("AddPolicy.errEmail"));
        String strErrContact = getText(getObject("AddPolicy.errContact"));
        String strErrorAccntNum = getText(getObject("AddPolicy.errBlankAccountNum"));
        String strPolicyType = dataTable.getData("General_Data", "PolicyType");
        String strErrAccNo = getText(getObject("AddPolicy.errAccountNo"));
        String strErrAddLine1 = getText(getObject("AddPolicy.errAddressLine1"));
        String strErrAddLine2 = getText(getObject("AddPolicy.errAddressLine2"));
        String strErrDOB = getText(getObject("AddPolicy.errDateOfBirth"));
        String strErrCreditcard = getText(getObject("AddPolicy.errCreditCard"));

        if (strPolicyType.equalsIgnoreCase("Current Account") ||
                strPolicyType.equalsIgnoreCase("Demand Deposit Account") ||
                strPolicyType.equalsIgnoreCase("Term Saving, Notice or Regular Savings Account") ||
                strPolicyType.equalsIgnoreCase("Loan Account") ||
                strPolicyType.equalsIgnoreCase("Bank of Ireland Mortgage")) {


            if (isElementDisplayed(getObject("AddPolicy.errAccountNo"), 3)) {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAccNo + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAccNo + "' error message is displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("AddPolicy.errAddressLine1"), 3)) {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAddLine1 + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAddLine1 + "' error message is displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("AddPolicy.errAddressLine2"), 3)) {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAddLine2 + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAddLine2 + "' error message is displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("AddPolicy.errDateOfBirth"), 3)) {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrDOB + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrDOB + "' error message is not displayed", Status_CRAFT.FAIL);
            }
            //****entered invalid account number  to validate error message
            String strAccntNum = "521458";
            if (isElementDisplayed(getObject("AddPolicy.errAccountNo"), 4)) {
                sendKeys(getObject("AddPolicy.edtAccountNum"), strAccntNum, "Account Number");
                click(getObject("AddPolicy.Continue"), "Continue (Enter Details Page)");
                waitForJQueryLoad();waitForPageLoaded();
                String strErrInvalidAccntnum = getText(getObject("AddPolicy.errInvAccountNum"));
                if (isElementDisplayed(getObject("AddPolicy.errInvAccountNum"), 5)) {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidAccntnum + "' error message is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidAccntnum + "' error message is displayed", Status_CRAFT.FAIL);
                }
            }
        } else if (strPolicyType.equalsIgnoreCase("Bank of Ireland Life Investment or Savings Plan") ||
                strPolicyType.equalsIgnoreCase("Bank of Ireland Life Pension Policy") ||
                strPolicyType.equalsIgnoreCase("Bank of Ireland Life Protection Policy")) {

            if (strIterationFlalgPolicy.equalsIgnoreCase("Yes")) {

                if (isElementDisplayed(getObject("AddPolicy.errPolicy"), 5)) {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrPolicy + "' error message is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrPolicy + "' error message is not displayed", Status_CRAFT.FAIL);
                }

                if (isElementDisplayed(getObject("AddPolicy.errEmail"), 5)) {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrEmail + "' error message is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrEmail + "' error message is not displayed", Status_CRAFT.FAIL);
                }

                if (isElementDisplayed(getObject("AddPolicy.errContact"), 5)) {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrContact + "' error message is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrContact + "' error message is not displayed", Status_CRAFT.FAIL);
                }
            }
        }
        //****entered invalid account number  to validate error message
        String strPolNum = dataTable.getData("General_Data", "InvPolicyNumber");
        String strEmail = dataTable.getData("General_Data", "InvEmail");
        if (isElementDisplayed(getObject("AddPolicy.edtPolicyNum"), 4)) {
            sendKeys(getObject("AddPolicy.edtPolicyNum"), strPolNum, "Policy Number");
            sendKeys(getObject("AddPolicy.edtemail"), strEmail, "email ID");
            clickJS(getObject("AddPolicy.Continue"), "Continue (Enter Details Page)");
            waitForJQueryLoad();waitForPageLoaded();
            String strErrInvalidPolicynum = getText(getObject("AddPolicy.errInvPolicyNum"));
            String strErrInvalidEmail = getText(getObject("AddPolicy.errInvEmail"));
            if (isElementDisplayed(getObject("AddPolicy.errInvPolicyNum"), 5)) {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidPolicynum + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidPolicynum + "' error message is not displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("AddPolicy.errInvEmail"), 1)) {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidPolicynum + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidEmail + "' error message is not displayed", Status_CRAFT.FAIL);
            }
//        }

        } else if (strPolicyType.equalsIgnoreCase("Credit Card")){
            if(isElementDisplayed(getObject("AddPolicy.txtCreditCardNo"),4)){
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrCreditcard + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrCreditcard + "' error message is not displayed", Status_CRAFT.FAIL);
            }
            //****entered invalid Credit Card number  to validate error message
            String strCardNum = "521458";
            if(isElementDisplayed(getObject("AddPolicy.errCreditCard"),4)){
                sendKeys(getObject("AddPolicy.txtCreditCardNo"),strCardNum,"Credit Card Number");
                click(getObject("AddPolicy.Continue"), "Continue (Enter Details Page)");
                String strErrInvalidCardnum = getText(getObject("AddPolicy.err16digtCreditCard"));
                if (isElementDisplayed(getObject("AddPolicy.err16digtCreditCard"),5)){
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidCardnum + "' error message is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidCardnum + "' error message is displayed", Status_CRAFT.FAIL);
                }
            }
        }
    }

    public void verifyInvalidPolicyFunc() throws Exception {
        String strIterationFlalgPolicy = dataTable.getData("General_Data","IterationFlagforPolicy");
        if (strIterationFlalgPolicy.equalsIgnoreCase("Yes")) {
            if(isElementDisplayed(getObject("AddPolicy.Continue"),5)){
                clickJS(getObject("AddPolicy.Continue"), "Continue (Enter Details Page)");

            }
        }
        String strErrPolicy = getText(getObject("AddPolicy.errPolicy"));
        String strErrEmail = getText(getObject("AddPolicy.errEmail"));
        String strErrContact = getText(getObject("AddPolicy.errContact"));
        String strErrorAccntNum = getText(getObject("AddPolicy.errBlankAccountNum"));
        String strPolicyType = dataTable.getData("General_Data","PolicyType");
        String strErrAccNo = getText(getObject("AddPolicy.errAccountNo"));
        String strErrAddLine1 = getText(getObject("AddPolicy.errAddressLine1"));
        String strErrAddLine2 = getText(getObject("AddPolicy.errAddressLine2"));
        String strErrDOB = getText(getObject("AddPolicy.errDateOfBirth"));
        String strErrCreditcard = getText(getObject("AddPolicy.errCreditCard"));

        if (strPolicyType.equalsIgnoreCase("Current Account")||
                strPolicyType.equalsIgnoreCase("Demand Deposit Account")||
                strPolicyType.equalsIgnoreCase("Term Saving, Notice or Regular Savings Account")||
                strPolicyType.equalsIgnoreCase("Loan Account")||
                strPolicyType.equalsIgnoreCase("Bank of Ireland Mortgage")){


            if (isElementDisplayed(getObject("AddPolicy.errAccountNo"),3)){
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAccNo +"' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAccNo +"' error message is displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("AddPolicy.errAddressLine1"),3)){
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAddLine1 +"' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAddLine1 +"' error message is displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("AddPolicy.errAddressLine2"),3)){
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAddLine2 +"' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrAddLine2 +"' error message is displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("AddPolicy.errDateOfBirth"), 3)) {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrDOB + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrDOB + "' error message is not displayed", Status_CRAFT.FAIL);
            }
            //****entered invalid account number  to validate error message
            String strAccntNum = "521458";
            if(isElementDisplayed(getObject("AddPolicy.errAccountNo"),4)){
                sendKeys(getObject("AddPolicy.edtAccountNum"),strAccntNum,"Account Number");
                click(getObject("AddPolicy.Continue"), "Continue (Enter Details Page)");
                String strErrInvalidAccntnum = getText(getObject("AddPolicy.errInvAccountNum"));
                if (isElementDisplayed(getObject("AddPolicy.errInvAccountNum"),5)){
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidAccntnum + "' error message is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidAccntnum + "' error message is displayed", Status_CRAFT.FAIL);
                }
            }
        } else if (strPolicyType.equalsIgnoreCase("Bank of Ireland Life Investment or Savings Plan")||
                strPolicyType.equalsIgnoreCase("Bank of Ireland Life Pension Policy")||
                strPolicyType.equalsIgnoreCase("Bank of Ireland Life Protection Policy")){

            if (strIterationFlalgPolicy.equalsIgnoreCase("Yes")) {

                if (isElementDisplayed(getObject("AddPolicy.errPolicy"), 5)) {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrPolicy + "' error message is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrPolicy + "' error message is not displayed", Status_CRAFT.FAIL);
                }
            }
            if (isElementDisplayed(getObject("AddPolicy.errEmail"), 5)) {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrEmail + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrEmail + "' error message is not displayed", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("AddPolicy.errContact"), 5)) {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrContact + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrContact + "' error message is not displayed", Status_CRAFT.FAIL);
            }

            //****entered invalid account number  to validate error message
            String strPolNum = dataTable.getData("General_Data", "InvPolicyNumber");
            if(isElementDisplayed(getObject("AddPolicy.edtPolicyNum"),4)){
                sendKeys(getObject("AddPolicy.edtPolicyNum"),strPolNum,"Policy Number");
                sendKeys(getObject("AddPolicy.edtemail"),"","email ID");
                clickJS(getObject("AddPolicy.Continue"), "Continue (Enter Details Page)");
                Thread.sleep(5000);
                String strErrInvalidPolicynum = getText(getObject("AddPolicy.errInvPolicyNum"));
                if (isElementDisplayed(getObject("AddPolicy.errInvPolicyNum"),10)){
                    Thread.sleep(1000);
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidPolicynum, Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidPolicynum + "' error message is displayed", Status_CRAFT.FAIL);
                }
            }

        } else if (strPolicyType.equalsIgnoreCase("Credit Card")){
            if(isElementDisplayed(getObject("AddPolicy.txtCreditCardNo"),4)){
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrCreditcard + "' error message is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrCreditcard + "' error message is not displayed", Status_CRAFT.FAIL);
            }
            //****entered invalid Credit Card number  to validate error message
            String strCardNum = "521458";
            if(isElementDisplayed(getObject("AddPolicy.errCreditCard"),4)){
                sendKeys(getObject("AddPolicy.txtCreditCardNo"),strCardNum,"Credit Card Number");
                click(getObject("AddPolicy.Continue"), "Continue (Enter Details Page)");
                String strErrInvalidCardnum = getText(getObject("AddPolicy.err16digtCreditCard"));
                if (isElementDisplayed(getObject("AddPolicy.err16digtCreditCard"),5)){
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidCardnum + "' error message is displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyErrorTextforInvalidPolicy", strErrInvalidCardnum + "' error message is displayed", Status_CRAFT.FAIL);
                }
            }
        }
    }

    public void verifyAddAccountPolicyReviewPage() throws Exception {
        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String strOperation = dataTable.getData("General_Data", "Operation");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            if(strFieldName.equals("Policy number")&& strFieldvalue.equals("TAKEFROMCODE")){
                if(!"".equals(scriptHelper.commonData.duplicatePolicyNumber)){
                    strFieldvalue = scriptHelper.commonData.duplicatePolicyNumber;
                }else{
                    strFieldvalue = strRandomPolicyNo;
                }
            }
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {

            if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"),5)&& isElementDisplayed(getObject("xpath~//*[text()='" + obj.getValue() + "']"), 5)) {
                report.updateTestLog("verifyAddAccountPolicyReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccountPolicyReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
            }
        }
        if (isElementDisplayed(getObject("xpath~//a[@role='button']/span[text()='Go back']"), 1)) {
            report.updateTestLog("verifyReviewPageCreditCard", "'Go back' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyReviewPageCreditCard", "'Go back' button is NOT displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("xpath~//a[@role='button']/span[text()='Confirm']"), 1)) {
            report.updateTestLog("verifyReviewPageCreditCard", "'Confirm' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyReviewPageCreditCard", "'Confirm' button is NOT displayed", Status_CRAFT.FAIL);
        }
        clickJS(getObject("xpath~//a[@role='button']/span[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
        waitForJQueryLoad();waitForPageLoaded();
    }

    public void verifyAddAccPolicyConfirmationPage() throws Exception {
        String strExpectedMsg = dataTable.getData("General_Data", "VerifyContent");
        if (isElementDisplayed(getObject("AddPolicy.imgSuccess"), 5)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Success Image is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Success Image is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPolicy.lblRequestSent"), 5)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Request Sent' text is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Request Sent' text  is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPolicy.lblThankYou"), 5)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Thank You' text is Displayed on Confirmation Page", Status_CRAFT.FAIL);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Thank You' text is NOT Displayed on Confirmation Page", Status_CRAFT.PASS);
        }
        String strActualMsg = getText(getObject("AddPolicy.lblWorkingDays"));
        if (strActualMsg.equals(strExpectedMsg)) {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Information message 'Please allow up to 5 working days for us to process your request.' is Displayed on Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Information message 'Please allow up to 5 working days for us to process your request.' NOT is Displayed on Confirmation Page", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("AddPolicy.lblTime"), 5)) {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Time' is Displayed on Confirmation Page", Status_CRAFT.PASS);

            } else {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Time' is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("AddPolicy.btnBackToHome"), 5)) {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Back To Home' button is Displayed on Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Back To Home' button is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
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



    public void messageclickAndDataValidation()throws Exception
        {
            boolean blnClicked;
            String  s1 = this.Referencenumber;

            String  refMsg=null;


            //Referencenumber = getText(getObject("xpath~//div[contains(text(),'Reference: ')]"),10);
            String FinalRef[] = s1.split(":");
            String FinalRef1 = FinalRef[1].trim();
            //TimeofRequest = getText(getObject("xpath~//div[contains(text(),'Time of request: ')]"),5);
           // scrollToView(By.xpath("//span[text()='SERVICES']"));
            ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
            clickMessage();
            clickSent();
            clickShowmorecomplete();
            List<WebElement> Sentmailslist = driver.findElements(By.xpath("//span[contains(@id,'C5__QUE_781A38DFD311C2FD268777_R')]"));
            for(int i=0; i<Sentmailslist.size();i++){
                String text = Sentmailslist.get(i).getText();
                if (text.equals(FinalRef1)){
                    ///////////////

                   // try {

                        //WebElement element = driver.findElement(locator);
                        JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                        executor.executeScript("arguments[0].click();", Sentmailslist.get(i));
                  /*  } catch (UnreachableBrowserException e) {
                        e.printStackTrace();
                    } catch (StaleElementReferenceException e) {
                        e.printStackTrace();
                    }*/
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
    public void sentMsgDetailValidation(String strSectionElement) throws Exception{

        String  s1 = this.Referencenumber;
        String FinalRef[] = s1.split(" ");
        String FinalRef1 = FinalRef[1].trim();



        String ReferencenumberOnScreen = getText(getObject("xpath~//div[@class='ecDIB  boi-tg__font--semibold boi-tg__size--default boi-mr-10 boi-tg__mod--word-break--break-word  ']")).substring(4,18);
        if(FinalRef1.equals(ReferencenumberOnScreen)){
            report.updateTestLog( "VerifySentmessagedetail", "Reference number checked", Status_CRAFT.PASS);
        }
        else
        {
            report.updateTestLog( "VerifySentmessagedetail", "Reference number checked", Status_CRAFT.FAIL);
        }

        ////div[@class='ecDIB  boi-tg__font--semibold boi-tg__size--default boi-mr-10 boi-tg__mod--word-break--break-word  ']



        String[] arrValidation = dataTable.getData("General_Data", "VerifyText").split(";");
        String dataSectionUI = "";
        String strValidateHead = "";
        String strValidateData = "";

        if (isElementDisplayed(getObject(strSectionElement), 5)) {
            dataSectionUI = getText(getObject(strSectionElement)).toUpperCase();
            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                strValidateHead = arrValidation[intValidate].split("::")[0];
                strValidateData = arrValidation[intValidate].split("::")[1];
                if (dataSectionUI.contains(strValidateData.toUpperCase())) {
                    report.updateTestLog("verifyElementDetails", strValidateHead + " value '" + strValidateData + "' is displayed correctly ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyElementDetails", strValidateHead + " value '" + strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("verifyElementDetails", "Section element '" + strSectionElement + "' is not displayed on Screen", Status_CRAFT.FAIL); }
    }




    public void verifyAddAccPolicyConfirmationPageNew() throws Exception{

            Referencenumber = getText(getObject("xpath~//*[contains(text(),'Reference:')]"),5);

            if (isElementDisplayed(getObject("AddPolicy.imgSuccess"), 5)) {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Success Image is Displayed on Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Success Image is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("AddPolicy.lblRequestSent"), 5)) {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Request Sent' text is Displayed on Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Request Sent' text  is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("AddPolicy.lblThankYou"), 5)) {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Thank You' text is Displayed on Confirmation Page", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Thank You' text is NOT Displayed on Confirmation Page", Status_CRAFT.PASS);
            }

            if (isElementDisplayed(getObject("AddPolicy.lblWorkingDays"), 5)) {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Information message 'Please allow up to 5 working days for us to process your request.\nDetails of this request are saved in your sent messages in Services.' is Displayed on Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Information message 'Please allow up to 5 working days for us to process your request.' NOT is Displayed on Confirmation Page", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject("AddPolicy.lblTime"), 5)) {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Time' is Displayed on Confirmation Page", Status_CRAFT.PASS);

            } else {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Time' is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("AddPolicy.btnBackToHome"), 5)) {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Back To Home' button is Displayed on Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Back To Home' button is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("AddPolicy.btnBackToHome"), 5)) {
                clickJS(getObject("AddPolicy.btnBackToHome"),"BackButton");
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Back To Home' button is Clicked on Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Back To Home' button is NOT clicked on Confirmation Page", Status_CRAFT.FAIL);
            }




        }

        public void navigateToMessage() throws Exception{
            ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
            if  (isElementDisplayed(getObject("services.MessageNavigation"),5)){
                clickJS(getObject("services.MessageNavigation"),"Click on Manage statements option");

            }
            ////button[@class='boi-lozenge-tab__header boi-display-block-override roleTab boi-remove-title']

        }





    public void verifySelectAddAccPolicy() throws Exception {

        if (isElementDisplayed(getObject("AddPolicy.lstSelectAccount"), 5)) {
            String policyName = dataTable.getData("General_Data", "PolicyType");
            Thread.sleep(1000);
            if (getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")).equalsIgnoreCase("please select")) {
                report.updateTestLog("verifySelectAddAccPolicy", "Directional text is correctly displayed on the Select Account dropdown", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifySelectAddAccPolicy", "Directional text is not as expected for Select Account, Expected: 'please select' :: Actual:'" + getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")) + "'", Status_CRAFT.FAIL);
            }
            click(getObject("xpath~//select[@id='C5__C1__QUE_618BBB1BD78DAE8E1975']/../following-sibling::div/ul/li[contains(.,'" + policyName + "')]"), "Selected policy '" + policyName + "'");
            Thread.sleep(1000);
        } else {
            report.updateTestLog("verifySelectAddAccPolicy", "Drop Down to Add account/policy Not Present", Status_CRAFT.FAIL);
        }
    }


    public void verifyAddAccPolicyDepositLinkForm() throws Exception {
        String strPolicyName = dataTable.getData("General_Data", "PolicyType");

        if (isElementDisplayed(getObject("AddPolicy.hdrAccountToView"), 5)) {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "header 'Add your account to view it online' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "header 'Add your account to view it online' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }

        //Verify Account with pretext selection
        if (getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")).equalsIgnoreCase(strPolicyName)) {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Pretext is correctly present select Account dropdown Expected:" + strPolicyName, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Pretext is NOT correctly Present Select Account dropdown, Expected: '" + strPolicyName + "':: Actual:'" + getText(getObject("AddPolicy.lblSelectAccDirectionaltxt")) + "'", Status_CRAFT.FAIL);
        }
        //Verify 'How to Contact' Label
        if (isElementDisplayed(getObject("AddPolicy.lblHowToContact"), 5)) {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Label 'How will we contact you if we need to clarify anything?*' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Label 'How will we contact you if we need to clarify anything?*' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }
        //Verify 'Phone' Label
        if (isElementDisplayed(getObject("AddPolicy.lblRadioOptionPhone"), 5)) {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Label 'Phone' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Label 'Phone' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }
        //Verify 'Email' Label
        if (isElementDisplayed(getObject("AddPolicy.lblRadioOptionEmail"), 5)) {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Label 'Email' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Label 'Email' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }
        //Verify Phone text Box populates after selecting 'Phone' option
        click(getObject("AddPolicy.lblRadioOptionPhone"), "Selected Phone Option");
        if (isElementDisplayed(getObject("AddPolicy.txtboxPhone"), 5) && isElementDisplayed(getObject("Addpolicy.lblPhone"), 5)) {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Textbox 'Phone' OR Lable 'Phone*' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Textbox 'Phone' OR Lable 'phone*'is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }
        //Verify Email text Box populates after selecting 'Email' option
        click(getObject("AddPolicy.lblRadioOptionEmail"), "Selected Email Option");
        if (isElementDisplayed(getObject("AddPolicy.txtboxEmail"), 5) && isElementDisplayed(getObject("AddPolicy.lblEmail"), 5)) {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Textbox 'Email' OR Lable 'Email*' is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "Textbox 'Email' OR Lable 'Email*' is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("AddPolicy.Continue"), 5)) {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "'Continue' Button is present on Add Account Policy page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyDepositLinkForm", "'Continue' Button is NOT present on Add Account Policy page", Status_CRAFT.FAIL);
        }

    }




//    public void EnterAddAccPolicyDepositlinkDetails() throws Exception {
//        String strPolicyName = dataTable.getData("General_Data", "PolicyType");
//        String strAccount = dataTable.getData("General_Data", "AccountNumber");
//        String strEmail = dataTable.getData("General_Data", "Email");
//        String strPhone = dataTable.getData("General_Data", "Phone");
//        String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
//        String strIterationFlalgPolicy = dataTable.getData("General_Data","IterationFlagforPolicy");
//        if (strIterationFlalgPolicy.equalsIgnoreCase("DuplicatePolicy")) {
//          strRandomPolicyNo = dataTable.getData("General_Data", "AccountNumber");
//        }
//        if (strIterationFlalgPolicy.equalsIgnoreCase("Yes")) {
//             strAccount = dataTable.getData("General_Data", "AccountNumber");
//        }
//        else {
//            Random rand = new Random();
//            int randomNum = 100000 + rand.nextInt((999999 - 100000) + 1);
//            strRandomPolicyNo = "IP" + String.valueOf(randomNum) + "B";
//        }
//        waitForElementPresent(getObject("AddPolicy.txtPolicyNum"), 10);
//        if (strIterationFlalgPolicy.equalsIgnoreCase("Yes")) {
//            //AddPolicy.edtAccountNum>>previously used object
//            sendKeys(getObject("AddPolicy.txtAccountNum"),strAccount,"Account Number");
//        }
//        else {
//            sendKeys(getObject("AddPolicy.txtPolicyNum"), strRandomPolicyNo);
//        }
//        //sendKeys(getObject("AddPolicy.txtboxEmail"), strEmail);
//        //need to change the code for clicking on the obejct as there are 2 objects for the same property
//        if (strContactPreference.equalsIgnoreCase("Email")) {
//            clickJS(getObject("AddPolicy.lblRadioOptionEmail"), "Email Option Radio Button");
//            waitForElementPresent(getObject("AddPolicy.txtboxEmail"), 10);
//            sendKeys(getObject("AddPolicy.txtboxEmail"), strEmail, "Email Entered");
//        } else if (strContactPreference.equalsIgnoreCase("Phone")) {
//            clickJS(getObject("AddPolicy.lblRadioOptionPhone"), "Phone As A Preference");
//            waitForElementPresent(getObject("AddPolicy.txtPhoneNo"), 10);
//            sendKeys(getObject("AddPolicy.txtPhoneNo"), strPhone, "Phone Number Entered");
//        }
//        clickJS(getObject("AddPolicy.Continue"), "Clicked Continue Button");
//    }

    public void EnterAddAccPolicyDepositlinkDetails() throws Exception {

        String strPolicyName = dataTable.getData("General_Data", "PolicyType");
        String strEmail = dataTable.getData("General_Data", "Email");
        String strPhone = dataTable.getData("General_Data", "Phone");
        String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
        String strIterationFlalgPolicy = dataTable.getData("General_Data","IterationFlagforPolicy");
        if (strIterationFlalgPolicy.equalsIgnoreCase("DuplicatePolicy")) {
//          strRandomPolicyNo = dataTable.getData("General_Data", "AccountNumber");
            strRandomPolicyNo = scriptHelper.commonData.duplicatePolicyNumber;
        }
        else {
            Random rand = new Random();
            int randomNum = 100000;
            randomNum = 100000 + rand.nextInt((999999 - 100000) + 1);
            strRandomPolicyNo = "IP" + String.valueOf(randomNum) + "B";
            scriptHelper.commonData.duplicatePolicyNumber = strRandomPolicyNo;
        }
        waitForElementPresent(getObject("AddPolicy.txtPolicyNum"), 10);
        Thread.sleep(1000);
        click(getObject("AddPolicy.txtPolicyNum"));
        report.updateTestLog("EnterAddAccPolicyDepositlinkDetails", " Screenshot", Status_CRAFT.SCREENSHOT);
        waitForPageLoaded();
        sendKeys(getObject("AddPolicy.txtPolicyNum"), strRandomPolicyNo);
        if (isMobile){
            sendKeysJS(getObject("AddPolicy.txtPolicyNum"), strRandomPolicyNo);
            waitForPageLoaded();
        }
        waitForJQueryLoad();waitForPageLoaded();
        sendKeys(getObject("AddPolicy.txtboxEmail"), strEmail);
        if (isMobile ){
            sendKeysJS(getObject("AddPolicy.txtboxEmail"), strEmail);
            waitForPageLoaded();
        }
        waitForJQueryLoad();waitForPageLoaded();
        //need to change the code for clicking on the obejct as there are 2 objects for the same property
        if (strContactPreference.equalsIgnoreCase("Email")) {
            clickJS(getObject("AddPolicy.lblRadioOptionEmail"), "Email Option Radio Button");
            waitForElementPresent(getObject("AddPolicy.txtboxEmail"), 10);
            sendKeys(getObject("AddPolicy.txtboxEmail"), strEmail, "Email Entered");
        } else if (strContactPreference.equalsIgnoreCase("Phone")) {
            clickJS(getObject("AddPolicy.lblRadioOptionPhone"), "Phone As A Preference");
            waitForElementPresent(getObject("AddPolicy.txtPhoneNo"), 10);
            sendKeys(getObject("AddPolicy.txtPhoneNo"), strPhone, "Phone Number Entered");
        }
        if (isMobile){
            sendKeysJS(getObject("AddPolicy.txtPolicyNum"), strRandomPolicyNo);
            Thread.sleep(1000);
        }
        report.updateTestLog("EnterAddAccPolicyDepositlinkDetails", " Screenshot", Status_CRAFT.SCREENSHOT);
        waitForJQueryLoad();waitForPageLoaded();
        ScrollAndClickJS("AddPolicy.Continue");
        Thread.sleep(1000);
    }



    public void validateAddPolicyDepositLinkMaxLength() throws Exception {
        String strPolicyName = dataTable.getData("General_Data", "PolicyName");
        String strContactPreference = dataTable.getData("General_Data", "ContactPreference");
        int intMaxLength = Integer.parseInt(dataTable.getData("General_Data", "MaxLength"));
        String objName = dataTable.getData("General_Data", "objName");

        if (strContactPreference.equalsIgnoreCase("Email")) {
            clickJS(getObject("AddPolicy.lblRadioOptionEmail"), "Email Option Radio Button");
            waitForElementPresent(getObject("AddPolicy.txtboxEmail"), 10);
        } else if (strContactPreference.equalsIgnoreCase("Phone")) {
            clickJS(getObject("AddPolicy.lblRadioOptionPhone"), "Phone As A Preference");
            waitForElementPresent(getObject("AddPolicy.txtPhoneNo"), 10);
        }
        HomePage homePg = new HomePage(scriptHelper);
        homePg.verifyMaxLength(intMaxLength, objName);
    }

    public void verifyItemDropDownNotPresentAddAccPolicy() throws Exception {
        boolean bflag = false;
        String strListItems = dataTable.getData("General_Data", "DropDownItems");
        String[] arrlistItems = strListItems.split("::");
        click(getObject("AddPolicy.lblSelectAccDirectionaltxt"),"Directional Text");
        List<WebElement> dropdownItems = driver.findElements(By.xpath("//*[@id='C6__C1__QUE_618BBB1BD78DAE8E1975_div']/div/ul/li"));
        for (int i = 0; i < arrlistItems.length; i++) {
            for (int j = 0; j < dropdownItems.size(); j++) {
                String UiValue = getText(getObject("xpath~//*[@id='C6__C1__QUE_618BBB1BD78DAE8E1975_div']/div/ul/li[" + (j + 1) + "]"));
                if (UiValue.equalsIgnoreCase(arrlistItems[i])) {
                    report.updateTestLog("verifyItemDropDownNotPresentAddAccPolicy", "Drop down Element ' " + arrlistItems[i] + "' is present", Status_CRAFT.FAIL);
                    bflag = true;
                    break;
                }
            }
            if (bflag == false) {
                report.updateTestLog("verifyItemDropDownNotPresentAddAccPolicy", "Drop down Element ' " + arrlistItems[i] + "' NOT present", Status_CRAFT.PASS);
            }
        }
    }

    public void EnterAddAccPolicyFormCurrAccAndClear() throws Exception {
//        String strAccountNo = dataTable.getData("General_Data", "AccountNumber");
//        String strAddress1 = dataTable.getData("General_Data", "Address1");
//        String strAddress2 = dataTable.getData("General_Data", "Address2");
//        String strAddress3 = dataTable.getData("General_Data", "Address3");
//        String strDOB = dataTable.getData("General_Data", "DOB");

        waitForElementPresent(getObject("AddPolicy.txtAccountNum"), 10);
        //sendKeysJS(getObject("AddPolicy.edtAccountNum"), strAccountNo);
        sendKeys(getObject("AddPolicy.edtAccountNum"),"123","Account Number");
        sendKeysJS(getObject("AddPolicy.txtAddr1"), "abc");
        if (isElementDisplayed(getObject("changeAddress.clearCountrySearchBox"), 5)) {
            click(getObject("changeAddress.clearCountrySearchBox"));
            report.updateTestLog("clearCountry", "X is displayed and clicked on Country Search box", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("clearCountry", "X is NOT displayed on Country Search box", Status_CRAFT.FAIL);
        }

        sendKeysJS(getObject("AddPolicy.txtAddr2"), "xyz");
        if (isElementDisplayed(getObject("changeAddress.clearCountrySearchBox"), 5)) {
            click(getObject("changeAddress.clearCountrySearchBox"));
            report.updateTestLog("clearCountry", "X is displayed and clicked on Country Search box", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("clearCountry", "X is NOT displayed on Country Search box", Status_CRAFT.FAIL);
        }


        sendKeysJS(getObject("AddPolicy.txtAddr3"), "pqr");
        if (isElementDisplayed(getObject("changeAddress.clearCountrySearchBox"), 5)) {
            click(getObject("changeAddress.clearCountrySearchBox"));
            report.updateTestLog("clearCountry", "X is displayed and clicked on Country Search box", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("clearCountry", "X is NOT displayed on Country Search box", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to click on confirm button
     */
        public void verifyClickConfirm() throws Exception {
            if (isElementDisplayed(getObject("xpath~//*[text()='Confirm']"),5)){
            clickJS(getObject("xpath~//*[text()='Confirm']"), "Clicked on Button Confirm");
         }
        }
    /**
     * Function to verify error message while adding duplicate policies
     */
    public void verifyErrorMessageforDuplicatePolicy()throws Exception {
        Thread.sleep(1000);
        waitForElementPresent(getObject("AddPolicy.errDuplicatePolicy"),5);
        if (isElementDisplayed(getObject("AddPolicy.errDuplicatePolicy"),5)){
            report.updateTestLog("verifyErrorTextforDuplicatePolicy", " 'To complete the registration of your policy please call Bank of Ireland Life at 1890 330 300' error message is displayed while adding duplicate policy", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyErrorTextforDuplicatePolicy", "No error message is displayed while adding duplicate policy", Status_CRAFT.FAIL);
        }

    }

    public void verifyAddAccPolicyMandFields()throws Exception {
        String addAccPolDOB = dataTable.getData("General_Data","DOB");
        isElementDisplayedWithLog(getObject("AddPolicy.formPage"), " 'Fields to enter details", "Add Account or ...", 15);
        String addAccPolFormLabels = "AddPolicy.formPageLabel";
        if (!addAccPolFormLabels.contains("*")) {
            report.updateTestLog("verifyAddAccPolicyMandatoryFields", "star '*' symbol is NOT displayed for Add account or Policy Mandatory fields", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyMandatoryFields", "star '*' symbol is displayed for Add account or Policy fields", Status_CRAFT.FAIL);
        }

        if (addAccPolDOB.equalsIgnoreCase("Yes")) {
            String strValue = driver.findElement(getObject("AddPolicy.dobValAddAccount")).getAttribute("type");
            String strDoBType=driver.findElement(getObject("AddPolicy.dobValAddAccount")).getAttribute("placeholder");
            if (strValue.equalsIgnoreCase("text")) {
                report.updateTestLog("verifyAddAccPolicyMandatoryFields", "User able to input 'Date of birth' field value manually", Status_CRAFT.PASS);
            }
            else
            {
                report.updateTestLog("verifyAddAccPolicyMandatoryFields", "User unable to input 'Date of birth' field value manually", Status_CRAFT.FAIL);
            }
            if (strDoBType.equalsIgnoreCase("DD/MM/YYYY")) {
                report.updateTestLog("verifyAddAccPolicyMandatoryFields", "User able to input 'Date of birth' in DD/MM/YYYY Format", Status_CRAFT.PASS);
            }
            else
            {
                report.updateTestLog("verifyAddAccPolicyMandatoryFields", "User unable to input 'Date of birth' in DD/MM/YYYY Format", Status_CRAFT.FAIL);
            }
        }
    }

    public void verifyAddPolicyConfirmationPage() throws Exception {

        if (isElementDisplayed(getObject("AddPolicy.imgSuccess"), 5)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Success Image is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Success Image is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPolicy.lblRequestSent"), 2)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Request Sent' text is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Request Sent' text  is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

//        if (isElementDisplayed(getObject("AddPolicy.lblThankYou"), 5)) {
//            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Thank You' text is Displayed on Confirmation Page", Status_CRAFT.PASS);
//        } else {
//            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Thank You' text is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
//        }

        if (isElementDisplayed(getObject("AddPolicy.lblWeMight"), 2)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Information message 'We might be in touch soon if we need to clarify anything.' is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Information message 'We might be in touch soon if we need to clarify anything.' NOT is Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }
        //Added as it was present on the screen. Need to validate if comes for any specific condition.
        if (isElementDisplayed(getObject("AddPolicy.lblDetailsSaved"), 2)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Information message 'Details of this request are saved in your sent messages in Services.' is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "Information message 'Details of this request are saved in your sent messages in Services.' NOT is Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("AddPolicy.lblTime"), 2)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Time' is Displayed on Confirmation Page", Status_CRAFT.PASS);

        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Time' is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("AddPolicy.btnBackToHome"), 2)) {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Back To Home' button is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyAddAccPolicyConfirmationPage", "'Back To Home' button is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }
    }

}

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
 * Function/Epic: Manage Account Alerts of Sprint-13 (Squad-3)
 * Class: Manage Alerts Accounts
 * Developed on: 26/03/2019
 * Developed by: C101965
 * Update on     Updated by     Reason
 * 15/04/2019     C103887       Done code clean up activity
 */

public class ManageAlertsAccounts extends WebDriverHelper {

    public String Referencenumber = "";
    public String TimeofRequest = "";

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public ManageAlertsAccounts(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function: Scroll to view particular  element using JS (“User story No.5531”)
     * Update on     Updated by     Reason
     * 15/04/2019     C103887       Done code clean up activity
     */

    public void ScrollAndClickJS(String linkToClick) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(2000);
            report.updateTestLog("ScrollAndClickJS", "Element To Click", Status_CRAFT.SCREENSHOT);
            JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
            executor.executeScript("arguments[0].click();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(2000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function: To verify and click Services option (“Common function”)
     * Update on     Updated by     Reason
     * 15/04/2019     C103887       Done code clean up activity
     */

    public void verifyAndClickServicesOption() throws Exception {
        String strServiceOption = dataTable.getData("General_Data", "Operation");
      ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
    //  String strServiceOptionXpath = "xpath~//*[contains(@class,'boi-services-btn')]/span[text()='" + strServiceOption + "']";
    String strServiceOptionXpath ="xpath~//span[text()='"+strServiceOption+"']";
    waitForElementPresent(getObject(strServiceOptionXpath), 40);
    clickJS(getObject(strServiceOptionXpath),"Manage Alert Account tile clicked");
    Thread.sleep(1000);
    report.updateTestLog("VerifyManageStatements", "Service Option ::" + strServiceOption + ":: is Clicked", Status_CRAFT.DONE);
}

    /**
     * Function: To click on More Info link on Manage alerts page(“User story No.5531”)
     * Update on     Updated by     Reason
     * 15/04/2019     C103887       Done code clean up activity
     */

    public void verifyNoMAA_ROI() throws Exception{
        String strServiceOption = dataTable.getData("General_Data", "Operation");
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        if (!isElementDisplayed(getObject("xpath~//*[contains(@class,'boi-services-btn')]/span[text()='" + strServiceOption + "']"), 5)) {
            report.updateTestLog("verifyMenuTabNotPresent", "Menu Tab '" + strServiceOption + " ' is not displayed ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyMenuTabNotPresent", "Menu Tab '" + strServiceOption + " ' is displayed ", Status_CRAFT.FAIL);
        }
    }

    public void clickonmoreInfotextlink() throws Exception{
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        if(isElementDisplayed(getObject("MAT.lnkMoreInfoText"),3)){
            click(getObject("MAT.lnkMoreInfoText"));
            payments.validateURLOpens("https://www.bankofirelanduk.com/personal/current-account/textalerts/benefits-of-text-alerts/");
            report.updateTestLog("ManageAccountAlerts", "Manage account alerts info link is Clicked and validated", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("ManageAccountAlerts", "Manage account alerts info link is not Clicked and validated", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function: To verify the account list on Manage alerts page(“User story No.5531”)
     * Update on     Updated by     Reason
     * 15/04/2019     C103887       Done code clean up activity
     */

    public void accountlist() throws Exception{
        if (isElementDisplayed(getObject("xpath~//div[@class='tc-table border-collapse-collapse tc-plain-table manage-account-alerts']"),3)){
            List<WebElement> AccntList = findElements(getObject("xpath~//div[@class='tc-table border-collapse-collapse tc-plain-table manage-account-alerts']/div"));
            for (int i = 1; i <= AccntList.size(); i++){
               if( i%2 ==0){
                        clickJS(getObject("xpath~//ul[@id='C5__QUE_1149C0C174BB56AD985064_R"+i+"_list']/li[2]/span[text()='Off']"), "Off toggle clicked");
                        Thread.sleep(1000);
                        report.updateTestLog("ManageAccountAlerts", "Manage account alerts toggle is Clicked", Status_CRAFT.PASS);
                        Thread.sleep(1000);
                    }
                else {
                        clickJS(getObject("xpath~//ul[@id='C5__QUE_1149C0C174BB56AD985064_R"+i+"_list']/li[1]/span[text()='On']"), "ON toggle clicked");
                        Thread.sleep(1000);
                        report.updateTestLog("ManageAccountAlerts", "Manage account alerts toggle is Clicked", Status_CRAFT.PASS);
                        Thread.sleep(1000);
                    }

                }
            }else{
            report.updateTestLog("ManageAccountAlerts", "Manage account alerts toggle is not present", Status_CRAFT.FAIL);
        }

        }

    public void offTogglebutton() throws Exception{
        if (isElementDisplayed(getObject("xpath~//div[@class='tc-table border-collapse-collapse tc-plain-table']"),3)){
            List<WebElement> AccntList = findElements(getObject("xpath~//div[@class='tc-table border-collapse-collapse tc-plain-table']/div"));
            for (int i = 1; i <= AccntList.size(); i++){
                if(isElementDisplayed(getObject("xpath~//div[@class='tc-table border-collapse-collapse tc-plain-table']"),3)){
                clickJS(getObject("xpath~//ul[@id='C5__QUE_1149C0C174BB56AD985064_R"+i+"_list']/li[2]/span[text()='Off']"), "Off toggle clicked");
                Thread.sleep(1000);
                report.updateTestLog("ManageAccountAlerts", "Manage account alerts Off toggle is Clicked", Status_CRAFT.PASS);
                }else{
                    report.updateTestLog("ManageAccountAlerts", "Manage account alerts Off toggle is NOT Clicked", Status_CRAFT.FAIL);
               }
            }
        }
    }
    public void oNTogglebutton() throws Exception{
        if (isElementDisplayed(getObject("xpath~//div[@class='tc-table border-collapse-collapse tc-plain-table']"),3)){
            List<WebElement> AccntList = findElements(getObject("xpath~//div[@class='tc-table border-collapse-collapse tc-plain-table']/div"));
            for (int i = 1; i <= AccntList.size(); i++){
                if(isElementDisplayed(getObject("xpath~//div[@class='tc-table border-collapse-collapse tc-plain-table']"),3)){
                    clickJS(getObject("xpath~//ul[@id='C5__QUE_1149C0C174BB56AD985064_R"+i+"_list']/li[1]/span[text()='On']"), "ON toggle clicked");
                    Thread.sleep(1000);
                    report.updateTestLog("ManageAccountAlerts", "Manage account alerts ON toggle is Clicked", Status_CRAFT.PASS);
                }else{
                    report.updateTestLog("ManageAccountAlerts", "Manage account alerts ON toggle is NOT Clicked", Status_CRAFT.FAIL);
                }
            }
        }
    }

    public void verifycontinuebtton() throws Exception {
        if(isElementDisplayed(getObject("MAT.btnContinue"), 10)){
        clickJS(getObject("MAT.btnContinue"),"Continue Button clicked");
        report.updateTestLog("ManageAccountAlerts", "Continue button is Clicked", Status_CRAFT.PASS);
    }else{
            report.updateTestLog("ManageAccountAlerts", "Continue button is not Clicked", Status_CRAFT.FAIL);
        }}
    public  void verifycrossbutton() throws Exception {
        if(isElementDisplayed(getObject("MAT.btncross") , 10)){
        clickJS(getObject("MAT.btncross"),"Cross button clicked");
        report.updateTestLog("ManageAccountAlerts", "Cross button is Clicked", Status_CRAFT.PASS);
    } else {
            report.updateTestLog("ManageAccountAlerts", "Cross button is not Clicked", Status_CRAFT.FAIL);
        }}
    public void verifyGobackbutton() throws Exception {
        if(isElementDisplayed(getObject("MAT.btnGoback"), 10)){
        clickJS(getObject("MAT.btnGoback"),"Go Back Button clicked");
        report.updateTestLog("ManageAccountAlerts", "Go back button is Clicked", Status_CRAFT.PASS);
    }
    else {
            report.updateTestLog("ManageAccountAlerts", "Go back button is not Clicked", Status_CRAFT.FAIL);
        }}
    public void verifyConfirmButton() throws Exception{
        if(isElementDisplayed(getObject("MAT.btnConfirm"),10)){
        clickJS(getObject("MAT.btnConfirm"),"Confirm button clicked");
        report.updateTestLog("ManageAccountAlerts", "Confirm button is Clicked", Status_CRAFT.PASS);
    }else {
            report.updateTestLog("ManageAccountAlerts", "Confirm button is not Clicked", Status_CRAFT.FAIL);
        }}

public void verifyConfirmFunction() throws Exception{
    verifycontinuebtton();
    verifycrossbutton();
    verifycontinuebtton();
    verifyGobackbutton();
    verifycontinuebtton();
    verifyConfirmButton();

}
    /**
     * Function: To verify user is navigated to profile page and has mobile number (“User story No.5548”)
     * Update on     Updated by     Reason
     * 15/04/2019     C103887       Done code clean up activity
     */
    // }

    public void Verifycontactinfo() throws Exception{
        VerifyReviewcontactbutton();
        Verifycontactnumber();
    }

    /**
     * Function: To verify user Review Contact button is present on Manage alerts confirmation page (“User story No.5548”)
     * Update on     Updated by     Reason
     * 15/04/2019     C103887       Done code clean up activity
     */

    public void VerifyReviewcontactbutton() throws Exception{
        if (isElementDisplayed(getObject("DirectDebit.lblRequestSent"), 5)) {
            report.updateTestLog("verifyDDConfirmationPage", "'Request Sent' text is Displayed on Confirmation Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyDDConfirmationPage", "Request Sent' text  is NOT Displayed on Confirmation Page", Status_CRAFT.FAIL);
        }
        isElementDisplayedWithLog(getObject("DirectDebit.ConfirmationText"), " 'Thank You' Message", "SEPA Direct Debit Services Confirmation", 10);
        String strMessagecheck = dataTable.getData("General_Data","ConfirmationMessage");
        String strMessagetext = getText(getObject("MAT.ConfirmationText"));
        if (strMessagetext.contains(strMessagecheck)) {
            report.updateTestLog("verifyDDConfirmationPage", "Confirmation Message is displayed successfully.Confirmation Message:" + strMessagecheck, Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyDDConfirmationPage", "Confirmation Message is NOT displayed successfully.Confirmation Message: " + strMessagetext, Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("MAT.Referencenumber"), 5) && isElementDisplayed(getObject("MAT.TimeofRequest"), 5)) {
            report.updateTestLog("Referencetimestamp", "Reference and Time Stamp are displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Referencetimestamp", "Reference and Time Stamp are not displayed on screen", Status_CRAFT.FAIL);
        }
        if(isElementDisplayed(getObject("MAT.ReviewContactDetails"),3)){
            isElementDisplayedWithLog(getObject("MAT.Referencenumber"),"Reference Number","Reference number is dispalyed",5);
            Referencenumber = getText(getObject("MAT.Referencenumber"),10);

            isElementDisplayedWithLog(getObject("MAT.TimeofRequest"),"Time of Request","Time of request is dispalyed",5);
            TimeofRequest = getText(getObject("MAT.TimeofRequest"),5);
            clickJS(getObject("MAT.ReviewContactDetails"),"Review contact details button clicked");
            report.updateTestLog("VerifyReviewcontactbutton", "Review Contact Details button present and Clicked", Status_CRAFT.PASS);
        }
        else{
            report.updateTestLog("VerifyReviewcontactbutton", "Review Contact Details button not present", Status_CRAFT.FAIL);
        }
        Thread.sleep(2000);
    }

    /**
     * Function: To verify contact information is present on Profile page (“User story No.5548”)
     * Update on     Updated by     Reason
     * 15/04/2019     C103887       Done code clean up activity
     */

    public void Verifycontactnumber() throws Exception{
        if(isElementDisplayed(getObject("MAT.mobilenumber"),3)){
            report.updateTestLog("Verifycontactnumber", "Contact Information present for User", Status_CRAFT.PASS);
        } else{
            report.updateTestLog("Verifycontactnumber", "Contact Information missing", Status_CRAFT.FAIL);
        }

    }

        /*else{
            report.updateTestLog("Verifyplaceholders", strServiceOption + " is not present on screen", Status_CRAFT.PASS);
        }*/


    //CFPUR-6503


    public void Verifystatementsoptions() throws Exception{
        String strOption = dataTable.getData("General_Data", "VerifyContent");

        List<WebElement> alloptions = findElements(getObject("Services.Statementoptions"));
        if (alloptions.size() > 1) {
            int intIndex = 1;
            for (WebElement ele : alloptions) {
                String linkname = ele.getText();
                if (!linkname.equalsIgnoreCase(strOption)) {
                    intIndex = intIndex;
                    report.updateTestLog("Verifystatementsoptions", strOption + " is not present on screen ", Status_CRAFT.PASS);
                    break;
                }
                intIndex = intIndex++;

            }
}
        else{
            report.updateTestLog("Verifystatementsoptions", strOption + " is present on screen", Status_CRAFT.FAIL);
        }
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

    public void CMAsentmessagetextval() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject("OrderIntcert.messagetextvalidation"),5)) {
            verifyElementDetails("OrderIntcert.messagetextvalidation");
            Thread.sleep(1000);
            report.updateTestLog("Mangae Alerts Account", "Manage Alerts account validation done on message page ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Mangae Alerts Account", "Manage Alerts account validation is not done on message page", Status_CRAFT.FAIL);
        }
    }
}











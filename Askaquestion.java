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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by C101965 on 23/08/2019.
 */
public class Askaquestion extends WebDriverHelper {

    public String Referencenumber = "";
    public  String TimeofRequest = "";
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public Askaquestion (ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /*General Function:
     *Scroll to view particular  element using JS
    */

    public void ScrollToVisibleJS(String linkToVisible) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToVisible))));
            Thread.sleep(2000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
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
    public void verifytab() throws Exception {

        waitForPageLoaded();
        Thread.sleep(3000);

        /*String windowHandle =driver.getWindowHandle();
        driver.findElementByCssSelector("body").sendKeys(Keys.CONTROL + "t");*/

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        if (tabs.size() > 0) {

            driver.switchTo().window(tabs.get(1));
            String linkTitle = driver.getTitle();
            report.updateTestLog("verifytab", " User is naviagted to :: " + linkTitle + ":: page succssfully.", Status_CRAFT.PASS);
            /*if (linkURL.equalsIgnoreCase(strURLlink)) {
                report.updateTestLog("ChildWindowhandler", " User is naviagted to correct URL :: " + strURLlink + ":: succssfully.", Status_CRAFT.PASS);
*/
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }
        else {
            report.updateTestLog("verifytab", " New Tab did not open ", Status_CRAFT.FAIL);
        }
    }
    public void validatePageHeader() throws Exception {
        //validating page title
        String strPageHeader = dataTable.getData("General_Data", "PageHeader");
        String strPageHeaderMob = dataTable.getData("General_Data", "PageHeader");

        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject("xpath~//h1[@class='boi-no-text-transform  ecDIB  '][text()='" + strPageHeader + "']"), 5)) {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeader + "' is displayed on Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeader + "' is NOT displayed on Page", Status_CRAFT.FAIL);
            }
        } else {
            if (isElementDisplayed(getObject("xpath~//h1[text()='" + strPageHeaderMob + "']"), 5)) {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeaderMob + "' is displayed on Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validatePageHeader", "Page Title '" + strPageHeaderMob + "' is NOT displayed on Page", Status_CRAFT.FAIL);
            }
        }
    }

    public void AskaquestionNavigation() throws Exception {
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        if(isElementDisplayed(getObject(deviceType() + "AAQ.tileaskaquestion"), 15)){
            clickJS(getObject(deviceType() + "AAQ.tileaskaquestion"),"Click on Send us a message tile");
            report.updateTestLog("Ask A Question ", "Ask A Question Tile is clicked", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("Ask A Question ", " Ask A Question Tile is not clicked", Status_CRAFT.FAIL);
        }
    }

    public void AAQpopupLoststolencard() throws Exception{
        if(deviceType.equals("Web")){
        if(isElementDisplayed(getObject(deviceType()+"AAQ.textbeforeucont"),5)) {
            report.updateTestLog("Before you continue ", "text is displayed", Status_CRAFT.PASS);
        }else{
                report.updateTestLog("Before you continue ", "text is not displayed", Status_CRAFT.FAIL);
            }
            if(isElementDisplayed(getObject(deviceType()+"AAQ.textvalidation"),5)){
                click(getObject(deviceType()+"AAQ.calluslink1"),"Report a lost/stolen card- call us link clicked");
                Thread.sleep(1000);
                report.updateTestLog("Report a lost/stolen card ", "call us link clicked", Status_CRAFT.PASS);
                click(getObject(deviceType() + "AAQ.btnGobackarrow"),"Go Back button clicked");
                Thread.sleep(3000);
                AskaquestionNavigation();
            }else{
                report.updateTestLog("Report a lost/stolen card ", "call us link not clicked", Status_CRAFT.FAIL);
            }
        }else{
            if(isElementDisplayed(getObject(deviceType()+"AAQ.textbeforeucont"),5)) {
                report.updateTestLog("Before you continue ", "text is displayed", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("Before you continue ", "text is not displayed", Status_CRAFT.FAIL);
            }
            if(isElementDisplayed(getObject(deviceType()+"AAQ.textvalidation"),5)){
                click(getObject(deviceType()+"AAQ.calluslink1"),"Report a lost/stolen card- call us link clicked");
                Thread.sleep(1000);
                report.updateTestLog("Report a lost/stolen card ", "call us link clicked", Status_CRAFT.PASS);
                click(getObject(deviceType()+"AAQ.btnGobackarrow"),"Go Back button clicked");
                Thread.sleep(3000);
                AskaquestionNavigation();
            }else{
                report.updateTestLog("Report a lost/stolen card ", "call us link not clicked", Status_CRAFT.FAIL);
            }
        }}


    public void AAQpopupFraudsuspiciousactivities() throws Exception{
        if(deviceType.equals("Web")){
        if(isElementDisplayed(getObject(deviceType()+"AAQ.textbeforeucont"),5)) {
                report.updateTestLog("Before you continue ", "text is displayed", Status_CRAFT.PASS);
        }else{
                report.updateTestLog("Before you continue ", "text is not displayed", Status_CRAFT.FAIL);
        }
        if(isElementDisplayed(getObject(deviceType()+"AAQ.textvalidation"),3)){
            click(getObject(deviceType()+"AAQ.calluslink2"),"Report fraud/suspicious activity - call us link clicked");
            Thread.sleep(1000);
            report.updateTestLog("Report fraud/suspicious activity ", "call us link clicked", Status_CRAFT.PASS);
            click(getObject(deviceType() + "AAQ.btnGobackarrow"),"Go Back button clicked");
            Thread.sleep(3000);
            AskaquestionNavigation();
        }else{
            report.updateTestLog("Report fraud/suspicious activity ", "call us link not clicked", Status_CRAFT.FAIL);
        }
    }else{
            if(isElementDisplayed(getObject(deviceType()+"AAQ.textbeforeucont"),5)) {
                report.updateTestLog("Before you continue ", "text is displayed", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("Before you continue ", "text is not displayed", Status_CRAFT.FAIL);
            }
            if(isElementDisplayed(getObject(deviceType()+"AAQ.textvalidation"),3)){
                click(getObject(deviceType()+"AAQ.calluslink2"),"Report fraud/suspicious activity - call us link clicked");
                Thread.sleep(1000);
                report.updateTestLog("Report fraud/suspicious activity ", "call us link clicked", Status_CRAFT.PASS);
                click(getObject(deviceType() + "AAQ.btnGobackarrow"),"Go Back button clicked");
                Thread.sleep(3000);
                AskaquestionNavigation();
            }else{
                report.updateTestLog("Report fraud/suspicious activity ", "call us link not clicked", Status_CRAFT.FAIL);
            }
        }
    }

    public void AAQpopupMakeacomplaint() throws Exception {
        if (deviceType.equals("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "AAQ.textbeforeucont"), 5)) {
                report.updateTestLog("Before you continue ", "text is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Before you continue ", "text is not displayed", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject(deviceType() + "AAQ.textvalidation"), 3)) {
                click(getObject(deviceType() + "AAQ.complainprocesslink3"), "Make a complaint - complaints process");
                Thread.sleep(1000);
                report.updateTestLog("Make a complaint  ", " Make a complaint call us link clicked", Status_CRAFT.PASS);
                verifytab();
                Thread.sleep(3000);

            } else {
                report.updateTestLog("Make a complaint ", "Make a complaint call us link not clicked", Status_CRAFT.FAIL);
            }
        }
    }

    public void clickContinue() throws Exception{
        if (isElementDisplayed(getObject(deviceType() + "AAQ.btncontinue"),3)){
            clickJS(getObject(deviceType() + "AAQ.btncontinue"),"Continue button clicked");
            Thread.sleep(1000);

            report.updateTestLog("Ask A Question ", "Continue button is clicked", Status_CRAFT.PASS);

        }else {
            report.updateTestLog("Ask A Question ", "Continue button is not clicked", Status_CRAFT.FAIL);
        }
    }

//    public void informativetextvalidation() throws Exception{
//        String strtext = "You'll get a response to your BOI Inbox in 2-3 working days. ";
//        if (isElementDisplayed(getObject(deviceType() + "xpath~//h3[text()='Your question']"),3)){
//            report.updateTestLog("Ask A Question ", "Continue button is clicked", Status_CRAFT.PASS);
//            isElementDisplayedWithLog(getObject(deviceType()+"xpath~//div[@class='boi_input']"),"","",3);
//
//
//        }
//    }

    public void selectaccount() throws Exception{
        String strAccount = dataTable.getData("General_Data", "IBAN_Number");
        String strAccountcheck = deviceType() + "AAQ.drpdwnAccount";
        ScrollToVisibleJS(strAccountcheck);
        if (isElementDisplayed(getObject(deviceType() + "AAQ.drpdwnAccount"), 3)) {
            report.updateTestLog("Ask a question", "Dropdown of 'Account' is displayed on Ask a Question Details Page", Status_CRAFT.PASS);
            if(!strAccount.equals("")) {
                clickJS(getObject("xpath~//button[contains(text(),'Please select')]"),"Select account" );
                clickJS(getObject("xpath~//li[contains(text(),'"+strAccount+"')]"),"Account No. is selected as : " +strAccount);
                waitForPageLoaded();
            }
        }
    }

    public void selectsubject() throws Exception{
        String strSubject = dataTable.getData("General_Data", "Account_Type");
        String strSubjectcheck = deviceType() + "AAQ.drpdwnSubject";
        ScrollToVisibleJS(strSubjectcheck);
        if (isElementDisplayed(getObject(deviceType() + "AAQ.drpdwnSubject"), 3)) {
            report.updateTestLog("Ask a question", "Dropdown of 'Subject' is dislayed on Ask a Question Details Page", Status_CRAFT.PASS);
            if(!strSubject.equals("")) {
                clickJS(getObject("xpath~//button[contains(text(),'Please select')]"),"Select subject");
                clickJS(getObject("xpath~//li[contains(text(),'"+strSubject+"')]"),"Subject is selected as : " +strSubject);
                waitForPageLoaded();
            }
        }
    }

    public void enterTextMessage() throws Exception{
        String strAccount = dataTable.getData("General_Data", "PayeeMessage");
        if(isElementDisplayed(getObject(deviceType()+"AAQ.txtMessage"),5)){
            sendKeys(getObject(deviceType()+"AAQ.txtMessage"),strAccount);
            Thread.sleep(1000);
            report.updateTestLog("Ask a question", "Ask a question text meesage is entered on Ask a Question Details Page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("Ask a question", "Ask a question text meesage is not entered on Ask a Question Details Page", Status_CRAFT.FAIL);
        }
    }
    public void textInformation() throws Exception{
        String strImpInfo = dataTable.getData("General_Data", "Nickname");
        String strImpInfoText = getText(getObject(deviceType()+ "AAQ.txtinformation"));
        if (strImpInfo.equalsIgnoreCase(strImpInfoText)){
            //if (isElementDisplayed(getObject("MTU.txtImpInfo"), 3)) {
            report.updateTestLog("Information Text  ", "Information Text is displayed as " + strImpInfoText, Status_CRAFT.PASS);
            Thread.sleep(2000);
        } else {
            report.updateTestLog("Important Information Text box ", "Information Text is not as expected", Status_CRAFT.FAIL);
        }
    }

    public void clickSend() throws Exception{
        if (isElementDisplayed(getObject(deviceType() + "AAQ.btnSend"),3)){
            clickJS(getObject(deviceType() + "AAQ.btnSend"),"Send button clicked");
            Thread.sleep(1000);
            report.updateTestLog("Ask A Question ", "Send button is clicked", Status_CRAFT.PASS);

        }else {
            report.updateTestLog("Ask A Question ", "Send button is not clicked", Status_CRAFT.FAIL);
        }
    }


    public void verifyConfirmationScreen() throws Exception{
        if (isElementDisplayed(getObject(deviceType() + "AAQ.txtRefrence"), 5) &&
                isElementDisplayed(getObject(deviceType() + "AAQ.txttimeofrequest"), 5)) {

            String strReferenceID = getText(getObject(deviceType() + "AAQ.txtRefrence"));
            String strTimestamp = getText(getObject(deviceType() + "AAQ.txttimeofrequest"));

            report.updateTestLog("Referencetimestamp", "Reference is displayed as : "+strReferenceID+ " and Time Stamp is displayed as : " +strTimestamp , Status_CRAFT.PASS);

        } else {
            report.updateTestLog("Referencetimestamp", "Reference and Time Stamp are not displayed on screen", Status_CRAFT.FAIL);
        }
        if(isElementDisplayed(getObject(deviceType()+ "AAQ.txtSent"),5) && isElementDisplayed(getObject(deviceType()+ "AAQ.txtvalidation"),5)){
            String strTextvaliadtion = getText(getObject(deviceType()+"AAQ.txtvalidation"),5);
            String strTextvaliadtionUi = "You'll get a response to your BOI Inbox in 2-3 working days.\n" + "Details of this request are saved in your sent messages in Services.";
            if(strTextvaliadtion.equalsIgnoreCase(strTextvaliadtionUi)){
            report.updateTestLog("Text Message and Sent ", "Text Message and Sent text are displayed on screen as "+strTextvaliadtion, Status_CRAFT.PASS);
        }else{
                report.updateTestLog("Text Message and Sent ", "Text Message and Sent text are not as expected", Status_CRAFT.FAIL);
            }}else{
            report.updateTestLog("Text Message and Sent ", "Text Message and Sent text are not displayed on screen", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject(deviceType()+ "AAQ.btnbacktoservices"),5)){
            report.updateTestLog("Back to Services button ", "Back to services button is displayed on confirmation screen", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("Back to Services button ", "Back to services button is not displayed on confirmation screen", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject(deviceType() +"AAQ.btnGotohome"), 5)) {
            isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'ReferenceID: ')]"),"Reference Number","Reference number is dispalyed",5);
            Referencenumber = getText(getObject("xpath~//*[contains(text(),'ReferenceID: ')]"),1);
            isElementDisplayedWithLog(getObject("xpath~//div[contains(text(),'Time of request: ')]"),"Time of Request","Time of request is dispalyed",5);
            TimeofRequest = getText(getObject("xpath~//div[contains(text(),'Time of request: ')]"),5);
            clickJS(getObject(deviceType() +"AAQ.btnGotohome"), "Back to Home");
            Thread.sleep(2000);
            report.updateTestLog("BacktoHome button ", "BacktoHome button Clicked on confirmation screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("BacktoHome button ", "BacktoHome button not Clicked on confirmation screen", Status_CRAFT.FAIL);
        }
    }
    public void verifyErrorMessage() throws Exception{
        String strAccountErrmsg = "You must select an account/policy to continue";
        String strSubjectErrmsg = "Please select a subject";
        String strTxtmsg = "Please insert message details";
        String strAccountErrobj = getText(getObject(deviceType()+ "AAQ.txtErrmsgAccount"),3);
        String strSubjectErrobj = getText(getObject(deviceType()+ "AAQ.txtErrmsgSubject"),3);
        String strTextmsgObj = getText(getObject(deviceType() + "AAQ.txtErrmsgText"),3);
        if(strAccountErrmsg.equals(strAccountErrobj)){
            report.updateTestLog("Account DropDown ", "Account dropdown error message is displayed" +strAccountErrobj, Status_CRAFT.PASS);
        }else{
            report.updateTestLog("Account DropDown ", "Account dropdown error message is not as expected ", Status_CRAFT.FAIL);
        }
        if(strSubjectErrmsg.equals(strSubjectErrobj)){
            report.updateTestLog("Subject DropDown ", "Subject dropdown error message is displayed" + strSubjectErrobj, Status_CRAFT.PASS);
        }else{
            report.updateTestLog("Subject DropDown ", "Subject dropdown error message is not as expected ", Status_CRAFT.FAIL);
        }
        if(strTxtmsg.equals(strTextmsgObj)){
            report.updateTestLog("Text message box ", "Text message box error message is displayed" + strTextmsgObj, Status_CRAFT.PASS);
        }else{
            report.updateTestLog("Text message box ", "Text message box error message is not as expected ", Status_CRAFT.FAIL);
        }
    }
    //CFPUR-10524
    public void VerifyInvalidMessageSendButton() throws Exception {
        if(isElementDisplayed(getObject(deviceType()+ "AskQuestionSendBtn"), 10)){
            clickJS(getObject(deviceType()+"AskQuestionSendBtn"), "Send button clicked");



           /* String juridiction = dataTable.getData("General_Data","JurisdictionType");
            if(juridiction.equalsIgnoreCase("NI"))*/


        String strErrorMessage = dataTable.getData("General_Data","VerifyText");
        String[] strErrorMsg = strErrorMessage.split(",");
        List<WebElement> ErrorMsgOnScreen = findElements(getObject(deviceType()+"AskQuestionErrorMsg"));
        if(ErrorMsgOnScreen.size()>1){
        for(int i=0;i<strErrorMsg.length;i++)
        {
            String var1 = ErrorMsgOnScreen.get(i).getText();
                String varErrorMsg = strErrorMsg[i];
                //String varErrorMsgOnScreen = var1;
                if (varErrorMsg.equalsIgnoreCase(var1)) {
                    report.updateTestLog("Verify Error Message ", "Error message verified successfully" + var1, Status_CRAFT.PASS);

                } else {
                    report.updateTestLog("Verify error message", "Error message verification failed", Status_CRAFT.FAIL);
                }


              }}
    }}

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

    public void verifymessageAAQ() throws Exception{
        HomePage home = new HomePage(scriptHelper);
        home.verifyElementDetails(deviceType()+"AAQ.Verifymessagevalidation");
        Thread.sleep(1000);
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
                if(isElementDisplayed(getObject(deviceType()+"AAQ.MessageFieldElements"),10));
                List<WebElement> SentMsgElement = findElements(getObject(deviceType()+"AAQ.MessageFieldElements"));
                {
                    for(WebElement ele: SentMsgElement)
                    {
                        String ele1 = ele.getText();

                    }
                }


                break;
            }

        }

    }

    public void ConfirmationPageValidation(String sectionElement) throws Exception {
        String[] arrValidation = dataTable.getData("General_Data", "VerifyContent").split(";");
        if (isElementDisplayed(getObject(sectionElement), 5)) {
            String dataSectionUI = getText(getObject(sectionElement)).toUpperCase();
            report.updateTestLog("verifyElementDetailsConfim", ":: Page Screenshot ::", Status_CRAFT.SCREENSHOT);
            for (int intValidate = 0; intValidate < arrValidation.length; intValidate++) {
                String strValidateHead = arrValidation[intValidate].split("::")[0];
                String strValidateData = arrValidation[intValidate].split("::")[1];
                if (dataSectionUI.contains(strValidateData.toUpperCase())) {
                    report.updateTestLog("verifyElementDetailsConfim", strValidateHead + " value '" + strValidateData + "' is displayed correctly ", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifyElementDetailsConfim", strValidateHead + " value '" + strValidateData + "' is not displayed correctly or not present ", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("verifyElementDetailsConfim", "Section element is not displayed on Screen", Status_CRAFT.FAIL);
        }

        /*if (isElementDisplayed(getObject(deviceType() + "AAQ.txtRefrence"), 5) &&
                isElementDisplayed(getObject(deviceType() + "AAQ.txttimeofrequest"), 5)) {

            report.updateTestLog("Referencetimestamp", "Reference and Time Stamp are displayed on screen", Status_CRAFT.PASS);

        } else {
            report.updateTestLog("Referencetimestamp", "Reference and Time Stamp are not displayed on screen", Status_CRAFT.FAIL);
        }*/
        if (isElementDisplayed(getObject(deviceType() +"AAQ.btnGotohome"), 5)) {
            Thread.sleep(5000);

            isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'Reference')]"),"Reference Number","Reference number is dispalyed",5);

            Referencenumber = getText(getObject("xpath~//*[contains(text(),'Reference')]"),10);
            Thread.sleep(3000);
//            String FinalRef[] = Referencenumber.split(":");
//            String FinalRef1 = FinalRef[1].trim();

            isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'Time of request')]"),"Time of Request","Time of request is dispalyed",5);
            TimeofRequest = getText(getObject("xpath~//*[contains(text(),'Time of request')]"),5);

            clickJS(getObject(deviceType() +"AAQ.btnGotohome"), "Back to Home");
            Thread.sleep(2000);
            report.updateTestLog("BacktoHome button ", "BacktoHome button Clicked on confirmation screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("BacktoHome button ", "BacktoHome button not Clicked on confirmation screen", Status_CRAFT.FAIL);
        }

    }
    public void messagesentPageValidation()throws Exception
    {
        boolean blnClicked;
        String  s1 = this.Referencenumber;
        String  s2 = this.TimeofRequest;
        String  refMsg=null;
        String timeMsg=null;

        //Referencenumber = getText(getObject("xpath~//div[contains(text(),'Reference: ')]"),10);
        String FinalRef[] = s1.split(" ");
        String FinalRef1 = FinalRef[2].trim();
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
                report.updateTestLog("message validation", ":: Page Screenshot ::", Status_CRAFT.SCREENSHOT);
                report.updateTestLog(" ", "message validation", Status_CRAFT.PASS);




                /////////////////
                // Sentmailslist.get(i).click();
                /*refMsg=getText(getObject("xpath~//span[@id='C5__QUE_09D302FA0AE6D1974460703']")).substring(4,18);
                if(refMsg.equals(FinalRef1)){
                   // System.out.print("Reference number matched");

                }
                else{
                    System.out.print("Reference numeber not matched");
                }
                if(isElementDisplayed(getObject(deviceType()+"AAQ.MessageFieldElements"),10));
                List<WebElement> SentMsgElement = findElements(getObject(deviceType()+"AAQ.MessageFieldElements"));
                {
                    for(WebElement ele: SentMsgElement)
                    {
                        String ele1 = ele.getText();

                    }
                }
*/

                break;
            }

        }

    }
    public void sentMsgDetailValidation(String strSectionElement) throws Exception{

        String  s1 = this.Referencenumber;
        String FinalRef[] = s1.split(" ");
        String FinalRef1 = FinalRef[2].trim();



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






}

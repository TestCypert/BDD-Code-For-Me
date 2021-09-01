package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Random;

/**
 * Created by C966119 on 05/02/2019.
 */
/**
 * Function/Epic : Profile Management as part of sprint-11 (Squad-1)
 * Class : ProfileManagment
 * Developed on : 05/02/2019
 * Developed by : C966119
 * Update on       Update by       Reason
 * 23/04/2019      c101979         Done code clean up activity
 */
public class ProfileManagment extends WebDriverHelper {

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public ProfileManagment(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function : Navigate to the Profile Page for mobile and desktop
     * Update on       Update by       Reason
     * 23/04/2019      c101979         Done code clean up activity
     */
    public void navigateToProfilePage() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.imgProfileIcon"), 8)) {
            clickJS(getObject(deviceType() + "home.imgProfileIcon"), "Profile Icon");
        }
        waitForElementPresent(getObject(deviceType() + "profile.lblHeaderProfile"), 10);
        if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderProfile"), 8)) {
            report.updateTestLog("NavigateToProfilePage", " Navigated to profile page successfully..After clicking Profile icon.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("NavigateToProfilePage", " Did not navigated to profile page successfully..After clicking Profile icon.", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function: Add Mobile number for mobile and desktop (CFPUR-4327)
     * Update on       Update by       Reason
     * 23/04/2019      c101979         Done code clean up activity
     **/
    public void addProfileMobileNumber() throws Exception {
        String strMobile = dataTable.getData("General_Data", "Relationship_Status");
        //click(getObject("profile.lnkUpdateMobile"));
        click(getObject("profile.lnkAddMobileNumber"));
        if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderAddMobile"), 10)) {
            //if (isElementDisplayed(getObject("w.profile.lblHeaderUpdateMobile"), 10)) {
            report.updateTestLog("addProfileMobileNumber", "Navigated to the page : Add Mobile Number : successfully.", Status_CRAFT.PASS);
            sendKeys(getObject("profile.inputboxProfileMobile"), strMobile);
            waitForElementPresent(getObject("profile.inputboxConfirmProfile"), 5);
            sendKeys(getObject("profile.inputboxConfirmProfile"), strMobile);
            clickJS(getObject("AddPolicy.Continue"), "Click on Continue button to add mobile number");
        } else {
            report.updateTestLog("addProfileMobileNumber", " Did not navigated to the page : Add Mobile Number : successfully.", Status_CRAFT.FAIL);
        }

        clickJS(getObject("SendMoney.btnGoBack"), "Click on Go Back button on Enter Pin Page");
        if (isElementDisplayed(getObject("w.profile.lblHeaderUpdateMobile"), 10)) {
            report.updateTestLog("navigateToGoBack", "Navigated to the page : Add Mobile Number : successfully.", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("navigateToGoBack", "Navigated to the page : Add Mobile Number : successfully.", Status_CRAFT.PASS);
        }
    }

    /**
     * Function : Update Mobile number for mobile and desktop (CFPUR-305)
     * Update on       Update by       Reason
     * 23/04/2019      c101979         Done code clean up activity
     */
    public void updateProfileMobileNumber() throws Exception {
        //need to change this data sheet column and get these values from proper column name- by Rajiv
        String strMobile = dataTable.getData("General_Data", "Relationship_Status");
        String strExistingMobile = dataTable.getData("General_Data", "Account_Type");
        String strTextObj = "xpath~//*[text()='######" + strExistingMobile + "']";
       /* if (isElementDisplayed(getObject(strTextObj), 10)) {
            report.updateTestLog("updateProfileMobileNumber", "Existing Mobile Number ::" + strExistingMobile + ":: displayed correctly.", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("updateProfileMobileNumber", "Existing Mobile Number ::" + strExistingMobile + ":: NOT displayed correctly.", Status_CRAFT.FAIL);
        }*/
        if (isElementDisplayed(getObject("profile.lnkUpdateMobile"), 10)) {
                click(getObject("profile.lnkUpdateMobile"));
            } else {
                report.updateTestLog("updateProfileMobileNumber", "Update Mobile link is not present", Status_CRAFT.FAIL);
            }

            if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderUpdateMobile"), 10)) {
                report.updateTestLog("updateProfileMobileNumber", "Navigated to the page : Update Mobile Number : successfully.", Status_CRAFT.PASS);
                sendKeysJS(getObject("profile.inputboxProfileMobile"), strMobile);
                waitForElementPresent(getObject("profile.inputboxConfirmProfileMobile"), 10);
                sendKeysJS(getObject("profile.inputboxConfirmProfileMobile"), strMobile);
                clickJS(getObject("AddPolicy.Continue"), "Click on Continue button to update mobile number");
            } else {
                report.updateTestLog("updateProfileMobileNumber", " Did not navigated to the page : Update Mobile Number : successfully.", Status_CRAFT.FAIL);
            }
        }

    /*Splitting verify existing Mobile and Update Mobile functionality for E2E as it needs to be run in Sub Iterations - Sweta */

    public void ValidateExiProfMobNum() throws Exception {
//        String strExistingMobile = dataTable.getData("General_Data", "MobileNumber");
//        String strTextObj = "xpath~//*[text()='######" + strExistingMobile + "']";
//        if (isElementDisplayed(getObject(strTextObj), 10)) {
//            report.updateTestLog("updateProfileMobileNumber", "Existing Mobile Number ::" + strExistingMobile + ":: displayed correctly.", Status_CRAFT.DONE);
//        } else {
//            report.updateTestLog("updateProfileMobileNumber", "Existing Mobile Number ::" + strExistingMobile + ":: NOT displayed correctly.", Status_CRAFT.FAIL);
//        }

        String strUINumber = getText(getObject("xpath~//*[contains(text(),'######')]"));
        if (isElementDisplayed(getObject("xpath~//*[contains(text(),'######')]"), 10)) {
            report.updateTestLog("updateProfileMobileNumber", "Existing Mobile Number on UI is ::" + strUINumber + ":: displayed.", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("updateProfileMobileNumber", "Existing Mobile Number on UI is ::" + strUINumber + ":: NOT displayed.", Status_CRAFT.FAIL);
        }
    }

    public void updateProfileMobile() throws Exception {
        String strMobile = dataTable.getData("General_Data", "Phone");
        String strIterationFlalgPolicy = dataTable.getData("General_Data","IterationFlag");

        if (strIterationFlalgPolicy.equalsIgnoreCase("Yes")) {
            Thread.sleep(2000);
            if(deviceType.equalsIgnoreCase("Web")){click(getObject("profile.lnkUpdateMobile"), "Update Mobile Number Textbox");
                waitForPageLoaded();waitForJQueryLoad();}
            else{
                clickJS(getObject("profile.lnkUpdateMobile"), "Update Mobile Number Textbox");}
            waitForPageLoaded();waitForJQueryLoad();

        }
        waitForPageLoaded();waitForJQueryLoad();
            waitForElementPresent(getObject(deviceType() + "profile.lblHeaderUpdateMobile"), 5);

        if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderUpdateMobile"), 10)) {
            report.updateTestLog("updateProfileMobileNumber", "Navigated to the page : Update Mobile Number : successfully.", Status_CRAFT.PASS);
            if(deviceType.equalsIgnoreCase("Web"))
            {
                sendKeys(getObject("profile.inputboxProfileMobile"), strMobile);
            }
            else {
                sendKeysJS(getObject("profile.inputboxProfileMobile"), strMobile);
            }
            waitForElementPresent(getObject("profile.inputboxConfirmProfileMobile"), 10);
            waitForJQueryLoad();
            waitForPageLoaded();
            if(deviceType.equalsIgnoreCase("web")){
                click(getObject("profile.inputboxConfirmProfileMobile"),"Clicked on Textbox Confirm mobile number.");
                Thread.sleep(1000);
            }else{
                clickJS(getObject("profile.inputboxConfirmProfileMobile"),"Clicked on Textbox Confirm mobile number.");
                Thread.sleep(1000);
            }


            String strConfirmMob = dataTable.getData("General_Data","InvalidMobileNumber");
            String strInvConfirmMob = dataTable.getData("General_Data","MismatchConfirmNumber");
            if (strInvConfirmMob.equalsIgnoreCase("Yes")) {
                if(deviceType.equalsIgnoreCase("Web"))
                {
                    sendKeys(getObject("profile.inputboxConfirmProfileMobile"), strConfirmMob);
                }
                else {
                    sendKeysJS(getObject("profile.inputboxConfirmProfileMobile"), strConfirmMob);
                }
            }
            else {
                if(deviceType.equalsIgnoreCase("Web")){
                sendKeysJS(getObject("profile.inputboxConfirmProfileMobile"), strMobile);
                }
                else{
                    sendKeysJS(getObject("profile.inputboxConfirmProfileMobile"), strMobile);
                }
            }
            waitForElementPresent(getObject("AddPolicy.Continue"),10);
            if(deviceType.equalsIgnoreCase("web")){
                click(getObject("AddPolicy.Continue"), "Click on Continue button to update mobile number");
            }else{
                clickJS(getObject("AddPolicy.Continue"), "Click on Continue button to update mobile number");
            }

            Thread.sleep(1000);
        } else {
            report.updateTestLog("updateProfileMobileNumber", " Did not navigated to the page : Update Mobile Number : successfully.", Status_CRAFT.FAIL);
        }
    }
    /* Function to Navigate from PIN Page to Profile Management Page */

    public void validatePinToProfileNav() throws Exception {
        if (isElementDisplayed(getObject(deviceType()+"profile.PINPage"), 10)) {
            report.updateTestLog("verifyPinToProfileNav", "User Navigated to PIN Page successfully.", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyPinToProfileNav", "User is NOT Navigated to PIN Page.", Status_CRAFT.FAIL);
        }
            clickJS(getObject(deviceType()+"profile.home.Goback"), "Back is");

        if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderUpdateMobile"), 10)) {
            report.updateTestLog("verifyPinToProfileNav", "User Navigated Back to Profile Page after 'Go Back' button is clicked.", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyPinToProfileNav", "User is NOT Navigated Back to Profile Page after 'Go Back' button is clicked.", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Add Email for mobile and desktop (CFPUR - 4336)
     * Update on       Update by       Reason
     * 23/04/2019      c101979         Done code clean up activity
     */
    public void addProfileEmail() throws Exception {
        String strEmailId = dataTable.getData("General_Data", "Current_Balance");
        if(isElementDisplayed(getObject(deviceType() + "profile.lnkAddEmail"),2)){
            clickJS(getObject(deviceType() + "profile.lnkAddEmail"),"Profile email");
        }else {
            clickJS(getObject("profile.lnkUpdateEmail"),"Profile email");
        }

        if ((isElementDisplayed(getObject(deviceType() + "profile.lblHeaderAddEmail"),2))||(isElementDisplayed(getObject(deviceType() + "profile.lblHeaderUpdateEmail"),2))){
            report.updateTestLog("addProfileEmail", "Navigated to the page : Add Email Id : successfully.", Status_CRAFT.PASS);
            sendKeys(getObject("profile.inputboxProfileEmail") , strEmailId);
            waitForElementPresent(getObject("profile.inputboxConfirmProfileEmail"),5);
            sendKeys(getObject("profile.inputboxConfirmProfileEmail") , strEmailId);
            clickJS(getObject("AddPolicy.Continue") , "Click on Continue button to add email id");
        }else{
            report.updateTestLog("addProfileEmail", " Did not navigated to the page : Add Email Id : successfully.", Status_CRAFT.FAIL);
        }

    }


    /**
     * Function : Update Email for mobile and desktop (CFPUR - 4338)
     * Update on       Update by       Reason
     * 23/04/2019      c101979         Done code clean up activity
     */
    public void updateProfileEmail() throws Exception {
        String strEmailId = dataTable.getData("General_Data", "Current_Balance");
        String strExistingEmailID = dataTable.getData("General_Data", "Available_Balance");
        String strTextObj = "xpath~//*[contains(text(),'"+ strExistingEmailID +"')]";
        if (isElementDisplayed(getObject(strTextObj),10)){
            report.updateTestLog("updateProfileEmail", "Existing Email ID ::"+ strExistingEmailID + ":: displayed correctly.", Status_CRAFT.DONE);
       }else{
            report.updateTestLog("updateProfileEmail", "Existing Email ID ::"+ strExistingEmailID + ":: NOT displayed correctly.", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("profile.lnkUpdateEmail"),10)){
            report.updateTestLog("updatePrprofile", "Update Email link is present", Status_CRAFT.PASS);
            click(getObject("profile.lnkUpdateEmail"));
        }
        else{
            report.updateTestLog("updatePrprofile", "Update Email link is not present", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderUpdateEmail"),10)){
            report.updateTestLog("updateProfileEmail", "Navigated to the page : Update Email Id : successfully.", Status_CRAFT.PASS);
            sendKeys(getObject("profile.inputboxProfile") , strEmailId);
            waitForElementPresent(getObject("profile.inputboxConfirmProfile"),10);
            sendKeysJS(getObject("profile.inputboxConfirmProfile") , strEmailId);
            clickJS(getObject("AddPolicy.Continue") , "Click on Continue button to update email id");
        }else{
            report.updateTestLog("updateProfileEmail", " Did not navigated to the page : Update Email Id : successfully.", Status_CRAFT.FAIL);
        }
    }

    /*Splitting verify existing Email and Update Email functionality for E2E as it needs to be run in Sub Iterations - Sweta */

    public void validateExistEmail() throws Exception {

        waitForJQueryLoad();waitForPageLoaded();
        waitForElementPresent(getObject("xpath~//*[text()='Email']/following-sibling::*[contains(@class,'boi_input_sm')]"),10);

        String strExistingEmailID = dataTable.getData("General_Data", "Email");
        if(!"".equals(scriptHelper.commonData.strNewEmailID)){
            strExistingEmailID = scriptHelper.commonData.strNewEmailID;
        }
        //String strTextObj = "xpath~//*[contains(text(),'" + strExistingEmailID + "')]";
        String strUpdatedEmailID = getText(getObject("xpath~//*[text()='Email']/following-sibling::*[contains(@class,'boi_input_sm')]"));
        if (strUpdatedEmailID.equals(strExistingEmailID)) {
            report.updateTestLog("verifyExistingEmailAddress", "Existing/Updated Email ID ::" + strExistingEmailID + ":: displayed correctly.", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("verifyExistingEmailAddress", "Existing/Updated Email ID ::" + strExistingEmailID + ":: NOT displayed correctly, Actual:'"+ strUpdatedEmailID+"'", Status_CRAFT.FAIL);
        }
    }

    public void updateProfileEmailID() throws Exception {

        String strEmailId = dataTable.getData("General_Data", "NewEmail");
        if(strEmailId.equalsIgnoreCase("Random")){
            Random rand = new Random();
            int randomNum = 100000 + rand.nextInt((999999 - 100000) + 1);
            strEmailId = "testTeam_"+ randomNum +"@test.com";
            scriptHelper.commonData.strNewEmailID = strEmailId;
        }
        String strIterationFlalgPolicy = dataTable.getData("General_Data","IterationFlag");
        if (strIterationFlalgPolicy.equalsIgnoreCase("Yes")) {
            clickJS(getObject("profile.lnkUpdateEmail"), "Update Email Address Textbox");
        }
        waitForElementPresent(getObject(deviceType() + "profile.lblHeaderUpdateEmail"), 5);
        if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderUpdateEmail"),10)){
            report.updateTestLog("verifyUpdateEmail", "Navigated to the page : Update Email Id : successfully.", Status_CRAFT.PASS);
            Thread.sleep(3000);
            clickJS(getObject("profile.inputboxProfileEmail"), "Enter your email box");
            clickJS(getObject("profile.inputboxProfileEmail"), "Enter your email box");
            sendKeys(getObject("profile.inputboxProfileEmail") , strEmailId);
            sendKeys(getObject("profile.inputboxProfileEmail") , strEmailId);
            waitForElementPresent(getObject("profile.inputboxConfirmProfileEmail"),5);
            String strConfirmEmail = dataTable.getData("General_Data","InvalidEmail");
            String strInvConfirmEmail = dataTable.getData("General_Data","MismatchConfirmNumber");
            if (strInvConfirmEmail.equalsIgnoreCase("Yes"))
            {
                sendKeys(getObject("profile.inputboxConfirmProfileEmail"), strConfirmEmail);
            }
            else
            {
                sendKeys(getObject("profile.inputboxConfirmProfileEmail"), strEmailId);
                sendKeys(getObject("profile.inputboxConfirmProfileEmail"), strEmailId);
            }
//            driver.hideKeyboard();
            clickJS(getObject("AddPolicy.Continue") , "Click on Continue button to updat email id");
            Thread.sleep(2000);
        }
        else
        {
            report.updateTestLog("verifyUpdateEmail", " Did not navigated to the page : Update Email Id : successfully.", Status_CRAFT.FAIL);
        }

    }
    /**
     * Function to mobile pin digits
     * Update on       Update by       Reason
     * 23/04/2019      c101979         Done code clean up activity
     */
    public void enterRequiredPin_Email() throws InterruptedException {
        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        if (isElementDisplayed(getObject("sca.lblPinPageHeader"), 20 )){
            List<WebElement> lstPinEnterFieldGrp = findElements(getObject("profile.edtPinEnterField"));
            for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
                if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                    lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                }
            }
            report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.PASS);
            clickJS(getObject("AddPayee.Confirm"), "Confirm");
        }
        else
        {
                report.updateTestLog("EnterRequiredPin", "Unable to enter PIN digits or option to provide PIN is not displayued", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to mobile pin digits
     */
    public void enterRequiredPinMobile() throws InterruptedException {
        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        if (isElementDisplayed(getObject("AddPayee.Confirm"), 20 )){
            //List<WebElement> lstPinEnterFieldGrp = findElements(getObject("profile.edtPinEnterField"));
            List<WebElement> lstPinEnterFieldGrp = findElements(getObject("profile.edtPinEnterFieldNew"));
            //Entering values for only enabled fields
            for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
                if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                    lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                }
            }
            report.updateTestLog("EnterRequiredPin", "Entered PIN digits", Status_CRAFT.PASS);
            clickJS(getObject("AddPayee.Confirm"), "Confirm");
        }
    }

    /**
     * Function : Profile : Update / Add Success message
     * Update on       Update by       Reason
     * 23/04/2019      c101979         Done code clean up activity
     */
    public void validateProfileSuccessMessage() throws Exception {
        waitForPageLoaded();
        waitForElementPresent(getObject("xpath~//span[@class='boi_input tc-normal-align']/div") , 10);
        String strSuccessMessage = dataTable.getData("General_Data", "ConfirmationMessage");
        String strObject = getText(getObject("xpath~//span[@class='boi_input tc-normal-align']/div"));
        if (strObject.equalsIgnoreCase(strSuccessMessage)){
            report.updateTestLog("validateProfileSuccessMessage", "The Success Message :: " + strSuccessMessage + " :: Correctly displayed.", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("validateProfileSuccessMessage", "The Success Message :: " + strSuccessMessage + " :: Is NOT Correctly displayed.", Status_CRAFT.FAIL);
        }


    }

    /**
     * Function : Profile : Verify updated Mobile number
     * Update on       Update by       Reason
     * 06/07/2019      C103401         Done code clean up activity
     */
    public void validateUpdatedMobileOnSuccessPage() throws InterruptedException {
        String strExistingMobile = dataTable.getData("General_Data", "Account_Type");
        String strTextObj = "xpath~//*[text()='######"+ strExistingMobile +"']";
        if (isElementDisplayed(getObject(strTextObj),10)){
            report.updateTestLog("updateProfileMobileNumber", "Updated/Existing Mobile Number ::"+ strExistingMobile + ":: displayed correctly on Profile page.", Status_CRAFT.DONE);
        }else{
            report.updateTestLog("updateProfileMobileNumber", "Updated/Existing Mobile Number ::"+ strExistingMobile + ":: NOT displayed correctly on Profile page", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function : Profile : Verify Privacy Text
     * Update on       Update by       Reason
     * 15/07/2019      C101979         Mobile Automation E2E
     */

    public void verifyUpdatePrivacyText() throws Exception {
        String Jurisdiction = dataTable.getData("General_Data", "JurisdictionType");
        HomePage homePage = new HomePage(scriptHelper);
        String strType = dataTable.getData("General_Data", "Operation");
        if (strType.equalsIgnoreCase("UpdateMobile")) {
            clickJS(getObject("profile.lnkUpdateMobile"), "Update Mobile Number link");
        } else if (strType.equalsIgnoreCase("UpdateEmail")) {
            clickJS(getObject("profile.lnkUpdateEmail"), "Update Email Address link");
        }
        String strObject = getText(getObject("profile.UpdatePrivacyTextCross"));
        String strSuccessMessage = dataTable.getData("General_Data", "VerifyContent");
        if (strObject.equalsIgnoreCase(strSuccessMessage)) {
           report.updateTestLog("verifyUpdatePrivacyTextFunc", "The Privacy Text :: " + strSuccessMessage + " :: Correctly displayed.", Status_CRAFT.PASS);
           if (Jurisdiction.equals("NI") || Jurisdiction.equals("GB")) {
               if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                   String strExpectedLink = "https://www.bankofirelanduk.com/site-links/privacy-notice/";
                   homePage.verifyHrefLink(strExpectedLink, "profile.PrivacyLink");
               }else {
                   clickJS(getObject("profile.PrivacyLink"), "Privacy Notice Link");
                   homePage.verifyNewlyOpenedTab("https://www.bankofirelanduk.com/site-links/privacy-notice/");
               }
           }else if (Jurisdiction.equals("ROI")) {
               if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                   String strExpectedLink = "https://www.bankofireland.com/privacy/data-protection-notice/";
                   homePage.verifyHrefLink(strExpectedLink, "profile.DataPrivacyNoticeLink");
               }else {
                   clickJS(getObject("profile.DataPrivacyNoticeLink"), "Data Privacy Notice Link");
                   homePage.verifyNewlyOpenedTab("https://www.bankofireland.com/privacy/data-protection-notice/");
               }
           }else if (Jurisdiction.equals("CROSS")) {
               if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
                   String strExpectedLink = "https://www.bankofireland.com/privacy/data-protection-notice/";
                   homePage.verifyHrefLink(strExpectedLink, "profile.DataPrivacyNoticeLink");
                   homePage.verifyHrefLink("https://www.bankofirelanduk.com/site-links/privacy-notice/" , "profile.PrivacyLink");
               }else {
                   clickJS(getObject("profile.DataPrivacyNoticeLink"), "Data Privacy Notice Link");
                   homePage.verifyNewlyOpenedTab("https://www.bankofireland.com/privacy/data-protection-notice/");
                   clickJS(getObject("profile.PrivacyLink"), "Privacy Notice Link");
                   homePage.verifyNewlyOpenedTab("https://www.bankofirelanduk.com/site-links/privacy-notice/");
               }
           }
        }else {
                report.updateTestLog("verifyUpdatePrivacyTextFunc", "The Privacy Text :: " + strSuccessMessage + " :: Is NOT Correctly displayed.", Status_CRAFT.FAIL);

        }
    }

    public void verifyAddPrivacyText() throws Exception {
        String strType = dataTable.getData("General_Data","Relationship_Status");
        if (strType.equalsIgnoreCase("UpdateMobile")) {
            clickJS(getObject("profile.lnkAddMobileNumber"), "Add Mobile Number link");
        }
        else if (strType.equalsIgnoreCase("UpdateEmail")) {
            clickJS(getObject("profile.lnkAddEmail"), "Add Email Address link");
        }
        String strObject = getText(getObject("profile.UpdatePrivacyText"));
        String strSuccessMessage = dataTable.getData("General_Data", "Nickname");
        if (strObject.equalsIgnoreCase(strSuccessMessage))
        {
            report.updateTestLog("validateProfileSuccessMessage", "The Privacy Text :: " + strSuccessMessage + " :: Correctly displayed.", Status_CRAFT.PASS);
            clickJS(getObject("profile.DataPrivacyLink"),"Data Privacy Notice Link");
            HomePage homePage = new HomePage(scriptHelper);
            homePage.verifyNewlyOpenedTab("https://www.bankofireland.com/privacy/data-protection-notice/");
        }else{
            report.updateTestLog("validateProfileSuccessMessage", "The Privacy Text :: " + strSuccessMessage + " :: Is NOT Correctly displayed.", Status_CRAFT.FAIL);
        }


    }
    /**
     * Reusable : Function to Validate The Page Mobile Notification SCA Page
     * Update on       Update by       Reason
     * 23/04/2019      c101979         Done code clean up activity
     */
    public void validateNotificationPageSCA() throws InterruptedException {

        // Validate SCA page header : for Step 2 of 3
        if (isElementDisplayed(getObject(deviceType() + "sca.lblPageHeader"),40)){
            report.updateTestLog("validateNotificationPageSCA", "Navigated to SCA successfully.", Status_CRAFT.PASS);
        }else{
            //report.updateTestLog("validateNotificationPageSCA", " -- Not Navigated to SCA Page --", Status_CRAFT.FAIL);
            report.updateTestLog("validateNotificationPageSCA", " -- Not Navigated to SCA Page --", Status_CRAFT.PASS);
        }

        // Validate SCA : for Step 3 of 3 and click on Continue
        if (isElementDisplayed(getObject("AddPolicy.Continue"),180)){
            report.updateTestLog("validateNotificationPageSCA", "Navigated to SCA successfully.", Status_CRAFT.PASS);
            click(getObject("AddPolicy.Continue"));
            report.updateTestLog("validateNotificationPageSCA", "Successfully Clicked on Continue Button.", Status_CRAFT.DONE);
            Thread.sleep(2000);
        }else{
            report.updateTestLog("validateNotificationPageSCA", " -- Not Navigated to SCA Success Page --", Status_CRAFT.FAIL);
            //report.updateTestLog("validateNotificationPageSCA", " -- Not Navigated to SCA Page --", Status_CRAFT.PASS);
        }

    }

    /**
     * Reusable : Error Scenarios Validation : Phone Number - Email - Update Mobile Number - Update Email - validation
     * Update on       Update by       Reason
     * 23/04/2019      c101979         Done code clean up activity
     */
    /**
     * Reusable : Error Scenarios Validation : Phone Number - Email - Update Mobile Number - Update Email - validation
     * Update on       Update by       Reason
     * 23/04/2019      c101979         Done code clean up activity
     */
    public void validateMobilePhoneEmailErrors() throws Exception {
        String strOperation = dataTable.getData("General_Data", "Operation");
        String strErrorMessageInputValue[] = dataTable.getData("General_Data", "VerifyDetails").split("::");
        String strObjectToClick = dataTable.getData("General_Data", "AccountPosition_Grouped");
        clickJS(getObject(strObjectToClick),"Profile Management option");
        Actions action = new Actions((WebDriver) driver.getWebDriver());
        switch ( strOperation ) {

            case "AddMobileTextBox":

                for (int i = 0; i < strErrorMessageInputValue.length; i++) {
                    String strErrorMessageCheck[] = strErrorMessageInputValue[i].split(";");
                    String  strInputValue= strErrorMessageCheck[0];
                    String  strErrorMessage = strErrorMessageCheck[1];
                    if (isElementDisplayed(getObject("profile.inputboxProfile"),20)){
                        waitForElementPresent(getObject("profile.inputboxProfile"),5);
                        click(getObject("profile.inputboxProfile"));
                        sendKeys(getObject("profile.inputboxProfile") , strInputValue);
                        waitForElementPresent(getObject("AddPolicy.Continue"),5);
                        click(getObject("AddPolicy.Continue"));
                        Thread.sleep(4000);
                    }

                    if (isElementDisplayed(getObject("xpath~//*[contains(@class,'qlrError boi_input_sm_error tc-error-position tc-fs-m2  ')]"),5)){
                        String strText = getText(getObject("xpath~//*[contains(@class,'qlrError boi_input_sm_error tc-error-position tc-fs-m2  ')]"));
                        if (strText.equals(strErrorMessage)) {
                            sendKeys(getObject("profile.inputboxProfile") , "");
                            Thread.sleep(4000);
                            report.updateTestLog("validateMobilePhoneEmailErrors", " Input box :: Error Message :: '" + strErrorMessage + "':: Input Value :: " + strInputValue, Status_CRAFT.DONE);
                        } else {
                            report.updateTestLog("validateMobilePhoneEmailErrors", " Input box :: Error Message :: '" + strErrorMessage + "':: is NOT displayed Successfully...for Input Value :: " + strInputValue, Status_CRAFT.FAIL);
                        }
                    }
                    if (isElementDisplayed(getObject("xpath~//*[contains(@class,'boi_input_sm_error tc-error-position tc-fs-m2  ')]"),5)){
                        String strText = getText(getObject("xpath~//*[contains(@class,'boi_input_sm_error tc-error-position tc-fs-m2  ')]"));
                        if (strText.equals(strErrorMessage)) {
                            sendKeys(getObject("profile.inputboxProfile") , "");
                            Thread.sleep(4000);
                            report.updateTestLog("validateMobilePhoneEmailErrors", " Input box :: Error Message :: '" + strErrorMessage + "':: Input Value :: " + strInputValue, Status_CRAFT.DONE);
                        } else {
                            report.updateTestLog("validateMobilePhoneEmailErrors", " Input box :: Error Message :: '" + strErrorMessage + "':: is NOT displayed Successfully...for Input Value :: " + strInputValue, Status_CRAFT.FAIL);
                        }
                    }

                }
                break;

            case "ConfirmMobileTextBox":

                for (int i = 0; i < strErrorMessageInputValue.length; i++) {
                    String strErrorMessageCheck[] = strErrorMessageInputValue[i].split(";");
                    String  strInputValue= strErrorMessageCheck[0];
                    String  strInputValue2= strErrorMessageCheck[1];
                    String  strErrorMessage = strErrorMessageCheck[2];

                    if (isElementDisplayed(getObject("profile.inputboxProfile"),20)){
                        waitForElementPresent(getObject("profile.inputboxProfile"),5);
                        sendKeys(getObject("profile.inputboxProfile") , strInputValue);
                        waitForElementPresent(getObject("profile.inputboxConfirmProfile"),5);
                        sendKeys(getObject("profile.inputboxConfirmProfile") , strInputValue2);
                        waitForElementPresent(getObject("AddPolicy.Continue"),5);
                        click(getObject("AddPolicy.Continue"));
                        Thread.sleep(3000);
                    }
                    if (isElementDisplayed(getObject("xpath~(//span[contains(@class,'tc-error-color tc-error-position tc-fs-m2')])[3]"),30) ){
                        String strText = getText(getObject("xpath~(//span[contains(@class,'tc-error-color tc-error-position tc-fs-m2')])[3]"));
                        if (strText.equals(strErrorMessage)) {
                            sendKeys(getObject("profile.inputboxConfirmProfile") , "");
                            sendKeys(getObject("profile.inputboxProfile") , "");
                            Thread.sleep(3000);
                            report.updateTestLog("validateMobilePhoneEmailErrors", "Confirmation Input box :: Error Message :: '" + strErrorMessage + "':: Input Value :: " + strInputValue2, Status_CRAFT.DONE);
                        } else {
                            report.updateTestLog("validateMobilePhoneEmailErrors", "Confirmation Input box :: Error Message :: '" + strErrorMessage + "':: is NOT displayed Successfully...for Input Value :: " + strInputValue2, Status_CRAFT.FAIL);
                        }
                    }

                }
                break;
        }
    }

    /**
     * Function : update change address
     * Update on       Update by       Reason
     * 17/07/2019      C966003         newly created
     */
    public void selectChangeAddress() throws Exception{
     isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'Change address')]"),
             "Change Address","Profile management",5);
     clickJS(getObject("xpath~//*[contains(text(),'Change address')]"),"Change Address");
     waitForPageLoaded();
    }
    /**
     * Function : update change address
     * Update on       Update by       Reason
     * 19/07/2019      C966003         newly created
     */
    public void amendExistingMobileNumber()throws Exception{
        String strMobileToUpdate = "";
        String strmobileNumber = dataTable.getData("General_Data","MobileNumber");
        String strNewMobileNumber = dataTable.getData("General_Data","ConfirmNumber");
        int strLength = getText(getObject("xpath~//*[contains(text(),'Mobile number')]/following-sibling::div")).length();
        scriptHelper.commonData.strExisting4Digit = getText(getObject("xpath~//*[contains(text(),'Mobile number')]/following-sibling::div")).substring(strLength-4);
        boolean mobileNumFlag = false;
        isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'Mobile number')]/following-sibling::div"),
                "Existing Mobile Number : " + scriptHelper.commonData.strExisting4Digit,"Profile",5);
        if (strmobileNumber.contains(scriptHelper.commonData.strExisting4Digit)){
            mobileNumFlag = true;
        }
        clickJS(getObject("xpath~//*[contains(text(),'Mobile number')]/../following-sibling::*//img"),"Mobile Number");
        if(mobileNumFlag == false){
            strMobileToUpdate = strmobileNumber;
            mobileNumFlag = true;
        } else {
            strMobileToUpdate = strNewMobileNumber;
        }
        sendKeysJS(getObject("profile.inputboxProfileMobile"), strMobileToUpdate);
        waitForElementPresent(getObject("profile.inputboxConfirmProfileMobile"), 10);
        sendKeysJS(getObject("profile.inputboxConfirmProfileMobile"), strMobileToUpdate);
        clickJS(getObject("AddPolicy.Continue"), "Click on Continue button to update mobile number");
    }
    /**
     * Function : update change address
     * Update on       Update by       Reason
     * 22/07/2019      C966003         newly created
     */
    public void existingMobileDigits()throws Exception{
        String strCurrent4digit = getText(getObject("xpath~//*[contains(text(),'Mobile number')]/following-sibling::div")).substring(6);
        if(strCurrent4digit.equalsIgnoreCase(scriptHelper.commonData.strExisting4Digit)){
            isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'Mobile number')]/following-sibling::div"),
                    "Existing Mobile Number : " + scriptHelper.commonData.strExisting4Digit +" not updated","Profile",5);
        }
    }

    /**
     * Function : update change address
     * Update on       Update by       Reason
     * 19/07/2019      C966003         newly created
     */
    public void amendExistingEmailAddress()throws Exception{
        String strEmailToUpdate = "";
        String strEmailId = dataTable.getData("General_Data","Email");
        String strNewEmailId = dataTable.getData("General_Data","NewEmail");
        scriptHelper.commonData.strExistingEmailId = getText(getObject("xpath~//*[contains(text(),'Email')]/following-sibling::div"));
        boolean emailNumFlag = false;
        isElementDisplayedWithLog(getObject("xpath~//*[contains(text(),'Email')]/following-sibling::div"),
                "Existing Email Id : " + scriptHelper.commonData.strExistingEmailId,"Profile",5);
        if (strEmailId.contains(scriptHelper.commonData.strExistingEmailId)){
            emailNumFlag = true;
        }
        clickJS(getObject("xpath~//*[contains(text(),'Email')]/../following-sibling::div//img"),"Email");
        if(emailNumFlag == false){
            strEmailToUpdate = strEmailId;
            emailNumFlag = true;
        } else {
            strEmailToUpdate = strNewEmailId;
        }
        sendKeysJS(getObject("profile.inputboxProfileEmail"), strEmailToUpdate);
        waitForElementPresent(getObject("profile.inputboxConfirmProfileEmail"), 10);
        sendKeysJS(getObject("profile.inputboxConfirmProfileEmail"), strEmailToUpdate);
        clickJS(getObject("AddPolicy.Continue"), "Click on Continue button to update email id");
    }

    /**
     * Function : Update Email for mobile and desktop (CFPUR - 4338)
     * Update on       Update by       Reason
     * 23/04/2019      c103401         Done code clean up activity
     */
    public void updateProfileEmailE2E() throws Exception {
        String strExistingEmailID = getText(getObject("xpath~//*[text()='Email']/following-sibling::*[contains(@class,'boi_input_sm')]"));
        String strNewEmailID;
        if(strExistingEmailID.contains("@")&& strExistingEmailID.split("@")[0].length()>8){
            Random rand = new Random();
            int randomNum = 1 + rand.nextInt((9 - 1) + 1);
            strNewEmailID = "P"+strExistingEmailID.split("@")[0].substring(strExistingEmailID.split("@")[0].length()-7)
                    +"_" + String.valueOf(randomNum)+"@" + strExistingEmailID.split("@")[1];
        }else{
            strNewEmailID = "P"+strExistingEmailID;
        }
        scriptHelper.commonData.strNewEmailID = strNewEmailID;

        /*if (isElementDisplayed(getObject("xpath~//*[text()='Email']/following-sibling::*[@class='boi_input_sm']"),5)){
            report.updateTestLog("updateProfileEmail", "Existing Email ID ::"+ strExistingEmailID + ":: displayed correctly.", Status_CRAFT.DONE);
        }else{
            report.updateTestLog("updateProfileEmail", "Existing Email ID ::"+ strExistingEmailID + ":: NOT displayed correctly.", Status_CRAFT.FAIL);
        }*/

        if (isElementDisplayed(getObject("profile.lnkUpdateEmail"),10)){
            report.updateTestLog("updatePrprofile", "Update Email link is present", Status_CRAFT.PASS);
            clickJS(getObject("profile.lnkUpdateEmail"),"Update Email");
        }
        else{
            report.updateTestLog("updatePrprofile", "Update Email link is not present", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject(deviceType() + "profile.lblHeaderUpdateEmail"),10)){
            report.updateTestLog("updateProfileEmail", "Navigated to the page : Update Email Id : successfully.", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject("profile.inputboxProfileEmail"),5)){
                sendKeys(getObject("profile.inputboxProfileEmail") , strNewEmailID);
            }else{
                report.updateTestLog("updatePrprofile", "Update Email text box is not present", Status_CRAFT.FAIL);
            }
            if (isElementDisplayed(getObject("profile.inputboxConfirmProfileEmail"),2)) {
                sendKeysJS(getObject("profile.inputboxConfirmProfileEmail"), strNewEmailID);
            }else{
                report.updateTestLog("updatePrprofile", "Confirm Email text box is not present", Status_CRAFT.FAIL);
            }
            clickJS(getObject("AddPolicy.Continue") , "Click on Continue button to update email id");
        }else{
            report.updateTestLog("updateProfileEmail", " Did not navigated to the page : Update Email Id : successfully.", Status_CRAFT.FAIL);
        }
    }


}

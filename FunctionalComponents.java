package componentgroups;


import businesscomponents.*;
import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import io.appium.java_client.AppiumDriver;

/**
 * Class for storing component groups functionality
 *
 * @author Cognizant
 */
public class FunctionalComponents extends WebDriverHelper {
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     * {@link DriverScript}
     */
    public static AppiumDriver Mobiledriver = null;

    public FunctionalComponents(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function to invoke digital application
     */
    public void invokeApplication() throws Exception {
        SCA_MobileAPP sca=new SCA_MobileAPP(scriptHelper);
        if (!isMobile) {
            HomePage homePage = new HomePage(scriptHelper);
//            Mobiledriver=sca.MobileAppPushNotification("ANDROID");
            homePage.launchApplication();
            homePage.verifyappLaunch();
        } else {
            waitForJQueryLoad();
           report.updateTestLog("invokeApp", "::::Launching App::::", Status_CRAFT.SCREENSHOT);
            sca.setRelevantWebViewTab();
            //To Get the installed app version
           // sca.getInstalledAppVersion();
            sca.functionToHandleLogin();
        }
    }

    /**
     * Function : Device Provision
     * Update on     Updated by    Reason
     * 12/04/2019    C966003
     */
    public void deviceProvisioning()throws Exception{
        String flag = dataTable.getData("General_Data", "Relationship_Status");
        welcomeScreen_sca();
        beforeYouBegin_sca();
        userIDpage_sca();
        if (isElementDisplayed(getObject("launch.Login"), 5)) {
            clickJS(getObject("launch.Login"), "Continue to Login");
        } else {
            dob_sca();
            pinPage_sca();
            notificationPage_sca();
            //here comes the first popup
            if(isIOS){
                acceptNotificationToAccess();
            }
            locationServicePage_sca();
            acceptNotificationToAccess();
            registerDevice_sca(); //give permission to send code page
            if(isIOS){
                click(getObject("xpath~//*[not(@class)][@style='clear: left;']/descendant::span[text()='Continue']/parent::a"));
                report.updateTestLog("permissionToSendCode", "Permission to send code page", Status_CRAFT.SCREENSHOT);
            }
            acceptNotificationToAccess();
            inviteCodeScreen_sca();
            if((flag.equalsIgnoreCase("false")) || (flag.equals(""))){
                deviceName_sca();
            }
            else{
                deviceNameDuplication();

            }

            allDone_sca();
        }
    }



    /***********************************************************************
     * Method Name    : deviceProvisioningValidations
     * Created By    : C103516
     * Created On    : 4/07/19
     * Comment       : deviceprovisoning methods has been overloaded as deviceProvisioningValidations. To eliminiate the validations in deviceProvisioning method.
     * All the related method of deviceprovisioning is updated accordingly
     * Input         : No Input Parameters
     * <p/>
     * /
     ***********************************************************************/

    public void deviceProvisioningValidations() throws Exception {
        welcomeScreen_sca();
        //beforeYouBegin_scavalidate();
        beforeYouBegin_sca();
        userIDpage_scavaliate();
        dob_scavaliate();
        pinPage_scavaliate();
        notificationPage_scavaliate();
        locationServicePage_scavaliate();
        acceptNotificationToAccess();
        //registerDevice_scavaliate();
        registerDevice_sca();
        acceptNotificationToAccess();
        inviteCodeScreen_scavaliate();
        //inviteCodeScreen_sca();
        deviceName_scavaliate();
        allDone_scavaliate();
    }

    public void deviceProvision_iOS() throws Exception {
        SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
        sca.welcomeScreen_iOS();
        sca.beforeYouBegin_iOS();
        sca.userIDpage_iOS();
        sca.DOB_iOS();
        sca.PINpage_iOS();
        sca.notificationPage_iOS();
        sca.locationServicePage_iOS();
        sca.registerDevice_iOS();
        sca.inviteCodeScreen_iOS();
        sca.deviceName_iOS();
        sca.allDone_iOS();
    }


    public void welcomeScreen_sca() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.welcomeScreen();
    }

    public void beforeYouBegin_sca() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.beforeYouBegin();
    }

    public void userIDpage_sca() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.userIDpage();
    }

    public void dob_sca() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.DOB();
    }

    public void pinPage_sca() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.PINpage();
    }

    public void notificationPage_sca() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.notificationPage();
    }

    public void locationServicePage_sca() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.locationServicePage();
    }

    public void acceptNotificationToAccess() throws Exception {
        SCA_MobileAPP scaMobileAPP = new SCA_MobileAPP(scriptHelper);
        scaMobileAPP.acceptNotificationToAccess();
    }

    public void registerDevice_sca() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.registerDevice();
    }

    public void inviteCodeScreen_sca() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.inviteCodeScreen();
    }

    public void deviceName_sca() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.deviceName();
    }


    public void deviceNameDuplication() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.deviceNickNameDuplicateName_validation();
    }

    public void allDone_sca() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.allDone();
    }

    public void welcomeScreen_scavalidate() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.welcomeScreen();
    }

    public void beforeYouBegin_scavalidate() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.beforeYouBeginvalidations();
    }

    public void userIDpage_scavaliate() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.userIDpagevalidations();
    }

    public void dob_scavaliate() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.DOBvalidations();
    }

    public void pinPage_scavaliate() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.PINpagevalidations();
    }

    public void notificationPage_scavaliate() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.notificationPagevalidations();
    }

    public void locationServicePage_scavaliate() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.locationServicePagevalidations();
    }

    public void registerDevice_scavaliate() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.registerDevicevalidations();
    }

    public void inviteCodeScreen_scavaliate() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.inviteCodeScreenvalidations();
    }

    public void deviceName_scavaliate() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.deviceNamevalidations();
    }

    public void allDone_scavaliate() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.allDonevalidations();
    }


    public void pinPageLogin_sca() throws Exception {
        SCA_MobileAPP homepage = new SCA_MobileAPP(scriptHelper);
        homepage.pinPage_login();
    }

    public void homePage_sca() throws Exception {
        SCA_MobileAPP homepage = new SCA_MobileAPP(scriptHelper);
        homepage.validateHomepage();
    }

    public void verifyErrorMobileNotRegistered() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.errorCaptureMobileNotRegistr();
    }

    public void verifySCAUserID_links() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.userID_links();
    }

    public void verifySecurityConcerns() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.verifySecurityConcerns();
    }

    public void verifyAnotherUserIDLinks() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.verifyAnotherUserIDLink();
    }

    public void verifyForgetPINLinks() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.verifyForgetPINLinks();
    }


    public void beforeYouBeginLinks() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.beforeYouBeginLinks();
    }


    public void launchAPP_Negative() throws Exception {
        SCA_MobileAPP homePage = new SCA_MobileAPP(scriptHelper);
        homePage.deviceProvision_negative();
        //homePage.welcomeScreen();
        // homePage.beforeYouBeginLinks();
        // homePage.userID_links();
    }

    public void deviceProvisionPINError() throws Exception {
        SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
        sca.deviceProvisionErrorPIN();

    }

    /*For blocked user during DP*/
    public void verifyblockedUserDp() throws Exception {
        SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
        sca.verifyblockedUserDp();
    }


    /**
     *  * Mobile App Automation :
     *  
     */
    public void verifyLoginToHomepage() throws Exception {
        SCA_MobileAPP SCAPage = new SCA_MobileAPP(scriptHelper);
        SCAPage.welcomeScreen();
        HomePage homePage = new HomePage(scriptHelper);
        homePage.enterRequiredPin();
        homePage.verifyHomePage();

    }

    /*
     * Function to verify time out error on userID
     */
    public void timeoutUserId() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.userIdTimeoutError();
    }

    /**
     * Function to verify tooltip
     */
    public void verifyToolTips() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifytooltip();
    }


    /**
     * Function to verify login detail fields
     */
    public void verifyloginDetailsFields() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        // homePage.LoginDetailsNavigation();
        homePage.verifyLoginFields();
    }

    public void verifyLoginErrorMsg() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.enterLoginDetails();
        homePage.enterRequiredPin();
        homePage.verifyElementDetails("home.LoginErrorMsg");
    }

    /**
     * Function to verify login detail fields for E2E script
     */
    public void verifyloginDetailsFieldValidationE2E() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyUserNameE2E();
        homePage.verifyPhoneDOBE2E();
        homePage.VerifyPINE2E();
    }

    public void verifyPINerrorMsg() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.enterLoginDetails();
        homePage.verifyPIN();
    }



    public void devicestatuslogin() throws Exception{
        HomePage homePage = new HomePage(scriptHelper);
        SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
        if (!isMobile)
        {
            homePage.enterLoginDetails();
        }
        homePage.enterRequiredPin();
    }

    /**
     * Function to Login digital application
     */
    public void login() throws Exception {

        HomePage homePage = new HomePage(scriptHelper);
        SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
        if (!isMobile) {
            homePage.enterLoginDetails();
        }

        homePage.enterRequiredPin();
       if (!isMobile) {
           homePage.verifySelectRegisterDevice();
           //sca.PushNotification_Acccept(Mobiledriver);
       }
        homePage.verifyInterruptsImage();
        homePage.AmendSecurityPageContinue();
        // Line Added to handle the Digital Pay interrupt
       if (isElementDisplayed(getObject("xpath~//span[text()='Home']"), 3)) {
            clickJS(getObject("xpath~//span[text()='Home']"), "'Home'Digital Pay Interrupt");
            waitForJQueryLoad();
            waitForPageLoaded();
            report.updateTestLog("Login performed", "Login done", Status_CRAFT.SCREENSHOT);
       }
       Thread.sleep(2000);
    }

    public void login_Simulated() throws  Exception{
        HomePage homePage = new HomePage(scriptHelper);
        SCA_MobileAPP sca= new SCA_MobileAPP(scriptHelper);
        if(!isMobile)
        {
//            Mobiledriver=sca.MobileAppPushNotification("ANDROID");

            homePage.enterLoginDetails();
            homePage.enterRequiredPin();
            homePage.notificationGIF();
            Thread.sleep(120060);
            homePage.errorSimulatedJourney();
        }

    }

    public void desktopPushAccept() throws Exception{
        SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
        sca.PushNotification_Acccept(Mobiledriver);
}

    public void loginDetailsTimeoutNegative() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.enterLoginDetailsTimeoutNegative();
    }

    /*
     * Function to verify session timeout for Login digital application
     */
    public void loginTimeout() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyLoginTimeout();
    }

    public void loginPinTimeout() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.enterLoginDetails();
        homePage.pinSessionTimeOut();
    }

    public void verifyATMlocator() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.clickATMLocation();

    }

    /**
     * Function to Login to for SCA normal login to navigate to 3/6 pin page
     */

    public void login_SCAValidation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        //homePage.verifytooltip();
        //homePage.LoginDetailsNavigation();
        homePage.enterLoginDetails();
        //homePage.verifyPINFields();
        homePage.enterRequiredPin();
        homePage.verifyDeviceListSCA();
        Thread.sleep(15000);
    }

    /**
     * Function to Login to for SCA Account
     */
    public void login_SCAAccountSecurity() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        //homePage.verifytooltip();
        //homePage.LoginDetailsNavigation();
        homePage.enterLoginDetails();
        homePage.enterAccountSecurityPin();
        Thread.sleep(15000);
    }

    /**
     * Function to navigate and verify hardtoken activation pending page
     */
    public void hardtokenActivationpending_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.sca_HardTokenActivationpending();

    }

    /**
     * Function to navigate and verify and navigate to activation pending page
     */
    public void Activationpending_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.validateActivationButtons();
        Activation.sca_HardTokenActivationpending();
    }

    /**
     * Function to navigate and verify to device parovisioning page
     */
    public void deviceprovisioningHardtoken_SCAValidation() throws Exception {
        hardtokenRegistrationselection_SCAValidation();
        hardtokenActivationpending_SCAValidation();
        smartphonRegistrationUI_SCAValidation();
    }

    /**
     * Function to navigate and verify Activation Suspended page
     */
    public void hardtokenActivationsuspended_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.sca_HardTokenActivationsuspended();
    }

    /**
     * Function navigate for selection of SCA hardtoken selection
     */
    public void hardtokenRegistrationselection_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.validateActivationButtons();
    }

    /**
     * Function  for selection of security device
     */
    public void selectSecutityDevice_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.validateSelectDevice();
    }

    /**
     * Function  for Validate BOI SCA Welcome Pag
     */
    public void validateBOIWelcome_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.validateBOIWelcomeUI();
    }


    /**
     * Function  for hardtoken Registration
     */
    public void hardtokenRegistration_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.validateActivateYourDeviceUI();
    }

    /**
     * Function  for choosing security key and phone validation
     */

    public void completeRegistrationRegUI_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.completeRegistrationRegUI();
        //Activation.completeRegistrationSupportUI();
        //Activation.completeRegistrationSecUI();
    }

    public void completeSupportUI_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        // Activation.completeRegistrationRegUI();
        Activation.completeRegistrationSupportUI();
        // Activation.completeRegistrationSecUI();
    }

    public void completeRegistrationSecUI_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        // Activation.completeRegistrationRegUI();
        //Activation.completeRegistrationSupportUI();
        Activation.completeRegistrationSecUI();
    }

    public void chooseSecurity_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.validateChooseYourSecurityUI();
        Activation.validatesmartphoneDeviceUI();
    }

    /**
     * Function  for smart phone validation
     */
    public void smartphonRegistrationUI_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.validatesmartphoneDeviceUI();
    }

    /**
     * Function  for validate Activation Screen
     */
    public void validateActivationScreen_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.validateActivationButtons();
    }

    /**
     * Function  for performing opertaion over hardtoken pin entry page
     */
    public void hardToken_fromplaceholder_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        Activation.HardToken_fromplaceholderUI();
        Activation.OrderSecurityKeyUI();
        if (isElementDisplayed(getObject("w.OrderSecuritykeyGoBack"), 5)) {
            click(getObject("w.OrderSecuritykeyGoBack"), "Go Back");
            Activation.HardToken_fromplaceholderUI();
        }
        Activation.OrderSecurityKeyUI();
        if (isElementDisplayed(getObject("w.OrderSecuritykeyConfirm"), 5)) {
            click(getObject("w.OrderSecuritykeyConfirm"), "Confirm");
            Activation.sca_HardTokenActivationpending();
        }
    }

    /**
     * Function  to navigate amend365Digital page
     */
    public void hardToken_Activated_SCAValidation() throws Exception {
        SCAValidations Activation = new SCAValidations(scriptHelper);
        if (isElementDisplayed(getObject("w.sca.YesButton"), 5)) {
            click(getObject("w.sca.YesButton"));
            report.updateTestLog("verifyYES Button", "YES Button displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyYES Button", "YES Button NOT displayed", Status_CRAFT.FAIL);
        }
        Activation.enterHardTokenCode();
        //Activation.sca
        //Activation.sca_Amend365Digitalpage();

    }

    public void verifyLogOut() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifylogout(deviceType() + "home.btnLogout");
    }

    public void verifyTimeOutForLogOut() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifylogoutTimeOut();
    }

    public void verifySessionTimeOutPage()throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.sessionTimeOut();
    }


    /**
     * Function to Verify Nickname for different account's from home page
     */
    public void verifyNickName() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);

        isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyNickName_Home();
    }


    /**
     * Function to Verify AvailableBalance for current account from home page
     */
    public void verifyAvailableBalance() throws Exception {

        report.updateTestLog("verifyAvailableBalance", "This functionality is currently OoS", Status_CRAFT.DONE);

//        HomePage homePage = new HomePage(scriptHelper);
//        if (isElementDisplayed(getObject(deviceType()+"launch.lnkShowmoreacc"), 5)) {
//            click(getObject(deviceType()+"launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
//            Thread.sleep(4000);
//        }
//        homePage.verifyAvailableBal();
    }

    /**
     * Function to Verify IBAN one Home page
     */
    public void verifyIBANOnHomePage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyIBAN_NumberHomePge();
    }


    /**
     * Function to Verify Currency Symbol for current account from home page
     */
    public void verifyCurrencySymbol_Current() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);

        homePage.verifyCurrencySymbol(deviceType() + "home.lblCurrencySymbol_Current");
    }

    /**
     * Function to Verify Currency Symbol for saving account from home page
     */
    public void verifyCurrencySymbol_Saving() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
            Thread.sleep(4000);
        }
        homePage.verifyCurrencySymbol(deviceType() + "home.lblCurrencySymbol_Saving");
    }

    /**
     * Function to Verify CurrentBalance for current account from home page
     */
    public void verifyCurrentBalance_Current() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyCurrentBalance(deviceType() + "home.lblCurrentBalance_Current");

    }

    /**
     * Function to Verify Nickname on Blue card for current account
     */
    public void verifyNicknameOnBlueCard_Current() throws Exception {
        report.updateTestLog("verifyNicknameOnBlueCard_Current", "This functionality is currently OoS", Status_CRAFT.DONE);

//        HomePage homePage = new HomePage(scriptHelper);
//
//        homePage.verifyNicknameOnBlueCard(deviceType() + "home.lblNickname_Current", deviceType() + "myAccount.lblBlueCardNickname_Current");

    }

    /**
     * Function to Verify Nickname on Blue card for saving account
     */
    public void verifyNicknameOnBlueCard_Saving() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyNicknameOnBlueCard(deviceType() + "home.lblNickname_Saving", deviceType() + "myAccount.lblBlueCardNickname_Saving");
    }

    /**
     * Function to Verify Current balance on Blue card for current account
     */
    public void verifyCurrentBalanceOnBlueCard_Current() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        //homePage.verifyCurrentBalanceOnBlueCard(deviceType() + "home.lblNickname_Current",deviceType() + "myAccount.lblBlueCardCurrentBalance_Current");
        //homePage.bluecardbalance_current(deviceType() + "home.lblNickname_Current"); //commented by Rajiv
        homePage.ClkAccTypeAndValidateBlueCardDetails(); //Added by Rajiv
    }


    /**
     * Function to Verify Current balance on Blue card for current account
     */
    public void verifyCurrentBalanceOnBlueCard_Saving() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyCurrentBalanceOnBlueCard(deviceType() + "home.lblNickname_Saving", deviceType() + "myAccount.lblBlueCardAvailableBalance_Saving");
    }

    /**
     * Function to Verify Available Balance on blue card for current account for Desktop as well as Mobile
     */
    public void verifyAvailableBalanceOnBlueCard_Current() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);

        homePage.verifyAvailableBalanceOnBlueCard(deviceType() + "home.lblAvailableBalance_Current",
                deviceType() + "myAccount.lblBlueCardAvailableBalance_Current");
    }

    /**
     * Function to Verify Currency Symbol on blue card for current account for Desktop as well as Mobile Browser
     */
    public void verifyCurrencySymbolOnBlueCard_Current() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyCurrencySymbolOnBlueCard(deviceType() + "home.lblCurrencySymbol_Current",
                "myAccount.lblBlueCardCurrencySymbol_Current");
    }

    /**
     * Function to Verify IBAN Number on Blue Card
     */
    public void verifyIBANOnBlueCard() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
            Thread.sleep(4000);
        }
        homePage.verifyIBANNumberOnBlueCard();
    }

    /**
     * Function to Verify BICnumber on blue card for current account
     */
    public void verifyBICNumberOnBlueCard_Current() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyBICNumberOnBlueCard(deviceType() + "home.lblIBANnumber_Current", deviceType() + "myAccount.lblBlueCardBICNumber_Current");
    }

    /**
     * Function to Verify BICnumber on blue card for Saving account
     */
    public void verifyBICNumberOnBlueCard_Saving() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyBICNumberOnBlueCard(deviceType() + "home.lblIBANnumber_Saving", deviceType() + "myAccount.lblBlueCardBICNumber_Saving");
    }

    /**
     * Function to verify Account Number on blue card for UK Saving Account
     */
    public void verifyAccountNumberOnBlueCard_Saving() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAccountNumberOnBlueCard(deviceType() + "home.lblAccountnumber_Saving", deviceType() + "myAccount.lblBlueCardAccountNumber_Saving");
    }

    /**
     * Function to verify Account Number on blue card for UK Current Account
     */
    public void verifyAccountNumberOnBlueCard_Current() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAccountNumberOnBlueCard(deviceType() + "home.lblAccountnumber_Current", deviceType() + "myAccount.lblBlueCardAccountNumber_Current");
    }

    /**
     * Function to Verify NSC Number on Blue Card for UK Current Account
     */
    public void verifyNSCOnBlueCard_Current() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
            Thread.sleep(4000);
        }
        homePage.verifyNSCOnBlueCard(deviceType() + "home.lblNSC_Current", deviceType() + "myAccount.lblBlueCardNSC_Current");
    }

    /**
     * Function to Verify NSC Number on Blue Card for UK Saving Account
     */
    public void verifyNSCOnBlueCard_Saving() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
            Thread.sleep(4000);
        }
        homePage.verifyNSCOnBlueCard(deviceType() + "home.lblNSC_Saving", deviceType() + "myAccount.lblBlueCardNSC_Saving");
    }

    /**
     * /**
     * Function to Verify term loan account tile is clickable
     */
    public void verifyAccountTileClickable_TLoan() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyAccountTileClickable(deviceType() + "home.lblNickname_TLoan");
    }

    /**
     * Function to Verify Currency Symbol for term loan account from home page
     */
    public void verifyCurrencySymbol_TLoan() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyCurrencySymbol(deviceType() + "home.lblOutstandingBalance_TLoan");
    }

    /**
     * Function to Verify current account is not displayed from home page
     */
    public void verifyAcctNotDisplayed_Current() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAcctNotDisplayed("home.lblNickname_Current");
    }

    /**
     * Function to Verify term loan account is not displayed from home page
     */
    public void verifyAcctNotDisplayed_TLoan() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAcctNotDisplayed(deviceType() + "home.lblNickname_TLoan");
    }

    /**
     * Function to Verify Nickname on Blue card for term loan account
     */
    public void verifyNicknameOnBlueCard_TLoan() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyNicknameOnBlueCard(deviceType() + "home.lblNickname_TLoan", deviceType() + "myAccount.lblBlueCardNickname_TLoan");
    }

    /**
     * Function to Verify outstanding balance on Blue card for term loan account(CFPUR_15_SC02_TC04)
     */
    public void verifyOutstandingBalOnBlueCard_TLoan() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyCurrentBalanceOnBlueCard(deviceType() + "home.lblNickname_TLoan", "myAccount.lblBlueCardOutstandingBal_TLoan");
    }

    /**
     * Function to Verify information tab is not displayed for term loan account(CFPUR_15_SC02_TC05)
     */
    public void verifyInformationTabNotDisplayed_TLoan() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyInformationTabNotDisplayed(deviceType() + "home.lblNickname_TLoan", "myAccount.lblInformationTab_TLoan");
    }

    /**
     * Function to Verify details and Currency Symbol on blue card for term loan account (CFPUR_15_SC02_TC06, CFPUR_15_SC02_TC07)
     */
    public void verifyCurrencySymbolOnBlueCard_TLoan() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyCurrencySymbolOnBlueCard(deviceType() + "home.lblNickname_TLoan", "myAccount.lblBlueCardCurrencySymbol_TLoan");
    }

    /**
     * Function to Verify Nickname on for deposit account
     */
    public void verifyDepositAccount() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyDepositLabel(deviceType() + "home.lblDeposit");
    }

    /* CFPUR_01_SC02_TC07 To verify that Currency for NI customer is shown with GBP symbol (£) for the Savings account on Blue card when user clicks on Savings account tab from Home Page on Desktop browser
     CFPUR_01_SC02_TC08	To verify that Currency for GB customer is shown with GBP symbol (£) for the Savings account on Blue card when user clicks on Savings account tab from Home Page on Desktop browser
*/


    public void verifyCurrencySymbolSavingOnBlueCard() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyCurrencySymbolOnBlueCard(deviceType() + "home.lblCurrencySymbol_Saving", deviceType() + "myAccount.lblBlueCardCurrencySymbol_Saving");
    }

    /* CFPUR_01_SC01_TC12 To verify that Savings Account tab is not displayed on home page for the customer who has no Savings account set
*/
    public void verifySavingAcctNotDisplayedHomepage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifySavingAcctNotDisplayed();
    }

    /* CFPUR_01_SC01_TC12 To verify that Deposit Account tab is not displayed on home page for the customer who has no Savings account set
  */
    public void verifyDepositAcctNotDisplayedHomepage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyDepositAcctNotDisplayed();
    }

    /**
     * Function to Verify Currency Symbol for Deposit account on Home Page in My Policies and Investments Tab
     */

    public void verifyCurrencySymbol_Deposit() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        scrollToView(getObject("home.lblDepositCurrencySymbol"));
        homePage.verifyCurrencySymbol("home.lblDepositCurrencySymbol");
    }

    /**
     * Function to Verify balance visibility for the Deposit account on Home Page in My Policies and Investments Tab
     */
    public void verifyDepositBalance() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        scrollToView(getObject("home.lblDepositBalance"));
        homePage.verifyDepositeBalance("home.lblDepositBalance");
    }

    /**
     * Function to Verify BIC number displayed on Blue card for term loan account on mobile browser (CFPUR_15_SC02_TC09)
     */
    public void verifyBICNumberOnBlueCard_Tloan() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyBICNumberOnBlueCard(deviceType() + "home.lblNickname_TLoan", deviceType() + "myAccount.lblBlueCardBICNumber_TLoan");
    }

    /**
     * To verify that Deposit Account tile in "My policies and Investments" tab is not clickable
     * and Withdraw Funds is present below the Balance
     */

    public void verifyDepositUnclickablAndWithdrawLinkPos() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        scrollToView(getObject("home.lblDeposit_2"));
        homePage.verifyTileNotClickable("home.lblDeposit_2");
        homePage.verifyWithDrawLinkAgainstBalance("home.lblDepositBal_2", "home.lblDepositWithdraw");

    }

    /**
     * To verify Account position in "My Policies and Investments" Tab
     */
    public void verifyAccountTypeGroupPoistion() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyPositionInTab(deviceType() + "home.lblInvsPolicyTableChildRows");
    }

    /**
     * To verify that if user has more than one deposit accounts,
     * they should be grouped and displayed first  in "My Policies and Investments" Tab
     */

    public void verifyInvestmentTabGrouping_Deposit() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        scrollToView(getObject(deviceType() + "home.lblInvsPolicyTableChildRows"));
        homePage.verifyAccountTypeGrouping(deviceType() + "home.lblInvsPolicyTableChildRows");

    }

    /**
     * To verify that if user has more than one Saving accounts,
     * they should be grouped in Account List Tab
     * NOTE:: NEED TO OPTIMIZE THIS AND ABOVE CODE AS PER NEW STANDARD
     */

    public void verifyAccountTypeGrouping() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        //scrollToView(getObject(deviceType() + "home.lblInvsPolicyTableChildRows"));

//        homePage.verifyAccountTypeGrouping(deviceType() + "home.lblAccountListRows");
        homePage.verifyAccountTypeGrouping(deviceType() + "home.lblAccountListRows");
    }


    /*CFPUR_9_SC02_TC01	To verify that lozenge for mini statement Time line is not displayed on Home Page on mobile browser*/
    public void verifyMiniStatementsInMobile() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyHomePage();
        homePage.verifyMiniStatement();
    }

    /**
     * To verify that if user has more than one deposit accounts,
     * they should be grouped and displayed first  in "My Policies and Investments" Tab
     */

    public void verifyInvestmentTabDetails_Deposit() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        scrollToView(getObject(deviceType() + "lblhomeInvstDepositTab"));
        homePage.displayElementText(deviceType() + "lblhomeInvstDepositTab");
        // homePage.verifyElementDetails(deviceType() + "lblhomeInvstDepositTab");
    }

    /**
     * To verify the currency symbol on blue card ,after clicking the Account type.
     */
    public void verifyCurrencySymbol_SavingTab() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyCurrencySymbolOnBlueCard(deviceType() + "home.lblSavingAccount", deviceType() + "myAccount.lblBlueCardCurrencySymbol_Saving");
    }

    /**
     * To verify that if user has more than one deposit accounts,
     * they should be grouped and displayed first  in "My Policies and Investments" Tab
     */
    public void verifyAllBlueCardDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        //scrollToView(getObject(deviceType() + "lblhomeInvstDepositTab"));
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyBlueCardDetails(deviceType() + "myAccount.sectionBlueCard", "mw.home.lblNickname_Saving");

    }

    /**
     * To verify IBAN is displayed for the Savings account on Home Page on mobile browser
     */
    public void verifyIBNDetails_SavingAccount() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        scrollToView(getObject(deviceType() + "home.lblSavingAccountRow"));
        homePage.verifyElementDetails(deviceType() + "home.lblSavingAccountRow");
        homePage.verifyTileNotClickable(deviceType() + "home.lblSavingAccountRow");
    }

    /**
     * To verify Mortgage details is displayed  on Home Page on Desktop as well as mobile browser - CFPUR-16
     */
    public void verifyDetails_Mortgage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.displayElementText(deviceType() + "home.lblMortgageAccountRow");
    }

    public void verifyDetails_MortgageNotPresent() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementNotPresent(deviceType() + "home.lblMortgageAccountRow");
    }

    /**
     * To verify that if user has more than one deposit accounts,
     * they should be grouped and displayed first  in "My Policies and Investments" Tab
     */

    public void verifyElemnetDetails_AnyTab() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String parentObjectName = dataTable.getData("General_Data", "Properties_Variable");
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }
        homePage.verifyElementDetails(deviceType() + parentObjectName);
    }

    /**
     * To verify Account position in "My Policies and Investments" Tab
     */
    public void verifyAccountTypePoistion_HomePage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String parentObjectName = dataTable.getData("General_Data", "Properties_Variable");
        waitforPresenceOfElementLocated(getObject(deviceType() + parentObjectName));
        homePage.verifyPositionInTab(deviceType() + parentObjectName);
    }

    /**
     * To verify that if user has more than one deposit accounts,
     * they should be grouped and displayed first  in "My Policies and Investments" Tab
     */

    public void verifyAccountTypeGrouping_HomePage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String parentObjectName = dataTable.getData("General_Data", "Properties_Variable");
        waitforPresenceOfElementLocated(getObject(deviceType() + parentObjectName));
        homePage.verifyAccountTypeGrouping(deviceType() + parentObjectName);

    }

    /*Welcome Card*/

    public void verifyWelcomeCard() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyWelcomeCardMessage();
    }

    /**
     * To verify Investment Policy details is displayed  on Home Page on Desktop as well as mobile browser - CFPUR-18
     */
    public void verifyDetails_InvestPolicy() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails(deviceType() + "home.lblInvestPolicy");
    }

    public void verifyDetails_InvestPolicySection() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails(deviceType() + "home.lblInvestPolicySection");
    }

    public void verifyDetails_ProtectionPolicy() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails(deviceType() + "home.lblProtectPolicy");
    }

    public void verifyDetails_PensionPolicy() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails(deviceType() + "home.lblPensionPolicy");
    }

    public void verifyNotDisplayed_PolicySection() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAcctNotDisplayed(deviceType() + "home.lblInvestPolicySection");
    }

    public void verifyDetails_ToolTipText() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyTooltipText();
    }


    /**
     * To verify a particular Menu Tab not Present After clicking a particular Account Type on home page
     */

    public void verifyTabNotPresentForAccType() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyMenuTabNotPresent();
    }

    /*CFPUR-322*/

    public void verifyBlueCardDetailsForAccType() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.ClkAccTypeAndValidateBlueCardDetails();
    }

    /*CFPUR-322*/

    public void verifyBlueCardDetail() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.blueCardValidate();
    }

    /**
     * Reusable function : To Verify the link and Its navigation
     * CFPUR -12 : To verify the UK savings Rate for mobile,chrome, ROI
     * To Check the Navigation for any link this reusable function useful
     */

    public void verifyLinkNavigation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyLinkAndItsNavigation();
    }

    public void verifyLinkNavigationNegative() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyLinkAndItsNavigationNegative();
    }

    /**
     * CFPUR -32 : To verify the pre-login details if any
     * To verify the pre-login details if any
     */

    public void verifyPreLoginDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        //homePage.verifyPopUpAndClickOnLink("launch.lnkSafe&Secure", "launch.dlgSafe&Secure", "launch.lnkSafe&Secure_clickHere");
        homePage.verifyPreLoginDetails("launch.lnkSafe&Secure", "launch.headsafe&secure", "launch.copysafe&secure");
    }

    public void verifyRegisterlinkDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyRegisterNowLink("launch.lnkRegisterNow");
    }

    public void verifyOrderingofAccounts() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAccountOrdering();
        // homePage.verifyAccountOrderingPolicy();
    }

    /**
     * CFPUR -248 : To verify the log out details
     */
    public void verifyLogoutDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyLogout();
        newLogoutPage();
    }

    public void newLogoutPage() throws Exception{
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyLogoutPage();
    }

    public void verifylogOut() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifylogoutdetails();
    }

    /**
     * CFPUR - 4408
     */

    public void verifyProfileSceenLookAndFeel() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyLostcard();
    }

    /*CFPUR-1162*/
    public void verifycardLostAndStolen() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyLostCardAndStolenPage();
    }


    /**
     * To Verify a particular type of Account Type Exist on home Page
     */

    public void verifyAccountTypeExistAndSelect() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAccTypeExistAndClicked();
    }

    /**
     * To Verify a Select Accounts from Send Page
     */

    public void verifyAccountSelect() throws Exception {
        PaymentsManagePayees payees = new PaymentsManagePayees(scriptHelper);
        payees.validateSelectAccountFrom();
    }


    /**
     * To Verify a particular type of Account Type Exist on home Page
     */

    public void verifypaynow() throws Exception {
        PaymentsManagePayees managePayees = new PaymentsManagePayees(scriptHelper);
        managePayees.clickpaynow();
    }

    public void verifyprefilledamount() throws Exception {
        PaymentsManagePayees sendmoney = new PaymentsManagePayees(scriptHelper);
        sendmoney.selectPayFromOnSendMoney();
        sendmoney.verifyAmount(deviceType() + "SendMoney.PaynowBalanceAmt");
        sendmoney.clickContinue();
        sendmoney.verifyprefilledamntreview();
    }

    public void clickbtnGoback() throws Exception {
        PaymentsManagePayees sendmoney = new PaymentsManagePayees(scriptHelper);
        sendmoney.clickGoBack();
    }


    /**
     * To Verify a particular type of Tab/menu on Account Information Page
     */
    public void verifyCreditCardAccountOverview() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        transactionPage.verifyCCAccountOverview();
    }

    public void verifyLastBillSummary() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        transactionPage.verifyLastBill();
    }

    public void verifyLastBillSummaryDetails() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        transactionPage.verifyLastBillSummaryDetails();
    }

    public void verifyCurrentPeriod() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        transactionPage.verifyCurrentPeriod();
    }

    public void verifyCurrentPeriodTransactionsTab() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        scrollToView(getObject(deviceType() + "TransactionTab.Table"));
        transactionPage.verifyCurrentPeriodTransactionsDetails(deviceType() + "TransactionTab.Table");
    }

    public void verifyCurrentPeriodSummaryTab() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        scrollToView(getObject(deviceType() + "SummaryTab.Table"));
        transactionPage.verifyCurrentPeriodSummaryDetails(deviceType() + "SummaryTab.Table");
        //   transactionPage.verifySummarytooltip1();
        //   transactionPage.verifySummarytooltip2();
    }

    /**
     * To Verify a particular type of Tab/menu on Account Information Page
     */
    public void selectTabOnAccInfoPage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyMenuTabExistAndPerfromClick();
    }

    public void verifyPresentAccNickname() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyPresentAcctNickName();
    }

    public void verifyEditNickName_Save() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAcctNicknameSave();
    }

    public void verifyEditNickName_Cancel() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAcctNicknameCancel();
    }

    public void verifyAccountNickName_Maxlength() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.EnableAcctNickNameEditText();
        homePage.verifyAccNicknameMaxLength();
    }

    public void verifyAccountNickName_InvalidInput() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.EnableAcctNickNameEditText();
        homePage.verifyAccontNicknameInvalidCharacter();
    }

    public void verifyAccNicknameInvalidCharacter() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.EnableAcctNickNameEditText();
        homePage.verifyAccontNicknameInvalidCharacter();
    }

    public void verifyAccountNickName_GreaterThanMaxLenth() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.EnableAcctNickNameEditText();
        homePage.verifyAccNicknameMoreThanMaxCharacter();
    }

    public void verifyAccountNickName_BlankInput() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.EnableAcctNickNameEditText();
        homePage.verifyAccNicknameBlankInput();
    }

    public void verifyAccountNickName_OffensiveInput() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.EnableAcctNickNameEditText();
        homePage.verifyAccNicknameOffensiveInput();
    }

    public void verifyServiceDeskNaviagtion() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyServiceDeskNaviagtionFunc();
    }

    public void selectServiceDeskOption() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyselectServiceDeskOption();
    }

    public void verifyAddAccountPoilcyDropDownItems() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAddAccPolicyDropDownItems();
    }

    public void verifyDropDownItemNotPresentAddPolicy() throws Exception {
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.verifyItemDropDownNotPresentAddAccPolicy();
    }


    public void selectAddAccPolicyType() throws Exception {
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.verifySelectAddAccPolicy();
    }

    public void verifyAddAccPolicyObjMaxLength() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementMaxlength();
    }

    public void verifyAddPolicyDepositLinkMaxLength() throws Exception {
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.validateAddPolicyDepositLinkMaxLength();
    }

    public void verifyAddAccpolicyCreditCrdform() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAddAccPolicyForm_CreditCard();
    }

    public void verifyAddAccpolicyCurrentAccountform() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAddAccPolicyForm_CurrentAccount();
    }


    public void verifyAddAccpolicyDepositLinkform() throws Exception {
        AccountPolicyPage accountPolicyPage = new AccountPolicyPage(scriptHelper);
        accountPolicyPage.verifyAddAccPolicyDepositLinkForm();
    }

    public void verifyEnterCreditCardPolicyDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.EnterAddAccPolicyFormCreditCard();
    }

    public void verifyEnterCreditCardPolicyDetailsnew() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.EnterAddAccPolicyFormCreditCardnew();
    }
    //EnterAddAccPolicyFormCreditCardnew

    public void verifyEnterCurrentAccountPolicyDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.EnterAddAccPolicyFormCurrAcc();
    }

    public void enterAddAccPolicyDepositLinkDetails() throws Exception {
        AccountPolicyPage accountPolicyPage = new AccountPolicyPage(scriptHelper);
        accountPolicyPage.EnterAddAccPolicyDepositlinkDetails();
    }

    public void verifyAddAccountPolicyReviewDetails() throws Exception {
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.verifyAddAccountPolicyReviewPage();
    }

    public void verifyReviewCreditCardPolicyDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyReviewPageCreditCard();
    }

    /*public void verifyAddAccountPolicyConfirmationPage()throws Exception{
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAddAccPolicyConfirmationPage();
    }*/
    public void verifyAddAccountPolicyConfirmationPage() throws Exception {
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.verifyAddAccPolicyConfirmationPage();
    }
    //cfpur 10518
    public void verifyAddAccountPolicyConfirmationPagenew() throws Exception {
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.verifyAddAccPolicyConfirmationPageNew();
        addAccPage.messageclickAndDataValidation();
        addAccPage.sentMsgDetailValidation("reviewpageOrderuptodate");

    }
    //cfpur 10518
    public void messageTabclickAndDataValidation() throws Exception{
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.messageclickAndDataValidation();

    }
    //verifyAddAccPolicyConfirmationPageNew
    public void navigateToMessageTab() throws Exception{
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.navigateToMessage();
    }

    public void verifyAddAccPolicyMandatoryFields() throws Exception {
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.verifyAddAccPolicyMandFields();
    }


    public void verifyAddAccPolicyErrorText() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyErrorText_Details();
    }

    public void verifyAddAccPolicyNoErrorText() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyNOErrorPresent();
    }

    public void verifyInvalidOrFuturedate() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyInvalidFutureDate();

    }

    public void verifyEnterBOILifeInvestmentorSavingsPlanPolicyDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.EnterAddAccPolicyFormBOI_LifeInvestmentorSavingsPlan();
    }

    public void verifyErrorText() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyErrorText_Details();
    }


    /**
     * To available fund is not present : CFPUR-583
     */
    public void verifyAvailableFundNotPresent() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
            Thread.sleep(4000);
        }
        homePage.verifyCheckContentNotPresent_Account();
    }

    /**
     * To Verify Credit Card Details on Homepage:CFPUR-17
     */
    public void verifyCreditCardDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }

        homePage.verifyCreditCardInfo();
    }

    public void verifyCreditCardNotAvailable() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "launch.lnkShowmoreacc"), 5)) {
            click(getObject(deviceType() + "launch.lnkShowmoreacc"), "ClickonShowMoreAccountLink");
        }

        homePage.verifyCreditCardUnavailable();
    }

    /**
     * Function to Verify Footer options and its Navigation
     */
    public void verifyMobileFooter() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyHomePageNaviagtion();
    }


    public void verifyAddAccountPolicy() throws Exception {

        AccountPolicyPage accPolicypage = new AccountPolicyPage(scriptHelper);
        accPolicypage.verifyAddAccountPolicies();
    }

    public void verifyErrorTextforInvalidPolicy() throws Exception {
        AccountPolicyPage accPolicypage_err = new AccountPolicyPage(scriptHelper);
        accPolicypage_err.verifyErrorTextforInvalidPolicyFunc();
    }

    public void verifyInvalidPolicy() throws Exception {
        AccountPolicyPage accPolicypage_err = new AccountPolicyPage(scriptHelper);
        accPolicypage_err.verifyInvalidPolicyFunc();
    }

    public void verifyErrorTextforInvalidAccFunction() throws Exception {
        AccountPolicyPage accPolicypage_err = new AccountPolicyPage(scriptHelper);
        accPolicypage_err.verifyErrorTextforInvalidAccFunc();
    }

    //CFPUR :69
    public void verifyAccountStatementDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.VerifyAccountEStatement();
    }

    //CFPUR :293
    public void verifyPaperlessStatementPromotionInfo() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.VerifyPaperlessStatementPromotionInfo();
    }

    //CFPUR :69
    public void verifyAccountStatementPagination() throws Exception {

        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyPaginationDisplayed();
    }

    public void verifyNavigationToHomePage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.NavigationToHomePage();
    }

    //CFPUR : 1255, 1256

    /**
     * Function to verify options are displayed on 'home' page for mobile browser
     */
    public void verifyOptionsDisplayed_Home() throws Exception {

        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyHomePageNaviagtion();
    }

    /**
     * Function to verify options are displayed by clicking 'More' button for mobile browser
     */
    public void verifyOptionsDisplayed_More() throws Exception {

        MoreOptionsPage moreoptions = new MoreOptionsPage(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        moreoptions.validateOptsTextDisplyd();
        //moreoptions.verifyRegStatBOIlinkClickable();
    }

    /**
     * Function to verify options are displayed on home page for Web Browser
     */
    public void verifyOptionsDisplayed_Desktop() throws Exception {

        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateOptsTextDisplydDesktop();
    }

    /**
     * CFPUR-1142
     */
    public void verifyManageStatementMenuOptions() throws Exception {
        ServicesManageStatement ServicesManage = new ServicesManageStatement(scriptHelper);
        ServicesManage.verifyManageStatementOptions();
    }

    /**
     * #CFPUR-104, #CFPUR-1490, #CFPUR-1626,#CFPUR-1730
     * Function to verify change address functionality
     */

    public void verifyAddressChange_SelectAccounts() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.selectAccountsChangeAddress();
    }

    public void verifyEnterAddressLineDetails() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.selectAccountsChangeAddress();
        changeAddress.enterAddressLinedetails();
        changeAddress.enterEmailDetails();
        changeAddress.clickContinue();
    }

    public void verifyChangeAddressReview() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.selectAccountsChangeAddress();
        changeAddress.selectAddressDropdowns();
        changeAddress.enterAddressLinedetails();
        changeAddress.enterEircode();
        changeAddress.enterEmailDetails();
        changeAddress.clickContinue();
        changeAddress.changeAddressReview();
        // changeAddress.verifyReviewPageOrder();
        changeAddress.clickConfirm();
        changeAddress.ChangeAdressPIN();
        changeAddress.clickSCAConfirm();
        //homePage.verifyDeviceListSCA();
        homePage.verifyHomePage();
        // homePage.verifyBackArrow();
        changeAddress.verifyreferencetimestamp();
        //homePage.verifyBackArrow();
    }

    public void verifyAndClickGoBackArrow() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyAndClickGoBackArrowMobile();
    }

    public void verifyChangeAddressReviewpageOrder_NI() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.modifyCheckboxes();
        //changeAddress.selectAccountsChangeAddress();
        changeAddress.verifyXonChangeAddressDetails();
        changeAddress.selectCountryTextbox();
        changeAddress.enterAddressLinedetails();
        changeAddress.enterEircode();
        // changeAddress.enterEmailDetails();
        changeAddress.clickContinue();
        changeAddress.changeAddressReview();
        changeAddress.clickConfirm();
        changeAddress.ChangeAdressPIN();
        changeAddress.clickConfirm();
        //homePage.verifyDeviceListSCA();
        // changeAddress.clickSCAConfirm();
        changeAddress.verifyreferencetimestamp();
        // homePage.verifyBackArrow();
    }

    public void verifyChangeAddressReview_Others() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.modifyCheckboxes();
        //changeAddress.selectAccountsChangeAddress();
        changeAddress.selectCountry();
        //changeAddress.enterCounty_Others();
        changeAddress.enterPostcode();
        changeAddress.enterAddressLinedetails();
        changeAddress.enterEmailDetails();
        changeAddress.clickContinue();
        changeAddress.changeAddressReview();
        changeAddress.clickConfirm();
        changeAddress.ChangeAdressPIN();
        changeAddress.clickConfirm();
        //homePage.verifyDeviceListSCA();
        //changeAddress.clickSCAConfirm();
        changeAddress.verifyreferencetimestamp();
    }

    public void verifyChangeAddressReviewpageOrder_Ireland() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.modifyCheckboxes();
        //changeAddress.selectAccountsChangeAddress();
        changeAddress.verifyXonChangeAddressDetails();
        changeAddress.verifyErrorMessageChangeAddress();
        changeAddress.selectAddressDropdowns();
        changeAddress.enterAddressLinedetails();
        changeAddress.enterEircode();
        //  changeAddress.enterEmailDetails();
        changeAddress.clickContinue();
        // changeAddress.verifyReviewPageOrder();
        changeAddress.changeAddressReview();
        changeAddress.clickConfirm();
        changeAddress.ChangeAdressPIN();
        changeAddress.clickConfirm();
        //homePage.verifyDeviceListSCA();
        //changeAddress.clickSCAConfirm();
        changeAddress.verifyreferencetimestamp();
    }

    public void verifyChangeAddressReviewpageOrder_NorthernIreland_ROI() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.modifyCheckboxes();
        //changeAddress.selectAccountsChangeAddress();
        changeAddress.verifyXonChangeAddressDetails();
        changeAddress.selectCountry();
        changeAddress.selectCounty_Ireland_NorthIreland();
        changeAddress.enterAddressLinedetails();
        changeAddress.enterPostcode();
        changeAddress.enterEmailDetails();
        changeAddress.clickContinue();
        changeAddress.changeAddressReview();
        changeAddress.clickConfirm();
        changeAddress.ChangeAdressPIN();
        changeAddress.clickConfirm();
        changeAddress.verifyreferencetimestamp();
    }

    public void verifyChangeAddressReviewpageOrder_Others() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.modifyCheckboxes();
        //changeAddress.selectAccountsChangeAddress();
        changeAddress.verifyXonChangeAddressDetails();
        changeAddress.selectCountry();
        changeAddress.enterCounty_Others();
        changeAddress.enterPostcode();
        changeAddress.enterAddressLinedetails();
        changeAddress.enterEmailDetails();
        changeAddress.clickContinue();
        changeAddress.changeAddressReview();
        changeAddress.clickConfirm();
        changeAddress.ChangeAdressPIN();
        changeAddress.clickConfirm();
        changeAddress.verifyreferencetimestamp();
    }

    //##CFPUR-1720,1730 for change address account selection errors
    public void verifyErrorMsg() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        //changeAddress.verifyErrorMessages();
        changeAddress.modifyCheckboxes();
    }

    public void selectPaymentsOptions() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.selectPaymentOption();
    }

    public void selectStandingOrderAccount() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.StandingOrderAccountSelection();
    }

    public void selectOrderFromStandingOrderList() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.StandingOrderSelection();
    }

    public void validateStandingOrderDetails() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.verifyStandingOrderDetails();
    }


    public void verifyAddressChange() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.clickContinue();
    }

    public void verifyAddressDropdowns() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.selectAddressDropdowns();
        changeAddress.enterEircode();
    }

    public void verifyEnterEircode() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.enterEircode();
    }

    public void enterCountryCounty_NI() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.selectCountry();
        changeAddress.selectCounty_Ireland_NorthIreland();
        changeAddress.enterPostcode();
    }

    public void enterCountryCounty_Others() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.selectCountry();
        changeAddress.enterCounty_Others();
        changeAddress.enterPostcode();
    }

    public void verifyAddressChangeE2E() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.verifyChangeAddressDetails();
        changeAddress.selectAccountsChangeAddress();
//        changeAddress.clickContinue();
    }


    /**
     * Story CFPUR-991
     */
    public void clearCountryCounty() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.clearCountrybox();
        changeAddress.selectCountry();
        changeAddress.clearCountybox();
    }

    public void enterCountyPostcode_Others() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.enterCounty_Others();
        changeAddress.enterPostcode();
    }

    public void enterCountyPostcode_NI() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.selectCounty_Ireland_NorthIreland();
        changeAddress.enterPostcode();
    }

    public void enterCountyPostcode_EngWales() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.selectCounty_EngWales();
        changeAddress.enterPostcode();
    }
    /**
     * CFPUR-1781
     */
    public void verifyCountryCurrencyValues() throws Exception {

        PaymentsManagePayees paymentPayees = new PaymentsManagePayees(scriptHelper);
        paymentPayees.verifyPaymentCountryCurrency();
    }

    /*CFPUR-1672,722*/
    public void verifyAddPayee() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.selectPaymentOption();
        addPayee.addPayee();
        addPayee.addPayeeDetails();
//        addPayee.verifyAddPayeeDetails();

        addPayee.enterRequiredPin();
        addPayee.PayeeAddConfirmation("w.Addpayee.lblConfirmPage");
    }

    public void verifyAddPayee_UK() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.selectPaymentOption();
        addPayee.addPayee();
//        addPayee.addPayeeDetails();
        addPayee.addUKDomPayeeDetails();
//         addPayee.verifyAddPayeeDetails();
        addPayee.verifyUKDomesticAddPayeeReviewPage();
        // addPayee.verifyAddPayeeDetails();

        addPayee.enterRequiredPin();
        addPayee.PayeeAddConfirmation("w.Addpayee.lblConfirmPage");
    }
    public void verifySelectPayment() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.selectPaymentOption();
    }

    public void verifyAddPayeeOptions() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.addPayee();
    }

    public void verifyAddPayeeForm() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.addPayeeDetails();
    }

    //Add Payee Review page
    public void verifyAddPayeeReview() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.verifyAddPayeeDetail();
    }

    public void verifyAddPayeeReviewForm() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.verifyAddPayeeDetail();
    }

    public void verifyErrorOnFormPageAddPayee() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.errorMessageValidation();
    }

    public void verifyFillFormPageOnAddPayee() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.EnterPayeeDetails();
    }

    public void verifyAddPayeeToolTip() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.AddPayeeToolTip();
    }

    public void verifyEnterRequiredPin() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.enterRequiredPin();
    }

    public void verifyPayeeAddConfirmation() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.PayeeAddConfirmation(deviceType() + "Addpayee.lblConfirmPage");
    }

    public void verifySendMoneyReviewROI() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.verifySendMoneyReviewPageROI();
    }

    public void verifySendMoneyReview() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.verifySendMoneyReviewPage();
    }

    public void validateManageStandingOrderPage() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.ManageStandingOrderPage();
    }

    //For SEPA payee form
    public void verifySEPApayeeFormDetails() throws Exception {
        PaymentsManagePayees payee = new PaymentsManagePayees(scriptHelper);
        payee.verifySEPApayeeFormDetail();
    }

    /**
     * Individual functions for Notice to Withdraw funds
     */
    public void verifyTransferToDropDownNTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.transferToDropDown();
    }

    public void verifyTooltipTextNTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.captureToolTip();
    }

    public void verifyCloseAccountToggleNTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.closeAccountToggle();
    }

    public void verifyDepositAmountNTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.enterAmount();
    }

    public void verifyPhoneNumberNTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.enterMobile();
    }

    public void verifyEmailAddressNTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.enterEmail();
    }

    public void verifyClickContinueNTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.clickContinue();
    }

    public void verifyClickConfirmNTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.clickConfirm();
    }

    public void verifyWithdrawFundsReviewNTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.withdrawFundsReview();
    }

    public void verifyWithdrawFundsPINNTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        HomePage home=new HomePage(scriptHelper);
        home.enterRequiredPin();
        if(isMobile){
            acceptPushNotification();
        }
    }

    public void verifyRequestSentValidationNTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        //withdrawFunds.requestSentValidation();
        withdrawFunds.withdrawfundsSuccessScreen();
       // withdrawFunds.sentmessagevalidation();
    }

    public void verifyMessagevalidationNTW() throws Exception{
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.withdrawfundsSuccessScreen();
        withdrawFunds.sentmessagevalidation();
    }

    public void verifybackLinkNTW() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyBackArrow();
    }

    public void verifybackArrowPrelogin() throws Exception {
        MoreOptionsPage moreoptions = new MoreOptionsPage(scriptHelper);
        moreoptions.verifyBackArrowPrelogin();
    }

    /**
     * function to capture PIN error for withdraw funds
     */
    public void verifyErrInvalidPIN() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.errInvalidPIN();
    }

    /**
     * Common function validate details from review and request sent page of Withdraw funds
     */
    public void verifyDetails_NTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.details_NTW();
    }

    /**
     * #CFPUR-1657,
     * Function to Withdraw funds for under story Notice to Withdraw
     */

    public void verifyEnterDetailsScreen() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
    }


    /**
     * #CFPUR-1662,1648,1646
     * Function to capture and compare tool tip text under story Notice to Withdraw
     */
    public void verifyTooltipText_NTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.captureToolTip();
    }

    public void verifyCloseAccountToggle() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.closeAccountToggle();
    }

    public void verifyDepositAmount() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.enterAmount();
    }

    public void verifyPhoneNumber() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        // withdrawFunds.enterAmount();
        withdrawFunds.enterMobile();
    }

    public void verifyEmailAddress() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.enterEmail();
    }

    public void verifyWithdrawFundsReview() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.errorMessagesWithdrawFunds();
        withdrawFunds.enterAmount();
        withdrawFunds.enterMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
    }

    public void verifyWithdrawFundsPin() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.enterAmount();
        withdrawFunds.enterMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
        withdrawFunds.enterRequiredPin();
        withdrawFunds.clickConfirm();
    }

    public void verifyWithdrawFundsRequest() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.errorMessagesWithdrawFunds();
        withdrawFunds.enterAmount();
        withdrawFunds.enterMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
        withdrawFunds.enterRequiredPin();
        withdrawFunds.clickConfirm();
        homePage.verifyDeviceListSCA();
        homePage.verifyHomePage();
        // ProfilePage.validateNotificationPageSCA();
        // withdrawFunds.clickConfirm();
        withdrawFunds.requestSentValidation();
    }

    public void verifyWithdrawFundsSCAUplift() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.enterAmount();
        withdrawFunds.enterMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
        withdrawFunds.enterRequiredPin();
        withdrawFunds.clickConfirm();
        homePage.verifyDeviceListSCA();
        homePage.verifyHomePage();
        withdrawFunds.requestSentValidation();
    }

    public void verifyWithdrawFundsRequestDropDown() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.errorMessagesWithdrawFunds();
        withdrawFunds.enterAmount();
        withdrawFunds.transferToDropDown();
        withdrawFunds.enterMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
        withdrawFunds.enterRequiredPin();
        withdrawFunds.clickConfirm();
        withdrawFunds.requestSentValidation();
    }

    public void verifyWithdrawFundsRequestEmail() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.errorMessagesWithdrawFunds();
        withdrawFunds.closeAccountToggle();
        withdrawFunds.enterEmail();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
        withdrawFunds.enterRequiredPin();
        withdrawFunds.clickConfirm();
        withdrawFunds.requestSentValidation();
    }

    public void verifyWithdrawFundsRequestEmailDropDown() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.errorMessagesWithdrawFunds();
        withdrawFunds.closeAccountToggle();
        withdrawFunds.transferToDropDown();
        withdrawFunds.enterEmail();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
        withdrawFunds.enterRequiredPin();
        withdrawFunds.clickConfirm();
        withdrawFunds.requestSentValidation();
    }

    public void verifyWithdrawFundsbackLink() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.enterAmount();
        withdrawFunds.enterMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
        withdrawFunds.enterRequiredPin();
        withdrawFunds.clickConfirm();
        homePage.verifyDeviceListSCA();
        homePage.verifyHomePage();
        withdrawFunds.requestSentValidation();
        homePage.verifyBackArrow();
    }

    public void verifyTransferToDropdown() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.enterAmount();
        withdrawFunds.transferToDropDown();
    }

    public void verifyErrorMessages_NTW() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.errorMessagesWithdrawFunds();
    }

    public void verifyProfileDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyHomePageProfileNaviagtion();
    }

    public void verifyAvailableFundNotPresentE2E() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyCheckContentNotPresent();
    }

    public void verifyNickNameOnPages() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.NickNameOnPages();
    }

    public void verifyAccountDetailsTileE2E() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAcctTileDisplayed(deviceType() + "home.lblNickname_Current");
        homePage.verifyIBANnumberCurrentTile(deviceType() + "home.lblIBANnumber_CurrentE2E");
        homePage.verifyCurrencySymbol(deviceType() + "home.lblCurrencySymbol_CurrentE2E");
        homePage.verifyCurrentBalance(deviceType() + "home.lblCurrentBalance_CurrentE2E");
// homePage.verifyAccountTileClickable(deviceType() + "home.lblNickname_CurrentE2E");
        //homePage.verifyBlueCardDetails(deviceType() + "myAccount.sectionBlueCard", deviceType() + "home.lblNickname_CurrentE2E");
        homePage.ClkAccTypeAndValidateBlueCardDetails();


    }

    public void verifyProductNotPresent() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyCheckContentNotPresent();
    }

    public void verifyStandingOrderListPage() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.StandingOrderListingPage();
    }

    public void verifyErrMsgNoStandingOrder() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.ErrorForNoStandingOrder();
    }

    public void verifyCancelStandingOrder() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.cancelStandingOrder();
    }

    public void verifyCancelStandingOrderConfirmation() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.CancelStandingOrderConfirmationPage();
    }

    /*CFPUR-1233*
     Navigation menu - Account level 'More' option (Mobile view only)
     */
    public void verifyMoreOptionMobile() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.moreOptionMobile_Account();
    }

    public void verifyStandingOrderCancelThisPage() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.cancelThisTabPage();
    }

    public void verifyPutonHoldPage() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.PutonholdPage();
    }

    public void verifyStandingOrderReviewPage() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.ReviewPage();
    }

    public void verifyPinSuccessPage() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.PinSuccessPage();
    }


    public void verifyStandingOrderConfirmation() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.StandingOrderConfirmationPage();
    }

    public void enterConfirmationPin() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.enterNewPin();
    }

    public void verifyErrMsgNoAmendment() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.ErrMsgNoAmendment();
    }

    public void verifyErrSelectOption() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.errMsgSelectOption();
    }

    public void verifyRemoveHoldPage() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.removeHoldPage();
    }

    public void verifyChangeAmountPage() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.ChangeAmountPage();
    }

    public void verifyErrInvalidAmountChange() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.errChangeAmountPage();
    }

    public void verifySOAmountErrorValidation() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.ChangeAmountPage();
        standingOrderPage.errChangeAmountPage();
    }

    public void enterNewPin() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.enterRequiredPin();
    }

    public void verifyAccountChequeSearch() throws Exception {
        TransactionsPage transactionsPage = new TransactionsPage(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAccTypeExistAndClicked();
        transactionsPage.verifyChequeSearch();

    }

    /*CFPUR-1638*/
    public void verifyPayFromForCrossJuridication() throws Exception {
        PaymentsManagePayees addPayeeCross = new PaymentsManagePayees(scriptHelper);
        addPayeeCross.verifyAddPayeeCrossJuridication();
    }

    public void verifySepaDebitCreditDetails() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.SEPADebitOrCreditDetails();
    }

    public void verifySEPATransactionDetailsPage() throws Exception {

        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.SEPATransactionDetailsPage();
    }

    /*
    CFPUR-1727-Page refresh
     */
    public void verifyPageRefresh() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyPageRefresh();
        homePage.verifyHomePage();
        homePage.verifyRefreshFunctionlity();
    }

  /*  public void verifyRefreshPages() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyRefreshFunctionlity();

    }*/

    // For E2E 991 user story
    public void verifyDeleteTextOptionServicesPage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.EnterAddAccPolicyFormCurrAccAndClear();
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.selectAccountsChangeAddress();
        changeAddress.enterAddressLinedetails();

    }

    public void verifyDeleteTextOptionloginPage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        //homePage.verifytooltip();
        homePage.LoginDetailsDeleteTextOption();
        homePage.LoginDetailsDeleteTextOptionError();
    }


    public void verifySepaViewDetailsNotPresent() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.SepaViewDetailsNotPresentInprogressTable();
    }

    /*
    CFPUR-2252 launch transfer between account screen
     */
    public void verifyLaunchTrasBetweenAccnt() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.launchTransferBetAccounts();
    }

    /*
    CFPUR-2264, 2265 Select account pay from on transfer between account screen
     */
    public void verifySelectAccountPayFrom() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.selectAccountPayFrom();
    }

    /*
    CFPUR-2296 Select account pay to on transfer between account screen
     */
    public void verifySelectAccountPayTo() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.selectAccountPayTo();
    }

    /*
    CFPUR-2302,2309 Select amount and process transfer options on transfer between account screen
     */
    public void verifyEnterAmount() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.enterAmount();
    }

    public void verifySelectOptProcessTransfer() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.selectOptProcessTransfer();
    }

    public void verifyClickContinue_TBA() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.clickContinue_TBA();
    }

    public void verifyClickConfirm_TBA() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        transBetAccnt.clickConfirm_TBA();
        //   homePage.verifyDeviceListSCA();
        //   homePage.verifyHomePage();
        // transBetAccnt.requestSentValidation();

    }

    /*
    CFPUR-2312 Verify details on Review page for transfer between account
     */
    public void verifyDetailsReviewPage_TBA() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.DetailsReviewPage_TBA();
    }

    /**
     * Common function to verify details on Transfer Between Account screen
     */
    public void verifyTBADetails() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.verifyDetails();
    }

    /**
     * Function to Capture Error messages on Transfer Between Account screen
     */
    public void verifyCaptureErrorPinPage() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.captureErrorPinPage();
    }

    /*
    CFPUR-2347 Enter PIN for transfer between account
     */
    public void verifyPIN_TBA() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.PIN_TBA();
    }

    /*
    CFPUR-2349 Verify Success (Request sent) screen details for transfer between account
     */
    public void verifyRequestSentValidation() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.requestSentValidation();
    }

    /**
     * CFPUR-40 Function to verify Apply link is not present for NI/GB customer
     */
    public void verifyApplyLinkNotPresent() throws Exception {
        HomePage homepage = new HomePage(scriptHelper);
        homepage.verifyNoApplyLink();
    }

    /**
     * CFPUR-1224 Function to verify Accessibility text
     */
    public void verifyAccessibilityText() throws Exception {
        HomePage homepage = new HomePage(scriptHelper);
        homepage.AccessibilityText();
    }

    /**
     * CFPUR-1221 Function to verify Regulatory Information text
     */
    public void verifyRegulatoryInfoText() throws Exception {
        HomePage homepage = new HomePage(scriptHelper);
        homepage.RegulatoryInfoText();
    }

    /**
     * Function to Capture Error messages on Transfer Between Account screen
     */
    public void verifyCaptureErrorMsgOnTBAScreen() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.captureErrorMsgOnTBAScreen();
    }


    /*CFPUR-631 - No Show More button verification*/
    public void verifyNoShowMore() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyNoShowMoreButton();
    }


    /*CFPUR631 - No Portfolio component verification*/
    public void verifyNoPortfolio() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyNoPolicyPortfolio();
    }

    /*CFPUR-1527 - */
    public void verifyPAD2Header() throws Exception {
        HomePage homepage = new HomePage(scriptHelper);
        homepage.verifyPAD2Header();
    }

    public void verifyReturnToTransactionListLink() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.verifyReturnToTransactionLinkFunc();
    }

    public void verifyTransactionsDisplayed() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.TransactionsOrCompletedTransactionsDisplayed();
    }

    public void verifyInprogressTodaysTransactionsDisplayed() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        //transaction.InprogressTodaysTransactionsDisplayed();
        transaction.InprogressTransDisplayedMobileDesktop();
    }

    public void verifyInprogressTodaysTransactionsNotDisplayed() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.InprogressTodaysTransactionsNOTDisplayed();
    }

    public void verifyErrNoTransactionsIssued() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.ErrNoTransactionsIssued();
    }

    public void verifyErrUnableToProcessRequest() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.ErrUnableToProcessRequest();
    }

    /*CFPUR-90*/
    public void verifyDirectDebitPage() throws Exception {
        PaymentsManagePayees pay = new PaymentsManagePayees(scriptHelper);
        pay.verifyDirectDebitPage();
    }

    public void verifyTransactionSearchPage() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.searchTransactionResultPage();
    }

    public void verifyTransactionnoRecordErr() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.errNoSearchResultForTransaction();
    }

    public void verifyErrToDateBeforeThanFromDate() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.errToDateBeforeThanFromDate();
    }

    public void verifyforeigncurrencyTransaction() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.foreigncurrencyTransaction();
    }

    /**
     * Negative Scenario: Function to check if Transfer Between my Accounts does not exists
     */

    public void verifyTBAexists() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.verifyTBAexists();
    }

    public void verifySetUpSOAccountSelection() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.setUpSOAccountSelection();
    }

    public void verifyEnterSOPayeedetails() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.enterSOPayeedetails();
    }

    public void verifyEnterSOPayeedetails_GB() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.enterSOPayeedetails_GB();
    }

    public void verifySetupSOReviewPage() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.SetupSOReviewPage();
    }

    public void verifySetupSOConfirmationPage() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.SetupSOConfirmationPage();
    }

    public void verifySOerrorMessageValidations() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.errorMessageValidations();
    }

    public void verifySetupStandingOrderBackNavigation() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.backNavigationValidation();
    }

    public void verifysetupStandingOrderMaxLength() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.setupStandingOrderMaxLength();
    }


    /*CFPUR-2800 : SEPA Payment ROI*/
    public void verifySEPAPaymentJourneyROI() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.SendMoneyFormHappyPath();

    }

    /*CFPUR-2800 : SEPA Payment ROI- Error scenarios*/
    public void verifySEPAPaymentJourneyROI_Error() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.SendMoneyForm_Error();
    }

    /*CFPUR-2800 : SEPA Payment ROI- All Error scenarios*/
    public void verifySEPAPaymentJourneyROI_AllError() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.SEPAPayment_AllError();
    }

    /*CFPUR-78 : Bill Payment ROI - All Error scenarios*/
    public void verifyBillPaymentAllError() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.BillPayment_AllError();
    }

    /*CFPUR-2800 : SEPA Payment ROI- All Error scenarios*/
    public void verifyAccountNotPresent() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.AccountNotPresent();
    }

    public void verifyEnterTransactionsSearchFilters() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.enterSearchTransactionFilters();
    }

    public void verifyExportTransaction() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.exportTransaction();
    }

    public void verifyLoadMoreTransactions() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.loadMoreTransactions();

    }

    public void verifyLoadMoreTransactionsPositive() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.loadMoreTransactionsPositive();
        ;
    }


    public void verifyTransactionsMenu() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.verifyTransactionsTooltip();
        transaction.verifyTransactionsTooltipMenu();
    }

    /*CFPUR-1833: Payments landing page*/
    public void verifyPaymentsPage() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyAllPaymentsOptions();
    }

    //CFPUR-71 : Order Up to Date statements
    public void verifyOrderUpToDateStatementsFlow() throws Exception {
        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Services.verifyAndClickServicesOption();
        clickJS(getObject("ManageStatements.lnkOrderUpToDate"), "Order up to date statements.");
        Services.verifyOrderUpToDateStatements();
        Services.verifyGeneralReviewPage();
        //isMobile>>TODO need to check its Impact
        if (deviceType.equalsIgnoreCase("MobileWeb")) {
            Services.verifyElementDetailsConfimMobile();
        } else {
            Services.verifyElementDetailsConfim("ManageStatements.pgConfirmation");
        }
    }

    public void verifyOrderUpToDateStatementsFlowGoBack() throws Exception {
        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Services.verifyAndClickServicesOption();
        clickJS(getObject("ManageStatements.lnkOrderUpToDate"), "Order up to date statements.");
        Services.verifyOrderUpToDateStatements();
        Services.verifyInfoGoBack();
        Services.verifyManageStatementOptions();
    }

	//CAF-2835
    public void verifyOrderacopyofStatementLandingpageGoback() throws Exception{
        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Services.verifyAndClickServicesOption();
        clickJS(getObject("ManageStatements.lnkOrderCopy"), "Order a copy of a statement.");
        Services.verifyInfoGoBack();
        Services.verifyManageStatementOptions();
    }

    //CFPUR-4243 : Credit Card services
    public void verifyCreditCardServices() throws Exception {
        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Services.verifyAndClickCreditcardServicesOption();
    }

    //CFPUR-4243 : Credit Card services
    public void verifyCreditCardServicesLink() throws Exception {
        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Services.verifyAndClickCreditcardServices();
    }

    //Manage Credit Card link should not present for MOBILE APP (negative check)
    public void verifyCreditCardServicesTile() throws Exception {
        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Services.verifyCreditCardTile();
    }

    public void verifyselectAdditionalPayeeType() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.addPayee();
    }

    public void verifySelectPayeeCountryAndCurrency() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.selectPayeeCountryAndCurrency();
    }

    public void verifyPADText() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.ValidateAlertText();
    }

    public void verifyEnterPayeeDetailsFormA() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.enterPayeeDetailsFormA();
    }

    public void verifyErroMessageMaximumPayee() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.verifyErroMessageMaximumPayee();
    }


    public void verifyEnterPayeeDetailsFormB() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.enterPayeeDetailsFormB();
    }

    public void verifyEnterPayeeDetailsFormC() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.enterPayeeDetailsFormC();
    }

    public void verifyEnterPayeeDetailsFormD() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.enterPayeeDetailsFormD();
    }

    public void verifyEnterPayeeDetailsFormE() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.enterPayeeDetailsFormE();
    }

    public void verifyAddPayeeReviewPage() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.AddPayeeReviewPage();
    }

    public void verifyAddPayeeEnterPin() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.enterNewPin();
    }

    public void verifyAddPayeeConfirmationPage() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.AddPayeeConfirmationPage();
    }

    public void verifyMobileTopUp() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.launchPaymentsTab();
    }

    public void verifyMobileTopUpselectAccountPayFrom() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.selectAccountPayFrom();
    }

    public void verifyMobileTopUpselectProvider() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.selectProvider();
    }

    public void verifyMobileTopUpenterPhoneNumber() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.enterPhoneNumber();
    }

    public void verifyMobileTopUpselectAmount() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.selectAmount();
        addTopUp.clickContinue_MTU();
    }

    public void verifyMobileTopUpclickContinue_MTU() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.clickContinue_MTU();
    }

    public void verifyMobileTopUpReview() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.verifyMobileTopUpReviewPage();
    }

    public void verifyMobileTopUpmobileTopUpPIN() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.mobileTopUpPIN();
    }


    public void verifyMobileTopUpSuccessScreen() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.MobileTopUpSuccessScreen();
    }

    public void verifyMobileTopUpenterConfirmNumber() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.enterConfirmNumber();
    }

    public void verifySelectaccount() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.launchPaymentsTab();
        addTopUp.selectAccountPayFrom();
    }

    public void verifySelectprovider() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.launchPaymentsTab();
        addTopUp.selectAccountPayFrom();
        addTopUp.selectProvider();


    }

    public void verifyEnterContactNumber() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.launchPaymentsTab();
        addTopUp.selectAccountPayFrom();
        addTopUp.selectProvider();
        addTopUp.enterPhoneNumber();
        addTopUp.enterConfirmNumber();
    }

    public void validationsPhoneNumberFields() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.verifyPhoneValidations();
    }

    public void enterCorrectPhoneNumberFields() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.enterPhoneNumber();
        addTopUp.enterConfirmNumber();
    }

    public void verifyAccountBalanceLess() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.verifyErrorAccountBalancelessAmount();

    }

    public void verifySelectAmount() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.launchPaymentsTab();
        addTopUp.selectAccountPayFrom();
        addTopUp.selectProvider();
        addTopUp.enterPhoneNumber();
        addTopUp.enterConfirmNumber();
        addTopUp.selectAmount();
    }

    public void verifyImpInformation() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.launchPaymentsTab();
        addTopUp.selectAccountPayFrom();
        addTopUp.selectProvider();
        addTopUp.enterPhoneNumber();
        addTopUp.enterConfirmNumber();
        addTopUp.selectAmount();
        addTopUp.textImportantInformation();
        addTopUp.clickContinue_MTU();
    }

    public void verifyReviewMTUPage() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.launchPaymentsTab();
        addTopUp.selectAccountPayFrom();
        addTopUp.selectProvider();
        addTopUp.enterPhoneNumber();
        addTopUp.enterConfirmNumber();
        addTopUp.selectAmount();
        //addTopUp.textImportantInformation();
        addTopUp.clickContinue_MTU();
        addTopUp.verifyDetails();
        // addTopUp.clickContinue_MTU();
    }

    public void verifyConfirmationMTUPage() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        addTopUp.launchPaymentsTab();
        addTopUp.selectAccountPayFrom();
        addTopUp.selectProvider();
        addTopUp.selectAmount();
        addTopUp.enterPhoneNumber();
        addTopUp.enterConfirmNumber();
        addTopUp.textImportantInformation();
        addTopUp.clickContinue_MTU();
        addTopUp.MobileTopUpReview();
        addTopUp.clickContinue_MTU();
        addTopUp.mobileTopUpPIN();
        addTopUp.clickConfirm();
        homePage.verifyDeviceListSCA();
        homePage.verifyHomePage();
        addTopUp.MobileTopUpSuccessScreen();
    }

    public void verifyValidAccountsMTUPage() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.launchPaymentsTab();
        addTopUp.validAccounts_MTU();
    }

    public void verifyValidAccounts_MTU() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.validAccounts_MTU();
    }

    public void verifyPayfromInValidAccountsMTUPage() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.launchPaymentsTab();
        addTopUp.PayfromInvalidAccounts_MTU();
    }

    /*public void verifyPayToInValidAccountsMTUPage() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.launchPaymentsTab();
        addTopUp.PayToInvalidAccounts_MTU();
    }*/

    //Saurabh In Progress
    public void verifyAddBillNavigation() throws Exception {
        PaymentsManagePayees addbillpay = new PaymentsManagePayees(scriptHelper);
        addbillpay.addBillPayeeNavigation();
    }


    public void verifySelectPayeeFromManagePayeeList() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.SelectPayeeFromManagePayeeList();
    }

    public void validateAddBillPayeeDetails() throws Exception {
        PaymentsManagePayees billpay = new PaymentsManagePayees(scriptHelper);
        billpay.checkAddBillPayeeDetailsPage();
    }

    public void validateAddBillReviewPageDetails() throws Exception {
        PaymentsManagePayees addbillpay = new PaymentsManagePayees(scriptHelper);
        addbillpay.checkAddBillReviewPageDetails();
    }

    public void validateAddBillConfirmationPage() throws Exception {
        PaymentsManagePayees addbillpay = new PaymentsManagePayees(scriptHelper);
        addbillpay.checkAddBillConfirmationPage();
    }

    public void validateAddBillErrors() throws Exception {
        PaymentsManagePayees addbillpay = new PaymentsManagePayees(scriptHelper);
        addbillpay.checkAddBillErrors();
    }

    public void validateGoBackNavigationsAddBill() throws Exception {
        PaymentsManagePayees addbillpay = new PaymentsManagePayees(scriptHelper);
        addbillpay.checkBackButtonBillPayees();
    }

    // CFPUR-1234 : Manage Email Statements notification
    public void verifyManageStatementsNotificationFlow() throws Exception {
        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Services.verifyAndClickServicesOption();
        Services.verifyManageStatementsEmailNotification();
        Services.verifyAndClickServicesOption();
        Services.verifyManageStatementsEmailNotification();
    }

    // CFPUR-1234 : Manage statement Toggle Swtich
    public void verifyStatementsNotificationToggle() throws Exception {
        ServicesManageStatement Services = new ServicesManageStatement(scriptHelper);
        Services.verifyManageStatementOptions();
        Services.verifyStatementsNotificationToggleSwitch();
    }

    public void verifyPayeeDetailsPage() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.payeeDetailsPage();
    }

    //    CFPUR-726 : Using Pay CTA button on Manage Payees
    public void validatePayFromAccountSelection() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.clickPay();
        payments.verifyPayFromAccountSelection();
    }

    public void validatePayFromAccountOperation() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.PayFromAccountOperation();
    }

    public void verifySendMoneyDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        scrollToView(getObject("xpath~//*[@class='tc-card-body']"));
        homePage.verifyElementDetails("xpath~//*[@class='tc-card-body']");
    }

    public void validatePayFromAccountReviewOperation() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.SendMoneyReviewPageOperation();
    }


    public void verifySelectPayFromAccount() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.selectPayFromAccount();
    }


    public void addPayeeE2E() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.selectPaymentOption();
        addPayee.addPayee();
        addPayee.addPayeeDetails();
    }

    public void navigateAddSEPAPayee() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.selectPaymentOption();
        addPayee.addPayee();
    }

    public void verifySEPAcountry() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.VerifyPayeeCountryAndCurrency();
    }

    public void verifyAddSEPAPayee() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.addPayeeDetails();
    }

    public void verifyAddPayeeErrorMessage() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.errorMessageValidation();
    }

    public void verifyAddPayeeDetails() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.verifyAddPayeeDetail();
        addPayee.enterRequiredPin();
        addPayee.PayeeAddConfirmation("w.Addpayee.lblConfirmPage");
        addPayee.clickBacktoPayee();
        //  addPayee.verifyAddedPayeeDetails("w.AddPayee.lblManagePayee");
    }


    public void verifyWithdrawFundsText() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.enterAmount();
        withdrawFunds.enterMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
        withdrawFunds.enterRequiredPin();
        withdrawFunds.clickConfirm();
        withdrawFunds.withdrawfundsSuccessScreen();
    }

    public void verifyCloseAccountToggleInfo() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.closeAccountToggle();
        withdrawFunds.withdrawfundsMessage();
    }

    public void verifyTooltipTextNoticetoWithdraw() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.captureToolTip();
    }

    public void verifyNoticeToWithdrawBlankMobile() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.closeAccountToggle();
        withdrawFunds.enterBlankMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.VerifyErrorBlankMobile();
    }

    public void verifyNoticeToWithdrawInvalidMobile() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.enterInvalidMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.VerifyErrorInvalidMobile();
    }

    public void enterValidMobileNo() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.enterMobile();
    }

    public void enterValidEmailAddress() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.enterEmail();
    }

    public void verifyNoticeToWithdrawPINError() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        Thread.sleep(2000);
        waitForPageLoaded();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
        withdrawFunds.clickConfirm();
        withdrawFunds.VerifyErrorPIN();
        //withdrawFunds.enterWithdrawFundsInvalidPIN();
        //withdrawFunds.clickConfirm();
        //withdrawFunds.VerifyErrorPIN();
    }

    public void verifyNoticeToWithdrawReviewScreen() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        //withdrawFunds.GObackContinueButton();
        withdrawFunds.enterRequiredPin();
        /*if(isMobile){
            acceptPushNotification();
        }
        withdrawFunds.withdrawfundsSuccessScreen();*/
    }

    public void verifyBlueCardDetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.blueCardDetails();
    }

    public void selectAccountTab() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.clickAccountTab();
    }

    public void verifyNickNameOnAllPages() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.NickNamePresentOnPages();
    }

    public void verifyDuplicateSOError() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.StandingOrderAccountSelection();
        standingOrderPage.StandingOrderDuplicateError();
    }


    public void clickServicesandContactUs() throws Exception {
        ServicesManageStatement servicesManage = new ServicesManageStatement(scriptHelper);
        servicesManage.ClickServicesContactUsLink();
    }

    public void verifyServicesandContactUsLinks() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyServicesContactUsLinks();
    }

    public void verifyServicesLinksNavigation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.ServicesLinksNavigation();
    }

    public void verifyPaymentsLinksNavigation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.PaymentsLinksNavigation();
    }

    public void verifyContactDetails() throws Exception {
        ServicesManageStatement servicesManage = new ServicesManageStatement(scriptHelper);
        servicesManage.creditCardServicesDetails();
    }

    public void verifyServicesDetails() throws Exception {
        ServicesManageStatement servicesManage = new ServicesManageStatement(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        servicesManage.clickServices();
        homePage.verifyElementDetails("ServiceManage.pageServices");
    }

    public void verifyBackNavigation() throws Exception {
        ServicesManageStatement servicesManage = new ServicesManageStatement(scriptHelper);
        servicesManage.service_BackNavigation();
    }

    public void verifyFooterLinks() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyDetails();
    }

    public void verifyFooterText() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.footerText();
    }

    public void verifyChangeAddressText() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails("homepage.ChangeAddressText");
    }

    public void verifylaunchChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
    }

    public void verifyaccountCheckboxes() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.modifyCheckboxes();
    }

    public void verifyselectCountry() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.selectCountry();
    }

    public void verifyselectCounty_Others() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.enterCounty_Others();
    }

    public void verifyenterPostcode() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.enterPostcode();
    }

    public void verifyaddressline() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.enterAddressLinedetails();
    }
    public void enterCountyPostcode_Ire() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.selectAddressDropdowns();
        changeAddress.enterAddressLinedetails();
    }
    public void verifyclickContinue() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.clickContinue();
    }

    public void verifychangeAddressReview() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.changeAddressReview();
    }

    public void verifyClickConfirmbutton() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.clickConfirm();
    }

    public void verifyCAPinPage() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.ChangeAdressPIN();
    }

    public void verifyCAConfirmationPage() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.verifyreferencetimestamp();
    }

    public void clickLogout() throws InterruptedException {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.logOut();
    }

    public void transferAccountLinkNotPresent() throws Exception {
        PaymentsManagePayees paymentPayees = new PaymentsManagePayees(scriptHelper);
        paymentPayees.TransferAccountLnkNotPresent();
    }

    public void verifyNavigateSOEnterDetails() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.NavigateSOEnterDetails();
    }

    public void verifyStandingOrderDuplicateError() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.StandingOrderDuplicateError();
    }

    public void verifyServicesPaperStatements() throws Exception {
        ServicesManageStatement servicesManage = new ServicesManageStatement(scriptHelper);
        servicesManage.ManagestetmentsNavigation();
        servicesManage.verifyPaperStatements();
        servicesManage.verifyPaperSwitch();
    }

    /**
     * To Verify the info displayed on paper statements on/off : CFPUR-3991
     **/
    public void verifyServicesPaperStatementsinfo() throws Exception {
        ServicesManageStatement servicesManage = new ServicesManageStatement(scriptHelper);
    //    servicesManage.ManagestetmentsNavigation();
        servicesManage.verifyPaperStatements();
        servicesManage.verifyinfo();

    }

    public void verifyServicesPaperStatementsGoBack() throws Exception {
        ServicesManageStatement servicesManage = new ServicesManageStatement(scriptHelper);
        servicesManage.verifyInfoGoBack();
    }

    /**
     * CFPUR826_E2E_TC058: Function to verify 'Switch between my accounts' option is not displayed
     */

    public void verifyCreditCardswitch() throws Exception {
        HomePage homepage = new HomePage(scriptHelper);
        homepage.verifyCreditCardswitch();
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        transBetAccnt.verifyTBAexists();
    }

    /**
     * CFPUR1710_ETE_TC030: Function to verify Arrow Button is displayed on HomePage
     */
    public void verifyArrowButtondisplayed() throws Exception {
        HomePage homepage = new HomePage(scriptHelper);
        homepage.verifyArrowButton();
    }

    public void verifyLoadButtondisplayed() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        transactionPage.verifyLoadMoreButton();
    }
    public void verifyEmailerrorvaliadtionNTW() throws Exception{
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.enterBlankEmail();
        withdrawFunds.clickContinue();
        withdrawFunds.VerifyErrorForEmail();
        withdrawFunds.VerifyErrorForConfirmEmail();
        withdrawFunds.clickContinue();
    }

    public void verifyNoticeToWithdrawBlankEmail() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.closeAccountToggle();
        withdrawFunds.enterBlankEmail();
        withdrawFunds.clickContinue();
        withdrawFunds.VerifyErrorForEmail();
    }

    public void verifyNoticeToWithdrawInvalidEmail() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.enterInvalidEmail();
        withdrawFunds.clickContinue();
        withdrawFunds.VerifyErrorForEmail();
    }

    public void verifyNoticeToWithdrawBlankMobileNon365() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.closeAccountToggle();
        withdrawFunds.transferToDropDown();
        withdrawFunds.enterBlankMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.VerifyErrorBlankMobile();
    }

    public void verifyNoticeToWithdrawBlankEmailNon365() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.closeAccountToggle();
        withdrawFunds.transferToDropDown();
        withdrawFunds.enterBlankEmail();
        withdrawFunds.clickContinue();
        withdrawFunds.VerifyErrorForEmail();
    }

    public void verifyDirectDebitPageDetails() throws Exception {
        PaymentsManagePayees pay = new PaymentsManagePayees(scriptHelper);
        pay.selectPaymentOption();
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.verifyDirectDebitPage();
        HomePage homepage = new HomePage(scriptHelper);
        homepage.verifyElementDetails("DirectDebit.page");

    }


    public void verifyAccountTileDetailsCrossJurisdictional() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.ValidateAccountTileDetails();
    }

    public void verifyAddPayeeErrorMessageValidations() throws Exception {
        PaymentsManagePayees paymentsSEPA = new PaymentsManagePayees(scriptHelper);
        paymentsSEPA.errorMessageValidations();
    }

    public void verifyAndValidateSOError() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.validateAndVerifySOErrors();
    }

    /*1228- PDF statements*/

    public void verifyPDFstatements() throws Exception {
        ServicesManageStatement service = new ServicesManageStatement(scriptHelper);
        service.verifyPDFStatements();
    }

    public void verifyMaxlength() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.validateMaxlength();
    }


    public void verifyPDFAccountStatementsE2E() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.clickStatementsTab();
        if (deviceType().equals("w.")) {
            homePage.verifyPaginationDisplayed();
        }
        homePage.clickPDFAccountStatements();
    }

    public void verifyChangeAddressReview_E2E() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        // changeAddress.launchChangeAddress();
        changeAddress.clickContinue();
        //changeAddress.clearCountrybox();
        changeAddress.verifyXonChangeAddressDetails();
        changeAddress.selectCountry();
        changeAddress.selectAddressDropdowns();
        //changeAddress.selectCounty_Ireland_NorthIreland();
        changeAddress.enterAddressLinedetails();
        changeAddress.enterEmailDetails();
        changeAddress.clickContinue();
        changeAddress.changeAddressReview();
        changeAddress.clickConfirm();
        changeAddress.ChangeAdressPIN();
        changeAddress.clickSCAConfirm();
        changeAddress.verifyreferencetimestamp();
        // homePage.verifyHomePage();

    }

    public void clickContinue_ChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.clickContinue();
    }

    public void clickXicon_ChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.verifyXonChangeAddressDetails();
    }

    public void selectCountry_ChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.selectCountry();
    }

    public void selectAddressDropdowns_ChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.selectAddressDropdowns();
    }

    public void enterAddressLine_ChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.enterAddressLinedetails();
    }

    public void clickConfirm_ChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.clickConfirm();
    }

    public void enterEmailDetails_ChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.enterEmailDetails();
    }

    public void verifyReview_ChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.changeAddressReview();
    }

    public void enterPIN_ChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.ChangeAdressPIN();
    }

    public void verifyReferenceTimeStamp_ChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.verifyreferencetimestamp();
    }

    public void verifyChangeAddressmessagevalidation() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
//        if(isMobile){
//            acceptPushNotification();
//        }
        changeAddress.verifyreferencetimestamp();
        changeAddress.sentmessagevalidation();
    }

    public void verifyChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        changeAddress.launchChangeAddress();
        //homePage.gobacknavigation();
        changeAddress.modifyCheckboxes();
        changeAddress.selectCountryTextbox();
        changeAddress.enterAddressLinedetails();
        changeAddress.clickContinue();
        // homePage.gobacknavigation();

        changeAddress.clickConfirm();
        // homePage.gobacknavigation();
        changeAddress.ChangeAdressPIN();
        changeAddress.clickSCAConfirm();
        //homePage.gobacknavigation();

    }

    public void verifyAccountPageLinks_Saving() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyPAD2Header();
    }

    public void verifySwitchAccounts_Current() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifySwitchAccount();
    }

    /**
     * CFPUR- 94
     */

    public void verifyCancelDirectDebitMandate() throws Exception {
        verifylaunchPaymentsTab();
        verifycancelDirectDebitMandate();
        verifyblankfieldValidations();
        verifysetupDirectDebitMaxLength();
        verifyCreditorDetails();
        verifyDirectDebitDetails();
        verifyDirectDebitContactDetails();
        verifyDirectDebitNotification();
        verifyDirectDebitReview();
        verifyDirectDebitConfirmation();

    }
    public void validateCancelDirectDebitMandate() throws Exception {
        verifylaunchPaymentsTab();
        verifycancelDirectDebitMandate();
        verifyblankfieldValidations();
        verifysetupDirectDebitMaxLength();
        verifyCreditorDetails();
        verifyDirectDebitDetails();
        verifyDirectDebitContactDetails();
        verifyDirectDebitNotification();
        verifyDirectDebitReview();
        verifyDirectDebitConfirmation();

    }
    public void verifycancelDirectDebitMandate() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.cancelDirectDebitMandate();
    }


    public void verifyblankfieldValidations() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.verifyblankfeildValidations();
    }

    public void verifyIbanText() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.cancelDirectDebitMandate();
        DD.DDYourIBANText();
        DD.CreditorDetails();
        DD.DirectDebitDetails();
        DD.DirectDebitContactDetails();
        DD.DirectDebitNotification();
        DD.DirectDebitReview();
        DD.DirectDebitConfirmation();
    }

    //CFPUR-94 error message
    public void verifylaunchcancelmandate() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.cancelDirectDebitMandate();

    }

    public void verifyCancelDirectDebiterror() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.CancelDDErrorvalidation();
    }

    //CFPUR-4021
    public void verifyDirectDebitOptions() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.verifyDD_options();
    }


    //CFPUR-569
    public void verifyReactivateAllDirectDebitOptions() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.reactivateAllDD();
        //DD.DirectDebitToolTip();
        // DD.DDYourIBANText();
        DD.CreditorDetails();
        DD.ReactivateAllDirectDebitDetails();
        DD.DirectDebitContactDetails();
        DD.DirectDebitNotification();
        DD.DirectDebitReview();
        DD.DirectDebitConfirmation();
    }

    public void verifyDDReactivate() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.reactivateAllDD();
    }

    //CFPUR-568
    public void verifyReactivateNextDirectDebitOptions() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.reactivatenextDD();
        // DD.DirectDebitToolTip();
        DD.CreditorDetails();
        DD.ReactivateNextDirectDebitDetails();
        DD.DirectDebitContactDetails();
        DD.DirectDebitNotification();
        DD.DirectDebitReview();
        DD.DirectDebitConfirmation();
    }

    public void verifyReactiveNextDDOption() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.reactivatenextDD();
    }

    public void verifyReactiveAllDDOption() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.reactivateAllDD();
    }

    public void verifyReactiveNextDDDetails() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.ReactivateNextDirectDebitDetails();
    }

    public void verifyDDReactivateNext() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.reactivatenextDD();
    }

    //CFPUR-556
    public void verifyRefuseAllDirectDebitOptions() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.refuseallDD();
        // DD.DirectDebitToolTip();
        DD.CreditorDetails();
        DD.ReactivateAllDirectDebitDetails();
        DD.DirectDebitContactDetails();
        DD.DirectDebitNotification();
        DD.DirectDebitReview();
        DD.DirectDebitConfirmation();
    }

    public void verifyPaymentsDDPageLaunch() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
    }

    public void verifyRefuseAllDDOption() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.refuseallDD();
    }

    public void verifyDDToolTip() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.directDebitFormToolTip();
    }

    public void verifyDDMaxLength() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.setupDirectDebitMaxLength();
    }

    public void verifyDDErrorText() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.errorMessageValidations();
    }

    public void verifyRefuseAllDDCreditorDetails() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.CreditorDetails();
    }

    public void verifyRefuseAllDDDetails() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.ReactivateAllDirectDebitDetails();
    }

    public void verifyRefuseAllDDContactDetailsOption() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitContactDetails();
    }

    public void verifyRefuseAllDDNotificationOption() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitNotification();
    }

    public void verifyRefuseAllReviewPage() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitReview();
    }

    public void verifyDDRefuseAll() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.refuseallDD();
    }

    //CFPUR-91
    public void verifyRefuseNextDirectDebitOptions() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.refusenextDD();
       //DD.DirectDebitToolTip();
        DD.CreditorDetails();
        DD.ReactivateNextDirectDebitDetails();
        DD.FirstpaymentDD();
        DD.DirectDebitContactDetails();
        DD.DirectDebitNotification();
        DD.DirectDebitReview();
        DD.DirectDebitConfirmation();

    }

    public void verifyRefuseNextDD() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.refusenextDD();
    }

    public void verifyCreditorDetails() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.CreditorDetails();
    }


    public void verifyDDDetails() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.ReactivateNextDirectDebitDetails();
    }
    public void verifysetupDirectDebitMaxLength() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.setupDirectDebitMaxLength();
    }
    public void verifyDirectDebitDetails() throws Exception {
         DirectDebit DD = new DirectDebit(scriptHelper);
         DD.DirectDebitDetails();
    }
    public void verifyDirectDebitContactDetails() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitContactDetails();
    }
    public void verifyDirectDebitNotification() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitNotification();
    }
//    public void verifyDirectDebitReview() throws Exception {
//        DirectDebit DD = new DirectDebit(scriptHelper);
//        DD.DirectDebitReview();
//    }
    public void verifyDDConfirmationPage() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitConfirmation();
    }
    public void verifysentmessagevalidation() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitConfirmation();
        DD.sentmessagevalidation();
    }
    public void verifyDDFirstPayment() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.FirstpaymentDD();
    }

    public void verifyDDContactDetails() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitContactDetails();
    }

    public void verifyDDNotification() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitNotification();
    }

    public void verifyDDReview() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitReview();
    }

    public void verifyDDConfirmation() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitConfirmation();
    }

    public void verifyCancelDDError() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();

    }
    public void verifylaunchPaymentsTab() throws Exception{
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
    }
    public void verifyblockDDservices() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.blockDDservices();
    }



    /**
     * CFPUR-3963
     */
    public void verifycancelextendedTransaction() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.clkAccTypeAndValidateAccount();

    }

    public void verifyCancelButton() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.clickExtendedTransaction();
        DD.clickCancelButton();
        DD.CancelDDpopupContinue();
    }

    /**
     * CFPUR-63
     */

    public void verifyExtendedTransactionForm() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        verifyclkAccTypeAndValidateAccount();
        verifyclickExtendedTransaction();
        verifyclickCancelButton();
        verifyclickContinuePopup();
        // DD.DirectDebitMandateExtenViewForm();
        verifyDirectDebitContactDetails();
        verifyDirectDebitNotification();
        verifyDirectDebitReview();
        verifyDirectDebitConfirmation();
    }

    public void verifyclkAccTypeAndValidateAccount() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.clkAccTypeAndValidateAccount();
    }

    public void verifyclickExtendedTransaction() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.clickExtendedTransaction();
    }

    public void verifyclickCancelButton() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.clickCancelButton();
    }

    public void verifyclickContinuePopup() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.clickContinuePopup();
    }

    public void verifyDirectDebitReview() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitReview();
    }

    public void verifyDirectDebitConfirmation() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.DirectDebitConfirmation();
    }

    /**
     * CFPUR-93 Block accounts
     *
     * @throws Exception
     */
    public void blockDirectdebit() throws Exception {
        verifylaunchPaymentsTab();
        verifyblockDDservices();
        verifysetupDirectDebitMaxLength();
        verifyDirectDebitContactDetails();
        verifyDirectDebitNotification();
        verifyDirectDebitReview();
        verifyDirectDebitConfirmation();
    }

    public void verifyDDBlock() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.blockDDservices();
    }

    /**
     * CFPUR-92 unblock accounts
     *
     * @throws Exception
     */
    public void unblockDirectdebit() throws Exception {
        verifylaunchPaymentsTab();
        verifyunblockDDservices();
        verifysetupDirectDebitMaxLength();
        verifyDirectDebitContactDetails();
        verifyDirectDebitNotification();
        verifyDirectDebitReview();
        verifyDirectDebitConfirmation();
    }
    public void verifyunblockDDservices() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.unblockDDservices();
    }

    public void verifyDDunblock() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.unblockDDservices();
    }

    public void validateDeletePayee() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.validateAndCheckDeletePayee();
    }


    public void verifySelectPayfromPayToSendMoney() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.selectPayfromSendMoney();
        payments.selectPayToSendMoney();
    }

    /*
    CFPUR-330: Currency converter (International Payments)
     */
    public void verifyCurrenmcyConverterErr() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.currencyConvsendMoney();
        payments.selectPayfromSendMoney();
        payments.selectPayToSendMoney();
        payments.currencyConvertorErr();
    }


    public void verifyServerName_FooterMobile() throws Exception {

        MoreOptionsPage moreoptions = new MoreOptionsPage(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        moreoptions.validateOptsTextDisplyd();
        //verifyServerNameFooter_desktop

    }

    /**
     * Function to verify options are displayed on home page for Web Browser
     */
    public void verifyServerName_FooterDesktop() throws Exception {

        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyServerNameFooter_desktop();
    }

    public void clickRegulatoryGoBack() throws InterruptedException {
        HomePage homepage = new HomePage(scriptHelper);
        homepage.regulatoryGoBack();
    }

    public void verifyCurrencySymbolHomePage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.VerifyCurrencySymbolHomePage();
    }

    public void verifyForeignCurrencyTransaction() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
//        transactionPage.VerifyForeignCurrencyTransaction();
    }

    public void verifyAddPayeeDuplicateError() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.selectPaymentOption();
        addPayee.addPayee();
        addPayee.addPayeeDetails();
        addPayee.errorValidation();
    }

    public void verifyAddPayeeDuplicateMFError() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.errorValidation();
    }

    /**
     * Profile managment : 4408 - 4327
     */
    public void verifyProfileAddMobile() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        verifyProfilePageNavigation();
        verifyAddMobileNumber();
        enterConfirmationPin();
        //ProfilePage.enterRequiredPinMobile();
        verifyProfileSuccessMessage();
    }

    //below methods has been carved out from the above 'verifyProfileAddMobile' method
    public void verifyAddMobileNumber() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.addProfileMobileNumber();
    }

    public void verifyProfilePageNavigation() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.navigateToProfilePage();
    }

    public void verifyProfileSuccessMessage() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.validateProfileSuccessMessage();
    }

    // This method is to validate the changed mobile number
    public void verifyUpdatedMobileNumber() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.validateUpdatedMobileOnSuccessPage();
    }

    /**
     * Profile managment : 4408 - 305
     */
    public void verifyProfileAmendMobile() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.navigateToProfilePage();
        ProfilePage.updateProfileMobileNumber();
        enterConfirmationPin();
        verifyProfileSuccessMessage();
    }

    public void verifyProfileAmendMobileNumber() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.updateProfileMobileNumber();
    }

    //below method has been carved out from the above 'verifyProfileAddMobile' method
    public void verifyProfileUpdateMobile() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.updateProfileMobileNumber();
    }
//below methods has been carved out from the above 'verifyProfileAmendMobile' method

    public void verifyExistingMobileNumber() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.ValidateExiProfMobNum();
    }

    public void verifyUpdateMobile() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.updateProfileMobile();
    }
    public void verifyPinToProfileNav() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.validatePinToProfileNav();
    }

    public void verifyProfileConfirmPIN() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        enterConfirmationPin();
    }

    public void verifyUpdatePrivacyTextFunc() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.verifyUpdatePrivacyText();
    }

    public void verifyAddPrivacyTextFunc() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.verifyUpdatePrivacyText();
    }

    /**
     * manage Payees : 112
     */
    public void verifyManagePayees() throws Exception {
        PaymentsManagePayees ManagePayees = new PaymentsManagePayees(scriptHelper);
        ManagePayees.verifyManagePayeePage();
        ManagePayees.verifyManagePayeeFunctionality();
    }


    /**
     * Profile managment : 4408 - 4336
     */
    public void verifyProfileAddEmail() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        HomePage homepage = new HomePage(scriptHelper);
        ProfilePage.navigateToProfilePage();
        ProfilePage.addProfileEmail();
        homepage.enterRequiredPin();
        if(deviceType().equals("mw.")){
            acceptPushNotification();
        }
//        ProfilePage.validateNotificationPageSCA();
        ProfilePage.validateProfileSuccessMessage();
    }

    /**
     * Profile managment : 4408 - 4338
     */
    public void verifyProfileAmendEmail() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        HomePage homepage = new HomePage(scriptHelper);
        ProfilePage.navigateToProfilePage();
        ProfilePage.updateProfileEmail();
        homepage.enterRequiredPin();
        ProfilePage.validateProfileSuccessMessage();
    }

    public void verifyProfileAmendEmailAddress() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.updateProfileEmail();
    }

    public void verifyExistingEmailAddress() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.validateExistEmail();
    }

    public void verifyUpdateEmail() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
    //    ProfilePage.navigateToProfilePage();
        ProfilePage.updateProfileEmailID();
    }

    /**
     * Profile managment : 4408 :: 4327, 305, 4338, 4336: Error
     */
    public void verifyProfileManagementError() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.navigateToProfilePage();
        ProfilePage.validateMobilePhoneEmailErrors();
    }

    public void verifyChangeaddress_EasySteps_popup() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.changeAddress_EasySteps_ToolTip();
    }

    public void verifyChangeaddress_EasySteps_popupE2E() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.navigateToProfilePage();
        ProfilePage.validateMobilePhoneEmailErrors();
        // changeAddress.launchChangeAddress();
        changeAddress.changeAddress_EasySteps_ToolTip();
    }


    /**
     * CFPUR : 4078 , 1185
     **/
    public void verifyPaperlessPromotionNavigation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyNavigateToAnyAccount();
        homePage.verifyPaperlessStmntPromotion();
    }

    /**
     * CFPUR : 4078 , 1185
     **/
    public void verifyPaperlessStatementPromotion() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyPaperlessStmntPromotion();
    }

    /**
     * Smoke Test : Accounts
     **/
    public void verifyAccountsSmokeTest() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyNavigateToAccountPageAnyAccount();
        homePage.verifyStatementsSmoke();
        homePage.verifyNickNameSmoke();
        homePage.verifyChequeSearchSmoke();
    }

    /**
     * Smoke Test : Accounts : transactions
     **/
    public void verifyAccountsTransactionSmokeTest() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.TransactionsDisplayedSmoke();
        transaction.InprogressTodaysTransactionsDisplayed();
    }

    /**
     * Smoke Test : Manage Statements
     **/
    public void verifyManageStatementsSmokeTest() throws Exception {
        ServicesManageStatement servicesManageStatement = new ServicesManageStatement(scriptHelper);
        servicesManageStatement.verifyManageStatementsSmoke();
    }

    public void validateSendMoneyDetails() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.sendmoney();
    }

    public void validateInternationalPaymentsReviewPage() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.internationalPaymentsReviewPage();
    }

    /*public void validateInternationalPaymentsReviewPage_E2E() throws Exception{
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.internationalPaymentsReviewPageE2E();
    }*/
    public void validateSendMoneyConfirmationPage() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.sendMoneyConfirmationPage();
    }

    /**
     * CFPUR_5127_302_5123_5124_5125
     **/
    public void verifyMonthlySaverEligiblity() throws Exception {
        MonthlySaverAccountPage MonthlySaver = new MonthlySaverAccountPage(scriptHelper);
//        MonthlySaver.NavigateMonthlySaverAccountPage();
//        MonthlySaver.verifyMonthlySaverEligibilityPage();
//        MonthlySaver.verifyGeneralReviewPage();
        MonthlySaver.verifyMonthlySaverForm2();
    }

    public void verifyConfirmationMTUPagebackLink() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        addTopUp.launchPaymentsTab();
        addTopUp.selectAccountPayFrom();
        addTopUp.selectProvider();
        addTopUp.selectAmount();
        addTopUp.enterPhoneNumber();
        addTopUp.enterConfirmNumber();
        addTopUp.textImportantInformation();
        addTopUp.clickContinue_MTU();
        addTopUp.MobileTopUpReview();
        addTopUp.clickContinue_MTU();
        addTopUp.mobileTopUpPIN();
        addTopUp.clickConfirm();
        homePage.verifyDeviceListSCA();
        homePage.verifyHomePage();
        addTopUp.MobileTopUpSuccessScreen();
        homePage.verifyBackArrow();
    }

    /*
    CFPUR-1218 : Pay a Bill BOI Credit Card (processing time warning)
     */
    public void verifyCreditCardWarningMsg() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyCreditCardWarningtxt();
    }

    /*
   CFPUR-72 : Pay a Bill BOI Mortgage (charges warning)
    */
    public void verifyBOIMortgageWarningMsg() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyBOIMortgageWarningtxt("SendMoney.lnkBOIMortgageWarningtxt");
    }

    /*
   CFPUR-342 : Add a Payee NI/GB - PAD2 content changes
    */
    public void verifyPAD2Content() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyPAD2Contenttxt();
        payments.verifyBOIMortgageWarningtxt("xpath~//*[@class='boi-alert-wrap boi-alert-wrap--info boi-alert-msg--blue']");
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyLinkAndItsNavigation();
    }

    /*
    CFPUR-5509 Manage Alerts account
     */
    public void verifyManageAlertsaccount() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        //   Alertsaccount.clickonmoreInfotextlink();
    }

    public void verifyNOManageAlertsaccount_ROI() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyNoMAA_ROI();
    }

    /*
   CFPUR-5531 Manage Alerts account
    */
    public void verifyManageAlertsToggle() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        Alertsaccount.accountlist();
    }

    /*
   CFPUR-5531 Manage Alerts account click on More info link
    */
    public void verifyManageAlertsInfoTextLink() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        Alertsaccount.clickonmoreInfotextlink();
    }

    /*
   CFPUR-5531 Manage Alerts account click on Off Toggle Button
    */
    public void verifyManageAlertsOffToggle() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        Alertsaccount.offTogglebutton();
    }

    /*
  CFPUR-5531 Manage Alerts account click on ON Toggle Button
   */
    public void verifyManageAlertsONToggle() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        Alertsaccount.oNTogglebutton();
    }

    /*
 CFPUR-5531 Manage Alerts account click on ON Toggle Button
  */
    public void verifyManageAlertsContinueButton() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        Alertsaccount.accountlist();
        Alertsaccount.verifycontinuebtton();
    }

    /*
   CFPUR-5546 Manage Alerts account confirm button
    */
    public void verifyManageAlertsConfirm() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        Alertsaccount.accountlist();
        Alertsaccount.verifyConfirmFunction();
    }

    /*
    CFPUR-5546 Manage Alerts account Cross button
     */
    public void verifyMAAcross() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        Alertsaccount.accountlist();
        Alertsaccount.verifycontinuebtton();
        Alertsaccount.verifycrossbutton();
    }

    /*
    CFPUR-5546 Manage Alerts account Go back button
     */
    public void verifyMAAGoBack() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        Alertsaccount.accountlist();
        Alertsaccount.verifycontinuebtton();
        Alertsaccount.verifyGobackbutton();

    }

    /*
    CFPUR-7249: To Check absence of Manage Alert Link on account page
    */

    public void verifyManageAlertaccountpage() throws Exception {

        HomePage homePage = new HomePage(scriptHelper);
        // homePage.verifyaccountsManageAlert();
        // homePage.verifyManageAccountLink();
        homePage.clkAccTypeAndValidateAccount();
        homePage.managealertslinkNotPresent();
    }

    /*
    CFPUR-283 : SEPA payment: input/review steps (NI/GB account holder)
     */
    public void verifySEPAPaymentJourneyNIGB() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.SendMoneySEPA();
    }

    public void verifySendMoneySEPAAmountErr() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.SendMoneySEPAAmountErr();
    }

    public void verifySendMoneySEPAReferenceErr() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.SendMoneySEPAReferenceErr();
    }

    public void verifySendMoneySEPAMsgtoPayeeErr() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.SendMoneySEPAMsgtoPayeeErr();
    }

    /*
   CFPUR-1292,1294-FAQs and Cookies link verification
    */
    public void verifyfooterlinks() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyfooterlinknaviagtion();

    }
    public void verifyfooterlinkspage() throws Exception {
        ContactUsPage CP = new ContactUsPage(scriptHelper);
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        CP.verifyfooterlinks();
        AAQ.verifytab();
    }

    public void verifyfooterlinkspagemobile() throws Exception{
        HomePage homePage = new HomePage(scriptHelper);
        ContactUsPage CP = new ContactUsPage(scriptHelper);
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        homePage.clickmore();
        CP.clickFAQsprelogin();
//        AAQ.verifytab();
    }

    /*
  CFPUR-5472-to vrify Go back link
   */
    public void verifyGoBackonMTUPage() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        addTopUp.launchPaymentsTab();
        homePage.gobacknavigation();
        addTopUp.launchPaymentsTab();
        addTopUp.selectAccountPayFrom();
        addTopUp.selectProvider();
        addTopUp.enterPhoneNumber();
        addTopUp.enterConfirmNumber();
        addTopUp.selectAmount();
        addTopUp.clickContinue_MTU();
        addTopUp.verifyDetails();
        addTopUp.clickContinue_MTU();
        verifyEnterRequiredPin();
        //desktopPushAccept();
        addTopUp.MobileTopUpSuccessScreen();

    }


    /*
   CFPUR-609/7085-Easy Way tip for cancel DD
    */

    public void verifyCancelEasyTip() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.cancelDirectDebitMandate();
        DD.CancelDDEasyWay();
       /* // DD.DirectDebitToolTip();
        DD.CreditorDetails();
        DD.DirectDebitDetails();
        DD.DirectDebitContactDetails();
        DD.DirectDebitNotification();
        DD.DirectDebitReview();
        DD.DirectDebitConfirmation();*/

    }

    /*
    CFPUR-6370 Services options
     */

      /*
    CFPUR-7318 Remove 'Open Monthly Savers Account' on services page
     */

    public void verifyinvalidservicesoptions() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        //  Alertsaccount.Verifyplaceholders();
    }

    /*
   CFPUR-6503 Services options
    */
    public void verifyinvalidstamentsoptions() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        //  Alertsaccount.Verifystatementsoptions();
    }


    /*
    CFPUR-490: International Payment NI/GB - PAD2 content changes
     */
    public void verifyInternationalPaymenttxt() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyBOIMortgageWarningtxt("SendMoney.internationalPAD2");
        payments.verifyFAQtxt();
    }

    /*
    CFPUR-5113: Present message if the user only has accounts blocked for payment
     */

    public void verifytxtBlockedpaymentaccounts() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails("SendMoney.pageView");
    }

    /*
    CFPUR-5113: Continued.. Presence of PAY NOW button on transactions page
     */
    public void verifyTransactionsPayNowbutton() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyPayNowbutton();
    }


    /*CFPUR-66: Credit Card - 'Pay Now' quick link (account level page) - card is a payee
     */

    public void validatePayNowpopup() throws Exception {
        TransactionsPage Transaction = new TransactionsPage(scriptHelper);
        Transaction.validatePayNowDetails();
    }

    public void validateAddbillpopup() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyPayNowPopUp(deviceType() + "Transactions.PayNow");
    }

     /*
    CFPUR-66: Credit Card - 'Pay Now' quick link (account level page) - card is a payee
     */

    public void validateAddBillPayeePopup() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyAddBillPayee();
    }

         /*
    CFPUR-4421: Credit Card - 'Pay Now' quick link (account level page) - card is a payee
     */

    public void validateAddBillPayeePopupOperation() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyAddBillPayeeOperationOnPopup();
    }

    /*
    CFPUR-1194: Credit Card Bill Payment - originating from Pay Now link
     */
    public void validatePayNowpopupBtn() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyPayNowPopUpbtnNavigation();
    }

    /*
    CFPUR-3605 :SCA UPLIFT- International transfer: SCA/success steps (all jurisdictions)
     */
    public void validateInternationalPaymentsConfirmationPage() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.checkInternationalPaymentsConfirmationPage();
    }


    /*
    CFPUR-78 : Pay a Bill: input/review steps (all jurisdictions)
     */;
    public void validateBillPaymentsInputDetailsPage() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.sendMoneyDetailsBill();
        if(dataTable.getData("General_Data", "Nickname").equals("")){
            payments.verifyCreditCardWarningtxt();
        }
        payments.inputdetailsBillOperation();
    }

    public void validateBillPaymentsReviewPage() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.billPaymentsReviewPage();
    }

    public void validateBillPaymentsConfirmationPage() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.checkBillPaymentsConfirmationPage();
    }


    public void validateReviewPage() throws Exception{
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyReviewPage();
    }

    public void validateUKDomReviewPage() throws Exception{
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyUKDomReviewPage();
    }

    /*
    CFPUR-83: UK Domestic Transfer: input/review steps
    Verify Send Money details page for UK Domestic Payment
     */
    public void validatesendMoneyDetailsUKDomestic() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.sendMoneyDetailsUKDomestic();
    }

    /*
    CFPUR-83: UK Domestic Transfer: input/review steps
    Verify UK Domestic Review Page
     */
    public void validateUKDomesticPaymentsReviewPage() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyUKDomesticPaymentsReviewPage();
    }

    /*
    Function: To validate the errors for UK Domestic Payee input screen
     */

    public void verifySendMoneyUKDOMAmountErr() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.SendMoneyUKDomesticAmountErr();
    }

    /*
    CFPUR-3610: SCA UPLIFT - UK Domestic Payment: SCA/Success steps
     */
    public void validateUKDomesticPaymentsConfirmationPage() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.checkUKDomesticPaymentsConfirmationPage();
    }


    public void verifyGoBackonTBA() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        transBetAccnt.launchTransferBetAccounts();
        homePage.gobacknavigation();
        transBetAccnt.launchTransferBetAccounts();
        transBetAccnt.selectAccountPayFrom();
        transBetAccnt.selectAccountPayTo();
        transBetAccnt.enterAmount();
        transBetAccnt.clickContinue_TBA();
        verifyTBADetails();
        verifyClickContinue_TBA();
        verifyPIN_TBA();
        transBetAccnt.clickConfirm_TBA();
        desktopPushAccept();
        verifyRequestSentValidation();
        homePage.gobacknavigation();
}

    public void verifyTransferbetweenAccount() throws Exception {
        TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        transBetAccnt.launchTransferBetAccounts();
        transBetAccnt.selectAccountPayFrom();
        transBetAccnt.selectAccountPayTo();
        transBetAccnt.enterAmount();
        transBetAccnt.clickContinue_TBA();
        verifyMobileTopUpReview();
        //transBetAccnt.clickContinue_TBA();
        transBetAccnt.PIN_TBA();
        transBetAccnt.clickConfirm_TBA();


    }


    public void verifyGoBackonChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        changeAddress.launchChangeAddress();
        homePage.gobacknavigation();
        changeAddress.modifyCheckboxes();
        changeAddress.selectCountryTextbox();
        changeAddress.enterAddressLinedetails();
        changeAddress.clickContinue();
        homePage.gobacknavigation();
        //changeAddress.changeAddressReview();
        // changeAddress.verifyReviewPageOrder();
        changeAddress.clickConfirm();
        homePage.gobacknavigation();
        changeAddress.ChangeAdressPIN();
        changeAddress.clickSCAConfirm();
        homePage.gobacknavigation();
        //homePage.verifyDeviceListSCA();
        //homePage.verifyHomePage();
        // homePage.verifyBackArrow();
        //changeAddress.verifyreferencetimestamp();
        //homePage.verifyBackArrow();
    }

    /*CFPUR-3310 mobile errors*/

    public void verifyMobileFieldnavigation() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);

        withdrawFunds.clicklink();
        withdrawFunds.navigationMobile();

    }

    public void verifyMobileFielderror() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        withdrawFunds.enterInvalidMobilenumber();
    }


    public void verifyGoBackonWithdrawFunds() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.clicklink();
        homePage.gobacknavigation();
        withdrawFunds.launchWithdrawFunds();
        withdrawFunds.clicklink();
        withdrawFunds.enterAmount();
        withdrawFunds.clickContinue();
        withdrawFunds.selectfromdropdown();
        withdrawFunds.enterMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
        withdrawFunds.enterRequiredPin();
        withdrawFunds.clickConfirm();
        withdrawFunds.withdrawfundsSuccessScreen();
    }

   /* CFPUR-6724-To verify Cancel DD popup */

    public void verifyExtendedTransactionFormCancel() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.clkAccTypeAndValidateAccount();
        DD.clickExtendedTransaction();
        DD.clickCancelButton();
        DD.CancelDDpopup();
        DD.CancelDDpopupClose();
        DD.clickCancelButton();
        DD.CancelDDpopupGoback();
        DD.clickCancelButton();
        DD.CancelDDpopupContinue();
    }

    public void verifyPaymentsTabOptions() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.clicksendmoney();
        payments.selectPayfromSendMoney();
        payments.selectPayToSendMoney();
        payments.sendMoneyDetailsBill();
    }

    public void clickPaymentTab() throws Exception {
        if (isElementDisplayed(getObject("w.home.tabPayments"), 5)) {
            report.updateTestLog("NickNameOnPages", "Menu Tab 'Payments' is displayed", Status_CRAFT.PASS);
            click(getObject("w.home.tabPayments"), "Click on Menu Tab 'Payments'");
        } else {
            report.updateTestLog("NickNameOnPages", "Menu Tab 'Payments' is NOT displayed", Status_CRAFT.FAIL);
        }
    }

    /*
    CFPUR-283 : SEPA payment: input/review steps (NI/GB account holder)
     */
    public void validatesendMoneyDetailsSEPA() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.sendMoneyDetailsSEPA();
        payments.verifyPaymentsOptions();
    }

    /*
    CFPUR-4521
    */
    public void verifyUnavailableCreditCard() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyCreditCardAccountDetails();
    }

    /*CFPUR-5548 Manage Alerts account*/
    public void verifyRequestScreen() throws Exception {
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        Alertsaccount.accountlist();
        Alertsaccount.verifyConfirmFunction();
        Alertsaccount.Verifycontactinfo();
    }

    public void verifyCMAsentmessagevalidation() throws Exception{
        ManageAlertsAccounts Alertsaccount = new ManageAlertsAccounts(scriptHelper);
        Alertsaccount.verifyAndClickServicesOption();
        Alertsaccount.accountlist();
        Alertsaccount.verifyConfirmFunction();
        Alertsaccount.Verifycontactinfo();
        Alertsaccount.sentmessagevalidation();
        Alertsaccount.CMAsentmessagetextval();
    }


/*CFPUR-5724 Go back link on various screens*/

    public void verifyGoBackonDD() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        DD.launchPaymentsTab();
        DD.refuseallDD();
        homePage.gobacknavigation();
        DD.launchPaymentsTab();
        DD.refuseallDD();
        verifyDDToolTip();
        DD.CreditorDetails();
        DD.ReactivateAllDirectDebitDetails();
        DD.DirectDebitContactDetails();
        DD.DirectDebitNotification();
        DD.DirectDebitReview();
        DD.DirectDebitConfirmation();

    }

    public void verifyMonthlySaverFormPageError() throws Exception {
        MonthlySaverAccountPage MonthlySaver = new MonthlySaverAccountPage(scriptHelper);
        MonthlySaver.NavigateMonthlySaverAccountPage();
        MonthlySaver.FormPageErrorMessageValidation();
    }

    public void verifyDDcontentchanges() throws Exception {
        DirectDebit DD = new DirectDebit(scriptHelper);
        DD.launchPaymentsTab();
        DD.ddContentChangesRefuse();
        DD.ddContentChangesReactivate();
        DD.ddcontentChangesBlock();
    }

    public void verifyAddressChangeUpdates() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.modifyCheckboxes();
        changeAddress.selectCountryTextbox();
    }

    public void verifychangesChangeAddress() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.verifyChangeAddressScreen();
        changeAddress.verifyAccountNotListedLinks();
        // changeAddress.modifyCheckboxes();
    }
    public void verifyselectCountryTextbox()throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.selectCountryTextbox();
    }


    //CFPUR-5655 changes in Contact Us page
    public void verifychangesContactUs() throws Exception {
        ContactUsPage contactus = new ContactUsPage(scriptHelper);
        contactus.launchContactUs();
        contactus.verifyContactNumberSection();
        contactus.verify365BankingTab();
        contactus.verifyCCTab();
        contactus.verifySocialMediaSection();
    }


    /*
    CFPUR-6763: Remove future dated payments menu item from Payments landing page
     */

    public void validatefuturedatedoptions() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.clickPaymentsPage();
        payments.verifyfuturedatedoptions();
    }

    /*
    CFPUR-6758: No Txns since last issued statement - make statements link clickable
     */
    public void
    validateViewStatementsCredit() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        transactionPage.verifyViewStatements_Credit();
    }

    public void validateViewStatementsBkk() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        transactionPage.verifyViewStatements_BKK();
    }

    /*
    CFPUR-1669: BIC / IBAN calculator
     */
    public void validateBICIBANCalculator() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyBICIBANCalculator();
    }

    /*
    To select an account from Pay From dropdown for Cross Customer
     */
    public void selectPayeeCountry() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.choosePayeeCountry();
    }

    /*
    CFPUR-6607: Add SEPA payee country information to manage payee page
     */
    public void validatePayeeCountry() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyPayeeCountry();
    }

    public void verifyCallFunctionality() throws Exception {
        ContactUsPage call = new ContactUsPage(scriptHelper);
        call.verifyContactNumberSection();
    }

    public void verifyAccountTypeAndNavigate() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAccountClickAndNavigate();
    }

    public void verifyCreditCardAndNavigate() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyCreditCardClickAndNavigate();
    }

    /**
     * Mobile App Automation :
     */
    public void verifyFooterNavigationApp() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyFooterNavigation();
    }

    public void verifyInprogressTransactionsIconDisplayed() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.VerifyInprogressTransactionDetails();
    }

    public void verifyInprogressTransactionsAmountDisplayed() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.VerifyAmountPatternInTransactions();
    }

    public void verifyViewCompletedTransactionsDisplayed() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.VerifyCompletedTransactionDetails();
    }

    public void verifyViewDetailsTransactionsDisplayed() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.VerifyExtendedTransactions();
    }

    public void verifyCompletedTrasAmountPattern() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.VerifyAmountPatternCompletedTransactions();
    }

    public void verifyCountDebitCreditTransaction() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.VerifyDebitCreditTransactionCount();
    }

    public void verifyErrorTextforDuplicatePolicy() throws Exception {
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.verifyErrorMessageforDuplicatePolicy();
    }

    public void LoginProvisionedDevice() throws Exception {
        pinPageLogin_sca();
        homePage_sca();
    }

    public void verifyPolicyConfirmationPage() throws Exception {
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.verifyAddPolicyConfirmationPage();
    }

    public void acceptPushNotification() throws Exception {
        SCA_MobileAPP scaMobileApp = new SCA_MobileAPP(scriptHelper);
        scaMobileApp.PushNotification_Acccept();
        waitForPageLoaded();
    }

    public void declinePushNotification() throws Exception {
        SCA_MobileAPP scaMobileApp = new SCA_MobileAPP(scriptHelper);
        scaMobileApp.PushNotification_Decline();
    }

    public void verifyXonAddAccountPolicy() throws Exception {
        AccountPolicyPage addAccPage = new AccountPolicyPage(scriptHelper);
        addAccPage.EnterAddAccPolicyFormCurrAccAndClear();
    }

    public void verifyXonChangeAddressPage() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.modifyCheckboxes();
        //changeAddress.selectAccountsChangeAddress();
        changeAddress.verifyXonChangeAddressDetails();
    }

    /**
     * Mobile App Automation Method : To Verify Login to home page after device provising
     * Update on     Updated by     Reason
     * 27/05/2019    C966119        for mobile Automation
     */
    public void verifyLoginToHomePage() throws Exception {
        SCA_MobileAPP SCAPage = new SCA_MobileAPP(scriptHelper);
        SCAPage.welcomeScreen();
        HomePage homePage = new HomePage(scriptHelper);
        homePage.enterRequiredPin();
        homePage_sca();
    }

    public void verifyLinksNavigationMobile() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyLinksNavigation();
    }

    public void validateAddSEPAPayeeSelectCountry() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.AddSEPAPayeeSelectCountry();
    }

    public void validateSEPApayeeFormDetails() throws Exception {
        PaymentsManagePayees payee = new PaymentsManagePayees(scriptHelper);
        payee.verifySEPApayeeFormDetail();
    }

    public void enterNewPinAddPayee() throws Exception {
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.enterRequiredPin();
    }

    public void verifySelectAddPersonOrBillOnSendmoneyPage() throws Exception {
        PaymentsManagePayees paymentsManagePayees = new PaymentsManagePayees(scriptHelper);
        if (deviceType.equals("MobileWeb")) {
            clickJS(getObject("xpath~//*[@class='boi_label'][text()='Select payee']/ancestor::a"), "Clicked on Select Payee link on Send Money Page");
        }
        paymentsManagePayees.addPayee();
    }

    public void validateUnrecoverableMainFrameError_Patter1() throws Exception {
        RecoverableUnrecoverableErrors errPage = new RecoverableUnrecoverableErrors(scriptHelper);
        errPage.UnrecoverableMainFrameError_Patter1();
    }

    public void verifySelectPayFromOnSendMoney() throws Exception {
        PaymentsManagePayees paymentsManagePayees = new PaymentsManagePayees(scriptHelper);
        paymentsManagePayees.selectPayFromOnSendMoney();
    }

    public void verifyMainFrameErrors() throws Exception {
        RecoverableUnrecoverableErrors errPage = new RecoverableUnrecoverableErrors(scriptHelper);
        errPage.validateMainFrameErrors();
    }

    public void verifyReduceClutter() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.VerifyPaperlessStatementPromotionInfoMobile();
    }

    public void verifyReduceClutterClick() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.VerifyPaperlessStatementPromotionNavigationMobile();
    }

    public void verifyReduceClutterOnOFfPaperChecked() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.VerifyToggleOnOFfPaperChecked();
    }

    public void verifyCheckOnOffAfter() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.VerifyPaperlessStatementPromotionAfterChange();
    }

    public void SetPaperOnAfterChangeMobile() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifySetPaperOnAfterChangeMobile();
    }

    public void verifyFetchOnAccount() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.VerifyAndGetAccountHavingPaperON();
    }

    public void verifyPayFrom_SendMoney() throws Exception {
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.payFrom_SendMoney();
    }

    public void verifyPayTo_SendMoney() throws Exception {
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.payTo_SendMoney();
    }

    public void verifyAmount_SendMoney() throws Exception {
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.amount_Sendmoney();
    }
    public void verifyReasonForPayment() throws Exception{
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.reasonForPayment();
    }

    public void verifyContinue_SendMoney() throws Exception {
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.continue_SendMoney();
    }

    public void verifyReference_SendMoney() throws Exception {
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.reference_SendMoney();
    }

    public void verifyMessageToPayee_SendMoney() throws Exception {
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.VerifyMessageToPayee();
    }

    public void verifyErrorMsg_SendMoney() throws Exception {
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.errorMsg_SendMoney();
    }

    public void verifyReviewPage_sendMoney() throws Exception {
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.reviewPage_SendMoney();
    }

    public void verifySuccess_sendMoney() throws Exception {
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.success_SendMoney();
    }

    public void verifyClickBackToHomeButton() throws Exception{
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.clickBackToHomeButton();
    }

    public void verifyFetchAndValidatePayee() throws Exception {
        PaymentsManagePayees PayeePage = new PaymentsManagePayees(scriptHelper);
        PayeePage.IdentifyPayeeFromManagePayeeList();
    }

    public void verifyCopyContentOnPaperOnOFff() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.VerifyCopyOnTurnPaperOnOff();
    }

    public void verifyPolicyLink() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyPolicyDetailsLink();
    }

    public void verifyAddEmail() throws Exception {
        ServicesManageStatement ServicesManage = new ServicesManageStatement(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        ServicesManage.verifyAndClickServicesOption();
        homePage.verifyAddEmailbutton();
    }

    public void verifyRecoverableErrServiceDeskPattern3() throws Exception {
        RecoverableUnrecoverableErrors errPage = new RecoverableUnrecoverableErrors(scriptHelper);
        errPage.RecoverableErrorServiceDeskFunctionsPatter3();
    }

    public void verifyRecoverableErrorNonTransactionalPattern4() throws Exception {
        RecoverableUnrecoverableErrors errPage = new RecoverableUnrecoverableErrors(scriptHelper);
        errPage.RecoverableErrorNonTransactionalFunctionsPattern4();
    }


    //CFPUR-6756
    public void verifyElementNotDisplayedForROI() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementNotDisplayed();
    }

    public void verifyclickgoback() throws Exception {
        PaymentsManagePayees payees = new PaymentsManagePayees(scriptHelper);
        payees.clickgoback();
    }

    public void verifySendMoneyUK_AmountInlineError() throws Exception {
        PaymentsManagePayees payees = new PaymentsManagePayees(scriptHelper);
        payees.amountFieldValidation_Inline();
    }

    public void verifySendMoneyUK_AmountMainframeError() throws Exception {
        PaymentsManagePayees payees = new PaymentsManagePayees(scriptHelper);
        payees.amountFieldValidation_Mainframe();
    }

    public void verifySendMoneyUKvalidation() throws Exception {
        PaymentsManagePayees payees = new PaymentsManagePayees(scriptHelper);
        payees.verifyCurrencyandReference();
    }

    public void verifyCaptureBKKAccountsDetails_Homepage() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.captureBKKAccountsDetails_Homepage();
    }

    public void verifyClickPay() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.clickPay();
    }

    public void verifyValidateAccountsHomeVsPayFrom() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.validateAccountsHomeVsPayFrom();
    }

    public void verifyInvalidError_UserID() throws Exception {
        SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
        sca.invalidErrors_UserID();
    }

    public void verifyInvalidError_DOB() throws Exception {
        SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
        sca.invalidErrors_DOB();
    }

    public void verifyAddUKDomPayeeDetails() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.addUKDomPayeeDetails();
    }

    public void verifyAddPayeeReviewPage_UKDomestic() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyUKDomesticAddPayeeReviewPage();
    }

    public void verifyAddPayeeConfirmationPage_UKDomestic() throws Exception {
        verifyPayeeAddConfirmation();
    }

    public void verifyPayeeCountryPosition_ManagePayee() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.payeeCountryPosition_ManagePayee();
    }

    public void verifySCADeclinePageOperation() throws Exception {
        SCA_DeclinePage declinePage = new SCA_DeclinePage(scriptHelper);
        declinePage.SCADeclinePageOperation();
    }

    public void verifyViewExchangeRate_SendMoney() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.viewExchangeRate_SendMoney();
    }

    public void verifyAddPayeeFormToolTip() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.addPayeeFormToolTip();
    }

    public void verifyMTURecovError() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        addTopUp.launchPaymentsTab();
        homePage.gobacknavigation();
        addTopUp.selectAccountPayFrom();
        addTopUp.selectProvider();
        addTopUp.enterPhoneNumber();
        addTopUp.enterConfirmNumber();
        addTopUp.selectAmount();
        //addTopUp.textImportantInformation();
        addTopUp.clickContinue_MTU();
    }

    public void verifyDeletePayee() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.deletePayee();
    }


    public void verifyAndValidateError() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.validateAndVerifyErrors();
    }

    /*
    Function: To enter the invalid pin wherever required
    */
    public void verifyinvalidPin() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.enterinvalidPin();
    }

    public void verifyGoBack() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.gobacknavigation();
    }

//    public void verifyProfileIcon() throws Exception {
//        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
//        payments.verifyProfileIconheader();
//    }


    /*
    Function: To verify if Pay button is enabled for a payee in Manage Payees page for Mobile device
     */
    public void paybtnenabled() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.paybtnenabledmobile();
    }

    /*
    Function: To select a pay from account for add payee journey for Cross customer
     */
    public void selectaccountpayfromCross() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.selectaccountpayfrom();
    }

    /*
    Function: To Verify the Authentication Failure Page and Its Operation
     */
    public void validateAuthFailure() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyAuthFailure();
    }

    /*
    Function: To verify the More section is appearing in order (CFPUR-7045)
     */
    public void validateMoreSection() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyMoreSection();
    }

    public void verifyCapturePinPageError() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.capturePinPageError();
    }

    public void verifySelectChangeAddress() throws Exception {
        ProfileManagment profile = new ProfileManagment(scriptHelper);
        profile.selectChangeAddress();
    }

    //Go back Button
    public void verifyGoBackButton() throws Exception {
        PaymentsManagePayees payees = new PaymentsManagePayees(scriptHelper);
        payees.goBackButton();
    }

    public void validatePayToMobileNotPresent() throws Exception {
        PayToMobilePage payToMobile = new PayToMobilePage(scriptHelper);
        payToMobile.verifyPayToMobileNotPresent();
    }

    public void verifyAmendExistingMobileNumber() throws Exception {
        ProfileManagment profile = new ProfileManagment(scriptHelper);
        profile.amendExistingMobileNumber();
    }

    public void verifyPayToMobAlredayRegError() throws Exception {
        PayToMobilePage payToMobile = new PayToMobilePage(scriptHelper);
        payToMobile.validateErrorMobileAlreadyRegistered();
    }

    public void verifyExistingMobileDigits() throws Exception {
        ProfileManagment profile = new ProfileManagment(scriptHelper);
        profile.existingMobileDigits();
    }

    public void verifyAmendExistingEmailAddress() throws Exception {
        ProfileManagment profile = new ProfileManagment(scriptHelper);
        profile.amendExistingEmailAddress();
    }

    public void selectPayToMobileAccount() throws Exception {
        PayToMobilePage payToMobilePage = new PayToMobilePage(scriptHelper);
        payToMobilePage.PayToMobileAccountSelection();
    }

    public void verifyPayToMobileForm() throws Exception {
        PayToMobilePage payToMobilePage = new PayToMobilePage(scriptHelper);
        payToMobilePage.PayToMobileFormPage();
    }

    public void verifyEnterPTMDetails() throws Exception {
        PayToMobilePage payToMobilePage = new PayToMobilePage(scriptHelper);
        payToMobilePage.enterPTMDetails();
    }

    public void verifyAndUpdateMobNumber() throws Exception {
        PayToMobilePage payToMobilePage = new PayToMobilePage(scriptHelper);
        payToMobilePage.verifyFlowUpdateMobile();
    }

    public void verifyPTMErrorText() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyErrorText_Details();
    }

    public void verifyPTMReviewPage() throws Exception {
        PayToMobilePage payToMobilePage = new PayToMobilePage(scriptHelper);
        payToMobilePage.validatePTMReviewPage();
    }

    public void verifyPTMSuccessPage() throws Exception {
        PayToMobilePage payToMobilePage = new PayToMobilePage(scriptHelper);
        payToMobilePage.validatePTMSuccessPage();
    }

    public void verifyPTMConfirmationPage() throws Exception {
        PaymentsManagePayees PayTomobile = new PaymentsManagePayees(scriptHelper);
        PayTomobile.validatePTMConfirmationPage();
    }

    public void verifyMainFrameError() throws Exception {
        PaymentsManagePayees payees = new PaymentsManagePayees(scriptHelper);
        payees.validateMainFrameErrors();
    }

    //CFPUR-88
    public void verifyPayToMobileRegLandingPage() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyPTMRegLandingPage();
    }

    public void verifyAddPayee_ManagePayee() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.addPayee_ManagePayee();
    }


    /*
         CFPUR-2372 Order a copy of Statement tile
     */
    public void verifyOrderacopyofStatement() throws Exception {
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.ManagestetmentsNavigation();
        statement.OrderacopyOfstatement();
    }
    /*
        CFPUR-8774 Order a copy of Statement landing page
    */
    public void verifyOrderacopyofStatementLandingpage() throws Exception{
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        HomePage home = new HomePage(scriptHelper);
        statement.OrderacopyOfstatementLandingPage();
        home.verifyCheckContentPresent(deviceType()+ "OCS.textvalidationparent");
        statement.clickContinue();
    }
    /*
       CFPUR-110 Order a copy of Statement Details page
   */
    public void verifyOrderacopyofStatementDetailpage() throws Exception{
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        HomePage home = new HomePage(scriptHelper);
       // statement.selectaccount();
        statement.enterFromDate(deviceType()+ "OCS.fromDate");
        statement.enterTODate(deviceType()+ "OCS.toDate");
        statement.selectaccount();
        statement.statementdetailsvalidation();
        statement.enterStatementNumber();
        statement.clickContinue();
    }
    public void verifypaytocharges() throws Exception{
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.payToCharges();
    }
    public void verifynotification() throws Exception{
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.notificationreq();
    }
    public void verifyPleasenotetext() throws Exception{
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.validatepleasenotecontent();
        //statement.clickContinue();
        statement.clickGoBackbutton();
        statement.clickContinue();
    }
//    public void verifyReviewOCSpage() throws Exception{
//        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
//        statement.verifyreviewpage();
//        statement.clickConfirm();
//    }

    //////// Updated by Maria ///////////////

    public void verifyReviewOCSpage() throws Exception{
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.verifyOCSreviewpage();
        //statement.clickConfirm();
}
    public void verifyConfirmbuttonOCSpage() throws Exception{
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.clickConfirm();
    }


    public void verifyConfirmationPage() throws Exception{
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.verifyConfirmationScreen();
    }

    /*
      CFPUR-6188 Order a copy of Statement Details page
  */
    public void verifyMultipledateOrderacopyofStatementDetailpage() throws Exception{
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        HomePage home = new HomePage(scriptHelper);
        statement.statementdetailsvalidation();
        statement.selectaccount();
        statement.enterStatementNumber();

    }

    public void verifyMultipledateentry() throws Exception{
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        HomePage home = new HomePage(scriptHelper);
        statement.addmoredateverificattion();


    }

    /*
     CFPUR-8786 Order a copy of Statement Details page
 */

    public void verifyChargesPage() throws Exception {
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.payToCharges();
        statement.notificationreq();
    }

    public void verifyerroronCharges() throws Exception {
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.verifyAccountfielderror();
        statement.verifyDatefieldblankerror();
    }

    /*
     Go back navigation functions
 */

    public void verifyGobackbuttonNavigation() throws Exception {
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.clickGoBackbutton();

    }

    public void verifyAAQTile() throws Exception{
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        HomePage home = new HomePage(scriptHelper);
        AAQ.AskaquestionNavigation();
    }

    public void verifyAAQpopupvalidation() throws Exception{
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        HomePage home = new HomePage(scriptHelper);
        home.verifyCheckContentPresent(deviceType()+ "AAQ.textvalidation");
        AAQ.AAQpopupLoststolencard();
        AAQ.AAQpopupFraudsuspiciousactivities();
        AAQ.AAQpopupMakeacomplaint();
        AAQ.clickContinue();
    }
    public void verifyAAQselectAccount() throws Exception{
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        AAQ.selectaccount();
    }
    public void verifyAAQselectSubject() throws Exception{
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        AAQ.selectsubject();
    }
    public void verifyAAQenterText() throws Exception{
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        AAQ.enterTextMessage();
    }
    public void verifytextinfor() throws Exception{
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        AAQ.textInformation();
    }
    public void verifyclickSend() throws Exception{
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        AAQ.clickSend();
    }
    public void verifyAAQconfirmation() throws Exception{
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        AAQ.verifyConfirmationScreen();
    }
    public void verifyAAQerrormessage() throws Exception{
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        AAQ.AskaquestionNavigation();
        AAQ.clickContinue();
        AAQ.clickSend();
        AAQ.verifyErrorMessage();
    }

    public void verifySLvalidation() throws Exception {
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        SL.ServicesLozengesNavigation();
    }

    public void verifyBacktosentitemsbutton() throws Exception{
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        SL.verifyBacktosentitems();
    }

    public void verifyMessageNAvigation() throws Exception {
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        SL.clickMessage();

    }

    public void verifyDefaultSL() throws Exception {
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        SL.verifyDefaultSelection();
    }

    public void verifyemptyinboxmsg() throws Exception {
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        SL.verifyemptyinbox();
    }

    public void verifySentNavigation() throws Exception {
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        SL.clickSent();
    }

    public void verifyshowmoreval() throws Exception {
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        SL.validatesentbox();

        // SL.clickShowMore(); validateInboxShowMore

    }
    public void verifyInboxshowmoreVal() throws Exception {
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
       // SL.validatesentbox();
        SL.validateInboxShowMore();

        // SL.clickShowMore(); validateInboxShowMore

    }

    public void verifyPayToMobileOptionPresent() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.VerifyPayToMobilePresent();
    }

    public void clickPayToMobileOption() throws Exception{
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyClickPayToMobilePayment();
    }

    public void clickSetUpPayToMobile() throws Exception{
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.clickSetUpNowButton();
    }

    public void fetchExistingMobileDigitsProfile() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.fetchExistingMobileDigits();
    }

    public void verifyMobileDigitsMatchingProfile() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyMobileDigitsMatching();
    }

    public void validatePayToMobRegFormPage() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyPayToMobRegFormPage();
    }

    //CFPUR-8480
    public void validatePayToMobRegReviewPage() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyPayToMobRegReview();
    }

    /**
     * CFPUR-7089 : Function to verify the presence of Show More button
     */
    public void verifyShowMoreButton() throws Exception {
        TransactionsPage transaction = new TransactionsPage(scriptHelper);
        transaction.ShowMoreTransactions();
    }
    public void verifyErrorMsgsScheduleFutureDate()throws Exception{
        TransferBetweenAccounts tba = new TransferBetweenAccounts(scriptHelper);
        tba.errorMsgsScheduleFutureDate();
    }

    public void verifySelectAccountPayToMobile() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.selectAccountPayFrom();
    }

    public void verifyPayToMobRegSuccessPage() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyPayToMobileRegSuccessPage();
    }

    //CFPUR-8583
    public void validateUpdateProfileLink() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.clickUpdateProfile();
    }

    public void verifyPayToMobileRegError() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.validateErrorAlreadyRegistered();
    }

    public void verifyPayToMobileSuccessOperations() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyOperationsOnSuccessPage();
    }

    public void verifyProfileRandomMobileNumber() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.updateProfileMobileRandom();
    }

    //CFPUR-106
    public void validateOrderInterestCertFormPage() throws Exception {
        OrderIntCert OrderIntPage = new OrderIntCert(scriptHelper);
        OrderIntPage.verifyOrderInterestCertFormPage();
    }

    public void enterOrderInterestCertFormPage() throws Exception {
        OrderIntCert OrderIntPage = new OrderIntCert(scriptHelper);
        OrderIntPage.OrderInterestCertFormPage_enterDetails();
    }

    /**CFPUR- 6687: New design for Manage payees desktop
     *Function: To verify the details on Manage Payee Page
     * Created by: C103403      Created on: 26/08/2019
     */

    public void validateManagePayeePageNew() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyManagePayeePageNew();
    }

    public void validateOrderIntCertReviewPage() throws Exception {
        OrderIntCert OrderIntPage = new OrderIntCert(scriptHelper);
        OrderIntPage.OrderIntCertsReviewPage();
    }

    public void validateOrderIntCertSuccessPage() throws Exception {
        OrderIntCert OrderIntPage = new OrderIntCert(scriptHelper);
        OrderIntPage.OrderIntCertsSuccessPage();
    }
    public void validateOrderIntCertsentmessagevalidation() throws Exception {
        OrderIntCert OrderIntPage = new OrderIntCert(scriptHelper);
        OrderIntPage.OrderIntCertsSuccessPage();
        OrderIntPage.sentmessagevalidation();
    }

    public void verifyBasicFDPsPageDetails() throws Exception {
        FutureDatedPayments fdps = new FutureDatedPayments(scriptHelper);
        fdps.basicFDPsPageDetails();
    }

    public void verifySelectFDPsPayee() throws Exception {
        FutureDatedPayments fdps = new FutureDatedPayments(scriptHelper);
        fdps.selectFDPsPayee();
    }

//    public void verifyFeeAndChargesDetails() throws Exception {
//        HomePage homePage = new HomePage(scriptHelper);
//        homePage.verifyRegisterNowLink("launch.lnkRegisterNow");
//    }

    public void verifyPTMErrorInfoLink() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyMoreInfoLink();
    }

    public void verifyErrSelectAcc() throws Exception{
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.validateSelectAccError();
    }

    public void verifyDeregisterPTM() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyDeregisterPTMPage();
    }

    public void clickDeregisterBtn() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.clickDeregister();
    }

    public void verifyRegistrationTile() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyNavigationToServices();
    }

    public void checkDeRegistrationOperations() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyDeRegistrationOperations();
    }

    public void verifyDeRegisterPopUpBtnOperation() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.clickDeRegisterPopUpBtn();
    }

    public void verifyCancelPopUpBtnOperation() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.clickCancelPopUpBtn();
    }

    public void verifyDeRegisterPopUpDetails() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyDeletePayeePopUpDetails();
    }

    public void verifyDeRegisterSuccessPage() throws Exception {
        PayToMobilePage PTMReg = new PayToMobilePage(scriptHelper);
        PTMReg.verifyDeRegisterSuccessPagePTM();
    }
    /*Created on    Created by     Reason
    * 12/08/2019    C101979        In-Sprint Automation
    */
    //CFPUR-107 Order Balance Certificate Form Page
    //CFPUR-8908 Order Balance Certificate Review Page
    //CFPUR-8909 Order Balance Certificate Success Page
    //CFPUR-8910 Order Balance Certificate Integation

    public void validateOrderBalanceCertFormPage() throws Exception {
        OrderBalCert OrderBalPage = new OrderBalCert(scriptHelper);
        OrderBalPage.verifyOrderBalanceCertFormPage();
    }
    public void validateOrderBalanceCertYear() throws Exception {
        OrderBalCert OrderBalPage = new OrderBalCert(scriptHelper);
        OrderBalPage.verifyOrderBalCertYear();
    }
    public void enterOrderBalanceCertFormPage() throws Exception {
        OrderBalCert OrderBalPage = new OrderBalCert(scriptHelper);
        OrderBalPage.OrderBalCertFormPage_enterDetails();
    }
    public void validateOrderBalCertReviewPage() throws Exception {
        OrderBalCert OrderBalPage = new OrderBalCert(scriptHelper);
        OrderBalPage.OrderBalCertsReviewPage();
    }

    public void validateOrderBalCertSuccessPage() throws Exception {
        OrderBalCert OrderBalPage = new OrderBalCert(scriptHelper);
        OrderBalPage.OrderBalCertsSuccessPage();
    }

    public void validateOrderBalCertsentmessage() throws Exception {
        OrderBalCert OrderBalPage = new OrderBalCert(scriptHelper);
        OrderBalPage.OrderBalCertsSuccessPage();
        OrderBalPage.sentmessagevalidation();
    }
    /*Created on    Created by     Reason
    * 30/09/2019    C101979        In-Sprint Automation
    */
    //CFPUR-8272 Replace damaged debit card: UI

    public void validateReplaceDDCardFormPage() throws Exception {
        ReplaceDamagedDebitCard ReplaceDamagedDC = new ReplaceDamagedDebitCard(scriptHelper);
        ReplaceDamagedDC.verifyReplaceDDCardFormPage();
    }
    public void enterDDCardDetails() throws Exception {
        ReplaceDamagedDebitCard ReplaceDamagedDC = new ReplaceDamagedDebitCard(scriptHelper);
        ReplaceDamagedDC.ReplaceDamagedDC_enterDetails();
    }
    public void validateReplaceDDCardReviewPage() throws Exception {
        ReplaceDamagedDebitCard ReplaceDamagedDC = new ReplaceDamagedDebitCard(scriptHelper);
        ReplaceDamagedDC.ReplaceDamagedDCReviewPage();
    }
    public void validateReplaceDDCardSuccessPage() throws Exception {
        ReplaceDamagedDebitCard ReplaceDamagedDC = new ReplaceDamagedDebitCard(scriptHelper);
        ReplaceDamagedDC.ReplaceDamagedDCSuccessPage();
    }

    public void validatemessagevalidationReplaceDDCard() throws Exception {
        ReplaceDamagedDebitCard ReplaceDamagedDC = new ReplaceDamagedDebitCard(scriptHelper);
        ReplaceDamagedDC.ReplaceDamagedDCSuccessPage();
        ReplaceDamagedDC.sentmessagevalidation();
    }
    /**CFPUR- 6687: New design for Manage payees desktop
     *Function: To click on the Payee and expand it on Manage Payee Page
     * Created by: C103403      Created on: 26/08/2019
     */

    public void selectPayeeFromNewManagePayeePage() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.selectPayeeFromManagePayee();
    }

    /**CFPUR- 6687: New design for Manage payees desktop
     *Function: To verify the details in the expanded view
     * Created by: C103403      Created on: 26/08/2019
     */

    public void verifyManagePayeesExpandedPayee() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyExpandedPayee();
    }

    public void verifyoperationManagePayee() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.operationManagePayee();
    }

    public void validateUKConfirmPage() throws Exception{
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyUKDomConfirmPage();
    }

    /*
   Function: To use both the function when using the same column in datasheet for data verification for SEPA Review Page
    */
    public void validateSEPAReviewPage() throws Exception{
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifySEPAReviewPage();
    }

    /*
  Function: To use both the function when using the same column in datasheet for data verification for International Review Page
   */
    public void validateInternationalReviewPage() throws Exception{
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyInternationalReviewPage();
    }

    /**CFPUR-8991: No future dated payments message
     * Function: To verify that when there are no future dated payments then i will see a text message on the screen
     * CREATED BY: C103403
     */
    public void validateNoFDPScreen() throws Exception{
        FutureDatedPayments fdps = new FutureDatedPayments(scriptHelper);
        fdps.verifyNoFDPScreen();
    }

    public void verifyProfileAmendEmailAddressE2E() throws Exception {
        ProfileManagment ProfilePage = new ProfileManagment(scriptHelper);
        ProfilePage.updateProfileEmailE2E();
    }

    //CFPUR-8879
    public void verifyAndClickAddMoreDate() throws Exception {
        OrderBalCert OrderBalCertPage = new OrderBalCert(scriptHelper);
        OrderBalCertPage.clickAddMoreDate();
    }

    //CFPUR-8879
    public void verifyLedgerDatesOnReview() throws Exception {
        OrderBalCert OrderBalCertPage = new OrderBalCert(scriptHelper);
        OrderBalCertPage.verifyDatesOnReview();
    }

    //CFPUR-8879
    public void verifyAddAllLedgerDates() throws Exception {
        OrderBalCert OrderBalCertPage = new OrderBalCert(scriptHelper);
        OrderBalCertPage.verifyAllEnterDates();
    }

    //CFPUR-8879
    public void verifyErrDuplicateDateIntervals() throws Exception {
        OrderBalCert OrderBalCertPage = new OrderBalCert(scriptHelper);
        OrderBalCertPage.verifyDuplicateDateIntervals();
    }

    //CFPUR-8879
    public void verifyRemoveAllLedgerDates() throws Exception {
        OrderBalCert OrderBalCertPage = new OrderBalCert(scriptHelper);
        OrderBalCertPage.verifyRemoveAllDateIntervals();
    }

    //CFPUR-8879
    public void verifyBalCertContinue() throws Exception {
        OrderBalCert OrderBalCertPage = new OrderBalCert(scriptHelper);
        OrderBalCertPage.verifyContinueButton();
    }
    //CFPUR-5685
    public void verifyMyOverviewRemoved() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyNoMyOverview();
    }

    public void verifyUnavailInvalidBKKCCAccountOnBlueCard() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.VerifyUnavaiInvalBKKAccountBluecard();
    }

    public void verifyUnavailableBKKAccountStatus() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.VerifyUnavailableBKKAccount();
    }


    /**
     * CFPUR-5451: Cancel transfer between accounts future dated payments (ROI & NI/GB)
     * Function: To test the functionality of Cancel Payment in Future Dated Payment
     * Created by: C103403      Created on: 27/09/2019
     */
    public void validateCancelPaymnent() throws Exception{
        FutureDatedPayments fdps = new FutureDatedPayments(scriptHelper);
        fdps.verifyCancelPaymnent();
    }

    /**
     * CFPUR-5451: Cancel transfer between accounts future dated payments (ROI & NI/GB)
     * Function: To test the details appearing on Cancel Payment popup in Future Dated Payment Page
     * Created by: C103403      Created on: 27/09/2019
     */
    public void validateCancelPaymnetpopupdetails() throws Exception{
        FutureDatedPayments fdps = new FutureDatedPayments(scriptHelper);
        fdps.verifyCancelPaymnetpopupdetails();
    }

    public void clickCancelPayment()throws Exception{
        FutureDatedPayments fdps = new FutureDatedPayments(scriptHelper);
        fdps.cancelPayment();
    }

    /*
   Function: To use both the function when using the same column in datasheet for Transfer Date
    */
    public void verifyPayFromPayToDetails()throws Exception{
        FutureDatedPayments fdps = new FutureDatedPayments(scriptHelper);
        fdps.fdpPayFromPayToDetails();
    }

    public void verifyPushNoAction() throws Exception{
        SCA_DeclinePage noAction=new SCA_DeclinePage(scriptHelper);
        noAction.noActionOnPush();
    }

    public void verifySCADeclinePage() throws Exception {
        SCA_DeclinePage declinePage = new SCA_DeclinePage(scriptHelper);
        declinePage.SCADeclinePage();
    }

    /*
   CFPUR-677 : Verify More Services page
   */
    public void verifyActiveBlockDevices() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.activeDeviceSelection();
        //homePage.blockDeviceSelection();

    }

    public void login_PushNotification() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.enterLoginDetails();
        homePage.enterRequiredPin();
        homePage.selectDeviceListSCA();

    }

    /**
     * Function  to navigate Register Device page
     */
    public void hardToken_RegisterNavigation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.Registerdevicenavigation();
    }

    public void profiledevicevalidation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.hardtoken();
    }

    public void profiledevicenickname() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.editdevicenickname();
    }

    /*
    CFPUR-674 : Verify Editable Devices
     */
    public void verifyEditableDevices () throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.editdevice();
        //homePage.blockDeviceSelection();

    }

    public void verifyDeviceNameBackToOriginal () throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.editDeviceNameBackToOriginal();
    }

    /*
    CFPUR-670 : Verify Nickname of Devices
    */
    public void verifyNickNameDevices
    () throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.validateupdateNickName();
        //homePage.blockDeviceSelection();

    }

    public void profileHardtokenDetailvalidation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        String Hardtokendetail = "xpath~//div[@class='boi-plain-table--responsive boi-plain-table--contact-us boi-plain-table--align-top boi-full-width']";
        String hardtokendeviceobj = "xpath~//div[contains(@class,'boi-tile-standard-rectangle boi-tile-height-67 ')]/descendant::div[@style='text-align: left; ']/div/img[@src='images/BOI/profile/hard-token.svg']";
        //List<WebElement> hardtokenindex = driver.findElements(getObject(hardtokendeviceobj));
        clickJS(getObject(hardtokendeviceobj),"HardToken");
        homePage.verifyElementDetails(Hardtokendetail);
    }


    public void profiledevicetimeout() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.verifyDeviceTimeout();

    }

    public void devicetimeoutverify() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.TimeoutDeviceverify();
    }

    // Function to change profile hardtoken device status

    public void hardtokenDevicestatuschange() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.securitydeviceAction();
    }

    /*
    CFPUR-2401 : My Security Devices - Delete a provisioned device - SCA
    */
    public void verifyRemoveDevices() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.validateDeleteDevice();
        //homePage.enterRequiredPin();
        //homePage.validateDeleteDeviceStatus();

    }

    /*
    CFPUR-3238 : My Security Devices - Popup before every action ( Block, Unblock ,Delete)- UI
    */
    public void verifyPopupDevices
    () throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.validatePopUpAction();
        //homePage.blockDeviceSelection();

    }

    // Function to navigate and verify block device
    public void hardtokenDeviceUserBlock() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateActiveBlockDevices();
        homePage.securitydeviceBlockAction();
    }

    /**
     * Function to Login digital application
     */
    public void lockuserlogin() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.enterLoginDetails();
        //homePage.verifyPINFields();
        // homePage.verifyDeviceListSCA();
        homePage.enterinvalidPin();
        Thread.sleep(4000);

        homePage.enterinvalidPin();
        Thread.sleep(4000);

        homePage.enterinvalidPin();
        Thread.sleep(4000);

        homePage.enterinvalidPin();
        Thread.sleep(4000);

        homePage.enterinvalidPin();

        if (isElementDisplayed(getObject(deviceType() + "blockmsg"), 40)) {
            report.updateTestLog("blocksuccessmessage", "user block success message displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("blocksuccessmessage", "user block success message NOT displayed", Status_CRAFT.FAIL);
        }
    }

    /*
       CFPUR-2347 Enter PIN for transfer between account
        */
    public void verifyincorrectPIN_TBA() throws Exception {
        //TransferBetweenAccounts transBetAccnt = new TransferBetweenAccounts(scriptHelper);
        //transBetAccnt.PIN_TBA();
        HomePage homePage = new HomePage(scriptHelper);
        homePage.enterinvalidPin();
        Thread.sleep(3000);
        clickJS(getObject(deviceType() + "tryagain"), "tryagain");
        homePage.enterinvalidPin();
        Thread.sleep(3000);
        clickJS(getObject(deviceType() + "tryagain"), "tryagain");
        homePage.enterinvalidPin();
        Thread.sleep(3000);
        clickJS(getObject(deviceType() + "tryagain"), "tryagain");
        homePage.enterinvalidPin();
        Thread.sleep(3000);
        clickJS(getObject(deviceType() + "tryagain"), "tryagain");
        homePage.enterinvalidPin();

        if (isElementDisplayed(getObject(deviceType() + "blockmsg"), 30)) {
            report.updateTestLog("blocksuccessmessage", "user block success message displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("blocksuccessmessage", "user block success message NOT displayed", Status_CRAFT.FAIL);
        }

    }

    public void loginincorrectdetail() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.enterLoginDetails();

        homePage.enterRequiredPin();

        if (isElementDisplayed(getObject(deviceType() + "incorrectloginmsg"), 130)) {
            report.updateTestLog("Verifyloginerror", "Incorrect login detail msg displayed successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("VerifyLoginerror", "Incorrect login detail msg Not successfull, Exiting Test", Status_CRAFT.FAIL);
            driver.quit();
        }

    }
    //squad5sprint23
    //verify to check whether the device details are available on device choose page
    public void verifydevicedisplaystatus() throws Exception
    {
        Squad5Sprint23 squad5Sprint23 = new Squad5Sprint23(scriptHelper);
        HomePage Hm = new HomePage(scriptHelper);
        Hm.validateActiveBlockDevices();
        squad5Sprint23.Devicedisplaystatuschange();
        Hm.validateActiveBlockDevices();
        squad5Sprint23.Devicedisplaystatus();
    }




   //Squad5

    /*
CFPUR-3597 : My Security Devices - Popup before every action ( Block, Unblock ,Delete)- UI
*/
    public void verifyPSKLink
    () throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.selectDeviceListHTokenSCA();
        homePage.validateHardTokenLinkPopup();
    }


    /*
    CFPUR-3597 : My Security Devices - Popup before every action ( Block, Unblock ,Delete)- UI
    */
    public void verifyHardTokenLink
    () throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        // homePage.selectDeviceListHTokenSCA();
        homePage.validatePSKLink();
    }
    // Function to change profile hardtoken device status

    public void devicestatuschange() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        Squad5Sprint23 profile = new Squad5Sprint23(scriptHelper);
        homePage.validateActiveBlockDevices();
        profile.securitydeviceAction();
    }
    public void pskdevicelogin() throws Exception {

        HomePage homePage = new HomePage(scriptHelper);
        SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
        if (!isMobile) {
            homePage.enterLoginDetails();
        }

        homePage.enterRequiredPin();

    }
    public void pskDeviceActivation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        Squad5Sprint23 profile = new Squad5Sprint23(scriptHelper);
        profile.activationpageerrmsgvalidation();
        //profile.securitydeviceAction();
    }

    public void pskActivationpending() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        Squad5Sprint23 profile = new Squad5Sprint23(scriptHelper);
        profile.activationpendingvalidation();
        //profile.securitydeviceAction();
    }
    public void pskvalidation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        Squad5Sprint23 profile = new Squad5Sprint23(scriptHelper);
        profile.validatePSKDetail();
        //profile.securitydeviceAction();
    }
    //CS-5176
    public void verifySecurityConcernsPage() throws  Exception{
        SecurityConcerns sec=new SecurityConcerns(scriptHelper);
        sec.securityConcernLink();
        sec.lostAndStolenContent();
        sec.moreInfoContent();

    }

    //cs-4635
    public void verifyForgetUserID() throws Exception{
        HomePage home=new HomePage(scriptHelper);
        home.verifyForgetUserIDContent();
    }


    public void verifyContentDisplayed() throws Exception{
        StandingOrder so = new StandingOrder(scriptHelper);
        so.contentDisplayed();
    }


    public void validateRequestPhysicalDevice() throws Exception{
        HomePage homePage= new HomePage(scriptHelper);
        homePage.validateRequestPhysical();
        homePage.verifyRequestPhysicalSecurityKey();
    }

    public void activeDeviceBlockOnApp() throws Exception {
        SCAValidations sca = new SCAValidations(scriptHelper);
        sca.activeDeviceBlock();
    }

    public void SendMoney_ConfirmationPage() throws Exception {
        PaymentsManagePayees sendMoney = new PaymentsManagePayees(scriptHelper);
        sendMoney.SendMoney_Confirmation();
    }



    /**
     * CFPUR-815: Private Banking Payment Landing Page - Link to GWS
     * Function: To validate the Private Banking Payment Page
     */
    public void validatePrivateBankingPaymentPage() throws Exception{
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.verifyPrivateBankingPaymentPage();
    }

    /**
     * CFPUR-4229
     * Function: To Launch UXP  with invalid URL
     */
    public void validateInvalidURLErrorPage() throws Exception{
        HomePage home=new HomePage(scriptHelper);
        home.validateErrorOnInvalidURL();
    }
    public void verifyPayeeExistAndDelete() throws Exception{
        PaymentsManagePayees pmp = new PaymentsManagePayees(scriptHelper);
        pmp.payeeExistAndDelete();

    }
    public void verifyLOSTPSKPage() throws  Exception{
        SecurityConcerns sec=new SecurityConcerns(scriptHelper);
        sec.securityConcernLink();
        sec.LostPSKContent();
    }



    public void verifyOwnAccountTransferInvalidAccount() throws Exception{
        PaymentsManagePayees paymentsManagePayees = new PaymentsManagePayees(scriptHelper);
        paymentsManagePayees.ownAccountTransferInvalidAccount();

    }

    /**
      * Function : To select accounts having inactive SO
     */
    public void selectStandingOrderAccountInactiveSO() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.inactiveSO_StandingOrder();
    }
    /**
      * Function : To view inactive SO for weekly frequencies
     */

    public void selectByWeeklyFrequency() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.viewInactiveSOAlertWeekly();
    }

    /**
      * Function : To view inactive SO for Monthly frequencies
     */

    public void selectByMonthlyFrequency() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.viewInactiveSOAlertMonthly();
    }

    /**
      * Function : To view inactive SO for Fortnightly frequencies
     */
    public void selectByFortnightlyFrequency() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.viewInactiveSOAlertFortnightly();
    }
    /**
      * Function : To view inactive SO for Yearly frequencies
     */
    public void selectByYearlyFrequency() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.viewInactiveSOAlertYearly();
    }
    /**
      * Function : To view inactive POSTAL but inactive standing order
     */
    public void selectByPostMethod() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.viewInactiveSOAlertPost();
    }
    /**
      * Function : To view and validate inactive SO alert message
     */
    public void viewInactiveSOAlert() throws Exception {
        StandingOrder standingOrderPage = new StandingOrder(scriptHelper);
        standingOrderPage.viewInactiveSOAlertText();
    }

    public void selectPaymentsOptions_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.selectPaymentOption_Light();
    }
    public void verifyPayFrom_SendMoney_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.payFrom_SendMoney_Light();
    }
    public void verifyPayTo_SendMoney_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.payTo_SendMoney_Light();
    }
    public void verifyAmount_SendMoney_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.amount_Sendmoney_Light();
    }
    public void verifySelectOptProcessTransfer_light() throws Exception{
        TransferBetweenAccounts tbd = new TransferBetweenAccounts(scriptHelper);
        tbd.selectOptProcessTransfer();
    }
    public void verifyMessageToPayee_SendMoney_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.verifyMessageToPayee_Light();
    }
    public void verifyMessageLine123ToPayee_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.messageLine123ToPayee_Light();
    }
    public void verifyContinue_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.continue_SendMoney_Light();
    }
    public void verifyReviewPage_sendMoney_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.reviewPage_SendMoney_Light();
    }
    public void verifyEnterRequiredPin_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.enterRequiredPin_Light();
    }
    public void verifySuccess_sendMoney_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.success_SendMoney_Light();
    }

    public void verifySelectAccountPayFrom_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.selectAccountPayFrom_Light();
    }
    public void verifySelectAccountPayTo_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.selectAccountPayTo_Light();
    }
    public void verifyEnterAmount_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.enterAmount_Light();
    }
    public void verifySuccess_TBA_light() throws Exception{
        PaymentsCreationLightVersion paymentCreat = new PaymentsCreationLightVersion(scriptHelper);
        paymentCreat.success_TBA_Light();
    }
     /*Created on    Created by     Reason
       20/11/2019    C101979        In-Sprint Automation
       CFPUR-11716   Navigation from Services page to Manage Devices
    */
    //all ok
    public void verifyNavToServices() throws Exception{
        HomePage homePage = new HomePage(scriptHelper);
        homePage.validateServicesNavigation();
    }
    public void verifyGoBackNavigation() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.gobacknav();
    }

    public void verifyDailyLimitsForAddPayeeReviewPage() throws Exception{
        PaymentsManagePayees addPayee = new PaymentsManagePayees(scriptHelper);
        addPayee.dailyLimitsForAddPayeeReviewPage();
    }
    public void verifyPayFrom_AddPayee() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.payFrom_AddPayee();
    }

    /**
     * CFPUR-56
     * Function: To Verify Life Online SSO
     */
    public void verifyLifeOnlineSSO() throws Exception{
        HomePage home=new HomePage(scriptHelper);
        home.validateLifeOnlineSSO();
    }

    /**
     * Function to invoke digital application
     */
    public void keepAcceptingPushNotifications() throws Exception {
        waitForJQueryLoad();
        report.updateTestLog("keepAcceptingPushNotifications", "::::Launching App::::", Status_CRAFT.DONE);
        SCA_MobileAPP scaMobileAPP = new SCA_MobileAPP(scriptHelper);
        scriptHelper.commonData.exitMobileAppFlag = false ;
        scaMobileAPP.setRelevantWebViewTab();
        scaMobileAPP.AllPushNotification_Acccept();
    }

    /**
     * Function to closeAppNotifications
     */
    public void closeAppNotifications() throws Exception {
        waitForJQueryLoad();
        report.updateTestLog("closeAppNotifications", "::::Close App::::", Status_CRAFT.DONE);
        scriptHelper.commonData.exitMobileAppFlag = true ;
        SCA_MobileAPP scaMobileAPP = new SCA_MobileAPP(scriptHelper);
        //scaMobileAPP.setRelevantWebViewTab();
        scaMobileAPP.AllPushNotification_Acccept();
    }

    public void verifySelectCountryAddPayee_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.selectCountryAddPayee_Light();
    }
    public void verifyAddPayeeDetails_International_light() throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.addPayeeDetails_International_Light();
    }
    public void verifyBackButton() throws Exception{
        HomePage homePage = new HomePage(scriptHelper);
        homePage.backButtonValidation();
    }

    public void verifyReasonForPayment_light() throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.reasonForPayment_Light();
    }

    public void verifyClickBackToHomeButton_light() throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.clickBackToHomeButton_Light();
    }
    public void verifyClickBackToPaymentsButton_light() throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.clickBackToPaymentsButton_Light();
    }
    public void verifySelectAccountFromHomepage_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.selectAccountFromHomepage_Light();
    }
    public void verifyInProgressTransaction_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.inProgressTransaction_Light();
    }
    public void verifySelectSetUpNewSO_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.selectSetUpNewSO_Light();
    }
    public void verifySelectPayFromSO_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.selectPayFromSO_Light();
    }
    public void verifyEnterPayeeDetailsOfSO_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.enterPayeeDetailsOfSO_Light();
    }
    public void verifyEnterAmountOfSO_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.enterAmountOfSO_Light();
    }
    public void verifyReviewPage_SO_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.reviewPage_SO_Light();
    }
    public void verifyRegulatoryInfo()throws Exception{
        MoreOptionsPage Regulatoryinfo = new MoreOptionsPage(scriptHelper);
        Regulatoryinfo.verifyRegulatoryOption();
    }

    public void verifySecurityConcernLink(){
        SecurityConcerns securityConcerns = new SecurityConcerns(scriptHelper);
        try {
            securityConcerns.securityConcernLink();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void verifyLaunchAppNotification(){
        SecurityConcerns securityConcerns = new SecurityConcerns(scriptHelper);
        securityConcerns.launchAppNotification();
    }
    public void verifyAppNotificationPageContents(){
        SecurityConcerns securityConcerns = new SecurityConcerns(scriptHelper);
        securityConcerns.appNotificationPageContents();
    }
    public void verifyGoBackAppNotification(){
        SecurityConcerns securityConcerns = new SecurityConcerns(scriptHelper);
        securityConcerns.goBack();
    }
    public void verifyEnterLoginDetails(){
        HomePage homePage = new HomePage(scriptHelper);
        try {
            homePage.enterLoginDetails();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void verifyClickGoBackButton(){
        StandingOrder standingOrder = new StandingOrder(scriptHelper);
        standingOrder.clickGoBackButton();
    }
    public void verifyAppLaunch() throws Exception {
        try {
            if(!isMobile){
                invokeApplication();
            }else{
                waitForJQueryLoad();
                report.updateTestLog("invokeApp", "::::Launching App::::", Status_CRAFT.DONE);
                SCA_MobileAPP scaMobileAPP = new SCA_MobileAPP(scriptHelper);
                scaMobileAPP.setRelevantWebViewTab();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void verifySelectAccount_SO_light(){
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.selectAccount_SO_Light();
    }
    public void verifySelectRequiredSO_light(){
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.selectRequiredSO_Light();
    }
    public void verifyManageSO_light(){
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.manageSO_Light();
    }
    public void verifyChangeAmount_SO_light(){
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.changeAmount_SO_Light();
    }
    public void verifyRemoveHold_SO_light(){
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.removeHold_SO_Light();
    }
    public void verifyCancelThis_SO_light(){
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.cancelThis_SO_Light();
    }
    public void verifyPutOnHold_SO_light(){
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.putOnHold_SO_Light();
    }
    public void verifySelectFDPsPayee_light(){
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.selectFDPsPayee_Light();
    }

    /**
     * CFPUR-11005: Available funds & arranged overdraft balance on transactions page (NI/GB/Cross only)
     * Function: To verify the vailable funds & arranged overdraft balance on transactions page
     * Created on     Created by     Reason
     * 11/12/2019    C103403        New Story
     */
    public void validateAvailable_OverdraftFunds() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        transactionPage.verifyAvailable_OverdraftFunds();
    }

    public void verifyPTMRegisterOrDeRegister() throws Exception {
        PayToMobilePage payToMobile = new PayToMobilePage(scriptHelper);
        payToMobile.checkPTMRegistrationOrDeReg();
    }

    public void verifySelectProvider_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.selectProvider_Light();
    }

    public void verifySelectAmount_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.selectAmount_Light();
    }

    public void verifyEnterPhoneNumber_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.enterPhoneNumber_Light();
    }

    public void verifyEnterConfirmNumber_light()throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.enterConfirmNumber_Light();
    }

    public void verifyMobileTopUpSuccessScreen_light() throws Exception {
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.mobileTopUpSuccessScreen_Light();
    }

    public void verifyEnterPTMDetails_light() throws Exception {
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.enterPTMDetails_Light();
    }

    public void verifyPTMSuccessPage_light() throws Exception {
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.validatePTMSuccessPage_Light();
    }

    public void verifyPTMReviewPage_light() throws Exception {
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.validatePTMReviewPage_Light();
    }
    public void verifySuccess_SO_light() throws Exception{
        PaymentsCreationLightVersion paymentLight = new PaymentsCreationLightVersion(scriptHelper);
        paymentLight.success_SO_Light();
    }




     /*
//    CFPUR-7249: To Check absence of Manage Alert Link on account page
//    */
//
//    public void verifyManageAlertaccountpage() throws Exception {
//
//        HomePage homePage = new HomePage(scriptHelper);
//        homePage.verifyaccountsManageAlert();
//        // homePage.verifyManageAccountLink();
//    }

    /*
CFPUR-6613 : Apply Online Navigation to BOI Forms Domain
*/
    public void validateApplyOnlinePage() throws Exception {
        ApplyPage apply = new ApplyPage(scriptHelper);
        apply.launchApply();
        apply.verifyApplypagesections();
        apply.verifyApplyoptionsLinks();
        apply.selectApplyoptions();
        apply.verifyTimeoutPopup();

    }

    /*
    CFPUR-4046 : Verify execption is not displayed at logout
    */
    public void validateExceptionOnLogout() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifylogoutexception();
    }

    /*
    CFPUR-946 : Verify More Services page
    */
    public void verifyMoreServicesPage() throws Exception {
        ContactUsPage contactus = new ContactUsPage(scriptHelper);
        contactus.launchMoreServices();
        //contactus.clickoption();
        contactus.verifyMoreServices();
        contactus.selectoptions();
        contactus.verifyTimeoutPopup();
        contactus.selectPaymentTab();
    }

  /*
CFPUR-6570 : Verify Withdraw Funds page
*/

    public void verifyWithdrawFundsChanges() throws Exception {
        WithdrawFunds withdrawFunds = new WithdrawFunds(scriptHelper);
        HomePage homePage = new HomePage(scriptHelper);
        //withdrawFunds.launchWithdrawFunds();
        withdrawFunds.clicklink();
        withdrawFunds.verifyWFinputpage();
        //withdrawFunds.captureToolTip();
        withdrawFunds.enterAmount();
        withdrawFunds.enterEmail();
        withdrawFunds.enterMobile();
        withdrawFunds.clickContinue();
        withdrawFunds.withdrawFundsReview();
        withdrawFunds.clickContinue();
        withdrawFunds.enterRequiredPin();
        withdrawFunds.clickConfirm();
        withdrawFunds.verifyImptextSuccessPage();
    }

    //CFPUR-7987
    public void verifyLoginfromLogout() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyloginredirect();
    }

  /*
 CFPUR-7517 Advert and Deep Links
 */

    public void verifyAdverts() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyAdvertisingBox();
    }

    public void verifyDeeplinks() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.selectDeepLink();
        homePage.handlingDeepLinkbackNavigation();
    }

   /*
CFPUR-5135 365 Monthly Saver: Back button
 */

    public void verifyBackNavigationonApply() throws Exception {
        ApplyPage apply = new ApplyPage(scriptHelper);
        apply.launchApply();
        apply.selectUXPlinks();
        apply.verifyGoBack1();
    }

  /*
CFPUR-7629 Change Address:country name change
 */

    public void verifyCountrychanges() throws Exception {
        ChangeAddress changeAddress = new ChangeAddress(scriptHelper);
        changeAddress.launchChangeAddress();
        changeAddress.modifyCheckboxes();
        changeAddress.verifyCountry();

    }

    public void verifyDeletepayee() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.clickDeletebutton();
    }

    public void verifyMessagevaliadtionOCS() throws Exception {
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.verifyConfirmationScreen();
        statement.sentmessagevalidation();
    }

    public void verifyDatefieldinvaliderror() throws Exception {
        OrderaCopyofStatement statement = new OrderaCopyofStatement(scriptHelper);
        statement.verifyDatefieldinvaliderrors();
    }

    /*
     Footer template changes
 */

    public void verifyfootertemplate() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyfooterlinknaviagtion();
        homePage.childWindowhandler();

    }

    public void verifyAAQmailverfication() throws Exception{
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        AAQ.verifymessageAAQ();
    }

   /* public void verifymessagevalidity() throws Exception {
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        SL.verify12monthmessage();


    }*/

    public void verifySentlayout() throws Exception {
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        SL.verifymessagenameSent();
        SL.verifymessagedateSent();
        SL.verify12monthmessagesent();


    }

    public void verifyInboxlayout() throws Exception {
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        if (isElementDisplayed(getObject(deviceType() + "SL.inboxemptymsg"), 10)) {
            report.updateTestLog("verifymessageboxlayout", "Inbox empty message displayed: ", Status_CRAFT.PASS);
        } else {
            SL.verifymessagenameInbox();
            SL.verifymessagedateInbox();
        }
        SL.verify12monthmessageinbox();
    }
    public void verifyunreadmessageindicatorbubble() throws Exception{
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        SL.verifymessageindicator();
    }

    public void verifyservicepage() throws Exception{
        ServicesManageStatement SM = new ServicesManageStatement(scriptHelper);
        SM.verifyServicePagelayout();
    }

    public void verifyContactusContentprelogin() throws Exception{
        HomePage home = new HomePage(scriptHelper);
        ContactUsPage CP = new ContactUsPage(scriptHelper);
       CP.verifyfooterlinks();
       CP.verifytab();
    }
    public void verifycontentpostlogin() throws Exception{
        HomePage home = new HomePage(scriptHelper);
        ContactUsPage CP = new ContactUsPage(scriptHelper);
        CP.launchContactUs();
        CP.verifytitle();
        home.verifyCheckContentPresent(deviceType() + "contactus.contentupdate");
//        CP.verifyfooterlinks();
//        CP.verifytab();
    }
    public void verifyPreLoginMoreContactus() throws Exception{
        ContactUsPage CP = new ContactUsPage(scriptHelper);
        CP.clickmore();
    }
    public void verifyPostLoginMoreContactus() throws Exception{
        ContactUsPage CP = new ContactUsPage(scriptHelper);
        HomePage home = new HomePage(scriptHelper);
        CP.clickmorePost();
        CP.launchContactUs();
        CP.verifytitle();
        home.verifyCheckContentPresent(deviceType() + "contactus.contentupdate");
    }

    public void verifysentmaildetails() throws Exception{
        Services_Lozenges SL = new Services_Lozenges(scriptHelper);
        SL.verifysentmaildetails();
        //SL.withdrawFundsmaildetails();
        //SL.verifyBacktosentitems();
    }
    public void verifyregulatory() throws Exception{
        HomePage home = new HomePage(scriptHelper);
        home.verifyregulatoryText();
    }

    //cfpur -341
    public void verifyNoNewMessagePresent01() throws Exception
    {
        HomePage unreadMsgChk = new HomePage(scriptHelper);
        unreadMsgChk.verifyNoElementOnHomepage();

    }
    //cfpur -341
    public void verifyIndicatorOnHomepage02() throws Exception{
        HomePage MsgChk = new HomePage(scriptHelper);
            MsgChk.VerifyMessageOnAwayToHomeScreen();
    }
    //cfpur -341
    public void verifyUnreadMsgChk05() throws Exception
    {
        HomePage MsgCountChk =new HomePage(scriptHelper);
        MsgCountChk.ReadNewUnreadMessage();


    }
    //cfpur - 341
    public void verifyCountdecreaseOfUnreadMessage() throws Exception
    {
        HomePage countdec = new HomePage(scriptHelper);
        countdec.VerifyTextOnHomePage();

    }

    public void verifyregulatorytextmobile() throws Exception
    {
        HomePage home = new HomePage(scriptHelper);
        home.clickmore();
        home.verifyCheckContentPresent(deviceType() + "home.regulatorytext");
    }
    //cfpur - 5490
    public void verifyInboxmessageServiceTab() throws Exception{
        Services_Lozenges SL1 = new Services_Lozenges(scriptHelper);
        SL1.validateUnreadmessage();

    }
    //cfpur-10524
    public void verifyInvalidMessageError() throws Exception{
        Askaquestion varAsk = new Askaquestion(scriptHelper);
        varAsk.AskaquestionNavigation();
        varAsk.clickContinue();
        varAsk.VerifyInvalidMessageSendButton();


    }

    public void navigateToAnotherAccount() throws Exception {
        TransactionsPage tp = new TransactionsPage(scriptHelper);
        tp.navigateToAnotherAccount();

    }
    public void servicevalidation() throws Exception{
        ServicesManageStatement SM = new ServicesManageStatement(scriptHelper);
        SM.servicepageverification();
        SM.VerifyBBKnone();
    }
    //cfpur-10534
    public void navigateToOrderStatement() throws Exception{
        OrderaCopyofStatement ocs = new OrderaCopyofStatement(scriptHelper);
        ocs.NavigationToOrderStatement();

    }
    public void verifyOrderUpToDateStatementsPage() throws Exception{
            ServicesManageStatement sms = new ServicesManageStatement(scriptHelper);
            OrderaCopyofStatement ocs = new OrderaCopyofStatement(scriptHelper);
            Askaquestion aaq = new Askaquestion(scriptHelper);
            HomePage hp = new HomePage(scriptHelper);
            sms.verifyOrderUpToDateStatements();
            hp.verifyElementDetails(deviceType()+"reviewpageOrderuptodate");
            ocs.clickConfirm();
        aaq.ConfirmationPageValidation(deviceType()+"ManageStatements.pgConfirmation");
            //sms.verifyElementDetailsConfim(deviceType()+"ManageStatements.pgConfirmation");
           aaq.messagesentPageValidation();
           aaq.sentMsgDetailValidation(deviceType()+"sentmsgpageDetailsChk");


            //aaq.verifyAAQmailverfication();
        //sms.verifyGeneralReviewPage();
    }


    public void verifyUKsavingsAndFSCSbannerOnHomePage() throws Exception{
        HomePage hp = new HomePage(scriptHelper);
        hp.verifyUKsavingsAndFSCSbanner();
    }
    public void verifyUKsavingsForNOBKK() throws Exception{
        HomePage hp = new HomePage(scriptHelper);
        hp.verifyUKsavingsForNOBKK();
    }
    public void verifyFSCSNeg() throws Exception{
        HomePage hp = new HomePage(scriptHelper);
        hp.verifyFSCSNeg();
    }

    public void verifyClickPreLgnMore() throws Exception {
        HomePage home = new HomePage(scriptHelper);
        home.clickmore();
    }

    /**
     * Function: To verify the details of High Cost of Credit in Transactions tab
     * Created by   Created On
     * C103403      05/02/2020
     */
    public void verifyTransactionsHCCDetails() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        transactionPage.verifyAvailableFunds();
        transactionPage.verifyArrangedOverdraftRemaining();
    }

    public void verifySelectPayfromAccount() throws Exception {
        PaymentsManagePayees payments = new PaymentsManagePayees(scriptHelper);
        payments.selectPayfromSendMoney();
    }

    public void validatePayFromHCCdetails() throws Exception {
        TransactionsPage transactionPage = new TransactionsPage(scriptHelper);
        transactionPage.verifyPayFromHCCDetails();
    }

    public void loginForUnrecoverableError() throws Exception {

        HomePage homePage = new HomePage(scriptHelper);
        SCA_MobileAPP sca = new SCA_MobileAPP(scriptHelper);
        if (!isMobile) {
            homePage.enterLoginDetails();
        }
        homePage.enterRequiredPin();
        if (!isMobile) {
            homePage.verifySelectRegisterDevice();
            //sca.PushNotification_Acccept(Mobiledriver);
        }
        validateUnrecoverableMainFrameError_Patter1();
    }

    public void selectPayeeFromManagePayeeList_light() throws Exception{
        PaymentsCreationLightVersion paymentsLightVersion = new PaymentsCreationLightVersion(scriptHelper);
        paymentsLightVersion.selectPayeeFromManagePayeeList_Light();
    }
    public void addPayee_FillDetails_light() throws Exception{
        PaymentsCreationLightVersion paymentsLightVersion = new PaymentsCreationLightVersion(scriptHelper);
        paymentsLightVersion.addPayee_FillDetails_Light();
    }
    public void success_light() throws Exception{
        PaymentsCreationLightVersion paymentsLightVersion = new PaymentsCreationLightVersion(scriptHelper);
        paymentsLightVersion.success_Light();
    }
    public void clickBackToHome_Success_light() throws Exception{
        PaymentsCreationLightVersion paymentsLightVersion = new PaymentsCreationLightVersion(scriptHelper);
        paymentsLightVersion.clickBackToHome_Success_Light();
    }
    public void clickSendMoney_Success_light() throws Exception{
        PaymentsCreationLightVersion paymentsLightVersion = new PaymentsCreationLightVersion(scriptHelper);
        paymentsLightVersion.clickSendMoney_Success_Light();

    }

	/* CAF-219 -addition of Back to home button on success screen */

    public void verifyMobileTopUpSuccessScreenBackToHome() throws Exception {
        MobileTopUp addTopUp = new MobileTopUp(scriptHelper);
        addTopUp.MobileTopUpSuccessScreenBackToHome();
    }

   //////////////////// CAF 2.0 Update///////////////////////

    /**
     * Function to invoke digital application and validate all DP screens
     */
    public void invokeApplication_validate() throws Exception {
        if (!isMobile) {
            HomePage homePage = new HomePage(scriptHelper);
            SCA_MobileAPP sca=new SCA_MobileAPP(scriptHelper);
            homePage.launchApplication();
            homePage.verifyappLaunch();
        } else {
            waitForJQueryLoad();
            report.updateTestLog("invokeApp", "::::Launching App::::", Status_CRAFT.SCREENSHOT);
            SCA_MobileAPP scaMobileAPP = new SCA_MobileAPP(scriptHelper);
            scaMobileAPP.setRelevantWebViewTab();
            scaMobileAPP.functionToHandleLogin_validate();
        }
    }

    public void removeApplication() throws Exception{
        driver.closeApp();
        report.updateTestLog("RemoveApplication","App closed",Status_CRAFT.SCREENSHOT);
        driver.removeApp("com.bankofireland.tcmb");
        report.updateTestLog("validateAppVersion","App removed",Status_CRAFT.SCREENSHOT);
    }
	
	public void verifyLaunchForgotPIN(){
        SecurityConcerns securityConcerns = new SecurityConcerns(scriptHelper);
        securityConcerns.launchForgotPIN();
    }
    public void verifyForgotPINPageContent() throws Exception {
        SecurityConcerns securityConcerns = new SecurityConcerns(scriptHelper);
        securityConcerns.forgotPINPageContent();
    }
    public void verifyForgotPINGoBack(){
        SecurityConcerns securityConcerns = new SecurityConcerns(scriptHelper);
        securityConcerns.goBack();
    }
	
	public void verifyLaunchFraudConcerns(){
        SecurityConcerns securityConcerns = new SecurityConcerns(scriptHelper);
        securityConcerns.launchFraudConcerns();
    }
    public void verifyFraudConcernsPageContent() throws Exception {
        SecurityConcerns securityConcerns = new SecurityConcerns(scriptHelper);
        securityConcerns.fraudConcernsPageContent();
    }
    public void verifyFraudConcernsGoBack(){
        SecurityConcerns securityConcerns = new SecurityConcerns(scriptHelper);
        securityConcerns.goBack();
    }
}

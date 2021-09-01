package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.*;


/**
 * Created by C104521 on 26/03/2019.
 */
public class SCAValidations extends WebDriverHelper {
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public SCAValidations(ScriptHelper scriptHelper) {
        super(scriptHelper);

    }
    //Activate your device Page

    public void enterHardTokenCode() throws InterruptedException {

        String[] arrCode = dataTable.getData("General_Data", "TokenPIN").split("");

        List<WebElement> lstCodeEnterFieldGrp = findElements(getObject("w.sca.EnterHardTokenEdit"));

        //Entering values for only enabled fields
        for (int i = 0; i < lstCodeEnterFieldGrp.size(); i++) {
            if (lstCodeEnterFieldGrp.get(i).isEnabled()) {
                lstCodeEnterFieldGrp.get(i).sendKeys(arrCode[i]);
            }
        }
        report.updateTestLog("EnterHardCode", "Entered Hard Code digits", Status_CRAFT.DONE);
        if (isElementEnabled(getObject("w.sca.ActivateNowButton"))) {
            report.updateTestLog("VerifyConfirm Button", "Confirm Button is Enable after enter all Digits in boxes", Status_CRAFT.PASS);
            clickJS(getObject("w.sca.ActivateNowButton"), "Confirm");
        } else {
            report.updateTestLog("VerifyConfirm Button", "Confirm Button is Disable after enter all Digits in boxes", Status_CRAFT.FAIL);
        }
    }

    //DeviceActivated Page

    //DeviceActivated  Screen vaidation page
    public void sca_DeviceActivated() throws InterruptedException {
        PaymentsManagePayees Scrl = new PaymentsManagePayees(scriptHelper);
        if (isElementDisplayed(getObject("w.DeviceActivatedmessage"), 2)) {
            Scrl.ScrollToVisibleJS("w.DeviceActivatedmessage");
            report.updateTestLog("DeviceActivated", "DeviceActivated page Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("DeviceActivated", "DeviceActivated Loginsecurely not displayed successfully", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("w.DeviceActivatedLoginsecurely"), 2)) {
            report.updateTestLog("DeviceActivatedLoginsecurely", "DeviceActivatedLoginsecurely Displayed Successfully", Status_CRAFT.PASS);
            clickJS(getObject("w.DeviceActivatedLoginsecurely"), "DeviceActivatedLoginsecurely");
        } else {
            report.updateTestLog("DeviceActivatedLoginsecurely", "DeviceActivatedLoginsecurely not displayed successfully", Status_CRAFT.FAIL);
        }
    }

    //Amend 365 Digital page

    //Amend365Digitalpage navigation
    public void sca_Amend365Digitalpage() throws InterruptedException {
        if (isElementDisplayed(getObject("w.Amend365Digitalpage"), 2)) {
            report.updateTestLog("Amend365Digita", "Amend365Digital page Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Amend365Digita", "DeviceActivated Loginsecurely not displayed successfully", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("w.Amend365DigitalContinue"), 2)) {
            report.updateTestLog("Amend365DigitalContinue", "Amend365DigitalContinue Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Amend365DigitalContinue", "Amend365DigitalContinue not displayed successfully", Status_CRAFT.FAIL);
        }
    }


    //HardToken Activation pending Page
    public void sca_HardTokenActivationpending() throws InterruptedException {
        String strValCaptured = "";
        if (isElementDisplayed(getObject("launch.checkhardtokenactivation"), 2)) {
            strValCaptured = getText(getObject("launch.checkhardtokenactivation"), 100);
            report.updateTestLog("HardToken activation", "Page " + strValCaptured + " Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("HardToken Activation", "Hardtoken Activation page not displayed successfully", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("Activationpending"), 2)) {
            report.updateTestLog("HardToken activation", "Button Activation pending Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("HardToken Activation", "Hardtoken Activation pending not displayed successfully", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("Activationhelp"), 2)) {
            report.updateTestLog("HardToken activation", "Button Activation pending with help link Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("HardToken Activation", "Hardtoken Activation pending with help link not displayed successfully", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("smartphoneheading"), 2)) {
            report.updateTestLog("smartphoneheading", "Smart phone heading Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("smartphoneheading", "Smart phone heading not displayed successfully", Status_CRAFT.FAIL);
        }
    }
//HardToken Activation Suspended Page vaidation

    public void sca_HardTokenActivationsuspended() throws InterruptedException {
        String strValCaptured = "";
        if (isElementDisplayed(getObject("launch.checkhardtokenactivation"), 2)) {
            strValCaptured = getText(getObject("launch.checkhardtokenactivation"), 100);
            report.updateTestLog("HardToken activation", "Page " + strValCaptured + " Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("HardToken Activation", "Hardtoken Activation page not displayed successfully", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("activationsuspended"), 2)) {
            report.updateTestLog("HardToken activation", "Button activation suspended Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("HardToken Activation", "Button activation suspended not displayed successfully", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("phoneregister"), 2)) {
            report.updateTestLog("phone register", "Button phone register Displayed Successfully", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("phone register", "Button phone register not displayed successfully", Status_CRAFT.FAIL);
        }

    }

    //Activation Pending Screen validation

    public void ActivationPendingScreenUI() throws Exception {

        if (isElementDisplayed(getObject("w.sca.ActivationPendingLink"), 5)) {
            report.updateTestLog("verifyActivationPendingHeader", "Activation Pending is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyActivationPendingHeader", "Activation Pending NOT displayed", Status_CRAFT.FAIL);
        }
        //Validate Content

        String Content = dataTable.getData("General_Data", "Content");
        if (isElementDisplayed(getObject("w.sca.ActivationPendinglabel"), 5)) {
            if (getText(getObject("w.sca.ActivationPendinglabel")).contains(Content)) {
                report.updateTestLog("verifyActivationPendingHeader", "Activation Pending Header" + getText(getObject("w.sca.ActivationPendinglabel")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyActivationPendingHeader", "Activation Pending Header" + getText(getObject("w.sca.ActivationPendinglabel")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyActivationPendingHeader", "Activation Pending Header" + getText(getObject("//*[@id=\"C2__C1__p1_HEAD_860F85ECC10F6BC81079828\"]")) + "NOT Exist", Status_CRAFT.FAIL);
        }


        //Validation Yes,No,not yet and Need Help

        if (isElementDisplayed(getObject("w.sca.YesButton"), 5)) {
            report.updateTestLog("verifyYESButton", "Yes Button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyYESButton", "Yes Button NOT displayed", Status_CRAFT.FAIL);
        }

        //Verify No,not yet
        if (isElementDisplayed(getObject("w.sca.NoNotYetButton"), 5)) {
            report.updateTestLog("verifyNoNotYet Button", "No,not Yet Button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyNoNotYet Button", "No,not Yet Button NOT displayed", Status_CRAFT.FAIL);
        }

        //Verify Need Help Link

        if (isElementDisplayed(getObject("w.sca.Needhelplink"), 5)) {
            report.updateTestLog("verifyNeedHelpLink", "Need Help link is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyNoNotYet Button", "Need Help link NOT displayed", Status_CRAFT.FAIL);
        }
    }

    //Validate Yes,No,not yet and Need Help Link

    public void validateActivationButtons() throws Exception {
        String Code = dataTable.getData("General_Data", "CODE");

        switch (Code) {

            case "Yes":
                if (isElementDisplayed(getObject("w.sca.YesButton"), 5)) {
                    // click(getObject("w.sca.YesButton"));
                    clickJS(getObject("w.sca.YesButton"), "Clicked on" + Code + "Button");
                    if (isElementDisplayed(getObject("w.sca.ActivateDeviceLabel"), 5)) {
                        report.updateTestLog("verifyYes Button", "Page is navigated to" + getText(getObject("w.sca.ActivateDeviceLabel")) + "after click on Yes Button", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyYes Button", "Page NOT navigated to" + getText(getObject("w.sca.ActivateDeviceLabel")) + "after click on Yes Button", Status_CRAFT.FAIL);
                    }
                } else {
                    report.updateTestLog("verifyYES Button", "YES Button NOT displayed", Status_CRAFT.FAIL);
                }
                break;

            case "No, not yet":
                if (isElementDisplayed(getObject("w.sca.NoNotYetButton"), 5)) {
                    clickJS(getObject("w.sca.NoNotYetButton"), "Clicked on" + Code + "Button");
                    //click(getObject("w.sca.NoNotYetButton"));
                    if (isElementDisplayed(getObject("w.sca.PhySecKeyLabel"), 5)) {
                        report.updateTestLog("verifyNoNotYet Button", "Page is navigated to" + getText(getObject("w.sca.PhySecKeyLabel")) + "Screen after click on No,not yet Button", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("verifyNoNotYet Button", "Page NOT navigated to" + getText(getObject("w.sca.PhySecKeyLabel")) + "Screen after click on No,not yet Button", Status_CRAFT.FAIL);
                    }
                } else {
                    report.updateTestLog("verifyNoNotYet Button", "No,not yet Button NOT displayed", Status_CRAFT.FAIL);
                }
                break;

            case "Need help?":

                if (isElementDisplayed(getObject("w.sca.Needhelplink"), 5)) {
                    //click(getObject("w.sca.Needhelplink"));
                    clickJS(getObject("w.sca.Needhelplink"), "Clicked on" + Code + "Button");
                    //Screen is not developed for Need Help
                } else {
                    report.updateTestLog("verifyNeedhelp Link", "Need help link NOT displayed", Status_CRAFT.FAIL);
                }
                break;
        }
    }

    //Validate Activate your Device Page

    public void validateActivateYourDeviceUI() throws Exception {

        //Validate Header

        String sHeader = dataTable.getData("General_Data", "HEADER");
        if (isElementDisplayed(getObject("w.sca.ActivationHeaderlabel"), 5)) {
            if (getText(getObject("w.sca.ActivationHeaderlabel")).contains(sHeader)) {
                report.updateTestLog("verifyActivateYourDevice Header", "Activate Your Device Header" + getText(getObject("w.sca.ActivationHeaderlabel")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyActivateYourDevice Header", "Activate Your Device Header" + getText(getObject("w.sca.ActivationHeaderlabel")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyActivateYourDevice Header", "Activate Your Device Header NOT EXIST", Status_CRAFT.FAIL);
        }

        //Validate Serial Number Image

        if (isElementDisplayed(getObject("w.sca.SerialImage"), 5)) {
            report.updateTestLog("verifySerialNumberImage", "Serial Number Image is displayed on Activate Your Device Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifySerialNumberImage", "Serial Number Image NOT displayed on Activate Your Device Page", Status_CRAFT.FAIL);
        }

        //Validate Go Back Button

        if (isElementDisplayed(getObject("w.sca.GoBackButton"), 5)) {
            report.updateTestLog("verifyGoBackButton", "Go Back Button is displayed on Activate Your Device Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyGoBackButton", "Go Back Button NOT displayed on Activate Your Device Page", Status_CRAFT.FAIL);
        }

        //Validate Need More Help Link

        if (isElementDisplayed(getObject("w.sca.NeedHelplink"), 5)) {
            report.updateTestLog("verifyNeedMoreHelpLink", "Need More Help? link is displayed on Activate Your Device Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyNeedMoreHelpLink", "Need More Help? NOT displayed on Activate Your Device Page", Status_CRAFT.FAIL);
        }

        //Validate Activate Now Button and it should be disable

        if (isElementDisplayed(getObject("w.sca.ActivateNowButton"), 5)) {
            report.updateTestLog("verifyActivateNowButton", "Activate Now Button is display on Activate Your Device Page", Status_CRAFT.PASS);
            if (isElementEnabled(getObject("w.sca.ActivateNowButtonDisabled"))) {
                report.updateTestLog("verifyActivateNowButtonDisable", "Activate Now Button is disable on Activate Your Device Page", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyActivateNowButtonDisable", "Activate Now Button is enable on Activate Your Device Page", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("verifyActivateNowButton", "Activate Now Button is display on Activate Your Device Page", Status_CRAFT.FAIL);
        }

        String[] arrPin = dataTable.getData("General_Data", "TokenPIN").split("");

        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("w.launch.edtHardtokenpinenter"));

        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
            }
        }

        //Validate Activate Now Button and it should be enabled

        if (isElementDisplayed(getObject("w.sca.ActivateNowButton"), 5)) {
            report.updateTestLog("verifyActivateNowButton", "Activate Now Button is display on Activate Your Device Page", Status_CRAFT.PASS);
            if (isElementEnabled(getObject("w.sca.ActivateNowButtonDisabled"))) {
                report.updateTestLog("verifyActivateNowButtonDisable", "Activate Now Button is disabled on Activate Your Device Page", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyActivateNowButtonDisable", "Activate Now Button is enabled on Activate Your Device Page", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("verifyActivateNowButton", "Activate Now Button is display on Activate Your Device Page", Status_CRAFT.FAIL);
        }
    }

    //Validate Added Security Page

    public void validateAddedSecurityPageUI() throws Exception {

        //Validate Header

        String sHeader = dataTable.getData("General_Data", "HEADER");
        if (isElementDisplayed(getObject("w.sca.AddedSecuritylabel"), 5)) {
            if (getText(getObject("w.sca.AddedSecuritylabel")).contains(sHeader)) {
                report.updateTestLog("verifyAddedSecurity Header", "Added Security Header" + getText(getObject("w.sca.AddedSecuritylabel")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddedSecurity Header", "Added Security Header" + getText(getObject("w.sca.AddedSecuritylabel")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAddedSecurity Header", "Added Security Header NOT EXIST", Status_CRAFT.FAIL);
        }

        //Validate Content

        String sContent = dataTable.getData("General_Data", "HEADER");
        if (isElementDisplayed(getObject("w.sca.AddedSecurityContent"), 5)) {
            if (getText(getObject("w.sca.AddedSecurityContent")).contains(sContent)) {
                report.updateTestLog("verifyAddedSecurity Content", "Content" + getText(getObject("w.sca.AddedSecurityContent")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyAddedSecurity Content", "Content" + getText(getObject("w.sca.AddedSecurityContent")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyAddedSecurity Content", "Content NOT EXIST", Status_CRAFT.FAIL);
        }

        //Validate OK Image

        if (isElementDisplayed(getObject("w.sca.AddedSecurityOKImg"), 5)) {
            report.updateTestLog("verifyOKImage", "OK Image is displayed on Added Security Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyOKImage", "OK Image NOT displayed on Added Security Page", Status_CRAFT.FAIL);
        }

        //Validate ENTER PIN Image

        if (isElementDisplayed(getObject("w.sca.AddedSecurityEntrPINImg"), 5)) {
            report.updateTestLog("verifyENTERPINImage", "ENTER PIN Image is displayed on Added Security Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyENTERPINImage", "ENTER PIN Image NOT displayed on Added Security Page", Status_CRAFT.FAIL);
        }

        //Validate 1+OK Image

        if (isElementDisplayed(getObject("w.sca.AddedSecurityPowerImg"), 5)) {
            report.updateTestLog("verify1+OKImage", "1+OK Image is displayed on Added Security Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verify1+OKImage", "1+OK Image is displayed on Added Security Page", Status_CRAFT.FAIL);
        }

        //Validate Go Back Button

        if (isElementDisplayed(getObject("w.sca.AddedSecurityGoBackBtn"), 5)) {
            report.updateTestLog("verifyGoBackButton", "Go Back Button is displayed on Added Security Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyGoBackButton", "Go Back Button NOT displayed on Added Security Page", Status_CRAFT.FAIL);
        }

        //Validate Need More Help Link

        if (isElementDisplayed(getObject("w.sca.AddedSecurityNeedHelplink"), 5)) {
            report.updateTestLog("verifyNeedMoreHelpLink", "Need More Help? link is displayed on Added Security Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyNeedMoreHelpLink", "Need More Help? NOT displayed on Added Security Page", Status_CRAFT.FAIL);
        }

        //Validate Activate Now Button and it should be disable

        if (isElementDisplayed(getObject("w.sca.AddedSecurityConfirmBtn"), 5)) {
            report.updateTestLog("verifyConfirmButton", "Confirm Button is display on Added Security Page", Status_CRAFT.PASS);
            if (isElementEnabled(getObject("w.sca.ActivateNowButtonDisabled"))) {
                report.updateTestLog("verifyConfirmButtonDisable", "Confirm Button is enable on Added Security Page", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyConfirmButtonDisable", "Confirm Button is disable on Added Security Page", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("verifyConfirmButtonDisable", "Confirm Button is enable on Added Security Page", Status_CRAFT.FAIL);
        }

        String[] arrPin = dataTable.getData("General_Data", "TokenPIN").split("");

        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("w.sca.AddSecurityHardtokenpinenter"));

        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
            }
        }

        //Validate Activate Now Button and it should be enabled

        if (isElementDisplayed(getObject("w.sca.AddedSecurityConfirmBtn"), 5)) {
            report.updateTestLog("verifyConfirmButton", "Confirm Button is display on Added Security Page", Status_CRAFT.PASS);
            if (isElementEnabled(getObject("w.sca.ActivateNowButtonDisabled"))) {
                report.updateTestLog("verifyConfirmButtonDisable", "Confirm Button is disabled on Added Security Page", Status_CRAFT.FAIL);
            } else {
                report.updateTestLog("verifyConfirmButtonDisable", "Confirm Button is enabled on Added Security Page", Status_CRAFT.PASS);
            }
        } else {
            report.updateTestLog("verifyConfirmButton", "Confirm Button is display on Added Security Page", Status_CRAFT.FAIL);
        }
    }

    /*General Function:
      Scroll to view particular  element using JS
     */
    public void ScrollToVisibleJS(String linkToVisible) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToVisible))));
            Thread.sleep(2000);
            //report.updateTestLog("ScrollAndClickJS", "Element To View", Status_CRAFT.SCREENSHOT);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }

    }

    //CS-379
    //Choose your Security Method

    public void validateChooseYourSecurityUI() throws Exception {

        //Validate Header
        String sHeader = dataTable.getData("General_Data", "HEADER");
        if (isElementDisplayed(getObject("w.sca.ChooseyoursecurityHeader"), 5)) {
            ScrollToVisibleJS("w.sca.ChooseyoursecurityHeader");
            if (getText(getObject("w.sca.ChooseyoursecurityHeader")).contains(sHeader)) {
                report.updateTestLog("verifyChooseYourSecurity Header:", "Choose Your Security method Header" + getText(getObject("w.sca.ChooseyoursecurityHeader")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyChooseYourSecurity Header:", "Choose Your Security method Header" + getText(getObject("w.sca.ChooseyoursecurityHeader")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyChooseYourSecurity Header", "Choose Your Security method Header NOT EXIST", Status_CRAFT.FAIL);
        }

        //Validate At Bank of Ireland, we take your online security very seriously. That's why we're introducing a new layer of protection for your accounts.
        String sContentMsg = dataTable.getData("General_Data", "Account_Label");
        if (isElementDisplayed(getObject("w.sca.BOIContentlabel"), 5)) {
            ScrollToVisibleJS("w.sca.BOIContentlabel");
            if (getText(getObject("w.sca.BOIContentlabel")).contains(sContentMsg)) {
                report.updateTestLog("verifyContent", "Content:" + getText(getObject("w.sca.BOIContentlabel")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent", "Content:" + getText(getObject("w.sca.BOIContentlabel")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Content:" + sContentMsg + "NOT Exist", Status_CRAFT.FAIL);
        }


        //Validate From 01 September 2019 you will be required to use a secondary device for security confirmation. You'll need to prove that it's really you whenever you log in, make important changes to your account, or send money. Your smartphone or tablet is the fastest and easiest way to do this, however you can also order a Physical Security Key.;
        String sContentsecMsg = dataTable.getData("General_Data", "AccountPosition_Grouped");
        if (isElementDisplayed(getObject("w.sca.BOIContentSecondlabel"), 5)) {
            ScrollToVisibleJS("w.sca.BOIContentSecondlabel");
            if (getText(getObject("w.sca.BOIContentSecondlabel")).contains(sContentsecMsg)) {
                report.updateTestLog("verifyContent", "Content:" + getText(getObject("w.sca.BOIContentSecondlabel")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent", "Content:" + getText(getObject("w.sca.BOIContentSecondlabel")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Content:" + sContentsecMsg + "NOT Exist", Status_CRAFT.FAIL);
        }

        //Validate No matter how you choose to bank online, your smartphone or tablet is now the recommended security key you use to access your account.
        String sContentBoldMsg = dataTable.getData("General_Data", "VerifyDetails");
        if (isElementDisplayed(getObject("w.sca.ContentBoldlabel"), 5)) {
            ScrollToVisibleJS("w.sca.ContentBoldlabel");
            if (getText(getObject("w.sca.ContentBoldlabel")).contains(sContentBoldMsg)) {
                report.updateTestLog("verifyContent", "Content:" + getText(getObject("w.sca.ContentBoldlabel")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent", "Content:" + getText(getObject("w.sca.ContentBoldlabel")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Content:" + sContentBoldMsg + "NOT Exist", Status_CRAFT.FAIL);
        }

        //Validate Get security notifications to your device whenever

        String sContentGetSecMsg = dataTable.getData("General_Data", "Account_Type");
        if (isElementDisplayed(getObject("w.sca.ContentGetSeclabel"), 5)) {
            ScrollToVisibleJS("w.sca.ContentGetSeclabel");
            if (getText(getObject("w.sca.ContentGetSeclabel")).contains(sContentGetSecMsg)) {
                report.updateTestLog("verifyContent", "Content:" + getText(getObject("w.sca.ContentGetSeclabel")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent", "Content:" + getText(getObject("w.sca.ContentGetSeclabel")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Content:" + sContentGetSecMsg + "NOT Exist", Status_CRAFT.FAIL);
        }

        //Validate a login happens with blue tick

        String sContentloginTickMsg = dataTable.getData("General_Data", "Nickname");
        if (isElementDisplayed(getObject("w.sca.Contentloginticklabel"), 5)) {
            ScrollToVisibleJS("w.sca.Contentloginticklabel");
            if (getText(getObject("w.sca.Contentloginticklabel")).contains(sContentloginTickMsg)) {
                report.updateTestLog("verifyContent", "Content:" + getText(getObject("w.sca.Contentloginticklabel")) + "with blue tick is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent", "Content:" + getText(getObject("w.sca.Contentloginticklabel")) + "with blue tick NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Content:" + sContentloginTickMsg + "NOT Exist", Status_CRAFT.FAIL);
        }

        //Validate changes are made to your account with blue tick

        String sContentChangesTickMsg = dataTable.getData("General_Data", "Current_Balance");
        if (isElementDisplayed(getObject("w.sca.ContentChangesticklabel"), 5)) {
            ScrollToVisibleJS("w.sca.ContentChangesticklabel");
            if (getText(getObject("w.sca.ContentChangesticklabel")).contains(sContentChangesTickMsg)) {
                report.updateTestLog("verifyContent", "Content:" + sContentChangesTickMsg + "with blue tick is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent", "Content:" + sContentChangesTickMsg + "with blue tick NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Content:" + sContentChangesTickMsg + "NOT Exist", Status_CRAFT.FAIL);
        }

        //Validate payments are made with blue tick

        //String sContentPaymentsTickMsg = "payments are made";
        String sContentPaymentsTickMsg = dataTable.getData("General_Data", "Available_Balance");
        if (isElementDisplayed(getObject("w.sca.ContentPaymentsTicklabel"), 5)) {
            ScrollToVisibleJS("w.sca.ContentPaymentsTicklabel");
            if (getText(getObject("w.sca.ContentPaymentsTicklabel")).contains(sContentPaymentsTickMsg)) {
                report.updateTestLog("verifyContent", "Content:" + sContentPaymentsTickMsg + "with blue tick is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent", "Content:" + sContentPaymentsTickMsg + "with blue tick NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Content:" + sContentPaymentsTickMsg + "NOT Exist", Status_CRAFT.FAIL);
        }


        //Validate Mobile Phone Image

        if (isElementDisplayed(getObject("w.sca.MobileImg"), 5)) {
            ScrollToVisibleJS("w.sca.MobileImg");
            report.updateTestLog("verifyMobileImage", "Mobile Image is displayed on Choose Your Security method Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyMobileImage", "Mobile Image is displayed on Choose Your Security method Page", Status_CRAFT.FAIL);
        }

        //Validate Register your smartphone or tablet in minutes
        String sContent = dataTable.getData("General_Data", "Currency_Symbol");
        if (isElementDisplayed(getObject("w.sca.Contentlabel"), 5)) {
            ScrollToVisibleJS("w.sca.Contentlabel");
            if (getText(getObject("w.sca.Contentlabel")).contains(sContent)) {
                report.updateTestLog("verifyContent", "Content:" + sContent + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent", "Content:" + sContent + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent:", "Register your smartphone or tablet in minutes label NOT EXIST", Status_CRAFT.FAIL);
        }

        //Validate Register Device Button

        if (isElementDisplayed(getObject("w.sca.RegisterDeviceBtn"), 5)) {
            ScrollToVisibleJS("w.sca.RegisterDeviceBtn");
            report.updateTestLog("verifyRegisterDeviceButton:", "Register Device is displayed on Choose your security method screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyRegisterDeviceButton:", "Register Device NOT displayed on Choose your security method screen", Status_CRAFT.FAIL);
        }
        //Validate Hard Token Image

        if (isElementDisplayed(getObject("w.sca.SecurityDeviceImg"), 5)) {
            ScrollToVisibleJS("w.sca.SecurityDeviceImg");
            report.updateTestLog("verifyHardTokenImage:", "Hard Token Image is displayed on Choose Your Security method Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyHardTokenImage:", "Hard Token Image is displayed on Choose Your Security method Page", Status_CRAFT.FAIL);
        }

        //Validate Don't have a smartphone? Order a Physical Security Key label

        String sHardToken = dataTable.getData("General_Data", "IBAN_Number");
        if (isElementDisplayed(getObject("w.sca.PhySecurityKeylabel"), 5)) {
            ScrollToVisibleJS("w.sca.PhySecurityKeylabel");
            if (getText(getObject("w.sca.PhySecurityKeylabel")).contains(sHardToken)) {
                report.updateTestLog("verifyContent:", "Content" + sHardToken + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent:", "Content" + sHardToken + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent:", "Don't have a smartphone? Order a Physical Security Key label NOT EXIST", Status_CRAFT.FAIL);
        }

        //Validate Find out more about the Physical Security Key Link

        String sFindOutlink = dataTable.getData("General_Data", "Account_Grouped");
        if (isElementDisplayed(getObject("w.sca.Findoutlink"), 5)) {
            ScrollToVisibleJS("w.sca.Findoutlink");
            if (getText(getObject("w.sca.Findoutlink")).contains(sFindOutlink)) {
                report.updateTestLog("verifyContent:", "Content" + sFindOutlink + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent:", "Content" + sFindOutlink + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Link:" + sFindOutlink + "NOT Exist", Status_CRAFT.FAIL);
        }


        //Validate It may take up to 10 working days for you to receive your new Physical Security Key.label

        String sContentSecuritylabel = dataTable.getData("General_Data", "Account_Name");
        if (isElementDisplayed(getObject("w.sca.SecContentlabel"), 5)) {
            ScrollToVisibleJS("w.sca.SecContentlabel");
            if (getText(getObject("w.sca.SecContentlabel")).contains(sContentSecuritylabel)) {
                report.updateTestLog("verifyContent", "Content:" + sContentSecuritylabel + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent", "Content:" + sContentSecuritylabel + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent:", "It may take up to 10 working days for you to receive your new Physical Security Key. label NOT EXIST", Status_CRAFT.FAIL);
        }
    }

    //Validate Choose Security Device

    public void validateSelectDevice() throws Exception {
        String sPhone = dataTable.getData("General_Data", "PHONE");
        String xpath = "xpath~//div[@style='text-align: left; ; float: left;']/descendant::span[text() = '" + sPhone + "']";
        if (isElementDisplayed(getObject(xpath), 10)) {
            clickJS(getObject(xpath), "Click on Instrument :" + sPhone);
            Thread.sleep(3000);
            if (isElementEnabled(getObject("w.sca.ChooseDeviceButton"))) {
                ScrollToVisibleJS("w.sca.ChooseDeviceButton");
                report.updateTestLog("VerifyChooseDevice Button", "Choose Device Button is Enable after Choose Security Device", Status_CRAFT.PASS);
                clickJS(getObject("w.sca.ChooseDeviceButton"), "Choose Security Device");
                if (isElementDisplayed(getObject("w.sca.AccountSecurityHeader"), 5)) {
                    report.updateTestLog("Verify Choose Security Button", "Choose Security Button is clicked and Page is Navigated to Account Security Page", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("Verify Choose Security Button", "Choose Security Button NOT clicked and Page NOT Navigated to Account Security Page", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("VerifyChooseDevice Button", "VerifyChooseDevice Button is Disable after Choose Security Device", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("Verifyxpath", "Xpath for Choose Device NOT Correct", Status_CRAFT.FAIL);
        }
    }

    //Validate Register device Page

    public void validatesmartphoneDeviceUI() throws Exception {

        if (isElementDisplayed(getObject("w.phoneregisterbutton"), 10)) {
            report.updateTestLog("phone register", "Button device register Displayed Successfully", Status_CRAFT.PASS);
            clickJS(getObject(("w.phoneregisterbutton")), "register phone device ");
            Thread.sleep(1000);
        } else {
            report.updateTestLog("phone register", "Button device register not displayed successfully", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("w.Registerphonpage"), 5)) {
            ScrollToVisibleJS("w.Registerphonpage");
            report.updateTestLog("Registerphonpage", "Register phone heading displayed over register phone Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Registerphonpage", "Register phone heading not displayed over register phone Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("w.Smartphonetext"), 5)) {
            ScrollToVisibleJS("w.Smartphonetext");
            report.updateTestLog("Smartphonetext", "Smart phone text displayed over register phone Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Smartphonetext", "Smart phone text not displayed over register phone Page", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("w.Smartphoneloginbutton"), 5)) {
            ScrollToVisibleJS("w.Smartphoneloginbutton");
            report.updateTestLog("Smartphoneloginbutton", "Smartphone login button displayed over register phone Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Smartphoneloginbutton", "Smart phone loginbutton not displayed over register phone Page", Status_CRAFT.FAIL);
        }
    }

    //
    public void HardToken_fromplaceholderUI() throws Exception {

        //Validate Header
        //String sHeader = "Choose your security method";
        String sHeader = dataTable.getData("General_Data", "HEADER");
        if (isElementDisplayed(getObject("w.sca.ChooseyoursecurityHeader"), 5)) {
            if (getText(getObject("w.sca.ChooseyoursecurityHeader")).contains(sHeader)) {
                report.updateTestLog("verifyChooseYourSecurity Header", "Choose Your Security method Header" + sHeader + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyChooseYourSecurity Header", "Choose Your Security method Header" + sHeader + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyChooseYourSecurity Header", "Choose Your Security method Header NOT EXIST", Status_CRAFT.FAIL);
        }

        //Validate Find out more about the Physical Security Key Link
        //String sFindOutlink = "Find out more about the Physical Security Key";
        String sFindOutlink = dataTable.getData("General_Data", "HEADER");
        if (isElementDisplayed(getObject("w.sca.Findoutlink"), 5)) {
            if (getText(getObject("w.sca.Findoutlink")).contains(sFindOutlink)) {
                report.updateTestLog("verifyContent", "Content" + sFindOutlink + "is displayed", Status_CRAFT.PASS);
                click(getObject("w.sca.Findoutlink"), "Find Physical Security Key");
            } else {
                report.updateTestLog("verifyContent", "Content" + sFindOutlink + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Find out more about the Physical Security Key Link NOT EXIST", Status_CRAFT.FAIL);
        }
    }
    //Validate Not ready to register yet? Log in on old site

    public void completeRegistrationRegUI() throws Exception {
        if (isElementDisplayed(getObject("w.sca.NotReadyToRegYetLink"), 5)) {
            ScrollToVisibleJS("w.sca.NotReadyToRegYetLink");
            report.updateTestLog("VerifyNotReady Link", "Not ready to register yet? Log in on old site Link is EXIST on Complete your Registration Page", Status_CRAFT.PASS);
            clickJS(getObject("w.sca.NotReadyToRegYetLink"), "Not ready to register yet?");
            if (isElementDisplayed(getObject("w.sca.Amanded365Label"), 5)) {
                report.updateTestLog("VerifyAmanded365Digital Page", "Amended 365 Digital Page is DISPLAYED after click on Not ready to register yet? Log in on old site Link", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("VerifyAmanded365Digital Page", "Amended 365 Digital Page NOT DISPLAYED after click on Not ready to register yet? Log in on old site Link", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("VerifyNotReady Link", "Not ready to register yet? Log in on old site Link NOT EXIST on Complete your Registration Page", Status_CRAFT.FAIL);
        }

    }

    public void completeRegistrationSupportUI() throws Exception {
        //Validate Contact Support Link

        if (isElementDisplayed(getObject("w.sca.ContactSupportLink"), 5)) {
            ScrollToVisibleJS("w.sca.ContactSupportLink");
            report.updateTestLog("VerifyContactSupport Link", "Contact Support Link is EXIST on Complete your Registration Page", Status_CRAFT.PASS);
            //clickJS(getObject("w.sca.ContactSupportLink"), "Contact Support");
            //if(isElementDisplayed(getObject("w.sca.OrderPhySecKeylabel"), 5)){
            //report.updateTestLog("Verify OrderPhysicalSecurity Page", "Order a Physical Security Key is DISPLAYED after click on Physical Security Key Link", Status_CRAFT.PASS);
            // }
            //  else {
            //report.updateTestLog("Verify OrderPhysicalSecurity Page", "Order a Physical Security Key NOT DISPLAYED after click on Physical Security Key Link", Status_CRAFT.FAIL);
            //}
        } else {
            report.updateTestLog("VerifyContactSupport Link", "Contact Support Link Link NOT EXIST on Complete your Registration Page", Status_CRAFT.FAIL);
        }
    }

    //Validate Complete Registration Page

    public void completeRegistrationSecUI() throws Exception {

        //Validate Complete Registration Heading

        if (isElementDisplayed(getObject("w.sca.ComRegLabel"), 5)) {
            report.updateTestLog("verifyCompleteRegistration Header", "Complete Registration Header is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyCompleteRegistration Header", "Complete Registration Header NOT EXIST", Status_CRAFT.FAIL);
        }

        //Validate Physical Security Link

        if (isElementDisplayed(getObject("w.sca.PhySecLink"), 5)) {
            ScrollToVisibleJS("w.sca.PhySecLink");
            report.updateTestLog("VerifyPhysicalSecurityKey Link", "Physical Security Key Link is EXIST on Complete your Registration Page", Status_CRAFT.PASS);
            clickJS(getObject("w.sca.PhySecLink"), "Physical Security Key");
            if (isElementDisplayed(getObject("w.sca.OrderPhySecKeylabel"), 5)) {
                report.updateTestLog("Verify OrderPhysicalSecurity Page", "Order a Physical Security Key is DISPLAYED after click on Physical Security Key Link", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Verify OrderPhysicalSecurity Page", "Order a Physical Security Key NOT DISPLAYED after click on Physical Security Key Link", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("VerifyPhysicalSecurityKey Link", "Physical Security Key Link NOT EXIST on Complete your Registration Page", Status_CRAFT.FAIL);
        }

    }

//Function to perform operation over physical security key

    public void OrderSecurityKeyUI() throws Exception {

        if (isElementDisplayed(getObject("w.OrderSecuritykeypage"), 5)) {
            report.updateTestLog("verifyChooseYourSecurity Header", "Order Security Key Page displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyChooseYourSecurity Header", "Order Security Key Page NOT EXIST", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("w.OrderSecuritykeyGoBack"), 5)) {
            report.updateTestLog("verifyContent", "Go Back is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyContent", "Go Back button NOT EXIST", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("w.OrderSecuritykeyConfirm"), 5)) {
            report.updateTestLog("verifyContent", "Confirm is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyContent", "Confirm button NOT EXIST", Status_CRAFT.FAIL);
        }
    }
    //CS-1118
    //Order a Physical Security Key Page validation

    public void validateOrderPhyKeyUI() throws Exception {

        //Validate Order a Physical Security Key Header
        String sHeader = dataTable.getData("General_Data", "HEADER");
        if (isElementDisplayed(getObject("w.sca.OrderPhySecKeylabel"), 5)) {
            if (getText(getObject("w.sca.OrderPhySecKeylabel")).contains(sHeader)) {
                report.updateTestLog("verifyOrderPhySecurity Header", " Header" + sHeader + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyOrderPhySecurity Header", " Header" + sHeader + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyOrderPhySecurity Header", "Order a Physical Security Key Header NOT EXIST", Status_CRAFT.FAIL);
        }

        //Validate Hard Token Image

        if (isElementDisplayed(getObject("w.sca.HardTokenImg"), 5)) {
            report.updateTestLog("verifyHardTokenImage", "Hard Token Image is displayed on Order a Physical Security Key Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyHardTokenImage", "Hard Token Image NOT displayed on Order a Physical Security Key Page", Status_CRAFT.FAIL);
        }

        //Validate Order Form Label

        //String sContentChangesTickMsg = "Order Form";
        String sContentChangesTickMsg = dataTable.getData("General_Data", "HEADER");
        if (isElementDisplayed(getObject("w.sca.OrderFormLabel"), 5)) {
            if (getText(getObject("w.sca.OrderFormLabel")).contains(sContentChangesTickMsg)) {
                report.updateTestLog("verifyOrderForm", "Heading" + getText(getObject("w.sca.OrderFormLabel")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyOrderForm", "Heading" + getText(getObject("w.sca.OrderFormLabel")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Content:" + sContentChangesTickMsg + "NOT Exist", Status_CRAFT.FAIL);
        }
        //Validate Go Back Button
        if (isElementDisplayed(getObject("w.sca.OrderGoBackButton"), 5)) {
            report.updateTestLog("verifyGoBackButton", "Go Back Button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyGoBackButton", "Go Back Button NOT displayed", Status_CRAFT.FAIL);
        }

        //Validate Confirm Button
        if (isElementDisplayed(getObject("w.sca.OrderConfirmButton"), 5)) {
            report.updateTestLog("verifyConfirmButton", "Confirm Button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyConfirmButton", "Confirm Button NOT displayed", Status_CRAFT.FAIL);
        }

        //Validate Content
        if (isElementDisplayed(getObject("w.sca.OrderPhySecParalabel"), 5)) {
            report.updateTestLog("verifyContent", "Content" + getText(getObject("w.sca.OrderPhySecParalabel")) + "is displayed above Order Form Label", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyContent", "Content" + getText(getObject("w.sca.OrderPhySecParalabel")) + "NOT displayed above Order Form Label", Status_CRAFT.FAIL);
        }

        //Validate Please confirm the following details are correct. A Bank of Ireland representative will then get in touch to arrange delivery of your Physical Security Key. label
        //String sHardLabel = "Please confirm the following details are correct. A Bank of Ireland representative will then get in touch to arrange delivery of your Physical Security Key.";
        String sHardLabel = dataTable.getData("General_Data", "VerifyContent");
        if (isElementDisplayed(getObject("w.sca.OrderPhyLabel"), 5)) {
            if (getText(getObject("w.sca.OrderPhyLabel")).contains(sHardLabel)) {
                report.updateTestLog("verifyContent", "Content" + getText(getObject("w.sca.OrderPhyLabel")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyContent", "Content" + getText(getObject("w.sca.OrderPhyLabel")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Content:" + sHardLabel + "NOT Exist", Status_CRAFT.FAIL);
        }
    }

    //CS-1100
    //Validate Bank of Ireland Welcome Page

    public void validateBOIWelcomeUI() throws Exception {

        //Validate Header
        if (isElementDisplayed(getObject("w.sca.BOIWlcmLabel"), 5)) {
            report.updateTestLog("verifyTitle", "Title Bank of Ireland is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyTitle", "Title Bank of Ireland NOT displayed", Status_CRAFT.FAIL);
        }

        //Validate Continue To Login Button

        if (isElementDisplayed(getObject("w.sca.ContinueToLoginbtn"), 5)) {
            report.updateTestLog("verifyContinueToLogin Button", "Continue to Login Button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyContinueToLogin Button", "Continue to Login Button NOT displayed", Status_CRAFT.FAIL);
        }

        //Validate Welcome to your new online banking experience Label

        String sWelcomeContent = dataTable.getData("General_Data", "HEADER");
        if (isElementDisplayed(getObject("w.sca.WelcmOnlineBankLabel"), 5)) {
            if (getText(getObject("w.sca.WelcmOnlineBankLabel")).contains(sWelcomeContent)) {
                report.updateTestLog("verifyTitle", "Title: " + getText(getObject("w.sca.WelcmOnlineBankLabel")) + "is displayed", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyTitle", "Title :" + getText(getObject("w.sca.WelcmOnlineBankLabel")) + "NOT displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Content:" + sWelcomeContent + "NOT Exist", Status_CRAFT.FAIL);
        }

        //Validate What you'll need to get started Link

        String sWhatContent = dataTable.getData("General_Data", "Account_Type");
        if (isElementDisplayed(getObject("w.sca.WhatYouwillLink"), 5)) {
            ScrollToVisibleJS("w.sca.WhatYouwillLink");
            report.updateTestLog("VerifyWhatWillYouNeed Link", "What you'll need to get started Link is EXIST on Welcome BOI Page", Status_CRAFT.PASS);
            clickJS(getObject("w.sca.WhatYouwillLink"), "What you Will neeed to get started Link");
            if (isElementDisplayed(getObject("w.sca.WhatwillyouNeedLabel"), 5)) {
                if (getText(getObject("w.sca.WhatwillyouNeedLabel")).contains(sWhatContent)) {
                    report.updateTestLog("Verify WhatWillYouNeed Label", "Label:" + getText(getObject("w.sca.WhatwillyouNeedLabel")) + "Exist after click on What you'll need to get started Link", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("Verify WhatWillYouNeed Label", "Label:" + getText(getObject("w.sca.WhatwillyouNeedLabel")) + " NOT Exist after click on What you'll need to get started Link", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("verifyWhatwillYou NeedLink", "Link :" + sWhatContent + "NOT Exist", Status_CRAFT.FAIL);
        }

        //Validate How to videos label

        if (isElementDisplayed(getObject("w.sca.HowToVideosLabel"), 5)) {
            report.updateTestLog("verifyHowToVideos Label", "How to videos label is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyHowToVideos Button", "How to videos label NOT displayed", Status_CRAFT.FAIL);
        }

        //Validate Click here if you need help with any of these Link

        String sLinkContent = dataTable.getData("General_Data", "Nickname");
        String lNeedHelp = dataTable.getData("General_Data", "Current_Balance");
        if (isElementDisplayed(getObject("w.sca.ClickhereLink"), 5)) {
            ScrollToVisibleJS("w.sca.ClickhereLink");
            report.updateTestLog("VerifyClickHere Link", "Link: " + sLinkContent + "is EXIST on Welcome BOI Page", Status_CRAFT.PASS);
            clickJS(getObject("w.sca.ClickhereLink"), sLinkContent);
            if (isElementDisplayed(getObject("w.sca.NeedHelpLink"), 5)) {
                if (getText(getObject("w.sca.NeedHelpLink")).contains(lNeedHelp)) {
                    report.updateTestLog("Verify NeedHelp? Label", "Label:" + getText(getObject("w.sca.NeedHelpLink")) + "Exist after click on" + sLinkContent + "Link", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("Verify NeedHelp? Label", "Label:" + getText(getObject("w.sca.NeedHelpLink")) + "NOT Exist after click on" + sLinkContent + "Link", Status_CRAFT.FAIL);
                }
            } else {
                report.updateTestLog("VerifyClickHere", "Link :" + sLinkContent + "NOT Exist", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("verifyContent", "Link:" + sLinkContent + "NOT Exist", Status_CRAFT.FAIL);
        }
    }


    public void activeDeviceBlock() throws Exception{
       if(isElementDisplayed(getObject("xpath~//h1[text()='This Device Is Blocked']"),3)){
           report.updateTestLog("activeDeviceBlock", "DeviceBlock screen is coming on launching the app", Status_CRAFT.PASS);
           isElementDisplayed(getObject("xpath~//div[text()='This device is blocked from accessing this user ID. Follow the instructions below to unblock it:']"),1);
           List<WebElement> para= driver.findElements(By.xpath("//div[contains(@class,'boi-ml-13')]/div/span[2]"));
           if(para.size()!=0){
               for(int i=0;i<para.size(); i++){
                   String text=para.get(i).getText();
                   if(text.equals("Log in to your account on another device")||text.equals("Tap the profile icon")||text.equals("Under 'My security devices' select this device from the list")||text.equals("Tap 'Unblock device'")){
                       report.updateTestLog("activeDeviceBlock", "bullet points have correct details",Status_CRAFT.PASS);
                   }
               }
           }
           else{
               report.updateTestLog("verifyCallFunctionality", "Call button not present",Status_CRAFT.FAIL);
           }
           isElementDisplayed(getObject("xpath~//span[text()='Contact us']"),2);

        }

    }
}

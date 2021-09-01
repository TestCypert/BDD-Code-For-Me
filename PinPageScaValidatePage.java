package com.boi.grp.pageobjects.login;

import com.boi.grp.pageobjects.BasePageForDevices;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by C112083 on 26/10/2020.
 */
public class PinPageScaValidatePage extends BasePageForDevices {


    @FindBy(how = How.XPATH, using = "//*[@class='spinner']")
    WebElement spinner;

    @FindBy(how = How.XPATH, using = "//*[text()='Back']")
    WebElement backButton;

    @FindBy(how = How.XPATH, using = "//*[@class='boi-sca-progress-bar boi-no-padding']")
    WebElement progressBar;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Step 3 of 5')]")
    WebElement txtStep3to5;

    @FindBy(how = How.XPATH, using = "//*[text()='PIN']")
    WebElement pin;

    @FindBy(how = How.XPATH, using = "//*[contains(@class,'boi-mt-30 boi_para')]")
    WebElement txtHeadTxtMsgPIN;

    @FindBy(how = How.XPATH, using = "//*[contains(@class,'boi-device-italic-blue-button')]")
    WebElement lnkForgotYourPIN;

    @FindBy(how = How.XPATH, using = "//fieldset[contains(@id,'GROUP_FS_HEAD')]/descendant::input[@type='number']")
    WebElement groupFsHead;

    @FindBy(how = How.XPATH, using = "//fieldset[@id='C2__GROUP_FS_HEAD_C153BBAC8B09CBAC3953749']//input")
    WebElement c2GroupFsHead;

    @FindAll(@FindBy(how = How.XPATH, using = "//fieldset[contains(@id,'GROUP_FS_HEAD')]/descendant::input[@type='number']"))
    List<WebElement> listGroupFsHead;

    @FindAll(@FindBy(how = How.XPATH, using = "//fieldset[@id='C2__GROUP_FS_HEAD_C153BBAC8B09CBAC3953749']//input"))
    List<WebElement> listC2GroupFsHead;

    @FindBy(how = How.XPATH, using = "//*[text()='Continue']")
    WebElement continueButton;

    public PinPageScaValidatePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void verifyPinPageIsVisible(){
        try {
            while (isElementDisplayed(spinner)) {
                waitForPageLoaded();
            }
            isElementDisplayedWithLog(txtStep3to5);
            logMessage("Device Provisioning - PIN page launched successfully");
            injectMessageToCucumberReport("Device Provisioning -PIN page launched successfully");
        } catch (Exception e) {
            logError("Failure in Device Provisioning -  PIN Page "+e.getMessage());
            injectWarningMessageToCucumberReport("Failure in Device Provisioning -  PIN Page "+e.getMessage());
            appendScreenshotToCucumberReport();
        }
    }

    public void enterPin(String pin){

        String[] arrPin = pin.split("");
        try {
            List<WebElement> lstPinEnterFieldGrp = null;

            if (isElementDisplayed(groupFsHead)) {
                lstPinEnterFieldGrp=listGroupFsHead;
            } else if (isElementDisplayed(c2GroupFsHead)) {
                lstPinEnterFieldGrp=listC2GroupFsHead;
            }
            //Entering values for only enabled fields
            for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
                if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                    lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                    logMessage("Pin has been entered successfully");
                    injectMessageToCucumberReport("Pin has been entered successfully");
                    appendScreenshotToCucumberReport();
                }
            }
            appendScreenshotToCucumberReport();
            clickJS(continueButton);
            logMessage("Continue button is clicked on PIN page");
            injectMessageToCucumberReport("Continue button is clicked on PIN page");
            appendScreenshotToCucumberReport();
        } catch (Exception e) {
            logError("Failure in PIN page entry "+e.getMessage());
            injectWarningMessageToCucumberReport("Failure in PIN page entry "+e.getMessage());
            appendScreenshotToCucumberReport();
        }
    }

}



package com.boi.grp.pageobjects.login;

import com.boi.grp.pageobjects.BasePageForDevices;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by C112083 on 26/10/2020.
 */
public class DobScaValidatePage extends BasePageForDevices {



    @FindBy(how = How.XPATH, using = "//*[@class='spinner']")
    WebElement spinner;

    @FindBy(how = How.XPATH, using = "//*[text()='Back']")
    WebElement backButton;

    @FindBy(how = How.XPATH, using = "//*[@class='boi-sca-progress-bar boi-no-padding']")
    WebElement progressBar;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Step 2 of 5')]")
    WebElement txtStep2to5;

    @FindBy(how = How.XPATH, using = "//*[text()='Enter your date of birth']")
    WebElement txtHeadDateOfBirth;

    @FindBy(how = How.XPATH, using = "//label[text()='Date of birth']")
    WebElement lblDateOfBirth;

    @FindBy(how = How.XPATH, using = "//input[@name='Day']")
    WebElement edtDay;

    @FindBy(how = How.XPATH, using = "//input[@name='Month']")
    WebElement edtMonth;

    @FindBy(how = How.XPATH, using = "//input[@name='Year']")
    WebElement edtYear;

    @FindBy(how = How.XPATH, using = "//*[contains(@name,'DATEOFBIRTH')]")
    WebElement edtEnterYourDateOfBirth;

    @FindBy(how = How.XPATH, using = "//*[text()='Continue']")
    WebElement continueButton;

    public DobScaValidatePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void verifyDobPageIsVisible(){
        try {
            while (isElementDisplayed(spinner)) {
                waitForPageLoaded();
            }
            isElementDisplayedWithLog(txtStep2to5);
            logMessage("Device Provisioning -  DOB launched successfully");
            injectMessageToCucumberReport("Device Provisioning -  DOB launched successfully");
        } catch (Exception e) {
            logError("Failure in Device Provisioning -  DOB launched "+e.getMessage());
            injectWarningMessageToCucumberReport("Failure in Device Provisioning -  DOB launched "+e.getMessage());
            appendScreenshotToCucumberReport();
        }
    }

    public void enterDOB(String date){
        try {
            if (isElementDisplayed(edtDay)) {
                sendKey(edtDay, date.split("/")[0]);
                logMessage("Entered date as ,  = "+date.split("/")[0]);
                sendKey(edtMonth, date.split("/")[1]);
                logMessage("Entered month as ,  = "+date.split("/")[1]);
                sendKey(edtYear, date.split("/")[2]);
                logMessage("Entered year as ,  = "+date.split("/")[2]);
            } else {
                sendKey(edtEnterYourDateOfBirth, date);
            }
            appendScreenshotToCucumberReport();
            clickJS(continueButton);
            logMessage("Clicked Next Button after DOB Page");

        } catch (Exception e) {
            logError("Failure in DOB Page data entry");
            injectWarningMessageToCucumberReport("Failure in DOB Page data entry "+e.getMessage());
            appendScreenshotToCucumberReport();
        }
    }

}

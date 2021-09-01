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
public class UserIdScaValidatePage extends BasePageForDevices {


    @FindBy(how = How.XPATH, using = "//*[text()='Back']")
    WebElement backButton;

    @FindBy(how = How.XPATH, using = "//*[@class='boi-sca-progress-bar boi-no-padding']")
    WebElement progressBar;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Step 1 of 5')]")
    WebElement txtStep1to5;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Enter your 365 user ID')]")
    WebElement txtPageHeading;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'This is the unique number we gave you to access your online banking.')]")
    WebElement txtHeadTextMsgUserID;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Enter your user ID')]")
    WebElement lblLabelEnterUserID;

    @FindBy(how = How.XPATH, using = "//*[contains(@name,'USERID')]")
    WebElement edtUserID;

    @FindBy(how = How.XPATH, using = "//*[text()='Continue']")
    WebElement continueButton;

    public UserIdScaValidatePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void verifyUserIdPageIsVisible(){
        try {
            waitForPageLoaded();
            isElementDisplayedWithLog(progressBar);
            isElementDisplayedWithLog(txtStep1to5);
            isElementDisplayedWithLog(txtPageHeading);
            isElementDisplayedWithLog(lblLabelEnterUserID);
            logMessage("Device Provisioning - User ID launched successfully");
            appendScreenshotToCucumberReport();
            injectMessageToCucumberReport("Device Provisioning - User ID launched successfully");
        } catch (Exception e) {
            logError("UserId Page is NOT launched, error is , = "+e.getMessage());
            injectWarningMessageToCucumberReport("UserId Page is NOT Visible, error is , = "+e.getMessage());
            appendScreenshotToCucumberReport();
        }
    }

    public void enterUserId(String userId){
        try {
            sendKey(edtUserID, userId);
            logMessage("userId is entered as = "+userId);
            appendScreenshotToCucumberReport();
            clickJS(continueButton);
            logMessage("Continue button is clicked");
            injectMessageToCucumberReport("UserID is successfully entered");
        } catch (Exception e) {
            logError("UserId is not entered, error is , = "+e.getMessage());
            injectWarningMessageToCucumberReport("UserId is not entered, error is , = "+e.getMessage());
            appendScreenshotToCucumberReport();
        }
    }
}

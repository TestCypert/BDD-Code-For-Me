package com.boi.grp.pageobjects.login;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class Logout extends BasePageForAllPlatform {

    public Logout(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void fnLogout() {
        try {
            if (System.getProperty("PLATFORM").equalsIgnoreCase("WINDOWS")) {
                clickJS(getObject("home.lnkLogout"));
                clickJS(getObject("home.btnPopLogout"));
                String ActualText = getText(getObject("home.txtLogout"));
                Assert.assertEquals("You have been logged out.", ActualText);
                appendScreenshotToCucumberReport();
                driver.close();
                logMessage("Logout successful in Logout function.");
                injectMessageToCucumberReport("Logout successful");
                Allure.step("Logout successful ", Status.PASSED);
            } else {
                clickJS(getObject("home.lnkProfileApp"));
                clickJS(getObject("home.m.lnkLogout"));
                waitForElementToClickable(getObject("home.btnPopLogout"), 5);
                clickJS(getObject("home.btnPopLogout"));
                appendScreenshotToCucumberReport();
                String ActualText = getText(getObject("home.txtLogout"));
                Assert.assertEquals("You have been logged out.", ActualText);
                //driver.close();
                logMessage("Logout successful in Logout function.");
                injectMessageToCucumberReport("Logout successful");
                Allure.step("Logout successful ", Status.PASSED);
            }
        } catch (Exception e) {
            logError("Error Occured inside Logout " + e.getMessage());
            injectWarningMessageToCucumberReport("Failure in log out " + e.getMessage());
            Allure.step("Logout NOT Successful ", Status.FAILED);
            appendScreenshotToCucumberReport();
        }
    }


    public void fnCloseApplication(WebDriver driver) {
        driver.close();
    }

    public void logout(){
        try {
            if (devTypeToGetProperty.equalsIgnoreCase("w.")) {
                if(explicitWaitForElementTobeClickable(getObjectBy(devTypeToGetProperty+"home.btnLogout"))){
                    clickJS(getObjectBy(devTypeToGetProperty+"home.btnLogout"));
                }else{
                    Assert.assertTrue("Logout button not present",false);
                }
            }else{
                if(explicitWaitForElementTobeClickable(getObjectBy("home.profile"))){
                    clickJS(getObjectBy("home.profile"));
                }else{
                    Assert.assertTrue("Profile button not present",false);
                }
                waitForVisibilityOfElement(getObjectBy(devTypeToGetProperty+"home.btnLogout"));
                clickJS(getObjectBy(devTypeToGetProperty+"home.btnLogout"));
            }
            waitForVisibilityOfElement(getObjectBy("home.btnPopLogout"));
            appendScreenshotToCucumberReport();
            clickJS(getObjectBy("home.btnPopLogout"));

            String expectedLogoutMsg = "You have been logged out.";
            String actualLogoutMsg = getText(getObjectBy("home.LogoutMessage"));
            if (actualLogoutMsg.equals(expectedLogoutMsg)) {
                logMessage("successfully logged out of the application");
                insertMessageToHtmlReport("successfully logged out of the application");
                appendScreenshotToCucumberReport();
            } else {
                logError("Logging out is failed");
                insertErrorMessageToHtmlReport("Logging out is failed");
                appendScreenshotToCucumberReport();
            }
        } catch (Exception e) {
            logError("Error in logout method, Error = "+e.getMessage());
        }
    }
}

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
public class WelcomeScreenPage extends BasePageForDevices {

    @FindBy(how = How.XPATH, using = "//*[text()='Register this device']")
    WebElement btnRegisterThisDevice;

    public WelcomeScreenPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void welcomeScreen() {
        try {
            waitForJQueryLoad();
            changeNativeToWebview();
            logInfo("Change from Native to Web view is successful");
            click(btnRegisterThisDevice);
            appendScreenshotToCucumberReport();
            injectMessageToCucumberReport("clicked, register this device successfully");
            logInfo("Clicked register this device button successfully");
        } catch (Exception e) {
            logError("Register this device failed"+ ", Error Description=" + e.getMessage());
            appendScreenshotToCucumberReport();
            injectErrorToCucumberReport("Register this device button is not clicked, Error Description=" +e.getMessage());
        }
    }


}

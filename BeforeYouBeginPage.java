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
public class BeforeYouBeginPage extends BasePageForDevices {

    @FindBy(how = How.XPATH, using = "//*[text()='Before you begin']" )
    WebElement beforeYouBegin;

    @FindBy(how = How.XPATH, using = "//a[.='Continue']")
    WebElement continueButton;

    public BeforeYouBeginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void beforeYouBegin(){
        String strHeadTitle = getText(beforeYouBegin);
        try {
            waitForPageLoaded();
            if (strHeadTitle.equals("Before you begin")) {
                logMessage("BeforeYourBegin Page launched successfully");
                injectMessageToCucumberReport("BeforeYourBegin Page launched successfully");
                appendScreenshotToCucumberReport();
                clickJS(continueButton);
            } else {
                logError("BeforeYourBegin Page  not launched successfully ");
                appendScreenshotToCucumberReport();
            }
        } catch (Exception e) {
            logError("BeforeYourBegin Page  not launched successfully "+e.getMessage());
            injectWarningMessageToCucumberReport("BeforeYourBegin Page  not launched successfully check try block "+e.getMessage());
            appendScreenshotToCucumberReport();
        }
    }
}

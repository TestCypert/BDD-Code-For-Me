package com.boi.grp.pageobjects.Services;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by C112083 on 07/05/2021.
 */
public class LaunchService extends BasePageForAllPlatform {

    public LaunchService(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        if(!driver.toString().contains("appium")){
            driver.manage().window().maximize();
        }
    }

    public void invokeUXPApplication(){
        try {
            switch (System.getProperty("TYPE").trim().toUpperCase()) {
                case "BROWSER":
                    if(driver.toString().contains("appium")){
                    String strAppUrl;
                    switch (System.getProperty("PREFERRED_BROWSER").toUpperCase()){
                        case "SAFARI":
                            strAppUrl =System.getProperty("URL").trim();
                            driver.get(strAppUrl);
                            break;
                        case "CHROME":
                            AndroidDriver androidDriver = (AndroidDriver) driver;
                            androidDriver.context("CHROMIUM");
                            strAppUrl =System.getProperty("URL").trim();
                            driver.get(strAppUrl);
                            break;
                    }
                    }else {
                        String strAppUrl = System.getProperty("URL").trim();
                        driver.get(strAppUrl);
                        if(System.getProperty("PREFERRED_BROWSER").equalsIgnoreCase("IE")){
                            waitForSeconds(2);
                            clickJS(getObjectBy("advanceIE"));
                            logMessage("InvokeChannelApplication: Advance link is clicked ");
                            waitForPageLoaded();
                        }
                    }
                    System.setProperty("RUNTYPE","NONMOBILEAPP");
                    break;
                case "APPLICATION":
                    setRelevantWebViewTab();
                    waitForPageLoaded();
                    System.setProperty("RUNTYPE","MOBILEAPP");
                    break;
            }
        } catch (Exception e) {
            logError("Error in invoking method, Error = "+e.getMessage());
        }
    }
}

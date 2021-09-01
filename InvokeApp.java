package com.boi.grp.pageobjects.login;

import com.boi.grp.pageobjects.BasePageForDevices;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by C112083 on 05/11/2020.
 */
public class InvokeApp extends BasePageForDevices {

    @FindBy(how = How.XPATH, using = "//h4[@class='ecDIB  '][text()='Log in']")
    WebElement login;

    @FindBy(how = How.XPATH, using = "//*[text()='Register this device']")
    WebElement register;

    @FindAll(@FindBy(how = How.XPATH, using = "//span[@class='boi-sca-user-answer-part']"))
    List<WebElement> scaUser;

    @FindBy(how = How.XPATH, using = "//button[@title='Add user']")
    WebElement addUser;

    @FindBy(how = How.XPATH, using = "//div[@class='tc-float-left  ']/descendant::span[text()=' Log in with another ID']")
    WebElement loginWithAnotherUserId;

    public InvokeApp(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void invoke(String userID){
        setRelevantWebViewTab();
        handleLogin(userID);
    }

    public void handleLogin(String userId){
        waitForPageLoaded();
        //waitForJQueryLoad();
        try {
            if(isElementDisplayed(login)){
                boolean bflag = false;
                for (int i = 0; i < scaUser.size(); i++) {
                    String ID = scaUser.get(i).getText();
                    if (ID.equals(userId)) {
                        scrollToView(By.xpath("//span[text()='" +userId+ "']"));
                        scaUser.get(i).click();
                        bflag = true;
                        logMessage("login with sca registered user, = "+userId+"successfully");
                        break;
                    }
                }
                if(!bflag){
                    clickJS(addUser);
                    logMessage("login by adding new user, userId = "+userId);
                }
            }else if(isElementDisplayed(loginWithAnotherUserId)) {
                clickJS(loginWithAnotherUserId);
                logMessage("login with another user, userId = "+userId);
            }else{
            }
        } catch (Exception e) {
            logError("Error in handle login method, Message = "+e.getMessage());
            e.printStackTrace();
        }
    }
}

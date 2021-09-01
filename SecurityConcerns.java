package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;

import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by C100136 on 17/10/2019.
 */
public class SecurityConcerns  extends WebDriverHelper {

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public SecurityConcerns(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    public void securityConcernLink() throws Exception {
        String strSecurityConcern = "";
        if(!isMobile){
            strSecurityConcern = "sca.securityconcerns";
        } else {
            if(scriptHelper.commonData.iterationFlag == false){
                 strSecurityConcern = "sca.securityconcerns";
                scriptHelper.commonData.iterationFlag = true;
            } else {
                 strSecurityConcern = "sca.lnksecurityconcerns";
            }
        }
        if (isElementDisplayed(getObject(strSecurityConcern), 2)) {
            clickJS(getObject(strSecurityConcern), "Security Concerns Link");
            report.updateTestLog("securityConcernLink", "securityConcernLink is clicked successfully", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject(deviceType()+"securityConcerns.header"), 2)) {
                report.updateTestLog("securityConcernLink", "securityConcernPage is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("securityConcernLink", "securityConcernPage is not displayed", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("securityConcernLink", "securityConcernLink is not clicked successfully", Status_CRAFT.FAIL);
        }
    }

    
    public void lostAndStolenContent() throws Exception {
        //Lost and stolen
        if (isElementDisplayed(getObject("securityConcerns.lost&StolenLink"), 2)) {
            clickJS(getObject("securityConcerns.lost&StolenLink"), "Lost and Stolen");
            /*if (isElementDisplayed(getObject(deviceType()+ "securityConcerns.lost&StolenPageheader"), 2)) {
                report.updateTestLog("securityConcern - lost&StolenPageheader", "securityConcern - lost&StolenPageheader is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("securityConcern - lost&StolenPageheader", "securityConcern - lost&StolenPageheader is not displayed", Status_CRAFT.FAIL);
            }*/

        }
        List<WebElement> call = driver.findElements(By.xpath("//*[@class='boi-href-icon-wrapper']"));
        if (call.size() != 0) {
            for (int i = 0; i < call.size(); i++) {
                String text = call.get(i).getAttribute("href");
                if (text.equals("tel:1800 946 764") || text.equals("tel:0800 121 7790") || text.equals("tel:+353 56775 7007")) {
                    report.updateTestLog("verifyCallFunctionality", "Call button have correct details", Status_CRAFT.PASS);
                    //call.get(i).isEnabled();
                }
            }
        } else {
            report.updateTestLog("verifyCallFunctionality", "Call button not present", Status_CRAFT.FAIL);
        }

        if (deviceType().equals("mw.")) {
            clickJS(getObject("xpath~//input[@title='Back']"), "GO BACK to SecurityConcern page");
        } else {
            clickJS(getObject("launch.btnGoBack"), "GO BACK to SecurityConcern page");
        }

    }

    public void lostPhysicalSecurityKeyContent() throws Exception {
        //Lost Physical Security Key
        if (isElementDisplayed(getObject("securityConcerns.lostPhysicaSeckey"), 2)) {
            clickJS(getObject("ssecurityConcerns.lostPhysicaSeckey"), "Lost Physical Security Key");
            /*if (isElementDisplayed(getObject(deviceType()+ "securityConcerns.lost&StolenPageheader"), 2)) {
                report.updateTestLog("securityConcern - lost&StolenPageheader", "securityConcern - lost&StolenPageheader is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("securityConcern - lost&StolenPageheader", "securityConcern - lost&StolenPageheader is not displayed", Status_CRAFT.FAIL);
            }*/

        }
        if(deviceType().equals("mw.")) {
            List<WebElement> call = driver.findElements(By.xpath("//*[@class='boi-href-icon-wrapper']"));
            if (call.size() != 0) {
                for (int i = 0; i < call.size(); i++) {
                    String text = call.get(i).getAttribute("href");
                    if (text.equals("tel:1800 946 764") || text.equals("tel:0800 121 7790") || text.equals("tel:+353 56775 7007")) {
                        report.updateTestLog("verifyCallFunctionality", "Call button have correct details", Status_CRAFT.PASS);
                        //call.get(i).isEnabled();
                    }
                }
            } else {
                report.updateTestLog("verifyCallFunctionality", "Call button not present", Status_CRAFT.FAIL);
            }
        }
        else{
            List<WebElement> callDesktop=driver.findElements(By.xpath("//*[@class='boi-href-icon-wrapper']"));
        }

        if (deviceType().equals("mw.")) {
            clickJS(getObject("xpath~//input[@title='Back']"), "GO BACK to SecurityConcern page");
        } else {
            clickJS(getObject("launch.btnGoBack"), "GO BACK to SecurityConcern page");
        }

    }


    public void moreInfoContent() throws Exception {
        //Lost and stolen
        if (isElementDisplayed(getObject("securityConcerns.MoreInfo"), 2)) {
            clickJS(getObject("securityConcerns.MoreInfo"), "More Information");
            /*if (isElementDisplayed(getObject(deviceType()+ "securityConcerns.lost&StolenPageheader"), 2)) {
                report.updateTestLog("securityConcern - lost&StolenPageheader", "securityConcern - lost&StolenPageheader is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("securityConcern - lost&StolenPageheader", "securityConcern - lost&StolenPageheader is not displayed", Status_CRAFT.FAIL);
            }*/

        }
        List<WebElement> call = driver.findElements(By.xpath("//*[@class='boi_input']"));
        if (call.size() != 0) {
            for (int i = 0; i < call.size(); i++) {
                String text = call.get(i).getText();
                if (text.equals("tel:1800 946 764") || text.equals("tel:0800 121 7790") || text.equals("tel:+353 56775 7007")) {
                    report.updateTestLog("verifyCallFunctionality", "Call button have correct details", Status_CRAFT.PASS);
                    //call.get(i).isEnabled();
                }
            }
        } else {
            report.updateTestLog("verifyCallFunctionality", "Call button not present", Status_CRAFT.FAIL);
        }

        if (deviceType().equals("mw.")) {
            clickJS(getObject("xpath~//input[@title='Back']"), "GO BACK to SecurityConcern page");
        } else {
            clickJS(getObject("launch.btnGoBack"), "GO BACK to SecurityConcern page");
        }

    }
    public void LostPSKContent() throws Exception {
        //Lost and stolen
        if (isElementDisplayed(getObject("securityConcerns.LostPSKLink"), 2)) {
            clickJS(getObject("securityConcerns.LostPSKLink"), "Lost PSK");
            if (isElementDisplayed(getObject(deviceType()+ "securityConcerns.LOSTPSKPageheader"), 2)) {
                report.updateTestLog("securityConcern - LOSTPSKPageheader", "securityConcern - LOSTPSKPageheader is displayed successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("securityConcern - LOSTPSKPageheader", "securityConcern - LOSTPSKPageheader is not displayed", Status_CRAFT.FAIL);
            }

        }
        List<WebElement> call = driver.findElements(By.xpath("//*[@class='boi-href-icon-wrapper']"));
        if (call.size() != 0) {
            for (int i = 0; i < call.size(); i++) {
                String text = call.get(i).getAttribute("href");
                if (text.equals("tel:1800 946 764") || text.equals("tel:0800 121 7790") || text.equals("tel:+353 56775 7007")) {
                    report.updateTestLog("verifyCallFunctionality", "Call button have correct details", Status_CRAFT.PASS);
                    //call.get(i).isEnabled();
                }
            }
        } else {
            report.updateTestLog("verifyCallFunctionality", "Call button not present", Status_CRAFT.FAIL);
        }

        if (deviceType().equals("mw.")) {
            clickJS(getObject("xpath~//input[@title='Back']"), "GO BACK to SecurityConcern page");
        } else {
            clickJS(getObject("launch.btnGoBack"), "GO BACK to SecurityConcern page");
        }

    }

    /**
     * Function : To launch App Notifications tab
     * Created by : C966003
     * Update on    Updated by     Reason
     * 02/12/2019   C966003        Newly created
     */
    public void launchAppNotification() {
        isElementDisplayedWithLog(getObject("launch.btnAppNotification"),
                "App notification","",3);
        try {
            clickJS(getObject("launch.btnAppNotification"),"App notification");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitForPageLoaded();
    }

    /**
     * Function : To validate App Notifications page contents
     * Created by : C966003
     * Update on    Updated by     Reason
     * 03/12/2019   C966003        Newly created
     */
    public void appNotificationPageContents() {
        HomePage homePage = new HomePage(scriptHelper);
        try {
            homePage.verifyElementDetails("launch.txtAppNotificationPage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goBack(){
        try {
            if (!deviceType().equals("w.")) {
                clickJS(getObject(deviceType()+"launch.btnGoBack"), "GO BACK to SecurityConcern page");
            } else {
                clickJS(getObject("launch.btnGoBack"), "GO BACK to SecurityConcern page");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	/**
     * Function : To launch Forgot PIN tab
     * Created by : C100193
     * Update on    Updated by     Reason
     * 18/09/2020   C100193        Newly created
     */
    public void launchForgotPIN() {
        isElementDisplayedWithLog(getObject("securityConcerns.forgotPINLink"),
                "Forgot PIN","",3);
        try {
            clickJS(getObject("securityConcerns.forgotPINLink"),"Forgot PIN");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitForPageLoaded();
    }

	/**
     * Function : To validate Forgot PIN page contents
     * Created by : C100193
     * Update on    Updated by     Reason
     * 18/09/2020   C100193        Newly created
     */
    public void forgotPINPageContent() throws Exception {
		if(isElementDisplayed(getObject(deviceType()+"securityConcerns.forgotPINPageHeader"),5)){
			report.updateTestLog("forgotPINPageContent", "forgotPINPageHeader is displayed successfully", Status_CRAFT.PASS);
			HomePage homePage = new HomePage(scriptHelper);
			try {
				homePage.verifyElementDetails(deviceType()+"securityConcerns.forgotPINPageContent");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			report.updateTestLog("forgotPINPageContent", "forgotPINPageHeader is not displayed", Status_CRAFT.FAIL);
		}
    }
	
	/**
     * Function : To launch Fraud concerns tab
     * Created by : C100193
     * Update on    Updated by     Reason
     * 18/09/2020   C100193        Newly created
     */
    public void launchFraudConcerns() {
        isElementDisplayedWithLog(getObject("securityConcerns.fraudConcernsLink"),
                "Fraud concerns","",3);
        try {
            clickJS(getObject("securityConcerns.fraudConcernsLink"),"Fraud concerns");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitForPageLoaded();
    }

	/**
     * Function : To validate Fraud concerns page contents
     * Created by : C100193
     * Update on    Updated by     Reason
     * 18/09/2020   C100193        Newly created
     */
    public void fraudConcernsPageContent() throws Exception {
		if(isElementDisplayed(getObject(deviceType()+"securityConcerns.fraudConcernsPageHeader"),5)){
			report.updateTestLog("fraudConcernsPageContent", "fraudConcernsPageHeader is displayed successfully", Status_CRAFT.PASS);
			HomePage homePage = new HomePage(scriptHelper);
			try {
				homePage.verifyElementDetails(deviceType()+"securityConcerns.fraudConcernsPageContent");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			report.updateTestLog("fraudConcernsPageContent", "fraudConcernsPageHeader is not displayed", Status_CRAFT.FAIL);
		}
    }
}

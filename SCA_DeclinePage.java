package businesscomponents;
import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.JavascriptExecutor;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by C966055 on 03/07/2019.
 */
public class SCA_DeclinePage extends WebDriverHelper {
    private static Properties mobileProperties;

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */


    public SCA_DeclinePage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    public void SCADeclinePageOperation() throws Exception {
        String header = getText(getObject("sca.declineHeader"),3);
        String paragraph = getText(getObject("sca.declineText"),3);
        if(isElementDisplayed(getObject("sca.declineHeader"),5)){
            report.updateTestLog("SCADeclinePage","Heading " +header+ "is displayed on decline page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("SCADeclinePage","Heading " +header+ "is not displayed on decline page", Status_CRAFT.FAIL);
        }
        if(isElementDisplayed(getObject("sca.declineText"),5)){
            report.updateTestLog("SCADeclinePage","Paragraph text " +paragraph+ " is displayed on decline page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("SCADeclinePage","Paragraph text " +paragraph+ " is not displayed on decline page", Status_CRAFT.FAIL);
        }

        isElementDisplayedWithLog(getObject("sca.btnTryAgain"),"Try Again button","SCA Decline Page",5);
        isElementDisplayedWithLog(getObject("sca.btnReviewDetails"),"Review Details button","SCA Decline Page",5);
        String strOperation = dataTable.getData("General_Data", "PutOnHold");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperation.toUpperCase()) {
                case "TRY AGAIN":
                    js.executeScript("arguments[0].scrollIntoView();",driver.findElement(getObject("sca.btnTryAgain")));
                    Thread.sleep(2000);
                    clickJS(getObject("sca.btnTryAgain"), "Try Again");
                    break;
                case "REVIEW DETAILS":
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject("sca.btnReviewDetails")));
                    Thread.sleep(2000);
                    click(getObject("sca.btnReviewDetails"), "Review details");
                    break;
                default:
                    report.updateTestLog("SCADeclinePageOperation", "Please provide the valid operation on SCA Decline Page: Try Again or Continue", Status_CRAFT.FAIL);
            }
    }

    public void SCADeclinePage() throws Exception {
        String header = getText(getObject("sca.declineHeader"), 3);
        if (isElementDisplayed(getObject("sca.declineHeader"), 5)) {
            report.updateTestLog("SCADeclinePage", "Heading " + header + "is displayed on decline page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SCADeclinePage", "Heading " + header + "is not displayed on decline page", Status_CRAFT.FAIL);
        }

        String paragraph = getText(getObject("sca.declineText"), 3);
        if (isElementDisplayed(getObject("sca.declineText"), 5)) {
            report.updateTestLog("SCADeclinePage", "Paragraph text " + paragraph + " is displayed on decline page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SCADeclinePage", "Paragraph text " + paragraph + " is not displayed on decline page", Status_CRAFT.FAIL);
        }
    }

    public void noActionOnPush() throws Exception{
        driver.context("NATIVE_APP");
        if (isElementDisplayed(getObject("id~com.bankofireland.tcmb:id/closeIcon"), 5)) {
            MobileElement el2 = (MobileElement) driver.findElementById("com.bankofireland.tcmb:id/closeIcon");
            //Thread.sleep(120000);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);
            report.updateTestLog("PushNotification_NoAction", "Push Notification came but No Action taken", Status_CRAFT.PASS);
            driver.context("WEBVIEW_com.bankofireland.tcmb");
        }
        else{
            report.updateTestLog("PushNotification_NoAction", "Push Notification came but Action taken", Status_CRAFT.FAIL);
        }
    }

}

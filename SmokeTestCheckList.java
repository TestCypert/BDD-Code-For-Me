package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.List;

/**
 * Created by C966119 on 26/02/2019.
 */
public class SmokeTestCheckList extends WebDriverHelper {

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public SmokeTestCheckList(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /*General Function:
  Scroll and Click on particular  element using JS
 */
    public void ScrollAndClickJS(String linkToClick) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(2000);
            report.updateTestLog("ScrollAndClickJS", "Element To Click", Status_CRAFT.SCREENSHOT);
            JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
            executor.executeScript("arguments[0].click();", driver.findElement(getObject((linkToClick))));
            Thread.sleep(3000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    /*General Function:
      Scroll to view particular  element using JS
     */
    public void ScrollToVisibleJS(String linkToVisible) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToVisible))));
            Thread.sleep(2000);
            //report.updateTestLog("ScrollAndClickJS", "Element To View", Status_CRAFT.SCREENSHOT);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reusable :
     * Navigate to any account from the home page
     */
    public void verifyNavigateToAccountPageAnyAccount() throws Exception {
        String strAccountToCheck = dataTable.getData("General_Data", "Account_Type");
        String strAccountIBAN = dataTable.getData("General_Data", "IBAN_Number");
        String strParentObject = deviceType() + "home.parentaccount_new";

        //List of all the Accounts
        List<WebElement> oUIRows = driver.findElements(getObject(strParentObject));
        report.updateTestLog("verifyNavigateToAccountPageAnyAccount", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
        ScrollToVisibleJS(strParentObject);

        for (int j = 0; j < oUIRows.size(); j++) {
            String strRowText = oUIRows.get(j).getText().toUpperCase();
            if ((strRowText.contains(strAccountToCheck.toUpperCase())) && (strRowText.contains(strAccountIBAN.toUpperCase()))) {
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", oUIRows.get(j));
                Thread.sleep(3000);
                JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                report.updateTestLog("verifyNavigateToAccountPageAnyAccount", ":: The Content Screenshot :: ", Status_CRAFT.SCREENSHOT);
                executor.executeScript("arguments[0].click();", oUIRows.get(j));
                Thread.sleep(8500);
                waitForElementPresent(getObject("AccStatement.lblStatementTab"), 20);
                report.updateTestLog("verifyNavigateToAccountPageAnyAccount", "The Account :: " + strAccountToCheck + ":: is displyed at position ::" + (j + 1) + ":: and clicked successfully.", Status_CRAFT.DONE);
                break;
            }
        }

        report.updateTestLog("verifyNavigateToAccountPageAnyAccount", "Blue Card Page", Status_CRAFT.SCREENSHOT);
    }

}

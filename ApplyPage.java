package businesscomponents;

/**
 * Function/Epic:
 * Class:
 * Developed on: 03/06/2019.
 * Developed by: C103887
 * Update on     Updated by     Reason
 * 03/06/2019        C103887
 */

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.WebElement;

import java.util.List;


/**
 * Class for storing component groups functionality
 *
 * @author Cognizant
 */
public class ApplyPage extends WebDriverHelper {
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public ApplyPage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function to launch Apply page
     */
    public void launchApply() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "apply.homeApplylink"), 3)) {
            click(getObject((deviceType() + "apply.homeApplylink")));
            report.updateTestLog("launchApply", "Apply Link is displayed", Status_CRAFT.PASS);
            Thread.sleep(3000);
        }

    }

    /**
     * Function to verify all the sections are present on the Apply screen
     */

    public void verifyApplypagesections() throws Exception {

        if ((isElementDisplayed(getObject("apply.Applyonlineheader"), 10))) {
            report.updateTestLog("verifyApplypagesections", "Apply Online title correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyApplypagesections", "Apply Online title not present on screen", Status_CRAFT.FAIL);
        }
        if ((isElementDisplayed(getObject("apply.Applytoday"), 10))) {
            report.updateTestLog("verifyApplypagesections", "Apply Today header correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyApplypagesections", "Apply Today header not present on screen", Status_CRAFT.FAIL);
        }
        if ((isElementDisplayed(getObject("apply.Currentaccountheader"), 10))) {
            report.updateTestLog("verifyApplypagesections", "Current Account holder section correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyApplypagesections", "Current Account holder section not present on screen", Status_CRAFT.FAIL);
        }
        if ((isElementDisplayed(getObject("apply.Insuranceheader"), 10))) {
            report.updateTestLog("verifyApplypagesections", "Insurance section correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyApplypagesections", "Insurance section not present on screen", Status_CRAFT.FAIL);
        }
        if ((isElementDisplayed(getObject("apply.Savingsheader"), 10))) {
            report.updateTestLog("verifyApplypagesections", "Savings section correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyApplypagesections", "Savings section not present on screen", Status_CRAFT.FAIL);
        }
        if ((isElementDisplayed(getObject("apply.CreditCardsheader"), 10))) {
            report.updateTestLog("verifyApplypagesections", "Credit Cards section correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyApplypagesections", "Credit Cards section not present on screen", Status_CRAFT.FAIL);
        }
        if ((isElementDisplayed(getObject("apply.Loansheader"), 10))) {
            report.updateTestLog("verifyApplypagesections", "Loans section correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyApplypagesections", "Loans section not present on screen", Status_CRAFT.FAIL);
        }
        if ((isElementDisplayed(getObject("apply.UXPlinksheader"), 10))) {
            report.updateTestLog("verifyApplypagesections", "UXP Links section correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyApplypagesections", "UXP Links section not present on screen", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to verify all the diffrent apply options and links present on the Apply screen
     */

    public void verifyApplyoptionsLinks() throws Exception {

        List<WebElement> allLinks = findElements(getObject("apply.allclikableoptions"));
        if (allLinks.size() > 1) {
            int intIndex = 1;
            for (WebElement ele : allLinks) {
                String linkname = ele.getText();
                report.updateTestLog("verifyApplyoptionsLinks", linkname + "present on screen", Status_CRAFT.PASS);
            }


        }
    }

    /**
     * Function to select from apply online items present on the Apply screen
     */

    public void selectApplyoptions() throws Exception{

        String strApplyOption = dataTable.getData("General_Data", "Nickname");
        List<WebElement> allLinks = findElements(getObject("apply.applyonlineoptions"));
        if (allLinks.size() > 1) {
            int intIndex = 1;
            for (WebElement ele : allLinks) {
                String linkname = ele.getText();
                if (linkname.equalsIgnoreCase(strApplyOption)) {
                    ele.click();
                    intIndex = intIndex;
                    report.updateTestLog("selectAmount", strApplyOption + " is clicked  ", Status_CRAFT.PASS);
                    break;
                }
                intIndex = intIndex++;

            }
        }

    }

    /**
     * Function to verify timeout popup not displayed
     */

    public void verifyTimeoutPopup() throws Exception{

        Thread.sleep(300000);

        if ((!isElementclickable(getObject("apply.timeout"), 10))) {
            report.updateTestLog("verifyTimeoutPopup", "TimeOut popup not  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyTimeoutPopup", "TimeOut popup displayed on screen", Status_CRAFT.FAIL);
        }



    }

    public void selectUXPlinks() throws Exception {

        String strApplyOption = dataTable.getData("General_Data", "Nickname");
        List<WebElement> allLinks = findElements(getObject("apply.UXPlinks"));
        if (allLinks.size() > 1) {
            int intIndex = 1;
            for (WebElement ele : allLinks) {
                String linkname = ele.getText();
                if (linkname.equalsIgnoreCase(strApplyOption)) {
                    ele.click();
                    intIndex = intIndex;
                    report.updateTestLog("selectUXPlinks", strApplyOption + " is clicked  ", Status_CRAFT.PASS);
                    break;
                }
                intIndex = intIndex++;

            }
        }
    }

    public void verifyGoBack1() throws Exception {

        Thread.sleep(2000);
        if (isElementDisplayed(getObject("home.Goback"), 20)) {

            report.updateTestLog("gobacknavigation", "Go Back link present on Screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("gobacknavigation", "Go Back link missing on Screen", Status_CRAFT.FAIL);
        }

        clickJS(getObject("home.Goback"), "Go Back link");
        Thread.sleep(2000);

        if ((isElementDisplayed(getObject("apply.Applyonlineheader"), 10))) {
            report.updateTestLog("verifyGoBack", "Navigation is correct to apply page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyGoBack", "Navigation is incorrect", Status_CRAFT.FAIL);
        }


    }

}




package businesscomponents;

/**
 * Function/Epic: More Options page functions across all Squads
 * Class: More Options
 * Developed on: 15/10/2018
 * Developed by: C966003
 * Update on     Updated by     Reason
 * 18/04/2019     c101992       Done code clean up activity
 */


import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import componentgroups.FunctionalComponents;

import java.util.ArrayList;

/**
 * Function: Class for storing component groups functionality
 * Update on     Updated by     Reason
 * 18/04/2019     c101992        Done code clean up activity
 * @author Cognizant
 */

public class MoreOptionsPage extends WebDriverHelper {
    /**
     * Function: Constructor to initialize the component group library
     * Update on     Updated by     Reason
     * 18/04/2019     c101992        Done code clean up activity
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     * @author Cognizant
     */

    public MoreOptionsPage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /**
     * Function: Validate options text displayed by clicking 'More' button
     * Update on     Updated by     Reason
     * 18/04/2019     c101992        Done code clean up activity
     */

    public void validateOptsTextDisplyd() throws Exception {

        HomePage homePage = new HomePage(scriptHelper);
        if (deviceType().equals("mw.")) {
            click(getObject("home.lnkPreLgnMore"), "More button");
            homePage.verifyElementDetails(deviceType() + "more.lblPageSection");
        } else {
            String strTabName = dataTable.getData("General_Data", "TabName");
            if (!isElementDisplayed(getObject("xpath~//span[text()='" + strTabName + "']"), 5)) {
                report.updateTestLog("verifyMenuTabNotPresent", "Menu Tab '" + strTabName + " ' is not displayed ", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyMenuTabNotPresent", "Menu Tab '" + strTabName + " ' is displayed ", Status_CRAFT.FAIL);
            }

        }
    }

    /**
     * Function: To Verify BOI link is clickable in Regulatory statement
     * Update on     Updated by     Reason
     * 18/04/2019     c101992       Done code clean up activity
     */

    public void verifyRegStatBOIlinkClickable() throws Exception {
        if (isElementDisplayed(getObject("mw.more.lnkBOIlink"), 3)) {
            report.updateTestLog("verifyRegStatBOIlink", "BOI link is displayed ", Status_CRAFT.PASS);
            isElementDisplayedWithLog(getObject(deviceType() + "mw.more.lnkBOIlink"), "BOI Link", "Clickable", 2);
        } else {
            report.updateTestLog("verifyRegStatBOIlink", "BOI link is not displayed ", Status_CRAFT.FAIL);
        }
    }


    public void verifyBackArrowPrelogin() throws Exception {
        waitForPageLoaded();
        waitForElementToClickable(getObject(deviceType()+"more.btnGoBackArrow") , 14);
        clickJS(getObject(deviceType()+"more.btnGoBackArrow"), "Back Arrow");
        waitForPageLoaded();
    }

    public void verifyRegulatory() throws Exception {
        String strTabName = dataTable.getData("General_Data", "TabName");
        String expectedlink = dataTable.getData("General_Data", "VerifyDetails");
        String strTabName1 = dataTable.getData("General_Data", "ServiceDeskOption");
        if (!deviceType().equalsIgnoreCase("MobileWeb")) {
            if (isElementDisplayed(getObject("xpath~//span[contains(text(),'" + strTabName + "')]"), 5)) {
                clickJS(getObject("xpath~//span[contains(text(),'" + strTabName + "')]"), "Link");
                if (!isMobile) {
                    HomePage homePage = new HomePage(scriptHelper);
                    homePage.verifyNewlyOpenedTab(expectedlink);
                    report.updateTestLog("verifyRegulatoryInfo", "Regulatory info link '" + expectedlink + "' is appearing", Status_CRAFT.PASS);
                }else{
                    String strText = getText(getObject("xpath~//*[@class='boi_input']"));
                    Boolean blnCheck = false ;
                    blnCheck = ( isElementDisplayed( getObject("more.lblregulatorytext1"), 1) &&
                            isElementDisplayed( getObject("more.lblregulatorytext2"), 1) &&
                            isElementDisplayed( getObject("more.lblregulatorytext3"), 1) &&
                            isElementDisplayed( getObject("more.lblregulatorytext4"), 1) &&
                            isElementDisplayed( getObject("more.lblregulatorytext5"), 1) &&
                            isElementDisplayed( getObject("more.lblregulatorytext6"), 1) &&
                            isElementDisplayed( getObject("more.lblregulatorytext7"), 1) );
                    if (blnCheck) {
                        report.updateTestLog("verifyRegulatoryInfo", "Regulatory info text along with headers :: '" + strText + "' :: is appearing correctly ", Status_CRAFT.PASS);
                    }else   {
                        report.updateTestLog("verifyRegulatoryInfo", "Regulatory info text '" + strText + "' is appearing", Status_CRAFT.FAIL);
                    }
                    HomePage homePage = new HomePage(scriptHelper);
                    clickJS(getObject("more.lnkfindyourbrnch") , "Find Your Nearest Branch");
                    waitForPageLoaded();
                    homePage.verifyNewlyOpenedTab("https://www.bankofireland.com/branch-locator/");
                    waitForPageLoaded();waitForJQueryLoad();
                    clickJS(getObject(deviceType()+ "more.btnGoBack") , "Go Back");
                    waitForPageLoaded();
                }
            } else {
                report.updateTestLog("verifyRegulatoryInfo", "Regulatory info link '" + expectedlink + "' is not appearing", Status_CRAFT.FAIL);
            }
        } else {
            scrollToView(getObject("home.lnkPreLgnMore"));
            clickJS(getObject("home.lnkPreLgnMore"), "Clicked on Regulatory Information Link");
            if (isElementDisplayed(getObject("xpath~//span[contains(text(),'" + strTabName1 + "')]"), 5)) {
                clickJS(getObject("xpath~//span[contains(text(),'" + strTabName1 + "')]"), "Link");
                report.updateTestLog("verifyRegulatoryInfo", "Regulatory info link '" + expectedlink + "' is appearing", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyRegulatoryInfo", "Regulatory info link '" + expectedlink + "' is not appearing", Status_CRAFT.FAIL);
            }
        }
    }

    public void verifyRegulatoryOptionMobile() throws Exception {
        String strTabName = dataTable.getData("General_Data", "TabName");
        String expectedlink = dataTable.getData("General_Data", "VerifyDetails");
        String strTabName1 = dataTable.getData("General_Data", "ServiceDeskOption");
        if (isElementDisplayed(getObject("xpath~//span[contains(text(),'" + strTabName + "')]"), 5)) {
            clickJS(getObject("xpath~//span[contains(text(),'" + strTabName + "')]"), "Link");
            String strText = getText(getObject("xpath~//*[@class='boi_input']"));
            Boolean blnCheck = false;
            blnCheck = (isElementDisplayed(getObject("more.lblregulatorytext1"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatorytext2"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatorytext3"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatorytext4"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatorytext5"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatorytext6"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatorytext7"), 1));
            if (blnCheck) {
                report.updateTestLog("verifyRegulatoryInfo", "Regulatory info text along with headers :: '" + strText + "' :: is appearing correctly ", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyRegulatoryInfo", "Regulatory info text '" + strText + "' is appearing", Status_CRAFT.FAIL);
            }
        }
    }

    public void verifyRegulatoryOptionDesktop() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        String strTabName = dataTable.getData("General_Data", "TabName");
        String expectedlink = dataTable.getData("General_Data", "VerifyDetails");
        String strTabName1 = dataTable.getData("General_Data", "VerifyContent");
        if (isElementDisplayed(getObject("xpath~//span[contains(text(),'" + strTabName + "')]"), 5)) {
            clickJS(getObject("xpath~//span[contains(text(),'" + strTabName + "')]"), "Link");

            homePage.verifyNewlyOpenedTab(expectedlink);
            ArrayList<String> arrTabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(arrTabs.get(arrTabs.size() - 1));
            report.updateTestLog("verifyRegulatoryInfo", "Regulatory info link '" + expectedlink + "' is appearing", Status_CRAFT.PASS);

            Boolean blnCheck = false;
            blnCheck = (isElementDisplayed(getObject("more.lblregulatoryDesktext1"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatoryDesktext2"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatoryDesktext3"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatoryDesktext4"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatoryDesktext5"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatoryDesktext6"), 1) &&
                    isElementDisplayed(getObject("more.lblregulatoryDesktext7"), 1));
            if (blnCheck) {
                report.updateTestLog("verifyRegulatoryInfo", "Regulatory info text '" + dataTable.getData("General_Data", "VerifyContent") + "' is appearing correctly.", Status_CRAFT.DONE);
            } else {
                report.updateTestLog("verifyRegulatoryInfo", "Regulatory info text '" + dataTable.getData("General_Data", "VerifyContent") + "' is NOT appearing correctly.", Status_CRAFT.FAIL);
            }

            clickJS(getObject("more.lnkfindyourbrnchDesk"), "Find Your Nearest Branch");
            waitForPageLoaded();
            homePage.verifyNewlyOpenedTab("https://www.bankofireland.com/branch-locator/");
            waitForPageLoaded();
        }
    }

    public void verifyRegulatoryOption() throws Exception {
        FunctionalComponents funComp = new FunctionalComponents(scriptHelper);
        HomePage home = new HomePage(scriptHelper);
        if ( deviceType()== "mw." |  deviceType()== "tw." | isMobile ){
            SCA_MobileAPP scaMobileAPP = new SCA_MobileAPP(scriptHelper);
            waitForElementToClickable(getObject("home.lnkPreLgnMore"), 7);
            funComp.verifyClickPreLgnMore();
            verifyRegulatoryOptionMobile();
            ScrollAndClickJS(deviceType()+ "more.btnGoBack");
            waitForPageLoaded();
            funComp.verifybackArrowPrelogin();
            waitForPageLoaded();
            if(isMobile) {
                scaMobileAPP.functionToHandleLogin();
            }
            funComp.login();
            if (!isMobile){
                if (isElementDisplayed(getObject("launch.approvalNotificationPage"), 3)) {
                    home.verifyPushIsNotAccepted();
                }
            }
            waitForPageLoaded();
            home.verifyHomePage();
            waitForElementToClickable(getObject("home.lnkPreLgnMore"), 7);
            funComp.verifyClickPreLgnMore();
            verifyRegulatoryOptionMobile();
            ScrollAndClickJS(deviceType()+ "more.btnGoBack");
            waitForPageLoaded();
            waitForElementToClickable(getObject(deviceType() + "home.imgProfileIcon"), 7);
            clickJS(getObject(deviceType() + "home.imgProfileIcon"), "Profile icon clicked");
            waitForElementToClickable(getObject(deviceType()+"home.btnLogout"),8);
            clickJS(getObject(deviceType()+"home.btnLogout"), "logout clicked");
            click(getObject(deviceType()+"home.btnYesMeLogOut"),"Yes, log me out");
            home.clickmore();
            verifyRegulatoryOptionMobile();
            ScrollAndClickJS(deviceType()+ "more.btnGoBack");
            waitForPageLoaded();
            funComp.verifybackArrowPrelogin();
            waitForPageLoaded();
            waitForElementToClickable(getObject("home.logoutpagelogin"), 14);
            clickJS(getObject("home.logoutpagelogin"), "Log in button on log out page clicked");
        }else{
            verifyRegulatoryOptionDesktop();
            ArrayList<String> arrTabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(arrTabs.get(0));
            funComp.login();
            if (isElementDisplayed(getObject("xpath~//*[text()='Device required']"), 3)) {
                home.verifyPushIsNotAccepted();
            }
            waitForPageLoaded();
            home.verifyHomePage();
            verifyRegulatoryOptionDesktop();
            ArrayList<String> arrTabsNew = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(arrTabsNew.get(0));
            waitForPageLoaded();
            home.verifylogout(deviceType() + "home.btnLogout");
            verifyRegulatoryOptionDesktop();
        }
    }
}




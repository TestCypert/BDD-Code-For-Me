     package businesscomponents;

/**
 * Function/Epic:
 * Class:
 * Developed on: 13/05/2019.
 * Developed by: C103887
 * Update on     Updated by     Reason
 * 13/05/2019        C103887
 */

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing component groups functionality
 *
 * @author Cognizant
 */

public class ContactUsPage extends WebDriverHelper {
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public ContactUsPage(ScriptHelper scriptHelper) {
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
    /**
     * Function to launch contact us page
     */
    public void launchContactUs() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.btnServiceDesk"), 3)) {
            clickJS(getObject(deviceType() + "home.btnServiceDesk"),"Service desk option clicked");
            report.updateTestLog("launchContactUs", "Services is displayed", Status_CRAFT.SCREENSHOT);
            report.updateTestLog("launchContactUs", "Services is displayed", Status_CRAFT.PASS);
            //report.updateTestLog(" 'SERVICES' ", "  'SERVICES' link is displayed successfully", Status_CRAFT.PASS);
            clickJS(getObject(deviceType()+ "services.contactus"), "Contact Us");
            report.updateTestLog("launchContactUs", "ContactUs page success", Status_CRAFT.SCREENSHOT);
            Thread.sleep(5000);
        }

    }

    public void verifyContactNumberSection() throws Exception {
        String strHeader = "Contact numbers";
        String strEmergency = "Emergency numbers available 24/7";
        String strROI = "Republic of Ireland";
        String strNIGB = "Northern Ireland and Great Britain";

        if ((isElementDisplayed(getObject("contactus.contactnumberheader"), 10))
                && ((getText(getObject("contactus.contactnumberheader")).equalsIgnoreCase(strHeader)))) {
            report.updateTestLog("verifyContactNumberSection", "Contact numbers header correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyContactNumberSection", "Contact numbers header not present on screen", Status_CRAFT.FAIL);
        }

//        if ((isElementDisplayed(getObject("contactus.emergengyheader"), 10))
//                && ((getText(getObject("contactus.emergengyheader")).equalsIgnoreCase(strEmergency)))) {
//            report.updateTestLog("verifyContactNumberSection", "Emergency numbers header correctly  displayed on screen", Status_CRAFT.PASS);
//        } else {
//            report.updateTestLog("verifyContactNumberSection", "Emergency numbers header not present on screen", Status_CRAFT.FAIL);
//        }

        if ((isElementDisplayed(getObject("contactus.emergencyROI"), 10))
                && ((getText(getObject("contactus.emergencyROI")).equalsIgnoreCase(strROI)))) {
            report.updateTestLog("verifyContactNumberSection", "ROI label correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyContactNumberSection", "ROI label not present on screen", Status_CRAFT.FAIL);
        }

        if ((isElementDisplayed(getObject("contactus.emergencyNI/GB"), 10))
                && ((getText(getObject("contactus.emergencyNI/GB")).equalsIgnoreCase(strNIGB)))) {
            report.updateTestLog("verifyContactNumberSection", "'Northern Ireland and Great Britain' label correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyContactNumberSection", "'Northern Ireland and Great Britain' label not present on screen", Status_CRAFT.FAIL);
        }

    }

    public void verify365BankingTab() throws Exception {
        String strHeader = "365 phone banking";
        String stropeninghrs = "365 phone banking opening hours";
        String strROI = "Republic of Ireland";
        String strNIGB = "Northern Ireland and Great Britain";
        String strhelp = "9am to 5pm, Monday to Friday. Closed weekends.";
        String strhelp2 = "9am to 5pm, Monday to Friday and 9am to 2pm Saturdays. Closed Sundays.";


        if ((isElementDisplayed(getObject("contactus.365header"), 10))
                && ((getText(getObject("contactus.365header")).equalsIgnoreCase(strHeader)))) {
            scrollToView(getObject("contactus.365header"));
            report.updateTestLog("verify365BankingTab", "365 phone banking  header correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verify365BankingTab", "365 phone banking header not present on screen", Status_CRAFT.FAIL);
        }

        if ((isElementDisplayed(getObject("contactus.365openingHrs"), 10))
                && ((getText(getObject("contactus.365openingHrs")).equalsIgnoreCase(stropeninghrs)))) {
            report.updateTestLog("verify365BankingTab", "365 phone banking opening hours  label correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verify365BankingTab", "365 phone banking opening hours  label not present on screen", Status_CRAFT.FAIL);
        }

        if ((isElementDisplayed(getObject("contactus.365ROI"), 10))
                && ((getText(getObject("contactus.365ROI")).equalsIgnoreCase(strROI)))) {
            scrollToView(getObject("contactus.365ROI"));
            report.updateTestLog("verify365BankingTab", "ROI label correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verify365BankingTab", "ROI label not present on screen", Status_CRAFT.FAIL);
        }

        if ((isElementDisplayed(getObject("contactus.365NI/GB"), 10))
                && ((getText(getObject("contactus.365NI/GB")).equalsIgnoreCase(strNIGB)))) {
            report.updateTestLog("verify365BankingTab", "NI/GB label correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verify365BankingTab", "NI/GB label not present on screen", Status_CRAFT.FAIL);
        }

        if ((isElementDisplayed(getObject("contactus.ROIhelp"), 10))
                && ((getText(getObject("contactus.ROIhelp")).equalsIgnoreCase(strhelp)))) {
            report.updateTestLog("verify365BankingTab", "ROI opening timings correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verify365BankingTab", "ROI help text not present on screen", Status_CRAFT.FAIL);
        }
        if ((isElementDisplayed(getObject("contactus.ROIhelp2"), 10))
                && ((getText(getObject("contactus.ROIhelp2")).equalsIgnoreCase(strhelp2)))) {
            report.updateTestLog("verify365BankingTab", "NI/GB opening timings correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verify365BankingTab", "NI/GB help text not present on screen", Status_CRAFT.FAIL);
        }

    }

    public void verifyCCTab() throws Exception {
        String stropeninghrs = "Credit card services opening hours";
       // String strhelpROI = "8am to 8pm Monday to Friday, 9am to 6pm Saturdays, 10am to 5pm on Sundays and Bank Holidays."; // edited by abhijeet
        String strhelpROI2 = "9am to 5pm, Monday to Friday. Closed weekends."; // edited by abhijeet
        if (isElementDisplayed(getObject("contactus.CCtab"), 3)) {
            clickJS(getObject("contactus.CCtab"), "Credit Card");
            Thread.sleep(2000);
        }
        if ((isElementDisplayed(getObject("contactus.ccopeninghrs"), 10))
                && ((getText(getObject("contactus.ccopeninghrs")).equalsIgnoreCase(stropeninghrs)))) {
            report.updateTestLog("verify365BankingTab", "Credit Card opening hours  label correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verify365BankingTab", "Credit Card opening hours  label not present on screen", Status_CRAFT.FAIL);
        }

        if ((isElementDisplayed(getObject("contactus.cchelp"), 10))
                && ((getText(getObject("contactus.cchelp")).equalsIgnoreCase(strhelpROI2)))) {
            report.updateTestLog("verify365BankingTab", "ROI opening timings correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verify365BankingTab", "ROI help text not present on screen", Status_CRAFT.FAIL);
        }
        if ((isElementDisplayed(getObject("contactus.ROIhelp3"), 10)) // xpath changed by abhijeet
                && ((getText(getObject("contactus.ROIhelp3")).equalsIgnoreCase(strhelpROI2)))) {
            report.updateTestLog("verify365BankingTab", "ROI opening timings correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verify365BankingTab", "ROI help text not present on screen", Status_CRAFT.FAIL);
        }


    }

    public void verifySocialMediaSection() throws Exception {
        String strwarning = "Never disclose your personal financial information on social media.";

        if ((isElementDisplayed(getObject("contactus.socialerror"), 10))
                && ((getText(getObject("contactus.socialerror")).equalsIgnoreCase(strwarning)))) {
            report.updateTestLog("verifySocialMediaSection", "Social Media warning correctly  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifySocialMediaSection", "Social Media warning not present on screen", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("contactus.twittertab"), 3)) {
            //clickJS(getObject("contactus.CCtab"), "Credit Card");
            report.updateTestLog("verifySocialMediaSection", "Twitter Tab displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifySocialMediaSection", "Twitter Tab not present on screen", Status_CRAFT.FAIL);
        }

        if (isElementDisplayed(getObject("conatctus.boardstab"), 3)) {
            // clickJS(getObject("contactus.CCtab"), "Credit Card");
            report.updateTestLog("verifySocialMediaSection", "Boards Tab displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifySocialMediaSection", "Boards Tab not present on screen", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function to launch contact us page
     */
    public void launchMoreServices() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "home.btnServiceDesk"), 3)) {
            click(getObject((deviceType() + "home.btnServiceDesk")));
            report.updateTestLog("launchContactUs", "Services is displayed", Status_CRAFT.PASS);
            //report.updateTestLog(" 'SERVICES' ", "  'SERVICES' link is displayed successfully", Status_CRAFT.PASS);
            click(getObject("launch.moreservices"), "More Services");
            Thread.sleep(5000);
        }

    }

    /**
     * Function to verify more services page
     */
    public void verifyMoreServices() throws Exception {

        if ((isElementDisplayed(getObject("moreservices.header"), 10))) {
            report.updateTestLog("verifyMoreServices", "More Services title correctly displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyMoreServices", "More Services title not present on screen", Status_CRAFT.FAIL);
        }

        List<WebElement> allLinks = findElements(getObject("moreservice.options"));
        if (allLinks.size() > 1) {
            int intIndex = 1;
            for (WebElement ele : allLinks) {
                String linkname = ele.getText();
                report.updateTestLog("verifyMoreServices", linkname + "present on screen", Status_CRAFT.PASS);
            }


        }

    }

    /**
     * Function to select service on more services page
     */

    public void selectoptions() throws Exception {

        String strApplyOption = dataTable.getData("General_Data", "Nickname");
        List<WebElement> allLinks = findElements(getObject("moreservice.options"));
        if (allLinks.size() > 1) {
            int intIndex = 1;
            for (WebElement ele : allLinks) {
                String linkname = ele.getText();
                if (linkname.equalsIgnoreCase(strApplyOption)) {
                    ele.click();
                    intIndex = intIndex;
                    report.updateTestLog("selectoptions", strApplyOption + " is clicked  ", Status_CRAFT.PASS);
                    break;
                }
                intIndex = intIndex++;

            }
        }

    }

    public void verifyTimeoutPopup() throws Exception {

        Thread.sleep(300000);

        if ((!isElementclickable(getObject("apply.timeout"), 10))) {
            report.updateTestLog("verifyTimeoutPopup", "TimeOut popup not  displayed on screen", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("verifyTimeoutPopup", "TimeOut popup displayed on screen", Status_CRAFT.FAIL);
        }


    }

    public void selectPaymentTab() throws Exception {

        if (deviceType.equalsIgnoreCase("Web")) {
            if (isElementDisplayed(getObject(deviceType() + "home.tabPayments"), 10)) {
                driver.findElement(By.xpath("//*[text()='PAYMENTS']")).click();
                Thread.sleep(2000);


            }
        }
    }

    public void clickoption() throws Exception {

        if (isElementDisplayed(getObject("moreservices.bookappointment"), 20)) {
            clickJS(getObject("moreservices.bookappointment"), "Logout");
            Thread.sleep(2000);
        }
    }
    public void verifytitle() throws Exception{
        if (isElementDisplayed(getObject(deviceType()+"services.contactustitle"),10)){
            report.updateTestLog("verifyContactUsTitle", "Contact us title is displayed as expected", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("verifyContactUsTitle", "Contact us title is not displayed as expected", Status_CRAFT.FAIL);
        }
    }

    public void verifyfooterlinks() throws Exception {
        String strLink = dataTable.getData("General_Data", "Nickname");//add the link name to be clicked in data sheet
        String strNavigation = dataTable.getData("General_Data", "VerifyDetails");
        scrollToView(getObject("home.foot"));
        Thread.sleep(3000);
        String[] arrLink = strLink.split(";");
        String[] arrNavigation = strNavigation.split(";");

        for (int i = 0; i < arrLink.length; i++) {
            String LinktoCheck = arrLink[i];
            String strURLlink = arrNavigation[i];


            // clickJS(getObject((("xpath~//*[@id='C3__row_LoginPageNavigation-FooterLogin-" + LinktoCheck + "']"))), "Footer link");
            List<WebElement> allLinks = findElements(getObject("home.footerlist"));

            if (allLinks.size() > 1) {
                int intIndex = 1;
                for (WebElement ele : allLinks) {
                    String linkname = ele.getText();
                    if (linkname.equalsIgnoreCase(LinktoCheck)) {
                        ele.click();
                        intIndex = intIndex;
                        // report.updateTestLog("verifyfooterlinks", LinktoCheck + " is clicked  ", Status_CRAFT.PASS);
                        break;
                    }
                    intIndex = intIndex++;

                }
            }
            Thread.sleep(5000);
            waitForJQueryLoad();
        }
    }

    public void verifytab() throws Exception {
        //String contactuscontent = "Notify the bank without delay to report: online fraud, unauthorised transactions, transactions incorrectly executed, lost/stolen card or if you know or suspect your 365 login credentials or any other security credentials are known by someone who should not know them. ";
        //String contactuscontentobj = getText(getObject("xpath~//div[@class='boi-alert-wrap boi-alert-wrap--info boi-alert-msg--blue']//span[2]"));
        waitForPageLoaded();
        Thread.sleep(3000);

        /*String windowHandle =driver.getWindowHandle();
                driver.findElementByCssSelector("body").sendKeys(Keys.CONTROL + "t");*/

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        if (tabs.size() > 0) {

            driver.switchTo().window(tabs.get(1));
            String linkTitle = driver.getTitle();
            report.updateTestLog("verifytab", " User is naviagted to :: " + linkTitle + ":: page succssfully.", Status_CRAFT.PASS);
            if (isElementDisplayed(getObject(deviceType()+"PreLogincontactustitle"),10)){
                report.updateTestLog("verifyContactUsTitle", "Contact us title is displayed as expected", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("verifyContactUsTitle", "Contact us title is not displayed as expected", Status_CRAFT.FAIL);
            }
            if(isElementDisplayed(getObject("xpath~//div[@class='boi-alert-wrap boi-alert-wrap--info boi-alert-msg--blue']/span[2]"),10)){
                report.updateTestLog("VerifyContactusUpdatedcontent", "Contact us content is as expected", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("VerifyContactusUpdatedcontent", "Contact us content is not as expected", Status_CRAFT.FAIL);
            }

            /*if (linkURL.equalsIgnoreCase(strURLlink)) {
                report.updateTestLog("ChildWindowhandler", " User is naviagted to correct URL :: " + strURLlink + ":: succssfully.", Status_CRAFT.PASS);
*/
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }
        else {
            report.updateTestLog("verifytab", " New Tab did not open ", Status_CRAFT.FAIL);
        }
    }

    //for mobile

    public void clickmore() throws Exception{
       if(deviceType().equals("mw.")){
        if (isElementDisplayed(getObject("home.lnkPreLgnMore"),5)){
            clickJS(getObject("home.lnkPreLgnMore"),"More option is clicked from footer");
            isElementDisplayed(getObject("more.contactus"),5);
            clickJS(getObject("more.contactus"),"Contact us tab is clicked");
            if(isElementDisplayed(getObject("xpath~//div[@class='boi-alert-wrap boi-alert-wrap--info boi-alert-msg--blue']/span[2]"),10)){
                report.updateTestLog("VerifyContactusUpdatedcontent", "Contact us content is as expected", Status_CRAFT.PASS);
                clickJS(getObject("back.navigationcontactus"),"contact us back navigation is clicked");
                Thread.sleep(1000);
                clickJS(getObject("back.navigation"),"Back navigation is clicked for navigating to homepage");
            }else{
                report.updateTestLog("VerifyContactusUpdatedcontent", "Contact us content is not as expected", Status_CRAFT.FAIL);
            }
        }
       }

       else {
            report.updateTestLog("VerifyMoreFooter", "More option is not displayed on Desktop", Status_CRAFT.DONE);
        }
    }

    public void clickmorePost() throws Exception {
        if(deviceType.equals("mw.") || deviceType.equals("tw.")) {
            if (isElementDisplayed(getObject("home.lnkPostlgnMore"), 5)) {
                clickJS(getObject("home.lnkPostlgnMore"), "More option is clicked post login");
                isElementDisplayed(getObject("more.contactus"), 5);
                clickJS(getObject("more.contactus"), "Contact us tab is clicked");
                if (isElementDisplayed(getObject("xpath~//div[@class='boi-alert-wrap boi-alert-wrap--info boi-alert-msg--blue']/span[2]"), 10)) {
                    report.updateTestLog("VerifyContactusUpdatedcontent", "Contact us content is as expected", Status_CRAFT.PASS);
                    clickJS(getObject("back.navigationcontactus"), "contact us back navigation is clicked");
                    waitForPageLoaded();
                } else {
                    report.updateTestLog("VerifyContactusUpdatedcontent", "Contact us content is not as expected", Status_CRAFT.FAIL);
                }
            }
        }
        else if(deviceType.equals("w.")){
            clickJS(getObject(deviceType() + "home.btnServiceDesk"),"Service desk option clicked");
            if (isElementDisplayed(getObject("xpath~//span[text()='More services']"), 2)){
                report.updateTestLog("clickmorePost in Web", "More services is as expected", Status_CRAFT.PASS);
            }
            else{
                report.updateTestLog("clickmorePost in Web", "More services is not  as expected", Status_CRAFT.FAIL);
            }
        }

    }

    public void clickFAQsprelogin() throws Exception{
        Askaquestion AAQ = new Askaquestion(scriptHelper);
        if (isElementDisplayed(getObject("more.FAQsprelogin"),5) && isMobile ){
            clickJS(getObject("more.FAQsprelogin"),"FAQ button is clicked pre login");
            report.updateTestLog("FAQs button ", "FAQs button is clicked", Status_CRAFT.PASS);
            AAQ.verifytab();
            Thread.sleep(1000);
            if(isElementDisplayed(getObject("back.navigation"),2)) {
                clickJS(getObject("back.navigation"), "Back button is clicked");
            }
            waitForPageLoaded();
        } else{
            report.updateTestLog("FAQs button ", "FAQs button is not clicked or on Desktop FAQ is not  present", Status_CRAFT.DONE);
        }
    }

}
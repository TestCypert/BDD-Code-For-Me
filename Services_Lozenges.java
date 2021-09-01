package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.Color;

import java.util.List;

/**
 * Created by C101965 on 06/09/2019.
 */
public class Services_Lozenges extends WebDriverHelper {
    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public Services_Lozenges(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /*General Function:
     *Scroll to view particular  element using JS
    */

    public void ScrollToVisibleJS(String linkToVisible) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject((linkToVisible))));
            Thread.sleep(2000);
        } catch (UnreachableBrowserException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }


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

    public void ServicesLozengesNavigation() throws Exception {
        ScrollAndClickJS(deviceType() + "home.btnServiceDesk");
        if (isElementDisplayed(getObject(deviceType() + "SL.tabSelfServeValidation"), 10)) {
            report.updateTestLog("Services Lozenges", "Self serve tab is displayed on Page", Status_CRAFT.PASS);
            String colorCode = "#106988";
            String color = driver.findElement(By.xpath("//*[@id=\"C5__BUT_7AA19D744D176164199812\"]")).getCssValue("background-color");
            String hexColorValue = Color.fromString(color).asHex();
            System.out.println(hexColorValue);
            if (colorCode.equalsIgnoreCase(hexColorValue)) {
                report.updateTestLog("Self serve", "Color code is equal to hex color value that is 'Blue'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("Self serve", "Color code is not equal to hex color value ", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("Services Lozenges", "Self serve tab is not displayed on Page", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject(deviceType() + "SL.tabMessageValidation"), 10)) {
            report.updateTestLog("Services Lozenges", "Message tab is displayed on Page", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Services Lozenges", "Message tab is not displayed on Page", Status_CRAFT.FAIL);
        }
    }

    public void clickMessage() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "SL.tabMessageValidation"), 10)) {
            clickJS(getObject(deviceType() + "SL.tabMessageValidation"), "Message tab is clicked");
            report.updateTestLog("Services Lozenges", "Message tab is clicked ", Status_CRAFT.PASS);
            waitForPageLoaded();
        } else {
            report.updateTestLog("Services Lozenges", "Message tab is not clicked ", Status_CRAFT.FAIL);
        }
    }


    public void verifyDefaultSelection() throws Exception {

        if (isElementDisplayed(getObject("SL.inboxtab"), 15)) {

            String defaulttab = driver.findElement(By.xpath("//div[@class='boi-internal-messages__tabs boi-internal-messages__tabs--selected boi-width-50pct boi-tg__font--bold boi-tg__size--small--fixed boi_grey--dark boi-remove-title ecDIB']/a")).getAttribute("tabindex");
            defaulttab.contains("0");                       //div[@class="boi-internal-messages__tabs boi-width-50pct boi-tg__font--regular boi-tg__size--small--fixed boi_grey--dark boi-remove-title ecDIB"]

            if (defaulttab.equalsIgnoreCase("0")) {

                report.updateTestLog("verifyDefaultSelection", "Inbox tab is selected by default ", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyDefaultSelection", "Default selection is incorrect ", Status_CRAFT.FAIL);
            }

        } else {
            report.updateTestLog("verifyDefaultSelection", "Inbox tab is not present on screen ", Status_CRAFT.FAIL);
        }
    }


    public void verifyemptyinbox() throws Exception {

        String strexpectedmsg = dataTable.getData("General_Data", "Relationship_Status");

        if (isElementDisplayed(getObject(deviceType() + "SL.inboxemptymsg"), 10)) {
            report.updateTestLog("verifyemptyinbox", "Inbox empty message displayed: ", Status_CRAFT.PASS);

            String stractualmsg = getText(getObject(deviceType() + "SL.inboxemptymsg"));

            if (isElementDisplayed(getObject("SL.inboxtab"), 10) && (stractualmsg.equalsIgnoreCase(strexpectedmsg))) {

                report.updateTestLog("verifyDefaultSelection", "Correct message is displayed: " + stractualmsg, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyDefaultSelection", "Expected message is incorrect ", Status_CRAFT.FAIL);
            }

        } else {
            report.updateTestLog("verifyDefaultSelection", "(There are no message in inbox) not displayed", Status_CRAFT.FAIL);
        }
    }

    public void verifyemptysent() throws Exception {

        String strexpectedmsg = dataTable.getData("General_Data", "Nickname");

        if (isElementDisplayed(getObject(deviceType() + "SL.sentemptymsg"), 10)) {

            String stractualmsg = getText(getObject(deviceType() + "SL.sentemptymsg"));

            if (isElementDisplayed(getObject(deviceType() + "SL.sentemptymsg"), 10) && (stractualmsg.equalsIgnoreCase(strexpectedmsg))) {

                report.updateTestLog("verifyemptysent", "Correct message is displayed: " + stractualmsg, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyemptysent", "Expected message is incorrect ", Status_CRAFT.FAIL);
            }

        } else {
            report.updateTestLog("verifyemptysent", "Message is not displayed", Status_CRAFT.FAIL);
        }
    }

    public void clickSent() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "SL.senttab"), 10)) {
            clickJS(getObject(deviceType() + "SL.senttab"), "Sent Tab");
            report.updateTestLog("Services Lozenges", "Sent tab is clicked ", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("Services Lozenges", "Sent tab is not clicked ", Status_CRAFT.FAIL);
        }
    }

    public void clickShowMore() throws Exception {

        String defaulttab = driver.findElement(By.xpath("//div[@class='boi-internal-messages__tabs boi-internal-messages__tabs--selected boi-width-50pct boi-tg__font--bold boi-tg__size--small--fixed boi_grey--dark boi-remove-title ecDIB']/a")).getAttribute("tabindex");
        defaulttab.contains("0");
        if (defaulttab.equalsIgnoreCase("0")) {
            clickJS(getObject(deviceType() + "SL.showmoreInbox"), "Show more button");
            report.updateTestLog("Services Lozenges", "Show more button is clicked for Inbox", Status_CRAFT.PASS);
        } else {
            clickJS(getObject(deviceType() + "SL.showmore"), "Show more button");
            report.updateTestLog("Services Lozenges", "Show more button is  clicked for Sent", Status_CRAFT.PASS);
        }
    }

    public void clickShowMorenew() throws Exception {

        String defaulttab = driver.findElement(By.xpath("//div[@class='boi-internal-messages__tabs boi-internal-messages__tabs--selected boi-width-50pct boi-tg__font--bold boi-tg__size--small--fixed boi_grey--dark boi-remove-title ecDIB']")).getAttribute("tabindex");
        defaulttab.contains("-1");
        if (defaulttab.equalsIgnoreCase("-1")) {
            clickJS(getObject(deviceType() + "SL.showmore"), "Show more button");
            report.updateTestLog("Services Lozenges", "Show more button is  clicked for Sent", Status_CRAFT.PASS);

        } else {
            clickJS(getObject(deviceType() + "SL.inboxtab"),"navigate to inbox");
            waitForPageLoaded();
            clickJS(getObject(deviceType() + "SL.senttab"),"navigate sentbox");

            //clickJS(getObject(deviceType() + "SL.showmoreInbox"), "Show more button");
            //report.updateTestLog("Services Lozenges", "Show more button is clicked for Inbox", Status_CRAFT.PASS);
        }
    }
   /* public void validatesentbox() throws Exception {
        List<WebElement> sentmaillist = driver.findElements(By.xpath("//div[contains(@id,'C5__FMT_781A38DFD311C2FD1039106_R') and contains(@class,'boi_grey--dark boi-flex')]"));
        if (sentmaillist.size() == 12) {
            clickShowMore();
            //click(getObject(deviceType() +"SL.showmore"), "Show more button");
            waitForPageLoaded();
            List<WebElement> sentmaillistCurrent = driver.findElements(By.xpath("//div[contains(@id,'C5__FMT_781A38DFD311C2FD1039106_R') and contains(@class,'boi_grey--dark boi-flex')]"));
            System.out.println(sentmaillistCurrent.size());
            if (sentmaillistCurrent.size() > sentmaillist.size()) {
                report.updateTestLog("Services Lozenges", "More than 12 mails are there after cicking on show more button ", Status_CRAFT.PASS);
            }

        } else {
            if (sentmaillist.size() < 12)
                report.updateTestLog("Services Lozenges", "Show more button is not present", Status_CRAFT.PASS);
        }
    }*/
    ///////////////
    public void validatesentbox() throws Exception {

        List<WebElement> sentmaillist = driver.findElements(By.xpath("//div[contains(@id,'C5__FMT_781A38DFD311C2FD1039106_R') and contains(@class,'boi_grey--dark boi-flex')]"));

        if (sentmaillist.size() == 12) {
            System.out.println("method started");
            //clickShowMore();
            //click(getObject(deviceType() +"SL.showmore"), "Show more button");
            clickShowMorenew();
            waitForPageLoaded();
            List<WebElement> sentmaillistCurrent = driver.findElements(By.xpath("//div[contains(@id,'C5__FMT_781A38DFD311C2FD1039106_R') and contains(@class,'boi_grey--dark boi-flex')]"));
            System.out.println(sentmaillistCurrent.size());
            System.out.println("size populated");
            if (sentmaillistCurrent.size() > sentmaillist.size()) {
                report.updateTestLog("Services Lozenges", "More than 12 mails are there after cicking on show more button ", Status_CRAFT.PASS);
            }
        } else {
            if (sentmaillist.size() < 12)
                report.updateTestLog("Services Lozenges", "Show more button is not present", Status_CRAFT.PASS);
        }
    }
    public void validateInboxShowMore() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "SL.inboxemptymsg"), 10)) {
            report.updateTestLog("Services Lozenges", "Show more button is not present and Inbox empty message displayed: ", Status_CRAFT.PASS);
        } else {
            List<WebElement> sentmaillist = driver.findElements(By.xpath("//div[contains(@id,'C5__FMT_781A38DFD311C2FD1039106_R') and contains(@class,'boi_grey--dark boi-flex')]"));

            if (sentmaillist.size() == 12) {
                System.out.println("method started");
                clickShowMore();
                //click(getObject(deviceType() +"SL.showmore"), "Show more button");
                //clickShowMorenew();
                waitForPageLoaded();
                List<WebElement> sentmaillistCurrent = driver.findElements(By.xpath("//div[contains(@id,'C5__FMT_781A38DFD311C2FD1039106_R') and contains(@class,'boi_grey--dark boi-flex')]"));
                System.out.println(sentmaillistCurrent.size());
                System.out.println("size populated");
                if (sentmaillistCurrent.size() > sentmaillist.size()) {
                    report.updateTestLog("Services Lozenges", "More than 12 mails are there after cicking on show more button ", Status_CRAFT.PASS);
                }
            } else {
                if (sentmaillist.size() < 12)
                    report.updateTestLog("Services Lozenges", "Show more button is not present", Status_CRAFT.PASS);
            }
        }
    }





    /////////////////////

    public void verify12monthmessagesent() throws Exception {

        String strexpectedmsg = dataTable.getData("General_Data", "Nickname");

        if (isElementDisplayed(getObject(deviceType() + "SL.12monthSent"), 10)) {

            String stractualmsg = getText(getObject(deviceType() + "SL.12monthSent"));

            if (isElementDisplayed(getObject(deviceType() + "SL.12monthSent"), 10) && (stractualmsg.equalsIgnoreCase(strexpectedmsg))) {

                report.updateTestLog("verify12monthmessage", "Correct message is displayed: " + stractualmsg, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verify12monthmessage", "Expected message is incorrect ", Status_CRAFT.FAIL);
            }
        }
    }


    public void verifymessagedateInbox() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "SL.dateinbox"), 10)) {

            for (int i = 1; i <= 1; i++) {

                WebElement date1 = driver.findElement(By.xpath("//div[@id='C5__COL_116D03C0E6B21296387080_R" + i + "']"));
                String strdate1 =  date1.getText();

                for (int j = i+1; j < 12; j++){

                    WebElement date2 = driver.findElement(By.xpath("//div[@id='C5__COL_116D03C0E6B21296387080_R" + j + "']"));
                    String strdate2 =  date2.getText();

                    HomePage homePage = new HomePage(scriptHelper);
                    boolean blnNewestFirst = homePage.ValidateNewestFirst(strdate1, strdate2, "dd MMM yyyy");
                    if (blnNewestFirst == true) {
                        report.updateTestLog("verifymessagedate", " Newest date appears first.", Status_CRAFT.DONE);
                    } else {

                        report.updateTestLog("verifymessagedate", "Mismatch in date order", Status_CRAFT.FAIL);

                    }
                }


            }
        }
        else {
            report.updateTestLog("verifymessagedate", "No messages are present ", Status_CRAFT.FAIL);
        }
    }

    public void verifymessagenameSent() throws Exception{

        if (isElementDisplayed(getObject(deviceType() + "SL.Referencesent"), 10)) {

            for (int i = 1; i <= 12; i++) {

                WebElement Msgname = driver.findElement(By.xpath("//div[@id='C5__row_QUE_5DAF9D17259002AB5814221_R" + i + "']"));
                WebElement Refnumber = driver.findElement(By.xpath("//div[(@id='C5__row_QUE_5DAF9D17259002AB5814221_R" + i + "')]/..//following-sibling::div[@id='C5__COL_781A38DFD311C2FD1030769_R" + i + "']"));

                String actualMsgname = Msgname.getText();
                String actualRefnumber = Refnumber.getText();

                if ((actualMsgname.contains("Ref")) && (actualMsgname.contains(actualRefnumber))) {
                    report.updateTestLog("verifymessagename", "Message and reference number are correctly displayed in the grid :: " + actualMsgname + " :: " + actualRefnumber, Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifymessagename", "Message and reference number are incorrect ", Status_CRAFT.FAIL);
                }
            }

        } else {
            report.updateTestLog("verifymessagename", "No messages are present ", Status_CRAFT.FAIL);
        }
    }


    public void verifymessagedateSent() throws Exception{

        if (isElementDisplayed(getObject(deviceType() + "SL.datesent"), 10)) {
            report.updateTestLog("verifymessagedateSent", " :: Screenshot :: ", Status_CRAFT.SCREENSHOT);
            for (int i = 1; i <= 1; i++) {

                //WebElement date1 = driver.findElement(By.xpath("//div[@id='C5__COL_781A38DFD311C2FD1030773_R" + i + "']"));
                WebElement date1 =driver.findElement(By.xpath("(//span[@class='accessibilityStyle'][contains(text(),'Sent on')]/..)['" + i + "']"));
                String strdate1 =  date1.getText();

                for (int j = i+1; j <= 12; j++){

                    //WebElement date2 = driver.findElement(By.xpath("//div[@id='C5__COL_781A38DFD311C2FD1030773_R" + j + "']"));
                    WebElement date2 =driver.findElement(By.xpath("(//span[@class='accessibilityStyle'][contains(text(),'Sent on')]/..)['" + j + "']"));
                    String strdate2 =  date2.getText();

                    HomePage homePage = new HomePage(scriptHelper);
                    boolean blnNewestFirst = homePage.ValidateNewestFirst(strdate1, strdate2, "dd MMM yyyy");
                    if (blnNewestFirst == true) {
                        report.updateTestLog("verifymessagedate", " Newest date appears first.", Status_CRAFT.DONE);
                    } else {

                        report.updateTestLog("verifymessagedate", "Mismatch in date order", Status_CRAFT.FAIL);

                    }
                }


            }
        }
        else {
            report.updateTestLog("verifymessagedate", "No messages are present ", Status_CRAFT.FAIL);
        }

    }

    public void verifyBacktoInbox() throws Exception{

        if (isElementDisplayed(getObject(deviceType() + "SL.BacktoInbox"), 10)){
            click(getObject(deviceType() + "SL.BacktoInbox"), "Back to Inbox");
            Thread.sleep(2000);
        }
    }

    public void verifyinboxMsg() throws Exception {

        while (isElementclickable(getObject(deviceType() + "SL.blueiconmessage"), 3)) {

            clickJS(getObject(deviceType() + "SL.showmoreInbox"), "Show More");
            Thread.sleep(2000);
        }

        String strRefrence = getText(getObject(deviceType() + "SL.blueiconmessage"));

        clickJS(getObject(deviceType() + "SL.blueiconmessage"), "First blue message clicked");
        Thread.sleep(2000);


        if (isElementDisplayed(getObject(deviceType() + "SL.subject"), 10)){

            String strSubject = getText(getObject(deviceType() + "SL.subjectline"));

            if ((isElementDisplayed(getObject(deviceType() + "SL.subjectline"), 3)) && (strSubject.contains(strRefrence))){

                report.updateTestLog("verifyinboxMsg", "Correct Subject line is displayed ", Status_CRAFT.PASS);
            }


        }else {
            report.updateTestLog("verifyinboxMsg", "Subject Line missing ", Status_CRAFT.FAIL);
        }

        verifyinboxMsg();

        WebElement openedmail = driver.findElement(By.xpath("//div[@class='ecDIBCol  ecDIB  boi-message__column-content--left']/div/div/div/span[2][text()='RE: [RefA9040100068803]Credit card']/../../../../../../descendant::div[@class='ecDIBCol  ecDIB  boi-message__column-icon boi-pr-15']/div/span[@class='fa fa-icon  fa-envelope-open  boi-fs-24']"));

        if (openedmail.isDisplayed()){

            report.updateTestLog("verifyinboxMsg", "Mail icon is changes succesfully", Status_CRAFT.PASS);
        }else {
            report.updateTestLog("verifyinboxMsg", "Mail icon does not change correctly", Status_CRAFT.FAIL);
        }

    }
    public void verifyBacktosentitems() throws Exception{

        if (isElementDisplayed(getObject(deviceType() + "SL.btnBacktosentitmes"), 10)){
            clickJS(getObject(deviceType() + "SL.btnBacktosentitmes"), "Back to Sent Items");
            Thread.sleep(1000);
            report.updateTestLog("verifybacktosentitems", "Back to sent items is displayed and clicked successfully", Status_CRAFT.PASS);
            report.updateTestLog("Back button clicked", ":: Page Screenshot ::", Status_CRAFT.SCREENSHOT);
        }else{
            report.updateTestLog("verifybacktosentitems", "Back to sent items is not displayed ", Status_CRAFT.FAIL);
            waitForPageLoaded();
            report.updateTestLog("Back button clicked", ":: Page Screenshot ::", Status_CRAFT.SCREENSHOT);
        }
    }

    public void clickShowmorecomplete() throws Exception{
        if (isElementDisplayed(getObject(deviceType()+"SL.showmore"),5)) {
            report.updateTestLog("verifyShowMoreButton", "Show more button is displayed", Status_CRAFT.PASS);
            do {
                click(getObject(deviceType()+"SL.showmore"), "Show more button");
                waitForPageLoaded();
//                break;
//                if (!isElementDisplayed(getObject(deviceType()+"SL.showmore"), 3)) {
//                    report.updateTestLog("verifyCompletedSection", "Completed Section is displayed", Status_CRAFT.PASS);
//
//                }
            } while (isElementDisplayed(getObject(deviceType()+"SL.showmore"), 3));
    }}


    public void validateUnreadmessage() throws Exception {
        if (isElementDisplayed(getObject(deviceType() + "Home.XpathMessage"), 10)) {
            report.updateTestLog("Services Lozenges", "Unread message indicator is present on homescreen", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "Home.XpathMessage"), "Unread message indicator clicked");
            report.updateTestLog("Services Lozenges", "navigated to inbox message - to read unread messages", Status_CRAFT.PASS);
            verifyBacktoInboxverifyinboxMsg();

            clickJS(getObject(deviceType() + "NavigateToHomeXpath"), "click on account tile");
            isElementDisplayed(getObject(deviceType() + "Home.XpathMessage"), 10);
            report.updateTestLog("Services Lozenges", "Unread message indicator is still present on homescreen", Status_CRAFT.PASS);

        } else {
            isElementNotDisplayed(getObject(deviceType() + "Home.XpathMessage"), 10);
            report.updateTestLog("Services Lozenges", "NO unread message indicator is present on homescreen", Status_CRAFT.PASS);
        }
    }

    public void verify12monthmessageinbox() throws Exception {

        String strexpectedmsg = dataTable.getData("General_Data", "Nickname");
        // String defaulttab = driver.findElement(By.xpath("//div[@class='boi-internal-messages__tabs boi-internal-messages__tabs--selected boi-width-50pct boi-tg__font--bold boi-tg__size--small--fixed boi_grey--dark boi-remove-title ecDIB']")).getAttribute("tabindex");
        if (isElementDisplayed(getObject(deviceType() + "SL.12monthInbox"), 10)) {
            String stractualmsg = getText(getObject(deviceType() + "SL.12monthInbox"));
            if (isElementDisplayed(getObject(deviceType() + "SL.12monthInbox"), 10) && (stractualmsg.equalsIgnoreCase(strexpectedmsg))) {
                report.updateTestLog("verify12monthmessage", "Correct message is displayed: " + stractualmsg, Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verify12monthmessage", "Expected message is incorrect ", Status_CRAFT.FAIL);
            }
        }
    }

    public void verifymessagenameInbox() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "SL.Referenceinbox"), 10)) {

            for (int i = 1; i <= 12; i++) {
                if(isElementDisplayed(getObject(deviceType() + "Message.ElementByrow"),5)){

                WebElement Msgname = driver.findElement(By.xpath("//div[@id='C5__row_QUE_9CD2BA4DB6D8D181185177_R" + i + "']"));
                WebElement Refnumber = driver.findElement(By.xpath("//div[(@id='C5__row_QUE_9CD2BA4DB6D8D181185177_R" + i + "')]/..//following-sibling::div[@id='C5__COL_116D03C0E6B21296387036_R" + i + "']"));

                String actualMsgname = Msgname.getText();
                String actualRefnumber = Refnumber.getText();

                if ((actualMsgname.contains("Ref")) && (actualMsgname.contains(actualRefnumber))) {

                    report.updateTestLog("verifymessagename", "Message and reference number are correctly displayed in the grid" + actualMsgname + actualRefnumber, Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("verifymessagename", "Message and reference number are incorrect ", Status_CRAFT.FAIL);
                }
            } }

        } else {
            report.updateTestLog("verifymessagename", "No messages are present ", Status_CRAFT.FAIL);
        }
    }

    public void verifyBacktoInboxverifyinboxMsg() throws Exception {

        while (isElementclickable(getObject(deviceType() + "SL.blueiconmessage"), 3)) {

            clickJS(getObject(deviceType() + "SL.showmoreInbox"), "Show More");
            Thread.sleep(2000);
        }

        String strRefrence = getText(getObject(deviceType() + "SL.blueiconmessage"));

        clickJS(getObject(deviceType() + "SL.blueiconmessage"), "First blue message clicked");
        Thread.sleep(2000);


        if (isElementDisplayed(getObject(deviceType() + "SL.subject"), 10)){

            String strSubject = getText(getObject(deviceType() + "SL.subjectline"));

            if ((isElementDisplayed(getObject(deviceType() + "SL.subjectline"), 3)) && (strSubject.contains(strRefrence))){

                report.updateTestLog("verifyinboxMsg", "Correct Subject line is displayed ", Status_CRAFT.PASS);
            }


        }else {
            report.updateTestLog("verifyinboxMsg", "Subject Line missing ", Status_CRAFT.FAIL);
        }

        verifyBacktoInbox();

        WebElement openedmail = driver.findElement(By.xpath("//div[@class='ecDIBCol  ecDIB  boi-message__column-content--left']/div/div/div/span[2][contains(text(),'RE: [RefA9040100071269]Proof of payment')]/../../../../../../descendant::div[@class='ecDIBCol  ecDIB  boi-message__column-icon boi-pr-15']/div/span[@class='fa fa-icon  fa-envelope-open  boi-fs-24']"));

       if (openedmail.isDisplayed()){

           report.updateTestLog("verifyinboxMsg", "Mail icon is changes succesfully", Status_CRAFT.PASS);
       }else {
           report.updateTestLog("verifyinboxMsg", "Mail icon does not change correctly", Status_CRAFT.FAIL);
       }

    }

    public void verifymessageindicator() throws Exception{
        if(isElementDisplayed(getObject(deviceType()+"SL.unreadmessageindicator"),5)){
            report.updateTestLog("verifyMesasageindicator", "Unread messages are present and indicator symbol is displayed", Status_CRAFT.PASS);
            String messageindivalue = getText(getObject("xpath~//button[text()='Messages ']/span[@aria-hidden='true']"));
            System.out.println(messageindivalue);
            String updateindicator = messageindivalue.substring(1,2);
            int oldcount = Integer.parseInt(updateindicator);
            report.updateTestLog("verifyMesasageindicator", " Old Message Indicator :: Old Count value before checking Inbox :: " + oldcount, Status_CRAFT.DONE);
            clickMessage();
            List<WebElement> mailcount = driver.findElements(By.xpath("//div[contains(@id,'C5__TXT_116D03C0E6B21296386922_R')]/span[@class='fa fa-icon  fa-envelope boi_blue  boi-fs-24']"));
            for(int i=0; i<mailcount.size();i++){
                try {
                    JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                    executor.executeScript("arguments[0].click();", mailcount.get(i));
                } catch (UnreachableBrowserException e) {
                    e.printStackTrace();
                } catch (StaleElementReferenceException e) {
                    e.printStackTrace();
                }
                clickJS(getObject(deviceType() + "SL.BacktoInbox"), "Back to Inbox");
                if (!isElementDisplayed(getObject(deviceType()+"SL.unreadmessageindicator"),5) && oldcount == 1 ){
                    report.updateTestLog("verifyMesasageindicator", "No unread message is to displayed as count is decreased from 1 to 0 which is expected.", Status_CRAFT.PASS);
                    break;
                }
                String newmessageindivalue = getText(getObject("xpath~//button[text()='Messages ']/span[@aria-hidden='true']")).toString();
                if (newmessageindivalue.length() > 0 ){
                    String decrementvalue = newmessageindivalue.substring(1, 2);
                    int newcount = Integer.parseInt(decrementvalue);
                    report.updateTestLog("verifyMesasageindicator", " New Message Indicator :: New Count value after checking Inbox :: " + newcount, Status_CRAFT.DONE);
                    if (oldcount>newcount){
                        report.updateTestLog("verifyMesasageindicator", "Count decreased", Status_CRAFT.PASS);
                    } else{
                        report.updateTestLog("verifyMesasageindicator", "No change in Count ", Status_CRAFT.FAIL);
                    }
                }else{
                    report.updateTestLog("verifyMesasageindicator", " New Message Indicator value is blank / Not Visible. Please Check.", Status_CRAFT.FAIL);
                }

                break;
            }
        }else{
            if (!isElementDisplayed(getObject(deviceType()+"SL.unreadmessageindicator"),5)){
                report.updateTestLog("verifyMesasageindicator", "No unread message is displayed", Status_CRAFT.PASS);
            }
        }
    }

    public void verifysentmaildetails() throws Exception{

        if (isElementDisplayed(getObject(deviceType() + "SL.Referencesent"), 10)) {

            for (int i = 1; i <= 1; i++) {

                WebElement Msgname = driver.findElement(By.xpath("//div[@id='C5__row_QUE_5DAF9D17259002AB5814221_R" + i + "']"));
                //WebElement Refnumber = driver.findElement(By.xpath("//div[(@id='C5__row_QUE_5DAF9D17259002AB5814221_R" + i + "')]/..//following-sibling::div[@id='C5__COL_781A38DFD311C2FD1030769_R" + i + "']"));

                String actualMsgname = Msgname.getText();
                //String actualRefnumber = Refnumber.getText();

                if (actualMsgname.contains("Deposit Account")) {
                    clickJS(getObject("xpath~//div[@id='C5__row_QUE_5DAF9D17259002AB5814221_R" + i + "']"),"mail is opened");

                    report.updateTestLog("verifymessagename", "Message "+ actualMsgname + "is displayed and clicked successfully", Status_CRAFT.PASS);
                    withdrawFundsmaildetails();
                    verifyBacktosentitems();
                } else {
                    report.updateTestLog("verifymessagename", "No message with deposit account is displayed", Status_CRAFT.FAIL);
                }
            }

        } else {
            report.updateTestLog("verifymessagename", "No messages are present ", Status_CRAFT.FAIL);
        }
    }

    public void withdrawFundsmaildetails() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        homePage.verifyElementDetails(deviceType() +"SL.validatecontent");
        verifyBacktosentitems();
    }
}




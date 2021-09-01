package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.xmlbeans.impl.xb.xsdschema.FieldDocument;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class Squad5Sprint23 extends WebDriverHelper {

    /**
     * Constructor to initialize the component group library
     *
     * @param scriptHelper The {@link ScriptHelper} object passed from the
     *                     {@link DriverScript}
     */
    public Squad5Sprint23(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    public void enterPSKpin() throws InterruptedException {
        String[] arrPin = dataTable.getData("General_Data", "InvalidPIN").split("");
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject(deviceType() + "PSKActiveserialnumber"));
        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
            }
        }
        clickJS(getObject(deviceType() + "PSKActive"), "Activate");

    }

    public void activationpendingvalidation() throws InterruptedException {
        if (isElementDisplayed(getObject(deviceType() + "PSKNobutton"), 10)) {
            report.updateTestLog("No not yet", "No not yet", Status_CRAFT.DONE);
            clickJS(getObject(deviceType() + "PSKNobutton"), "No not yet");
        }
        Thread.sleep(1000);
        click(getObject(deviceType() + "PSKsmartphonesetup"), "Smart Phone setup");

        if (isElementDisplayed(getObject(deviceType() + "registersmartphnedlg"), 10)) {
            report.updateTestLog("registersmartphnedlg", "Register smart phone dialog", Status_CRAFT.DONE);
            click(getObject(deviceType() + "smartphonedlgclose"), "Smart Phone dialog close");
            Thread.sleep(1000);
        } else {
            report.updateTestLog("registersmartphnedlg", "Register smart phone dialog", Status_CRAFT.FAIL);
        }
        click(getObject(deviceType() + "PSK365login"), "365 login");

    }

    public void activationpageerrmsgvalidation() throws InterruptedException {
        if (isElementDisplayed(getObject(deviceType() + "PSKYesbutton"), 10)) {
            report.updateTestLog("Yes i have it in my hand", "i have it in my hand", Status_CRAFT.DONE);
            clickJS(getObject(deviceType() + "PSKYesbutton"), "i have it in my hand");
        }
        Thread.sleep(1000);
        enterPSKpin();
        Thread.sleep(1000);
        enterPSKpin();
        Thread.sleep(1000);
        if (isElementDisplayed(getObject(deviceType() + "PSKActive1sterrormsg"), 10)) {
            report.updateTestLog("Activaion1sterrormsg", "Activaion 1st errormsg", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("Activaion1sterrormsg", "Activaion 1st errormsg", Status_CRAFT.FAIL);
        }
        Thread.sleep(1000);
        enterPSKpin();
        if (isElementDisplayed(getObject(deviceType() + "PSKAccountblockmsg"), 10)) {
            report.updateTestLog("Accountblockmsg", "Account block message displayed", Status_CRAFT.DONE);
        } else {
            report.updateTestLog("Accountblockmsg", "Account block message", Status_CRAFT.FAIL);
        }
        //click(getObject("launch.btnLogIn"), "Login");
    }

    public void securitydeviceAction() throws Exception {
        HomePage homePage = new HomePage(scriptHelper);
        int i = 1;
        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        String deviceselect = dataTable.getData("General_Data", "Tokendeviceselect");
        if (deviceselect.equals("Blocked")) {
            for (i = 1; i <= allLinks.size(); i++) {
                // String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
                String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
                if (isElementDisplayed(getObject(activexpath), 3)) {

                    if ((getText(getObject(activexpath)).equals(deviceselect)) || (getText(getObject(activexpath)).equals("(" + deviceselect + ")"))) {
                        clickJS(getObject(activexpath), "Security Device");


                        //click(getObject(activexpath));
                        report.updateTestLog("Verify" + deviceselect, deviceselect + "Devices No." + i + "is CLICKED", Status_CRAFT.PASS);
                        if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevices"), 3)) {
                            report.updateTestLog("VerifyUnblockdevices", "UnBlock Device button is present", Status_CRAFT.PASS);
                            clickJS(getObject(deviceType() + "txtUnblockdevices"), "UnBlock Device");
                            if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevicespopup"), 3)) {
                                report.updateTestLog("VerifyunBlockdevices", "unBlock Device button is present on Pop up", Status_CRAFT.PASS);
                                clickJS(getObject(deviceType() + "txtUnblockdevicespopup"), "UnBlock Device popup");
                                homePage.enterRequiredPin();


                                if (isElementDisplayed(getObject(deviceType() + "unblockmessage"), 30)) {
                                    report.updateTestLog("unblocksuccessmessage", "unblock success message displayed", Status_CRAFT.PASS);
                                } else {
                                    report.updateTestLog("unblocksuccessmessage", "unblock success message NOT displayed", Status_CRAFT.FAIL);
                                }


                                //click on edit button
                                if (isElementDisplayed(getObject(deviceType() + "editnickname"), 3)) {
                                    clickJS(getObject(deviceType() + "editnickname"), "Edit Icon");
                                }

                                if (isElementDisplayed(getObject(deviceType() + "txtnewnickname"), 3)) {
                                    sendKeys(getObject(deviceType() + "txtnewnickname"), dataTable.getData("General_Data", "NewNickname"));
                                }

                                //click on continue

                                clickJS(getObject(deviceType() + "deviceContinue"), "Continue");


                                //validate Nickname Message

                                if (isElementDisplayed(getObject(deviceType() + "updatednickname"), 3)) {
                                    report.updateTestLog("VerifyNickname", " 16 digit alphanumeric Nickname is Updated", Status_CRAFT.PASS);
                                } else {
                                    report.updateTestLog("VerifyNickname", "16 digit alphanumeric Nickname NOT Updated", Status_CRAFT.FAIL);
                                }

                                clickJS(getObject(deviceType() + "txtblockdevices"), "Block Device");
                                if (isElementDisplayed(getObject(deviceType() + "txtblockdevicespopup"), 3)) {
                                    report.updateTestLog("VerifyBlockdevices", "Block Device button is present on Pop up", Status_CRAFT.PASS);
                                    clickJS(getObject(deviceType() + "txtblockdevicespopup"), "UnBlock Device popup");
                                    homePage.enterRequiredPin();
                                    if (isElementDisplayed(getObject(deviceType() + "blockmessage"), 30)) {
                                        report.updateTestLog("unblocksuccessmessage", "block success message displayed", Status_CRAFT.PASS);
                                    } else {
                                        report.updateTestLog("unblocksuccessmessage", "block success message NOT displayed", Status_CRAFT.FAIL);
                                    }
                                    break;
                                } else {
                                    report.updateTestLog("VerifyBlockdevices", "Block Device button is not present on Pop up", Status_CRAFT.FAIL);
                                }


                            } else {
                                report.updateTestLog("VerifyunBlockdevices", "unBlock Device button is not present on Pop up", Status_CRAFT.FAIL);
                            }

                        }
                    }
                }
            }
        } else {
            if (deviceselect.equals("Active")) {
                for (i = 1; i <= allLinks.size(); i++) {
                    // String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
                    String activexpath = "xpath~//*[@id='C5__C1__C4__p1_QUE_22C3AC5D525AD48C348549_R" + i + "']";
                    if (isElementDisplayed(getObject(activexpath), 3)) {

                        if ((getText(getObject(activexpath)).equals(deviceselect)) || (getText(getObject(activexpath)).equals("(" + deviceselect + ")"))) {
                            clickJS(getObject(activexpath), "Security Device");
                            //click(getObject(activexpath));
                            report.updateTestLog("Verify" + deviceselect, deviceselect + "Devices No." + i + "is CLICKED", Status_CRAFT.PASS);
                            if (isElementDisplayed(getObject(deviceType() + "txtblockdevices"), 3)) {
                                report.updateTestLog("blockdevices", "Block Device button is present", Status_CRAFT.PASS);
                                clickJS(getObject(deviceType() + "txtblockdevices"), "Block Device");
                                if (isElementDisplayed(getObject(deviceType() + "txtblockdevicespopup"), 3)) {
                                    report.updateTestLog("VerifyBlockdevices", "Block Device button is present on Pop up", Status_CRAFT.PASS);
                                    clickJS(getObject(deviceType() + "txtblockdevicespopup"), "UnBlock Device popup");
                                    homePage.enterRequiredPin();
                                    if (isElementDisplayed(getObject(deviceType() + "blockmessage"), 30)) {
                                        report.updateTestLog("blocksuccessmessage", "block success message displayed", Status_CRAFT.PASS);
                                    } else {
                                        report.updateTestLog("blocksuccessmessage", "block success message NOT displayed", Status_CRAFT.FAIL);
                                    }

                                    clickJS(getObject(deviceType() + "txtUnblockdevices"), "UnBlock Device");
                                    if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevicespopup"), 3)) {
                                        report.updateTestLog("VerifyunBlockdevices", "unBlock Device button is present on Pop up", Status_CRAFT.PASS);
                                        clickJS(getObject(deviceType() + "txtUnblockdevicespopup"), "UnBlock Device popup");
                                        homePage.enterRequiredPin();


                                        if (isElementDisplayed(getObject(deviceType() + "unblockmessage"), 30)) {
                                            report.updateTestLog("unblocksuccessmessage", "unblock success message displayed", Status_CRAFT.PASS);
                                        } else {
                                            report.updateTestLog("unblocksuccessmessage", "unblock success message NOT displayed", Status_CRAFT.FAIL);
                                        }
                                    } else {
                                        report.updateTestLog("Unblockdevicespopup", "Unblock Device Pop up not displayed", Status_CRAFT.FAIL);
                                    }
                                    break;
                                } else {
                                    report.updateTestLog("VerifyBlockdevices", "Block Device button is not present on Pop up", Status_CRAFT.FAIL);
                                }

                            }
                        }

                    }
                }

            }
        }
        if (i > allLinks.size()) {
            report.updateTestLog(deviceselect, deviceselect + " Device  is not present ", Status_CRAFT.FAIL);
        }
    }

    public void validatePSKDetail() throws Exception {

        if (isElementDisplayed(getObject(deviceType() + "profileIcon"), 3)) {
            Thread.sleep(2000);
            clickJS(getObject(deviceType() + "profileIcon"), "Profile Icon");
            //click(getObject(deviceType() + "profileIcon"));
            if (isElementDisplayed(getObject(deviceType() + "txtprofile"), 3)) {
                if (deviceType().equals("mw.")) {
                    clickJS(getObject(deviceType() + "home.SecurityDevices"), "Security Device");
                }
                report.updateTestLog("verifyprofileclick", "click on profile successfully", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("verifyprofileclick", "Profile Icon not clicked", Status_CRAFT.FAIL);
            }
        }

        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        for (int i = 1; i <= allLinks.size() + 1; i++) {
            String devicenameXpath = "xpath~//*[@id='C5__C1__C4__QUE_22C3AC5D525AD48C348548_R" + i + "']";
            if (isElementDisplayed(getObject(devicenameXpath), 3)) {
                if ((getText(getObject(devicenameXpath)).equals("Physical Security Key")) || (getText(getObject(devicenameXpath)).equals("(Physical Security Key)"))) {
                    String deviceName = getText(getObject(devicenameXpath));
                    clickJS(getObject(devicenameXpath), "Physical Security Key");
                    Thread.sleep(2000);
                    if (isElementDisplayed(getObject(deviceType() + "PSKpageheader"), 3)) {
                        report.updateTestLog("PSK page", "PSK Page detail displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("PSK Page", "PSK Page Detail", Status_CRAFT.FAIL);
                    }
                    //validatehardtokenpage();
                    break;
                }
            }
        }
    }
    public void Devicedisplaystatus() throws Exception {
        HomePage Hm = new HomePage(scriptHelper);
        //String devicestate1 = dataTable.getData("General_Data", "Tokendeviceselect");
        Thread.sleep(2000);
        String strdevice = dataTable.getData("General_Data", "NewNickname");
        Thread.sleep(2000);
        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        for (int i = 1; i <= allLinks.size() + 1; i++) {
            String devicenameXpath = "xpath~//*[@id='C5__C1__C4__QUE_22C3AC5D525AD48C348548_R" + i + "']";
            if (isElementDisplayed(getObject(devicenameXpath), 3)) {
                if ((getText(getObject(devicenameXpath)).equals(strdevice)) || (getText(getObject(devicenameXpath)).equals(strdevice))) {
                    String deviceName = getText(getObject(devicenameXpath));
                    clickJS(getObject(devicenameXpath), strdevice);
                    Thread.sleep(2000);
                   /* if (isElementDisplayed(getObject(deviceType() + "PSKpageheader"), 3)) {
                        report.updateTestLog("PSK page", "PSK Page detail displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("PSK Page", "PSK Page Detail", Status_CRAFT.FAIL);
                    }*/
                    //validatehardtokenpage();
                    break;
                }
            }
        }

        if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevices"), 3)) {
            report.updateTestLog("VerifyUnblockdevices", "UnBlock Device button is present", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "txtUnblockdevices"), "UnBlock Device");
            if (isElementDisplayed(getObject(deviceType() + "txtUnblockdevicespopup"), 3)) {
                report.updateTestLog("VerifyunBlockdevices", "unBlock Device button is present on Pop up", Status_CRAFT.PASS);
                clickJS(getObject(deviceType() + "txtUnblockdevicespopup"), "UnBlock Device popup");
                Hm.enterRequiredPin();


                if (isElementDisplayed(getObject(deviceType() + "unblockmessage"), 30)) {
                    report.updateTestLog("unblocksuccessmessage", "unblock success message displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("unblocksuccessmessage", "unblock success message NOT displayed", Status_CRAFT.FAIL);
                }
            }
        }
       /* if (isElementDisplayed(getObject(deviceType() + "txtblockdevices"), 3)) {
            report.updateTestLog("blockdevices", "Block Device button is present", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "txtblockdevices"), "Block Device");
            if (isElementDisplayed(getObject(deviceType() + "txtblockdevicespopup"), 3)) {
                report.updateTestLog("VerifyBlockdevices", "Block Device button is present on Pop up", Status_CRAFT.PASS);
                clickJS(getObject(deviceType() + "txtblockdevicespopup"), "UnBlock Device popup");
                Hm.enterRequiredPin();
                if (isElementDisplayed(getObject(deviceType() + "blockmessage"), 30)) {
                    report.updateTestLog("blocksuccessmessage", "block success message displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("blocksuccessmessage", "block success message NOT displayed", Status_CRAFT.FAIL);
                }
            }
        }*/
    }
    public void Devicedisplaystatuschange() throws Exception {
        HomePage Hm = new HomePage(scriptHelper);
        String devicestate1 = dataTable.getData("General_Data", "Tokendeviceselect");
        Thread.sleep(2000);
        String strdevice = dataTable.getData("General_Data", "NewNickname");
        Thread.sleep(2000);
        List<WebElement> allLinks = findElements(getObject(deviceType() + "txtalldevices"));

        for (int i = 1; i <= allLinks.size() + 1; i++) {
            String devicenameXpath = "xpath~//*[@id='C5__C1__C4__QUE_22C3AC5D525AD48C348548_R" + i + "']";
            if (isElementDisplayed(getObject(devicenameXpath), 3)) {
                if ((getText(getObject(devicenameXpath)).equals(strdevice)) || (getText(getObject(devicenameXpath)).equals(strdevice))) {
                    String deviceName = getText(getObject(devicenameXpath));
                    clickJS(getObject(devicenameXpath), strdevice);
                    Thread.sleep(2000);
                    /*if (isElementDisplayed(getObject(deviceType() + "PSKpageheader"), 3)) {
                        report.updateTestLog("PSK page", "PSK Page detail displayed", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("PSK Page", "PSK Page Detail", Status_CRAFT.FAIL);
                    }*/
                    //validatehardtokenpage();
                    break;
                }
            }
        }
        if (isElementDisplayed(getObject(deviceType() + "txtblockdevices"), 3)) {
            report.updateTestLog("blockdevices", "Block Device button is present", Status_CRAFT.PASS);
            clickJS(getObject(deviceType() + "txtblockdevices"), "Block Device");
            if (isElementDisplayed(getObject(deviceType() + "txtblockdevicespopup"), 3)) {
                report.updateTestLog("VerifyBlockdevices", "Block Device button is present on Pop up", Status_CRAFT.PASS);
                clickJS(getObject(deviceType() + "txtblockdevicespopup"), "UnBlock Device popup");
                Hm.enterRequiredPin();
                if (isElementDisplayed(getObject(deviceType() + "blockmessage"), 30)) {
                    report.updateTestLog("blocksuccessmessage", "block success message displayed", Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("blocksuccessmessage", "block success message NOT displayed", Status_CRAFT.FAIL);
                }
            }
        }
    }
                // if (isElementDisplayed(getObject("launch.deviceListPage"), 5))
        //{
         //   report.updateTestLog("DeviceListPage", " Choose security device page is displayed", Status_CRAFT.PASS);


            //if (isElementDisplayed(getObject("Devicelist.Next"), 5)) {
              //  report.updateTestLog("selectDeviceListSCA", "Next button is displayed", Status_CRAFT.PASS);

              // if (devicestate1.equalsIgnoreCase("Active")) {
                    /*while (isElementDisplayed(getObject("xpath~//span[text()='" + strdevice + "']"), 3)) {
                        if(isElementDisplayed(getObject("xpath~//span[text()='" + strdevice + "']"), 3)){
                        report.updateTestLog("Devicesdisplaystatus", "active device name is displayed", Status_CRAFT.PASS);
                        clickJS(getObject("Devicelist.Next"), "Next button");
                        }

                    else{
                        report.updateTestLog("Devicesdisplaystatus", "active device name is displayed", Status_CRAFT.FAIL);
                    }
                        if (devicestate1.equalsIgnoreCase("blocked"))
                            while (isElementDisplayed(getObject("xpath~//span[text()='" + strdevice + "']"), 3)) {
                                if(isElementDisplayed(getObject("xpath~//span[text()='" + strdevice + "']"), 3)){
                                    report.updateTestLog("Devicesdisplaystatus", "Block device name is displayed", Status_CRAFT.FAIL);
                                    clickJS(getObject("Devicelist.Next"), "Next button");
                                }

                                else{
                                    report.updateTestLog("Devicesdisplaystatus", "Block device name is  not displayed", Status_CRAFT.PASS);
                                }


                    {

                    }
                }


                }*/
               // Thread.sleep(1000);
                //report.updateTestLog("DeviceListPage", " Device selected from list successfully", Status_CRAFT.PASS);
            /*{
                List<WebElement> choosedevice = driver.findElements(getObject("numberofdevices"));
                for (int i = 1; i <= choosedevice.size(); i++)

                {
                    String devicetochoose = "xpath~//label[@for='C2__C1__QUE_0D857B61A322D937351294_R" + i + "']";

                    if (getText(getObject(devicetochoose)).equals(dataTable.getData("General_Data", "NewNickname"))) {
                        report.updateTestLog("Devicedisplaystatus", "device name is displayed on the device choose page", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("Devicedisplaystatus", "devie name is not displayed", Status_CRAFT.FAIL);
                    }
                }

            }
        } else if (devicestate1.equalsIgnoreCase("Blocked")) {
            if (isElementDisplayed(getObject("choosesecuritydevice"), 5)) {
                List<WebElement> choosedevice = driver.findElements(getObject("numberofdevices"));
                for (int i = 1; i <= choosedevice.size(); i++) {
                    String devicetochoose = "xpath~//label[@for='C2__C1__QUE_0D857B61A322D937351294_R" + i + "']";
                    if (!getText(getObject(devicetochoose)).equals(dataTable.getData("General_Data", "NewNickname"))) {
                        report.updateTestLog("Devicedisplaystatus", " Blocked device name is not displayed ", Status_CRAFT.PASS);
                    } else {
                        report.updateTestLog("Devicedisplaystatus", "Blocked device name is displayed on the device choose page", Status_CRAFT.FAIL);
                    }

                }
            }*/

            //}
        //}


   // }

}

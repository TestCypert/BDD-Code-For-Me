/*---------------------------------Start <SD_SS_NeedHelp>----------------------------------------
Function Name: SD_SS_NeedHelp
Purpose: To view the contact us & FAQ pages in services module
Parameter: NeedHelp
Author Name: CAF Automation 
 Create Date: 09-06-2021
Modified Date| Modified By  |Modified purpose 
 09/05/2021      C113329       Code completion
-----------------------------------End <SD_SS_NeedHelp>---------------------------------------
*/
// Review Section
package com.boi.grp.pageobjects.Services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class Service_SS_NeedHelp extends BasePageForAllPlatform{
	
	public UIResusableLibrary cafFunctional;
	
	By ContactUs = getObject("sd.buttonContactUs");
	By FAQ = getObject("sd.buttonFAQ");
	By ContactNumberHeader = getObject("sd.ContactNumberHeader");
	By EmergencyHeader = getObject("sd.EmergengyHeader");
	By EmergencyROI = getObject("sd.EmergencyROI");
	By EmergencyNIGB = getObject("sd.EmergencyNI/GB");
	By Header365 = getObject("sd.365Header");
	By OpeningHrs365 = getObject("sd.365OpeningHrs");
	By ROI365 = getObject("sd.365ROI");
	By NIGB365 = getObject("sd.365NI/GB");
	By ROIHelp = getObject("sd.ROIhelp");
	By ROIHelp2 = getObject("sd.ROIhelp2");
	By CreditCardServiceTab = getObject("sd.CCServicetab");
	By CCOpeningHrs = getObject("sd.ccopeninghrs");
	By CCHelp = getObject("sd.cchelp");
	By SocialError = getObject("sd.socialerror");
	By TwiterTab = getObject("sd.twittertab");
	By BoardsTab = getObject("sd.boardstab");
	
	/*---------------------------------Start <SD_SS_NeedHelp>----------------------------------------
	Function Name: SD_SS_NeedHelp
	Purpose: To view the contact us & FAQ pages in services module
	Parameter: NeedHelp
	Author Name: CAF Automation 
	 Create Date: 09-06-2021
	Modified Date| Modified By  |Modified purpose 
	 09/05/2021      C113329       Code completion
	-----------------------------------End <SD_SS_NeedHelp>---------------------------------------
	*/
	
	public Service_SS_NeedHelp(WebDriver driver){
		
		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional= new UIResusableLibrary(driver);
	}
	
	/* Verify BP Need Help */
	public void SD_SS_NeedHelp(String serNHOpt){
		try{
            logMessage("SERVICES OPTION TYPE : " + serNHOpt);
            boolean bFlageSerNHOpt = false;            
            switch (serNHOpt.toUpperCase()) {                 
            case "CONTACT_US":
            	if(isElementDisplayed(ContactUs, 10, "ContactUs")){
            	scrollToView(ContactUs);
            	SD_NeedHelp_ContactUS_P();
                bFlageSerNHOpt = true;
            	} else {
            	bFlageSerNHOpt = false;
            	}
                break;
                
            case "FAQ":
            	if(isElementDisplayed(FAQ, 10, "FAQ")){
            	scrollToView(FAQ);
            	SD_NeedHelp_FAQs_P();
            	bFlageSerNHOpt = true;
            	} else {
            	bFlageSerNHOpt = false;
            	}
            	break;
            }
            if (bFlageSerNHOpt == true) {
				appendScreenshotToCucumberReport();
				logMessage(serNHOpt + " Click on Need Help tile successfully in SD_SS_NeedHelp function ");
				injectMessageToCucumberReport(serNHOpt + " Click on Need Help tile successfully");
				Allure.step("Click on Need Help tile successfully", Status.PASSED);
			} else {
				logMessage(serNHOpt + " Click on Need Help tile NOT successfully");
				injectMessageToCucumberReport(serNHOpt + " Failure in Click on Need Help tab ");
				Allure.step(serNHOpt + " Click on Need Help tile NOT successfully ", Status.FAILED);
				appendScreenshotToCucumberReport();
			}
		} catch (Exception e) {
			logError("Error Occured inside SD_SS_NeedHelp " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Need Help option selection " + e.getMessage());
			Allure.step("Failure in Need Help option selection ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}
	
	/*---------------------------------Start <SD_NeedHelp_ContactUS_P>----------------------------------------
	Function Name: SD_NeedHelp_ContactUS_P
	Purpose: To view the contact us page in services module
	Parameter: NeedHelp
	Author Name: CAF Automation 
	 Create Date: 09-06-2021
	Modified Date| Modified By  |Modified purpose 
	 09/06/2021      C113329       Code completion
	-----------------------------------End <SD_NeedHelp_ContactUS_P>---------------------------------------
	*/
	
	private void SD_NeedHelp_ContactUS_P() {
		try {
			clickJS(ContactUs, "Services - Need Help - CantactUS");
			verifyContactNumberSection();
			verify365BankingTab();
			verifyCCTab();
			verifySocialMediaSection();
		} catch (Exception e) {
			logError("Error Occured inside SD_NeedHelp_ContactUS_P " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Contact Us option selection " + e.getMessage());
			Allure.step("Failure in Contact Us option selection ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}
	
	/*---------------------------------Start <SD_NeedHelp_FAQs_P>----------------------------------------
	Function Name: SD_NeedHelp_FAQs_P
	Purpose: To view the contact us page in services module
	Parameter: NeedHelp
	Author Name: CAF Automation 
	 Create Date: 16-06-2021
	Modified Date| Modified By  |Modified purpose 
	 16/06/2021      C113329       Code completion
	-----------------------------------End <SD_NeedHelp_FAQs_P>---------------------------------------
	*/
	
	private void SD_NeedHelp_FAQs_P() {
		try{
		scrollToView(FAQ);
		if(isElementDisplayed(FAQ, 10, "FAQ")){
			clickJS(FAQ, "Services - Need Help - FAQ");
			logMessage("FAQ Link is displayed on screen");
		}else{
			logMessage("FAQ Link is not displayed on screen");
		}
		} catch (Exception e) {
			logError("Error Occured inside SD_NeedHelp_FAQs_P " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in FAQs option selection " + e.getMessage());
			Allure.step("Failure in FAQs option selection ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}
	
	// Method To Verify Social Media Selection tab
	private void verifySocialMediaSection() throws Exception {
		try{
       
        if ((isElementDisplayed(SocialError, 10, "SocialError"))
                && ((getText(SocialError).equalsIgnoreCase(getMessageText("SocialMediaWarning", "UXPBANKING365"))))) {
            logMessage("Social Media warning correctly displayed on screen");
        } else {
            logMessage("Social Media warning not displayed on screen");
        }
        if (isElementDisplayed(TwiterTab, 3, "TwiterTab")) {
            //clickJS(getObject("contactus.CCtab"), "Credit Card");
            logMessage("Twitter Tab displayed on screen");
        } else {
            logMessage("Twitter Tab not displayed on screen");
        }

        if (isElementDisplayed(BoardsTab, 3, "BoardsTab")) {
            // clickJS(getObject("contactus.CCtab"), "Credit Card");
            logMessage("Boards Tab displayed on screen");
        } else {
            logMessage("Boards Tab not displayed on screen");
        }
		} catch (Exception e) {
			logError("Error Occured inside verifySocialMediaSection " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Social Media option selection " + e.getMessage());
			Allure.step("Failure in Social Media option selection ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}
    }
	
	// Method to verify Credit Card tab
	private void verifyCCTab() throws Exception {
        
       try{
        
        if (isElementDisplayed(CreditCardServiceTab, 3, "CreditCardServiceTab")) {
            clickJS(CreditCardServiceTab, "Credit Card");
            Thread.sleep(2000);
        }
        if ((isElementDisplayed(CCOpeningHrs, 10, "CCOpeningHrs"))
                && ((getText(CCOpeningHrs).equalsIgnoreCase(getMessageText("stropeninghrs", "UXPBANKING365"))))) {
            logMessage("Credit Card opening hours label correctly displayed on screen");
        } else {
            logMessage("Credit Card opening hours label not displayed on screen");
        }

        if ((isElementDisplayed(CCHelp, 10, "CCHelp"))
                && ((getText(CCHelp).equalsIgnoreCase(getMessageText("strhelpROI2", "UXPBANKING365"))))) {
            logMessage("ROI opening timings correctly displayed on screen");
        } else {
            logMessage("ROI opening timings not displayed on screen");
        }
       } catch (Exception e) {
			logError("Error Occured inside verifyCCTab " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Credit Card option selection " + e.getMessage());
			Allure.step("Failure in Credit Card option selection ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}		
	}
	
	// Method To Verify Contact Number Selection tab
	private void verifyContactNumberSection() throws Exception, Exception {
		
        try{
		if ((isElementDisplayed(ContactNumberHeader, 10, "ContactNumberHeader"))
                && ((getText(ContactNumberHeader).equalsIgnoreCase(getMessageText("strContNumHeader", "UXPBANKING365"))))) {
            logMessage("Contact Number Header is Displayed Successfully");
        } else {
            logMessage("Contact Number Header is Not Displayed");
        }

        if ((isElementDisplayed(EmergencyHeader, 10, "EmergencyHeader"))
                && ((getText(EmergencyHeader).equalsIgnoreCase(getMessageText("strEmergency", "UXPBANKING365"))))) {
        	logMessage("Emergency Header is Displayed Successfully");
        } else {
        	logMessage("Emergency Header is Not Displayed");
        }

        if ((isElementDisplayed(EmergencyROI, 10, "EmergencyROI"))
                && ((getText(EmergencyROI).equalsIgnoreCase(getMessageText("strROI", "UXPBANKING365"))))) {
        	logMessage("Emergency ROI is Displayed Successfully");
        } else {
        	logMessage("Emergency ROI is Not Displayed");
        }

        if ((isElementDisplayed(EmergencyNIGB, 10, "EmergencyNIGB"))
                && ((getText(EmergencyNIGB).equalsIgnoreCase(getMessageText("strNIGB", "UXPBANKING365"))))) {
        	logMessage("Emergency NI/GB is Displayed Successfully");
        } else {
        	logMessage("Emergency NI/GB is Not Displayed");
        }
        } catch (Exception e) {
			logError("Error Occured inside verifyContactNumberSection " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Contact Number option selection " + e.getMessage());
			Allure.step("Failure in Contact Number option selection ", Status.FAILED);
			appendScreenshotToCucumberReport();
		}	
	}
	
	// Method to verify 365 Banking tab
	private void verify365BankingTab()throws Exception {
        
         try{
        	 if ((isElementDisplayed(Header365, 10, "Header365"))
                     && ((getText(Header365).equalsIgnoreCase(getMessageText("str365BankingHeader", "UXPBANKING365"))))) {
                 scrollToView(Header365); 
            logMessage("365 phone banking  header correctly  displayed on screen");
        } else {
            logMessage("365 phone banking  header not  displayed on screen");
        }

        if ((isElementDisplayed(OpeningHrs365, 10, "OpeningHrs365"))
                && ((getText(OpeningHrs365).equalsIgnoreCase(getMessageText("str365BankingOpeningHrs", "UXPBANKING365"))))) {
            logMessage("365 phone banking opening hours  label correctly  displayed on screen");
        } else {
            logMessage("365 phone banking opening hours  label not  displayed on screen");
        }

        if ((isElementDisplayed(ROI365, 10, "ROI365"))
                && ((getText(ROI365).equalsIgnoreCase(getMessageText("strROI", "UXPBANKING365"))))) {
            scrollToView(ROI365);
            logMessage("ROI label correctly  displayed on screen");
        } else {
            logMessage("ROI label not  displayed on screen");
        }

        if ((isElementDisplayed(NIGB365, 10, "NIGB365"))
                && ((getText(NIGB365).equalsIgnoreCase(getMessageText("strNIGB", "UXPBANKING365"))))) {
            logMessage("NI/GB label correctly  displayed on screen");
        } else {
            logMessage("NI/GB label correctly  displayed on screen");
        }

        if ((isElementDisplayed(ROIHelp, 10, "ROIHelp"))
                && ((getText(ROIHelp).equalsIgnoreCase(getMessageText("strhelpROI2", "UXPBANKING365"))))) {
            logMessage("ROI opening timings correctly  displayed on screen");
        } else {
            logMessage("ROI opening timings not displayed on screen");
        }
        if ((isElementDisplayed(ROIHelp2, 10, "ROIHelp2"))
                && ((getText(ROIHelp2).equalsIgnoreCase(getMessageText("str365BankingHelp2", "UXPBANKING365"))))) {
            logMessage("NI/GB opening timings correctly  displayed on screen");
        } else {
            logMessage("NI/GB opening timings correctly  displayed on screen");
        }
	} catch (Exception e) {
		logError("Error Occured inside verify365BankingTab " + e.getMessage());
		injectWarningMessageToCucumberReport("Failure in 365 Banking option selection " + e.getMessage());
		Allure.step("Failure in 365 Banking option selection ", Status.FAILED);
		appendScreenshotToCucumberReport();
	}	
}
}

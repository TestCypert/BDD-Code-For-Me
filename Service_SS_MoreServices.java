package com.boi.grp.pageobjects.Services;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Set;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class Service_SS_MoreServices extends BasePageForAllPlatform {

	public UIResusableLibrary cafFunctional;
	
	@FindBy(how = How.XPATH, using = "//*[@id='details-button']")
	WebElement advance;

	@FindBy(how = How.XPATH, using = "//*[@id='proceed-link']")
	WebElement proceed;

	public Service_SS_MoreServices(WebDriver driver) {

		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional = new UIResusableLibrary(driver);
	}

	By linkSendUsMessage = getObject("sd.linkSendUsMessage");
	By clickCallUsLostOrStolen = getObject("sd.clickCallUsLostOrStolen");
	By msgLostOrStolen = getObject("sd.msgLostOrStolen");
	By clickCallUsContactUs = getObject("sd.clickCallUsContactUs");
	By msgContactUs = getObject("sd.msgContactUs");
	By linkComplaintProcess = getObject("sd.linkComplaintProcess");
	By buttonContinue = getObject("sd.buttonContinue");
	By msgYourQuestion = getObject("sd.msgYourQuestion");
	By msgAccountsAndServices = getObject("sd.msgAccountsAndServices");
	By msgSubject = getObject("sd.msgSubject");
	By msgMessage = getObject("sd.msgMessage");
	By dropdownAccountorPolicy = getObject("sd.dropdownAccountorPolicy");
	By dropdownSubject = getObject("sd.dropdownSubject");
	By textareaMessage = getObject("sd.textareaMessage");
	By buttonBackToHome = getObject("sd.buttonBackToHome");
	By buttonSend = getObject("sd.buttonSend");
	By selectDrpDwnName = getObject("sd.selectDrpDwnName");
	By selectDrpDwnName2 = getObject("sd.selectDrpDwnName2");
	By linkAccessLifeOnline = getObject("sd.linkAccessLifeOnline");
	By imgAccessLifeOnlie = getObject("sd.imgAccessLifeOnlie");
	
	/*---------------------------------Start <SD_SS_MoreService>----------------------------------------
	Function Name: More Service
	Purpose: To verify Send us message and Access Life Online
	Parameter: SEND_US_MESSAGE and ACCESS_LIFE_ONLINE
	Author Name: CAF Automation 
	 Create Date: 01-07-2021
	Modified Date| Modified By  |Modified purpose 
	 01-07-2021      C114688       Code completion
	-----------------------------------End <SD_SS_MoreService>---------------------------------------
	*/  
	public void SD_SS_MoreService(String serOptions) {
		try {
			boolean bServiceMoreFlag =false;
			switch (serOptions.toUpperCase()) 
			{
			
			//for send us message
			case "SEND_US_MESSAGE":
				if (isElementDisplayed(linkSendUsMessage,10,"linkSendUsMessage")) {
					SD_More_SendUsMsg_P(serOptions);
					
					bServiceMoreFlag=true;
				}
				else{
					bServiceMoreFlag=false;
				}
				break;
			//for access life online	
			case "ACCESS_LIFE_ONLINE":
				if (isElementDisplayed(linkAccessLifeOnline,10,"linkAccessLifeOnline")){
					SD_More_AccessLifeOnline_P();
					bServiceMoreFlag=true;
				}
				else{
					bServiceMoreFlag=false;
				}
				break;
			}
			if (bServiceMoreFlag==true)
			{
				logMessage(serOptions+" ServiceMoreAction is clicked successfully");
				injectMessageToCucumberReport( serOptions+" ServiceMoreAction tab successfully");
				Allure.step( serOptions+" ServiceMoreAction tab Successfully ",Status.PASSED);
			}
			else {
				logMessage(serOptions+" ServiceMoreAction tab is not clicked ");
				Allure.step(serOptions+" Click on ServiceMoreAction tab NOT Successfully ",Status.FAILED);
			}
		} catch (Exception e) {
			logError("Error Occured inside SD_SS_MoreService " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in SD_SS_MoreService functionality " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in SD_SS_MoreService functionality", Status.FAILED);

		}

	}
	//for More access life online 
	private void SD_More_AccessLifeOnline_P() {
		try {
		clickJS(linkAccessLifeOnline, "Service - More - clicked on Access life online");			
		Set <String> allWindowHandles=driver.getWindowHandles(); 
        String parentWindowHandle=driver.getWindowHandle(); 
        Iterator<String> iteratorForWindow = allWindowHandles.iterator();
        while(iteratorForWindow.hasNext()){
               String childWindowHandle = iteratorForWindow.next();
               if(!parentWindowHandle.equals(childWindowHandle)) {
                     driver.switchTo().window(childWindowHandle);           
                     if (isElementDisplayed(advance)){
             			clickJS(advance); //, "Advance"
             			appendScreenshotToCucumberReport();
             			logMessage("InvokeChannelApplication: Advance link is clicked ");
             			waitForPageLoaded();
             			clickJS(proceed); //, "Proceed link"
             			logMessage("InvokeChannelApplication: Proceed link is clicked ");
             		}
                     if (isElementDisplayed(imgAccessLifeOnlie,10,"imgAccessLifeOnlie"))
             		{	//logging
             			logMessage("Access life online  verified successfully");
             			injectMessageToCucumberReport("Access life online verified successfully");
             			Allure.step("Access life online verified successfully", Status.PASSED);
             		}	
               }
		}
        driver.switchTo().window(parentWindowHandle); 
		}
		catch (Exception e) {
			logError("Error Occured inside SD_More_AccessLifeOnline_P " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in SD_More_AccessLifeOnline_P functionality " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in SD_More_AccessLifeOnline_P functionality", Status.FAILED);
		}
	}
	// for More send us message
	private void SD_More_SendUsMsg_P(String serOptions) {
		try {
				
			Services service = new Services(driver);
			String tabs=driver.getWindowHandle();	
			clickJS(linkSendUsMessage,"Services - More Services - SendUsMessage link");// click on senusmesseage link
			clickJS(clickCallUsLostOrStolen,"Services - More Services - Lost or stolen call us"); // click on call us link
			// chceking the screen message value
			String msgLostorStolen = getText(msgLostOrStolen);
			Assert.assertEquals(getMessageText("LostOrStolenMessage", "UXPBANKING365"), msgLostorStolen);
			service.ClickOnServicesTab();
			//SD_SS_MoreService(serOptions);
			clickJS(linkSendUsMessage,"Services - More Services - SendUsMessage link");// click on senusmesseage link
			clickJS(clickCallUsContactUs,"Services - More Services - Contact Us link");
			String msgContactus = getText(msgContactUs);
			Assert.assertEquals(getMessageText("contactUs", "UXPBANKING365"), msgContactus);
			service.ClickOnServicesTab();
			//SD_SS_MoreService(serOptions);
			clickJS(linkSendUsMessage,"Services - More Services - SendUsMessage link");// click on senusmesseage link
			clickJS(linkComplaintProcess,"Services - More Services - Complaint process link");// click on complaint process link
			Thread.sleep(5000);			
			driver.switchTo().window(tabs);	//switching to previous tab		
			Thread.sleep(5000);			
			// check and close the second tab
			clickJS(buttonContinue,"Services - More Services - continue button");// clciking on continue button
			String yourQuestion = getText(msgYourQuestion);
			Assert.assertEquals(getMessageText("yourQuestion", "UXPBANKING365"), yourQuestion);
			String accountsAndPolicy = getText(msgAccountsAndServices);
			Assert.assertEquals(getMessageText("accountAndPolicy", "UXPBANKING365"), accountsAndPolicy);
			String subject = getText(msgSubject);
			Assert.assertEquals(getMessageText("subject", "UXPBANKING365"), subject);
			String message = getText(msgMessage);
			Assert.assertEquals(getMessageText("message", "UXPBANKING365"), message);
			// Setting value
			cafFunctional.clickSelectValueFromDropDown(dropdownAccountorPolicy, selectDrpDwnName, "CURRENT ACC");
			WebElement demo = driver.findElement(dropdownSubject);
			demo.click();
			WebDriverWait ele=new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#C5__QUE_42D7FF66B12542DF18473_item_Proof\\ of\\ payment\\ ROI-X")));
			driver.findElement(By.cssSelector("#C5__QUE_42D7FF66B12542DF18473_item_Proof\\ of\\ payment\\ ROI-X")).click();
			setValue(textareaMessage, generateRandomString() + " " + LocalDate.now().toString());
			clickJS(buttonSend,"Services - More Services - Send button");// clicking on continue button
			clickJS(buttonBackToHome,"Services - More Services - Back to Home button");// clicking on BacktoSend
			//Logging
			logMessage("Send us message verified successfully");
			injectMessageToCucumberReport("Send us message verified successfully");
			Allure.step("Send us message verified successfully", Status.PASSED);

		} catch (Exception e) {
			logError("Error Occured inside SD_More_SendUsMsg_P " + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in SD_More_SendUsMsg_P functionality " + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in SD_More_SendUsMsg_P functionality", Status.FAILED);

		}

	}

}

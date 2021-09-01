package com.boi.grp.pageobjects.Cards;


import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import static io.qameta.allure.Allure.step;

import java.util.List;

import com.boi.grp.pageobjects.BasePageForAllPlatform;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import junit.framework.Assert;

public class ActivateCard extends BasePageForAllPlatform {

	public CCfunctionalComponent cardsFunctional;
	public ManageCardPage manageCardPg;
	public DataLibrary dataLibrary;
	public ActivateCard (WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);		
		cardsFunctional= new CCfunctionalComponent();
		manageCardPg = new ManageCardPage(driver);
		dataLibrary=new DataLibrary();
	}

	
	//************************** COMMON FUNCTIONS *********************************

	public void AC_ClickActivateCardlnk(){		 
		try {
			if (isElementDisplayed(getObject("mc.dots"))) {
				List<WebElement> Bullets = driver.findElements(getObject("mc.dots"));
				for(int i=0; i<Bullets.size();i++){
					if(Bullets.get(i).isEnabled()){
						Bullets.get(i).click();
						Thread.sleep(2*1000);
						if(i==2){
							clickJS(getObject("mc.btnViewPIN"));
							//driver.findElement(getObject("mc.cardsSwipe")).click();
						}

						/*if(i==2){

							System.out.println("**INSIDE CLICK VIEW CARD***" + i+1);
							clickJS(getObject("mc.lnkVCdetails",i+1));
							Thread.sleep(2*1000);
							//driver.findElement(getObject("mc.cardsSwipe")).click();
						}*/

					}
				}
			}
			logMessage("Click Activate card link successfully in AC_ClickActivateCardlnk function.");
			injectMessageToCucumberReport("Click Activate card link successfully");
			step("Click Activate card link Successfully ");
		}catch (Exception e) {
			logError("Error Occured inside AC_ClickActivateCardlnk" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Activate card link "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}


	public void AC_VerifyACErrMsg(String eErr){
		try {
			String ActualErrorText = getText(getObject("enterPin.msgAfterWrongPINtwice"));
			cardsFunctional.validateErrorMessage(eErr, ActualErrorText);
			//TO DO:  CardsFunctionalComponent.enterRequiredPin(String pin);
			logMessage("Verify Error message successfully in AC_ErrorMessage function.");
			injectMessageToCucumberReport("Verify Error message  launch successfully");
			step("Verify Error message  Launch Successfully ");
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside AC_ErrorMessage " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify Error message"+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

	public void AC_Enter3of6DigitPIN(String PinFlag, String Pin){
		System.out.println("****PINFLAG***"+PinFlag);
		try {
			switch (PinFlag.toUpperCase()){
			case "VALIDPIN":
				//EnterPINdetails(Pin);
				System.out.println("*****VALID PIN****");
				break;
			case "INVALIDPIN":						
				//EnterPINdetails(Pin);
				break;
			}
			logMessage("Enter pin details successfully in VC_Enter3of6DigitPIN function.");
			injectMessageToCucumberReport("Enter pin details successfully");
			step("Enter pin details Successfully ");
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside VC_Enter3of6DigitPIN" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Enter pin details "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	}

}

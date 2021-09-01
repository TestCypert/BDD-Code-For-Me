package com.boi.grp.pageobjects.Cards;

import static io.qameta.allure.Allure.step;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import junit.framework.Assert;

public class ReplaceDamagedCard extends BasePageForAllPlatform{

	public UIResusableLibrary cardsFunctional;
	public ManageCardPage manageCardPg;

	public ReplaceDamagedCard(WebDriver driver) {

		super(driver);
		PageFactory.initElements(driver, this);

		cardsFunctional= new UIResusableLibrary(driver);
		manageCardPg = new ManageCardPage(driver);

		// TODO Auto-generated constructor stub
	}
	
	public void OR_ValActivateCard(String cardNum){
		
		try {

			List<WebElement> Bullet = driver.findElements(getObject("mc.dots"));
			//CODE TO NAVIGATE VIEW CARD DETAILS PAGE
			for(int i=0; i<Bullet.size();i++)
			{
				Bullet = driver.findElements(getObject("mc.dots"));

				if(Bullet.get(i).isEnabled())
				{
					Bullet.get(i).click();
					Thread.sleep(2*1000);
					//TODO: Card number extract from digital image on Manage Card page

					String CardNum = getText(getObject("mc.imgCardsNum",i));
					String CardApp= CardNum.substring(CardNum.length()-4);
					//String CardNum = getText(getObject("mc.lnkVCdetails",i+1));
					String eCardNumber= cardNum.substring(cardNum.length()-4);
					logMan.info("******CARDNUM*******"+eCardNumber);
					logMan.info("******CARDAPP*******"+CardApp);
					//Code For clicking View Card Details only when View Card link is present
					if(CardApp.equals(eCardNumber)){	
						Thread.sleep(2*1000);
						String getTextActivate= getText(getObject("ac.flagActivateCard"));
						Assert.assertEquals("Activate card flag display",isElementDisplayed(getObject("ac.flagActivateCard")),true);
					}
					else{
						logMan.info("Navigating to next card");
					}
				}
				else{
					logMan.error("Bullet is NOT enabled in ManageCard_NavigateTO function");

				}

			}
			logMessage("Activate flag displayed successfully in OR_ValActivateCard function.");
			injectMessageToCucumberReport("Activate flag displayed successfully");
			Allure.step("Activate flag displayed Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside OR_ValActivateCard " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Order Replace Activate display "+e.getMessage());
			Allure.step("Activate flag displayed Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
		
		
		
	}

	public void OR_ValChangeAddress(String CardNum,String Address){
		try {

			manageCardPg.MC_NavigateTo("ORDER REPLACEMENT",CardNum);//Click on View Card link on Manage Card page   
			//clickJS(getObject("vc.btnViewCardgoBack"));
			//manageCardPg.MC_NavigateTo("ORDER REPLACEMENT");//Click on Order Replacement link on Manage Card page
			Thread.sleep(2*1000);
			
             if(isElementDisplayed(getObject("or.btnOrderNow"))&&isElementDisplayed(getObject("or.lnkChange"))){
				
            	 String acCustAddress = getText(getObject("or.txtChangeAddress"));//TODO :Capture xpath of error message
     			Assert.assertEquals(Address,acCustAddress);
				logMessage("Order Replace page displayed successfully in OR_ValidateCustDetail function.");
				injectMessageToCucumberReport("Order Replace page displayed successfully");
				Allure.step("Order Replace page displayed Successfully ",Status.PASSED);
			}else{
				logError("Error Occured inside OR_ValChangeAddress " );
				injectWarningMessageToCucumberReport("Failure in Order Replace Customer detail ");
				Allure.step("Order Replace page NOT displayed Successfully ",Status.FAILED);
				appendScreenshotToCucumberReport();
			}
					} catch (Exception e) {
			logError("Error Occured inside OR_ChangeAddress " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Order Replace Change address "+e.getMessage());
			appendScreenshotToCucumberReport();
		}

	}

	public void OR_ValCustDetail(String cardNum){
		try {
			manageCardPg.MC_NavigateTo("ORDER REPLACEMENT",cardNum);//Click on View Card link on Manage Card page   
			//clickJS(getObject("vc.btnViewCardgoBack"));
			//manageCardPg.MC_NavigateTo("ORDER REPLACEMENT");//Click on Order Replacement link on Manage Card page
			Thread.sleep(2*1000);
			if(isElementDisplayed(getObject("or.btnOrderNow"))&&isElementDisplayed(getObject("or.lnkChange"))){
				
				clickJS(getObject("or.lnkChange"),"Change address button");				
				clickJS(getObject("or.ChangeAddressYes"),"Change address - Yes button");
				Thread.sleep(6*1000);
				//Assert.assertEquals("Change Address", getText(getObject("or.ChangeAddressTitle")));
				appendScreenshotToCucumberReport();
				logMessage("Order Replace page displayed successfully in OR_ValidateCustDetail function.");
				injectMessageToCucumberReport("Order Replace page displayed successfully");
				Allure.step("Order Replace page displayed Successfully ",Status.PASSED);
			}else{
				Assert.assertEquals("Change Address", getText(getObject("or.ChangeAddressTitle")));
				logError("Error Occured inside OR_ValidateCustDetail " );
				injectWarningMessageToCucumberReport("Failure in Order Replace Customer detail ");
				Allure.step("Order Replace page NOT displayed Successfully ",Status.FAILED);
				appendScreenshotToCucumberReport();
			}		

			/*//Validating Customer detail
			String ORcustDetail = getText(getObject("mc.topMenu"));//TODO:Chnage Address page
			System.out.println("*****Header" +ORcustDetail);
			Assert.assertEquals("Change Address", ORcustDetail);

			String acMsgText = getText(getObject("mc.topMenu"));//TODO:Message text 
			System.out.println("*****Header" +acMsgText);
			Assert.assertEquals("Change Address", acMsgText);*/

			
		} catch (Exception e) {
			logError("Error Occured inside OR_ValidateCustDetail " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Order Replace Customer detail "+e.getMessage());
			appendScreenshotToCucumberReport();
		}

	}

	public void OR_ValNewCardMsg(String cardNumber){
		try {
			List<WebElement> Bullet = driver.findElements(getObject("mc.dots"));
			//CODE TO NAVIGATE VIEW CARD DETAILS PAGE
			for(int i=0; i<Bullet.size();i++)
			{
				Bullet = driver.findElements(getObject("mc.dots"));

				if(Bullet.get(i).isEnabled())
				{
					Bullet.get(i).click();
					Thread.sleep(2*1000);
					//TODO: Card number extract from digital image on Manage Card page

					String CardNum = getText(getObject("mc.imgCardsNum",i));
					String CardApp= CardNum.substring(CardNum.length()-4);
					//String CardNum = getText(getObject("mc.lnkVCdetails",i+1));
					String eCardNumber= cardNumber.substring(cardNumber.length()-4);
					logInfo("******CARDNUM*******"+eCardNumber);
					logInfo("******CARDAPP*******"+CardApp);
					//Code For clicking View Card Details only when View Card link is present
					if(CardApp.equals(eCardNumber)){
						Thread.sleep(2*1000);
						String newCardMessage=getText(getObject("rt.newCardMessage"));
						Assert.assertEquals(getMessageText("newCardMessage","CARDS"), newCardMessage);
					}
					else{
						logInfo("Navigating to next card");
					}
				}
				else{
					logError("Bullet is NOT enabled in ManageCard_NavigateTO function");

				}
			}

			//manageCardPg.MC_NavigateTo("ORDER REPLACEMENT");//Click on Order Replacement link on Manage Card page
			clickJS(getObject("vc.btnViewCardconfirm"),"Yes button ");//TODO : YES button
			clickJS(getObject("vc.btnViewCardconfirm"),"Order button ");//TODO : Order button

			clickJS(getObject("vc.btnViewCardconfirm"),"Order button ");//TODO : MESSAGE INBOX 
			logMessage("Order Replace page displayed successfully in OR_VerifyNewCardMsg function.");
			injectMessageToCucumberReport("New Card message displayed successfully");
			Allure.step("New Card message displayed Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside OR_VerifyNewCardMsg " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in New Card message "+e.getMessage());
			Allure.step("New Card message NOT displayed Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

	public void OR_ValSuccesReplace(String cardNum){
		try {

			manageCardPg.MC_NavigateTo("ORDER REPLACEMENT",cardNum);//Click on View Card link on Manage Card page   
			//clickJS(getObject("vc.btnViewCardgoBack"));
			//manageCardPg.MC_NavigateTo("ORDER REPLACEMENT");//Click on Order Replacement link on Manage Card page
			Thread.sleep(2*1000);
			if(isElementDisplayed(getObject("or.btnOrderNow"))&&isElementDisplayed(getObject("or.lnkChange"))){
				Thread.sleep(2*1000);
				clickJS(getObject("or.btnOrderNow"),"Order Now button ");
				Thread.sleep(5*1000);
			}else{
				logError("Error Occured inside OR_VerifySuccesReplace " );
				injectWarningMessageToCucumberReport("Failure in Replacement Successful ");
				Allure.step("Replacement NOT Successful ",Status.FAILED);
				appendScreenshotToCucumberReport();
			}
			logMessage("Order Replace page displayed successfully in OR_VerifySuccesReplace function.");
			injectMessageToCucumberReport("Replacement Successful ");
			Allure.step("Replacement Successful ",Status.PASSED);
			// Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside OR_VerifySuccesReplace " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Replacement Successful "+e.getMessage());
			Allure.step("Replacement NOT Successful ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}
	
	public void OR_ValInvalidPIN(String cardNum){
		try {

			//Navigating to change address page
			manageCardPg.MC_NavigateTo("ORDER REPLACEMENT",cardNum);//Click on Order Replacement link on Manage Card page
			cardsFunctional.ExplicitWait("WITHIN", "ORDER REPLACEMENT");
			String ActualTextHeader = getText(getObject("mc.topMenu"));
			logInfo("*****Header" +ActualTextHeader);
			Assert.assertEquals("Manage Card", ActualTextHeader);//TODO : Success message
			logMessage("Order Replace page displayed successfully in OR_VerifySuccesReplace function.");
			injectMessageToCucumberReport("Replacement Successful ");
			Allure.step("Replacement Successful ",Status.PASSED);
			// Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside OR_VerifySuccesReplace " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Replacement Successful "+e.getMessage());
			Allure.step("Replacement NOT Successful ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}

		
		
	}

	//***************  COMMON FUNCTIONS ********************

	public void OR_ClickonOrdRep(){
		try {
			//manageCardPg.MC_NavigateTo("REPLACE DAMAGE CARD");
			/*if (isElementDisplayed(getObject("mc.dots"))) {
				List<WebElement> Bullets = driver.findElements(getObject("mc.dots"));
				for(int i=0; i<Bullets.size();i++){
					if(Bullets.get(i).isEnabled()){
						Bullets.get(i).click();
						Thread.sleep(2*1000);
						if(i==2){
							clickJS(getObject("mc.btnViewPIN"));
							//driver.findElement(getObject("mc.cardsSwipe")).click();
						}

						if(i==2){

							System.out.println("**INSIDE CLICK VIEW CARD***" + i+1);
							clickJS(getObject("mc.lnkVCdetails",i+1));
							Thread.sleep(2*1000);
							//driver.findElement(getObject("mc.cardsSwipe")).click();
						}

					}
				}
			}
			 */			logMessage("Click Order Replacement successfully in OR_ClickonOrdRep function.");
			 injectMessageToCucumberReport("Click Order Replacement launch successfully");
			 Allure.step("Click Order Replacement Launch Successfully ",Status.PASSED);
		}catch (Exception e) {
			logError("Error Occured inside OR_ClickonOrdRep" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Click Order Replacement "+e.getMessage());
			Allure.step("Click Order Replacement Launch NOT Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}
	}	

	public void OR_OrdRepPgDisplayed(){
		try {
			//clickJS(getObject("vc.btnViewCardgoBack"));
			isElementDisplayed(getObject("vc.lblViewCardheader"));
			String ActualTextHeader = getText(getObject("vc.lblViewCardheader"));
			logInfo("*****Header" +ActualTextHeader);
			Assert.assertEquals("Order Replacement", ActualTextHeader);
			logMessage("Order Replace page displayed successfully in OR_OrdRepPgDisplayed function.");
			injectMessageToCucumberReport("Order Replace page displayed successfully");
			Allure.step("Order Replace page displayed Successfully ",Status.PASSED);
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside OR_OrdRepPgDisplayed " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Order Replace page display "+e.getMessage());
			Allure.step("Order Replace page NOT displayed Successfully ",Status.FAILED);
			appendScreenshotToCucumberReport();
		}

	}

	/*public void OR_ExplicitWait(String ExplicitFlag,String sPageName){
		try {
			cardsFunctional.ExplicitWait(ExplicitFlag);
			switch (ExplicitFlag.toUpperCase()){
			case "EXPLICIT":
				cardsFunctional.IsPageDisplayed(sPageName);
				break;
			case "WITHIN":
				cardsFunctional.IsPageDisplayed(sPageName);

				break;
			case "EXACT":
				cardsFunctional.IsPageDisplayed(sPageName);

				break;
			}
			logMessage("Explicit wait successful in OR_ExplicitWait function.");
			injectMessageToCucumberReport("Explicit wait successful");
			step("Explicit wait Successful ");
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside OR_ExplicitWait " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Out of Explicit wait "+e.getMessage());
			appendScreenshotToCucumberReport();
		}

	}
	 */



}

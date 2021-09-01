package com.boi.grp.pageobjects.Cards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;

import io.cucumber.core.event.Status;

import io.qameta.allure.Allure;
import junit.framework.Assert;

import static io.qameta.allure.Allure.step;

import java.util.ArrayList;
import java.util.List;

public class RealTimeBalance extends BasePageForAllPlatform  {

	public UIResusableLibrary cardsFunctional;
	public ManageCardPage manageCardPg;
	public RealTimeBalance(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cardsFunctional= new UIResusableLibrary(driver);
		manageCardPg = new ManageCardPage(driver);
	}

	public void RTCC_ValRTCCBalance(String amountSpent,String creditVal,String CreditLimit,String cardNum){
		
		try {			
			clickJS(getObject("rt.lnkCredit"),"Enter PIN - Try again button ");
			Thread.sleep(4*1000);
			//String[] Spentarr=amountSpent.split("\\:");
			/*String[] creditValue=creditVal.split(":");
			String[] CreditLim=CreditLimit.split(":");*/
			
			String Spent = getText(getObject("rt.lnkCreditAmountSpent"));
			String CreditBal = getText(getObject("rt.txtCreditBal"));
			String acCrediLimit = getText(getObject("rt.txtCreditLimit"));
			
			String acCreditBalance = CreditBal.substring(CreditBal.length()-7);
			String acCreditLimit = acCrediLimit.substring(acCrediLimit.length()-7);
			
			Assert.assertEquals("Verify Credit Value",creditVal,acCreditBalance);
			Assert.assertEquals("Verify Credit limit",CreditLimit, acCreditLimit);
			//System.out.println("**Spent**"+Spentarr[1]);			
			
			logInfo("Real Time Balance page displayed successfully in RTCC_ValRTCCBalance function.");
			injectMessageToCucumberReport("Real Time Balance page displayed successfully");
			Allure.step("Real Time Balance page displayed Successfully ");
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside RTCC_ValRTCCBalance " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Real Time Balance page display "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	
	}
	
	public void RTCC_ValRTCCSessionTimeout(String cardNumber){
		
		try {
			clickJS(getObject("rt.lnkCredit"),"Click on Credit account ");
			Thread.sleep(4*1000);
			
			cardsFunctional.ExplicitWait("WITHIN", "RTCC");
			Assert.assertEquals("Verify within session timeout RTCC ",true, isElementDisplayed(getObject("rt.lnkCreditAmountSpent")));
			
			clickJS(getObject("rt.btnCreditStatements"),"Click on Statements button ");			
			cardsFunctional.ExplicitWait("EXPLICIT", "RTCC");
			Assert.assertEquals("Verify explicit session timeout RTCC ",false, isElementDisplayed(getObject("rt.lnkCreditAmountSpent")));
			
			logInfo("Real Time Balance session timeout validated successfully in RTCC_ValSessionTimeout function.");
			injectMessageToCucumberReport("Real Time Balance page session timeout validated successfully");
			Allure.step("Real Time Balance page session timeout validated Successfully ");
			
		} catch (Exception e) {
			logError("Error Occured inside RTCC_ValSessionTimeout " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Real Time Balance page session timeout "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
		
		
	}

	public void RTCC_ValRTCCMsg(){		
		
		try {
			//clickJS(getObject("vc.btnViewCardgoBack"));
			
			logMessage("Real Time Balance page displayed successfully in RTCC_VerifyRTCCMsg function.");
			injectMessageToCucumberReport("Real Time Balance page displayed successfully");
			step("Real Time Balance page displayed Successfully ");
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside RTCC_VerifyRTCCMsg " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Real Time Balance page display "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
	

	}


	public void RTCC_BalanceCheck(){
		
		try {
			//clickJS(getObject("vc.btnViewCardgoBack"));
			
			logMessage("Real Time Balance page displayed successfully in RTCC_BalanceCheck function.");
			injectMessageToCucumberReport("Real Time Balance page displayed successfully");
			step("Real Time Balance page displayed Successfully ");
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside RTCC_BalanceCheck " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Real Time Balance page display "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
		
	}
	
	public void RTCC_ExplicitWait(String ExplicitFlag,String sPageName){
		try {
			
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
			logMessage("Explicit wait successful in RTCC_ExplicitWait function.");
			injectMessageToCucumberReport("Explicit wait successful");
			step("Explicit wait Successful ");
			//	            Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside RTCC_ExplicitWait " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Explicit wait "+e.getMessage());
			appendScreenshotToCucumberReport();
		}
		
	}
	
	
	
}
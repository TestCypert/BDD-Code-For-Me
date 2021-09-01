package com.boi.grp.pageobjects.Profile;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.DataLibrary;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class MoreRegulatory extends BasePageForAllPlatform {

	public UIResusableLibrary cardsFunctional;
	public DataLibrary dataLibrary;
	public BasePageForAllPlatform basePage;

	By moreBtn = getObject("mr.moreBtn");
	By regulatoryContact = getObject("mr.regulatoryContact");
	By regulatoryFaq = getObject("mr.regulatoryFaq");
	By regulatoryAbout = getObject("mr.regulatoryAbout");
	By regulatorySecurity = getObject("mr.regulatorySecurity");
	By regulatoryCookies = getObject("mr.regulatoryCookies");
	By regulatoryTnc = getObject("mr.regulatoryTnc");
	By regulatoryOption = getObject("mr.regulatory");
	By regulatoryAccessibility = getObject("mr.regulatoryAccessibility");
	By regulatoryStatement = getObject("mr.regulatoryStatement");
	By nearBranch = getObject("mr.nearBranch");
	By lblRegNotice = getObject("mr.lblRegNotice");
	By lblRegDetail = getObject("mr.lblRegDetail");

	public MoreRegulatory(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cardsFunctional = new UIResusableLibrary(driver);
		dataLibrary = new DataLibrary();
		basePage = new BasePageForAllPlatform();
	}

	/*---------------------------------Start <MORE_Regulatory>----------------------------------------
	Function Name: MORE_Regulatory
	Purpose: To verify More Regulatory information from the footer section 
	Parameter:NA
	Author Name: CAF Automation 
	Create Date: 07-06-2021
	Modified Date| Modified By  |Modified purpose 
	 07/06/2021      C112253       Code completion
	-----------------------------------End <MORE_Regulatory>---------------------------------------
	 */

	public void MORE_Regulatory() {
		String message = "";
		try {
			clickJS(regulatoryOption,"click on regulatry link");
			if (isElementDisplayed(lblRegNotice,10,"validate reg notice heading") && isElementDisplayed(lblRegDetail,10,"Validate reg detail heading")) {
				message = getText(lblRegNotice);
				Assert.assertEquals("Validate registration notice heading","Registration notice", message);
				String messageRegDetail = getText(lblRegDetail);
				Assert.assertEquals("Validate registration details heading","Registration details", messageRegDetail);
			}

					
			logMessage("Verify More Regulatory successfully in MORE_Regulatory function");
			injectMessageToCucumberReport("Verify More Regulatory successfully");
         	appendScreenshotToCucumberReport();
			Allure.step("Verify More Regulatory Successfully ", Status.PASSED);

		} catch (Exception e) {
			logError("Error Occured inside MORE_Regulatory()" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify More Regulatory" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Verify More Regulatory NOT Successful" + Status.FAILED);
		}
	}
}

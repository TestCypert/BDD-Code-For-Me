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

public class MoreSection extends BasePageForAllPlatform {

	public UIResusableLibrary cardsFunctional;
	public DataLibrary dataLibrary;

	By listMore = getObject("ms.listMore");
	By btnContact = getObject("ms.btnContact");
	By btnPhoico = getObject("ms.btnPhoico");
	By btnCredServ = getObject("ms.btnCredServ");
	By contactNumber = getObject("ms.lblContactNumber");
	By availabilityTwentyFourSeven = getObject("ms.availablity");
	By republicOfIrelandTerm = getObject("ms.lblRoi");
	By northernIrelandTerm = getObject("ms.lblNigb");
	By creditCardRepublicOfIrelandTerm = getObject("ms.creditCardRepublicOfIrelandTerm");
	By creditCardNorthernIrelandTerm = getObject("ms.creditCardNorthernIrelandTerm");
	By phoneBankingOpeningHours = getObject("ms.phoneBankingOpeningHours");
	By creditCardOpeningHours = getObject("ms.creditCardOpeningHours");
	By alertPersonalInfo = getObject("ms.alertPersonalInfo");
	By twitter = getObject("ms.twitter");

	public MoreSection(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cardsFunctional = new UIResusableLibrary(driver);
		dataLibrary = new DataLibrary();
	}

	/*---------------------------------Start <MORE_MoreSection>----------------------------------------
	Function Name: MORE_MoreSection
	Purpose: Footer Info Reference Validation
	Parameter:NA
	Author Name: CAF Automation 
	 Create Date: 03-06-2021
	Modified Date| Modified By  |Modified purpose 
	 03/06/2021      C113757       Code completion
	-----------------------------------End <MORE_MoreSection>---------------------------------------
	 */

	public void MORE_MoreSection() {
		try {
			isElementDisplayed(listMore,10,"list more element show");
			clickJS(listMore,"click on more button");
			isElementDisplayed(btnContact,10,"Element is showing");
			clickJS(btnContact,"clickn on contact button");
			String contactHead = getText(contactNumber);
			Assert.assertEquals("Validate Contact number heading","Contact numbers", contactHead);
			String availabilityHead = getText(availabilityTwentyFourSeven);
			Assert.assertEquals("Validate availability heading","Emergency numbers available 24/7", availabilityHead);
			String republicOfIrelandHead = getText(republicOfIrelandTerm);
			Assert.assertEquals("Validate ROI heading","Republic of Ireland", republicOfIrelandHead);
			String northernIrelandHead = getText(northernIrelandTerm);
			Assert.assertEquals("Validate NIGB heading","Northern Ireland and Great Britain", northernIrelandHead);
			isElementDisplayed(btnPhoico,10,"click on photo icon btn");
			isElementDisplayed(btnCredServ,10,"click on credit service link");
			clickJS(btnCredServ,"click on credit service btn");
			String phoneBankingOpeningHoursHead = getText(phoneBankingOpeningHours);
			Assert.assertEquals("Validate 365 heading","365 phone banking opening hours", phoneBankingOpeningHoursHead);
			String creditCardOpeningHoursHead = getText(creditCardOpeningHours);
			Assert.assertEquals("Validate CC service heading","Credit card services opening hours", creditCardOpeningHoursHead);
			String creditCardrepublicOfIrelandHead = getText(creditCardRepublicOfIrelandTerm);
			Assert.assertEquals("Validate ROI Heading","Republic of Ireland", creditCardrepublicOfIrelandHead);
			String creditCardNorthernIrelandHead = getText(creditCardNorthernIrelandTerm);
			Assert.assertEquals("Validate NIGB heading","Northern Ireland and Great Britain", creditCardNorthernIrelandHead);
			String alertPersonalInfoHead = getText(alertPersonalInfo);
			Assert.assertEquals("VAlidate personal info heading","Never disclose your personal financial information on social media.",alertPersonalInfoHead);
			String twitterHead = getText(twitter);
			Assert.assertEquals("Validate Twitter heading","Twitter", twitterHead);
			isElementDisplayed(btnPhoico,10,"click on photo icon btn");

			logMessage("Verify More Section successfully in MORE_MoreSection function");
			injectMessageToCucumberReport("Verify More Section successfully");
          appendScreenshotToCucumberReport();
			Allure.step("Verify More Section Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside MORE_MoreSection()" + e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify More Section" + e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Verify More Section NOT Successful" + Status.FAILED);
		}
	}
}

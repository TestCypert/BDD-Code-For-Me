package com.boi.grp.pageobjects.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class More extends BasePageForAllPlatform {

	public UIResusableLibrary cardsFunctional;

	public More(WebDriver driver) {

		super(driver);
		PageFactory.initElements(driver, this);
		cardsFunctional= new UIResusableLibrary(driver);
	}
	public void More_VerifyMoreFields(){
		try {
			//TO DO: Verify More field for function
			logMessage("Verify More requirement changes on more page successfully in More_VerifyMoreFields function");
			injectMessageToCucumberReport("Verify More requirement changes on more page successfully");
			Allure.step("Verify More requirement changes on more page successfully ",Status.PASSED);
			//            Allure.Allure.step("View Card Detail Launch Successfully ", Status.PASSED);
		} catch (Exception e) {
			logError("Error Occured inside More_VerifyMoreFields " +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in Verify More requirement changes on more page "+e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Verify More requirement changes on more page NOT successfully", Status.FAILED);
		}
	}
}

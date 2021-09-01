package com.boi.grp.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class GoogleSearchPage extends BasePageForBrowsers {

	@FindBy(how = How.XPATH, using = "//input[@name='q']")
	WebElement SearchTextBox;

	@FindBy(how = How.ID, using = "introAgreeButton")
	WebElement agree;

	public GoogleSearchPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		driver.manage().window().maximize();
	}

	public void navigate(String URL) {
		try {
			this.driver.get(URL);
			logInfo("Navigation successful, URL=" + URL);
		} catch (Exception e) {
			logError("Navigation Failed, URL=" + URL + ", Error Description=" + e.getMessage());
		}
	}

	public void clickGoogleIagree(){
		try {
			wait.until(driver -> driver.switchTo().frame(0));
			agree.click();
			driver.switchTo().defaultContent();
			logInfo("Accepted I agree pop up");
		} catch (Exception e) {
			logError("Unable to Accept, I agree pop up, error ="+e.getMessage());
		}
	}

	public void searchStockPrices(String STOCKPRICE) {
		try {
			//setValue(SearchTextBox, STOCKPRICE);
			setValue(getWebElementFromOR("GooglePage_SearchTextBox_Xpath"),STOCKPRICE);
			EnterClick();
		} catch (Exception e) {
			logError("searchStockPrices failed for stock=" + STOCKPRICE + ", Error Description=" + e.getMessage());
		}
	}

	public void verifyStockPricesIsdisplayed(String StockName) {
		try {
			if (driver.findElements(By.xpath("//div[text()='1 day']")).size() == 1) {
				Assert.assertTrue(System.getProperty("StockName") + " Stock Price is displayed", true);
				appendScreenshotToCucumberReport();
				logInfo("Stock Price is displayed");
			} else {
				logError("Stock Price is not displayed");
				injectErrorToCucumberReport("Stock Price is Not displayed for stock=" + StockName);
				appendScreenshotToCucumberReport();
				Assert.assertTrue("Stock Price is Not displayed for stock=" + StockName, false);

			}
		} catch (Exception e) {
			injectErrorToCucumberReport("Stock Price is Not displayed for stock=" + StockName);
			appendScreenshotToCucumberReport();
			logError("verifyStockPricesIsdisplayed failed for stock=" + StockName + ", Error Description="
					+ e.getMessage());
		}
	}

	public void closeSearch()
	{
		try {
			driver.close();
			driver.quit();
			logInfo("Browser Closed Successfully");
		} 
		catch (Exception e) 
		{
			logError("Error Occured while closing the browsers, Error Description=" + e.getMessage());
		}
	}
}

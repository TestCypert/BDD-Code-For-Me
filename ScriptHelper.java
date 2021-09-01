package com.cognizant.craft;

import businesscomponents.CommonData;
import com.aventstack.extentreports.ExtentTest;
import com.cognizant.framework.selenium.*;
import org.openqa.selenium.WebDriver;

import com.cognizant.framework.CraftDataTable;

/**
 * Wrapper class for common framework objects, to be used across the entire test
 * case and dependent libraries
 * 
 * @author Cognizant
 */
public class ScriptHelper {

	private final CraftDataTable dataTable;
	private final SeleniumReport report;
	private final ExtentTest test;

	private CraftDriver craftDriver;
	private WebDriverUtil driverUtil;
	private SeleniumTestParameters testParameters;
	public CommonData commonData;

	/**
	 * Constructor to initialize all the objects wrapped by the
	 * {@link ScriptHelper} class
	 * 
	 * @param dataTable
	 *            The {@link CraftDataTable} object
	 * @param report
	 *            The {@link SeleniumReport} object
	 * @param craftDriver
	 *            The {@link WebDriver} object
	 * @param driverUtil
	 *            The {@link WebDriverUtil} object
	 * @param testParameters
	 *            The {@link SeleniumTestParameters} object
	 */

	public ScriptHelper(CraftDataTable dataTable, SeleniumReport report, CraftDriver craftDriver,
			WebDriverUtil driverUtil, SeleniumTestParameters testParameters, ExtentTest test) {
		this.dataTable = dataTable;
		this.report = report;
		this.craftDriver = craftDriver;
		this.driverUtil = driverUtil;
		this.testParameters = testParameters;
		this.test = test;

		commonData = new CommonData();
	}

	/**
	 * Function to get the {@link CraftDataTable} object
	 * 
	 * @return The {@link CraftDataTable} object
	 */
	public CraftDataTable getDataTable() {
		return dataTable;
	}

	/**
	 * Function to get the {@link SeleniumReport} object
	 * 
	 * @return The {@link SeleniumReport} object
	 */
	public SeleniumReport getReport() {
		return report;
	}


	/**
	 * Function to get the {@link SeleniumReport} object
	 *
	 * @return The {@link SeleniumReport} object
	 */
	public ExtentTest getReportTest() {
		return test;
	}


	/**
	 * Function to get the {@link CraftDriver} object
	 * 
	 * @return The {@link CraftDriver} object
	 */
	public CraftDriver getcraftDriver() {
		return craftDriver;
	}

	/**
	 * Function to get the {@link WebDriverUtil} object
	 * 
	 * @return The {@link WebDriverUtil} object
	 */
	public WebDriverUtil getDriverUtil() {
		return driverUtil;
	}

	/**
	 * Function to get the {@link SeleniumTestParameters} object
	 * 
	 * @return The {@link SeleniumTestParameters} object
	 */
	public SeleniumTestParameters getTestParameters() {
		return testParameters;
	}

}
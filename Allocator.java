package runners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cognizant.framework.*;
import com.cognizant.framework.selenium.*;
import org.openqa.selenium.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class to manage the batch execution of test scripts within the framework
 * 
 * @author Cognizant
 */
public class Allocator {
	public static long timeout = 5000;
	private FrameworkParameters frameworkParameters = FrameworkParameters
			.getInstance();
	private Properties properties;
	private Properties mobileProperties;
	private ResultSummaryManager resultSummaryManager = ResultSummaryManager
			.getInstance();
	
	//Variable to handle object map
	private Properties objectProperties;

	//Variables to Handle Extent Report
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static String strMobilePlatform ="";
	/**
	 * The entry point of the test batch execution <br>
	 * Exits with a value of 0 if the test passes and 1 if the test fails
	 * 
	 * @param args
	 *            Command line arguments to the Allocator (Not applicable)
	 */
	public static void main(String[] args) {
		Allocator allocator = new Allocator();
		allocator.driveBatchExecution();
	}

	private void driveBatchExecution() {
		resultSummaryManager.setRelativePath();
		properties = Settings.getInstance();
		mobileProperties = Settings.getMobilePropertiesInstance();
		objectProperties= Settings.getObjectPropertiesInstance();
		
		String runConfiguration;
		if (System.getProperty("RunConfiguration") != null) {
			runConfiguration = System.getProperty("RunConfiguration");
		} else {
			runConfiguration = properties.getProperty("RunConfiguration");
		}
		resultSummaryManager.initializeTestBatch(runConfiguration);

		int nThreads;
		if (System.getProperty("threads") != null) {
			nThreads = Integer.parseInt(System.getProperty("threads"));
		} else {
			nThreads = Integer.parseInt(properties
					.getProperty("NumberOfThreads"));
		}

		resultSummaryManager.initializeSummaryReport(nThreads);

		resultSummaryManager.setupErrorLog();
		System.out.println("Test execution starts in '"+nThreads+"' number of threads");

		int testBatchStatus = executeTestBatch(nThreads);

		resultSummaryManager.wrapUp(false);

		//ExtentReports will push/write everything to the document
		extent.flush();

		resultSummaryManager.manageJenkinsReportsFolder();

		resultSummaryManager.launchResultSummary();

		System.exit(testBatchStatus);
	}

	/***** When working with SeeTest/Perfecto Parellel *****/
	/*
	 * private int executeTestBatch(int nThreads) { List<SeleniumTestParameters>
	 * testInstancesToRun = getRunInfo(frameworkParameters
	 * .getRunConfiguration()); ExecutorService parallelExecutor = Executors
	 * .newFixedThreadPool(nThreads); ParallelRunner testRunner = null; int i=0;
	 * while(i<testInstancesToRun.size()) { System.out.println("I:"+i); int
	 * size=i+nThreads; //System.out.println("First For"); for(int
	 * currentTestInstance
	 * =size-nThreads;currentTestInstance<size;currentTestInstance++) {
	 * testRunner = new ParallelRunner(
	 * testInstancesToRun.get(currentTestInstance));
	 * parallelExecutor.execute(testRunner);
	 * 
	 * if(frameworkParameters.getStopExecution()) { break; } }
	 * 
	 * parallelExecutor.shutdown(); while(!parallelExecutor.isTerminated()) {
	 * try { Thread.sleep(3000); } catch (InterruptedException e) {
	 * e.printStackTrace(); } } System.out.println("Waitng for thread to stop");
	 * i=size; } if (testRunner == null) { return 0; // All tests flagged as
	 * "No" in the Run Manager } else { return testRunner.getTestBatchStatus();
	 * } }
	 */

	private int executeTestBatch(int nThreads) {
		List<SeleniumTestParameters> testInstancesToRun = getRunInfo(frameworkParameters
				.getRunConfiguration());
		ExecutorService parallelExecutor = Executors
				.newFixedThreadPool(nThreads);
		ParallelRunner testRunner = null;
		for (int currentTestInstance = 0; currentTestInstance < testInstancesToRun
				.size(); currentTestInstance++) {
			testRunner = new ParallelRunner(
					testInstancesToRun.get(currentTestInstance));
			parallelExecutor.execute(testRunner);

			if (frameworkParameters.getStopExecution()) {
				break;
			}
		}

		parallelExecutor.shutdown();
		while (!parallelExecutor.isTerminated()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (testRunner == null) {
			return 0; // All tests flagged as "No" in the Run Manager
		} else {
			return testRunner.getTestBatchStatus();
		}
	}

	private List<SeleniumTestParameters> getRunInfo(String sheetName) {
		ExcelDataAccessforxlsm runManagerAccess = new ExcelDataAccessforxlsm(
				frameworkParameters.getRelativePath() + Util.getFileSeparator()
						+ "src" + Util.getFileSeparator() + "main"
						+ Util.getFileSeparator() + "resources", "Run Manager");
		runManagerAccess.setDatasheetName(sheetName);

		runManagerAccess.setDatasheetName(sheetName);
		List<SeleniumTestParameters> testInstancesToRun = new ArrayList<SeleniumTestParameters>();
		String[] keys = { "Execute", "TestScenario", "TestCase",
				"TestInstance", "Description", "IterationMode",
				"StartIteration", "EndIteration", "QC_TestCase", "TestConfigurationID" };
		List<Map<String, String>> values = runManagerAccess.getValues(keys);

		for (int currentTestInstance = 0; currentTestInstance < values.size(); currentTestInstance++) {

			Map<String, String> row = values.get(currentTestInstance);
			String executeFlag = row.get("Execute");

			if (executeFlag.equalsIgnoreCase("Yes")) {
				String currentScenario = row.get("TestScenario");
				String currentTestcase = row.get("TestCase");
				SeleniumTestParameters testParameters = new SeleniumTestParameters(
						currentScenario, currentTestcase);
				testParameters
						.setCurrentTestDescription(row.get("Description"));
				testParameters.setCurrentTestInstance("Instance"
						+ row.get("TestInstance"));

				String iterationMode = row.get("IterationMode");
				if (!iterationMode.equals("")) {
					testParameters.setIterationMode(IterationOptions
							.valueOf(iterationMode));
				} else {
					testParameters
							.setIterationMode(IterationOptions.RUN_ALL_ITERATIONS);
				}

				String startIteration = row.get("StartIteration");
				if (!startIteration.equals("")) {
					testParameters.setStartIteration(Integer
							.parseInt(startIteration));
				}
				String endIteration = row.get("EndIteration");
				if (!endIteration.equals("")) {
					testParameters.setEndIteration(Integer
							.parseInt(endIteration));
				}

				//Fetching QC test case name for ALM Upload
				String currentTestcaseQC = row.get("QC_TestCase");
				if (!currentTestcaseQC.equals("")) {
					testParameters.setCurrentTestcaseQC(currentTestcaseQC);
				}

				String testConfig = row.get("TestConfigurationID");
				if (!"".equals(testConfig)) {
					getTestConfigValues(runManagerAccess, "TestConfigurations",
							testConfig, testParameters);
					strMobilePlatform = testParameters.getMobileExecutionPlatform().toString();
//					if(strMobilePlatform.equals("IOS")){
//						objectProperties= Settings.getIOSobjectPropertiesInstance();
//					}
				}

				testInstancesToRun.add(testParameters);
				runManagerAccess.setDatasheetName(sheetName);
			}
		}
		return testInstancesToRun;
	}

	private void getTestConfigValues(ExcelDataAccessforxlsm runManagerAccess,
			String sheetName, String testConfigName,
			SeleniumTestParameters testParameters) {

		runManagerAccess.setDatasheetName(sheetName);
		int rowNum = runManagerAccess.getRowNum(testConfigName, 0, 1);

		String[] keys = { "TestConfigurationID", "ExecutionMode",
				"MobileToolName", "MobileExecutionPlatform", "MobileOSVersion",
				"DeviceName", "Browser", "BrowserVersion", "Platform",
				"SeeTestPort", "TestingPlatform"};
		Map<String, String> values = runManagerAccess.getValuesForSpecificRow(
				keys, rowNum);

		String executionMode = values.get("ExecutionMode");
		if (!"".equals(executionMode)) {
			testParameters.setExecutionMode(ExecutionMode
					.valueOf(executionMode));
		} else {
			testParameters.setExecutionMode(ExecutionMode.valueOf(properties
					.getProperty("DefaultExecutionMode")));
		}

		String toolName = values.get("MobileToolName");
		if (!"".equals(toolName)) {
			testParameters.setMobileToolName(MobileToolName.valueOf(toolName));
		} else {
			testParameters.setMobileToolName(MobileToolName
					.valueOf(mobileProperties
							.getProperty("DefaultMobileToolName")));
		}

		String executionPlatform = values.get("MobileExecutionPlatform");
		if (!"".equals(executionPlatform)) {
			testParameters.setMobileExecutionPlatform(MobileExecutionPlatform
					.valueOf(executionPlatform));
		} else {
			testParameters.setMobileExecutionPlatform(MobileExecutionPlatform
					.valueOf(mobileProperties
							.getProperty("DefaultMobileExecutionPlatform")));
		}

		String mobileOSVersion = values.get("MobileOSVersion");
		if (!"".equals(mobileOSVersion)) {
			testParameters.setmobileOSVersion(mobileOSVersion);
		}

		String deviceName = values.get("DeviceName");
		if (!"".equals(deviceName)) {
			testParameters.setDeviceName(deviceName);
		}

		String browser = values.get("Browser");
		if (!"".equals(browser)) {
			testParameters.setBrowser(Browser.valueOf(browser));
		} else {
			testParameters.setBrowser(Browser.valueOf(properties
					.getProperty("DefaultBrowser")));
		}

		String browserVersion = values.get("BrowserVersion");
		if (!"".equals(browserVersion)) {
			testParameters.setBrowserVersion(browserVersion);
		}

		String platform = values.get("Platform");
		if (!"".equals(platform)) {
			testParameters.setPlatform(Platform.valueOf(platform));
		} else {
			testParameters.setPlatform(Platform.valueOf(properties
					.getProperty("DefaultPlatform")));
		}

		String seeTestPort = values.get("SeeTestPort");
		if (!"".equals(seeTestPort)) {
			testParameters.setSeeTestPort(seeTestPort);
		} else {
			testParameters.setSeeTestPort(mobileProperties
					.getProperty("SeeTestDefaultPort"));
		}

		String testingPlatform = values.get("TestingPlatform");
		if (!"".equals(testingPlatform)) {
			testParameters.setTestingPlatform(testingPlatform);
		}else{
			testParameters.setTestingPlatform("");
		}

	}

}
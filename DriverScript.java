package com.cognizant.craft;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.cognizant.framework.*;
import com.cognizant.framework.ReportThemeFactory.Theme_CRAFT;
import com.cognizant.framework.selenium.*;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import runners.Allocator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

/*import com.experitest.client.Client;
import com.experitest.selenium.MobileWebDriver;*/

/**
 * Driver script class which encapsulates the core logic of the framework
 *
 * @author Cognizant
 */
public class DriverScript {
    private List<String> businessFlowData;
    private int currentIteration, currentSubIteration;
    private Date startTime, endTime;
    private String executionTime;

    private CraftDataTable dataTable;
    private ReportSettings reportSettings;
    private SeleniumReport report;
    private CraftDriver driver;
    //private Client client;

    private WebDriverUtil driverUtil;
    private ScriptHelper scriptHelper;

    private Properties properties;
    private Properties mobileProperties;
    private final FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();

    private Boolean linkScreenshotsToTestLog = true;

    private String seeTestResultPath;
    private final SeleniumTestParameters testParameters;
    private String reportPath;
    private ExtentTest test;

    /**
     * DriverScript constructor
     *
     * @param testParameters A {@link SeleniumTestParameters} object
     */
    public DriverScript(SeleniumTestParameters testParameters) {
        this.testParameters = testParameters;
    }

    /**
     * Function to configure the linking of screenshots to the corresponding
     * test log
     *
     * @param linkScreenshotsToTestLog Boolean variable indicating whether screenshots should be
     *                                 linked to the corresponding test log
     */
    public void setLinkScreenshotsToTestLog(Boolean linkScreenshotsToTestLog) {
        this.linkScreenshotsToTestLog = linkScreenshotsToTestLog;
    }

    /**
     * Function to get the name of the test report
     *
     * @return The test report name
     */
    public String getReportName() {
        return reportSettings.getReportName();
    }

    /**
     * Function to get the status of the test case executed
     *
     * @return The test status
     */
    public String getTestStatus() {
        return report.getTestStatus();
    }

    /**
     * Function to get the decription of any failure that may occur during the
     * script execution
     *
     * @return The failure description (relevant only if the test fails)
     */
    public String getFailureDescription() {
        return report.getFailureDescription();
    }

    /**
     * Function to get the execution time for the test case
     *
     * @return The test execution time
     */
    public String getExecutionTime() {
        return executionTime;
    }

    /**
     * Function to execute the given test case
     */
    public void driveTestExecution() throws Exception {
        startUp();
        initializeTestIterations();
        initializeWebDriver();
        initializeTestReport();

        initializeDatatable();
        System.out.println("Execution triggered for '"+testParameters.getCurrentTestcase()+"'");
        executeCraft();

        quitWebDriver();
        wrapUp();
        System.out.println("Execution completed for '"+testParameters.getCurrentTestcase()+"'");
    }

    private void executeCraft() {
        initializeTestScript();
        executeCRAFTTestIterations();
    }

    private void startUp() {
        startTime = Util.getCurrentTime();

        properties = Settings.getInstance();
        mobileProperties = Settings.getMobilePropertiesInstance();

        setDefaultTestParameters();
    }

    private void setDefaultTestParameters() {
        if (testParameters.getIterationMode() == null) {
            testParameters.setIterationMode(IterationOptions.RUN_ALL_ITERATIONS);
        }

        if (testParameters.getExecutionMode() == null) {
            testParameters.setExecutionMode(ExecutionMode.valueOf(properties.getProperty("DefaultExecutionMode")));

        }

        if (testParameters.getMobileExecutionPlatform() == null) {
            testParameters.setMobileExecutionPlatform(
                    MobileExecutionPlatform.valueOf(mobileProperties.getProperty("DefaultMobileExecutionPlatform")));
        }

        if (testParameters.getMobileToolName() == null) {
            testParameters
                    .setMobileToolName(MobileToolName.valueOf(mobileProperties.getProperty("DefaultMobileToolName")));
        }

        if (testParameters.getDeviceName() == null) {
            testParameters.setDeviceName(mobileProperties.getProperty("DefaultDevice"));
        }

        if (testParameters.getBrowser() == null) {
            testParameters.setBrowser(Browser.valueOf(properties.getProperty("DefaultBrowser")));
        }

        if (testParameters.getSeeTestPort() == null) {
            testParameters.setSeeTestPort(mobileProperties
                    .getProperty("SeeTestDefaultPort"));
        }

        testParameters.setInstallApplication(
                Boolean.parseBoolean(mobileProperties.getProperty("InstallApplicationInDevice")));
        testParameters.setSeeTestPort(mobileProperties.getProperty("SeeTestDefaultPort"));

    }

    private void initializeTestIterations() {
        switch (testParameters.getIterationMode()) {
            case RUN_ALL_ITERATIONS:
                int nIterations = getNumberOfIterations();
                testParameters.setEndIteration(nIterations);

                currentIteration = 1;
                break;

            case RUN_ONE_ITERATION_ONLY:
                currentIteration = 1;
                break;

            case RUN_RANGE_OF_ITERATIONS:
                if (testParameters.getStartIteration() > testParameters.getEndIteration()) {
                    throw new FrameworkException("Error", "StartIteration cannot be greater than EndIteration!");
                }
                currentIteration = testParameters.getStartIteration();
                break;

            default:
                throw new FrameworkException("Unhandled Iteration Mode!");
        }
    }

    private int getNumberOfIterations() {
        String datatablePath = frameworkParameters.getRelativePath() + Util.getFileSeparator() + "src"
                + Util.getFileSeparator() + "main" + Util.getFileSeparator() + "resources" + Util.getFileSeparator()
                + "Datatables";
        ExcelDataAccess testDataAccess = new ExcelDataAccess(datatablePath, testParameters.getCurrentScenario());
        testDataAccess.setDatasheetName(properties.getProperty("DefaultDataSheet"));

        int startRowNum = testDataAccess.getRowNum(testParameters.getCurrentTestcase(), 0);
        int nTestcaseRows = testDataAccess.getRowCount(testParameters.getCurrentTestcase(), 0, startRowNum);
        int nSubIterations = testDataAccess.getRowCount("1", 1, startRowNum); // Assumption:
        // Every
        // test
        // case
        // will
        // have
        // at
        // least
        // one
        // iteration
        return nTestcaseRows / nSubIterations;

    }

    @SuppressWarnings("rawtypes")
    private void initializeWebDriver() {

        switch (testParameters.getExecutionMode()) {

            case LOCAL:
                WebDriver webDriver = WebDriverFactory.getWebDriver(testParameters.getBrowser());
                driver = new CraftDriver(webDriver);
                driver.setTestParameters(testParameters);
                WaitPageLoad();
                break;

            case REMOTE:
                WebDriver remoteWebDriver = WebDriverFactory.getRemoteWebDriver(testParameters.getBrowser(),
                        properties.getProperty("RemoteUrl"));
                driver = new CraftDriver(remoteWebDriver);
                driver.setTestParameters(testParameters);
                WaitPageLoad();
                break;

            case LOCAL_EMULATED_DEVICE:
                testParameters.setBrowser(Browser.CHROME);// mobile emulation
                // supported only
                // on chrome
                WebDriver localEmulatedDriver = WebDriverFactory.getEmulatedWebDriver(testParameters.getDeviceName());
                driver = new CraftDriver(localEmulatedDriver);
                driver.setTestParameters(testParameters);
                WaitPageLoad();
                break;

            case REMOTE_EMULATED_DEVICE:
                testParameters.setBrowser(Browser.CHROME);// mobile emulation
                // supported only
                // on chrome
                WebDriver remoteEmulatedDriver = WebDriverFactory.getEmulatedRemoteWebDriver(testParameters.getDeviceName(),
                        properties.getProperty("RemoteUrl"));
                driver = new CraftDriver(remoteEmulatedDriver);
                driver.setTestParameters(testParameters);
                break;

            case GRID:
                WebDriver remoteGridDriver = WebDriverFactory.getRemoteWebDriver(testParameters.getBrowser(),
                        testParameters.getBrowserVersion(), testParameters.getPlatform(),
                        properties.getProperty("RemoteUrl"));
                driver = new CraftDriver(remoteGridDriver);
                driver.setTestParameters(testParameters);
                WaitPageLoad();
                break;

            case MOBILE:
                if ((testParameters.getMobileToolName().equals(MobileToolName.APPIUM))) {
                    WebDriver appiumDriver = AppiumDriverFactory.getAppiumDriver(
                            testParameters.getMobileExecutionPlatform(),
                            mobileProperties.getProperty("DeviceName"),
                            testParameters.getMobileOSVersion(),
                            mobileProperties.getProperty("UDID"),
                            mobileProperties.getProperty("AppiumURL"));
                    driver = new CraftDriver(appiumDriver);
                    driver.setTestParameters(testParameters);
                } else if (testParameters.getMobileToolName().equals(MobileToolName.REMOTE_WEBDRIVER)) {
                    WebDriver remoteAppiumDriver = AppiumDriverFactory.getAppiumRemoteWebDriver(
                            testParameters.getMobileExecutionPlatform(),
                            mobileProperties.getProperty("DeviceName"),
                            testParameters.getMobileOSVersion(),
                            mobileProperties.getProperty("UDID"),
                            mobileProperties.getProperty("AppiumURL"));
                    driver = new CraftDriver(remoteAppiumDriver);
                    driver.setTestParameters(testParameters);
                }
                break;

            case SEETEST:
                testParameters.setMobileToolName(MobileToolName.DEFAULT);
            /*MobileWebDriver seeTestDriver = SeeTestDriverFactory.getSeeTestDriver(
                    mobileProperties.getProperty("SeeTestHost", "localhost"),
					Integer.parseInt(testParameters.getSeeTestPort()),
					mobileProperties.getProperty("SeeTestProjectBaseDirectory"),
					mobileProperties.getProperty("SeeTestReportType", "xml"), "report", "Test Name from Driver Init",
					testParameters.getMobileExecutionPlatform(),
					mobileProperties.getProperty("SeeTestAndroidApplicationName"),
					mobileProperties.getProperty("SeeTestiOSApplicationName"),
					mobileProperties.getProperty("SeeTestAndroidWebApplicationName"),
					mobileProperties.getProperty("SeeTestiOSWebApplicationName"), testParameters.getDeviceName());
			driver = new CraftDriver(seeTestDriver);
			client = seeTestDriver.client;
			driver.setSeeTestdriver(seeTestDriver);
			driver.setTestParameters(testParameters);*/
                break;

            case PERFECTO:

                if (testParameters.getMobileToolName().equals(MobileToolName.APPIUM)) {
                    WebDriver appiumPerfectoDriver = PerfectoDriverFactory.getPerfectoAppiumDriver(
                            testParameters.getMobileExecutionPlatform(), testParameters.getDeviceName(),
                            mobileProperties.getProperty("PerfectoHost"), testParameters);
                    driver = new CraftDriver(appiumPerfectoDriver);
                    driver.setTestParameters(testParameters);

                } else if (testParameters.getMobileToolName().equals(MobileToolName.REMOTE_WEBDRIVER)) {
                    WebDriver remotePerfectoDriver = PerfectoDriverFactory.getPerfectoRemoteWebDriver(
                            testParameters.getMobileExecutionPlatform(), testParameters.getDeviceName(),
                            mobileProperties.getProperty("PerfectoHost"), testParameters.getBrowser());
                    driver = new CraftDriver(remotePerfectoDriver);
                    driver.setTestParameters(testParameters);
                }

                break;

            case SAUCELABS:

                if (testParameters.getMobileToolName().equals(MobileToolName.APPIUM)) {
                    AppiumDriver appiumSauceDriver = SauceLabsDriverFactory.getSauceAppiumDriver(
                            testParameters.getMobileExecutionPlatform(), testParameters.getDeviceName(),
                            mobileProperties.getProperty("SauceHost"), testParameters);
                    driver = new CraftDriver(appiumSauceDriver);
                    driver.setTestParameters(testParameters);

                } else if (testParameters.getMobileToolName().equals(MobileToolName.REMOTE_WEBDRIVER)) {
                    WebDriver remoteSauceDriver = SauceLabsDriverFactory.getSauceRemoteWebDriver(
                            mobileProperties.getProperty("SauceHost"), testParameters.getBrowser(),
                            testParameters.getBrowserVersion(), testParameters.getPlatform(), testParameters);

                    driver = new CraftDriver(remoteSauceDriver);
                    driver.setTestParameters(testParameters);
                }
                break;

            case MINT:

                testParameters.setMobileToolName(MobileToolName.APPIUM);
                WebDriver mintAppiumDriver = MintDriverFactory.getmintAppiumDriver(
                        testParameters.getMobileExecutionPlatform(), testParameters.getDeviceName(),
                        mobileProperties.getProperty("mintHost"), testParameters.getMobileOSVersion());
                driver = new CraftDriver(mintAppiumDriver);
                driver.setTestParameters(testParameters);
                break;

            case BROWSERSTACK:
                if (testParameters.getMobileToolName().equals(MobileToolName.REMOTE_WEBDRIVER)) {
                    WebDriver browserstackRemoteDrivermobile = BrowserStackDriverFactory
                            .getBrowserStackRemoteWebDriverMobile(testParameters.getMobileExecutionPlatform(),
                                    testParameters.getDeviceName(), mobileProperties.getProperty("BrowserStackHost"),
                                    testParameters);
                    driver = new CraftDriver(browserstackRemoteDrivermobile);
                    driver.setTestParameters(testParameters);

                } else if (testParameters.getMobileToolName().equals(MobileToolName.DEFAULT)) {
                    WebDriver browserstackRemoteDriver = BrowserStackDriverFactory.getBrowserStackRemoteWebDriver(
                            mobileProperties.getProperty("BrowserStackHost"), testParameters.getBrowser(),
                            testParameters.getBrowserVersion(), testParameters.getPlatform(), testParameters);

                    driver = new CraftDriver(browserstackRemoteDriver);
                    driver.setTestParameters(testParameters);
                }

                break;

            case MOBILECENTRE:
                if ((testParameters.getMobileToolName().equals(MobileToolName.APPIUM))) {
                    System.out.println("ToolName: "+testParameters.getMobileToolName());
                    if((testParameters.getBrowserVersion())!=null)
                    {
                        System.out.println("Execution : Performed on mobile devices on thread ");
                        System.out.println(testParameters.getDeviceName()+" "+testParameters.getBrowserVersion());
                        WebDriver mobileCentreDriver = HPMobileCenterDriverFactory.getMobileCenterAppiumDriverThread(
                                testParameters.getMobileExecutionPlatform(),
                                testParameters.getDeviceName(),
                                mobileProperties.getProperty("MobileCenterHost"),
                                testParameters.getBrowserVersion());// UDID
                        driver = new CraftDriver(mobileCentreDriver);
                        driver.setTestParameters(testParameters);
                    }
                    else
                    {
                        System.out.println("Execution : Performed on mobile device");
                        WebDriver mobileCentreDriver = HPMobileCenterDriverFactory.getMobileCenterAppiumDriver(
                                testParameters.getMobileExecutionPlatform(),
                                mobileProperties.getProperty("DeviceName"),
                                testParameters.getMobileOSVersion(),
                                mobileProperties.getProperty("MobileCenterHost"),
                                mobileProperties.getProperty("UDID"));
                        driver = new CraftDriver(mobileCentreDriver);
                        driver.setTestParameters(testParameters);}
                }
                else if (testParameters.getMobileToolName().equals(MobileToolName.REMOTE_WEBDRIVER))
                {
                    System.out.println("ToolName: "+testParameters.getMobileToolName());
                    if((testParameters.getBrowserVersion())!= null){
                        System.out.println("Execution : Performed on mobile browser on thread ");
                        System.out.println(testParameters.getDeviceName()+" "+testParameters.getBrowserVersion());
                        WebDriver mobileCentreDriver = HPMobileCenterDriverFactory.getMobileCenterAndroidDriverThread(
                                testParameters.getMobileExecutionPlatform(),
                                testParameters.getDeviceName(),
                                mobileProperties.getProperty("MobileCenterHost"),
                                testParameters.getBrowserVersion());//udid
                        driver = new CraftDriver(mobileCentreDriver);
                        driver.setTestParameters(testParameters);}
                    else {
                        System.out.println("Execution : Performed on mobile browser device ");
                        WebDriver mobileCentreDriver = HPMobileCenterDriverFactory.getMobileCenterAndroidDriver(
                                testParameters.getMobileExecutionPlatform(),
                                testParameters.getDeviceName(),
                                testParameters.getMobileOSVersion(),
                                mobileProperties.getProperty("MobileCenterHost"),
                                testParameters.getBrowserVersion());
                        driver = new CraftDriver(mobileCentreDriver);
                        driver.setTestParameters(testParameters);}

                }
                break;



            case MOBILELABS:

                testParameters.getMobileToolName().equals(MobileToolName.APPIUM);
                WebDriver mobilelabsDriver = MobileLabsDriverFactory.getMobileLabsDriver(
                        testParameters.getMobileExecutionPlatform(), testParameters.getDeviceName(),
                        mobileProperties.getProperty("AppiumURL"), testParameters.getMobileOSVersion());
                driver = new CraftDriver(mobilelabsDriver);
                driver.setTestParameters(testParameters);

                break;

            default:
                throw new FrameworkException("Unhandled Execution Mode!");
        }
        implicitWaitForDriver();

    }

    private void implicitWaitForDriver() {
        long objectSyncTimeout = Long.parseLong(properties.get("ObjectSyncTimeout").toString());
        driver.manage().timeouts().implicitlyWait(objectSyncTimeout, TimeUnit.SECONDS);
    }

    private void WaitPageLoad() {
        long pageLoadTimeout = Long.parseLong(properties.get("PageLoadTimeout").toString());
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    private void initializeTestReport() {
        initializeReportSettings();
        ReportTheme reportTheme = ReportThemeFactory
                .getReportsTheme(Theme_CRAFT.valueOf(properties.getProperty("ReportsTheme")));

        //To initialize extent report
        initializeExtentReport();

        report = new SeleniumReport(reportSettings, reportTheme, testParameters, test);

        report.initialize();
        report.setDriver(driver);
        /*if(testParameters.getExecutionMode().equals(ExecutionMode.SEETEST)){
            report.setClient(client);
		}*/
        report.initializeTestLog();
        createTestLogHeader();
    }

    private void initializeExtentReport() {
        test = Allocator.extent.createTest(testParameters.getCurrentTestcase(), testParameters.getCurrentTestDescription());
    }

    private void initializeReportSettings() {
        if (System.getProperty("ReportPath") != null) {
            reportPath = System.getProperty("ReportPath");
        } else {
            reportPath = TimeStamp.getInstance();
        }

        reportSettings = new ReportSettings(reportPath, testParameters.getCurrentScenario() + "_"
                + testParameters.getCurrentTestcase() + "_" + testParameters.getCurrentTestInstance());

        reportSettings.setDateFormatString(properties.getProperty("DateFormatString"));
        reportSettings.setLogLevel(Integer.parseInt(properties.getProperty("LogLevel")));
        reportSettings.setProjectName(properties.getProperty("ProjectName"));
        reportSettings.setGenerateExcelReports(Boolean.parseBoolean(properties.getProperty("ExcelReport")));
        reportSettings.setGenerateHtmlReports(Boolean.parseBoolean(properties.getProperty("HtmlReport")));
        reportSettings.setGenerateSeeTestReports(
                Boolean.parseBoolean(mobileProperties.getProperty("SeeTestReportGeneration")));
        reportSettings.setGeneratePerfectoReports(
                Boolean.parseBoolean(mobileProperties.getProperty("PerfectoReportGeneration")));
        reportSettings
                .setTakeScreenshotFailedStep(Boolean.parseBoolean(properties.getProperty("TakeScreenshotFailedStep")));
        reportSettings
                .setTakeScreenshotPassedStep(Boolean.parseBoolean(properties.getProperty("TakeScreenshotPassedStep")));
        reportSettings.setConsolidateScreenshotsInWordDoc(
                Boolean.parseBoolean(properties.getProperty("ConsolidateScreenshotsInWordDoc")));
        reportSettings.setisMobileExecution(isMobileAutomation());

        reportSettings.setLinkScreenshotsToTestLog(this.linkScreenshotsToTestLog);

    }

    private void createTestLogHeader() {
        report.addTestLogHeading(reportSettings.getProjectName() + " - " + reportSettings.getReportName()
                + " Automation Execution Results");
        report.addTestLogSubHeading("Date & Time",
                ": " + Util.getFormattedTime(startTime, properties.getProperty("DateFormatString")), "Iteration Mode",
                ": " + testParameters.getIterationMode());
        report.addTestLogSubHeading("Start Iteration", ": " + testParameters.getStartIteration(), "End Iteration",
                ": " + testParameters.getEndIteration());

        switch (testParameters.getExecutionMode()) {
            case LOCAL:
                report.addTestLogSubHeading("Browser/Platform", ": " + testParameters.getBrowserAndPlatform(),
                        "Execution on", ": " + "Local Machine");
                break;
            case LOCAL_EMULATED_DEVICE:
                report.addTestLogSubHeading("Browser/Platform", ": " + testParameters.getBrowserAndPlatform(),
                        "Execution on", ": " + "Emulated Mobile Device on Local Machine");
                report.addTestLogSubHeading("Emulated Device Name", ":" + testParameters.getDeviceName(), "", "");
                break;

            case REMOTE:
                report.addTestLogSubHeading("Browser/Platform", ": " + testParameters.getBrowserAndPlatform(),
                        "Execution on", ": " + "Remote Machine @" + properties.getProperty("RemoteUrl"));
                break;

            case REMOTE_EMULATED_DEVICE:
                report.addTestLogSubHeading("Browser/Platform", ": " + testParameters.getBrowserAndPlatform(),
                        "Execution on",
                        ": " + "Emulated Mobile Device on Remote Machine @" + properties.getProperty("RemoteUrl"));
                report.addTestLogSubHeading("Emulated Device Name", ":" + testParameters.getDeviceName(), "", "");
                break;

            case GRID:
                report.addTestLogSubHeading("Browser/Platform", ": " + testParameters.getBrowserAndPlatform(),
                        "Execution on", ": " + "Grid @" + properties.getProperty("RemoteUrl"));
                break;

            case MOBILE:
                report.addTestLogSubHeading("Execution Mode", ": " + testParameters.getExecutionMode(),
                        "Execution Platform", ": " + testParameters.getMobileExecutionPlatform());
                report.addTestLogSubHeading("Tool Used", ": " + testParameters.getMobileToolName(), "Device Name/ID",
                        ": " + testParameters.getDeviceName());
                break;

            case PERFECTO:
                report.addTestLogSubHeading("Execution Mode", ": " + testParameters.getExecutionMode(),
                        "Execution Platform", ": " + testParameters.getMobileExecutionPlatform());
                report.addTestLogSubHeading("Tool Used", ": " + testParameters.getMobileToolName(), "Device Name/ID",
                        ": " + testParameters.getDeviceName());
                report.addTestLogSubHeading("Executed on",
                        ": " + "Perfecto MobileCloud @ " + mobileProperties.getProperty("PerfectoHost"), "Perfecto User",
                        ": " + mobileProperties.getProperty("PerfectoUser"));
                break;

            case SEETEST:
                report.addTestLogSubHeading("Execution Mode", ": " + testParameters.getExecutionMode(),
                        "Execution Platform", ": " + testParameters.getMobileExecutionPlatform());
                report.addTestLogSubHeading("Tool Used", ": " + testParameters.getMobileToolName(), "Device Name/ID",
                        ": " + testParameters.getDeviceName());
                break;

            case MINT:
                report.addTestLogSubHeading("Execution Mode", ": " + testParameters.getExecutionMode(),
                        "Execution Platform", ": " + testParameters.getMobileExecutionPlatform());
                report.addTestLogSubHeading("Tool Used", ": " + testParameters.getMobileToolName(), "Device OS Version",
                        ": " + testParameters.getMobileOSVersion());
                report.addTestLogSubHeading("Executed on",
                        ": " + "mint cloud @ " + mobileProperties.getProperty("mintHost"), "mint User",
                        ": " + mobileProperties.getProperty("mintUsername"));
                break;

            case SAUCELABS:

                if (testParameters.getMobileToolName().toString().equalsIgnoreCase("REMOTE_WEBDRIVER")) {
                    report.addTestLogSubHeading("Execution Mode", ": " + testParameters.getExecutionMode(),
                            "Execution Platform", ": " + testParameters.getPlatform());
                    report.addTestLogSubHeading("Tool Used", ": " + testParameters.getMobileToolName(), "Browser",
                            ": " + testParameters.getBrowser());
                } else {
                    report.addTestLogSubHeading("Execution Mode", ": " + testParameters.getExecutionMode(),
                            "Execution Platform", ": " + testParameters.getMobileExecutionPlatform());
                    report.addTestLogSubHeading("Tool Used", ": " + testParameters.getMobileToolName(), "Device Name/ID",
                            ": " + testParameters.getDeviceName());
                }

                break;

            case BROWSERSTACK:
                if (testParameters.getMobileToolName().toString().equalsIgnoreCase("REMOTE_WEBDRIVER")) {
                    report.addTestLogSubHeading("ExecutionPatform", ": " + testParameters.getExecutionMode(),
                            "Execution on", ": " + testParameters.getMobileExecutionPlatform());
                    report.addTestLogSubHeading("Tool Used", ": " + testParameters.getMobileToolName(), "Device Name/ID",
                            ": " + testParameters.getDeviceName());
                } else {
                    report.addTestLogSubHeading("ExecutionPlatform", ": " + testParameters.getExecutionMode(),
                            "Execution on", ": " + testParameters.getPlatform());
                    report.addTestLogSubHeading("Tool Used", ": " + testParameters.getMobileToolName(), "Browser",
                            ": " + testParameters.getBrowser());
                }

                break;

            case MOBILECENTRE:
                report.addTestLogSubHeading("ExecutionPlatform", ": " + testParameters.getExecutionMode(), "Execution on",
                        ": " + testParameters.getMobileExecutionPlatform());
                report.addTestLogSubHeading("Tool Used", ": " + testParameters.getMobileToolName(), "Device Name/ID",
                        ": " + testParameters.getDeviceName());

                break;

            case MOBILELABS:
                report.addTestLogSubHeading("Execution Mode", ": " + testParameters.getExecutionMode(),
                        "Execution Platform", ": " + testParameters.getMobileExecutionPlatform());
                report.addTestLogSubHeading("Tool Used", ": " + testParameters.getMobileToolName(), "Device Name/ID",
                        ": " + testParameters.getDeviceName());
                report.addTestLogSubHeading("Executed on",
                        ": " + "MobileLabs Cloud @ " + mobileProperties.getProperty("HostIP"), "MobileLabs User",
                        ": " + mobileProperties.getProperty("UserName"));
                break;

            default:
                throw new FrameworkException("Unhandled Execution Mode!");
        }

        report.addTestLogTableHeadings();
    }

    private synchronized void initializeDatatable() {
        String datatablePath = frameworkParameters.getRelativePath() + Util.getFileSeparator() + "src"
                + Util.getFileSeparator() + "main" + Util.getFileSeparator() + "resources" + Util.getFileSeparator()
                + "Datatables";

        String runTimeDatatablePath;
        Boolean includeTestDataInReport = Boolean.parseBoolean(properties.getProperty("IncludeTestDataInReport"));
        if (includeTestDataInReport) {
            runTimeDatatablePath = reportPath + Util.getFileSeparator() + "Datatables";

            File runTimeDatatable = new File(
                    runTimeDatatablePath + Util.getFileSeparator() + testParameters.getCurrentScenario() + ".xls");
            if (!runTimeDatatable.exists()) {
                File datatable = new File(
                        datatablePath + Util.getFileSeparator() + testParameters.getCurrentScenario() + ".xls");

                try {
                    FileUtils.copyFile(datatable, runTimeDatatable);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new FrameworkException(
                            "Error in creating run-time datatable: Copying the datatable failed...");
                }
            }

            File runTimeCommonDatatable = new File(
                    runTimeDatatablePath + Util.getFileSeparator() + "Common Testdata.xls");
            if (!runTimeCommonDatatable.exists()) {
                File commonDatatable = new File(datatablePath + Util.getFileSeparator() + "Common Testdata.xls");

                try {
                    FileUtils.copyFile(commonDatatable, runTimeCommonDatatable);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new FrameworkException(
                            "Error in creating run-time datatable: Copying the common datatable failed...");
                }
            }
        } else {
            runTimeDatatablePath = datatablePath;
        }

        dataTable = new CraftDataTable(runTimeDatatablePath, testParameters.getCurrentScenario());
        dataTable.setDataReferenceIdentifier(properties.getProperty("DataReferenceIdentifier"));
    }

    private void initializeTestScript() {
        driverUtil = new WebDriverUtil(driver);
        scriptHelper = new ScriptHelper(dataTable, report, driver, driverUtil, testParameters, test);
        driver.setRport(report);
        driver.setExtentRport(test);
        initializeBusinessFlow();
    }

    private void initializeBusinessFlow() {
        ExcelDataAccess businessFlowAccess = new ExcelDataAccess(
                frameworkParameters.getRelativePath() + Util.getFileSeparator() + "src" + Util.getFileSeparator()
                        + "main" + Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "Datatables",
                testParameters.getCurrentScenario());
        businessFlowAccess.setDatasheetName("Business_Flow");

        int rowNum = businessFlowAccess.getRowNum(testParameters.getCurrentTestcase(), 0);
        if (rowNum == -1) {
            throw new FrameworkException("The test case \"" + testParameters.getCurrentTestcase()
                    + "\" is not found in the Business Flow sheet!");
        }

        String dataValue;
        businessFlowData = new ArrayList<String>();
        int currentColumnNum = 1;
        while (true) {
            dataValue = businessFlowAccess.getValue(rowNum, currentColumnNum);
            if ("".equals(dataValue)) {
                break;
            }
            businessFlowData.add(dataValue);
            currentColumnNum++;
        }

        if (businessFlowData.isEmpty()) {
            throw new FrameworkException(
                    "No business flow found against the test case \"" + testParameters.getCurrentTestcase() + "\"");
        }
    }

    private void executeCRAFTTestIterations() {
        while (currentIteration <= testParameters.getEndIteration()) {
            report.addTestLogSection("Iteration: " + Integer.toString(currentIteration));

            // Evaluate each test iteration for any errors
            try {
                executeTestcase(businessFlowData);
            } catch (FrameworkException fx) {
                exceptionHandler(fx, fx.getErrorName());
            } catch (InvocationTargetException ix) {


                exceptionHandler((Exception) ix.getCause(), "Error");
            } catch (Exception ex) {
                exceptionHandler(ex, "Error");
            }

            currentIteration++;
        }
    }

    private void executeTestcase(List<String> businessFlowData)
            throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        Map<String, Integer> keywordDirectory = new HashMap<String, Integer>();

        for (int currentKeywordNum = 0; currentKeywordNum < businessFlowData.size(); currentKeywordNum++) {
            String[] currentFlowData = businessFlowData.get(currentKeywordNum).split(",");
            String currentKeyword = currentFlowData[0];

            int nKeywordIterations;
            if (currentFlowData.length > 1) {
                nKeywordIterations = Integer.parseInt(currentFlowData[1]);
            } else {
                nKeywordIterations = 1;
            }

            for (int currentKeywordIteration = 0; currentKeywordIteration < nKeywordIterations; currentKeywordIteration++) {
                //if (keywordDirectory.containsKey(currentKeyword) && nKeywordIterations != 1) {
                if (keywordDirectory.containsKey(currentKeyword)) {
                    keywordDirectory.put(currentKeyword, keywordDirectory.get(currentKeyword) + 1);
                } else {
                    keywordDirectory.put(currentKeyword, 1);
                }
                currentSubIteration = keywordDirectory.get(currentKeyword);

                dataTable.setCurrentRow(testParameters.getCurrentTestcase(), currentIteration, currentSubIteration);

                if (currentSubIteration > 1) {
                    test.log(Status.INFO, "<p style='text-align:center' class='label time-taken black lighten-1 white-text'><b>"+currentKeyword + " (Sub-Iteration: " + currentSubIteration + ")\"</b></p>");
                    report.addTestLogSubSection(currentKeyword + " (Sub-Iteration: " + currentSubIteration + ")");

                } else {
                    test.log(Status.INFO, "<p style='text-align:center' class='label time-taken black lighten-1 white-text'><b>"+currentKeyword+"</b></p>");
                    report.addTestLogSubSection(currentKeyword);
                }

                invokeBusinessComponent(currentKeyword);

            }
        }
        if(scriptHelper.commonData.watch.isStarted()){
            getRequiredExecutionTime();
        }
    }

    private void invokeBusinessComponent(String currentKeyword)
            throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        Boolean isMethodFound = false;
        final String CLASS_FILE_EXTENSION = ".class";
        File[] packageDirectories = {
                new File(frameworkParameters.getRelativePath() + Util.getFileSeparator() + "target"
                        + Util.getFileSeparator() + "classes" + Util.getFileSeparator() + "businesscomponents"),
                new File(frameworkParameters.getRelativePath() + Util.getFileSeparator() + "target"
                        + Util.getFileSeparator() + "" +
                        "classes" + Util.getFileSeparator() + "componentgroups")};

        for (File packageDirectory : packageDirectories) {
            File[] packageFiles = packageDirectory.listFiles();
            String packageName = packageDirectory.getName();

            for (int i = 0; i < packageFiles.length; i++) {
                File packageFile = packageFiles[i];
                String fileName = packageFile.getName();

                // We only want the .class files
                if (fileName.endsWith(CLASS_FILE_EXTENSION)) {
                    // Remove the .class extension to get the class name
                    String className = fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION.length());

                    Class<?> reusableComponents = Class.forName(packageName + "." + className);
                    Method executeComponent;

                    try {
                        // Convert the first letter of the method to lowercase
                        // (in line with java naming conventions)
                        currentKeyword = currentKeyword.substring(0, 1).toLowerCase() + currentKeyword.substring(1);
                        executeComponent = reusableComponents.getMethod(currentKeyword, (Class<?>[]) null);
                    } catch (NoSuchMethodException ex) {
                        // If the method is not found in this class, search the
                        // next class
                        continue;
                    }

                    isMethodFound = true;

                    Constructor<?> ctor = reusableComponents.getDeclaredConstructors()[0];
                    Object businessComponent = ctor.newInstance(scriptHelper);

                    executeComponent.invoke(businessComponent, (Object[]) null);

                    break;
                }
            }
        }
        if (!isMethodFound) {
            throw new FrameworkException("Keyword " + currentKeyword + " not found within any class "
                    + "inside the businesscomponents package");
        }
    }

    private void exceptionHandler(Exception ex, String exceptionName) {
        // Error reporting
        String exceptionDescription = ex.getMessage();
        if (exceptionDescription == null) {
            exceptionDescription = ex.toString();
        }

        if (ex.getCause() != null) {
            report.updateTestLog(exceptionName, exceptionDescription + " <b>Caused by: </b>" + ex.getCause(),
                    Status_CRAFT.FAIL);
        } else {
            report.updateTestLog(exceptionName, exceptionDescription, Status_CRAFT.FAIL);
        }

        // Print stack trace for detailed debug information
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        String stackTrace = stringWriter.toString();
        report.updateTestLog("Exception stack trace", stackTrace, Status_CRAFT.DEBUG);

        // Error response
        if (frameworkParameters.getStopExecution()) {
            report.updateTestLog("CRAFT Info", "Test execution terminated by user! All subsequent tests aborted...",
                    Status_CRAFT.DONE);
            currentIteration = testParameters.getEndIteration();
        } else {
            OnError onError = OnError.valueOf(properties.getProperty("OnError"));
            switch (onError) {
                // Stop option is not relevant when run from QC
                case NEXT_ITERATION:
                    report.updateTestLog("CRAFT Info",
                            "Test case iteration terminated by user! Proceeding to next iteration (if applicable)...",
                            Status_CRAFT.DONE);
                    break;

                case NEXT_TESTCASE:
                    report.updateTestLog("CRAFT Info",
                            "Test case terminated by user! Proceeding to next test case (if applicable)...", Status_CRAFT.DONE);
                    currentIteration = testParameters.getEndIteration();
                    break;

                case STOP:
                    frameworkParameters.setStopExecution(true);
                    report.updateTestLog("CRAFT Info", "Test execution terminated by user! All subsequent tests aborted...",
                            Status_CRAFT.DONE);
                    currentIteration = testParameters.getEndIteration();
                    break;

                default:
                    throw new FrameworkException("Unhandled OnError option!");
            }
        }
    }

    /***********************************************************************

     Method Name    : quitWebDriver
     Modified By    : C103516
     Modified On    : 11/06/19
     Input          : No Input Parameter
     Comment       : Adding driver.close and driver.quit to release the mobile instance
     Comment       : Updating driver.ResetApp to release mobile instance

     /***********************************************************************/

    private void quitWebDriver() {
        switch (testParameters.getExecutionMode()) {
            case LOCAL:
                driver.quit();
                break;
            case REMOTE:
            case LOCAL_EMULATED_DEVICE:
            case REMOTE_EMULATED_DEVICE:
            case GRID:
            case MOBILE:
                driver.quit();
                break;
            case MINT:
            case SAUCELABS:
            case BROWSERSTACK:
            case MOBILECENTRE:
                if((testParameters.getMobileExecutionPlatform().toString()).equalsIgnoreCase("ANDROID"))
                {
                  driver.quit();
                }
                else if((testParameters.getMobileExecutionPlatform().toString()).equalsIgnoreCase("IOS"))
                {
                  driver.closeApp();
                  driver.close();
                }
                else
                {
                    driver.quit();
                }
                break;
            case MOBILELABS:
                driver.quit();
                break;
            case SEETEST:
                // client.applicationClose(properties.getProperty("SeeTestAndroidApplicationName"));
      /*client.releaseDevice("*", true, false, true);
      seeTestResultPath = client.generateReport(true);
      downloadAddtionalReport();
      client.releaseClient();
      driver.quit();
      break;*/

            case PERFECTO:
                downloadAddtionalReport();
                driver.quit();
                break;

            default:
                throw new FrameworkException("Unhandled Execution Mode!");
        }

    }

    private void wrapUp() throws Exception {
        endTime = Util.getCurrentTime();
        closeTestReport();
        uploadResultsToALM();
    }


    private void closeTestReport() {
        executionTime = Util.getTimeDifference(startTime, endTime);
        report.addTestLogFooter(executionTime);

        if (reportSettings.shouldConsolidateScreenshotsInWordDoc()) {
            report.consolidateScreenshotsInWordDoc();
        }
    }

    private void downloadAddtionalReport() {
        if (testParameters.getExecutionMode().equals(ExecutionMode.PERFECTO)
                && reportSettings.shouldGeneratePerfectoReports()) {
            try {
                driver.close();
                RemoteWebDriverUtils.downloadReport((RemoteWebDriver) driver.getWebDriver(), "pdf",
                        reportPath + Util.getFileSeparator() + "Perfecto Results" + Util.getFileSeparator()
                                + testParameters.getCurrentScenario() + "_" + testParameters.getCurrentTestcase() + "_"
                                + testParameters.getCurrentTestInstance() + ".pdf");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (testParameters.getExecutionMode().equals(ExecutionMode.SEETEST)
                && reportSettings.shouldGenerateSeeTestReports()) {
            new File(reportPath + Util.getFileSeparator() + "SeeTest Results" + Util.getFileSeparator()
                    + testParameters.getCurrentTestcase() + "_" + testParameters.getCurrentTestInstance()).mkdir();
            File source = new File(seeTestResultPath);
            File dest = new File(reportPath + Util.getFileSeparator() + "SeeTest Results" + Util.getFileSeparator()
                    + testParameters.getCurrentTestcase() + "_" + testParameters.getCurrentTestInstance());

            try {
                FileUtils.copyDirectoryToDirectory(source, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isMobileAutomation() {
        boolean isMobileAutomation = false;
        if (testParameters.getExecutionMode().equals(ExecutionMode.MOBILE)
                || testParameters.getExecutionMode().equals(ExecutionMode.PERFECTO)
                || testParameters.getExecutionMode().equals(ExecutionMode.SEETEST)
                || testParameters.getExecutionMode().equals(ExecutionMode.SAUCELABS)
                || testParameters.getExecutionMode().equals(ExecutionMode.MINT)
                || testParameters.getExecutionMode().equals(ExecutionMode.BROWSERSTACK)
                || testParameters.getExecutionMode().equals(ExecutionMode.MOBILECENTRE)
                || testParameters.getExecutionMode().equals(ExecutionMode.MOBILELABS)) {
            isMobileAutomation = true;
        }
        return isMobileAutomation;

    }

    private void uploadResultsToALM() throws Exception {

        if (almUpdate()) {
            Map<String, String> almValues = new HashMap<String, String>();
            almValues.put("testCaseName", testParameters.getCurrentTestcase());
            almValues.put("testCaseNameQC", testParameters.getCurrentTestcaseQC());
            almValues.put("htmlReportPath", reportSettings.getReportPath() + "\\HTML Results");
            almValues.put("htmlAttachName", testParameters.getCurrentTestcase() + "_Instance1.html");
            almValues.put("screenShotPath", reportSettings.getReportPath() + "\\Screenshots");
            almValues.put("reportFolder", reportSettings.getReportPath());
            almValues.put("overAllResult", report.getTestStatus());

            ALMRest almRest = new ALMRest(properties, almValues);

            if (!almRest.checkALMConnection(properties.getProperty("ALMUserName"), properties.getProperty("ALMPassword"))) {
                throw new FrameworkException("Error", "ALM Login not successful");
            }
            almRest.almUpdateTCStatus();
        }
    }

    public boolean almUpdate() {

        boolean almUpdate = false;

        try {
            almUpdate = Boolean.valueOf(properties.getProperty("UploadResultsToALM"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return almUpdate;
    }

    /**
     * Function : To calculate total execution time based on start and end time
     * Created by : C966003 on 29/05/2020
     * Update on    Updated by     Reason
     *
     */
    public void getRequiredExecutionTime(){
        scriptHelper.commonData.watch.stop();
        long executionTimeInMilli = scriptHelper.commonData.watch.getTime();
        double executionTime = Double.valueOf(executionTimeInMilli/6);
        String executionTimeInMin = String.format("%.06f", (executionTime/10000));

        report.updateTestLog("Total Execution Time","Execution time taken(in min) : "+executionTimeInMin,Status_CRAFT.DONE);
    }


}
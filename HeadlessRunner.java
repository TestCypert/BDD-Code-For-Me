package runners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cognizant.framework.*;
import com.cognizant.framework.selenium.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by C966055 on 17/05/2019.
 */
public class HeadlessRunner {
    private HeadlessRunner() {
        // To prevent external instantiation of this class
    }

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
    /**
     * The entry point of the test batch execution <br>
     * Exits with a value of 0 if the test passes and 1 if the test fails
     *
     * @param args
     *            Command line arguments to the Allocator (Not applicable)
     */
    public static void main(String[] args) {
        HeadlessRunner HeadlessRunner = new HeadlessRunner();
        HeadlessRunner.driveBatchExecution();
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

        int nThreads = Integer.parseInt(properties
                .getProperty("NumberOfThreads"));
        resultSummaryManager.initializeSummaryReport(nThreads);

        resultSummaryManager.setupErrorLog();

        int testBatchStatus = executeTestBatch(nThreads);

        resultSummaryManager.wrapUp(false);

        //ExtentReports will push/write everything to the document
        extent.flush();

        resultSummaryManager.manageJenkinsReportsFolder();

        resultSummaryManager.launchResultSummary();

        System.exit(testBatchStatus);
    }

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

                //String testConfig = row.get("TestConfigurationID");
                String testConfig = properties.getProperty("DriverType");
                if (!"".equals(testConfig)) {
                    testParameters.setExecutionMode(ExecutionMode.valueOf(properties
                            .getProperty("ProfileExecutionMode")));
                }

                if (!"".equals(testConfig)) {
                    testParameters.setBrowser(Browser.valueOf(properties
                            .getProperty("DriverType")));
                }
                testInstancesToRun.add(testParameters);
                runManagerAccess.setDatasheetName(sheetName);
            }
        }
        return testInstancesToRun;
    }


}

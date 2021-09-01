package com.boi.grp.hooks;

import java.io.*;
import java.util.*;

import com.boi.grp.driverManager.DriverManager;
import com.boi.grp.pageobjects.BasePage;
import com.boi.grp.pageobjects.Services.LaunchService;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.boi.grp.utilities.LogManager;


import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks{
	public  WebDriver driver;
	public  WebDriverWait wait;
	public  Logger logman;
	public  WebDriverManager driverManager;
	public  static Scenario testscenario;
	public static final String propFilePath = "src/test/java/com/boi/grp/globalconfig/GlobalConfig.properties";
	public  Properties globalConfig;
	public static SoftAssertions softAssertions;
	public  FileInputStream FIS = null;
    public Map<String, Map<String, String>> deserializeData;

	@Before
	public void init(Scenario scenario) {
		testscenario = scenario;
		System.setProperty("ScenarioName", scenario.getName());
		System.setProperty("ScenarioID", parseScenarioId(scenario.getId()));
		System.setProperty("PropFilePath", propFilePath);
		softAssertions=new SoftAssertions();
		LogManager.resetLogger();
		logman = LogManager.getInstance();
		logman.debug("Scenario Name is =" + System.getProperty("ScenarioName"));
		loadGlobalConfig();
		failSafePropertyGenenration();
		setDataInSystemPropertiesForRunConfigurationUsingExcel();
        writePlatformAndTypeDetailsForReport();
        setObjectRepoInSystemVariables();
        driver= new DriverManager().GetDriver(System.getProperty("PLATFORM"),System.getProperty("TYPE"));
        invoke();
	}



	@After
	public void tearDown() throws Error {
		try {
			if(System.getProperty("TYPE").equalsIgnoreCase("API")){
                System.out.println("Type is API");
            }else{
                if(!(driver.toString().contains("(null)"))){
                	StringBuilder builder = null;
                    List<Throwable> list = softAssertions.errorsCollected();
                    if (list.size()>0) {
                        builder=new StringBuilder();
                        for (Throwable errorValue:list) {
                            builder.append("There are Soft Assertion Failures : \n ").append(errorValue.getMessage()).append("\n \n");
                        }
                        new BasePage().insertErrorMessageToHtmlReport(builder.toString());
                    }
                    driver.quit();
                    try {
						softAssertions.assertAll();
					} catch (Throwable e) {
						logman.warn("There are "+builder.toString());
                        throw new Error("There are Soft Assertion Failures");
					}
                }else{
                    List<Throwable> list = softAssertions.errorsCollected();
                    StringBuilder builder = null;
                    if (list.size()>0) {
                        builder=new StringBuilder();
                        for (Throwable errorValue:list) {
                            builder.append("There are Soft Assertion Failures : \n ").append(errorValue.getMessage()).append("\n");
                        }
                        new BasePage().insertErrorMessageToHtmlReport(builder.toString());
                    }
                    try {
						softAssertions.assertAll();
					} catch (Throwable e) {
						logman.warn("There are "+builder.toString());
                        throw new Error("There are Soft Assertion Failures");
					}
                }
            }
		} catch (Throwable e) {
            logman.warn("Error in teardown, message =  "+e.getMessage());
            throw new Error("There are Soft Assertion Failures");
		}
	}

    public String parseScenarioId(String scenarioId){
        try {
            String[] arr = scenarioId.split("/");
			return arr[arr.length - 1].replaceAll(":","_");
        } catch (Throwable e) {
            logman.error("Error in parsing please check parseScenarioId method, error = "+e.getMessage());
            return "";
        }
    }

	public void loadGlobalConfig() {
		try {
			FIS = new FileInputStream(propFilePath);
			globalConfig = new Properties();
			globalConfig.load(FIS);
			logman.debug("GlobalConfig file loaded successfully");
		} catch (Exception e) {
			logman.error("Error Occurred Inside init block in Hooks, Error Description=" + e.getMessage());
		}
	}

	public void failSafePropertyGenenration() {
		try {
			for (Object prop : globalConfig.keySet()) {
				if (System.getenv(prop.toString()) != null) {
					System.setProperty(prop.toString().trim().toUpperCase(), System.getenv(prop.toString()));
					logman.info(prop.toString().trim().toUpperCase() + " property is set from environment to "
							+ System.getenv(prop.toString()));
				} else {
					System.setProperty(prop.toString().trim().toUpperCase(), globalConfig.getProperty(prop.toString()));
					logman.info(prop.toString().trim().toUpperCase() + " property is set from environment to "
							+ globalConfig.getProperty(prop.toString()));
				}
			}
		} catch (Exception e) {
			logman.error("Error Occurred Inside failSafePropertyGenenration block in Hooks, Error Description="
					+ e.getMessage());
		}
	}

	public WebDriver GetDriver(){
		return this.driver;
	}

	public void setDriver(WebDriver driver){
		this.driver=driver;
	}


	public void setDataInSystemPropertiesForRunConfigurationUsingExcel(){
		try {
			if(System.getProperty("EXCEL_CONFIGURATION").equalsIgnoreCase("TRUE")){
				List<Map<String, String>> data = getDeserializeDataForExcelConfiguration();
				for (Map<String,String> map:data) {
	                    if(System.getProperty("ScenarioName").equalsIgnoreCase(map.get("ScenarioName"))){
                        map.remove("ScenarioName");
                        map.remove("DEVICECONFIGURATION");
                        for (Map.Entry<String,String> entry :map.entrySet()) {
                            if (!entry.getValue().isEmpty()){
                                System.setProperty(entry.getKey(),entry.getValue());
                                logman.info(entry.getKey().trim().toUpperCase() + " property is set from environment to "
                                        + entry.getValue());
                            }
                        }
                    }
				}
				logman.info("Added System variables for all the deserialize data");
			}
		} catch (Exception e) {
			logman.error("Error in setDataInSystemPropertiesForMobileCenterParallelConfiguration mrthod, Error = "+e.getMessage());
		}
	}

	public List<Map<String, String>> getDeserializeDataForExcelConfiguration(){
        List<Map<String,String>> excelConfiguration = new ArrayList<Map<String,String>>();

        try{
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/target/listData");
            ObjectInputStream ois = new ObjectInputStream(fis);
            excelConfiguration = (List<Map<String, String>>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e){
            logman.error("Error in getDeserializeDataForMCConfiguration, Error = "+e.getMessage());
            return excelConfiguration;
        } catch (ClassNotFoundException e){
            logman.error("Class not found, Error = "+e.getMessage());
            return excelConfiguration;
        }
        logman.info("Deserialization of excel data is complete");
        return excelConfiguration;
    }

    public void writePlatformAndTypeDetailsForReport(){
        try {
            Properties prop = new Properties();
            prop.load(new FileReader(System.getProperty("user.dir")+"/src/test/resources/report.properties"));
            //two keys REPORT_PLATFORM,REPORT_PLATFORM_TYPE,DEVICENAME
            if(prop.getProperty("REPORT_PLATFORM")==null){
                setKeyInProperties();
            }else{
                boolean deviceNameFlag=false;
                String existingDeviceName = null;
                String existingPlatform = prop.getProperty("REPORT_PLATFORM");
                String existingType = prop.getProperty("REPORT_PLATFORM_TYPE");
                if(prop.getProperty("REPORT_DEVICE_NAME")!=null){
                    existingDeviceName = prop.getProperty("REPORT_DEVICE_NAME");
                    deviceNameFlag=true;
                }
                if(!existingPlatform.contains(System.getProperty("PLATFORM"))){
                    amendKeysInExistingPropertiesFile("REPORT_PLATFORM",existingPlatform+","+System.getProperty("PLATFORM"));
                }

                if(!existingType.contains(System.getProperty("TYPE"))){
                    amendKeysInExistingPropertiesFile("REPORT_PLATFORM_TYPE",existingType+","+System.getProperty("TYPE"));
                }

                if (deviceNameFlag) {
                    if(!existingDeviceName.contains(System.getProperty("DEVICENAME"))){
                        amendKeysInExistingPropertiesFile("REPORT_DEVICE_NAME",existingDeviceName+","+System.getProperty("DEVICENAME"));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Written By Atmaj
    public void setObjectRepoInSystemVariables(){
        try {
            File file =new File(System.getProperty("user.dir")+"/src/test/resources/repository");
            Properties properties=new Properties();
            File[] fileList = file.listFiles();
            String eachFile;
            assert fileList != null;
            for (File fileTemp:fileList) {
                eachFile=fileTemp.getAbsolutePath();
                FileInputStream fis=new FileInputStream(eachFile);
                properties.load(fis);
                Set<Object> setOfKeys = properties.keySet();
                for (Object key:setOfKeys) {
                    System.setProperty(key.toString().trim(), properties.getProperty(key.toString()));
                }
            }
        } catch (Throwable e) {
            logman.error("Error in storing repository values in System properties, Error = "+e.getMessage());
        }
    }


    public void setKeyInProperties(){
        OutputStream outputStream = null;
        try {
            Properties properties=new Properties();
            outputStream=new FileOutputStream(System.getProperty("user.dir")+"/src/test/resources/report.properties");
            properties.setProperty("REPORT_PLATFORM",System.getProperty("PLATFORM"));
            properties.setProperty("REPORT_PLATFORM_TYPE",System.getProperty("TYPE"));
            if(System.getProperty("DEVICENAME")!=null){
                properties.setProperty("REPORT_DEVICE_NAME",System.getProperty("DEVICENAME"));
            }
            properties.store(outputStream,"");
        } catch (Exception e) {
            logman.error("Error in setKeyInProperties method, Error = "+e.getMessage());
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                logman.error("Error in closing output steam while appending data in globalConfig properties file, Error =  "+e.getMessage());
            }
        }
    }

    public void amendKeysInExistingPropertiesFile(String key,String data){
        OutputStream outputStream = null;
        FileInputStream fileIn;
        try {
            Properties properties=new Properties();
            File file = new File(System.getProperty("user.dir")+"/src/test/resources/report.properties");
            fileIn = new FileInputStream(file);
            properties.load(fileIn);
            outputStream=new FileOutputStream(System.getProperty("user.dir")+"/src/test/resources/report.properties");
            properties.setProperty(key, data);
            properties.store(outputStream,"");
        } catch (Exception e) {
            logman.error("Error in amendKeysInExistingPropertiesFile method, Error = "+e.getMessage());
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                logman.error("Error in closing output steam while appending data in globalConfig properties file, Error =  "+e.getMessage());
            }
        }
    }

    public void invoke(){
        new LaunchService(GetDriver()).invokeUXPApplication();
    }


}

package com.boi.grp.utilities;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PreRunUtility{
	
    public static FileInputStream FIS = null;
    public static Properties Config = null;
    public static Logger logman;

	public static void main(String[] args) {
        System.out.println("PreRunUtility method");
        try {
            System.setProperty("ScenarioName","PreRunLog");
            System.setProperty("ScenarioID","PreRunId");
            logman = LogManagerPreRun.getInstance();
            FIS = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/com/boi/grp/globalconfig/GlobalConfig.properties");
            Config = new Properties();
            Config.load(FIS);
            failSafePropertyGeneration();
        } catch (Throwable e) {
            logman.error("Unable to create PreRun Log , error = "+e.getMessage());
        }

        handleRunConfigurationUsingExcel();
        handleTestDataFromExcelFile();
        invokeWireMockServer();
        removeReportPropertiesData();
	}
	
	
	public static void failSafePropertyGeneration() {
        try {
            for (Object prop : Config.keySet()) {
                if (System.getenv(prop.toString()) != null) {
                    System.setProperty(prop.toString().trim().toUpperCase(), System.getenv(prop.toString()));
                } else {
                    System.setProperty(prop.toString().trim().toUpperCase(), Config.getProperty(prop.toString()));
                }
            }
        } catch (Exception e) {
            logman.error("Error Occurred Inside failSafePropertyGenenration block in PreRun, Error Description="
                    + e.getMessage());
        }
    }

    public static void removeReportPropertiesData(){
        FileOutputStream outputStream = null;
        try {
            Properties prop = new Properties();
            prop.load(new FileReader(System.getProperty("user.dir")+"/src/test/resources/report.properties"));
            prop.clear();
            outputStream = new FileOutputStream(System.getProperty("user.dir") + "/src/test/resources/report.properties");
            prop.store(outputStream,null);
        } catch (IOException e) {
            logman.error("Error in removeReportPropertiesData , Error = "+e.getMessage());
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                logman.error("Error in closing output steam while removing data from properties file, Error =  "+e.getMessage());
            }
        }
    }

    public static void invokeWireMockServer(){
    	
        try {
            if (System.getProperty("TYPE").equalsIgnoreCase("API")) {
                if(System.getProperty("ISMOCKINGENABLED")==null){
                    System.setProperty("ISMOCKINGENABLED","FALSE");
                }
                if(System.getProperty("ISMOCKINGENABLED").equalsIgnoreCase("TRUE")){
                        System.out.println("wiremock process");
                        String path=System.getProperty("user.dir")+"/src/test/resources/wiremock/wiremock-jre8-standalone-2.27.2.jar";
                        int port=Integer.valueOf(System.getProperty("URI").split(":")[2]);
                        try {
                            Runtime.getRuntime().exec("java.exe -jar " + path + " --port=" + port);
                            System.out.println("Wiremock up");
                        } catch (IOException e) {
                            System.out.println("There are some errors during setting up wiremock server, message ="+e.getMessage());
                        }
                    }
            }
        } catch (Exception e) {
            System.out.println("There are some error in invokeWireMockServer, Message =  "+e.getMessage());
        }
    }

    public static void handleRunConfigurationUsingExcel(){
        if (System.getProperty("EXCEL_CONFIGURATION").equalsIgnoreCase("TRUE")) {
            String excelConfigPath;
            excelConfigPath = System.getProperty("EXCEL_CONFIGURATION_FILEPATH");
            if(excelConfigPath==null){
                excelConfigPath="NA";
            }
            getSerializedConfigDataUsingExcel(excelConfigPath);
        }
        //write code to serialize data for platdetails config tab
        String excelConfigPath;
        excelConfigPath = System.getProperty("EXCEL_CONFIGURATION_FILEPATH");
        if(excelConfigPath==null){
            excelConfigPath="NA";
        }
        getSerializedPlatformDetailsData(excelConfigPath);
    }

    public static void handleTestDataFromExcelFile(){
        try {
            String testDataPath;
            testDataPath = System.getProperty("TEST_DATA_FILEPATH");
            if(testDataPath==null){
                testDataPath="NA";
            }
            additionOfDataInFeatureFile(testDataPath);
        } catch (Exception e) {
            System.out.println("Error in handleTestDataFromExcelFile, Error = "+e.getMessage());
        }
    }


    public static void getSerializedConfigDataUsingExcel(String excelConfigPath){
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/RunConfiguration_dependencies/RunConfig.properties");
            Properties properties = new Properties();
            properties.load(fis);

            try {
                for (Object prop : properties.keySet()) {
                    if (System.getenv(prop.toString()) != null) {
                        System.setProperty(prop.toString().trim().toUpperCase(), System.getenv(prop.toString()));
                    } else {
                        System.setProperty(prop.toString().trim().toUpperCase(), properties.getProperty(prop.toString()));
                    }
                }
            } catch (Exception e) {
                logman.error("Error Occurred Inside getSerializedConfigDataForMobileCenterParallelRun, Error Description="
                        + e.getMessage());
            }

            File file = new File(excelConfigPath);
            Xls_Reader xls_reader;
            if(file.exists()){
                if(excelConfigPath.equalsIgnoreCase("resources")){
                   excelConfigPath=System.getProperty("user.dir")+"/src/test/resources/RunConfiguration_dependencies/"+System.getProperty("CONFIGURATION_WORKBOOK");
                   xls_reader=new Xls_Reader(excelConfigPath);
                }else{
                    xls_reader=new Xls_Reader(excelConfigPath);
                }
            }else{
                excelConfigPath=System.getProperty("user.dir")+"/src/test/resources/RunConfiguration_dependencies/"+System.getProperty("CONFIGURATION_WORKBOOK");
                xls_reader=new Xls_Reader(excelConfigPath);
            }
            List<Map<String, String>> runConfigData = xls_reader.getDataInFormOfListOfMapByLinkingTwoSheets(System.getProperty("PLATFORMDETAILSTAB"),System.getProperty("CONFIGURATIONTAB"),System.getProperty("LINKEDCOLUMN"));

            try{
                FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+"/target/listData");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(runConfigData);
                oos.close();
                fos.close();
                System.out.println("Serialisation complete for configuration using excel file");
            }
            catch (IOException ioe){
                logman.error("Error in serialization , Error = "+ioe.getMessage());
            }
        } catch (Exception e) {
            logman.error("Error in getSerializedConfigDataUsingExcel Method, Message = "+e.getMessage());
            System.out.println("Error in getSerializedConfigDataUsingExcel Method, Message = "+e.getMessage());
        }
    }

    public static void additionOfDataInFeatureFile(String testDataPath){

        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/testData/TestDataConfig.properties");
            Properties properties = new Properties();
            properties.load(fis);

            try {
                for (Object prop : properties.keySet()) {
                    if (System.getenv(prop.toString()) != null) {
                        System.setProperty(prop.toString().trim().toUpperCase(), System.getenv(prop.toString()));
                    } else {
                        System.setProperty(prop.toString().trim().toUpperCase(), properties.getProperty(prop.toString()));
                    }
                }
            } catch (Exception e) {
                logman.error("Error Occurred Inside getSerializedTestData, Error Description="
                        + e.getMessage());
            }

            File file = new File(testDataPath);
            Xls_Reader xls_reader;
            if(file.exists()){
            	if(testDataPath.equalsIgnoreCase("resources")){
                    testDataPath=System.getProperty("user.dir")+"/src/test/resources/testData/"+System.getProperty("TESTDATA_WORKBOOK");
                    xls_reader=new Xls_Reader(testDataPath);
                }else{
                     xls_reader=new Xls_Reader(testDataPath);
                }
                //xls_reader=new Xls_Reader(testDataPath);
            }else{
                testDataPath=System.getProperty("user.dir")+"/src/test/resources/testData/"+System.getProperty("TESTDATA_WORKBOOK");
                xls_reader=new Xls_Reader(testDataPath);
            }

            Map<String, List<Map<String, String>>> testData=xls_reader.getTestDataInFormOfMap(System.getProperty("TESTDATATAB"), System.getProperty("KEY_COLUMN_NAME1"), System.getProperty("KEY_COLUMN_NAME2"),System.getProperty("EXECUTION_COLUMN"));
            Map<String, List<String>> colNamePerScenarioTestData = xls_reader.scenarioTestDataColumnName(System.getProperty("TESTDATATAB"), System.getProperty("KEY_COLUMN_NAME1"));
            FeatureUtils featureUtils=new FeatureUtils();
            featureUtils.readAllFeatureFile(System.getProperty("INITIAL_FEATURE_FOLDER"),System.getProperty("FINAL_FEATURE_FOLDER"),testData,colNamePerScenarioTestData);

            if(System.getProperty("TYPE").equalsIgnoreCase("API")) {
                Map<String, Map<String, String>> dataForJsonPayload = xls_reader.getMappedData(System.getProperty("LINKED_TAB"), System.getProperty("MATCHING_COLUMN"));
                serializationOfMappedDataForJsonPayLoadGeneration(dataForJsonPayload);
            }
        } catch (Exception e) {
            logman.error("Error in additionOfDataInFeatureFile Method, Message = "+e.getMessage());
            System.out.println("Error in additionOfDataInFeatureFile Method, Message = "+e.getMessage());
        }

    }


    public static void serializationOfMappedDataForJsonPayLoadGeneration(Map<String, Map<String, String>> dataForJsonPayload){
        try{
            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+"/target/jsonPayLoadData");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dataForJsonPayload);
            oos.close();
            fos.close();
            System.out.println("Serialisation complete for JsonPayLoad using excel file");
        }
        catch (IOException ioe){
            logman.error("Error in serialization , Error = "+ioe.getMessage());
        }
    }

    public static void getSerializedPlatformDetailsData(String excelConfigPath){
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/RunConfiguration_dependencies/RunConfig.properties");
            Properties properties = new Properties();
            properties.load(fis);

            try {
                for (Object prop : properties.keySet()) {
                    if (System.getenv(prop.toString()) != null) {
                        System.setProperty(prop.toString().trim().toUpperCase(), System.getenv(prop.toString()));
                    } else {
                        System.setProperty(prop.toString().trim().toUpperCase(), properties.getProperty(prop.toString()));
                    }
                }
            } catch (Exception e) {
                logman.error("Error Occurred Inside getSerializedConfigDataForMobileCenterParallelRun, Error Description="
                        + e.getMessage());
            }

            File file = new File(excelConfigPath);
            Xls_Reader xls_reader;
            if(file.exists()){
                if(excelConfigPath.equalsIgnoreCase("resources")){
                    excelConfigPath=System.getProperty("user.dir")+"/src/test/resources/RunConfiguration_dependencies/"+System.getProperty("CONFIGURATION_WORKBOOK");
                    xls_reader=new Xls_Reader(excelConfigPath);
                }else{
                    xls_reader=new Xls_Reader(excelConfigPath);
                }
            }else{
                excelConfigPath=System.getProperty("user.dir")+"/src/test/resources/RunConfiguration_dependencies/"+System.getProperty("CONFIGURATION_WORKBOOK");
                xls_reader=new Xls_Reader(excelConfigPath);
            }
            //List<Map<String, String>> runConfigData = xls_reader.getDataInFormOfListOfMapByLinkingTwoSheets(System.getProperty("PLATFORMDETAILSTAB"),System.getProperty("CONFIGURATIONTAB"),System.getProperty("LINKEDCOLUMN"));
            Map<String, Map<String, String>> platformDetailsData = xls_reader.getDataInFormOfMapofMap(System.getProperty("CONFIGURATIONTAB"), System.getProperty("LINKEDCOLUMN"));

            try{
                FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+"/target/platformData");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(platformDetailsData);
                oos.close();
                fos.close();
            }
            catch (IOException ioe){
                logman.error("Error in getSerializedPlatformDetailsData, Error = "+ioe.getMessage());
            }
        } catch (Exception e) {
            logman.error("Error in getSerializedPlatformDetailsData Method, Message = "+e.getMessage());
            System.out.println("Error in getSerializedPlatformDetailsData Method, Message = "+e.getMessage());
        }
    }



}
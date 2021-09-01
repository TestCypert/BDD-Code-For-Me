package com.boi.grp.utilities;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.presentation.PresentationMode;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by C112083 on 09/11/2020.
 */
public class ReportUtils {
    public Logger logman;

    public ReportUtils(){
        logman = LogManager.getInstance();
    }

    public void invokeAspectWeaverJar(String mavenRepoPath){
        try {
            Runtime.getRuntime().exec("java -jar "+mavenRepoPath);
            logman.info("AspectWeaver jar is invoked successfully");
        } catch (Throwable e) {
            logman.error("Error in invoking AspectWeaver jar, Error = "+e.getMessage());
        }
    }

    public void createEnvironmentXmlFile(){
        try {
            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            // root elements
            Element rootElement = document.createElement("environment");
            document.appendChild(rootElement);

            // first child elements
            Element parameter = document.createElement("parameter");
            rootElement.appendChild(parameter);
            // platform elements
            Element key = document.createElement("key");
            key.appendChild(document.createTextNode("PLATFORM"));
            parameter.appendChild(key);
            // value of platform
            Element value = document.createElement("value");
            value.appendChild(document.createTextNode(System.getProperty("REPORT_PLATFORM")));
            parameter.appendChild(value);

            //second child elements
            Element parameter1 = document.createElement("parameter");
            rootElement.appendChild(parameter1);
            // type elements
            Element key1 = document.createElement("key");
            key1.appendChild(document.createTextNode("TYPE"));
            parameter1.appendChild(key1);
            // value of type
            Element value1 = document.createElement("value");
            value1.appendChild(document.createTextNode(System.getProperty("REPORT_PLATFORM_TYPE")));
            parameter1.appendChild(value1);

            //third child
            Element parameter2 = document.createElement("parameter");
            rootElement.appendChild(parameter2);
            // type elements
            Element key2 = document.createElement("key");
            key2.appendChild(document.createTextNode("ENVIRONMENT"));
            parameter2.appendChild(key2);
            // value of type
            Element value2 = document.createElement("value");
            value2.appendChild(document.createTextNode(System.getProperty("ENVIRONMENT")));
            parameter2.appendChild(value2);

            //fourth child
            Element parameter3 = document.createElement("parameter");
            rootElement.appendChild(parameter3);
            // type elements
            Element key3 = document.createElement("key");
            key3.appendChild(document.createTextNode("DEVICENAME"));
            parameter3.appendChild(key3);
            // value of type
            Element value3 = document.createElement("value");
            value3.appendChild(document.createTextNode(System.getProperty("REPORT_DEVICE_NAME")));
            parameter3.appendChild(value3);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(System.getProperty("user.dir")+"/src/test/resources/allure_dependencies/environment.xml"));
            transformer.transform(source, result);
            logman.info("Environment.xml file is created successfully");
        } catch (Exception e) {
            logman.error("Error in creating Environment.xml file, Error = "+e.getMessage());
        }
    }


    public void copyEnvironmentAndCategoriesFile(){
        try {
            FileUtils.copyFile(new File(System.getProperty("user.dir")+"/src/test/resources/allure_dependencies/environment.xml"),new File(System.getProperty("user.dir")+"/target/allure-results/environment.xml"));
            FileUtils.copyFile(new File(System.getProperty("user.dir")+"/src/test/resources/allure_dependencies/categories.json"),new File(System.getProperty("user.dir")+"/target/allure-results/categories.json"));
            logman.info("Successfully copied 'Environment.xml' and 'Categories.json' file onto target folder");
        } catch (Throwable e) {
            logman.error("Error in copying 'Environment.xml' and 'Categories.json' file onto target folder, Error = "+e.getMessage());
        }
    }

    public void generateAllureHtmlReport(){
        if(System.getProperty("PLATFORM").toUpperCase().equalsIgnoreCase("LINUX")){
            //code for webdrivers.linux

        }else if(System.getProperty("PLATFORM").toUpperCase().equalsIgnoreCase("MAC")){
            //code for webdrivers.mac

        }else{
            try {
                if (!System.getenv("ALLURE_HOME").isEmpty()){
                    ProcessBuilder processBuilder = new ProcessBuilder(System.getenv("ALLURE_HOME") + "/bin/allure.bat", "serve", System.getProperty("user.dir") + "/target/allure-results");
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();
                    logman.info("Allure Html report generated successfully");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    line = reader.readLine();

                    while (line != null) {
                        System.out.println("tasklist: " + line);
                        line = reader.readLine();
                        if(line.contains("Server started at")){
                            process.destroy();
                            break;
                        }
                    }
                }else{
                    System.out.println("Allure is not configured in environment");
                    //need to write code implementations
                }
            } catch (Throwable e) {
                logman.error("Error in generating Allure HTML report, Error = " + e.getMessage());
            }
        }
    }


    public void generateAllureReport(String mavenRepoPath){
        try {
            invokeAspectWeaverJar(mavenRepoPath);
            createEnvironmentXmlFile();
            copyEnvironmentAndCategoriesFile();
            generateAllureHtmlReport();
            logman.info("Allure Report generated successfully");
        } catch (Throwable e) {
            logman.error("Error in generating Allure Report, error = "+e.getMessage());
        }
    }


    public void generateConsolidatedReport(){
        try {
            File reportOutputDirectory = new File("target/consolidatedreport/");
            List<String> jsonFiles = new ArrayList<>();
            String TESTREPORTSPARALLEL="./target/cucumber-parallel/";

            final File folder=new File(TESTREPORTSPARALLEL);
            List<String> resultRep = new ArrayList<>();
            search(".*\\.json",folder,resultRep);
            logman.info("Searching of JSON files under cucumber-parallel is completed");

            for(String s: resultRep){
                jsonFiles.add(s);
            }
            String buildNumber = "1";
            String projectName = "Base";

            Configuration configuration = new Configuration(reportOutputDirectory, projectName);
            configuration.setBuildNumber(buildNumber);
            configuration.addClassifications("PLATFORM", System.getProperty("REPORT_PLATFORM"));
            configuration.addClassifications("TYPE", System.getProperty("REPORT_PLATFORM_TYPE"));
            configuration.addClassifications("ENVIRONMENT", System.getProperty("ENVIRONMENT"));
            configuration.addPresentationModes(PresentationMode.PARALLEL_TESTING);
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            Reportable result = reportBuilder.generateReports();
            logman.info("Cucumber Jenkins Report generated successfully");
        } catch (Throwable e) {
            logman.error("Error in generating Cucumber Jenkins Report, error = "+e.getMessage());
        }
    }

    public void search(final String pattern, final File folder, List<String> result){
        try {
            for(final File f:folder.listFiles()){
                if(f.isDirectory()){
                    search(pattern, f, result);
                }
                if(f.isFile()){
                    if(f.getName().matches(pattern)){
                        result.add(f.getAbsolutePath());
                    }
                }
            }
            logman.info("Json files has been added into List");
        } catch (Exception e) {
            logman.error("Error in searching JSON files , error = "+e.getMessage());
        }
    }


    public void generateCucumberDefaultReport() {
        File reportDirectory = new File(System.getProperty("user.dir") + "/target/cucumber-parallel");
        if (reportDirectory.exists()) {
            ReportMerger reportMerger = new ReportMerger();
            try {
                reportMerger.mergeReports(reportDirectory);
                logman.info("Individual reports are merged successfully for Default Report");
            } catch (Throwable e) {
                logman.error("Error in generating default report, error = "+e.getMessage());
            }
        }
    }
}

package com.cognizant.craft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Util;
import org.apache.tools.ant.util.Base64Converter;
import org.jasypt.util.text.BasicTextEncryptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*import com.cognizant.framework.Report;
import com.cognizant.framework.ReportSettings;
import com.cognizant.framework.Settings;
import com.cognizant.framework.TestParameters;*/

/**
 * Created by c965303 on 10/06/2018.
 */
public class ALMRest {

    public String cookie;
    public String cookie1;
    public String cookies;
    public String cookies1;

    public String cookiesAll;
    public String strResponsefromDefect;
    static ArrayList<String> listOfCookies = new ArrayList<String>();
    static ArrayList<String> listOfCookies1 = new ArrayList<String>();

    public String sFolderPath;
    public String sHTMLPath;
    public String sScreenShotPath;
    public String sCurrentQCTestCase;
    public String sQCURL, sQCProject, sQCDomain;
    public String sQCUserName;
    public String sTestCaseIDFromTestPlan;
    public String sCurrentRunName;

    public String almTestSet;
    public String testCaseName;
    public String testCaseNameQC;
    public String htmlReportPath;
    public String htmlAttachName;
    public String screenShotPath;
    public String reportFolder;
    public String browser;
    public String overAllResult;

    public boolean isConnected = false;

    public String testCase;
    private String almTestSetID;
    String sTestCaseID = "";
    String sTestSetID = "";
    String sFolderID = "";

    private Properties properties;

    public String reportFolderName;
    public String almPassword;

    String almReportFolderPath;
    String almHtmlReportPath;
    String almScreenShotPath;
    FrameworkParameters frameworkParameters;
    String almReportZip;

    /**
     * Constructor to initialize the {@link ScriptHelper} object and in turn the
     * objects wrapped by it
     */
    public ALMRest(Properties properties, Map almValues)
            throws NoSuchAlgorithmException, KeyManagementException, IOException {

        // this.almTestSet = testCase.almTestLabSet;
        this.testCaseName = almValues.get("testCaseName").toString();
        this.testCaseNameQC = almValues.get("testCaseNameQC").toString();
        this.htmlReportPath = almValues.get("htmlReportPath").toString();
        this.htmlAttachName = almValues.get("htmlAttachName").toString();
        this.screenShotPath = almValues.get("screenShotPath").toString();
        this.reportFolder = almValues.get("reportFolder").toString();
        this.overAllResult = almValues.get("overAllResult").toString();

        //ALM upload parameters
        this.almReportFolderPath = reportFolder + Util.getFileSeparator() + testCaseName;
        this.almHtmlReportPath = reportFolder + Util.getFileSeparator() + testCaseName + Util.getFileSeparator() + "HTML Results";
        this.almScreenShotPath = reportFolder + Util.getFileSeparator() + testCaseName + Util.getFileSeparator() + "Screenshots";

        frameworkParameters = FrameworkParameters.getInstance();

      /*  String browser = "";

        switch (scriptHelper.getTestParameters().getBrowser()) {

            case Chrome:
                browser = "Chrome";
                break;
            case FireFox:
                browser = "Firefox";
                break;
            case IE:
                browser = "IE";
                break;
            case Safari:
                browser = "Safari";
                break;
        }*/

        this.browser = "Chrome";
        //this.browser = scriptHelper.getTestParameters().getBrowser().getValue();
        ignoreCert();

        this.properties = properties;

        /*isConnected = qcAuth(properties.getProperty("ALMServerName"),
                properties.getProperty("ALMUserName"),
                properties.getProperty("ALMPassword"));*/
    }


    /**
     * Method to check QC connection
     *
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    public boolean checkALMConnection(String userName, String password)
            throws Exception {

        boolean almConnected;

        //Setting password
        decrypt(password);

        almConnected = qcAuth(properties.getProperty("ALMServerName"), userName,
                almPassword);

        isConnected = true;

        return almConnected;
    }


    /**
     * Method to verify QC user
     *
     * @param sURL
     * @param sUserName
     * @param sPassword
     * @return
     * @throws IOException
     */
    public boolean qcAuth(String sURL, String sUserName, String sPassword)
            throws IOException {
        try {
            ignoreCert();
            URL url = new URL(sURL + "/authentication-point/authenticate");
            String credentials = sUserName + ":" + sPassword;

            Base64Converter Base64 = new Base64Converter();
            String encoding = Base64.encode(credentials.getBytes("UTF-8"));
            HttpsURLConnection connection = (HttpsURLConnection) url
                    .openConnection();

            connection.setRequestProperty("Authorization",
                    String.format("Basic %s", encoding));
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            // Get all the cookies from get Response
            String headerName = null;

            for (int i = 1; (headerName = connection.getHeaderFieldKey(i)) != null; i++) {
                if (headerName.equals("Set-Cookie")) {

                    cookie = connection.getHeaderField(i);

                    if (cookie.contains("LWSSO_COOKIE_KEY"))
                        listOfCookies.add(cookie);
                }
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {

                response.append(inputLine);
            }

            in.close();

            if (responseCode == 200) {

                System.out.println("ALM Login Successful");
                return true;

            } else {

                System.out.println("Error Login to ALM, Response code:"
                        + responseCode);
                return false;
            }

        } catch (Exception e) {

            System.out.println("Error Log in to ALM" + e.getMessage());

            return false;
        }
    }


    /**
     * Method to ignore certificate
     *
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    private void ignoreCert() throws KeyManagementException,
            NoSuchAlgorithmException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs,
                                           String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs,
                                           String authType) {
            }
        }};
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    /**
     * Method to update alm status
     *
     * @return
     * @throws Exception
     */
    public boolean almUpdateTCStatus() throws Exception {

        boolean updateSuccess = false;

        if (isConnected) {

            try {
                updateSuccess = updateALM();

                if (sendEMail()) {
                    if (createALM_MailRequest()) {
                        System.out.println("Email triggered successfully");
                    } else {
                        System.out.println("Email not triggered successfully");
                    }

                }

                logOff();

            } catch (Exception ex) {

            }
        }

        return updateSuccess;
    }


    /**
     * Method to update ALM
     *
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    private boolean updateALM() throws IOException, SAXException,
            ParserConfigurationException {

        getSiteSession(properties.getProperty("ALMServerName"));
        getAllCookies();

        sTestSetID = properties.getProperty("ALMTestSetID");
        sTestCaseID = getTestInstanceID(sTestSetID, testCaseNameQC);

        //sTestCaseIDFromTestPlan = testCase.almTestPlanTCID;

        /*String runStatus = this.overAllResult == this.overAllResult ? "Passed"
                : "Failed";*/

        String runStatus = this.overAllResult;

        if (!createTestRun(sTestCaseID, sTestSetID, runStatus)) {

            return false;

        } else {

            System.out.println("Test Run Created Successfully");
        }

        if (!UpdateTestInstanceStatus(sTestCaseID, runStatus, browser)) {

            return false;

        } else {

            System.out.println("Test Instance status updated successfully");
        }

        String sRunID = getRunID();

        if (sRunID == "") {

            return false;
        }

        createZipFile();

        String sAttachementResponse = UploadAttachement("runs", sRunID,
                reportFolder, reportFolderName);


        if (sAttachementResponse == "") {

            return false;
        } else {

            System.out.println("HTML Report Uploaded Successfully");
        }

/*        File SnapshotfolderFile = new File(screenShotPath);

        for (File file : SnapshotfolderFile.listFiles()) {

            System.out.println(file.getName());

            String sSnapshotResponse = UploadAttachement("runs", sRunID,
                    file.getAbsolutePath(), file.getName());

            if (sSnapshotResponse == "") {

                return false;

            } else {

                System.out.println("Snapshot: " + file.getName()
                        + " Uploaded successfully");
            }
        }*/

        deleteResponseXMLFile();

        deleteALMReportFolder();

        return true;
    }

    private void createZipFile() throws IOException {

        //Creating folder structure for ALM upload
        Path almReportFolder = Paths.get(almReportFolderPath);
        Path almHtmlReport = Paths.get(almHtmlReportPath);
        Path almScreenShot = Paths.get(almScreenShotPath);

        try {
            Files.createDirectories(almReportFolder);
            Files.createDirectories(almHtmlReport);
            Files.createDirectories(almScreenShot);

        } catch (IOException e) {
            throw new FrameworkException("CreateZipFile: Cannot create directories - " + e);
        }

        //Copying HTML to ALM directory
        Files.copy(Paths.get(htmlReportPath + Util.getFileSeparator() + frameworkParameters.getRunConfiguration() + "_" + htmlAttachName),
                Paths.get(almHtmlReport + Util.getFileSeparator() + frameworkParameters.getRunConfiguration() + "_" + htmlAttachName));

        //Copying Screenshots to ALM directory
        List<File> files = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(screenShotPath), "*" + testCaseName + "*")) {
            for (Path entry : stream) {
                files.add(entry.toFile());
            }
        } catch (IOException e) {
            throw new FrameworkException("CreateZipFile: " + e);
        }

        for (File tempFile : files) {
            tempFile.getAbsoluteFile();

            try {
                Files.copy(tempFile.toPath(), Paths.get(almScreenShot + Util.getFileSeparator() + tempFile.getName()));
            } catch (Exception e) {
                throw new FrameworkException("CreateZipFile: " + e);
            }
        }

        //Creating zip for ALM upload
        String sourceDirPath = almReportFolderPath;
        almReportZip = almReportFolderPath + ".zip";

        File file = new File(almReportZip);
        if (file.exists()) {
            file.delete();
        }
        reportFolderName = file.getName();

        Path p = Files.createFile(Paths.get(almReportZip));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
    }

    /**
     * Method to delete response from XML file
     */
    private void deleteResponseXMLFile() {

        File fXmlFile = new File(reportFolder + "/response_" + testCaseName
                + ".xml");
        fXmlFile.delete();
    }

    /**
     * Method to delete report zip folder
     */
    private void deleteALMReportFolder() {

        // Delete ALM report folder
        File directory = new File(reportFolder + Util.getFileSeparator() + reportFolderName.replace(".zip", ""));

        // make sure directory exists
        if (!directory.exists()) {
            System.exit(0);

        } else {
            try {
                delete(directory);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }


        // Delete ALM report folder
        File reportZip = new File(reportFolder + Util.getFileSeparator() + reportFolderName);
        if (reportZip.exists()) {
            reportZip.delete();
        }

    }


    public void delete(File file) throws IOException {

        if (file.isDirectory()) {

            // directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();

            } else {

                // list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    // construct the file structure
                    File fileDelete = new File(file, temp);

                    // recursive delete
                    delete(fileDelete);
                }

                // check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                }
            }

        } else {
            // if file, then delete it
            file.delete();
        }
    }

    /**
     * Method to get test instance ID
     *
     * @param sTestSetID
     * @param sTestCaseName
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    private String getTestInstanceID(String sTestSetID, String sTestCaseName) {

        try {
            sTestCaseName = getFormattedString(sTestCaseName);
            String sResponse = getEntity("test-instances?query={cycle-id["
                    + sTestSetID + "];test.name[" + sTestCaseName + "]}");
            if (sResponse == "") {
                return "";
            }
            SaveCurrentXML(sResponse);
            String sTestCaseID = getCurrentIDFromResponseXML();
            sTestCaseIDFromTestPlan = getCurrentIDFromTestPlan();

            return sTestCaseID;

        } catch (Exception e) {

            System.out.println("Error occured getting the Test Instance:"
                    + e.getMessage());
            return "";
        }
    }

    /**
     * Method to get current ID from Test Plan
     *
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private String getCurrentIDFromTestPlan()
            throws ParserConfigurationException, SAXException, IOException {

        File fXmlFile = new File(reportFolder + "/response_" + testCaseName
                + ".xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("Field");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            if (eElement.getAttribute("Name").equals("test-id")) {
                NodeList valueList = eElement.getElementsByTagName("Value");
                Node valueNode = valueList.item(0);
                Element valueElement = (Element) valueNode;
                return valueElement.getTextContent();
            }
        }

        return "";
    }


    /**
     * Method to get Formatted String
     *
     * @param sCurrentString
     * @return
     */
    private String getFormattedString(String sCurrentString) {

        sCurrentString = sCurrentString.replace("(", "*");
        sCurrentString = sCurrentString.replace(" ", "*");
        sCurrentString = sCurrentString.replace(")", "*");
        sCurrentString = sCurrentString.replace("&", "*");
        sCurrentString = sCurrentString.replace("+", "*");
        sCurrentString = sCurrentString.replace(">", "*");
        sCurrentString = sCurrentString.replace("<", "*");
        sCurrentString = sCurrentString.replace("-", "*");

        return sCurrentString;
    }

    /**
     * Method to upload attachment
     *
     * @param sEntityType
     * @param sIdentifier
     * @param attachmentPath
     * @param attachmentFullName
     * @return
     */
    private String UploadAttachement(String sEntityType, String sIdentifier,
                                     String attachmentPath, String attachmentFullName) {
        attachmentPath = attachmentPath + Util.getFileSeparator() + attachmentFullName;
        try {

            String url = properties.getProperty("ALMServerName") + "/rest/domains/"
                    + properties.getProperty("ALMDomainName") + "/projects/"
                    + properties.getProperty("ALMProjectName") + "/" + sEntityType
                    + "/" + sIdentifier + "/attachments";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            Path path = Paths.get(attachmentPath);
            byte[] data = Files.readAllBytes(path);
            String octetStreamFileName = attachmentFullName;
            con.setRequestProperty("Content-Type", "application/octet-stream");
            con.setRequestProperty("Slug", octetStreamFileName);
            con.setRequestMethod("POST");
            con.setRequestProperty("Cookie", cookiesAll);
            con.setDoOutput(true);
            OutputStream out = con.getOutputStream();
            out.write(data);
            out.flush();
            out.close();

            int responseCode = con.getResponseCode();
            // System.out.println("RESPONSE CODE : " + responseCode);
            BufferedReader in;

            if (responseCode != 201) {

                in = new BufferedReader(new InputStreamReader(
                        con.getErrorStream()));
                return "";

            } else {

                in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
            }

            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {

                response.append(inputLine);
            }

            in.close();
            // System.out.println("RESPONSE: "+response);

            String sReponse = response.toString();
            SaveCurrentXML(sReponse);
            return sReponse;

        } catch (Exception e) {

            System.out.println("Some error occured uploading attachement"
                    + e.getMessage());
            return "";
        }
    }

    /**
     * Method to get Run ID
     *
     * @return
     */
    private String getRunID() {

        try {
            String sResponse = getEntity("runs?query={name[" + sCurrentRunName
                    + "]}");
            SaveCurrentXML(sResponse);
            String sID = getCurrentIDFromResponseXML();
            return sID;

        } catch (Exception e) {

            System.out.println("Error retrieving Run ID" + e.getMessage());
            return "";
        }
    }


    /**
     * Method to get current ID from response XML
     *
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private String getCurrentIDFromResponseXML()
            throws ParserConfigurationException, SAXException, IOException {

        File fXmlFile = new File(reportFolder + "/response_" + testCaseName
                + ".xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("Field");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            if (eElement.getAttribute("Name").equals("id")) {
                NodeList valueList = eElement.getElementsByTagName("Value");
                Node valueNode = valueList.item(0);
                Element valueElement = (Element) valueNode;
                return valueElement.getTextContent();
            }
        }

        return "";
    }

    /**
     * Method to get entity
     *
     * @param sQuery
     * @return
     */
    private String getEntity(String sQuery) {

        try {

            String url = properties.getProperty("ALMServerName") + "/rest/domains/"
                    + properties.getProperty("ALMDomainName") + "/projects/"
                    + properties.getProperty("ALMProjectName") + "/" + sQuery;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Content-Type", "application/xml");
            con.setRequestMethod("GET");
            con.setRequestProperty("Cookie", cookiesAll);

            int responseCode = con.getResponseCode();
            BufferedReader in;

            if (responseCode != 200) {

                in = new BufferedReader(new InputStreamReader(
                        con.getErrorStream()));
                return "";

            } else {

                in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {

                response.append(inputLine);
            }

            in.close();
            String sResponse = response.toString();

            SaveCurrentXML(sResponse);

            return sResponse;

        } catch (Exception e) {

            return "";
        }
    }


    /**
     * Method to update test instance status
     *
     * @param sTestInstanceID
     * @param sStatus
     * @param sBrowser
     * @return
     */
    private boolean UpdateTestInstanceStatus(String sTestInstanceID,
                                             String sStatus, String sBrowser) {

        try {

            Map<String, String> fields = new HashMap<String, String>();
            fields.put("status", sStatus);
            //Below field is not needed user-template-10
            //fields.put("user-template-10", sBrowser);
            String sXML = getFormattedXML("test-instance", fields);

            String sResponse = UpdateEntity(sXML, "test-instances",
                    sTestInstanceID);
            if (sResponse == "") {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {

            System.out.println("Error creating test Run" + e.getMessage());
            return false;
        }
    }

    /**
     * Method to update Entity
     *
     * @param sXML
     * @param sEntityType
     * @param sIdentifier
     * @return
     * @throws Exception
     */
    private String UpdateEntity(String sXML, String sEntityType,
                                String sIdentifier) throws Exception {

        try {

            String url = properties.getProperty("ALMServerName") + "/rest/domains/"
                    + properties.getProperty("ALMDomainName") + "/projects/"
                    + properties.getProperty("ALMProjectName") + "/" + sEntityType
                    + "/" + sIdentifier;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Accept", "application/xml");
            con.setRequestProperty("Content-Type", "application/xml");
            con.setRequestMethod("PUT");
            con.setRequestProperty("Cookie", cookiesAll);
            OutputStream out = con.getOutputStream();
            out.write(sXML.getBytes());
            out.flush();
            out.close();

            int responseCode = con.getResponseCode();

            BufferedReader in;

            if (responseCode != 200) {

                in = new BufferedReader(new InputStreamReader(
                        con.getErrorStream()));
                return "";

            } else {

                in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            String sResponse = response.toString();
            SaveCurrentXML(sResponse);

            return sResponse;

        } catch (Exception e) {

            System.out.println("Error updating the entity" + e.getMessage());
            return "";
        }
    }

    /**
     * Method to get All Cookies
     */
    private void getAllCookies() {

        String strCookies = "" + listOfCookies + "";
        String strCookies1 = "" + listOfCookies1 + "";

        cookiesAll = "LWSSO_COOKIE_KEY="
                + strCookies.split("LWSSO_COOKIE_KEY=")[1].split(";")[0].trim()
                + ";" + "Path=/" + ";" + "QCSession="
                + strCookies1.split("QCSession=")[1].split(";")[0].trim() + ";"
                + "path=/" + ";";
    }


    /**
     * Method to get site session
     *
     * @param sURL
     * @throws IOException
     */
    @SuppressWarnings("unused")
    private void getSiteSession(String sURL) throws IOException {

        try {
            String url = sURL + "/rest/site-session";
            String tempString = "" + listOfCookies + "";
            String tempCookie = "LWSSO_COOKIE_KEY="
                    + tempString.split("LWSSO_COOKIE_KEY=")[1].split(";")[0]
                    .trim() + ";" + "Path=/" + ";";

            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestProperty("Cookie", tempCookie);
            con.setRequestMethod("POST");

            con.setDoOutput(true);
            con.setDoInput(true);
            String urlParameters = "";
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            // System.out.println("Response Code : " + responseCode);

            // Get all the cookies from get Response
            String headerName = null;
            for (int i = 1; (headerName = con.getHeaderFieldKey(i)) != null; i++) {
                cookie1 = con.getHeaderField(i);
                if (headerName.equals("Set-Cookie")) {
                    cookie1 = con.getHeaderField(i);
                    if (cookie1.contains("QCSession"))
                        listOfCookies1.add(cookie1);
                }
            }

        } catch (Exception e) {

        }
    }


    /**
     * Method to create test run
     *
     * @param sTestInstanceID
     * @param sTestSetID
     * @param sStatus
     * @return
     */
    private boolean createTestRun(String sTestInstanceID, String sTestSetID,
                                  String sStatus) {

        try {

            Map<String, String> fields = new HashMap<String, String>();

            String strTimeStmp = "Run_"
                    + getCurrentFormattedTime().replace(" ", "_").replace(":",
                    "-");

            sCurrentRunName = strTimeStmp + "_" + sTestInstanceID;
            System.out.println(sCurrentRunName);

            //Newly added field - user-01
            fields.put("user-01", properties.getProperty("ALMUserName"));
            fields.put("name", sCurrentRunName);
            fields.put("test-instance", "1");
            fields.put("status", sStatus);
            fields.put("testcycl-id", sTestInstanceID);
            fields.put("owner", properties.getProperty("ALMUserName"));
            fields.put("subtype-id", "hp.qc.run.MANUAL");
            fields.put("cycle-id", sTestSetID);
            fields.put("test-id", sTestCaseIDFromTestPlan);

            String sXML = getFormattedXML("run", fields);
            String sResponse = CreateEntity(sXML, "runs");

            if (sResponse == "") {

                return false;

            } else {

                return true;
            }

        } catch (Exception e) {

            System.out.println("Error updating test case status"
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Method to create entity
     *
     * @param sXML
     * @param sEntityType
     * @return
     * @throws Exception
     */
    private String CreateEmailRequest(String sXML, String sEntityType)
            throws Exception {

        try {

            String url = properties.getProperty("ALMServerName") + "/rest/domains/"
                    + properties.getProperty("ALMDomainName") + "/projects/"
                    + properties.getProperty("ALMProjectName") + "/test-instances/"
                    + sTestCaseID + "/" + sEntityType
                    + "/";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Accept", "application/xml");
            con.setRequestProperty("Content-Type", "application/xml");
            con.setRequestMethod("POST");
            con.setRequestProperty("Cookie", cookiesAll);
            OutputStream out = con.getOutputStream();
            out.write(sXML.getBytes());
            out.flush();
            out.close();

            int responseCode = con.getResponseCode();
            BufferedReader in;

            if (responseCode != 201) {

                in = new BufferedReader(new InputStreamReader(
                        con.getErrorStream()));

                return "";

            } else {

                in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {

                response.append(inputLine);
            }

            in.close();

            String strResponse = response.toString();
            SaveCurrentXML(strResponse);

            return strResponse;

        } catch (Exception e) {

            System.out.println("Error creating entity" + e.getMessage());
            return "";
        }
    }

    /**
     * Method to get current formatted time
     *
     * @return
     */
    private String getCurrentFormattedTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Method to save current XML
     *
     * @param sResponse
     * @throws IOException
     */
    private void SaveCurrentXML(String sResponse) throws IOException {

        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(reportFolder + "/response_"
                + testCaseName + ".xml"));
        writer.write(sResponse.toString());
        writer.close();
    }

    /**
     * Method to get formatted XML
     *
     * @param entityType
     * @param fields
     * @return
     */
    private String getFormattedXML(String entityType, Map<String, String> fields) {

        String sXML = "<Entity Type=\"" + entityType + "\"><Fields>";

        Iterator<Entry<String, String>> fieldsIterator = fields.entrySet()
                .iterator();

        while (fieldsIterator.hasNext()) {

            Entry<String, String> singleField = fieldsIterator.next();
            String sFieldName = singleField.getKey();
            String sFieldValue = singleField.getValue();
            sXML = sXML + "<Field Name=\"" + sFieldName + "\"><Value>"
                    + sFieldValue + "</Value></Field>";
        }

        sXML = sXML + "</Fields></Entity>";
        return sXML;

    }


    /**
     * Method to log off
     *
     * @throws IOException
     */
    @SuppressWarnings("unused")
    public void logOff() throws IOException {

        String url = properties.getProperty("ALMServerName") + "/rest/site-session";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestProperty("Cookie", cookiesAll);
        con.setRequestMethod("DELETE");
        con.setDoOutput(true);
        con.setDoInput(true);

        String urlParameters = "";
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        // System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        // System.out.println(response.toString());
        System.out.println("ALM Log off successful");
    }

    public void decrypt(String password) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("BOI");

        almPassword = textEncryptor.decrypt(password);
    }

    public boolean sendEMail() {

        boolean sendMail = false;

        try {
            sendMail = Boolean.valueOf(properties.getProperty("SendEmail"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return sendMail;
    }


    /**
     * Method to create test run
     *
     * @return
     */
    private boolean createALM_MailRequest() {

        try {

            Map<String, String> fields = new HashMap<String, String>();

            fields.put("to-recipients", properties.getProperty("ToRecipients").trim());
            fields.put("subject", sCurrentRunName);
            fields.put("comment", "1");

            //String sXML = getFormattedXML("mail", fields);
            String sXML = "<mail>\n" +
                    "    <to-recipients>" + properties.getProperty("ToRecipients").trim() + "</to-recipients>\n" +
                    "    <subject>Smoke Test Status: " + overAllResult + "</subject>\n" +
                    "    <comment><![CDATA[<b>Test Execution Report of: </b>" + testCaseName + "]]></comment>\n" +
                    "</mail>";


            String sResponse = CreateEmailRequest(sXML, "mail");

            if (sResponse == "") {

                return false;

            } else {

                return true;
            }

        } catch (Exception e) {

            System.out.println("Error in sending email"
                    + e.getMessage());
            return false;
        }
    }


    /**
     * Method to create entity
     *
     * @param sXML
     * @param sEntityType
     * @return
     * @throws Exception
     */
    private String CreateEntity(String sXML, String sEntityType)
            throws Exception {

        try {

            String url = properties.getProperty("ALMServerName") + "/rest/domains/"
                    + properties.getProperty("ALMDomainName") + "/projects/"
                    + properties.getProperty("ALMProjectName") + "/" + sEntityType
                    + "/";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Accept", "application/xml");
            con.setRequestProperty("Content-Type", "application/xml");
            con.setRequestMethod("POST");
            con.setRequestProperty("Cookie", cookiesAll);
            OutputStream out = con.getOutputStream();
            out.write(sXML.getBytes());
            out.flush();
            out.close();

            int responseCode = con.getResponseCode();
            BufferedReader in;

            if (responseCode != 201) {

                in = new BufferedReader(new InputStreamReader(
                        con.getErrorStream()));

                return "";

            } else {

                in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {

                response.append(inputLine);
            }

            in.close();

            String strResponse = response.toString();
            SaveCurrentXML(strResponse);

            return strResponse;

        } catch (Exception e) {

            System.out.println("Error creating entity" + e.getMessage());
            return "";
        }
    }
}

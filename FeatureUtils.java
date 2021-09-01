package com.boi.grp.utilities;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by C112083 on 25/01/2021.
 */
public class FeatureUtils {
    public Logger logMan;

    public FeatureUtils(){
        logMan = LogManagerPreRun.getInstance();
    }


    public void readAllFeatureFile(String initialPath,String finalPath,Map<String,List<Map<String,String>>> testData,Map<String,List<String>> colNamePerScenarioTestData) throws Exception{
        try {
            File file=new File(System.getProperty("user.dir")+"/"+initialPath);
            String[] featureArray = file.list();
            for (String featureName:featureArray) {
                FileReader filereader=new FileReader(System.getProperty("user.dir")+"/"+initialPath+"/"+featureName);
                BufferedReader bufferedReader=new BufferedReader(filereader);
                StringBuilder stringBuilder=new StringBuilder();
                String line =bufferedReader.readLine();
                while(line!=null){
                    stringBuilder.append(line+"!!");
                    line=bufferedReader.readLine();
                }
                String featureInOneLine =stringBuilder.toString();
                //logMan.debug("old data"+stringBuilder.toString());
                String featureWithAppendedData = getScenarioNameAndParseForEachOfThem(featureInOneLine,testData,colNamePerScenarioTestData);
                //logMan.debug("new data = "+featureWithAppendedData);
                PrintWriter printWriter=new PrintWriter(new FileWriter(System.getProperty("user.dir")+"/"+finalPath+"/"+featureName));
                String[] arr = featureWithAppendedData.split("!!");
                for (String featureLine :arr) {
                    printWriter.println(featureLine);
                }
                printWriter.flush();
                printWriter.close();
                logMan.info("New Feature file = "+featureName+" is created with data appended from Excel file at location = "+finalPath);
            }
        } catch (IOException e) {
            logMan.error("Error in readAllFeatureFile, Error =  "+e.getMessage());
        }
    }


    public String getScenarioNameAndParseForEachOfThem(String text, Map<String,List<Map<String,String>>> data,Map<String,List<String>> colNamePerScenarioTestData){
        String newText= null;
        try {
            newText = text;
            List<Integer> listOfScenarioIndex = searchPatternForScenario("Scenario",newText);
            String splitter="!!";

            for (int i=0;i<listOfScenarioIndex.size();i++){
                if(newText.length()!=text.length()){
                    listOfScenarioIndex=searchPatternForScenario("Scenario",newText);
                }
                int endpoint = newText.indexOf(splitter, listOfScenarioIndex.get(i));
                String textWithScenarioName=newText.substring(listOfScenarioIndex.get(i),endpoint);
                String scenarioName = textWithScenarioName.split(":")[1].trim();
                if(data.containsKey(scenarioName)){
                    logMan.info("Test data for scenarioName, '"+scenarioName+"' is = "+data.get(scenarioName));
                    newText=parseFeatureFileAndAppendDataFromExcelFile(newText,splitter,scenarioName,data,colNamePerScenarioTestData);
                }
            }
        } catch (Exception e) {
            logMan.error("Error in getScenarioNameAndParseForEachOfThem method, Error = "+e.getMessage());
        }
        return newText;
    }

    public String parseFeatureFileAndAppendDataFromExcelFile(String text, String splitter, String regex,Map<String,List<Map<String,String>>> testData,Map<String,List<String>> colNamePerScenarioTestData ){
        //List<Integer> scenarioRegex1 = searchPattern(regex, text);
        List<Integer> scenarioRegex = searchPatternUsingIndex(regex, text, 0);
        int value = text.lastIndexOf("Scenario", scenarioRegex.get(0));
        String outlineSubstring = text.substring(value, scenarioRegex.get(0));
        if(outlineSubstring.contains("Outline")){
            //logic for outline
            List<Integer> examplesRegex = searchPatternUsingIndex("Examples",text, scenarioRegex.get(1));
            //code for number of spaces
            int spacePosition = text.lastIndexOf("!",examplesRegex.get(0));
            String spaceToBeAdded=text.substring(spacePosition+1,examplesRegex.get(0))+"  ";

            int startIndex = text.indexOf(splitter, examplesRegex.get(1))+splitter.length();
            String firstDataForExamplesHeader = text.substring(0, startIndex);
            String secondDataForExamplesHeader=text.substring(startIndex);
            //for list of column names for each scenarioName
            List<String> list = colNamePerScenarioTestData.get(regex);
            StringBuilder build = new StringBuilder();
            build.append(spaceToBeAdded).append("|");
            for (String aList : list) {
                build.append(aList).append("|");
            }
            build.append(splitter);
            text=firstDataForExamplesHeader+build.toString()+secondDataForExamplesHeader;
            //logic for appending data for examples section
            int endIndex=text.indexOf(splitter,startIndex);
            int nextLineStartPoint = endIndex + splitter.length();
            String firstPartData = text.substring(0, nextLineStartPoint);
            String secondPartData = text.substring(nextLineStartPoint);

            List<Map<String, String>> listData = testData.get(regex);
            StringBuilder stringBuilder = new StringBuilder();
            for (Map<String,String> tempData:listData) {
                stringBuilder.append(spaceToBeAdded).append("|");
                for (String aList : list) {
                    stringBuilder.append(tempData.get(aList)).append("|");
                }
                stringBuilder.append(splitter);
            }
            String stringToBeAppended = stringBuilder.toString();
            String newScenarioText = firstPartData + stringToBeAppended + secondPartData;
            return newScenarioText;
        }else{
            //logic for normal
        	int endPoint = text.indexOf("Scenario",scenarioRegex.get(1));
            if(endPoint==-1){
                endPoint=text.length();
            }
            String reg = "DataFromExcel";
            String newScenarioText = null;
            int dataModificationInstance = text.indexOf(reg, scenarioRegex.get(0));
            while (dataModificationInstance<endPoint){
                int spacePosition = text.indexOf(" ", dataModificationInstance);
                int exclamationPosition=text.indexOf(splitter,dataModificationInstance);
                String firstPartData;
                String secondPartData;
                String columnName;
                int columnStartPosition;
                if(exclamationPosition>spacePosition){
                    firstPartData=text.substring(0,dataModificationInstance);
                    columnStartPosition = text.lastIndexOf("_", spacePosition);
                    columnName = text.substring(columnStartPosition+1, spacePosition);
                    if(columnName.contains("\"")){
                        int secondPartStartingPosition = text.indexOf("\"", columnStartPosition);
                        columnName=columnName.split("\"")[0];
                        secondPartData=text.substring(secondPartStartingPosition);
                    }else{
                        int secondPartStartingPosition=columnStartPosition+1+columnName.length();
                        secondPartData=text.substring(secondPartStartingPosition);
                    }
                }else{
                    firstPartData=text.substring(0,dataModificationInstance);
                    columnStartPosition = text.lastIndexOf("_", exclamationPosition);
                    columnName = text.substring(columnStartPosition+1, exclamationPosition);
                    if(columnName.contains("\"")){
                        int secondPartStartingPosition = text.indexOf("\"", columnStartPosition);
                        columnName=columnName.split("\"")[0];
                        secondPartData=text.substring(secondPartStartingPosition);
                    }else{
                        int secondPartStartingPosition=columnStartPosition+1+columnName.length();
                        secondPartData=text.substring(secondPartStartingPosition);
                    }
                }

                List<Map<String, String>> data = testData.get(regex);
                if(data.size()==1){
                    Map<String, String> mapData = data.get(0);
                    String stringToBeAppended = mapData.get(columnName);
                    newScenarioText = firstPartData + stringToBeAppended + secondPartData;
                    text=newScenarioText;
                    dataModificationInstance=newScenarioText.indexOf(reg,dataModificationInstance);
                    if(dataModificationInstance==-1){
                        break;
                    }
                }
            }
            return newScenarioText;
        }
    }


    /**
     *
     * @param regex
     * @param text
     * @return
     */

    public List<Integer> searchPattern(String regex, String text){
        List<Integer> list = new ArrayList<Integer>();
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            boolean found = false;
            while(matcher.find()){
                /*System.out.println("I found the text "+matcher.group()+" starting at index "+
                        matcher.start()+" and ending at index "+matcher.end());*/
                list.add(matcher.start());
                list.add(matcher.end());
                found=true;
            }

            if(!found){
                logMan.warn("No match found.");
            }
        } catch (Exception e) {
            logMan.error("Error in searchPattern method, for regular expression = "+regex+" , Error = "+e.getMessage());
        }
        return list;
    }

    public List<Integer> searchPatternForScenario(String regex, String text){
        List<Integer> list = new ArrayList<Integer>();
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            boolean found = false;
            while(matcher.find()){
               /* System.out.println("I found the text "+matcher.group()+" starting at index "+
                        matcher.start()+" and ending at index "+matcher.end());*/
                list.add(matcher.end());
                found=true;
            }

            if(!found){
                logMan.warn("No match found.");
            }
        } catch (Exception e) {
            logMan.error("Error in searchPatternForScenario method, for regular expression = "+regex+" , Error = "+e.getMessage());
        }
        return list;
    }

    public List<Integer> searchPatternUsingIndex(String regex, String text, int startingFromIndex){
        List<Integer> list = null;
        try {
            list = new ArrayList<Integer>();
            int startPositionOfRegex=text.indexOf(regex,startingFromIndex);
            int endPositionOfRegex=startPositionOfRegex+regex.length();
            list.add(startPositionOfRegex);
            list.add(endPositionOfRegex);
        } catch (Exception e) {
            logMan.error("Error in searchPatternUsingIndex method, Error = "+e.getMessage());
        }
        return list;
    }

}

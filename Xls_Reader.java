package com.boi.grp.utilities;

import org.apache.log4j.Logger;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTAutoFilter;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFilterColumn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


public class Xls_Reader {
        public  String path;
        public FileInputStream fis = null;
        public FileOutputStream fileOut =null;
        private XSSFWorkbook workbook = null;
        private XSSFSheet sheet = null;
        private XSSFRow row   =null;
        private XSSFCell cell = null;
        public Logger logMan;

        public Xls_Reader(String path) {
            logMan = LogManagerPreRun.getInstance();
            this.path=path;
            try {
                fis = new FileInputStream(path);
                workbook = new XSSFWorkbook(fis);
                fis.close();
                logMan.info("Excel XSSFWorkbook object is created successfully");
            } catch (Exception e) {
                logMan.error("Error in creating object of excel file, Error = "+e.getMessage());
            }

        }
        // returns the row count in a sheet
        public int getRowCount(String sheetName){
            try {
                int index = workbook.getSheetIndex(sheetName);
                if(index==-1) {
                    logMan.info("Invalid sheetName, in getRowCount method");
                    return 0;
                }
                else{
                    sheet = workbook.getSheetAt(index);
                    int number=sheet.getLastRowNum()+1;
                    logMan.info("row number is returned successfully for sheet = "+sheetName+" as "+number);
                    return number;
                }
            } catch (Exception e) {
                logMan.error("Error in getRowCount method, Error = "+e.getMessage());
                return 0;
            }
        }

        // returns the data from a cell
        public String getCellData(String sheetName,String colName,int rowNum){
            try{
                if(rowNum <=0)
                    return "";

                int index = workbook.getSheetIndex(sheetName);
                int col_Num=-1;
                if(index==-1)
                    return "";

                sheet = workbook.getSheetAt(index);
                row=sheet.getRow(0);
                for(int i=0;i<row.getLastCellNum();i++){
                    if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                        col_Num=i;
                }
                if(col_Num==-1)
                    return "";

                sheet = workbook.getSheetAt(index);
                row = sheet.getRow(rowNum-1);
                if(row==null)
                    return "";

                cell = row.getCell(col_Num);
                if(cell==null)
                    return "";
                if(cell.getCellType()== CellType.STRING)
                    return cell.getStringCellValue();
                else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA ){
                    String cellText  = String.valueOf(cell.getNumericCellValue());
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // format in form of M/D/YY
                        double d = cell.getNumericCellValue();
                        Calendar cal =Calendar.getInstance();
                        cal.setTime(HSSFDateUtil.getJavaDate(d));
                        cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                        cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH)+1 + "/" + cellText;
                    }
                    return cellText;
                }else if(cell.getCellType()==CellType.BLANK)
                    return "";
                else
                    return String.valueOf(cell.getBooleanCellValue());
            }
            catch(Exception e){
                logMan.error("row "+rowNum+" or column "+colName +" does not exist in xls");
                return "row "+rowNum+" or column "+colName +" does not exist in xls";
            }
        }

        // returns the data from a cell
        public String getCellData(String sheetName,int colNum,int rowNum){
            try{
                if(rowNum <=0)
                    return "";

                int index = workbook.getSheetIndex(sheetName);
                if(index==-1)
                    return "";

                sheet = workbook.getSheetAt(index);
                row = sheet.getRow(rowNum-1);
                if(row==null)
                    return "";
                cell = row.getCell(colNum);
                if(cell==null)
                    return "";

                if(cell.getCellType()==CellType.STRING)
                    return cell.getStringCellValue();
                else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA ){
                    String cellText  = String.valueOf(cell.getNumericCellValue());
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // format in form of M/D/YY
                        double d = cell.getNumericCellValue();
                        Calendar cal =Calendar.getInstance();
                        cal.setTime(HSSFDateUtil.getJavaDate(d));
                        cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                        cellText = cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
                    }
                    return cellText;
                }else if(cell.getCellType()==CellType.BLANK)
                    return "";
                else
                    return String.valueOf(cell.getBooleanCellValue());
            }
            catch(Exception e){
                logMan.error("row "+rowNum+" or column "+colNum +" does not exist  in xls");
                return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
            }
        }

        // returns true if data is set successfully else false
        public boolean setCellData(String sheetName,String colName,int rowNum, String data){
            try{
                if(rowNum<=0)
                    return false;

                int index = workbook.getSheetIndex(sheetName);
                int colNum=-1;
                if(index==-1)
                    return false;

                sheet = workbook.getSheetAt(index);
                row=sheet.getRow(0);
                for(int i=0;i<row.getLastCellNum();i++){
                    if(row.getCell(i).getStringCellValue().trim().equals(colName))
                        colNum=i;
                }
                if(colNum==-1)
                    return false;

                sheet.autoSizeColumn(colNum);
                row = sheet.getRow(rowNum-1);
                if (row == null)
                    row = sheet.createRow(rowNum-1);

                cell = row.getCell(colNum);
                if (cell == null)
                    cell = row.createCell(colNum);

                cell.setCellValue(data);
                fileOut = new FileOutputStream(path);
                workbook.write(fileOut);
                fileOut.close();
            }
            catch(Exception e){
                logMan.error("Error in setCellData method, Error = "+e.getMessage());
                return false;
            }
            return true;
        }


        // returns true if data is set successfully else false
        public boolean setCellData(String sheetName,String colName,int rowNum, String data,String url){
            try{
                fis = new FileInputStream(path);
                workbook = new XSSFWorkbook(fis);
                if(rowNum<=0)
                    return false;

                int index = workbook.getSheetIndex(sheetName);
                int colNum=-1;
                if(index==-1)
                    return false;

                sheet = workbook.getSheetAt(index);
                row=sheet.getRow(0);
                for(int i=0;i<row.getLastCellNum();i++){
                    if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
                        colNum=i;
                }

                if(colNum==-1)
                    return false;
                sheet.autoSizeColumn(colNum); //ashish
                row = sheet.getRow(rowNum-1);
                if (row == null)
                    row = sheet.createRow(rowNum-1);

                cell = row.getCell(colNum);
                if (cell == null)
                    cell = row.createCell(colNum);

                cell.setCellValue(data);
                XSSFCreationHelper createHelper = workbook.getCreationHelper();
                //cell style for hyperlinks
                //by default hypelrinks are blue and underlined
                CellStyle hlink_style = workbook.createCellStyle();
                XSSFFont hlink_font = workbook.createFont();
                hlink_font.setUnderline(XSSFFont.U_SINGLE);
                hlink_font.setColor(IndexedColors.BLUE.getIndex());
                hlink_style.setFont(hlink_font);
                //hlink_style.setWrapText(true);
                XSSFHyperlink link = createHelper.createHyperlink(HyperlinkType.FILE);
                link.setAddress(url);
                cell.setHyperlink(link);
                cell.setCellStyle(hlink_style);
                fileOut = new FileOutputStream(path);
                workbook.write(fileOut);
                fileOut.close();
            }
            catch(Exception e){
                logMan.error("Error in setCellData method with hyperlink, Error = "+e.getMessage());
                return false;
            }
            return true;
        }

        // returns true if sheet is created successfully else false
        public boolean addSheet(String  sheetname){
            FileOutputStream fileOut;
            try {
                workbook.createSheet(sheetname);
                fileOut = new FileOutputStream(path);
                workbook.write(fileOut);
                fileOut.close();
                logMan.info("Sheet , = "+sheetname+" added successfully");
            } catch (Exception e) {
                logMan.error("Error in addSheet method, Error = "+e.getMessage());
                return false;
            }
            return true;
        }

        // returns true if sheet is removed successfully else false if sheet does not exist
        public boolean removeSheet(String sheetName){
            try {
                int index = workbook.getSheetIndex(sheetName);
                if(index==-1)
                    return false;

                FileOutputStream fileOut;
                workbook.removeSheetAt(index);
                fileOut = new FileOutputStream(path);
                workbook.write(fileOut);
                fileOut.close();
                logMan.info("Sheet , = "+sheetName+" removed successfully");
            } catch (Exception e) {
                logMan.error("Error in addSheet method, Error = "+e.getMessage());
                return false;
            }
            return true;
        }

        // returns true if column is created successfully
        public boolean addColumn(String sheetName,String colName){
            try{
                fis = new FileInputStream(path);
                workbook = new XSSFWorkbook(fis);
                int index = workbook.getSheetIndex(sheetName);
                if(index==-1)
                    return false;

                XSSFCellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                sheet=workbook.getSheetAt(index);
                row = sheet.getRow(0);
                if (row == null)
                    row = sheet.createRow(0);

                if(row.getLastCellNum() == -1)
                    cell = row.createCell(0);
                else
                    cell = row.createCell(row.getLastCellNum());

                cell.setCellValue(colName);
                cell.setCellStyle(style);
                fileOut = new FileOutputStream(path);
                workbook.write(fileOut);
                fileOut.close();
                logMan.info(colName+" Column added successfully for sheet = "+sheetName);
            }catch(Exception e){
                logMan.error("Error in addColumn method");
                return false;
            }
            return true;
        }

        // removes a column and all the contents
        public boolean removeColumn(String sheetName, int colNum) {
            try{
                if(!isSheetExist(sheetName))
                    return false;

                fis = new FileInputStream(path);
                workbook = new XSSFWorkbook(fis);
                sheet=workbook.getSheet(sheetName);
                XSSFCellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
                XSSFCreationHelper createHelper = workbook.getCreationHelper();
                style.setFillPattern(FillPatternType.NO_FILL);

                for(int i =0;i<getRowCount(sheetName);i++){
                    row=sheet.getRow(i);
                    if(row!=null){
                        cell=row.getCell(colNum);
                        if(cell!=null){
                            cell.setCellStyle(style);
                            row.removeCell(cell);
                        }
                    }
                }
                fileOut = new FileOutputStream(path);
                workbook.write(fileOut);
                fileOut.close();
                logMan.info("Successfully removed the contents of cloumnNo = "+colNum+" for sheet = "+sheetName);
            }
            catch(Exception e){
                logMan.error("Error in removeColumn method and all it's contents ");
                return false;
            }
            return true;
        }

        // find whether sheets exists
        public boolean isSheetExist(String sheetName){
            try {
                int index = workbook.getSheetIndex(sheetName);
                if(index==-1){
                    index=workbook.getSheetIndex(sheetName.toUpperCase());
                    if(index==-1) {
                        logMan.error(sheetName + " does not exists");
                        return false;
                    }
                    else {
                        logMan.info(sheetName + " exists");
                        return true;
                    }
                }else
                    logMan.info(sheetName +" exists");
                    return true;
            } catch (Exception e) {
                logMan.error("Erro rin isSheetExist method");
                e.printStackTrace();
            }
            return true;
        }

        // returns number of columns in a sheet
        public int getColumnCount(String sheetName){
            try {
                if(!isSheetExist(sheetName))
                    return -1;

                sheet = workbook.getSheet(sheetName);
                row = sheet.getRow(0);

                if(row==null)
                    return -1;
                logMan.info("Number of column in sheet = "+sheetName +", is = "+row.getLastCellNum());
                return row.getLastCellNum();
            } catch (Exception e) {
                logMan.error("Error in getColumnCount method for sheet = "+sheetName);
                return -1;
            }
        }

        //String sheetName, String testCaseName,String keyword ,String URL,String message
        public boolean addHyperLink(String sheetName,String screenShotColName,String testCaseName,int index,String url,String message){
            try {
                url=url.replace('\\', '/');
                if(!isSheetExist(sheetName))
                    return false;

                sheet = workbook.getSheet(sheetName);
                for(int i=2;i<=getRowCount(sheetName);i++){
                    if(getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)){
                        setCellData(sheetName, screenShotColName, i+index, message,url);
                        break;
                    }
                }
            } catch (Exception e) {
                logMan.error("Error in addHyperLink method , Error = "+e.getMessage());
                return false;
            }
            logMan.info("Hyperlink added successfully");
            return true;
        }


        public int getCellRowNum(String sheetName,String colName,String cellValue){

            try {
                for(int i=2;i<=getRowCount(sheetName);i++){
                    if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
                        logMan.info("row number for the cell with value = "+cellValue+"in cloumn = "+colName +", is = "+i);
                        return i;
                    }
                }
            } catch (Exception e) {
                logMan.error("Error in getCellRowNum methos, Error = "+e.getMessage());
                e.printStackTrace();
            }
            logMan.error("row number for the cell with value = "+cellValue+"in cloumn = "+colName +", is not found");
            return -1;
        }


    //added new methods
        public List<String> getAllColumnName(String sheetName){
            List<String> columnArrayList = null;
            try {
                columnArrayList = new ArrayList<String>();
                this.sheet = workbook.getSheetAt(workbook.getSheetIndex(sheetName));
                this.row = this.sheet.getRow(0);
                for(int i =0 ; i<this.row.getLastCellNum();i++){
                    String key = this.row.getCell(i).getStringCellValue();
                    if(!key.isEmpty()){
                        columnArrayList.add(key);
                    }

                }
            } catch (Exception e) {
                logMan.error("Error in getAllColumnName method, Error = "+e.getMessage());
            }
            return columnArrayList;
        }

        public void setFilter(String sheetName,int rowNo,String columnName,String filterValue){
            try {
                int index = workbook.getSheetIndex(sheetName);
                this.sheet=workbook.getSheetAt(index);
                this.sheet.setAutoFilter(new CellRangeAddress(0, 5100, 0, this.sheet.getRow(0).getLastCellNum()));
                this.row=sheet.getRow(rowNo);
                int cellIndex=getCellColumnNo(sheetName, columnName);

                CTAutoFilter filter = sheet.getCTWorksheet().getAutoFilter();
                CTFilterColumn filterColumn = filter.addNewFilterColumn();
                filterColumn.setColId(cellIndex);

                for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
                    if(!(sheet.getRow(i).getZeroHeight())){
                        Row r=sheet.getRow(i);
                        if(r.getCell(cellIndex)==null){
                            r.setZeroHeight(true);
                        }else{
                            String value=r.getCell(cellIndex).toString();
                            if(value.endsWith(".0")){
                                value=value.substring(0,value.length()-2);
                            }else if(value.endsWith(".00")){
                                value=value.substring(0,value.length()-3);
                            }
                            if(!value.equalsIgnoreCase(filterValue)){
                                logMan.info("Filter is set successfully in cloumn = "+columnName+" with value = "+filterValue);
                                r.setZeroHeight(true);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logMan.error("Error in setFilter method, Error = "+e.getMessage());
            }
        }

    public void setFilterForMultipleValues(String sheetName,int rowNo,String columnName,List<String> filterValue){
        try {
            int index = workbook.getSheetIndex(sheetName);
            this.sheet=workbook.getSheetAt(index);
            sheet.setAutoFilter(new CellRangeAddress(0, 7000, 0, sheet.getRow(0).getLastCellNum()));
            row=sheet.getRow(rowNo);
            int cellIndex=getCellColumnNo(sheetName, columnName);

            CTAutoFilter filter = sheet.getCTWorksheet().getAutoFilter();
            CTFilterColumn filterColumn = filter.addNewFilterColumn();
            filterColumn.setColId(cellIndex);

            for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
                if(!sheet.getRow(i).getZeroHeight()){
                    Row r=sheet.getRow(i);
                    if(r.getCell(cellIndex)==null){
                        r.setZeroHeight(true);
                    }else{
                        String value=r.getCell(cellIndex).toString();
                        if(value.endsWith(".0")){
                            value=value.substring(0,value.length()-2);
                        }else if(value.endsWith(".00")){
                            value=value.substring(0,value.length()-3);
                        }
                        if(!filterValue.contains(value)){
                            logMan.info("Filter is set successfully in cloumn = "+columnName+" with value = "+filterValue);
                            r.setZeroHeight(true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logMan.error("Error in setFilterForMultipleValues method, Error = "+e.getMessage());
        }
    }

    public int getCellColumnNo(String sheetName,String columnName){
        int columnNum= 0;
        try {
            int index = workbook.getSheetIndex(sheetName);
            this.sheet=workbook.getSheetAt(index);
            row=sheet.getRow(0);
            columnNum = 0;
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if(row.getCell(i).getStringCellValue().equalsIgnoreCase(columnName)){
                    columnNum=i;
                    break;
                }
            }
        } catch (Exception e) {
            logMan.error("Error in getCellColumnNo method, Error = "+e.getMessage());
        }
        //logMan.info("Column Number for the cloumn = "+columnName+" is = "+columnNum);
        return columnNum;
    }


    public List<String> getAllCellDataForColumn(String sheetName,String columnName){
        List<String> valuelist = null;
        try {
            valuelist = new ArrayList<String>();
            int index = workbook.getSheetIndex(sheetName);
            this.sheet=workbook.getSheetAt(index);
            row=sheet.getRow(0);
            int columnNum=0;
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if(row.getCell(i).getStringCellValue().equalsIgnoreCase(columnName)){
                    columnNum=i;
                    break;
                }
            }

            for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
                row=sheet.getRow(i);
                //cell=row.getCell(columnNum);
                this.cell=this.row.getCell(columnNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if(cell==null){
                    cell=row.createCell(columnNum);
                }
                String value = null;
                if(cell.getCellType()==CellType.STRING){
                    value=row.getCell(columnNum).getStringCellValue();
                }else if(cell.getCellType()==CellType.NUMERIC){
                    value=String.valueOf(row.getCell(columnNum).getNumericCellValue());
                }else if(cell.getCellType()==CellType.BLANK){
                    value="";
                }else{
                    value= "";
                }
                valuelist.add(value);
            }
        } catch (Exception e) {
            logMan.error("Error in getAllCellDataForColumn method , Error = "+e.getMessage());
        }
        return valuelist;

    }

    public Map<String, String> getDataAsMapForFilteredRow(String sheetName){
        Map<String, String> map = null;
        try {
            map = new HashMap<String , String>();
            ArrayList<String> keyArrayList = new ArrayList<String>();
            ArrayList<String> valueArrayList = new ArrayList<String>();
            this.sheet = workbook.getSheetAt(workbook.getSheetIndex(sheetName));
            this.row = this.sheet.getRow(0);
            for(int i =0 ; i< this.row.getLastCellNum(); i++){
                String key = this.row.getCell(i).getStringCellValue();
                keyArrayList.add(key);
            }

            List<Integer> visibleRows = getVisibleRowIndexes(sheetName);
            int rownumber = visibleRows.get(0)-1;
            this.row = this.sheet.getRow(rownumber);
            String valuef = null;

            for(int i =0 ; i< this.row.getLastCellNum(); i++){
                //XSSFCell cell = this.row.getCell(i);
                XSSFCell cell=this.row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if(cell==null){
                    valuef = "";
                }else{
                    if(cell.getCellType()==CellType.STRING){
                        valuef= cell.getStringCellValue();
                    }else if(cell.getCellType()==CellType.NUMERIC){
                        valuef= String.valueOf(cell.getNumericCellValue());
                    }else if(cell.getCellType()==CellType.BLANK){
                        valuef="";
                    }else{
                        valuef="";
                    }
                }

                if(valuef!=null){
                    valueArrayList.add(valuef);
                }
            }

            for(int i =0 ; i<valueArrayList.size(); i++){
                if(valueArrayList.get(i)!=null){
                    if(!(valueArrayList.get(i).equals(""))){
                        map.put(keyArrayList.get(i),valueArrayList.get(i));
                    }
                }
            }
        } catch (Exception e) {
            logMan.error("Error in getDataAsMapForFilteredRow, Error = "+e.getMessage());
        }
        logMan.info("Data as map = "+map);
        return map;
    }

    public void writeOutputToFile(String myFilename) throws IOException {
        try {
            fileOut = new FileOutputStream(new File(myFilename));
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            logMan.error("Error in writeOutputToFile method, Error = "+e.getMessage());
        }
    }

    public void clearPreviousData(String sheetname) {
        try {
            sheet = workbook.getSheet(sheetname);
            for(int i=1; i<= sheet.getLastRowNum(); i++){
                for(int j=0; j<=sheet.getRow(i).getLastCellNum(); j++){
                    sheet.getRow(i).getCell(j).setCellValue("");
                }
            }
        } catch (Exception e) {
            logMan.error("Error in clearPreviousData, Error = "+e.getMessage());
        }
    }

    public int getVisibleRowCount(String sheetName){
        int count= 0;
        try {
            count = 0;
            int index = workbook.getSheetIndex(sheetName);
            if(index==-1){
                return 0;
            }else{
                sheet=workbook.getSheetAt(index);
                for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
                    if(!sheet.getRow(i).getZeroHeight()){
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            logMan.error("Error in getVisibleRowCount method, Error = "+e.getMessage());
        }
        logMan.info("visible run count = "+count);
        return count;
    }

    public List<Integer> getVisibleRowIndexes(String sheetName){
        List<Integer> visibleRows = new ArrayList<Integer>();
        try {
            int index = workbook.getSheetIndex(sheetName);
            if(index==-1){
                return null;
            }else{
                this.sheet=workbook.getSheetAt(index);
                for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
                    if(!sheet.getRow(i).getZeroHeight()){
                        visibleRows.add(i+1);
                    }
                }
            }
        } catch (Exception e) {
            logMan.error("Error in getVisibleRowIndexes method, Error = "+e.getMessage());
        }
        return visibleRows;
    }

    public String getCellValueFromVisibleRows(String sheetName,String columnName){
        try {
            int index = workbook.getSheetIndex(sheetName);
            this.sheet=workbook.getSheetAt(index);
            String cellValue;
            List<Integer> visibleRows = getVisibleRowIndexes(sheetName);

            for (Integer i : visibleRows) {
                cellValue=getCellData(sheetName, columnName, i);
                return cellValue;
            }
        } catch (Exception e) {
            logMan.error("Error in getCellValueFromVisibleRows method, Error = "+e.getMessage());
        }
        return "";
    }

    public List<List<String>> getDataInFormOfListOfList(String sheetName){

        List<List<String>> data = null;
        try {
            data = new ArrayList<List<String>>();
            String value;
            this.sheet = workbook.getSheetAt(workbook.getSheetIndex(sheetName));
            for(int i=1;i<sheet.getLastRowNum()+1;i++){
                this.row=sheet.getRow(i);
                List<String> tempRowData = new ArrayList<String>();
                for (int j=0;j<this.row.getLastCellNum();j++){
                    //this.cell=this.row.getCell(j);
                    this.cell=this.row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if(cell.getCellType()==CellType.STRING){
                        value= cell.getStringCellValue();
                    }else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA){
                        value= String.valueOf(cell.getNumericCellValue());
                    }else if(cell.getCellType()==CellType.BLANK){
                        value="";
                    }else{
                        value="";
                    }
                    tempRowData.add(value);
                }
                data.add(tempRowData);
            }
        } catch (Exception e) {
            logMan.error("Error in getDataInFormOfListOfList method, Error = "+e.getMessage());
        }
        //logMan.info("Data in listOfList for sheet ="+sheetName+",is = "+data);
        return data;
    }

    public List<Map<String, String>> getDataInFormOfListOfMap(String sheetName){
        List<String> columnNames = getAllColumnName(sheetName);
        List<List<String>> data = getDataInFormOfListOfList(sheetName);
        List<Map<String, String>> listMapData = new ArrayList<>();

        try {
            for (List<String> list:data) {
                HashMap<String, String> tempMap = new HashMap<>();
               for (int i=0;i<list.size();i++){
                   tempMap.put(columnNames.get(i),list.get(i));
               }
                listMapData.add(tempMap);
            }
        } catch (Exception e) {
            logMan.error("Error in getDataInFormOfListOfMap method, Error = "+e.getMessage());
        }
        logMan.info("Data in form of List of Map for sheet = "+sheetName+", is = "+listMapData);
        return listMapData;
    }

    public List<Map<String, String>> getDataInFormOfListOfMapByLinkingTwoSheets(String sheetName,String linkedSheet,String linkedColumnName){
        List<String> columnNames = getAllColumnName(sheetName);
        List<List<String>> data = getDataInFormOfListOfList(sheetName);
        Map<String, Map<String, String>> mapOfMap = getDataInFormOfMapofMap(linkedSheet, linkedColumnName);

        List<Map<String, String>> listMapData = new ArrayList<>();

        try {
            for (List<String> list:data) {
                HashMap<String, String> tempMap = new HashMap<>();
                for (int i=0;i<list.size();i++){
                    tempMap.put(columnNames.get(i),list.get(i));
                }
                tempMap.putAll(mapOfMap.get(tempMap.get(linkedColumnName)));
                listMapData.add(tempMap);
            }
        } catch (Exception e) {
            logMan.error("Error in getDataInFormOfListOfMapByLinkingTwoSheets method, Error = "+e.getMessage());
        }
        logMan.info("Configuration details = "+listMapData);
        return listMapData;
    }

    public Map<String, List<String>> getDataInFormOfMapWithListValue(String sheetName){
        Map<String, List<String>> map = null;
        try {
            map = new HashMap<String, List<String>>();
            String value;
            this.sheet = workbook.getSheetAt(workbook.getSheetIndex(sheetName));
            for(int i=1;i<sheet.getLastRowNum()+1;i++){
                this.row=sheet.getRow(i);
                List<String> tempRowData = new ArrayList<String>();
                for (int j=0;j<this.row.getLastCellNum();j++){
                    //this.cell=this.row.getCell(j);
                    this.cell=this.row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if(cell.getCellType()==CellType.STRING){
                        value= cell.getStringCellValue();
                    }else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA){
                        value= String.valueOf(cell.getNumericCellValue());
                    }else if(cell.getCellType()==CellType.BLANK){
                        value="";
                    }else{
                        value="";
                    }
                    tempRowData.add(value);
                }
                map.put(tempRowData.get(0),tempRowData);
            }
        } catch (Exception e) {
            logMan.error("Error in getDataInFormOfMapWithListValue, Error = "+e.getMessage());
        }
        logMan.info("Data in Map of list for sheet ="+sheetName+",is = "+map);
        return map;
    }

    public Map<String, Map<String,String>> getDataInFormOfMapofMap(String sheetName,String linkedColumnName){
        List<String> columnNames = getAllColumnName(sheetName);
        Map<String, Map<String,String>> map = null;
        try {
            map = new HashMap<String, Map<String,String>>();
            String value;

            this.sheet = workbook.getSheetAt(workbook.getSheetIndex(sheetName));
            for(int i=1;i<sheet.getLastRowNum()+1;i++){
                this.row=sheet.getRow(i);
                Map<String, String> tempMap = new HashMap<String, String>();
                for (int j=0;j<this.row.getLastCellNum();j++){
                    //this.cell=this.row.getCell(j);
                    this.cell=this.row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if(cell.getCellType()==CellType.STRING){
                        value= cell.getStringCellValue();
                    }else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA){
                        value= String.valueOf(cell.getNumericCellValue());
                    }else if(cell.getCellType()==CellType.BLANK){
                        value="";
                    }else{
                        value="";
                    }
                    tempMap.put(columnNames.get(j),value);
                }
                map.put(tempMap.get(linkedColumnName),tempMap);
            }
        } catch (Exception e) {
            logMan.error("Error in getDataInFormOfMapofMap method, Error = "+e.getMessage());
        }
        //logMan.info("Data in Map of Map for sheet ="+sheetName+" and linked column = "+linkedColumnName+ ", is = "+map);
        return map;
    }


    public Map<String, Map<String,String>> getDataInFormOfMapofMapForTestData(String sheetName,String linkedColumnName,String linkedColumnName2){
        List<String> columnNames = getAllColumnName(sheetName);
        Map<String, Map<String,String>> map = null;
        try {
            map = new HashMap<String, Map<String,String>>();
            String value;

            this.sheet = workbook.getSheetAt(workbook.getSheetIndex(sheetName));
            for(int i=1;i<sheet.getLastRowNum()+1;i++){
                this.row=sheet.getRow(i);
                Map<String, String> tempMap = new HashMap<String, String>();
                for (int j=0;j<this.row.getLastCellNum();j++){
                    //this.cell=this.row.getCell(j);
                    this.cell=this.row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if(cell.getCellType()==CellType.STRING){
                        value= cell.getStringCellValue();
                    }else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA){
                        //DataFormatter dataFormatter=new DataFormatter();
                        //value=dataFormatter.formatCellValue(cell);
                        value= cell.getRawValue();
                    }else if(cell.getCellType()==CellType.BLANK){
                        value="";
                    }else{
                        value="";
                    }
                    tempMap.put(columnNames.get(j),value);
                }
                if(tempMap.get(linkedColumnName2).isEmpty()){
                    map.put(tempMap.get(linkedColumnName),tempMap);
                }else{
                    map.put(tempMap.get(linkedColumnName)+":"+tempMap.get(linkedColumnName2),tempMap);
                }
            }
        } catch (Exception e) {
            logMan.error("Error in getDataInFormOfMapofMapForTestData method, Error = "+e.getMessage());
        }
        logMan.info("Data in Map of Map for sheet ="+sheetName+" and linked column = "+linkedColumnName+ ", is = "+map);
        return map;
    }


    public Map<String, List<Map<String, String>>> getTestDataWhenColumnNameIsWrittenInFeatureFile(String sheetName, String linkedColumnName, String linkedColumnName2){
        List<String> columnNames = getAllColumnName(sheetName);
        int likedColumnNumber = getCellColumnNo(sheetName, linkedColumnName);
        Map<String, List<Map<String,String>>> map = null;
        try {
            map = new HashMap<String, List<Map<String,String>>>();
            String value;

            this.sheet = workbook.getSheetAt(workbook.getSheetIndex(sheetName));
            for(int i=1;i<sheet.getLastRowNum()+1;i++){
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                this.row=sheet.getRow(i);
                Map<String, String> tempMap = new HashMap<String, String>();
                for (int j=0;j<this.row.getLastCellNum();j++){
                    this.cell=this.row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if(cell.getCellType()==CellType.STRING){
                        value= cell.getStringCellValue();
                    }else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA){
                        //DataFormatter dataFormatter=new DataFormatter();
                        //value=dataFormatter.formatCellValue(cell);
                        value= cell.getRawValue();
                    }else if(cell.getCellType()==CellType.BLANK){
                        value="";
                    }else{
                        value="";
                    }
                    tempMap.put(columnNames.get(j),value);
                }
                String initialLinkedValue = tempMap.get(linkedColumnName);
                list.add(tempMap);
                if(tempMap.get(linkedColumnName2).isEmpty()){
                    map.put(tempMap.get(linkedColumnName),list);
                }else{
                    i++;
                    while(i<sheet.getLastRowNum()+1){
                        this.row=this.sheet.getRow(i);
                        String nextRowValue = row.getCell(likedColumnNumber).getStringCellValue();
                        if(initialLinkedValue.equalsIgnoreCase(nextRowValue)){
                            Map<String, String> map1 = getDataForListOfColNames(columnNames);
                            list.add(map1);
                            i++;
                        }else{
                            i--;
                            break;
                        }
                    }
                    map.put(tempMap.get(linkedColumnName),list);
                }
            }
        } catch (Exception e) {
            logMan.error("Error in getTestDataWhenColumnNameIsWrittenInFeatureFile method, Error = "+e.getMessage());
        }
        logMan.info("Data in Map of List Map for sheet ="+sheetName+" and linked column = "+linkedColumnName+ ", is = "+map);
        return map;
    }


    /**
     * This will return test data in Map
     * @param sheetName
     * @param linkedColumnName
     * @param linkedColumnName2
     * @param executionColumnName
     * @return
     */
    public Map<String, List<Map<String, String>>> getTestDataInFormOfMap(String sheetName, String linkedColumnName, String linkedColumnName2,String executionColumnName){
        List<String> columnNamesForZerothRow = getAllColumnName(sheetName);
        int likedColumnNumber = getCellColumnNo(sheetName, linkedColumnName);
        Map<String, List<Map<String,String>>> map = null;
        try {
            map = new HashMap<String, List<Map<String,String>>>();
            String value;

            this.sheet = workbook.getSheetAt(workbook.getSheetIndex(sheetName));
            for(int i=1;i<sheet.getLastRowNum()+1;i++){
                this.row=sheet.getRow(i);
                if(row==null){
                    this.row=this.sheet.createRow(i);
                }
                String scenarioNamePerRow = getDataForPerRowBasis(likedColumnNumber);
                if(!scenarioNamePerRow.isEmpty()){
                    List<String> columnNamesForScenario = getColumnNamesPerScenarioForData(i - 1);

                    List<String> columnNames = new ArrayList<String>();
                    columnNames.addAll(columnNamesForZerothRow);
                    columnNames.addAll(columnNamesForScenario);

                    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                    Map<String, String> tempMap = new LinkedHashMap<String, String>();
                    for (int j=0;j<this.row.getLastCellNum();j++){
                        this.cell=this.row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        if(cell.getCellType()==CellType.STRING){
                            value= cell.getStringCellValue();
                        }else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA){
                            //DataFormatter dataFormatter=new DataFormatter();
                            //value=dataFormatter.formatCellValue(cell);
                            value= cell.getRawValue();
                        }else if(cell.getCellType()==CellType.BLANK){
                            value="";
                        }else{
                            value="";
                        }
                        if(j<columnNames.size()){
                            tempMap.put(columnNames.get(j),value);
                        }else{
                            break;
                        }
                    }
                    String initialLinkedValue = tempMap.get(linkedColumnName);
                    if(tempMap.get(executionColumnName).equalsIgnoreCase("YES")){
                        list.add(tempMap);
                    }
                    if(tempMap.get(linkedColumnName2).isEmpty()){
                        if(tempMap.get(executionColumnName).equalsIgnoreCase("YES")){
                            map.put(tempMap.get(linkedColumnName),list);
                        }

                    }else{
                        i++;
                        while(i<sheet.getLastRowNum()+1){
                            this.row=this.sheet.getRow(i);
                            if(row==null){
                                this.row=this.sheet.createRow(i);
                            }
                            //String nextRowValue = row.getCell(likedColumnNumber).getStringCellValue();
                            String nextRowValue=getDataForPerRowBasis(likedColumnNumber);
                            if(initialLinkedValue.equalsIgnoreCase(nextRowValue)){
                                Map<String, String> map1 = getDataForListOfColNames(columnNames);
                                if(map1.get(executionColumnName).equalsIgnoreCase("YES")){
                                    list.add(map1);
                                }
                                i++;
                            }else{
                                i--;
                                break;
                            }
                        }
                        map.put(tempMap.get(linkedColumnName),list);
                    }
                }
            }
        } catch (Exception e) {
            logMan.error("Error in getTestDataInFormOfMap method, Error = "+e.getMessage());
        }
        //logMan.info("Data in Map of List of Map for sheet ="+sheetName+" and linked column = "+linkedColumnName+ ", is = "+map);
        return map;
    }


    public Map<String, String> getDataForListOfColNames(List<String> columnNames){
        String value;
        Map<String, String> newmap = new LinkedHashMap<String,String>();
        try {
            for (int j=0;j<this.row.getLastCellNum();j++){
                this.cell=this.row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if(cell.getCellType()==CellType.STRING){
                    value= cell.getStringCellValue();
                }else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA){
                    //DataFormatter dataFormatter=new DataFormatter();
                    //value=dataFormatter.formatCellValue(cell);
                    value= cell.getRawValue();
                }else if(cell.getCellType()==CellType.BLANK){
                    value="";
                }else{
                    value="";
                }
                if(j<columnNames.size()){
                    newmap.put(columnNames.get(j),value);
                }else{
                    break;
                }
            }
        } catch (Exception e) {
            logMan.error("Error in getDataForListOfColNames method, Error = "+e.getMessage());
        }
        return newmap;
    }

    public String getDataForPerRowBasis(int columnNumber){
        String value = null;
        try {
            this.cell=this.row.getCell(columnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if(cell.getCellType()==CellType.STRING){
                value= cell.getStringCellValue();
            }else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA){
                //DataFormatter dataFormatter=new DataFormatter();
                //value=dataFormatter.formatCellValue(cell);
                value= cell.getRawValue();
            }else if(cell.getCellType()==CellType.BLANK){
                value="";
            }else{
                value="";
            }
        } catch (Exception e) {
            logMan.error("Error in getDataForPerRowBasis method, Error = "+e.getMessage());
        }
        return value;
    }

    public List<String> getColumnNamesPerScenarioForData(int rowNumber){
        XSSFRow rowPerScenario = sheet.getRow(rowNumber);
        List<String> columnArrayList = new ArrayList<String>();
        try {
            for(int i =0 ; i<rowPerScenario.getLastCellNum();i++){
                String value;
                XSSFCell cellForScenario = rowPerScenario.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if(cellForScenario.getCellType()==CellType.STRING){
                    value= cellForScenario.getStringCellValue();
                }else if(cellForScenario.getCellType()==CellType.NUMERIC || cellForScenario.getCellType()==CellType.FORMULA){
                    //DataFormatter dataFormatter=new DataFormatter();
                    //value=dataFormatter.formatCellValue(cell);
                    value= cellForScenario.getRawValue();
                }else if(cellForScenario.getCellType()==CellType.BLANK){
                    value="";
                }else{
                    value="";
                }
                if(!value.isEmpty()){
                    columnArrayList.add(value);
                }
            }
        } catch (Exception e) {
            logMan.error("Error in getColumnNamesPerScenarioForData method, Error = "+e.getMessage());
        }
        return columnArrayList;

    }

    public Map<String, List<String>> scenarioTestDataColumnName(String sheetName, String linkedColumnName){
        Map<String, List<String>> map = null;
        try {
            int likedColumnNumber = getCellColumnNo(sheetName, linkedColumnName);
            map = new HashMap<String, List<String>>();
            this.sheet = workbook.getSheetAt(workbook.getSheetIndex(sheetName));
            for(int i=1;i<sheet.getLastRowNum()+1;i++) {
                this.row = sheet.getRow(i);
                String scenarioNamePerRow = getDataForPerRowBasis(likedColumnNumber);
                if (!scenarioNamePerRow.isEmpty()) {
                    List<String> columnNamesForScenario = getColumnNamesPerScenarioForData(i - 1);
                    if(!map.containsKey(scenarioNamePerRow)){
                        map.put(scenarioNamePerRow,columnNamesForScenario);
                    }
                }
            }
        } catch (Exception e) {
            logMan.error("Error in scenarioTestDataColumnName method, Error = "+e.getMessage());
        }
        return map;
    }

    public Map<String, Map<String, String>> getMappedData(String sheetName, String mappedColumnName){

        List<String> columnNamesForZerothRow = getAllColumnName(sheetName);
        int mappedColumnNumber = getCellColumnNo(sheetName, mappedColumnName);
        Map<String, Map<String,String>> map = null;
        try {
            map = new HashMap<String,Map<String,String>>();
            String value;

            this.sheet = workbook.getSheetAt(workbook.getSheetIndex(sheetName));
            for(int i=1;i<sheet.getLastRowNum()+1;i++){
                this.row=sheet.getRow(i);
                if(row==null){
                    this.row=this.sheet.createRow(i);
                }
                String mappedValue = getDataForPerRowBasis(mappedColumnNumber);
                if(!mappedValue.isEmpty()){
                    List<String> columnNamesForScenario = getColumnNamesPerScenarioForData(i - 1);

                    List<String> columnNames = new ArrayList<String>();
                    columnNames.addAll(columnNamesForZerothRow);
                    columnNames.addAll(columnNamesForScenario);

                    Map<String, String> tempMap = new LinkedHashMap<String, String>();
                    for (int j=0;j<this.row.getLastCellNum();j++){
                        this.cell=this.row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        if(cell.getCellType()==CellType.STRING){
                            value= cell.getStringCellValue();
                        }else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA){
                            //DataFormatter dataFormatter=new DataFormatter();
                            //value=dataFormatter.formatCellValue(cell);
                            value= cell.getRawValue();
                        }else if(cell.getCellType()==CellType.BLANK){
                            value="";
                        }else{
                            value="";
                        }
                        if(j<columnNames.size()){
                            tempMap.put(columnNames.get(j),value);
                        }else{
                            break;
                        }
                    }
                    String mappedValueFromMap = tempMap.get(mappedColumnName);
                    map.put(mappedValueFromMap,tempMap);

                    if(checkValueIsNotNotBlankInNextRow(i+1,mappedColumnNumber)){
                        i++;
                        while(i<sheet.getLastRowNum()+1){
                            this.row=this.sheet.getRow(i);
                            if(row==null){
                                this.row=this.sheet.createRow(i);
                            }
                            String nextRowMappedValue=getDataForPerRowBasis(mappedColumnNumber);
                            Map<String, String> map1 = getDataForListOfColNames(columnNames);
                            map.put(nextRowMappedValue,map1);

                            if(checkValueIsNotNotBlankInNextRow(i+1,mappedColumnNumber)){
                                i++;
                            }else{
                                break;
                            }
                        }
                    }
                }
            }
            //System.out.println(map);
        } catch (Exception e) {
            logMan.error("Error in getTestDataInFormOfMap method, Error = "+e.getMessage());
        }
        return map;
        //logMan.info("Data in Map of List of Map for sheet ="+sheetName+" and linked column = "+linkedColumnName+ ", is = "+map);
    }

    public boolean checkValueIsNotNotBlankInNextRow(int rowNumber,int colNumber){
        boolean flag=false;
        if(rowNumber<sheet.getLastRowNum()+1){
            XSSFRow rowPerData = sheet.getRow(rowNumber);
            if(rowPerData==null){
                rowPerData=sheet.createRow(rowNumber);
            }
            String value = null;
            try {
                //for(int i =0 ; i<rowPerData.getLastCellNum();i++){
                XSSFCell cellForScenario = rowPerData.getCell(colNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if(cellForScenario.getCellType()==CellType.STRING){
                    value= cellForScenario.getStringCellValue();
                }else if(cellForScenario.getCellType()==CellType.NUMERIC || cellForScenario.getCellType()==CellType.FORMULA){
                    //DataFormatter dataFormatter=new DataFormatter();
                    //value=dataFormatter.formatCellValue(cell);
                    value= cellForScenario.getRawValue();
                }else if(cellForScenario.getCellType()==CellType.BLANK){
                    value="";
                }else{
                    value="";
                }
                //}
                if(!value.isEmpty()){
                    flag=true;
                }
            } catch (Exception e) {
                logMan.error("Error in getColumnNamesPerScenarioForData method, Error = "+e.getMessage());
            }
        }
        return flag;
    }

}

package businesscomponents;

import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Function/Epic: Standing Orders functions (Squad-1 and Squad 2)
 * Class: Standing Order
 * Developed on: 22/10/2018
 * Developed by: C100493
 * Update on     Updated by     Reason
 * 16/04/2019     C104521      Done code clean up activity
 */

public class StandingOrder extends WebDriverHelper {

    public static LinkedHashMap<String, String> mapStandingOrderFields = new LinkedHashMap<String, String>();
    public static String strChangedAmount;

    public StandingOrder(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }


    /**
     * Function: To Validate Standing Order Account Selection Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void StandingOrderAccountSelection() throws Exception {
        //validating page title 'Standing Orders'
        isElementDisplayedWithLog(getObject(deviceType() + "StandingOrderDetails.hdrPageTitle"),
                "Header page title", "Standing Orders", 2);

        //validating text/label 'To view your standing orders select an account'
        isElementDisplayedWithLog(getObject("StandingOrder.lblToView"),
                "Text to help 'To view your standing orders select an account.'", "Standing Orders", 2);

        //validating label over dropdown 'Account'
        isElementDisplayedWithLog(getObject("StandingOrder.lblAccount"),
                "Dropdown Label 'Account", "Standing Orders", 2);

        //validating text 'or'
        isElementDisplayedWithLog(getObject("xpath~//div[text()='or']"),
                "Text '-----or----'", "Standing Orders", 2);

        //validating button 'Button :Set up new standing order'
        isElementDisplayedWithLog(getObject("StandingOrder.btnSetUpNewStandingOrder"),
                "Button 'Set Up New Standing Order'", "Standing Orders", 2);

        //validating warning text
        isElementDisplayedWithLog(getObject("xpath~//*[@class='fa fa-exclamation']/following-sibling::*[text()='Standing orders can be set up online to transfer funds from your current account to another account in the same country. (maximum limit €3,000 /£3,000).']"),
                "Warning text 'Standing orders can be set up online to transfer funds from your current account to another account in the same country. (maximum limit €3,000 /£3,000).'", "Standing Order", 2);

        //Validate Select Accounts
        if (isElementDisplayedWithLog(getObject("StandingOrder.lstSelectAccount"),
                "Dropdown 'To Select Standing Order'", "Standing Orders", 2)) {
            isElementDisplayedWithLog(getObject("StandingOrder.lstSelectAccount"),
                    "Dropdown 'To Select Standing Order'", "Standing Orders", 2);

            String strAccount = dataTable.getData("General_Data", "Account_Type");
            if (dataTable.getData("General_Data", "ActionStandingOrder").equalsIgnoreCase("Setup")) {
                clickJS(getObject("StandingOrder.btnSetUpNewStandingOrder"), "Clicked on 'Set up new standing order' Button");
                waitForPageLoaded();
            } else {
                if (deviceType.equalsIgnoreCase("Web")) {
                    click(getObject("StandingOrder.lstSelectAccount"));
                    driver.findElement(By.xpath("//ul[contains(@class,'exp_elem_list')]/descendant::li[contains(@class,'option boi_input')][contains(text(),'" + strAccount + "')]")).click();
                    report.updateTestLog("selectStandingOrderAccount", "'" + strAccount + "' Account is selected Standing Order dropdown", Status_CRAFT.PASS);
                } else {
                    try {
                        clickJS(getObject("StandingOrder.lstSelectAccount"),"Select Account");
                    //    driver.findElement(By.xpath("//ul[contains(@class,'exp_elem_list')]/descendant::li[contains(@class,'option boi_input')][contains(text(),'" + strAccount + "')]")).click();
                    //   JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                    //   js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//select[contains(@name,'STANDINGORDERLIST[1].ACCOUNT')]/following-sibling::div/ul/li[text()='"+strAccount+"']")));
                    } catch (UnreachableBrowserException e) {
                        e.printStackTrace();
                    } catch (StaleElementReferenceException e) {
                        e.printStackTrace();
                    }
                    Thread.sleep(2000);

                //   driver.findElement(By.xpath("//select[contains(@name,'STANDINGORDERLIST[1].ACCOUNT')]/following-sibling::div/ul/li[text()='"+strAccount+"']")).click();


                   List<WebElement> temp =driver.findElements(By.xpath("//ul/li[@role='option']"));

                    for (int i = 0; i < temp.size(); i++) {
                            if (temp.get(i).isDisplayed()) {
                                String strUiText = temp.get(i).getText();
                                if (strUiText.equalsIgnoreCase(strAccount)) {
                                    report.updateTestLog("verifyAccTypeExistAndClicked", "Account Type : '" + strAccount + " ' is present", Status_CRAFT.PASS);
                                    JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                                    js.executeScript("arguments[0].scrollIntoView();", temp.get(i));
                                    JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                                    executor.executeScript("arguments[0].click();", temp.get(i));
                                    report.updateTestLog("verifyAccTypeExistAndClicked", "Account Type : '" + strAccount + " ' is Selected", Status_CRAFT.PASS);
                                    break;
                                }
                            }
                        }
                    //report.updateTestLog("selectStandingOrderAccount", "Account '"+ strAccount +"' Selected", Status_CRAFT.PASS);
                   // driver.findElement(By.xpath("//select[contains(@name,'STANDINGORDERLIST[1].ACCOUNT')]/../following-sibling::div/ul/li[contains(.,'" + strAccount + "')]")).click();
                    //report.updateTestLog("selectStandingOrderAccount", "Account '" + strAccount + "' Selected", Status_CRAFT.PASS);
                }
            }
        }
    }

    /**
     * Function : To return fetched values for FDPs details in desktop
     * Created by : C966003
     * Update on    Updated by     Reason
     * 21/10/2019   C966003        Newly created
     */
    public void contentDisplayed() throws Exception{
        waitForPageLoaded();
        String strContentTextToCompare = dataTable.getData("General_Data","VerifyContent");
        String strContentTextFetched = getText(getObject("StandingOrder.lblContentText"));
        isElementDisplayedWithLog(getObject("StandingOrder.lblContentText"),
                "Content text : '" + strContentTextFetched +"' ","Standing Order",3);
        strTextToCompare(strContentTextToCompare,strContentTextFetched,"Content text");
    }

    /**
     * Function: To Validate Standing Order Listing Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void StandingOrderListingPage() throws Exception{
        //validating page title 'Standing Orders'
        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrderDetails.hdrPageTitle"),
                "Page Title 'Standing Orders'","Standing Orders",2);

        //validating text/label 'To view your standing orders select an account'
        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.lblToView"),
                "Text to help 'To view your standing orders select an account.'","Standing Orders",2);

        //validating label over dropdown 'Account'
        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.lblAccount"),
                "Dropdown Label 'Account' is displayed on Standing Order Listing Page","Standing Orders",2);

        //validating column names
        if(!dataTable.getData("General_Data","isMobile").equalsIgnoreCase("Yes")){
            String strColList =  dataTable.getData("General_Data","StandingOrderListColumns");
            String[] colsName = strColList.split("::");
            for(int i=0;i<colsName.length;i++){
                isElementDisplayedWithLog(getObject(deviceType()+"xpath~//table[@summary='StandingOrderList']/descendant::div[text()='"+ colsName[i] +"']"),
                        "Column '" + colsName[i] +"'","Standing Orders",2);
            }
        }
        //validating button 'Add Standing Order for Current Account
       dataTable.getData("General_Data","Account_Type");
        if(dataTable.getData("General_Data","Account_Type").toUpperCase().contains("CURRENT")){
            isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.btnSetUpNewStandingOrder"),
                    "Button 'Set Up New Standing Order' is displayed on Standing Order Listing Page for Current Account","Standing Orders",2);
        }
        else{
            if(isElementDisplayed(getObject("StandingOrder.btnSetUpNewStandingOrder"),5)){
                report.updateTestLog("StandingOrderListingPage", "Button 'Set Up New Standing Order' is displayed on Standing Order Listing Page for Other Account Types", Status_CRAFT.FAIL);
            }else{report.updateTestLog("StandingOrderListingPage", "Button 'Set Up New Standing Order' is NOT displayed on Standing Order Listing Page for Other Account Types", Status_CRAFT.PASS);}
        }
    }
    /**
     * Function: To Validate Error for No Standing Order
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void ErrorForNoStandingOrder()throws Exception{
        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.ErrMsgNoStandingOdr"),
                "Error message 'There are no standing orders for this account.' is displayed on Standing Order Listing Page for Account Types with no Standing Order","Standing Orders",2);
    }

    /**
     * Function: To Validate Standing Order Selection
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void StandingOrderSelection() throws Exception{
        String strOrderType = dataTable.getData("General_Data","StandingOrderStatus");
        boolean bflag =false;
        List<WebElement> tblRows = driver.findElements(By.xpath("//table[contains(@class,'boi-plain-table')]/tbody/tr"));

        //List<WebElement> tblRows = driver.findElements(By.xpath("//select[contains(@name,'STANDINGORDERLIST[1].ACCOUNT')]/../following-sibling::div/button/following-sibling::ul/li"));
        for(int irow=0;irow<tblRows.size();irow++){
            String rowText = tblRows.get(irow).getText();
           if(rowText.contains(strOrderType)){
               bflag = true;
               if(dataTable.getData("General_Data","isMobile").equalsIgnoreCase("Yes")||deviceType.equalsIgnoreCase("MobileWeb")){
                   getSelectedOrderContent_Mobile(irow);
               }
               else{
                   getSelectedOrderContent(irow);
               }

               if (deviceType.equalsIgnoreCase("Web")||deviceType.equalsIgnoreCase("TabletWeb"))
               {
                   tblRows.get(irow).click();
               }else if (deviceType.equalsIgnoreCase("MobileWeb"))
               {
                   JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                   js.executeScript("arguments[0].scrollIntoView();", tblRows.get(irow));
                   JavascriptExecutor executor = (JavascriptExecutor) driver.getWebDriver();
                   executor.executeScript("arguments[0].click();", tblRows.get(irow));
               }
               report.updateTestLog("StandingOrderSelection", "Order Type Selected '"+ strOrderType +"' at  row number '"+(irow + 1)+"'", Status_CRAFT.PASS);
               break;
           }
        }
        if(!bflag){
            report.updateTestLog("StandingOrderSelection", "Order Type '"+ strOrderType +"' is not present", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function: To Validate Selected Order Content
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void getSelectedOrderContent(int row)throws Exception{

        //List<WebElement> colElm = driver.findElements(By.xpath("//table[@summary='StandingOrderList']/tbody/tr["+ (row+1) +"]/td"));
        List<WebElement> colElm = driver.findElements(By.xpath(" //caption[text()='StandingOrderList']/../tbody/tr["+ (row+1) +"]/td"));
       String strColList =  dataTable.getData("General_Data","StandingOrderListColumns");
        String[] colsName = strColList.split("::");
        int intCol = 0;
        int intTd = 0;
        String colText;
        for(int irow=0;irow<colElm.size();irow++) {
            if(!colElm.get(irow).getAttribute("style").contains("none")){
                intTd = intTd +1;
                if(intTd>colsName.length){
                    report.updateTestLog("StandingOrderSelection", "Columns count on UI are more than expected Columns", Status_CRAFT.FAIL);
                    break;
                }
                colText = colElm.get(irow).getText();
                mapStandingOrderFields.put(colsName[intCol], colText);
                intCol = intCol + 1;
            }
        }
    }

    /**
     * Function: To Validate Selected Order Content for Mobile
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void getSelectedOrderContent_Mobile(int irow) throws Exception{

        String strColList =  dataTable.getData("General_Data","StandingOrderListColumns");
        String[] colsName = strColList.split("::");
        //Since DOM is different
        String[] uiColNames = "Name::Frequency::Previous amount::Status".split("::");
        WebElement uitblRow = driver.findElement(By.xpath("//table[contains(@class,'boi-plain-table')]/tbody/tr["+(irow+1)+"]"));

        String[] uiContentOrderList = uitblRow.getText().split("\n");
        LinkedHashMap<String, String> tempMap = new LinkedHashMap<String, String>();
        //UI Name,Frequency,Amount,Status
        for(int j=0;j<uiColNames.length;j++){tempMap.put(uiColNames[j],uiContentOrderList[j]);}
        for(int i=0;i<colsName.length;i++ ){mapStandingOrderFields.put(colsName[i],"MOBILE_PLACE_HOLDER");}
        for(int k=0;k<uiColNames.length;k++){mapStandingOrderFields.put(uiColNames[k],tempMap.get(uiColNames[k]));}

    }

    /**
     * Function: To Validate Standing Order Details
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void verifyStandingOrderDetails() throws Exception{

        //validate back link
        //Return to Standing order link has been removed as a part of Sprint 23

//        if(!dataTable.getData("General_Data","isMobile").equalsIgnoreCase("Yes")){
//                isElementDisplayedWithLog(getObject("StandingOrderDetails.lnkReturn"),
//                        "Link 'Return to standing order list","Standing Orders Detail",2);
//        }

        //validate page title
        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrderDetails.hdrPageTitle"),
                "Title 'Standing Orders'","Standing Orders Detail",2);

        //validate top section details
        verifyTopSection();
        //validating Manage button
        if (!dataTable.getData("General_Data","StandingOrderStatus").equals("Inactive")){
            isElementDisplayedWithLog(getObject("StandingOrderDetails.btnManage"),
                    "Button 'Manage'","Standing Orders Detail",2);
        }
        else{
            if(isElementDisplayed(getObject("StandingOrderDetails.btnManage"),5)){
                report.updateTestLog("verifyStandingOrderDetails", "Button 'Manage' is displayed on Standing Order Details Page for Inactive Status", Status_CRAFT.FAIL);
            }else{report.updateTestLog("verifyStandingOrderDetails", "Button 'Manage' is NOT displayed on Standing Order Details Page for Inactive Status", Status_CRAFT.PASS);}

        }
        //validate table content
        verifyStandingOrderTableDetails();

        //validate information msg & validate cancel standing order button
        if(!dataTable.getData("General_Data","StandingOrderStatus").equals("Inactive")){
            isElementDisplayedWithLog(getObject("StandingOrderDetails.lblInformationMsg"),
                    "Information Message 'Standing order changes and cancellations must be made at least 1 working day prior to their due date.'","Standing Orders Detail",2);
        }
        else{
            isElementDisplayedWithLog(getObject("StandingOrderDetails.lblInformationMsg_Inactive"),
                    "Information Message 'Once activated, the status of your standing order will update within 1 working day.'","Standing Orders Detail",2);
        }
        isElementDisplayedWithLog(getObject("StandingOrderDetails.btnCancelStandingOrder"),
                "Button 'Cancel Standing Order","Standing Orders Detail",2);
    }

    /**
     * Function: Validate Top Section Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void verifyTopSection() throws Exception{
        //Payee Name
        String strName = driver.findElement(getObject("StandingOrderDetails.lblTopSecPayeeName")).getText();
        if(strName.equals(mapStandingOrderFields.get("Name"))){
            report.updateTestLog("verifyStandingOrderDetails", "Payee Name '"+ mapStandingOrderFields.get("Name") +"' is correctly displayed in Top Section of the Page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("verifyStandingOrderDetails", "Payee Name is NOT correctly displayed in Top Section of the Page. Expected  '"+ mapStandingOrderFields.get("Name") +"', Actual '"+ strName +"' ", Status_CRAFT.FAIL);
        }
        //Frequency
        if(getText(getObject("StandingOrderDetails.lblTopSecFrequency")).equals(mapStandingOrderFields.get("Frequency"))){
            report.updateTestLog("verifyStandingOrderDetails", "Frequency '"+ mapStandingOrderFields.get("Frequency") +"' is correctly displayed in Top Section of the Page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("verifyStandingOrderDetails", "Frequency is NOT correctly displayed in Top Section of the Page. Expected  '"+ mapStandingOrderFields.get("Frequency") +"', Actual '"+ getText(getObject("StandingOrderDetails.lblTopSecFrequency")) +"' ", Status_CRAFT.FAIL);
        }
        //Last Amount Deducted
        if((getText(getObject("StandingOrderDetails.lblTopSecCurrSymbol"))+ " "+ getText(getObject("StandingOrderDetails.lblTopSecCurrency"))).equals(mapStandingOrderFields.get("Previous amount"))){
            report.updateTestLog("verifyStandingOrderDetails", "Previous Amount '"+ mapStandingOrderFields.get("Previous amount") +"' is correctly displayed in Top Section of the Page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("verifyStandingOrderDetails", "Previous Amount is NOT correctly displayed in Top Section of the Page. Expected  '"+ mapStandingOrderFields.get("Previous amount") +"', Actual '"+ (getText(getObject("StandingOrderDetails.lblTopSecCurrSymbol"))+ " "+ getText(getObject("StandingOrderDetails.lblTopSecCurrency"))) +"' ", Status_CRAFT.FAIL);
        }
        //Order Status
        if(getText(getObject("StandingOrderDetails.lblTopSecStatus")).equals(mapStandingOrderFields.get("Status"))){
            report.updateTestLog("verifyStandingOrderDetails", "Status '"+ mapStandingOrderFields.get("Status") +"' is correctly displayed in Top Section of the Page", Status_CRAFT.PASS);
        }else{
            report.updateTestLog("verifyStandingOrderDetails", "Status is NOT correctly displayed in Top Section of the Page. Expected  '"+ mapStandingOrderFields.get("Status") +"', Actual '"+ getText(getObject("StandingOrderDetails.lblTopSecStatus")) +"' ", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function: Validate Standing Order Table Details
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void verifyStandingOrderTableDetails() throws Exception{

        //validating the number of labels displayed
        String[] fieldNames = dataTable.getData("General_Data","StandingOrderDetailsLabel").split("::");
        List<WebElement> elmLabels = driver.findElements(By.xpath("//div[contains(@class,'input_review')]"));
        int intRow = 0;
        int intTr = 0;

        for(int irow=0;irow<elmLabels.size();irow++) {
            if(!elmLabels.get(irow).getAttribute("style").contains("none")){
                intTr = intTr +1;
                if(intTr>fieldNames.length){
                    report.updateTestLog("verifyStandingOrderDetails", "Details Section Label count on UI are more than expected Labels", Status_CRAFT.FAIL);
                    break;
                }

                String uiFieldText = driver.findElement(By.xpath("//div[contains(@class,'input_review')]["+ (irow+1) +"]/div[contains(@class,'tc-question-part boi_label')]")).getText();
                JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(getObject(("xpath~//div[contains(@class,'input_review')]["+ (irow+1) +"]/div[contains(@class,'tc-answer-part')]"))));
                String uiAnsText = driver.findElement(By.xpath("//div[contains(@class,'input_review')]["+ (irow+1) +"]/div[contains(@class,'tc-answer-part')]")).getText();
                if(uiFieldText.equals(fieldNames[intRow])){
                    if(uiFieldText.equals("BIC")){
                        String expectedBIC = mapStandingOrderFields.get("BIC/IBAN").split(" ")[0];
                        //mapStandingOrderFields.put("BIC/IBAN", expectedBIC);
                    }
                    if(uiFieldText.equals("IBAN")){
                        if(mapStandingOrderFields.get("BIC/IBAN").equalsIgnoreCase("MOBILE_PLACE_HOLDER")){
                            String expectedAccountNumber = "MOBILE_PLACE_HOLDER";
                           // mapStandingOrderFields.put("BIC/IBAN", expectedAccountNumber);
                        }else{

                            String expectedAccountNumber = mapStandingOrderFields.get("BIC/IBAN").replaceAll("   "," ").split(" ")[1].replace("\n","");
                           // mapStandingOrderFields.put("BIC/IBAN", expectedAccountNumber);
                        }
                    }
                    if(uiFieldText.equals("Currency")){
                        String currency = mapStandingOrderFields.get("Previous amount").split(" ")[0];
                        switch (currency){
                            case "€":
                                mapStandingOrderFields.put("Currency","EUR");
                                break;
                            case "£":
                                mapStandingOrderFields.put("Currency","GBP");
                                break;
                        }
                    }

                    if(uiFieldText.equals("Expiry date")){
                        DateFormat df = new SimpleDateFormat("DD-MMM-YYYY");
                        try {
                            df.parse(uiAnsText.replace(" ","-"));
                            report.updateTestLog("verifyStandingOrderDetails", "Text Label '"+fieldNames[intRow]+"' has correct date format dd Mmm yyyy", Status_CRAFT.PASS);
                        } catch (ParseException e) {
                            report.updateTestLog("verifyStandingOrderDetails", "Text Label '"+fieldNames[intRow]+"' has incorrect date format, Expected format 'dd Mmm yyyy'" +"', Actual '"+ uiAnsText +"'", Status_CRAFT.PASS);
                        }
                    }
                    else{
                        if((dataTable.getData("General_Data","isMobile").equalsIgnoreCase("Yes")|(deviceType.equalsIgnoreCase("MobileWeb")))&& (uiFieldText.equalsIgnoreCase("Account Number")|uiFieldText.equalsIgnoreCase("IBAN/Account Number")|uiFieldText.equalsIgnoreCase("BIC/NSC")|uiFieldText.equalsIgnoreCase("NSC"))){
                            report.updateTestLog("verifyStandingOrderDetails", "Text Label '"+fieldNames[intRow]+"' is correctly displayed with its value '"+ uiAnsText +"'", Status_CRAFT.PASS);
                        }else{
                            if(uiFieldText.equals("BIC")|uiFieldText.equals("IBAN")) {
                                if (mapStandingOrderFields.get("BIC/IBAN").contains(uiAnsText)) {
                                    report.updateTestLog("verifyStandingOrderDetails", "Text Label '" + fieldNames[intRow] + "' is correctly displayed with its value '" + uiAnsText + "'", Status_CRAFT.PASS);
                                } else {
                                    report.updateTestLog("verifyStandingOrderDetails", "Text Label '" + fieldNames[intRow] + "' does not have correct value displayed, Expected  '" + mapStandingOrderFields.get(uiFieldText) + "', Actual '" + uiAnsText + "'", Status_CRAFT.FAIL);
                                }
                            }else
                            {
                                if (mapStandingOrderFields.get(uiFieldText).contains(uiAnsText)) {
                                    report.updateTestLog("verifyStandingOrderDetails", "Text Label '" + fieldNames[intRow] + "' is correctly displayed with its value '" + uiAnsText + "'", Status_CRAFT.PASS);
                                } else {
                                    report.updateTestLog("verifyStandingOrderDetails", "Text Label '" + fieldNames[intRow] + "' does not have correct value displayed, Expected  '" + mapStandingOrderFields.get(uiFieldText) + "', Actual '" + uiAnsText + "'", Status_CRAFT.FAIL);
                                }
                            }
                        }
                    }

                }else{report.updateTestLog("verifyStandingOrderDetails", "Text Label is not correctly displayed, Expected '"+fieldNames[intRow]+"' , Actual '"+ uiFieldText +"'", Status_CRAFT.FAIL);}

                intRow = intRow + 1;
            }
        }
    }

    /**
     * Function: Validate Manage Standing Order Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void ManageStandingOrderPage()throws Exception{
        if (deviceType.equalsIgnoreCase("Web"))
        {
            click(getObject("StandingOrderDetails.btnManage"),"Manage on Standing Order Details Page");
        }else if (deviceType.equalsIgnoreCase("MobileWeb"))
        {
            clickJS(getObject("StandingOrderDetails.btnManage"),"Manage on Standing Order Details Page");
        }
        //validate back link
//        if(!dataTable.getData("General_Data","isMobile").equalsIgnoreCase("Yes")&& !isMobile){
//            isElementDisplayedWithLog(getObject("StandingOrderDetails.lnkReturn"),
//                    "Link 'Return to standing order list'","Manage Standing Orders",2);
//        }
        //validate page title
        isElementDisplayedWithLog(getObject(deviceType()+"ManageStandingOrder.hdrPageTitle"),
                "Page Title  'Manage Standing Order'","Manage Standing Orders",2);
        verifyTopSection();
        verifySelectMenuTabs();
    }

    /**
     * Function: Validate Select Menu Tabs
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void verifySelectMenuTabs() throws Exception{
        //Validate Menu Tabs
        isElementDisplayedWithLog(getObject("ManageStandingOrder.tabChangeAmount"),
                "Menu Tab 'Change amount'","Standing Order Manage Page",2);

        if(dataTable.getData("General_Data","StandingOrderStatus").equals("On hold")){
            isElementDisplayedWithLog(getObject("ManageStandingOrder.tabRemoveHold"),
                    "Menu Tab 'Remove hold'","Standing Order Manage Page",2);
        }
        else{
            isElementDisplayedWithLog(getObject("ManageStandingOrder.tabPutOnHold"),
                    "Menu Tab 'Put on hold'","Standing Order Manage Page",2);
        }

        isElementDisplayedWithLog(getObject("ManageStandingOrder.tabCancelThis"),
                "Menu Tab 'Cancel this'","Standing Order Manage Page",2);

        if(!dataTable.getData("General_Data","ManagingStandingOrderTab").equalsIgnoreCase("")){
            switch (dataTable.getData("General_Data","ManagingStandingOrderTab")) {
                case "Change amount":
                    clickJS(getObject("ManageStandingOrder.tabChangeAmount"), "Clicked on Manage Standing Order menu Tab '" + dataTable.getData("General_Data", "ManagingStandingOrderTab") + "'");
                    break;
                case "Cancel this":
                    click(getObject("ManageStandingOrder.tabCancelThis"), "Clicked on Manage Standing Order menu Tab '" + dataTable.getData("General_Data", "ManagingStandingOrderTab") + "'");
                    break;
                case "Put on hold":
                    clickJS(getObject("ManageStandingOrder.tabPutOnHold"), "Clicked on Manage Standing Order menu Tab '" + dataTable.getData("General_Data", "ManagingStandingOrderTab") + "'");
                    break;
                case "Remove hold":
                    click(getObject("ManageStandingOrder.tabRemoveHold"), "Clicked on Manage Standing Order menu Tab '" + dataTable.getData("General_Data", "ManagingStandingOrderTab") + "'");
                    break;
               default:
                   report.updateTestLog("verifySelectMenuTabs", "Please enter a correct menu tab name from Manage Standing Order page", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function: Validate Cancel This Tab Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void cancelThisTabPage() throws Exception{
        verifyTopSection();
        //validating the text
        isElementDisplayedWithLog(getObject(deviceType()+"ManageStandingOrder.hdrCancelStandingOrder"),
                "Text 'Do you want to cancel this standing order permanently?'","Standing Order Cancel This Page",2);
        //validating the CancelPermanently
        isElementDisplayedWithLog(getObject(deviceType()+"ManageStandingOrder.txtCancelPermanently"),
                "Header 'Cancel Standing Order'","Standing Order Cancel This Page",2);
        //validating the Goback
        isElementDisplayedWithLog(getObject(deviceType()+"ManageStandingOrder.btnGoback"),
                "Button 'Go back'","Standing Order Cancel This Page",2);
        //validating the CancelStandingOrder
        isElementDisplayedWithLog(getObject(deviceType()+"ManageStandingOrder.btnCancelStandingOrder"),
                "Button 'Cancel Standing Order'","Standing Order Cancel This Page",2);

    }

    /**
     * Function: Validate Cancel Standing Order
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void cancelStandingOrder() throws Exception {
        //Validate Cancel Standing Order Button
        if (isElementDisplayedWithLog(getObject(dataTable.getData("General_Data", "CancelStandingOrderBtnObj")),
                "Button 'Cancel Standing Order'", "Standing Orders Details", 2)) {
            clickJS(getObject(dataTable.getData("General_Data", "CancelStandingOrderBtnObj")), "Clicked on Cancel Standing Order");
            //Validate Header
            isElementDisplayedWithLog(getObject(deviceType() + "StandingOder.titlePopUpCancelStandingOrder"),
                    "Cancel popup has correct header 'Cancel standing order?'", "Standing Order Cancel This Page", 2);

            String strPopupMsg = getText(getObject("StandingOrder.txtPopUpMsg")).replaceAll("\n", "");
            String strExpected = "You 've chosen to cancel the standing order permanently.Are you sure you want to continue?";
            if (strPopupMsg.equals(strExpected)) {
                report.updateTestLog("cancelStandingOrder", "Pop Up message 'You 've chosen to cancel the standing order permanently.    Are you sure you want to continue?' is displayed on Cancel Standing Order Popup", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("cancelStandingOrder", "Pop Up message 'You 've chosen to cancel the standing order permanently.   Are you sure you want to continue?' is NOT displayed/Incorrect on Cancel Standing Order Popup", Status_CRAFT.FAIL);
            }
            isElementDisplayedWithLog(getObject(deviceType() + "StandingOrder.btnYesCancel"),
                    "Button 'Yes, cancel standing order'", "Cancel Standing Order PopUp", 2);

            isElementDisplayedWithLog(getObject(deviceType() + "StandingOrder.btnGoback"),
                    "Button 'Go back'", "Cancel Standing Order PopUp", 2);

            if(dataTable.getData("General_Data","Operation").equalsIgnoreCase("Cancel Standing Order")){
                if (deviceType.equalsIgnoreCase("Web"))
                {
                    click(getObject(deviceType() + "StandingOrder.btnYesCancel"),"Clicked on 'Yes, cancel standing order'");
                }else if (deviceType.equalsIgnoreCase("MobileWeb"))
                {
                    clickJS(getObject("StandingOrder.btnYesCancel"),"Clicked on 'Yes, cancel standing order'");
                }

            }else{
                click(getObject(deviceType() + "StandingOrder.btnGoback"),"Clicked on 'Go back'");
            }
        }
    }

    /**
     * Function: Validate Cancel Standing Order Confirmation Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void CancelStandingOrderConfirmationPage() throws Exception{
        //validate page title
        if (deviceType.equalsIgnoreCase("Web"))
        {
            if(isElementDisplayed(getObject(deviceType()+"ManageStandingOrder.hdrPageTitle"),5)){
                report.updateTestLog("CancelStandingOrderConfirmationPage", "Page Title 'Manage Standing Order' is displayed on Cancelled Standing Order Confirmation page", Status_CRAFT.PASS);
            }else{report.updateTestLog("CancelStandingOrderConfirmationPage", "Page Title 'Manage Standing Order' is NOT displayed on Cancelled Standing Order Confirmation page", Status_CRAFT.FAIL);}
        }

        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.lblCancelled"),
                "Success Icon heading 'Cancelled'","Cancelled Standing Order Confirmation",2);

        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.lblThankYou"),
                "Text message 'Thank you!'","Cancelled Standing Order Confirmation",2);

        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.lblInformativeText"),
                "Informative message 'Your standing order has been permanently cancelled.'","Cancelled Standing Order Confirmation",2);

        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.btnBacktoHome"),
                "Button 'Back to home'","Cancelled Standing Order Confirmation",2);

        /*isElementDisplayedWithLog(getObject("StandingOrder.btnBacktoStandingOrders"),
                "Button 'Back to standing orders'","Cancelled Standing Order Confirmation",2);*/

        if (isElementDisplayedWithLog(getObject("StandingOrder.lblTimeOfRequest"),
                "Time Stamp with message 'Time of request'", "Cancelled Standing Order Confirmation", 5)) {
            String strRequestSubmitted = getText(getObject("StandingOrder.lblTimeOfRequest"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("StandingOrder.lblTimeOfRequest", "Text Label '"+strRequestSubmitted+"' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("StandingOrder.lblTimeOfRequest", "Text Label '"+strRequestSubmitted+"' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" +"', Actual '"+ uiDateText +"'", Status_CRAFT.PASS);
            }
        }

        //Validating Navigation/functionality of the buttons
        if(dataTable.getData("General_Data","NavigationfromConfirmationPage").equalsIgnoreCase("Back to home")){
            click(getObject(deviceType()+"StandingOrder.btnBacktoHome"),"Clicked on 'Back to home'");
            isElementDisplayedWithLog(getObject(deviceType()+"home.sectionTimeLine"),
                    "Upon clicking 'Back to home'","Navigated to Home page from Cancel Standing Order Confirmation",2);
        }else if(dataTable.getData("General_Data","NavigationfromConfirmationPage").equalsIgnoreCase("Back to standing orders")){
            if (deviceType.equalsIgnoreCase("Web"))
            {
                click(getObject("StandingOrder.btnBacktoStandingOrders"),"Clicked on 'Back to standing orders'");
            }else if (deviceType.equalsIgnoreCase("MobileWeb"))
            {
                clickJS(getObject("StandingOrder.btnBacktoStandingOrders"),"Clicked on 'Back to standing orders'");
            }

            if ((isElementDisplayed(getObject("StandingOrder.lblAccount"), 5))&& (!isElementDisplayed(getObject("xpath~//table[@summary='StandingOrderList']"), 5))) {
                report.updateTestLog("CancelStandingOrderConfirmationPage", "Upon clicking 'Back to standing orders' ,successfully Navigated to Standing Order Landing page from Cancel Standing Order Confirmation Page", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("CancelStandingOrderConfirmationPage", "Upon clicking 'Back to standing orders' ,navigation unsuccessful to Standing Order Landing page from Cancel Standing Order Confirmation Page", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function: Validate Put on Hold Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void PutonholdPage() throws Exception{

        verifyTopSection();

        //validating the  Informative text
        isElementDisplayedWithLog(getObject("ManageStandingOrder.InfoTextPutOnHold"),
                "Informative text 'Standing order changes must be made  at least 1 working day prior to their due date.'","Put on Hold Page",2);

        //validate question label
        isElementDisplayedWithLog(getObject("ManageStandingOrder.lblDoYouWantTo"),
                "Question Label 'Do you want to put the standing order on hold?'","Standing Order Put on Hold Page",2);

        //verify Toggle options Yes & No

        if(isElementDisplayed(getObject("ManageStandingOrder.tglBtnYes"),5) && isElementDisplayed(getObject("ManageStandingOrder.tglBtnNo"),5) ){
            report.updateTestLog("PutonholdPage", "Toggle Buttons 'Yes' and 'No' are displayed on Standing Order Put on Hold Page", Status_CRAFT.PASS);
        }else{report.updateTestLog("PutonholdPage", "Toggle Buttons 'Yes' or 'No'is NOT displayed on Standing Order Put on Hold Page", Status_CRAFT.FAIL);}

        isElementDisplayedWithLog(getObject("ManageStandingOrder.btnGoback"),
                "Button 'Go back'","Standing Order Put on Hold Page",2);

        isElementDisplayedWithLog(getObject("ManageStandingOrder.btnContinue"),
                "Button 'Continue'","Standing Order Put on Hold Page",2);


        String strAnsPutOnHold = dataTable.getData("General_Data","PutOnHold");
        if(!strAnsPutOnHold.equals("")){
            switch (strAnsPutOnHold.toUpperCase()){
                case "YES":
                    clickJS(getObject("ManageStandingOrder.tglBtnYes"),"Clicked on 'Yes', Put on Hold Page");
                    break;
                case "NO":
                    clickJS(getObject("ManageStandingOrder.tglBtnNo"),"Clicked on 'No', Put on Hold Page");
                    break;
                default:
                    report.updateTestLog("PutonholdPage", "Please provide the valid ans to question 'Do you want to put the standing order on hold, Yes or No", Status_CRAFT.FAIL);
            }
        }
        //Validate Put on Hold Operation

        if(!dataTable.getData("General_Data","Operation").equals("")){
            String strOperationOnPutonHold = dataTable.getData("General_Data","Operation");
            switch (strOperationOnPutonHold.toUpperCase()){
                case "CONTINUE":
                    clickJS(getObject("ManageStandingOrder.btnContinue"),"Clicked on 'Continue', Put on Hold Page");
                    break;
                case "GO BACK":
                    clickJS(getObject("ManageStandingOrder.btnGoback"),"Clicked on 'Go back', Put on Hold Page");
                    isElementDisplayedWithLog(getObject(deviceType()+"StandingOrderDetails.lblInformationMsg"),
                            "Page navigated to Standing Order Details Page upon clicking 'Go back'","Standing Order Put on Hold Page",2);
                    break;
                default:
                    report.updateTestLog("PutonholdPage", "Please provide the valid operation on Remove hold page, Continue or Go Back",Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function: Validate Review Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void ReviewPage() throws Exception{
        waitForJQueryLoad();
        verifyTopSection();
        //Validate header
        isElementDisplayedWithLog(getObject("ManageStandingOrder.hdrReview"),
                "Section Header 'Review'","Review Page",2);
        //Validate Text
        if(dataTable.getData("General_Data","ManagingStandingOrderTab").equalsIgnoreCase("Change amount")){
            isElementDisplayedWithLog(getObject("ManageStandingOrder.lblYouRequestedAmountChange"),
                    "Label text 'You have requested the following amount change:'","Review Page",2);
        }else{
            isElementDisplayedWithLog(getObject("ManageStandingOrder.lblYouRequested"),
                    "Label text 'You have requested the following:'","Review Page",2);
        }

        String strValStatus;
        String strStatus;
        String strXpath ;
        if(dataTable.getData("General_Data","ManagingStandingOrderTab").equalsIgnoreCase("put on hold")){
            strValStatus = "On hold";
            strStatus = "Status";
            strXpath = "//label[text()='"+strStatus+"']/../../following-sibling::*/*/*[text()='"+ strValStatus +"']";
        }else if(dataTable.getData("General_Data","ManagingStandingOrderTab").equalsIgnoreCase("Remove hold")){
            strValStatus = "Remove hold";
            strStatus = "Status";
            strXpath = "//label[text()='Status']/../../following-sibling::*/*/*[text()='Remove hold']";
        }
        else if(dataTable.getData("General_Data","ManagingStandingOrderTab").equalsIgnoreCase("Change amount")){
            strValStatus = mapStandingOrderFields.get("Previous amount").split(" ")[0] + dataTable.getData("General_Data","NewAmount");
            strStatus = "New amount";
            strXpath = "//span[text()='New amount']/../../following-sibling::div[contains(@class,'ecDIB  responsive-column col-1-2 reverse-align-xs')]/descendant::span[contains(text(),'"+strChangedAmount+"')]";
        }else{
            strValStatus = "";
            strStatus = "Status";
            strXpath = "//label[text()='"+strStatus+"']/../../following-sibling::*/*/*[text()='"+ strValStatus +"']";
        }
/*
        if(isElementDisplayed(getObject("xpath~"+strXpath),5)){
            report.updateTestLog("ReviewPage", "Blue Section card label '"+ strStatus +"' and order status '"+ strValStatus +"' correctly displayed on Review Page", Status_CRAFT.PASS);
        }else{report.updateTestLog("ReviewPage", "Blue Section card label '"+ strStatus +"' and order status '"+ strValStatus + "'  NOT correctly displayed on Review Page", Status_CRAFT.FAIL);}
*/
        if(isElementDisplayed(getObject("ManageStandingOrder.lblSectionHeading"),5)){
            report.updateTestLog("ReviewPage", "Section heading 'Standing order details' is displayed on Review Page", Status_CRAFT.PASS);
        }else{report.updateTestLog("ReviewPage", "Section heading 'Standing order details' is NOT displayed on Review Page", Status_CRAFT.FAIL);}
        //Validate Label
        isElementDisplayedWithLog(getObject("xpath~"+strXpath),
                "Blue Section card label '" + strStatus +"' and order status '"+ strValStatus +"' and order status '","Review",2);
        //Validate Heading
        isElementDisplayedWithLog(getObject("ManageStandingOrder.lblSectionHeading"),
                "Section heading 'Standing order details'","Review Page",2);

        verifyReviewPageDetails();
        String strOperationOnPutonHold = dataTable.getData("General_Data","OperationOnReviewPage");

        switch (strOperationOnPutonHold.toUpperCase()){
            case "CONTINUE":
                clickJS(getObject("ManageStandingOrder.btnContinue"),"Clicked on 'Continue', Standing Order Review Page");
                break;
            case "GO BACK":
                clickJS(getObject("ManageStandingOrder.btnGoback"),"Clicked on 'Go back',Standing Order Review Page");
                isElementDisplayedWithLog(getObject("ManageStandingOrder.tabChangeAmount"),
                        "Navigate 'Manage Standing Order'","After Click Go Back",2);
                break;
            default:
                report.updateTestLog("PutonholdPage", "Please provide the valid ans to question 'Do you want to put the standing order on hold, Yes or No", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function: Validate Review Page Details
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void verifyReviewPageDetails() throws Exception{

        //validating the number of labels displayed
        String[] fieldNames = dataTable.getData("General_Data","ManageStandingOrderReviewFields").split("::");
        List<WebElement> elmLabels = driver.findElements(By.xpath("//div[contains(@class,'input_review')]"));
        int intRow = 0;
        int intTr = 0;

        for(int irow=0;irow<elmLabels.size();irow++) {
            if(!elmLabels.get(irow).getAttribute("style").contains("none")){
                intTr = intTr +1;
                if(intTr>fieldNames.length){
                    report.updateTestLog("verifyStandingOrderDetails", "Details Section Label count on UI are more than expected Labels", Status_CRAFT.DONE);
                    break;
                }

                String uiFieldText = driver.findElement(By.xpath("//div[contains(@class,'input_review')]["+ (irow+1) +"]/div[contains(@class,'tc-question-part boi_label')]")).getText();
                String uiAnsText = driver.findElement(By.xpath("//div[contains(@class,'input_review')]["+ (irow+1) +"]/div[contains(@class,'tc-answer-part')]")).getText();

                if(uiFieldText.equals(fieldNames[intRow])){
                    if(uiFieldText.equals("BIC")){
                        String expectedBIC = mapStandingOrderFields.get("BIC/IBAN").split(" ")[0];
                        mapStandingOrderFields.put("BIC", expectedBIC);
                    }

                    if(uiFieldText.equals("Pay from")){
                        mapStandingOrderFields.put("Pay from", dataTable.getData("General_Data","Account_Type"));
                    }

                    if(uiFieldText.equals("Message to payee")){
                        mapStandingOrderFields.put("Message to payee","MOBILE_PLACE_HOLDER");
                    }
                    if(uiFieldText.equals("IBAN")){
                        if(mapStandingOrderFields.get("BIC/IBAN").equalsIgnoreCase("MOBILE_PLACE_HOLDER")){
                            String expectedAccountNumber = "MOBILE_PLACE_HOLDER";
                            mapStandingOrderFields.put("IBAN", expectedAccountNumber);
                        }else{
                            String expectedAccountNumber = mapStandingOrderFields.get("BIC/IBAN").split("  ")[1].replace("\n","");
                            mapStandingOrderFields.put("IBAN", expectedAccountNumber);
                        }
                    }
                    if(uiFieldText.equals("Account number")){
                        if(mapStandingOrderFields.get("Account number").equalsIgnoreCase("MOBILE_PLACE_HOLDER")){
                            String expectedAccountNumber = "MOBILE_PLACE_HOLDER";
                            mapStandingOrderFields.put("Account number", expectedAccountNumber);
                        }else{
                            String expectedAccountNumber = mapStandingOrderFields.get("Account number");
                            mapStandingOrderFields.put("Account number", expectedAccountNumber);
                        }
                    }

                    if(uiFieldText.equals("Start date")){
                        DateFormat df = new SimpleDateFormat("DD-MMM-YYYY");
                        try {
                            df.parse(uiAnsText.replace(" ","-"));
                            report.updateTestLog("verifyStandingOrderDetails", "Text Label '"+fieldNames[intRow]+"' has correct date format dd Mmm yyyy", Status_CRAFT.PASS);
                        } catch (ParseException e) {
                            report.updateTestLog("verifyStandingOrderDetails", "Text Label '"+fieldNames[intRow]+"' has incorrect date format, Expected format 'dd Mmm yyyy'" +"', Actual '"+ uiAnsText +"'", Status_CRAFT.PASS);
                        }
                    }
                    else if(uiFieldText.equals("Message to payee")){
                        report.updateTestLog("verifyStandingOrderDetails", "Text Label '"+fieldNames[intRow]+"' is correctly displayed with its value '"+ uiAnsText +"'", Status_CRAFT.PASS);
                    }
                    else{
                        if((dataTable.getData("General_Data","isMobile").equalsIgnoreCase("Yes"))&& (uiFieldText.equalsIgnoreCase("Account Number")|uiFieldText.equalsIgnoreCase("IBAN/Account Number")|uiFieldText.equalsIgnoreCase("NSC")|uiFieldText.equalsIgnoreCase("BIC")|uiFieldText.equalsIgnoreCase("IBAN"))){
                            report.updateTestLog("verifyStandingOrderDetails", "Text Label '"+fieldNames[intRow]+"' is correctly displayed with its value '"+ uiAnsText +"'", Status_CRAFT.PASS);
                        }
                        else{
                            if(uiAnsText.equals(mapStandingOrderFields.get(uiFieldText))){
                                report.updateTestLog("verifyStandingOrderDetails", "Text Label '"+fieldNames[intRow]+"' is correctly displayed with its value '"+ uiAnsText +"'", Status_CRAFT.PASS);
                            }else{
                                report.updateTestLog("verifyStandingOrderDetails", "Text Label '"+fieldNames[intRow]+"' does not have correct value displayed, Expected  '"+ mapStandingOrderFields.get(uiFieldText) +"', Actual '"+ uiAnsText +"'", Status_CRAFT.FAIL);
                            }
                        }
                    }

                }else{report.updateTestLog("verifyStandingOrderDetails", "Text Label is not correctly displayed, Expected '"+fieldNames[intRow]+"' , Actual '"+ uiFieldText +"'", Status_CRAFT.FAIL);}

                intRow = intRow + 1;
            }
        }
    }

    /**
     * Function: Validate Enter New Pin
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void enterNewPin() throws Exception {

        String[] arrPin = dataTable.getData("Login_Data", "PIN").split("");
        List<WebElement> lstPinEnterFieldGrp = findElements(getObject("ManageStandingOrder.txtPinObjects"));

        //Entering values for only enabled fields
        for (int i = 0; i < lstPinEnterFieldGrp.size(); i++) {
            if (lstPinEnterFieldGrp.get(i).isEnabled()) {
                lstPinEnterFieldGrp.get(i).sendKeys(arrPin[i]);
                report.updateTestLog("enterNewPin", "Entered pin :"+ arrPin[i], Status_CRAFT.DONE);
            }
        }
        //if(!isMobile){
            if(!dataTable.getData("General_Data","Operation").equals("")){
                String strOperationOnPutonHold = dataTable.getData("General_Data","Operation");
                switch (strOperationOnPutonHold.toUpperCase()){
                    case "CONTINUE":
                        if(isMobile){
                            try{
                                driver.context("NATIVE_APP");
                                if(isIOS){
                                    clickButtonOrLinkIOS("Confirm");
                                }else{
                                    MobileElement el1 = (MobileElement) driver.findElementByXPath("//android.view.View[3]/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.Button");
                                    (new TouchAction(driver.getAppiumDriver())).tap(PointOption.point(el1.getCenter().getX(),el1.getCenter().getY())).perform();
                                }
                                report.updateTestLog("PushNotification_Acccept", "Push Notification Accepted",Status_CRAFT.PASS);
                            }catch(Exception e){
                                System.out.print("Exception is there ::"+e.getMessage());
                            }
                            Thread.sleep(3000);
                        }else{
                            clickJS(getObject("ManageStandingOrder.btnConfirm"),"Clicked on 'Confirm', Enter Pin Page");
                            waitForJQueryLoad();waitForPageLoaded();
                            Thread.sleep(3000);
                        }
                        break;
                    case "GO BACK":
                        clickJS(getObject("ManageStandingOrder.btnGoback"),"Clicked on 'Go back', Enter Pin  Page");
                        break;

                    default:
                        report.updateTestLog("enterNewPin", "Please provide the valid operation Continue/Go back", Status_CRAFT.FAIL);
                }
            }
    }

    /**
     * Function: Validate Standing Order Confirmation Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void StandingOrderConfirmationPage() throws Exception{
        //validate page title
        isElementDisplayedWithLog(getObject(deviceType()+"ManageStandingOrder.hdrPageTitle"),
                "Page Title 'Manage Standing Order'","Standing Order Confirmation",2);
        //Validate Icon Confirmation
        isElementDisplayedWithLog(getObject("StandingOrder.iconConfirmation"),
                "Success Icon 'Tick Mark'","Standing Order Confirmation",2);
        //Validate label Request Sent
        isElementDisplayedWithLog(getObject("ManageStandingOrder.lblRequestSent"),
                "Success! 'message'","Standing Order Confirmation",2);
         //Validate Label Thank You
//        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.lblThankYou"),
//                "Text message 'Thank you!'","Standing Order Confirmation",2);

       if(dataTable.getData("General_Data","ManagingStandingOrderTab").equalsIgnoreCase("Change amount")){
           String strToCompare = "The new amount won't be reflected in your standing order details until the next payment is made.";
           if(isElementDisplayed(getObject("StandingOrder.lblInformativeTextChangeAmount"),5)&& getText(getObject("StandingOrder.lblInformativeTextChangeAmount")).equals(strToCompare)){
               report.updateTestLog("StandingOrderConfirmationPage", "Informative message 'The new amount won't be reflected in your standing order details until the next payment is made.' is displayed on Standing Order Confirmation page", Status_CRAFT.PASS);
           }else{report.updateTestLog("StandingOrderConfirmationPage", "Informative message 'The new amount won't be reflected in your standing order details until the next payment is made.'is NOT displayed on Standing Order Confirmation page", Status_CRAFT.FAIL);}

       }else{
           isElementDisplayedWithLog(getObject(deviceType()+"ManageStandingOrder.hdrInfoListUpdateIn"),
                   "Informative message 'We will update your list of standing orders within 1 working day.'","Standing Order Confirmation",2);
       }
        //Validate Back To Home Button
        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.btnBacktoHome"),
                "Button 'Back to home'","Standing Order Confirmation",2);
        //Validate Label Time of Request
        if (isElementDisplayedWithLog(getObject("StandingOrder.lblTimeOfRequest"),
                "Time Stamp with message 'Time of request'", "Standing Order Confirmation", 5)) {
            report.updateTestLog("StandingOrderConfirmationPage", "Time Stamp with message 'Time of request' is displayed on Standing Order Confirmation page", Status_CRAFT.PASS);

            String strRequestSubmitted = getText(getObject("StandingOrder.lblTimeOfRequest"));
            String uiDateText = strRequestSubmitted.split(": ")[1];

            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("StandingOrderConfirmationPage", "Text Label '"+strRequestSubmitted+"' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("StandingOrderConfirmationPage", "Text Label '"+strRequestSubmitted+"' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" +"', Actual '"+ uiDateText +"'", Status_CRAFT.PASS);
            }
        }

        //Validating Navigation/functionality of the buttons
        if(dataTable.getData("General_Data","NavigationfromConfirmationPage").equalsIgnoreCase("Back to home")){
            click(getObject("StandingOrder.btnBacktoHome"),"Clicked on 'Back to home'");
            isElementDisplayedWithLog(getObject(deviceType()+"home.sectionTimeLine"),
                    "Upon clicking 'Back to home' Navigate to Home Page","Standing Order Confirmation",2);
        }
    }

    /**
     * Function: Validate Error Message No Amendment
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void ErrMsgNoAmendment() throws Exception{

        switch (dataTable.getData("General_Data","ManagingStandingOrderTab")) {
            case "Change amount":
                isElementDisplayedWithLog(getObject("ManageStandingOrder.lblErrNoAmountChange"),
                        "Error message 'Amount entered is invalid. Please try again'","Change Amount Page",2);
                break;
            case "Put on hold":
                isElementDisplayedWithLog(getObject("ManageStandingOrder.infoNoAmendment"),
                        "Error message 'You have not made an amendment to this standing order'","Put on Hold",2);
                break;
            case "Remove hold":
                isElementDisplayedWithLog(getObject("ManageStandingOrder.infoNoAmendment"),
                        "Error message 'You have not made an amendment to this standing order'","Put on Hold",2);
                break;
            default:
                report.updateTestLog("ErrMsgNoAmendment", "Please enter a correct menu tab name from Manage Standing Order page", Status_CRAFT.FAIL);
        }

    }

    /**
     * Function: Validate Error Message Select Option
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void errMsgSelectOption() throws Exception{

        isElementDisplayedWithLog(getObject("ManageStandingOrder.lblErrSelectOption"),
                "Error message 'Please select an option'","Put on Hold",2);
    }

    /**
     * Function: Validate Remove Hold Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void removeHoldPage() throws Exception{

        verifyTopSection();
        //validating the  Informative text
        isElementDisplayedWithLog(getObject("ManageStandingOrder.InfoTextPutOnHold"),
                "Informative text 'Standing order changes must be made  at least 1 working day prior to their due date.'","Standing Order Remove Hold",2);

        //validate question label
        isElementDisplayedWithLog(getObject("ManageStandingOrder.lblQuesRemovehold"),
                "Question Label 'Do you want to remove the hold on this standing order?'","Standing Order Remove Hold",2);

        //verify Toggle options Yes & No
        if(isElementDisplayed(getObject("ManageStandingOrder.tglBtnYes"),5) && isElementDisplayed(getObject("ManageStandingOrder.tglBtnNo"),5) ){
            report.updateTestLog("removeHoldPage", "Toggle Buttons 'Yes' and 'No' are displayed on Standing Order Remove Hold Page", Status_CRAFT.PASS);
        }else{report.updateTestLog("removeHoldPage", "Toggle Buttons 'Yes' or 'No'is NOT displayed on Standing Order Remove Hold Page", Status_CRAFT.FAIL);}

        isElementDisplayedWithLog(getObject("ManageStandingOrder.btnGoback"),
                "Button 'Go back'","Standing Order Remove Hold",2);

        isElementDisplayedWithLog(getObject("ManageStandingOrder.btnContinue"),
                "Button 'Continue'","Standing Order Remove Hold",2);

        String strAnsPutOnHold = dataTable.getData("General_Data","RemoveHold");
        if(!strAnsPutOnHold.equals("")){
            switch (strAnsPutOnHold.toUpperCase()){
                case "YES":
                    clickJS(getObject("ManageStandingOrder.tglBtnYes"),"Clicked on 'Yes', Remove Hold Page");
                    break;
                case "NO":
                    clickJS(getObject("ManageStandingOrder.tglBtnNo"),"Clicked on 'No', Remove Hold Page");
                    break;
                default:
                    report.updateTestLog("removeHoldPage", "Please provide the valid ans to question 'Do you want to remove the hold on this standing order?, Yes or No", Status_CRAFT.FAIL);
            }
        }

        if(!dataTable.getData("General_Data","Operation").equals("")){
            String strOperationOnPutonHold = dataTable.getData("General_Data","Operation");
            switch (strOperationOnPutonHold.toUpperCase()){
                case "CONTINUE":
                    clickJS(getObject("ManageStandingOrder.btnContinue"),"Clicked on 'Continue', Remove Hold Page");
                    break;
                case "GO BACK":
                    clickJS(getObject("ManageStandingOrder.btnGoback"),"Clicked on 'Go back', Remove Hold Page");
                    isElementDisplayedWithLog(getObject("StandingOrderDetails.lblInformationMsg"),
                            "Page navigated to Standing Order Details Page upon clicking 'Go back'","Standing Order Remove Hold",2);
                    break;
                default:
                    report.updateTestLog("removeHoldPage", "Please provide the valid operation on Remove hold page, Continue or Go Back", Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function: Validate Change Amount Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void ChangeAmountPage() throws Exception{

        verifyTopSection();
        //validating the  Informative text
        isElementDisplayedWithLog(getObject("ManageStandingOrder.InfoTextPutOnHold"),
                "Informative text 'Standing order changes must be made  at least 1 working day prior to their due date.'","Standing Order Change Amount",2);
        //validate Label New Amount
        isElementDisplayedWithLog(getObject("ManageStandingOrder.lblNewAmount"),
                "Label 'New amount' ","Standing Order Change Amount",2);
         //Validate Label Amount Range
        isElementDisplayedWithLog(getObject("ManageStandingOrder.lblAmountRange"),
                "Label acceptable amount range 'Between 1.00 and 3,000.00' ","Standing Order Change Amount",2);

        String currency = mapStandingOrderFields.get("Previous amount").split(" ")[0];


        if (isElementDisplayedWithLog(getObject("ManageStandingOrder.lblCurrencySymbol"),
                "Label for Currency", "Standing Order Change amount", 5)) {
            if(getText(getObject("ManageStandingOrder.lblCurrencySymbol")).trim().equals(currency)){
                report.updateTestLog("ChangeAmount", "Currency symbol is displayed as expected on Standing Order Change amount Page, Expected: '"+ currency +",':: Actual '"+ getText(getObject("ManageStandingOrder.lblCurrencySymbol")).trim() +"'", Status_CRAFT.DONE);
            }else{report.updateTestLog("ChangeAmount", "Currency symbol is NOT displayed as expected on Standing Order Change amount Page, Expected: '"+ currency +",':: Actual '"+ getText(getObject("ManageStandingOrder.lblCurrencySymbol")).trim() +"'", Status_CRAFT.FAIL);}
        }

        if (isElementDisplayedWithLog(getObject("ManageStandingOrder.txtNewAmount"),
                "Text box 'New Amount'", "Standing Order Change amount", 5)) {
            if(!dataTable.getData("General_Data","NewAmount").equals("")){
                strChangedAmount = dataTable.getData("General_Data","NewAmount");
                sendKeys(getObject("ManageStandingOrder.txtNewAmount"),strChangedAmount,"New Amount :");
            }
        }
        //Validate Buttons
        isElementDisplayedWithLog(getObject("ManageStandingOrder.btnGoback"),
                "Button 'Go back' ","Standing Order Change Amount",2);

        isElementDisplayedWithLog(getObject("ManageStandingOrder.btnContinue"),
                "Button 'Continue' ","Standing Order Change Amount",2);

        if(!dataTable.getData("General_Data","Operation").equals("")){
            String strOperationOnPutonHold = dataTable.getData("General_Data","Operation");
            switch (strOperationOnPutonHold.toUpperCase()){
                case "CONTINUE":
                    clickJS(getObject("ManageStandingOrder.btnContinue"),"Clicked on 'Continue', Standing Order Change amount Page");
                    break;
                case "GO BACK":
                    clickJS(getObject("ManageStandingOrder.btnGoback"),"Clicked on 'Go back', Put on Hold Page");
                    if(isElementDisplayed(getObject("StandingOrderDetails.lblInformationMsg"),5)&& isElementDisplayed(getObject(deviceType()+"StandingOrderDetails.hdrPageTitle"),5)){
                        report.updateTestLog("PutonholdPage", "Page navigated to Standing Order Details Page upon clicking 'Go back'", Status_CRAFT.PASS);
                    }else{report.updateTestLog("PutonholdPage", "Page does not navigated to Standing Order Details Page upon clicking 'Go back'", Status_CRAFT.FAIL);}
                    break;
                default:
                    report.updateTestLog("PutonholdPage", "Please provide the valid operation on Standing Order Change amount Page, Continue or Go Back",Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function: Validate Error Change Amount Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void errChangeAmountPage() throws Exception{
        //Validate Error Message
        String currency = mapStandingOrderFields.get("Previous amount").split(" ")[0];
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if(currency.equals("€")){
            isElementDisplayedWithLog(getObject("xpath~//*[contains(@class,'boi_input_sm_error boi-error-color boi-error-position')]" +
                    "[contains(text(),'Amount entered must be a minimum of €1.00 and cannot exceed €3,000.00')]"),
                    "Error message 'Amount entered must be a minimum of €1.00 and cannot exceed €3,000.00'","Put on Standing Order Amount Change",2);
        }else{
            isElementDisplayedWithLog(getObject("xpath~//*[contains(@class,'boi_input_sm_error boi-error-color boi-error-position')]" +
                    "[contains(text(),'Amount entered must be a minimum of £1.00 and cannot exceed £3,000.00')]"),
                    "Error message 'Amount entered must be a minimum of £1.00 and cannot exceed £3,000.00'","Standing Order Amount Change",2);
        }
    }

    /**
     * Function: Validate Set Up SO Account Selection
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */

    public void setUpSOAccountSelection() throws Exception{
        int index = 0 ;

        //validating page title 'Set up Standing Order'
        isElementDisplayedWithLog(getObject(deviceType()+"SetupStandingOrder.hdrPageTitle"),
                "Page Title 'Set up Standing Order' ","Set up Standing Order Account Selection",2);

        //validating section category
        isElementDisplayedWithLog(getObject("SetupStandingOrder.lblSectionCategory"),
                "Section category label 'Your account' ","Set up Standing Order Account Selection",2);

        //validating informative text
        isElementDisplayedWithLog(getObject("SetupstandingOrder.lblInformativetext"),
                "Informative text 'You can set up a standing order online from your current account to another account in the same country.' ","Set up Standing Order Account Selection",2);

        //validating dropdown label
        isElementDisplayedWithLog(getObject("SetupStaningOrder.lblPayfrom"),
                "Dropdown label 'Pay from' ","Set up Standing Order Account Selection",2);

        //validating and selecting dropdown items
        if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lstPayfrom"),
                "Drop Down 'Pay from'", "Set up Standing Order Account Selection", 5)) {
            String strAccPayfrom = dataTable.getData("General_Data","Account_Type");

            if (deviceType.equalsIgnoreCase("Web"))
            {
                click(getObject("SetupStandingOrder.lstPayfrom"),"Pay From drop down");
            }else if (deviceType.equalsIgnoreCase("MobileWeb"))
            {
                clickJS(getObject("SetupStandingOrder.lstPayfrom"),"Pay From drop down");
            }
            List<WebElement> elm = driver.findElements(getObject("SetupStandingOrder.lstItemsPayfrom"));
            boolean bflag = true;
            for (int i = 0; i < elm.size(); i++) {
                String strAccntText = elm.get(i).getText();
                if (strAccntText.contains(strAccPayfrom)) {
                    index = i;
                    break;
                }
            }
            if(bflag){
                report.updateTestLog("setUpSOAccountSelection", "Drop Down has only 'Current' accounts displayed", Status_CRAFT.PASS);
            }
            if (deviceType.equalsIgnoreCase("Web"))
            {
                click(getObject("xpath~//button[contains(text(),'Select an account')]/following-sibling::ul[contains(@class,'list')]/li[contains(text(),'"+strAccPayfrom+"')]"),"Selected Account  '"+strAccPayfrom+"'");
            }else if (deviceType.equalsIgnoreCase("MobileWeb"))
            {
                //clickJS(getObject("xpath~//select[contains(@name,'GETVALIDPAYERCURRENTACCOUNTS[1].ACCOUNTS')]/following-sibling::div/ul/li[@class='option'][text()='"+strAccPayfrom+"']"),"Selected Account  '"+strAccPayfrom+"'");
               clickJS(getObject("xpath~//ul[contains(@class,'exp_elem_list')]/descendant::li[contains(@class,'option boi_input')][contains(text(),'" + strAccPayfrom + "')]"),"Selected Account  '"+strAccPayfrom+"'");

            }
        } else{
            report.updateTestLog("setUpSOAccountSelection", "Drop Down 'Pay from' is Not present on Set up Standing Order Account Selection Page", Status_CRAFT.FAIL);
        }
    }

    /**
     * Function: Validate Enter SOPayee Details Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void enterSOPayeedetails() throws Exception {
        String strCurrSymbol = dataTable.getData("General_Data", "Currency_Symbol");

        //validating section heading 'Payee Details'
        isElementDisplayedWithLog(getObject("SetupStandingOrder.hdrPayeeDetails"),
                "Section header 'Payee details' ","Set up Standing Order's Payee Details",2);

        //validating label 'Name'
        if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblName"),
                "Label 'Name'", "Set up Standing Order's Payee Details", 5)) {
            String strPayeeName = dataTable.getData("General_Data", "PayeeName");
            if (!strPayeeName.equals("")) {
                sendKeys(getObject("SetupStandingOrder.txtName"), strPayeeName, "Payee Name entered is '" + strPayeeName + "'");
            }
        }

        if(strCurrSymbol.equalsIgnoreCase("€")){
            //validating label 'IBAN'
            if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblIBAN"),
                    "Label 'IBAN'", "Set up Standing Order's Payee Details", 5)) {
                //validating IBAN ToolTip
                Actions action = new Actions((WebDriver) driver.getWebDriver());
                action.moveToElement(driver.findElement(getObject("SetupStandingOrder.lblIBAN"))).clickAndHold().build().perform();
                String strExpectedIBANToolTip = "The International Bank Account Number of the Payee quoted in international format that identifies the individual account as being in a specific financial institution in a particular country.";
                String strUIIBANToolTip = getText(getObject("SetupStandingOrder.elmIBANToolTip"));

                if(strUIIBANToolTip.equals(strExpectedIBANToolTip)){
                    report.updateTestLog("enterSOPayeedetails", "IBAN tooltip is correctly displayed on Set up Standing Order's Payee Details Page, Expected '"+strExpectedIBANToolTip+"'", Status_CRAFT.DONE);
                }else{
                    report.updateTestLog("enterSOPayeedetails", "IBAN tooltip is NOT correctly displayed on Set up Standing Order's Payee Details Page, Actual '"+strUIIBANToolTip+"'", Status_CRAFT.DONE);
                }
                //enter the BIC
                String strPayeeIBAN = dataTable.getData("General_Data","IBAN_Number");
                if(!strPayeeIBAN.equals("")){
                    Thread.sleep(1000);
                    sendKeys(getObject("SetupStandingOrder.txtIBAN"),strPayeeIBAN,"IBAN entered is '"+strPayeeIBAN+"'");
                    Thread.sleep(1000);
                }
            }

            //validating label 'BIC'
            if (isElementDisplayedWithLog(getObject(deviceType() + "SetupStandingOrder.lblBicOptional"),
                    "Label 'BIC(optional)'", "Set up Standing Order's Payee Details", 5)) {

                //validating BIC ToolTip
//                Actions a1 = new Actions((WebDriver) driver.getWebDriver());
//                WebElement strUIBicToolTip = driver.findElement(By.xpath("//span[@class='boi-tooltip boi-top-3 fa fa-info-circle']"));
//                Thread.sleep(1000);
//                a1.clickAndHold().moveToElement(strUIBicToolTip);
//                a1.moveToElement(strUIBicToolTip).build().perform();

                Actions action = new Actions((WebDriver) driver.getWebDriver());
                action.moveToElement(driver.findElement(getObject(deviceType() + "SetupStandingOrder.lblBicOptional"))).clickAndHold().build().perform();
                String strUIBicToolTip = getText(getObject("SetupStandingOrder.elmBICToolTip"));
                String strExpectedBICToolTip = "Bank Identifier Code (BIC) is the SWIFT address assigned to a Payee's bank. A BIC consists of eight or eleven characters. if you choose not to provide a BIC, we will use the IBAN to determine it.";
                if(strUIBicToolTip.equals(strExpectedBICToolTip)){
                    report.updateTestLog("enterSOPayeedetails", "BIC tooltip is correctly displayed on Set up Standing Order's Payee Details Page, Expected '"+strExpectedBICToolTip+"'", Status_CRAFT.DONE);
                }else{
                    report.updateTestLog("enterSOPayeedetails", "BIC tooltip is NOT correctly displayed on Set up Standing Order's Payee Details Page, Actual '"+strUIBicToolTip+"'", Status_CRAFT.FAIL);
                }
                //enter the BIC
                String strPayeeBIC = dataTable.getData("General_Data","BIC_Number");
                if(!strPayeeBIC.equals("")){
                    sendKeys(getObject("SetupStandingOrder.txtBIC"),strPayeeBIC,"BIC entered is '"+strPayeeBIC+"'");
                }
            }


            } else   //for NI/GB customers
            {
                if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblAccountnumber"),
                        "Label 'Account number'", "Set up Standing Order's Payee Details", 5)) {
                    String AccountNumber = dataTable.getData("General_Data", "AccountNumber");
                    if (!AccountNumber.equals("")) {
                        sendKeys(getObject("SetupStandingOrder.txtAccountnumber"), AccountNumber, "Account number entered is '" + AccountNumber + "'");
                    }
                }
                //Validate Label National Sort Code
                if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblNationalsortcode"),
                        "Label 'National sort code'", "Set up Standing Order's Payee Details", 5)) {
                    String NSC = dataTable.getData("General_Data", "NationalSortCode");
                    if (!NSC.equals("")) {
                        sendKeys(getObject("SetupStandingOrder.txtNationalsortcode"), NSC, "National sort code entered is '" + NSC + "'");
                    }
                }

            }

            //Message to payee
            if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblMessageToPayee"),
                    "Label 'Message to payee'", "Set up Standing Order's Payee Details", 5)) {
                String strPayeeName = dataTable.getData("General_Data", "PayeeMessage");
                if (!strPayeeName.equals("")) {
                    sendKeys(getObject("SetupStandingOrder.txtMessageToPayee"), strPayeeName, "Message to payee entered is '" + strPayeeName + "'");
                }
            }

        //validating section heading 'Frequency & Amount'
        isElementDisplayedWithLog(getObject("SetupStandingOrder.hdrFrequencyAmount"),
                "Section header 'Frequency & amount' ","Set up Standing Order's Payee Details",2);

        isElementDisplayedWithLog(getObject("SetupStandingOrder.lblFrequency"),
                "Label 'Frequency' ","Set up Standing Order's Payee Details",2);

        isElementDisplayedWithLog(getObject("SetupStandingOrder.lblSelectFrequency"),
                "Label 'Select frequency' ","Set up Standing Order's Payee Details",2);

            //frequency Tabs
            List<WebElement> tabsFreq = driver.findElements(getObject("SetupStandingOrder.tbsSelectFrequency"));
            String[] arrTabsFreq = {"Weekly", "Fortnightly", "Monthly", "Yearly"};
            if (tabsFreq.size() != 0) {
                for (int i = 0; i < tabsFreq.size(); i++) {
                    if (tabsFreq.get(i).getText().equals(arrTabsFreq[i])) {
                        report.updateTestLog("enterSOPayeedetails", "Select frequency tab '" + arrTabsFreq[i] + "' is displayed on Set up Standing Order's Payee Details Page", Status_CRAFT.DONE);
                    } else {
                        report.updateTestLog("enterSOPayeedetails", "Select frequency tab '" + arrTabsFreq[i] + "' is NOT displayed on Set up Standing Order's Payee Details Page", Status_CRAFT.FAIL);
                    }
                }

            String strFreq = dataTable.getData("General_Data","SOFrequency");
            if(!strFreq.equals("")) {
                if (isElementDisplayed(getObject("xpath~//*[contains(@class,'boi-toggle-blue-light-mini')]/ul/li/span[text()='" + strFreq + "']"), 5)) {
                    clickJS(getObject("xpath~//*[contains(@class,'boi-toggle-blue-light-mini')]/ul/li/span[text()='" + strFreq + "']"), "Frequency selected is '" + strFreq + "'");
                }
            }

        }else{
            report.updateTestLog("enterSOPayeedetails", "None of the Select frequency tabs are displayed on Set up Standing Order's Payee Details Page", Status_CRAFT.FAIL);
        }
        //startDate
        String strFreq = dataTable.getData("General_Data","SOFrequency");
        if(strFreq.equalsIgnoreCase("Monthly")|strFreq.equalsIgnoreCase("Yearly")|strFreq.equalsIgnoreCase("Weekly")|strFreq.equalsIgnoreCase("Fortnightly")){
            if(isElementDisplayed(getObject("SetupStandingOrder.lblStartDate"),5)){
                report.updateTestLog("enterSOPayeedetails", "Label 'Start date' is displayed on Set up Standing Order's Payee Details Page", Status_CRAFT.PASS);
                if(isElementDisplayed(getObject("SetupStandingOrder.lblStartDateInfo"),5)){
                    report.updateTestLog("enterSOPayeedetails", "Informative message 'Must be later than today's date' is displayed ", Status_CRAFT.PASS);
                }else{
                    report.updateTestLog("enterSOPayeedetails", "Informative message 'Must be later than today's date' is NOT displayed ", Status_CRAFT.FAIL);
                }
                if(driver.findElement(getObject("SetupStandingOrder.txtStartDate")).getAttribute("placeholder").equalsIgnoreCase("DD/MM/YYYY")){
                    report.updateTestLog("enterSOPayeedetails", "Text box 'Start date' has correct directional text, Actual :"+driver.findElement(getObject("SetupStandingOrder.txtStartDate")).getAttribute("placeholder"), Status_CRAFT.DONE);
                }else{
                    report.updateTestLog("enterSOPayeedetails", "Text box 'Start date' has incorrect directional text, Actual :"+driver.findElement(getObject("SetupStandingOrder.txtStartDate")).getAttribute("placeholder"), Status_CRAFT.FAIL);
                }
                String strStartDate = dataTable.getData("General_Data", "DateRangeFrom");
                if(!strStartDate.equals("")){
                    enterDate("SetupStandingOrder.btncal",strStartDate);
                }
            }
        }

        //Amount
        if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblAmount"),
                "Label 'Amount'", "Set up Standing Order's Payee Details", 5)) {
            String strAmountRange = "Between "+strCurrSymbol+"1.00 and "+strCurrSymbol+"3,000.00";
            //validate Amount Range
            if(getText(getObject("xpath~//label[text()='Amount ']/span[@class='boi_input_sm ']")).equals(strAmountRange)){
                report.updateTestLog("enterSOPayeedetails", "Amount Range label is correct '"+ strAmountRange +"' on Set up Standing Order's Payee Details Page", Status_CRAFT.DONE);
            }else{
                report.updateTestLog("enterSOPayeedetails", "Amount Range label is not correct or not present on Set up Standing Order's Payee Details Page, UI label value '"+getText(getObject("xpath~//label[text()='Amount ']/span[@class='boi_input_sm ']"))+"'", Status_CRAFT.FAIL);
            }
            //Validate Currency Symbol
            if(getText(getObject("ManageStandingOrder.lblCurrencySymbol")).equals(strCurrSymbol)){
                report.updateTestLog("enterSOPayeedetails", "Currency Symbol label is correct '"+ strCurrSymbol +"' in Amount Field on Set up Standing Order's Payee Details Page", Status_CRAFT.DONE);
            }else{
                report.updateTestLog("enterSOPayeedetails", "Currency Symbol label is NOT correct '"+ strCurrSymbol +"' in Amount Field on Set up Standing Order's Payee Details Page, UI label value '"+getText(getObject("xpath~//label[text()='Amount ']/span[@class='boi_input_sm ']"))+"'", Status_CRAFT.FAIL);
            }

                String strAmount = dataTable.getData("General_Data", "NewAmount");
                if (!strAmount.equals("")) {
                    sendKeys(getObject("ManageStandingOrder.txtNewAmount"), strAmount, "Amount entered is '" + strAmount + "'");
                }
            }

        isElementDisplayedWithLog(getObject("ManageStandingOrder.btnGoback"),
                "Button 'Go back' ","Set up Standing Order's Payee Details",2);

        isElementDisplayedWithLog(getObject("ManageStandingOrder.btnContinue"),
                "Button 'Continue' ","Set up Standing Order's Payee Details",2);

        if(!dataTable.getData("General_Data","Operation").equals("")){
            String strOperationOnPutonHold = dataTable.getData("General_Data","Operation");
            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
            switch (strOperationOnPutonHold.toUpperCase()){
                case "CONTINUE":
                    js.executeScript("arguments[0].scrollIntoView();",driver.findElement(getObject("ManageStandingOrder.btnContinue")));
                    Thread.sleep(2000);
                    if (deviceType.equalsIgnoreCase("Web")) {
                        click(getObject("ManageStandingOrder.btnContinue"),"Clicked on 'Continue'on Set up Standing Order's Payee Details Page");
                    } else if (deviceType.equalsIgnoreCase("MobileWeb")) {
                        clickJS(getObject("ManageStandingOrder.btnContinue"),"Clicked on 'Continue' on Set up Standing Order's Payee Details Page");
                    }

                    break;
                case "GO BACK":
                    js.executeScript("arguments[0].scrollIntoView();",driver.findElement(getObject("ManageStandingOrder.btnGoback")));
                    Thread.sleep(2000);
                    click(getObject("ManageStandingOrder.btnGoback"),"Clicked on 'Go back' on Set up Standing Order's Payee Details Page");
                    isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.btnSetUpNewStandingOrder"),
                            "Page navigated to Standing Order Landing Page","After Click 'Go back'",2);
                    break;
                default:
                    report.updateTestLog("enterSOPayeedetails", "Please provide the valid operation on enter Payee Details, Continue or Go Back",Status_CRAFT.FAIL);
            }
        }
    }

    /**
     * Function: Validate SetUp SO Review Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void SetupSOReviewPage() throws Exception{
        //validating page title 'Set up Standing Order'
        isElementDisplayedWithLog(getObject(deviceType()+"SetupStandingOrder.hdrPageTitle"),
                "Page Title 'Set up Standing Order' ","Review Page",2);

        isElementDisplayedWithLog(getObject("ManageStandingOrder.hdrReview"),
                "Section Header 'Review' ","Review Page",2);

        LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
        String[] strVerifyDetails = dataTable.getData("General_Data", "VerifyDetails").split(";");
        String strOperation = dataTable.getData("General_Data", "OperationOnReviewPage");

        for (int i = 0; i < strVerifyDetails.length; i++) {
            String strFieldName = strVerifyDetails[i].split("::")[0];
            String strFieldvalue = strVerifyDetails[i].split("::")[1];
            mapData.put(strFieldName, strFieldvalue);
        }

        for (Map.Entry obj : mapData.entrySet()) {
            if(!obj.getValue().equals("NA")){
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']/ancestor::div[contains(@class,'question-part')]/following-sibling::div[contains(@class,'answer-part')]/descendant::*[text()='" + obj.getValue() + "']"), 5)) {
                    report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' correctly displayed on review page,input::" + obj.getValue(), Status_CRAFT.PASS);
                } else {
                    report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' Not correctly displayed on review page,Expected::" + obj.getValue(), Status_CRAFT.FAIL);
                }
            }else{
                if (isElementDisplayed(getObject("xpath~//*[text()='" + obj.getKey() + "']"), 5)) {
                    report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' is displayed on review page for blank input", Status_CRAFT.FAIL);
                } else {
                    report.updateTestLog("SetupSOReviewPage", "'" + obj.getKey() + "' is Not displayed on review page for blank input" , Status_CRAFT.PASS);
                }
            }
        }
        //Validate Go Back Button on Review Page
        isElementDisplayedWithLog(getObject(deviceType()+"xpath~//a/*[text()='Go back']"),
                "Button ''Go back' ","Review",2);

        //Validate Continue Button on Review Page
        isElementDisplayedWithLog(getObject(deviceType()+"xpath~//a/*[text()='Continue']"),
                "Button ''Continue' ","Review",2);

        clickJS(getObject("xpath~//a/*[text()='" + strOperation + "']"), " " + strOperation);
        if (isElementDisplayed(getObject("xpath~//span/*[text()='Go back']"), 5)) {
            report.updateTestLog("SetupSOReviewPage", "'Go back' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SetupSOReviewPage", "'Go back' button is NOT displayed", Status_CRAFT.FAIL);
        }
        if (isElementDisplayed(getObject("xpath~//a/*/*[text()='Confirm']"), 5)) {
            report.updateTestLog("SetupSOReviewPage", "'Confirm' button is displayed", Status_CRAFT.PASS);
        } else {
            report.updateTestLog("SetupSOReviewPage", "'Confirm' button is NOT displayed", Status_CRAFT.FAIL);
        }

    /*    if (deviceType.equalsIgnoreCase("Web"))
        {
            click(getObject("xpath~//a*//*[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
        }else if (deviceType.equalsIgnoreCase("MobileWeb"))
        {
            clickJS(getObject("xpath~//a*//*[text()='" + strOperation + "']"), "Clicked on Button" + strOperation);
        }

        if(strOperation.equalsIgnoreCase("Go back")){
            isElementDisplayedWithLog(getObject(deviceType()+"SetupStandingOrder.hdrPayeeDetails"),
                    "Button ''Go Back' ","Navigated To Set up Standing Order Payee Details",2);
        }*/
    }

    /**
     * Function: Validate SetUp SO Confirmation Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void SetupSOConfirmationPage() throws Exception{
        String strUserStatus = dataTable.getData("General_Data", "Relationship_Status");
        if (strUserStatus.equals("CheckPushNotification")){
            report.updateTestLog("SetupSOConfirmationPage page", " :: SCA Push notification ::", Status_CRAFT.SCREENSHOT);
            Boolean blnPush = isElementDisplayed(getObject(deviceType()+"SetupStandingOrder.hdrPageTitle"),150);
            if (blnPush){
                report.updateTestLog("SetupSOConfirmationPage page", " :: SCA Push notification Approved Successfully..!", Status_CRAFT.PASS);
            }
        }
        //validating page title 'Set up Standing Order'
        isElementDisplayedWithLog(getObject(deviceType()+"SetupStandingOrder.hdrPageTitle"),
                "Page Title 'Set up Standing Order' ","Confirmation Page",2);
         //Validate Label Please Activate
        isElementDisplayedWithLog(getObject("SetupStandingOrder.lblPleaseActivate"),
                "Success Icon heading 'Success!' ","Confirmation Page",2);
        //Validate Label Thank You
//        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.lblThankYou"),
//                "Text message 'Thank you!' ","Confirmation Page",2);
        //Validate Label Information Message
        isElementDisplayedWithLog(getObject("StandingOrderDetails.lblInformationMsg_Inactive"),
                "Informative message 'Once activated, this will be added to your list of standing orders within 1 working day.' ","Confirmation Page",2);
        //Validate Back To Home
        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.btnBacktoHome"),
                "Button 'Back to home' ","Confirmation Page",2);

        if (isElementDisplayedWithLog(getObject("StandingOrder.lblTimeOfRequest"),
                "Time Stamp with message 'Time of request'", "Confirmation", 5)) {
            String strRequestSubmitted = getText(getObject("StandingOrder.lblTimeOfRequest"));
            String uiDateText = strRequestSubmitted.split(": ")[1];
            DateFormat df = new SimpleDateFormat("DD/MM/YYYY HH:MM");
            try {
                df.parse(uiDateText);
                report.updateTestLog("StandingOrder.lblTimeOfRequest", "Text Label '"+strRequestSubmitted+"' has correct date format DD/MM/YYYY HH:MM", Status_CRAFT.PASS);
            } catch (ParseException e) {
                report.updateTestLog("StandingOrder.lblTimeOfRequest", "Text Label '"+strRequestSubmitted+"' has incorrect date format, Expected format 'DD/MM/YYYY HH:MM'" +"', Actual '"+ uiDateText +"'", Status_CRAFT.PASS);
            }
        }

        //Validating Navigation/functionality of the buttons
        if(dataTable.getData("General_Data","NavigationfromConfirmationPage").equalsIgnoreCase("Back to home")){
            clickJS(getObject(deviceType()+"StandingOrder.btnBacktoHome"),"Clicked on 'Back to home'");
            isElementDisplayedWithLog(getObject(deviceType()+"home.sectionTimeLine"),
                    "Upon clicking 'Back to home' ","Navigate Home Page from Confirmation Page",7);
        }
    }

    /**
     * Function: Validate Enter Date Function
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void enterDate(String objDOB, String strDate) throws Exception {

        //Splitting the date
        String strYY = strDate.split("/")[2];
        String strMON = strDate.split("/")[1];
        String strDT = strDate.split("/")[0].replaceAll("^0*", "");
        if(!deviceType.equals("w.")){
            driver.hideKeyboard();
        }
        //Date selection
        clickJS(getObject(objDOB), "Date Object");
        waitForPageLoaded();
        Thread.sleep(1000);
        selectDropDown(getObject("launch.lstYearSavingDate"), strYY);
        selectDropDown(getObject("launch.lstMonthSavingDate"), strMON);
        clickJS(getObject("xpath~//a[.='" + strDT + "']"), "Date :" + strDT + " selected");
    }

    /**
     * Function: Validate Error Messages
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void errorMessageValidations() throws Exception{
        waitForPageLoaded();
        Thread.sleep(3000);
        clickJS(getObject("ManageStandingOrder.btnContinue"),"Clicked on 'Continue', Set up Standing Order's Payee Details Page");
        String errorMessage[] =  dataTable.getData("General_Data","ErrorText").split("::");
        for(int i=0;i<errorMessage.length;i++){
            if(!(errorMessage[i].contains("Please select a frequency")|errorMessage[i].contains("Please select a frequency")|errorMessage[i].contains("Please enter an amount")|errorMessage[i].contains("Amount entered must be a minimum of "))){
                isElementDisplayedWithLog(getObject("xpath~//*[contains(@class,'tc-error-position')][contains(text(),'"+errorMessage[i]+"')]"),
                        "Error Message '" + errorMessage[i] +"'" ,"Standing Order Account Selection",2);
            }else if(errorMessage[i].contains("Between €1.00 and  €3,000.00")){
                isElementDisplayedWithLog(getObject("xpath~//span[contains(text(),'Between €1.00 and  €3,000.00')]"),
                        "Error Message '" + errorMessage[i] +"'" ,"Standing Order Account Selection",2);
            }else{
                isElementDisplayedWithLog(getObject("xpath~//*[contains(@class,'boi_input_sm_error')][contains(text(),'"+errorMessage[i]+"')]"),
                        "Error Message '" + errorMessage[i] +"'" ,"Standing Order Account Selection",2);


            }
        }
        }


    /**
     * Function: Validate Back navigatios Function
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void backNavigationValidation() throws Exception{
        //Validating Payments link
        if(deviceType.equalsIgnoreCase("Web")){
            if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lnkSetupStandingOrder"),
                    "Back Navigation link 'Set up Standing Order'", "Set up Standing Order", 5)) {
                click(getObject("SetupStandingOrder.lnkSetupStandingOrder"),"Clicked on Back Navigation link");
                waitForPageLoaded();
                isElementDisplayedWithLog(getObject(deviceType()+"StandingOrderDetails.hdrPageTitle"),
                        "Title 'Back Navigation' ","navigated to Standing Order list",2);
            }

        }else{
            if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lnkBackArrow"),
                    "Back Navigation link", "Set up Standing Order Account Selection", 5)) {
                click(getObject("SetupStandingOrder.lnkBackArrow"),"Clicked on Back Navigation link");
                waitForPageLoaded();
                isElementDisplayedWithLog(getObject(deviceType()+"StandingOrderDetails.hdrPageTitle"),
                        "Title 'Back Navigation' ","navigated to Standing Order list",2);
            }
        }
    }

    /**
     * Function: Validate SetUp Order Maximum Length
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void setupStandingOrderMaxLength() throws Exception{
        String []obj = dataTable.getData("General_Data", "objName").split("::");
        String []maxLength = dataTable.getData("General_Data", "MaxLength").split("::");

        HomePage homepage = new HomePage(scriptHelper);
        for(int i=0;i<obj.length;i++){
            int intMaxLength = Integer.parseInt(maxLength[i]);
            homepage.verifyMaxLength(intMaxLength, obj[i]);
        }

    }
    /**
     * Function: Validate Navigate So Enter Details
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void NavigateSOEnterDetails() throws Exception
    {
        if (isElementDisplayedWithLog(getObject("StandingOrder.lstSelectAccount"),
                "Drop Down to Select Standing Order", "Set up Standing Order", 5)) {
            String strAccount = dataTable.getData("General_Data", "Account_Type");
            {
                if (dataTable.getData("General_Data", "ActionStandingOrder").equalsIgnoreCase("Setup")) {
                    click(getObject("StandingOrder.btnSetUpNewStandingOrder"), "Clicked on 'Set up new standing order' Button");
                    waitForPageLoaded();
                }
                else {
                    if (deviceType.equalsIgnoreCase("Web")) {
                        click(getObject("StandingOrder.lstSelectAccount"));
                        driver.findElement(By.xpath("//select[contains(@name,'STANDINGORDERLIST[1].ACCOUNT')]/following-sibling::div/ul/li[contains(.,'" + strAccount + "')]")).click();
                        report.updateTestLog("selectStandingOrderAccount", "'" + strAccount + "' Account is selected Standing Order dropdown", Status_CRAFT.PASS);
                    }
                    else
                    {
                        try {
                            click(getObject("StandingOrder.lstSelectAccount"));
                            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//button[contains(text(),'Select an account')]/following-sibling::ul/li[contains(text(),'" + strAccount + "')]")));
                            Thread.sleep(2000);
                        } catch (UnreachableBrowserException e) {
                            e.printStackTrace();
                        } catch (StaleElementReferenceException e) {
                            e.printStackTrace();
                        }
                        driver.findElement(By.xpath("//button[contains(text(),'Select an account')]/following-sibling::ul/li[contains(text(),'" + strAccount + "')]")).click();
                        report.updateTestLog("selectStandingOrderAccount", "Account '" + strAccount + "' Selected", Status_CRAFT.PASS);
                    }
                }
            }
        }


            //Validate dropdown has only 'Current' accounts
            String strAccPayfrom = dataTable.getData("General_Data","Account_Type");
            click(getObject("SetupStandingOrder.lstPayfrom"),"Clicked on Pay From drop down");
            List<WebElement> elm = driver.findElements(getObject("SetupStandingOrder.lstItemsPayfrom"));
            boolean bflag = true;
            for(int i=1;i<elm.size();i++){
                if(!elm.get(i).getText().toUpperCase().contains("CURRENT")){
                    bflag = false;
                    report.updateTestLog("setUpSOAccountSelection", "Drop Down has other account than 'Current' account,at index '"+(i+1)+", value '"+ elm.get(i).getText() +"'", Status_CRAFT.FAIL);
                }
            }
            if(bflag){
                report.updateTestLog("setUpSOAccountSelection", "Drop Down has only 'Current' accounts displayed", Status_CRAFT.PASS);
            }
            click(getObject("xpath~//button[contains(text(),'Select an account')]/following-sibling::ul/li[contains(text(),'"+strAccPayfrom+"')]"),"Selected Account  '"+strAccPayfrom+"'");
        }

    /**
     * Function: Validate Standing Order Duplicate Error
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void StandingOrderDuplicateError() throws Exception
    {

        if (isElementDisplayedWithLog(getObject("StandingOrder.lstSelectAccount"),
                "Drop Down to Select Standing Order", "Set up Standing Order", 5)) {
            String strAccount = dataTable.getData("General_Data", "Account_Type");
            {
                if (dataTable.getData("General_Data", "ActionStandingOrder").equalsIgnoreCase("Setup")) {
                    click(getObject("StandingOrder.btnSetUpNewStandingOrder"), "Clicked on 'Set up new standing order' Button");
                    waitForPageLoaded();
                }
                else {
                    if (deviceType.equalsIgnoreCase("Web")) {
                        click(getObject("StandingOrder.lstSelectAccount"));
                        driver.findElement(By.xpath("//select[contains(@name,'STANDINGORDERLIST[1].ACCOUNT')]/following-sibling::div/ul/li[contains(.,'" + strAccount + "')]")).click();
                        report.updateTestLog("selectStandingOrderAccount", "'" + strAccount + "' Account is selected Standing Order dropdown", Status_CRAFT.PASS);
                    }
                    else
                    {
                        try {
                            click(getObject("StandingOrder.lstSelectAccount"));
                            JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
                            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//button[contains(text(),'Select an account')]/following-sibling::ul/li[contains(text(),'" + strAccount + "')]")));
                            Thread.sleep(2000);
                        } catch (UnreachableBrowserException e) {
                            e.printStackTrace();
                        } catch (StaleElementReferenceException e) {
                            e.printStackTrace();
                        }
                        driver.findElement(By.xpath("//button[contains(text(),'Select an account')]/following-sibling::ul/li[contains(text(),'" + strAccount + "')]")).click();
                        report.updateTestLog("selectStandingOrderAccount", "Account '" + strAccount + "' Selected", Status_CRAFT.PASS);
                    }
                }
            }
        }
        String strAccPayfrom = dataTable.getData("General_Data","Account_Type");
        click(getObject("SetupStandingOrder.lstPayfrom"),"Clicked on Pay From drop down");
        click(getObject("xpath~//button[contains(text(),'Select an account')]/following-sibling::ul/li[contains(text(),'"+strAccPayfrom+"')]"),"Selected Account  '"+strAccPayfrom+"'");
        //validating section heading 'Payee Details'

        //validating label 'Name'
        if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblName"),
                "Label 'Name'", "Set up Standing Order's Payee Details", 5)) {
            report.updateTestLog("enterSOPayeedetails", "Label 'Name' is displayed on Set up Standing Order's Payee Details Page", Status_CRAFT.PASS);
            String strPayeeName = dataTable.getData("General_Data","PayeeName");
            if(!strPayeeName.equals("")){
                sendKeys(getObject("SetupStandingOrder.txtName"),strPayeeName,"Payee Name entered is '"+strPayeeName+"'");
            }
        }

        //validating label 'BIC'
        if (isElementDisplayedWithLog(getObject("SetupStandingOrder.txtBIC"),
                "'BIC(optional)'", "Set up Standing Order's Payee Details", 5)) {
            //validating BIC ToolTip
            click(getObject("SetupStandingOrder.txtBIC"));
            //enter the BIC
            String strPayeeBIC = dataTable.getData("General_Data","BIC_Number");
            if(!strPayeeBIC.equals("")){
                sendKeys(getObject("SetupStandingOrder.txtBIC"),strPayeeBIC,"BIC entered is '"+strPayeeBIC+"'");
            }
        }

        //validating label 'IBAN'
      if (isElementDisplayedWithLog(getObject("SetupStandingOrder.txtIBAN"),
                "'IBAN'", "Set up Standing Order's Payee Details", 5)) {
            //validating IBAN ToolTip
            click(getObject("SetupStandingOrder.txtIBAN"));
            //enter the BIC
            String strPayeeIBAN = dataTable.getData("General_Data","IBAN_Number");
            if(!strPayeeIBAN.equals("")){
                sendKeys(getObject("SetupStandingOrder.txtIBAN"),strPayeeIBAN,"IBAN entered is '"+strPayeeIBAN+"'");
            }
        }

        //Message to payee
        if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblMessageToPayee"),
                "Label 'Message to payee'", "Set up Standing Order's Payee Details", 5)) {
            String strPayeeName = dataTable.getData("General_Data","PayeeMessage");
            if(!strPayeeName.equals("")){
                sendKeys(getObject("SetupStandingOrder.txtMessageToPayee"),strPayeeName,"Message to payee entered is '"+strPayeeName+"'");
            }
        }


        //frequency Tabs
        List<WebElement> tabsFreq = driver.findElements(getObject("SetupStandingOrder.tbsSelectFrequency"));
        String [] arrTabsFreq = {"Weekly","Fortnightly","Monthly","Yearly"};
        if(tabsFreq.size()!=0){
            for(int i=0;i<tabsFreq.size();i++){
                if(tabsFreq.get(i).getText().equals(arrTabsFreq[i])){
                    report.updateTestLog("enterSOPayeedetails", "Select frequency tab '"+arrTabsFreq[i]+"' is displayed on Set up Standing Order's Payee Details Page", Status_CRAFT.DONE);
                }else{
                    report.updateTestLog("enterSOPayeedetails", "Select frequency tab '"+arrTabsFreq[i]+"' is NOT displayed on Set up Standing Order's Payee Details Page", Status_CRAFT.FAIL);
                }
            }

            String strFreq = dataTable.getData("General_Data","SOFrequency");
            if(!strFreq.equals("")){
                click(getObject("xpath~//*[contains(@class,'boi-toggle-blue-light-mini')]/ul/li/span[text()='"+strFreq+"']"),"Frequency selected is '"+strFreq+"'");
            }

        }else{
            report.updateTestLog("enterSOPayeedetails", "None of the Select frequency tabs are displayed on Set up Standing Order's Payee Details Page", Status_CRAFT.FAIL);
        }
        //startDate
        String strFreq = dataTable.getData("General_Data","SOFrequency");
        if(strFreq.equalsIgnoreCase("Monthly")|strFreq.equalsIgnoreCase("Yearly")|strFreq.equalsIgnoreCase("Weekly")){
            if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblStartDate"),
                    "Label 'Start date'", "Set up Standing Order's Payee Details", 5)) {
                String strStartDate = dataTable.getData("General_Data", "DateRangeFrom");
                enterDate("SetupStandingOrder.txtStartDate",strStartDate);
            }
        }

        //Amount
        if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblAmount"),
                "Label 'Amount'", "Set up Standing Order's Payee Details", 5)) {
            String strAmount = dataTable.getData("General_Data","NewAmount");
            if(!strAmount.equals("")){
                sendKeys(getObject("ManageStandingOrder.txtNewAmount"),strAmount,"Amount entered is '"+strAmount+"'");
            }
        }


        if(!dataTable.getData("General_Data","Operation").equals("")){
            String strOperationOnPutonHold = dataTable.getData("General_Data","Operation");
            switch (strOperationOnPutonHold.toUpperCase()){
                case "CONTINUE":
                    clickJS(getObject("ManageStandingOrder.btnContinue"),"Clicked on 'Continue', Set up Standing Order's Payee Details Page");
                    break;
                case "GO BACK":
                    clickJS(getObject("ManageStandingOrder.btnGoback"),"Clicked on 'Go back', Set up Standing Order's Payee Details Page");
                    isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.btnSetUpNewStandingOrder"),
                            "Button 'Go back' ","navigated to Standing Order Landing Page on click 'Go Back'",2);
                    break;
                default:
                    report.updateTestLog("enterSOPayeedetails", "Please provide the valid operation on enter Payee Details, Continue or Go Back",Status_CRAFT.FAIL);
            }
        }
        String ExpectedErrorText = "";
        String ActualErrorText = getText(getObject("SetupStandingOrder.DuplicateSOError"));

        if(ExpectedErrorText.equalsIgnoreCase(ActualErrorText))
        {
            report.updateTestLog("VerifyErrorDuplicateSO", "Error message '"+ExpectedErrorText+" 'is displayed correctly", Status_CRAFT.PASS);
        }
        else
        {
            report.updateTestLog("VerifyErrorDuplicateSO", "Error message '"+ExpectedErrorText+" 'is not displayed correctly", Status_CRAFT.FAIL);
        }

    }
    /**
     * Function: Validate Pin Success Page
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void PinSuccessPage() throws Exception {
        Thread.sleep(3000);
        waitForElementPresent(getObject("xpath~//div[text()='Success']"), 50);
        if (isElementDisplayedWithLog(getObject("xpath~//span[text()='Continue']"),
                "Button 'Continue' clicked", "PIN ", 5)) {
            click(getObject("xpath~//span[text()='Continue']"));
        }
    }
    /**
     * Function: Validate Enter SO Payee Details_GB
     * Update on     Updated by     Reason
     * 16/04/2019     c104521       Done code clean up activity
     */
    public void enterSOPayeedetails_GB() throws Exception {
        //validating section heading 'Payee Details'
        isElementDisplayedWithLog(getObject("SetupStandingOrder.hdrPayeeDetails"),
                "Section header 'Payee details' ","Set up Standing Order's Payee Details",2);

        //validating label 'Name'
        if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblName"),
                "Label 'Name'", "Set up Standing Order's Payee Details", 5)) {
            String strPayeeName = dataTable.getData("General_Data","PayeeName");
            if(!strPayeeName.equals("")){
                sendKeys(getObject("SetupStandingOrder.txtName"),strPayeeName,"Payee Name entered is '"+strPayeeName+"'");
            }
        }

        //Message to payee
        if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblMessageToPayee"),
                "Label 'Message to payee'", "Set up Standing Order's Payee Details", 5)) {
            String strPayeeName = dataTable.getData("General_Data","PayeeMessage");
            if(!strPayeeName.equals("")){
                sendKeys(getObject("SetupStandingOrder.txtMessageToPayee"),strPayeeName,"Message to payee entered is '"+strPayeeName+"'");
            }
        }

        //validating section heading 'Frequency & Amount'
        isElementDisplayedWithLog(getObject("SetupStandingOrder.hdrFrequencyAmount"),
                "Section header 'Frequency & amount' ","Set up Standing Order's Payee Details",2);

        isElementDisplayedWithLog(getObject("SetupStandingOrder.lblFrequency"),
                "Label 'Frequency' ","Set up Standing Order's Payee Details",2);

        isElementDisplayedWithLog(getObject("SetupStandingOrder.lblSelectFrequency"),
                "Label 'Select frequency'","Set up Standing Order's Payee Details",2);
        //enter the BIC
        String strPayeeIBAN = dataTable.getData("General_Data","IBAN_Number");
        if(!strPayeeIBAN.equals("")){
            Thread.sleep(1000);
            sendKeys(getObject("SetupStandingOrder.txtIBAN"),strPayeeIBAN,"IBAN entered is '"+strPayeeIBAN+"'");
            Thread.sleep(1000);
        }
        //frequency Tabs
        List<WebElement> tabsFreq = driver.findElements(getObject("SetupStandingOrder.tbsSelectFrequency"));
        String [] arrTabsFreq = {"Weekly","Fortnightly","Monthly","Yearly"};
        if(tabsFreq.size()!=0){
            for(int i=0;i<tabsFreq.size();i++){
                if(tabsFreq.get(i).getText().equals(arrTabsFreq[i])){
                    report.updateTestLog("enterSOPayeedetails", "Select frequency tab '"+arrTabsFreq[i]+"' is displayed on Set up Standing Order's Payee Details Page", Status_CRAFT.DONE);
                }else{
                    report.updateTestLog("enterSOPayeedetails", "Select frequency tab '"+arrTabsFreq[i]+"' is NOT displayed on Set up Standing Order's Payee Details Page", Status_CRAFT.FAIL);
                }
            }


        //startDate
        String strFreq = dataTable.getData("General_Data","SOFrequency");
        if(strFreq.equalsIgnoreCase("Monthly")|strFreq.equalsIgnoreCase("Yearly")|strFreq.equalsIgnoreCase("Weekly")){
            if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblStartDate"),
                    "Label 'Start date'", "Set up Standing Order's Payee Details", 5)) {
                isElementDisplayedWithLog(getObject("SetupStandingOrder.lblStartDateInfo"),
                        "Informative message 'Must be later than today's date'","Set up Standing Order's Payee Details",2);
                if(driver.findElement(getObject("SetupStandingOrder.txtStartDate")).getAttribute("placeholder").equalsIgnoreCase("DD/MM/YYYY")){
                    report.updateTestLog("enterSOPayeedetails", "Text box 'Start date' has correct directional text, Actual :"+driver.findElement(getObject("SetupStandingOrder.txtStartDate")).getAttribute("placeholder"), Status_CRAFT.PASS);
                }else{
                    report.updateTestLog("enterSOPayeedetails", "Text box 'Start date' has incorrect directional text, Actual :"+driver.findElement(getObject("SetupStandingOrder.txtStartDate")).getAttribute("placeholder"), Status_CRAFT.FAIL);
                }
                String strStartDate = dataTable.getData("General_Data", "DateRangeFrom");
                TransferBetweenAccounts tba = new TransferBetweenAccounts(scriptHelper);
                enterDate("SetupStandingOrder.txtStartDateCalendar",tba.getFutureDate(strStartDate));
            }
        }

            if(!strFreq.equals("")){
                if (deviceType.equalsIgnoreCase("Web"))
                {
                    click(getObject("xpath~//*[contains(@class,'boi-toggle-blue-light-mini')]/ul/li/span[text()='"+strFreq+"']"),"Frequency selected is '"+strFreq+"'");
                }else if (deviceType.equalsIgnoreCase("MobileWeb"))
                {
                    clickJS(getObject("xpath~//*[contains(@class,'boi-toggle-blue-light-mini')]/ul/li/span[text()='"+strFreq+"']"),"Frequency selected is '"+strFreq+"'");
                }
            }

        }else{
            report.updateTestLog("enterSOPayeedetails", "None of the Select frequency tabs are displayed on Set up Standing Order's Payee Details Page", Status_CRAFT.FAIL);
        }
        //Amount
        if (isElementDisplayedWithLog(getObject("SetupStandingOrder.lblAmount"),
                "Label 'Amount'", "Set up Standing Order's Payee Details", 5)) {
            String strCurrSymbol = dataTable.getData("General_Data","Currency_Symbol");
            String strAmountRange = "Between "+strCurrSymbol+"1.00 and "+strCurrSymbol+"3,000.00";
            //validate Amount Range
            if(getText(getObject("xpath~//label[text()='Amount ']/span[@class='boi_input_sm ']")).equals(strAmountRange)){
                report.updateTestLog("enterSOPayeedetails", "Amount Range label is correct '"+ strAmountRange +"' on Set up Standing Order's Payee Details Page", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("enterSOPayeedetails", "Amount Range label is not correct or not present on Set up Standing Order's Payee Details Page, UI label value '"+getText(getObject("xpath~//label[text()='Amount ']/span[@class='boi_input_sm ']"))+"'", Status_CRAFT.FAIL);
            }
            //Validate Currency Symbol
            if(getText(getObject("ManageStandingOrder.lblCurrencySymbol")).equals(strCurrSymbol)){
                report.updateTestLog("enterSOPayeedetails", "Currency Symbol label is correct '"+ strCurrSymbol +"' in Amount Field on Set up Standing Order's Payee Details Page", Status_CRAFT.PASS);
            }else{
                report.updateTestLog("enterSOPayeedetails", "Currency Symbol label is NOT correct '"+ strCurrSymbol +"' in Amount Field on Set up Standing Order's Payee Details Page, UI label value '"+getText(getObject("xpath~//label[text()='Amount ']/span[@class='boi_input_sm ']"))+"'", Status_CRAFT.FAIL);
            }

            String strAmount = dataTable.getData("General_Data","NewAmount");
            if(!strAmount.equals("")){
                sendKeys(getObject("ManageStandingOrder.txtNewAmount"),strAmount,"Amount entered is '"+strAmount+"'");
            }
        }

        String strAccountNumber = dataTable.getData("General_Data","AccountNumber");
        if (isElementDisplayedWithLog(getObject("StandingOrder.lblAccountNumber"),
                "label 'Account Number'", "Set up Standing Order's Payee Details", 5)) {
            sendKeys(getObject("StandingOrder.lblAccountNumber"), strAccountNumber, "Account Number is "+strAccountNumber);
        }

        String strSortCode = dataTable.getData("General_Data","NationalSortCode");
        if (isElementDisplayedWithLog(getObject("StandingOrder.lblSortCode"),
                "Label 'National sort code'", "Set up Standing Order's Payee Details", 5)) {
            sendKeys(getObject("StandingOrder.lblSortCode"), strSortCode, "National Sort Code is "+strSortCode);
        }

        if(!deviceType.equals("w.")){
            driver.hideKeyboard();
        }

        isElementDisplayedWithLog(getObject("ManageStandingOrder.btnGoback"),
                "Button 'Go back'","Set up Standing Order's Payee Details",2);

        isElementDisplayedWithLog(getObject("ManageStandingOrder.btnContinue"),
                "Button 'Continue'","Set up Standing Order's Payee Details",2);

        if(!dataTable.getData("General_Data","Operation").equals("")){
            String strOperationOnPutonHold = dataTable.getData("General_Data","Operation");
            switch (strOperationOnPutonHold.toUpperCase()){
                case "CONTINUE":
                    clickJS(getObject("ManageStandingOrder.btnContinue"),"Clicked on 'Continue', Set up Standing Order's Payee Details Page");
                    break;
                case "GO BACK":
                    clickJS(getObject("ManageStandingOrder.btnGoback"),"Clicked on 'Go back', Set up Standing Order's Payee Details Page");
                    isElementDisplayedWithLog(getObject("StandingOrder.btnSetUpNewStandingOrder"),
                            "Button 'Page navigated to Standing Order Landing Page upon clicking 'Go back'","Standing Orders",2);
                    break;
                default:
                    report.updateTestLog("enterSOPayeedetails", "Please provide the valid operation on enter Payee Details, Continue or Go Back",Status_CRAFT.FAIL);
            }
        }
    }

    //CFPUR-7660_View Inactive Standing Order alerts for all types of SO--Author-> Saurabh
    public void inactiveSO_StandingOrder() throws Exception {
        String accountNumber = dataTable.getData("General_Data", "AccountNumber");
        waitForPageLoaded();
        clickJS(getObject("StandingOrder.lstSelectAccount"), "Standing Order Dropdown");
        clickJS(getObject("xpath~//li[contains(text(),'" + accountNumber + "')]"), "Selected Inactive SO', View Standing Order");
    }
    public void viewInactiveSOAlertWeekly() throws Exception {
        waitForPageLoaded();
        Thread.sleep(3000);
        isElementDisplayedWithLog(getObject(deviceType() + "StandingOrder.lstClickWeekly"),
                "WEEKLY standing order","Standing order details",3);
            clickJS(getObject(deviceType() + "StandingOrder.lstClickWeekly"), "First weekly standing order to see details, To view your standing orders select an account ");
    }

    public void viewInactiveSOAlertMonthly() throws Exception {
        waitForPageLoaded();
        Thread.sleep(3000);
        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.lstClickMonthly"),
                "MONTHLY standing order","Standing order details",5);
        clickJS(getObject(deviceType()+"StandingOrder.lstClickMonthly"), "First Monthly standing order to see details, To view your standing orders select an account ");
    }

    public void viewInactiveSOAlertFortnightly() throws Exception {
        waitForPageLoaded();
        Thread.sleep(3000);
        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.lstClickFortnightly"),
                "FORTNIGHTLY standing order","Standing Order Details",5);
        clickJS(getObject(deviceType()+"StandingOrder.lstClickFortnightly"), "First Monthly standing order to see details, To view your standing orders select an account ");
    }

    public void viewInactiveSOAlertYearly() throws Exception {
        waitForPageLoaded();
        Thread.sleep(3000);
        isElementDisplayedWithLog(getObject(deviceType()+"StandingOrder.lstClickYearly"),
                "YEARLY standing order","Standing Order Details",5);
        clickJS(getObject(deviceType()+"StandingOrder.lstClickYearly"), "First Yearly standing order to see details, To view your standing orders select an account ");
    }

    public void viewInactiveSOAlertPost() throws Exception {
        waitForPageLoaded();
        Thread.sleep(3000);
        clickJS(getObject("StandingOrder.lstClickPost"), "First POST Method standing order to see details', To view your standing orders select an account ");
    }

    public void viewInactiveSOAlertText() throws Exception {
        isElementDisplayedWithLog(getObject("StandingOrder.lblType"),"INACTIVE SO","Standing Order Details",5);
        String strAlertMessage = dataTable.getData("General_Data", "verifyAlertInactive").trim();
        fetchTextToCompare(getObject("xpath~(//span[@class='boi-alert-content'])[2]"),strAlertMessage,"Alert Message inactive SO");
    }
    public void clickGoBackButton(){
        try {
            isElementDisplayedWithLog(getObject("standingOrder.btnGoBack"),
                    "Button ''Go back' ","Standing order",2);
            while (isElementDisplayed(getObject("launch.spinSpinner"), 2)) {
                waitForPageLoaded();
            }
            clickJS(getObject("standingOrder.btnGoBack"),"'Go back' button");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


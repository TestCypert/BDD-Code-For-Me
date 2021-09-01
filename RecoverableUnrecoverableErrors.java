package businesscomponents;

import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status_CRAFT;
import com.cognizant.framework.selenium.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.LinkedHashMap;
import java.util.List;

public class RecoverableUnrecoverableErrors extends WebDriverHelper {

    public RecoverableUnrecoverableErrors(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    public void UnrecoverableMainFrameError_Patter1() throws Exception {
        //validating page Error
        LinkedHashMap<String, String> mapPhnDeatils = new LinkedHashMap<>();
        mapPhnDeatils.put("Republic of Ireland", "0818 365 365 or 01 404 4000");
        mapPhnDeatils.put("Northern Ireland and Great Britain", "0345 7 365 555");
        mapPhnDeatils.put("Other locations", "+353 1 404 4000");
        String[] arrJurisdictions = {"Republic of Ireland", "Northern Ireland and Great Britain", "Other locations"};

        isElementDisplayedWithLog(getObject(deviceType() + "Err.PageHeader"), "Error Page Header", "'Error'", 5);
        isElementDisplayedWithLog(getObject("Err.image"), "Error Page", "'Image'", 2);
        isElementDisplayedWithLog(getObject("Err.InfoParaCannotComplete"), "Info text ", "'We cannot complete your request just now. Sorry about that.'", 2);
        isElementDisplayedWithLog(getObject("Err.infoParaTryAgain"), "Info text ", "'You could try again later, or you can access your account by calling the appropriate number below.'", 2);
        isElementDisplayedWithLog(getObject("Err.sectionHeader365PhnBanking"), "Section Header ", "'365 phone banking'", 2);

        //code for table content validation
        List<WebElement> tblRows = driver.findElements(By.xpath("//*[@role='presentation']/div[contains(@class,'boi-table--tr boi-plain-table')]"));
        if (tblRows.size() != 0) {
            for (int irow = 0; irow < tblRows.size(); irow++) {
                String strUiText = tblRows.get(irow).getText();
                if (strUiText.contains(arrJurisdictions[irow]) && strUiText.contains(mapPhnDeatils.get(arrJurisdictions[irow]))) {
                    report.updateTestLog("UnrecoverableMainFrameError_Patter1", "'Phone Banking Number Details' table are correctly displayed as '" + arrJurisdictions[irow] + "'   '" + mapPhnDeatils.get(arrJurisdictions[irow]) + "'", Status_CRAFT.PASS);
                    if (isMobile) {
                        if (isElementDisplayed(getObject("xpath~//*[@role='presentation']/div[contains(@class,'boi-table--tr boi-plain-table')][" + (irow + 1) + "]/descendant::a[@role='button']"), 3)) {
                            report.updateTestLog("UnrecoverableMainFrameError_Patter1", "'Phone Banking Number Details' Phone Icon is correctly displayed for '" + arrJurisdictions[irow] + "' on mobile", Status_CRAFT.PASS);
                        } else {
                            report.updateTestLog("UnrecoverableMainFrameError_Patter1", "'Phone Banking Number Details' Phone Icon is NOT displayed for '" + arrJurisdictions[irow] + "' on mobile", Status_CRAFT.FAIL);
                        }
                    }
                } else {
                    report.updateTestLog("UnrecoverableMainFrameError_Patter1", "'Phone Banking Number Details' table are NOT displayed as '" + arrJurisdictions[irow] + "'   '" + mapPhnDeatils.get(arrJurisdictions[irow]) + "'", Status_CRAFT.FAIL);
                }
            }
        } else {
            report.updateTestLog("UnrecoverableMainFrameError_Patter1", "'Phone Banking Number Details' table is not present", Status_CRAFT.FAIL);
        }
        //*[@role='presentation']/div[contains(@class,'boi-table--tr boi-plain-table')]
//*[@role='presentation']/div[contains(@class,'boi-table--tr boi-plain-table')]/descendant::a[@role='button']

        isElementDisplayedWithLog(getObject("Err.sectionHeaderPhnBankingOpenngHours"), "Section Header", "'365 phone banking opening hours''", 2);
        isElementDisplayedWithLog(getObject("Err.lblROI"), "Label", "'Republic of Ireland'", 2);
        isElementDisplayedWithLog(getObject("Err.infoTxtROIPhnBankingHours"), "text ROI phone banking opening hours", "'8am to midnight, Monday to Friday and 9am to 6pm on Saturdays, Sundays, bank and public holidays.'", 2);
        isElementDisplayedWithLog(getObject("Err.lblNI&GB"), "Label", "'Republic of Ireland'", 2);
        isElementDisplayedWithLog(getObject("Err.infoTxtNI&GBPhnBankingHours"), "text NI and GB phone banking opening hours", "'8am to 8pm, Monday to Friday, 9am to 5pm on Saturdays, 10am to 5pm on bank and public holidays. Closed on Sundays.'", 2);
    }

    public void validateMainFrameErrors() throws Exception {
        String expectedErr = dataTable.getData("General_Data", "ErrorText");
        String errXpath = "xpath~//*[@class='fa fa-exclamation-circle']//following-sibling::*[@class='boi_input']";
        if (isElementDisplayed(getObject(errXpath), 5)) {
            String actualErr = getText(getObject(errXpath));
            if (expectedErr.trim().equals(actualErr.trim())) {
                report.updateTestLog("validateMainFrameErrors", "Mainframe error message displayed is as expected. Expected::'" + expectedErr + "', Actual::'" + actualErr + "'", Status_CRAFT.PASS);
            } else {
                report.updateTestLog("validateMainFrameErrors", "Mainframe error message displayed is not as expected. Expected::'" + expectedErr + "', Actual::'" + actualErr + "'", Status_CRAFT.FAIL);
            }
        } else {
            report.updateTestLog("validateMainFrameErrors", "Mainframe error message is NOT displayed on the Tpo of the screen", Status_CRAFT.FAIL);
        }
    }

    public void RecoverableErrorServiceDeskFunctionsPatter3() throws Exception{

        isElementDisplayedWithLog(getObject("Err.sectionHeaderServiceUnavailable"), "Section header 'Service Unavailable","'Error'",5);
        isElementDisplayedWithLog(getObject("Err.imgExclamation"), "Error Page","'Exclamation Image'",2);
        isElementDisplayedWithLog(getObject("Err.infoParaServiceIsunavailable"), "Info text ","'Unfortunately, this service is unavailable at this time. Please try again later'",2);

        isElementDisplayed(getObject("services.btnBackToHome"),2);
        clickJS(getObject("services.btnBackToHome"),"Button 'Back to home'");
        waitForPageLoaded();
        waitForJQueryLoad();
        isElementDisplayedWithLog(getObject("launch.edtUserName"),"Page Navigated to Login Page","Page Navigated to Login Page",5);

    }

    public void RecoverableErrorNonTransactionalFunctionsPattern4() throws Exception{
        if(!dataTable.getData("General_Data","PageHeader").equals("")){
            isElementDisplayedWithLog(getObject("xpath~h3[text()='"+dataTable.getData("General_Data","PageHeader")+"']"), "Section header 'Service Unavailable","'Error'",5);
        }
        isElementDisplayedWithLog(getObject("Err.sectionHeaderServiceUnavailable"), "Section header 'Service","'Error'",5);
        isElementDisplayedWithLog(getObject("Err.imgExclamation"), "Error Page","'Exclamation Image'",2);
        isElementDisplayedWithLog(getObject("Err.infoParaServiceIsunavailable"), "Info text ","'Unfortunately, this service is unavailable at this time. Please try again later'",2);
    }

}

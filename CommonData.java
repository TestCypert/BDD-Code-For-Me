package businesscomponents;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.Set;

/**
 * Class for storing common data
 *
 * @author c965303
 */
public class CommonData {

    public Set<String> setWindowHandles = null;
    public String firstChildWindowT24 = "";
    public String secondChildWindowT24 = "";
    public Boolean iterationFlag = false;
    public String  defaultAccountName = "";
    public ArrayList<String> accountList = new ArrayList<String>();
    public String strExisting4Digit = "";
    public String strExistingEmailId = "";
    public String strPayFromAccount = "";
    public String strPayToAccount = "";
    public String strAmount = "";
    public String strProssTran = "";
    public String strTransferDate = "";
    public String strSetDate = "";
    public Boolean confirmationFlag = true;
    public String date = "";
    public int intPayeeCount;
    public String strDate ="";
    public Boolean transferFlag = false;
    public Boolean exitMobileAppFlag = false;
    public String strTCName ="";
    public String strFetchedAmount;
    public String strNewEmailID="";
    public String duplicatePolicyNumber="";
    public StopWatch watch = new StopWatch();


}

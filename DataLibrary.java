package com.boi.grp.utilities;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.openqa.selenium.WebElement;
import com.boi.grp.pageobjects.BasePageForAllPlatform;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import nl.garvelink.iban.CountryCodes;
import nl.garvelink.iban.IBAN;
import nl.garvelink.iban.IBANFields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import nl.garvelink.iban.CountryCodes;
import nl.garvelink.iban.IBAN;
import nl.garvelink.iban.IBANFields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import nl.garvelink.iban.CountryCodes;
import nl.garvelink.iban.IBAN;
import nl.garvelink.iban.IBANFields;

public class DataLibrary {

	public DataLibrary(){
		
	}
	
	public String getinValidPin(){		
		String InvalidPinFlag="000000";		
		return InvalidPinFlag;
	}
	
	/**
	 * Generate Random string
	 * @return
	 * Function written by C114323
	 */
	public String generateRandomString(){
		Random rand = new Random();
		String rand_str="Test";
		int rand_int = rand.nextInt(999);	
		return (rand_str + rand_int);
	}
	
	/**
	 * Generate Random Email
	 * @return
	 * Function written by C112253
	 */
	
	public String generateRandomEmail(){
        String alphabet =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        int length =  7;
        Random rm= new Random();
        for (int i= 0; i<= length; i++){
           int index = rm.nextInt(alphabet.length());
           char randomchar = alphabet.charAt(index);
           sb.append(randomchar);
        }
          String name = sb.toString();
          Random random = new Random();
          int randominteger = random.nextInt(1000);
          String email = name + randominteger +"@gmail.com";
          return email;
    }

	
	/**
	 * Generate Random Mobile Number
	 * @return
	 * Function written by C113329
	 */
	
	public String generateRandomMobileNumber() {
        double random = Math.random();
        Double randomten = random*1000000000.0;
        return "0"+Math.round(randomten);
    }
	
	/**
	 * Generate Random Date
	 * @param 
	 * Created By C113329
	 * @return
	 */
	
	public String generateRabdomDate() {
	    Long max =0L;
	    Long min =100000000000L;
	    SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
	    Date dt = null;
	    for (int i = 0; i <= 10; i++) {
	        Random r = new Random();
	        Long randomLong=(r.nextLong() % (max - min)) + min;
	        dt =new Date(randomLong);        
	    }
		return spf.format(dt);
	}


	/**
	 * Generate Random numbers
	 * @param DigitsOfTheRandomNumber
	 * @return
	 */
	public  String generateRandomNumber(int DigitsOfTheRandomNumber){
		String returnValue="";
		try{
			while(returnValue.length() != DigitsOfTheRandomNumber){
				returnValue =String.valueOf((long) (Math.random() * 90000000000000000l)).substring(0, DigitsOfTheRandomNumber);;
			}
		} catch(Throwable t){
			returnValue="Error";
		}
		return returnValue;
	}
	
	// Added by C114688
	//to get invalid email address 
	public String getInvalidEMailId() {
		String GetInvalidEMailId = "$jhon@gmail.com";
		return GetInvalidEMailId;

	}
	
		//to get valid pin flag
	public String validPinFlag() {
		String ValidPinFlag = "VALIDPIN";
		return ValidPinFlag;

	}
	
	//For getting random Irish county
	public String getRandomIrishCounty(String country)
	{
		String county="";
		String roiCounty[]={"Cavan","Carlow","Donegal","Galway","Kerry","Laois","Leitrim","Limerick","Longford","Louth","Mayo","Meath","Monaghan",
				"Offaly","Roscommon","Sligo","Tipperary","Waterford","Westmeath","Wexford","Wicklow"};
		if (country.equalsIgnoreCase("Ireland"));
		{
			Random rand = new Random(); 
			int i = rand.nextInt(roiCounty.length);
			county = roiCounty[i];
		}
		return county;
	}
	//for getting Ireland contry
	public String getIrishCountry()
	{
		String country = "Ireland";
		return country;
	}
//for getting ABAcode for intpayees - C113331
		public String getABAcode()
		{
			String strABAcode = "031207607";
			return strABAcode;
		}
		//for getting AccNo for intpayees - C113331
		public String getInternationalAccNo(String strcountry)
		{
			String strAccno = null;
			try{
			switch (strcountry.toUpperCase().trim()) {
			// Country code
			case "AUSTRALIA":
			strAccno = "57008068833013";
			break;
			case "UNITED STATES":
				 strAccno = "802414514";
				break;
			case "CANADA":
				 strAccno = "111111111";
				break;
		    }
			}
			catch(Throwable t){
				strAccno="Error";
			}
			return strAccno;
		}
	//for getting AddressLine 1
	public String getAddressLine1()
	{
		String addressLine1 = "Address1";
		return addressLine1;
	}
	//for getting AddressLine 2
		public String getAddressLine2()
		{
			String addressLine2 = "Address2";
			return addressLine2;
		}

		/*---------------------------------Start <GetValidIBAN>----------------------------------------
		Function Name: GetValidIBAN
		Argument : User type - GB,ROI,CROSS, IBAN as valid IBAN //TODO for other customer type and  CROSS customer
		Purpose: Get valid IBAN based on Country passed
		Author Name: CAF Automation 
		 Create Date: 13-06-2021
		Modified Date| Modified By  |Modified purpose 
		  23/07/2021      C113331     Code update 
		 -----------------------------------End <GetValidIBAN>--------------------------------------- */
		public String getIBAN_BIC(String strCountry) {
			
			String IBAN_BIC = null;
			switch (strCountry.toUpperCase().trim()) {
			// Country code
			case "ROI":	
			case "IRELAND":	
				IBAN_BIC = "IE79BOFI90368044742663_BOFIIE2DXXX";
				break;
			case "GB":
			case "CR1":
			case "CR2":
			case "UK":	
			case "UNITED KINGDOM":	
				IBAN_BIC = "GB79BOFI30102711000372_BOFIGB2B";
				break;
			case "ESTONIA":
				IBAN_BIC = "EE021010010121762016_EEUHEE2X";
				break;
			case "NORWAY":
				IBAN_BIC = "NO1216450998064_DNBANOKK";
				break;
			case "POLAND":
				IBAN_BIC = "PL61109010140000071219812874_POLUPLPR";
				break;
			case "ANDORA":
				IBAN_BIC = "AD7900010000000110100136_BACAADADXXC";
				break;
			case "LITHUANIA":
				IBAN_BIC = "LT667044000053611201_CBVILT2X";
				break;
			case "NETHERLAND":
				IBAN_BIC = "NL35RABO0157314413_CBVILT2X";
				break;
			case "GIBRALTA":
			case "FRANCE":
				IBAN_BIC = "GI75NWBK000000007099453_RBOSGIGIXXX";
				break;
			}
			return IBAN_BIC;

		}
	
				
		/*---------------------------------Start <GetAccNo>----------------------------------------
		 Function Name: GetAccNo
		 Argument : User type - ROI,CROSS or country name, processtype - Validpin/invalidpin, Contentcheck-YES/NO
		 Purpose:  Get Account extracted based on IBAN and customer type/Country passed with the help of IBAN class to identify branchid, 
		 from the branch id ,sortcode can be derived using substring followed by sortcode account number can be derived using another substring method. 
		 Author Name: CAF Automation 
		 Create Date: 13-06-2021
		 Modified Date| Modified By  |Modified purpose 
		 23/07/2021      C113331     Code update 
		 -----------------------------------End <GetAccNo>--------------------------------------- */
		public String getAccNo(String IBAN, String Country) {
//			String[] arrUsData = Usertype.split("_");
//			String usertype1 = arrUsData[0];
			String ValidIBAN = IBAN;
			String countrycode = null;
			switch (Country.toUpperCase().trim()) {
			// Country code
			case "ROI":
			case "IRELAND":
				countrycode = "IE";
				break;
			case "NI":
			case "GB":
			case "CR1":
			case "UK":
				countrycode = "GB";
				break;
			case "CR2":
				countrycode = "UK";
				break;
			}

			IBAN iban = nl.garvelink.iban.IBAN.valueOf(IBAN);
			int length = CountryCodes.getLengthForCountryCode(countrycode);
			Optional<String> bankId = IBANFields.getBankIdentifier(iban);
			Optional<String> branchId = IBANFields.getBranchIdentifier(iban);
			String sortCode = branchId.toString().substring(9, 15);
			String[] code = ValidIBAN.split(sortCode);
			String Accno = code[1].substring(0, 8);
			return Accno;
		}

		/**
		 * Get Sort code extracted based on IBAN and customer type passed with the
		 * help of IBAN class to identify branchid, from the branch id ,sortcode can
		 * be derived using substring based on IBAN and customer type passed
		 * 
		 * @param GB,ROI,CROSS
		 * @return string
		 */
		/*---------------------------------Start <GetNSC>----------------------------------------
		 Function Name: GetNSC
		 Argument : User type - ROI,CROSS , processtype - Validpin/invalidpin, Contentcheck-YES/NO
		 Purpose: Get Sort code extracted based on IBAN and customer type passed with the help of IBAN class to identify branchid, 
		 * from the branch id ,sortcode can be derived using substring based on IBAN and customer type passed 
		 Author Name: CAF Automation 
		 Create Date: 13-06-2021
		 Modified Date| Modified By  |Modified purpose 
		  23/07/2021      C113331     Code update 
		 -----------------------------------End <GetNSC>--------------------------------------- */
		public String getNSC(String IBAN, String Country) {
//			String[] arrUsData = Usertype.split("_");
//			String usertype1 = arrUsData[0];
			String ValidIBAN = IBAN;
			String countrycode = null;
			switch (Country.toUpperCase().trim()) {
			// Country code
			case "ROI":
			case "IRELAND":
				countrycode = "IE";
				break;
			case "NI":
			case "GB":
			case "CR1":
				countrycode = "GB";
				break;
			case "UNITED KINGDOM":
				countrycode = "UK";
				break;
			case "CR2":
				countrycode = "UK";
				break;
			}

			IBAN iban = nl.garvelink.iban.IBAN.valueOf(IBAN);
			int length = CountryCodes.getLengthForCountryCode(countrycode);
			Optional<String> bankId = IBANFields.getBankIdentifier(iban);
			Optional<String> branchId = IBANFields.getBranchIdentifier(iban);
			String sortCode = branchId.toString().substring(9, 15);
			return sortCode;
		}
				public String getAddressCountry(String userType)
	              {
	                     String[] arrUsData = userType.split("_");
	                     String usertype1=arrUsData[0];
	                     String country="";
	                     switch (usertype1.toUpperCase()) {

	                     case "ROI":
	                           country = "Ireland";
	                           break;
	                     case "GB":
	                           country = "England";
	                           break;
	                     case "CR1":
	                           country = "";
	                           break;
	                     case "NI":
	                           country = "Ireland";
	                           break;
	               case "CR2":
	                     country = "";
	                           break;
	                     }
	                     return country;
	              }

				/**
			       * Generate Random String 
			        * @return
			       * Function written by C112253
			       */
			       
			       public String generateRandomString(int num){
			        String alphabet =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			        StringBuilder sb = new StringBuilder();
			        Random rm= new Random();
			        for (int i= 0; i<= num; i++){
			           int index = rm.nextInt(alphabet.length());
			           char randomchar = alphabet.charAt(index);
			           sb.append(randomchar);
			        }
			          String name = sb.toString();
			          return name;
			    }
			       /**
			        * Getter Method to get transaction reference number
			        * @return
			        * Function written by C113329
			        */
			        
			        public String refno;
			        public String getRefno() {
			               return refno;
			        }
			        
			        /**
			        * Setter Method to set transaction reference number
			        * @return
			        * Function written by C113329
			        */
			        
			        public void setRefno(String refno) {
			               this.refno = refno;
			        }
			        
			      //for generating random irish num
					public String generateRandomIrishMobileNumber() {
				        double random = Math.random();
				        Double randomten = random*100000000.0;
				        return "0"+"8"+Math.round(randomten);
				    } 
			        
			    
			        
}

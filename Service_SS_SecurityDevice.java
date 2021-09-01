/*Function to Manage Devices options 
 * written by C115677*/
package com.boi.grp.pageobjects.Services;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.DataLibrary;
import com.boi.grp.utilities.UIResusableLibrary;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
public class Service_SS_SecurityDevice extends BasePageForAllPlatform {
	public UIResusableLibrary cafFunctional;
	public DataLibrary dataLibrary;
	By ServiceTab = getObject("sd.tabServices");
	By MngDevBox = getObject("sd.MngDevBox");
	By DevName = getObject("sd.DevName");
	By DevNicknameBox = getObject("sd.DevNickNameBox");
	By NewnicknameFld = getObject("sd.NewNicknameFld");
	By ContBtn = getObject("sd.ContBtn");
	By verNicknameChgMsgBox = getObject("sd.verMsgBox");
	By BlockDevButton = getObject("sd.blockDevBut");
	By BlockDevConfBut = getObject("sd.blockDevConfBut");
	By PinConfBut = getObject("sd.authPinConfBut");
	By verBlockDevSuccessMsgBox=getObject("sd.verBlockSuccessConfBox");
	By UnBlockDevButton = getObject("sd.unblockDevBut");
	By UnBlockDevConfBut = getObject("sd.unblockDevConfBut");
	By verUnBlockDevSuccessMsgBox=getObject("sd.verUnBlockSuccessConfBox");
	By RemoveDevBut = getObject("sd.RemoveDevBut");
	By RemoveDevConfBut = getObject("sd.RemoveDevConfBut");
	String nicknameUpdateSuccessMsg = getMessageText("devNicknameUpdateSuccessMsg", "SERVICES");
	String devBlockSuccessMsgSubString = getMessageText("devBlockSuccessMsg","SERVICES");
	String curNickname,newNickname;

	public Service_SS_SecurityDevice(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cafFunctional = new UIResusableLibrary(driver);
	}
	
	//Control Method for redirecting flow as per unit input
	public void MngSerDevice(String serSdOpt, String userTypeOpt){
		try{
			boolean bFlagSerMSOpt = false;
			switch (serSdOpt.toUpperCase()) {
			case "CHANGE_NICKNAME":							
				if(isElementDisplayed(MngDevBox, 10, "MngDevBox")){
					UpdDevNickname_P();
					bFlagSerMSOpt = true;
				}
				else {
					bFlagSerMSOpt=false;
				}
				break;
			case "BLOCK_DEVICE":
				if(isElementDisplayed(MngDevBox, 10, "MngDevBox")){
					BlockSerDev_P(userTypeOpt);
					bFlagSerMSOpt = true;
				}
				else {
					bFlagSerMSOpt=false;
				}
				break;
			case "UNBLOCK_DEVICE":
				if(isElementDisplayed(MngDevBox, 10, "MngDevBox")){
					UnBlockSerDev_P(userTypeOpt);
					bFlagSerMSOpt = true;
				}
				else {
					bFlagSerMSOpt=false;
				}
				break;
			case "REMOVE_DEVICE":
				if(isElementDisplayed(MngDevBox, 10, "MngDevBox")){
					RemoveDev_P(userTypeOpt);
					bFlagSerMSOpt = true;
				}
				else {
					bFlagSerMSOpt=false;
				}
				break;
			}
			if (bFlagSerMSOpt == true) {
				appendScreenshotToCucumberReport();
				logMessage(serSdOpt + " Device Renamed Successfully ");
				injectMessageToCucumberReport(serSdOpt + " Device Renamed Successfully");
				Allure.step("Device Renamed Successfully", Status.PASSED);
			} else {
				logMessage(serSdOpt + " Device Rename Failed");
				injectMessageToCucumberReport(serSdOpt + " Device Rename Failed ");
				Allure.step(serSdOpt + " Device RenameFailed ", Status.FAILED);
				appendScreenshotToCucumberReport();
			}
		}
		catch (Exception e){							//check for status of throwable patch for assert fail scenario.
			logError("Error occured inside MngSerDevice" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in managing device" +e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in managing service device",Status.FAILED);
		}
	}

	//control method for Changing Nickname
	private void UpdDevNickname_P() throws Exception{
		try{
			newNickname = generateRandomString();
			clickJS(MngDevBox,"Services Tab - Manage Devices Button");// 
			clickJS(DevName,"Choose Device from device list");//
			clickJS(DevNicknameBox, "Device options - device name");//
			UpdateNickname_P(newNickname);					//Update nickname with randomly generated string
			isElementDisplayed(verNicknameChgMsgBox);
			Allure.step("Success in updating device nickname",Status.PASSED);
		}
		catch (Exception e){
			logError("Error occured inside UpdSerDevNickname" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in updating device nickname" +e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in updating device nickname",Status.FAILED);
		}
		
	}
	
	//Update new nickname
	private void UpdateNickname_P(String newNickname){
		try{
		//clickJS(DevNicknameBox, "Device name option - Existing Nickname");
		setValue(NewnicknameFld,newNickname);
		clickJS(ContBtn, "Nickname field - updated nick name - continue button");//
		} 
		catch (Exception e) {
			logMessage("Error inside UpdateNickname()");
		}	
	}
	
	
	//Control method for BLOCK Device
	private void BlockSerDev_P(String userTypeOpt) throws Exception{
		try{
			clickJS(MngDevBox,"Services Tab - Manage Devices Button");//
			clickJS(DevName,"Choose Device from device list");//
			clickJS(BlockDevButton, "Device options menu - block device button");//
			clickJS(BlockDevConfBut, "Device options menu - block device button - confirm block option");//
			//cafFunctional.Enter3of6DigitPIN(dataLibrary.validPinFlag(), cafFunctional.GetvalidPIN(userTypeOpt));
			clickJS(PinConfBut, "Block Device - enter pin - confirm pin button");
			waitForElementToBeDisplayed(verBlockDevSuccessMsgBox);
			isElementDisplayed(verBlockDevSuccessMsgBox);
		}
		
		catch (Exception e) {
			logError("Error occured inside BlockSerDev" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in blocking device" +e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in blocking device",Status.FAILED);
		}
	}
	
	private void UnBlockSerDev_P(String userTypeOpt) throws Exception{
		try{
			clickJS(MngDevBox,"Services Tab - Manage Devices Button");//
			clickJS(DevName,"Choose Device from device list");//
			clickJS(UnBlockDevButton, "Device - Unblock device button");//
			clickJS(UnBlockDevConfBut, "Unblock device option - confirm unblock option");//
			//cafFunctional.Enter3of6DigitPIN(dataLibrary.validPinFlag(), cafFunctional.GetvalidPIN(userTypeOpt));
			clickJS(PinConfBut, "pin enter page - confirm entered pin");//
			waitForElementToBeDisplayed(verUnBlockDevSuccessMsgBox);
			isElementDisplayed(verUnBlockDevSuccessMsgBox);
		}
		catch (Exception e) {
			logError("Error occured inside UnBlockSerDev" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in unblocking device" +e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in unblocking device",Status.FAILED);
		}
	}
	
	private void RemoveDev_P(String userTypeOpt) throws Exception{
		try{
			clickJS(MngDevBox,"Services Tab - Manage Devices Button");//
			clickJS(DevName,"Choose Device from device list");//
			clickJS(RemoveDevBut, "Device options - remove device button");//
			clickJS(RemoveDevConfBut, "remove device button - confirm remove device button");//
			//cafFunctional.Enter3of6DigitPIN(dataLibrary.validPinFlag(), cafFunctional.GetvalidPIN(userTypeOpt));
			//waitforpagetoload
			//clickJS(PinConfBut , "pin enter page - confirm entered pin");
			
		}
		catch (Exception e) {
			logError("Error occured inside UnBlockSerDev" +e.getMessage());
			injectWarningMessageToCucumberReport("Failure in unblocking device" +e.getMessage());
			appendScreenshotToCucumberReport();
			Allure.step("Failure in unblocking device",Status.FAILED);
		}
	}
	
}
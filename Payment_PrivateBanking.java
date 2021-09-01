package com.boi.grp.pageobjects.Payments;

import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.boi.grp.pageobjects.BasePageForAllPlatform;
import com.boi.grp.utilities.UIResusableLibrary;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class Payment_PrivateBanking extends BasePageForAllPlatform{
                public UIResusableLibrary cafFunctional;
                
                By linkPBP  = getObject("pd.PBPlink");
                By ReqPaymentBtn = getObject("PrivateBancking.btnReqPayment");
                By GoBackBtn = getObject("PrivateBancking.btnGoBack");
                By bdyContent1 = getObject("PrivateBancking.bdyContent1");
                By bdyContent2 = getObject("PrivateBancking.bdyContent2");
                By bdyContent3 = getObject("PrivateBancking.bdyContent3");
                By bdyContent4 = getObject("PrivateBancking.bdyContent4");
                By bdyContent5 = getObject("PrivateBancking.bdyContent5");
                By bdyContent6 = getObject("PrivateBancking.bdyContent6");
                By bdyContent7 = getObject("PrivateBancking.bdyContent7");
                By bdyContent8 = getObject("PrivateBancking.bdyContent8");
                By bdyContent9 = getObject("PrivateBancking.bdyContent9");
                By bdyContent10 = getObject("PrivateBancking.bdyContent10");
                //By bdyContent11 = getObject("PrivateBancking.bdyContent11");
                
                //By PrivateBankingTopMenu = getObject("PrivateBanking.TopMenu");
                //By PaymentTopMenu = getObject("Payment.TopMenu");
                
                public Payment_PrivateBanking(WebDriver driver) {
                                super(driver);
                                PageFactory.initElements(driver, this);
                                cafFunctional = new UIResusableLibrary(driver);
                                
                }
                
                /*---------------------------------Start <PD_PrivateBanking>----------------------------------------
                Function Name: PD_PrivateBanking
                Argument :
                Purpose: Navigate to Private Banking page ,validate the screen
                Author Name: CAF Automation 
                 Create Date: 27-06-2021
                Modified Date| Modified By  |Modified purpose 
                  24/06/2021      C113329     Code update
                -----------------------------------End <PD_PrivateBanking>--------------------------------------- */
                public void PD_PrivateBanking(){
                                logMessage("Inside Private Banking");
                                try{
                                                
                                                String PBPageTitle = driver.getTitle();
                                                System.out.println("**********" +PBPageTitle);
                                                Assert.assertEquals("Private Banking Payment - Bank of Ireland", PBPageTitle);
                                                
                                                //Verify Body Content Start
                                                String aContent1 = getText(bdyContent1);
                                                Assert.assertEquals("If your payment cannot be made on 365 online, you can make a separate payment request through the request payment option below.", aContent1);
                                                
                                                String aContent2 = getText(bdyContent2);
                                                Assert.assertEquals("When, for example:", aContent2);
                                                
                                                String aContent3 = getText(bdyContent3);
                                                Assert.assertEquals("The amount exceeds the 365 total working day limit.", aContent3);
                                                
                                                String aContent4 = getText(bdyContent4);
                                                Assert.assertEquals("You want to send money to countries or in currencies that are not included in 365 online.", aContent4);
                                                
                                                String aContent5 = getText(bdyContent5);
                                                Assert.assertEquals("You want to send money from your currency account in global markets.", aContent5);
                                                
                                                String aContent6 = getText(bdyContent6);
                                                Assert.assertEquals("Please note:", aContent6);
                                                
                                                String aContent7 = getText(bdyContent7);
                                                Assert.assertEquals("The request form will open in a separate browser outside of 365 online.", aContent7);
                                                
                                                String aContent8 = getText(bdyContent8);
                                                Assert.assertEquals("We will call you to confirm the request before we can process it.", aContent8);
                                                
                                                String aContent9 = getText(bdyContent9);
                                                Assert.assertEquals("We can only confirm and process payments during normal working hours.", aContent9);
                                                
                                                String aContent10 = getText(bdyContent10);
                                                Assert.assertEquals("We can only confirm and process payments during normal working hours.", aContent10);
                                                
                                                //Verify Body Content End
                                                
                                                
                                                
                                                //Verify Go Back Button
                                                if(isElementDisplayed(GoBackBtn, 20, "Payment - Private Banking - Go Back Button")){
                                                                                clickJS(GoBackBtn, "Payment - Private Banking - Go Back Button");
                                                                                String PaymentPageTitle = driver.getTitle();
                                                                                System.out.println("**********" +PaymentPageTitle);
                                                                                Assert.assertEquals("Payments - Bank of Ireland", PaymentPageTitle);
                                                                                clickJS(linkPBP);
                                                                }
                                                
                                                //Verify Request Payment Button
                                                if(isElementDisplayed(ReqPaymentBtn, 10, "Payment - Private Banking - Request Payment Button")){
                                                                clickJS(ReqPaymentBtn, "Payment - Private Banking - Request Payment Button");
                                                                
                                                                Set <String> allWindowHandles=driver.getWindowHandles(); 
                                        String parentWindowHandle=driver.getWindowHandle(); 
                                        Iterator<String> iteratorForWindow = allWindowHandles.iterator();
                                        while(iteratorForWindow.hasNext()){
                                               String childWindowHandle = iteratorForWindow.next();
                                               if(!parentWindowHandle.equals(childWindowHandle)) {
                                                     driver.switchTo().window(childWindowHandle);
                                                     driver.close();
                                                     driver.switchTo().window(parentWindowHandle);
                                               }
                                        }
                                                }
                                                else{
                                                                logMessage("Request Payment button not display successfully in PD_PrivateBanking function ");
                                                                injectErrorToCucumberReport("Request Payment button not display successfully");
                                                                Allure.step("Request Payment button not display successfully", Status.PASSED);
                                                }
                                                driver.navigate().refresh();
                                                logMessage("Validation for Private Banking Page - Page Title, Request Payment, Go Back Button successfully in PD_PrivateBanking function ");
                                                injectMessageToCucumberReport("Validation for Private Banking Page - Page Title, Request Payment, Go Back Button successfully");
                                                Allure.step("Validation for Private Banking Page - Page Title, Request Payment, Go Back Button successfully", Status.PASSED);
                                
                                }
                                catch(Exception e){
                                                logError("Error Occured inside PD_PrivateBanking " + e.getMessage());
                                                injectErrorToCucumberReport("Failure in Private Banking option selection " + e.getMessage());
                                                Allure.step("Failure in Private Banking option selection ", Status.FAILED);
                                                appendScreenshotToCucumberReport();
                                }
                }
}


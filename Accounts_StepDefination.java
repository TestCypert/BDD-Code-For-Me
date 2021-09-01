package com.boi.grp.stepDefinations;

import java.util.Map;

import com.boi.grp.pageobjects.login.*;
import org.openqa.selenium.WebDriver;
import com.boi.grp.hooks.Hooks;
import com.boi.grp.pageobjects.GoogleSearchPage;
import com.boi.grp.pageobjects.Accounts.AccountChequeSearch;
import com.boi.grp.pageobjects.Accounts.AccountNickname;
import com.boi.grp.pageobjects.Accounts.AccountOptions;
import com.boi.grp.pageobjects.Accounts.AccountStatements;
import com.boi.grp.pageobjects.Accounts.AccountTransactions;
import com.boi.grp.pageobjects.Cards.ManageCardPage;
import com.boi.grp.pageobjects.Cards.RealTimeBalance;
import com.boi.grp.pageobjects.Cards.ReplaceDamagedCard;
import com.boi.grp.pageobjects.Cards.ViewCardDetails;
import com.boi.grp.pageobjects.Cards.ViewPIN;
import com.boi.grp.pageobjects.Payments.Payment_AddBillPage;
import com.boi.grp.pageobjects.Payments.Payment_ManagePayeePage;
import com.boi.grp.pageobjects.Services.Services;
import com.boi.grp.pageobjects.login.Footers;
import com.boi.grp.pageobjects.login.LoginPage;
import com.boi.grp.pageobjects.login.Logout;
import com.boi.grp.pageobjects.login.More;
import com.boi.grp.pageobjects.login.Profile;
import com.boi.grp.utilities.UIResusableLibrary;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.cucumber.core.logging.Logger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class Accounts_StepDefination {

	public WebDriver driver;
	public GoogleSearchPage googleSearchPage;
	public Hooks hooks;
	Map<String, String> scenarioData;
	Map<String, String> testdata;
	public LoginPage loginPg;
	public AccountOptions accOpt,accOpt1;
	public AccountNickname accNicknm;
	public AccountStatements accStats;
	public AccountChequeSearch accChqSearch;
	public AccountTransactions accTrans,accTrans1;
	public Payment_AddBillPage addBill;
	public Payment_ManagePayeePage managepayeePg1;
	public UIResusableLibrary cafFunctionalComponent;
	public Logout logout;

	public Accounts_StepDefination(Hooks hooks) {
		this.hooks = hooks;
	}

	// ******************************** LAUNCH APP AND LOGIN
	// ********************************
	@And("^User launch the Application and login with (.+) user$")

	public void fn_LaunchApplicationURL(String userType) throws Throwable {

		driver = hooks.GetDriver();
		/*loginPg = new LoginPage(driver);
		loginPg.launchApplication(userType);// (System.getProperty("URL"));*/
		LoginPageNew loginPageNew=new LoginPageNew(driver);
		loginPageNew.launchUXPApplication(userType);
		Allure.step("Application launch and user login successfully" + userType, Status.PASSED);
	}

	// *********************************** SELECT ACCOUNT
	// ***********************************
	@When("^User clicks on the account tab and select (.+) account$")

	public void fn_getAccount(String accountType) throws InterruptedException {

		accOpt = new AccountOptions(driver);
		accOpt.ClickOnAccountsTab();
		accOpt.ACC_DiffOptions(accountType);
	}

	// ****************************** UPDATE ACCOUNT NICKNAME
	// *******************************
	@Then("^User update the account nickname and verify success message$")
	public void fn_updateNickname() {
		accNicknm = new AccountNickname(driver);
		accNicknm.ACC_NickName_P();
	}

	// **************************** VIEW ACCOUNT STATEMENT PDF
	// *****************************
	@Then("^User view the (.+) statement PDF$")
	public void fn_viewStatementPDF(String statementType) {
		accStats = new AccountStatements(driver);

		accStats.ACC_Statements_P(statementType);
	}

	// **************************** VIEW ACCOUNT STATEMENT PDF
	// *****************************
	@Then("^User do statement tab content validation$")
	public void fn_validateStatementTab() {
		accStats = new AccountStatements(driver);
		accStats.ACC_Statements_Validation();
	}

	// *************************************** PAY BILL NEW
	// ****************************************
	@Then("^User with (.+) Pay bill with (.+), (.+) and (.+) via consumer credit card account$")
	public void fn_payBill(String userType, String account, String billNameValue, String billRefNum) throws Exception {

		accTrans = new AccountTransactions(driver);
		accTrans.ACC_Transactions_P("PAY_BILL");
		managepayeePg1 = new Payment_ManagePayeePage(driver);
        managepayeePg1.PD_AddBillPay_P(userType, account, billNameValue, billRefNum);

	}

	@Then("^User exports transaction history in .csv format$")
	public void fn_ExportTransaction() throws Exception {
		accTrans = new AccountTransactions(driver);
		accTrans.ACC_Transactions_P("EXPORT_TRANSACTION_HISTORY");
	}

	// *************************************** CHEQUE SEARCH
	// ****************************************
	@Then("^User perform check search with cheque (.+)$")
	public void fn_chequeSearch(String chequeNo) {
		accChqSearch = new AccountChequeSearch(driver);
		accChqSearch.ACC_ChequeSearch_P(chequeNo);
	}

	// *************************************** Reduce The Clutter
		// *
	@Then("^User performs reduce the clutter functionality$")
	public void fn_reduceTheClutter() {
		accStats = new AccountStatements(driver);
		accStats.ACC_ReduceTheCultter_P();
	}
	
	// *************************************** NOTICE 2 WITHDRAW ****************************************
	@Then("^User perform notice to withdraw journey on deposit account$")
	public void fn_notice2WD() throws InterruptedException {
		accOpt1 = new AccountOptions(driver);
		accOpt1.ClickOnAccountsTab();
		accOpt1.ACC_Notice2WD();
	}
			
	@Then("^User validates extended transaction details$")
	public void fn_extTransDataVal() throws InterruptedException {
		accTrans1 = new AccountTransactions(driver);
		accTrans1.ACC_ExtTransDetailsVal_P();
	}
	
    // ***************************************TRANSACTION FILTER VALIDATION
    // ****************************************
	@When("^User clicks on the (.+) type of account$")
	public void fn_accSelectionOnAccPg(String accountNumber){
		accOpt = new AccountOptions(driver);
		accOpt.selectAccountFromAccountPg(accountNumber);
	}

	@Then("User select (.+) the transaction of account")
	public void fn_accTransactionFilter(String tansactionType){
		accTrans = new AccountTransactions(driver);
		accTrans.ACC_Transactions_P(tansactionType);
	}

	// *************************************** LOGOUT
	// ****************************************
	@And("^User log out from the application$")
	public void fnLogout() {
		logout = new Logout(driver);
		//logout.fnLogout();
		logout.logout();
	}

	@Then("User verify credit Bill Summary")
	public void userVerifyCreditBillSummary() {
		AccountTransactions accountTransactions=new AccountTransactions(driver);
		accountTransactions.ACC_SMOP_Creditbillsummary_P();
	}
}

package ui.reports;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import shareClasses.FxComboBox;
import shareClasses.Notification;

public class AccountsReportController implements Initializable{

	@FXML
	RadioButton radioReportByLedgeer;
	@FXML
	RadioButton radioCashPaymentVoucher;
	@FXML
	RadioButton radioCashRecivedVoucher;
	@FXML
	RadioButton radioBankPaymentVoucher;
	@FXML
	RadioButton radioBankRecivedVoucher;
	@FXML
	RadioButton radioBankTransection;
	@FXML
	RadioButton radioMonthlyIncomeExpence;
	@FXML
	RadioButton radioIncomeStatement;
	/*@FXML
	RadioButton radioProfitAndLoss;*/
	@FXML
	RadioButton radioTrialBalance;
	@FXML
	RadioButton radioBalanceSheet;

	@FXML
	RadioButton radioSummery;	
	@FXML
	RadioButton radiokDetails;

	@FXML
	Button btnPreview;


	@FXML
	DatePicker dateFromDate;
	@FXML
	DatePicker dateToDate;


	@FXML
	VBox vBoxCustomerName;
	@FXML
	VBox vBoxSupplierName;
	/*@FXML
	VBox vBoxCategoryName;
	@FXML
	VBox vBoxItemName;*/

	FxComboBox cmbHeadName = new FxComboBox<>();
	FxComboBox cmbLedgerName = new FxComboBox<>();
	FxComboBox cmbCategoryName = new FxComboBox<>();
	FxComboBox cmbItemName = new FxComboBox<>();
	
	@FXML
	CheckBox checkHeadAll;
	@FXML
	CheckBox checkLedgerAll;
	/*@FXML
	CheckBox checkCategoryAll;
	@FXML
	CheckBox checkItemNameAll;*/
	
	String pattern = "dd-MMM-yyyy";
	StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		@Override
		public String toString(LocalDate date) {
			if (date != null) {
				return dateFormatter.format(date);
			} else {
				return "";
			}
		}

		@Override
		public LocalDate fromString(String string) {
			if (string != null && !string.isEmpty()) {
				return LocalDate.parse(string, dateFormatter);
			} else {
				return null;
			}
		}
	};

	//CheckBox checkSalesStatement;

	private DatabaseHandler databaseHandler;
	private String sql;
	private HashMap map;
	
	ToggleGroup groupSummeryDetails ;
	ToggleGroup groupReportSelect;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		addCmp();
		focusMoveAction();
		setCmpData();
		setCmpAction();
		setCmpFocusAction();
		btnRefreshAction(null);
	}

	@FXML
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub
		CustomerLoad();
		LedgerNameLoad();
		CategoryLoad();
		ItemNameLoad();
		
		//checkCategoryAll.setDisable(true);
		//checkItemNameAll.setDisable(true);
		checkLedgerAll.setDisable(true);
		checkHeadAll.setDisable(true);
		
		cmbCategoryName.setDisable(true);
		cmbItemName.setDisable(true);
		cmbLedgerName.setDisable(false);
		cmbHeadName.setDisable(false);
		
		setCheckHeadAll(false);
		setCheckLedgerAll(false);
		/*setCheckCategoryAll(false);
		setCheckItemNameAll(false);*/
		
		dateFromDate.setDisable(false);
		dateToDate.setDisable(false);
		
	}

	@FXML
	private void btnPreviewAction(ActionEvent event) {

		map.put("fromDate", getDateFromDate());
		map.put("toDate", getDateToDate());
		if(getRadioReportByLedger()) {
			openReportByLedger();
		}else if(getRadioCashPaymentVoucher()) {
			openCashPaymentVoucher();;
		}else if(getRadioCashRecivedVoucher()) {
			openCashRecivedVoucher();
		}else if(getRadioBankPayementVoucher()) {
			openBankPayment();
		}else if(getRadioBankRecivedVoucher()) {
			openBankRecived();
		}else if(getRadioBankTransection()) {
			openBankTransection();
		}else if(getRadioMonthlyIncomeExpence()) {
			openMonthlyIncomeExpence();
		}else if(getRadioTrialBalance()) {
			openTrialBalance();
		}else if(getRadioIncomeStatement()) {
			openIncomeStatement();
		}

	}

	

	private void openReportByLedger() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";
			String ledgerId = getLedgerId(getCmbLedgerName());
			String ledgerType = getLedgerType(getCmbLedgerName());
			
			
			report = "src/resource/reports/accountsReportFile/LedgerReport.jrxml";
			sql = "select * from LedgerReport ('"+ledgerId+"','"+getCmbLedgerName()+"','"+getDateFromDate()+"','"+getDateToDate()+"') t order by t.date";
			/*if(ledgerType.equals("1")){
				sql="select t.Id,t.LedgerName,t.Date,t.Perticular,t.PaidToFrom,t.Narration,t.OpeningBalance,t.Debit,t.Credit, t.OpeningBalance+SUM(t.Debit-t.Credit) over (order by t.Id) AS Balance FROM LedgerReport ('"+ledgerId+"','"+getCmbLedgerName()+"','"+getDateFromDate()+"','"+getDateToDate()+"') t order by t.Id asc";
			}
			else{
				sql="select t.Id,t.LedgerName,t.Date,t.Perticular,t.PaidToFrom,t.Narration,t.OpeningBalance,t.Debit,t.Credit, t.OpeningBalance+SUM(t.Credit-t.Debit) over (order by t.Id) AS Balance FROM LedgerReport ('"+ledgerId+"','"+getCmbLedgerName()+"','"+getDateFromDate()+"','"+getDateToDate()+"') t order by t.Id asc";
			}
			*/
			/*if(ledgerType.equals("1")) {
				sql = "select accft.date,\r\n" + 
						"((select ISNULL((select tbAccfledger.openingBalance from tbAccfledger  where tbAccfledger.ledgerId='"+ledgerId+"'),0))+(select (select ISNULL(sum(tbAccftransection.amount),0)) from tbAccftransection where tbAccftransection.d_l_id='"+ledgerId+"' and tbAccftransection.date <'"+getDateFromDate()+"' ))-(select (select ISNULL(sum(tbAccftransection.amount),0)) from tbAccftransection where tbAccftransection.c_l_id='"+ledgerId+"' and tbAccftransection.date <'"+getDateFromDate()+"')as OB,\r\n" + 
						"(select ledgerTitle from tbAccfledger where ledgerId='"+ledgerId+"')as ledger,\r\n" + 
						"(select ledgerTitle from tbAccfledger where ledgerId=accft.d_l_id ) debitledger,\r\n" + 
						"(select ledgerTitle from tbAccfledger where ledgerId=accft.c_l_id)as creditLedger ,\r\n" + 
						"(select ledgerTitle from tbAccfledger where ledgerId=accft.c_l_id) as perticular,\r\n" + 
						"accft.amount,\r\n" + 
						"accft.description,\r\n" + 
						"(select username from tblogin where user_id=accft.createBy)as username \r\n" + 
						"from tbAccftransection accft where (accft.d_l_id='"+ledgerId+"' or  accft.c_l_id='"+ledgerId+"') and accft.amount>0 and accft.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' ORDER BY transectionid ";
				
			}else if(ledgerType.equals("2")){
				sql = "select accft.date,\r\n" + 
						"((select ISNULL((select tbAccfledger.openingBalance from tbAccfledger  where tbAccfledger.ledgerId='"+ledgerId+"'),0))+(select (select ISNULL(sum(tbAccftransection.amount),0)) from tbAccftransection where tbAccftransection.c_l_id='"+ledgerId+"' and tbAccftransection.date <'"+getDateFromDate()+"' ))-(select (select ISNULL(sum(tbAccftransection.amount),0)) from tbAccftransection where tbAccftransection.d_l_id='"+ledgerId+"' and tbAccftransection.date <'"+getDateFromDate()+"')as OB,\r\n" + 
						"(select ledgerTitle from tbAccfledger where ledgerId='"+ledgerId+"')as ledger,\r\n" + 
						"(select ledgerTitle from tbAccfledger where ledgerId=accft.d_l_id ) debitledger,\r\n" + 
						"(select ledgerTitle from tbAccfledger where ledgerId=accft.c_l_id)as creditLedger ,\r\n" + 
						"(select ledgerTitle from tbAccfledger where ledgerId=accft.c_l_id) as perticular,\r\n" + 
						"accft.amount,\r\n" + 
						"accft.description,\r\n" + 
						"(select username from tblogin where user_id=accft.createBy)as username \r\n" + 
						"from tbAccftransection accft where (accft.d_l_id='"+ledgerId+"' or  accft.c_l_id='"+ledgerId+"') and accft.amount>0 and accft.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' ORDER BY transectionid ";
				
			}*/
			

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);
			

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	private void openCashPaymentVoucher() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			
			report = "src/resource/reports/accountsReportFile/CashPaymentVoucher.jrxml";
			sql="select voucherNo,dl.ledgerTitle as DebitLedger,cl.ledgerTitle as CreditLedger,amount,description,PaidTo,act.date from tbAccftransection act\r\n" + 
					"join tbAccfledger dl\r\n" + 
					"on dl.ledgerId = act.d_l_id\r\n" + 
					"join tbAccfledger cl\r\n" + 
					"on cl.ledgerId = act.c_l_id where act.type = 11 and  act.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' order by cl.ledgerTitle,act.date";
			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);
			

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void openCashRecivedVoucher() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			
			report = "src/resource/reports/accountsReportFile/CashRecivedVoucher.jrxml";
			sql="select voucherNo,dl.ledgerTitle as DebitLedger,cl.ledgerTitle as CreditLedger,amount,description,PaidTo,act.date from tbAccftransection act\r\n" + 
					"join tbAccfledger dl\r\n" + 
					"on dl.ledgerId = act.d_l_id\r\n" + 
					"join tbAccfledger cl\r\n" + 
					"on cl.ledgerId = act.c_l_id where act.type = 12 and  act.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' order by dl.ledgerTitle,act.date";
			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);
			

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	private void openBankPayment() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			
			report = "src/resource/reports/accountsReportFile/BankPaymentVoucher.jrxml";
			sql="select voucherNo,dl.ledgerTitle as DebitLedger,cl.ledgerTitle as CreditLedger,act.chequeNo,act.chequeDate,amount,description,PaidTo,act.date from tbAccftransection act\r\n" + 
					"join tbAccfledger dl\r\n" + 
					"on dl.ledgerId = act.d_l_id\r\n" + 
					"join tbAccfledger cl\r\n" + 
					"on cl.ledgerId = act.c_l_id where act.type = 13 and  act.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' order by cl.ledgerTitle,act.date";
			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);
			

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void openBankRecived() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			
			report = "src/resource/reports/accountsReportFile/BankRecivedVoucher.jrxml";
			sql="select voucherNo,dl.ledgerTitle as DebitLedger,cl.ledgerTitle as CreditLedger,act.chequeNo,act.chequeDate,amount,description,PaidTo,act.date from tbAccftransection act\r\n" + 
					"join tbAccfledger dl\r\n" + 
					"on dl.ledgerId = act.d_l_id\r\n" + 
					"join tbAccfledger cl\r\n" + 
					"on cl.ledgerId = act.c_l_id where act.type = 14 and  act.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' order by cl.ledgerTitle,act.date";
			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);
			

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void openBankTransection() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWise.jrxml";
				if(getCmbHeadName().equals(""))
					sql = "select customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName order by customerName";
				else
					sql = "select customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and customerName = '"+getCmbHeadName()+"' and customerID = '"+getCustomerId(getCmbHeadName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName order by customerName";
			}else {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWiseDetails.jrxml";
				
				if(getCmbHeadName().equals(""))
					sql = "select customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName,date,Invoice order by customerName";
				else
					sql = "select customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and customerName = '"+getCmbHeadName()+"' and customerID = '"+getCustomerId(getCmbHeadName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName,date,Invoice order by customerName";
			}

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);
			

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void openMonthlyIncomeExpence() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWise.jrxml";
				if(getCmbHeadName().equals(""))
					sql = "select customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName order by customerName";
				else
					sql = "select customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and customerName = '"+getCmbHeadName()+"' and customerID = '"+getCustomerId(getCmbHeadName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName order by customerName";
			}else {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWiseDetails.jrxml";
				
				if(getCmbHeadName().equals(""))
					sql = "select customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName,date,Invoice order by customerName";
				else
					sql = "select customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and customerName = '"+getCmbHeadName()+"' and customerID = '"+getCustomerId(getCmbHeadName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName,date,Invoice order by customerName";
			}

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);
			

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void openIncomeStatement() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/accountsReportFile/IncomeStatement.jrxml";
				
				sql = "select * from fnIncomeStatement('"+getDateFromDate()+"','"+getDateToDate()+"')";
				
			}else {
				report = "src/resource/reports/accountsReportFile/IncomeStatement.jrxml";
				
				sql = "select * from fnIncomeStatement('"+getDateFromDate()+"','"+getDateToDate()+"')";
				
			}

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);
			

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void openProfitAndLoss() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWise.jrxml";
				if(getCmbHeadName().equals(""))
					sql = "select customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName order by customerName";
				else
					sql = "select customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and customerName = '"+getCmbHeadName()+"' and customerID = '"+getCustomerId(getCmbHeadName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName order by customerName";
			}else {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWiseDetails.jrxml";
				
				if(getCmbHeadName().equals(""))
					sql = "select customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName,date,Invoice order by customerName";
				else
					sql = "select customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and customerName = '"+getCmbHeadName()+"' and customerID = '"+getCustomerId(getCmbHeadName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName,date,Invoice order by customerName";
			}

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);
			

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void openTrialBalance() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/accountsReportFile/TrialBalance.jrxml";
				
				sql = "select * from FnTrialBalance('"+getDateFromDate()+"','"+getDateToDate()+"')";
				
			}else {
				report = "src/resource/reports/accountsReportFile/TrialBalanceDetails.jrxml";
				
				sql = "select * from FnTrialBalanceDetails('"+getDateFromDate()+"','"+getDateToDate()+"')";
				
			}
			

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);
			

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void openBalanceSheet() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWise.jrxml";
				if(getCmbHeadName().equals(""))
					sql = "select customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName order by customerName";
				else
					sql = "select customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and customerName = '"+getCmbHeadName()+"' and customerID = '"+getCustomerId(getCmbHeadName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName order by customerName";
			}else {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWiseDetails.jrxml";
				
				if(getCmbHeadName().equals(""))
					sql = "select customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName,date,Invoice order by customerName";
				else
					sql = "select customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbSales where type = 3 and customerName = '"+getCmbHeadName()+"' and customerID = '"+getCustomerId(getCmbHeadName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by customerName,date,Invoice order by customerName";
			}

			System.out.println(sql);
			JasperDesign jd=JRXmlLoader.load(report);
			JRDesignQuery jq=new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, map,databaseHandler.conn);
			JasperViewer.viewReport(jp, false);
			

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private boolean isCustomerExist(String customerName) {
		try {
			sql = "select * from tbCustomer where CustomerName = '"+customerName+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return false;
	}

	private String getCustomerId(String customerName) {
		// TODO Auto-generated method stub
		try {
			sql = "select id from tbCustomer where CustomerName = '"+customerName+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	private String getLedgerId(String ledgerName) {
		// TODO Auto-generated method stub
		try {
			String sql="select ledgerId from tbAccfledger where ledgertitle='"+ledgerName+"'";
			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("ledgerId");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}

	private String getLedgerType(String ledgerName) {
		// TODO Auto-generated method stub
		try {
			String sql="select type from tbAccfledger where ledgertitle='"+ledgerName+"'";
			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("type");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}

	public void CustomerLoad() {
		try {
			sql = "select CustomerName from tbCustomer";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbHeadName.data.clear();
			while (rs.next()) {
				cmbHeadName.data.add(rs.getString("CustomerName"));
			}
			
			sql = "select SellerCustomerName from tbInvoice where SellerCustomerID = '0' group by SellerCustomerName order by SellerCustomerName";
			rs = databaseHandler.execQuery(sql);
			while (rs.next()) {
				cmbHeadName.data.add(rs.getString("SellerCustomerName"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void LedgerNameLoad() {
		try {
			sql = "select ledgertitle from tbAccfledger order by ledgerTitle";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbLedgerName.data.clear();
			while (rs.next()) {
				cmbLedgerName.data.add(rs.getString("ledgertitle"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void CategoryLoad() {
		try {
			sql = "select categoryName from tbCategory order by categoryName";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbCategoryName.data.clear();
			while (rs.next()) {
				cmbCategoryName.data.add(rs.getString("categoryName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void ItemNameLoad() {
		try {
			sql = "select projectedItemName from tbItem order by projectedItemName";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbItemName.data.clear();
			while (rs.next()) {
				cmbItemName.data.add(rs.getString("projectedItemName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbHeadName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbHeadName);
		});
		
		cmbLedgerName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbLedgerName);
		});
		
		cmbCategoryName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCategoryName);
		});
		
		cmbItemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbItemName);
		});
	}

	
	private void selectCombboxIfFocused(ComboBox box){
		Platform.runLater(() -> {
			if ((box.getEditor().isFocused() || box.isFocused()) && !box.getEditor().getText().isEmpty()) {
				box.getEditor().selectAll();
			}
		});
	}
	private void selectTextIfFocused(TextField text){
		Platform.runLater(() -> {
			if (text.isFocused()  && !text.getText().isEmpty()) {
				text.selectAll();
			}
		});
	}
	private void setCmpAction() {
		// TODO Auto-generated method stub

		checkHeadAll.setOnAction(e->{
			if(checkHeadAll.isSelected()) {
				
				setCmbHeadName("");		
				cmbHeadName.setDisable(true);
				
			}else {

				setCmbHeadName("");
				cmbHeadName.setDisable(false);
				
			}
		});
		
		checkLedgerAll.setOnAction(e->{
			if(checkLedgerAll.isSelected()) {
				
				setCmbLedgerName("");			
				cmbLedgerName.setDisable(true);
				
			}else {

				setCmbLedgerName("");
				cmbLedgerName.setDisable(false);
				
			}
		});
		
		/*checkCategoryAll.setOnAction(e->{
			if(checkCategoryAll.isSelected()) {
				
				setCmbItemName("");
				setCmbCategoryName("");
				cmbItemName.setDisable(true);
				cmbCategoryName.setDisable(true);
			}else {

				setCmbItemName("");
				setCmbCategoryName("");
				cmbItemName.setDisable(false);
				cmbCategoryName.setDisable(false);
			}
		});*/
		
		/*checkItemNameAll.setOnAction(e->{
			if(checkItemNameAll.isSelected()) {
				
				setCmbItemName("");			
				cmbItemName.setDisable(true);
				
			}else {

				setCmbItemName("");
				cmbItemName.setDisable(false);
				
			}
		});*/
		
		radioReportByLedgeer.setOnAction(e->{
			/*checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);*/
			checkLedgerAll.setDisable(true);
			checkHeadAll.setDisable(true);
			
			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbLedgerName.setDisable(false);
			cmbHeadName.setDisable(false);
			
			setCheckHeadAll(true);
			setCheckLedgerAll(true);
			/*setCheckCategoryAll(false);
			setCheckItemNameAll(false);*/
			
			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
		});
		
		radioCashPaymentVoucher.setOnAction(e->{
			/*checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);*/
			checkLedgerAll.setDisable(true);
			checkHeadAll.setDisable(false);
			
			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbLedgerName.setDisable(true);
			cmbHeadName.setDisable(true);
			
			setCheckHeadAll(true);
			setCheckLedgerAll(true);
			/*setCheckCategoryAll(false);
			setCheckItemNameAll(false);*/
			
			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
		});
		
		radioCashRecivedVoucher.setOnAction(e->{
			/*checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);*/
			checkLedgerAll.setDisable(true);
			checkHeadAll.setDisable(true);
			
			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbLedgerName.setDisable(true);
			cmbHeadName.setDisable(true);
			
			setCheckHeadAll(false);
			setCheckLedgerAll(false);
			/*setCheckCategoryAll(false);
			setCheckItemNameAll(false);
			*/
			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
		});
		
		radioBankPaymentVoucher.setOnAction(e->{
			/*checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);*/
			checkLedgerAll.setDisable(true);
			checkHeadAll.setDisable(false);
			
			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbLedgerName.setDisable(true);
			cmbHeadName.setDisable(false);
			
			setCheckHeadAll(true);
			setCheckLedgerAll(false);
			/*setCheckCategoryAll(false);
			setCheckItemNameAll(false);*/
			
			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
		});
		
		radioBankRecivedVoucher.setOnAction(e->{
			/*checkCategoryAll.setDisable(false);
			checkItemNameAll.setDisable(false);*/
			checkLedgerAll.setDisable(true);
			checkHeadAll.setDisable(true);
			
			cmbCategoryName.setDisable(false);
			cmbItemName.setDisable(false);
			cmbLedgerName.setDisable(true);
			cmbHeadName.setDisable(true);
			
			setCheckHeadAll(true);
			setCheckLedgerAll(true);
			/*setCheckCategoryAll(true);
			setCheckItemNameAll(true);*/
			
			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
		});
		
		radioBankTransection.setOnAction(e->{
			/*checkCategoryAll.setDisable(false);
			checkItemNameAll.setDisable(false);*/
			checkLedgerAll.setDisable(true);
			checkHeadAll.setDisable(true);
			
			cmbCategoryName.setDisable(false);
			cmbItemName.setDisable(false);
			cmbLedgerName.setDisable(true);
			cmbHeadName.setDisable(true);
			
			setCheckHeadAll(false);
			setCheckLedgerAll(false);
			/*setCheckCategoryAll(true);
			setCheckItemNameAll(true);*/
			
			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);
		});
	}

	private void setCmpData() {
		// TODO Auto-generated method stub

		dateFromDate.setConverter(converter);
		dateFromDate.setValue(LocalDate.now());

		dateToDate.setConverter(converter);
		dateToDate.setValue(LocalDate.now());

		radioSummery.setToggleGroup(groupSummeryDetails);
		radiokDetails.setToggleGroup(groupSummeryDetails);
		radioSummery.setSelected(true);
		

		//groupReportSelect.getChildren().addAll(checkSalesStatement,checkSalesStatementSupplierWise);
		radioReportByLedgeer.setToggleGroup(groupReportSelect);
		radioCashPaymentVoucher.setToggleGroup(groupReportSelect);
		radioCashRecivedVoucher.setToggleGroup(groupReportSelect);
		radioBankPaymentVoucher.setToggleGroup(groupReportSelect);
		radioBankRecivedVoucher.setToggleGroup(groupReportSelect);
		radioBankTransection.setToggleGroup(groupReportSelect);
		radioMonthlyIncomeExpence.setToggleGroup(groupReportSelect);
		radioIncomeStatement.setToggleGroup(groupReportSelect);
		//radioProfitAndLoss.setToggleGroup(groupReportSelect);
		radioTrialBalance.setToggleGroup(groupReportSelect);
		radioBalanceSheet.setToggleGroup(groupReportSelect);
		
	}

	private void focusMoveAction() {
		// TODO Auto-generated method stub

	}

	private void addCmp() {
		// TODO Auto-generated method stub

		map = new HashMap<>();
		groupSummeryDetails = new ToggleGroup();
		groupReportSelect = new ToggleGroup();

		vBoxCustomerName.getChildren().clear();
		cmbHeadName.setPrefHeight(28);
		cmbHeadName.setPrefWidth(295);
		vBoxCustomerName.getChildren().add(cmbHeadName);
		
		vBoxSupplierName.getChildren().clear();
		cmbLedgerName.setPrefHeight(28);
		cmbLedgerName.setPrefWidth(295);
		vBoxSupplierName.getChildren().add(cmbLedgerName);

		//vBoxCategoryName.getChildren().clear();
		cmbCategoryName.setPrefHeight(28);
		cmbCategoryName.setPrefWidth(295);
		//vBoxCategoryName.getChildren().add(cmbCategoryName);
		
		//vBoxItemName.getChildren().clear();
		cmbItemName.setPrefHeight(28);
		cmbItemName.setPrefWidth(295);
		//vBoxItemName.getChildren().add(cmbItemName);

	}

	public boolean getRadioReportByLedger() {
		return radioReportByLedgeer.isSelected();
	}

	public void setRadioReportByLedger(boolean radioReportByLedgeer) {
		this.radioReportByLedgeer.setSelected(radioReportByLedgeer);
	}

	public boolean getRadioCashPaymentVoucher() {
		return radioCashPaymentVoucher.isSelected();
	}

	public void setRadioCashPaymentVoucher(boolean radioCashPaymentVoucher) {
		this.radioCashPaymentVoucher.setSelected(radioCashPaymentVoucher);
	}

	public boolean getRadioSummery() {
		return radioSummery.isSelected();
	}

	public void setRadioSummery(boolean radioSummery) {
		this.radioSummery.setSelected( radioSummery);
	}

	public boolean getRadiokDetails() {
		return radiokDetails.isSelected();
	}

	public void setRadiokDetails(boolean radiokDetails) {
		this.radiokDetails.setSelected( radiokDetails);
	}
	
	

	public boolean getRadioCashRecivedVoucher() {
		return radioCashRecivedVoucher.isSelected();
	}

	public void setRadioCashRecivedVoucher(boolean radioServicingStatement) {
		this.radioCashRecivedVoucher.setSelected(radioServicingStatement);
	}

	public boolean getRadioBankPayementVoucher() {
		return radioBankPaymentVoucher.isSelected();
	}

	public void setRadioBankPaymentVoucher(boolean radioServicingStatementCustomerWise) {
		this.radioBankPaymentVoucher.setSelected(radioServicingStatementCustomerWise);
	}
	
	public boolean getRadioBankRecivedVoucher() {
		return radioBankRecivedVoucher.isSelected();
	}

	public void setRadioBankRecivedVoucher(boolean RadioAllItemStockPosition) {
		this.radioBankRecivedVoucher.setSelected(RadioAllItemStockPosition);
	}
	
	public boolean getRadioBankTransection() {
		return radioBankTransection.isSelected();
	}

	public void setRadioBankTransection(boolean RadioAllItemStockPositionWithValue) {
		this.radioBankTransection.setSelected(RadioAllItemStockPositionWithValue);
	}
	
	public boolean getRadioMonthlyIncomeExpence() {
		return radioMonthlyIncomeExpence.isSelected();
	}

	public void setRadioMonthlyIncomeExpence(boolean radioMonthlyIncomeExpence) {
		this.radioMonthlyIncomeExpence.setSelected(radioMonthlyIncomeExpence);
	}
	
	public boolean getRadioTrialBalance() {
		return radioTrialBalance.isSelected();
	}

	public void setRadioTrialBalance(boolean radioTrialBalance) {
		this.radioTrialBalance.setSelected(radioTrialBalance);
	}
	
	public boolean getRadioIncomeStatement() {
		return radioIncomeStatement.isSelected();
	}

	public void setRadioIncomeStatement(boolean radioIncomeStatement) {
		this.radioIncomeStatement.setSelected(radioIncomeStatement);
	}
	
	/*public boolean getRadioProfitAndLoss() {
		return radioProfitAndLoss.isSelected();
	}

	public void setRadioProfitAndLoss(boolean radioProfitAndLoss) {
		this.radioProfitAndLoss.setSelected(radioProfitAndLoss);
	}*/
	
	public boolean getRadioBalanceSheet() {
		return radioBalanceSheet.isSelected();
	}

	public void setRadioBalanceSheet(boolean radioBalanceSheet) {
		this.radioBalanceSheet.setSelected(radioBalanceSheet);
	}

	public String getDateFromDate() {
		if(dateFromDate.getValue() != null)
			return dateFromDate.getValue().toString();
		else 
			return "";
	}

	public void setDateFromDate(DatePicker dateFromDate) {
		if(dateFromDate != null) {
			int dd=Integer.valueOf(new SimpleDateFormat("dd").format(dateFromDate));
			int mm=Integer.valueOf(new SimpleDateFormat("MM").format(dateFromDate));
			int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(dateFromDate));
			this.dateFromDate.setValue(LocalDate.of(yy,mm,dd));
		}else {
			this.dateFromDate.getEditor().setText("");
			this.dateFromDate.setValue(null);
		}
	}

	public String getDateToDate() {
		if(dateToDate.getValue() != null)
			return dateToDate.getValue().toString();
		else 
			return "";
	}
	

	public boolean getCheckHeadAll() {
		return checkHeadAll.isSelected();
	}

	public void setCheckHeadAll(boolean checkCustomerAll) {
		this.checkHeadAll.setSelected(checkCustomerAll);
	}

	public boolean getCheckLedgerAll() {
		return checkLedgerAll.isSelected();
	}

	public void setCheckLedgerAll(boolean checkSupplierAll) {
		this.checkLedgerAll.setSelected(checkSupplierAll);
	}

	/*public boolean getCheckCategoryAll() {
		return checkCategoryAll.isSelected();
	}

	public void setCheckCategoryAll(boolean checkCategoryAll) {
		this.checkCategoryAll.setSelected(checkCategoryAll);
	}

	public boolean getCheckItemNameAll() {
		return checkItemNameAll.isSelected();
	}

	public void setCheckItemNameAll(boolean checkItemNameAll) {
		this.checkItemNameAll.setSelected(checkItemNameAll);
	}*/

	public String getCmbHeadName() {
		if(cmbHeadName.getValue() != null)
			return cmbHeadName.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbHeadName(String cmbCustomerName) {
		if(cmbCustomerName.equals("")) 
			this.cmbHeadName.setValue(null);
		this.cmbHeadName.getEditor().setText(cmbCustomerName);
	}
	
	
	public String getCmbLedgerName() {
		if(cmbLedgerName.getValue() != null)
			return cmbLedgerName.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbLedgerName(String cmbSupplierName) {
		if(cmbSupplierName.equals("")) 
			this.cmbLedgerName.setValue(null);
		this.cmbLedgerName.getEditor().setText(cmbSupplierName);
	}
	
	public String getCmbCategoryName() {
		if(cmbCategoryName.getValue() != null)
			return cmbCategoryName.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbCategoryName(String cmbCategory) {
		if(cmbCategory.equals("")) 
			this.cmbCategoryName.setValue(null);
		this.cmbCategoryName.getEditor().setText(cmbCategory);
	}
	
	public String getCmbItemName() {
		if(cmbItemName.getValue() != null)
			return cmbItemName.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbItemName(String cmbItemName) {
		if(cmbItemName.equals("")) 
			this.cmbItemName.setValue(null);
		this.cmbItemName.getEditor().setText(cmbItemName);
	}
	
	
	
	public void setDateToDate(Date dateToDate) {
		if(dateToDate != null) {
			int dd=Integer.valueOf(new SimpleDateFormat("dd").format(dateToDate));
			int mm=Integer.valueOf(new SimpleDateFormat("MM").format(dateToDate));
			int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(dateToDate));
			this.dateToDate.setValue(LocalDate.of(yy,mm,dd));
		}else {
			this.dateToDate.getEditor().setText("");
			this.dateToDate.setValue(null);
		}
	}
	
	
	



}

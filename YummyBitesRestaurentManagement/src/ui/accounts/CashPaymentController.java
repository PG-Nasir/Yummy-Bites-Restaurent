package ui.accounts;

import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
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
import shareClasses.AlertMaker;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.Notification;
import shareClasses.SessionBeam;
import ui.accounts.CashRecivedController.VoucherDetails;
import ui.accounts.HeadAndLedgerCreateController.Ledger;





public class CashPaymentController implements Initializable{


	@FXML
	TextField txtVoucherNo;
	@FXML
	TextField txtAmount;
	@FXML
	TextField txtPaidTo;
	@FXML
	TextField txtNarration;
	@FXML
	TextField txtTotalPayableAmount;

	@FXML
	VBox vBoxFind;
	@FXML
	VBox vBoxDebitLedger;
	@FXML
	VBox vBoxCreditLedger;

	FxComboBox cmbFind = new FxComboBox<>();
	FxComboBox cmbDebitLedger = new FxComboBox<>();
	FxComboBox cmbCreditLedger = new FxComboBox<>();

	@FXML
	Button btnFind;
	@FXML
	Button btnSave;
	@FXML
	Button btnEdit;
	@FXML
	Button btnRefresh;
	@FXML
	Button btnPrint;
	@FXML
	Button btnAdd;

	@FXML
	DatePicker date;

	@FXML
	CheckBox checkInvoicePayement;

	CheckBox checkSelectAll;


	@FXML
	private TableView<VoucherDetails> table;

	ObservableList<VoucherDetails> list = FXCollections.observableArrayList();

	@FXML
	private TableColumn<VoucherDetails, String> transectionIdCol;
	@FXML
	private TableColumn<VoucherDetails, String> voucherNoCol;
	@FXML
	private TableColumn<VoucherDetails, String> dateCol;
	@FXML
	private TableColumn<VoucherDetails, String> debitLedgerCol;
	@FXML
	private TableColumn<VoucherDetails, String> creditLedgerCol;
	@FXML
	private TableColumn<VoucherDetails, String> amountCol;
	@FXML
	private TableColumn<VoucherDetails, String> deleteCol;


	@FXML
	private TableView<InvoiceList> tableInvoiceList;

	static ObservableList<InvoiceList> listInvoiceList = FXCollections.observableArrayList();

	@FXML
	private TableColumn<InvoiceList, String> invoiceNoCol;
	@FXML
	private TableColumn<InvoiceList, String> totalAmountCol;
	@FXML
	private TableColumn<InvoiceList, String> netAmountCol;
	@FXML
	private TableColumn<InvoiceList, String> paidAmountCol;
	@FXML
	private TableColumn<InvoiceList, String> dueAmountCol;
	@FXML
	private TableColumn<InvoiceList, CheckBox> isSelectCol;

	private DecimalFormat df = new DecimalFormat("#0.00");
	private HashMap map;

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
	private DatabaseHandler databaseHandler;
	String sql;

	String voucherType="11";
	String purchaseType = "1";


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		cmpAdd();
		cmpSetData();
		setCmpFocusAction();
		cmpAction();
		refreshAction(null);
		focusMoveAction();
	}

	@FXML
	private void btnAddAction(ActionEvent event) {
		if(!isVoucherNoExist()) {
			if(ledgerAddValidationCheck()) {
				if(doubleCreditLedgerAndDateCheck()) {
					if(!doubleDebitLedgerCheck()) {
						list.add(new VoucherDetails("", getTxtVoucherNo(), getDate(), getCmbDebitLedger(),getCmbCreditLedger(),getTxtAmount(),"Del"));
						table.setItems(list);
						cmbDebitLedger.requestFocus();
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","You Can't Entry Double Debit Ledger .");
						cmbDebitLedger.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","You Can't Entry Multiple Credit Ledger And Date In a Debit Voucher.\nPlease Enter Same Credit Ledger and Date...");
					cmbCreditLedger.requestFocus();
				}
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Voucher No Allready Exist. \nPlease Set a new Voucher No...");
			btnRefresh.requestFocus();
		}
	}

	@FXML
	private void editAction(ActionEvent event) {
		try {
			if(isVoucherNoExist()) {
				if(list.size()>0) {
					if(confrimationCheck("Edit")) {
						for( int i =0 ;i< list.size();i++) {
							VoucherDetails tempDetails = list.get(i);
							sql = "update tbAccftransection set PaidTo= '"+getTxtPaidTo()+"' ,description = '"+getTxtNarration()+"' ,entryTime = CURRENT_TIMESTAMP ,createBy = '"+SessionBeam.getUserId()+"' where voucherNo = '"+getTxtVoucherNo()+"' and type = '"+voucherType+"';";

							databaseHandler.execAction(sql);

							new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Voucher Edit Successfully...");
							printAction(null);
							refreshAction(null);
						}
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter any Debit Ledger");
					cmbDebitLedger.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Voucher No Allready Exist. \nYou Can not save This voucher again.....");
				btnRefresh.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void saveAction(ActionEvent event) {
		try {
			if(!isVoucherNoExist()) {
				if(list.size()>0) {
					if(listInvoiceList.size()>0 && getCheckInvoicePayement()) {
						if(isTotalPayableAmountExist()) {
							if(confrimationCheck("Save")) {
								double availableAmount = getTotalPaymentAmount();
								String invoiceNo;
								double invoicePaidAmount;
								double invoiceDueAmount;
								double invoiceCash;
								double invoiceCard;
								String invoiceCashId;
								String invoiceCardId;
								double invoiceDuePay=0;
								String payType = "Due Pay By Cash Payment";
								String transectionId;
								String d_l_id;
								String c_l_id;

								sql = "insert into tbpaymentHistory (invoiceNo,cash, card,due,depID,type,date,paymentType,flag,entryTime,entryBy) values ( '" + getTxtVoucherNo() + "', '" + availableAmount + "','0','0','" + 1 + "','" + voucherType + "','" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "','Cash','Total Purchase Due Payment',CURRENT_TIMESTAMP, '" + SessionBeam.getUserId() + "' )";
								databaseHandler.execAction(sql);

								transectionId = getNewTransectionId();
								VoucherDetails tempDetails = list.get(0);
								sql = "insert into tbAccftransection (transectionid,voucherNo,type,Status,unitId,depId,d_l_id,c_l_id,"
										+ "amount,groupId,description,PaidTo,date,transectionType,chequeNo,chequeDate,entryTime,createBy) " + 
										"values('"+getNewTransectionId()+"','"+getTxtVoucherNo()+"','"+voucherType+"','Cash Payement','1','1',"
										+ "'"+getLedgerId(tempDetails.getDebitLedger())+"',"
										+ "'"+getLedgerId(tempDetails.getCreditLedger())+"','"+tempDetails.getAmount()+"',"
										+ "'1','"+getTxtNarration()+"','"+getTxtPaidTo()+"','"+getDate()+"','Purchase Due Cash Payment','','',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";

								databaseHandler.execAction(sql);

								for(int i=0;i<listInvoiceList.size() && availableAmount>0 ;i++) {

									InvoiceList tempInvoice = listInvoiceList.get(i);

									if(tempInvoice.idCheckSelect()) {

										invoiceNo = tempInvoice.getInvoiceNo();
										invoiceDueAmount = tempInvoice.getDueAmount();
										invoicePaidAmount = tempInvoice.getPaidAmount();
										invoiceCash = tempInvoice.getCash();
										invoiceCard = tempInvoice.getCard();
										invoiceCashId = tempInvoice.getCashId();
										invoiceCardId = tempInvoice.getCardId();

										if(invoiceDueAmount>=availableAmount) {
											invoiceDuePay = availableAmount;
											availableAmount = 0;
											invoiceDueAmount = invoiceDueAmount - invoiceDuePay;
										}else if(invoiceDueAmount < availableAmount) {
											invoiceDuePay = invoiceDueAmount;
											invoiceDueAmount = 0;
											availableAmount = availableAmount - invoiceDuePay;
										}

										invoicePaidAmount += invoiceDuePay;
										invoiceCash += invoiceDuePay;

										sql = "insert into tbpaymentHistory (invoiceNo,cash, card,due,depID,type,date,paymentType,flag,entryTime,entryBy) values ( '" + invoiceNo + "', '" + invoiceDuePay + "','0','" + invoiceDueAmount + "','" + 1 + "','" + purchaseType + "','" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "','Cash','" + payType + "',CURRENT_TIMESTAMP, '" + SessionBeam.getUserId() + "' )";

										databaseHandler.execAction(sql);




										/*transectionId=transectionAutoId();
										d_l_id = supplierledgerId;
										c_l_id = paymentLedgerId;
										sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+getTxtInvoiceNO()+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+txtDueAmount.getText()+"','"+payType+"','"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
										databaseHandler.execAction(sql);
										*/
										

										sql="update tbInvoice set paid="+invoicePaidAmount+",cash="+invoiceCash+",card="+invoiceCard+",due="+invoiceDueAmount+", cashid='"+invoiceCardId+","+transectionId+"' where invoice='"+invoiceNo+"' and type='"+purchaseType+"'";

										databaseHandler.execAction(sql);

										
									}
									
									

								}
								new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Voucher Save Successfully...");
								printAction(null);
							}

						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Payement Amount Exist...","You Can't Pay More Than Payable Due Amount...");
						}
					}else {

						if(confrimationCheck("Save")) {
							for( int i =0 ;i< list.size();i++) {
								VoucherDetails tempDetails = list.get(i);
								sql = "insert into tbAccftransection (transectionid,voucherNo,type,Status,unitId,depId,d_l_id,c_l_id,"
										+ "amount,groupId,description,PaidTo,date,transectionType,chequeNo,chequeDate,entryTime,createBy) " + 
										"values('"+getNewTransectionId()+"','"+getTxtVoucherNo()+"','"+voucherType+"','Cash Payement','1','1',"
										+ "'"+getLedgerId(tempDetails.getDebitLedger())+"',"
										+ "'"+getLedgerId(tempDetails.getCreditLedger())+"','"+tempDetails.getAmount()+"',"
										+ "'1','"+getTxtNarration()+"','"+getTxtPaidTo()+"','"+getDate()+"','Cash','','',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";

								databaseHandler.execAction(sql);


							}
							new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Voucher Save Successfully...");
							printAction(null);
							refreshAction(null);
						}
					}

				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter any Debit Ledger");
					cmbDebitLedger.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Voucher No Allready Exist. \nYou Can not save This voucher again.....");
				btnRefresh.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}



	@FXML
	private void refreshAction(ActionEvent event) {
		loadMaxVoucherNo();
		loadLedgerName();
		loadCmbFind();
		setDate(new Date());
		setCmbDebitLedger("");
		setCmbCreditLedger("Cash");
		setTxtAmount("0");
		setTxtPaidTo("");
		setTxtNarration("");
		list.clear();
	}

	@FXML
	private void printAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			if(isVoucherNoExist()) {
				
				map.put("orgName", SessionBeam.getOrgName());
				map.put("orgAddress", SessionBeam.getOrgAddress());
				map.put("orgContact", SessionBeam.getOrgContact());
				
				sql="select *,(select dbo.number((select sum(amount) from tbaccftransection where type='"+voucherType+"' and voucherNo='"+getTxtVoucherNo()+"'))) as Taka,(select ledgerTitle from tbAccfledger where ledgerId=tbaccftransection.c_l_id) as CashLedger,(select ledgerTitle from tbAccfledger where ledgerId=tbAccftransection.d_l_id) as PaidToLedger from tbAccftransection where type='"+voucherType+"' and voucherNo='"+getTxtVoucherNo()+"'";
				System.out.println(sql);
				String report="src/resource/reports/Invoices/CashPaymentVoucher.jrxml";
				JasperDesign jd=JRXmlLoader.load(report);
				JRDesignQuery jq=new JRDesignQuery();
				jq.setText(sql);
				jd.setQuery(jq);
				JasperReport jr=JasperCompileManager.compileReport(jd);
				JasperPrint jp=JasperFillManager.fillReport(jr, map ,databaseHandler.conn);
				JasperViewer.viewReport(jp, false);
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Voucher No Is Not Exist......");
				cmbFind.requestFocus();
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void findAction(ActionEvent event) {
		try {
			sql = "select transectionid,voucherNo,amount,(select ledgertitle from tbAccfledger where ledgerId = d_l_id) as debidLedger,(select ledgertitle from tbAccfledger where ledgerId = c_l_id) as creditLedger,description,PaidTo,date from tbAccftransection where voucherNo = '"+getCmbFind()+"' and type='"+voucherType+"';";
			System.out.println(sql);
			ResultSet rs = databaseHandler.execQuery(sql);
			list.clear();
			while(rs.next()) {
				setTxtVoucherNo(rs.getString("voucherNo"));
				setTxtPaidTo(rs.getString("PaidTo"));
				setTxtNarration(rs.getString("description"));
				list.add(new VoucherDetails(rs.getString("transectionid"), rs.getString("voucherNo"), rs.getString("date"), rs.getString("debidLedger"),rs.getString("creditLedger"),df.format(rs.getDouble("amount")),"Del"));
			}
			table.setItems(list);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private String getLedgerId(String LedgerName) {
		try {
			sql = "select ledgerId from tbAccfledger where ledgerTitle = '"+LedgerName+"' ";
			System.out.println(sql);
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("ledgerId");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "";

	}

	private String getNewTransectionId() {
		// TODO Auto-generated method stub
		try {
			sql = "select isnull(max(transectionid),0)+1 as maxId from tbAccftransection";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("maxId");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}

	private double getTotalPaymentAmount() {

		double total = 0;

		for(int i = 0 ;i<list.size();i++) {
			total += list.get(i).getAmountDouble();
		}

		return total; 
	}

	@FXML
	private void tableClickAction() {
		try {
			if(table.getSelectionModel().getSelectedItem() != null) {
				TablePosition firstCell = table.getSelectionModel().getSelectedCells().get(0);
				VoucherDetails tempProduct = table.getSelectionModel().getSelectedItem();
				
				if(firstCell.getColumn()==6 && tempProduct.getTransectionId().equals("")) {
					list.remove(tempProduct);
					table.setItems(list);

				}

			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}



	private boolean confrimationCheck(String name) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation", "Are You Sure to "+name+" this Debit Voucher?");
	}

	private boolean doubleCreditLedgerAndDateCheck() {
		// TODO Auto-generated method stub
		if(list.size()>0) {

			VoucherDetails tempVoucher = list.get(0);
			if(tempVoucher.getDate().equals(getDate()) && tempVoucher.getCreditLedger().equals(getCmbCreditLedger())) {
				return true;
			}else{
				return false;
			}

		}
		return true;
	}
	
	private boolean doubleDebitLedgerCheck() {
		// TODO Auto-generated method stub
		if(list.size()>0) {

			VoucherDetails tempVoucher = list.get(0);
			for(int i = 0;i<list.size();i++) {
				if(list.get(i).getDebitLedger().equals(getCmbDebitLedger())) {
					return true;
				}
			}
			

		}
		return false;
	}

	

	private boolean ledgerAddValidationCheck() {
		// TODO Auto-generated method stub

		if(!getTxtVoucherNo().isEmpty()) {
			if(date.getValue() != null) {
				if(!getCmbDebitLedger().isEmpty()) {
					if(!getCmbCreditLedger().isEmpty()) {
						if(isLedgerNameExist(getCmbDebitLedger())) {
							if(isLedgerNameExist(getCmbCreditLedger())) {
								if(!(getCmbDebitLedger().equals(getCmbCreditLedger()))) {
									if(!getTxtAmount().isEmpty()) {
										if(Double.valueOf(getTxtAmount())>0) {
											return true;
										}else {
											new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Enter a Valid Amount");
											txtAmount.requestFocus();
										}
									}else {
										new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Amount..");
										txtAmount.requestFocus();
									}
								}else {
									new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Your Debit Ledger and Credit Ledger are Same...");
									cmbCreditLedger.requestFocus();
								}
							}else {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Your Debit Ladger is Invalid.\nPlease Select a Valid Credit Ledger...");
								cmbCreditLedger.requestFocus();
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Your Debit Ladger is Invalid.\nPlease Select a Valid Debit Ledger...");
							cmbDebitLedger.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter a Credit Ledger...");
						cmbCreditLedger.requestFocus();
					}	
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter a Debit Ledger...");
					cmbDebitLedger.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select a Valid Date...");
				date.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Voucher No...");
			btnRefresh.requestFocus();
		}
		return false;
	}

	private boolean isLedgerNameExist(String ledgerName) {
		// TODO Auto-generated method stub
		try {
			sql = "select ledgerTitle from tbAccfledger where ledgerTitle = '"+ledgerName+"' ";
			System.out.println(sql);
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}
	private boolean isVoucherNoExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select voucherNo from tbaccftransection where type = '"+voucherType+"' and voucherNo = '"+getTxtVoucherNo()+"';";

			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}

	private boolean isTotalPayableAmountExist() {
		// TODO Auto-generated method stub
		double tempTotal=0;
		for(int i=0;i<list.size();i++) {
			tempTotal += list.get(i).getAmountDouble();
		}
		if(tempTotal<=Double.valueOf(getTxtTotalPayableAmount())) {
			return true;
		}	
		return false;

	}
	private void loadCmbFind() {
		try {
			cmbFind.data.clear();
			sql = " select voucherNo from tbAccftransection where type = '"+voucherType+"' group by voucherno";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbFind.data.add(rs.getString("voucherNo"));
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void loadLedgerName() {
		try {
			cmbDebitLedger.data.clear();
			cmbCreditLedger.data.clear();
			sql = "select ledgerTitle  from tbaccfledger ";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {

				cmbDebitLedger.data.add(rs.getString("ledgerTitle"));
				cmbCreditLedger.data.add(rs.getString("ledgerTitle"));
			}
			cmbCreditLedger.setValue("Cash");
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void loadMaxVoucherNo() {
		// TODO Auto-generated method stub
		try {
			sql = "select isnull(max(voucherno),0)+1 maxVoucherNo from tbAccftransection where type = '"+voucherType+"' ";

			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtVoucherNo(rs.getString("maxVoucherNo"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void focusMoveAction() {
		Control[] control =  {date,cmbDebitLedger,cmbCreditLedger,txtAmount,btnAdd};
		new FocusMoveByEnter(control);

		Control[] control2 =  {txtPaidTo,txtNarration,btnSave};
		new FocusMoveByEnter(control2);
	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbDebitLedger.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbDebitLedger);
		});
		cmbCreditLedger.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCreditLedger);
		});
		/*cmbItemName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	        selectTextIfFocused(cmbItemName);
	    });*/

		txtAmount.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtAmount);
		});
		txtPaidTo.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtPaidTo);
		});
		txtNarration.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtNarration);
		});
		txtTotalPayableAmount.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtTotalPayableAmount);
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

	private void cmpAction() {
		checkSelectAll.setOnAction(e->{
			if(checkSelectAll.isSelected()) {
				for(int i=0;i<listInvoiceList.size();i++) {
					listInvoiceList.get(i).setCheckSelect(true);

				}
			}else {
				for(int i=0;i<listInvoiceList.size();i++) {		
					listInvoiceList.get(i).setCheckSelect(false);
				}
			}
			tableInvoiceList.setItems(listInvoiceList);

			double total = 0;

			for(int i = 0;i<listInvoiceList.size();i++) {
				if(listInvoiceList.get(i).idCheckSelect()) {
					total += listInvoiceList.get(i).getDueAmount();
				}
			}
			setTxtTotalPayableAmount(total);
		});

		cmbDebitLedger.valueProperty().addListener(new ChangeListener<String>() {
			@Override 
			public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					loadDueInvoiceBySupplierLedger(newValue);

				}
			}    
		});
		
		btnAdd.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnAddAction(null); });
		
		btnSave.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnAddAction(null); });
	}

	public String transectionAutoId() {
		String id="";
		try {
			String sql="select isnull(max(transectionid),0)+1 as autoid from tbaccfTransection";
			ResultSet rs=databaseHandler.execQuery(sql);
			while(rs.next()) {
				id=rs.getString("autoid");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return id;
	}
	private void loadDueInvoiceBySupplierLedger(String ledgerName) {
		// TODO Auto-generated method stub
		try {
			listInvoiceList.clear();
			tableInvoiceList.setItems(listInvoiceList);

			sql = "select l.ledgerTitle,s.ID,s.SupplierName from tbAccfledger l\r\n" + 
					"join tbSupplier s\r\n" + 
					"on l.ledgerId = s.ledgerId where ledgerTitle = '"+ledgerName+"'";
			ResultSet rs = databaseHandler.execQuery(sql);

			if(rs.next()) {
				sql = "select invoice,totalamount,netAmount,paid,due,cash,Card,cashId,cardId from tbInvoice where SellerCustomerID='"+rs.getString("Id")+"' and due>0";
				ResultSet rs2 = databaseHandler.execQuery(sql);
				while(rs2.next()) {
					InvoiceList tempInvoice = new InvoiceList(rs2.getString("invoice"), rs2.getDouble("totalamount"), rs2.getDouble("netAmount"), rs2.getDouble("paid"), rs2.getDouble("due"), rs2.getDouble("cash"), rs2.getDouble("card"), rs2.getString("cashid"), rs2.getString("cardId"), false);

					tempInvoice.getCheckSelect().setOnAction(e->{
						double total = 0;
						for(int i = 0;i<listInvoiceList.size();i++) {
							if(listInvoiceList.get(i).idCheckSelect()) {
								total += listInvoiceList.get(i).getDueAmount();
							}
						}
						setTxtTotalPayableAmount(total);
					});

					listInvoiceList.add(tempInvoice);
				}	
			}
			tableInvoiceList.setItems(listInvoiceList);

			if(listInvoiceList.size()>0) {
				setCheckInvoicePayement(true);
				list.clear();
			}else {
				setCheckInvoicePayement(false);
			}

			table.setItems(list);
			setTxtTotalPayableAmount("0");

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void cmpAdd() {
		// TODO Auto-generated method stub

		cmbDebitLedger.setPrefWidth(212);
		cmbDebitLedger.setPrefHeight(28);

		vBoxDebitLedger.getChildren().clear();
		vBoxDebitLedger.getChildren().add(cmbDebitLedger);

		cmbCreditLedger.setPrefWidth(222);
		cmbCreditLedger.setPrefHeight(28);

		vBoxCreditLedger.getChildren().clear();
		vBoxCreditLedger.getChildren().add(cmbCreditLedger);

		cmbFind.setPrefWidth(183);
		cmbFind.setPrefHeight(28);
		vBoxFind.getChildren().clear();
		vBoxFind.getChildren().add(cmbFind);
	}

	private void cmpSetData() {
		// TODO Auto-generated method stub
		date.setConverter(converter);
		date.setValue(LocalDate.now());

		map = new HashMap<>();


		transectionIdCol.setCellValueFactory(new PropertyValueFactory("transectionId"));
		voucherNoCol.setCellValueFactory(new PropertyValueFactory("voucherNo"));
		dateCol.setCellValueFactory(new PropertyValueFactory("date"));
		debitLedgerCol.setCellValueFactory(new PropertyValueFactory("debitLedger"));
		creditLedgerCol.setCellValueFactory(new PropertyValueFactory("creditLedger"));
		amountCol.setCellValueFactory(new PropertyValueFactory("amount"));
		deleteCol.setCellValueFactory(new PropertyValueFactory("delete"));
		table.setColumnResizePolicy(table.CONSTRAINED_RESIZE_POLICY);

		invoiceNoCol.setCellValueFactory(new PropertyValueFactory("invoiceNo"));
		totalAmountCol.setCellValueFactory(new PropertyValueFactory("totalAmount"));
		netAmountCol.setCellValueFactory(new PropertyValueFactory("netAmount"));
		paidAmountCol.setCellValueFactory(new PropertyValueFactory("paidAmount"));
		dueAmountCol.setCellValueFactory(new PropertyValueFactory("dueAmount"));
		isSelectCol.setCellValueFactory(new PropertyValueFactory("checkSelect"));
		tableInvoiceList.setColumnResizePolicy(tableInvoiceList.CONSTRAINED_RESIZE_POLICY);

		tableInvoiceList.setTableMenuButtonVisible(true);
		tableInvoiceList.getColumns().get(1).setVisible(false);
		tableInvoiceList.getColumns().get(3).setVisible(false);

		checkSelectAll = new CheckBox();
		isSelectCol.setGraphic(checkSelectAll);

	}

	public static class InvoiceList{


		private SimpleStringProperty invoiceNo;
		private SimpleDoubleProperty totalAmount;
		private SimpleDoubleProperty netAmount;
		private SimpleDoubleProperty paidAmount;
		private SimpleDoubleProperty dueAmount;
		private SimpleDoubleProperty cash;
		private SimpleDoubleProperty card;
		private SimpleStringProperty cashId;
		private SimpleStringProperty cardId;

		private CheckBox checkSelect;


		public InvoiceList(String invoiceNo,double totalAmount,double netAmount,double paidAmount,double dueAmount,double cashAmount,double cardAmount,String cashId,String cardId,boolean isSelect) {
			this.invoiceNo = new SimpleStringProperty(invoiceNo);
			this.totalAmount = new SimpleDoubleProperty(totalAmount);
			this.netAmount = new SimpleDoubleProperty(netAmount);
			this.paidAmount = new SimpleDoubleProperty(paidAmount);
			this.dueAmount = new SimpleDoubleProperty(dueAmount);
			this.cash = new SimpleDoubleProperty(cashAmount);
			this.card = new SimpleDoubleProperty(cardAmount);
			this.cashId = new SimpleStringProperty(cashId);
			this.cardId = new SimpleStringProperty(cardId);
			checkSelect = new CheckBox();
			checkSelect.setSelected(isSelect);

		}



		public String getInvoiceNo() {
			return invoiceNo.get();
		}

		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = new SimpleStringProperty(invoiceNo);
		}

		public double getTotalAmount() {
			return totalAmount.get();
		}

		public void setTotalAmount(double totalAmount) {
			this.totalAmount = new SimpleDoubleProperty(totalAmount);
		}


		public double getNetAmount() {
			return netAmount.get();
		}

		public void setNetAmount(double netAmount) {
			this.netAmount = new SimpleDoubleProperty(netAmount);
		}


		public double getPaidAmount() {
			return paidAmount.get();
		}

		public void setPaidAmount(double paidAmount) {
			this.paidAmount = new SimpleDoubleProperty(paidAmount);
		}


		public double getDueAmount() {
			return dueAmount.get();
		}

		public void setDueAmount(double dueAmount) {
			this.dueAmount = new SimpleDoubleProperty(dueAmount);
		}

		public double getCash() {
			return cash.get();
		}

		public void setCash(double cash) {
			this.cash = new SimpleDoubleProperty(cash);
		}

		public double getCard() {
			return card.get();
		}



		public void setCard(double card) {
			this.card = new SimpleDoubleProperty(card);
		}



		public String getCashId() {
			return cashId.get();
		}



		public void setCashId(String cashId) {
			this.cashId = new SimpleStringProperty(cashId);
		}



		public String getCardId() {
			return cardId.get();
		}



		public void setCardId(SimpleStringProperty cardId) {
			this.cardId = cardId;
		}



		public CheckBox getCheckSelect() {
			return checkSelect;
		}

		public boolean idCheckSelect() {
			return checkSelect.isSelected();
		}

		public void setCheckSelect(boolean checkSelect) {
			this.checkSelect.setSelected(checkSelect);
		}

	}
	public static class VoucherDetails{


		private SimpleStringProperty transectionId;
		private SimpleStringProperty voucherNo;
		private SimpleStringProperty date;
		private SimpleStringProperty debitLedger;
		private SimpleStringProperty creditLedger;
		private SimpleStringProperty amount;
		private SimpleStringProperty delete;


		public VoucherDetails(String transectionId,String voucherNo,String date,String debitLedger,String creditLedger,String amount,String delete) {
			this.transectionId = new SimpleStringProperty(transectionId);
			this.voucherNo = new SimpleStringProperty(voucherNo);
			this.date = new SimpleStringProperty(date);
			this.debitLedger = new SimpleStringProperty(debitLedger);
			this.creditLedger = new SimpleStringProperty(creditLedger);
			this.amount = new SimpleStringProperty(amount);
			this.delete = new SimpleStringProperty(delete);

		}

		public String getTransectionId() {
			return transectionId.get();
		}

		public void setTransectionId(String transectionId) {
			this.transectionId = new SimpleStringProperty(transectionId);
		}

		public String getVoucherNo() {
			return voucherNo.get();
		}

		public void setVoucherNo(String voucherNo) {
			this.voucherNo = new SimpleStringProperty(voucherNo);
		}

		public String getDate() {
			return date.get();
		}

		public void setDate(String date) {
			this.date = new SimpleStringProperty(date);
		}

		public String getDebitLedger() {
			return debitLedger.get();
		}

		public void setDebitLedger(String debitLedger) {
			this.debitLedger = new SimpleStringProperty(debitLedger);
		}


		public String getCreditLedger() {
			return creditLedger.get();
		}

		public void setCreditLedger(String creditLedger) {
			this.creditLedger = new SimpleStringProperty(creditLedger);
		}

		public String getAmount() {
			return amount.get();
		}

		public double getAmountDouble() {
			return Double.valueOf(amount.get());
		}

		public void setAmount(String Amount) {
			this.amount = new SimpleStringProperty(Amount);
		}

		public String getDelete() {
			return delete.get();
		}

		public void setDelete(String delete) {
			this.delete = new SimpleStringProperty(delete);
		}
	}


	public String getTxtVoucherNo() {
		return txtVoucherNo.getText().trim();
	}


	public void setTxtVoucherNo(String txtVoucherNo) {
		this.txtVoucherNo.setText(txtVoucherNo);
	}


	public String getTxtAmount() {
		return txtAmount.getText().trim();
	}


	public void setTxtAmount(String txtAmount) {
		this.txtAmount.setText(txtAmount);
	}


	public String getTxtTotalPayableAmount() {
		if(txtTotalPayableAmount.getText().isEmpty()) {
			return "0";
		}
		return txtTotalPayableAmount.getText().trim();
	}


	public void setTxtTotalPayableAmount(String txtTotalPayableAmount) {
		this.txtTotalPayableAmount.setText(txtTotalPayableAmount);
	}

	public void setTxtTotalPayableAmount(double txtTotalPayableAmount) {
		this.txtTotalPayableAmount.setText(df.format(txtTotalPayableAmount));
	}

	public String getTxtPaidTo() {
		return txtPaidTo.getText().trim();
	}

	public void setTxtPaidTo(String txtPaidTo) {
		this.txtPaidTo.setText(txtPaidTo);
	}



	public String getTxtNarration() {
		return txtNarration.getText().trim();
	}


	public void setTxtNarration(String txtNarration) {
		this.txtNarration.setText(txtNarration);
	}


	public String getCmbFind() {
		if(cmbFind.getValue() != null)
			return cmbFind.getValue().toString().trim();
		else return "";
	}


	public void setCmbFind(String cmbFind) {
		this.cmbFind.setValue(cmbFind);
	}


	public String getCmbDebitLedger() {
		if(cmbDebitLedger.getValue() != null)
			return cmbDebitLedger.getValue().toString().trim();
		else return "";
	}


	public void setCmbDebitLedger(String cmbDebitLedger) {
		this.cmbDebitLedger.setValue(cmbDebitLedger);
	}


	public String getCmbCreditLedger() {
		if(cmbCreditLedger.getValue() != null)
			return cmbCreditLedger.getValue().toString().trim();
		else return "";
	}


	public void setCmbCreditLedger(String cmbCreditLedger) {
		this.cmbCreditLedger.setValue(cmbCreditLedger);
	}


	public String getDate() {
		return date.getValue().toString();
	}


	public boolean getCheckInvoicePayement() {
		return checkInvoicePayement.isSelected();
	}

	public void setCheckInvoicePayement(boolean checkInvoicePayement) {
		this.checkInvoicePayement.setSelected(checkInvoicePayement);
	}

	public boolean getCheckSelectAll() {
		return checkSelectAll.isSelected();
	}

	public void setCheckSelectAll(boolean checkSelectAll) {
		this.checkSelectAll.setSelected(checkSelectAll);
	}

	public void setDate(Date date) {
		if(date != null) {
			int dd=Integer.valueOf(new SimpleDateFormat("dd").format(date));
			int mm=Integer.valueOf(new SimpleDateFormat("MM").format(date));
			int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(date));
			this.date.setValue(LocalDate.of(yy,mm,dd));
		}else {
			this.date.getEditor().setText("");
		}
	}


}

package ui.accounts;

import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import shareClasses.Notification;
import shareClasses.SessionBeam;
import shareClasses.FxComboBox;;

public  class BankPaymentControllenr implements Initializable{

	


		@FXML
		TextField txtVoucherNo;
		@FXML
		TextField txtAmount;
		@FXML
		TextField txtPaidTo;
		@FXML
		TextArea txtDescription;
		@FXML
		TextField txtCheckNo;

		@FXML
		VBox vBoxFind;
		@FXML
		VBox vBoxDebitLedger;
		@FXML
		VBox vBoxCreditLedger;
		@FXML
		VBox vBoxTransectionType;

		FxComboBox cmbFind = new FxComboBox<>();
		FxComboBox cmbDebitLedger = new FxComboBox<>();
		FxComboBox cmbCreditLedger = new FxComboBox<>();
		FxComboBox cmbTransectionType = new FxComboBox<>();

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
		DatePicker date;
		@FXML
		DatePicker checkDate;


		@FXML
		private TableView<VoucherDetails> table;

		ObservableList<VoucherDetails> list = FXCollections.observableArrayList();

		
		@FXML
		private TableColumn<VoucherDetails, String> voucherNoCol;
		@FXML
		private TableColumn<VoucherDetails, String> dateCol;
		@FXML
		private TableColumn<VoucherDetails, String> bankLedgerCol;
		@FXML
		private TableColumn<VoucherDetails, String> creditLedgerCol;
		@FXML
		private TableColumn<VoucherDetails, String> checkNoCol;
		@FXML
		private TableColumn<VoucherDetails, String> amountCol;
		@FXML
		private TableColumn<VoucherDetails, String> deleteCol;


		private DecimalFormat df = new DecimalFormat("#0.00");

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

		String voucherType="13";	


		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			databaseHandler = DatabaseHandler.getInstance();
			cmpAdd();
			cmpSetData();
			loadMaxVoucherNo();
			loadLedgerName();
			loadCmbFind();
			loadTableData();
			focusMoveAction();

		}

	/*
	 * @FXML private void btnAddAction(ActionEvent event) { if(!isVoucherNoExist())
	 * { if(ledgerAddValidationCheck()) { if(doubleCreditLedgerAndDateCheck()) {
	 * 
	 * list.add(new VoucherDetails("", getTxtVoucherNo(), getDate(),
	 * getCmbDebitLedger(),getCmbCreditLedger(),getTxtAmount(),"Del"));
	 * table.setItems(list); cmbDebitLedger.requestFocus(); }else { new
	 * Notification(Pos.TOP_CENTER, "Warning graphic",
	 * "Warning!","You Can't Entry Multiple Credit Ledger And Date In a Debit Voucher.\nPlease Enter Same Credit Ledger and Date..."
	 * ); cmbCreditLedger.requestFocus(); } } }else { new
	 * Notification(Pos.TOP_CENTER, "Warning graphic",
	 * "Warning!","This Voucher No Allready Exist. \nPlease Set a new Voucher No..."
	 * ); btnRefresh.requestFocus(); } }
	 */
		@FXML
		private void editAction(ActionEvent event) {
			try {
				if(isVoucherNoExist()) {
					if(validationCheck()) {
						if(confrimationCheck("Edit")) {
							
								sql = "update tbAccftransection set PaidTo= '"+getTxtPaidTo()+"' ,description = '"+getTxtDescription()+"',transectionType='"+getCmbTransectionType()+"',chequeNo='"+getTxtChequeNo()+"',chequeDate='"+getCheckDate()+"' ,entryTime = CURRENT_TIMESTAMP ,createBy = '"+SessionBeam.getUserId()+"' where voucherNo = '"+getTxtVoucherNo()+"' and type = '"+voucherType+"';";
								
								databaseHandler.execAction(sql);
								
								new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Voucher Edit Successfully...");
								printAction(null);
								refreshAction(null);
							
						}
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
					if(validationCheck()) {
						if(confrimationCheck("Save")) {
								sql = "insert into tbAccftransection (transectionid,voucherNo,type,Status,unitId,depId,d_l_id,c_l_id,"
										+ "amount,groupId,description,PaidTo,date,transectionType,chequeNo,chequeDate,entryTime,createBy) " + 
										"values('"+getNewTransectionId()+"','"+getTxtVoucherNo()+"','"+voucherType+"','Bank Payement','1','1',"
										+ "'"+getLedgerId(getCmbDebitLedger())+"',"
										+ "'"+getLedgerId(getCmbCreditLedger())+"','"+getTxtAmount()+"',"
										+ "'1','"+getTxtDescription()+"','"+getTxtPaidTo()+"','"+getDate()+"','"+getCmbTransectionType()+"','"+getTxtChequeNo()+"','"+getCheckDate()+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
								
								databaseHandler.execAction(sql);
							
							new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Voucher Save Successfully...");
							printAction(null);
							refreshAction(null);
						}
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
			loadTableData();
			loadCmbFind();
			setDate(new Date());
			setCmbDebitLedger("");
			setCmbCreditLedger("");
			setTxtAmount("0");
			setTxtPaidTo("");
			setTxtDescription("");
			setTxtCheckNo("");
			setCmbTrasectionType("");
			
			
		}
		
		@FXML
		private void printAction(ActionEvent event) {
			// TODO Auto-generated method stub
			try {
				if(isVoucherNoExist()) {
					sql=" select voucherNo,(select ledgerTitle from tbAccfledger where ledgerId=t.c_l_id ) as creditLedger,(select ledgerTitle from tbAccfledger where ledgerId=t.d_l_id ) as debitLedger,amount,description,PaidTo ,date,transectionType,chequeNo,chequeDate, dbo.number(amount) as Taka from tbAccftransection t where voucherNo = '"+getTxtVoucherNo()+"' and type = '"+voucherType+"'";
					System.out.println(sql);
					String report="src/resource/reports/Invoices/BankPaymentVoucher.jrxml";
					JasperDesign jd=JRXmlLoader.load(report);
					JRDesignQuery jq=new JRDesignQuery();
					jq.setText(sql);
					jd.setQuery(jq);
					JasperReport jr=JasperCompileManager.compileReport(jd);
					JasperPrint jp=JasperFillManager.fillReport(jr, null,databaseHandler.conn);
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
				sql = "select transectionid,date,voucherNo,amount,(select ledgertitle from tbAccfledger where ledgerId = d_l_id) as debidLedger,(select ledgertitle from tbAccfledger where ledgerId = c_l_id) as creditLedger,description,PaidTo,date,t.chequeNo,t.chequeDate,t.transectionType from tbAccftransection t where voucherNo = '"+getCmbFind()+"' and type='"+voucherType+"';";
				System.out.println(sql);
				ResultSet rs = databaseHandler.execQuery(sql);
				while(rs.next()) {
					setTxtVoucherNo(rs.getString("voucherNo"));
					setTxtAmount(df.format(rs.getDouble("amount")));;
					setTxtPaidTo(rs.getString("PaidTo"));
					setTxtDescription(rs.getString("description"));
					setCheckDate(rs.getDate("chequeDate"));
					setTxtCheckNo(rs.getString("chequeNo"));
					setCmbDebitLedger(rs.getString("debidLedger"));
					setCmbCreditLedger(rs.getString("creditLedger"));
					setCmbTrasectionType(rs.getString("transectionType"));
					
				}
				
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

		@FXML
		private void tableClickAction() {
			if(table.getSelectionModel().getSelectedItem() != null) {
				TablePosition firstCell = table.getSelectionModel().getSelectedCells().get(0);
				VoucherDetails tempVoucher = table.getSelectionModel().getSelectedItem();
				if(firstCell.getColumn()==6) {
					list.remove(tempVoucher);
					table.setItems(list);
					
				}else {
					setCmbFind(tempVoucher.getVoucherNo());
					findAction(null);
				}

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



		private boolean validationCheck() {
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
									new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Your Credit Ladger is Invalid.\nPlease Select a Valid Credit Ledger...");
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
		
		private void loadTableData() {
			try {
				sql = "select transectionid,voucherNo,amount,(select ledgertitle from tbAccfledger where ledgerId = d_l_id) as debidLedger,(select ledgertitle from tbAccfledger where ledgerId = c_l_id) as creditLedger,chequeNo,description,PaidTo,date from tbAccftransection where type='"+voucherType+"' order by date desc;";
				
				ResultSet rs = databaseHandler.execQuery(sql);
				list.clear();
				while(rs.next()) {
					
					list.add(new VoucherDetails(rs.getString("voucherNo"), rs.getString("date"), rs.getString("debidLedger"),rs.getString("chequeNo"),rs.getString("creditLedger"),df.format(rs.getDouble("amount")),"Del"));
				}
				table.setItems(list);
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
				//cmbCreditLedger.setValue("Cash");
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
		private void loadBankLedgerName() {
			try {
				cmbDebitLedger.data.clear();
				cmbCreditLedger.data.clear();
				sql = "select ledgerTitle  from tbaccfledger ";
				System.out.println(sql);
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
		
		private void loadCmbFind() {
			try {
				cmbFind.data.clear();
				sql = "select voucherno from tbAccftransection where type = '"+voucherType+"' group by voucherno";
				
				ResultSet rs = databaseHandler.execQuery(sql);
				while(rs.next()) {

					cmbFind.data.add(rs.getString("voucherno"));
					
				}
				
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
			Control[] control =  {date,cmbDebitLedger,cmbCreditLedger,cmbTransectionType,txtPaidTo,checkDate,txtCheckNo,txtAmount,txtDescription,btnSave};
			new FocusMoveByEnter(control);

			
		}
		private void cmpAdd() {
			// TODO Auto-generated method stub

			cmbDebitLedger.setPrefWidth(230);
			cmbDebitLedger.setPrefHeight(28);

			vBoxDebitLedger.getChildren().clear();
			vBoxDebitLedger.getChildren().add(cmbDebitLedger);

			cmbCreditLedger.setPrefWidth(230);
			cmbCreditLedger.setPrefHeight(28);

			vBoxCreditLedger.getChildren().clear();
			vBoxCreditLedger.getChildren().add(cmbCreditLedger);
			
			cmbTransectionType.setPrefWidth(230);
			cmbTransectionType.setPrefHeight(28);

			vBoxTransectionType.getChildren().clear();
			vBoxTransectionType.getChildren().add(cmbTransectionType);

			cmbFind.setPrefWidth(183);
			cmbFind.setPrefHeight(28);
			vBoxFind.getChildren().clear();
			vBoxFind.getChildren().add(cmbFind);
		}

		private void cmpSetData() {
			// TODO Auto-generated method stub
			date.setConverter(converter);
			checkDate.setConverter(converter);
			date.setValue(LocalDate.now());



			
			voucherNoCol.setCellValueFactory(new PropertyValueFactory("voucherNo"));
			dateCol.setCellValueFactory(new PropertyValueFactory("date"));
			bankLedgerCol.setCellValueFactory(new PropertyValueFactory("bankLedger"));
			creditLedgerCol.setCellValueFactory(new PropertyValueFactory("creditLedger"));
			checkNoCol.setCellValueFactory(new PropertyValueFactory("checkNo"));
			amountCol.setCellValueFactory(new PropertyValueFactory("amount"));
			deleteCol.setCellValueFactory(new PropertyValueFactory("delete"));


		}
		public static class VoucherDetails{


			
			private SimpleStringProperty voucherNo;
			private SimpleStringProperty date;
			private SimpleStringProperty bankLedger;
			private SimpleStringProperty creditLedger;
			private SimpleStringProperty checkNo;
			private SimpleStringProperty amount;
			private SimpleStringProperty delete;


			public VoucherDetails(String voucherNo,String date,String bankLedger,String creditLedger,String checkNo,String amount,String delete) {
				
				this.voucherNo = new SimpleStringProperty(voucherNo);
				this.date = new SimpleStringProperty(date);
				this.bankLedger = new SimpleStringProperty(bankLedger);
				this.creditLedger = new SimpleStringProperty(creditLedger);
				this.checkNo = new SimpleStringProperty(checkNo);
				this.amount = new SimpleStringProperty(amount);
				this.delete = new SimpleStringProperty(delete);

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

			public String getBankLedger() {
				return bankLedger.get();
			}

			public void setBankLedger(String bankLedger) {
				this.bankLedger = new SimpleStringProperty(bankLedger);
			}


			public String getCreditLedger() {
				return creditLedger.get();
			}

			public void setCreditLedger(String creditLedger) {
				this.creditLedger = new SimpleStringProperty(creditLedger);
			}

			public String getCheckNo() {
				return checkNo.get();
			}

			public void setCheckNo(String checkNo) {
				this.checkNo = new SimpleStringProperty(checkNo);
			}
			
			public String getAmount() {
				return amount.get();
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


		public String getTxtPaidTo() {
			return txtPaidTo.getText().trim();
		}


		public void setTxtPaidTo(String txtPaidTo) {
			this.txtPaidTo.setText(txtPaidTo);
		}

		public String getTxtChequeNo() {
			return txtCheckNo.getText().trim();
		}


		public void setTxtCheckNo(String txtPaidTo) {
			this.txtCheckNo.setText(txtPaidTo);
		}

		public String getTxtDescription() {
			return txtDescription.getText().trim();
		}


		public void setTxtDescription(String txtNarration) {
			this.txtDescription.setText(txtNarration);
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
		
		public String getCmbTransectionType() {
			if(cmbTransectionType.getValue() != null)
				return cmbTransectionType.getValue().toString().trim();
			else return "";
		}


		public void setCmbTrasectionType(String cmbCreditLedger) {
			this.cmbTransectionType.setValue(cmbCreditLedger);
		}



		public String getDate() {
			return date.getValue().toString();
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

		public String getCheckDate() {
			return checkDate.getValue().toString();
		}


		public void setCheckDate(Date date) {
			if(date != null) {
				int dd=Integer.valueOf(new SimpleDateFormat("dd").format(date));
				int mm=Integer.valueOf(new SimpleDateFormat("MM").format(date));
				int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(date));
				this.checkDate.setValue(LocalDate.of(yy,mm,dd));
			}else {
				this.checkDate.getEditor().setText("");
			}
		}


}

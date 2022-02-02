package ui.trading;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;



import java.net.URL;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.joda.time.DateTimeComparator;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseEvent;
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
import shareClasses.NumberField;
import shareClasses.SessionBeam;
import ui.trading.SalesController.InvoiceList;





public class PurchaseReturnController implements Initializable{

	@FXML
	TextField txtInvoiceNO;
	@FXML
	TextField txtQuantity;
	@FXML
	TextField txtPrice;
	@FXML
	TextField txtDiscount;
	@FXML
	TextField txtTotalAmount;
	@FXML
	TextField txtVat;
	@FXML
	TextField txtGrossAmount;
	@FXML
	TextField txtDiscountPercent;
	@FXML
	TextField txtPercentDiscount;
	@FXML
	TextField txtManualDiscount;
	@FXML
	TextField txtTotalDiscount;
	@FXML
	TextField txtNetAmount;
	@FXML
	TextField txtPaidAmount;
	@FXML
	TextField txtCheckNo;
	@FXML
	TextField txtDueCheckNo;
	@FXML
	TextField txtDueAmount;
	


	@FXML
	Button btnFind;
	@FXML
	Button btnAdd;
	@FXML
	Button btnSave;
	@FXML
	Button btnEdit;
	@FXML
	Button btnPrint;
	@FXML
	Button btnRefresh;
	@FXML
	Button btnPay;

	@FXML
	DatePicker date;
	/*@FXML
	DatePicker dateWarrenty;*/
	@FXML
	DatePicker datePass;
	@FXML
	DatePicker dateDuePass;
	

	@FXML
	VBox vBoxSupplier;
	@FXML
	VBox vBoxItemName;
	@FXML
	VBox vBoxRemarks;



	@FXML
	ComboBox<String> cmbUnit;
	@FXML
	ComboBox<String> cmbPayementMethod;
	@FXML
	ComboBox<String> cmbPaymentLedger;
	@FXML
	ComboBox<String> cmbDuePayementMetod;
	@FXML
	ComboBox<String> cmbDuePaymentLedger;

	FxComboBox cmbFind=new FxComboBox<>();
	FxComboBox cmbSupplier=new FxComboBox<>();
	FxComboBox cmbItemName=new FxComboBox<>();
	FxComboBox cmbRemarks = new FxComboBox<>();

	NumberField numberField = new NumberField();

	@FXML
	private TableView<PaymentDetails> tablePaymentDetails;

	ObservableList<PaymentDetails> listPaymentDetails = FXCollections.observableArrayList();

	@FXML
	private TableColumn<PaymentDetails, String> dateCol;
	@FXML
	private TableColumn<PaymentDetails, String> paymentTypeCol;
	@FXML
	private TableColumn<PaymentDetails, String> checkNoCol;
	@FXML
	private TableColumn<PaymentDetails, String> passDateCol;
	@FXML
	private TableColumn<PaymentDetails, String> amountCol;
	@FXML
	private TableColumn<PaymentDetails, String> dueCol;




	@FXML
	private TableView<ProductDetails> tableItemDetails;

	ObservableList<ProductDetails> listProductDetails = FXCollections.observableArrayList();

	@FXML
	private TableColumn<ProductDetails, String> itemIdCol;
	@FXML
	private TableColumn<ProductDetails, String> itemNameCol;
	@FXML
	private TableColumn<ProductDetails, String> unitCol;
	@FXML
	private TableColumn<ProductDetails, String> unitQuantityCol;
	@FXML
	private TableColumn<ProductDetails, String> quantityCol;
	@FXML
	private TableColumn<ProductDetails, String> unitPriceCol;
	@FXML
	private TableColumn<ProductDetails, String> priceCol;
	@FXML
	private TableColumn<ProductDetails, String> totalAmountCol;
	@FXML
	private TableColumn<ProductDetails, String> discountCol;
	@FXML
	private TableColumn<ProductDetails, String> netAmountCol;
	@FXML
	private TableColumn<ProductDetails, String> deleteCol;


	@FXML
	private TableView<InvoiceList> tableInvoiceList;

	ObservableList<InvoiceList> listInvoiceList = FXCollections.observableArrayList();

	@FXML
	private TableColumn<InvoiceList, String> invoiceNOCol;
	@FXML
	private TableColumn<InvoiceList, String> totalAmountInvoiceListCol;
	@FXML
	private TableColumn<InvoiceList, String> supplierNameCol;

	private DatabaseHandler databaseHandler;
	private String sql;

	private HashMap map;
	//this String variable for Payment Method Id
	private String paymentMethodHeadId="2";
	//this String variable for purchase type
	private String type = "2";
	//ledger Type
	private String supplierLedgerType = "1";
	private Date invoiceDate;
	
	private DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();

	Calendar calender = Calendar.getInstance();

	private DecimalFormat dfId = new DecimalFormat("#00");
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

	SimpleDateFormat simpleDateFormate= new SimpleDateFormat("yyyy-MM-dd");
	DateTimeFormatter dbDateFormate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		addCmp();
		focusMoveAction();
		setCmpData();
		setCmpAction();
		setCmpFocusAction();
		btnRefreshAction(null);
		cmbFindLoad();

	}

	@FXML
	private void btnAddAction(ActionEvent event) {
		if(addValidationCheck()) {
			//There Have Many local Variable for temporary Use
			String itemId;

			double unitQuantity=0,quantityPerUnit,quantity=0,free=0,unitPrice=0,price=0,totalAmount=0,netAmount=0,discount=0;

			unitQuantity = Double.valueOf(getTxtQuantity());


			String unit = getCmbUnit();
			quantityPerUnit = Double.valueOf(unit.substring(unit.indexOf('(')+1, unit.indexOf(')')));
			quantity = unitQuantity * quantityPerUnit;

			
			unitPrice = Double.valueOf(getTxtPrice());
			price = unitPrice/quantityPerUnit;
			totalAmount = unitPrice * unitQuantity;
			discount = Double.valueOf(getTxtDiscount());
			netAmount  = totalAmount - discount;

			itemId = getItemId(getCmbItemName());

			int rowCount = 0;
			for(rowCount = 0;rowCount < listProductDetails.size();rowCount++) {
				ProductDetails  tempPd= listProductDetails.get(rowCount);
				if(tempPd.getItemId().equals(itemId)) {

					listProductDetails.remove(rowCount);
					listProductDetails.add(rowCount,new ProductDetails(itemId, getCmbItemName(), unit, unitQuantity, quantity, unitPrice, unitPrice, totalAmount, discount, netAmount, "Del"));
					clearTxtAfterItemAdd();


					break;
				}
			}
			if(rowCount == listProductDetails.size()) {

				listProductDetails.add(new ProductDetails(itemId, getCmbItemName(), unit, unitQuantity, quantity, unitPrice, unitPrice, totalAmount, discount, netAmount, "Del"));
				clearTxtAfterItemAdd();

			}


			tableItemDetails.setItems(listProductDetails);
			netAmountCount();
		}
	}



	@FXML
	private void btnSaveAction(ActionEvent event) {
		try {
			if(!isInvoiceIdExist(getTxtInvoiceNO())) {
				if(saveValidaionCheck()) {
					if(confirmationCheck("Save")) {

						String commonTransection="";
						String cashTransection="";
						String cardTransection="";
						String discountTransection="";
						String vatTransection="";

						String payType = "Purchase Return(New Pay)";
						String supplierid = getSupplierId();

						String supplierledgerId= getSupplierLedgerId(getCmbSupplier());
						String paymentLedgerId= getPaymentLedgerId(getCmbPaymentLedger());

						String purchaseReturnLedger = "7";
						String purchaseReturnDiscountLedger = "12";
						String purchaseReturnVatLedger = "82";
						String d_l_id;
						String c_l_id;
						String cash="0";
						String card="0";
						String invoiceAutoNo = getMaxInvoiceAutoNo();
						double due = ( Double.valueOf(getTxtNetAmount())-Double.valueOf(getTxtPaidAmount()));
						double vatAmount= Double.valueOf(getTxtGrossAmount())-Double.valueOf(getTxtTotalAmount());
						maxInvoiceNoSet();
						if(getCmbPayementMethod().equals("Cash")) {
							cash = txtPaidAmount.getText();
						}else {
							card = txtPaidAmount.getText();
						}


						if(Double.valueOf(getTxtTotalAmount())>0) {
							commonTransection = transectionAutoId();
							d_l_id = supplierledgerId;
							c_l_id = purchaseReturnLedger;
							sql ="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) "
									+ "values('"+transectionAutoId()+"','"+invoiceAutoNo+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+getTxtTotalAmount()+"','Purchase Return','"+dbDateFormate.format(date.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(sql);

							/*sql="update tbInvoice set transectionId='"+commonTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"'";
							databaseHandler.execAction(sql);*/

						}


						if(vatAmount>0) {
							vatTransection=transectionAutoId();
							d_l_id = supplierledgerId;
							c_l_id = purchaseReturnVatLedger;
							String accTransection2="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoNo+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+vatAmount+"','Purchase Return Vat','"+dbDateFormate.format(date.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(accTransection2);


							/*sql="update tbInvoice set vatId='"+discountTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"' ";
							databaseHandler.execAction(sql);*/
						}

						if (Double.parseDouble(getTxtTotalDiscount())>0) {
							discountTransection=transectionAutoId();
							d_l_id = purchaseReturnDiscountLedger;
							c_l_id = supplierledgerId;
							String accTransection2="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoNo+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+txtTotalDiscount.getText()+"','Purchase Return Discount','"+dbDateFormate.format(date.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(accTransection2);


							/*sql="update tbInvoice set discountid='"+discountTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"' ";
							databaseHandler.execAction(sql);*/

						}

						if (Double.parseDouble(txtPaidAmount.getText()) >= 0) {

							if(getCmbPayementMethod().equals("Cash")) {
								String Payment = "insert into tbpaymentHistory (invoiceNo,cash, card,due,depID,type,date,paymentType,flag,entryTime,entryBy) values ( '" + invoiceAutoNo + "', '" + txtPaidAmount.getText() + "','0','" + due + "','" + 1 + "','" + type + "','" + dbDateFormate.format(date.getValue()) + "','"+getCmbPayementMethod()+"','" + payType + "',CURRENT_TIMESTAMP, '" + SessionBeam.getUserId() + "' )";
								databaseHandler.execAction(Payment);
								cashTransection=transectionAutoId();
								d_l_id = paymentLedgerId;
								c_l_id = supplierledgerId;
								sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoNo+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+txtPaidAmount.getText()+"','Cash Purchase Return','"+dbDateFormate.format(date.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbInvoice set cashid='"+cashTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"'";
								databaseHandler.execAction(sql);*/
							}else {
								String checkId = getMaxCheckId();
								if(getCmbPayementMethod().equals("Check")) {
									sql = " insert into tbcheckinformation (id,invoiceId,invoiceType,checkType,transactionId,ledgerId,checkNo,Amount,passDate,confirmDate,postingDate,entryTime,entryBy) "
											+ "values('"+checkId+"','"+invoiceAutoNo+"','"+type+"','1','"+transectionAutoId()+"','"+paymentLedgerId+"','"+txtCheckNo.getText()+"',"+txtPaidAmount.getText()+",'"+dbDateFormate.format(date.getValue())+"','','"+dbDateFormate.format(date.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
									databaseHandler.execAction(sql);
								}
								String Payment = "insert into tbpaymentHistory (invoiceNo,cash, card,due,depID,type,date,paymentType,checkId,flag,entryTime,entryBy) "
										+ "values ( '" + invoiceAutoNo + "', '0','" + txtPaidAmount.getText() + "','" + due + "','" + 1 + "','" + type + "','" + dbDateFormate.format(date.getValue()) + "','"+getCmbPayementMethod()+"','" + checkId + "','"+payType+"',CURRENT_TIMESTAMP, '" + SessionBeam.getUserId() + "' )";
								databaseHandler.execAction(Payment);

								cardTransection=transectionAutoId();
								d_l_id = paymentLedgerId;
								c_l_id = supplierledgerId;
								sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoNo+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+txtPaidAmount.getText()+"','Card Purchase Return','"+dbDateFormate.format(date.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbInvoice set cardid='"+cardTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"'";
								databaseHandler.execAction(sql);*/
							}

						}


						String sql = "insert into tbInvoice(invoice,"
								+ " tradingDate, "
								+ "SellerCustomerID, "
								+ "SellerCustomerName, "
								+ "totalAmount,"
								+ " vat, "
								+ "vatAmount,"
								+ " discount,"
								+ " percentDiscount,"
								+ " ManualDiscount,"
								+ "totalDiscount,"
								+ " netAmount,"
								+ "paid,"
								+ " cash,"
								+" card,"
								+ "due, "
								+ "type,"
								+ "Date,"
								+ "transectionID,"
								+ "costID,"
								+ "cashId,"
								+ "cardId,"
								+ "vatId,"
								+ "discountID,"
								+ "paytype, "
								+ "remarks, "
								+ "entryTime, "
								+ "entryBy) values('" + getTxtInvoiceNO() + "', "
								+ "'" + dbDateFormate.format(date.getValue()) + "',"
								+ " '" + supplierid + "', "
								+ "'" + getCmbSupplier() + "',"
								+ "'" + getTxtTotalAmount() + "',"
								+ "'" + getTxtVat() + "',"
								+ "'" + vatAmount+ "',"
								+ "'" + getTxtDiscountPercent() + "',"
								+ "'" + getTxtPercentDiscount() + "',"
								+ "'" + getTxtManualDiscount() + "',"
								+ "'" + getTxtTotalDiscount() + "',"
								+ "'" + getTxtNetAmount() + "',"
								+ "'" + getTxtPaidAmount() + "',"
								+ "'" + cash + "', "
								+ "'" + card + "', "
								+ " '" + due + "',"
								+ "'" + type + "',"
								+ "'" + dbDateFormate.format(date.getValue()) + "',"
								+ "'"+commonTransection+"',"
								+ "'','"+cashTransection+"',"
								+ "'"+cardTransection+"',"
								+ "'"+vatTransection+"','"+discountTransection+"',"
								+ "'" + getCmbPayementMethod() + "',"
								+ "'" + getCmbRemarks() + "',"
								+ "CURRENT_TIMESTAMP, "
								+ "'" + SessionBeam.getUserId() + "')";

						databaseHandler.execAction(sql);

						invoiceAutoNo = getAutoInvoiceNo(getTxtInvoiceNO());

						for(int i=0;i<listProductDetails.size();i++) {
							ProductDetails temPd = listProductDetails.get(i);

							sql = "insert into tbStore ("
									+ "invoiceAutoNo,"
									+ "InvoiceNo,"
									+ " itemId,"
									+ " itemName,"
									+ "isHaveImei,"
									+ "dLid,"
									+ "cLid,"
									+ "unit,"
									+ " unitqty,"
									+ " qty,"
									+ "isHaveFree,"
									+ "freeQty,"
									+ "freeitemprice, "
									+ "unitPrice, "
									+ " averagePrice,"
									+ "PurchasePrice,"
									+ "salesPrice,"
									+ "totalAmount,"
									+ " discount,"
									+ " averageDiscount,"
									+ "netamount,"
									+ "type,"
									+ "isHaveWarrenty,"
									+ "warrantyDate ,"
									+ "date,"
									+ "entryTime,"
									+ " entryBy )"
									+ "values('" +invoiceAutoNo  + "',"
									+ "'" + getTxtInvoiceNO() + "',"
									+ "'" + temPd.getItemId() + "',"
									+ "'" + temPd.getItemName() + "',"
									+ "'0',"
									+ "'" + supplierid + "',"
									+ "'" +  temPd.getItemId()+ "',"
									+ "'" + temPd.getUnit() + "',"
									+ "'" + temPd.getUnitQuantity() + "',"
									+ "'" + temPd.getQuantity() + "',"
									+ "'',"
									+ "'',"
									+ "'" + temPd.getPrice() + "',"
									+ "'" + temPd.getUnitPrice() + "',"	
									+ "'" +	temPd.getPrice() + "',"
									+ "'" +	temPd.getPrice() + "',"
									+ "'',"
									+ "'" + temPd.getTotalAmount() + "',"
									+ "'" + temPd.getDiscount() + "',"
									+ "'" + temPd.getDiscount()/temPd.getQuantity() + "',"
									+ "'" + temPd.getNetAmount() + "',"
									+ "'" + type + "',"
									+ "'0',"
									+ "'',"
									+ "'" + dbDateFormate.format(date.getValue()) + "',"
									+ "CURRENT_TIMESTAMP, "
									+ "'" + SessionBeam.getUserId() + "' )";

							databaseHandler.execAction(sql);


						}
						new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfull....!","Purchase Return Invoice Save Successfully...");
						txtQuantity.requestFocus();
						btnPrintAction(null);
						//btnRefreshAction(null);
						cmbFindLoad();
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Sorry....!","You Can Not Save This Invoice Id...\nThis Invoice Allready Exist...");
				btnRefresh.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnEditAction(ActionEvent event) {
		try {
			if(isInvoiceIdExist(getTxtInvoiceNO())) {
				if(editValidaionCheck()) {
					if(confirmationCheck("Edit")) {

						sql = "delete from tbStore where invoiceNo = '"+getTxtInvoiceNO()+"' and type = '"+type+"';";
						databaseHandler.execAction(sql);

						String commonTransection="";
						String cashTransection="";
						String cardTransection="";
						String discountTransection="";
						String vatTransection="";

						String payType = "Purchase Return (New Pay)";
						String supplierid = getSupplierId();

						String supplierledgerId= getSupplierLedgerId(getCmbSupplier());
						String paymentLedgerId= getPaymentLedgerId(getCmbPaymentLedger());

						String purchaseReturnLedger = "7";
						String purchaseReturnDiscountLedger = "12";
						String purchaseReturnVatLedger = "82";
						String d_l_id;
						String c_l_id;
						String cash="0";
						String card="0";
						double due = ( Double.valueOf(getTxtNetAmount())-Double.valueOf(getTxtPaidAmount()));
						double vatAmount= Double.valueOf(getTxtGrossAmount())-Double.valueOf(getTxtTotalAmount());
						String invoiceAutoNo = getAutoInvoiceNo(getTxtInvoiceNO());
						double updateDue=0;
						sql= "select ("+getTxtNetAmount()+"-netAmount)as updateDue from tbInvoice where type = "+type+" and Invoice = "+getTxtInvoiceNO()+"";
						ResultSet rs = databaseHandler.execQuery(sql);
						if(rs.next()) {
							updateDue = rs.getDouble("updateDue");
						}

						//This Step For Updaate Supplier Ledger Id from Transection Table
						String[] cashTId=null;
						String[] cardTId=null;
						sql = "select isnull(cashid,'')as cashid,isnull(cardId,'')as cardId from tbInvoice where Invoice = "+getTxtInvoiceNO()+" and type = "+type+"";
						rs = databaseHandler.execQuery(sql);
						if(rs.next()) {
							cashTId = rs.getString("Cashid").split(",");
							cardTId = rs.getString("CardId").split(",");
						}	
						for(int i=0;i<cashTId.length;i++) {
							sql = "update tbaccftransection set c_l_id = '"+supplierledgerId+"' where transectionid = '"+cashTId[i]+"' and type = '"+type+"'";

							databaseHandler.execAction(sql);
						}
						for(int i=0;i<cardTId.length;i++) {
							sql = "update tbaccftransection set c_l_id = '"+supplierledgerId+"' where transectionid = '"+cardTId[i]+"' and type = '"+type+"'";	
							databaseHandler.execAction(sql);
						}

						//this Step For Update Total Amount
						if(Double.valueOf(getTxtTotalAmount())>0) {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoNo+"' and c_l_id='"+purchaseReturnLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								commonTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtTotalAmount()+"',d_l_id='"+supplierledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and c_l_id = '"+purchaseReturnLedger+"' and voucherNo='"+invoiceAutoNo+"'";
								databaseHandler.execAction(sql);

							}else {
								commonTransection=transectionAutoId();
								d_l_id = supplierledgerId;
								c_l_id = purchaseReturnLedger;
								sql ="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoNo+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+txtTotalAmount.getText()+"','Purchase Return','"+dbDateFormate.format(date.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbInvoice set transectionId='"+commonTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"'";
								databaseHandler.execAction(sql);*/

							}


						}


						//This Step For Discount Update
						if (Double.parseDouble(getTxtTotalDiscount())>0) {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoNo+"' and d_l_id='"+purchaseReturnDiscountLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								discountTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtTotalDiscount()+"' ,c_l_id='"+supplierledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  d_l_id = '"+purchaseReturnDiscountLedger+"' and voucherNo='"+invoiceAutoNo+"'";
								databaseHandler.execAction(sql);

							}else {
								discountTransection=transectionAutoId();
								d_l_id = purchaseReturnDiscountLedger;
								c_l_id = supplierledgerId;
								sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoNo+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+getTxtTotalDiscount()+"','Purchase Return Discount','"+dbDateFormate.format(date.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbInvoice set discountid='"+discountTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"' ";
								databaseHandler.execAction(sql);*/

							}

						}else {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoNo+"' and d_l_id='"+purchaseReturnDiscountLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								discountTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtTotalDiscount()+"' ,c_l_id='"+supplierledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  d_l_id = '"+purchaseReturnDiscountLedger+"' and voucherNo='"+invoiceAutoNo+"'";
								databaseHandler.execAction(sql);

							}
						}

						//This Step For Vat Update
						if (vatAmount>0) {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoNo+"' and c_l_id='"+purchaseReturnVatLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								vatTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+vatAmount+"' ,d_l_id='"+supplierledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  c_l_id = '"+purchaseReturnVatLedger+"' and voucherNo='"+invoiceAutoNo+"'";
								databaseHandler.execAction(sql);

							}else {
								vatTransection=transectionAutoId();
								d_l_id = supplierledgerId;
								c_l_id = purchaseReturnVatLedger;
								sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoNo+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+vatAmount+"','Purchase Return Vat','"+dbDateFormate.format(date.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbInvoice set vatId='"+vatTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"' ";
								databaseHandler.execAction(sql);*/

							}

						}else {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoNo+"' and c_l_id='"+purchaseReturnVatLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								vatTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+vatAmount+"' ,d_l_id='"+supplierledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  c_l_id = '"+purchaseReturnVatLedger+"' and voucherNo='"+invoiceAutoNo+"'";
								databaseHandler.execAction(sql);

							}
						}

						//due = Double.parseDouble(txtnetAmount.getText()) - Double.parseDouble(txtPaidAmount.getText());
						String sql = "update tbInvoice set " + 
								"tradingDate = '"+dbDateFormate.format(date.getValue())+"',\r\n" + 
								"SellerCustomerID='"+supplierid+"',\r\n" + 
								"SellerCustomerName='"+getCmbSupplier()+"', \r\n" + 
								"totalAmount='"+getTxtTotalAmount()+"',\r\n" + 
								"vat='"+getTxtVat()+"',\r\n" + 
								"vatAmount = '"+vatAmount+"',\r\n" + 
								"discount= '"+getTxtDiscountPercent()+"', \r\n" + 
								"percentDiscount='"+getTxtPercentDiscount()+"',\r\n" + 
								"ManualDiscount='"+getTxtManualDiscount()+"',\r\n" + 
								"totalDiscount='"+getTxtTotalDiscount()+"',\r\n" + 
								"netAmount='"+getTxtNetAmount()+"',\r\n" + 
								"due='"+getTxtNetAmount()+"'-paid,\r\n" + 
								"transectionID='"+commonTransection+"',\r\n" + 
								"vatId = '"+vatTransection+"', " + 
								"discountID = '"+discountTransection+"', " + 
								"Date='"+dbDateFormate.format(date.getValue())+"', " + 
								"remarks = '"+getCmbRemarks()+"', " + 
								"entryTime=CURRENT_TIMESTAMP, " + 
								"entryBy='"+SessionBeam.getUserId()+"' where invoice='"+getTxtInvoiceNO()+"' and type = '"+type+"'";
						databaseHandler.execAction(sql);
						sql = "update tbPaymentHistory set due = due+("+updateDue+") where invoiceNo ="+invoiceAutoNo+" and type = "+type+"";
						databaseHandler.execAction(sql);

						invoiceAutoNo = getAutoInvoiceNo(getTxtInvoiceNO());

						for(int i=0;i<listProductDetails.size();i++) {
							ProductDetails temPd = listProductDetails.get(i);

							sql = "insert into tbStore ("
									+ "invoiceAutoNo,"
									+ "InvoiceNo,"
									+ " itemId,"
									+ " itemName,"
									+ "isHaveImei,"
									+ "dLid,"
									+ "cLid,"
									+ "unit,"
									+ " unitqty,"
									+ " qty,"
									+ "isHaveFree,"
									+ "freeQty,"
									+ "freeitemprice, "
									+ "unitPrice, "
									+ " averagePrice,"
									+ "PurchasePrice,"
									+ "salesPrice,"
									+ "totalAmount,"
									+ " discount,"
									+ " averageDiscount,"
									+ "netamount,"
									+ "type,"
									+ "isHaveWarrenty,"
									+ "warrantyDate ,"
									+ "date,"
									+ "entryTime,"
									+ " entryBy )"
									+ "values('" +invoiceAutoNo  + "',"
									+ "'" + getTxtInvoiceNO() + "',"
									+ "'" + temPd.getItemId() + "',"
									+ "'" + temPd.getItemName() + "',"
									+ "'0',"
									+ "'" + supplierid + "',"
									+ "'" +  temPd.getItemId()+ "',"
									+ "'" + temPd.getUnit() + "',"
									+ "'" + temPd.getUnitQuantity() + "',"
									+ "'" + temPd.getQuantity() + "',"
									+ "'',"
									+ "'',"
									+ "'" + temPd.getPrice() + "',"
									+ "'" + temPd.getUnitPrice() + "',"	
									+ "'" +	temPd.getPrice() + "',"
									+ "'" +	temPd.getPrice() + "',"
									+ "'',"
									+ "'" + temPd.getTotalAmount() + "',"
									+ "'" + temPd.getDiscount() + "',"
									+ "'" + temPd.getDiscount()/temPd.getQuantity() + "',"
									+ "'" + temPd.getNetAmount() + "',"
									+ "'" + type + "',"
									+ "'0',"
									+ "'',"
									+ "'" + dbDateFormate.format(date.getValue()) + "',"
									+ "CURRENT_TIMESTAMP, "
									+ "'" + SessionBeam.getUserId() + "' )";

							databaseHandler.execAction(sql);

						}

						new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfull....!","Invoice Edit Successfully...");
						btnRefresh.requestFocus();
						btnFindAction(null);
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Sorry....!","You Can Not Edit This Invoice Id...\nThis Invoice Is Not Exist...");
				cmbFind.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnFindAction(ActionEvent event) {
		try {
			if(!getCmbFind().isEmpty()) {
				if(isInvoiceIdExist(getCmbFind())) {
					btnRefreshAction(null);
					sql = "select *,(vatAmount+totalAmount)as grossAmount from tbInvoice where Invoice='"+getCmbFind()+"' and type='"+type+"'";
					ResultSet rs = databaseHandler.execQuery(sql);
					if(rs.next()) {
						setTxtInvoiceNO(rs.getString("invoice"));
						setCmbSupplier(rs.getString("SellerCustomerName"));
						setDate(rs.getDate("date"));
						setTxtTotalAmount(rs.getDouble("totalAmount"));
						setTxtVat(rs.getString("vat"));
						setTxtGrossAmount(rs.getString("grossAmount"));
						setTxtDiscountPercent(rs.getString("discount"));
						setTxtPercentDiscount(rs.getString("percentDiscount"));	
						setTxtManualDiscount(rs.getString("manualDiscount"));
						setTxtTotalDiscount(rs.getString("totalDiscount"));
						setTxtNetAmount(rs.getString("netAmount"));
						setCmbRemarks(rs.getString("remarks"));
						
						setInvoiceDate(rs.getDate("date"));
					}

					sql = "select * from tbStore where invoiceNo = '"+getCmbFind()+"' and type = '"+type+"'";
					rs = databaseHandler.execQuery(sql);
					while(rs.next()) {
						listProductDetails.add(new ProductDetails(rs.getString("itemId"),rs.getString("itemName"), rs.getString("unit"), rs.getDouble("UnitQty"), rs.getDouble("qty"), rs.getDouble("unitPrice"), rs.getDouble("PurchasePrice"), rs.getDouble("totalamount"), rs.getDouble("discount"), rs.getDouble("netAmount"), "Del"));


					}

					tableItemDetails.setItems(listProductDetails);


					sql = "select ph.date, isnull(paymentType,'') as paymentType, isnull(ph.cash, 0) as cash, isnull(ph.card,0) as card,ISNULL(ph.due,0) as due,ISNULL(ci.checkNo,'')as CheckNo,ISNULL(ci.passDate,'') as passDate  \r\n" + 
							"from tbpaymentHistory ph \r\n" + 
							"left join tbCheckInformation ci\r\n" + 
							"on ph.checkid = ci.id\r\n" + 
							"join tbInvoice i\r\n" + 
							"on i.AutoId = ph.invoiceNo where i.Invoice='"+getCmbFind()+"' and ph.type='"+type+"'";

					rs = databaseHandler.execQuery(sql);
					listPaymentDetails.clear();
					while(rs.next()) {
						listPaymentDetails.add(new PaymentDetails(rs.getString("date"), rs.getString("paymentType"), rs.getString("checkNo"), rs.getString("CheckNo").equals("")?"":rs.getString("passDate"), rs.getDouble("Cash")+rs.getDouble("Card"), rs.getDouble("Due")));
					}

					tablePaymentDetails.setItems(listPaymentDetails);
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Sorry....!","Please Enter or Select any invoice no....");
				cmbFind.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}

	@FXML
	private void btnPrintAction(ActionEvent event) {
		try {
			if(isInvoiceIdExist(getTxtInvoiceNO())) {
				
				map.put("orgName", SessionBeam.getOrgName());
				map.put("orgAddress", SessionBeam.getOrgAddress());
				map.put("orgContact", SessionBeam.getOrgContact());
				
				String report = "src/resource/reports/Invoices/PurchaseIvoice.jrxml";
				//report="LabStatementReport/PurchaseIvoice.jrxml";
				JasperDesign jd = JRXmlLoader.load(report);
				JRDesignQuery jq = new JRDesignQuery();
				

				sql = "select pd.invoiceNo,pd.type,pd.itemId,i.Model,i.BrandName,pd.itemName,pd.itemId,isnull(pd.isHaveImei,'') as im,isnull(pd.SalesPrice,0) as salesPrice,isnull(pd.PurchasePrice,0) as purchasePrie,pd.unit,pd.qty,ISNULL(pd.totalamount,0) as ptamount,pd.discount,isnull(pd.netAmount,0) as pnetAmount,pd.entryTime,p.SellerCustomerName as supplier,ISNULL(p.totalAmount,0) as totalAmount,ISNULL(p.vat,0) as vatPercent,ISNULL(p.vatAmount,0) as vatAmount,ISNULL(p.manualDiscount,0) as ManualDiscount,ISNULL(p.totalDiscount,0) as TotalDiscount,ISNULL(p.netAmount,0) as netAmount, ISNULL(p.cash,0) as cash,ISNULL(p.card,0) as card,ISNULL(p.paid,0) as paid,p.date,l.displayName,  dbo.number(p.netAmount) as Taka   from tbInvoice p  join tbStore pd  on pd.invoiceNo = p.Invoice and pd.type = p.type  join tblogin l  on l.user_id = p.entryby join  tbItem i on i.id = pd.itemId where p.Invoice = '"+getTxtInvoiceNO()+"' and p.type = '"+type+"'";
				ResultSet rs = databaseHandler.execQuery(sql);
				int rowCount=0;
				while(rs.next()) {
					rowCount++;
				}
				
				jq.setText(sql);
				jd.setQuery(jq);
				jd.setPageHeight(185+(rowCount*10));

				JasperReport jr = JasperCompileManager.compileReport(jd);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, databaseHandler.conn);
				JasperViewer.viewReport(jp, false);
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Sorry....!","This Invoice No Not Exist.....\nFind a Exist Invoice...");
				txtQuantity.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnDuePayAction(ActionEvent event) {
		try {		
			String payType = "Purchase Return Due Payment";

			String purchaseReturnLedgerId= "7";

			String supplierledgerId= getSupplierLedgerId(getCmbSupplier());
			String paymentLedgerId= getPaymentLedgerId(getCmbDuePaymentLedger());

			String d_l_id="";
			String c_l_id="";
			String transectionId;

			String cashId = "";
			String cardId = "";
			double cash=0;
			double card = 0;
			double paidAmount = 0;
			double netAmount = 0;
			double due2 = 0;
			String invoiceAutoNo = getAutoInvoiceNo(getTxtInvoiceNO());
			sql = "select isnull(netAmount,0)as netAmount,isnull(paid,0)as paid,isnull(cash,0)as cash  ,isnull(Card,0)as Card ,isnull(due,0)as due,cashId,cardId  from tbInvoice where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"' ";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				netAmount = rs.getDouble("netAmount");
				paidAmount = rs.getDouble("paid");
				cash = rs.getDouble("cash");
				card = rs.getDouble("Card");
				due2 = rs.getDouble("Due");
				cashId = rs.getString("CashId");
				cardId = rs.getString("CardId");
			}




			if(!getTxtDueAmount().isEmpty()) {
				if(Double.valueOf(getTxtDueAmount())>0) {
					if(Double.valueOf(getTxtDueAmount())<=due2) {
						if(!getCmbDuePayementMetod().isEmpty()) {
							if(confirmationCheck("Save Due Payment In")) {
								due2 = due2 - Double.valueOf(getTxtDueAmount());
								paidAmount += Double.valueOf(getTxtDueAmount());
								if(getCmbDuePayementMetod().equals("Cash")) {
									cash += Double.valueOf(txtDueAmount.getText());

								}else {
									card += Double.valueOf(txtDueAmount.getText());
								}

								if(getCmbDuePaymentLedger().equals("Cash")) {
									sql = "insert into tbpaymentHistory (invoiceNo,cash, card,due,depID,type,date,paymentType,flag,entryTime,entryBy) values ( '" + invoiceAutoNo + "', '" + txtDueAmount.getText() + "','0','" + due2 + "','" + 1 + "','" + type + "','" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "','"+getCmbDuePayementMetod()+"','" + payType + "',CURRENT_TIMESTAMP, '" + SessionBeam.getUserId() + "' )";

									databaseHandler.execAction(sql);


									transectionId=transectionAutoId();
									d_l_id = paymentLedgerId;
									c_l_id = supplierledgerId;
									sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoNo+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+txtDueAmount.getText()+"','"+payType+"','"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";

									databaseHandler.execAction(sql);

									sql="update tbInvoice set paid="+paidAmount+",cash="+cash+",card="+card+",due="+due2+", cashid='"+cashId+","+transectionId+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"'";

									databaseHandler.execAction(sql);

									new Notification(Pos.TOP_CENTER, "Information graphic", "Successfull..!","Due Payement Successfully...");
									btnFindAction(null);

								}else {
									if(!getTxtDueCheckNo().isEmpty()) {
										if(!getDateDuePass().isEmpty()) {
											String checkId=getMaxCheckId();
											if(getCmbDuePayementMetod().equals("Check")) {

												sql = " insert into tbcheckinformation (id,invoiceId,invoiceType,checkType,transactionId,ledgerId,checkNo,Amount,passDate,confirmDate,postingDate,entryTime,entryBy) "
														+ "values('"+checkId+"','"+invoiceAutoNo+"','"+type+"','"+type+"','"+transectionAutoId()+"','"+paymentLedgerId+"','"+txtDueCheckNo.getText()+"',"+txtDueAmount.getText()+",'"+dbDateFormate.format(dateDuePass.getValue())+"','','"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";

												databaseHandler.execAction(sql);
											}
											String Payment = "insert into tbpaymentHistory (invoiceNo,cash, card,due,depID,type,date,paymentType,checkId,flag,entryTime,entryBy) values ( '" + invoiceAutoNo + "', '0','" + txtDueAmount.getText() + "','" + due2 + "','" + 1 + "','" + type + "','" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "','"+getCmbDuePayementMetod()+"','"+checkId+"','" + payType + "',CURRENT_TIMESTAMP, '" + SessionBeam.getUserId() + "' )";

											databaseHandler.execAction(Payment);

											transectionId=transectionAutoId();
											d_l_id = paymentLedgerId;
											c_l_id = supplierledgerId;
											sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoNo+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+txtDueAmount.getText()+"','"+payType+"','"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";

											databaseHandler.execAction(sql);

											sql="update tbInvoice set paid="+paidAmount+",cash="+cash+",card="+card+",due="+due2+",cardid='"+cardId+","+transectionId+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"'";
											databaseHandler.execAction(sql);

											new Notification(Pos.TOP_CENTER, "Information graphic", "Successfull..!","Due Payement Successfully...");
											btnFindAction(null);
										}else {
											new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Pass Date...");
											dateDuePass.requestFocus();
										}
									}else {
										new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Check No...");
										txtDueCheckNo.requestFocus();
									}
								}
							}

						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select Due Payement Method...");
							cmbDuePayementMetod.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due Payment Amount Exist Due Amount...");
						txtDueAmount.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Due Amount Must be more than 0...");
					txtDueAmount.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Due Amount...");
				txtDueAmount.requestFocus();
			}


		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	@FXML
	private void btnRefreshAction(ActionEvent event) {
		supplierLoad();
		maxInvoiceNoSet();
		paymentMethodLoad();
		productNameLoad();
		remarksLoad();
		invoiceListTableLoad("");


		listProductDetails.clear();
		tableItemDetails.setItems(listProductDetails);

		listPaymentDetails.clear();
		tablePaymentDetails.setItems(listPaymentDetails);

		setDate(new Date());
		setInvoiceDate(new Date());
		setCmbSupplier("");
		setCmbItemName("");
		setCmbUnit("");
		setTxtQuantity("0");
		setTxtPrice("0");
		setTxtDiscount("0");
		

		setTxtTotalAmount("0");
		setTxtVat("0");
		setTxtGrossAmount("0");
		setTxtPercentDiscount("0");
		setTxtManualDiscount("0");
		setTxtTotalDiscount("0");
		setTxtNetAmount("0");
		setTxtPaidAmount("0");

		setCmbPayementMethod("Cash");
		setCmbPaymentLedger("Cash");
		setTxtCheckNo("");
		setDatePass(null);

		setCmbDuePayementMetod(null);
		setCmbDuePaymentLedger(null);
		setTxtCheckNo("");
		setDateDuePass(null);
		setTxtDueAmount("");
		setCmbRemarks("");
	}

	@FXML
	private void ProductTableClickAction(MouseEvent mouseEvent) {
		try {
			if(tableItemDetails.getSelectionModel().getSelectedItem() != null) {
				TablePosition firstCell = tableItemDetails.getSelectionModel().getSelectedCells().get(0);
				ProductDetails  tempProductDetail= tableItemDetails.getSelectionModel().getSelectedItem();
				if(firstCell.getColumn()==10) {
					if(confirmationCheck("Delete This Item From")) {
						listProductDetails.remove(tempProductDetail);
						tableItemDetails.setItems(listProductDetails);
						netAmountCount();
					}
	
				}else {
	
					if(tempProductDetail != null) {
						setCmbItemName(tempProductDetail.getItemName());
						unitLoadByProductName();
						setCmbUnit(tempProductDetail.getUnit());
						setTxtQuantity(tempProductDetail.getUnitQuantity());	
						
						
						setTxtPrice(tempProductDetail.getUnitPrice());
						setTxtDiscount(tempProductDetail.getDiscount());
						
	
					}
				}
			}
	
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void invoiceListTableClickAction(MouseEvent mouseEvent) {
		try {
			if(tableInvoiceList.getSelectionModel().getSelectedItem() != null) {
				TablePosition firstCell = tableInvoiceList.getSelectionModel().getSelectedCells().get(0);
				InvoiceList tempInvoiceList = tableInvoiceList.getSelectionModel().getSelectedItem();
	
				setCmbFind(tempInvoiceList.getInvoiceNo()+" / "+tempInvoiceList.getSupplierName());
				btnFindAction(null);
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
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
	private String getSupplierLedgerId(String supplierName) {
		// TODO Auto-generated method stub
		try {
			String sql="select ledgerId from tbAccfledger where ledgertitle='"+supplierName+" (S)' and type ='"+supplierLedgerType+"'";
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

	private String getPaymentLedgerId(String paymentLedger) {
		// TODO Auto-generated method stub
		try {
			String sql="select ledgerId from tbAccfledger where ledgertitle='"+paymentLedger+"'";
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

	private String getSupplierId() {
		// TODO Auto-generated method stub
		try {
			sql = "select id from tbSupplier where SupplierName = '"+getCmbSupplier()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	private String getMaxInvoiceAutoNo() {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT IDENT_CURRENT('tbInvoice')+1 as maxAutoId;";
			ResultSet rs = databaseHandler.execQuery(sql);
			if (rs.next()) {
				return rs.getString("maxAutoId");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}

	private String getAutoInvoiceNo(String invoiceNO) {
		// TODO Auto-generated method stub
		try {
			sql = "select autoid from tbInvoice where invoice = '"+invoiceNO+"' and type='"+type+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("autoid");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}

	private String getItemId(String itemName) {
		// TODO Auto-generated method stub
		try {
			sql = "select id from tbItem where projectedItemName = '"+itemName+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}

	private String getMaxCheckId() {
		// TODO Auto-generated method stub
		try {
			String sql = "select (isnull(max(id),0)+1)as maxiD from tbcheckinformation;";
			ResultSet rs = databaseHandler.execQuery(sql);
			if (rs.next()) {
				return rs.getString("maxiD");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}

	private boolean addValidationCheck() {
		// TODO Auto-generated method stub
		if(!getCmbItemName().isEmpty()) {
			if(!getCmbUnit().isEmpty()) {
				if(!getTxtQuantity().isEmpty()) {
					
						if(!getTxtPrice().isEmpty()) {
							if(!getTxtPrice().isEmpty()) {
								
									if(checkIsProductNameValid()) {
										if(Double.valueOf(getTxtQuantity())>0) {
											String unit = getCmbUnit();
											double quantity;
											quantity = Double.valueOf(unit.substring(unit.indexOf('(')+1, unit.indexOf(')')));
											quantity = Double.valueOf(getTxtQuantity()) * quantity;
											
												for(int i = 0;i<listProductDetails.size();i++) {
													ProductDetails tempPd = listProductDetails.get(i);


												}
												return true;
											
										}else {
											new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Quantity Must be more than 0..");
											txtQuantity.requestFocus();
										}
									}else {
										new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Your Product Name is Invalid..\nPlease Select a valid Product Name");
										cmbItemName.requestFocus();
									}

								
							}else {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Discount..");
								txtPrice.requestFocus();
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Price..");
							txtPrice.requestFocus();
						}
					
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter a Valid item name..");
					txtQuantity.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select Any Unit..");
				cmbUnit.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter a Valid item name..");
			cmbItemName.requestFocus();
		}
		return false;
	}


	private boolean saveValidaionCheck() {
		// TODO Auto-generated method stub
		if(listProductDetails.size()>0) {
			if(isSupplierExist()) {
				if(!getDate().isEmpty()) {

					if(Double.valueOf(getTxtPaidAmount())>0) {
						if(getCmbPayementMethod().equals("Cash")) {
							return true;
						}else {
							if(!getTxtCheckNo().isEmpty()) {
								if(!getDatePass().isEmpty()) {
									return true;
								}else {
									new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Your Check Pass Date is Invalid....\nPlease Select a valid check pass date...");
									datePass.requestFocus();
								}
							}else {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Check No...");
								txtCheckNo.requestFocus();
							}
						}
					}else if(Double.valueOf(getTxtPaidAmount()) == 0){
						return true;
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Negative Paid Amount is not Valid...");
						txtPaidAmount.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Your Date Is InValid..\nPlease Select a Valid Date...");
					date.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select a Valid Supplier..");
				cmbSupplier.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Any Product To This Invoice..");
			cmbItemName.requestFocus();
		}
		return false;
	}

	private boolean editValidaionCheck() {
		// TODO Auto-generated method stub
		if(isInvoiceDateExist()) {
		if(listProductDetails.size()>0) {
			if(isSupplierExist()) {
				if(!getDate().isEmpty()) {
					double paidAmount= getPreviousPaidAmount();
					if(paidAmount<=Double.valueOf(getTxtNetAmount())) {
						return true;
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Net Amount is not Valid!","Net Amount is less then pevious paid Amount...\nYou will not be able to edit any invoices Which net amount less than the previous Paid Amount...");
						txtManualDiscount.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Your Date Is InValid..\nPlease Select a Valid Date...");
					date.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select a Valid Supplier..");
				cmbSupplier.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Any Product To This Invoice..");
			cmbItemName.requestFocus();
		}
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Cannot Change Invoice from the previous days....!","Any Previous Day Invoice Can Not Change...\nYou Can Pay Due Payment For This Invoice...");
			btnPay.requestFocus();
		}
		return false;
	}

	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub
	
		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message+" this Invoice?");
	}

	private double getPreviousPaidAmount() {
		// TODO Auto-generated method stub
		try {
			sql = "select ISNULL(paid,0) as paid from tbInvoice where Invoice = '"+getTxtInvoiceNO()+"' and type = '"+type+"';";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getDouble("paid");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return 0;
	}

	private boolean isSupplierExist() {
		try {
			sql = "select * from tbSupplier where SupplierName = '"+getCmbSupplier()+"'";
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

	private boolean isInvoiceIdExist(String invoiceId) {
		// TODO Auto-generated method stub
		try {
			sql = "select * from tbInvoice where type = '"+type+"' and Invoice = '"+invoiceId+"'";
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
	
	private boolean isInvoiceDateExist() {
		// TODO Auto-generated method stub
		
		int i =  dateTimeComparator.compare(new Date(), invoiceDate);
		if(i==0) return true;
		return false;
	}

	private void clearTxtAfterItemAdd() {
		// TODO Auto-generated method stub
		setCmbItemName("");
		setCmbUnit("");
		cmbUnit.getItems().clear();
		setTxtQuantity("0");
		setTxtPrice("0");
		setTxtDiscount("0");
		cmbItemName.requestFocus();
	}

	@FXML
	private void netAmountCount() {
		// TODO Auto-generated method stub
		double totalAmount,grossAmnt,vat,vatAmnt,discount,discountPercent,percentDiscountAmnt,manualDiscountAmnt,totalDisctount,netAmount;

		totalAmount = 0;
		vat = Double.valueOf(getTxtVat());
		vatAmnt = 0;
		discount = 0;
		discountPercent = Double.valueOf(getTxtDiscountPercent());
		//manualDiscountAmnt = Double.valueOf(getTxtManualDiscount());
		percentDiscountAmnt = 0;
		manualDiscountAmnt = Double.valueOf(getTxtManualDiscount());
		totalDisctount = 0;
		netAmount = 0;
		for(int i=0;i<listProductDetails.size();i++) {
			ProductDetails tempPD = listProductDetails.get(i);

			totalAmount += tempPD.getTotalAmount();
			discount += tempPD.getDiscount();

		}
		setTxtTotalAmount(totalAmount);

		if(vat>0) vatAmnt =(totalAmount*vat)/100;

		grossAmnt = totalAmount+vatAmnt;
		setTxtGrossAmount(grossAmnt);

		discountPercent = Double.valueOf(getTxtDiscountPercent());
		if(discountPercent>0) percentDiscountAmnt = (totalAmount * discountPercent)/ 100;
		setTxtPercentDiscount(percentDiscountAmnt);

		if(manualDiscountAmnt<discount) {
			setTxtManualDiscount(discount);
			totalDisctount = discount + percentDiscountAmnt;
		}
		else {
			totalDisctount = manualDiscountAmnt + percentDiscountAmnt;
		}

		setTxtTotalDiscount(totalDisctount);

		netAmount = grossAmnt - totalDisctount;
		setTxtNetAmount(netAmount);
	}


	private boolean checkIsHaveImei(String itemName) {
		// TODO Auto-generated method stub
		try {
			sql = "select id from tbItem where projectedItemName = '"+itemName+"' and IMEI = '1';";
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
	
	private void maxInvoiceNoSet() {
		// TODO Auto-generated method stub
		try {
			String year = String.valueOf(calender.get(Calendar.YEAR)).substring(2);
			String month = dfId.format(calender.get(Calendar.MONTH)+1);
			
			sql = "select  IsNULL(MAX(cast(SUBSTRING(Invoice,6,LEN(Invoice)-5) as int)),0)+1 as maxInvoiceId from tbInvoice WHERE type = '"+type+"' and Invoice LIKE '"+type+year+month+"%';";
			ResultSet rs = databaseHandler.execQuery(sql);
			if (rs.next()) {
				setTxtInvoiceNO(type+year+month+dfId.format(rs.getInt("maxInvoiceId")));		
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void cmbFindLoad() {
		try {
			sql = "select invoice,SellerCustomerName from tbInvoice where type = '"+type+"' order by Invoice desc";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbFind.data.clear();
			while(rs.next()) {
				cmbFind.data.add(rs.getString("invoice")+" / "+rs.getString("SellerCustomerName"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void invoiceListTableLoad(String customerName) {
		try {
			if(customerName.equals("")) {
				sql = "select invoice,SellerCustomerName,totalAmount from tbInvoice where type = '"+type+"' order by Invoice desc";
			}else {
				sql = "select invoice,SellerCustomerName,totalAmount from tbInvoice where type = '"+type+"' and SellerCustomerName = '"+customerName+"' order by Invoice desc";
			}

			ResultSet rs = databaseHandler.execQuery(sql);
			listInvoiceList.clear();
			while(rs.next()) {
				listInvoiceList.add(new InvoiceList(rs.getString("invoice"),rs.getString("SellerCustomerName"),df.format(rs.getDouble("totalAmount"))));
			}

			tableInvoiceList.setItems(listInvoiceList);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public void productNameLoad() {
		try {
			sql = "select projectedItemName from tbItem where isActive = '1' order by projectedItemName";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbItemName.data.clear();
			while (rs.next()) {
				cmbItemName.data.add(rs.getString("projectedItemName"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void remarksLoad() {
		try {
			sql = "select isnull(CONVERT(VARCHAR(500),remarks),'') as remarks from tbInvoice where type = '"+type+"' group by CONVERT(VARCHAR(500),remarks)  order by CONVERT(VARCHAR(500),remarks)";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbRemarks.data.clear();
			while (rs.next()) {
				cmbRemarks.data.add(rs.getString("remarks"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void supplierLoad() {
		try {
			sql = "select SupplierName from tbsupplier";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbSupplier.data.clear();
			while (rs.next()) {
				cmbSupplier.data.add(rs.getString("SupplierName"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void paymentMethodLoad() {
		try {
			sql="select headTitle from tbAccfhead where pheadid='"+paymentMethodHeadId+"' order by headid";
			ResultSet rs=databaseHandler.execQuery(sql);
			cmbPayementMethod.getItems().clear();
			cmbDuePayementMetod.getItems().clear();
			while(rs.next()) {
				cmbPayementMethod.getItems().add(rs.getString("headTitle"));
				cmbDuePayementMetod.getItems().add(rs.getString("headTitle"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void setCmpAction() {
		/*cmbItemName.setOnMouseClicked(e ->{
			//loadBayerByCustomer();
			System.out.println("Mouse Clicked Action");

		});*/
		/*
		 * cmbCustomer.setOnKeyReleased(e ->{ if(e.getCode() == KeyCode.ENTER)
		 * loadBayerByCustomer(); });
		 */

		/*cmbItemName.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener()
				{

					@Override
					public void changed(ObservableValue arg0, Object arg1, Object arg2) {
						// TODO Auto-generated method stub
						//loadBayerByCustomer();
						System.out.println("Change Listener");
					}
		});*/
		btnAdd.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnAddAction(null); });
		btnPay.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnDuePayAction(null); });
		btnSave.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnSaveAction(null); });

		

		cmbItemName.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV && !getCmbItemName().isEmpty()) { // focus lost
				if(checkIsProductNameValid()) {
					unitLoadByProductName();
					
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select a valid Item Name...");
					cmbItemName.requestFocus();
				}
			}
		});

		cmbSupplier.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					invoiceListTableLoad(newValue);
				}
			}    
		});
		/*txtManualDiscount.setOnKeyReleased(event ->{
			netAmountCountForManualDiscount();
		});*/

		txtManualDiscount.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV ) { // focus lost
				if(getTxtManualDiscount().isEmpty()) {
					setTxtManualDiscount("0");
				}else {

				}

				netAmountCount();
			}
		});

		cmbPayementMethod.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					paymentLedgeLoadByPmntMethod(cmbPaymentLedger,newValue);
					if(0!=cmbPayementMethod.getSelectionModel().getSelectedIndex()) {
						cmbPaymentLedger.setDisable(false);
						txtCheckNo.setDisable(false);
						datePass.setDisable(false);
					}else {
						setTxtCheckNo("");
						setDatePass(null);
						cmbPaymentLedger.setDisable(true);
						txtCheckNo.setDisable(true);
						datePass.setDisable(true);
					}
				}
			}    
		});
		cmbDuePayementMetod.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					paymentLedgeLoadByPmntMethod(cmbDuePaymentLedger,newValue);
					if(0!=cmbDuePayementMetod.getSelectionModel().getSelectedIndex()) {
						cmbDuePaymentLedger.setDisable(false);
						txtDueCheckNo.setDisable(false);
						dateDuePass.setDisable(false);
					}else {
						setTxtDueCheckNo("");
						setDateDuePass(null);
						cmbDuePaymentLedger.setDisable(true);
						txtDueCheckNo.setDisable(true);
						dateDuePass.setDisable(true);
					}

				}
			}    
		});




		/*KeyCombination sa = new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_DOWN);
		 Mnemonic mn = new Mnemonic(btnAdd, sa);
		 btnAdd.setMnemonicParsing(true);*/


		/* vendorList_textField_remaining.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		            if (!newValue.matches("\\d*(\\.\\d*)?")) {
		                vendorList_textField_remaining.setText(oldValue);
		            }
		        }
		    });*/
	}

	protected void paymentLedgeLoadByPmntMethod(ComboBox<String> cmbPaymentLedger, String newValue) {
		// TODO Auto-generated method stub
		try {
			sql = "select headid,ledgerTitle from tbAccfhead h  " + 
					"join tbAccfledger l  " + 
					"on h.headid  = l.pheadId where headTitle = '"+newValue+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbPaymentLedger.getItems().clear();
			while(rs.next()) {
				cmbPaymentLedger.getItems().add(rs.getString("ledgerTitle"));
			}
			cmbPaymentLedger.getSelectionModel().selectFirst();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void netAmountCountForManualDiscount() {
		// TODO Auto-generated method stub
		double totalAmount,grossAmnt,vat,vatAmnt,discountPercent,percentDiscountAmnt,manualDiscountAmnt,totalDisctount,netAmount;

		totalAmount = 0;
		vat = Double.valueOf(getTxtVat());
		vatAmnt = 0;

		discountPercent = Double.valueOf(getTxtDiscountPercent());
		//manualDiscountAmnt = Double.valueOf(getTxtManualDiscount());
		percentDiscountAmnt = 0;
		manualDiscountAmnt = Double.valueOf(getTxtManualDiscount());
		totalDisctount = 0;
		netAmount = 0;

		setTxtTotalAmount(totalAmount);

		if(vat>0) vatAmnt =(totalAmount*vat)/100;

		grossAmnt = totalAmount+vatAmnt;
		setTxtGrossAmount(grossAmnt);

		discountPercent = Double.valueOf(getTxtDiscountPercent());
		if(discountPercent>0) percentDiscountAmnt = (grossAmnt * discountPercent)/ 100;
		setTxtPercentDiscount(percentDiscountAmnt);

		totalDisctount = manualDiscountAmnt + percentDiscountAmnt;
		setTxtTotalDiscount(totalDisctount);

		netAmount = grossAmnt - totalDisctount;
		setTxtNetAmount(netAmount);
	}

	/*ChangeListener<String> numberAllowedListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                vendorList_textField_remaining.setText(oldValue);
            }
        }
    };*/
	private boolean checkIsProductNameValid() {
		try {
			sql = "select ItemName from tbItem where projectedItemName = '"+getCmbItemName()+"';";
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

	public void unitLoadByProductName() {
		try {
			sql = "select id,u.UnitName,u.UnitQuantity,IMEI,isnull((select top 1 pd.PurchasePrice from tbStore pd where pd.itemId = i.id order by pd.invoiceNo desc),0) as lastPurchasePrice from tbitem i  " + 
					"join tbUnit u  " + 
					"on i.id = u.itemId where projectedItemName='"+getCmbItemName()+"' order by u.UnitQuantity";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbUnit.getItems().clear();
			while (rs.next()) {
				cmbUnit.getItems().add(rs.getString("UnitName")+" ("+rs.getString("UnitQuantity")+")");
				setTxtPrice(rs.getDouble("lastPurchasePrice"));
			}
			if(cmbUnit.getItems().size()>0)
				cmbUnit.getSelectionModel().selectFirst();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*public void stockSetByProductName() {
		try {
			sql = "select dbo.presentStock(i.id) as stock from tbItem i where projectedItemName = '"+getCmbItemName()+"';";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtStock(rs.getInt("stock"));
				//setTxtPrice(rs.getDouble("salePrice"));
			}



		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbItemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbItemName);
		});
		cmbSupplier.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSupplier);
		});
		/*cmbItemName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	        selectTextIfFocused(cmbItemName);
	    });*/

		txtPrice.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtPrice);
		});
		txtQuantity.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtQuantity);
		});
		
		txtDiscount.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtDiscount);
		});


		txtVat.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtVat);
		});

		txtDiscountPercent.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtDiscountPercent);
		});

		txtManualDiscount.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtManualDiscount);
		});

		txtPaidAmount.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtPaidAmount);
		});

		txtDueAmount.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtDueAmount);
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


	private void focusMoveAction() {
		Control[] control =  {txtInvoiceNO,cmbSupplier,date,cmbItemName,cmbUnit,txtQuantity,txtPrice,txtDiscount,btnAdd};
		new FocusMoveByEnter(control);

		Control[] control2 =  {txtVat,txtDiscountPercent,txtManualDiscount,txtPaidAmount,cmbPayementMethod,btnSave};
		new FocusMoveByEnter(control2);

		Control[] control5 =  {cmbPaymentLedger,txtCheckNo,datePass,btnSave};
		new FocusMoveByEnter(control5);

		Control[] control6 =  {cmbDuePayementMetod,btnPay};
		new FocusMoveByEnter(control6);

		Control[] control7 =  {cmbDuePaymentLedger,txtCheckNo,dateDuePass,txtDueAmount,btnPay};
		new FocusMoveByEnter(control7);
	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		map = new HashMap();
		date.setConverter(converter);
		date.setValue(LocalDate.now());

		
		datePass.setConverter(converter);
		dateDuePass.setConverter(converter);

		txtQuantity.setTextFormatter(NumberField.getDoubleFormate());
		txtPrice.setTextFormatter(NumberField.getDoubleFormate());
		txtDiscount.setTextFormatter(NumberField.getDoubleFormate());
		txtTotalAmount.setTextFormatter(NumberField.getDoubleFormate());
		txtVat.setTextFormatter(NumberField.getDoubleFormate());
		txtGrossAmount.setTextFormatter(NumberField.getDoubleFormate());
		txtPercentDiscount.setTextFormatter(NumberField.getDoubleFormate());
		txtTotalDiscount.setTextFormatter(NumberField.getDoubleFormate());
		txtDiscountPercent.setTextFormatter(NumberField.getDoubleFormate());
		txtManualDiscount.setTextFormatter(NumberField.getDoubleFormate());
		txtNetAmount.setTextFormatter(NumberField.getDoubleFormate());
		txtPaidAmount.setTextFormatter(NumberField.getDoubleFormate());

		txtDueAmount.setTextFormatter(NumberField.getDoubleFormate());
		//date.setValue(LocalDate.now());


		itemIdCol.setCellValueFactory(new PropertyValueFactory("itemId"));
		itemNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		unitCol.setCellValueFactory(new PropertyValueFactory("unit"));
		unitQuantityCol.setCellValueFactory(new PropertyValueFactory("unitQuantity"));
		quantityCol.setCellValueFactory(new PropertyValueFactory("qunatityWithFree"));
		unitPriceCol.setCellValueFactory(new PropertyValueFactory("unitPrice"));
		priceCol.setCellValueFactory(new PropertyValueFactory("price"));
		totalAmountCol.setCellValueFactory(new PropertyValueFactory("totalAmount"));
		discountCol.setCellValueFactory(new PropertyValueFactory("discount"));
		netAmountCol.setCellValueFactory(new PropertyValueFactory("netAmount"));
		deleteCol.setCellValueFactory(new PropertyValueFactory("delete"));

		tableItemDetails.setColumnResizePolicy(tableItemDetails.CONSTRAINED_RESIZE_POLICY);


		dateCol.setCellValueFactory(new PropertyValueFactory("date"));
		paymentTypeCol.setCellValueFactory(new PropertyValueFactory("paymentType"));
		checkNoCol.setCellValueFactory(new PropertyValueFactory("checkNo"));
		passDateCol.setCellValueFactory(new PropertyValueFactory("passDate"));
		amountCol.setCellValueFactory(new PropertyValueFactory("amount"));
		dueCol.setCellValueFactory(new PropertyValueFactory("due"));

		tablePaymentDetails.setColumnResizePolicy(tablePaymentDetails.CONSTRAINED_RESIZE_POLICY);

		invoiceNOCol.setCellValueFactory(new PropertyValueFactory("invoiceNo"));
		totalAmountInvoiceListCol.setCellValueFactory(new PropertyValueFactory("totalAmount"));
		supplierNameCol.setCellValueFactory(new PropertyValueFactory("supplierName"));
	}



	private void addCmp() {
		// TODO Auto-generated method stub
		cmbFind.setPrefWidth(214);
		cmbFind.setPrefHeight(28);

		cmbSupplier.setPrefWidth(214);
		cmbSupplier.setPrefHeight(28);

		vBoxSupplier.getChildren().remove(0);
		//vBoxSupplier.getChildren().remove(0);
		vBoxSupplier.getChildren().remove(1);
		vBoxSupplier.getChildren().add(0,cmbFind);
		//vBoxSupplier.getChildren().add(1,cmbFind);
		vBoxSupplier.getChildren().add(2,cmbSupplier);

		cmbItemName.setPrefWidth(435);
		cmbItemName.setPrefHeight(28);

		vBoxItemName.getChildren().remove(1);
		vBoxItemName.getChildren().add(1,cmbItemName);

		cmbRemarks.setPrefWidth(997);
		cmbRemarks.setPrefHeight(28);

		vBoxRemarks.getChildren().clear();
		vBoxRemarks.getChildren().add(cmbRemarks);
	}

	public static class PaymentDetails{
		private SimpleStringProperty date;
		private SimpleStringProperty paymentType;
		private SimpleStringProperty checkNo;
		private SimpleStringProperty passDate;
		private SimpleDoubleProperty amount;
		private SimpleDoubleProperty due;

		public PaymentDetails(String date,String paymentType,String checkNo,String passDate,double amount,double due) {
			this.date = new SimpleStringProperty(date);
			this.paymentType = new SimpleStringProperty(paymentType);
			this.checkNo = new SimpleStringProperty(checkNo);
			this.passDate = new SimpleStringProperty(passDate);
			this.amount = new SimpleDoubleProperty(amount);
			this.due = new SimpleDoubleProperty(due);
		}

		public String getDate() {
			return date.get();
		}

		public void setDate(String date) {
			this.date = new SimpleStringProperty(date);
		}

		public String getPaymentType() {
			return paymentType.get();
		}

		public void setPaymentType(String paymentType) {
			this.paymentType = new SimpleStringProperty(paymentType);
		}

		public String getCheckNo() {
			return checkNo.get();
		}

		public void setCheckNo(String checkNo) {
			this.checkNo = new SimpleStringProperty(checkNo);
		}

		public String getPassDate() {
			return passDate.get();
		}

		public void setPassDate(String passDate) {
			this.passDate = new SimpleStringProperty(passDate);
		}

		public double getAmount() {
			return amount.get();
		}

		public void setAmount(double amount) {
			this.amount = new SimpleDoubleProperty(amount);
		}

		public double getDue() {
			return due.get();
		}

		public void setDue(double due) {
			this.due = new SimpleDoubleProperty(due);
		}

	}


	public static class ProductDetails{


		private SimpleStringProperty itemId;
		private SimpleStringProperty itemName;
		private SimpleStringProperty unit;
		private SimpleDoubleProperty unitQuantity;
		private SimpleDoubleProperty quantity;
		private SimpleStringProperty qunatityWithFree;
		private SimpleDoubleProperty unitPrice;
		private SimpleDoubleProperty price;
		private SimpleDoubleProperty totalAmount;
		private SimpleDoubleProperty discount;
		private SimpleDoubleProperty netAmount;
		private SimpleStringProperty delete;




		public ProductDetails(String itemId,String itemName,String unit,double unitQuantity,double quantity,double unitPrice,double price,double totalAmount,double discount,double netAmount,String delete) {
			this.itemId = new SimpleStringProperty(itemId);
			this.itemName = new SimpleStringProperty(itemName);
			this.unit = new SimpleStringProperty(unit);
			
			this.unitQuantity = new SimpleDoubleProperty(unitQuantity);
			this.quantity = new SimpleDoubleProperty(quantity);
			
			this.unitPrice = new SimpleDoubleProperty(unitPrice);
			this.price = new SimpleDoubleProperty(price);
			this.totalAmount = new SimpleDoubleProperty(totalAmount);
			this.discount = new SimpleDoubleProperty(discount);
			this.netAmount = new SimpleDoubleProperty(netAmount);
		
		
			this.delete = new SimpleStringProperty(delete);



		}



		public String getItemId() {
			return itemId.get();
		}
		public void setItemId(String itemId) {
			this.itemId = new SimpleStringProperty(itemId);
		}
		public String getItemName() {
			return itemName.get();
		}
		public void setItemName(String itemName) {
			this.itemName = new SimpleStringProperty(itemName);
		}
		public String getUnit() {
			return unit.get();
		}
		public void setUnit(String unit) {
			this.unit = new SimpleStringProperty(unit);
		}
		public double getUnitQuantity() {
			return unitQuantity.get();
		}
		public void setUnitQuantity(double unitQuantity) {
			this.unitQuantity = new SimpleDoubleProperty(unitQuantity);
		}
		public double getQuantity() {
			return quantity.get();
		}
		public void setQuantity(double quantity) {
			this.quantity = new SimpleDoubleProperty(quantity);
		}
		

		public String getQunatityWithFree() {
			
			setQunatityWithFree(String.valueOf(getQuantity()));
			return qunatityWithFree.get();
		}



		public void setQunatityWithFree(String qunatityWithFree) {
			this.qunatityWithFree =new SimpleStringProperty(qunatityWithFree);
		}



		public double getUnitPrice() {
			return unitPrice.get();
		}
		public void setUnitPrice(double unitPrice) {
			this.unitPrice = new SimpleDoubleProperty(unitPrice);
		}
		public double getPrice() {
			return price.get();
		}
		public void setPrice(double price) {
			this.price = new SimpleDoubleProperty(price);
		}
		public double getTotalAmount() {
			return totalAmount.get();
		}
		public void setTotalAmount(double totalAmount) {
			this.totalAmount = new SimpleDoubleProperty(totalAmount);
		}
		public double getDiscount() {
			return discount.get();
		}
		public void setDiscount(double discount) {
			this.discount = new SimpleDoubleProperty(discount);
		}
		public double getNetAmount() {
			return netAmount.get();
		}
		public void setNetAmount(double netAmount) {
			this.netAmount = new SimpleDoubleProperty(netAmount);
		}

		public String getDelete() {
			return delete.get();
		}
		public void setDelete(String delete) {
			this.delete = new SimpleStringProperty(delete);
		}
		
		


	}

	public static class InvoiceList{


		private SimpleStringProperty invoiceNo;
		private SimpleStringProperty totalAmount;
		private SimpleStringProperty supplierName;

		public InvoiceList(String invoiceNo,String customerName,String totalAmount) {
			this.invoiceNo = new SimpleStringProperty(invoiceNo);
			this.totalAmount = new SimpleStringProperty(totalAmount);
			this.supplierName = new SimpleStringProperty(customerName);
		}

		public String getInvoiceNo() {
			return invoiceNo.get();
		}

		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = new SimpleStringProperty(invoiceNo);
		}

		public String getTotalAmount() {
			return totalAmount.get();
		}

		public void setTotalAmount(String totalAmount) {
			this.totalAmount = new SimpleStringProperty(totalAmount);
		}

		public String getSupplierName() {
			return supplierName.get();
		}

		public void setSupplierName(String supplierName) {
			this.supplierName =  new SimpleStringProperty(supplierName);
		}



	}

	public String getTxtInvoiceNO() {
		return txtInvoiceNO.getText().trim();
	}


	public void setTxtInvoiceNO(String txtInvoiceNO) {
		this.txtInvoiceNO.setText(txtInvoiceNO);
	}

	public String getTxtQuantity() {
		return txtQuantity.getText().trim();
	}


	public void setTxtQuantity(String txtQuantity) {
		this.txtQuantity.setText(txtQuantity);
	}
	public void setTxtQuantity(double txtQuantity) {
		this.txtQuantity.setText(String.valueOf(txtQuantity));
	}

	public String getTxtPrice() {
		return txtPrice.getText().trim();
	}


	public void setTxtPrice(String txtPrice) {
		this.txtPrice.setText(txtPrice);
	}
	public void setTxtPrice(double txtPrice) {
		this.txtPrice.setText(String.valueOf(txtPrice));
	}


	public String getTxtDiscount() {
		return txtDiscount.getText().trim();
	}


	public void setTxtDiscount(String txtDiscount) {
		this.txtDiscount.setText(txtDiscount);
	}
	public void setTxtDiscount(double txtDiscount) {
		this.txtDiscount.setText(String.valueOf(txtDiscount));
	}


	public String getTxtTotalAmount() {
		return txtTotalAmount.getText().trim();
	}


	public void setTxtTotalAmount(String txtTotalAmount) {
		this.txtTotalAmount.setText(txtTotalAmount);
	}
	public void setTxtTotalAmount(double txtTotalAmount) {
		this.txtTotalAmount.setText(String.valueOf(txtTotalAmount));
	}


	public String getTxtVat() {
		return txtVat.getText().trim();
	}


	public void setTxtVat(String txtVat) {
		this.txtVat.setText(txtVat);
	}


	public String getTxtGrossAmount() {
		return txtGrossAmount.getText().trim();
	}


	public void setTxtGrossAmount(String txtGrossAmount) {
		this.txtGrossAmount.setText(txtGrossAmount);
	}
	public void setTxtGrossAmount(double txtGrossAmount) {
		this.txtGrossAmount.setText(String.valueOf(txtGrossAmount));
	}


	public String getTxtPercentDiscount() {
		return txtPercentDiscount.getText().trim();
	}


	public void setTxtPercentDiscount(String txtPercentDiscount) {
		this.txtPercentDiscount.setText(txtPercentDiscount);
	}
	public void setTxtPercentDiscount(double txtPercentDiscount) {
		this.txtPercentDiscount.setText(String.valueOf(txtPercentDiscount));
	}


	public String getTxtDiscountPercent() {
		return txtDiscountPercent.getText().trim();
	}

	public void setTxtDiscountPercent(String txtDiscountPercent) {
		this.txtDiscountPercent.setText(txtDiscountPercent);
	}

	public String getTxtManualDiscount() {
		return txtManualDiscount.getText().trim();
	}


	public void setTxtManualDiscount(String txtManualDiscount) {
		this.txtManualDiscount.setText(txtManualDiscount);
	}
	public void setTxtManualDiscount(double txtManualDiscount) {
		this.txtManualDiscount.setText(String.valueOf(txtManualDiscount));
	}


	public String getTxtTotalDiscount() {
		return txtTotalDiscount.getText().trim();
	}


	public void setTxtTotalDiscount(String txtTotalDiscount) {
		this.txtTotalDiscount.setText(txtTotalDiscount);
	}
	public void setTxtTotalDiscount(double txtTotalDiscount) {
		this.txtTotalDiscount.setText(String.valueOf(txtTotalDiscount));
	}

	public String getTxtPaidAmount() {
		return txtPaidAmount.getText().trim();
	}


	public void setTxtPaidAmount(String txtPaidAmount) {
		this.txtPaidAmount.setText(txtPaidAmount);
	}


	public String getTxtNetAmount() {
		return txtNetAmount.getText().trim();
	}


	public void setTxtNetAmount(String txtNetAmount) {
		this.txtNetAmount.setText(txtNetAmount);
	}
	public void setTxtNetAmount(double txtNetAmount) {
		this.txtNetAmount.setText(String.valueOf(txtNetAmount));
	}


	public String getTxtCheckNo() {
		return txtCheckNo.getText().trim();
	}


	public void setTxtCheckNo(String txtCheckNo) {
		this.txtCheckNo.setText(txtCheckNo);
	}


	public String getTxtDueCheckNo() {
		return txtDueCheckNo.getText().trim();
	}


	public void setTxtDueCheckNo(String txtDueCheckNo) {
		this.txtDueCheckNo.setText(txtDueCheckNo);
	}


	public String getTxtDueAmount() {
		return txtDueAmount.getText().trim();
	}


	public void setTxtDueAmount(String txtDueAmount) {
		this.txtDueAmount.setText(txtDueAmount);
	}


	public String getCmbRemarks() {
		if(cmbRemarks.getValue() != null)
			return cmbRemarks.getValue().toString().trim();
		else
			return null;
	}


	public void setCmbRemarks(String txtRemarks) {
		this.cmbRemarks.setValue(txtRemarks);
	}


	public String getDate() {
		if(date.getValue() != null)
			return date.getValue().toString();
		else 
			return "";

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


	


	public String getDatePass() {
		if(datePass.getValue() != null)
			return datePass.getValue().toString();

		return "";
	}


	public void setDatePass(Date datePass) {
		if(datePass != null) {
			int dd=Integer.valueOf(new SimpleDateFormat("dd").format(datePass));
			int mm=Integer.valueOf(new SimpleDateFormat("MM").format(datePass));
			int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(datePass));
			this.datePass.setValue(LocalDate.of(yy,mm,dd));
		}else {
			this.datePass.getEditor().setText("");
		}
	}


	public String getDateDuePass() {
		if(dateDuePass.getValue() != null)
			return dateDuePass.getValue().toString();

		return "";
	}


	public void setDateDuePass(Date dateDuePass) {
		if(dateDuePass != null) {
			int dd=Integer.valueOf(new SimpleDateFormat("dd").format(dateDuePass));
			int mm=Integer.valueOf(new SimpleDateFormat("MM").format(dateDuePass));
			int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(dateDuePass));
			this.dateDuePass.setValue(LocalDate.of(yy,mm,dd));
		}else {
			this.dateDuePass.getEditor().setText("");
		}
	}


	


	public String getCmbUnit() {
		if(cmbUnit.getValue() != null)
			return cmbUnit.getValue().toString().trim();
		else return "";
	}


	public void setCmbUnit(String cmbUnit) {
		this.cmbUnit.setValue(cmbUnit);
	}


	public String getCmbPayementMethod() {
		if(cmbPayementMethod.getValue() != null)
			return cmbPayementMethod.getValue().toString().trim();
		else return "";
	}


	public void setCmbPayementMethod(String cmbPayementMethod) {
		this.cmbPayementMethod.setValue(cmbPayementMethod);
	}


	public String getCmbPaymentLedger() {
		if(cmbPaymentLedger.getValue() != null)
			return cmbPaymentLedger.getValue().toString().trim();
		else return "";
	}


	public void setCmbPaymentLedger(String cmbPaymentLedger) {
		this.cmbPaymentLedger.setValue(cmbPaymentLedger);
	}


	public String getCmbDuePayementMetod() {
		if(cmbDuePayementMetod.getValue() != null)
			return cmbDuePayementMetod.getValue().toString().trim();
		else return "";
	}


	public void setCmbDuePayementMetod(String cmbDuePayementMetod) {
		this.cmbDuePayementMetod.setValue(cmbDuePayementMetod);
	}


	public String getCmbDuePaymentLedger() {
		if(cmbDuePaymentLedger.getValue() != null)
			return cmbDuePaymentLedger.getValue().toString().trim();
		else return "";
	}


	public void setCmbDuePaymentLedger(String cmbDuePaymentLedger) {
		this.cmbDuePaymentLedger.setValue(cmbDuePaymentLedger);
	}


	public String getCmbFind() {
		if(cmbFind.getValue() != null) {
			String find[]=cmbFind.getValue().toString().trim().split("/");
			return find[0].trim();
		}

		else return "";
	}


	public void setCmbFind(String cmbFind) {
		this.cmbFind.setValue(cmbFind);
	}


	public String getCmbSupplier() {
		if(cmbSupplier.getValue() != null)
			return cmbSupplier.getValue().toString().trim();
		else return "";
	}


	public void setCmbSupplier(String cmbSupplier) {
		this.cmbSupplier.setValue(cmbSupplier);
	}


	public String getCmbItemName() {
		if(cmbItemName.getValue() != null)
			return cmbItemName.getValue().toString().trim();
		else return "";
	}


	public void setCmbItemName(String cmbItemName) {
		this.cmbItemName.setValue(cmbItemName);
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
}

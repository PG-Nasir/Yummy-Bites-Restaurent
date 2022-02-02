package ui.trading;

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




public class KitchenReturnController implements Initializable{

	@FXML
	TextField txtInvoiceNO;
	@FXML
	TextField txtQuantity;	


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
	VBox vBoxSupplier;
	@FXML
	VBox vBoxItemName;
	@FXML
	VBox vBoxRemarks;



	@FXML
	ComboBox<String> cmbUnit;

	FxComboBox cmbFind=new FxComboBox<>();
	FxComboBox cmbReciver=new FxComboBox<>();
	FxComboBox cmbItemName=new FxComboBox<>();
	FxComboBox cmbRemarks = new FxComboBox<>();

	NumberField numberField = new NumberField();


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
	private TableColumn<ProductDetails, String> deleteCol;


	@FXML
	private TableView<InvoiceList> tableInvoiceList;

	ObservableList<InvoiceList> listInvoiceList = FXCollections.observableArrayList();

	@FXML
	private TableColumn<InvoiceList, String> invoiceNOCol;
	@FXML
	private TableColumn<InvoiceList, String> dateCol;
	@FXML
	private TableColumn<InvoiceList, String> receiverNameCol;

	private DatabaseHandler databaseHandler;
	private String sql;

	private HashMap map;
	//this String variable for Payment Method Id
	private String paymentMethodHeadId="2";
	//this String variable for purchase type
	private String type = "6";
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

			double unitQuantity=0,quantityPerUnit,quantity=0;

			unitQuantity = Double.valueOf(getTxtQuantity());


			String unit = getCmbUnit();
			quantityPerUnit = Double.valueOf(unit.substring(unit.indexOf('(')+1, unit.indexOf(')')));
			quantity = unitQuantity * quantityPerUnit;

			
			

			itemId = getItemId(getCmbItemName());

			int rowCount = 0;
			for(rowCount = 0;rowCount < listProductDetails.size();rowCount++) {
				ProductDetails  tempPd= listProductDetails.get(rowCount);
				if(tempPd.getItemId().equals(itemId)) {

					listProductDetails.remove(rowCount);
					listProductDetails.add(rowCount,new ProductDetails(itemId, getCmbItemName(), unit, unitQuantity, quantity, "Del"));
					clearTxtAfterItemAdd();

					break;
				}
			}
			if(rowCount == listProductDetails.size()) {

				listProductDetails.add(new ProductDetails(itemId, getCmbItemName(), unit, unitQuantity, quantity, "Del"));
				clearTxtAfterItemAdd();

			}


			tableItemDetails.setItems(listProductDetails);
			
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

						String payType = "Purchase (New Pay)";
						String receiverId = "R0";

						String invoiceAutoNo = getMaxInvoiceAutoNo();
						String kitchenReceiver= getReceiverLedgerId("Kitchen Receiver");


						
						String d_l_id;
						String c_l_id;
						String cash="0";
						String card="0";


						


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
								+ " '" + receiverId + "', "
								+ "'" + getCmbReceiver() + "',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'" + cash + "', "
								+ "'" + card + "', "
								+ " '0',"
								+ "'" + type + "',"
								+ "'" + dbDateFormate.format(date.getValue()) + "',"
								+ "'"+commonTransection+"',"
								+ "'','"+cashTransection+"',"
								+ "'"+cardTransection+"',"
								+ "'"+vatTransection+"','"+discountTransection+"',"
								+ "'',"
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
									+ "'" + temPd.getItemId() + "',"
									+ "'" + receiverId + "',"
									+ "'" + temPd.getUnit() + "',"
									+ "'" + temPd.getUnitQuantity() + "',"
									+ "'" + temPd.getQuantity() + "',"
									+ "'',"
									+ "'',"
									+ "'0',"
									+ "'0',"	
									+ "'0',"
									+ "'0',"
									+ "'',"
									+ "'0',"
									+ "'0',"
									+ "'0',"
									+ "'0',"
									+ "'" + type + "',"
									+ "'0',"
									+ "'',"
									+ "'" + dbDateFormate.format(date.getValue()) + "',"
									+ "CURRENT_TIMESTAMP, "
									+ "'" + SessionBeam.getUserId() + "' )";

							databaseHandler.execAction(sql);


						}
						new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfull....!","Invoice Save Successfully...");
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

						String payType = "Purchase (New Pay)";
						String supplierledgerId= getReceiverLedgerId(getCmbReceiver());
						String receiverId = "R0";
						
						String d_l_id;
						String c_l_id;
						String cash="0";
						String card="0";

						String invoiceAutoNo = getAutoInvoiceNo(getTxtInvoiceNO());	

						//due = Double.parseDouble(txtnetAmount.getText()) - Double.parseDouble(txtPaidAmount.getText());
						String sql = "update tbInvoice set " + 
								"tradingDate = '"+dbDateFormate.format(date.getValue())+"',\r\n" + 
								"SellerCustomerID='"+receiverId+"',\r\n" + 
								"SellerCustomerName='"+getCmbReceiver()+"', \r\n" + 
								"totalAmount='0',\r\n" + 
								"vat='0',\r\n" + 
								"vatAmount = '0',\r\n" + 
								"discount= '0', \r\n" + 
								"percentDiscount='0',\r\n" + 
								"ManualDiscount='0',\r\n" + 
								"totalDiscount='0',\r\n" + 
								"netAmount='0',\r\n" + 
								"due='0'-paid,\r\n" + 
								"transectionID='"+commonTransection+"',\r\n" + 
								"vatId = '"+vatTransection+"', " + 
								"discountID = '"+discountTransection+"', " + 
								"Date='"+dbDateFormate.format(date.getValue())+"', " + 
								"remarks = '"+getCmbRemarks()+"', " + 
								"entryTime=CURRENT_TIMESTAMP, " + 
								"entryBy='"+SessionBeam.getUserId()+"' where invoice='"+getTxtInvoiceNO()+"' and type = '"+type+"'";
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
									+ "'" + temPd.getItemId() + "',"
									+ "'" + receiverId + "',"
									+ "'" + temPd.getUnit() + "',"
									+ "'" + temPd.getUnitQuantity() + "',"
									+ "'" + temPd.getQuantity() + "',"
									+ "'',"
									+ "'',"
									+ "'0',"
									+ "'0',"	
									+ "'0',"
									+ "'0',"
									+ "'',"
									+ "'0',"
									+ "'0',"
									+ "'0',"
									+ "'0',"
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
						setCmbReceiver(rs.getString("SellerCustomerName"));
						setDate(rs.getDate("date"));
						setCmbRemarks(rs.getString("remarks"));

						setInvoiceDate(rs.getDate("date"));
					}

					sql = "select * from tbStore where invoiceNo = '"+getCmbFind()+"' and type = '"+type+"'";
					rs = databaseHandler.execQuery(sql);
					while(rs.next()) {
						listProductDetails.add(new ProductDetails(rs.getString("itemId"),rs.getString("itemName"), rs.getString("unit"), rs.getDouble("UnitQty"), rs.getDouble("qty"),"Del"));
					}

					tableItemDetails.setItems(listProductDetails);


					
					
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

				String report = "src/resource/reports/Invoices/KitchenReturnInvoice.jrxml";
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
				jd.setPageHeight(150+(rowCount*10));

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
	private void btnRefreshAction(ActionEvent event) {
		receiverLoad();
		maxInvoiceNoSet();
		productNameLoad();
		remarksLoad();
		invoiceListTableLoad("");

		listProductDetails.clear();
		tableItemDetails.setItems(listProductDetails);


		setDate(new Date());
		setInvoiceDate(new Date());
		setCmbReceiver("");
		setCmbItemName("");
		setCmbUnit("");
		setTxtQuantity("0");
		//setTxtFree("0");
		
		setCmbRemarks("");
	}

	@FXML
	private void ProductTableClickAction(MouseEvent mouseEvent) {
		try {
			if(tableItemDetails.getSelectionModel().getSelectedItem() != null) {
				TablePosition firstCell = tableItemDetails.getSelectionModel().getSelectedCells().get(0);
				ProductDetails  tempProductDetail= tableItemDetails.getSelectionModel().getSelectedItem();
				if(firstCell.getColumn()==5) {
					if(confirmationCheck("Delete This Item From")) {
						listProductDetails.remove(tempProductDetail);
						tableItemDetails.setItems(listProductDetails);
						
					}

				}else {

					if(tempProductDetail != null) {
						setCmbItemName(tempProductDetail.getItemName());
						unitLoadByProductName();
						setCmbUnit(tempProductDetail.getUnit());
						setTxtQuantity(tempProductDetail.getUnitQuantity());	
						
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

				setCmbFind(tempInvoiceList.getInvoiceNo()+" / "+tempInvoiceList.getReceiverName());
				btnFindAction(null);
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private String getReceiverLedgerId(String receiverName) {
		// TODO Auto-generated method stub
		try {
			String sql="select ledgerId from tbAccfledger where ledgertitle='"+receiverName+"'";
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

	private String getMaxInvoiceAutoNo() {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT IDENT_CURRENT('tbInvoice')+1 as maxAutoId";
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


	private boolean saveValidaionCheck() {
		// TODO Auto-generated method stub
		if(listProductDetails.size()>0) {
			
				if(!getDate().isEmpty()) {
					return true;				
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Your Date Is InValid..\nPlease Select a Valid Date...");
					date.requestFocus();
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
				
					if(!getDate().isEmpty()) {
						for(int i = 0;i<listProductDetails.size();i++) {
							ProductDetails tempPd = listProductDetails.get(i);


						}
						double paidAmount= getPreviousPaidAmount();

					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Your Date Is InValid..\nPlease Select a Valid Date...");
						date.requestFocus();
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

	private boolean addValidationCheck() {
		// TODO Auto-generated method stub
		if(!getCmbItemName().isEmpty()) {
			if(!getCmbUnit().isEmpty()) {
				if(!getTxtQuantity().isEmpty()) {
					if(checkIsProductNameValid()) {
						if(Double.valueOf(getTxtQuantity())>0) {

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

	
	private boolean isInvoiceDateExist() {
		// TODO Auto-generated method stub

		int i =  dateTimeComparator.compare(new Date(), invoiceDate);
		if(i==0) return true;
		return false;
	}
	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message+" this Invoice?");
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

	private void clearTxtAfterItemAdd() {
		// TODO Auto-generated method stub
		setCmbItemName("");
		setCmbUnit("");
		cmbUnit.getItems().clear();
		setTxtQuantity("0");	

		cmbItemName.requestFocus();
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

	private void invoiceListTableLoad(String supplierName) {
		try {
			if(supplierName.equals("")) {
				sql = "select invoice,date,SellerCustomerName,totalAmount from tbInvoice where type = '"+type+"' order by Invoice desc";
			}else {
				sql = "select invoice,date,SellerCustomerName,totalAmount from tbInvoice where type = '"+type+"' and SellerCustomerName = '"+supplierName+"' order by Invoice desc";
			}

			ResultSet rs = databaseHandler.execQuery(sql);
			listInvoiceList.clear();
			while(rs.next()) {
				listInvoiceList.add(new InvoiceList(rs.getString("invoice"),rs.getString("Date"),rs.getString("SellerCustomerName")));
			}

			tableInvoiceList.setItems(listInvoiceList);
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
	public void receiverLoad() {
		try {
			sql = "select SellerCustomerName from tbInvoice where type = "+type+" group by SellerCustomerName order by SellerCustomerName";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbReciver.data.clear();
			while (rs.next()) {
				cmbReciver.data.add(rs.getString("SellerCustomerName"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	private void setCmpAction() {

		btnAdd.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnAddAction(null); });
		
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

		cmbReciver.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					invoiceListTableLoad(newValue);
				}
			}    
		});

		/*txtManualDiscount.setOnKeyReleased(event ->{
			netAmountCountForManualDiscount();
		});*/





	}


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

			}
			if(cmbUnit.getItems().size()>0)
				cmbUnit.getSelectionModel().selectFirst();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbItemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbItemName);
		});
		cmbReciver.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbReciver);
		});
		/*cmbItemName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	        selectTextIfFocused(cmbItemName);
	    });*/
		txtQuantity.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtQuantity);
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
		Control[] control =  {txtInvoiceNO,cmbReciver,date,cmbItemName,cmbUnit,txtQuantity,btnAdd};
		new FocusMoveByEnter(control);

	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		map = new HashMap();

		date.setConverter(converter);
		date.setValue(LocalDate.now());



		txtQuantity.setTextFormatter(NumberField.getDoubleFormate());

		//date.setValue(LocalDate.now());


		itemIdCol.setCellValueFactory(new PropertyValueFactory("itemId"));
		itemNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		unitCol.setCellValueFactory(new PropertyValueFactory("unit"));
		unitQuantityCol.setCellValueFactory(new PropertyValueFactory("unitQuantity"));
		quantityCol.setCellValueFactory(new PropertyValueFactory("quantity"));
		deleteCol.setCellValueFactory(new PropertyValueFactory("delete"));

		tableItemDetails.setColumnResizePolicy(tableItemDetails.CONSTRAINED_RESIZE_POLICY);

		invoiceNOCol.setCellValueFactory(new PropertyValueFactory("invoiceNo"));
		dateCol.setCellValueFactory(new PropertyValueFactory("date"));
		receiverNameCol.setCellValueFactory(new PropertyValueFactory("receiverName"));
	}



	private void addCmp() {
		// TODO Auto-generated method stub
		cmbFind.setPrefWidth(314);
		cmbFind.setPrefHeight(28);

		cmbReciver.setPrefWidth(314);
		cmbReciver.setPrefHeight(28);

		vBoxSupplier.getChildren().remove(0);
		//vBoxSupplier.getChildren().remove(0);
		vBoxSupplier.getChildren().remove(1);
		vBoxSupplier.getChildren().add(0,cmbFind);
		//vBoxSupplier.getChildren().add(1,cmbFind);
		vBoxSupplier.getChildren().add(2,cmbReciver);

		cmbItemName.setPrefWidth(435);
		cmbItemName.setPrefHeight(28);

		vBoxItemName.getChildren().remove(1);
		vBoxItemName.getChildren().add(1,cmbItemName);

		cmbRemarks.setPrefWidth(997);
		cmbRemarks.setPrefHeight(28);

		vBoxRemarks.getChildren().clear();
		vBoxRemarks.getChildren().add(cmbRemarks);
	}

	


	public static class ProductDetails{


		private SimpleStringProperty itemId;
		private SimpleStringProperty itemName;
		private SimpleStringProperty unit;
		private SimpleDoubleProperty unitQuantity;
		private SimpleDoubleProperty quantity;
		private SimpleStringProperty delete;




		public ProductDetails(String itemId,String itemName,String unit,double unitQuantity,double quantity,String delete) {
			this.itemId = new SimpleStringProperty(itemId);
			this.itemName = new SimpleStringProperty(itemName);
			this.unit = new SimpleStringProperty(unit);

			this.unitQuantity = new SimpleDoubleProperty(unitQuantity);
			this.quantity = new SimpleDoubleProperty(quantity);


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

		public String getDelete() {
			return delete.get();
		}
		public void setDelete(String delete) {
			this.delete = new SimpleStringProperty(delete);
		}




	}
	public static class InvoiceList{


		private SimpleStringProperty invoiceNo;
		private SimpleStringProperty date;
		private SimpleStringProperty receiverName;

		public InvoiceList(String invoiceNo,String date,String receiverName) {
			this.invoiceNo = new SimpleStringProperty(invoiceNo);
			this.date = new SimpleStringProperty(date);
			this.receiverName = new SimpleStringProperty(receiverName);
		}

		public String getInvoiceNo() {
			return invoiceNo.get();
		}

		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = new SimpleStringProperty(invoiceNo);
		}

		public String getDate() {
			return date.get();
		}

		public void setDate(String date) {
			this.date = new SimpleStringProperty(date);
		}

		public String getReceiverName() {
			return receiverName.get();
		}

		public void setReceiverName(String receiverName) {
			this.receiverName =  new SimpleStringProperty(receiverName);
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


	/*public String getDateWarrenty() {
		if(getCheckWarrenty()) {
			if(dateWarrenty.getValue() != null)
				return dateWarrenty.getValue().toString();
			else
				return "";
		}else {
			return "";
		}
	}*/


	/*public void setDateWarrentyString(String dateWarrenty) {
		this.dateWarrenty.setDisable(false);
		this.dateWarrenty.getEditor().setText(dateWarrenty);
	}
	public void setDateWarrenty(Date dateWarrenty) {
		if(dateWarrenty != null) {
			int dd=Integer.valueOf(new SimpleDateFormat("dd").format(dateWarrenty));
			int mm=Integer.valueOf(new SimpleDateFormat("MM").format(dateWarrenty));
			int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(dateWarrenty));
			this.dateWarrenty.setValue(LocalDate.of(yy,mm,dd));
		}else {
			this.dateWarrenty.getEditor().setText("");
		}
	}*/


	

	public String getCmbUnit() {
		if(cmbUnit.getValue() != null)
			return cmbUnit.getValue().toString().trim();
		else return "";
	}


	public void setCmbUnit(String cmbUnit) {
		this.cmbUnit.setValue(cmbUnit);
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


	public String getCmbReceiver() {
		if(cmbReciver.getValue() != null)
			return cmbReciver.getValue().toString().trim();
		else return "";
	}


	public void setCmbReceiver(String cmbReceiver) {
		this.cmbReciver.setValue(cmbReceiver);
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

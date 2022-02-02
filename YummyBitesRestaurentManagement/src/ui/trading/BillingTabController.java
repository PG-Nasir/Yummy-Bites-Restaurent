package ui.trading;



import java.io.InputStream;
import java.net.URL;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.jdesktop.swingx.rollover.TableRolloverProducer;
import org.joda.time.DateTimeComparator;

import com.a.a.a.g.m.n;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import com.sun.javafx.scene.control.skin.TableViewSkin;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import shareClasses.AlertMaker;

import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.LoadedInfo;
import shareClasses.Notification;
import shareClasses.NumberField;
import shareClasses.SalesCounter;
import shareClasses.SessionBeam;
import shareClasses.LoadedInfo.CategoryInfo;
import shareClasses.LoadedInfo.ItemInfo;
import shareClasses.LoadedInfo.PackageInfo;
import shareClasses.LoadedInfo.PackageItemInfo;
import shareClasses.LoadedInfo.RawMaterialsInfo;
import ui.trading.SalesController.ProductDetails;
import ui.trading.TableNameSettingController.TableName;

public class BillingTabController implements Initializable{




	Tab tab;
	TabPane tabPane;

	@FXML
	ImageView imageView;
	@FXML
	ImageView imageViewAdd;


	@FXML
	TextField txtContactNo;
	@FXML
	TextArea txtAddress;
	@FXML
	TextField txtQuantity;
	@FXML
	TextField txtStock;
	@FXML
	TextField txtPrice;
	@FXML
	TextField txtTotal;
	@FXML
	TextField txtTotalAmount;
	@FXML
	TextField txtVat;
	@FXML
	TextField txtVatAmount;
	@FXML
	TextField txtServiceChargeAmount;
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
	TextField txtTakenAmount;
	@FXML
	TextField txtChangeAmount;
	@FXML
	TextField txtPaidAmount;
	@FXML
	TextField txtCheckNo;
	@FXML
	TextField txtDueCheckNo;
	@FXML
	TextField txtDueAmount;

	@FXML
	MenuItem menuItemInvoicePrint;
	@FXML
	MenuItem menuItemKitchenCopyPrint;

	@FXML
	SplitMenuButton btnSplitPrint;

	@FXML
	Button btnFind;
	@FXML
	Button btnTableAdd;
	@FXML
	Button btnAdd;
	@FXML
	Button btnConfirm;
	@FXML
	Button btnCancelOrder;
	/*@FXML
	Button btnPrint;*/
	@FXML
	Button btnClose;
	@FXML
	Button btnPay;
	@FXML
	Button btnNewBill;


	@FXML
	DatePicker date;

	@FXML
	VBox vBoxCustomer;
	@FXML
	VBox vBoxItemName;
	@FXML
	VBox vBoxApprovedBy;
	@FXML
	VBox vBoxCategory;
	@FXML
	HBox hBoxRemarks;
	@FXML
	HBox hBoxTableInfo;
	@FXML
	HBox hBoxItemName;

	@FXML
	ComboBox<String> cmbOrderType;

	MenuItem menuItemDeleteTableInfo;
	MenuItem menuItemDeleteItemDetails;
	MenuItem menuItemTableNameSetting;

	ContextMenu contexItemDetails;
	ContextMenu contexTableDetails;
	ContextMenu contexTableNameSetting;


	FxComboBox cmbCategory=new FxComboBox<>();
	FxComboBox cmbCustomer=new FxComboBox<>();
	FxComboBox cmbItemName=new FxComboBox<>();
	FxComboBox cmbRemarks = new FxComboBox<>();
	FxComboBox cmbTableName = new FxComboBox<>();

	@FXML
	RadioButton radioCash;
	@FXML
	RadioButton radioCard;
	@FXML
	RadioButton radioBkash;

	CheckBox checkReturn = new CheckBox("Return Item");

	ToggleGroup groupPaymentMethod;

	NumberField numberField = new NumberField();

	@FXML
	TreeView<String> treeCategory;
	TreeItem<String> rootNode = new TreeItem<>("All Items Category");

	@FXML
	ListView<String> listTableInfo;

	@FXML
	private TableView<ItemDetails> tableItemDetails;

	ObservableList<ItemDetails> listItemDetails = FXCollections.observableArrayList();	
	@FXML
	private TableColumn<ItemDetails, String> itemNameCol;
	@FXML
	private TableColumn<ItemDetails, String> statusCol;
	@FXML
	private TableColumn<ItemDetails, ItemDetails> quantityCol;
	@FXML
	private TableColumn<ItemDetails, String> priceCol;
	@FXML
	private TableColumn<ItemDetails, String> totalAmountCol;



	@FXML
	private TableView<ItemList> tableItemList;

	ObservableList<ItemList> listItemList = FXCollections.observableArrayList();	
	@FXML
	private TableColumn<ItemList, String> itemNameListCol;
	@FXML
	private TableColumn<ItemList, String> priceListCol;

	private ContextMenu tableContextMenu;

	private DatabaseHandler databaseHandler;
	private String sql;

	//this String variable for Payment Method Id
	private boolean isInvoiceConfirmed;
	private boolean isInvoicePay;
	private boolean isInvoicePrint;
	private boolean isInvoiceNew;
	private boolean isInvoiceEdit;
	private String invoiceNo;
	private int activeStatus;

	private final int active = 1;
	private final int canceled = 0;

	private final int deActive = 0;
	
	private final int itemType = 1;
	private final int packageType = 2;
	private final int packageItemType = 3;
	private final int rawMaterialsType = 4;


	private final int finishedGoods = 1;
	private final int rawMaterials=2;

	private String paymentMethodHeadId="2";

	private DialogFindInvoice dialogFindInvoice;

	private HashMap map;
	//this String variable for Sales type
	private String type = "3";
	private String returnType = "4";
	private String genarelCustomerLedger = "16";
	//ledger Type
	private String CustomerLedgerType = "2";

	private Date invoiceDate;
	private DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
	private double previousPaidAmount;
	private double currentPaidAmount;


	Calendar calender = Calendar.getInstance();

	private DecimalFormat dfId = new DecimalFormat("#00");

	private int currentCounter=0;
	private SalesCounter salesCounterArray[] = new SalesCounter[5];

	private DecimalFormat df = new DecimalFormat("#0.00");
	private DecimalFormat dfMoney = new DecimalFormat("#0.0");
	private DecimalFormat dfRoundMoney = new DecimalFormat("#0");

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

		categoryTreeLoad();
		setCmpAction();
		setCmpFocusAction();
		refreshAction();
		tableNameLoad();
		//cmbFindLoad();
	}

	@FXML
	private void btnAddAction(ActionEvent event) {
		if(isInvoiceDateExist() || isInvoiceNew()) {
			if(addValidationCheck()) {
				//There Have Many local Variable for temporary Use
				String itemId;
				int unitQuantity=0,quantity=0;
				double quantityPerUnit,free=0,unitPrice=0,price=0,purchasePrice = 0,totalAmount=0,netAmount=0,discount=0;

				unitQuantity = Integer.valueOf(getTxtQuantity());



				quantity = unitQuantity ;

				unitPrice = Double.valueOf(getTxtPrice());
				price = unitPrice;
				totalAmount = unitPrice * unitQuantity;

				netAmount  = totalAmount - discount;

				itemId = getItemId(getCmbItemName());
				purchasePrice = getItemPurchasePrice(itemId);

				int rowCount = 0;

				if(isInvoiceNew()) {
					for(rowCount = 0;rowCount < listItemDetails.size();rowCount++) {
						ItemDetails  tempPd= listItemDetails.get(rowCount);
						if(tempPd.getItemId().equals(itemId)) {

							listItemDetails.remove(rowCount);
							listItemDetails.add(rowCount,new ItemDetails(itemId, getCmbItemName(), "", unitQuantity, quantity, unitPrice, unitPrice ,purchasePrice, totalAmount, discount, netAmount,itemType,true,true,this));	

							clearTxtAfterItemAdd();

							break;
						}
					}
					if(rowCount == listItemDetails.size()) {

						listItemDetails.add(new ItemDetails(itemId, getCmbItemName(), "", unitQuantity, quantity, unitPrice, unitPrice, purchasePrice,totalAmount, discount, netAmount,itemType,true,true,this));
						clearTxtAfterItemAdd();

					}
				}else {
					if(getCheckReturn()) {
						for(rowCount = 0;rowCount < listItemDetails.size();rowCount++) {
							ItemDetails  tempPd= listItemDetails.get(rowCount);
							if(tempPd.getItemId().equals(itemId) && !tempPd.isNew()) {
								if(tempPd.getQuantity()>= quantity) {
									listItemDetails.add(new ItemDetails(itemId, getCmbItemName(), "", unitQuantity, quantity, unitPrice, unitPrice, purchasePrice,totalAmount, discount, netAmount,itemType,true,false,tempPd.getQuantity(),this));
									clearTxtAfterItemAdd();
									break;
								}else {
									new Notification(Pos.TOP_CENTER, "Warning graphic", "Return Quantity is not Exist....!","Return Quantity Can not Exist From Sold Quantity...");
									break;
								}

							}
						}
						if(rowCount == listItemDetails.size()) {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "This Item Not Sold In This Bill....!","You Can Not Take Return This Item...\nBecouse This Item Not Sold In This Bill...");
						}
					}else {
						for(rowCount = 0;rowCount < listItemDetails.size();rowCount++) {
							ItemDetails  tempPd= listItemDetails.get(rowCount);
							if(tempPd.getItemId().equals(itemId) && tempPd.isNew()) {
								listItemDetails.remove(rowCount);
								listItemDetails.add(rowCount,new ItemDetails(itemId, getCmbItemName(), "", unitQuantity, quantity, unitPrice, unitPrice ,purchasePrice, totalAmount, discount, netAmount,itemType,true,true,this));	
								clearTxtAfterItemAdd();
								break;
							}
						}
						if(rowCount == listItemDetails.size()) {
							listItemDetails.add(new ItemDetails(itemId, getCmbItemName(), "", unitQuantity, quantity, unitPrice, unitPrice, purchasePrice,totalAmount, discount, netAmount,itemType,true,true,this));
							clearTxtAfterItemAdd();
						}
					}

				}
				tableItemDetails.setItems(listItemDetails);
				netAmountCount();

			}
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Cannot change Bill from the previous days....!","Any Previous Day Bill Can Not Change...\nYou Should Create New Bill For Sale...");
			btnFind.requestFocus();
		}

	}



	@FXML
	private void btnConfirmAction(ActionEvent event) {
		try {

			if(saveValidaionCheck()) {
				if(confirmationCheck("Confirm  this Bill?")) {

					String commonTransection="";
					String cashTransection="";
					String cardTransection="";
					String discountTransection="";
					String serviceChargeTransection="";
					String vatTransection="";


					String Customerid = getCustomerId();

					String CustomerledgerId= getCustomerLedgerId(getCmbCustomer());


					String SalesLedger = "8";
					String SalesDiscountLedger = "11";
					String SalesVatLedger = "77";
					String ServiceChargeLedger = "23";
					String d_l_id;
					String c_l_id;

					String tableList="";
					for(int i=0; i<listTableInfo.getItems().size();i++) {
						tableList += listTableInfo.getItems().get(i)+",";
					}

					if(!isInvoiceConfirmed() && isInvoiceNew()) {
						maxInvoiceNoSet();

						String sql = "insert into tbInvoice(invoice,"
								+ " tradingDate, "
								+ "SellerCustomerID, "
								+ "SellerCustomerName, "
								+ "Mobileno, "
								+ "Address, "
								+ "totalAmount,"
								+ " vat, "
								+ "vatAmount,"
								+ " serviceChargeAmount, "
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
								+ "orderType,"
								+ "Date,"
								+ "transectionID,"
								+ "costID,"
								+ "cashId,"
								+ "cardId,"
								+ "vatId,"
								+ "serviceChargeId,"
								+ "discountID,"
								+ "paytype, "
								+ "remarks, "
								+ "entryTime, "
								+ "entryBy) values('" + getInvoiceNo() + "', "
								+ "GETDATE(),"
								+ " '" + Customerid + "', "
								+ "'" + getCmbCustomer() + "',"
								+ "'" + getTxtContactNo() + "',"
								+ "'" + getTxtAddress() + "',"
								+ "'" + getTxtTotalAmount() + "',"
								+ "'" + getTxtVat() + "',"
								+ "'" + getTxtVatAmount()+ "',"
								+ "'" + getTxtServiceChargeAmount()+ "',"
								+ "'" + getTxtDiscountPercent() + "',"
								+ "'" + getTxtPercentDiscount() + "',"
								+ "'" + getTxtManualDiscount() + "',"
								+ "'" + getTxtTotalDiscount() + "',"
								+ "'" + getTxtNetAmount() + "',"
								+ "'0',"
								+ "'0', "
								+ "'0', "
								+ " '"+getTxtNetAmount()+"',"
								+ "'" + type + "',"
								+ "'" + getCmbOrderType() + "',"
								+ "GETDATE(),"
								+ "'"+commonTransection+"',"
								+ "'','"+cashTransection+"',"
								+ "'"+cardTransection+"',"
								+ "'"+vatTransection+"',"
								+ "'"+serviceChargeTransection+"',"
								+ "'"+discountTransection+"',"
								+ "'',"
								+ "'" + tableList+"#Remarks="+getCmbRemarks() + "',"
								+ "CURRENT_TIMESTAMP, "
								+ "'" + SessionBeam.getUserId() + "')";

						databaseHandler.execAction(sql);

						//set current date
						setInvoiceDate(new Date());
					}

					if(isInvoiceDateExist() || isInvoiceNew()) {
						String invoiceAutoId = getAutoInvoiceNo(getInvoiceNo());


						String[] cashTId=null;
						String[] cardTId=null;
						sql = "select isnull(cashid,'')as cashid,isnull(cardId,'')as cardId from tbInvoice where Invoice = '"+getInvoiceNo()+"' and AutoId='"+invoiceAutoId+"' and type = "+type+"";
						ResultSet rs = databaseHandler.execQuery(sql);
						if(rs.next()) {
							cashTId = rs.getString("Cashid").split(",");
							cardTId = rs.getString("CardId").split(",");

							for(int i=0;i<cashTId.length;i++) {
								sql = "update tbaccftransection set c_l_id = '"+CustomerledgerId+"' where transectionid = '"+cashTId[i]+"' and type = '"+type+"'";
								databaseHandler.execAction(sql);
							}
							for(int i=0;i<cardTId.length;i++) {
								sql = "update tbaccftransection set c_l_id = '"+CustomerledgerId+"' where transectionid = '"+cardTId[i]+"' and type = '"+type+"'";	
								databaseHandler.execAction(sql);
							}
						}	

						if(Double.valueOf(getTxtTotalAmount())>=0) {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and c_l_id='"+SalesLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								commonTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtTotalAmount()+"',d_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and c_l_id = '"+SalesLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}else {
								commonTransection=transectionAutoId();
								d_l_id = CustomerledgerId;
								c_l_id = SalesLedger;
								sql ="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoId+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+txtTotalAmount.getText()+"','Sales','"+simpleDateFormate.format(getInvoiceDate())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbSales set transectionId='"+commonTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"'";
										databaseHandler.execAction(sql);*/

							}


						}


						//This Step For Discount Update
						if (Double.parseDouble(getTxtTotalDiscount())>0) {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and d_l_id='"+SalesDiscountLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								discountTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtTotalDiscount()+"' ,c_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  d_l_id = '"+SalesDiscountLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}else {
								discountTransection=transectionAutoId();
								d_l_id = SalesDiscountLedger;
								c_l_id = CustomerledgerId;
								sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoId+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+getTxtTotalDiscount()+"','Sales Discount','"+simpleDateFormate.format(getInvoiceDate())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbSales set discountid='"+discountTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"' ";
										databaseHandler.execAction(sql);*/

							}

						}else {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and d_l_id='"+SalesDiscountLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								discountTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtTotalDiscount()+"' ,c_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  d_l_id = '"+SalesDiscountLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}
						}

						//This Step For Vat Update
						if (Double.valueOf(getTxtVatAmount())>0) {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and c_l_id='"+SalesVatLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								vatTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtVatAmount()+"' ,d_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  c_l_id = '"+SalesVatLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}else {
								vatTransection=transectionAutoId();
								d_l_id = CustomerledgerId;
								c_l_id = SalesVatLedger;
								sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoId+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+getTxtVatAmount()+"','Sales Vat','"+simpleDateFormate.format(getInvoiceDate())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbSales set vatId='"+vatTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"' ";
										databaseHandler.execAction(sql);*/

							}

						}else {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and c_l_id='"+SalesVatLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								vatTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtVatAmount()+"' ,d_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  c_l_id = '"+SalesVatLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}
						}


						//This Step For Service Charge Update
						if (Double.valueOf(getTxtServiceChargeAmount())>0) {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and c_l_id='"+ServiceChargeLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								serviceChargeTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtServiceChargeAmount()+"' ,d_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  c_l_id = '"+ServiceChargeLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}else {
								serviceChargeTransection=transectionAutoId();
								d_l_id = CustomerledgerId;
								c_l_id = ServiceChargeLedger;
								sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoId+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+getTxtServiceChargeAmount()+"','Sales Vat','"+simpleDateFormate.format(getInvoiceDate())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbSales set vatId='"+vatTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"' ";
										databaseHandler.execAction(sql);*/

							}

						}else {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and c_l_id='"+ServiceChargeLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								serviceChargeTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtServiceChargeAmount()+"' ,d_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  c_l_id = '"+ServiceChargeLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}
						}


						//due = Double.parseDouble(txtnetAmount.getText()) - Double.parseDouble(txtPaidAmount.getText());
						sql = "update tbInvoice set " + 
								"SellerCustomerID='"+Customerid+"',\r\n" + 
								"SellerCustomerName='"+getCmbCustomer()+"', \r\n" + 
								"MobileNo='"+getTxtContactNo()+"',\r\n" + 
								"address='"+getTxtAddress()+"',\r\n" + 
								"totalAmount='"+getTxtTotalAmount()+"',\r\n" + 
								"vat='"+getTxtVat()+"',\r\n" + 
								"vatAmount = '"+getTxtVatAmount()+"',\r\n" + 
								"serviceChargeAmount = '"+getTxtServiceChargeAmount()+"',\r\n" + 
								"discount= '"+getTxtDiscountPercent()+"', \r\n" + 
								"percentDiscount='"+getTxtPercentDiscount()+"',\r\n" + 
								"ManualDiscount='"+getTxtManualDiscount()+"',\r\n" + 
								"totalDiscount='"+getTxtTotalDiscount()+"',\r\n" + 
								"netAmount='"+getTxtNetAmount()+"',\r\n" + 
								"due='"+getTxtNetAmount()+"'-paid,\r\n" + 
								"transectionID='"+commonTransection+"',\r\n" + 
								"vatId = '"+vatTransection+"', " +
								"serviceChargeId = '"+serviceChargeTransection+"', " +
								"discountID = '"+discountTransection+"', " + 
								"orderType = '"+getCmbOrderType()+"', " + 
								"remarks = '"+tableList+"#Remarks="+getCmbRemarks()+"', " + 
								"PromissDate = '', " + 
								"entryTime=CURRENT_TIMESTAMP, " + 
								"entryBy='"+SessionBeam.getUserId()+"' where invoice='"+getInvoiceNo()+"' and type = '"+type+"'";

						databaseHandler.execAction(sql);

						sql = "delete from tbStore where invoiceNo = '"+getInvoiceNo()+"' and invoiceAutoNo = '"+invoiceAutoId+"';";
						databaseHandler.execAction(sql);


						for(int i=0;i<listItemDetails.size();i++) {
							ItemDetails temPd = listItemDetails.get(i);
							//if(temPd.isNew()) {
							if(temPd.getItemType() == itemType){
								if(temPd.isSales()) {
									sql = "insert into tbStore ("
											+ "invoiceAutoNo,"
											+ "InvoiceNo,"
											+ " itemId,"
											+ " itemName,"
											+ "isHaveImei,"
											+ " packageId,"
											+ " itemType,"
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
											+ "values('" +invoiceAutoId  + "',"
											+ "'" + getInvoiceNo() + "',"
											+ "'" + temPd.getItemId() + "',"
											+ "'" + temPd.getItemName() + "',"
											+ "'0',"
											+ "'',"
											+ "'"+temPd.getItemType()+"',"
											+ "'" + Customerid + "',"
											+ "'" + temPd.getItemId() + "',"
											+ "'" + temPd.getUnit() + "',"
											+ "'" + temPd.getUnitQuantity() + "',"
											+ "'" + temPd.getQuantity() + "',"
											+ "'',"
											+ "'',"
											+ "'" + temPd.getPrice() + "',"
											+ "'" + temPd.getUnitPrice() + "',"	
											+ "'" +	temPd.getPrice() + "',"
											+ "'" +	temPd.getPrice() + "',"
											+ "'" +	temPd.getPrice() + "',"
											+ "'" + temPd.getTotalAmount() + "',"
											+ "'" + temPd.getDiscount() + "',"
											+ "'" + temPd.getDiscount()/temPd.getQuantity() + "',"
											+ "'" + temPd.getNetAmount() + "',"
											+ "'" + type + "',"
											+ "'0',"
											+ "'',"
											+ "GETDATE(),"
											+ "CURRENT_TIMESTAMP, "
											+ "'" + SessionBeam.getUserId() + "' )";
									
									databaseHandler.execAction(sql);
									
									ArrayList<RawMaterialsInfo> tempRawMaterialas= LoadedInfo.getRawMalerialsInfoistByItemId(temPd.getItemId());
									if(tempRawMaterialas != null){
										for(int index = 0;index<tempRawMaterialas.size();index++){
											sql = "insert into tbStore ("
													+ "invoiceAutoNo,"
													+ "InvoiceNo,"
													+ " itemId,"
													+ " itemName,"
													+ "isHaveImei,"
													+ " packageId,"
													+ " itemType,"
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
													+ "values('" +invoiceAutoId  + "',"
													+ "'" + getInvoiceNo() + "',"
													+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
													+ "'" + tempRawMaterialas.get(index).getItemName() + "',"
													+ "'0',"
													+ "'',"
													+ "'"+rawMaterialsType+"',"
													+ "'" + Customerid + "',"
													+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
													+ "'" + tempRawMaterialas.get(index).getUnit() + "',"
													+ "'" + tempRawMaterialas.get(index).getUnitQuantity()*temPd.getQuantity() + "',"
													+ "'" + tempRawMaterialas.get(index).getQuantity()*temPd.getQuantity() + "',"
													+ "'',"
													+ "'',"
													+ "'" +tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" + tempRawMaterialas.get(index).getPrice() + "',"	
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" + tempRawMaterialas.get(index).getTotalPrice()*temPd.getQuantity() + "',"
													+ "'0',"
													+ "'0',"
													+ "'" + tempRawMaterialas.get(index).getTotalPrice()*temPd.getQuantity() + "',"
													+ "'" + type + "',"
													+ "'0',"
													+ "'',"
													+ "GETDATE(),"
													+ "CURRENT_TIMESTAMP, "
													+ "'" + SessionBeam.getUserId() + "' )";
											
											databaseHandler.execAction(sql);
										}
									}
									
								}else {
									sql = "insert into tbStore ("
											+ "invoiceAutoNo,"
											+ "InvoiceNo,"
											+ " itemId,"
											+ " itemName,"
											+ "isHaveImei,"
											+ " packageId,"
											+ " itemType,"
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
											+ "values('" +invoiceAutoId  + "',"
											+ "'" + getInvoiceNo() + "',"
											+ "'" + temPd.getItemId() + "',"
											+ "'" + temPd.getItemName() + "',"
											+ "'0',"
											+ "'',"
											+ "'"+temPd.getItemType()+"',"
											+ "'" + temPd.getItemId() + "',"
											+ "'" + Customerid + "',"
											+ "'" + temPd.getUnit() + "',"
											+ "'" + temPd.getUnitQuantity() + "',"
											+ "'" + temPd.getQuantity() + "',"
											+ "'',"
											+ "'',"
											+ "'" + temPd.getPrice() + "',"
											+ "'" + temPd.getUnitPrice() + "',"	
											+ "'" +	temPd.getPrice() + "',"
											+ "'" +	temPd.getPrice() + "',"
											+ "'" +	temPd.getPrice() + "',"
											+ "'" + temPd.getTotalAmount() + "',"
											+ "'" + temPd.getDiscount() + "',"
											+ "'" + temPd.getDiscount()/temPd.getQuantity() + "',"
											+ "'" + temPd.getNetAmount() + "',"
											+ "'" + returnType + "',"
											+ "'0',"
											+ "'',"
											+ "GETDATE(),"
											+ "CURRENT_TIMESTAMP, "
											+ "'" + SessionBeam.getUserId() + "' )";
									
									databaseHandler.execAction(sql);
									
									ArrayList<RawMaterialsInfo> tempRawMaterialas= LoadedInfo.getRawMalerialsInfoistByItemId(temPd.getItemId());
									if(tempRawMaterialas != null){
										for(int index = 0;index<tempRawMaterialas.size();index++){
											sql = "insert into tbStore ("
													+ "invoiceAutoNo,"
													+ "InvoiceNo,"
													+ " itemId,"
													+ " itemName,"
													+ "isHaveImei,"
													+ " packageId,"
													+ " itemType,"
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
													+ "values('" +invoiceAutoId  + "',"
													+ "'" + getInvoiceNo() + "',"
													+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
													+ "'" + tempRawMaterialas.get(index).getItemName() + "',"
													+ "'0',"
													+ "'',"
													+ "'"+rawMaterialsType+"',"
													+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
													+ "'" + Customerid + "',"
													+ "'" + tempRawMaterialas.get(index).getUnit() + "',"
													+ "'" + tempRawMaterialas.get(index).getUnitQuantity() + "',"
													+ "'" + tempRawMaterialas.get(index).getQuantity()*temPd.getQuantity() + "',"
													+ "'',"
													+ "'',"
													+ "'" +tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" + tempRawMaterialas.get(index).getPrice() + "',"	
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" + tempRawMaterialas.get(index).getTotalPrice()*temPd.getQuantity() + "',"
													+ "'0',"
													+ "'0',"
													+ "'" + tempRawMaterialas.get(index).getTotalPrice()*temPd.getQuantity() + "',"
													+ "'" + returnType + "',"
													+ "'0',"
													+ "'',"
													+ "GETDATE(),"
													+ "CURRENT_TIMESTAMP, "
													+ "'" + SessionBeam.getUserId() + "' )";
											
											databaseHandler.execAction(sql);
										}
									}
								}
							}else if(temPd.getItemType() == packageType){
								if(temPd.isSales()) {
									sql = "insert into tbStore ("
											+ "invoiceAutoNo,"
											+ "InvoiceNo,"
											+ " itemId,"
											+ " itemName,"
											+ "isHaveImei,"
											+ " packageId,"
											+ " itemType,"
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
											+ "values('" +invoiceAutoId  + "',"
											+ "'" + getInvoiceNo() + "',"
											+ "'" + temPd.getItemId() + "',"
											+ "'" + temPd.getItemName() + "',"
											+ "'0',"
											+ "'',"
											+ "'"+temPd.getItemType()+"',"
											+ "'" + Customerid + "',"
											+ "'" + temPd.getItemId() + "',"
											+ "'" + temPd.getUnit() + "',"
											+ "'" + temPd.getUnitQuantity() + "',"
											+ "'" + temPd.getQuantity() + "',"
											+ "'',"
											+ "'',"
											+ "'" + temPd.getPrice() + "',"
											+ "'" + temPd.getUnitPrice() + "',"	
											+ "'" +	temPd.getPrice() + "',"
											+ "'" +	temPd.getPrice() + "',"
											+ "'" +	temPd.getPrice() + "',"
											+ "'" + temPd.getTotalAmount() + "',"
											+ "'" + temPd.getDiscount() + "',"
											+ "'" + temPd.getDiscount()/temPd.getQuantity() + "',"
											+ "'" + temPd.getNetAmount() + "',"
											+ "'" + type + "',"
											+ "'0',"
											+ "'',"
											+ "GETDATE(),"
											+ "CURRENT_TIMESTAMP, "
											+ "'" + SessionBeam.getUserId() + "' )";
									
									databaseHandler.execAction(sql);
									
									ArrayList<PackageItemInfo> tempPackitemList= LoadedInfo.getPackgeItemInfoList(temPd.getItemId());
									
									for(int packIndex=0;packIndex<tempPackitemList.size();packIndex++){
										sql = "insert into tbStore ("
												+ "invoiceAutoNo,"
												+ "InvoiceNo,"
												+ " itemId,"
												+ " itemName,"
												+ "isHaveImei,"
												+ " packageId,"
												+ " itemType,"
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
												+ "values('" +invoiceAutoId  + "',"
												+ "'" + getInvoiceNo() + "',"
												+ "'" + tempPackitemList.get(packIndex).getItemId() + "',"
												+ "'" + tempPackitemList.get(packIndex).getItemName() + "',"
												+ "'0',"
												+ "'"+temPd.getItemId()+"',"
												+ "'"+packageItemType+"',"
												+ "'" + Customerid + "',"
												+ "'" + tempPackitemList.get(packIndex).getItemId() + "',"
												+ "'Pices (1)',"
												+ "'" + tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
												+ "'" + tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
												+ "'',"
												+ "'',"
												+ "'" + tempPackitemList.get(packIndex).getPackagePrice() + "',"
												+ "'" + tempPackitemList.get(packIndex).getPackagePrice() + "',"	
												+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
												+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
												+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
												+ "'" + tempPackitemList.get(packIndex).getTotalPrice()*temPd.getQuantity() + "',"
												+ "'" + tempPackitemList.get(packIndex).getDiscount() + "',"
												+ "'" + tempPackitemList.get(packIndex).getDiscount() + "',"
												+ "'" + tempPackitemList.get(packIndex).getTotalPrice()*temPd.getQuantity() + "',"
												+ "'" + type + "',"
												+ "'0',"
												+ "'',"
												+ "GETDATE(),"
												+ "CURRENT_TIMESTAMP, "
												+ "'" + SessionBeam.getUserId() + "' )";
										
										databaseHandler.execAction(sql);
										
										ArrayList<RawMaterialsInfo> tempRawMaterialas= LoadedInfo.getRawMalerialsInfoistByItemId(tempPackitemList.get(packIndex).getItemId());	
										if(tempRawMaterialas != null){
											for(int index = 0;index<tempRawMaterialas.size();index++){
												sql = "insert into tbStore ("
														+ "invoiceAutoNo,"
														+ "InvoiceNo,"
														+ " itemId,"
														+ " itemName,"
														+ "isHaveImei,"
														+ " packageId,"
														+ " itemType,"
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
														+ "values('" +invoiceAutoId  + "',"
														+ "'" + getInvoiceNo() + "',"
														+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
														+ "'" + tempRawMaterialas.get(index).getItemName() + "',"
														+ "'0',"
														+ "'',"
														+ "'"+rawMaterialsType+"',"
														+ "'" + Customerid + "',"
														+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
														+ "'" + tempRawMaterialas.get(index).getUnit() + "',"
														+ "'" + tempRawMaterialas.get(index).getUnitQuantity()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
														+ "'" + tempRawMaterialas.get(index).getQuantity()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
														+ "'',"
														+ "'',"
														+ "'" +tempRawMaterialas.get(index).getPrice() + "',"
														+ "'" + tempRawMaterialas.get(index).getPrice() + "',"	
														+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
														+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
														+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
														+ "'" + tempRawMaterialas.get(index).getTotalPrice()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
														+ "'0',"
														+ "'0',"
														+ "'" + tempRawMaterialas.get(index).getTotalPrice()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
														+ "'" + type + "',"
														+ "'0',"
														+ "'',"
														+ "GETDATE(),"
														+ "CURRENT_TIMESTAMP, "
														+ "'" + SessionBeam.getUserId() + "' )";
												
												databaseHandler.execAction(sql);
											}
										}
									}
									
									
									
								}else {
									sql = "insert into tbStore ("
											+ "invoiceAutoNo,"
											+ "InvoiceNo,"
											+ " itemId,"
											+ " itemName,"
											+ "isHaveImei,"
											+ " packageId,"
											+ " itemType,"
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
											+ "values('" +invoiceAutoId  + "',"
											+ "'" + getInvoiceNo() + "',"
											+ "'" + temPd.getItemId() + "',"
											+ "'" + temPd.getItemName() + "',"
											+ "'0',"
											+ "'',"
											+ "'"+temPd.getItemType()+"',"
											+ "'" + temPd.getItemId() + "',"
											+ "'" + Customerid + "',"
											+ "'" + temPd.getUnit() + "',"
											+ "'" + temPd.getUnitQuantity() + "',"
											+ "'" + temPd.getQuantity() + "',"
											+ "'',"
											+ "'',"
											+ "'" + temPd.getPrice() + "',"
											+ "'" + temPd.getUnitPrice() + "',"	
											+ "'" +	temPd.getPrice() + "',"
											+ "'" +	temPd.getPrice() + "',"
											+ "'" +	temPd.getPrice() + "',"
											+ "'" + temPd.getTotalAmount() + "',"
											+ "'" + temPd.getDiscount() + "',"
											+ "'" + temPd.getDiscount()/temPd.getQuantity() + "',"
											+ "'" + temPd.getNetAmount() + "',"
											+ "'" + returnType + "',"
											+ "'0',"
											+ "'',"
											+ "GETDATE(),"
											+ "CURRENT_TIMESTAMP, "
											+ "'" + SessionBeam.getUserId() + "' )";
									
									databaseHandler.execAction(sql);
									
									ArrayList<PackageItemInfo> tempPackitemList= LoadedInfo.getPackgeItemInfoList(temPd.getItemId());
									
									for(int packIndex=0;packIndex<tempPackitemList.size();packIndex++){
										sql = "insert into tbStore ("
												+ "invoiceAutoNo,"
												+ "InvoiceNo,"
												+ " itemId,"
												+ " itemName,"
												+ "isHaveImei,"
												+ " packageId,"
												+ " itemType,"
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
												+ "values('" +invoiceAutoId  + "',"
												+ "'" + getInvoiceNo() + "',"
												+ "'" + tempPackitemList.get(packIndex).getItemId() + "',"
												+ "'" + tempPackitemList.get(packIndex).getItemName() + "',"
												+ "'0',"
												+ "'"+temPd.getItemId()+"',"
												+ "'"+packageItemType+"',"
												+ "'" + tempPackitemList.get(packIndex).getItemId() + "',"
												+ "'" + Customerid + "',"
												+ "'Pices (1)',"
												+ "'" + tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
												+ "'" + tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
												+ "'',"
												+ "'',"
												+ "'" + tempPackitemList.get(packIndex).getPackagePrice() + "',"
												+ "'" + tempPackitemList.get(packIndex).getPackagePrice() + "',"	
												+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
												+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
												+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
												+ "'" + tempPackitemList.get(packIndex).getTotalPrice()*temPd.getQuantity() + "',"
												+ "'" + tempPackitemList.get(packIndex).getDiscount() + "',"
												+ "'" + tempPackitemList.get(packIndex).getDiscount() + "',"
												+ "'" + tempPackitemList.get(packIndex).getTotalPrice()*temPd.getQuantity() + "',"
												+ "'" + type + "',"
												+ "'0',"
												+ "'',"
												+ "GETDATE(),"
												+ "CURRENT_TIMESTAMP, "
												+ "'" + SessionBeam.getUserId() + "' )";
										
										databaseHandler.execAction(sql);
										
										ArrayList<RawMaterialsInfo> tempRawMaterialas= LoadedInfo.getRawMalerialsInfoistByItemId(tempPackitemList.get(packIndex).getItemId());
										if(tempRawMaterialas != null){
											for(int index = 0;index<tempRawMaterialas.size();index++){
												sql = "insert into tbStore ("
														+ "invoiceAutoNo,"
														+ "InvoiceNo,"
														+ " itemId,"
														+ " itemName,"
														+ "isHaveImei,"
														+ " packageId,"
														+ " itemType,"
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
														+ "values('" +invoiceAutoId  + "',"
														+ "'" + getInvoiceNo() + "',"
														+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
														+ "'" + tempRawMaterialas.get(index).getItemName() + "',"
														+ "'0',"
														+ "'',"
														+ "'"+rawMaterialsType+"',"
														+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
														+ "'" + Customerid + "',"
														+ "'" + tempRawMaterialas.get(index).getUnit() + "',"
														+ "'" + tempRawMaterialas.get(index).getUnitQuantity()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
														+ "'" + tempRawMaterialas.get(index).getQuantity()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
														+ "'',"
														+ "'',"
														+ "'" +tempRawMaterialas.get(index).getPrice() + "',"
														+ "'" + tempRawMaterialas.get(index).getPrice() + "',"	
														+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
														+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
														+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
														+ "'" + tempRawMaterialas.get(index).getTotalPrice()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
														+ "'0',"
														+ "'0',"
														+ "'" + tempRawMaterialas.get(index).getTotalPrice()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
														+ "'" + returnType + "',"
														+ "'0',"
														+ "'',"
														+ "GETDATE(),"
														+ "CURRENT_TIMESTAMP, "
														+ "'" + SessionBeam.getUserId() + "' )";
												
												databaseHandler.execAction(sql);
											}
										}
									}
									
									
								}
							}
							


							

						}



						setInvoiceConfirmed(true);

						new Notification(Pos.TOP_CENTER, "Information graphic", "Confirmed Successfull....!","Invoice Confirmed Successfully...");
						btnSplitPrint.requestFocus();
						menuItemKitchenCopyPrintAction(null);
						//btnPrintAction(null);
						//btnRefreshAction(null);
						//cmbFindLoad();
					}else {
						new Notification(Pos.TOP_CENTER, "Information graphic", "Cannot change Bill from the previous days....!","Any Previous Day Bill Can Not Change...\nYou Can Recived Due Payment From This Bill...");
						btnPay.requestFocus();
					}

				}
			}
			//}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	@FXML
	private void btnCancelOrderAction(ActionEvent event) {
		try {
			if(!isInvoicePay()) {
				if(confirmationCheck("Cancel Order?")) {
					cancelAction();
					refreshAction();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Information graphic", "You Can't Cancel Order after bill pay....","You Can't Cancel Order after Bill Pay....\n You Can Take Return Item if Necessary....");
				checkReturn.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}



	@FXML
	private void btnFindAction(ActionEvent event) {
		if(dialogFindInvoice == null) dialogFindInvoice = new DialogFindInvoice(tabPane);
		dialogFindInvoice.show();

	}

	@FXML
	private void btnCloseAction(ActionEvent event) {
		if(isInvoiceConfirmed()) {
			if(isInvoicePay()) {
				if(confirmationCheck("Close This Bill?"))
				{
					if(tabPane.getTabs().size()>1) {

						tabPane.getTabs().remove(tab);
					}else{
						btnNewBillAction(null);
						tabPane.getTabs().remove(tab);

					}
				}

			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Pay Bill First....!","Please Confirm Bill Payment.....");
				btnPay.requestFocus();
			}
		}else {
			if(confirmationCheck("Close This Bill?")) {
				if(tabPane.getTabs().size()>1) {

					tabPane.getTabs().remove(tab);
				}
				else{
					btnNewBillAction(null);
					tabPane.getTabs().remove(tab);

				}
			}		
		}
	}

	@FXML
	private void btnPrintAction(ActionEvent event) {
		try {
			if(isInvoiceConfirmed()) {
				if(isInvoiceIdExist(getInvoiceNo())) {

					map.put("orgName", SessionBeam.getOrgName());
					map.put("orgAddress", SessionBeam.getOrgAddress());
					map.put("orgContact", SessionBeam.getOrgContact());

					String report = "src/resource/reports/Invoices/SalesIvoiceCustomerCopy.jrxml";
					//report="LabStatementReport/PurchaseIvoice.jrxml";
					JasperDesign jd = JRXmlLoader.load(report);
					JRDesignQuery jq = new JRDesignQuery();


					sql = "select pd.invoiceNo,pd.type,pd.itemId,substring(pd.itemName,charindex('-',pd.itemname)+1,DATALENGTH(pd.itemName)) as itemName,pd.itemType,isnull(pd.SalesPrice,0) as salesPrice,sum(pd.qty) as qty,sum(ISNULL(pd.totalamount,0)) as ptamount,sum(isnull(pd.netAmount,0)) as pnetAmount,i.SellerCustomerName as customer,i.mobileNo,i.address,ISNULL(i.totalAmount,0) as totalAmount,ISNULL(i.vat,0) as vatPercent,ISNULL(i.vatAmount,0) as vatAmount,ISNULL(i.serviceChargeAmount,0) as serviceCharge,ISNULL(i.manualDiscount,0) as ManualDiscount,ISNULL(i.totalDiscount,0) as TotalDiscount,ISNULL(i.netAmount,0) as netAmount, ISNULL(i.cash,0) as cash,ISNULL(i.card,0) as card,ISNULL(i.paid,0) as paid,isnull(i.due,0) as due,i.date,l.displayName,  dbo.number(i.netAmount) as Taka   from tbInvoice i \r\n" + 
							"join tbStore pd  on pd.invoiceAutoNo = i.AutoId   \r\n" + 
							"join tblogin l  on l.user_id = i.entryby \r\n" + 
							"where i.Invoice = '"+getInvoiceNo()+"' and i.type = '"+type+"' and (pd.itemType = '"+packageType+"' or pd.itemType = '"+itemType+"') \r\n" + 
							"group by pd.invoiceNo,pd.type,pd.itemId,pd.itemName,pd.itemType,SalesPrice,SellerCustomerName,Mobileno,Address,i.totalAmount,vat,vatAmount,serviceChargeAmount,manualDiscount,totalDiscount,i.netAmount,cash,Card,paid,due,i.date,displayName\r\n" + 
							"order by pd.type ";
					ResultSet rs = databaseHandler.execQuery(sql);
					int rowCount=0;
					int subRowCount=0;
					while(rs.next()) {
						if(rs.getInt("ItemType")==packageType){
							subRowCount += LoadedInfo.getPackgeItemInfoList(rs.getString("ItemId")).size();
						}
						rowCount++;
					}

					jq.setText(sql);
					jd.setQuery(jq);
					jd.setPageHeight(190+(rowCount*11)+(subRowCount*20));

					JasperReport jr = JasperCompileManager.compileReport(jd);
					JasperPrint jp = JasperFillManager.fillReport(jr, map, databaseHandler.conn);
					JasperViewer.viewReport(jp, false);
					JasperPrintManager.printReport(jp, false);
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Sorry....!","This Invoice No Not Exist.....\nFind a Exist Invoice...");
					txtQuantity.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Confirm First....!","Please Confirm This Bill First.....");
				cmbCategory.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void menuItemInvoicePrintAction(ActionEvent event) {
		try {
			if(isInvoiceConfirmed()) {
				if(isInvoiceIdExist(getInvoiceNo())) {

					map.put("orgName", SessionBeam.getOrgName());
					map.put("orgAddress", SessionBeam.getOrgAddress());
					map.put("orgContact", SessionBeam.getOrgContact());

					String report = "src/resource/reports/Invoices/SalesIvoiceCustomerCopy.jrxml";
					//report="LabStatementReport/PurchaseIvoice.jrxml";
					JasperDesign jd = JRXmlLoader.load(report);
					JRDesignQuery jq = new JRDesignQuery();


					sql = "select pd.invoiceNo,pd.type,pd.itemId,substring(pd.itemName,charindex('-',pd.itemname)+1,DATALENGTH(pd.itemName)) as itemName,pd.itemType,isnull(pd.SalesPrice,0) as salesPrice,sum(pd.qty) as qty,sum(ISNULL(pd.totalamount,0)) as ptamount,sum(isnull(pd.netAmount,0)) as pnetAmount,i.SellerCustomerName as customer,i.mobileNo,i.address,ISNULL(i.totalAmount,0) as totalAmount,ISNULL(i.vat,0) as vatPercent,ISNULL(i.vatAmount,0) as vatAmount,ISNULL(i.serviceChargeAmount,0) as serviceCharge,ISNULL(i.manualDiscount,0) as ManualDiscount,ISNULL(i.totalDiscount,0) as TotalDiscount,ISNULL(i.netAmount,0) as netAmount, ISNULL(i.cash,0) as cash,ISNULL(i.card,0) as card,ISNULL(i.paid,0) as paid,isnull(i.due,0) as due,i.date,l.displayName,  dbo.number(i.netAmount) as Taka   from tbInvoice i \r\n" + 
							"join tbStore pd  on pd.invoiceAutoNo = i.AutoId   \r\n" + 
							"join tblogin l  on l.user_id = i.entryby \r\n" + 
							"where i.Invoice = '"+getInvoiceNo()+"' and i.type = '"+type+"' and i.type = '"+type+"' and (pd.itemType = '"+packageType+"' or pd.itemType = '"+itemType+"') \r\n" + 
							"group by pd.invoiceNo,pd.type,pd.itemId,pd.itemName,pd.itemType,SalesPrice,SellerCustomerName,Mobileno,Address,i.totalAmount,vat,vatAmount,serviceChargeAmount,manualDiscount,totalDiscount,i.netAmount,cash,Card,paid,due,i.date,displayName\r\n" + 
							"order by pd.type ";
					ResultSet rs = databaseHandler.execQuery(sql);
					int rowCount=0;
					int subRowCount=0;
					while(rs.next()) {
						if(rs.getInt("ItemType")==packageType){
							subRowCount += LoadedInfo.getPackgeItemInfoList(rs.getString("ItemId")).size();
						}
						rowCount++;
					}

					
					jq.setText(sql);
					jd.setQuery(jq);
					jd.setPageHeight(190+(rowCount*11)+(subRowCount*20));

					JasperReport jr = JasperCompileManager.compileReport(jd);
					JasperPrint jp = JasperFillManager.fillReport(jr, map, databaseHandler.conn);
					JasperViewer.viewReport(jp, false);
					JasperPrintManager.printReport(jp, false);
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Sorry....!","This Invoice No Not Exist.....\nFind a Exist Invoice...");
					txtQuantity.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Confirm First....!","Please Confirm This Bill First.....");
				cmbCategory.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void menuItemKitchenCopyPrintAction(ActionEvent event) {
		try {
			if(isInvoiceConfirmed()) {
				if(isInvoiceIdExist(getInvoiceNo())) {

					map.put("orgName", SessionBeam.getOrgName());
					map.put("orgAddress", SessionBeam.getOrgAddress());
					map.put("orgContact", SessionBeam.getOrgContact());

					String report = "src/resource/reports/Invoices/SalesIvoiceKitchenCopy.jrxml";
					//report="LabStatementReport/PurchaseIvoice.jrxml";
					JasperDesign jd = JRXmlLoader.load(report);
					JRDesignQuery jq = new JRDesignQuery();


					sql = "select pd.invoiceNo,pd.type,pd.itemId,pd.itemName,pd.itemType,isnull(pd.SalesPrice,0) as salesPrice,sum(pd.qty) as qty,sum(ISNULL(pd.totalamount,0)) as ptamount,sum(isnull(pd.netAmount,0)) as pnetAmount,i.SellerCustomerName as customer,i.mobileNo,i.address,ISNULL(i.totalAmount,0) as totalAmount,ISNULL(i.vat,0) as vatPercent,ISNULL(i.vatAmount,0) as vatAmount,ISNULL(i.serviceChargeAmount,0) as serviceCharge,ISNULL(i.manualDiscount,0) as ManualDiscount,ISNULL(i.totalDiscount,0) as TotalDiscount,ISNULL(i.netAmount,0) as netAmount, ISNULL(i.cash,0) as cash,ISNULL(i.card,0) as card,ISNULL(i.paid,0) as paid,isnull(i.due,0) as due,substring(i.remarks,0,charindex('#',i.remarks)-1) as tableList,i.orderType,i.date,l.displayName,  dbo.number(i.netAmount) as Taka   from tbInvoice i \r\n" + 
							"join tbStore pd  on pd.invoiceAutoNo = i.AutoId   \r\n" + 
							"join tblogin l  on l.user_id = i.entryby \r\n" + 
							"where i.Invoice = '"+getInvoiceNo()+"' and i.type = '"+type+"' and i.type = '"+type+"' and (pd.itemType = '"+packageType+"' or pd.itemType = '"+itemType+"') \r\n" + 
							"group by pd.invoiceNo,pd.type,pd.itemId,pd.itemName,pd.itemType,SalesPrice,SellerCustomerName,Mobileno,Address,i.totalAmount,vat,vatAmount,serviceChargeAmount,manualDiscount,totalDiscount,i.netAmount,cash,Card,paid,due,i.remarks,i.orderType,i.date,displayName\r\n" + 
							"order by pd.type ";
					ResultSet rs = databaseHandler.execQuery(sql);
					int rowCount=0;
					int subRowCount=0;
					while(rs.next()) {
						if(rs.getInt("ItemType")==packageType){
							subRowCount += LoadedInfo.getPackgeItemInfoList(rs.getString("ItemId")).size();
						}
						rowCount++;
					}
					jq.setText(sql);
					jd.setQuery(jq);
					jd.setPageHeight(160+(rowCount*10)+(subRowCount*9));

					JasperReport jr = JasperCompileManager.compileReport(jd);
					JasperPrint jp = JasperFillManager.fillReport(jr, map, databaseHandler.conn);
					JasperViewer.viewReport(jp, false);
					JasperPrintManager.printReport(jp, false);
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Sorry....!","This Invoice No Not Exist.....\nFind a Exist Invoice...");
					txtQuantity.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Confirm First....!","Please Confirm This Bill First.....");
				cmbCategory.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnPayAction(ActionEvent event) {
		try {
			if(saveValidaionCheck()) {
				if(confirmationCheck("Complete this Bill??\nNetAmount="+getTxtNetAmount()+"\nCurrent Paid = "+getCurrentPaidAmount()+(Double.valueOf(getTxtDueAmount())>=0?"\nDue = "+Double.valueOf(getTxtDueAmount()):"\nRefound="+(-Double.valueOf(getTxtDueAmount()))))) {


					String commonTransection="";
					String cashTransection="";
					String cardTransection="";
					String discountTransection="";
					String serviceChargeTransection="";
					String vatTransection="";


					String Customerid = getCustomerId();
					String paymentLedgerId="";
					String paymentType="";
					String CustomerledgerId= getCustomerLedgerId(getCmbCustomer());


					String SalesLedger = "8";
					String SalesDiscountLedger = "11";
					String SalesVatLedger = "77";
					String ServiceChargeLedger = "23";
					String d_l_id;
					String c_l_id;

					String tableList="";
					for(int i=0; i<listTableInfo.getItems().size();i++) {
						tableList += listTableInfo.getItems().get(i)+",";
					}

					if(!isInvoiceConfirmed() && isInvoiceNew()) {
						maxInvoiceNoSet();

						String sql = "insert into tbInvoice(invoice,"
								+ " tradingDate, "
								+ "SellerCustomerID, "
								+ "SellerCustomerName, "
								+ "Mobileno, "
								+ "Address, "
								+ "totalAmount,"
								+ " vat, "
								+ "vatAmount,"
								+ " serviceChargeAmount, "
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
								+ "orderType,"
								+ "Date,"
								+ "transectionID,"
								+ "costID,"
								+ "cashId,"
								+ "cardId,"
								+ "vatId,"
								+ "serviceChargeId,"
								+ "discountID,"
								+ "paytype, "
								+ "remarks, "
								+ "entryTime, "
								+ "entryBy) values('" + getInvoiceNo() + "', "
								+ "GETDATE(),"
								+ " '" + Customerid + "', "
								+ "'" + getCmbCustomer() + "',"
								+ "'" + getTxtContactNo() + "',"
								+ "'" + getTxtAddress() + "',"
								+ "'" + getTxtTotalAmount() + "',"
								+ "'" + getTxtVat() + "',"
								+ "'" + getTxtVatAmount()+ "',"
								+ "'" + getTxtServiceChargeAmount()+ "',"
								+ "'" + getTxtDiscountPercent() + "',"
								+ "'" + getTxtPercentDiscount() + "',"
								+ "'" + getTxtManualDiscount() + "',"
								+ "'" + getTxtTotalDiscount() + "',"
								+ "'" + getTxtNetAmount() + "',"
								+ "'"+getTxtPaidAmount()+"',"
								+ "'0', "
								+ "'0', "
								+ " '"+getTxtDueAmount()+"',"
								+ "'" + type + "',"
								+ "'" + getCmbOrderType() + "',"
								+ "GETDATE(),"
								+ "'"+commonTransection+"',"
								+ "'','"+cashTransection+"',"
								+ "'"+cardTransection+"',"
								+ "'"+vatTransection+"',"
								+ "'"+serviceChargeTransection+"',"
								+ "'"+discountTransection+"',"
								+ "'',"
								+ "'" + tableList+"#Remarks="+getCmbRemarks() + "',"
								+ "CURRENT_TIMESTAMP, "
								+ "'" + SessionBeam.getUserId() + "')";

						databaseHandler.execAction(sql);
						setInvoiceDate(new Date());
					}



					String invoiceAutoId = getAutoInvoiceNo(getInvoiceNo());

					String[] cashTId=null;
					String[] cardTId=null;
					String cashId	= "";
					String cardId	= "";
					sql = "select isnull(cashid,'')as cashid,isnull(cardId,'')as cardId from tbInvoice where Invoice = '"+getInvoiceNo()+"' and AutoId='"+invoiceAutoId+"' and type = "+type+"";
					ResultSet rs = databaseHandler.execQuery(sql);
					if(rs.next()) {
						cashId =rs.getString("Cashid");
						cardId =rs.getString("CardId");
						cashTId = cashId.split(",");
						cardTId = cardId.split(",");


						for(int i=0;i<cashTId.length;i++) {
							sql = "update tbaccftransection set c_l_id = '"+CustomerledgerId+"' where transectionid = '"+cashTId[i]+"' and type = '"+type+"'";
							databaseHandler.execAction(sql);
						}
						for(int i=0;i<cardTId.length;i++) {
							sql = "update tbaccftransection set c_l_id = '"+CustomerledgerId+"' where transectionid = '"+cardTId[i]+"' and type = '"+type+"'";	
							databaseHandler.execAction(sql);
						}
					}	


					if(getRadioCash()) {
						paymentType = "Cash";
						paymentLedgerId = "3";
					}else if(getRadioCard()) {
						paymentType = "Card";
						paymentLedgerId = "4";
					}else if(getRadioBkash()) {
						paymentType = "BKash";
						paymentLedgerId = "97";
					}


					if(isInvoiceDateExist() || isInvoiceNew()) {
						if(Double.valueOf(getTxtTotalAmount())>=0) {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and c_l_id='"+SalesLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								commonTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtTotalAmount()+"',d_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and c_l_id = '"+SalesLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}else {
								commonTransection=transectionAutoId();
								d_l_id = CustomerledgerId;
								c_l_id = SalesLedger;
								sql ="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoId+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+txtTotalAmount.getText()+"','Sales','"+simpleDateFormate.format(getInvoiceDate())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbSales set transectionId='"+commonTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"'";
								databaseHandler.execAction(sql);*/

							}


						}


						//This Step For Discount Update
						if (Double.parseDouble(getTxtTotalDiscount())>0) {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and d_l_id='"+SalesDiscountLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								discountTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtTotalDiscount()+"' ,c_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  d_l_id = '"+SalesDiscountLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}else {
								discountTransection=transectionAutoId();
								d_l_id = SalesDiscountLedger;
								c_l_id = CustomerledgerId;
								sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoId+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+getTxtTotalDiscount()+"','Sales Discount','"+simpleDateFormate.format(getInvoiceDate())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbSales set discountid='"+discountTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"' ";
								databaseHandler.execAction(sql);*/

							}

						}else {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and d_l_id='"+SalesDiscountLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								discountTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtTotalDiscount()+"' ,c_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  d_l_id = '"+SalesDiscountLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}
						}

						//This Step For Vat Update
						if (Double.valueOf(getTxtVatAmount())>0) {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and c_l_id='"+SalesVatLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								vatTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtVatAmount()+"' ,d_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  c_l_id = '"+SalesVatLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}else {
								vatTransection=transectionAutoId();
								d_l_id = CustomerledgerId;
								c_l_id = SalesVatLedger;
								sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoId+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+getTxtVatAmount()+"','Sales Vat','"+simpleDateFormate.format(getInvoiceDate())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbSales set vatId='"+vatTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"' ";
								databaseHandler.execAction(sql);*/

							}

						}else {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and c_l_id='"+SalesVatLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								vatTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtVatAmount()+"' ,d_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  c_l_id = '"+SalesVatLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}
						}


						//This Step For Service Charge Update
						if (Double.valueOf(getTxtServiceChargeAmount())>0) {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and c_l_id='"+ServiceChargeLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								serviceChargeTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtServiceChargeAmount()+"' ,d_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  c_l_id = '"+ServiceChargeLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}else {
								serviceChargeTransection=transectionAutoId();
								d_l_id = CustomerledgerId;
								c_l_id = ServiceChargeLedger;
								sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoId+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+getTxtServiceChargeAmount()+"','Sales Service Charge','"+simpleDateFormate.format(getInvoiceDate())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
								databaseHandler.execAction(sql);

								/*sql="update tbSales set vatId='"+vatTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"' ";
								databaseHandler.execAction(sql);*/

							}

						}else {
							sql = "select * from tbaccftransection where voucherno='"+invoiceAutoId+"' and c_l_id='"+ServiceChargeLedger+"' and type='"+type+"'";
							rs = databaseHandler.execQuery(sql);
							if(rs.next()) {
								int tranId= rs.getInt("transectionid");
								serviceChargeTransection = String.valueOf(tranId);
								sql = "update tbaccftransection set amount = '"+getTxtServiceChargeAmount()+"' ,d_l_id='"+CustomerledgerId+"' where transectionid='"+tranId+"' and type='"+type+"' and  c_l_id = '"+ServiceChargeLedger+"' and voucherNo='"+invoiceAutoId+"'";
								databaseHandler.execAction(sql);

							}
						}
					}
					if (getCurrentPaidAmount() > 0) {

						String Payment = "insert into tbpaymentHistory (invoiceNo,cash, card,due,depID,type,date,paymentType,flag,entryTime,entryBy) values ( '" + invoiceAutoId + "', '" + getCurrentPaidAmount() + "','0','" + getTxtDueAmount() + "','" + 1 + "','" + type + "',GETDATE(),'"+paymentType+"','" + (isInvoiceNew()?"Cash Sales":"Due Payment") + "',CURRENT_TIMESTAMP, '" + SessionBeam.getUserId() + "' )";
						databaseHandler.execAction(Payment);
						cashTransection=transectionAutoId();
						d_l_id = paymentLedgerId;
						c_l_id = CustomerledgerId;
						sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoId+"','"+type+"','"+d_l_id+"','"+c_l_id+"','"+getCurrentPaidAmount()+"','"+(isInvoiceNew()?"Cash Sales":"Due Payment")+"',GETDATE(),CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);

						if(getRadioCash()){
							cashId +=  cashTransection+",";
						}else{
							cardId +=  cashTransection+",";
						}
					}

					if (Double.valueOf(getTxtDueAmount()) < 0) {
						String Payment = "insert into tbpaymentHistory (invoiceNo,cash, card,due,depID,type,date,paymentType,flag,entryTime,entryBy) values ( '" + invoiceAutoId + "', '" + Math.abs(Double.valueOf(getTxtDueAmount())) + "','0','0','" + 1 + "','" + returnType + "',GETDATE(),'"+paymentType+"','" + "Refound" + "',CURRENT_TIMESTAMP, '" + SessionBeam.getUserId() + "' )";
						databaseHandler.execAction(Payment);
						cashTransection=transectionAutoId();
						d_l_id = CustomerledgerId;
						c_l_id = paymentLedgerId;
						sql="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) values('"+transectionAutoId()+"','"+invoiceAutoId+"','"+returnType+"','"+d_l_id+"','"+c_l_id+"','"+Math.abs(Double.valueOf(getTxtDueAmount()))+"','Refound',GETDATE(),CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
						databaseHandler.execAction(sql);

						if(getRadioCash()){
							cashId +=  cashTransection+",";
						}else{
							cardId +=  cashTransection+",";
						}
					}

					//due = Double.parseDouble(txtnetAmount.getText()) - Double.parseDouble(txtPaidAmount.getText());
					sql = "update tbInvoice set " + 
							"SellerCustomerID='"+Customerid+"',\r\n" + 
							"SellerCustomerName='"+getCmbCustomer()+"', \r\n" + 
							"MobileNo='"+getTxtContactNo()+"',\r\n" + 
							"address='"+getTxtAddress()+"',\r\n" + 
							"totalAmount='"+getTxtTotalAmount()+"',\r\n" + 
							"vat='"+getTxtVat()+"',\r\n" + 
							"vatAmount = '"+getTxtVatAmount()+"',\r\n" + 
							"serviceChargeAmount = '"+getTxtServiceChargeAmount()+"',\r\n" + 
							"discount= '"+getTxtDiscountPercent()+"', \r\n" + 
							"percentDiscount='"+getTxtPercentDiscount()+"',\r\n" + 
							"ManualDiscount='"+getTxtManualDiscount()+"',\r\n" + 
							"totalDiscount='"+getTxtTotalDiscount()+"',\r\n" + 
							"paid='"+getTxtPaidAmount()+"',\r\n" + 
							"netAmount='"+getTxtNetAmount()+"',\r\n" +
							"due='"+getTxtDueAmount()+"',\r\n" + 
							"transectionID='"+commonTransection+"',\r\n" + 
							"cashId='"+cashId+"',\r\n" + 
							"cardId = '"+cardId+"', " +
							"vatId = '"+vatTransection+"', " +
							"serviceChargeId = '"+serviceChargeTransection+"', " +
							"discountID = '"+discountTransection+"', " + 
							"orderType = '"+getCmbOrderType()+"', " + 
							"remarks = '"+tableList+"#Remarks="+getCmbRemarks()+"', " + 
							"PromissDate = '', " + 
							"entryTime=CURRENT_TIMESTAMP, " + 
							"entryBy='"+SessionBeam.getUserId()+"' where invoice='"+getInvoiceNo()+"' and type = '"+type+"'";

					databaseHandler.execAction(sql);

					sql = "delete from tbStore where invoiceNo = '"+getInvoiceNo()+"' and invoiceAutoNo = '"+invoiceAutoId+"';";
					databaseHandler.execAction(sql);


					for(int i=0;i<listItemDetails.size();i++) {
						ItemDetails temPd = listItemDetails.get(i);
						//if(temPd.isNew()) {
						if(temPd.getItemType() == itemType){
							if(temPd.isSales()) {
								sql = "insert into tbStore ("
										+ "invoiceAutoNo,"
										+ "InvoiceNo,"
										+ " itemId,"
										+ " itemName,"
										+ "isHaveImei,"
										+ " packageId,"
										+ " itemType,"
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
										+ "values('" +invoiceAutoId  + "',"
										+ "'" + getInvoiceNo() + "',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + temPd.getItemName() + "',"
										+ "'0',"
										+ "'',"
										+ "'"+temPd.getItemType()+"',"
										+ "'" + Customerid + "',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + temPd.getUnit() + "',"
										+ "'" + temPd.getUnitQuantity() + "',"
										+ "'" + temPd.getQuantity() + "',"
										+ "'',"
										+ "'',"
										+ "'" + temPd.getPrice() + "',"
										+ "'" + temPd.getUnitPrice() + "',"	
										+ "'" +	temPd.getPrice() + "',"
										+ "'" +	temPd.getPrice() + "',"
										+ "'" +	temPd.getPrice() + "',"
										+ "'" + temPd.getTotalAmount() + "',"
										+ "'" + temPd.getDiscount() + "',"
										+ "'" + temPd.getDiscount()/temPd.getQuantity() + "',"
										+ "'" + temPd.getNetAmount() + "',"
										+ "'" + type + "',"
										+ "'0',"
										+ "'',"
										+ "GETDATE(),"
										+ "CURRENT_TIMESTAMP, "
										+ "'" + SessionBeam.getUserId() + "' )";
								
								databaseHandler.execAction(sql);
								
								ArrayList<RawMaterialsInfo> tempRawMaterialas= LoadedInfo.getRawMalerialsInfoistByItemId(temPd.getItemId());
								if(tempRawMaterialas != null){
									for(int index = 0;index<tempRawMaterialas.size();index++){
										sql = "insert into tbStore ("
												+ "invoiceAutoNo,"
												+ "InvoiceNo,"
												+ " itemId,"
												+ " itemName,"
												+ "isHaveImei,"
												+ " packageId,"
												+ " itemType,"
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
												+ "values('" +invoiceAutoId  + "',"
												+ "'" + getInvoiceNo() + "',"
												+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
												+ "'" + tempRawMaterialas.get(index).getItemName() + "',"
												+ "'0',"
												+ "'',"
												+ "'"+rawMaterialsType+"',"
												+ "'" + Customerid + "',"
												+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
												+ "'" + tempRawMaterialas.get(index).getUnit() + "',"
												+ "'" + tempRawMaterialas.get(index).getUnitQuantity()*temPd.getQuantity() + "',"
												+ "'" + tempRawMaterialas.get(index).getQuantity()*temPd.getQuantity() + "',"
												+ "'',"
												+ "'',"
												+ "'" +tempRawMaterialas.get(index).getPrice() + "',"
												+ "'" + tempRawMaterialas.get(index).getPrice() + "',"	
												+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
												+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
												+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
												+ "'" + tempRawMaterialas.get(index).getTotalPrice()*temPd.getQuantity() + "',"
												+ "'0',"
												+ "'0',"
												+ "'" + tempRawMaterialas.get(index).getTotalPrice()*temPd.getQuantity() + "',"
												+ "'" + type + "',"
												+ "'0',"
												+ "'',"
												+ "GETDATE(),"
												+ "CURRENT_TIMESTAMP, "
												+ "'" + SessionBeam.getUserId() + "' )";
										
										databaseHandler.execAction(sql);
									}
								}
								
							}else {
								sql = "insert into tbStore ("
										+ "invoiceAutoNo,"
										+ "InvoiceNo,"
										+ " itemId,"
										+ " itemName,"
										+ "isHaveImei,"
										+ " packageId,"
										+ " itemType,"
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
										+ "values('" +invoiceAutoId  + "',"
										+ "'" + getInvoiceNo() + "',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + temPd.getItemName() + "',"
										+ "'0',"
										+ "'',"
										+ "'"+temPd.getItemType()+"',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + Customerid + "',"
										+ "'" + temPd.getUnit() + "',"
										+ "'" + temPd.getUnitQuantity() + "',"
										+ "'" + temPd.getQuantity() + "',"
										+ "'',"
										+ "'',"
										+ "'" + temPd.getPrice() + "',"
										+ "'" + temPd.getUnitPrice() + "',"	
										+ "'" +	temPd.getPrice() + "',"
										+ "'" +	temPd.getPrice() + "',"
										+ "'" +	temPd.getPrice() + "',"
										+ "'" + temPd.getTotalAmount() + "',"
										+ "'" + temPd.getDiscount() + "',"
										+ "'" + temPd.getDiscount()/temPd.getQuantity() + "',"
										+ "'" + temPd.getNetAmount() + "',"
										+ "'" + returnType + "',"
										+ "'0',"
										+ "'',"
										+ "GETDATE(),"
										+ "CURRENT_TIMESTAMP, "
										+ "'" + SessionBeam.getUserId() + "' )";
								
								databaseHandler.execAction(sql);
								
								ArrayList<RawMaterialsInfo> tempRawMaterialas= LoadedInfo.getRawMalerialsInfoistByItemId(temPd.getItemId());
								if(tempRawMaterialas != null){
									for(int index = 0;index<tempRawMaterialas.size();index++){
										sql = "insert into tbStore ("
												+ "invoiceAutoNo,"
												+ "InvoiceNo,"
												+ " itemId,"
												+ " itemName,"
												+ "isHaveImei,"
												+ " packageId,"
												+ " itemType,"
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
												+ "values('" +invoiceAutoId  + "',"
												+ "'" + getInvoiceNo() + "',"
												+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
												+ "'" + tempRawMaterialas.get(index).getItemName() + "',"
												+ "'0',"
												+ "'',"
												+ "'"+rawMaterialsType+"',"
												+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
												+ "'" + Customerid + "',"
												+ "'" + tempRawMaterialas.get(index).getUnit() + "',"
												+ "'" + tempRawMaterialas.get(index).getUnitQuantity() + "',"
												+ "'" + tempRawMaterialas.get(index).getQuantity()*temPd.getQuantity() + "',"
												+ "'',"
												+ "'',"
												+ "'" +tempRawMaterialas.get(index).getPrice() + "',"
												+ "'" + tempRawMaterialas.get(index).getPrice() + "',"	
												+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
												+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
												+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
												+ "'" + tempRawMaterialas.get(index).getTotalPrice()*temPd.getQuantity() + "',"
												+ "'0',"
												+ "'0',"
												+ "'" + tempRawMaterialas.get(index).getTotalPrice()*temPd.getQuantity() + "',"
												+ "'" + returnType + "',"
												+ "'0',"
												+ "'',"
												+ "GETDATE(),"
												+ "CURRENT_TIMESTAMP, "
												+ "'" + SessionBeam.getUserId() + "' )";
										
										databaseHandler.execAction(sql);
									}
								}
							}
						}else if(temPd.getItemType() == packageType){
							if(temPd.isSales()) {
								sql = "insert into tbStore ("
										+ "invoiceAutoNo,"
										+ "InvoiceNo,"
										+ " itemId,"
										+ " itemName,"
										+ "isHaveImei,"
										+ " packageId,"
										+ " itemType,"
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
										+ "values('" +invoiceAutoId  + "',"
										+ "'" + getInvoiceNo() + "',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + temPd.getItemName() + "',"
										+ "'0',"
										+ "'',"
										+ "'"+temPd.getItemType()+"',"
										+ "'" + Customerid + "',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + temPd.getUnit() + "',"
										+ "'" + temPd.getUnitQuantity() + "',"
										+ "'" + temPd.getQuantity() + "',"
										+ "'',"
										+ "'',"
										+ "'" + temPd.getPrice() + "',"
										+ "'" + temPd.getUnitPrice() + "',"	
										+ "'" +	temPd.getPrice() + "',"
										+ "'" +	temPd.getPrice() + "',"
										+ "'" +	temPd.getPrice() + "',"
										+ "'" + temPd.getTotalAmount() + "',"
										+ "'" + temPd.getDiscount() + "',"
										+ "'" + temPd.getDiscount()/temPd.getQuantity() + "',"
										+ "'" + temPd.getNetAmount() + "',"
										+ "'" + type + "',"
										+ "'0',"
										+ "'',"
										+ "GETDATE(),"
										+ "CURRENT_TIMESTAMP, "
										+ "'" + SessionBeam.getUserId() + "' )";
								
								databaseHandler.execAction(sql);
								
								ArrayList<PackageItemInfo> tempPackitemList= LoadedInfo.getPackgeItemInfoList(temPd.getItemId());
								
								for(int packIndex=0;packIndex<tempPackitemList.size();packIndex++){
									sql = "insert into tbStore ("
											+ "invoiceAutoNo,"
											+ "InvoiceNo,"
											+ " itemId,"
											+ " itemName,"
											+ "isHaveImei,"
											+ " packageId,"
											+ " itemType,"
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
											+ "values('" +invoiceAutoId  + "',"
											+ "'" + getInvoiceNo() + "',"
											+ "'" + tempPackitemList.get(packIndex).getItemId() + "',"
											+ "'" + tempPackitemList.get(packIndex).getItemName() + "',"
											+ "'0',"
											+ "'"+temPd.getItemId()+"',"
											+ "'"+packageItemType+"',"
											+ "'" + Customerid + "',"
											+ "'" + tempPackitemList.get(packIndex).getItemId() + "',"
											+ "'Pices (1)',"
											+ "'" + tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
											+ "'" + tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
											+ "'',"
											+ "'',"
											+ "'" + tempPackitemList.get(packIndex).getPackagePrice() + "',"
											+ "'" + tempPackitemList.get(packIndex).getPackagePrice() + "',"	
											+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
											+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
											+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
											+ "'" + tempPackitemList.get(packIndex).getTotalPrice()*temPd.getQuantity() + "',"
											+ "'" + tempPackitemList.get(packIndex).getDiscount() + "',"
											+ "'" + tempPackitemList.get(packIndex).getDiscount() + "',"
											+ "'" + tempPackitemList.get(packIndex).getTotalPrice()*temPd.getQuantity() + "',"
											+ "'" + type + "',"
											+ "'0',"
											+ "'',"
											+ "GETDATE(),"
											+ "CURRENT_TIMESTAMP, "
											+ "'" + SessionBeam.getUserId() + "' )";
									
									databaseHandler.execAction(sql);
									
									ArrayList<RawMaterialsInfo> tempRawMaterialas= LoadedInfo.getRawMalerialsInfoistByItemId(tempPackitemList.get(packIndex).getItemId());	
									if(tempRawMaterialas != null){
										for(int index = 0;index<tempRawMaterialas.size();index++){
											sql = "insert into tbStore ("
													+ "invoiceAutoNo,"
													+ "InvoiceNo,"
													+ " itemId,"
													+ " itemName,"
													+ "isHaveImei,"
													+ " packageId,"
													+ " itemType,"
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
													+ "values('" +invoiceAutoId  + "',"
													+ "'" + getInvoiceNo() + "',"
													+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
													+ "'" + tempRawMaterialas.get(index).getItemName() + "',"
													+ "'0',"
													+ "'',"
													+ "'"+rawMaterialsType+"',"
													+ "'" + Customerid + "',"
													+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
													+ "'" + tempRawMaterialas.get(index).getUnit() + "',"
													+ "'" + tempRawMaterialas.get(index).getUnitQuantity()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
													+ "'" + tempRawMaterialas.get(index).getQuantity()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
													+ "'',"
													+ "'',"
													+ "'" +tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" + tempRawMaterialas.get(index).getPrice() + "',"	
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" + tempRawMaterialas.get(index).getTotalPrice()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
													+ "'0',"
													+ "'0',"
													+ "'" + tempRawMaterialas.get(index).getTotalPrice()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
													+ "'" + type + "',"
													+ "'0',"
													+ "'',"
													+ "GETDATE(),"
													+ "CURRENT_TIMESTAMP, "
													+ "'" + SessionBeam.getUserId() + "' )";
											
											databaseHandler.execAction(sql);
										}
									}
								}
								
								
								
							}else {
								sql = "insert into tbStore ("
										+ "invoiceAutoNo,"
										+ "InvoiceNo,"
										+ " itemId,"
										+ " itemName,"
										+ "isHaveImei,"
										+ " packageId,"
										+ " itemType,"
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
										+ "values('" +invoiceAutoId  + "',"
										+ "'" + getInvoiceNo() + "',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + temPd.getItemName() + "',"
										+ "'0',"
										+ "'',"
										+ "'"+temPd.getItemType()+"',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + Customerid + "',"
										+ "'" + temPd.getUnit() + "',"
										+ "'" + temPd.getUnitQuantity() + "',"
										+ "'" + temPd.getQuantity() + "',"
										+ "'',"
										+ "'',"
										+ "'" + temPd.getPrice() + "',"
										+ "'" + temPd.getUnitPrice() + "',"	
										+ "'" +	temPd.getPrice() + "',"
										+ "'" +	temPd.getPrice() + "',"
										+ "'" +	temPd.getPrice() + "',"
										+ "'" + temPd.getTotalAmount() + "',"
										+ "'" + temPd.getDiscount() + "',"
										+ "'" + temPd.getDiscount()/temPd.getQuantity() + "',"
										+ "'" + temPd.getNetAmount() + "',"
										+ "'" + returnType + "',"
										+ "'0',"
										+ "'',"
										+ "GETDATE(),"
										+ "CURRENT_TIMESTAMP, "
										+ "'" + SessionBeam.getUserId() + "' )";
								
								databaseHandler.execAction(sql);
								
								ArrayList<PackageItemInfo> tempPackitemList= LoadedInfo.getPackgeItemInfoList(temPd.getItemId());
								
								for(int packIndex=0;packIndex<tempPackitemList.size();packIndex++){
									sql = "insert into tbStore ("
											+ "invoiceAutoNo,"
											+ "InvoiceNo,"
											+ " itemId,"
											+ " itemName,"
											+ "isHaveImei,"
											+ " packageId,"
											+ " itemType,"
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
											+ "values('" +invoiceAutoId  + "',"
											+ "'" + getInvoiceNo() + "',"
											+ "'" + tempPackitemList.get(packIndex).getItemId() + "',"
											+ "'" + tempPackitemList.get(packIndex).getItemName() + "',"
											+ "'0',"
											+ "'"+temPd.getItemId()+"',"
											+ "'"+packageItemType+"',"
											+ "'" + tempPackitemList.get(packIndex).getItemId() + "',"
											+ "'" + Customerid + "',"
											+ "'Pices (1)',"
											+ "'" + tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
											+ "'" + tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
											+ "'',"
											+ "'',"
											+ "'" + tempPackitemList.get(packIndex).getPackagePrice() + "',"
											+ "'" + tempPackitemList.get(packIndex).getPackagePrice() + "',"	
											+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
											+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
											+ "'" +	tempPackitemList.get(packIndex).getPackagePrice() + "',"
											+ "'" + tempPackitemList.get(packIndex).getTotalPrice()*temPd.getQuantity() + "',"
											+ "'" + tempPackitemList.get(packIndex).getDiscount() + "',"
											+ "'" + tempPackitemList.get(packIndex).getDiscount() + "',"
											+ "'" + tempPackitemList.get(packIndex).getTotalPrice()*temPd.getQuantity() + "',"
											+ "'" + type + "',"
											+ "'0',"
											+ "'',"
											+ "GETDATE(),"
											+ "CURRENT_TIMESTAMP, "
											+ "'" + SessionBeam.getUserId() + "' )";
									
									databaseHandler.execAction(sql);
									
									ArrayList<RawMaterialsInfo> tempRawMaterialas= LoadedInfo.getRawMalerialsInfoistByItemId(tempPackitemList.get(packIndex).getItemId());
									if(tempRawMaterialas != null){
										for(int index = 0;index<tempRawMaterialas.size();index++){
											sql = "insert into tbStore ("
													+ "invoiceAutoNo,"
													+ "InvoiceNo,"
													+ " itemId,"
													+ " itemName,"
													+ "isHaveImei,"
													+ " packageId,"
													+ " itemType,"
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
													+ "values('" +invoiceAutoId  + "',"
													+ "'" + getInvoiceNo() + "',"
													+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
													+ "'" + tempRawMaterialas.get(index).getItemName() + "',"
													+ "'0',"
													+ "'',"
													+ "'"+rawMaterialsType+"',"
													+ "'" + tempRawMaterialas.get(index).getItemId() + "',"
													+ "'" + Customerid + "',"
													+ "'" + tempRawMaterialas.get(index).getUnit() + "',"
													+ "'" + tempRawMaterialas.get(index).getUnitQuantity()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
													+ "'" + tempRawMaterialas.get(index).getQuantity()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
													+ "'',"
													+ "'',"
													+ "'" +tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" + tempRawMaterialas.get(index).getPrice() + "',"	
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" +	tempRawMaterialas.get(index).getPrice() + "',"
													+ "'" + tempRawMaterialas.get(index).getTotalPrice()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
													+ "'0',"
													+ "'0',"
													+ "'" + tempRawMaterialas.get(index).getTotalPrice()*tempPackitemList.get(packIndex).getQuantity()*temPd.getQuantity() + "',"
													+ "'" + returnType + "',"
													+ "'0',"
													+ "'',"
													+ "GETDATE(),"
													+ "CURRENT_TIMESTAMP, "
													+ "'" + SessionBeam.getUserId() + "' )";
											
											databaseHandler.execAction(sql);
										}
									}
								}
								
								
							}
						}

					}




					new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfull....!","Invoice Save Successfully...");
					btnSplitPrint.requestFocus();

					invoiceFindAction(getInvoiceNo());

					btnPrintAction(null);
					tabPane.getTabs().remove(tab);
					btnNewBillAction(null);
					//btnRefreshAction(null);
					//cmbFindLoad();

					//}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	@FXML
	private void btnNewBillAction(ActionEvent event) {
		tabPane.getTabs().add(new BillingTab());
		tabPane.getSelectionModel().selectLast();		
	}

	@FXML
	private void btnTableAddAction() {
		tableNameAddInInforList(getcmbTableName());
	}

	@FXML
	private void treeClickAction(MouseEvent mouseEvent) {
		try {
			TreeItem<String >  tempTreeItem= treeCategory.getSelectionModel().getSelectedItem();
			if(tempTreeItem != null) {
				setCmbCategory(tempTreeItem.getValue());
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void ItemDetailsTableClickAction(MouseEvent mouseEvent) {
		try {

			if(tableItemDetails.getSelectionModel().getSelectedItem() != null) {
				TablePosition firstCell = tableItemDetails.getSelectionModel().getSelectedCells().get(0);
				ItemDetails  tempProductDetail= tableItemDetails.getSelectionModel().getSelectedItem();

				if(tempProductDetail != null) {
					setCmbItemName(tempProductDetail.getItemName());
					setTxtQuantity(tempProductDetail.getQuantity());	
					setTxtPrice(tempProductDetail.getUnitPrice());
					priceSetByProductName();
				}

			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void ItemListTableClickAction(MouseEvent mouseEvent) {
		try {

			if(isInvoiceDateExist()|| isInvoiceNew()) {
				if(tableItemList.getSelectionModel().getSelectedItem() != null) {

					ItemList  tempItem= tableItemList.getSelectionModel().getSelectedItem();
					int rowCount;

					if(isInvoiceNew()) {
						for(rowCount=0;rowCount<listItemDetails.size();rowCount++) {
							if(tempItem.getItemId().equals(listItemDetails.get(rowCount).getItemId()) && tempItem.getType() == listItemDetails.get(rowCount).getItemType()) {
								listItemDetails.get(rowCount).incrementQuantity();
								listItemDetails.get(rowCount).calculateTotalAmount();
								listItemDetails.get(rowCount).calculateNetAmount();
								break;
							}
						}
						if(rowCount == listItemDetails.size()) {
							listItemDetails.add(new ItemDetails(tempItem.getItemId(), tempItem.getItemName(), tempItem.getUnit(), 1,1, tempItem.getPrice(),tempItem.getPrice(), 0, tempItem.getPrice(), 0, tempItem.getPrice(),tempItem.getType(),true,true,this));	
						}
					}else {				
						for(rowCount=0;rowCount<listItemDetails.size();rowCount++) {
							if(listItemDetails.get(rowCount).isNew() && listItemDetails.get(rowCount).isSales &&tempItem.getItemId().equals(listItemDetails.get(rowCount).getItemId()) && tempItem.getType() == listItemDetails.get(rowCount).getItemType()) {
								listItemDetails.get(rowCount).incrementQuantity();
								listItemDetails.get(rowCount).calculateTotalAmount();
								listItemDetails.get(rowCount).calculateNetAmount();
								break;
							}
						}
						if(rowCount == listItemDetails.size()) {
							listItemDetails.add(new ItemDetails(tempItem.getItemId(), tempItem.getItemName(), tempItem.getUnit(), 1,1, tempItem.getPrice(),tempItem.getPrice(), 0, tempItem.getPrice(), 0, tempItem.getPrice(),tempItem.getType(),true,true,this));	
						}
					}

					/*if(tempItem.getType() == packageType){

						rowCount++;
						ArrayList<PackageItemInfo> tempItemList = LoadedInfo.getPackgeItemInfoList(tempItem.getItemId());
						for(int i=0;i<tempItemList.size();i++){
							if(rowCount==listItemDetails.size()){
								listItemDetails.add(new ItemDetails(tempItemList.get(i).getItemId(), tempItemList.get(i).getItemName(), "Pices(1)", tempItemList.get(i).getQuantityInt(),tempItemList.get(i).getQuantityInt(), tempItemList.get(i).getPrice(),tempItemList.get(i).getPackagePrice(), 0, tempItemList.get(i).getPrice() * tempItemList.get(i).getQuantity(), tempItemList.get(i).getDiscount(), tempItemList.get(i).getTotalPrice(),packageItemType,true,true,this));
							}else if(tempItemList.get(i).getItemId().equals(listItemDetails.get(rowCount).getItemId()) && listItemDetails.get(rowCount).getItemType() == packageItemType){
								listItemDetails.get(rowCount).addQuantity(tempItemList.get(i).getQuantityInt());
								listItemDetails.get(rowCount).calculateTotalAmount();
								listItemDetails.get(rowCount).calculateNetAmount();

							}else{
								listItemDetails.add(rowCount,new ItemDetails(tempItemList.get(i).getItemId(), tempItemList.get(i).getItemName(), "Pices(1)", tempItemList.get(i).getQuantityInt(),tempItemList.get(i).getQuantityInt(), tempItemList.get(i).getPrice(),tempItemList.get(i).getPackagePrice(), 0, tempItemList.get(i).getPrice() * tempItemList.get(i).getQuantity(), tempItemList.get(i).getDiscount(), tempItemList.get(i).getTotalPrice(),packageItemType,true,true,this));
							}
							rowCount++;
						}
					}*/


					tableItemDetails.setItems(listItemDetails);
					tableItemDetails.refresh();
					netAmountCount();
				}

			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Cannot change Bill from the previous days....!","Any Previous Day Bill Can Not Change...\nYou Should Create New Bill For Sale...");
				btnFind.requestFocus();
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void invoiceFindAction(String invoiceNo) {
		try {

			tab.setText("Bill No-"+invoiceNo);
			listItemDetails.clear();

			addCheckReturn();
			setInvoiceNew(false);
			setInvoiceConfirmed(true);
			setInvoicePay(true);
			setCurrentPaidAmount(0);


			sql = "select *,substring(remarks,0,charindex('#',remarks)-1) as tableList,substring(remarks,charindex('=',remarks)+1,DATALENGTH(remarks)) as remarks2,(vatAmount+totalAmount)as grossAmount from tbInvoice where Invoice='"+invoiceNo+"' and type='"+type+"' ";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setInvoiceNo(rs.getString("invoice"));
				setCmbCustomer(rs.getString("SellerCustomerName"));
				setTxtContactNo(rs.getString("Mobileno"));
				setDate(rs.getDate("date"));
				setTxtAddress(rs.getString("address"));
				setTxtTotalAmount(rs.getDouble("totalAmount"));
				setTxtVat(rs.getString("vat"));
				setTxtVatAmount(rs.getString("vatAmount"));
				setTxtServiceChargeAmount(rs.getString("serviceChargeAmount"));
				setTxtGrossAmount(rs.getString("grossAmount"));
				setTxtDiscountPercent(rs.getString("discount"));
				setTxtPercentDiscount(rs.getString("percentDiscount"));	
				setTxtManualDiscount(rs.getString("manualDiscount"));
				setTxtTotalDiscount(rs.getString("totalDiscount"));
				setTxtNetAmount(rs.getString("netAmount"));
				setTxtPaidAmount(rs.getString("Paid"));
				setTxtDueAmount(rs.getString("Due"));
				setCmbRemarks(rs.getString("remarks2"));

				setInvoiceDate(rs.getDate("date"));
				setPreviousPaidAmount(rs.getDouble("Paid"));
				if(rs.getDouble("Paid") <=0){
					setInvoicePay(false);
				}
				setActiveStatus(rs.getInt("activeStatus"));

				setTxtTakenAmount(0);
				setTxtChangeAmount(0);

				String tableList[] = rs.getString("tableList").split(",");
				for(int i =0 ;i<tableList.length;i++) {
					listTableInfo.getItems().add(tableList[i]);
				}



			}

			sql = "select itemId,itemName,unit,sum(UnitQty)as UnitQty,sum(qty)as qty,UnitPrice,SalesPrice,PurchasePrice,sum(totalAmount)as totalAmount,sum(discount)as discount,sum(netAmount)as netAmount,type from tbStore \r\n" + 
					"where invoiceNo = '"+getInvoiceNo()+"'  and (itemType = '"+packageType+"' or itemType = '"+itemType+"')\r\n" + 
					"group by itemId,itemName,unit,UnitPrice,SalesPrice,PurchasePrice,discount,type order by type";
			rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				listItemDetails.add(new ItemDetails(rs.getString("itemId"),rs.getString("itemName"), "", rs.getInt("UnitQty"), rs.getInt("qty"), rs.getDouble("unitPrice"), rs.getDouble("SalesPrice"),rs.getDouble("purchasePrice"), rs.getDouble("totalamount"), rs.getDouble("discount"), rs.getDouble("netAmount"),itemType,false,(rs.getInt("type")==3),this));

			}

			tableItemDetails.setItems(listItemDetails);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void cancelAction() {
		// TODO Auto-generated method stub
		try{
			String autoNo = getAutoInvoiceNo(getInvoiceNo());
			sql = "update tbInvoice set activeStatus = '"+canceled+"' where AutoId = '"+autoNo+"' and type='"+type+"' ";
			databaseHandler.execAction(sql);
			sql = "update tbStore set activeStatus = '"+canceled+"' where invoiceAutoNo = '"+autoNo+"' and invoiceNo = '"+getInvoiceNo()+"' and type in('"+type+"','"+returnType+"');";
			databaseHandler.execAction(sql);
			sql = "update tbPaymentHistory set activeStatus = '"+canceled+"' where invoiceNo = '"+autoNo+"' and type in ('"+type+"','"+returnType+"');";
			databaseHandler.execAction(sql);
			sql = "update tbAccftransection set activeStatus = '"+canceled+"' where voucherNo = '"+autoNo+"' and type in ('"+type+"','"+returnType+"');";
			databaseHandler.execAction(sql);

			new Notification(Pos.TOP_CENTER, "Information graphic", "Cancel Successull....!","This Invoice Order Cancel Successfully...");

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void refreshAction() {
		CustomerLoad();
		loadCategoryName();
		//maxInvoiceNoSet();
		//paymentMethodLoad();
		productNameLoad();
		remarksLoad();
		setActiveStatus(active);
		itemAndPackageLoadByCategoryName("0");
		//approvedByLoad();


		listItemDetails.clear();
		tableItemDetails.setItems(listItemDetails);


		//setDate(new Date());
		setCmbCustomer("");
		setCmbItemName("");
		//setCmbUnit("");
		setTxtQuantity("0");
		//setTxtStock("0");
		setTxtPrice("0");
		//setTxtDiscount("0");
		//setDateWarrenty(null);

		setTxtTotalAmount("0");
		setTxtVat("0");
		setTxtGrossAmount("0");
		setTxtDiscountPercent("0");
		setTxtPercentDiscount("0");
		setTxtManualDiscount("0");
		setTxtTotalDiscount("0");
		setTxtNetAmount("0");
		setTxtTakenAmount("0");
		setTxtChangeAmount("0");
		setTxtPaidAmount("0");

		setInvoiceDate(new Date());
		setInvoiceConfirmed(false);
		setInvoiceNew(true);


	}

	private void clearTxtAfterItemAdd() {
		// TODO Auto-generated method stub
		setCmbItemName("");

		setTxtQuantity("0");
		setTxtPrice("0");	
		cmbItemName.requestFocus();
	}

	private String getCustomerLedgerId(String CustomerName) {
		// TODO Auto-generated method stub
		try {
			String sql="select ledgerId from tbAccfledger where ledgertitle='"+CustomerName+" (C)' and type ='"+CustomerLedgerType+"'";
			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("ledgerId");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return genarelCustomerLedger;
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

	private String getCustomerId() {
		// TODO Auto-generated method stub
		try {
			sql = "select id from tbCustomer where CustomerName = '"+getCmbCustomer()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			} 
		}catch(Exception e) {
			e.printStackTrace();
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

	/*private String getCategoryId(String categoryName) {
		// TODO Auto-generated method stub
		try {
			String sql = "select id from tbCategory where categoryName = '"+categoryName+"';";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				return rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}*/

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

	private double getItemPurchasePrice(String itemId) {
		// TODO Auto-generated method stub
		try {
			sql = "select purchasePrice from tbStore where itemid= '"+itemId+"' order by invoiceNo desc";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getDouble("purchasePrice");
			}else {
				sql = "select purchasePrice from tbitem  where id= '"+itemId+"'";
				rs = databaseHandler.execQuery(sql);
				if(rs.next()) {
					return rs.getDouble("purchasePrice");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return 0.0;
	}

	private boolean addValidationCheck() {
		// TODO Auto-generated method stub
		if(!getCmbItemName().isEmpty()) {

			if(!getTxtQuantity().isEmpty()) {

				if(!getTxtPrice().isEmpty()) {

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
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Discount..");
					txtPrice.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter a Valid item name..");
				txtQuantity.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter a Valid item name..");
			cmbItemName.requestFocus();
		}
		return false;
	}


	private boolean saveValidaionCheck() {

		if(listItemDetails.size()>0) {
			return true;
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Any Product To This Invoice..");
			cmbItemName.requestFocus();
		}
		return false;
	}

	private boolean editValidaionCheck() {
		// TODO Auto-generated method stub
		if(billNoValidCheck()) {
			if(listItemDetails.size()>=0) {
				//if(isCustomerExist()) {

				/*}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select a Valid Customer..");
					cmbCustomer.requestFocus();
				}*/
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Any Product To This Invoice..");
				cmbItemName.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Customer Not Valid!","Customer Name Of Bill Of this invoice is invalid... \nPlease Select Valid Customer..");
			cmbCustomer.requestFocus();
		}

		return false;
	}



	private boolean billNoValidCheck() {
		// TODO Auto-generated method stub
		try {
			sql = "select BillNo from tbInvoice where Invoice = '"+getInvoiceNo()+"' and type='"+type+"'";
			ResultSet rs2 = databaseHandler.execQuery(sql);
			if(rs2.next()) {
				if(rs2.getString("BillNo") != null) {
					sql = "select billNo from tbBillInfo where BillNo = '"+rs2.getString("BillNo")+"' and customerId='"+getCustomerId()+"'";
					rs2 = databaseHandler.execQuery(sql);
					if(rs2.next()) {
						return true;
					}else {
						return false;
					}
				}
			}else {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}

	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message);
	}

	/*private double getPreviousPaidAmount() {
		// TODO Auto-generated method stub
		try {
			sql = "select ISNULL(paid,0) as paid from tbInvoice where Invoice = '"+getInvoiceNo()+"' and type = '"+type+"';";
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
	 */
	private boolean isCustomerExist() {
		try {
			sql = "select * from tbCustomer where CustomerName = '"+getCmbCustomer()+"'";
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

	@FXML
	private void netAmountCount() {
		// TODO Auto-generated method stub

		if(isInvoiceDateExist() || isInvoiceNew()) {
			double totalAmount,grossAmnt,vat,vatAmnt,serviceChaargeAmount,discount,discountPercent,percentDiscountAmnt,manualDiscountAmnt,totalDisctount,netAmount,takenAmount,changeAmount,currentPaidAmount,previousPaidAmount,due;


			totalAmount = 0;
			vat = Double.valueOf(getTxtVat());
			vatAmnt = 0;
			serviceChaargeAmount = Double.valueOf(getTxtServiceChargeAmount());
			discount = 0;
			discountPercent = Double.valueOf(getTxtDiscountPercent());
			//manualDiscountAmnt = Double.valueOf(getTxtManualDiscount());
			percentDiscountAmnt = 0;
			manualDiscountAmnt = Double.valueOf(getTxtManualDiscount());
			totalDisctount = 0;
			netAmount = 0;
			for(int i=0;i<listItemDetails.size();i++) {
				ItemDetails tempPD = listItemDetails.get(i);

				if(tempPD.isSales) {
					totalAmount += tempPD.getTotalAmount();
					discount += tempPD.getDiscount();
				}else {
					totalAmount -= tempPD.getTotalAmount();
					discount -= tempPD.getDiscount();
				}


			}
			setTxtTotalAmount(totalAmount);
			setTxtTotal(totalAmount);

			if(vat>0) vatAmnt =(totalAmount*vat)/100;

			setTxtVatAmount(vatAmnt);

			grossAmnt = totalAmount+vatAmnt+serviceChaargeAmount;
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

			previousPaidAmount = getPreviousPaidAmount();

			setTxtTakenAmount(netAmount - previousPaidAmount);
			takenAmount = Double.valueOf(getTxtTakenAmount());

			if(previousPaidAmount>= netAmount) {		
				currentPaidAmount = 0;
				changeAmount = takenAmount - currentPaidAmount;		
			}else {
				if(takenAmount+previousPaidAmount>=netAmount) {
					currentPaidAmount = netAmount-previousPaidAmount;
					changeAmount = takenAmount - currentPaidAmount;
				}else {
					currentPaidAmount = takenAmount;
					changeAmount = 0;
				}

			}

			due = netAmount - currentPaidAmount - previousPaidAmount;



			setCurrentPaidAmount(currentPaidAmount);
			setTxtChangeAmount(changeAmount);
			setTxtPaidAmount(currentPaidAmount+previousPaidAmount);
			setTxtDueAmount(due);

			setInvoiceEdit(true);
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Cannot change Bill from the previous days....!","Any Previous Day Bill Can Not Change...\nYou Can Recived Due Payment From This Bill...");
			btnPay.requestFocus();
		}


	}
	@FXML
	private void netAmountCountForTxtTakenAmount() {
		// TODO Auto-generated method stub

		if(isInvoiceDateExist() || isInvoiceNew()) {
			double totalAmount,grossAmnt,vat,vatAmnt,serviceChaargeAmount,discount,discountPercent,percentDiscountAmnt,manualDiscountAmnt,totalDisctount,netAmount,takenAmount,changeAmount,currentPaidAmount,previousPaidAmount,due;


			totalAmount = 0;
			vat = Double.valueOf(getTxtVat());
			vatAmnt = 0;
			serviceChaargeAmount = Double.valueOf(getTxtServiceChargeAmount());
			discount = 0;
			discountPercent = Double.valueOf(getTxtDiscountPercent());
			//manualDiscountAmnt = Double.valueOf(getTxtManualDiscount());
			percentDiscountAmnt = 0;
			manualDiscountAmnt = Double.valueOf(getTxtManualDiscount());
			totalDisctount = 0;
			netAmount = 0;
			for(int i=0;i<listItemDetails.size();i++) {
				ItemDetails tempPD = listItemDetails.get(i);

				if(tempPD.isSales) {
					totalAmount += tempPD.getTotalAmount();
					discount += tempPD.getDiscount();
				}else {
					totalAmount -= tempPD.getTotalAmount();
					discount -= tempPD.getDiscount();
				}


			}
			setTxtTotalAmount(totalAmount);
			setTxtTotal(totalAmount);

			if(vat>0) vatAmnt =(totalAmount*vat)/100;

			setTxtVatAmount(vatAmnt);

			grossAmnt = totalAmount+vatAmnt+serviceChaargeAmount;
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

			previousPaidAmount = getPreviousPaidAmount();

			//setTxtTakenAmount(netAmount - previousPaidAmount);
			takenAmount = Double.valueOf(getTxtTakenAmount());

			if(previousPaidAmount>= netAmount) {		
				currentPaidAmount = 0;
				changeAmount = takenAmount - currentPaidAmount;		
			}else {
				if(takenAmount+previousPaidAmount>=netAmount) {
					currentPaidAmount = netAmount-previousPaidAmount;
					changeAmount = takenAmount - currentPaidAmount;
				}else {
					currentPaidAmount = takenAmount;
					changeAmount = 0;
				}

			}

			due = netAmount - currentPaidAmount - previousPaidAmount;



			setCurrentPaidAmount(currentPaidAmount);
			setTxtChangeAmount(changeAmount);
			setTxtPaidAmount(currentPaidAmount+previousPaidAmount);
			setTxtDueAmount(due);

			setInvoiceEdit(true);
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Cannot change Bill from the previous days....!","Any Previous Day Bill Can Not Change...\nYou Can Recived Due Payment From This Bill...");
			btnPay.requestFocus();
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

	private void maxInvoiceNoSet() {
		// TODO Auto-generated method stub
		try {
			String year = String.valueOf(calender.get(Calendar.YEAR)).substring(2);
			String month = dfId.format(calender.get(Calendar.MONTH)+1);

			sql = "select  IsNULL(MAX(cast(SUBSTRING(Invoice,6,LEN(Invoice)-5) as int)),0)+1 as maxInvoiceId from tbInvoice WHERE type = '"+type+"' and Invoice LIKE '"+type+year+month+"%';";
			ResultSet rs = databaseHandler.execQuery(sql);
			if (rs.next()) {
				setInvoiceNo(type+year+month+dfId.format(rs.getInt("maxInvoiceId")));		
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void priceSetByProductName() {
		try {
			sql = "select dbo.presentStock(i.id) as stock,id,salePrice from tbItem i where projectedItemName = '"+getCmbItemName()+"';";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtPrice(rs.getDouble("salePrice"));
				if(getTxtQuantity().isEmpty()||getTxtQuantity().equals("0")) {
					setTxtQuantity("1");
				}

			}



		} catch (Exception e) {
			e.printStackTrace();
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

	private void cmbFindLoad() {
		try {
			sql = "select invoice,customerName from tbInvoice where type = '"+type+"' order by Invoice desc";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbCategory.data.clear();
			while(rs.next()) {
				cmbCategory.data.add(rs.getString("invoice")+" / "+rs.getString("customerName"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	private void loadCategoryName() {
		// TODO Auto-generated method stub
		try {
			cmbCategory.data.clear();
			sql = "select categoryName from tbCategory where type = '1' order by categoryname";
			cmbCategory.data.add("");
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbCategory.data.add(rs.getString("CategoryName"));
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public void productNameLoad() {
		try {
			sql = "select projectedItemName from tbItem where isActive = '1' and type = '1' order by cast(substring(projectedItemName,0,isnull (charindex('-',projectedItemName),DATALENGTH(projectedItemName))) as int)";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbItemName.data.clear();
			while (rs.next()) {
				cmbItemName.data.add(rs.getString("projectedItemName"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void tableNameLoad() {
		// TODO Auto-generated method stub
		try {
			sql = "select autoId,tableName from tbTableInfo";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbTableName.data.clear();
			while(rs.next()) {
				cmbTableName.data.add(rs.getString("tablename"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}
	private void itemAndPackageLoadByCategoryName(String categoryId) {
		try {
			listItemList.clear();
			if(categoryId.equals("0")) {
				sql = "select i.id,CategoryId,c.categoryName,i.unit,projectedItemName,salePrice,case when isActive = 1 then 'Active' else 'Deactive' end as activeStatus  from tbItem i\r\n" + 
						"join tbCategory c\r\n" + 
						"on c.id = i.CategoryId where i.type = '1' and i.isActive ='"+active+"' order by cast(substring(projectedItemName,0,isnull (charindex('-',projectedItemName),DATALENGTH(projectedItemName))) as int)";
				ResultSet rs = databaseHandler.execQuery(sql);
				while(rs.next()){
					listItemList.add(new ItemList(rs.getString("id"),rs.getString("projectedItemName"),rs.getString("Unit"), Double.valueOf(df.format(rs.getDouble("salePrice"))),itemType));
				}
			}else {
				/*sql = "select i.id,CategoryId,c.categoryName,i.unit,projectedItemName,salePrice,case when isActive = 1 then 'Active' else 'Deactive' end as activeStatus  from tbItem i\r\n" + 
						"join tbCategory c\r\n" + 
						"on c.id = i.CategoryId where i.type = '1' and i.CategoryId= '"+categoryId+"' order by cast(substring(projectedItemName,0,isnull (charindex('-',projectedItemName),DATALENGTH(projectedItemName)))as int)";*/
				ArrayList<PackageInfo> temp = LoadedInfo.getPackageInfoListByCategory(categoryId);
				if(temp != null){
					for(int i =0;i<temp.size();i++){
						listItemList.add(new ItemList(temp.get(i).getPackageId(),temp.get(i).getPackageName(),"Pices", Double.valueOf(df.format(temp.get(i).getPriceDouble())),packageType));
					}
				}

				ArrayList<ItemInfo> tempItem = LoadedInfo.getmFinishedGoodsListByCategory(categoryId);
				if(tempItem != null){
					for(int i =0;i<tempItem.size();i++){
						listItemList.add(new ItemList(tempItem.get(i).getItemId(),tempItem.get(i).getProjectedName(),tempItem.get(i).getUnit(), Double.valueOf(df.format(tempItem.get(i).getPriceDouble())),itemType));
					}
				}
			}




			tableItemList.setItems(listItemList);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
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



	public void CustomerLoad() {
		try {
			sql = "select CustomerName from tbCustomer";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbCustomer.data.clear();
			while (rs.next()) {
				cmbCustomer.data.add(rs.getString("CustomerName"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	private void categoryTreeLoad() {
		// TODO Auto-generated method stub
		treeCategory.setRoot(treeLoad(rootNode,"1"));

	}

	private TreeItem<String> treeLoad(TreeItem<String> treeItem,String pHeadId) {
		// TODO Auto-generated method stub
		try {
			treeItem.getChildren().clear();
			/*ArrayList<String> treeName = new ArrayList<>();
			ArrayList<Integer> treeId = new ArrayList<>();

			sql = "select id,categoryName from tbCategory where parentId  = '"+pHeadId+"' and type = '1'";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				treeName.add(rs.getString("categoryName"));
				treeId.add(rs.getInt("id"));
			}*/
			ArrayList<CategoryInfo> temp = LoadedInfo.getCategoryInfoList(pHeadId);
			for(int i =0 ;i<temp.size();i++) {
				treeItem.getChildren().add(treeLoad(new TreeItem<String>(temp.get(i).getCategoryName()),temp.get(i).getCategoryId()));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		treeItem.setExpanded(true);
		return treeItem;
	}

	private void setCmpAction() {

		btnAdd.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnAddAction(null); });

		btnPay.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnPayAction(null); });
		cmbTableName.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			tableNameAddInInforList(getcmbTableName());	 });

		/*cmbTableName.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					tableNameAddInInforList(getcmbTableName());
				}
			}    
		});*/

		cmbItemName.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV && !getCmbItemName().isEmpty()) { // focus lost
				if(checkIsProductNameValid()) {

					priceSetByProductName();
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select a valid Item Name...");
					cmbItemName.requestFocus();
				}
			}
		});

		txtDiscountPercent.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV ) { // focus lost
				if(getTxtDiscountPercent().isEmpty()) {
					setTxtDiscountPercent("0");
				}
				netAmountCount();
			}
		});
		txtVat.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV ) { // focus lost
				if(getTxtVat().isEmpty()) {
					setTxtVat("0");
				}
				netAmountCount();
			}
		});
		txtServiceChargeAmount.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV ) { // focus lost
				if(getTxtServiceChargeAmount().isEmpty()) {
					setTxtServiceChargeAmount("0");
				}
				netAmountCount();
			}
		});
		txtManualDiscount.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV ) { // focus lost
				if(getTxtManualDiscount().isEmpty()) {
					setTxtManualDiscount("0");
				}
				netAmountCount();
			}
		});

		txtTakenAmount.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV ) { // focus lost
				if(getTxtTakenAmount().isEmpty()) {
					setTxtTakenAmount("0");
				}
			}
		});


		cmbTableName.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					tableNameAddInInforList(newValue);
				}
			}    
		});

		cmbCategory.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					if(newValue.isEmpty()){
						itemAndPackageLoadByCategoryName("0");
					}else {
						itemAndPackageLoadByCategoryName(LoadedInfo.getCategoryInfo(newValue).getCategoryId());
					}
				}
			}    
		});

		menuItemDeleteTableInfo.setOnAction(e->{

			if(listTableInfo.getSelectionModel().getSelectedItem() != null) {
				int i = listTableInfo.getSelectionModel().getSelectedIndex();
				listTableInfo.getItems().remove(i);
				tableNameAddInInforList("");
			}
		});

		menuItemDeleteItemDetails.setOnAction(e ->{
			if(tableItemDetails.getSelectionModel().getSelectedItem() != null) {
				ItemDetails tempItemDetails = tableItemDetails.getSelectionModel().getSelectedItem();
				if(tempItemDetails.isNew()) {
					listItemDetails.remove(tempItemDetails);
					tableItemDetails.setItems(listItemDetails);
					netAmountCount();

				}
			}
		});

		menuItemTableNameSetting.setOnAction(e ->{
			try{

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/trading/TableNameSetting.fxml"));
				Parent root = fxmlLoader.load();
				//imeigetController = fxmlLoader.getController();

				Dialog dialogTableNameSetting = new Dialog<>();
				dialogTableNameSetting.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
				Node closeButton = dialogTableNameSetting.getDialogPane().lookupButton(ButtonType.CANCEL);
				closeButton.managedProperty().bind(closeButton.visibleProperty());
				closeButton.setVisible(false);
				//this.setResizable(true);
				dialogTableNameSetting.setTitle("Table Name Setting....");
				dialogTableNameSetting.initModality(Modality.APPLICATION_MODAL);

				dialogTableNameSetting.setWidth(200);
				dialogTableNameSetting.setHeight(400);
				dialogTableNameSetting.getDialogPane().setContent(root);
				dialogTableNameSetting.show();

			}catch(Exception ex) {
				ex.printStackTrace();
			}



		});
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

	public void tableNameAddInInforList(String tableName) {
		int i;

		String tabName="";

		for(i= 0;i<listTableInfo.getItems().size();i++) {
			String temp[] = listTableInfo.getItems().get(i).split("-");
			tabName += temp[1]+",";
			if(listTableInfo.getItems().get(i).equals(tableName)) {
				break;
			}
		}
		if(i == listTableInfo.getItems().size()) {
			String temp[] = tableName.split("-");
			if(temp.length>1) {
				listTableInfo.getItems().add(tableName);
				tab.setText("Table-"+tabName+temp[1]);
			}else
				tab.setText("Table-"+tabName+temp[0]);
		}

	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbItemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbItemName);
		});
		cmbCustomer.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCustomer);
		});
		cmbCategory.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCategory);
		});

		cmbTableName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbTableName);
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



		txtVat.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtVat);
		});
		txtServiceChargeAmount.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtServiceChargeAmount);
		});

		txtDiscountPercent.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtDiscountPercent);
		});

		txtManualDiscount.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtManualDiscount);
		});
		txtTakenAmount.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtTakenAmount);
		});
		txtChangeAmount.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtChangeAmount);
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
		Control[] control =  {cmbOrderType,cmbTableName,cmbCustomer,cmbItemName,txtQuantity,btnAdd};
		new FocusMoveByEnter(control);

		Control[] control2 =  {txtVat,txtServiceChargeAmount,txtDiscountPercent,txtManualDiscount,txtTakenAmount,txtChangeAmount,btnPay};
		new FocusMoveByEnter(control2);

		/*Control[] control5 =  {cmbPaymentLedger,txtCheckNo,datePass,btnSave};
		new FocusMoveByEnter(control5);*/
		/*
		Control[] control7 =  {cmbOrderType,txtCheckNo,dateDuePass,txtDueAmount,btnPay};
		new FocusMoveByEnter(control7);*/


	}

	private void setCmpData() {

		date.setConverter(converter);
		date.setValue(LocalDate.now());

		map = new HashMap<>();
		groupPaymentMethod = new ToggleGroup();
		radioCash.setToggleGroup(groupPaymentMethod);
		radioCard.setToggleGroup(groupPaymentMethod);
		radioBkash.setToggleGroup(groupPaymentMethod);

		setPreviousPaidAmount(0);
		setCurrentPaidAmount(0);

		setRadioCash(true);


		cmbOrderType.getItems().addAll("In House","Take Away","Party Sale","Out Catering");
		cmbOrderType.getSelectionModel().select(0);
		//txtStock.setTextFormatter(NumberField.getIntegerFormate());
		txtQuantity.setTextFormatter(NumberField.getIntegerFormate());
		txtPrice.setTextFormatter(NumberField.getDoubleFormate());
		txtTotalAmount.setTextFormatter(NumberField.getDoubleFormate());
		txtVat.setTextFormatter(NumberField.getDoubleFormate());
		txtVatAmount.setTextFormatter(NumberField.getDoubleFormate());
		txtServiceChargeAmount.setTextFormatter(NumberField.getDoubleFormate());
		txtGrossAmount.setTextFormatter(NumberField.getDoubleFormate());
		txtPercentDiscount.setTextFormatter(NumberField.getDoubleFormate());
		txtTotalDiscount.setTextFormatter(NumberField.getDoubleFormate());
		txtDiscountPercent.setTextFormatter(NumberField.getDoubleFormate());
		txtManualDiscount.setTextFormatter(NumberField.getDoubleFormate());
		txtNetAmount.setTextFormatter(NumberField.getDoubleFormate());
		txtTakenAmount.setTextFormatter(NumberField.getDoubleFormate());
		txtPaidAmount.setTextFormatter(NumberField.getDoubleFormate());
		txtChangeAmount.setTextFormatter(NumberField.getDoubleFormate());
		txtDueAmount.setTextFormatter(NumberField.getDoubleFormate());




		itemNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		statusCol.setCellValueFactory(new PropertyValueFactory("status"));
		quantityCol.setCellValueFactory(new PropertyValueFactory("spinner"));

		priceCol.setCellValueFactory(new PropertyValueFactory("price"));
		totalAmountCol.setCellValueFactory(new PropertyValueFactory("totalAmount"));
		tableItemDetails.setColumnResizePolicy(tableItemDetails.CONSTRAINED_RESIZE_POLICY);
		itemNameCol.setCellFactory(tc -> {
			TableCell<ItemDetails, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(itemNameCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});


		itemNameListCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		priceListCol.setCellValueFactory(new PropertyValueFactory("price"));
		tableItemList.setColumnResizePolicy(tableItemList.CONSTRAINED_RESIZE_POLICY);
		itemNameListCol.setCellFactory(tc -> {
			TableCell<ItemList, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(itemNameListCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell ;
		});


		menuItemDeleteItemDetails = new MenuItem("Delete");
		menuItemDeleteTableInfo = new MenuItem("Delete");

		menuItemTableNameSetting = new MenuItem("Table Name Setting");

		contexItemDetails = new ContextMenu();
		contexTableDetails = new ContextMenu();
		contexTableNameSetting = new ContextMenu();

		contexTableDetails.getItems().add(menuItemDeleteTableInfo);
		listTableInfo.setContextMenu(contexTableDetails);

		contexItemDetails.getItems().add(menuItemDeleteItemDetails);
		tableItemDetails.setContextMenu(contexItemDetails);

		contexTableNameSetting.getItems().add(menuItemTableNameSetting);	
		cmbTableName.getEditor().setContextMenu(contexTableNameSetting);
		cmbTableName.setPromptText("Table Name");

	}

	private void addCheckReturn() {
		if(hBoxItemName.getChildren().size()<2)
			hBoxItemName.getChildren().add(checkReturn);
	}

	private void addCmp() {
		// TODO Auto-generated method stub

		treeCategory.setShowRoot(false);

		cmbCategory.setPrefWidth(258);
		cmbCategory.setPrefHeight(25);
		cmbCategory.setPromptText("Category Name");

		vBoxCategory.getChildren().clear();
		vBoxCategory.getChildren().add(cmbCategory);

		cmbCategory.setPrefWidth(213);
		cmbCategory.setPrefHeight(28);

		cmbCustomer.setPrefWidth(236);
		cmbCustomer.setPrefHeight(28);

		vBoxCustomer.getChildren().remove(1);
		vBoxCustomer.getChildren().add(1,cmbCustomer);

		cmbItemName.setPrefWidth(350);
		cmbItemName.setPrefHeight(28);

		vBoxItemName.getChildren().remove(1);
		vBoxItemName.getChildren().add(1,cmbItemName);

		cmbRemarks.setPrefWidth(340);
		cmbRemarks.setPrefHeight(25);

		hBoxRemarks.getChildren().remove(1);
		hBoxRemarks.getChildren().add(1,cmbRemarks);

		cmbTableName.setPrefWidth(235);
		cmbTableName.setPrefHeight(25);

		hBoxTableInfo.getChildren().remove(0);
		hBoxTableInfo.getChildren().add(0,cmbTableName);


		Image findImage = new Image(getClass().getResourceAsStream("/image/icon/search-black.png"));
		imageView.setImage(findImage);

		Image addImage = new Image(getClass().getResourceAsStream("/image/icon/add.png"));
		imageViewAdd.setImage(addImage);


		tableItemDetails.setTableMenuButtonVisible(true);
		tableItemDetails.getColumns().get(1).setVisible(false);
		/*tableItemDetails.skinProperty().addListener(event -> {
			// ChangeListener.changed event
			tableItemDetails.tableMenuButtonVisibleProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue == true) {
					registerListeners();
				}
			});
			if (tableItemDetails.isTableMenuButtonVisible()) {
				registerListeners();
			}
		});*/
	}

	private void registerListeners() {

		final Node buttonNode = getMenuButton();
		// replace mouse listener on "+" node
		buttonNode.setOnMousePressed(event -> {
			showContextMenu();
			event.consume();
		});
	}
	private Node getMenuButton() {
		final TableHeaderRow tableHeaderRow = getTableHeaderRow();
		if (tableHeaderRow == null) {
			return null;
		}
		// child identified as cornerRegion in TableHeaderRow.java
		return tableHeaderRow.getChildren().stream().filter(child ->
		child.getStyleClass().contains("show-hide-columns-button")).findAny().get();
	}
	private TableHeaderRow getTableHeaderRow() {
		final TableViewSkin<?> tableSkin = (TableViewSkin<?>) tableItemDetails.getSkin();
		if (tableSkin == null) {
			return null;
		}
		// find the TableHeaderRow child
		return (TableHeaderRow) tableSkin.getChildren().stream().filter(child -> 
		child instanceof TableHeaderRow).findAny().get();
	}
	protected void showContextMenu() {
		final Node buttonNode = getMenuButton();
		// When the menu is already shown clicking the + button hides it.
		if (tableContextMenu != null) {
			tableContextMenu.hide();
		} else {
			// Show the menu
			// rebuilds the menu each time it is opened
			tableContextMenu = createContextMenu();
			tableContextMenu.setOnHidden(event -> tableContextMenu = null);
			tableContextMenu.show(buttonNode, Side.BOTTOM, 0, 0);
			// Repositioning the menu to be aligned by its right side (keeping
			// inside the table view)
			tableContextMenu.setX(
					buttonNode.localToScreen(buttonNode.getBoundsInLocal())
					.getMaxX() - tableContextMenu.getWidth());
		}
	}

	private ContextMenu createContextMenu() {
		final ContextMenu contextMenu = new ContextMenu();
		contextMenu.getItems().add(createSelectAllMenuItem(contextMenu));
		contextMenu.getItems().add(createDeselectAllMenuItem(contextMenu));
		contextMenu.getItems().add(new SeparatorMenuItem());
		addColumnCustomMenuItems(contextMenu);
		return contextMenu;
	}
	private void addColumnCustomMenuItems(final ContextMenu contextMenu) {
		// menu item for each of the available columns
		tableItemDetails.getColumns().forEach(column -> contextMenu.getItems()
				.add(createColumnCustomMenuItem(contextMenu, column)));
	}



	private CustomMenuItem createColumnCustomMenuItem(
			final ContextMenu contextMenu, final TableColumn<?, ?> column) {
		final CheckBox checkBox = new CheckBox(column.getText());
		// adds listener to the check box to change the size so the user
		// can click anywhere in the menu items area and not just on the
		// text to activate its onAction
		contextMenu.focusedProperty().addListener(
				event -> checkBox.setPrefWidth(contextMenu.getWidth() * 0.75));
		// the context menu item's state controls its bound column's visibility
		checkBox.selectedProperty().bindBidirectional(column.visibleProperty());
		final CustomMenuItem customMenuItem = new CustomMenuItem(checkBox);
		customMenuItem.getStyleClass().set(1, "check-menu-item");
		customMenuItem.setOnAction(event -> {
			checkBox.setSelected(!checkBox.isSelected());
			event.consume();
		});
		// set to false so the context menu stays visible after click
		customMenuItem.setHideOnClick(false);
		return customMenuItem;
	}
	private CustomMenuItem createSelectAllMenuItem(
			final ContextMenu contextMenu) {
		final Label selectAllLabel = new Label("Select all");
		// adds listener to the label to change the size so the user
		// can click anywhere in the menu items area and not just on the
		// text to activate its onAction
		contextMenu.focusedProperty().addListener(event -> selectAllLabel
				.setPrefWidth(contextMenu.getWidth() * 0.75));
		final CustomMenuItem selectAllMenuItem = new CustomMenuItem(
				selectAllLabel);
		selectAllMenuItem.setOnAction(event -> selectAll(event));
		// set to false so the context menu stays visible after click
		selectAllMenuItem.setHideOnClick(false);
		return selectAllMenuItem;
	}


	private void selectAll(final Event event) {
		tableItemDetails.getColumns().forEach(column -> column.setVisible(true));
		event.consume();
	}
	private CustomMenuItem createDeselectAllMenuItem(
			final ContextMenu contextMenu) {
		final Label deselectAllLabel = new Label("Deselect all");
		// adds listener to the label to change the size so the user
		// can click anywhere in the menu items area and not just on the
		// text to activate its onAction
		contextMenu.focusedProperty().addListener(event -> deselectAllLabel
				.setPrefWidth(contextMenu.getWidth() * 0.75));
		final CustomMenuItem deselectAllMenuItem = new CustomMenuItem(
				deselectAllLabel);
		deselectAllMenuItem.setOnAction(event -> deselectAll(event));
		// set to false so the context menu stays visible after click
		deselectAllMenuItem.setHideOnClick(false);
		return deselectAllMenuItem;
	}

	private void deselectAll(final Event event) {
		tableItemDetails.getColumns().forEach(column -> column.setVisible(false));
		event.consume();
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


	public static class ItemDetails{


		private SimpleStringProperty itemId;
		private SimpleStringProperty itemName;
		private SimpleStringProperty unit;
		private SimpleIntegerProperty unitQuantity;
		private SimpleIntegerProperty quantity;

		//private SimpleStringProperty qunatityWithFree;
		private Spinner<Integer> spinner;
		private SimpleDoubleProperty unitPrice;
		private SimpleDoubleProperty price;
		private SimpleDoubleProperty purchasePrice;
		private SimpleDoubleProperty totalAmount;
		private SimpleDoubleProperty discount;
		private SimpleDoubleProperty netAmount;
		private BillingTabController billingTab;
		private SimpleIntegerProperty itemType;
		private boolean isNew;
		private boolean isSales;

		public ItemDetails(String itemId,String itemName,String unit,int unitQuantity,int quantity,double unitPrice,double price,double purchasePrice,double totalAmount,double discount,double netAmount,int itemType,boolean isNew,boolean isSales,BillingTabController billingTab) {
			this.itemId = new SimpleStringProperty(itemId);
			this.itemName = new SimpleStringProperty(itemName);
			this.unit = new SimpleStringProperty(unit);

			this.unitQuantity = new SimpleIntegerProperty(unitQuantity);
			this.quantity = new SimpleIntegerProperty(quantity);

			this.unitPrice = new SimpleDoubleProperty(unitPrice);
			this.price = new SimpleDoubleProperty(price);
			this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
			this.totalAmount = new SimpleDoubleProperty(totalAmount);
			this.discount = new SimpleDoubleProperty(discount);
			this.netAmount = new SimpleDoubleProperty(netAmount);
			this.itemType = new SimpleIntegerProperty(itemType);
			this.billingTab = billingTab;

			this.isNew = isNew;
			this.isSales = isSales;
			setAction();


		}

		public ItemDetails(String itemId,String itemName,String unit,int unitQuantity,int quantity,double unitPrice,double price,double purchasePrice,double totalAmount,double discount,double netAmount,int itemType,boolean isNew,boolean isSales,int maximumQuantity,BillingTabController billingTab) {
			this.itemId = new SimpleStringProperty(itemId);
			this.itemName = new SimpleStringProperty(itemName);
			this.unit = new SimpleStringProperty(unit);

			this.unitQuantity = new SimpleIntegerProperty(unitQuantity);
			this.quantity = new SimpleIntegerProperty(quantity);

			this.unitPrice = new SimpleDoubleProperty(unitPrice);
			this.price = new SimpleDoubleProperty(price);
			this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
			this.totalAmount = new SimpleDoubleProperty(totalAmount);
			this.discount = new SimpleDoubleProperty(discount);
			this.netAmount = new SimpleDoubleProperty(netAmount);
			this.itemType = new SimpleIntegerProperty(itemType);
			this.billingTab = billingTab;
			this.isNew = isNew;
			this.isSales = isSales;


			spinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maximumQuantity, quantity,1));

			spinner.setMinWidth(30);
			spinner.setEditable(true);


			spinner.setOnMouseClicked(e->{
				setQuantity(getSpinnerValue());
				calculateTotalAmount();
				calculateNetAmount();
				billingTab.tableItemDetails.refresh();
				billingTab.netAmountCount();

			});
			spinner.focusedProperty().addListener((ov, oldV, newV) -> {
				if (!newV) { // focus lost
					if(!getSpinnerText().isEmpty()) {
						setSpinnerValue(Integer.valueOf(getSpinnerText()));
						setQuantity(getSpinnerValue());
						calculateTotalAmount();
						calculateNetAmount();
						billingTab.tableItemDetails.refresh();
						billingTab.netAmountCount();

					}else {
						setSpinnerValue(1);
						setQuantity(getSpinnerValue());
						calculateTotalAmount();
						calculateNetAmount();
						billingTab.tableItemDetails.refresh();
						billingTab.netAmountCount();

					}
				}
			});

		}

		private void setAction() {
			if(isNew()) {

				spinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999999, quantity.get(),1));

				spinner.setMinWidth(40);
				spinner.setEditable(true);

				spinner.setOnMouseClicked(e->{
					setQuantity(getSpinnerValue());
					calculateTotalAmount();
					calculateNetAmount();
					billingTab.tableItemDetails.refresh();
					billingTab.netAmountCount();

				});
				spinner.focusedProperty().addListener((ov, oldV, newV) -> {
					if (!newV) { // focus lost
						if(!getSpinnerText().isEmpty()) {
							setSpinnerValue(Integer.valueOf(getSpinnerText()));
							setQuantity(getSpinnerValue());
							calculateTotalAmount();
							calculateNetAmount();
							billingTab.tableItemDetails.refresh();
							billingTab.netAmountCount();

						}else {
							setSpinnerValue(1);
							setQuantity(getSpinnerValue());
							calculateTotalAmount();
							calculateNetAmount();
							billingTab.tableItemDetails.refresh();
							billingTab.netAmountCount();

						}
					}
				});
			}else {
				spinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(quantity.get(), quantity.get(), quantity.get()));
				spinner.setMinWidth(40);
			}


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
		public void setUnitQuantity(int unitQuantity) {
			this.unitQuantity = new SimpleIntegerProperty(unitQuantity);
		}
		public Spinner	getSpinner() {
			return spinner;
		}
		public String getSpinnerText() {
			return spinner.getEditor().getText();
		}
		public void setSpinnerValue(int spinnerValue) {
			this.spinner.getValueFactory().setValue(spinnerValue);
		}

		public int getSpinnerValue() {
			return spinner.getValue();
		}
		public int getQuantity() {
			return quantity.get();
		}
		public int getIntQuantity() {
			return (int) quantity.get();
		}
		public void setQuantity(int quantity) {
			this.quantity = new SimpleIntegerProperty(quantity);
		}

		public void addQuantity(int quantity) {
			this.quantity = new SimpleIntegerProperty(this.quantity.get()+quantity);
			setSpinnerValue(this.quantity.get());
		}



		public void incrementQuantity() {
			this.quantity = new SimpleIntegerProperty(quantity.get()+1);
			setSpinnerValue(this.quantity.get());
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
		public double getPurchasePrice() {
			return purchasePrice.get();
		}
		public void setPurchasePrice(double purchasePrice) {
			this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
		}
		public double getTotalAmount() {
			return totalAmount.get();
		}
		public void setTotalAmount(double totalAmount) {
			this.totalAmount = new SimpleDoubleProperty(totalAmount);
		}
		public void calculateTotalAmount() {
			this.totalAmount = new SimpleDoubleProperty(quantity.get()*price.get());
		}
		public void calculateNetAmount() {
			this.netAmount = new SimpleDoubleProperty((quantity.get()*price.get())-discount.get());
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


		public int getItemType() {
			return itemType.get();
		}

		public void setItemType(int itemType) {
			this.itemType = new SimpleIntegerProperty(itemType);
		}

		public boolean isNew() {
			return isNew;
		}

		public void setNew(boolean isNew) {
			this.isNew = isNew;
		}

		public boolean isSales() {
			return isSales;
		}

		public void setSales(boolean isSales) {
			this.isSales = isSales;
		}

		public String getStatus(){


			if(isSales()) {
				if(isNew()) {
					return "Sale";
				}else {
					return "Sold";
				}
			}else {
				return "Return";
			}

		}

	}

	public static class ItemList{


		private SimpleStringProperty itemId;
		private SimpleStringProperty itemName;
		private SimpleStringProperty unit;
		private SimpleDoubleProperty price;
		private SimpleIntegerProperty type;

		public ItemList(String itemId,String itemName,String unit,double price,int type) {
			this.itemId = new SimpleStringProperty(itemId);
			this.itemName = new SimpleStringProperty(itemName);
			this.unit = new SimpleStringProperty(unit);		
			this.price = new SimpleDoubleProperty(price);
			this.type = new SimpleIntegerProperty(type);
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

		public double getPrice() {
			return price.get();
		}
		public void setPrice(double price) {
			this.price = new SimpleDoubleProperty(price);
		}

		public int getType() {
			return type.get();
		}
		public void setType(int type) {
			this.type = new SimpleIntegerProperty(type);
		}

	}




	public String getTxtContactNo() {
		return txtContactNo.getText().trim();
	}
	public void setAction() {
		// TODO Auto-generated method stub

	}

	public void setTxtContactNo(String txtContactNo) {
		this.txtContactNo.setText(txtContactNo);
	}

	public String getTxtAddress() {
		return txtAddress.getText().trim();
	}
	public void setTxtAddress(String txtAddress) {
		this.txtAddress.setText(txtAddress);
	}

	public String getTxtQuantity() {
		return txtQuantity.getText().trim();
	}


	public void setTxtQuantity(String txtQuantity) {
		this.txtQuantity.setText(txtQuantity);
	}
	public void setTxtQuantity(int txtQuantity) {
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

	public String getTxtTotalAmount() {
		return txtTotalAmount.getText().trim();
	}


	public void setTxtTotalAmount(String txtTotalAmount) {
		this.txtTotalAmount.setText(txtTotalAmount);
	}
	public void setTxtTotalAmount(double txtTotalAmount) {
		this.txtTotalAmount.setText(String.valueOf(txtTotalAmount));
	}

	public String geTtxtTotal() {
		return txtTotal.getText().trim();
	}


	public void setTxtTotal(String txtTotal) {
		this.txtTotal.setText(txtTotal);
	}
	public void setTxtTotal(double txtTotal) {
		this.txtTotal.setText(String.valueOf(txtTotal));
	}


	public String getTxtVat() {
		return txtVat.getText().trim();
	}


	public void setTxtVat(String txtVat) {
		this.txtVat.setText(txtVat);
	}

	public String getTxtVatAmount() {
		return txtVatAmount.getText().trim();
	}


	public void setTxtVatAmount(String txtVatAmount) {
		this.txtVatAmount.setText(txtVatAmount);
	}

	public void setTxtVatAmount(Double txtVatAmount) {
		this.txtVatAmount.setText(dfRoundMoney.format(txtVatAmount));
	}

	public String getTxtServiceChargeAmount() {
		return txtServiceChargeAmount.getText().trim();
	}


	public void setTxtServiceChargeAmount(String txtServiceChargeAmount) {
		this.txtServiceChargeAmount.setText(txtServiceChargeAmount);
	}


	public String getTxtGrossAmount() {
		return txtGrossAmount.getText().trim();
	}


	public void setTxtGrossAmount(String txtGrossAmount) {
		this.txtGrossAmount.setText(txtGrossAmount);
	}
	public void setTxtGrossAmount(double txtGrossAmount) {
		this.txtGrossAmount.setText(dfRoundMoney.format(txtGrossAmount));
	}


	public String getTxtPercentDiscount() {
		return txtPercentDiscount.getText().trim();
	}


	public void setTxtPercentDiscount(String txtPercentDiscount) {
		this.txtPercentDiscount.setText(txtPercentDiscount);
	}
	public void setTxtPercentDiscount(double txtPercentDiscount) {
		this.txtPercentDiscount.setText(dfRoundMoney.format(txtPercentDiscount));
	}


	public String getTxtDiscountPercent() {
		if(txtDiscountPercent.getText().isEmpty()) {
			return "0";
		}
		return txtDiscountPercent.getText().trim();
	}

	public void setTxtDiscountPercent(String txtDiscountPercent) {
		this.txtDiscountPercent.setText(txtDiscountPercent);
	}

	public String getTxtManualDiscount() {
		if(txtManualDiscount.getText().isEmpty()) {
			return "0";
		}
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
		this.txtTotalDiscount.setText(dfRoundMoney.format(txtTotalDiscount));
	}

	public String getTxtPaidAmount() {
		return txtPaidAmount.getText().trim();
	}


	public void setTxtPaidAmount(String txtPaidAmount) {
		this.txtPaidAmount.setText(txtPaidAmount);
	}
	public void setTxtPaidAmount(double txtPaidAmount) {
		this.txtPaidAmount.setText(dfRoundMoney.format(txtPaidAmount));
	}


	public String getTxtNetAmount() {
		return txtNetAmount.getText().trim();
	}


	public void setTxtNetAmount(String txtNetAmount) {
		this.txtNetAmount.setText(txtNetAmount);
	}
	public void setTxtNetAmount(double txtNetAmount) {
		this.txtNetAmount.setText(dfRoundMoney.format(txtNetAmount));
	}


	public String getTxtTakenAmount() {
		return txtTakenAmount.getText().trim();
	}
	public void setTxtTakenAmount(String txtTakenAmount) {
		this.txtTakenAmount.setText(txtTakenAmount);
	}
	public void setTxtTakenAmount(double txtTakenAmount) {
		this.txtTakenAmount.setText(dfRoundMoney.format(txtTakenAmount));
	}


	public String getTxtChangeAmount() {
		return txtChangeAmount.getText().trim();
	}
	public void setTxtChangeAmount(String txtChangeAmount) {
		this.txtChangeAmount.setText(txtChangeAmount);
	}
	public void setTxtChangeAmount(double txtChangeAmount) {
		this.txtChangeAmount.setText(dfRoundMoney.format(txtChangeAmount));
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
	public void setTxtDueAmount(double txtDueAmount) {
		this.txtDueAmount.setText(dfRoundMoney.format(txtDueAmount));
	}
	public String getDate() {
		if(date.getValue() != null)
			return date.getValue().toString();
		else 
			return "";

	}

	public void setDateString(String date) {
		if(date.equals("")) {
			this.date.setValue(null);
			this.date.getEditor().setText("");
		}else {
			try {
				setDate(simpleDateFormate.parse(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	public String getCmbRemarks() {
		if(cmbRemarks.getValue() != null)
			return cmbRemarks.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbRemarks(String txtRemarks) {
		this.cmbRemarks.setValue(txtRemarks);
	}


	public String getCmbOrderType() {
		if(cmbOrderType.getValue() != null)
			return cmbOrderType.getValue().toString().trim();
		else return "";
	}


	public void setCmbOrderType(String cmbOrderType) {
		this.cmbOrderType.setValue(cmbOrderType);
	}

	public String getcmbTableName() {
		if(cmbTableName.getValue() != null)
			return cmbTableName.getValue().toString().trim();
		else return "";
	}


	public void setCmbTableName(String cmbTableInfo) {
		this.cmbTableName.setValue(cmbTableInfo);
	}


	public String getCmbFind() {
		if(cmbCategory.getValue() != null) {
			String find[]=cmbCategory.getValue().toString().trim().split("/");
			return find[0].trim();
		}

		else return "";
	}


	public void setCmbCategory(String cmbCategory) {
		this.cmbCategory.setValue(cmbCategory);
	}

	public String getCmbCategory() {
		if(cmbCategory.getValue() != null)
			return cmbCategory.getValue().toString().trim();
		else return "";
	}


	public String getCmbCustomer() {
		if(cmbCustomer.getValue() != null)
			return cmbCustomer.getValue().toString().trim();
		else return "";
	}


	public void setCmbCustomer(String cmbCustomer) {
		this.cmbCustomer.setValue(cmbCustomer);
	}


	public String getCmbItemName() {
		if(cmbItemName.getValue() != null)
			return cmbItemName.getValue().toString().trim();
		else return "";
	}


	public void setCmbItemName(String cmbItemName) {
		this.cmbItemName.setValue(cmbItemName);
	}

	public int getCurrentCounter() {
		return currentCounter;
	}

	public void setCurrentCounter(int currentCounter) {
		this.currentCounter = currentCounter;
	}

	public Tab getTab() {
		return tab;
	}

	public void setTab(Tab tab) {
		this.tab = tab;
	}

	public TabPane getTabPane() {
		return tabPane;
	}

	public void setTabPane(TabPane tabPane) {
		this.tabPane = tabPane;
	}


	public boolean getRadioCash() {
		return radioCash.isSelected();
	}

	public void setRadioCash(boolean radioCash) {
		this.radioCash.setSelected(radioCash);
	}

	public boolean getRadioCard() {
		return radioCard.isSelected();
	}

	public void setRadioCard(boolean radioCard) {
		this.radioCard.setSelected(radioCard);
	}

	public boolean getRadioBkash() {
		return radioBkash.isSelected();
	}

	public void setRadioBkash(boolean radioBkash) {
		this.radioBkash.setSelected(radioBkash);
	}

	public boolean getCheckReturn() {
		return checkReturn.isSelected();
	}

	public void setCheckReturn(boolean checkReturn) {
		this.checkReturn.setSelected(checkReturn);
	}

	public boolean isInvoiceConfirmed() {
		return isInvoiceConfirmed;
	}

	public void setInvoiceConfirmed(boolean isInvoiceConfirmed) {
		this.isInvoiceConfirmed = isInvoiceConfirmed;
	}

	public boolean isInvoicePay() {
		return isInvoicePay;
	}

	public void setInvoicePay(boolean isInvoicePay) {
		this.isInvoicePay = isInvoicePay;
	}

	public boolean isInvoicePrint() {
		return isInvoicePrint;
	}

	public void setInvoicePrint(boolean isInvoicePrint) {
		this.isInvoicePrint = isInvoicePrint;
	}


	public boolean isInvoiceNew() {
		return isInvoiceNew;
	}

	public void setInvoiceNew(boolean isInvoiceNew) {
		this.isInvoiceNew = isInvoiceNew;
	}

	public boolean isInvoiceEdit() {
		return isInvoiceEdit;
	}

	public void setInvoiceEdit(boolean isInvoiceEdit) {
		this.isInvoiceEdit = isInvoiceEdit;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}



	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public double getCurrentPaidAmount() {
		return currentPaidAmount;
	}

	public void setCurrentPaidAmount(double currentPaidAmount) {
		this.currentPaidAmount = currentPaidAmount;
	}

	public void setPreviousPaidAmount(double previousPaidAmount) {
		this.previousPaidAmount = previousPaidAmount;
	}

	public double getPreviousPaidAmount() {
		return previousPaidAmount;
	}


	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public class BillingTab extends Tab{

		public BillingTab() {

			try {
				this.setText("New Bill");
				this.setClosable(false);

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/trading/BillingTab.fxml"));
				Parent root = fxmlLoader.load();
				BillingTabController billingTabController = fxmlLoader.getController();
				billingTabController.setTab(this);
				billingTabController.setTabPane(tabPane);

				AnchorPane.setTopAnchor(root, 0.0);
				AnchorPane.setLeftAnchor(root, 0.0);
				AnchorPane.setRightAnchor(root, 0.0);
				this.setContent(root);
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e);
			}

		}
	}
}

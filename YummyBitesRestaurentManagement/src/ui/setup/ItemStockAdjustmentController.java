package ui.setup;

import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.controlsfx.control.InfoOverlay;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import shareClasses.AlertMaker;

import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.Notification;
import shareClasses.NumberField;
import shareClasses.SessionBeam;
import ui.trading.PurchaseController.PaymentDetails;
import ui.trading.PurchaseController.ProductDetails;




public class ItemStockAdjustmentController implements Initializable{
	
	
	@FXML
	ComboBox<String> cmbUnit;
	
	FxComboBox cmbFind=new FxComboBox<>();
	FxComboBox cmbItemName=new FxComboBox<>();
	FxComboBox cmbRemarks = new FxComboBox<>();
	
	@FXML
	DatePicker adjustmentDate;
	
	@FXML
	TextField txtStockQty;
	@FXML
	TextField txtCurrentQty;
	@FXML
	TextField txtPrice;
	
	@FXML
	HBox hBoxFind;
	@FXML
	VBox vBoxItemName;
	@FXML
	VBox vBoxRemarks;
	
	
	@FXML
	CheckBox checkAutoAdjustment;
	@FXML
	CheckBox checkWastage;
	
	@FXML
	Button btnAdd;
	@FXML
	Button btnConfirmAdjustment;
	@FXML
	Button btnReCalculateAdjustment;
	
	

	@FXML
	private TableView<AdjustmentTableData> table;

	ObservableList<AdjustmentTableData> list = FXCollections.observableArrayList();

	@FXML
	private TableColumn<AdjustmentTableData, String> slCol;
	@FXML
	private TableColumn<AdjustmentTableData, String> itemIdCol;
	@FXML
	private TableColumn<AdjustmentTableData, String> itemNameCol;
	@FXML
	private TableColumn<AdjustmentTableData, String> stockQuantityCol;
	@FXML
	private TableColumn<AdjustmentTableData, String> currentQuantityCol;
	@FXML
	private TableColumn<AdjustmentTableData, String> adjustQuantityCol;
	@FXML
	private TableColumn<AdjustmentTableData, String> adjustTypeCol;
	@FXML
	private TableColumn<AdjustmentTableData, String> priceCol;
	@FXML
	private TableColumn<AdjustmentTableData, String> totalAmountCol;
	@FXML
	private TableColumn<AdjustmentTableData, String> deleteCol;
	
	
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
	
	private DatabaseHandler databaseHandler;
	private String sql;
	private String inType="8";
	private String outType="9";
	private String wastageType="10";


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		addCmp();
		setCmpData();
		setCmpAction();
		focusMoveAction();
		setCmpFocusAction();
		btnRefreshAction(null);
		cmbFindLoad();
	}
	
	@FXML
	private void btnAddAction(ActionEvent event) {
		if(addValidationCheck()) {
			//There Have Many local Variable for temporary Use
			String itemId,adjustType;

			double unitQuantity=0,quantityPerUnit,stockQuantity,quantity=0,adjustQuantity=0,free=0,unitPrice=0,price=0,totalAmount=0,netAmount=0,discount=0;

			unitQuantity = Double.valueOf(getTxtCurrentQty());


			String unit = getCmbUnit();
			stockQuantity  = Double.valueOf(getTxtStockQty());
			quantityPerUnit = Double.valueOf(unit.substring(unit.indexOf('(')+1, unit.indexOf(')')));
			quantity = unitQuantity * quantityPerUnit;
			
			adjustQuantity = stockQuantity - quantity;
			
			if(adjustQuantity>0) {
				adjustType = "Out";
			}else {
				adjustType = "In";
			}
			
			adjustQuantity = Math.abs(adjustQuantity);
			
			if(getCheckWastage()) {
				adjustType = "Wastage";
				adjustQuantity = quantity;
			}
			
			unitPrice = Double.valueOf(getTxtPrice());
			price = unitPrice/quantityPerUnit;
			
			totalAmount = unitPrice * adjustQuantity;
			discount = 0;
			netAmount  = totalAmount - discount;

			itemId = getItemId(getCmbItemName());

			int rowCount = 0;
			for(rowCount = 0;rowCount < list.size();rowCount++) {
				AdjustmentTableData  tempPd= list.get(rowCount);
				if(tempPd.getItemId().equals(itemId)) {
					
						list.remove(rowCount);
						list.add(rowCount,new AdjustmentTableData(list.size()+1, itemId, getCmbItemName(), unit, unitQuantity, stockQuantity, quantity , adjustQuantity, adjustType, unitPrice, price, totalAmount, 0, "Del"));
						clearTxtAfterItemAdd();
					

					break;
				}
			}
			if(rowCount == list.size()) {
				
					list.add(new AdjustmentTableData(list.size()+1, itemId, getCmbItemName(), unit, unitQuantity, stockQuantity, quantity , adjustQuantity, adjustType, unitPrice, price, totalAmount, 0, "Del"));
					clearTxtAfterItemAdd();
				
			}

			
			table.setItems(list);
			
		}
	}
	
	

	@FXML
	private void btnConfirmAdjustmentAction(ActionEvent event) {
		try {
		
				if(confirmValidaionCheck()) {
					if(confirmationCheck("Save")) {

						String commonTransection="",invoiceNO;
						String d_l_id;
						String c_l_id;
						double inTotal = 0 , outTotal = 0 ,wastageTotal = 0;
						
						invoiceNO = getMaxInvoiceNo();
						
						String ledgerAdjustmentInId="79";
						String ledgerWastageId="80";
						String ledgerAdjustmentOutId="91";
						String ledgerAdjustmentId="90";
						
						btnReCalculateAdjustmentAction(null);
						
						for(int i = 0; i < list.size();i++) {
							AdjustmentTableData  tempATD = list.get(i);
							
							if(tempATD.getAdjustType().equals("In")) {
								inTotal += tempATD.getTotalAmount();
							}else if(tempATD.getAdjustType().equals("Out")){
								outTotal += tempATD.getTotalAmount();
							}else{
								wastageTotal += tempATD.getTotalAmount();
							}
						}
						

						if( inTotal >= 0) {
							commonTransection = transectionAutoId();
							d_l_id = ledgerAdjustmentInId;
							c_l_id = ledgerAdjustmentId;
							
							sql ="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) "
									+ "values('"+transectionAutoId()+"','"+invoiceNO+"','"+inType+"','"+d_l_id+"','"+c_l_id+"','"+inTotal+"','Adjustment In','"+dbDateFormate.format(adjustmentDate.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(sql);

							/*sql="update tbInvoice set transectionId='"+commonTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"'";
							databaseHandler.execAction(sql);*/

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
								+ "entryBy) values('" + inType+invoiceNO + "', "
								+ "'" + dbDateFormate.format(adjustmentDate.getValue()) + "',"
								+ " '', "
								+ "'',"
								+ "'" + inTotal + "',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'" + inTotal + "',"
								+ "'" + inTotal + "',"
								+ "'', "
								+ "'', "
								+ " '0',"
								+ "'" + inType + "',"
								+ "'" + dbDateFormate.format(adjustmentDate.getValue()) + "',"
								+ "'"+commonTransection+"',"
								+ "'','',"
								+ "'',"
								+ "'','',"
								+ "'',"
								+ "'" + getCmbRemarks() + "',"
								+ "CURRENT_TIMESTAMP, "
								+ "'" + SessionBeam.getUserId() + "')";

						databaseHandler.execAction(sql);
						

						if( outTotal >= 0) {
							commonTransection = transectionAutoId();
							d_l_id = ledgerAdjustmentId;
							c_l_id = ledgerAdjustmentOutId;
							
							sql ="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) "
									+ "values('"+transectionAutoId()+"','"+invoiceNO+"','"+outType+"','"+d_l_id+"','"+c_l_id+"','"+outTotal+"','Adjustment Out','"+dbDateFormate.format(adjustmentDate.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(sql);

							/*sql="update tbInvoice set transectionId='"+commonTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"'";
							databaseHandler.execAction(sql);*/

						}
						
						
						sql = "insert into tbInvoice(invoice,"
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
								+ "entryBy) values('" + outType+invoiceNO + "', "
								+ "'" + dbDateFormate.format(adjustmentDate.getValue()) + "',"
								+ " '', "
								+ "'',"
								+ "'" + outTotal + "',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'" + outTotal + "',"
								+ "'" + outTotal + "',"
								+ "'', "
								+ "'', "
								+ " '0',"
								+ "'" + outType + "',"
								+ "'" + dbDateFormate.format(adjustmentDate.getValue()) + "',"
								+ "'"+commonTransection+"',"
								+ "'','',"
								+ "'',"
								+ "'','',"
								+ "'',"
								+ "'" + getCmbRemarks() + "',"
								+ "CURRENT_TIMESTAMP, "
								+ "'" + SessionBeam.getUserId() + "')";

						databaseHandler.execAction(sql);
						
						
						if( wastageTotal >= 0) {
							commonTransection = transectionAutoId();
							d_l_id = ledgerAdjustmentId;
							c_l_id = ledgerWastageId;
							
							sql ="insert into tbaccftransection(transectionid,voucherno,type,d_l_id, c_l_id,amount,description, date, entrytime, createby) "
									+ "values('"+transectionAutoId()+"','"+invoiceNO+"','"+wastageType+"','"+d_l_id+"','"+c_l_id+"','"+outTotal+"','Wastage','"+dbDateFormate.format(adjustmentDate.getValue())+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"')";
							databaseHandler.execAction(sql);

							/*sql="update tbInvoice set transectionId='"+commonTransection+"' where invoice='"+getTxtInvoiceNO()+"' and type='"+type+"'";
							databaseHandler.execAction(sql);*/

						}
						
						
						sql = "insert into tbInvoice(invoice,"
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
								+ "entryBy) values('" + wastageType+invoiceNO + "', "
								+ "'" + dbDateFormate.format(adjustmentDate.getValue()) + "',"
								+ " '', "
								+ "'',"
								+ "'" + wastageTotal + "',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'0',"
								+ "'" + wastageTotal + "',"
								+ "'" + wastageTotal + "',"
								+ "'', "
								+ "'', "
								+ " '0',"
								+ "'" + wastageType + "',"
								+ "'" + dbDateFormate.format(adjustmentDate.getValue()) + "',"
								+ "'"+commonTransection+"',"
								+ "'','',"
								+ "'',"
								+ "'','',"
								+ "'',"
								+ "'" + getCmbRemarks() + "',"
								+ "CURRENT_TIMESTAMP, "
								+ "'" + SessionBeam.getUserId() + "')";

						databaseHandler.execAction(sql);
						
						String inInvoiceAutoNo = getAutoInvoiceNo(inType+invoiceNO, inType);
						String outInvoiceAutoNo = getAutoInvoiceNo(outType+invoiceNO, outType);
						String wastageInvoiceAutoNo = getAutoInvoiceNo(wastageType+invoiceNO, wastageType);
						
						
						for(int i=0;i<list.size();i++) {
							AdjustmentTableData temPd = list.get(i);

							if(temPd.getAdjustType().equals("In")) {
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
										+ "values('" + inInvoiceAutoNo + "',"
										+ "'" + inType+invoiceNO + "',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + temPd.getItemName() + "',"
										+ "'" + temPd.getIsHaveIMEI() + "',"
										+ "'" + temPd.getItemId() + "',"
										+ "'0',"
										+ "'" + temPd.getUnit() + "',"
										+ "'" + temPd.getUnitQuantity() + "',"
										+ "'" + temPd.getAdjustQuantity() + "',"
										+ "'0',"
										+ "'0',"
										+ "'" + temPd.getPrice() + "',"
										+ "'" + temPd.getUnitPrice() + "',"	
										+ "'" +	temPd.getPrice() + "',"
										+ "'" +	temPd.getPrice() + "',"
										+ "'',"
										+ "'" + temPd.getTotalAmount() + "',"
										+ "'0',"
										+ "'0',"
										+ "'" + temPd.getTotalAmount() + "',"
										+ "'" +inType+ "',"
										+ "'0',"
										+ "'',"
										+ "'" + dbDateFormate.format(adjustmentDate.getValue()) + "',"
										+ "CURRENT_TIMESTAMP, "
										+ "'" + SessionBeam.getUserId() + "' )";
							}else if(temPd.getAdjustType().equals("Out")){
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
										+ "values('" + outInvoiceAutoNo + "',"
										+ "'" + outType+invoiceNO + "',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + temPd.getItemName() + "',"
										+ "'" + temPd.getIsHaveIMEI() + "',"
										+ "'0',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + temPd.getUnit() + "',"
										+ "'" + temPd.getUnitQuantity() + "',"
										+ "'" + temPd.getAdjustQuantity() + "',"
										+ "'0',"
										+ "'0',"
										+ "'" + temPd.getPrice() + "',"
										+ "'" + temPd.getUnitPrice() + "',"	
										+ "'" +	temPd.getPrice() + "',"
										+ "'" +	temPd.getPrice() + "',"
										+ "'',"
										+ "'" + temPd.getTotalAmount() + "',"
										+ "'0',"
										+ "'0',"
										+ "'" + temPd.getTotalAmount() + "',"
										+ "'" +outType+ "',"
										+ "'0',"
										+ "'',"
										+ "'" + dbDateFormate.format(adjustmentDate.getValue()) + "',"
										+ "CURRENT_TIMESTAMP, "
										+ "'" + SessionBeam.getUserId() + "' )";
							}else {
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
										+ "values('" + wastageInvoiceAutoNo + "',"
										+ "'" + wastageType+invoiceNO + "',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + temPd.getItemName() + "',"
										+ "'" + temPd.getIsHaveIMEI() + "',"
										+ "'0',"
										+ "'" + temPd.getItemId() + "',"
										+ "'" + temPd.getUnit() + "',"
										+ "'" + temPd.getUnitQuantity() + "',"
										+ "'" + temPd.getAdjustQuantity() + "',"
										+ "'0',"
										+ "'0',"
										+ "'" + temPd.getPrice() + "',"
										+ "'" + temPd.getUnitPrice() + "',"	
										+ "'" +	temPd.getPrice() + "',"
										+ "'" +	temPd.getPrice() + "',"
										+ "'',"
										+ "'" + temPd.getTotalAmount() + "',"
										+ "'0',"
										+ "'0',"
										+ "'" + temPd.getTotalAmount() + "',"
										+ "'" +wastageType+ "',"
										+ "'0',"
										+ "'',"
										+ "'" + dbDateFormate.format(adjustmentDate.getValue()) + "',"
										+ "CURRENT_TIMESTAMP, "
										+ "'" + SessionBeam.getUserId() + "' )";
							}
							

							databaseHandler.execAction(sql);
					
							
						}
						new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfull....!","Adjustment Save Successfully...");
						cmbItemName.requestFocus();
						//btnPrintAction(null);
						//btnRefreshAction(null);
						btnRefreshAction(null);
						cmbFindLoad();
					}
				}
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	

	@FXML
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub
		productNameLoad();
		
		list.clear();
		table.setItems(list);
		
		setCmbItemName("");
		setCmbUnit("");
		setCmbRemarks("");
		setTxtStockQty("0");
		setTxtCurrentQty("0");
		setTxtPrice("0");
		setAdjustmentDate(new Date());
		setCheckAutoAdjustment(true);
		setCheckWastage(false);
		//setCmbFind("");
		
		btnConfirmAdjustment.setDisable(false);
		
	}
	
	@FXML
	private void btnReCalculateAdjustmentAction(ActionEvent event) {
		// TODO Auto-generated method stub
		for(int i =0 ;i<list.size();i++) {
			AdjustmentTableData tempATD = list.get(i);
			double stockQuantity = getPresentStcok(tempATD.getItemId());
			double currentQuantity = tempATD.getCurrentQuantity();
			double adjustQuantity = stockQuantity - currentQuantity;
			
			if(tempATD.getAdjustType().equals("Wastage")) {
				
				adjustQuantity = currentQuantity;
				
			}else {
				if(adjustQuantity > 0) {
					tempATD.setAdjustType("Out");
				}else{
					tempATD.setAdjustType("In");
				}
				adjustQuantity = Math.abs(adjustQuantity);
			}
			
			
			
			
			
			tempATD.setStockQuantity(stockQuantity);
			tempATD.setAdjustQuantity(adjustQuantity);
			tempATD.setTotalAmount(tempATD.getPrice()*adjustQuantity);
			
			list.remove(i);
			if(tempATD.getAdjustQuantity() != 0 ) {
				list.add(i,tempATD);
			}else {
				--i;
			}
				
		}
		
		table.setItems(list);
	}
	
	
	@FXML
	private void tableClickAction(MouseEvent mouseEvent) {
		try {
			if(table.getSelectionModel().getSelectedItem() != null) {
				TablePosition firstCell = table.getSelectionModel().getSelectedCells().get(0);
				AdjustmentTableData  tempProductDetail= table.getSelectionModel().getSelectedItem();
				if(firstCell.getColumn() == 9) {
					if(confirmationCheck("Delete This Item From")) {
						list.remove(tempProductDetail);
						table.setItems(list);
					}

				}else {

					if(tempProductDetail != null) {
						setCmbItemName(tempProductDetail.getItemName());
						unitLoadByProductName();
						setCmbUnit(tempProductDetail.getUnit());
						setTxtCurrentQty(tempProductDetail.getCurrentQuantity());	
						setTxtPrice(tempProductDetail.getUnitPrice());

						stockSetByProductName();
					}
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	

	private boolean addValidationCheck() {
		// TODO Auto-generated method stub
		if(!getCmbItemName().isEmpty()) {
			if(!getCmbUnit().isEmpty()) {
				if(!getTxtCurrentQty().isEmpty()) {

					if(!getTxtPrice().isEmpty()) {
						if(!getTxtPrice().isEmpty()) {
								if(checkIsProductNameValid()) {
									if(Double.valueOf(getTxtCurrentQty())>=0 &&((getCheckWastage()?true:false) ||(Double.valueOf(getTxtCurrentQty()) != Double.valueOf(getTxtCurrentQty())))) {
										String unit = getCmbUnit();
										double quantity;
										quantity = Double.valueOf(unit.substring(unit.indexOf('(')+1, unit.indexOf(')')));
										quantity = Double.valueOf(getTxtCurrentQty()) * quantity;
										if((getCheckWastage()?true:false) || quantity != Double.valueOf(getTxtStockQty())) {
											for(int i = 0;i<list.size();i++) {
												AdjustmentTableData tempPd = list.get(i);

											}
											return true;
										}else {
											new Notification(Pos.TOP_CENTER, "Warning graphic", "Stock is Same!","Your Current Quantity and Stock Quantity are same..");
											txtCurrentQty.requestFocus();
										}
									}else {
										new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Quanity!","Quantity Must be more than or equal to 0..");
										txtCurrentQty.requestFocus();
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
					txtCurrentQty.requestFocus();
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
	
	
	private boolean confirmValidaionCheck() {
		// TODO Auto-generated method stub
		if(list.size()>0) {
			//if(isCustomerExist()) {
			if(!getAdjustmentDate().isEmpty()) {
				return true;
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Your Date Is InValid..\nPlease Select a Valid Date...");
				adjustmentDate.requestFocus();
			}
			
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Any Product To Adjustment Table..");
			cmbItemName.requestFocus();
		}
		return false;
	}
	
	
	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message+" this Adjustment?");
	}
	
	private void clearTxtAfterItemAdd() {
		// TODO Auto-generated method stub
		//setCmbItemName("");
		setCmbUnit("");
		cmbUnit.getItems().clear();
		setTxtCurrentQty("0");
		setTxtPrice("0");
		setTxtStockQty("0");
		
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
	
	public double getPresentStcok(String itemId) {
		try {
			sql = "select dbo.presentStock('"+itemId+"') as stock";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getDouble("stock");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private String getAutoInvoiceNo(String invoiceNO,String type) {
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
	
	private String getMaxInvoiceNo() {
		// TODO Auto-generated method stub
		try {
			String year = String.valueOf(calender.get(Calendar.YEAR)).substring(2);
			String month = dfId.format(calender.get(Calendar.MONTH)+1);
	
			sql = "select  IsNULL(MAX(cast(SUBSTRING(Invoice,7,LEN(Invoice)-6) as int)),0)+1 as maxInvoiceId from tbInvoice WHERE type = '"+inType+"' and Invoice LIKE '"+inType+year+month+"%';";
			ResultSet rs = databaseHandler.execQuery(sql);
			if (rs.next()) {
				return year+month+dfId.format(rs.getInt("maxInvoiceId"));		
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
	
	
	private boolean isAdjustmentIdExist(String invoiceId) {
		// TODO Auto-generated method stub
		try {
			sql = "select * from tbInvoice where type = '"+inType+"' and Invoice = '"+invoiceId+"'";
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
	
	private void setCmpAction() {
		// TODO Auto-generated method stub
		
		btnAdd.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnAddAction(null); });
		
		cmbItemName.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV && !getCmbItemName().isEmpty()) { // focus lost
				if(checkIsProductNameValid()) {
					unitLoadByProductName();
					stockSetByProductName();
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select a valid Item Name...");
					cmbItemName.requestFocus();
				}
			}
		});
		
		cmbFind.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					findAction(newValue);
				}
			}    
		});
		
		checkAutoAdjustment.setOnAction(event ->{
			if(checkAutoAdjustment.isSelected()) {
				checkWastage.setSelected(false);

			}
		});
		
		checkWastage.setOnAction(event ->{
			if(checkWastage.isSelected()) {
				checkAutoAdjustment.setSelected(false);

			}
		});
	}

	protected void findAction(String newValue) {
		// TODO Auto-generated method stub
		try {
			if(!getCmbFind().isEmpty()) {
				if(isAdjustmentIdExist(getCmbFind())) {
					
					sql = "select * from tbStore where invoiceNo = '"+getCmbFind()+"' and (type = '"+inType+"' or type = '"+outType+"' or type = '"+wastageType+"')";
					ResultSet rs = databaseHandler.execQuery(sql);
					list.clear();
					while(rs.next()) {
						list.add(new AdjustmentTableData(list.size()+1,rs.getString("itemId"),rs.getString("itemName"), rs.getString("unit"), rs.getDouble("UnitQty"),0.0 ,0.0, rs.getDouble("qty"),(rs.getInt("type")==8?"In":"Out"), rs.getDouble("unitPrice"), rs.getDouble("PurchasePrice"), rs.getDouble("totalamount"),  rs.getInt("isHaveIMEI"), "Del"));
					}
					btnConfirmAdjustment.setDisable(true);
					table.setItems(list);	
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
			sql = "select id,u.UnitName,u.UnitQuantity,IMEI from tbitem i  " + 
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

	public void stockSetByProductName() {
		try {
			sql = "select dbo.presentStock(i.id) as stock,id,salePrice from tbItem i where projectedItemName = '"+getCmbItemName()+"';";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtStockQty(rs.getInt("stock"));
				setTxtPrice(rs.getDouble("salePrice"));
			}



		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private void cmbFindLoad() {
		try {
			sql = "select invoice from tbInvoice where type = '"+(checkAutoAdjustment.isSelected()?inType:outType)+"' order by Invoice desc";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbFind.data.clear();
			while(rs.next()) {
				cmbFind.data.add(rs.getString("invoice"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void productNameLoad() {
		try {
			sql = "select projectedItemName from tbItem i\r\n" + 
					"join tbCategory c\r\n" + 
					"on c.id = i.CategoryId\r\n" + 
					"order by c.categoryName,projectedItemName";
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
		cmbItemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbItemName);
		});
		
		cmbFind.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbFind);
		});
		txtPrice.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtPrice);
		});
		txtCurrentQty.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtCurrentQty);
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
		// TODO Auto-generated method stub
		Control[] control =  {adjustmentDate,cmbItemName,txtCurrentQty,txtPrice,btnAdd};
		new FocusMoveByEnter(control);
	}


	private void setCmpData() {
		// TODO Auto-generated method stub
		adjustmentDate.setConverter(converter);
		adjustmentDate.setValue(LocalDate.now());



		txtCurrentQty.setTextFormatter(NumberField.getIntegerFormate());
		txtStockQty.setTextFormatter(NumberField.getDoubleFormate());
		txtPrice.setTextFormatter(NumberField.getDoubleFormate());
		
		slCol.setCellValueFactory(new PropertyValueFactory("sl"));
		itemIdCol.setCellValueFactory(new PropertyValueFactory("itemId"));
		itemNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		stockQuantityCol.setCellValueFactory(new PropertyValueFactory("stockQuantity"));
		currentQuantityCol.setCellValueFactory(new PropertyValueFactory("currentQuantity"));
		adjustQuantityCol.setCellValueFactory(new PropertyValueFactory("adjustQuantity"));
		adjustTypeCol.setCellValueFactory(new PropertyValueFactory("adjustType"));
		priceCol.setCellValueFactory(new PropertyValueFactory("price"));
		totalAmountCol.setCellValueFactory(new PropertyValueFactory("totalAmount"));
		deleteCol.setCellValueFactory(new PropertyValueFactory("delete"));

		table.setColumnResizePolicy(table.CONSTRAINED_RESIZE_POLICY);

	}






	private void addCmp() {
		// TODO Auto-generated method stub
		cmbFind.setPrefWidth(100);
		cmbFind.setPrefHeight(28);
		
		hBoxFind.getChildren().remove(1);
		hBoxFind.getChildren().add(1,cmbFind);

		cmbItemName.setPrefWidth(420);
		cmbItemName.setPrefHeight(28);

		vBoxItemName.getChildren().remove(1);
		vBoxItemName.getChildren().add(1,cmbItemName);

		cmbRemarks.setPrefWidth(1200);
		cmbRemarks.setPrefHeight(28);

		vBoxRemarks.getChildren().clear();
		vBoxRemarks.getChildren().add(cmbRemarks);
	}






	public class AdjustmentTableData{
		
		private SimpleIntegerProperty sl;
		private SimpleStringProperty itemId;
		private SimpleStringProperty itemName;
		private SimpleStringProperty unit;
		private SimpleDoubleProperty unitQuantity;
		private SimpleDoubleProperty stockQuantity;
		private SimpleDoubleProperty currentQuantity;
		private SimpleDoubleProperty adjustQuantity;
		private SimpleStringProperty adjustType;
		private SimpleDoubleProperty unitPrice;
		private SimpleDoubleProperty price;
		private SimpleDoubleProperty totalAmount;
		private SimpleIntegerProperty isHaveIMEI;
		private SimpleStringProperty delete;
		
		public AdjustmentTableData(int sl, String itemId,String itemName,String unit,double unitQuantity,double stockQuantity,double currentQuantity,double adjustQuantity,String adjustType,double unitPrice,double price,double totalAmount,int isHaveImei,String delete) {
			this.sl = new SimpleIntegerProperty(sl);
			this.itemId = new SimpleStringProperty(itemId);
			this.itemName = new SimpleStringProperty(itemName);
			this.unit = new SimpleStringProperty(unit);
			this.unitQuantity = new SimpleDoubleProperty(unitQuantity);
			this.stockQuantity = new SimpleDoubleProperty(stockQuantity);
			this.currentQuantity = new SimpleDoubleProperty(currentQuantity);
			this.adjustQuantity = new SimpleDoubleProperty(adjustQuantity);
			this.adjustType = new SimpleStringProperty(adjustType);
			this.unitPrice = new SimpleDoubleProperty(unitPrice);
			this.price = new SimpleDoubleProperty(price);
			this.totalAmount = new SimpleDoubleProperty(totalAmount);
			this.isHaveIMEI = new SimpleIntegerProperty(isHaveImei);
			this.delete = new SimpleStringProperty(delete);
		}

		public int getSl() {
			return sl.get();
		}

		public void setSl(int sl) {
			this.sl = new SimpleIntegerProperty(sl);
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

		public double getStockQuantity() {
			return stockQuantity.get();
		}

		public void setStockQuantity(double stockQuantity) {
			this.stockQuantity = new SimpleDoubleProperty(stockQuantity);
		}

		public double getCurrentQuantity() {
			return currentQuantity.get();
		}

		public void setCurrentQuantity(double currentQuantity) {
			this.currentQuantity = new SimpleDoubleProperty(currentQuantity);
		}

		public double getAdjustQuantity() {
			return adjustQuantity.get();
		}

		public void setAdjustQuantity(double adjustQuantity) {
			this.adjustQuantity = new SimpleDoubleProperty(adjustQuantity);
		}

		public String getAdjustType() {
			return adjustType.get();
		}

		public void setAdjustType(String adjustType) {
			this.adjustType = new SimpleStringProperty(adjustType);
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

		public int getIsHaveIMEI() {
			return isHaveIMEI.get();
		}

		public void setIsHaveIMEI(int isHaveIMEI) {
			this.isHaveIMEI = new SimpleIntegerProperty(isHaveIMEI);
		}

		public String getDelete() {
			return delete.get();
		}

		public void setDelete(String delete) {
			this.delete = new SimpleStringProperty(delete);
		}

		
		
		
	}






	public String getCmbUnit() {
		if(cmbUnit.getValue() != null)
			return cmbUnit.getValue().toString().trim();
		else
			return "";
	}

	public void setCmbUnit(String cmbUnit) {
		this.cmbUnit.setValue(cmbUnit);
	}

	public String getCmbFind() {
		if(cmbFind.getValue() != null)
			return cmbFind.getValue().toString().trim();
		else
			return "";
	}

	public void setCmbFind(String cmbFind) {
		this.cmbFind.setValue(cmbFind);
	}

	public String getCmbItemName() {
		if(cmbItemName.getValue() != null)
			return cmbItemName.getValue().toString().trim();
		else
			return "";
	}

	public void setCmbItemName(String cmbItemName) {
		this.cmbItemName.setValue(cmbItemName);
	}

	public String getCmbRemarks() {
		if(cmbRemarks.getValue() != null)
			return cmbRemarks.getValue().toString().trim();
		else
			return "";
	}

	public void setCmbRemarks(String cmbRemarks) {
		this.cmbRemarks.setValue(cmbRemarks);
	}

	public String getAdjustmentDate() {
		if(adjustmentDate.getValue() != null)
			return adjustmentDate.getValue().toString();
		else 
			return "";
	}

	public void setAdjustmentDate(Date adjustmentDate) {
		if(adjustmentDate != null) {
			int dd=Integer.valueOf(new SimpleDateFormat("dd").format(adjustmentDate));
			int mm=Integer.valueOf(new SimpleDateFormat("MM").format(adjustmentDate));
			int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(adjustmentDate));
			this.adjustmentDate.setValue(LocalDate.of(yy,mm,dd));
		}else {
			this.adjustmentDate.getEditor().setText("");
			this.adjustmentDate.setValue(null);
		}
	}

	public String getTxtStockQty() {
		return txtStockQty.getText().trim();
	}

	public void setTxtStockQty(String txtStockQty) {
		this.txtStockQty.setText(txtStockQty);
	}
	public void setTxtStockQty(int txtStockQty) {
		this.txtStockQty.setText(String.valueOf(txtStockQty));
	}

	public String getTxtCurrentQty() {
		return txtCurrentQty.getText().trim();
	}

	public void setTxtCurrentQty(String txtCurrentQty) {
		this.txtCurrentQty.setText(txtCurrentQty);
	}
	public void setTxtCurrentQty(double txtCurrentQty) {
		this.txtCurrentQty.setText(String.valueOf(txtCurrentQty));
	}

	public String getTxtPrice() {
		return txtPrice.getText().trim();
	}

	public void setTxtPrice(String txtPrice) {
		this.txtPrice.setText(txtPrice);
	}
	public void setTxtPrice(double txtPrice) {
		this.txtPrice.setText(df.format(txtPrice));
	}

	public boolean getCheckAutoAdjustment() {
		return checkAutoAdjustment.isSelected();
	}

	public void setCheckAutoAdjustment(boolean checkAdjustIn) {
		this.checkAutoAdjustment.setSelected(checkAdjustIn);
	}

	public boolean getCheckWastage() {
		return checkWastage.isSelected();
	}

	public void setCheckWastage(boolean checkAdjustOut) {
		this.checkWastage.setSelected(checkAdjustOut);
	}

	
	
}


package ui.reports;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.security.auth.callback.ChoiceCallback;
import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
import shareClasses.ItemType;
import shareClasses.Notification;
import shareClasses.NumberField;
import shareClasses.SessionBeam;
import shareClasses.StageType;
import ui.trading.BillingTabController.ItemList;

public class TrandingReportController implements Initializable{


	@FXML
	RadioButton radioPurchaseStatement;
	@FXML
	RadioButton radioPurchaseStatementSupplierWise;
	@FXML
	RadioButton radioSalesStatement;
	@FXML
	RadioButton radioSalesStatementCustomerWise;
	@FXML
	RadioButton radioSalesDetails;
	@FXML
	RadioButton radioDueSalesStatementDetails;
	@FXML
	RadioButton radioItemWiseSaleStatement;
	@FXML
	RadioButton radioAllItemStockPosition;
	@FXML
	RadioButton radioAllItemStockPositionByDate;
	@FXML
	RadioButton radioRawMaterialsStockPositionByDate;
	@FXML
	RadioButton radioReorderItemList;
	/*@FXML
	RadioButton radioImeiSerialInformation;*/

	@FXML
	RadioButton radioSummery;	
	@FXML
	RadioButton radiokDetails;

	@FXML
	CheckBox checkCustomerAll;
	@FXML
	CheckBox checkSupplierAll;
	@FXML
	CheckBox checkCategoryAll;
	@FXML
	CheckBox checkItemNameAll;
	@FXML
	CheckBox checkRawMaterialsAll;
	@FXML
	CheckBox checkSearchBy;

	CheckBox checkSelectAll;


	@FXML
	Button btnPreview;
	@FXML
	Button btnAdd;


	@FXML
	DatePicker dateFromDate;
	@FXML
	DatePicker dateToDate;

	@FXML
	TextField txtReorderQty;



	@FXML
	VBox vBoxCustomerName;
	@FXML
	VBox vBoxSupplierName;
	@FXML
	VBox vBoxCategoryName;
	@FXML
	VBox vBoxItemName;
	@FXML
	VBox vBoxRawMaterials;


	@FXML
	HBox hBoxContentName;

	FxComboBox cmbCustomerName = new FxComboBox<>();
	FxComboBox cmbSupplierName = new FxComboBox<>();
	FxComboBox cmbCategoryName = new FxComboBox<>();
	FxComboBox cmbItemName = new FxComboBox<>();
	FxComboBox cmbRawMaterials = new FxComboBox<>();
	FxComboBox cmbContentName = new FxComboBox<>();
	//FxComboBox cmbImei = new FxComboBox<>();

	@FXML
	private TableView<ContentInfo> tableMultipleContentList;

	ObservableList<ContentInfo> listMultipleContent = FXCollections.observableArrayList();

	@FXML
	private TableColumn<ContentInfo, String> contentIdCol;
	@FXML
	private TableColumn<ContentInfo, String> contentNameCol;
	@FXML
	private TableColumn<ContentInfo, String> checkSelectCol;



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

	private final int finishedGoods = 1;
	private final int rawMaterials=2;


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
		SupplierLoad();
		CategoryLoad();
		ItemNameLoad();
		rawMaterialsLoad();


		checkCategoryAll.setDisable(true);
		checkItemNameAll.setDisable(true);
		checkRawMaterialsAll.setDisable(true);
		checkSupplierAll.setDisable(true);
		checkCustomerAll.setDisable(true);


		cmbCategoryName.setDisable(true);
		cmbItemName.setDisable(true);
		cmbRawMaterials.setDisable(true);
		cmbSupplierName.setDisable(true);
		cmbCustomerName.setDisable(true);


		setCheckCustomerAll(false);
		setCheckSupplierAll(false);
		setCheckCategoryAll(false);
		setCheckItemNameAll(false);
		setCheckRawMaterialsAll(false);


		setCmbCustomerName("");
		setCmbSupplierName("");
		setCmbCategoryName("");
		setCmbItemName("");



		dateFromDate.setDisable(false);
		dateToDate.setDisable(false);

		radioSummery.setDisable(false);
		radiokDetails.setDisable(false);

	}

	@FXML
	private void btnPreviewAction(ActionEvent event) {

		map.put("fromDate", getDateFromDate());
		map.put("toDate", getDateToDate());

		if(getRadioPurchaseStatement()) {
			openPurchaseStatement();;
		}else if(getRadioPurchaseStatementSupplierWise()) {
			openPurchaseStatementSupplierWise();;
		}else if(getRadioSalesStatement()) {
			openSaleStatement();
		}else if(getRadioSalesStatementCustomerWise()) {
			openSaleStatementCustomerWise();
		}else if(getRadioSalesDetails()) {
			openSalesDetails();
		}else if(getRadioDueSalesStatementDetails()) {
			openDueSalesStatementDetails();
		}else if(getRadioItemWiseSaleStatement()) {
			openItemWiseSalesStatement();
		}else if(getRadioAllItemStockPosition()) {
			openAllItemStockPosition();
		}else if(getRadioAllItemStockPositionByDate()) {
			openAllItemStockPositionByDate();
		}else if(getRadioRawMaterialsStockPositionByDate()) {
			openRawMaterialsStockPositionByDate();
		}else if(getRadioReorderItemList()) {
			openReorderItemList();
		}

	}

	@FXML
	private void btnAddAction(ActionEvent evetnt) {
		if(!getCmbContentName().isEmpty()) {
			if(getRadioAllItemStockPosition() || getRadioAllItemStockPositionByDate()|| getRadioRawMaterialsStockPositionByDate()) {

				multipleContentAddInList(new ContentInfo(getItemId(getCmbContentName()), getCmbContentName(), "Item", true));
				tableMultipleContentList.setItems(listMultipleContent);
			}else if(getRadioPurchaseStatementSupplierWise()) {

			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Any Content Name...","Please Select a valid Content Name");
			cmbContentName.requestFocus();
		}
	}



	private void openPurchaseStatement() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/PurchaseStatement.jrxml";
				sql = "select date,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 1 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by date order by date ";
			}else {
				report = "src/resource/reports/tradingReportFile/PurchaseStatementDetails.jrxml";
				sql = "select date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 1 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by date,Invoice order by date";
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

	private void openPurchaseStatementSupplierWise() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/PurchaseStatementSupplierWise.jrxml";
				if(getCmbSupplierName().equals(""))
					sql = "select SellerCustomerName as suppliername,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 1 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by SellerCustomerName order by SellerCustomerName";
				else
					sql = "select SellerCustomerName as suppliername,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 1 and SellerCustomerName = '"+getCmbSupplierName()+"' and supplierId = '"+getSupplierId(getCmbSupplierName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by SellerCustomerName order by SellerCustomerName";
			}else {
				report = "src/resource/reports/tradingReportFile/PurchaseStatementSupplierWiseDetails.jrxml";

				if(getCmbSupplierName().equals(""))
					sql = "select SellerCustomerName as suppliername,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 1 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by SellerCustomerName,date,Invoice order by SellerCustomerName";
				else
					sql = "select SellerCustomerName as suppliername,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 1 and SellerCustomerName = '"+getCmbSupplierName()+"' and supplierID = '"+getSupplierId(getCmbSupplierName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by SellerCustomerName,date,Invoice order by SellerCustomerName";
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


	private void openSaleStatement() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/SaleStatement.jrxml";
				sql = "select date,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by date order by date ";
			}else {
				report = "src/resource/reports/tradingReportFile/SaleStatementDetails.jrxml";
				sql = "select date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by date,Invoice order by date";
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

	private void openSaleStatementCustomerWise() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWise.jrxml";
				if(getCmbCustomerName().equals(""))
					sql = "select SellerCustomerName as customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by SellerCustomerName order by SellerCustomerName";
				else
					sql = "select SellerCustomerName as customerName,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 3 and customerName = '"+getCmbCustomerName()+"' and customerID = '"+getCustomerId(getCmbCustomerName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by SellerCustomerName order by SellerCustomerName";
			}else {
				report = "src/resource/reports/tradingReportFile/SaleStatementCustomerWiseDetails.jrxml";

				if(getCmbCustomerName().equals(""))
					sql = "select SellerCustomerName as customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 3 and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by SellerCustomerName,date,Invoice order by SellerCustomerName";
				else
					sql = "select SellerCustomerName as customerName,date,Invoice,SUM(isnull(totalAmount,0))as totalAmount,SUM(isnull(vatAmount,0))as vatAmount,SUM(isnull(totalDiscount,0))as totalDiscount,SUM(isnull(netAmount,0))as netAmount,SUM(isnull(paid,0))as paid,(SUM(isnull(netAmount,0)) - SUM(isnull(paid,0)))as due from tbInvoice where type = 3 and SellerCustomerName = '"+getCmbCustomerName()+"' and customerID = '"+getCustomerId(getCmbCustomerName())+"' and date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by SellerCustomerName,date,Invoice order by SellerCustomerName";
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

	private void openSalesDetails() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";
			String itemId = getItemId(getCmbItemName());


			report = "src/resource/reports/tradingReportFile/SalesDetailsReport.jrxml";

			if(!getCheckCustomerAll() && !getCmbCustomerName().isEmpty()  && !getCheckItemNameAll() && !getCmbItemName().isEmpty()){
				sql = "select i.Invoice,i.date,i.SellerCustomerName,i.Mobileno,i.ApprovedBy,i.PromissDate,i.totalAmount,i.vat,i.vatAmount,i.discount,i.percentDiscount,i.manualDiscount,i.totalDiscount,i.netAmount,i.paid,i.cash,i.Card,i.due,l.username,s.invoiceNo,s.itemId,s.itemName,s.warrantyDate,s.unit,s.UnitQty,s.qty,freeQty,s.freeItemPrice,s.UnitPrice,s.AveragePrice,s.SalesPrice,s.totalAmount as sdTotalAmount,s.discount as sdDiscount,s.netAmount as sdNetAmount "+
						"from tbInvoice i  "+
						"join tbStore s "+
						"on i.Invoice = s.invoiceNo  "+
						"join tblogin l  "+
						"on i.entryby = l.user_Id  "+
						"where i.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' and i.type = '"+StageType.Sales.getType()+"' and s.itemType = '"+ItemType.Finish_Goods.getType()+"' and i.SellerCustomerName = '"+getCmbCustomerName()+"' and s.itemId = '"+itemId+"' "+
						"order by i.type,i.Invoice";

			}else if((!getCheckCustomerAll() && !getCmbCustomerName().isEmpty()) && getCheckItemNameAll()){
				sql = "select i.Invoice,i.date,i.SellerCustomerName,i.Mobileno,i.ApprovedBy,i.PromissDate,i.totalAmount,i.vat,i.vatAmount,i.discount,i.percentDiscount,i.manualDiscount,i.totalDiscount,i.netAmount,i.paid,i.cash,i.Card,i.due,l.username,s.invoiceNo,s.itemId,s.itemName,s.warrantyDate,s.unit,s.UnitQty,s.qty,freeQty,s.freeItemPrice,s.UnitPrice,s.AveragePrice,s.SalesPrice,s.totalAmount as sdTotalAmount,s.discount as sdDiscount,s.netAmount as sdNetAmount  "+
						"from tbInvoice i  "+
						"join tbStore s   "+
						"on i.Invoice = s.invoiceNo   "+
						"join tblogin l  "+
						"on i.entryby = l.user_Id  "+
						"where i.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' and i.type = '"+StageType.Sales.getType()+"' and s.itemType = '"+ItemType.Finish_Goods.getType()+"' and i.SellerCustomerName = '"+getCmbCustomerName()+"' "+
						"order by i.type,i.Invoice";
			}else if(getCheckCustomerAll()  && (!getCheckItemNameAll() && !getCmbItemName().isEmpty())){
				sql = "select i.Invoice,i.date,i.SellerCustomerName,i.Mobileno,i.ApprovedBy,i.PromissDate,i.totalAmount,i.vat,i.vatAmount,i.discount,i.percentDiscount,i.manualDiscount,i.totalDiscount,i.netAmount,i.paid,i.cash,i.Card,i.due,l.username,s.invoiceNo,s.itemId,s.itemName,s.warrantyDate,s.unit,s.UnitQty,s.qty,freeQty,s.freeItemPrice,s.UnitPrice,s.AveragePrice,s.SalesPrice,s.totalAmount as sdTotalAmount,s.discount as sdDiscount,s.netAmount as sdNetAmount  "+
						"from tbInvoice i  "+
						"join tbStore s   "+
						"on i.Invoice = s.invoiceNo   "+
						"join tblogin l  "+
						"on i.entryby = l.user_Id  "+
						"where i.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' and i.type = '"+StageType.Sales.getType()+"' and s.itemType = '"+ItemType.Finish_Goods.getType()+"' and s.itemId = '"+itemId+"' "+
						"order by i.type,i.Invoice";
			}else if(getCheckCustomerAll()  && getCheckItemNameAll()){
				sql = "select i.Invoice,i.date,i.SellerCustomerName,i.Mobileno,i.ApprovedBy,i.PromissDate,i.totalAmount,i.vat,i.vatAmount,i.discount,i.percentDiscount,i.manualDiscount,i.totalDiscount,i.netAmount,i.paid,i.cash,i.Card,i.due,l.username,s.invoiceNo,s.itemId,s.itemName,s.warrantyDate,s.unit,s.UnitQty,s.qty,freeQty,s.freeItemPrice,s.UnitPrice,s.AveragePrice,s.SalesPrice,s.totalAmount as sdTotalAmount,s.discount as sdDiscount,s.netAmount as sdNetAmount  "+
						"from tbInvoice i  "+
						"join tbStore s   "+
						"on i.Invoice = s.invoiceNo  "+ 
						"join tblogin l  "+
						"on i.entryby = l.user_Id  "+
						"where i.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' and i.type = '"+StageType.Sales.getType()+"' and s.itemType = '"+ItemType.Finish_Goods.getType()+"' "+
						"order by i.type,i.Invoice";
			}else{
				sql = "select i.Invoice,i.date,i.SellerCustomerName,i.Mobileno,i.ApprovedBy,i.PromissDate,i.totalAmount,i.vat,i.vatAmount,i.discount,i.percentDiscount,i.manualDiscount,i.totalDiscount,i.netAmount,i.paid,i.cash,i.Card,i.due,l.username,s.invoiceNo,s.itemId,s.itemName,s.warrantyDate,s.unit,s.UnitQty,s.qty,freeQty,s.freeItemPrice,s.UnitPrice,s.AveragePrice,s.SalesPrice,s.totalAmount as sdTotalAmount,s.discount as sdDiscount,s.netAmount as sdNetAmount "+ 
						"from tbInvoice i  "+
						"join tbStore s   "+
						"on i.Invoice = s.invoiceNo   "+
						"join tblogin l  "+
						"on i.entryby = l.user_Id  "+
						"where i.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' and i.type = '"+StageType.Sales.getType()+"' and s.itemType = '"+ItemType.Finish_Goods.getType()+"' "+
						"order by i.type,i.Invoice";
			}

			map.put("fromDate", getDateFromDate());
			map.put("toDate", getDateToDate());
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


	private void openDueSalesStatementDetails() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";
			String itemId = getItemId(getCmbItemName());


			report = "src/resource/reports/tradingReportFile/DueSalesStatementDetailsReport.jrxml";

			if(!getCheckCustomerAll() && !getCmbCustomerName().isEmpty()  ){
				sql = "select i.Invoice,i.date,i.SellerCustomerName,i.Mobileno,i.ApprovedBy,i.PromissDate,i.totalAmount,i.vat,i.vatAmount,i.discount,i.percentDiscount,i.manualDiscount,i.totalDiscount,i.netAmount,i.paid,i.cash,i.Card,i.due,l.username,s.invoiceNo,s.itemId,s.itemName,s.warrantyDate,s.unit,s.UnitQty,s.qty,freeQty,s.freeItemPrice,s.UnitPrice,s.AveragePrice,s.SalesPrice,s.totalAmount as sdTotalAmount,s.discount as sdDiscount,s.netAmount as sdNetAmount "+
						"from tbInvoice i  "+
						"join tbStore s "+
						"on i.Invoice = s.invoiceNo  "+
						"join tblogin l  "+
						"on i.entryby = l.user_Id  "+
						"where i.due>0 and i.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' and i.type = '"+StageType.Sales.getType()+"' and s.itemType = '"+ItemType.Finish_Goods.getType()+"' and i.SellerCustomerName = '"+getCmbCustomerName()+"' "+
						"order by i.type,i.Invoice";

			}else {
				sql = "select i.Invoice,i.date,i.SellerCustomerName,i.Mobileno,i.ApprovedBy,i.PromissDate,i.totalAmount,i.vat,i.vatAmount,i.discount,i.percentDiscount,i.manualDiscount,i.totalDiscount,i.netAmount,i.paid,i.cash,i.Card,i.due,l.username,s.invoiceNo,s.itemId,s.itemName,s.warrantyDate,s.unit,s.UnitQty,s.qty,freeQty,s.freeItemPrice,s.UnitPrice,s.AveragePrice,s.SalesPrice,s.totalAmount as sdTotalAmount,s.discount as sdDiscount,s.netAmount as sdNetAmount  "+
						"from tbInvoice i  "+
						"join tbStore s   "+
						"on i.Invoice = s.invoiceNo   "+
						"join tblogin l  "+
						"on i.entryby = l.user_Id  "+
						"where i.due>0 and i.date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' and i.type = '"+StageType.Sales.getType()+"' and s.itemType = '"+ItemType.Finish_Goods.getType()+"'  "+
						"order by i.type,i.Invoice";
			}

			map.put("fromDate", getDateFromDate());
			map.put("toDate", getDateToDate());
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

	private void openItemWiseSalesStatement() {
		// TODO Auto-generated method stub



		try {

			map.put("fromDate", getDateFromDate());
			map.put("toDate", getDateToDate());
			String report="";
			String sql = "";


			//if(getRadioSummery()) {
			report = "src/resource/reports/tradingReportFile/ItemWiseSalesReportSummery.jrxml";

			/*}else {
					report = "src/resource/reports/tradingReportFile/ItemWiseSalesReportDetails.jrxml";		
				}*/

			if(getCheckSearchBy()) {
				String idList="'0'";
				for(int i=0;i<listMultipleContent.size();i++) {
					if(listMultipleContent.get(i).getIsSelect()) idList +=",'"+listMultipleContent.get(i).getContentId()+"'";
				}
				sql = "select c.categoryname,s.itemId,s.itemName,s.unit,sum(s.UnitQty) unitQty,sum(s.qty) qty from tbStore s "+
						"join tbItem i "+
						"on s.itemId = i.id "+
						"join tbCategory c "+
						"on i.CategoryId = c.id where s.type = '3' and s.itemId in ("+idList+") and s.Date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by c.categoryname,s.itemId,s.itemName,s.unit order by qty desc";
			}else if(getCheckItemNameAll()) {
				sql = "select c.categoryname,s.itemId,s.itemName,s.unit,sum(s.UnitQty) unitQty,sum(s.qty) qty from tbStore s "+
						"join tbItem i "+
						"on s.itemId = i.id "+
						"join tbCategory c "+
						"on i.CategoryId = c.id where s.type = '3' and s.Date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by c.categoryname,s.itemId,s.itemName,s.unit order by qty desc";
			}else {
				if(!getCmbItemName().isEmpty()) {
					sql = "select c.categoryname,s.itemId,s.itemName,s.unit,sum(s.UnitQty) unitQty,sum(s.qty) qty from tbStore s "+
							"join tbItem i "+
							"on s.itemId = i.id "+
							"join tbCategory c "+
							"on i.CategoryId = c.id where s.type = '3' and where s.ItemName = '"+getCmbItemName()+"' and s.Date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by c.categoryname,s.itemId,s.itemName,s.unit order by qty desc";
				}else if(getCheckCategoryAll()) {
					sql = "select c.categoryname,s.itemId,s.itemName,s.unit,sum(s.UnitQty) unitQty,sum(s.qty) qty from tbStore s "+
							"join tbItem i "+
							"on s.itemId = i.id "+
							"join tbCategory c "+
							"on i.CategoryId = c.id where s.type = '3' and s.Date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by c.categoryname,s.itemId,s.itemName,s.unit order by qty desc";
				}else {
					if(!getCmbCategoryName().isEmpty()) {
						sql = "select c.categoryname,s.itemId,s.itemName,s.unit,sum(s.UnitQty) unitQty,sum(s.qty) qty from tbStore s "+
								"join tbItem i "+
								"on s.itemId = i.id "+
								"join tbCategory c "+
								"on i.CategoryId = c.id where s.type = '3' and c.categoryName = '"+getCmbCategoryName()+"' and s.Date between '"+getDateFromDate()+"' and '"+getDateToDate()+"' group by c.categoryname,s.itemId,s.itemName,s.unit order by qty desc";
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Any Category/Item Name...","Please Select a valid Category/Item Name");
						cmbCategoryName.requestFocus();
					}
				}
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






	private void openAllItemStockPosition() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/AllItemStockReportSummery.jrxml";

			}else {
				report = "src/resource/reports/tradingReportFile/AllItemStockReportDetails.jrxml";		
			}

			if(getCheckSearchBy()) {
				String idList="'0'";
				for(int i=0;i<listMultipleContent.size();i++) {
					if(listMultipleContent.get(i).getIsSelect()) idList +=",'"+listMultipleContent.get(i).getContentId()+"'";
				}
				sql = "select *from FnCurrentStockReport() where itemid in ("+idList+") order by catagoryName,itemname";
			}else if(getCheckItemNameAll()) {
				sql = "select *from FnCurrentStockReport()  order by catagoryName,itemname";
			}else {
				if(!getCmbItemName().isEmpty()) {
					sql = "select * from FnCurrentStockReport() where itemname = '"+getCmbItemName()+"' order by catagoryName,itemname";
				}else if(getCheckCategoryAll()) {
					sql = "select * from FnCurrentStockReport()  order by catagoryName,itemname";
				}else {
					if(!getCmbCategoryName().isEmpty()) {
						sql = "select * from FnCurrentStockReport()  where  catagoryName = '"+getCmbCategoryName()+"' order by catagoryName,itemname";
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Any Category/Item Name...","Please Select a valid Category/Item Name");
						cmbCategoryName.requestFocus();
					}
				}
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

	private void openAllItemStockPositionByDate() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/AllItemReportWithValueSummery.jrxml";

			}else {
				report = "src/resource/reports/tradingReportFile/AllItemReportWithValue.jrxml";		
			}

			if(getCheckSearchBy()) {
				String idList="'0'";
				for(int i=0;i<listMultipleContent.size();i++) {
					if(listMultipleContent.get(i).getIsSelect()) idList +=",'"+listMultipleContent.get(i).getContentId()+"'";
				}
				sql = "select * from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') where ProductId in ("+idList+") order by categroyName,productname";
			}else if(getCheckItemNameAll()) {
				sql = "select * from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') order by categroyName,productname";
			}else {
				if(!getCmbItemName().isEmpty()) {
					sql = "select *from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') where productName = '"+getCmbItemName()+"' order by categroyName,productname";
				}else if(getCheckCategoryAll()) {
					sql = "select * from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') order by categroyName,productname";
				}else {
					if(!getCmbCategoryName().isEmpty()) {
						sql = "select *from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') where categroyName = '"+getCmbCategoryName()+"' order by categroyName,productname";
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Any Category/Item Name...","Please Select a valid Category/Item Name");
						cmbCategoryName.requestFocus();
					}
				}
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

	private void openRawMaterialsStockPositionByDate() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";

			if(getRadioSummery()) {
				report = "src/resource/reports/tradingReportFile/AllItemReportWithValueSummery.jrxml";

			}else {
				report = "src/resource/reports/tradingReportFile/AllItemReportWithValue.jrxml";		
			}

			if(getCheckSearchBy()) {
				String idList="'0'";
				for(int i=0;i<listMultipleContent.size();i++) {
					if(listMultipleContent.get(i).getIsSelect()) idList +=",'"+listMultipleContent.get(i).getContentId()+"'";
				}
				sql = "select * from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') where ProductId in ("+idList+") and type='"+rawMaterials+"' order by categroyName,productname";
			}else if(getCheckItemNameAll()) {
				sql = "select * from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') where type='"+rawMaterials+"' order by categroyName,productname";
			}else {
				if(!getCmbItemName().isEmpty()) {
					sql = "select *from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') where productName = '"+getCmbRawMaterials()+"' and type='"+rawMaterials+"' order by categroyName,productname";
				}else if(getCheckCategoryAll()) {
					sql = "select * from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') where type='"+rawMaterials+"' order by categroyName,productname";
				}else {
					if(!getCmbCategoryName().isEmpty()) {
						sql = "select *from FnAllItemStockPosition('"+getDateFromDate()+"','"+getDateToDate()+"') where categroyName = '"+getCmbCategoryName()+"' and type='"+rawMaterials+"' order by categroyName,productname";
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Any Category/Item Name...","Please Select a valid Category/Item Name");
						cmbCategoryName.requestFocus();
					}
				}
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

	private void openReorderItemList() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";


			report = "src/resource/reports/tradingReportFile/ReorderItemList.jrxml";


			if(getCheckCategoryAll() || getCmbCategoryName().isEmpty()) {

				if(!getTxtReorderQty().isEmpty()) {
					sql = "select c.categoryName,i.projectedItemName,i.unit,dbo.presentStock(i.id) as presentStock,"+getTxtReorderQty()+" as ReorderQty from tbItem i\r\n" + 
							"join tbcategory c \r\n" + 
							"on i.CategoryId = c.id where dbo.presentStock(i.id)<"+getTxtReorderQty()+" order by c.categoryName,i.projectedItemName ";
				}else {
					sql = "select c.categoryName,i.projectedItemName,i.unit,dbo.presentStock(i.id) as presentStock,i.ReorderQty from tbItem i\r\n" + 
							"join tbcategory c \r\n" + 
							"on i.CategoryId = c.id where dbo.presentStock(i.id)<i.ReorderQty order by c.categoryName,i.projectedItemName ";
				}

			}else {	

				if(!getTxtReorderQty().isEmpty()) {
					sql = "select c.categoryName,i.projectedItemName,i.unit,dbo.presentStock(i.id) as presentStock,"+getTxtReorderQty()+" as ReorderQty from tbItem i\r\n" + 
							"join tbcategory c \r\n" + 
							"on i.CategoryId = c.id where dbo.presentStock(i.id)<"+getTxtReorderQty()+" and c.categoryName = '"+getCmbCategoryName()+"' order by c.categoryName,i.projectedItemName ";
				}else {
					sql = "select c.categoryName,i.projectedItemName,i.unit,dbo.presentStock(i.id) as presentStock,i.ReorderQty from tbItem i\r\n" + 
							"join tbcategory c \r\n" + 
							"on i.CategoryId = c.id where dbo.presentStock(i.id)<i.ReorderQty and c.categoryName = '"+getCmbCategoryName()+"' order by c.categoryName,i.projectedItemName ";
				}


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

	private void openImeiSerialInformation() {
		// TODO Auto-generated method stub
		try {
			String report="";
			String sql = "";


			report = "src/resource/reports/tradingReportFile/ImeiSerialInformaion.jrxml";


			if(getCheckCategoryAll() ||  getCmbItemName().isEmpty()) {

				sql = "select i.projectedItemName,im.imei,isnull(im.purchaseInvoiceNo,'')as purchaseInvoiceNo,\r\n" + 
						"isnull(im.purchaseReturnInvoiceNo,'') as purchaseReturnInvoiceNo,\r\n" + 
						"isnull(im.SalesInvoiceNo,'') as SalesInvoiceNo,\r\n" + 
						"isnull(im.SalesReturnInvoiceNo,'') as SalesReturnInvoiceNo,\r\n" + 
						"isnull(im.inType,'') as inType,\r\n" + 
						"isnull(im.outType,'') as outType,im.isAvailable from tbIMEI im\r\n" + 
						"join tbItem i\r\n" + 
						"on im.itemID = i.id order by i.projectedItemName";

			}else {	

				if(!getCmbItemName().isEmpty()) {
					sql = "select i.projectedItemName,im.imei,isnull(im.purchaseInvoiceNo,'')as purchaseInvoiceNo,\r\n" + 
							"isnull(im.purchaseReturnInvoiceNo,'') as purchaseReturnInvoiceNo,\r\n" + 
							"isnull(im.SalesInvoiceNo,'') as SalesInvoiceNo,\r\n" + 
							"isnull(im.SalesReturnInvoiceNo,'') as SalesReturnInvoiceNo,\r\n" + 
							"isnull(im.inType,'') as inType,\r\n" + 
							"isnull(im.outType,'') as outType,im.isAvailable from tbIMEI im\r\n" + 
							"join tbItem i\r\n" + 
							"on im.itemID = i.id  where i.projectedItemName = '"+getCmbItemName()+"' order by i.projectedItemName";
				}



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


	private void multipleContentAddInList(ContentInfo contentInfo) {
		// TODO Auto-generated method stub
		int i;
		for(i=0;i<listMultipleContent.size();i++) {
			if(listMultipleContent.get(i).getContentId().equals(contentInfo.getContentId()) && listMultipleContent.get(i).getContentType().equals(contentInfo.getContentType())) {
				listMultipleContent.get(i).setIsSelect(true);
				break;
			}else if(!listMultipleContent.get(i).getIsSelect()) {
				listMultipleContent.remove(i);
			}
		}
		if(i == listMultipleContent.size()) {
			listMultipleContent.add(contentInfo);
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

	private String getSupplierId(String supplierName) {
		// TODO Auto-generated method stub
		try {
			sql = "select id from tbSupplier where SupplierName = '"+supplierName+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("id");
			} 
		}catch(Exception e) {
			e.printStackTrace();
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

	private String getCategoryId(String categoryName) {
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
	}

	public void CustomerLoad() {
		try {
			sql = "select CustomerName from tbCustomer";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbCustomerName.data.clear();
			while (rs.next()) {
				cmbCustomerName.data.add(rs.getString("CustomerName"));
			}

			sql = "select SellerCustomerName from tbInvoice where SellerCustomerID = '0' group by SellerCustomerName order by SellerCustomerName";
			rs = databaseHandler.execQuery(sql);
			while (rs.next()) {
				cmbCustomerName.data.add(rs.getString("SellerCustomerName"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void SupplierLoad() {
		try {
			sql = "select supplierName from tbSupplier";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbSupplierName.data.clear();
			while (rs.next()) {
				cmbSupplierName.data.add(rs.getString("supplierName"));
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

	public void rawMaterialsLoad() {
		try {
			sql = "select projectedItemName from tbItem where type = '"+rawMaterials+"' order by projectedItemName";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbRawMaterials.data.clear();
			while (rs.next()) {
				cmbRawMaterials.data.add(rs.getString("projectedItemName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ItemNameLoadInCmbContent() {
		try {
			sql = "select projectedItemName from tbItem order by projectedItemName";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbContentName.data.clear();
			while (rs.next()) {
				cmbContentName.data.add(rs.getString("projectedItemName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void rawMaterialsLoadInCmbContent() {
		try {
			sql = "select projectedItemName from tbItem where type='"+rawMaterials+"' order by projectedItemName ";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbContentName.data.clear();
			while (rs.next()) {
				cmbContentName.data.add(rs.getString("projectedItemName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void itemLoadByCategoryName(String categoryId) {
		try {

			if(categoryId.equals("0")) {
				sql = "select i.id,CategoryId,c.categoryName,i.unit,projectedItemName,salePrice,case when isActive = 1 then 'Active' else 'Deactive' end as activeStatus  from tbItem i\r\n" + 
						"join tbCategory c\r\n" + 
						"on c.id = i.CategoryId where i.type = '1' order by cast(substring(projectedItemName,0,isnull (charindex('-',projectedItemName),DATALENGTH(projectedItemName))) as int)";
			}else {
				sql = "select i.id,CategoryId,c.categoryName,i.unit,projectedItemName,salePrice,case when isActive = 1 then 'Active' else 'Deactive' end as activeStatus  from tbItem i\r\n" + 
						"join tbCategory c\r\n" + 
						"on c.id = i.CategoryId where i.type = '1' and i.CategoryId= '"+categoryId+"' order by cast(substring(projectedItemName,0,isnull (charindex('-',projectedItemName),DATALENGTH(projectedItemName)))as int)";
			}
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				multipleContentAddInList(new ContentInfo(rs.getString("id"),rs.getString("projectedItemName"),"item",false));
			}
			while(rs.next()) {
				listMultipleContent.add( new ContentInfo(rs.getString("id"),rs.getString("projectedItemName"),"item",false));
			}
			tableMultipleContentList.setItems(listMultipleContent);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbCustomerName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCustomerName);
		});

		cmbSupplierName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbSupplierName);
		});

		cmbCategoryName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCategoryName);
		});

		cmbItemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbItemName);
		});

		cmbRawMaterials.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbRawMaterials);
		});

		txtReorderQty.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtReorderQty);
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

		cmbCategoryName.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					itemLoadByCategoryName(getCategoryId(newValue));
				}
			}    
		});

		checkSelectAll.setOnAction(e->{
			if(checkSelectAll.isSelected()) {
				for(int i=0;i<listMultipleContent.size();i++) {
					listMultipleContent.get(i).setIsSelect(true);
				}
			}else {
				for(int i=0;i<listMultipleContent.size();i++) {
					listMultipleContent.get(i).setIsSelect(false);
				}
			}
			tableMultipleContentList.setItems(listMultipleContent);
		});

		checkCustomerAll.setOnAction(e->{
			if(checkCustomerAll.isSelected()) {

				setCmbCustomerName("");		
				cmbCustomerName.setDisable(true);

			}else {

				//setCmbCustomerName("");
				cmbCustomerName.setDisable(false);

			}
		});

		checkSupplierAll.setOnAction(e->{
			if(checkSupplierAll.isSelected()) {

				setCmbSupplierName("");			
				cmbSupplierName.setDisable(true);

			}else {

				//setCmbSupplierName("");
				cmbSupplierName.setDisable(false);

			}
		});

		checkCategoryAll.setOnAction(e->{
			if(checkCategoryAll.isSelected()) {

				setCmbItemName("");
				setCmbCategoryName("");
				cmbItemName.setDisable(true);
				cmbCategoryName.setDisable(true);
			}else {

				//setCmbItemName("");
				//setCmbCategoryName("");
				cmbItemName.setDisable(false);
				cmbCategoryName.setDisable(false);
			}
		});

		checkItemNameAll.setOnAction(e->{
			if(checkItemNameAll.isSelected()) {

				setCmbItemName("");			
				cmbItemName.setDisable(true);

			}else {

				//setCmbItemName("");
				cmbItemName.setDisable(false);

			}
		});

		checkRawMaterialsAll.setOnAction(e->{
			if(checkRawMaterialsAll.isSelected()) {

				setCmbRawMaterials("");			
				cmbRawMaterials.setDisable(true);

			}else {

				//setCmbItemName("");
				cmbRawMaterials.setDisable(false);

			}
		});


		radioPurchaseStatement.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);



			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);


			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);


			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);

			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioPurchaseStatementSupplierWise.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(false);
			checkCustomerAll.setDisable(true);


			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbSupplierName.setDisable(false);
			cmbCustomerName.setDisable(true);


			setCheckCustomerAll(false);
			setCheckSupplierAll(true);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);


			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);

			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioSalesStatement.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);


			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);


			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);


			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);

			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioSalesStatementCustomerWise.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(false);


			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(false);


			setCheckCustomerAll(true);
			setCheckSupplierAll(false);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);


			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);

			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioSalesDetails.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(false);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(false);


			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(false);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(false);


			setCheckCustomerAll(true);
			setCheckSupplierAll(false);
			setCheckCategoryAll(false);
			setCheckItemNameAll(true);


			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);

			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioDueSalesStatementDetails.setOnAction(e->{
			checkCategoryAll.setDisable(true);
			checkItemNameAll.setDisable(true);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(false);


			cmbCategoryName.setDisable(true);
			cmbItemName.setDisable(true);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(false);


			setCheckCustomerAll(true);
			setCheckSupplierAll(false);
			setCheckCategoryAll(false);
			setCheckItemNameAll(false);


			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);

			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);
		});

		radioItemWiseSaleStatement.setOnAction(e->{
			checkCategoryAll.setDisable(false);
			checkItemNameAll.setDisable(false);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);


			cmbCategoryName.setDisable(false);
			cmbItemName.setDisable(false);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);


			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(true);
			setCheckItemNameAll(true);

			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);

			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);

			ItemNameLoadInCmbContent();
		});


		radioAllItemStockPosition.setOnAction(e->{
			checkCategoryAll.setDisable(false);
			checkItemNameAll.setDisable(false);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);


			cmbCategoryName.setDisable(false);
			cmbItemName.setDisable(false);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);


			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(true);
			setCheckItemNameAll(true);

			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(true);
			dateToDate.setDisable(true);

			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);

			ItemNameLoadInCmbContent();
		});

		radioAllItemStockPositionByDate.setOnAction(e->{
			checkCategoryAll.setDisable(false);
			checkItemNameAll.setDisable(false);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);


			cmbCategoryName.setDisable(false);
			cmbItemName.setDisable(false);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);


			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(true);
			setCheckItemNameAll(true);


			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);

			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);

			ItemNameLoadInCmbContent();
		});

		radioRawMaterialsStockPositionByDate.setOnAction(e->{
			checkCategoryAll.setDisable(false);
			checkItemNameAll.setDisable(false);
			checkRawMaterialsAll.setDisable(false);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);



			cmbCategoryName.setDisable(false);
			cmbItemName.setDisable(false);
			cmbRawMaterials.setDisable(false);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);


			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(true);
			setCheckItemNameAll(true);
			setCheckRawMaterialsAll(true);


			setTxtReorderQty("");
			txtReorderQty.setDisable(true);


			dateFromDate.setDisable(false);
			dateToDate.setDisable(false);

			radioSummery.setDisable(false);
			radiokDetails.setDisable(false);

			ItemNameLoadInCmbContent();
		});

		radioReorderItemList.setOnAction(e->{
			checkCategoryAll.setDisable(false);
			checkItemNameAll.setDisable(false);
			checkSupplierAll.setDisable(true);
			checkCustomerAll.setDisable(true);


			cmbCategoryName.setDisable(false);
			cmbItemName.setDisable(false);
			cmbSupplierName.setDisable(true);
			cmbCustomerName.setDisable(true);


			setCheckCustomerAll(false);
			setCheckSupplierAll(false);
			setCheckCategoryAll(true);
			setCheckItemNameAll(true);


			setTxtReorderQty("");
			txtReorderQty.setDisable(false);

			dateFromDate.setDisable(true);
			dateToDate.setDisable(true);

			radioSummery.setDisable(true);
			radiokDetails.setDisable(true);
		});



	}


	private void setCmpData() {
		// TODO Auto-generated method stub

		map.put("orgName",SessionBeam.getOrgName());
		map.put("orgAddress",SessionBeam.getOrgAddress());
		map.put("orgContact",SessionBeam.getOrgContact());


		dateFromDate.setConverter(converter);
		dateFromDate.setValue(LocalDate.now());

		dateToDate.setConverter(converter);
		dateToDate.setValue(LocalDate.now());

		radioSummery.setToggleGroup(groupSummeryDetails);
		radiokDetails.setToggleGroup(groupSummeryDetails);
		radioSummery.setSelected(true);


		//groupReportSelect.getChildren().addAll(checkSalesStatement,checkSalesStatementSupplierWise);
		radioPurchaseStatement.setToggleGroup(groupReportSelect);
		radioPurchaseStatementSupplierWise.setToggleGroup(groupReportSelect);
		radioSalesStatement.setToggleGroup(groupReportSelect);
		radioSalesStatementCustomerWise.setToggleGroup(groupReportSelect);
		radioSalesDetails.setToggleGroup(groupReportSelect);
		radioDueSalesStatementDetails.setToggleGroup(groupReportSelect);

		radioItemWiseSaleStatement.setToggleGroup(groupReportSelect);

		radioAllItemStockPosition.setToggleGroup(groupReportSelect);
		radioAllItemStockPositionByDate.setToggleGroup(groupReportSelect);
		radioRawMaterialsStockPositionByDate.setToggleGroup(groupReportSelect);
		radioReorderItemList.setToggleGroup(groupReportSelect);


		radioSalesStatement.setSelected(true);

		txtReorderQty.setTextFormatter(NumberField.getIntegerFormate());

		tableMultipleContentList.setColumnResizePolicy(tableMultipleContentList.CONSTRAINED_RESIZE_POLICY);
		contentIdCol.setCellValueFactory(new PropertyValueFactory("contentId"));
		contentNameCol.setCellValueFactory(new PropertyValueFactory("contentName"));
		checkSelectCol.setCellValueFactory(new PropertyValueFactory("checkSelect"));

		checkSelectAll = new CheckBox();
		checkSelectCol.setGraphic(checkSelectAll);
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
		cmbCustomerName.setPrefHeight(28);
		cmbCustomerName.setPrefWidth(295);
		vBoxCustomerName.getChildren().add(cmbCustomerName);

		vBoxSupplierName.getChildren().clear();
		cmbSupplierName.setPrefHeight(28);
		cmbSupplierName.setPrefWidth(295);
		vBoxSupplierName.getChildren().add(cmbSupplierName);

		vBoxCategoryName.getChildren().clear();
		cmbCategoryName.setPrefHeight(28);
		cmbCategoryName.setPrefWidth(295);
		vBoxCategoryName.getChildren().add(cmbCategoryName);

		vBoxItemName.getChildren().clear();
		cmbItemName.setPrefHeight(28);
		cmbItemName.setPrefWidth(295);
		vBoxItemName.getChildren().add(cmbItemName);

		vBoxRawMaterials.getChildren().clear();
		cmbRawMaterials.setPrefHeight(28);
		cmbRawMaterials.setPrefWidth(295);
		vBoxRawMaterials.getChildren().add(cmbRawMaterials);

		hBoxContentName.getChildren().remove(0);
		cmbContentName.setPrefHeight(28);
		cmbContentName.setPrefWidth(366);
		hBoxContentName.getChildren().add(0,cmbContentName);



	}

	public static class ContentInfo{


		private SimpleStringProperty contentId;
		private SimpleStringProperty contentName;
		private SimpleStringProperty contentType;
		private CheckBox checkSelect;

		public ContentInfo(String contentId,String contentName,String contentType,boolean isSelect) {
			this.contentId = new SimpleStringProperty(contentId);
			this.contentType = new SimpleStringProperty(contentType);
			this.contentName = new SimpleStringProperty(contentName);
			this.checkSelect = new CheckBox();
			this.checkSelect.setSelected(isSelect);

		}

		public String getContentId() {
			return contentId.get();
		}
		public void setContentId(String contentId) {
			this.contentId = new SimpleStringProperty(contentId);
		}
		public String getContentName() {
			return contentName.get();
		}
		public void setContentName(String contentName) {
			this.contentName = new SimpleStringProperty(contentName);
		}

		public String getContentType() {
			return contentType.get();
		}

		public void setContentType(String contentType) {
			this.contentType = new SimpleStringProperty(contentType);
		}
		public boolean getIsSelect() {
			return checkSelect.isSelected();
		}
		public void setIsSelect(boolean isSelect) {
			this.checkSelect.setSelected(isSelect);
		}

		public CheckBox getCheckSelect() {
			return this.checkSelect;
		}



	}

	public boolean getRadioPurchaseStatement() {
		return radioPurchaseStatement.isSelected();
	}

	public void setRadioPurchaseStatement(boolean radioPurchaseStatement) {
		this.radioPurchaseStatement.setSelected(radioPurchaseStatement);
	}

	public boolean getRadioPurchaseStatementSupplierWise() {
		return radioPurchaseStatementSupplierWise.isSelected();
	}

	public void setRadioPurchaseStatementSupplierWise(boolean radioPurchaseStatementSupplierWise) {
		this.radioPurchaseStatementSupplierWise.setSelected(radioPurchaseStatementSupplierWise);
	}

	public boolean getRadioSalesStatement() {
		return radioSalesStatement.isSelected();
	}

	public void setRadioSalesStatement(boolean radioSalesStatement) {
		this.radioSalesStatement.setSelected(radioSalesStatement);
	}

	public boolean getRadioSalesStatementCustomerWise() {
		return radioSalesStatementCustomerWise.isSelected();
	}

	public void setRadioSalesStatementCustomerWise(boolean radioSalesStatementCustomerWise) {
		this.radioSalesStatementCustomerWise.setSelected(radioSalesStatementCustomerWise);
	}

	public boolean getRadioItemWiseSaleStatement() {
		return radioItemWiseSaleStatement.isSelected();
	}

	public boolean getRadioSalesDetails() {
		return radioSalesDetails.isSelected();
	}

	public void setRadioSalesDetails(boolean radioSalesDetails) {
		this.radioSalesDetails.setSelected(radioSalesDetails);
	}

	public boolean getRadioDueSalesStatementDetails() {
		return radioDueSalesStatementDetails.isSelected();
	}

	public void setRadioDueSalesStatementDetails(boolean radioDueSalesStatementDetails) {
		this.radioDueSalesStatementDetails.setSelected(radioDueSalesStatementDetails);
	}

	public void setRadioItemWiseSaleStatement(boolean radioItemWiseSaleStatement) {
		this.radioItemWiseSaleStatement.setSelected(radioItemWiseSaleStatement);
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

	public boolean getRadioAllItemStockPosition() {
		return radioAllItemStockPosition.isSelected();
	}

	public void setRadioAllItemStockPosition(boolean RadioAllItemStockPosition) {
		this.radioAllItemStockPosition.setSelected(RadioAllItemStockPosition);
	}

	public boolean getRadioAllItemStockPositionByDate() {
		return radioAllItemStockPositionByDate.isSelected();
	}

	public void setRadioAllItemStockPositionWithValue(boolean RadioAllItemStockPositionWithValue) {
		this.radioAllItemStockPositionByDate.setSelected(RadioAllItemStockPositionWithValue);
	}

	public boolean getRadioRawMaterialsStockPositionByDate() {
		return radioRawMaterialsStockPositionByDate.isSelected();
	}

	public void setRadioRawMaterialsStockPositionByDate(boolean radioRawMaterialsStockPositionByDate) {
		this.radioRawMaterialsStockPositionByDate.setSelected(radioRawMaterialsStockPositionByDate);
	}

	public boolean getRadioReorderItemList() {
		return radioReorderItemList.isSelected();
	}

	public void setRadioReorderItemList(boolean radioReorderItemList) {
		this.radioReorderItemList.setSelected(radioReorderItemList);
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


	public boolean getCheckCustomerAll() {
		return checkCustomerAll.isSelected();
	}

	public void setCheckCustomerAll(boolean checkCustomerAll) {
		this.checkCustomerAll.setSelected(checkCustomerAll);
	}

	public boolean getCheckSupplierAll() {
		return checkSupplierAll.isSelected();
	}

	public void setCheckSupplierAll(boolean checkSupplierAll) {
		this.checkSupplierAll.setSelected(checkSupplierAll);
	}

	public boolean getCheckCategoryAll() {
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
	}

	public boolean getCheckRawMaterialsAll() {
		return checkRawMaterialsAll.isSelected();
	}

	public void setCheckRawMaterialsAll(boolean checkRawMaterialsAll) {
		this.checkRawMaterialsAll.setSelected(checkRawMaterialsAll);
	}

	public boolean getCheckSearchBy() {
		return checkSearchBy.isSelected();
	}

	public void setCheckSearchBy(boolean searchBy) {
		this.checkSearchBy.setSelected(searchBy);
	}



	public String getCmbCustomerName() {
		if(cmbCustomerName.getValue() != null)
			return cmbCustomerName.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbCustomerName(String cmbCustomerName) {
		if(cmbCustomerName.equals("")) 
			this.cmbCustomerName.setValue(null);
		this.cmbCustomerName.getEditor().setText(cmbCustomerName);
	}


	public String getCmbSupplierName() {
		if(cmbSupplierName.getValue() != null)
			return cmbSupplierName.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbSupplierName(String cmbSupplierName) {
		if(cmbSupplierName.equals("")) 
			this.cmbSupplierName.setValue(null);
		this.cmbSupplierName.getEditor().setText(cmbSupplierName);
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

	public String getCmbContentName() {
		if(cmbContentName.getValue() != null)
			return cmbContentName.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbContentName(String cmbContentName) {
		if(cmbContentName.equals("")) 
			this.cmbContentName.setValue(null);
		this.cmbContentName.getEditor().setText(cmbContentName);
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

	public String getCmbRawMaterials() {
		if(cmbRawMaterials.getValue() != null)
			return cmbRawMaterials.getValue().toString().trim();
		else
			return "";
	}


	public void setCmbRawMaterials(String cmbRawMaterials) {
		if(cmbRawMaterials.equals("")) 
			this.cmbRawMaterials.setValue(null);
		this.cmbRawMaterials.getEditor().setText(cmbRawMaterials);
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

	public String getTxtReorderQty() {
		return txtReorderQty.getText().trim();
	}


	public void setTxtReorderQty(String txtReorderQty) {
		this.txtReorderQty.setText(txtReorderQty);
	}






}

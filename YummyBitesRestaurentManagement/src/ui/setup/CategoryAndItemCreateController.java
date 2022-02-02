package ui.setup;

import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.joda.time.chrono.IslamicChronology;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
import shareClasses.ItemType;
import shareClasses.LoadedInfo;
import shareClasses.Notification;
import shareClasses.NumberField;
import shareClasses.SessionBeam;
import shareClasses.LoadedInfo.CategoryInfo;





public class CategoryAndItemCreateController implements Initializable{

	@FXML
	TextField txtCategoryId;
	@FXML
	TextField txtCategoryName;
	@FXML
	TextField txtItemId;
	@FXML
	TextField txtUnitQty;
	@FXML
	TextField txtPurchasePrice;
	@FXML
	TextField txtSalesPrice;
	@FXML
	TextField txtOpeningQty;
	@FXML
	TextField txtReorderQty;
	@FXML
	TextField txtStock;
	@FXML
	TextField txtProjectedName;
	@FXML
	TextArea txtDescription;
	@FXML
	TextField txtPackageId;
	@FXML
	TextField txtPackegeName;
	@FXML
	TextField txtPrice;
	@FXML
	TextField txtDiscount;
	@FXML
	TextField txtQty;
	@FXML
	TextField txtPackagePrice;
	@FXML
	TextField txtTotalPackagePrice;
	@FXML
	TextField txtRawMaterialsQuantity;


	@FXML
	Button btnCategoryAdd;
	@FXML
	Button btnCategoryEdit;
	@FXML
	Button btnCategoryRefresh;

	@FXML
	Button btnItemSave;
	@FXML
	Button btnItemEdit;
	@FXML
	Button btnItemRefresh;
	@FXML
	Button btnFind;
	@FXML
	Button btnItemCodeAdd;
	@FXML
	Button btnPackageItemAdd;
	@FXML
	Button btnUnitAdd;
	@FXML
	Button btnPrintItemList;

	@FXML
	Button btnPackageSave;
	@FXML
	Button btnPackageEdit;
	@FXML
	Button btnPackageRefresh;
	@FXML
	Button btnRawMaterialsAdd;

	/*@FXML
	CheckBox checkImei ;*/

	@FXML
	ComboBox cmbCreatedUnit;
	@FXML
	ComboBox cmbStatus;
	@FXML
	ComboBox cmbItemType;
	@FXML
	ComboBox cmbPackageActiveStatus;
	@FXML
	ComboBox cmbRawMaterialsUnit;

	FxComboBox cmbParentCategory = new FxComboBox<>();
	FxComboBox cmbItemCode = new FxComboBox<>();
	FxComboBox cmbItemName = new FxComboBox<>();
	FxComboBox cmbCategory = new FxComboBox<>();
	FxComboBox cmbBrand = new FxComboBox<>();
	FxComboBox cmbUnit = new FxComboBox<>();
	FxComboBox cmbFind = new FxComboBox<>();
	FxComboBox cmbPackageItem = new FxComboBox<>();
	FxComboBox cmbPackageCategory = new FxComboBox<>();

	FxComboBox cmbRawMaterialsItemName = new FxComboBox<>();
	FxComboBox cmbRawMaterialsName = new FxComboBox<>();

	@FXML
	TreeView<String> treeCategory;	
	TreeItem<String> rootNode = new TreeItem<>("All Items Category");

	@FXML
	VBox vBoxParentCategory;
	@FXML
	HBox hBoxItemCode;
	@FXML
	VBox vBoxItemName;
	@FXML
	VBox vBoxCategory;
	@FXML
	VBox vBoxBrand;
	@FXML
	HBox HBoxUnit;
	@FXML
	VBox vBoxFind;
	@FXML
	VBox vBoxPackageCatagory;
	@FXML
	VBox vBoxPackageItemName;
	@FXML
	VBox vBoxRawMaterials;
	@FXML
	HBox hBoxActiveStatus;


	@FXML
	private TableView<ItemInfo> tableItemList;

	ObservableList<ItemInfo> list = FXCollections.observableArrayList();

	@FXML
	private TableColumn<ItemInfo, String> itemIdCol;
	@FXML
	private TableColumn<ItemInfo, String> categoryCol;
	@FXML
	private TableColumn<ItemInfo, String> itemNameCol;
	@FXML
	private TableColumn<ItemInfo, String> priceCol;
	@FXML
	private TableColumn<ItemInfo, String> activeStatusCol;


	@FXML
	private TableView<PackageInfo> tablePackageList;

	ObservableList<PackageInfo> packageList = FXCollections.observableArrayList();

	@FXML
	private TableColumn<PackageInfo, String> packageNameCol;
	@FXML
	private TableColumn<PackageInfo, String> packagePriceCol;


	@FXML
	private TableView<PackageItemInfo> tablePackageItemList;

	ObservableList<PackageItemInfo> packageItemList = FXCollections.observableArrayList();

	@FXML
	private TableColumn<PackageItemInfo, String> packageItemNameCol;
	@FXML
	private TableColumn<PackageItemInfo, String> packageItemPriceCol;
	@FXML
	private TableColumn<PackageItemInfo, String> packageItemDiscountCol;
	@FXML
	private TableColumn<PackageItemInfo, String> packageItemPackagePriceCol;
	@FXML
	private TableColumn<PackageItemInfo, String> packageTotalPriceCol;
	@FXML
	private TableColumn<PackageItemInfo, String> packageItemQuantityCol;



	@FXML
	private TableView<RawMaterialsInfo> tableRawMaterials;

	ObservableList<RawMaterialsInfo> rawMaterialsList = FXCollections.observableArrayList();

	@FXML
	private TableColumn<RawMaterialsInfo, String> rawMaterialsItemNameCol;
	@FXML
	private TableColumn<RawMaterialsInfo, String> rawMaterialsNameCol;
	@FXML
	private TableColumn<RawMaterialsInfo, String> rawMaterialsUnitCol;
	@FXML
	private TableColumn<RawMaterialsInfo, String> rawMaterialsUnitQtyCol;
	@FXML
	private TableColumn<RawMaterialsInfo, String> rawMaterialsQuantityCol;

	MenuItem menuItemRawMaterialsDelete;
	MenuItem menuItemRawMaterialsRefresh;
	MenuItem menuItemPackageItemDelete;
	ContextMenu contextMenuRawMaterials;
	ContextMenu contextMenuPackage;

	private DatabaseHandler databaseHandler;
	private String sql;

	private HashMap map;

	private DecimalFormat df = new DecimalFormat("#0.00");

	
	
	private final int activeType=1;
	private final int deActiveType=0;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		addCmp();
		setCmpData();
		setCmpAction();
		setCmpFocusAction();
		focusMoveAction();
		loadMaxCategoryId();
		loadMaxItemId();
		loadMaxPackageId();
		loadCategoryName();
		loadItemName();
		loadCmbFind();
		loadBrandName();
		loadUnit();
		loadItemInfoTable("");
		categoryTreeLoad();
		btnItemRefresh(null);
		categoryRefreshAction(null);
		packageItemRefreshAction(null);
		rawMaterialsRefreshAction();
	}



	private void setCmpAction() {
		cmbCategory.setOnMouseClicked(e ->{
			concateProjectedItemName(null);
		});
		/*
		 * cmbCustomer.setOnKeyReleased(e ->{ if(e.getCode() == KeyCode.ENTER)
		 * loadBayerByCustomer(); });
		 */

		cmbCategory.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener()  {

					@Override
					public void changed(ObservableValue arg0, Object arg1, Object arg2) {
						// TODO Auto-generated method stub
						concateProjectedItemName(null);
					}
				});

		cmbBrand.setOnMouseClicked(e ->{
			concateProjectedItemName(null);
		});
		/*
		 * cmbCustomer.setOnKeyReleased(e ->{ if(e.getCode() == KeyCode.ENTER)
		 * loadBayerByCustomer(); });
		 */

		cmbBrand.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener()  {

					@Override
					public void changed(ObservableValue arg0, Object arg1, Object arg2) {
						// TODO Auto-generated method stub
						concateProjectedItemName(null);
					}
				});

		cmbItemName.setOnMouseClicked(e ->{
			concateProjectedItemName(null);
		});
		/*
		 * cmbCustomer.setOnKeyReleased(e ->{ if(e.getCode() == KeyCode.ENTER)
		 * loadBayerByCustomer(); });
		 */

		cmbItemName.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener()  {

					@Override
					public void changed(ObservableValue arg0, Object arg1, Object arg2) {
						// TODO Auto-generated method stub
						concateProjectedItemName(null);
					}
				});


		cmbFind.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					btnFindAction();

				}
			}    
		});

		btnItemSave.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			btnItemSaveAction(null); });

		btnCategoryAdd.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			categoryAddAction(null); });

		btnPackageItemAdd.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			packageItemAddAction(null); });

		menuItemPackageItemDelete.setOnAction(e->{
			packageItemDeleteAction(null);
		});
		cmbPackageItem.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV && !getCmbPackageItem().isEmpty()) { // focus lost
				if(isProjectedNameExist(getCmbPackageItem())) {

					priceSetByItemName(getCmbPackageItem());
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select a valid Item Name...");
					cmbItemName.requestFocus();
				}
			}
		});

		txtPackagePrice.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV && !getTxtPackagePrice().isEmpty()) { // focus lost
				if(getTxtPriceDouble()>getTxtPackagePriceDouble()){
					setTxtDiscount(String.valueOf(getTxtPriceDouble()-getTxtPackagePriceDouble()));
				}else{
					setTxtDiscount("0");
				}

			}else{
				setTxtDiscount("0");
			}
		});

		cmbRawMaterialsItemName.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener()  {

					@Override
					public void changed(ObservableValue arg0, Object arg1, Object arg2) {
						// TODO Auto-generated method stub
						loadRawMaterialsTableList();
					}
				});
		cmbRawMaterialsName.focusedProperty().addListener((ov, oldV, newV) -> {

			if (!newV && !getCmbRawMaterialsName().isEmpty()) { // focus lost	

				rawMaterialsUnitLoadByProductName(getCmbRawMaterialsName());
			}
		});

		menuItemRawMaterialsRefresh.setOnAction(e->{
			rawMaterialsRefreshAction();
		});

		menuItemRawMaterialsDelete.setOnAction(e->{
			rawMaterialsDeleteAction();
		});
		/*cmbItemName.setOnKeyReleased(e->{
			concateProjectedItemName(null);
		});*/
	}

	@FXML
	private void btnItemSaveAction(ActionEvent event) {

		if(!LoadedInfo.isItemIdExist(getTxtItemId())) {
			if(itemValidationCheck()) {

				if(confrimationCheck("Are You Sure to Save This Item?")) {

					itemSave();
					LoadedInfo.loadAllMapItemInfo();
				}
			}
		}
	}

	@FXML
	private void btnItemEditAction(ActionEvent event) {
		if(LoadedInfo.isItemIdExist(getTxtItemId())) {
			if(itemValidationCheck()) {
				if(confrimationCheck("Are You Sure To Edit This Item......")) {

					itemEdit();
					LoadedInfo.loadAllMapItemInfo();
				}
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Item Id Not Exist .... Please Select a valid Item For Edit");
			btnItemRefresh.requestFocus();
		}
	}


	@FXML
	private void btnPrintItemListAction(ActionEvent event) {
		try {

			map.put("orgName", SessionBeam.getOrgName());
			map.put("orgAddress", SessionBeam.getOrgAddress());
			map.put("orgContact", SessionBeam.getOrgContact());

			String report = "src/resource/reports/generalReport/ItemList.jrxml";
			//report="LabStatementReport/PurchaseIvoice.jrxml";

			sql = "select (select categoryName from tbCategory c where c.id = i.CategoryId ) as categoryName,projectedItemName,BrandName,Model,PurchasePrice,salePrice,isActive from tbitem i order by categoryName,projectedItemName";
			System.out.println(sql);

			JasperDesign jd = JRXmlLoader.load(report);
			JRDesignQuery jq = new JRDesignQuery();
			jq.setText(sql);
			jd.setQuery(jq);

			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, map, databaseHandler.conn);
			JasperViewer.viewReport(jp, false);

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	@FXML
	private void btnItemRefresh(ActionEvent event) {
		// TODO Auto-generated method stub
		setCmbItemCode("");
		setCmbCategory("");
		setCmbBrand("");

		setCmbItemName("");
		setCmbStatus("Active");
		setTxtProjectedName("");
		setCmbUnit("");
		setTxtUnitQty("");
		setCmbCreatedUnit("");
		setTxtPurchasePrice(0);
		setTxtSalesPrice(0);
		setTxtOpeningQty(0);
		setTxtReorderQty(0);
		setTxtDescription("");
		setTxtStock(0);
		//setCheckImei(false);
		loadMaxItemId();
		loadCategoryName();
		loadBrandName();
		loadItemName();
		loadCmbFind();
		loadItemInfoTable("");
		loadUnit();

	}



	@FXML
	private void btnFindAction(){
		if(!getCmbFind().isEmpty()) {
			if(isProjectedNameExist(getCmbFind())){
				try {
					sql = "select *,(select categoryName from tbCategory c where c.id = i.CategoryId)as categoryName, dbo.presentStock(i.id) as stock from tbItem i where projectedItemName = '"+getCmbFind()+"'";
					ResultSet rs = databaseHandler.execQuery(sql);
					if(rs.next()) {
						setTxtItemId(rs.getString("id"));
						setCmbCategory(rs.getString("categoryName"));
						setCmbBrand(rs.getString("BrandName"));
						setCmbItemName(rs.getString("ItemName"));
						setTxtProjectedName(rs.getString("projectedItemName"));
						setCmbUnit(rs.getString("Unit"));
						setTxtPurchasePrice(rs.getDouble("PurchasePrice"));
						setTxtSalesPrice(rs.getDouble("SalePrice"));
						setTxtOpeningQty(rs.getDouble("OpeningQty"));
						setTxtReorderQty(rs.getDouble("ReorderQty"));
						setTxtDescription(rs.getString("description"));
						setTxtStock(rs.getDouble("Stock"));
						setCmbStatus(rs.getInt("isActive")==1?"Active":"Deactive");
						setCmbItemTypeIndex(rs.getInt("type"));

					}

					sql = "select UnitName,UnitQuantity from tbUnit where itemId = '"+getTxtItemId()+"'";
					rs= databaseHandler.execQuery(sql);
					cmbCreatedUnit.getItems().clear();
					while(rs.next()) {
						cmbCreatedUnit.getItems().add(rs.getString("UnitName")+" ("+rs.getString("UnitQuantity")+")");
					}

				}catch(Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Your Item Name is Invalid...");
				cmbFind.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Please Select A Item Name...");
			cmbFind.requestFocus();
		}
	}



	@FXML
	private void btnUnitAddAction() {
		if(LoadedInfo.isItemIdExist(getTxtItemId())) {
			sql = "insert into tbunit (UnitName,UnitQuantity,itemId,entryTime,entryBy) "
					+ "values('"+getCmbUnit()+"','"+getTxtUnitQty()+"','"+getTxtItemId()+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
			if(databaseHandler.execAction(sql)) {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Unit Save Successfully For This Item...");
				btnItemSave.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","First Save Your Item Then Try For Unit Add...");
			btnItemSave.requestFocus();
		}
	}



	@FXML
	private void categoryAddAction(ActionEvent event) {

		if(!isCategoryidExist()) {
			if(categoryValidationCheck()) {
				if(confrimationCheck("Are you Sure to Save This Category?")) {
					sql= "insert into tbCategory (id,categoryName,type,parentId,isFixed,entryTime,createBy) "
							+ "values('"+getTxtCategoryId()+"','"+getTxtCategoryName()+"','"+getCategoryType(getCmbParentCategory())+"','"+getCategoryId(getCmbParentCategory())+"','0',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";

					System.out.println(sql);
					databaseHandler.execAction(sql);
					new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Category Save Successfully...");
					categoryRefreshAction(null);
					LoadedInfo.loadCategoryInfo();
					LoadedInfo.loadCategoryInfoList();
				}
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","This Category Id Allready Exist \n Please Refresh For New Category Id...");
			btnCategoryRefresh.requestFocus();
		}
	}



	@FXML
	private void categoryEditAction(ActionEvent event) {

		if(isCategoryidExist()) {
			if(categoryValidationCheck()) {
				if(!isHeadFixed()) {
					if(confrimationCheck("Are you Sure To Edit This Category?")) {
						sql = "update tbCategory set categoryName = '"+getTxtCategoryName()+"',parentId='"+getCategoryId(getCmbParentCategory())+"',type='"+getCategoryType(getCmbParentCategory())+"',entryTime = CURRENT_TIMESTAMP,createBy = '"+SessionBeam.getUserId()+"' where id = '"+getTxtCategoryId()+"';";
						databaseHandler.execAction(sql);
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Category Edit Successfully...");
						//haadRefreshAction(null);
						LoadedInfo.loadCategoryInfo();
						LoadedInfo.loadCategoryInfoList();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Category is not Editable, It Is Fixed Head......");
					btnCategoryRefresh.requestFocus();
				}
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","This Category Id is not exist...");
			btnCategoryRefresh.requestFocus();
		}
	}



	@FXML
	private void categoryRefreshAction(ActionEvent event) {
		loadMaxCategoryId();
		loadCategoryName();;
		setCmbParentCategory("");
		setTxtCategoryName("");
		categoryTreeLoad();
	}


	@FXML
	private void packageItemAddAction(ActionEvent event){
		if(isProjectedNameExist(getCmbPackageItem())){
			if(!getTxtPackagePrice().isEmpty()){
				if(getTxtQtyDouble()>0){
					int i;
					for(i=0;i<packageItemList.size();i++){		
						if(LoadedInfo.getItemIdByProjectedName(getCmbPackageItem()).equals(packageItemList.get(i).getItemId())){
							packageItemList.get(i).setPrice(getTxtPriceDouble());
							packageItemList.get(i).setPackagePrice(getTxtPackagePriceDouble());
							packageItemList.get(i).setDiscount(getTxtDiscountDouble());
							packageItemList.get(i).setQuantity(getTxtQtyDouble());
							break;
						}
					}
					if(i == packageItemList.size()){
						packageItemList.add(new PackageItemInfo(LoadedInfo.getItemIdByProjectedName(getCmbPackageItem()), getCmbPackageItem(), getTxtPriceDouble(), getTxtDiscountDouble(), getTxtPackagePriceDouble(), getTxtQtyDouble()));
					}
					
					tablePackageItemList.setItems(packageItemList);
					tablePackageItemList.refresh();
					packagePriceCount();
					cmbPackageItem.requestFocus();
				}else{
					new Notification(Pos.TOP_CENTER, "Information graphic", "Quantity Must more than 0..","Please Enter Item Quantity more than 0...");
					txtQty.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Item Package Price..","Please Enter Item Package Price...");
				txtPackagePrice.requestFocus();
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Item Name...","This Item Name is not exist...");
			cmbPackageItem.requestFocus();
		}
	}

	@FXML
	private void packageItemSaveAction(ActionEvent event){
		if(!LoadedInfo.isPackageExist(getTxtPackageId())) {
			if(packageValidationCheck()) {

				if(confrimationCheck("Are You Sure to Save This Package?")) {

					packageSave();
					LoadedInfo.LoadPackageInfo();
					LoadedInfo.LoadPackageInfoList();
					LoadedInfo.loadPackageListByCategory();
					loadPackageListTable();
				
				}
			}
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Package Id all ready exist..!","This Package Id Allready Exist .... Please Refresh for new Package ID");
			btnPackageRefresh.requestFocus();
		}
	}

	@FXML
	private void packageItemEditAction(ActionEvent event){
		if(LoadedInfo.isPackageExist(getTxtPackageId())) {
			if(packageValidationCheck()) {
				if(confrimationCheck("Are You Sure To Edit This Package......")) {

					packageEdit();
					LoadedInfo.LoadPackageInfo();
					LoadedInfo.LoadPackageInfoList();
					LoadedInfo.loadPackageListByCategory();
					loadPackageListTable();
				}
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Package Id Not Exist .... Please Select a valid Package For Edit");
			tablePackageList.requestFocus();
		}
	}

	@FXML
	private void packageItemDeleteAction(ActionEvent event){
		if(tablePackageItemList.getSelectionModel().getSelectedItem()!= null){
			packageItemList.remove(tablePackageItemList.getSelectionModel().getSelectedItem());
			tablePackageItemList.setItems(packageItemList);
		}else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Item Name...","Please Select any Item Name ...");
			tablePackageItemList.requestFocus();
		}
	}

	@FXML
	private void packageItemRefreshAction(ActionEvent event){
		loadProjectedItemName();
		loadMaxPackageId();
		loadPackageListTable();
		setCmbPackageCategory("");
		setTxtPackegeName("");
		setCmbPackageItem("");
		setTxtPrice("0");
		setTxtPackagePrice("0");
		setTxtDiscount("0");
		setTxtQty("0");
		setTxtTotalPackagePrice("0");
		setCmbPackageActiveStatus("Active");
		packageItemList.clear();
		tablePackageItemList.setItems(packageItemList);

	}

	@FXML
	private void btnRawMaterialsAddAction(ActionEvent event){
		try{

			if(!getCmbRawMaterialsItemName().isEmpty()){
				if(!getCmbRawMaterialsName().isEmpty()){
					if(LoadedInfo.isItemExistByProjectedName(getCmbRawMaterialsItemName())){
						if(LoadedInfo.isItemExistByProjectedName(getCmbRawMaterialsName())){
							if(getTxtRawMaterialsQuantityDouble()>0){
								if(confrimationCheck("Are you sure to Add this Raw Materials?")){
									double quantityPerUnit,quantity,unitQuantity;

									String unit = getCmbRawMaterialsUnit();
									unitQuantity = getTxtRawMaterialsQuantityDouble();
									quantityPerUnit = Double.valueOf(unit.substring(unit.indexOf('(')+1, unit.indexOf(')')));
									quantity = unitQuantity * quantityPerUnit;
									sql = "insert into tbRawMaterials (itemId,rawMaterialsId,unit,unitQuantity,quantity,entryTime,userId) values ('"+LoadedInfo.getItemIdByProjectedName(getCmbRawMaterialsItemName())+"','"+LoadedInfo.getItemIdByProjectedName(getCmbRawMaterialsName())+"','"+unit+"','"+unitQuantity+"','"+quantity+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
									if(databaseHandler.execAction(sql)){
										new Notification(Pos.TOP_CENTER, "Information graphic", "Add Successfully.....!","Raw Materials Add Successfully........");

										loadRawMaterialsTableList();
										cmbRawMaterialsName.requestFocus();
									}
								}

							}else{
								new Notification(Pos.TOP_CENTER, "Information graphic", "Quantity Must be more than 0..!","Please Enter a quantity more than 0.....");
								txtRawMaterialsQuantity.requestFocus();
							}
						}else{
							new Notification(Pos.TOP_CENTER, "Information graphic", "Invalid Item Name..!","Your Select Raw Materials Is Invalid!\nPlease Select a Valid Raw Materials.....");
							cmbRawMaterialsName.requestFocus();
						}
					}else{
						new Notification(Pos.TOP_CENTER, "Information graphic", "Invalid Item Name..!","Your Select Item Name Is Invalid!\nPlease Select a Valid Item Name.....");
						cmbRawMaterialsItemName.requestFocus();
					}
				}else{
					new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Raw Materials Name..!","Please Select Raw-Materials Name.....");
					cmbRawMaterialsName.requestFocus();
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Item Name..!","Please Select Item Name.....");
				cmbRawMaterialsItemName.requestFocus();
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void rawMaterialsDeleteAction(){
		try{
			if(tableRawMaterials.getSelectionModel().getSelectedItem() != null){
				if(confrimationCheck("Are you sure to delete this raw materials")){
					sql = "delete tbRawMaterials where autoId = '"+tableRawMaterials.getSelectionModel().getSelectedItem().getAutoId()+"'";
					if(databaseHandler.execAction(sql)){
						new Notification(Pos.TOP_CENTER, "Information graphic", "Delete Successfully..!","Raw Materials Delete Successfully.....");
						loadRawMaterialsTableList();
					}
				}
			}else{
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select a raw Materials..!","Please Select a raw Materials.....");
				tableRawMaterials.requestFocus();
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void rawMaterialsRefreshAction(){
		loadRawMaterialsItemName();
		loadRawMaterialsName();
		loadRawMaterialsTableList();
		setCmbRawMaterialsItemName("");
		setCmbRawMaterialsName("");
		setTxtRawMaterialsQuantity("");
	}





	@FXML
	private void itemListTableClickAction() {
		if(tableItemList.getSelectionModel().getSelectedItem() != null) {
			TablePosition firstCell = tableItemList.getSelectionModel().getSelectedCells().get(0);
			ItemInfo  temItemInfo= tableItemList.getSelectionModel().getSelectedItem();
			setCmbFind(temItemInfo.getItemName());
			btnFindAction();
		}
	}

	@FXML
	private void rawMaterialsListTableClickAction() {
		if(tableRawMaterials.getSelectionModel().getSelectedItem() != null) {
			TablePosition firstCell = tableRawMaterials.getSelectionModel().getSelectedCells().get(0);
			RawMaterialsInfo  temItemInfo= tableRawMaterials.getSelectionModel().getSelectedItem();
			setCmbRawMaterialsItemName(temItemInfo.getItemName());
			setCmbRawMaterialsName(temItemInfo.getRawMaterialsName());
			setCmbRawMaterialsUnit(temItemInfo.getUnit());
			setTxtRawMaterialsQuantity(String.valueOf(temItemInfo.getUnitQty()));
		}
	}

	@FXML
	private void packageListTableClickAction(){
		if(tablePackageList.getSelectionModel().getSelectedItem() != null){
			PackageInfo temp = tablePackageList.getSelectionModel().getSelectedItem();
			setTxtPackageId(temp.getPackageId());
			setCmbPackageCategory(temp.getCategoryName());
			setTxtPackegeName(temp.getPackageName());
			setCmbPackageActiveStatus(temp.getActiveStatus());
			setTxtTotalPackagePrice(temp.getPrice());
			
			ArrayList<shareClasses.LoadedInfo.PackageItemInfo> tempItemList = LoadedInfo.getPackgeItemInfoList(temp.getPackageId());
			packageItemList.clear();
			for(int i =0;i<tempItemList.size();i++){
				packageItemList.add(new PackageItemInfo(tempItemList.get(i).getItemId(), tempItemList.get(i).getItemName(), tempItemList.get(i).getPrice(), tempItemList.get(i).getDiscount(), tempItemList.get(i).getPackagePrice(), tempItemList.get(i).getQuantity()));
			}
			tablePackageItemList.setItems(packageItemList);
		}
	}
	
	@FXML
	private void packageItemTableClickAction(){
		if(tablePackageItemList.getSelectionModel().getSelectedItem() != null){
			PackageItemInfo tempItem = tablePackageItemList.getSelectionModel().getSelectedItem();
			
			setCmbPackageItem(tempItem.getItemName());
			setTxtPrice(tempItem.getPrice());
			setTxtPackagePrice(tempItem.getPackagePrice());
			setTxtDiscount(tempItem.getDiscount());
			setTxtQty(tempItem.getQuantity());
		}
	}
	private void itemSave() {
		// TODO Auto-generated method stub
		try {
			String sql = "insert into tbItem (id,CategoryId,ItemName,"
					+ "BrandName,SupplierID,projectedItemName,"
					+ "unit,PurchasePrice,salePrice,OpeningQty,"
					+ "ReorderQty,description,isActive,IMEI,type,entryTime,EntryBy) \r\n" + 
					"values('"+getTxtItemId()+"',"
					+ "'"+getCategoryId(getCmbCategory())+"',"
					+ "'"+getCmbItemName()+"',"
					+ "'"+getCmbBrand()+"',"
					+ "'',"
					+ "'"+getTxtProjectedName()+"',"
					+ "'"+getCmbUnit()+"',"
					+ "'"+getTxtPurchasePrice()+"',"
					+ "'"+getTxtSalesPrice()+"',"
					+ "'"+getTxtOpeningQty()+"',"
					+ "'"+getTxtReorderQty()+"',"
					+ "'"+getTxtDescription()+"',"
					+ "'"+(getCmbStatus().equals("Active")?"1":0)+"',"
					+ "'0','"+getCmbItemTypeIndex()+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
			if(databaseHandler.execAction(sql)) {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully..","Item Save Successfully.....");
				cmbCategory.requestFocus();
				sql = "insert into tbunit (UnitName,UnitQuantity,itemId,entryTime,entryBy) "
						+ "values('"+getCmbUnit()+"','1','"+getTxtItemId()+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
				databaseHandler.execAction(sql);
				btnItemRefresh(null);
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Save Can Not Successfull..!","Something went wrong ...\nPlease Try Again.....");
				btnItemSave.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void itemEdit() {
		try {
			String sql = "update tbItem set CategoryId='"+getCategoryId(getCmbCategory())+"',"
					+ "ItemName='"+getCmbItemName()+"',"
					+ "BrandName='"+getCmbBrand()+"',"
					+ "SupplierID='0',"
					+ "projectedItemName='"+getTxtProjectedName()+"',"
					+ "unit='"+getCmbUnit()+"',"
					+ "PurchasePrice='"+getTxtPurchasePrice()+"',"
					+ "salePrice='"+getTxtSalesPrice()+"',"
					+ "OpeningQty='"+getTxtOpeningQty()+"',"
					+ "ReorderQty='"+getTxtReorderQty()+"',"
					+ "description='"+getTxtDescription()+"',"
					+ "isActive ='"+(getCmbStatus().equals("Active")?"1":0)+"',"
					+ "IMEI='0',"
					+ "Type='"+getCmbItemTypeIndex()+"',"
					+ "entryTime=CURRENT_TIMESTAMP,"
					+ "EntryBy='"+SessionBeam.getUserId()+"' where id= '"+getTxtItemId()+"';";

			if(databaseHandler.execAction(sql)) {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Item Edit Successfully...");
				btnItemRefresh.requestFocus();

				sql = "update tbUnit set UnitName = '"+getCmbUnit()+"',entryTime = CURRENT_TIMESTAMP,entryBy= '"+SessionBeam.getUserId()+"' where UnitQuantity = '1' and itemId= '"+getTxtItemId()+"';";
				databaseHandler.execAction(sql);
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	private void packageSave(){
		try{
			sql = "insert into tbPackage (id,CategoryId,PackageName,price,type,ActiveStatus,entryTime,entryby) "
					+ "values('"+getTxtPackageId()+"',"
					+ "'"+LoadedInfo.getCategoryInfo(getCmbPackageCategory()).getCategoryId()+"',"
					+ "'"+getTxtPackegeName()+"',"
					+ "'"+getTxtTotalPackagePrice()+"',"
					+ "'"+LoadedInfo.getCategoryInfo(getCmbPackageCategory()).getType()+"',"
					+ "'"+getCmbPackageActiveStatusInt()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+SessionBeam.getUserId()+"');";
			if(databaseHandler.execAction(sql)){

				sql = "delete tbPackageDetails where packageId='"+getTxtPackageId()+"'";
				databaseHandler.execAction(sql);

				for(int i=0;packageItemList.size()>i;i++){
					PackageItemInfo temp = packageItemList.get(i);
					sql = "insert into tbPackageDetails (packageId,itemId,SalesPrice,discount,quantity,packagePrice,totalPrice,type,entryTime,EntryBy)"
							+ " values('"+getTxtPackageId()+"','"+temp.getItemId()+"','"+temp.getPrice()+"','"+temp.getDiscount()+"','"+temp.getQuantity()+"','"+temp.getPackagePrice()+"','"+temp.getTotalPrice()+"','"+LoadedInfo.getCategoryInfo(getCmbPackageCategory()).getType()+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
					databaseHandler.execAction(sql);

				}
				new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully...","Package Save Successfully...");

			}


		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}

	private void packageEdit(){
		try{
			sql = "update tbPackage set CategoryId ='"+LoadedInfo.getCategoryInfo(getCmbPackageCategory()).getCategoryId()+"',"
					+ "PackageName='"+getTxtPackegeName()+"',price='"+getTxtTotalPackagePrice()+"',"
					+ "type='"+LoadedInfo.getCategoryInfo(getCmbPackageCategory()).getType()+"',"
					+ "ActiveStatus='"+getCmbPackageActiveStatusInt()+"',entryby='"+SessionBeam.getUserId()+"' where id='"+getTxtPackageId()+"'; ";
			if(databaseHandler.execAction(sql)){

				sql = "delete tbPackageDetails where packageId='"+getTxtPackageId()+"'";
				databaseHandler.execAction(sql);

				for(int i=0;packageItemList.size()>i;i++){
					PackageItemInfo temp = packageItemList.get(i);
					sql = "insert into tbPackageDetails (packageId,itemId,SalesPrice,discount,quantity,packagePrice,totalPrice,type,entryTime,EntryBy)"
							+ " values('"+getTxtPackageId()+"','"+temp.getItemId()+"','"+temp.getPrice()+"','"+temp.getDiscount()+"','"+temp.getQuantity()+"','"+temp.getPackagePrice()+"','"+temp.getTotalPrice()+"','"+LoadedInfo.getCategoryInfo(getCmbPackageCategory()).getType()+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
					databaseHandler.execAction(sql);

				}
				new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully...","Package Save Successfully...");

			}


		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}

	@FXML
	private void treeClickAction(MouseEvent mouseEvent) {
		try {
			TreeItem<String >  tempTreeItem= treeCategory.getSelectionModel().getSelectedItem();
			if(tempTreeItem != null) {
				sql = "select id,categoryName,(select categoryName from tbCategory where id= c.parentId) as parentCategoryName from tbCategory c where categoryName = '"+tempTreeItem.getValue()+"'";
				ResultSet rs = databaseHandler.execQuery(sql);
				if(rs.next()) {
					setTxtCategoryId(rs.getString("id"));
					setCmbParentCategory(rs.getString("parentCategoryName"));
					setTxtCategoryName(rs.getString("categoryName"));
				}
				setTxtCategoryName(tempTreeItem.getValue());
				loadItemInfoTableByCategory(getCategoryId(getTxtCategoryName()));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}




	private boolean confrimationCheck(String message) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", message);
	}

	private void packagePriceCount() {
		// TODO Auto-generated method stub
		double total=0;
		for(int i=0;i<packageItemList.size();i++){
			total += packageItemList.get(i).getTotalPrice();
		}
		setTxtTotalPackagePrice(total);
	}
	/*private String getItemId(String itemName) {
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
	}*/
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

	private String getCategoryType(String categoryName) {
		// TODO Auto-generated method stub
		try {
			String sql = "select type from tbCategory where categoryName = '"+categoryName+"';";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				return rs.getString("type");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}

	private boolean itemValidationCheck() {
		if(!getTxtItemId().isEmpty()) {
			if(!getCmbCategory().isEmpty()) {
				if(LoadedInfo.isCategoryExist(getCmbCategory())) {
					if(!getCmbItemName().isEmpty()) {
						//if(!isItemNameExist(getCmbItemName())) {
						if(!getTxtProjectedName().isEmpty()) {
							if(!isProjectedNameExist(getTxtProjectedName(),getTxtItemId())) {
								if(!getCmbUnit().isEmpty()) {
									if(!getTxtPurchasePrice().isEmpty()) {
										if(!getTxtSalesPrice().isEmpty()) {
											if(!getTxtOpeningQty().isEmpty()) {		
												if(!getTxtReorderQty().isEmpty()) {		
													if(getCmbStatus().equals("Active")?true:Double.valueOf(getTxtStock())<=0?true:false) {
														if(getCmbItemTypeIndex()>0?true:false) {
															return true;
														}else {
															new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Item Type!","Please Select Item Type...");
															cmbItemType.requestFocus();
														}
													}else {
														new Notification(Pos.TOP_CENTER, "Warning graphic", "Item Stock Should be 0!","This Item Stock is "+getTxtStock()+".\nStock Should be 0 for Deactive a Item...");
														cmbStatus.requestFocus();
													}	
												}else {
													new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Reorder Quantity...");
													txtReorderQty.requestFocus();
												}		
											}else {
												new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Opening Quantity...");
												txtOpeningQty.requestFocus();
											}

										}else {
											new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Sale Price...");
											txtSalesPrice.requestFocus();
										}

									}else {
										new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Purchase Price...");
										txtPurchasePrice.requestFocus();
									}

								}else {
									new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Unit...");
									cmbUnit.requestFocus();
								}
							}else {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Projecte Item Name All Ready Exist.\nPlease Enter Defferent Projected Item Name...");
								txtProjectedName.requestFocus();
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Projected Item Name...");
							txtProjectedName.requestFocus();
						}

					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter A New Item Name...");
						cmbItemName.requestFocus();
					}
				}

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select Any Category Name...");
				cmbCategory.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Item Id...");
			btnItemRefresh.requestFocus();
		}



		return false;
	}

	private boolean packageValidationCheck() {
		if(!getTxtPackageId().isEmpty()) {
			if(!getCmbPackageCategory().isEmpty()) {
				if(LoadedInfo.isCategoryExist(getCmbPackageCategory())) {
					if(!getTxtPackegeName().isEmpty()) {
						//if(!isItemNameExist(getCmbItemName())) {

						if(packageItemList.size()>0) {
							return true;
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Add Any Package Item...");
							cmbPackageItem.requestFocus();
						}

					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter A Package Name...");
						txtPackegeName.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select a valid Category Name...");
					cmbPackageCategory.requestFocus();
				}

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select Any Category Name...");
				cmbPackageCategory.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Package Id...");
			btnPackageRefresh.requestFocus();
		}



		return false;
	}

	private boolean categoryValidationCheck() {
		if(!getTxtCategoryId().isEmpty()) {
			if(!getTxtCategoryName().isEmpty()) {
				if(!getCmbParentCategory().isEmpty()) {
					if(isCategoryNameExist(getCmbParentCategory())) {
						if(!isCategoryNameExist(getTxtCategoryName())) {
							return true;
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Category Name Allready Exist..\n Please Enter Another Category Name...");
							txtCategoryName.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select Any Valid Parent Category Name...");
						cmbParentCategory.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select Any Parent Category Name...");
					cmbParentCategory.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Any Category Name...");
				txtCategoryName.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Category Id...");
			btnCategoryRefresh.requestFocus();
		}
		return false;
	}

	/*private boolean checkCategoryExist() {
		try {
			ResultSet rs=databaseHandler.execQuery("select * from tbcategory where categoryName='"+getCmbCategory()+"' ;");
			if(rs.next()) {
				return true;
			}else {
				if(confrimationCheck("This Category Name is Not Exist...\n Do you want to save \""+getCmbCategory()+"\" as Category Name?")) {
					if(databaseHandler.execAction("insert into tbCategory (id,categoryName,type,parentId,isFixed,entryTime,createBy) values('"+getMaxCategoryId()+"','"+getCmbCategory()+"','1','1','0',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');"))
						return true;
				}else {
					return false;
				}
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return false;
	}*/
	private boolean isHeadFixed() {
		try {
			sql = "select isFixed from tbCategory where  id = '"+getTxtCategoryId()+"' and isFixed = 1; ";
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

	private boolean checkDuplicateModelInABrand() {
		// TODO Auto-generated method stub
		try {
			sql = "select itemName from tbItem where brandname = '"+getCmbBrand()+"' and  id != '"+getTxtItemId()+"';";
			System.out.println(sql);
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}

	private boolean isItemNameExist(String itemName,String itemId) {
		// TODO Auto-generated method stub
		try {
			sql = "select itemName from tbItem where itemName = '"+itemName+"' and id != '"+itemId+"'";
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

	private boolean isItemNameExist(String itemName) {
		// TODO Auto-generated method stub
		try {
			sql = "select itemName from tbItem where itemName = '"+itemName+"'";
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



	private boolean isProjectedNameExist(String projectedName,String itemId) {
		// TODO Auto-generated method stub
		try {
			sql = "select itemName from tbItem where projectedItemName = '"+projectedName+"' and id != '"+itemId+"'";
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
	private boolean isProjectedNameExist(String projectedName) {
		// TODO Auto-generated method stub
		try {
			sql = "select itemName from tbItem where projectedItemName = '"+projectedName+"'";
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
	private boolean isCategoryNameExist(String categoryName) {
		// TODO Auto-generated method stub
		try {
			sql = "select categoryName from tbCategory where categoryName = '"+categoryName+"' and id != '"+getTxtCategoryId()+"'";
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
	private boolean isCategoryidExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select id,categoryName from tbCategory where id = '"+getTxtCategoryId()+"'";
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

	/*private boolean isItemIdExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select id from tbItem where id = '"+getTxtItemId()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}*/

	private void priceSetByItemName(String projectedItemName) {
		try {
			sql = "select dbo.presentStock(i.id) as stock,id,salePrice from tbItem i where projectedItemName = '"+projectedItemName+"';";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtPrice(df.format(rs.getDouble("salePrice")));
				if(getTxtQty().isEmpty()||getTxtQty().equals("0")) {
					setTxtQty("1");
				}

			}



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rawMaterialsUnitLoadByProductName(String projectedName) {
		try {
			sql = "select UnitName,UnitQuantity from tbUnit where itemId = '"+LoadedInfo.getItemIdByProjectedName(projectedName)+"'";
			ResultSet rs= databaseHandler.execQuery(sql);
			cmbRawMaterialsUnit.getItems().clear();
			while(rs.next()) {
				cmbRawMaterialsUnit.getItems().add(rs.getString("UnitName")+" ("+rs.getString("UnitQuantity")+")");
			}
			if(cmbRawMaterialsUnit.getItems().size()>0)
				cmbRawMaterialsUnit.getSelectionModel().selectFirst();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void loadItemInfoTableByCategory(String categoryId) {
		// TODO Auto-generated method stub
		try {
			list.clear();
			if(categoryId.equals("0")) {
				sql = "select i.id,CategoryId,c.categoryName,projectedItemName,salePrice,case when isActive = 1 then 'Active' else 'Deactive' end as activeStatus  from tbItem i\r\n" + 
						"join tbCategory c\r\n" + 
						"on c.id = i.CategoryId order by cast(substring(projectedItemName,0,isnull (charindex('-',projectedItemName),DATALENGTH(projectedItemName))) as int)";
			}else {
				sql = "select i.id,CategoryId,c.categoryName,projectedItemName,salePrice,case when isActive = 1 then 'Active' else 'Deactive' end as activeStatus  from tbItem i\r\n" + 
						"join tbCategory c\r\n" + 
						"on c.id = i.CategoryId where i.CategoryId= '"+categoryId+"' order by cast(substring(projectedItemName,0,isnull (charindex('-',projectedItemName),DATALENGTH(projectedItemName))) as int)";
			}
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				list.add(new ItemInfo(rs.getString("id"), rs.getString("categoryName"),rs.getString("projectedItemName"), df.format(rs.getDouble("salePrice")),rs.getString("activeStatus")));
			}
			tableItemList.setItems(list);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void loadItemInfoTable(String itemName) {
		// TODO Auto-generated method stub
		try {
			list.clear();
			if(itemName.equals("")) {
				sql = "select i.id,CategoryId,c.categoryName,projectedItemName,PurchasePrice,salePrice,case when isActive = 1 then 'Active' else 'Deactive' end as activeStatus  from tbItem i\r\n" + 
						"join tbCategory c\r\n" + 
						"on c.id = i.CategoryId";
			}else {
				sql = "select i.id,CategoryId,c.categoryName,projectedItemName,PurchasePrice,salePrice,case when isActive = 1 then 'Active' else 'Deactive' end as activeStatus  from tbItem i\r\n" + 
						"join tbCategory c\r\n" + 
						"on c.id = i.CategoryId where i.projectedItemName= '"+itemName+"'";
			}
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				list.add(new ItemInfo(rs.getString("id"), rs.getString("categoryName"),rs.getString("projectedItemName"), df.format(rs.getDouble("SalePrice")),rs.getString("activeStatus")));
			}
			tableItemList.setItems(list);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void loadUnit() {
		// TODO Auto-generated method stub
		try {
			cmbUnit.data.clear();
			sql = "select unitName from tbunit group by unitName order by UnitName";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbUnit.data.add(rs.getString("unitName"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	private void loadBrandName() {
		// TODO Auto-generated method stub
		try {
			cmbBrand.data.clear();
			sql = "select BrandName from tbItem group by BrandName order by BrandName";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbBrand.data.add(rs.getString("BrandName"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null	, e);
		}
	}



	private void loadItemName() {
		// TODO Auto-generated method stub
		try {
			cmbItemName.data.clear();
			//cmbPackageItem.data.clear();
			sql = "select ItemName from tbItem where isActive='"+activeType+"' group by itemname order by ItemName";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbItemName.data.add(rs.getString("ItemName"));
				//cmbPackageItem.data.add(rs.getString("ItemName"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	private void loadProjectedItemName() {
		// TODO Auto-generated method stub
		try {
			//cmbItemName.data.clear();s
			cmbPackageItem.data.clear();
			sql = "select projectedItemName from tbItem where type = '"+ItemType.Finish_Goods.getType()+"' group by projectedItemName order by projectedItemName";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				//cmbItemName.data.add(rs.getString("ItemName"));
				cmbPackageItem.data.add(rs.getString("projectedItemName"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	private void loadPackageListTable(){
		try{
			packageList.clear(); 
			sql = "select p.id,c.categoryName,PackageName,price,p.type,ActiveStatus from tbPackage p \n"+
					"join tbCategory c \n"+
					"on p.CategoryId = c.id";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()){
				packageList.add(new PackageInfo(rs.getString("id"), rs.getString("categoryName"), rs.getString("packageName"), (rs.getInt("ActiveStatus")==1?"Active":"Deactive"), df.format(rs.getDouble("price"))));
			}
			tablePackageList.setItems(packageList);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void loadRawMaterialsItemName() {
		// TODO Auto-generated method stub
		try {
			//cmbItemName.data.clear();s
			cmbRawMaterialsItemName.data.clear();
			sql = "select projectedItemName from tbItem where type = '"+ItemType.Finish_Goods.getType()+"' group by projectedItemName order by projectedItemName";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				//cmbItemName.data.add(rs.getString("ItemName"));
				cmbRawMaterialsItemName.data.add(rs.getString("projectedItemName"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	private void loadRawMaterialsName() {
		// TODO Auto-generated method stub
		try {
			//cmbItemName.data.clear();s
			cmbRawMaterialsName.data.clear();
			sql = "select projectedItemName from tbItem where type = '"+ItemType.Raw_Materials.getType()+"' group by projectedItemName order by projectedItemName";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				//cmbItemName.data.add(rs.getString("ItemName"));
				cmbRawMaterialsName.data.add(rs.getString("projectedItemName"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	private void loadRawMaterialsTableList() {
		// TODO Auto-generated method stub
		try{
			if(getCmbRawMaterialsItemName().isEmpty()){
				sql = "select r.autoId,r.itemId,r.rawMaterialsId,r.unit,r.unitQuantity,r.quantity,i1.projectedItemName as itemName,i2.projectedItemName as RawMaterials from tbRawMaterials r \n"+
						"join tbItem i1 \n"+
						"on r.itemId = i1.id \n"+
						"join tbItem i2 \n"+
						"on r.rawMaterialsId = i2.id  ";						
			}else{
				sql = "select r.autoId,r.itemId,r.rawMaterialsId,r.unit,r.unitQuantity,r.quantity,i1.projectedItemName as itemName,i2.projectedItemName as RawMaterials from tbRawMaterials r \n"+
						"join tbItem i1 \n"+
						"on r.itemId = i1.id \n"+
						"join tbItem i2 \n"+
						"on r.rawMaterialsId = i2.id  \n"+
						"where r.itemId='"+LoadedInfo.getItemIdByProjectedName(getCmbRawMaterialsItemName())+"'";
			}

			ResultSet rs = databaseHandler.execQuery(sql);
			rawMaterialsList.clear();
			while(rs.next()){
				rawMaterialsList.add(new RawMaterialsInfo(rs.getString("autoId"), rs.getString("itemId"), rs.getString("itemName"), rs.getString("rawMaterialsId"), rs.getString("RawMaterials"), rs.getString("unit"), rs.getDouble("unitQuantity"), rs.getDouble("quantity")));

			}
			tableRawMaterials.setItems(rawMaterialsList);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void loadCmbFind() {
		// TODO Auto-generated method stub
		try {

			cmbFind.data.clear();
			sql = "select projectedItemName from tbItem order by ItemName";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbFind.data.add(rs.getString("projectedItemName"));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null	, e);
		}
	}



	private void loadCategoryName() {
		// TODO Auto-generated method stub
		try {
			cmbCategory.data.clear();
			cmbParentCategory.data.clear();
			cmbPackageCategory.data.clear();
			sql = "select categoryName from tbCategory order by categoryname";

			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbCategory.data.add(rs.getString("CategoryName"));
				cmbParentCategory.data.add(rs.getString("CategoryName"));
				cmbPackageCategory.data.add(rs.getString("CategoryName"));
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void loadMaxItemId() {
		// TODO Auto-generated method stub
		try {
			sql = "select concat('I-',isnull(max(cast(substring(id,3,len(id)-2) as int)),0)+1) as maxId from tbItem";

			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtItemId(rs.getString("maxId"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void loadMaxPackageId() {
		// TODO Auto-generated method stub
		try {
			sql = "select concat('P-',isnull(max(cast(substring(id,3,len(id)-2) as int)),0)+1) as maxId from tbPackage";

			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtPackageId(rs.getString("maxId"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void loadMaxCategoryId() {
		try {
			sql = "select isNull(MAX(id),0)+1 as maxId from tbCategory";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtCategoryId(rs.getString("maxId"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}


	private int getMaxCategoryId() {
		try {
			ResultSet rs = databaseHandler.execQuery("select isNull(MAX(id),0)+1 as maxId from tbCategory");
			if(rs.next()) {
				return rs.getInt("maxId");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return 0;
	}
	private void categoryTreeLoad() {
		// TODO Auto-generated method stub
		treeCategory.setRoot(treeLoad(rootNode,"1"));

	}

	@FXML
	private void concateProjectedItemName(KeyEvent event) {

		setTxtProjectedName(getCmbBrand()+" "+getCmbItemName());
	}



	private TreeItem<String> treeLoad(TreeItem<String> treeItem,String pHeadId) {
		// TODO Auto-generated method stub
		try {
			treeItem.getChildren().clear();
			/*ArrayList<String> treeName = new ArrayList<>();
			ArrayList<String> treeId = new ArrayList<>();*/

			ArrayList<CategoryInfo> temp = LoadedInfo.getCategoryInfoList(pHeadId);
			//sql = "select id,categoryName from tbCategory where parentId  = '"+pHeadId+"'";
			//ResultSet rs = databaseHandler.execQuery(sql);
			/*while(rs.next()) {
				treeName.add(rs.getString("categoryName"));
				treeId.add(rs.getString("id"));
			}*/
			/*for(int i=0;i<temp.size();i++) {
				treeName.add(temp.get(i).getCategoryName());
				treeId.add(temp.get(i).getCategoryId());
			}*/
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

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbParentCategory.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbParentCategory);
		});

		cmbItemCode.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbItemCode);
		});
		cmbFind.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbFind);
		});
		cmbCategory.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbCategory);
		});
		cmbBrand.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbBrand);
		});
		cmbItemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbItemName);
		});
		cmbUnit.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbUnit);
		});


		cmbPackageCategory.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbPackageCategory);
		});
		cmbPackageItem.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbPackageItem);
		});


		cmbRawMaterialsItemName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbRawMaterialsItemName);
		});

		cmbRawMaterialsName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbRawMaterialsName);
		});

		txtCategoryName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtCategoryName);
		});


		txtProjectedName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtProjectedName);
		});
		txtPurchasePrice.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtPurchasePrice);
		});
		txtSalesPrice.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtSalesPrice);
		});
		txtOpeningQty.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtOpeningQty);
		});
		txtReorderQty.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtReorderQty);
		});

		txtPackegeName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtPackegeName);
		});

		txtPrice.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtPrice);
		});
		txtPackagePrice.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtPackagePrice);
		});
		txtQty.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtQty);
		});

		txtTotalPackagePrice.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtTotalPackagePrice);
		});

		txtRawMaterialsQuantity.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtRawMaterialsQuantity);
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

	private void addCmp() {
		// TODO Auto-generated method stub
		cmbFind = new FxComboBox<>();

		cmbFind.setPrefWidth(338);
		cmbFind.setPrefHeight(28);
		vBoxFind.getChildren().clear();
		vBoxFind.getChildren().add(cmbFind);

		cmbParentCategory.setPrefWidth(338);
		cmbParentCategory.setPrefHeight(28);
		vBoxParentCategory.getChildren().clear();
		vBoxParentCategory.getChildren().add(cmbParentCategory);

		cmbItemCode.setPrefWidth(299);
		cmbItemCode.setPrefHeight(28);
		hBoxItemCode.getChildren().remove(0);
		hBoxItemCode.getChildren().add(0,cmbItemCode);

		cmbItemName.setPrefWidth(335);
		cmbItemName.setPrefHeight(28);
		vBoxItemName.getChildren().clear();
		vBoxItemName.getChildren().add(cmbItemName);

		cmbCategory.setPrefWidth(335);
		cmbCategory.setPrefHeight(28);
		vBoxCategory.getChildren().clear();
		vBoxCategory.getChildren().add(cmbCategory);

		cmbBrand.setPrefWidth(335);
		cmbBrand.setPrefHeight(28);
		vBoxBrand.getChildren().clear();
		vBoxBrand.getChildren().add(cmbBrand);

		cmbUnit.setPrefWidth(108);
		cmbUnit.setPrefHeight(28);
		HBoxUnit.getChildren().remove(0);
		HBoxUnit.getChildren().add(0,cmbUnit);


		cmbPackageItem.setPrefWidth(300);
		cmbPackageItem.setPrefHeight(28);
		vBoxPackageItemName.getChildren().remove(1);
		vBoxPackageItemName.getChildren().add(1,cmbPackageItem);

		cmbPackageCategory.setPrefWidth(215);
		cmbPackageCategory.setPrefHeight(28);
		vBoxPackageCatagory.getChildren().remove(1);
		vBoxPackageCatagory.getChildren().add(1,cmbPackageCategory);


		cmbPackageActiveStatus.setPrefWidth(92);
		cmbPackageActiveStatus.setPrefHeight(28);
		hBoxActiveStatus.getChildren().remove(1);
		hBoxActiveStatus.getChildren().add(1,cmbPackageActiveStatus);

		cmbRawMaterialsItemName.setPrefWidth(280);
		cmbRawMaterialsItemName.setPrefHeight(28);
		vBoxRawMaterials.getChildren().remove(0);
		vBoxRawMaterials.getChildren().add(0,cmbRawMaterialsItemName);

		cmbRawMaterialsName.setPrefWidth(280);
		cmbRawMaterialsName.setPrefHeight(28);
		vBoxRawMaterials.getChildren().remove(1);
		vBoxRawMaterials.getChildren().add(1,cmbRawMaterialsName);
	}

	private void setCmpData() {

		//dialogImeiGet = new DialogImeiGet();
		map = new HashMap<>();
		cmbFind.setPromptText("Select Item Name For Search");

		cmbStatus.getItems().addAll("Active","Deactive");
		cmbItemType.getItems().addAll("Finished goods","Raw Materials");

		cmbPackageActiveStatus.getItems().addAll("Active","Deactive");

		contextMenuRawMaterials = new ContextMenu();
		menuItemRawMaterialsDelete = new MenuItem("Delete");
		menuItemRawMaterialsRefresh = new MenuItem("Refresh");

		contextMenuRawMaterials.getItems().addAll(menuItemRawMaterialsDelete,menuItemRawMaterialsRefresh);
		tableRawMaterials.setContextMenu(contextMenuRawMaterials);

		contextMenuPackage = new ContextMenu();
		menuItemPackageItemDelete = new MenuItem("Delete");
		contextMenuPackage.getItems().add(menuItemPackageItemDelete);
		tablePackageItemList.setContextMenu(contextMenuPackage);

		itemIdCol.setCellValueFactory(new PropertyValueFactory("itemId"));
		categoryCol.setCellValueFactory(new PropertyValueFactory("category"));
		itemNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		priceCol.setCellValueFactory(new PropertyValueFactory("price"));
		activeStatusCol.setCellValueFactory(new PropertyValueFactory("activeStatus"));

		tableItemList.setColumnResizePolicy(tableItemList.CONSTRAINED_RESIZE_POLICY);


		packageItemNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		packageItemPriceCol.setCellValueFactory(new PropertyValueFactory("price"));
		packageItemDiscountCol.setCellValueFactory(new PropertyValueFactory("discount"));
		packageItemPackagePriceCol.setCellValueFactory(new PropertyValueFactory("packagePrice"));
		packageItemQuantityCol.setCellValueFactory(new PropertyValueFactory("quantity"));
		packageTotalPriceCol.setCellValueFactory(new PropertyValueFactory("totalPrice"));
		tablePackageItemList.setColumnResizePolicy(tablePackageItemList.CONSTRAINED_RESIZE_POLICY);


		packageNameCol.setCellValueFactory(new PropertyValueFactory("packageName"));
		packagePriceCol.setCellValueFactory(new PropertyValueFactory("price"));
		tablePackageList.setColumnResizePolicy(tablePackageList.CONSTRAINED_RESIZE_POLICY);


		rawMaterialsItemNameCol.setCellValueFactory(new PropertyValueFactory("itemName"));
		rawMaterialsNameCol.setCellValueFactory(new PropertyValueFactory("rawMaterialsName"));
		rawMaterialsUnitCol.setCellValueFactory(new PropertyValueFactory("unit"));
		rawMaterialsUnitQtyCol.setCellValueFactory(new PropertyValueFactory("unitQty"));
		rawMaterialsQuantityCol.setCellValueFactory(new PropertyValueFactory("quantity"));
		tableRawMaterials.setColumnResizePolicy(tableRawMaterials.CONSTRAINED_RESIZE_POLICY);

		txtSalesPrice.setTextFormatter(NumberField.getDoubleFormate());
		txtPurchasePrice.setTextFormatter(NumberField.getDoubleFormate());
		txtOpeningQty.setTextFormatter(NumberField.getDoubleFormate());
		txtReorderQty.setTextFormatter(NumberField.getDoubleFormate());
		txtPrice.setTextFormatter(NumberField.getDoubleFormate());
		txtPackagePrice.setTextFormatter(NumberField.getDoubleFormate());
		txtQty.setTextFormatter(NumberField.getDoubleFormate());
		txtRawMaterialsQuantity.setTextFormatter(NumberField.getDoubleFormate());

	}

	private void focusMoveAction() {
		Control[] control =  {cmbItemCode,cmbCategory,cmbBrand,cmbItemName,txtProjectedName,cmbUnit,txtPurchasePrice,txtSalesPrice,txtOpeningQty,txtReorderQty,txtDescription,btnItemSave};
		new FocusMoveByEnter(control);

		Control[] control2 =  {cmbParentCategory,txtCategoryName,btnCategoryAdd};
		new FocusMoveByEnter(control2);

		Control[] control3 =  {cmbPackageCategory,txtPackegeName,cmbPackageItem,txtPackagePrice,txtQty,btnPackageItemAdd};
		new FocusMoveByEnter(control3);

		Control[] control4 =  {cmbPackageActiveStatus,txtTotalPackagePrice,btnPackageSave};
		new FocusMoveByEnter(control4);

		Control[] control5 =  {cmbRawMaterialsItemName,cmbRawMaterialsName,cmbRawMaterialsUnit,txtRawMaterialsQuantity,btnRawMaterialsAdd};
		new FocusMoveByEnter(control5);

	}

	public static class ItemInfo{


		private SimpleStringProperty itemId;
		private SimpleStringProperty itemName;
		private SimpleStringProperty category;
		private SimpleStringProperty activeStatus;
		private SimpleStringProperty price;



		public ItemInfo(String itemId,String categroy,String itemName,String price,String activeStatus) {
			this.itemId = new SimpleStringProperty(itemId);
			this.category = new SimpleStringProperty(categroy);
			this.itemName = new SimpleStringProperty(itemName);
			this.price = new SimpleStringProperty(price);
			this.activeStatus = new SimpleStringProperty(activeStatus);

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

		public String getCategory() {
			return category.get();
		}

		public void setCategory(String category) {
			this.category = new SimpleStringProperty(category);
		}
		public String getActiveStatus() {
			return activeStatus.get();
		}
		public void setActiveStatus(String activeStatus) {
			this.activeStatus = new SimpleStringProperty(activeStatus);
		}
		public String getPrice() {
			return price.get();
		}
		public void setPrice(String price) {
			this.price = new SimpleStringProperty(price);
		}


	}

	public static class PackageInfo{
		private SimpleStringProperty packageId;
		private SimpleStringProperty categoryName;
		private SimpleStringProperty packageName;
		private SimpleStringProperty activeStatus;
		private SimpleStringProperty Price;

		public PackageInfo(String packageId,String categoryName,String packageName,String activeStatus,String Price) {
			this.packageId = new SimpleStringProperty(packageId);
			this.categoryName = new SimpleStringProperty(categoryName);
			this.packageName = new SimpleStringProperty(packageName);
			this.activeStatus = new SimpleStringProperty(activeStatus);
			this.Price = new SimpleStringProperty(Price);
		}

		public String getPackageId() {
			return packageId.get();
		}

		public void setPackageId(String packageId) {
			this.packageId = new SimpleStringProperty(packageId);
		}

		public String getCategoryName() {
			return categoryName.get();
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = new SimpleStringProperty(categoryName);
		}

		public String getPackageName() {
			return packageName.get();
		}

		public void setPackageName(String packageName) {
			this.packageName = new SimpleStringProperty(packageName);
		}

		public String getActiveStatus() {
			return activeStatus.get();
		}

		public void setActiveStatus(String activeStatus) {
			this.activeStatus = new SimpleStringProperty(activeStatus);
		}

		public String getPrice() {
			return Price.get();
		}

		public void setPrice(String Price) {
			this.Price = new SimpleStringProperty(Price);
		}



	}

	public static class PackageItemInfo{
		private SimpleStringProperty itemId;

		private SimpleStringProperty itemName;
		private SimpleDoubleProperty Price;
		private SimpleDoubleProperty discount;
		private SimpleDoubleProperty packagePrice;
		private SimpleDoubleProperty quantity;


		public PackageItemInfo(String itemId,String itemName,double Price,double discount,double packagePrice,double quantity) {
			this.itemId = new SimpleStringProperty(itemId);

			this.itemName = new SimpleStringProperty(itemName);
			this.Price = new SimpleDoubleProperty(Price);
			this.discount = new SimpleDoubleProperty(discount);
			this.packagePrice = new SimpleDoubleProperty(packagePrice);
			this.quantity = new SimpleDoubleProperty(quantity);


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

		public double getPrice() {
			return Price.get();
		}

		public void setPrice(double Price) {
			this.Price = new SimpleDoubleProperty(Price);
		}

		public double getDiscount() {
			return discount.get();
		}

		public void setDiscount(double discount) {
			this.discount = new SimpleDoubleProperty(discount);
		}

		public double getPackagePrice() {
			return packagePrice.get();
		}

		public void setPackagePrice(double packagePrice) {
			this.packagePrice = new SimpleDoubleProperty(packagePrice);
		}

		public double getQuantity() {
			return quantity.get();
		}

		public void setQuantity(double quantity) {
			this.quantity = new SimpleDoubleProperty(quantity);
		}

		public double getTotalPrice() {
			return quantity.get()*packagePrice.get();
		}



	}

	public static class RawMaterialsInfo{
		private SimpleStringProperty autoId;
		private SimpleStringProperty itemId;
		private SimpleStringProperty itemName;
		private SimpleStringProperty rawMaterialsId;
		private SimpleStringProperty rawMaterialsName;
		private SimpleStringProperty unit;
		private SimpleDoubleProperty unitQty;
		private SimpleDoubleProperty quantity;


		public RawMaterialsInfo(String autoId,String itemId,String itemName,String rawMaterilasId ,String rawMaterialsName,String unit,double unitQty,double quantity) {
			this.autoId = new SimpleStringProperty(autoId);
			this.itemId = new SimpleStringProperty(itemId);
			this.itemName = new SimpleStringProperty(itemName);
			this.rawMaterialsId = new SimpleStringProperty(rawMaterilasId);
			this.rawMaterialsName = new SimpleStringProperty(rawMaterialsName);
			this.unit = new SimpleStringProperty(unit);
			this.unitQty = new SimpleDoubleProperty(unitQty);
			this.quantity = new SimpleDoubleProperty(quantity);
		}
		public String getAutoId() {
			return autoId.get();
		}

		public void setAutoId(String autoId) {
			this.autoId = new SimpleStringProperty(autoId);
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

		public String getRawMaterialsId() {
			return rawMaterialsId.get();
		}

		public void setRawMaterialsId(String rawMaterialsId) {
			this.rawMaterialsId = new SimpleStringProperty(rawMaterialsId);
		}

		public String getRawMaterialsName() {
			return rawMaterialsName.get();
		}

		public void setRawMaterialsName(String rawMaterialsName) {
			this.rawMaterialsName = new SimpleStringProperty(rawMaterialsName);
		}

		public String getUnit() {
			return unit.get();
		}

		public void setUnit(String unit) {
			this.unit = new SimpleStringProperty(unit);
		}


		public double getUnitQty() {
			return unitQty.get();
		}

		public void setUnitQty(double unitQty) {
			this.unitQty = new SimpleDoubleProperty(unitQty);
		}

		public double getQuantity() {
			return quantity.get();
		}

		public void setQuantity(double quantity) {
			this.quantity = new SimpleDoubleProperty(quantity);
		}




	}

	public String getTxtCategoryId() {
		return txtCategoryId.getText().trim();
	}

	public void setTxtCategoryId(String txtCategoryId) {
		this.txtCategoryId.setText(txtCategoryId);
	}

	public String getTxtCategoryName() {
		return txtCategoryName.getText().trim();
	}

	public void setTxtCategoryName(String txtCategoryName) {
		this.txtCategoryName.setText(txtCategoryName);
	}

	public String getTxtItemId() {
		return txtItemId.getText().trim();
	}

	public void setTxtItemId(String txtItemId) {
		this.txtItemId.setText(txtItemId);
	}



	public String getTxtUnitQty() {
		return txtUnitQty.getText().trim();
	}

	public void setTxtUnitQty(String txtUnitQty) {
		this.txtUnitQty.setText(txtUnitQty);
	}

	public String getTxtPurchasePrice() {
		return txtPurchasePrice.getText().trim();
	}

	public void setTxtPurchasePrice(double txtPurchasePrice) {
		this.txtPurchasePrice.setText(df.format(txtPurchasePrice));
	}

	public String getTxtSalesPrice() {
		return txtSalesPrice.getText().trim();
	}

	public void setTxtSalesPrice(double txtSalesPrice) {
		this.txtSalesPrice.setText(df.format(txtSalesPrice));
	}

	public String getTxtOpeningQty() {
		return txtOpeningQty.getText().trim();
	}

	public void setTxtOpeningQty(double txtOpeningQty) {
		this.txtOpeningQty.setText(df.format(txtOpeningQty));
	}

	public String getTxtReorderQty() {
		return txtReorderQty.getText().trim();
	}

	public void setTxtReorderQty(double txtReorderQty) {
		this.txtReorderQty.setText(df.format(txtReorderQty));
	}

	public String getTxtStock() {
		return txtStock.getText().trim();
	}

	public void setTxtStock(double txtStock) {
		this.txtStock.setText(df.format(txtStock));
	}

	public String getTxtProjectedName() {
		return txtProjectedName.getText().trim();
	}

	public void setTxtProjectedName(String txtProjectedName) {
		this.txtProjectedName.setText(txtProjectedName);
	}

	public String getTxtDescription() {
		return txtDescription.getText().trim();
	}

	public void setTxtDescription(String txtDescription) {
		this.txtDescription.setText(txtDescription);
	}

	/*public boolean getCheckImei() {
		return checkImei.isSelected();
	}

	public void setCheckImei(boolean checkImei) {
		this.checkImei.setSelected(checkImei);
	}*/

	public String getCmbCreatedUnit() {
		if(cmbCreatedUnit.getValue() != null)
			return cmbCreatedUnit.getValue().toString().trim();
		else return "";
	}

	public void setCmbCreatedUnit(String cmbCreatedUnit) {
		this.cmbCreatedUnit.setValue(cmbCreatedUnit);;
	}

	public String getCmbParentCategory() {
		if(cmbParentCategory.getValue() != null)
			return cmbParentCategory.getValue().toString().trim();
		else return "";
	}

	public void setCmbParentCategory(String cmbParentCategory) {
		this.cmbParentCategory.setValue(cmbParentCategory);
	}

	public String getCmbItemCode() {
		if(cmbItemCode.getValue() != null)
			return cmbItemCode.getValue().toString().trim();
		else return "";
	}

	public void setCmbItemCode(String cmbItemCode) {
		this.cmbItemCode.setValue(cmbItemCode);
	}

	public String getCmbItemName() {
		if(cmbItemName.getValue() != null)
			return cmbItemName.getValue().toString().trim();
		else return "";
	}

	public void setCmbItemName(String cmbItemName) {
		this.cmbItemName.setValue(cmbItemName);
	}

	public String getCmbCategory() {
		if(cmbCategory.getValue() != null)
			return cmbCategory.getValue().toString().trim();
		else return "";
	}

	public void setCmbCategory(String cmbCategory) {
		this.cmbCategory.setValue(cmbCategory);
	}

	public String getCmbBrand() {
		if(cmbBrand.getValue() != null)
			return cmbBrand.getValue().toString().trim();
		else return "";
	}

	public void setCmbBrand(String cmbBrand) {
		this.cmbBrand.setValue(cmbBrand);
	}

	public String getCmbUnit() {
		if(cmbUnit.getValue() != null)
			return cmbUnit.getValue().toString().trim();
		else return "";
	}

	public void setCmbUnit(String cmbUnit) {
		this.cmbUnit.setValue(cmbUnit);
	}

	public String getCmbFind() {
		if(cmbFind.getValue() != null)
			return cmbFind.getValue().toString().trim();
		else return "";
	}

	public void setCmbFind(String cmbFind) {
		this.cmbFind.setValue(cmbFind);
	}

	public String getCmbStatus() {
		if(cmbStatus.getValue() != null)
			return cmbStatus.getValue().toString().trim();
		else return "";
	}

	public void setCmbStatus(String cmbStatus) {
		this.cmbStatus.setValue(cmbStatus);
	}

	public String getCmbItemType() {
		if(cmbItemType.getValue() != null)
			return cmbItemType.getValue().toString().trim();
		else return "";
	}

	public int getCmbItemTypeIndex() {
		if(cmbItemType.getValue() != null)
			return cmbItemType.getSelectionModel().getSelectedIndex()+1;
		else return 0;
	}

	public void setCmbItemType(String cmbItemType) {
		this.cmbItemType.setValue(cmbItemType);
	}
	
	public void setCmbItemTypeIndex(int indexNo) {
		this.cmbItemType.getSelectionModel().select(indexNo-1);
	}

	public String getTxtPackageId() {
		return txtPackageId.getText().trim();
	}

	public void setTxtPackageId(String txtPackageId) {
		this.txtPackageId.setText(txtPackageId);
	}

	public String getTxtPackegeName() {
		return txtPackegeName.getText().trim();
	}

	public void setTxtPackegeName(String txtPackegeName) {
		this.txtPackegeName.setText(txtPackegeName);
	}

	public String getTxtDiscount() {
		return txtDiscount.getText().trim();
	}

	public double getTxtDiscountDouble() {
		if(txtDiscount.getText().trim().isEmpty()){
			return 0;
		}else{
			return Double.valueOf(txtDiscount.getText().trim());
		}

	}

	public void setTxtDiscount(String txtDiscount) {
		this.txtDiscount.setText(txtDiscount);
	}
	
	public void setTxtDiscount(double txtDiscount) {
		this.txtDiscount.setText(String.valueOf(txtDiscount));
	}

	public String getTxtPrice() {
		return txtPrice.getText().trim();
	}
	public double getTxtPriceDouble() {
		if(txtPrice.getText().trim().isEmpty()){
			return 0;
		}else
			return Double.valueOf(txtPrice.getText().trim());
	}

	public void setTxtPrice(String txtPrice) {
		this.txtPrice.setText(txtPrice);
	}
	
	public void setTxtPrice(double txtPrice) {
		this.txtPrice.setText(String.valueOf(txtPrice));
	}

	public String getTxtQty() {
		return txtQty.getText().trim();
	}

	public double getTxtQtyDouble() {
		if(txtQty.getText().trim().isEmpty()){
			return 0;
		}else
			return Double.valueOf(txtQty.getText().trim());
	}
	public void setTxtQty(String txtQty) {
		this.txtQty.setText(txtQty);
	}
	
	public void setTxtQty(double txtQty) {
		this.txtQty.setText(String.valueOf(txtQty));
	}

	public String getTxtPackagePrice() {
		return txtPackagePrice.getText().trim();
	}

	public double getTxtPackagePriceDouble() {
		if(txtPackagePrice.getText().trim().isEmpty()){
			return 0;
		}else
			return Double.valueOf(txtPackagePrice.getText().trim());
	}

	public void setTxtPackagePrice(String txtPackagePrice) {
		this.txtPackagePrice.setText(txtPackagePrice);
	}

	public void setTxtPackagePrice(double txtPackagePrice) {
		this.txtPackagePrice.setText(String.valueOf(txtPackagePrice));
	}

	public String getTxtTotalPackagePrice() {
		return txtTotalPackagePrice.getText().trim();
	}

	public void setTxtTotalPackagePrice(String txtTotalPackagePrice) {
		this.txtTotalPackagePrice.setText(txtTotalPackagePrice);
	}

	public void setTxtTotalPackagePrice(double txtTotalPackagePrice) {
		this.txtTotalPackagePrice.setText(String.valueOf(txtTotalPackagePrice));
	}

	public String getCmbPackageActiveStatus() {		
		if(cmbPackageActiveStatus.getValue() != null)
			return cmbPackageActiveStatus.getValue().toString().trim();
		else return "";		
	}

	public int getCmbPackageActiveStatusInt() {		
		if(getCmbPackageActiveStatus().equalsIgnoreCase("Active")){
			return 1;
		}else if(getCmbPackageActiveStatus().equalsIgnoreCase("Deactive")){
			return 0;
		}else{
			return 1;
		}
	}

	public void setCmbPackageActiveStatus(String cmbPackageActiveStatus) {
		this.cmbPackageActiveStatus.setValue(cmbPackageActiveStatus);
	}

	public String getCmbPackageItem() {
		if(cmbPackageItem.getValue() != null)
			return cmbPackageItem.getValue().toString().trim();
		else return "";	
	}

	public void setCmbPackageItem(String cmbPackageItem) {
		this.cmbPackageItem.setValue(cmbPackageItem);
	}

	public String getCmbPackageCategory() {

		if(cmbPackageCategory.getValue() != null)
			return cmbPackageCategory.getValue().toString().trim();
		else return "";
	}

	public void setCmbPackageCategory(String cmbPackageCategory) {
		this.cmbPackageCategory.setValue(cmbPackageCategory);
	}

	public String getCmbRawMaterialsItemName() {
		if(cmbRawMaterialsItemName.getValue() != null)
			return cmbRawMaterialsItemName.getValue().toString().trim();
		else return "";

	}

	public void setCmbRawMaterialsItemName(String cmbRawMaterialsItemName) {
		this.cmbRawMaterialsItemName.setValue(cmbRawMaterialsItemName);
	}

	public String getCmbRawMaterialsName() {
		if(cmbRawMaterialsName.getValue() != null)
			return cmbRawMaterialsName.getValue().toString().trim();
		else
			return "";
	}

	public void setCmbRawMaterialsName(String cmbRawMaterialsName) {
		this.cmbRawMaterialsName.setValue(cmbRawMaterialsName);
	}

	public String getCmbRawMaterialsUnit() {
		if(cmbRawMaterialsUnit.getValue() != null)
			return cmbRawMaterialsUnit.getValue().toString().trim();
		else return "";

	}

	public void setCmbRawMaterialsUnit(String cmbRawMaterialsUnit) {
		this.cmbRawMaterialsUnit.setValue(cmbRawMaterialsUnit);
	}

	public String getTxtRawMaterialsQuantity() {
		return txtRawMaterialsQuantity.getText().trim();
	}

	public double getTxtRawMaterialsQuantityDouble() {
		if(txtRawMaterialsQuantity.getText().trim().isEmpty()){
			return 0;
		}else
			return Double.valueOf(txtRawMaterialsQuantity.getText().trim());
	}
	public void setTxtRawMaterialsQuantity(String txtRawMaterialsQuantity) {
		this.txtRawMaterialsQuantity.setText(txtRawMaterialsQuantity);
	}
}

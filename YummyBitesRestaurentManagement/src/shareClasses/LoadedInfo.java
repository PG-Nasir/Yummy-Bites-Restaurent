package shareClasses;

import java.awt.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.sun.prism.TextureMap;

import databaseHandler.DatabaseHandler;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class LoadedInfo {

	public static HashMap<String, CategoryInfo> mapCategoryInfo = new HashMap<>();
	public static HashMap<String, ArrayList<CategoryInfo>> mapCategoryInfoList = new HashMap<>();

	public static HashMap<String, AccountsHeadInfo> mapAccountHeadInfo = new HashMap<>();
	public static HashMap<String, ArrayList<AccountsHeadInfo>> mapAccountHeadInfoList = new HashMap<>();

	public static HashMap<String, ItemInfo> mapItem = new HashMap<>();
	public static HashMap<String, ItemInfo> mapFinishedGoods = new HashMap<>();
	public static HashMap<String, ArrayList<ItemInfo>> mapFinishedGoodsListByCategory = new HashMap<>();

	public static HashMap<String, ItemInfo> mapRawMaterials = new HashMap<>();
	public static HashMap<String, PackageInfo> mapPackageInfo = new HashMap<>();
	public static HashMap<String, ArrayList<PackageItemInfo>> mapPackageItemInfoList = new HashMap<>();
	public static HashMap<String, ArrayList<PackageInfo>> mapPackageInfoListByCategory = new HashMap<>();
	public static HashMap<String, ArrayList<RawMaterialsInfo>> mapRawMaterialInfoLisByItemId = new HashMap<>();
	public static HashMap<String, ObservableList<String>> mapUnitListByItemId = new HashMap<>();


	private static ArrayList<String> listRawMaterialsItemId = new ArrayList<>();
	private static ArrayList<String> listPackageId = new ArrayList<>();
	private static ArrayList<String> listCategoryId = new ArrayList<>();
	private static ArrayList<String> listItemId = new ArrayList<>();
	private static ArrayList<String> listAccountsHeadId = new ArrayList<>();
	private static DatabaseHandler databaseHandler;

	private static final int finishedGoods = 1;
	private static final int rawMaterials = 2;

	private final static int active = 1;
	private final static int deactive = 0;

	private static String  sql;

	public LoadedInfo(){
		databaseHandler = DatabaseHandler.getInstance();
		loadCategoryInfo();
		loadRawMaterialsItemIdList();
		loadCategoryInfoList();
		loadAllMapItemInfo();
		LoadPackageInfo();
		LoadPackageInfoList();
		loadAccountsHeadInfo();
		loadAccountsHeadInfoList();
		loadFinishGoodsListByCategory();
		loadPackageListByCategory();
		LoadAllUnitByItemId();
	}

	public static String getItemIdByProjectedName(String projectedName){
		return mapItem.get(projectedName).getItemId();
	}

	public static boolean isItemExistByProjectedName(String projectedName){
		if( mapItem.get(projectedName) !=null) return true;
		else return false;
	}

	public static boolean isItemIdExist(String itemId){
		if( mapItem.get(itemId) !=null) return true;
		else return false;
	}

	public static ArrayList<ItemInfo> getmFinishedGoodsListByCategory(String categoryId){
		return mapFinishedGoodsListByCategory.get(categoryId);
	}

	public static ArrayList<RawMaterialsInfo> getRawMalerialsInfoistByItemId(String itemId){
		return mapRawMaterialInfoLisByItemId.get(itemId);
	}
	public static CategoryInfo getCategoryInfo(String categroyIdOrName){
		return mapCategoryInfo.get(categroyIdOrName);
	}

	public static boolean isCategoryExist(String categroyIdOrName){
		if( mapCategoryInfo.get(categroyIdOrName) !=null) return true;
		else return false;
	}
	public static ArrayList<CategoryInfo> getCategoryInfoList(String parentId){
		return mapCategoryInfoList.get(parentId);
	}

	public static PackageInfo getPackageInfo(String packageIdOrName){
		return mapPackageInfo.get(packageIdOrName);
	}

	public static boolean isPackageExist(String packageIdOrName){
		if( mapPackageInfo.get(packageIdOrName) !=null) return true;
		else return false;
	}

	public static ArrayList<PackageItemInfo> getPackgeItemInfoList(String packageId){
		return mapPackageItemInfoList.get(packageId);
	}

	public static ArrayList<PackageInfo> getPackageInfoListByCategory(String categoryId){
		return mapPackageInfoListByCategory.get(categoryId);
	}

	public static AccountsHeadInfo getAccountsHeadInfo(String headIdOrName){
		return mapAccountHeadInfo.get(headIdOrName);
	}

	public static boolean isAccountHeadExist(String headIdOrName){
		if( mapAccountHeadInfo.get(headIdOrName) !=null) return true;
		else return false;
	}
	public static ArrayList<AccountsHeadInfo> getAccountHeadInfoList(String headId){
		return mapAccountHeadInfoList.get(headId);
	}

	public static ObservableList<String> getUnitList(String headId){
		return mapUnitListByItemId.get(headId);
	}
	
	public static void loadCategoryInfo(){
		try{
			sql = "select id,categoryName,type,parentId,isFixed from tbCategory";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapCategoryInfo.clear();
			listCategoryId.clear();
			while(rs.next()){
				listCategoryId.add(rs.getString("id"));
				mapCategoryInfo.put(rs.getString("id"), new CategoryInfo(rs.getString("id"), rs.getString("categoryName"), rs.getString("parentId"), rs.getInt("type")));
				mapCategoryInfo.put(rs.getString("categoryName"), new CategoryInfo(rs.getString("id"), rs.getString("categoryName"), rs.getString("parentId"), rs.getInt("type")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	public static void loadCategoryInfoList(){
		try{
			mapCategoryInfoList.clear();

			for(int i = 0;i<listCategoryId.size();i++){
				sql = "select id,categoryName,type,parentId,isFixed from tbCategory where parentId = '"+listCategoryId.get(i)+"'";
				ResultSet rs = databaseHandler.execQuery(sql);
				ArrayList<CategoryInfo> temp = new ArrayList<>();
				while(rs.next()){
					temp.add(new CategoryInfo(rs.getString("id"), rs.getString("categoryName"), rs.getString("parentId"), rs.getInt("Type")));
				}

				mapCategoryInfoList.put(listCategoryId.get(i), temp);
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	public static void loadRawMaterialsItemIdList() {
		// TODO Auto-generated method stub
		try{
			sql = "select itemId from tbRawMaterials group by itemId";
			ResultSet rs = databaseHandler.execQuery(sql);
			listRawMaterialsItemId.clear();
			while(rs.next()){
				listRawMaterialsItemId.add(rs.getString("itemId"));
			}

			mapRawMaterialInfoLisByItemId.clear();
			for(int i=0;i<listRawMaterialsItemId.size();i++){
				sql = "select r.autoId,r.itemId,r.rawMaterialsId,r.unit,r.unitQuantity,r.quantity,i2.projectedItemName as itemName,i2.salePrice from tbRawMaterials r \n"+ 
						"join tbItem i2 \n"+ 
						"on r.rawMaterialsId = i2.id  \n"+
						"where r.itemId = '"+listRawMaterialsItemId.get(i)+"'";
				rs = databaseHandler.execQuery(sql);
				ArrayList<RawMaterialsInfo> temp = new ArrayList<>();
				while(rs.next()){
					temp.add(new RawMaterialsInfo(rs.getString("rawMaterialsId"), rs.getString("itemName"), rs.getDouble("salePrice"), rs.getString("unit"), rs.getDouble("unitQuantity"), rs.getDouble("quantity")));
				}
				if(temp.size()>0) mapRawMaterialInfoLisByItemId.put(listRawMaterialsItemId.get(i), temp);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void loadAllMapItemInfo(){
		try{
			sql = "select autoId,id,ItemName,projectedItemName,PurchasePrice,salePrice,unit,isActive,Type from tbItem ";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapItem.clear();
			mapFinishedGoods.clear();
			mapRawMaterials.clear();
			listItemId.clear();
			while(rs.next()){
				listItemId.add(rs.getString("Id"));
				mapItem.put(rs.getString("Id"), new ItemInfo(rs.getString("id"), rs.getString("itemName"), rs.getString("projectedItemName"), rs.getString("salePrice"),rs.getString("Unit"), rs.getString("IsActive"), rs.getString("type")));
				mapItem.put(rs.getString("projectedItemName"), new ItemInfo(rs.getString("id"), rs.getString("itemName"), rs.getString("projectedItemName"), rs.getString("salePrice"),rs.getString("Unit"), rs.getString("IsActive"), rs.getString("type")));

				if(rs.getInt("Type") == finishedGoods && rs.getInt("isActive")== active){
					mapFinishedGoods.put(rs.getString("Id"), new ItemInfo(rs.getString("id"), rs.getString("itemName"), rs.getString("projectedItemName"), rs.getString("salePrice"),rs.getString("Unit"), rs.getString("IsActive"), rs.getString("type")));
					mapFinishedGoods.put(rs.getString("projectedItemName"), new ItemInfo(rs.getString("id"), rs.getString("itemName"), rs.getString("projectedItemName"), rs.getString("salePrice"),rs.getString("Unit"), rs.getString("IsActive"), rs.getString("type")));

				}else if(rs.getInt("Type") == rawMaterials && rs.getInt("isActive")== active) {
					mapRawMaterials.put(rs.getString("Id"), new ItemInfo(rs.getString("id"), rs.getString("itemName"), rs.getString("projectedItemName"), rs.getString("salePrice"),rs.getString("Unit"), rs.getString("IsActive"), rs.getString("type")));
					mapRawMaterials.put(rs.getString("projectedItemName"), new ItemInfo(rs.getString("id"), rs.getString("itemName"), rs.getString("projectedItemName"), rs.getString("salePrice"),rs.getString("Unit"), rs.getString("IsActive"), rs.getString("type")));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void LoadPackageInfo(){
		try{
			sql = "select p.id,p.CategoryId,p.PackageName,p.price,p.type,p.ActiveStatus,c.categoryName from tbpackage p \n"+
					"join tbCategory c \n"+
					"on p.CategoryId = c.id";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapPackageInfo.clear();
			listPackageId.clear();
			while(rs.next()){
				listPackageId.add(rs.getString("id"));
				mapPackageInfo.put(rs.getString("id"), new PackageInfo(rs.getString("id"), rs.getString("categoryName"), rs.getString("PackageName"),(rs.getInt("ActiveStatus")==1?"Active":"Deactive"), rs.getString("price")));
				mapPackageInfo.put(rs.getString("PackageName"), new PackageInfo(rs.getString("id"), rs.getString("categoryName"), rs.getString("PackageName"),(rs.getInt("ActiveStatus")==1?"Active":"Deactive"), rs.getString("price")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	public static void LoadPackageInfoList(){
		try{
			mapPackageItemInfoList.clear();


			for(int i = 0;i<listPackageId.size();i++){
				sql = "select p.*,i.projectedItemName from tbpackageDetails p \n"+
						"join tbitem i \n"+
						"on p.itemId = i.id \n"+
						" where packageId = '"+mapPackageInfo.get(listPackageId.get(i)).getPackageId()+"' order by p.AutoId";
				ResultSet rs = databaseHandler.execQuery(sql);
				ArrayList<PackageItemInfo> temp = new ArrayList<>();
				while(rs.next()){
					temp.add(new PackageItemInfo(rs.getString("itemId"), rs.getString("projectedItemName"), rs.getDouble("salesPrice"),rs.getDouble("Discount"),rs.getDouble("packagePrice"), rs.getDouble("quantity")));
				}

				mapPackageItemInfoList.put(listPackageId.get(i), temp);
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}


	public static void loadAccountsHeadInfo(){
		try{
			sql = "select headid,headTitle,pheadId,UnitId,DepId,type,isFixed from tbaccfhead";
			ResultSet rs = databaseHandler.execQuery(sql);
			mapAccountHeadInfo.clear();
			listAccountsHeadId.clear();
			while(rs.next()){
				listAccountsHeadId.add(rs.getString("headid"));
				mapAccountHeadInfo.put(rs.getString("headid"), new AccountsHeadInfo(rs.getString("headid"), rs.getString("headTitle"), rs.getString("pheadId"), rs.getString("UnitId"), rs.getString("DepId"), rs.getInt("type"), rs.getInt("isFixed")));
				mapAccountHeadInfo.put(rs.getString("headTitle"), new AccountsHeadInfo(rs.getString("headid"), rs.getString("headTitle"), rs.getString("pheadId"), rs.getString("UnitId"), rs.getString("DepId"), rs.getInt("type"), rs.getInt("isFixed")));
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	public static void loadAccountsHeadInfoList(){
		try{
			mapAccountHeadInfoList.clear();

			for(int i = 0;i<listAccountsHeadId.size();i++){
				sql = "select headid,headTitle,pheadId,UnitId,DepId,type,isFixed from tbaccfhead where pheadId = '"+listAccountsHeadId.get(i)+"'";
				ResultSet rs = databaseHandler.execQuery(sql);
				ArrayList<AccountsHeadInfo> temp = new ArrayList<>();
				while(rs.next()){
					temp.add(new AccountsHeadInfo(rs.getString("headid"), rs.getString("headTitle"), rs.getString("pheadId"), rs.getString("UnitId"), rs.getString("DepId"), rs.getInt("type"), rs.getInt("isFixed")));
				}

				mapAccountHeadInfoList.put(listAccountsHeadId.get(i), temp);
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	public static void loadFinishGoodsListByCategory(){
		try{
			mapFinishedGoodsListByCategory.clear();

			for(int i = 0;i<listCategoryId.size();i++){
				sql = "select i.id,CategoryId,c.categoryName,i.unit,ItemName,projectedItemName,salePrice,i.type,case when isActive = 1 then 'Active' else 'Deactive' end as activeStatus  from tbItem i \n"+
						"join tbCategory c \n"+
						"on c.id = i.CategoryId where i.type = '"+finishedGoods+"' and i.isActive='"+active+"' and i.CategoryId= '"+listCategoryId.get(i)+"' order by cast(substring(projectedItemName,0,isnull (charindex('-',projectedItemName),DATALENGTH(projectedItemName)))as int)";
				ResultSet rs = databaseHandler.execQuery(sql);
				ArrayList<ItemInfo> temp = new ArrayList<>();
				while(rs.next()){
					temp.add(new ItemInfo(rs.getString("id"), rs.getString("projectedItemName"), rs.getString("ItemName"), rs.getString("salePrice"),rs.getString("unit"), rs.getString("activeStatus"), rs.getString("type")));
				}

				mapFinishedGoodsListByCategory.put(listCategoryId.get(i), temp);
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	public static void loadPackageListByCategory(){
		try{
			mapPackageInfoListByCategory.clear();

			for(int i = 0;i<listCategoryId.size();i++){
				sql = "select p.*,c.categoryName from tbPackage p \n"+
						"join tbCategory c \n"+
						"on p.CategoryId = c.id where p.CategoryId = '"+listCategoryId.get(i)+"'";
				ResultSet rs = databaseHandler.execQuery(sql);
				ArrayList<PackageInfo> temp = new ArrayList<>();
				while(rs.next()){
					temp.add(new PackageInfo(rs.getString("id"), rs.getString("categoryName"), rs.getString("packageName"), (rs.getInt("ActiveStatus")==active?"Active":"Deactive"), rs.getString("price")));
				}

				mapPackageInfoListByCategory.put(listCategoryId.get(i), temp);
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}
	
	public static void LoadAllUnitByItemId(){
		try{
			
			for(int i = 0; i < listItemId.size();i++){
				ResultSet rs = databaseHandler.execQuery("Select unitName,unitQuantity from tbUnit where itemId='"+listItemId.get(i)+"'");
				ObservableList<String> temp = FXCollections.observableArrayList() ; 
				while(rs.next()){
					temp.add(rs.getString("unitName")+" ("+rs.getString("unitQuantity")+")");
				}
				mapUnitListByItemId.put(listItemId.get(i), temp);
			}
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static class ItemInfo{
		private SimpleStringProperty itemId;
		private SimpleStringProperty itemName;
		private SimpleStringProperty projectedName;
		private SimpleStringProperty unit;
		private SimpleStringProperty activeStatus;
		private SimpleStringProperty price;
		private SimpleStringProperty type;



		public ItemInfo(String itemId,String projectedName,String itemName,String price,String unit,String activeStatus,String type) {
			this.itemId = new SimpleStringProperty(itemId);
			this.projectedName = new SimpleStringProperty(projectedName);
			this.itemName = new SimpleStringProperty(itemName);
			this.unit = new SimpleStringProperty(unit);
			this.price = new SimpleStringProperty(price);
			this.activeStatus = new SimpleStringProperty(activeStatus);
			this.type = new SimpleStringProperty(type);

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

		public String getProjectedName() {
			return projectedName.get();
		}

		public void setProjectedName(String projectedName) {
			this.projectedName = new SimpleStringProperty(projectedName);
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
		public double getPriceDouble() {
			return Double.valueOf(price.get());
		}
		public void setPrice(String price) {
			this.price = new SimpleStringProperty(price);
		}

		public String getUnit() {
			return unit.get();
		}
		public void setUnit(String unit) {
			this.unit = new SimpleStringProperty(unit);
		}

		public String getType() {
			return type.get();
		}
		public void setType(String type) {
			this.type = new SimpleStringProperty(type);
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

		public double getPriceDouble() {
			return Double.valueOf(Price.get());
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

		public int getQuantityInt() {
			return quantity.intValue();
		}

		public void setQuantity(double quantity) {
			this.quantity = new SimpleDoubleProperty(quantity);
		}

		public double getTotalPrice() {
			return quantity.get()*packagePrice.get();
		}



	}


	public static class RawMaterialsInfo{
		private SimpleStringProperty itemId;

		private SimpleStringProperty itemName;
		private SimpleDoubleProperty Price;
		private SimpleStringProperty unit;
		private SimpleDoubleProperty unitQuantity;
		private SimpleDoubleProperty quantity;


		public RawMaterialsInfo(String itemId,String itemName,double Price,String unit,double unitQuantity,double quantity) {
			this.itemId = new SimpleStringProperty(itemId);

			this.itemName = new SimpleStringProperty(itemName);
			this.Price = new SimpleDoubleProperty(Price);
			this.unit = new SimpleStringProperty(unit);
			this.unitQuantity = new SimpleDoubleProperty(unitQuantity);
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

		public int getQuantityInt() {
			return quantity.intValue();
		}

		public void setQuantity(double quantity) {
			this.quantity = new SimpleDoubleProperty(quantity);
		}

		public double getTotalPrice() {
			return quantity.get()*Price.get();
		}



	}

	public static class CategoryInfo{
		private SimpleStringProperty categoryId;
		private SimpleStringProperty categoryName;
		private SimpleStringProperty parentId;
		private SimpleIntegerProperty type;



		public CategoryInfo(String categoryId,String categoryName,String parentId,int type) {
			this.categoryId = new SimpleStringProperty(categoryId);

			this.categoryName = new SimpleStringProperty(categoryName);
			this.parentId = new SimpleStringProperty(parentId);
			this.type = new SimpleIntegerProperty(type);



		}

		public String getCategoryId() {
			return categoryId.get();
		}

		public void setCategoryId(String categoryId) {
			this.categoryId = new SimpleStringProperty(categoryId);
		}

		public String getCategoryName() {
			return categoryName.get();
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = new SimpleStringProperty(categoryName);
		}
		public String getParentId() {
			return parentId.get();
		}

		public void setParentId(String parentId) {
			this.parentId = new SimpleStringProperty(parentId);
		}

		public int getType() {
			return type.get();
		}

		public void setType(int type) {
			this.type = new SimpleIntegerProperty(type);
		}


	}

	public static class AccountsHeadInfo{
		private SimpleStringProperty headid;
		private SimpleStringProperty headTitle;
		private SimpleStringProperty parentHeadId;
		private SimpleStringProperty UnitId;
		private SimpleStringProperty DepId;
		private SimpleIntegerProperty type;
		private SimpleIntegerProperty isFixed;

		public AccountsHeadInfo(String headid,String headTitle,String parentHeadId,String UnitId,String DepId,int type,int isFixed) {
			this.headid = new SimpleStringProperty(headid);
			this.headTitle = new SimpleStringProperty(headTitle);
			this.parentHeadId = new SimpleStringProperty(parentHeadId);
			this.UnitId = new SimpleStringProperty(UnitId);
			this.DepId = new SimpleStringProperty(DepId);
			this.type = new SimpleIntegerProperty(type);
			this.isFixed = new SimpleIntegerProperty(isFixed);
		}

		public String getHeadid() {
			return headid.get();
		}

		public void setHeadid(String headid) {
			this.headid = new SimpleStringProperty(headid);
		}

		public String getHeadTitle() {
			return headTitle.get();
		}

		public void setHeadTitle(String headTitle) {
			this.headTitle = new SimpleStringProperty(headTitle);
		}

		public String getParentHeadId() {
			return parentHeadId.get();
		}

		public void setParentHeadId(String parentHeadId) {
			this.parentHeadId = new SimpleStringProperty(parentHeadId);
		}

		public String getUnitId() {
			return UnitId.get();
		}

		public void setUnitId(String unitId) {
			UnitId = new SimpleStringProperty(unitId);
		}

		public String getDepId() {
			return DepId.get();
		}

		public void setDepId(String depId) {
			DepId = new SimpleStringProperty(depId);
		}

		public int getType() {
			return type.get();
		}

		public void setType(int type) {
			this.type = new SimpleIntegerProperty(type);
		}

		public int getIsFixed() {
			return isFixed.get();
		}

		public void setIsFixed(int isFixed) {
			this.isFixed =new SimpleIntegerProperty(isFixed);
		}



	}

}

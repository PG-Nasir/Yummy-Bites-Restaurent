package ui.settings;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import shareClasses.AlertMaker;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.Notification;
import shareClasses.SessionBeam;



public class UserAuthenticationController implements Initializable{

	@FXML
	ComboBox cmbModuleName;
	FxComboBox cmbUserName = new FxComboBox<>();
	
	@FXML
	Button btnConfirm;
	@FXML
	Button btnRefresh;
	
	@FXML
	CheckBox checkAll;
	@FXML
	CheckBox checkInserAll;
	@FXML
	CheckBox checkEditAll;
	@FXML
	CheckBox checkDeleteAll;
	@FXML
	CheckBox checkBlockAll;
	
	@FXML
	HBox hBox;
	
	@FXML
	private TableView<ModuleItems> table;

	ObservableList<ModuleItems> list = FXCollections.observableArrayList();

	@FXML
	private TableColumn<ModuleItems, String> idCol;
	@FXML
	private TableColumn<ModuleItems, String> moduleNameCol;
	@FXML
	private TableColumn<ModuleItems, String> moduleItemNameCol;
	@FXML
	private TableColumn<ModuleItems, CheckBox> checkInsertCol;
	@FXML
	private TableColumn<ModuleItems, CheckBox> checkEditCol;
	@FXML
	private TableColumn<ModuleItems, CheckBox> checkDeleteCol;
	@FXML
	private TableColumn<ModuleItems, CheckBox> checkBlockCol;

	private DatabaseHandler databaseHandler;
	String sql;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		addCmp();
		setCmpData();
		focusMoveAction();
		setCmpAction();
		setCmpFocusAction();
		btnRefreshAction(null);
	}
	
	
	@FXML
	private void btnConfirmAction(ActionEvent event) {
		try {
			if(isUserExist(getCmbUserName())) {
				if(confrimationCheck()) {
					sql = "delete from tbUserAuthentication";
					databaseHandler.execAction(sql);
					
					String userId = getUserId(getCmbUserName());
					String userName = getCmbUserName();
					for(int i = 0 ;i<list.size();i++) {
						ModuleItems tempAM = list.get(i);
						sql = "insert into tbUserAuthentication (moduleId,userId,username,moduleName,moduleItemName,checkInsert,checkEdit,checkDelete,checkBlock,entryTime,createBy) \r\n" + 
								"values('"+tempAM.getId()+"','"+userId+"','"+userName+"','"+tempAM.getModuleName()+"','"+tempAM.getModuleItemName()+"','"+tempAM.isCheckInsertSelected()+"','"+tempAM.isCheckEditSelected()+"','"+tempAM.isCheckDeleteSelected()+"','"+tempAM.isCheckBlockSelected()+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
						databaseHandler.execAction(sql);
						
						
					}
					new Notification(Pos.TOP_CENTER, "Information graphic","Save Successfully.....!","User Authentication Save Successfully....");
				}
				
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic","User Invalid!.....!","Select a valid User....");
				cmbUserName.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	@FXML
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub
		loadUserDisplayName();
		setCmbModuleName("");
		setCmbUserName("");
		setCheckAll(false);
		list.clear();
		table.setItems(list);
	}
	
	private String getUserId(String username) {
		// TODO Auto-generated method stub
		try {
			sql = "select user_id from tblogin where displayName = '"+username+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("user_id");
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	private boolean confrimationCheck() {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are You Sure to Save this Autehntication for user "+getCmbUserName()+"?");
	}
	private boolean isUserExist(String username) {
		// TODO Auto-generated method stub
		try {
			sql = "select user_id from tblogin where displayName = '"+username+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	protected void loadModuleItemNameByUserId(String userId) {
		// TODO Auto-generated method stub
		try {
			sql = "select * from tbUserAuthentication where userId = '"+userId+"' order by moduleId";
			ResultSet rs = databaseHandler.execQuery(sql);
			list.clear();
			while(rs.next()) {
				list.add(new ModuleItems(rs.getInt("ModuleId"), rs.getString("ModuleName"), rs.getString("ModuleItemName"), rs.getInt("checkInsert")==1?true:false, rs.getInt("checkEdit")==1?true:false, rs.getInt("checkDelete")==1?true:false, rs.getInt("checkBlock")==1?true:false));
			}
			
			table.setItems(list);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void loadUserDisplayName() {
		// TODO Auto-generated method stub
		try {
			sql = "select isnull(displayName,'') as displayname from tblogin where userType != 'Super Admin' order by username ";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbUserName.data.clear();
			while(rs.next()) {
				cmbUserName.data.add(rs.getString("displayName"));
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbUserName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbUserName);
		});
		
		cmbModuleName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbModuleName);
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
		
		cmbModuleName.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					
					int i;
					for(i = 0;i<list.size();i++) {
						if(list.get(i).getModuleName().equals(newValue)) {
							break;
						}
					}
					if(i == list.size()) {
						switch(newValue){
						case "Setup":
							list.addAll(new ModuleItems(1, "Setup", "Supplier Create", true, true, true, false),
									new ModuleItems(2, "Setup", "Customer Create", true, true, true, false),
									new ModuleItems(3, "Setup", "Category And Item Create", true, true, true, false),
									new ModuleItems(4, "Setup", "Item Stock Adjustment", true, true, true, false),
									new ModuleItems(5, "Setup", "Bar-Code Generator", true, true, true, false),
									new ModuleItems(6, "Setup", "Organization Information Set", true, true, true, false));
							
								break;
						case "Trading":
							list.addAll(new ModuleItems(7, "Trading", "Billing", true, true, true, false),
									new ModuleItems(8, "Trading", "Purchase", true, true, true, false),
									new ModuleItems(9, "Trading", "Purchase Return", true, true, true, false),
									new ModuleItems(10, "Trading", "Kitchen Issue", true, true, true, false),
									new ModuleItems(11, "Trading", "Kitchen Return", true, true, true, false));
									//new ModuleItems(12, "Trading", "Warrenty And Repaire", true, true, true, false));
							
							break;
						case "Accounts":
							list.addAll(new ModuleItems(13, "Accounts", "Cash Payment", true, true, true, false),
									new ModuleItems(14, "Accounts", "Cash Recived", true, true, true, false),
									new ModuleItems(15, "Accounts", "Bank Payment", true, true, true, false),
									new ModuleItems(16, "Accounts", "Bank Recived", true, true, true, false),
									new ModuleItems(17, "Accounts", "Head And Ledger create", true, true, true, false));
							
							break;
						case "Reports":
							list.addAll(new ModuleItems(18, "Reports", "Trading Reports", true, true, true, false),
									new ModuleItems(19, "Reports", "Accounts Reports", true, true, true, false));
							
							break;
						case "Settings":
							list.addAll(new ModuleItems(20, "Settings", "User Create", true, true, true, false),
									new ModuleItems(21, "Settings", "User Authentication", true, true, true, false),
									new ModuleItems(22, "Settings", "My Profile", true, true, true, false),
									new ModuleItems(23, "Settings", "LogOut", true, true, true, false));
							
							
							break;
						}
					}
					table.setItems(list);
				}
			}    
		});
		
		cmbUserName.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldValue, String newValue) {
				if(newValue != null) {
					
					loadModuleItemNameByUserId(getUserId(getCmbUserName()));
				}
			}    
		});
		
		checkAll.setOnAction(e->{
			if(getCheckAll()) {
				
				list.addAll(new ModuleItems(1, "Setup", "Supplier Create", true, true, true, false),
						new ModuleItems(2, "Setup", "Customer Create", true, true, true, false),
						new ModuleItems(3, "Setup", "Category And Item Create", true, true, true, false),
						new ModuleItems(4, "Setup", "Item Stock Adjustment", true, true, true, false),
						new ModuleItems(5, "Setup", "Bar-Code Generator", true, true, true, false),
						new ModuleItems(6, "Setup", "Organization Information Set", true, true, true, false));
				
				list.addAll(new ModuleItems(7, "Trading", "Purchase", true, true, true, false),
						new ModuleItems(8, "Trading", "Purchase Return", true, true, true, false),
						new ModuleItems(9, "Trading", "Billing", true, true, true, false));
				
				list.addAll(new ModuleItems(13, "Accounts", "Cash Payment", true, true, true, false),
						new ModuleItems(14, "Accounts", "Cash Recived", true, true, true, false),
						new ModuleItems(15, "Accounts", "Bank Payment", true, true, true, false),
						new ModuleItems(16, "Accounts", "Bank Recived", true, true, true, false),
						new ModuleItems(17, "Accounts", "Head And Ledger create", true, true, true, false));
				
				list.addAll(new ModuleItems(18, "Reports", "Trading Reports", true, true, true, false),
						new ModuleItems(19, "Reports", "Accounts Reports", true, true, true, false));
				
				list.addAll(new ModuleItems(20, "Settings", "User Create", true, true, true, false),
						new ModuleItems(21, "Settings", "User Authentication", true, true, true, false),
						new ModuleItems(22, "Settings", "My Profile", true, true, true, false),
						new ModuleItems(23, "Settings", "LogOut", true, true, true, false));
				
				
				
			}else {
				list.clear();
				table.setItems(list);
			}
		});
		
		checkInserAll.setOnAction(e->{
			if(checkInserAll.isSelected()) {
				for(int i=0;i<list.size();i++) {
					list.get(i).setCheckInsert(true);

				}
			}else {
				for(int i=0;i<list.size();i++) {		
					list.get(i).setCheckInsert(false);
				}
			}
			table.setItems(list);
		});
		
		checkEditAll.setOnAction(e->{
			if(checkEditAll.isSelected()) {
				for(int i=0;i<list.size();i++) {
					list.get(i).setCheckEdit(true);

				}
			}else {
				for(int i=0;i<list.size();i++) {		
					list.get(i).setCheckEdit(false);
				}
			}
			table.setItems(list);
		});
		checkDeleteAll.setOnAction(e->{
			if(checkDeleteAll.isSelected()) {
				for(int i=0;i<list.size();i++) {
					list.get(i).setCheckDelete(true);

				}
			}else {
				for(int i=0;i<list.size();i++) {		
					list.get(i).setCheckDelete(false);
				}
			}
			table.setItems(list);
		});
		
		checkBlockAll.setOnAction(e->{
			if(checkBlockAll.isSelected()) {
				for(int i=0;i<list.size();i++) {
					list.get(i).setCheckBlock(true);

				}
			}else {
				for(int i=0;i<list.size();i++) {		
					list.get(i).setCheckBlock(false);
				}
			}
			table.setItems(list);
		});
	}

	

	private void focusMoveAction() {
		// TODO Auto-generated method stub
		Control[] control1 = {cmbUserName,cmbModuleName};
		new FocusMoveByEnter(control1);
	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		cmbModuleName.getItems().addAll("Setup","Trading","Accounts","Reports","Settings");
		
		table.setColumnResizePolicy(table.CONSTRAINED_RESIZE_POLICY);

		idCol.setCellValueFactory(new PropertyValueFactory("id"));
		moduleNameCol.setCellValueFactory(new PropertyValueFactory("moduleName"));
		moduleItemNameCol.setCellValueFactory(new PropertyValueFactory("moduleItemName"));
		checkInsertCol.setCellValueFactory(new PropertyValueFactory("checkInsert"));
		checkEditCol.setCellValueFactory(new PropertyValueFactory("checkEdit"));
		checkDeleteCol.setCellValueFactory(new PropertyValueFactory("checkDelete"));
		checkBlockCol.setCellValueFactory(new PropertyValueFactory("checkBlock"));
		
		checkInserAll = new CheckBox();
		checkInsertCol.setGraphic(checkInserAll);
		
		checkEditAll = new CheckBox();
		checkEditCol.setGraphic(checkEditAll);
		
		checkDeleteAll = new CheckBox();
		checkDeleteCol.setGraphic(checkDeleteAll);
		
		checkBlockAll = new CheckBox();
		checkBlockCol.setGraphic(checkBlockAll);

	}

	private void addCmp() {
		// TODO Auto-generated method stub
		cmbUserName.setPrefWidth(200);
		cmbUserName.setPrefHeight(28);
		hBox.getChildren().remove(0);
		hBox.getChildren().add(0,cmbUserName);
	}

	public class ModuleItems{

		private SimpleIntegerProperty id;
		private SimpleStringProperty moduleName;
		private SimpleStringProperty moduleItemName;
		private CheckBox checkInsert;
		private CheckBox checkEdit;
		private CheckBox checkDelete;
		private CheckBox checkBlock;

		public ModuleItems(int id,String modulename,String moduleItemName,boolean checkInsert,boolean checkEdit,boolean checkDelete,boolean checkBlock) {
			this.id = new SimpleIntegerProperty(id);
			this.moduleName = new SimpleStringProperty(modulename);
			this.moduleItemName = new SimpleStringProperty(moduleItemName);
			this.checkInsert = new CheckBox();
			this.checkInsert.setSelected(checkInsert);;
			//this.checkInsert.setDisable(!checkInsert);
			this.checkEdit = new CheckBox();
			this.checkEdit.setSelected(checkEdit);;
			//this.checkEdit.setDisable(!checkEdit);
			this.checkDelete = new CheckBox();
			this.checkDelete.setSelected(checkDelete);;
			//this.checkDelete.setDisable(!checkDelete);
			this.checkBlock = new CheckBox();
			this.checkBlock.setSelected(checkBlock);;
			//this.checkBlock.setDisable(!checkBlock);
			
			
		}

		public int getId() {
			return id.get();
		}

		public void setId(int id) {
			this.id = new SimpleIntegerProperty(id);
		}

		public String getModuleName() {
			return moduleName.get();
		}

		public void setModuleName(String moduleName) {
			this.moduleName = new SimpleStringProperty(moduleName);
		}
		

		public String getModuleItemName() {
			return moduleItemName.get();
		}

		public void setModuleItemName(String moduleItemName) {
			this.moduleItemName = new SimpleStringProperty(moduleItemName);
		}
		
		public int isCheckInsertSelected() {
			return checkInsert.isSelected()?1:0;
		}

		public CheckBox getCheckInsert() {
			return checkInsert;
		}

		public void setCheckInsert(boolean checkInsert) {
			this.checkInsert.setSelected(checkInsert);
		}
		
		public int isCheckEditSelected() {
			return checkEdit.isSelected()?1:0;
		}
		
		public CheckBox getCheckEdit() {
			return checkEdit;
		}
		
		
		public void setCheckEdit(boolean checkEdit) {
			this.checkEdit.setSelected(checkEdit);
		}
		
		public int isCheckDeleteSelected() {
			return checkDelete.isSelected()?1:0;
		}

		public CheckBox getCheckDelete() {
			return checkDelete;
		}
		

		public void setCheckDelete(boolean checkDelete) {
			this.checkDelete.setSelected(checkDelete);
		}

		public CheckBox getCheckBlock() {
			return checkBlock;
		}
		
		public int isCheckBlockSelected() {
			return checkBlock.isSelected()?1:0;
		}

		public void setCheckBlock(boolean checkBlock) {
			this.checkBlock.setSelected(checkBlock);
		}

		


	}

	public String getCmbModuleName() {
		if(cmbModuleName.getValue() != null)
		return cmbModuleName.getValue().toString().trim();
		
		return "";
	}

	public void setCmbModuleName(String cmbModuleName) {
		this.cmbModuleName.setValue(cmbModuleName);
	}

	public String getCmbUserName() {
		if(cmbUserName.getValue() != null)
		return cmbUserName.getValue().toString().trim();
		
		return "";
	}

	public void setCmbUserName(String cmbUserName) {
		this.cmbUserName.setValue(cmbUserName);
	}

	public boolean getCheckAll() {
		return checkAll.isSelected();
	}

	public void setCheckAll(boolean checkAll) {
		this.checkAll.setSelected(checkAll);
	}
	
	

}

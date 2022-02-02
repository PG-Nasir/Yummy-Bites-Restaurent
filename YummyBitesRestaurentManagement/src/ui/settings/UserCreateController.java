package ui.settings;


import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;



import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import shareClasses.AlertMaker;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.Notification;
import shareClasses.SessionBeam;


public class UserCreateController implements Initializable{


	@FXML
	TextField txtUserId;
	@FXML
	TextField txtName;
	@FXML
	TextField txtDisplayName;
	@FXML
	TextField txtUserName;
	@FXML
	PasswordField txtPassword;
	@FXML
	PasswordField txtConfirmPassword;

	@FXML
	ComboBox cmbDepartment;
	@FXML
	ComboBox cmbUserType;
	@FXML
	ComboBox cmbActiveStatus;

	@FXML
	Button btnSave;
	@FXML
	Button btnEdit;
	@FXML
	Button btnRefresh;
	@FXML
	Button btnFind;

	CheckBox checkSelectAll;

	@FXML
	HBox hBox;

	FxComboBox cmbFind = new FxComboBox<>();


	@FXML
	private TableView<AccessModules> table;

	ObservableList<AccessModules> list = FXCollections.observableArrayList();

	@FXML
	private TableColumn<AccessModules, String> idCol;
	@FXML
	private TableColumn<AccessModules, String> moduleNameCol;
	@FXML
	private TableColumn<AccessModules, CheckBox> checkCol;

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
		loadUserDisplayName();
		btnRefreshAction(null);
	}

	@FXML
	private void btnSaveAction(ActionEvent event) {
		try {
			if(duplicateIdCheck()) {
				if(validationCheck()) {
					if(duplicateUserNameCheck()) {
						if(confrimationCheck("Save")) {
							sql = "insert into tblogin(user_Id,name,displayname,Department,userType,username,password,activestatus,entrytime,CreateBy) "
									+ "values('"+getTxtUserId()+"','"+getTxtName()+"','"+getTxtDisplayName()+"','"+getCmbDepartment()+"','"+getCmbUserType()+"','"+getTxtUserName()+"','"+getTxtPassword()+"','"+getCmbActiveStatus()+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
							databaseHandler.execAction(sql);

							for(int i = 0;i<list.size();i++) {
								AccessModules tempAM = list.get(i);
								if(tempAM.getIsCheckSelect()) {
									sql = "insert into tbloginDetails (userid,Department,userType,moduleId,moduleName,status,entrytime,createby) "
											+ "values('"+getTxtUserId()+"','"+getCmbDepartment()+"','"+getCmbUserType()+"','"+tempAM.getId()+"','"+tempAM.getModuleName()+"','"+(tempAM.getIsCheckSelect()?1:0)+"',current_timestamp,'"+SessionBeam.getUserId()+"');";
									databaseHandler.execAction(sql);
								}

							}
							loadUserDisplayName();
							new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully..!","User Save Successfully...");
						}

					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "User ID All Ready exist..!","You Can Edit This User. \nClick Refresh for create new User....");
				btnRefresh.requestFocus();
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}

	}

	@FXML
	private void btnEditAction(ActionEvent event) {
		try {
			if(!duplicateIdCheck()) {
				if(validationCheck()) {
					if(duplicateDisplayNameCheck()) {
						if(duplicateUserNameCheck()) {
							if(confrimationCheck("Edit")) {
								sql = "update tblogin set name='"+getTxtName()+"',displayName='"+getTxtDisplayName()+"',Department='"+getCmbDepartment()+"',userType='"+getCmbUserType()+"',username = '"+getTxtUserName()+"',password='"+getTxtPassword()+"',activestatus='"+getCmbActiveStatus()+"',entrytime=CURRENT_TIMESTAMP,CreateBy='"+SessionBeam.getUserId()+"' where user_id = '"+getTxtUserId()+"'";		
								databaseHandler.execAction(sql);


								sql = "delete from tblogindetails where userid = '"+getTxtUserId()+"'";
								databaseHandler.execAction(sql);
								for(int i = 0;i<list.size();i++) {
									AccessModules tempAM = list.get(i);
									if(tempAM.getIsCheckSelect()) {
										sql = "insert into tbloginDetails (userid,Department,userType,moduleId,moduleName,status,entrytime,createby) "
												+ "values('"+getTxtUserId()+"','"+getCmbDepartment()+"','"+getCmbUserType()+"','"+tempAM.getId()+"','"+tempAM.getModuleName()+"','"+(tempAM.getIsCheckSelect()?1:0)+"',current_timestamp,'"+SessionBeam.getUserId()+"');";
										databaseHandler.execAction(sql);
									}

								}
								loadUserDisplayName();
								new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully..!","User Edit Successfully...");
								
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "User Name All ready exist..!","This user name all ready exist. \nPlease Change this user name...");
							txtUserName.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Display Name All ready exist..!","This Display name all ready exist. \nPlease Change this Display name...");
						txtDisplayName.requestFocus();
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "User ID Not exist..!","This user id not exist. \nYou can save this user as new user...");
				btnSave.requestFocus();
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		}
	}

	@FXML
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub
		setTxtUserId(getMaxUserId());
		setTxtName("");
		setTxtUserName("");
		setTxtDisplayName("");
		setTxtPassword("");
		setTxtConfirmPassword("");
		setCmbDepartment("");
		setCmbActiveStatus("");
		setCmbUserType("");

		list.clear();

		list.addAll(new AccessModules(1, "Setup", false),
				new AccessModules(2, "Restaurent Trading", false),
				new AccessModules(3, "Accounts", false),
				new AccessModules(4, "Reports", false),
				new AccessModules(5, "Settings", false));


		table.setItems(list);


	}

	@FXML
	private void btnFindAction(ActionEvent event) {
		try {
			
			if(!getCmbFind().isEmpty()) {
				
				btnRefreshAction(null);
				
				sql = "select * from tblogin where displayname = '"+getCmbFind()+"'";
				ResultSet rs = databaseHandler.execQuery(sql);
				if(rs.next()) {
					setTxtUserId(rs.getString("user_id"));
					setTxtName(rs.getString("name"));
					setTxtDisplayName(rs.getString("displayname"));
					setCmbDepartment(rs.getString("department"));
					setCmbUserType(rs.getString("userType"));
					setTxtUserName(rs.getString("username"));
					setTxtPassword(rs.getString("password"));
					setTxtConfirmPassword(rs.getString("password"));
					setCmbActiveStatus(rs.getString("activestatus"));
				}
				
				sql = "select * from tbloginDetails where userid = '"+getTxtUserId()+"'";
				rs = databaseHandler.execQuery(sql);
				
				while(rs.next()) {
					for(int i =0 ;i<list.size();i++) {
						if(list.get(i).getId() == rs.getInt("moduleId")) {
							list.get(i).setCheck((rs.getInt("status")==1?true:false));
							break;
						}
						
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select a user..!","Select User For Find....");
				cmbFind.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private boolean validationCheck() {
		if(!getTxtUserId().isEmpty()) {
			if(!getTxtName().isEmpty()) {
				if(!getTxtDisplayName().isEmpty()) {
				if(!getCmbDepartment().isEmpty()) {
					if(!getCmbUserType().isEmpty()) {
						if(!getTxtUserName().isEmpty()) {
							if(!getTxtPassword().isEmpty()) {
								if(!getTxtConfirmPassword().isEmpty()) {
									if(!getCmbActiveStatus().isEmpty()) {
										if(getTxtPassword().equals(getTxtConfirmPassword())) {
											int i;
											for(i = 0;i<list.size();i++) {
												if(list.get(i).getIsCheckSelect())
													break;
											}
											if(i != list.size()) {
												return true;
											}else {
												new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Access Module..!","Please Select Any Access Module....");
												table.requestFocus();
											}
										}else {
											new Notification(Pos.TOP_CENTER, "Warning graphic", "Incorrect, Confirm Password..!","Please Confirm your password....");
											txtConfirmPassword.requestFocus();
										}
									}else {
										new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Active Status..!","Please Select Active Status....");
										cmbActiveStatus.requestFocus();
									}
								}else {
									new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter Confirm Password..!","Please Enter Confirm Password....");
									txtConfirmPassword.requestFocus();
								}
							}else {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter Password..!","Please Enter Password....");
								txtPassword.requestFocus();
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter User Name..!","Please Enter User Name....");
							txtUserName.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Select User Type..!","Please Select User Type....");
						cmbUserType.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Select Department..!","Please Select any Department....");
					cmbDepartment.requestFocus();
				}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter Display name..!","Please Enter Display Name....");
					txtDisplayName.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter Name..!","Please Enter Name....");
				txtName.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "User Id is empty..!","Please Enter User Id.....");
			txtUserId.requestFocus();
		}
		return false;
	}

	private boolean duplicateUserNameCheck() {
		try {
			sql = "select username from tblogin where user_Id != '"+getTxtUserId()+"' and username = '"+getTxtUserName()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}

		return true;
	}

	private boolean duplicateDisplayNameCheck() {
		try {
			sql = "select username from tblogin where user_Id != '"+getTxtUserId()+"' and displayName = '"+getTxtDisplayName()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}

		return true;
	}

	private boolean duplicateIdCheck() {
		try {
			sql = "select username from tblogin where user_Id = '"+getTxtUserId()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}

		return true;
	}
	private boolean confrimationCheck(String name) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are You Sure to "+name+" this User?");
	}

	private String getMaxUserId() {
		// TODO Auto-generated method stub
		try {
			sql = "select isnull(max(user_Id),0)+1 maxUserId from tblogin";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("maxUserId");
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}

	private void loadUserDisplayName() {
		// TODO Auto-generated method stub
		try {
			sql = "select isnull(displayName,'') as displayname from tblogin where userType != 'Super Admin' order by username ";
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbFind.data.clear();
			while(rs.next()) {
				cmbFind.data.add(rs.getString("displayName"));
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void setCmpAction() {
		// TODO Auto-generated method stub

		checkSelectAll.setOnAction(e->{
			if(checkSelectAll.isSelected()) {
				for(int i=0;i<list.size();i++) {
					list.get(i).setCheck(true);

				}
			}else {
				for(int i=0;i<list.size();i++) {
					AccessModules tempAM = list.get(i);
					tempAM.setCheck(false);
				}
			}
			table.setItems(list);
		});
	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbFind.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbFind);
		});

		txtName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtName);
		});

		txtDisplayName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtDisplayName);
		});

		txtUserName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtUserName);
		});

		txtPassword.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtPassword);
		});

		txtConfirmPassword.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtConfirmPassword);
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
		Control[] control =  {txtName,txtDisplayName,cmbDepartment,cmbUserType,txtUserName,txtPassword,txtConfirmPassword,cmbActiveStatus,btnSave};
		new FocusMoveByEnter(control);

		Control[] control1 = {cmbFind,btnFind};
		new FocusMoveByEnter(control1);
	}

	private void setCmpData() {
		// TODO Auto-generated method stub

		table.setColumnResizePolicy(table.CONSTRAINED_RESIZE_POLICY);

		idCol.setCellValueFactory(new PropertyValueFactory("id"));
		moduleNameCol.setCellValueFactory(new PropertyValueFactory("moduleName"));
		checkCol.setCellValueFactory(new PropertyValueFactory<AccessModules,CheckBox>("check"));

		//checkCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkCol));

		checkSelectAll = new CheckBox("All");
		checkCol.setGraphic(checkSelectAll);

		cmbDepartment.getItems().addAll("","Sales","Accounts");
		cmbUserType.getItems().addAll("","Super Admin","Admin","General User");
		cmbActiveStatus.getItems().addAll("","Active","Deactive");

	}

	private void addCmp() {
		// TODO Auto-generated method stub

		cmbFind.setPrefWidth(216);
		cmbFind.setPrefHeight(28);
		hBox.getChildren().remove(1);
		hBox.getChildren().add(1,cmbFind);


	}

	public class AccessModules{

		private SimpleIntegerProperty id;
		private SimpleStringProperty moduleName;
		private CheckBox check;

		public AccessModules(int id,String modulename,boolean check) {
			this.id = new SimpleIntegerProperty(id);
			this.moduleName = new SimpleStringProperty(modulename);
			this.check = new CheckBox();
			this.check.setSelected(check);
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

		public CheckBox getCheck() {
			return check;
		}

		public boolean getIsCheckSelect() {
			return check.isSelected();
		}
		public void setCheck(boolean check) {
			this.check.setSelected(check);
		}


	}

	public String getTxtUserId() {
		return txtUserId.getText().trim();
	}

	public void setTxtUserId(String txtUserId) {
		this.txtUserId.setText(txtUserId);
	}

	public String getTxtName() {
		return txtName.getText().trim();
	}

	public void setTxtName(String txtName) {
		this.txtName.setText(txtName);
	}

	public String getTxtDisplayName() {
		return txtDisplayName.getText().trim();
	}

	public void setTxtDisplayName(String txtDisplayName) {
		this.txtDisplayName.setText(txtDisplayName);
	}

	public String getTxtUserName() {
		return txtUserName.getText().trim();
	}

	public void setTxtUserName(String txtUserName) {
		this.txtUserName.setText(txtUserName);
	}

	public String getTxtPassword() {
		return txtPassword.getText().trim();
	}

	public void setTxtPassword(String txtPassword) {
		this.txtPassword.setText(txtPassword);
	}

	public String getTxtConfirmPassword() {
		return txtConfirmPassword.getText().trim();
	}

	public void setTxtConfirmPassword(String txtConfirmPassword) {
		this.txtConfirmPassword.setText(txtConfirmPassword);
	}

	public String getCmbDepartment() {
		if(cmbDepartment.getValue() != null) {
			return cmbDepartment.getValue().toString().trim();
		}
		return "";
	}

	public void setCmbDepartment(String cmbDepartment) {
		this.cmbDepartment.setValue(cmbDepartment);
	}

	public String getCmbUserType() {
		if(cmbUserType.getValue() != null) {
			return cmbUserType.getValue().toString().trim();
		}
		return "";
	}

	public void setCmbUserType(String cmbUserType) {
		this.cmbUserType.setValue(cmbUserType);
	}

	public String getCmbActiveStatus() {
		if(cmbActiveStatus.getValue() != null) {
			return cmbActiveStatus.getValue().toString().trim();
		}
		return "";
	}

	public void setCmbActiveStatus(String cmbActiveStatus) {
		this.cmbActiveStatus.setValue(cmbActiveStatus);
	}

	public String getCmbFind() {
		if(cmbFind.getValue() != null) {
			return cmbFind.getValue().toString().trim();
		}
		return "";
	}

	public void setCmbFind(String cmbFind) {
		this.cmbFind.setValue(cmbFind);
	}



}

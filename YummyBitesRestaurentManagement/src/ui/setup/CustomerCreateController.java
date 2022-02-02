package ui.setup;

import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import shareClasses.AlertMaker;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.Notification;
import shareClasses.SessionBeam;
import ui.setup.CustomerCreateController.CustomerTableData;

public class CustomerCreateController implements Initializable{

	@FXML
	TextField txtId;
	@FXML
	TextField txtCustomerName;
	@FXML
	TextField txtContuctNo;
	@FXML
	TextField txtEmail;
	@FXML
	TextArea txtAddress;
	@FXML
	TextArea txtRemarks;
	@FXML
	TextField txtOpeningBalannce;

	@FXML
	Button btnSave;
	@FXML
	Button btnEdit;
	@FXML
	Button btnRefresh;
	@FXML
	Button btnFind;


	@FXML
	VBox vBoxFind;

	FxComboBox cmbFind;	

	@FXML
	private TableView<CustomerTableData> table;

	ObservableList<CustomerTableData> list = FXCollections.observableArrayList();

	@FXML
	private TableColumn<CustomerTableData, String> idCol;
	@FXML
	private TableColumn<CustomerTableData, String> CustomerNameCol;
	@FXML
	private TableColumn<CustomerTableData, String> contactNoCol;
	@FXML
	private TableColumn<CustomerTableData, String> addressCol;
	
	private DecimalFormat df = new DecimalFormat("#0.00");

	private DatabaseHandler databaseHandler;
	private String sql;
	private String ledgerType="2";
	private String ledgerHeadId="";


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		addCmp();
		setCmpData();
		setCmpFocusAction();
		focusMoveAction();
		setLedgerHeadId();
		btnRefreshAction(null);
	}

	@FXML
	private void btnFindActon(ActionEvent event) {
		try {
			if(!getCmbFind().isEmpty()) {
				findCustomer(getCustomerId(getCmbFind()));
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select a Customer Name....");
				cmbFind.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}



	@FXML
	private void btnRefreshAction(ActionEvent event) {
		setTxtId(getMaxCustomerId());
		setTxtCustomerName("");
		setTxtContuctNo("");
		setTxtEmail("");
		setTxtAddress("");
		setTxtRemarks("");
		setTxtOpeningBalannce("");
		loadCustomer();
	}

	@FXML
	private void btnEditAction(ActionEvent event) {
		try {
			if(isIdExist()) {
				if(validationCheck()) {
					if(!duplicateSuppierCheck(getTxtCustomerName(),getTxtId())) {
						if(confrimationCheck("Edit")) {
							String sql="update tbCustomer set CustomerName='"+getTxtCustomerName()+"',Mobile='"+getTxtContuctNo()+"',email= '"+getTxtEmail()+"',address = '"+getTxtAddress()+"',remarks='"+getTxtRemarks()+"',openingBalance='"+getTxtOpeningBalannce()+"' where ID = '"+getTxtId()+"';";

							String ledgerSql="update tbAccfledger set ledgerTitle = '"+getTxtCustomerName()+" (C)"+"' ,openingBalance = '"+getTxtOpeningBalannce()+"',remark='"+getTxtRemarks()+"',createBy='"+SessionBeam.getUserId()+"' where ledgerId='"+getLedgerId(getTxtId())+"' and Type = '"+ledgerType+"'";
							if(databaseHandler.execAction(sql) && databaseHandler.execAction(ledgerSql)) {
								new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully..","Customer Edit Successfully.....");
								btnRefresh.requestFocus();
								loadCustomer();
							}else {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Save Can Not Successfull..!","Something went wrong ...\nPlease Try Again.....");
								btnEdit.requestFocus();
							}
						}

					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Customer Name Allready Exist ...\nPlease Enter Another Name.....");
						txtCustomerName.requestFocus();
					}
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Customer Id is not Exist...\nPlease Enter Another Name.....");
				btnSave.requestFocus();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void btnSaveAction(ActionEvent event) {
		try {
			if(validationCheck()) {
				if(!duplicateSuppierCheck(getTxtCustomerName(),getMaxCustomerId())) {
					if(confrimationCheck("Save")) {
						String sql="insert into tbCustomer (ID,CustomerName,Mobile,Phone,email,address,remarks,openingBalance,ledgerId,entryTime,entryBy) values('"+getMaxCustomerId()+"','"+getTxtCustomerName()+"', '"+getTxtContuctNo()+"','','"+getTxtEmail()+"','"+getTxtAddress()+"','"+getTxtRemarks()+"','"+getTxtOpeningBalannce()+"','"+getMaxLedgerId()+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"') ;";
						
						String ledgerSql="insert into tbAccfledger (unitId,depId,ledgerId,ledgerTitle,pheadId,Type,isFixed,openingBalance,isBank,accountNo,accountType,Branch,address,date,remark,entryTime,createBy) " + 
								" values('1','1','"+getMaxLedgerId()+"','"+getTxtCustomerName()+" (C)"+"','"+ledgerHeadId+"','"+ledgerType+"','1','"+getTxtOpeningBalannce()+"','0','','','','','"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"','"+getTxtRemarks()+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"'); ";
						
						if(databaseHandler.execAction(sql) && databaseHandler.execAction(ledgerSql)) {
							new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfully..","Customer Save Successfully.....");
							txtCustomerName.requestFocus();
							btnRefreshAction(null);
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Save Can Not Successfull..!","Something went wrong ...\nPlease Try Again.....");
							btnSave.requestFocus();
						}
					}

				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Customer Name Allready Exist ...\nPlease Enter Another Name.....");
					txtCustomerName.requestFocus();
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void tableClickAction() {
		try {
			if(table.getSelectionModel().getSelectedItem() != null) {
				TablePosition firstCell = table.getSelectionModel().getSelectedCells().get(0);
				CustomerTableData tempSuppllier = table.getSelectionModel().getSelectedItem();
				if(firstCell.getRow()<list.size()) {
					findCustomer(tempSuppllier.getId());
				}

			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void findCustomer(String CustomerId) {
		try {
			sql = "select * from tbCustomer where id = '"+CustomerId+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtId(rs.getString("id"));
				setTxtCustomerName(rs.getString("CustomerName"));
				setTxtContuctNo(rs.getString("Mobile"));
				setTxtEmail(rs.getString("Email"));
				setTxtAddress(rs.getString("Address"));
				setTxtRemarks(rs.getString("Remarks"));
				setTxtOpeningBalannce(df.format(rs.getDouble("openingBalance")));
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Infromation!","Your Customer Name is in valid.....");
				cmbFind.requestFocus();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private boolean confrimationCheck(String name) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are You Sure to "+name+" this Customer?");
	}

	private int getMaxLedgerId(){
		try {
			sql="select (isnull(max(ledgerid),0)+1)as autoID from tbAccfledger";

			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()){
				return rs.getInt("autoId");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private int getLedgerId(String id){
		try {
			sql="select ledgerid from tbCustomer where ID = '"+id+"';";

			ResultSet rs=databaseHandler.execQuery(sql);
			if(rs.next()){
				return rs.getInt("ledgerid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private boolean isIdExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select * from tbCustomer where id = '"+getTxtId()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
		return false;
	}

	private String getCustomerId(String CustomerName) {
		try {
			sql = "select id from tbCustomer where CustomerName='"+CustomerName+"'";
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


	private boolean duplicateSuppierCheck(String name,String id) {
		try {
			sql = "select * from tbCustomer where CustomerName='"+name+"' and id != '"+id+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
		return false;
	}

	private boolean validationCheck() {
		if(!getTxtCustomerName().isEmpty()) {
			if(!getTxtOpeningBalannce().isEmpty()) {
				return true;
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Opening Balance.....");
				txtOpeningBalannce.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Customer Name.....");
			txtCustomerName.requestFocus();
		}
		return false;
	}


	private void loadCustomer() {
		// TODO Auto-generated method stub
		try {
			cmbFind.data.clear();
			list.clear();
			sql = "select id,CustomerName,Phone,address from tbCustomer";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbFind.data.add(rs.getString("CustomerName"));
				list.add(new CustomerTableData(rs.getString("id"), rs.getString("CustomerName"), rs.getString("Phone"), rs.getString("address")));
			}
			
			table.setItems(list);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void setLedgerHeadId() {
		try {
			sql = "select headid from tbAccfhead where headTitle = 'Customer'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				ledgerHeadId = rs.getString("headid");
			}else {
				ledgerHeadId = "0";
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private String getMaxCustomerId() {
		// TODO Auto-generated method stub
		try {
			sql = "select concat('C',isnull(max(cast(substring(id,2,len(id)-1) as int)),0)+1) as maxCustomerId from tbCustomer";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("maxCustomerId");
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}

	private void focusMoveAction() {
		Control[] control =  {txtId,txtCustomerName,txtContuctNo,txtEmail,txtAddress,txtRemarks,txtOpeningBalannce,btnSave};
		new FocusMoveByEnter(control);


	}
	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbFind.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbFind);
		});

		txtCustomerName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtCustomerName);
		});
		txtContuctNo.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtContuctNo);
		});
		txtEmail.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtEmail);
		});
		txtOpeningBalannce.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtOpeningBalannce);
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

		cmbFind.setPrefWidth(290);
		cmbFind.setPrefHeight(28);
		vBoxFind.getChildren().clear();
		vBoxFind.getChildren().add(cmbFind);
	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		//date.setConverter(converter);
		//date.setValue(LocalDate.now());

		table.setColumnResizePolicy(table.CONSTRAINED_RESIZE_POLICY);

		idCol.setCellValueFactory(new PropertyValueFactory("id"));
		CustomerNameCol.setCellValueFactory(new PropertyValueFactory("CustomerName"));
		contactNoCol.setCellValueFactory(new PropertyValueFactory("contactNo"));
		addressCol.setCellValueFactory(new PropertyValueFactory("address"));


		
		
	}
	public static class CustomerTableData{


		private SimpleStringProperty id;
		private SimpleStringProperty CustomerName;
		private SimpleStringProperty contactNo;
		private SimpleStringProperty address;



		public CustomerTableData(String id,String CustomerName,String contactNo,String address) {
			this.id = new SimpleStringProperty(id);
			this.CustomerName = new SimpleStringProperty(CustomerName);
			this.contactNo = new SimpleStringProperty(contactNo);
			this.address = new SimpleStringProperty(address);


		}



		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}

		public String getCustomerName() {
			return CustomerName.get();
		}

		public void setCustomerName(String CustomerName) {
			this.CustomerName = new SimpleStringProperty(CustomerName);
		}

		public String getContactNo() {
			return contactNo.get();
		}

		public void setContactNo(String contactNo) {
			this.contactNo = new SimpleStringProperty(contactNo);
		}

		public String getAddress() {
			return address.get();
		}

		public void setAddress(String address) {
			this.address = new SimpleStringProperty(address);
		}



	}
	public String getTxtId() {
		return txtId.getText().trim();
	}

	public void setTxtId(String txtId) {
		this.txtId.setText(txtId);
	}

	public String getTxtCustomerName() {
		return txtCustomerName.getText().trim();
	}

	public void setTxtCustomerName(String txtCustomerName) {
		this.txtCustomerName.setText(txtCustomerName);
	}

	public String getTxtContuctNo() {
		return txtContuctNo.getText().trim();
	}

	public void setTxtContuctNo(String txtContuctNo) {
		this.txtContuctNo.setText(txtContuctNo);
	}

	public String getTxtEmail() {
		return txtEmail.getText().trim();
	}

	public void setTxtEmail(String txtEmail) {
		this.txtEmail.setText(txtEmail);
	}

	public String getTxtAddress() {
		return txtAddress.getText().trim();
	}

	public void setTxtAddress(String txtAddress) {
		this.txtAddress.setText(txtAddress);
	}

	public String getTxtRemarks() {
		return txtRemarks.getText().trim();
	}

	public void setTxtRemarks(String txtRemarks) {
		this.txtRemarks.setText(txtRemarks);
	}

	public String getTxtOpeningBalannce() {
		return txtOpeningBalannce.getText().trim();
	}

	public void setTxtOpeningBalannce(String txtOpeningBalannce) {
		this.txtOpeningBalannce.setText(txtOpeningBalannce);
	}

	public String getCmbFind() {
		if(cmbFind.getValue() != null)
			return cmbFind.getValue().toString().trim();
		else return "";
	}

	public void setCmbFind(String cmbFind) {
		this.cmbFind.setValue(cmbFind);
	}



}

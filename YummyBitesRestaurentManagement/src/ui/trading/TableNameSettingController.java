package ui.trading;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import shareClasses.AlertMaker;
import shareClasses.Notification;
import shareClasses.SessionBeam;
import ui.trading.BillingTabController.ItemDetails;

public class TableNameSettingController implements Initializable{


	@FXML
	Button btnSave;
	@FXML
	Button btnEdit;
	@FXML
	Button btnDelete;

	@FXML
	TextField txtTableName;

	@FXML
	private TableView<TableName> table;

	ObservableList<TableName> list = FXCollections.observableArrayList();	
	@FXML
	private TableColumn<TableName, String> tableNameCol;


	private DatabaseHandler databaseHandler;
	private String sql;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		addCmp();
		focusMoveAction();
		setCmpData();
		tableLoad();
	}

	@FXML
	public void btnSaveAction(ActionEvent event) {
		try {
			if(!getTxtTableName().isEmpty()) {
				if(!isTableNameExist()) {
					if(confirmationCheck("Save")) {
						sql = "insert into tbtableinfo (tableName ,entryTime,userId) values('"+getTxtTableName()+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
						if(databaseHandler.execAction(sql)) {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Save Successfully....!","Information Save Successfully...");
							tableLoad();
						}
					}

				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Table Name Exist....!","This Table Name All ready Exist...");
					txtTableName.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Table Name....!","Please Enter Table Name...");
				txtTableName.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}




	@FXML
	public void btnEitAction(ActionEvent event) {
		try {
			if(table.getSelectionModel().getSelectedItem() != null) {
				TableName temp = table.getSelectionModel().getSelectedItem();
				if(!getTxtTableName().isEmpty()) {
					if(!isTableNameExist()) {
						if(confirmationCheck("Edit")) {
							sql = "update tbtableinfo set tableName = '"+getTxtTableName()+"' where autoId = "+temp.getTableId()+"";
							if(databaseHandler.execAction(sql)) {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Edit Successfully....!","Information Edit Successfully...");
								tableLoad();
							}
						}

					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Table Name Exist....!","This Table Name All ready Exist...");
						txtTableName.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Table Name....!","Please Enter Table Name...");
					txtTableName.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select Any Table Name....!","Please Select Any Table Name...");
				table.requestFocus();
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	public void btnDeleteAction(ActionEvent event) {
		try {
			if(table.getSelectionModel().getSelectedItem() != null) {
				TableName temp = table.getSelectionModel().getSelectedItem();
				if(!getTxtTableName().isEmpty()) {
					if(confirmationCheck("Delete")) {
						sql = "delete from tbtableinfo where autoId = "+temp.getTableId()+"";
						if(databaseHandler.execAction(sql)) {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Edit Successfully....!","Information Edit Successfully...");
							tableLoad();
						}
					}

				}else {
					new Notification(Pos.TOP_CENTER, "Information graphic", "Empty Table Name....!","Please Enter Table Name...");
					txtTableName.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Information graphic", "Select Any Table Name....!","Please Select Any Table Name...");
				table.requestFocus();
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void ItemListTableClickAction(MouseEvent mouseEvent) {
		try {
			if(table.getSelectionModel().getSelectedItem() != null) {
				setTxtTableName(table.getSelectionModel().getSelectedItem().getTableName());
			}		
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}
	private void tableLoad() {
		// TODO Auto-generated method stub
		try {
			sql = "select autoId,tableName from tbTableInfo";
			ResultSet rs = databaseHandler.execQuery(sql);
			list.clear();
			while(rs.next()) {
				list.add(new TableName(rs.getString("autoId"), rs.getString("tablename")));
			}
			table.setItems(list);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	private boolean isTableNameExist() {
		// TODO Auto-generated method stub
		String newTableName = getTxtTableName();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getTableName().equalsIgnoreCase(newTableName)) {
				return true;
			}
		}
		return false;
	}

	private boolean confirmationCheck(String message) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are you sure to "+message+" this Table Name?");
	}

	private void setCmpData() {
		// TODO Auto-generated method stub

		tableNameCol.setCellValueFactory(new PropertyValueFactory("tableName"));
	}


	private void focusMoveAction() {
		// TODO Auto-generated method stub

	}


	private void addCmp() {
		// TODO Auto-generated method stub

	}


	public class TableName{
		private SimpleStringProperty tableId;
		private SimpleStringProperty tableName;

		public TableName(String tableId,String tableName) {
			this.tableId = new SimpleStringProperty(tableId);
			this.tableName = new SimpleStringProperty(tableName);
		}

		public String getTableId() {
			return tableId.get();
		}
		public void setItemId(String tableId) {
			this.tableId = new SimpleStringProperty(tableId);
		}
		public String getTableName() {
			return tableName.get();
		}
		public void setItemName(String tableName) {
			this.tableName = new SimpleStringProperty(tableName);
		}
	}


	public String getTxtTableName() {
		return txtTableName.getText().trim();
	}

	public void setTxtTableName(String txtTableName) {
		this.txtTableName.setText(txtTableName);
	}


}

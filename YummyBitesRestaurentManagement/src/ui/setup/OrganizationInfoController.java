package ui.setup;

import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JButton;
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
import ui.accounts.CashPaymentController.VoucherDetails;

public class OrganizationInfoController implements Initializable{

	
	
	@FXML
	TextArea txt1st;
	@FXML
	TextArea txt2nd;
	@FXML
	TextField txt3rd;
	@FXML
	TextField txt4th;

	@FXML
	Button btnSubmit;
	@FXML
	Button btnRefresh;


	

	private DatabaseHandler databaseHandler;
	private String sql;
	private String ledgerType="1";
	private String ledgerHeadId="";


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		addCmp();
		setCmpData();
		focusMoveAction();
		setCmpFocusAction();
		
		btnRefreshAction(null);
	}

	@FXML
	private void btnRefreshAction(ActionEvent event) {
		try {
			ResultSet rs = databaseHandler.execQuery("select top 1 * from tbOrganizationInfo");
			if(rs.next()) {
				setTxt1st(rs.getString("orgName"));
				setTxt2nd(rs.getString("orgAddress"));
				setTxt3rd(rs.getString("orgNumber"));
				setTxt4th(rs.getString("Other"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	@FXML
	private void btnSubmitAction(ActionEvent event) {
		try {
			if(isHaveARow()) {
				databaseHandler.execAction("update tbOrganizationInfo set orgName= '"+getTxt1st()+"',orgAddress= '"+getTxt2nd()+"',orgNumber='"+getTxt3rd()+"',other='"+getTxt4th()+"',entryTime = CURRENT_TIMESTAMP ,userID = '"+SessionBeam.getUserId()+"'");
				new Notification(Pos.TOP_CENTER, "Information graphic", "Update Successfull....!","Organization Update Successfully....");
			}else {
				databaseHandler.execAction("insert into tbOrganizationInfo (orgName,orgAddress,orgNumber,other,entrytime,userID) values('"+getTxt1st()+"',"
						+ "'"+getTxt2nd()+"',"
						+ "'"+getTxt3rd()+"',"
						+ "'"+getTxt4th()+"',"
						+ "current_timestamp,'"+SessionBeam.getUserId()+"');");
				new Notification(Pos.TOP_CENTER, "Information graphic", "Save Successfull....!","Organization Save Successfully....");
			}
			
			SessionBeam.setOrgName(getTxt1st());
			SessionBeam.setOrgAddress(getTxt2nd());
			SessionBeam.setOrgContact(getTxt3rd());
			
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private boolean isHaveARow() {
		try {
			ResultSet rs = databaseHandler.execQuery("select top 1 * from tbOrganizationInfo");
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}
	private boolean confrimationCheck(String name) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are You Sure to "+name+" this Supplier?");
	}


	

	private void focusMoveAction() {
		Control[] control =  {txt1st,txt2nd,txt3rd,txt4th};
		new FocusMoveByEnter(control);
	}
	
	private void setCmpFocusAction() {
		// TODO Auto-generated method stub

		txt3rd.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txt3rd);
		});
		
		txt4th.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txt4th);
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
		
	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		//date.setConverter(converter);
		//date.setValue(LocalDate.now());



	}

	public String getTxt1st() {
		return txt1st.getText().trim();
	}

	public void setTxt1st(String txt1st) {
		this.txt1st.setText(txt1st);
	}
	
	public String getTxt2nd() {
		return txt2nd.getText().trim();
	}

	public void setTxt2nd(String txt2nd) {
		this.txt2nd.setText(txt2nd);
	}
	
	public String getTxt3rd() {
		return txt3rd.getText().trim();
	}

	public void setTxt3rd(String txt3rd) {
		this.txt3rd.setText(txt3rd);
	}
	
	public String getTxt4th() {
		return txt4th.getText().trim();
	}

	public void setTxt4th(String txt4th) {
		this.txt4th.setText(txt4th);
	}

	
	
}

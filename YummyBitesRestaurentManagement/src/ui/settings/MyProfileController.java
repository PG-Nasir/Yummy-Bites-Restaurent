package ui.settings;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import shareClasses.AlertMaker;
import shareClasses.FocusMoveByEnter;
import shareClasses.Notification;
import shareClasses.SessionBeam;

public class MyProfileController implements Initializable{

	@FXML
	TextField txtUserId;
	@FXML
	TextField txtName;
	@FXML
	TextField txtDisplayName;
	@FXML
	TextField txtDepartment;
	@FXML
	TextField txtUserName;
	@FXML
	PasswordField txtCurrentPassword;
	@FXML
	PasswordField txtNewPassword;
	@FXML
	PasswordField txtConfirmPassword;

	@FXML
	TextArea txtPresentAddress;
	@FXML
	TextArea txtParmanentAddress;

	@FXML
	ComboBox cmbRegion;

	@FXML
	Button btnChangePassword;
	@FXML
	Button btnEditProfile;
	@FXML
	Button btnRefresh;
	@FXML
	Button btnUpload;

	@FXML
	DatePicker dateOfBirth;

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
	private void btnChangePassword(ActionEvent event) {
		try {
			if(!getTxtCurrentPassword().isEmpty()) {
				if(isCurrentPasswordExist()) {
					if(!getTxtNewPassword().isEmpty()) {
						if(!getTxtConfirmPassword().isEmpty()) {
							if(getTxtNewPassword().equals(getTxtConfirmPassword())) {
								if(confrimationCheck("Change your password")) {
									sql = "update tblogin set password = '"+getTxtNewPassword()+"' where user_Id = '"+getTxtUserId()+"'";
									databaseHandler.execAction(sql);

									new Notification(Pos.TOP_CENTER, "Information graphic", "Change Successfully..!","Password Change Successfully...");
									btnRefreshAction(null);
								}


							}else {
								new Notification(Pos.TOP_CENTER, "Warning graphic", "Incorrect, Confirm Password..!","Please Confirm your password....");
								txtConfirmPassword.requestFocus();
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter Confirm Password..!","Please Enter Confirm Password");
							txtConfirmPassword.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter New Password..!","Please Enter New Password");
						txtNewPassword.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Current Password is invalid..!","Please Enter Corrent Current Password");
					txtCurrentPassword.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Enter Current Password..!","Please Enter Current Password");
				txtCurrentPassword.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}



	@FXML
	private void btnEditProfile(ActionEvent event) {
		try {
			if(!getTxtUserId().isEmpty()) {
				if(confrimationCheck("Edit your profile")) {
					sql = "update tblogin set username='"+getTxtUserName()+"',dateOfBirth='"+getDateOfBirth()+"',Religion='"+getCmbRegion()+"',presentAddress='"+getTxtPresentAddress()+"',parmanantAddress='"+getTxtParmanentAddress()+"' where user_Id = '"+getTxtUserId()+"'";
					databaseHandler.execAction(sql);

					new Notification(Pos.TOP_CENTER, "Information graphic", "Edit Successfully..!","User Edit Successfully...");
					btnRefreshAction(null);
				}

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Empty User Id..!","Please Enter User id Or Click Refresh Button....");
				btnRefresh.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void btnRefreshAction(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			sql = "select * from tblogin where user_Id = '"+SessionBeam.getUserId()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtUserId(rs.getString("user_id"));
				setTxtName(rs.getString("name"));
				setTxtDisplayName(rs.getString("displayname"));
				setTxtDepartment(rs.getString("department"));
				setTxtUserName(rs.getString("userName"));
				setDateOfBirth(rs.getDate("dateOfBirth"));
				setCmbRegion(rs.getString("Religion"));
				setTxtPresentAddress(rs.getString("presentAddress"));
				setTxtParmanentAddress(rs.getString("parmanantAddress"));

				setTxtCurrentPassword("");
				setTxtNewPassword("");
				setTxtConfirmPassword("");
			}

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private boolean confrimationCheck(String name) {
		// TODO Auto-generated method stub

		return AlertMaker.showConfirmationDialog("Confirmation..", "Are You Sure to "+name+"?");
	}
	private boolean isCurrentPasswordExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select username from tblogin where user_Id = '"+getTxtUserId()+"' and password = '"+getTxtCurrentPassword()+"'";
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

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub

		txtUserName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtUserName);
		});



		txtCurrentPassword.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtCurrentPassword);
		});

		txtNewPassword.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtNewPassword);
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
	private void setCmpAction() {
		// TODO Auto-generated method stub

	}

	private void focusMoveAction() {
		// TODO Auto-generated method stub
		Control[] control =  {txtUserId,txtName,txtDisplayName,txtDepartment,txtUserName,dateOfBirth,cmbRegion,txtPresentAddress,txtParmanentAddress,txtCurrentPassword,txtNewPassword,txtConfirmPassword,btnEditProfile};
		new FocusMoveByEnter(control);
	}

	private void setCmpData() {
		// TODO Auto-generated method stub
		dateOfBirth.setConverter(converter);

		cmbRegion.getItems().addAll("Islam","Hindu","Buddhist","Christians");
	}

	private void addCmp() {
		// TODO Auto-generated method stub

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

	public String getTxtDepartment() {
		return txtDepartment.getText().trim();
	}

	public void setTxtDepartment(String txtDepartment) {
		this.txtDepartment.setText(txtDepartment);
	}

	public String getTxtUserName() {
		return txtUserName.getText().trim();
	}

	public void setTxtUserName(String txtUserName) {
		this.txtUserName.setText(txtUserName);
	}

	public String getTxtCurrentPassword() {
		return txtCurrentPassword.getText().trim();
	}

	public void setTxtCurrentPassword(String txtCurrentPassword) {
		this.txtCurrentPassword.setText(txtCurrentPassword);
	}

	public String getTxtNewPassword() {
		return txtNewPassword.getText().trim();
	}

	public void setTxtNewPassword(String txtNewPassword) {
		this.txtNewPassword.setText(txtNewPassword);
	}

	public String getTxtConfirmPassword() {
		return txtConfirmPassword.getText().trim();
	}

	public void setTxtConfirmPassword(String txtConfirmPassword) {
		this.txtConfirmPassword.setText(txtConfirmPassword);
	}

	public String getTxtPresentAddress() {
		if(txtPresentAddress.getText() != null)
			return txtPresentAddress.getText().trim();
		return "";
	}

	public void setTxtPresentAddress(String txtPresentAddress) {
		this.txtPresentAddress.setText(txtPresentAddress);
	}

	public String getTxtParmanentAddress() {
		if(txtParmanentAddress.getText() != null)
			return txtParmanentAddress.getText().trim();
		return "";
	}

	public void setTxtParmanentAddress(String txtParmanentAddress) {
		this.txtParmanentAddress.setText(txtParmanentAddress);
	}

	public String getCmbRegion() {
		if(cmbRegion.getValue() != null) {
			return cmbRegion.getValue().toString().trim();
		}
		return "";
	}

	public void setCmbRegion(String cmbRegion) {
		this.cmbRegion.setValue(cmbRegion);

	}

	public String getDateOfBirth() {
		if(dateOfBirth.getValue() != null)
			return dateOfBirth.getValue().toString();
		else 
			return "";
	}

	public void setDateOfBirth(Date dateOfBirth) {
		if(dateOfBirth != null) {
			int dd=Integer.valueOf(new SimpleDateFormat("dd").format(dateOfBirth));
			int mm=Integer.valueOf(new SimpleDateFormat("MM").format(dateOfBirth));
			int yy=Integer.valueOf(new SimpleDateFormat("yyyy").format(dateOfBirth));
			this.dateOfBirth.setValue(LocalDate.of(yy,mm,dd));
		}else {
			this.dateOfBirth.getEditor().setText("");
		}
	}



}

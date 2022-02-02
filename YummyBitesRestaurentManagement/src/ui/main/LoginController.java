package ui.main;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shareClasses.AlertMaker;
import shareClasses.FocusMoveByEnter;
import shareClasses.LoadedInfo;
import shareClasses.Notification;
import shareClasses.SessionBeam;

public class LoginController implements Initializable{

	Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

	@FXML
	TextField txtUserName;
	@FXML
	TextField txtPassword;

	@FXML
	Button btnLogIn;
	@FXML
	Button btnCancel;

	@FXML
	CheckBox checkRememberMe;

	String sql ;
	DatabaseHandler databaseHandler;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		focusMoveAction();
		setCmpAction();
		setCmpFocusAction();
		setRememberMeValue();

	}

	private void setCmpAction() {
		// TODO Auto-generated method stub
		btnLogIn.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			loginAction(null); });
	}

	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		txtUserName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtUserName);
		});
		//setTxtUserName("admin");
		txtPassword.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtPassword);
		});
		//setTxtPassword("sa");

	}

	private void selectCombboxIfFocused(ComboBox box){
		Platform.runLater(() -> {
			if ((box.getEditor().isFocused() || box.isFocused()) && !box.getEditor().getText().isEmpty()) {
				box.getEditor().selectAll();
			}
		});
	}
	private void setRememberMeValue() {
		// TODO Auto-generated method stub
		try{
			File file=new File("src/resource/textFile/loginData.txt");
			Scanner scan=new Scanner(file);

			boolean isRemember = false;
			String userName = "";
			String password = "";

			if(scan.hasNextLine()){ isRemember = scan.nextBoolean(); scan.nextLine();}
			if(isRemember)
			{
				if(scan.hasNextLine()) userName = scan.nextLine();
				if(scan.hasNextLine()) password = scan.nextLine();
			}

			setCheckRememberMe(isRemember);
			setTxtUserName(userName);
			setTxtPassword(password);

		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null	, e);
		}
	}

	private void reWriteRememberMeValue() {
		// TODO Auto-generated method stub
		try{
			File file=new File("src/resource/textFile/loginData.txt");
			FileWriter fileWriter = new FileWriter(file);
			String newValue = String.valueOf(getCheckRememberMe())+"\n"+getTxtUserName()+"\n"+getTxtPassword();
			fileWriter.write(newValue);
			fileWriter.close();
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
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
		Control[] control =  {txtUserName,txtPassword,btnLogIn};
		new FocusMoveByEnter(control);
	}

	@FXML
	private void loginAction(ActionEvent event) {

		if(!getTxtUserName().isEmpty()) {
			if(!getTxtPassword().isEmpty()) {
				if(isUserNameExist(getTxtUserName())) {
					if(isPasswordValid(getTxtUserName(),getTxtPassword())) {			
						loadMain();
						reWriteRememberMeValue();
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic","Invalid Password..","Please Enter Valid Password...");
						txtPassword.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic","Invalid User Name..","Please Enter valid user name...");
					txtUserName.requestFocus();
				}

			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic","Enter Password..","Please Enter your password...");
				txtPassword.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic","Enter User Name..","Please Enter your user name...");
			txtUserName.requestFocus();
		}


		//}else
		{ 
			//txtUserName.getStyleClass().add("wrong-credentials");
			//txtPassword.getStyleClass().add("wrong-credentials");
		}

	}

	private boolean isUserNameExist(String userName) {
		// TODO Auto-generated method stub
		try {
			sql = "select user_id from tblogin where username = '"+userName+"' and activeStatus = 'Active'";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return true;
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean isPasswordValid(String userName, String password) {
		// TODO Auto-generated method stub
		try {
			sql = "select user_id,password from tblogin where username = '"+userName+"' and activeStatus = 'Active'";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				if(rs.getString("password").equals(password))
					return true;
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@FXML
	private void cancelAction(ActionEvent event) {
		System.exit(0);
	}

	void loadMain() {
		try {

			new SessionBeam(getTxtUserName());


			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main/MainFrame.fxml"));
			Parent parent = loader.load();
			Stage stage = ((Stage)txtUserName.getScene().getWindow());
			Scene scene = new Scene(parent);

			//scene.setRoot(parent);


			stage.setTitle("Yummy Bites Platter");
			stage.setScene(scene);

			stage.centerOnScreen();
			stage.setResizable(true);

			stage.setX(bounds.getMinX());
			stage.setY(bounds.getMinY());
			stage.setWidth(bounds.getWidth());
			stage.setHeight(bounds.getHeight());
			stage.setMaximized(true);
			stage.setOnCloseRequest(e->{
				if(AlertMaker.showConfirmationDialog("Close Application?", "Are You Want to Close Application?")) {
					stage.close();
				}else {
					e.consume();
				}
			});
			stage.show();
			//stage.setWidth(value);
			MainFrameController mainController = loader.getController();
			new Thread(new Runnable() {

				public void run() {
					new LoadedInfo();
					mainController.loadAllTabs();
					
				}
			}).start();


			// LibraryAssistantUtil.setStageIcon(stage);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex);
			// Logger.getLogger(MainFxmlController.class.getName());
		}
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

	public boolean getCheckRememberMe() {
		return checkRememberMe.isSelected();
	}

	public void setCheckRememberMe(boolean checkRememberMe) {
		this.checkRememberMe.setSelected(checkRememberMe);;
	}



}

package ui.trading;

import java.sql.ResultSet;

import javax.swing.JOptionPane;



import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.Notification;
import shareClasses.SessionBeam;
import ui.trading.BillingTabController.BillingTab;

public class DialogFindInvoice extends Dialog{

	AnchorPane pane = new AnchorPane();
	
	DatabaseHandler databaseHandler;
	
	FxComboBox cmbFind = new FxComboBox();
	
	Button btnFind = new Button("Find");
	Button btnCancel = new Button("Cancel");
	
	HBox hBoxButton = new HBox();
	
	String type = "3";
	
	TabPane tabPane;
	
	private final int active = 1;
	private final int canceled = 0;
	private final int hidden = 1;
	private final int visible = 0;
	
	private final String sueperAdminType = "Super Admin";
	private final String adminType = "Admin";
	private final String generalType = "General User";
	
	
	public DialogFindInvoice(TabPane tabPane) {
		this.tabPane = tabPane;
		databaseHandler = DatabaseHandler.getInstance();
		
		
		getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Node closeButton = this.getDialogPane().lookupButton(ButtonType.CANCEL);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		//this.setResizable(true);
		this.setTitle("Find Sales Invoice....");
		this.initModality(Modality.APPLICATION_MODAL);
		
		this.setWidth(300);
		this.setHeight(80);
		this.getDialogPane().setContent((Parent)pane);
		
		addCmp();
		setCmpFocusAction();
		focusMoveAction();
		loadInvoiceNo();
		btnAction();
	}

	private void btnAction() {
		// TODO Auto-generated method stub
		btnCancel.setOnAction(e->{
			closeDialog();
			
		});
		
		btnFind.setOnAction(e->{
			findEvent();
		});
		
		btnFind.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			findEvent(); });
	}

	private void closeDialog() {
		// TODO Auto-generated method stub
		Stage dialog=(Stage)pane.getScene().getWindow();
		dialog.close();
	}

	private void findEvent() {
		// TODO Auto-generated method stub
		try {
			if(!getCmbFind().isEmpty()){
				if(isInvoiceIdExist(getCmbFind())) {
					tabPane.getTabs().add(new BillingTab(getCmbFind()));
					tabPane.getSelectionModel().selectLast();
					closeDialog();
					Stage dialog=(Stage)pane.getScene().getWindow();
					dialog.close();
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Invalid Invoice No....!","Your Invoice No is Invalid...Please Enter or Select any valid invoice no....");
					cmbFind.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Sorry....!","Please Enter or Select any invoice no....");
				cmbFind.requestFocus();
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private boolean isInvoiceIdExist(String invoiceId) {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from tbInvoice where type = '"+type+"' and Invoice = '"+invoiceId+"'";
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
	private void loadInvoiceNo() {
		try {
			String sql = "select invoice,SellerCustomerName from tbInvoice where type = '"+type+"' order by Invoice desc";
			if(SessionBeam.getUserType().equals(generalType)){
				sql = "select invoice,SellerCustomerName from tbInvoice where type = '"+type+"' and activeStatus='"+active+"' and hiddenStatus='"+visible+"' order by Invoice desc";
			}
			
			ResultSet rs = databaseHandler.execQuery(sql);
			cmbFind.data.clear();
			while(rs.next()) {
				cmbFind.data.add(rs.getString("invoice"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbFind.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbFind);
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
		Control[] control =  {cmbFind,btnFind};
		new FocusMoveByEnter(control);
	}

	private void addCmp() {
		// TODO Auto-generated method stub
		
		
		
		pane.setPrefWidth(300);
		pane.setPrefHeight(80);
		pane.setMaxHeight(80);
		
		pane.setTopAnchor(cmbFind, 0.0);
		pane.setLeftAnchor(cmbFind, 5.0);
		pane.setRightAnchor(cmbFind, 5.0);
		
		
		cmbFind.setLayoutX(5);
		cmbFind.setLayoutY(0);
		
		
		pane.getChildren().add(cmbFind);
		
		
		
		pane.setLeftAnchor(hBoxButton, 5.0);
		pane.setRightAnchor(hBoxButton, 5.0);
		
		hBoxButton.setSpacing(5);
		hBoxButton.setPrefWidth(290);
		hBoxButton.setPrefHeight(34);
		hBoxButton.setLayoutX(5);
		hBoxButton.setLayoutY(40);
		
		btnFind.setPrefWidth(145);
		btnCancel.setPrefWidth(145);
		hBoxButton.getChildren().addAll(btnFind,btnCancel);
		
		
		pane.getChildren().add(hBoxButton);
		
		
	}
	
	public String getCmbFind() {
		if(cmbFind.getValue() != null) {
			String find[]=cmbFind.getValue().toString().trim().split("/");
			return find[0].trim();
		}
			
		else return "";
	}


	public void setCmbFind(String cmbFind) {
		this.cmbFind.setValue(cmbFind);
	}
	
	
	public class BillingTab extends Tab{

		public BillingTab(String invoiceNo) {

			try {
				
				this.setClosable(false);

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/trading/BillingTab.fxml"));
				Parent root = fxmlLoader.load();
				BillingTabController billingTabController = fxmlLoader.getController();
				billingTabController.setTab(this);
				billingTabController.setTabPane(tabPane);
				billingTabController.invoiceFindAction(invoiceNo);
				
				AnchorPane.setTopAnchor(root, 0.0);
				AnchorPane.setLeftAnchor(root, 0.0);
				AnchorPane.setRightAnchor(root, 0.0);
				this.setContent(root);
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e);
			}

		}
	}
}

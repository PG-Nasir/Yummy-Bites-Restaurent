
package ui.main;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shareClasses.AlertMaker;
import shareClasses.LoadedInfo;
import shareClasses.SessionBeam;
import ui.util.MainUtil;

public class MainFrameController implements Initializable{

	@FXML
	MenuBar menuBar;

	@FXML
	Menu menuSetup;
	@FXML
	Menu menuTrading;
	@FXML
	Menu menuReport;
	@FXML
	Menu menuSettings;
	@FXML
	Menu menuAccounts;
	@FXML
	Menu menuNotification;


	@FXML
	MenuItem menuItemCategoryAndItem;
	@FXML
	MenuItem menuItemItemStockAdjustment;
	@FXML
	MenuItem menuItemSupplierCreate;
	@FXML
	MenuItem menuItemCustomerCreate;
	@FXML
	MenuItem menuItemManagement;
	@FXML
	MenuItem menuItemOrganizationInfoSet;

	@FXML
	MenuItem menuItemPurchase;
	@FXML
	MenuItem menuItemPurchaseReturn;
	@FXML
	MenuItem menuKitchenIssue;
	@FXML
	MenuItem menuKitchenReturn;
	@FXML
	MenuItem menuItemBilling;
	@FXML
	MenuItem menuItemSalesReturn;
	/*@FXML
	MenuItem menuItemServicing;
	@FXML
	MenuItem menuItemBillCreate;*/
	@FXML
	MenuItem menuItemWareentyAdnRepaire;
	

	@FXML
	MenuItem menuItemCashPayment;
	@FXML
	MenuItem menuItemCashRecived;
	@FXML
	MenuItem menuItemBankPayment;
	@FXML
	MenuItem menuItemBankRecived;
	@FXML
	MenuItem menuItemLedgerAndHeadCreate;

	@FXML
	MenuItem menuItemAccountsReport;
	@FXML
	MenuItem menuItemTradingReport;
	@FXML
	MenuItem menuItemStockNotification;

	@FXML
	MenuItem menuItemUserCreate;
	@FXML
	MenuItem menuItemUserAuthentication;
	@FXML
	MenuItem menuItemMyProfile;
	@FXML
	MenuItem menuItemLogOut;

	@FXML
	AnchorPane parentPane;

	@FXML
	VBox vBoxSetup;
	@FXML
	VBox vBoxTrading;
	@FXML
	VBox vBoxAccounts;
	@FXML
	VBox vBoxReport;
	@FXML
	VBox vBoxSettings;

	@FXML
	Button btnSupplierCreate;
	@FXML
	Button btnCusotmerCreate;
	@FXML
	Button btnCategoryAndItemCreate;
	@FXML
	Button btnItemStockAdjustment;
	@FXML
	Button btnManagement;
	@FXML
	Button btnOrganizationInfoSet;

	@FXML
	Button btnPurchase;
	@FXML
	Button btnPurchaseReturn;
	@FXML
	Button btnKitchenIssue;
	@FXML
	Button btnKitchenReturn;
	@FXML
	Button btnBilling;
	/*@FXML
	Button btnSalesReturn;*/
	/*@FXML
	Button btnServicing;
	@FXML
	Button btnBillCreate;*/
	/*@FXML
	Button btnWarrentyAndRepair;*/

	@FXML
	Button btnCashPayment;
	@FXML
	Button btnCashRecived;
	@FXML
	Button btnBankPayment;
	@FXML
	Button btnBankRecived;
	@FXML
	Button btnHeadAndLedgerCreate;

	@FXML
	Button btnTradingReport;
	@FXML
	Button btnAccountsReport;

	@FXML
	Button btnUserCreate;
	@FXML
	Button btnUserAuthentication;
	@FXML
	Button btnMyProfile;
	@FXML
	Button btnLogOut;

	@FXML
	Accordion accordion;
	@FXML
	TitledPane titlePaneSetup;
	@FXML
	TitledPane titlePaneTrading;
	@FXML
	TitledPane titlePaneAccounts;
	@FXML
	TitledPane titlePaneReport;
	@FXML
	TitledPane titlePaneSettings;

	@FXML
	TabPane tabPane;

	Parent[] allTabs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		manuItemAdd();
		
		
	}

	private void manuItemAdd() {
		// TODO Auto-generated method stub
		menuBar.getMenus().clear();
		

		menuSetup.getItems().clear();
		menuTrading.getItems().clear();
		menuReport.getItems().clear();
		menuAccounts.getItems().clear();
		menuNotification.getItems().clear();
		menuSettings.getItems().clear();
		
		accordion.getPanes().clear();
		
		vBoxSetup.getChildren().clear();
		vBoxTrading.getChildren().clear();
		vBoxAccounts.getChildren().clear();
		vBoxReport.getChildren().clear();
		vBoxSettings.getChildren().clear();
		
		//menuBar.getMenus().add(menuNotification);
		
		
		if(SessionBeam.getMapModule("1")) {
			menuBar.getMenus().add(menuSetup);
			accordion.getPanes().add(titlePaneSetup);
			
			
			if(!SessionBeam.getMapModuleItemBlock("1")) {
				menuSetup.getItems().add(menuItemSupplierCreate);
				vBoxSetup.getChildren().add(btnSupplierCreate);
			}
			if(!SessionBeam.getMapModuleItemBlock("2")) {
				menuSetup.getItems().add(menuItemCustomerCreate);
				vBoxSetup.getChildren().add(btnCusotmerCreate);
			}
			if(!SessionBeam.getMapModuleItemBlock("3")) {
				menuSetup.getItems().add(menuItemCategoryAndItem);
				vBoxSetup.getChildren().add(btnCategoryAndItemCreate);
			}
			if(!SessionBeam.getMapModuleItemBlock("4")) {
				menuSetup.getItems().add(menuItemItemStockAdjustment);
				vBoxSetup.getChildren().add(btnItemStockAdjustment);
			}
			if(!SessionBeam.getMapModuleItemBlock("5")) {
				menuSetup.getItems().add(menuItemManagement);
				vBoxSetup.getChildren().add(btnManagement);
			}
			
			if(!SessionBeam.getMapModuleItemBlock("6")) {
				menuSetup.getItems().add(menuItemOrganizationInfoSet);
				vBoxSetup.getChildren().add(btnOrganizationInfoSet);
			}
			
		}
		
		if(SessionBeam.getMapModule("2")) {
			menuBar.getMenus().add(menuTrading);
			accordion.getPanes().add(titlePaneTrading);
			
			if(!SessionBeam.getMapModuleItemBlock("7")) {
				menuTrading.getItems().add(menuItemBilling);
				vBoxTrading.getChildren().add(btnBilling);
			}
			
			if(!SessionBeam.getMapModuleItemBlock("8")) {
				menuTrading.getItems().add(menuItemPurchase);
				vBoxTrading.getChildren().add(btnPurchase);
			}
			if(!SessionBeam.getMapModuleItemBlock("9")) {
				menuTrading.getItems().add(menuItemPurchaseReturn);
				vBoxTrading.getChildren().add(btnPurchaseReturn);
			}
			if(!SessionBeam.getMapModuleItemBlock("10")) {
				menuTrading.getItems().add(menuKitchenIssue);
				vBoxTrading.getChildren().add(btnKitchenIssue);
			}
			if(!SessionBeam.getMapModuleItemBlock("11")) {
				menuTrading.getItems().add(menuKitchenReturn);
				vBoxTrading.getChildren().add(btnKitchenReturn);
			}
			/*if(!SessionBeam.getMapModuleItemBlock("11")) {
				menuTrading.getItems().add(menuItemBillCreate);
				vBoxTrading.getChildren().add(btnBillCreate);
			}*/
			/*if(!SessionBeam.getMapModuleItemBlock("12")) {
				menuTrading.getItems().add(menuItemWareentyAdnRepaire);
				vBoxTrading.getChildren().add(btnWarrentyAndRepair);
			}*/
			
		}


		if(SessionBeam.getMapModule("3")) {
			menuBar.getMenus().add(menuAccounts);
			accordion.getPanes().add(titlePaneAccounts);
			
			if(!SessionBeam.getMapModuleItemBlock("13")) {
				menuAccounts.getItems().add(menuItemCashPayment);
				vBoxAccounts.getChildren().add(btnCashPayment);
			}
			if(!SessionBeam.getMapModuleItemBlock("14")) {
				menuAccounts.getItems().add(menuItemCashRecived);
				vBoxAccounts.getChildren().add(btnCashRecived);
			}
			if(!SessionBeam.getMapModuleItemBlock("15")) {
				menuAccounts.getItems().add(menuItemBankPayment);
				vBoxAccounts.getChildren().add(btnBankPayment);
			}
			if(!SessionBeam.getMapModuleItemBlock("16")) {
				menuAccounts.getItems().add(menuItemBankRecived);
				vBoxAccounts.getChildren().add(btnBankRecived);
			}
			if(!SessionBeam.getMapModuleItemBlock("17")) {
				menuAccounts.getItems().add(menuItemLedgerAndHeadCreate);
				vBoxAccounts.getChildren().add(btnHeadAndLedgerCreate);
			}
			
		}
		
		if(SessionBeam.getMapModule("4")) {
			menuBar.getMenus().add(menuReport);
			accordion.getPanes().add(titlePaneReport);
			
			if(!SessionBeam.getMapModuleItemBlock("19")) {
				menuReport.getItems().add(menuItemTradingReport);
				vBoxReport.getChildren().add(btnTradingReport);
			}
			
			if(!SessionBeam.getMapModuleItemBlock("18")) {
				menuReport.getItems().add(menuItemAccountsReport);
				vBoxReport.getChildren().add(btnAccountsReport);
			}
			
			
		}
		
		if(SessionBeam.getMapModule("5")) {
			menuBar.getMenus().add(menuSettings);
			accordion.getPanes().add(titlePaneSettings);
			
			if(!SessionBeam.getMapModuleItemBlock("20")) {
				menuSettings.getItems().add(menuItemUserCreate);
				vBoxSettings.getChildren().add(btnUserCreate);
			}
			if(!SessionBeam.getMapModuleItemBlock("21")) {
				menuSettings.getItems().add(menuItemUserAuthentication);
				vBoxSettings.getChildren().add(btnUserAuthentication);
			}
			if(!SessionBeam.getMapModuleItemBlock("22")) {
				menuSettings.getItems().add(menuItemMyProfile);
				vBoxSettings.getChildren().add(btnMyProfile);
			}
			if(!SessionBeam.getMapModuleItemBlock("23")) {
				menuSettings.getItems().add(menuItemLogOut);
				vBoxSettings.getChildren().add(btnLogOut);
			}
		}
	
		//menuNotification.getItems().add(menuItemStockNotification);

		/*menuBar.getMenus().clear();
		if(menuSetup.getItems().size()>0) {
			menuBar.getMenus().add(menuSetup);
		}
		if(menuTrading.getItems().size()>0) {
			menuBar.getMenus().add(menuTrading);
		}
		if(menuAccounts.getItems().size()>0) {
			menuBar.getMenus().add(menuAccounts);
		}
		if(menuReport.getItems().size()>0) {
			menuBar.getMenus().add(menuReport);
		}
		if(menuNotification.getItems().size()>0) {
			menuBar.getMenus().add(menuNotification);
		}
		if(menuSettings.getItems().size()>0) {
			menuBar.getMenus().add(menuSettings);
		}*/

		

	}

	@FXML
	private void setSupplierCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/SupplierCreate.fxml"), parentPane,895);
		
		setTab("Supplier Create", allTabs[0]);


	}
	@FXML
	private void setCustomerCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/CustomerCreate.fxml"), parentPane,895);
		setTab("Customer Create", allTabs[1]);
	}
	@FXML
	private void setCategoryAndItemCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/CategoryAndItemCreate.fxml"), parentPane,1000);
		setTab("Category And Item Create", allTabs[2]);
	}
	
	@FXML
	private void setItemStockAdjustment(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Item Stock Adjustment", allTabs[3]);
	}
	
	@FXML
	private void setManagement(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Management", allTabs[4]);
	}

	@FXML
	private void setPurchase(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Purchase", allTabs[5]);
	}
	
	@FXML
	private void setOrganizationInformationSet(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Organization Information", allTabs[10]);
	}
	
	@FXML
	private void setPurchaseReturn(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Purchase Return", allTabs[6]);
	}
	
	@FXML
	private void setBilling(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Billing", allTabs[7]);
	}
	@FXML
	private void setKitchenIssue(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Kitchen Issue", allTabs[8]);
	}
	
	@FXML
	private void setKitchenReturn(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Kitchen Return", allTabs[9]);
	}
	
	/*@FXML
	private void setSalesReturn(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Sales Return", allTabs[10]);
	}
	*/
	
	
	/*@FXML
	private void setBillCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Bill Create", allTabs[11]);
	}
	@FXML*/
	private void setWarrantyAndRepair(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Warranty And Repair", allTabs[11]);
	}
	
	/*@FXML
	private void setWastageAndAdjustment(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/setup/BarCodeGenarator.fxml"), parentPane,700);
		setTab("Wastage And Adjustment", allTabs[12]);
	}*/

	@FXML
	private void setCashPayment(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/accounts/CashPayment.fxml"), parentPane,900);
		setTab("Cash Payment", allTabs[12]);
	}

	@FXML
	private void setCashRecived(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/accounts/CashRecived.fxml"), parentPane,900);
		setTab("Cash Recived", allTabs[13]);
	}

	@FXML
	private void setBankPayment(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/accounts/BankPayment.fxml"), parentPane,800);
		setTab("Bank Payment", allTabs[14]);
	}

	@FXML
	private void setBankRecived(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/accounts/BankRecived.fxml"), parentPane,800);
		setTab("Bank Recived", allTabs[15]);
	}

	@FXML
	private void setHeadAndLeadgerCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/accounts/HeadAndLedgerCreate.fxml"), parentPane,940);
		setTab("Head And Ledger Create", allTabs[16]);
	}
	
	@FXML
	private void setTradingReport(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/accounts/HeadAndLedgerCreate.fxml"), parentPane,940);
		setTab("Trading Report", allTabs[17]);
	}
	
	@FXML
	private void setAccountsReport(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/accounts/HeadAndLedgerCreate.fxml"), parentPane,940);
		setTab("Accounts Report", allTabs[18]);
	}


	@FXML
	private void setUserCreate(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/settings/UserCreate.fxml"), parentPane,707);
		setTab("User Create", allTabs[19]);
	}

	@FXML
	private void setUserAuthentication(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/settings/UserAuthentication.fxml"), parentPane,805);
		setTab("User Authentication", allTabs[20]);
	}

	@FXML
	private void setMyProfile(ActionEvent event) {
		//MainUtil.setScene(getClass().getResource("/ui/settings/MyProfile.fxml"), parentPane,516);
		setTab("My Profile", allTabs[21]);
	}
	
	@FXML
	private void logOutAction(ActionEvent event) {
		try {
			if(AlertMaker.showConfirmationDialog("LogOut?", "Are You Sure to LogOut From Application?")) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main/LogIn.fxml"));
				Parent parent = loader.load();
				Stage stage = ((Stage)accordion.getScene().getWindow());
				Scene scene = new Scene(parent);
				
				stage.centerOnScreen();
				//stage.setResizable(true);
				//stage.setTitle("Creative Information Technology ");
				stage.setWidth(400);
				stage.setHeight(300);
				stage.centerOnScreen();
				stage.setScene(scene);

				stage.show();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex);
			// Logger.getLogger(MainFxmlController.class.getName());
		}
	}


	private void setTab(String tabName,Parent node) {
		try {
			if(tabPane != null) {
				int i;
				for(i = 0;i<tabPane.getTabs().size();i++) {
					if(tabName.equals(tabPane.getTabs().get(i).getText())) {
						tabPane.getSelectionModel().select(i);
						break;
					}
				}
				if(i==tabPane.getTabs().size()) {
					tabPane.getTabs().add(new Tab(tabName,node));
					tabPane.getSelectionModel().selectLast();
				}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}


	public void loadAllTabs() {
		try {
			allTabs=new Parent[25];
			
			
			allTabs[0] = FXMLLoader.load(getClass().getResource("/ui/setup/SupplierCreate.fxml"));
			allTabs[1] = FXMLLoader.load(getClass().getResource("/ui/setup/CustomerCreate.fxml"));
			allTabs[2] = FXMLLoader.load(getClass().getResource("/ui/setup/CategoryAndItemCreate.fxml"));
			allTabs[3] = FXMLLoader.load(getClass().getResource("/ui/setup/ItemStockAdjustment.fxml"));
			allTabs[4] = FXMLLoader.load(getClass().getResource("/ui/setup/Management.fxml"));
			allTabs[10] = FXMLLoader.load(getClass().getResource("/ui/setup/OrganizationInfo.fxml"));
			
			allTabs[5] = FXMLLoader.load(getClass().getResource("/ui/trading/Purchase.fxml"));
			allTabs[6] = FXMLLoader.load(getClass().getResource("/ui/trading/PurchaseReturn.fxml"));
			allTabs[7] = FXMLLoader.load(getClass().getResource("/ui/trading/Sales.fxml"));
			allTabs[8] = FXMLLoader.load(getClass().getResource("/ui/trading/KitchenIssue.fxml"));
			allTabs[9] = FXMLLoader.load(getClass().getResource("/ui/trading/KitchenReturn.fxml"));
			
			//allTabs[10] = FXMLLoader.load(getClass().getResource("/ui/trading/BillCreate.fxml"));
			allTabs[11] = FXMLLoader.load(getClass().getResource("/ui/trading/WarrantyAndRepair.fxml"));
			//allTabs[12] = FXMLLoader.load(getClass().getResource("/ui/trading/WastageAndAdjustment.fxml"));
			
			allTabs[12] = FXMLLoader.load(getClass().getResource("/ui/accounts/CashPayment.fxml"));
			allTabs[13] = FXMLLoader.load(getClass().getResource("/ui/accounts/CashRecived.fxml"));
			allTabs[14] = FXMLLoader.load(getClass().getResource("/ui/accounts/BankPayment.fxml"));
			allTabs[15] = FXMLLoader.load(getClass().getResource("/ui/accounts/BankRecived.fxml"));
			allTabs[16] = FXMLLoader.load(getClass().getResource("/ui/accounts/HeadAndLedgerCreate.fxml"));
			
			allTabs[17] = FXMLLoader.load(getClass().getResource("/ui/reports/TrandingReport.fxml"));
			allTabs[18] = FXMLLoader.load(getClass().getResource("/ui/reports/AccountsReport.fxml"));
			
			allTabs[19] = FXMLLoader.load(getClass().getResource("/ui/settings/UserCreate.fxml"));
			allTabs[20] = FXMLLoader.load(getClass().getResource("/ui/settings/UserAuthentication.fxml"));
			allTabs[21] = FXMLLoader.load(getClass().getResource("/ui/settings/MyProfile.fxml"));
			
			//allTabs[22] = FXMLLoader.load(getClass().getResource("/ui/setup/SupplierCreate.fxml"));
			//allTabs[23] = FXMLLoader.load(getClass().getResource("/ui/setup/SupplierCreate.fxml"));
			//allTabs[24] = FXMLLoader.load(getClass().getResource("/ui/setup/SupplierCreate.fxml"));
			//allTabs[25] = FXMLLoader.load(getClass().getResource("/ui/setup/SupplierCreate.fxml"));
			//allTabs[25] = FXMLLoader.load(getClass().getResource("/ui/setup/SupplierCreate.fxml"));
			//allTabs[27] = FXMLLoader.load(getClass().getResource("/ui/setup/SupplierCreate.fxml"));
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

}

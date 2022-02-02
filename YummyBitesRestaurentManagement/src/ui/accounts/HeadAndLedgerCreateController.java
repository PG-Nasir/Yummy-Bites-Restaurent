package ui.accounts;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.text.StyledEditorKit.StyledTextAction;
import javax.swing.tree.TreeNode;

import databaseHandler.DatabaseHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import shareClasses.FocusMoveByEnter;
import shareClasses.FxComboBox;
import shareClasses.LoadedInfo;
import shareClasses.Notification;
import shareClasses.SessionBeam;
import shareClasses.LoadedInfo.AccountsHeadInfo;
import shareClasses.LoadedInfo.CategoryInfo;
import shareClasses.SessionBeam;
import shareClasses.FxComboBox;
import shareClasses.SessionBeam;
import shareClasses.FxComboBox;;


public class HeadAndLedgerCreateController implements Initializable{

	@FXML
	TextField txtHeadId;
	@FXML
	TextField txtSubHeadName;
	@FXML
	TextField txtLedgerName;
	@FXML
	TextField txtOpeningBalance;
	@FXML
	TextField txtAccountNo;
	@FXML
	TextField txtBranch;
	@FXML
	TextField txtAddress;
	@FXML
	TextField txtId;

	@FXML
	VBox vBoxParentHead;
	@FXML
	VBox vBoxHeadName;
	@FXML
	VBox vBoxFind;


	FxComboBox cmbParentHead = new FxComboBox<>();
	FxComboBox cmbHeadName = new FxComboBox<>();
	FxComboBox cmbFind = new FxComboBox<>();

	@FXML
	ComboBox cmbAccountType;

	@FXML
	CheckBox checkFixedHead;
	@FXML
	CheckBox checkFixedLedger;
	@FXML
	CheckBox checkIsBank;

	@FXML
	Button btnSaveHead ;
	@FXML
	Button btnEditHead ;
	@FXML
	Button btnRefreshHead ;
	@FXML
	Button btnSaveLedger ;
	@FXML
	Button btnEditLedger ;
	@FXML
	Button btnRefreshLedger ;
	@FXML
	Button btnFind ;

	@FXML
	Label lblAccountNo;
	@FXML
	Label lblAccountType;
	@FXML
	Label lblAccountBranch;
	@FXML
	Label lblAccountAddress;

	@FXML
	TreeView<String> headTree;

	@FXML
	private TableView<Ledger> table;

	ObservableList<Ledger> list = FXCollections.observableArrayList();

	@FXML
	private TableColumn<Ledger, String> ledgerIdCol;
	@FXML
	private TableColumn<Ledger, String> ledgerNameCol;
	@FXML
	private TableColumn<Ledger, String> ledgerHeadNameCol;
	@FXML
	private TableColumn<Ledger, String> openingBalanceCol;


	@FXML
	TreeItem<String> rootNode = new TreeItem<>("Creative Information Technology");
	String sql;
	DatabaseHandler databaseHandler;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		cmpAdd();
		cmpSetData();
		cmbAction();
		setCmpFocusAction();
		focusMoveAction();
		loadMaxHeadId();
		loadMaxLedgerId();
		loadHeadName();
		loadLedgerName();
		headTreeLoad();
	}

	@FXML
	private void headSaveAction(ActionEvent event) {

		if(!isHeadidExist()) {
			if(headValidationCheck()) {
				sql= "insert into tbAccfhead (headid,headTitle,pheadId,UnitId,DepId,type,remark,isFixed,entryTime,CreateBy) "
						+ "values('"+getTxtHeadId()+"','"+getTxtSubHeadName()+"','"+getHeadId(getCmbParentHead())+"','1','1','"+getHeadType(getCmbParentHead())+"','','"+(checkFixedHead.isSelected()?"1":"0")+"',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
				databaseHandler.execAction(sql);
				new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Head Save Successfully...");
				
				LoadedInfo.loadAccountsHeadInfo();
				LoadedInfo.loadAccountsHeadInfoList();
				haadRefreshAction(null);

			}
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","This Head Id Allready Exist \n Please Refresh For New Head Id...");
			btnRefreshHead.requestFocus();
		}
	}

	
	@FXML
	private void ledgerSaveAction(ActionEvent event) {

		if(!isLedgeridExist()) {
			if(ledgerValidationCheck()) {
				sql= "insert into tbAccfledger (unitId,depId,ledgerId,ledgerTitle,pheadId,openingBalance,isFixed,Type,isBank,accountNo,accountType,Branch,address,date,remark,entryTime,createBy) " + 
						"values ('1','1','"+getTxtId()+"',"
						+ "'"+getTxtLedgerName()+"',"
						+ "'"+getHeadId(getCmbHeadName())+"',"
						+ "'"+getTxtOpeningBalance()+"',"
						+ "'"+(checkFixedLedger.isSelected()?"1":"0")+"',"
						+ "'"+getHeadType(getCmbHeadName())+"','"+(checkIsBank.isSelected()?"1":"0")+"',"
						+ "'"+getTxtAccountNo()+"','"+getCmbAccountType()+"',"
						+ "'"+getTxtBranch()+"','"+getTxtAddress()+"',"
						+ "'"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"',"
						+ "'remarks',CURRENT_TIMESTAMP,'"+SessionBeam.getUserId()+"');";
				databaseHandler.execAction(sql);
				new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Ledger Save Successfully...");
				ledgerRefreshAction(null);



			}
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","This Ledger Id Allready Exist \n Please Refresh For New Ledger Id...");
			btnRefreshLedger.requestFocus();
		}
	}
	@FXML
	private void headEditAction(ActionEvent event) {

		if(isHeadidExist()) {
			if(headValidationCheck()) {
				if(!isHeadFixed()) {
					sql = "update tbAccfhead set headTitle = '"+getTxtSubHeadName()+"',pheadId='"+getHeadId(getCmbParentHead())+"',isFixed = '"+(checkFixedHead.isSelected()?"1":0)+"',type='"+getHeadType(getCmbParentHead())+"',entryTime= CURRENT_TIMESTAMP,CreateBy = '"+SessionBeam.getUserId()+"' where headid = '"+getTxtHeadId()+"';";
					databaseHandler.execAction(sql);
					new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Head Edit Successfully...");
					//haadRefreshAction(null);
					LoadedInfo.loadAccountsHeadInfo();
					LoadedInfo.loadAccountsHeadInfoList();
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Head is not Editable, It Is Fixed Head......");
					btnRefreshHead.requestFocus();
				}


			}
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","This Head Id is not exist...");
			btnRefreshHead.requestFocus();
		}
	}

	@FXML
	private void ledgerEditAction(ActionEvent event) {

		if(isLedgeridExist()) {
			if(ledgerValidationCheck()) {
				if(!isLedgerFixed()) {
					sql = "update tbAccfledger set ledgerTitle = '"+getTxtLedgerName()+"',pheadId = '"+getHeadId(getCmbHeadName())+"',isfixed='"+(checkFixedLedger.isSelected()?"1":"0")+"',openingBalance='"+getTxtOpeningBalance()+"',Type='"+getHeadType(getCmbHeadName())+"',isBank = '"+(checkIsBank.isSelected()?"1":"0")+"',accountNo='"+getTxtAccountNo()+"',accountType='"+getCmbAccountType()+"',Branch='"+getTxtBranch()+"',address = '"+getTxtAddress()+"',date = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"',entryTime = CURRENT_TIMESTAMP,createBy = '"+SessionBeam.getUserId()+"' where ledgerId = '"+getTxtId()+"';";
					databaseHandler.execAction(sql);
					new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","Ledger Edit Successfully...");
					//haadRefreshAction(null);
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Ledger is not Editable, It Is Fixed Ledger......");
					btnRefreshLedger.requestFocus();
				}


			}
		}else {
			new Notification(Pos.TOP_CENTER, "Information graphic", "Information..","This Ledger Id is not exist...");
			btnRefreshLedger.requestFocus();
		}
	}

	@FXML
	private void haadRefreshAction(ActionEvent event) {
		loadMaxHeadId();
		loadHeadName();
		setCmbParentHead("");
		setTxtSubHeadName("");
		checkFixedHead.setSelected(false);
		headTreeLoad();
	}

	@FXML
	private void ledgerRefreshAction(ActionEvent event) {
		loadMaxLedgerId();
		loadLedgerName();
		setCmbHeadName("");
		setTxtLedgerName("");
		setTxtOpeningBalance("0");
		checkIsBank.setSelected(true);
		isBankCheckClickAction(null);
		setTxtAccountNo("");
		setCmbAccountType("");
		setTxtBranch("");
		setTxtAddress("");
		checkFixedLedger.setSelected(false);
		checkIsBank.setSelected(false);
		isBankCheckClickAction(null);

	}

	@FXML
	private void isBankCheckClickAction(ActionEvent event) {
		if(checkIsBank.isSelected()) {
			lblAccountNo.setDisable(false);
			lblAccountType.setDisable(false);
			lblAccountBranch.setDisable(false);
			lblAccountAddress.setDisable(false);

			txtAccountNo.setDisable(false);
			cmbAccountType.setDisable(false);
			txtBranch.setDisable(false);
			txtAddress.setDisable(false);

		}else {
			lblAccountNo.setDisable(true);
			lblAccountType.setDisable(true);
			lblAccountBranch.setDisable(true);
			lblAccountAddress.setDisable(true);

			txtAccountNo.setDisable(true);
			cmbAccountType.setDisable(true);
			txtBranch.setDisable(true);
			txtAddress.setDisable(true);
		}
	}



	private boolean isHeadFixed() {
		try {
			sql = "select isFixed from tbAccfhead where  headId = '"+getTxtHeadId()+"' ";
			System.out.println(sql);
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				if(rs.getInt("isFixed")==1) {
					return true;
				}
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;

	}

	private boolean isLedgerFixed() {
		try {
			sql = "select isFixed from tbAccfledger where  ledgerid = '"+getTxtId()+"' ";
			System.out.println(sql);
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				if(rs.getInt("isFixed")==1) {
					return true;
				}
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return false;

	}

	private boolean ledgerValidationCheck() {
		if(!getTxtId().isEmpty()) {
			if(!getTxtLedgerName().isEmpty()) {
				if(!getTxtOpeningBalance().isEmpty()) {
					if(isLedgerHeadExist()) {
						if(!isLedgerNameExist()) {
							if(checkIsBank.isSelected()) {
								if(!getTxtAccountNo().isEmpty()) {
									if(!getTxtBranch().isEmpty()) {
										return true;
									}else {
										new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Bank Name Or Branch...");
										txtBranch.requestFocus();
									}
								}else {
									new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Bank Account No...");
									txtAccountNo.requestFocus();
								}
							}else {
								return true;
							}
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Ledger Name Allready Exist..\n Please Enter Other Ledger Name...");
							txtLedgerName.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select Any Valid Ledger Head Name...");
						cmbHeadName.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Opening Balance...");
					txtOpeningBalance.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Any Ledger Name...");
				txtLedgerName.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Head Id...");
			btnRefreshLedger.requestFocus();
		}
		return false;
	}

	private boolean headValidationCheck() {
		if(!getTxtHeadId().isEmpty()) {
			if(!getTxtSubHeadName().isEmpty()) {
				if(!getCmbParentHead().isEmpty()) {
					if(isParentHeadExist()) {
						if(!isHeadNameExist()) {
							return true;
						}else {
							new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","This Head Name Allready Exist..\n Please Enter Other Head Name...");
							txtSubHeadName.requestFocus();
						}
					}else {
						new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select Any Valid Parent Head Name...");
						cmbParentHead.requestFocus();
					}
				}else {
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select Any Parent Head Name...");
					cmbParentHead.requestFocus();
				}
			}else {
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Any Head Name...");
				txtSubHeadName.requestFocus();
			}
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Enter Head Id...");
			btnRefreshHead.requestFocus();
		}
		return false;
	}

	private String getHeadId(String headName) {
		// TODO Auto-generated method stub
		try {
			sql = "select headId from tbAccfhead where headTitle = '"+headName+"' ";
			System.out.println(sql);
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("headId");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "0";
	}
	
	private int getHeadType(String headName) {
		// TODO Auto-generated method stub
		try {
			sql = "select type from tbAccfhead where headTitle = '"+headName+"' ";
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getInt("type");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return 0;
	}

	private boolean isHeadNameExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select headTitle from tbAccfhead where headTitle = '"+getTxtSubHeadName()+"' and headId != '"+getTxtHeadId()+"'";
			System.out.println(sql);
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

	private boolean isLedgerNameExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select ledgerTitle from tbAccfledger where ledgerTitle = '"+getTxtLedgerName()+"' and ledgerId != '"+getTxtId()+"'";
			System.out.println(sql);
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

	private boolean isParentHeadExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select headTitle from tbAccfhead where headTitle = '"+getCmbParentHead()+"'";
			System.out.println(sql);
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

	private boolean isLedgerHeadExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select headTitle from tbAccfhead where headTitle = '"+getCmbHeadName()+"'";
			System.out.println(sql);
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
	private boolean isLedgeridExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select ledgerTitle from tbAccfLedger where ledgerid = '"+getTxtId()+"'";
			System.out.println(sql);
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
	private boolean isHeadidExist() {
		// TODO Auto-generated method stub
		try {
			sql = "select headTitle from tbAccfhead where headid = '"+getTxtHeadId()+"'";
			System.out.println(sql);
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

	private void loadLedgerName() {
		try {
			cmbFind.data.clear();
			list.clear();
			sql = "select ledgerId,ledgerTitle,(select headTitle from  tbAccfhead h where h.headid = l.pheadId) as LedgerHead,openingBalance  from tbaccfledger l";
			
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbFind.data.add(rs.getString("ledgerTitle"));
				list.add(new Ledger(rs.getString("ledgerId"), rs.getString("LedgerHead"), rs.getString("ledgerTitle"), rs.getString("openingBalance")));
			}
			table.setItems(list);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void loadLedgerNameByHeadId(String headId) {
		try {

			list.clear();
			sql = "select ledgerId,ledgerTitle,(select headTitle from  tbAccfhead h where h.headid = l.pheadId) as LedgerHead,openingBalance  from tbaccfledger l where l.pheadId = '"+headId+"'";
			System.out.println(sql);
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {

				list.add(new Ledger(rs.getString("ledgerId"), rs.getString("LedgerHead"), rs.getString("ledgerTitle"), rs.getString("openingBalance")));
			}
			table.setItems(list);
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	private void loadHeadName() {
		try {
			cmbParentHead.data.clear();
			cmbHeadName.data.clear();
			sql = "select headTitle  from tbaccfHead";
			
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				cmbParentHead.data.add(rs.getString("headTitle"));
				cmbHeadName.data.add(rs.getString("headTitle"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void loadMaxLedgerId() {
		try {
			sql = "select isNull(MAX(ledgerId),0)+1 as maxId from tbaccfledger";
			
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtId(rs.getString("maxId"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}



	private void loadMaxHeadId() {
		try {
			sql = "select isNull(MAX(headId),0)+1 as maxId from tbaccfHead";
			
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtHeadId(rs.getString("maxId"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void headTreeLoad() {
		// TODO Auto-generated method stub
		headTree.setRoot(treeLoad(rootNode,"1"));
	}


	private TreeItem<String> treeLoad(TreeItem<String> treeItem,String pHeadId) {
		// TODO Auto-generated method stub
		try {
			treeItem.getChildren().clear();
			/*ArrayList<String> treeName = new ArrayList<>();
			ArrayList<Integer> treeId = new ArrayList<>();

			sql = "select * from tbaccfhead where pheadid = '"+pHeadId+"'";
			
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				treeName.add(rs.getString("headTitle"));
				treeId.add(rs.getInt("headId"));
			}*/
			ArrayList<AccountsHeadInfo> temp = LoadedInfo.getAccountHeadInfoList(pHeadId);
			for(int i =0 ;i<temp.size();i++) {
				treeItem.getChildren().add(treeLoad(new TreeItem<String>(temp.get(i).getHeadTitle()),temp.get(i).getHeadid()));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		treeItem.setExpanded(true);
		return treeItem;
	}

	@FXML
	private void tableClickAction(MouseEvent event) {
		try {
			if(table.getSelectionModel().getSelectedItem() != null) {

				Ledger tempLedger = table.getSelectionModel().getSelectedItem();
				findLedgerById(tempLedger.getLedgerId());
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void findButtonAction(ActionEvent event) {
		if(!getCmbFind().isEmpty()) {
			findLedgerById(getLedgerId(getCmbFind()));
		}else {
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!","Please Select Any Ledger For Find...");
			cmbFind.requestFocus();
		}
	}

	@FXML
	private void findLedgerById(String ledgerId) {
		// TODO Auto-generated method stub
		try {
			sql = "select ledgerId,ledgerTitle,openingBalance,isFixed,isBank,accountNo,accountType,Branch,address,(select headTitle from tbaccfHead h where h.headid=l.pheadId) as headName from tbAccfledger l where ledgerId = '"+ledgerId+"'";
			
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				setTxtId(rs.getString("ledgerId"));
				setTxtLedgerName(rs.getString("ledgerTitle"));
				setTxtOpeningBalance(rs.getString("openingBalance"));

				if(rs.getInt("isFixed")==1) {
					checkFixedLedger.setSelected(true);
				}else
					checkFixedLedger.setSelected(false);

				if(rs.getInt("isBank")==1) {
					checkIsBank.setSelected(true);
				}else
					checkIsBank.setSelected(false);

				setTxtAccountNo(rs.getString("accountNo"));
				setCmbAccountType(rs.getString("accountType"));
				setTxtBranch(rs.getString("Branch"));
				setTxtAddress(rs.getString("address"));

				setCmbHeadName(rs.getString("headName"));

				isBankCheckClickAction(null);
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private String getLedgerId(String ledgerName) {
		// TODO Auto-generated method stub
		try {
			sql = "select ledgerId from tbAccfLedger where ledgerTitle = '"+ledgerName+"' ";
			System.out.println(sql);
			ResultSet rs = databaseHandler.execQuery(sql);
			if(rs.next()) {
				return rs.getString("ledgerId");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return "";
	}

	public void treeClickAction(MouseEvent mouseEvent) {
		try {
			TreeItem<String >  tempTreeItem= headTree.getSelectionModel().getSelectedItem();
			if(tempTreeItem != null) {
				sql = "select headid,headTitle,pheadId,isFixed,(select headTitle from tbAccfhead where headid = a.pheadId) as pheadTitle from tbaccfhead a where headtitle = '"+tempTreeItem.getValue()+"'";
				System.out.println(sql);
				ResultSet rs = databaseHandler.execQuery(sql);
				if(rs.next()) {
					setTxtHeadId(rs.getString("headid"));
					setCmbParentHead(rs.getString("pheadTitle"));
					setTxtSubHeadName(rs.getString("headTitle"));
					setCmbHeadName(rs.getString("headTitle"));
					if(rs.getInt("isFixed")==1) 
						checkFixedHead.setSelected(true);
					else
						checkFixedHead.setSelected(false);
				}
				setTxtSubHeadName(tempTreeItem.getValue());
				loadLedgerNameByHeadId(getHeadId(getCmbHeadName()));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void focusMoveAction() {
		Control[] control =  {cmbHeadName,txtSubHeadName};
		new FocusMoveByEnter(control);

		Control[] control2 =  {cmbFind,btnFind};
		new FocusMoveByEnter(control2);
		
		Control[] control3 =  {txtLedgerName,txtOpeningBalance,btnSaveLedger};
		new FocusMoveByEnter(control3);
		
		Control[] control4 =  {txtAccountNo,txtBranch,txtAddress,btnSaveLedger};
		new FocusMoveByEnter(control4);
	}
	
	
	private void setCmpFocusAction() {
		// TODO Auto-generated method stub
		cmbParentHead.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbParentHead);
		});
		cmbHeadName.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbHeadName);
		});
		cmbFind.focusedProperty().addListener((observable, oldValue, newValue) -> {
			selectCombboxIfFocused(cmbFind);
		});
		/*cmbItemName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	        selectTextIfFocused(cmbItemName);
	    });*/

		txtSubHeadName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtSubHeadName);
		});
		txtLedgerName.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtLedgerName);
		});
		txtOpeningBalance.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtOpeningBalance);
		});
		txtAccountNo.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtAccountNo);
		});
		txtBranch.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtBranch);
		});
		txtAddress.focusedProperty().addListener((observable,oldValue,newValue) ->{
			selectTextIfFocused(txtAddress);
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


	private void cmpAdd() {
		// TODO Auto-generated method stub

		cmbParentHead.setPrefWidth(240);
		cmbParentHead.setPrefHeight(28);

		vBoxParentHead.getChildren().clear();
		vBoxParentHead.getChildren().add(cmbParentHead);

		cmbHeadName.setPrefWidth(162);
		cmbHeadName.setPrefHeight(28);

		vBoxHeadName.getChildren().clear();
		vBoxHeadName.getChildren().add(cmbHeadName);

		cmbFind.setPrefWidth(236);
		cmbFind.setPrefHeight(28);
		vBoxFind.getChildren().clear();
		vBoxFind.getChildren().add(cmbFind);
	}

	private void cmbAction() {
		cmbHeadName.setOnMouseClicked(e ->{
			if(!getCmbHeadName().isEmpty())
				loadLedgerNameByHeadId(getHeadId(getCmbHeadName()));
		});
		/*
		 * cmbCustomer.setOnKeyReleased(e ->{ if(e.getCode() == KeyCode.ENTER)
		 * loadBayerByCustomer(); });
		 */

		cmbHeadName.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener()  {

					@Override
					public void changed(ObservableValue arg0, Object arg1, Object arg2) {
						// TODO Auto-generated method stub
						if(!getCmbHeadName().isEmpty())
							loadLedgerNameByHeadId(getHeadId(getCmbHeadName()));
					}
				});
		
		btnSaveHead.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			headSaveAction(null); });
		
		btnSaveLedger.setOnKeyPressed(e ->{ if(e.getCode() == KeyCode.ENTER)
			ledgerSaveAction(null); });
	}

	private void cmpSetData() {
		// TODO Auto-generated method stub

		ledgerIdCol.setCellValueFactory(new PropertyValueFactory("ledgerId"));
		ledgerNameCol.setCellValueFactory(new PropertyValueFactory("ledgerName"));
		ledgerHeadNameCol.setCellValueFactory(new PropertyValueFactory("ledgerHeadName"));
		openingBalanceCol.setCellValueFactory(new PropertyValueFactory("openingBalance"));

	}

	public static class Ledger{


		private SimpleStringProperty ledgerId;
		private SimpleStringProperty ledgerName;
		private SimpleStringProperty ledgerHeadName;
		private SimpleStringProperty openingBalance;


		public Ledger(String ledgerId,String ledgerHeadName,String ledgerName,String openingBalance) {
			this.ledgerId = new SimpleStringProperty(ledgerId);
			this.ledgerName = new SimpleStringProperty(ledgerName);
			this.ledgerHeadName = new SimpleStringProperty(ledgerHeadName);
			this.openingBalance = new SimpleStringProperty(openingBalance);


		}

		public String getLedgerId() {
			return ledgerId.get();
		}

		public void setLedgerId(String sl) {
			this.ledgerId = new SimpleStringProperty(sl);
		}

		public String getLedgerName() {
			return ledgerName.get();
		}

		public void setLedgerName(String length) {
			this.ledgerName = new SimpleStringProperty(length);
		}

		public String getLedgerHeadName() {
			return ledgerHeadName.get();
		}

		public void setLedgerHeadName(String width) {
			this.ledgerHeadName = new SimpleStringProperty(width);
		}

		public String getOpeningBalance() {
			return openingBalance.get();
		}

		public void setOpeningBalance(String height) {
			this.openingBalance = new SimpleStringProperty(height);
		}


	}
	public String getTxtHeadId() {
		return txtHeadId.getText().trim();
	}

	public void setTxtHeadId(String txtHeadId) {
		this.txtHeadId.setText(txtHeadId);
	}
	public String getTxtId() {
		return txtId.getText().trim();
	}

	public void setTxtId(String txtId) {
		this.txtId.setText(txtId);
	}
	public String getTxtSubHeadName() {
		return txtSubHeadName.getText().trim();
	}
	public void setTxtSubHeadName(String txtSubHeadName) {
		this.txtSubHeadName.setText(txtSubHeadName);
	}
	public String getTxtLedgerName() {
		return txtLedgerName.getText().trim();
	}

	public void setTxtLedgerName(String txtLedgerName) {
		this.txtLedgerName.setText(txtLedgerName);
	}

	public String getTxtOpeningBalance() {
		return txtOpeningBalance.getText().trim();
	}

	public void setTxtOpeningBalance(String txtOpeningBalance) {
		this.txtOpeningBalance.setText(txtOpeningBalance);
	}
	public String getTxtAccountNo() {
		if(checkIsBank.isSelected())
			return txtAccountNo.getText().trim();
		else
			return "";
	}

	public void setTxtAccountNo(String txtAccountNo) {
		this.txtAccountNo.setText(txtAccountNo);
	}

	public String getTxtBranch() {

		if(checkIsBank.isSelected())
			return txtBranch.getText().trim();
		else
			return "";
	}

	public void setTxtBranch(String txtBranch) {
		this.txtBranch.setText(txtBranch);
	}
	public String getTxtAddress() {
		if(checkIsBank.isSelected())
			return txtAddress.getText().trim();
		else
			return "";
	}
	public void setTxtAddress(String txtAddress) {
		this.txtAddress.setText(txtAddress);
	}
	public String getCmbParentHead() {
		if(cmbParentHead.getValue() != null)
			return cmbParentHead.getValue().toString().trim();
		else return "";
	}
	public void setCmbParentHead(String cmbParentHead) {
		this.cmbParentHead.setValue(cmbParentHead);
	}

	public String getCmbHeadName() {
		if(cmbHeadName.getValue() != null)
			return cmbHeadName.getValue().toString().trim();
		else return "";
	}
	public void setCmbHeadName(String cmbHeadName) {
		this.cmbHeadName.setValue(cmbHeadName);
	}
	public String getCmbFind() {
		if(cmbFind.getValue() != null)
			return cmbFind.getValue().toString().trim();
		else return "";
	}

	public void setCmbFind(String cmbFind) {
		this.cmbFind.setValue(cmbFind);
	}

	public String getCmbAccountType() {
		if(cmbAccountType.getValue() != null) {
			if(checkIsBank.isSelected())
				return cmbAccountType.getValue().toString().trim();
			else
				return "";
		}
		else return "";
	}
	public void setCmbAccountType(String cmbAccountType) {
		this.cmbAccountType.setValue(cmbAccountType);
	}


}

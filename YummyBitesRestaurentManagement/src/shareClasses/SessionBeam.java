package shareClasses;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.text.StyledEditorKit.StyledTextAction;

import databaseHandler.DatabaseHandler;

public class SessionBeam {
	String UserName="",UserIp="",PCName="",Department="",LoginTime="";
	static String userType;
	static String  UserId;
	String Password="";
	String ClientCompanyName="",ClientAddress="",ClientPhone="";
	String DeveloperCompanyName="",Tele="",Email="",Web="",HelpLine="",Address="",SystemName="";
	String sql;
	static String orgName,orgAddress,orgContact,orgOther;

	
	
	static private HashMap<String, Boolean> mapModule = new HashMap<>();
	static private HashMap<String, Boolean> mapModuleItemInsert = new HashMap<>();
	static private HashMap<String, Boolean> mapModuleItemEdit = new HashMap<>();
	static private HashMap<String, Boolean> mapModuleItemDelete = new HashMap<>();
	static private HashMap<String, Boolean> mapModuleItemBlock = new HashMap<>();
	
	DatabaseHandler databaseHandler;
	
	public SessionBeam(String userName) {
		
		System.out.println("---------- In Session Beam Constructor--------------");
		this.databaseHandler = DatabaseHandler.getInstance();
		this.UserName = userName;
		loadUserInfo();
		loadAccessMoudele();
		loadAccessModuleItem();
		loadOrganizationInfo();
	}
	
	private void loadOrganizationInfo() {
		// TODO Auto-generated method stub
		try {
			ResultSet rs = databaseHandler.execQuery("select top 1 * from tbOrganizationInfo");
			if(rs.next()) {
				setOrgName(rs.getString("orgName"));
				setOrgAddress(rs.getString("orgAddress"));
				setOrgContact(rs.getString("orgNumber"));
				setOrgOther(rs.getString("other"));

			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void loadAccessModuleItem() {
		// TODO Auto-generated method stub
		try {
			mapModuleItemInsert.clear();
			mapModuleItemEdit.clear();
			mapModuleItemDelete.clear();
			mapModuleItemBlock.clear();
			sql = "select * from tbUserAuthentication where userId = '"+getUserId()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				mapModuleItemInsert.put(rs.getString("ModuleId"), rs.getInt("CheckInsert")==1?true:false);
				mapModuleItemEdit.put(rs.getString("ModuleId"), rs.getInt("CheckEdit")==1?true:false);
				mapModuleItemDelete.put(rs.getString("ModuleId"), rs.getInt("CheckDelete")==1?true:false);
				mapModuleItemBlock.put(rs.getString("ModuleId"), rs.getInt("CheckBlock")==1?true:false);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void loadAccessMoudele() {
		// TODO Auto-generated method stub
		try {
			sql = "select * from tblogindetails where userId = '"+getUserId()+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				mapModule.put(rs.getString("ModuleId"), rs.getInt("status")==1?true:false);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void loadUserInfo() {
		// TODO Auto-generated method stub
		try {
			
			sql = "select * from tblogin where username = '"+UserName+"'";
			ResultSet rs = databaseHandler.execQuery(sql);
			while(rs.next()) {
				setUserId(rs.getString("user_Id"));
				setUserType(rs.getString("userType"));
				setDepartment(rs.getString("department"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	
	
	public static boolean getMapModule(String moduleId) {
		if(mapModule.get(moduleId) != null)
			return mapModule.get(moduleId);
		return false;
	}

	public static boolean getMapModuleItemInsert(String moduleId) {
		if(mapModuleItemInsert.get(moduleId) != null)
			return mapModuleItemInsert.get(moduleId);
		return true;
	}

	public static boolean getMapModuleItemEdit(String moduleId) {
		if(mapModuleItemEdit.get(moduleId) != null)
			return mapModuleItemEdit.get(moduleId);
		return true;
	}

	public static boolean getMapModuleItemDelete(String moduleId) {
		if(mapModuleItemDelete.get(moduleId) != null) 
			return mapModuleItemDelete.get(moduleId);
		return true;
	}

	public static boolean getMapModuleItemBlock(String moduleId) {
		if(mapModuleItemBlock.get(moduleId) != null)
			return mapModuleItemBlock.get(moduleId);
		return false;
	}
	
	public void setUserName(String userName) {
		this.UserName = userName;
	}
	public String getUserName() {

		return UserName;
	}

	public void setPassword(String passWord) {
		this.Password = passWord;
	}
	
	public String getPassword() {
		int i=0;
		String EncrypString="";
		while(i<Password.length()){
			char ch=Password.charAt(i);
			int ascii = (int) ch;
			ascii=ascii-2;
			ascii=ascii+5;
			EncrypString=EncrypString+ascii+"$*S";
			i++;
		}
		return EncrypString;
	}

	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	
	
	public String getLoginTime() {
		return LoginTime;
	}
	public void setLoginTime(String loginTime) {
		LoginTime = loginTime;
	}
	public String getDeveloperCompanyName() {
		return DeveloperCompanyName;
	}
	public void setDeveloperCompanyName(String developerCompanyName) {
		DeveloperCompanyName = developerCompanyName;
	}
	public String getTele() {
		return Tele;
	}
	public void setTele(String tele) {
		Tele = tele;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	public String getWeb() {
		return Web;
	}
	public void setWeb(String web) {
		Web = web;
	}
	public String getHelpLine() {
		return HelpLine;
	}
	public void setHelpLine(String helpLine) {
		HelpLine = helpLine;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}

	public String getSystemName() {
		return SystemName;
	}
	public void setSystemName(String systemName) {
		SystemName = systemName;
	}
	
	public static String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public static String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getUserIp() {
		return UserIp;
	}
	public void setUserIp(String userIp) {
		UserIp = userIp;
	}
	public String getPCName() {
		return PCName;
	}
	public void setPCName(String pCName) {
		PCName = pCName;
	}
	public String getClientCompanyName() {
		return ClientCompanyName;
	}
	public void setClientCompanyName(String clientCompanyName) {
		ClientCompanyName = clientCompanyName;
	}
	public String getClientAddress() {
		return ClientAddress;
	}
	public void setClientAddress(String clientAddress) {
		ClientAddress = clientAddress;
	}
	public String getClientPhone() {
		return ClientPhone;
	}
	public void setClientPhone(String clientPhone) {
		ClientPhone = clientPhone;
	}

	public static String getOrgName() {
		return orgName;
	}

	public static void setOrgName(String orgNam) {
		orgName = orgNam;
	}

	public static String getOrgAddress() {
		return orgAddress;
	}

	public static void setOrgAddress(String orgAddres) {
		orgAddress = orgAddres;
	}

	public static String getOrgContact() {
		return orgContact;
	}

	public static void setOrgContact(String orgContac) {
		orgContact = orgContac;
	}

	public static String getOrgOther() {
		return orgOther;
	}

	public static void setOrgOther(String orgOthe) {
		orgOther = orgOthe;
	}
	
	
	
}

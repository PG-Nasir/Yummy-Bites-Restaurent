package databaseHandler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;



public final class DatabaseHandler {

	private static DatabaseHandler handler;


	//private static final String DB_URL="jdbc:sqlserver://localhost:1433;databaseName = YummyBitesPlatter";
	private static final String DB_URL="jdbc:sqlserver://Cursor-PC5:1433;databaseName = YummyBitesPlatter";
	public static Connection conn = null;
	private static Statement stmt = null; 

	PreparedStatement pst;

	private DatabaseHandler() {
		createConnection();
	}

	public static DatabaseHandler getInstance() {
		if(handler == null) {
			handler = new DatabaseHandler();
		}
		return handler;
	}
	
	
	void createConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(DB_URL, "sa", "Cursor777");
			stmt = conn.createStatement();
			DatabaseMetaData dbm = conn.getMetaData();

		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Can't Load Database", "Database Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			
		}
	}

	
	public ResultSet execQuery(String query) {
		ResultSet result;
		try {
			stmt = conn.createStatement();
			System.out.println(query);
			System.out.println();
			result = stmt.executeQuery(query);
		}catch(SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage()," Error Ocoured",JOptionPane.ERROR_MESSAGE );
			System.out.println("Exception at execQuery:dataHandler "+ex.getLocalizedMessage());
			return null;
		}finally {

		}

		return result;
	}

	public boolean execAction(String qu) {
		try {
			stmt = conn.createStatement();
			System.out.println(qu);
			System.out.println();
			stmt.execute(qu);
			return true;
		}catch(SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage()," Error Ocoured",JOptionPane.ERROR_MESSAGE );
			System.out.println("Exception at execQuery:dataHandler "+ex.getLocalizedMessage());
			return false;
		}finally {

		}
	}


}

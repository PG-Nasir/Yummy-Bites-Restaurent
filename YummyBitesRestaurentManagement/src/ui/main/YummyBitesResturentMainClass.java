package ui.main;

import javax.swing.JOptionPane;

import databaseHandler.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.util.MainUtil;

public class YummyBitesResturentMainClass extends Application{

	
	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/ui/main/LogIn.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Yummy Bites Platter");
			
			MainUtil.setStageIcon(primaryStage);
			
			
			new Thread(new Runnable() {
				
				public void run() {
					DatabaseHandler.getInstance();					
				}
			}).start();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
}

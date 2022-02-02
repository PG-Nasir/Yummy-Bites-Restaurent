package ui.util;

import java.net.URL;
import java.util.logging.Logger;

import javax.swing.JOptionPane;



import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import ui.main.MainFrameController;



public class MainUtil {
private static final String IMAGE_LOC="/image/icon/man.png";
	

	static Stage stage1;
	public static void setStageIcon(Stage stage) {
		stage1 = stage;
		stage.getIcons().add(new Image(IMAGE_LOC));
	}
	public static void loadDialogWindow(URL loc,String title) {
		try {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(stage1);
			Parent parent =FXMLLoader.load(loc);
			Scene dialogScene = new Scene(parent);
			dialog.setTitle(title);
			dialog.setScene(dialogScene);
			dialog.show();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	public static void setScene(URL loc,AnchorPane parentPane,int width) {
		try {
			
			Parent child =FXMLLoader.load(loc);
			Scene childScene = new Scene(child);
			
			Stage temStage = new Stage();
			temStage.setScene(childScene);
			
			child.setLayoutX((parentPane.getWidth()/2)-(width/2));
			parentPane.getChildren().clear();
			
			parentPane.getChildren().add(child);
		
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	public static void loadWiindow(URL loc,String title,Stage parentStage) {
		try {
			
			Parent parent =FXMLLoader.load(loc);
			Stage stage = null;
			if(parentStage != null) {
				stage = parentStage;
			}else {
				stage = new Stage(StageStyle.DECORATED);
			}
			stage.setTitle(title);
			stage.setScene(new Scene(parent));
			stage.show();
			setStageIcon(stage);
		}catch(Exception ex) {
			Logger.getLogger(MainFrameController.class.getName());
		}
	}
}

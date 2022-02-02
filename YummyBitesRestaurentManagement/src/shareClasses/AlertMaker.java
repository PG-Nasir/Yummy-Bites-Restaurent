package shareClasses;

import java.awt.List;
import java.io.PrintWriter;
import java.io.StringWriter;

/*import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;*/

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;

public class AlertMaker {

	public static void showSimpleAlert(String title,String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static void showErrorMessage(String title,String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText("Error");
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static boolean showConfirmationDialog(String title,String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonData.NO);
		alert.setTitle(title);
		alert.setHeaderText("Confirmation");
		alert.setContentText(content);
		alert.getButtonTypes().setAll(yesButton,noButton);
		alert.showAndWait();
		//System.out.println(alert.getResult());
		if(alert.getResult() == yesButton) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void showErrorMessage(Exception ex) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error occured");
		alert.setHeaderText("Error Occured");
		alert.setContentText(ex.getLocalizedMessage());
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();
		
		Label label = new Label("The exception stacktrace was:");
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		
		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		
		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}
	
	/*public static void shawMaterialDialog(StackPane root, Node nodeToBeBlurred,java.util.List<JFXButton> controll) {
		 BoxBlur blur = new BoxBlur(3, 3, 3);
		 
		 JFXDialogLayout dialogLayout = new JFXDialogLayout();
		 
		
		 JFXDialog dialog = new JFXDialog(root,dialogLayout,JFXDialog.DialogTransition.TOP);
		 
		 controll.forEach(controlButton -> {
			 controlButton.getStyleClass().add("dialog-button");
			 controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
				 dialog.close();
			 });
		 });
		 
		 
		 dialogLayout.setHeading(new Label("No Such book exists in Issue Records"));
		 dialogLayout.setActions(controll);
		 dialog.show();
		 dialog.setOnDialogClosed((JFXDialogEvent eventt1) ->{
			 nodeToBeBlurred.setEffect(null);
		 });
		 nodeToBeBlurred.setEffect(blur);
	}*/
}

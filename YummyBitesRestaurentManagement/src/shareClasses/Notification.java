package shareClasses;

import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;

import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.cell.ColorGridCell;

public class Notification
{
	protected String graphicMode = "";

	public Notification(Pos pos, String graphicMode, String Title, String Message)
	{
		Node graphic = null;
		switch (graphicMode)
		{
		default:
		case "No graphic": 
		case "Warning graphic":
		case "Information graphic":
		case "Confirm graphic":
		case "Error graphic":
			break;
		case "Custom graphic":
			graphic = null;//new ImageView(SMALL_GRAPHIC);
			break;
		case "Total-replacement graphic": 
			Message = "";
			graphic = buildTotalReplacementGraphic();
			break;
		}

		Notifications notificationBuilder = Notifications.create()
				//.title(true ? Title : "")
				.title(Title)
				.text(Message)
				.graphic(graphic)
				.hideAfter(Duration.seconds(2))
				.position(pos)
				.onAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent arg0)
					{
						System.out.println("Notification clicked on!");
					}
				});

		//if(ownerChkBox.isSelected())
		/*{
			notificationBuilder.owner(stage);
		}*/

		/*if (! showCloseButtonChkBox.isSelected()) {
			notificationBuilder.hideCloseButton();
		}*/

		/*if (darkStyleChkBox.isSelected()) {
			notificationBuilder.darkStyle();
		}*/

		switch (graphicMode)
		{
		case "Warning graphic":     notificationBuilder.showWarning(); break;
		case "Information graphic": notificationBuilder.showInformation(); break;
		case "Confirm graphic":     notificationBuilder.showConfirm(); break;
		case "Error graphic":       notificationBuilder.showError(); break;
		default: notificationBuilder.show(); 
		}
	}

	private Node buildTotalReplacementGraphic()
	{
		final ObservableList<Color> list = FXCollections.<Color>observableArrayList();

		GridView<Color> colorGrid = new GridView<>(list);
		colorGrid.setPrefSize(300, 300);
		colorGrid.setMaxSize(300, 300);

		colorGrid.setCellFactory(new Callback<GridView<Color>, GridCell<Color>>(){
			@Override public GridCell<Color> call(GridView<Color> arg0)
			{
				return new ColorGridCell();
			}
		});
		Random r = new Random(System.currentTimeMillis());
		for(int i = 0; i < 500; i++)
		{
			list.add(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 1.0));
		}
		return colorGrid;
	}
}

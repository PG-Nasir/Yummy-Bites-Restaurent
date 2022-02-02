package shareClasses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FxComboBox<T> extends ComboBox implements EventHandler<KeyEvent> {
	//public ComboBox comboBox;
	private StringBuilder sb;
	public ObservableList<T> data;
	private boolean moveCaretToPos = false;
	private int caretPos;
	public FxComboBox() {
		sb = new StringBuilder();
		data = this.getItems();
		this.setEditable(true);
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent t) {
				//hide();
			}
		});
		setOnKeyReleased(this);
	}

	@Override
	public void handle(KeyEvent event) {

		if(event.getCode() == KeyCode.UP) {
			caretPos = -1;
			moveCaret(getEditor().getText().length());
			return;
		} else if(event.getCode() == KeyCode.DOWN) {
			if(!isShowing()) {
				show();
			}
			caretPos = -1;
			moveCaret(getEditor().getText().length());
			return;
		} else if(event.getCode() == KeyCode.BACK_SPACE) {
			moveCaretToPos = true;
			caretPos = getEditor().getCaretPosition();
		} else if(event.getCode() == KeyCode.DELETE) {
			moveCaretToPos = true;
			caretPos =getEditor().getCaretPosition();
		}else if(event.getCode().isLetterKey() || event.getCode().isDigitKey() || event.getCode()==KeyCode.SPACE){
			moveCaretToPos = true;
			caretPos =getEditor().getCaretPosition();
		}
		if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
				|| event.isControlDown() || event.getCode() == KeyCode.HOME
				|| event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
			return;
		}
		
		if(event.getCode() == KeyCode.ENTER) {
			//getEditor().setText(data.get(caretPos).toString());
			hide();
			return;
		}

		ObservableList list = FXCollections.observableArrayList();
		for (int i=0; i<data.size(); i++) {
			if(data.get(i).toString().trim().toLowerCase().contains(
					getEditor().getText().trim().toLowerCase())) {
				list.add(data.get(i));
			}
		}
		String t =getEditor().getText();
		setItems(list);
		getEditor().setText(t);
		if(!moveCaretToPos) {
			caretPos = -1;
		}
		
		moveCaret(t.length());
		if(!list.isEmpty() || t.length() >0) {
			show();	
		}else {
			hide();
		}
		
	}
	private void moveCaret(int textLength) {
		if(caretPos == -1) {
			getEditor().positionCaret(textLength);
		} else {
			getEditor().positionCaret(caretPos);
		}
		moveCaretToPos = false;
	}
	
	public void setLoadItems() {
		setItems(data);
	}
}
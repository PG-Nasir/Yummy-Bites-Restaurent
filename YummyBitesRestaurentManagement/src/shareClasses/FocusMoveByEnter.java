package shareClasses;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/*
 * User those line where your control object define...
 * final Control ob[] = {txtRoll,txtName,txtSemester,cmbInstitute,txtDepartment};	
new FocusMoveByEnter(ob);*/

public class FocusMoveByEnter {
	public FocusMoveByEnter(final Control oparent[])
	{
		for(int a=0;a<oparent.length-1;a++){
			final int i=a;
			oparent[a].setOnKeyPressed(keyEvent -> {
		        if(keyEvent.getCode() == KeyCode.ENTER) {
		        	oparent[i+1].requestFocus();
		        }
		    });
		}
	}

}

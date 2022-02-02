package shareClasses;

import java.util.regex.Pattern;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class NumberField extends TextField{
	static Pattern validDoubleText = Pattern.compile("-?((\\d*)|(\\d+\\.\\d*))");
	public NumberField(){
		   TextFormatter<Double> textFormatter = new TextFormatter<Double>(new DoubleStringConverter(), 0.0, 
		            change -> {
		                String newText = change.getControlNewText() ;
		                if (validDoubleText.matcher(newText).matches()) {
		                    return change ;
		                } else return null ;
		            });

		   this.setTextFormatter(textFormatter);
	}
	public static TextFormatter<Double> getDoubleFormate(){
		return new TextFormatter<Double>(new DoubleStringConverter(), 0.0, 
				change -> {
					String newText = change.getControlNewText() ;
					if (validDoubleText.matcher(newText).matches()) {
						return change ;
					} else return null ;
		});
	}
	public static TextFormatter<Integer> getIntegerFormate(){
		return new TextFormatter<Integer>(new IntegerStringConverter(), 0, 
				change -> {
					String newText = change.getControlNewText() ;
					if (validDoubleText.matcher(newText).matches()) {
						return change ;
					} else return null ;
		});
	}
}
package oogasalad.frontend.modals.fields;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class IntegerPickerComponent extends Field {

    private String labelText;
    private int propertyValue;
    private TextField textField;

    public IntegerPickerComponent() {

    }

    public IntegerPickerComponent(String labelText, int propertyValue) {
        this.labelText = labelText;
        this.propertyValue = propertyValue;
    }

    @Override
    public HBox createField() {
        textField = new TextField(Integer.toString(propertyValue));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                propertyValue = Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                propertyValue = 0; // Default value if input is not an integer
            }
        });
        return new HBox(new Label(labelText + ": "), textField);
    }

    public int getValue(){
        return propertyValue;
    }

    public String getLabelText(){
        return labelText;
    }
}
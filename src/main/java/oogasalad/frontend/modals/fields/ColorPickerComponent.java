package oogasalad.frontend.modals.fields;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ColorPickerComponent extends Field {

    private String labelText;
    private String propertyValue;
    private ColorPicker colorPicker;

    public ColorPickerComponent() {
    }

    public ColorPickerComponent(String labelText, String propertyValue) {
        this.labelText = labelText;
        this.propertyValue = propertyValue;
    }

    @Override
    public HBox createField() {
        colorPicker = new ColorPicker();
        colorPicker.setValue(javafx.scene.paint.Color.valueOf("white"));
        return new HBox(new Label(labelText + ": "), colorPicker);
    }

    public String getValue() {
        return colorPicker.getValue().toString();
    }

    public String getLabelText() {
        return labelText;
    }
}
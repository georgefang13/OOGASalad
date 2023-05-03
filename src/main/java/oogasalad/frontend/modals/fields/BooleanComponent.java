package oogasalad.frontend.modals.fields;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BooleanComponent extends Field {

    private String labelText;
    private Boolean propertyValue;
    private CheckBox checkBox;

    public BooleanComponent(String labelText, Boolean propertyValue) {
        this.labelText = labelText;
        this.propertyValue = propertyValue;
    }

    @Override
    public HBox createField() {
        checkBox = new CheckBox();
        checkBox.setSelected(propertyValue);
        HBox hBox = new HBox(new Label(labelText + ": "), checkBox);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
        return hBox;
    }

    public Boolean getValue() {
        return checkBox.isSelected();
    }

    public String getLabelText() {
        return labelText;
    }

}
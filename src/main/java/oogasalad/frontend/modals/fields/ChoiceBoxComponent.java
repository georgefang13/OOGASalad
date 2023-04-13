package oogasalad.frontend.modals.fields;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.List;

public class ChoiceBoxComponent extends Field {
    private List<String> options;
    private String labText;

    public ChoiceBoxComponent(String labelText, String valueText) {
        this.options = Arrays.asList(valueText.split(","));
        this.labText = labelText;
    }

    @Override
    public HBox createField() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(options);
        return new HBox(new Label(labText+": "), choiceBox);
    }
}
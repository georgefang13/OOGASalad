package oogasalad.frontend.modals.fields;

import java.util.Arrays;
import java.util.List;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ChoiceBoxComponent extends Field {

  private List<String> options;
  private String labText;
  private ChoiceBox<String> choiceBox;

  public ChoiceBoxComponent(String labelText, String valueText) {
    this.options = Arrays.asList(valueText.split(","));
    this.labText = labelText;
  }

  @Override
  public HBox createField() {
    choiceBox = new ChoiceBox<>();
    choiceBox.getItems().addAll(options);
    return new HBox(new Label(labText + ": "), choiceBox);
  }

  public String getValue() {
    return choiceBox.getValue();
  }

  public String getLabelText() {
    return labText;
  }

}
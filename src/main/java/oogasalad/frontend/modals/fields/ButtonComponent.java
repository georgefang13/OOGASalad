package oogasalad.frontend.modals.fields;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ButtonComponent extends Field {

  private String labelText;
  private String propertyValue;

  public ButtonComponent(String labelText, String propertyValue) {
    this.labelText = labelText;
    this.propertyValue = propertyValue;
  }

  @Override
  public HBox createField() {
    Button button = new Button(labelText);
    return new HBox(button);
  }
}

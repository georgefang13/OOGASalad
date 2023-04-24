package oogasalad.frontend.modals.fields;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TextFieldComponent extends Field {

  private String labelText;
  private String propertyValue;
  private TextField textField;

  public TextFieldComponent() {

  }

  public TextFieldComponent(String labelText, String propertyValue) {
    this.labelText = labelText;
    this.propertyValue = propertyValue;
  }

  @Override
  public HBox createField() {
    textField = new TextField(propertyValue);
    return new HBox(new Label(labelText + ": "), textField);
  }
  public String getValue(){
    return textField.getText();
  }
  public String getLabelText(){
    return labelText;
  }
}

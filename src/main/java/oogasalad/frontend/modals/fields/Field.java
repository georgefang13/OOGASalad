package oogasalad.frontend.modals.fields;

import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
// ...

public class Field {
  private String id;

  protected HBox createField() {
    return null;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

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
//            choiceBox.setValue(propertyValue);
      return new HBox(new Label(labText), choiceBox);
    }
  }

  public class TextFieldComponent extends Field {

    private String labelText;
    private String propertyValue;

    public TextFieldComponent(String labelText, String propertyValue) {
      this.labelText = labelText;
      this.propertyValue = propertyValue;
    }

    @Override
    public HBox createField() {
      TextField textField = new TextField(propertyValue);
      return new HBox(new Label(labelText), textField);
    }

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
  }


}



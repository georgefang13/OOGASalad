package oogasalad.frontend.components.factories;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonFactory {

  public static Button makeButton(String id, EventHandler<ActionEvent> handler) {
    Button button = new Button();
    button.setText(id);
    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    button.setOnAction(handler);
    return button;
  }

}

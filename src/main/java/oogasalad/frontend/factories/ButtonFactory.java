package oogasalad.frontend.factories;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;

/**
 * This class represents a factory for creating buttons
 */
public class ButtonFactory {

  public ButtonFactory() {
  }

  /**
   * Creates a button with the given text, font size, and text color
   *
   * @param property the key value in the label bundle representing the text of
   *                 the button
   * @return the Button
   */
  public Button createDefaultButton(String property) {
    Button defaultButton = new Button();
    defaultButton.setText(property); //TODO:make like this textBundle.getString(property)
    return defaultButton;
  }


  /**
   * Creates a tooltop with the given text
   * @param text
   * @return
   */
  public Tooltip createTooltip(String text) {
    Tooltip tooltip = new Tooltip();
    tooltip.setText(text); //TODO: make like this textBundle.getString(text)
    return tooltip;
  }
}
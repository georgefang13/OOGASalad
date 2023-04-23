package oogasalad.frontend.factories;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents a factory for creating buttons
 */
public class ButtonFactory {

  private final ResourceBundle textBundle;
  private static final String ENGLISH_PROPERTIES = "frontend/properties/text/english";
  private static final String ENGLISH_FILE_PATH = "frontend/properties/text/english.properties";
  private static final String CANT_LOAD_FILE_ID = "unableToLoadPropertiesFile";
  private static final String ERROR_LOADING_FILE_ID = "Error loading properties file";
  private Map<String, String> myButtonPropertiesMap;
  private Map<String, String> myTooltipPropertiesMap;
  private static final String BUTTON_PREFIX = "Button";
  private static final String TOOLTIP_PREFIX = "Tooltip";
  private static final Integer ROTATE_CCW_90 = -90;

  public ButtonFactory() {
    this.textBundle = ResourceBundle.getBundle(ENGLISH_PROPERTIES);
    myButtonPropertiesMap = setPropertiesMap(BUTTON_PREFIX);
    myTooltipPropertiesMap = setPropertiesMap(TOOLTIP_PREFIX);
  }

  /**
   * Creates a button with the given text, font size, and text color
   *
   * @param property the key value in the label bundle representing the text of the button
   * @return the Button
   */
  public Button createDefaultButton(String property) {
    Button defaultButton = new Button();
    defaultButton.setText(myButtonPropertiesMap.get(property));
    return defaultButton;
  }

  /**
   * Creates a button with the given text, font size, and text color
   *
   * @param property the key value in the label bundle representing the text of the button
   * @return the Button
   */
  public Button createComponentButton(String property) {
    Button componentButton = new Button();
    componentButton.setText(myButtonPropertiesMap.get(property));
    return componentButton;
  }

  /**
   * Creates a button with the given text, font size, and text color
   *
   * @param property the key value in the label bundle representing the text of the button
   * @return the Button
   */
  public Button createVeritcalButton(String property) {
    Button verticalButton = new Button();
    verticalButton.setText(myButtonPropertiesMap.get(property));
    verticalButton.setRotate(ROTATE_CCW_90);
    return verticalButton;
  }

  public Button createIconButton(String text, String iconPath) {
    Button button = new Button(text);

    // Load the icon image from the file path
    Image iconImage = new Image(iconPath);

    // Create an ImageView with the icon image and set it as the button's graphic
    ImageView iconView = new ImageView(iconImage);
    button.setGraphic(iconView);

    // Set the button's layout to display the icon on the left and text on the right
    button.setContentDisplay(ContentDisplay.LEFT); // Set the content display to display the graphic on the left
    button.setGraphicTextGap(10); // Set the gap between the icon and text
    button.setPrefWidth(150); // Set the button's preferred width to fit the text and icon

    return button;
  }

  /**
   * Creates a tooltop with the given text
   *
   * @param text
   * @return
   */
  public Tooltip createTooltip(String text) {
    Tooltip tooltip = new Tooltip();
    tooltip.setText(myTooltipPropertiesMap.get(text));
    return tooltip;
  }

  private HashMap<String, String> setPropertiesMap(
      String prefix) { //TODO: this is reused from MODAL.java...fix that so its not repeated code
    Properties properties = new Properties();
    try {
      InputStream inputStream = getClass().getClassLoader()
          .getResourceAsStream(ENGLISH_FILE_PATH);
      if (inputStream != null) {
        properties.load(inputStream);
      } else {
        System.err.println(textBundle.getString(CANT_LOAD_FILE_ID));
      }
    } catch (Exception e) {
      System.out.println(textBundle.getString(ERROR_LOADING_FILE_ID));
    }

    HashMap<String, String> myPropertiesMap = new HashMap<>();

    for (String key : properties.stringPropertyNames()) {
      if (key.startsWith(prefix)) {
        String newKey = key.substring(prefix.length() + 1);
        myPropertiesMap.put(newKey, properties.getProperty(key));
      }
    }
    return myPropertiesMap;
  }

}
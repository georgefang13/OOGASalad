package oogasalad.frontend.factories;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;

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


  public ButtonFactory() {
    this.textBundle = ResourceBundle.getBundle(ENGLISH_PROPERTIES);
    myButtonPropertiesMap = setPropertiesMap(BUTTON_PREFIX);
    myTooltipPropertiesMap = setPropertiesMap(TOOLTIP_PREFIX);
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
    defaultButton.setText(myButtonPropertiesMap.get(property));
    return defaultButton;
  }


  /**
   * Creates a tooltop with the given text
   * @param text
   * @return
   */
  public Tooltip createTooltip(String text) {
    Tooltip tooltip = new Tooltip();
    tooltip.setText(myTooltipPropertiesMap.get(text));
    return tooltip;
  }

  private HashMap<String, String> setPropertiesMap(String prefix) {
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
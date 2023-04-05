package oogasalad.frontend.components.modals;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class DisplayModal extends Modal{
  final public static int GAP = 10;
  final public static int INSET_TOP = 20;
  final public static int INSET_RIGHT = 150;
  final public static int INSET_BOTTOM = 10;
  final public static int INSET_LEFT = 10;
  private Map<String, String> myPropertiesMap;


  public DisplayModal(String title) {
    super(title);
    myPropertiesMap = setPropertiesMap(title);
  }
  protected HashMap<String, String> setPropertiesMap(String title) {
    Properties properties = new Properties();
    try {
      properties.load(getClass().getResourceAsStream("Modals.properties"));
    } catch (IOException e) {
      System.out.println("Error loading properties file");
    }

    HashMap<String, String> myPropertiesMap = new HashMap<>();

    for (String key : properties.stringPropertyNames()) {
      if (key.startsWith(title)) {
        String newKey = key.substring(title.length() + 1);
        myPropertiesMap.put(newKey, properties.getProperty(key));
      }
    }
    return myPropertiesMap;

  }

  @Override
  protected DialogPane createDialogPane() {
    return null;
  }

  @Override
  protected Object convertResult(ButtonType buttonType) {
    return null;
  }
}

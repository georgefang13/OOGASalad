package oogasalad.windowscene.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class PropertiesManager {

  private static final String PROPERTIES = "frontend/properties/";
  private static final String NUMERIC_RESOURCES = PROPERTIES + "numeric/";
  private static final String TEXT_RESOURCES = PROPERTIES + "text/";
  private static ResourceBundle numericResources = ResourceBundle.getBundle(
      NUMERIC_RESOURCES + "numeric");
  private static ResourceBundle textResources = ResourceBundle.getBundle(
      TEXT_RESOURCES + "spanish");
  private static List<PropertiesObserver> observers = new ArrayList<>();

  public static double getNumeric(String key) {
    return Double.parseDouble(numericResources.getString(key));
  }

  public static String getText(String key) {
    return textResources.getString(key);
  }

  public static void setTextResources(String resource) {
    textResources = ResourceBundle.getBundle(TEXT_RESOURCES + resource);
    notifyObservers();
  }

  public static void addObserver(PropertiesObserver observer) {
    observers.add(observer);
  }

  private static void notifyObservers() {
    for (PropertiesObserver observer : observers) {
      observer.setText();
    }
  }
}
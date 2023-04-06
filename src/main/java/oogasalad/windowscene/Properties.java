package oogasalad.windowscene;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Properties {

  private static final String RESOURCE_PROPERTIES = "frontend/properties/";
  private static final String NUMERIC_RESOURCES = RESOURCE_PROPERTIES + "numeric/";
  private static final String TEXT_RESOURCES = RESOURCE_PROPERTIES + "text/";
  private static ResourceBundle numericResources = ResourceBundle.getBundle(
      NUMERIC_RESOURCES + "numeric");
  private static ResourceBundle textResources = ResourceBundle.getBundle(
      TEXT_RESOURCES + "spanish");
  private static List<LanguageObserver> observers = new ArrayList<>();

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

  public static void addObserver(LanguageObserver observer) {
    observers.add(observer);
  }

  public static void removeObserver(LanguageObserver observer) {
    observers.remove(observer);
  }

  private static void notifyObservers() {
    for (LanguageObserver observer : observers) {
      observer.updateText();
    }
  }
}
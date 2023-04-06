package oogasalad.windowscene;

import java.util.ResourceBundle;

public class Properties {

  private static ResourceBundle numericResources = ResourceBundle.getBundle(
      "frontend/properties/numeric/numeric");
  private static ResourceBundle textResources = ResourceBundle.getBundle(
      "frontend/properties/text/english");

  public static double getNumeric(String key) {
    return Double.parseDouble(numericResources.getString(key));
  }

  public static String getText(String key) {
    return textResources.getString(key);
  }

  public static void setTextResources(String resource) {
    textResources = ResourceBundle.getBundle(resource);
  }

  public static void setNumericResources(String resource) {
    numericResources = ResourceBundle.getBundle(resource);
  }
}

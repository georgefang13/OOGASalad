package oogasalad.frontend.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class StandardPropertyManager implements PropertyManager {

  private static final String PROPERTIES = "frontend/properties/";
  private static final String NUMERIC_RESOURCES = PROPERTIES + "numeric/";
  private static final String TEXT_RESOURCES = PROPERTIES + "text/";

  private static StandardPropertyManager instance = null;
  private ResourceBundle numericResources;
  private ResourceBundle textResources;
  private List<PropertiesObserver> observers;

  private StandardPropertyManager() {
    this.numericResources = ResourceBundle.getBundle(NUMERIC_RESOURCES + "numeric");
    this.textResources = ResourceBundle.getBundle(TEXT_RESOURCES + "english");
    this.observers = new ArrayList<>();
  }

  public static StandardPropertyManager getInstance() {
    if (instance == null) {
      instance = new StandardPropertyManager();
    }
    return instance;
  }

  @Override
  public double getNumeric(String key) {
    return Double.parseDouble(numericResources.getString(key));
  }

  @Override
  public String getText(String key) {
    return textResources.getString(key);
  }

  public Set<String> getTextKeys() {
    return textResources.keySet();
  }

  @Override
  public void setTextResources(String resource) {
    this.textResources = ResourceBundle.getBundle(TEXT_RESOURCES + resource);
    notifyObservers();
  }

  @Override
  public void addObserver(PropertiesObserver observer) {
    observers.add(observer);
  }

  private void notifyObservers() {
    for (PropertiesObserver observer : observers) {
      observer.setText();
    }
  }
}

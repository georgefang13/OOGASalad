package oogasalad.frontend.managers;

public interface PropertyManager {

  double getNumeric(String key);

  String getText(String key);

  void setTextResources(String resource);

  void addObserver(PropertiesObserver observer);

}

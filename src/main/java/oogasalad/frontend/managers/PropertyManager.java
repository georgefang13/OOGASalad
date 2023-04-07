package oogasalad.frontend.managers;

import java.util.Set;

public interface PropertyManager {

  double getNumeric(String key);

  String getText(String key);

  void setTextResources(String resource);

  void addObserver(PropertiesObserver observer);

  Set<String> getTextKeys();

}

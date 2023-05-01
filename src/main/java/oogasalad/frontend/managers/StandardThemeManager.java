package oogasalad.frontend.managers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class StandardThemeManager implements ThemeManager {

  private static final String THEME = "frontend/css/";
  private static StandardThemeManager instance = null;
  private String theme;
  private List<ThemeObserver> observers;

  private StandardThemeManager() {
    this.theme = THEME + "dark.css";
    this.observers = new ArrayList<>();
  }

  public static StandardThemeManager getInstance() {
    if (instance == null) {
      instance = new StandardThemeManager();
    }
    return instance;
  }

  @Override
  public String getTheme() {
    return theme;
  }

  @Override
  public void setTheme(String newTheme) {
    this.theme = THEME + newTheme + ".css";
    notifyObservers();
  }

  @Override
  public void addObserver(ThemeObserver observer) {
    observers.add(observer);
  }

  private void notifyObservers() {
    for (ThemeObserver observer : observers) {
      observer.setTheme();
    }
  }
}
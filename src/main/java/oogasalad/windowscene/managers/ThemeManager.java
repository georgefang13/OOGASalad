package oogasalad.windowscene.managers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class ThemeManager {

  private static final String THEME = "frontend/css/";

  private static String theme = THEME + "light";

  private static List<ThemeObserver> observers = new ArrayList<>();

  public static String getTheme() {
    return theme;
  }

  public static void setTheme(String newTheme) {
    theme = THEME + newTheme + ".css";
    notifyObservers();
  }

  public static void addObserver(ThemeObserver observer) {
    observers.add(observer);
  }

  private static void notifyObservers() {
    for (ThemeObserver observer : observers) {
      observer.setTheme();
    }
  }
}

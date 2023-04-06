package oogasalad.windowscene;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;

public class ThemeManager {

  private static final String THEME = "frontend/css/";

  private static String theme = THEME + "light";

  private static List<ThemeObserver> observers = new ArrayList<>();

  public static void applyTheme(Scene scene) {
    scene.getStylesheets().clear();
    scene.getStylesheets().add(theme + ".css");
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

package oogasalad.frontend.managers;

public interface ThemeManager {

  String getTheme();

  void setTheme(String newTheme);

  void addObserver(ThemeObserver observer);

}

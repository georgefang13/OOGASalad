package oogasalad.gamerunner.frontend.components.panels;

/*
* Panel is an interface that defines the way that our panels work
* Should include the default methods that all panels have for communication with the backend
* as well as other panels
*/
public interface Panel {


  oogasalad.gameeditor.frontend.ViewObjects.panels.Panel makePanel();

  oogasalad.gameeditor.frontend.ViewObjects.panels.Panel refreshPanel();

  String getTitle();

  void save();

}

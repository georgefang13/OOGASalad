package oogasalad;

import oogasalad.WindowTypes.WindowType;

public interface WindowMediator {

  String registerWindow(WindowType windowType);

  void showWindow(String windowID);

  void registerAndShow(WindowType windowType);

  void closeWindow(String windowID);

  void receiveMessage();

}

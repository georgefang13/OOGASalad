package oogasalad;

import oogasalad.WindowTypeEnum.WindowType;

public interface WindowMediator {

  String registerWindow(WindowType windowType);

  void showWindow(String windowID);

  void closeWindow(String windowID);

  void receiveMessage();

}

package oogasalad;

import oogasalad.WindowTypeEnum.WindowType;

public interface WindowMediator {

  String registerWindow(WindowType windowType);

  void showWindow(String windowID);

  void registerAndShow(WindowType windowType);

  void closeWindow(String windowID);

  void receiveMessage();

}

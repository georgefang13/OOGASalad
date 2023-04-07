package oogasalad.frontend.windows;

import oogasalad.frontend.windows.WindowTypes.WindowType;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public interface WindowMediator {

  String registerWindow(WindowType windowType);

  void showWindow(String windowID);

  void registerAndShow(WindowType windowType);

  void closeWindow(String windowID);

}

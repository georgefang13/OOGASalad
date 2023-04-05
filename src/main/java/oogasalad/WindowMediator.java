package oogasalad;

public interface WindowMediator {
  String registerWindow(String windowType);
  void showWindow(String windowType, String windowID);

  void receiveMessage();

}

package oogasalad;

public class WindowController implements WindowMediator {

  @Override
  public String registerWindow(String windowType) {
    AbstractWindow window = WindowFactory.createWindow(windowType);
    int counter = windowCounters.getOrDefault(windowType, 0) + 1;
    String windowId = windowType + "_" + counter;
    windowMap.computeIfAbsent(windowType, k -> new HashMap<>()).put(windowId, window);
    windowCounters.put(windowType, counter);
    return windowId;
    return null;
  }

  @Override
  public void showWindow(String windowType, String windowID) {

  }

  @Override
  public void receiveMessage() {

  }
}

package oogasalad;

import java.util.Map;

public class WindowController implements WindowMediator {
  private Map<String, Map<String, AbstractWindow>> windowMap;
  private Map<String, Integer> windowCounters;

  @Override
  public String registerWindow(String windowType) {
    AbstractWindow window = WindowFactory.createWindow(windowType);
    int counter = windowCounters.getOrDefault(windowType, 0) + 1;
    String windowId = windowType + "_" + counter;
    windowMap.computeIfAbsent(windowType, k -> new HashMap<>()).put(windowId, window);
    windowCounters.put(windowType, counter);
    return windowID;
  }

  @Override
  public void showWindow(String windowType, String windowID) {

  }

  @Override
  public void receiveMessage() {

  }
}

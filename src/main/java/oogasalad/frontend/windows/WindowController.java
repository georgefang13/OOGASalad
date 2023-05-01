package oogasalad.frontend.windows;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import oogasalad.frontend.windows.WindowTypes.WindowType;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class WindowController implements WindowMediator {

  private Map<String, AbstractWindow> windowMap;
  private int windowIDCounter;
  private Stack<Object> windowData = new Stack<>();

  public WindowController() {
    windowMap = new HashMap<>();
    registerAndShow(WindowType.SPLASH_WINDOW);
  }

  @Override
  public void registerAndShow(WindowType windowType) {
    showWindow(registerWindow(windowType));
  }

  @Override
  public String registerWindow(WindowType windowType) {
    String windowID = windowType + "_" + windowIDCounter;
    AbstractWindow window = WindowFactory.createWindow(windowType, windowID, this);
    windowIDCounter++;
    windowMap.put(windowID, window);
    window.sceneController.setDefaultScene(window.getDefaultSceneType()); //messy
    return windowID;
  }

  @Override
  public void showWindow(String windowID) {
    getWindow(windowID).show();
  }

  @Override
  public void closeWindow(String windowID) {
    getWindow(windowID).close();
    windowMap.remove(windowID);
  }

  @Override
  public AbstractWindow getWindow(String windowID) {
    return windowMap.get(windowID);
  }

  @Override
  public void passData(Object data) {
    windowData.push(data);
  }

  @Override
  public Object getData() {
    return windowData.pop();
  }

}

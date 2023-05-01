# How to use log

Usage:
Import following:
```
import oogasalad.logging.MainLogger;
import ch.qos.logback.classic.Level;
```

add to your class:
```
private static final MainLogger logger = MainLogger.getInstance(PUTCLASSNAMEHERE.class);
```

To log something use one of follwoing:
```
logger.trace("String - text of log here");
logger.debug(String.format("Registered a new window: Type - %s",  windowType));
logger.info("some info");
logger.warn("Invalid scene type: ");
logger.error("Show the error");
```

Log can be set up to filter only certain types. To decide which to use this is simple guide:

.trace - is for the most minor logs (such as registered a window)
.debug - is for something not entirely relevant for the app to run, but can help identify major issues (such as creatd a window)
.info - is for logging essential operations (such as show a new window)
.warn - is for warning messages or other alerts
.error - is for handling and logging errors


In main class you can set the level of settings that is shown. Ther are lsited above from the most irrelevant to the most critcial. The defaults setting is to show INFO and more important ones, you can change it there if needed for the whoeldocument.

If you want to show lower level logs (e.g., trace) only foor your class add (and unccoment) the follwing line inside your class
`//logger.setLogLevel(Level.ALL); // uncomment if you want to add lover level logs specific to this class`



**The most simple usage example is in Main.java**

for more examples look in oogasalad.frontend.windows



Example code:

```
package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.GameEditorEditorScene;
import oogasalad.frontend.scenes.GameEditorLogicScene;
import oogasalad.frontend.scenes.GameEditorMainScene;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.logging.MainLogger;
import ch.qos.logback.classic.Level;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GameEditorWindow extends AbstractWindow {
  private static final MainLogger logger = MainLogger.getInstance(GameEditorWindow.class);

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE,
    EDITOR_SCENE,
    LOGIC_SCENE,
  }

  public GameEditorWindow(String windowID, WindowMediator windowController) {
    super(windowID, windowController);
    //logger.setLogLevel(Level.ALL); // uncomment if you want to add lover level logs specific to this class
    logger.trace(String.format("Created a new GameEditorWindow Instance: ID - %s", windowID));
  }

  @Override
  public SceneTypes getDefaultSceneType() {
    return WindowScenes.MAIN_SCENE;
  }

  @Override
  public AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
      logger.trace("Added new GameEditorMainScene ↑");
      return new GameEditorMainScene(this.sceneController);
    } else if (sceneType.equals(WindowScenes.EDITOR_SCENE)) {
      logger.trace("Added new GameEditorEditorScene ↑");
      return new GameEditorEditorScene(this.sceneController);
    } else if (sceneType.equals(WindowScenes.LOGIC_SCENE)) {
      logger.trace("Added new GameEditorLogicScene ↑");
      return new GameEditorLogicScene(this.sceneController);
    }
    logger.warn("Invalid scene type: "); // TODO add a sceneType string
    throw new IllegalArgumentException("Invalid scene type: " + sceneType);

    //TODO: replace with reflection
  }


}

```
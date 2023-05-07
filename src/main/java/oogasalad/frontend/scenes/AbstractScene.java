package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import oogasalad.frontend.managers.PropertiesObserver;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.managers.StandardThemeManager;
import oogasalad.frontend.managers.ThemeManager;
import oogasalad.frontend.managers.ThemeObserver;
import oogasalad.frontend.panels.PanelController;

/**
 * @author George Fang
 * @author Connor Wells
 * @author Owen MacKenzie
 *
 * This is the abstract scene class that all scene inherit from. It contains common functionality
 * that can be used by subclasses to create and manage scenes, such as a reference to the
 * PanelController and SceneMediator, which are used to manage the panels and mediate between
 * scenes, and the propertyManager and themeManager, which are used to manage properties and
 * themes. It also defines an abstract method makeScene() that is used to create the actual scene,
 * which must be implemented by subclasses. Additionally, it implements the PropertiesObserver and
 * ThemeObserver interfaces, allowing it to observe changes to properties and themes and update its
 * scene accordingly.
 */

public abstract class AbstractScene implements PropertiesObserver, ThemeObserver {

  protected PanelController panelController;
  protected SceneMediator sceneController;
  protected Scene scene;
  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();
  protected ThemeManager themeManager = StandardThemeManager.getInstance();

  /**
   * Constructor for the abstract scene
   */
  public AbstractScene() {
    this.scene = makeScene();
    propertyManager.addObserver(this);
    themeManager.addObserver(this);
  }

  /**
   * Overloaded constructor for the abstract scene with a scene controlller
   * @param sceneController
   */
  public AbstractScene(SceneMediator sceneController) {
    this.panelController = new PanelController(sceneController);
    this.sceneController = sceneController;
    this.scene = makeScene();
    propertyManager.addObserver(this);
    themeManager.addObserver(this);
  }

  /**
   * Makes the scene
   * @return
   */
  public abstract Scene makeScene();

  /**
   * Gets the scene
   * @return
   */
  public Scene getScene() {
    return scene;
  }

  /**
   * Gets the property manager
   * @return
   */
  protected PropertyManager getPropertyManager() {
    return propertyManager;
  }

  /**
   * Gets the theme manager
   * @return
   */
  protected ThemeManager getThemeManager() {
    return themeManager;
  }

  protected void setScene(Scene scene) {this.scene = scene;}

  /**
   * Sets the theme
   */
  public final void setTheme() {
    scene.getStylesheets().clear();
    scene.getStylesheets().addAll(themeManager.getTheme());
  }
}

package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oogasalad.frontend.managers.PropertiesObserver;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.managers.StandardThemeManager;
import oogasalad.frontend.managers.ThemeManager;
import oogasalad.frontend.managers.ThemeObserver;
import oogasalad.frontend.panels.PanelController;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public abstract class AbstractScene implements PropertiesObserver, ThemeObserver {

  protected PanelController panelController;
  protected SceneMediator sceneController;
  protected Scene scene;
  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();
  protected ThemeManager themeManager = StandardThemeManager.getInstance();

  public AbstractScene() {
    this.scene = makeScene();
    propertyManager.addObserver(this);
    themeManager.addObserver(this);
  }

  public AbstractScene(SceneMediator sceneController) {
    this.panelController = new PanelController(sceneController);
    this.sceneController = sceneController;
    this.scene = makeScene();
    propertyManager.addObserver(this);
    themeManager.addObserver(this);
  }

  public abstract Scene makeScene();

  public void showModal(AbstractScene modalScene) {
    Stage modalStage = new Stage();
    modalStage.initModality(Modality.APPLICATION_MODAL);
    modalStage.initOwner(modalStage);
    modalStage.setScene(modalScene.getScene());
    modalStage.setResizable(false);
    modalStage.setWidth(propertyManager.getNumeric("ModalWidth"));
    modalStage.setHeight(propertyManager.getNumeric("ModalHeight"));
    modalStage.showAndWait();
  }

  public Scene getScene() {
    return scene;
  }

  protected PropertyManager getPropertyManager() {
    return propertyManager;
  }

  protected ThemeManager getThemeManager() {
    return themeManager;
  }

  protected void setScene(Scene scene) {this.scene = scene;}

  public final void setTheme() {
    scene.getStylesheets().clear();
    scene.getStylesheets().addAll(themeManager.getTheme());
  }
}

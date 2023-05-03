package oogasalad.frontend.scenes;

import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.Controller.FilesController;
import oogasalad.frontend.panels.editorPanels.ComponentPanel;
import oogasalad.frontend.panels.editorPanels.EnvironmentPanel;
import oogasalad.frontend.panels.editorPanels.HeaderMenuPanel;
import oogasalad.frontend.panels.editorPanels.PropertiesPanel;

/**
 * @author George Fang
 * @author Owen MacKenzie
 */
public class GameEditorEditorScene extends AbstractScene {

  private static final String VISUAL_EDITOR_SCENE = "visual";
  private BorderPane root;
  private VBox rightTab; //REPLACE WITH A PANEL
  private Map<Button, VBox> buttonVBoxMap;
  private HeaderMenuPanel headerMenu;
  private ComponentPanel componentsPanel;
  private EnvironmentPanel environmentPanel;
  private PropertiesPanel propertiesPanel;
  /**
   * Constructor for the visual editor scene
   *
   * @param sceneController
   */
  public GameEditorEditorScene(SceneMediator sceneController) {
    super(sceneController);
  }

  private void setButtonVisualPanel(Button button, String title) {
    VBox newVisualPanel = new VBox();
    newVisualPanel.getChildren().add(new Label(title));
    button.setOnAction(e -> updateVisualPanel(button));
    buttonVBoxMap.put(button, newVisualPanel);
  }

  private void updateVisualPanel(Button button) {
    System.out.print(button.getText());
    //visualPanel = buttonVBoxMap.get(button);
    refreshScene();
    //sceneController.wirefreshScene();
  }

  /**
   * Creates the scene for the visual editor
   */
  @Override
  public Scene makeScene() {
    root = new BorderPane();
    createTopPanel();
    createLeftPanel();
    createRightPanel();
    createCenterPanel();
    refreshScene();
    return getScene();
  }

  private void refreshScene() {
    root.setRight(rightTab);
    root.setCenter(environmentPanel);
    root.setTop(headerMenu);
    root.setLeft(componentsPanel);
    root.setCenter(environmentPanel);
    setScene(new Scene(root));
    setText();
    setTheme();
  }

  private void createLeftPanel() {
    componentsPanel = new ComponentPanel();
    componentsPanel.setReferenceRoot(root);
    componentsPanel.setFiles(this.sceneController.getFilesController());
  }

  private void createRightPanel() {
    rightTab = new VBox();
//        buttonVBoxMap = new HashMap<>(); //TODO: what does this do?
//        Button boardButton = new Button("Board");
//        setButtonVisualPanel(boardButton, "Board");
//        Button variableButton = new Button("Variable");
//        setButtonVisualPanel(variableButton, "Variable");
//        Button playerButton = new Button("Player");
//        setButtonVisualPanel(playerButton, "Player");
//        Button backButton = new Button("Back");
//        backButton.setOnAction(e -> panelController.switchSceneFromPanel("main"));
//        rightTab.getChildren().addAll(boardButton,variableButton,playerButton,backButton);
    propertiesPanel = new PropertiesPanel();
    rightTab.getChildren().addAll(propertiesPanel);
  }

  private void createCenterPanel() {
    environmentPanel = new EnvironmentPanel();
  }

  private void createTopPanel() {
    headerMenu = new HeaderMenuPanel(panelController, VISUAL_EDITOR_SCENE);
//    headerMenu.setFiles(this.sceneController.getFilesController());
  }

  @Override
  public void setText() {
    //need this
  }

}

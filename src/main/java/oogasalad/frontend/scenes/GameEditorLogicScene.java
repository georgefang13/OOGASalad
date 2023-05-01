package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.NodeScene;
import oogasalad.frontend.panels.editorPanels.ComponentPanel;
import oogasalad.frontend.panels.editorPanels.HeaderMenuPanel;
import oogasalad.frontend.panels.editorPanels.NodeLogicPanel;

/**
 * @author George Fang
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GameEditorLogicScene extends AbstractScene {

  private BorderPane root;
  private HeaderMenuPanel headerMenu;
  private ComponentPanel componentPanel;
  private NodeController nodeController;
  private static final String LOGIC_EDITOR_SCENE = "logic";

  public GameEditorLogicScene(SceneMediator sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    root = new BorderPane();
    createTopPanel();
    createLeftPanel();
    createCenterPanel();
    refreshScene();
    return getScene();
  }

  private void refreshScene() {
    root.setCenter(nodeController.getScene().getRoot());
    root.setTop(headerMenu);
    root.setLeft(componentPanel.createSingleAccordionVBox());
    setScene(new Scene(root));
    setTheme();
  }

  private void createCenterPanel() {
    nodeController = new NodeController();
  }

  private void createTopPanel() {
    headerMenu = new HeaderMenuPanel(panelController, LOGIC_EDITOR_SCENE);
  }

  private void createLeftPanel() {
    componentPanel = new ComponentPanel();
  }

  @Override
  public void setText() {

  }
}

package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import oogasalad.frontend.panels.subPanels.ComponentPanel;
import oogasalad.frontend.panels.subPanels.EnvironmentPanel;
import oogasalad.frontend.panels.subPanels.HeaderMenuPanel;
import oogasalad.frontend.panels.subPanels.NodeLogicPanel;

/**
 * @author George Fang
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GameEditorLogicScene extends AbstractScene {
    private BorderPane root;
    private HeaderMenuPanel headerMenu;
    private ComponentPanel componentPanel;
    private NodeLogicPanel nodeLogicPanel;
    private static final String LOGIC_EDITOR_SCENE = "logic";

    public GameEditorLogicScene(SceneController sceneController) {
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
    private void refreshScene(){
        root.setCenter(nodeLogicPanel);
        root.setTop(headerMenu.createMenu());
        root.setLeft(componentPanel.createSingleAccordionVBox());
        root.setCenter(nodeLogicPanel.createNodeLogicEnvironment());
        setScene(new Scene(root));
        setTheme();
    }
    private void createCenterPanel(){
        nodeLogicPanel = new NodeLogicPanel();
    }
    private void createTopPanel(){
        headerMenu = new HeaderMenuPanel(panelController, LOGIC_EDITOR_SCENE);
    }
    private void createLeftPanel(){
        componentPanel = new ComponentPanel();
    }
    @Override
    public void setText() {

    }
}

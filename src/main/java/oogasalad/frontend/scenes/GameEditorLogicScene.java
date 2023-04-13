package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import oogasalad.frontend.panels.subPanels.EnvironmentPanel;
import oogasalad.frontend.panels.subPanels.HeaderMenuPanel;
import oogasalad.frontend.panels.subPanels.ComponentLibraryPanel;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 * @author George Fang
 */

public class GameEditorLogicScene extends AbstractScene {
    private BorderPane root;
    private HeaderMenuPanel headerMenu;
    private ComponentLibraryPanel componentLibraryPanel;
    private EnvironmentPanel environmentPanel;
    private static final String LOGIC_EDITOR_SCENE = "logic";

    public GameEditorLogicScene(SceneController sceneController) {
        super(sceneController);
    }

    @Override
    public Scene makeScene() {
        root = new BorderPane();
        headerMenu = new HeaderMenuPanel(panelController, LOGIC_EDITOR_SCENE);
        componentLibraryPanel = new ComponentLibraryPanel();
        environmentPanel = new EnvironmentPanel();
        refreshScene();
        return getScene();
    }
    private void refreshScene(){
        root.setCenter(environmentPanel);
        root.setTop(headerMenu.createMenu());
        root.setLeft(componentLibraryPanel.createAccordion());
        root.setCenter(environmentPanel.createEnvironment());
        setScene(new Scene(root));
        setTheme();
    }
    @Override
    public void setText() {

    }
}

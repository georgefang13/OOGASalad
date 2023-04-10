package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import oogasalad.frontend.panels.subPanels.EnvironmentPanel;
import oogasalad.frontend.panels.subPanels.HeaderMenuPanel;
import oogasalad.frontend.panels.subPanels.ObjectLibraryPanel;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 * @author George Fang
 */

public class GameEditorLogicScene extends AbstractScene {
    private BorderPane root;
    private HeaderMenuPanel headerMenu;
    private ObjectLibraryPanel objectLibraryPanel;
    private EnvironmentPanel environmentPanel;

    public GameEditorLogicScene(SceneController sceneController) {
        super(sceneController);
    }

    @Override
    public Scene makeScene() {
        root = new BorderPane();
        headerMenu = new HeaderMenuPanel(panelController);
        objectLibraryPanel = new ObjectLibraryPanel();
        environmentPanel = new EnvironmentPanel();
        refreshScene();
        return getScene();
    }
    private void refreshScene(){
        root.setCenter(environmentPanel);
        root.setTop(headerMenu.createMenu());
        root.setLeft(objectLibraryPanel.createAccordion());
        root.setCenter(environmentPanel.createEnvironment());
        setScene(new Scene(root));
        setTheme();
    }
    @Override
    public void setText() {

    }
}

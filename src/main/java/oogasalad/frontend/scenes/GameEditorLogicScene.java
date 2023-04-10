package oogasalad.frontend.scenes;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.panels.subPanels.EnvironmentPanel;
import oogasalad.frontend.panels.subPanels.HeaderMenuPanel;
import oogasalad.frontend.panels.subPanels.PropertiesPanel;
import oogasalad.frontend.windows.GameEditorWindow;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.managers.PropertiesManager;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 * @author George Fang
 */

public class GameEditorLogicScene extends AbstractScene {

    private Button editGridButton;
    private Label gameEditorLabel;
    private BorderPane root;
    private VBox leftTab; //REPLACE WITH A PANEL
    private Map<Button,VBox> buttonVBoxMap;
    private HeaderMenuPanel headerMenu;
    private PropertiesPanel propertiesPanel;
    private EnvironmentPanel environmentPanel;

    public GameEditorLogicScene(SceneController sceneController) {
        super(sceneController);
    }

    @Override
    public Scene makeScene() {
        root = new BorderPane();
        headerMenu = new HeaderMenuPanel(panelController);
        propertiesPanel = new PropertiesPanel();
        environmentPanel = new EnvironmentPanel();

        leftTab = new VBox();
        Button boardButton = new Button("Board");
        leftTab.getChildren().addAll(boardButton);

        refreshScene();
        return getScene();
    }
    private void refreshScene(){
        root.setRight(leftTab);
        root.setCenter(environmentPanel);
        root.setTop(headerMenu.createMenu());
        root.setLeft(propertiesPanel.createAccordion());
        root.setCenter(environmentPanel.createEnvironment());
        setScene(new Scene(root));
        setTheme();
    }
    @Override
    public void setText() {

    }
}

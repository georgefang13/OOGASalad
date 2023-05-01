package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.panels.gamePanels.GameSetupPanel;
/**
 * @author Owen MacKenzie
 */
public class GamePlayerSelectScene extends AbstractScene{
    public GamePlayerSelectScene(SceneMediator sceneController) {
        super(sceneController);
    }
    @Override
    public Scene makeScene() {
        BorderPane root = new BorderPane();
        root.setCenter(new GameSetupPanel(panelController));
        Scene scene = new Scene(root);
        setScene(scene);
        setText();
        setTheme();
        return getScene();
    }
    @Override
    public void setText() {

    }
}

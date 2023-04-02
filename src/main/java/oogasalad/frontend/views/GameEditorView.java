package oogasalad.frontend.views;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.controller.ViewController;
import oogasalad.frontend.components.factories.ButtonFactory;

public class GameEditorView extends View{
    /**
     * View constructor makes scene and sets scene in stage.
     *
     * @param stage      stage for displaying scene
     * @param controller
     */
    protected GameEditorView(Stage stage, ViewController controller) {
        super(stage, controller);
    }

    @Override
    protected Scene makeScene() {
        VBox box = new VBox();
        Scene scene = new Scene(box);
        return scene;
    }

    @Override
    protected Scene refreshScene() {
        return getScene();
    }
}

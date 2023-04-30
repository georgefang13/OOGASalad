package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GamePlayerSelectScene extends AbstractScene{
    public GamePlayerSelectScene(SceneController sceneController) {
        super(sceneController);
    }
    @Override
    public Scene makeScene() {
        BorderPane root = new BorderPane();

        Button btn = new Button("Start Local Game");
        //btn.setOnAction(e -> startGame("local"));
        Button btn2 = new Button("Create Online Game");
        //btn2.setOnAction(e -> startGame("create"));
        TextField codeField = new TextField();
        codeField.setPromptText("Enter Code");
        Button btn3 = new Button("Join Online Game");
        /*
        btn3.setOnAction(e -> {
            code = codeField.getText();
            startGame("join");
        });

         */
        VBox hbox = new VBox(btn, btn2, codeField, btn3);
        root.setCenter(hbox);
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

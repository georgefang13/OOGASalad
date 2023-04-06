package oogasalad;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.windowscene.controllers.WindowController;
import oogasalad.windowscene.controllers.WindowMediator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import oogasalad.gameeditor.frontend.components.modals.SubDisplayModals.AlertModal;
import oogasalad.gameeditor.frontend.components.modals.SubInputModals.CreateGameModal;


/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */
public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    WindowMediator mediator = new WindowController();
      /*
        VBox root = new VBox();
        Button button = new Button("Create Game Modal");
        button.setOnAction(e -> {
            CreateGameModal modal = new CreateGameModal();
            modal.showAndWait();
        });
        root.getChildren().add(button);
        Button errorButton = new Button("Create Error Modal");
        errorButton.setOnAction(e -> {
            AlertModal modal = new AlertModal();
            modal.showAndWait();
        });
        root.getChildren().add(errorButton);
        Scene scene = new Scene(root, 1000, 700);

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        */
    }

    public static void main(String[] args) {
        launch(args);
    }
}

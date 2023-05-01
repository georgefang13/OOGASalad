package oogasalad.frontend.modals;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.frontend.modals.subDisplayModals.AlertModal;
import oogasalad.frontend.modals.subInputModals.CreateNewModal;


public class ModalRunner extends Application {

  public void start(Stage stage) {
    VBox root = new VBox();
    Button button = new Button("Create Game Modal");
    button.setOnAction(e -> {
      CreateNewModal modal = new CreateNewModal("Create_Game_Modal", null);
      modal.showAndWait();
    });
    root.getChildren().add(button);
    Button playerButton = new Button("Create New Player");
    playerButton.setOnAction(e -> {
      CreateNewModal modal = new CreateNewModal("Create_Player_Modal", null);
      modal.showAndWait();
    });
    root.getChildren().add(playerButton);
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
  }

  public static void main(String[] args) {
    launch(args);
  }

}

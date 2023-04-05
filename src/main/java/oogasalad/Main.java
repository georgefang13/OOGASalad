package oogasalad;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import oogasalad.frontend.components.modals.Modal;
import oogasalad.frontend.components.modals.SubDisplayModals.AlertModal;
import oogasalad.frontend.components.modals.SubInputModals.CreateGameModal;
import oogasalad.frontend.components.modals.InputModal;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application{
    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }

    /**
     * Start of the program.
     */

//    public static void main (String[] args) {
//        Main m = new Main();
//        System.out.println(m.getVersion());
//    }
    public void start(Stage stage) {
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
    }

    public static void main(String[] args) {
        launch(args);
    }

}

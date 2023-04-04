package oogasalad;


import static javafx.application.Application.launch;

import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {
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

    public static void main (String[] args) {
        launch(args);
        // from Duvall below
        Main m = new Main();
        System.out.println(m.getVersion());
    }

    public void start(Stage stage) throws Exception {
        Button button = new Button("Open Modal");
        button.setOnAction(e -> {
            AModal modal = new Modal();
            modal.showAndWait();
        });
    }
}
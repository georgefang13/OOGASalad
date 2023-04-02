package oogasalad;


import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.ViewController;
import oogasalad.frontend.views.OriginView;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }

    /**
     * Start of the program.
     */

    public static void main (String[] args) {
        launch(args);
        // from Duvall below
        Main m = new Main();
        System.out.println(m.getVersion());
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewController viewController = new ViewController(stage);

    }
}

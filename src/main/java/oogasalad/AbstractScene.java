package oogasalad;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;

public abstract class AbstractScene extends Scene {
    protected Node root;
    public AbstractScene() {
        Parent root = new Parent() {
        };
        super(root, 600,400); //TODO: Properties file
    }

    public abstract void makeScene();
}

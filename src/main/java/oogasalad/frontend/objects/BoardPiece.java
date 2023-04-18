package oogasalad.frontend.objects;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.draggableComponent.DraggableObject;
import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.frontend.components.gameObjectComponent.GameObjectComponent;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BoardPiece extends DraggableObject implements GameObjectComponent {
    private String name;
    private List<Node> children;
    private boolean playable;
    private final String DEFAULT_FILE_PATH = "frontend.properties.Defaults.GameObject";
    private ResourceBundle DEFAULT_BUNDLE = ResourceBundle.getBundle(DEFAULT_FILE_PATH);

    public BoardPiece(String pieceName, int ID) {
        super(ID);
        children = null;
        setName(pieceName);
        setImage(readImageFileFromFile(pieceName));
        followMouse();
    }
    private String readImageFileFromFile(String pieceName) {
        switch (pieceName) {
            case "X":
                return DEFAULT_BUNDLE.getString("X_IMAGE");
            case "O":
                return DEFAULT_BUNDLE.getString("DEFAULT_IMAGE");
            default:
                return DEFAULT_BUNDLE.getString("DEFAULT_IMAGE");
        }
    }
    @Override
    public Node getNode() {
        return getImage();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Node> getChildren() {
        return null;
    }

    @Override
    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    public String getName(){
        return name;
    }


}

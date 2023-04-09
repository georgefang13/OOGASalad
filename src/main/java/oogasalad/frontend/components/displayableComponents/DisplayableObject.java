package oogasalad.frontend.components.displayableComponents;

import javafx.scene.Node;

public class DisplayableObject extends AbstractDisplayableObject implements DisplayableComponent {
    public DisplayableObject(int num, Node container) {
        super(num, container);
    }

    public DisplayableObject(int ID) {
        super(ID);
    }

    @Override
    public Node getNode() {
        return null;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setID(int id) {

    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public void setZIndex(int zIndex) {

    }

    @Override
    public void setImage(String imagePath) {

    }

    @Override
    public void setSize(int size) {

    }
}

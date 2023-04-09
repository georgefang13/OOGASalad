package oogasalad.frontend.components.displayableComponents;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.Point;

public class DisplayableObject extends AbstractDisplayableObject implements DisplayableComponent {
    private boolean visible;
    private int zIndex;
    private int size;
    private ImageView image;
    private Point absolute;
    private Point editor;
    private double xOffset;
    private double yOffset;


    public DisplayableObject(int num, Node container) {
        super(num, container);
    }
    public DisplayableObject(int ID) {
        super(ID);
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void setZIndex(int zIndex) {
        image.setTranslateZ(zIndex);
        absolute.setZ(zIndex);
        editor.setZ(zIndex);
    }

    @Override
    public void setImage(String imagePath) {
        Image newImage = new Image(imagePath);
        image.setImage(newImage);
    }

    @Override
    public ImageView getImage() {
        return image;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
        image.setScaleX(size);
        image.setScaleY(size);
    }

    @Override
    public double getxOffset() {
        return xOffset;
    }

    @Override
    public double getyOffset() {
        return yOffset;
    }

    @Override
    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    @Override
    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

}

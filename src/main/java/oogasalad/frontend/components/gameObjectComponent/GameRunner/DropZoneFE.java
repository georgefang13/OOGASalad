package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.Controller.GameController;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.DropZoneVisual;
import oogasalad.frontend.managers.DisplayManager;

/**
 * @author Owen MacKenzie
 */

public class DropZoneFE extends GameRunnerObject{
    public enum DropZoneDistribution{
        COLLAPSE, HORIZONTAL, VERTICAL
    }
    private final int x;
    private final int y;
    private Node unselectedImage;
    private DropZoneDistribution distribution = DropZoneDistribution.COLLAPSE;
    public DropZoneFE(String ID, AbstractSelectableVisual.SelectableVisualParams unselected, AbstractSelectableVisual.SelectableVisualParams selected, int width, int height, int x, int y, GameController gameRunnerController) {
        super(ID, gameRunnerController);
        setWidth(width);
        setHeight(height);
        this.x = x;
        this.y = y;
        setSelectableVisual(unselected,selected);
    }
    @Override
    public void setSelectableVisual(AbstractSelectableVisual.SelectableVisualParams unselected, AbstractSelectableVisual.SelectableVisualParams selected) {
        this.unselectedImage = createImage(unselected.hasSelectImage(),unselected.param());
        setSelectVisual(selected);
    }

    @Override
    public void setSelectVisual(AbstractSelectableVisual.SelectableVisualParams selected) {
        Node selectedImage = createImage(selected.hasSelectImage(),selected.param());
        selectableVisual = new DropZoneVisual(unselectedImage,selectedImage,getWidth(),getHeight(),x,y,ID);
    }

    private Node loadDefaultDropRectangle(String hexColor){
        Color fillColor = Color.web(hexColor);
        return new Rectangle(getWidth(), getHeight(), fillColor);
    }
    private Node createImage(boolean isImage, String param){
        Node visual;
        if (isImage){
            visual = DisplayManager.loadImage(param,getHeight(),getWidth());
        } else {
            visual = loadDefaultDropRectangle(param);
        }
        return visual;
    }
    public Point2D getDropZoneCenter(){
        return getNode().localToScene(((double) getWidth())/2, ((double) getHeight())/2);
    }
    public DropZoneDistribution getDistribution() {
        return distribution;
    }
    public void setDistribution(DropZoneDistribution distribution) {
        this.distribution = distribution;
    }
    // get dropzone bounds
    public Bounds getDropZoneBounds(){
        return getNode().localToScene(getNode().getBoundsInLocal());
    }
}

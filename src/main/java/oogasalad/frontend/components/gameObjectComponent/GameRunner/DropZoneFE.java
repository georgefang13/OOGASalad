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

    /**
     * Constructor for DropZoneFE
     * @param ID
     * @param unselected
     * @param selected
     * @param width
     * @param height
     * @param x
     * @param y
     * @param gameRunnerController
     */
    public DropZoneFE(String ID, AbstractSelectableVisual.SelectableVisualParams unselected, AbstractSelectableVisual.SelectableVisualParams selected, int width, int height, int x, int y, GameController gameRunnerController) {
        super(ID, gameRunnerController);
        setWidth(width);
        setHeight(height);
        this.x = x;
        this.y = y;
        setSelectableVisual(unselected,selected);
    }

    /**
     * Sets the visual of the dropzone
     * @param unselected
     * @param selected
     */
    @Override
    public void setSelectableVisual(AbstractSelectableVisual.SelectableVisualParams unselected, AbstractSelectableVisual.SelectableVisualParams selected) {
        this.unselectedImage = createImage(unselected.hasSelectImage(),unselected.param());
        setSelectVisual(selected);
    }

    /**
     * Sets the visual of the dropzone when selected
     * @param selected
     */
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

    /**
     * Gets the center of the dropzone
     * @return
     */
    public Point2D getDropZoneCenter(){
        return getNode().localToScene(((double) getWidth())/2, ((double) getHeight())/2);
    }

    /**
     * Gets the distribution of the dropzone
     * @return
     */
    public DropZoneDistribution getDistribution() {
        return distribution;
    }

    /**
     * Sets the distribution of the dropzone
     * @param distribution
     */
    public void setDistribution(DropZoneDistribution distribution) {
        this.distribution = distribution;
    }

    /**
     * Get dropzone bounds
     * @return
     */
    public Bounds getDropZoneBounds(){
        return getNode().localToScene(getNode().getBoundsInLocal());
    }
}

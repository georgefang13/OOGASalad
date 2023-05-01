package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import oogasalad.Controller.GameController;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.DropZoneVisual;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.PieceVisualSelectBorder;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.PieceVisualSelectImage;
/**
 * @author Owen MacKenzie
 */
public class Piece extends GameRunnerObject{
    private double lastTranslateX;
    private double lastTranslateY;
    private AbstractSelectableVisual.SelectableVisualParams unselected;

    /**
     * Constructor for Piece
     * @param ID
     * @param gameRunnerController
     * @param imagePath
     * @param hasSelectImage
     * @param param
     * @param height
     * @param width
     */
    public Piece(String ID, GameController gameRunnerController, String imagePath, boolean hasSelectImage, String param, int height, int width) {
        super(ID, gameRunnerController);
        setHeight(height);
        setWidth(width);
        setSelectableVisual(new AbstractSelectableVisual.SelectableVisualParams(true,imagePath),new AbstractSelectableVisual.SelectableVisualParams(hasSelectImage,param));
    }

    /**
     * Sets the selectable visual
     * @param unselected
     * @param selected
     */
    @Override
    public void setSelectableVisual(AbstractSelectableVisual.SelectableVisualParams unselected, AbstractSelectableVisual.SelectableVisualParams selected) {
        this.unselected = unselected;
        setSelectVisual(selected);
    }

    /**
     * Sets the select visual
     * @param selected
     */
    @Override
    public void setSelectVisual(AbstractSelectableVisual.SelectableVisualParams selected) {
        if (selected.hasSelectImage()){
            selectableVisual = new PieceVisualSelectImage(unselected.param(),selected.param(),getHeight(),getWidth(),ID);
        } else {
            selectableVisual = new PieceVisualSelectBorder(unselected.param(),selected.param(),getHeight(),getWidth(),ID);
        }
        followMouse();
        setDragSelection();
    }

    /**
     * Makes the piece follow the mouse
     *
     * @param dropZoneCenter
     */
    public void moveToDropZoneXY(Point2D dropZoneCenter){
        moveToXY(dropZoneCenter);
        acceptDrag();
    }
    private void setDragSelection() {
        getNode().setOnDragDetected(e -> {
            if (playable){
                active = true;
                gameRunnerController.select(ID);
            }
        });
        getNode().setOnMouseReleased(e -> selectDropZoneBelow());
    }
    private void selectDropZoneBelow(){
        DropZoneVisual dropZoneVisual = getIntersectingDropZones();
        if (dropZoneVisual == null){
            goBack();
            return;
        }
        if (gameRunnerController.isObjectPlayable(dropZoneVisual.getObjectID())){
            gameRunnerController.select(dropZoneVisual.getObjectID());
            acceptDrag();
            active = false;
            setDraggable(playable);
            if (playable) {
                makePlayable();
            } else {
                makeUnplayable();
            }

        } else {
            goBack();
        }
    }
    private DropZoneVisual getIntersectingDropZones(){
        Bounds pieceBounds = getNode().localToScene(getNode().getBoundsInLocal());
        for (Node n : getNode().getParent().getChildrenUnmodifiable()) {
            if (n instanceof DropZoneVisual){
                if (n.localToScene(n.getBoundsInLocal()).intersects(pieceBounds)){
                    return (DropZoneVisual) n;
                }
            }
        }
        return null;
    }
    private void acceptDrag() {
        Node node = getNode();
        lastTranslateX = node.getTranslateX();
        lastTranslateY = node.getTranslateY();
    }
    private void goBack() {
        Node node = getNode();
        node.setTranslateX(lastTranslateX);
        node.setTranslateY(lastTranslateY);
    }
}

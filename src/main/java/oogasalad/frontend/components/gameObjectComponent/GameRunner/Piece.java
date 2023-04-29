package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import oogasalad.gamerunner.backend.GameController;

public class Piece extends GameRunnerObject{
    private double lastTranslateX;
    private double lastTranslateY;
    public Piece(String ID, GameController gameRunnerController, String imagepath, double size) {
        super(ID, gameRunnerController);
        setImage(imagepath);
        setSize(size);
        setSelectableVisual();
        followMouse();
        setDragSelection();
    }
    @Override
    void setSelectableVisual() {
        ImageView img = getImage();
        img.setFitWidth(size);
        img.setFitHeight(size);
        selectableVisual = new PieceVisual(img,size,ID);
    }

    private void setDragSelection() {
        getNode().setOnDragDetected(e -> gameRunnerController.select(ID));
        getNode().setOnMouseReleased(e -> selectDropZoneBelow());
    }
    private void selectDropZoneBelow(){
        DropZoneVisual dzv = getIntersectingDropZones();
        gameRunnerController.select(dzv.getObjectID());
    }
    public DropZoneVisual getIntersectingDropZones(){
        Bounds pieceBounds = getNode().localToScene(getNode().getBoundsInLocal());
        for (Node n : getNode().getParent().getChildrenUnmodifiable()) {
            if (n instanceof DropZoneVisual){
                if (n.localToScene(n.getBoundsInLocal()).intersects(pieceBounds)){
                    return (DropZoneVisual) n;
                }
            }
        }
        //goBack();
        return null;
    }

    public void moveToDropZoneXY(DropZoneFE.DropZoneCenter DZcenter){
        setCentertoCenter(DZcenter.x(), DZcenter.y());
        //acceptDrag();
    }
    private void setCentertoCenter(double x, double y){
        //double actualX = pieceBox.getLayoutX() + pieceBox.getTranslateX();
        //double actualY = pieceBox.getLayoutY() + pieceBox.getTranslateY();
        double shiftX = x - size/2;
        double shiftY = y - size/2;
        //this.resetOffset();
        getNode().setTranslateX(shiftX);
        getNode().setTranslateY(shiftY);
    }
    public void acceptDrag() {
        Node node = getNode();
        lastTranslateX = node.getTranslateX();
        lastTranslateY = node.getTranslateY();
    }

    public void goBack() {
        Node node = getNode();
        node.setTranslateX(lastTranslateX);
        node.setTranslateY(lastTranslateY);
    }
}

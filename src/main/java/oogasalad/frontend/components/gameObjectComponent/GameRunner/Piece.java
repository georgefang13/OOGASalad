package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import oogasalad.frontend.managers.DisplayManager;
import oogasalad.gamerunner.backend.GameController;

public class Piece extends GameRunnerObject{
    private double lastTranslateX;
    private double lastTranslateY;
    private boolean hasSelectImage;
    private Object param;
    public Piece(String ID, GameController gameRunnerController, String imagePath, boolean hasSelectImage, Object param, double height, double width) {
        super(ID, gameRunnerController);
        setImage(imagePath);
        this.hasSelectImage = hasSelectImage;
        this.param = param;
        setHeight(height);
        setWidth(width);
        setSelectableVisual();
        //getNode().setOnMouseClicked(e -> gameRunnerController.select(ID));
        followMouse();
        setDragSelection();
    }
    @Override
    public void setSelectableVisual() {
        ImageView img = getImage();
        img.setFitWidth(getWidth());
        img.setFitHeight(getHeight());
        if (hasSelectImage){
            String selectImgPath = (String) param;
            Node selectImage = DisplayManager.loadImage(selectImgPath,(int) getHeight(),(int) getWidth());
            selectableVisual = new PieceVisualSelectImage(img,selectImage,getHeight(),getWidth(),ID);
        } else {
            Color selectBorderColor = (Color) param;
            selectableVisual = new PieceVisualSelectBorder(img,selectBorderColor,getHeight(),getWidth(),ID);
        }
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
        if (gameRunnerController.isObjectPlayable(dropZoneVisual.objectID)){
            gameRunnerController.select(dropZoneVisual.getObjectID());
            acceptDrag();
            active = false;
            setDraggable(playable);
        } else {
            goBack();
        }

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
        return null;
    }

    public void moveToDropZoneXY(Point2D dropZoneCenter){
        setCentertoCenter(dropZoneCenter.getX(), dropZoneCenter.getY());
        acceptDrag();
    }
    private void setCentertoCenter(double x, double y){
        double currentTranslateX = getNode().getTranslateX();
        double currentTranslateY = getNode().getTranslateY();
        System.out.println(currentTranslateX);
        System.out.println(currentTranslateY);
        double shiftX = x; //- getWidth()/2;
        double shiftY = y; //- getHeight()/2;
        System.out.println(shiftX);
        System.out.println(shiftY);
        if (getNode() != null) {
            Point2D positionInScene = getNode().localToScene(getWidth()/2,getHeight()/2);
            shiftX -= positionInScene.getX();
            System.out.println(shiftX);
            shiftY -= positionInScene.getY();
            System.out.println(shiftY);
        } else {
            currentTranslateX = 0;
            currentTranslateY = 0;

        }
        getNode().setTranslateX(currentTranslateX + shiftX);
        getNode().setTranslateY(currentTranslateY + shiftY);
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

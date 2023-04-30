package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.*;
import oogasalad.frontend.managers.DisplayManager;
import oogasalad.Controller.GameController;

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
    public void moveToDropZoneXY(Point2D dropZoneCenter){
        Point2D pieceCenter = getNode().localToScene(getWidth()/2,getHeight()/2);
        double shiftX = dropZoneCenter.getX() - pieceCenter.getX();
        double shiftY = dropZoneCenter.getY() - pieceCenter.getY();
        getNode().setTranslateX(getNode().getTranslateX() + shiftX);
        getNode().setTranslateY(getNode().getTranslateY() + shiftY);
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
        SelectableVisual dropZoneVisual = getIntersectingDropZones();
        if (dropZoneVisual == null){
            goBack();
            return;
        }
        if (gameRunnerController.isObjectPlayable(dropZoneVisual.getObjectID())){
            gameRunnerController.select(dropZoneVisual.getObjectID());
            acceptDrag();
            active = false;
            setDraggable(playable);
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

    @Override
    public void setHighlight(String img){
        boolean isImg = !img.startsWith("#");
        if (hasSelectImage == isImg){
            if (hasSelectImage){
                selectableVisual.updateClickableVisual(DisplayManager.loadImage(img, (int) getHeight(), (int) getWidth()));
            } else {
                Color c = Color.web(img);
                Rectangle r = new Rectangle(getWidth(),getHeight(),c);
                selectableVisual.updateClickableVisual(r);
            }
        }
        else {
            hasSelectImage = isImg;
            param = isImg ? img : Color.web(img);
            if (isImg){
                Node selectImage = DisplayManager.loadImage(img, (int) getHeight(), (int) getWidth());
                Node origImg = ((PieceVisualSelectBorder) selectableVisual).getImage();
                selectableVisual = new PieceVisualSelectImage(origImg,selectImage,getHeight(),getWidth(),ID);
            }
            else {
                Color c = Color.web(img);
                Node origImg = ((PieceVisualSelectImage) selectableVisual).getImage();
                selectableVisual = new PieceVisualSelectBorder(origImg,c,getHeight(),getWidth(),ID);
            }
        }



    }
}

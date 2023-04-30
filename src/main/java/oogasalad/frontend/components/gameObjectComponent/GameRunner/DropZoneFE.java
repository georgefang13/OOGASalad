package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import oogasalad.gamerunner.backend.GameController;

public class DropZoneFE extends GameRunnerObject{
    private DropZoneVisual dropZoneVisual;
    public DropZoneFE(String ID, Node unselected, Node selected, int width, int height, int x, int y, GameController gameRunnerController) {
        super(ID, gameRunnerController);
        setWidth(width);
        setHeight(height);

        dropZoneVisual = new DropZoneVisual(unselected,selected,width,height,x,y,ID);
        setSelectableVisual();
    }
    @Override
    public void setSelectableVisual() {
        selectableVisual = dropZoneVisual;
    }

    public Point2D getDropZoneCenter(){
        Point2D positionInScene = getNode().localToScene(getWidth()/2,getHeight()/2);
        return positionInScene;
    }

}

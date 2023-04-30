package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.gamerunner.backend.GameController;
import javafx.scene.Node;

public class DropZoneFE extends GameRunnerObject{
    private DropZoneVisual dropZoneVisual;
    private DropZoneCenter dropZoneCenter;
    public DropZoneFE(String ID, Node unselected, Node selected, int width, int height, int x, int y, GameController gameRunnerController) {
        super(ID, gameRunnerController);

        double centerX = toCenter(x,width);
        double centerY = toCenter(y,height);
        dropZoneCenter = new DropZoneCenter(centerX,centerY);

        dropZoneVisual = new DropZoneVisual(unselected,selected,width,height,x,y,ID);
        setSelectableVisual();
        followMouse();
    }
    @Override
    public void setSelectableVisual() {
        selectableVisual = dropZoneVisual;
    }

    record DropZoneCenter(double x, double y){};

    private double toCenter(int start,int length){
        return start + ((double) length)/2;
    }

    public DropZoneCenter getDropZoneCenter(){
        return dropZoneCenter;
    }

}

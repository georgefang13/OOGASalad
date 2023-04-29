package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.gamerunner.backend.GameController;

public class DropZoneFE extends GameRunnerObject{
    private DropZoneVisual dropZoneVisual;
    private DropZoneCenter dropZoneCenter;
    public DropZoneFE(String ID, int width, int height, int x, int y, GameController gameRunnerController) {
        super(ID, gameRunnerController);

        Rectangle unselectedfill = new Rectangle(width, height);
        unselectedfill.setStroke(Color.BLACK);
        unselectedfill.setFill(Color.SKYBLUE);

        Rectangle selectedfill = new Rectangle(width, height);
        selectedfill.setStroke(Color.BLACK);
        selectedfill.setFill(Color.YELLOW);

        double centerX = toCenter(x,width);
        double centerY = toCenter(y,height);
        dropZoneCenter = new DropZoneCenter(centerX,centerY);

        dropZoneVisual = new DropZoneVisual(unselectedfill,selectedfill,width,height,x,y,ID);
        setSelectableVisual();
    }
    @Override
    void setSelectableVisual() {
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

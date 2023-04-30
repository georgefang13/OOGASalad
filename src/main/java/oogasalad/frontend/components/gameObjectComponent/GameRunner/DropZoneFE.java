package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.Controller.GameController;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.DropZoneVisual;
import oogasalad.frontend.managers.DisplayManager;

public class DropZoneFE extends GameRunnerObject{
    private final SelectableVisualParams unselectedParams;
    private SelectableVisualParams selectedParams;
    private final int x;
    private final int y;
    public DropZoneFE(String ID, SelectableVisualParams unselected, SelectableVisualParams selected, int width, int height, int x, int y, GameController gameRunnerController) {
        super(ID, gameRunnerController);
        setWidth(width);
        setHeight(height);
        this.unselectedParams = unselected;
        this.selectedParams = selected;
        this.x = x;
        this.y = y;
        setSelectableVisual();
    }
    @Override
    public void setSelectableVisual() {
        Node unselected = createImage(unselectedParams.hasSelectImage,unselectedParams.param);
        Node selected = createImage(selectedParams.hasSelectImage,selectedParams.param);
        selectableVisual = new DropZoneVisual(unselected,selected,getWidth(),getHeight(),x,y,ID);
    }

    public record SelectableVisualParams(boolean hasSelectImage, String param){}

    private Node loadDefaultDropRectangle(String hexColor){
        Color fillColor = Color.web(hexColor);
        return new Rectangle(getWidth(), getHeight(), fillColor);
    }
    private Node createImage(boolean isImage, String param){
        Node visual;
        if (isImage){
            visual = DisplayManager.loadImage(param,(int) getHeight(),(int) getWidth());
        } else {
            visual = loadDefaultDropRectangle(param);
        }
        return visual;
    }

    public Point2D getDropZoneCenter(){
        return getNode().localToScene(getWidth()/2,getHeight()/2);
    }

    @Override
    public void setHighlight(String img){
        boolean hasSelectImage = !img.startsWith("#");
        selectedParams = new SelectableVisualParams(hasSelectImage, img);
        setSelectableVisual();
    }

}

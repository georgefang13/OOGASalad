package oogasalad.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import oogasalad.frontend.components.Arrow;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;
import oogasalad.frontend.components.gridObjectComponent.GridObject;

public class DropZoneController implements ControllerSubscriber {

  private Dropzone previous;
  private Dropzone current;
  private Pane root;
  private List<Dropzone> validatedDropzone;
  private List<GridObject> validatedGridObject;
  private Map<Dropzone, Dropzone> arrowConnections;

  public DropZoneController(){
    arrowConnections = new HashMap<>();
    validatedDropzone = new ArrayList<>();
    validatedGridObject = new ArrayList<>();
  }

  public void createArrow(){
    Arrow connection = new Arrow(previous, current);
    root.getChildren().add(connection.getArrow());
  }

  /**
   *
   */
  @Override
  public void update() {
    createArrow();
  }

  /**
   *
   */
  @Override
  public void setDropZones(Dropzone d) {
    if(previous == null){
      previous = d;
    }
    else{
      current = d;
      if(previous != current && !(arrowConnections.get(previous) == current)){
        current = d;
        createArrow();
        resetDropZones();
      }
      resetDropZones();
    }
  }

  private void resetDropZones() {
    previous = null;
    current = null;
  }

  public void addDropZone(Component c){
    try{
      validatedDropzone.add((Dropzone) c);
      ((Dropzone) c).addControllerSubscriber(this);
    } catch (Exception e){
      //TODO Add Logging Component

    }
  }
  public void addGridObject(Component c){
    try{
      validatedGridObject.add((GridObject) c);
      Dropzone[][] zones = ((GridObject) c).getDropZones();
      for(Dropzone[] row: zones){
        for(Dropzone dropzone: row){
          dropzone.addControllerSubscriber(this);
        }
      }
    } catch (Exception e){
      //TODO Add Logging Component
    }
  }
  public void setRoot(Pane rt){
    root = rt;
  }
}

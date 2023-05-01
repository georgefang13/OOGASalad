package oogasalad.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import oogasalad.frontend.components.Arrow;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.Subscriber;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;

public class DropZoneController implements ControllerSubscriber {

  private Dropzone previous;
  private Dropzone current;
  private Pane root;
  private List<Dropzone> validatedDropzone;
  private Map<Dropzone, Dropzone> arrowConnections;

  public DropZoneController(){
    arrowConnections = new HashMap<>();
    validatedDropzone = new ArrayList<>();
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
  public void setRoot(Pane rt){
    root = rt;
  }
}

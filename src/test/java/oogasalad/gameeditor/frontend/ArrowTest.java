package oogasalad.gameeditor.frontend;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oogasalad.Controller.DropZoneController;
import oogasalad.frontend.components.Arrow;
import oogasalad.frontend.components.ComponentsFactory;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrowTest extends DukeApplicationTest {

  private ComponentsFactory factory;
  private Pane root;
  private DropZoneController dropZoneController;
  private Dropzone c;
  private Dropzone c1;
  @Override
  public void start(Stage stage){
    dropZoneController = new DropZoneController();
    root = new Pane();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    Map<String, String> params = new HashMap<>();
    params.put("fill", "Blue");
    params.put("border", "Black");
    params.put("width", "50");
    params.put("height", "50");
    factory = new ComponentsFactory();
    c = (Dropzone) factory.create("Dropzone", params);
    params.remove("fill");
    params.put("fill", "Red");
    c1 = (Dropzone) factory.create("Dropzone", params);
    root.getChildren().add(c.getNode());
    root.getChildren().add(c1.getNode());
  }

  @Test
  public void ArrowCenterTests(){
    c1.getNode().setTranslateY(150);
    c1.getNode().setTranslateX(175);
    dropZoneController.addDropZone(c);
    dropZoneController.addDropZone(c1);
    dropZoneController.setRoot(root);
    clickOn(c.getNode());
    clickOn(c.getNode());
    clickOn(c1.getNode());
    clickOn(c1.getNode());
    for(List<Arrow> arrows: dropZoneController.getArrow()){
      for (Arrow arrow: arrows){
        assertEquals(25, arrow.getStartX());
        assertEquals(25, arrow.getStartY());
        assertEquals(200, arrow.getEndX());
        assertEquals(175, arrow.getEndY());
      }
    }
  }
}

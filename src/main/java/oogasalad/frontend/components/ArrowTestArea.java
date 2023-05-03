package oogasalad.frontend.components;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oogasalad.Controller.DropZoneController;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;

public class ArrowTestArea extends Application {

  private ComponentsFactory factory;
  private Pane root;
  private DropZoneController dropZoneController;

  @Override
  public void start(Stage stage) throws Exception {
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
    Dropzone c = (Dropzone) factory.create("Dropzone", params);
    params.remove("fill");
    params.put("fill", "Red");
    Dropzone c1 = (Dropzone) factory.create("Dropzone", params);
    root.getChildren().add(c.getNode());
    root.getChildren().add(c1.getNode());
//   c1.getNode().setTranslateX(50);
    c1.getNode().setTranslateY(150);
    System.out.println(c.getNode().getTranslateX());
    System.out.println(c1.getNode().getTranslateX());
    dropZoneController.addDropZone(c);
    dropZoneController.addDropZone(c1);
    dropZoneController.setRoot(root);
  }
}

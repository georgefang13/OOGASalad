package oogasalad.gameeditor.frontend;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;
import oogasalad.frontend.components.gameObjectComponent.GameObject;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComponentsFactoryTest extends DukeApplicationTest {

  private ComponentsFactory factory;
  private Pane root;
  @Override
  public void start(Stage stage) {
    factory = new ComponentsFactory();
    root = new Pane();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

//  @Test
//  public void createDefault() {
//    Component c = factory.create("GameObject");
//    Platform.runLater(() -> {
//      root.getChildren().add(c.getNode());
//    });
//    System.out.println(c.getNode().getBoundsInLocal().getMinX());
//    simulateMousePress(c.getNode(), MouseButton.PRIMARY, 0, 0);
//    simulateMouseDrag(c.getNode(), MouseButton.PRIMARY, 500, 500);
//    simulateMouseRelease(c.getNode(), MouseButton.PRIMARY, 500, 500);
//    System.out.println(c.getNode().getBoundsInLocal().getMinX());
//  }

//  @Test
//  public void createFromMap(){
//    Map<String, String> map = new HashMap<>();
//    map.put("name", "Hello");
//    GameObject c = (GameObject) factory.create("GameObject", map);
//    System.out.println(c.getNode().getBoundsInLocal().getMinX());
//    simulateMousePress(c.getNode(), MouseButton.PRIMARY, 0, 0);
//    simulateMouseDrag(c.getNode(), MouseButton.PRIMARY, 500,500);
//    simulateMouseRelease(c.getNode(), MouseButton.PRIMARY, 500,500);
//    assertEquals("Hello",c.getName());
//  }
  private void simulateMousePress(Node node, MouseButton button, double x, double y) {
    MouseEvent event = new MouseEvent(
        MouseEvent.MOUSE_PRESSED,
        x, y, x, y,
        button, 1,
        false, false, false, false, true, false, false, false, false, false, null
    );
    Platform.runLater(() -> node.fireEvent(event));
  }

  private void simulateMouseDrag(Node node, MouseButton button, double x, double y) {
    MouseEvent event = new MouseEvent(
        MouseEvent.MOUSE_DRAGGED,
        x, y, x, y,
        button, 1,
        false, false, false, false, true, false, false, false, false, false, null
    );
    Platform.runLater(() -> node.fireEvent(event));
  }

  private void simulateMouseRelease(Node node, MouseButton button, double x, double y) {
    MouseEvent event = new MouseEvent(
        MouseEvent.MOUSE_RELEASED,
        x, y, x, y,
        button, 1,
        false, false, false, false, false, false, false, false, false, false, null
    );
    Platform.runLater(() -> node.fireEvent(event));
  }
}

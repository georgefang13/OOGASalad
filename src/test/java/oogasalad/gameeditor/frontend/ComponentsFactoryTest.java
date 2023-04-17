package oogasalad.gameeditor.frontend;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ComponentsFactoryTest extends DukeApplicationTest {

  ComponentsFactory factory;
  Pane root;

  @Override
  public void start(Stage stage) {
    factory = new ComponentsFactory();
    root = new Pane();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void createDefault() {
    Component c = factory.create("GameObject");
    Platform.runLater(() -> {
      root.getChildren().add(c.getNode());
    });
    System.out.println(c.getNode().getBoundsInLocal().getMinX());
    simulateMousePress(c.getNode(), MouseButton.PRIMARY, 0, 0);
    simulateMouseDrag(c.getNode(), MouseButton.PRIMARY, 500, 500);
    simulateMouseRelease(c.getNode(), MouseButton.PRIMARY, 500, 500);
    System.out.println(c.getNode().getBoundsInLocal().getMinX());
  }

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

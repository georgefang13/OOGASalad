package oogasalad.frontend.modals;

import java.util.Map;
import javafx.scene.layout.Pane;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;
import oogasalad.frontend.components.GraphicHandler;

/**
 * @author Han Allows the Modal to Communicate inputs and actions back with the rest of frontend
 */
public class ModalController {

  private Pane root;

  public ModalController() {

  }

  ;

  public void createAGameObjectComponent(Map<String, String> map) {
    ComponentsFactory factory = new ComponentsFactory();
    Component c = factory.create("GameObject");
    GraphicHandler handler = new GraphicHandler();
    handler.moveToCenter(c);
    root.getChildren().add(c.getNode());
  }

  public void setRoot(Pane rt) {
    root = rt;
  }
}

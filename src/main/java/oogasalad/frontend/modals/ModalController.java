package oogasalad.frontend.modals;

import java.util.Map;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;

/**
 * @author Han
 * Allows the Modal to Communicate inputs and actions back with the rest of frontend
 */
public class ModalController {

  private Pane root;
  public ModalController(){

  };
  public void createAGameObjectComponent(Map<String, String> map){
    ComponentsFactory factory = new ComponentsFactory();
    Component c = factory.create("GameObject");
    root.getChildren().add(c.getNode());
  }

  public void setRoot(Pane rt) {
    root = rt;
  }
}

package oogasalad.frontend.panels;

import javafx.scene.Node;

/**
 * Panel is an interface that defines the way that our panels work
 * Should include the default methods that all panels have for communication with the backend
 * as well as other panels
 */
public interface Panel {


  Panel makePanel();

  /**
   * this is  a work around for node to be able to be casted to a node
   * @return
   */
  default Node asNode(){
    return (Node) this;
  };

  void refreshPanel();

  String getTitle();

  void save();

}

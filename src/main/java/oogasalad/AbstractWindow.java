package oogasalad;

import javafx.stage.Stage;

public abstract class AbstractWindow extends Stage {
  protected WindowMediator windowController;

  public AbstractWindow(WindowMediator windowController) {
    this.windowController = windowController;
  }

}

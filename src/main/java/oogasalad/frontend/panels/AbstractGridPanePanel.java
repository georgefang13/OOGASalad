package oogasalad.frontend.panels;

import javafx.scene.layout.GridPane;

public abstract class AbstractGridPanePanel extends GridPane implements Panel{
  private String panelID;
  PanelController panelController;
  public AbstractGridPanePanel(PanelController panelController, String panelID) {
    super();
    this.panelID = panelID;
    this.panelController = panelController;
    panelController.registerPanel(panelID, this);
  }
}

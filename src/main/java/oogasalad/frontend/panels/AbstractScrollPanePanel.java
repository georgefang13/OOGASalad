package oogasalad.frontend.panels;

import javafx.scene.control.ScrollPane;
/**
 * @author George Fang
 * @author Owen MacKenzie
 */
public abstract class AbstractScrollPanePanel extends ScrollPane implements Panel{
  private String panelID;
  PanelController panelController;
  public AbstractScrollPanePanel(PanelController panelController, String panelID) {
    super();
    this.panelID = panelID;
    this.panelController = panelController;
    panelController.registerPanel(panelID, this);
  }
}

package oogasalad.frontend.panels.subPanels;

import javafx.scene.layout.HBox;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.HBoxPanel;

public class HeaderMenuPanel extends HBoxPanel {
  ButtonFactory buttonFactory = new ButtonFactory();

  /**
   * Constructor for HeaderMenu
   */
  public HeaderMenuPanel() {
    super();
  }
  /**
   * Creates the menu for the header
   * @return
   */
  public HBox createMenu() {
    HBox menu = new HBox();
    menu.getChildren().addAll(buttonFactory.createDefaultButton("VisualEditor"), buttonFactory.createDefaultButton("RulesEditor")); //TODO: export these
    return menu;
  }
  @Override
  public Panel makePanel() {
    return null;
  }

  @Override
  public Panel refreshPanel() {
    return null;
  }

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public void save() {

  }
}


package oogasalad.frontend.panels.subPanels;

import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.HBoxPanel;

public class ComponentLibraryPanel extends HBoxPanel {
  ButtonFactory buttonFactory = new ButtonFactory();

  /**
   * Constructor for HeaderMenu
   */
  public ComponentLibraryPanel() {
    super();
  }
  /**
   * Creates the menu for the header
   * @return
   */
  public Accordion createAccordion() {
    TitledPane t1 = new TitledPane("T1", new Button("B1"));
    TitledPane t2 = new TitledPane("T2", new Button("B2"));
    TitledPane t3 = new TitledPane("T3", new Button("B3"));
    Accordion accordion = new Accordion();
    accordion.getPanes().addAll(t1, t2, t3);
    return accordion;
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

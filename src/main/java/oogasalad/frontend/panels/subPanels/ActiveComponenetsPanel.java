package oogasalad.frontend.panels.subPanels;

import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.AccordionPanel;
import oogasalad.frontend.panels.Panel;

public class ActiveComponenetsPanel extends AccordionPanel {
  ButtonFactory buttonFactory = new ButtonFactory();

  /**
   * Constructor for HeaderMenu
   */
  public ActiveComponenetsPanel() {
    super();
  }
  /**
   * Creates the menu for the header
   * @return
   */
  public Accordion createAccordion() {
    TitledPane t1 = new TitledPane("Game Objects", new Button("B1")); // TODO: make this dynamic so when you press ok on the modal after adding a compoennet it shows up in this panel
    Accordion accordion = new Accordion();
    accordion.getPanes().addAll(t1);
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

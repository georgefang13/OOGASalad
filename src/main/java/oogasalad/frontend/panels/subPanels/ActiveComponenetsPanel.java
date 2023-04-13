package oogasalad.frontend.panels.subPanels;

import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.modals.InputModal;
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
    TitledPane t1 = new TitledPane("Game Objects", createAccordionButton()); // TODO: make this dynamic so when you press ok on the modal after adding a compoennet it shows up in this panel
    Accordion accordion = new Accordion();
    accordion.getPanes().addAll(t1);
    return accordion;
  }
  private Button createAccordionButton(){
    Button b = new Button("Make a new Game Object");
    b.setOnAction(e -> promptModal());
    return b;
  }

  private void promptModal() {
    InputModal modal = new InputModal();
    System.out.println("Test");
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

package oogasalad.frontend.panels.subPanels;

import java.util.ResourceBundle;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.modals.InputModal;
import oogasalad.frontend.modals.ModalController;
import oogasalad.frontend.panels.Panel;

public class ComponentPanel extends VBox implements Panel {

  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle(
      "frontend/properties/text/english");
  private static final String COMPONENT_LIBRARY_ACCORDION_LABEL = "ComponentLibrary";
  private static final String ACTIVE_COMPONENTS_ACCORDION_LABEL = "ActiveComponents";
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String ACCORDION_LABEL_ID = "AccordionLabelID";
  private Pane root;

  /**
   * Constructor for HeaderMenu
   */
  public ComponentPanel() {
    super();
  }

  /**
   * Creates the VBox that contains the two accordions and the labels for each accordion
   *
   * @return
   */
  public VBox createDualAccordionVBox() {
    VBox componentPanel = new VBox();
    componentPanel.getChildren()
        .addAll(createComponentLibraryLabel(), createComponenetLibraryAccordion(),
            createActiveComponentsLabel(), createActiveComponentsAccordion());
    return componentPanel;
  }

  public VBox createSingleAccordionVBox() {
    VBox componentPanel = new VBox();
    componentPanel.getChildren()
        .addAll(createActiveComponentsLabel(), createActiveComponentsAccordion());
    return componentPanel;
  }

  private Label createComponentLibraryLabel() {
    Label label = new Label("Component Library"); //TODO: export using the propertiesMap
    label.getStyleClass().add(ID_BUNDLE.getString(ACCORDION_LABEL_ID));
    return label;
  }

  private Label createActiveComponentsLabel() {
    Label label = new Label("Active Components"); //TODO: export using the propertiesMap
    label.getStyleClass().add(ID_BUNDLE.getString(ACCORDION_LABEL_ID));
    return label;
  } //TODO: turn these two methods into one method that takes in a string

  private Accordion createComponenetLibraryAccordion() {
    TitledPane t1 = new TitledPane("Game Objects", new Button("B1"));
    TitledPane t2 = new TitledPane("Players", new Button("B2"));
    TitledPane t3 = new TitledPane("Displayable", new Button("B3"));
    Accordion accordion = new Accordion();
    accordion.getPanes().addAll(t1, t2, t3);
    return accordion;
  }

  private Accordion createActiveComponentsAccordion() {
    TitledPane t1 = new TitledPane("Game Objects",
        createAccordionButton()); // TODO: make this dynamic so when you press ok on the modal after adding a compoennet it shows up in this panel
    Accordion accordion = new Accordion();
    accordion.getPanes().addAll(t1);
    return accordion;
  }

  private Button createAccordionButton() {
    Button b = new Button("Make a new Game Object");
    b.setOnAction(e -> promptModal());
    return b;
  }

  private void promptModal() {
    InputModal modal = new InputModal("createComponent");
    ModalController mController = new ModalController();
    mController.setRoot(root);
    modal.attach(mController);
    modal.showAndWait();
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

  public void setReferenceRoot(Pane rt) {
    root = rt;
  }
}

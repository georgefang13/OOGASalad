package oogasalad.frontend.panels.subPanels;

import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.modals.ModalController;
import oogasalad.frontend.modals.subInputModals.CreateNewModal;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.VBoxPanel;

public class ComponentPanel extends VBoxPanel {

  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle(
      "frontend/properties/text/english");
  private static final String COMPONENT_LIBRARY_ACCORDION_LABEL = "ComponentLibrary";
  private static final String ACTIVE_COMPONENTS_ACCORDION_LABEL = "ActiveComponents";
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String ACCORDION_LABEL_ID = "AccordionLabelID";
  private Pane root;
  private ModalController mController;
  private VBox gameComponents;
  private VBox players;
  private VBox displayable;
  private VBox gameComponentInstances;

  private double xOffset;
  private double yOffset;

  /**
   * Constructor for HeaderMenu
   */
  public ComponentPanel() {
    super();
    mController = new ModalController(this);

    //TODO is there a better way?
    gameComponents = new VBox();
    players = new VBox();
    displayable = new VBox();
    gameComponentInstances = new VBox();
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
    TitledPane t1 = new TitledPane("Game Objects", gameComponents);
    TitledPane t2 = new TitledPane("Players", players);
    TitledPane t3 = new TitledPane("Displayable", displayable);
    gameComponents.getChildren().add(createComponentTemplate());
    Accordion accordion = new Accordion();
    accordion.getPanes().addAll(t1, t2, t3);
    return accordion;
  }

  private Button createComponentTemplate() {
    Button b = new Button("Make a Game Object Template");
    b.setOnAction(e -> createNewComponentTemplate());
    return b;
  }

  private Accordion createActiveComponentsAccordion() {

    TitledPane t1 = new TitledPane("Game Objects",
        gameComponentInstances); // TODO: make this dynamic so when you press ok on the modal after adding a compoennet it shows up in this panel
    Accordion accordion = new Accordion();
    accordion.getPanes().addAll(t1);
    return accordion;
  }


  public void addGameComponentTemplate(String name){

    Button b = new Button(name);
    b.setOnAction(e -> createNewComponentInstance(name));
//    b.setOnMousePressed(e -> {
//      xOffset = e.getSceneX();
//      yOffset = e.getSceneY();
//    });
    gameComponents.getChildren().add(b);
  }
  private void createNewComponentInstance(String name) {
    mController.createGameObjectInstance(name);
    Button b = new Button(name);
    gameComponentInstances.getChildren().add(b);
  }

  private void createNewComponentTemplate(){
    CreateNewModal modal = new CreateNewModal("createComponent");
    mController.setRoot(root);
    modal.attach(mController);
    modal.showAndWait();
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

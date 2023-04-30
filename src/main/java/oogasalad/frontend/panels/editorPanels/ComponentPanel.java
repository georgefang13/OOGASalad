package oogasalad.frontend.panels.editorPanels;

import java.awt.*;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.modals.ModalController;
import oogasalad.frontend.modals.subInputModals.CreateNewModal;
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
  private ModalController mController;
  private VBox gameComponents;
  private VBox players;
  private VBox gameComponentInstances;

  private double xOffset;
  private double yOffset;
  private int count;

  /**
   * Constructor for HeaderMenu
   */
  public ComponentPanel() {
    super();
    mController = new ModalController(this);

    //TODO is there a better way?
    gameComponents = new VBox();
    players = new VBox();
    gameComponentInstances = new VBox();
    this.makePanel();
    count = 0;
  }

  /**
   * Creates the VBox that contains the two accordions and the labels for each accordion
   *
   * @return
   */
  public Panel makePanel() {
    this.getChildren()
        .addAll(createComponentLibraryLabel(), createComponenetLibraryAccordion(),
            createActiveComponentsLabel(), createActiveComponentsAccordion());
    return this;
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
    TitledPane t1 = new TitledPane("Components", gameComponents);
    TitledPane t2 = new TitledPane("Players", players);
    gameComponents.getChildren().addAll(createComponentTemplate("gameObject"),
            createComponentTemplate("lineObject"), createComponentTemplate("textObject"),
            createComponentTemplate("rectangleObject"), createComponentTemplate("gridObject"));
    Accordion accordion = new Accordion();
    accordion.getPanes().addAll(t1, t2);
    return accordion;
  }

  private Button createComponentTemplate(String objectType) {
    Button b = new Button("Make a " + objectType + " Template");
    b.setOnAction(e -> openModal(objectType, false));
    return b;
  }

  private Accordion createActiveComponentsAccordion() {

    TitledPane t1 = new TitledPane("Objects",
        gameComponentInstances); // TODO: make this dynamic so when you press ok on the modal after adding a compoennet it shows up in this panel
    Accordion accordion = new Accordion();
    accordion.getPanes().addAll(t1);
    return accordion;
  }


  public void addComponentTemplate(String name, String objectType){

    Button b = new Button(name);
    b.setOnAction(e -> createNewComponentInstance(name, objectType));
//    b.setOnMousePressed(e -> {
//      xOffset = e.getSceneX();
//      yOffset = e.getSceneY();
//    });
    gameComponents.getChildren().add(b);
  }
  private void createNewComponentInstance(String name, String objectType) {
    mController.createObjectInstance(name, objectType);
    HBox buttonLine = new HBox();
    Button b1 = new Button(name);
    Button b2 = new Button("delete");
    Button b3 = new Button("edit");
    buttonLine.getChildren().addAll(b1,b2,b3);
    gameComponentInstances.getChildren().add(buttonLine);

    b3.setOnMouseClicked(event -> {
      openModal(objectType, true);
    });

    b2.setOnMouseClicked(event -> {
        mController.deleteObjectInstance(name);
        gameComponentInstances.getChildren().remove(buttonLine);
    });
  }

  private void openModal(String title, boolean editMode){
    CreateNewModal modal = new CreateNewModal(title, editMode);
    mController.setRoot(root);
    modal.attach(mController);
    modal.showAndWait();
  }

  public Node asNode() {
    return this;
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

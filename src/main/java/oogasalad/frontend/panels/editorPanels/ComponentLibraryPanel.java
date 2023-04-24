//package oogasalad.frontend.panels.subPanels;
//
//import javafx.scene.control.Accordion;
//import javafx.scene.control.Button;
//import javafx.scene.control.TitledPane;
//
//import javafx.scene.layout.VBox;
//import oogasalad.frontend.factories.ButtonFactory;
//import oogasalad.frontend.panels.AccordionPanel;
//import oogasalad.frontend.panels.Panel;
//import oogasalad.frontend.panels.PanelController;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ComponentLibraryPanel extends AccordionPanel {
//  ButtonFactory buttonFactory = new ButtonFactory();
//  PanelController panelController;
//
//  Map<String, TitledPane> paneMap;
//
//  /**
//   * Constructor for HeaderMenu
//   */
//  public ComponentLibraryPanel() {
//    super();
//    this.panelController = panelController;
//    paneMap = new HashMap<>();
//  }
//  /**
//   * Creates the menu for the header
//   * @return
//   */
//  public Accordion createAccordion() {
//    TitledPane t1 = new TitledPane("Game Objects", new Button("B1"));
//    TitledPane t2 = new TitledPane("Players", new Button("B2"));
//    TitledPane t3 = new TitledPane("Memes", new Button("B3"));
//    Button addPlayer = new Button("add player");
//    addPlayer.setOnAction(e -> addInstanceToAccordion("player","player1"));
//    VBox playerVBOX = new VBox(addPlayer);
//    TitledPane player = new TitledPane("Player", playerVBOX);
//    paneMap.put("player",player);
//
//    Button addPiece = new Button("add piece");
//    addPiece.setOnAction(e -> addInstanceToAccordion("piece","piece1"));
//    VBox pieceVBOX = new VBox(addPiece);
//    TitledPane piece = new TitledPane("Piece", pieceVBOX);
//    paneMap.put("piece",piece);
//
//    Button addVariable = new Button("add variable");
//    addPiece.setOnAction(e -> addInstanceToAccordion("variable","variable1"));
//    VBox variableVBOX = new VBox(addVariable);
//    TitledPane variable = new TitledPane("Variable", variableVBOX);
//    paneMap.put("variable",variable);
//
//    Accordion accordion = new Accordion();
//    accordion.getPanes().addAll(player, piece, variable);
//    return accordion;
//  }
//
//  private void addInstanceToAccordion(String paneName, String instanceName){
//    TitledPane pane = paneMap.get(paneName);
//    Button instanceButton = new Button(instanceName);
//    VBox content = (VBox) pane.getContent();
//    content.getChildren().add(instanceButton);
//    System.out.print(instanceName);
//  }
//
//  @Override
//  public Panel makePanel() {
//    return null;
//  }
//
//  @Override
//  public Panel refreshPanel() {
//    return null;
//  }
//
//  @Override
//  public String getTitle() {
//    return null;
//  }
//
//  @Override
//  public void save() {
//
//  }
//}

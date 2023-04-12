package oogasalad.frontend.scenes;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.panels.subPanels.EnvironmentPanel;
import oogasalad.frontend.panels.subPanels.HeaderMenuPanel;
import oogasalad.frontend.panels.subPanels.ComponentLibraryPanel;

public class GameEditorEditorScene extends AbstractScene {
    private BorderPane root;
    private VBox rightTab; //REPLACE WITH A PANEL
    private Map<Button,VBox> buttonVBoxMap;
    private HeaderMenuPanel headerMenu;
    private ComponentLibraryPanel componentLibraryPanel;
    private EnvironmentPanel environmentPanel;

  public GameEditorEditorScene(SceneController sceneController) {
    super(sceneController);
  }

  private void setButtonVisualPanel(Button button, String title) {
    VBox newVisualPanel = new VBox();
    newVisualPanel.getChildren().add(new Label(title));
    button.setOnAction(e -> updateVisualPanel(button));
    buttonVBoxMap.put(button, newVisualPanel);
  }

  private void updateVisualPanel(Button button) {
    System.out.print(button.getText());
//        visualPanel = buttonVBoxMap.get(button);
    refreshScene();
    //sceneController.wirefreshScene();
  }
  @Override
  public Scene makeScene() {
      root = new BorderPane();
      headerMenu = new HeaderMenuPanel(panelController);
      componentLibraryPanel = new ComponentLibraryPanel();
      //propertiesPanel = new PropertiesPanel(panelController);
      environmentPanel = new EnvironmentPanel();
      //left sidebar
      rightTab = new VBox();
      buttonVBoxMap = new HashMap<>(); //TODO: what does this do?
        Button boardButton = new Button("Board");
        setButtonVisualPanel(boardButton, "Board");
        Button variableButton = new Button("Variable");
        setButtonVisualPanel(variableButton, "Variable");
        Button playerButton = new Button("Player");
        setButtonVisualPanel(playerButton, "Player");
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> panelController.switchSceneFromPanel("main"));
        rightTab.getChildren().addAll(boardButton,variableButton,playerButton,backButton);

        refreshScene();
        return getScene();
  }
    private void refreshScene(){
        root.setRight(rightTab);
        root.setCenter(environmentPanel);
        root.setTop(headerMenu.createMenu());
        root.setLeft(componentLibraryPanel.createAccordion());
        root.setCenter(environmentPanel.createEnvironment());
        setScene(new Scene(root));
        setText();
        setTheme();
    }
  @Override
  public void setText() {
    //editGridButton.setText(getPropertyManager().getText("GameEditorMainScene.EditGridButton"));
    //gameEditorLabel.setText(getPropertyManager().getText("GameEditorMainScene.GameEditorLabel"));
  }

}

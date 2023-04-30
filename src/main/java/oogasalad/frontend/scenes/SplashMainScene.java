package oogasalad.frontend.scenes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class SplashMainScene extends AbstractScene {
  private static final String LANGUAGE_DIRECTORY_PATH = "src/main/resources/frontend/properties/text";
  private static final String LANGUAGE_PLACEHOLDER = "English";
  private static final String THEME_DIRECTORY_PATH = "src/main/resources/frontend/css";
  private static final String THEME_PLACEHOLDER = "Light";
  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle(
      "frontend/properties/text/english");
  private static final String USERNAME = "Username";
  private static final String PASSWORD = "Password";
  private static final String LOGIN = "Login";
  private static final String SIGN_UP = "SignUp";
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String LEFT_HEADER_BOX_ID = "LeftHeaderBoxID";
  private Label username;
  private Label password;
  private TextField usernameField;
  private TextField passwordField;
  private Hyperlink login;
  private Hyperlink signUp;
  private ComboBox<String> languageDropdown;
  private ComboBox<String> themeDropdown;
  private VBox root;

  public SplashMainScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
//    StackPane root = new StackPane();
//    VBox vbox = new VBox(10);
//    playButton = new Button();
//    playButton.setOnAction(
//        e -> panelController.getSceneController().getWindowController()
//            .registerAndShow(WindowTypes.WindowType.LIBRARY_WINDOW));
//    editButton = new Button();
//    editButton.setOnAction(
//        e -> panelController.getSceneController().getWindowController()
//            .registerAndShow(WindowTypes.WindowType.EDIT_WINDOW));
//    modalButton = new Button(); //ADD NEW WINDOW
//    modalButton.setOnAction(
//        e -> panelController.getSceneController().getWindowController()
//            .registerAndShow(WindowTypes.WindowType.MODAL_WINDOW));
//    ComboBox<String> languageDropdown = new ComboBox<>();
//    languageDropdown.getItems().addAll("english", "spanish");
//    languageDropdown.getSelectionModel().selectedItemProperty()
//        .addListener((observable, oldValue, newValue) -> {
//          getPropertyManager().setTextResources(newValue);
//        });
//    languageDropdown.setValue("english");
//    ComboBox<String> themeDropdown = new ComboBox<>();
//    themeDropdown.getItems().addAll("light", "dark");
//    themeDropdown.getSelectionModel().selectedItemProperty()
//        .addListener((observable, oldValue, newValue) -> {
//          getThemeManager().setTheme(newValue);
//        });
//    themeDropdown.setValue("light");
//    vbox.getChildren().addAll(playButton, editButton, modalButton, languageDropdown, themeDropdown);
//    vbox.setAlignment(Pos.CENTER);
//    root.getChildren().add(vbox);
//    setScene(new Scene(root));
//    setText();
//    setTheme();
    root = new VBox();
    makeInputFields();
    makeLoginSignUpButtons();
    makeDropDowns();
    refreshScene();
    return getScene();
  }
  private void refreshScene() {
    root.getChildren().clear();
    root.getChildren().addAll(makeInputFields(), makeLoginSignUpButtons(), makeDropDowns());
    setScene(new Scene(root));
  }
  private VBox makeInputFields() {
    HBox usernameBox = new HBox();
    username = new Label(ELEMENT_LABELS.getString(USERNAME));
    usernameField = new TextField();
    usernameBox.getChildren().addAll(username, usernameField);
    HBox passwordBox = new HBox();
    password = new Label(ELEMENT_LABELS.getString(PASSWORD));
    passwordField = new TextField();
    passwordBox.getChildren().addAll(password, passwordField);
    VBox inputFields = new VBox();
    inputFields.getChildren().addAll(usernameBox, passwordBox);
    return inputFields;
  }
  private HBox makeLoginSignUpButtons() {
    HBox loginSignUpButtons = new HBox();
    login = new Hyperlink(ELEMENT_LABELS.getString(LOGIN));
    signUp = new Hyperlink(ELEMENT_LABELS.getString(SIGN_UP));
    loginSignUpButtons.getChildren().addAll(login, signUp);
    return loginSignUpButtons;
  }
  private HBox makeDropDowns() {
    HBox dropDowns = new HBox();
    languageDropdown = new ComboBox<>();
    languageDropdown.getItems().addAll(getFileNames(LANGUAGE_DIRECTORY_PATH));
    languageDropdown.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          getPropertyManager().setTextResources(newValue);
        });
    languageDropdown.setValue(LANGUAGE_PLACEHOLDER);
    themeDropdown = new ComboBox<>();
    themeDropdown.getItems().addAll(getFileNames(THEME_DIRECTORY_PATH));
    themeDropdown.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          getThemeManager().setTheme(newValue);
        });
    themeDropdown.setValue(THEME_PLACEHOLDER);
    dropDowns.getChildren().addAll(languageDropdown, themeDropdown);
    return dropDowns;
  }
  private List<String> getFileNames(String theDirectory) {
    List<String> propertyFileNames = new ArrayList<>();
    String directory = theDirectory;
    File dir = new File(directory);
    File[] files = dir.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isFile()) {
          String propertyFileName = file.getName().substring(0, file.getName().indexOf("."));
          propertyFileNames.add(propertyFileName);
        }
      }
    }
    return propertyFileNames;
  }

  @Override
  public void setText() {
  }
}

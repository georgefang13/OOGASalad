package oogasalad.frontend.scenes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oogasalad.frontend.modals.subDisplayModals.AlertModal;
import oogasalad.frontend.windows.WindowTypes.WindowType;
import oogasalad.sharedDependencies.backend.database.Database;
import oogasalad.sharedDependencies.backend.database.UserManager;

/**
 * @author George Fang
 *
 *  This class is the main scene for the splash screen. It contains the login and sign up
 *  functionality for the user to access the rest of the program. Some noteworthy features in
 *  this class include changing color theme, and language. These are done through the theme
 *  manager and property manager. On selecting spanish, the property manager in this scene
 *  calls setText(). Since each panel was created with abstraction, all element are
 *  repopulated from the setText using the new language properties file. On selecting light
 *  mode, the theme manager notifies the theme observer interface of the new css file, then
 *  calls setTheme. Abstract scene implements this interface, and calls setTheme which sets
 *  the theme of each scene to the new stylesheet.
 */

public class SplashMainScene extends AbstractScene {
  private static final String LANGUAGE_DIRECTORY_PATH = "src/main/resources/frontend/properties/text";
  private static final String LANGUAGE_PLACEHOLDER = "english";
  private static final String THEME_DIRECTORY_PATH = "src/main/resources/frontend/css";
  private static final String THEME_PLACEHOLDER = "dark";
  private static final String USERNAME = "Username";
  private static final String PASSWORD = "Password";
  private static final String LOGIN = "Login";
  private static final String SIGN_UP = "SignUp";
  private static final String TITLE = "Title";
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String SPLASH_ROOT_ID = "SplashRootID";
  private static final String TITLE_ID = "TitleID";
  private static final String USERNAME_PASSWORD_BOX_ID = "UsernamePasswordBoxID";
  private static final String INPUT_FIELDS_ID = "InputFieldsID";
  private static final String USERNAME_PASSWORD_LABEL_ID = "UsernameLabelID";
  private static final String USERNAME_PASSWORD_FIELD_ID = "UsernameFieldID";
  private static final String LOGIN_SIGNUP_ID = "LoginID";
  private static final String DROPDOWN_ID = "DropdownID";
  private static final String DROPDOWN_BOX_ID = "DropdownBoxID";
  private static final String DEFAULT_GAMES = "all";
  private Label usernameLabel;
  private Label passwordLabel;
  private TextField usernameField;
  private PasswordField passwordField;
  private Hyperlink login;
  private Hyperlink signUp;
  private ComboBox<String> languageDropdown;
  private ComboBox<String> themeDropdown;
  private VBox root;
  private UserManager userManager;
  private Label title;

  /**
   * Constructor for the splash main scene, calls abstract scene contructor
   * @param sceneController the scene controller
   */
  public SplashMainScene(SceneMediator sceneController) {
    super(sceneController);
  }

  /**
   * Sets up the scene, sets the theme and text
   *
   * @return
   */
  @Override
  public Scene makeScene() {
    root = new VBox();
    userManager = new UserManager(Database.getInstance());
    root.getStyleClass().add(ID_BUNDLE.getString(SPLASH_ROOT_ID));
    makeTitle();
    makeInputFields();
    makeLoginSignUpButtons();
    makeDropDowns();
    refreshScene();
    setTheme();
    setText();
    return getScene();
  }
  private void refreshScene() {
    root.getChildren().clear();
    root.getChildren().addAll(makeTitle(), makeInputFields(), makeLoginSignUpButtons(), makeDropDowns());
    setScene(new Scene(root));
  }
  private HBox makeTitle() {
    HBox titleBox = new HBox();
    title = new Label();
    title.getStyleClass().add(ID_BUNDLE.getString(TITLE_ID));
    titleBox.getChildren().add(title);
    return titleBox;
  }
  private VBox makeInputFields() {
    HBox usernameBox = new HBox();
    usernameBox.getStyleClass().add(ID_BUNDLE.getString(USERNAME_PASSWORD_BOX_ID));
    usernameLabel = new Label();
    usernameLabel.getStyleClass().add(ID_BUNDLE.getString(USERNAME_PASSWORD_LABEL_ID));
    usernameField = new TextField();
    usernameField.getStyleClass().add(ID_BUNDLE.getString(USERNAME_PASSWORD_FIELD_ID));
    usernameBox.getChildren().addAll(usernameLabel, usernameField);
    HBox passwordBox = new HBox();
    passwordBox.getStyleClass().add(ID_BUNDLE.getString(USERNAME_PASSWORD_BOX_ID));
    passwordLabel = new Label();
    passwordLabel.getStyleClass().add(ID_BUNDLE.getString(USERNAME_PASSWORD_LABEL_ID));
    passwordField = new PasswordField();
    passwordField.getStyleClass().add(ID_BUNDLE.getString(USERNAME_PASSWORD_FIELD_ID));
    passwordBox.getChildren().addAll(passwordLabel, passwordField);
    VBox inputFields = new VBox();
    inputFields.getStyleClass().add(ID_BUNDLE.getString(INPUT_FIELDS_ID));
    inputFields.getChildren().addAll(usernameBox, passwordBox);
    return inputFields;
  }
  private HBox makeLoginSignUpButtons() {
    HBox loginSignUpButtons = new HBox();
    login = new Hyperlink();
    login.setUnderline(true);
    login.getStyleClass().add(ID_BUNDLE.getString(LOGIN_SIGNUP_ID));
    login.setOnMouseClicked(e -> {
      if (userManager.tryLogin(usernameField.getText(), passwordField.getText())) {
        sceneController.getWindowController().passData(DEFAULT_GAMES);
        displayLibrary();
        sceneController.getWindowController().closeWindow(sceneController.getWindow());
      }
      else {
        AlertModal error = new AlertModal("LoginErrorHeader", "LoginErrorBody");
        error.showAlert();
      }
    });
    signUp = new Hyperlink();
    signUp.setUnderline(true);
    signUp.getStyleClass().add(ID_BUNDLE.getString(LOGIN_SIGNUP_ID));
    signUp.setOnMouseClicked(e -> {
      if (userManager.tryRegister(usernameField.getText(), passwordField.getText())) {
        sceneController.passData(DEFAULT_GAMES);
        displayLibrary();
        sceneController.getWindowController().closeWindow(sceneController.getWindow());
      }
      else {
        AlertModal error = new AlertModal("RegisterErrorHeader", "RegisterErrorBody");
        error.showAlert();
      }
    });
    loginSignUpButtons.getChildren().addAll(login, signUp);
    return loginSignUpButtons;
  }

  private void displayLibrary() {
    panelController.getSceneController().getWindowController().registerAndShow(WindowType.LIBRARY_WINDOW);
  }

  private HBox makeDropDowns() {
    HBox dropDowns = new HBox();
    dropDowns.getStyleClass().add(ID_BUNDLE.getString(DROPDOWN_BOX_ID));
    languageDropdown = new ComboBox<>();
    languageDropdown.getStyleClass().add(ID_BUNDLE.getString(DROPDOWN_ID));
    languageDropdown.getItems().addAll(getFileNames(LANGUAGE_DIRECTORY_PATH));
    languageDropdown.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          getPropertyManager().setTextResources(newValue);
        });
    languageDropdown.setValue(LANGUAGE_PLACEHOLDER);
    themeDropdown = new ComboBox<>();
    themeDropdown.getStyleClass().add(ID_BUNDLE.getString(DROPDOWN_ID));
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

  /**
   * Sets the text of the scene, gives each component its text value from the properties file
   */
  @Override
  public void setText() {
    title.setText(getPropertyManager().getText(TITLE));
    usernameLabel.setText(getPropertyManager().getText(USERNAME));
    passwordLabel.setText(getPropertyManager().getText(PASSWORD));
    login.setText(getPropertyManager().getText(LOGIN));
    signUp.setText(getPropertyManager().getText(SIGN_UP));
  }
}

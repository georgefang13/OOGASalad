package oogasalad.frontend.modals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;


public class InputModal extends Modal {

  //    private static final ResourceBundle MODAL_ID_BUNDLE = ResourceBundle.getBundle("frontend/modals/ModalStylingID");
  private static final String IMAGE_PICKER_ID = "ImagePickerID";
  final public static int GAP = 10;
  final public static int INSET_TOP = 20;
  final public static int INSET_RIGHT = 150;
  final public static int INSET_BOTTOM = 10;
  final public static int INSET_LEFT = 10;

  protected Map<String, String> myPropertiesMap;
  private String myTitle;

  private ModalController controller;

  private Map<String, TextField> textFields;

  /**
   * Constructor for InputModal
   *
   * @param title
   */
  public InputModal(String title) {
    super(title);
    myTitle = title;
  }

  /**
   * Default constructor for InputModal
   */
  public InputModal() {
    super();
  }

  /**
   * Creates the dialog pane for the modal and gives it the appropriate content
   *
   * @return
   */
  @Override
  protected DialogPane createDialogPane() {
    myPropertiesMap = super.getPropertiesMap();

    this.getDialogPane().setHeaderText(myPropertiesMap.get("title"));
    myPropertiesMap.remove("title");

    ArrayList<String> stringFields = new ArrayList<>(myPropertiesMap.values());

    this.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
    setContentAsTextField(stringFields);
    this.getDialogPane().getChildren().add(createOKButton());

    return this.getDialogPane();
  }

  /**
   * Uses the array list of strings to create text fields as part of a grid and add them to the
   * dialog pane
   *
   * @return
   */
  protected void setContentAsTextField(ArrayList<String> fields) {
    GridPane grid = createGrid();

    textFields = new HashMap<>();
    for (int i = 0; i < fields.size(); i++) {
      System.out.println(fields.get(i));
      grid.add(new Label(fields.get(i)), 0, i);
      grid.add(makeTextField(fields.get(i)), 1, i);
      textFields.put(fields.get(i), makeTextField(fields.get(i)));
    }
    grid.add(createOKButton(), 0, 3);

    this.getDialogPane().setContent(grid);
  }

  /**
   * Creates a grid pane with the appropriate spacing where the inputs will be
   *
   * @return
   */
  protected GridPane createGrid() {
    GridPane grid = new GridPane();
    grid.setHgap(GAP);
    grid.setVgap(GAP);
    grid.setPadding(new Insets(INSET_TOP, INSET_RIGHT, INSET_BOTTOM, INSET_LEFT));

    return grid;
  }

  /**
   * Creates dropdown menu for the modal
   *
   * @param dropdownName
   * @return
   */
  protected ChoiceBox<String> makeDropdown(String dropdownName) {
    ChoiceBox<String> dropdown = new ChoiceBox<>();
    dropdown.getItems().addAll("1", "2", "3", "4", "5");
    dropdown.setValue("1");
    return dropdown;
  }

  /**
   * Creates a toggle group of radio buttons for the modal
   *
   * @param fieldName
   * @return
   */
  protected VBox makeRadioButtons(String fieldName) {
    ToggleGroup toggleGroup = new ToggleGroup();

    RadioButton radioButton1 = new RadioButton("Option 1");
    radioButton1.setToggleGroup(toggleGroup);
    radioButton1.setSelected(true);

    RadioButton radioButton2 = new RadioButton("Option 2");
    radioButton2.setToggleGroup(toggleGroup);

    RadioButton radioButton3 = new RadioButton("Option 3");
    radioButton3.setToggleGroup(toggleGroup);

    HBox radioBox = new HBox(radioButton1, radioButton2, radioButton3);
    radioBox.setSpacing(10);

    VBox root = new VBox(radioBox);

    return root;

  }

  /**
   * Creates a field for selecting an image
   *
   * @param fieldName
   * @return
   */
  protected Button makeImagePicker(String fieldName) {
    Button ImageButton = new Button("Select Image");
    ImageButton.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Image File");
      fileChooser.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
    });
//        ImageButton.setId(MODAL_ID_BUNDLE.getString(IMAGE_PICKER_ID));
    return ImageButton;
  }

  protected ColorPicker makeColorPicker(String fieldName) {
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setValue(Color.WHITE); // Set default color to white

    return colorPicker;
  }

  @Override
  protected Object convertResult(ButtonType buttonType) {
    if (buttonType == ButtonType.OK) {
      return getDialogPane().getContent().toString();
    } else {
      return null;
    }
  }

  /**
   * Allows for Modals to attach their controller to them, which in turn allows them to send info
   * back to the Modal.
   *
   * @param mController the controller that is attached to this input Modal
   */
  public void attach(ModalController mController) {
    controller = mController;
  }

  private Button createOKButton() {
    Button ok = new Button("Ok");
    ok.setOnAction(e -> sendtoController());
    return ok;
  }

  private void sendtoController() {
    Map<String, String> map = new HashMap<>();
    for (String field : textFields.keySet()) {
      String param = field.toString();
      String value = textFields.get(field).getText();
      map.put(param, value);
    }
    //TODO remove, just for testing purposes
    System.out.println(map);
    controller.createObjectTemplate(map, myTitle);
    this.getDialogPane().getScene().getWindow().hide();
  }

  public Map<String, TextField> getTextFields() {
    return textFields;
  }
  protected ModalController getController(){
    return controller;
  }
}

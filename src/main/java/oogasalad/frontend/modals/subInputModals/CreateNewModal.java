package oogasalad.frontend.modals.subInputModals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;
import oogasalad.frontend.modals.InputModal;
import oogasalad.frontend.modals.fields.ColorPickerComponent;
import oogasalad.frontend.modals.fields.ImagePickerComponent;
import oogasalad.frontend.modals.fields.IntegerPickerComponent;
import oogasalad.frontend.modals.fields.TextFieldComponent;

public class CreateNewModal extends InputModal {

  //    private static final ResourceBundle MODAL_ID_BUNDLE = ResourceBundle.getBundle("frontend/modals/ModalStylingID");
  private static final String IMAGE_PICKER_ID = "ImagePickerID";
  private Map<String, String> myPropertiesMap;
  private List<ImagePickerComponent> ImagePickers;
  private List<TextFieldComponent> textFields;
  private List<ColorPickerComponent> colorPickers;
  private List<IntegerPickerComponent> integerPickers;
  private String myTitle;
  private boolean editMode;
  private Map<String, String> values;
  private List<String> dropzoneIDs;

  /**
   * Constructor for the CreateGameModal dialog
   */
  public CreateNewModal(String title) {
    super(title);
    myTitle = super.getMyTitle();
    editMode = false;
    values = null;
    setDialogPane(createDialogPane());
//        myPropertiesMap = super.setPropertiesMap(myTitle
  }

  public CreateNewModal(String title, boolean editMode, Map<String, String> values) {
    super(title);
    this.editMode = editMode;
    this.values = values;
    myTitle = super.getMyTitle();
    setDialogPane(createDialogPane());
  }

  /**
   * Creates the dialog pane for the CreateGameModal dialog
   *
   * @return
   */
  @Override
  protected DialogPane createDialogPane() {
    myPropertiesMap = super.getPropertiesMap();

    this.getDialogPane().setHeaderText(super.getMyTitle());

    ArrayList<String> stringFields = new ArrayList<>(myPropertiesMap.values());

    this.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
    try {
      makeFields(myPropertiesMap);
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
             InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }

    return this.getDialogPane();
  }


  /**
   * Makes the appropriate types of input fields for the CreateGameModal dialog
   *
   * @param
   * @return
   */
  protected void makeFields(Map<String, String> map)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    GridPane grid = createGrid();
    int rowIndex = 0;
    initializeArrayLists();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      String propertyName = entry.getKey();
      String fieldType = propertyName.split("\\.")[propertyName.split("\\.").length - 1];
      String propertyValue;

      int start = entry.getKey().toString().indexOf("*") + 1;

      String labelName = entry.getKey().toString().substring(start);
      labelName = labelName.split("\\.")[0];

      if(editMode) {
        propertyValue = values.get(labelName);
      } else {
        propertyValue = entry.getValue();
      }

      // Get the field class corresponding to the property name using reflection
      Class<?> fieldClass = Class.forName(
          "oogasalad.frontend.modals.fields." + fieldType + "Component");

      // Create a new instance of the field class using reflection
      Constructor<?> constructor = fieldClass.getDeclaredConstructor(String.class, String.class);
      Object field = constructor.newInstance(labelName, propertyValue);

      // Invoke the createField() method on the field instance using reflection
      Method createFieldMethod = fieldClass.getDeclaredMethod("createField");
      HBox fieldHBox = (HBox) createFieldMethod.invoke(field);

      //TODO hide in strategy
      if(field.getClass() == ImagePickerComponent.class){
        ImagePickers.add((ImagePickerComponent) field);
      }
      if(field.getClass() == TextFieldComponent.class){
        textFields.add((TextFieldComponent) field);
      }
      if(field.getClass() == ColorPickerComponent.class){
        colorPickers.add((ColorPickerComponent) field);
      }
      if(field.getClass() == IntegerPickerComponent.class){
        integerPickers.add((IntegerPickerComponent) field);
      }

      // Add the field to the grid
      grid.add(fieldHBox, 0, rowIndex);

      // Increment the row index
      rowIndex++;
    }
    rowIndex++;
    grid.add(createOKButton(), 0, rowIndex);
    this.getDialogPane().setContent(grid);
  } // TODO: make classes for each of these elements and use java reflection to create them. for


  private void initializeArrayLists() {
    ImagePickers = new ArrayList<>();
    textFields = new ArrayList<>();
    colorPickers = new ArrayList<>();
    integerPickers = new ArrayList<>();
  }
  // TODO: styling use the last .NAME in the properties file to get the styling id

  protected Button createOKButton() {
    Button ok = new Button("Ok");
    ok.setOnAction(e -> sendtoController());
    return ok;
  }

  private void sendtoController(){
    Map<String, String> map = new HashMap<>();
    for (TextFieldComponent fieldComponent : textFields) {
      map.put(fieldComponent.getLabelText(), fieldComponent.getValue());
    }
    for (ImagePickerComponent imageComponent : ImagePickers){
      map.put(imageComponent.getLabelText(), imageComponent.getFile().toString());
    }
    for (ColorPickerComponent colorComponent : colorPickers){
      map.put(colorComponent.getLabelText(), colorComponent.getValue());
    }
    for (IntegerPickerComponent integerComponent : integerPickers){
      map.put(integerComponent.getLabelText(), Integer.toString(integerComponent.getValue()));
    }
    //TODO remove, just for testing purposes
    if(editMode) {
      this.getController().editObjectInstance(map, myTitle);
    } else {
      if(myTitle == "save"){
        this.getController().configGeneral(map);
      }
      this.getController().createObjectTemplate(map, myTitle);
    }
    this.getDialogPane().getScene().getWindow().hide();
  }
}
package oogasalad.frontend.modals.fields;


import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImagePickerComponent extends Field{
  private String labelText;
  private String PropertyText;
  private File file;
  public ImagePickerComponent(String label, String Property){
    labelText = label;
    PropertyText = Property;
  }

  @Override
  public HBox createField() {
    Button ImageButton = new Button(labelText);
    ImageButton.setOnAction(event -> {
      System.out.println("Test");
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Image File");
      fileChooser.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
      Stage fileChooserstage = new Stage();
      file = fileChooser.showOpenDialog(fileChooserstage);
    });
    return new HBox(ImageButton);
  }

  public File getFile() {
    return file;
  }
  public String getLabelText(){
    return labelText;
  }
}

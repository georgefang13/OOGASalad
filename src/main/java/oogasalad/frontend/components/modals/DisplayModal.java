package oogasalad.frontend.components.modals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class DisplayModal extends Modal {
  private Map<String, String> myPropertiesMap;

  public DisplayModal(String title) {
    super(title);
  }

  public DisplayModal() {
    super();
  }

  @Override
  protected DialogPane createDialogPane() {
    myPropertiesMap = super.getPropertiesMap();
    this.getDialogPane().setHeaderText(myPropertiesMap.get("title"));
    myPropertiesMap.remove("title");

    ArrayList<String> stringFields = new ArrayList<>(myPropertiesMap.values());
    setContentAsLabels(stringFields);
    this.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
    return this.getDialogPane();

  }

  @Override
  protected Object convertResult(ButtonType buttonType) {
    if (buttonType == ButtonType.OK) {
      return getDialogPane().getContent().toString();
    } else {
      return null;
    }
  }
}

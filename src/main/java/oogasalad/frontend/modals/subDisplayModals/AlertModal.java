package oogasalad.frontend.modals.subDisplayModals;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.modals.DisplayModal;


public class AlertModal extends DisplayModal {

  Alert alert;


  /**
   * Constructor for the CreateGameModal dialog
   */
  public AlertModal() {
    super("alert");
  }

  public AlertModal(String header, String body) {
    StandardPropertyManager pm = StandardPropertyManager.getInstance();
    alert = new Alert(AlertType.ERROR);
    alert.setTitle(pm.getText("Error"));
    alert.setHeaderText(pm.getText(header));
    alert.setContentText(pm.getText(body));
    alert.setResizable(true);
  }

  public void showAlert() {
    alert.showAndWait();
  }

}
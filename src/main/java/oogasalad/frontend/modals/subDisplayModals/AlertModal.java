package oogasalad.frontend.modals.subDisplayModals;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogEvent;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.modals.DisplayModal;

import java.util.function.Consumer;


public class AlertModal extends DisplayModal {

  Alert alert;


  /**
   * Constructor for the CreateGameModal dialog
   */
  public AlertModal() {
    super("alert");
  }

  public AlertModal(String header, String body, Object...bodyArgs) {
    StandardPropertyManager pm = StandardPropertyManager.getInstance();
    alert = new Alert(AlertType.ERROR);
    alert.setTitle(pm.getText("Error"));
    alert.setHeaderText(pm.getText(header));
    alert.setContentText(String.format(pm.getText(body), bodyArgs));
    alert.setResizable(true);
  }

  public void setModalTitle(String title){
    StandardPropertyManager pm = StandardPropertyManager.getInstance();
    alert.setTitle(pm.getText(title));
  }
  public void setModalType(AlertType type){
    alert.setAlertType(type);
  }

  public void setOnClose(Consumer<DialogEvent> onClose){
    alert.setOnCloseRequest(onClose::accept);
  }

  public void showAlert() {
    alert.showAndWait();
  }

}
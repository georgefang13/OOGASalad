package oogasalad.frontend.components.DropzoneComponent;

import java.util.ResourceBundle;
import oogasalad.frontend.components.AbstractComponent;

/**
 * @author Han
 * Dropzone is the visual representation of where stuff should be
 */
public class Dropzone extends AbstractComponent {

  /**
   * Dropzone
   * @param ID the id of the DropZone
   */
  public Dropzone(int ID){
    super(ID);
    instantiatePropFile("frontend.properties.Defaults.Dropzone.properties");
  }

  /**
   *
   */
  @Override
  public void setDefault() {

  }

}

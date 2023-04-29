package oogasalad.frontend.components.dropzoneComponent;

import java.util.List;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.gameObjectComponent.GameObject;

/**
 * @author Han
 * Interface for Dropzone
 */
public interface DropzoneComponent extends Component {

  /**
   * get the connecting regions
   * @return
   */
  List<Dropzone> getNeighbors();
  /**
   * get
   */
  List<GameObject> getContent();
}

package oogasalad.Controller;

import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.Subscriber;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;

public interface ControllerSubscriber extends Subscriber {
  void setDropZones(Dropzone d);
}

package oogasalad.frontend.components;

import java.util.ArrayList;
import java.util.List;

public interface DropZonePublisher {

  public void addSubscriber(ArrowSubscriber subscriber);

  public void removeSubscriber(ArrowSubscriber subscriber);

  public void publish();
}

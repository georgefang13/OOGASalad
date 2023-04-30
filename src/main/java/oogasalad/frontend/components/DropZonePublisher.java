package oogasalad.frontend.components;

public interface DropZonePublisher {

  public void addSubscriber(Subscriber subscriber);

  public void removeSubscriber(Subscriber subscriber);

  public void publish();
}

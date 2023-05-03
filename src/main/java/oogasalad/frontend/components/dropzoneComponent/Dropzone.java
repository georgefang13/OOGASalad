package oogasalad.frontend.components.dropzoneComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.Controller.ControllerSubscriber;
import oogasalad.frontend.components.AbstractComponent;
import oogasalad.frontend.components.Subscriber;
import oogasalad.frontend.components.DropZonePublisher;
import oogasalad.frontend.components.gameObjectComponent.GameObject;

/**
 * @author Han
 * Dropzone is the visual representation of where stuff should be
 */
public class Dropzone extends AbstractComponent implements DropZonePublisher {

  private final String DEFAULT_PATH = "frontend.properties.Defaults.Dropzone.properties";
  private String name;
  private Map<String, Dropzone> edges;
  private Map<String, GameObject> content;
  private HBox node;
  private int rows = 1;
  private int cols = 1;
  private final static String BOARD_TYPE = "createGrid";
  private double width;
  private double height;
  private Color fill;
  private Color border;
  private Rectangle square;
  private List<Subscriber> subscribers;
  private List<ControllerSubscriber> controllerSubscribers;
  private boolean doubleClick;
  private int clicks;

  private long lastClickTime = 0;
  private final long DOUBLE_CLICK_DELAY = 200; // 200 milliseconds
  /**
   * Dropzone
   * @param ID the id of the DropZone
   */
  public Dropzone(String ID){
    super(ID);
    initializeValues();
  }

  /**
   * Constructor of Dropzone with param
   * @param ID string ID for Dropzone
   * @param params
   */
  public Dropzone(String ID, Map<String, String> params){
    super(ID);
    initializeValues();
    setValuesfromMap(params);
    setSquareParams();
    setDraggable(true);
  }

  private void initializeValues(){
    node = new HBox();
    edges = new HashMap<>();
    content = new HashMap<>();
    square = new Rectangle();
    node.getChildren().add(square);
    subscribers = new ArrayList<>();
    controllerSubscribers = new ArrayList<>();
    setNode(node);
    setonUpdate();
    followMouse();
    doubleClick = false;
    clicks = 0;
  }
  /**
   * Adds a neighbor to whatever you need
   */
  public void addEdges(Dropzone edge){
    edges.put(edge.getID(), edge);
  }
  /**
   * Returns the list of Neighbors that the dropzone is adjacent to
   *
   * @return the set of Neighbors
   */
  public Set<String> getEdges(){
    return edges.keySet();
  }
  /**
   * set the params of the square based on the input
   */
  public void setSquareParams(){
    square.setFill(fill);
    square.setStroke(border);
    square.setWidth(width);
    square.setHeight(height);
  }
  private void setonUpdate(){
    node.setOnMouseClicked(e ->
        {super.getNode().getOnMouseClicked();
          ClickDelay();
          publish();
        }
    );
  }

  /**
   *
   */
  private void ClickDelay() {
    long clickTime = System.currentTimeMillis();
    if (clickTime - lastClickTime < DOUBLE_CLICK_DELAY) {
      doubleClick = true;
      lastClickTime = 0; // Reset last click time
    } else {
      lastClickTime = clickTime;
      doubleClick = false;
    }
  }

  /**
   * For GameObject, remove the object
   */
  public void addContent(GameObject object){
    content.put(object.getID(), object);
  }
  /**
   * Returns the list of GameObjects contained inside the dropzone
   *
   * @return the Set of GameObjects
   */
  public Set<String> returnContent(){
    return content.keySet();
  }

  /**
   * Returns the visual representation of the dropzone, which is hte rectangle
   * @return square, the visual representation of dropzone
   */
  public Rectangle getSquare() {
    return square;
  }

  public double getSquareWidth(){
    return this.width;
  }
  public double getSquareHeight(){
    return height;
  }
  public List<Subscriber> getSubscribers(){
    return subscribers;
  }
  /**
   * Adds a subscriber to the subscriber list
   * @param subscriber
   */
  @Override
  public void addSubscriber(Subscriber subscriber) {
    subscribers.add(subscriber);
  }

  /**
   * Removes a subscriber from the subscriber list
   * @param subscriber
   */
  @Override
  public void removeSubscriber(Subscriber subscriber) {
    subscribers.remove(subscriber);
  }

  /**
   * Updates all subscribes with info
   */
  @Override
  public void publish() {
    for(Subscriber subscriber : subscribers){
      subscriber.update();
    }
    if(doubleClick){
      for (ControllerSubscriber controller : controllerSubscribers){
        controller.setDropZones(this);
        System.out.println("Double Click");
        doubleClick = false;
      }
    }
  }
  public void addControllerSubscriber(ControllerSubscriber subscriber) {
    controllerSubscribers.add(subscriber);
  }
}


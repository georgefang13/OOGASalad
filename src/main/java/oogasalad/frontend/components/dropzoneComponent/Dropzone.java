package oogasalad.frontend.components.dropzoneComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.components.AbstractComponent;
import oogasalad.frontend.components.Arrow;
import oogasalad.frontend.components.ArrowSubscriber;
import oogasalad.frontend.components.DropZonePublisher;
import oogasalad.frontend.components.gameObjectComponent.GameObject;

/**
 * @author Han
 * Dropzone is the visual representation of where stuff should be
 */
public class Dropzone extends AbstractComponent implements DropZonePublisher {

  private final String DEFAULT_PATH = "frontend.properties.Defaults.Dropzone.properties";
  private Map<String, Dropzone> edges;
  private Map<String, GameObject> content;
  private HBox node;
  private Color fill;
  private Color border;
  private Rectangle square;
  private double width;
  private double height;
  private List<ArrowSubscriber> subscribers;
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
    setNode(node);
    setonUpdate();
    followMouse();
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
   * @return the list of Neighbors
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
    node.setOnMouseReleased(e -> publish());
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

  /**
   * Return the width of the square
   * @return width;
   */
  public double getWidth(){
    return width;
  }
//  @Override
//  public void setDefault() {
//
//  }

  /**
   * Return the height of the square
   * @return height
   */
  public double getHeight(){
    return height;
  }


  /**
   * Adds a subscriber to the subscriber list
   * @param subscriber
   */
  @Override
  public void addSubscriber(ArrowSubscriber subscriber) {
    subscribers.add(subscriber);
  }

  /**
   * Removes a subscriber from the subscriber list
   * @param subscriber
   */
  @Override
  public void removeSubscriber(ArrowSubscriber subscriber) {
    subscribers.remove(subscriber);
  }

  /**
   * Updates all subscribes with info
   */
  @Override
  public void publish() {
    for(ArrowSubscriber subscriber : subscribers){
      subscriber.update();
    }
  }
}

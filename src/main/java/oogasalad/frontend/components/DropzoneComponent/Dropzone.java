package oogasalad.frontend.components.DropzoneComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.components.AbstractComponent;
import oogasalad.frontend.components.gameObjectComponent.GameObject;

/**
 * @author Han
 * Dropzone is the visual representation of where stuff should be
 */
public class Dropzone extends AbstractComponent {

  private final String DEFAULT_PATH = "frontend.properties.Defaults.Dropzone.properties";
  private Map<String, Dropzone> edges;
  private Map<String, GameObject> content;
  private HBox node;
  private Color fill;
  private Color border;
  private Rectangle square;
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
  }

  private void initializeValues(){
    node = new HBox();
    edges = new HashMap<>();
    content = new HashMap<>();
    square = new Rectangle();
    node.getChildren().add(square);
    setNode(node);
    instantiatePropFile(DEFAULT_PATH);
    setColor();
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
   * set the color of the square inside fill and border
   */
  public void setColor(){
    square.setFill(fill);
    square.setStroke(border);
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


}

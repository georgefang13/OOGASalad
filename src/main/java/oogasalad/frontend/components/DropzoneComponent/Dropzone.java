package oogasalad.frontend.components.DropzoneComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import oogasalad.frontend.components.AbstractComponent;
import oogasalad.frontend.components.gameObjectComponent.GameObject;

/**
 * @author Han
 * Dropzone is the visual representation of where stuff should be
 */
public class Dropzone extends AbstractComponent {

  private String ID;
  private final String DEFAULT_PATH = "frontend.properties.Defaults.Dropzone.properties";
  private List<Dropzone> edges;
  private List<GameObject> content;
  private StackPane pane;
  /**
   * Dropzone
   * @param ID the id of the DropZone
   */
  public Dropzone(String ID){
    super(ID);
    edges = new ArrayList<>();
    content = new ArrayList<>();
    instantiatePropFile(DEFAULT_PATH);

  }

  public Dropzone(String ID, Map<String, String> params){
    super(ID);
    setValuesfromMap(params);
  }

  /**
   * Adds a neighbor to whatever you need
   */
  public void addEdges(Dropzone edge){
    edges.add(edge);
  }
  /**
   * Returns the list of Neighbors that the dropzone is adjacent to
   * @return the list of Neighbors
   */
  public List<Dropzone> getEdges(){
    return edges;
  }

  /**
   * For GameObject, remove
   * @param object
   */
  public void addContent(GameObject object){
    content.add(object);
  }
  /**
   * Returns the list of GameObjects contained inside the dropzone
   * @return the List of GameObjects
   */
  public List<GameObject> returnContent(){
    return content;
  }
  /**
   *
   */
  @Override
  public void setDefault() {

  }
  @Override
  public ImageView getNode(){
    return image;
  }

}

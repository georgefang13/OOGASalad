package oogasalad.frontend.components;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Han and Aryan AbstractComponent is the abstraction that all Components are built off of.
 */
public abstract class AbstractComponent implements Component {
  protected int ID;
  protected Node node;
  private boolean draggable;
  private boolean active;
  private boolean visible;
  private int zIndex;
  private double size;
  protected double XOffset;
  protected double YOffset;
  private Point absolute;
  private Point editor;
  private String DEFAULT_FILE_PATH;
  private ResourceBundle DEFAULT_BUNDLE;

  public AbstractComponent(int id) {
    ID = id;
  }


  protected void instantiatePropFile(String filepath) {
    setDEFAULT_FILE_PATH(filepath);
    setDEFAULT_BUNDLE(ResourceBundle.getBundle(getDEFAULT_FILE_PATH()));
  }

  protected void setValuesfromMap(Map<String, String> map) {
    for(String param: map.keySet()){
      try{
        Field field = this.getClass().getDeclaredField(param);
        field.setAccessible(true);
        Class<?> fieldType = field.getType();
        ConversionContext<?> conversionContext = ParamFactory.createConversionContext(fieldType);
        Object value = conversionContext.convert(map.get(param));
        field.set(this, value);
      } catch (Exception e){
        e.printStackTrace();
      }
    }
  }

  @Override
  public Node getNode() {
    return node;
  }
  @Override
  public void setNode(Node node) {
    this.node = node;
  }

  @Override
  public int getID() {
    return ID;
  }
  @Override
  public void setID(int id) {
    ID = id;
  }
  @Override
  public void setDraggable(boolean draggable) {
    this.draggable = draggable;
  }
  @Override
  public void setActiveSelected(boolean active) {
    this.active = active;
  }
  @Override
  public void followMouse() {
    getNode().setOnMousePressed(e -> {
      XOffset = e.getSceneX() - (getNode().getTranslateX());
      YOffset = e.getSceneY() - (getNode().getTranslateY());

    });
    getNode().setOnMouseDragged(e -> {
      getNode().setTranslateX(e.getSceneX() - XOffset);
      getNode().setTranslateY(e.getSceneY() - YOffset);
    });
  }

  @Override
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  @Override
  public void setZIndex(int zIndex) {
    getNode().setTranslateZ(zIndex);
    absolute.setZ(zIndex);
    editor.setZ(zIndex);
  }

  @Override
  public void setSize(double size) {
    this.size = size;
    getNode().setScaleY(size);
    getNode().setScaleX(size);
  }

  protected String getDEFAULT_FILE_PATH() {
    return DEFAULT_FILE_PATH;
  }
  protected void setDEFAULT_FILE_PATH(String DEFAULT_FILE_PATH) {
    this.DEFAULT_FILE_PATH = DEFAULT_FILE_PATH;
  }
  protected ResourceBundle getDEFAULT_BUNDLE() {
    return DEFAULT_BUNDLE;
  }
  protected void setDEFAULT_BUNDLE(ResourceBundle DEFAULT_BUNDLE) {
    this.DEFAULT_BUNDLE = DEFAULT_BUNDLE;
  }

  protected void setVisibleBool(boolean vis){
    visible = vis;
  }
  protected void setzIndex(int z){
    zIndex = z;
  }
  protected void setAbsolutePoint(Point abs){
    absolute = abs;
  }
  protected void setEditorPoint(Point ed){
    editor = ed;
  }
}

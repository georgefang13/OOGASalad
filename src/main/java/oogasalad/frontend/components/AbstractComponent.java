package oogasalad.frontend.components;

import javafx.scene.Node;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.ColorPicker;


/**
 * @author Han and Aryan AbstractComponent is the abstraction that all Components are built off of.
 */
public abstract class AbstractComponent implements Component {
  private String ID;
  private Node node;
  private boolean draggable;
  private boolean active;
  private boolean visible;
  private int zIndex;
  private double size;
  private double XOffset;
  private double YOffset;

  private Point absolute;
  private Point editor;
  private String DEFAULT_FILE_PATH;
  private ResourceBundle DEFAULT_BUNDLE;

  private String name;
  private double width;
  private double height;
  private double rotate;

  public AbstractComponent(String id) {
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
  public String getID() {
    return ID;
  }
  @Override
  public void setID(String id) {
    ID = id;
  }
  @Override
  public void setDraggable(boolean draggable) {
    this.draggable = draggable;
  }
  @Override
  public void setActiveSelected(boolean active) {
    this.active = active;
    node.setScaleY(10);
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

  @Override
  public void setName(String newName) {
    name = newName;
  }
  @Override
  public String getName(){
    return name;
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

  protected double getXOffset() {
    return XOffset;
  }
  protected void setXOffset(double XOffset) {
    this.XOffset = XOffset;
  }
  protected double getYOffset() {
    return YOffset;
  }
  protected void setYOffset(double YOffset) {
    this.YOffset = YOffset;
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

package oogasalad.frontend.components;

import javafx.geometry.Point2D;
import javafx.scene.Node;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * @author Han and Aryan AbstractComponent is the abstraction that all Components are built off of.
 */
public abstract class AbstractComponent implements Component {
  protected String ID;
  protected Node node;
  protected int width;
  protected int height;
  private boolean draggable;
  private double XOffset;
  private double YOffset;

  private boolean active;
  private boolean visible;
  private int zIndex;

  private double size;

  private Point absolute;
  private Point editor;
  private String DEFAULT_FILE_PATH;
  private ResourceBundle DEFAULT_BUNDLE;
  private String name;
  private double rotate;
  protected Map<String, String> parameters;

  public AbstractComponent(String id) {
    ID = id;
  }

  public AbstractComponent(String id, Map<String, String> map) {
    ID = id;
    parameters = map;
  }

  protected void instantiatePropFile(String filepath) {
    setDEFAULT_FILE_PATH(filepath);
    setDEFAULT_BUNDLE(ResourceBundle.getBundle(getDEFAULT_FILE_PATH()));
  }

  @Override
  public void setValuesfromMap(Map<String, String> map) {
    this.parameters = map;
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
      if (draggable) {
        getNode().setTranslateX(e.getSceneX() - XOffset);
        getNode().setTranslateY(e.getSceneY() - YOffset);
      }
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
  }

  @Override
  public void setName(String newName) {
    name = newName;
  }
  @Override
  public String getName(){
    return name;
  }

  @Override
  public Map<String, String> getParameters() {
    return parameters;
  }

  public void moveToXY(Point2D dropZoneCenter){
    Point2D pieceCenter = getNode().localToScene(((double) getWidth())/2,((double) getHeight())/2);
    double shiftX = dropZoneCenter.getX() - pieceCenter.getX();
    double shiftY = dropZoneCenter.getY() - pieceCenter.getY();
    getNode().setTranslateX(getNode().getTranslateX() + shiftX);
    getNode().setTranslateY(getNode().getTranslateY() + shiftY);
  }



  private String getDEFAULT_FILE_PATH() {
    return DEFAULT_FILE_PATH;
  }
  private void setDEFAULT_FILE_PATH(String DEFAULT_FILE_PATH) {
    this.DEFAULT_FILE_PATH = DEFAULT_FILE_PATH;
  }
  private ResourceBundle getDEFAULT_BUNDLE() {
    return DEFAULT_BUNDLE;
  }
  private void setDEFAULT_BUNDLE(ResourceBundle DEFAULT_BUNDLE) {
    this.DEFAULT_BUNDLE = DEFAULT_BUNDLE;
  }

  private double getXOffset() {
    return XOffset;
  }
  private void setXOffset(double XOffset) {
    this.XOffset = XOffset;
  }
  private double getYOffset() {
    return YOffset;
  }
  private void setYOffset(double YOffset) {
    this.YOffset = YOffset;
  }


  private void setVisibleBool(boolean vis){
    visible = vis;
  }
  private void setzIndex(int z){
    zIndex = z;
  }
  private void setAbsolutePoint(Point abs){
    absolute = abs;
  }
  private void setEditorPoint(Point ed){
    editor = ed;
  }
  private void resetOffset(){
    XOffset = 0;
    YOffset = 0;
  }

  protected void setHeight(int height){
    this.height = height;
  }
  protected void setWidth(int width){
    this.width = width;
  }
  protected int getHeight(){
    return height;
  }
  protected int getWidth(){
    return width;
  }

}


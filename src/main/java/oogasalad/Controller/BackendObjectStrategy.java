package oogasalad.Controller;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;
import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.frontend.components.gridObjectComponent.GridObject;
import oogasalad.gameeditor.backend.ObjectParameter;


public class BackendObjectStrategy {

  private Set<Class> GameObjects;
  private Set<Class> Grids;
  private ResourceBundle bundle  = ResourceBundle.getBundle("frontend/properties/permanentText/BackendObjectStrategy");
  public BackendObjectStrategy(){
    GameObjects = new HashSet<>();
    Grids = new HashSet<>();
    GameObjects.add(GameObject.class);
    Grids.add(Dropzone.class);
    Grids.add(GridObject.class);
  }
  public String getOwnableType(Component c){
    if(GameObjects.contains(c.getClass())){
      return bundle.getString("GameObject");
    }
    if(Grids.contains(c.getClass())){
      return bundle.getString("Grids");
    }
    else{
      return bundle.getString("Variable");
    }
  }
}

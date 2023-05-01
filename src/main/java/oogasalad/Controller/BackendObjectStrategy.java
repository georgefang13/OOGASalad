package oogasalad.Controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

  public void putParams(Map<String, String> map, HashMap<ObjectParameter, Object> param, String type) {
    if(type.equals(bundle.getString("GameObject"))){
      param.put(ObjectParameter.CONSTRUCTOR_ARGS, map);
      param.put(ObjectParameter.ID, map.get("ID"));
      param.put(ObjectParameter.OWNER, map.get("Parent"));
      param.put(ObjectParameter.DROPZONE_ID, map.get("dropzoneID"));
    }
    if(type.equals(bundle.getString("Grids"))){
      HashMap<Object, Object> construct = new HashMap<>();
      construct.put(ObjectParameter.BOARD_TYPE, map.get("BOARD_TYPE"));
      construct.put(ObjectParameter.BOARD_COLS, map.get("rows"));
      construct.put(ObjectParameter.BOARD_ROWS, map.get("cols"));
      param.put(ObjectParameter.CONSTRUCTOR_ARGS, construct);
    }
  }
}

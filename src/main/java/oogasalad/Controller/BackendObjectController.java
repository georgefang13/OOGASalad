package oogasalad.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import oogasalad.frontend.components.Component;
import oogasalad.gameeditor.backend.GameInator;
import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.gameeditor.backend.ObjectType;

public class BackendObjectController {

  private GameInator game;
  public BackendObjectController(){

  }
  public void sendOwnableObject(Component c){
    HashMap<ObjectParameter, Object> param = setUpMap(c);
    System.out.println(param.get(ObjectParameter.ID));
    game.sendObject(ObjectType.OWNABLE, param);
  }

  private static HashMap<ObjectParameter, Object> setUpMap (Component c) {
    ConvertingStrategy e = new ConvertingStrategy();
    Map<String, String> mp = e.paramsToMap(c);
    BackendObjectStrategy b = new BackendObjectStrategy();
    HashMap<ObjectParameter, Object> param = new HashMap<>();
    String type = b.getOwnableType(c);
    param.put(ObjectParameter.OWNABLE_TYPE, type);
    b.putParams(mp,param,type);
    return param;
  }

  public void deleteOwnableObject(Component c){
    HashMap<ObjectParameter, Object> param = setUpMap(c);
    game.deleteObject(ObjectType.OWNABLE, param);
  }
  public void setGame(GameInator g) {
    game = g;
  }

  public void editOwnableObject(Component c) {
    HashMap<ObjectParameter, Object> param = setUpMap(c);
    param.remove(ObjectParameter.ID);
    System.out.println(c.getID());
    game.updateObjectProperties(c.getID(), ObjectType.OWNABLE, param);
  }
}

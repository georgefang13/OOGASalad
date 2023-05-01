package oogasalad.Controller;

import java.util.HashMap;
import java.util.Map;
import oogasalad.frontend.components.Component;
import oogasalad.gameeditor.backend.GameInator;
import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.gameeditor.backend.ObjectType;

public class BackendObjectController {

  private GameInator game;
  public BackendObjectController(){

  }
  public void sendOwnableObject(Map<String, String> map, Component c){
    BackendObjectStrategy b = new BackendObjectStrategy();
    HashMap<ObjectParameter, Object> param = new HashMap<>();
    param.put(ObjectParameter.OWNABLE_TYPE, b.getOwnableType(c));
    param.put(ObjectParameter.CONSTRUCTOR_ARGS, map);
    param.put(ObjectParameter.ID, map.get("ID"));
    param.put(ObjectParameter.OWNER, map.get("Parent"));
    game.sendObject(ObjectType.OWNABLE,param);
    System.out.println("test");
  }

  public void setGame(GameInator g) {
    game = g;
  }
}

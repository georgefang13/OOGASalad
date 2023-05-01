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
    System.out.println(map);
    ConvertingStrategy e = new ConvertingStrategy();
    Map<String, String> mp = e.paramsToMap(c);
    BackendObjectStrategy b = new BackendObjectStrategy();
    HashMap<ObjectParameter, Object> param = new HashMap<>();
    String type = b.getOwnableType(c);
    param.put(ObjectParameter.OWNABLE_TYPE, type);
    b.putParams(mp,param,type);
    game.sendObject(ObjectType.OWNABLE, param);
    System.out.println("test");
  }

  public void setGame(GameInator g) {
    game = g;
  }
}

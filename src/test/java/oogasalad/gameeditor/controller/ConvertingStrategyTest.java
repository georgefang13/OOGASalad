package oogasalad.gameeditor.controller;

import java.util.Map;
import oogasalad.Controller.ConvertingStrategy;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConvertingStrategyTest {
  ConvertingStrategy converter;
  @BeforeEach
  public void startUp(){
    converter = new ConvertingStrategy();
  }
  @Test
  public void sendGameObject(){
    ComponentsFactory factory = new ComponentsFactory();
    Component c = factory.create("GameObject");
    Map<String, String> map = converter.paramsToMap(c);
    System.out.println(map);
  }
}

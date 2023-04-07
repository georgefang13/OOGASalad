package oogasalad.Controller;

import java.lang.reflect.Field;
import oogasalad.frontend.components.Component;

public class StringConvertingStrategy {

  /**
   * Returns the parameters of the Component to be converted into a String of params. Used reflection
   * to catch all the parameters. Note that if the params don't have a String value, then they must be deleted
   * or substituted. With all parameters implementing the values
   * https://shareg.pt/4u2upk3
   * @return the string that contains all the params
   */
  public String paramsToString(Component component){
    StringBuilder sb = new StringBuilder();
    Field[] fields = component.getClass().getDeclaredFields();
    for(Field field:fields){
      field.setAccessible(true);
      try {
        sb.append(field.getName()).append(": ").append(field.get(this).toString());
      } catch (IllegalAccessException e) {
        sb.append(field.getName()).append("=").append("null");
      }
    }
    return sb.toString();
  }
}

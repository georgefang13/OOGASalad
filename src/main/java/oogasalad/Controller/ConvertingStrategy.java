package oogasalad.Controller;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import oogasalad.frontend.components.Component;

public class ConvertingStrategy {

  public ConvertingStrategy(){

  }
  /**
   * Returns the parameters of the Component to be converted into a String of params. Used
   * reflection to catch all the parameters. Note that if the params don't have a String value, then
   * they must be deleted or substituted. With all parameters implementing the values
   * https://shareg.pt/4u2upk3
   *
   * @return the string that contains all the params
   */
  public String paramsToString(Component component) {
    StringBuilder sb = new StringBuilder();
    Field[] fields = component.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        sb.append(field.getName()).append(": ").append(field.get(component).toString());
      } catch (IllegalAccessException e) {
        sb.append(field.getName()).append("=").append("null");
      }
    }
    return sb.toString();
  }

  /**
   * Returns the parameters of a Component to a Map of params.
   *
   * @param component the input component
   * @return The Map of parameters
   */
  public Map<String, String> paramsToMap(Component component) {
    Map<String, String> map = new HashMap<>();

    Class<?> componentClass = component.getClass();
    Field[] fieldsChild = componentClass.getDeclaredFields();
    Field[] fieldsParent = componentClass.getSuperclass().getDeclaredFields();


    Field[] combinedFields = Arrays.copyOf(fieldsChild, fieldsChild.length + fieldsParent.length);
    System.arraycopy(fieldsParent, 0, combinedFields, fieldsChild.length, fieldsParent.length);

    for(Field field:combinedFields) {
      field.setAccessible(true);
      try {
        String value = field.get(component).toString();
        map.put(field.getName(), value);
      } catch (Exception e) {
        map.put(field.getName(), null);
      }
    }
    return map;
  }

}

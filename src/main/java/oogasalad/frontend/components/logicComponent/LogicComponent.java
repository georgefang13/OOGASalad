package oogasalad.frontend.components.logicComponent;


import java.util.AbstractCollection;
import java.util.List;
import javafx.scene.Node;
import oogasalad.frontend.components.displayableComponents.DisplayableComponent;

/**
 *
 * @author Han, Aryan
 * These are the components of the View that represent Rule and Goal creation
 */
public interface LogicComponent extends DisplayableComponent {

 /**
  * This is the method to get the LogicComponents contained within this LogicComponent. For example,
  * getChildren for an if block returns all the LogicComponents contained within the ifBlock.
  * @return a list of all the contained LogicComponents, return null if it's empty
  */
 List<LogicComponent> getChildren();

 /**
  * This is the method to get the LogicComponent that is connected from the beginning of this block.
  * This is the LogicComponent that "flows" into the current Logic Component
  * @return the LogicComponent that runs before, should be null if it's the starting component
  */
 LogicComponent getBefore();

 /**
  * This is the method to get the LogicComponent that is connected from the end of this block.
  *   * This is the LogicComponent that "flows" out of the current Logic Component
  * @return the LogicComponent that runs after, should be null if it's the ending component
  */
 LogicComponent getAfter();

 /**
  * The params of the Code block, such as what values it requests, the number of empty fields to
  * fill, etc.
  * @return a String that contains all the Params information to be parsed by the ViewController
  */
 String getParams();

}

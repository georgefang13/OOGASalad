package oogasalad.backend.ownables.gameobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import oogasalad.backend.Game;
import oogasalad.backend.ownables.Ownable;
import oogasalad.backend.ownables.variables.Variable;
import oogasalad.backend.owners.Owner;

/**
 * A GameObject is an Ownable that can be owned by an Owner
 * Represents an object such as a game piece
 * @author Michael Bryant
 */
public abstract class GameObject extends Ownable {

    ArrayList<Variable> variables = new ArrayList<>();

  /**
   * @see oogasalad.backend.ownables.Ownable#canBeOwnedBy(Owner)
   */
  public abstract boolean canBeOwnedBy(Owner potentialOwner);

  /**
   * Adds a variable to the GameObject.
   * @param variable the variable to add
   */
  public void addVariable(Variable variable) {
    variables.add(variable);
  }

  /**
   * Adds a list of variables to the GameObject.
   * @param variables the list of variables to add
   */
  public void addVariables(ArrayList<Variable> variables) {
    this.variables.addAll(variables);
  }

  /**
   * Gets the list of variables of the GameObject.
   * @return the unmodifiable list of variables
   */
  public List getVariables() {
    return Collections.unmodifiableList(variables);
  }

  /**
   * Removes a variable from the GameObject, if it exists.
   * @param variable the variable to remove
   */
  public void removeVariable(Variable variable) {
    if(variables.contains(variable)) {
      variables.remove(variable);
    }
  }
}


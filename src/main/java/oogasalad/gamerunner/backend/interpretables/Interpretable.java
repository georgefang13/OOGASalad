package oogasalad.gamerunner.backend.interpretables;

import oogasalad.gameeditor.backend.id.IdManageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract implementation for classes that handle instructions
 * Main goal is to encapsulate how instructions are saved
 *
 * @author Rodrigo Bassi Guerreiro
 */
public abstract class Interpretable extends IdManageable {
  private List<String> myInstructions;

  public Interpretable() {
    myInstructions = new ArrayList<>();
  }

  public void addInstruction(String instruction) {
    // TODO: probably check whether instruction is valid
    myInstructions.add(instruction);
  }

  protected Iterable<String> getInstructions() {
    return Collections.unmodifiableList(myInstructions);
  }
}

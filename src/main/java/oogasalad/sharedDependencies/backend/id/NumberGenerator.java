package oogasalad.sharedDependencies.backend.id;

import java.util.Iterator;

/**
 * Generates a sequence of String forms of integers. Used for numbering ownables. Starts at 1. Does
 * not display the number if it is 1. For example, the first three generated values would be: "",
 * "2", "3".
 *
 * @author Michael Bryant
 */
public class NumberGenerator implements Iterator<String> {

  private int currentNumber = 0;

  @Override
  public boolean hasNext() {
    // the generator always has a next number
    return true;
  }

  @Override
  public String next() {
    // generate the next number in the sequence
    currentNumber++;
    if (currentNumber == Integer.MAX_VALUE) {
      // reset the number if it has reached the max value
      currentNumber = 0;
    }
    if (currentNumber == 1) {
      return "";
    }
    return Integer.toString(currentNumber);
  }
}
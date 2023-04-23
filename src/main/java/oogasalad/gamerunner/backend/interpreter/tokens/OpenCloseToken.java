package oogasalad.gamerunner.backend.interpreter.tokens;

public interface OpenCloseToken {

  /**
   * returns true if the bracket is an open bracket ( [ ) and false if it is a close bracket ( ] )
   *
   * @return true if the bracket is an open bracket ( [ ) and false if it is a close bracket ( ] )
   */
  boolean isOpen();
}

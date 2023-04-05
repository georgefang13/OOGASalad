package oogasalad.gamerunner.backend;

import oogasalad.sharedDependencies.backend.ownables.Ownable;

/**
 * An API for the interpreter to call to access and move game assets
 */
public interface GameToInterpreterAPI {

    public Ownable getOwnable(String id);

}

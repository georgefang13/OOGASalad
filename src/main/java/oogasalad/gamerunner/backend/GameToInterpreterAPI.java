package oogasalad.gamerunner.backend;

import oogasalad.sharedDependencies.backend.owners.Player;

/**
 * An API for the interpreter to call to access and move game assets
 */
public interface GameToInterpreterAPI {

    public Player getPlayer(int playerNum);

}

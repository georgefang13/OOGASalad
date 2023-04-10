package oogasalad.gameeditor.backend.goals;

import oogasalad.gameeditor.backend.id.IdManageable;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.owners.Player;

public class Goal extends IdManageable {

    /**
     * Evaluate if a goal is complete. Checks for all players.
     * @param ownableIdManager the ownable id manager
     * @param playerIdManager the player id manager
     * @return the player id of the player who completed the goal, -1 if no one has completed the goal
     */
    public int evaluate(IdManager<Ownable> ownableIdManager, IdManager<Player> playerIdManager){
        return -1;
    }

}

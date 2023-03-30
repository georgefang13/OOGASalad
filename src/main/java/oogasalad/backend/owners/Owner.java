package oogasalad.backend.owners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import oogasalad.backend.ownables.Ownable;

public abstract class Owner {

  private final ArrayList<Ownable> ownables = new ArrayList<>();

  //TODO figure out concurrency issues

  /**
   * Adds an ownable to the owner.
   * Only to be called from the Game.
   * This is where it assigns Ids to Ownables and maps them to the Owner.
   * @param ownable the ownable to add
   */
  public void addOwnable(Ownable ownable) {
    ownables.add(ownable);
  }

  /**
   * Removes an ownable from the owner.
   * @param ownable the ownable to remove
   */
  public void removeOwnable(Ownable ownable) {
    ownables.remove(ownable);
  }

  /**
   * @return an unmodifiable list of the ownables owned by the Owner
   */
  public List getOwnables() {
    return Collections.unmodifiableList(ownables);
  }

  /**
   * Returns a set of the ids of the ownables owned by the owner.
   * @return a set of the ids of the ownables owned by the owner
   */
  public Set getOwnableIds() {
    Set<String> ids = new HashSet<>();
    for (Ownable ownable : ownables) {
      ids.add(ownable.getId());
    }
    return ids;
  }

  /**
   * Removes an ownable from the owner if owned.
   * Only to be called from the Game.
   * @param id the id of the ownable to remove
   */
  public void removeOwnable(String id) {
    for (Ownable ownable : ownables) {
      if (ownable.getId().equals(id)) {
        ownables.remove(ownable);
        return;
      }
    }
  }

}

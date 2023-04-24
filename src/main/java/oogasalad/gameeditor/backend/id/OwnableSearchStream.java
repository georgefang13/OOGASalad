package oogasalad.gameeditor.backend.id;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.owners.Owner;

public class OwnableSearchStream {

  private IdManager<Ownable> ownableIdManager;

  //NOTE: encapsulation over inheritance
  public OwnableSearchStream(IdManager<Ownable> ownableIdManager) {
    this.ownableIdManager = ownableIdManager;
  }

  /**
   * Used for filtering a list of Ownables to only those that are directly owned by a certain owner.
   * @param owner the owner to check for
   * @return a Predicate that returns true if the Ownable is directly owned by the owner
   */
  public Predicate<Ownable> isOfOwner(Owner owner) {
    return ownable -> {
      List<String> ids = new ArrayList<>();
      if (ownable.getOwner().equals(owner)) {
        ids.add(ownableIdManager.getId(ownable));
      }
      return new HashSet<>(ids).contains(ownableIdManager.getId(ownable));
    };
  }

  public Predicate<Ownable> isOfClass(String... classNames) {
    return ownable -> {
      if (ownable == null) {
        return false;
      }
      for (String className : classNames) {
        if (ownable.usesClass(className)) {
          return true;
        }
      }
      return false;
    };
  }



}

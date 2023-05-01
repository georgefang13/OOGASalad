package oogasalad.sharedDependencies.backend.id;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.owners.Owner;

public class OwnableSearchStream {

  private final IdManager<Ownable> ownableIdManager;

  //NOTE: encapsulation over inheritance
  public OwnableSearchStream(IdManager<Ownable> ownableIdManager) {
    this.ownableIdManager = ownableIdManager;
  }

  /**
   * Used for filtering a list of Ownables to only those that are directly owned by a certain owner.
   * @param owner the owner to check for
   * @return a Predicate that returns true if the Ownable is directly owned by the owner
   */
  public Predicate<Ownable> isOwnedByOwner(Owner owner) {
    return ownable -> {
      List<String> ids = new ArrayList<>();
      if (ownable.getOwner() != null && ownable.getOwner().equals(owner)) {
        ids.add(ownableIdManager.getId(ownable));
      }
      return new HashSet<>(ids).contains(ownableIdManager.getId(ownable));
    };
  }

  /**
   * Test if an Ownable contains any of the classes specified
   * @param classNames the classes to check for
   * @return a Predicate that returns true if the Ownable contains any of the classes specified
   */
  public Predicate<Ownable> isOfAnyClass(String... classNames) {
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

  /**
   * Test if an Ownable contains all the classes specified
   * @param classNames the classes to check for
   * @return a Predicate that returns true if the Ownable contains all of the classes specified
   */
  public Predicate<Ownable> isOfAllClasses(String... classNames) {
    return ownable -> {
      if (ownable == null) {
        return false;
      }
      for (String className : classNames) {
        if (!ownable.usesClass(className)) {
          return false;
        }
      }
      return true;
    };
  }

  /**
   * Checks if an object is owned by another ownable. Checks for any ownership, not direct.
   * @param owner the owner ownable
   * @return a Predicate that returns if an object is owned by an ownable
   */
  public Predicate<Ownable> isOwnedByOwnable(Ownable owner) {
    return ownable -> ownableIdManager.getId(ownable).startsWith(ownableIdManager.getId(owner) + ".");
  }

  /**
   * Checks if an ownable is directly owned by another ownable
   * @param owner the owner ownable
   * @return a Predicate that returns if the object is directly owned by an ownable
   */
  public Predicate<Ownable> isDirectlyOwnedByOwnable(Ownable owner) {
    String ownerId = ownableIdManager.getId(owner);
    return ownable -> {
      String ownableId = ownableIdManager.getId(ownable);
      return ownableId.startsWith(ownerId + ".");
    };
  }

}

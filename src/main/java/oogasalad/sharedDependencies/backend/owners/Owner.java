package oogasalad.sharedDependencies.backend.owners;

import oogasalad.gameeditor.backend.id.IdManageable;
import oogasalad.gameeditor.backend.id.IdManager;

/**
 * An Owner owns Ownables.
 * It does not store the Ownables, but can retrieve them from the IdManager.
 */
public abstract class Owner extends IdManageable {

}

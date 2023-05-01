package oogasalad.gamerunner.backend.interpreter.commands.dropzones;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

public class AddDropZoneItem extends OperatorToken {

  public AddDropZoneItem() {
    super(2, "PutDropZoneItem");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t1 = getArg(0).evaluate(env);
    Token t2 = getArg(1).evaluate(env);

    ValueToken<Ownable> value = checkArgumentWithSubtype(env, t1, ValueToken.class, Ownable.class.getName());
    ValueToken<DropZone> dz = checkArgumentWithSubtype(env, t2, ValueToken.class,
        DropZone.class.getName());

    env.getGame().putInDropZone(value.VALUE, dz.VALUE);
    return null;
  }
}

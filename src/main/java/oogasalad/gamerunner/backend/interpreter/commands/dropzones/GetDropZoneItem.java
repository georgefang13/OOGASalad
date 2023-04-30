package oogasalad.gamerunner.backend.interpreter.commands.dropzones;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

public class GetDropZoneItem extends OperatorToken {

  public GetDropZoneItem() {
    super(2, "GetDropZoneItem");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t1 = getArg(0).evaluate(env);
    Token t2 = getArg(1).evaluate(env);

    ValueToken<String> x1 = checkArgumentWithSubtype(env, t1, ValueToken.class,
        String.class.getName());
    ValueToken<DropZone> x2 = checkArgumentWithSubtype(env, t2, ValueToken.class,
        DropZone.class.getName());

    if (!x2.VALUE.hasObject(x1.VALUE)) {
      return new ValueToken<>("");
    }

    return new ValueToken<>(x2.VALUE.getObject(x1.VALUE));
  }
}

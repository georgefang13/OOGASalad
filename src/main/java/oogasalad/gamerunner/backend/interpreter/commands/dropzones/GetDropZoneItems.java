package oogasalad.gamerunner.backend.interpreter.commands.dropzones;

import java.util.List;
import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

public class GetDropZoneItems extends OperatorToken {

  public GetDropZoneItems() {
    super(1, "GetDropZoneItems");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t = getArg(0).evaluate(env);

    ValueToken<DropZone> x = checkArgumentWithSubtype(env, t, ValueToken.class,
        DropZone.class.getName());

    List<Object> items = x.VALUE.getAllObjects();
    Variable<List<Object>> var = new Variable<>(items);
    return env.convertVariableToToken(var);
  }
}
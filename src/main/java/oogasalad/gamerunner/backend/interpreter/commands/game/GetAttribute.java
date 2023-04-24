package oogasalad.gamerunner.backend.interpreter.commands.game;

import java.lang.reflect.Field;
import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;

public class GetAttribute extends OperatorToken {

  public GetAttribute() {
    super(2, "GetAttribute");
  }

  @Override
  public Token evaluate(Environment env) {
    Token t1 = getArg(0).evaluate(env);
    Token t2 = getArg(1).evaluate(env);

    ValueToken<GameObject> x1 = checkArgumentWithSubtype(env, t1, ValueToken.class,
        GameObject.class.getName());
    ValueToken<String> x2 = checkArgumentWithSubtype(env, t2, ValueToken.class,
        String.class.getName());

    GameObject obj = x1.VALUE;
    String attr = x2.VALUE;

    Class<?> c = obj.getClass();

    try {
      Field f = c.getDeclaredField(attr);
      f.setAccessible(true);
      Object value = f.get(obj);
      return new ValueToken<>(value);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      return null;
    }

  }
}

public interface GameEngineAPI {

  /**
   * Receives click on GameObject.
   */
  public void receiveClick(String id);

  /**
   * Receives string from input attached to a GameObject
   */
  public void receiveString(String id, String message);

  /**
   * Receives key up from user
   */
  public void getKeyUp(String key);

  /**
   * Receives key down from user
   */
  public void getKeyDown(String key);
}
package oogasalad.sharedDependencies.backend.database;

public class UserManager {
  // TODO: add encryption system for passwords
  private final static String COLLECTION = "Login Information";
  private final static String ENTRY = "Users";

  private final Database db;

  public UserManager() {
    db = new Database();
  }

  /**
   * Check database to validate login action
   * @param username username
   * @param password unencrypted password
   * @return true if login is successful, else false (user doesn't exist or password doesn't match)
   */
  public boolean tryLogin(String username, String password) {
    Object storedPassword = db.getData(COLLECTION, ENTRY, username);
    return (password.equals(storedPassword));
  }

  /**
   * Check database to validate register action
   * @param username username
   * @param password unencrypted password
   * @return true if register is successful, else false (user already exists)
   */
  public boolean tryRegister(String username, String password) {
    Object storedPassword = db.getData(COLLECTION, ENTRY, username);
    if (storedPassword == null) {
      return false;
    }
    db.addData(COLLECTION, ENTRY, username, password);
    return true;
  }
}

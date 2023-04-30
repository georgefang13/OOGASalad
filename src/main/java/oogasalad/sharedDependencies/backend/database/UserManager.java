package oogasalad.sharedDependencies.backend.database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserManager {
  // TODO: add encryption system for passwords
  private final static String COLLECTION = "Login Information";
  private final static String ENTRY = "Users";

  private final Database db;

  public UserManager(Database database) {
    db = database;
  }

  /**
   * Check database to validate login action
   * @param username username
   * @param password unencrypted password
   * @return true if login is successful, else false (user doesn't exist or password doesn't match)
   */
  public boolean tryLogin(String username, String password) {
    Object storedPassword = db.getData(COLLECTION, ENTRY, username);
    return (encrypt(password).equals(storedPassword));
  }

  /**
   * Check database to validate register action
   * @param username username
   * @param password unencrypted password
   * @return true if register is successful, else false (user already exists)
   */
  public boolean tryRegister(String username, String password) {
    Object storedPassword = db.getData(COLLECTION, ENTRY, username);
    if (storedPassword != null) {
      return false;
    }
    db.addData(COLLECTION, ENTRY, username, encrypt(password));
    return true;
  }

  /**
   * Encrypts a password in a standard format (undisclosed methodology!)
   * @param password password to be encrypted
   * @return encrypted password
   */
  public static String encrypt(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for (byte b : hashedPassword) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }
}

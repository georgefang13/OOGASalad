package oogasalad.sharedDependencies.backend.database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
  private static final String COLLECTION = "Users";
  private static final String PASSWORD_TAG = "password";
  private static final String SECURITY_QUESTION_TAG = "question";
  private static final String SECURITY_ANSWER_TAG = "answer";

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
    if (username == null || username.equals("") || (! db.hasEntry(COLLECTION, username))
    || password == null) {
      return false;
    }
    Object storedPassword = db.getData(COLLECTION, username, PASSWORD_TAG);
    return (encrypt(password).equals(storedPassword));
  }

  public boolean tryRegister(String username, String password) {
    return tryRegister(username, password, new HashMap<>());
  }

  /**
   * Check database to validate register action
   * @param username username
   * @param password unencrypted password
   * @param securityQuestions Map containing security questions as keys and answers as values
   * @return true if register is successful, else false (user already exists)
   */
  public boolean tryRegister(String username, String password, Map<String, String> securityQuestions) {
    if (db.hasEntry(COLLECTION, username)) {
      return false;
    }
    db.addData(COLLECTION, username, PASSWORD_TAG, encrypt(password));
    int counter = 1;
    for (String question : securityQuestions.keySet()) {
      db.addData(COLLECTION, username,
          SECURITY_QUESTION_TAG + counter, question);
      db.addData(COLLECTION, username,
          SECURITY_ANSWER_TAG + counter, securityQuestions.get(question));
      counter++;
    }
    return true;
  }

  /**
   * Check database to validate changing user password
   * @param username username
   * @param newPassword unencrypted password
   * @param securityQuestions Map containing security questions as keys and answers as values
   * @return true if register is successful, else false (user already exists)
   */
  public boolean tryChangePassword(String username, String newPassword, Map<String, String> securityQuestions) {
    for (int i = 1; i <= securityQuestions.size(); i++) {
      String question = (String) db.getData(COLLECTION, username,
          SECURITY_QUESTION_TAG + i);
      if (! securityQuestions.containsKey(question) ||
          ! securityQuestions.get(question)
            .equals(db.getData(COLLECTION, username, SECURITY_ANSWER_TAG + i))) {
        return false;
      }
    }
    db.addData(COLLECTION, username, PASSWORD_TAG, encrypt(newPassword));
    return true;
  }

  /**
   * Encrypts a password in a standard format (undisclosed methodology!)
   * @param password password to be encrypted
   * @return encrypted password
   */
  private static String encrypt(String password) {
    // Code generated by ChatGPT
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

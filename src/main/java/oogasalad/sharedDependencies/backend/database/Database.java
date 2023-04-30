package oogasalad.sharedDependencies.backend.database;


import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.SetOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import com.google.cloud.firestore.Firestore;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


/**
 * General organization for getting information to / from a database
 *
 * @author Rodrigo Bassi Guerreiro
 */
public class Database {
  private static final String DATABASE_INFO_PATH = System.getProperty("user.dir")
      + "/src/main/resources/backend/database/duvalley-boiz-firebase-adminsdk-f3yeq-3262eaff65.json";
  private static final String DATABASE_URL = "https://duvalley-boiz.firebaseio.com/";
  private static final String PROJECT_ID = "duvalley-boiz";

  private final Firestore database;

  public Database() {
    this(PROJECT_ID, DATABASE_INFO_PATH, DATABASE_URL);
  }

  protected Database(String projectId, String infoPath, String url) {
    initializeDatabase(projectId, infoPath, url);
    database = FirestoreClient.getFirestore();
  }

  /**
   * Add data to database
   * @param collection high-level collection to be updated
   * @param entry entry within collection
   * @param field field inside entry
   * @param data data to be saved under specified field
   */
  public void addData(String collection, String entry, String field, Object data) {
    DocumentReference document = database.collection(collection).document(entry);
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put(field, data);
    document.set(dataMap, SetOptions.merge());
    try {
      Thread.sleep(100); // Ensure that read-after-write gets most up-to-date information
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Retrieve data from database
   * @param collection high-level collection to be updated
   * @param entry entry within collection
   * @param field field inside entry
   * @return Object contained in specified field of database, if its class is the one expected
   */
  public Object getData(String collection, String entry, String field) {
    // Code adapted from https://firebase.google.com/docs/firestore/quickstart
    ApiFuture<DocumentSnapshot> document = database.collection(collection).document(entry).get();
    DocumentSnapshot documentSnapshot;
    try {
      documentSnapshot = document.get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
    return documentSnapshot.get(field);
  }

  /**
   * Delete data from database at specified location
   * @param collection high-level collection to be updated
   * @param entry entry within collection
   * @param field field inside entry
   */
  public void deleteData(String collection, String entry, String field) {
    addData(collection, entry, field, FieldValue.delete());
  }

  /**
   * Goes through standard procedure of initializing Firebase
   * @param projectId ID of Firebase project associated with database
   * @param infoPath path to JSON file containing initialization details
   * @param url URL associated with database
   */
  protected void initializeDatabase(String projectId, String infoPath, String url){
    // Code from https://firebase.google.com/docs/firestore/quickstart
    FileInputStream serviceAccount = null;
    GoogleCredentials credentials;
    try {
      serviceAccount = new FileInputStream(infoPath);
      credentials = GoogleCredentials.fromStream(serviceAccount);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(credentials)
        .setDatabaseUrl(url)
        .build();
    FirebaseApp.initializeApp(options);
  }
}

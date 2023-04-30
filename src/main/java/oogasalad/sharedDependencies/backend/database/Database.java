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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import org.apache.maven.plugin.lifecycle.Execution;


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

  private static Database instance;
  private static Firestore database;

  protected Database() {

  }

  private Database(String projectId, String infoPath, String url) {
    initializeDatabase(projectId, infoPath, url);
    database = FirestoreClient.getFirestore();
  }

  public static synchronized Database getInstance() {
    return getInstance(PROJECT_ID, DATABASE_INFO_PATH, DATABASE_URL);
  }

  protected static synchronized Database getInstance(String projectId, String infoPath, String url) {
    if (instance == null) {
      instance = new Database(projectId, infoPath, url);
    }
    return instance;
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
   * Delete entire entry in database (recursively deleting all associated fields)
   * @param collection collection to be deleted
   * @param entry entry within collection
   */
  public void deleteEntry(String collection, String entry) {
    database.collection(collection).document(entry).delete();
  }

  /**
   * Checks if a collection in the database contains a certain entry
   *
   * @param collection collection in database
   * @param entry entry in collection
   * @return boolean, true if collection has entry, else false
   */
  public boolean hasEntry(String collection, String entry) {
    DocumentReference documentReference = database.collection(collection).document(entry);
    try {
      return documentReference.get().get().exists();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get all entries that have been created in a given collection within the database
   * @param collection name of collection
   * @return Iterable of Strings containing existing entries in collection
   */
  public Iterable<String> getEntries(String collection) {
    List<String> entries = new LinkedList<>();
    try {
      for (DocumentSnapshot document : database.collection(collection).get().get()) {
        entries.add(document.getId());
      }
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
    return entries;
  }

  /**
   * Get all fields that exist in the specified entry
   * @param collection name of collection
   * @param entry entry inside collection
   * @return Iterable of Strings containing existing fields in entry
   */
  public Iterable<String> getFields(String collection, String entry) {
    List<String> entries = new LinkedList<>();
    Map<String, Object> data;
    try {
      data = database.collection(collection).document(entry).get().get().getData();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
    return data.keySet();
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

package oogasalad.gamerunner.backend.database;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.SetOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import java.io.FileInputStream;
import java.io.IOException;
import com.google.cloud.firestore.Firestore;
import java.util.HashMap;
import java.util.Map;


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

  public Database() throws IOException {
    this(PROJECT_ID, DATABASE_INFO_PATH, DATABASE_URL);
  }

  protected Database(String projectId, String infoPath, String url) throws IOException {
    initializeDatabase(projectId, infoPath, url);
    database = FirestoreClient.getFirestore();
  }

  public void addData(String collection, String entry, String tag, Object data) {
    DocumentReference document = database.collection(collection).document(entry);
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put(tag, data);
    document.set(dataMap, SetOptions.merge());
  }

//  public void getData(Class<?> clazz, )

  /**
   * Goes through standard procedure of initializing Firebase
   * @param projectId ID of Firebase project associated with database
   * @param infoPath path to JSON file containing initialization details
   * @param url URL associated with database
   */
  protected void initializeDatabase(String projectId, String infoPath, String url) throws IOException {
    // Code from https://firebase.google.com/docs/firestore/quickstart
    FileInputStream serviceAccount = new FileInputStream(infoPath);
    GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(credentials)
        .setDatabaseUrl(url)
        .build();
    FirebaseApp.initializeApp(options);
  }
}

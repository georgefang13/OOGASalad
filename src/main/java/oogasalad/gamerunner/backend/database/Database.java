package oogasalad.gamerunner.backend.database;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * General organization for getting information to / from a database
 *
 * @author Rodrigo Bassi Guerreiro
 */
public class Database {
  private static final String DATABASE_INFO_PATH = System.getProperty("user.dir")
      + "/src/main/resources/backend/database/duvalley-boiz-firebase-adminsdk-f3yeq-3262eaff65.json";
  private static final String DATABASE_URL = "https://duvalley-boiz.firebaseio.com/";

  private final DatabaseReference topLevel;

  public Database() throws IOException {
    this("");
  }

  public Database(String... tags) throws IOException {
    this(DATABASE_INFO_PATH, DATABASE_URL, tags);
  }

  protected Database(String infoPath, String url, String... tags) throws IOException {
    initializeDatabase(infoPath, url);
    topLevel = FirebaseDatabase.getInstance().getReference(String.join("/", tags));
  }

  public void addData(Object data, String... tags) {
    traverse(tags).setValue(data, (databaseError, databaseReference) -> {
      if (databaseError != null) {
        throw new RuntimeException();
      }
    });
  }

//  public void getData(Class<?> clazz, )

  /**
   * Goes through standard procedure of initializing Firebase
   * @param infoPath path to JSON file containing initialization details
   * @param url URL associated with database
   */
  protected void initializeDatabase(String infoPath, String url) throws IOException {
    // Code from https://firebase.google.com/docs/admin/setup/
    FileInputStream serviceAccount = new FileInputStream(infoPath);
    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl(url)
        .build();
    FirebaseApp.initializeApp(options);
  }

  private DatabaseReference traverse(String... tags) {
    DatabaseReference ref = topLevel;
    for (String tag : tags) {
      ref = ref.child(tag);
    }
    return ref;
  }
}

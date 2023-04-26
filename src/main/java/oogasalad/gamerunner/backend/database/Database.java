package oogasalad.gamerunner.backend.database;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * General organization for getting information to / from a database
 *
 * @author Rodrigo Bassi Guerreiro
 */
public class Database {
  public static final String DATABASE_INFO_PATH = System.getProperty("user.dir")
      + "/src/main/resources/backend/database/duvalley-boiz-firebase-adminsdk-f3yeq-3262eaff65.json";

  public Database() throws IOException {
    FileInputStream serviceAccount = new FileInputStream(DATABASE_INFO_PATH);
    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build();
    FirebaseApp.initializeApp(options);
  }

}

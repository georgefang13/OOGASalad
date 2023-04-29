package oogasalad.frontend.nodeEditor.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SerializationUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(new AbstractNodeTypeAdapterFactory()).create();

    public static <T> void serialize(T object, String filePath) throws IOException {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(object, writer);
        }
    }

    public static <T> T deserialize(String filePath, Class<T> clazz) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            return gson.fromJson(reader, clazz);
        }
    }
}

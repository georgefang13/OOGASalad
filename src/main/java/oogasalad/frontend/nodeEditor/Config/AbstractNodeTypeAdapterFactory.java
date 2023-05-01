package oogasalad.frontend.nodeEditor.Config;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.scene.layout.VBox;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;

import java.io.IOException;
import java.lang.reflect.Type;

public class AbstractNodeTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        if (!AbstractNode.class.isAssignableFrom(typeToken.getRawType())) {
            return null;
        }

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, typeToken);

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter jsonWriter, T t) throws IOException {
                delegate.write(jsonWriter, t);
            }

            @Override
            public T read(JsonReader jsonReader) throws IOException {
                T node = delegate.read(jsonReader);
                if (node instanceof AbstractNode) {
//                    ((AbstractNode) node).initializeVBox();
                }
                return node;
            }
        };
    }


}

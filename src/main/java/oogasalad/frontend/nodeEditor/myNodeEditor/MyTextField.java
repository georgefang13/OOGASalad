package oogasalad.frontend.nodeEditor.myNodeEditor;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MyTextField extends TextField implements Serializable {

    // Constructor
    public MyTextField(String text) {
        super(text);
    }

    // Override the writeObject method to write only the text property
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(getText());
    }

    // Override the readObject method to read the text property and set it to the TextField
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        setText((String) in.readObject());
    }
}
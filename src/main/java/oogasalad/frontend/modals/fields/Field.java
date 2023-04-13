package oogasalad.frontend.modals.fields;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.util.List;

abstract class Field {
    abstract Control createControl();

    class TextFieldComponent extends Field {
        private String text;

        TextFieldComponent(String text) {
            this.text = text;
        }

        @Override
        Control createControl() {
            TextField textField = new TextField();
            textField.setText(text);
            return textField;
        }
    }

    class ChoiceBoxComponent extends Field {
        private List<String> options;

        ChoiceBoxComponent(List<String> options) {
            this.options = options;
        }

        @Override
        Control createControl() {
            ChoiceBox<String> choiceBox = new ChoiceBox<>();
            choiceBox.getItems().addAll(options);
            return choiceBox;
        }
    }

    class ButtonComponent extends Field {
        private String text;

        ButtonComponent(String text) {
            this.text = text;
        }

        @Override
        Control createControl() {
            return new Button(text);
        }
    }

}
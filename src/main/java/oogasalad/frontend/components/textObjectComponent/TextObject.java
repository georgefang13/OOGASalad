package oogasalad.frontend.components.textObjectComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.frontend.components.AbstractComponent;

import java.util.ResourceBundle;


public class TextObject extends AbstractComponent implements TextObjectComponent {
    private Text text;
    private final String DEFAULT_FILE_PATH = "frontend.properties.Defaults.TextObject";
    private ResourceBundle DEFAULT_BUNDLE = ResourceBundle.getBundle(DEFAULT_FILE_PATH);

    public TextObject(int id) {
        super(id);
        this.setDefault();
        this.followMouse();
        this.getNode();
    }

    public TextObject(int id, Node container, String content, double x, double y, Color color, double fontSize) {
        super(id, container);
        text = new Text(content);
        text.setX(x);
        text.setY(y);
        text.setFill(color);
        text.setFont(Font.font(fontSize));
    }

    @Override
    public void setText(String content) {
        text.setText(content);
    }

    @Override
    public void setTextPosition(double x, double y) {
        text.setX(x);
        text.setY(y);
    }

    @Override
    public void setTextColor(Color color) {
        text.setFill(color);
    }

    @Override
    public void setTextSize(double fontSize) {
        text.setFont(Font.font(fontSize));
    }

    @Override
    public Text getNode() {
        return text;
    }

    @Override
    public void setDefault() {
        String content = DEFAULT_BUNDLE.getString("text.content");
        double x = Double.parseDouble(DEFAULT_BUNDLE.getString("text.x"));
        double y = Double.parseDouble(DEFAULT_BUNDLE.getString("text.y"));
        String colorString = DEFAULT_BUNDLE.getString("text.color");
        double fontSize = Double.parseDouble(DEFAULT_BUNDLE.getString("text.fontSize"));
        Color color = Color.web(colorString);

        text = new Text(x, y, content);
        text.setFont(Font.font(fontSize));
        text.setFill(Color.BLACK);
    }
}

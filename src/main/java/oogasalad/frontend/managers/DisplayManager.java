package oogasalad.frontend.managers;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
/**
 * @author Owen MacKenzie
 */
public class DisplayManager {
    public static Node loadImage(String imgPath, int width, int height){ //TODO: A similar function is in game object move it there just send paths
        Image img;
        try {
            img = new Image(new FileInputStream(imgPath));
        } catch (Exception e) {
            System.out.println("Image " + imgPath + " not found");
            return null;
        }
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
}

package oogasalad;

import java.util.List;

public class SplashScreenWindow extends AbstractWindow {
    public SplashScreenWindow() {
        AbstractScene scene = new SplashMainScene();
        setScene(scene);
    }
}

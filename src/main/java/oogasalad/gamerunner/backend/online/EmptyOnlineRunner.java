package oogasalad.gamerunner.backend.online;

import oogasalad.gamerunner.backend.Game;

/**
 * This class uses the null design pattern. Does nothing.
 */
public class EmptyOnlineRunner implements OnlineRunner{
    @Override
    public void create() {}

    @Override
    public void join(String code) {}

    @Override
    public String getCode() {
        return "";
    }

    public void send(String type, Object... args){}

    @Override
    public int getPlayerNum() {
        return 0;
    }

    @Override
    public void start() {

    }
}
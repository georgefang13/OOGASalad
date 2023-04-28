package oogasalad.gamerunner.backend.online;

public interface OnlineRunner {
    void create();
    void join(String code);
    String getCode();
    void send(String type, Object... args);

    int getPlayerNum();

    void start();
}
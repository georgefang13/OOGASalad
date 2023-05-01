package oogasalad.gamerunner.backend.online;

public interface OnlineRunner {
    /**
     * Creates a new game
     */
    void create();

    /**
     * Joins an existing game
     * @param code the code of the game to join
     */
    void join(String code);

    /**
     * Gets the code of the game
     * @return the code of the game
     */
    String getCode();

    /**
     * Sends a message to the server
     * @param type the main data
     * @param args anything else
     */
    void send(String type, Object... args);

    /**
     * Gets the number of players
     * @return the number of players
     */
    int getPlayerNum();

    /**
     * Starts the game
     */
    void start();
}
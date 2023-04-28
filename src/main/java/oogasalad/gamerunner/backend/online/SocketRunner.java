package oogasalad.gamerunner.backend.online;


import io.socket.client.IO;
import io.socket.client.Socket;
import oogasalad.gamerunner.backend.Game;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class SocketRunner implements OnlineRunner {
    private static final String SERVER_URL = "https://gameserver.duvalleyboiz.repl.co";
    private final Socket socket;
    private String code = "";
    private final Game game;
    private final Map<String, Boolean> runMap = new HashMap<>();

    private int playerNum;
    public SocketRunner(Game game){
        this.game = game;
        URI uri = URI.create(SERVER_URL);
        IO.Options options = IO.Options.builder().build();

        socket = IO.socket(uri, options);
        socket.on(io.socket.client.Socket.EVENT_CONNECT, args1 -> {
            System.out.println("Connected!");
        });
        socket.connect();

        socket.on("change", args -> {
           String obj = (String) args[0];
            System.out.println("FOUND CLICK " + obj);
           game.clickPiece(obj, false);
        });
        socket.on("player", args -> {
            game.setNumPlayers((int) args[0]);
        });
        socket.on("start", args -> {
            game.startOnlineGame();
        });
    }

    public void send(String type, Object... args){
        if (!runMap.containsKey(type)) runMap.put(type, false);
        if (!runMap.get(type)){
            socket.emit("change", type, args);
        }
        runMap.put(type, false);
    }

    @Override
    public int getPlayerNum() {
        return playerNum;
    }

    public void start(){
        socket.emit("start");
    }

    @Override
    public void create(){
        socket.emit("create");
        System.out.println("creating room...");
        socket.once("room", args -> {
            playerNum = 0;
            code = (String) args[0];
            game.sendCode(code);
            System.out.println("CODE: " + code);
        });
    }
    @Override
    public void join(String code){
        socket.emit("join", code);
        socket.once("entered", args -> {
            System.out.println("Entered!");
            playerNum = ((int) args[0]) - 1;
            game.setNumPlayers(playerNum + 1);
            this.code = code;
        });
    }
    @Override
    public String getCode(){
        return code;
    }

}

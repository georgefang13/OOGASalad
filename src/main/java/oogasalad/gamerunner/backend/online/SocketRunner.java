package oogasalad.gamerunner.backend.online;


import io.socket.client.IO;
import io.socket.client.Socket;
import oogasalad.gamerunner.backend.Game;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.owners.Player;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.InvocationTargetException;
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
           String type = (String) args[0];
           switch(type){
               case "movePiece" -> movePiece(args);
                case "removePiece" -> removePiece(args);
                case "putInDropZone" -> putInDropZone(args);
                case "setTurn" -> setTurn(args);
                case "putClass" -> putClass(args);
                case "removeClass" -> removeClass(args);
                case "setObjectImage" -> setObjectImage(args);
                case "setObjectOwner" -> setObjectOwner(args);
                case "setPlayerOwner" -> setPlayerOwner(args);
           }
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

    private void movePiece(Object... args){
        runMap.put("movePiece", true);
        JSONArray arr = (JSONArray) args[1];
        try {
            String objName = arr.getString(0);
            String dzName = arr.getString(1);
            String name = arr.getString(2);
            game.movePiece((GameObject) game.getVariable(objName), (DropZone) game.getVariable(dzName), name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void removePiece(Object... args){
        runMap.put("removePiece", true);
        JSONArray arr = (JSONArray) args[1];
        try {
            String objName = arr.getString(0);
            GameObject g = (GameObject) game.getVariable(objName);
            game.removePiece(g);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void putInDropZone(Object... args){
        runMap.put("putInDropZone", true);
        JSONArray arr = (JSONArray) args[1];
        try {
            String objName = arr.getString(0);
            String dzName = arr.getString(1);
            game.putInDropZone(game.getVariable(objName), (DropZone) game.getVariable(dzName), (String) args[3]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setTurn(Object... args) {
        JSONArray arr = (JSONArray) args[1];
        try {
            double playerNum = arr.getDouble(0);
            runMap.put("setTurn", true);
            game.setTurn(playerNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void putClass(Object... args){
        runMap.put("putClass", true);
        String objName = (String) args[1];
        game.putClass(game.getVariable(objName), (String) args[2]);
    }

    private void removeClass(Object... args){
        runMap.put("removeClass", true);
        String objName = (String) args[1];
        game.removeClass(game.getVariable(objName), (String) args[2]);
    }

    private void setObjectImage(Object... args){
        runMap.put("setObjectImage", true);
        String objName = (String) args[1];
        game.setObjectImage(game.getVariable(objName), (String) args[2]);
    }

    private void setObjectOwner(Object... args){
        runMap.put("setObjectImage", true);
        String objName = (String) args[1];
        String ownerName = (String) args[2];
        game.setObjectOwner(game.getVariable(objName), game.getVariable(ownerName));
    }

    private void setPlayerOwner(Object... args){
        runMap.put("setPlayerOwner", true);
        String objName = (String) args[1];
        int ownerName = (int) args[2];
        game.setPlayerOwner(game.getVariable(objName), game.getOwner(ownerName));
    }

    @Override
    public void create(){
        socket.emit("create");
        socket.once("room", args -> {
            playerNum = 0;
            code = (String) args[0];
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

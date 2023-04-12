package oogasalad.gamerunner.backend.fsm;

import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;

enum States {
    INIT, MOVE1, DONE
}

class TurnState extends State {
    DropZone selected;
    @Override
    public Object getValue() {
        return selected;
    }

    @Override
    public void setInnerValue(FSM.StateData data, Object value) {
        selected = (DropZone) value;
    }

    @Override
    public void onLeave(FSM.StateData data){
        IdManager<?> idManager = (IdManager<?>) data.get("idManager");
        Variable<Integer> turnVar = (Variable<Integer>) idManager.getObject("turn");
        int turn = turnVar.get();

        if (turn == 0){
            selected.putObject("piece", "X");
        }
        else {
            selected.putObject("piece", "O");
        }
    }

}

class DoneState extends State {
    @Override
    public void onEnter(FSM.StateData data) {
        IdManager<?> idManager = (IdManager<?>) data.get("idManager");
        Variable<Integer> turn = (Variable<Integer>) idManager.getObject("turn");
        Variable<Integer> numPlayers = (Variable<Integer>) idManager.getObject("numPlayers");
        turn.set((turn.get() + 1) % numPlayers.get());
    }

    @Override
    public Object getValue() {
        return null;
    }
}

class InitState extends State {
    @Override
    public void onEnter(FSM.StateData data) {
        IdManager<?> idManager = (IdManager<?>) data.get("idManager");

        List<GameObject> available = new ArrayList<>();

        System.out.println("running onInit");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                DropZone dropZone = (DropZone) idManager.getObject(i + "," + j);
                if (dropZone.getAllObjects().size() == 0){
                    available.add(dropZone);
                }
            }
        }

        Variable<List<GameObject>> availableVar = (Variable<List<GameObject>>) idManager.getObject("available");
        availableVar.set(available);
    }

    @Override
    public Object getValue() {
        return null;
    }
}

public class FSMExample {
    public static void main(String[] args) {
        IdManager<Ownable> idManager = new IdManager<>();

        FSM<States> fsm = new FSM<>(idManager);

        Variable<Integer> turn = new Variable<>(0);
        turn.addListener((value) -> fsm.setState(States.INIT));

        Variable<Integer> numPlayers = new Variable<>(2);
        Variable<List<GameObject>> availableVar = new Variable(new ArrayList<>());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                addDropZone(i, j, idManager);
            }
        }

        idManager.addObject(turn, "turn");
        idManager.addObject(numPlayers, "numPlayers");
        idManager.addObject(availableVar, "available");

        fsm.putState(States.INIT, new InitState(), (state, data) -> States.MOVE1);
        fsm.putState(States.MOVE1, new TurnState(), (state, data) -> States.DONE);
        fsm.putState(States.DONE, new DoneState(), (state, data) -> States.DONE);

        fsm.setState(States.INIT);

        Scanner in = new Scanner(System.in);

        while (true){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    DropZone dropZone = (DropZone) idManager.getObject(i + "," + j);
                    if (dropZone.getAllObjects().size() == 0){
                        System.out.print("-");
                    }
                    else {
                        System.out.print(dropZone.getAllObjects().get(0));
                    }
                }
                System.out.println();
            }
            System.out.println("Turn: " + ((Variable<Integer>) idManager.getObject("turn")).get());
            fsm.transition();

            List<GameObject> available = availableVar.get();
            if (available.size() == 0){
                break;
            }

            System.out.print("Available spots: ");
            for (GameObject obj : available) {
                System.out.print(idManager.getId(obj) + " ");
            }
            System.out.println("\nSelect a spot: ");
            String s = in.nextLine();
            fsm.setStateInnerValue(idManager.getObject(s));
            fsm.transition();
        }
    }

    private static void addDropZone(int y, int x, IdManager idManager){
        DropZone dropZone = new DropZone(y + "," + x);
        idManager.addObject(dropZone, y + "," + x);
    }
}

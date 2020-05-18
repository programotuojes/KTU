/****************************************************************
 * Šioje klasėje tiriame bazinių klasių Stack ir Queue metodus
 *
 * Visos demonstracinės klasės turi main metodą ir vykdomos Run File (Shift+F6)
 *
 * Pradžioje vykdykite kodą ir stebėkite atliekamus veiksmus
 * Užduotis atlikite sekdami nurodymus programinio kodo komentaruose
 * Gynimo metu atlikite dėstytojo nurodytas užduotis naujų metodų pagalba.
 *
 * @author Eimutis Karčiauskas, KTU programų inžinerijos katedra 2019 08 05
 **************************************************************************/
package demos.console;

import extendsFX.BaseConsole;

import java.util.*;

import javafx.stage.Stage;

public class Demo2_StackQueue extends BaseConsole {
    final Stack<String> stack = new Stack<>();
    final Queue<String> queue = new ArrayDeque<>();
    final List<String> list = new LinkedList<>();
    private int click;

    private void pushStack() {
        stack.push("e" + click++);
        printLn(stack.toString());
    }

    private void popStack() {
        try {  // gaudome tuščio steko exception
            String t = stack.pop();
            queue.add(t);
            printLn(stack.toString());
            ta1.appendText(queue.toString() + nL);
        } catch (EmptyStackException e) {
            printLn("ERROR!!! Operacija pop() su tuščiu steku negalima");
        }
    }

    private void poolQueue() {
        queue.poll();
        ta1.appendText(queue.toString() + nL);
    }

    private void addToListMid() {
        list.add(list.size() / 2, "e" + click++);
        ta1.appendText(list.toString() + nL);
    }

    @Override
    public void createControls() {
        super.createControls();
        addButton("push", action -> pushStack());
        addButton("pop", action -> popStack());
        addButton("pool", action -> poolQueue());
        addButton("addToMid", action -> addToListMid());
    }

    // UŽDUOTIS: ištirkite kitų struktūrų metodus
// https://www.geeksforgeeks.org/introduction-to-data-structures-10-most-commonly-used-data-structures/    
    @Override
    public void start(Stage stage) throws Exception {
        setTextAreas("green", "red", 350, 400);
        stage.setTitle("Eksperimentai su Stack'u ir Queue (VirP)");

        super.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}

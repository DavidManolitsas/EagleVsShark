package main.java.model.commands;

import java.util.Stack;

/**
 * @author WeiYi Yu
 * @date 2020-06-01
 */
public class MoveController {

    private final Stack<Command> commandHistory;

    public MoveController() {
        commandHistory = new Stack<>();
    }

    public void submit(Command command) {
        commandHistory.push(command);
        command.execute();
    }

    public void undo(int steps) {
        int popSize = Math.min(steps * 2, commandHistory.size());

        for (int i = 0; i < popSize; i++) {
            commandHistory.pop().undo();
        }
    }
}

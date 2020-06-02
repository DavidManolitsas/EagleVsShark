package main.java.model.commands;

public interface Command {
    void execute();

    void undo();
}

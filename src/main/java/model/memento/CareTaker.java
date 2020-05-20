package main.java.model.memento;

import java.util.LinkedList;

public class CareTaker {
    private LinkedList<Memento> mementos;

    public CareTaker() {
        mementos = new LinkedList<Memento>();
    }

    public void addMemento(Memento memento) {
        mementos.push(memento);
    }

    public Memento getMemento() {
        return mementos.pop();
    }

    public LinkedList<Memento> getMementos() {
        return mementos;
    }

    public int getMementoNumbers() {
        return mementos.size();
    }
}

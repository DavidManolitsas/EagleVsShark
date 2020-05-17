package main.java.model.momento;

import java.util.LinkedList;

public class CareTaker {
    private LinkedList<Memento> mementos;

    public CareTaker() {
        mementos = new LinkedList<Memento>();
    }

    public void addMomento(Originator originator) {
        mementos.push(originator.create());
    }

    public Memento getMomento() {
        return mementos.pop();
    }

    public LinkedList<Memento> getMementos() {
        return mementos;
    }

    public int getMomentoNumbers() {
        return mementos.size();
    }
}

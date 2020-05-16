package main.java.model.momento;

import java.util.LinkedList;

public class CareTaker {
    private LinkedList<Momento> momentos;

    public CareTaker() {
        momentos = new LinkedList<Momento>();
    }

    public void addMomento(Originator originator) {
        momentos.push(originator.create());
    }

    public Momento getMomento() {
        return momentos.pop();
    }

    public LinkedList<Momento> getMomentos() {
        return momentos;
    }

    public int getMomentoNumbers() {
        return momentos.size();
    }
}

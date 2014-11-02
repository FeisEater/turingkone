/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turing;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author FeisEater
 */
public class State {
    private final String name;
    private final Map<Character, Transition> transitions;

    public State(String name) {
        this.name = name;
        transitions = new HashMap<>();
    }
    
    public String getName() {
        return name;
    }

    public Transition getTransition(char in) {
        return transitions.get(in);
    }
    
    public void addTransition(Transition trans)
    {
        transitions.put(trans.getInChar(), trans);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
}

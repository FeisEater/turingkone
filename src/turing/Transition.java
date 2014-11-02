/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turing;

/**
 *
 * @author FeisEater
 */
public class Transition {
    private final char inChar;
    private final char outChar;
    private final Shift shift;
    private final State nextState;

    public Transition(char inChar, char outChar, Shift shift, State next) {
        this.inChar = inChar;
        this.outChar = outChar;
        this.shift = shift;
        this.nextState = next;
    }

    public Transition(char inChar, Shift shift, State next) {
        this.inChar = inChar;
        this.outChar = inChar;
        this.shift = shift;
        this.nextState = next;
    }

    public char getInChar() {
        return inChar;
    }

    public char getOutChar() {
        return outChar;
    }

    public Shift getShift() {
        return shift;
    }

    public State getNextState() {
        return nextState;
    }
    
    
}

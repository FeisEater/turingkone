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
public class Machine {
    private TapeCell tape;
    private State currentState;
    private final State haltState;

    public Machine(TapeCell tape, State startState, State halt) {
        this.tape = tape;
        this.currentState = startState;
        this.haltState = halt;
    }
    
    public void step()
    {
        Transition trans = currentState.getTransition(tape.getValue());
        if (trans == null)
        {
            currentState = haltState;
            return;
        }
        tape.setValue(trans.getOutChar());
        switch (trans.getShift())
        {
            case RIGHT:
                tape = tape.getNext();
                break;
            case LEFT:
                tape = tape.getPrev();
                break;
            default:
                break;
        }
        currentState = trans.getNextState();
        printProcess(5);
    }
    
    public boolean halted()
    {
        return currentState == haltState;
    }
    
    public void printProcess(int tapeLength)
    {
        System.out.println(currentState);
        for (int i = 0; i < tapeLength; i++)
            System.out.print(" ");
        System.out.println("|");
        for (int i = 0; i < tapeLength; i++)
            System.out.print(" ");
        System.out.println("v");
        TapeCell printTape = tape;
        for (int i = 0; i < tapeLength; i++)
            printTape = printTape.getPrev();
        for (int i = 0; i < tapeLength; i++)
        {
            System.out.print(printTape);
            printTape = printTape.getNext();
        }
        for (int i = 0; i <= tapeLength; i++)
        {
            System.out.print(printTape);
            printTape = printTape.getNext();
        }
        System.out.println("");
        for (int i = 0; i < tapeLength * 2 + 1; i++)
            System.out.print("-");
        System.out.println("");
    }
}

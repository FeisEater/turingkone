/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turing;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author FeisEater
 */
public class Turing {

    /**
     * @param args the command line arguments
     */
    private static final Map<String, State> states = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in);
    private static TapeCell tape;
    private static State startState;
    private static State halt;
    
    public static void main(String[] args) {
        /*tape = setupInput("111");
        makeTransition("inc", "ff", '0', '1', Shift.RIGHT);
        makeTransition("inc", "inc", '1', '0', Shift.RIGHT);
        makeTransition("inc", "accept", '_', '1', Shift.RIGHT);
        makeTransition("ff", "ff", '0', Shift.RIGHT);
        makeTransition("ff", "ff", '1', Shift.RIGHT);
        makeTransition("ff", "accept", '_', Shift.RIGHT);*/
        userSetup();
        Machine machine = new Machine(tape, startState, halt);
        System.out.println("Machine started!");
        machine.printProcess(5);
        while (!machine.halted())
            machine.step();
        System.out.println("Machine halted!");
    }
    
    public static void userSetup()
    {
        askTransitions();
        askInput();
        askStart();
        askHalt();
    }
    
    private static void askInput() {
        System.out.println("Initialize tape (all characters before and after your given character string will be set to '_')");
        tape = setupInput(sc.nextLine());
        System.out.println("Give tape offset, 0 is first character of your input");
        int offset = Integer.parseInt(sc.nextLine());
        if (offset > 0)
            for (int i = 0; i < offset; i++)
                tape = tape.getNext();
        if (offset < 0)
            for (int i = 0; i < offset; i++)
                tape = tape.getPrev();
    }

    private static void askTransitions() {
        System.out.println("Define state transitions using following arguments separated by whitespace:");
        System.out.println("State to transition FROM | State to transition TO | read tape character | written tape character (not required) | shift direction (L/R)");
        System.out.println("Each transition is defined in one line. When you're done, give an empty line");
        while (true)
        {
            String givenLine = sc.nextLine();
            if (givenLine.isEmpty())    break;
            String[] args = givenLine.split(" ");
            if (args.length < 4)
            {
                System.out.println("Not enough arguments");
                continue;
            }
            if (args.length > 5)
                System.out.println("Too many arguments, leftovers will be discarded");
            char dir = (args.length == 4) ? args[3].charAt(0) : args[4].charAt(0);
            if (dir != 'l' && dir != 'L' && dir != 'r' && dir != 'R')
                System.out.println("Didn't recognize shift direction '" + dir + "', set it to right");
            else if ((args.length == 5 && args[4].length() > 1) || (args.length == 4 && args[3].length() > 1))
                System.out.println("Shift direction can be defined with one character, 'l' or 'r'");
            if (args[2].length() > 1)   System.out.println("Read tape character consisted of several characters, set it to first character");
            if (args.length == 5 && args[3].length() > 1)
                System.out.println("Written tape character consisted of several characters, set it to first character");
            Shift shift;
            switch (dir)
            {
                case 'l':
                case 'L':
                    shift = Shift.LEFT;
                    break;
                default:
                    shift = Shift.RIGHT;
                    break;
            }
            String direction = (shift == Shift.LEFT) ? "left" : "right";
            if (args.length == 4)
            {
                makeTransition(args[0], args[1], args[2].charAt(0), shift);
                System.out.println("Transition made from " + args[0] + " to " + args[1] + " when character '" + args[2].charAt(0) + "' is read. Then shift tape to the " + direction);
            }
            else
            {
                makeTransition(args[0], args[1], args[2].charAt(0), args[3].charAt(0), shift);
                System.out.println("Transition made from " + args[0] + " to " + args[1] + " when character '" + args[2].charAt(0) + "' is read. Then write character '" + args[3].charAt(0) + "' and shift tape to the " + direction);
            }
            System.out.println(printStates());
        }
    }

    private static void askStart() {
        System.out.println("Define starting state (" + printStates() + "):");
        String givenState = sc.nextLine();
        if (!states.keySet().contains(givenState))
        {
            System.out.println("You gave a new state without any transitions. This will halt the machine immediately. Please define your starting state again");
            System.out.println("This prompt will not be repeated if you wish to proceed with your initial wish");
            givenState = sc.nextLine();
        }
        startState = getState(givenState);
    }

    private static void askHalt() {
        System.out.println("Define halt state (you can give a state that was not defined in transitions):");
        halt = getState(sc.nextLine());
    }

    public static TapeCell setupInput(String input)
    {
        TapeCell start = new TapeCell('_');
        TapeCell t = start;
        for (int i = 0; i < input.length(); i++)
        {
            t.setValue(input.charAt(i));
            t = t.getNext();
        }
        return start;
    }
    
    public static void makeTransition(String start, String end, char in, char out, Shift shift)
    {
        getState(start).addTransition(new Transition(in, out, shift, getState(end)));
    }
    
    public static void makeTransition(String start, String end, char in, Shift shift)
    {
        getState(start).addTransition(new Transition(in, shift, getState(end)));
    }

    public static State getState(String state)
    {
        if (states.containsKey(state))
            return states.get(state);
        State newstate = new State(state);
        states.put(state, newstate);
        return newstate;
    }

    public static String printStates()
    {
        String result = "Defined states: ";
        for (String s : states.keySet())
            result += s + " ";
        return result;
    }
}

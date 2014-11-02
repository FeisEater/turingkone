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
public class TapeCell {
    private TapeCell prev;
    private TapeCell next;
    private char value;

    public TapeCell(char value) {
        this.value = value;
    }

    public TapeCell getPrev() {
        if (prev == null)
            setPrev(new TapeCell('_'));
        return prev;
    }

    public void setPrev(TapeCell prev) {
        if (this.prev == null)
        {
            this.prev = prev;
            prev.setNext(this);
        }
    }

    public TapeCell getNext() {
        if (next == null)
            setNext(new TapeCell('_'));
        return next;
    }

    public void setNext(TapeCell next) {
        if (this.next == null)
        {
            this.next = next;
            next.setPrev(this);
        }
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}

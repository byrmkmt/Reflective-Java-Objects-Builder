package org.reflections;

import java.util.List;
import java.util.Map;

public class ChildObject extends ParentObject{
    private GrandChildObject child = null;
    private List<Integer> numbers = null;
    private String word = "Bayram";
    private int value = 2;

    public GrandChildObject getChild() {
        return child;
    }

    public void setChild(GrandChildObject child) {
        this.child = child;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public String getWord() {
        return word;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setWord(String word) {
        this.word = word;
    }



}

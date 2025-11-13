package org.reflections;

import java.util.List;
import java.util.Map;

public class GrandChildObject {
    private String word = "Bayram";
    private List<Integer> numbers = null;
    private Map<Integer,String> maps = null;

    public Map<Integer, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<Integer, String> maps) {
        this.maps = maps;
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

    public void setWord(String word) {
        this.word = word;
    }
}

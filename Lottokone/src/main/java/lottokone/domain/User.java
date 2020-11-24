package lottokone.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private List<Integer[]> numbersList;
    
    public User(String name) {
        this(name, new ArrayList<>());
    }
    
    public User(String name, List<Integer[]> numbersList) {
        this.name = name;
        this.numbersList = numbersList;
    }

    public String getName() {
        return name;
    }

    public List<Integer[]> getNumbersList() {
        return numbersList;
    }

    public void setNumbersList(List<Integer[]> numbersList) {
        this.numbersList = numbersList;
    }
}

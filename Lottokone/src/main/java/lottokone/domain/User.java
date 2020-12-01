package lottokone.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private List<int[]> numbersList;
    
    public User(String name) {
        this(name, new ArrayList<>());
    }
    
    public User(String name, List<int[]> numbersList) {
        this.name = name;
        this.numbersList = numbersList;
    }

    public String getName() {
        return name;
    }

    public List<int[]> getNumbersList() {
        return numbersList;
    }

    public void setNumbersList(List<int[]> numbersList) {
        this.numbersList = numbersList;
    }
    
    public void addNumbers(int[] numbers) {
        this.numbersList.add(numbers);
    }
    
    public void removeNumbers(int i) {
        this.numbersList.remove(i);
    }
}

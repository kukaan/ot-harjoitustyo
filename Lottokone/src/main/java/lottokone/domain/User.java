package lottokone.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private List<Numbers> numbersList;
    
    public User(String name) {
        this(name, new ArrayList<>());
    }
    
    public User(String name, List<Numbers> numbersList) {
        this.name = name;
        this.numbersList = numbersList;
    }

    public String getName() {
        return name;
    }

    public List<Numbers> getNumbersList() {
        return numbersList;
    }

    public void setNumbersList(List<Numbers> numbersList) {
        this.numbersList = numbersList;
    }
    
    public void addNumbers(Numbers numbers) {
        this.numbersList.add(numbers);
    }
    
    public void removeNumbers(int i) {
        this.numbersList.remove(i);
    }
}

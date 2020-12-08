package lottokone.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Numbers {
    private List<Integer> numbers;

    public Numbers(int[] numbers) {
        this.numbers = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            this.numbers.add(i, numbers[i]);
        }
    }
    
    public Numbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    @Override
    public String toString() {
        return numbers.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass() && int[].class != o.getClass()) {
            return false;
        }
        if (int[].class == o.getClass()) {
            int[] nums = (int[]) o;
            int[] theseNumbers = new int[this.numbers.size()];
            for (int i = 0; i < this.numbers.size(); i++) {
                theseNumbers[i] = this.numbers.get(i);
            }
            return Arrays.equals(theseNumbers, nums);
        }
        if (List.class == o.getClass()) {
            List nums = (List) o;
            return this.numbers.equals(nums);
        }
        Numbers nums = (Numbers) o;
        return this.numbers.equals(nums.getNumbers());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.numbers);
        return hash;
    }
}

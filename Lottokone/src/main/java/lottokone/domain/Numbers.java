package lottokone.domain;

import java.util.Arrays;

public class Numbers {
    private int[] numbers;

    public Numbers(int[] numbers) {
        this.numbers = numbers;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return Arrays.toString(numbers);
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
            return Arrays.equals(this.numbers, nums);
        }
        Numbers nums = (Numbers) o;
        return Arrays.equals(this.numbers, nums.getNumbers());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Arrays.hashCode(this.numbers);
        return hash;
    }
}

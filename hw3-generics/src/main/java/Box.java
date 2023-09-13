import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {
    private final List<T> fruits = new ArrayList<>();

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public double weight() {
        double totalWeight = 0.0;
        for (T fruit : fruits) {
            totalWeight += fruit.getWeight();
        }
        return totalWeight;
    }

    public boolean compare(Box<?> anotherBox) {
        return Math.abs(this.weight() - anotherBox.weight()) < 0.0001;
    }

    public void transferFruitsToAnotherBox(Box<T> anotherBox) {
        if (this == anotherBox) {
            return;
        }

        if (this.fruits.isEmpty()) {
            return;
        }

        anotherBox.fruits.addAll(this.fruits);
        this.fruits.clear();
    }


}

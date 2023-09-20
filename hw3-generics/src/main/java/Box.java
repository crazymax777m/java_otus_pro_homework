import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {
    private final List<T> fruits = new ArrayList<>();


    public void addFruit(T fruit) {
        if (fruit != null) {
            fruits.add(fruit);
        } else {
            System.out.println("Нельзя добавить null в коробку.");
        }
    }

    public double weight() {
        double totalWeight = 0.0;
        for (T fruit : fruits) {
            totalWeight += fruit.getWeight();
        }
        return totalWeight;
    }

    public boolean compare(Box<?> anotherBox) {
        if (anotherBox == null) {
            System.out.println("Ошибка: Сравниваемая коробка не может быть null.");
            return false;
        }

        return Math.abs(this.weight() - anotherBox.weight()) < 0.0001;
    }

    public void transferFruitsToAnotherBox(Box<? super T> anotherBox) {
        if (anotherBox == null) {
            return;
        }

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

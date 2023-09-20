public class Main {
    public static void main(String[] args) {
        Box<Apple> appleBox1 = new Box<>();
        Box<Apple> appleBox2 = new Box<>();
        Box<Orange> orangeBox = new Box<>();
        Box<Fruit> fruitBox1 = new Box<>();
        Box<Fruit> fruitBox2 = new Box<>();

        appleBox1.addFruit(null);

        appleBox1.addFruit(new Apple(0.2));
        appleBox1.addFruit(new Apple(0.3));

        appleBox1.transferFruitsToAnotherBox(fruitBox1);

        appleBox2.addFruit(new Apple(0.5));

        orangeBox.addFruit(new Orange(0.4));
        orangeBox.addFruit(new Orange(0.2));
        orangeBox.addFruit(new Orange(0.6));

        fruitBox1.addFruit(new Apple(1.0));
        fruitBox1.addFruit(new Orange(2.0));

        fruitBox2.addFruit(new Apple(3.0));
        fruitBox2.addFruit(new Orange(4.0));

        System.out.println("Вес appleBox1: " + appleBox1.weight());
        System.out.println("Вес appleBox2: " + appleBox2.weight());
        System.out.println("Вес orangeBox: " + orangeBox.weight());

        System.out.println("Сравнение масс appleBox1 и appleBox2: " + appleBox1.compare(appleBox2));
        System.out.println("Сравнение масс appleBox1 и orangeBox: " + appleBox1.compare(orangeBox));

        appleBox1.transferFruitsToAnotherBox(appleBox2);
        System.out.println("После пересыпки фруктов из appleBox1 в appleBox2:");
        System.out.println("Вес appleBox1: " + appleBox1.weight());
        System.out.println("Вес appleBox2: " + appleBox2.weight());

        System.out.println("Вес fruitBox1: " + fruitBox1.weight());
        System.out.println("Вес fruitBox2: " + fruitBox2.weight());

        fruitBox1.transferFruitsToAnotherBox(fruitBox2);
        System.out.println("После пересыпки фруктов из fruitBox1 в fruitBox2:");
        System.out.println("Вес fruitBox1: " + fruitBox1.weight());
        System.out.println("Вес fruitBox2: " + fruitBox2.weight());
    }
}

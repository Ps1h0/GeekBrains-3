import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {
    private final List<T> fruits = new ArrayList<>();

    //g
    public void putFruit(T fruit){
        fruits.add(fruit);
    }
    //d
    public double getWeight(){
        if (fruits.size() > 0){
            return fruits.size() * fruits.get(0).getWeight();
        }
        return 0;
    }
    //e
    public boolean compare(Box<?> another){
        return Math.abs(this.getWeight() - another.getWeight()) < 0.0001;
    }
    //f
    public void shiftFruits(Box<T> b){
        b.fruits.addAll(fruits);
        fruits.clear();
    }
}

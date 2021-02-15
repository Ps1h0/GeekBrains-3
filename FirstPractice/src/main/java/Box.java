import java.util.ArrayList;

public class Box<T extends Fruit> {
    private T obj;
    public ArrayList<Fruit> fruits = new ArrayList<>();

    //g
    public void putFruit(Fruit fruit){
        fruits.add(fruit);
    }
    //d
    public double getWeight(){
        double weight = 0;
        if (fruits.isEmpty())
            return 0;
        if (fruits.get(0) instanceof Apple){
            weight = fruits.size();
        } else{
            weight = fruits.size() * 1.5;
        }
        return weight;
    }
    //e
    public boolean compare(Box<T> another){
        return Math.abs(this.getWeight() - another.getWeight()) < 0.0001;
    }
    //f
    public void shiftFruits(Box<T> b){
        for (int i = 0; i < fruits.size(); i++) {
            b.putFruit(fruits.get(i));
        }
        fruits.clear();
    }
}

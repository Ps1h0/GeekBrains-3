import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Array<Integer> nums = new Array<>(1, 2, 3, 4, 5);
        Array<Double> nums1 = new Array<>(2.5, 3.5, 4.5);
        Array<String> str = new Array<>("A", "B", "C", "D", "E");

        nums.swap(0, 1);
        nums1.swap(0, 1);
        str.swap(0, 1);

        ArrayList<Integer> numsArr = nums.toArrayList();
        ArrayList<Double> nums1Arr = nums1.toArrayList();
        ArrayList<String> strArr = str.toArrayList();

        Box<Apple> boxA = new Box<>();
        Box<Orange> boxB = new Box<>();
        Apple a = new Apple();
        Apple b = new Apple();
        Apple c = new Apple();
        Orange o = new Orange();
        Orange p = new Orange();

        boxA.putFruit(a);
        boxA.putFruit(b);
        boxB.putFruit(o);
        boxB.putFruit(p);

        double weightA = boxA.getWeight();
        double weightB = boxB.getWeight();
        System.out.println(weightA + " " + weightB);

        System.out.println(boxA.compare(boxB));
        boxA.putFruit(c);
        weightA = boxA.getWeight();
        System.out.println(weightA + " " + weightB);
        System.out.println(boxA.compare(boxB));

        Box<Apple> boxC = new Box<>();
        boxA.shiftFruits(boxC);
        Double weightC = boxC.getWeight();
        System.out.println(weightC);
        System.out.println(boxA.getWeight());
    }
}

import java.util.ArrayList;
import java.util.Arrays;

public class Array<T> {

    private T[] obj;

    @SafeVarargs
    public Array(T... obj) {
        this.obj = obj;
    }

    //1 задание
    public void swap(int a, int b){
        T temp = obj[a];
        obj[a] = obj[b];
        obj[b] = temp;
    }

    //2 задание
    public ArrayList<T> toArrayList(){
        return new ArrayList<>(Arrays.asList(obj));
    }

}

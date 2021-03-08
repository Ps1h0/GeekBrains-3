import java.util.Arrays;

public class Main {

    public int[] getNewArray(int[] arr){
        int[] newArray;
        for (int i = arr.length - 1; i > 0; i--){
            if (arr[i] == 4){
                return Arrays.copyOfRange(arr, i + 1, arr.length);
            }
        }
        throw new RuntimeException();
    }

    public boolean arrayOfOneAndFour(int[] arr){
        boolean checkOne = false;
        boolean checkFour = false;
        for (int i : arr) {
            if (i != 1 && i != 4)
                return false;
            if (i == 1)
                checkOne = true;
            if (i == 4)
                checkFour = true;
        }
        return checkOne && checkFour;
    }
}

import org.junit.Assert;
import org.junit.Test;

public class MainTest {

    private final Main main = new Main();


    @Test
    public void testFirst1(){
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        int[] exp = {5, 6, 7};
        int[] res = main.getNewArray(arr);
        Assert.assertArrayEquals(exp, res);
    }

    @Test
    public void testFirst2(){
        int[] arr = {1, 2, 3, 4};
        int[] exp = {};
        int[] res = main.getNewArray(arr);
        Assert.assertArrayEquals(exp, res);
    }

    @Test(expected = RuntimeException.class)
    public void testFirst3(){
        int[] arr = {1, 2, 3};
        int[] exp = {};
        int[] res = main.getNewArray(arr);
        Assert.assertArrayEquals(exp, res);
    }

    @Test
    public void testSecond1(){
        int[] arr = {1, 2};
        boolean res = main.arrayOfOneAndFour(arr);
        Assert.assertFalse(res);
    }

    @Test
    public void testSecond2(){
        int[] arr = {1, 4};
        boolean res = main.arrayOfOneAndFour(arr);
        Assert.assertTrue(res);
    }

    @Test
    public void testSecond3(){
        int[] arr = {1, 1};
        boolean res = main.arrayOfOneAndFour(arr);
        Assert.assertFalse(res);
    }

    @Test
    public void testSecond4(){
        int[] arr = {4, 4};
        boolean res = main.arrayOfOneAndFour(arr);
        Assert.assertFalse(res);
    }

}

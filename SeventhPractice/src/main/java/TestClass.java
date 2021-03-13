import java.lang.reflect.Method;

public class TestClass {

    @BeforeSuite
    public static void test1(){
        System.out.println("метод с аннтоацией BeforeSuite");
    }

    @Test(priority = 7)
    public static void test2(){
        System.out.println("метод с приоритетом 7");
    }

    @Test(priority = 10)
    public static void test3(){
        System.out.println("метод с приоритетом 10");
    }

    @Test(priority = 4)
    public static void test4(){
        System.out.println("метод с приоритетом 4");
    }

    @Test(priority = 9)
    public static void test5(){
        System.out.println("метод с приоритетом 9");
    }

    @Test
    public static void test6(){
        System.out.println("метод с приоритетом по умолчанию");
    }

    @AfterSuite
    public static void test7(){
        System.out.println("метод с аннотацией AfterSuite");
    }
}

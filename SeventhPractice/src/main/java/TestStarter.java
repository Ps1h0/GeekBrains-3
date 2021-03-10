import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestStarter {

    public static void start(Class test) throws InvocationTargetException, IllegalAccessException {
        List<Method> methods = new ArrayList<>();
        Method[] declaredMethods = test.getDeclaredMethods();

        for(Method dec : declaredMethods){
            if (dec.isAnnotationPresent(Test.class)){
                methods.add(dec);
            }
        }

        methods.sort(Comparator.comparingInt((Method m) -> m.getAnnotation(Test.class).priority()).reversed());

        for (Method dec : declaredMethods){
            if(dec.isAnnotationPresent(BeforeSuite.class)){
                if (methods.size() > 0 && methods.get(0).isAnnotationPresent(BeforeSuite.class)){
                    throw new RuntimeException("2 метода BeforeSuite");
                }
                methods.add(0, dec);
            }

            if(dec.isAnnotationPresent(AfterSuite.class)){
                if (methods.size() > 0 && methods.get(methods.size() - 1).isAnnotationPresent(AfterSuite.class)){
                    throw new RuntimeException("2 метода AfterSuite");
                }
                methods.add(dec);
            }
        }

        for(Method m : methods)
            m.invoke(null);
    }
}

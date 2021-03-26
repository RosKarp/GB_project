import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MakeTests {        // класс выполняющий тесты

    public static void start (String name) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        start(Class.forName(name));
    }

    public static void start (Class test) throws InvocationTargetException, IllegalAccessException, InstantiationException {

        int countBefore = 0;
        int countAfter = 0;
        Method[] methods = test.getMethods();
        for (Method o : methods) {
            if (o.getAnnotation(BeforeSuite.class) != null) {
                countBefore++;
            }
            if(o.getAnnotation(AfterSuite.class) != null){
                countAfter++;
            }
        }
        try {
            if(countBefore != 1 || countAfter != 1) throw new RuntimeException();
        } catch (RuntimeException e) {
            System.out.println("Block @BeforeSuite or @AfterSuite are not alone.");
            e.printStackTrace();
        }

        for (Method o : methods) {
                if (o.getAnnotation(BeforeSuite.class) != null) {
                    o.invoke(test.newInstance());
                }
        }
        for (int i = 10; i > 0 ; i--) { // выполнение тестов по приоритету в аннотациях
            for (Method o : methods) {
                if (o.getAnnotation(MarkingAnnotation.class) != null) {
                    if(o.getAnnotation(MarkingAnnotation.class).value() == i) {
                            o.invoke(test.newInstance());       // выполнение теста, при провале - исключения
                            System.out.println("Method " + o.getName() + " - OK");
                    }
                }
            }
        }
        for (Method o : methods) {
            if (o.getAnnotation(AfterSuite.class) != null) {
                o.invoke(test.newInstance());
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        try {
            MakeTests.start(Task2test.class);
            MakeTests.start(Task2test.class.getName());
            MakeTests.start(Task3test.class);
            MakeTests.start(Task3test.class.getName());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
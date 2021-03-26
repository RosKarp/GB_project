import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.GeekBrains.Rostislav.Java3Group3.Lesson6.Task2;
import java.util.Arrays;

public class Task2test {

    @BeforeSuite
    public void BeforeSuite() {
        System.out.println("Start Task2test.");
    }

    @MarkingAnnotation (value = 1)
    @Test
    public void test1Task2() {
        Assertions.assertArrayEquals(new int[]{5, 7}, Task2.afterLast4(new int[]{0, 8, 3, 4, 5, 7}));
    }

    @MarkingAnnotation (value = 2)
    @Test
    public void test2Task2() {
        Assertions.assertArrayEquals(new int[]{1, 7}, Task2.afterLast4(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}));
    }

    @MarkingAnnotation (value = 3)
    @Test
    public void test3Task2() {
        Assertions.assertArrayEquals(new int[]{2, 3, 9, 1, 7}, Task2.afterLast4(new int[]{4, 2, 4, 4, 2, 3, 9, 1, 7}));
    }

    @MarkingAnnotation (value = 4)
    @Test
    public void testExceptionTask2() { // как-то не очень, тестируется не метод целиком, а только его
        // модифицированная реализация
        Assertions.assertThrows (RuntimeException.class, () -> {
            // тело метода afterLast4 с поправками
            int[] out;
            int[] in = new int[]{7, 3, 5, 6, 9, 7, 8, 2, 1, 10}; // массив без четверок на вход
            for (int i = in.length - 1; i > 0; i--) { // поиск 4 с конца
                if (in[i] == 4) {
                    out = Arrays.copyOfRange(in, i + 1, in.length);
                    //return out;  // требование вернуть void
                }
            }
            throw new RuntimeException();
        });
    }

    @MarkingAnnotation (value = 6)
    @Test
    public void testEmptyArrayTask2() { // как-то не очень, тестируется не метод целиком, а только его
        // модифицированная реализация
        Assertions.assertThrows (RuntimeException.class, () -> {
            // тело метода afterLast4 с поправками
            int[] out;
            int[] in = new int[]{};                                         // пустой массив на вход
            for (int i = in.length - 1; i > 0; i--) { // поиск 4 с конца
                if (in[i] == 4) {
                    out = Arrays.copyOfRange(in, i + 1, in.length);
                    //return out;  // требование вернуть void
                }
            }
            throw new RuntimeException();
        });
    }

    @AfterSuite
    public void AfterSuite() {
        System.out.println("Finish Task2test.");
    }
}
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.GeekBrains.Rostislav.Java3Group3.Lesson6.Task3;

public class Task3test {

    @Test
    public void test1Task3() {
        Assertions.assertTrue(Task3.isOneAndFour(new int[]{1, 4, 1, 1, 4, 4}));
    }

    @Test
    public void test2Task3() {
        Assertions.assertFalse(Task3.isOneAndFour(new int[]{1, 1, 1, 1, 1, 1}));
    }

    @Test
    public void test3Task3() {
        Assertions.assertFalse(Task3.isOneAndFour(new int[]{4, 4, 4, 4, 4, 4}));
    }

    @Test
    public void test4Task3() {
        Assertions.assertFalse(Task3.isOneAndFour(new int[]{1, 4, 1, 1, 4, 3}));
    }

    @Test
    public void test5Task3() {
        Assertions.assertFalse(Task3.isOneAndFour(new int[]{}));
    }
}
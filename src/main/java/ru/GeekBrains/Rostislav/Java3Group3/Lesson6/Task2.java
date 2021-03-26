package ru.GeekBrains.Rostislav.Java3Group3.Lesson6;

import java.util.Arrays;

public class Task2 {

    public static int[] afterLast4 (int[] in) {
        int[] out;

            for (int i = in.length - 1; i > 0; i--) {   // поиск 4 с конца
                if (in[i] == 4) {
                    out = Arrays.copyOfRange(in, i + 1, in.length);
                    return out;
                }
            }
        throw new RuntimeException();
    }
}
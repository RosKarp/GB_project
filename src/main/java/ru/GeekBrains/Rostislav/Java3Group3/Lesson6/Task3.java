package ru.GeekBrains.Rostislav.Java3Group3.Lesson6;

public class Task3 {

    public static boolean isOneAndFour(int[] in) {
        int count1 = 0;
        int count4 = 0;

        for (int j : in) {
            if (j != 1 && j != 4) return false;
            if (j == 1) count1++;
            if (j == 4) count4++;
        }
        return count1 != 0 && count4 != 0;
    }
}
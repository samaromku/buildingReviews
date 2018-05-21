package ru.andrey.savchenko.buildingreviews;

import org.junit.Test;

import java.util.Random;

/**
 * Created by Andrey on 21.05.2018.
 */

public class SearchTest {
    @Test
    public void searchTest() {
        long start = System.currentTimeMillis();
        int[]randomArray = generateRandomArray(1000);

        System.out.println(isElementInArray(randomArray, 158));

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private boolean isElementInArray(int[] array, int element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i]==(element)) {
                return true;
            }
        }
        return false;
    }

    private int[] generateRandomArray(int n) {
        int[] list = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            list[i]=(random.nextInt(1000));
        }
        return list;
    }
}

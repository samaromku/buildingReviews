package ru.andrey.savchenko.buildingreviews;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Andrey on 21.05.2018.
 */

public class SearchTest {
    @Test
    public void searchTest() {
        int[] randomArray = generateRandomArray(1000);
        Arrays.sort(randomArray);

        for (int i = 0; i < randomArray.length; i++) {
            System.out.print(randomArray[i] + ", ");
        }
        System.out.println();
        long start = System.currentTimeMillis();

        System.out.println(isElementInArray(randomArray, 158));
        System.out.println(binarySearch(randomArray, 158, 0, randomArray.length - 1));
        System.out.println(jumpSearch(randomArray, 158));
        System.out.println(interpolationSearch(randomArray, 158));
        System.out.println(exponentialSearch(randomArray, 158));

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private boolean isElementInArray(int[] array, int element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == (element)) {
                return true;
            }
        }
        return false;
    }

    private int exponentialSearch(int[]array, int element){
        if(array[0]==element){
            return 0;
        }
        int i = 1;
        int size = array.length;
        while (i < size && array[i] <= element){
            i = i*2;
        }
        return binarySearch(array, element, i/2, Math.min(i, size));
    }

    private int interpolationSearch(int[] array, int element) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high && element >= array[low] && element <= array[high]) {
            int position = low + (((high - low) / (array[high] - array[low])) * (element - array[low]));
            if (array[position] == element) {
                return position;
            }
            if (array[position] < element) {
                low = position + 1;
            }else {
                high = position-1;
            }
        }
        return -1;
    }

    private int jumpSearch(int[] arr, int item) {

        int size = arr.length;

        int step = (int) Math.floor(Math.sqrt(size));

        int prev = 0;
        while (arr[Math.min(step, size) - 1] < item) {
            prev = step;
            step += (int) Math.floor(Math.sqrt(size));
            if (prev >= size) {
                return -1;
            }
        }

        while (arr[prev] < item) {
            prev++;

            if (prev == Math.min(step, size)) {
                return -1;
            }
        }

        if (arr[prev] == item) {
            return prev;
        }
        return -1;
    }

    private int binarySearch(int[] array, int element, int left, int right) {
        if (right >= left) {
            int middle = (right + left) / 2;
            if (array[middle] == element) {
                return middle;
            }
            if (element < array[middle]) {
                return binarySearch(array, element, left, middle - 1);
            }
            return binarySearch(array, element, middle + 1, right);
        }
        return -1;
    }

    private int[] generateRandomArray(int n) {
        int[] list = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            list[i] = (random.nextInt(1000));
        }
        return list;
    }
}

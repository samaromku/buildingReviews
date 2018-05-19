package ru.andrey.savchenko.buildingreviews;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.annotations.Nullable;

/**
 * Created by Andrey on 19.05.2018.
 */

public class CollectionsTest {
    @Test
    public void testArrayList() {
        MyArrayLust myArrayLust = new MyArrayLust<Integer>();
        List<Integer> randomArray = generateRandomArray(10_000_000);
        List<Integer> defaultArrayList = new ArrayList<>();

        long startDef = System.currentTimeMillis();
//        for (Integer i : randomArray) {
//            defaultArrayList.add(i);
//        }
        defaultArrayList.addAll(randomArray);
//        System.out.println(defaultArrayList.indexOf(123));
        long endDef = System.currentTimeMillis();
        System.out.println("default collection " + (endDef - startDef));

        long startMy = System.currentTimeMillis();
//        for (Integer i : randomArray) {
//            myArrayLust.add(i);
//        }
        myArrayLust.addAll(randomArray);
//        System.out.println(myArrayLust.indexOf(123));
        long endMy = System.currentTimeMillis();
        System.out.println("my collection " + (endMy - startMy));
    }

    private List<Integer> generateRandomArray(int n) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            list.add(random.nextInt(1000));
        }
        return list;
    }

    class MyArrayLust<T> {

        private Object[] objects;
        private int size;

        public MyArrayLust() {
            objects = new Object[10];
        }

        public void add(Object object) {
            if (size >= objects.length) {
                grow();
            }
            objects[size] = object;
            size++;
        }

        public void addAll(List<Object>addObjects){
            objects = Arrays.copyOf(objects, objects.length + addObjects.size());
            for (int i = 0; i < addObjects.size(); i++) {
                add(addObjects.get(i));
            }
        }

        public int size(){
            return size;
        }

        public int indexOf(@Nullable Object object){
            if(object!=null) {
                for (int i = 0; i < objects.length; i++) {
                    if (objects[i].equals(object)) {
                        return i;
                    }
                }
            }
            return -1;
        }

        private void grow() {
            int oldCapacity = objects.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            objects = Arrays.copyOf(objects, newCapacity);
        }
    }
}

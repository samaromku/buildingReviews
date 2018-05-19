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
        List<Integer>dataArray = new ArrayList<>();
        dataArray.add(1);
        dataArray.add(5);
        dataArray.add(6);
        dataArray.add(88);
        dataArray.add(10);

        List<Integer> defaultArrayList = new ArrayList<>();

        long startDef = System.currentTimeMillis();
//        for (Integer i : randomArray) {
//            defaultArrayList.add(i);
//        }
        defaultArrayList.addAll(randomArray);
        for (int i = 0; i < 100; i++) {
            defaultArrayList.remove(1);
        }
//        for (int i = 0; i < defaultArrayList.size(); i++) {
//            System.out.print(defaultArrayList.get(i) + ", ");
//        }
        System.out.println();
        long endDef = System.currentTimeMillis();
        System.out.println("default collection " + (endDef - startDef));

        long startMy = System.currentTimeMillis();
//        for (Integer i : randomArray) {
//            myArrayLust.add(i);
//        }
        myArrayLust.addAll(randomArray);
        for (int i = 0; i < 100; i++) {
            myArrayLust.remove(1);
        }
//        for (int i = 0; i < myArrayLust.size(); i++) {
//            System.out.print(myArrayLust.get(i) + ", ");
//        }
        System.out.println();
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

        //удалить элемент под индексом, сдвинуть все элементы справа от него на один влево, уменьшить size
        public void remove(int index){
            for (int i = index; i < size-1; i++) {
                objects[i] = objects[i+1];
            }
            objects[size]=null;
            size--;
        }

        public Object get(int index){
            return objects[index];
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

        public boolean contains(@Nullable Object object){
            if(object!=null){
                for (int i = 0; i < objects.length; i++) {
                    if (objects[i].equals(object)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private void grow() {
            int oldCapacity = objects.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            objects = Arrays.copyOf(objects, newCapacity);
        }
    }
}

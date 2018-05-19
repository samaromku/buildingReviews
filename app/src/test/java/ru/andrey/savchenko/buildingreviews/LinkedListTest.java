package ru.andrey.savchenko.buildingreviews;

import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by Andrey on 19.05.2018.
 */

public class LinkedListTest {
    @Test
    public void testLinkedList(){
        LinkedList<Integer>linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(39);
        linkedList.add(-1);
        linkedList.add(12);
        linkedList.add(120);
        for(Integer i:linkedList){
            System.out.println(i);
        }

    }
}

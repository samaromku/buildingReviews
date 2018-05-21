package ru.andrey.savchenko.buildingreviews;

import org.junit.Test;

import java.util.HashSet;

/**
 * Created by Andrey on 21.05.2018.
 */

public class HashSetTest {
    @Test
    public void testSet() {
        HashSet<String> set = new HashSet<String>();
        set.add("test");
        set.add("test2");
        set.add("test2");
        set.add("test");
        for (String s : set) {
            System.out.println(s);
        }
    }
}

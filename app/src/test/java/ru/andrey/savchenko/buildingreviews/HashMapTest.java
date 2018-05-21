package ru.andrey.savchenko.buildingreviews;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Andrey on 21.05.2018.
 */

public class HashMapTest {
    @Test
    public void testHashMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "test1");
        map.put(2, "test2");
        map.put(5, "test3");
        map.put(2, "test4");
        map.put(4, "test5");

        Map<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(10, "test1");
        treeMap.put(2, "test2");
        treeMap.put(5, "test3");
        treeMap.put(2, "test4");
        treeMap.put(4, "test5");


//        System.out.println(256>>>16);
//        System.out.println("test" + map.get(1));
//        for (Map.Entry<Integer, String> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }

        MyHashMap<Integer, String> myHashMap = new MyHashMap<>();
        myHashMap.put(1, "test");
        myHashMap.put(2, "test2");
        myHashMap.put(3, "test3");
        myHashMap.put(4, "test4");
        myHashMap.put(5, "test5");
        myHashMap.put(3, "test333");
        myHashMap.put(4, "test444");
        System.out.println(myHashMap.get(1));
        System.out.println(myHashMap.get(2));
        System.out.println(myHashMap.get(3));
        System.out.println(myHashMap.get(4));
        System.out.println(myHashMap.get(5));
    }


    class MyHashMap<K, V> {
        private Entry<K, V>[] table;
        private int size;

        public MyHashMap() {
            table = new Entry[10];
        }

        public V get(K key) {
            Entry<K, V> entry = getNode(hash(key), key);
            if (entry != null) {
                return entry.value;
            }
            return null;
        }

        final Entry<K, V> getNode(int hash, Object key) {
            Entry<K, V>[] tab = table;
            Entry<K, V> e;
            int n = tab.length;
            Entry<K, V> first = tab[0];
            if (tab != null && n > 0 && first != null) {
                K k = first.key;
                if (first.hash == hash && (k == key || (key != null && key.equals(k))))
                    return first;
                if ((e = first.next) != null) {
                    do {
                        K kk = e.key;
                        if (e.hash == hash && (kk == key || (key != null && key.equals(k))))
                            return e;
                    } while ((e = e.next) != null);
                }
            }
            return null;
        }

        public void put(K key, V value) {
            Entry<K, V> entry = new Entry<>(key, value, null, hash(key));
            if (size >= table.length) {
                grow();
            }
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    if (hash(table[i].key) == hash(entry.key)) {
                        if (table[i].key == entry.key) {
                            entry.next = table[i].next;
                            table[i - 1].next = entry;
                            break;
                        }
                    }
                }
                table[size - 1].next = entry;
            }
            table[size] = entry;
            size++;
        }

        private void grow() {
            int oldCapacity = table.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            table = Arrays.copyOf(table, newCapacity);
        }

        class Entry<K, V> {
            private K key;
            private V value;
            private Entry<K, V> next;
            private int hash;

            public Entry(K key, V value, Entry<K, V> next, int hash) {
                this.key = key;
                this.value = value;
                this.next = next;
                this.hash = hash;
            }
        }
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}

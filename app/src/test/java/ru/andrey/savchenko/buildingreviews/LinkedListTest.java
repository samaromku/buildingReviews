package ru.andrey.savchenko.buildingreviews;

import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by Andrey on 19.05.2018.
 */

public class LinkedListTest {
    @Test
    public void testLinkedList() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(39);
        linkedList.add(-1);
        linkedList.add(12);
        linkedList.add(120);
//        for(Integer i:linkedList){
//            System.out.println(i);
//        }

        MyLinkedList myLinkedList = new MyLinkedList<Integer>();
        myLinkedList.add(1);
        myLinkedList.add(2);
//        myLinkedList.add(3);
        for (int i = 0; i < myLinkedList.size(); i++) {
            System.out.println(myLinkedList.get(i));
        }
    }

    class MyLinkedList<T> {
        private Node<T> first;
        private Node<T> last;
        private int size;

        //если это превый элемент, то заполняем first,
        // если же нет, заполняем last, создаем цепочку промежуточных элементов, от first до last
        public void add(T element) {
            if (size == 0) {
                first = new Node<T>(element);
                last = new Node<T>(element);
            } else if (size == 1) {
                last = new Node<T>(element, first, null);
                first.next = last;
            }
            size++;
        }

        public T get(int index) {
            Node<T> current = first;
            for (int i = 0; i <= index; i++) {
                if (i == 0) {
                    current = first;
                } else if(i==1){
                    current = last;
                }
            }
            return current.element;
        }

        public int size() {
            return size;
        }

        private class Node<E> {
            private E element;
            private Node<E> previous;
            private Node<E> next;

            public Node(E element, Node<E> previous, Node<E> next) {
                this.element = element;
                this.previous = previous;
                this.next = next;
            }

            public Node(E element) {
                this.element = element;
            }
        }
    }
}

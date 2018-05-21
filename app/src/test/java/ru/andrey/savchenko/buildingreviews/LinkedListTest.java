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
        System.out.println("test gasdkfhjshjap oaspofhsadp oufhpasdhgsa [gsadygposidfhpjsadfoiuwyaepou sd;fhyasd ypfasd poufyasdpofihjasdjfhy wpoueaf hasdf pouhasdf psd".hashCode());
        System.out.println("test1".hashCode());

        MyLinkedList myLinkedList = new MyLinkedList<Integer>();
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(11);
        myLinkedList.add(25);
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(333);
        myLinkedList.add(4);
        myLinkedList.add(1);

        myLinkedList.remove(2);
        myLinkedList.remove(5);
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
        //при добавлении нового элемента:
        //создаем новый элемент со ссылкой на предыдущий, предыдущему указываем правой ссылкой тот, который создаем
        public void add(T element) {
            if (size == 0) {
                first = new Node<T>(element);
                last = new Node<T>(element);
            } else if (size == 1) {
                last = new Node<T>(element, first, null);
                first.next = last;
            } else {
                Node current = new Node<T>(element, last, null);
                last.next = current;
                last = current;
            }
            size++;
        }

        //найти элемент по индексу, сделать так, чтобы предыдуший ссылался на следующий, а следующий на предыдущий
        public void remove(int index){
            Node current = getNode(index);
            Node previous = current.previous;
            Node next = current.next;
            previous.next = next;
            next.previous = previous;
            size--;
        }

        public T get(int index) {
            return getNode(index).element;
        }

        private Node<T>getNode(int index){
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
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

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Node<?> node = (Node<?>) o;

                if (element != null ? !element.equals(node.element) : node.element != null)
                    return false;
                if (previous != null ? !previous.equals(node.previous) : node.previous != null)
                    return false;
                return next != null ? next.equals(node.next) : node.next == null;
            }

            @Override
            public int hashCode() {
                int result = element != null ? element.hashCode() : 0;
                result = 31 * result + (previous != null ? previous.hashCode() : 0);
                result = 31 * result + (next != null ? next.hashCode() : 0);
                return result;
            }

            public Node(E element) {
                this.element = element;
            }
        }
    }
}

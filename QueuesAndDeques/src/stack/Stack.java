package stack;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by makaronodentro on 30/11/15.
 */
public class Stack<Item> implements Iterable<Item>{

    // Helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    private Node<Item> first; // top of the stack
    private int N;            // size of the stack

    // Constructor - Initializes empty stack
    public Stack(){
        first = null;
        N = 0;
    }

    public boolean isEmpty() {
        return first == null; // Returns true if stack is empty
    }

    public int size() {
        return N;
    }

    // Adds item on top of the stack
    public void push(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    // Removes and returns the top of the stack
    public Item pop() {
        if(isEmpty()) throw new NoSuchElementException("Stack Underflow");
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }

    public Item peek() {
        if(isEmpty()) throw new NoSuchElementException("Stack Underflow");
        return first.item;
    }

    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    /**
     * Unit tests the <tt>Stack</tt> data type.
     */
    public static void main(String[] args) {

        // Input stream
        Scanner reader = new Scanner(System.in);

        Stack<String> s = new Stack<String>();
        while (reader.next() != "1") {
            String item = reader.next();
            if (!item.equals("-")) s.push(item);
            else if (!s.isEmpty()) System.out.print(s.pop() + " ");
        }
        System.out.println("(" + s.size() + " left on stack)");
    }


}

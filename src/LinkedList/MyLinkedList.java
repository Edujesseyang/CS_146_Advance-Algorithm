package LinkedList;

public class MyLinkedList {
    private class Node {
        private Integer val;
        private Node next;
        private Node prev;

        private Node(int val) {
            this.val = val;
        }
    }

    private class MyIterator {
        private Node current;

        private MyIterator() {
            this.current = sentinel;
        }

        private Node next() {
            current = current.next;
            return current;
        }

        private Node prev() {
            current = current.prev;
            return current;
        }

        private boolean hasNext() {
            return current.next != sentinel;
        }

        private boolean hasPrev() {
            return current.prev != sentinel;
        }
    }

    private int size;
    private final Node sentinel;

    public MyLinkedList() {
        sentinel = new Node(-1);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    public int getSize() {
        return this.size;
    }

    public void addLast(int val) {
        Node newNode = new Node(val);
        newNode.next = sentinel;
        newNode.prev = sentinel.prev;
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    public void addFirst(int val) {
        Node newNode = new Node(val);
        newNode.prev = sentinel;
        newNode.next = sentinel.next;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    public Integer pollFirst() {
        if (this.isEmpty()) {
            return null;
        }

        Node oldFirst = sentinel.next;
        Node newFirst = oldFirst.next;

        sentinel.next = newFirst;
        newFirst.prev = sentinel;

        oldFirst.next = oldFirst.prev = null;
        return oldFirst.val;
    }

    public Integer pollLast() {
        if (this.isEmpty()) {
            return null;
        }

        Node oldLast = sentinel.prev;
        Node newLast = oldLast.prev;

        sentinel.prev = newLast;
        newLast.next = sentinel;

        oldLast.next = oldLast.prev = null;
        return oldLast.val;
    }

    public Integer get(int index) {
        return 0;
    }

    public void add(int index, int val) {
        // special cases
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound: (0 - " + size + ")");
        }
        if (index == 0) {
            this.addFirst(val);
            return;
        }
        if (index == size) {
            this.addLast(val);
            return;
        }

        // iterate to correct position
        MyIterator iter = new MyIterator();
        if (index <= size / 2) {
            for (int i = 0; i < index; i++) {
                iter.next();
            }
        } else {
            for (int i = 0; i < size - index + 1; i++) {
                iter.prev();
            }
        }

        // add connecting
        Node insertPrev = iter.current;
        Node insertNext = insertPrev.next;
        Node newNode = new Node(val);
        newNode.prev = insertPrev;
        newNode.next = insertNext;
        insertPrev.next = newNode;
        insertNext.prev = newNode;
        size++;
    }


    public void printList() {
        MyIterator iter = new MyIterator();
        while (iter.hasNext()) {
            System.out.print(iter.next().val + ", ");
        }
        System.out.println();
    }
}

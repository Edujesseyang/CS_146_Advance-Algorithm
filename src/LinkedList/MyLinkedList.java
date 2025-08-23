package LinkedList;

public class MyLinkedList {
    private static class Node {
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
        size--;
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
        size--;
        return oldLast.val;
    }

    public Integer peekFirst() {
        return sentinel.next.val;
    }

    public Integer peekLast() {
        return sentinel.prev.val;
    }

    private Node nodeAt(int index) {
        MyIterator iter = new MyIterator();
        if (index <= size / 2) {
            for (int i = 0; i <= index; i++) {
                iter.next();
            }
        } else {
            for (int i = 0; i < size - index; i++) {
                iter.prev();
            }
        }
        return iter.current;
    }

    public Integer get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bound: (0 - " + (size - 1) + ")");
        }
        return this.nodeAt(index).val;
    }

    public void replace(int index, int val) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bound: (0 - " + (size - 1) + ")");
        }
        Node modifyNode = this.nodeAt(index);
        modifyNode.val = val;
    }

    public void add(int index, int val) {
        // special cases
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bound: (0 - " + size + ")");
        }

        if (index == 0) {
            this.addFirst(val);
            return;
        }
        if (index == size) {
            this.addLast(val);
            return;
        }

        // add connecting
        Node insertPrev = nodeAt(index).prev;
        Node insertNext = insertPrev.next;
        Node newNode = new Node(val);
        newNode.prev = insertPrev;
        newNode.next = insertNext;
        insertPrev.next = newNode;
        insertNext.prev = newNode;
        size++;
    }

    public Integer poll(int index) {
        // handling side cases
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bound: (0 - " + (size - 1) + ")");
        }

        if (index == 0) {
            return this.pollFirst();
        }
        if (index == size - 1) {
            return this.pollLast();
        }

        Node deletingNode = nodeAt(index);
        Node prevNode = deletingNode.prev;
        Node nextNode = deletingNode.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        deletingNode.prev = deletingNode.next = null;
        size--;
        return deletingNode.val;
    }

    public boolean contains(int val) {
        MyIterator iter = new MyIterator();
        while (iter.hasNext()) {
            if (iter.next().val == val) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        MyIterator iter = new MyIterator();
        while (iter.hasNext()) {
            sb.append(iter.next().val + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
        return sb.toString();
    }

    public void printList() {
        String s = this.toString();
        System.out.println(s);
    }
}

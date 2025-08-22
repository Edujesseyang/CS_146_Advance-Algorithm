package LinkedList;

public class MyLinkedList {
    private class Node {
        private int val;
        private Node next;
        private Node prev;

        private Node(int val) {
            this.val = val;
        }
    }

    private class Iterator {
        private Node node;

        private Iterator() {
            this.node = sentinel.next;
        }

        private void next() {
            node = node.next;
        }

        private boolean isNext() {
            return node != sentinel;
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

    public void addBack(int val) {
        Node newNode = new Node(val);
        newNode.next = sentinel;
        newNode.prev = sentinel.prev;
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    public void addFront(int val) {
        Node newNode = new Node(val);
        newNode.prev = sentinel;
        newNode.next = sentinel.next;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }


    public void printList() {
        Iterator iter = new Iterator();
        while (iter.isNext()) {
            System.out.println(iter.node.val + ", ");
        }
        iter.next();
    }
}

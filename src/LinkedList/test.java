package LinkedList;

public class test {
    public static void main(String[] args) {
        MyLinkedList l1 = new MyLinkedList();
        System.out.println("is list empty:" + l1.isEmpty());

        l1.addBack(1);
        l1.addBack(2);
        l1.addBack(3);
        l1.addBack(4);
        l1.printList();
        System.out.println("is list empty:" + l1.isEmpty());

        l1.addFront(1);
        l1.addFront(2);
        l1.addFront(3);
        l1.addFront(4);
        l1.printList();


    }
}
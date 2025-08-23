package LinkedList;

public class test {
    public static void main(String[] args) {
        // testing constructor
        System.out.println("*** Testing constructor ***");
        MyLinkedList l1 = new MyLinkedList();
        l1.printList();
        System.out.println("*** Passed ***\n");

        // testing add/print/isEmpty/getSize
        System.out.println("*** Testing add/print/isEmpty/getSize ***");
        System.out.println("is list empty:" + l1.isEmpty());
        l1.addLast(5);
        l1.addLast(6);
        l1.addLast(7);
        l1.addLast(8);
        l1.printList();
        System.out.println("is list empty:" + l1.isEmpty());
        System.out.println("Size is " + l1.getSize() + "\n");
        l1.addFirst(4);
        l1.addFirst(3);
        l1.addFirst(2);
        l1.addFirst(1);
        l1.printList();
        System.out.println("*** Passed ***\n");

        // testing pollFirst/pollLast/peekFirst/peekLast
        int f1 = l1.pollFirst();
        int b1 = l1.pollLast();
        System.out.println("after poll last and first: " + f1 + ", " + b1);
        l1.printList();
        System.out.println("The first element is " + l1.peekFirst());
        System.out.println("The last element is " + l1.peekLast());
        System.out.println("*** Passed ***\n");


        // testing add by index
        System.out.println("*** Testing add by index ***");
        System.out.println("Adding -1 to -1 : ");
        try {
            l1.add(-1, -1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Adding 20 to 20 : ");
        try {
            l1.add(-1, -1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Adding 1 to 0 : ");
        l1.add(0, 1);
        l1.printList();
        System.out.println("Adding 0 to 0 : ");
        l1.add(0, 0);
        l1.printList();

        System.out.println("Adding 8 to 8 : ");
        l1.add(8, 8);
        l1.printList();
        System.out.println("Adding 9 to 9 : ");
        l1.add(9, 9);
        l1.printList();

        System.out.println("Adding 11 to 1 : ");
        l1.add(1, 11);
        l1.printList();
        System.out.println("Adding 10 to 1 : ");
        l1.add(1, 10);
        l1.printList();

        System.out.println("Adding 88 to 8 : ");
        l1.add(8, 88);
        l1.printList();
        System.out.println("Adding 99 to 10 : ");
        l1.add(10, 99);
        l1.printList();
        System.out.println("Size is " + l1.getSize());
        System.out.println(" *** Passed ***\n");

        // testing poll by index
        System.out.println("*** Testing poll by index ***");
        System.out.println("Polling 1 to get : " + l1.poll(1));
        l1.printList();
        System.out.println("Polling 1 to get : " + l1.poll(1));
        l1.printList();
        System.out.println("Polling 6 to get : " + l1.poll(6));
        l1.printList();
        System.out.println("Polling 6 to get : " + l1.poll(7));
        l1.printList();
        System.out.println(" *** Passed ***\n");

        // testing get/replace by index
        System.out.println("*** Testing get/replace by index *** ");
        for (int i = 0; i < 8; i++) {
            System.out.println("Num at index(" + i + ") is " + l1.get(i));
        }
        l1.printList();
        for (int i = 0; i < 10; i++) {
            System.out.println("Replacing " + (l1.getSize() - i - 1) + " to index(" + i + ")");
            l1.replace(i, l1.getSize() - i - 1);
        }
        l1.printList();
        System.out.println(" *** Passed ***\n");

        // testing contains/toString
        System.out.println("The list is : " + l1);
        for (int i = 0; i < 20; i++) {
            System.out.println("It list contains " + i + " : " + l1.contains(i));
        }
        System.out.println(" *** Passed ***\n");

    }
}
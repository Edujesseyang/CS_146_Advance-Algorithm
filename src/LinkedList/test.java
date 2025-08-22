package LinkedList;

public class test {
    public static void main(String[] args) {
        // testing constructor
        MyLinkedList l1 = new MyLinkedList();


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

        System.out.println("*** pass ***\n");

        // testing add
        System.out.println("*** testing add ***");
        System.out.println("Adding -1 to -1 : ");
        try{
            l1.add(-1,-1);
        } catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Adding 20 to 20 : ");
        try{
            l1.add(-1,-1);
        } catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Adding 0 to 0 : ");
        l1.add(0, 0);
        System.out.println("Adding 9 to 9 : ");
        l1.add(9, 9);
        l1.printList();
        System.out.println("Size is " + l1.getSize());

        System.out.println("Adding 11 to 1 : ");
        l1.add(1, 11);
        System.out.println("Adding 88 to 8 : ");
        l1.add(8, 88);
        l1.printList();
        System.out.println("Size is " + l1.getSize());
        System.out.println(" *** pass ***\n");

        // testing pollFirst/pollLast
        int f1 = l1.pollFirst();
        int b1 = l1.pollLast();
        System.out.println("after poll last and first: " + f1 + ", " + b1);
        l1.printList();


    }
}
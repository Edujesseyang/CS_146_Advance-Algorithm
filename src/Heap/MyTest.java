package Heap;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MyTest {
    public static void main(String[] args) {
        // lists
        ArrayList<Integer> i1 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7));
        ArrayList<Integer> i2 = new ArrayList<>(List.of(1, 2, 7, 4, 5, 9, 10));
        ArrayList<Integer> i3 = new ArrayList<>(List.of(15, 26, 77, 48, 53, 95, 106));

        // test 1
        MyMaxHeap t1 = new MyMaxHeap(i1);
        System.out.println(t1);
        System.out.println("max = " + t1.getMaxVal());
        System.out.println("min = " + t1.getMinVal());

        // test 2
        MyMaxHeap t2 = new MyMaxHeap(i2);
        System.out.println(t2);
        System.out.println("max = " + t2.getMaxVal());
        System.out.println("min = " + t2.getMinVal());

        // test 3
        MyMaxHeap t3 = new MyMaxHeap(i1, i2);
        System.out.println(t3);
        System.out.println("max = " + t3.getMaxVal());
        System.out.println("min = " + t3.getMinVal());

        // test 4
        MyMaxHeap t4 = new MyMaxHeap(i1, i2, i3);
        System.out.println(t4);
        t4.insert(500);
        System.out.println(t4);
        t4.insert(700);
        System.out.println(t4);
        t4.insert(600);
        System.out.println(t4);
        System.out.println("max = " + t4.getMaxVal());
        System.out.println("min = " + t4.getMinVal());
        t4.insert(-100);
        t4.insert(10000);
        System.out.println(t4);
        System.out.println("max = " + t4.getMaxVal());
        System.out.println("min = " + t4.getMinVal());

        // test 5 empty heap
        MyMaxHeap t5 = new MyMaxHeap();
        System.out.println(t5 + "<- empty tree");
        System.out.println("size = " + t5.getSize());
        try {
            System.out.println("max = " + t5.getMinVal());
            System.out.println("min = " + t5.getMinVal());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("height = " + t5.getHeight());


        // test 6
        ArrayList<Integer> r1 = t4.copySorted();
        System.out.println("sorted: " + r1);

    }

}


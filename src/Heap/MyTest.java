package Heap;

import java.util.ArrayList;
import java.util.List;

public class MyTest {
    public static void main(String[] args) {
        // lists
        ArrayList<Integer> i1 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7));
        ArrayList<Integer> i2 = new ArrayList<>(List.of(1, 2, 7, 4, 5, 9, 10));
        ArrayList<Integer> i3 = new ArrayList<>(List.of(15, 26, 77, 48, 53, 95, 106));

        // test 1
        MyMaxHeap t1 = new MyMaxHeap(i1);
        System.out.println(t1);

        // test 2
        MyMaxHeap t2 = new MyMaxHeap(i2);
        System.out.println(t2);

        // test 3
        MyMaxHeap t3 = new MyMaxHeap(i1, i2);
        System.out.println(t3);

        // test 4
        MyMaxHeap t4 = new MyMaxHeap(i1, i2, i3);
        System.out.println(t4);
        t4.insert(500);
        System.out.println(t4);
        t4.insert(700);
        System.out.println(t4);
        t4.insert(600);
        System.out.println(t4);
    }

}


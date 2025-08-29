package Heap;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // tset 1
        ArrayList<Integer> i1 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7));
        MyMaxHeap t1 = new MyMaxHeap(i1);
        System.out.println(t1);
        // test 2
        ArrayList<Integer> i2 = new ArrayList<>(List.of(1, 2, 7, 4, 5, 9, 10));
        MyMaxHeap t2 = new MyMaxHeap(i2);
        System.out.println(t2);
        // test 3
        MyMaxHeap t3 = new MyMaxHeap(i1,i2);
        System.out.println(t3);
    }
}

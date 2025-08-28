package Heap;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> i1 = new ArrayList<>(List.of(1, 2, 3));
        MyMaxHeap t1 = new MyMaxHeap(i1);

        System.out.println(t1.toString());
    }
}

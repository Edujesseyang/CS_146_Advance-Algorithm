package Heap.HeapPractice;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class JunitTest {
    @Test
    public void testBuild() {
        MyMaxHeap h1 = new MyMaxHeap();
        assertNotNull(h1);
        assertEquals(0, h1.getSize());
        assertEquals(0, h1.getHeight());

        ArrayList<Integer> l1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, -5, 6, 7, 8, 9));
        ArrayList<Integer> l2 = new ArrayList<>(Arrays.asList(10, 20, 30, -40, -5, -5, -5, 80, 90));
        ArrayList<Integer> l3 = new ArrayList<>(Arrays.asList(100, -200, -300, 400, 500, 600, 700, 800, 900));
        MyMaxHeap h2 = new MyMaxHeap(l1);
        MyMaxHeap h3 = new MyMaxHeap(l1, l2, l3);
        assertNotNull(h2);
        assertNotNull(h3);
        assertEquals(9, h2.getMaxVal());
        assertEquals(9, h2.getSize());
        assertEquals(4, h2.getHeight());
        assertEquals(900, h3.getMaxVal());
        assertEquals(27, h3.getSize());
        assertEquals(5, h3.getHeight());
    }

    @Test
    public void testInsert() {
        MyMaxHeap h1 = new MyMaxHeap(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
        assertEquals(9, h1.getMaxVal());
        h1.insert(10);
        assertEquals(10, h1.getMaxVal());
        h1.insert(25);
        assertEquals(25,h1.getMaxVal());
        h1.insert(0);
        assertEquals(25,h1.getMaxVal());
    }
}

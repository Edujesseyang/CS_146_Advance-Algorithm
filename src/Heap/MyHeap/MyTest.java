package Heap.MyHeap;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;


public class MyTest {
    ArrayList<Integer> i1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
    ArrayList<Integer> i2 = new ArrayList<>(Arrays.asList(6, 7, 32, 46, 51));
    ArrayList<Integer> i3 = new ArrayList<>(Arrays.asList(15, 62, 73, 34, 8, 51, 101));

    MyHeap t1 = new MyHeap();
    MyHeap t2 = new MyHeap(i1);
    MyHeap t3 = new MyHeap(i1, i2, i3);


    @Test
    public void emptyBuild() {
        assertNotNull(t1);

    }

    @Test
    public void singleParamBuild() {
        assertNotNull(t2);
        assertEquals(6, t2.getMax());

    }

    @Test
    public void multiParamBuild() {
        MyHeap t3 = new MyHeap(i1, i2, i3);
        assertNotNull(t3);
        assertEquals(101, t3.getMax());
    }

    @Test
    public void insertsSingleVal() {
        t1.insert(10);
        assertEquals(10, t1.getMax());
        t2.insert(100);
        assertEquals(100, t2.getMax());
        t3.insert(800);
        assertEquals(800, t3.getMax());

    }

    @Test
    public void insertList() {
        t2.insert(i3);
        assertEquals(101, t2.getMax());
        t3.insert(new ArrayList<>(Arrays.asList(900, 901, 902)));
        assertEquals(902, t3.getMax());
    }

    @Test
    public void delete() {
        t3.insert(new ArrayList<>(Arrays.asList(800, 900, 901, 902)));
        System.out.println("Before delete :" + t3);
        t3.delete(0); // delete 902
        System.out.println("After deleting 902 : " + t3);
        assertEquals(901, t3.getMax());

        assertFalse(t3.contains(902));

        t3.delete(0); // delete 901
        assertEquals(900, t3.getMax());
        assertFalse(t3.contains(901));

        t3.delete(0); // delete 900
        assertEquals(800, t3.getMax());
        assertFalse(t3.contains(900));

        t3.delete(0); // delete 800
        assertEquals(101, t3.getMax());
        assertFalse(t3.contains(800));
        System.out.println("Final : " + t3);
    }
}

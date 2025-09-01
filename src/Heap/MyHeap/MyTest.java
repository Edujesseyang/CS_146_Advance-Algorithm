package Heap.MyHeap;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;


public class MyTest {
    @Test
    public void emptyBuild() {
        MyHeap t1 = new MyHeap();
        assertNotNull(t1);
    }

    @Test
    public void singleParamBuild() {
        MyHeap t1 = new MyHeap(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6)));
        assertNotNull(t1);
        assertEquals(6, t1.getMax());
        System.out.println(t1);
    }

    @Test
    public void multiParamBuild() {
        ArrayList<Integer> i1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> i2 = new ArrayList<>(Arrays.asList(6, 7, 32, 46, 51));
        ArrayList<Integer> i3 = new ArrayList<>(Arrays.asList(15, 62, 73, 34, 8, 51));

        MyHeap t1 = new MyHeap(i1, i2, i3);
        assertNotNull(t1);
        assertEquals(73, t1.getMax());
    }


}

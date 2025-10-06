package TwoThreeTree;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class Tests {
    @Test
    public void testSortOrder() { // test random 3000 elements
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 3000; i++) {
            input.add((int) Math.floor(Math.random() * 1000));
        }
        Tree t1 = new Tree(input);
        List<Integer> output = t1.toList();
        Integer compare = output.get(0);
        output.remove(0);
        for (Integer i : output) {
            assertTrue(compare < i);
            compare = i;
        }
        System.out.println(output);
    }

    @Test
    public void testConstructor() {
        Tree t1 = new Tree();
        assertNotNull(t1);
        System.out.println(t1);

        t1.insert(1);
        System.out.println(t1 + "\n");
        t1.insert(10);
        System.out.println(t1 + "\n");
        t1.insert(1);
        System.out.println(t1 + "\n");
        t1.insert(10);
        System.out.println(t1 + "\n");
        t1.insert(15);
        System.out.println(t1 + "\n");
        t1.insert(25);
        System.out.println(t1 + "\n");
        t1.insert(34);
        System.out.println(t1 + "\n");
        t1.insert(105);
        System.out.println(t1 + "\n");
        t1.insert(14);
        System.out.println(t1 + "\n");
        t1.insert(51);
        System.out.println(t1 + "\n");
        t1.insert(31);
        System.out.println(t1 + "\n");

        assertEquals(9, t1.size());
        assertEquals(9, t1.size(25));
        assertEquals(4, t1.size(10));
        System.out.println("10 has " + t1.size(10) + " children");
        System.out.println("25 has " + t1.size(25) + " children");


        int n1 = t1.get(2);
        System.out.println("index 2 = " + n1);


        int n2 = t1.get(5);
        System.out.println("index 5 = " + n2);
    }

    @Test
    public void testListConstruct() {
        List<Integer> input = new ArrayList<>(Arrays.asList(90, 54, 82, 2, 63, 1, 0, 45, 45, 90));
        Tree t1 = new Tree(input);

        assertEquals(8, t1.size);
        assertEquals(0, t1.size(255));

        List<Integer> input2 = new ArrayList<>(Arrays.asList(905, 544, 852, 24, 653, 15, 4, 454, 435, 950));
        List<Integer> input3 = new ArrayList<>(Arrays.asList(5, 4, 2, 2, 3, 1, 0, 4, 5, 9));
        t1.insert(input2);
        t1.insert(input3);
        System.out.println(t1);

        int n1 = t1.get(3);
        assertEquals(3, n1);
        System.out.println("index 3 = " + n1);

        int n2 = t1.get(4);
        assertEquals(4, n2);
        System.out.println("index 4 = " + n2);

        int n3 = t1.get(5);
        assertEquals(5, n3);
        System.out.println("index 5 = " + n3);

        int n4 = t1.get(14);
        assertEquals(435, n4);
        System.out.println("index 14 = " + n4);

        int n5 = t1.get(15);
        assertEquals(454, n5);
        System.out.println("index 15 = " + n5);

        int n6 = t1.get(19);
        assertEquals(905, n6);
        System.out.println("index 20 = " + n6);

        int n7 = t1.get(20);
        assertEquals(950, n7);
        System.out.println("index 950 = " + n7);

        try {
            int n8 = t1.get(220);
            System.out.println(n8);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index out of bound", e.getMessage());
        }
        Tree t2 = new Tree();
        assertEquals(0, t2.size());
        assertEquals(0, t2.size(2));

        try {
            int n8 = t1.get(-1);
            System.out.println(n8);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index out of bound", e.getMessage());
        }

        try {
            int n8 = t2.get(0);
            System.out.println(n8);
        } catch (IllegalStateException e) {
            assertEquals("empty tree", e.getMessage());
        }

        try {
            Integer i = null;
            t2.insert(i);
        } catch (IllegalArgumentException e) {
            assertEquals("null parameter", e.getMessage());
        }
    }
}

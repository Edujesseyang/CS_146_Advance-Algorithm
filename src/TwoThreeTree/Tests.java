package TwoThreeTree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class Tests {

    @Test
    public void testConstructor() {
        Tree t1 = new Tree();
        assertNotNull(t1);
        try {
            int key = t1.get(244);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index out of bound", e.getMessage());
        }
        // test empty tree
        assertEquals(0, t1.size());
        assertEquals(0, t1.size(0));

        t1.insert(1); //total = 1
        t1.insert(10); //total = 2
        // now      1 10
        assertEquals(2, t1.size());
        assertEquals(2, t1.size(1));
        assertEquals(2, t1.size(10));
        t1.insert(1);
        t1.insert(10);
        t1.insert(15); //total = 3
        // now:    10
        //        /  \
        //       1    15
        assertEquals(3, t1.size());
        assertEquals(1, t1.size(1));
        assertEquals(1, t1.size(15));
        assertEquals(3, t1.size(10));

        t1.insert(25); //total = 4
        // now:    10
        //        /  \
        //      1   15 25
        assertEquals(4, t1.size());
        assertEquals(1, t1.size(1));
        assertEquals(2, t1.size(15));
        assertEquals(2, t1.size(25));
        assertEquals(4, t1.size(10));

        t1.insert(31); //total = 5
        // now:    10 25
        //        /  |  \
        //       1   15  31
        assertEquals(5, t1.size());
        assertEquals(1, t1.size(1));
        assertEquals(1, t1.size(15));
        assertEquals(5, t1.size(25));
        assertEquals(5, t1.size(10));
        assertEquals(1, t1.size(31));

        t1.insert(1);
        t1.insert(14); //total = 6
        // now:    10 25
        //        /  |  \
        //      1  14 15  31
        assertEquals(6, t1.size());
        assertEquals(1, t1.size(1));
        assertEquals(2, t1.size(14));
        assertEquals(2, t1.size(15));
        assertEquals(6, t1.size(25));
        assertEquals(6, t1.size(10));
        assertEquals(1, t1.size(31));

        t1.insert(14);
        t1.insert(31);
        t1.insert(14);
        t1.insert(31);
        t1.insert(1);
        t1.insert(10);
        t1.insert(15); // adding dup
        assertEquals(6, t1.size());
        assertEquals(1, t1.size(1));
        assertEquals(2, t1.size(14));
        assertEquals(2, t1.size(15));
        assertEquals(6, t1.size(25));
        assertEquals(6, t1.size(10));
        assertEquals(1, t1.size(31));

        assertEquals(0, t1.size(311));
        assertEquals(0, t1.size(314));
        assertEquals(0, t1.size(351));
        assertEquals(0, t1.size(3151));


        // [1, 10, 14, 15, 25, 31]
        assertEquals(1, t1.get(0));
        assertEquals(10, t1.get(1));
        assertEquals(14, t1.get(2));
        assertEquals(15, t1.get(3));
        assertEquals(25, t1.get(4));
        assertEquals(31, t1.get(5));

        try {
            int key = t1.get(244);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index out of bound", e.getMessage());
        }

    }

    @Test
    public void testListConstruct() {
        int[] input = {90, 54, 82, 2, 63, 1, 0, 45, 45, 90};
        Tree t1 = new Tree(input);
        assertEquals(8, t1.size);
        assertEquals(0, t1.size(255));

        int[] input2 = {905, 544, 852, 24, 653, 15, 4, 454, 435, 950};
        int[] input3 = {5, 4, 2, 2, 3, 1, 0, 4, 5, 9};
        t1.insert(input2);
        t1.insert(input3);

        int n1 = t1.get(3);
        assertEquals(3, n1);
        int n2 = t1.get(4);
        assertEquals(4, n2);
        int n3 = t1.get(5);
        assertEquals(5, n3);
        int n4 = t1.get(14);
        assertEquals(435, n4);
        int n5 = t1.get(15);
        assertEquals(454, n5);
        int n6 = t1.get(19);
        assertEquals(905, n6);
        int n7 = t1.get(20);
        assertEquals(950, n7);

        Tree t2 = new Tree();
        assertEquals(0, t2.size());
        assertEquals(0, t2.size(2));

    }

    @Test
    public void testNewAdding() {
        Tree t1 = new Tree();
        t1.insert(19);
        t1.insert(20);
        t1.insert(21);
        assertEquals(1, t1.size(19));
        assertEquals(3, t1.size(20));
        assertEquals(1, t1.size(21));

        Tree t = new Tree();
        t.insert(new int[]{5, 10});
        assertEquals(2, t.size(5));
        assertEquals(2, t.size(10));
        assertEquals(0, t.size(7));


    }

    @Test
    public void testTwoThreeTree_CorrectnessTest() {
        // empty tree
        Tree t = new Tree();
        assertEquals(0, t.size());

        // basic insert
        assertTrue(t.insert(10));                      // [10]
        assertEquals(1, t.size());
        assertEquals(Arrays.asList(10), t.toList());
        assertEquals(1, t.size(10));         // single key leaf and root

        assertTrue(t.insert(20));                      // [10,20] two keys leaf
        assertEquals(2, t.size());
        assertEquals(Arrays.asList(10, 20), t.toList());

        assertEquals(2, t.size(10));
        assertEquals(2, t.size(20));

        assertTrue(t.insert(30));                      // (split)：root=[20], L=[10], R=[30]
        assertEquals(3, t.size());
        assertEquals(Arrays.asList(10, 20, 30), t.toList());
        assertEquals(3, t.size(20));                   // root subtree size =3

        assertEquals(1, t.size(10));                   // leaf
        assertEquals(1, t.size(30));                   // leaf

        // ---- insert more ----
        // inset 40,50,60,70，：
        //            [40]
        //        /         \
        //     [20]         [60]
        //    /    \       /    \
        //  [10]  [30]   [50]  [70]
        assertTrue(t.insert(40));
        assertTrue(t.insert(50));
        assertTrue(t.insert(60));
        assertTrue(t.insert(70));
        assertEquals(7, t.size());
        assertEquals(Arrays.asList(10, 20, 30, 40, 50, 60, 70), t.toList());

        // check structure（use size(key)）
        assertEquals(7, t.size(40));   // subtree or root = size
        assertEquals(3, t.size(20));   // left [20] + [10] + [30]
        assertEquals(3, t.size(60));   // right [60] + [50] + [70]
        assertEquals(1, t.size(10));
        assertEquals(1, t.size(30));
        assertEquals(1, t.size(50));
        assertEquals(1, t.size(70));

        // ---- check sorting order ----
        List<Integer> sorted = Arrays.asList(10, 20, 30, 40, 50, 60, 70);
        for (int i = 0; i < sorted.size(); i++) {
            assertEquals(sorted.get(i).intValue(), t.get(i));
        }

        // ---- insert duplicate, size should not be changed----
        int before = t.size();
        t.insert(40);
        assertEquals(before, t.size());

        // ---- size(key) for non-exists key ----
        assertEquals(0, t.size(99999));

        // ---- list input ----
        Random rnd = new Random(42);
        int[] input = new int[1000];
        for (int i = 0; i < input.length - 1; i++) {
            input[i] = rnd.nextInt(1000); // may or may not include duplicates
        }
        Tree t2 = new Tree(input);
        System.out.println(t2.toList());
    }
}

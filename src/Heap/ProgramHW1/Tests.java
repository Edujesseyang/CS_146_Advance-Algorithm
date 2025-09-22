package Heap.ProgramHW1;

import static org.junit.Assert.*;
import org.junit.Test;
import java.security.InvalidParameterException;
import java.util.*;

public class Tests {
    private final Student jesse = new Student("Jesse", 3.8, 15);
    private final Student chloe = new Student("Chloe", 3.9, 12);
    private final Student lucky = new Student("Lucky", 2.8, 3);
    private final Student cookie = new Student("Cookie", 1.5, 9);
    private final Student klee = new Student("Klee", 0.8, 3);
    private final Student sushi = new Student("Sushi", 1.2, 6);
    private final List<Student> allStudents = new ArrayList<>(Arrays.asList(lucky, cookie, chloe, klee, jesse, sushi));

    @Test
    public void testBuildWithCapacity() {
        MaxHeap test1 = new MaxHeap(10);
        assertNotNull(test1);
    }

    @Test
    public void testBuildWithParameter() {
        MaxHeap test1 = new MaxHeap(allStudents);
        assertNotNull(test1);
        assertEquals(chloe, test1.getMax());
    }

    @Test
    public void testGetMaxAndExtractMax() {
        MaxHeap test1 = new MaxHeap(allStudents);
        assertEquals(chloe, test1.getMax());
        assertEquals(chloe, test1.extractMax());
        assertEquals(jesse, test1.getMax());
        assertEquals(jesse, test1.extractMax());
        assertEquals(lucky, test1.getMax());
        assertEquals(lucky, test1.extractMax());
        assertEquals(cookie, test1.getMax());
        assertEquals(cookie, test1.extractMax());
        assertEquals(sushi, test1.getMax());
        assertEquals(sushi, test1.extractMax());
        assertEquals(klee, test1.getMax());
        assertEquals(klee, test1.extractMax());

        MaxHeap test2 = new MaxHeap(1);
        try {
            Student bestStudent = test2.getMax();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("No maximum value:  the heap is empty.", e.getMessage());
        }
    }

    @Test
    public void testSizeAndInsert() {
        MaxHeap test1 = new MaxHeap(10);
        assertEquals(0, test1.size());
        test1.insert(klee);
        assertEquals(klee, test1.getMax());
        assertEquals(1, test1.size());
        test1.insert(sushi);
        assertEquals(sushi, test1.getMax());
        assertEquals(2, test1.size());
        test1.insert(cookie);
        assertEquals(cookie, test1.getMax());
        assertEquals(3, test1.size());
        test1.insert(lucky);
        assertEquals(lucky, test1.getMax());
        assertEquals(4, test1.size());
        test1.insert(jesse);
        assertEquals(jesse, test1.getMax());
        assertEquals(5, test1.size());
        test1.insert(chloe);
        assertEquals(chloe, test1.getMax());
        assertEquals(6, test1.size());

        try {
            test1.insert(null);
        } catch (InvalidParameterException e) {
            assertEquals("Null parameter", e.getMessage());
        }
    }

    @Test
    public void testAddGrade() {
        List<Student> students = new ArrayList<>(Arrays.asList(klee, sushi));
        MaxHeap test1 = new MaxHeap(students);
        assertEquals(sushi, test1.getMax());
        test1.addGrade(klee, 2.0, 3);
        assertEquals(klee, test1.getMax());
        test1.addGrade(sushi, 2.5, 3);
        assertEquals(sushi, test1.getMax());
        test1.addGrade(sushi, -2.0, 3);
        assertEquals(klee, test1.getMax());
        test1.addGrade(klee, -2.5, 3);
        assertEquals(sushi, test1.getMax());
        test1.addGrade(sushi, 4.0, 3);
        assertEquals(sushi, test1.getMax());

        MaxHeap test2 = new MaxHeap(allStudents);
        double sushiGPA = sushi.gpa();
        test2.addGrade(sushi, 0, 6);
        assertNotEquals(sushiGPA, sushi.gpa());


        try {
            test1.addGrade(null, 30, 30);
        } catch (InvalidParameterException e) {
            assertEquals("Null parameter", e.getMessage());
        }

        try {
            test1.addGrade(jesse, 2.3, 3);
        } catch (NoSuchElementException e) {
            assertEquals("No such this student present", e.getMessage());
        }

    }

    @Test
    public void testStudentMethods() {
        assertEquals("Jesse", jesse.getName());
        assertEquals(3.8, jesse.gpa(), 0.001);
        Student jack = new Student("jack");
        jack.addGrade(3.8, 0);
        assertEquals(0, jack.gpa(), 0.001);
        Student mike = new Student("mike", 4.0, 10);
        assertTrue(mike.compareTo(jack) > 0);
        assertTrue(jack.compareTo(mike) <= 0);
        jack.addGrade(3.8, 15);
        assertEquals(0, jesse.compareTo(jack));
        assertEquals(3.8, jack.gpa(), 0.0001);
        assertEquals(jack.getIndex(), -1);
        jack.setIndex(15);
        assertEquals(jack.getIndex(), 15);

        try {
            jack.setIndex(-2);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index out of bound", e.getMessage());
        }


    }

    // part2:

    @Test
    public void testExtractMaxOnEmptyHeap() {
        MaxHeap t1 = new MaxHeap(20);
        try { // test extractMax on empty heap
            t1.extractMax();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("No maximum value:  the heap is empty.", e.getMessage());
        }

        MaxHeap t2 = new MaxHeap(allStudents);
        while (t2.size() > 0) {
            assertEquals(0, t2.getMax().getIndex());
            t2.extractMax();
        }
        assertEquals(0, t2.size());

        MaxHeap t3 = new MaxHeap(allStudents);


    }


    @Test
    public void testObjectIndex() {
        MaxHeap t3 = new MaxHeap(10);
        t3.insert(klee);
        assertEquals(0, t3.getMax().getIndex());
        assertEquals(klee, t3.getMax());

        t3.insert(lucky);
        assertEquals(0, t3.getMax().getIndex());
        assertEquals(lucky, t3.getMax());

        t3.insert(jesse);
        assertEquals(0, t3.getMax().getIndex());
        assertEquals(jesse, t3.getMax());

        t3.addGrade(klee, 4, 100);
        assertEquals(0, t3.getMax().getIndex());
        assertEquals(klee, t3.getMax());
        assertEquals(0, klee.getIndex());

        t3.addGrade(lucky, 4, 100);
        assertEquals(0, t3.getMax().getIndex());
        assertEquals(lucky, t3.getMax());
        assertEquals(0, lucky.getIndex());

        t3.addGrade(jesse, 4, 100);
        assertEquals(0, t3.getMax().getIndex());
        assertEquals(jesse, t3.getMax());
        assertEquals(0, jesse.getIndex());

        // try to find unexist student
        Student nobody = new Student("Nobody");
        try {
            t3.addGrade(nobody, 4, 12);
        } catch (NoSuchElementException e) {
            assertEquals("No such this student present", e.getMessage());
        }
    }

}

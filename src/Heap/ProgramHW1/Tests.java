package Heap.ProgramHW1;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class Tests {
    private final Student jesse = new Student("Jesse", 3.8, 15);
    private final Student chloe = new Student("Chloe", 3.9, 12);
    private final Student lucky = new Student("Lucky", 2.8, 3);
    private final Student cookie = new Student("Cookie", 1.5, 9);
    private Student klee = new Student("Klee", 0.8, 3);
    private Student sushi = new Student("Sushi", 1.2, 6);
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
    }

    @Test
    public void testInsertRecursively() {
        MaxHeap test1 = new MaxHeap(10);
        assertEquals(0, test1.size());
        test1.insertRecursively(klee);
        assertEquals(klee, test1.getMax());
        assertEquals(1, test1.size());
        test1.insertRecursively(sushi);
        assertEquals(sushi, test1.getMax());
        assertEquals(2, test1.size());
        test1.insertRecursively(cookie);
        assertEquals(cookie, test1.getMax());
        assertEquals(3, test1.size());
        test1.insertRecursively(lucky);
        assertEquals(lucky, test1.getMax());
        assertEquals(4, test1.size());
        test1.insertRecursively(jesse);
        assertEquals(jesse, test1.getMax());
        assertEquals(5, test1.size());
        test1.insertRecursively(chloe);
        assertEquals(chloe, test1.getMax());
        assertEquals(6, test1.size());
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
    }

}
